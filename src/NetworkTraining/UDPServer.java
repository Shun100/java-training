import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class UDPServer {
  public static void main(String args[]) {
    ByteBuffer buffer = ByteBuffer.allocate(1000);
    /*
     * DatagramChannel: データを読み込んだり書き込んだりするためのチャネル
     * UDPには接続確立が無いため、TCPにおけるServerSocketChannelに相当するクラスは無い
     */
    try (DatagramChannel channel = DatagramChannel.open()) {
      channel.bind(new InetSocketAddress(55555));
      while (true) {
        channel.receive(buffer); // 受信待機 ブロッキングポイント
        buffer.flip();
        String request = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println("Hello, 私は " + request + " です");
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
