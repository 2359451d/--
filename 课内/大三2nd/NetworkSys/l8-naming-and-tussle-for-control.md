# Content

廉价机翻

* What is the DNS and DNS resolution
* DNS naming structure and organisation
  * DNS名称的结构和组织以及名称的分配方式
* Methods for DNS resolution
* The politics of names
  * 名称分配方式政策

---

* [Content](#content)
* [域名解析：DNS Name Resolution](#域名解析dns-name-resolution)
  * [DNS定义：What is the DNS?](#dns定义what-is-the-dns)
  * [DNS域名结构：Structure of DNS Names](#dns域名结构structure-of-dns-names)
  * [DNS域名解析概念 & DNS记录类型：DNS Name Resolution](#dns域名解析概念--dns记录类型dns-name-resolution)
    * [DNS解析器 & 客户端作用](#dns解析器--客户端作用)
    * [DNS解析过程-逐层向下（DNS查找过程）](#dns解析过程-逐层向下dns查找过程)
    * [响应TTL](#响应ttl)
* [DNS Names](#dns-names)
  * [DNS域名在层次中分配：Structure of DNS Names](#dns域名在层次中分配structure-of-dns-names)
  * [ICANN顶级域管理：From IANA to ICANN](#icann顶级域管理from-iana-to-icann)
  * [顶级域：Top-Level Domains](#顶级域top-level-domains)
    * [4种TLD类型](#4种tld类型)
    * [国家地区顶级域 - CCTLD](#国家地区顶级域---cctld)
    * [通用顶级域 - gTLD](#通用顶级域---gtld)
    * [基础设施顶级域](#基础设施顶级域)
    * [特殊用途TLD](#特殊用途tld)
  * [国际化域名&域名代码（Punycode）：Internationalised DNS](#国际化域名域名代码punycodeinternationalised-dns)
  * [13个根服务器作用：The DNS Root](#13个根服务器作用the-dns-root)
  * [谁控制根目录:Who operates routes servers](#谁控制根目录who-operates-routes-servers)
  * [事实：物理机器>13个](#事实物理机器13个)
* [Methods for DNS Resolution](#methods-for-dns-resolution)
  * [DNS不安全问题：DNS Security](#dns不安全问题dns-security)
  * [确保DNS安全的两种方法](#确保dns安全的两种方法)
    * [传输安全 transport security](#传输安全-transport-security)
    * [记录安全](#记录安全)
    * [需要运输和记录安全，以实现完全DNS安全](#需要运输和记录安全以实现完全dns安全)
  * [DNS Over UDP](#dns-over-udp)
    * [DNS通常工作方式](#dns通常工作方式)
    * [为什么基于UDP（对比TCP）](#为什么基于udp对比tcp)
    * [UDP-DNS报文格式](#udp-dns报文格式)
      * [查询问题区域：Question Section](#查询问题区域question-section)
      * [回答问题 & 权威名称服务器 & 附加信息区域](#回答问题--权威名称服务器--附加信息区域)
    * [UDP-DNS报文例子-dig](#udp-dns报文例子-dig)
  * [DNS Over TLS （DoT）](#dns-over-tls-dot)
  * [DNS over HTTPS (DoH)](#dns-over-https-doh)
    * [GET](#get)
    * [POST](#post)
  * [DNS over QUIC (DoQ)](#dns-over-quic-doq)
  * [Methods for DNS Resolution](#methods-for-dns-resolution-1)
* [The Politics of Name](#the-politics-of-name)
  * [如何选择 DNS 解析器（DHCP/手动）：Implications of Choice of DNS Resolver](#如何选择-dns-解析器dhcp手动implications-of-choice-of-dns-resolver)
  * [DNS解析 - 系统服务](#dns解析---系统服务)
  * [应用自选解析器优缺点](#应用自选解析器优缺点)
  * [网络封锁DNS解析](#网络封锁dns解析)
  * [国家地区 & 通用域名 - 知识产权：Intellectual Property and the DNS](#国家地区--通用域名---知识产权intellectual-property-and-the-dns)
  * [什么域名允许存在 & 决定权：What Domains Should Exist?](#什么域名允许存在--决定权what-domains-should-exist)
    * [通用域](#通用域)
    * [子域](#子域)
  * [根服务器控制问题：Who Controls the Root Servers?](#根服务器控制问题who-controls-the-root-servers)
  * [Should There Be a Single DNS Root?](#should-there-be-a-single-dns-root)

# 域名解析：DNS Name Resolution

## DNS定义：What is the DNS?

![](/static/2021-04-18-22-52-24.png)

如图，IP数据包结构，包含的是地址，而不是名字 So to start with, what is the  DNS?  Well, as we see in the packet  diagrams at the top of slide,  which have IPv4, on the left,  and IPv6 packets, we can see that  IP packets contain addresses rather than names.

* 【**IP 数据包包含地址而不是名字** IP packets contain addresses rather than names】当网络传送一个 IP 数据包时，**它不使用域名，而是使用一个 IP 地址**。
* **【设计用于路由器高效处理数据包转发位置** Designed for efficient processing by routers determining where to forward the packet】IP 地址的设计是为了通过路由硬件进行有效的处理。它们不是为人类可读而设计的。 When the network is delivering an IP  packet it doesn't use a domain name,  it uses an IP address. And the  IP addresses are designed for efficient processing  by routing hardware. They’re not designed to  be human readable.
* 【无法让人读懂——人们更喜欢名字，而不是地址 Not human readable – people prefer names, not addresses】
  * 现在，我们很幸运，我认为，IPv4地址至少是近似人类可读的，至少不比电话号码差，所以人们在某些情况下用它们作为人类可读的标识符。但是，随着我们越来越多地使用 IPv6，这是不可能的; IPv6地址一点也不容易记住 Now, we have been lucky enough,  I think, that IPv4 addresses are at  least approximately human readable, at least no  less so than a phone number,  for example, and so people have used  them  as human readable identifiers in some cases.  But, as we move more and more  towards IPv6, this is not really possible;  the IPv6 addresses are not at all  memorable.

:orange: <font color="deeppink">所以，作为用户，我们需要一种方法，为网络上的设备使用更有意义的名称，当我们连接到网络上的设备时，可以转换成网络内部使用的 IP 地址</font> So, as users, we need a way  of using more meaningful names for devices  on the network, when we're connecting to  devices on the network,  that can be translated into the IP  addresses which the network uses internally.

* 【**域名系统(DNS)是一个分布式数据库，将名称映射成 IP 地址** Domain name system (DNS) is a distributed database; maps names to IP addresses】域名系统 DNS 提供了这样的命名方案。域名系统是一个分布式数据库。它运行在互联网之上，并将人类可读的名字映射成 IP 地址   And the Domain Name System, the DNS,  provides such a  naming scheme.  The DNS is a distributed database.  It runs on top of the Internet,  and maps human readable names into IP  addresses.

---

:orange: 例子

![](/static/2021-04-18-22-55-06.png)

* 从一个 URL 开始，在这里是 https://cperkins.org/teaching/。在一开始，它包括用于访问该站点的协议 HTTPS。它有一个域名，还有一个文件部分，指定了要访问的特定文件，站点上的特定目录。start with a URL, in this  case https://cperkins.org/teaching/.  And that comprises,  at the start, the protocol used to  access the site, HTTPS. It’s got a  domain name, and it's got the file  part which specifies which particular file,  which particular directory on the site,  to access.
* 你可以从中提取出域名，在这个例子中是 www.csperkins.org ，这就是网站的名字。但是，当然，这只是名字，它不是可以用在数据包中的东西 And you can extract the domain name  from that, in this case www.csperkins.org  And that's the name of the site.  But, of course, that's just the name,  it's not something which can be used  in the packets.  
  * 因此，DNS 的作用是翻译该域名，并将其转换为一组 IP 地址，可用于到达服务器。所以你要把这个名字输入 DNS，然后输出一组 IP 地址。在这种情况下，对于这个特定的站点，会有一个 IPv4地址和一个 IPv6地址，正如你在幻灯片底部看到的。So the role of the DNS is  to translate that domain name, and turn  it into a set of IP addresses  which can be used to reach the  server.  So you'd feed that name into the  DNS, and out would pop a set  of IP addresses. And, in this case,  for this particular site, there’d be an  IPv4 address and an IPv6 address,  as you see at the bottom of  the slide.
* 对于人和应用程序，我们处理名称。人们不关心 IP 地址，他们关心的是名字，而应用程序应该关心的是名字 And for people and applications, we deal  with the names. People don't care about  the IP addresses, they care about the  names, and the application’s should care about  the names.
* **而网络路由和转发应该处理 IP 地址**。And the Internet routing and forwarding should  deal with the IP addresses.
* 在建立连接之前的最后一步，应该是解析地址的名称，然后可以用它来建立连接。应用程序中的其他所有内容都应该处理名称 And the  very last step before establishing a connection,  should be to resolve the name to  the addresses  which can then be used to establish  the connection. And everything else in the  application should work on the names.

## DNS域名结构：Structure of DNS Names

![](/static/2021-04-18-23-52-57.png)

我们看到 DNS 名称是层次结构化的。有一系列的**子域名**，一个**顶级域域名**，和 **DNS 根目录** We see that the DNS names are  structured hierarchically.  There’s a sequence of subdomains, a top-level  domain, and the DNS root.

* **子域名**，Sub-domains first
  * <font color="red">它描述了特定的站点，站点中的特定部分</font> describe  the particular site, the particular part within  the site.
  * **如**：`www` & `csperkins`,, `dcs.gla.ac.uk`的 前3个
  * <font color="deeppink">所有的子域名都在一个顶级域名内</font> The subdomains all live within a top-level  domain
* **顶级域名** Top-level Domains 【Concludes with a top-level domain (TLD)】顶级域可以是一个国家和地区顶级域名 The top-level domain can be either a  country code top-level domain
  * **国家**代码顶级域(cctld) Country-code top-level domains (ccTLDs)
    * such as “.uk”,  “.de”, “.cn” for China, “.io” for the  British Indian Ocean Territory, “.ly” for Libya,  and so on
  * **通用顶级域名** Generic top-level domains (gTLDs)
    * Or it can be  a generic top-level domain, such as “.com”,  “.org”, or “.net”
  * <font color="deeppink">顶级域名存在于 DNS 根中</font> Top-level domains live within the DNS root
    * **如**：此例的`org`为顶级域名
  * <font color="red">root是用于标识和交付顶级域名的服务器</font>  It’s the servers which identify  and deliver the top-level domains
    * 必须有人控制什么是可能的顶级域的集合 Someone has  to control what is the set of  possible top-level domains, and it's the DNS  root which defines this.

:orange: 【The root servers advertise the top-level domains】**还有一组称为根服务器的服务器，它们宣传顶级域名，并指定层次结构的顶级** And there’s a set of what are  known as root servers, which advertise the  top-level domains, and specify the top of  the hierarchy.

* <font color="red">DNS 根目录不能存在于 DNS 中</font> DNS  root can't live in the DNS.
  * 【**他们有已知的、固定的 IP 地址——新的 DNS 解析器需要在他们应答 DNS 查询之前，到达根服务器查询到tld顶级域名** They have well-known, fixed, IP addresses – new DNS resolvers need to reach them to find the TLDs before they can answer DNS queries】<font color="deeppink">这是开始进行 DNS 解析的地方，因此根服务器必须具有已知的、固定的 IP 地址，并且可以通过 IP 地址访问</font> This is the place where you start  doing DNS resolution, so the root servers  have to have well-known, fixed, IP addresses,  and be reachable by IP address,  because they're the thing you contact in-order  to start making use of the DNS.
* <font color="blue">新的 DNS 解析需要能够到达根服务器找到顶级域名，然后才能响应DNS查询。因此Root必须独立于DNS工作（不存在DNS中）</font>  So new DNS resolves need to be  able to reach them to find the  top-level domains, before they can answer DNS  queries. So the root has to work  independently of the DNS.

:orange: 【每一级都是独立管理和运作的 Each level is independently administered and operated】层次结构中的每个级别都是独立管理和独立操作的 Each of the levels in the hierarchy  is independently administered, and independently operated

* 根服务器运营商和 ICANN 操作根区域，。它们委托给顶级域，顶级域委托给子域，等等 The root server operators, and ICANN,  operate the root zone,. They delegate to  the top-level domains, the top-level domains delegate  down to the subdomains, and so on.

:orange: 【DNS 是一个分布式数据库的授权和实现 DNS is a distributed database – in authority and implementation】

* 每个层次都是独立管理和运作的 independently administered and independently operated.
* 它不仅在实现中分布，因为名称空间的不同部分都由不同的服务器控制和服务，而且还在权限中，**每个子域中的权限通过层次结构被下放** It’s distributed both in  implementation, in that the different parts of  the namespace are all controlled and served  by different servers, but also in authority,  with the authority what goes in each  subdomain being delegated down through the hierarchy.
* 【**层次结构中的每个级别都控制自己的数据** Each level in the hierarchy controls its own data】

## DNS域名解析概念 & DNS记录类型：DNS Name Resolution

![](/static/2021-04-19-00-17-54.png)

:orange: **DNS 用于域名解析（主要功能）** The DNS is used for name resolution

* 【**给定一个名称，查找与该名称相关的特定类型的记录** Given a name, lookup a particular type of record relating to that name】给定一个名称，DNS 的目标是查找特定类型的记录，并提供有关该名称的信息 Given a name,  the goal of the DNS is to  look up a particular type of record,  giving information about that name.

:orange: 【许多不同类型的记录: a，AAAA，CNAME，MX，NS，SRV，.. Many different types of record: A, AAAA, CNAME, MX, NS, SRV, …】

* <font color="red">记录是从名称到 IP 地址的映射</font> An A record is a mapping from  a name to an IP address.
* 【最常见的是 a 和 AAAA 记录，**它们将主机名映射到 IPv4和 IPv6地址**。，NS记录**识别域名服务器** Most common are A and AAAA records, that map hostnames to IPv4 and IPv6 addresses, and NS records that identify the name server for a domain】
  * **如**： www.csperkins.org”，对应这个 IPv4地址，或者这组 IPv4地址
    * AAAA 的记录也是如此，只不过是针对 IPv6地址
  * **NS 记录可以用来提供域名服务器的 IP 地址**  NS records, for example, can be used  to give you the IP address of  the name server for domain.
  * **CNAME 记录提供规范名称，它们在 DNS 中提供别名** CNAME records provide that canonical names,  they provide alias in the DNS
  * **MX 记录，邮件交换记录，让您查找特定域的电子邮件服务器**MX records, mail exchanger records, let you  look up the email server for a  particular domain
  * **这些被一般化为 SRV 记录，允许您查找域中的任何其他类型的服务器**【**一种资源记录的类型，它记录了哪台计算机提供了哪个服务，指明某域名下提供的服务**】 these got generalised into  SRV records which allow you to look-up  any other type of server within a  domain.

### DNS解析器 & 客户端作用

![](/static/2021-04-19-00-12-49.png)

:orange: 解析过程---DNS查找过程 【**DNS 客户端通过调用 `getaddrinfo ()`请求其解析器执行DNS查找** A DNS client asks its resolver to perform the lookup by calling getaddrinfo()】

* 解析过程，也就是查找名称的过程，**发生在 DNS 客户端请求 DNS 解析器执行查找时，这通常是由应用程序在调用 getaddrinfo ()系统调用时触发的**。The process of resolution, the process of  looking-up a name, happens when a DNS  client asks a DNS resolver to perform  the look-up  And this is usually triggered by an  application, when it calls the getaddrinfo() system  call
  * **如**：客户端在创建之后，在获得名称查找之后，首先要做的是调用 getaddrinfo () ，然后遍历结果，尝试依次与每个结果建立连接。And we saw this in the  examples of the labs, where the first  thing that the client does, after creating,  after getting the name to look-up,  is call getaddrinfo(), then loop through the  results, try to make connections to each  one in turn.

:orange: 【**解析器可以是一个在客户机上运行的进程，它通常在网络运营商提供的机器上运行** The resolver could be a process running on the client, it more commonly runs on a machine provided by the network operator】

* DNS 客户端只是一台**调用 getaddrinfo ()的机器**，它知道如何与解析器通话 A DNS client is just a machine  which runs the getaddrinfo() call, and knows  how to talk to a resolver.
* **解析是一个进程，一个应用程序，它可以查找名称。解析器可以是在本地计算机上运行的进程**  A resolve is  a process, an application, which can look  up names.  The resolver could be process running on  your local machine.
  * <font color="red">更常见的情况是，【DNS解析是一个在网络运营商提供的计算机上运行的进程】，您的客户机通过网络与解析器进行通信</font>  More commonly, it's a process that runs  on a machine provided by your Internet  service provider, by the network operator,  and your client talks over the network  to the resolver

:candy: 在配置计算机与网络通信时，指定该网络的 DNS 解析器的 IP 地址。And when you configure the machine to  talk to the network, you specify the  IP address of the DNS resolver for  that network.

* 如果您的机器正在使用动态主机配置，并且使用 DHCP 协议，**解析器 IP 地址就是它配置的细节之一** And if your machine is using dynamic  host configuration, with the DHCP protocol,  the resolver IP addresses one of the  details it gets configured with
  * 通常这是自动发生的
    * 您将计算机**连接到网络**，网络配置将**提供您的 Internet 服务提供商(您的网络运营商)正在运行的DNS服务器/解析器的 IP 地址**。You connect your  machine to the network, and the network  configuration provides the IP address of the  resolver your Internet service provider, your network  operator, is operating.

---

### DNS解析过程-逐层向下（DNS查找过程）

![](/static/2021-04-19-13-16-38.png)

slides步骤

* 如果解析器没有信息，它会通过 DNS **根服务器**进行递归查询 If the resolver has no information, it makes a recursive query via the DNS root servers
* 查询根服务器，找到TLD（NS记录）Query the root to find the TLD
* 查询 TLD 以找到子域名 Query the TLD to find the subdomain
* 必要时重复 Repeat as necessary
* 查询子域名以找到地址 （A记录/AAAA记录） Query the subdomain to find the address

---

当用户希望查找一个名字时，会发生什么呢 So when the client wishes to look  up a name, what happens?

* 在这种情况下，查找 a 记录， www.csperkins.org。客户端与解析器（某进程）通信，问 csperkins. org 的 a 记录是什么 Well, in this case we're looking-up the  A record for my website, www.csperkins.org.  And the client talks to the resolver,  and says what is the A record  for csperkins.org?  
  * 假设，这是解析器收到的 第一个查询，所以他没有关于网络其他部分的信息，**需要先找到 ‘org’的信息** And, if we assume that this is  the first query this revolver has ever  received, so it has no information about  the rest of the network,  what happens is it says, ‘I don't  know, first I need to find what  is “.org”
* **先找到顶级域名，然后向下查**，It needs to find the  top-level domain, and then worked down
  * 因此**解析器将与根服务器**通话，然后向 DNS 根服务器**发送一个查询，".org"的NS 记录（NS 记录可以用来提供域名服务器的 IP 地址）是什么** So the resolver would talk to the  root servers, and it would send a  query to the DNS root servers and  say what is the name server record,  the NS record, for “.org”?
  * <font color="deeppink">查找结果会从根服务器返回，告知本地解析器，哪个域名服务器（IP）有“.org”信息（即，代表了 .org的TLD）</font> And that answer would come back from  the root servers, and it will tell  the local resolver what is the IP  address of the name server which knows  about “.org”
* **解析器与该域名服务器（IP）通信** The resolver would then talk to that  name server
  * 向 “.org” **查询， “csperkins.org”的NS记录**（提供域名服务器的 IP 地址） It would send a query  to “.org” to say what's the name  server record for “csperkins.org”? 
    * 即，沿着层次从根服务器一直向下查询，1.TLD - “.org” 2.剩余子域名 “csperkins.org”. 3.与 "csperkins.org"通信，**查询“www.csperkins.org”的A记录**。 It’s working its way down the hierarchy.  We've gone from the root servers,  to “.org”, then it asks “.org” what's  the name server for “csperkins.org”.And then, once it gets that answer,  it contacts that server. It contacts the  server for “csperkins.org” and says what is  the A record, the address, for “www.csperkins.org”? 
* “csperkins.org”DNS服务器返回响应给本地解析器，**本地解析器已获得足够信息，将查询结果返回给客户端** And the server, the DNS server for  csperkins.org. responds. That gets to the local  resolver, and now it has the information  it needs, so it returns the answer  to the client.

:candy:每一层都有DNS服务器，，，如TLD，，子域名，，他们的DNS都为一个分布式数据库，他们的NS记录都为其DNS的IP

:candy: 我们看到这是一个相当反复的过程。And we see it's quite an iterative  process. 

* 解析器通过 DNS 根服务器获取顶级域服务器的NS录，The resolver talks to the DNS  root servers to get the  name server record for the top-level domain,  in this case “.org”.
  * “ org”
* 它与顶级域交流，以获得子域名的服务器记录。It talks to  the top-level domain, to get the name  server record for the sub domain.  
* 等等。如果有多个子域名，它将继续在这些域名中工作，直到查询结束，在这种情况下，它查询 a 记录If there are multiple  subdomains, it will keep working its way  down through those domains until it finds  the end of the query, in which  case it asks for the A record.

---

### 响应TTL

![](/static/2021-04-19-13-37-29.png)

:orange:【**响应包括一个存活时间，允许解析器在一定时间段内缓存该值** Responses include a time to live that allows a resolver to cache the value for a certain time period】<font color="red">从这些服务器返回的各种响应，无论是从根服务器返回，还是从顶级域服务器返回，还是从特定站点的子域服务器返回，都包括生存时间</font> The various responses coming back from these  servers, whether they're coming back from the  root servers, the top-level domain servers,  or the subdomain servers for a particular  site, all include a time-to-live.

* 因此，除了特定的记录和与该记录对应的 IP 地址之外，它们还有一个存活时间值，**该值表示解析程序可以缓存该记录的时间** So, as well as the particular record and  the IP address corresponding to that record,  they also have a time-to-live value which  says how long the resolver can cache  that record

:orange:【**在可能的情况下，后续查询将从缓存中得到答复** Subsequent queries are answered from the cache, where possible】 并且，在将来，如果您询问相同的查询，如果您再次对解析器进行相同的查询，**如果它有这些缓存值中的一个，如果它没有达到其存活时间的最大值，它可以只从缓存响应** And, in future, if you ask the  same query, if you make the same  query to the resolver again, provided it  has one of these cached values,  provided it's not reached its maximum time-to-live,  it can just respond from the cache.

* <font color="deeppink">这样就节省了所有的查找时间，并且使得响应速度更快</font> And that saves all the look-up times,  and makes the responses much quicker.
* 【如果缓存的条目超时，它将从层次结构中的下一级刷新 If the cached entry times out, it’s refreshed from the next level up in the hierarchy】<font color="deeppink">当条目超时时，它将被刷新，解析程序将请求层次结构中的下一个级别，以防有任何变更。最终，返回到根服务器</font>  When the entry times-out, it gets refreshed  and  the resolver asks the next level up  in the hierarchy in case it's changed.  And, eventually, it would work its way  back up to the root servers.

:candy: 【**根服务器的 IP 地址是众所周知的，永远不会超时** The IP addresses for the root servers are well known, and never time out】

* 如果您正在配置一个域，那么您认为TTL值应设为多大？这很大程度上取决于你在做什么 What value do you give to the  time-to-live if you're configuring a domain?  I think it very much depends on  what you're doing.
  * **如**自用网站，没有过大负载，TTL可以很大。一天，几天，或者一个星期，也许，因为它不会改变，它总是在同一个服务器上
  * **如**大型网站，可能有几百个服务器托管，TTL可能更短，（几秒），每次DNS查询，结果可能不同，指向不同服务器，因为它是不同服务器之间的负载平衡
  * **如**访问内容分布式网络，TTL也可能很短，所以可以根据负载&方位，指向/改变一个本地缓存，重定向查询 Similarly, if you're accessing a content distribution  network, it's likely that it will have  a short time to live,  so it can point you to one  of the local caches, and so it  can change which local cache, which local  proxy, it redirects your query to,  based on the load, and based on  as you move around

:candy: 【**子域可以设置一个简短的 TTL，每次请求特定名称时给出不同的答案→负载平衡; cdn 将查询指向本地缓存**  Subdomains can set a short TTL and give different answers each time a particular name is requested → load balancing; CDNs directing queries to local caches】

* 因此，可以修改TTL，影响DNS查找结果，行为

# DNS Names

* Who controls the DNS?
* TLD
* internationalised DNS
* DNS root

## DNS域名在层次中分配：Structure of DNS Names

![](/static/2021-04-19-14-26-28.png)

:orange: **DNS 域名在层次结构中分配** DNS names are  assigned in a hierarchy

* 【subdomains delegated from a top-level domain delegated from the DNS root】<font color="red">一个 DNS 域名包含一个子域名，这个子域名是从其他子域名委派的，这些子域名是从一个顶级域委派的，TLD是从根目录委派的</font>. A DNS name comprises a sub-domain,  which is delegated from potentially other sub-domains,  which are delegated from a top-level domain,  which is delegated from the root.

:orange: 例子

* `www.csperkins.org`
  * `www`,`csperkins` 是TLD中的子域名
  * `.org`是位于root中的TLD

---

:orange: 有哪些TLD？What top-level domains exist?

:orange: 对于每一个给定的顶级域名，顶级域在决定何时分配子域名时有什么政策？What policy does each top-level domain have for allocating
sub-domains?

:orange: What's a valid name within “.org”,  for example, what's a valid name within  “.com”?

:orange: 谁决定何时添加新的顶级域名？Who decides when to add new top-levels domains? 

:orange:网络中有效的顶级域名的集合是什么 what's  the set of valid top level domains  in the network

:orange: 谁控制根目录？它是做什么的？它是如何运作的？DNS root: who controls the  root? What what does it do? How  does it operate?

## ICANN顶级域管理：From IANA to ICANN

![](/static/2021-04-19-14-48-22.png)

> 随着互联网规模的不断扩大，若每一台主机都随意的给自己起名的话，那么彼此之间的通信会很麻烦。所以出现了所谓的名称地址管理机构IANA（互联网地址名称分配机构），但IANA有政府背景，后来DNS成为了一种基础性的互联网资源，因此不适合再被美国政府所维护了，后来将这种名称（地址）分配的功能转交给了民间组织ICANN（为了规范名称和地址的对应关系）。
> **ICANN管理最顶级的名称（域**），在现在有了很多的第二级各地驻地机构。IANA维护了一个数据库，记录互联网上所有的主机和IP地址的对应关系。

![](/static/2021-04-19-14-36-56.png)

**互联网的顶级域名集合由一个名为「互联网名称与数字地址分配机构」的组织所控制及界定,ICANN**。The set of top-level domains in the  Internet is controlled and defined by an  organisation known as the Internet Corporation for  Assigned Names and Numbers, ICANN.  

ICANN 有着漫长而复杂的历史。最初导致互联网发展的项目叫做 ARPANET。阿帕网是一个美国政府资助的研究项目，从20世纪60年代末一直持续到1990年左右。阿帕网项目开发了我们今天使用的互联网协议的最初版本。例如，它开发了 TCP/IP 的初始版本，以及一些早期的应用协议。作为阿帕网开发的一部分，研究人员发现，致力于开发这些协议的研究人员需要一套协议规范，他们需要写下他们正在开发的协议的描述，他们需要一个参数注册。ICANN has a long and somewhat complex  history.  The original project which led to the  development of the Internet, was something known  as ARPANET.  The ARPANET was a US government funded  research project, that ran from the late  1960s up until about 1990.  The ARPANET project develops the initial versions  of the Internet protocols, we use today.  It developed the initial versions of TCP/IP,  for example, and some of the early  application protocols.  As part of the development of the  ARPANET, it was found that the researchers  working to develop those protocols needed a  set of protocol specifications, they needed to  write down the descriptions of the protocols  they were developing, and they needed a  parameter registry.

他们需要一种存储地址的方法，以及协议具有的各种参数。参与这项工作的研究人员之一，乔恩 · 波斯特尔，自愿参与了这项工作。他自愿扮演 RFC 编辑器的角色，编辑和分发协议规范，并扮演一个后来被称为 IANA 的角色，即互联网号码分配局协议分配协议参数 They needed a way of storing the  addresses, and the various parameters that the  protocols had.  And one of the researchers involved in  that effort, Jon Postel, volunteered to do  this. He volunteered to act in the  role of what became the RFC Editor,  editing and distributing the protocol specifications,  and in a role which became known  as the IANA, the Internet Assigned Numbers  Authority, to assign protocol parameters

最初，当他开始这样做的时候，他是加州大学洛杉矶分校的一名研究生，后来，在他职业生涯的后期，他在南加州大学信息科学研究所工作。而且，在 ISI 工作期间，Postel 负责域名分配。随着 DNS 的出现，随着人们开始注册名字，在现有的协议参数注册中心注册这些名字似乎是很自然的事情。他这样做是兼职的，相当非正式的，活动，作为他正在进行的互联网，和互联网相关协议研究的一部分，主要由美国政府资助。IANA 的角色逐渐变得更加正式。And, initially, when he started doing this,  he was a graduate student working at  UCLA, and later he did this,  later in his career, while working at  the University of Southern California’s Information Sciences  Institute. And, while working at ISI, Postel handled  domain name allocation. As the DNS came  into being, as people started registering names,  it seemed natural to register these names  in the existing protocol parameter registry,  which Postel was operating as essentially the  IANA organisation.  And he did this as a part  time, fairly informal, activity, as part of  his ongoing research into the Internet,  and Internet-related protocols, primarily funded by the  US Government.  The IANA role gradually became more formalised.

到20世纪90年代后期，IANA 变得更加结构化，除了 Postel 之外还有其他人在研究它，因为互联网开始起飞。并且，在1998年，这导致了 ICANN 的成立，互联网名称与数字地址分配合作，作为一个专门管理域名的组织。ICANN 成立于1998年9月，是一家总部设在洛杉矶的美国非营利公司，实际上总部设在波斯特尔工作的同一栋楼里，也是 ISI 的总部所在地，洛杉矶的 Marina del Rey。不幸的是，1998年10月，也就是 ICANN 成立两三周后，波斯特尔去世了。到目前为止，他实际上是唯一一个以 RFC 标准发布讣告的人。在他去世后，ICANN 负责域名的管理。它很大程度上是作为一个全球多方利益相关者论坛运作的。它试图从尽可能多的人那里获得信息，试图尽可能多地从不同的角度看待网络应该如何运作，应该存在什么域名。在组织上，它是一家总部设在洛杉矶的非营利性公司，所以基本上是一家注册的美国慈善机构。而且，从2016年起，它不再与美国政府签订合同，正式地，**域名由 ICANN 管理**  By the late 1990s, IANA was becoming  more structured, there were people other than  Postel working on it, because the Internet  was starting to take off.  And, in 1998, this led to the  formation of ICANN, the Internet Cooperation for  Assigned Names and Numbers, as a dedicated  organisation to manage domain names.  ICANN was formed in September 1998,  as a US not-for-profit corporation based in  Los Angeles, actually based in the same  building where Postel worked, and where ISI  was based, in Marina del Rey in  Los Angeles.  And, unfortunately, Postel passed away in October  1998, just two or three weeks after  ICANN was formed. He's actually the only  person, so far, have an obituary published  as an RFC.  And after he passed away, ICANN look  over the management of the domain names.  And it's very-much been run as a  global multi-stakeholder forum. It’s trying to get  input from as many people as possible,  trying to take as many different views  on how the network should work,  what domain name should exist, as possible.  Organisationally, it's not-for-profit corporation based in Los  Angeles, so it's a registered US charity,  essentially.  And, as of 2016, it's now no longer under contract to the US Government,  and  officially, the domain names are managed by  ICANN.

---

![](/static/2021-04-19-14-45-52.png)

除了其复杂的历史，ICANN 还有一个相当复杂的管理模式。而且，在某种程度上，这源于，ICANN 的历史。最初的域名，网络的最初发展，正如我们所看到的，是由美国政府赞助的。互联网变得更加普遍，随着它变得更加无处不在的部署，随着它变得更加全球化，美国以外的人们开始对此感到不安，并推动 ICANN 脱离美国政府 ICANN, has a fairly, in addition to  its complex history, ICANN has a fairly  complex governance model.  And, in part, this comes from,  this springs out of, the history of  ICANN. The original domain names, the original  development of the network, as we saw,  was sponsored by the US Government.  And the Internet became much more  widespread, as it became more ubiquitously deployed,  as it became more global,  people outside the US started to be  uncomfortable with this, and were pushing for  ICANN turn to divest from the US  Government.

因此，它有一个治理模式，从世界各地的大量不同组织中获取信息，以确保不同利益相关者的需求是平衡的，而不是被任何一家公司所控制。And, as a result of that,  it has a governance model which takes  input from a large number of different  organisations around the world, to try and  make sure that the needs of the  different stakeholders are balanced, and it's not  controlled by any one company

所以 ICANN 是由一家董事会管理委员会控制的。他们从许多组织那里获取信息，包括一个支持某个组织的通用名称，这个通用名称代表通用顶级域名，如“。“ org”，“。“ com”等等。一个支持我们组织的国家代码名称，代表国家代码域，如“。“ uk”还是“。比如“ de”So ICANN is controlled by a Board  of Governors. They take input from a  number of organisations, including a generic names  supporting an organisation, which represents generic top  level domains such as “.org”, “.com”,  and so on.  A country code names supporting our organisation,  which represents the country code domains such  as “.uk” or “.de”, for example.

它从一个地址支持组织那里获取信息，该组织代表区域互联网注册机构，如 RIPE、 APNIC 和 ARIN，这些机构为 isp 和其他组织分配 IP 地址。它从政府咨询委员会获得投入，政府咨询委员会由来自联合国认可的112个国家的代表组成。它接受一个大型咨询委员会、一个根服务器操作员咨询委员会、一个稳定性和安全委员会以及一个技术联络小组的意见  It takes input from an address supporting  organisation, which represents the regional Internet registries,  such as RIPE, APNIC, and ARIN,  which assign IP addresses to ISPs and  other organisations.  It takes input from a Governmental Advisory  Committee, and the Governmental Advisory Committee is  formed of representatives from each of the,  I think, 112 UN recognised countries.  It takes input from an at-large advisory  committee, a root server operators advisory committee,  a stability and security committee, and a  technical liaison group.

除此之外，它还定期举行公开会议，一年三四次，环游世界，听取有关各方的意见。互联网名称与数字地址分配机构已经演变成一个庞大的组织。它的年度预算大约是一亿四千万美元。它接受来自广大人民的意见，包括来自联合国不同国家的代表，它是一个令人难以置信的政治组织。许许多多的国家和组织想要影响域名的管理方式，域名的分配方式，以及域名存在的类型 And, in addition to this, it holds  regular public meetings, three or four times  a year, circling around the globe,  to get input from interested parties.  ICANN has evolved into a massive organisation.  It's got an annual budget of somewhere  on the order of 140 million US  dollars.  It takes input from an enormous range  of people, including representatives from the different  countries in the United Nations, and it's  an incredibly political organisation.  Many, many countries and organisations want to  influence the way domain names are managed,  the way domain names are allocated,  and what sort of domain names exist

这不再是一个简单的、兼职的、由加利福尼亚大学的一位学者发起的项目，而是一个全球性的大型企业。尽管如此，这种方法似乎还是有效的。DNS 似乎是稳定的，虽然 ICANN 分配的一些名称当然是有争议的，但我认为，这个过程是广泛的工作 This is no longer a simple,  part-time, project by an academic at a  University in California, it's a global mega-corporation.  That said, it seems to work.  The DNS seems to be stable,  and while some of the names that  ICANN has allocated are certainly controversial,  the process is, I think, broadly working.

## 顶级域：Top-Level Domains

### 4种TLD类型

![](/static/2021-04-19-14-51-29.png)

:orange:**在互联网上有4种类型的顶级域** There are four types of top-level domain:

* **国家和地区顶级域名** Country code top-level domains (ccTLDs)
* **通用域** Generic top-level domains (gTLDs)
* **基础设施域** Infrastructure top-level domains
* **特殊用途域** Special-use top-level domains

### 国家地区顶级域 - CCTLD

![](/static/2021-04-19-16-06-55.png)

【**DNS 包括国家地区顶级域(cctld**) DNS includes Country Code Top-Level Domains (ccTLDs)】<font color="red">国家地区顶级域是那些标识分配给不同国家的名称空间部分的域</font> The country code top-level domains are those  which identify the portions of the namespace  assigned to different countries

* 实现方式：**ICANN 实质上已经将决定谁是一个国家的问题委托给了国际标准组织 ISO**。And the way this is done is  that  ICANN has essentially delegated the problem of  deciding who is a country to the  International Organisation for Standards, ISO
  * 【国际标准化组织标准3166-1定义了两个字母的国家名称 ISO standard 3166-1 defines two-letter country names】**ISO 有一个标准 ISO 3166-1，它定义了一组允许的国家名称缩写**。这些技术得到了广泛的应用  ISO has a Standard, ISO 3166-1,  which defines the set of allowable country  name abbreviations.  And these are reasonably widely used.
* **ISO 3166-1为联合国会员国、联合国特别机构，如国际货币基金组织、联合国教科文组织、世界卫生组织等，定义了国家代码缩写** And ISO 3166-1 defines country code abbreviations  for a Member States of the United  Nations, for the UN special agencies such  as the International Monetary Fund, UNESCO,  the World Health Organisation, and so on
  * **ICANN 已将存在哪些顶级国家地区域的决定权委托给 ISO，而 ISO 基本上已将其委托给联合国**。这巧妙地回避了什么是一个国家的争论。如果你是联合国的会员国，你就是一个国家，ISO 会给你一个国家代码，然后这个代码会反映到互联网上 ICANN has delegated  the decision of what top-level country code  domains exist to ISO, and ISO has,  essentially, delegated it to the United Nations.  And this neatly sidesteps the argument of  what is a country. In that if  you're a Member States of the UN,  you’re a country, and ISO assigns you  a country code, and that then gets  reflected into the Internet.

---

![](/static/2021-04-19-16-07-39.png)

:orange: **在 ISO 3166-1中定义的每个国家代码都被添加到 DNS 根区域** And every country code defined in ISO  3166-1 is added into the DNS root  zone.

* .uk, .fr, .de, .cn, .us, .io, .ly, …
* 【**每个国家对 CCTLD（国家地区顶级域） 的子域都有自己的政策**  Each country has it’s own policy for sub-domains of the CCTLD】<font color="red">然后，每个国家都可以为自己的国家地区域的子域所做的事情设置自己的策略。这可以委托给这些国家的政府</font>  And each country can then set its  own policy for what it does for  subdomains of that country code domain.  And that can be delegated to the  government of those countries.

:orange: 这个系统中有一些例外和一些奇怪的现象 .cs – JANET Name Resolution Systems and the “Czechoslovakia problem” (historical)

* 在20世纪80年代，90年代早期，90年代早期，英国运行一个非互联网的研究学术研究网络，一个叫做 JANET 的系统，联合学术网络。珍妮特运行了一套被称为彩色图书协议的协议，他们有一个替代的名字解析系统。**这个网络中的站点名称使用了一些看起来很像 DNS 名称的东西，但是是向后运行的**。
  * 所以他们把国家代码放在前面，然后一直往下移动到子域
  * 从根本上来说，用哪种顺序写名字并不重要，所以用相反的顺序写名字就可以了
  * 还有一个网关，可以在英国联合学术网络的机器和互联网其他机器之间翻译电子邮件信息。它只是通过重写地址，改变域名组件的顺序来做到这一点。这项工作还不错，直到 国家地区域 `.cs`被分配
    * 而且，在这一点上，网关变得混乱，因为突然变得很难分辨“ uk.ac.glasgow.cs”是在格拉斯哥大学的计算机科学部门，还是在捷克斯洛伐克的一个网站。您无法查看域名的第一个或最后一个部分，并查看它是否是有效的国家代码域之一，如果不是，则将其反转
  * 这个问题通过两种方式得到解决。
    * 域名重新命名，解决了这个问题。这就是为什么计算机科学是“ dcs.Glasgow.ac.uk”，而不是“ cs.Glasgow.ac.uk”。
    * 这个问题也得到了解决，因为 Czechoslovakia went away.

---

![](/static/2021-04-19-16-07-48.png)

:orange: Exceptions - 还有四个怪异之处，四个例外，其中顶级域名在互联网不匹配 ISO 3166-1种规定的 There are also four oddities, four exceptions,  where the top-level domains in the Internet  don't match what is in ISO 3166-1

* 【**.gb – The UK should use .gb to match ISO 3166-1 (.gov.uk used to be .hmg.gb, but .gb never widely used)**】The first is the United Kingdom.  The country code abbreviation for the United  Kingdom in ISO 3166-1 is GB.  **So if  it followed the prescribed form, we should  be using “.gb” rather than “.uk”**
  * 但是这种方法从未被广泛使用，最初在英国建立互联网的人，主要是伦敦大学学院的 Peter Kirstein 的错，他在英国建立了最初的互联网节点，决定他们更喜欢使用 uk
* 【**.su – The domain for the Soviet Union still exists and accepts new registrations**】
  * Su，仍然存在于互联网，仍然接受新的域名注册
* 【 **.eu – The EU is not an ISO 3166-1 country, but has a ccTLD**】
  * 有一个国家和地区顶级域名，但它没有在 ISO 3166-1中注册
* 【 .oz – Australia, sadly, changed from .oz to .au】

### 通用顶级域 - gTLD

![](/static/2021-04-19-16-22-23.png)

最初，**它包含了一组代表不同类型使用的核心域**   And originally this comprised the set of  core  domains that represented different types of use

* **.com, .org, .net** – unrestricted use
  * 最初用于公司、非营利组织和网络，但多年来现在可以不受限制地使用 originally for companies,  nonprofit organisations, and networks,  but for many, many years now available  for unrestricted use
* .**edu** – higher educational organisations (restricted use; primarily US-based)
  * 高等教育机构，主要是美国的
* **.mil** – US military
* **.gov – US government**
* **.int** – International Treaty Organisations (United National, Interpol, NATO, Red Cross, …)

![](/static/2021-04-19-16-22-31.png)

:orange: **ICANN已经大规模扩大了 gTLD 的注册数量** ICANN has since massively expanded the set of gTLD registrations

* **在很长一段时间里，这些域名是唯一存在的一组通用顶级域名，而且对于组织是否应该在这些通用顶级域名中的一个进行注册，或者是否应该在其国家地区域名下注册，存在着争议** And, for a long time, those were  the only set of generic top-level domains  that existed, and there was a debate  about whether organisations should register in one  of these generic top-level domains, or whether  they should register under their country code  domain.
* 最近，ICANN 大规模扩展了可能的通用顶级域名集合。现在已经有大约1500个通用顶级域名注册，而不是原来的7个   More recently, ICANN has massively expanded the  set of possible generic top-level domains.  Rather than the original 7, I think  there’s now about 1500 generic top-level domains  registered.
  * ~1,500 gTLDs registered

:orange: 通用顶级域名有很多不同用途 And these have a whole bunch of  different uses

* “.scot”是一个通用的顶级域名，世界上其他许多城市和地区也有这个域名。而且可以为品牌和其他组织获得通用的顶级域名，尽管这个过程是困难和昂贵的 “.scot” is a generic top  level domain, for example, and there’s others  for many other cities and regions around  the world. And it's possible to get  generic top-level domains for brands and other  organisations,  although this process is difficult and expensive.

### 基础设施顶级域

![](/static/2021-04-19-16-40-38.png)

国家地区顶级域名和通用顶级域名构成了互联网顶级域名的绝大多数。And the country code top-level domains,  and the generic top-level domains, comprise the  overwhelming majority of top-level domains in the  Internet.

【The infrastructure top-level domain, .arpa, is a historical relic】

* 【**用于从互联网前身 ARPANET 的过渡，它目前有一个用途: 反向 DNS**。 Used in the transition from the ARPANET – the precursor to the Internet，It has one current use: reverse DNS 】Arpa”，源于网络的原始发展，源于 ARPANET。它基本上是一个历史遗迹，用于从阿帕网到互联网的过渡。**这个顶级域服务器目前只有一个用途，那就是反向 DNS** Now, obviously, that name, “.arpa”, stems from  the original development of the network,  from the ARPANET.  And it's mostly a historical relic,  that was used in this transition from  the ARPANET to the Internet.  That top-level domain has one current use,  which is reverse DNS.

:orange: forward DNS lookup 正向DNS查找

* 取一个域名，在 DNS 中查找这个域名，它会给出相应的 IP 地址 Where you take  a domain name, and you look that  name up in the DNS, and it  gives you the corresponding IP addresses

:orange: **反向 DNS 查找是另一种方式的过程。这是从一个 IP 地址到一个域名的过程** Reverse DNS lookup is the process of  going the other way. It’s the process  of going from an IP address to  a domain name. 

* 数字IP反转&存储为 “.arpa”顶级域下的域名  And the way this is done,  is that the numeric  human-readable form of the IP address is reversed and stored as a domain  name under the “.arpa” top-level Domain
* **如**：`127.131.93.93.in-addr.arpa`注册了，如果在DNS查找这个域名，会返回一个DNS CNAME记录，指向 csperkins.org
  * CNAME 记录提供规范名称，它们在 DNS 中提供别名
  * 即，网站IP的反转，注册，允许查找，允许从IP映射到网站别名
* IPv6地址同理，IP的每4bit作为一个单独子域
  * `0.1.0.0.0....in6.arpa`是网站的反转IPv6 address。当他在DNS中被解析，会返回DNS CNAME记录，指向网站

:orange: **反向DNS查找 - 这是一种从 IPv4或 IPv6地址返回到原始域名的方法。这是目前唯一 ".arpa"的用途** It’s a way of  going back from either the IPv4 or  IPv6 addresses to the original domain name.  And that's the only current use of  “.arpa”.

### 特殊用途TLD

![](/static/2021-04-19-16-45-22.png)

存在六个特殊用途领域:Six special-use domains exist:

* .example – examples and documentation (example.com, example.org, etc., also exist)
  * example 是用来作为例子的，用于文档，已经注册了，而且还有通用顶级域版本，所以“ example. com”，“ example. org”等等，这些都是存在的，并且保证不会用于文档以外的任何用途 used for documentation,  is registered, and there's also  generic top-level domain versions of it,  so “example.com”, “example.org”, and so on which  exist and are guaranteed not to be  used for anything than for documentation
* .invalid – guaranteed never to exist
  * 保证它将永远不会被注册，作为一个测试域名，这将永远不会存在 .invalid” is guaranteed that it will never  be registered,  as a testing domain name which will  never exist
* .local – represents the local network
* .localhost – represents the local machine
* .onion – gateway to Tor hidden services (see https://datatracker.ietf.org/doc/rfc7686/)
  * 被用作 Tor 隐藏服务的网关,一种反审查技术。“.onion” is  used as a gateway for Tor hidden  services, and the RFC on the slide  talks about that in more detail;  this is The Onion Router, which is  a an anti-censorship technology.
* .test – for testing
  * 是用来测试网站，测试用途，用来测试确实存在的域名 “.test” is there for testing  sites, testing uses, for a domain name  which does exist

## 国际化域名&域名代码（Punycode）：Internationalised DNS

![](/static/2021-04-19-19-56-46.png)

:orange:【DNS名称应适用任何语言 DNS names should be available in any language】

* 【**最初的顶级域名和子域名使用 ASCII 码，但 UTF-8应该是允许的** Initial TLDs and sub-domains were in ASCII, but UTF-8 ought to be allowed】原始的 DNS，以及我们谈到的所有 DNS 名称，都使用 ASCII。最初的顶级域名集合，即子域名的最初集合，都注册在 ASCII 中 The original DNS,  and all the DNS names we've spoken  about, all use ASCII.  The initial set of top-level domains,  the initial set of subdomains, were all  registered in an ASCII.
  * 当然，如果您不会说英语，或者您所说的语言不能用 ASCII 字符集表示，那么这种情况就是有问题的
* 【**DNS 原则上应该与 UTF-8名称一起工作——实际上由于协议的僵化，它不应该这样做** DNS should, in principle, work with UTF-8 name – in practice it doesn’t, due to protocol ossification】**原则上**，DNS 协议中没有任何东西可以阻止您以 UTF8格式注册名称。DNS 只处理字节串，并不真正关心它们是什么。在实践中，很多处理 DNS 名称的软件都假定它们是 ASCII 码，**当人们尝试使用 UTF8名称，来允许非 ASCII 域名时，发现它在实践中并不起作用**  In principle, there's nothing in the DNS  protocol that should stop you being able  to register names in UTF8 format.  DNS just deals with strings of bytes,  and doesn't really care what they are.  In practice, a lot of the software  which deals with DNS names assumes they  are ASCII, and when people experimented with  using UTF8 names, to allow non-ASCII domain  names, it was found it didn't work  in practice.
  * 【**国际化的 DNS 通过将非 ASCII 名称翻译成 ASCII 来解决这个问题** Internationalised DNS works around this by translating non-ASCII names into ASCII:】使用了一种比较复杂的方法来**将非 ASCII 名称转换为 ASCII 名称，从而允许在 DNS 中使用它们**。它基于一个叫做 Punycode 的系统。
    * 【**将任何 unicode 文本编码为ASCII 字母、数字和连字符序列** Encodes any unicode text as a sequence of ASCII letters, digits, and hyphens】而 Punycode 是 Unicode (全局字符集)的一种编码，编码成一个由 ASCII 字母、数字和连字符组成的序列   As a result of this, we have  a somewhat complex approach to translating non-ASCII  names into ASCII, which allows them to  be used in DNS.  And it's based on a system known  as Punycode. And Punycode is an encoding  of Unicode, the global character set,  into a sequence of ASCII letters,  digits, and hyphens.
  * **如**：
    * München → Mnchen-3ya （translated into Punycode.）
    * Bahnhof München-Ost → Bahnhof Mnchen-Ost-u6b
    * 可以看到，**在 ASCII 中不能表示的字符在初始名称中被省略了**，然后在连接符后面有一个编码序列 And we  see that the characters which are not representable in ASCII  get omitted from the initial  name, and then there's a hyphen and  an encoded sequence at the end,  after the hyphen
    * 【**最终连字符后面的部分是一个 base-36(a-z0-9)编码的 Unicode字符序列表示，以及它们应该插入的位置** Part after final hyphen is a base-36 (a-z0-9) encoded representation of a sequence of Unicode character and the locations where they should be inserted】在结尾的编码序列是一个 base-36编码的 Unicode字符表示，它被省略了，省略的位置，以及它应该插入的位置 And that encoded sequence at the end  is a base-36 encoded representation of the  Unicode character, which was omitted, and the  location of where it was omitted from,  and so where it should be inserted.

:orange: 【**国际化的 DNS 名称使用 Punycode，前缀为 xn --** Internationalised DNS names use Punycode, prefixed with xn--】

* 例如：企鹅.com，用Punycode转换后为：xn--hoq754q. com
中国.cn，用Punycode转换后为：xn--fiqs8s.cn
* e.g., http://Яндекс.рф translates to http://xn--70akdum1a.xn--p1ai (Yandex, a Russian search engine)
* 国际化的 DNS 使用这个名称，**但是它在每个名称前面加上特殊的前缀“ xn ー”**，And the internationalised DNS uses this,  but it prefixes each of the names  with the special prefix “xn—“
  * 这个前缀在当时任何一个注册的、合法的顶级域名中都不存在，**这样解析器就可以区分国际化的名称和普通的名称，并且知道他们必须先执行翻译（将非 ASCII 名称翻译成 ASCII ）** which was  found not to exist in any of  the registered,  legitimate, top-level domains of the time,  to allow resolvers to distinguish internationalised names  from regular names, and know that they  have to perform the translation. 
* 这个方法是有效的
  * If you look-up the  example in Cyrillic at the bottom there,  it translates, the browser, for example,  will translate this into the string “`xn--70ak…`”,  which then **gets resolved as normal in  the DNS**, And this is Yandex（actual name, non ascii）,  which is one of the popular Russian  search engines
  * So the format the names have on  the wire, is this  unfortunately encoded form which translates them into  ASCII, but what gets displayed to the  users is the native form in Unicode.

## 13个根服务器作用：The DNS Root

![](/static/2021-04-19-20-09-53.png)

所以。互联网名称与数字地址分配机构(ICANN)决定一组合法的顶级域名。它们可以是国家代码域，也可以是通用顶级域，或特殊用途域，或者现在它们可以是国际化域名 So.  ICANN decides the set of legal top  level domains.  They can be country code domains,  or they can be generic top-level domains,  or special-use domains, or they can be  internationalised names these days

:orange: 【**ICANN 决定一组合法的顶级域名——根服务器然后商业化这些域名的TLD，广告** ICANN decides the set of legal top-level domains – the root servers then advertise the name servers for these domains】ICANN 然后告诉根服务器操作员这组名称，根服务器然后为这些顶级域名的名称服务器做广告  ICANN then tells the root server operators  that set of names, and the root  servers then advertise the name servers for  those top level domains.

:orange: **什么是根服务器**？What are the root servers?

* 【**一组13个服务器，为顶级域名的名称服务器做广告** The set of 13 servers that advertise the name servers for the top level domains】有13个服务器为顶级域名的域名服务器做广告。它们是在 DNS 注册的 Well, there’s a set of 13 servers  which advertise the name servers for the  top level domains.  They’re registered in the DNS.
  * **a.root-servers.net ~ m.root-servers.net** （13）
* 【**拥有众所周知的 IPv4及 IPv6地址** Also have well-known IPv4 and IPv6 addresses】
  * <font color="red">因为，根服务器的目的是宣传顶级域名，作为 DNS 层次结构的起点，所以他们需要不用 DNS 就可以访问</font>  the point of the root servers  is to advertise the top level domains,  to make the  starting point for the DNS hierarchy,  so they need to be reachable without  using the DNS

:orange: 【Why 13 servers?】这13个服务器为顶级域名做广告，它们是整个 DNS 的关键。为什么是13个 And these  13 servers advertise the top-level domains,  and they're the key to the whole  DNS.  Why 13 of them?

* 【**希望能够要求 DNS 解析器返回根服务器列表** Want to be able to ask a DNS resolver to return a list of the root servers】我们希望能够向 DNS 服务器询问可能的根服务器列表 Well, we want to be able to  ask a DNS  server for the list of possible root  servers.
* 【基于 UDP 的 DNS 有一个依赖→13根服务器的大小限制，这就是单个 UDP 数据包所能容纳的全部 DNS over UDP has a size limit on relies → 13 root servers is all that will fit into a single UDP packet】
  * 可能的根服务器列表必须容纳至DNS消息中 it has to fit in  a DNS message.
  * <font color="red">但是 DNS 长时间只运行在 UDP 上。而且 UDP 的回复也有大小限制。13是单个 UDP 数据包最大容纳的服务器数量，这就是为什么有13个根服务器的原因</font> but DNS for  a long time only ran over UDP.  And there's a size limit in replies  for UDP. And 13 is the maximum  number of servers that will fit in  a single UDP packet, that's why there  are 13 root servers.

## 谁控制根目录:Who operates routes servers

![](/static/2021-04-19-21-15-15.png)

谁操作这些路由服务器？Who operates these routes servers?

见slides, 这13个地址中的每一个都是通过字母识别的，并且它有一个已知 IPv4地址和IPv6地址 Well the  slide shows the current set.  Each of the 13 is identified by  letter, and it has a well-known IPv4  address and a well-known IPv6 address.  And on the right, we see the  operators of these servers.

* 由于历史原因，严重美国本土化 Heavily US-based, for historical reasons – discussed later
  * 这是由于历史原因。根服务器是在互联网完全由美国主导的时候建立的  And that's there for historical reasons.  The root servers were set up at  a time when the Internet was entirely  US dominated.

:orange: **根服务器的 IP 地址不能改变——它们太广为人知了——操作根服务器的人可能会改变** The IP addresses of root servers cannot be changed – they’re too widely known – who operates the root servers could change

* 无法更改这些根服务器的 IP 地址。它们被硬编码到，基本上，世界上每一个 DNS 解析器中，而且它们太广为人知以至于无法改变。谁来操作服务器是可以改变的，但是现在 IP 地址几乎是永远固定的了 The IP addresses of these root servers  cannot be changed. They are hard coded  into, essentially, every DNS resolver in the  world, and they’re far too widely known  to be changed.  Who operates the servers can change,  but the IP addresses are pretty much  fixed forever now. 

## 事实：物理机器>13个

![](/static/2021-04-19-21-16-28.png)

**实际上并没有13个服务器→选播路由**（Lecture 9）There are not really 13 servers → anycast routing (lecture 9)

* 现在有13个根服务器，但是没有13个物理机器。<font color="red">几乎所有的根服务器运营都使用一种称为选播路由的技术</font> Now there are 13  root servers, but there are not 13  physical machines. Almost all of the root server operators  use a technique, known as anycast routing

:orange: 选播路由

* 【同样的 IP 地址在网络中的多个地方发布——你能到达哪个副本取决于你所在的位置 Same IP address advertised from multiple places in the network – which replica you reach depends where you’re located】**你有多台机器，拥有相同的 IP 地址。它们从网络的几个不同的地方进入路由系统** you have multiple machines that  have the same IP address. And they  get advertised into the routing system from  several different places in the network
  * **然后，路由系统确保发送到该 IP 地址的流量到最近的拥有该地址的机器** And the routing system then ensures that  traffic sent to that IP address goes  to the closest machine that has that  address
* 【**根服务器使用的 IP 地址有13个，但物理服务器要多得多** There are 13 IP addresses used by the root servers, but many more physical servers】因此，有13个 IP 地址用于识别根服务器，**但实际上有超过13个物理服务器** So, as a result, there are 13  IP addresses used to identify root servers,  but there are actually many more than  13 physical servers.
  * **大多数根服务器实际上有几百台机器使用相同的地址**，在不同的数据中心，在世界各地的不同地点。  Most of the root servers actually have  several hundred machines using the same address,  in different data centres, and in different  locations around the world. 
  * 因此，它是一个非常负载均衡，非常严密保护的系统，即使它看起来只有13个 IP 地址，只有13台机器 So it's a very heavily load balanced,  very heavily protected, system, even though it  appears as only 13 IP addresses,  only 13 machines.

# Methods for DNS Resolution

* DNS security
  * historic security problems with the DNS
* DNS resolution over UDP, TLS, HTTPS, and QUIC

## DNS不安全问题：DNS Security

![](/static/2021-04-19-21-48-09.png)

【**DNS 历来完全不安全** DNS has historically been completely insecure】DNS 的问题在于，从历史上看，它一直是完全不安全的

* 【**通过未加密和未经认证的协议提出的请求和作出的答复,答复中不包括核实数据真实性的数字签名** Requests and responses delivered via unencrypted and unauthenticated protocol，Responses do not include a digital signature to verify authenticity of data】<font color="deeppink">原始的 DNS 协议使用 UDP 进行请求和交付响应。【它使用 UDP 的方式】没有任何形式的加密或身份验证</font>  The original DNS protocol made requests,  and delivered, responses using UDP. And it  used UDP in a way which did  not have any form of encryption or  authentication.
  * 【Trivial to eavesdrop on who is looking up what name】这意味着，在发出请求的主机和应答请求的解析器之间的路径上，**攻击者窃听正在查找的名称是非常简单的**。这些请求没有加密，所以路径上的任何人，任何能够读取网络流量的人，都可以看到哪些主机正在查看哪些名字   This meant it was trivial for attackers  on the path between the host making  the request, and the resolver which was  answering that request, to eavesdrop on what  names were being looked-up.  And the requests are not encrypted,  so anyone on the path, anyone who  can read the network traffic, can see  which hosts are looking at which names.
* 【**对于正在进行中的攻击者或恶意解决者来说，伪造答复是轻而易举的** Trivial for on-path attackers, or malicious resolvers, to forge replies 】
  * 由于消息和回复不以任何方式进行身份验证，这种在路径上的攻击者可以轻松伪造响应  In addition, because the messages and the  replies are not authenticated in any way,  such an on-path attacker can easily forge  a  response.
  * <font color="red">【如果它的响应速度快于预期的 DNS 解析器】，那么请求主机就不会知道这是伪造的，而是正确的响应。无法对响应进行身份验证。这使得通过伪造 DNS 响应以恶意方式重定向主机变得简单</font>  If it responds faster than the  intended DNS resolver, there's nothing for the  requesting host to know that this is  a forgery, rather than the correct response.  There’s no way to authenticate the responses.  And this makes it straightforward to  redirect hosts in malicious ways by forging  DNS responses.

## 确保DNS安全的两种方法

**确保 DNS 安全的两种方法** Two approaches to securing DNS:

* 运输安全 transport security
* 记录安全 record security

### 传输安全 transport security

![](/static/2021-04-19-21-59-25.png)

:orange: 运输安全 Transport Security

* <font color="deeppink">关于传输安全性的问题是我们【是否能够安全地传递 DNS 请求和接收响应】。使得通过某种安全通道发送 DNS 请求成为可能，并且通过同一通道获得答案和响应</font>  The issue about transport security is whether  we can make it possible to deliver  DNS requests, and receive replies, securely.  Make it possible to send DNS requests  over some sort of secure channel,  and get the answer, get the response,  back over that same channel
* 【**通过 TLS (或其他安全渠道)发出 DNS 请求并收到回复** Make DNS requests, and receive replies, over TLS (or some other secure channel)】使用一个协议，例如，TLS，来交付 DNS 请求并检索响应
  * 【**请求是加密的响应，因此不能被攻击者理解或修改** Requests are responses are encrypted, so can’t be understood or modified by attacker】<font color="red">而且，由于请求和响应是加密的，攻击者无法理解或修改它们。如果您相信解析器会给出正确的答案，那么这就提供了一种安全性</font>   And,  since that the requests and the responses  are encrypted, they can't be understood or  modified by attackers. And that provides a  form of security, provided you trust the  resolver to give you the right answer.
  * 【**如果你信任解析器，它可以防止攻击** If you trust the resolver, this protects against attack】这在提出请求的主机和解析器之间提供了一个可信的、安全的、经过加密和认证的通道，可以阻止任何人读取传输中的DNS报文，并阻止他们伪造回复  This provides a trusted, and secure,  and encrypted and authenticated channel between the  host making the request and the resolver  that stops anyone reading the DNS messages  in transit., and stops them forging replies.
* 因此，只要解析器正确地回答了查询，就能保护你免受 DNS 的攻击 So, as long as the resolver is  correctly answering the queries, this protects you  from the DNS.

### 记录安全

Record security – DNSSEC

【**向 DNS 响应添加数字签名，客户可以核实数据是否有效** Add a digital signature to DNS responses that client can verify to check the data is valid】<font color="green">向 【DNS 响应中添加某种形式的数字签名】，以便客户机可以验证其接收的数据是否有效</font> Add some form of digital signature to  the DNS responses, such that the client  can verify the data it’s receiving is  valid. 

* 【**ICANN 签署根区** ICANN signs the root zone】<font color="deeppink">ICANN 将数字签名附加到根区域，根区域指定顶级域的集合。</font>  ICANN attaches a digital signature to the  root zone, which specifies the set of  top-level domains.
* 【**根服务器提供的有关顶级域名的签名信息** Root servers sign information they provide about TLDs】根服务器运营签署他们提供的关于顶级域名的信息 The root server operators sign the information  they provide about the top-level domains.
* 【**顶级域名提供的子域名签名信息** TLDs sign information they provide about sub-domains】

:orange: 【**（原则上）允许客户端从根上验证签名，提供一个信任链来证明域的所有权-防止恶意解析** Allows a client to verify signatures back to the root, providing a chain of trust to demonstrate ownership of a domain – protects against malicious resolvers】

* **数字签名链**，可以一直追溯到 ICANN，以及每个被查询的名字的根源  And there’s a chain of digital signatures  that leads all the way back to  ICANN, and the root, for every name  that gets looked-up
* **当执行DNS查找，解析域名，得到结果时，还会得到一个数字签名，允许验证DNS查找结果是否被篡改** when you perform a  DNS lookup, when you resolve a name,  and you get a name back,  in addition to  the record which says this is the  name you looked-up, and this is the  corresponding IP address, you also get a  digital signature which allows you to verify  that it's not been tampered with.  
  * 至少在原则上，客户机可以验证签名，沿DNS层次向上到根，并提供一个信任链来证明域的所有权 And the clients, at least in principle,  can then verify the signatures, all the  way back up the hierarchy to the  root, and provide a chain of trust  that demonstrates ownership of the domain.

:orange: 【**广泛使用公开密码匙加密技术-细节很复杂** Makes extensive use of public key cryptographic techniques – details are complex】

* 至少顶级域和根区域都是有签名的，一些更受欢迎的网站开始这样做，并开始签署他们的请求，这样他们的数据和记录的完整性，就可以得到验证 And, at least the top-level domains,  and the root zone, are all signed.  And a few of the more popular  sites are starting to  do this, and starting to sign their  requests, so the integrity of their data,  of the records, can be verified.
* 【**已经实施，但尚未广泛使用** Implemented, but not widely used】<font color="red"></font>它已经开始使用了，但是还没有被广泛应用

### 需要运输和记录安全，以实现完全DNS安全

:orange: 【**需要运输和记录安全，以实现完全安全的 DNS** Need both transport and record security for fully secure DNS】理想情况下，需要传输安全和记录安全。理想情况下，我们希望既保护请求的安全，这样就没有人能看到我们正在发出哪些请求，也没有人能修改我们从解析器得到的响应，同时也使用记录安全来验证解析器没有对我们撒谎。目前，我们有能力提供传输安全性，我们开始看到正在部署记录安全性。Ideally, we want both transport security and  record security. Ideally, we want to both  secure the requests, so no one can  see which requests we are making,  and no one can modify the responses  we’re getting back from the resolvers,  and also use record security to verify  that the resolvers are not lying to  us.  

* 目前，我们有能力提供传输安全性，我们开始看到正在部署记录安全性 At present, we have the ability to  provide transport security, and we're starting to  see record security being deployed.

## DNS Over UDP

### DNS通常工作方式

:orange: DNS通常工作方式

* 客户端向解析器提出查询，解析器查询域名，响应 The usual way this works in the  DNS, is that the client makes a  query to the resolver, the resolver looks-up  the name, and replies.
* **查询请求**大小很小，只包含域名 query is small
* **响应**只是一个IP地址 the response is just an IP  address
* 因此，<font color="deeppink">这不需要太大的空间。它不需要很多UDP数据包。所以我们可以同时发出请求和得到响应，每个请求都在一个包中。如果数据由解析器缓存，则在单个往返时间内得到答案。这比在 TCP 上运行更有效率</font> That doesn't need much space. It doesn't  need lots of packets.  So we can make both the request,  and get the response, each in a  single packet. And get the answer in  a single round-trip time, if the data  is cached by the resolver.  And this is more efficient than running  it over TCP.
  * **DNS query over UDP** completes in **1 RTT** – one packet for request, one for response
  * **DNS over TCP** exists, but is slower; initial and final TCP handshakes take several RTT and send extra packets

---

### 为什么基于UDP（对比TCP）

:orange: DNS如何传输（以前，DNS over UDP）？how does the transport actually work?

![](/static/2021-04-19-22-55-06.png)

* 【**DNS 查询通常通过 UDP 端口53进行** DNS queries generally made over UDP port 53】历史上 DNS 运行在 UDP 之上。它在 UDP 端口53上运行。
* 【**请求和答复通常很小，足以单包传输** Requests and responses are generally small enough to fit into a single packet】
  * 在 DNS 中使用 UDP 的想法是，请求和响应都很小 The ideas of using UDP for DNS,  is that the requests and the responses  are both small. 
* 【**不需要 TCP 的可靠性——如果没有响应，请重新发送请求** TCP reliability isn’t needed – if no answer, retransmit the request】
  * TCP 上运行这个，就会有 SYN、 SYN-ACK、 ACK 握手来建立连接; DNS 查询会在该 ACK 之后立即发送; 响应将在 TCP 连接上往返一次。然后还有 FIN、 FIN-ACK 和 ACK 握手来拆除 TCP 连接
     * 为了 TCP 连接，你发送了六个数据包，三次往返。最初的握手是为了建立连接、请求和响应，然后是为了拆除连接而握手。而且它发送的数据包远远超过需要的数量。使用 TCP 并没有什么好处
  * 三重复的 ack 不会有帮助，因为只有一个数据包被发送
  * 一旦你建立了连接，**如果一个数据包丢失了**，会发生什么？
    * TCP 重发它。好的，但是我们不需要 TCP 来做这个。
    * 我们只需要一个简单的超时，然后通过 UDP 重新传输数据包，不需要复杂的可靠性测量，也不需要拥塞控制，因为发送的数据只要一个数据包
* 【**不需要拥塞控制——不能调整你发送一个包裹的速率** Congestion control isn’t needed – can’t adjust the rate you send a single packet】
  * 拥塞控制不会有帮助: 只有一个数据包被发送
* <font color="red">因此，DNS 在历史上一直运行在 UDP 上，避免了运行在 TCP 上的复杂性和开销</font>So, as a result, DNS historically has  run over UDP, and avoided the complexity  and the overheads of running over TCP.

### UDP-DNS报文格式

what's in a DNS over UDP  packet

图中显示的是一个IPv4数据包，里面有一个UDP头，然后是DNS报文的内容 the diagram shows an IPv4 packet,  with a UDP header in it,  and then the contents of the DNS  message.

* **DNS报文的内容是固定的,以表明这是一个DNS包** The contents of the DNS message are  fixed header to indicate that this is  a DNS packet
  * **Question section** 查询问题区域
  * **Answer Section**
  * **Authority Section**
  * **Additional information Section**

#### 查询问题区域：Question Section

当你在进行请求时，问题部分会被填满 When you're making a request, the question  section gets filled it

* **域名和要求的记录类型，表** List of domain names and requested record type
* **如**：question section might say
  * what is the AAAA record  for domain “csperkins.org”
* 【Can include more than one question】<font color="red">只要数据包能容纳，可以在一个请求中包含多个查询问题</font> And you can include more than one  question in a request, provided they fit  in the packet.

#### 回答问题 & 权威名称服务器 & 附加信息区域

Answer, authority, and additional information sections:

![](/static/2021-04-19-23-45-47.png)

:orange: **回答问题区域** Answer

* 【**域名和记录类型、记录数据和存活时间，列表** List of domain names and record type, record data, and time-to-live】包含与正在查找的名称对应的 IP 地址，并且包含一个生存时间来指定该名称的有效期
* 【**答案部分响应前一个请求中的一个问题**Answer section answers a question made in a previous request】
  * **如**：the AAAA record for domain csperkins.org is 2a00:1098:0:86:1000::10 and it’s valid for 1 hour

:orange: **权威名称服务器部分描述了响应的来源** Authority describes where the answer came from

### UDP-DNS报文例子-dig

![](/static/2021-04-19-23-51-13.png)

这是通过一个名为 dig 的工具捕获的，dig 是 Linux 和 macOS 上存在的标准 DNS 查找实用程序

黑色的部分，是问题部分，显示我们正在查找我网站的 a 记录，“ csperkins.org”

在蓝色部分，我们可以看到答案部分的内容，其中指定站点的 IP 地址为93.93.121.127，其生存时间为2681秒

我们看到一个权威部分，它指定响应来自 ns1.mythic-beasts.com 或 ns2.mythic-beasts.com 的名称服务器，这些是托管该域名的名称服务器

红色的附加信息部分，它告诉我们这些名称服务器的 IP 地址，所以我们可以联系这些服务器，如果我们想找到关于这个域名的附加信息

这是典型的结构，你可以在 DNS 数据包中看到。请求和响应中的一个问题。以及问题、回答、权威和附加信息部分。

## DNS Over TLS （DoT）

【**基于 UDP 的 DNS 是不安全的** DNS over UDP is insecure】

* **数据包没有以任何方式进行加密或身份验证** The packets are not encrypted or authenticated
* **客户端和解析器之间路径上的设备可以查看 DNS 查询和响应——并可以伪造响应** Devices on the path between client and resolver can see DNS queries and responses – and can forge responses

---

![](/static/2021-04-20-12-02-57.png)

:orange:【**DNS over TLS 解决了这个问题**】解决这个问题的一种方法是在 TLS 上运行 DNS，而不是在 UDP 上运行它

* 【**DNS 客户端打开TCP 连接(端口853)到解析器** DNS client opens a TCP connection to the resolver (port 853)】<font color="red">这种方式的工作原理是，DNS 客户端打开一个到解析器的 TCP 连接，而不是发送 UDP 数据包。它与端口853上的解析器进行 TCP 连接</font> And the way this works, is that  the DNS client opens a TCP connection  to the resolver, rather than sending UDP  packets. It makes a TCP connection to  the resolve on port 853.
* 【**DNS 客户端和解析器协商 TCP 连接上的 TLS 1.3会话**】DNS client and resolver negotiate a TLS 1.3 session on the TCP connection
* 【**DNS 客户端通过 TLS 连接发送查询并接收响应** DNS client sends query, and receives response, over the TLS connection】一旦（协商）完成，它就会发送查询并通过 TLS 连接接收响应，该连接在 TCP 连接上运行

:orange: 【**DNS over TLS 消息的格式与 DNS over UDP 的格式完全相同，并且包含完全相同的信息——唯一的区别是它们是通过 TLS 而不是 UDP 发送的**  DNS over TLS messages are formatted exactly the same as DNS over UDP, and contain exactly the same information – only difference is that they're sent over TLS not UDP】

* 现在，请求中的内容和响应中的内容，与通过 UDP 发送的内容完全相同。请求的内容的格式与 UDP 数据包的内容完全相同。但是，它们不是通过 UDP 数据包发送，而是通过 TLS 记录发送  Now what's in the request, and what's  in the response, is exactly the same  as if it was sending over UDP.  The contents of the request are formatted  exactly the same way, as would be  the contents of the UDP packet.  Except, instead of being sent in a  UDP packet, they’re sent within a TLS  record. 

:orange: 【**由于需要协商 TCP 和 TLS，因此在 UDP 上比 DNS 的开销更慢和更高，但更安全** Slower and higher overhead than DNS over UDP – due to need to negotiate TCP and TLS –but more secure】

* 显然，这也是一个更高的开销。您必须首先协商一个 TCP 连接。然后你必须协商一个 TLS 连接。然后您可以发送 DNS 请求，并获得响应。然后关闭 TLS 连接，关闭 TCP 连接   It's also, clearly, a lot higher overhead.  You have to first negotiate a TCP  connection.  Then you have to negotiate a TLS  connection. And then you can send the  DNS request, and get the response.  Then you tear down the TLS connection,  and you tear down the TCP connection.
* 因此，通过UDP的DNS发送请求和获得响应的单一往返时间，变成了建立TCP连接的往返时间，接着是协商TLS的往返时间，接着是发出DNS请求和获得响应的往返时间，接着是关闭所有连接的几个往返时间  So, what would be a single round-trip  time, to send the request and get  the response  with DNS over UDP, turns into  a round-trip time to set-up the TCP  connection, followed by a round-trip time to  negotiate TLS, followed by a round-trip time  to make the DNS request and get  the response,  followed by a couple more round-trip times  to tear down all the connections
* **这是一个更高的开销，而且它的运行速度明显较慢，但它提供了更多的安全性**  It’s a lot higher overhead, and it  runs it runs noticeably slower, but it  provides more security.

## DNS over HTTPS (DoH)

![](/static/2021-04-20-12-21-35.png)

:orange: DNS 服务器 TLS 实际上工作得相当好，而且部署得比较广泛。我们也开始看到一些提供安全访问 DNS 的替代方法。其中一个基于 HTTPS 上的 DNS，通常简称为“ DoH”。DNS server TLS actually works reasonably well,  and is moderately widely deployed.  We’re also starting to see a couple  of alternative methods of providing secure access  to the DNS.  One of these DNS over HTTPS,  often shortened to “DoH”

:orange: 【**DoH 允许客户端使用 HTTPS 向解析器发送查询** DoH allows a client to send queries to a resolver using HTTPS】DoH 是一种允许客户端使用 HTTPS 而不是 UDP 或 TLS 查询 DNS 解析器的方法

* 这里的想法是，打开一个到解析器的 HTTPS 连接，然后通过这个连接发送查询，然后得到回应 And the idea here, is that you  open an HTTPS connection to the resolver,  and you then send the query over  that connection, and you get the response  back in return
* 【**可以使用 HTTPS 中的 GET 或 POST 方法** Can use with either GET or POST methods in HTTPS】有两种格式化请求的方法
  * GET
  * POST
  * <font color="red">不管是使用GET还是POST，假设名字存在，将是一个HTTP 200 Ok的响应。且content type 为`application/dns-message`,响应体都为DNS信息。与基于UDP的DNS响应数据完全相同</font>

:orange: **HTTP 响应具有 Content-Type: application/DNS-message，并且包含在基于 udp 的 DNS 响应中发送的完全相同的数据**。 HTTP response has Content-Type: application/dns-message and contains the exact same data that would be sent in a UDP-based DNS response

### GET

![](/static/2021-04-20-12-21-44.png)

为URL发送一个HTTP GET请求，对于URL的文件部分，"`/dns-query?dns=`"，然后是你将在UDP包中发送的数据的`base-64`编码版本（DNS请求内容），用 "`Accept:` "头表示你期望的响应类型是 "`application/dns-message`"。 you send an  HTTP GET request for the URL,  for the file part of the URL,  “/dns-query?dns=“ and then the base-64 encoded version  of the data you would have sent  in the UDP packet,  with an “Accept:” header to indicate that  you expect a response type “application/dns-message”.

### POST

![](/static/2021-04-20-12-21-44.png)

使用HTTP POST请求，

* 相同URL path `/dns-query`s
* content type - `application/dns-message`
* 请求体content为, DNS请求内容

## DNS over QUIC (DoQ)

:candy: **不像 DNS over TLS，或 DNS over HTTPS，DNS over QUIC 还没有标准化。幻灯片上的 URL 指向规范草案，但是这仍然是一个正在进行的工作** Unlike DNS over TLS, or DNS over  HTTPS, DNS over QUIC is not yet  standardised.  The URL on the slide points that  to the draft specification, but that's still  a work in progress.

* Work in progress to define DNS over QUIC

---

![](/static/2021-04-20-12-59-25.png)

我们看到人们开始考虑进行 DNS 查询的方法，就是在 QUIC 上运行它们 And the final way we're seeing people  starting to think about making DNS queries,  is to run them over QUIC.

* <font color="red">通过 QUIC 进行 DNS 查询的想法是，它可以避免一些开销，同时仍然提供安全性</font> And the idea with making DNS queries  over QUIC, is that it can avoid  some of the overheads, while still providing  security.
* 【**与 DNS over TLS 相同的原则**  Same principle as DNS over TLS:】所以这个原理和在 TLS 上运行 DNS 是一样的

:orange: 工作方式 - 【**客户端打开 QUIC 连接到解析器** Client opens a QUIC connection to the resolver】

* 【**协商 TLS 安全性作为连接设置的一部分** Negotiates TLS security as part of the connection setup】客户端打开到解析器的 QUIC 连接，作为打开这个连接的一部分，它协商 TLS 安全性 The client opens a QUIC connection to  the resolver and, as part of opening  that connection, it negotiates TLS security.
* 【**通过该连接发送请求并接收响应**  Sends the request and receives the response over that connection】然后它在该连接中发送 DNS 请求，并通过相同的连接返回响应 And then it sends the DNS request  inside that connection, and gets the response  back over the same connection.
* 【**请求和响应包含与基于 UDP 进行的 DNS 完全相同的数据——现在基于 QUIC 发送** Requests and response contain exactly the same data as DNS over UDP – just sent via QUIC】

## Methods for DNS Resolution

![](/static/2021-04-20-13-10-56.png)

【进行 DNS 查询的方法越来越多 Increasingly many ways of making DNS queries:】

* DNS over UDP
* DNS over TLS
* DNS over HTTPS
* DNS over QUIC

【**查询和答复的内容在所有情况下都是相同的** The contents of the query and the response are identical in all cases】

* <font color="red">并且，在所有这些情况下，请求的内容、查询的内容和响应的内容都是相同的。你正在发送完全相同的 DNS 查询，完全相同的 DNS 请求。你会得到完全相同的 DNS 响应</font> And, in all of these cases,  the contents of the request, the contents  of the query, and the contents of  the response are identical.  You're sending the exact same DNS queries,  the exact same DNS requests. You're getting  the exact same DNS responses back.

:orange: 【**它们改变了查询传递到解析器的方式以及响应的返回方式，但不改变消息的内容**  They change how the query is delivered to the resolver and how the response is returned, but not the contents of the messages】改变的只是传输协议。改变的只是查询如何传递给解析器，以及响应如何返回。它根本不会改变消息的内容。它所做的，就是改变安全保障  All that's changing is the transport protocol.  All that’s changing is how the query  is delivered to the resolver, and how  the response is returned.  It doesn't change the contents of the  messages at all. What it does, is change the security  guarantees

* 如果您使用 TLS、 HTTPS 或 QUIC 来传递 DNS 查询，那么您可以确保客户端和解析器之间的网络设备上没有任何设备可以看到这些查询。所以你要保密 If you're using TLS, or HTTPS,  or QUIC to deliver the DNS queries  you're guaranteed that nobody, none of the  devices on the network between the client  and the resolver, can see those queries.  So you’re providing confidentiality. 
* 并且可以保证在客户端和解决方案之间的网络中没有任何设备可以伪造响应。所以它可以防止信息被窃听，也可以防止本地网络上的人欺骗 DNS 响应并将你重定向到一个恶意站点 And you're guaranteed that none of the  devices on the network between the client  and the resolve of can forge responses.  So it protects from eavesdropping on the  messages, and it protects from people on  the local network spoofing DNS responses  and redirecting you to a malicious site.
* <font color="red">如果你不信任解析器，它不能做的就是保护你。我们仍然需要 DNS 安全，我们仍然需要 DNS 响应签名，以便您检查解析器是否在说谎，但它至少使客户端和解析器之间的连接安全</font>  What it doesn't do is protect you  if you don't trust the resolver.  We still need DNS security, we still  need signed DNS responses, to allow you  to check if the resolver is lying,  but it at least makes the connection  between the client and the resolver secure

【**它们潜在地为客户端提供了查询不同解析器的更大灵活性**They potentially gives clients more flexibility to query different resolvers】

* 当然，通过 HTTPS 运行 DNS 的选项，它也为客户端提供了查询不同解析器的灵活性，可以使用 HTTPS 向任何它喜欢的解析器发出请求。 And, certainly with the option of running  DNS over HTTPS, it also gives the  client the flexibility to query different resolvers,  to make requests to whichever resolver it  likes, using HTTPS.
* 因此，可以灵活地为特定域选择它信任的解析程序 So that gives some flexibility to choose  a resolver that it trusts for a  particular domain.

# The Politics of Name

- DNS解析器的选择 Choice of DNS resolver
- 知识产权和DNS Intellectual property and the DNS
  - some problems involved
- 应该存在哪些域？What domains should exist?
- 谁来控制DNS根服务器？Who controls the DNS root?

## 如何选择 DNS 解析器（DHCP/手动）：Implications of Choice of DNS Resolver

![](/static/2021-04-20-14-49-59.png)

:orange: 如何选择 DNS 解析器？主机如何知道要使用哪个 DNS 解析？How is the DNS resolver chosen?How does  a host know which DNS resolve to  use?

* 【**当连接到网络时，主机使用 DHCP (DHCP)发现网络设置和配置** When connecting to network, hosts use DHCP (dynamic host configuration protocol) to discover network settings and configuration】DHCP 向主机提供它的 IP 地址，告诉它路由器的 IP 地址、网络掩码和诸如此类的参数 DHCP provides the host with its IP  address, tells it the IP address of  the router, the network mask, and parameters  such as that.
* 【**DHCP 告诉主机对网络使用什么 DNS 解析器**】DHCP tells the host what DNS resolver to use for the network
  * <font color="red">通常，由网络运营商提供操作的 DNS 解析，由ISP（ Internet 服务提供商操作）</font> And, usually, this would be a DNS  resolve operated by the network operator,  operated by the Internet service provider. 

:orange: 【**如果一个主机有多个网络接口，它可能每个接口使用不同的 DNS 解析器** If a host has multiple network interfaces, it may use a different DNS resolver for each】如果主机连接到多个网络，<font color="red">如果主机有多个网络接口，DHCP 在每个接口上分别运行，它可能为每个接口提供不同的 DNS 解析器</font> If the host connects to multiple networks,  if the host has multiple network interfaces,  DHCP runs separately on each interface,  and it may give a different DNS  resolver for each interface.

* 【**getaddrinfo ()调用带有一个提示参数，该参数可以包含本地 IP 地址** The getaddrinfo() call takes a hints parameter, that can include the local IP address】
  * <font color="deeppink">在具有多个网络接口的主机上，应该指定用来解析域名的网络接口，，通过指定本地IP来指定，确保用正确的网络接口解析域名</font> on hosts  with multiple network interfaces, should specify which  network interface  they're resolving names on, by specifying a  local IP addresses as one of the  parameters, one of the hints parameters,  in the getaddrinfo() call, to make sure  the names are resolved in the correct  interface, on the correct network
  * 例如，考虑一个连接到4G 蜂窝网络的设备和一个私人公司的以太网——以太网可能会提供公众无法访问的内部服务的名称，而这些名称在4 g 网络中是不可见的 e.g., consider a device connected to 4G cellular network and a private company Ethernet – the Ethernet might make available names of internal services that aren’t accessible to the public

:orange: 【**可以手动配置 DNS 解析器** Possible to configure the DNS resolver manually】

* e.g., to talk to Google public DNS resolver (IPv4 address 8.8.8.8)

## DNS解析 - 系统服务

![](/static/2021-04-20-15-03-19.png)

DNS 解析通常作为系统范围的服务来实现 DNS resolution has typically been implemented as  a system wide service.

* 【**操作系统实现 DNS 解析服务; 所有 DNS 查询都使用该服务** Operating system implements a DNS resolution service; all DNS queries use that service】<font color="deeppink">DHCP 配置主机，告诉主机选择哪个解析器，然后主机上的所有应用程序通过操作系统接口访问相同的解析器</font> DHCP configures the  host, tells it the resolvers to use,  and then all applications on the host  access the same resolvers through the operating  system interface
* 【**域名与地址的一致映射** A consistent mapping of names to addresses】无论哪个应用程序进行查询，它总是得到相同的答案，因为总是使用相同的 DNS 解析器  No matter which application makes the query,  it will always get the same answer,  because it's always talking to the same  DNS resolver.

:orange: DoH(DNS over HTTPS) 正在改变这一切 DoH is changing this

* 当您通过 HTTPS 使用 DNS 时，应用程序可以轻松地执行它们自己的 DNS 查询 When you have DoH, when you have  DNS over HTTPS, it's possible for applications  to easily perform their own DNS queries.
* 【**JavaScript web 应用程序现在可以通过任何 HTTP 网站轻松执行 DNS 查询** JavaScript web applications can now easily perform DNS queries via any HTTP website】使用 JavaScript 编写的 web 应用程序可以通过向任何网站（任何支持DoH的网站）发出 HTTPS 请求来执行 DNS 查询。  And, in particular, it's possible for web  applications, written in JavaScript, to perform DNS  queries by making HTTPS requests to any  website,  any website that supports DoH.
* 【**根据服务器的不同，每个应用程序可能会为同一个查询得到不同的答案; 通过 DNS 实施策略已不再容易** Each application may get different answers for the same query, depending on the server; it’s no longer easily possible to enforce policy via the DNS】
  * 不同应用程序，不同网站可能对于存在的域名，映射IP地址可能不同，，
  * **原则上应用程序可以覆盖DNS选择，可以绑定基于它自己的UDP DNS解析器，因为现在实现简单，越来越多程序都这样** in principle, it was always possible  for applications to do. It was always  possible for applications to override the choice  of DNS, it was always possible for  an application to bundle it’s own UDP-based  DNS resolver.  But it's now much easier. And,  because it's easier, more applications are starting  to do it.

## 应用自选解析器优缺点

![](/static/2021-04-20-17-00-18.png)

:orange: 赋予应用程序安全访问任意 DNS 服务器的能力，使其能够避免本地监测和/或过滤 DNS 流量 Giving applications ability to securely access arbitrary DNS servers allows them to avoid local observation and/or filtering of DNS traffic

:candy: problem - Is this flexibility for each application to perform DNS queries differently a concern?

* 如果我们让应用程序能够选择不同的 DNS 解析器，根据他们选择的解析器解析名称，这有关系吗？ Does it matter  if we're giving applications the ability to  pick different DNS resolvers, to resolve names  according to a resolver of their choice?
* 允许应用程序使用自己选择的 DNS 服务器安全地解析名称，给予灵活性是一个问题吗？我们允许应用程序进行自己的 DNS 查询是一个问题吗？  In particular, given that we're allowing applications  to securely resolve names using DNS server  of their choice, why does that matter?  Is it a problem that we're giving  flexibility? Is it a problem that we're  allowing applications to make their own DNS  queries?

:orange: 优点

* 【**应用程序应该有能力使用他们信任的安全 DNS 服务器，以避免钓鱼攻击，恶意软件，监控等** Applications should have the ability to use a secure DNS server they trust to avoid phishing attacks, malware, monitoring, etc.】应用程序应该能够选择它们信任的 DNS 服务器。确保他们避免钓鱼攻击，确保他们避免恶意软件，确保他们避免监控 In some ways it's clearly a good  thing, and it's not a concern that  different applications can perform DNS queries in  different ways.  And you can easily make the argument  that applications should have the ability to  choose a DNS server they trust.  To make sure that they avoid phishing  attacks, to make sure they avoid malware,  to make sure they avoid monitoring
* 【**为什么网络运营商能够看到 DNS 查询和修改响应? 这是一个隐私和安全风险** Why should network operators be able to see DNS queries and modify responses? This is a privacy and security risk】
  * 网络运营商应该不能看到 DNS 查询，他们应该不能修改响应。由网络运营商运行的解析器应该不能看到查询应用程序正在生成什么，如果允许这样做，就会带来隐私和安全风险。network operators should not be able to  see the DNS queries, they should not  be able to modify the responses.  Resolvers run by network operators should not  be able to see what queries applications  are making, and that by allowing this,  this is a privacy and security risk.
  * 允许应用程序与自己选择的 DNS 解析器对话，并防止网络运营商窥探他们的流量也有好处   And there's a benefit in allowing applications  to talk to a DNS resolver of  their choice, and prevent the network operator  from snooping on their traffic.

:orange: 缺点，应用程序有能力覆盖 DNS 的选择是有问题的

* 【**网络运营商过滤 DNS 响应，以阻止访问恶意网站和防止恶意软件传播-允许应用程序绕过运营商选择这是一个安全风险** Network operators filter DNS responses to block access to malicious sites and prevent malware spreading – allowing applications to bypass this is a security risk】
  * 网络运营商，他们可以过滤DNS响应，以阻止对提供恶意软件的网站的访问，或者是恶意的，或者是欺诈性的 Network operators will say that they can  filter DNS responses to block access to  sites which are providing malware, or which  are being malicious, or which are fraudulent.
  * 而允许应用程序覆盖DNS的选择，与应用程序自己选择的服务器对话，允许他们绕过这些安全服务。 它允许他们绕过保护他们免受恶意软件攻击的过滤系统，不再保护他们免受欺诈性网站的攻击。  And that allowing applications to override the  choice of DNS, talk to a server  of their choice, allows them to bypass  these security services.  It allows them to bypass the filtering  which is protecting them from malware,  that's protecting them from fraudulent websites
* 【**网络运营商过滤 DNS 响应以执行法律或社会约束——例如，互联网观察基金会 DNS 块列表以阻止英国访问存有儿童性虐待材料的网站** Network operators filter DNS responses to enforce legal or societal constraints – e.g., Internet Watch Foundation DNS block list to stop UK-based access to sites hosting child sexual abuse material】在许多国家，法律要求网络运营商过滤 DNS 响应，执行法律或社会约束  And, in many countries,  network operators are required by law to  filter DNS responses, to enforce legal or  societal constraints.
  * 例如，在英国，互联网服务提供商应用互联网观察基金会提供的 DNS 阻止列表，这是为了防止访问存有儿童性虐待材料的网站
  * 通过允许应用程序自己选择 DNS 解析器，允许它们访问互联网服务提供商提供的解析器以外的解析器，这允许应用程序选择不进行这种过滤，并访问这种被禁止的内容 By allowing applications to make their own  choice of DNS resolver, by allowing them  to access resolvers other than the one  provided by the Internet service provider,  this allows the applications to opt out  of such filtering, and to access such  prohibited content. 
* 而且，从根本上说，问题在于合法过滤和恶意/有害的DNS过滤都使用相同的机制。保护网络钓鱼攻击、恶意软件和监控DNS的机制，也保护和防止DNS请求的合法过滤。 And, fundamentally, the problem is that both  legitimate filtering, and malicious/harmful DNS  filtering, use the same mechanisms. And the  mechanisms to protect against  phishing attacks, malware, and monitoring the DNS,  also protect against, and prevent the legitimate  filtering of DNS requests.

## 网络封锁DNS解析

![](/static/2021-04-20-17-46-20.png)

网络会限制 DNS 解析器的选择吗？如果应用程序希望选自己的DNS，网络能够阻止它们选择自己的 DNS 吗？Can a network restrict the choice of DNS resolver?Can the network stop applications  from choosing their own DNS, if they  wish to do so?

* 【**防火墙可以阻止对 DNS-over-UDP 和 DNS-over-TLS 解析器的访问** Firewalls can block access to DNS-over-UDP and DNS-over-TLS resolvers】对于 DNS-over-UDP 或 DNS-over-TLS，这当然是可能的
  * **阻止进入 UDP 端口53** Block access to UDP port 53
    * 如果一个网络阻止 UDP 端口53上传出的 UDP 流量，在它的防火墙上，这将有效地阻止 dns-over-UDP 到它选择的任何站点 If a network blocks outgoing UDP traffic  on UDP port 53, for example,  in its firewall, this will effectively block  DNS-over-UDP to any sites which it chooses
  * **阻止进入 TCP 端口853** Block access to TCP port 853
    * 【封锁所有目的地 IP 地址，但允许的 DNS 解析器的地址除外 For all destination IP addresses except those of allowed DNS resolvers】同样，对于DNS-over-TLS解析器，你可以阻止对TCP 853端口的访问，并阻止向该端口的出站流量，这将阻止DNS-over-TLS访问它所允许之外的任何网站 Similarly for DNS-over-TLS resolver, you can block  access  to TCP port 853, and prevent outgoing  traffic to that port, and that will  stop DNS-over-TLS to any sites other than  the ones it allows. 
* **难以封锁 DNS over HTTPS**
  * 【**网络无法区分 DNS-over-HTTPS 与通过 HTTPS 传输的任何其他流量** Network cannot distinguish DNS-over-HTTPS from any other traffic over HTTPS】对于网络运营商来说，这里的问题是，由于流量是加密的，它所能看到的是一个向外的、加密的、到网络服务器的TCP连接。 <font color="red">而且，它无法判断通过该连接交换的数据是由网页组成的常规HTTPS流量，还是DNS-over-HTTPS请求</font>The problem here, for the network operators,  is that since the traffic is encrypted,  all it can see is an outgoing,  encrypted, TCP connection to a web server.  And it can't tell whether the data  being exchanged over that connection is regular  HTTPS traffic comprising web pages, or DNS-over-HTTPS  requests
  * 【**可能某些情况从目标IP地址分辨** May be able to tell from the destination IP address】
    * 例如，Google 使用 IPv4地址8.8.8.8作为公共 DoH 服务，但不使用其他流量 e.g., Google use IPv4 address 8.8.8.8 for public DoH services, but not other traffic
      * 而且，你知道如果你看到HTTPS请求进入这个地址，这就是DoH流量，因为谷歌在这个地址上没有运行任何其他流量
    * 【**但是如果一个网络服务器通过 HTTPS 处理一个混合的网络和 DNS 流量，不能阻止一个而不阻止另一个** But if a web server handles a mix of web and DNS traffic over HTTPS, cannot block one without blocking the other】但是，如果你有一个网络服务器，**同时处理普通网络流量和DNS over HTTPS流量**，ISP不可能阻止其中一个而不阻止另一个，**因为无法分辨** But, if you have a web server  that handles a mix of both regular  web traffic, and DNS over HTTPS traffic,  it's not possible for an ISP to  block one of these without blocking the  other.

:candy: 【**许多互联网服务提供商、许多网络运营商和许多政府都开始担心，通过HTTPS使用DNS会使使用DNS作为控制点变得更加困难** Many ISPs and governments concerned that DoH prevents use of DNS as a control point】

* 如果这是一个受欢迎的网站，如果谷歌决定在提供常规网络服务的同时提供DoH服务，那么网络运营商将很难阻止DNS over HTTPs流量 And if this is a popular website,  if Google decided to offer DoH services  along with its regular web services,  it would be very difficult for network  operators to block the DNS over HTTPs  traffic. 
* 许多组织习惯于使用DNS来阻止对某些类型流量的访问 Many organisations are used to using DNS  to block access to certain types of  traffic
  * 随着越来越多的流量转移到HTTPS的DNS，这对他们来说变得更加困难 And this is becoming much harder for  them, as more and more traffic moves  to DNS over HTTPS.
  * 当然，这到底是好事还是坏事，取决于你的决策，也取决于被阻止的流量类型。但这肯定是一个问题，而且是网络运行方式的改变 And, of course, whether that's a good  or a bad thing depends on your  politics, and it depends on what type  of traffic is being blocked. But it's  certainly an issue, and it's a change  in the way the network operates.

## 国家地区 & 通用域名 - 知识产权：Intellectual Property and the DNS

![](/static/2021-04-20-18-44-56.png)

**DNS域名，也往往会影响到知识产权的问题** DNS names, also tend to impinge on questions of intellectual property rights

问题 - **知识产权是在国家的基础上管理的** Intellectual property is managed on a national basis

* 例如，一个特定的公司可能在英国拥有某个商标，而另一个公司可能在爱尔兰共和国拥有该商标，这是完全可能的 For example, it's entirely possible that a  particular company might own a certain trademark  in the UK, while a different company  might own that trademark in the Republic  of Ireland.
  * 在这种情况下，域名 "trademark.ie "由爱尔兰共和国的公司拥有，而域名 "trademark.co.uk "由英国的公司拥有，是完全合理的，也是完全明智的。And, in that case, it would be  perfectly reasonable, and perfectly sensible, for  the domain name “trademark.ie” to be owned  by the company in the Republic of  Ireland, and the domain name “trademark.co.uk” to  be owned by the company in the  UK.
  * **这些公司中的哪家应该拥有这些域名**，是一个非常简单的法律问题，**由这些国家的法院处理**  And which of those companies should  own which of those domains is then  a very straightforward legal question,  and it's handled by the courts in  those in those countries.
* **对于国家地区顶级域名，这种问题很简单** And, for  country code top-level domains, this sort of  question is straightforward.
* **但是，对于通用顶级域名，它变得有点棘手**For the generic top-level domains, though,  it gets a bit trickier.
  * 哪家公司拥有trademark.com可能更难决定 Which of those companies owns trademark.com is likely harder to decide
  * <font color="red">每家公司都在其所在的司法管辖区拥有各自的商标。 然而，你有一个通用的域名，它不与特定的国家、特定的司法管辖区相联系，那么哪个公司应该拥有对它的权利？</font>  Each of the companies has the respective  trademark in the jurisdiction where they're based.  Yet you have a generic domain,  which is not tied to a particular  country, to a particular jurisdiction, so which  of those should have the rights over  it?
  * 【 Especially since .com is operated by a US-based organisation, and a third company might own the trademark in the US 】 特别是，这可能会变得很困难，因为".com "目前是由位于美国的组织运营的，而在美国，不同的组织可能拥有该商标  And, in particular, this may get hard  because “.com” is operated by US-based organisation  currently, and a different organisation may own  that trademark in the US.

:candy: 【**国家代码顶级名称显然是根据特定国家的法律制度运作的** A ccTLD clearly operates under legal regime of a particular country】国家代码顶级域名的优点是**明确地在一个特定国家的法律制度下运作。这使得解决有关知识产权和域名所有权的法律问题变得容易**   Country code top-level domains have the advantage  of clearly operating under the legal regime  of a particular country. It makes it  easy to resolve legal questions about intellectual  property, and about ownership of the domains

:orange: 【**使用 gTLD 可能会引起法律纠纷** Use of a gTLD might lead to legal complications】通用顶级域名则不那么明确 Generic top-level domains are much less clear.

* 通用顶级域名的所有权是基于域名运营商的所在地？还是基于申请该域名的人在哪里？ Is the right of ownership for a  generic top-level domain based on where the  domain operator is? Or based on where  the person requesting the name is? 
* 而且，如果有多个国家的人想要这个域名，而他们又不一定与域名运营商在同一个国家，这在法律上就很难确定谁有所有权，谁有控制权 And, if there are multiple people who  want the name, in multiple different countries,  and they're not necessarily the same country  as the domain operator, this gets legally  tricky to work out who has ownership  and who has control.

## 什么域名允许存在 & 决定权：What Domains Should Exist?

### 通用域

![](/static/2021-04-20-19-01-37.png)

哪些顶级域和哪些子域应被允许存在的问题 to the questions about which top-level  domains, and which subdomains should be allowed  to exist

* 而如果考虑到顶级域，**ICANN应该允许哪些通用顶级域的存在**？And If you think about top-level domains,  what generic top-level domains should ICANN permit  to exist? 
* 应该允许的(通用顶级)域名清单是什么？谁又能控制这些？What's the list of domains that should  be allowed? And who gets to control  that?

:orange:  一个长期存在的、有争议的例子是域名".xxx"，顶级域名".xxx"。 问题是这个域名，这个顶级域名，是否应该存在，以承载成人内容 And an example which has been long-running,  and is contentious, is the domain “.xxx’,  the top-level domain “.xxx”. And question is about whether this domain,  this top-level domain, should exist, in order  to host adult content.

* 如果它确实存在，谁能决定哪些内容应该放在这个域名里，放在这个顶级域名里？ 哪些内容必须放在该域中？  And if it does exist, who gets  to decide what content should sit within  that domain, within that top-level domain? And  what content must sit within that domain?  
* 而且，不同的国家有非常不同的规范和非常不同的标准，以确定什么是成人内容，以及什么类型的过滤是合适和不合适 And, different countries have very different norms,  and very different standards, for what constitutes  adult content, and for what type of  filtering is, and isn’t, appropriate
* 这显然是一个有争议的例子，但还有许多其他这样的例子
  * **什么样的顶级域名应该存在，由谁来决定 Who controls what TLDs ICANN permits? Who should control it?**？因为世界上不同地区对什么是可接受的或不可接受的有非常不同的规范。

---

### 子域

:orange: **是否应该允许特定的子域存在**？Should a particular subdomain be allowed to exist?

![](/static/2021-04-20-19-28-46.png)

* 【**世界不同地区在言论自由和允许讨论的主题方面存在显著差异** Significant differences around freedom of speech and permissible topics in different parts of the world】当**涉及到特定的子域**时，同样，不同的地区、不同的国家，在其关于言论自由的法律和规范，以及关于允许的主题、关于允许的网站主题方面有很大的差异 When it comes to particular subdomains,  again, different regions, different countries, have significant  differences in their laws and norms about  freedom of speech, and about permissible topics,  about permissible topics for websites.

:orange: 【**国家代码的顶级名称可以执行地方惯例和规则** A country-code top level name can enforce local conventions and rules】一个国家代码顶级域名可以明确地执行它所代表的国家的当地惯例和规则  And a country code top-level domain can  clearly enforce the local conventions and rules  for the country that it represents.

* 例如，如果你有一个".co.uk "域名，很明显，它应该执行英国法律。如果你有一个".de "域名，很明显它应该执行德国法律  If you have a “.co.uk” domain,  for example, it’s pretty clear that it  should enforce UK law. If you have  a “.de” domain, it's pretty clear that  should be enforcing German law

:orange: 什么规则适用于 gTLD（通用顶级域名呢？）？What rules apply to a gTLD?

* 例如，".com "呢？
* 【**如果某个特定国家/群体认为某个网站令人反感，是否应该将其删除？** If a particular country/group finds a site objectionable, should it be taken down?】如果某个国家或某个团体认为某个网站的内容令人反感，如果该网站是在通用顶级域名中，是否应该被关闭
* 【**如果 x 国认为某些内容是非法的，但在 y 国是合法的，那么在 y 国以外运作，但在 x 国可以访问的 <通用顶级域名> 是否应允许这些内容？】**  If country X decides particular content is illegal, but it is legal in country Y, should a gTLD operated out of country Y but accessible in country X permit such content?如果一个通用顶级域名的网站承载的内容在某些国家是合法的，但在其他国家是非法的，这应该被允许吗
  * 【例如，否认犹太人大屠杀在德国是非法的，但在美国不是-应该。在美国运营的网站是否允许提供这类内容的网站？  e.g., Holocaust denial is illegal in Germany but not in the US – should .com, operating from the US, permit sites hosting such content?】
  * 否认大屠杀的行为在德国是非法的，但在美国不是 To make this concrete, holocaust denial is  illegal in Germany, but not in the  US.
  * 在美国运营的".com "是否应该允许网站承载否认大屠杀发生的材料  Should “.com”, operating from the US,  permit sites which host material which denies  that the Holocaust happened?
    * 这在".com "存在的地方是合法的，但这些网站可以从德国的国家访问，而这些国家的内容是非法的  It's legal where “.com” exists, but those  sites are accessible from countries, from Germany,  where this content is illegal.

:orange: 谁来执行这些决定呢？ 谁能在这些网站之间进行仲裁？ 通用顶级域名是否应该受其所在国家的法律约束，或者只受其所在国家的法律约束？ 或者，我们是否需要某种国际规范、国际法律，关于全球可访问的域名应该如何运作，以及它们应该执行什么规则？  And who gets to enforce these decisions?  Who gets to arbitrate between the sites?  Should the generic top-level domains be bound  by,  only be bound by, the laws of  the country which they operate from?  Or do we need some sort of  international  norms, international set of laws, about how  globally accessible domains should operate, and what  rules they should enforce?

## 根服务器控制问题：Who Controls the Root Servers?

关于根服务器也有类似的问题

![](/static/2021-04-20-19-46-04.png)

【**DNS 根服务器主要由美国的组织控制** DNS root servers are mostly controlled by US-based organizations】目前，大多数**DNS根服务器是由美国的组织**运营或控制的 Currently, most of the DNS root servers  are operate, or controlled, by US-based organisations.

* 而且它们目前都承载着相同的内容。他们目前都遵循ICANN定义的顶级域名集  And they all currently host the same  content. They all currently follow the set  of top-level domains that ICANN  defines.
  * 但在技术上并没有要求他们这样做  But there's nothing technically requiring they do  so. 

:orange: **问题是，所有这些根服务器都由一个国家控制，大多数根服务器都由一个国家控制，这对其他国家是否有风险** ？ The question is, is it a risk  to other countries that all of these  root servers are controlled, that most of  the root servers are controlled, by a  single country? Should we be looking to  broaden the mix of countries that operate,  and that control, the root servers?

:orange: **我们是否应该寻求扩大运营和控制根服务器的国家的组合? 如果我们这样做，谁来决定如何发生**？  And if we do, who gets to  decide how this happens? And if we do, who gets to  decide how this happens? 

* ICANN是否应该决定，ICANN是否应该强制要求根服务器在不同国家运行?   Is this something where ICANN should be deciding, ICANN should be  mandating, that the root servers move to  be operated in different countries?
  * 这是一个特定的国家政府应该做的事情吗，并宣布他们将为他们国家的主机运行一个不同的路由服务器？这是不是联合国应该介入的事情？ Is this  something that a particular national government should  do,  and declare that they will run a  different route server for hosts in their  country? Is this something where the United  Nations should step in?
* **控制一个DNS根服务器有什么好处吗？**  And is there a benefit in controlling  a DNS root server? 
  * 还是说这只是一个没有人真正想要的行政开销？Or is it  just an administrative overhead that nobody actually  wants?
  * 从理论上讲，所有的根服务器都会返回完全相同的内容，所以你为什么要关心你是否控制了一个？  In theory, all the root servers return  exactly the same content anyway, so why  should you care if you control one?
  * 也许，你想对DNS有不同的看法，除非你想在你的国家有一套不同于世界其他地方的顶级域名   Unless, perhaps, you want a different view  of the DNS, unless you want a  different set of top-level domains in your  country than in other parts of the  world
* 同样，控制一个通用顶级域服务器有什么好处吗？Similarly, is there benefit in controlling a  generic top-level domain server? 
  * 例如，托管".com "对一个国家有好处吗 Is there a benefit to a country  in hosting “.com”, for example?

## Should There Be a Single DNS Root?

![](/static/2021-04-20-19-46-21.png)

**是否应该有一个单一的DNS根？是否所有的顶级域都可以从各地访问**？There’s also the question about whether there  should be a single DNS root?  Should all of the top-level domains be  accessible from everywhere?

* 【**是否应该有一个单一的全球 DNS？** Should there be a single global DNS?】无论你从哪里来，全球的DNS处理都应该是一样的吗？ Should the global view  of the DNS be the same, no matter where you're coming from?
* 同样的域名是否应该总是解析到同一个网站？  Should the same name always resolve to  the same site?
  * 在全球内容分布式网络中，你如何判断？With global content distribution networks, how can you tell? （with content distribution  networks which host sites at local proxies  throughout the world, can you tell?）
* 【**是否应允许不同国家过滤 DNS？** Should different countries be allowed to filter DNS?】应该允许对DNS流量进行什么样的过滤？是否应该允许不同的国家这样做，是否对允许的过滤有任何限制？And what sort of filtering of the  DNS traffic should be permitted? And should  different countries be allowed to do this,  and are there any restrictions on what  filtering should be permitted, and how it  should be implemented?
  * 如果有，以及应该如何实施限制？If so, how should such restrictions be implemented?
  * 【很难区分为符合政府授权的过滤要求而对 DNS 响应进行的修改与恶意软件、网络钓鱼攻击等造成的修改。这是一个功能还是一个 bug？ It is difficult to distinguish modifications to DNS responses made to conform to government-mandated filtering requirements from those made by malware, phishing attacks, etc. – is this a feature or a bug?】
    * 正如我们在DNS-over-HTTPS中看到的那样，目前很难区分 为了符合政府强制过滤要求而对DNS响应所做的修改，与 由恶意软件和网络钓鱼攻击等所做的修改。 DNS-over-HTTPS,  it's currently very difficult to distinguish modifications  made to DNS responses, in order to  conform to government mandated filtering requirements,  from those made by malware, and phishing  attacks, and so on. 
    * 这里的问题是，这是DNS的一个特点，还是一个错误？ 什么样的过滤应该被允许？应该是可能的吗？the question here is,  is this a feature of the DNS,  or is this a bug?  And what sort of filtering should be  permitted? Should be possible?

