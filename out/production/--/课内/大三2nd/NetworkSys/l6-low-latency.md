# Content

* [Content](#content)
* [Outline: 数据传输时延 Lowering Latency](#outline-数据传输时延-lowering-latency)
  * [TCP拥塞控制介绍：TCP Congestion Control](#tcp拥塞控制介绍tcp-congestion-control)
  * [QUIC拥塞控制（类似TCP）：Congestion Control](#quic拥塞控制类似tcpcongestion-control)
  * [拥塞控制原则：Congestion Control Principles](#拥塞控制原则congestion-control-principles)
  * [原则1-TCP丢包->拥塞信号](#原则1-tcp丢包-拥塞信号)
  * [路由器 & 丢包拥塞控制信号：Key principles- packet loss as a congestion signal](#路由器--丢包拥塞控制信号key-principles--packet-loss-as-a-congestion-signal)
  * [原则2-包数量守恒&ACK时钟（拥塞，等待ACK时间更长） Clocking：Key Principles-Conservation of Packets](#原则2-包数量守恒ack时钟拥塞等待ack时间更长-clockingkey-principles-conservation-of-packets)
  * [原则3-和式增加，积式减少->保证网络稳定:AIMD Algorithms](#原则3-和式增加积式减少-保证网络稳定aimd-algorithms)
* [Outline: TCP Reno](#outline-tcp-reno)
  * [TCP Reno算法（遵循AIMD原则，慢开始-拥塞避免增强功能）：Congestion Control](#tcp-reno算法遵循aimd原则慢开始-拥塞避免增强功能congestion-control)
  * [TCP目标：多个流共享等额链路带宽](#tcp目标多个流共享等额链路带宽)
  * [1包1ACK（stop-wait）->滑动窗口协议-多包1ACK：From Stop-and-Wait协议 to Sliding Window Protocols](#1包1ackstop-wait-滑动窗口协议-多包1ackfrom-stop-and-wait协议-to-sliding-window-protocols)
    * [滑动窗口协议提升链路利用率](#滑动窗口协议提升链路利用率)
  * [滑动窗口协议-涉及窗口大小问题：Sliding Window Protocols Improve Link Utilisation](#滑动窗口协议-涉及窗口大小问题sliding-window-protocols-improve-link-utilisation)
    * [问题-滑动窗口大小应为多少](#问题-滑动窗口大小应为多少)
  * [Reno需要进行的优化（针对滑动窗口协议）：TCP Reno Congestion Control](#reno需要进行的优化针对滑动窗口协议tcp-reno-congestion-control)
  * [Reno传统/现代拥塞窗口大小：Choosing the initial window](#reno传统现代拥塞窗口大小choosing-the-initial-window)
  * [Reno-选定初始窗口后，需要长期调整窗口的机制（慢开始&拥塞避免）: Finding the Path Capacity](#reno-选定初始窗口后需要长期调整窗口的机制慢开始拥塞避免-finding-the-path-capacity)
    * [慢开始：TCP Reno - Slow Start](#慢开始tcp-reno---slow-start)
    * [拥塞避免-开始长期调整：Congestion Avoidance](#拥塞避免-开始长期调整congestion-avoidance)
    * [拥塞避免-3个冗余ACK【丢包】：快重传 & 快恢复](#拥塞避免-3个冗余ack丢包快重传--快恢复)
    * [拥塞避免-超时机制-重新进入慢开始：TCP Tahoe版本](#拥塞避免-超时机制-重新进入慢开始tcp-tahoe版本)
  * [Reno算法对收方影响 & 如何使网络稳定：Congestion Window Growth, Buffering, Throughput](#reno算法对收方影响--如何使网络稳定congestion-window-growth-buffering-throughput)
  * [Reno局限性: Discussion](#reno局限性-discussion)
* [Outline: TCP Cubic - 快速长距离网络](#outline-tcp-cubic---快速长距离网络)
  * [Reno长距离网络局限性 & Cubic改进：TCP Performance on Fast Long-distance Networks](#reno长距离网络局限性--cubic改进tcp-performance-on-fast-long-distance-networks)
  * [概述：TCP Cubic 拥塞避免阶段的改变](#概述tcp-cubic-拥塞避免阶段的改变)
  * [TCP Cubic控制拥塞窗口细节： Congestion Control](#tcp-cubic控制拥塞窗口细节-congestion-control)
  * [TCP Cubic vs Reno - 时延换吞吐量](#tcp-cubic-vs-reno---时延换吞吐量)
* [Outline：Delay-based Congestion Control](#outlinedelay-based-congestion-control)
  * [Reno/Cubic拥塞控制对时延的影响：Impact of TCP on Latency](#renocubic拥塞控制对时延的影响impact-of-tcp-on-latency)
  * [TCP Vegas降低延迟概述: Reducing Latency](#tcp-vegas降低延迟概述-reducing-latency)
  * [TCP Vegas拥塞控制细节： Congestion Control](#tcp-vegas拥塞控制细节-congestion-control)
  * [Vegas优点](#vegas优点)
  * [Vegas局限性：Limitations](#vegas局限性limitations)
  * [TCP BBR](#tcp-bbr)
* [Outline: Explicit Congestion Notification](#outline-explicit-congestion-notification)
  * [原TCP拥塞控制的不足 & 为何引入显式拥塞通知：Explicit Congestion Notification](#原tcp拥塞控制的不足--为何引入显式拥塞通知explicit-congestion-notification)
  * [ECN bit and IP](#ecn-bit-and-ip)
  * [ECN and TCP](#ecn-and-tcp)
    * [拥塞经历标记-ECN Congestion Experienced mark：ECN-CE](#拥塞经历标记-ecn-congestion-experienced-markecn-ce)
    * [CWR：拥塞窗口减少位](#cwr拥塞窗口减少位)
  * [ECN特性](#ecn特性)
  * [ECN部署问题&发展：ECN & TCP - Deployment](#ecn部署问题发展ecn--tcp---deployment)
* [Outline:Light Speed](#outlinelight-speed)
  * [传播（介质）时延：Light Speed？](#传播介质时延light-speed)
  * [减少传播时延：Reducing Propagation Delay](#减少传播时延reducing-propagation-delay)
    * [物理减少](#物理减少)
    * [选取的介质对传播时延的影响](#选取的介质对传播时延的影响)
  * [为什么致力于物理层面降低时延？](#为什么致力于物理层面降低时延)

# Outline: 数据传输时延 Lowering Latency

降低通信时延的协议

![](/static/2021-04-06-16-09-53.png)

:orange: 时延 - 影响效率的关键因素 key factor limiting performance is latency

* **连接建立时延** connection establishment latency
  * 减少RTT数量加快连接建立时间 reduce number of RTT to improve connection setup time
  * 减少需要的连接数量 reduce number of connections needed
  * TCP + TLS 1.3 -> QUIC(用于改进连接建立时延)
    * 连接建立过程中进行握手协商 Overlapping connection establishment and security handshake
    * 0-RTT模式复用TCP连接 0-RTT connection re-establishment with idempotent data
    * 单一连接内的流复用 Stream multiplexing within a single connection
* **数据传输时延** Data transfer latency
  * **你如何在网络上传递数据，不会导致过大的延迟，如何逐步找到降低延迟的方法，让网络更好地适应实时应用**，比如说电话，还有视频会议，还有游戏，还有高频交易，还有物联网，还有控制应用。How you deliver data across the network  in a way which doesn't lead to  excessive delays, and how you can gradually  find ways of reducing the latency,  and making the network better suited to  real time applications, such as telephony,  and video conferencing, and gaming, and high  frequency trading, and  Internet of Things, and control applications.
  * 主要：如何去<font color="red">建立拥塞控制</font>，how you go about building  congestion control
    * 很多重点是要讲TCP拥塞控制是如何工作的 and a lot of the  focus in this lecture is going to  be on how TCP  congestion control works
    * 以及其他协议如何做拥塞，以低延迟的方式来传递数据 how other protocols  do congestion, to deliver data in a  low latency manner
    * Impact of TCP congestion control
    * Impact of delay-based congestion control
    * Impact of explicit congestion notification
    * Impact of the speed of light

## TCP拥塞控制介绍：TCP Congestion Control

拥堵控制的一些原则。并谈一谈要解决的问题是什么，我们如何去**调整TCP连接在网络上传送数据的速度，以最佳地利用网络容量，并以不在网络中建立队列和引起过多延迟**。some of  the principles of congestion  control. And talk about what is the  problem that's being solved, and how can  we go about adapting the rate at  which a TCP connection delivers data over  the network  to make best use of the network  capacity, and to do so in a  way which doesn't build up queues in  the network and induce too much latency.

![](/static/2021-04-06-16-27-54.png)

* **TCP拥塞控制原则** TCP congestion control principles
* **基于丢包的拥塞控制** loss-based congestion control
  * **非常有效利用整个网络容量的方法** ways of making very effective  use of the overall network capacity
    * TCP Reno
    * TCP Cubic
* **基于(降低)延迟的拥塞控制** delay-based congestion control
  * TCP Vegas
  * TCP BBR
* **显式拥塞通知** Explicit Congestion Notification

:orange:**TCP是一个复杂且高度优化的协议，特别是涉及到拥塞控制和损失恢复机制时**，TCP is a  complex and very highly optimised protocol,  especially when it comes to congestion control  and loss recovery mechanisms.

* 本课只介绍拥塞控制的工作方式，但请注意，这只是对一些相当复杂的问题的简化回顾 the way congestion control  works in this lecture, but be aware  that this is a very simplified review  of some quite complex issues

## QUIC拥塞控制（类似TCP）：Congestion Control

![](/static/2021-04-06-16-43-20.png)

QUIC本质上采用了和TCP一样的拥塞控制机制。QUIC essentially adopts the same  congestion control mechanisms as TCP.

* QUIC 1 标准使用TCP Reno，使用和TCP Reno一样的拥塞控制算法。The QUIC v1 standard adopts congestion control as used in
TCP Reno
* 而在实际应用中，大部分QUIC的实现都是使用Cubic或BBR拥塞控制算法 in practice, most of the QUIC  implementations use the Cubic or the BBR  congestion control algorithms

## 拥塞控制原则：Congestion Control Principles

![](/static/2021-04-06-17-22-53.png)

拥堵控制的概念是**为一个连接找到合适的传输速率**。the idea of congestion control is  to find the right transmission rate for  a connection

* **我们试图找到最快的发送速率，以匹配网络的容量，【并以不建立队列，不超载，不使网络拥堵】的方式进行数据传输** find the fastest sending  rate which you can send at to  match the capacity of the network,  and to do so in a way  that doesn't build up queues, doesn't overload,  doesn't congest the network.
* **所以，我们希望调整网络上TCP流量的传输速率，以匹配可用的网络容量**。adapt the transmission  rate of a flow of TCP traffic  over the network, to match the available  network capacity.
  * 当网络容量发生变化时，也许是因为其他流量的启动，也许是因为你在移动设备上，你移动到了一个无线电覆盖范围不同的区域，**TCP传输数据的速度应该适应变化和可用容量** as the network capacity changes,  perhaps because other flows of traffic start  up, or perhaps because you're on a  mobile device and you move into an  area with different radio coverage,  the speed at which the TCP is  delivering the data should adapt to match  the changes and available capacity.

## 原则1-TCP丢包->拥塞信号

:orange: TCP 拥塞控制核心原则 TCP congestion control key principles

* 【TCP将丢包作为拥塞信号，响应 TCP responds  to packet loss as a congestion signal】因为互联网是一个尽力的分组网络，会丢包，如果不能送出的话，**TCP把这个丢包，作为一个拥塞信号，作为它发送太快的信号，应该放慢速度** TCP treats that loss  of a packet, as a congestion signal,  and as a signal of it's sending  too fast and should slow down.
* 它依靠的是数据包的守恒原则。它试图保持穿越网络的**数据包数量大致不变**，假设网络中没有任何变化 It relies on the principle of conservation  of packets. It tries to keep the  number of packets, which are traversing the  network roughly constant,  assuming nothing changes in the network.
* **而它依靠的是和式增加，积式减少**的原则。And it relies on the principles of  additive increase, multiplicative decrease.
  * 如果要提高它的发送速度，它的速度相对较慢，是一个增量。If it has to increase its sending  rate, it does so relatively slowly,  an additive increase in the rate.
  * 而如果要降低它的发送速度，它也会很快降低，倍数降低。And if it has to reduce its  sending rate, it does so quickly,  a multiplicative decrease.

## 路由器 & 丢包拥塞控制信号：Key principles- packet loss as a congestion signal

TCP丢包作为网络拥塞的信号 packet loss is an indication that the network is congested

![](/static/2021-04-07-15-02-25.png)

在互联网中的数据流通过一系列**路由器**从发送方流向接收方。IP路由器将构成网络的不同链路连接在一起。Data flying across the Internet flows from  the sender to the receiver through a  series of routers. The IP routers connect  together the different links that comprise the  network.

* 而路由器有两个功能：一是执行**路由功能**，二是执行**转发功能**。And routers perform two functions:  they perform a routing function, and a  forwarding function.
  * **路由功能的目的是弄清楚数据包应该如何到达目的地。它们从某条网络链路上接收到一个数据包，查看目的地IP地址，并决定向哪个方向转发该数据包。它们负责在网络中寻找正确的路径**。The purpose of the routing function is  to figure out how packets should get  to their destination. They receive a packet  from some network link, look at the  destination IP address, and decide which direction  to forward that packet. They’re responsible for  finding the right path through the network.
  * 但他们还负责**转发，实际上就是把数据包放到链路的出站流量队列中，并管理这个数据包队列，以实际在网络中传输数据包**。But they're also responsible for forwarding,  which is actually putting the packets into  the queue of outgoing traffic for the  link, and managing that queue of packets  to actually transmit the packets across the  network.

:orange: 而网络中的路由器有不同的链路，路由器的全部意义就是连接不同的链路。**而在每一条链路上，它们都有一个数据包队列，这些数据包被排队在该链路上传递** And routers in the network have a  set of different links; the whole point  of a router is to connect different  links. And at each link, they have  a queue of packets, which are enqueued  to be delivered on that link.

* **如果数据包到达的速度超过了链路传递这些数据包的速度，那么队列就会逐渐建立起来**。if packets are arriving  faster than the link can deliver those  packets, then the queue gradually builds up.
  * 越来越多的数据包在路由器中被enqueued，等待着被交付。More and more packets get enqueued in  the router waiting to be delivered.
* **而如果数据包到达的速度比链路可以转发的速度慢，那么随着数据包的传输，链路上队列会逐渐清空**。And if packets are arriving slower than  they can be forwarded,  then the queue gradually empties as the  packets get transmitted.
* <font color="deeppink">显然，路由器的内存是有限的，在某些时候，它会用完空间（链路容量）并且队列数据包。所以，如果数据包的传递速度比它们快，【如果数据包到达路由器的速度比它们在链路上传递的速度快，队列就会建立起来，并逐渐被填满，直到达到路由器的最大大小。这时，路由器就没有空间来保存新到达的数据包，于是它就会丢弃这些数据包】。</font> Obviously the router has a limited amount  of memory, and at some point it's  going to run out of space to  enqueue packets. So, if packets are being  delivered faster than they,  if packets arriving at the router faster  than they can be delivered down the  link, the queue will build up and  gradually fill, until it reaches its maximum  size. At that point, the router has  no space to keep the newly arrived  packets, and so it discards the packets.
  * Queues have maximum size → packets discarded if the queue is full
  * 这就是 TCP 所使用的拥塞信号。它利用了这样一个事实，<font color="deeppink">即路由器上传出链路上的数据包队列已经被填满</font>。**它将此作为【队列填满、数据包丢失】的指示，它将数据包丢失作为发送速度过快的指示。它发送的速度超过了数据包的传送速度，结果是队列已经溢出，数据包已经丢失，因此需要减慢速度** And this is what TCP is using  as the congestion signal. It’s using the  fact that the queue of packets on  an outgoing link at a router has  filled up. It's using that as an  indication that  the queue fills up, the packet gets  lost, it uses that packet loss as  an indication that it's sending too fast.  It’s sending faster than the packets can  be delivered, and as a result the  queue has overflowed, a packet has been  lost, and so it needs to slow  down.
  * **而如果它们慢下来，队列就会逐渐清空，数据包就不会再丢失了** And if  they slow down, the queues will gradually  empty, and packets will stop being lost.

## 原则2-包数量守恒&ACK时钟（拥塞，等待ACK时间更长） Clocking：Key Principles-Conservation of Packets

TCP为数据包发送确认。当一个数据包被传送时，它有一个序列号，接收方将回传响应，确认收到该序列号 TCP sends acknowledgments for packets. When a  packet is transmitted it has a sequence  number, and the response will come back  from the receiver acknowledging receipt of that  sequence number.

* TCP的一般做法是，一旦连接开始了，每次收到确认，就把它作为一个信号，表示已经收到了一个数据包。The general approach for TCP, once the  connection has got going, is that every  time it gets an acknowledgement, it uses  that as a signal that a packet  has been received.
* 而如果一个数据包已经被接收，则说明有东西已经离开了网络。发送到网络中的一个数据包已经到达了另一边，在接收端已经从网络中删除。 这意味着应该有空间把另一个数据包放到网络中去。 **而这是一种叫做ACK时钟的方法。每当一个数据包到达接收端，你收到一个确认回执说已经收到了，这就说明你可以再放一个数据包进去。 所以数据包的总数和在网络上的传输量最终是大致不变的。一个数据包出来，你再放一个数据包进去**。And if a packet has been received,  something has left the network. One of  the packets sent into the network has  reached the other side, and has been  removed from the network at the receiver.  That means there should be space to  put another packet into the network.  And it's an approach that’s called ACK  clocking. **Every time a packet arrives at  the receiver, and you get an acknowledgement  back saying it was received, that indicates  you can put another packet in.  So the total number of packets and  transit across the network ends up being  roughly constant. One packet out, you put  another packet in.**
  * 它的好处是，如果你的时钟了新的数据包在收到确认，如果，由于某种原因，网络变得拥挤，它需要更长的时间等待ACK回来，那么，【**这将自动减慢你发送的速度**】。**因为下一个ACK回来需要更长的时间，因此，在你发送下一个数据包之前，需要更长的时间**。 And it has the advantage that if  you're clocking out new packets in receipt  of acknowledgments, if, for some reason,  the network gets congested, and it takes  longer for acknowledgments to come back,  because it's taking longer for them to  work their way across the network,  then, that will automatically slow down the  rate at which you send. **Because it  takes longer for the next acknowledgment to  come back, therefore it's longer before you  send your next packet.**

![](/static/2021-04-07-19-02-07.png)

:orange: 所以，**当网络开始拥塞，当队列开始建立时，但在【队列还没有溢出之前（无丢包）】，ACK back需要更长的时间，因为数据包在中间链路中排队**，这逐渐减慢了TCP的行为。 **降低了数据传输速率** So, as the network starts to get  busy, as the queue starts to build  up, but before the queue has overflowed,  it takes longer for the acknowledgments to  come back, because the packets are queued  up in the intermediate links, and that  gradually slows down the behaviour of TCP.  It reduces the rate at which you  can send.

* **所以，至少在某种程度上，它是自我调整的。【网络越忙，ACK回来的速度越慢，因此你发送的速度就会慢一点**】。So it’s, to at least some extent,  self adjusting. The network gets busier,  the ACKs come back slower, therefore you  send a little bit slower.
* Automatically reduces sending rate if network gets congested and 
delivers packets more slowly

## 原则3-和式增加，积式减少->保证网络稳定:AIMD Algorithms

![](/static/2021-04-07-19-27-24.png)

:orange: 数据包守恒的原则 - 前提：网络处于稳定状态（没丢包，ACK回来的有点慢
）,还需要能够适应发送的速度 And the principle of conservation of packets  is great, provided the network is in  the steady state.also need to be able  to adapt the rate at which you're  sending

:orange: 核心原则3 - 和式增加，积式减少

* TCP的适应网络容量方式，->慢开始，逐渐增加（窗口大小）
  * 当它需要增加它的发送速率时，TCP会线性增加（**和式增加**）。它每次往返都会增加少量的发送速率。 所以它就会逐渐的，慢慢的增加发送等级。**它逐渐推高速率，。直到丢包（直到它一个队列溢出**） When it needs to increase it’s sending  rate, TCP increases linearly. It adds a  small amounts to the sending rate each  round trip time.  So it just gradually, slowly, increases the  sending rating. It gradually  pushes up the rate  until it spots a loss. Until it  loses a packet. Until it overflows a  queue.
  * 然后它通过快速降低速率来应对拥堵。**如果发生拥堵事件（丢包），TCP就会把发送速率减半。降低的速度比增加的速度快** And then it responds to congestion by  rapidly decreasing its rate. If a congestion  event happens, if a packet is lost,  TCP halves its rate. It responds faster  than it increases, it slows down faster  than it increases.
* AIMD目的：<font color="deeppink">保持网络稳定，使网络不过载</font> And this is the final principle,  what’s known as additive increase, multiplicative decrease.  The goal is to keep the network  stable. The goal is to not overload  the network.
  * 如适用，按照ACK时钟的方法，保持稳定的速度。keep going at a  steady rate. Follow the ACK clocking approach.
  * **慢开始**（万一容量比想象的大/小）Gradually, just slowly, increase the rate a  bit. Keep pushing, just in case there’s  more capacity than you think. So just  gradually keep probing to increase the rate.
  * 如果链路队列溢出（网络拥塞），造成数据包丢失，**迅速放慢速度**。将你的发送速率减半，再逐渐加大 If you overload the network, if you  cause congestion, if you overflow the queues,  cause a packet to be lost,  slow down rapidly. Halve your sending rate,  and gradually build up again.

:candy: 事实上，<font color="deeppink">放慢的速度比加快的速度要快，你遵循数据包一进一出的方式（数据包守恒，ACK始终），可以保持网络的稳定</font>。The fact that you slow down faster  than you speed up, the fact that  you follow the one in, one out  approach,  keeps the network stable

* **AIMD确保了网络不会过载，也意味着如果网络真的过载了，它能快速响应并恢复，目标是保持流量的移动（网络稳定**）. It makes sure  it doesn't overload the network, and it  means that if the network does overload,  it responds and recovers quickly The goal  is to keep the traffic moving.

# Outline: TCP Reno

Reno是TCP采用的默认拥塞控制算法 (基于滑动窗口协议进行改进)

* 基本拥塞控制算法 Basic TCP congestion control
* 滑动窗口算法原理 sliding window algorithms
  * 如何改变发送速率 how to adapt the sending rate
* 慢开始 slow start
* 拥塞避免 congestion avoidance

## TCP Reno算法（遵循AIMD原则，慢开始-拥塞避免增强功能）：Congestion Control

![](/static/2021-04-07-22-50-19.png)

TCP维护滑动窗口 TCP uses window-based congestion control, maintains a sliding window onto the available data

* 而**滑动窗口**决定了TCP（根据AIMD算法）**可以发送到网络上的序列号的范围（数据大小**） And the sliding window determines what range  of sequence numbers can be sent by  TCP onto the network according to the AIMD algorithm.
* <font color="red">它采用和式增加积式减少的方式(AIMD原则)来增长和缩小窗口。 而这就决定了，在任何时候，TCP发送方能向网络发送多少数据</font> It uses the additive increase multiplicative decrease  approach to grow and shrink the window.  And that determines, at any point,  how much data TCP sender can send  onto the network.
  * **它用称为【慢速启动和拥塞避免的算法】来【增强】这些功能**。It augments these with algorithms known as  slow start and congestion avoidance.
  * **慢速启动**是TCP用来让连接以安全的方式进行的方法， Slow start  being the approach TCP uses to get  a connection going in a safe way
  * 而**拥塞避免**则是在流量启动后用来维持发送速率的方法 congestion avoidance being the approach it  uses to maintain the sending rate once  the flow has got started.

## TCP目标：多个流共享等额链路带宽

:orange: **TCP基本目标：同链路TCP流共享（几乎）等额带宽** The fundamental goal of TCP is that  if you have several TCP flows sharing  a link, sharing a bottleneck link in  the network,  each of those flows should get an  approximately equal share of the bandwidth.

* TCP的基本目标是，如果你有几个TCP流共享一条链路，共享网络中的一条瓶颈链路，这些流中的每一个都应该得到大约相等的带宽份额 
* 所以，如果你有四个TCP流共享一条链路，它们应该各自获得该链路容量的大约四分之一。 而TCP在这方面做得相当好。So, if you have four TCP flows  sharing a link, they should each get  approximately one quarter of the capacity of  that link.  And TCP does this reasonably well
  * 它并不完美。在某种程度上，它对**长距离流量**有偏见，*短距离流量往往比长距离流量更胜一筹*。 但是，总的来说，它运行得很好，而且确实给了流量大致相等的带宽份额。 .  It’s not perfect. It, to some extent,  biases against long distance flows,  and shorter flows tend to win out  a little over long distance flows.  But, in general, it works pretty well,  and does give flows roughly  equal share of the bandwidth.

:orange: Reno是应用最广泛且较为成熟的算法。**该算法所包含的慢启动、拥塞避免和快速重传、快速恢复机制**，是现有的众多算法的基础 The basic algorithm it uses to do  this, the basic congestion control algorithm,  is an approach known as TCP Reno.

* And this is the state of the  art in TCP as of about 1990.

## 1包1ACK（stop-wait）->滑动窗口协议-多包1ACK：From Stop-and-Wait协议 to Sliding Window Protocols

TCP is an ACK based protocol.

* 发送数据包，一段时间后，ACK响应指明数据包到达了，并指出了下一个预期的数据包的序列号 You send a packet, and sometime later  an acknowledgement comes back telling you that  the packet arrived, and indicating the sequence  number of the next packet which is  expected.
* <font color="deeppink">问题是，这种模式【stop and wait】往往表现得非常糟糕。 它需要一定的时间来发送一个数据包。 这取决于数据包的大小， 和链路带宽</font> The problem with that, is that it  tends to perform very poorly.  It takes a certain amount of time  to send a packet down a link.  That depends on the size of the  packet, and the link bandwidth.

![](/static/2021-04-08-11-12-34.png)

:orange: 数据包的大小用一些要发送的位数来表示 The size of the packet is expressed  some number of bits to be sent.

* 发送每个数据包所需秒数（时间）time to send a packet `ts= packet size/ link bandwidth`
* ACK响应时间 - `tRTT` s later (acknowledgment come back to you, depending on  the **round trip of the link**)

### 滑动窗口协议提升链路利用率

:orange: 链路利用率 & **滑动窗口协议提升链路利用率** link utilisation

* `U=ts（发送数据包所需时间）/ tRTT`
* 希望这个分数接近于1。要把**大部分时间花在发送数据包上，而不是花太多时间等待确认回来后再发送下一个数据包** And, ideally, you want that fraction be  close to one. You want to be  spending most of the time sending packets,  and not much time waiting for the  acknowledgments to come back before you can  send the next packet.
* **然而通常链路利用率很低，大部分时间链路都空闲** --- 滑动窗口协议的思想不仅仅是发送一个数据包并等待确认。<font color="deeppink">是发送几个数据包，并等待确认。窗口是在确认返回之前可以处理的数据包数量</font> The idea of a sliding window protocol  is to not just send one packets  and wait for an acknowledgement. It’s to  send several packets,  and wait for the acknowledgments. And the  window is the number of packets that  can be outstanding before the acknowledgement comes  back.
  * <font color="red">可以发送几个数据包，再等待ACK，触发下个数据包被时钟输出，提高链路利用率</font> The ideas is, you can start several  packets going, and eventually the acknowledgement comes  back, and that starts triggering the next  packets to be clocked out. This idea  is to improve the utilisation by sending  more than one packet before you get  an acknowledgment.
  * 即，Sliding window protocols improve on stop-and-wait by sending more than one packet before stopping for acknowledgement

## 滑动窗口协议-涉及窗口大小问题：Sliding Window Protocols Improve Link Utilisation

这是**滑动窗口协议**的基本方法。And this is the fundamental approach to  sliding window protocols.

* **发送方开始发送数据包，有一个【拥塞窗口】，指定在收到ACK之前允许发送多少个数据包** The sender starts sending  data packets, and there's what's known as  a congestion window that's that specifies how  many packets that's it’s allowed to send  before it gets an acknowledgement.

![](/static/2021-04-08-11-40-19.png)

滑动窗口协议通过拥塞窗口，提高链路利用率

:orange: 例子，congestion window=6

* 而在这种情况下，我们将窗口大小设置为六个数据包。 **恰好第一个数据包的ACK回到了发送方，就在发送方发送完第六个数据包的时候。  这就触发了窗口的滑动**。 所以我们现在不允许发送1到6个数据包，而是允许发送2到7个数据包。因为一个数据包已经到达了，这就打开了窗口，允许我们再发送一个数据包 And in this case we've set the  window size to be six packets.  And it just so happens that the  acknowledgement for the first packet arrives back  at the sender, just as it has  finished sending packet six.  And that triggers the window to increase.  That triggers the window to slide along.  So instead of being allowed to send  packets one through six,  we're now allowed to send packets two  through seven. Because one packet has arrived,  that's opened up the window to allow  us to send one more packet.  And the acknowledgement indicates that packet one  has arrived. So just as we'd run  out of packets to send, just as  we've sent our six packets which are  allowed by the window, the acknowledgement arrives,  slides the window a long one,  tells us we can now send one  more.
* 每次ACK都会释放下一个数据包进行传输，如果你的窗口大小合适的话 And each acknowledgement releases the next packet  for transmission, if you get the window  sized right.

:orange: **如果有问题，如果因为丢包（队列溢出）导致ACK没有回来，那么它就会停止**。And if there's a problem, if the  acknowledgments don't come back because something got  lost,  then it stalls.

* 你没有发送过多的信息包，你不仅仅是在没有得到确认的情况下继续发送，你只是发送了足够多的确认信息，以便确认信息能够返回，就像你没有东西可以发送一样。一切都保持着平衡。每个确认都会触发下一个要发送的数据包，它就会继续运行 You hadn't sent too  many excess packets, you're not just keeping  sending without getting acknowledgments, you're just sending  enough  that the acknowledgments come back, just as  you run out of things to send.  And everything just keeps it sort-of balanced.  Every acknowledgement triggers the next packet to  be sent, and it rolls along.

### 问题-滑动窗口大小应为多少

:orange: 窗口大小应为多少？How big should the window be? (what is the optimal size for the window)

* `bandwidth * delay`
* **问题是，发送方不知道路径的带宽，也不知道这个延迟**。
  * 它不知道往返时间。 它可以测量往返时间，但要等到它开始发送之后。 **一旦它发送了一个数据包，它可以等待一个确认回来，得到一个往返时间的估计**。但是在它开始发送的时候，它不能这样做
  * <font color="deeppink">这就是滑动窗口算法的问题。如果你得到了正确的窗口大小， 允许ACK时钟同步， 允许在正确的时间时钟出来数据包， 刚好赶上下一个数据包可用。 【但是，为了选择正确的窗口大小，需要知道带宽和延迟，而在连接开始时，你不知道这些】</font>

## Reno需要进行的优化（针对滑动窗口协议）：TCP Reno Congestion Control

TCP采用滑动窗口协议（但存在不足） TCP follows the sliding window approach.

* **TCP Reno基于滑动窗口协议，针对未知窗口大小进行优化** TCP Reno is very much a sliding  window protocol, and it's optimised for not  knowing what the window sizes are.

![](/static/2021-04-08-12-11-08.png)

:orange: 因此需要解决的问题 （TCP）

* **如何选择初始窗口**（大小）？pick what should be the initial window
  * RTT，带宽，延迟未知情况下
* **如何找到路径容量** how to find the path capacity,
* **如何知道在某时已经有了何时窗口大小再进行调整，以应对容量变化**？how to figure out at what point  you've got the right size window. how to adapt the window  to cope with changes in the capacity.
* 即，**TCP Reno**需要改进（解决的问题）
  * 为发送的第一组数据包选取**初始窗口大小**。 然后，**调整这个初始窗口大小，找到瓶颈容量，并适应瓶颈容量的变化**。Picking the initial window  size  for the first set of packets you  send.  And then, adapting that initial window size  to find the bottleneck capacity, and to  adapt to changes in that bottleneck capacity.  
    * 如果窗口大小对了，你就可以有效地利用网络容量。If you get the window size right,  you can make effective use of the  network capacity.
    * 如果错了，要么发送太慢，最后浪费了容量。或者发送得太快，使网络过载，导致数据包因（链路）队列满溢出而丢失 If you get it wrong  you’ll either send too slowly, and end  up wasting capacity. Or you'll send too  quickly, and overload the network, and cause  packets to be lost because the queues  fill.

## Reno传统/现代拥塞窗口大小：Choosing the initial window

![](/static/2021-04-08-12-19-54.png)

TCP 如何选定初始窗口大小（bandwith * delay）？---- `Winit=1` how TCP find the initial window size?

* 当你向一个你从未与之通信过的主机进行TCP连接时，你不知道到该主机的往返时间(delay?)，你不知道得到响应需要多长时间，你也不知道网络容量(bandwidth)。 所以你没有任何信息知道一个合适的窗口大小应该是多少。Well, to start with, you have no  information. When you're making a TCP connection  to a host you haven't communicated with  before, you don't know the round trip  time to that host, you don’t know  how long it will take to get  a response, and you don't know the  network capacity.  So you have no information to know  what an appropriately sized window should be.
  * 唯一安全的事情，在任何情况下， **是发送一个数据包， 看看它是否到达， 看看你是否得到一个ACK**。 如果成功，下一次发送快一点。**然后逐渐增加发送的速度**。
  * 唯一安全的做法是，**从尽可能低的速率开始，相当于stop and wait模式（1包1ACK），然后从那里逐渐增加你的速率，一旦知道可行** The only thing which is safe in  all circumstances, is to send one packet,  and see if it arrives, see if  you get an ACK.  And if it works, send a little  bit faster next time.  And then gradually increase the rate at  which you send.  Right, the only safe thing to do  is to start at the lowest possible  rate, equivalent of stop-and-wait, and then gradually  increase your rate from there, once you  know that it works.
* <font color="red">问题：大多数情况下不可行，大多数链接并不是最慢的链接。大多数链接，你可以发送得更快。【上述从1开始启动的太慢了</font>】  Most links are not the slowest possible  link. Most links, you can send faster  than that.

:orange: 实践，TCP & TCP Reno传统做法

* **将初始窗口声明为3个数据包 `Winit=3`**。因此，您可以发送3个数据包，而不需要收到任何回复。而且，**当第三个数据包被发送时，您应该正要收到ACK信息，这将为发送第四个数据包的触发**。在这一点上，它开始应答时钟  What TCP has traditionally done, and the  traditional approach in TCP Reno, is declared  the initial window to be three packets.  So you can send three packets,  without getting any acknowledgments back.  And, by the time the third packet  has been sent, you should be just  about to get the acknowledgement back,  which will open it up for you  to send the fourth. And at that  point, it starts ACK clocking.
* WHY `3`？
  * some measurements, decided that was what safe

:orange: `Winit=10`

* **现代TCP实现**
* WHY `10`?
  * 同样，这也是安全性和性能之间的平衡。
  * 如果你把太多的数据包发送到一个**无法处理它们的网络上，这些数据包就会被排队**，在**最好的情况下，它只会增加延迟**，因为它们都在某个地方排队。在**最坏的情况下，它们会溢出队列，并导致数据包丢失**，您将**不得不重新传输它们** Again, it's a balance between safety and  performance. If you send too many packets  onto a network which can't cope with  them, those packets will get queued up  and, in the best case, it’ll just  add latency because they're all queued up  somewhere. And in the worst case they'll  overflow the queues, and cause packet loss,  and you'll have to re-transmit them.
    * **所以你不想发得太快。同样，你也不想发送的速度太慢，因为那只会浪费容量**。 So you don't want to send too  fast. Equally, you don't want to send  too slow, because that just wastes capacity.
    * 大约10年前，Google 提出的测量方法是，对于大多数连接来说，**大约10个数据包是一个很好的起点。在大多数情况下，它不太可能造成拥塞，也不太可能浪费太多带宽**。And the measurements that Google came up  with  at this point, which was around 10  years ago, was that about 10 packets  was a good starting point for most  connections.   It was unlikely to cause congestion in  most cases, and was also unlikely to  waste too much bandwidth.  
    * 我们**期望**看到的是，随着时间的推移，最初的窗口会逐渐增加，世界各地的网络连接会逐渐加快。它正在平衡，充分利用世界上有良好基础设施的第一世界地区的连接，与世界上基础设施不发达的地区不超负荷的连接 And I think what we'd expect to  see, is that over time the initial  window will gradually increase, as network connections  around the world gradually get faster.  And it's balancing making good use of  connections in well-connected  first-world parts of the world, where there’s  good infrastructure,  against not overloading connections in parts of  the world where the infrastructure at less  well developed.

## Reno-选定初始窗口后，需要长期调整窗口的机制（慢开始&拥塞避免）: Finding the Path Capacity

![](/static/2021-04-08-12-38-51.png)

使用现代的 TCP，它可以让你发送10个包。您可以发送这10个数据包，或者不管最初的窗口是什么，而无需等待确认返回。The initial window lets you send something.  With a modern TCP, it lets you  send 10 packets.  And you can send those 10 packets,  or whatever the initial window is,  without waiting for an acknowledgement to come  back.  

* **但它的大小可能不对，窗口的大小可能也不对**。But it's probably not the right size;  it’s probably not the right window size.  
* 如果你在一个非常快的连接上，在一个连接良好的世界的一部分，你可能需要一个比10个数据包大得多的窗口【没问题】。If you're on a very fast connection,  in a well-connected part of the world,  you probably want a much bigger window  than 10 packets.
* 如果你使用的是**低质量**的移动连接，或者是在世界上基础设施不发达的地区，你可能需要一个更小的窗口。**因此，您需要以某种方式调整窗口以匹配网络容量**。And if you're on a poor quality  mobile connection, or in a part of  the world where the infrastructure is less  well developed, you probably want a smaller  window.  So you need to somehow adapt the  window to match the network capacity.

:orange: Reno提供，调整窗口以匹配网络容量变化，的算法

* **慢开始 Slow Start**
  * 快速找到合适的初始窗口，从初始窗口`Winit`开始，你快速转换到正确的窗口 where you try  to quickly find the appropriate initial window,  where starting from initial window, you quickly  convert on what the right window is.
* **拥塞避免 Congestion Avoidance**
  * **长期调整窗口大小以适应容量变化** where you adapt in  the long term to match changes in  capacity once the thing is running.

### 慢开始：TCP Reno - Slow Start

![](/static/2021-04-08-13-09-38.png)

:orange: 连接开始初期 phase at the beginning of the connection

* **每收到一个ACK，窗口大小+1** the way slow start works is  that each acknowledgment you get back  increases the window by one.
  * `Winit=1`,发送1，返回1ACK
  * `W=1+1=2`，可发送2，返回2ACK
  * `W=1+2+1=4`，...
* **每次往返时间窗口都会加倍。每次发送速率都会加倍** But each round trip time the window  doubles. It doubles it's sending rate each  time.
* <font color="red">【初始窗口大小取决于TCP版本】</font>

:orange: **窗口会一直增加，直到丢失一个数据包（即，队列填满，溢出当前网络容量）** And this carries on until it loses  a packet. This carries on until it  fills the queues and overflows the capacity  of the network somewhere.

* <font color="red">此时，窗口减半，并退出慢启动阶段</font>   At which points it halves back to  its previous value, and drops out of  the slow start phase.

---

![](/static/2021-04-08-13-21-23.png)

* expotential growth of the rate,
  * each round trip time the window  doubles.
  * It doubles it's sending rate each  time.
* **队列溢出，（第一次？）丢包时，，窗口减小到原来的一半，，此时进入拥塞避免阶段** half the rate back and enter the congestion avoidance phase

### 拥塞避免-开始长期调整：Congestion Avoidance

![](/static/2021-04-08-13-24-30.png)

:orange: **阻塞避免的目的是（调整窗口/发送速率以）适应网络容量的变化** The goal of congestion avoidance is to  adapt to changes in capacity.

* 在慢启动阶段之后，**已经知道获得了路径的大致合适的窗口大小**。它**粗略**地告诉你每个往返时间**应该发送多少包**。After the slow start phase, you know  you've got approximately the right size window  for the path. It's telling you roughly  how many packets you should be sending  each round trip time.
  * 一旦你**进入了拥塞避免，你的目标就是适应网络容量变化** The goal,  once you’re in congestion avoidance, is to  adapt to changes.
* 存在什么变化：
  * 也许路径的容量改变了。也许你在一个移动设备上，有一个无线连接，无线连接的质量改变了。也许交叉路口的数量会发生变化。也许有更多的人开始与您共享链接，而您的容量较小，因为您与更多的 TCP 流共享。也许有些交叉流量消失了，可用的容量增加了，因为竞争的流量减少了 Maybe the capacity of the path changes.  Maybe you're on a mobile device,  with a wireless connection, and the quality  of the wireless connection changes.  Maybe the amount of cross traffic changes.  Maybe additional people start sharing the link  with you, and you have less capacity  because you’re sharing with more TCP flows.  On maybe some of the cross traffic  goes away, and the amount of capacity  you have available increases because there's less  competing traffic.

:candy: **拥塞避免采用和式增加， 倍数减少原则【AIMD原则，保证网络稳定】，以调整窗口大小适应网络容量变化** And the congestion avoidance phase follows an  additive increase, multiplicative decrease, approach to adapting  the congestion window when that happens.

---

![](/static/2021-04-08-13-44-00.png)

* 慢开始后第一次丢包，进入拥塞避免阶段
* **之后每次线性增加拥塞窗口大小 `+1` ，直到再次丢包**linear additive increase until reach the network capactiy at some points

---

### 拥塞避免-3个冗余ACK【丢包】：快重传 & 快恢复

![](/static/2021-04-08-14-10-26.png)

* **如拥塞避免阶段，通过3个冗余ACK检测到丢包【快重传】** If a packet is lost and detected via triple duplicate acknowledgement
  * 窗口大小倍数减半，`Wi=Wi-1 * 0.5` halve the window back down
  * 返回和式增加模式【<font color="red">这里算快恢复啊啊啊啊啊啊啊啊啊，cwnd只为原来的一半</font>】，每次cwnd+1，直到下一次丢包出现 then adopt the additive increase again

---

### 拥塞避免-超时机制-重新进入慢开始：TCP Tahoe版本

![](/static/2021-04-09-11-41-23.png)
![](/static/2021-04-08-14-27-06.png)

* **通过超时机制，检测到丢包** The other way TCP can detect the  loss is by what’s known as a  time out.
  * 它正在发送数据包，突然之间，确认完全停止了。这意味着要么接收器已经崩溃，要么接收系统已经消失，或者更有可能的是网络已经失败 It’s sending the packets,  and suddenly the acknowledgements stop coming back  entirely.  And this means that either the receiver  has crashed, the receiving system has gone  away, or perhaps more likely the network  has failed.
  * 发送的数据要么没有到达发送方，要么反向路径失败，并且确认没有返回。**此时，在一段时间内没有返回任何数据之后，它假设发生了超时，并将窗口重置为【初始窗口大小】** And the data it’s sending is either  not reaching the sender, or the reverse  path has failed, and the acknowledgments are  not coming back.  At that point, after nothing has come  back for a while,  it assumes a timeout has happened,  and resets the window down to the  initial window.

:orange: 窗口重置为 `Winit`大小 【**非快恢复，直接打回init**】

* 取决于TCP版本，1，3，10
* **重新进入慢开始阶段，再次尝试连接** it  re-enters slow start, and it tries again  for the connection.
  * 如果这只是暂时的失败，那么这种方法很可能会成功。And if this was a transient failure,  that will probably succeed.
  * 如果不是，它可能会在另一个超时中结束，同时网络需要时间恢复，或者你正在与之通话的系统需要时间恢复，并且在它成功发送数据包之前需要一段时间。但是，当它恢复时，当 x 网络恢复时，它开始再次发送，并从开始重置连接 If it wasn’t,  it may end up in yet another  timeout, while it takes time for the  network to recover, or  for the system you're talking to,  to recover, and it will be a  while before it can successfully send a  packet. But, when it does, when the x network recovers, it starts sending again,  and resets the connection from the beginning.

:orange: **超时时长应为多少**？how long should the timeout be?

* 标准 - 1s the standard says a maximum of  one second
* 或，平均往返时间加上往返时间的四倍统计方差 or the average round trip  time plus four times the statistical variance  in the round trip time
* `Trto = max(1 second, average RTT + (4 x RTT variance))`

## Reno算法对收方影响 & 如何使网络稳定：Congestion Window Growth, Buffering, Throughput

![](/static/2021-04-08-14-37-27.png)

**TCP遵循“锯齿模式**” follows the sawtooth pattern

* 随着发送速率的增加，队列逐渐增加。最初队列是空的，随着它开始发送速度的加快，队列逐渐变得更满。到了某个时候，队列就会满，然后就会溢出。
* 当队列满时，当队列溢出时，当数据包丢失时，TCP 减半发送速率。**这会导致队列迅速清空，因为进入的数据包较少，所以队列会消耗殆尽。但是我们看到的是，正当队列逐渐变空时，速率又开始增加了**
  * 正当队列到达无需发送的点时，速率开始加快，**这样队列开始逐渐重新填充**。因此，路由器中的队列也遵循锯齿模式。他们逐渐下降，直到他们到达一个满点，然后速率减半，队列迅速空，因为有更少的流量返回，并且随着它的排空速率，发送者发送的是逐渐填满，和队列大小振荡
* RTT同理也遵从“锯齿模式”（图3），当队列逐渐满了，往返时间增加了，，数据包需要更长的时间，因为它们在某个地方排队。然后速率降低，排队减少，往返时间减少。并且随着速率的逐渐回升，队列逐渐填满，往返时间逐渐增加

:orange: 图4 - 接收方速率 at the rate at  which packets are arriving at the receiver.

* <font color="deeppink">数据包到达接收端的速率几乎是恒定的</font> the rate at  which packets are arriving at the receiver  is pretty much constant.
  * **因为窗口减小时，队列从来不会被完全清空，因此TCP发方就算遵循“锯齿模式”，通过窗口逐渐填满队列，然后减半速率后，队列逐渐耗尽，但永远不会完全空出，总会有一些数据要发送，而收方总是在接收数据【网络稳定**】 when TCP  backs-off, when it reduces it's window,  that lets the queue drain. But the  queue never quite empties. typically the queue always has something in it, though getting less data in it, but the queue ,if the buffer is sized right and window is chosen right, would never quite empties
* <font color="blue">即，即使发送方遵循锯齿模式，接收方在整个过程中都会以恒定速率接收到数据，大约是链路带宽</font>  So, even though the sender's following the  sawtooth pattern, the receiver receives constant rate  data the whole time,  at approximately the bottleneck bandwidth.

:candy: TCP - **通过和式增加，倍数减少的模式，适应调整速率，这样缓冲永远不会空闲，最大限度利用链路（bottleneck），数据能持续被交付** And that's the genius of TCP.  It manages, by following this additive increase,  multiplicative decrease, approach, it manages to adapt  the rate such that the buffer never  quite empties, and the data continues to  be delivered.

* 为了实现这一点，它需要**路由器有足够的缓冲能力。而路由器需要的缓冲量，是带宽乘以路径的延迟（`bandwidth * delay`）**。And for that to work, it needs  the router to have enough buffering capacity  in it. And the amount of buffering  the router needs, is the bandwidth times  the delay of the path.
  * 路由器中的<font color="red">缓冲太少会导致队列溢出，并且不能很好地维持速率。太多，你只会得到所谓的缓冲区膨胀</font>。And too  little buffering in the router  leads to  the queue overflowing, and it not quite  managing to sustain the rate. Too much,  you just get what’s known as buffer  bloat.
  * It's safe, I mean in terms of  throughput, it keeps receiving the data.  But the queues get very big,  and they never get anywhere near empty,  so the amount of data queued up  increases, and you just get increased latency.

## Reno局限性: Discussion

![](/static/2021-04-08-15-41-39.png)

TCP Reno

* 高效保证链路利用率 effectively at keeping bottleneck link fully utilised
* 它以延迟为代价换取吞吐量 it trades latency for throughput.
  * **它试图填充队列，它不断地推送，它不断地排队数据。确保队列永远不会是空的【前提：网络有足够缓冲，确保总有数据包被传送】** It tries to fill the queue,  it's continually pushing, it’s continually queuing up  data.  Making sure the queue is never empty.  Making sure the queue is never empty,【provided there’s enough buffering in the  network there are always packets being delivered】

:orange: TCP Reno除了延迟的增加外，还有另外2个局限性 There are two other limitations, other than  an increased latency. 

* **TCP假设丢包是由于拥塞造成的（显式拥塞通知解决**） TCP assumes that losses  are due to congestion.
  * 而从历史上看，这也是事实。当然，在有线链路中，数据包丢失几乎都是由于队列填满、溢出，以及路由器没有空间来enqueue一个数据包造成的 And historically that's been true. Certainly in  wired links, packet loss is almost always  caused by a queue filling up,  overflowing, and a router not having space  to enqueue a packet.
  * 在某些类型的无线链路中，**在4G或WiFi链路中，情况并不总是如此，【会因为损坏而导致数据包丢失】**。 **而TCP会把这当作一个信号，放慢速度**。也就是说，TCP在无线链路上有时会表现得不理想。 而有一种机制叫做**显式拥塞通知**，，它试图解决这个问题  In certain types of wireless links,  in 4G or in WiFi links,  that's not always the case, and you  do get packet loss due to corruption.  And TCP will treat this as a  signal to slow down. Which means that  TCP sometimes behaves sub-optimally on wireless links.  And there's a mechanism called Explicit Congestion  Notification, which we'll talk about in one  of the later parts of this lecture,   which tries to address that.
* **避免拥塞阶段可能需要很长的时间增加容量，因为每次AIMD，cwnd+1（TCP Cubic解决**） Congestion avoidance phase takes a long time to use increased capacity
  * 在非常长距离的链路上，非常高容量的链路上，它可能需要很长的时间，**丢包之后，可能需要很长的时间才能恢复到一个合适的速率**。On  very long distance links, very high capacity   links, it can take a long time   to get up to, after packet loss,  it can take a very long time  to get back up to an appropriate  rate.
  * 而且在一些非常快的长距离链路的场合，它的表现很差，因为避拥塞的工作方式 And there are some occasions with very   fast long distance links, where it performs  poorly, because of the way the congestion  avoidance works.

# Outline: TCP Cubic - 快速长距离网络

现代TCP大多使用TCP Cubic（默认的Reno并不会广泛使用）

* 而TCP Cubic的目标是**提高TCP在快速长距离网络上的性能**。 And the goal of TCP cubic is  to improve TCP performance on fast long  distance networks.

## Reno长距离网络局限性 & Cubic改进：TCP Performance on Fast Long-distance Networks

:orange: TCP Reno局限性, 存在的问题

![](/static/2021-04-08-16-12-40.png)

* 高速长距离网络中效率很低 perform poorly on fast long-distance network
  * 如果在链路上丢失了一个数据包，**在10万个数据包的窗口中，会导致TCP后退，将它的窗口减半**。然后它又会增加发送速率，**每往返一次就增加一个数据包（收敛的太慢了！！！！！**）。 而从10万个数据包的窗口退到5万个数据包的窗口，然后每次增加一个，就意味着需要5万个往返时间才能恢复到满窗口。 5万次往返，当往返时间为100毫秒时，约为1.4小时 ---  **所以，TCP从一个数据包丢失中恢复过来大约需要一个半小时**

:candy: <font color="deeppink">TCP cubic 用于解决该问题(问题主要出现在拥塞避免阶段，因为Reno慢开始阶段是倍数增长的)</font> TCP cubic is one of a range  of algorithms which were developed to try  and address this problem

* **在拥塞窗口非常大，数据包丢失非常少的情况下**，尝试比 TCP Reno <font color='red'>恢复的快得多</font> To try and  recover much faster than TCP Reno would,  in the case when you had very  large congestion windows, and small amounts of  packet loss.

## 概述：TCP Cubic 拥塞避免阶段的改变

![](/static/2021-04-08-16-22-16.png)

:orange: **TCP cubic 改变了拥塞控制在拥塞避免阶段的工作方式** So the idea of TCP cubic,  is that it changes the way the  congestion control works in the congestion avoidance  phase.

* 因此，在拥塞避免中，**当窗口较大**时，**TCP cubic 比 TCP Reno 更快地增加拥塞窗口大小** So, in congestion avoidance, TCP cubic will  increase the congestion window faster than TCP  Reno would, in cases where the window  is large.
* 在窗口相对较小的情况下，在具有 Reno 的网络类型中，TCP Cubic行为具有非常相似的性能 In cases where the window is relatively  small, in the types of networks were  Reno has good performance, TCP cubic behaves  in a very similar way.
  * 但是当**窗口变得越来越大**时，由于 TCP Reno 系统不能有效工作，TCP cubic 在调整拥塞窗口时变得越来越积极，并且**为了应对丢包而更快地增加拥塞窗口** But as the windows get bigger,  as it gets to a regime with  TCP Reno doesn't work effectively, TCP cubic  gets more aggressive in adapting its congestion  window, and increases the congestion window much  more quickly in response to loss.

:orange: <font color="deeppink">随着速度增加，【窗口大小接近丢包前最大数值】，增加速度会变慢。如果随后以这个速率发送成功，并且高于先前的发送速度，那么会逐渐增加发送速度</font> However, as the rate of increase,  as the window approaches the value it  was before the loss, it slows its  rate of increase, so it starts increasing  rapidly, slows its rate of increase  as it approaches the previous value.  And if it then successfully manages to  send at that rate, if it successfully  moves above the previous sending rate,  then it gradually increases sending rate again.

## TCP Cubic控制拥塞窗口细节： Congestion Control

![](/static/2021-04-08-16-30-35.png)

TCP Cubic拥塞控制分两部分

* **当处于拥塞避免阶段，如丢包，倍数减少** If a packet is lost when a  TCP cubic sender is in the congestion  avoidance phase,  it does a multiplicative decrease
  * 但与Reno倍数减半（\*0.5）不同，**Cubic回退到 `70%` （*0.7**）
    * 更积极地使用带宽。 它降低了它的发送速率以应对丢包，**但降低的幅度较小**  It backs-off less, it's more aggressive.  It’s more aggressive at using bandwidth.  It reduces it’s sending rate in response  to loss, but by smaller fraction.
* **同时改变了之后增加发送速率的方式** change the way in which increases it's sending rate in future(after packet loss during congestion aovidance)
  * TCP Reno每一个RTT，cwnd+1
  * 而 TCP Cubic，将窗口设置为 `Wcubic = C (t - K)^3 + Wmax`
    * `C` constant -> `0.4` 阈值，控制 **how fair it is to TCP Reno** and was determined experimentally
      * <font color="red">速度较慢的链路上，or RTT较短的链路上，与Reno持平</font> make sure it's fair with TCP  Reno on links which are slower,  or where the round trip time is  shorter.
    * `t` the time since the packet loss
    * `K` it will take to increase the window back to Wmax, assuming no further packet losses
    * `Wmax` is the maximum window  size it reached before the loss

:orange: **上述Cubic这种窗口公式，使得窗口开始快速增加，当它接近丢包前达到的那个前值时，增长速度就会放缓，如果成功通过这个点，增长速度又会增加** window starts to increase quickly,  the growth slows as it approaches that  previous value  it reached just before the loss,  and if it successfully passes through that  point, the rate of growth increases again.

* 使得<font color="red">足够大拥塞窗口时，能够更快的从丢包中恢复过来，更有效地继续利用高带宽&高时延链路</font> allows it to recover  from losses more quickly, and to more  effectively continue to make use of higher  bandwidths and higher latency paths.

## TCP Cubic vs Reno - 时延换吞吐量

TCP cubic是大多数现代操作系统的默认选项。Linux，FreeBSD，macOS，微软Windows有一种算法叫Compound TCP，这是一种不同的算法，但效果相似 TCP Cubic is default in most modern operating systems

* **Cubic 比 Reno复杂的多，而其中大部分是为了确保它对TCP Reno，在Reno通常工作的体制下是合理公平的** Much more complex than TCP Reno – core response is relatively straight-forward 
Much complexity ensures fairness with TCP Reno in its typical operating regime;
* **但是对于往返时间较长（时延长），带宽较高的网络来说，它可以提高性能** improves
performance for networks with longer RTT and higher bandwidth

:orange: 无论是TCP Cubic，还是TCP Reno，都使用拥塞控制，**将丢包作为拥塞信号** Both algorithms use packet loss as congestion signal and eventually fill router buffers

* 而且它们最终都填满了路由器的缓冲区。 而TCP cubic比Reno做得更积极  And they both eventually  filled the router buffers.  And TCP cubic does so more aggressively  than Reno
* 所以在这两种情况下，他们都在用**延迟来换取吞吐量**，他们试图确保缓冲区是满的。**他们都在确保他们保持足够大的拥塞窗口，以保持缓冲区的充分利用**，所以数据包在任何时候都能到达收方So in both cases,  they're trading off latency for throughput,  They're trying to make sure the buffers  are full. They're trying to make sure  the buffers in the intermediate routers are  full. And they're both making sure that they  keep the congestion window large enough to  keep the buffers fully utilised, so packets  keep arriving at the receiver at all  times
* 这对实现高吞吐量(链路利用率)很有好处，但会增加延迟。 **所以，他们以增加延迟来换取良好的性能，换取良好的吞吐量** And that's very good for achieving high  throughput, but it pushes the latency up.  So, again, they’re trading-off increased latency for  good performance, for good throughput.

# Outline：Delay-based Congestion Control

**TCP Reno和TCP cubic**。这些都是标准的、**基于丢包**的、拥塞控制算法，大多数TCP实现都使用这些算法来调整其发送速率。这些是TCP的标准拥塞控制算法 TCP Reno and TCP cubic. These are  the standard, loss based, congestion control algorithms  that most TCP implementations use to adapt  their sending rate. These are the standard  congestion control algorithms for TCP

* **为什么这些算法会在网络中造成【额外的延迟**】 why these algorithms cause  additional latency in the network
* 谈谈两种替代方案，**它们试图在不建立队列的情况下适应TCP的发送速率，并且不会使网络超载和造成过多的延迟**  two alternatives which try to adapt  the sending rate of TCP without building  up queues, and without  overloading the network and causing too much  latency.

## Reno/Cubic拥塞控制对时延的影响：Impact of TCP on Latency

![](/static/2021-04-08-17-35-25.png)

TCP Reno & CUbic将丢包作为拥塞信号 TCP Cubic and  TCP Reno both aim to fill up  the network.  They use packet loss as a congestion  signal.

* **工作方式都是逐渐提高发送速率，逐渐填满链路队列，直到【队列溢出**，，一定会溢出，因为需要丢包信号来作拥塞控制】 the way they work is they  gradually increase their sending rate, they’re in  either slow start or congestion avoidance phase,  and they’re always gradually increasing the sending  rates, gradually filling up the queues in  the network, until those queues overflow
  * 此时，丢包后，降低速率，使得队列清空（几乎），但清空时，Reno & Cubic又重新增加发送速率（窗口大小）**逐渐重新填满队列** The TCP backs-off it's sending rate,  it backs-off its window, which allows the  queue to drain, but as the queue  is draining, both  Reno and Cubic are increasing their sending  rate, are increasing the sending window,  so are to gradually start filling up  the queue again.
* 无论是Reno还是Cubic，目标都是在网络中保持一些数据包的排队，确保总有一些数据排队，这样他们就可以继续提供数据。both Reno and Cubic, the goal  is to keep some packets queued up  in the network, make sure there's always  some data queued up, so they can  keep delivering data.
* **而且，无论你在网络中放了多大的队列，无论你给网络中的路由器多少内存，TCP Reno和TCP cubic【最终都会导致它溢出**】。
  * 它们会不断地发送，不断地提高发送速率，直到网络中无论什么队列都满了，它就会溢出。no matter how big a queue  you put in the network, no matter  how much memory you give the routers  in the network, TCP Reno and TCP  cubic will eventually cause it to overflow.  They will keep sending, they'll keep increasing  the sending rate, until whatever queue is  in the network it's full, and it  overflows. 
  * **而路由器中的内存越多，路由器中的缓冲区越多，那个队列就会越长，【延迟（丢包重传造成的）】就会越大** And the more memory in the routers,  the more buffer in the routers,  the longer that queue will get and  the worse the latency will be.

:orange: **但在任何情况下，为了达到很高的吞吐量，为了保持网络繁忙，保持瓶颈链路繁忙，TCP Reno和 TCP Cubic队列一些数据。这就增加了延迟时间**。But in all cases, in order to  achieve very high throughput, in order to  keep the network busy, keep the bottleneck  link busy, TCP Reno and TCP cubic  queue some data up.  And this adds latency.  

* **这意味着，只要有 TCP Reno，只要有 TCP Cubic，使用网络，队列就会有数据排队**。总会有一些数据排队等待发送。总是有数据包在等待分发 It means that, whenever there’s TCP Reno,  whenever there’s TCP cubic flows, using the  network, the queues will have data queued  up.  There’ll always be data queued up for  delivery. There's always packets waiting for delivery.  
* **因此，它迫使网络在一个总是有一些过度延迟的机制下工作** So it forces the network to work  in a regime where there's always some excess latency.

## TCP Vegas降低延迟概述: Reducing Latency

![](/static/2021-04-08-21-09-55.png)

实时应用 - 希望延迟尽可能地低 low latency for real-time applications

* 所以，**如果我们能够有一个替代TCP Reno或TCP cubic的方案，能够为TCP实现良好的吞吐量，而【不强迫队列满溢出**】，这将是可取的 So it will be desirable if we  could have a an alternative to TCP  Reno or TCP cubic that can achieve  good throughput for TCP, without forcing the  queues to be full.
  * 已知 Reno & Cubic会队列数据包至溢出（超过了网络所能提供的最大容量，延迟、往返时间会逐渐增加），丢包后才降低速率 TCP Reno and Cubic wait until the queue overflows and packets are lost before slowing down

:orange: TCP Vegas

* **可以监视队列的增长速度，或者说增加速度，并以此来推断你的发送速度是快于网络所能支持的速度，还是慢于网络** watch the rate of growth,  or increase, of the queue, and use  that to infer whether you're sending faster,  or slower, than the network can support.
* **Vegas试图平衡速率 & RTT** trying to balance it’s rate  with the round trip time, and not  build or shrink the queues.
  * 监视到延迟增加的时候 -> **队列溢出之前放慢速度** watch as the delay increases, and as  it sees the delay increasing, it slows  down before the queue overflows.  So it uses the gradual increase in  the round trip time, as an indication  that it should send slower
  * 当RTT下降 -> **队列正在耗尽的指示，此时可以发送的更快**  And as the round-trip time reduces,  as the round-trip time starts to drop,  it treats that as an indication that  the queue is draining, which means it  can send faster.
* **因此，【数据包不会丢失（队列从来不溢出），不需要重传】。从而提高吞吐量**，It also means that because packets are  not being lost, you don't need to  re-transmit as many packets. So it improves  the throughput that way, because you're not  resending data that you've already sent and  has gotten lost.

:orange: TCP Vegas不影响慢开始阶段 Only affects congestion avoidance; slow start unchanged

* 一旦进入拥塞避免，会开始**监视RTT变化**，而不关注数据包丢失，以此来驱动发送速率的变化 once you're into congestion avoidance,  it looks at the variation in round  trip time rather than looking at packet  loss, and uses that to drive the  variation in the speed at which it’s sending

## TCP Vegas拥塞控制细节： Congestion Control

* 首先估计 **BaseRTT**
  * 每次发送一个数据包，测量多长时间收到ACK first, it tries to estimate what  it calls the base round trip time.  So every time it sends a packet,  it measures how long it takes to  get a response.
  * <font color="deeppink">试图找到最小的RTT---队列最空的时候</font> the smallest time it gets a response, would be the time when the queue is that it's emptiest
  * *它可能不会得到实际的、完全空的队列，但是响应时间越短，它就会试图估计在网络中没有其他任何东西的情况下所需要的时间。上面的任何内容都表明网络中某个地方存在排队的数据* It may not get the actual,  completely empty, queue, but the smaller the  response time, it's trying to estimate the  time it takes when there's nothing else  in the network.  And anything on top of that indicates  that there is data queued up somewhere  in the network.
* **然后计算【预期的发送速率】 ExpectedRate = `W/ BaseRTT`**。
  * 它采用窗口大小，表示在往返时间内应该发送多少个数据包，在往返时间内应该发送多少个字节的数据，然后用基本往返时间除以它。这就给出了它应该发送数据的速率。
  * **如果网络能够支持以这种速率发送，它应该能够在一个完整的往返时间内提供该窗口大小的数据包**。  if the network can  support sending at that rate, it should  be able to deliver that window of  packets within a complete round trip time. <font color="deeppink">而如果不能，则需要比一个往返时间更长的时间来发送该窗口的数据包，队列将逐渐建立起来 另外，如果需要的时间少于一个往返时间，则表明队列正在减少</font> And, if it can’t, it will take  longer than a round trip time to  deliver that window of packets, and the  queues will be gradually building up Alternatively,  if it takes less than a round  trip time, this is an indication that  the queues are decreasing.
* **最后测量它发送数据包的【实际速率**】。**然后进行比较**。• Measure ActualRate then compare
  * **如果实际发送数据包的速率低于预期速率，(如果发送完整窗口数据包的往返时间长于预期速率)，这表明数据包不能全部发送**。And if the actual rate at which  it's sending packets is less than the  expected rate, if it's taking longer than  a round-trip time to deliver the complete  window worth of packets, this is a  sign that the packets can’t all be  delivered
    * 它试图以过快的速度发送信息，它应该降低速率，让队列减少。And it,  you know, it's trying to send too  much. It’s trying to send at too  fast a rate, and it should reduce  its rate and let the queues drop
  * **同样，在另一种情况下，它应该提高速率**并衡量实际利率和预期利率之间的差异，它可以衡量队列是增长还是减少。Equally, in the other case it should  increase its rate, and measuring the difference  between the actual and the expected rates,  it can measure whether the queues growing or shrinking.

---

:orange: **TCP Vegas 将预期速率与实际速率进行了比较**，实际上预期速率设法以，收到ACK的预期速率发送。**它调整窗口大小** And TCP Vegas compares the expected rate,  which actually manages to send at,  the expected rate at which it gets  the acknowledgments back, with the actual rate.  And it adjusts the window.

* **如果差异小于阈值，增加窗口** If ExpectedRate - ActualRate < R1 then additive increase to window
  * 数据接近预期速率，网络可以支持更高速率，可以更快一点发送 if data is arriving at  the expected rate, or very close to  it, this is probably a sign that  the network can support a higher rate,  and you should try sending a little  bit faster.
* **如果差异大于阈值，减少窗口** If ExpectedRate - ActualRate > R2 then additive increase to window
  * 如果数据到达的速度比发送的速度慢，这说明你发送的速度太快了，你应该放慢速度 data is arriving slower than  it's being sent, this is a sign  that you're sending too fast and you  should slow down.
* 阈值 `R1/R2` 决定离预期速率的远近程度 two thresholds, R1 and R2,  determine how close you have to be  to the expected rate, and how far  away from it you have to be  in order to slow down.
  * Parameters R1 and R2, where R1 < R2, critical to performance

:orange: 由图，以及Vegas的模式，其具<font color="deeppink">有更平稳的传输速率</font>

* **Reno - 遵从锯齿模式（“sawtooth pattern”） & Cubic 遵从立方方程**
  * 这两种协议只要**丢包**出现，就会突然改变 Unlike TCP Reno, which follows the characteristic  sawtooth pattern, or TCP cubic which follows  the  cubic equation to change it’s rate,  both of which adapt quite abruptly whenever  it's a packet loss,  
* 而Vegas只是**逐渐改变** TCP Vegas makes a gradual change.
  * <font color="red">它根据队列的变化，逐渐增加或减少发送速率。所以，它的算法更加平滑，【它不会不断地建立起队列，也不会不断地清空队列。 因为队列不是持续地建立起来，不是持续地被填满，所以，这就在保持延迟的同时，还能达到近期良好的性能】</font>It gradually increases, or decreases, it’s sending  rate in line with the variations in  the queues. So, it’s a much smoother  algorithm, which doesn't continually build up and  empty the queues.  Because the queues are not continuing building  up, not continually being filled, this keeps  the latency down while still achieving recently  good performance.

## Vegas优点

![](/static/2021-04-09-09-36-05.png)

:orange: TCP Vegas is a good idea in  principle.

* **Delay-based congestion control**
* **降低时延** reduces latency
  * 因为它**不会填满队列，不会造成丢包重传** because it doesn't fill the  queues
* **降低丢包** reduces packet loss
  * 因为**它不会导致，队列溢出和数据包丢失**。所以你只会得到由传输问题引起的数据包丢失。这减少了不必要的，减少了你重输数据包的必要性，（迫使网络过载，迫使它丢失数据包，需要重传），**从而减少了延迟** because it's  not causing, t's not pushing the queues  to overflow and causing packets to be  lost. so the only packet losses you  get are those caused by transmission problems.  And this reduces unnecessary, reduces you having  to transmit packets, because you forced the  network into overload, and forced it to  lose the packets, and it reduces the  latency.

## Vegas局限性：Limitations

:candy: 存在的问题 【部署困难，无法与Reno/Cubic协同】

* **无法兼容** The problem with TCP Vegas is that  it doesn't work, doesn’t interwork work with,  TCP Reno or TCP cubic. 【loss- and delay-based congestion control don’t cooperate】
* 如果你的网络上有任何**TCP Reno或Cubic流，它们会积极地提高发送速率，并试图填满队列，并将队列推向超负荷**。If you have any TCP Reno or  Cubic flows on the network, they will  aggressively increase their sending rate and try  to fill the queues, and the push  the queues into overload.
  * 而这将增加往返时间，**降低Vegas的发送速度（因为通过监视速率，几乎快达到阈值），会迫使TCP Vegas减速**。And this will increase the round-trip time,  reduce the rate at which Vegas can  send, and it will force TCP Vegas  to slow down.  
    * 因为TCP Vegas看到队列在增加，因为Cubic和Reno有意要填满这些队列，如果队列增加，就会导致Vegas放慢速度Because TCP Vegas sees the queues increasing,  because Cubic and Reno are intentionally trying  to fill those queues, and if the  queues increase, this causes Vegas to slow  down.
    * 这就逐渐意味着队列中有更多的空间，Cubic和Reno会逐渐填满这些队列，这就会导致Vegas放慢速度，最后它们会形成一个螺旋式上升，TCP Vegas的流量会被推到零，而Reno或Cubic的流量会使用所有的容量 That gradually means there's more space in  the queues, which Cubic and Reno will  gradually fill-up, which causes Vegas to slow  down, and they end up in a  spiral, where the TCP Vegas flows get  pushed down to zero, and the Reno  or Cubic flows use all of the  capacity.

:orange: **所以，如果网络中仅使用Vegas，表现应理想，延迟低** So if we only have TCP Vegas  in the network, I think it would  behave really nicely, and we get really  good, low latency, behaviour from the network.

* 但是，<font color="deeppink">当前Reno & Cubic已广泛部署，很难完全撤销它们再换上Vegas，而Vegas与Reno/Cubic又不能协同</font>  Unfortunately we're in a world where Reno,  and Cubic, have been deployed everywhere.  And without a step change, without an  overnight switch where we turn of Cubic,  and we turn off Reno, and we  turn on Vegas, everywhere we can't deploy  TCP Vegas because always loses out to  Reno and Cubic.

## TCP BBR

![](/static/2021-04-09-09-50-14.png)

人们试图找到一个**基于队列时延作为拥塞控制的（致力于低时延），且能与 基于丢包的拥塞控制算法（Reno,Cubic）共同工作的算法** develop a delay-based congestion control algorithm for TCP that can be deployed on a network algongside TCP Reno & Cubic(loss  based congestion control)

> 其思想是，它在发送数据包时，试图显式地度量往返时间。它厌倦了以与 TCP Vegas 相同的方式明确地测量发送速率。并且，基于这些测量，和一些探测器，它改变它的速率尝试和发现它是否有更多的容量，或尝试和感觉是否有其他流量在网络上。它试图根据这些测量值直接设置一个与网络容量相匹配的拥塞窗口。而且，因为这是来自谷歌，它得到了很多的媒体，谷歌打开了它，为他们提供了很多流量。我知道他们曾经在 YouTube 上做过一段时间，很多人看到了这个视频，就赶上了这股潮流。有一段时间，它开始得到合理数量的部署。 The ideas is it tries to explicitly  measure the round-trip time as it sends  the packets. It tires to explicitly measure  the sending rate in much the same  way same way that  TCP Vegas does. And, based on those  measurements, and some probes where it varies  its rate to try and find if  it's got more capacity, or try and  sense if there is other traffic on  the network.  It tries to directly set a congestion  window that matches the network capacity,  based on those measurements.  And, because this came out of Google,  it got a lot of press,  and Google turned it on for a  lot of their traffic. I know they  were running it for YouTube for a  while, and a lot of people saw  this, and jumped on the bandwagon.  And, for a while, it was starting  to get a reasonable amount of deployments.
> 问题是，这种方法效果不是很好.对常规的 TCP 流量是极其不公平的
> TCP Reno 和 TCP Cubic 将迫使 TCP Vegas 流变为零，而 TCP BBR 则相反，它破坏了 Reno 和 Cubic 流，并给这些流造成了巨大的数据包丢失。所以在某些情况下，它确实比其他资本流动更具侵略性，这导致了相当严重的不公平问题
> 并清楚地证明了 TCP BBR version 2确实存在很多问题，在当前的网络上部署并不安全。还有一个变体叫做 BBR v2，它正在开发中，并且看起来正在改变，当然是每月一次，它正在试图解决这些问题。这是一个非常活跃的研究领域，人们正在寻找更好的替代品。

# Outline: Explicit Congestion Notification

显式拥塞通知(解决基于丢包的，Reno，Cubic的另一个方式)，当网络发送速度过快，需要降低其传输速率时，直接通知TCP Explicit  Congestion Notification, which allows the network to  directly tell TCP when it's sending too  fast, and needs to reduce it’s transmission  rate.

## 原TCP拥塞控制的不足 & 为何引入显式拥塞通知：Explicit Congestion Notification

![](/static/2021-04-09-09-59-14.png)

大多TCP流，推断方式通过丢包判断 TCP has inferred congestion through measurement

* TCP Reno and Cubic → packets lost when queues overflow
* 不断地逐步提高发送速率，试图造成队列溢出。 而他们造成队列溢出，造成数据包丢失，并将数据包丢失作为网络繁忙的信号，说明他们已经达到了网络容量，应该降低发送速率 keep gradually  increasing their sending rates, trying to cause  the queues to overflow.  And they cause a queue overflow,  cause a packet to be lost,  and use that packet loss as the  signal that the network is busy,  that they've reached the network capacity,  and they should reduce the sending rate.
* 存在的问题
  * **加大了延迟** increases delay
    * 它不断地将队列推到满，这意味着网络在满队列的情况下运行，其可能的最大延迟（丢包重传延迟 & 排队延迟。）It's continually pushing the queues to be  full, which means the network’s operating with  full queues, with its maximum possible delay.
  * **这使得很难区分哪些损失是由于队列溢出造成的，哪些损失是由于链路上的传输错误造成的，也就是所谓的非拥塞丢包，也就是你可能因为干扰或无线链路而得到的** it makes  it difficult to distinguish loss which is  caused because the queues overflowed, from loss  caused because of a transmission error on  a link, so called non-congestive loss,  which you might get due to interference  or a wireless link.

TCP Vegas

* 其中观察排队延迟的变化，并将其作为损失的指示。where look at variation in queuing latency  and use that as an indication of  loss.
  * 因此，与其将队列推到溢出，并检测溢出，不如观察队列开始变大，并将其作为降低发送速率的指示。或者，同样地，发现队列越来越小，并用它作为一个指示，可能应该提高发送速率 So, rather than pushing the queue until  it overflows, and detecting the overflow,  you watch to see as the queue  starts to get bigger, and use that  as an indication that you should reduce  your sending rate. Or, equally, you spot  the queue getting smaller, and use that  as an indication that you should maybe  increase your sending rate.
* 而这在概念上是个好主意，因为它可以让你以较低的延迟运行TCP。**但它很难部署，因为它与TCP Cubic和TCP Reno的交互性很差，这两个都试图填补队列**。And this is conceptually a good idea,    because it lets you run TCP with  lower latency. But it's difficult to deploy,  because it interacts poorly with TCP Cubic  and TCP Reno, both of which try  to fill the queues.

:orange: 如想避免Reno & Cubic，避免使网络链路队列满溢出，**用一种低延迟方式来使用TCP（在网络不填满队列的情况下**）we'd like to  go for a lower latency way of  using TCP, and make the network work  without filling the queues

* **与其让TCP把队列推到溢出，不如让【网络】在TCP发送太快时，直接通知它，需要放慢速度  --- 显式拥塞通知** rather than have TCP  push the queues to overflow,  have the network rather tell TCP when  it's sending too fast.  Have something in the network tell the  TCP connections that they are congesting the  network, and they need to slow down.  And this thing's called Explicit Congestion Notification.

## ECN bit and IP

**ECN bit在IP头中** Explicit Congestion Notification, the ECN bits,  are present in the IP header.

![](/static/2021-04-09-10-46-38.png)

* 使用方式
  * 如发方支持，`ECN=01`
    * 报头的第15位设置为1
  * 如发方不支持，`ECN=00`

:orange: **如果网络中发生了拥塞，如果网络中的一些队列开始满了，还没有到溢出的时候**，这样网络中的一些**路由器**认为要开始遇到拥塞了 If congestion occurs in the network,  if some queue in the network is  beginning to get full, it’s not yet  at the point of overflow but it's  beginning to get full, such that some  router in the network thinks it's about  to start experiencing congestion

* **网络中的拥塞【路由器，会将IP包中的ECN两个位都设为1，即`11`】** that router in the  network, changes those bits in the IP  packets, of some of the packets going  past, and sets both of the ECN  bits to one.
  * <font color="deeppink">由rooter来标记的啊啊啊啊啊啊啊啊啊啊啊</font>
  * 表示经历拥塞 (“congestion experienced”) --- **ECN Congestion  Experienced mark**
  * **网络给端点的拥塞信号 --- 网络认为开始繁忙/拥塞，端点应放慢速度** It's a signal from  the network to the endpoints, that the  network thinks it's getting busy, and the  endpoint should slow down.

:orange: **路由器，监控队列占用率，如果队列占用率高于某个阈值，设置数据包的ECN位，以表示达到了阈值，网络开始繁忙，端点需要放慢速度** It monitors  the occupancy in the queues, and if  the queue occupancy is higher than some  threshold, it sets the ECN bits in  the packets going past, to indicate that  threshold has been reached and the network  is starting to get busy.

## ECN and TCP

### 拥塞经历标记-ECN Congestion Experienced mark：ECN-CE

![](/static/2021-04-09-11-01-22.png)

接收者可能会收到一个TCP数据包/TCP段（在一个IP数据包中），A receiver might get a TCP packet,  a TCP segment, delivered within an IP  packet, where that

* 这个IP数据包有**ECN拥塞经历的标记**设置。IP packet has the  ECN Congestion Experienced mark set. **ECN-CE**
  * 其中网络将IP报头中的这两个位改成了11，表示**网络正在经历拥塞** Where the  network has changed those two bits in  the IP header to 11, to indicate  that it's experiencing congestion.
* 收方设置发给发方的TCP-**ACK**包中的**TCP头的 ECN Echo字段【ECE】=1** it sets a bit in  the TCP header of the acknowledgement packet  it sends back to the sender.  That bit’s known as the ECN Echo  field, the ECE field
  * 接收到发方的IP数据包（其中 `ECN=11`，经历拥塞）后，收方设置ACK包的TCP头 `ECE=1`，
    * 告诉发送方，"我在你发送的一个数据包中遇到了一个经历拥塞的标记” So it's telling the sender, “I got  a Congestion Experienced mark in one of  the packets you sent”.

---

### CWR：拥塞窗口减少位

![](/static/2021-04-09-11-18-51.png)

* **发方在ACK TCP头中看到 `ECN Echo （ECE） =1`，意识到先前发送的数据导致路由器（网络）经历了拥塞，且收方将这一信息/标记反馈了回来** When that packet gets to the sender,  the sender sees this bit in the  TCP header, the ECN Echo bit set  to one, and it realises that the  data it was sending  caused a router on the path to  set the ECN Congestion Experienced mark,  which the receiver has then fed back  to it.
* 随后，<font color="deeppink">发方，减少拥塞窗口</font>
  * **假设，模拟有一个包丢失**，并采取改变拥塞窗口的行为（模拟Reno/Cubic等）it reduces its congestion window.  It acts as-if a packet had been  lost, in terms of how it changes  its congestion window.
    * Reno - 窗口减半
    * Cubic - 窗口减至原来的70%，并通过 Cubic equation改变窗口大小
* <font color="deeppink">最后，发方在下一个发出的TCP段的头中设置 `CWR=1` 位</font>
  * **拥塞窗口减少位 Congestion Window Reduced Bit**
  * 告诉网络和接收方它已经完成了窗口减少 tell the  network and the receiver that congestion window already reduced

:orange: 显式拥塞通知 vs 无显式拥塞通知

* 先前，丢包（队列溢出，3个冗余ACK，超时机制）
  * TCP减少拥塞窗口，重传数据包，造成时延
* 应用显式拥塞通知ECN
  * TCP（IP包中），，收方设置 ECE bit
  * 发方，意识到网络拥塞，减少窗口，模拟丢包情况
  * 发方下一个TCP包设置 `CWR=1`，通知网络&收方窗口已减少
* <font color="red">因此，显式拥塞通知，没有实际丢包，只通过拥塞经历标记来表示网络繁忙，不需要重传，队列不会满，所以延迟更低</font> So there's no actual packet loss  here, there’s just a mark to indicate  that the network was getting busy.  So it doesn't have to retransmit data,  and this happens before the queue is  full, so you get lower latency.

---

## ECN特性

![](/static/2021-04-09-11-52-19.png)

:orange: 所以ECN是一种**允许TCP在丢包之前对拥塞做出反应的机制** So ECN is a mechanism to allow  TCP to react to congestion before packet  loss occurs.

* **它允许网络中的【路由器】在队列溢出之前发出拥塞信号** It allows routers in the network to  signal congestion before the queue overflows.

:orange: **无关TCP拥塞协议，算法的选择** independent of how TCP then responds,  whether it follows Reno or Cubic or  Vegas that doesn't really matter,

* 只需要一个拥塞经历标记，作为需要减少窗口的指示 an indication that it needs to slow  down

:orange: TCP如对指示（ECN bit）做出响应

* <font color="red">发方降低速率，队列会清空，路由器停止标记数据包，不会造成任何数据包的丢失</font> if TCP reacts to the ECN Echo bit going  back, and the sender reduces its rate,  the queues will empty, the router will  stop marking the packets, and everything will  settle down at a slightly slower rates  without causing any packet loss
* **这一机制将与TCP Reno & Cubic有相同的吞吐量，但延迟更低(队列更小，提前响应拥塞**)，react earlier,  so you have smaller queues and lower  latency.  And this gives you the same throughput  as you would with TCP Reno or  TCP Cubic, but with low latency, 
  * 适合 real-time applications。 which means it's better for competing video  conferencing or gaming traffic.

:candy: QUIC & RTP协议也有类似ECN扩展 ECN extensions also exist in QUIC and RTP

* 实现相同目标，低延迟协作

## ECN部署问题&发展：ECN & TCP - Deployment

![](/static/2021-04-09-12-10-15.png)

:orange: 采取ECN的端点，back-off，能在网络过载（队列溢出）前降低发送速率，实现良好拥塞控制，良好吞吐量，低延迟 And if the endpoints believe it,  if they back off,  they reduce their sending rate before the  network is overloaded, and we end up  in a world where h we still  achieve good congestion control, good throughput,  but with lower latency.

:orange: **不过为了部署ECN，需要做出改变**。

* **需要改变端点，改变终端系统，支持IP头中的这些位**，并且把对ECN的支持添加到TCP中 In order to deploy ECN, though,  we need to make changes. We need  to change the endpoints, to change the  end systems, to support these bits in  the IP header, and to support,  to add support for this into TCP.
* 而且我们**需要更新路由器，当数据包开始超载时，要实际标记数据包**。
 And we need to update the routers,  to actually mark the packets when they're  starting to get overloaded.

> 更新端点到现在已经做得差不多了。 我想每一个TCP的实现，在过去15-20年左右的时间里实现的，都是支持ECN的，现在，大部分的TCP实现都是默认开启的。Updating the end points has pretty much  been done by now.  I think  every TCP implementation,  implemented in the last 15-20 years or  so, supports ECN, and these days,  most of them have it turned on  by default.  
> 而我觉得我们其实要感谢苹果。 ECN在很长一段时间内都是被实现的，但默认情况下是关闭的，因为在20多年前，一些老的防火墙出现了问题，对它的反应很不好。And I think we actually have Apple  to thank for this.  ECN, for a long time, was implemented  but turned off by default, because there’d  been problems with some old firewalls which  reacted badly to it, 20 or so  years ago.
> 而且，相对来说，最近，苹果公司决定，他们想要这些更低的延迟优势，他们认为应该部署ECN。所以他们开始在iPhone中默认开启它。 他们有点，按照一个有趣的方法。 在iOS 9，随机子集 5％的iPhone将打开ECN的一些连接。 他们测量了发生了什么. 他们发现，在绝大多数情况下 这工作正常，偶尔它会失败。 他们会打电话给网络运营商，谁的网络出现了问题，他们会说："你的网络不能和iPhone一起使用，目前有5%的iPhone不能很好地工作，但我们会增加这个数字，也许你应该修复它"。 然后，一年之后，当iOS 10出来的时候，他们做到了这个50%的连接由iPhone进行。然后一年后，对于所有的连接。 而一个受欢迎的厂商打电话给网络运营商连接，对让他们修复设备的影响是惊人的。 而结果是，ECN现在在手机中被广泛默认启用，网络似乎也支持得很好。And, relatively recently, Apple decided that they  wanted these lower latency benefits, and they  thought ECN should be deployed. So they  started turning it on by default in  the iPhone.  And they kind-of followed an interesting approach.  In that for iOS nine, a random  subset of 5% of iPhones would turn  on ECN for some of their connections.  And they measured what happened. And they  found out that in the overwhelming majority  of cases this worked fine, and occasionally  it would fail.  And they would call up the network  operators, who's networks were showing problems,  and they would say “your network doesn't  work with iPhones; and currently it's not  working well with 5% of iPhones but  we're going to increase that number,  and maybe you should fix it”.  And then, a year later, when iOS  10 came out, they did this 50%  of connections made by iPhones. And then  a year later, for all of the  connections.  And it's amazing what impact a  popular vendor  calling up a network operator connect can  have on getting them to fix the  equipment.  And, as a result,  ECN is now widely enabled by default  in the phones, and the network seems  to support it just fine.

:orange:大部分路由器也支持ECN，默认应用的比较少 Most of the routers also support ECN.  Although currently relatively few of them seem  to enable it by default.

> 因此，大多数端点现在都处于发送 ECN 启用流量的阶段，并且能够对 ECN 标记作出反应，但是大多数网络目前并没有设置 ECN 标记。我认为，这种情况正开始改变。最近的一些 DOCSIS，即电缆调制解调器标准，已经开始支持您的 ECN。我们开始看到电缆调制解调器，电缆互联网连接，默认启用 ECN。而且，我们开始看到3GPP 的兴趣，它是移动电话标准机构，能够在5 g、6 g 网络中实现这一功能，所以我认为它正在到来。但这需要时间。而且，我认为，随着 ECN 的逐渐部署，我们将逐渐看到网络延迟的减少。这不会是戏剧性的。它不会突然改变网络的运行方式，但希望在未来5年或10年内，随着 ECN 得到更广泛的部署，我们将逐渐看到延迟减少 So most  of the endpoints are now  at the stage of sending ECN enabled  traffic, and are able to react to  the ECN marks, but most of the  networks are not currently setting the ECN  marks.  This is, I think, starting to change.  Some of the recent DOCSIS, which is  the cable modem standards, are starting to  support you ECN. We’re starting to see  cable modems, cable Internet connections, which enable  ECN by default.  And, we're starting to see interest from  3GPP, which is the mobile phone standards  body to enable this in 5G,  6G, networks, so I think it's coming.  but it's going to take time.  And, I think, as it comes,  as ECN gradually gets deployed, we’ll gradually  see a reduction in latency across the  networks. It’s not going to be dramatic.  It's not going to suddenly transform the  way the network behaves, but hopefully over  the next 5 or 10 years we’ll  gradually see the latency reducing as ECN  gets more widely deployed.

# Outline:Light Speed

传播延迟的影响。impact of propagation delays.

* 无关delay，loss

## 传播（介质）时延：Light Speed？

![](/static/2021-04-09-12-27-07.png)

有两个因素会影响延迟。 there are two factors which impact that  latency.

* **首先是数据包在网络内各个路由器排队的时间 【队列时延**】The first is the time packets spent  queued up at various routers within the  network
  * 这在很大程度上受**TCP拥塞控制的选择（收敛快慢**），this is highly influenced  by the choice of TCP congestion control
  * 以及**是否启用Explicit Congestion Notification（不启用的话，强制队列溢出，造成丢包重传时延**）的影响 whether Explicit Congestion Notification is enabled  or not.
* <font color="deeppink">数据包在路由器之间的链路上实际传播所需的时间。</font> time it  takes the packets to actually propagate down  the links between the routers.
  * **这取决于信号在传输介质中传播的速度** This depends  on the speed at which the signal  propagates down the transmission medium.
  * 如果你使用光纤来传输数据包，这取决于光在光纤中传播的速度 If you're using an optical fibre to  transmit the packets, it depends on the  speed at which the light propagates through  the fibre.
  * 如果你在电缆中使用电信号，这取决于电场在电缆中传播的速度。If you're using electrical signals in a  cable, it depends on the speed at  which electrical field propagates down the cable.
  * 如果你使用的是无线电信号，那就要看光速，无线电信号在空气中传播的速度 And if you're using radio signals,  it depends on the speed of light,  the speed at which the radio signals  propagate through the air.

## 减少传播时延：Reducing Propagation Delay

### 物理减少

![](/static/2021-04-09-12-50-13.png)

* **物理上较短的链路具有较低的传播延迟**。
  * 一个数据包在长距离链路上传输所需的很多时间只是信号沿链路实际传输所需的时间。如果你把链路做得更短，就会花费更少的时间 physically shorter links  have lower propagation delays.  A lot of the time it takes  a packet to get down a long  distance link is just the time it  takes the signal to physically transmit along  the link. If you make the link  shorter it takes less time.
* **通过铺设新的电缆，沿着热门目的地之间的大循环路线，显著减少延迟** Significant reduction in latency by laying new cables that follow great circle routes between popular destinations
  * 而也许并不那么明显的是，你实际上可以在某些路径中获得显著的延迟优势，因为现有的网络链路走的是相当间接的路线 And what is perhaps not so obvious,  though, is that you can actually get  significantly significant latency benefits in certain paths,  because the existing network links follow quite  indirect routes.
  * 例如，如果你观察网络连接的路径，如果你将数据从欧洲发送到日本。很多时候，这些数据从欧洲，穿过大西洋，到达，比如纽约或波士顿，或者类似的地方，穿过美国到旧金山，或者洛杉矶，或者西雅图，或者沿着这些线路的某个地方，然后从那里，通过电缆穿过太平洋到达日本。或者，它从欧洲经过地中海，苏伊士运河和中东，然后穿过印度，等等，直到反过来到达日本。但这两条路都不是特别直接的。事实证明，从欧洲到日本，还有一条更直接、更快捷的路线，那就是铺设一条光纤，穿过加拿大北部，穿过北冰洋，穿过白令海峡，再经过俄罗斯直接到达日本，这条路线就是从欧洲到日本的西北水道。它更接近于环球大圆路线，而且比目前网络所采用的路线要短得多。而且，从历史上看，这是不可能的，因为北极的冰层。但是，随着全球变暖，西北水道现在已经有足够的时间无冰，人们开始谈论沿着这条路线铺设光纤，因为他们可以得到一个明显的延迟减少，对于一定数量的交通，只是沿着物理较短的路线。For example, if you look at the  path the network links take, if you're  sending data from Europe to Japan.  Quite often, that data goes from Europe,  across the Atlantic to, for example,  New York or Boston, or somewhere like  that, across the US to  San Francisco, or Los Angeles, or Seattle,  or somewhere along those lines, and then  from there, in a cable across the  Pacific to Japan.  Or alternatively, it goes from Europe through  the Mediterranean, the Suez Canal and the  Middle East, and across India, and so  on, until it eventually reaches Japan the  other way around. But neither of these  is a particularly direct route.  And it turns out that there is  a much more direct, a much faster  route, to get from Europe to Japan,  which is to lay a an optical  fibre  through the Northwest Passage, across Northern Canada,  through the Arctic Ocean, and down through  the Bering Strait, and past Russia to  get directly to Japan. It's much closer  to the great circle route around the  globe, and it's much shorter than the  route that the networks currently take.  And, historically, this hasn't been possible because  of the ice in the Arctic.  But, with global warming, the Northwest Passage  is now ice-free for enough of the  year that people are starting to talk  about laying optical fibres along that route,  because they can get a noticeable latency  reduction, for certain amounts of traffic,  by just following the physically shorter route.

---

### 选取的介质对传播时延的影响

![](/static/2021-04-09-12-58-10.png)

另一个影响传播延迟的因素是**传输介质中的光速**。Another factor which influences the propagation delay  is the speed of light in the  transmission media.

* **如果你使用无线电链路发送数据 或者在真空中使用激光，那么这些数据在真空中以光速传播**。 也就是每秒3亿米的速度 Now, if you're sending data using radio  links,  or using lasers in a vacuum,  then these propagate at the speed of  light in the vacuum.  Which is about 300 million meters per  second.
* 不过，**光在光纤中的速度，是比较慢的**。光在光纤中传播的速度，也就是光在玻璃中传播的速度，只有大约20万公里/秒，2亿米/秒。所以它是真空中传播速度的三分之二 The speed of light in optical fibre,  though, is slower. The speed at which  light propagates down that down a fibre,  the speed at which light propagates through  glass, is only about 200,000.  kilometres per second, 200 million meters per  second. So it’s about two thirds of  the speed at which it propagates in  a vacuum.

> 这就是SpaceX正在部署的StarLink等系统的原因。 这些系统的理念是，不是通过光纤发送互联网信号，而是将信号发送到100英里或几百英里高的卫星上，然后它们在星座中的不同卫星之间绕行，在低地球轨道上，然后向下到达目的地附近的接收器。 And this is the reason for systems  such as StarLink, which SpaceX is deploying.  And the idea of these systems is  that, rather than sending the Internet signals  down an optical fibre,  you send them 100, or a couple  of hundred miles, up to a satellite,  and they then go around between various  satellites in the constellation, in low earth  orbit, and then down to a receiver  near the destination.
> **通过真空传播，而不是通过光纤，真空中的光速明显更快，比光纤中的光速快50%左右，这可以减少延迟**。 而且估计显示，如果你有足够大的卫星星座，SpaceX公司计划部署大约4000颗卫星，我相信，如果仔细的路由，你可以得到大约40、45、50%的延迟减少。 只是因为信号是通过无线电波，通过卫星间的激光链路传输，而卫星间的激光链路是在真空中，而不是通过光缆传输。 只是因为这两种介质之间的光速不同。And by propagating through vacuum, rather than  through optical fibre, the speed of light  in vacuum is significantly faster, it's about  50% faster than the speed of light  in fibre, and this can reduce the  latency.  And the estimates show that if you  have a large enough constellation of satellites,  and SpaceX is planning on deploying around  4000 satellites, I believe, and with careful  routing, you can get about a 40,  45, 50% reduction in latency.  Just because the signals are transmitting via  radio waves, and via inter-satellite laser links,  which are in a vacuum, rather than  being transmitted through a fibre optic cable.  Just because of the differences in the  speed of light between the two mediums.

幻灯片上的链接指向 StarLink 网络的一些模拟，这些模拟试图演示这是如何工作的，它如何实现两条紧密沿着大圆路线的网络路径，以及它如何通过使用卫星来减少延迟。And the link on the slide points  to some simulations of the StarLink network,  which try and demonstrate how this would  work, and how it can achieve  both network paths that closely follow the  great circle routes, and  how it can reduce the latency because  of the use of satellites.

## 为什么致力于物理层面降低时延？

![](/static/2021-04-09-13-10-56.png)

人们显然在用一些相当**极端**的方式来降低延迟。 我们在上一部分谈到的是使用**ECN标记**，通过减少队列数量来降低延迟。而这只是一个配置的改变，是对一些路由器的软件改变。而在我看来，**这似乎是一种合理的降低延迟的方法** what we see is that people  are clearly going to some quite extreme   lengths to reduce latency.  I mean, what we spoke about in  the previous part was the use of  ECN marking to reduce latency by reducing  the amount of queuing. And that's just  a configuration change, it’s a software change  to some routers. And that seems to  me like a reasonable approach to reducing  latency.

* 但有些人显然愿意花大力气发射数千颗卫星，或者可能是稍微不那么极端的情况下，通过北冰洋铺设新的光纤。 那么人们为什么要这么做呢？为什么人们如此关心减少延迟，以至于愿意花费数十亿美元发射数千颗卫星，或者运行新的海底电缆来做这件事 But some people are clearly willing to  go to the effort of  launching thousands of satellites, or  perhaps the slightly less extreme case of  laying new optical fibres through the Arctic  Ocean.  So why are people doing this? Why  do people care so much about reducing  latency, that they're willing to spend billions  of dollars launching thousands of satellites,  or running new undersea cables, to do  this?

:orange: 人们为什么要这么做？高频率的交易。 竞争者认为他们可以赚更多钱，通过与竞争对手相比，通过降低几毫秒的延迟。他们决定是否值得， 不管如何，希望我们的其他人也能得到更低的延迟。 Why are people doing this? High frequency  share trading.  Share traders believe they can make a  lot of money, by getting a few  milliseconds worth  of latency reduction compared to their competitors. But the end result may be,  hopefully, that we will get lower latency  for the rest of us as well.

