package AnnotationTraining;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationProcessor {
  /**
   * 文字列の長さチェック
   * <p>
   * 受け取ったインスタンスに対し、そのインスタンスが持つ各フィールに対して@MaxLengthアノテーションが付与されているかチェックする。
   * 付与されていれば、フィールドにセットされた文字列の長さと、アノテーションの属性値を比較して長さチェックを行う。
   * </p>
   * @param Object object - 長さチェックを行う対象のインスタンス
   */
  public static void checkMaxLength(Object object) {
    // インスタンスが持つフィールドの一覧を取得
    List<Field> fieldList = new ArrayList<>(
      Arrays.asList(object.getClass().getDeclaredFields()));

    for (Field field : fieldList) {
      MaxLength fieldAnnotation = field.getAnnotation(MaxLength.class);

      // @MaxLengthアノテーションが付与されていなければスキップする
      if (fieldAnnotation == null) {
        continue;
      }
      
      try {
        field.setAccessible(true);
        String str = (String) field.get(object);
        int max = fieldAnnotation.value();

        if (str.length() > max) {
          throw new RuntimeException(max + "文字以内で入力してください");
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
