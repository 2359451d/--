# Content

* [Content](#content)
* [概述](#概述)
* [spring模块](#spring模块)
* [IOC(inversion of control)](#iocinversion-of-control)
  * [DI（Dependency Injection）依赖注入思想](#didependency-injection依赖注入思想)
* [注册 & 取出组件对象](#注册--取出组件对象)
  * [Spring配置文件注册组件](#spring配置文件注册组件)
  * [根据ID取出组件](#根据id取出组件)
  * [IOC容器配置文件路径](#ioc容器配置文件路径)

# 概述

> Spring 是一个 **IOC** ( DI) 和 **AOP** 容器框架。

是一个可以管理所有组件（类）的容器框架

特性

![](/static/2021-07-11-18-24-25.png)

# spring模块

黑色为组成模块的jar包

![](/static/2021-07-11-18-39-55.png)
![](/static/2021-07-15-14-50-26.png)

* Test - Spring单元测试模块
* Core Container - **核心容器IOC**
  * 由4个jar包组成，要使用完整的IOC功能，需要导入这些jar
* AOP + Aspects - **面向切面编程模块**
* Data Access/Integration - **数据访问 & 集成 - spring数据库访问模块**
  * 数据
    * JDBC
    * TX（事务）
    * ORM
  * 集成
    * OXM
    * JMS
* web -
 **spring开发web应用模块**
  * web-x.0.0.RELEASE,
  * 原生web相关（servlet）
  * 开发web项目的webmvc
  * 开发web应用的组件集成 portlet

**建议：用哪个模块导哪个包**

* 装插件
* maven
* 手动导

# IOC(inversion of control)

控制反转

* 控制资源获取方式
  * 主动
    * 需要的资源得自己创建 （复杂对象的创建复杂）
  * **被动（接收资源**)
    * **资源的获取不是开发者自己创建，而是容器来创建 & 设置**
      * 容器：管理所有组件（有功能的类），**容器可以自动探查哪些组件（类）需要另一组件（类**）。容器会自动帮我们创建需要用到的资源

## DI（Dependency Injection）依赖注入思想

> 容器能知道哪个组件运行时所需要的依赖（其他组件）
>
> **容器通过反射形式，将准备好的资源注入（利用反射给属性赋值）这个需要依赖的组件**

只要被容器管理的组件，都能使用容器提供的功能

# 注册 & 取出组件对象

现在，IOC容器给我们创建对象，我们先再容器中注册组件(配置文件中)

以后框架编写流程

* 导包
* 写配置
* 测试

## Spring配置文件注册组件

容器中注册的对象一启动就创建好了（不需要等到get）

* `<bean>`标签注册
* `property`标签填属性

且，**同一个组件在IOC容器中是单实例的**

* 但，如果容器中没有注册这个组件，尝试获取就报错

**容器property根据getter，setter来创建组件对象，进行赋值**

<font color="red">getter/setter才是`javaBean`的属性名</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--bean标签注册组件（对象，类）-->
    <bean id="p1" class="top.bento.bean.Person">
        <constructor-arg index="0" value=""/>
        <constructor-arg index="1" value=""/>
        <property name="name" value="haha"></property>
        <property name="age" value="18"></property>
    </bean>

</beans>
```

## 根据ID取出组件

```java
public void test() {
        //  ApplicationContext代表IOC容器
        // ClassPathXmlApplicationContext:当前应用的xml配置文件在classPath下
        // 根据配置文件得到ioc容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
        Person bean = (Person)ioc.getBean("p1");
        System.out.println(bean);
        //
    }
```

## IOC容器配置文件路径

`ApplicationContext`IOC容器

注意：

* src 源码包开始的路径都会在**类路径**下 /bin
* web项目会在 /WEB-INF/classes 里面（作为类路径）

IOC容器配置文件在类路径下

* `ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");`

IOC容器配置文件在磁盘路径下

* `FileSystemXmlApplicationContext("path")`
