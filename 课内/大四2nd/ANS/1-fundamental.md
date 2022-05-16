# Content

* [Content](#content)
* [需求（连通性）:Requirements(Connectivity)](#需求连通性requirementsconnectivity)
* [资源共享-需求：Requirements (Resource Sharing)](#资源共享-需求requirements-resource-sharing)
* [支持公共服务-需求：Supporting Common Services](#支持公共服务-需求supporting-common-services)
* [网络架构：Network Architecture](#网络架构network-architecture)
* [Reference Network Architectures](#reference-network-architectures)
* [封装，多路分解，协议标准：Encapsulation, Demultiplexing, Conformance](#封装多路分解协议标准encapsulation-demultiplexing-conformance)
* [分层不仅是抽象概念：Layring:more than an abstract concept](#分层不仅是抽象概念layringmore-than-an-abstract-concept)
* [网络两个关键概念: Two Key concepts in Networking](#网络两个关键概念-two-key-concepts-in-networking)
* [数据包交换：Packet Switching](#数据包交换packet-switching)
* [最小生成树算法-避免环路：Avoiding Loops, Spanning Tree Algorithm](#最小生成树算法-避免环路avoiding-loops-spanning-tree-algorithm)
* [互联路由：Internetworking](#互联路由internetworking)
* [Global Internet](#global-internet)
* [无类域间路由 (CIDR) & 子网subnet](#无类域间路由-cidr--子网subnet)
* [ARP, DHCP-Global to Local](#arp-dhcp-global-to-local)
* [==========](#)
* [域内路由-Routing（Intradomain)](#域内路由-routingintradomain)
* [RIP距离向量路由 - Distance Vector Routing](#rip距离向量路由---distance-vector-routing)
* [OSPF-链路状态路由：Link State Routing](#ospf-链路状态路由link-state-routing)
  * [最短路径算法-Forward Search(Shortest Path Algorithm)](#最短路径算法-forward-searchshortest-path-algorithm)
* [==========](#-1)
* [BGP-域间路由：Interdomain Routing](#bgp-域间路由interdomain-routing)
* [==========](#-2)
* [建立可靠服务TCP/UDP-Building reliable (end to end) services](#建立可靠服务tcpudp-building-reliable-end-to-end-services)

# 需求（连通性）:Requirements(Connectivity)

Links, nodes, and clouds
链接、节点和云

* Point-to-point; multiple access 点对点;多路访问
  * Scale problematic if all nodes need to be directly connected to each other 如果所有节点都需要直接相互连接，则扩展存在问题
* lndirect connectivity 间接连通性
  * Switched networks (switches forward data between links) 交换网络（**交换机**在链路之间转发数据）
    * 构建大规模局域网的方式
  * Internetwork (routers/gateways forward messages from one network to another) 互联网（**路由器**/网关将消息从一个网络转发到另一个网络）

# 资源共享-需求：Requirements (Resource Sharing)

How do hosts share the network to communicate at the same time?。主机如何共享网络**同时进行通信**？。

Mutliplexing 多路复用

* Synchronous **Time** Division Mutliplexing (STDM)- divide time equally inRound-Robin 同步时分多路复用 (STDM) - **在循环中均分时间片**
* **Frequency** Division Multiplexing (FDM)-e.g. TV transmission 频分复用 (FDM) - 例如电视传输
* **STDM &FDM存在某流（主机对）没有数据发送，占用的物理链路会空闲**
* Statistical multiplexing (avoid idle time) **统计复用（避免空闲时间**）
  * 最常用多路复用形式
  * 然而，不 同于STDM的是，每个流的数据是根据需要传输的，而不是在一个预先规定的时间片进 行传输。这样，如果只有一个流有数据要发送，那么它不必等到它的时间片到来就可以发 送数据，这样，也就不会岀现分配给其他流的时间片白白浪费的情况。正是这种避免空闲 时间的方法使分组交换具有较高的效率
  * **统计复用，需要机制来限制发送，使其他流有机会传输数据**
    * 统计多路复用定义每 个流在给定的时间内允许传输的数据块大小的上界。这个限定大小的数据块通常称为一个 分组(packet) 9用以与应用程序可能传输的任意大小的消息】message)作区分。因为分 组交换网限制分组的最大尺寸，所以主机可能无法用一个分组发送一条完整的消息。源节 点可能需要将消息分划为几个分组，接收者再把这些分组重新组装成原始消息。
* ，由多个用户发送的数据可在构成网络的多条物理链路上被多路复用。
  * ![](/static/2022-05-15-21-50-10.png)

Packet switching 分组交换

* Break arbitrarily large messages to limited-size block of data (packet) 将任意大的消息分解为有限大小的数据块（数据包）
* Switch service policies (e.g., First-In-First-Out- FIFO) 切换服务策略（例如，先进先出-FIFO）

# 支持公共服务-需求：Supporting Common Services

Application programs on hosts need to communicate in meaningful ways 主机上的应用程序需要以有意义的方式进行通信

* Build all complicated functionality in the applications? 在**应用程序中构建**所有复杂的功能？
* More logical to implement common services once (logical communication channels) 更合乎逻辑地是，实现**公共服务**（逻辑沟通渠道）
  * Sending application puts data on the wire; network delivers to the receiver 发送应用程序将数据放在网络上；网络传递给接收者
  * 应用之间直接通信，应用能看见数据知道如何操作数据，不用关心底层网络

如何有效实现公共网络服务？（网络服务需要能被泛化，为不同应用提供无缝服务）

* ldentify common communication patterns and application requirements 确定常见的通信模式和应用程序要求
* 应用要分类，提供合适的服务。有两种服务
  * Stream vs. packet service
  * Ordering; reliability; security

公共服务作用：

* Fill in the semantic gap What application expects; what the underlying technology can provide 填补语义空白 应用程序期望什么；什么 底层技术可以提供

# 网络架构：Network Architecture

指导网络的设计和实施 Guide the design and implementation of networks

Layering

* Introduce abstractions: start with services offered by the underlying hardware 引入抽象：从底层硬件提供的服务开始
  * 每层能解决不同的问题，模块化，分离关注点
* Add sequence of layers, each providing a higher (more abstract) level of service 添加层序列，每层都提供更高（更抽象）的服务级别
* ![](/static/2022-02-07-22-14-48.png)
  * 4层模型
    * 进城到进程通道，区分不同类型服务，多路复用
      * request/reply protocol

Communication Protocols 分层由协议驱动

* Abstract objects making up the layers 构成层的抽象对象
* Communication service for higher-level objects 高层对象的通信服务
* Protocols provide two interfaces 协议提供两个接口
  * Service interface for objects on the same computer **同一台计算机上**对象的服务接口
    * 向上，or向下，取决于主机是出于通信的接收端还是发送端
  * Peer interface to its counterpart on another machine 与另一台机器上对应的对等接口

# Reference Network Architectures

![](/static/2022-02-07-22-34-10.png)

* Except at the hardware layer (direct communication over a link),each protocol communicates with its peer indirectly: by passingmessages to lower-layer protocols 除了在硬件层（通过**链路直接通信**），每个协议都与它的**对等体间接通信：通过将消息传递给较低层协议**
* Open Systems Interconnection (oSl) architecture 开放系统互连 (osl) 架构
* Internet (TCP/IP) architecture 互联网 (TCP/IP) 架构
  * IP hour glass

# 封装，多路分解，协议标准：Encapsulation, Demultiplexing, Conformance

协助通信3个元素，保证了系统之间连接：数据封装，去复用，协议服从

Encapsulation

* Information transmitted as stream of bytes 作为**字节流**传输的信息
* Receivers need to be instructed how to handle the message at each layer; protocol headers tell them that; 需要指示**接收者如何在每一层处理消息**；**协议头**告诉他们；
* At each layer, data encapsulated in a protocol header - payload manner 在每一层，数据以协议头-有效载荷的方式封装
  * ![](/static/2022-02-07-22-36-40.png)

(De)multiplexing 多路分解

![](/static/2022-02-07-22-55-00.png)

Conformance to standards 符合标准

* At each layer, systems need to know how to interpret information received。 在每一层，系统都需要知道如何解释接收到的信息。
* Adhere to protocol specifications 遵守协议规范
* E.g..any browser should be able to translate a web page fetched from a given server (in the same way) 例如，任何浏览器都应该能够翻译从给定服务器获取的网页（以相同的方式

# 分层不仅是抽象概念：Layring:more than an abstract concept

分层不仅是抽象概念，还指导了如何设计网络通信架构

![](/static/2022-02-07-22-51-37.png)
![](/static/2022-02-07-22-52-15.png)

* Linux (2.4) kernel TCP/IP architecture Linux (2.4) 内核 TCP/IP 架构
* Function call path from device driver to application and vice versa 从设备驱动程序到应用程序的函数调用路径，反之亦然

# 网络两个关键概念: Two Key concepts in Networking

Main goal of the underlying network infrastructure 底层网络基础设施的主要目标

* Get packets/frames from A to B 获取从 A 到 B 的数据包/帧
* ldentify A and B in meaningful (and unique) way(s) 以有意义（和独特）的方式识别 A 和 B

Addressing

* Uniquely identify hosts 唯一标识主机
* Different scopes: in LAN, and over the glol bal Internet 不同的范围：在 LAN 中和在全球 Internet 上

(Hop-by-hop) Packet forwarding （逐跳）数据包转发

* Different scopes: in LAN(switching), over the globallnternet (routing) 不同的范围：局域网（交换），全球互联网（路由）
  * LAN如何尽快的将数据包从一个输入端口发送到另一个

# 数据包交换：Packet Switching

Aswitch allows to interconnect links to form a larger network

* Multi-input/multi-output device transferring packets from an input to one or more outputs. 多输入/多输出设备将数据包从输入传输到一个或多个输出。
* lf a switch has enough aggregate bandwidth it can support many hosts at full speed (not possible in shared media networks to transmit continuously at line speed) **如果交换机有足够的聚合带宽，它可以全速支持许多主机**（在共享媒体网络中不可能以线速连续传输）

How does a switch decide which output port to place each packet on? 交换机如何决定将每个数据包放置在哪个输出端口上？

* Look at the packet header for an identifier **查看数据包头中的标识符**
  * 链路层

Connectionless (datagram) approach 无状态连接 （将数据包交换到不同端口）

* Others possible (virtual circuit; source routing,label switching) 其他可能方法（虚拟电路；源路由，标签交换）

---

![](/static/2022-02-07-23-28-44.png)

Simplest approach: flooding 泛洪

* Switch listens in promiscuous mode; accept frames on its inputs, forward on all other outputs Switch **以混杂模式监听；在其输入上接受帧，在所有其他输出上转发**

Consult a forwarding table to make decision 查阅转发表做出决定

* Tables can be configured statically, but impractical(doesn't scale) 表格可以静态配置，但不切实际（无法扩展）
* **Learning bridges算法**: * inspect source address of received frames; start populating table 学习网桥（主键学习拓扑）： 
  * 检查接收帧的源地址；开始填充表
  * ![](/static/2022-02-07-23-33-30.png)
* if host not on table, flood 如果主机不在表中，泛洪
* Use timeouts to refresh network topology 使用超时刷新网络拓扑
  * 需要能解决网络失败问题

# 最小生成树算法-避免环路：Avoiding Loops, Spanning Tree Algorithm

Introduce hierarchy among the bridges (switches)

![](/static/2022-02-08-14-43-14.png)

* 虚线 - 不转发，避免loop
* 。Lowest ID switch is **root**; always forward frames over all its ports **最低ID**交換機是**root**；始终通过其所有端口转发帧
  * **需要唯一标识区分**
  * root做泛洪
  * 交换机需要记录他们离root多远，决定是否负责将流量转发到这条路由，或者是否有最短路径存在
    * hop by hop添加交换机，交换情报
* Each bridge computes shortest path to the root; notes which of its ports is on this path 每个网桥计算到根的最短路径；说明它的哪个端口在这条路径上
* Bridges on a LAN elect **designated bridge** (responsible for forwarding frames to root); **bridge closest to root on each LAN** LAN 上的网桥选择指定网桥（负责将帧转发到根）；每个 LAN 上**离根最近的网桥**（指定网桥）
  * 网段A，选B5为指定网桥，
  * 每个交换机先只有0跳到root，自己Id到自己。当收到来自网桥的更小Id的数据包，进行转发
    * B5(Lan A指定网桥)收到更低B1的数据包，（视为root），B5到B1有1跳，开始转发
    * 当网桥收到冲突信息，B5，B3的
      * B5发现B3有B1(root)信息，距离为2 hops。然而B5距离root B1，只有1 hop。**因此LAN A的指定网桥是B5，所以B3不会将流量转发至Lan A端口**
    * Lan C
      * B3离root 2Hops（B2-B1，或者B5-B1），发送 配置信息给C，，然而B2离root 1hop，**所以B3也不是Lan C的指定网桥**
    * **所以B3做消息备份用，不会给Lan进行任何数据包转发，只是在Lan上收到数据包，可能会发送到本地连接站点（其他端口）**
* Bridges only forward frames on ports for which are designated bridges 网桥仅在指定网桥的端口上转发帧

# 互联路由：Internetworking

![](/static/2022-02-22-22-45-32.png)

* 标识符问题
* learning bridges, spanning tree算法不能应用于global networks
* IP在整个path中是唯一的

# Global Internet

Global addresses

![](/static/2022-02-23-13-37-39.png)

* Terminal hardware addresses unique (based on manufacturer numbers); but flat; no cluesfor switching/routing **终端硬件地址唯一（基于制造商编号）；但平坦；没有切换/路由的线索**
* Globally unique (IP addresses) are hierarchical: have a network and host part;hosts attached on same network, have samenetwork part **全局唯一（IP 地址）是分层的**：具有**网络**和**主机**部分；连接在同一网络上的主机，具有相同的网络部分
  * 前面几位保留字，指定地址类型
* 3 main classes of IP addresses, for different-sized networks **3 种主要类型的 IP 地址**，适用于不同规模的网络

Datagram forwarding in IP，如何根据IP进行数据转发

![](/static/2022-02-23-13-37-49.png)

* If destination on same network; deliver packet locally using LAN switching (see before) **如果目的地在同一网络上；使用 LAN 交换在本地传送数据包（见前文）**
* Else, deliver to next hop router, based on network number of iP address **否则，根据IP地址的网络号传递到下一跳路由器**
* if no matching entry in own forwarding table, deliver to default router **如果在自己的转发表中没有匹配的条目，则传递到默认路由器(一个路由器接口)/网关**

---

# 无类域间路由 (CIDR) & 子网subnet

Subnetting

* Large campuses have lots of internal networks; each need a unique network number: a Class B/C address block **大校园有很多内部网络；每个都需要一个唯一的网络号：一个 B/C 类地址块**
  * Address assignment inefficiency and (core) routing table explosion **地址分配效率低下和（核心）路由表爆炸**
    * 因为每个路由器都需要了解互联网上的每个网络
* **解决**
  * Allocate a single network number to several physical networks **将一个网络号分配给多个物理网络**
  * Subnet mask used to introduce subnet number (virtually expand network part over the host part of the address): **用于引入子网号的子网掩码（实际上是在地址的主机部分之上扩展网络部分）**：
    * 虚拟扩展网络部分
  * Network - subnet - host parts **网络 - 子网 - 主机部分**
  * E.g.,130.209.0.0/16 vs.130.209.1.0/24,130.209.2.0/24,etc.
  * When routing. bitwise AND between own subnet mask and destination IP address gives subnet number 路由时。**自己的子网掩码和目标 IP 地址之间的<按位与>给出子网号**
  * ![](/static/2022-02-23-14-14-08.png)

Classless Inter-Domain Routing (CIDR)

* Subnetting can help to structure networks internally but cannot extend global addressspace: still, a network with >256 hosts needs a Class B address **子网化有助于在内部构建网络，但不能扩展全局地址空间：不过，拥有 >256 台主机的网络需要 B 类地址**
* Instead, can give out the right number of Class C addresses to cover the number of hosts(more Class C networks available - 2^21) **相反，可以给出正确数量的 C 类地址以覆盖主机数量（更多可用的 C 类网络 - 2^21**）
* But this bloats global routing (tables) state **但这会使全局路由（表）状态膨胀**
* CIDR helps aggregate routes: single entry to tell us how to reach different networks。Routing protocol must understand that network number can be of any length **CIDR 帮助聚合路由：单个条目告诉我们如何到达不同的网络。路由协议必须了解网络号可以是任意长度**
* Use contiquous Class C addresses; mask at common most significant bits (supernetting) **使用连续的 C 类地址；掩码公共最高有效位（超网）**
* CIDR用可变长子网掩码 （VLSM），根据各人需要来分配IP地址，而不是按network-wide rule。所以，网络/主机的划分可以在地址内的任意位置进行。这个划分可以是递归进行的，即通过 增加掩码位数，来使一部分地址被继续分为更小的部分。整个互联网都在使用CIDR/VLSM网络地址。不过在其他方面，尤其是大型私人网络，它也有应用。在普通大小的局域网里则较少应用，因为这些局域网一般使用私有网络。
* CIDR的另一个好处就是可以进行前缀路由聚合。例如， 16个原来的C类（/24）网络现在可以聚合在一起，对外显示了一个/20的网络了（如果这些网络的的地址前20位都相同）。两个对齐的/20网络又可进一步聚合为/19，依此类推。这有效地减少了要对外显示的网络数，防止了'路由表爆炸'，也遏制了互联网进一步扩大。

---

# ARP, DHCP-Global to Local

Address Resolution Protocol (ARP)

* In order to deliver information to terminal, need to be able to translate to physical address (e.g., Ethernet) from **IP 为了向终端传递信息，需要能够从 【IP 转换为物理地址】（例如以太网**）
  * To send an IP datagram to a host on the network, broadcast an ARP query ("which station has IP address X?")  **要将 IP 数据报发送到网络上的主机，请广播 ARP 查询（“哪个站的 IP 地址为 X**？”）
  * Every hosts receives query and if its own IP matches, it sends a reply **每个主机都收到查询，如果自己的 IP 匹配，它会发送回复**
* Senders (and receivers) store the IP-physical address mapping to local ARPcache/table; use timer to expire entries **发送方（和接收方）将 IP 物理地址映射存储到本地 ARP cache/表；使用计时器使条目过期**

Dynamic Host Configuration Protocol (DHCP)

* On boot, each hosts needs basic information in order to send (and receive) traffic,e.g., IP address (and subnet mask), default gateway,DNS servers, etc. **在启动时，每台主机都需要【基本信息】才能发送（和接收）流量，**。
  * 例如 IP 地址（和子网掩码）、默认网关、DNS 服务器等
* Sends a DHCP DISCOVER IP broadcast (to 255.255.255.255) **发送 DHCP DISCOVER IP 广播（到 255.255.255.255**）
* Routers don't propagate message onto other networks (prevent global Internet flooding) **路由器不会将消息传播到其他网络（防止全球互联网泛滥**）
* DHCP server replies with configuration information.  **DHCP 服务器回复配置信息**。
* DHCP server manages address allocation policy DHCP **服务器管理地址分配策略**

# ==========

# 域内路由-Routing（Intradomain)

![](/static/2022-02-23-14-39-53.png)

A single (administrative) domain may consist of many physical networks, connected by routers **单个（管理）域可能由许多物理网络组成，通过路由器连接**

* Can span a large geographical area **可以跨越很大的地理区域**

Routers need to know how to forward datagrams between different interfaces. **路由器需要知道如何在不同接口之间转发数据报。**

* Similar to switching but with much more structurel hierarchy:route (mainly) to networks (but also to hosts) **类似于交换，但具有更多的结构层次结构：路由（主要）到网络（但也到主机）**
* Hide state (individual host information) **隐藏状态（个别主机信息）**

Routing is the process by which each router builds its forwarding table: each row contains mapping from a network number to an outgoing interface and MAC **路由是每个路由器构建其转发表的过程：每一行包含从网络号到传出接口和 MAC 的映射**

Deal with reachability and efficiency **处理可达性和效率**

* Find lowest cost path based on distributed,self-organising algorithms (protocols) **基于分布式、自组织算法（协议）找到成本最低的路径**
* Metrics/weights assigned as cost important **分配为成本重要的指标/权重**

Deal with (link/node) failures **处理（链接/节点）故障**

* Reroute over redundant links **通过冗余链路重新路由**

# RIP距离向量路由 - Distance Vector Routing

![](/static/2022-02-23-14-43-23.png)

Each node constructs a one-dimensional array containing distances to all other nodes **每个节点构造一个一维数组，其中包含到所有其他节点的距离**

* Distribute this to immediate neighbours **将此分发给直接邻居**
* Starting assumption: each node knows cost to directly connected neighbours **开始假设：每个节点都知道直接连接的邻居的成本**

Consider Node A

* F tells A that it can reach G at a cost of 1 **F 告诉 A 它可以以 1 的成本到达 G**
  * 与F开始交换信息后，，
* A knows it can reach F at a cost of 1; add to get the cost of reaching G; 2< infinity, so replace entry in table; and so on.. .**A 知道它可以以 1 的成本到达 F；加起来到达 G 的成本； 2< infinity，所以替换表中的条目；等等**

Use periodic or triggered updates to refresh topology 使用**定期**或**触发更新**来刷新拓扑

Count-to-infinity problem **该协议存在数到无穷大问题**

* ![](/static/2022-02-23-14-54-45.png)
* Split horizon: do not send routes learned from a neighbour back to this neighbour * **解决：水平分割-不将从邻居学到的路由发回给这个邻居**
  * Still, only works with loops involving two nodes; check what happens with larger routing loops **不过，仅适用于涉及两个节点的循环；检查较大的路由循环会发生什么**

# OSPF-链路状态路由：Link State Routing

![](/static/2022-02-23-15-07-44.png)

Instead of talking only to direct neighbours and telling them everything it knows, each node talks to all other nodes but only tells them what it knows for sure (state of directly connected links) **每个节点不是只与直接邻居交谈并告诉他们它知道的一切，而是与所有其他节点交谈，但只告诉他们它肯定知道的内容（直接连接链路的状态**）

Two main mechanisms

* Reliable flooding: each node send link-state information out on all its directly connected links (Link State Packet-LSP); each receiver subsequently forwards out on all of its links (flooding) **可靠泛洪：每个节点在其所有直接连接的链路上发送链路状态信息（Link State Packet-LSP）；每个接收器随后在其所有链路上转发出去（泛滥**）
* Route calculation from sum of all accumulated link-state knowledge **从所有累积的链路状态知识的总和计算路由**

Each LSP contains: 每个LSP包含

* ID of creator node; list of directly connected neighbour s with costs; sequence number;Time-to-Live (TTL) **创建者节点ID；具有成本的直连邻居列表；序列号；生存时间** (TTL)

Similar to RIP, LSPs are generated if: 与 RIP 类似，在以下情况下会生成 LSP：

* Aperiodic timer expires; change in topology (link failure) detected **非周期定时器到期；检测到拓扑变化（链路故障**）

Route calculation 路由计算

* Once a node has a copy of the LSP from every other node, it can compute a complete map of the topology and decide on best route for every destination **一旦一个节点从每个其他节点获得了 LSP 的副本，它就可以计算一个完整的拓扑图并决定每个目的地的最佳路由**
* Dijkstra's shortest-path algorithm **Dijkstra 的最短路径算法**

## 最短路径算法-Forward Search(Shortest Path Algorithm)

shortest - performance

![](/static/2022-02-23-15-16-57.png)

1. D只知道0成本到自己
2. D邻居，B(11),C(2)
   1. tentative list
3. 路径C,2,C移到确认list
   1. 最短路径。
   2. 查看C的邻居
4. D到B（从C，5成本） & D到A（从C,12成本）
   1. 临时list里之前D-B的entry替换成D-C-B(5cost)

# ==========

# BGP-域间路由：Interdomain Routing

![](/static/2022-02-23-15-25-05.png)

Global internet an arbitrarily-interconnected set of Autonomous Systems **(AS) 全球互联网是一组任意互连的自治系统 (AS**)

* Additional way to hierarchically aggregate routing information **分层聚合路由信息的其他方法**

Global routing between AS border routers AS **边界路由器之间的全局路由**

* Use default routes to upstream providers; **使用到上游供应商的默认路由；**
* eventually, a backbone' router will know how to reach everything **最终，骨干路由器将知道如何访问所有内容**
* internally each AS can use any routing protocol they choose **在内部，每个 AS 可以使用他们选择的任何路由协议**
* Scale problematic: even with CIDR,~150Kprefixes for Internet backbones **规模问题：即使使用 CIDR，互联网骨干网的前缀约为 150K**

interdomain routing only concerned with (loop-free) reachability (optimality too hard) **域间路由只关心（无环路）可达性（最优性太难）**

* “You can reach this network through this AS" **“你可以通过这个AS到达这个网络”**
* Policies a major factor: providers charge for upstream bandwidth; a lot of ASes want to prevent transit (don't advertise reachability) **政策是主要因素：提供商对上行带宽收费；很多 AS 想要阻止传输（不要宣传可达性）**

BGP(v4) speaker(s) in each AS

* BGP advertises complete paths as an enumerated list of ASes to reach a particular network (prevents loops) **BGP 将完整路径作为 AS 的枚举列表通告以到达特定网络（防止循环**）
* (16-bit)AS numbers need to be unique; not a problem for non-transit (stub) networks （**16 位）AS 编号需要是唯一的；对于非传输（存根）网络来说不是问题**
* Speakers not obligated to advertise routes to destinations. even if they have one **Speakers没有义务公布到目的地的路线。即使他们有一个**

# ==========

# 建立可靠服务TCP/UDP-Building reliable (end to end) services

Datagram forwarding unreliable 数据报转发不可靠

* Transmission errors; packet/frame drops; out-of-order delivery 传输错误；丢包/丢帧；无序交货
* IP offers a best-effort service IP 提供尽力而为的服务
* Integrity mechanisms available at different layers (e.g., checksums) 不同层可用的完整性机制（例如校验和）

Transmission Control Protocol 传输控制协议

* Error correction (retransmission); sequencing/acknowledgment; flow control 纠错（重传）；排序/确认；流量控制
* Fair (network) resource usage 公平（网络）资源使用
* Applications see a stream of bytes; TCP manages everything; overhead of error control and connection establishment 应用程序看到一个字节流； TCP 管理一切；错误控制和连接建立的开销

User Datagram Protocol (UDP)

* Simple unreliable service on top of IP 基于 IP 的简单不可靠服务
* Gives applications control over datagram size; transmission rate; etc.。 使应用程序可以控制数据报的大小；传输速率;等等..
* Can exhaust network bandwidth 会耗尽网络带宽
* Preferable for application where timeliness more important than reliable delivery 适用于及时性比可靠交付更重要的应用

The sockets abstraction 套接字抽象

* Further de-multiplexing of communication flows within a host **主机内通信流的进一步解复用**
* lP address / port (number) to identify a flow at any given time **IP地址/端口（编号）在任何给定时间识别流**
