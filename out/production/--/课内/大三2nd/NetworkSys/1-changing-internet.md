# The Changing Internet

前面有一部分未补全

Content

* [The Changing Internet](#the-changing-internet)
* [什么是网络系统？Network System](#什么是网络系统network-system)
* [通信协议：Communication Protocols](#通信协议communication-protocols)
* [OSI模型](#osi模型)
* [物理层：Physical Layer](#物理层physical-layer)
  * [信道：Channel](#信道channel)
  * [编码 vs 调制：Encoding vs Modulation](#编码-vs-调制encoding-vs-modulation)
  * [基带信号 vs 宽带信号](#基带信号-vs-宽带信号)
  * [基带编码/传输：Encoding Data onto Wired Channel](#基带编码传输encoding-data-onto-wired-channel)
  * [Nyquist’s theorem-信道最大比特率 & 最大带宽](#nyquists-theorem-信道最大比特率--最大带宽)
  * [基带编码方式：Baseband Data Encoding](#基带编码方式baseband-data-encoding)
    * [NRZ编码-二进制数据/信号](#nrz编码-二进制数据信号)
    * [曼彻斯特编码-二进制数据/信号](#曼彻斯特编码-二进制数据信号)
  * [无线（模拟）信道编码-载波调制：Encoding Data onto Wirele sChannels](#无线模拟信道编码-载波调制encoding-data-onto-wirele-schannels)
  * [载波调制方式-（数字信号）调制模式：Modulation Scheme](#载波调制方式-数字信号调制模式modulation-scheme)
  * [扩频通信-单频信道/单载波局限：Spread Spectrum Communication](#扩频通信-单频信道单载波局限spread-spectrum-communication)
  * [香农定理（噪音，信道最大bps）-物理链路特性&局限性：Physical Link Characteristics and Limitations](#香农定理噪音信道最大bps-物理链路特性局限性physical-link-characteristics-and-limitations)
* [数据链路层：Data Link Layer](#数据链路层data-link-layer)
  * [装帧&寻址：Framing&Addressing](#装帧寻址framingaddressing)
  * [介质访问控制&CSMA技术：Media Access Control](#介质访问控制csma技术media-access-control)
    * [解决共享信道信号冲突](#解决共享信道信号冲突)
    * [CSMA技术实现媒体访问控制](#csma技术实现媒体访问控制)
    * [信号冲突2：长距离传输，高传播时延](#信号冲突2长距离传输高传播时延)
    * [局限性](#局限性)
* [网络层：Network Layer](#网络层network-layer)
  * [IP协议](#ip协议)
    * [IPv5?](#ipv5)
  * [IP寻址：Addressing](#ip寻址addressing)
  * [自治系统：AS](#自治系统as)
  * [IGP，BGP,EGP](#igpbgpegp)
  * [AS&域间选路协议：Inter-Domain Routing Protocol](#as域间选路协议inter-domain-routing-protocol)
  * [域间选路协议&BGP：Inter-Domain Routing & BGP](#域间选路协议bgpinter-domain-routing--bgp)
  * [转发&尽力而为传输:Forwarding & best-effort](#转发尽力而为传输forwarding--best-effort)
* [传输层：Transport Layer](#传输层transport-layer)
  * [UDP](#udp)
  * [TCP](#tcp)
    * [TCP包格式：Packet Format](#tcp包格式packet-format)
    * [TCP3次握手：Handshake](#tcp3次握手handshake)
    * [TCP拥塞控制：Congestion Control](#tcp拥塞控制congestion-control)
* [高级层协议：Higher Layer Protocols](#高级层协议higher-layer-protocols)
  * [会话层-管理连接：Session Layer-Manage connections](#会话层-管理连接session-layer-manage-connections)
  * [表示层：Presentation Layer](#表示层presentation-layer)
  * [应用层：Application Layer](#应用层application-layer)
  * [互操作性-协议标准重要性：Importance of Protocol Standards](#互操作性-协议标准重要性importance-of-protocol-standards)
* [互联网变迁-架构设想：The Changing Internet-Architectural Assumptions](#互联网变迁-架构设想the-changing-internet-architectural-assumptions)
  * [设备地址不能持久化：Changes in Addressing & Reachability](#设备地址不能持久化changes-in-addressing--reachability)
    * [IPV4地址用尽：Address Exhaustion](#ipv4地址用尽address-exhaustion)
    * [IPV6部署慢：Slow Deployment](#ipv6部署慢slow-deployment)
    * [IPv4不足-NAT穿越：Establish Connectivity in Modern Internet](#ipv4不足-nat穿越establish-connectivity-in-modern-internet)
    * [双栈协议不足](#双栈协议不足)
  * [移动性使维护长期连接困难：Increasing Mobility](#移动性使维护长期连接困难increasing-mobility)
  * [巨型企业&中心化：Hypergiants & Centralisation](#巨型企业中心化hypergiants--centralisation)
  * [如何保证实时流量：Real-time Traffic](#如何保证实时流量real-time-traffic)
  * [安全假设：Pervasive Monitoring and Trust in the Network](#安全假设pervasive-monitoring-and-trust-in-the-network)
  * [网络创新：Innovation in the Network](#网络创新innovation-in-the-network)

# 什么是网络系统？Network System

**一组合作的自主计算设备，交换数据以执行一些应用目标** A cooperating set of autonomous computing devices that exchange data to perform some application goal

- 网络有四个方面
- **通信**--如何在一个单一的链接中交换信息？ Communication – how is information exchanged across a single link?
- **联网** - 如何将链接连接起来形成一个广域网？ Networking – how are links connected to form a wide area network?
  - 第二，是联网。我们如何能将多个网络链接结合起来，形成一个广域网络，围绕一栋楼、一个校园或一个地区。
- **互联网**--网络如何从互联网上互连起来？数据如何在该网络中路由？ Internetworking – how are networks interconnected to from an internet? How is data routed across that network?
  - 第三是互联网化。我们如何能将多个网络连接在一起，形成一个互联网？ 如何使多个独立运行的网络一起工作，就像它们是一个单一的全球网络一样？
  - 数据如何在这个网络集合中被路由，以到达目的地？
- **传输**--终端系统如何可靠地交换数据？Transport – how do end-systems reliably exchange data?
  - 还有一个运输的问题。终端系统如何确保数据以适当的可靠性在这个或这些网络中传递，以满足应用的需要？

---

**谈论计算设备，因为网络化系统并不局限于传统的PC、笔记本电脑或服务器。例如，连接到网络的智能手机和平板电脑远比笔记本电脑和服务器多。但也有众多的传感器和控制器构成了物联网：摄像头、智能灯泡、加热控制器、气象站、医疗设备、工业自动化等等**。

网络是由越来越多的设备组成的，并且必须满足它们的不同需求。

这些设备是自主的。每个设备都独立行动，没有中央控制，可以选择发送什么数据，以及何时发送。而这些设备都在运行不同的应用程序，有不同的要求。它们从网络中需要不同的东西，并需要不同的网络协议来支持它们的需求

So, what is a networked system?  A networked system is a set of  cooperating, autonomous, computing devices that exchange data  to perform some application goal.  I talk about computing devices, because networked  systems are not limited to traditional PCs,  laptops, or servers. There are far more  smart phones and tablets connected to the  network than there are laptops and servers,  for example. But there are also the  numerous sensors and controllers that form the  Internet of Things: cameras, smart light bulbs,  heating controllers, weather stations, medical devices,  industrial automation, and so on.  The network is comprised of an increasingly  diverse set of devices, and has to  meet their diverse needs.  These devices are autonomous. Each device acts  independently, with no central control, and can  choose what data to send, and when  to send it. And these devices are all running different  applications, with different requirements. They require different  things from the network, and need different  network protocols to support their needs.  There are four aspects to the network.  The first is communication. How can two  devices, that are connected to a single  link, reliably exchange data.  Second, is networking. How can we combine  multiple network links to form a wide  area network, around a building, a campus,  or a region.  Third is internetworking. How can we connect  multiple networks together to form an internet?  How can multiple independently operated networks work  together to act as-if they were a  single global network? How can data be  routed across this collection of networks to  reach its destination?  And, finally, there is the problem of  transport. How do the end systems ensure  that data is delivered across this network  or networks with appropriate reliability to meet  the needs of the application?

# 通信协议：Communication Protocols

![](/static/2021-04-29-12-03-57.png)

:orange: **网络系统从根本上说是关于通信协议的**。 一个发送方试图通过一些**通信渠道与**一个或多个接收方进行对话

* 它通过发送信息来实现这一目的，这些信息必须符合通信渠道的限制。

:orange: 信道限制约束了通信速度和可靠性（信道不是无限快或完全可靠的） Channel constraints bound communications speed and reliability

- **消息的语法、语义和通信模式构成了一个网络协议--HTTP、TCP、IP等**。 The message syntax, semantics, and communication patterns form a network protocol – HTTP, TCP, IP, etc.
  - 网络协议有明确的语法，描述了可以发送的信息的结构和格式
  - 它们也有明确的语义，定义了信息的含义、发送的顺序和通信的模式
  - **消息的语法和语义**共同定义了一个网络协议，如HTTP、TCP/IP等
  - 每个协议都有一个特定的目的，解决一个特定的问题
- **协议可以被组成和分层，以提高抽象水平** Protocols can be composed and layered to raise level of abstraction
  - 协议可以被组合起来，并相互分层，以逐步提高抽象水平，并为应用程序提供更复杂的服务

Networked systems are fundamentally about communications protocols.  A sender is trying to talk to  one of more receivers, via some communications  channel.  It does this by sending messages,  that have to fit within the constraints  of the communications channel.  These constraints provide limits on the speed  and reliability of transmission. The channel is  not infinitely fast or perfectly reliable.  The messages being sent across the channel  have a well defined format. Much like  programming languages, network protocols have well defined  syntax that describes the structure and format  of the messages that can be sent.  They also have well defined semantics that  define the meaning of the messages,  the order in which they are sent,  and the patterns of communication.  Together the syntax and semantics of the  messages define a network protocol, such as  HTTP, TCP/IP, etc. Each protocol has a particular purpose and  solves a particular problem. Protocols can be  combined, and layered on one another,  to gradually raise the level of abstraction,  and provide more sophisticated services to the  applications.

# OSI模型

![](/static/2021-04-29-12-17-02.png)

分层协议设计的标准模型--真实的网络并不遵循这个模型--它们更加复杂，违反层、子层和隧道是很常见的。A standard model of layered protocol design – real networks don’t follow this model – they’re more complex, and layer violations, sublayers, and tunnels are commonplace

- 分层模型对于帮助构建关于网络的讨论非常有用 A layered model is extremely useful for helping structure discussions about networks

开放系统互连参考模型，即OSI模型，是思考协议分层的一种常见方式。 OSI模型将网络构造为一组七层。 

* 在协议栈的底部是**物理层**。 对于两个直接连接的设备，两个终端系统，如幻灯片所示，**物理层代表了互连的手段。如果是有线链接，则是电缆的类型，或者是无线电通道的细节**
  * 物理层的目的是在设备之间交换数据
* 在此之上是**数据链路层**。
  * 链路层将数据结构化为信息，识别设备，并协调每个设备可以发送的时间，以便它们共享通道的访问。
  * **如果一个网络包括一个以上的物理链路，可以在网络中插入被称为交换机的设备**。
    * 交换机在数据链路层工作，适应信息构架和仲裁对不同通道的访问，以便将不同的链路连接起来。 
    * **这方面的例子包括以太网交换机，它将多个以太网链接连接在一起，形成一个更大的以太网网络**，以及在以太网和Wi-Fi网络之间架设桥梁的Wi-Fi基站。
* OSI模型中的第三层是**网络层**。 
  * 网络层支持**多个独立运行的网络的互连**。
  * 它**抽象了不同类型的链接层的细节，并将不同的网络结合在一起**，使人产生一种错觉，以为有一个单一的全球互联网。 
  * 互联网协议，即IPv4和IPv6，是网络层协议的例子，**而将不同网络连接在一起的设备被称为路由器**。
* 在网络层之上，**传输层确保数据在终端系统之间以适当的可靠性传递**。
  * 传输控制协议，即TCP，是一个广泛使用的传输协议的例子。 
* 最后，**会话层、演示层和应用层支持多个传输连接的协调，描述使用的数据格式，并支持应用语义**。

OSI参考模型是分层协议设计的一个标准模型。 但是，重要的是要意识到，真实的网络并不遵循OSI模型。 **没有一个已部署的网络系统是以这种方式结构的七层**。 真正的系统是更复杂的。在某些情况下，特别是在堆栈的上层，各层合并在一起，它们之间的界限并不清楚。 在其他情况下，系统被设计成有更多的层，或有明确定义的子层，以支持那些不能整齐地融入OSI模型所定义的层边界的功能。

* **隧道解决方案也很常见，它将较低层的数据封装起来，在较高层的连接中传输**。
* 虚拟专用网络（VPN）就是一个很好的例子，**它将网络层的数据以IP数据包的形式，在传输层的连接中进行隧道传输到网络的其他部分**，给人一种设备在其他地方有物理连接的假象。没有严格的层级递进。

我们谈论OSI模型，是因为它是一个非常有用的方法来组织关于网络的讨论，而不是因为它反映了任何特定网络的结构。

The Open Systems Interconnection Reference Model,  the OSI Model, is a common way  of thinking about protocol layering.  The OSI model structures the network as  a set of seven layers.  At the bottom of the protocol stack  is the physical layer.  For two devices, two end systems,  that are directly connected, as shown on  the slide, the physical layer represents the  means of interconnection. The type of cable,  if it's a wired link, or the  details of the radio channel.  The purpose of the physical layer is  to exchange data between the devices.  Above this sits the data link layer.  The link layer structures that data into  messages, identifies the devices, and coordinates when  each device can send, so they share  access to the channel.  If a single network comprises more than  one physical link, devices known as switches  can be inserted into the network.  Switches operate at the data link layer,  adapting the message framing and arbitrating access  to the different channels, in order to  bridge the different links together.  Examples of this include Ethernet switches,  that connect multiple Ethernet links together to  form a larger Ethernet network, and Wi-Fi  base stations that bridge between Ethernet and  Wi-Fi networks.  The third layer in the OSI model  is the network layer.  The network layer supports the interconnection of  multiple independently operated networks. It abstracts away  the details of the different types of  link layer, and combines different networks into  one, to give the illusion that there  is a single global internetwork.  The Internet protocols, IPv4 and IPv6,  are examples of network layer protocols,  and the devices that connect different networks  together are known as routers.  Above the network layer, the transport layer  ensures that data is delivered with appropriate  reliability between end systems. The Transmission Control  Protocol, TCP, is a widely used example  of a transport protocol.  Finally, the Session, Presentation, and Application layers  support the coordination of multiple transport connections,  describe the data formats used, and support  application semantics.  The OSI reference model is a standard  model for layered protocol design.  It's important to realise, though, that real  networks don't follow the OSI model.  No deployed networked system has seven layers  structured in this way.  Real systems are more complex. In some  cases, especially around the upper layers of  the stack, the layers merge together,  and the boundaries between them are unclear.  In other cases, systems are designed with  more layers, or with clearly defined sub-layers,  to support features that don't fit neatly  into the layer boundaries defined by the  OSI model.  Tunnelling solutions, that encapsulate data from a  lower layer, and transmit it within a  higher layer connection are also common.  Virtual Private Networks, VPNs, are a good  example of this, and take network layer  data in the form IP packets,  and tunnel them inside a transport layer  connection to some other part of the  network, giving the illusion that a device  is physically connected elsewhere. There’s not a  strict progression of layers.  We talk about the OSI model because  it's an extremely useful way to structure  discussion about networks, not because it reflects  the structure of any particular network.

# 物理层：Physical Layer

![](/static/2021-01-14-15-40-40.png)

**物理层描述了通信通道的属性。它是电气工程师的领域；是电缆、光纤和无线电传输的领域**。

物理层允许传输原始比特流，进行通信 enables communication - transmission of raw bitstream

* 在考虑物理层时，我们讨论**信道的物理属性，比特编码到信道的方式，以及信道的容量和错误率**
* 电缆类型，线类型（电气特性） What type of cable or wireless link do you use?
  * 我们首先要问的是，该链路是有线的还是无线的？
  * 如果是有线链路，我们就考虑使用的电缆或光纤的类型。**我们问在电缆上施加什么电压，或在光纤上发送什么频率的激光**
    * **比特是如何被编码**为电压或激光强度的变化的
  * 如果信道是无线的，我们要考虑使用什么类型的天线，**传输功率，载波频率，以及用于将数据编码到载波上的调制方案**
* 如何编码bit至信道上？How to encode bits onto that channel?
  * 基带编码 baseband encoding
  * 载波调制 carrier modulation
* 信道容量？What is the capacity of the channel?
  * 考虑到这些细节，我们就可以估计信道的容量，并对传输链路的性能进行物理限制建模。

The physical layer describes the properties of  the communications channel. It’s the realm of  the electrical engineer; of cables, optical fibres,  and radio transmission. When considering the physical layer, we discuss  the physical properties of the channel,  the way bits are encoded onto the  channel, and the capacity and error rate  of the channel.  We begin by asking whether the link  is wired or wireless?  If it is a wired link,  we then consider the type of cable  or optical fibre used. We ask what  voltage is applied to the cable,  or what frequency of laser light is  sent down the fibre. And we ask  how the bits are encoded as variations  in that voltage or in the intensity  of the laser.  If, instead, the channel is wireless,  we consider what type of antenna is  used, the transmission power, the carrier frequency,  and the modulation scheme used to encode  data onto the carrier wave. Given these details, we can then estimate  the capacity of the channel, and model  the physical limitations of the performance of  the transmission link.

## 信道：Channel

> 信号的传输媒介
>
> 一条完整的通信线路，包括发送信道 & 接收信道

:orange:分类

* 传输信号类型分类
  * **模拟信道** - 传输模拟信号，如电话线
  * **数字信道** - 传输数字信号，如宽带
* 传输介质分类
  * **无线信道** - 通过无线电波传输
    * **载波调制**（模拟信道） - 载波调制
  * **有线信道** - 通过光纤，双绞线传输
    * **基带传输/编码**（数字信道） - 常用编码，曼彻斯特编码

## 编码 vs 调制：Encoding vs Modulation

（基带）编码

* 将数据/信号转为**数字信号**

（载波）调制

* 将（数字/基带）数据/信号转为**模拟信号**

![](/static/2021-01-14-16-52-09.png)

## 基带信号 vs 宽带信号

![](/static/2021-01-14-16-55-50.png)

## 基带编码/传输：Encoding Data onto Wired Channel

有线（数字）信道编码

> **信号通常直接编码至数字信道，通过改变信道的性质（电线的电压，光纤的镭射密度），形成基带** signal usually directly encoded onto the channel, by varing some property of channel(voltage applied to an electrical cable, intensity of laser in optical fibre), and occupies baseband region
>
> **当使用有线连接时，无论是电缆还是光纤，要传输的信号通常直接编码到通道上**。When using a wired link, whether an  electrical cable or an optical fibre,  the signal to be transmitted is usually  directly encoded onto the channel.
> 也就是说，施加在**电缆上的电压，或照射在光纤上的激光的亮度，是以直接针对传输的信号方式改变的** That is,  the voltage applied to an electrical cable,  or the brightness of a laser shining  down an optical fibre, is changed in  a way that directly corresponds to the  signal to be transmitted.

基带信号(基带传输)

* 将数字信号`0`和`1`使用不同的电压表示,然后再送到 数字信道上去传输
  * **直接在数字信道上传输信号** --- 基带传输
* 基带信号源
  * 信源的信号，计算机输出的文字，图像等数据信号都是基带信号

## Nyquist’s theorem-信道最大比特率 & 最大带宽

![](/static/2021-01-14-16-03-52.png)

:orange:**信道的最大比特率** bitrate

* Nyquist’s theorem 奈奎斯特定律
  * 信息速率
  * 理想低通信道信息极限传输速率=`2Blog 2V` 比特/秒（信息传输速率，比特率，bps，表示单位时间内传递的比特数）
  * <font color="red">对于理论上的无噪音nosiy-free channel线路，速率可以到达无穷大(理想状态下)</font>【**只有在无噪音的通道上才能达到最大的数据速率**  Maximum data rate only reached with a noise-free channel】
* `Rmax ≤ 2B log2 V`
  * B = **信道带宽** bandwidth of the channel
    * `2B` - 码元速率（传码率，Baud，表示单位时间内传输的码元数）
  * V = **每个符号的离散值的数量** number of discrete values per symbol
    * 如电压，0，1 V=2

---

:orange:带宽bandwidth - 两个定义：

1.某信号具有的 频率差范围（频带宽度），单位HZ（模拟信号）

* **信号将占据一定的频率范围，被称为其带宽**。这是以`Hz`为单位测量的，直接对应于信号的复杂性和信息含量 The signal will occupy a certain frequency  range, known as its bandwidth. This is  measured in units of Hertz, Hz, and directly corresponds to the complexity and  information content of the signal.
* **在一个给定的时间间隔内传输的信息越多，带宽就越大** The more  information being transmitted in a given time  interval, the greater the bandwidth.
* 直接应用于信道的信号占据了所谓的基带频率范围：从0赫兹开始的范围，一直到信号的最大带宽 A signal  directly applied to a channel occupies what’s  known as the baseband frequency range:  the range starting at 0 Hz,  and reaching up to the maximum bandwidth  of the signal.

2.网络中某信道传送数据的能力，单位时间内某信道能通过的最高信号数。单位 bps 【特殊情况。。。有线电缆，bps=max带宽】

本质一样，链路 带宽 越宽，传输能力越高

:orange: **每个信道也有一个它可以传输的最大带宽。这取决于通道的物理特性**  Every channel also has a maximum bandwidth  it can transmit. This depends on the  physical characteristics of the channel.

* 例如，在双绞线上可以发送的最大带宽取决于**电缆的长度**、捻子的松紧度和**电线的厚度** For example,  the maximum bandwidth that can be sent  over a twisted pair electrical cable depends  on the length of the cable,  the tightness of the twists, and the  thickness of the wires.

:orange: **只有当信号的带宽小于信道的带宽时，信道才能够传输一个特定的信号**。 A channel is only able to transmit  a particular signal if the bandwidth of  the signal is less than the bandwidth  of the channel.

* **根据信道的物理特性，有一个可以传输数据的最大速率**。 There’s a maximum rate  at which data can be transmitted,  depending on the physical characteristics of the  channel.
* 该最大数据速率是由奈奎斯特定理决定的。
  * 该定理指出，信道可支持的**最大数据速率`Rmax<=2 B Log2 V bps`**，
    * 其中B是信道的最大带宽，
    * **V是每个符号可采取的不同值的数量**。
      * 对于二进制数据，每个符号可以有两个值之一，它可以是0或1，因此，V等于2。
      * 在这种情况下，"Log2 V "项的值被评估为1，**最大数据速率直接对应于信道带宽**

That maximum data rate is determined by  Nyquist’s theorem. This states that the maximum  data rate a channel can support,  Rmax, cannot exceed 2 B Log2 V  bits per second, where B is the  maximum bandwidth of the channel, and V  is the number of different values each  symbol can take. For binary data, each symbol can have  one of two values, it can be  0 or a 1. Accordingly, V is  equal to two. The value of “Log2  V” term, in this case, evaluates to  one, and the maximum data rate directly  corresponds to the channel bandwidth.

---

:candy:注意，载波调制技术也可以用于有线传输

## 基带编码方式：Baseband Data Encoding

> 信号通常直接编码至数字信道，通过改变信道的性质（电线的电压，光纤的镭射密度），形成基带(之后在数字信道上进行基带传输) signal usually directly encoded onto the channel, by varing some property of channel(voltage applied to an electrical cable, intensity of laser in optical fibre), and occupies baseband region

有许多不同的基带数据编码方法，其中NRZ和曼彻斯特编码是最简单的。不同的编码方法对增加的复杂性进行了权衡，以获得更好的性能。There are many different methods of baseband  data encoding, of which the NRZ and  Manchester encodings are the simplest. The different  encodings trade-off increased complexity for better performance.

### NRZ编码-二进制数据/信号

:orange:常用编码 - **NRZ编码**

![](/static/2021-01-14-16-08-50.png)

* (单极)不归零码 Non-return to zero encoding
  * 当发送**二进制数据时**，NRZ编码**直接将信号编码到信道上**
    * 例如，如果二进制值1要通过电缆发送，则在电缆上施加高电压，而施加低电压来发送二进制值0 If the binary value 1 is to  be sent over an electrical cable,  for example, a high voltage is applied  to the cable, whereas a low voltage  is applied to send the binary value  zero.
    * 接收器只需测量电压，并直接将其转换为二进制值 The receiver simply measures the voltage,  and directly translates it into binary values.
  * 只使用一个电压值，高电平-1，没电压-0
* **问题**：<font color="red">如果长期运行发送相同的值，波形无变化-容易错记bit数</font>【**即长时间的1或0的运行会导致信号在很长一段时间内保持相同的值** the problem that long runs  of ones or zeros result in signals  that maintain the same value for long  periods of time.】
  * 这个例子有四个连续的1比特的运行，稍后是四个连续的0比特的运行，**每一个都会导致一个信号在相当长的时间内不变** The example has a  run of four consecutive one bits,  followed a little later by a run  of four consecutive zeros, and each results  in a signal that’s unchanging for a  significant period of time.
  * 在高数据率和同一数值的长时间连续序列中，可能很难测量信号不变的确切时间。这就<font color="red">导致了误算，即接收器认为发送的比特比预期的多一个或少一个，从而产生了破坏性信号</font> At high data rates, and with long  consecutive sequences of the same value,  it can be difficult to measure the  exact time for which the signal is  unchanging. This leads to miscounting, where the  receiver believes one more, or one less,  bit was sent than intended, giving a  corrupt signal.

### 曼彻斯特编码-二进制数据/信号

避免NRZ编码的问题

:orange:曼彻斯特编码

![](/static/2021-01-14-16-11-50.png)
![](/static/2021-01-14-16-13-00.png)

* **它将每一个要发送的比特 编码为 一对数值** This encodes every bit to be sent  as a pair of values.
  * 一个二进制的1被发送为信号强度的高到低的转换，而一个二进制的0被发送为低到高的转换 A binary  1 is sent as a high-to-low transition  in the signal strength, whereas a binary  0 is sent as a low-to-high transition.
  * 这**避免了NRZ编码的误码问题**，因为无论发送的是什么数据，信号总是在变化，<font color="red">但代价是需要两倍的转换，因此使用两倍的带宽</font> This avoids the miscounting problem of NRZ  encoding, since the signal always changes irrespective  of the data being sent, but at  the cost of requiring twice as many  transitions, and hence using twice the bandwidth.
* 一个时钟周期表示1个bit
* 0 - low->high
* 1 - high->low
* 需要双倍带宽（只要有数据输入就会跳转），避免了bit数错误计量

## 无线（模拟）信道编码-载波调制：Encoding Data onto Wirele sChannels

宽带信号（宽带传输），

* <font color="red">将**基带（数字）信号**载波调制后，将信号频率范围移动到较高频段</font>，形成频分复用模拟信号，**便于在模拟信道上传输** --- 宽带传输

![](/static/2021-01-14-16-31-39.png)

* 载波调制（模拟信道的宽带传输） --- 应用于**无线连接**
  * 当在无线信道上进行数据编码时，使用的是载波调制而不是基带编码
* **允许信道中存在多个信号，调制至不同频段** allows multiple signals on a channel, modulated onto carriers of different frequency
  * <font color="red">载波调制将信号所占用的频率范围向上移动，因此它是以载波频率为中心的。</font> 信号不再占用0-B(最大带宽) Hz的基带范围，而是被**转移到以载波频率为中心的频率范围**。Carrier modulation shifts the frequency range occupied  by a signal up, so it’s centred  on the carrier frequency. Instead of occupying  the baseband range, from 0 - B  Hz, the signal is shifted to occupy  a frequency range centred on the carrier  frequency.
  * 如，FM/AM广播站，频段
* **这是通过改变载波的某些属性来匹配正在发送的信号** This is done by varying some  property of the carrier wave to match  the signal being sent. 
  * <font color="red">接收器调整到载波频率，并测量载波的变化以提取信号</font> The receiver tunes  into the carrier frequency, and measures the  variation in the carrier wave to extract  the signal.
  * <font color="blue">也可用于有线连接，ADSL&有线电共用一条线</font> Can be used on wired links – this is how ADSL and voice telephones share a phone line
* 与有线链接一样，可以发送的信号的带宽也有限制，（传输）效率受以下影响 Performance affected by carrier frequency, transmission power, modulation scheme, type of antenna, etc
  * 载波频率 carrier frequency
  * 传输强度 transmissio power
  * 调制模式 modulation scheme
  * 天线类型 type of antenna

:orange: 广播电台使用载波调制，在不同的频率上进行传输。数字传输的原理是一样的，只是传输的是数据，而不是音乐。Broadcast radio stations use carrier modulation to  transmit on different frequencies. The principle is  the same for digital transmission, except that  it’s data being transmitted, rather than music.

## 载波调制方式-（数字信号）调制模式：Modulation Scheme

有三种类型的载波调制可供使用

[参考](https://blog.csdn.net/shulianghan/article/details/108022986)

> **调制**:将数字(基带)信号转为模拟信号

:orange:调幅ASK，AM

![](/static/2021-01-14-17-04-20.png)

* 0 - 没有幅度
* 1 - 有幅度
* **振幅调制会改变载波的响度**，以直接匹配正在传输的信号。Amplitude modulation varies the loudness of the  carrier wave to directly match the signal  being transmitted.
* AM无线电的工作方式也是如此。 AM radio works the same  way.
* **振幅调制很简单，但性能很差，因为无线电噪声以信号响度变化的形式出现，破坏了接收的数据** Amplitude modulation is simple, but can  perform poorly, because radio noise takes the  form of changes in the loudness of  the signal that corrupt the received data.

:orange:调频 FSK，FM

![](/static/2021-01-14-17-06-11.png)

* 0 - 低频率
* 1 - 高频率
* **频率调制会改变载波的频率以匹配信号**。Frequency modulation varies the frequency of the  carrier to match the signal.
* 在幻灯片上的例子中，它在对应于二进制零的高频率和对应于二进制一的低频率之间切换。 In the  example on the slide, it switches between  a high frequency that corresponds to binary  zeros and a lower frequency that corresponds  to a binary ones.
* 调频广播以类似的方式工作，改变频率以匹配正在传输的语音或音乐。 FM radio works  in a similar way, varying the frequency  to match the speech or music being  transmitted.
* **这比振幅调制稍微复杂一些，但对噪音和干扰的抵抗力更强**。 This is slightly more complex than  amplitude modulation, but more resistant to noise  and interference.

:orange:调相 PSK，PM

![](/static/2021-01-14-17-08-46.png)

* 0 - 余弦波
* 1 - 正弦波
* 最后，相位调制在波形的周期中向前或向后移动以表示不同的符号。 。Finally, phase modulation shifts forwards or backwards  in the cycle of the waveform to  indicate different symbols.

:orange: **真正的系统倾向于使用调制技术的组合，也许同时改变振幅和相位，以提高数据速率** Real systems tend to use a combination  of modulation techniques, perhaps varying both the  amplitude and phase, to increase the data  rate.

* :orange:QAM调制
* 调幅 & 调相结合

---

![](/static/2021-01-14-17-16-03.png)

## 扩频通信-单频信道/单载波局限：Spread Spectrum Communication

:orange:**单频信道易受干扰(调制在单一载波上的无线电信号很容易受到干扰**) Single frequency channels prone to interference(Radio signals modulated onto a single carrier  wave are prone to interference.)

* 这是因为以特定频率发送的信号往往会被周围的车辆、树木和人阻挡，或被天气和其他无线电传输阻挡，而以不同频率的载波发送的信号可能不受影响 This is  because signals sent at particular frequencies tend  to be blocked by vehicles, trees,  and people moving around, or by the  weather and other radio transmissions, while signals  sent on a carrier at a different  frequency may be unaffected.
  * 这种干扰的强度可以迅速变化 This strength of  this interference can change rapidly.
* 为了避免这个问题，**许多无线链路使用一种被称为扩频通信的技术**，To avoid this problem, many wireless links  use a technique known as spread spectrum  communication,
  * 每秒多次，重复改变载波频率【其中载波频率每秒改变数次，遵循发送方和接收方都知道的伪随机序列】 where the carrier frequency is changed  several times per second, following a pseudo-random  sequence known to both the sender and  receiver.
    * 噪音不可能影响所有频段 Mitigate by repeatedly changing carrier frequency, many times per second: noise unlikely to affect all frequencies
  * 使用伪随机序列来选择每个时段使用的载波频率 Use a pseudo-random sequence to choose which carrier frequency is used for each time slot
    * 伪随机数生成器的种子在发送方和接收方之间共享秘密，确保安全 Seed of pseudo-random number generator is shared secret between sender and receiver, ensuring security

:orange: **扩频通信限制了干扰的影响，因为传输将迅速从性能不佳的信道中切换出来。 它增加了很多复杂性，因为发送方和接收方都需要不断地改变载波频率，并同步他们使用的频率，但大大改善了性能**  Spread spectrum communication limits the impact of  interference, because the transmission will quickly switch  away from a poorly performing channel.  It adds a lot of complexity,  since both sender and receiver need to  continually change the carrier frequency, and synchronise  what frequencies they use, but greatly improves  performance.

:candy:例子  Example: 802.11b Wi-Fi uses spread spectrum using several frequencies centred at either 2.4 GHz or 5 GHz

* 例如：802.11b Wi-Fi使用以2.4 GHz或5 GHz为中心的几个频率的扩频

## 香农定理（噪音，信道最大bps）-物理链路特性&局限性：Physical Link Characteristics and Limitations

> 真实网络链路易受噪音局限：电气，广播干扰，光纤缺点
> real-world network links are imperfect and subject to noise(electrical,radio interference, imperfections in optical fibre)

:orange: **【噪声】对特定通道上可实现的【数据速率的影响】是可以预测的**。

* 无论该噪声是由电或无线电干扰、光纤中的缺陷或其他手段造成的，情况都是如此。 
* 在最简单的情况下，**假设噪声对传输所使用的所有频率的影响程度相同，信道的最大数据速率可以通过香农-哈特利定理来确定**。
  * 该定理指出，最大数据速率Rmax=信道的带宽B * （1+信号强度S除以噪声量N）的对数。
  * 例如，对于无线链接，它们取决于载波频率、天线类型、天气、发送方和接收方之间是否有障碍物，以及是否有其他同时进行的无线电传输。信号强度取决于在发射器上应用的功率大小。这使得发送方可以在电池寿命和性能之间进行权衡，通过更慢的传输速度来节省电力。
* The impact of noise on the data  rate achievable over a particular channel can  be predicted. This is the case whether  that noise is due to electrical or  radio interference, imperfections in an optical fibre,  or other means.  In the simplest case, where it’s assumed  the noise affects all frequencies used by  the transmission to the same extent,  the maximum data rate of the channel  can be determined using the Shannon-Hartley theorem.  This states that the maximum data rate,  Rmax, is equal to the bandwidth of  the channel, B, multiplied by the log  of 1 plus the strength of the  signal, S, divided by the amount of  noise, N.  The bandwidth and the amount of noise  depend on the channel and the environment.  For example, for a wireless link,  they depend on the carrier frequency,  the type of antenna, the weather,  the presence of obstacles between the sender  and receiver, and whether there are other  simultaneous radio transmissions. The signal strength depends  on the amount of power applied at  the transmitter. This allows the sender to  trade-off battery life for performance, saving power  by transmitting more slowly.

:orange:香农定理 - Shannon-Hartley theorem

* MAX信道信息传送速率（bps）和信道信噪比及带宽的关系
  * ` Rmax = B log2 (1 + S/N) `
  * `B`带宽
  * `S`信号强度
  * `N`噪音强度
  * <font color="blue">因为是MAX信息传送速率，也可看为【信道容量】</font>
  * <font color="red">假设高斯噪音【Gaussian noise】 - 对每个频段干扰相同</font>

:orange:物理限制（因素）影响MAX信息传送速率

* `B&N`带宽，噪音取决于信道性质，种类
* `S`信号强度，取决于传输强度 - 以电池寿命换取性能

# 数据链路层：Data Link Layer

> 数据链路层提供装帧，寻址，媒体访问控制
> data-link layer provides framing, addressing, media access control

**我们已经看到，物理层实现了通信。它允许发送方和接收方在信道上交换一连串的数据位，但对这些位没有赋予任何意义** We have seen that the physical layer  enables communication. It allows the sender and  receiver to exchange a sequence of bits  of data across a channel, but assigns  no meaning to those bits.

* 【数据链路层开始为物理层提供的比特流提供结构】，**将物理层原始bitstream装帧成有意义的frame** structure the raw bitstream into meaningful frames of data
  * 它提供成帧，将比特流分割成单个信息，并提供检测和可能纠正这些信息传输中的错误的能力
* 检测，**纠错（不主要**)传输错误 detect&correct transmission errors
* **识别设备（MAC）--寻址** identify devices - Addressing
  * 它提供寻址，给每个设备一个标识符，可用于指示什么设备发送了信息，以及什么设备或多个设备应该对该信息采取行动
* **仲裁信道媒体访问** arbitrate access to the channel
  * 最后，**数据链路层提供媒体访问控制。它对信道的访问进行仲裁，以确保不止一个设备不会试图在同一时间发送，并确保每个设备得到其公平的传输份额**

The data link layer starts to provide  structure to the bitstream provided by the  physical layer.  It provides framing, splitting the bitstream into  individual messages, and gives the ability to  detect, and possibly correct, errors in the  transmission of those messages.  It provides addressing, giving each device an  identifier that can be used to indicate  what device sent the message and what  device, or devices, should act on the  message.  And, finally, the data link layer provides  media access control. It arbitrates access to  the channel, to make sure that more  than one device doesn’t try to send  at the same time, and to ensure  that each gets its fair share to  transmit.

## 装帧&寻址：Framing&Addressing

![](/static/2021-01-14-18-17-39.png)

:orange: 数据链路层的一个关键作用是**将比特流分成有意义的数据帧，并识别发送和接收这些帧的设备**。

* 例如，如果我们考虑一个以太网链接，比特流被分割成包含若干不同元素的帧
* 首先是**起始代码** Start code
  * Start code 提供了同步和时间恢复。这是一个有规律的模式，只在帧的开始发送，并允许接收器精确测量帧的发送速度
  * Preamble 这是一个序言，包含一个只在信息开始时出现的特定模式，用于提醒接收者一个新信息正在开始。
* **头** Header
  * 头部包括一个**源地址**，指定发送帧的设备的身份，以及一个**识别接收器的目标地址**
    * 源地址和目的地址识别发送和接收信息的设备
    * 每个地址的大小为48位，即6个字节，并且是全球唯一的。
    * 地址被分成两个24位的部分，一个表示供应商，一个表示设备
      * 在这个例子中，00:14:51的供应商ID表示苹果公司，04:27:ea的设备ID表示我正在录制这个讲座的笔记本电脑
  * 这些信息之后是一个**长度**字段，表示后面的数据量
* **数据**，长度不超过1500字节
  * 以太网帧的数据包包含协议栈中下一层的数据，即网络层
* 一个**循环冗余码**结束了数据包，使接收方能够检查是否正确接收了该帧

现代操作系统开始在每次连接到网络时随机改变以太网地址，以限制跟踪和提高隐私。

A key role of the data link  layer is to separate the bitstream into  meaningful frames of data, and to identify  the devices that are sending and receiving  those frames.  For example, if we consider an Ethernet  link, the bitstream is split up into  frames that contain a number of different  elements. First is the start code. This is  a preamble, containing a particular pattern that  only occurs at the start of a  message, and is used to alert the  receiver that a new message is starting.  This is followed by some header information.  The header comprises a source address,  specifying the identity of the device sending  the frame, and a destination address that  identifies the receiver. These are followed by  a length field, indicating the amount of  data to follow. The data comes next, up to 1500  bytes in length.  And, finally, a cyclic redundancy code concludes  the packet, allowing the receiver to check  if the frame was received correctly.  The start code provides for synchronisation and  timing recovery. It’s a regular pattern that’s  only sent at the start of a  frame, and allows the receiver to precisely  measure the speed at which the frame  is being sent.  The source and destination addresses identify the  devices sending and receiving the message.  Each is 48 bits, six bytes,  in size, and is globally unique.  The addresses are split into two 24-bit  parts, one indicating the vendor, and one  indicating the device. In this example,  the vendor ID of 00:14:51 indicates Apple,  and the device ID of 04:27:ea indicates  the laptop on which I’m recording this  lecture.  Modern operating systems are starting to randomly  change the Ethernet addresses each time they  connect to the network, to limit tracking  and improve privacy.  Finally, the data packet of the Ethernet  frame contains data for the next layer  up in the protocol stack, the network  layer.

---

* 收方纠错
  * 如果收到的frame无差错，会从中提取出IP数据报交给网络层，否则丢弃这个帧（或者重传/海明码）
* 标头 - 帧同步
  * 判断bit流开始&结束位置，解决clock skew/discrrupted[物理层的不可靠性除了bit corrupt]，这样能判断帧是否完整，不完整应该丢弃帧并retransmit

## 介质访问控制&CSMA技术：Media Access Control

### 解决共享信道信号冲突

数据链路层的**媒体访问控制**。

* 如果你有一个**在多个设备之间共享的信道**，如一个共同的无线链路或一个共享的电缆，那么就<font color="red">有两个设备可以尝试同时发送的风险。</font>If you have a channel that’s shared  between multiple devices, such as a common  wireless link or a shared cable,  then there’s the risk that two devices  can try to send at once.
* 例如，在这张幻灯片中，**设备A和B都试图同时向设备C发送一个信息**。
  * 中间的图片显示了由设备A和B发送的信号，每个设备都使用NRZ编码发送。这些是完全正常的信号。In this slide, for example, devices A  and B both try to send a  message to device C at the same  time.  The centre image shows the signals sent  by the devices A and B,  each of which is sending using NRZ  encoding.
  * 右边的图片显示了在C处收到的信号，这是两个信号的**叠加，是两个信号相加的结果，对接收者来说是损坏的**，毫无意义。These are entirely normal signals.  The right-hand image shows what’s received at  C. This is the superposition of the  two signals, the result of adding the  two signals together, and is corrupt and  meaningless to the receiver.
* **媒体访问控制是避免这种碰撞问题**。Media access control is the problem of  avoiding such collisions.

![](/static/2021-01-14-18-25-17.png)

* **因为同时传输可能会破坏信号**
  * 发送帧时控制访问，以免产生冲突
* <font color="blue">是解决当局域网中共用信道的使用产生竞争时，如何分配信道的使用权问题</font>
  * 避免传输冲突（由高传播时延造成的），导致信息重叠，利用CSMA/CD技术（随机型介质访问控制协议）在低传播时延时，发送前先监听信道

---

### CSMA技术实现媒体访问控制


![](/static/2021-01-14-18-49-49.png)

:orange:为什么传播时延会有影响？

* A开始传输，B监听（A未到达之前，监听到无冲突，**传输时延**），B传输
* **冲突出现**，传输中信息重叠
  * <font color="red">传播时延越低，出现冲突概率越低</font>

**执行媒体访问控制的一个常见方法是使用一种被称为带碰撞检测的载波感应多路访问技术，即CSMA/CD**。A common way to perform media access  control is using a technique known as  carrier sense multiple access with collision detection,  CSMA/CD.

* 其原理是，**当一个设备想要发送时，它首先倾听另一个设备是否已经在发送**。 The idea is that when a device  wants to send, it first listens to  see if another device is sending already.
  * 如果另一个传输正在进行，那么它在再次尝试之前会等待。If another transmission is active, then it  waits before trying again.
  * 如果它没有听到任何声音，它就开始发送数据。 If it doesn’t hear anything, it starts  to send data.
  * <font color="red">在发送的同时，它听着看另一个设备是否也开始发送</font>。 While sending, it listens  to see if another device also starts  to send.
    * 如果发生这样的碰撞，设备就会停止发送，等待，然后再试一次。 If such a collision occurs,  the device stops sending, waits, and tries  again.
* **碰撞通常不会发生，因为设备在发送前会进行监听**，<font color="red">但由于信息穿越网络所需的时间，总是有一些信息重叠的可能</font>。Collisions don’t usually happen, because devices listen  before sending, but there’s always some chance  that messages might overlap because of the  time it takes a message to traverse  the network.
  * 正如我们在右图中看到的，如果设备A开始发送，同时B也在监听，**由于A的信息还没有到达它那里，所以没有听到任何信息，然后开始发送，那么就有可能出现两个信息碰撞并被破坏**。As we see from the  diagram on the right, if device A  starts to send and simultaneously B listens,  hears nothing because the message from A  hasn’t reached it yet, and starts sending,  then there’s the risk that both messages  collide and are corrupted.

### 信号冲突2：长距离传输，高传播时延

:orange: **碰撞更可能发生在长距离的网络中，有很大的传播延迟，但也可能发生在任何有共享信道的网络中**。Collisions are more likely to occur in  long distance networks, with large propagation delays,  but can happen in any network with  a shared channel.

* 如果发生碰撞，设备应该等待多长时间才能尝试重新发送消息？If a collision occurs, how long should  a device wait before trying to re-send  a message?
  * 设备**不应该总是等待相同的时间**。这样做会有两个设备被卡住的风险，每个设备都重复地试图发送，等待相同的时间，然后在一个循环中再次碰撞。Well, devices shouldn’t always wait for the  same amount of time. Doing so would  run the risk that two devices get  stuck, each repeatedly trying to send,  waiting the same time, then colliding again  in a loop.
  * **等待的时间应该是随机的，以避免确定性的碰撞**。The amount of time  to wait should be randomised, to avoid  deterministic collisions.
    * <font color="red">如果使用了这种随机化，但在等待后又发生了碰撞，这表明网络很忙</font>。If such randomisation is used, but another  collision occurs after waiting, this suggests that  the network is busy. 
    * 两个设备不可能随机地等待相同的时间，所以随后的碰撞表明有许多设备在试图发送。It’s unlikely that  two devices will randomly wait for the  same time, so a subsequent collisions suggest  there are many devices trying to send.
    * <font color="blue">因此，发送方应该增加每次碰撞后的等待时间，以减少网络的整体负荷</font>。A sender should therefore increase the time  it waits after each collision, to reduce  the overall load on the network.
      * 许多数据链路层协议在每次重复碰撞后将等待时间延长一倍，在成功传输时重新设置。Many data link layer protocols double the  wait time after each repeated collision,  resetting when a successful transmission occurs.

---

![](/static/2021-01-14-18-35-26.png)

:orange:<font color="red">传播延时低时</font>，先**监听**后发送 【CSMA/CD技术】

* 如果链路空闲 - link is idle
  * 立即发送数据 - send data immediately
* 如果链路有其他传输活动，或出现冲突 - if another trasmission is active or collision occurs
  * **停止发送，等待** stop sending, wait
    * <font color="blue">等待时间应随机 - 以避免决定性的重复冲突</font>
      * 选择一个随机的初始回避时间间隔x秒±50%
    * <font color="blue">等待时间应随着冲突次数增加而增加</font>
      * 重复冲突 - 意味信号拥堵 signal congestion
      * 降低传输速率使网络得以恢复
      * 每一次重复冲突出现，直到成功前，x->2x s
  * **重新传输** retransmit

:orange:提高利用率

* 主动传输不受冲突干扰 Active transmissions not disrupted by collisions
* 只有信道可用时，发送者回避 Only the new sender backs-off if the channel is active

### 局限性

这种方法被称为CSMA/CD，被广泛使用，包括在以太网和WiFi网络中。 它的一个结果是，**设备共享一个信道，它们能以多快的速度发送信息，取决于网络的繁忙程度。【这给网络上发送的许多信息的时间引入了一些不可预测的因素**】。

This approach, known as CSMA/CD, is widely  used, including in Ethernet and WiFi networks.  A consequence of it is that devices  share access to a channel, and how  quickly they can send a message depends  on how busy is the network. This introduces some element of unpredictability into  the timing of many messages sent over  the network.

# 网络层：Network Layer

网络层 & 网络协议

![](/static/2021-01-14-19-49-02.png)

* 全球网络间协议，漏斗形协议栈
* 单一标准的网络层协议 IP协议
  * **分组交换网络，尽力而为服务**
  * 统一网络，主机寻址
  * 统一端到端连接 - 受限于防火墙政策
* 多种传输 & 应用层协议
* 支持多种链路层技术
* 解耦端到端功能与转发 Decouples end-to-end functionality from
per-hop functionality

---

网络层是协议栈中的网络连接点。 使用通用的网络层协议，可以使我们把组成互联网的网络的运行与在互联网上运行的应用程序的运行脱钩。 它允许每个网络对使用什么样的数据链路和物理层技术做出自己的选择，因为这种选择被共同的网络层从应用和传输协议中隐藏起来。

底层网络是以太网、WiFi、光纤还是其他什么都不重要，因为这些差异被上层协议所掩盖。

:orange: 同样地，**使用一个共同的网络层使得部署不同的应用和传输协议变得容易**。

* 下层必须交付网络层数据包，但不知道这些数据包中包含的应用数据类型。它们无法判断被传送的数据包是否包括电子邮件信息、网页、电话、流媒体视频或其他内容。
  * **这种方法非常灵活，可以很容易地支持新的物理层和数据链路层技术，以及新的传输协议和应用，只要它们能够为共同的网络层提供数据包，或在其上运行**。

其缺点是，**网络层没有为任何一个应用进行优化。 它强调通用性和灵活性，以支持许多不同的用途，而不是为任何特定的用例提供最佳性能**。

* 在互联网上，网络层被称为互联网协议，即IP。 这是众所周知的TCP/IP协议套件的IP部分。
* 互联网协议提供了一种通用的方式来**识别网络上的设备，使用的是所谓的IP地址**。
* 它提供了**路由算法**来引导数据包在网络上从源头到目的地。
* 它以<font color="red">最大努力的方式转发这些数据包，接受网络可能是不可靠的</font>。

:orange: **互联网协议的核心是提供统一的连接**--<font color="red">任何主机都可以向任何其他主机发送数据，但要遵守防火墙政策--但不提供质量保障</font>。

The network layer is the internetworking point  in the protocol stack.  The use of a common network layer  protocol allows us to decouple the operation  of the networks that comprise an internet,  from the operation of the applications that  run on that internet.  It allows each network to make its  own choice about what sort of data  link and physical layer technologies to use,  because that choice is hidden from the  applications and transport protocols by the common  network layer. It doesn’t matter whether the  underlying network is Ethernet, WiFi, optical fibre,  or something else, because the differences are  hidden from the upper-layer protocols.  Similarly, the use of a common network  layer makes it easy to deploy different  applications and transport protocols. The lower layers  must deliver network layer packets, but are  unaware of the type of application data  contained in those packets. They cannot tell  whether the packets being delivered comprise an  email message, a web page, a phone  call, streaming video, or whatever.  This approach is very flexible, and makes  it easy to support new physical and  data link layer technologies, and new transport  protocols and applications, provided they can deliver  packets for, or operate over, the common  network layer.  The disadvantage is that the network layer  is not optimised for any one application.  It emphasises generality and flexibility to support  many different uses, rather than providing optimal  performance for any particular use case.  In the Internet, the network layer is  known as the Internet Protocol, IP.  This is the IP part of the  well known TCP/IP protocol suite.  The Internet Protocol provides a common way  to identify devices on the network,  using what’s known as an IP address.  It provides routing algorithms to direct packets  across the network from source to destination.  And it forwards those packets in a  best effort manner, accepting that the network  may be unreliable.  At its core, the Internet Protocol provides  uniform connectivity – any host can send  data to any other host, subject to  firewall policy – but makes no quality  guarantees.

## IP协议

:orange:IPV4 - 目前使用的互联网协议有两个版本。最常用的版本是IP版本4。这是在1983年被引入到后来的互联网中的。There are two versions of the Internet  Protocol in use. The most commonly used  version is IP version 4. This was  introduced into what became the Internet in  1983. The figure shows the format of an  IPv4 packet.

![](/static/2021-01-14-20-22-33.png)

图中显示了一个IPv4数据包的格式。**这是在数据链路层数据包的有效载荷数据部分发送的**，如以太网或WiFi帧，IPv4数据包的不同部分按照所示的顺序，从左到右，从上到下，从版本号、报头长度、DSCP字段等开始发送，最后是传输层数据 This is sent in the  payload data section of a data link  layer packet, such as an Ethernet or  WiFi frame, with the different parts of  the IPv4 packet being sent in the  order shown, left-to-right, top-to-bottom, starting with the  version number, header length, DSCP field,  and so on, concluding with the transport  layer data.

* **IPv4数据包的关键部分是源地址和目的地址，每个地址的大小为32位，表示数据包从哪个网络接口发出，以及应该被送到哪个网络接口**。Key parts of an IPv4 packet are  the source and destination addresses, each 32  bits in size, that denote the network  interfaces from which the packet was sent,  and to which it should be delivered.
  * 使用32位的地址字段允许2到32位的可能地址，大约40亿，这对目前的互联网来说是不够的。The use of 32 bits for the  address fields allows 2-to-the-power-32 possible addresses,  around 4 billion, which is not enough  for the current Internet.
  * 这就是转向IPv6的动机。 This is the  motivation to switch to IPv6.
* 除了寻址之外，**IPv4还提供了碎片标识符、碎片偏移量、"不要碎片"（DF）和 "更多碎片"（MF）字段**，In addition to addressing, IPv4 provides the  fragment identifier, fragment offset, “don’t fragment” (DF),  and “more fragments” (MF) fields 
  * 允许将大的IPv4数据包分割成碎片，以便在只能传送小数据包的网络上传送。to allow  large IPv4 packets to be split into  pieces for delivery over networks that can  only deliver small packets.
* 它还包括一个**差异化服务代码点（DSCP）字段**，允许数据包要求网络进行特殊处理。It also includes a Differentiated Services Code  Point (DSCP) field to allow packets to  request special treatment by the network.
  * 例如，一个携带**视频会议或游戏数据的数据包可能要求低延迟传输**，而一个携带背景软件更新的数据的数据包可能表明它是低优先级的。For example, a packet that carries video  conferencing or gaming data might ask for  low latency delivery, while one carrying data  that’s part of a background software update  might indicate that it’s low priority.
* **存活时间（TTL）字段**可以防止数据包在网络中永远循环，以防路由问题导致数据包的循环，而**报头校验**可以检测传输错误。The time-to-live (TTL) field prevents packets from  circulating forever in the network in case  a routing problem causes them to go  around in a loop, and the header  checksum detects transmission errors.
* **上层协议标识符**确定了IPv4头之后的传输层数据的格式。Finally, the upper layer protocol identifier identifies  the format of the transport layer data  that follows the IPv4 header.
  * 这通常表明传输层数据是一个TCP段或一个UDP数据报。 This usually  indicates that the transport layer data is  a TCP segment or a UDP datagram.
* 高数据传输率（bps，每秒传输的bit数）时，装帧困难
* 扩展性局限

---

:orange:IPV6

![](/static/2021-01-14-20-27-31.png)

IPv6是为了解决IPv4地址太小的问题而设计的。 它用128位地址取代了IPv4中使用的32位地址。 这大大增加了可以添加到网络中的设备数量，因为每增加一个比特，可用的地址数量就增加一倍。IPv6 was designed to solve the problem  that the IPv4 addresses are too small.  It replaces the 32 bit addresses used  in IPv4 with 128 bit addresses.  This vastly increases the number of devices  that can be added to the network,  since each additional bit doubles the number  of addresses that are available.

* 此外，**IPv6简化了报头**。In addition, IPv6 simplifies the header.
  * 它**取消了对网络内分片**的支持，这在IPv4中是存在的，因为它很难有效地实现，而是要求主机调整它们发送的数据包的大小以匹配网络路径。It removes the support for in-network fragmentation,  that was present in IPv4, since it  was difficult to implement efficiently, and instead  requires the hosts to adjust the size  of the packets they send to match  the network path.
* 它还**删除了报头校验**，因为它通常与数据链路层提供的校验是多余的。It also removes the  header checksum, since it’s usually redundant with  the checksum provided by the data link  layer.
* 截至2020年底，谷歌报告称，他们约有三分之一的用户通过IPv6访问谷歌。大型内容分发网络Akamai的统计数据显示，来自印度的约60%的网络连接是通过IPv6的，约50%来自美国、德国、比利时、希腊、台湾和越南，约35%来自英国。 IPv6花了很长时间才开始部署，但在过去几年中其使用速度大大加快。As of late 2020, Google reports that  about a third of their users access  Google over IPv6. Statistics from Akamai,  a large content distribution network, report around  60% of connections to their network from  India are over IPv6, around 50% from  the US, Germany, Belgium, Greece, Taiwan,  and Vietnam, and around 35% from the  UK.  IPv6 took a long time to start  seeing deployment, but its use has greatly  accelerated over the last few years.

* 更大的地址空间
* 网络层间无帧信息
* 去掉无用校验和
* 简化标头格式

### IPv5?

:orange:IPV5

![](/static/2021-01-14-20-31-39.png)

如果我们有了IPv4和IPv6，你可能会问那IPv5呢？ 好吧，通过ARPA网络（互联网的前身）进行的分组语音实验始于20世纪70年代初，由南加州大学信息科学研究所的Danny Cohen开发的网络语音协议。这项工作最终导致了互联网流协议，**即ST-II，它是一个实验性的多媒体流协议**，主要在20世纪80年代和90年代初开发。 **ST-II与IPv4平行运行，并在其标题中使用IP版本5**。 **ST-II没有被广泛部署，但它帮助在分组网络上的多媒体传输方面形成了一些重要的想法**。 哪些方面做得好，哪些方面做得不好，都是如此。 Steve Casner和Eve Schooler与Danny Cohen一起在ISI工作，他们**根据ST-II和早期协议的经验，帮助领导了下一波多媒体传输协议【RTP和SIP的发展】。RTP和SIP在现代视频会议服务中得到了极为广泛的应用**，如Zoom、Webex和Microsoft Teams，并作为当今移动电话网络的基础。If we have IPv4 and IPv6,  you might ask what about IPv5?  Well, experiments with packet voice over the  ARPAnet, the precursor to the Internet,  started in the early 1970s, with the  Network Voice Protocol developed by Danny Cohen  at the University of Southern California’s Information  Sciences Institute. This work eventually led to  the Internet Stream Protocol, ST-II, that was  an experimental multimedia streaming protocol developed mostly  in the 1980s and early 1990s.  ST-II ran in parallel to IPv4,  and used IP version 5 in its  header.  ST-II was not widely deployed, but it  helped prototype a number of important ideas  around multimedia transport over packet networks.  Both what worked well, and what didn’t.  Steve Casner and Eve Schooler, who both  worked with Danny Cohen at ISI,  helped lead the development of the next  wave of multimedia transport protocols, RTP and  SIP, based on experiences with ST-II and  earlier protocols. RTP and SIP are extremely  widely used in modern video conferencing services,  such as Zoom, Webex, and Microsoft Teams,  and as the basis for today’s mobile  phone networks.

* 网络语音协议 https://www.ietf.org/rfc/rfc741.txt
* 这演变成了互联网流协议ST-II，被分配到IPv5--1979年至1995年期间开发的实验性多媒体流协议，但从未广泛部署过
* ST-II+规格：http://www.ietf.org/rfc/rfc1819.txt

## IP寻址：Addressing

![](/static/2021-01-14-20-36-20.png)

IPv4和IPv6的地址大小不同，但其他方面的工作原理相似。

* 在这两个协议中，**一个IP地址代表一个特定网络接口的位置**。
  * 如果一个设备有一个以上的网络接口，例如，如果它是一个同时拥有5G和WiFi的智能手机，它将有一个IP地址用于每个接口。
  * 它也可能**同时有IPv4和IPv6地址**分配给它的一些或所有网络接口。
    * 而且，在某些情况下，可以为一个接口分配一个以上的每种类型的地址。
* <font color="red">重要的是，IP地址确定了一个网络接口连接到网络的位置</font>。
  * **它并不识别设备**，如果一个设备移动到不同的地方，它将获得一个不同的IP地址。
* IPv4和IPv6地址都包括一个**网络部分**和一个**主机部分**。
  * 网络部分，通常被称为**网络前缀，用于识别设备所连接的网络**
  * **而地址的主机部分则用于识别该网络上的一个特定连接点**。
  * 地址中识别网络部分的部分和留给主机的部分各不相同，不同的网络被分配不同数量的地址空间
    * 举例来说，计算科学学院在Lilybank花园和Boyd Orr大楼运营一个IPv4网络，IPv4地址的前20位是网络部分，最后12位是主机部分。
    * 在130.209.240.0到130.209.255.255范围内的IPv4地址，都共享相同的初始20位前缀，都在该网络内。
    * 学校也有一个IPv6网络，其工作原理与此类似，尽管地址更长。
* 在广域范围内，**互联网流量被路由到其目的地，只看IP地址的网络部分**。
  * 只有当它<font color="red">到达目的地网络时，网络中的路由器才会检查地址的主机部分，以找到应该接收数据包的设备。</font>
* 最后，重要的是要记住，网络层不使用诸如example.com这样的名称。在互联网上传递的**流量包含源和目的IP地址，并根据这些IP地址进行路由**。
  * **发送方主机将目的地域名析为一个IP地址**，并将该地址放入IP数据包的目的地地址栏。
  * 网络只使用该IP地址来传递该数据包。
  * 解析名称的DNS只是在互联网上运行的另一个应用程序，并不是其运作的根本。

---

:orange:IP地址 - 编码网络接口位置(encoding location of netwrok interface)

* 如果某主机有**多个网络接口**（如WIFI&以太网），会对应**多个IP地址**
* 主机的每个接口都支持IPV4和IPV6
* 主机的每个指定类型的接口可以有多个IP地址

:orange:DNS域名系统是应用层面概念，不涉及于网络层

* 将域名与IP地址相互映射

:orange:子网掩码

* 就是将某个IP地址划分成网络地址和主机地址两部分

## 自治系统：AS

![](/static/2021-01-14-20-50-09.png)

:orange:AS - Antuonomous System 自治系统 （互联网中每个网络都是单独管理和运作的，即AS）

* 指使用统一内部路由协议的一组网络（较大LAN），每个AS网络分别管理 administered separately
  * <font color="blue">每个AS有权,自主决定在本系统中应采取何种路由协议</font>
* AS之间
  * 不同技术 different technologies
  * 不同政策 different policies
  * AS之间，AS与内部使用者之间，都互不信任 mutual distrust between AS&peer, AS&customers

---

## IGP，BGP,EGP

:orange: IGP -内部网关协议

* 一个自治系统网络内部进行路由信息的通信使用内部网关协议（IGP，Interior Gateway Protocols）

:orange: BGP -外部网关协议

* 各个自治系统网络之间是通过边界网关协议（BGP，Border Gateway Protocol）来共享路由信息

:orange: EGP - 边界网关协议

* 用于进行路由信息通信
  * BGP是最新的外部网关协议,是具体的一种EGP协议
* <font color="red">有望被IDRP域间选路协议替换</font>

## AS&域间选路协议：Inter-Domain Routing Protocol

![](/static/2021-01-14-20-59-17.png)

在一个网络中，网络运营商将寻求尽可能有效地将数据传送到目的地。互联网对他们如何做到这一点，或**对他们使用什么数据链路层或物理层技术没有要求**。每个网络运营商可以**自由地使用它所选择的任何技术和任何路由或转发算法**。 通常情况下，网络运营商会使用距离矢量路由算法，或更有可能使用OSPF等链路状态路由算法，来确保流量在其网络中从源头到目的地的最短路径。不过，根据网络的规模和网络运营商及其客户的需求，有各种各样的不同方法。Within a network, the network operator will  seek to deliver data to its destination  as efficiently as possible. The Internet places  no requirements on how they do this,  or on what data link layer or  physical layer technologies they use. Each network  operator is free to use whatever technologies,  and whatever routing or forwarding algorithms,  that it chooses.  Typically, the network operator will seek to  ensure traffic follows the shortest path from  source to destination across their network,  using either a distance vector routing algorithm,  or, more likely, a link state routing  algorithm such as OSPF. There are a  wide variety of different approaches used,  though, depending on the size of the  network, and the needs the network operator  and its customers.

每个AS

* 分开管理，使用路由协议不同
* 最短路径路由
* **距离向量**
* **链路状态算法**

## 域间选路协议&BGP：Inter-Domain Routing & BGP

![](/static/2021-01-14-21-06-23.png)

大多数互联网流量并不局限于一个单一的AS。相反，**流量在从源头到目的地的路径上经过几个自治系统是很常见的**。Most Internet traffic is not confined to  a single AS. Rather, it’s common for  traffic to pass through several Autonomous Systems  on its path from source to destination.

* 例如，从大学发送到谷歌的数据包将从该大学的网络开始，然后穿越一个被称为JANET（联合学术网络系统；该大学的ISP）的网络，然后最终到达谷歌。For example, packets sent from the University to Google will start in  the University’s network, then traverse a network  known as JANET (the Joint Academic NETwork;  the University’s ISP), and then finally reach  Google.
  * 这三个网络中的每一个都是一个自治系统。每个人都必须合作转发数据包，并为数据在网络上找到合适的路线 Each of these three networks is an  Autonomous Systems. Each must cooperate to forward  the packets, and find an appropriate route  for the data across the network.
* 事实上，组成互联网的所有AS都在合作，以确保网络能够成功地将数据路由到其目的地，无论目的地在哪里。Indeed, all of the ASes that comprise  the Internet cooperate to ensure the network  can successfully route data to its destination,  wherever that destination is.
  * **这种合作是由边界网关协议（BGP）实现的。 BGP是一个路由协议**。This cooperation is enabled by the Border  Gateway Protocol, BGP.  BGP is a routing protocol.
  * 它允许<font color="red">每个AS公布其拥有的网络前缀，以告诉网络的其他部分在哪里发送以这些前缀中的IP地址为目的地的数据包</font>。It allows  each AS to advertise the network prefixes  that it owns, to tell the rest  of the network where to send packets  destined for IP addresses contained within those  prefixes.
  * 此外，BGP<font color='red'>允许AS公布它们可以用来到达其他AS拥有的网络前缀的路由</font>。In addition, BGP allows ASes to  advertise the routes they can use to  reach the network prefixes owned by other  ASes.
    * 例如，这允许ISP公布如何到达其客户使用的IP地址。This allows, for example, an ISP  to advertise how to reach the IP  addresses used by its customers.
  * 同样，BGP<font color="red">允许AS过滤掉他们不会转发流量的网络前缀宣布</font>。 Similarly,  BGP allows ASes to filter out advertisements  for network prefixes to which they will  not forward traffic.
  * **BGP中交换的信息允许ASes决定如何在网络上路由数据包**。The information exchanged in BGP allows ASes  to decide how to route packets across  the network.
    * 位于互联网**边缘的网络需要维护相对较少的信息来参与BGP**。他们只需要知道**自己的网络前缀和客户的前缀**。然后他们将这些**前缀公布给网络的其他部分**。Networks located near the edge of the  Internet need to maintain relatively little information  to participate in BGP. They simply need  to know their own network prefixes,  and those of their customers. They then  advertise those prefixes to the rest of  the network.
    * 这些**边缘AS知道如何将流量路由给自己和自己的客户**，而只是<font color="red">将其他的东西传递给一个默认路由，将其引导到更广泛的网络</font>。 **对于靠近互联网核心的网络，这种方法已经不够了**。Those edge ASes know how  to route traffic to themselves and their  customers, and just pass everything else to  a default route that directs it out  to the wider network.For networks nearer the core of the  Internet, this approach is no longer sufficient.
  * <font color="red">核心AS形成了所谓的缺省自由区，即DFZ，它们使用BGP把接近于完整的互联网地图放在一起，因此它们知道如何转发数据包以到达任何可能的目的地</font> These ASes form the so-called default free  zone, the DFZ, where they use BGP  to put together something close to a  complete map of the Internet, so they  know how to forward packets to reach  any possible destination.
  * 当涉及到BGP路由，以及为数据穿越广域互联网找到正确的路由时，<font color="deeppink">政策、政治和经济往往比找到最短的路由更重要。</font> When it comes to BGP routing,  and finding the correct route for data  to cross the wide area Internet,  policy, politics, and economics are often more  important than finding the shortest route.

---

域间选路IDRP & 边界网关协议BGP(都属于EGP)

* 将每个网络视为图顶点，在AS之间路由
* AS层拓扑结构
  * 专注核心连接，边界连接分散
  * <font color="deeppink">边缘网络可以通过默认路由到达核心网</font>
  * <font color="red">核心网需要记录完整路由表 --- 核心路由表【DFZ(default free zone)】</font>
* AS间路由 - 无可信任组织
  * 政策，政治，经济都为主要限制。<font color="red">无最短路径</font>
  * <font color="orange">使用BGP路由协议</font>

## 转发&尽力而为传输:Forwarding & best-effort

![](/static/2021-01-14-21-33-07.png)

网络层的最后一个特点是转发。 **给出一条通过网络的路由，由BGP计算出域间路径，并由域内路由协议（如OSPF）在每个网络内计算，那么数据包实际上是如何沿着该路径转发的呢**？A final feature of the network layer  is forwarding.  Given a route through the network,  calculated by BGP for the inter-domain path,  and by an intra-domain routing protocol such  as OSPF within each network, how are  the packets actually forwarded along that path?

* **互联网上的转发遵循尽力而为的原则**。Forwarding in the Internet follows a best  effort approach.
  * 网络中的一个路由器收到一个数据包，并尽最大努力将其转发到目的地。A router in the network  receives a packet, and makes its best  effort to forward it towards its destination.
  * 该网络是无连接的。发送者在发送数据包之前不需要建立连接或请求允许，也不需要尝试保留容量。因此，网络不做任何保证。The network is connectionless.A sender doesn’t  need to establish a connection or ask  permission before sending a packet, and makes  no attempt to reserve capacity. As a  result, the network makes no guarantees.
  * 如果没有足够的资源来转发一个数据包，那么它可能会被延迟或直接被丢弃。If there are insufficient resources available to  forward a packet, then it may be  delayed or will simply be discarded.
* 图中显示了一个例子，显示了数据包穿越网络所需的时间，X轴显示了自传输开始以来的时间，Y轴显示了从目的地收到响应所需的时间。The figure shows an example, showing the  time packets take to traverse the network,  with the x-axis showing time since the  start of transmission, and the y-axis showing  the time taken to receive a response  from the destination. As can be seen,  the time taken to get a response  may vary significantly, depending on the amount  of other traffic in the network.  Packets may be delayed, reordered, lost,  duplicated, or corrupted in transit.
  * 可以看出，**获得响应所需的时间可能会有很大变化，这取决于网络中其他流量的多少**。
  * **数据包在传输过程中可能被延迟、重新排序、丢失、重复或损坏**。
  * **在一个设计良好的网络中，几乎没有时间上的变化，数据包很少丢失或损坏，而且几乎不会不按顺序到达**。然而，如果数据包穿越质量较差的网络，其行为可能不那么可预测。In a well engineered network, there is  little timing variation, packets are rarely lost  or corrupted, and almost never arrive out  of order. If the packets traverse a  poor quality network, however, behaviour may be  less predictable.
* <font color="red">互联网可以提供极高的质量，但并不要求它这样做。这就提供了灵活性。互联网可以包括各种不同的网络，而不仅仅是那些拥有发达基础设施的富裕国家的网络。但是，它要求应用程序能够应对不可预测的质量</font>。The Internet can provide extremely high quality,  but there’s no requirement that it does  so. This gives flexibility. The Internet can  encompass all sorts of different networks,  not just those in rich countries with  well-developed infrastructure. But, it requires applications to  be able to cope with unpredictable quality.

---

:orange:不可靠通信，尽力而为服务

* best effort，无连接 connectionless，发送IP数据报 packet delivery
* <font color="red">只发送 - 不需要建立链接</font>
* 网络尽力而为发送IP数据报，但**不提供保证** network makes its best effort to deliver packets, but provides no guarantees
  * 网络传输时间可能不同 time taken to transit may vary
  * <font color="orange">可能丢包，延误，重排序，重复，损坏</font> packets may be lost, delayed, reordered, duplicated or corrupted
  * <font color="deeppink">如果无法传输，网络层直接丢弃IP数据报</font> discards packets if cannot deliver
* <font color="red">任意链路层都适用</font> easily run over any type of link layer

# 传输层：Transport Layer

正如上一部分所讨论的，**IP网络只提供尽力而为的数据包交付服务。IP数据包在传输过程中可能会丢失、重复、延迟或重新排序**。

:orange:TCP & UDP传输

![](/static/2021-01-14-21-53-15.png)

:orange: **传输协议的作用是尽可能地将应用程序与网络隔离**。

* 传输协议对用于不同应用的流量进行解复用。**它增强了网络的服务质量，为这些应用提供适当的可靠性**。
* 它还执行**拥堵控制**，使传输率适应可用的网络容量。
* 有两种传输协议已经成功部署在互联网上。
  * UDP，用户数据报协议，提供不可靠的服务。
  * TCP，传输控制协议，提供可靠、有序和拥堵控制的服务。
  * 在互联网上运行的应用程序使用这两种传输协议中的一种。

* IP层，IP网络提供尽力而为服务（不可靠）
  * 可能丢包，重复，延误，重排序
* 传输层 - 将应用层与网络层分离
  * <font color="blue">不同应用的流量解复用</font> Demultiplexes traffic for different applications
  * <font color="blue">提高网络质量,提供适当可靠性</font> Enhances network quality of service to offer appropriate reliability
  * <font color="blue">拥塞控制，适应各网络容量</font> Performs congestion control, adapts to network capacity

:orange:两种可部署传输层协议

* UDP - 用户数据包协议 user datagram protocol
* TDP - 传输控制协议 transmission control protocol

## UDP

![](/static/2021-01-14-22-06-54.png)

UDP是可以在互联网上运行的最简单的传输协议。

* **它向应用程序暴露了原始的IP服务，只增加了一个端口号的概念，以识别在单个主机上运行的不同应用程序**。
* 像IPv4和IPv6一样，UDP是无连接的。
  * 一个应用程序不需要建立一个UDP连接。相反，它可以简单地向目的地发送一个数据包，而不需要征求许可或建立连接。
* **UDP数据报是以尽力而为的方式传递的**。
  * 数据包可能根本没有到达，如果它们到达了，也可能没有按照发送的顺序到达。
  * UDP传输协议并不试图纠正这一点，或确保可靠的交付。
  * **这是使用UDP的【应用程序的责任】，以重建顺序，或检测丢失的数据包**。
* 同样，**UDP也不执行任何形式的拥堵控制**。
  * 如果一个使用UDP的应用程序试图以比网络能够交付的数据包更快的速度发送，那么这些数据包将被简单地丢弃。
  * UDP不能帮助应用程序调整其发送速度以匹配网络容量。
* 因此，**使用UDP的应用程序必须能够容忍一些数据的丢失，能够接受不按顺序的数据，并能够估计和适应可用的网络容量**。
  * 做好这些是非常困难的，UDP不适合许多应用。
  * UDP有用的地方是当应用喜欢**及时性而不是可靠性时**。因为它不试图重传丢失的数据，也不对数据进行缓冲以使其进入正确的顺序，所以**UDP为在网络上发送的数据提供了尽可能低的延迟**。
    * 这对IP**语音、视频会议和游戏等应用（实时应用**）非常有用，这些应用可以容忍一些数据丢失，但需要低延迟。
    * 但是，对于大多数应用来说，UDP的不可靠性质使它不太适合它们的需要。

UDP is the simplest possible transport protocol  that can run on the Internet.  It exposes the raw IP service to  applications, adding only the concept of a  port number, to identify different applications running  on a single host. Like IPv4 and IPv6, UDP is connectionless.  An application doesn’t need to establish a  UDP connection. Rather, it can simply send  a packet towards a destination without asking  permission or establishing a connection.  UDP datagrams are delivered in a best  effort manner. Datagrams might not arrive at  all, and if they do arrive,  they might not arrive in the order  they were sent. The UDP transport protocol  doesn’t attempt to correct for this,  or to ensure reliable delivery. It’s the  responsibility of the application using UDP to  reconstruct the ordering, or to detect lost  packets.  Similarly, UDP doesn’t perform any form of  congestion control. If an application using UDP  tries to send faster than the network  can deliver packets, then those packets will  simply be discarded. UDP doesn’t help the  application adapt its sending rate to match  the network capacity.  Accordingly, applications using UDP must be able  to tolerate some loss of data,  to receive data out of order,  and to be able to estimate and  adapt to the available network capacity.  Doing this well is extremely difficult,  and UDP is not suitable for many  applications. Where UDP is useful is when the  application prefers timeliness over reliability. Because it  doesn’t attempt to retransmit lost data,  and doesn’t buffer data to allow it  to be put into the correct order,  UDP offers the lowest possible latency for  data sent across the network. That’s useful  for applications like voice-over-IP, video conferencing,  and gaming, that can tolerate some data  loss but need low latency.  For most applications, though, the unreliable nature  of UDP makes it poorly suited to  their needs.

---

* 最简单的传输协议
* 为app提供原始IP服务 exposes raw IP service to app
  * 无连接 connectionless
  * 尽力而为IP报传输 best effort packet delivery
  * 装帧 framed
  * 不可靠 unreliable
* <font color="blue">无拥塞控制</font> no congestion control
* <font color="blue">有16bit端口号识别设备(应用)</font>
* <font color="deeppink">应用于时间性优于可靠性的应用</font> used by app preferring timeliness over reliability
  * voice-over-IP电话
  * 流媒体视频
  * 游戏
* <font color="deeppink">必须忍受数据丢失，适应app层的阻塞(因为UDP协议，不支持拥塞控制)</font> must be able to tolerate data loss, adapt to congestion in the app layer

## TCP

![](/static/2021-01-14-22-23-49.png)

:orange: 作为对比，TCP协议提供了一个**完全可靠的、有序的、在IP网络上运行的字节流传输服务**。

* TCP是一个面向连接的传输协议。
  * 使用TCP作为传输的应用程序必须首先建立一个从发送方到接收方的连接，然后才能发送数据。
* 一旦建立了连接，**TCP协议确保任何丢失的数据被重新传输，任何重新排序的数据被放回正确的顺序，然后再将其传递给接收的应用程序**。
* TCP协议还**调整数据在网络上的发送速度，以匹配可用的网络容量，这一过程被称为拥堵控制**。
* TCP发送方将**一串字节写入TCP连接，然后将这一串完全相同的字节传递给接收方。可靠地。按照发送的顺序。并以网络可以支持的最大速度**。
  * 在互联网上运行的绝大多数应用程序都使用TCP作为其传输协议

By way of contrast, the TCP protocol  provides a fully reliable, ordered, byte stream  delivery service than runs over an IP  network.  TCP is a connection oriented transport protocol.  Applications that use TCP as their transport  must first setup a connection from sender  to receiver, before they can send data.  Once a connection is established, the TCP  protocol ensures that any lost data is  retransmitted, and that any reordered data is  put back into the correct order,  before delivering it to the receiving application.  The TCP protocol also adapts the rate  at which data is sent across the  network to match the available network capacity,  a process known as congestion control.  A TCP sender writes a sequence of  bytes into a TCP connection, and that  exact same sequence of bytes is delivered  to the receiver. Reliably. In the order  sent. And at the maximum speed the  network can support.  The overwhelming majority of applications running on  the Internet use TCP as their transport  protocol.

---

:orange: TCP有两个限制。

* 第一个限制是，TCP传输的是字节序列，而不是信息的序列。【消息边界不被保留】
  * 如果一个发送者向TCP连接写了2000字节的数据，包括两个各1000字节的消息，那么TCP保证这**2000字节将被可靠地接收，并按照发送的顺序**。它并**不保证它们会被当作两个各1000字节的信息来接收**。
  * 事实上，TCP完全有可能将数据作为一个1500字节的块，然后是一个包含剩余500字节的单独块来传送。
  * <font color="red">关心消息边界的应用程序必须对他们通过TCP连接发送的数据进行结构化处理，以使这些消息边界能够被重建。</font>
    * 由于向文件读写数据也不保留消息边界，所以这样做并不困难。
* TCP的另一个限制是，由于它**可靠地按顺序传送数据，它必须重新传输任何丢失的数据包。 这就延迟了这些丢失的数据，因为在重传到达之前，这些数据不能被传递给应用程序**。
  * 如果要在一个不可靠的网络中按顺序可靠地传送数据，这是一个不可避免的权衡，但这确实意味着<font color="red">TCP以延迟换取可靠性</font>。

TCP has two limitations.  The first is that TCP delivers a  sequence of bytes, not a sequence of  messages. If a sender writes 2000 bytes  of data into a TCP connection,  comprising two messages of 1000 bytes each,  then TCP guarantees that those 2000 bytes  will be received reliably, and in the  order sent. It does not guarantee that  they will be received as two messages  of 1000 bytes each. Indeed, it’s entirely  possible that TCP will deliver the data  as a block of 1500 bytes followed  by a separate block containing the remaining  500 bytes. Applications that care about message  boundaries must structure the data they send  over a TCP connection to allow those  message boundaries to be reconstructed. Since reading  and writing data to a file also  doesn’t preserve message boundaries, doing this tends  not to be difficult.  The other limitation of TCP is that,  because it delivers data reliably and in  order, it must retransmit any lost packets.  This delays any data following those lost  packets, since it can’t be delivered to  the application until the retransmission arrives.  This is an unavoidable trade-off, if data  is to be delivered reliably and,  in the order sent, across an unreliable  network, but does mean that TCP trades  latency for reliability.

---

* 可靠，有序，IP<font color="deeppink">字节流</font>传输服务 reliable, ordered, byte stream delivery service running over IP
  * 丢包会进行<font color="deeppink">重传输</font> retransmitted
  * <font color="deeppink">保证顺序</font> ordering ensurance
  * <font color="deeppink">消息边界不被保留</font> message boundaries are not preserved
    * UDP数据包有边界，byte提前固定
* <font color="orange">拥塞控制</font> congestion control
  * 调整传输速率适应各网络容量 adapts sending rate to match network capacity
* 添加端口号，识别设备（应用）
* <font color="orange">用于需要保证可靠性的应用 - 大多应用的默认选择</font>

### TCP包格式：Packet Format

![](/static/2021-01-15-01-34-06.png)

TCP以段的形式传递数据，**每个段都在一个IP数据包中传递**。

* 每个TCP段包括一个源端口和目标端口号码，用于识别发送和接收数据的应用程序。
* 每个TCP段还包括一个**序列号**，用于计算所发送数据的字节数，并允许**接收方检测数据丢失和重建原始发送顺序**，
* 还包括一个**确认号**ACK，**表明它希望接收的下一个段**。 
* TCP段还包括**接收者窗口大小**，它表明接收者**有多少缓冲空间来存储传入的TCP段**，
* 一个用于检测数据包损坏的校验和，一组用于管理连接设置的标志，以及一个紧急指针以支持重要数据的提前交付。
* 实际的TCP有效载荷数据在这个TCP头之后。
  * 可以看出，除了IP包头中携带的信息外，TCP包还携带很多信息。
* TCP是一个复杂而精密的传输协议，它为IP增加了许多功能，以确保数据的有效传输。

TCP delivers data in the form of  segments, each delivered within an IP packet.  Each TCP segment includes a source and  destination port numbers, that identify the applications  that send and receive the data.  Each TCP segment also includes a sequence  number, that counts the number of bytes  of data sent and allows the receiver  to detect loss and reconstruct the original  sending order, and an acknowledgement number that  indicates the next segment it wishes to  receive.  TCP segments also include the receiver window  size, that indicates the amount of buffer  space the receiver has to store incoming  TCP segments, a checksum to detect packet  corruption, a set of flags to manage  connection setup, and an urgent pointer to  support advance delivery of important data.  The actual TCP payload data follows this  header.  As can be seen, TCP packets carry  a lot of information in addition to  that carried in an IP packet header.  TCP is a complex and sophisticated transport  protocol, that adds a lot of features  to IP in order to ensure that  data is delivered effectively.

### TCP3次握手：Handshake

![](/static/2021-01-15-01-37-17.png)

一个TCP连接分三个阶段进行

* 在连接的开始，是一个建立连接的初始三方握手。
* TCP**客户端**向服务器**发送一个初始的TCP数据包，在TCP头中设置SYN（"同步"）位，以表明连接的开始**。
* **服务器**发送一个响应，
  * 设置SYN位，表示它愿意建立一个连接。
  * 这个响应也设置了ACK位，表明确认号是有效的，因为它确认收到了那个初始数据包。
* 客户端通过向服务器发送确认来完成SYN - SYN+ACK - ACK的三向握手。这就建立了连接。
  * 然后，客户端和服务器可以交换数据
* 在这个例子中，客户端向服务器发送数据，而服务器确认收到该数据。
  * 最初的数据包有序列号0，每个数据包包括1500字节的数据。
  * 我们看到服务器发送确认ack，并在数据包到达时响应**recv()调用，将数据传递给应用程序**。
* 我们还看到，在**收到确认之前，客户端可以发送一定数量的数据包，称为拥塞窗口**。
  * 在这个例子中，拥塞窗口是3000字节：**客户端被允许发送多达3000字节的数据，而不会收到确认**。
* **从客户端发送至服务器的序列号为4500的数据包被丢失**。
  * 结果，当下一个序列号为6000的数据包到达时，服务器产生了另一个确认，表明它仍在等待4500数据包。
  * **这种情况一直持续到收到三个重复的确认**，这时客户端会**重新传输丢失的数据包**。 最终，丢失的数据到达了服务器。这就填补了这个空白，丢失的数据和已经收到的三个后续数据包被送到了应用程序。
  * 在丢失的数据被重传时，服务器会<font color='red'>看到一个延迟</font>，然后收到一阵数据。 消息的边界没有被保留下来。
* 最后，使用与打开连接时类似的三方握手来关闭连接。

A TCP connection proceeds in three stages.  At the start of the connection is  an initial three-way handshake that establishes the  connection. An initial TCP packet is sent  from the TCP client to the server,  with the SYN (“synchronise”) bit set in  the TCP header, to indicate the start  of a connection.  The server sends a response, with its  SYN bit set, to indicate that it’s  willing to establish a connection. This response  also has the ACK bit set,  indicating that the acknowledgement number is valid,  because it acknowledges receipt of that initial  packet. The client completes the three-way SYN -  SYN+ACK - ACK handshake by sending an  acknowledgement to the server. This establishes the  connection.  The client and server can then exchange  data.  In this example, the client sends data  to the server, and the server acknowledges  receipt of that data. The initial packet  had sequence number 0, and each packet  includes 1500 bytes of data.  We see the server sending acknowledgement packets,  and delivering data to the application in  response to recv() calls, as the data  packets arrive.  We also see that the client can  send some number of data packets,  known as its congestion window, before it  receives an acknowledgement. In this example,  the congestion window is 3000 bytes:  the client is allowed to send up  to 3000 bytes of data without receiving  an acknowledgement. The packet with sequence number 4500,  sent from client to server, is lost.  As a result, when the next packet,  with sequence number 6000, arrives the server  generates another acknowledgment indicating that it’s still  expecting packet 4500. This continues until three  duplicate acknowledgements have been received, when the  client retransmits the lost packet.  Eventually, the missing data arrives at the  server. This fills the gap, and the  missing data, and the three following packets  that were already received, are delivered to  the application. The server sees a delay,  while the missing data is retransmitted,  then receives a burst of data.  Message boundaries are not preserved.  Finally, the connection is closed using a  three-way handshake similar to that used to  open the connection.  We’ll talk more about how TCP establishes  connections, and reliably transmits data, in later  lectures.

### TCP拥塞控制：Congestion Control

![](/static/2021-01-15-01-37-55.png)

TCP协议包括一个复杂的算法，被称为拥堵控制，它适应其传输速率以匹配可用的网络容量。

* 这分两个阶段进行。 第一阶段被称为**慢启动**。
  * 在连接开始时，TCP开始缓慢地发送，但以指数形式增加其发送速率，直到达到网络容量。
* 一旦它的发送速度与可用的网络容量相匹配，TCP就会切换到**避免拥堵阶段**，按照锯齿状模式调整其发送速率，使其能够逐渐适应容量的变化。
* 在TCP拥塞控制行为中，有很多微妙的细节，我们将在后面的课程中讨论。 现在最重要的是，**TCP可以有效地调整数据发送的速率，以适应可用网络容量的变化**。它用来做这件事的算法乍一看很简单，但实际上包含了很多复杂的功能，以及不明显的行为，而且非常有效。

The TCP protocol includes a sophisticated algorithm,  known as congestion control, that adapts its  transmission rate to match the available network  capacity.  This operates in two phases.  The first phase is known as slow-start.  At the start of a connection,  TCP starts by sending slowly, but increases  its sending rate exponentially until it reaches  the network capacity.  Once it’s sending at a speed that  matches the available network capacity, TCP switches  to a congestion avoidance phase, adapting its  sending rate following a sawtooth pattern that  allows it to gradually adapt to changes  in capacity. There are a lot of subtle details  in TCP congestion control behaviour, that we’ll  talk about later in the course.  What’s important for now is that TCP  can effectively adjust the rate at which  data is sent to match changes in  the available network capacity. The algorithm it  uses to do this looks simple at  first glance, but actually contains a lot  of complex features, and not obvious behaviours,  and is highly effective.

> 若出现拥塞而不进行控制，整个网络的吞吐量将随输入负荷的增大而下降

* <font color="deeppink">注意上图，超时重传时，只将拥塞窗口大小缩减到原来的一半，没有调整阈值</font>

:orange:拥塞控制算法

* 慢开始 slow start
  * 拥塞窗口可指数增长
  * *发送方此时可以连续发送两个数据报文段，接收方收到该数据报文段后，给发送方一次发回2个确认报文段，发送方收到这两个确认报文后，将拥塞窗口的值加2变为4，发送方此时可连续发送4个报文段，接收方收到4个报文段后，给发送方依次回复4个确认报文，发送方收到确认报文后，将拥塞窗口加4，置为8*
  * <font color="deeppink">超时重传，也把阈值降为一半，窗口大小=1，重新进行慢开始算法</font>
* 拥塞避免 congestion avoidance
  * 拥塞窗口只能+1
  * <font color="deeppink">如丢包，超时，出现拥塞，更改拥塞窗口大小&阈值（为一半），重新慢开始算法</font>

![](/static/2021-01-15-01-58-23.png)

* 发送方维护动态变量- 拥塞窗口（初始为1）
  * 无拥塞，增大拥塞窗口+1
  * 有拥塞，减小拥塞窗口-1
* 将拥塞窗口作为发送窗口
  * 发送窗口值为几，就能发送几个数据报文段
* 设置**慢开始阈值变量**
  * 如拥塞窗口<阈值，使用慢开始算法
  * 拥塞窗口>阈值，使用拥塞避免算法

# 高级层协议：Higher Layer Protocols

会话，表示，应用层 session，presentation，application

* 网络架构没有具体区分该几层

:orange:应用需求

![](/static/2021-01-15-02-13-12.png)

* 管理传输层连接 manage transport layer connections
* 命名定位app资源
* 协调数据结构，按需进行格式转换
* 以合适顺序规格展示数据
* 实现app层语义

---

**围绕高层协议需要考虑的一些问题**，以及**协议标准对互操作性的重要性**。

* OSI参考模型在传输层之上定义了三个层次。它们是会话层、表现层和应用层。
* **这些层的协议的目标是支持应用程序的需求**。
  * 它们管理传输连接，命名和定位应用所使用的资源，描述和协商数据格式，并以适当的方式呈现数据。
  * 从本质上讲，它们将应用的需求转化为协议机制。 **在这些层使用的协议往往与特定的应用类别紧密相连，与传输层及以下的协议相比，其通用性较差**。<font color="red">因此，这些层之间的界限往往不那么清晰，许多系统在实施这些高层协议时没有明确区分各层之间的界限。</font>

In this part, I’ll talk briefly about  some issues to consider around higher layer  protocols, and of the importance of protocol  standards for interoperability.  The OSI reference model defines three layers  above the transport layer. These are the  session, presentation, and application layers.  The goal of protocols at these layers  is to support the needs of applications.  They manage transport connection, name and locate  resources used by the application, describe and  negotiate data formats, and present the data  in an appropriate manner. Essentially, they translate  the application’s needs into protocol mechanisms.  The protocols used at these layers tend  to be quite tightly bound to particular  classes of application, and are less general  purpose than protocols at the transport layer  and below. As a result, the boundaries  between these layers tend to be less  clear, and many systems implement these higher  layer protocols without a clear distinction between  the layers.

## 会话层-管理连接：Session Layer-Manage connections

![](/static/2021-01-15-02-15-07.png)

* App层需要的连接类型？
  * P2P
  * 广播
  * 1-1
  * N-N
  * ...
* 如何找到参与者？
* 如何建立连接？
* 会话成员如何改变？
  * 组大小变化
  * 变化速度
  * 可感知

---

**会话层是关于管理传输层的连接**。

* 有些应用是直截了当的，一个客户端连接到一个服务器，或一小批服务器。 
* HTTP就是这种协议的一个例子。这类协议往往需要相对较少的会话层支持，通常仅限于**能够重新使用一个传输层连接来发送和接收多个消息**。
* 其他系统则更为复杂，涉及多个客户端和服务器协作以满足应用需求。
  * 例子包括视频会议和聊天应用，以及多人游戏，它们使用服务器集群来支持终端用户的应用。
  * 这些应用中的会话层往往关注寻找通话中的参与者或游戏中的玩家，并在这些参与者和支持每个用户的服务器之间转发消息。
* 点对点的应用往往有更复杂的会话层功能。
  * **这些应用有一个问题，就是如何在防火墙和网络地址转换器存在的情况下建立点对点连接，并经常需要适应群体成员的快速变化**。
  * BitTorrent是这一类应用的一个众所周知的例子。
* 最后，还有**一些使用组播或广播传输的应用，网络可以向一组接收者发送消息**。
  * 像点对点应用一样，这些往往需要会话层来管理组成员的变化。
  * 每个会话层协议都是不同的，它们之间的共同点相对较少，因为它们往往与特定类别的应用紧密相连。

The session layer is about managing transport  layer connections.  Some applications are straight forward, with a  single client connecting to a single server,  or to a small set of servers.  HTTP is an example of such a  protocol. This class of protocols tends to  need relatively little session layer support,  often limited to being able to re-use  a transport layer connection to send and  receive multiple messages.  Others systems are more complex, involving multiple  clients and servers collaborating to meet the  application needs. Examples include video conferencing and  chat applications, and multiplayer games, that use  a cluster of servers to support end-user  applications. The session layer in these applications  tends to be concerned with finding the  participants in a call or players in  the game, and forwarding messages between those  participants and between the servers supporting each  user.  Peer-to-peer applications tend to have still more  sophisticated session layer features. These applications have  the problem of how to set-up peer-to-peer  connections in the presence of firewalls and  network address translators, and often need to  adapt to rapid changes in group membership.  BitTorrent is a well known example of  this class of application.  Finally, there are applications that use multicast  or broadcast transmission, where the network can  send messages to a group of receivers.  Like peer-to-peer applications, these tend to require  the session layer to manage group membership  changes.  Each session layer protocol is different,  and they have relatively little in common  with each other, since they tend to  be closely tied to particular classes of  application.

## 表示层：Presentation Layer

> 表示层为在应用过程之间传送的信息提供表示方法的服务，它只关心信息发出的语法和语义。

![](/static/2021-01-15-02-21-40.png)

* 管理数据的表示，展示，转换
  * 媒体类型，内容协调
  * 信道编码
  * 国际化，语言，字符集
* app广泛应用的常见服务

---

演示层位于会话层之上，管理数据的演示、表示和转换。 **演示层的功能包括网络服务器和视频会议工具用来描述数据格式的媒体类型描述。 它们指定一个页面是HTML，一个图像是JPEG还是PNG，或者视频是用H.264而不是VP8压缩的**。 

它们允许应用程序描述它们所支持的数据格式，并与会话中的其他参与者协商商定格式。

其他表现层功能包括通道编码，其中数据被调整以适应通信通道的限制。一个例子是电子邮件，最初的设计只支持ASCII格式的文本数据。 当增加对电子邮件附件的支持时，这些附件必须看起来像文本，所以它们可以通过尚未升级的邮件服务器。为此，开发了一个通道编码方案，即BASE64编码，允许任意数据被转换为基于文本的格式进行传输。

最后，表现层通常是实现对国际化支持的地方。这里有两类问题。一个是围绕着用于表示文本数据的字符集的标记，无论是UTF-8格式的Unicode文本，还是一些国家的字符集，如ASCII、Latin1，或者台湾和香港使用的Big5系统。另一个是围绕标记语言，以及可能使用的区域变体或方言。 表达层解决的问题范围很广，往往与更广泛的系统有关，与应用程序的运行环境有关，而不仅仅是与网络通信有关。

The presentation layer sits above the session  layer, and manages the presentation, representation,  and conversion of data.  Presentation layer features include the media type  descriptions that web servers and video conferencing  tools use to describe data formats.  They specify that a page is in  HTML, whether an image is a JPEG  or a PNG, or that the video  is compressed with H.264 rather than VP8.  And they allow applications to describe the  data formats they support, and to negotiate  an agreed format with the other participants  in the session.  Other presentation layer features include channel encodings,  where data is adapted to fit the  limitations of the communications channel. An example  is email, where the original design only  supported textual data in ASCII format.  When support for email attachments was added,  those attachments had to look like text,  so they could pass through mail servers  that hadn’t yet been upgraded. A channel  encoding scheme, known as BASE64 encoding,  was developed to do this, allowing arbitrary  data to be converted to a text-based  format for transmission. Finally, the presentation layer is often where  support for internationalisation is implemented. There are  two sorts of concerns here. One is  around labelling the character set used to  represent textual data, whether it is Unicode  text in UTF-8 format, or some national  character set such as ASCII, Latin1,  or the Big5 system used in Taiwan x and Hong Kong. The other is around  labelling the language, and possibly the regional  variant or dialect, that is used.  The problems addressed by the presentation layer  are wide-ranging, and tend to relate to  the broader system, and to the environment  in which the application operates, not just  to the network communication.

## 应用层：Application Layer

![](/static/2021-01-15-02-23-54.png)

* 协议具体根据app业务逻辑定义 Protocol functions specific to the application logic

7层OSI参考模型的最后一层是应用层。 正是在这里实现了特定于应用逻辑的协议功能。应用层协议传递电子邮件信息、检索网页、流媒体视频、支持多人游戏，等等。 根据定义，这类消息完全是特定于应用的，在这里几乎没有什么一般性的东西可以说。The final layer in the 7-layer OSI  reference model is the application layer.  It is here that protocol functions that  are specific to the application logic are  implemented. The application layer protocols deliver email  messages, retrieve web pages, stream video,  support multiplayer games, and so on.  By definition, such messages are entirely application  specific, and there is little that is  general to say here.

## 互操作性-协议标准重要性：Importance of Protocol Standards

![](/static/2021-01-15-02-27-06.png)

OSI模型是一种合理的网络协议抽象，但忽略了

* 经济层
* 政治层
* 成功的网络协议支持不同厂商之间的互操作性--这种互操作性的存在是因为这些厂商致力于**协议的标准化**
* 粗略的共识和运行代码→标准是经过大量讨论和协商的结果 Rough consensus and running code → standards are the result of much discussion and negotiation

**OSI模型是思考网络协议的一种合理方式。有一个共同的模型有助于构建和组织网络协议的讨论**。真实的网络更加复杂，层的界限也不像模型所建议的那样清晰，尤其是在较高的层上，但OSI模型足够接近现实，因此是有用的。 **但是，它忽略了两个关键层：金融和政治**。 网络协议只有在它们能使不同的设备进行通信的情况下才是成功的。这就要求由不同群体实施的协议的不同实施方案之间具有互操作性。 激励机制的正确性，使不同的供应商能够一起工作，以确保他们的产品能够互操作，是一个迅速超越技术的问题，并进入组织政治、市场力量、监管和经济的领域。 这是一个标准制定组织，如互联网工程任务组、国际电信联盟、全球网络联盟、第三代伙伴关系项目、移动图像专家组等，发挥重要作用的领域。The OSI model is a reasonable way  of thinking about network protocols. Having a  common model helps to frame and organise  discussion of network protocols. Real networks are  more complex, and the layer boundaries are  less clear cut than the model suggests,  especially around the higher layers, but the  OSI model is close enough to reality  to be useful.  But, it misses two key layers:  the financial and the political.  Network protocols are only successful to the  extent that they enable different devices to  communicate. This requires interoperability between different implementations  of a protocol, implemented by different groups  of people.  Getting the incentives right, so that different  vendors can work together to ensure their  products interoperate, is a problem that rapidly  goes beyond the technical, and into the  realms of organisational politics, market forces,  regulation, and economics.  It’s an area where standards setting organisations,  such as the Internet Engineering Task Force,  the International Telecommunications Union, the World-wide Web  Consortium, the 3rd Generation Partnership Project,  the Moving Picture Experts Group, and others,  play an important role.

网络协议标准是一个非常人性化的过程。它们是许多讨论和协商的结果，是许多争论和妥协的结果。 IETF将其结果描述为 "粗略的共识和运行的代码"。**互联网之所以能够运行，是因为成千上万的工程师花费了大量的精力来确保它的运行，确保他们的产品能够协同工作，并确保协议的描述足够好，以支持互操作性**。Network protocol standards are a very human  process. They’re the result of much discussion  and negotiation, much argument and compromise.  The IETF describes the outcome as “rough  consensus and running code”. The Internet works  because thousands of engineers spend the effort  to make sure it works, to make  sure their products work together, and that  the protocols are described well enough to  support interoperability.

# 互联网变迁-架构设想：The Changing Internet-Architectural Assumptions

![](/static/2021-01-15-02-33-01.png)

互联网的设计者们做了一些假设。 他们假设设备通常位于网络中的一个固定位置，并将有少量的网络接口，这些接口可以被赋予持久的、全球唯一的地址。 他们假设网络和在其上运行的服务将由许多不同的组织操作，作为同行工作，并且没有中央控制点。他们假设尽力而为的数据包传送服务将提供足够的质量，并且应用程序将是自适应的，能够应对网络容量和可用带宽的变化。 他们假设网络是可信和安全的。 他们还假设创新将发生在边缘，而网络本身将只提供简单的数据包传送服务。 这些假设在20世纪80年代中期对研究网络是有意义的，当时互联网协议的最初设计正在定稿。今天，**这些假设对一个网络来说仍然有意义吗**？The designers of the Internet made certain  assumptions.  They assumed that devices would generally be  located at a fixed location in the  network, and would have a small number  of network interfaces that could be given  persistent and globally unique addresses.  They assumed the network, and the services  that run on it, would be operated  by many different organisations, working as peers,  and that there would be no central  points of control. They assumed that best effort packet delivery  service would provide sufficient quality, and that  applications would be adaptive, and able to  cope with changes in the network capacity  and available bandwidth.  They assumed that the network was trusted  and secure.  And they assumed that innovation would happen  at the edges, and that the network  itself would provide only a simple packet  delivery service.  These assumptions generally made sense for a  research network in the mid-1980s, when the  original design of the Internet protocols was  being finalised. Do they still make sense  for a network today?

---

互联网**最初设计假设**

* 设备位于固定位置，拥有少量唯一寻址的网络接口
* 网络 & 服务分散
* 尽力而为服务提供足够质量
* 网络可信
* 在边缘网络进行创新

:candy:以上设想在1980IP架构定义好时为合理的，但之后网络&协议如何变迁？

## 设备地址不能持久化：Changes in Addressing & Reachability

![](/static/2021-01-15-02-36-13.png)

**第一个假设是，设备位于网络中的固定位置，每个设备都有少量的网络接口，这些接口可以被赋予持久的、全球唯一的IP地址**。 

* 这就给出了一个理想的属性，即每个设备都可以被其他设备寻址。 它假定了一个由对等物组成的网络，客户和服务器之间没有概念上的区别，任何连接到网络的设备都可以扮演任何一个角色，这取决于它所运行的软件。
* 而且，原则上，任何设备都可以连接到任何其他设备。当然，**可寻址并不一定意味着可到达，互联网长期以来一直支持防火墙，通过阻止对某些设备的访问来提供访问控制，但任何设备成为服务器的能力赋予了终端用户**。
  * <font color="red">如果任何设备都可以充当服务器，那么就应该可以在网络的任何地方运行网站或其他服务，包括在家用机器上。不应该要求支付位于管理数据中心的专用服务器，以托管一个网站或其他服务。</font>

:orange: 不幸的是，这个假设失败了。 **它的失败是因为IPv4没有足够的地址来支持它，也因为设备变得移动**。

* **IPv4缺乏地址的问题导致许多主机与其他主机共享IP地址，使用的是一种称为网络地址转换（NAT）的技术**。
* 从理论上讲，IPv6将通过为每台主机提供足够的地址来解决这个问题，**但IPv6的部署速度很慢**。
* <font color="red">缺乏地址的结果是，连接变得困难</font>
  * 当连接到一个设备时，**越来越有必要同时尝试IPv4和IPv6地址，也许可以并行地进行连接尝试，以获得良好的性能--这种技术被称为 "快乐眼球"，因为它可以提高最终用户的网络浏览性能**。
  * 也**有必要考虑NAT穿越问题**。
    * 在NAT后面的客户端可以**很容易地连接到公共互联网上的服务器**，但正如我们将在第2讲中讨论的那样，**要连接到位于NAT后面的设备，以及建立点对点的应用是很困难的**。
  * 这种组合大大增加了建立连接的复杂性。 这使得网络应用变得更慢，更不可靠。
  * **而且，也许更重要的是，它迫使服务器应用程序在数据中心运行，而不鼓励点对点的应用程序**。
    * <font color="red">这迫使人们依赖云服务，并鼓励将互联网服务集中到大型云供应商，如亚马逊网络服务和谷歌。</font>

The first assumption was that devices were  located at fixed places in the network,  and that each device had a small  number of network interfaces, that could be  given persistent and globally unique IP addresses.  This gives the desirable property that every  device is addressable by every other device.  It assumes a network of peers,  with no conceptual difference between clients and  servers, where any device connected to the  network can take either role, depending on  the software it runs. And where,  in principle, any device can connect to  any other device.  Of course, addressable doesn’t necessarily imply reachable,  and the Internet has long supported firewalls  to provide access control, by blocking access  to certain devices, but the ability for  any device to be a server empowers  end-users.  If any device can act as a  server, it ought to be possible to  run a website, or other service,  anywhere in the network, including on a  home machine. There should be no requirement  to pay for a dedicated server,  located in a managed data centre,  to host a website or other service.  Unfortunately, this assumption failed.  It failed because IPv4 had insufficient addresses  to support it, and because devices became  mobile.  The problem with the lack of addresses  in IPv4 results in many hosts sharing  IP address with others, using a technique  known as network address translation (NAT).  In theory, IPv6 will solve this problem  by providing enough addresses for each host,  but IPv6 has been slow to deploy.  The result of this lack of addresses  is that connectivity becomes difficult.  It’s increasingly necessary to try both IPv4  and IPv6 addresses when connecting to a  device, perhaps racing connection attempts in parallel  to get good performance – a technique  known as Happy Eyeballs because it improves  end user web browsing performance.  It’s also necessary to think about NAT  traversal. A client behind a NAT can  easily connect to a server on the  public Internet but, as we’ll discuss in  Lecture 2, it’s difficult to connect to  a device located behind a NAT,  and to build peer-to-peer applications.  This combination greatly increases the complexity of  establishing a connection.  This makes networked applications slower, and less  reliable. And, perhaps more importantly, it forces  server applications to run in data centres,  and discourages peer-to-peer applications. This forces reliance  on cloud services, and encourages centralisation of  Internet services onto large cloud providers,  such as Amazon Web Services and Google.

---

假设，每个网络接口IP地址唯一

* 统一连接的设备可唯一寻址
* 原则上，任何设备都可以连接到任何其他设备；政策由防火墙执行，由改变地址分配来保护隐私
* <font color="deeppink">IPV4地址空间不支持这种模式</font>
* <font color="deeppink">IPV6部署慢，NAT模式普遍存在</font>

:orange:造成的影响 - 连接困难

* 双栈IPv4/IPv6连接竞速？？&
* 点对点app的NAT穿越？？？
  * 软件设计实现复杂化
  * 强制依赖云服，鼓励集中化？？

### IPV4地址用尽：Address Exhaustion

![](/static/2021-01-15-02-47-11.png)

IPv4地址的短缺有多严重？

* **IP地址的分配遵循一个分层的模式**。互联网号码分配机构（IANA）将IP地址块分配给区域互联网注册处。这些区域注册机构然后将地址分配给互联网服务供应商，以及他们区域内的其他组织，而这些组织又将地址分配给最终用户。
* IANA在2011年将最后一批可用的IP地址块分配给了区域注册机构--大约十年前。 从那时起，区域注册机构已经逐渐减少了他们的可用地址库。截至2020年底，欧洲、北美、拉丁美洲和亚太地区的区域注册机构已经完全耗尽了可用的IPv4地址。预计非洲的IPv4地址将在2021年耗尽。 现在有一个繁荣的Pv4地址转让市场，以前被分配到IPv4地址的网络，拥有超过他们需要的地址，将其出售给其他人。 截至2020年底，一个IPv4地址可以以大约25美元的价格出售。 例如，大学拥有的IPv4地址空间，价值约为160万美元。

How severe is the shortage of IPv4  addresses?  Well, IP address assignment follows a hierarchical  model. The Internet Assigned Numbers Authority (IANA)  assigns blocks of IP addresses to Regional  Internet Registries. Those regional registries then assign  addresses to ISPs, and other organisations within  their region, and those in turn allocate  addresses to end users.  The IANA assigned the last available blocks  of IP addresses to the regional registries  in 2011 – around ten years ago.  Since then, the regional registries have gradually  been running down their pool of available  addresses. As of late 2020, the regional  registries for Europe, North America, Latin America,  and the Asia-Pacific Region have entirely run  out of available IPv4 addresses. Africa is  projected to run out of IPv4 addresses  during 2021.  There is now a thriving market in  the transfer of Pv4 addresses, with networks  that were previously assigned IPv4 addresses,  and have more than they need,  selling the addresses on to others.  As of late 2020, a single IPv4  address can be sold for around $25.  The IPv4 address space owned by the  University , for example, is worth  around $1,600,000.

### IPV6部署慢：Slow Deployment

![](/static/2021-01-15-02-47-49.png)

尽管IPv4地址短缺，但IPv6的采用却相对缓慢，尽管它现在已经达到了临界质量。 例如，如幻灯片所示，谷歌目前报告说，其大约三分之一的用户使用IPv6。由于网络运营商倾向于一次性转换他们的客户，因此IPv6的可用性变化很大。一般来说，在一个特定的网络中，要么所有的用户都有IPv6，要么都没有。例如，在英国，大多数移动网络默认为其网络上的智能手机分配IPv6地址，但许多住宅ISP和企业仍然使用IPv4。你得到什么类型的地址，取决于你如何连接到网络。 其他国家也遵循类似的模式，一些网络全盘切换到IPv6，而另一些则仍然使用IPv4。Despite this shortage of IPv4 addresses,  adoption of IPv6 has been relatively slow,  although it’s now reaching critical mass.  For example, as shown on the slide,  Google currently reports that around a third  of its users are on IPv6. Availability of IPv6 is highly variable,  since network operators tend to switch their  customers all at once. Generally, either all  the users in a particular network have  IPv6, or none of them. In the  UK, for example, most mobile networks assign  IPv6 addresses to smartphones on their networks  by default, but many residential ISPs and  businesses still use IPv4. What type of  address you get depends on how you  connect to the network.  Other countries follow similar patterns, with some  networks switching wholesale to IPv6, while others  remain on IPv4.

### IPv4不足-NAT穿越：Establish Connectivity in Modern Internet

![](/static/2021-01-15-02-48-43.png)
![](/static/2021-01-15-02-53-16.png)

这种IPv4和IPv6的混合使用（快乐眼球，），以及许多IPv4主机位于**网络地址转换器NAT**后面，使连接的建立变得非常复杂。

* 在IPv4互联网中，点对点应用程序必须执行一个复杂的过程来发现**NAT绑定，与他们的同伴交换候选地址，并探测以建立哪些地址可用于连接**。
  * 这需要一套协议，即**STUN和TURN**，以及一个拥有**全球唯一公共IPv4地址的中央服务器**的协助，以检测网络地址转换的存在，确定正在进行的地址转换类型，并**得出一组可用于P2P通信的候选地址**。
  * 对等体**通过中央服务器相互交换这些候选地址，然后遵循一种被称为交互式连接建立（ICE）的算法，建立直接的、低延迟的、对等的流量**。
* 这在大多数情况下是可行的，但却**很复杂、缓慢、耗电，并产生了大量不必要的流量--这一切都是因为没有足够的IPv4地址**。

This mixed use of IPv4 and IPv6,  with many IPv4 hosts being located behind  network address translators, greatly complicates connection establishment.  In the IPv4 Internet, peer-to-peer applications must  perform a complex process to discover NAT  bindings, exchange candidate addresses with their peer,  and probe to establish what addresses are  usable for a connection.  This uses a set of protocols,  known as STUN and TURN, and the  assistance of a central server with a  globally unique public IPv4 address, to detect  the presence of network address translation,  to determine what type of address translation  is being performed, and to derive a  set of candidate addresses that can be  used for peer to peer communication.  The peers use the central server to  exchange these candidate addresses with each other,  and then follow an algorithm known as  Interactive Connectivity Establishment (ICE) to setup direct,  low-latency, peer-to-peer flows.  This works, most of the time,  but is complicated, slow, power hungry,  and generates a lot of otherwise unnecessary  traffic – and all because there aren’t  enough IPv4 addresses.

:orange:NAT穿越 - 绑定发现，交换，探测

* STUN，TURN，ICE算法
* 需要一个具有公共IP地址的服务器进行绑定发现；集中控制点
* 复杂，慢，不可靠，浪费电，产生冗余的流量问题

---

### 双栈协议不足

当然，解决的办法是转移到IPv6。 不幸的是，迁移到IPv6将需要很长的时间。而且，在这一过程中，会有一些设备和一些网络只支持IPv4，一些只支持IPv6，还有一些同时支持这两个版本。 为**了同时接触到IPv4和IPv6的用户，流行的服务往往被托管在同时拥有IPv4和IPv6地址的服务器上。这就是所谓的双栈托管**，它进一步鼓励集中到有资源提供两种地址的大型托管供应商。 为了获得良好的性能，客户必须尝试同时或接近同时使用IPv4和IPv6地址进行连接。**这使连接设置更加复杂，使编写网络应用更加困难**。The fix, of course, is to move  to IPv6.  Unfortunately, the move to IPv6 will take  a long time. And, while it’s happening,  there will be some devices, and some  networks, that support only IPv4, some that  support only IPv6, and some that support  both.  To reach users on both IPv4 and  IPv6, popular services tend to be hosted  on servers that have both IPv4 and  IPv6 addresses. This is known as dual  stack hosting, and it further encourages centralisation  onto large hosting providers with the resources  to provide both types of address.  To get good performance, clients must try  to connect using both IPv4 and IPv6  addresses simultaneously, or near simultaneously. This further  complicates connection setup, making it harder to  write networks applications.

:orange: 双栈协议 - 链接竞争

* *双协议栈技术就是指在一台设备上同时启用IPv4协议栈和IPv6协议栈。这样的话，这台设备既能和IPv4网络通信，又能和IPv6网络通信。如果这台设备是一个路由器，那么这台路由器的不同接口上，分别配置了IPv4地址和IPv6地址，并很可能分别连接了IPv4网络和IPv6网络。如果这台设备是一个计算机，那么它将同时拥有IPv4地址和IPv6地址，并具备同时处理这两个协议地址的功能*
* 有些路径支持IPv4，有些支持IPv6；客户端-服务器应用的探测和连接超时速度很慢
* 并行检测-连接竞争，难以实现，浪费资源

:candy:NAT穿越&双栈协议复杂化连接的建立，应将中心定于云服务

## 移动性使维护长期连接困难：Increasing Mobility

![](/static/2021-01-15-02-59-35.png)

互联网协议的设计是这样的：IP地址对网络中的网络接口的位置进行编码。**一个IP地址并不代表一个设备，它代表一个设备可以连接到网络的位置**。

* 如果一个设备通过无线连接连接到网络上，但移动时改变了它所连接的WiFi或4G基站，那么该设备将被分配一个新的IP地址。
* **这有一些隐私方面的好处，但也使得与该设备保持长期的连接变得困难**。
  * 例如，当设备移动时，TCP连接失败，必须由应用程序重新建立。
  * 而UDP应用需要与他们的同行协调，以改变他们用于通信的IP地址。
  * **想保持长期连接的应用程序，或想接受传入的连接，必须处理改变IP地址的复杂性，以及在设备移动时向它们的同伴发出这种变化的信号和重新建立连接的需要**。
  * 有些东西必须跟踪每个设备的位置，以便对流量进行路由。互联网设计中的假设意味着复杂性对应用程序是可见的，而不是隐藏在网络中。

The Internet Protocols are designed such that  IP addresses encode the location of a  network interface within the network. An IP  address does not represent a device,  it represents a location where a device  can attach to the network.  If a device is attached to the  network via a wireless connection, but moves  so that it changes the WiFi or  4G base station to which it connects,  then that device will be assigned a  new IP address.  This has some privacy benefits, but makes  it difficult to maintain long-lived connections with  that device. For example, TCP connections fail,  and must be re-established by the application,  when a device moves. And UDP applications  need to coordinate with their peers to  change the IP addresses they use for  communication.  Applications that want to maintain long-lived connections,  or that want to accept incoming connections,  must deal with the complexity of changing  IP addresses, and the need to signal  such changes to their peers and reestablish  connections as the device moves.  Something has to keep track of where  each device is, in order to route  traffic. The assumptions in the design of  the Internet mean that complexity is visible  to applications, rather than hidden inside the  network.

IP地址用于编码网络位置

* 移动设备，（手机）使寻址&可达性复杂
  * 因为每次设备移动IP地址都有可能改变
  * 每次移动后必须重新建立TCP链接
  * 每次移动后必须重定向UDP数据包
  * 如果设备频繁移动，则需要复杂信令&连接管理

## 巨型企业&中心化：Hypergiants & Centralisation

![](/static/2021-01-15-03-03-37.png)

正如我们所看到的，互联网设计的几个方面都在**向集中化推进**。 网络拓扑结构正在逐渐扁平化，**并从复杂的网状对等连接**转向以**少数大型集中式服务为中心的枢纽和辐条模式，直接连接到所谓的 "眼球 "网络**，在网络的边缘，这些服务的消费者居住在那里。 这使得包括谷歌、Facebook、亚马逊、Akamai、苹果、Netflix等在内的一系列巨型内容提供商能够**占据主导地位，并使新的竞争者难以立足于此**。这对网络中立性、竞争和创新都有影响。As we’ve seen, several aspects of the  Internet’s design push toward centralisation.  The network topology is gradually flattening,  and moving away from a complex mesh  of peer connections, towards a hub-and-spoke model  centred around a small number of large,  centralised, services, directly connecting to so-called “eyeball”  networks, at the edge of the network,  where consumers of those services live.  This enables the set of hypergiant content  providers, including Google, Facebook, Amazon, Akamai,  Apple, Netflix, and so on, to dominate,  and makes it difficult for new competitors  to gain a foothold. This has implications  for network neutrality, competition, and innovation.

* 互联网拓扑结构扁平化，越来越集中化
* 对网络中立性、竞争、创新的影响

## 如何保证实时流量：Real-time Traffic

![](/static/2021-01-15-03-04-50.png)

我们还看到实时流量的稳定增长，到目前为止，**流媒体视频是网络中最主要的流量类型**--同时仍以40%的速度逐年增长。

* 流媒体视频有**相当严格的时间和质量限制**，这些都促使网络运营商提高其网络质量，并促使流媒体视频内容提供商直接与住宅边缘网络对等。
* 这**进一步刺激了集中化和网络扁平化，因为这种直接对等的方式更容易确保高质量的视频被传送给观众**。
* **流媒体视频的增加也推动了TCP拥堵控制的变化**，如谷歌的BBR算法，以及TCP替代品的开发，如QUIC，都是为了减少延迟和提高质量。 第4和第5讲将讲述其中的一些发展。 基于WebRTC的视频会议服务，如Zoom、Webex和Microsoft Teams，有更严格的延时要求。

We’re also seeing steady growth of real-time  traffic, with streaming video being, by far,  the dominant type of traffic in the  network – while still growing at ~40%  year on year.  Streaming video has reasonably strict timing and  quality constraints, and these push network operators  to improve the quality of their networks,  and push streaming video content providers to  peer directly with the residential edge networks.  This is a further incentive towards centralisation,  the flattening of the network, since such  direct peerings make it easier to ensure  high-quality video is delivered to viewers.  Increases in streaming video are also driving  changes in TCP congestion control, such as  Google’s BBR algorithm, and the development of  TCP replacements, such as QUIC, both aimed  at reducing latency and increasing quality.  Lectures 4 and 5 talk will about  some of these developments.  WebRTC-based video conferencing services, such as Zoom,  Webex, and Microsoft Teams, have even stricter  latency requirements.

视频流媒体主导互联网流量，每年增长>40%。

* 包丢失→质量受损或延迟增加
* 驱动集中化和CDN直连→更容易管理质量。
* 驱动TCP拥塞控制和TCP替换→BBR算法，QUIC；低延迟。
* 互动实时媒体有更严格的约束
* WebRTC、Zoom、VoIP、游戏......→非TCP传输以降低延迟

---

![](/static/2021-01-15-03-12-54.png)
![](/static/2021-01-15-03-15-05.png)


COVID-19大流行病加速了这些影响。 这张图显示了2020年3月开始的初始封锁时的互联网流量测量。它显示，许多住宅网络所承载的互联网流量总量增加了20-25%，因为人们转而在家工作。 它还显示了移动流量的相应下降。 不过，这种流量的转移并不是均匀分布的。The COVID-19 pandemic has accelerated these effects.  This graph shows measurement of Internet traffic  as the initial lockdowns started in March  2020. It shows that many residential networks  saw a 20-25% increase in the total  amount of Internet traffic they were carrying,  as people shifted to working from home.  It also shows a corresponding drop in  mobile traffic.  That shift in traffic wasn’t evenly distributed,  though.

---

第二张图显示了在类似时间段内通过Webex（流行的视频会议平台之一）发送的视频数据量。在不到一周的时间里，使用量增长了大约20倍，而且此后一直在增长。 令人印象深刻的是，互联网足够灵活和强大，足以支持其使用方式的这种快速变化。 问题是，我们能否在保持这种灵活性的同时提高质量？第6和第7讲将进一步讨论这个话题。This second graph shows the amount of  video data being sent over Webex,  one of the popular video conferencing platforms,  over a similar time period. Usage grew  by around a factor of 20 in  less than a week, and has continued  growing since.  Impressively, the Internet was flexible and robust  enough to support this rapid change in  how it is used.  The question is, can we maintain such  flexibility while also improving quality? Lectures 6  and 7 discuss this topic further.

---

住宅网络在COVID-19 lockdown开始时，一夜之间流量增加了>20%。

* 移动网络的流量下降了约10%。

视频会议提供商的流量出现大规模增长

* 互联网的灵活性足以支持这种转变--我们能否在提高质量的同时，保持这种灵活性

## 安全假设：Pervasive Monitoring and Trust in the Network

网络中普遍存在的监控和信任

假设的最后转变是围绕着安全。 互联网协议最初是为支持研究网络而设计的，有相对较少的用户，他们有相当紧密的目标，并且几乎没有提供安全保障。 **多年来，协议已经改变，以提供越来越复杂的安全和保护，防止攻击**。爱德华-斯诺登的揭露加速了这一趋势，因为它提高了人们对大规模政府监控的认识，但在这之前，为了应对黑客和犯罪活动，安全性的提高已经开始。 **未来的一个重大挑战将是如何平衡执法部门以有针对性的方式访问和监控一些流量的需求，同时保护隐私和防止攻击者**。The final shift in assumptions has been  around security.  The Internet Protocols were originally designed to  support a research network, with a relatively  small set of users, who had reasonably  closely aligned goals, and provided little in  the way of security.  Over the years, the protocols have changed  to provide increasingly sophisticated security and protection  from attacks. The Edward Snowden revelations accelerated  that trend, by increasing awareness of large-scale  government surveillance, but the increase in security  started before that, in response to hacking  and criminal activity.  A significant challenge going forward will be  in balancing the needs of law enforcement  to access and monitor some traffic,  in a targeted manner, while preserving privacy  and protecting against attackers.

## 网络创新：Innovation in the Network

![](/static/2021-01-15-03-16-24.png)

互联网现在是一个全球部署的网络。像任何大型系统一样，**随着时间的推移，它变得越来越僵化，越来越难以改变**。

* **从IPv4到IPv6的缓慢过渡就是这种僵化的一个例子**。
* **另一个例子是更新TCP的困难，以更好地支持低延迟服务和提高性能**。
  * **NAT、防火墙和其他中间箱的广泛使用使得这种改变变得出奇的困难**。
  * 我们现在开始看到取代TCP的严肃尝试，如QUIC协议，它采用普遍的加密和UDP隧道，以避免传统网络的这种干扰，但目前还不清楚这些会不会成功。
* 最后，我们必须**考虑在技术和商业考虑的驱动下，推动集中式服务和应用的效果，以及这些是否对消费者和网络用户有利，或者没有**。
  * 这种向少数超大型服务提供商的转变是网络设计的必然结果，还是商业和监管环境的必然结果，以及我们应该在多大程度上试图通过网络的技术变革来影响它？

The Internet is now a globally deployed  network. Like any large system, it becomes  increasingly ossified, increasingly difficult to change,  over time.  The slow transition from IPv4 to IPv6  is one example of this ossification.  Another example would be the difficulty in  updating TCP, to better support low-latency services  and improve performance. The widespread use of  NATs, firewalls, and other middleboxes makes such  changes surprisingly difficult. We’re now starting to  see serious attempts to replace TCP,  with protocols such as QUIC that employ  pervasive encryption and tunnel over UDP to  avoid such interference by the legacy network,  but it’s not clear that these will  succeed.  Finally, we must consider the effects of  the push towards centralised services and applications,  driven by both technical and business considerations,  and whether these are beneficial to consumers  and users of the network, or not.  Is this shift towards a small number  of hypergiant service providers an inevitable consequence  of the design of the network,  or of the business and regulatory environment,  and to what extent should we attempt  to influence it through technological change in  the network?

网络协议还能改吗？

* NAT、防火墙、中间盒→TCP现在很难改了。
* 传输加密，UDP上的隧道→QUIC? 无孔不入的加密技术，战胜无孔不入的监控和监视？
* 协议僵化是一个真正的问题
* 智能边缘和哑巴网络的模式是否合适？
* 新的创业公司能否与超巨头竞争？
* 几种力量在推动中心化--我们能否进化网络来应对这些？
* 我们能否实现去中心化的网络

---

如何在碎片化的网络中建立连接？

* 如何加密防范无孔不入的监控，防止传输骨化？
* 如何降低延迟，支持实时和交互式内容？
* 我们如何适应无线网络的变化无常？
* 如何识别和发布内容？我们如何管理对DNS和命名控制权的争夺？
* 我们如何管理域间路由以高效地传递内容？
* 如何将网络再分散化？