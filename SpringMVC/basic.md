# Content

源码部分跳过了，，0经验没能力看 :face_with_head_bandage:

* [Content](#content)
* [简介](#简介)
* [HelloWorld](#helloworld)
  * [配置](#配置)
    * [spring框架自身配置](#spring框架自身配置)
  * [创建controller处理派发请求](#创建controller处理派发请求)
  * [配视图解析器](#配视图解析器)
* [运行流程概述](#运行流程概述)
* [RequestMapping](#requestmapping)
  * [类上注解](#类上注解)
  * [RequestMapping支持Ant路径风格](#requestmapping支持ant路径风格)
  * [@PathVariable - 获取路径占位符](#pathvariable---获取路径占位符)
* [/ & /* 区别](#---区别)
  * [DefaultServlet](#defaultservlet)
* [不指定配置文件位置](#不指定配置文件位置)
* [REST风格](#rest风格)
* [HiddenHttpMethodFilter](#hiddenhttpmethodfilter)
  * [配置Filter](#配置filter)
  * [发起请求](#发起请求)
  * [源码](#源码)
* [请求参数获取](#请求参数获取)
  * [@RequestParam](#requestparam)
  * [@RequestHeader](#requestheader)
  * [@CookieValue](#cookievalue)
* [请求参数处理 - POJO](#请求参数处理---pojo)
* [支持Servlet原生API作为入参](#支持servlet原生api作为入参)
* [请求数据乱码](#请求数据乱码)
* [============](#)
* [响应数据输出](#响应数据输出)
  * [Map，Model，ModelMap](#mapmodelmodelmap)
  * [ModelAndView](#modelandview)
  * [@SessionAttributes](#sessionattributes)
  * [@ModelAttribute](#modelattribute)
    * [POJO](#pojo)
    * [与SessionAttribute产生的异常](#与sessionattribute产生的异常)
    * [源码](#源码-1)
* [底层 - DispatcherServlet](#底层---dispatcherservlet)
* [源码 - 请求大致流程](#源码---请求大致流程)
  * [getHandler（）](#gethandler)
  * [HandlerAdapter](#handleradapter)
* [9大组件](#9大组件)
  * [目标方法执行](#目标方法执行)
* [视图解析](#视图解析)
  * [forward:/](#forward)
  * [redirect:/](#redirect)
  * [（源码）原理](#源码原理)
  * [JstlView - 国际化](#jstlview---国际化)
    * [坑](#坑)
  * [mvc:view-controller](#mvcview-controller)
  * [自定义视图解析器 & 视图](#自定义视图解析器--视图)
* [============](#-1)
* [数据转换，数据格式化，数据校验](#数据转换数据格式化数据校验)
* [自定义类型转换器](#自定义类型转换器)
* [mvc:annotation-driven](#mvcannotation-driven)
  * [3种配置情况](#3种配置情况)
* [数据格式化](#数据格式化)
* [如何获取格式化错误信息](#如何获取格式化错误信息)
* [数据校验](#数据校验)
  * [校验流程](#校验流程)
  * [自定义国际化错误信息](#自定义国际化错误信息)
  * [message属性指定错误信息](#message属性指定错误信息)
* [JSON & Ajax](#json--ajax)
  * [返回json数据 - @ResponseBody](#返回json数据---responsebody)
  * [ajax获取所有员工例子](#ajax获取所有员工例子)
  * [RequestBody](#requestbody)
  * [发送json](#发送json)
* [请求头 - HttpEntity](#请求头---httpentity)
* [ResponseEntity](#responseentity)
* [自定义消息转换器 - HttpMessageConverter](#自定义消息转换器---httpmessageconverter)
* [文件下载 - ResponseEntity](#文件下载---responseentity)
* [文件上传](#文件上传)
  * [多文件上传](#多文件上传)
* [============](#-2)
* [拦截器](#拦截器)
  * [使用](#使用)
  * [**](#-3)
* [Filter vs 拦截器](#filter-vs-拦截器)
* [============](#-4)
* [国际化](#国际化)
  * [区域信息](#区域信息)
  * [获取国际化信息](#获取国际化信息)
  * [自定义区域解析器 - 通过超链接切换Locale](#自定义区域解析器---通过超链接切换locale)
  * [SessionLocalResolver](#sessionlocalresolver)
    * [配合拦截器使用](#配合拦截器使用)
* [============](#-5)
* [异常处理](#异常处理)
  * [](#-6)

# 简介

spring简化web开发

![](/static/2021-07-26-19-13-36.png)
![](/static/2021-07-26-19-15-23.png)

* springweb模块称为SpringMVC

---

以前MVC模式

![](/static/2021-07-26-19-17-30.png)
![](/static/2021-07-26-19-17-37.png)

SpringMVC模式

![](/static/2021-07-26-19-20-50.png)

* 多了个**前端控制器**（请求转给控制器，响应回来时数据模型派发给指定的页面，再进行页面渲染

# HelloWorld

导包，配置，测试

## 配置

**web.xml配置 & 框架本身配置**

配前端控制器拦截所有请求，并实现智能派发

* 前端控制器（spring提供） - 本身servlet，所以在web.xml中完成配置
  * 注意指定**springmvc配置文件路径**（类路径下）

```xml
   <servlet>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <servlet-name>springDispatcherServlet</servlet-name>

        <!-- 初始化时加载配置文件 -->
        <init-param>
            <!-- 指定spring配置文件位置 -->
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>

        <!-- 容器在启动时立即加载Servlet(原本是第一次访问创建对象)
            值越小，优先级越高，越先创建对象
            -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springDispatcherServlet</servlet-name>
<!--        /* 和 / - 拦截所有请求，
            /* 范围更大：还会拦截到*.jsp页面请求，一旦拦截jsp页面就无法显示
            / - 不会拦jsp
            -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### spring框架自身配置

spring配置文件

* 包扫描，域名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--    扫描所有组件-->
    <context:component-scan base-package="top.bento"></context:component-scan>

</beans>
```

创建jsp页面

## 创建controller处理派发请求

以前路径的处理要配置servlet路径(urlpattern)，现在可以直接定义Controller组件，加注解实现

* `@Controller`
* `@RequestMapping`

这种未改进方法返回的路径太长了 ，可以配一个视图解析器

```java
/*
 * description: 告诉springmvc这是个处理器，处理请求
 * @ Controller:标识控制器
 * @param null
 * @return
 */
@Controller
public class MyFirstController {
    /**
     * 从当前项目下开始，处理当前项目下的hello请求
     */
    @RequestMapping("/hello")
    public String firstGet() {
        System.out.println("processing...");
        return "/WEB-INF/pages/sources.jsp";
    }
}
```

## 配视图解析器

简化请求处理方法的返回值（长路由路径）

* `InternalResourceViewResolver`
  * `suffix`
  * `preffix`

```xml
<!--    视图解析器，拼接路由-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```

```java
    @RequestMapping("/hello")
    public String firstGet() {
        System.out.println("processing...");
        //配置视图解析器
        return "sources";
    }
```

# 运行流程概述

![](/static/2021-07-28-21-50-01.png)

1. HTTPGET请求
2. mvc前端控制器收到所有请求（`/`)
3. 来看请求地址和 `@RequestMapping`标注的方法哪个匹配
4. 找到目标处理器&类，反射执行目标方法
5. 方法执行完毕，返回值 - 转发页面路径
6. 获取返回值， 视图解析器拼接url
7. 前端控制器转发请求至页面

# RequestMapping

告诉springmvc，方法用来处理什么请求

* `/`可以省略，即使省略也是从当前项目下开始（webapps下项目名）

:bulb: 属性

* **填写的路径默认是 `value`属性**
  * URL路径上可以有占位符（可变参）
* `method`
  * **限定请求方式，默认所有请求**
  * `RequestMethod`枚举类
    * GET，HEAD，POST，PUT...
* `params`
  * 规定请求参数（**查询参数 query string**)
  * 如`params={"username"}`，请求时必须带username这个参数
  * 如`params={"username=123"}`，请求时必须带username=123（值）这个参数
  * 如`params={"!username"}`，请求时**不能带**username这个参数
  * **支持多规则，用`,`隔开**
* `headers`
  * 规定请求头
  * 如 `User-Agent`客户端访问信息
  * ![](/static/2021-07-28-21-55-19.png)
* `consumes`
  * 只接收内容类型是哪种的请求，相当于请求头的 `Content-Type`
* `produce`
  * 返回的内容类型，响应头 `Content-Type`

---

![](/static/2021-07-28-21-33-21.png)

## 类上注解

为当前控制器类的所有方法的请求地址指定一个基准路径

![](/static/2021-07-28-21-35-44.png)

* 必须在`/haha`下访问到handle01

## RequestMapping支持Ant路径风格

![](/static/2021-07-28-22-07-04.png)

* 模糊匹配
* RequestMapping也支持Ant风格的url

![](/static/2021-07-28-22-16-16.png)

* `?` - 任意一个字符
* `*` - 多个字符 或 单层路径
* `**` - 多层路径

:bulb: 模糊和精确多个匹配情况下，精确优先

## @PathVariable - 获取路径占位符

通过 @PathVariable 可以将 **URL 中占位符参数（value属性中的路径**）绑定到控制器处理方法的**入参（形参**）中

* 占位符只能占一层路径

![](/static/2021-07-29-13-26-41.png)

# / & /* 区别

原本`/`拦截所有请求，**不包括jsp页面**（`*.jsp`)

* 此时 `*.jsp`<font color="blue">因为不被拦截交给tomcat的`JspServlet`处理</font>
* 而其他请求（如`*.html`静态资源），原本交给`DefaultServlet`，<font color="deeppink">但是如果子模块覆写url-pattern为`/`，会禁用掉`DefaultServlet`，转而交给前端控制器来处理</font>
  * 如果不存在方法能处理这个静态资源，那么就返回404

:bulb: 为什么jsp可以访问？

* 因为如果前端控制器配`/`，**首先不会拦截jsp请求**，而是会被tomcat服务器的`JspServlet`拦截到进行处理

---

`/*`**拦截所有请求**（包括`*.jsp`），拦截jsp页面，

* 所以如果前端控制器配`/*`，会把jsp请求也拦截到，而如果找不到对应controller就报错

## DefaultServlet

tomcat中处理静态资源的（除jsp之外）

# 不指定配置文件位置

web.xml中配springmvc前端控制器时，不指定配置文件位置

![](/static/2021-07-28-20-45-12.png)

* 默认找 `WEB-INF/<dispatcher_servlet_name>-servlet.xml`
  * 匹配配置文件中指定的name，`servlet-name`标签

:bulb: 不想指定位置就可以直接在WEB-INF文件夹下创建 `xxx-servlet.xml`配置文件

# REST风格

![](/static/2021-07-29-13-39-20.png)

![](/static/2021-07-29-13-43-06.png)

* 风格统一的设计
* 以简洁url提交请求，以请求方式区分对资源的操作

:bulb: 问题

* 默认form表单只支持GET，POST请求，
* 需要配置spring3.0支持的过滤器`HiddenHttpMethodFilter`，可以将请求转换为标准http方法，使其支持其他HTTP请求

:bulb: 注意高版本tomcat不支持PUT，DELTE这种方法，

* jsp头加 `isErrorPage=True`

# HiddenHttpMethodFilter

:bulb: 问题

* 默认form表单只支持GET，POST请求，
* 需要配置spring3.0支持的过滤器`HiddenHttpMethodFilter`，可以将请求转换为标准http方法，使其支持其他HTTP请求

---

## 配置Filter

web.xml中配置spring提供的filter

```xml
<!--    support RESTful-->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

## 发起请求

发 送 POST 请求 时携带 一个 `name="_method"` 的 隐含域`type=hidden` ， 值 为 PUT 或 DELETE

![](/static/2021-07-29-15-26-07.png)
![](/static/2021-07-29-15-26-16.png)

---

controller指定method

![](/static/2021-07-29-15-27-00.png)

## 源码

![](/static/2021-07-29-15-28-45.png)
![](/static/2021-07-29-15-31-28.png)

# 请求参数获取

默认方式获取请求（查询）参数 `xxx?username=admin`

![](/static/2021-07-29-15-42-58.png)

* 直接给方法入参上写一个和请求参数名`username`相同的变量

---

## @RequestParam

`@RequestParam`

* 显式获取请求参数
* `value`
  * 参数名
* `required`
  * 默认情况下，请求参数必须包含对应参数，不存在将抛出异常
* `defaultValue`
  * 默认值，没有传递参数时使用该值

![](/static/2021-07-29-15-43-45.png)
![](/static/2021-07-29-15-44-49.png)
![](/static/2021-07-29-15-47-51.png)

## @RequestHeader

获取请求头信息

![](/static/2021-07-29-15-50-40.png)
![](/static/2021-07-29-15-50-50.png)
![](/static/2021-07-29-15-54-26.png)

## @CookieValue

以前获取cookie，麻烦

![](/static/2021-07-29-15-57-01.png)

---

获取请求中cookie值

* `value`
* `required`
* `defaultValue`

![](/static/2021-07-29-15-57-54.png)
![](/static/2021-07-29-15-58-10.png)

# 请求参数处理 - POJO

如果请求参数是一个POJO，springmvc支持自动填充

* 将POJO属性，一一与请求参数配对
* **支持级联封装**
  * ![](/static/2021-07-29-16-06-26.png)
  * ![](/static/2021-07-29-16-07-56.png)

---

POJO类 （有参无参）
![](/static/2021-07-29-16-02-55.png)

表单指定的请求参数名要对应的上
![](/static/2021-07-29-16-04-08.png)

实现自动填充
![](/static/2021-07-29-16-03-45.png)

# 支持Servlet原生API作为入参

![](/static/2021-07-29-16-22-34.png)
![](/static/2021-07-29-16-25-43.png)

* 能用的基本前3个

---

例子

![](/static/2021-07-29-16-23-14.png)
![](/static/2021-07-29-16-23-34.png)

* 利用原生API的域对象，在页面中渲染数据

# 请求数据乱码

![](/static/2021-07-29-16-30-16.png)
![](/static/2021-07-29-16-30-24.png)

建议

![](/static/2021-07-29-16-33-20.png)

* 一般配置在任何其他Filter之前

# ============

# 响应数据输出

如何将数据带给页面

* 原本是通过原生API向域对象里添加数据，然后EL表达式在页面中取出数据

---

spirngmvc可以通过以下方式输出数据

![](/static/2021-07-29-19-48-31.png)

## Map，Model，ModelMap

:bone: Map，Model，ModelMap，这些参数中保存的所有数据都会放在**请求域**`RequestScope`中，可以在页面中获取

* 不管用的哪个，最终都是转为`BindingAwareModelMap`，**相当于`BindingAwareModelMap`保存的数据都会放在请求域中**
  * 推荐使用`Map`，**方便框架移植**
* 关系
  * ![](/static/2021-07-29-19-57-04.png)
  * ![](/static/2021-07-29-19-58-32.png)
* 使用
  * ![](/static/2021-07-29-19-59-27.png)
  * ![](/static/2021-07-29-20-02-34.png)

## ModelAndView

方法返回值可以转为ModelAndView类型

![](/static/2021-07-29-20-03-50.png)
![](/static/2021-07-29-20-06-06.png)

* 视图名 - 页面
  * 视图解析器会帮我们最终拼串得到页面真实地址
* 数据放在请求域中`RequestScope`

## @SessionAttributes

给session域添加数据 （推荐不要使用这个注解，可能会引发异常（结合ModelAttribute使用下），使用原生API就够了）

* 类（控制器）上注解
* 属性
  * `value`
    * 如果用Map/Model/ModelMap给`BindingAwareModelMap`中指定保存的数据，其`key`符合`value`属性指定的值，同时会给session中保存一份
  * `types`
    * 只要数据符合指定的类型，就也在session域中放一份

![](/static/2021-07-29-20-18-03.png)

## @ModelAttribute

以前没有mybatis会用到

:question: 问题 - 场景，全字段修改演示（一般都不会进行全字段更新，，）

* 一般可以显示不需要修改的字段（但不支持用户输入）
* 而如果为了简单，springmvc中入参直接写一个POJO对象，如果某属性映射不到，会自动注入`null`。**如果之后调用了一个全字段更新的DAO操作，那么这个`null`值会被注入进原本不该修改的字段**
  * ![](/static/2021-07-29-20-57-07.png)

:bone: 解决方案 - 思想

* 如果为`null`，就从数据库中取出的对象值，带了的字段的就改为携带的值，此时全字段更新无问题
  * 即，不应该采用springmvc自己new出来的对象，而应该用数据库中取出来的对象，再进行封装

---

`@ModelAttribute`

* 注解位置
  * 方法 - 在执行方法之前运行
    * 可以提前查出数据库中对象信息。**然后在更新方法中指明使用这个查出来的对象，而不是自己new的（这样解决了之前POJO封装在全字段更新场景下产生的问题，因为之前的不用ModelAttribute，会自己封装new的一个对象，有值的带值，没值的为null**）
    * ![](/static/2021-07-29-21-28-41.png)
    * ![](/static/2021-07-29-21-28-30.png)
  * 入参前
    * 获取域对象中存储的数据
* 原理
  * ![](/static/2021-07-29-21-36-19.png)
  * ![](/static/2021-07-29-21-36-26.png)
  * `BindingAwareModelMap`request域数据共享容器

### POJO

![](/static/2021-07-30-17-25-46.png)

可以提前查出数据库中对象信息。**然后在更新方法中指明使用这个查出来的对象，而不是自己new的（这样解决了之前POJO封装在全字段更新场景下产生的问题，因为之前的不用ModelAttribute，会自己封装new的一个对象，有值的带值，没值的为null**）

先MOdelAttribute方法上，提前执行，取数据，然后通过入参ModelAttribute封装POJO，数据根据key（ModelAttribute注解指定）-value(POJO对象)存入请求域对象

---

其他用法 （**推荐只用map**）

![](/static/2021-07-30-17-37-32.png)
![](/static/2021-07-30-17-37-01.png)

* **不写入参前ModelAttribute注解也可以，但是请求域中key名称要对应的上入参变量名**

将数据return也会放在请求域，默认key为返回类型名小写

![](/static/2021-07-30-17-41-57.png)

最后还取不到就会自己新建一个对象，可能会出现null值

### 与SessionAttribute产生的异常

注意，如果入参前标了ModelAttribute且类上标了SessionAttribute会尝试在会话域中查询，不存在会抛异常

:bulb:

* 要么隐含模型中有SessionAttribute标注的属性
* 如果没有，session说有就必须有，不然会抛异常 - 建议不用SessionAttribute

### 源码

现阶段没能力看

![](/static/2021-07-30-17-35-04.png)

# 底层 - DispatcherServlet

前端控制器

![](/static/2021-07-30-15-25-25.png) 

# 源码 - 请求大致流程

DispatcherServlet的doDispatch方法，[参考](https://blog.csdn.net/qq_40353040/article/details/109445941)

* ![](/static/2021-07-30-16-15-14.png)

![](/static/2021-07-30-15-34-33.png)
![](/static/2021-07-30-15-41-45.png)
![](/static/2021-07-30-15-43-18.png)
![](/static/2021-07-30-15-44-36.png)

## getHandler（）

怎么根据当前请求找到能处理的目标处理器类

`getHandler()`

* 返回目标处理器类的执行链

![](/static/2021-07-30-15-56-25.png)

## HandlerAdapter

如何找到目标处理器的目标方法  ---适配器

![](/static/2021-07-30-16-11-30.png)

# 9大组件

DispatcherServlet中9个属性 --- Springmvc9大组件（全是接口）

* ![](/static/2021-07-30-16-20-22.png)
* ![](/static/2021-07-30-16-22-17.png)
* ![](/static/2021-07-30-16-23-06.png)

---

初始化细节

![](/static/2021-07-30-16-26-03.png)

* 9大组件何时有值？
* 初始化
  * 去容器中找组件，如果没有找到就用默认配置
  * 有些组件在容器中使用ID找，有的按类型找

## 目标方法执行

关键：如何确定目标方法每一个参数的值

![](/static/2021-07-30-16-53-16.png)

# 视图解析

## forward:/

表示转发到**当前项目**下一个页面，不会走视图解析器进行拼串

* 一定要加上`/`不然很容易出问题
* 有前缀的转发&重定向操作，配置的视图解析器不会进行拼串

![](/static/2021-07-31-16-44-22.png)

## redirect:/

![](/static/2021-07-31-16-46-50.png)

* 一定要加上`/`不然很容易出问题
* 有前缀的转发&重定向操作，配置的视图解析器不会进行拼串

## （源码）原理

![](/static/2021-07-31-17-19-38.png)
![](/static/2021-07-31-17-23-04.png)

* 视图解析器根据方法返回值得到视图对象
* 多个视图解析器都会尝试是否能得到视图对象
* 视图对象不同就可以具有不同功能

`mv.handle()` 任何方法值最终被封装成ModelAndView

![](/static/2021-07-31-17-00-26.png)
![](/static/2021-07-31-17-10-39.png)

`view.render()`

视图解析器为了得到View对象，而View对象才能真正的转发（模型数据放在请求域中）、重定向到页面，，即**渲染视图**

## JstlView - 国际化

spring中bean标签配，或者导包

或者指定property - viewClass

![](/static/2021-07-31-17-33-00.png)

---

![](/static/2021-07-31-17-35-46.png)
![](/static/2021-07-31-17-38-22.png)

1. spring中配置国际化资源管理器
   1. ![](/static/2021-07-31-17-37-43.png)
2. 页面中直接取值
   1. ![](/static/2021-07-31-17-39-04.png)

### 坑

![](/static/2021-07-31-17-44-04.png)

要获取到国际化渲染必须走springmvc，，因为mvc里配了管理器，直接访问路径下的首先没有控制器能处理，走的是tomcat直接返回

且不能通过forward:,redirect，这两个不会封装国际化信息

![](/static/2021-07-31-17-47-26.png)
![](/static/2021-07-31-17-50-38.png)

## mvc:view-controller

![](/static/2021-07-31-17-50-38.png)

* 上面虽然可行，但是为了访问一个页面就要写一个方法后期冗余
* 如果发送的请求不想通过controller，只想直接地跳转到目标页面，这时候就可以使用mvc:view-controller标签
  * 对应一些我们不需要其他操作的JSP页面，我们可以使用<mvc:view-controller path=""/>来配置，这样就可以不用再控制器中再去做转发映射，从而减轻我们的部分开发工作量
  * **在SpringMVC中有时候针对一个请求不做任何处理，仅仅只是返回一个视图**
* 使用`view-controller`会走整个springmvc流程，**视图解析，国际化**。。。
  * <font color="deeppink">注意要开启注解驱动的mvc模式，不然会造成其他方法无法处理请求</font> - `mvc:annotation-driven`

![](/static/2021-07-31-17-53-30.png)
![](/static/2021-07-31-17-57-02.png)

## 自定义视图解析器 & 视图

自定义视图解析器实现`ViewResolver`接口，实现方法根据**视图名（包括前缀**）返回视图对象。**完成后在springmvc（ioc容器）中配置**

![](/static/2021-07-31-18-32-53.png)
![](/static/2021-07-31-18-46-35.png)
![](/static/2021-07-31-18-33-29.png)
![](/static/2021-07-31-18-33-43.png)

* 遍历所有视图解析器，看看哪个视图解析器能处理视图名
  * ![](/static/2021-07-31-18-35-05.png)![](/static/2021-07-31-18-36-33.png)发现Internalxxxx解析器不能处理，但是会创建一个普通视图渲染至视图名路径（造成404），**所以要配置优先度**

自定义视图解析器实现`Ordered`接口，确保能保证执行优先级（越小越优先）

![](/static/2021-07-31-18-38-11.png)
![](/static/2021-07-31-18-38-20.png)
![](/static/2021-07-31-18-39-21.png)

* 之后发现自定义视图解析器是第一个能处理该视图名的，返回自定义的视图对象

自定义视图对象自定义渲染逻辑`render()`

![](/static/2021-07-31-18-41-12.png)
![](/static/2021-07-31-18-44-38.png)
![](/static/2021-07-31-18-42-51.png)

* 注意要设置响应类型（字符编码过滤器才能发挥作用）

# ============

# 数据转换，数据格式化，数据校验

javaBean和页面提交的数据进行一一绑定，牵扯到以下操作

* 数据绑定期间的数据类型转换
* 数据绑定期间的数据格式化问题
* 数据校验，数据合法
  * 前端校验（不要依赖）
  * 后端校验（重要数据）
    * 校验成功
    * 校验失败

`WebDataBinder`负责数据绑定工作

* `ConversionService`组件 - 数据类型转换 & 格式化
* `validators`数据校验
* `bindingResult`保存&解析数据绑定期间产生的校验错误信息

![](/static/2021-08-01-16-25-29.png)

---

数据绑定流程

![](/static/2021-08-01-16-30-37.png)
![](/static/2021-08-01-16-31-12.png)

# 自定义类型转换器

提取分析请求数据，然后封装成对象？

![](/static/2021-08-01-16-36-47.png)

原本不存在能处理这个对象封装的类型转换器

![](/static/2021-08-01-16-41-17.png)

---

自定义类型转换器`ConversionService`，进行类型转换&格式化

* 实现`Converter<S,T>`接口
  * `convert`方法

![](/static/2021-08-01-16-48-26.png)
![](/static/2021-08-01-16-49-01.png)

`Converter`是`ConversionService`的组件，需要放进去，spring配置文件中配置自定义的converter，**加入默认类型转换器的集合`converters`中（原来的不会被覆盖**）

![](/static/2021-08-01-16-58-07.png)

告诉springmvc使用自己修改后的`ConversionService`类型转换组件

![](/static/2021-08-01-17-02-55.png)

# mvc:annotation-driven

:bulb:何时配置

* 直接返回视图响应的请求，不走控制器，`mvc:view-controller`，注意配置`mvc:annotation-driven`不然其他控制器用不了
* CRUD删除时，静态资源（js）无法访问到，要配置`mvc:annotaiton-driven` & `mvc:default-servlet-handler`
  * **会在springmvc上下文中定义一个`DefaultServletHttpRequestHandler`，对进入`DispatcherServlet`的请求进行筛查，如果发现是未经映射的请求，交给WEB服务器（tomcat）默认的`DefaultServlet`处理。如果不是静态资源，才继续由`DispatcherServlet`前端控制器处理**
  * 原本/拦截所有请求，不包括jsp页面（\*.jsp)，其他请求（如\*.html静态资源），原本交给DefaultServlet，但是如果子模块覆写url-pattern为/，会禁用掉DefaultServlet，转而交给前端控制器来处理
    * 如果不存在方法能处理这个静态资源，那么就返回404
* 配置类型转换器
  * 指定需要用到的类型转换器
  * `mvc:annotation-driven conversion-service=""`
* JSR303数据校验

## 3种配置情况

![](/static/2021-08-01-17-47-49.png)

![](/static/2021-08-01-17-48-04.png)
![](/static/2021-08-01-17-53-26.png)

![](/static/2021-08-01-17-48-40.png)

---

![](/static/2021-08-01-17-49-15.png)

# 数据格式化

页面提交的数据格式不正确 - 400

可用注解

* `DataTimeFormat(pattern="")`
* `NumberFormat(style=, pattern=)`
  * ![](/static/2021-08-01-18-32-22.png)

改配置

![](/static/2021-08-01-18-30-37.png)
![](/static/2021-08-01-18-33-05.png)

# 如何获取格式化错误信息

![](/static/2021-08-01-18-34-59.png)

`BindingResult`

# 数据校验

只有前端校验 - 不安全，重要的数据一定要进行后端验证

* 可以通过逻辑将数据取出进行校验，失败就提示用户重新提交 :-1:
* **springmvc可以用JSR303规范做数据校验**
  * Hibernate Validator 第三方校验框架等

JSR303规范 - bean上标注解

![](/static/2021-08-01-18-41-37.png)
![](/static/2021-08-01-18-42-02.png)
![](/static/2021-08-01-18-42-55.png)

## 校验流程

1.导包 （如使用Hibernate Validator

![](/static/2021-08-01-18-44-13.png)

* 带EL的包不要加入，tomcat7.0以上自带el表达式比较强大

2.Bean属性上标校验注解

![](/static/2021-08-01-18-46-38.png)

3.`@Valid`指定Bean封装时走校验规则

![](/static/2021-08-01-18-48-45.png)

4.校验结果

* 需要校验的javaBean后面紧跟一个`BindingResult`

![](/static/2021-08-01-18-50-49.png)

5.根据校验结果编写逻辑 & spring表单错误信息展示

![](/static/2021-08-01-18-52-11.png)
![](/static/2021-08-01-18-54-57.png)

:bulb: 原生表单如何展示错误信息？

* 错误信息放在隐含请求域模型中
* ![](/static/2021-08-01-18-58-32.png)
* ![](/static/2021-08-01-18-58-59.png)

## 自定义国际化错误信息

可以用`message`属性指定错误信息。。。（不支持国际化）

Hibernate Validator已实现国际化信息

1.编写国际化文件信息

* xxxCN.properties
* xxxUS.properties
* **key有规定 - 每一个字段发生错误以后都有自己的错误代码；key必须对应一个错误代码**
  * ![](/static/2021-08-01-19-06-18.png)
  * ![](/static/2021-08-01-19-11-29.png)

配置文件例子（key为错误代码）

![](/static/2021-08-01-19-07-17.png)
![](/static/2021-08-01-19-07-28.png)
![](/static/2021-08-01-19-11-58.png)

---

2.springmvc管理国际化资源文件 `ResourceBundleMessageSource`

![](/static/2021-08-01-19-10-18.png)

---

:bulb: 细节

* 动态传入消息参数
* ![](/static/2021-08-01-19-14-53.png)
* ![](/static/2021-08-01-19-15-32.png)

## message属性指定错误信息

![](/static/2021-08-01-19-17-37.png)

# JSON & Ajax

## 返回json数据 - @ResponseBody

流程

1.导包(jakson)

![](/static/ 2021-08-01-19-20-35.png)

2.目标方法添加`@ResponseBody`注解

* `@JsonIgnore`注解指定不参与序列化的字段
  * ![](/static/2021-08-01-19-24-42.png)
* `@JsonFormat`格式化
  * ![](/static/2021-08-01-19-25-56.png)

![](/static/2021-08-01-19-22-32.png)

3.测试

![](/static/2021-08-01-19-23-36.png)

## ajax获取所有员工例子

![](/static/2021-08-01-19-30-53.png)

## RequestBody

获取请求体，接收json数据，or反序列化（自动封装）

![](/static/2021-08-01-20-54-29.png)
![](/static/2021-08-01-20-55-31.png)

## 发送json

![](/static/2021-08-01-20-26-42.png)
![](/static/2021-08-01-20-26-48.png)

# 请求头 - HttpEntity

![](/static/2021-08-01-20-37-46.png)
![](/static/2021-08-01-20-56-33.png)

# ResponseEntity

能返回响应体&头&状态码

![](/static/2021-08-01-20-52-04.png)
![](/static/2021-08-01-20-56-33.png)

# 自定义消息转换器 - HttpMessageConverter

![](/static/2021-08-0zidingy59-49.png)

将请求信息转为对象，或对象转为响应信息

![](/static/2021-08-01-21-58-12.png)

* 序列化，反序列化

---

![](/static/2021-08-01-22-18-39.png)

# 文件下载 - ResponseEntity

![](/static/2021-08-01-20-57-04.png)
![](/static/2021-08-01-20-57-18.png)

# 文件上传

1.文件上传表单准备，`enctype="multipart/form-data"`

![](/static/2021-08-01-22-30-28.png)

2.导入fileupload

3.springmvc中配置文件上传解析器MultipartResolver
![](/static/2021-08-01-22-34-49.png)

4.文件上传请求处理，处理器上入参标注`@RequestParam("headerimg")MultipartFile file` 封装当前文件信息，可以直接保存

* `getName()`文件项名字
* `getOriginalName()`文件名字
* `transferTo()`保存

![](/static/2021-08-01-22-33-23.png)

## 多文件上传

![](/static/2021-08-01-22-37-34.png)
![](/static/2021-08-01-22-38-19.png)

# ============

# 拦截器

允许运行目标方法之前进行一些拦截工作，或者目标方法运行之后进行一些其他处理

![](/static/2021-08-02-14-50-51.png)

* `preHandle`
  * 目标方法运行之前调用，返回`boolean`
    * `true` - `chain.doFilter()`放行
    * `false` - 不放行
* `postHandle`
  * 目标方法运行之后调用
* `afterCompletion`
  * 整个请求完成之后；
  * 来到目标页面之后；
  * `chain.doFilter()`放行；资源响应之后

:bulb: 正常流程

* preHandle - 目标方法 - postHandle - 页面 - afterCompletion
* ![](/static/2021-08-02-15-16-16.png)

:bulb: 其他流程

* 只要preHandle不放行后面就不执行
* 只要放行了，afterCompletion都会执行

:bulb: 多个拦截器

* ![](/static/2021-08-02-15-27-00.png)
  * ![](/static/2021-08-02-15-17-36.png)
* ![](/static/2021-08-02-15-27-45.png)
  * ![](/static/2021-08-02-15-19-37.png)

---

## 使用

1.实现`HandlerInterceptor`

![](/static/2021-08-02-14-59-03.png)

2.spring中注册拦截器 `mvc:interceptors`，

* 默认拦截所有请求 ![](/static/2021-08-02-15-01-21.png)
* `mvc:interceptor`具体配置某个拦截器
  * ![](/static/2021-08-02-15-02-12.png)

## **

执行失败一定是false，只不过之前会执行一下AfterCompletion

只要有一个拦截器返回false，就走不到适配器执行目标方法那行，直接跳到AfterCompletion

如果目标方法正常执行，postHandle执行，最后页面渲染

* 异常直接跳到AfterCompletion

页面渲染正常或失败，都执行AfterCompletion

![](/static/2021-08-02-15-36-39.png)

# Filter vs 拦截器

拦截器更强大，且在容器中

如果某些功能，需要其他组件配合完成，使用拦截器（因为可以@AutoWired自动装配）

# ============

# 国际化

1.写好国际化配置文件

![](/static/2021-08-02-15-49-29.png)
![](/static/2021-08-02-15-49-38.png)

2.声明国际化资源文件

![](/static/2021-08-02-15-51-37.png)

3.控制器

![](/static/2021-08-02-15-49-08.png)

4.页面国际化

![](/static/2021-08-02-15-52-52.png)
![](/static/2021-08-02-15-52-34.png)

## 区域信息

国际化区域信息决定国际化显示的因素

* 区域信息由区域信息解析器`LocaleResolver`得到

`AcceptheaderLocalResolver`使用请求头区域信息

`FixedLocaleResolver`使用系统默认区域信息

`SessionLocaleResolver`获取session域中区域信息。可以根据请求参数创建一个local对象，放在session域中

`CookieLocalResolver`

## 获取国际化信息

![](/static/2021-08-02-16-07-07.png)
![](/static/2021-08-02-16-07-47.png)

## 自定义区域解析器 - 通过超链接切换Locale

实现`LocaleResolver`接口

![](/static/2021-08-02-16-19-01.png)
![](/static/2021-08-02-16-16-16.png)

## SessionLocalResolver

![](/static/2021-08-02-16-35-24.png)
![](/static/2021-08-02-16-40-28.png)
![](/static/2021-08-02-16-39-39.png)
![](/static/2021-08-02-16-39-45.png)

### 配合拦截器使用

![](/static/2021-08-02-16-41-37.png)
![](/static/2021-08-02-16-43-19.png)

* 通过拦截器帮忙获取区域信息

# ============

# 异常处理

![](/static/2021-08-02-16-49-45.png)

![](/static/2021-08-02-16-55-41.png)
![](/static/2021-08-02-17-00-02.png)

如果异常解析器都不能处理某个异常，就抛出去了

异常没人处理，就到默认的处理 `DefaultHandlerExceptionResolver`

## @ExceptionptionHanler

告诉spring标注了这个注解的方法，专门处理这个类发生的异常

* `value` - 指定异常类型
  * ![](/static/2021-08-02-17-02-46.png)
* 如果存在多个能处理异常的方法，精确的优先

---

方法入参要携带异常信息只能带`Exception`，但可以通过返回`ModelAndView`获取信息

![](/static/2021-08-02-17-06-23.png)
![](/static/2021-08-02-17-06-36.png)

## 全局异常处理类

异常处理类要放入ioc容器中

`@ControllerAdvice`

![](/static/2021-08-02-17-11-59.png)

* 本类&全局异常处理同时存在，本类优先

## @ResponseStatus

* `value`
  * 状态码
* `reason`
  * string

标在自定义异常上

![](/static/2021-08-02-17-25-56.png)
![](/static/2021-08-02-17-26-27.png)

## SimpleMappingExceptionHandle

优先级比较低

![](/static/2021-08-02-18-07-10.png)

![](/static/2021-08-02-18-10-01.png)
![](/static/2021-08-02-18-14-47.png)