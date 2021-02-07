# Lecture 3 - Secure Communications

outline

![](/static/2021-02-06-23-14-14.png)

* need for secure communications
* principles of secure communication
* TLS v 1.3
* Discussion（常见network security problems & how to write secure networed app）

* [Lecture 3 - Secure Communications](#lecture-3---secure-communications)
* [为什么需要安全通信：The need for Secure Communications](#为什么需要安全通信the-need-for-secure-communications)
  * [众多机构监控流量:Numerous Organisations Monitor Internet traffic](#众多机构监控流量numerous-organisations-monitor-internet-traffic)
  * [保护信息一致性：Protect Message Integrity](#保护信息一致性protect-message-integrity)
  * [避免协议僵化：Preventing Protocol Ossification](#避免协议僵化preventing-protocol-ossification)
  * [Security & Policy](#security--policy)
* [安全通信原则：Principles of Secure Communication](#安全通信原则principles-of-secure-communication)
  * [安全通信目标：Goals of Secure Communication](#安全通信目标goals-of-secure-communication)
  * [如何提供机密性--加密：How to Provide Confidentiality -- Encryption](#如何提供机密性--加密how-to-provide-confidentiality----encryption)
    * [对称加密：Symmetric Cryptography](#对称加密symmetric-cryptography)
    * [公钥加密：Public Key Cryptography](#公钥加密public-key-cryptography)
    * [混合加密：Hybrid Cryptography](#混合加密hybrid-cryptography)
  * [验证（信息未篡改）--数字签名：Authentication--digital signature](#验证信息未篡改--数字签名authentication--digital-signature)
    * [加密哈希散列：Cryptographic Hash Functions](#加密哈希散列cryptographic-hash-functions)
    * [数字签名：Digital Signature](#数字签名digital-signature)
  * [公钥基础设施: Trust & Public Key Infrastructure](#公钥基础设施-trust--public-key-infrastructure)
* [TLS安全传输层协议：Transport Layer Security v 1.3](#tls安全传输层协议transport-layer-security-v-13)
  * [TCP连接隐患：WHY TLS？](#tcp连接隐患why-tls)
  * [TLS v1.3 Goals](#tls-v13-goals)
  * [TLS工作原理：TLS v1.3 Overview](#tls工作原理tls-v13-overview)
  * [TLS握手协议：TLS v1.3 Handshake Protocol](#tls握手协议tls-v13-handshake-protocol)
    * [ClientHello：TLS v1.3](#clienthellotls-v13)
    * [ServerHello: TLS v1.3](#serverhello-tls-v13)
    * [Finished：TLS v1.3](#finishedtls-v13)
    * [加密算法-Client/ServerHello：Cryptographic Algorithms](#加密算法-clientserverhellocryptographic-algorithms)
  * [TLS记录协议：TLS v1.3 Record Protocol](#tls记录协议tls-v13-record-protocol)
  * [0-RTT mode扩展 连接复用：TLS v1.3 0-RTT Mode](#0-rtt-mode扩展-连接复用tls-v13-0-rtt-mode)
  * [局限性：Limitations of TLS](#局限性limitations-of-tls)
* [端到端安全必要性：End-to-end Security](#端到端安全必要性end-to-end-security)
* [鲁棒性原则：Robustness Principle（Postel's Law）](#鲁棒性原则robustness-principlepostels-law)
* [验证输入数据：Validating Input Data](#验证输入数据validating-input-data)
* [Be carefult when writing secure code](#be-carefult-when-writing-secure-code)

# 为什么需要安全通信：The need for Secure Communications

1. 基本问题  - 第三方网络流量监控 network traffic monitoring
   1. 窃听网络链路，或配置路由器以拷贝数据包
2. 保护隐私 protect privacy
   1. 如果想要数据安全传输， 可能需要加密
3. 保护数据一致性 protect message integrity
   1. 路由器可以篡改数据，传送方无法控制这一行为，但可以添加message integrity protection (电子签名 digital signature, 让发方拒收被篡改的信息)
4. Preventing Protocol Ossification
   1. firewall - prevent “bad” traffic from entering a network
   2. “WAN accelerator” devices that try to improve the performance of TCP connections running over satellite links.
   3. protocol  ossification - 限制改变网络协议的能力,限制网络发展 limit the ability to change network protocols, deter network from evolution

## 众多机构监控流量:Numerous Organisations Monitor Internet traffic

![](/static/2021-02-06-23-27-10.png)

* governments, intelligence agencies, law enforcement
  * crime prevention activities
  * domestic intelligence agencies inspect traffic to protect against terrorism

## 保护信息一致性：Protect Message Integrity

![](/static/2021-02-06-23-39-22.png)

* 除了监控，观察数据，有些组织（**government， intelligence agencies, law enforcement**）可能会修改数据 Numerous organisations may want to change messages in transit
  * 如 许多政府要求ISP审查或修改DNS响应 ， Many governments require ISPs to censor or modify DNS responses(indicates that certain sites don't exist)
  * 或重定向用户，表明页面封锁 change the address in the DNS response to direct users to a page indicating that the content is blocked.
  * 如，政府要求ISP覆写信息包含特定内容 Many governments require ISPs to censor messages containing certain content
* 许多国家已广泛接受关于限制仇恨言论、阻止儿童色情或防止恐怖主义的法律 Many countries have widely accepted laws about restricting hate speech, blocking child pornography, or preventing terrorism
  * 改写DNS响应，限制特定用户群的访问或特定页面的访问

:orange: Businesses & network operators

* 也可以限制，改写内容 can also modify the content or block the content, To enforce legal restrictions on content, terms of service, or to prevent copyright infringement
  * 如火车上DNS重定向至sign-up page要求用户付费
  * 其他的例子可能是过滤垃圾邮件或阻止恶意附件的服务，执行服务条款的服务，或试图防止版权侵权的服务 Other examples might be services that filter spam or block malicious attachments, that enforce terms of service, or that try to prevent copyright infringement.

:orange: 恶意用户 Criminals and malicious users

* 也可以进行内容修改，进行钓鱼诈骗，盗取身份，误导和诈骗。malicious users, people modifying content to conduct phishing scams, steal identity, mislead and defraud.

:candy: <font color="deeppink">难题 - 保护消息完整性的机制，防止恶意攻击者的同时，也防止良性修改</font>  mechanisms that protect message integrity against malicious attackers, also prevent benign modification

* 如部署了 DNS-over-HTTPS的网络，加密了DNS流量，保护用户免受钓鱼攻击，但是可能会无意中阻止了滥用阻止列表的工作（某些ISP故意欺骗DNS响应，以封锁访问特定不良网页），因为两者依赖于相同漏洞 For example, a recent development in network security is DNS over HTTPS. This is an approach to encrypting DNS traffic, that was designed to protect users from phishing attacks, where an attacker spoofs DNS responses to perform identity theft. It does this successfully. Unfortunately, some Internet service providers in the UK intentionally spoofed DNS responses to block access to sites hosting child abuse material, as part of a government mandated block list. Encrypting DNS traffic, to prevent identit theft, unintentionally also prevented the child abuse block list from working, since both relied on the same vulnerability.
* 难点2 - <font color="deeppink">没有从技术上区分良性完整性修改和恶意攻击的方法</font> No technical way to distinguish beneficial integrity violations from malicious attacks

## 避免协议僵化：Preventing Protocol Ossification

![](/static/2021-02-06-23-59-34.png)

* 网络运营商通常会部署各种**中间盒middleboxes**来监控/修改流量 It’s common for network operators deploy middleboxes, of various sorts, to monitor or modify traffic.
  * NAT和防火墙、流量变换器、过滤器或协议加速器等设备 These can be devices such as NATs and firewalls, traffic shapers, filters, or protocol accelerators
  * 有些是有效，必须的，有些不是
* **这些中间盒需要了解他们正在观察或修改的流量** These middleboxes need to understand the traffic they’re observing or modifying
  * 例如，为了翻译IP地址和端口，NAT需要知道IP数据包的格式以及端口在TCP或UDP头中的位置 For example, in order to translate IP addresses and ports, a NAT needs to know the format of an IP packet and where the ports are located in the TCP or UDP header.
  * 同样，一个流量整形设备，旨在限制特定用户的TCP连接吞吐量，需要了解TCP使用的拥塞控制算法，否则怎么能影响连接的发送速率 Equally, a traffic shaping device, intended to limit the throughput of TCP connections for a particular user, needs to understand the congestion control algorithm used by TCP, otherwise how can it influence the sending rate of a connection?
* 网络变的更加复杂，可能导致协议僵化 This leads to protocol ossification
  * 网络中的设备不再只是看IP头，并根据其目的地址转发数据包。 它们还了解TCP、UDP等协议的一些细节，也会观察、检查、修改这些协议。 **这就导致了协议僵化，改变终端之间运行的协议变得很困难，因为这样做会与不理解新版本协议的中间盒产生不良的交互** Devices in the network no longer just look at the IP header, and forward the packets based on their destination address. They also understand some details of TCP, UDP, and other protocols, and observe, inspect, and modify those protocols too.  **This leads to protocol ossification, where it becomes difficult to change the protocols running between the endpoints, because doing to interacts poorly with middleboxes that don’t understand the  new version of the protocol**.
  * 例如，现在要改变TCP头的格式是非常困难的，即使我们能将所有的终端系统升级到支持新版本，因为所有的NAT和防火墙(中间盒)也需要更新（不然中间箱无法理解更改）。For example, it would be very difficult to change the format of the TCP header now, even if we could upgrade  all the end systems to support the  new version, because of all the NATs  and firewalls that would also need updating.
* <font color="red">加密提供了一种防止骨化的方法。 越是加密的协议，越是容易改变，【因为加密会已经阻止中间箱理解或修改数据】。</font>Encryption offers one way to prevent ossification.  The more of a protocol that is encrypted, the easier it is to change,  since the encryption will have stopped middleboxes from understanding or modifying the data.
  * <font color="deeppink">不过，在改变端到端的协议能力，和网络的帮助功能之间，有一个权衡。越是加密的协议，越是容易改变协议，但是中间盒越是难以提供给网络有用的帮助</font> There’s a trade-off, though, between the ability to change end-to-end protocols, and the ability of the network to helpful features. The more of a protocol that’s encrypted, the easier it is to change the protocol, but the harder it is for middleboxes to provide help from the network.

## Security & Policy

![](/static/2021-02-07-00-15-08.png)

正如我们所看到的，有充分的理由对数据进行加密和认证，以保护隐私，防止欺诈，并允许协议发展，同时避免网络僵化。As we’ve seen, there are good reasons to encrypt and authenticate data, to protect   privacy, to prevent fraud, and to allow   protocols to evolve while avoiding network ossification.

* 以这种方式提供安全性是一件好事，但总是有一些**权衡**，Providing security in this way is a good thing, but there are always trade-offs,
  * 特别是，总有一些例子表明，**提供安全保护(以防止某些攻击)会阻止一些有益的监控或服务** In particular, there are always examples   where providing security to protect against some  attack will prevent some beneficial monitoring or  service
  * 我们必须为了隐私而对所有东西进行加密，但却忽略了这将造成真正的问题。 同样，我们很容易认为执法部门应该有特殊的权限来帮助防止恐怖主义和虐待儿童，例如，忽略了这将导致其他严重问题的非常真实的风险。 我们需要在工程师、协议设计者、网络运营商、政策制定者和执法部门之间进行更多的对话，以更好地理解限制和关注  There are no easy answers here.   It’s easy to argue that we must  encrypt all the things for privacy,  missing that this causes real problems.  Equally, it’s easy to argue that law  enforcement should have exceptional access to help  prevent terrorism and child abuse, for example,  missing that there are very real risks  that this will cause other serious problems.  We need more dialogue between engineers,  protocol designers, network operators, policy makers,  and law enforcement, to better understand the  constraints and concerns.

# 安全通信原则：Principles of Secure Communication

1. 如何保证信息机密性 ensuring confidentiality of messages
2. 传输中如何验证信息未被篡改 authenticate messages to ensure that they're not modified in transit
3. 验证通信中参与者身份 validating the identity of the participants in a communicaiton

## 安全通信目标：Goals of Secure Communication

![](/static/2021-02-07-01-09-09.png)

在互联网上传送数据， 发送方 - 收方，这个过程中要避免 deliver message from sender to receiver, in the process we want to avoid

* **避免信息窃听---我们需要对信息【进行加密，以提供保密性】，确保除了预定的接收者之外，没有人能够访问信息的内容**。avoid  eavesdropping on the message – we need  to encrypt it in order to provide  confidentiality, to make sure no one other  than the intended receiver can have access  to the content of the message.
* **我们要避免信息被篡改--我们需要对信息【进行认证】，以确保信息在传输过程中不会被任何参与传递该信息的设备修改** avoid tampering with the  message – we need to authenticate the  message to ensure that it's not modified  in transit by any of the devices which are which are involved in the delivery of that message.
* **我们要避免欺骗--我们要以某种方式【验证发送者的身份】，让接收者知道，并能确定消息来自谁** avoid spoofing –  we want to somehow validate the identity  of the sender, so that the receiver  knows, and can be sure of who the message came from

## 如何提供机密性--加密：How to Provide Confidentiality -- Encryption

![](/static/2021-02-07-01-14-58.png)

:orange: 不幸的是，**网络中传输的数据可以被收-发方路径上的任何设备读取** Well unfortunately data traversing the network can  be read by any of the devices on the path between the sender and  the receiver.

* **可以窃听链路上的数据**  It's possible to eavesdrop on packets as  they traverse the links that comprise the  network
* **可以配置交换机路由器，在网络不同链路之间转发数据时，对数据进行窥探** And it's also possible to configure  the switches or routers to snoop on  the data as they're forwarding it between  the different links in the network
* **网络运营商**总可以做到这一点，可以配置设备来**保存数据的副本** The network operator can always do this.  They own the network; they can configure  the devices to save a copy of  the data if they choose to do  so
* 如果**网络被入侵了**，其他用户也可以If the network's been compromised, maybe so  can others.
  * 如，侵入路由器，保存数据，将网络中数据副本重定向到其他位置 If an attacker can break  into the routers, for example, there's nothing  stopping them saving the data, redirecting copies  of data traversing the network to some  other location.

---

![](/static/2021-02-07-01-20-37.png)

:candy: 如果数据总是可以被读取，我们如何提供保密性？If the data can always be read,  how do we provide confidentiality?

* **使用加密**来确保数据被拦截或复制时是无用的。use encryption to make sure that the data is useless if it's  intercepted or copied
  * 我们无法阻止攻击者或网络运营商读取我们的数据。但我们可以确保如果他们读到了数据，他们也无法理解 We can't stop an  attacker, or the network operator, from reading  our data. But we can make sure  that they can't make sense of it  if they do read it.

:orange: 基本加密方法

* **对称加密** symmetric cryptography
  * Advanced Encryption Standard, AES.
* **公钥加密** public key cryptography
  * Diffie-Hellman algorithm,
  * the RSA algorithm
  * elliptic curve algorithms
* *它们都是基于一些相当复杂的数学原理。重要的不是数学的细节。但它们的特性是什么，它们提供了什么行为，以及它们如何帮助我们保护数据在网络中的安全*

### 对称加密：Symmetric Cryptography

![](/static/2021-02-07-01-26-43.png)

:orange: 对称加密的理念是，它可以借助密钥将明文转化为密文 the idear of symmetric encryption is that convert plain text into cipher-text with the aid of a secret key

* *通过加密算法，在本例中，AES高级加密算法，在加密密钥的帮助下，我们得到了一个我们在中间看到的加密文本块。 如果我们用同样的密钥把这个加密的文本通过逆向算法，也就是解密算法，那么我们就会得到原来的文本出来* we pass plain-text  through the encryption algorithm, in this case,   the AES Advanced Encryption Algorithm, with the  aid of an encryption key, we get  a blob of encrypted text as we  see it in the middle.  If we pass that encrypted text through  the inverse algorithm, the decryption algorithm,  using the same key, then we get  the original text back out
* <font color="deeppink">特点：一个密钥同时控制着加密和解密过程。加密用的密钥和解密用的密钥是一样的</font> The point is that a single secret  key controls both the encryption and the  decryption process. The key used to encrypt  is the same as the key used  to decrypt.
* <font color="deeppink">特点：只要秘钥保密，只有收发方知道，传输非常安全，非常快</font> provided the key is kept secret.  And it's known only to the sender  and receiver. This can be very secure,  and it can be very fast.
  * 对称算法（如AES）可以每秒加密和解密许多千兆比特。 这使得它们非常适用于互联网通信，因为它们不会降低通信速度，同时还能提供安全性。 有多种不同的对称加密算法，最广泛使用的可能是美国高级加密标准AES。Symmetric algorithms such as AES can encrypt  and decrypt many gigabits per second.  This makes them very suitable for Internet  communications because they don't slow down the  communications, while still providing security.  There are a wide range of different  symmetric encryption algorithms, probably the most widely  used is the US Advanced Encryption Standard,  AES. (AES算法是由美国国家标准研究所举办的公开竞赛的部分成果，它实际上是一个被称为Rijndael的荷兰算法。 重要的是，AES算法、Rijndael算法是公开的，**算法的安全性只取决于对密钥的保密，而不是对算法本身的保密**。 The AES algorithm was developed as part  of the output of an open competition,  run by the US National Institute of  Standards, and it's actually a Dutch algorithm  known as Rijndael.  Importantly, the AES algorithm, the Rijndael algorithm,  is public and the security of the  algorithm depends only on keeping the key  secret, not on keeping the algorithm itself  secret.)

:orange: 对称加密涉及的问题

* **如何安全进行密钥分配**？key distribution problem
  * 需要对密钥进行保密。 如果除了发送方和接收方之外的任何人都知道密钥，那么加密的安全性就会失效 need to keep the key secret.  If anyone other than the sender and  the receiver know the key, then the  security of the encryption fails.
  * 亲自见面，保证别人无法窃听 meet them in person,  give them the key, and ensure that  no one else can eavesdrop on that  communication
  * 如果无法私下见面，只能通过互联网，就给了其他人解密的可能性，破坏了安全性 --- 考虑公钥加密（非对称） if cannot meet to exchange the key and can only send the key over the Internet,  someone can eavesdrop on the key,   and that gives them the ability to  decrypt our communications and breaks the security.

### 公钥加密：Public Key Cryptography

![](/static/2021-02-07-02-08-42.png)

:orange: 公钥加密

* 将纯文本信息转换成加密形式 used to convert a plain text  message into an encrypted form
* <font color="deeppink">特点：有两个不同的密钥，用于加密信息的密钥，和解密信息的密钥是不同的 密钥是成对的。这对密钥的两半被称为公钥和私钥。 重要的是，使用其中一个密钥加密的信息，只能使用另一个密钥解密。例如，如果消息是用公钥加密的，那么只有私钥才能解密该消息</font> there are two different keys, and the key used to encrypt  the message, and the key to decrypt  the message are different  The keys come in pairs. The two  halves of the pair are known as  the public key and the private key.  Importantly, a message which is encrypted using  one of those keys can only be  decrypted using the other key. If the  message is encrypted with the public key,  for example, then only the private key  can decrypt that message.
* 并且私钥必须被保密 the corresponding private key must bee kept secret

:orange: 公钥加密可以解决对称加密的密钥分配问题 solves key distribution problem

* 解决了密钥分配的问题。 只要你能在目录中为接收者查找到合适的公钥，并且你能相信这个接收者对自己的私钥保密，那么你用他们的公钥来加密消息，你知道只有他们能解密 This solves the key distribution problem. Provided you can look up the appropriate  public key for the receiver in a  directory,  and you can trust that that receiver  has kept their private key secret,  then you use their public key to  encrypt the message, and you know that  they're the only one who can decrypt it.

:candy: 公钥加密涉及的问题 problem - very slow to encrypt & decrypt

* 公钥加密技术的问题是**非常非常慢**。公钥算法，**如Diffie-Hellman算法、RSA算法和椭圆曲线算法**，工作速度比对称加密算法慢数百万倍。 其结果是，它们太慢了，无法用于任何现实的通信量。性能不在那里。 The problem with public key cryptography is  very, very, slow. The public key algorithms  such as the **Diffie-Hellman algorithm, the RSA  algorithm,  and the elliptic curve algorithms**, work millions  of times slower than symmetric encryption algorithms.  The result is that they’re too slow  to use for any realistic amount of  communication. The performance just isn't there.

### 混合加密：Hybrid Cryptography

![](/static/2021-02-07-02-18-12.png)

:orange: 现代通信使用了所谓的混合密码学，即同时使用公开密钥和对称密码学。 这样既安全又快捷 modern communications use what's known as  hybrid cryptography, where they use a combination  of both public key and symmetric cryptography.  This provides both security and speed.

* 其工作方式是，发送方和接收方使用公钥加密技术（速度很慢）交换少量信息，然后将这些信息作为对称加密算法的密钥，速度非常快 The way this works is that the  sender and receiver use public key cryptography,  which is very slow, to exchange a  small amount of information. That information is then used as the key for the symmetric encryption algorithm,  which is very fast.

:orange: 详细原理

* 发送方选择一个随机值，我们称之为`Ks`，它将被用来作为**对称加密的密钥** the sender chooses a random  value, that we’ll call Ks, which will  be used as the key for the  symmetric encryption
* 然后发送方查找接收方的公钥`Kpub`，用它来加密`Ks`，并将结果发送给接收方 The sender then looks up the receiver’s  public key, Kpub, uses it to encrypt  Ks and sends the result to the  receiver.
* 接收方使用其对应的私钥`Kpriv`对消息进行解密，并取回`Ks`（对称加密秘钥）The receiver uses its corresponding private key,  Kpriv, to decrypt the message and retrieve  Ks.
* 这样就可以将对称加密算法的密钥Ks从发送方安全地传输到接收方。**使用公钥加密做这件事是非常慢的，但对称加密的密钥Ks（随机值）是非常小的，所以并不重要**。This securely transfers Ks, the key for  the symmetric encryption algorithm, from the sender  to the receiver. Doing this using public key encryption is  very slow, but the key for the  symmetric encryption, Ks, is very small,  so the fact it's very slow doesn't  matter.
* <font color="deeppink">双方都有对称加密秘钥后---发送方使用该密钥`Ks`，使用对称加密技术对未来的消息进行加密，例如，使用AES算法。 接收方也有`Ks`，它使用公钥加密交换的，可以用它来解密消息</font> The sender, then uses that key,  Ks, to encrypt future messages using symmetric  cryptography, for example, using the AES algorithm.  The receiver also has Ks, which it  exchanged using the public key encryption,  and can use that to decrypt the  messages.

:candy: 混合加密优点

* 对称加密技术的速度是非常快的，所以通信的性能，一旦开始，就非常快，但是它需要安全地交换密钥 Symmetric cryptography is very fast, so the  performance of the communication, once it's got  started, is very quick, but it requires  the key to be exchanged securely.
* 用公钥算法，它的速度很慢，但安全地交换密钥。The public key algorithm, which is slow,  is used to securely exchange the key.
* **混合加密，结合两者，这样做的结果就是既实现了保密性，又解决了密钥分配的问题，还能达到很好的性能** The result is something which achieves both  confidentiality, and solves the key distribution problem,   and also achieves good performance.

## 验证（信息未篡改）--数字签名：Authentication--digital signature

![](/static/2021-02-07-02-43-10.png)

* 加密技术提供了数据的保密性，并确保没有人可以偷听到从发送者向接收者发送的信息。<font color="deeppink">不过，我们还需要验证发件人的身份，并确保消息在传输过程中没有被修改</font> Encryption gives you confidentiality of data and  makes sure that no one can eavesdrop  on the messages being sent from the  sender to the receiver. We also, though, need to verify the  identity of the sender, and make sure  that messages haven't been modified in transit
* 解决方法 solution
  * <font color="blue">生成一个数字签名来验证我们的消息</font>  generate  a digital signature to authenticate our messages.
    * 而接收者则可以验证这个签名，检查签名，以确保它们来自预期的发送者  And the receiver can then validate that  signature, check the signature, to make sure they came from the expected sender
  * **数字签名依赖于公钥加密和加密哈希算法的结合** The digital signature relies on a combination  of public key cryptography, and a cryptographic hash algorithm

### 加密哈希散列：Cryptographic Hash Functions

![](/static/2021-02-07-02-47-34.png)

:orange: 定义 cryptographic hash function

* 加密哈希函数是一个函数，它接受一些任意长度的输入，并产生一个固定长度的输出哈希散列 a function that takes some arbitrary length input and  produces a fixed length output hash that  somehow represents that input

:orange: 特性

* **每一个输入都会产生不同的输出，输入的微小变化都会改变输出值** The first is that every input  will generate a different output, and the  slightest change to the input will change  the output value
* **要给找到两个输入，给出相同的输出是不可行的** The second is that it should be infeasible to give to find two inputs  that gives the same output.
* **计算哈希本身的速度要快，从输入到输出的过程要非常快** The third is that calculating the hash  itself should be fast, and going from   input to output should happen very quickly.
* **反转哈希应该是不可行的。如果你只得到了输出，就不可能找到输入是什么** And the fourth, and perhaps most important,  is that reversing a hash should be  infeasible. If you're only given the output,  there should be no way of finding  out what the inputs was.

:orange: 加密哈希作为输入数据的唯一指纹。 它提供一个简短的输出，以唯一的方式识别一个给定的信息。

* 有许多不同的加密哈希算法。 目前的建议是由IETF在RFC 6234中指定的**SHA256**。There are many different cryptographic hash algorithms.  The current recommendation is the SHA256 over  specified by the IETF in RFC 6234.
* 还有一些较老的算法，如MD5和SHA1，你可能会听到，但这些算法都有已知的安全缺陷，不建议使用There are a number of older algorithms, such MD5 and SHA1, which you may  hear about, but these all have known  security flaws and are not recommended for  use

### 数字签名：Digital Signature

![](/static/2021-02-07-02-54-49.png)

* **发送者生成一个数字签名** sender generates a digital signature
  * 为想发送的消息， 计算一个加密哈希的消息 take the message you wish to  send, and you calculate a cryptographic hash  of that message
  * 发送者用他们的私钥加密哈希值。现在私钥只有发送者知道，所以他们是唯一一个可以加密该消息的人（**只要发送者可以信任地保持其私钥的私密性，就是证明发送者一定对哈希进行了加密**）。但能解密它的是发件人的公钥，它对所有人都是可用的。用发件人的私钥加密哈希并不能提供任何保密性，因为任何人都可以用公钥解密信息 take the message you wish to  send, and you calculate a cryptographic hash  of that message.  The sender that encrypts that hash with  their private key. Now the private key  is known only to the sender,  so they're the only one who can  encrypt that message(provided the  sender can be trusted to keep its  private key private, is demonstrate that the   sender must have encrypted the hash).  But the thing which would decrypt it  is the sender’s public key, which is  available to everybody. Encrypting the hash with  the sender’s private key doesn't provide any  confidentiality, because anyone can decrypt the message  using the public key
* 由于哈希值是信息的指纹，这意味着发送者必须生成原始信息。 **然后，发送者将加密的哈希值附加到信息上，形成数字签名。然后，信息及其数字签名将被加密，并使用混合加密技术发送给接收方** Since the hash is a fingerprint of the message, this means that the sender  must have generated the original message.  The sender then attaches the encrypted hash to the message, forming the digital signature. The message, and its digital signature,  are then encrypted and sent to the  receiver using hybrid encryption.
* 当数据到达接收方时，**接收方可以验证签名。要做到这一点，它首先要解密该信息及其数字签名** When the message arrives at the receiver,  the receiver can verify the signature. To do this, it first decrypt that the message and its digital signature

---

![](/static/2021-02-07-03-09-18.png)

信息到接收方后，接收方需要验证签名，（首先要解密信息&数字签名）【签名验证过程 signature verification process】

* **接收者接收信息本身，并计算其加密哈希值** receiver decrypts the message & calculate the crptographic hash of the message
* **对于数字签名，查找发送方的公钥，并用它来解密数字签名，以获取信息中的原始加密哈希值** decrypts the digital signature using sender's public key to find the hash that the sender calculates(retrieve the original  cryptographic hash that was in the message)
* <font color="deeppink">将两个哈希值进行比较</font> It compares the hash, which has sent  in the message as part of the  digital signature, with the cryptographic hash it  just calculated.
  * 如果两者相吻合，那么它就知道该消息是真实的，并且没有被修改，但前提是它相信发送者的私钥是保密的（发送方用于加密哈希散列值的私钥）If the two match, then it knows  the messages is authentic and has been  unmodified, provided is trusts the sender to  have kept its private key private.
  * 如果它计算出的消息的哈希值，和数字签名中发送的哈希值不匹配，那么它就知道消息在传输过程中被修改了 If the hash of the message it  calculated, and the hash that was sent  in the digital signature, don't match then  it knows that somehow the message has  been modified in transit.

## 公钥基础设施: Trust & Public Key Infrastructure

> 公钥加密是安全网络的基本构件之一。 它让我们能够安全地发送消息给收件人，即使我们没有见过收件人，也能确定他们是唯一能够解密该消息的人。**而且它允许我们使用数字签名来验证信息在传输过程中没有被修改** Public Key Encryption is therefore one of  the fundamental building blocks of a secure  network.  It allows us to send a message  to a recipient securely, even if we've  not met that recipient, and be sure  that they're the only one who’ll be  able to decrypt that message. And it  allows us to use digital signatures to  verify that messages have not been modified  in transit.

:orange: 不过，公钥加密的安全性取决于是否知道**哪个公钥对应某个特定的接收方** The security of public key encryption,  though, depends on **knowing which public key corresponds to a particular receiver**.

* 三种方法 -- 确定接收者
  * **接收者亲自给你他们的密钥** The first is that the receiver gives you their key in person.
  * 第二种是**接收者把他们的密钥发给你，但是他们发送的信息是经过你信任的人认证的**。 就是说，信息里有一个**数字签名**，签名的人已经有了钥匙，可以证明这个信息是它声称的人发来的 The second is that the receiver sent  you their key, but the message in  which they send it is authenticated by  someone you trust.  That is, there’s a digital signature in  the message, signed by someone who's key  already have, that authenticates that this message  is from who it claims to be  from.
  * 第三种是，**你信任的人给你收信人的密钥**  The third is that someone you trust  gives you the receivers key.
    * 在互联网上，你**所信任的人的角色通常是由一个被称为证书颁发机构(CA**)的组织扮演的，它是公钥基础设施的一部分。In the Internet, the role of someone  you trust is often played by an  organisation known as a certificate authority,  as part of a public key infrastructure.
    * 证书颁发机构（CA）的作用是**验证潜在发送者的身份**。The role of a certificate authority is  to validate the identity of potential senders.
    * 证书颁发机构（CA）**检查潜在发送者的身份，然后在发送者的公钥上添加数字签名，以表明它已经通过CA认证**。The certificate authority checks the identity of  a potential sender, and then adds a  digital signature to the sender’s public key  to indicate that it's done so
    * **如果接收方信任公钥基础设施，信任证书颁发机构，那么它就可以验证证书颁发机构添加的数字签名，以确认发件人的身份** If a receiver trusts the public key  infrastructure, trusts the certificate authority, then it  can verify that digital signature, added by  the certificate authority, to confirm the identity  of the sender.

# TLS安全传输层协议：Transport Layer Security v 1.3

1. TLS定义 - what is TLS
2. TLS连接建立 - 握手协议，TLS handshake protocol used to establish TLS connection
3. TLS记录协议 - TLS record protocol
4. TLS 0-RTT mode/extension
   1. 减少连接建立时间 reduced conneciton setup times
5. TLS限制 limitations

## TCP连接隐患：WHY TLS？

![](/static/2021-02-07-16-21-43.png)

:orange: TCP连接是不安全的 TCP is not secure

* 无论是TCP头，还是IP头，还是它们传输的数据都没有以任何方式进行加密或验证。Neither the TCP headers, nor the IP  headers, nor the data they transfer are  encrypted or authenticated in any way
* TCP连接中发送的数据并不保密。它可以被政府、企业、网络运营商、犯罪分子或恶意用户观察到。Data sent in a TCP connection is  not confidential. It can be observed by  governments, businesses, network operators, criminals, or malicious  users
* 同样，数据也没有经过认证。 任何能够访问网络连接，或者数据流经的路由器的人，都能够修改这些数据。而发送者和接收者将无法分辨出数据是否被修改 Similarly, the data is not authenticated.  Anyone who's able to access the network  connections, or the routers over which the  data flows, is able to modify that  data. And the sender and the receiver  will not be able to tell that  such modifications have been performed.

:orange: 为了给通过TCP连接的数据提供安全性，我们需要在TCP连接中运行某种附加的安全协议来保护数据 In order to provide security for data  going across a TCP connection, we need  to run some sort of additional security  protocol within that TCP connection to protect the data

* <font color="deeppink"> 最新的版本是TLS 1.3，它用来对TCP连接中传输的数据进行加密和认证 </font>The way this is typical in  the Internet, is using a protocol called  the Transport Layer Security protocol.  The latest version of this is TLS  1.3 and it's used to encrypt and  authenticate data that is carried within a  TCP connection.
  * 解决了为TCP连接提供安全的问题 Providing security over the  top of an insecure connection, a TCP  connection, is a complex challenge, and TLS  has to define the number of complex  mechanisms in order to provide that security.
  * **因为TLS是老协议，最新的TLS必须向后兼容，不仅要兼容以前规定的TLS版本，还要兼容以前的实现问题，以及TLS规范和实现中的bug 不过协议设计者已经做得很好了。TLS 1.3版本比之前的TLS版本更小、更快、更简单，而且也更安全** In other part, the complexity comes because  TLS is an old protocol.  The latest versions of TLS have to  be backwards compatible, not only with previous  versions of TLS as specified, but with  previous implementation problems, and bugs in the  TLS specification and in its implementations  The protocol designers have done a good  job, though. TLS version 1.3 is smaller,  faster, and simpler than previous versions of  TLS, and it's also more secure.

## TLS v1.3 Goals

![](/static/2021-02-07-17-09-23.png)

:orange: 给定一个现有的连接，能够可靠地按照发送的顺序发送数据，但不安全，TLS 1.3旨在增加安全性 given an existing connection, that's capable  of delivering data reliably and in the  order it was sent, but is insecure, TLS 1.3 aims to add security

* 也就是给定一个TCP连接，它似乎是给通过该连接发送的数据增加了**认证性、保密性和完整性保护** That is given a TCP connection,  it seems to add authentication, confidentiality,  and integrity protection to the data sent  over that connection.
* **认证方面** Authentication
  * 使用了公钥加密技术，以及公钥基础设施，以验证建立连接的服务器的身份。 it uses public  key cryptography, and a public key infrastructure,  in order to verify the identity of  the server to which the connection is  made.
  * 也就是说，客户端可以随时验证它是在和所需的服务器对话。 此外，它还为客户端提供了可选的身份验证，以便服务器验证客户端的身份 That is, the client can always verify  that it's talking to the desired server.  In addition, it provides optional authentication for  the client, to allow the server to  verify the identity of the client.
* **保密性方面** Confidentiality
  * 一旦建立了连接，并验证了正确性，TLS就会为通过该连接发送的数据提供保密性。 它使用混合加密方案来提供良好的性能，同时还能提供强大的安全性 Once the connection has been established,  and verified to be correct, TLS provides  confidentiality for data sent across that connection.  It uses hybrid encryption schemes to provide  good performance, while still providing a strong  amount of security.
* **完整性方面** Integrity
  * 最后，TLS对通过连接发送的数据进行验证，以提供完整性保护。攻击者不可能修改通过TLS连接发送的数据而不被端点检测到这种修改 Finally, TLS authenticates data sent across the  connection, to provide integrity protection. It's not  possible for an attacker to modify data  sent across a TLS connection without that  modification being detectable by the endpoints.

## TLS工作原理：TLS v1.3 Overview

![](/static/2021-02-07-17-22-24.png)

* **首先，必须建立一个TCP连接**。first of all, a TCP connection  must be established.
  * TLS本身并不是一个传输协议，它依赖于一个基础的TCP连接来交换数据, 一旦TCP连接建立，TLS就会在该连接中运行 TLS is not a  transport protocol itself, and it relies on  an underlying TCP connection in order to  exchange data. Once the TCP connection has been established,  TLS runs within that connection.
* **握手协议** TLS handshake protocol
  * 在连接开始时，**握手协议的目标是对端点进行认证，并同意使用什么加密密钥** The goal of the handshake protocol,  at the beginning of the connection,  is to authenticate the endpoints and agree  on what encryption keys to use.
* **记录协议** TLS record protocol
  * 一旦握手协议完成，TLS就会切换到运行记录协议，**让端点通过连接，交换经过认证和加密的数据块** Once this is completed, TLS switches to  running the record protocol, which lets endpoints exchange authenticated and encrypted blocks of data over the connection.
  * TLS将TCP字节流变成一系列的记录（块）。它提供了框架，逐块传送数据，**每个数据块都经过加密和验证，以确保在该数据块中发送的数据是保密的，并且没有被修改** TLS turns the TCP byte stream into  a series of records. It provides framing,  delivers data block by block, each block  being encrypted and authenticated to ensure that  the data being sent in that block  is confidential, and arrives unmodified.

## TLS握手协议：TLS v1.3 Handshake Protocol

![](/static/2021-02-07-19-22-07.png)

:orange:TLS建立过程

* **先正常建立TCP连接** A secure connection over the Internet starts  up establishing a TCP connection as normal.
  * 客户端连接到服务器，发送一个`SYN`包，以及**客户端的初始序列号** The client connects to the server,  sending a SYN packet, along with its  initial sequence number.
  * 服务器用`SYN-ACK`响应，确认客户端的初始序列号，并提供**服务器的初始序列号** The server response with the SYN-ACK,   acknowledging the client’s initial sequence number,   and providing the server’s initial sequence number
  * 然后客户端用`ACK`包响应，确认服务器发来的那个包 And then the client responsive with an  ACK packet, acknowledging that packet from the server
* **TCP连接建立后，开始TLS握手，在TCP连接之上运行** TLS handshake protocol immediately follows the set up of TCP connection
  * <font color="deeppink">TLS**客户端**在TCP握手的最后一次`ACK`后，立即向服务器发送`TLS ClientHello`消息</font> The TLS client sends a TLS ClientHello  message to a server immediately following the  final ACK of the TCP handshake.
  * <font color="deeppink">服务器用`TLS ServerHello`消息回应</font> The server responds to that with a TLS ServerHello message
  * <font color="deeppink">然后客户端用`TLS Finished`消息回应</font> then the client in return  responds with a TLS Finished message
* 这就结束了TLS握手，并携带了第一个安全数据块。之后，客户端和服务器切换到通过TCP连接运行TLS记录协议，并进一步交换安全数据块 This concludes the TLS handshake, and carries the  first block of secure data. Following this,  the client and the server switch to  running the TLS record protocol over the  TCP connection, and exchange further secure data  blocks

:orange: TLS握手会给TCP连接建立额外增加一个RTT往返时间 As can be seen the TLS handshake  adds an additional round trip time to  the connection establishment.

* 在连接开始的时候，TCP连接建立的时候，有一个初始的往返时间。 然后，这之后还有一个额外的往返时间，同时TLS连接和安全参数的协商，然后才能设置数据 At the start of the connection,  there's an initial round trip time while  TCP connection is set up.  And then this is followed by an  additional round trip, while the TLS connection  and the security parameters are negotiated,  before the data can be set.
* <font color="blue">因此，从TCP连接开始到TLS握手结束，再到发送第一个安全数据段，**至少有两个来回的时间（2RTT**）。</font> There's a minimum of two round trip  times from the start of the TCP  connection to the conclusion of the TLS  handshake and the first secure data segment  being sent.

### ClientHello：TLS v1.3

![](/static/2021-02-07-19-36-44.png)

:orange: TLS握手的第一部分是ClientHello消息 The first part of the TLS handshake  is the ClientHello message

* 从客户端发送到服务器的，并开始安全参数的协商 This is sent  from the client to the server,  and begins the negotiation of the security  parameters.
* **三个作用** ClientHello message does 3 things
  * **它表明了要使用的TLS版本**。It's indicates the version TLS that is  to be used
    * ClientHello消息中包含了一个**版本号，表明它希望使用【TLS 1.2版本】，也就是之前的TLS版本。 ClientHello消息包含了一组可选的扩展头，其中一个【扩展头】包含了 "其实我真的是TLS 1.3版本 "的扩展**<font color="orange">版本协商发生的方式很奇怪，在版本字段中指定一个旧版本的TLS，而用扩展头来表示真正的版本 The reason the version negotiation happens in  such a weird way, specifying an old  version of TLS in the version field,  and using an extension to indicate the  real version,</font>  What actually happens, though, is that the  ClientHello message includes a version number indicating  that it wants to use TLS version  1.2, the previous version of TLS.  The ClientHello message includes an optional set  of extension headers, and one of those  extension headers includes an extension which says  “actually I’m really TLS version 1.3”.
    * 原因 --- <font color="deeppink"> 【是因为有太多的中间盒子(middleboxes)，有太多的设备试图检查网络中的TLS流量，如果版本号改变，就会失败。协议已经僵化了。】我们在不同版本的TLS之间等待了太长时间。 部署了太多的设备，部署了太多的端点，这些端点只理解1.2版本，而且不支持版本协商。然后，当部署一个新的版本时，人们尝试用早期的TLS版本将版本号改为1.3，结果发现这些新版本不支持这种变化</font> 【because there are too many middle  boxes, too many devices which try to  inspect TLS traffic in the network,  and which fail if the version number  changes. The protocol has become ossified.】 We waited too long between versions of TLS.  Too many devices were deployed, to many  endpoints were deployed, which only understood version  1.2  and which didn't correctly support the version  negotiation. And then, when it came to  deploying a new version, and people tried  with early versions of TLS to just  change the version number to 1.3,  is was found that those new versions  didn't support the change.
    * <font color="purple">结果是，在头(header)中表示TLS版本1.3的连接往往会失败，而那些假装是TLS版本1.2的连接(extension header)，使用**扩展头**来升级版本号，则可以通过这些中间盒，成功连接，并使用新的版本</font> The result was that connections that indicated  TLS version 1.3 in the header would  tend to fail,  whereas those that pretended to be TLS  version 1.2, using an extension header to  upgrade the version number, would work through  those middleboxes, and the connection could succeed  and proceed with the new version.
  * **它表明了客户端支持的加密算法，并提供了它的初始密钥材料**。It indicates the cryptographic  algorithms that the client supports, and provides  its initial keying material.
  * **它表明了客户端连接的服务器的名称** it indicates  the name of the server to which  the client is connecting.
    * 既然是在刚刚建立的TCP连接上运行的，为什么ClientHello消息需要说明服务器名称? why the ClientHello message  needs to indicate server name, given that  it's running over a TCP connection that's  just been established to that server.<font color="deeppink">TLS经常与虚拟主机一起使用，而虚拟主机通常会托管多个网站，因此，TLS `ClientHello`中提供的服务器名称可表明，TLS消息试图通过该TCP连接，建立安全连接，访问哪些网站</font> The reason for this, is that TLS  is often used with web hosting,  and it's common for web servers to  host more than one website,  so the server name provided in the  TLS ClientHello indicates which of the sites,  which are accessible over that TCP connection,  the TLS message is trying to establish a secure connection, to.
* **ClientHello消息是连接设置握手的第一部分。它不携带任何新数据** The ClientHello message is the first part  of the connection setup handshake. It doesn't  carry any new data.

### ServerHello: TLS v1.3

![](/static/2021-02-07-19-57-15.png)

:orange: 在ClientHello之后，服务器会用ServerHello消息进行响应 Following the ClientHello, the server responds with  a ServerHello message

* **ServerHello消息指出了要使用的TLS版本** The ServerHello message also indicates the version  of TLS which is to be used
  * 和ClientHello一样，**它也指出了该版本实际上是TLS 1.2版本，并包含了一个扩展头，以说明正在建立的真实是TLS 1.3连接**  like the ClientHello, it indicates that  the version is actually TLS version 1.2  and includes an extension header to say  that it’s really a TLS 1.3 connection  that's being established
* **TLS ServerHello包括服务器选择的加密算法** The TLS ServerHello includes the cryptographic algorithms  selected by the server
  * 这些算法是客户端建议的加密算法集的子集 which are a  subset of the set suggested by the client
  * 也就是说，客户端建议它支持的加密算法，**服务器查看这些算法，找到它可以接受的算法子集**，选择其中的一种，并将其包含在响应中 That is, the client suggests the cryptographic  algorithms which it supports, and the server  looks at those, finds the subset of  them which are acceptable to it,  picks one of them, and includes that  in its response.
* ServerHello消息还**包括服务器的公钥，以及一个数字签名，可以用来验证服务器的身份** The ServerHello message also includes the server’s  public key, and a digital signature which  can be used to verify the identity  of the server
* 和ClientHello一样，它**不包含任何数据** Like the ClientHello, it doesn't include any data.

### Finished：TLS v1.3

![](/static/2021-02-07-20-03-42.png)

:orange: TLS握手结束时，会有一个Finished消息，从客户端流向服务器 the TLS handshake concludes with a  Finished message, which flows from the client  to the server

* TLS Finished消息结束了TLS连接建立握手 The TLS Finished message concludes the connection setup handshake
* **包括客户端的公钥**，includes the clients public key
* **可选地，它包括一个证书，用来验证客户端与服务器的关系**。 optionally, it includes a certificate which is used  to authenticate the client to the server.
* 除了连接建立外，它**还可以包括从客户端发送到服务器的应用数据的第一部分** In addition to the connection setup,  it may therefore include the first part  of application data that is sent from  the client to the server.

### 加密算法-Client/ServerHello：Cryptographic Algorithms

![](/static/2021-02-07-20-09-25.png)

:orange: TLS(client-server)使用ephemeral elliptic curve Diffie-Hellman密钥交换算法来推导出用于对称加密的密钥 TLS uses the ephemeral elliptic curve Diffie-Hellman  key exchange algorithm in order to derive  the keys used for the symmetric encryption

* 客户端和服务器交换公钥（通过ClientHello & ServerHello消息），作为连接设置握手的一部分，然后他们结合这两个公钥，得出用于对称加密的密钥 The client and the server exchange that  public keys, as part of the connection  setup handshake, and they then combine those  two public keys to derive the key  that's used for the symmetric cryptography
* **重要的是，对称密钥从来没有通过有线交换过。 客户端和服务器只交换他们的公钥，而对称密钥是由这些公钥衍生出来的** What's important though, is that the symmetric  key is never exchanged over the wire.  The client and the server only exchange  their public keys, and the symmetric key  is derived from those.
* **TLS服务器提供一个证书，允许客户端验证其身份，（作为ServerHello消息的一部分）。 客户端可以选择在其Finished消息中一起提供验证客户端身份的信息** A TLS server provides a certificate that  allows the client to verify its identity  as part of the ServerHello message.  The client can optionally provide this information  along with its Finished message.
  * 结果是客户端可以始终验证服务器的身份，而服务器也可以选择性地验证客户端的身份 Result is that the client can always  verify the identity of the server,  and the server can optionally verify the  identity of the client.

:orange: 加密算法的选择是由客户端驱动的 The choice of encryption algorithm is driven by the client

* 客户端提供了它所支持的对称加密算法的列表，作为其ClientHello消息的一部分  client provides the list of the symmetric encryption algorithms that it  supports as part of its ClientHello message
* 服务器从这些算法中选择，并在其ServerHello中回复 The server picks from these, and replies  in its ServerHello
* 通常的结果是选择AES或ChaCha20对称加密算法 The usual result is that either the  Advanced Encryption Standard, AES, or the ChaCha20  symmetric encryption algorithm is chosen.

## TLS记录协议：TLS v1.3 Record Protocol

![](/static/2021-02-07-20-20-33.png)

TLS连接建立，握手协议结束后， TLS记录协议开始 Once the TLS connection establishment protocol,  the handshake protocol, has completed the TLS  record protocol starts.

* <font color="deeppink">记录协议允许客户端和服务器通过TCP连接交换数据记录。 每条记录最多可以包含两个到14个字节的数据，并且是经过加密和认证的</font> The record protocol allows  the client and the server to exchange  records of data over the TCP connection.  Each record can contain up to two  to the power 14 bytes of data,  and is both encrypted and authenticated.
* **数据的记录有一个序列号，它们按照发送的顺序可靠、安全地传递** Records of data have a sequence number,  and they are delivered reliably, securely,  and in the order in which they  were sent
* 底层的TCP连接**不保留记录边界** The underlying TCP connection does not preserve  record boundaries
  * **TLS为连接添加了分帧，这样连接就可以不保留记录边界（消息边界），并且从TLS连接读取数据将被阻塞，直到收到完整的数据记录** TLS adds framing to the  connection so that it does so,  and reading from a TLS connection will  block until a complete record of data  is received.
* **TLS可以在记录之间重新协商加密密钥** TLS can  renegotiate encryption keys between records
  * <font color="blue">【TLS 连接通常使用相同的加密密钥来保护整个连接的数据】。但是，原则上，如果需要通过连接中途更改加密密钥，它可以在记录之间重新协商加密密钥。</font> A TLS connection usually uses the same  encryption key to protect data for the  entire connection. However, in principle, it can  renegotiate encryption keys between records, if there's  a need to change the encryption key  partway through a connection.

:orange: TLS 记录协议允许客户机和服务器交换记录，按照它们认为合适的方式发送和接收数据 The TLS record protocol allows the client  and the server to exchange records,  to send and receive data as they  see fit.

* 一旦他们完成记录交换，他们关闭连接，关闭底层的 TCP 连接。Once they finish doing so, they close the connection, which closes the underlying TCP  connection.

## 0-RTT mode扩展 连接复用：TLS v1.3 0-RTT Mode

![](/static/2021-02-07-20-32-25.png)

:candy: **TLS 1.3在TCP连接建立后，通常需要一个往返的时间来建立连接。 也就是有TCP SYN、SYN-ACK、ACK握手建立TCP连接，然后再进行TLS ClientHello、ServerHello、Finished交换的往返时间 = 2RTT**(至少) TLS 1.3 usually takes one round trip  time to establish the connection after the  TCP connection set up.  That is, there's the TCP SYN,  SYN-ACK, ACK handshake to establish the TCP  connection, and then an additional round trip  time for the TLS ClientHello, ServerHello,  Finished exchange.

* 然而，如果客户端和服务器之前已经进行过通信，**TLS 1.3允许他们重新使用一些连接建立参数，并重新使用同一个加密密钥** However, if the client and the server  have previously communicated, TLS 1.3 allows them  to reuse some of the connection setup  parameters, and re-use the same encryption key.

:orange: 连接复用实现原理

* pre-shared key **预共享秘钥**
  * 服务器可以发送一个额外的加密密钥作为ServerHello消息的一部分，客户端可以记住这个密钥，并在下次连接到服务器时使用它  The way this works is that the  server can send an additional encryption key  as part of its ServerHello message,  and the client can remember that key,  and use it the next time it  connects to the server
* 当客户端下一次连接到该服务器时，它会像正常的一样发送ClientHello消息。**但是，除了该ClientHello消息之外，它还可以包含一些数据，这些数据会使用预共享密钥进行加密** When the client next connects to that  server, it sends its ClientHello message as  normal. However, in addition to that ClientHello  message, it can also include some data,  and that data is encrypted using the  pre-shared key.
* ServerHello 也照常运行。**但是，可以包含使用预共享密钥加密的数据，并将其发送到客户机，以响应 ClientHello 消息中包含的数据** The ServerHello also proceeds as normal.  But again, can contain data encrypted using  the pre-shared key, and sent in reply  to the client, to the data included  in the ClientHello message.
* <font color="deeppink">因此，使用预共享密钥允许客户机和服务器利用初始的连接建立握手，交换数据。它允许在连接建立的0-RTT 内交换数据，作为第一次RTT的一部分</font> The use of the pre-shared key therefore  allows the client and the server to  exchange data along with the initial connection  setup handshake. It allows data to be  exchanged within zero RTTs of the connection  set up, as part of the first  round trip.
  * <font color="purple">因此，这个扩展被称为 TLS 1.3的0-RTT 模式。0-RTT 模式很有用，因为它允许连接更早地开始发送数据。它消除了相当于一次往返行程的延迟</font> This extension is therefore known as the  0-RTT mode of TLS 1.3.  The 0-RTT mode is useful, because it  allows connections to start sending data much  earlier. It removes one round trip times  worth of latency.

:candy: TLS v1.3 0-RTT的局限性 limitation

* 与包含序列号（sequence number）的记录包不同，TLS ClientHello 和 ServerHello 消息不包含序列号 The limitation is that, unlike the record  packets which contain a sequence number,  TLS ClientHello and ServerHello messages don't contain  a sequence number.
  * **这样做的一个后果是，作为 ClientHello 或 ServerHello 的一部分发送的数据可能会被复制，而 TLS 无法阻止这种情况** A consequence of this, is that data sent as part of a ClientHello, or a ServerHello, may be duplicated, and TLS has no way of stopping this.
  * 如果您正在编写一个在0-RTT 模式下使用 TLS 的应用程序，那么您需要非常小心，**并且只在0-RTT 包中发送所谓的幂等数据**(即数据是否多次发送到服务器并不重要的数据)。If you're writing an application that uses  TLS in 0-RTT mode you need to  be careful, and only send what's known  as idempotent data,  data where it doesn't matter if that  data is delivered more than once to  the server, in the 0-RTT packets.
  * 作为常规 TLS 连接的一部分，在第一个往返时间结束后发送的数据不会受到这个问题的影响，并且只发送到应用程序一次 Data that is sent after the first  round trip time has concluded, as part of the regular TLS connection, doesn't suffer from this problem, and is only ever  delivered to the application once.

## 局限性：Limitations of TLS

![](/static/2021-02-07-20-50-15.png)

TLS连接是安全的，但它有一些限制

* **不加密服务器名** does not encrypt server name
  * 此外，TLS ClientHello消息包括服务器名称，但没有加密。 这暴露了正在进行连接的服务器的主机名，可能是一个严重的隐私泄露 Further, the TLS ClientHello message includes the  server name, but doesn't encrypt that.  This exposes the host name of the  server to which the connection is being  made, and may be a significant privacy leak
  * <font color="orange">目前正在开发一个名为 "加密服务器名称指示 "的扩展，但这一扩展尚未完成，有人担心它可能很难部署。</font> An extension, known as Encrypted Server Name  Indication, is under development, but this is  not finished yet, and there are some  concerns that it may be very difficult  to deploy.
* **TLS是在TCP连接中运行的**TLS operates within a TCP connection.
  * 其结果是IP地址和TCP端口号不受保护。 。这就暴露了谁在进行通信，以及正在使用什么应用程序的信息 A consequence of this, is that the  IP addresses and the TCP port numbers  are not protected. This exposes information about  who is communicating, and what application is  being used.
* **TLS还依赖于公钥基础设施来验证密钥，并验证客户和服务器的身份** TLS also relies on a public key  infrastructure to validate the keys, and to  verify the identity of clients and servers.
  * 对于这个公钥基础设施的可信度，人们有一些重大关切 There are some significant concerns about the  trustworthiness this public key infrastructure
  * 原因并不是加密算法或机制不安全，**而是浏览器倾向于信任一个非常大范围的证书机构，而这些证书机构在多大程度上是值得信任的并不清楚** The reasons for this are not that  the the cryptographic algorithms or the mechanisms  are insecure, they’re that the browsers tend  to trust a very large range of  certificate authorities, and it's not clear to  which extent all of these certificates authorities  are actually trustworthy.
* TLS的最后一个限制是，0-RTT扩展(0-RTT mode)可能会不止一次地传递数据  The final limitation of TLS is that  the 0-RTT extension may deliver data more  than once
  * 0-RTT是一个非常有用的扩展，因为它允许在连接开始时以低延迟的方式传递数据，但它有风险，这些数据会被多次传递，所以必须谨慎使用 0-RTT is a very useful extension,  because it allows data to be delivered  with low latency at the start of  the connection, but it runs the risk  that that data is delivered multiple times,  so must be used with care.

# 端到端安全必要性：End-to-end Security

![](/static/2021-02-07-21-03-54.png)

> **为了保证通信的安全，必须采用端到端的方式。也就是说，安全通信必须在初始发送方和最终接收方之间运行**，消息不能被解密或在路径的任何点上失去完整性保护。这比你想象的要难安排 For communication to be secure, it must  be end-to-end.  That is, the secure communication must run  between the initial sender and the final  recipient, and the message must not be  decrypted or lose integrity protection at any  point along the path.  That is harder to arrange than you  might imagine.

* 如果通信是在客户机和位于数据中心的服务器之间进行的，那么很容易理解客户机端点是什么。发出请求的应用程序运行在手机、平板电脑或笔记本电脑上   If the communication is between a client  and a server located in a data  centre, it’s easy to understand what is  the client endpoint. It’s the phone,  tablet, or laptop on which the application  making the request is running.
  * 但是数据中心的端点是什么？安全连接是否终止于数据中心入口处的负载平衡设备，该设备选择许多可能的服务器中的哪一个响应请求？ What is  the endpoint in the data centre though?  Does the secure connection terminate at the  load balancing device at the entrance to  the data centre, that chooses which of  the many possible servers responds to the  request?
  * 如果是这样，那么负载均衡器是否能够安全地连接到后端服务器，或者该连接在数据中心内是否不受保护 If so, does that load balancer  make a secure onward connection to the  back-end server, or is the connection unprotected  within the data centre?
  * 如果安全连接通过负载均衡器并在后端服务器上终止，那么后端服务器与数据库、计算服务器和数据中心其他部分的存储服务器之间的连接是否安全？If the secure connection passes through the  load balancer and terminates on the back-end  server, are the connections between the back-end  servers and the databases, compute servers,  and storage servers in other parts of  the data centre secure?
  * 一旦请求得到处理，数据存储在数据中心后如何保护它？And, once the  request has been handled, how is the  data protected once it’s stored in the  data centre?
* 你的威胁模型是什么？您是否担心在通信穿越客户端和数据中心之间的广域网时保护通信？或者你也关心数据中心内部的通信保护？如果您担心数据中心内的通信和数据存储，您是否试图防止其他租户的数据中心？还是针对可能破坏了数据中心基础设施的恶意用户？还是针对数据中心运营商？   What is your threat model? Are you  concerned about protecting your communication as it  traverses the wide area network between your  client and the data centre? Or are  you also concerned with protecting communications within  the data centre? If you’re concerned about  communications and data storage within the data  centre, are you trying to protect against  other tenants of the data centre? Or  against malicious users that may have compromised  the data centre infrastructure? Or against the  data centre operator?
* 内容分销网络也出现了类似的问题。像 Akamai 这样的 cdn 被广泛用作网站、软件更新、流媒体视频服务和游戏服务的后端基础设施。像 Steam store、 BBC iPlayer、 Netflix 和 Windows Update 这样的应用程序，都在不同的时间运行在 CDNs 上，尽管其中许多现在使用自己的基础设施。Cdn 本质上是大规模的高度分布式网络缓存。它们提供数据的本地副本，与必须从主站点获取内容相比，可以提高性能。Similar issues arise with content distribution networks.  CDNs, such as Akamai, are widely used  as the backend infrastructure for websites,  software updates, streaming video services, and gaming  services. Applications like the Steam store,  the BBC iPlayer, Netflix, and Windows Update,  have all run on CDNs at various  times, although many of them now use  their own infrastructure.  CDNs are essentially large-scale highly distributed web  caches. They provide local copies of data,  to improve performance compared to having to  fetch the content from the master site.
  * 因此，安全的 HTTPS 连接是从客户机到 CDN，而不是从客户机到原始站点。这就把中间人引入了这条路径。CDN 现在除了原始服务之外，还可以看到客户机发出的请求。性能更好，但是你不得不信任第三方提供关于你正在访问的网站的信息。同样，数据必须以某种方式到 CDN 缓存，并且必须在从原始服务器获取数据时受到保护，以填充缓存。您必须信任 CDN 才能正确地做到这一点。 The secure HTTPS connection is therefore from  the client to the CDN, rather than  from the client to the original site.  This introduces an intermediary into the path.  The CDN now has visibility into what  requests a client is making, in addition  to the original service.  Performance is better, but you’re forced to  trust a third party with information about  what sites you’re visiting.  Equally, the data has to get to  the CDN caches somehow, and has to  be protected as its fetched from the  original server to populate the cache.  You have to trust the CDN to  do this correctly. 
  * 作为 CDN 的用户，您知道如何或者是否知道该数据是安全的。在许多情况下，数据在两个用户之间移动。数据是否在两个用户之间进行端到端加密？或者用户和某个数据中心之间的数据加密了，但数据中心可以看到？区别可能很重要: 如果数据中心可以访问不受保护的数据，它可能会被用于针对性的广告，而且它更有可能被执法部门或政府监控部门访问 As a user of  the CDN, you have know way of  knowing how, or indeed if, that data  is secure.  In many cases, data is moving between  two users. Is that data encrypted end-to-end  between the two users? Or is the  data encrypted between the users and some  data centre, but visible to the data  centre? The difference can matter: if the  data centre has access to the unprotected  data, it may be used to target  advertising, and it’s much more likely to  be accessible to law enforcement or government  monitoring.

---

![](/static/2021-02-07-21-10-45.png)

许多应用程序使用某种形式的网络内处理。例如，视频会议系统通常使用一个中央服务器来进行音频混合和缩放视频以生成缩略图。例如，在一个大型视频会议中，如果许多用户正在发送视频，那么所有视频都将发送到中央服务器。该服务器只为活动扬声器转发高质量的视频，并为其他参与者发送一个更小、压缩更严重的版本。这样可以减少发送给每个参与者的视频数量，并防止超载他们的网络连接 Many applications use some form of in-network  processing. For example, video conferencing systems often  use a central server to perform audio  mixing and to scale the video to  produce thumbnails.  For example, in a large video conference,  if many users are sending video,  then all the video goes to a  central server. That server only forwards high  quality video for the active speaker,  and sends a smaller, more heavily compressed,  version for the other participants.  This reduces the amount of video sent  out to each of the participants,  and prevents overloading their network connections.

* 这是一件好事。但是，这也意味着中央服务器可以访问音频和视频。如果服务器愿意的话，它可以录制这个视频，并可能与其他人分享。这可能是一个问题，取决于正在讨论的内容 This is a good thing.But, it also means that the central  server has access to the audio and  video. The server can record that video,  if it so chooses, and potentially share  it with others. That may be a  concern, depending on what’s being discussed.
* 构建这样一个应用程序的另一种方法是对数据进行加密，不让服务器访问。这增加了用户的隐私，因为数据是端到端加密的，服务器无法访问，但是这意味着服务器无法帮助压缩数据和管理负载，这意味着基于服务器的特性，比如云记录和字幕变得更难提供。**它牺牲了功能和性能，以增加隐私** An alternative way of building such an  application leaves the data encrypted, and doesn’t  give the server access. This increases the  privacy of the users, since the data  is encrypted end-to-end and isn’t available to  the server, but means that the server  can’t help compress the data and manage  the load, and it means that server-based  features, like cloud recording and captioning become  much harder to provide. It trades-off features  and performance, for increased privacy.

# 鲁棒性原则：Robustness Principle（Postel's Law）

![](/static/2021-02-07-21-14-23.png)

在构建网络应用程序时，重要的是要考虑网络协议是如何实现的。网络协议可能相当复杂，并且难以实现。它们具有语法和语义，在许多方面类似于编程语言。**而且，像程序一样，应用程序接收到的协议消息可能包含语法错误或其他错误。如果您收到的协议数据不正确，您会怎么做**？When building networked applications, it’s important to  consider how the network protocol is implemented.  Network protocols can be reasonably complex,  and difficult to implement. They have a  syntax and semantics, in many ways similar  to a programming language. And, like a  program, the protocol messages your application receives  may contain syntax errors or other bugs.  What do you if, if the protocol  data you receive is incorrect?

* **一个经常被引用的准则是 --- "伯斯塔尔法则 Postel's Law"** A frequently quoted guideline is Postel's law.
  * Postel’s law can be summarised as “Be liberal in what you accept, and conservative  in what you send” 可以概括为“在你接受的事情上要自由，而在你发送的事情上要保守"
  * **也就是说，在生成协议消息时，尽最大努力做到正确。确保您发送的消息严格遵守协议规范。但是，在接收消息时，要接受这些消息的生成器可能并不完美。如果一个信息是畸形的，但是毫不含糊和可以理解的，伯斯塔尔定律建议无论如何都要接受它**。That is, when generating protocol messages,  try your hardest to do so correctly.  Make sure the messages you send strictly  conform to the protocol specification.  But, when receiving messages, accept that the  generator of those messages may be imperfect.  If a message is malformed, but unambiguous  and understandable, Postel’s law suggests to accept  it anyway.
  * 这很好，但是在兼顾安全性和互操作性方面很重要。不要在你试图接受的事情上过于宽容。有一个明确的规定，你将如何和何时失败可能会更合适 That’s fine, but i’s important to balance  interoperability with security. Don’t be too liberal  in what you try to accept.  Having a clear specification of how and  when you will fail might be more  appropriate.

---

![](/static/2021-02-07-21-20-24.png)

> Postel定律说："接受的东西要自由，发送的东西要保守。"如果你信任网络上的其他设备，这是有道理的。如果他们发送的信息有问题是诚实的错误，而不是有意的恶意，那就有意义。Postel’s law says “Be liberal in what  you accept, and conservative in what you  send”.  That makes sense if you trust the  other devices on the network.  It makes sense if the problems with  the messages they send are honest mistakes,  and not intended to be malicious.

---

![](/static/2021-02-07-21-21-13.png)

> 不过从Postel的时代开始，网络已经发生了变化。 正如 FreeBSD 开发者之一 Poul-Henning Kamp 所说："Postel 与他所有的朋友生活在一个网络中。我们生活在一个有所有敌人的网络中。 Postel对今天的互联网来说是错误的"。
> The network has changed since Postel’s time,  though.  As Poul-Henning Kamp, one of the FreeBSD  developers, says “Postel lived on a network  with all his friends. We live on  a network with all our enemies.  Postel was wrong for todays internet”.

---

![](/static/2021-02-07-21-22-18.png)

> 这是一个重要的问题。 任何网络系统都会经常受到攻击。 有很多人在扫描网络的漏洞。积极尝试破解你的应用程序。如果你写了一个服务器，并让它在互联网上可以访问，那么人们会试图破解它。 这不是因为你是目标。 而是因为现在的机器和网络连接速度已经足够快，可以在几个小时内扫描互联网上的每一台机器，看看它是否存在特定问题的漏洞。 这不是个人的问题。但你的服务器会被攻击。 幻灯片上显示的Martin Thomson写的关于 "稳健性原则的危害后果 "的论文，详细地谈到了这一点，并给出了如何处理畸形消息的详细指导。如果你写的是网络化、应用，我强烈鼓励你去读一读。
> This is an important point.  Any networked system is frequently attacked.  There are many people scanning the network  for vulnerabilities. Actively trying to break your  applications. If you write a server,  and make it accessible on the Internet,  then people will try to break it.  This is not because you’re a target.  It’s because machines and network connections are  now fast enough that it’s possible to  scan every machine on the Internet,  to see if it’s vulnerable to a  particular problem, within a few hours.  It’s not personal. But your server will  be attacked.  The paper shown on the slide,  on “The Harmful Consequences of the Robustness  Principle”, by Martin Thomson, talks about this  in detail, and gives detailed guidance on  how to handle malformed messages. If you  write networked, applications, I strongly encourage you  to read it.

# 验证输入数据：Validating Input Data

![](/static/2021-02-07-21-24-20.png)

> 其中一个关键点是，网络应用程序使用不可信的第三方提供的数据。正如我们所讨论的，从网络读取的数据可能不符合协议规范。这可能是由于无知、漏洞、恶意或者想要破坏服务。最重要的经验教训之一是，在使用从网络接收的所有数据之前，必须仔细地验证它们。不要相信来自网络上其他设备的任意数据。在使用之前，仔细检查它，并确保它包含了你期望的内容。这在使用脚本语言时尤为重要，因为它们通常包含转义字符，会触发特殊处理。幻灯片上的漫画就是一个例子。这个想法是，处理学生名字的软件看到结束引用，并将名字的其余部分解释为一个 SQL 命令，以便从数据库中删除学生记录。这是一个愚蠢的例子。但令人惊讶的是，类似的问题，即 SQL 注入攻击，在实践中发生的频率很高。类似的问题也出现在许多其他编程语言中。这不仅仅是一个与 sql 相关的问题。注意处理数据的方式。
>   One of the key points made is  that networked applications work with data supplied  by un-trusted third parties.  As we’ve discussed, data read from the  network may not conform to the protocol  specification. This may be due to ignorance,  bugs, malice, or a desire to disrupt  services.  One of the most critical lessons is  that you must carefully validate all data  received from the network before you make  use of it.  Don’t trust arbitrary data that comes from  another device over the network. Check it  carefully, and make sure it contains what  you expect, before use.  This is especially important when working in  scripting language, that often contain escape characters  that trigger special processing. The cartoon on  the slide is an example. The idea  is that the software processing the student’s  name sees the closing quote, and interprets  the rest of the name as an  SQL commands to delete the student records  from the database.  It’s a silly example.  But it’s surprising how often similar problems,  known as SQL injection attacks, occur in  practice.  And similar problems occur in many other  programming languages. This is not just an  SQL-related problem.  Be careful how you process data.

# Be carefult when writing secure code

![](/static/2021-02-07-21-26-42.png)

一般来说，在编写网络应用程序时要小心。网络充满敌意。任何联网的应用程序都是安全的关键。任何从网络接收数据的东西都会受到攻击。在编写联网应用程序时，仔细指定它们在正确和不正确的输入下的行为。仔细验证输入和处理错误。并检查代码是否按预期的方式运行。在别人破坏你的应用程序之前，试着破坏它 in general, be careful how you  write networked applications.  The network is hostile.  Any networked application is security critical.  Anything that receives data from the network   will be attacked.  When writing networked applications, carefully specify how  they should behave with both correct and  incorrect inputs. Carefully validate inputs and handle  errors. And check that your code behaves  as expected. Try to break your application,  before someone else does. 

如果您使用类型或内存不安全的语言(如 c 和 c + +)编写应用程序，则需要额外的大小写，因为这些语言具有额外的故障模式。编写一个容易遭受缓冲区溢出、自由 bug、竞态条件等问题的 c 或 c + + 程序是非常容易的。这些漏洞几乎可以肯定是安全漏洞。根据经验，如果您已经编写了一个 c 或 c + + 程序，并且可能会导致程序崩溃，产生“分段冲突”消息，那么这可能是一个可利用的安全漏洞。您是否曾经设法编写过一个不会以这种方式崩溃的非常重要的 c 程序？这就是为什么网络编程是困难的  If you’re writing your application using a  type- or memory-unsafe language, such as C  and C++, take extra case, since these  languages have additional failure modes.  It’s very easy to write a C  or C++ program that suffers from buffer  overflows, use after free bugs, race conditions,  and so on. Such bugs are almost  certainly security vulnerabilities.  As a rule of thumb, if you’ve  written a C or C++ program,  and can cause it to crash with  a “segmentation violation” message, then that’s probably  exploitable as a security vulnerability.  Have you ever managed to write a  non-trivial C program that never crashes in  that way?  This is why network programming is difficult.

今天，是一个极端敌对的环境。联网应用程序是安全的关键，编写安全代码是一项非常困难的技能。如果可以选择，尽可能为网络协议使用流行的、经过良好测试的、预先存在的软件库，特别是对于安全协议(如 TLS)的实现。并确保定期更新这些库，因为经常会发现问题和安全漏洞。The network, today, is an extremely hostile  environment. Networked applications are security critical,  and writing secure code is a very  difficult skill.  If you have the choice, use popular,  well-tested, pre-existing software libraries for network protocols  where possible, especially do so for implementations  of security protocols such as TLS.  And make sure to update these libraries  regularly, because problems and security vulnerabilities are  found frequently.

* 如果端点可能被破坏，数据在加密之前就被盗取，那么世界上最好的加密方法也无济于事 The best encryption in the world doesn’t  help you, if the endpoints can be  compromised and the data stolen before it’s  encrypted.

