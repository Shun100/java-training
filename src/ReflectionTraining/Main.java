/**
 * リフレクションの概要
 * クラスのメタ情報を元に様々な処理が行える機能。
 * 静的型付けに反するようなことも可能であり、一種の黒魔術のようなもの。
 * フレームワークの開発によく利用される。
 * 
 * - 文字列として与えられたクラス名からインスタンスを生成する。
 * - 文字列をして与えれられたメソッド名からメソッドを呼び出す。
 */

package ReflectionTraining;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class Main {
  public static void main(String[] args) {

    try {
      Class<?> clazz = Class.forName("ReflectionTraining.Greeting");
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      Object instance = constructor.newInstance();
      Method method1 = clazz.getMethod("sayHello");
      Object result1 = method1.invoke(instance);

      // privateメソッドも呼び出し可能
      Method method2 = clazz.getDeclaredMethod("sayGoodNight");
      method2.setAccessible(true);
      Object result2 = method2.invoke(instance);

      // 引数のあるメソッドの呼び出し
      Method method3 = clazz.getDeclaredMethod("sayMessage", String.class);
      Object[] params = {"Good Morning"};
      Object result3 = method3.invoke(instance, params);

      // BeanからBeanに値を移し替える
      Person person = new Person("Alice", 25, "中央区1-1-1");
      Customer customer = new Customer(1, "Alice");
      Class<Person> personClazz = Person.class;
      Class<Customer> customerClazz = Customer.class;

      Field personNameField = personClazz.getDeclaredField("name");
      Field customerNameField = customerClazz.getDeclaredField("name");
      personNameField.setAccessible(true);
      customerNameField.setAccessible(true);
      Object personName = personNameField.get(person);
      Object customerName = customerNameField.get(customer);

      // 氏名が一致していれば各フィールドの値をコピー
      if (Objects.equals(personName, customerName)) {
        PERSON : for (Field personField : personClazz.getDeclaredFields()) {
          for (Field customerField : customerClazz.getDeclaredFields()) {
            if (personField.getName().equals(customerField.getName())) {
              personField.setAccessible(true);
              customerField.setAccessible(true);
              customerField.set(customer, personField.get(person));

              System.out.println(personField.get(person));
              System.out.println(customerField.get(customer));

              continue PERSON;
            }
          }
        }
      }
      
    } catch (ClassNotFoundException e) {
      System.err.println("class not found");
    } catch (NoSuchMethodException e) {
      System.err.println("method not found");
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      System.err.println("failed to generate an instance");
    } catch (NoSuchFieldException e) {
      System.err.println("field not found");
    }



  }
}
