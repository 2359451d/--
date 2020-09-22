# 基础

- [基础](#基础)
  - [目录结构](#目录结构)
  - [实操](#实操)
    - [远程登陆linux](#远程登陆linux)
    - [Xshell5：远程控制终端](#xshell5远程控制终端)
    - [HFtp5：远程上传&下载](#hftp5远程上传下载)
    - [vi & vim编辑器](#vi--vim编辑器)
      - [vi & vim常见三种模式](#vi--vim常见三种模式)
      - [快捷键练习](#快捷键练习)
    - [关机 & 重启命令](#关机--重启命令)
    - [登录 & 注销](#登录--注销)
  - [用户管理](#用户管理)
    - [添加 & 删除用户](#添加--删除用户)
    - [指定 & 修改用户密码](#指定--修改用户密码)
    - [查询用户信息](#查询用户信息)
    - [切换用户](#切换用户)
    - [查看当前用户](#查看当前用户)
  - [User Group](#user-group)
    - [Add & Delete Group](#add--delete-group)
    - [Edit Group](#edit-group)
    - [用户&组相关文件](#用户组相关文件)
  - [Running Level](#running-level)
    - [修改默认运行级别](#修改默认运行级别)
    - [如何找回root密码](#如何找回root密码)
  - [Useful Command](#useful-command)
    - [Help](#help)

## 目录结构

> linux的文件系统是**采用级层式的树状目录结构，在此结构中的最上层是根目录“/”**，然后在此目录下再创建其他的目录

🍊 一切皆文件

- **`/bin`**
  - Binary缩写，存放经常使用的命令
- `/sbin`
  - superuser，存放系统管理员使用的系统管理程序
- **`/home`**
  - 存放**普通用户**
  - 每个用户有自己的目录，一般以用户账号命名
- **`/root`**
  - 系统管理员用户的主目录
- `/lib`
  - **系统开机**所需要的**最基本动态链接共享库**
  - 类似win的DLL文件
- `/lost+found`
  - 一般为空，**非法关机后存放文件**
- **`/etc`**
  - 所有**系统管理所需要的配置文件&子目录`my.conf`**
- **`/usr`**
  - 用户应用程序&文件
  - **类似win的program files**
- **`/boot`**
  - 存放的是**启动linux使用的核心文件（连接&镜像）**
- `/temp`
  - 存放临时文件
- `/dev`
  - 管理设备-**类似设备管理器**（硬件映射成文件进行管理）
  - cpu
  - disk
- **`/media`目录**
  - **自动识别一些设备，如U盘，管区**
- `/opt`
  - 给**主机额外安装软件所摆放的目录**，如oracle
- **`/usr/local/`**
  - 给另一个主机额外安装软件的安装目录
  - 一般通过编译源码方式安装
- **`/var`**
  - 存放不断扩充的东西
  - 经常**被修改的目录**放在这里，**如日志文件**
- `/selinux[security-enhanced linux]`
  - SELinux**一种安全子系统，能控制程序只能访问特定文件**
- **`/mnt`**
  - 共享文件夹
  - 让用户临时挂载别的文件系统的，可以将外部的存挂
载在/mnt/上

🍊 内核相关

- `/proc`
  - 虚拟目录，**系统内存的映射（获取系统信息）**
- `/srv`
  - **存放服务启动后需要提取的数据**
- `/sys`
  - 安装了2.6内核中出现的文件系统

🍬 总结

- linux目录有且仅有一个根目录`/`

## 实操

### 远程登陆linux

![](/static/2020-09-21-20-52-07.png)

- 正式项目运行在公网，linux服务器开发小组共享
  - **需要远程登陆到centos进行开发&管理**

🍊 工具

- **`xshell5`**
- `xftp`

### Xshell5：远程控制终端

需要linux开启一个**sshd服务**：监听`22`port

![](/static/2020-09-21-21-09-55.png)

- 等待`xshell5`，等**远程工具访问控制其终端**

![](/static/2020-09-21-21-17-36.png)

### HFtp5：远程上传&下载

![](/static/2020-09-21-21-24-10.png)
![](/static/2020-09-21-21-28-14.png)

### vi & vim编辑器

![](/static/2020-09-21-21-37-48.png)

#### vi & vim常见三种模式

![](/static/2020-09-21-22-09-21.png)

- **正常模式**
  - 默认模式，可以上下左右移动光标
  - 删除字符，删除整行等处理档案内容
  - 复制粘贴，处理文件数据
- **插入/编辑模式**
  - **i**, I ,o ,O ,a A, r,R进入插入/编辑
- **命令行模式**
  - 可以完成读取，存盘，替换，离开vim等操作

🍊 命令模式

- `:wq`写入保存
- `:q`保存退出
- `:q!`不保存退出

#### 快捷键练习

- **拷贝**
  - 当前行`yy`，粘贴`p`
  - 当前行向下5行`5yy`，粘贴`p`
- **删除**
  - 删除当前行`dd`
  - 当前向下5行删除`5dd`
- **查找单词**
  - 命令行下（/进入）`/关键字`enter查找
  - `n`查找下一个
- **设置文件行号**(命令行下)
  - `:set nu`
  - `:set nonu`
- **回到文件顶部**（正常模式下）
  - `gg`
- **回到文件底部**（正常模式下）
  - `G`
- **撤销**（正常模式下）
  - `u`
- **光标移动到指定行**
  - 首先显示行号`:set nu`（命令模式）
  - 输入`20`行数（正常模式）
  - 输入`shift+g`（命令模式）
  - <font color="red">关闭移动到，当前行以下的指定行`数字+enter`</font>

### 关机 & 重启命令

`shutdown`

- **`shutdown -h now`立即关机**
- **`shutdown -h 1`1分钟后关机**
- **`shutdown -r now`立即重启**

`halt`直接关机

`reboot`直接重启系统

`sync`**内存数据同步到磁盘上**

- <font color="red">建议在关机前使用</font>

### 登录 & 注销

![](/static/2020-09-21-22-38-56.png)

- **尽量不用`root`直接登录**
- `logout`注销用户
  - （不占用连接，**对图形界面无效）**
  - **对其他级别，如远程登陆，有效**

## 用户管理

> Linux系统是一个多用户多任务的操作系统，任何一个要使用系统资源的用户，都必须首先向系统管理员申请一个账号，然后以这个账号的身份进入系统

![](/static/2020-09-21-23-04-13.png)

- `/home/`下有**各个用户对应的家目录（组）**
  - **用户登录时，自动登陆进加目录**
  - **管理员登录进`/root/`**
- **linux用户至少属于一个组（家目录）**

### 添加 & 删除用户

🍊 添加用户

`useradd [option] username`

- 如果没给用户指定组，**默认创建一个跟用户名一样的组（家目录）**
- <font color="red">也可以通过`useradd -d <group> <username>`给用户指定家目录（组）</font>
  - 加目录不要提前创建
  - <font color="red">提前创建，走提前创建组&再分配用户进组，如`groupadd groupname`&`useradd -g groupname username`</font>

🍊 删除用户

`userdel username`

- 删除用户&**保留家目录(删除用户时，一般保留家目录)**
  - `userdel username`
- 删除**用户&家目录**
  - `userdel -r username`

### 指定 & 修改用户密码

指定 & 修改密码

- `passwd username`

### 查询用户信息

`id username`

![](/static/2020-09-21-23-35-16.png)

- `uid`user id
- `gid`group id
- `groups`in which group

🍊 if the user does exist

- `No such user`

### 切换用户

can be used for :

- grant the user with **higher privilege（like the root）**
- `su - username`

🍊 example

- create a user 'zf' & specify the pass
  - `useradd zf`
  - `passwd zf`
- switch to zf
  - <font color="red">high level group switch to the lower group, password is not required</font>,reverse case pass required
- switch back to the original user
  - `exit`

### 查看当前用户

check who's current user

- `whoami`

## User Group

system can **uniformly manage** several users who have **same traits**

### Add & Delete Group

ADD: `groupadd groupname` & add a group and a user `useradd -g groupname username`

DELETE: `groupdel groupname`

🍊 example

- add a user `zwj` & assign it to the group `wudang`
  - `groupadd wudang` - get the user group
  - **`useradd -g wudang zwj`**

### Edit Group

edit the user'group

- **`usermod -g groupname username`**

🍊 Example

- move zwj from wudang to other group
  - `groupadd shaolin`new group
  - `usermod -g shaolin zwj`

### 用户&组相关文件

files related to the group & user

![](/static/2020-09-22-22-20-00.png)

- 用户管理相关指令生效后，3个文件会有所改变

----

![](/static/2020-09-22-22-14-42.png)
![](/static/2020-09-22-22-15-44.png)

- **用户配置文件**
  - 用户信息
  - `/etc/passwd`
  - 对应：（x是加密的密码）用户id,组id，家目录，shell

![](/static/2020-09-22-22-17-11.png)

- **口令配置文件**
  - 密码&登录信息（加密的）
  - `/etc/shadow`

![](/static/2020-09-22-22-18-56.png)

- **组配置文件**
  - 组信息
  - `/etc/group`
  - 组名:口令(x，加密看不到):组id（标识号）:组内用户列表（看不到）

## Running Level

7个运行级别

![](/static/2020-09-22-22-23-04.png)

0. **关机**（`init 0`）
1. 单用户（**找回丢失密码**）
2. 多用户无网
3. **多用户有网**
4. 系统未使用保留给用户
5. **图形界面**
6. 系统重启

### 修改默认运行级别

系统运行级别的配置文件`/etc/inittab`

- `id:5:initdefault:`**修改这一行的数字，默认5级**
- <font color="red">切换到指定运行级别的指令`init [012356]`</font>

### 如何找回root密码

进入**单用户模式**，修改root密码

![](/static/2020-09-22-22-58-42.png)

- `init 1`
- <font color="blue">因为进入单用户模式不需要密码</font>

## Useful Command

实用指令

### Help

帮助指令

- `man`
  - 获得帮助信息
- `help`
  - 获得shell内置命令的帮助信息

