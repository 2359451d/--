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