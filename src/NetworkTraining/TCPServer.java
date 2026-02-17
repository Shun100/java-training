import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCPServer {
  public static void main(String[] args) {
    try (ServerSocketChannel ssc = ServerSocketChannel.open()) { // チャネルをオープン
      // ソケットアドレス（ポート番号）をバインド
      ssc.bind(new InetSocketAddress(55555));
      while (true) { // 無限ループで常駐プロセス化
        // 接続を確立（ブロッキング）
        SocketChannel socketChannel = ssc.accept();
        readAndWrite(socketChannel); // socketチャネル: TCPクライアントとの間でデータの読み書きするためのチャネル
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public static void readAndWrite(SocketChannel socketChannel) {
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
