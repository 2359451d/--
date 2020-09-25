# Fundamentals

study note

- 磁盘分区&挂载

- [Fundamentals](#fundamentals)
  - [磁盘分区 & 挂载](#磁盘分区--挂载)
    - [linux硬盘说明](#linux硬盘说明)
    - [linux分区](#linux分区)
    - [挂载经典案例](#挂载经典案例)
    - [磁盘情况查询 & 工作实用指令](#磁盘情况查询--工作实用指令)
  - [网络配置](#网络配置)
    - [自动获取](#自动获取)
    - [静态IP](#静态ip)
  - [进程管理](#进程管理)
    - [显示系统执行的进程](#显示系统执行的进程)
    - [终止进程](#终止进程)
    - [查看进程树：pstree](#查看进程树pstree)
    - [服务管理（守护进程）](#服务管理守护进程)
      - [service管理指令](#service管理指令)
      - [查看服务名](#查看服务名)
      - [服务运行级别](#服务运行级别)
      - [chkconfig：给每个服务的运行级别设置自启/关闭](#chkconfig给每个服务的运行级别设置自启关闭)
    - [动态监控进程：top](#动态监控进程top)
    - [监控网络状态: netstat(*)](#监控网络状态-netstat)
  - [RPM软件包管理](#rpm软件包管理)
    - [RPM包简单查询](#rpm包简单查询)
    - [RPM包其他查询](#rpm包其他查询)
    - [RPM包：卸载](#rpm包卸载)
    - [RPM包：安装](#rpm包安装)
  - [yum包管理](#yum包管理)
    - [安装&配置环境变量](#安装配置环境变量)

## 磁盘分区 & 挂载

Disk Partition & Mount

🍊 分区方式

- mbr分区（最大2TB分区，**兼容性好**）
  - **最多4个主分区**
  - **os装在主分区**
  - **扩展分区占一个主分区**
- gtp分区
  - 支持无限多个主分区（但操作系统可能限制，比如 windows下**最多128个分区）**
  - 最大支持**18EB的大容量**（1EB=1024 PB，1PB=1024 TB ）
  - windows7 64位以后支持gtp

🍊 win下的磁盘分区

![](/static/2020-09-25-15-08-23.png)

### linux硬盘说明

![](/static/2020-09-25-15-18-48.png)

### linux分区

![](/static/2020-09-25-15-11-54.png)
![](/static/2020-09-25-15-21-18.png)

- 专门使用“**mount”挂载**，将分区一一对应文件系统（**分区&文件系统关联起来,为每个分区在FS中建立节点**）
  - 相对的，称为**卸载unmount**

🍊 查看所有设备挂载情况（分区情况，）

![](/static/2020-09-25-15-23-23.png)

- `lsblk -f`

### 挂载经典案例

![](/static/2020-09-25-15-28-32.png)

🍊 步骤

![](/static/2020-09-25-15-58-31.png)
![](/static/2020-09-25-15-58-41.png)
![](/static/2020-09-25-15-58-50.png)
![](/static/2020-09-25-15-59-01.png)

- 添加硬盘
- **分区**
  - `fdisk /dev/sdb`
- **格式化**
  - `mkfs -t ext4 /dev/sdb1`分区格式化成ext4类型
- **挂载（临时挂载，重启后失效）**
  - `mount /dev/sdb1/ /home/newdisk`
- 设置自动挂载
  - `vim /etc/fstab`手动配置后
  - `mount -a`执行挂载生效
- 卸载
  - `unmount /dev/sdb1`或`unmout /home/newdisk`

### 磁盘情况查询 & 工作实用指令

`df -h`

- 查询系统整体**disk使用情况**

`du -h /dir`

![](/static/2020-09-25-16-10-14.png)

- 查询**指定目录的占用磁盘情况**
- 查询/opt目录占用情况，`du -ach --max-depth=1 /opt`

🍊 使用指令

![](/static/2020-09-25-16-13-19.png)

- 统计文件夹下文件个数
  - `ls -l /home | grep "^-" | wc -l`
    - `^-`文件以`-`类型开头
    - wc是统计
- 统计目录个数
  - `ls -l /home | grep "^d" | wc -l`
- 统计文件 & 子目录下的文件个数
  - `ls -lR /home | grep "^-" | wc -l`
- **树状显示目录结构**
  - 先安装tree`yum install tree`
  - 直接在目录下实用`tree`就可以使用
    - `tree | less`分页显示

## 网络配置

network configuration

![](/static/2020-09-25-16-34-01.png)
![](/static/2020-09-25-16-34-18.png)
![](/static/2020-09-25-16-35-54.png)
![](/static/2020-09-25-16-36-28.png)
![](/static/2020-09-25-16-37-53.png)

- 可以通过VM，配置虚拟网卡ip

### 自动获取

![](/static/2020-09-25-16-43-13.png)

- 动态ip（**不适用做服务器**）

### 静态IP

![](/static/2020-09-25-16-46-40.png)
![](/static/2020-09-25-17-09-41.png)

- `vi
/etc/sysconfig/network-scripts/ifcfg-eth0`

## 进程管理

![](/static/2020-09-25-17-52-42.png)

### 显示系统执行的进程

![](/static/2020-09-25-17-55-53.png)
![](/static/2020-09-25-18-20-48.png)
![](/static/2020-09-25-18-34-08.png)

- `ps`
- <font color="red">一般使用`ps -aux`</font>或 `ps -aux | grep 特定进程名`
  - `ps -a`显示当前终端所有进程信息
  - `ps -u`以用户格式显示进程信息
  - `ps -x`显示后台进程运行参数

### 终止进程

![](/static/2020-09-25-18-36-21.png)

- `kill PID`通过进程号杀死进程
  - **`kill -9 PID`**常用，**强制进程停止**
- `killall process_name`通过进程名杀死进程，
  - **支持通配符**

🍊 例子

- 踢掉某个非法登录用户
  - `ps -aux | grep sshd`
  - `kill 4010`
- 终止远程登陆服务sshd
  - ![](/static/2020-09-25-18-41-14.png)
- 通过进程名称，终止gedit编辑器
  - `killall gedit`
- 强制终止一个终端
  - ![](/static/2020-09-25-19-03-07.png)
  - `ps -aux | grep bash`
  - `kill -9 PID`

### 查看进程树：pstree

树状形式显示

![](/static/2020-09-25-19-03-51.png)

- `pstree -p`
- `pstree -u`

### 服务管理（守护进程）

本质是进程，**运行在后台**

![](/static/2020-09-25-19-13-40.png)

- 通常，**监听某个端口，等待其他程序请求**
  - 入mysql，sshd，防火墙

#### service管理指令

- `service name [start | stop | restart | reload | status]`
  - <font color="red">注意centos7.0后实用`systemctl`</font>

🍊 例子

- 查看当前防火墙状况
  - `service iptables status`
  - ![](/static/2020-09-25-19-16-41.png)
- 关闭&启动防火墙（**重启后会失效，恢复原来状态**，永久关闭开启需要实用`chkconfig`）
  - `service iptables stop`
  - `service iptables start`

🍊 测试某个端口是否可以使用（是否在监听）

- `telnet ip port`【dos指令】

#### 查看服务名

![](/static/2020-09-25-19-25-41.png)

- `setup`GUI界面查看服，控制服务
- `/etc/init.d/`存放服务名称

#### 服务运行级别

![](/static/2020-09-25-19-29-40.png)

- 查看or修改默认服务运行级别`vi /etc/inittab`
- **常用3&5**
- 服务对于**每一个运行级别**，都会设置**是否自启动**

---

![](/static/2020-09-25-19-36-16.png)

#### chkconfig：给每个服务的运行级别设置自启/关闭

🍬 该指令需要reboot后才会生效

![](/static/2020-09-25-19-38-34.png)

- `chkconfig --list | grep service_name`
  - 筛选出想关注的service
- `chkconfig service_name --list`
  - 列出想关注的service的每个级别对应的自启/关闭状态
- **`chkconfig --level 5 service on/off`**
  - **设置某service在level 5的情况下设置自启or关闭自启**
  - <font color="red">忽略--level参数，所有级别下关闭</font>

---

🍊 例子

![](/static/2020-09-25-19-43-44.png)

- 显示当前os所有服务的各个运行级别的状态
  - `chkconfig --list`
- 查看sshd服务的运行状态
  - `chkconfig --list | grep sshd`
  - `chkconfig sshd --list`
- level5时，关闭防火墙
  - `chkconfig -level 5 iptables off`
- 所有level下，关闭防火墙
  - `chkconfig iptables off`

### 动态监控进程：top

![](/static/2020-09-25-19-52-51.png)
![](/static/2020-09-25-20-01-38.png)

- top会自动刷新
- 按内存使用排序`M`
- 按CPU使用排序`P`
- 按进程号排序`N`

🍊 例子

- 指定刷新间隔（默认3s）
  - `top -d 10`10秒更新

### 监控网络状态: netstat(*)

![](/static/2020-09-25-20-12-33.png)
![](/static/2020-09-25-20-18-22.png)

- 一般使用`netstat -anp`

## RPM软件包管理

![](/static/2020-09-25-20-19-53.png)

- 后缀`.RPM`，**可看为windows的`setup.exe`文件**

### RPM包简单查询

查询**已安装的rpm包**列表

- `rpm -qa|grep xx`
  - 可以过滤关注特定rpm包

🍊 例子

- 查看当前linux是否安装firefox
  - ![](/static/2020-09-25-20-26-30.png)
  - `rpm -qa | grep firefox`

### RPM包其他查询

![](/static/2020-09-25-20-27-27.png)

- `rpm -qa`查看**所有已安装的rpm软件包**
  - `rpm -qa | less/more`分页查看
  - `rpm -qa | grep name`过滤
- `rpm -qi file`查看**已安装软件包详细信息**
- `rpm -ql file`查看**已安装软件包具体安装路径**
- `rpm -qf file`查看**具体文件所属的rpm包是哪个**

### RPM包：卸载

![](/static/2020-09-25-20-32-50.png)

- `rpm -e RPM_name`删除软件包
- `rpm -e--nodeps RPM_name`强制删除软件包
  - **尽量不要使用**

### RPM包：安装

![](/static/2020-09-25-20-36-37.png)

- `rpm -ivh dir`
  - `-i`安装
  - `-v`提示verbose
  - `-h`进度条

## yum包管理

![](/static/2020-09-25-20-49-51.png)

- 自动处理依赖
- 可以从指定服务器（yum服务器）自动下载rpm包，并且安装
- **需要联网**

🍊 查询yum服务器是否有需要安装的软件

- `yum list|grep xx`

🍊 安装指定yum包

- `yum install xx`

---

### 安装&配置环境变量

![](/static/2020-09-25-22-38-20.png)

- 解压`tar -zxvf`
- **配置环境变量**`vim /etc/profile`

![](/static/2020-09-25-23-08-16.png)