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
  * [TCP不保证消息边界-造成应用程序复杂](#tcp不保证消息边界-造成应用程序复杂)
  * [TCP编程模型：TCP Programming Model](#tcp编程模型tcp-programming-model)
* [TCP-C/S连接模式：Client-server Connections](#tcp-cs连接模式client-server-connections)
* [TCP连接建立过程：Client-Server Connection Establishment](#tcp连接建立过程client-server-connection-establishment)
* [时延（RTT&网络拥塞量&BW）对连接建立&下载效率影响：Impact of Latency on Performance](#时延rtt网络拥塞量bw对连接建立下载效率影响impact-of-latency-on-performance)
  * [距离&网络拥塞程度（可用带宽）决定RTT](#距离网络拥塞程度可用带宽决定rtt)
  * [RTT影响效率/下载时间的主要原因](#rtt影响效率下载时间的主要原因)
  * [协议设计决定RTT- Example: HTTP/1.1 & SMTP](#协议设计决定rtt--example-http11--smtp)
* [TLS&IPv6对连接效率的影响：Impact of TLS & IPV6 on Connection Establishment](#tlsipv6对连接效率的影响impact-of-tls--ipv6-on-connection-establishment)
  * [TLS原理: Impact of TLS](#tls原理-impact-of-tls)
  * [TLS-额外RTT对效率的影响 & 如何改进](#tls-额外rtt对效率的影响--如何改进)
    * [例子](#例子)
    * [TLS仍必不可少，TLS1.3已经提供最佳性能](#tls仍必不可少tls13已经提供最佳性能)
  * [IPV4/6（双栈协议）对TCP性能的影响：Impact of IPV6 and Dual Stack Deployment](#ipv46双栈协议对tcp性能的影响impact-of-ipv6-and-dual-stack-deployment)
  * [“快乐眼球”-双栈协议(过渡)，如何建立TCP连接？](#快乐眼球-双栈协议过渡如何建立tcp连接)
* [5种方式保证TCP性能：Connection Latency](#5种方式保证tcp性能connection-latency)
* [NAT增加P2P连接困难:Peer to Peer Connections](#nat增加p2p连接困难peer-to-peer-connections)
* [NAT概念：Network Address Translation](#nat概念network-address-translation)
  * [无地址转换-单主机：Connecting a single host](#无地址转换-单主机connecting-a-single-host)
  * [无地址转换-多主机连入外网：Connecting Multiple Hosts](#无地址转换-多主机连入外网connecting-multiple-hosts)
  * [有地址转换：Network Address Translation](#有地址转换network-address-translation)
* [NAT隐藏内网&可选的私有网段：Private Address Ranges](#nat隐藏内网可选的私有网段private-address-ranges)
* [NAT造成的问题&为什么NAT：Problem due to Network Address Translation](#nat造成的问题为什么natproblem-due-to-network-address-translation)
  * [NAT内网穿透问题（C/S，P2P困难）-推进使用集中云服务：NAT routers Encourage Centralisation](#nat内网穿透问题csp2p困难-推进使用集中云服务nat-routers-encourage-centralisation)
  * [为什么NAT？Why Use](#为什么natwhy-use)
* [NAT翻译记录基于对TCP连接内数据包的监控：Implications of NAT for TCP Connections](#nat翻译记录基于对tcp连接内数据包的监控implications-of-nat-for-tcp-connections)
* [NAT对UDP连接的影响：Implications of NAT for UDP Flows](#nat对udp连接的影响implications-of-nat-for-udp-flows)
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

TCP是目前互联网中最广泛使用的传输协议。 TCP连接提供了一个可靠的、有序的、在尽力而为的IP网络上运行的**字节流交付服务**。

* 一旦建立了TCP连接，一个应用程序可以向套接字中**写入一串字节，代表连接，而TCP将以正确的顺序将这些字节可靠地传递给接收方**。
* **如果任何包含TCP段的IP数据包丢失，操作系统中的TCP堆栈将注意到这一点，并自动重新传输丢失的段**。
* 同样，如果任何一个IP数据包被**延迟并不按顺序到达，TCP协议栈将在把数据传送给应用程序之前将其放回正确顺序**。 
* 最后，TCP将**调整其发送数据的速度以匹配可用的网络容量**。
  * 如果网络繁忙，TCP将放慢它发送数据的速度，以公平地分享传输之间的容量
  * 同样，如果网络变得空闲，TCP连接将加快速度以使用空闲的容量。
  * 这个过程被称为拥堵控制，而TCP实现了复杂的拥堵控制算法。

:orange: **使用TCP的应用程序不知道重传、重新排序和拥塞控制。他们只是看到一个套接字，向其中写入字节流。然后这些字节被可靠地传递给接收者**。

* 在内部，这些字节被分割成TCP段。每个段都有一个头，以识别数据。 该**TCP段被放置在一个IP包的数据部分中。该IP包又被放在链接层帧的数据部分中，并在网络上发送**。
  * IP层只是看到它必须传送的TCP段序列，而不知道其内容。 
  * 同样，TCP将数据段交给IP层来传输，而不知道底层网络是以太网、WiFi、光纤还是其他什么

TCP is currently the most widely used  transport protocol in the Internet.  A TCP connection provides a reliable,  ordered, byte stream delivery service that runs  on the best-effort IP network. Once a  TCP connection is established, an application can  write a sequence of bytes into the  socket, representing the connection, and TCP will  deliver those bytes to the receiver,  reliably, and in the correct order.  If any of the IP packets containing  TCP segments are lost, the TCP stack  in the operating system will notice this,  and automatically retransmit the missing segments.  Similarly, if any of the IP packets  are delayed and arrive out of order,  the TCP stack will put the data  back into the correct order before delivering  it to the application.  Finally, TCP will adapt the speed at  which it sends the data to match  the available network capacity. If the network  is busy, TCP will slow down the  rate at which it sends data,  to fairly share the capacity between transmissions.  Similarly, if the network becomes idle,  TCP connections will speed up to use  the spare capacity. This process is known  as congestion control, and TCP implements sophisticated  congestion control algorithms.  We’ll talk more about TCP congestion control  in Lecture 6.  Applications using TCP are unaware of retransmissions,  reordering, and congestion control. They just see  a socket, into which they write a  stream of bytes. Those bytes are then  delivered reliably to the receiver.  Internally, those bytes are split into TCP  segments. Each segment has a header added  to it, to identify the data.  The segment is placed inside the data  part of an IP packet. That IP  packet is, in turn, put inside the  data part of a link layer frame,  and sent across the network. The IP layer just sees a sequence  of TCP segments that it must deliver,  and is unaware of their contents.  Equally, TCP gives data segments to the  IP layer to deliver, and is unaware  whether the underlying network is Ethernet,  WiFi, optical fibre, or something else.

---

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

幻灯片上的图显示了一个IPv4数据包内的TCP段头的格式。

* 数据在链路上按照所示顺序，从左到右，从上到下，**在链路层帧的有效载荷部分发送**。
  * **首先发送IP头，然后是TCP段头，最后是TCP有效载荷数据**。
* 看一下绿色显示的**TCP段头**，我们会发现它包括一些字段。
  * 一个TCP段以**源和目的端口号**开始。源端口号标识了发送该段的套接字，而目的端口号标识了应该被传送到的套接字。
  * 当建立一个TCP连接时，TCP服务器会绑定到一个众所周知的端口，以确定其提供的服务类型。
    * 例如，网络服务器绑定到80端口。这就**提供了一个已知的目标端口，客户可以连接到该端口**。
    * **客户指定他们连接的目标端口，但通常不指定他们的源端口**。
    * 然后，**操作系统为该连接选择了一个未使用的源端口**。
    * 所有从客户端发送到服务器的TCP段，作为**单个连接的一部分，都有相同的源端口和目的端口，并且响应回来的时候，这些端口被交换了**。
    * 每个新的连接都有一个新的源端口。
  * **序列号和确认号**。
    * 在一个TCP连接开始时，**序列号被设置为一个随机的初始值**。
      * 随着数据的发送，发送方插入的TCP序列号会增加，**计算正在发送的数据字节数**。
    * **确认号**表示**接收方所期望的下一个数据字节**。
      * 例如，如果**收到一个序列号为4,000的TCP段，该段包含100个字节的数据，那么确认号将是4,100**。 
        * 这表明预期的下一个TCP段是序列号为4,100的段。
    * **如果返回的确认号码与预期的不同，这表明一些数据包已经丢失**。
      * 然后，TCP将**重新传输丢失数据包中的段**。
  * **数据偏移字段表示段中有效载荷数据的起始位置**。
    * 也就是说，它表示数据包中包含的任何TCP选项的大小。
  * **保留位不被使用**。
  * 然后有6个单比特标志。
    * URG位表示紧急指针是否有效。
    * ACK位表示确认号码是否有效。
    * PSH位表示这是消息中的最后一段，应该尽快推送给应用程序。
    * SYN，同步，位在连接上发送的第一个数据包上被设置。
    * FIN位表明连接应该被干净地关闭。
    * 而RST位表示连接将被重置，中止，不需要清理。
  * **接收窗口大小**允许接收方指示它有多少可用的缓冲空间来接收新数据。
    * 这允许接收方告诉发送方放慢速度，如果它的发送速度比接收方能够处理的数据快的话。
  * **校验和**用于检测损坏的数据包，然后可以重新传输。
  * **紧急指针（Urgent Pointer**）允许TCP发送方表明某些数据将由接收方紧急处理。
    * 不幸的是，经验表明，由于规范不明确和实施不一致，**TCP中的紧急数据机制在实践中无法使用**。
* 之后是**TCP选项头，它允许TCP增加新的功能和扩展**，
* 然后是有效载荷数据。

The diagram on the slide shows the  format of a TCP segment header,  inside an IPv4 packet.  Data is sent over the link in  the order shown, left-to-right, top-to-bottom, in the  payload part of a link layer frame.  The IP header is sent first,  then the TCP segment header, then the  TCP payload data.  Looking at the TCP segment header,  highlighted in green, we see that it  comprises a number of fields.  A TCP segment starts with the source  and destination port numbers. The source port  number identifies the socket that sent the  segment, while the destination port number identifies  the socket to which it should be  delivered.  When establishing a TCP connection, a TCP  server binds to a well-known port that  identifies the type of service it offers.  For example, web servers bind to port  80. This gives a known destination port  to which clients can connect.  Clients specify the destination port to which  they connect, but usually leave their source  port unspecified. The operating system then chooses  an unused source port for that connection.  All TCP segments sent from the client  to the server, as part of a  single connection, have the same source and  destination ports, and the responses come back  with those ports swapped. Each new connection  gets a new source port.  Following the port numbers in the TCP  segment header, come the sequence number and  the acknowledgement number. At the start of  a TCP connection, the sequence number is  set to a random initial value.  As data is sent, the TCP sequence  number inserted by the sender increases,  counting the number of bytes of data  being sent. The acknowledgement number indicates the next byte  of data that’s expected by the receiver.  For example, if a TCP segment is  received with sequence number 4,000, and that  segment contains 100 bytes of data,  then the acknowledgement number will be 4,100.  This indicates that the next TCP segment  expected is that with sequence number 4,100.  If the acknowledgement number that comes back  is different from that expected, it’s a  sign that some of the packets have  been lost. TCP will then retransmit the  segments that were in the lost packets.  The data offset field indicates where the  payload data starts in the segment.  That is, it indicates the size of  any TCP options included in the packet.  The reserved bits are not used.  There are then six single-bit flags.  The URG bit indicates whether the urgent  pointer is valid. The ACK bit indicates  whether the acknowledgement number is valid.  The PSH bit indicates that this is  the last segment in a message,  and should be pushed up to the  application as soon as possible.  The SYN, synchronise, bit is set on  the first packet sent on a connection.  The FIN bit indicates that the connection  should be cleanly closed. And the RST  bit indicates that the connection will reset,  aborted, without cleanup.  The receive window size allows the receiver  to indicate how much buffer space it  has available to receive new data.  This allows the receiver tell the sender  to slow down, if it’s sending faster  than the receiver can process the data.  The checksum is used to detect corrupted  packets, that can then be retransmitted.  Finally, the Urgent Pointer allows TCP senders  to indicate that some data is to  be processed urgently by the receiver.  Unfortunately, experience has shown that the urgent  data mechanism in TCP is not usable  in practice, due to a combination of  an ambiguous specification and inconsistent implementation.  The fixed TCP segment header is followed  by TCP option headers, that allow TCP  to add new features and extensions,  and then the payload data.

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

## TCP不保证消息边界-造成应用程序复杂

TCP对丢失的数据包进行重传，并确保数据按照最初发送的顺序传递给应用程序。它还调整其发送数据的速度，以配合可用的网络容量。因此，TCP提供了一个可靠、有序的字节流传输服务。

![](/static/2021-01-21-17-38-20.png)

:orange: TCP服务模型的一个限制是，**消息的边界没有被保留下来**。

* 也就是说，如果一个应用程序将2000个字节的数据块写入TCP连接，那么TCP将按照发送的顺序将这2000个字节可靠地传递给接收者。
  * 然而，TCP并不保证这些字节作为一个2000字节的单一块被传递给接收方的应用程序。这有可能发生。
  * 同样，它们也可能以两个1000字节的块的形式传递给应用程序。
  * 或者作为一个1,500字节的块，接着是一个500字节的块。
  * 或者作为2000个单字节。
  * **或者以任何其他组合的形式，只要数据能可靠地按照发送的顺序交付**。
* **这使得使用TCP的【应用程序的设计变得复杂】，因为它们必须解析从TCP套接字中收到的数据，以检查它们是否得到了完整的信息**。
  * <font color="red">尽管有这种不便，但对于大多数应用来说，TCP是正确的选择。如果你需要可靠地、尽可能快地传递数据，那么就使用TCP</font>。

TCP retransmits any data that was sent  in packets that are lost, and makes  sure that data is delivered to the  application in the order in which it  was originally sent. It also adapts the  speed at which it sends data to  match the available network capacity. As a  result, TCP provides a reliable, ordered,  byte stream delivery service.  A limitation of the TCP service model  is that message boundaries are not preserved.  That is, if an application writes,  for example, a block of 2,000 bytes  to a TCP connection, then TCP will  deliver those 2,000 bytes to the receiver,  reliably, and in the order they are  sent.  However, what TCP does not do,  is guarantee that those bytes are delivered  to the receiving application as a single  block of 2,000 bytes. That might happen.  Equally, they could be delivered to the  application as two blocks of 1,000 bytes.  Or as a block of 1,500 bytes,  followed by a block of 500 bytes.  Or as 2,000 single bytes. Or as  any other combination, provided the data is  delivered reliably and in the order sent.  This complicates the design of applications that  use TCP, since they have to parse  the data received from a TCP socket  to check if they’ve got the complete  message. Despite this inconvenience, TCP is the right  choice for most applications. If you need  to deliver data reliably, and as fast  as possible, then use TCP.

---

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

# TCP-C/S连接模式：Client-server Connections

C-S连接建立原理 & 影响连接建立速度的因素

---

![](/static/2021-01-21-21-02-25.png)

**基于TCP的应用程序通常以客户-服务器的方式工作**。

* **服务器**在一个众所周知的**端口上监听连接**，
* 客户端连接到服务器，发送请求，并接收响应。
* <font color="red">原则上，TCP也可以以点对点的方式使用(P2P)</font>
  * 如果两个设备创建TCP套接字，绑定到已知的端口，并同时试图连接（）到对方，那么TCP将能够创建一个连接，**只要没有防火墙或NAT设备阻挡流量**。
  * 这就是所谓的**同时开放**。 <font color="red">TCP同时开放可以工作，但不是特别有用，因为它要求两个对等体同时尝试连接。</font>。
* **通常在客户-服务器模式下工作更好，因为服务器可以等待客户，客户和服务器不需要同步何时连接**

TCP-based applications usually work in a a  client-server manner. The server listens for connections  on a well-known port, and the client  connects to the server, sends a request,  and receives a response.  TCP can also, in principle, be used  in a peer-to-peer manner.  If two devices create TCP sockets,  bind to known ports, and simultaneously attempt  to connect() to each other, then TCP  will able to create a connection,  provided there are no firewalls or NAT  devices blocking traffic. This is known as  simultaneous open.  TCP simultaneous open can work, but isn’t  especially useful, since it requires both peers  to try to connect at the same  time. It’s usually better to work in  client-server mode, since the server can wait  for clients and the client and server  don’t need to synchronise when to connect.

---

:orange: 通常基于TCP的app都为**client-server**连接模式

* server监听已知端口
  * server可以等待client的连入，不需要进行同步
* client连入server
* client发送请求，server响应请求

:orange:TCP也可以用于**P2P**连接模式

* 两个设备同时互相连接，互相交换数据
* 不常见，但可行，只需要两个设备IP可达已知，都绑定到已知port上（前提，没有firewall&NAT阻塞流量）

# TCP连接建立过程：Client-Server Connection Establishment

C/S

ACK: 期待收到**对方下一个报文段的第一个数据字节的序号**

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

例如，让我们假设一个浏览器通过TCP运行的HTTP从一个网络服务器上请求一个简单的网页。 该网页包括一个HTML文件、一个CSS样式表和一张图片。HTML和CSS文件在一台服务器上，而图像位于另一台服务器上。 检索该页面需要多长时间？

* 客户最初连接到HTML文件所在的服务器。 这需要**一个往返的时间来交换SYN和SYN-ACK数据包，并完成connect()调用**。
* 一旦connect()完成，客户端就发送对HTML的请求。**请求到达服务器后，又需要一个往返的过程，然后是响应的第一部分回来**，接着是**响应的其余部分的序列化时间**。
* 当它收到HTML时，客户端知道它需要检索CSS文件和图像。 它重新使用与第一个服务器的现有连接来**检索CSS文件**。
  * 这需要**一个额外的往返**，**加上CSS数据的序列化时间**。
* 与此同时，它还打开了一个与第二台服务器的TCP连接，发送了一个图片请求，并下载了响应。**这需要两次往返，加上发送图像数据的时间**。
  * 其中所需时间最长的，加上建立初始连接和获取HTML的时间，决定了下载页面的总时间。

:orange: **往返的时间（建立连接，请求资源）取决于从客户端到服务器的距离**。 **序列化时间（获取后续数据）取决于网络的可用容量**。例如，如果图像大小为1兆字节，8兆比特，而链接的可用带宽为每秒2兆比特，那么除了往返时间外，图像的下载将需要4秒。

For example, let’s assume a browser is  requesting a simple web page from a  web server using HTTP running over TCP.  That web page comprises a single HTML  file, a CSS style sheet, and an  image. The HTML and CSS files are  on one server, while the image is  located on a different server.  How long does it take to retrieve  that page?  Well, the client initially connects to the  server where the HTML file is located.  This takes one round-trip time for the  SYN and SYN-ACK packets to be exchanged,  and for the connect() call to complete.  As soon as the connect() completes,  the client sends the request for the  HTML. It takes another round-trip for the  request to reach the server and the  first part of the response to come  back, followed by the serialisation time for  the rest of the response.  When it’s received the HTML, the client  knows that it needs to retrieve the  CSS file and the image.  It reuses the existing connection to the  first server to retrieve the CSS file.  This takes an additional round trip,  plus the serialisation time of the CSS  data.  In parallel to this, it opens a  TCP connection to the second server,  sends a request for the image,  and downloads the response. This takes two  round trips, plus the time to send  the image data.  Whichever of these takes the longest,  plus the amount of time to make  the initial connection and fetch the HTML,  determines the total time to download the  page.  The round trip time depends on the  distance from the client to the server.  The serialisation time depends on the available  capacity of the network. For example,  if the image is 1 megabyte,  8 megabits, in size, and the available  bandwidth of the link is 2 megabits  per second, then the image will take  4 seconds to download, in addition to  the round trip time.

---

## 距离&网络拥塞程度（可用带宽）决定RTT

通常RTT

![](/static/2021-01-22-00-49-38.png)

可以看出，**总的下载时间（连接时间+请求时间+下载时间）既取决于往返时间，也取决于可用的带宽和正在下载的数据大小**

* **典型的往返时间是多少**？ 这**取决于客户端和服务器之间的距离，以及网络拥堵的程度**。
* 右边的表格给出了一些典型的数值，是从我的家庭网络上的一台笔记本电脑到不同的目的地测量的。 有很多的变化。在最好的情况下，从英国的服务器获得响应需要33ms左右，从美国东海岸的服务器获得响应需要100ms左右，从加利福尼亚的服务器获得响应需要165ms左右，从澳大利亚获得响应需要300ms左右。最坏的情况是，当网络上有其他流量时，要高得多。 这意味着，无论请求多少数据，向纽约的服务器发送一个请求至少需要1/10秒的时间。
* **可用带宽**如何？ ADSL通常获得每秒25兆比特左右。 VDSL，通常被称为光纤到路边，连接通过家庭电话线到街道上的机柜，然后通过光纤到交换机和其他地方，通常每秒约50兆比特。 而光纤到户，即光纤直接进入你的家庭，可以每秒传输几百兆比特。 **4G无线网络的变化很大，取决于你的供应商启用的选项和接收质量，但通常在每秒15-30兆比特的范围内**

It can be seen that the total  download time depends on both the round  trip time, the available bandwidth, and the  size of the data being downloaded.  What’s a typical round trip time?  This depends on the distance between the  client and server, and on the amount  of network congestion. The table on the  right gives some typical values, measured from  a laptop on my home network to  various destinations.  There’s a lot of variation. In the  best case, it takes around 33ms to  get a response from a server in  the UK, around 100ms to get a  response from a server on the East  coast of the US, around 165ms from  a server in California, and around 300ms  from Australia. Worst case, when there’s other  traffic on the network, is considerably higher.  This means that a request sent to  a server in New York takes at  least 1/10th of a second, irrespective of  how much data is requested.  What about available bandwidth?  Well, ADSL typically gets around 25 megabits  per second.  VDSL, often known as fibre to the  kerb, where the connection runs over your  home phone line to a cabinet in  your street, then over fibre to the  exchange and beyond, typically gets around 50  megabits per second.  And fibre to the premises, where the  optical fibre runs direct into your home,  can transmit several hundred megabits per second.  4G wireless is highly variable, depending on  the options enabled by your provider and  the reception quality, but somewhere in the  15-30 megabits per second range is typical.

* **取决于client & server之间距离**
* **取决于网络拥塞量**

---

## RTT影响效率/下载时间的主要原因

![](/static/2021-01-22-01-04-32.png)

这在实践中意味着什么？ 让我们以我们之前使用的单一网页为例，包括HTML、CSS样式表和一张图片，并插入一些典型的文件大小数字，如幻灯片所示。**我们还假设两个服务器的往返时间是相同的，以使数字更容易理解**。 然后，该表描绘了在**不同的带宽值和到服务器的往返时间下，下载该简单网页所需的总时间**。

* 最慢的情况是表格的左下方，假设以**每秒1兆**的链接到服务器，往返时间为**300毫秒**，则需要45.1秒才能下载该网页。这是与澳大利亚服务器的**慢速连接**的模型。
* 最快的是表格的右上方，从**千兆**链接上位于**1毫秒**之外的服务器下载该页面需要0.04秒。 
* 有趣的是，随着链接速度的提高，下载时间是如何变化的。 如果我们看最上面一行，**往返时间为1毫秒**，
  * 我们可以看到，**如果我们将带宽增加10倍，从100Mbps增加到1Gbps，下载页面的时间就会减少10倍，减少90%。链接快了十倍，页面下载也快了十倍**。
* 如果我们反过来看最下面一行，**往返时间为300ms，将链接速度从100Mbps提高到1Gbps，下载时间只减少了22%**。
* 其他链接则处于中间位置。
* **互联网服务提供商喜欢根据链接速度来宣传他们的服务。 他们自豪地宣布，他们现在可以提供千兆位的链接，而且这些链接现在比以前快十倍以上**！
  * 这是真的。 而这是事实。 
  * 但是，就实际下载时间而言，**除非你下载的是非常大的文件**，<font color="red">否则往返时间往往是限制因素</font>。
  * 对于典型的网页，**如果链接速度提高10倍，下载时间可能只提高2倍**。 仍然值得为更快的互联网连接支付额外费用吗？

:orange: RTT &带宽之间的关系

* 拥塞量低时，link 10 times faster，download speed 10 times faster（带宽增加10倍，下载速度快10倍）

## 协议设计决定RTT- Example: HTTP/1.1 & SMTP

HTTP/1.1
![](/static/2021-01-22-01-08-12.png)

* 这对协议设计意味着什么？ 这个例子显示了一个HTTP/1.1交换。 一旦连接被打开，客户端就向服务器发送蓝色的数据，前缀为 "C:"。然后，服务器用红色的数据进行响应，前缀为 "S:"，包括一些头信息和所请求的页面。 **一切都在一次往返中完成**。请求。然后是响应。What does this mean for protocol design?  The example shows an HTTP/1.1 exchange.  Once the connection has been opened,  the client sends the data shown in  blue, prefixed with the letter “C:”,  to the server. The server then responds  with the data shown in red,  prefixed with the letter “S:”, comprising some  header information and the requested page.  Everything is completed in a single round  trip. Request. Then response.

simple mail transfer protocol

![](/static/2021-01-22-01-13-53.png)

* 3次握手（连接建立RTT） + initial greeting （RTT） + HELO(RTT) + FROM(RTT) + RCPT(RTT) + DATA（RTT） + true data（RTT）+ quit（RTT）= 8RTT
* 与这个例子相比，显示了用于发送电子邮件的简单邮件传输协议（SMTP）。 与上一张幻灯片一样，从客户端发送到服务器的数据显示为蓝色，前缀为字母 "C:"，从服务器发送到客户端的数据显示为红色，前缀为字母 "S:"。
* **我们看到，该协议是非常健谈的。
  * **一旦连接建立，在SYN、SYN-ACK和ACK之后，服务器会发送一个初始问候语。建立连接和发送这个初始问候语需要两次往返**。
  * 然后，客户端发送HELO，并等待服务器的同意。 **这还需要一次往返**。
  * 然后，客户端发送来自地址，并等待服务器的到来。**再进行一次往返**。
  * 客户端然后发送收件人，并等待服务器。**再来一次往返**。
  * 然后客户端说它现在想发送数据，并等待服务器。再来**一次往返**。
  * 然后，客户端终于可以发送数据了，一旦确认收到了数据，就发送QUIT，等待，然后关闭连接。
  * **整个交换过程需要8次往返**。 这是必要的或有效的吗？ 没有！

:orange: **如果协议设计得不同，所有的数据都可以在连接被打开时一次性发送，而服务器可以用 "好 "或 "错误 "来回应。八次往返可以减少到两次**：一次是建立连接，一次是发送信息并得到服务器的确认。 这就是为什么电子邮件的发送速度很慢。

Compare that with this example, showing the  Simple Mail Transfer Protocol, SMTP, used to  send email.  As with the previous slide, data sent  from client to server is shown in  blue and prefixed with the letter “C:”,  and that sent from the server to  the client is in red, prefixed with  the letter “S:”.  We see that the protocol is very  chatty.  Once the connection is established, after the  SYN, SYN-ACK, and ACK, the server sends  an initial greeting. Establishing the connection and  sending this initial greeting takes two round  trips.  The client then sends HELO, and waits  for the go ahead from the server.  This takes one more round trip.  The client then sends the from address,  and waits for the server. One more  round trip.  The client then send the recipients,  and waits for the server. One more  round trip.  The client then says it’d like to  send data now, and waits for the  server. One more round trip.  Then, finally the client gets to send  the data, and once it’s confirmed that  the data was received, sends QUIT,  waits, then closes the connection.  The whole exchange takes eight round trips.  Is this necessary or efficient?  No!  If the protocol were designed differently,  all the data could be sent at  once, as soon as the connection was  opened, and the server could respond with  an okay or an error. The eight  round trips could be reduced to two:  one to establish the connection, one to  send the message and get confirmation from  the server.  This is why email is slow to  send.

# TLS&IPv6对连接效率的影响：Impact of TLS & IPV6 on Connection Establishment

* 添加TLS后对连接效率的影响 Transport Layer Security (TLS) needs extra RTTs to negotiate security
* 当目标主机采用双栈协议，如何保证效率？Dual-stack IPv4 and IPv6 hosts must race connections for good performance

## TLS原理: Impact of TLS

:orange: 在上一部分中，我展示了**网络往返时间是如何成为性能（下载速度，，RTT过大情况下，即使带宽提升，下载速度不一定有显著提升）的限制因素的**。

* 这是因为**每个TCP连接至少需要两次往返时间：一次是建立连接，另一次是客户端发送请求并从服务器接收响应**。 
  * 以HTTP和SMTP为例，说明了在TCP上运行的协议是如何对性能产生重大影响的，
    * 前者在一次往返中发送请求和接收响应，后者则进行多次不必要的往返。

:orange: **在TCP上运行的重要协议之一是传输层安全协议，即TLS。 TLS为TCP连接提供安全性**。

* 也就是说，**它允许客户端和服务器商定加密和认证密钥，以确保通过该TCP连接发送的数据是保密的，并保护其在传输过程中不被修改**
* **TLS对互联网安全至关重要**
  * 当你使用HTTPS检索一个安全网页时，它**首先打开一个与服务器的TCP连接**。
  * 然后，它**运行TLS以启用该连接的安全性**。
  * 然后，它要求检索该网页。

:orange: **根据所使用的TLS的版本，这将增加连接的时间**。

* 对于最新的TLS版本，即**TLS v1.3，它需要额外的一次往返，以商定加密和认证密钥**。
  * 也就是说，在通过SYN-SYN-ACK-ACK握手建立TCP连接后，客户端和服务器需要额外的往返以启用TLS，然后才能请求数据。

:orange: **TLS握手分为三个部分**。

![](/static/2021-01-22-02-01-33.png)

* 首先，**客户端向服务器发送一个TLS ClientHello消息以提出安全参数**。
* 然后，**服务器以TLS ServerHello回应，包含其密钥和其他安全参数**。
* 最后，假设有一个匹配，**客户端用一个TLS Finished消息回应，以设置加密参数**。然后，**客户端立即接着发送应用数据，如HTTP GET请求，而不等待响应**。
* <font color="red">在大多数情况下，这将增加一个额外的往返时间。 旧版本的TLS需要更长的时间</font>。
  * 例如，TLS v1.2需要至少两次往返，以协商一个安全连接。

In the previous part, I showed how  the network round trip time can be  the limiting factor in performance. This is  because every TCP connection needs at least  two round trip times: one to establish  the connection, and one for the client  to send a request and receive a  response from the server.  I also showed how the protocol running  over TCP can make a significant difference  to performance, with the examples of HTTP,  which sends a request and receives a  response in a single round trip,  and SMTP, which makes multiple unnecessary round  trips.  One of the important protocols that runs  over TCP is the transport layer security  protocol, TLS.  TLS provides security for a TCP connection.  That is, it allows the client and  server to agree encryption and authentication keys  to make sure that the data sent  over that TCP connection is confidential and  protected from modification in transit.  TLS is essential to Internet security.  When you retrieve a secure web page  using HTTPS, it first opens a TCP  connection to the server. Then, it runs  TLS to enable security for that connection.  Then, it asks to retrieve the web  page.  Depending on the version of TLS used,  this adds additional time to the connection.  With the latest version of TLS,  TLS v1.3, it takes one additional round  trip to agree the encryption and authentication  keys. That is, after the TCP connection  has been established, via the SYN -  SYN-ACK - ACK handshake, then the client  and server need an additional round trip  to enable TLS, before they can request  data.  The TLS handshake is in three parts.  First, the client sends a TLS ClientHello  message to the server to propose security  parameters. Then, the server responds with a  TLS ServerHello, containing its keys and other  security parameters. Finally, assuming there’s a match,  the client responds with a TLS Finished  message to set the encryption parameters.  The client then immediately follows this by  sending the application data, such as an  HTTP GET request, without waiting for a  response.  This adds one additional round trip time,  in most cases.  Older versions of TLS take longer.  TLS v1.2, for example, takes at least  two round trips to negotiate a secure  connection.

:orange: 每个TCP基本需要2RTT

* **建立连接** 1RTT
* client发送接受请求 1RTT
* 基于TCP连接的app层协议的采用，效率差异也非常大（HTTP/1.1 vs SMTP）
  * HTTP发送&检索数据非常快 2RTT
  * SMTP 8RTT（不必要）

:orange: TLS

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

### 例子

![](/static/2021-01-22-02-09-23.png)

由于TLS而产生的额外往返对性能有什么影响？ 好吧，让我们再看看上一部分的简单网页下载例子。【进一步强调，一个连接必须在**客户端和服务器之间进行的往返次数，往往是性能的限制因素**】

* **当往返时间可以忽略不计时，如最上面一行的1ms往返时间，性能是不变的（与之前2个RTT一致**）。
* 不过，随着我们往下看，性能会越来越差。
  * 当往返时间为100ms时，整体性能和提高链路速度的好处都会下降。与**没有TLS的连接相比，千兆链接上的页面下载时间增加了45%，从0.44秒到0.64秒**。
  * 而从百兆链接到千兆链接的好处只有36%，而不是没有TLS的45%。
  * 在往返时间为300毫秒的情况下，情况甚至更糟。与不使用TLS的情况相比，总的下载时间增加了48%，而从100兆的链接到千兆的链接时，下载时间只减少了22%。What impact does the additional round trip  due to TLS have on performance?  Well, let’s look again at the simple  web page download examples from the previous  part.  When the round-trip time is negligible,  as on the top row with 1ms  round trip time, performance is unchanged.  As we go down the table,  though, performance gets worse. With 100ms round  trip time, both the overall performance and  the benefit of increasing the link speed  go down. The download time for the  page on a gigabit link is increased  by 45%, from 0.44 to 0.64 seconds,  compared to a connection without TLS.  And the benefit of going from a  100 megabit link to a gigabit link  is only 36%, rather than 45% without  TLS.  With 300ms round trip time the behaviour  is even worse. Total download time increases  by 48% compared to the non-TLS case,  and there’s only a 22% reduction in  download time when going from a 100  megabit link to a gigabit link.

* 1ms（无拥塞，RTT可忽略情况下），效率跟不采用TLS一致
* 300ms（高延误），效率比无TLS更差，增加带宽后效果差异也更不明显

---

### TLS仍必不可少，TLS1.3已经提供最佳性能

![](/static/2021-01-22-02-14-49.png)

这并不是说TLS不好! 远非如此--安全是必不可少的。 相反，它进一步强调，一个连接必须在**客户端和服务器之间进行的往返次数，往往是性能的限制因素**。

* 具有良好性能的应用程序将**试图减少他们建立的TCP连接的数量，因为每个连接的建立需要时间**。
* 他们还试图**限制请求-响应交换的数量，每个连接中都需要一个往返**。
* 2018年标准化的TLS v1.3是一个很大的胜利，因为它将实现安全所需的往返次数从两次减少到一次。
  * **当与TCP一起使用时，这提供了可能的最佳性能：一次往返建立TCP连接，一次往返协商安全，然后才可以发送数据**。 我们将在第三和第四讲中更多地讨论TLS以及如何提高安全连接的性能。This is not to say that TLS  is bad! Far from it – security  is essential.  Rather, it further highlights that the number  of round trips that a connection must  perform, between client and server, is often  the limiting factor in performance.  Applications that have good performance will try  to reduce the number of TCP connections  that they establish, since each connection takes  time to establish. They also try to  limit the number of request-response exchanges,  each taking a round trip, they make  on each connection.  TLS v1.3, standardised in 2018, was a  big win here, because it reduces the  number of round trips needed to enable  security from two, down to one.  When used with TCP, this gives the  best possible performance: one round trip to  establish the TCP connection, and one to  negotiate the security, before the data can  be sent.  We’ll talk more about TLS and how  to improve the performance of secure connections  in lectures 3 and 4.

:orange: 改进效率

* 减少TCP连接数量
* 限制client&server之间交换request-response的数量

## IPV4/6（双栈协议）对TCP性能的影响：Impact of IPV6 and Dual Stack Deployment

:orange: **影响TCP连接性能的另一个因素是正在向IPv6过渡**

* 这一过渡意味着我们目前有两个互联网：IPv4互联网和IPv6互联网。
  * 一些主机只能使用IPv4进行连接。 有些主机只能使用IPv6进行连接。
  * 而有些主机则同时拥有两种类型的地址
  * 同样，有些网络链接只能承载IPv4流量，有些只能承载IPv6，而有些链接可以承载两种流量。
  * **而有些防火墙，或其他中间箱，会阻止IPv4，有些会阻止IPv6，有些会阻止两种类型的流量**。 重要的是，IPv6网络不是IPv4网络的一个子集。它是一个独立的互联网，在某些地方是重叠的。The other factor affecting TCP connection performance  is the ongoing transition to IPv6.  This transition means that we currently have  two Internets: the IPv4 Internet and the  IPv6 Internet.  Some hosts can only connect using IPv4.  Some hosts can only connect using IPv6.  And some hosts have both types of  address.  Similarly, some network links can only carry  IPv4 traffic, some only IPv6, and some  links can carry both types of traffic.  And some firewalls, or other middleboxes,  block IPv4, some block IPv6, and some  block both types of traffic.  Importantly, the IPv6 network is not a  subset of the IPv4 network. It’s a  separate Internet, that overlaps in places.

---

IPV4到IPV6的转移也对效率（连接建立，。即TCP性能）有影响

![](/static/2021-01-22-02-23-09.png)

* 注意IPv6不是IPv4的子集
* 某些主机，链路，只支持v4，支支持v6，或同时支持v4&v6

---

## “快乐眼球”-双栈协议(过渡)，如何建立TCP连接？

:orange: 因为支持的网络不确定（双栈），如何建立连接？（**并保证TCP性能**）

![](/static/2021-01-22-02-41-53.png)

**鉴于有些主机可以通过IPv4到达，但不能通过IPv6到达，反之亦然，那么在过渡期间如何建立连接**？

* 给定一个主机名，你可以使用getaddinfo()调用进行DNS查询，找到该主机的IP地址。
  * 这将返回一个**该主机可能的IP地址列表，包括IPv4和IPv6地址**。
  * 简单的方法是一个**循环**，依次尝试每个地址，**直到有一个成功连接**。
    * <font color="red">这种方法是可行的，但可能会很慢。</font>
    * 在幻灯片上的例子中，Netflix有16个可能的IP地址，其中8个是IPv6，8个是IPv4，并在其DNS响应中首先列出IPv6地址。如果你只有IPv4的连接，那么你可能要花很长时间来尝试连接8个不同的IPv6地址，但都以失败告终，最后才找到一个可以使用的IPv4地址。
* <font color="red">为了获得良好的性能，应用程序使用一种被称为 "快乐眼球 "的技术。</font>
  * 这涉及到**两个独立的DNS查询，并行进行**，一个只要求IPv4地址，一个只要求IPv6地址。
  * 从这些DNS查询中**最先完成的那一个开始，客户端与服务器返回的第一个地址进行连接**。
    * 如果在100毫秒内没有成功，它就开始向下一个可能的地址发出另一个连接请求，在IPv4和IPv6地址之间交替进行。
  * **不同的连接请求并行进行，直到最终有一次成功**。
    * <font color="red">第一个成功的连接被使用，无论是通过IPv4还是IPv6，其他的连接请求被取消</font>

:orange: **快乐眼球技术**试图平衡连接所需的时间与同时尝试许多可能的连接所带来的网络过载。**它增加了连接设置的复杂性，以达到良好的性能（连接建立速度 & 查询**）。Given that some hosts will be reachable  over IPv4 but not IPv6, and vice  versa, how do you establish connections during  the transition?  Well, given a hostname, you perform a  DNS lookup to find the IP addresses  for that host using the getaddinfo() call.  This returns a list of possible IP  addresses for the host, including both IPv4  and IPv6 addresses.  The simple approach is a loop,  trying each address in turn, until one  successfully connects.  This works, but can be very slow.  In the example on the slide,  Netflix has 16 possible IP addresses,  eight IPv6 and eight IPv4, and lists  the IPv6 addresses first in its DNS  response. If you have only IPv4 connectivity,  it may take a long time to  try, and fail, to connect to eight  different IPv6 addresses before you get to  an IPv4 address that works.  To get good performance, applications use a  technique known as “Happy Eyeballs”.  This involves making two separate DNS lookups,  in parallel, one asking for only IPv4  addresses and one for only IPv6 addresses.  Starting with whichever of these DNS lookups  completes first, the client makes a connection  to the first address returned by the  server. If that hasn’t succeeded within 100ms,  it starts another connection request to the  next possible address, alternating between IPv4 and  IPv6 addresses.  The different connection requests proceed in parallel,  until once eventually succeeds. That first successful  connection is used, whether over IPv4 or  IPv6, and the other connection requests are  cancelled.  The happy eyeballs technique tries to balance  the time taken to connect vs.  the network overload of trying many possible  connections at once in parallel. It adds  complexity to the connection setup, to achieve  good performance.

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

# 5种方式保证TCP性能：Connection Latency

![](/static/2021-01-22-02-44-22.png)

影响TCP性能的两个因素是**带宽和RTT**延时。**在许多情况下，延迟，即往返时间，占主导地位**。

* 使用TCP的应用有**五种方法来提高其性能**。 
  * 第一种是，如果服务器有一个以上的地址（**双栈**），
    * 客户端应该使用类似**快乐眼球**的东西，重叠连接请求。
    * 这比试图依次连接到每个不同的地址要**复杂得多，但连接的速度要快得多**。
  * 提高TCP性能的第二个方法是**减少TCP连接的数量。每个连接都需要时间来建立**。
    * 如果你能**建立一个TCP连接并将其重复用于多个请求【复用TCP连接**】，这比为每个请求建立一个新的连接要快。
  * 第三，如果你**减少每个连接的请求-响应交换的数量，你就会减少往返延迟的影响**。
    * 通过**有效地使用TCP连接**，所有这些对任何应用程序来说都是可能的。
  * 还有两个更激进的变化可以做。
    * 第一个是<font color="red">重叠TCP和TLS的连接设置握手，将安全参数与初始连接请求一起发送，这样连接设置和安全参数就可以在一次往返中协商完成。</font>
      * 这在TCP中是不可能的 ----- **QUIC**传输协议确实允许这样做。
      * 最后，人们总是可以通过**减少往返的延迟**来提高性能。
      * 这个延迟取决于两件事：**信号在链路上传播的速度，以及其他流量的数量**。
        * 由于信号是以光速沿着电缆和光纤传播的，所以几乎没有办法提高传播速度，尽管低地球轨道卫星可以提供帮助，我们将在第6讲讨论。
        * 不过，<font color="deeppink">减少在中间环节排队的其他流量是一种可能性，这可以通过选择TCP拥塞控制算法来影响</font>。The two factors affecting TCP performance are  bandwidth and latency. In many cases,  the latency, the round trip time,  dominates.  The are five ways in which applications  using TCP improve their performance.  The first is that a client should  use something like happy eyeballs, overlapping connection  requests if the server, if the server  has more than one address. This is  more complicated to implement than trying to  connect to each different address in turn,  but connects a lot faster.  The second way to improve TCP performance  is to reduce the number of TCP  connections made. Each connection takes time to  establish. If you can make a single  TCP connection and reuse it for multiple  requests, that’s faster than making a new  connection for each request.  Third, if you reduce the number of  request-response exchanges made over each connection,  you reduce the impact of the round  trip latency.  All these are possible for any application,  by using TCP connections effectively.  There are also two more radical changes  that can be made.  The first is to overlap and TCP  and TLS connection setup handshakes, by sending  the security parameters along with the initial  connection request, so that both the connection  setup and security parameters can be negotiated  in a single round trip. This isn’t  possible with TCP, but the QUIC transport  protocol, that we’ll discuss in lecture 4,  does allow this.  Finally, one can always improve performance by  reducing the round trip latency. This latency  depends on two things: the speed at  which the signal propagates down the link,  and the amount of other traffic.  Since signals travel down electrical cables and  optical fibres at the speed of light,  there’s little that can be done to  increase the propagation speed, although low earth  orbit satellites can help, we’ll discuss in  lecture 6. Reducing the amount of other traffic queued  up at intermediate links is a possibility  though, and this can be affected by  the choice of TCP congestion control algorithm.

* 影响TCP效率（主要是连接效率）的**主要**因素
  * **RTT**
    * C-S之间的距离
    * **网络拥塞量**（latency）
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

# NAT增加P2P连接困难:Peer to Peer Connections

网络概来说都是P2P连接，不管设备位置/类型 （注意TCP可以建立P2P连接，但很复杂，前提是无防火墙&NAT设备拦截流量）

![](/static/2021-01-22-16-02-48.png)

互联网被设计成**点对点的网络，在IP层上没有区分客户和服务器**。

* <font color="red">原则上，应该可以在网络上的【任何主机】上运行一个TCP服务器，或者一个基于UDP或TCP的点对点应用程序</font>。
  * 只要客户有办法找到服务器的IP地址，并知道它使用的端口号，只要任何防火墙的漏洞被打开，那么服务器是位于某人的家中还是位于数据中心，都不重要。
  * 当然，**数据中心的服务器可能会有更好的性能，因为它可能有一个更快的连接到网络的其他部分。 它也可能更强大**，因为数据中心将有冗余的电源和网络链接、空调和专业的系统管理员。但是，在协议层面上，应该没有什么区别。
* 在实践中，情况并非如此。 在连接到大多数**家用的主机上运行一个服务器是很困难的，而且很难使点对点的连接发挥作用。 造成这种情况的原因是网络地址转换（NAT）的广泛使用**。The Internet was designed as a peer-to-peer  network, and makes no distinction between clients  and servers at the IP layer.  In principle, it should be possible to  run a TCP server, or a UDP-  or TCP-based peer-to-peer application, on any host  on the network. As long as the  clients have some way of finding the  server’s IP address, and knowing what port  number it’s using, and as long as  any firewall pinholes are opened, then it  shouldn’t matter whether a server is located  in someone’s home or in a data  centre.  A server in a data centre is  likely to have better performance, of course,  because it’s probably got a faster connection  to the rest of the network.  It’s also likely to be more robust,  because the data centre will have redundant  power and network links, air conditioning,  and professional system administrators. But, at the  protocol level, there shouldn’t be a difference.  In practise, this is not the case.  It’s difficult to run a server on  a host connected to most residential broadband  connections, and it’s difficult to make peer-to-peer  connections work.  The reason for this is the widespread  use of network address translation – NAT.

* 实践中，很难为绝大宽带建立P2P连接（因为NAT的广泛使用）

# NAT概念：Network Address Translation

NAT原理

![](/static/2021-01-22-20-59-48.png)

什么是网络地址转换？

* NAT是一个过程，通过这个过程，**几个设备可以共享一个公共IP地址**。
* **它允许几个主机形成一个私人内部网络，并从一个特殊用途的范围内分配IP地址**。
* 一个设备--网络地址转换器，即**NAT--同时连接到私人网络和互联网，并可以在这两个网络之间转发数据包**
  * 当它这样做时，它改写、翻译IP地址以及TCP和UDP端口号，因此所有数据包看起来都来自NAT的IP地址
  * 从本质上讲，它<font color="red">将整个私人网络隐藏在一个单一的IP地址后面</font>
* **这很有用，因为没有足够的IPv4地址给每一个想要连接到网络的设备，也因为部署IPv6需要很长的时间**。
  * NAT是一种解决方法，让你继续使用IPv4设备，并有一些限制，即使没有足够的IPv4地址。What is network address translation?  NAT is the process by which several  devices can share a single public IP  address. It allows several hosts to form  a private internal network, with IP addresses  assigned from a special-use range. One device  – the network address translator, the NAT  – is connected to both the private  network and to the Internet, and can  forward packets between the two networks.  As it does so, it rewrites,  translates, the IP addresses, and the TCP  and UDP port numbers, so all the  packets appear to come from the NAT’s  IP address.  Essentially, it hides an entire private network  behind a single IP address.  This is useful because there aren’t enough  IPv4 addresses for every device that wants  to connect to the network, and because  it’s taking a long time to deploy  IPv6.  NAT is a work around, to let  you keep using IPv4 devices, with some  limitations, even though there aren’t enough IPv4  addresses.

---

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

NAT是如何工作的？好吧，让我们先退一步，想想一个单一的主机是如何连接到网络的。 在图中，一个客户拥有一个单一的主机。该主机连接到一个由互联网服务提供商运行的网络。 该ISP反过来又连接到更广泛的互联网。 

* ISP拥有一系列的IP地址，它可以分配给它的客户。在这个例子中，**它拥有IPv4前缀203.0.113.0/24。也就是说，前24位（即地址的网络部分）与203.0.113.0相匹配的一组IPv4地址被分配给ISP**。 
  * 这些是203.0.113.0至203.0.113.255范围内的IPv4地址。 **主机部分等于0的地址代表网络，不能分配给一个设备**。
  * ISP将该范围内第一个可用的IP地址203.0.113.1**分配给路由器**的内部网络接口，将其与网络的其他部分连接起来，并将**其余的地址分配给客户机器**。
  * 一个特定的客户为其设备分配了IP地址203.0.113.7。 路由器的外部，即面向互联网的一侧，将ISP与网络的其他部分连接起来，其IP地址由ISP所连接的网络分配。在这个例子中，它的IP地址是192.0.2.47。 客户的主机连接到互联网上的一个服务器。该服务器刚好有IP地址192.0.2.53。 
* 客户的主机发送的数据包，其目的IP地址等于服务器的192.0.2.53，源IP地址等于客户的主机的203.0.113.7。 **这些数据包通过网络时没有任何变化**，当它们到达服务器时，它们仍然有目的地IP地址192.0.2.53和源IP地址203.0.113.7。 当它发送回复时，服务器将把目标IP地址设置为客户设备的地址203.0.113.7，并使用自己的地址192.0.2.53作为源IP地址。 **不发生地址转换**。

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

在后来的某个时候，客户购买了另一台主机。它是如何连接到网络的呢？ 应该发生的情况是这样的。

* 首先，客户购买了一个**IP路由器，或由ISP提供一个**。 路由器被用来**为客户创建一个内部网络，连接到ISP的网络**。 这可能是一个以太网，一个WiFi网络，或其他。
  * 路由器的外部接口，即**连接客户和ISP的接口**，继承以前分配给客户单一设备的IP地址，在这种情况下是203.0.113.7。 
  * ISP还为客户分配了一个新的IP地址范围。这将是ISP拥有的IP地址范围的一个子集。在这个例子中，客户被分配的IP地址范围是203.0.113.16/28。也就是说，前28位与203.0.113.16相匹配的IP地址，即203.0.113.16到203.0.113.31的范围。 客户将该范围内的第一个可用地址203.0.113.17分配给路由器的内部网络接口，并将其他地址分配给他们的两个主机。 在这个例子中，这两台主机被赋予203.0.113.18和203.0.113.19地址。
* **最终的结果是，ISP将他们拥有的一些IP地址委托给他们的客户，而客户在他们的网络中使用这些地址**。 客户的一个主机连接到互联网上的一个服务器。 
  * 正如预期的那样，为了做到这一点，该主机发送了一个IP数据包，源IP地址设置为其IP地址，在这种情况下为203.0.113.18，目标地址设置为服务器的IP地址，192.0.2.53。 该数据包通过客户的网络到达其路由器，并被转发到ISP的网络上。它穿过ISP的网络到连接ISP和互联网的路由器，并从那里转发到互联网。 最终，数据包到达了服务器。 当它到达时，它的目的地址仍然等于服务器的地址192.0.2.53，而源地址等于发送它的主机的地址203.0.113.18。 当它发送回复时，服务器将把目标IP地址设置为客户设备的地址，即203.0.113.18，并使用自己的地址，即192.0.2.53，作为源IP地址。 **不发生地址转换**

* 需要额外一个router（占用一个IP），为customer的多设备建立一个内网，再与ISP网连接
* 与外网server交换数据过程同理，整个过程中source，dst不变，**无地址转换**

## 有地址转换：Network Address Translation

通常ISP没有足够IPv4地址供给客户

![](/static/2021-01-22-21-43-08.png)
![](/static/2021-01-22-21-54-01.png)
![](/static/2021-01-22-22-54-23.png)

这就是应该发生的事情，但实际上会发生什么？ ISP要么没有足够的IPv4地址来委托给他们的客户，要么他们想为此收取大量的额外费用。 因此，**客户购买了一个网络地址转换器，并将其连接到ISP的网络**，以取代他们的单一原始主机。

* **NAT的外部接口得到分配给客户原始主机的IP地址**，203.0.113.7。
* 客户像以前一样设置他们的**内部网络，但不是使用ISP分配的IP地址，而是使用其中一个私有IP地址范围**。
  * 在这个例子中，他们使用192.168.0.0到192.168.255.255范围内的地址。
  * **NAT的内部接口**被赋予IP地址192.168.0.1，两台主机得到地址192.16.0.2和192.168.0.3。
* 客户的一台主机再次连接到互联网上的一台服务器。 正如预期的那样，该主机发送了一个IP包，**源IP地址（内网**）设置为其IP地址，在这种情况下是192.168.0.2，**目标地址设置为服务器的IP地址**，192.0.2.53。
  * 该数据包通过客户的网络**到达其NAT路由器**。**NAT重写数据包的源地址，以匹配NAT的外部地址，在这种情况下是203.0.113.7，还将【TCP或UDP端口号重写为NAT上未使用的一些新端口号】，并将数据包转发到ISP的网络**。
  * 在内部，**NAT保留了一个与端口相关的地址转换记录**。Internally, the NAT keeps a record on  the changes it made, associated with the  port.
* 该数据包穿过ISP的网络，到达**连接ISP和互联网的路由器**，并从那里转发到互联网。最终，数据包到达了服务器。
  * 当它到达时，它的目的地址仍然等于服务器的地址，192.0.2.53，但**源地址将等于NAT的地址，203.0.113.7**。
  * **对服务器来说，这个数据包似乎来自NAT**。
    * 当它发送回复时，**服务器将把目标IP地址设置为NAT的203.0.113.7，并使用自己的地址192.0.2.53，作为源地址**。
    * 回复将穿越网络，直到它到达NAT。<font color="red">NAT会查看数据包目的地的TCP或UDP端口号，并使用它来检索所执行的重写的内部记录</font>。
    * 然后，**NAT利用内部记录进行反向重写，将数据包中的【目标IP地址】和【端口】改为【私有网络！！！】上的【主机的地址和端口**】，然后将数据包转发到私有网络上进行传输。

That’s what’s supposed to happen, but what  actually happens?  Well, most likely the ISP either doesn’t  have enough IPv4 addresses to delegate some  of them to their customer, or they  want to charge a lot extra to  do so.  Accordingly, the customer buys a network address  translator, and connects it to the ISP’s  network in place of their single original  host.  The external interface of the NAT gets  the IP address assigned to the customer’s  original host, 203.0.113.7.  The customer sets up their internal network  as before, but instead of using IP  addresses assigned by their ISP, they use  one of the private IP address ranges.  @ 08:50 In this example, they use  addresses in the range 192.168.0.0 to 192.168.255.255.  The internal interface of the NAT is  given IP address 192.168.0.1, and the two  hosts get addresses 192.16.0.2 and 192.168.0.3.  One of the customer’s hosts again connects  to a server on the Internet.  As expected, that host sends an IP  packet with the source IP address set  to its IP address, in this case  192.168.0.2, and the destination address set to  the IP address of the server,  192.0.2.53.  That packet travels through the customer’s network  to its NAT router. The NAT rewrites  the source address of the packet to  match the external address of the NAT,  in this case 203.0.113.7, and also rewrites  the TCP or UDP port number to  some new port number that’s unused on  the NAT, and forwards the packet on  to the ISP’s network.  Internally, the NAT keeps a record on  the changes it made, associated with the  port.  The packet traverses the ISP’s network to  the router connecting the ISP to the  Internet, and is forwarded on from there  to the Internet. Eventually, the packet arrives  at the server. When it arrives,  it still has destination address equal to  that of the server, 192.0.2.53, but source  address will equal to that of the  NAT, 203.0.113.7.  To the server, the packet appears to  have come from the NAT. When it  sends a reply, the server will set  the destination IP address to that of  the NAT, 203.0.113.7, and use its own  address, 192.0.2.53, as the source address.  The reply will traverse the network until  it reaches the NAT. The NAT looks  at the TCP or UDP port number  to which the packet is destined,  and uses this to retrieve its internal  record of the rewrites that were performed.  It then uses this to do the  inverse rewrite, changing the destination IP address  and port in the packet to those  of the host on the private network,  then forwards the packet onto the private  network for delivery.

* 客户使用NAT设备（network address translator）连入ISP网
* NAT设备外接口由ISP分配，
  * NAT内接口连用户子网，IP地址范围私有特定
* **注意C-S间交换数据时，存在地址转换**
  * client->server, source-内网clientIP，dst-外网serverIP. <font color="deeppink">到达NAT内接口后，将地址转换，以匹配外接口IP。NAT设备也会记录转换&关联port</font> <font color="blue">最后数据包到达server时，dst不变，但是source为【NAT地址】</font>
  * 响应时，source为serverIP，dst为NAT地址（然后NAT内部查询port&转换记录，进行inverse translation以便转发到内网设备）

:orange: NAT需要记录地址转换-port映射，以便转发响应包时进行inverse translation

# NAT隐藏内网&可选的私有网段：Private Address Ranges

NAT会隐藏私有内部网段

![](/static/2021-04-29-23-09-44.png)

从本质上讲，**NAT在一个单一的公共IP地址后面隐藏了一个私人网络**。 

* 私人网络可以**使用三个私人IPv4地址范围中的一个**。
  * 10.0.0.0/8, 176.16.0.0/12, 和192.168.0.0/16。
* **私有网络中的机器可以直接使用这些私有IP地址相互交谈，只要通信停留在私有网络内（内网可以随意通信**）。
* 当它们**与网络的其他部分通信时，IP地址被改写（NAT转换**），
  * 这样，对网络的其他部分来说，私有网络看起来**像一个单一的设备（与NAT的外部地址相匹配的IP地址，因为送到服务器的数据包src addr是NAT的外接口IP**。）
  * 这给人一种错觉，即有更多的IPv4地址可用，因为允许相同的私人地址范围在网络的不同部分被重复使用。
  * 例如，你的家庭网络，几乎肯定使用192.168.0.0/16私人地址范围内的地址，并通过你的ISP提供的NAT路由器连接到网络的其他部分。

* IPv4私有网段可为以下3种
  * `10.0.0.0/8`
  * `176.16.0.0/12`
  * **`192.168.0.0/16`**
  * 同一私有网段内的设备可以直接通信，不同网段需要进行地址转换以匹配内网接口网段。（给人一种不同网段复用了IP地址的错觉）

# NAT造成的问题&为什么NAT：Problem due to Network Address Translation

NAT造成的问题 & 即便有问题为什么还使用NAT？

## NAT内网穿透问题（C/S，P2P困难）-推进使用集中云服务：NAT routers Encourage Centralisation

其中一个问题 - NAT break certain classes of app（app通信模式） & encourage centralisation

![](/static/2021-01-22-23-05-11.png)

**NAT路由器的第一个问题是，它们破坏了某些类别的应用，并鼓励集中化**。

* **NAT的设计是为了支持客户-服务器应用**，客户在NAT后面，服务器是公共互联网上的主机。
  * 由拥有私人IP地址的主机发送的数据包可以通过NAT，并将其**IP地址和端口翻译成NAT的公共IP地址**，然后再转发到公共互联网上。
  * NAT还将**保留状态，因此反向翻译**将被应用于对这些数据包的回复，允许它们通过NAT返回。
* 这种行为允许客户端连接到服务器，在此过程中设置NAT翻译状态，并接收响应。
* 不过，<font color="red">反过来就不行了</font>。 **NAT路由器依靠传出的数据包来建立他们需要翻译传入的数据包的映射关系**。
  * 也就是说，**当一个传入(传入NAT外接口的数据包，来自server或其他，主动)的TCP或UDP数据包到达NAT上的某个特定端口时**，NAT会**查看记录**，它以前从该端口发送的内容的记录，以及它是如何被翻译的，以知道什么是要做的反向翻译。
  * 如果该端口上没有传出的数据包（**意味着，这个端口上之前内网没有数据包传出，不存在映射**），**NAT不知道如何翻译**传入的数据包。
    * 它将不知道哪一个私有IP地址可以作为翻译后的数据包的目标地址。
    * **这使得在NAT后面运行服务器变得复杂，因为NAT不知道如何翻译传入的服务器请求（主动的**）。

:orange: 当然，**有可能手动配置NAT以适当地转发数据包，像UPnP这样的工具可以帮助实现这一点，但这些方法很复杂或不可靠**。

* 一般来说，**付钱给云计算供应商来托管服务器更容易、更可靠，这鼓励了集中到大型托管服务**。

:orange: **NAT也使得编写点对点的应用程序变得困难**。

* 部分原因是NAT使传入连接变得困难（外网相对于NAT这边是server，主动）。
* 但这也是因为位于NAT后面的主机只知道他们的私有地址，所以**不能给他们的对等机一个可以连接的公共地址**。
* 这方面有一些解决方案，我将在本讲座的下一部分讲述，但它们很复杂，很慢，而且很浪费。
* <font color="red">除非你真的需要直接点对点连接的隐私和延迟优势，否则通过托管在某地数据中心的服务器转发流量通常更容易</font>，该服务器有一个公共IP地址，再次鼓励服务的集中化。

The first issue with NAT routers is  that they break certain classes of application,  and encourage centralisation.  NATs are designed to support client-server applications,  where the client is behind the NAT  and the server is a host on  the public Internet. Packets sent by a  host with a private IP address can  pass out through the NAT, and will  have their IP address and port translated  to use the public IP address of  the NAT before they’re forwarded to the  public Internet. The NAT will also retain  state, so that the reverse translation will  be applied to replies to those packets,  allowing them to pass back through the  NAT.  This behaviour allows clients to connect to  servers, setting up NAT translation state in  the process, and to receive responses.  The reverse doesn’t work, though.  NAT routers rely on outgoing packets to  establish the mappings they need to translate  incoming packets. That is, when an incoming  TCP or UDP packet arrives at some  particular port on a NAT, the NAT  looks at its record of what it  previously sent from that port, and how  it was translated, to know what’s the  reverse translation to make. If there’s been  no outgoing packet on that port,  the NAT won’t know how to translate  the incoming packet. It won’t know which  of the private IP addresses to use  as the destination address for the translated  packet.  This complicates running a server behind a  NAT, since the NAT won’t know how  to translate incoming requests for the server.  It’s possible to manually configure the NAT  to forward packets appropriately, of course,  and protools like UPnP can help with  this, but these approaches are complicated or  unreliable. It’s generally easier and more reliable  to pay a cloud computing provider to  host the server, which encourages centralisation onto  large hosting services.  NATs also make it hard to write  peer-to-peer applications. In part, this is because  NATs make incoming connections difficult. But it’s  also because hosts located behind a NAT  only know their private address, so can’t  give their peer a public address to  which it can connect. There are solutions  to this, that I’ll talk about in  the next part of this lecture,  but they’re complicated, slow, and wasteful.  Unless you really need the privacy and  latency benefits of a direct peer-to-peer connection,  it’s often easier to relay traffic via  a server hosted in a data centre  somewhere, with a public IP address,  again encouraging centralisation of services.

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

如果NAT路由器有这么多问题，为什么人们还要使用它们？有三个原因。 

* **首先是为了解决IPv4地址空间不足的问题**。 如右图所示，区域互联网注册处的IPv4地址已经用完。没有更多的IPv4地址可供互联网服务供应商和想要连接到互联网的公司使用，他们无法提供足够的IPv4地址来满足需求。
  * **其结果是，IPv4地址稀缺且昂贵。ISP要么没有足够的地址来满足客户的需求，要么这些地址的成本过高**，客户使用带有NAT的私人网络而不是使用公共IPv4地址。
  * **向IPv6的过渡将解决这个问题**，因为IPv6使地址变得廉价和丰富。一个IPv6网络的最小地址分配比整个IPv4互联网大40亿倍! **不幸的是，向IPv6的过渡一直很缓慢**。If NAT routers are so problematic,  why do people use them? There are  three reasons.  The first is to work around the  lack of IPv4 address space.  As shown in the figure on the  right, the Regional Internet Registries have run  out of IPv4 addresses. There are no  more IPv4 addresses available for ISPs and  companies that want to connect to the  Internet, and they can’t provide enough IPv4  addresses to fulfil demand.  The result is that IPv4 addresses are  scarce and expensive. ISPs either don’t have  enough addresses to meet their customers needs,  or the cost of those addresses is  prohibitive, and customers use a private network  with a NAT instead of using public  IPv4 addresses.  The transition to IPv6 will solve this  problem, since IPv6 makes addresses cheap and  plentiful. The smallest possible address allocation for  an IPv6 network is a factor of  four billion times larger than the entire  IPv4 Internet! Unfortunately, the transition to IPv6  has been slow.

1. **IPv4地址空间短缺的变通方法**
   1. ISP无法响应客户需求 - 通过NAT解决，每个客户只分配一个IPv4地址&NAT router使用私有网段
   2. IPv4（稀有，贵）->IPv6（多，便宜）转移也可以解决，但是部署时间慢

---

![](/static/2021-01-22-23-09-43.png)

这表明使用NAT的第二个原因：**在IPv4和IPv6地址之间进行转换**。 在这种模式下，ISP或其他网络运营商在其网络内部运行IPv6，而不支持IPv4。 这为ISP提供了一个干净、现代、面向未来的网络。 

* ISP还运行两套NAT。 **对于想在内部使用IPv4的客户，客户使用私人IPv4网络，当IPv4数据包离开客户的网络时，NAT将其翻译成IPv6数据包**。
  * 其原理与我们在本讲座最后一部分讨论的NAT路由器相同，**只是NAT不是将带有私人IP地址的数据包改写为公共IPv4地址，而是【改写整个IPv4头，并将其替换为IPv6头**】。
  * 当数据包到达ISP网络的边缘，连接到公共互联网时，**如果目的地可以通过IPv6访问，它们会被转发为本地IPv6，或者被另一个NAT翻译为IPv4**。 
* 这种运行网络的方法的期望是，<font color="red">随着时间的推移，需要IPv4的客户和目标网络的数量将减少，更多的流量将运行IPv6端到端。</font>
  * NAT被用来作为一种希望是暂时的变通方法。This suggests the second reason why NAT  is used: to translate between IPv4 and  IPv6 addresses.  In this model, an ISP, or other  network operator, runs IPv6 internally in their  network, and does not support IPv4.  This gives the ISP a clean,  modern, and future-proof network.  The ISP also runs two sets of  NATs.  For customers that want to use IPv4  internally, the customer uses a private IPv4  network, and the NAT translates the IPv4  packets into IPv6 packets when they leave  the customer’s network. The principle is the  same as the NAT routers we discussed  in the last part of this lecture,  except that rather than rewriting packets with  private IP addresses to have public IPv4  addresses, the NAT rewrites the entire IPv4  header and replaces it with an IPv6  header.  When packets get to the edge of  the ISPs network, where it connects to  the public Internet, they’re either forwarded as  native IPv6 if the destination is accessible  via IPv6, or translated to IPv4 by  another NAT.  The expectation in this approach to running  a network is that, over time,  the number of customers and destination networks  that need IPv4 will go down,  and more traffic will run IPv6 end-to-end.  NAT is used as a, hopefully temporary,  workaround.

2. **用于IPv4/6地址的转换**
   1. ISP内部使用v6网段，用户使用v4
   2. 数据包转发时，转换成特定类型地址
      1. 私有IP&port->公共IP&port
      2. v4/6->v4/6
   3. 留下更充足的IPv4空间

---

![](/static/2021-01-22-23-15-39.png)

使用NAT的第三个原因是为了**避免重新编号**。

* 拥有一个**公共IP地址范围的网络，随着时间的推移，往往最终会将该范围的IP地址硬编码到配置文件、应用程序和设置中**。
  * 这是一个错误。**应用程序应该始终使用DNS名称，以允许IP地址的变化，但人们还是这样做了**。
  * **其结果是，很难改变网络上的机器所使用的IP地址。一台主机使用一个特定的IP地址的时间越长，就越有可能在网络上的某个地方有该地址的硬编码，如果该主机的地址发生变化，就会失败**。
* 如果一个网络有一个由ISP委托给它的IP地址范围，也就是所谓的供应商分配的IP地址范围，并且想要更换ISP，那么它将**需要把它使用的IP地址范围改为由新的供应商委托的。许多机构发现这非常困难**，
  * 因此,**在内部保留旧的IP地址，并使用NAT将地址转换为新的ISP分配的范围更容易**。
  * 如果一家公司收购了另一家公司，并且必须将新公司的IT系统整合到其现有的网络中，也会出现类似的问题。
* **与IPv4相比，IPv6有更好的自动配置支持，并试图使重新编号更容易**，但目前还不清楚其效果如何。
  * 因此，一些网络设备供应商已经开始销售能在两个不同的IPv6前缀之间进行转换的NAT，以方便重新编号。
* 在这两种情况下，更好的方法是，<font color="red">一个组织直接从一个区域互联网注册处获得所谓的独立于供应商的IP地址，因此它拥有自己使用的IP地址。 在这种情况下，该组织向其ISP付费，让其将流量导向它所拥有的地址，并可以在不重新编号的情况下转移到一个新的ISP</font>。The third reason to use NAT is  to avoid renumbering.  Networks that have a public IP address  range tend, over time, to end up  hard coding IP addresses from that range  into configuration files, applications, and settings.  This is a mistake. Applications should always  use DNS names, to allow the IP  addresses to change, but people do it  anyway.  The result is that it’s difficult to  change the IP addresses used by machines  on a network. The longer a host  has used a particular IP address,  the more likely it is that something,  somewhere, on the network has that address  hard-coded, and will fail if the host’s  address changes.  If a network has an IP address  range delegated to it from its ISP,  what’s known as a provider allocated IP  address range, and wants to change ISP,  then it will need to change the  IP address range it uses to one  delegated from its new provider. Many organisations  have found this sufficiently difficult that it’s  easier to keep the old IP addresses  internally, and use a NAT to translate  addresses to the range assigned by the  new ISP.  A similar problem can occur if one  company buys another, and has to integrate  the IT systems of the new company  into its existing network.  IPv6 has better auto-configuration support than IPv4,  and tries to make renumbering easier,  but it’s not clear how well this  works. As a result, some network equipment  vendors have started selling NATs that translate  between two different IPv6 prefixes, to ease  renumbering.  In both cases, a better approach is  that an organisation gets what’s known as  provider independent IP addresses, directly from one  of the Regional Internet Registries, so it  owns the IP addresses it uses.  In this case, the organisation pays its  ISP to route traffic to the addresses  it owns, and can move to a  new ISP without renumbering.

1. **避免网络地址更改renumbering**
   1. 很难改变网络中IP，如果硬编码（静态）。如果ISP更变，所有都需要改变
   2. IPv6使地址更改更简单（但还是不知道具体实践如何实现）

# NAT翻译记录基于对TCP连接内数据包的监控：Implications of NAT for TCP Connections

NAT怎么知道要记录何种转换状态？

![](/static/2021-01-22-23-30-25.png)

鉴于这些原因，NAT路由器将被使用，尽管它们有问题，**那么NAT路由器对TCP连接的影响是什么**？ 

* **出站连接在NAT中创建了状态，所以回复可以被翻译，以到达私有网络上的正确主机**。
* 那么，问题是，NAT如何知道要设置什么翻译状态(**转换基于什么**？)？
  * 其工作方式是，**NAT路由器查看它正在翻译和转发的TCP段，并观察代表TCP连接建立握手的数据包**。 
    * 如果NAT看到一个传出的SYN数据包，接着是一个传入的SYN-ACK，然后是一个传出的ACK，具有匹配的序列号和确认号，那么它可以推断出这是一个**TCP连接的开始，并设置适当的翻译**。
    * TCP连接在连接结束时也有类似的交换，有FIN、FIN-ACK和ACK数据包。NAT路由器可以观察到这些交换，并**推断出相应的TCP连接已经结束，并且翻译状态可以被删除**。
* <font color="red">不幸的是，应用程序和主机有时会崩溃，连接在没有发送FIN、FIN-ACK和ACK包的情况下消失</font>。
  * 出于这个原因，**NAT路由器也实现了一个超时**。
    * 如果一个**连接在发送数据包之间等待的时间太长，NAT会认为它已经失败，并删除翻译状态**。
    * IETF的建议是，<font color="red">NAT使用两小时的超时，但测量表明，【许多NAT忽略了这一点，并使用更短的计时器】</font>。
      * 其结果是，长期存在的TCP连接，需要每隔几分钟发送一些东西，否则会被闲置。即使只是一个空的TCP段，以防止路径上的NATs超时并丢弃连接。
      * 如果你曾经用ssh登录到一个远程系统，然后去做别的事情，几个小时后回来，想知道为什么ssh连接失败，这很可能是由于**NAT超时**造成的。
* 另一个问题，是**NAT不会对传入的连接有状态（内网穿透问题，外网主动请求某端口，某端口不会有之前相应的翻译记录。影响P2P和NAT后服务器），除非手动配置成这样（过于复杂**）。 这使得在NAT后面运行一个服务器或点对点应用程序变得很困难。Given these reasons why NAT routers will  be used, despite their problems, what are  the implications for NAT routers on TCP  connections?  Well, as I’ve explained, outgoing connections create  state in the NAT, so replies can  be translated to reach the correct host  on the private network. The question is,  then, how does the NAT know what  translation state to setup?  The way this works is that the  NAT router looks at TCP segments it’s  translating and forwarding, and watches for packets  representing a TCP connection establishment handshake.  If the NAT sees an outgoing SYN  packet, followed by an incoming SYN-ACK,  then an outgoing ACK, with matching sequence  and acknowledgment numbers, then it can infer  that this is the start of a TCP connection, and setup the appropriate translation.  TCP connections have a similar exchange at  the end of the connection, with FIN,  FIN-ACK, and ACK packets. The NAT router  can watch for these exchanges, and infer  that the corresponding TCP connections have finished,  and that the translation state can be  removed.  Unfortunately, applications and hosts sometimes crash,  and connections disappear without sending the FIN,  FIN-ACK, and ACK packets. For this reason,  NAT routers also implement a timeout.  If a connection waits too long between  sending packets, the NAT will assume it’s  failed, and remove the translation state.  The recommendation from the IETF is that  NATs use a two hour timeout,  but measurements have shown that many NATs  ignore this and use a shorter timer.  The result is that long-lived TCP connections,  that would otherwise go idle, need to  send something, even if just an empty  TCP segment, every few minutes, to prevent  NATs on the path from timing out  and dropping the connection.  If you’ve ever used ssh to login  to a remote system, gone to do  something else, then come back after a  couple of hours and wondered why the  ssh connection has failed, this may well  be due to NAT timeout.  The other issue, as I mentioned at  the start of this part, is that  the NAT won’t have state for incoming  connections, unless manually configured to do so.  This makes it difficult to run a  server or peer-to-peer application behind the NAT.

:orange:针对 host behind NAT（主动请求），NAT也支持反向转换

* NAT会查看TCP数据段
  * NAT监控TCP开始标志，如果出现SYN--SYN-ACK--ACK，代表TCP连接建立，需要开始建立正确的转换
  * NAT监控TCP完成标志（FIN--FIN-ACK-ACK），如果TCP连接关闭，转换状态记录可以被移除
  * <font color="deeppink">因为有时候app崩溃，不会发送FIN包，NAT路由还实现了超时timeout机制</font>，如果连接-发送用时过长，NAT视为失败，直接移除状态记录

:orange: 针对incoming connection（之前无状态记录的被动新请求），NAT无处理机制

* NAT不知道该转发至哪里，<font color="blue">必须手动配置</font>
* 使P2P & server behind NAT复杂
  * TCP-P2P连接只能匹配正确流量，“更严格”

# NAT对UDP连接的影响：Implications of NAT for UDP Flows

![](/static/2021-01-22-23-39-57.png)

NAT对UDP流的影响与对TCP的**影响相似**，只是**UDP缺乏连接使事情变得复杂**。

* **对于TCP，NAT可以观察到连接的建立和拆除部分，并知道TCP连接何时开始和结束** --- 有利于建立/删除翻译记录状态
  * TCP连接可以在不发送FIN、FIN-ACK、ACK交换的情况下失败，但这种情况很少，**NAT路由器一般依靠观察TCP连接建立和拆除消息来管理翻译状态**。
* **另一方面，UDP没有连接建立，因为它没有连接的概念**。
  * <font color="red">当涉及到在NAT中【建立状态时，这不是一个大问题</font>】。
    * **如果NAT看到任何具有特定地址和端口的出站UDP数据包，它就会在NAT中设置状态以允许回复**。
* 问题是要知道**何时在NAT中【删除】该翻译状态**。 由于UDP没有 "连接结束 "的消息，唯一的方法是用**超时**来完成。
  * 历史上，使用最广泛的UDP应用是DNS。DNS客户倾向于联系许多不同的服务器，但只与每个服务器交换少量的数据。
  * **许多NAT对UDP翻译状态有很短的超时--几十秒的时间，以防止他们为太多的UDP流积累状态**。
  * 这种情况的一个不幸后果是，**使用UDP的应用，如视频会议和游戏，必须频繁地在两个方向上发送数据包，以确保NAT的绑定保持开放**。
    * IETF建议此类应用<font color="red">至少每15秒发送和接收一次，这可能会产生不必要的流量</font>。

:orange: 不过，有一个**好处**，那就是**UDP中缺乏连接建立信号**。

* 通过TCP，NAT可以看到SYN、SYN-ACK、ACK交换，并知道客户和服务器正在使用的确切地址和端口。 **这允许NAT创建一个非常具体的绑定，并拒绝来自其他地址的流量**。
  * 这些非常具体的绑定是一个**安全**的好处，**但使点对点的连接更难建立**。
* **UDP应用在接受数据包方面往往更加灵活，因此NAT通常建立绑定，允许任何到达正确端口的UDP数据包被翻译并转发到NAT。这使得点对点连接的建立对NAT来说要容易得多**，我们将在下一部分看到这一点。The implications of NAT for UDP flows  are similar to those for TCP,  except that the lack of connections with  UDP complicates things.  For TCP, a NAT can watch for  the connection establishment and teardown segments,  and know when the TCP connections start  and finish. TCP connections can fail without  sending the FIN, FIN-ACK, ACK exchange,  but this is rare, and NAT routers  generally rely on watching the TCP connection  setup and teardown messages to manage translation  state.  UDP, on the other hand, has no  connection establishment, since it has no concept  of connections.  This is not a great problem when  it comes to establishing state in a  NAT. If the NAT sees any outgoing  UDP packet with a particular address and  port, it sets up the state in  the NAT to allow replies.  The problem comes with knowing when to  remove that translation state in the NAT.  Since UDP has no “end of connection”  message, the only way to do this  is with a timeout.  The most widely used UDP application,  historically, has been DNS. DNS clients tend  to contact a lot of different servers,  but exchange only a small amount of  data with each. As a result,  many NATs have very short timeouts -  on the order of tens of seconds  - for UDP translation state, to prevent  them accumulating state for too many UDP  flows.  An unfortunate consequence of this, is that  applications that use UDP, such as video  conferencing and gaming, must send packets frequently,  in both directions, to make sure the  NAT bindings stay open. The IETF recommends  that such applications send and receive something  at least once every 15 seconds.  This can generate unnecessary traffic.  There is one benefit, though, that comes  from the lack of connection establishment signalling  in UDP. With TCP, the NAT can  see the SYN, SYN-ACK, ACK exchange,  and knows the exact addresses and ports  that the client and server are using.  This allows the NAT to create a  very specific binding, and reject traffic from  other addresses.  These very specific bindings are a security  benefit, but make peer-to-peer connections harder to  establish. UDP applications tend to be more  flexible in where they accept packets from,  so NATs generally establish bindings that allow  any UDP packets that arrive on the  correct port to be translated and forwarded  across the NAT. This makes peer to  peer connection establishment much easier for NAT,  as we’ll see in the next part.

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