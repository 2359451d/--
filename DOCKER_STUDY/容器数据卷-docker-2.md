# Content

* [Content](#content)
* [docker容器数据卷（持久化）](#docker容器数据卷持久化)
* [容器内添加数据卷](#容器内添加数据卷)
  * [直接添加](#直接添加)
  * [DockerFile添加卷](#dockerfile添加卷)
* [数据卷容器](#数据卷容器)

# docker容器数据卷（持久化）

相当于外置硬盘，将想要保存的数据存入数据卷（容器间数据共享 & 容器宿主机间数据共享）

![](/static/2021-07-04-15-35-57.png)

卷-能干吗

![](/static/2021-07-04-15-44-43.png)

# 容器内添加数据卷

## 直接添加

`docker run -it -v /宿主机path:/容器path 镜像名`

* dir不存在会自己新建，类似共享文件夹
* ![](/static/2021-07-04-15-55-25.png)
  * `docker inspect <container_id>`

---

带权限（只读）

* `docker run -it -v /hostpath:/conatinerpath:ro image`
  * **只读**

## DockerFile添加卷

镜像的源码级别描述

![](/static/2021-07-04-16-13-36.png)

* 注意只支持容器内部添加卷（因为不能保证所有宿主机上都存在特定目录）
* `VOLUME["/dataVolume1", "/dataVolume2", ...]`

---

示例

![](/static/2021-07-04-16-18-55.png)
![](/static/2021-07-04-16-24-28.png)
![](/static/2021-07-04-16-27-06.png)

* 注意，~~直接命令不支持直接在宿主机里添加多卷~~
* 编写完后，编译获得新镜像
  * `docker build -f <path> -t <image_name>`
* docker会在宿主机内自动分配默认映射目录

# 数据卷容器

容器挂载数据卷，其他容器通过挂在父容器实现数据共享。**挂载数据卷的容器，称为数据卷容器**，，<font color="red">即容器间共享数据，多容器共享卷</font>

1. 新建一个容器挂载数据卷
2. `docker run -it --name host2 --volumes-from host1 <image_name>`
   1. 容器host2拷贝host1的数据卷

---

![](/static/2021-07-04-16-38-31.png)

* 注意，后续容器如果修改数据卷内容，会进行同步
* <font color="deeppink">除非所有使用该卷的容器都不存在了，这个数据卷会一直存在</font>