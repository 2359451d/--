# JDK9 New Features

study note

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
  - [String存储结构变更](#string存储结构变更)

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

## String存储结构变更

之前String底层是`char[]`数组实现

* jdk9改为`byte[]`数组
* 