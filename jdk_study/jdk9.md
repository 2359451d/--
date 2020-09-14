# JDK9 New Features

study note

![](/static/2020-09-14-16-21-47.png)

- [JDK9 New Features](#jdk9-new-features)
  - [模块化系统: Jigsaw -> modularity](#模块化系统-jigsaw---modularity)
    - [模块化实现目标](#模块化实现目标)
    - [模块化特性使用](#模块化特性使用)
  - [REPL工具：JShell](#repl工具jshell)
  - [多版本兼容JAR包](#多版本兼容jar包)
    - [使用步骤](#使用步骤)
  - [语法改进：接口的私有方法](#语法改进接口的私有方法)
    - [使用例子](#使用例子)
    - [抽象类 vs 接口](#抽象类-vs-接口)
  - [语法改进：钻石操作符升级](#语法改进钻石操作符升级)
  - [语法改进：tryresource升级](#语法改进tryresource升级)
  - [语法改进：UnderScore下划线的使用限制](#语法改进underscore下划线的使用限制)
  - [String存储结构变更: byte[]](#string存储结构变更-byte)
    - [扩展：StringBuffer & StringBuilder](#扩展stringbuffer--stringbuilder)
  - [集合工厂方法：快速创建只读集合](#集合工厂方法快速创建只读集合)
    - [jdk9快速创建只读集合](#jdk9快速创建只读集合)
  - [增强的Stream API](#增强的stream-api)
    - [takeWhile()](#takewhile)
    - [dropWhile()](#dropwhile)
    - [Stream.ofNullable()](#streamofnullable)
    - [Stream.iterate()重载](#streamiterate重载)
    - [Optional: stream()](#optional-stream)
  - [多分辨率图像API](#多分辨率图像api)
  - [全新HTTP客户端API](#全新http客户端api)
    - [使用例子](#使用例子-1)
  - [Deprecated相关API](#deprecated相关api)
  - [JAVA智能编译工具: sjavac](#java智能编译工具-sjavac)
  - [统一的JVM日志系统](#统一的jvm日志系统)
  - [javadoc对H5的支持](#javadoc对h5的支持)
  - [js引擎升级：Nashorn](#js引擎升级nashorn)
  - [java动态编译器](#java动态编译器)
  - [Summary](#summary)
    - [jdk9看不到什么](#jdk9看不到什么)
    - [展望](#展望)

## 模块化系统: Jigsaw -> modularity

🍊 jdk9`jigsaw`项目(后期更名为`Modularity`)**产生背景**

![](/static/2020-09-11-14-04-25.png)
![](/static/2020-09-11-14-05-25.png)
![](/static/2020-09-11-14-07-56.png)

* **java运行环境的膨胀&臃肿**
  * 整个jar都会被加载至内存，
  * **模块化：实现划分，按需加载**
* **类库交叉依赖，阻碍开发效率**
* 公共类被误访问
* 类路径问题：是否会有重复项？

🍬 module本质

* <font color="red">package外再包装一层</font>
  * **一个module可以有多个package**
* **用于管理各个package**
  * 默认不声明的package为隐藏，<font color="blue">可以声明某个package暴露用于使用或给其他module使用</font>

### 模块化实现目标

![](/static/2020-09-11-14-18-21.png)

* **减少内存开销**
* **划分，按需使用必要模块，而非全部jdk模块**
  * 简化类库&应用的开发
* JAVASE**平台适应性**，适应不同大小的计算设备
* 改进**安全性&可维护性**

### 模块化特性使用

同一project下，两个自定义module的使用

* 为两个module创建`module-info.java`文件
  * 希望被使用模块的文件中，写入`exports <package_name>`![](/static/2020-09-11-14-24-48.png)
  * **希望导入模块的文件中，写入**`requires<module_name>`![](/static/2020-09-11-14-26-43.png)
* 最后希望导模块使用的`java`文件中，写入`import`信息
  * 导入module中包下`class`![](/static/2020-09-11-14-36-59.png)

🍊 使用定义好的module

![](/static/2020-09-11-14-43-04.png)

## REPL工具：JShell

> `REPL`: read-evaluate-print-loop，交互式编程环境

产生背景

![](/static/2020-09-11-14-47-09.png)

**默认导入的包**

![](/static/2020-09-11-14-51-38.png)

🍊 `tab`可补全

🍊 一些命令

![](/static/2020-09-11-14-53-27.png)

* `/edit <method_name>`
  * 会弹出编辑框，供修改
  * ![](/static/2020-09-11-14-54-13.png)
* `/edit`
  * 弹出编辑框，修改先前定义的所有东西![](/static/2020-09-11-14-55-01.png)
* `/open <path>`
  * 打开外部java文件
* <font color="red">jshell后台会隐藏编译时期异常(checked excpetion受检异常)</font>
  * ![](/static/2020-09-11-14-59-51.png)
* `/exit`
  * 退出jshell

## 多版本兼容JAR包

支持新的jdk情况下，旧版本API得到保护

> By this scheme, it is possible for versions of a calss designed for a later java platform release to **override** the version of that **same class** designed for an earlier java platform release.

![](/static/2020-09-11-15-26-37.png)

### 使用步骤

1. 提供必要的类
   1. 模拟不同版本jdk，不同版本的类
   2. 希望实现不同版本jdk下，运行版本不同的相同类
2. 代码打包
   1. ![](/static/2020-09-11-16-12-08.png)
   2. `javac -d build --release 8 src/main/java/com/atguigu/*.java `进行编译
   3. `javac -d build9 --release 9 src/main/java-9/com/atguigu/*.java`进行编译
   4. `jar --create --main-class=Application --file multijar.jar -C build . --release 9 -C build9 .`生成jar包
3. 添加jar为library - `Add as library`
   1. ![](/static/2020-09-11-16-17-45.png)
   2. 此时，不同jdk环境运行的相同类名功能运行结果不同

## 语法改进：接口的私有方法

jdk7

* 支持全局常量`public static final`
* 抽象方法`public abstract`
* 修饰符为`public`

jdk8增强接口

* 支持默认&静态接口方法
* 修饰符为`public`

jdk9，支持`private`接口

- 此时方法**不会成为对外暴露的API的一部分**
  - 如排序方法中的`swap()`，不想对外暴露，可以进行封装

### 使用例子

```java
interface MyInterface{
    void normalInterface();

    private void int(){
        System.out.println("通用操作");
    }
}

class Test implements MyInterface{
    @Override
    public void normalInterface(){
        System.out.println("实现接口的方法");
    }
}

public class MyInterfaceTest{
    public static void main(String[] args){
        Test test = new Test();
        test.normalInterface();
        // test.int(); //无法调用，因为为private方法
    }
}

```

### 抽象类 vs 接口

不同版本jdk角度

* 声明方式
  * `class`声明
  * `interface`声明
* 内部结构
  * 抽象类
    * 抽象方法
    * 具体方法
    * 构造函数
  * 接口
    * jdk7
      * 抽象方法
      * 全局常量
    * jdk8
      * 抽象方法
      * 全局常量
      * 默认方法
      * 静态方法
    * jdk9
      * 抽象方法
      * 全局常量
      * 默认方法
      * 静态方法
      * 私有方法
* 共同点
  * 都不能实例化
  * 以多态方式使用
* 抽象类：单继承，接口：多实现

## 语法改进：钻石操作符升级

diamond operator，jdk8及之前**无法使用于匿名类**，使用意义为**类型推断**

![](/static/2020-09-11-16-51-55.png)

* 此例，创建一个继承了`HashSet`的匿名(子)类对象

🍊 jdk9使用

![](/static/2020-09-11-16-59-07.png)

## 语法改进：tryresource升级

jdk8之前，习惯完整的try...catch结构关闭资源

![](/static/2020-09-11-17-21-16.png)

jdk8实现tryresource语法，实现资源的自动关闭

![](/static/2020-09-11-19-52-25.png)

- 资源必须在try表达中初始化，否则抛受控异常

🍊 jdk9，可以在try子句中使用已经初始化过的资源

![](/static/2020-09-11-19-54-02.png)

- 此时资源为`final`，不可更改

## 语法改进：UnderScore下划线的使用限制

有关标识符

jdk8中，标识符可以独立用`_`来命名

```java
String _ = "hello";
System.out.println(_);
```

🍊 **jdk9不支持**使用`_`单独命名标识符，如果使用会报错

## String存储结构变更: byte[]

之前String底层是`char[]`数组实现

- <font color="red">jdk9改为`byte[]`数组</font>
  - 因为大多string对象仅有一个latin字符，只占用1B内存，而char单位为2B
- 加上编码标记`encoding flag`，节约了一些空间

```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence{
    @Stable
    private final byte[] value;
}
```

### 扩展：StringBuffer & StringBuilder

![](/static/2020-09-11-22-11-10.png)

🍊 区别

- `String`
  - 不可变的字符序列
- `StringBuffer`
  - 可变的字符序列
  - **线程安全，效率低**
- `StringBuilder`(jdk5)
  - 可变的字符序列
  - **非线程安全，效率高**

## 集合工厂方法：快速创建只读集合

🍊 普通地、如何创建一个**只读，不可改变的集合？**

- 需要**构造&分配`new`**，然后**添加元素`put/add`**，最后**包装成一个不可修改的集合`Collections.unmodifiableXXX()`**
  - 写法多余

```java
List<String> namesList = new ArrayList <>();
namesList.add("Joe");
namesList.add("Bob");
namesList.add("Bill");

namesList = Collections.unmodifiableList(namesList);//将集合变为只读的
System.out.println(namesList);
```

稍微简化的处理

```java
List<String> list = Collections.unmodifiableList(Arrays.asList("a","b","c"));
Set<String> set = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("a","b","c")));
```

jdk9增强钻石表达式对匿名类操作后的写法

```java
//匿名HashMap对象
Map<String,Integer> map  = Collections.unmodifiableMap(new HashMap<>(){
    //以下为向该匿名类中添加的代码块
    {
        put("a",1);
        put("b",2);
        put("c",3);
    }
});

map.forEach((k,v)->System.out.println(k+":"+v));
```

### jdk9快速创建只读集合

🍊 jdk9支持**接口的静态方法`of()`**

- 可以将不同数量的参数传输到此工厂方法中
- 支持`List`&`Set`&`Map`
  - 此时得到的集合是**不可变的(只读)**
  - 尝试添加会抛出`UnsupportedOperationException`异常

```java
//接口的静态方法
List<Integer> list = List.of(1,2,3);
Set<String> set = Set.of("a", "b", "c");

Map<String, Integer> map1 = Map.of("Tom", 12, "Jerry", 21,         "Lilei", 33, "HanMeimei", 18);

Map<String, Integer> map2 = Map.ofEntries(
    Map.entry("Tom", 89),
    Map.entry("Jim", 78),
    Map.entry("Tim", 98)
);
```

## 增强的Stream API

Stream接口添加了**4个新方法**

- `dropWhile`
- `takeWhile`
- `ofNullable`
- `iterate`新重载方法，提供`Predicate`决定何时结束迭代

🍊 Optional & Stream之间的结合得到改进

- 可以通过`Optional`新方法`stream()`
  - 将一个`Optional`对象转换为一个`Stream`对象，**允许为空**

### takeWhile()

从stream中获取一部分数据，接收`Predicate`进行选择

- 在有序的Stream中，**返回从开头开始尽量多的元素**
- 遇到**第一个不符合条件的，就结束流操作**
  - <font color="red">与filter不同，filter会返回所有符合条件的元素</font>

```java
List<Integer> list = Arrays.asList(45,43,76,87,42,77,90,73,67,88);
list.stream()
    .takeWhile(x -> x < 50)
    .forEach(System.out::println);
```

### dropWhile()

与`takeWhile()`相反，

![](/static/2020-09-12-15-16-14.png)

- 返回**剩余的元素**

### Stream.ofNullable()

jdk8的**stream不能完全为`null`**，否则报空指针异常

- `ofNullable()`允许创建单元素stream，
  - 可**包含非空元素**
  - 也可以创建**一个空Stream**

```java
//不允许单元素为null的情况
//Stream.of(null);

Stream.of("aa","bb",null);//3
List.of("AA",null).stream().count();//2

//ofNullable()：允许值为null
Stream.ofNullable(null).count();//count: 0
Stream.ofNullable("hello world").count();//count: 1
```

### Stream.iterate()重载

🍊 复习: Stream对象创建&实例化

- 通过集合的`stream()`方法
- 通过数组工具类`Arrays`
- Stream静态方法`Stream.of()`

~~🍬 提供一个新的获取stream对象的方法~~

~~- `iterator()`~~

~~- `generate()`~~

🍬 iterate()重载，添加`Predicate`接口参数

- 用于**控制终止迭代条件**
- `public static<T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)`

```java
//原来的控制终止方式
//迭代器，生成1~max的前10个数字的流并打印
Stream.iterate(1, i->i+1).limit(10)
    .forEach(System.out::println);

//现在的终止方式
Stream.iterate(1,i->i<100,i->i+1)
    .forEach(System.out::println);
```

### Optional: stream()

`Optional`类新增`stream()`方法

- 可完成Optional类到Stream对象的转换
- `public Stream<T> stream()`

```java
List<String> list = List.of("Tom","Jerry","Tim");
Optional<List<String>> optional = Optional.ofNullable(list);

Stream<List<String>> stream = optional.stream();//optional转stream
Stream.flatMap(x->x.stream())
    .forEach(System.out::println);
```

## 多分辨率图像API

产生背景

![](/static/2020-09-12-16-24-05.png)

使用说明

![](/static/2020-09-12-16-28-13.png)

- 自动实现同一张图像的多分辨率变体
  - 适配不同平台的图像显示效果

## 全新HTTP客户端API

HTTP/1.1和HTTP/2基本区别

![](/static/2020-09-12-16-31-18.png)

jdk9的全新httpAPI

![](/static/2020-09-12-16-32-10.png)

- `HttpClient`全新支持`WebSocket`&`HTTP/2`
  - 用于替代`HttpURLConnection`

### 使用例子

```java
HttpClient client = HttpClient.newHttpClient();

HttpRequest req = HttpRequest.newBuilder(URI.create("xxx.com"))
    .GET()
    .build();
HttpResponse<String> response = client.send(req,HttpResponse.BodyHandler.asString());

System.out.println(response.statusCode());
System.out.println(response.version().name());
System.out.println(response.body());
```

## Deprecated相关API

![](/static/2020-09-12-17-41-56.png)

- `Applet API`
- `appleviewer`

## JAVA智能编译工具: sjavac

![](/static/2020-09-12-17-44-46.png)

1. sjavac提升javac工具，以便作为jdk默认编译工具
2. jdk9更新javac工具，以便运行java9代码于低版本java中

## 统一的JVM日志系统

![](/static/2020-09-12-18-00-39.png)

- 日志是解决问题的有效途径
- 但是vm的**日志碎片化**和日志选项，很难对VM进行调试

🍊 解决方法

- 对VM组件全引入进一个单一系统，**实现统一**

## javadoc对H5的支持

![](/static/2020-09-12-18-04-46.png)

## js引擎升级：Nashorn

![](/static/2020-09-12-18-07-12.png)

- 之前使用Rhino项目，为js提供运行环境
  - 执行效率较低
- jdk9提供轻量级，更高效的js运行时
  - Nashorn项目

## java动态编译器

![](/static/2020-09-12-18-10-48.png)

- 解决启动慢，吃内存问题

![](/static/2020-09-12-18-13-34.png)

## Summary

### jdk9看不到什么

![](/static/2020-09-12-18-14-31.png)

---

![](/static/2020-09-12-18-16-24.png)
![](/static/2020-09-12-18-16-37.png)

### 展望

![](/static/2020-09-12-18-17-51.png)
![](/static/2020-09-12-18-18-02.png)