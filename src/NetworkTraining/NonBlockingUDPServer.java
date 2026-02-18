import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NonBlockingUDPServer {
  private static Selector selector;

  public static void main(String[] args) {
    try (DatagramChannel channel = DatagramChannel.open()) {
      channel.bind(new InetSocketAddress(55555));
      channel.configureBlocking(false);
      selector = Selector.open();
      channel.register(selector, SelectionKey.OP_READ);

      while (0 < selector.select()) {
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();
          if (key.isAcceptable()) {
            read((DatagramChannel) key.channel());
          }
        }
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  private static void read(DatagramChannel channel) {
    ByteBuffer buffer = ByteBuffer.allocate(1000);
    try {
      channel.receive(buffer);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
    buffer.flip();
    String request = StandardCharsets.UTF_8.decode(buffer).toString();
    System.out.println("Hello, 私は " + request + " です");
  }
}
