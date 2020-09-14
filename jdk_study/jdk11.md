# JDK11 New Features

study note

- [JDK11 New Features](#jdk11-new-features)
  - [Prologue](#prologue)
  - [String: 新增处理方法](#string-新增处理方法)
  - [Optional: 增强](#optional-增强)
  - [局部变量推断升级](#局部变量推断升级)
  - [HTTPClient API](#httpclient-api)
  - [更简化的编译运行程序](#更简化的编译运行程序)
  - [废弃Nashorn引擎](#废弃nashorn引擎)
  - [ZGC(*)](#zgc)
  - [其他新特性](#其他新特性)
  - [JDK11为止看不到什么](#jdk11为止看不到什么)

## Prologue

![](/static/2020-09-13-16-00-19.png)

## String: 新增处理方法

![](/static/2020-09-13-16-03-34.png)

- **判断字符串是否为空白**
  - `public boolean isBlank()`
- **去除首尾空白**
  - `public String strip()`
- **去除尾部空格**
  - `public String stripTrailing()`
- **去除首部空格**
  - `public String stripLeading()`
- **复制字符串**
  - `public String repeat(int count)`
- **行数统计**
  - `public Stream<String> lines()`，**之后再对Stream调用`count()`统计行数**

```java
public static void main(String[] args) {
    System.out.println("\t\t\n".isBlank());//check whether str is blank
    System.out.println("----"+"\tabc\n\t".strip()+"***");//remove the space of head & tail
    System.out.println("----"+"\tabc\n\t".stripTrailing()+"***");//remove the space of tail
    System.out.println("----" + "\tabc\n\t".stripLeading() + "***");//remove the space of head

    //repeat
    String str = "abc";
    System.out.println(str.repeat(3));

    //count the lines
    String s = "deg\na\nc";
    System.out.println(s.lines().count());
}

/*
OUTPUT:

true
----abc***
----	abc***
----abc
	***
abcabcabc
3
*/
```

## Optional: 增强

![](/static/2020-09-13-16-19-39.png)

- JDK9
  - 快速转换Optional至Stream`Stream<T> stream()`
  - value非空返回。**否则返回形参封装的`or(Supplier)`**
  -  **value非空执行参数1功能，接收参数无返回**。**否则执行2功能无参无返回**`ifPresentOrElse(Consumer, Runnable)`
- JDK10
  - value非空返回。**否则抛异常`NoSuchElementException`**`orElseThrow()`
- JDK11
  - 判断value是否为空`isEmpty()`
  - **与JDK8的`isPresent()`正好相反**

```java
public static void main(String[] args) {
    Optional<String> s = Optional.empty();
    Optional<String> op1 = Optional.of("hello2");
    Optional<String> or = s.or(() -> op1);
    System.out.println(or);
}
```

## 局部变量推断升级

var上添加注解jdk10不支持

- jdk11支持
  - 可以实现lambda表达式参数添加注解

```java
// 错误形式：必须有类型，可以添加var
// Consumer<String> con1 = (@Deprecated t)->System.out.println(t.toUpperCase());

// jdk11增强var，可以为lambda表达式添加注解
Consumer<String> con2 = (@Deprecated var t)->System.out.println(t.toUpperCase());
```

## HTTPClient API

> 这是 Java 9 开始引入的一个处理 HTTP 请求的的 HTTP Client API，该 API 支持同步和异步，而在 Java 11 中已经为正式可用状态，你可以在 java.net 包中找到这个 API

- 替换原有的HttpURLConnection

```java
var request = HttpRequest.newBuilder()
    .uri(URI.create("https://javastack.cn"))
    .GET()
    .build();
var client = HttpClient.newHttpClient();

// 同步
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());

// 异步
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println);
```

## 更简化的编译运行程序

增强java启动器

- **支持运行单个java源码文件的程序**
- 之前分两步
  - `javac javastack.java`编译
  - `java javastack`运行
- **jdk11，可以直接运行**
  - `java javastack.java`编译&运行

🍊 要求

- 执行源文件中，**第一个类必须包含主方法**
  - **不可以使用其他源文件中的自定义类**，本文件中的自定义类要求是可以自由使用

## 废弃Nashorn引擎

> 废除Nashorn javascript引擎，在后续版本准备移除掉，有需要的可以考虑使用GraalVM

## ZGC(*)

![](/static/2020-09-14-16-39-00.png)

ZGC优势

![](/static/2020-09-14-16-39-54.png)

## 其他新特性

![](/static/2020-09-14-16-40-46.png)

## JDK11为止看不到什么

![](/static/2020-09-14-16-42-07.png)
![](/static/2020-09-14-16-43-30.png)