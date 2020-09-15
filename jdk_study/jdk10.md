# JDK10 New Features

study note

- [JDK10 New Features](#jdk10-new-features)
  - [特性](#特性)
  - [局部变量类型推断](#局部变量类型推断)
    - [原理](#原理)
    - [不适用的情况](#不适用的情况)
  - [jdk10: 新增创建不可变集合(只读)](#jdk10-新增创建不可变集合只读)

## 特性

![](/static/2020-09-12-18-29-49.png)
![](/static/2020-09-12-18-31-28.png)

## 局部变量类型推断

![](/static/2020-09-12-18-33-08.png)
![](/static/2020-09-12-18-35-39.png)

- 认为`LinkedHashSet<Integer>xxx = new LinkedHashSet<>();`左边声明有点多余
- 声明类型包括了很长的泛型，有些多余

🍊 类型推断使用方式

- 类型替代成`var`

```java
//声明变量时，根据所附的值，推断变量的类型
var num = 10;
var list = new ArrayList<Integer>();//右边泛型不添加，默认看作Object集合
list.add(123);
```

```java
// 遍历操作
var list = new ArrayList<Integer>();//右边泛型不添加，默认看作Object集合
list.add(123);
for (var i:list){
  System.out.println(i);
}

// 普通遍历操作
for(var i=0;i<100;i++){
  System.out.println(i);
}
```

### 原理

![](/static/2020-09-12-19-14-44.png)

- `var`不是一个关键字，仅仅是一个标识符(类型)
  - **不能作为类名**，其他都可以
- 这不是js
  - `var`不会改变java是**静态类型语言**的事实

### 不适用的情况

![](/static/2020-09-12-18-50-20.png)
![](/static/2020-09-12-19-03-33.png)

- lambda表达式
- 初始值为null
  - `var a = null;`不可以
- 方法引用
- 数组静态初始化
  - `var arr = new int[]{1,2,3}`可以
  - `var arr = {1,2,3}`不可以
- 没有右边初始化的局部变量声明
  - `var arr;`不可以
- 方法返回类型
- 方法参数类型
- 构造器参数类型
- 属性
- catch块

## jdk10: 新增创建不可变集合(只读)

jdk9新增`接口名.of()`方法创建不可变集合

- 支持`List/Set/Map`

🍊 <font color="red">jdk10新增`copyOf()`方法，快速创建只读集合</font>

![](/static/2020-09-12-19-21-50.png)

- 如果**集合本身就是只读的**，调用`copyOf()`就返回**一个一样的引用指向同一个集合**
- 如果**集合本身可修改的**，调用`copyOf()`会在**堆中新建一个拷贝的集合**