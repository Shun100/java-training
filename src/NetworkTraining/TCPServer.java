import java.nio.channels.ServerSocketChannel;

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
    
  }
}
