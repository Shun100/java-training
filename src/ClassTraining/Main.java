/**
 * Classクラスの学習量
 * 
 * - java.lang.Classクラス
 *  - クラスやインタフェースのメタ情報を管理するためのクラス
 *  - メタ情報には、そのクラス・インターフェースの名称や、フィールド・メソッド・コンストラクタの情報などが含まれる。
 *  - ClassオブジェクトはJavaランタイム内に1つしか存在せず、自動的に管理されるため、明示的な生成は不可。
 * 
 * - Classオブジェクトの参照方法
 *  - 全てのクラスの親にあたるjava.lang.Objectクラスには、getClass()メソッドが定義されており、自身のClassオブジェクトを取得可能
 * 
 * - Classクラスが持つAPI
 *  - クラスの情報を取得するためのAPI
 *  - キャストするためのAPI
 *  - リフレクションのためのAPI
 *  - アノテーションを取得するためのAPI
 */

package ClassTraining;

public class Main {

  public static void main(String[] args) {
    Main main = new Main();
    main.showClassObject();
    main.showIntegerClassObject();
    main.showClassInfo();

    main.showClassObject2();
  }

  /*
   * クラスオブジェクトは.getClass()メソッドで取得する。
   */
  private void showClassObject() {
    Greeting greeting = new Greeting();
    Class<?> clazz = greeting.getClass();
    System.out.println(clazz);
  }

  private void showClassObject2() {
    Animal dog = new Dog();

    System.out.println(dog.getClass());  // Dog (動的に取得)
    System.out.println(Animal.class);    // Animal (静的に取得)
  }

  /**
   * プリミティブ型の場合は.TYPEで取得する。
   */
  private void showIntegerClassObject() {
    System.out.println(Integer.TYPE);
  }

  private void showClassInfo() {
    Class<Greeting> clazz = Greeting.class;
    
    System.out.println(clazz.getCanonicalName()); // クラスの正規名（≒FQCN 入れ子クラスでは異なる）
    System.out.println(clazz.getSimpleName());    // クラスの単純名
    System.out.println(clazz.getPackageName());   // パッケージ名
  }
}
