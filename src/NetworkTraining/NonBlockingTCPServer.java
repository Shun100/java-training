import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NonBlockingTCPServer {
  private static Selector selector;
  public static void main(String[] args) {
    try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
      ssc.bind(new InetSocketAddress(5555));
      ssc.configureBlocking(false);
      
      // Selector: どのチャネルで何のイベントが発生したか監視する仕組み
      selector = Selector.open();
      // OP_ACCEPTを監視対象に指定 = クライアントから接続要求が来たら通知してほしい の意味
      ssc.register(selector, SelectionKey.OP_ACCEPT);

      /*
       * selector.keys(): 登録されている全てのソケットチャネル
       * selector.selectedKeys(): イベントが発生したソケットチャネルだけ
       */
      while (0 < selector.select()) { // 何らかのイベント(接続要求 or データ受信)を待つ 待つ間はブロッキング
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

        // イベントが発生したソケットチャネルごとに、イベントの内容を判定して適切な処理を行う
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();
          if (key.isAcceptable()) { // 接続要求イベントの場合
            accept((ServerSocketChannel) key.channel());
          } else if (key.isReadable()) { // データ受信イベントの場合
            readAndWrite((SocketChannel) key.channel());
          }
        }
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  // 接続処理
  private static void accept(ServerSocketChannel ssc) {
    try {
      SocketChannel socketChannel = ssc.accept();
      socketChannel.configureBlocking(false);
      socketChannel.register(selector, SelectionKey.OP_READ);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  // 読込と書込処理
  private static void readAndWrite(SocketChannel socketChannel) {
    ByteBuffer requestBuffer = ByteBuffer.allocate(1000);
    ByteBuffer responseBuffer = ByteBuffer.allocate(1000);
    try {
      // 受信処理
      // SocketChannelからByteBufferにデータを読み込み（ブロッキング）
      socketChannel.read(requestBuffer);
      requestBuffer.flip(); // 取り出し開始位置を0に設定
      String request = StandardCharsets.UTF_8.decode(requestBuffer).toString();
      
      // 送信処理
      String response = "Hello, 私は" + request + "です";
      responseBuffer = StandardCharsets.UTF_8.encode(response);
      socketChannel.write(responseBuffer);
      socketChannel.close();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
