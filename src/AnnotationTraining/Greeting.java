package AnnotationTraining;

@CustomAnnotation("挨拶クラス") // メタ情報を保持するアノテーション
public class Greeting {
  @CustomAnnotation("名前") // メタ情報を保持するアノテーション
  @MaxLength(8)
  private String name;
  
  private Greeting(String name) {
    this.name = name;
  }

  public static Greeting create(String name) {
    Greeting greeting = new Greeting(name);
    AnnotationProcessor.checkMaxLength(greeting);

    return greeting;
  }

  @CustomAnnotation("挨拶する")
  public void sayHello(@CustomAnnotation(value="年齢", elemName="age") int age) {
    System.out.println(String.format("こんにちは。私は%s, %d歳です。", name, age));
  }
}
