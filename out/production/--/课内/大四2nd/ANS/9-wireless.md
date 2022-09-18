# Content

* [Content](#content)
* [无线,移动网络：Wireless and Mobile Networks](#无线移动网络wireless-and-mobile-networks)
* [无线链路性质：wireless link properties](#无线链路性质wireless-link-properties)
* [无线网络&标准：wireless networks & standards](#无线网络标准wireless-networks--standards)
* [IEEE 802.11 - WIFI](#ieee-80211---wifi)
* [IEEE 802.11 - MAC](#ieee-80211---mac)
* [IEEE 802.11 - MAC - hidden node problem](#ieee-80211---mac---hidden-node-problem)
* [IEEE 802.11 - frames](#ieee-80211---frames)
* [IEEE 802.11 - LAN & Mobility](#ieee-80211---lan--mobility)
* [蜂窝网络：Cellular Networks](#蜂窝网络cellular-networks)
* [蜂窝网络-4G/5G：Cellular Networks](#蜂窝网络-4g5gcellular-networks)
* [现代蜂窝网络-典型4G配置：exemplar 4G configuration](#现代蜂窝网络-典型4g配置exemplar-4g-configuration)
* [RAN overview](#ran-overview)
* [4G解复用-OFDMA：Multiplexing in 4G](#4g解复用-ofdmamultiplexing-in-4g)
* [4G解复用-调度决策：Multiplexing in 4G-Scheduling decision](#4g解复用-调度决策multiplexing-in-4g-scheduling-decision)
* [Towards 5G](#towards-5g)
* [5G-CQI 信道质量指示(Channel Quality indicator)](#5g-cqi-信道质量指示channel-quality-indicator)
* [5G-EPC分组核心网：Evolved Packet Core](#5g-epc分组核心网evolved-packet-core)
* [5G-3GPP:报文处理流水线 packet processing pipeline](#5g-3gpp报文处理流水线-packet-processing-pipeline)
* [5G: Software Defined RAN – SD-RAN](#5g-software-defined-ran--sd-ran)
* [O-RAN architecture](#o-ran-architecture)
* [SD-RAN概念例子](#sd-ran概念例子)
* [SD-RAN：Near-RT RIC](#sd-rannear-rt-ric)
* [RAN slicing](#ran-slicing)
* [RAN slicing - scheduling](#ran-slicing---scheduling)
* [Summary](#summary)

# 无线,移动网络：Wireless and Mobile Networks

![](/static/2022-05-15-21-19-25.png)

# 无线链路性质：wireless link properties

![](/static/2022-05-15-21-19-59.png)

# 无线网络&标准：wireless networks & standards

![](/static/2022-05-15-21-20-23.png)

# IEEE 802.11 - WIFI

![](/static/2022-05-15-21-21-20.png)

# IEEE 802.11 - MAC

![](/static/2022-05-15-21-21-40.png)

# IEEE 802.11 - MAC - hidden node problem

![](/static/2022-05-15-21-22-45.png)

# IEEE 802.11 - frames

![](/static/2022-05-15-21-23-24.png)

# IEEE 802.11 - LAN & Mobility

![](/static/2022-05-15-21-24-19.png)

# 蜂窝网络：Cellular Networks

![](/static/2022-05-15-21-24-43.png)

# 蜂窝网络-4G/5G：Cellular Networks

![](/static/2022-05-15-21-25-13.png)

* LTE long term evolution
* OFDMA (OFDM)
* Multiple Input Multiple Output (MIMO) antennas & OFDM
* 蜂窝网络使用基于预留（reservation-based）的策略，而Wi-Fi则是基于竞争（contention-based）。这种差异根源于每个系统对利用率的基本假设：Wi-Fi假定网络负载较轻（因此在无线链路空闲时尽量进行传输，在检测到竞争时退出），而4G/5G蜂窝网络假定（并争取）高利用率（因此明确的将可用无线电频谱的不同“份额”分配给不同用户共享使用）。

# 现代蜂窝网络-典型4G配置：exemplar 4G configuration

![](/static/2022-05-15-21-26-43.png)

# RAN overview

![](/static/2022-05-15-21-27-06.png)

# 4G解复用-OFDMA：Multiplexing in 4G

![](/static/2022-05-15-21-28-02.png)

* 数据如何复用也是4G和5G的一个主要区别，它完成了从基本电路交换到基本分组交换蜂窝网络的过渡。
* 4G和5G都基于OFDM（Orthogonal Frequency-Division Multiplexing，正交频分复用），即在多个正交子载波频率上复用数据，每个子载波都是独立调制的。OFDM的价值和效率在于如何选择子载波频率以避免干扰，即如何实现正交性。这个话题超出了这本书的范围。相反，我们采取了一个非常抽象的多路复用的观点，专注于“无线频谱的离散可调度单元”，而不是产生这些可调度单元的信号和调制机制。

# 4G解复用-调度决策：Multiplexing in 4G-Scheduling decision

![](/static/2022-05-15-21-28-58.png)

# Towards 5G

![](/static/2022-05-15-21-29-23.png)

# 5G-CQI 信道质量指示(Channel Quality indicator)

![](/static/2022-05-15-21-31-03.png)

# 5G-EPC分组核心网：Evolved Packet Core

![](/static/2022-05-15-21-32-09.png)

# 5G-3GPP:报文处理流水线 packet processing pipeline

![](/static/2022-05-15-21-33-19.png)

# 5G: Software Defined RAN – SD-RAN

![](/static/2022-05-15-21-34-48.png)

# O-RAN architecture

![](/static/2022-05-15-21-35-03.png)

# SD-RAN概念例子

![](/static/2022-05-15-21-35-29.png)

# SD-RAN：Near-RT RIC

![](/static/2022-05-15-21-35-51.png)

# RAN slicing

![](/static/2022-05-15-21-36-16.png)

![](/static/2022-05-15-21-36-27.png)

# RAN slicing - scheduling

![](/static/2022-05-15-21-36-53.png)

# Summary

![](/static/2022-05-15-21-37-08.png)