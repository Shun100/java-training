import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class UDPClient {
  public static void main(String[] args) {
    ByteBuffer buffer = ByteBuffer.allocate(1000);
    buffer = StandardCharsets.UTF_8.encode("Alice");
    try (DatagramChannel channel = DatagramChannel.open()) {
      channel.send(buffer, new InetSocketAddress("localhost", 55555));
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
