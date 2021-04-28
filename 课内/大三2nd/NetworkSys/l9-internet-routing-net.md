# Content

机翻

* [Content](#content)
* [数据中心](#数据中心)
* [Networks and Internet Routing](#networks-and-internet-routing)
* [内容分发网：Content Distribution Networks (CDNs)](#内容分发网content-distribution-networks-cdns)
  * [定义](#定义)
  * [负载均衡：Using CDNs to Distribute Load](#负载均衡using-cdns-to-distribute-load)
  * [减少时延:Using CDNs to reduce latency](#减少时延using-cdns-to-reduce-latency)
  * [使用DNS定位最近的CDN节点：Locating the Nearest CDN Node using DNS](#使用dns定位最近的cdn节点locating-the-nearest-cdn-node-using-dns)
  * [使用任播路由定位最近的CDN节点:Locating the Nearest CDN Node using Anycast Routing](#使用任播路由定位最近的cdn节点locating-the-nearest-cdn-node-using-anycast-routing)
  * [最近CDN节点选择](#最近cdn节点选择)
* [域间路由：Inter-domain Routing](#域间路由inter-domain-routing)
  * [Autonomous Systems](#autonomous-systems)
  * [AS网络拓扑图：AS-level Internet Topology Graph (IPv4)](#as网络拓扑图as-level-internet-topology-graph-ipv4)
    * [AS-level Internet Topology Graph (IPv4 and IPv6)](#as-level-internet-topology-graph-ipv4-and-ipv6)
    * [IPv4/6拓扑区别：Differences Between IPv4 & IPv6 AS-level Topologies](#ipv46拓扑区别differences-between-ipv4--ipv6-as-level-topologies)
  * [边界路由:Routing at the Edge](#边界路由routing-at-the-edge)
  * [核心路由：Routing in the Core of the Internet](#核心路由routing-in-the-core-of-the-internet)
    * [DFZ](#dfz)
  * [路由政策：Routing Policy](#路由政策routing-policy)
  * [Border Gateway Protocol (BGP)](#border-gateway-protocol-bgp)
  * [EBGP路由表：Routing Information Exchanged in eBGP](#ebgp路由表routing-information-exchanged-in-ebgp)
  * [利用路由表绘制AS拓扑：AS Topology Graph](#利用路由表绘制as拓扑as-topology-graph)
  * [EBGP路由政策：Routing Policy in eBGP](#ebgp路由政策routing-policy-in-ebgp)
  * [路由决策过程：BGP Routing Decision Process](#路由决策过程bgp-routing-decision-process)
* [路由安全：Routing Security](#路由安全routing-security)
  * [网络路由不安全问题：Internet Routing is not Secure](#网络路由不安全问题internet-routing-is-not-secure)
  * [资源公共密钥基础架构：Resource Public Key Infrastructure (RPKI)](#资源公共密钥基础架构resource-public-key-infrastructure-rpki)
  * [可检测网络路由安全：MANRS（Mutually Agreed Norms for Routing Security）](#可检测网络路由安全manrsmutually-agreed-norms-for-routing-security)
* [域内路由：Intra-domain Routing](#域内路由intra-domain-routing)
  * [域内单播路由：Intra-domain Unicast Routing](#域内单播路由intra-domain-unicast-routing)
  * [距离矢量路由：Distance Vector Routing](#距离矢量路由distance-vector-routing)
  * [链路状态路由：Link State Routing](#链路状态路由link-state-routing)
  * [Distance Vector vs. Link State](#distance-vector-vs-link-state)
  * [域内路由的挑战：Challenges in Intra-domain Routing](#域内路由的挑战challenges-in-intra-domain-routing)

# 数据中心

数据中心一词来表示**一个维护有许多服务器的位置**。**数据中心是一个容纳许多联网计算机的设施**，这些计算机协同工作以处理、存储和共享数据。大多数主流高科技公司都高度依赖数据中心，作为提供在线服务的中心组件。

CDN 缓存服务器位于何处？
**CDN 缓存服务器位于全球各地的数据中心中**。Cloudflare 的 CDN 服务器遍布全球 200 个城市，以尽可能缩短与访问内容的最终用户的距离。**CDN 服务器所在的位置也称为数据中心。**

# Networks and Internet Routing

Networks and Internet Routing

* CDN - content distribution networks
* inter-domain routing
  * how operators deliver data across the wide-area network
* routing security
* intra-domain routing
  * how routing within an operator’s network.

# 内容分发网：Content Distribution Networks (CDNs)

- 负载平衡和减少延迟 Load balancing and latency reduction
- 使用DNS实现 Implementation using DNS
- 使用任播路由的实现 Implementation using anycast routing

---

## 定义

【内容分发网络(cdn)提供可伸缩性、负载平衡和低延迟 Content distribution networks (CDNs) provide scalability, load balancing, and low latency】**内容分发网络是一种为网络内容提供可伸缩的、负载平衡的、低延迟的托管服务** A content distribution network is a service  which provides scalable, load-balanced, low-latency hosting for  web content.  

* CDN 运营商、 Akamai、 CloudFlare 和 Fastly 等公司都从事为客户托管内容的业务 CDN operators, companies such as Akamai,  CloudFlare, and Fastly, are in the business  of hosting content for their customers.
  * 他们的客户给CDN的网络内容，这可能是图片，可能是软件上传，可能是视频，不管是什么，任何可以放在网上的东西  Their customers give them web content,  and this may be images, it may  be software uploads, it may be video,  doesn't matter what it is, anything that  can sit on the web.  
* 【**在边缘网络和数据中心为客户提供网络缓存中的内容** Host content for their customers in web caches spread around the world in edge networks and data centres】而 **CDN** 承载着遍布世界各地的网络缓存中的内容。**其中一些位于数据中心，其中一些位于由不同 isp 操作的边缘网络** And the CDN hosts that content in  web caches that are spread around the  world.  And some of these are located in  data centres, some of these are located  in edge networks operated by various ISPs. 
* 【**减少主服务器的负载——而不是保留文件的本地副本，它们链接到 cdn 托管的副本** Reduces load on the main servers – rather than keep a local copy of the files, they link to the CDN-hosted copy】为了减少主服务器的负载。<font color="red">客户没有保留文件的本地副本，而是将其提供给 CDN 并链接到 CDN 承载的副本</font> the idea is to reduce the  load on the main servers.  Rather than keeping a local copy of  the file, the customer gives it to  the CDN and links to the copy  hosted by the CDN. 
  * 这减少了客户的负载，**并将负载放到 CDN 上**。这个想法是，<font color="red">cdn 足够大，在足够的数据中心和足够的边缘网络中有足够的缓存，这将负载分散到世界各地，并防止高流量网站超载</font> this reduces  the load on the customer, and puts  the load onto the CDN.  The idea is that the CDNs are  big enough, and have enough caches in  enough data centres and enough edge networks,  that this spreads the load throughout the  world, and prevents from being overloaded for  high traffic sites.
* 【**减少响应请求的延迟** Reduces latency to respond to requests】它减少了请求的延迟，因为 <font color="deeppink">CDN 将在发出请求的人附近有一个缓存，并且可以遍布全世界</font>  It reduces latency for the requests,  because the CDN will have a cache  near to the person making the request,  and  can sprinkle around the world.
* 【**减少成功分布式拒绝服务攻击的机会** Reduces chances of successful denial-of-service attack】而且它降低了分布式拒绝服务攻击组织成功攻击的机会，这也是**因为 CDN 的规模和它拥有的缓存数量** And it  reduces the chances of a successful denial  of service attack,  again just because of the sheer size  of the CDN, and the sheer number  of caches it has
* 【**许多商业 cdn 可用，许多大型机构也运行自己的 cdn** Many commercial CDNs available，Many large organisations run their own，】
  * 特别是，所谓的超级巨头，大公司，如谷歌，Facebook，Netflix，苹果等，都经营自己的大规模内容分销网络。

## 负载均衡：Using CDNs to Distribute Load

Cdn 的目标是分发负载（负载均衡） The goal of CDNs is to distribute  load.

* 【**Cdn 通过定位世界各地的网络缓存和从本地缓存响应大多数请求来分配负载** CDNs distribute load by locating web caches around the world and answering most requests from a local cache】它们通过缓存世界各地的内容和回答大多数来自本地缓存的请求来分配负载 They distribute load by caching content  all around the world, and by answering  most requests from a local cache.
  * 而且，为了做到这一点，他们需要到处都有服务器。他们需要非常大，并有非常广泛的地理分布   And, in order to do that,  they need to have servers located everywhere.  They need to be very large, and have very wide geographical distribution.
* 【**需要广泛的投资，与互联网服务供应商、互联网交换点等进行大规模合作** Needs extensive investment, large-scale cooperation with ISPs, Internet Exchange Points, etc.】这意味着他们需要大规模的投资，与网络运营商的大规模合作，与 isp 的合作，与互联网交换点的合作，与数据中心的合作，等等  This means they need a large-scale investments,  large-scale cooperation with network operators, with ISPs,  with Internet exchange points, with data centres,  and the like.
  * 他们尽可能地把**缓存**设置在**离客户最近的地方**  And they try to host caches as  near to the customers as they can.
* 【**互联网服务提供商(ISP)承载 CDN 的服务器的互利性** Mutual benefit for an ISP to host the servers for a CDN】
  * 大型ISP肯定会为其他大型CDN提供缓存服务，ISP承载这种CDN缓存的互利性
  * 【**增加 CDN 的覆盖面和健全性** Increases the reach and robustness of the CDN】很明显，从 CDN 的角度来看，**如果能够在尽可能多的网络中放置缓存，那么它将增加 CDN 的覆盖范围和健壮性** clearly, from the CDNs point of  view, it increases the reach and the  robustness of the CDN, if they can  put caches in as many networks as  possible.
  * 【**减少ISP网络的负载** Reduces the load on the ISP’s network】从 ISP 的角度来看，它减少了他们网络的负载
    * 【**发送到缓存的内容的一个副本; 然后缓存会分发多次** One copy of the content sent to cache; the cache then distributes many times】CDN 可以将**文件的一个副本推送到缓存中，然后将其分发给该 ISP 的其他客户** The CDN can push one copy of  a file into the cache, and it  can then distribute it to the other  customers of that ISP.
    * 【**避免从 ISP 到外部世界的链接过载** Avoids overload of link from ISP to outside world】这意味着<font color="red">所有的负载都来自 ISP 的网络，而不需要通过昂贵的广域连接到互联网的其他部分。这样就避免了从 ISP 到外部世界的链接过载</font> And it means that all of that  load is then served from within the  ISP’s network, without having to go over  the expensive wide-area links to the rest  of the Internet.  And this avoid overloading the links from  the ISP to the outside world..
      * 一些流行服务的规模意味着这是必要的 And the scale of some of the  popular services means that this is necessary
      * 例如，Netflix 每秒发布“数十兆兆比特”的视频——不可能从单个数据中心发送; 需要本地分发 e.g., Netflix distribute “tens of terabits per second” of video – not possible from a single data centre; needs local fanout
        * 例如，Netflix 谈到他们如何每秒发布10兆兆位的视频，而这显然是不可能从一个单一的数据中心。它必须在一个层次结构中被推出，**中央数据中心(源站)将数据推出到 CDN，CDN 将数据推出到边缘缓存，再分发给客户**   Netflix, for example, talk about how they  distribute 10s of terabits of video per  second, and this clearly isn't possible from  a single data centre.  It has to be pushed out in  a hierarchy, with the central data centre  pushing data out to a CDN,  which pushes it out to edge caches,  which distribute to the customers.
        * 你不能在一个单一的数据中心，在一个单一的网站上托管所有这些，你必须将负载分散到世界各地。You can't  host all of this from a single  data centre, from a single site,  you have to spread the load around  the world.

## 减少时延:Using CDNs to reduce latency

【**Cdn 的另一个关键好处是可以减少延迟→内容从附近的缓存传递** Key CDN benefit is reducing latency → content is delivered from nearby caches】

* 我们的目标是内容**不仅因为负载平衡的原因而分散在不同的地理位置**，还因为总是有一个**本地副本在请求数据用户附近** The goal is that the content is  not only spread geographically for load balancing  reasons, but it’s spread geographically so that  there's always a local copy near to  the person requesting the data.
  * **这样可以减少请求的延迟，因为它可以缓存在您附近**  And this reduces the latency for your  requests, because it can be cached near  to you
  * 例如，对基于美国的服务的请求通过欧洲的 CDN 缓存进行响应，而不需要跨越到美国的长途路径 e.g., requests to a US-based service are answered from a CDN cache in Europe, rather than having to cross long-distance path to the US
    * 这意味着当你从一个受欢迎的网站请求内容时，如果你在欧洲，你从一个受欢迎的网站请求内容，它不必去网站所在的美国，但可以被回答，但请求可以被回答，从位于欧洲的CDN缓存。

【但是，**需要 CDN 代理缓存的全局分布** Requires global distribution of CDN proxy caches】

* 问题 - Cdn 是否有效地为整个世界服务？ Are CDNs effectively serving the entire world?
  * 如果我们看看这张来自 Netflix 的图片，我们会发现如果你在欧洲或北美，肯定有很多 CDN 缓存，而且有一个就在你附近。如果你位于南美洲的某些地区，如果你位于西澳大利亚人口稠密的地区，如果你位于新加坡，或者日本，或者其他类似的地方，你附近就会有 CDN
  * 但是，如果你**在非洲，你可能得不到很好的服务。如果你在亚洲的大部分地区，你就不会得到很好的服务**
* 【**向世界发展中地区提供互联网接入需要的不仅仅是提供连接** Providing effective Internet access to developing regions requires more than just connectivity; also needs data centres and infrastructure to host the CDN nodes】
  * 如果你想为非洲部分地区，比如亚洲部分地区，提供高质量的互联网接入，你不仅需要提供带宽，<font color="red">你不仅需要提供网络链接，你还需要提供数据中心，这些数据中心可以承载 CDN 缓存</font>  If you want to provide high-quality Internet  access to parts of Africa, for example,  or parts of Asia which don't have  it yet, you don't just need to  provide bandwidth, you don't just need to  provide network links, you need to provide  data centres that can host CDN caches.
* **因此，它增加了获得良好网络性能所需的投资成本** So it's increasing the investment needed to  get good performance.

【**可缓存的静态内容(视频、软件更新、图像)可能需要边缘计算基础设施来支持其他应用程序**  Effective for cacheable static content – video, software updates, images – may need edge compute infrastructure to support other applications】

* 这对可缓存的静态内容有效。内容发布网络一直专注于视频、图像、软件更新，以及发布大型文件，它们在这方面的表现令人难以置信地好  And this works for cacheable static content.  CDNs have historically been focused on video,  and images, and software updates, and distributing  large files, and they work incredibly well  for that.
* 但我们也开始看到人们谈论**边缘计算应用程序**。还有一些应用程序，在**靠近客户的地方进行某种计算**。这通常适用于扩增实境游戏和类似的应用程序，你需要【**低延迟的计算服务器---- 数据中心**】 But we're also starting to see people  talk about edge compute applications.  And applications where there is some sort  of computation going on near to the  customer. And this tends to be for  augmented reality games, and applications like that,  where you need low latency to the  compute server, the data centre
  * <font color="red">Cdn 开始承载这类内容，开始允许计算被推入边缘（边缘计算）</font> CDNs are starting to host  this sort of content, starting to allow  compute to be pushed into the edges.
  * **这意味着他们不仅需要【缓存】和【边缘数据存储】，还需要大规模的【计算基础设施**】   And, again, this means that they don't  just need caching and data storage at  the edges, but they need large scale  computing infrastructure.
  * 世界上**发达国家**的这一目标也是显而易见的。在**发展中国家**，这种基础设施还没有到位  Again, developed parts of the world this  is eminently achievable. In developing, in less  well-developed parts of the world, this infrastructure  isn't yet there.

## 使用DNS定位最近的CDN节点：Locating the Nearest CDN Node using DNS

那么，CDNs 是如何工作的呢？他们如何找到最近的节点来传递内容【**我们怎么知道用户的所在位置从而给他分配最佳的cdn节点呢**---dns定位】？So, how do the CDNs work? How  do they find the nearest node in  order to deliver the content?

:orange: **CDN 节点只是一个网络服务器**，上面有文件，**通过 HTTP 传递文件** a CDN node is just a web  server which has the files located on  it, and it just delivers them using  HTTP. The question is how you find  the right CDN node, that has the  file you're looking for.

* **有两种方法找到最近CDN节点**
  * DNS
  * Anycast路由技术

:orange: **对于使用 DNS 的 CDN，目标是通过对 DNS 查询，找到最近的 CDN 节点，并给出节点位置**  For the CDNs they use the DNS,  the goal is that they locate the  nearest CDN node based on,  and give you an answer for where that node is, by playing games with  the DNS queries.

* 【**CDN 托管的每个资源都有一个唯一的 DNS 名称——每个资源都有一个不同的域名** Each resource hosted by the CDN has a unique DNS name – each resource is given a different domain name】<font color="deeppink">当 CDN 的客户将资源提供给要托管的 CDN 时，CDN 将提供该资源对应的唯一的域名</font> when a customer  of the CDN gives a resource to  the CDN to be hosted, the CDN  gives that resources unique domain name.
  * **CDN 上的每个图像、每个资源、每个文件都有一个唯一的主机名**
  * 例如，如果“ site example. com”试图在 CDN 上托管一个小猫的图像，CDN 会给它一个唯一的主机名  For example, if the "site example.com" is  trying to host an image of a  kitten on a CDN, the CDN would  give that a unique hostname
    * e.g., the CDN hosts https://example.com/images/kitten.jpg as https://9BC1C10B7947A890B9.cdn-example.com/kitten.jpg
* 【**注意，资源对应的域名不一定总是指向真实主机（源站**）they don't alway refer  to real hosts. 】<font color="deeppink">域名都是 DNS 中指向缓存中特定服务器的条目，但是它提供了灵活性</font> They’re all entries in  the DNS which point to a particular  server in the cache, but it gives  the flexibility.
  * 因为 CDN 上的每个文件，每张图片，每段内容，都有一个不同的主机名，**CDN 可以为每个图片，每个文件返回一个不同的 IP 地址，并且它可以指向一个适当的副本**   Because every file, every image, every piece  of content, on the CDN has a  different hostname, the CDN can return a  different IP address for each image,  each file, and it can point it  at an appropriate replica
* 【CDN 的 DNS 服务器为一个域名返回不同的 a 或 AAAA 记录，**具体取决于请求位置，哪个CDN 缓存中有数据**】 DNS server for the CDN returns different A or AAAA records for a name, depending on where it’s requested from, what CDN caches have the data
  * 【**根据进行查询的DNS解析程序的 IP 地址指向本地缓存**】 Directs to local cache based on IP address of resolver making the query
    * CDN 在获得此主机的DNS域名查找时，通过返回一个**引用本地缓存的不同 IP 地址**来重定向它 CDN,  when it gets the name look-up for  this  host, redirects it by returning a different  IP address that refers to a local  cache.
    * 如果我在家里查找这个域名，我可能会在我的 ISP 里找到一个特定的 CDN 缓存，如果你在**不同 ISP 的网络里，在你家里做同样的查找，你会得到一个不同的 IP 地址，指向不同的由 CDN 托管的缓存**   And if I look up  this name from my home, I might  get particular CDN cache located in the  ISP I have, and if you make  the same look-up from your home,  in a different ISP’s network, you’ll get  a different IP address back for that  name, pointing to a different cache that's  hosted by the CDN.
    * <font color="red">这是基于解析器的 IP 地址，因为 CDN 看到的只是来自本地（DNS服务器）解析器的请求</font>  And this is based on the IP  address of the resolver, because all the  CDN sees is the requests coming from  the local resolvers.
  * 【**远程解析器的 DNS 客户机子网扩展[ RFC7871]** DNS client subnet extension [RFC7871] for remote resolvers】但是 DNS 解析器有一个名为 DNS 客户端子网扩展的扩展，<font color="red">所以如果客户端和解析器不在同一个地方，解析器可以告诉 CDN 客户端的 IP 地址</font>  But the DNS resolver has an extension  called DNS client subnet extension, so if  the client is not in the same place as the resolver, the resolver can  tell the CDN the IP address where  the client came from.
* <font color="deeppink">CDN 必须查找发出请求的 IP 地址，无论是解析器的 IP 地址还是客户机子网扩展的 IP 地址，并尝试猜测它在世界的哪个地方。它需要查找 IP 地址，并有一个 IP 地址到位置的映射</font> And the CDN has to look-up the  IP address where it sees the requests  coming from, that of the resolver or  that of the client with the client  subnet extension, and try and guess where  in the world it is.  It needs to look-up the IP address,  and have a mapping of IP addresses  to locations.
  * 这并不需要特别精确。我们的目标是弄清楚你是否在英国，并且直接连接到位于伦敦的缓存，而不是纽约的缓存。它并不真的在乎你是否意识到你在格拉斯哥，或者曼彻斯特，或者其他什么地方，主要的是它知道你在英国，所以你应该去英国的缓存  And this doesn't need to be particularly  accurate. The goal is to figure out  if you're in the UK, and direct  to the cache based in London,  rather than the cache based in New  York, for example
* 【**允许非常细粒度的控制，但给 DNS 带来高负载** Allows very fine-grained control, but puts high load on DNS】这使 CDN 具有非常细粒度的控制
  * 它可以把 **DNS 响应**的**存活时间降低到几秒钟**，所以每次客户端查找一个图像，对于 CDN 托管的每个不同的图像，每个不同的资源，它都可以返回不同的答案 And this gives the CDN very fine-grained  control. It can put the time-to-live on  its DNS responses down to be a  small number of seconds, so  every time a client looks up an  image, for every different image, every different  resource the CDN is hosting, it can  return a different answer.
  * 因此，它可以在不同的数据中心之间，快速地在不同的缓存之间进行负载平衡。So it can  very rapidly load balance among it’s different  caches,  amongst it’s different data centres.
  * **但是它给 DNS 带来了很高的负载，这意味着有大量的 DNS 查询正在发生，而且它们不能被长时间缓存** But it  puts a high load on the DNS,  it means there's lots of DNS queries  happening, and they can't be cached for  very long.

## 使用任播路由定位最近的CDN节点:Locating the Nearest CDN Node using Anycast Routing

如果**同时向同一源站服务器发出许多请求**，该服务器可能会**不堪重负，无法有效响应其他传入请求**。

* 在 Anycast 网络中，主要流量不是由一台**源站**服务器来承担，其**负载可以分散到其他可用的数据中心（也就是缓存，CDN服务器所在位置**），每个数据中心都具有能够处理和响应传入请求的服务器

![](/static/2021-04-27-15-03-04.png)

:orange: Cdn 使用的另一种方法是选播路由。**以一种更加传统的方式使用 DNS** The other approach CDNs use, is known  as anycast routing.  And this doesn't play games with DNS,  it uses the DNS in a much  more traditional way.

* 因为 缓存CDN 的 DNS 域名总是相同的; 它们总是只引用 CDN。所以IP也相同  In that  the DNS names for the CDN always  the same; they always just refer to  the CDN. And they always return the  same answer

:orange: 【**CDN托管的每个资源都有一个独特的文件名** Each resource hosted by the CDN has a unique filename】CDN 托管的每一个资源，都会给它一个不同的文件名

* 【**DNS域名总是映射到相同的IP地址 - 该IP地址指向CDN数据中心的负载平衡器** The DNS name always maps to the same IP address – that IP address is a load balancer at the entrance to a data centre】
* 【**CDN使用多个数据中心--都使用相同的IP地址**】The CDN uses multiple data centres – all using the same IP addresses
  * 例如，CDN 有三个数据中心，它们都使用 IP 地址192.0.2.4  example,  the CDN has three data centres,  all of which are using IP address  192.0.2.4
  * 【**每个数据中心通过BGP公布其地址** Each data centre advertises its addresses via BGP】**CDN 在世界各地有许多数据中心，它们都使用相同的 IP 地址范围**。他们将这些 IP 地址范围广告到路由系统，到 **BGP 路由系统** And the CDN has many data centres  around the world, and they all use  the same IP address ranges. And they  advertise those IP address ranges into the  routing system, into the BGP routing system
  * 【**域间互联网路由将确保流量进入离源头最近的数据中心** Inter-domain Internet routing will ensure traffic goes to the closest data centre to source】互联网路由然后确保流量到最近的数据中心来源
    * <font color="deeppink">通过从多个地方向路由发送相同的 IP 地址，路由系统确保流量到达最近的数据中心</font>  By advertising the same IP address into  the routing from multiple places, the routing  system makes sure that the traffic goes  to the nearest data centre. 

## 最近CDN节点选择

:orange: anycast是对路由的滥用。**它有意宣传来自多个地方的同一 IP 地址，让路由负责数据如何到达那里**。 And it's an abuse of routing.  It's intentionally advertising the same IP address  from multiple places,  letting the routing take care of how  the data gets there.

* Cdn 使用哪种方法？可能两者都有。  Which approach do CDNs use? Probably a  mix of both. 
* 其中一些大公司只使用基于 dns 的方法，其中一些公司使用两种方法的混合，**两种方法都有效，而且它们有不同的权衡** Some of the large ones just use  the DNS-based approach, some of them use  a mix of both approaches, and both  approaches work, and they have different trade-offs.

# 域间路由：Inter-domain Routing

- 自治系统和AS图 Autonomous Systems and the AS graph
- 边缘的路由 Routing at the edge
- 核心路由 Routing in the core
- BGP

---

【互联网，互联的网络】The Internet is a network of networks

* 从根本上说，它是作为一个独立拥有、独立运营的网络而建立的，这些网络可以相互交流，也可以协同传输数据
* **每个网络都是一个自治系统（AS**） Each network is an Autonomous System (AS)
  * 它独立运作
* **每个网络都是一个独立的路由域** Each network is a separate routing domain
  * 它在内部自己决定如何在自己的网络中路由数据 It makes its own decisions internally  how to route data around its own  network.

:orange: 域间路由是寻找从**源网络到目的网络的最佳路径的问题** Inter-domain routing is the problem of finding the best path from the source network to the destination network

:orange: 将每个网络视为路由图（"AS拓扑图"）上的一个节点 Treat each network as a node on the routing graph (the “AS topology graph”)

* 所以，它不是在网络中寻找最佳的逐跳路径，而是在组成互联网的各种网络之间寻找最佳路径 So, it's not finding the best hop-by-hop  path through the network, it’s finding the  best path between the set of networks  that comprise the Internet.
* 它将互联网中的每个网络视为图中的一个节点，也就是众所周知的 AS 拓扑图，它将网络之间的连接视为图中的边  It treats each network in the Internet  as a node on the graph,  what’s known as the AS topology graph,  and it treats the connections between the  networks as edges in the graph
* 通过 AS 图,试图找到最好的网络集合来选择，从源到目的地 And it's trying to find the best  set of networks to choose, to get  from the source to the destination across  the AS graph.

## Autonomous Systems

![](/static/2021-04-27-15-51-37.png)

【**一个自治系统（AS）是一个独立运行的网络** An Autonomous System (AS) is an independently operated network】正如我所说，互联网是一个网络的网络。这些网络中的每一个，每一个自治系统，都是独立拥有和运营的 As I said, the Internet is a  network of networks. Each of these networks,  each autonomous system, is independently owned and  operated

* .而互联网路由系统，即**边界网关协议路由系统**，就是基于这种**自治系统**的思想运作的 And the Internet routing system, the Border  Gateway Protocol, operates based on this idea  of autonomous systems, ASes.
* 【**ISP或其他组织，运营网络并希望参与域间路由** An Internet service provider, or other organisation, that operates a network and wants to participate in interdomain routing】AS 是一个ISP，或者其他一些运营网络的组织，它们想要参与路由选择 an AS is an Internet service  provider, or some other organisation that operates a  network, and that wants to participate in  the routing. 
* 【**一些组织运营一个以上的AS**】但是大公司，Facebook 和谷歌，以及类似的公司在路由意义上也是自主的系统。这些组织中的一些运营着不止一个自治系统，也许是因为他们收购了其他本身就是自主系统的公司，或者也许只是为了便于管理而将他们的网络分开 But so  are large companies, Facebook, and Google,  and the like are also autonomous systems  in the routing sense.  Some of these organisations operate more than  one autonomous system,  perhaps because they've bought other companies which  were themselves autonomous systems, or perhaps just  split their network up for ease of  administration.
  * 为了便于管理 For ease of administration
  * 由于公司的合并 Due to company mergers

:orange: 【**每个AS都有一个独特的号码，由RIR分配** Each AS is identified by a unique number, allocated by the RIR】自动化系统是通过独特的号码来识别的，被称为 AS 号码，这些号码由地区互联网注册中心分配给它们 Autonomous systems are identified by unique numbers,  are known as AS numbers, and these  are allocated to them by the Regional  Internet Registries

* .**这些 AS 数字其实没有任何意义，除了标识** The AS numbers don't really have any  meaning, except that they provide a unique  identifier for each autonomous system.
* 截至2021年3月6日，共分配了114685个AS号码；其中70926个号码，是在BGP中公布的
  * http://www.potaroo.net/tools/asn32/ - 目前AS号码的分配状况
  * http://bgp.potaroo.net/cidr/autnums.html - 完整的AS号码和名称列表，并有细节链接
  * http://www.cidr-report.org/as2.0/ - 无类域间路由（CIDR）报告

## AS网络拓扑图：AS-level Internet Topology Graph (IPv4)

:orange: AS 拓扑图。（我们在幻灯片上看到的图片是这个图表的可视化）

![](/static/2021-04-27-16-02-41.png)

* 这是 ASes 之间的互连集合。自治系统之间的相互连接，网络之间的相互连接，构成了互联网。And this is the set of interconnections  between the ASes. The set of interconnections  between the autonomous systems, between the networks,  that form the Internet.
* 通过将每个节点、每个网络、每个自治系统作为一个节点来处理，形成 AS 拓扑图  And the AS topology graph is formed  by treating each node, each network,  each autonomous system as a node in  the graph
* 互连显示了不同网络之间的联系，它们显示了流量在这些独立运作的网络之间通过的不同方式 And the interconnections show the links between  the different networks, they show the different  ways in which traffic can pass between  these independently operated networks.

:orange: AS级拓扑结构--AS间的连接是什么？（AS拓扑图原理） AS-level topology – what are the inter-AS connections?

- **每个节点都是一个自治系统 Each node is an autonomous system**
- **基于地理位置在圆周上的位置 Position around circle based on geographic location**
  - 圆周的位置是基于地理位置的，所以它是基于地理位置的 And the position around the circle is  done based on geography, so it's based  on geographic location.
- **与中心的距离基于与其他网络的链接数量→链接越多，越接近中心 Distance from centre based on number of links to other networks → more links, closer to the centre**
  - 从圆心到边缘的距离取决于自治系统网络与网络其余部分的连接数量。一个与其他网络的连接很少的网络将出现在边缘，而一个与其他网络的连接很多的网络将出现在图的中间 And the distance from the centre towards  the edge of the circle is based  on number of connections that network,  that autonomous system, has to the rest  of the network. A network that has very few connections  to other networks will appear at the  edge, whereas a network that has very  many connections to other networks will appear  in the middle of the graph.
  - 而且，正如我所说，它是按地理位置排列的，可能有点难以阅读。在大约八点的位置，逆时针方向转动，如果我们从八点的位置开始，我们就能看到夏威夷。接近底部大约七点钟的位置，你可以看到圣地亚哥和洛杉矶，然后穿过美国，转到纽约，大约四点钟的位置。这个缺口是大西洋，然后从三点钟的位置，到一点钟的位置，你可以看到我们正在欧洲工作，标签显示了各个欧洲城市。它一路穿过亚洲，远东，然后回到夏威夷。我们可以看到，正如你可能预期的那样，这些相互联系的丰富程度在地理上是不同的，这取决于人们生活在哪里，以及在某种程度上取决于这些国家的发达程度  And, as I say, it's arranged geographically,  and it’s perhaps a little hard to  read. At about the eight o'clock position,  and going around anticlockwise, if we start  at the eight o'clock position we see  Hawaii. And, towards the bottom at about the  seven o'clock, position you've got San Diego,  and Los Angeles, and working the way  through the US,  rounds to New York and so on, about the four o'clock position.  The gap is the Atlantic Ocean, and then from around the three o'clock  position, to about one o'clock, you see  we're working the way through Europe,  and the labels show the various European  cities. And it works its way around,  through Asia, and the Far East,  and back to Hawaii.  And we see, as you might expect,  the richness of the interconnections varies geographically,  based on where the people live,  and based on, to some extent,  how developed the countries are.
- **边显示自治系统之间的联系 Edges show links between autonomous systems**
  - 在边缘有很多网络，有一个重要的数字，一个较小但重要的数字，一个丰富的连接拓扑在核心。这就是你所期待的。那些非常大的互联网公司，Facebook，谷歌，苹果，还有像 Akamai 这样的内容分发网络，都处于中间地带，与每个人都互相连接。然后在边缘地带有很多网络，它们只是在特定地区提供互联网接入   There's a lot of networks at the  edges, and there's a significant number,  a smaller but significant number, a richly  connected topology in the core.  And that’s what you'd expect. That the  very large Internet companies, Facebook, and Google,  and Apple,  and the content distribution networks, like Akamai  and so on, are all in the middle, interconnecting to everyone. And then there's  lots of networks around the edges,  which just provide Internet access in particular  regions.
- **流量可以流动的潜在路线 A potential route traffic can flow**
  - 它展示了自治系统之间，网络之间的相互联系。因此，它提供了流量可以通过网络流动的潜在路径，  It’s showing  the interconnections between the autonomous systems,  between networks. So, it's giving potential routes  which traffic can flow through the network.  
- **不是所有可能的路线都在使用 Not all possible routes are in use**

### AS-level Internet Topology Graph (IPv4 and IPv6)

![](/static/2021-04-27-16-03-09.png)

你可以对 IPv6做同样的事情，正如我们在这张幻灯片上看到的，也许正如你所期望的，IPv6图表有点稀疏，也许更容易阅读，因为 IPv6网络更小。不过，它的发展方式也是一样的。如果你看一下 IPv4图表的历史数据，你会发现 IPv6图表跟 IPv4的轨迹是一样的，只是比 IPv4晚了几年而已。You can do the same thing for  IPv6, as we see on this slide,  and as you would expect, perhaps,  the IPv6 graph is somewhat sparser and  perhaps a bit easier to read,  because the IPv6 network is smaller.  Tt's developing in the same way,  though.  If you look at the historic data  for the IPv4 graph, the IPv6 graph  is following the same trajectory as the  IPv4 Internet did, it’s just a few  years behind.

### IPv4/6拓扑区别：Differences Between IPv4 & IPv6 AS-level Topologies

![](/static/2021-04-27-16-05-57.png)

与IPv4相比，IPv6的AS级拓扑图是稀疏的，但有大量的IPv6使用→IPv4在部署上有30年的领先优势；可以预期拓扑图会有更密集的互连 IPv6 AS-level topology graph is sparse compared to IPv4, but there is significant IPv6 usage → IPv4 has 30 years head start on deployment; the topology can be expected to be more densely interconnected

在这张幻灯片中，这是来自谷歌的数据。它绘制了使用 IPv6的谷歌连接的比例。我们看到谷歌大约三分之一的流量是使用 IPv6的，这与上一张幻灯片上的图表相吻合。**与 IPv4相比，IPv6网络发展得不够完善，它的拓扑结构更为简单，使用它的流量也更少**。但我想这正是你所期待的。**IPv4已经在部署上领先了30年。当然，IPv4之间的互联会更加密集，IPv4的流量会比 IPv6的流量大得多。但是 IPv6正在发展，它也在以同样的速度增长** And in this slide, this is data  from Google. It’s plotting the fraction of  connections going to Google that  use IPv6. We see that about a third of the traffic to Google is  using IPv6, and that matches-up with the  graphs on the previous slide. The IPv6 network is a lot less  well developed, it's a much sparser topology  compared with IPv4, and there's less traffic  using it.  But I think that's what you'd expect.  IPv4 has had 30 years head-start on  deployment. Of course it's going to be  much more densely interconnected, of course there's  going to be much more IPv4 traffic  than IPv6 traffic. But IPv6 is developing,  it’s growing at a similar rate.

## 边界路由:Routing at the Edge

那么，我们如何在这个图上进行流量路由？鉴于我们在前面的幻灯片中看到的大量互连，基本上是一个完全无法阅读的大量互连，有这么多的网络，这么多的互连，我们如何在网络中路由流量So how do we route traffic around  this graph? Given that mass of interconnections  we saw in the previous slides,  essentially a completely unreadable mass of interconnections,  with so many networks, so many interconnections,  how do we route traffic around the  network?

![](/static/2021-04-27-16-24-28.png)

:orange: **边缘的设备往往有简单的路由表**（在网络的边缘，这是非常直接的） Devices at the edge tend to have simple routing tables

- **本地网络中的设备** The devices on the local network
  - 本地网段，，全直连
  - 放到本地以太网上，它就会被传送
  - If it's got an IP address in the range  of current local network  put it out on to the local  Ethernet, and it will be delivered to  the machine directly.
- **其他所有流量都有一个默认路由** A default route for everything else
- 例如，格拉斯哥SoCS中的主机的路由表。e.g., routing table for hosts in Glasgow SoCS:
  - 例如，如果你看一下大学计算科学系网络中的机器，计算科学系的所有机器都有130.209.240.0/20范围内的IP地址
  - 他们都有IPv4地址，地址的前20位与130.209.240.0相匹配，最后12位是计算科学网络中的特定机器
    - 他们的路由表只是说，如果机器在计算科学网络上，把它放到本地以太网上，它就会被传送。如果它的IP地址在130.209.240.0/20范围内，把它放到本地以太网上，它将被直接传送到该机器
  - 然后它有一个所谓的**默认条目**，它说如果它有任何其他的IP地址，就把它发送到IP地址为`130.209.240.48`的机器
    - **130.209.240.48号机器是位于计算机科学部【边缘的路由器**】。它是连接计算机科学系和校园其他地方的路由器，并从校园其他地方的边缘路由器连到互联网 And machine 130.209.240.48  Is the router at the edge of  the Computer Science Department. It’s the router  which connects Computing Science to the rest  of campus, and from then on to  the rest of the Internet.

而边缘的路由通常是这样的。路由表规定 "这是本地网络"，并说为了向这个网络上的任何机器发送，只要把它放到以太网上，或放到WiFi上，它们都是直接连接 The routing table specifies “this  is the local network” and says in  order to send to any machines on  this network, just put it out onto  the Ethernet, or on to the WiFi,  and they're all directly connected.

* 而其他任何东西，都要送到那边去。而 "那边 "就是连接到网络其他部分的路由器  And anything  else, send it over there. And “over  there” is the router that connects to  the rest of the network.
* 如果你看一下你家里机器的路由表，你会看到类似的东西。而且，最有可能的是，你有一个私人网络，你在一个网络地址转换器后面，路由表会说网络是192.168.0.0/16，这是在你的本地WiFi上，
* 其他任何东西你都可以发送到，可能是机器192.168.0.1，这将是WiFi基站，反过来，它将被发送到互联网的其他地方 If you look at the routing tables  on machines in your home, you will  see something similar. And, most likely,  you have a private network, you're behind  a network address translator,  and the routing table will say the  network is 192.168.0.0/16, and that's on your  local WiFi, and anything else you send  to, probably, machine 192.168.0.1, which will be  the WiFi base station which will,  in turn, send it out to the  rest of the Internet.

## 核心路由：Routing in the Core of the Internet

### DFZ

接近网络的核心时，路由就变得更加复杂

![](/static/2021-04-27-16-42-08.png)

- **靠近边缘的网络通常可以使用默认路由来处理其大部分流量** Networks near the edge can often use a default route for much of their traffic
  - 在边缘有一些网络，它有几个客户。有几个客户网络的链接，红色箭头指向内部，它知道分配给这些客户的地址范围是什么 Where there’s some network at the edge,  which has a couple of its customers.  it has links to a couple of  customer networks, with the red arrows pointing  inwards,  and it knows what are the address ranges  assigned to those customers.
    - 如果它有通往这些地址范围的流量，它可以通过这些链接将其路由到这些客户  So it knows that if it's got  traffic to those address ranges, it can  route it down those links to those  customers.
  - **但是，在边缘，网络可以有一个默认路由** We saw at the edges, the networks  can just have a default route that  points up towards the core.
    - **在边缘地区，这种基于默认的方法效果很好**，因为网络中只有一小部分是已知的，其他的都是 "外面的" And, at the edges, this sort of  default based approach works quite well,  because there's only a small part of  the network which is known, and everything  else is “out there”. 
    - 然而，当你进入**核心**区域时，网络往往需要越来越多的信息 As you get into the core,  though, the networks tend to need more-and-more information.
- **核心网络需要完整的路由表** Core networks need full routing table
  - 默认自由区（DFZ The default free zone (DFZ)
    - 网络互连部分
    - 核心AS，没有默认路由就能发送目的数据包的AS集合
  - **有了足够的互连，它们必须存储关于广泛的网络拓扑结构的路由信息** With enough interconnections, they must store routing information about the broad network topology
    - 这个位于网络中间的大型自治系统核心，基本上要跟踪整个互联网的拓扑结构，整个AS图 this large core of autonomous systems  in the middle of the network,  has to keep track of essentially the  whole Internet topology, the whole AS graph
  - **许多可能的路径 - 需要信息来选择最合适的路径** Many possible paths – need information to choose the most appropriate
    - 因此，他们需要存储所有的路径，到网络中的所有自治系统，以弄清他们如何能够交付数据  So they need to store all the  paths, to all the autonomous systems in  the network, to figure out how they  can deliver data
    - 他们需要保持整个互联网拓扑结构的地图。由此，他们可以决定以何种方式发送数据包，将数据包发送到哪个网络，以使其得到传递 They need to keep a map of the entire Internet topology. And from  that, they can decide which way to  send the packets, which network to send  the packets to next, in order that  they get delivered.

---

![](/static/2021-04-27-16-59-37.png)

- **AS级拓扑结构的扁平化→越来越丰富的网络互连** AS-level topology flattening → increasingly rich interconnections
  - 随着时间的推移，拓扑结构，即AS图，正逐渐变得更加复杂
  - 它开始是相对简单的，就像你在这里的幻灯片的左边看到的那样
    - **在边缘有ISP，提供与特定区域的连接。 它们连接到区域性的ISP，提供更广泛的区域连接。有少数网络运营商提供长途国际连接** There were ISPs at the edges,  which provided connectivity to particular regions.  They connected to regional ISPs, which provided  wider-area connectivity.there were a small  number of network operators that provided long-distance  international connectivity.
  -  随着时间的推移，我们逐渐看到越来越多的链接被添加，例如右边红色显示的链接。我们在区域层面得到了更多的互连，在边缘得到了更密集的互连。 网络的连接越来越多
     -  网络的连接越来越多。互联网服务供应商、网络运营商、组成网络的公司，正在逐步建立他们之间越来越多的互连
     -  流量越来越少地流向核心，然后通过这些小的长途供应商，然后再流回去，而越来越多地从边缘流向某种区域性的中转层，或者从边缘直接流向目标网络，而不必通过这些长途中转供应商 And the traffic is less flowing up  towards the core, and then through this  small sets of long-distance providers, and then  back down, and is increasingly going from the edges  up to some sort of regional transit  layer, or from the edges directly to  the destination network, without having to go  via these long-distance transit providers.
     - 我们看到**大型互联网公司**，例如谷歌，或**内容分发网络**，Akamai，CloudFlare，Fastly，等等，**在区域一级连接，直接连接到边缘ISP，以改善其客户的连接**   And we're seeing more  interconnection by large Internet companies, Google for  example, or the content distribution networks,  Akamai, CloudFlare, Fastly, and the like,  connecting at the regional level, connecting to  the edge ISPs directly, in order to  improve connectivity for their customers.
- 互联网交换点（IXPs）正变得普遍 Internet Exchange Points (IXPs) are becoming commonplace
  - **我们看到越来越多的被称为互联网交换点的地方。 网络运营商可以聚集在一起并相互连接的地点**   And we're seeing increasing numbers of what  are known as Internet Exchange Points.  Locations where network operators can come together  and interconnect themselves.
  - 例子。伦敦互联网交换点 Example: London Internet Exchange
    - 850个不同的网络互连 >850 different networks interconnected
      - 在这个国家，一个突出的例子是伦敦互联网交换中心，那里有大约800-850个不同的网络，都聚集在一个特定的建筑里，把他们的网络连接在一起
    - 如图所示，你看到它只是一个普通的办公大楼。 如果你进入其中一个地方，你会发现它的核心是一个巨大的以太网交换机
      - 所有的网络都把他们的设备带进来，他们都插入到一个巨大的以太网，使他们能够交换流量
      - LINX，即伦敦互联网交易所，谈到它如何有每秒数兆字节的流量通过它。 而这种类型的规模是很常见的。在欧洲有几十个，可能是几百个这样的交易所，在世界各地还有更多这样的交易所。它们是这种互连倾向于发生的点
    - 4.3 Tbps的流量 >4.3 Tbps of traffic

## 路由政策：Routing Policy

:orange: 域间路由是在竞争者之间进行的（正如我们所说，互联网是一个网络的网络。自主系统是独立运作的，在许多情况下，它们是竞争对手） Interdomain routing is between competitors

- **每个AS都是独立运营的，可能会对客户产生竞争** Each AS is independently operated and may compete for customers
  - 例如，如果你考虑一下英国的网络边缘，你已经有了自主系统，如英国电信、维珍传媒、Talk Talk、O2和所有其他的系统，所有这些都在竞争业务
    - 这些自治系统必须合作，在它们之间传输数据，并将数据传输到互联网的其他部分，但从根本上说，它们是竞争对手
    - 他们都在竞争成为你的互联网供应商，他们在争夺业务，他们在互相争夺客户。 这在所有的层次结构中都是如此
    - **自治系统，组成互联网的网络，需要合作以使互联网工作，但从根本上说，他们并不信任对方。他们是竞争对手，他们在不同的地方运作，他们有不同的目标，不同的价值观** The autonomous  systems, the networks that comprise the Internet,  need to cooperate to make the Internet  work, but fundamentally they don't trust each  other. They’re competitors, they're operating in different  places, they have different goals, different values.
- **商业、政治和经济关系影响着路由选择** Business, political, and economic relationships influence routing

:orange: 路由必须考虑政策（这些政策考虑在很大程度上影响了互联网路由的工作方式） Routing must consider policy

- 互联网路由基于如何最有效的方式将数据送到一个特定的目的地，**但它也是基于政策的**。而政策限制在很大程度上决定了拓扑结构。它们决定了网络之间的互连，并决定了这些互连的使用 Internet routing is based on  what's the most efficient way to get  data to a particular destination, but it's  also based on policy. And policy restrictions very much determine the  topology. They determine the interconnections between the  networks, and they determine which of those  interconnections are used.
- **对谁可以决定网络拓扑结构的政策限制** Policy restrictions on who can determine the network topology
- **对流量在特定来源和目的地之间应遵循哪条路线的政策限制** 目的地 Policy restrictions on which route traffic should follow between a particular source and destination
  -  哪些流量可以通过这些链接流动？ 并非所有的流量都可以流经某个特定的链接，这取决于所做的政策选择
- **政策限制可能会优先考虑非最短路径的路由** Policy restrictions might prioritise non-shortest path routes
  -  而这些不同的政策选择可能会优先考虑流量，使其通过非最短路径的路线，而不一定是最佳路线
  - 在选择路由时，你是优先考虑成本、带宽，还是优先考虑延迟？ Do you prioritise cost, bandwidth, or latency when choosing a route?
    - 网络运营商可能会优先考虑最短路径，他们在选择路线时可能会优先考虑最低延迟的路径。 但他们也可能优先考虑最高带宽的路径。或者最便宜的路径。或者他们可能有限制，优先考虑避开某些网络的路径，或者避开世界的某些地方
  - 某些网络之间的流量可能被禁止通过其他网络 Traffic between certain networks may be prohibiting from passing through other networks
  - 某些地区之间的交通应该避开其他地区 Traffic between certain regions should avoid other regions
    - 他们可能出于政治原因或经济原因，试图避免流量通过某些地区，或通过某些网络运营商

## Border Gateway Protocol (BGP)

互联网路由使用边界网关协议（BGP）。 Internet routing uses the Border Gateway Protocol (BGP)

* 互联网中的路由使用一个被称为边界网关协议的系统进行操作。The routing in the Internet operates using  a system known as the Border Gateway  Protocol.
* 边界网关协议有两个部分，**BGP有两个部分。 外部BGP和内部BGP** There's two parts to the Border Gateway  Protocol, two parts to BGP. 

:orange: **外部BGP（eBGP）用于在AS之间交换路由信息** External BGP (eBGP) used to exchange routing information between ASes

- **一个AS使用eBGP会话与邻近的AS交换关于AS级拓扑结构的信息** 相邻的AS之间交换AS级拓扑信息 An eBGP session is used by an AS to exchange information about the AS-level topology with a neighbouring AS
  - 它被AS用来与邻居交换信息，告诉他们哪些路径是可用的
- **通过两个路由器之间的TCP连接运行，每个AS都有一个**。 Runs over a TCP connection between two routers, one in each AS
  - 外部BGP通过路由器之间的TCP连接运行，每个自治系统都有一个，所以它将自治系统相互连接起来 External BGP runs over TCP connections between routers,  one in each autonomous system, so it  interconnects the autonomous systems.  
- **交换网络的AS级拓扑知识，根据政策进行过滤** Exchanges knowledge of the AS-level topology of the network, filtered according to policy
  - 它允许这两个自治系统交换关于AS拓扑结构的知识，它们根据自己的策略对这些知识进行过滤 And it allows those two autonomous systems  to exchange knowledge of the AS topology,  which they’ve filtered according to their policies.
- **用于计算到每个目标AS的适当的域间路由** Used to compute appropriate interdomain routes to each destination AS
  - 外部BGP是两个自治系统相互对话的方式，以交换有关网络结构的信息。由此，他们可以计算出**域间路由**，可以计算出整个网络的可用路径 External BGP is the way two autonomous  system will talk to each other,  to exchange information about the structure of  the network. And from that they can compute  interdomain routes, they can compute the paths  that are available across the network.

:orange: **内部BGP（iBGP）将这些信息传播给AS内的路由器** Internal BGP (iBGP) propagates this information to routers within an AS

- **内部BGP是BGP的一部分，在一个【自治系统内】用于向其他【边缘路由器】分发信息，以及向该系统的【内部路由器】分发信息** Internal BGP is the part of BGP  that’s used within an autonomous system for distributing that information to the other edge  routers, and for distributing that information to  the internal routers in that system.
- **域内路由协议决定AS内的路由** An intra-domain routing protocol determines routes within the AS
  - 内部BGP允许一个自治系统在内部协调路由信息。它告诉组成网络的路由器，如何到达边缘，如何到达世界其他地方 Internal BGP allows an autonomous system to  coordinate routing information internally. It tells the  routers that comprise a network, how to  get to the edges, how to get  out to the rest of the world.
- **iBGP向内部路由器分发关于如何到达外部目的地的信息** iBGP distributes information to internal routers on how to reach external destinations
  - 而外部BGP则用于自治系统之间的对话，以协调他们对世界其他地区的看法  And external BGP is used for talking  between autonomous systems to coordinate their view  of what the rest of the world  looks like.

## EBGP路由表：Routing Information Exchanged in eBGP


:orange: **eBGP路由器公布IP地址范围（"前缀"）及其相关AS级路径的列表** eBGP routers advertise lists of IP address ranges (“prefixes”) and their associated AS-level paths

- **结合起来形成一个路由表** Combined to form a routing table
  - 在外部BGP层面，自治系统、自治系统边缘的路由器公布了**IP地址范围**，并公布了AS路径，以便到达这些IP地址范围（每个IP地址范围/前缀都有多种途径可以到达）。这些结合起来就形成了所谓的路由表 At the external BGP level,  the autonomous systems, the routers at the  edges of the autonomous systems, advertise out  IP address ranges, and advertise the AS  paths in order to get to those  IP address ranges. And these combine to form what's known  as a routing table.
  - 有一个**IP地址范围的列表**，也就是所谓的**前缀列表**，<font color="red">对于每个前缀，你有一个自治系统的列表，你需要通过这个AS列表才能到达这个IP前缀</font> you have a list of IP  address ranges, what’s known as a list  of prefixes, and for each prefix,  you have the list of autonomous systems  you need to get through to get  to that prefix
 
![](/static/2021-04-27-18-14-46.png)

* 12.10.231.0/24相匹配的IP地址，其中前24位与12.10.231.0相匹配
* Next hop - 7种不同方式，通过七个不同的下一跳路由器到达那里，每一个对应一个AS路径
* 第一行用黄色突出显示，我们可以通过下一跳`194.68.130.254`到达前缀`12.10.231.0/24`，
  * **如果我们发送一个以该前缀为目的地的数据包，到下一跳路由器**，它将进入自治系统号码5459，它将发送至5413，它将发送至5696，它将发送至7369。
  * **而7369，因为它在AS路径的末端，所以7369是拥有该IP前缀范围的AS**。  And 7369, because it’s at the end  of the AS path, is the one  that owns the prefix.
  * 而 "i "只是意味着这是由内部BGP从其他自治系统收集的 它是通过这个路由器从网络中的其他AS之一传递过来的  And “i” just means this was gathered  by internal BGP from some other autonomous  system。 It’s been passed through this router  from one of other ASes in the  network
* **红色标记的为最优路径** And that line highlighted in red is  the preferred path.

:orange: 什么是下一跳的IP地址？ 它们是**这个自治系统与其邻居的路由器的IP地址**   What are the next hop IP addresses?  They’re the IP addresses of the routers  this autonomous system peers with in its  neighbours. 

* 如红色标记的IP，连接到一个IP地址为202.231.1.8的路由器，该路由器属于AS的一个邻居中，它在自治系统2497中 The particular autonomous system I’ve taken this  routing table from, connects to a router  with IP address 202.231.1.8, and that router  is in one of its neighbours, it's in autonomous system 2497. 

## 利用路由表绘制AS拓扑：AS Topology Graph

![](/static/2021-04-27-18-32-24.png)

你可以提取这些信息，并绘制成图表。 我们在左边看到的图显示了从收集这个路由表的地方开始的网络视图，也就是用**绿色突出显示的自治系统**，显示了我们发现的与所有其他系统的互连 You can extract this information, and you  can plot, it and you can form  a graph.  And  the figure we see on the left  here, shows the view of the network  from the point where this routing table  was gathered, which is the autonomous system  highlighted in green, showing the interconnections we  found to all the others.

AS拓扑，都是在显示路径上每一对相邻的自治系统都连接在一起  And all this is doing, is showing  each pair of adjacent autonomous systems on  the path are connected together

* 因此，如果我们看第一行，我们看到我们可以通过自治系统5459、5413、5696和7369到达需要的前缀12.10.231.0
* 红色的箭头显示了首选路径，这些路径在路由表的分段中被突出显示 And the arrows in a red show  the preferred paths, which are highlighted  on the segment of the routing table

你可以看到我们已经开始建立AS图了。我们开始建立一个拓扑图的地图。而且，如果你对整个图都这样做，如果你把路由表中的所有条目都拿出来，你最终会得到一个像我前面展示的CAIDA图一样的图

## EBGP路由政策：Routing Policy in eBGP

:orange: 每个AS选择向其邻居公布哪些路由 Each AS chooses what routes to advertise to its neighbours

* 因此，我们看到，路由的工作原理是每个自治系统向其邻居公布一些IP地址前缀。So, we see that the routing works  by each autonomous system advertising some IP  address prefixes to its neighbours. 
  * BGP的工作原理是，每个AS告诉其邻居 "我可以到达这些IP前缀"，"如果你向我发送流量，我将把它送到这些前缀"  BGP works by each AS telling its  neighbours  “I can reach these IP prefixes”, “if you send traffic to me,  I will deliver it to these prefixes”.
* **每个AS选择这些前缀中的哪些**，这些路由中的哪些，来向其邻居公布, **但它不需要公布它所知道的一切。它也不需要把它收到的所有东西都公布出去**  And each AS chooses which of these  prefixes, which of these routes, to advertise  to its neighbours.  But it doesn't need to advertise everything  it knows. It doesn't need to advertise  out everything it receives.

![](/static/2021-04-27-18-40-22.png)

:orange: **不需要公布它收到的所有路线** No need to advertise everything it receives

- **通常会从广告中删除一些路由，这取决于所选择的路由策略**。
  - 事实上，对于BGP来说，BGP中的自治系统从其广告中删除一些路由是很常见的   Indeed it's common for BGP,  it's common for autonomous systems in BGP,  to drop some routes from their advertisement.
  - 而且，他们公布的地址范围和AS路径，实际上取决于不同自治系统之间的关系   And, what address ranges, what AS paths  they advertise, really depends on the relationship  between the different autonomous systems.
- **常见的路由策略方法：Gao-Rexford规则**。  And a common way this is done,  is using what’s known as the Gao-Rexford  rules
  - **这是一种对自治系统进行分类的方法，并对路由的工作方式进行分类**  And this is a way of  categorising autonomous systems, and categorising how the  routing should work.
    - 对于互联网中的任何自治系统，它将其他自治系统归类为该自治系统的客户、同行或供应商  And for any autonomous system in the Internet, it categorises the other  autonomous systems as either being  customers, peers, or providers of that AS.
    - **例如**， 考虑的网络是JANET，即连接英国大学的联合学术网络系统，那么客户就是各个大学。 对等人是与之交换流量的其他网络，在对等的基础上没有真正的挑战。客户是为互联网接入付费的人；同行是你同意免费与之共享流量的人
      - 就英国的学术研究网络JANET而言，同行可能是欧洲的其他学术研究网络，例如。 供应商是那些你为互联网接入付费的人，这个AS为互联网接入付费
  - Gao-Rexford规则规定了公布的路由，而不是流量转发的方式 - 控制平面和数据平面之间的区别 Gao-Rexford rules specify what routes are advertised, not how traffic is forwarded - difference between control plane and data plane
    - **如果你从你的一个客户那里得到一个路由，所以如果你的一个客户说 "我有这个IP地址范围"，"我拥有这些IP地址"，你将向所有人公布这个路由信息**
      - 你的一个客户，一个向你支付互联网接入费用的人，宣传他们拥有一个特定的IP地址范围，**你告诉你的其他客户，你告诉你的同行，你也告诉你的供应商**
      - 客户付钱给你是为了获得互联网接入，付钱给你是为了为他们提供流量，但也付钱给你是为了向他们提供流量
      - **因为客户付了钱，因此，如果他们拥有一个特定的IP地址范围，他们想接收以这些地址为目的地的流量，所以你把这个消息告诉互联网的其他部分**
    - **如果你从你的一个供应商，或从你的一个同行那里得到一条路由，你只告诉你的客户**
      - 这是一条你付钱使用的路线，而不是被付钱使用的路线，因此你只告诉客户，他们付钱给你想要使用它 This is a route you're paying to  use, rather than being paid to use,  and therefore you only tell the customers, who are  paying you to use it.
      - 你明确付钱后访问才能使用供应商路线，所以你告诉你的客户。但你不告诉你的同行，因为你正在为这个访问付费。你为什么要让他们使用它？（同行没付钱）
      - 对于从你的同行那里收到的路由，你可以告诉客户，因为对等人愿意让你在不花钱的情况下使用这个路由，那么客户也可以，但你没有告诉你的提供者，你没有告诉互联网的其他部分 And, for routes received from your peers,  you tell your customers, because the peer  is willing to let you use this  route at no cost to your customers,  but you don't tell your provider,  you didn't tell the rest of the  Internet about it.
  - **结果图是无谷DAG：流量向上流动到共同提供者，然后向下流动** Resulting graph is valley-free DAG: traffic flows up to common provider, then down
    - 规定了流量可以流动的潜在方式
    - 这并不是说 "流量会走这条路"，而是说如果流量想到达这个地址，有一条潜在的路线可以遵循。其结果就是所谓的无谷**有向无环图**，无谷**DAG** This isn't saying “the traffic will go  this way”, it's saying there is a  potential route that traffic could follow,  if it wanted to get to this  address. And the result is what’s known as  a valley-free  directed acyclic graph, a valley-free DAG.
      - 有向无环意味着有一个方向：它告诉你该走哪条路，以到达一个特定的IP地址范围   And directed and acyclic means that  there's a direction: it shows you which  way to go, to get to a  particular range of IP addresses.
      -  它是无环的，这意味着没有循环  It’s acyclic,  that means there are no loops. 
      -  无谷意味着它向上，然后沿着，然后向下 And valley-free means it goes up,  and then along, and then down.
  - **旨在优化利润，而不是路由效率 - 不公布昂贵的路由** Designed to optimise profit, not routing efficiency – don’t advertise expensive routes
    - 它的设计，从本质上讲，是为了优化利润。 And it's designed, essentially, to optimise for  profit.
    - **如果有人向你支付访问，你将公布他们的路线，这允许流量流向他们**   If someone is paying you for access,  you will advertise their routes, which allows  traffic to flow to them.
    - **如果你为一条路线付费，你只向向你付费的人宣传它**。它的设计是为了避免将你付费的东西宣传给那些没付费访问的人  If you're paying for a route,  you only advertise it to people who  are paying you.  It’s designed to avoid advertising things which  you pay for, to people who are  not paying you for access.

## 路由决策过程：BGP Routing Decision Process

![](/static/2021-04-27-19-21-34.png)

:orange: **每个AS与邻居交换路由信息** Each AS exchanges routing information with neighbours

- **所有的自治系统与他们的邻居交换路由信息。 交换IP前缀的路由表，以及如何到达它们。 你必须通过什么样的路径，什么样的自治系统，才能到达该前缀** All the autonomous systems exchange routing information  with their neighbours.  The exchange lists of IP prefixes,  and how they can be reached.  What path, what set of autonomous systems,  you have to go through to get  to that prefix
- **AS级拓扑信息，根据每个AS应用的策略进行过滤** AS-level topology information, filtered based on policies applied by each AS
  - **他们根据政策来过滤这些路由表**。也许他们应用了Gao-Rexford规则，也许他们应用了一些其他规则，**但他们不一定公布所有的前缀和所有的路径，他们知道所有的同行，所有的邻居** And they filter this based on the  policies. Maybe they apply the Gao-Rexford rules,  maybe they apply some other rules,  but they don't necessarily advertise all of  the prefixes, and all of the paths,  they know to all of their peers,  to all of their neighbours.
- **提供AS级网络拓扑结构的部分视图** Gives a partial view of AS-level network topology
  - 它知道它的邻居们愿意告诉它什么

:orange: **每个AS应用策略来选择使用什么路由（采用这种拓扑结构的视图，并应用一套规则来执行其政策，也许他们会过滤掉某些路由。 也许他们不告诉邻居某些路由的存在**）。Each AS applies policy to choose what routes to use:

- **为每个目标IP网段前缀选择安装在转发表中的路由**。 Choose what route to install in forwarding table for each destination prefix
- **BGP可能找不到路由，即使存在，因为可能被政策所禁止** BGP may not find a route, even if one exists, since may be prohibited by policy
- **路由通常不是最短的AS路径--符合政策的最短路径** Routes are often not the shortest AS path – shortest policy-compliant path

:orange: **每个AS应用策略来决定向邻居公布哪些路由** Each AS applies policy to determine what routes to advertise to neighbours

- **过滤掉邻居不应该使用的路由（例如，Gao-Rexford规则，以及其他原因** Filter out routes the neighbour should not use (e.g., Gao-Rexford rules, and other reasons)
  - 邻居AS愿意向那个方向输送流量，但它不希望流量流向那个方向，所以它从它的路由表中过滤掉那个前缀
- **降低某些其他路由的优先级** De-prioritise certain other routes
  -  也许它对某些其他的路由进行了优先排序，或者取消了优先排序 Maybe it prioritises, or de-prioritises, certain other  routes.
- **在存在业务协议的情况下对路由进行特殊处理的标记** Tag routes for special processing where business agreements exist
  - 如果有特殊的商业原因，它可能会标记特定的路线进行特殊处理 Maybe it tags particular routes for  special processing, if there's a particular business  reason to do so. 
- **将业务目标映射到BGP政策上的文件不多→许多操作秘密** Mapping business goals to BGP policies is poorly documented  → many operational secrets

---

![](/static/2021-04-27-19-32-56.png)

* 该表显示了人们使用的标准，有一个本地偏好，AS路径的长度，来源的类型 The table shows the criteria people use,  and there’s a local preference, the length  of the AS path,  the type of origin;
  * 这是你知道的东西，因为它是你直接连接的客户之一，或者是你从其他网络中了解到的东西 is this something  you know because it's one of your  directly connected customers, or is it something  you’ve learnt from one of the other  networks?
* 如果有几种方式可以到达一个目的地，就有一个多出口判别器。有一堆政策等等  There’s a multi-exit discriminator if there are  several ways of getting to a single  destination.  And so on. there’s a bunch of policies and so on.
  * 问题是，你知道一条路线的存在，并不意味着你会使用它
  * **而且，你不一定挑选最短的路线，你挑选的是过滤图后与你所有政策相匹配的最短路线**   And you don't necessarily  pick the shortest routes, you pick the  shortest route that matches all your policies  after filtering the graph.
    * 这意味着，数据通过网络的路线，不一定是通过网络的最短路线。 它是满足所有政策约束的最短路线 And, this means that the route that  data takes to get through the network,  may not necessarily be the shortest route  through the network.  It’s the shortest route that meets all  policy constraints.
    * **这意味着在有些情况下，即使有一条潜在的路线，数据也不能到达一个特定的目的地，因为自治系统没有一个允许它去那个方向的政策**  It means there may be cases where  Data can't get to a particular destination,  even if there is a potential route  there, because the autonomous systems don't have  a policy which allows it to go  in that direction.
    * 在有些情况下，网络**可以**把数据送到某个特定的目的地，**但不会**，因为世界上某些地方的一些或更多的ISP所做的政策选择，不允许来自世界上这些地方的流量到达该目的地。这是在**寻找符合政策的最短路径**  There are cases where the network could  deliver data to a particular destination,  but won’t, because the policy choices made  by some, or more, of the ISPs  in some parts of the world, won't allow traffic from those parts of  the world to reach that destination.  it's finding the shortest policy-compliant path.

---

如何交换信息是很简单的。 各个自治系统交换前缀列表，以及为了获得这些前缀，交换AS路径。 **如何对这些路径进行过滤和优先排序是它的难点所在** How the information is exchanged is straightforward.  The autonomous systems exchange lists of prefixes,  and the AS path in order to  get to those prefixes.  How those paths are filtered and prioritised  is where it gets difficult.

**BGP在自治系统之间交换路由** BGP exchanges routes between autonomous systems

- **在许多情况下，政策、经济和政治方面的考虑比最短路径更重要** Policy, economic, and political concerns outweigh shortest path in many cases
  - 在许多情况下，政策、经济和政治方面的考虑超过了最短路径的考虑。 路径被过滤，它们被优先考虑，它们被取消优先权，基于政策选择，基于特定AS的成本，以及基于政治决定，哪些AS、哪些地区、哪些国家更受欢迎  In many cases the policy, and economic,  and political concerns outweigh the shortest path.  The routes are filtered, and they’re prioritised,  and they’re de-prioritised, based on policy choices,  based on how much it costs a  particular AS, and based on  political decisions as to which ASes,  which regions, which countries, to prefer
- **路由是在竞争者之间进行的--信任度低，公共数据少** Routing is between competitors – there is little trust and little public data
  - 自治系统是竞争对手，他们并不真正信任对方 And the autonomous systems are competitors,  they don't really trust each other. 
  - 因此，很难说BGP到底是如何工作的，因为AS不会告诉他们自己组织以外的人 And, as a result, it's hard to  say how BGP really works, because the  ASes won't tell anyone outside their own  organisation
  - 我们知道什么信息，我们可以在网络的某个点上放一个监视器，看看有什么信息到达网络的那个点，我们可以看到其他AS愿意在网络的那个点上向监视器发布什么信息。我们可以让一个友好的AS向我们展示他们所收到的BGP数据  We know what information, we can put  a monitor at some point in the  network and see what information is reaching  that point of network, we can see  what other ASes are willing to advertise  to a monitor at that point in  the network.We can get a friendly AS to  show us the BGP data they're receiving
  - 还有一些项目，比如RIPE RIS，或者俄勒冈大学的RouteViews项目，它们将这些数据归档，并储存起来，供人们使用 And there are projects, such as RIPE  RIS, or the RouteViews project from the  University of Oregon, which archive this data,  and store it, and make it available  for people.
- **BGP的决策过程是公开的，但输入数据却不是，因此很难评估路由决策是如何做出的** The BGP decision process is public – the input data is not, making it difficult to evaluate how routing decisions are made
  - 我们知道BGP的决策过程，我们知道路由器交换数据时遵循的算法 And we know the BGP decision process,  we know the algorithm the routers follow  to exchange the data
  - 它是决定性的，即他们如何选择一条特定的路线。 **但我们不知道的是进入该算法的数据** it's deterministic  about how they pick a particular route.  But what we don't know is the  data which is going into that algorithm. 
  -  我们知道正在公布的路由集，但它们在进入路由器的决策过程之前，会被过滤、优先化、去优先化和混合化。 而每个自治系统如何做到这一点，是该自治系统的商业秘密，他们不会告诉网络的其他部分  We know the set of routes that  are being advertised, but they are then  filtered, and prioritised, and de-prioritised, and munged,  before they go into the decision process  in the routers.  And how each autonomous system does this,  is a trade secret of that AS,  and they won't tell the rest of  the network.
     -  这使得我们很难评估路由决策在实践中是如何做出的
  -  我们可以看到最终的结果。 我们可以在网络的某个地方放一个监视器，看看它得到的路由表。在此基础上，我们可以推断出数据将如何到达一个特定的目的地。 **但是，我们不知道这些表是如何被过滤的，也不知道还有哪些路由被取消了优先级并被过滤掉，所以我们看不到它们**。 我们不知道那些我们不允许使用的潜在连接  We can see the end result.  We can put a monitor in the  network somewhere and see the routing tables  that it gets. And, based on that,  we can infer how the data will  get to a particular destination.  But how those tables got filtered,  and what other routes exist which are  being de-prioritised and filtered out so we  can't see them, that we don't know.  We don't know the potential connections which  we're not allowed to use.

# 路由安全：Routing Security

- Internet Routing Security 互联网路由安全
  - problem
- 保证路由安全的方法
  - RPKI
  - MANRS

## 网络路由不安全问题：Internet Routing is not Secure

:orange: 互联网中路由问题---**任何参与BGP路由的AS都可以公布任何地址前缀--无论他们是否拥有它** Any AS participating in BGP routing can announce any address prefix – whether or not they own it

- 互联网中的路由问题是能够在BGP中公布前缀、地址范围。而且，**要确保只有该地址范围的合法拥有者，只有特定前缀的合法拥有者，才能做到这一点，从而使流量到达正确的目的地** the issue with routing in the  Internet is being able to advertise prefixes,  address ranges, into BGP. And, to be sure that only the  legitimate owner of that address range,  only the legitimate owner of a particular  prefix, can do that, such that the  traffic goes to the correct destinations
  - 而BGP的问题，以及互联网路由安全的问题，是**它没有提供这种保证** And the problem with BGP, and the  problem with Internet routing security, is that  it doesn't provide this guarantee
  - BGP的问题是，任何参与BGP路由的自治系统都可以公布任何地址前缀。而且他们可以公布任何地址前缀，无论他们是否拥有该前缀
- **有时这是意外的** Sometimes this is accidental
  - 但是，如果一个自治系统选择宣布**别人拥有的地址空间**，那么没有什么可以阻止它这样做 But, if an autonomous system chooses to  announce address space owned by someone else,  then there’s nothing to stop it from  doing that.
- **有时是恶意的** Sometimes this is malicious
  - 有人恶意地试图重定向流量，例如，前往特定目的地的流量会进入一个假的网站，或通过一个可以窥探特定流量的网站的路径 it  can happen because of people maliciously trying  to redirect traffic, such that traffic to  a particular destination goes to a fake  site, or follows a  path through a site which can snoop  on particular traffic. 

:orange: **问题导致的结果是，流量被误导。BGP劫持攻击** Result is that traffic is misdirected: BGP hijacking

- 如果流量被误导，会有安全风险 Security risk if traffic can be misdirected
- **意外劫持对网络来说是一个严重的稳定性问题** Accidental hijacking is a serious stability problem for the network
  - **但它也可能由于恶意的活动而发生**  But it can also happen due to  malicious activities.
    - 还有恶意攻击，即网站被重定向到一个假的网站，或流量被重定向，使其通过一个特定的网络，攻击者可以窥探该流量。And there are also malicious  attacks, where  sites are redirected to a fake version  of a site, or traffic is redirected  so that it passes through a particular  network, where an attacker can snoop on  that traffic.
    - 而这是一个严重的问题。 **我们想解决这个问题，我们想确保只有前缀的合法拥有者才能公布到该前缀的路由** And this is a serious problem.  We'd like to solve this problem,  we'd like to make sure that only  the legitimate owner of a prefix can  advertise routes to that prefix.
  - 这种情况经常**意外发生**，这些意外的前缀劫持对网络的稳定性是一个严重的问题 And this happens frequently by accident,  and these accidental hijackings of prefixes are  a serious stability problems for the network.
    - 一个著名的例子是，巴基斯坦的一个互联网服务提供商设法向互联网公布了YouTube的IP地址范围
    - 发生的情况是，巴基斯坦的一个法院裁定该国的互联网服务供应商必须阻止对YouTube的访问，因为YouTube上的内容，一些内容被裁定为违反了当地法律
    - 巴基斯坦的互联网服务供应商被告知要阻止对这些内容的访问
      - 这家ISP试图做到这一点的方法是，在其网络中注入一条通往YouTube拥有和使用的IP地址范围的路由，在该国的所有客户都会看到这个路由广告，他们的流量会被重定向到一个页面，上面写着 "在这个国家访问该网站被阻止"
      - 如果他们成功地**将这一公告只发送到巴基斯坦**，这将会很好地发挥作用。这是一个完全合理的阻止访问某个特定网站的技术方法，就是你以这种方式注入路线
        - 这是一个完全合理的阻止访问某个特定网站的技术方法，就是你以这种方式注入路线
    - **问题是，他们错误地配置了他们的路由器，并且还向互联网的其他地方以及他们在国内的客户宣布了这一消息。 其结果是，网络中所有的YouTube流量都被重定向到巴基斯坦的这个网站，该网站称这些流量被封锁了**
      - 发布错误公告的特定互联网服务供应商被找到了，公告被过滤掉了，离该互联网服务供应商很近，所以问题没有持续很久。**但它确实表明，有可能意外地破坏全球的路由操作，而且是以一种非常令人惊讶和广泛的方式**
      - 这种类型的问题每天都在发生，也许是不太引人注目的方式

## 资源公共密钥基础架构：Resource Public Key Infrastructure (RPKI)

那么，目前解决这个问题的最佳方法是一种技术，被称为资源公钥基础设施，RPKI。 Well, the  current best approach to solving this is  a technique, known as the Resource Public  Key Infrastructure, RPKI.

:orange: **RPKI是对互联网路由安全的一种尝试** RPKI is an attempt to secure Internet routing

:orange: **让AS进行签名的路由原点授权 (ROA) - 一个数字签名，通过BGP宣布，以证明 该AS被授权公布一个IP地址前缀**  Lets an AS make a signed Route Origin Authorisation (ROA) – a digital signature, announced via BGP, proving the AS is authorised to announce a IP address prefix

- **在宣布一个自治系统拥有一个特定的IP地址范围，并可以将流量路由到该地址范围的同时，RPKI还允许自治系统发送数字签名，这在BGP中是正常的，你会得到我们在上一部分看到的通常的AS路径**  So, along with the announcement that  an autonomous system owns a particular IP  address range, and can route traffic to  that address range, which goes into BGP  as normal, and you get the usual  AS paths like we saw in the  previous part, RPKI allows the autonomous systems to send  a digital signature.
- 分层授权。RIR→供应商AS→客户AS Hierarchical delegation: RIR → provider AS → customer AS
  - 这也是通过BGP系统进行的，在BGP中遵循同样的路线，并在BGP中以与路由交换路线相同的方式进行过滤和处理  And this also progresses through the BGP  system, and follows the same route through  BGP, and gets filtered and processed in  BGP in the same way that the  route of advertisements do.
  - 但它也包括一个数字签名，说明ISP拥有这个特定的地址范围，并由路由系统的上一级签署  But it also includes a digital signature,  stating that the ISP owns this particular  address range, and signed by the next  level up in the hierarchy of the  routing system. 
  - 因此，在顶层，区域互联网注册处、RIPE和ARIN等等，将IP地址范围分配给ISP，提供一个签名声明，说明他们已经将特定的地址范围委托给一个特定的自治系统，一个特定的ISP So, at the top-level, the regional Internet  registries, RIPE, and ARIN, and so on,  which assign IP address ranges to ISPs,  provide a signed statement that they have  delegated a particular address range to a  particular autonomous system, a particular ISP.
  -  如果该ISP将该地址范围的一个子集委托给它的一个客户，它可以发布一个有签名的声明来这样做，而这又是有签名的  And if that ISP delegates a subset  of that address range to one of  its customers, it can make a signed  announcement to do so, and that is,  in turn, signed. 
  -  签名会一直延伸到根部  The signatures ripple up all the way  to the root
  -  因此，你会得到这种分层委托，用数字签名的声明宣布前缀的委托 So you get this hierarchical delegation,  with digitally signed statements announcing the delegation  of the prefixes.
- **允许路由器验证一个前缀公告是否被授权 - 被劫持的前缀将没有有效的签名** Allows a router to validate whether a prefix announcement is authorised – hijacked prefixes will not have a valid signature
  - 这允许收到前缀广告的路由器，并收到这些路由起源认证公告之一，以验证该前缀是否被授权 And this allows a router which receives  a prefix advertisement, and receives one of  these Route Origin Authentication announcements, to validate  whether that prefix is authorised.
  - 有效的前缀将有这些ROA之一，即提供的路由起源授权数字签名，而无效的前缀，即被劫持的前缀，将没有  And the idea is that valid prefixes  will have one of these ROAs,  the Route Origin Authorisation digital signatures provided,  and the invalid prefixes, the hijacked prefixes,  will not. 
- **成为BGP政策的一部分：倾向于有签名的前缀** Becomes part of BGP policy: prefer signed prefixes
  - **当应用BGP政策时，组成互联网的其他网络可以查看，他们可以优先选择有数字签名的前缀，而不是那些没有签名的前缀。 这使得劫持前缀变得更加困难** when applying BGP policy, the other  networks that comprise the Internet can look,  and they can prefer prefixes which are  digitally signed than those which are not.  And that makes it harder to hijack  a prefix.
- 截至2019年，约有12%的IPv4地址被具有有效数字签名的前缀所覆盖。有有效数字签名的前缀，但增长迅速 As of 2019, about ~12% of IPv4 addresses are covered by prefixes with valid digital signatures, but growing rapidly 
  - **RPKI正开始受到关注。 这是一个相对较新的标准，现在可能有10年的历史了**，我们在这里看到的幻灯片上链接的论文中的测量结果显示，截至几年前，大约10-12%的IPv4地址被一个有有效签名的前缀所覆盖，而且这个比例正在迅速增长
  - 与CloudFlare博客和isbgpsafeyet.com网站的链接显示了更多的最新统计数据，其继续增长，**RPKI开始被广泛使用。 并且开始有可能验证路由公告的真实性**  And it's starting to become possible to  validate the authenticity of the routing announcements.

## 可检测网络路由安全：MANRS（Mutually Agreed Norms for Routing Security）

另一种路由安全的方法是被称为MANRS的系统

* MANRS是一套相互同意的路由安全规范。它是一个由互联网协会赞助的项目，是一组网络运营商之间的合作，以改善路由安全  And MANRS is a set of mutually  agreed norms for routing security.  it's a project which is sponsored by  the Internet society, and is a collaboration between a set  of network operators to improve routing security.
* **它主要是为了分享最佳实践**。 它分享了如何有效使用RPKI的信息；它分享了配置选项；它分享了正确配置路由器、正确配置过滤、提供反欺骗措施的技巧和方法；以及在发现意外或恶意的路由劫持时协调应对  And it's mostly there to share best  practices.  It shares information in how to effectively  use RPKI; it shares configuration options;  it shares tips and approaches for correctly  configuring routers, for correctly configuring filtering,  for providing anti-spoofing measures; and for coordinating  responses to accidental or malicious  route hijacking when it's discovered. 
* **而且，它主要是作为一个谈话的场所，作为ISP协调的论坛，以确保路由系统的稳定，解决发生的问题，并分享和发展安全的最佳实践**    And it's mostly there's as a talking  shopping, as a forum for the ISPs  to coordinate, to make sure that the  routing system is stable, to address problems  as they occur, and to share and  to develop best practices for security.

MANRS）项目旨在改善路由安全（https://www.manrs.org）

- 过滤 Filtering
- 反欺骗 Anti-spoofing
- 协调 Coordination
- 通过RKPI进行全球验证 Global validation via RKPI
- 通过RKPI将签名的路由广告与路由过滤、反欺骗的最佳实践相结合，并在ISP之间进行协调，以防止错误配置。Combination of signed route advertisements via RKPI to catch malicious attacks, with best practises for route filtering, anti-spoofing, and coordination between ISPs to protect against misconfigurations

# 域内路由：Intra-domain Routing

Distance vector and link state algorithms

## 域内单播路由：Intra-domain Unicast Routing 

BGP提供到达其他网络的路径信息 BGP gives information on the path to reach other networks

* BGP和域间路由是关于提供到达其他网络的路径信息。它们是关于构成互联网的一组网络共同工作的方式，以交换在网络上路由数据包所需的信息 BGP and interdomain  routing are about giving information on the  path to reach other networks. They're on the way the set of  networks that comprise the Internet work together  to exchange information needed to route packets  across the network.
* **而BGP在很大程度上是一个以政策为重点的路由协议**。域间路由的挑战主要是与执行路由策略有关  And BGP is very much a policy-focused  routing protocol. The challenges in interdomain routing  are primarily to do with enforcing routing  policy.
  * 它们主要是为了让构成互联网的网络（从根本上说是竞争对手）充分合作，以便它们能够在网络上传输数据。这是关于表达商业约束、经济约束、政治约束和政策约束，这些都会影响数据的传输方式   They’re primarily to do with getting the  networks which comprise the Internet,  which are, fundamentally, competitors, to work together  enough that they can deliver data across  the network. It's about expressing the business  constraints, the economic constraints, the political constraints,  the policy constraints, that affect the way  data is delivered.

:orange: BGP和域间路由时，不同的网络，系统的不同部分，想要隐藏其内部细节。他们想隐藏关于他们网络内部的信息，不让他们的竞争对手知道   When we're talking about BGP, and interdomain  routing,  the different networks, the different parts of  the system, want to hide their internal  details. They want to hide the information  about what's going on inside their network,  from their competitors

* 如果我们考虑域内路由，我们是在一个单一组织所拥有和运营的网络内进行路由，而该组织的其他部分可以看到发生了什么 If we're considering intradomain routing, we’re routing  within a network owned and operated by  a single organisation, and the rest of  the organisation can see what's going on.
  * 他们可以看到网络的拓扑结构，他们可以理解网络运行的限制，因为该组织的所有部分为一个目标而合作  They can see the topology of the  network, they can understand the constraints it’s  operating under, because all of the parts  of the organisation working together for one  goal.
  * 因此，对于谁可以看到拓扑结构，或者哪些设备可以了解网络的约束，往往没有政策限制  So there tend not to be policy  restrictions on who can see the topology,  or which devices can understand the constraints  on the network.

如何在一个**AS内**路由流量？ How to route traffic within an AS?

- **单一信任域** Single trust domain
  - **何在一个自治系统内，在一个网络内路由流量，你会发现它是非常单一的信任域** If you look at routing, how to  route traffic within an autonomous system,  within a network, you find that  it's very much a single trust domain.
    - 整个**网络是由一个运营商运营的**，这就是域内路由的要点，它是在一个域内，在一个自治系统内，在一个网络内。**所以有一个单一的信任域**，对于谁能看到网络的信息，或者哪些链接可以使用，**没有真正的政策限制** The entire network is operated by a  single operator, and that's the point of  intradomain routing, it's within a domain,  it’s within an autonomous system, it's within  a network. So there's a single trust domain,  and there's no real policy restrictions on  who can see the information about the  network, or on which links can be  used.
  - **对谁能确定网络拓扑结构没有政策限制** No policy restrictions on who can determine network topology
  - **对哪些链接可以使用没有政策限制** No policy restrictions on which links can be used
    - 而且，对于哪些链接可以使用，往往没有政策限制。 当然，备份链接等是存在的，但没有必要隐藏这些链接；它们对整个系统是可见的  And there tend not to be policy  restrictions on which links can be used.  Certainly backup links, and so on,  exist, but there's no need to hide  those links; they’re visible to the entire  system.
- **一般来说，目标是获得高效路由→最短路径** Desire efficient routing → shortest path
  - 最好地利用你所拥有的网络 Make best use of the network you have available
  - 与域间路由不同，**域间路由的目标是找到符合政策的最短路径**，而**域内的目标**只是找到你所拥有的资源的**最有效利用**  Unlike inter domain routing, where the goal  is to find the shortest policy-compliant path,  the goal here is just to find the most efficient use of the resources  you have
- 两种方法 - 域内路由
  - **距离矢量--路由信息协议(RIP**) Distance vector – the Routing Information Protocol (RIP) 
  - **链接状态--开放最短路径优先路由（OSPF**） Link state – Open Shortest Path First routing (OSPF)

## 距离矢量路由：Distance Vector Routing

![](/static/2021-04-27-20-58-50.png)

**节点保持一个向量，包含与网络中每个其他节点的距离** Nodes maintain a vector containing the distance to every other node in network

- 网络中的节点，即组成网络的路由器，维护一个路由表，其中包含它们与每个其他节点的距离，以及通往该节点的下一跳  The idea here is that the nodes  in the network, the routers that comprise  the network, maintain a routing table which  contains  the distance they are from every other  node, and the next hop to get  towards that node.
- **定期与邻居交换信息**，而这些信息逐渐在网络中传播，最终每个节点都知道与其他节点的距离和下一跳。 Periodically exchanged with neighbours, eventually every node know distance and next hop to every other node
  - 在第一轮交流中，每个节点只是找出自己的邻居，然后找出其邻居的邻居，然后是其邻居的邻居，等等
- 在这个**例子**中，它们被标上了字母A、B、C、D等。 在一个真实的系统中，这些节点会有IP地址来识别它们
  - 节点A显示的路由表的例子，我们看到节点A包含了网络中所有其他节点的列表，目的地B、C、D、E、F和G，对于每一个节点，它都保持着距离，即它离那个节点有多远，以跳数计算
  - 因此，它离节点B只有一跳，它与B直接相连，它可以通过节点B到达它，它是直接相连的
  - 同样地，它离C有一跳，离D有两跳，它知道到达那里的下一跳是C，以此类推
- <font color="red">该协议以轮为单位运行。 它不断地与邻居交换这些信息，并逐渐填入网络地图，这样它就知道自己离网络中的每个节点有多远，以及到达那里的最佳途径是什么</font>  And the protocol operates in rounds.  It continually exchanges this information with the  neighbours, and gradually fills in the map  of the network so it knows how  far away it is from every node  in the network, and what's the best  way of getting there.

**数据包以最短路径转发到目的地** Packets forwarded on shortest path to destination

* 一旦它完成了这些工作，它就会根据跳数和距离，以最短的路径转发数据包到目的地
* 如果有两种到达目的地的方式，而且跳数相同，它可以任意选择
* <font color="deeppink">距离矢量路由是相对简单的，它在节点上不需要维护太多的信息。它所存储的只是一个其他节点的列表，以及距离和下一跳，所以它所需要的状态量与网络的大小是线性的</font> Now, distance vector routing is  relatively straightforward, and it doesn't maintain too  much information at the nodes. All it  stores is a list of the other  nodes, and the distance, and next hop,  so the amount of state it needs  is linear with the size of the  network.
  * 路由表中的条目数量与网络中的节点数量呈线性增长，所以它的资源效率比较高  The amount of entries in the routing  table grows linearly with the number of  nodes in the network. so it's relatively  resource efficient.

**在实践中没有广泛使用**。 Not widely used in practice:

- **在正常运行中收敛速度慢** Slow to converge in normal operation
  - 但是它收敛的速度很慢，因为它的操作方式是轮流进行的，而且它有一个问题，即某些类型的故障会导致一种行为，即在算法的每一次迭代中，距离会逐渐增加一个，路由协议的每一次迭代   But it's slow to converge, because of  the way it operates in rounds,  and  it has a problem where certain types  of failures can lead  to a behaviour where the distance gradually  counts up by one each iteration of  the algorithm, each iteration of the routing  protocol.
- **在含有环路的网络中，计数到无穷大的问题使其在故障时变得更糟** Count-to-infinity problem makes this worse on failure in networks containing loops
  - 当故障发生时，它逐渐增加一个，直到它在系统中被表示为无穷大，并需要多轮收敛和检测故障   And when a failure has happened,  it gradually counts up by one until  it gets to the representation of infinity  in the system, and takes multiple rounds  to converge and detect the failure.
  - 而这种行为会导致收敛速度非常慢，而且系统无法有效地从链路故障中恢复。  And that behaviour can lead to very  slow convergence, and the system not being  able to recover from a link failure  effectively.

## 链路状态路由：Link State Routing

在网络中被广泛使用的另一种算法是所谓的链接状态路由 The alternative algorithm, which is widely used  in the network, is what's known as  link state routing

* 链接状态路由的理念是，网络中的节点显然知道与邻居的链接。And the idea of link state routing  is that the nodes in the network  know, obviously, the links to their neighbours.  They know which other routers they directly  connected to.
* **他们知道他们直接连接到哪些其他路由器。 他们知道一些关于使用这些链接的成本的指标**  And they know some metric about the  cost of using those links.
  * 这可能只是链接带宽，作为一个**指标**，也可能是延迟，也可能是运营商选择的一个硬编码的指标。。And that may just be the link bandwidth, as a metric, or it may  be the delay, or it may be  a hard-coded metric chosen by the operator.
* **当一个节点启动时，或当一条链路发生变化时，当网络中的某些东西发生变化时，节点可以在整个网络中泛滥这些信息**  And when a node starts up,  or when a link changes, when something  changes in the network, the nodes can  flood this information throughout the network.
  * 他们可以向他们所有的邻居发送直接连接的节点列表，以及使用该**链接的成本，还有这些信息的序列号**。 They can send to all of their neighbours the list of directly connected nodes,  and the cost for using that link,  along with a sequence number for these  messages.
  * 这些信息在整个网络中泛滥，所以网络中的每个节点都能了解网络中的其他节点，以及每个节点的邻居是什么。 And this gets flooded throughout the whole  network, so every node in the network  learns every other node in the network,  and what are each node’s neighbours.

因此，节点A，在这个例子中，将通过网络涌出它是节点A，它的邻居是B、C、E和F，它将涌出指标，例如链接的速度。So node A, in this example,  will flood out through the network that  it's node A, its neighbours are B,  C, E, and F, and it will  flood out the metrics, the speed of  the links for example.

* 这将无处不在。 这将充斥整个网络，所以节点B将知道什么是节点A，什么是它的邻居，所以它将节点C、D、E、F和G，这些节点中的每一个都知道节点A的存在，以及它直接连接到哪些节点。And this will  go everywhere.  This will get flooded throughout this entire  network, so node B will know what  is node A and what are its neighbours, and so it will node C,  and D, and E, and F,  and G, and each-and-every one of those  nodes knows that node A exists,  and which nodes it's directly connected to.
* 这发生在每个节点上。 And this happens for every node.
  * 每一个节点都会定期将这些信息发布出去，只要有任何变化。Each node periodically floods this information out,  whenever anything changes.
  * 随着时间的推移，这意味着整个网络，网络中的所有节点，网络中的所有路由器，会了解网络中的所有其他链接。And, over time, this means that the  entire network, all of the nodes in  the network, all the routers in the  network, get to learn all of the  other links in the network.
  * 他们得到知道哪些节点是直接连接的。 They get to know which nodes are  directly connected.
* 这一点，他们可以直接画出一张完整的网络地图。That point they can just draw a  complete map of the network.
  * 每个节点都知道完整的网络拓扑结构，在这一点上，它可以运行Dijkstra算法，计算出通往网络中每个其他节点的最短路径，并使用它来决定转发数据包的方式 Every node  knows the complete network topology,  and at that point, it can run  Dijkstra’s algorithm, calculate the shortest path to  every other node in the network,  and use that to make the decisions  which way it forwards the packets.
* 现在，这样做效果更好，因为每个节点都知道完整的拓扑结构。Now, this works much better,  because every node knows the complete topology.
  * 如果发生故障，他们可以很快恢复，只要信息到达他们那里，他们就不必像距离矢量路由那样等待计数到无穷的周期。If something fails, they can recover quite  quickly, as soon as the message gets  to them, they don't have to wait  for the count-to-infinity cycle that the distance  vector routing has.
* 不过，它的缺点是需要更多的内存，而且需要更多的计算周期。The disadvantage of it, though, is that  it needs more memory, and it needs  more compute cycles.
  * 每个节点不仅要存储到每个其他节点的距离，而且还要存储完整的网络地图。Not only does each node store the  distance to every other node, but it  stores a complete map of the network.
  * 因此，每个路由器，网络中的每个节点，需要存储的状态量等于网络大小的平方。So the amount of state each router,  each node in the network, needs to  store is equal to the size of the network squared.
  * 因此，它的规模是网络大小的n倍，因为每个节点都在存储所有节点的完整矩阵以及它们与其他每个节点的连接。So it scales order n squared with  the size of the network, because each  node is storing the complete matrix of  all the nodes and their connections to  every other node.
  * 而计算Dijkstra算法在计算上要比仅仅看距离更复杂。And calculating Dijkstra’s algorithm is more computationally  complex than just looking at the distances.
* 因此，这种算法，即路由的链接状态方法，比距离向量更耗费内存，而且计算量更大。 但它收敛得更快。 在错误发生后，在链接失败后，它恢复得更快。And so this algorithm, the link state  approach to routing, is more memory hungry,  and it's more computationally intensive, than distance  vector.  But it converges much faster.  It recovers much faster after errors,  after links fail.

![](/static/2021-04-27-21-58-23.png)

节点知道与邻居的链接，以及使用这些链接的成本。这些链接的成本 - 链接状态信息 Nodes know the links to their neighbours, and cost of using those links – link state information

- 启动时在整个网络中传播这些信息，以及 每当一个链接被添加或删除时 Flood this information throughout the network on startup, and whenever a link is added or removed
  - 发送更新的节点地址、直接连接的邻居列表和使用链路的费用 邻居的列表和使用链路的费用，以及信息序列号 Send address of node sending the update, the list of directly connected neighbours and costs for link usage, and message sequence number
  - 序列号可以防止循环 Sequence numbers prevent loops
- 然后每个节点直接计算到其他每个节点的最短路径 节点的最短路径，作为路由表使用 Each node then directly calculates shortest path to every other node, uses as routing table

所有节点的链路状态数据的泛滥，确保所有节点 知道整个拓扑结构 Flooding link state data from all nodes ensures all nodes know the entire topology

- 每个节点使用Dijkstra的最短路径算法来计算 到每个其他节点的最佳路线 Each node uses Dijkstra’s shortest-path algorithm to calculate optimal route to every other node
- 按成本计算，最佳路径被认为是最短的路径 Optimal is assumed to be the shortest path, by cost

## Distance Vector vs. Link State

![](/static/2021-04-27-22-09-46.png)

距离向量路由。Distance vector routing:

- **实施简单** Simple to implement
  - 这种方法实现起来非常简单，路由器的资源开销很低，但存在收敛速度非常慢的问题  simple to  implement, has low resource overheads in routers,  but suffers from very slow convergence.
- 路由器只存储到每个其他节点的距离。O(n) Routers only store the distance to each other node: O(n)
- **存在收敛速度慢的问题** Suffers from slow convergence
  - 如果网络中的一条链路发生故障，需要很长的时间来恢复，数据包不能被传递，在这段时间内，到某些目的地的数据包将不能被正确传递  If a link in the network fails,  it takes a long time to recover,  and packets cannot be delivered, packets to  certain destinations will not be correctly delivered  during that time.

链接状态路由。Link State routing:

- **更加复杂** More complex
  - 或者你可以使用链路状态的路由方法，这更复杂，需要路由器有更多的内存，做更多的计算，但它的收敛速度要快得多 Or you can use the link state  approach to routing, which is more complex,  requires the routers to have more memory,  do more computations, but it's much faster  to converge.
  - 而且，在网络刚刚起步的时候，距离矢量路由是相对流行的，因为内存很贵，机器很慢，而且网络上没有特别严格的性能限制。 And, when the network was starting out,  distance vector routing was relatively popular because  memory was expensive, because machines was slow,  and because there were not particularly strict  performance bounds on the network.
  - 如今，内存很便宜，机器也很快，所以链接状态的方法通常是首选，因为它收敛得更快，因为网络从故障中恢复得更快。 These days, memory is cheap, machines are  fast, and so the link state approach  is generally preferred, because it converges faster,  because the network recovers from failures much  faster.
- 需要每个路由器存储完整的网络地图。O(n2) Requires each router to store complete network map: O(n2)
- **收敛速度快得多** Much faster convergence

**缓慢的收敛时间使距离向量路由不适合大型网络** Slow convergence times make distance vector routing unsuitable for large networks

- 如今，内存很便宜，机器也很快，所以链接状态的方法通常是首选，因为它收敛得更快，因为网络从故障中恢复得更快。 These days, memory is cheap, machines are  fast, and so the link state approach  is generally preferred, because it converges faster,  because the network recovers from failures much  faster.

## 域内路由的挑战：Challenges in Intra-domain Routing

那么域内路由的挑战是什么呢？有两个

:orange: **如何从链接故障中迅速恢复**？How to rapidly recover from link failures?

- **建筑工程出乎意料地善于破坏网络电缆。拖网渔船对海底电缆造成了类似的破坏** Construction work surprisingly good at breaking network cables; trawlers do similar damage to undersea cables
  - 虽然网络设备非常健壮，也非常可靠，但事实证明，建筑工人实际上非常擅长破坏网络电缆。而且这是令人惊讶的常见，有人挖掘的道路通过电缆把一个 JCB 和破坏网络。同样，对于长途网络的运营者，国际链路的运营者，事实证明拖网渔船非常擅长破坏海底电缆  While network equipment is pretty robust,  and pretty reliable,  it turns out that construction workers are  actually surprisingly good at breaking network cables.  And it's surprisingly common that someone digging  up the road puts a JCB through  the cables and breaks the network.  And, similarly, for people operating long distance  networks, people operating the international links,  it turns out that trawlers are pretty  good at damaging undersea cables.
- **好的网络设计有从源头到目的地的多条路径** Good network designs have multiple paths from source to destination
  - 因此，好的网络设计需要有从源到目的地的多条路径。他们需要能够在链接中断时，通过不同的路径进行故障转移，而且他们需要能够相对快速地做到这一点  And so good network designs need to  have multiple paths from source to destination.  And they need to be able to  fail-over to a different path if a  link breaks, and they need to be  able to do that relatively quickly.
- **必须迅速发现故障并切换到备用路径** Must quickly notice failure and switch to backup path
  - 他们需要多快注意到这一点？他们需要多快切换到备份路径？**这要看你给你的顾客提供了什么样的保证**。
    - 对于某些类型的网络，几分钟的停机时间是可以接受的。如果连接中断半个小时，那个接线员的客户也许没问题。不过，这似乎不太可能。几秒钟的失败？这越来越可以接受了。可能很明显，但是对于很多用户来说，如果链接中断10秒也是可以接受的 How quickly do they need to notice  this? How quickly do they need to  switch over to a backup path?  Well,  It depends, what sort of guarantees you've  given your customers.  For certain types of networks, it may  be that a few minutes downtime is  acceptable. Maybe the customers of that operator  are okay if the link goes away for half-an-hour. That seems less likely, though.  A few seconds failure? That's getting more  acceptable.  it's noticeable, probably, but it's probably acceptable  if the link goes down for 10  seconds, for a lot of users.
  - **如果链接是用于电话或视频流，这必须发生 在<1/60秒内发生，否则会中断语音或视频播放**。 If the link is used for telephony or video streaming, this must happen in <1/60th of a second else it interrupts the speech or video playout
    - 但是，如果这些链接被用来传输实时流量，如果你想让这些链接，以一种不会干扰流量的方式，恢复故障，也许你是在为 BBC 提供网络链接，也许你是在为一个传输高质量视频的服务提供网络链接，比如关键视频，如果你想恢复这些链接，使它不会影响那种媒体，你需要能够在**一帧的时间内恢复**  But if the links are being used  to carry real-time traffic,  and if you want to have the  links, have the failures, recovered in a  way that doesn't disrupt that traffic,  maybe you're providing the network link for  the BBC, maybe you're providing a network  link for a service which is carrying  production quality video,  critical video, for example,  and if you want to recover such  that it doesn't affect that sort of  media, you need to be able to  recover within the duration of a single  frame.
    - 因此，你需要能够切换到一个备份链接，也许在60分之一秒  So you need to be able to  switch-over to a backup link within maybe  a 60th of a second.
    - 有了这个链接，有了这个备份链接，就有了和原始链接相似的延迟，所以它不会在接收到的数据包中，造成明显的间隔 And have  that link, have that backup link,  have similar latency to the original so  it doesn't cause a  significant gap in the packets being received.
- **快速故障切换 - 预先计算替代路径以实现快速切换** Fast failover – pre-calculate alternative paths to allow rapid switchover
  - 因此，许多挑战是，对于您的客户来说，故障转移的速度有多快，以及在发生链接故障时需要多快进行故障转移？如果你是一家网络运营商，你的客户对你的恢复速度有什么要求？不同的服务水平保证，不同的服务水平协议，显然会影响你向客户收取多少费用。但它们也会影响你的组织方式，以及你如何设计网络，以及你设置了什么机制来检测故障。以及如何调整协议以处理故障，并从故障中恢复。And so, a lot of the challenge  is how quickly can you fail-over,  and how quickly do you need to  fail-over in the event of a link  failure, for your customers?  If you’re a network operator, what demands  are your customers placing on how quickly  it recovers?  And different service level guarantees,  different service level agreements, obviously affect how  much you charge your customers. But also  they affect how you organise, and how  you design, the network, and what mechanisms  you put in place for detecting failures.  And how you tune the protocol to  handle failures, and to recover from failures.
  - **通常，这涉及到预先计算可选路径的技术，因此系统有几个不同的预先配置的路由表，考虑到不同的链路故障，并且可以检测到故障并立即切换到预先计算的替代路径，而不必等待信息传播。** And quite often, this involves techniques  to pre-calculate alternative paths, so the system  has  several different routing tables pre-configured,  accounting for different link failures, and can  just detect the failure and switch over  instantly to a pre-computed alternative, and doesn't  have to wait for the Information to  propagate.

:orange: **如何在多条路径上平衡负载**？ How to balance load across multiple paths?

- **等成本多路径路由--如果两条路径的成本相同，则在它们之间交替使用流量** Equal cost multipath routing – if two paths with the same cost, alternate traffic between them
  - 如果你有多条路径通过你的网络，你试图分散流量的数量，你必须有效地利用这些路径，通过网络的不同路径，这样不是所有的流量都集中在一个单一的链接，但它是分散在整个网络，以避免拥挤一个特定的链接 If you have multiple paths  through your network,  and you're trying to spread the amount  of traffic you have to make effective  use of those paths, of those different  paths through the network,  such that not all of the traffic  is concentrated on a single link,  but it's being spread across the network  to avoid congesting a particular link
  - 然后，很多时候，这个想法就是所谓的等成本多路径。**你对网络进行排列，使得热链路上有多条并行路径，在可以看到大部分流量的链路上，你对网络进行排列，使得这些路径之间的流量交替**.  Then, quite often, the idea is what's  called equal-cost multipath. You arrange the network  so there's multiple parallel paths on  the hot links, on the links that  see most of the traffic, and you  arrange it so that it alternates the  traffic between those paths.
- **需要注意。TCP使用三重重复ACK作为丢失信号，因此对少量数据包的重新排序不敏感。，但将大量的重新排序视为丢失并减慢速度** Care needed: TCP uses a triple duplicate ACK to signal loss so is insensitive to small amount of packet re-ordering, but treats large amounts of reordering as loss and slows down
  - 因为像 TCP 这样的具有三重 ACK 的协议至少对重新排序有些敏感 But you need to be at least  somewhat careful,  because protocols like TCP, with the triple-duplicate  ACK, are at least slightly sensitive to reordering.
  - 如果您沿着可选路径向目的地发送数据包，而这些路径具有不同的延迟和不同数量的流量，那么数据包可能会无序到达。这是网络中重新排序的一个常见来源。 If you're sending packets down alternative routes  to a destination, and those routes have  different delays, and different amounts of traffic  on them, the packets can arrive out-of-order.
  - **TCP，和 TCP 恢复时所看到的，它对少量的重新排序不敏感，但是如果通过网络的不同路径，有显著不同的延迟，通过分散负载，通过在不同路径之间交换数据包，你可以引入大量的重新排序。然后 TCP 将其解释为数据包丢失，并开始重新传输数据包**。And, as we  saw when we spoke about TCP,  and TCP recovery, it’s insensitive to a  small amount of reordering,  but if the paths, the different routes  through the network, have significantly different latency,  by spreading the load, by alternating packets  between different paths, you can introduce large  amounts of reordering.  Which TCP would then interpret as a  packet loss, and start retransmitting packets.
- **RTP应用不关心顺序，只要数据包在截止日期前到达即可** RTP applications don’t care about order, as long as packets arrive before deadline
  - 不同的应用，不同的协议，对重排序有不同程度的敏感性。许多实时应用程序根本不在乎这些，只要数据包在截止日期之前到达  And different applications, different protocols, have different  degrees of sensitivity to reordering. A lot  of the real-time applications don't care at  all, as long as the packets arrive  before their deadline.
  - 但是像 TCP 和 QUIC 这样的协议，至少在某种程度上，确实很在意，所以你需要安排网络，这样如果你在多个路由之间平衡流量，它就不会意外地导致大量的重新排序。But protocols like TCP and QUIC,  to at least some extent, do care  so you need to arrange the network, so that if you  are balancing traffic between multiple routes it  doesn't accidentally cause large amounts of reordering.

