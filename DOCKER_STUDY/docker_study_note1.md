# Docker Study Note

容器虚拟化技术

* [Docker Study Note 1](#docker-study-note-1)
  * [为什么](#为什么)
    * [Docker理念](#docker理念)
  * [Docker作用](#docker作用)
    * [容器虚拟化技术：Linux容器](#容器虚拟化技术linux容器)

# 为什么

解决了运行环境&配置问题的软件容器，方便做CI并有助于整体发布的容器虚拟化技术

* 环境&配置
* docker打包代码/配置/系统/数据

🍊 软件带环境安装？

* 安装的时候，把原始环境一摸一样的复制过来

---

docker包含两方面技术

1.镜像技术

* 从系统环境开始，自底至上打包整个项目

![](../static/2020-11-21-18-07-24.png)

# Docker理念

![](../static/2020-11-21-18-11-43.png)

* 假如4个服务，4个镜像，构建成1份用于docker安装

# Docker作用 & 底层原理

==之前的虚拟机技术==
![](../static/2020-11-21-18-18-02.png)

* 占用资源大
* 启动慢
* 冗余步骤多
* 虚拟一套硬件
* 客户端相当于本机命令

==docker vs 以前技术==
![](..\static\docker_study_note1\image-20201216214107946.png)

## 容器虚拟化技术：Linux容器

容器虚拟化技术 - **Linux容器（Linux Containers）**

* 改善普通虚拟机的缺点，
* ==不是模拟一个完整的os，而是对进程进行隔离==
  * 内核部分精简，其他不相关部分省去
  * 有了容器，可以将软件运行所需的所有资源打包到一个隔离的容器中
* <font color="red">容器内的应用进程直接运行在宿主的内核中</font>，自己本身没有内核，也没有对硬件虚拟
  * 所以比传统虚拟机更轻便
* ==每个容器之间相互隔离，有自己的文件系统==
  * 容器之间进程不会相互影响，能区分计算资源

# Docker基本组成

## Docker架构图：镜像、容器、仓库

![](..\static\docker_study_note1\image-20201214150416281.png)

==docker本身是一个容器运行载体，管理引擎（daemon线程）==

**镜像 Image**

* 只读的==模板==，镜像可以用来创建docker容器
* <font color="red">同一个镜像可以创建很多容器</font>

**容器 Container**

* docker利用容器独立运行的一个或一组应用 
* 镜像创建的运行实例，<u>一个容器运行一种服务</u>
* <font color="red">可以把容器看作一个简易版Linux环境</font>
  * 如centos镜像，内核，仅占170M

**仓库 Repository**

* 存放镜像文件的场所
* 与==仓库注册服务器(Registry)==有区别。
  * Registry上往往存放着多个仓库，每个仓库中又包含多个镜像，每个镜像有不同标签（tag）
* **分类**
  * 公开仓库 - Docker Hub（最大）
  * 私有仓库

# docker run

![](..\static\docker_study_note1\image-20201216213402888.png)

> 由于本地没有`hello-world`镜像，所以先下载一个并在容器内运行

`docker run` 以一个镜像为模板，创建这个镜像的容器

* 默认 `docker run image_name:latest`， latest标签可省略

# 帮助命令

## docker version：查看版本

![](..\static\docker_study_note1\image-20201217145149054.png)

## docker info: 查看信息

比 `docker version` 更完整

![](..\static\docker_study_note1\image-20201217150756857.png)

## docker --help

# 镜像命令

## docker images

> docker images
>
> * 列出本地镜像

![image-20201217161458301](C:\Workspace\gitnote\static\docker_study_note1\image-20201217161458301.png)

此时能运行的本地镜像只有 `hello-world`

---

![image-20201217161816008](C:\Workspace\gitnote\static\docker_study_note1\image-20201217161816008.png)

* ==REPOSITORY==镜像仓库源
* ==TAG==镜像标签
* ==IMAGE ID==镜像ID
* ==CREATED==镜像创建时间
* ==SIZE==镜像大小

---

**选项**

* `docker images -a` 列出所有本地镜像，==包含中间镜像==
* `docker images -q` 查看当前所有本地镜像的==id==
  * `docker images -qa` 查看当前所有本地镜像的镜像id （包含中间镜像）
* `docker images --digests` 显示镜像摘要信息
* `docker images --no-trunc` 显示镜像完整信息，可以看见完整id

## docker search

> 搜索某个镜像名字 - hub.docker.com

![](..\static\docker_study_note1\image-20201217164047953.png)

**选项**

* `docker search -s` 指定star数，筛选镜像
  * `dockeer search -s 30 tomcat` 筛选start数>30的tomcat
* `docker search --no-trunc` 显示镜像完整信息

## docker pull

```bash
docker pull <image_name>:<TAG>
```

拉取镜像

* <font color="red">不写`TAG`默认为`latest`</font>

## docker rmi

```bash
docker rmi <image_name>:<TAG>
```

删除镜像

* 不写`TAG`默认为`latest`

**选项**

* `docker rmi -f` 强制删除
* `docker rmi <image_id/name>` 删除单个特定镜像（通过镜像id/name指定）
* `docker rmi <image_id/name>:<TAG>  <image_id/name>:<TAG>` 删除多个镜像
* `docker rmi -f $(docker images -qa)`删除全部本地镜像
  * `docker images -qa` 列出当前所有本地镜像id（包括中间镜像）

# 容器命令

## docker run IMAGE

```bash
docker run [OPTIONS] IMAGE [COMMAND][ARG...]
```

* (根据某镜像)新建并启动容器

**OPTIONS**说明

![](..\static\docker_study_note1\image-20201217212028686.png)

* `--name=""`为容器指定名称
* `-i`以交互模式运行容器，通常与`-t`同时使用
* `-t`为容器重新分配一个伪输入终端，
