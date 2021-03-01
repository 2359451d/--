# The Changing Internet

前面有一部分未补全

Content

* [The Changing Internet](#the-changing-internet)
* [物理层：Physical Layer](#物理层physical-layer)
  * [信道：Channel](#信道channel)
  * [编码 vs 调制：Encoding vs Modulation](#编码-vs-调制encoding-vs-modulation)
  * [基带信号 vs 宽带信号](#基带信号-vs-宽带信号)
  * [有线（数字）信道编码-基带传输：Encoding Data onto Wired Channel](#有线数字信道编码-基带传输encoding-data-onto-wired-channel)
    * [基带编码：Baseband Data Encoding](#基带编码baseband-data-encoding)
  * [无线（模拟）信道编码-载波调制：Encoding Data onto Wirele sChannels](#无线模拟信道编码-载波调制encoding-data-onto-wirele-schannels)
    * [（数字信号）调制模式：Modulation Scheme](#数字信号调制模式modulation-scheme)
  * [扩频通信：Spread Spectrum Communication](#扩频通信spread-spectrum-communication)
  * [香农定理-物理链路特性&局限性：Physical Link Characteristics and Limitations](#香农定理-物理链路特性局限性physical-link-characteristics-and-limitations)
* [数据链路层：Data Link Layer](#数据链路层data-link-layer)
  * [装帧&寻址：Framing&Addressing](#装帧寻址framingaddressing)
  * [介质访问控制&CSMA技术：Media Access Control](#介质访问控制csma技术media-access-control)
* [【】网络层：Network Layer](#网络层network-layer)
  * [IP协议](#ip协议)
  * [IP寻址：Addressing](#ip寻址addressing)
  * [路由&自治系统：Routing](#路由自治系统routing)
    * [AS&域间选路协议：Inter-Domain Routing Protocol](#as域间选路协议inter-domain-routing-protocol)
    * [域间选路协议&BGP：Inter-Domain Routing & BGP](#域间选路协议bgpinter-domain-routing--bgp)
  * [转发&尽力而为传输:Forwarding & best-effort](#转发尽力而为传输forwarding--best-effort)
* [传输层：Transport Layer](#传输层transport-layer)
  * [UDP](#udp)
  * [【】TCP](#tcp)
    * [TCP包格式：Packet Format](#tcp包格式packet-format)
    * [【】TCP3次握手：Handshake](#tcp3次握手handshake)
    * [【】TCP拥塞控制：Congestion Control](#tcp拥塞控制congestion-control)
* [高级层协议：Higher Layer Protocols](#高级层协议higher-layer-protocols)
  * [会话层-管理连接：Session Layer-Manage connections](#会话层-管理连接session-layer-manage-connections)
  * [表示层：Presentation Layer](#表示层presentation-layer)
  * [应用层：Application Layer](#应用层application-layer)
  * [协议标准重要性：Importance of Protocol Standards](#协议标准重要性importance-of-protocol-standards)
* [互联网变迁-架构设想：The Changing Internet-Architectural Assumptions](#互联网变迁-架构设想the-changing-internet-architectural-assumptions)
  * [【】寻址&可达性改变：Changes in Addressing & Reachability](#寻址可达性改变changes-in-addressing--reachability)
    * [IPV4地址用尽：Address Exhaustion](#ipv4地址用尽address-exhaustion)
    * [IPV6部署慢：Slow Deployment](#ipv6部署慢slow-deployment)
    * [【】现代互联网-建立连接（NAT穿越）：Establish Connectivity in Modern Internet](#现代互联网-建立连接nat穿越establish-connectivity-in-modern-internet)
  * [挑战-移动性的增加：Increasing Mobility](#挑战-移动性的增加increasing-mobility)
  * [巨型企业&中心化：Hypergiants & Centralisation](#巨型企业中心化hypergiants--centralisation)
  * [如何保证实时流量：Real-time Traffic](#如何保证实时流量real-time-traffic)
  * [网络创新：Innovation in the Network](#网络创新innovation-in-the-network)

# 物理层：Physical Layer

![](/static/2021-01-14-15-40-40.png)

物理层允许传输原始比特流，进行通信 enables communication - transmission of raw bitstream

* 插头类型，线类型（电气特性）
* 如何编码bit至信道上？
  * 基带编码 baseband encoding
  * 载波调制 carrier modulation
* 信道容量？

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
    * 载波调制（模拟信道） - 载波调制
  * **有线信道** - 通过光纤，双绞线传输
    * 基带传输（数字信道） - 常用编码，曼彻斯特编码

## 编码 vs 调制：Encoding vs Modulation

编码

* 将数据/信号转为**数字信号**

调制

* 将（数字/基带）数据/信号转为**模拟信号**

![](/static/2021-01-14-16-52-09.png)

## 基带信号 vs 宽带信号

![](/static/2021-01-14-16-55-50.png)

## 有线（数字）信道编码-基带传输：Encoding Data onto Wired Channel

> 信号通常直接编码至数字信道，通过改变信道的性质（电线的电压，光纤的镭射密度），形成基带 signal usually directly encoded onto the channel, by varing some property of channel(voltage applied to an electrical cable, intensity of laser in optical fibre), and occupies baseband region

基带信号(基带传输)

* 将数字信号`0`和`1`使用不同的电压表示,然后再送到 数字信道上去传输
  * **直接在数字信道上传输信号** --- 基带传输
* 基带信号源
  * 信源的信号，计算机输出的文字，图像等数据信号都是基带信号

![](/static/2021-01-14-16-03-52.png)

:orange:比特率bitrate

* Nyquist’s theorem 奈奎斯特定律
  * 信息速率
  * 理想低通信道信息极限传输速率=`2Blog 2V` 比特/秒（信息传输速率，比特率，bps，表示单位时间内传递的比特数）
  * <font color="red">对于理论上的无噪音nosiy-free channel线路，速率可以到达无穷大(理想状态下)</font>
* `Rmax ≤ 2B log2 V`
  * B = bandwidth of the channel
    * `2B` - 码元速率（传码率，Baud，表示单位时间内传输的码元数）
  * V = number of discrete values per symbol

---

:orange:带宽bandwidth - 两个定义：

1.某信号具有的 频率差范围（频带宽度），单位HZ（模拟信号）
2.网络中某信道传送数据的能力，单位时间内某信道能通过的最高信号数。单位 bps

本质一样，链路 带宽 越宽，传输能力越高

---

:candy:注意，载波调制技术也可以用于有线传输

### 基带编码：Baseband Data Encoding

> 信号通常直接编码至数字信道，通过改变信道的性质（电线的电压，光纤的镭射密度），形成基带(之后在数字信道上进行基带传输) signal usually directly encoded onto the channel, by varing some property of channel(voltage applied to an electrical cable, intensity of laser in optical fibre), and occupies baseband region

:orange:常用编码

![](/static/2021-01-14-16-08-50.png)

* (单极)不归零码 Non-return to zero encoding
  * 只使用一个电压值，高电平-1，没电压-0
  * 如果长期运行发送相同的值，波形无变化-容易错记bit数

:orange:曼彻斯特编码

![](/static/2021-01-14-16-11-50.png)
![](/static/2021-01-14-16-13-00.png)

* 一个时钟周期表示1个bit
* 0 - low->high
* 1 - high->low
* 需要双倍带宽（只要有数据输入就会跳转），避免了bit数错误计量

## 无线（模拟）信道编码-载波调制：Encoding Data onto Wirele sChannels

宽带信号（宽带传输）

* <font color="red">将**基带（数字）信号**载波调制后，将信号频率范围移动到较高频段</font>，形成频分复用模拟信号，**便于在模拟信道上传输** --- 宽带传输

![](/static/2021-01-14-16-31-39.png)

* 载波调制（模拟信道的宽带传输） --- 应用于**无线连接**
* 允许信道中存在多个信号，调制至不同频段 allows multiple signals on a channel, modulated onto carriers of different frequency
  * 如，FM/AM广播站，频段
  * <font color="blue">也可用于有线连接，ADSL&有线电共用一条线</font>
* （传输）效率受以下影响
  * 载波频率 carrier frequency
  * 传输强度 transmissio power
  * 调制模式 modulation scheme
  * 天线类型 type of antenna

### （数字信号）调制模式：Modulation Scheme

[参考](https://blog.csdn.net/shulianghan/article/details/108022986)

> **调制**:将数字(基带)信号转为模拟信号

:orange:调幅ASK，AM

![](/static/2021-01-14-17-04-20.png)

* 0 - 没有幅度
* 1 - 有幅度

:orange:调频 FSK，FM

![](/static/2021-01-14-17-06-11.png)

* 0 - 低频率
* 1 - 高频率

:orange:调相 PSK，PM

![](/static/2021-01-14-17-08-46.png)

* 0 - 余弦波
* 1 - 正弦波

---

![](/static/2021-01-14-17-16-03.png)

:orange:QAM调制

* 调幅 & 调相结合

## 扩频通信：Spread Spectrum Communication

:orange:**单频信道易受干扰**

* 每秒多次，重复改变载波频率
  * 噪音不可能影响所有频段
* 使用伪随机序列来选择每个时段使用的载波频率
  * 伪随机数生成器的种子在发送方和接收方之间共享秘密，确保安全

:candy:例子

* 例如：802.11b Wi-Fi使用以2.4 GHz或5 GHz为中心的几个频率的扩频

## 香农定理-物理链路特性&局限性：Physical Link Characteristics and Limitations

> 真实网络链路易受噪音局限：电气，广播干扰，光纤缺点
> real-world network links are imperfect and subject to noise(electrical,radio interference, imperfections in optical fibre)

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

* **将物理层原始bitstream装帧成有意义的frame** structure the raw bitstream into meaningful frames of data
* 检测，**纠错（不主要**)传输错误 detect&correct transmission errors
* **识别设备（MAC）--寻址** identify devices - Addressing
* **仲裁信道媒体访问** arbitrate access to the channel

## 装帧&寻址：Framing&Addressing

![](/static/2021-01-14-18-17-39.png)

* 收方纠错
  * 如果收到的frame无差错，会从中提取出IP数据报交给网络层，否则丢弃这个帧（或者重传/海明码）
* 标头 - 帧同步
  * 判断bit流开始&结束位置，解决clock skew/discrrupted[物理层的不可靠性除了bit corrupt]，这样能判断帧是否完整，不完整应该丢弃帧并retransmit

## 介质访问控制&CSMA技术：Media Access Control

![](/static/2021-01-14-18-25-17.png)

* **因为同时传输可能会破坏信号**
  * 发送帧时控制访问，以免产生冲突
* <font color="blue">是解决当局域网中共用信道的使用产生竞争时，如何分配信道的使用权问题</font>
  * 避免传输冲突（由高传播时延造成的），导致信息重叠，利用CSMA/CD技术（随机型介质访问控制协议）在低传播时延时，发送前先监听信道

---

![](/static/2021-01-14-18-49-49.png)

:orange:为什么传播时延会有影响？

* A开始传输，B监听（A未到达之前，监听到无冲突，**传输时延**），B传输
* **冲突出现**，传输中信息重叠
  * <font color="red">传播时延越低，出现冲突概率越低</font>

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

# 【】网络层：Network Layer

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

## IP协议

:orange:IPV4

![](/static/2021-01-14-20-22-33.png)

* 32bit地址不够用
* 高数据传输率（bps，每秒传输的bit数）时，装帧困难
* 扩展性局限

:orange:IPV6

![](/static/2021-01-14-20-27-31.png)

* 更大的地址空间
* 网络层间无帧信息
* 去掉无用校验和
* 简化标头格式

:orange:IPV5

![](/static/2021-01-14-20-31-39.png)

在ARPAnet（互联网的前身）上进行的语音实验始于20世纪70年代初。

* 网络语音协议 https://www.ietf.org/rfc/rfc741.txt
* 这演变成了互联网流协议ST-II，被分配到IPv5--1979年至1995年期间开发的实验性多媒体流协议，但从未广泛部署过
* ST-II+规格：http://www.ietf.org/rfc/rfc1819.txt

## IP寻址：Addressing

![](/static/2021-01-14-20-36-20.png)

:orange:IP地址 - 编码网络接口位置(encoding location of netwrok interface)

* 如果某主机有**多个网络接口**（如WIFI&以太网），会对应**多个IP地址**
* 主机的每个接口都支持IPV4和IPV6
* 主机的每个指定类型的接口可以有多个IP地址

:orange:DNS域名系统是应用层面概念，不涉及于网络层

* 将域名与IP地址相互映射

:orange:子网掩码

* 就是将某个IP地址划分成网络地址和主机地址两部分

## 路由&自治系统：Routing

![](/static/2021-01-14-20-50-09.png)

:orange:AS - Antuonomous System 自治系统

* 指使用统一内部路由协议的一组网络（较大LAN），每个AS网络分别管理 administered separately
  * <font color="blue">每个AS有权,自主决定在本系统中应采取何种路由协议</font>
* AS之间
  * 不同技术 different technologies
  * 不同政策 different policies
  * AS之间，AS与内部使用者之间，都互不信任 mutual distrust between AS&peer, AS&customers

---

:orange: IGP -内部网关协议

* 一个自治系统网络内部进行路由信息的通信使用内部网关协议（IGP，Interior Gateway Protocols）

:orange: BGP -外部网关协议

* 各个自治系统网络之间是通过边界网关协议（BGP，Border Gateway Protocol）来共享路由信息

:orange: EGP - 边界网关协议

* 用于进行路由信息通信
  * BGP是最新的外部网关协议,是具体的一种EGP协议
* <font color="red">有望被IDRP域间选路协议替换</font>

### AS&域间选路协议：Inter-Domain Routing Protocol

![](/static/2021-01-14-20-59-17.png)

每个AS

* 分开管理，使用路由协议不同
* 最短路径路由
* 距离向量
* 链路状态算法

### 域间选路协议&BGP：Inter-Domain Routing & BGP

![](/static/2021-01-14-21-06-23.png)

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

:orange:不可靠通信，尽力而为服务

* best effort，无连接 connectionless，发送IP数据报 packet delivery
* <font color="red">只发送 - 不需要建立链接</font>
* 网络尽力而为发送IP数据报，但**不提供保证** network makes its best effort to deliver packets, but provides no guarantees
  * 网络传输时间可能不同 time taken to transit may vary
  * <font color="orange">可能丢包，延误，重排序，重复，损坏</font> packets may be lost, delayed, reordered, duplicated or corrupted
  * <font color="deeppink">如果无法传输，网络层直接丢弃IP数据报</font> discards packets if cannot deliver
* <font color="red">任意链路层都适用</font> easily run over any type of link layer

# 传输层：Transport Layer

:orange:TCP & UDP传输

![](/static/2021-01-14-21-53-15.png)

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

## 【】TCP

![](/static/2021-01-14-22-23-49.png)

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

### 【】TCP3次握手：Handshake

![](/static/2021-01-15-01-37-17.png)

### 【】TCP拥塞控制：Congestion Control

![](/static/2021-01-15-01-37-55.png)

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

## 表示层：Presentation Layer

> 表示层为在应用过程之间传送的信息提供表示方法的服务，它只关心信息发出的语法和语义。

![](/static/2021-01-15-02-21-40.png)

* 管理数据的表示，展示，转换
  * 媒体类型，内容协调
  * 信道编码
  * 国际化，语言，字符集
* app广泛应用的常见服务

## 应用层：Application Layer

![](/static/2021-01-15-02-23-54.png)

* 协议具体根据app业务逻辑定义

## 协议标准重要性：Importance of Protocol Standards

![](/static/2021-01-15-02-27-06.png)

OSI模型是一种合理的网络协议抽象，但忽略了

* 经济层
* 政治层
* 成功的网络协议支持不同厂商之间的互操作性--这种互操作性的存在是因为这些厂商致力于**协议的标准化**
* 粗略的共识和运行代码→标准是经过大量讨论和协商的结果 Rough consensus and running code → standards are the result of much discussion and negotiation

# 互联网变迁-架构设想：The Changing Internet-Architectural Assumptions

![](/static/2021-01-15-02-33-01.png)

互联网最初设计假设

* 设备位于固定位置，拥有少量唯一寻址的网络接口
* 网络 & 服务分散
* 尽力而为服务提供足够质量
* 网络可信
* 在边缘网络进行创新

:candy:以上设想在1980IP架构定义好时为合理的，但之后网络&协议如何变迁？

## 【】寻址&可达性改变：Changes in Addressing & Reachability

![](/static/2021-01-15-02-36-13.png)

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

### IPV6部署慢：Slow Deployment

![](/static/2021-01-15-02-47-49.png)

### 【】现代互联网-建立连接（NAT穿越）：Establish Connectivity in Modern Internet

![](/static/2021-01-15-02-48-43.png)
![](/static/2021-01-15-02-53-16.png)

？？？

:orange:NAT穿越 - 绑定发现，交换，探测

* STUN，TURN，ICE算法
* 需要一个具有公共IP地址的服务器进行绑定发现；集中控制点
* 复杂，慢，不可靠，浪费电，产生冗余的流量问题

:orange: 双栈协议 - 链接竞争

* *双协议栈技术就是指在一台设备上同时启用IPv4协议栈和IPv6协议栈。这样的话，这台设备既能和IPv4网络通信，又能和IPv6网络通信。如果这台设备是一个路由器，那么这台路由器的不同接口上，分别配置了IPv4地址和IPv6地址，并很可能分别连接了IPv4网络和IPv6网络。如果这台设备是一个计算机，那么它将同时拥有IPv4地址和IPv6地址，并具备同时处理这两个协议地址的功能*
* 有些路径支持IPv4，有些支持IPv6；客户端-服务器应用的探测和连接超时速度很慢
* 并行检测-连接竞争，难以实现，浪费资源

:candy:NAT穿越&双栈协议复杂化连接的建立，应将中心定于云服务

## 挑战-移动性的增加：Increasing Mobility

![](/static/2021-01-15-02-59-35.png)

IP地址用于编码网络位置

* 移动设备，（手机）使寻址&可达性复杂
  * 因为每次设备移动IP地址都有可能改变
  * 每次移动后必须重新建立TCP链接
  * 每次移动后必须重定向UDP数据包
  * 如果设备频繁移动，则需要复杂信令&连接管理

## 巨型企业&中心化：Hypergiants & Centralisation

![](/static/2021-01-15-03-03-37.png)

* 互联网拓扑结构扁平化，越来越集中化
* 对网络中立性、竞争、创新的影响

## 如何保证实时流量：Real-time Traffic

![](/static/2021-01-15-03-04-50.png)

视频流媒体主导互联网流量，每年增长>40%。

* 包丢失→质量受损或延迟增加
* 驱动集中化和CDN直连→更容易管理质量。
* 驱动TCP拥塞控制和TCP替换→BBR算法，QUIC；低延迟。
* 互动实时媒体有更严格的约束
* WebRTC、Zoom、VoIP、游戏......→非TCP传输以降低延迟

---

![](/static/2021-01-15-03-12-54.png)
![](/static/2021-01-15-03-15-05.png)

住宅网络在COVID-19 lockdown开始时，一夜之间流量增加了>20%。

* 移动网络的流量下降了约10%。

视频会议提供商的流量出现大规模增长

* 互联网的灵活性足以支持这种转变--我们能否在提高质量的同时，保持这种灵活性

## 网络创新：Innovation in the Network

![](/static/2021-01-15-03-16-24.png)

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