# Content

* [Content](#content)
* [c++ vs java](#c-vs-java)
* [实例变量 vs 局部var: instance var vs local var](#实例变量-vs-局部var-instance-var-vs-local-var)
* [封装](#封装)
* [JIT](#jit)
* [copy constructor](#copy-constructor)
* [final](#final)
* [final, finally, finalize](#final-finally-finalize)
* [super访问父类重写方法/隐藏字段](#super访问父类重写方法隐藏字段)
* [子不会覆盖父static方法](#子不会覆盖父static方法)
* [static var/methond vs static class](#static-varmethond-vs-static-class)
* [ClassLoader](#classloader)
* [引用/浅/深拷贝](#引用浅深拷贝)
* [为什么String设计为不可变的](#为什么string设计为不可变的)
* [线程安全类：thread safe classes](#线程安全类thread-safe-classes)
* [String, StringBuilder, StringBuffer](#string-stringbuilder-stringbuffer)
* [抽象类vs接口](#抽象类vs接口)
* [HashSet vs TreeSet](#hashset-vs-treeset)
* [char[] vs String安全性](#char-vs-string安全性)
* [HashMap vs HashTable](#hashmap-vs-hashtable)
* [反射](#反射)
* [构造函数 vs 普通方法](#构造函数-vs-普通方法)
* [IS-A关系](#is-a关系)
* [异常传播链：exception propagate chain](#异常传播链exception-propagate-chain)
* [ArrayList, array连续存储](#arraylist-array连续存储)
* [为什么arr索引以0开头](#为什么arr索引以0开头)
* [链表删除vs数组删除](#链表删除vs数组删除)
* [List接口add(), addAll()](#list接口add-addall)
* [简述ArrayList扩容](#简述arraylist扩容)
* [继承vs组合：inheritance vs composition](#继承vs组合inheritance-vs-composition)
* [组合vs聚合：Composition vs aggregation](#组合vs聚合composition-vs-aggregation)
* [String创建](#string创建)
* [new(), newInstance()](#new-newinstance)
* [线程周期：thread life cycle](#线程周期thread-life-cycle)
* [标记接口](#标记接口)
* [匿名类方法初始化集合](#匿名类方法初始化集合)
* [依赖注入：inject dependency](#依赖注入inject-dependency)
* [Spring Bean范围](#spring-bean范围)
* [====================](#)
* [(*) Volatile vs transient关键字](#-volatile-vs-transient关键字)
* [====================](#-1)
* [====================](#-2)

# c++ vs java

![](/static/2022-09-14-12-13-38.png)

# 实例变量 vs 局部var: instance var vs local var

![](/static/2022-09-14-12-28-01.png)

# 封装

![](/static/2022-09-14-13-26-02.png)

* 对外部隐藏内部细节，实体看为一个整体
* 通过访问修饰符来辅助实现( 限定访问权限)

# JIT

![](/static/2022-09-14-13-29-07.png)
![](/static/2022-09-14-13-31-51.png)

* jit interpreter, compiler

# copy constructor

![](/static/2022-09-14-13-56-27.png)

* `.clone`浅拷贝

# final

![](/static/2022-09-14-14-16-01.png)

# final, finally, finalize

![](/static/2022-09-14-14-20-30.png)

# super访问父类重写方法/隐藏字段

![](/static/2022-09-14-14-32-26.png)

# 子不会覆盖父static方法

静态方法只是被隐藏，，私有不能被覆盖，，成员变量是不会被覆盖（怎么样还是父类的）

![](/static/2022-09-14-14-51-55.png)
![](/static/2022-09-14-14-52-33.png)

# static var/methond vs static class

![](/static/2022-09-14-14-54-14.png)

# ClassLoader

* 配合JVM那章

# 引用/浅/深拷贝

![](/static/2022-09-14-15-04-07.png)
![](/static/2022-09-14-15-05-19.png)

# 为什么String设计为不可变的

![](/static/2022-09-14-15-12-00.png)
![](/static/2022-09-14-15-13-44.png)
![](/static/2022-09-14-15-16-20.png)

* 底层final char[]
* 安全考虑
* 促进常量池实现(共享，减少临时String)
* 减少复杂并发过程。
* 促进集合安全，

# 线程安全类：thread safe classes

![](/static/2022-09-14-15-19-19.png)

# String, StringBuilder, StringBuffer

![](/static/2022-09-14-15-29-55.png)
![](/static/2022-09-14-15-31-24.png)

# 抽象类vs接口

![](/static/2022-09-14-15-34-50.png)
![](/static/2022-09-14-11-12-34.png)

# HashSet vs TreeSet

![](/static/2022-09-14-15-49-15.png)

# char[] vs String安全性

![](/static/2022-09-14-15-51-41.png)

# HashMap vs HashTable

![](/static/2022-09-14-16-02-07.png)

![](/static/2022-09-14-16-07-25.png)

# 反射

![](/static/2022-09-14-16-09-48.png)
![](/static/2022-09-14-16-10-44.png)

# 构造函数 vs 普通方法

![](/static/2022-09-14-16-52-41.png)

# IS-A关系

![](/static/2022-09-14-16-56-54.png)

# 异常传播链：exception propagate chain

![](/static/2022-09-14-19-17-14.png)

# ArrayList, array连续存储

![](/static/2022-09-14-19-40-06.png)

# 为什么arr索引以0开头

![](/static/2022-09-14-19-43-50.png)

# 链表删除vs数组删除

![](/static/2022-09-14-19-45-13.png)

# List接口add(), addAll()

不重要

![](/static/2022-09-14-19-46-35.png)

# 简述ArrayList扩容

![](/static/2022-09-14-19-48-59.png)
![](/static/2022-09-14-19-49-25.png)
![](/static/2022-09-14-19-49-56.png)

---

删除

![](/static/2022-09-14-19-50-36.png)

# 继承vs组合：inheritance vs composition

![](/static/2022-09-14-19-55-48.png)

# 组合vs聚合：Composition vs aggregation

![](/static/2022-09-14-20-00-14.png)

* 弱关联 weak association
* 强关联 strong association

# String创建

![](/static/2022-09-14-20-03-04.png)
![](/static/2022-09-14-20-03-53.png)

# new(), newInstance()

反射创建，，会抛出类定义不存在异常

# 线程周期：thread life cycle

![](/static/2022-09-14-21-17-44.png)

# 标记接口

![](/static/2022-09-14-21-38-23.png)
比如RandomAccess

# 匿名类方法初始化集合

![](/static/2022-09-14-21-39-53.png)

# 依赖注入：inject dependency

![](/static/2022-09-14-21-44-39.png)

# Spring Bean范围

![](/static/2022-09-14-21-45-24.png)

# ====================

# (*) Volatile vs transient关键字

![](/static/2022-09-15-15-04-40.png)

# ====================
# ====================