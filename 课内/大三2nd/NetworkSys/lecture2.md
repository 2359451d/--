# Connection Establishment in a Fragmented Network

Outline

* 复习TCP传输协议 & 编程模型(C-S模型)
* C-S连接建立原理(TCP模型的一种连接方式，TCP3此握手) & 影响建立速度因素
* TLS作用（transport layer security） & IPV6连接建立
  * 对效率的影响
* P2P建立 & NAT
* NAT问题
* NAT穿越原理

Content

* [Connection Establishment in a Fragmented Network](#connection-establishment-in-a-fragmented-network)
* [传输控制协议：TCP(Transmission Control Protocol)](#传输控制协议tcptransmission-control-protocol)
  * [TCP数据段结构：Segment Format](#tcp数据段结构segment-format)
  * [TCP可靠 & 拥塞控制 & 信息边界限制](#tcp可靠--拥塞控制--信息边界限制)
  * [TCP编程模型：TCP Programming Model](#tcp编程模型tcp-programming-model)
* [TCP连接模式：Client-server Connections](#tcp连接模式client-server-connections)
* [TCP连接建立过程：Client-Server Connection Establishment](#tcp连接建立过程client-server-connection-establishment)
* [时延（RTT&网络拥塞量&BW）对连接建立&下载效率影响：Impact of Latency on Performance](#时延rtt网络拥塞量bw对连接建立下载效率影响impact-of-latency-on-performance)
  * [对协议设计的影响- Example: HTTP/1.1 & SMTP](#对协议设计的影响--example-http11--smtp)
* [TLS&IPv6对连接效率的影响：Impact of TLS & IPV6 on Connection Establishment](#tlsipv6对连接效率的影响impact-of-tls--ipv6-on-connection-establishment)
  * [TLS原理: Impact of TLS](#tls原理-impact-of-tls)
  * [TLS-额外RTT对效率的影响 & 如何改进](#tls-额外rtt对效率的影响--如何改进)
  * [IPV4/6对效率的影响 & 如何与双栈主机建立连接：Impact of IPV6 and Dual Stack Deployment](#ipv46对效率的影响--如何与双栈主机建立连接impact-of-ipv6-and-dual-stack-deployment)
* [总结-连接效率&时延：Connection Latency](#总结-连接效率时延connection-latency)
* [点到点连接困难:Peer to Peer Connections](#点到点连接困难peer-to-peer-connections)
* [NAT原理&私有网段：Network Address Translation](#nat原理私有网段network-address-translation)
  * [无地址转换-单主机：Connecting a single host](#无地址转换-单主机connecting-a-single-host)
  * [无地址转换-多主机连入外网：Connecting Multiple Hosts](#无地址转换-多主机连入外网connecting-multiple-hosts)
  * [有地址转换：Network Address Translation](#有地址转换network-address-translation)
  * [NAT&私有网段：Private Address Ranges](#nat私有网段private-address-ranges)
* [NAT造成的问题&为什么NAT：Problem due to Network Address Translation](#nat造成的问题为什么natproblem-due-to-network-address-translation)
  * [NAT routers Encourage Centralisation](#nat-routers-encourage-centralisation)
  * [为什么NAT？Why Use](#为什么natwhy-use)
* [NAT对TCP连接的意义：Implications of NAT for TCP Connections](#nat对tcp连接的意义implications-of-nat-for-tcp-connections)
* [NAT对UDP连接意义：Implications of NAT for UDP Flows](#nat对udp连接意义implications-of-nat-for-udp-flows)
* [NAT穿越问题：NAT Traversa & P2P Connection Establishement](#nat穿越问题nat-traversa--p2p-connection-establishement)
  * [P2P NAT穿越概念：NAT Traversal Concepts](#p2p-nat穿越概念nat-traversal-concepts)
* [STUN(UDP) & TURN：Binding Discovery](#stunudp--turnbinding-discovery)
* [中转vs直连：Relay or P2P-Candidate Exchange](#中转vs直连relay-or-p2p-candidate-exchange)
* [连接探测-ICE：Probe for Connectivity-The ICE Algo](#连接探测-iceprobe-for-connectivity-the-ice-algo)
* [总结：P2P NAT Traversal](#总结p2p-nat-traversal)

# 传输控制协议：TCP(Transmission Control Protocol)

TCP复习

* TCP作用
* 数据段格式
* 服务模型
* 如何用BSD（Berkeley Sockets API）进行TCP编程

---

![](/static/2021-01-21-16-59-41.png)

TCP - 传输层

* TCP连接为尽力而为的IP网提供**可靠有序，字节流传输服务** Provides a reliable,ordered, byte stream service over the best-effort IP network
  * <font color="deeppink">TCP连接一旦建立，发送方可以往套接字中写入有序字节流（代表一个TCP连接），通过套接字可靠&有序将字节流发送给收方</font>
  * <font color="deeppink">如果IP包中任何段丢失/延误/超时，TCP会进行重传该段，或是重新调整至正确顺序</font>
* **提供拥塞控制（UDP没有，给APP层暴露最原始的IP协议），适应网络容量 provides congestion control to adapt to network capacity**
  * 网络繁忙/空闲时，TCP负责调整传输速度（窗口大小，阈值，慢开始算法,拥塞避免算法...）
* 最广为使用的传输协议 most widely used
* TCP数据段通过IP传输
  * 每个TCP段有标头分辨数据，装入IP报的data部分（IP层不关心TCP段的内容，只负责传输时它段的内容，只负责传输它。反之，TCP层不关心底层网络类型）
  * IP报又作为帧的data部分，通过物理层进行帧传输

## TCP数据段结构：Segment Format

![](/static/2021-01-21-17-20-17.png)

* TCP数据段header（绿） inside IPV4数据包
* 自顶向下，自左向右传输

:orange:TCP header

* source port - 标识发送数据段的套接字
  * 一般指定收方port，source不指定（通过OS选择一个未使用port）
  * <font color="deeppink">相同TCP连接，端口号不变，建立新连接则改变</font>
* destination port - 标识接收数据段的套接字
* **sequence number**
  * TCP建立初期，选择随机初始值。根据发送的数据段大小进行增量
* **acknowledge number**
  * 收方expected收到的下一字节数。如果不匹配，则表明可能出现丢包情况（TCP进行重传）
* data offset
  * 标识有效载荷数据payload在段中位置
* **Receive Window Size**
  * 有多大缓冲空间可以用来接收数据，用于收方通知发送方进行拥塞控制

## TCP可靠 & 拥塞控制 & 信息边界限制

![](/static/2021-01-21-17-38-20.png)

* TCP提供可靠，有序字节流传输服务
  * 丢包重传，保证顺序，**不包括信息边界（限制）**
* 控制传输速率，以适应网络容量 --- 拥塞控制
* 通过端口号辩别设备
* <font color="red">为需要可靠传输的app提供服务，大多app的默认选择</font>

:orange: message boundaries are not preserved

* <font color="blue">TCP不保证传输过程中信息边界，即如果传输一个2000B的数据段，传输过程中无法保证完整一次传输这一个2000B block（可能多分blocks）</font>
  * 但是因为有序，所以可靠性仍能保证
* <font color="deeppink">这一点也增加了APP设计的不便，因为需要检查最后收到的TCP数据段是否有效</font>

## TCP编程模型：TCP Programming Model

Client-Server模型（注：fd-file descriptor）

![](/static/2021-01-21-18-01-07.png)

1. 在Server端建立Socket
   1. `int fd = socket(PF_INET6/4, SOCK_STREAM, 0)`
   2. `bind(fd, .., ..)` 绑定套接字，可指定port
   3. `listen(fd, ...)`监听请求
   4. `accept(fd)` server准备接收新连接，直到client连入，accept方法会一直阻塞。**连接成功-返回新fd（代表新连接），且原fd保持不变继续监听**
2. Client端
   1. `int fd = socket(PF_INET6/4, SOCK_STREAM, 0)`
   2. `connect(fd, ..., ...)`剩余参数：serverIP，server监听的port。该方法将client连入server。**连接成功-返回，否则-server unreachable**
3. 一旦C-S连接完成，（`connfd`生成），C-S之间可以进行数据交换
   1. `send()`
   2. `recv()`
   3. <font color="deeppink">注意使用连接完成的socket，不是监听请求socket</font>
4. 数据交换完成，client关闭连接
   1. server可以继续通过监听socket处理接收新连接

# TCP连接模式：Client-server Connections

C-S连接建立原理 & 影响连接建立速度的因素

---

![](/static/2021-01-21-21-02-25.png)

:orange: 通常基于TCP的app都为**client-server**连接模式

* server监听已知端口
  * server可以等待client的连入，不需要进行同步
* client连入server
* client发送请求，server响应请求

:orange:TCP也可以用于**P2P**连接模式

* 两个设备同时互相连接，互相交换数据
* 不常见，但可行，只需要两个设备IP可达已知，都绑定到已知port上（前提，没有firewall&NAT阻塞流量）

# TCP连接建立过程：Client-Server Connection Establishment

TCP client-server模式的连接建立过程

![](/static/2021-01-21-21-39-49.png)

1. server创建套接字，绑定port，用于监听连接
   1. 调用`accept()`表示，就绪等待连接，一直阻塞直到连接接入
2. client创建TCP套接字，<font color="deeppink">调用`connect()`触发TCP连接3次握手</font>
   1. client向server发送TCP数据段（标头包含 1. `SYN bit set`，表明这是本次连接的第一个数据包 2. 随机选择的`sequence number`）.除此之外，<font color="blue">第一个数据段不包括任何数据</font>
3. server收到第一个数据段后，发送TCP数据段响应client
   1. header包含 `SYN bit set` （表明这是本次连接中，server发送的第一个数据段） & 随机选择的`sequence number`（server） & `ACK bit set = client initial sequence number+1` （识别client发送的sequence number，识别下一个预期sequence number） --- 这个包又称 SYN-ACK包
4. client接收SYN-ACK包
   1. `ACK->server initial sequence number + 1`，如果ACK有效-> set to `1`
   2. `seq = client initial sequence number + 1`
5. <font color="deeppink">3次握手完成后，连接建立, 为server返回`connfd`用于访问该连接的套接字</font>
   1. 连接完成后，典型操作为，client立即向server发送请求，server进行响应

![](/static/2021-01-21-21-40-46.png)

* 注意此图，连接建立（3次握手）占了大部分时间

---

# 时延（RTT&网络拥塞量&BW）对连接建立&下载效率影响：Impact of Latency on Performance

:candy: 注意，不管本身带宽如何，RTT永远是主要影响因素（尤其latency非常大的情况下）

* <font color="deeppink">最佳实践 - 减少需要发送的data大小，从而减少RTT（即，限制每个TCP连接中，请求响应数量 - 即，减少RTT影响（减少数据传输量））。因为发送大文件时，拥塞量特别大增加BW后的差异也不明显</font>
* 考虑TLS情况下，也可以考虑减少TCP连接数量

![](/static/2021-01-22-00-26-30.png)

* 假设通过hTTP-TCP方式从web server获取web page
  * HTML & CSS - 其中一个server，image - 另一个server
* 检索该web page时间
  * 1x RTT（Rround Trip Time） - 交换SYN & SYN-ACK包 & 完成`connect()`
  * 1x RTT - 发送获取HTML文件请求 & 请求到达server + 获取HTML文件序列化时间
  * 复用第一个TCP连接 - 获取CSS文件 = 1x RTT + serialization time
  * 与server2建立新TCP连接 = 1x RTT
  * 发送image请求 & 获取image = 1xRTT + serialization time

---

以下为实践例子

:orange: <font color="deeppink">RTT取决于client与server之间的距离。serialization time取决于当前网络可用容量（可用带宽BW）</font>

![](/static/2021-01-22-00-26-43.png)

* 如image 1MB，带宽 2MBps，则需要4s下载（还需要考虑RTT花费时间）
* <font color="red">即，下载时间（效率）取决于 RTT & 可用带宽 & 需要下载的数据大小</font>

通常RTT

![](/static/2021-01-22-00-49-38.png)

* 取决于client & server之间距离
* 取决于网络拥塞量

![](/static/2021-01-22-01-04-32.png)

:orange: RTT &带宽之间的关系

* 拥塞量低时，link 10 times faster，download speed 10 times faster（带宽增加10倍，下载速度快10倍）

## 对协议设计的影响- Example: HTTP/1.1 & SMTP

HTTP/1.1
![](/static/2021-01-22-01-08-12.png)

simple mail transfer protocol

![](/static/2021-01-22-01-13-53.png)

* 3次握手（连接建立RTT） + initial greeting （RTT） + HELO(RTT) + FROM(RTT) + RCPT(RTT) + DATA（RTT） + true data（RTT）+ quit（RTT）= 8RTT

---

# TLS&IPv6对连接效率的影响：Impact of TLS & IPV6 on Connection Establishment

* 添加TLS后对连接效率的影响 Transport Layer Security (TLS) needs extra RTTs to negotiate security
* 当目标主机采用双栈协议，如何保证效率？Dual-stack IPv4 and IPv6 hosts must race connections for good performance

## TLS原理: Impact of TLS

:orange: 每个TCP基本需要2RTT

* 建立连接
* client发送接受请求
* 基于TCP连接的app层协议的采用，效率差异也非常大（HTTP/1.1 vs SMTP）
  * HTTP发送&检索数据非常快 2RTT
  * SMTP 8RTT（不必要）

:orange: TLS

![](/static/2021-01-22-02-01-33.png)

* 基于TCP连接，**为TCP提供安全** provide security for TCP connection
  * <font color="deeppink">即，client&server需要加密&认证秘钥，确保该TCP连接内传输的数据保密&避免传输中被篡改</font> allow client & server to accept encryption & authentication key, ensure the data sent in TCP connection confidential & protected from modification in transit
* 采用HTTPS的网页，通常
  * 先建立TCP连接
  * 再采用TLS保证该连接传输安全性，再允许进行网页访问
* <font color="red">TLS版本决定连接建立额外所需时间</font> depending on the TLS version used, additioanl RTT is added to Connection Establishement
  * TLS1.3（最新） - <font color="deeppink">连接建立后，在交换数据之前需要额外1RTT，client&server接受加密认证</font>
  * TLS1.2需要额外2RTT

:orange: TLS过程（通常额外1RTT，旧TLS协议更久[2RTT]）

![](/static/2021-01-22-02-02-24.png)

* client发送 `TLS ClientHello`信息
  * 向server发送**security parameter**
* server响应 `TLS ServerHello`信息
  * 包含密钥 & 另一个**security parameter**
* 如果匹配成功，client发送信息，设置加密参数 **encryption parameters**
* 之后进行数据传输
  * client - HTTP request

## TLS-额外RTT对效率的影响 & 如何改进

![](/static/2021-01-22-02-09-23.png)

* 1ms（无拥塞，RTT可忽略情况下），效率跟不采用TLS一致
* 300ms（高延误），效率比无TLS更差，增加带宽后效果差异也更不明显

---

![](/static/2021-01-22-02-14-49.png)

:orange: 改进效率

* 减少TCP连接数量
* 限制client&server之间交换request-response的数量

## IPV4/6对效率的影响 & 如何与双栈主机建立连接：Impact of IPV6 and Dual Stack Deployment

IPV4到IPV6的转移也对效率（连接建立）有影响

![](/static/2021-01-22-02-23-09.png)

* 注意IPv6不是IPv4的子集
* 某些主机，链路，只支持v4，支支持v6，或同时支持v4&v6

---

:orange: 因为支持的网络不确定（双栈），如何建立连接？

![](/static/2021-01-22-02-41-53.png)

* 给定一个hostname，通过DNS找出其IP
  * 返回一个包含所有 IPv4/6地址的列表
* **采用顺序查找，建立连接** loop
  * 按表顺序找，直到一个可用连接，**慢**
* **采用 “happy eyeballs”方法进行连接**
  * 采用两个单独DNS查询（并行）， IPv4查询 & IPv6查询
  * 两个独立DNS查询竞争，第一个返回IP结果的进行连接尝试
  * 如果与返回结果中的第一个IP地址，未在100ms内完成连接，继续与下一个地址进行连接
  * <font color="deeppink">一旦连接建立，另一个连接请求直接需要</font>
  * <font color="blue">“happy eyeballs”权衡连接速率&并行查询网路开销</font>，保证效率情况下，复杂化了连接建立过程

:candy: 双栈协议由于链接的不确定性，需要采用并行DNS查找来高效建立连接

# 总结-连接效率&时延：Connection Latency

![](/static/2021-01-22-02-44-22.png)

* 影响效率（主要是连接效率）的**主要**因素
  * **RTT**
    * C-S之间的距离
    * 网络拥塞量（latency）
  * **带宽BW**
    * serialization time
* 如何改善效率（连接建立）
  * **使用“happy eyeballs”方法用于连接建立**（当目标主机IP多于1个时，因为IPv4/6对效率也有影响，需要考虑不同情况下该如何建立连接） - 复杂化了连接建立的过程，但是比顺序查询&连接IP更快
  * **减少TCP连接数量 & 复用**
  * **减少每个TCP连接中，请求响应数量 - 即，减少RTT影响（减少数据传输量）**
  * **结合TCP&TLS建立过程**
    * <font color="deeppink">在初始SYN请求中加入security parameter信息， 即在一个RTT内同时完成建立&TLS</font>
    * <font color="red">TCP中不可能，但是QUIC传输协议允许</font>
  * **减少RTL（round trip latency）**
    * latency由 **网络拥塞程度（TCP拥塞控制算法）** & **链路中信号传播速度（改变物理性质？**）决定

# 点到点连接困难:Peer to Peer Connections

网络概念上来说都是P2P连接，不管设备位置/类型

![](/static/2021-01-22-16-02-48.png)

* 实践中，很难为绝大宽带建立P2P连接（因为NAT的广泛使用）

# NAT原理&私有网段：Network Address Translation

NAT原理

![](/static/2021-01-22-20-59-48.png)

* 允许多个设备共享一个公共IP
* 允许多个设备形成一个私有内部网（如果IP分配为特殊使用范围）
* **1 device作为network address translator，NAT连接至私有内部网 & 因特网**
  * 为这两个网络进行包转发
  * 因为NAT会进行IP，port转换，所以对外看来所有数据都来源于NAT的IP
  * NAT会隐藏私有内部网

:candy: 因为IPv4不足以供应，而IPv6部署很慢

* NAT是一个变通方法，允许仍然使用IPv4（但有限制）

## 无地址转换-单主机：Connecting a single host

无地址转换 - 最普通连入方式
![](/static/2021-01-22-21-22-51.png)

* host连入本地ISP供应商网络，ISP连入边界网
* ISP拥有特定网段，可以分配给所属host
  * `203.0.113.0/24`
  * `203.0.113.0` - network IP被占用，不能分配给其他host，剩下的IP都可以进行分配
* 直连，**无地址转换**，整个过程中source，dst不变
  * host 向外网server发送数据包，dst-serverIP，source-clientIP
  * 响应时同理

## 无地址转换-多主机连入外网：Connecting Multiple Hosts

![](/static/2021-01-22-21-38-17.png)
![](/static/2021-01-22-21-38-43.png)

* 需要额外一个router（占用一个IP），为customer的多设备建立一个内网，再与ISP网连接
* 与外网server交换数据过程同理，整个过程中source，dst不变，**无地址转换**

## 有地址转换：Network Address Translation

通常ISP没有足够IPv4地址供给客户

![](/static/2021-01-22-21-43-08.png)
![](/static/2021-01-22-21-54-01.png)
![](/static/2021-01-22-22-54-23.png)

* 客户使用NAT设备（network address translator）连入ISP网
* NAT设备外接口由ISP分配，
  * NAT内接口连用户子网，IP地址范围私有特定
* **注意C-S间交换数据时，存在地址转换**
  * client->server, source-内网clientIP，dst-外网serverIP. <font color="deeppink">到达NAT内接口后，将地址转换，以匹配外接口IP。NAT设备也会记录转换&关联port</font> <font color="blue">最后数据包到达server时，dst不变，但是source为【NAT地址】</font>
  * 响应时，source为serverIP，dst为NAT地址（然后NAT内部查询port&转换记录，进行inverse translation以便转发到内网设备）

:orange: NAT需要记录地址转换-port映射，以便转发响应包时进行inverse translation

## NAT&私有网段：Private Address Ranges

NAT会隐藏私有内部网段

* IPv4私有网段可为以下3种
  * `10.0.0.0/8`
  * `176.16.0.0/12`
  * **`192.168.0.0/16`**
  * 同一私有网段内的设备可以直接通信，不同网段需要进行地址转换以匹配内网接口网段。（给人一种不同网段复用了IP地址的错觉）

# NAT造成的问题&为什么NAT：Problem due to Network Address Translation

NAT造成的问题 & 即便有问题为什么还使用NAT？

## NAT routers Encourage Centralisation

其中一个问题 - NAT break certain classes of app（app通信模式） & encourage centralisation

![](/static/2021-01-22-23-05-11.png)

* NAT设计用于C-S应用
  * client behind NAT
  * server is a host on public internet
  * 内网包转发时，IP&port（TCP header）都会被转换成NAT所属的网段&未使用的port，进行映射记录，再转发至公网
* **复杂化了NAT后server的处理**，因为需要指定port，否则无法接收client来的响应（reverse translation），而NAT不知道如何为server(behind NAT)处理新请求（可以手动配置，但复杂或不可靠）
  * 因此鼓励部署server，鼓励大型hosting服务
* **复杂化了P2P应用**
  * 因为NAT复杂化了incoming connection
  * 并且**host** behind NAT只知道自己的私有网IP，无法向请求方提供自己的公网
  * 鼓励集中化服务

## 为什么NAT？Why Use

![](/static/2021-01-22-23-09-22.png)

1. **IPv4地址空间短缺的变通方法**
   1. ISP无法响应客户需求 - 通过NAT解决，每个客户只分配一个IPv4地址&NAT router使用私有网段
   2. IPv4（稀有，贵）->IPv6（多，便宜）转移也可以解决，但是部署时间慢

![](/static/2021-01-22-23-09-43.png)

2. **用于IPv4/6地址的转换**
   1. ISP内部使用v6网段，用户使用v4
   2. 数据包转发时，转换成特定类型地址
      1. 私有IP&port->公共IP&port
      2. v4/6->v4/6
   3. 留下更充足的IPv4空间

![](/static/2021-01-22-23-15-39.png)

3. **避免网络地址更改renumbering**
   1. 很难改变网络中IP，如果硬编码（静态）。如果ISP更变，所有都需要改变
   2. IPv6使地址更改更简单（但还是不知道具体实践如何实现）

# NAT对TCP连接的意义：Implications of NAT for TCP Connections

NAT怎么知道要记录何种转换状态？

![](/static/2021-01-22-23-30-25.png)

:orange:针对 host behind NAT（主动请求），NAT也支持反向转换

* NAT会查看TCP数据段
  * NAT监控TCP开始标志，如果出现SYN--SYN-ACK--ACK，代表TCP连接建立，需要开始建立正确的转换
  * NAT监控TCP完成标志（FIN--FIN-ACK-ACK），如果TCP连接关闭，转换状态记录可以被移除
  * <font color="deeppink">因为有时候app崩溃，不会发送FIN包，NAT路由还实现了超时timeout机制</font>，如果连接-发送用时过长，NAT视为失败，直接移除状态记录

:orange: 针对incoming connection（之前无状态记录的被动新请求），NAT无处理机制

* NAT不知道该转发至哪里，<font color="blue">必须手动配置</font>
* 使P2P & server behind NAT复杂
  * TCP-P2P连接只能匹配正确流量，“更严格”

# NAT对UDP连接意义：Implications of NAT for UDP Flows

![](/static/2021-01-22-23-39-57.png)

* <font color="deeppink">UDP无连接建立，NAT无法监控【UDP开始-结束状态】</font>
  * 针对outgoing请求，并不会影响NAT的状态转换**建立**，<font color="deeppink">但会影响状态转换的删除</font>
  * NAT仅能应用**超时机制**，进行状态转换的删除，因为时间片会设置的非常短（保证不会积累太多UDP状态记录），所以双方必须频繁交换数据，才能保证NAT绑定的激活（“UDP还未关闭”） --- 可能产生无用流量（timeout-15s）
* UDP-P2P比TCP更宽松，允许转发&转换任何匹配上端口的流量【因为无连接】

# NAT穿越问题：NAT Traversa & P2P Connection Establishement

how app can work around NAT routers to establish P2P connection

## P2P NAT穿越概念：NAT Traversal Concepts

![](/static/2021-01-22-23-57-53.png)

问题

* NAT外部无法向内部服务器建立连接
  * 除非手动配置，不然不知道如何转发

---

:orange: NAT穿越概念

> 在现实Internet网络环境中，大多数计算机主机都位于防火墙或NAT之后，只有少部分主机能够直接接入Internet。很多时候，我们希望网络中的两台主机能够直接进行通信，即所谓的P2P通信，而不需要其他公共服务器的中转。由于主机可能位于防火墙或NAT之后，在进行P2P通信之前，**我们需要进行检测(交换candidate address后发送probe信息)以确认它们之间能否进行P2P通信以及如何通信**。这种技术通常称为NAT穿透（NAT Traversal）。最常见的**NAT穿透是基于UDP的技术**，如RFC3489中定义的**STUN协议**

![](/static/2021-01-23-00-27-26.png)

* A&B都为NAT后主机，hostA&B如何建立连接？
  * A无法直接发送包给B，因为IP相同
* **A发包给NATB，需要知道其公网IP&port，再做映射转发给B**
  * 必须A & B都与公网上一个**referral/STUN server**进行连接，然后通过server查询包来源（称为candidate addresses） --- 【这个方法称为binding discovery】
  * 交换获取candidate addresses后，两主机发送probe信息，尝试连接 & 尝试建立NAT映射
  * 如果连接probe成功，两主机直接P2P连接，不再需要公网server
* <font color="deeppink">STUN可以让应用程序确定NAT分配给它们的公网IP地址和端口号。STUN是一种Client/Server的协议，也是一种Request/Response的协议，默认端口号是3478</font>

# STUN(UDP) & TURN：Binding Discovery

查找NAT映射 - binding discovery

![](/static/2021-01-23-00-41-35.png)

* STUN协议常用于binding discovery 【UDP】
  * host先通过NAT转换地址发送包给公网server，server又通过NAT反向转换将**转换后信息【server reflexive address: the address the server think it comes from】**告诉该host
  * 如果server reflexive address与hostIP一致，则server与host之间无NAT
* <font color="deeppink">STUN可以让应用程序确定NAT分配给它们的公网IP地址和端口号。STUN是一种Client/Server的协议，也是一种Request/Response的协议，默认端口号是3478</font>

![](/static/2021-01-23-00-42-43.png)

:orange:进行binding discovery时，需要注意找出所有接口的candidate IP地址（server reflexive address）

* **STUN**会找出host所有接口的server reflexive address，然后通过UDP方式探测所有candidate地址的连接性

:orange: TURN

* <font color="deeppink">如果所有reflexive address都不可达（无法直接建立P2P连接），可能需要考虑使用referral server中转（relay）数据</font>
  * TURN协议（server采用） - 需要作为一个“中间人（代理）”来转发流量，实现穿透

# 中转vs直连：Relay or P2P-Candidate Exchange

referral server上采用协议（STUN/TURN）找出host所有接口的candidate address（送回去作为server reflexive address）后

![](/static/2021-01-23-02-01-49.png)

* host & peer 将其**candidate地址列表**传回referral server
* referral server将列表信息转发给与其需要进行通信的host
* 最后，两个host都知道互相的candidate address，<font color="deeppink">准备探测是否这些地址可以建立连接，可达</font>

---

:candy: 为什么host之间非要建立P2P连接，而不直接通过中转relay来交换数据？ why not just send data via the relay server? but to direct connect?

* **P2P直连时延比中转转发低** direct connection latency is lower than connection via a relay
  * video call, latency matters
* **relay server可以监听中转的连接，不安全**
  * relay is always aware that communication is being attempted; communication metadata is potentially sensitive info

# 连接探测-ICE：Probe for Connectivity-The ICE Algo

referral server上采用协议（STUN/TURN）找出host所有接口的candidate address（送回去作为server reflexive address）后

![](/static/2021-01-23-02-01-49.png)

* host & peer 将其**candidate地址列表**传回referral server
* referral server将列表信息转发给与其需要进行通信的host
* 最后，两个host都知道互相的candidate address，<font color="deeppink">准备探测是否这些地址可以建立连接，可达</font>

---

:orange: 探测 - ICE算法

![](/static/2021-01-23-02-17-48.png)

* 交换candidate address信息后，<font color="deeppink">两个主机从所有candidate address轮流向所有peer’s candidate address发送探测信息包(probe packets)</font>，以确定是否可以建立P2P直连
  * 向对方server reflexive address发出probe packet时，NAT会建立相应映射（便于后续响应找到相应映射，转发至内网）
* <font color="deeppink">探测过程可能很长，因此candidate address会有优先度，以便最快找到可达地址</font>
* <font color="red">如果有多条candidate address可达，则选择最优的那条（低时延, ...），并放弃其他连接</font> if there are several candidate addresses reachable, select the best path

# 总结：P2P NAT Traversal

![](/static/2021-01-23-02-19-39.png)

* Binding discovery（查找所有candidate address，并交换）& systematic connection probing（探测所有candidate address是否可以建立连接）
  * 复杂，慢，产生大量不必要流量
* UDP - NAT更高效
  * STUN & ICE支持voice-over-IP应用（UDP）
  * 因为connectionless特性，只要匹配NAT映射（IP&port），包可以直接被转发
* TCP（P2P）- NAT低效，难建立连接
  * 除了检查NAT映射是否匹配，还需要检查是否匹配TCP sequence number