/**
 * - アノテーションの概要
 *   - クラス、メソッド、フィールドなどの要素に対して「注釈」として不要するメタデータ記法のこと。
 * 　- 何らかのツールや別のソフトウェアに解釈されることによって、はじめて機能する。
 * 
 * - アノテーションの保持ポリシー
 *   - SOURCE: コンパイラによって評価され、コンパイル時に破棄される。
 *     - 例. @Override
 *   - CLASS: コンパイル後もクラスファイルに記録され、実行時に破棄される。
 *   - RUNTIME: 実行時まで保持される。リフレクションAPIでアノテーションの情報を取得可能。 
 *     - 例. @FunctionalInterface
 * 
 * - アノテーションに付与対象
 *   - java.lang.annotaion.ElementType列挙型によって表される。
 *   
 *   - TYPE: クラス、インタフェース、列挙型など
 *     - 例. @FunctionalInterface
 *   - METHOD: メソッド
 *     - 例. @Override
 *   - FIELD: フィールド
 *   - CONSTRUCTOR: コンストラクタ
 *   - LOCAL_VARIABLE: ローカル変数
 *   - PARAMETER: メソッド引数
 * 
 * - JavaSEのアノテーション
 *   - @java.lang.Override
 *     - 保持ポリシー: SOURCE
 *     - 付与対象: METHOD
 *     - 説明: オーバライドメソッドに付与し、コンパイラにオーバライドのルール違反を検知させる。
 *   
 *   - @java.lang.FunctionalInterface
 *     - 保持ポリシー: RUNTIME
 *     - 付与対象: TYPE
 *     - 説明: 関数型インタフェースに付与し、コンパイラに関数型インタフェースのルール違反を検知させる。
 *   
 *   - @java.lang.Deprecated
 *     - 保持ポリシー: RUNTIME
 *     - 付与対象: TYPE, METHOD, FIELD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETERなど
 *     - 説明: 非推奨扱いの要素に付与し、コンパイラに警告を出させる。
 *   
 *   - @java.lang.SuppressWarnings
 *     - 保持ポリシー: SOURCE
 *     - 付与対象: TYPE, METHOD, FIELD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETERなど
 *     - 説明: コンパイラが発する警告を抑制する。
 * 
 * - JavaSEアノテーションの内部実装
 *   - @Override
 *     ``` Java
 *     @Retention(RetentionPolicy.SOURCE)
 *     @Target(ElementType.METHOD)
 *     public @interface Override {}
 *     ```
 *   - @Deprecated
 *     ``` Java
 *     @Retention(RetentionPolicy.RUNTIME)
 *     @Target({TYPE, FILED, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODUKE, PACKAGE})
 *     @Documented
 *     public @interface Deprecated {
 *       String since() default "";
 *       boolean forRemoval() defalt false;
 *     }
 *     ```
 */

package AnnotationTraining;

public class Main {
  public static void main(String[] args) {
    Main main = new Main();
    // main.showMetaInfo();
    main.validate();
  }

  /*
  private void showMetaInfo() {
    Greeting greeting = new Greeting("Alice");
    greeting.sayHello(25);

    // アノテーション情報の取得
    CustomAnnotation classAnnotation1 = Greeting.class.getAnnotation(CustomAnnotation.class);
    System.out.println("value = " + classAnnotation1.value());
    System.out.println("elemName = " + classAnnotation1.elemName());

    CustomAnnotation classAnnotation2 = greeting.getClass().getAnnotation(CustomAnnotation.class);
    System.out.println("value = " + classAnnotation2.value());
    System.out.println("elemName = " + classAnnotation2.elemName());
  }
  */

  private void validate() {
    /*
    Greeting greeting = new Greeting("123456789");
    AnnotationProcessor.checkMaxLength(greeting);
    */

    Greeting greeting = Greeting.create("123456789");
  }
}
