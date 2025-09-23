import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Main {
  // クラスファイル -(クラスローダ)-> スタティック領域 -(インスタンス生成)-> ヒープ領域

  /**
   * クラスに対して以下の処理が行われると、クラスローダによってクラスがロードされる。
   * 1. スタティックなメンバへのアクセス
   * 2. ClassクラスのforName()メソッド呼び出し
   * 3. new演算子によるインスタンス生成
   * 4. ConstructorクラスのnewInstance()メソッド呼び出し
   */
  
  public static void main(String[] args) {
    showPlatformClassLoader();
    showSystemClassLoader();
    showContextClassLoader("./config.properties");
  }

  /**
   * プラットフォームクラスローダ
   */
  private static void showPlatformClassLoader() {
    ClassLoader pfClassLoader = ClassLoader.getPlatformClassLoader();
    System.out.println(pfClassLoader.getParent()); // null BootStrapクラスローダが上位の存在するが、この方法では参照できない。
  }

  /**
   * システムクラスローダ
   */
  private static void showSystemClassLoader() {
    ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
    System.out.println(sysClassLoader.getParent()); // プラットフォームクラスローダ
  }

  /**
   * コンテキストクラスローダ
   * <p>
   * 開発者がクラスローダを明示的に利用するケースとして、リソースの読み込みが挙げられる。
   * リソースとはクラス以外の静的コンテンツのことで、読み込みにはコンテキストクラスローダが必要。
   * ここでは例としてプロパティファイルを扱う。
   * </p>
   * @param String config - コンフィグファイル
   */
  private static void showContextClassLoader(String config) {
    Properties props = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    
    try (InputStream is = classLoader.getResourceAsStream(config)) {
      props.load(new InputStreamReader(is, StandardCharsets.UTF_8));

      // 項目の読取
      String name = props.getProperty("name");
      int age = Integer.parseInt(props.getProperty("age"));
      String address = props.getProperty("address");

      System.out.println("name = " + name);
      System.out.println("age = " + age);
      System.out.println("address = " + address);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
