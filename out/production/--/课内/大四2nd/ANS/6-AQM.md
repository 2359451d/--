# Content

* [Content](#content)
* [拥塞控制：Congestion Control](#拥塞控制congestion-control)
* [TCP拥塞控制： Congestion control](#tcp拥塞控制-congestion-control)
* [问题：拥塞避免：Congestion avoidance](#问题拥塞避免congestion-avoidance)
* [主动队列管理:Active Queue Management (AQM)](#主动队列管理active-queue-management-aqm)
* [ACQ机制：AQM approaches](#acq机制aqm-approaches)
* [DECbit](#decbit)
* [RED-Random Early Detection](#red-random-early-detection)
* [RED steps](#red-steps)
* [RED - Drop概率分布: Drop probability Distribution](#red---drop概率分布-drop-probability-distribution)
* [RED - 结论verdict](#red---结论verdict)
* [===================](#)
* [缓冲膨胀bufferbloat问题](#缓冲膨胀bufferbloat问题)
* [缓冲膨胀-延迟vs吞吐量：delay vs throughput](#缓冲膨胀-延迟vs吞吐量delay-vs-throughput)
* [Good vs Bad Queues](#good-vs-bad-queues)
* [CoDeL调度算法- Controlled Delay](#codel调度算法--controlled-delay)
* [CoDeL出队伪码：CoDeL dequeue pseudo](#codel出队伪码codel-dequeue-pseudo)
* [FQ_CoDeL](#fq_codel)
* [HTB+FQ_CoDeL:Common Applications Kept Enhanced](#htbfq_codelcommon-applications-kept-enhanced)
* [Summary](#summary)

# 拥塞控制：Congestion Control

![](/static/2022-05-15-18-49-01.png)

# TCP拥塞控制： Congestion control

![](/static/2022-05-15-18-52-30.png)

* cwnd - sliding window, based on AIMD
* slow start
* scongestion avoidance

# 问题：拥塞避免：Congestion avoidance

![](/static/2022-05-15-19-03-06.png)

# 主动队列管理:Active Queue Management (AQM)

![](/static/2022-05-15-19-05-23.png)

# ACQ机制：AQM approaches

![](/static/2022-05-15-19-05-49.png)

# DECbit

![](/static/2022-05-15-19-09-33.png)
![](/static/2022-05-15-19-10-15.png)

路由器如何测量平均队列长度？

![](/static/2022-05-15-19-12-06.png)

# RED-Random Early Detection

![](/static/2022-05-15-19-12-40.png)

* 丢包隐式通知
* 短期burst到达缓冲

![](/static/2022-05-15-19-13-10.png)

# RED steps

![](/static/2022-05-15-19-13-34.png)

# RED - Drop概率分布: Drop probability Distribution

![](/static/2022-05-15-19-14-10.png)

# RED - 结论verdict

![](/static/2022-05-15-19-14-31.png)

# ===================

# 缓冲膨胀bufferbloat问题

> **缓冲膨胀**是一种因数据包过度缓冲而引起的数据包交换网络高延迟原因。缓冲膨胀还可能导致数据包延迟变化（也称为抖动），并降低整体网络吞吐量。当路由器或交换机配置了过大的缓冲区时，对于许多交互式应用程序，例如IP语音（VoIP），在线游戏，甚至普通的网页浏览，即使是非常高速的网络也几乎无法使用。
> 一些通信设备制造商在他们的某些网络产品中不必要地设计了过大的缓冲区。在这种设备中，当网络链路拥塞时，就会发生缓冲膨胀，从而导致数据包在这些超大缓冲区中长时间排队。在先进先出队列系统中，过大的缓冲区会导致更长的队列和更高的延迟，并且不会提高网络吞吐量。

![](/static/2022-05-15-19-16-58.png)
![](/static/2022-05-15-19-18-41.png)
![](/static/2022-05-15-19-20-05.png)

delay intolerant, mission critical applications, buffer is a bottleneck

# 缓冲膨胀-延迟vs吞吐量：delay vs throughput

![](/static/2022-05-15-19-17-50.png)

# Good vs Bad Queues

![](/static/2022-05-15-19-19-17.png)

# CoDeL调度算法- Controlled Delay 

> 它旨在**通过设置缓冲区中网络数据包的延迟限制来克服网络硬件（如路由器）中的 缓冲膨胀** ，改善了随机早期检测（英语：random early detection）（RED）算法的整体性能，解决了Jacobson 开发 RED 时抱有的一些误解，设计上 CoDel 比 RED 更容易管理和配置

![](/static/2022-05-15-19-21-51.png)
![](/static/2022-05-15-19-22-21.png)

# CoDeL出队伪码：CoDeL dequeue pseudo

![](/static/2022-05-15-19-23-12.png)

# FQ_CoDeL

![](/static/2022-05-15-19-23-34.png)

# HTB+FQ_CoDeL:Common Applications Kept Enhanced

![](/static/2022-05-15-19-24-29.png)

# Summary

![](/static/2022-05-15-19-25-16.png)