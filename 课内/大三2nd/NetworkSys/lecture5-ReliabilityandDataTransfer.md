# Reliability and Data Transfer

# 丢包：Packet Loss in the Internet

![](/static/2021-02-28-20-12-14.png)

* 互联网中的丢包问题 packet loss in the internet
* 互联网提供尽力服务是什么意思 best effort traffic
* 端到端的设计讨论 end to end argument
* 以及互联网设计中固有的及时性与可靠性的权衡 timeliness vs reliability trade-off

---

![](/static/2021-02-28-20-13-08.png)

* 因特网采取尽力而为包传输网络 - 不可靠 the Internet is a best effort packet  delivery network.  This means that it’s unreliable by design.
* **传输过程中IP包可能丢失，延误，重组，损坏** IP packets can be lost, delayed,  reordered, or corrupted in transit
  * 这被认为是一种特性，而不是一个错误。一个不能传送数据包的网络应该丢弃它 And this  is regarded as a feature, rather than  a bug. A network that can’t deliver  a packet is supposed to discard it.
  * **数据包丢失或丢弃的原因有很多** There are many reasons why a packet  can get lost or discarded
    * 它可能是由于传输错误，无线干扰的电噪声破坏了传输中的数据包，使数据包无法读取 It could  be due to a transmission error,  where electrical noise of wireless interference corrupts  the packet in transit, making the packet  unreadable.
    * 也可能是因为太多的流量到达网络中的某个中间链路，所以中间路由器的缓冲空间用完了。如果流量从几个不同的传入链路到达路由器，但都是去同一个目的地，所以它到达的速度比它能传递的速度快，那么一个数据包队列就会建立起来，等待传输。 如果这种情况持续下去，队列可能会增长得太多，以至于路由器的内存耗尽，别无选择，只能丢弃数据包  Or it could be because too much  traffic is arriving at some intermediate link  in the network, so an intermediate router  runs out of buffer space. If traffic  is arriving at a router from several  different incoming links, but all going to  the same destination, so it’s arriving faster  than it can be delivered, a queue  of packets will build up, waiting for  transmission. If this situation persists, the queue might  grow so much that a router runs  out of memory, and has no choice  but to discard the packets.
    * 或者数据包可能因为链路故障而丢失。或者是路由器的错误。 或者是其他原因 Or packets could be lost because of  a link failure. Or a router bug.  Or for other reasons.
* **丢包发生的频率差异很大** How often this happens varies significantly.
  * 丢包率取决于链路的类型。The packet loss rate depends on the  type of link.
  * 例如，无线链路往往比有线链路更不可靠。通过无线链路（如WiFi或4G）发送的数据包在传输过程中很有可能因噪音、干扰或交叉流量而损坏。 在以太网或光纤链路上，这种情况不太可能发生。Wireless links tend to be less reliable  than wired links, for example. It’s reasonably  likely that packet sent over a wireless  link, such as WiFi or 4G,  will be corrupted in transit due to  noise, interference, or cross traffic.  This is very unlikely on an Ethernet  or optical fibre link.
  * 数据包丢失率还取决于基础设施的总体质量和健全程度。基础设施发达和维护良好的国家往往拥有可靠的互联网链接；基础设施不健全或能力较低的国家往往出现更多问题。The packet loss rate also depends on  the overall quality and robustness of the  infrastructure. Countries with well developed and well  maintained infrastructure tend to have reliable Internet  links; countries with less robust or lower  capacity infrastructure tend to see more problems.
    * 而损耗率则取决于协议。有些协议有意地试图将链路推到容量，造成暂时的过载，因为它们试图找到极限，因为它们试图找到它们能达到的最大传输速率。 TCP和QUIC在很多情况下都会这样做，这取决于所使用的拥塞控制算法，我们将在第6讲中看到。 其他应用，如电话或视频会议，往往在它们可以发送的数据量上有一个上限。And the loss rate depends on the  protocol. Some protocols intentionally try to push  links to capacity, causing temporary overload as  they try to find the limit,  as they try to find the maximum  transmission rate they can achieve.  TCP and QUIC do this in many  cases, depending on the congestion control algorithm  used, as we’ll see in lecture 6.  Other applications, such as telephony or video  conferencing, tend to have an upper bound  in the amount of data they can  send.
  * **不过不管是什么原因，一些数据包的丢失是不可避免的**。Whatever the reason, though, some packet loss  is inevitable.
* 传输层需要认识到这一点。 它**必须检测数据包丢失**。而且，**如果应用程序需要可靠性，它必须重传或以其他方式修复任何丢失的数据**。The transport layer needs to recognise this.  It must detect packet loss. And,  if the application needs reliability, it must  retransmit or otherwise repair any lost data.

## 端到端协议：End to end argument

![](/static/2021-02-28-20-23-45.png)

互联网提供尽最大努力数据包传送 --- 端到端讨论 That the Internet provides best effort packet  delivery is a result of the end-to-end  argument.

* 端到端协议考虑的是将功能放在网络内部好还是放在端点好 The end-to-end argument considers whether it’s better  to place functionality inside the network or  at the end points.
* 例如，**我们可以尝试使网络可靠地传送数据包，而不是提供最大努力的传送**。For example, rather than provide best effort  delivery, we could try to make the  network deliver packets reliably
  * 我们可以设计一些方法来检测特定链路上的数据包丢失，并要求将丢失的数据包【在网络中的某个地方】进行本地重传。We could design  some way to detect packet loss on  a particular link, and request that the  lost packets be retransmitted locally, somewhere within  the network.
  * 而事实上，有些【网络链路】也是这样做的。 **例如，在WiFi网络中，基站确认从客户端收到的数据包，并要求重新发送任何损坏的数据包，以纠正错误**。And, indeed, some network links do this.  In WiFi networks, for example, the base  station acknowledges packets it receives from the  clients, and requests any corrupted packets are  re-sent, to correct the error.
  * <font color="deeppink">问题是，除非这种机制一直都是100%完美的，否则【终端系统】仍然需要检查数据是否被正确接收，并且在出现问题的情况下仍然需要一些重传数据包的方式。</font> The problem is, that unless this mechanism  is 100% perfect all the time,  then end systems will still need to  check if the data has been received  correctly, and will still need some way  of retransmitting packets in the case of  problems.
    * 如果终端有这个能力，又何必去做网内转发和维修呢？And if they’ve got that, why bother  with the in-network retransmission and repair?
* 通常情况下，如果你在网络路由器中添加功能，它们最终会重复**网络终端无论如何也需要提供的功能**。Often times, if you add features into  the network routers, they end up duplicating  functionality that the network endpoints need to  provide anyway.
  * 也许在网络中增加功能的性能收益非常大，值得。Maybe the performance benefit of adding features  to the network is so big that  it’s worth while.
  * **但很多时候，正确的做法是保持网络简单。 省略一切可以由终端完成的事情。而偏向于简单，而不是绝对的最佳性能**。But often, the right thing to do  is to keep the network simple.  Omit anything that can be done by  the endpoints. And favour simplicity over the  absolute optimal performance.

:orange: 端到端的讨论是互联网的定义原则之一。在可能的情况下，这仍然是一个好办法。如果可以的话，保持网络的简单性 the end-to-end argument is one of the  defining principles of the Internet. And I  think it’s still a good approach to  take, when possible. Keep the network simple,  if you can

## 时效性与可靠性的权衡: Timeliness vs Reliability Trade-off

![](/static/2021-02-28-20-37-05.png)

无论重传丢失的数据包是发生在端点之间还是网络内部，都需要时间。Irrespective of whether retransmission of lost packets  happen between the endpoints or within the  network, it takes time.

这就导致了网络设计的根本取舍,权衡可靠信 & 时效性。This leads to a fundamental trade-off in  the design of the network.

* **如果要保证连接的可靠性，就不能保证及时性**。If a connection is to be reliable,  it cannot guarantee timeliness.
  * 不可能建立绝对完美的网络链路，永不丢弃或损坏数据包。总有一些风险，数据丢失了，需要重传。It’s not possible to build absolutely perfect  network links, that never discard or corrupt  packets. There’s always some risk that the  data is lost and needs to be  retransmitted. 
  * 而重传一个数据包总是需要时间，所以会破坏传输的及时性。And retransmitting a packet will always  take time, and so disrupt the timeliness  of the delivery.
* **而同样，如果要保证连接的及时性，也不能保证可靠性**And similarly, if a connection is to  be timely, it cannot guarantee reliability.

:orange: 像UDP这样的协议是**及时的，但并不试图做到可靠**。Protocols like UDP are timely but don’t  attempt to be reliable. 

* 它们发送数据包，如果数据包丢失，它们就会丢失。They send packets,  and if they get lost, they get  lost.

:orange: 另一方面，TCP和QUIC的目标是可靠。它们发送数据包，如果数据包丢失，它们会重新发送。TCP and QUIC, on the other hand,  aim to be reliable. They send the  packets, and if they get lost,  they retransmit them.

* 如果重传的数据丢失了呢？他们再试一次，直到数据最终到达。And if the retransmission gets lost? They  try again, until the data eventually arrives.
* 本讲座的第3部分 ---，这会导致行头阻塞，使协议的及时性降低 As we’ll see in part 3 of  this lecture, this causes head of line  blocking, making the protocol less timely.

:orange: 而其他的协议，比如第7讲中会讲到的实时传输协议RTP，或者部分可靠版本的流控制传输协议SCTP，都是以中间权衡为目标。And other protocols, such as the Real-time  Transport Protocol, RTP, that I’ll talk about  in lecture 7, or the partially reliable  version of the Stream Control Transport Protocol,  SCTP, aim for a middle ground.

* 他们试图纠正一些，但不是所有的传输错误。它们试图在及时性和可靠性之间达到一种平衡，一种中间地带。They try to correct some, but not  all, of the transmission errors. The try  to achieve a balance, a middle-ground,  between timeliness and reliability.

---

![](/static/2021-02-28-21-40-37.png)

不同的协议之所以存在，是因为不同的应用会做出不同的权衡。有些应用喜欢及时性，有些应用喜欢可靠性。The different protocols exist because different applications  make different trade-offs. Some applications prefer timeliness,  some prefer reliability.

* 对于网页浏览、电子邮件或消息等应用，你希望收到所有的数据。For applications like web browsing, email,  or messaging, you want to receive all  the data.
* 如果我正在加载一个网站，我当然希望它能快速加载。但我更希望它能缓慢地加载，并且不被破坏，而不是在快速加载的同时缺少一些部分。  If I’m loading a web  site, I’d like it to load quickly,  sure. But I prefer for it to  load slowly, and be uncorrupted, rather than  load quickly with some parts missing.
* 不过对于Zoom这样的视频会议工具来说，取舍就不一样了。如果我在和别人对话，比起画质的完美，低延迟更重要。 对于游戏来说，可能也是如此。For a video conferencing tool, like Zoom,  though, the trade-off is different. If I’m  having a conversation with someone, it’s more  important that the latency is low,  than the picture quality is perfect.  The same may be true for gaming.

:orange: 而这对我们设计网络的方式有影响。And this has implications for the way  we design the network.

* 这意味着IP层需要不可靠。它需要是一个尽力的网络。It means that the IP layer needs  to be unreliable. It needs to be  a best effort network.
* 如果IP层不可靠，像TCP和QUIC这样的协议可以坐在上面重传数据包，使其可靠。传输协议可以使一个不可靠的网络变成一个可靠的网络。If the IP layer is unreliable,  protocols like TCP and QUIC can sit  on top and retransmit packets to make  it reliable. A transport protocol can make  an unreliable network into a reliable one.
* 但是如果IP层是可靠的，如果IP层自己重传数据包，那么网络、应用、传输协议都无法挽回。But if the IP layer is reliable,  if the IP layer retransmits packets itself,  then the network, the applications, the transport  protocols, can’t undo that.

# UDP不可靠传输：Unreliable Data Using UDP

UDP提供了一种不可靠的、无连接的数据报服务。UDP provides an unreliable, connectionless, datagram service.

![](/static/2021-03-01-00-27-36.png)

* **它只在IP层之上增加了两个功能：端口号和校验和** It adds only two features on top  of the IP layer: port numbers and  a checksum.

:orange: 校验和 - **用于检测数据包在传输过程中是否已经损坏**

* 如果是，该数据包将被接收方操作系统中的UDP代码丢弃，不会被送到应用程序。The checksum is used to detect whether  the packet has been corrupted in transit.  If so, the packet will be discarded by the UDP code in the operating  system of the receiver, and won’t be  delivered to the application.

:orange: **端口号决定了当UDP数据报到达目的地时，由哪个应用程序接收**

* 在创建套接字后, 它们由bind()设置 The port numbers determine what application receives  the UDP datagrams when they arrive at  the destination. They’re set by the bind()  call, once the socket has been created.

:orange: 互联网编号分配机构（IANA）维护着一个已知的UDP端口号列表，应该为特定的应用程序使用这些端口号 The Internet Assigned Numbers Authority, the IANA,  maintains a list of well-known UDP port  numbers which you should use for particular  applications. This is linked from the bottom  of the slide.

---

:orange: **UDP的作用非常小。它不提供可靠性，或排序，或拥塞控制**。 它只是将数据包传送给一个应用，而这个应用是绑定在一个特定的端口上的。UDP is very minimal. It doesn’t provide  reliability, or ordering, or congestion control.  It just delivers packets to an application,  that’s bound to a particular port.

* **大多数情况下，UDP是作为一个底层。 它是一个基础，在这个基础上建立更高层次的协议**。QUIC就是一个例子。其他的还有实时传输协议，和DNS协议 Mostly, UDP is used as a substrate.  It’s a base on which higher-layer protocols  are built. QUIC is an example of  this, as we discussed in the last  lecture. Others are the Real-time Transport Protocol,  and the DNS protocol, that we’ll talk  about later in the course.

## UDP套接字使用：Using UDP Datagrams

![](/static/2021-03-01-00-28-35.png)

UDP是无连接的。它没有客户端或服务器的概念，也没有在使用前建立连接的概念。UDP is connectionless. It’s got no notion   of clients or servers, or of establishing  a connection before it can be used.

:orange: To use UDP, you first create a  socket.

* 然后你调用bind()，选择该套接字监听传入数据报的本地端口。Then you call bind(), to choose the  local port on which that socket listens  for incoming datagrams.
* 如果你想在该套接字上接收数据报，就调用recvfrom()，如果你想发送数据报，就调用sendto()。 They you call recvfrom() if you want  to receive a datagram on that socket,  or sendto() if you want to send  a datagram.
* 不需要连接。不需要接受连接。只需要发送和接收数据。也许数据会被传送(如果未被破坏) You don’t need to connect. You don’t  need to accept connections. You just send  and receive data. And maybe that data  is delivered.
* 传输完成，关闭套接字 if finished, close the socket

:orange: **运行在UDP之上的协议，如QUIC，可能会增加对连接、可靠性、排序、拥塞控制等的支持，但UDP本身不支持这些**。Protocols that run on top of UDP,  such as QUIC, might add support for  connections, reliability, ordering, congestion control, and so  on, but UDP itself supports none of  this.

## 发送UDP数据报：Sending UDP Datagram

![](/static/2021-03-01-00-33-28.png)

要发送UDP数据报，使用sendto()函数。To send a UDP datagram, you use  the sendto() function.

* 类似于通过TCP连接发送数据的send()函数，similarly to the send() function
* 除了它需要两个额外的参数来指示数据报应该被发送的地址，以及该地址的大小。except that  it takes two additional parameters to indicate  the address to which the datagram should  be sent, and the size of that  address.

:orange: 当使用TCP时，你在一个绑定到本地地址和端口的套接字和一个在某个远程IP地址的特定端口上监听的服务器之间建立一个连接。而一旦建立了连接，所有的数据都会通过这个连接，到达同一个目的地。When using TCP, you establish a connection  between a socket, bound to a local  address and port, and a server listening  on a particular port on some remote  IP address. And once the connection is  established, all the data goes over that  connection, to the same destination.

* UDP不是这样的。 每次你调用sendto()时，都会指定目标地址。UDP is not like that.  Every time you call sendto(), you specify  the destination address
* 如果你想的话，你从UDP套接字发送的每个数据包都可以去不同的目的地。Every packet you send  from a UDP socket can go to  a different destination, if you want
* UDP没有连接的概念 There’s no notion of connections.

:orange: **可以在一个UDP套接字上调用connect()**

* 但令人困惑的是，它并没有创建一个连接。 you can call connect() on a  UDP socket, but confusingly it doesn’t create  a connection.
* 相反，它只是记住了你给它的地址，**所以你可以在以后调用send()，而不是sendto()，以节省每次都要指定地址**。 Rather, it just remembers the address you give it, so you can  call send(), rather than sendto() in future,  to save having to specify the address  each time.

## 接收UDP数据报：Receiving UDP Datagram

![](/static/2021-03-01-00-42-29.png)

To receive a UDP datagram, you call the `recvfrom()` function

* 像在TCP中使用的recv()调用一样，但它同样有两个额外的参数。
* 这两个参数允许它记录收到的数据报的地址，所以你可以在sendto()函数中使用它们来发送回复 This is like the recv() call you  use with TCP, but again it has  two additional parameters. These allow it to  record the address that the received datagram  came from, so you can use them  in the sendto() function to send a  reply.

:orange: 也可以调用recv()，而不是像TCP那样调用recvfrom()，它也能用，但它不会给你返回地址，所以不是很有用。You can also call recv(), rather than  recvfrom(), like with TCP, and it works,  but it doesn’t give you the return  address, so it’s not very useful.

:orange: **UDP重要的一点是，数据包在传输过程中可能会丢失、延迟或重新排序，而UDP并不试图从中恢复**。The important point with UDP is that  packets can be lost, delayed, or reordered  in transit, and UDP doesn’t attempt to  recover from this.

* 仅仅因为你发送了一个数据报，并不意味着它会到达。Just because you send a datagram,  doesn’t mean it will arrive.
* 如果数据报真的到达，也不一定会按照发送的顺序到达。 And if  datagrams do arrive, they won’t necessarily arrive  in the order sent.

## UDP装帧 & 可靠性：UDP Framing & Reliability

![](/static/2021-03-01-01-40-08.png)

与TCP不同的是，在TCP中，一次send()调用中写入连接的数据可能会在接收器处被分割成多个read()调用，**而一次UDP发送则正好产生一个数据报**。
Unlike TCP, where data written to a  connection in a single send() call might  end up being split across multiple read()  calls at the receiver, a single UDP  send generates exactly one datagram.

* 如果要传递的话，单次调用sendto()发送的数据将由单次调用recvfrom()来传递。UDP不拆分消息。If it’s delivered at all, the data  sent by a single call to sendto()  will be delivered by a single call  to recvfrom(). UDP doesn’t split messages.
* 但UDP在其他方面是不可靠的。 数据报在传输过程中可能会丢失、延迟、重新排序或重复。But UDP is otherwise unreliable.  Datagrams can be lost, delayed, reordered,  or duplicated in transit.
  * 用sendto()发送的数据可能永远不会到达。 或者它可能不止一次到达 Data sent with sendto() might never arrive.  Or it might arrive more than once
  * 或者在连续调用sendto()时发送的数据可能不按顺序到达，后面发送的数据会先到达。Or data sent in consecutive calls to  sendto() might arrive out of order,  with data sent later arriving first.
  * UDP并没有试图纠正这些事情。UDP doesn’t attempt to correct any of  these things.

:orange:建立在UDP之上的协议可以纠错 The protocol built on UDP is responsible for correcting the order, detecting duplicates, and repairing loss – if necessary

* 例如，我们看到**QUIC**将**数据包序列号和确认帧**添加到它在UDP数据包内发送的数据中。For example, we saw that QUIC adds  packet sequence numbers and acknowledgement frames to  the data it sends within UDP packets.
  * 这可以让它把数据放回正确的顺序，并重传任何丢失的数据包。This lets it put the data back  into the correct order, and retransmit any  missing packets.
* 但没有要求在UDP上运行的协议是可靠的。But there’s no requirement that the protocol  running over UDP is reliable.
* 视频会议应用中使用的**RTP**，即实时传输协议，它在发送的UDP数据报里面放了**序列号和时间戳**，所以可以知道是否有数据丢失，它可以隐瞒丢失或重建数据包播放时间，**但它一般不会重传丢失的数据**。RTP, the Real-time Transport Protocol, that’s used  for video conferencing apps, puts sequence numbers  and timestamps inside the UDP datagrams it  sends, so it can know if any  data is missing, and it can conceal  loss or reconstruct the packet playout time,  but it generally doesn’t retransmit missing data.

:candy: UDP给了应用程序选择建立可靠性的权利，如果它想要的话。 但它并不要求应用程序可靠地传送数据。UDP gives the application the choice of  building reliability, if it wants it.  But it doesn’t require that the applications  deliver data reliably.

---

![](/static/2021-03-01-02-17-34.png)

使用UDP的应用程序需要组织他们发送的数据，所以如果一些数据丢失，它是有用的。Applications that use UDP need to organise  the data they send, so it’s useful  if some data is lost.

* 不同的应用根据自己的需求，以不同的方式进行。Different applications do this in different ways,  depending on their needs.
* 例如，QUIC在连接中把数据组织成子流，并重传丢失的数据。QUIC, for example, organises the data into  sub-streams within a connection, and retransmits missing  data.
* 视频会议应用往往会做一些不同的事情。Video conferencing applications tend to do something  different.
  * 视频压缩的工作方式，是编解码器偶尔发送**完整的视频帧，被称为I帧，索引帧**，每几秒钟。而在这些之间，它只发送与前一帧的差异，称为P帧，预测帧。The way video compression works, is that  the codec sends occasional full frames of  video, known as I-frames, index frames,  every few seconds. And in between these  it sends only the differences from the  previous frame, known as P-frames, predicted frames.
  * 在视频通话中，常见的情况是背景不变，而人在前景移动，所以每次很多画面都是一样的。通过只发送差异，视频压缩可以节省带宽。In a video call, it’s common for  the background to stay the same,  while the person moves in the foreground,  so a lot of the frame is  the same each time. By only sending  the differences, video compression saves bandwidth.
  * 但这会影响应用程序如何处理不同的数据报。But this affects how the application treats  the different datagrams.
  * 如果一个包含预测帧(P frame)的UDP数据报丢失了，那就没那么重要了。 你会在一帧视频中得到一个小故障。If a UDP datagram containing a predicted  frame is lost, it’s not that important.  You’ll get a glitch in one frame  of video.
  * 但如果一个包含索引帧或索引帧的一部分的UDP数据报丢失了，那么这就更重要了，因为接下来几秒钟的视频价值是根据这个索引帧来预测的。丢失一个索引帧会破坏几秒钟的视频。But if a UDP datagram containing an  index frame, or part of an index  frame, is lost, then that matters a  lot more because the next few seconds  worth of video are predicted based on  that index frame. Losing an index frame  corrupts several seconds worth of video.
  * 出于这个原因，许多通过UDP运行的视频会议应用程序试图确定丢失的数据包是否包含索引帧。**而且它们会尝试重传索引帧，但不会重传预测帧**。For this reason, many video conferencing apps  running over UDP try to determine if  missing packets contained an index frame or  not. And they try to retransmit index  frames, but not predicted frames.

:orange: 但重要的是，UDP为应用程序提供了灵活性，使其发送的一些数据报不可靠，同时试图可靠地传送其他数据报。What’s important though, is that UDP gives  the application flexibility to be unreliable for  some of the datagrams it sends,  while trying to deliver other datagrams reliably.

* 在TCP中，没有这种灵活性。You don’t have that flexibility with TCP.
* UDP比较难用，因为它提供的服务很少，无法帮助你的应用，**但它更灵活，因为你可以在UDP之上准确地构建你需要的服务**。UDP is harder to use, because it  provides very few services to help your  application, but it’s more flexible because you  can build exactly the services you need  on top of UDP.

## UDP灵活性：UDP Sequencing and Reliability

![](/static/2021-03-01-02-27-56.png)

从根本上说，UDP并没有做出任何提供排序、可靠性、定时恢复或拥塞控制的尝试。Fundamentally, UDP doesn’t make any attempt to  provide sequencing, reliability, timing recovery, or congestion  control.

* 它只是在尽力的基础上传送数据报 It just delivers datagrams on a best  effort basis.  
* 它可以让你建立任何类型的传输协议，在UDP包内运行 It lets you build any type of  transport protocol you want, running inside UDP  packets.

:orange: 也许该传输协议有序列号和确认，并重传部分或全部丢失的数据包。Maybe that transport protocols has sequence numbers  and acknowledgements, and retransmits some or all  of the lost packets.

:orange: 也许，它反而会使用纠错码，让一些数据包得到修复而不重传。Maybe, instead, it uses error correcting codes,  to allow some of the packets to  be repaired without retransmission.

:orange: 也许它包括时间戳，所以接收器可以仔细地重建时间。 也许它包含其他信息。Maybe it includes timestamps, so the receiver  can carefully reconstruct the timing.  Maybe it contains other information.

:candy: 关键是，UDP给你带来了**灵活性**，**但代价是必须自己实现这些功能。以增加复杂性为代价**。The point is that UDP gives you  flexibility, but at the cost of having  to implement these features yourself. At the  cost of adding complexity.

## UDP程序编写指南：Guidelines for writing UDP app

![](/static/2021-03-01-02-38-36.png)

在编写一个基于UDP的协议或基于UDP的应用程序时，需要考虑很多问题。There’s a lot to think about when  writing a UDP-based protocol or a UDP-  based application.

* 如果你使用的传输协议，比如QUIC或比如RTP，是在UDP上运行的，那么该协议的设计者已经做出了这些决定，并且会给你一个可以使用的库。If you use a transport protocol,  like QUIC or like RTP, that runs  over UDP, then the designers of that  protocol have made these decisions, and will  have given you a library you can  use.
* 如果不是，如果你正在设计自己的协议，在UDP上运行，那么IETF已经写了一些指南，强调了你需要考虑的问题，在RFC 8085中。If not, if you’re designing your own  protocol that runs over UDP, then the  IETF has written some guidelines, highlighting the  issues you need to think about,  in RFC 8085.

# TCP可靠传输：Reliable data with TCP

![](/static/2021-03-01-02-42-20.png)

* TCP服务模型
* TCP序列号&ACK使用原理
* TCP丢包检测&恢复原理

## 服务模型：TCP Service Model

![](/static/2021-03-01-02-42-44.png)

TCP提供了一种可靠的、有序的、在IP上运行的字节流传输服务。TCP provides a reliable, ordered, byte stream  delivery service that runs over IP.

* 应用程序将数据写入TCP套接字，在发送系统中进行缓冲，然后通过IP层，传送数据段序列。The applications write data into the TCP  socket, that buffers it up in the  sending system, and then delivers it over  a sequence of data segments over the  IP layer.
* 当这些数据包，这些数据段被接收后，它们会被积累在接收器的接收缓冲区中。**如果有什么东西丢失了，或者到达的顺序不对，就会被重新发送，最终数据被送到应用程序**。When these data packets, these data segments,  are received, they are accumulated in a  receive buffer at the receiver. If anything  is lost, or arrives out of order,  it's re-transmitted, and eventually the data is  delivered to the application.
* **传递给应用程序的数据总是可靠地按照发送的顺序传递**。The data delivered to the application is  always delivered reliably, and in the order  sent.
* **如果有什么东西丢失了，如果有什么东西需要重新传输，这就会暂停后面数据的传递，以确保所有的东西总是按部就班的传递**。If something is lost, if something needs  to be re-transmitted, this stalls the delivery  of the later data, to make sure  that everything is always delivered in order.

## TCP可靠传输：Reliable Data Transfer with TCP

![](/static/2021-03-01-02-48-11.png)

如我们所说，TCP传递的是一个有序、可靠的字节流。TCP delivers, as we say, an order,  reliable, byte stream.

* 连接建立后，经过SYN、SYN-ACK、ACK握手后，客户端和服务器可以发送和接收数据。After the connection has been established,  after the SYN, SYN-ACK, ACK handshake,  the client and the server can send  and receive data.
* 数据可以在该TCP连接内向任何一个方向流动。The data can flow in either direction  within that TCP connection.
* 通常情况下，数据遵循请求响应模式。It’s usual that the data follows a  request response pattern. You open the connection
  * 你打开连接。 客户端向服务器发送请求。服务器回复一个响应。 客户端发出另一个请求。服务器回复另一个响应，以此类推。The client sends a request to the  server. The server replies with a response.  The client makes another request. The server  replies with another response, and so on.
  * **但是TCP并没有对此做出任何要求。没有要求数据必须按照请求响应的模式流动，客户端和服务器可以按照自己喜欢的顺序发送数据**。But TCP doesn't make any requirements on  this. There’s no requirement that the data  flows in a request response pattern,  and the client and the server can  send data in any order they feel  like.
* 不过TCP确实能保证数据的可靠传递，而且是按照发送的顺序。TCP does ensure that the data is  delivered reliably, and in the order it  was sent, though.
  * TCP在收到每个数据段时都会发送确认包。如果有数据丢失，它就会重传丢失的数据。TCP sends acknowledgments for each data segment  as it's received. And if any data  is lost, it retransmits that lost data.
  * 而如果段被延迟，到达的顺序不对，或者一个段必须重新发送，到达的顺序不对，那么TCP会重新构建顺序，然后再把段还给应用。And if segments are delayed and arrive  out of order, or if a segment  has to be re-transmitted and arrives out  of order, then TCP will reconstruct the  order before giving the segments back to  the application.

## TCP读写：reading and writing data on a TCP connection

### send()

![](/static/2021-03-01-02-54-07.png)

使用send()发送数据

* 这将通过TCP连接传输一个数据块。This transmits a block of data over  the TCP connection
  * 参数是代表套接字的文件描述符--TCP套接字、数据、数据的长度和一个标志。而标志字段通常为零。 The parameters are the  file descriptor representing the socket – the  TCP socket, the data, the length of  the data, and a flag. And the  flag field is usually zero.
* send()函数会阻塞，直到所有的数据可以被写入。The send() function blocks until all the data can be written.
  * **而根据网络的可用容量，这可能需要大量的时间来完成**。And it might take a significant amount  of time to do this, depending on  the available capacity of the network.
  * **也可能无法发送所有数据**。It also might not be able to  send all the data.
* 如果连接拥堵，无法再接受任何数据，那么send()函数就会返回-1，表示无法成功发送所有请求的数据。If the connection is congested, and can't  accept any more data, then the send()  function will return -1 to indicate that it  wasn't able to successfully send all the  data that was requested.
* send()函数的返回值是它在连接上实际设法发送的数据量。 而且可以小于要求它发送的数量。The return value from the send() function  is the amount of data it actually  managed to send on the connection.  And that can be less than the  amount it was asked to send.
  * **在这种情况下，你需要通过查看返回值，以及你要求的传输量，弄清楚哪些数据没有发送，在另一个调用中重新发送缺少的部分**。In which case, you need to figure  out what data was not sent,  by looking at the return value,  and the amount you asked for,  and re-send just the missing part in  another call.
  * **同样，如果发生错误，如果连接因某种原因而失败，send()函数将返回-1，并且会设置全局变量errno来表示**。Similarly, if an error occurs, if the  connection has failed for some reason,  the send() function will return -1,  and it will set the global variable  errno to indicate that.

### recv()

![](/static/2021-03-01-03-00-48.png)

recv()函数来接收TCP连接上的数据。

* recv()函数会阻塞，直到数据可用，或者直到连接关闭。The recv() function blocks until data is  a它传递了一个缓冲区，buf，以及缓冲区的大小，BUFLEN，它最多读取BUFLEN字节的数据。vailable, or until the connection is closed.
* 它传递了一个缓冲区，buf，以及缓冲区的大小，BUFLEN，它最多读取BUFLEN字节的数据。It’s passed a buffer, buf, and the  size of the buffer, BUFLEN, and it  reads up to BUFLEN bytes of data.
* 而它返回的是读取的数据字节数。And what it returns is the number  of bytes of data that were read.
* 或者，如果连接被关闭，它返回0。或者，如果发生错误，它返回-1，并再次设置全局变量errno来指示发生了什么。 Or, if the connection was closed,  it returns zero. Or, if an error  occurs, it returns -1, and again sets  global variable errno to indicate what happened.

:orange: 当一个recv()调用完成后，你必须检查这三种可能性。When a recv() call finishes, you have  to check these three possibilities.

* 你必须检查返回值是否为0，以表示连接已经关闭，你已经成功接收了该连接中的所有数据。这时，你也应该关闭该连接。 You have  to check if the return value is  zero, to indicate that the connection is  closed and you've successfully received all the  data in that connection. At which point,  you should also close the connection.
* 你必须检查返回值是否为-1，在这种情况下，发生了错误，该连接已经失败，你需要以某种方式处理这个错误。You have to check if the return  value is -1, in which case  an error has occurred, and that connection  has failed, and you need to somehow  handle that error.
* 而你需要检查是否是其他的数值(收到的字节数)，表示你已经收到了一些数据，然后你需要处理这些数据。And you need to check if it's  some other value, to indicate that you've  received some data, and then you need  to process that data.

:orange: **重要的是要记住，recv()调用只是给你缓冲区中的数据**。What's important is to remember that the  recv() call just gives you that data  in the buffer.

* 如果receive的返回值是157，这就表明缓冲区里有157字节的数据。 If the return value  from receive is 157, this indicates that  the buffer has 157 bytes of data  in it.
* **调用recv()不曾做的事情，是在该缓冲区中添加一个终止的null(\0**)。What the recv() called doesn't ever do,  is add a terminating null to that  buffer.
* 现在，如果你很小心，这并不重要，因为你知道缓冲区里有多少数据，你可以显式处理数据到这个长度。Now, if you're careful that doesn't matter,  because you know how much data is  in the buffer, and you can explicitly  process the data up to that length.
* 但是，基于TCP的应用程序的一个普遍问题是，**他们把数据当作一个字符串，用%s传给printf()调用，或者传给strstr()这样的函数来搜索其中的字符串，或者strcpy()，或者类似的东西**。But, a common problem with TCP-based applications,  is that they treat the data as  if it was a string.  They pass it to the printf() call  using %s as if it were a  string, or they pass it to function  like strstr() to search for a string  within it, or strcpy(), or something like  that.
  * 而问题是字符串函数假设有一个终止的null(\0)，而recv()调用并没有提供一个。And the problem is the string functions  assume there’s a terminating null, and the  recv() call doesn't provide one.
  * <font color="deeppink">如果你要把recv()调用返回的数据传递给C字符串函数之一，你需要自己显式地添加那个null</font>。If you're going to pass the data  that's returned from a recv() call to  one of the C string functions,  you need to explicitly add that null  yourself.
  * 你需要看一下缓冲区，在最后一个成功接收的字节之后，在最后添加null。如果你不这样做，字符串函数就会从缓冲区的末尾运行，你会受到缓冲区溢出攻击。You need to look at the buffer,  add the null at the end,  after the last byte which was successfully  received. If you don't do, this the  string functions will just run off the  end of the buffer and you'll get  a buffer overflow attack.
  * 而这是一个重大的安全隐患。 这是使用C语言的网络代码最大的安全问题之一，它滥用这些缓冲区，不小心使用了其中的一个字符串函数，它就从缓冲区的末端读取(无终止符)，谁知道它处理了什么。And this is a significant security risk.  It’s one of the biggest security problems  with network code using C. It’s misusing  these buffers, accidentally using one of the  string functions, and it just reads off  the end of buffer, and who knows  what it processes.

## TCP报文段&序列号：TCP segment and sequence number

![](/static/2021-03-01-18-06-23.png)

当您使用 TCP 发送数据时，send ()调用对数据进行队列传输 When you send data using TCP,  the send() call enqueues the data for  transmission.

* 操作系统中的TCP代码，将你用各种send()调用写的数据分割成所谓的段，并将每个段放入一个TCP包中。The operating system, the TCP code in  the operating system, splits the data you've  written using the various send() calls into  what’s known as segments, and puts each  of these into a TCP packet.
* **TCP数据包是以IP数据包的形式发送的。而TCP运行拥塞控制算法来决定何时可以发送这些数据包**。The TCP packets are sent in IP  packets. And TCP runs a congestion control  algorithm to decide when it can send  those packets.

:orange: 每个TCP段，每个段都在一个TCP包中。TCP数据包有一个头，头有一个序列号。Each TCP segment, each segment is in a TCP packet. The TCP packets have  a header, which has a sequence number.

* 当连接设置握手发生时，在 **SYN 和 SYN-ack 包中，连接同意初始序列号**; 同意序列号的起始值。When the connection setup handshake happens,  in the SYN and the SYN-ACK packets,  the connection agrees the initial sequence numbers;  agrees the starting value for the sequence  numbers.
* 例如，如果您是客户机，客户机随机选择一个序列号，并将其发送到它的 SYN 包中 If you’re the client, for example;  the client picks a sequence number at  random, and sends this in its SYN  packet.
  * 然后当它开始发送数据时，下一个数据包的序列号比 SYN 包的序列号高一个（SYN标识符占1B） And then when it starts sending data,  the next data packet has a sequence  number that is one higher than that  in the SYN packet.
  * 而且，随着它继续发送数据，序列号会随着发送的数据字节数而增加。And, as it continues to send data,  the sequence numbers increase by the number  of data bytes sent.
  * 例如，如果最初的序列号是1001，只是随机选择，然后它在数据包中发送30个字节的数据，那么下一个序列号将是1031。So, for example, if the initial sequence  number was 1001, just picked randomly,  and it sends 30 bytes of data  in the packet, then the next sequence  number will be 1031.

:orange: **序列号空间在每个方向上都是独立的**。The sequence number spaces are separate for  each in each direction

* 客户端使用的序列号基于客户端发送 SYN 包的初始序列号而增加。The sequence numbers  the client uses increase based on the  initial sequence number the client sent the  SYN packet.
* 服务器使用的序列号，基于服务器在 SYN-ACK 包中发送的初始序列号启动，并基于服务器发送的数据量增加。这两个数空间是不相关的。 The sequence numbers the server use,  start based on the initial sequence number  the server sent in the SYN-ACK packet,  and increase based on the amount of  data the server is sending. The two  number spaces are unrelated.

---

重要的是 send ()调用不会直接映射到 TCP 段上。What's important is that calls to send()  don't map directly onto TCP segments.

* **如果给send()调用的数据太大，无法容纳在一个TCP段中，那么TCP代码将把它分成几个段；它会把它分成几个数据包**。If the data which is given to  a send() call is too big to  fit into one TCP segment, then the  TCP code will split it across several  segments; it'll split it across several packets.
* 类似地，如果您发送的数据，您给 send ()调用的数据非常小，TCP 可能不会立即发送它。Similarly, if the data you send,  that data you give the send() call  is quite small, TCP might not send  it immediately.
  * 它可以对其进行缓冲，将其与作为稍后的 send ()调用的一部分发送的数据结合起来。然后把它们组合起来，发送到一个更大的段，一个更大的 TCP 数据包。It might buffer it up, combine it  with data sent as part of a  later send() call. And combine it,  and send it in a single larger  segment, a single larger TCP packet.
* <font color="deeppink">这是一个被称为纳格尔算法的想法。它只是通过发送大数据包来提高效率，因为每个数据包都有一定的开销。</font> This is an idea known as Nagle’s  algorithm. It's there to improve efficiency by  only sending big packets, because there's a  certain amount of overhead for each packet. 
  * TCP 发送的每个数据包都有一个 TCP 报头。它有一个 IP 头。根据链路层的不同，它有以太网和 WiFi 头信号。这就增加了一定的开销。我想大概是每个数据包40字节。因此，如果您只发送少量的数据，那将是大量的开销，大量的浪费数据 Each packet that’s sent by TCP has  a TCP header. It’s got an IP  header. It's got the Ethernet or the  WiFi headers depending on the link layer.  And that adds a certain amount of  overhead. It’s about, I think, 40 bytes  per packet. So if you're only sending  a small amount of data, that's a  lot of overhead, a lot of wasted  data.
* <font color="purple">因此，TCP 使用 Nagle 算法，尽可能地将这些数据包组合成更大的数据包。但是，当然，这增加了一些延迟。它必须等待你发送更多的数据; 等待看看它是否能形成一个更大的数据包。</font> So TCP, with the Nagle algorithm,  tries to combine these packets into larger  packets when it can. But, of course,  this adds some delay. It’s got to  wait for you to send more data;  wait to see if it can form  a bigger packet.

:orange: 如果你真的需要低延迟，你可以禁用 Nagle 算法。有一个名为 `TCP_nodelay` 的套接字选项，我们可以看到幻灯片上的代码来演示如何使用它。If you really need low latency,  you can disable the Nagle algorithm.  There’s a socket option called TCP_NODELAY,  and we see the code on the  slide to show how to use that.

* 因此，您创建了套接字，建立了连接，然后调用 `TCP_nodelay` 选项并关闭该选项。这意味着每次在套接字上`send()`时，它都会立即以尽可能快的速度发送。So you create the socket, you  establish the connection, and then you call  the TCP_NODELAY option and that turns this  off. And this means that every time  you send() on the socket, it immediately  gets sent as quickly as possible.
* <font color="red">不过，这种行为的一个含义是，TCP 可以将在单个 send ()中写入的数据分散到多个段中，或者可以将多个 send ()调用合并到单个段中，recv ()调用返回的数据并不总是对应于单个 send ()</font>。 One implication of this behaviour, though,  where TCP can either split data written  in a single send() across multiple segments,  or where it can combine several send()  calls into a single segment, is that  the data returned by the recv() calls  doesn't always correspond to a single send().
  * 当您调用 recv ()时，您可能只得到消息的一部分。您需要再次调用 recv ()以获得剩余的消息。When you call recv(), you might get  just part of a message. And you  need to call recv() again to get  the rest of the message.
  * 或者您可能在一个 recv ()调用中收到多条消息。 Or you may get several messages in  one recv() call.

## TCP不保留消息边界：TCP Does Not Preserver Message Boundaries

![](/static/2021-03-01-19-39-30.png)

当使用 TCP 时，recv ()的调用可靠地返回数据，并按发送的顺序返回数据。When you're using TCP, the recv() calls  return the data reliably, and they return  the data in the order that it  was sent.

* 但是TCP没有做的是装帧数据，他们没有做的是保留消息边界。But what they don't do is frame  the data. What they don't do is  preserve the message boundaries.

:orange: 例如，如果我们使用 HTTP，我们可以看到一个 HTTP 消息可能被发送的例子，一个 HTTP 响应可能被网络服务器发送回浏览器。For example, if we're using HTTP,  which we see, we see an example  of an HTTP message that might be  sent,  an HTTP response that might be sent,  by a web server back to a  browser.
* 如果我们使用 HTTP，我们希望的是整个响应一次性被接收。因此，如果我们实现了一个 web 浏览器，我们只需要在 TCP 连接上调用 recv () ，**我们就可以在一个调用 recv ()中获得所有的头部和正文**，然后我们就可以解析它，并处理它。If we're using HTTP, what we would  like is that the whole response is  received in one go. So if we're  implementing a web browser we just call  recv() on the TCP connection  and we get all of the headers,  and all of the body, in just  in just one call to recv() and  we can then parse it, and process  it, and deal with it.
* **但是，TCP 不能保证这一点。它可以任意分割消息，这取决于数据包中有多少数据，底层链路层可以发送多大的数据包，以及网络的可用容量取决于拥塞控制**。TCP doesn't guarantee this, though.  It can split the messages arbitrarily,  depending on how much data was in  the packets, what size packets the underlying link layers can send, and on the available capacity of the network depending on  the congestion control.
  * 而且它可以在任意点分割数据包。And it can split the packets at  arbitrary points.
  * 例如，幻灯片上，我们会看到请求头，有些用红色标注，有些用蓝色，有些用蓝色，有些用绿色标注。而且可能是 TCP 连接将数据拆分，这样第一个 recv ()调用只获得以红色突出显示的标头部分，结束于“ ETag:”行的中间部分。然后再次调用 recv ()。然后你会看到用蓝色高亮显示的消息部分，其中包含消息头的其余部分和正文的第一部分。For example, if we look at the  slide, we see that the headers,  some of them are labeled in red,  some are in blue,  some of the body is in blue,  some the rest of the body is  in green. And it could be that  the TCP connection splits the data up,  so that the first recv() call just  gets the part of the headers highlighted  in red,  ending halfway through the “ETag:” line.  And then you have to call recv()  again. And then you get the part  of the message highlighted in blue,  which contains the rest of the headers  and the first part of the body.
  * 然后必须再次调用 recv () ，以获取幻灯片上以绿色高亮显示的其余消息。Then you have to call recv() again,  to get the rest of the message  that's highlighted in green on the slide.
* **这使得解析变得更加困难; 对于程序员来说更加困难**。And this makes it much harder to  parse; much harder for the programmer.
  * 因为你必须查看你得到的数据，解析它，检查你是否得到了整个消息，检查你是否得到了完整的消息头，检查你是否得到了完整的消息体。Because you have to look at the  data you've got, parse it, check to  see if you've got the whole message,  check if you've received the complete headers,  check to see if you've received the  complete body
  * 你必须处理这样一个事实，你可能有部分消息 And you have to handle  the fact that you might have partial  messages.

:orange: **不保留消息边界，使得调试有点困难**，And it's something which makes it a  little bit hard to debug

* 因为如果你只发送小的消息，如果你发送的数据包只有1000字节左右，它们可能小到可以装进一个数据包，而且它们总是一次性发送完毕 because if  you only send small messages,  if you're sending packets which are only  like 1000 bytes, or so, they’re probably  small enough to fit in a single  packet, and they always get delivered in  one go.
* 只有当你开始发送一个更大的数据包，或者通过连接发送大量的数据，这样数据才会因为拥塞控制而分开，你才会开始看到这种行为，即消息在任意点被分离。 It’s only when you start sending a  larger packets, or sending lots of data  over connection so things get split up  due to congestion control, that you start  to see this behaviour where the messages  get split at arbitrary points.

## ACK：TCP Acknowledgment

![](/static/2021-03-01-20-06-28.png)

正如我们所看到的，TCP 段包含序列号，序列号与发送的字节数一起计数。So as we've seen, the TCP segments  contain sequence numbers, and the sequence numbers  count up with the number of bytes  being sent.

* 每个 TCP 段还有一个确认号 - ACK Each TCP segment also has an acknowledgement  number.
* **当一个 TCP 段被发送时，它会确认以前接收到的任何段**。 When a TCP segment is sent,  it acknowledges any segments that have previously  been received.
* 因此，如果一个 TCP 端点在 TCP 连接上收到了一些数据，当它发送下一个数据包时，<font color="deeppink">ACK 位将被设置在 TCP 报头中，以表明确认号码是有效的，确认号码将有一个值指示它所期望的下一个序列号</font>。也就是说，它在连接上预期的下一个连续字节。 So if,  if a TCP endpoint has received some  data on a TCP connection,  when it sends its next packet,  the ACK bit will be set in  the TCP header, to indicate that the  acknowledgement number is valid, and the acknowledgement  number will have a value indicating the  next sequence number it is expecting.  That is, the next contiguous byte it's  expecting on the connection.

:orange: 因此，在这个例子中，我们有一个稍微不现实的例子，连接一次发送一个字节，而第一个数据包是以序列号5发送的。然后下一个数据包被发送，序列号是6，然后是7,8,9,10，以此类推。这就是 ssh 连接可能发生的情况，您键入的每个密钥生成一个 TCP 段，其中只按一个密钥。So, in the example, we have a  slightly unrealistic example in that the connection  is sending one byte at a time,  and the first packet is sent with  sequence number five.  And then the next packet is sent  with sequence number six, and then seven,  and eight, and nine, and ten,  and so on. And this is what  might happen with an ssh connection,  where each key you type generates a  TCP segment, with just the one key  press in it.

* 当主机 b 接收到这些数据包时，它会发送一个设置了确认位ACK的 TCP 段，确认接下来会发生什么。 And when those packets are received at  host B, it sends a TCP segment  with the acknowledgement bit set, acknowledging what's  expected next.
* 因此，当它接收到**序列号为5的 TCP 数据包，其中包含一个字节的数据时，它会发送一个确认信息ACK，表示它已经接收到了，接下来它将期待序列号为6的数据包**。 So when it receives the TCP packet  with sequence number five, and one byte  of data in it, it sends an  acknowledgement saying it got it, and it's  expecting the packet with sequence number six  next.
  * 当它接收到序列号为6的数据包，其中包含一个字节的数据时，它会发送一个确认信息，说它期待7个数据包。诸如此类。When it receives the packet with sequence  number six, and one byte of data  in it, it sends an acknowledgement saying  it's expecting seven. And so on.

:orange: **TCP 只确认预期的下一个连续序列号**。 TCP only ever acknowledges the next contiguous  sequence number expected.

* 发送确认每个接收到的段- 包含确认号码ACK，指示预期下一个连续字节的序列号 Segments sent to acknowledge each received segment – contains acknowledgment number indicating sequence number of the next contiguous byte expected 

:orange: **如果数据包丢失，随后的数据包会产生重复确认(冗余确认)**。 And if a packet is lost,  subsequent packets generate duplicate acknowledgments.

* 所以在这种情况下，第五个数据包被发送。它到达了收方，并且发送了确认信息，说它预计6B。6个已经发出，到达了接收端，所以确认表示预计7个。发送了七个，到达收方，发送确认信说它期望八个。8序列号的包被送走了，然后丢失。9发出去了，到达了收方。此时，接收方接收到序列号为5、6和7的数据包; 缺少8的数据包; 到达9的数据包。所以它期望的下一个连续序列号仍然是8。 So in this case, packet five was  sent. It got to the receiver,  and that sent the acknowledgement saying it  expected six. Six was sent, arrived at  the receiver, so the acknowledgement says it  expects seven.  Seven was sent, arrives at the receiver,  sends the acknowledgement saying it expects  eight. Eight was sent, and gets lost.  Nine was sent, and arrives at the  receiver.  At this point, the receiver’s received the  packets with sequence numbers five, six,  and seven; eight is missing; and nine  has arrived. So the next contiguous sequence  number it's expecting is still eight.
* 所以它会发送一个确认信息说“我希望接下来是第八个序列”。发送的数据包，即下一个数据包，序列号为10。到了这里，确认回来说“我还没有得到8，我还在期待8”，**这种情况继续下去。TCP 不断发送重复冗余的确认，而在序列号空间有一个差距**。 So it sends an acknowledgement saying “I’m  expecting sequence number eight next”.  The packet sent, the next packet sent,  has sequence number 10. This arrives,  the acknowledgement goes back saying “I still  haven't got eight, I’m still expecting eight”,  and this carries on. TCP keeps sending  duplicate acknowledgments while there’s a gap in  the sequence number space.

:candy: **此外， TCP 也可以发送延迟确认，其中它只确认每一次的第二个数据包**。In addition, we don't show it here,  but TCP can also send delayed acknowledgments,  where it only acknowledges every second packet.

* 在这种情况下，ACK可能是，6,8。In this case the acknowledgments might go,  six, eight.
* 序列号为5的数据包被发送出去，它确认发送出去的序列号为6。带有第六个数字的数据包发送，到达，然后发送第七个数字的数据包，然后它发送确认信息，说它期待有第八个数字。**因此，它不必发送每个确认，它可以发送每个其他确认ACK以减少开销**。The packet with sequence number  five is sent, and it acknowledges six.

## ACK-丢包检测：Loss Detection

**TCP 使用确认ACK来检测数据包丢失; 检测数据段丢失的时间** TCP uses the acknowledgments to detect packet  loss; to detect when segments are lost.

* **两种检测机制** 2 way does the loss detection

### 超时检测：Timeout

![](/static/2021-03-01-21-23-49.png)

第一种是，如果它发送数据，但由于某种原因，确认完全停止（未能收到任何ACK）。The first is that if it sends  data, but for some reason the acknowledgments  stop entirely.

* 这是一个信号，**要么是收方失败了，数据包被传送到接收者，但是应用程序已经崩溃了，那里没有任何东西来接收数据，回复**。This is a sign that either the  receiver has failed,  And, you know, the packets are being  delivered to the receiver, but the application  has crashed, and there's nothing there to  receive the data, to reply.
* **或者这表示网络连接失败，数据包没有到达接收端**。 Or it's an indication that the network  connection has failed, and the packets are  just not reaching the receiver.

:candy: 因此，**如果 TCP 正在发送数据，并且没有得到任何回复，过一段时间后，它会超时，并使用这个来表示连接已经失败**。 So if TCP is sending data,  and it's not getting any acknowledgments back,  after a while it times out and  uses this as an indication that the  connection has failed.

### 3个冗余ACK：Triple Duplicate Acknowledgement

![](/static/2021-03-01-21-45-14.png)

if some data is lost,  but the later segments arrive, then TCP  will start sending the duplicate acknowledgments.如果一些数据丢失，但后面的片段到达，那么 TCP 将开始发送重复的确认。

:orange: 同样，回到这个例子，我们看到第八个数据包丢失了，第九个数据包到达了，序列号，确认号，返回来说“ i’m expecting sequence number eight”。 Again, back to the example, we see  that packet eight is lost, packet nine  arrives, and the sequence number, the acknowledgement  number, comes back says “I’m expecting sequence  number eight”.

* 然后第十个包被发送，它到达了，它仍然说“我仍然期待第八个序列的包”，而这只是继续。 And packet ten is sent and it  arrives, and it still says “I’m still  expecting packet with sequence number eight”,  and this just carries on.
* 最终，TCP 得到了所谓的三个重复确认ACK。它得到了最初的确认信息，说它期待第八个数据包，然后是三个重复的数据包，所以总共有四个数据包，都说“我仍然期待第八个数据包”。 And, eventually, TCP gets what's known as  a triple duplicate acknowledgement. It’s got the  original acknowledgement saying it's expecting packet eight,  and then three duplicates following that,  so four packets in total, all saying  “I’m still expecting packet eight”.
* 这意味着，数据仍然在传递，但是有些东西丢失了。**它只在一个新数据包到达时生成确认，所以如果我们不断看到指示同一事物的确认，这表明新数据包到达，因为这是触发要发送的确认的原因，但仍然有一个数据包丢失**，它告诉我们它在期待哪个数据包。 And what this indicates, is that data  is still arriving, but something's got lost.  It only generates acknowledgements when a new  packet arrives, so if we keep seeing  acknowledgments indicating the same thing, this indicates  that new packets arriving, because that's what  triggers the acknowledgement to be sent,  but there's still a packet missing,  and it's telling us which one it's  expecting.
* 在这一点上，TCP 认为数据包已经丢失，并重新传输这个段。它重新传输序列号8的数据包。 At that point TCP assumes that the  packet has got lost, and retransmits that  segment. It retransmits the packet with sequence  number eight.

#### 为什么3个冗余？

![](/static/2021-03-01-21-53-07.png)

Why does it wait for a triple  duplicate acknowledgement?  Why does it not just retransmit it  immediately. when it sees a duplicate?为什么要等待三次重复的确认ACK？为什么它不直接重新发送。当它看到冗余ACK的时候？

:orange: 例子

* 在这种情况下，发送一个序列号为5的数据包，包含一个字节的数据，它到达，接收方承认它，说它期待6。In this case, a packet with sequence  number five is sent, containing one byte  of data, and it arrives, and the  receiver acknowledges it, saying it's expecting six.
* 第六个是发送的，它到达了，接收者承认它，表明它期待着第七个。And six is sent, and it arrives,  and the receiver acknowledges it, indicating it’s  expecting seven.
* **第七个数据包被发送了，但是被延迟了。然后发送第八个数据包，最终到达接收端**。 And packet seven is sent, and it's  delayed. And packet eight is sent,  and eventually arrives at the receiver.
  * **现在接收方还没有收到第七个数据包（但收到了8），所以它发送了一个确认信息，上面写着“我仍然期待着第七个”。这是一个重复的确认**。 Now the receiver hasn't received packet seven  yet, so it sends an acknowledgement which  says “I’m still expecting seven”. So that's  a duplicate acknowledgement.
  * 在这一点上，被延迟的数据包7终于到达了。 At that point packet seven, which was  delayed, finally does arrive.
  * 现在七号包已经到了，八号包已经到了，所以现在期待的是九号包，所以它发送了一个九号包的确认 Now packet seven has arrived, packet eight  had arrived previously, so what is now  expecting is nine, so it sends an  acknowledgement for nine.
* 我们可以看到确认信息是6,7,7,9，因为数据包7被稍**微延迟了一点**。And we see that the acknowledgments go  six, seven, seven, nine, because that packet  seven was delayed a little bit.

:orange: 如果 TCP 对单个重复的确认(即看见两个一样的ACK)作出反应，表明数据包丢失了，**那么您就冒着在假设数据包丢失时重新发送数据包的风险，而实际上它只是延迟了一点点** And if TCP reacts to a single  duplicate acknowledgement as an indication that the  packet was lost, then you run the  risk that you're resending a packet on  the assumption when it was lost,  when it was just merely delayed a  little bit.

:candy: 可以权衡冗余ACK的选择

* 认为单个冗余ACK是丢包的标志？认为两个冗余？三个？四个？五个？ Do you treat, a single duplicate as  an indication of loss? Do you treat  two duplicates as an indication of loss?  Three? Four? Five? 
* 什么时候你会说“这是丢包的信号”，而不是“这是一个稍微延迟的数据包，它可能在一分钟内恢复”？At what point do  you say “this as an indication of  loss”, rather than just “this is a  slightly delayed packet, and it might recover  itself in a minute”?

:candy: **使用三重复制的原因，是因为有人做了一些测量，认为数据包被延迟到足以导致一到两个重复ACK，因为它们到达的时候有点乱，这是相对常见的**。 The reason that a triple duplicate is  used, is because someone did some measurements,  and decided that packets being delayed  enough to cause one or two duplicates,  because they arrived just a little bit  out of order, was relatively common.

* 但是数据包被延迟到足以导致三个或更多重复的情况是很少见的。 But packets being delayed enough that they  cause three or more duplicates is rare. 
* 因此，这是损失检测的平衡速度与一个仅仅延迟的数据包被视为丢失并不必要地重新传输的可能性之间的平衡 So it's balancing-off speed of loss detection  vs. the likelihood that a merely delayed  packet is treated as if it were  lost, and retransmitted unnecessarily. 

:candy: 基于统计数据，TCP 的设计者认为等待三个冗余ACK是正确的阈值。 And, based on the statistics, the belief  by the designers of TCP was that  waiting for three duplicates was the right  threshold.

* 你可以有一个 TCP 版本，把这个**减少到两个，甚至一个副本，它可以更快地响应丢失**，但是有更大的风险，**不必要地重传一些延迟的东西**。 And you could make a TCP version  that reduced this to two, or even  one duplicate, and it would respond to  loss faster, but would have the risk  that it's more likely to unnecessarily retransmit  something that's just delayed.
* 或者你**可以使用4、5、6，甚至更多的重复确认，这将减少不必要地重传数据的可能性。但是它会慢一些，因为它对丢失的反应会慢一些，而对丢失的数据包的重新传输也会慢一些**。 Or you could make it four,  five, six, even more duplicate acknowledgments,  which will be less likely to unnecessarily  retransmit data. But it’d be slower,  because it would be slower in responding  to loss, and slower in retransmitting actually  lost packets.

## TCP队头阻塞：head-of-line blocking

> TCP的队头阻塞发生： 当**一个TCP分节丢失的时候，因为TCP是可靠传输，所有后续分节将被接收端一直保存，直到丢失的第一个分节被发送端重传并且到达接受端为止才会继续被传输**。
> 这种可靠的传输机制确保接收应用进程能够按照发送端的发送顺序接受数据。虽然可靠但是也有不利之处。
> 比如说，客户端向服务端请求3幅图片，为了营造这几幅图像在用户屏幕上并行显示的效果，服务器先发送第一幅图像的一个断片，再发送第二幅图像的一个断片，然后再发送第三幅图像的一个断片；服务器重复这个过程，直到这3幅图像全部成功地发送到浏览器为止。
> **如果第一幅图的某个断片的TCP分节丢失，客户端将保持已到达的不按序的所有数据，直到丢失的分节重传成功。这样一来延迟了第一幅图像的数据传送，也影响了后面两幅图像数据递送**。

![](/static/2021-03-01-22-14-12.png)

:orange: 例子 - 正在发送全尺寸的数据包，每个数据包包含1500字节的数据。sending something  more realistic. We're sending full size packets,  with 1500 bytes of data in each  packet.

* 1500是你可以通过以太网或者 WiFi 发送的最大数据包大小，所以这是一个典型的数据包大小。 And 1500 is the maximum packet size  that you can send in an Ethernet  packet, or in a WiFi packet,  so this is a typical size that  actually gets sent.
* 在这种情况下，发送的第一个数据包的序列号在0到1499之间。这个数据包到达接收端，接收端发送一个确认信息，表示接收到了，接收端期待的下一个数据包的序列号是1500。所以它发送了一个1500的确认。In this case, the first packet is  sent with sequence numbers in the range  zero through to 1499.  And this arrives at the receiver,  and the receiver sends an acknowledgement saying  it got it, and the next packet  it’s expecting has sequence number 1500.  So it sends an acknowledgement for 1500.
  * 如果该套接字上有一个未完成的 recv ()调用，那么该 recv ()调用将在该点返回，并返回1500字节的数据。它返回接收到的数据。And if there’s a recv() call outstanding  on that socket, that recv() call will  return at that point, and return 1500  bytes of data. It returns the data  as it was received.
* 下一个数据包到达接收器，包含1500到2999的序列号，如果有recv()调用的话，再次返回，并返回这下一个1500字节。The next packet arrives at the receiver,  containing sequence numbers 1500 through to 2999,  and again the recv() call, if there  is one, will return, and return that  next 1500 bytes.
  * 同理，当包含下一个1500的数据包进来时，接收器会发送ACK说 "我期待4500"，recv()调用会返回。 Similarly, when the packet containing the next  1500 comes in, the receiver will send  the ACK saying “I’m expecting 4500”,  and the recv() call will return.
* 包含序列号4500至5999的数据包丢失。The packet containing sequence numbers 4500 though  to 5999 is lost.
* 包含6000到7499的数据包到达。 The packet containing 6000 through to 7499  arrives.
  * 确认回传表示它仍然在等待序列号4500，因为那个数据包丢失了。而在这一点上，一些数据已经到达， 一些新的数据已经到达接收器。 但是有一个缺口。包含序列号4500到5999的数据的数据包仍然丢失了。The acknowledgement goes back indicating that it’s  still expecting sequence number 4500, because that  packet got lost. And at that point,  some data has arrived, some new data  has arrived at the receiver.  But there's a gap. The packets,  containing data with sequence numbers  4500 through to 5999 is still missing.
* **所以，如果接收方应用程序已经在该套接字上调用了recv()，它就不会返回。 数据已经到达了，它在操作系统的TCP层中被缓冲起来了，但是TCP不会把它还给应用程序**。So if the receiver application has called  recv() on that socket, it won't return.  The data has arrived, it's buffered up  in the TCP layer in the operating  system, but TCP won't give it back  to the application.
  * 而且数据包可以一直发送，接收方一直发送重复的确认，**最后它发送了三重重复的确认，TCP发送方注意到并重发了序列号为4500到5999的数据包**。And the packets can keep being sent,  and the receiver keeps sending the duplicate  acknowledgments, and eventually it’s sent the triple  duplicate acknowledgement, and the TCP sender notices  and retransmits the packet with sequence numbers  4500 through to 5999.
* 最终这些数据会到达接收器。 这时，接收机就有一个连续的数据块可用，其中没有空隙，**它把序列号4500到序列号12000的所有数据一次性返回给应用程序**。And eventually those arrive at the receiver.  At that point, the receiver has a  contiguous block of data available, with no  gaps in it, and it returns all  of the data from sequence number 4500  up to sequence number 12,000,  up to the application in one go.
  * 而如果**应用程序给了一个足够大的缓冲区**，这时recv()调用将返回**7500字节的数据**。它会把**所有接收到的数据**都在一个大的突发事件中返回。And if the application has given a  big enough buffer, at that point the  recv() call will returned 7500 bytes of  data. It’ll return all of that received  data in one big burst.

:orange: **如果丢包，就会发生三重重复的ACK，最终会被重传，在重传发生之前，接收器不会返回任何东西给应用程序**。If anything's missing, the triple duplicate ACK  happens, it eventually gets retransmitted, and the  receiver won't return anything to the application  until that retransmission has happened.

* **这就是所谓的队头阻塞。 数据停止传送，直到它能按顺序传送给应用程序**。这一切都只是在操作系统中，在TCP代码中被缓冲起来。It’s called head of line blocking.  The data stops being delivered, until it  can be delivered in sequence to the  application. It’s all just buffered up in  the operating system, in the TCP code.
* <font color="deeppink">TCP总是按照发送的顺序，以一个连续的有序序列将数据给应用程序。 而这也是为什么recv()调用并不总是保留消息边界的另一个原因。</font>TCP always gives the data to the  application in a contiguous ordered sequence,  in the order it was sent.  And this is another reason why the  recv() calls don't always preserve the message  boundaries.
  * **因为要看因为丢包等原因排队的数据有多少，因此能一直按顺序送达**。Because it depends how much data was  queued up because of packet losses,  and so on, so that it can  always be delivered in order.

### 队首阻塞：丢包队时间的影响

![](/static/2021-03-01-22-38-02.png)

线路头阻塞会增加总的下载时间 The head of line blocking increases the  total download time

* **延迟取决于RTT和数据包序列化延迟的相对值**。Delay depends on relative values of RTT and packet serialisation delay
* 左边的情况是，一个数据包丢失了，不得不重新发送。on the  left, the case where one packet was  lost, and had to be re-transmitted
* 右边是所有数据包都按时收到的情况。And we see on the right,  the case where all the packets were  received on time.
* 我们看到因为丢包而增加了下载时间。 And we see an  increase in the download time because of  the packet loss.

**丢包阻挡了接收，它延迟了一些事情，等待重传。而且会增加一点整体的下载时间**。 It blocks the receiving, it delays things  a little bit, waiting for the retransmission.And it increases the overall download time  a little bit.

* **在下载过程中，它破坏了数据包什么时候收到的行为**，很明显。It disrupts the behaviour of when the  packets are received, during the download quite  significantly.
* 我们看到在数据包丢失的情况下，1500、1500、1500，差距很大，7500、1500、1500。We see 1500, 1500, 1500,  big gap, seven thousand five hundred,  1500, 1500,  in the case where the packets were  lost
* 或者说，在全部收到的情况下，数据进来的时候很顺利。它的间隔是有规律的。Or, in the case where they  were all received, the data is coming  in quite smoothly. It's regularly spaced
* 所以它影响了时间，影响了数据什么时候送到应用上，它对整个下载时间的影响比较小。So it affects the timing, it effects  when the data is delivered to the  application, and it has a smaller effect  on the overall download times.

### 丢包对实时应用的影响

![](/static/2021-03-01-23-09-17.png)

而如果你正在构建实时应用，这是一个重要的问题。And if you're building real time applications,  this is a significant problem. 

* 我们看到右边的案例，如果所有的东西都能按时交付，那么数据就会非常快速、非常可预测的发布到应用中。 而且你在接收端不需要太多的缓冲延迟。 事情可以只管交付，事情只管交付给应用，按固定的时间表重复交付。We see  the case on the right, if everything  is delivered on time, then the data  is released to the application very quickly  and very predictably.  And you don't need  much buffering delay at the receiver.  Things can be just delivered, things are  just delivered to the application, repeatedly on  a regular schedule.
* 但是一丢东西，就得等重传。 在这种情况下，它要等一个RTT的时间，因为ACK要拿回来，然后要重传数据。But the minute something gets lost,  it has to wait for the retransmission.  In this case it waits for one  round trip time, because the ACK has  to get back, and then the data  has to be retransmitted. 
  * 另外，它还要等待四倍的数据包之间的空隙，以备四次重复、三次三番的ACK和原始的ACK，所以你会得到一个往返时间加上四倍的数据包间距。  Plus, it has to wait for four  times the gap between packets, to allow  for the four duplicates, the triple tripling  ACK and the original ACK, so you  get one round trip time plus four  times the packet spacing.

因此，如果你使用TCP来发送，例如，语音数据，它每20毫秒定期发送数据包，你需要缓冲80毫秒加上往返时间RTT，以允许这些重新传输，如果你使用它来进行实时应用。So if you're using TCP to send,  for example, speech data, where it's sending  packets regularly every 20 milliseconds, you need  to buffer 80 milliseconds plus the round  trip time, to allow for these re-transmissions,  if you're using it for a real  time application.

* 因为，它在等待重传，也因为队头阻塞。Because,  it waits for the retransmissions, and because  of the head of line blocking.

:orange: 当你使用Netflix或iPlayer这样的应用时，当你按下视频播放键时，会有一个小暂停，上面写着 "缓冲"。 这就是它在做的事情。**它在缓冲足够的数据，以便等待重传的发生，在TCP连接中缓冲足够的数据，以便继续按顺序播放视频帧，同时仍有时间让重传发生**。And when you're using applications like Netflix  or the iPlayer, when you press play  on the video there’s a little pause  where it says “buffering”.  This is what it's doing. It’s buffering  up enough data that it can wait  for the retransmissions to happen,  buffering up enough data in the TCP  connection that it can keep playing out  the video frames, in order, while still  allowing time for a retransmission to happen.

* 所以它是缓冲了数据的等待，确保有足够多的数据缓冲起来，因为TCP的这个队头阻塞问题。So it's buffering up the data waiting,  making sure there's enough enough data buffered  up, because of this head of line  blocking issue in TCP.

## 总结-使用TCP进行可靠的数据传输： Reliable Data Transfer with TCP

![](/static/2021-03-01-23-13-34.png)

TCP给你一个有序的、可靠的、字节流。TCP gives you an ordered, reliable, byte stream.

* 作为一种服务模式，它很容易理解。这就像从文件中读取一样，你从连接中读取，字节按照发送的顺序可靠地到达。As a service model it's easy to  understand. It’s like reading from a file;  you read from the connection and the  bytes arrive reliably and in the order  they were sent.
* 不过，时间是不可预知的。每次从连接中读到多少数据，数据是定期到达，还是大批量到达，中间有很大的空隙，**取决于数据丢失的多少，取决于TCP是否要重传丢失的数据**。The timing, though, is unpredictable. How much  you get from the connection each time  you read from it,  and whether the data arrives regularly,  or whether it's arrives in big bursts  with large gaps between them, depends on  how much data is lost, and depends  on whether the TCP has to retransmit  missing data.
  * 而如果你只是用这个来下载文件，那就无所谓了。这意味着进度条或许不准确，但除此之外，并没有太大的区别。And if you're just using this to  download files that doesn't matter. It means  that the progress bar is perhaps inaccurate,  but otherwise it doesn't make much difference.
  * 但是，如果你是用于实时应用，比如说视频流，比如说电话，这个队首阻塞会相当大的影响播放效果。But, if you're using it for real  time applications, like video streaming, like telephony,  this head of line blocking can quite  significantly affect the play out.
  * 这就是为什么应用程序使用，为什么实时应用程序使用，UDP的原因。而对于那些不使用UDP的应用，像Netflix这样使用自适应流媒体的HTTP的应用，我们会在第七讲讲到，这就是为什么在开始播放之前会有缓冲延迟。And a lot of that is the  reason why applications use, why real time  applications use, UDP. And for those that  don't use UDP,  applications like Netflix that use adaptive streaming  over HTTP, which we'll talk about in  lecture seven, that's why there’s this buffering  delay before they start playing.
* 当然，缺乏框架也让应用设计变得复杂，**你必须解析数据，以确保你已经收到了所有的数据；里面没有消息边界，所以你必须解析数据**。 它不会告诉你，连接不会告诉你，什么时候你已经收到了所有的数据。And, of course, the lack of framing  complicates the application design, you have to  parse the data to make sure you've  got all the data;  there's no message boundaries in there,  so you have to parse the data.  It doesn't tell you, the connection doesn't  tell you, when you've received all the  data.

# QUIC可靠传输：Reliable Data Transfer with QUIC

* QUIC service model
  * 如何处理packet numbers & retransmission
* Multi-streaming features of quic
* how it avoids/limit head-of-line blocking

## QUIC服务模型：QUIC Service Model

正如我们之前所看到的，TCP的服务模型是，它传递的是一个可靠的、有序的、字节流的数据。 应用程序将一个字节流写入，而这个字节流最终会被传送给接收器。The service model for TCP, as we  saw previously, is that it delivers a  single reliable, ordered, byte stream of data.  Applications write a stream of bytes in,  and that stream of bytes is delivered  to the receiver, eventually.

![](/static/2021-03-01-23-21-43.png)

相比之下，**QUIC在一个连接中传送多个有序的可靠字节流**。QUIC, by contrast, delivers several ordered reliable  byte streams within a single connection.

* 应用程序可以将他们发送的数据分离成不同的数据流，并且每个数据流都能可靠地按顺序传送 Applications can separate the data they're sending  into different streams, and each stream is  delivered reliably and in order.
* **QUIC不会保留一个连接内的流之间的顺序**，QUIC doesn't preserve the ordering between the  streams within a connection, 
  * 所以如果你发送一个流，然后发送第二个流，那么你第二个发送的数据，在第二个流中，可能会先到达，**但是它保留了流内顺序？**。so if you  send in one stream, and then send  in a second stream, then the data  you sent second, in that second stream,  may arrive first, but it preserves the  ordering with a stream.

:orange: 而且你可以把每个流当作是**并行运行多个TCP连接**，所以它给你提供了相同的服务模型，有多个数据流，And you can treat each stream as  if it were running multiple TCP connections  in parallel, so it gives you the  same service model with several streams of  data,

:orange: 或者你也许可以把**每个流当作一个要发送的消息序列，用流表示消息边界**。 or you could perhaps treat each  stream as a  sequence of messages to be sent,  with the streams indicating message boundaries.

## QUIC包&序列号：QUIC Packet and Sequence Number

![](/static/2021-03-02-12-04-43.png)

每个QUIC包都有一个包序号，一个包号，包号分为两个包号空间。 Each QUIC packet has a packet sequence  number, a packet number,  and the packet numbers  are split into two packet number spaces.

* **在初始QUIC握手过程中发送的数据包以数据包序列号0开始，在握手过程中每发送一个数据包，该数据包序列号就增加一个**。The packets sent during the initial QUIC  handshake start with packet sequence number zero,  and that packet sequence number increases by  one for each packet sent during the  handshake.
* **然后，当握手完成后，它切换到发送数据，它将数据包序列号重置为0，然后重新开始**。Then, when the handshake’s complete, and it  switches to sending data, it resets the  packet sequence number to zero and starts  again.
* **在每一个包号空间、握手空间和数据空间内，包号序列从0开始，每发送一个包就增加一个**。Within each of these packet number spaces,  the handshake space, and the data space,  the packet number sequence starts at zero,  and goes up by one for every  packet sent.
* **也就是QUIC中的序列号，QUIC中的数据包号，统计的是发送数据包的数量**。That is, the sequence numbers in QUIC,  the packet numbers in QUIC, count the  number of packets of data being sent.
  * 这与TCP不同。**在TCP中，头中的序列号计算的是字节流内的偏移量，它计算的是发送了多少字节的数据。而在QUIC中，数据包号计算的是数据包的数量**。  That's different to TCP. In TCP,  the sequence number in the header counts  the offset within the byte stream,  it counts how many bytes of data  have been sent. Whereas in QUIC,  the packet numbers count the number of  packets.

:orange: **QUIC数据包内是一帧序列，其中有些帧可能是流帧，流帧携带数据**。 Inside a QUIC packet is a sequence  of frames. Some of those frames may  be stream frames, and stream frames carry  data.

* **每个流帧都有一个流ID，所以它知道它为众多子流中的哪个子流携带数据，它也有=被携带的数据量，以及这些数据从流开始的偏移量**。Each stream frame has a stream ID,  so it knows which of the many  sub-streams it’s carrying data for, and it  also has the = amount of data  being carried, and the offset of that  data from the start of the stream.
* **所以，本质上流包含的序列号与TCP序列号的作用是一样的，它们统计该流中发送的数据字节数**。 **而QUIC数据包的序列号则是计算发送数据包的数量**。So, essentially the stream contains sequence numbers  which play the same role as TCP  sequence numbers, in that they count bytes  of data being sent in that stream.  And the packets have sequence numbers that  count the number of packets being sent.

:orange: 我们可以在右边的图中看到，我们可以看到**QUIC数据包的数量在上升，0，1，2，3，4**。而**流号，零号数据包承载着第一流的数据，从零到1000的字节**。And we can see this in the  diagram on the right, where we see  the packet numbers going up, zero,  one, two, three, four. And the stream  numbers, packet zero carries data from the  first stream, bytes zero through 1000.

* 数据包1携带第一流的数据，字节1001至2000。Packet one carries data from the first  stream, bytes 1001 to 2000.
* 而数据包2则从第一流携带字节2001到2500，从第二流携带0到500，以此类推。And packet  two carries bytes 2001 to 2500  from the first stream, and zero to  500 from the second stream, and so  on.

:candy: 而且我们看到，**我们可以在一个QUIC数据包中发送多个流的数据**。And we see that we can send  data on multiple streams in a single  packet.

### QUIC不保留流的消息边界

![](/static/2021-03-02-12-18-06.png)

**QUIC不保留流内的消息边界**。 QUIC doesn't preserve message boundaries within the  streams.

* 同理，在一个**TCP流内**，如果你向流中写入数据，而你写入的数据量太大，无法放入一个数据包中，那么数据包之间可能会被任意分割 In the same way that,  within a TCP stream, if you write  data to the stream and  the amount you write is too big  to fit into a packet, it may  be arbitrarily split between packets.
  * 或者如果你在TCP流中发送的数据太小，没有填满整个数据包，可能会延迟等待更多的数据，能够填满数据包再发送。 Or if the data you send in  a TCP Stream is too small,  and doesn't fill a whole packet,  it may be delayed waiting for more  data, to be able to fill up  the packet before it’s sent.
* 同样的事情也发生在QUIC上。 **如果您写入流的数据量太大，无法放入 QUIC 数据包，那么它将被分割成多个数据包**。 The same thing happens with QUIC.  If the amount of data you write  to a stream is too big to  fit into a QUIC packet, then it  will be split across multiple packets.
  * 同样，如果你写入流的数据量非常小，QUIC可能会把它缓冲起来，延迟，等待更多的数据，这样它就可以发送，填满一个完整的数据包。Similarly, if the amount of data you  write to a stream is very small,  QUIC may buffer it up, delay it,  wait for more data, so it can  send it and fill a complete packet.

:orange:此外，如果有空间的话，**QUIC可以从多个流中获取数据，并以单个数据包的形式发送**。 In addition, QUIC can take data from  more than one stream, and send it  in a single packet, if there’s space  to do so.

* 而**如果有多个流的数据可供发送，那么quic发送方可以任意决定，如何优先处理这些数据，以及如何从每个流中发送帧**。And if there's more than one stream  with data that's available to send,  then the QUIC sender can make an  arbitrary decision, how it prioritises that data,  and how it delivers frames from each stream.
* 通常，它会拆分流中数据，所以每个数据包有一半的数据来自一个流，一半来自另一个流。但是，如果它愿意，它可以交替使用它们，发送一个数据包，**数据来自流1，一个来自流2**，以此类推。 And usually it will split those the data from the streams, so each packet has  half the data from one stream,  and half from another stream. But it  may alternate them if it wants,  sending one packet with data from stream  1, one from stream 2, one from  stream 1, one from stream 2,  and so on.

## QUIC ACK：QUIC Acknowledgment

![](/static/2021-03-02-12-23-22.png)

在接收端，**QUIC收方对收到的数据包发送确认**。 On the receiving side,  the QUIC receiver sends acknowledgments for the  packets it receives.

* 所以，**与TCP确认下一个预期的序列号不同，QUIC接收器只是发送一个确认**，说 "我收到了这个数据包"。 So, unlike TCP which acknowledges the next  expected sequence number, a QUIC receiver just  sends an acknowledgement to say “I got  this packet”.
  * 所以，**当零号数据包到达时，它会发送一个确认，说 "我收到了零号数据包"。 而当数据包1到达时，它会发送一个确认，说 "我收到了数据包1"**，以此类推。So when packet zero arrives, it sends  an acknowledgement saying “I got packet zero”.  And when packet one arrives, it sends  an acknowledgement saying “I got packet one”,  and so on.
* **发送方需要记住它在每个数据包中放了什么数据**，The sender needs to remember what data  it puts in each packet,
  * 所以当它收到第二个数据包的确认时，它知道在这种情况下，**它包含了流1的2001到2500字节，以及流2的0到500字节。 这些信息不在确认中**。 确认中的内容只是数据包的编号，所以发送方需要跟踪它是如何把数据从流放到数据包中的。  so it  knows when it gets an acknowledgement for  packet two that,  in this case, it contained bytes 2001  to 2500 from stream one, and bytes  zero through 500 from stream two.  That information isn't in the acknowledgments.  What's in the acknowledgments it's just the  packet numbers, so the sender needs to  keep track of how it puts the  data from the streams into the packets.

### QUIC ACK帧格式

QUIC中的确认也比TCP中的复杂一些，**它不只是在头中有一个确认号字段**。The acknowledgments in QUIC are also a  bit more sophisticated than they are in  TCP, in that it doesn't just have  an acknowledgement number field in the header.

* 而是在回来的数据包中以帧的形式发送确认信息。Rather, it sends the acknowledgments as frames  in the packets coming back.
* 这就提供了更多的灵活性，**因为它可以有一个相当复杂的帧格式，如果需要的话，它可以改变帧格式，包括不同的，支持不同的发送头的方式**。 And this gives a lot more flexibility,  because  it can have a fairly sophisticated frame  format, and it can change the frame  format to include different, to support different  ways of sending a header, if it  needs to.

:orange: 在QUIC的初始版本中，在帧格式中，在从接收方传回发送方的ACK帧中，**有一个字段表示最大的确认**，这与TCP确认基本相同--它告诉你**收到的最高序列号是什么**。 In the initial version of QUIC,  what's in the frame format, in the  ACK frames coming back from the receiver  to the sender,  is a field indicating the largest acknowledgement,  which is essentially the same as the  TCP acknowledgment – it tells you what's  what's the highest sequence number received.

:orange: 有一个**ACK延迟字段，它告诉你在收到该数据包之间，接收方在发送确认之前等待了多长时间**。There's an ACK delay field, that tells  you how long between receiving that packet  the receiver waited before sending the acknowledgement.

* 所以这就是接收方的延迟。而**通过测量确认回来所需的时间，并去掉这个ACK延迟字段，就可以估算出不包括收方中处理延迟的网络往返时间RTT**。 So this is the delay in the  receiver. And by measuring the time it  takes for the acknowledgment come back,  and removing this ACK delay field,  you can estimate the network round trip  time excluding the processing delays in the  receiver.

:orange:有一个ACK范围的列表。 There’s a list of ACK ranges.

* 而ACK范围是**接收方说 "我收到了一个范围的数据包 "的一种方式**。 And the ACK ranges are a way  of the receiver saying “I got a  range of packets”.
* 所以你可以发送一个确认，说：我一次就收到了5到7的数据包。你**可以把这个拆开，使用多个ACK范围**。 因此，你可以有一个确认，说 "我得到了5个数据包；我得到了7到9个数据包；我得到了11到15个数据包"，你可以在**一个单一的确认块中，在一个ACK帧中**，在反向路径流中发送所有的ACK范围确认。 So you can send an acknowledgement that  says, I got packets from five through  seven  in a single go. And you can  split this up, with multiple ACK ranges.  So you could have an acknowledgement that  says “I got packet five; I got  packets  seven through nine; and I got packets  11 through 15” and you can send  that all within a single acknowledgement block,  in an ACK frame, within the reverse  path stream.
* 而这就给了它更多的灵活性，所以它**不只是要确认最近收到的数据包，提供发送者更多的信息来进行重发**。And this gives it more flexibility,  so it doesn't just have to acknowledge  the most recently received packet, which gives  the sender more information to make retransmissions.
  * 类似TCP选择性确认ACK扩展。 This is a bit like the TCP  selective acknowledgement extension.

## QUIC重传&丢包恢复：QUIC Retransmissions and Loss Recovery

![](/static/2021-03-02-12-41-47.png)

和TCP一样，QUIC会重传丢失的数据。Like TCP, QUIC will retransmit lost data.

* 不同的是，**TCP重传**的数据包，和原来发送的数据包一模一样，**所以重传的数据包看起来和原来的数据包一样**。 The difference is that TCP retransmits packets,  exactly as they would be originally sent,  so the retransmission looks just the same  as the original packet.
* **QUIC从不重传数据包**。QUIC中的每个数据包都有一个唯一的数据包序列号，**每个数据包只传输一次**。 QUIC never retransmits packets. Each packet in  QUIC has a unique packet sequence number,  and each packet is only ever transmitted  once.
  * QUIC宁可做的，**是将这些数据包中的数据重新传输到一个新的数据包中**。 What QUIC rather does, is it retransmits  the data which was in those packets  in a new packet.

:orange: 因此，在这个例子中，我们看到的数据包，在幻灯片上，我们看到，**数据包号2得到了丢失，它包含的数据字节2001至2500从流1，和字节0到500从流2**。 So in this example, we see that  packet, on the slide, we see that  packet number two got lost, and it  contain the data bytes 2001 to 2500  from stream one, and bytes zero through  500 from stream two.

* 而且，当它收到表示数据包丢失的确认ACK时，它会重新发送数据。 And, when it gets the the acknowledgments  indicating that packet was lost, it resends  that data.
* 而在这种情况下，**它在发送第六个数据包，它在重新发送流的第一个数据字节，它在发送流一的2001到2500字节，它最终会在以后的某个时刻，重新发送流二的数据**。 And in this case it's sending in  packet six, it’s resending the first bytes  of data from stream, it’s sending the  bytes 2001 to 2500 from stream one,  and it will eventually, at some point  later, retransmit the data from stream two.

正如我们所说，**每个数据包都有唯一的数据包序列号**。由于每个数据包在到达时都会被确认，而且它不是以TCP的方式确认最高的，不是以TCP的方式确认下一个预期的序列号，**所以你不能以同样的方式进行三重重复的ACK**，As we say, each packet has a  unique packet sequence number. Since each packet is acknowledged as it  arrives, and it's not acknowledging the highest,  not acknowledging the next sequence number expected  in the same way TCP does,  you can’t do the triple duplicate ACK  in the same way,

* 因为你不会得到重复的ACK。**每个ACK都会确认下一个新的QUIC数据包** because you don't  get duplicate ACKs. Each ACK acknowledges the  next new packet.
* **而是QUIC在收到三个数据包的ACKS时，就宣布一个数据包丢失**，而这三个数据包的数据包号比它发送的那个数据包高。 这时，它就可以重传该数据包中的数据。 Rather QUIC declares a packet to be  lost when it's got ACKS for three  packets with higher packet numbers than the  one which it sent.  At that point, it can retransmit the  data that was in that packet.
* **而这就是QUIC相当于三倍重复的ACK，它是三个跟随的序列号而不是三个重复的序列号**。 And that’s QUIC’s equivalent to the triple  duplicate ACK; it's three following sequence numbers  rather than three duplicate sequence numbers.

:orange: 还有，就像TCP一样，**如果出现超时，它停止接收ACK，它就宣布数据包丢失**。 And also, just like TCP, if there's  a timeout, and it stops getting ACKs,  then it declares the packets to be  lost.

## QUIC队首阻塞：Head-ofLine Blocking in QUIC

![](/static/2021-03-02-12-50-08.png)

QUIC **在一个连接中传送多个数据流。在每个数据流中，数据都能按照发送的顺序可靠地传送**。 QUIC delivers multiple streams within a single  connection. And within each stream, the data  is delivered reliably, and in the order  it was sent.

* 如果一个数据包的丢失，那么显然会导致该**数据包中包含的数据流、流的数据丢失**。 If a packet’s lost, then that clearly  causes data for the stream, streams,  where the data was included in that  packet to be lost.

:orange: **数据包丢失是否会影响一个或多个流**，其实取决于**发送者如何选择将不同流的数据放入数据包中**。Whether a packet loss effects one,  or more, streams really depends on how  the sender chooses to put the data  from different streams into the packets.

* **一个QUIC数据包有可能包含来自多个流的数据**。我们在例子中看到，数据包同时包含来自流一和流二的数据。It’s possible that a QUIC packet can  contain data from several streams. We saw  in the examples, how the packets contain  data from both stream one and stream  two simultaneously.
* **在这种情况下，如果一个数据包丢失，会影响到两个流，如果数据包中有两个以上流的数据，则会影响到所有的流**。 In that case, if a packet is  lost, it will affect both of the  streams, all of the streams if there’s  data from more than two streams in  the packet.

:orange: 同样，一个QUIC发送者可以选择其他方式，**发送一个数据流1的数据包，然后再发送一个数据流2的数据包，并且在每个数据包中只放一个数据流的数据**。 Equally, a QUIC sender can choose to  alternate, and send one packet with data  from stream one, and then another packet  with data from stream two, and only  ever put data from a single stream  in each packet.

:orange: 规范没有对发送者如何做提出要求，不同的发送者可以根据他们是想同时在每个流上取得进展，还是想他们想交替进行，选择不同的做法，并确保数据包丢失只影响到单个流。The specification puts no requirements on how  the sender does this, and different senders  can choose to do it differently depending  whether they're trying to make progress on  each stream simultaneously, or whether they want  to  they want to alternate, and make sure  that packet loss only ever affects a  single stream.

* 根据它们的方式，**流的行首阻塞单独进行**。Depending on how they do this,  the streams can suffer from head of  line blocking independently.
  * **如果数据在一个特定的流上丢失了，那么在丢失的数据没有被传输之前，这个流就不能向应用程序传送以后的数据**。但是**其他的流，如果它们已经得到了所有的数据，就可以继续向应用程序传送**。  If data is lost on a particular  stream, then that stream can't deliver later  data to the application, until that  lost data has been transmitted. But the  other streams, if they've got all the  data, can keep delivering to the application.
* **所以每个流单独遭受队首阻塞，但流之间没有队首阻塞**。So streams suffer from head of line  blocking individually, but there's no head of  line blocking between streams.
  * 这意味着数据在一个流上能可靠地、有序地传输，但流与流之间的顺序并没有得到保留 This means that the data is delivered  reliably, and in order, on a stream,  but order’s not preserved between streams.
* 很有可能一个流被阻断，等待数据包中的部分数据重传，而其他流在继续传送数据，还没有看到该流有任何丢包。 It’s quite possible that one stream can  be blocked, waiting for a retransmission of  some of the data in the packets,  while the other streams are continuing to  deliver data and haven't seen any loss  on that stream.

:orange:**每个流都是独立发送和接收的。 而这意味着，如果你小心翼翼地将数据分割到不同的流中，如果实现时小心翼翼地将流中的数据放到不同的数据包中，就可以限制队首阻塞的持续时间，并使流在队首阻塞和数据传输方面独立**。 Each stream is sent and received independently.  And this means if you're careful with  how you split data  across streams, and if the implementation is  careful with how it puts data from  streams into different packets, it can limit  the duration of the head of line  blocking, and make the streams independent in  terms of head of line blocking and  data delivery.

## 总结-QUIC可靠传输：Reliable Data Transfer with QUIC

![](/static/2021-03-02-13-02-55.png)

QUIC delivers, as we've seen, several ordered,  reliable, byte streams of data in a  single connection.正如我们所看到的，QUIC在一个连接中提供了几个有序的、可靠的、字节流的数据。

* 如何对待这些不同的字节流，我想，还是一个解释的问题。 How you treat these different bytes streams,  is, I think, still a matter of  interpretation.
* **可以把一个QUIC连接当作是几个并行的TCP连接**。 It's possible to treat a QUIC connection  as though it was several parallel TCP  connections.
  * 所以，**你不是打开多个TCP连接到服务器，而是打开一个QUIC连接，并在其中发送和接收多个数据流**。 So, rather than opening multiple TCP connections  to a server, you open one QUIC  connection, and you send and receive several  streams of data within that.
  * 然后你把**每一个数据流当作是一个TCP流**，你把数据的解析和处理当作是一个TCP流。 而且你可能会在**每个数据流上发送多个请求，并得到多个响应**。 And then you treat each stream of  data as-if it were a TCP stream,  and you parse and process the data  as if it were a TCP stream.  And you possibly send multiple requests,  and get multiple responses, over each stream.
* 或者，你可以把流更多地当作一个帧设备。你可以选择把每个流，解释为发送一个单一的对象。然后，当你从流中发送数据时，**在该流上，一旦你发送完这个对象，你就关闭该流，继续使用下一个流**。 Or, you can treat the streams more  as a framing device.   you can choose to interpret each stream,  as sending a single object. And then,  when you send data from the stream,  on that stream, once you finish sending  that object, you close the stream and  move on to use the next one.
  * 而在接收端，你只**需要读取所有的数据，直到你看到流的终点标记，然后你处理它，知道你已经得到了一个完整的对象**。 And, on the receiving side, you just  read all the data until you see  the end of stream marker, and then  you process it knowing you’ve got a  complete object.

:orange: **最佳做法，即对QUIC连接的思考方式，以及连接中的流，仍在不断发展**。 目前还不清楚这两种方法中哪一种一定是正确的方式。the best practices,  the way of thinking about a QUIC  connection, and the streams within a connection,  is still evolving.And it's not clear which of these  two approaches is the necessarily the right  way to do it.

* 这可能取决于应用，什么才是最合理的。 it probably depends on the application what  makes the most sense.

