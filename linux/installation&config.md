# 基础篇

- [基础篇](#基础篇)
  - [入门介绍](#入门介绍)
  - [linux与window比较(了解)](#linux与window比较了解)
  - [o:VM&linux(发行版本:centOS)的安装](#ovmlinux发行版本centos的安装)
    - [虚拟机](#虚拟机)
      - [连接配置](#连接配置)
  - [centOS终端使用&联网](#centos终端使用联网)
  - [安装VMTOOLS](#安装vmtools)
    - [设置共享文件夹](#设置共享文件夹)

## 入门介绍

- linux是免费,开源(定制,对源代码进行二次开发)操作系统

- 安全,高效,**稳定**,处理高并发,**很多**企业级项目都部署到linux/unix服务器运行
  - windows开发的部署到windows系统

:blue_heart:使用linux一定要指定发行版

- linux内核(以下为基于内核,**不同厂商进行二次开发定制的不同发行版本的linux**)
  - RedHat
    - centOSE
  - Ubuntu
  - Suse
  - 红旗linux

- 目前操作系统: windows,车载嵌入系统,Android.etc

---

## linux与window比较(了解)

- linux中自由开发软件,基本都是免费

- 安全性:
  - linux高,10%的安全问题(开源),
  - windows(非开源)打补丁安装系统安全,会中病毒木马

- 使用习惯:
  - 结合gui&**command**

---

## o:VM&linux(发行版本:centOS)的安装

:blue_heart:需要安装虚拟机&并且在该虚拟机上安装centOS进行学习

- 先安装VM12,虚拟机 virtual machine

- 在安装linux, centOS 6.8

### 虚拟机

通过VM软件,创建一个**虚拟机空间**(此例centOS操作系统空间)

再在该空间内安装**CentOS操作系统(一系列文件)**

- bios(基本输出系统?开机F2)里修改设置开启虚拟化设备支持(enable)

#### 连接配置

:speech_balloon:

![](/static/2020-09-21-16-59-24.png)

- **桥连接模式**
  - <font color="red">与主机在同一IP字段</font>,虚拟机可以与其他主机通信
  - 可能会造成**IP地址冲突,不够用**
  
- **NAT**
  - 网络地址转换方式
  - **ip不冲突,虚拟机可以访问外网（外界主机无法直接访问虚拟机）**
  - 通过代理ip

- **主机模式**
  - linux是独立主机，不能访问外网
  - 虚拟机ip独立,不能访问外网

:dash:**一般使用NAT模式**

## centOS终端使用&联网

1. 鼠标右键，打开terminal
2. 保证联网，配置网络可以上网
   1. ![](/static/2020-09-21-18-26-09.png)

## 安装VMTOOLS

更好管理VM，如

![](/static/2020-09-21-18-28-46.png)

- **直接粘贴命令**
  - windows 和 centos系统之间
- 可以**设置windows和centos的共享文件夹**

安装步骤

![](/static/2020-09-21-18-29-03.png)

---

### 设置共享文件夹

![](/static/2020-09-21-18-52-45.png)

- 共享文件夹默认放在`/mnt/hgfs/`下