# Content

* [Content](#content)
* [Evolving the Network](#evolving-the-network)

# Evolving the Network

该课程的重点是**互联网如何改变和发展以应对未来的挑战**。The course has focussed on how can the Internet change and evolve to address coming challenges:

- **如何在一个分散的网络中建立连接**？How to establish connections in a fragmented network?
  - 它的重点是我们如何在一个日益分散的网络中建立连接，思考网络地址转换的问题，IPv6和双栈主机的兴起的问题，我已经详细地讲了当机器不一定在一个共同的寻址领域时，当有多种不同的方式可能到达一台机器时，建立连接的挑战 It’s focused on the issues of how  we establish connections in an increasingly fragmented  network,  thinking about the issues with network address  translation, the issues with the rise of  IPv6 and dual-stack hosts,  and I’ve spoken in some detail about  the challenges in establishing connections when the  machines are not necessarily in a common  addressing realm, and when there are multiple  different ways of potentially reaching a machine. 
  - 这就是诸如用于穿越NAT的ICE算法，以及用于IPv6连接竞赛的Happy Eyeballs技术等技术 And this is techniques such as the  ICE algorithm for NAT traversal, and the  Happy Eyeballs technique for connection racing for  IPv6.
- **如何通过加密来防止无孔不入的监控和防止运输僵化**？How can encryption protect against pervasive monitoring and prevent transport ossification?
  - 我已经谈到了一些关于加密的问题，以及防止普遍的网络监控，防止和预防传输僵化的问题 some of the issues  with encryption, and  protecting against pervasive network monitoring, and protecting  against, and preventing, transport ossification.
  - 这导致了QUIC的一些设计，整个协议，包括绝大多数的传输层头，都是加密的，而那些没有加密的则被greased，以允许进化。 这部分是一种安全措施，部分是一种可进化性和反僵化措施。它正在研究如何通过**部署加密来防止中间箱干扰我们的通信，从而不断改变协议** And this led to some of the  design of QUIC, with the entire protocol,  including the overwhelming majority of the transport  layer headers, being encrypted, and those which  are not encrypted being greased in order to allow evolution.  And that's partly a security measure,  and it's partly an evolvability and anti-ossification  measure. It’s looking at ways in which  we can keep changing the protocols by  deploying encryption to prevent middleboxes interfering with  our communications.
- **我们如何减少延迟并支持实时和互动内容**？How can we reduce latency and support real-time and interactive content?
  - 部分原因是，在QUIC等协议的设计中，在TLS 1.3的设计中，减少了建立连接所需的往返次数。 它出现在像内容分发网络这样的系统中，这些系统将内容移到靠近边缘、靠近客户的地方，以减少延迟  And, partly, this comes in, again,  in the design of protocols like QUIC,  in the design of TLS 1.3 with  reducing the number of round trips needed  to set up a connection.  It comes in, in  systems like content distribution networks that move  content nearer the edges, near the customers,  to reduce latency.
  - 它还出现在实时应用和RTP等协议的设计中  And it comes in, in the design  of real-time applications and protocols like RTP.
- **我们如何适应无线网络的变化无常**？How can we adapt to the vagaries of wireless networks?
- **我们如何识别和分发内容？我们如何管理对DNS和命名的控制权的争斗**？How can we identify and distribute content? How can we manage the tussle for control of the DNS and naming?
  - 还有一些挑战，以及对DNS和命名控制的争夺，以及这些与审查和过滤的关系，还有DNS如何被用来支持内容分发网络   And some of the challenges, and the  tussle for control of the DNS and  naming,  and how those relate to censorship and  filtering, but also how the DNS can  be used to support content distribution networks.
- **我们如何管理域间路由以有效地交付内容**？How can we manage interdomain routing to efficiently delivery content?
- **我们怎样才能重新分散网络的注意力**？How can we re-decentralise the network?
  - 网络去中心化的讨论，以及超巨头和内容分发网络的兴起，它们将内容集中在少数供应商身上 decentralisation of the network, and the  rise of hyper-giants and content distribution networks,  that centralise content onto a small number  of providers.

---

为了应对这些挑战，互联网正处于一个重大的变革之中 To address these challenges, the Internet is in the middle of a significant change

- **以IPv6为基础进行路由和转发的演变** IPv6 as a basis for routing and forwarding evolution
  - IPv6开始被大量部署。 部分原因是，这提供了更多的地址空间，以及网络上越来越多的设备。 由于地址空间的大小，它也足够灵活，人们开始关注他们能用IPv6做什么来发展网络的方式 We're seeing IPv6 beginning to be significantly  deployed.  And, partly, this is providing for increased  address space,  and increasing numbers of devices on the  network.  But it is also flexible enough,  because of the size of that address  space, that people are starting to look  at what they can do with IPv6  to evolve the way the network is  being developed.
  - 它的灵活性在于，它的地址中有足够多的比特，**因此可以为地址分配语义**。因此，比特可以有其他的意义，也许只是网络上设备的位置。 It's flexible, in that it’s got enough  bits in the address, that semantics can  be assigned to the addresses. So bits  can have meaning other than, perhaps,  just the location of a device on  the network.
  - 它有一个非常**灵活的报头扩展机制，可用于分层功能，提供额外的语义，并提供应用语义，作为数据包报头的一部分，以允许特殊处理**。And it's got a very flexible header  extension mechanism that can be used to  layer-in features,  provide extra semantics,  and provide application semantics, as part of  the packet headers, to allow special processing.
- **TLS 1.3用于简化和提高安全性** TLS 1.3 to simplify and improve security
  - TLS 1.3的推出，它极大地改善和简化了安全。TLS 1.3 be rolled-out,  and massively improve and simplify security.
  - 我们已经看到它被纳入了QUIC协议，作为未来传输演进的基础 And we've seen it be incorporated into  the QUIC protocol, as the basis for  future transport evolution.
  - 我已经把QUIC描述为本质上是一个更好的TCP版本，或者说是一个**加密的TCP版本，它结合了TCP和TLS的目标，还增加了多流的概念**。QUIC as essentially a  better version of TCP, or as an  encrypted version of TCP, which combines the  goals TCP and TLS, and also adds  this idea of multi-streaming.
- **QUIC作为未来传输演进的基础，避免了协议僵化** QUIC as a basis for future transport evolution, avoiding protocol ossification
  - QUIC实际上将成为**更多发展的基础，以及更多的演变**。 在某种程度上，我们已经看到了这一点。But I think QUIC is actually going  to be the basis for a lot  more developments, and a lot more evolution.  We’re already seeing this, to some extent.
  - 已经有一个QUIC的数据报扩展正在通过标准程序，开始在QUIC上有效地支持**实时应用**，而且很明显，我们将在这个领域看到更多的演变和发展，人们使用**QUIC作为未来传输协议的基础，用于实时和互动应用**，等等。There is already a datagram extension to  QUIC going through the standards process,  to start supporting real-time applications effectively over  QUIC, and it's pretty clear that we're  going to see a lot more evolution  and development in that space, with people  using QUIC  as the basis for future transport protocols,  for real-time and interactive applications, and so  on.
- **HTTP/3作为网络演进的基础** HTTP/3 as a basis for web evolution
  - 采用HTTP/3作为网络发展的基础，HTTP的发展超越了网络文件，包括更丰富的实时和互动服务 adoption of HTTP/3 as the  basis for evolving the web,  at HTTP growing beyond web documents to  include a much richer set of real-time  and interactive services.
- **加密的DNS（HTTPS、TLS、QUIC）用于安全的名称解析** DNS over encryption (HTTPS, TLS, QUIC) for secure name resolution
  - DNS的变化，许多通过加密运行DNS的方法，无论是通过HTTPS的DNS，还是通过TLS，或者通过QUIC，以获得安全的名称解析。并避免一些控制点 in parallel to this, I think  we've seen the rise of changes to  the DNS, many ways of running DNS  over encryption, whether it's a DNS over  HTTPS, or over TLS, or over QUIC,  in order to get secure name resolution.And to avoid some of the control  points.
- **CDN和覆盖** CDNs and overlays
  - CDN和覆盖越来越多地使用DNS来引导内容的主机。 And we’re seeing CDNs and overlays  increasingly making use of DNS  for directing hosts of the content.

不同行业和不同供应商之间的控制权争夺越来越激烈 And I think we're seeing an increasing  tussle for control, between the different industries  and the different providers.

* 有了QUIC、TLS、HTTP/3和加密的DNS，允许应用提供商和他们的客户以及最终用户直接对话，并限制网络对这种通信的可见性。with QUIC,  and TLS, and HTTP/3, and encrypted DNS  to allow the application providers, and their  customers, and the end-users, to talk directly,  and to limit the visibility of the  network into that communication.
* 另一方面，我们的运营商试图在他们的网络中建立应用意识，试图增加网络和终端之间的通信，以提高性能，并销售增强的服务。 这是一场竞争，不清楚它将如何发展。operators trying to build application awareness into  their networks, trying to increase the communication  between the network and the endpoints,  to improve performance, and to sell enhanced  services.  And there’s a tussle, where it’s not  clear how it's going to play out.

