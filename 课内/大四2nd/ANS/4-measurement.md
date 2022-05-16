# Content

* [Content](#content)
* [网络性能：Network Performance](#网络性能network-performance)
* [结合带宽和延迟：Combining bandwidth and latency](#结合带宽和延迟combining-bandwidth-and-latency)
* [带宽延迟积 (BDP):Bandwidth-Delay Product (BDP)](#带宽延迟积-bdpbandwidth-delay-product-bdp)
* [高速网络：High-speed networks](#高速网络high-speed-networks)
* [一些延迟/吞吐量测量：Some latency/throughput measurements](#一些延迟吞吐量测量some-latencythroughput-measurements)
* [why,what,where-Network measurement](#whywhatwhere-network-measurement)
* [为什么测量难：Why is measurement so hard?](#为什么测量难why-is-measurement-so-hard)
* [=================](#)
* [主动测量：Active Measurement](#主动测量active-measurement)
* [网络层主动测量：Network-Layer Active Measurement](#网络层主动测量network-layer-active-measurement)
* [传输层主动测量：Transport-Layer Active Measurement](#传输层主动测量transport-layer-active-measurement)
* [被动测量：Passive Measurement](#被动测量passive-measurement)
* [元素管理 - 简单网络管理协议（SNMP）：Element management – Simple Network Management Protocol (SNMP)](#元素管理---简单网络管理协议snmpelement-management--simple-network-management-protocol-snmp)
* [流测量：Flow Measurements](#流测量flow-measurements)
* [包检测：Packet Monitoring](#包检测packet-monitoring)
* [=================](#-1)
* [主动测量局限：Limitations of Active Measurement](#主动测量局限limitations-of-active-measurement)
* [被动测量局限：Limitations of Passive Measurement](#被动测量局限limitations-of-passive-measurement)
* [Summary](#summary)

# 网络性能：Network Performance

![](/static/2022-05-15-14-05-55.png)

* 。**带宽(吞吐量)和延时（延迟**)。Bandwidth (throughput) and Latency (delay)
  * 。BW:随着时间的推移，可以在网络上传输的比特数（例如，10 Mbls)。
  * 。延迟∶信息从网络的一端到另一端所需的时间(例如，24毫秒)。
  * 。**有时更重要的是知道发送一个信息到网络另一端和返回所需的时间（往返时间--RTT**) 而不是单向延迟(例如用于TCP拥塞控制，见后文)。
* **延迟的组成部分(=传播+传输+排队**)。latency = propagation + transimission + queuing
  * 传播延迟:距离/光速（也取决于媒介)。 Propagation delay: distance / speed-of-light (depends on the medium too)
  * 传输延迟:传输一个单位的数据所需的时间。数据包大小/带宽• Transmission delay: the amount of time it takes to transmit a unit of data; packet_size/bandwidth
  * 排队延迟:存储和转发开交换机中发生的可变延迟 Queuing delay: variable delays happening in store-and-forward switches
    * 难衡量

# 结合带宽和延迟：Combining bandwidth and latency

![](/static/2022-05-15-14-12-31.png)

感知延迟是复制应用程序传输这么多字节的消息而不担心要传输多少数据包需要多长时间 perceived latency

* 较小BW链路，，时间差不多相同

# 带宽延迟积 (BDP):Bandwidth-Delay Product (BDP)

![](/static/2022-05-15-14-18-17.png)

管道，给定时间内可以容纳多少字节 （延迟 * 带宽）

* 延迟 - 管道长
* 带宽 - 管道宽

这个属性很重要，比如可以用来设计TCP拥塞算法

* 主机之前发送了多少bit和字节，期望在收到响应前应该发送多少字节
* 如果发送者没有填满管道，它就没有充分利用网络 If the sender does not fill the pipe, it underutilises the network

# 高速网络：High-speed networks

延迟无法改动，主导性能 Latency, rather than throughput starts dominating performance

![](/static/2022-05-15-14-24-12.png)

# 一些延迟/吞吐量测量：Some latency/throughput measurements

使用UDP， TCP传输某消息要多长时间

* UDP比TCP花费时间更少来传输，所以感知延迟也少

图表表示，大消息大小才能接近链接的理论容量

![](/static/2022-05-15-14-29-20.png)

# why,what,where-Network measurement

![](/static/2022-05-15-14-34-05.png)

* 并考虑不同设计选择对算法的性能影响。 * 看看应用程序等等只有通过测量才能知道。
* 协议是否符合预期，只有通过测量

# 为什么测量难：Why is measurement so hard?

![](/static/2022-05-15-14-37-52.png)

* stateless routers
* IP narrow waist
* violation of end-to-end argument
* decentralised controls
  * 自治系统被激励向他们的邻居或他们的自治系统之外的任何人隐藏细节，并且还存在关于缺乏全局时间概念以及需要非常同步的技术问题

# =================

# 主动测量：Active Measurement

主动向网络注入某种刺激

![](/static/2022-05-15-14-40-54.png)

# 网络层主动测量：Network-Layer Active Measurement 

![](/static/2022-05-15-14-43-00.png)

* ICMP
* ping

# 传输层主动测量：Transport-Layer Active Measurement 

![](/static/2022-05-15-15-27-39.png)

* traceroute

Traceroute Operation and Output

![](/static/2022-05-15-15-28-34.png)

# 被动测量：Passive Measurement

![](/static/2022-05-15-15-34-05.png)

监测运行中的网络流量，记录并分析它；不要向网络注入特殊目的的刺激物 Monitor operational network traffic, record and analyse it; do not inject special-purpose stimulus to the network

# 元素管理 - 简单网络管理协议（SNMP）：Element management – Simple Network Management Protocol (SNMP)

> 近年来，随着网络应用和规模的不断增加，网络管理工作越来越繁重.由于缺乏必要的网络监控手段，有时甚至无法及时发现网络故障的发生.而网络管理的目标是最大限度的保证网络运行的稳定性，提高网络设备的利用率.网络性能.服务质量和安全性.因此，先进的网络管理手段对于保持良好的网络运行状态显得尤为重要.目前多数网络设备都支持SNMP（Simple Network ManagementProtocol,简单网络管理协议），所以可以通过SNMP协议，对网络设备及参数进行实时测量，及时了解网络设备的性能及带宽使用情况，以便实时了解网络当前的运行状态.

MIB management Information Base

![](/static/2022-05-15-15-38-27.png)

# 流测量：Flow Measurements

![](/static/2022-05-15-15-39-11.png)

# 包检测：Packet Monitoring

![](/static/2022-05-15-15-42-15.png)

# =================

# 主动测量局限：Limitations of Active Measurement

![](/static/2022-05-15-15-43-31.png)

# 被动测量局限：Limitations of Passive Measurement

![](/static/2022-05-15-15-44-29.png)

# Summary

![](/static/2022-05-15-15-44-50.png)