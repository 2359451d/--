# Nginx Study Note 1

nginx学习笔记1

* [Nginx Study Note 1](#nginx-study-note-1)
  * [Nginx简介](#nginx简介)
  * [正向代理 vs 反向代理](#正向代理-vs-反向代理)
    * [正向代理 Forward proxy](#正向代理-forward-proxy)
    * [反向代理 Reverse proxy](#反向代理-reverse-proxy)
  * [负载均衡 Load balancing](#负载均衡-load-balancing)
    * [负载均衡分配策略 allocation strategy](#负载均衡分配策略-allocation-strategy)
  * [动静分离 Dynamic and Static Separation](#动静分离-dynamic-and-static-separation)
  * [Nginx配置文件：nginx.conf](#nginx配置文件nginxconf)
    * [全局块](#全局块)
    * [Events块](#events块)
    * [(*)Http块](#http块)
      * [Http全局块](#http全局块)
      * [Server块](#server块)
    * [Server-Location指令](#server-location指令)
  * [Nginx配置实例：反向代理](#nginx配置实例反向代理)
    * [反向代理1](#反向代理1)
    * [反向代理2](#反向代理2)
      * [Location指令说明](#location指令说明)
  * [Nginx配置示例：负载均衡](#nginx配置示例负载均衡)
  * [Nginx配置：动静分离](#nginx配置动静分离)
  * [Nginx配置：高可用集群](#nginx配置高可用集群)
  * [虚拟主机](#虚拟主机)
  * [Nginx原理](#nginx原理)
    * [worker是如何工作的](#worker是如何工作的)
    * [1个master多个worker的好处](#1个master多个worker的好处)
    * [多少个worker合适](#多少个worker合适)
    * [worker_connection连接数为多少](#worker_connection连接数为多少)

## Nginx简介

什么是Nginx？

> Nginx 是**高性能的 HTTP 和反向代理的服务器**，**特点是占有内存少，并发能力强**，**能经受高负载的考验**,有报告表明能支持高达 50,000 个并发连接数

## 正向代理 vs 反向代理

### 正向代理 Forward proxy

![](/static/2020-11-18-18-38-11.png)
![](/static/2020-11-18-18-38-21.png)

正向代理

* 在客户端（浏览器）中需要**配置代理服务器**，通过代理服务器进行互联网访问

### 反向代理 Reverse proxy

![](/static/2020-11-18-18-41-39.png)
![](/static/2020-11-18-18-52-34.png)

* 通过**反向代理服务器**，**根据请求转发到不同的服务器**中（虚拟or真实）
  * 客户端不知道有几个服务器（反向&目标服务器对外就是一个服务器）。**真正暴露的是反向代理服务器，隐藏的是真实服务器**

## 负载均衡 Load balancing

最普通的交互模式
![](/static/2020-11-18-19-01-36.png)

* 过于单一，**并发请求相对较少的情况下比较合适，成本低**
  * 随着信息数量的不断增长，访问量和数据量的飞速增长，以及系统业务的复杂度增加，**这种架构会造成服务器相应客户端的请求日益缓慢，并发量特别大，容易造成服务器崩溃**

🍊 如何解决？

![](/static/2020-11-18-19-05-32.png)
![](/static/2020-11-18-19-12-19.png)

* 提升服务器配置
  * 不能从根本解决问题
* 集群概念 - **单个服务器解决不了，增加服务器的数量，将请求分发到各个服务器上**
  * 即，将负载分发到不同服务器上，也就是负载均衡

### 负载均衡分配策略 allocation strategy

而且 Nginx 提供了几种分配方式(策略)：

1. **轮询（默认）**
   1. 每个请求按时间顺序逐一分配到不同的后端服务器，
   2. 如果后端服务器 down 掉，能自动剔除。
2. **weight**
   1. 代表权重，默认为`1`
   2. **权重越高，被分配的客户端越多**
   3. 指定轮询几率，weight和访问比率成正比，用于后端服务器性能不均的情况
   4. ![](/static/2020-11-20-15-50-38.png)
3. **ip_hash**
   1. 每个请求按照访问ip的hash结果分配
   2. 这样每个访客固定访问一个后端服务器，可以解决session问题
   3. ![](/static/2020-11-20-15-53-03.png)
4. **fair（第三方）**
   1. 按照后端服务器的响应时间来分配请求，**响应时间短的优先分配**
   2. ![](/static/2020-11-20-15-53-50.png)

## 动静分离 Dynamic and Static Separation

![](/static/2020-11-18-19-27-29.png)

* 最原始的方案，静态&动态资源一起存放在真实服务器中

实现动静分离
![](/static/2020-11-18-19-13-25.png)
![](/static/2020-11-18-19-30-03.png)

* 如果请求的是静态资源，nginx返回静态资源
* 如果请求动态资源，nginx返回动态资源


🍬 为什么要动静分离

* **提高访问效率**

动静分离（**严格来说是动态请求&静态请求分开处理**） - 两种实现角度

![](/static/2020-11-20-16-12-18.png)

1. **纯粹把静态文件独立成单独的域名，放在独立的服务器上**
   1. 目前主流推崇的方案；
   2. 静态资源 & tomcat（动态资源）分开部署的
2. **动态跟静态文件混合在一起发布，通过 nginx 来分开**

🍬 通过`location`指定**不同后缀名实现的请求转发**

* `expires`参数设置
  * 使浏览器缓存过期时间，减少与服务器之前的请求和流量
  * <font color="red">给资源设定一个过期时间，无需去服务端验证，直接通过浏览器自身确认是否过期即可。</font>不会产生额外流量，非常适合不经常变动的资源
  * 比如设置`3d`，表示3天之内访问这个url，发送请求，**如果比对服务器，该文件最后更新时间没有改变，则不会从服务器抓取，返回状态码`304`. 如果有修改，则直接从服务器重新下载，返回状态码`200`**

 Nginx: 常用命令

🍬 注意，没配环境变量情况下，需要
`cd /usr/local/nginx/sbin`中运行`.nginx`

---

查看版本

* `nginx -v`

启动nginx

* `nginx`

关闭nginx

* `ngxin -s stop`

**重新加载nginx**

* `nginx -s reload`
* **修改nginx配置文件`nginx.conf`后，不想重新启动nginx，可以使用重新加载**

## Nginx配置文件：nginx.conf

位置

* `/usr/local/nginx/conf/nginx.conf`

配置内容 - **分为三部分**

* **全局块**
* **events块**
* **http块**

### 全局块

> 从配置**文件开始到 events 块之间的内容**，主要会设置一些影响 nginx 服务器**整体运行的配置指令**，主要包括配置运行 Nginx 服务器的用户（组）、允许生成的worker process 数，进程 PID 存放路径、日志存放路径和类型以及配置文件的引入等。

```conf

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

```

如`worker-processes 1;`

* Nginx 服务器**处理并发数量**的服务关键配置，
  * worker_processes 值越大，可以支持的并发处理量也越多，
  * <font color="red">但是会受到硬件、软件等设备的制约</font>

### Events块

events 块涉及的指令**主要影响 Nginx 服务器与用户的网络连接**，常用的设置包括

* 是否开启对多 `work process`下的网络连接进行序列化
* 是否允许同时接收多个网络连接
* 选取哪种事件驱动模型来处理连接请求，
* 每个 `worker_process` 可以同时支持的最大连接数等

```conf
events {
    worker_connections  1024;
}
```

上述例子就表示**每个 work process 支持的最大连接数为 `1024`**.

🍊 <font color="red">这部分的配置对 Nginx 的性能影响较大，在实际中应该灵活配置</font>

### (*)Http块

这算是 Nginx 服务器**配置中最频繁的部分**

![](/static/2020-11-18-23-48-46.png)

* 代理、缓存和日志定义等绝大多数功能和第三方模块的配置都在这里
* <font color="red">需要注意的是：http 块也可以包括 **【http 全局块**、**server 块】**</font>

#### Http全局块

http 全局块配置的指令包括

* **文件引入**
* **MIME-TYPE 定义**
* **日志自定义**
* **连接超时时间**
* **单链接请求数上限等**

#### Server块

和虚拟主机有密切关系

![](/static/2020-11-18-23-50-05.png)

### Server-Location指令

![](/static/2020-11-19-17-22-39.png)

## Nginx配置实例：反向代理

### 反向代理1

实现效果：使用 nginx 反向代理，访问 www.123.com 直接跳转到 linux_ip:8080

* ip - www.123.com
  * 多出来一个映射 `www.123.com->linux_ip:80`
  * **改hosts，配置域名-ip映射**
* 暴露反向代理ip - linux_ip:80
* 真实服务ip - linux_ip:8080

---

🍊 步骤

1. 改host（不改也可以，直接请求反向代理服务器）
   1. ![](/static/2020-11-19-00-13-30.png)
   2. 格式 `ip dns`
2. 配置nginx的反向代理配置
   1. 反向代理转发至8080端口
   2. ![](/static/2020-11-19-00-15-59.png)
3. 重新加载nginx
   1. `nginx -s reload`

### 反向代理2

实现效果：使用 nginx 反向代理，**根据访问的路径跳转到不同端口的服务中**
nginx 监听端口为 9001（反向代理服务器端口），

访问 http://linux_Ip:9001/edu/ 直接跳转到 127.0.0.1:8081下的`/edu/`
访问 http://linux_ip:9001/vod/ 直接跳转到 127.0.0.1:8082下的`/vod/`

---

🍊 步骤

1. 两个tomcat，一个端口8080，一个端口8081
   1. tomcat的`server.xml`中改端口
2. 准备两个tomcat的测试页面
   1. 第一个tomcat`webapp`目录下放路由，`webapp/edu/a.html`
   2. 第二个tomcat`webapp`目录下放路由，`webapp/vod/a.html`
3. 修改nginx配置，`nginx.conf`
   1. 添加http-server块
4. 重启nginx，`nginx -s reload`

```conf
server{
    listen 9001;
    server_name 192.168.136.145;

    #正则表达式
    location ~ /edu/{
        proxy_pass 127.0.0.1:8080;
    }
    location ~ /vdo/{
        proxy_pass 127.0.0.1:8081;
    }
}
```

#### Location指令说明

![](/static/2020-11-20-14-54-30.png)

* `=`
  * 请求str与uri严格匹配。匹配成功，就停止继续向下搜索，并处理该请求
* `~`
  * uri包含正则表达式，且区分大小写
* `~*`
  * uri包含正则表达式，不区分大小写
* `^~`
  * 要求 Nginx 服务器找到标识 uri 和请求字
符串匹配度最高的 location 后，立即使用此 location 处理请求，而不再使用 location
块中的正则 uri 和请求字符串做匹配

🍬 如果uri包含正则表达式，则必须有`~`或`~*`标识

## Nginx配置示例：负载均衡

![](/static/2020-11-18-19-05-32.png)
![](/static/2020-11-18-19-12-19.png)

* 提升服务器配置
  * 不能从根本解决问题
* 集群概念 - **单个服务器解决不了，增加服务器的数量，将请求分发到各个服务器上**
  * 即，<font color="red">将负载分发到不同服务器上，也就是负载均衡</font>

---

实现效果

* 浏览器地址栏输入地址 http://192.168.17.129/edu/a.html，负载均衡效果，平均 8080
和 8081 端口中

---

🍊 步骤

* 启动2个tomcat
  * 8080
  * 8081
* 准备测试文件
  * `webapps/edu/a.html`
* 配置`nginx.conf`进行负载均衡配置
  * `http`全局块中添加`upstream`块
  * 修改`http-server-location`块

![](/static/2020-11-20-15-30-51.png)

```conf

upstream myserver{
    server linux_ip:8080;
    server linux_ip:8081;
}
...
server{
    listen  80;
    server_name linux_ip;

    location /{
        proxy_pass http://myserver;
        root html;
        index index.html index.htm;
    }
}

```

Nginx 提供了几种分配方式(策略)：

1. **轮询（默认）**
   1. 每个请求按时间顺序逐一分配到不同的后端服务器，
   2. 如果后端服务器 down 掉，能自动剔除。
2. **weight**
   1. 代表权重，默认为`1`
   2. **权重越高，被分配的客户端越多**
   3. 指定轮询几率，weight和访问比率成正比，用于后端服务器性能不均的情况
   4. ![k](/static/2020-11-20-15-50-38.png)
3. **ip_hash**
   1. 每个请求按照访问ip的hash结果分配
   2. 这样每个访客固定访问一个后端服务器，可以解决session问题
   3. ![kl](/static/2020-11-20-15-53-03.png)
4. **fair（第三方）**
   1. 按照后端服务器的响应时间来分配请求，**响应时间短的优先分配**
   2. ![lp](/static/2020-11-20-15-53-50.png)

## Nginx配置：动静分离

🍬 为什么要动静分离

* **提高访问效率**

动静分离（**严格来说是动态请求&静态请求分开处理**） - 两种实现角度

![](/static/2020-11-20-16-12-18.png)

1. **纯粹把静态文件独立成单独的域名，放在独立的服务器上**
   1. 目前主流推崇的方案；
   2. 静态资源 & tomcat（动态资源）分开部署的
2. **动态跟静态文件混合在一起发布，通过 nginx 来分开**

🍬 通过`location`指定**不同后缀名实现的请求转发**

* `expires`参数设置
  * 使浏览器缓存过期时间，减少与服务器之前的请求和流量
  * <font color="red">给资源设定一个过期时间，无需去服务端验证，直接通过浏览器自身确认是否过期即可。</font>不会产生额外流量，非常适合不经常变动的资源
  * 比如设置`3d`，表示3天之内访问这个url，发送请求，**如果比对服务器，该文件最后更新时间没有改变，则不会从服务器抓取，返回状态码`304`. 如果有修改，则直接从服务器重新下载，返回状态码`200`**

---

🍊 步骤

1. 在linux中准备静态资源，用于访问
   1. ![](/static/2020-11-20-16-40-14.png)
   2. ![](/static/2020-11-20-16-47-34.png)
   3. ![](/static/2020-11-20-16-48-04.png)
2. 配置`location`（不走tomcat）
   1. ![](/static/2020-11-20-16-50-57.png)
3. 测试
   1. ![](/static/2020-11-20-16-53-35.png)
   2. ![](/static/2020-11-20-16-54-37.png)
   3. ![](/static/2020-11-20-16-55-08.png)

## Nginx配置：高可用集群

当一台nginx宕机，还有其他nginx可用(实现高可用，多nginx)
![](/static/2020-11-20-17-15-59.png)

实现高可用
![](/static/2020-11-20-17-17-44.png)

* 一台nginx主服务器master
  * `keepalived`路由作用，通过脚本检测当前nginx是否宕机
* 一台nginx备份服务器backup
  * `keepalived`路由作用，通过脚本检测当前nginx是否宕机
* **请求先给nginx主服务器处理，如果宕机了就分给backup服务器**
* 对外需要一个虚拟ip

🍬 `keepalived`是路由软件服务

---

🍊 步骤

1. 两台服务器
   1. 都安装nginx
   2. 安装keepalived，`yum install keepalived –y`
2. 修改`keepalived.conf`配置文件（**主从配置**）
   1. `/etc/keepalived/keepalivec.conf`
3. ![](/static/2020-11-21-01-10-03.png)
   1. ![](/static/2020-11-21-01-10-38.png)
4. ![](/static/2020-11-21-01-10-51.png)

其中一台服务器的keepalivec.conf的配置

全局配置
![](/static/2020-11-21-01-04-14.png)

* `rooter_id`访问到的主机名

检测脚本
![](/static/2020-11-21-01-04-38.png)

* `script`检测的脚本
* `interval`检测脚本执行的间隔
  * 单位：s
* `weight`权重
  * 如果当前主服务器宕机，则权重改变

检测脚本内容
![](/static/2020-11-21-01-06-34.png)

虚拟IP
![](/static/2020-11-21-01-05-04.png)
![](/static/2020-11-21-01-06-13.png)

* `state`当前为主服务器，or备份服务器
  * `MASTER/BACKUP`
* `interface`绑定的网卡
* `priority`优先级
  * 一般主服务器设为`100`
  * 从服务器设为`90`
* `advert_int`每隔多少s检测服务器是否存活
  * 默认为`1`
* `authentication`校验方式
* `virtual_ipaddress`绑定虚拟IP地址

## 虚拟主机

🍬 适用于多级域名转发，但不适用一台nginx高可用

![](/static/2020-11-21-01-28-28.png)

1.基于端口的虚拟主机（一般不用）

![](/static/2020-11-21-01-29-52.png)

2.基于域名（常用）

![](/static/2020-11-21-01-30-30.png)

* 匹配后直接转发至负载均衡
* 需要修改本地hosts（目前没有条件绑定dns）
  * ![](/static/2020-11-21-01-31-31.png)

## Nginx原理

nginx启动之后有2个进程，worker &master
![](/static/2020-11-21-16-58-01.png)
![](/static/2020-11-21-16-57-23.png)

* `ps -aux | grep nginx` 
* master将任务分配给worker
  * **一个master可能管理多个worker**

### worker是如何工作的

![](/static/2020-11-21-17-00-10.png)

争抢机制，得到具体任务，再通过tomcat进行反向代理

* 一个master可能管理多个worker

### 1个master多个worker的好处

![](/static/2020-11-21-17-03-34.png)

* **可以使用`nginx -s reload`进行热部署（加载）**
  * 不用重载nginx，就可以刷新配置
  * 空闲的worker进行重载，已经有任务的worker不参与争抢
* **每个worker是独立进程，如果其中一个worker出现问题，其他worker独立，继续进行争抢**
  * 实现请求过程中，不会造成服务终中断

### 多少个worker合适

![](/static/2020-11-21-17-14-37.png)

* **worker 数和服务器的 cpu 数相等**是最为适宜的

### worker_connection连接数为多少

![](/static/2020-11-21-17-17-10.png)
![](/static/2020-11-21-17-17-51.png)

🍊 考虑

* 发送一个请求，占用worker几个连接数？
  * 2（静态资源）or4（tomcat）个
