# Revision

## Project Build

构建涉及内容

* 编译
* 测试正确性
* 打包
* 部署

maven支持的构建

* **清理：先前项目的编译文件**
* **（批量）编译：源码编译成字节码，`.class`**
  * 批量处理，`javac *.java`一次只能编译一个文件(按顺序)
* **（批量）测试：验证代码正确性，功能**
* **生成测试报告**
* **打包：`class`&配置等文件压缩成`jar/war`**
* **安装：安装`jar/war`文件至本地仓库**
* 建议手动~~部署：程序安装完毕执行~~

## Core Concepts

核心概念

* POM
  * 文件，名`pom.xml`，**项目对象模型**
  * **每个项目看为一个模型**，控制构建，管理依赖
* 约定的目录结构
  * maven项目目录位置有规定
* 坐标
  * **唯一的字符串**，用于表示资源
* 依赖管理
  * 管理项目中可使用的`jar`
* 仓库管理（了解）
  * 资源存放的位置
* 生命周期（了解）
  * maven构建项目的过程
* 插件&目标（了解）
  * 执行构建时用的工具是插件
* 继承
* 聚合

### Directory Structure Convention

遵循约定的目录结构

```txt
Project/
    ---/scr
    ------/main #主程序&配置文件
    ---------/java #放**程序包&包中java文件**
    ---------/resources #java程序中要使用的配置文件
    ------/test #测试用文件，可以没有
    ---------/java #放**测试包&包中java文件**
    ---------/resources #测试java程序中要使用的配置文件
    ---/pom.xml #maven核心文件
```

### Some Commands

🍊 <font color="blue">注意：执行以下命令必须在命令行进入`pom.xml`所在目录！</font>

一些常用指令

* `mvn compile`
  * <font color="red">编译`src/main`目录下所有`.java`文件</font>
  * <font color="blue">结果：项目根目录下【生成`target`目录，存放编译后的`.class`文件,同时`main/resources`下所有文件都拷贝至`target/classes`目录下】</font>
  * 为什么要下载？ - maven工具执行构建需要很多插件（java类-jar文件），需要从中央仓库中下载
  * 下载了什么？ - jar文件，插件，完成某些功能
  * 下载文件放在哪（默认仓库）？ - ![download-direcotry](/static/2020-08-09-00-50-35.png)
* `mvn test-compile`
  * 编译`test/main`目录下`.java`文件
* `mvn clean`
  * <font color="red">清理(会删除原来编译和测试的目录，即【清理 target 目录】，但是已经 install 到仓库里的包不会删除)</font>
* `mvn test`
  * 测试，生成`surefire-reports`目录，存放测试结果
  * <font color="red">执行此命令，会将以上所有步骤（编译，测试）执行一遍</font>
* `mvn package`
  * 打包主程序
  * 会编译、编译测试、测试、并且按照 pom.xml 配置把主程序打包生成 jar 包或者 war 包
  * <font color="red">输出命名规则`artefactId+version.jar/war`</font>
* `mvn install`
  * 安装主程序
  * 把本工程打包，并按照本工程坐标保存(安装)到**本地仓库Manven/repository/...**中
* `mvn deploy`
  * 部署主程序
  * 把本工程打包，并按照坐标保存到本地仓库中&并且保存到私服仓库中，自动把项目部署到web容器中

#### Unit Testing

单元测试

* 使用junit，专门用于测试的框架
* 测试类中方法，每个方法独立测试

🍊 在依赖中加入`JUnit`

* 在maven项目中的`src/test/java`目录下，创建测试程序
* 测试类名`Test+class_name`
* 测试方法名`test+method_name`
  * 无返回值，`public`，注解`@Test`

## Repository

[中央仓库地址](www.mvnrepository.com)

🍊 分类

* 本地仓库
  * 默认路径`users\..\.m2\repository`
  * 可通过`%Maven_home%/conf/settings.xml`配置文件修改
* 远程仓库
  * **中央仓库**：包含了绝大多数**流行的开源Java构件，以及源码、作者信息、许可证信息**
  * 中央镜像：中央仓库备份，**不同镜像，不同国家州服务器，给中央仓库分流，减轻访问下载压力**
  * <font color="blue">私服：在局域网环境中部署的服务器，为当前局域网范围内的所有 Maven工程服务。公司中常用</font>

🍊 maven仓库，存放

* Maven插件，即jar文件
* 自己开发项目的模块
* 第三方框架/工具的jar包

🍊 请求顺序 - request order

* 需要的驱动->local repo->private server->central mirror->central repo

### Setup the Local Repo Path

修改默认存放资源的目录路径

* 修改maven配置文件`./conf/settings.xml`，先备份
* 修改`<local_repository>path</local_repository>`

## pom.xml

> **Project Object Model:项目对象模型**。Maven把每个项目（结构&内容）抽象成一个模型，通过xml文件进行声明

![](/static/2020-08-10-20-01-39.png)
![](/static/2020-08-10-20-02-44.png)
![](/static/2020-08-10-23-58-56.png)

* `groupId`&`artifactId`&`version`组成坐标【gav】：项目**唯一标识**，互联网中通过坐标定位唯一项目
  * `groupId` - 公司域名倒写
  * `artifactId` - 项目名
  * `version` - 版本号

### add Junit

![](/static/2020-08-30-15-26-41.png)
![](/static/2020-08-30-15-26-58.png)
![](/static/2020-08-30-15-27-08.png)

### Add Dependency

🍊 添加依赖

```xml
<dependencies>
<dependency>坐标唯一标识</dependency>
</dependencies>
```

## Useful Properties Configuration

<font color="red">控制配置maven构建项目的参数设置</font>

* 设置JDK版本【配置编译插件】

![](/static/2020-08-30-17-40-01.png)
![](/static/2020-08-30-17-50-03.png)

* `compile`插件`maven-compiler-plugin`

```java
<properties>
  <maven.compiler.source>14</maven.compiler.source>
  <maven.compiler.target>14</maven.compiler.target>
</properties>
```

## Conjunction with IDEA

idea内置maven，一般使用自行安装的。需要手动配置

![](/static/2020-08-30-18-06-09.png)
![](/static/2020-08-30-18-47-12.png)
![](/static/2020-08-30-18-50-41.png)
![](/static/2020-08-30-19-50-49.png)
![](/static/2020-08-30-19-51-26.png)

* `-DarchetypeCatalog=internal`

创建新javasemodule项目，基于模板

![](/static/2020-08-30-19-39-32.png)
![](/static/2020-08-30-19-44-44.png)