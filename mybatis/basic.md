# Content

* [Content](#content)
* [简介](#简介)
* [流程对比](#流程对比)
* [Mybatis流程](#mybatis流程)
  * [导包](#导包)
  * [全局配置文件](#全局配置文件)
  * [SQL映射文件](#sql映射文件)
  * [全局配置中注册映射文件](#全局配置中注册映射文件)
  * [测试](#测试)
* [HelloWorld示例](#helloworld示例)
  * [TestCase](#testcase)
* [实现细节](#实现细节)
* [全局配置文件](#全局配置文件-1)
  * [properties（*） - 引用外部配置文件](#properties---引用外部配置文件)
  * [settings（*） - 修改mybatis运行时行为](#settings---修改mybatis运行时行为)
  * [typeAliases - 类型别名](#typealiases---类型别名)
  * [typeHandlers - 类型处理器](#typehandlers---类型处理器)
  * [plugins - 插件](#plugins---插件)
  * [environments - 环境配置](#environments---环境配置)
  * [databaseIdProvider - 数据库移植](#databaseidprovider---数据库移植)
  * [mappers（*）- 批量注册](#mappers--批量注册)
* [SQL映射文件](#sql映射文件-1)
  * [CUD标签](#cud标签)
    * [selectKey子标签](#selectkey子标签)
  * [参数传递](#参数传递)
  * [参数处理](#参数处理)
  * [#{} vs ${}](#-vs-)
  * [查询返回list](#查询返回list)
  * [查询返回map](#查询返回map)
  * [resultMap自定义封装规则](#resultmap自定义封装规则)
  * [联合查询](#联合查询)
    * [自定义resultMap](#自定义resultmap)
    * [推荐：resultMap-association标签](#推荐resultmap-association标签)
    * [关联关系](#关联关系)
    * [推荐：resultMap-collection标签](#推荐resultmap-collection标签)
    * [【不推荐】分步查询](#不推荐分步查询)
    * [association分步查询](#association分步查询)
    * [collection分步查询](#collection分步查询)
    * [分步查询-传参多个](#分步查询-传参多个)
    * [分步查询 - 按需/延迟加载](#分步查询---按需延迟加载)

# 简介

持久化框架

![](/static/2021-08-04-14-40-38.png)
![](/static/2021-08-04-14-37-27.png)

* jdbc耦合

![](/static/2021-08-04-14-37-01.png)
![](/static/2021-08-04-14-37-10.png)

* hibernate限制复杂sql编写
  * 非要写必须通过HQL
* 全映射框架
  * 部分字段映射很难

mybatis（轻量级框架）

![](/static/2021-08-04-14-43-41.png)
![](/static/2021-08-04-14-48-55.png)

# 流程对比

1.导包

* mybatis
* 数据库驱动
* 日志包（建议）`log4j`
  * 关键环节有log输出
  * 依赖`log4j.xml`配置文件

2.环境搭建

* 原生环境搭建
  * 创建测试库，测试表，以及封装数据的javaBean，操作数据库的DAO
  * ![](/static/2021-08-04-15-06-25.png)
  * ![](/static/2021-08-04-15-06-45.png)
* mybatis环境搭建
  * 导包
  * 写配置
  * 测试

# Mybatis流程

## 导包

![](/static/2021-08-04-15-13-08.png)

## 全局配置文件

指导mybatis如何正确运行，如连接哪个数据库

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <!-- 连接池 -->
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <mappers>
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```

## SQL映射文件

编写每个DAO方法如何向数据库发送sql，如何执行

* 本来是给DAO接口写一个实现类，现在用映射文件充当
* **这个映射文件，mybatis默认不知道，需要在全局配置文件中注册**

`#{属性名}`取出传过来的参数

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!-- namespace：名称空间，接口全类名，告诉mybatis这个配置文件实现哪个接口的 -->
<mapper namespace="org.mybatis.example.BlogMapper">
<!-- 定义查询操作
    id:方法名，相当于对于某个方法的实现
    resultType：方法返回后的返回值类型（全类名）
    #{属性名}取出传过来的参数
 -->
  <select id="selectBlog" resultType="Blog">
    select * from Blog where id = #{id}
  </select>
</mapper>
```

![](/static/2021-08-04-15-06-45.png)
![](/static/2021-08-04-15-34-41.png)

## 全局配置中注册映射文件

![](/static/2021-08-04-15-36-46.png)

## 测试

1.根据全局配置文件，创建一个`SqlSessionFactory`

* 负责创建SqlSession对象
  * sql会话，代表和db的一次会话

```java
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

2.获取和db的一次会话（拿一次连接）

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
//   Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
}
```

3.使用SqlSession操作db，获取DAO接口的实现（**映射器mapper**） & 调用接口方法

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  Blog blog = mapper.selectBlog(101);
}
```

![](/static/2021-08-04-15-47-23.png)

4.用完释放会话

![](/static/2021-08-04-15-49-41.png)

# HelloWorld示例

DAO接口
![](/static/2021-08-04-16-04-25.png)

全局配置
![](/static/2021-08-04-16-05-07.png)
![](/static/2021-08-04-16-18-14.png)

---

映射文件
![](/static/2021-08-04-16-07-44.png)

* 指定映射哪个DAO接口（哪个DAO接口的实现）

查
![](/static/2021-08-04-16-11-01.png)

改
![](/static/2021-08-04-16-12-31.png)

删
![](/static/2021-08-04-16-13-39.png)

增
![](/static/2021-08-04-16-14-33.png)

## TestCase

![](/static/2021-08-04-16-17-25.png)

查
![](/static/2021-08-04-16-17-48.png)

增
![](/static/2021-08-04-16-21-36.png)
![](/static/2021-08-04-16-22-36.png)

# 实现细节

SqlSession中获取的**映射器**（获取到的是代理对象），相当于DAO的一个实现类，mybatis自动创建的

SqlSessionFactory创建SqlSession对象，Factory本身只用new一次就行

* `SqlSession`，相当于与db进行交互的一次会话，每次会话应该创建一个新的

# 全局配置文件

![](/static/2021-08-04-18-05-41.png)

保证标签先后顺序

## properties（*） - 引用外部配置文件

相当于 spring，context，property-placeholder。引用外部配置文件

* `resource` - 类路径下开始引用
* `url` - 磁盘路径或网络资源

![](/static/2021-08-04-16-35-30.png)

## settings（*） - 修改mybatis运行时行为

属性

* `mapUnderscoreToCamelCase`
  * 是否开启驼峰命名自动映射，即从经典数据库列名 `A_COLUMN` 映射到经典 Java 属性名 `aColumn`

:bulb: bean字段名和db字段名对得上的可以自动映射，不一样但满足上面驼峰命名映射的，开启映射后也可以进行自动映射

![](/static/2021-08-04-16-50-24.png)
![](/static/2021-08-04-16-50-40.png)
![](/static/2021-08-04-16-48-47.png)

## typeAliases - 类型别名

<font color="deeppink">不推荐起别名</font>

为常用的java类型（javabean）起别名

![](/static/2021-08-04-16-56-33.png)

* 之前得写全类名

默认别名（类名
![](/static/2021-08-04-16-57-18.png)
![](/static/2021-08-04-16-57-01.png)

指定别名
![](/static/2021-08-04-16-57-48.png)
![](/static/2021-08-04-16-57-59.png)

---

批量起别名

* 也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

```xml
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
```

![](/static/2021-08-04-16-59-21.png)

:bulb: 如果想在批量起别名时指定别名，使用`@Alias`注解

```java
@Alias("author")
public class Author {
    ...
}
```

## typeHandlers - 类型处理器

![](/static/2021-08-04-17-42-37.png)

* 填充占位符的时候需要自己判断setStr/int
* mybatis底层一样，通过typeHandler进行判断

可以自定义，实现后加进全局配置文件就可以
![](/static/2021-08-04-17-51-15.png)

## plugins - 插件

![](/static/2021-08-04-17-55-22.png)

## environments - 环境配置

配一个具体环境，每一个都需要事务管理器 & 数据源

* `id` - 当前环境唯一表示
* `default` - 默认使用哪个环境

![](/static/2021-08-04-18-00-02.png)

* :bulb:**之后数据源 & 事务管理器都在spring中配，更强大**

## databaseIdProvider - 数据库移植

一般很少有这个需求

```xml
<databaseIdProvider type="DB_VENDOR">
<!-- name：数据库厂商标识，value：标识名字 -->
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```

![](/static/2021-08-04-18-25-50.png)
![](/static/2021-08-04-18-25-40.png)

## mappers（*）- 批量注册

写好的sql映射文件需要用mappers注册

* `class`
  * 引用接口全类名
  * **1.需要xml和DAO接口同目录下，且文件名&DAO接口名相同，不然绑定失败** （不太好维护，推荐用`resource`）
    * ![](/static/2021-08-04-18-30-22.png)
  * <font color="deeppink">或者不写配置文件，直接用注解(不推荐单独使用。复杂重要的用配置，简单的用注解）</font>
    * ![](/static/2021-08-04-18-32-32.png)
    * ![](/static/2021-08-04-18-32-17.png)
* `resource`
  * 类路径下找映射文件
* `url`
  * 从磁盘或网络路径引用

:bulb: 批量注册

```xml
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

![](/static/2021-08-04-18-38-24.png)

* 注意普通DAO还是得和配置文件同目录
  * ![](/static/2021-08-04-18-39-38.png)可以配置文件放在同名包里

# SQL映射文件

![](/static/2021-08-06-16-20-24.png)

## CUD标签

![](/static/2021-08-06-16-22-42.png)

i nsert 、 update 、 delete属性

:bulb: `useGeneratedKeys` & `keyProperty`可以获取插入数据后的自增ID

![](/static/2021-08-06-16-36-15.png)

* 需要配置让mybatis自动将自增id注入bean
  * `useGeneratedKey=True`使用自动生成的主键
  * `keyProperty`将自增的id封装给哪个属性

### selectKey子标签

:bulb: `selectKey`

![](/static/2021-08-06-16-45-36.png)
![](/static/2021-08-06-16-44-27.png)
![](/static/2021-08-06-16-44-46.png)

* 不支持自增型主键的db，可以使用这个子元素
* `order="BEFORE"`可以在核心sql语句之前运行一个查询sql，查到id，然后通过`keyProperty`赋值至属性

## 参数传递

:bulb: 单参数

* 基本类型
  * `#{随便一个名字}`就能取到
* pojo

:bulb: 多参数传递

![](/static/2021-08-06-16-50-33.png)

* `#{参数名，参数名}`取不到（**因为map默认没有这样的key**）
* 做法1 - 默认取mapkey
  * `#{param1,param2, 0,1}`使用这种组合，麻烦
  * <font color="deeppink">只要传入了多个参数，mybatis会自动将这些参数封装在一个map中，封装时使用的key就是参数的索引 & 参数的第几个标识</font>，**所以`#{key}`是从这个map中取值**
  * ![](/static/2021-08-06-16-50-47.png)
* 做法2 - **命名参数(推荐**）
  * 使用`@Param`在DAO入参前指定封装进map的key名称
  * ![](/static/2021-08-06-16-59-19.png)
  * ![](/static/2021-08-06-16-59-26.png)

:bulb: POJO传递

* `#{pojo属性}`

:bulb: map传递

![](/static/2021-08-06-17-03-48.png)
![](/static/2021-08-06-17-04-10.png)

* `#{map的key}`

---

扩展，多参数，pojo属性

![](/static/2021-08-06-17-06-48.png)

## 参数处理

![](/static/2021-08-06-17-08-44.png)

`#{key}`取值时，可以设置规则

* 一般只用`jdbcType`，**如果传递了null值，需要设置（oracle可能报错）**
* ![](/static/2021-08-06-17-13-07.png)

## #{} vs ${}

`#{}`参数预编译方式，参数位置用`?`替代，参数后来都是预编译设置进去的

![](/static/2021-08-06-17-19-10.png)

* 无sql注入问题
* **只有sql参数位置能预编译**，所以不支持动态注入表名的查询

`${}`不是预编译，直接和sql语句进行拼串

![](/static/2021-08-06-17-19-34.png)

* 可以支持动态注入表名，即，**在不支持参数预编译位置进行取值使用${}**

## 查询返回list

查询多条记录，封装成list

![](/static/2021-08-06-17-21-19.png)
![](/static/2021-08-06-17-24-24.png)
![](/static/2021-08-06-17-24-34.png)

## 查询返回map

查询单条记录，封装map

![](/static/2021-08-06-17-27-15.png)
![](/static/2021-08-06-17-27-35.png)

* map
  * key - **字段名**
  * value - **字段值**
* **这里是单条，所以返回类型可以写成map**

查询多条记录，封装map

![](/static/2021-08-06-17-32-33.png)
![](/static/2021-08-06-17-34-31.png)

* key - 记录PK（id
  * **需要使用`@MapKey`指定封装用记录的什么字段名作为key**
* value - 记录本身（Employee pojo
* **多条记录（集合），返回类型应该写封装的元素类型本身**

## resultMap自定义封装规则

默认mybatis自动封装结果集

* 字段名 & bean属性名一一对应（不区分大小写）
* 对不上
  * 开启**驼峰命名法**，（如字段`A_COLUMN`对应bean属性`aColumn`）
  * **起别名**
    * ![](/static/2021-08-06-17-44-09.png)
    * ![](/static/2021-08-06-17-44-17.png)

---

:bulb: **自定义结果集** `resultMap` 标签

![](/static/2021-08-06-17-49-34.png)

* 自己定义字段 & javabean映射规则
* 属性
  * `id` 可引用的唯一标识
  * `type` 指定为哪个javabean自定义封装规则
* 子标签
  * `id` 指定**主键列规则**
    * `column`指定哪一列是PK
    * `property`指定bean的哪个属性封装id这列数据
  * `result` **其他列**
    * `column`指定字段列
    * `property`指定bean的哪个属性封装这列数据

:balloon: 注意要指定使用自定义规则，不要使用原`resultType`（默认规则）

![](/static/2021-08-06-17-51-28.png)

* `resultMap=“自定义结果集唯一表示”`

## 联合查询

注意：之前的map,list封装都不涉及联合属性（而且用的是默认映射结果集）

### 自定义resultMap

推荐自定义结果集封装规则（不然映射名字对不上很麻烦）

![](/static/2021-08-06-19-59-27.png)
![](/static/2021-08-06-20-00-37.png)
![](/static/2021-08-06-20-01-25.png)

* 注意sql语句join属性最好起别名，不然不好封装

### 推荐：resultMap-association标签

mybatis推荐

`association`表示联合的一个对象

![](/static/2021-08-06-20-11-48.png)

### 关联关系

1-1

* 1key-1lock

1-n

* 1lock-nkey

n-m

* 中间表表示对应关系

### 推荐：resultMap-collection标签

nkey对应1lock（根据lockid查出所有key）

![](/static/2021-08-06-20-23-17.png)

* TypeHandler自动推断出List集合封装，type指定集合中元素类型，之后通过自定义结果集映射bean属性&字段

定义集合类型属性的封装

![](/static/2021-08-06-20-31-10.png)
![](/static/2021-08-06-20-35-36.png)
![](/static/2021-08-06-20-35-17.png)

### 【不推荐】分步查询

虽然简单但是要看db好几次，性能也不怎么样

推荐用连接查询

### association分步查询

原本涉及join属性，写起来很麻烦

* 现在FK部分让mybatis自己调用另一个查询查

![](/static/2021-08-06-20-44-29.png)
![](/static/2021-08-06-20-50-38.png)

先查key，再查lock（查的时候mybatis通过association-column把lockid传过去）

---

### collection分步查询

1lock得到n个key

先定义根据lockid查询所有key的一个sql
![](/static/2021-08-06-20-59-25.png)
![](/static/2021-08-06-21-00-49.png)

定义根据lockid查lock的sql（包括FK所有数据） & 自定义结果集映射规则（调用查key的sql时，传参lockid）

![](/static/2021-08-06-21-02-30.png)
![](/static/2021-08-06-21-03-19.png)
![](/static/2021-08-06-21-05-20.png)

### 分步查询-传参多个

![](/static/2021-08-06-21-13-26.png)

`column="{id=id,...}"`

* beankey1=字段名，beankey2=字段名

### 分步查询 - 按需/延迟加载

![](/static/2021-08-06-20-51-15.png)

* 原本这个分步查询，如果只需要key的信息不管怎么样lock的信息都会被查出来，db性能问题

**按需/延迟加载**

* mybatis全局开启按需加载策略
* **开启`lazyLoadingEnabalsetrue`延迟加载**
  * 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 fetchType 属性来覆盖该项的开关状态。
* **关闭`aggressiveLazyLoading=false`，=开启属性按需加载**
  * 开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 lazyLoadTriggerMethods)。

:bulb: 以上全局配置，可以被association中属性覆盖

* `fetchType`
  * `eager`
  * `lazy`

