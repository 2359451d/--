针对System&Scanner类的一些记录，[参考](https://www.cnblogs.com/yinzhengjie/p/8887145.html)

## System

> **System**类不能直接初始化

1. 获取系统当前毫秒值

   `public static long currentTimeMillis()`
   > Returns the current time in milliseconds.

   可用于测试程序执行时间等

2. 终止当前运行的java程序(终止JVM)

    `public static void exit(int status)`
    > Terminates the currently running JVM.

    参数传入`0`为正常状态，其他为异常

3. 标准输入流

    `public static final InputStream in`
    > The standard input stream.This stream is already open and ready to supply input data. Typically this stream corresponds to keyboard input or another input source specified by the host environment or user.

    支持键盘输入(逐字节)or其他输入/可读源(如文本文件)

    `BufferedReader br = new BufferedReader(new InputStreamReader(System.in));`

    * 可配合`readLine()`使用

## Scanner

[API参考](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/util/Scanner.html)

### 快速示例

```java
/*
读取用户输入的数字
allow user to read a number from System.in
*/
Scanner sc = new Scanner(System.in);
int i = sc.nextInt();
```

```java
/*
读取文件中long类型数据
allow long types to be assgined from entries in a file
*/
Scanner sc = new Scanner(new File("myNumbers"));
while(sc.hasNextLong()){
    long aLong = sc.nextLong();
}

```java
/*
使用除空格外分隔符的示例
use delimiters other than whitespace
*/

String input = "1 fish 2 fish red fish blue fish";
Scanner s = new Scanner(input).useDelimiter("\\s*fish\\s*");
System.out.println(s.nextInt());
System.out.println(s.nextInt());
System.out.println(s.next());
System.out.println(s.next());
s.close();
```

![](/static/2020-07-14-23-17-56.png)

* 正则表达式同样示例

### 说明

1. `Scanner`默认分隔符通过`Character.isWhitespce()`静态方法分辨

   * `scanner.reset()`方法可重置scanner分隔符至**默认(空格)**设定

2. `next()`&`hasNext()`&`nextInt()`&`hasNextInt()`等方法跳过匹配分隔符模式的第一个输入，返回下一个token

   * `hasNext()`&`next()`&`tokens()`会阻塞等待用户输入