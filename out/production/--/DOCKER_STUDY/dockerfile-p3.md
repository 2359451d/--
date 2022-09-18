# Content

* [Content](#content)
* [定义](#定义)
* [编写规范](#编写规范)
* [保留字指令](#保留字指令)
  * [FROM](#from)
  * [MAINTAINER](#maintainer)
  * [RUN](#run)
  * [EXPOSE](#expose)
  * [WORKDIR](#workdir)
  * [ENV](#env)
  * [ADD](#add)
  * [COPY](#copy)
  * [VOLUME](#volume)
  * [CMD](#cmd)
  * [ENTRYPOINT](#entrypoint)
  * [ONBUILD](#onbuild)
  * [总结](#总结)
* [---](#---)
* [案例分析1](#案例分析1)
* [CMD/ENTRYPOINT案例](#cmdentrypoint案例)
* [ONBUILD案例](#onbuild案例)
* [Tomcat自定义Dockerfile案例](#tomcat自定义dockerfile案例)

# 定义

用来构建镜像的构建文件，一系列命令 & 参数构成的脚本

三步骤

1. 编写
2. docker build
3. docker run

# 编写规范

![](/static/2021-07-04-16-51-42.png)

Dockerfile执行流程

1. 基础镜像运行一个容器
2. 执行一条指令并对容器进行修改
3. 执行类似 `docker commit`的操作提交新镜像层
4. 基于刚提交的镜像运行一个新容器
5. 执行后续dockerfile指令，直到所有指令都完成

# 保留字指令

## FROM

基础镜像，新镜像基于哪个镜像

## MAINTAINER

镜像维护者姓名 & 邮箱

## RUN

容器构建时需要运行的命令

## EXPOSE

当前容器对外暴露出的端口号

## WORKDIR

指定在创建容器后，终端默认登陆进来的工作目录

## ENV

构建镜像过程中设置环境变量

## ADD

比copy强大，=拷贝 + 解压缩

* 将宿主机目录下的文件拷贝进镜像
* 且ADD命令会自动处理URL & 解压tar压缩包

## COPY

类似ADD，拷贝文件&目录到镜像中

将从构建上下文目录中<源路径>的文件/目录复制到新的一层镜像内的<目标路径>位置

* `COPY src dest`
* `COPY ["src", "dest"]`

## VOLUME

容器数据卷，用于数据保存&持久化工作

## CMD

指定一个容器启动时要运行的命令

* 可以有多个CMD指令，但**只有最后一个生效**，CMD会被 `docker run`之后的参数替换

## ENTRYPOINT

指定一个容器启动时要运行的命令,追加

## ONBUILD

当构建一个被继承的Dockerfile时运行命令，父镜像在被子继承后父镜像的onbuild被触发

## 总结

![](/static/2021-07-04-19-41-39.png)

# ---

# 案例分析1

自定义`mycentos`镜像

* 修改登陆后默认路径
* 支持vim
* 支持ifconfig

# CMD/ENTRYPOINT案例

都是容器要**启动**的CMD指令

![](/static/2021-07-05-17-17-16.png)

![](/static/2021-07-05-17-17-37.png)

* docker run之后的参数会被当作参数传递给ENTRYPOINT，形成新的命令组合

---

![](/static/2021-07-05-17-24-35.png)
![](/static/2021-07-05-17-26-28.png)

# ONBUILD案例

当构建一个被继承的Dockerfile时运行命令，**父镜像**在被子继承后**父镜像的onbuild被触发**

![](/static/2021-07-05-17-28-15.png)

# Tomcat自定义Dockerfile案例

![](/static/2021-07-05-17-36-48.png)

![](/static/2021-07-05-17-44-50.png)

* 运行&添加容器数据卷