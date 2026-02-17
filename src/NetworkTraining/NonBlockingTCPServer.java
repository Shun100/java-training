import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockingTCPServer {
  private static Selector selector;
  public static void main(String[] args) {
    try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
      ssc.bind(new InetSocketAddress(5555));
      ssc.configureBlocking(false);
      selector = Selector.open();
      ssc.register(selector, SelectionKey.OP_ACCEPT);

      while (0 < selector.select()) {
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        // WIP
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  // 接続処理
  private static void accept(ServerSocketChannel ssc) {

  }

  // 読込と書込処理
  private static void readAndWrite(SocketChannel socketChannel) {
    
  }
}
