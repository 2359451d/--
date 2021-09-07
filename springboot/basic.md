# Content

* [Content](#content)
* [Spring缺点](#spring缺点)
* [Springboot功能](#springboot功能)
* [快速搭建](#快速搭建)
  * [IDEA快速构建](#idea快速构建)
* [SpringBoot 起步依赖原理分析](#springboot-起步依赖原理分析)
* [SpringBoot 配置](#springboot-配置)
  * [配置文件分类](#配置文件分类)
  * [yaml](#yaml)
  * [读取配置文件内容](#读取配置文件内容)
  * [Profile](#profile)
  * [内部配置加载顺序](#内部配置加载顺序)
  * [外部配置加载顺序](#外部配置加载顺序)
* [SpringBoot整合Junit](#springboot整合junit)
* [SpringBoot整合Redis](#springboot整合redis)
* [SpringBoot整合MyBatis](#springboot整合mybatis)
* [SpringBoot 自动配置](#springboot-自动配置)
  * [Condition](#condition)
  * [Condition案例](#condition案例)
  * [切换内置web服务器](#切换内置web服务器)
  * [@Enable*注解](#enable注解)
  * [@Import注解](#import注解)
  * [@EnableAutoConfiguration 注解](#enableautoconfiguration-注解)
  * [自定义starter](#自定义starter)
* [SpringBoot 监听机制](#springboot-监听机制)
  * [ApplicationContextInitializer](#applicationcontextinitializer)
  * [SpringApplicationRunListener](#springapplicationrunlistener)
  * [CommandLineRunner](#commandlinerunner)
  * [ApplicationRunner](#applicationrunner)
* [启动流程分析](#启动流程分析)
* [SpringBoot 监控](#springboot-监控)
  * [SpringBoot 监控 - Spring Boot Admin](#springboot-监控---spring-boot-admin)
* [项目部署](#项目部署)

# Spring缺点

![](/static/2021-09-02-16-07-05.png)

# Springboot功能

![](/static/2021-09-02-16-07-32.png)

# 快速搭建

搭建SpringBoot工程，定义HelloController.hello()方法，返回”Hello SpringBoot!”。

* ① 创建Maven项目
* ② 导入SpringBoot起步依赖
* ③ 定义Controller
* ④ 编写引导类 - 项目入口
* ⑤ 启动测试

![](/static/2021-09-02-16-11-19.png)
![](/static/2021-09-02-16-12-50.png)

引导类（一般Application结尾

* `@SpringBootApplication`
* ![](/static/2021-09-02-16-13-41.png)

## IDEA快速构建

![](/static/2021-09-02-16-15-36.png)
![](/static/2021-09-02-16-16-16.png)

选依赖

![](/static/2021-09-02-16-16-40.png)

然后完成搭建

# SpringBoot 起步依赖原理分析

1） spring-boot-starter-parent

* 父工程里定义好了一些坐标版本信息，子类继承时不用定义版本信息了
* ![](/static/2021-09-02-16-21-30.png)
* ![](/static/2021-09-02-16-21-19.png)

2） spring-boot-starter-web

* ![](/static/2021-09-02-16-22-11.png)
* ![](/static/2021-09-02-16-22-20.png)

---

![](/static/2021-09-02-16-23-21.png)

# SpringBoot 配置

## 配置文件分类

SpringBoot是基于约定的，所以很多配置都有默认值，但如果想使用自己的配置替换默认配置的话，就可以使用 `application.properties`或者`application.yml`（`application.yaml`）进行配置

![](/static/2021-09-02-16-24-20.png)

* `server.port=8080`
* SpringBoot提供了2种配置文件类型：properties和yml/yaml
* 默认配置文件名称：application
* **在同一级目录下优先级为：properties > yml > yaml**

## yaml

> YAML全称是 YAML Ain't Markup Language 。YAML是一种直观的能够被电脑识别的的数据数据序列化格式，并且容易被人类阅 读，容易和脚本语言交互的，可以被支持YAML库的不同的编程语言程序导入，比如： C/C++, Ruby, Python, Java, Perl, C#, PHP 等。YML文件是以数据为核心的，比传统的xml方式更加简洁。 YAML文件的扩展名可以使用.yml或者.yaml。

![](/static/2021-09-02-18-55-27.png)

---

基本语法

* 大小写敏感
* 数据值前边必须有空格，作为分隔符
* 使用缩进表示层级关系
* **缩进时不允许使用Tab键，只允许使用空格**（各个系统 Tab对应的 空格数目可能不同，导致层次混乱）。
* 缩进的空格数目不重要，只要相同层级的元素左侧对齐即可
* `#` 表示注释，从这个字符一直到行尾，都会被解析器忽略

---

数据格式

![](/static/2021-09-02-18-57-41.png)

---

参数引用

```yaml
name: lisi
person:
  name: ${name} # 引用上边定义的name值
```

## 读取配置文件内容

* 1） @Value
* 2） Environment
* 3） @ConfigurationProperties

@Value【获取单个值方便】

![](/static/2021-09-02-19-06-30.png)
![](/static/2021-09-02-19-06-49.png)

Environment对象，设置自动装配

![](/static/2021-09-02-19-10-45.png)
![](/static/2021-09-02-19-11-13.png)

* `env.getProperty(...)`

@ConfigurationProperties

![](/static/2021-09-02-19-13-19.png)

![](/static/2021-09-02-19-14-54.png)
![](/static/2021-09-02-19-14-30.png)

## Profile

我们在开发Spring Boot应用时，通常同一套程序会被安装到不同环境，比如：开发、测试、生产等。其中数据库地址、服务 器端口等等配置都不同，如果每次打包时，都要修改配置文件，那么非常麻烦。profile功能就是来进行动态配置切换的。

1） **profile配置方式**

* 多profile文件方式
  * ![](/static/2021-09-02-19-19-45.png)
* yml多文档方式
  * 单yml文件，分为不同部分
  * 利用`spring: profiles: xxx`区分，起别名 【**sb2中已过时**】
  * ![](/static/2021-09-02-19-22-55.png)
  * `spring.config.activate.on-profile: test/dev/pro`

2） **profile激活方式**

* 配置文件
  * `spring.profiles.active=dev`
  * ![](/static/2021-09-02-19-20-22.png)
  * yml ![](/static/2021-09-02-19-23-57.png)
* 虚拟机参数
  * `-Dspring.profiles.active=dev`
* 命令行参数
  * `java –jar xxx.jar --spring.profiles.active=dev`

![](/static/2021-09-02-19-19-32.png)
![](/static/2021-09-02-19-19-45.png)

## 内部配置加载顺序

Springboot程序启动时，会从以下位置加载配置文件：

1. file:`./config/`：当前项目下的/config目录下
2. file:`./` ：当前项目的根目录
3. `classpath:/config/`：classpath的/config目录
4. `classpath:/` ：classpath的**根目录**
   1. resources

加载顺序为上文的排列顺序，高优先级配置的属性会生效

![](/static/2021-09-04-11-55-59.png)

## 外部配置加载顺序

通过官网查看外部属性加载顺序：

https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

* 命令行jar包设置参数
  * 项目路径下的配置不会打包进来

# SpringBoot整合Junit

* ① 搭建SpringBoot工程
* ② 引入starter-test起步依赖
* ③ 编写测试类
* ④ 添加测试相关注解
  * @RunWith(SpringRunner.class)
    * 好像可以不写
  * @SpringBootTest(classes = 启动类.class)
* ⑤ 编写测试方法

![](/static/2021-09-04-12-32-51.png)

![](/static/2021-09-04-12-33-20.png)

# SpringBoot整合Redis

* ① 搭建SpringBoot工程
* ② 引入redis起步依赖
* ③ 配置redis相关属性
  * 6379（默认port）
* ④ 注入RedisTemplate模板
* ⑤ 编写测试方法，测试

![](/static/2021-09-04-12-36-14.png)

改配置

![](/static/2021-09-04-12-38-00.png)

# SpringBoot整合MyBatis

* ① 搭建SpringBoot工程
* ② 引入mybatis起步依赖，添加mysql驱动
  * `mybatis-spring-boot-starter`
* ③ 编写DataSource和MyBatis相关配置
* ④ 定义表和实体类
* ⑤ 编写dao和mapper文件/纯注解开发
* ⑥ 测试

数据源配置

![](/static/2021-09-04-14-14-43.png)

映射文件，mappeer接口【纯注解】

![](/static/2021-09-04-18-44-55.png)
![](/static/2021-09-04-18-45-14.png)

mapper接口 & xml配置

![](/static/2021-09-04-18-49-02.png)
![](/static/2021-09-04-18-49-32.png)
![](/static/2021-09-04-18-50-26.png)

# SpringBoot 自动配置

## Condition

Condition

Condition 是在Spring 4.0 增加的条件判断功能，通过这个可以功能可以实现选择性的创建 Bean 操作。

* redistemplate怎么创建的？判断当前环境下有没有某坐标，有就帮忙注入

**自定义条件：**

① 定义条件类：自定义类实现**Condition接口**，重写 matches 方法，在 matches 方法中进行逻辑判断，返回boolean值 。 matches 方法两个参数：

* `context`：上下文对象，可以获取属性值，获取类加载器，获取BeanFactory等。
* `metadata`：元数据对象，用于获取注解属性。【注解所在位置的注释信息
* 返回`true`注入

② 判断条件： 在初始化Bean时，使用 @Conditional(条件类.class)注解

SpringBoot 提供的常用条件注解：

* `ConditionalOnProperty`：判断配置文件中是否有对应属性和值才初始化Bean
* `ConditionalOnClass`：判断环境中是否有对应字节码文件才初始化Bean
* `ConditionalOnMissingBean`：判断环境中没有对应Bean才初始化Bean

## Condition案例

在 Spring 的 IOC 容器中有一个 User 的 Bean，现要求：

1. 导入Jedis坐标后，加载该Bean，没导入，则不加载。

![](/static/2021-09-05-14-27-09.png)
![](/static/2021-09-05-14-27-24.png)

2. 将类的判断定义为**动态**的。判断哪个字节码文件存在可以动态指定【自己实现@ConditionOnClass

自定义注解【**自定义注解上加@condition注解之所以有用，是因为spring提供了递归查找注解的功能，所以自定义的注解才能生效，这种组合注解的用法并非JAVA的源生能力**】

![](/static/2021-09-05-14-30-03.png)
![](/static/2021-09-05-14-29-05.png)

修改之前接口逻辑

![](/static/2021-09-05-14-36-21.png)

## 切换内置web服务器

SpringBoot的web环境中默认使用tomcat作为内置服务器，其实SpringBoot提供了4种内置服务器供我们选择，我们可 以很方便的进行切换

![](/static/2021-09-05-14-47-59.png)
![](/static/2021-09-05-14-48-38.png)
![](/static/2021-09-05-14-48-49.png)

通过导入不同web服务器坐标来实现，web服务器动态切换

![](/static/2021-09-05-14-50-09.png)

## @Enable*注解

SpringBoot中提供了很多Enable开头的注解，这些注解都是用于动态启用某些功能的。而其底层原理是使用@Import注 解导入一些配置类，实现Bean的动态加载

## @Import注解

@Enable*底层依赖于@Import注解导入一些类，使用@Import导入的类会被Spring加载到IOC容器中。而@Import提供4中用 法：

* ① 导入Bean
* ② 导入配置类
* ③ 导入 `ImportSelector` 实现类。一般用于加载配置文件中的类【字节码导入】
  * ![](/static/2021-09-05-17-58-46.png)
  * ![](/static/2021-09-05-17-58-54.png)
* ④ 导入 `ImportBeanDefinitionRegistrar`实现类。【包导入】
  * ![](/static/2021-09-05-18-01-04.png)
  * ![](/static/2021-09-05-18-01-24.png)

## @EnableAutoConfiguration 注解

@EnableAutoConfiguration 注解内部使用 `@Import(AutoConfigurationImportSelector.class)`来加载配置类。

* 配置文件位置：**`META-INF/spring.factories`**，该配置文件中定义了大量的配置类，当 SpringBoot 应用启动时，会自动加载 这些配置类，初始化Bean
* 并不是所有的Bean都会被初始化，**在配置类中使用Condition来加载满足条件的Bean**

## 自定义starter

自定义redis-starter。要求当导入redis坐标时，SpringBoot自动创建Jedis的Bean

* ① 创建 redis-spring-boot-autoconfigure 模块
* ② 创建 redis-spring-boot-starter 模块,依赖 redis-spring-boot-autoconfigure的模块
* ③ 在 redis-spring-boot-autoconfigure 模块中初始化 Jedis 的Bean。并定义META-INF/spring.factories 文件
* ④ 在测试模块中引入自定义的 redis-starter 依赖，测试获取
Jedis 的Bean，操作 redis。

![](/static/2021-09-05-19-21-49.png)

![](/static/2021-09-05-19-29-45.png)

* 全局搜索redis前缀配置
* 没有就用默认值

![](/static/2021-09-05-19-36-30.png)

* 动态获取参数，并注入Jedis

![](/static/2021-09-05-19-31-43.png)

# SpringBoot 监听机制

SpringBoot 的监听机制，其实是对Java提供的事件监听机制的封装。

Java中的事件监听机制定义了以下几个角色：

* ① **事件**：Event，继承 `java.util.EventObject` 类的对象
* ② **事件源**：`Source` ，任意对象Object
* ③ **监听器**：Listener，实现 `java.util.EventListener` 接口 的对象

---

SpringBoot 在项目启动时，会对几个监听器进行回调，**我们可以实现这些监听器接口，在项目启动时完成 一些操作**。

* ApplicationContextInitializer
* SpringApplicationRunListener
* CommandLineRunner
* ApplicationRunner

## ApplicationContextInitializer

![](/static/2021-09-05-19-44-48.png)

需要配置后生效

![](/static/2021-09-05-20-05-50.png)

## SpringApplicationRunListener

![](/static/2021-09-05-19-44-37.png)

![](/static/2021-09-05-19-45-31.png)
![](/static/2021-09-05-19-46-02.png)

需要配置后生效

![](/static/2021-09-05-20-06-28.png)

## CommandLineRunner

![](/static/2021-09-05-19-46-32.png)

当项目启动后执行

## ApplicationRunner

![](/static/2021-09-05-20-02-25.png)

当项目启动后执行

和上面那个一样

# 启动流程分析

![](/static/2021-09-05-20-09-39.png)

# SpringBoot 监控

SpringBoot自带监控功能Actuator，可以帮助实现对程序内部运行情况监控，比如监控状况、Bean加载情况、配置属性
、日志信息等

---

① 导入依赖坐标

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

② 访问http://localhost:8080/acruator

![](/static/2021-09-05-20-32-29.png)

* 暴露所有监控

![](/static/2021-09-05-20-29-45.png)

## SpringBoot 监控 - Spring Boot Admin

* Spring Boot Admin是一个开源社区项目，用于管理和监控SpringBoot应用程序。
* Spring Boot Admin 有两个角色，客户端(Client)和服务端(Server)。
* 应用程序作为Spring Boot Admin Client向为Spring Boot Admin Server注册
* Spring Boot Admin Server 的UI界面将Spring Boot Admin Client的Actuator Endpoint上的一些监控信息

![](/static/2021-09-05-20-36-39.png)

server

![](/static/2021-09-05-20-39-15.png)
![](/static/2021-09-05-20-39-34.png)

client

![](/static/2021-09-05-20-39-50.png)
![](/static/2021-09-05-20-41-10.png)

![](/static/2021-09-05-20-41-51.png)

# 项目部署

SpringBoot 项目开发完毕后，支持两种方式部署到服务器：

① jar包(官方推荐)

② war包

* ![](/static/2021-09-05-21-48-58.png)

