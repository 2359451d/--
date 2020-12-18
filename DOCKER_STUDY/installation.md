# 安装

centos 6.8

![image-20201214153500864](C:\Workspace\gitnote\static\installation\image-20201214153500864.png)

**epel**

<img src="C:\Workspace\gitnote\static\installation\image-20201214153612954.png" alt="image-20201214153612954" style="zoom:67%;" />



==如出现错误，先查看时区是否准确==

* 调整 `ntpdate cn.pool.ntp.org`



----

失败了，[参考](https://github.com/vfleaking/uoj/wiki/Install-Docker-on-CentOS-6.x)..6.8 yum源国内换到国外都没整成重整了个7，直接照[官方文档](https://docs.docker.com/engine/install/centos/)安装就行



查看当前centos版本

```bash
cat /etc/redhat-release
```



**启动docker服务**

```bash
sudo systemctl start docker
```



**验证是否正确安装docker引擎**

```bash
sudo docker run hello-world
```

* 从hub拉取镜像