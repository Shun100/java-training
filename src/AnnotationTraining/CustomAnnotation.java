/**
 * - アノテーションの作成方法
 *   - アノテーションは広い意味でのインタフェースの1つであり、構文もインタフェースに似ている。
 * 
 * - アノテーション利用者からの見え方
 *   - @Overrideのように属性がないアノテーションは、対象の要素に@Overrideと付与するだけ。
 *   - @Deprecatedのように属性を持つアノテーションは、対象の要素に@Override(forRemoval=true)のように付与する。
 *    - ただし、属性にデフォルト値が設定されている場合は属性は省略可能。
 * 
 * - メタアノテーションとは
 *   - アノテーションに付与するためのアノテーション
 *   
 *   - @java.lang.annotation.Retention: 保持ポリシーを表す。
 *     - 省略すると自動的にCLASSになる。
 *   - @java.lang.annotation.Target: 付与対象を表す。
 *     - 省略すると自動的に全ての要素が付与対象になる。
 *   - @java.lang.annotation.Documented: ドキュメント対象であることを表す。
 * 
 * - カスタムアノテーションの情報取得
 *   - 保持ポリシーをRUNTIMEに設定し、実行時にリフレクションAPIによって情報を取得し、それに対して何らかの処理を行うことが多い。
 */

package AnnotationTraining;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface CustomAnnotation {
  String value();               // アノテーションの属性1 必須属性
  String elemName() default ""; // アノテーションの属性2 デフォルト値あり
}
