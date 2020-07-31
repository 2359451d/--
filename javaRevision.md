# Revision

self-revision using: fundamentals of Java

## Do While

```java
do {
    // looping body
}while(bool exp);
```

## Increment

```java
do{
    System.out.println(i++); // assigning first then do the increment
    // int temp = i++;
    // System.out.println(temp);
    System.out.println(++i); // increment first then assigning
}while(bool exp);
```

## JVM: Method Area

![](/static/2020-07-26-21-37-48.png)
![](/static/2020-07-27-11-41-59.png)

> **classloader** loads the bytecode(xxx.class file) into the **method area** in JVM. Namely, the **code snippet** is stored in method area.
>
> static variables are initialised & stored in the method area when class is loaded.

## JVM: Heap

![](/static/2020-07-27-00-01-44.png)

> keyword *new* will allocate a space for **objects & its instance variables** in the heap memory

* reference 引用: the variable label points to a object address in heap， 内存中变量标签指向堆内存中的对象地址

## Some Tips of Compiling Process

![](/static/2020-07-26-23-55-04.png)

`javac *.java`

> Assume that there are two classes: **Student & StudentTest(utilising the Student class)**. If the compiler cannot find the Student class then it will **automatically compile it before compiling the Test class**.

## This

1. Cannot using in **static method**
2. Could be ommitted, but must be included when **recognizing local & instacne variables**
3. Implements the code re-using

## Method Override

什么时候满足方法覆盖(重写/覆写)?

* 满足继承关系，**对实例方法而言**
* **具有相同方法名，返回值类型，形式参数列表**
* 子方法访问权限**不能更低**(public>protected>default>private)
* 抛出异常不能更多

什么不能覆盖？

* 私有方法
* 静态方法不谈覆盖 - 无意义，团队合作容易混淆

方法重载 & 覆盖区别？

* 重载发生在同一个类中，覆盖发生在继承关系父子类中
* 重载
  * 方法名相同，**参数列表不同**
  * <font color="red">与返回值类型 & 修饰符无关</font>
* 覆盖
  * 继承父子类，重写后方法必须和之前方法一致【**方法签名 & 返回类型**】
  * 返回值类型：如多态**向下转型**则可以，不支持转大。<font color="red">建议完全复制</font>

## Upcasting & Downcasting

![](/static/2020-07-27-23-41-09.png)

**向上转型**

* 父类引用指向子类对象
* 子->父，自动类型转换

**向下转型**

* 父->子，强制类型转换
* 想要访问子类中特有的方法时，向下转型
* ![](/static/2020-07-27-23-57-07.png)

---

![](/static/2020-07-27-23-45-21.png)

### instanceof

![](/static/2020-07-28-00-00-57.png)

* 运行阶段动态判断引用指向的对象类型
* <font color="red">习惯：向下转型时，使用instanceof进行判断</font>

🍊 使用`instanceof`的重要性

![](/static/2020-07-28-00-13-24.png)

## Polymorphic

![](/static/2020-07-28-00-20-22.png)

方法覆盖需要和多态机制联合起来使用，才有意义

* 降低耦合度，提高扩展性
* OCP原则：扩展开放，修改关闭

## Super

当子类，父类有相同变量名，且想在子类中访问父类变量时，`super.`不能省略

## final

* 类，无法继承
* 方法，无法覆盖
* 变量，只能赋值一次
* 引用，一旦指向某个对象，则不能重新指向其他对象，但内部数据可以修改
* 实例变量，必须手动初始化，不能使用系统默认值
  * 通常和`static`使用，称为常量

## abstract & interface

抽象类

* **无法实例化，无法创建对象**，用来被子类继承
  * **可以有构造方法**，供子类使用
* `final`&`abstract`不能联合使用
* 子类可以是具体类，也可以是抽象类
* 可有**抽象&具体方法**
* **子类必须覆写所有抽象方法**

接口

* 引用数据类型，编译后也是class字节码文件
* 支持多继承，一个接口可以继承多个接口
* 包括常量，抽象方法
  * 所有元素访问修饰符`public`
  * 定义抽象方法，`public abstract`可忽略
  * 定义常量，`public static final`可忽略
* <font color="red">常用多态，接口引用实现类(父类引用指向子类对象)</font>

### difference

接口

* 接口之间支持多继承
* 没有构造方法
* 一个类可实现多个接口
* 常量 & 抽象方法

抽象类

* 类之间单继承
* 有构造方法
* 一个类只继承一个抽象类
  
🍊 接口使用的比抽象类多，一般为"行为"的抽象

## package & import

`package`防止命名冲突

```java
// 存储路径: net/java/util/Something.java
package net.java.util;
public class Something{
   ...
}
```

带有包名如何编译？ `javac -d . xxx.java`

* `java 完整类名`运行

---

`import`

什么时候不需要？ `java.lang`&同包不需要引入

* `import 完整类名`
* `import *`

## finalize

新JDK不建议使用

`protected void finalize() throws Throwable`

* JVM垃圾回收器负责调用【<font color="blue">GC自动调用</font>】
  * 当一个对象即将被回收时，调用
* <font color="red">提供在垃圾销毁时机，如需执行特定代码，则覆写`finalize()`方法</font>
* ![](/static/2020-07-28-15-04-17.png)

类似静态代码块，是一个类加载时机

```java
static{
  ...
}
```

* 在类加载时刻执行，并且只执行一次

### Example

```java
class Person{
  protected void finalize() throws Throwable{
    System.out.pritnln("即将被销毁：时间...");
  }
}
```

## anonymous inner class

**匿名内部类**

> 在类的内部又定义一个新的类. 能不用尽量不用
>
> * 可读性太差，“乱”
> * 类没有名字，无法复用

分类

* 静态内部类，类似静态变量
* 实例内部类，类似实例变量
* **局部内部类，类似局部变量**
  * **匿名内部类是局部内部类的一种，该类没有名字**

---

![](/static/2020-07-28-22-23-38.png)

`m.mySum(new Compute(){...这里实现接口的方法},100,200)`

* 其中`{}`部分指对该接口的实现

## Array

数组(元素地址连续)

🍬 不能存储大数据量

* 因为很难在内存空间中找到一块特别大的连续内存空间

![](/static/2020-07-28-23-13-27.png)

* 属于**引用数据类型**，父类`Object`
* 可以存放基本数据类型&引用数据类型
* 数组对象**在堆内存中**

### initialising

静态初始化

* `int[] array = {100,2100,300,55}`

动态初始化

* `int[] array = new int[5]//元素默认值为0`
* `String[] array = new String[5]//元素默认值为null`

### array(length fixed) extension

定长数组扩容

* 创建另外的大容量数组，将元素一个个拷贝进去
* <font color="red">效率低，需要提前预估</font>

### copy

`System.arraycopy(...)`

![](/static/2020-07-29-01-20-59.png)

* `src` - 源
* `srcPos` - 起始位置
* `dest` - 目标
* `destPos` - 目标位置
* `length` - 长度

![](/static/2020-07-29-01-42-44.png)
![](/static/2020-07-29-01-43-10.png)

## main method

`String[] args`

![](/static/2020-07-29-00-27-26.png)

* 接收控制台参数，自动转换为String数组(根据空格分离)

## String

### constructors

`String s1 = "hello world"`;

byte数组

```java
byte[] bytes = {97,98,99};
String s2= new String(bytes);
String s2= new String(bytes, 1, 2);//offset, length 截取byte数组
```

char数组

```java
char[] chars = {97,98,99};
String s2= new String(chars);
String s2= new String(chars, 1, 2);//offset, length 截取byte数组
```

### commonly used methods

1. `charAt`

   ![](/static/2020-07-31-12-37-20.png)

2. `compareTo`

   ![](/static/2020-07-31-12-46-55.png)
   ![](/static/2020-07-31-12-57-40.png)
  对字符串进行差值比较，按字典顺序

  * 需要实现`Comparable`接口
  * 封装类基本都改写了该方法
  * `0` - 相等
  * `<0` - 后大
  * `>0` - 后小

3. `contains`

   ![](/static/2020-07-31-13-01-40.png)

4. `endsWith` & `startWith`

   ![](/static/2020-07-31-13-07-21.png)
   * 判断字符串是否以某字符串结尾
   * 判断字符串是否以某字符串开头

5. `equalsIgonreCase`

   ![](/static/2020-07-31-14-15-44.png)

6. `getBytes`

  ![](/static/2020-07-31-14-26-27.png)
  ![](/static/2020-07-31-14-26-58.png)

   * **将字符串对象转换为字节数组**

7. `indexOf`

   ![](/static/2020-07-31-14-38-24.png)
   ![](/static/2020-07-31-14-39-29.png)

   * 判断某个子字符串在当前字符串中第一次出现的位置
   * 相对的，`lastIndexOf`

8. `length()`

   * 数组长度`length`属性
   * 字符串长度`length()`方法

9. `split`

  ![](/static/2020-07-31-15-18-58.png)

  * 以某字符串进行拆分，返回字符串数组

10. `substring`

  ![](/static/2020-07-31-15-25-46.png)

  * 字符串截取interception

11. `toCharArray`

    ![](/static/2020-07-31-15-30-31.png)

    * **返回`char[]`**

12. `toLowerCase` & `toUpperCase`

13. `trim`

    ![](/static/2020-07-31-15-33-32.png)

    * 去除字符串前后空白

14. `String.valueOf`

    ![](/static/2020-07-31-15-35-26.png)

    * `String`唯一的静态方法
    * 将非字符串，转换为字符串，返回`String`对象
      * 与`+""`字符串拼接相同
    * 如传入引用，则调用`toString`方法，未重写则返回内存地址
    * `println`源码调用`valueOf`方法

### StringBuffer(thread-safe)

🍊 常用于字符串拼接

java字符串不可变，每一次拼接产生新字符串，**占用大量方法区常量池内存**。推荐使用StringBuffer

StringBuffer底层是**byte[]数组（JDK8为char数组），初始化容量16**

![](/static/2020-07-31-17-57-00.png)

* `StringBuffer stringBuffer = new StringBffer();`
* 扩容&字符串拼接: `stringBuffer.append(...)`
    * ![](/static/2020-07-31-17-17-34.png)
* 如果byte数组满了会自动扩容
  * 建议初始化时，<font color="red">定义一个大容量StringBuffer数组，减少之后的扩容次数</font>
* append时会确定容量，调用`System.arraycopy`
* <font color="red">注意，StringBuffer方法由synchronized关键字修饰，线程安全</font>

### StringBuilder(thread-unsafe)

* <font color="red">注意，StringBuffer方法由synchronized关键字修饰，线程安全</font>StringBuilder没有，非线程安全

## Boxing & Unboxing

基本数据类型中数字类型，都继承抽象`Number`类，

![](/static/2020-07-31-20-47-29.png)

* 提供`xxxValue()`方法进行**拆箱**【引用数据类型->基本数据类型】
* **装箱**可以通过封装类的构造方法完成，将字面量常量传入
  * <font color="red">JAVA9不推荐 new Interger(int)</font>这种方法，java5已经支持自动拆装箱

---

🍊 自jdk1.5（5）后，支持自动拆装箱

* 用不到Number类的拆箱方法`xxxValue()`
* 可以不用构造方法装箱，但是引用类型保存的还是内存地址
  * `==`不触发拆箱机制
  * 且字面量超出byte范围时`-128,127`，不相等，此时引用指向堆内存对象地址。未超出时引用指向常量池地址。<font color="red">java提高效率，Integer类加载时候，提前将范围内对象创建在方法区整型常量池中</font>
  * ![](/static/2020-07-31-22-09-39.png)

## Integer

### commonly used method

🍊 静态方法

`Integer.valueOf(str/int)` 转换or装箱

`Integer.toBinaryString(...)`

* 将十进制转换成二进制字符串
* ![](/static/2020-07-31-23-02-20.png)
* 同理十进制转换成十六进制字符串`Integer.toHexString(...)`
  * `Object.toString`调用该方法![](/static/2020-07-31-23-06-07.png)
* 同理十进制转换成八进制字符串`Integer.toOctalString(...)`

## Date

`java.util`包，对日期处理