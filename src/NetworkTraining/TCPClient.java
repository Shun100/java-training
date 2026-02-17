import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCPClient {
  public static void main(String[] args) {
    ByteBuffer requestBuffer = ByteBuffer.allocate(1000);
    ByteBuffer responseBuffer = ByteBuffer.allocate(1000);
    requestBuffer = StandardCharsets.UTF_8.encode("Alice");
    try (
      /*
       * 接続の確立
       * ここで3ウェイハンドシェイクが行われる
       * 接続が確立すると、サーバ側のaccept()メソッドのブロッキングが解放される
       */
      SocketChannel socketChannel = SocketChannel.open(
        new InetSocketAddress("localhost", 55555))) {
      // ブロッキングモードのため、送信処理が終わるまで受信はできない
      socketChannel.write(requestBuffer); // ここでブロッキング
      // レスポンスを受け取るまでブロッキング
      socketChannel.read(responseBuffer); // ここでブロッキング
      responseBuffer.flip(); // 読出開始位置を0に設定
      String response = StandardCharsets.UTF_8.decode(responseBuffer).toString();
      System.out.println("response => " + response);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
