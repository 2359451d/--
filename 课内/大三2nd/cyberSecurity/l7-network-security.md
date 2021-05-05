# Content

* network layers & how they can be attacked
* denial of service attacks
* firewalls

---

* [Content](#content)
* [Wired vs Wifi Network](#wired-vs-wifi-network)
* [Network Layers](#network-layers)
* [数据链路层：Data Link Layer](#数据链路层data-link-layer)
  * [有线:Cable & MAC](#有线cable--mac)
  * [无线:Wifi, Wireless](#无线wifi-wireless)
  * [Example - ifconfig](#example---ifconfig)
* [无线加密：WiFi Security](#无线加密wifi-security)
* [网路层：Network Layer](#网路层network-layer)
  * [地址解析协议：Address Resolution Protocol (ARP)](#地址解析协议address-resolution-protocol-arp)
  * [（ARP）涉及的攻击：Types of attacks](#arp涉及的攻击types-of-attacks)
    * [AC 泛洪：Switch Downgrading: ARP/MAC flooding](#ac-泛洪switch-downgrading-arpmac-flooding)
    * [ARP欺骗：ARP Poisoning](#arp欺骗arp-poisoning)
* [传输层：Transport Layer](#传输层transport-layer)
  * [TCP/UDP](#tcpudp)
* [应用层：Application Layer](#应用层application-layer)
  * [DNS缓存中毒：DNS Poisoning](#dns缓存中毒dns-poisoning)
  * [Sockets: Transport Layer Security](#sockets-transport-layer-security)
  * [TLS Summary](#tls-summary)
* [数据（块）传输模式：Bulk Data Transmission](#数据块传输模式bulk-data-transmission)
* [会话管理：Resumed Sessions](#会话管理resumed-sessions)
* [前向安全性:Forward Secrecy](#前向安全性forward-secrecy)
* [拒绝服务攻击：Denial of Service (DOS) Attacks](#拒绝服务攻击denial-of-service-dos-attacks)
* [分布式拒绝服务攻击：DDOS](#分布式拒绝服务攻击ddos)
  * [直接攻击：Direct Attacks](#直接攻击direct-attacks)
  * [反射式攻击：Reflector Attacks](#反射式攻击reflector-attacks)
  * [抵御DDoS](#抵御ddos)
    * [检测&ISP过滤：Detect and Filter](#检测isp过滤detect-and-filter)
* [防火墙：Firewalls](#防火墙firewalls)
  * [例子-深度防御：Defence in Depth](#例子-深度防御defence-in-depth)
  * [防火墙-代理服务：Proxy Service](#防火墙-代理服务proxy-service)
  * [包过滤功能：Packet Filetering](#包过滤功能packet-filetering)
  * [状态检测-防火墙技术：Stateful Inspection](#状态检测-防火墙技术stateful-inspection)
    * [规则例子：Example Rules](#规则例子example-rules)
* [新生代防火墙：Next Generation Firewalls (NGFW)](#新生代防火墙next-generation-firewalls-ngfw)
  * [入侵检测：Intrusion Detection](#入侵检测intrusion-detection)
  * [入侵预防：Intrusion Prevention](#入侵预防intrusion-prevention)
  * [应用感知和控制: Application Awareness and Control](#应用感知和控制-application-awareness-and-control)
* [虚拟专用网：Virtual Private Network (VPN)](#虚拟专用网virtual-private-network-vpn)
* [Attacks:Heartbleed](#attacksheartbleed)
* [基于网络的应用程序的安全性:Security of a Web Based Application](#基于网络的应用程序的安全性security-of-a-web-based-application)
  * [客户端威胁 Threats : Client](#客户端威胁-threats--client)
  * [反措施 : 客户 Countermeasures : Client](#反措施--客户-countermeasures--client)
  * [威胁：网络服务器-Threats : Web Server](#威胁网络服务器-threats--web-server)
      * [反措施](#反措施)
  * [进程服务器-Threats : Process Server](#进程服务器-threats--process-server)
  * [数据库 & 其他服务 - Threats : Database and Other Services](#数据库--其他服务---threats--database-and-other-services)
    * [反措施](#反措施-1)
  * [威胁: 计算机之间的连接 - Threats : Connections Between Computers](#威胁-计算机之间的连接---threats--connections-between-computers)
  * [安全矩阵： security matrix](#安全矩阵-security-matrix)
  * [为什么4层架构对网站有用？Why is a 4 tier architecture useful for web sites?](#为什么4层架构对网站有用why-is-a-4-tier-architecture-useful-for-web-sites)
  * [客户机上有哪些问题，如何缓解这些问题？](#客户机上有哪些问题如何缓解这些问题)
  * [网络服务器机器上有哪些问题，如何缓解这些问题？为什么它在DMZ中？](#网络服务器机器上有哪些问题如何缓解这些问题为什么它在dmz中)
  * [在系统中，用户在哪里被认证？](#在系统中用户在哪里被认证)
  * [如何处理电子邮件？为什么选择这种解决方案？](#如何处理电子邮件为什么选择这种解决方案)

# Wired vs Wifi Network

![](/static/2021-04-18-10-28-45.png)

* router
* switch通过cable与多个host相连

![](/static/2021-04-18-10-28-53.png)

* access points
  * easier to connect, as don't need a physical connection

# Network Layers

* **数据链路层** Data Link Layer
  * MAC - Medium Access Control address
  * Cable or Wifi
* **网络层** Network Layer
  * Internet Protocol **IP** Address
    * connectionless
  * Address Resolution Protocol **ARP**
    * which direction send
    * **主机发送信息时将包含目标IP地址的ARP请求广播到局域网络上的所有主机，并接收返回消息，以此确定目标的物理地址**；收到返回消息后将该IP地址和物理地址存入本机ARP缓存中并保留一定时间，下次请求时直接查询ARP缓存以节约资源
* **传输层** Transport Layer
  * ports
    * different programs running on the host
* **应用层** Application Layer
  * **DNS** - Domain Name Server
    * translates address into numeric values

# 数据链路层：Data Link Layer

## 有线:Cable & MAC

![](/static/2021-04-18-10-46-52.png)

* （特定协议，以太网）线连入各个设备 Connects individual devices, typically via ethernet: IEEE 802.3
* 每**个设备都有一个网络接口控制器的MAC地址**。 Each device has a MAC address for its Network Interface Controller.
  * 48bit, 6 groups of 2-hex digits eg `00:24:E8:95:7A:9E`
  * 前24位 - uniquely identitfy the supplier/organization (organisationally unique identifier)
    * Dell: 00-14-22 (dell has more than one)
  * 后24位 - 产商自定 network interface controller NIC specific

:orange: 特殊 MAC address

* FF:FF:FF:FF:FF:FF special, broadcast
* 01: . . . Special, multicast (restricted broadcast)
  * 01开头的，，

:orange: 基于以太网**帧**（包）通信 communivation via an ethernet frame (packet)

* Destination MAC address
* source MAC address
* Payload
* checksum

## 无线:Wifi, Wireless

Wi Fi标准显然是不同的标准 Wi Fi standard which is different standard obviously.

* Individual devices connected by WiFi: IEEE 802.11
* **有额外的帧类型** additional frame types
  * management
  * control
  * data (wifi & normal data ones for the Ethernet address)
* **接入点MAC包括在帧头中** MAC address of the Access Point AP is included in the frame header

## Example - ifconfig

![](/static/2021-04-18-11-11-57.png)

# 无线加密：WiFi Security

WiFi安全有哪些问题，如何克服这些问题？What are the problems with WiFi Security and how can they be overcome?

任何人都可以监听WiFi通信，因为它们必须是广播的。Anyone can listen in to WiFi communications since they have to be broadcast.

所有数据包都必须进行加密，因此双方都需要知道密钥。  在WPA中，密钥在初始通信中被交换，然后每个数据包根据共享的主密钥得出不同的密钥。  这一点很难做到，WPA2协议被Krack攻击所破坏。All packets have to be encrypted and so both parties need to know the key.   In WPA keys are exchanged in the initial communications and then each packet derives a different key based on the shared master key.   It has been difficult to make this work and the WPA2 protocol was broken by the Krack attack.

---

在基于连接的网络中，数据包被限制在电缆（和数据包嗅探器）上 Packets are constrained to cables (and packet sniffers) in a connectionbased network.

**在无线网络中，只要在范围内，任何人都可以监听** In a wireless network anyone can listen if, provided that are in range.

* **因此。重要信息必须加密** Important information MUST BE encrypted.

:orange: 无线等效隐私**(WEP**)已被破解 Wireless Equivalent Privacy (WEP) has been broken

* never use

:orange: WiFi保护访问(**WPA**)：2003年 WiFi Protected Access (WPA): 2003

* 每个数据包有不同的128位密钥 Different 128-bit key per packet.
* Master key
* 中间产品，比WEP好，但仍有缺陷 Intermediate produce, better than WEP but still flawed

---

:orange: **WPA2**: 2006

* Uses AES.
* 使用预共享密钥，允许蛮力破解。存在密钥重装攻击（KRACK） Uses pre-shared keys, which allow brute force cracking. Key Reinstallation AttaCK (KRACK)
* https://www.krackattacks.com/

:candy: **密钥重装攻击 KRACK - 攻击迫使涉及到的设备在几个不同的消息中重复使用同一个密钥** The attack forces the devices involves to reuse the same key for several different messages.

* 如果知道其中一个消息，那么就可以恢复包密钥 If one of the messages is know then the packet key can be recovered.
* 已知的明文攻击 Known plaintext attack.

:candy: **可以通过软件更新来修复** It can be fixed by a software update

---

:orange: **WPA3**: proposal 2018

* 旨在解决KRACK攻击(密钥重装攻击)所显示的问题。 Designed to fix the problem shown up by the KRACK attack.

# 网路层：Network Layer

* Ipv4: 32 bit address
  * 4 x 8-bit decimal values eg. 192.158.56.1
* Ipv6: 128 bit address

:orange: Special address

* 127.0.0.1 local machine
* 255.255.255.0 subnet mask (24 bit subnet)

:orange: IP packets （与以太网帧非常相似）

* Destination address
* source address
* payload
* checksum

## 地址解析协议：Address Resolution Protocol (ARP)

:orange: <font color='red'>一个设备拥有数据包目标IP地址，想找到拥有这个IP地址的设备的MAC地址</font> A device has the IP address of the packet destination and wants to find the MAC address of the device that has this IP address

:orange: 例子

* 先建立一个表
  * a table of where to send various packets
  * 第一次收到某包时，无法知道目标MAC，所以先发送广播信息（给所有连入的设备）first time received the packets has no idea where the destination is, it will send out a broadcast message to everyone it's connected to
  * 收到响应后，假设后续还会有相应数据包传入目标IP，所以将获取到的MAC-IP计入表中 assume that there will be more packets going to the same address coming fairly shortly, so put the information in the table
* CA:FE:CO:FF:EE:00 收到了 IP 地址为 192.168.0.1 的数据包，想知道要发送给谁 （**收方IP对应的收方MAC是什么**）CA:FE:CO:FF:EE:00 has received the packet with IP address 192.168.0.1 and wants to know who to send it to.
* 广播：谁有192.168.0.1：发送到FF:FF:FF:FF:FF(所有人) Who has 192.168.0.1 : sent to FF:FF:FF:FF:FF:FF` (everyone)
* 响应：192.168.0.1在`FE:ED:DE:AD:BE:EF` 发送至主机CA:FE:CO:FF:EE:00

## （ARP）涉及的攻击：Types of attacks

* 交换机降级 Switch Downgrading: ARP or MAC flooding. （AC 泛洪）
  * 也叫**拒绝服务攻击（Denial of Service**），攻击者向设备发送大量目的IP地址无法解析的伪造ARP请求报文或免费报文，**造成设备上的ARP表项溢出，无法缓存正常用户的ARP表项，从而影响正常的报文转发（广播）**
* ARP欺骗 ARP Poisoning

### AC 泛洪：Switch Downgrading: ARP/MAC flooding

什么是ARP泛滥？对它有什么防御措施？ What is ARP flooding? What defence is there against it?

运行ARP的交换机维护一个响应各种IP/端口请求的MAC地址表。  这个表是一个固定的大小。如果该表变满，那么交换机就会广播一个不在表中的服务请求。

攻击者将试图填满该表，从而使MAC帧被广播，并收到它们。

防御方必须尝试过滤数据包以防止表被填满。这可以在防火墙中完成。

The switch running ARP maintains a table of MAC addresses that respond to various IP/port requests.   This table is a fixed size. If the table becomes full then the switch broadcasts a request for a service that is not in the table.

An attacker will try and fill up the table so that the MAC frames are broadcast and they receive them.

The defence must try and filter packets to prevent the table filling up. This can be done in the firewall.

---

:orange: 交换机将 A switch will:

* 接收一个MAC帧 Receive a MAC frame
* 使用IP和端口号找到目标MAC。 Use the IP and port number to find the destination MAC
* 转发该帧 Forward the frame.

:orange: **交换机维护一个内部表（ARP表），其中包括IP地址、端口号和相关设备的MAC地址** The switch maintains an internal table of IP addresses, port numbers and the MAC addresses of the associated devices.

* 它通过观察流量动态地建立(ARP)表 It builds the table dynamically by observing the traffic.
* <font color="red">该表有一个有限的大小</font> The table has a finite size.
* **表满时**  What happens when the table is full?
  * 有些目的地将不在表中 Some destinations will not be in the table.
  * **这些帧将被广播**而不是发送到目的地 These frames will be broadcast rather than sent to the destination.

---

The Attack
:orange: 攻击

* 攻击者会发送很多不同地址的帧。The attacker sends lots of frames with different addresses
* <font color="red">这将填满表，迫使交换机开始广播帧。</font> This will fill up the table, forcing the switch to start broadcasting frames.
  * destination not in the table, switch start broadcasting the frame

:orange: 影响 implications

* **流量可以被监控，因为它被广播**。The traffic can be monitored since it is being broadcas .
* **增加的流量会降低性能**。The increased traffic reduces performance.
  * 从某设备沿着链路到另一个设备，最大限度减少流量。如果数据包广播到多个设备连接上，增加流量，降低性能come into the device, and go out onto the next device along the connection. And so that minimizes the traffic. If the packet comes in and goes out on lots and lots of connections rebroadcast then it increases the traffic and reduces the performance. 

### ARP欺骗：ARP Poisoning

什么是ARP中毒？对它有什么防御措施？What is ARP poisoning? What defence is there against it?

这种攻击试图将攻击者的IP地址插入ARP表，使数据包通过他们被路由。它反复说它承载了所请求的服务。这使得中间人攻击成为可能。This attack tries to insert the attackers IP address into the ARP table so that packets are routed through them. It repeatedly says it hosts the requested service. This allows a man-in-the-middle attack.

重复的托管要求应被拒绝。Repeated hosting claims should be rejected.

---

:orange: **这种攻击的目的不是使表溢出，而是插入不正确的值** The aim of this attack is not to fill up the table but to insert incorrect values.

* 一台接收到不在表中（ARP表中无映射）的以太网帧的主机将广播周围设备，以确定将数据包转发给谁 Remember that a machine that receives an ethernet frame not in the table will ask around to find out who to forward the packet to.
* 它可以接收不同的回复，因为 IP 协议不依赖于特定的路由。它是无连接的 It can receive different replies, since the IP protocol does not depend on a specific route. It is connectionless.
  * 机器会决定走哪条链路，选择哪个响应 The machine will then decide which route to take.
* **攻击者将发送大量的响应，声称它可以转发数据包，希望它将被选中** The attacker will send a large number of responses saying that it can forward the packet, hoping it will be chosen.
  * <font color="red">这样做的目的是让所有这些流量都通过攻击者路由，攻击者可以将其转发到其他地方</font> The aim is for all this traffic to be routed via the attacker, who can then forward it onwards.
  * 中间人攻击 Man in the middle attack

# 传输层：Transport Layer

位于IP之上，应用层之下 Sits on top of IP and connects applications

* 每个IP地址有64k个端口，用`16`位整数寻址（2^16=65536） Each IP address has 64k ports, addressed by 16-bit integers.
  * **Well known ports** have 10-bit addresses, port numbers < 1024
  * 1024 to 49151 registered for **particular applications**
  * 49152 to 65535 are **public ports**.

:orange: **每个应用程序都连接到一个特定的端口** Each application connects to a specific port.

* 因此能决定IP包发至哪个应用 how the IP packets sepcify which program it wants to process
* 20 (data), 21 (control) FTP
* 80 HTTP

## TCP/UDP

**UDP单包传输** UDP Single packet transmission

* 不保证交付 Delivery not guaranteed.

**TCP包序列传输** a sequence of packets

* 传输保证 guaranteed delivery
* 保证顺序 will be delivered in order
* 使用UDP发送数据包 并得到一个确认，数据包已被接收。如果它没有得到任何确认，它就会重传数据包，且它有一种序列号计数器，确保你可以按照正确的顺序来排列数据包。 sends out packets using UDP and gets an acknowledgment that packet has been received. And if it doesn't get no acknowledgement, it will rebroadcast the packet, and the other end, it has a sort of sequence number counter that makes sure that you can order the packets in the right order.

:orange: 都是以一个IP地址连接到端口 Both connect to ports at an IP address.

# 应用层：Application Layer

* Domain Name Server (DNS).
* Connects/translate text network addresses to IP addresses.

## DNS缓存中毒：DNS Poisoning

什么是DNS中毒？对它有什么防御措施？What is DNS poisoning? What defence is there against it?

* DNS表将网站名称与IP地址相匹配。  攻击的目的是在表中插入虚假的条目。拒绝服务攻击会重定向到127.0.0.1，而审查会重定向到一个不同的网站。The DNS table matches web site names with IP addresses.   The attack aims to insert bogus entries into the table. A denial of service attack would redirect to 127.0.0.1, while censorship would redirect to a different site.

---

本地计算机上的`/hosts`文件是域名服务器进程中的第一个接触点。/hosts file on the local computer is the first point of contact in the Domain Name Server process.

* `C:\Windows\System32\Drivers\etc\hosts`
* 如果域名不在hosts文件中，那么程序会进一步搜索。If the name is not there then the process searches further afield.

:orange: DNS缓存中毒 - **这可以通过将广告服务和监控IP地址重定向到127.0.0.1(本地主机)来作为一个添加拦截器使用** This can be use as an add blocker by redirecting advert serving and monitoring IP addresses to 127.0.0.1 (local host)

* 试图引用这些地址的代码将被发送到没有这些地址的主机（比如发到 localhost，让电脑误以为到达正确地址） Code trying to refer to these addresses will be sent to the host machine, which does not have them.
* 不会显示 They will not be displayed.
* **这种方法也可用于审查** This approach can also be used for censorship.
  * large scale hosts files

:orange: 这里有一个缺陷是，如果你知道实际的IP地址，数字，而不是文字，那么你可以绕过DNS缓存中毒。One flaw in here is that if you know the actual IP address, the numbers, rather than the text, then you can bypass DNS poisoning. 

## Sockets: Transport Layer Security

**Sockets & transport Layer Security以前叫安全套接字层**。 Used to be called Secure Sockets Layer.

* **一个套接字用一个 "管道 "连接两个应用程序**。最早在UNIX中使用。A socket connects two applications with a 'pipe'. First used in UNIX.
  * 管道还可以用于网络间，不仅仅是连接应用 pipes can beused across the network not just between 2 applications
* **数据可以双向传输**。 Data can be transmitted in both directions.
  * **数据到达目的地的顺序与放入管道的顺序相同。** Data arrives at the destination in the same order that it was put into the pipe.
* <font color="red">例外情况可以跳转队列</font> Exceptions can jump the queue.
* 管道通常在互联网上运行，将一台计算机上的应用程序与另一台计算机上的应用程序连接起来。The pipe typically runs across the internet, connecting an application on one computer with an application on another.
  * 通常情况下，浏览器连接服务器 Typically a browser connects to a server

## TLS Summary

* 公钥基础设施 Details provided in the PKI lecture.(public key infrastructure)
* 如果客户端拥有签署服务器公钥证书的认证机构（CA）的公钥证书，则使用公钥证书 Public key certificates are used if the client has a public key certificate of the certifying authority that signed the server public key certificate.
* Diffie-Hellman用于创建短暂的密钥（没有证书可用时） Diffie-Hellman is used to create ephemeral keys is there are no certificates available.
* 证书用来抵御中间人攻击 Certificate pinning and perspectives are used to try and foil a man in the middle attack

# 数据（块）传输模式：Bulk Data Transmission

块模式传输

* 传输通常使用 AES 或3DES。It typically uses either AES or 3DES.
* 通常使用密码块链接(CBC)。It typically uses cipher block chaining (CBC).
  * That's a way of encrypting data that uses an initialization vector or a bit of extra randomization, at the start of the file

:orange: **计数器密码模式**(CCM)和**伽罗瓦计数器模式**(GCM)是常见的流密码，当它们可用时传输字节。 Counter Cipher Mode (CCM) and Galois Counter Mode (GCM) are common stream ciphers, transmitting bytes when· they become available.

* string ciphers
* 一旦单个字节可用，就立即传输它们，而不是等待收集块，这是很有用的。 It can be useful to transmit single bytes as soon as they are available rather than waiting to collect blocks.

:orange: stream cipher

* it is possible to have devices or applications that encrypted, every single keystroke.

# 会话管理：Resumed Sessions

:orange: 如果一个连接可能用于多个会话，那么服务器可以在初次握手期间发送会话 ID 或会话票证(类似于 Kerberos 票证）If a connection is likely to be used for several sessions then the server can send a session ID or a session ticket (similar to a Kerberos ticket) during the initial handshake.

* 票据可以用于会话管理 So, tickets can be used for session management, you just get a ticket at staff, and you present the same ticket again for the next transmission in the session.

* 如果客户端得到了一个会话 ID，**那么当他们想要继续会话时，他们只需要提供这个ID** If the client is given a session ID then when they want to resume the session they just present the ID.
  * <font color="red">服务器通过当前会话 ID 的集合进行搜索，并检查客户端状态(IP 地址等)是否相同</font> The server searches through its collection of current session ID's and checks that the client state (IP address etc) is the same.
  * <font color="red">如果它是相同的，那么它返回相同的会话 ID，否则它返回不同的会话 ID</font> If it is the same then it returns the same session ID, otherwise it returns a different one.
* 会话票据以加密的形式包含客户端状态 A session ticket contains the client state in an encrypted form.
  * 然后服务器不需要存储所有可能的客户端状态本身，但从票据出现时恢复它们 The server then doesn't need to store all the possible client states itself, but recovers them from the ticket when it is presented

# 前向安全性:Forward Secrecy

:orange: 如果即使握手参数泄露，**如通信仍然保密，客户机-服务器会话具有前向保密性** A client-server session has forward secrecy if the communications remain secret even if the handshake parameters become compromised.

:orange: 如果所有 AES 会话密钥都是从初始握手参数生成的，然后在后续通信中重用，则 If all the AES session keys are generated from the initial handshake parameters and then reused for subsequent communications, then

* 过去和未来的所有通信(如果已经被记录)都将受到威胁 All communications in the past (if they have been recorded) and future will be compromised.
  * 其中的一个被泄露，那么所有其余的泄露，应用程序没有前向保密性 if one of them is compromised, then all the rest of the compromise the application doesn't have forward secrecy

:orange: <font color="red">实现前向保密的最佳方法是使用临时 D-H 来计算会话密钥</font>  The best way to achieve forward secrecy is to use ephemeral D-H to calculate the session keys.

* 为每个会话生成一个新的随机密钥 A new random key is generated for each session
  * 即，前向安全 - 每次通信使用随机会话密钥。因此，如果一个密钥泄露，其余的不影响。Use the random session key for each communication. And so if one is compromised the rest of the not compromised.

# 拒绝服务攻击：Denial of Service (DOS) Attacks

:orange: 几种利用系统弱点的方式 Exploit system weaknesses.

* **Ping of death** using the network enquiry ping command
  * check to see if a connected device is alive (can be reached)
  * broadcast
* **Teardrop**，发送乱七八糟的IP数据包，并利用代码中的bug将其重新组合 Teardrop, sends mangled IP packets and exploits a bug in the code that reassembles them.
* **Smurf攻击依赖于配置不良的网络**。向广播地址发送一个数据包，机器将其放大后发送给其他所有人  Smurf attack relies on a poorly configured network. Sends a packet to the broadcast address and the machine amplifies it by sending it to everyone else.
* 计算密集型计算 Computationally intensive calculations
  * Diffie-Hellman calculations in SSL or IPSec[diffie hellman RSA calculations, they will use exponentiation, of very large numbers and so they take a significant amount of time. And so the idea is to use all available processor capacity to do this. ]

# 分布式拒绝服务攻击：DDOS

分布式拒绝服务攻击 Distributed denial of service attacks.(more powerful, )

* **大量的攻击者** Vast number of attackers overwhelm victim.
* **耗尽系统资源** Exhaust system resources, or
* **耗尽带宽** Exhaust bandwidth

形成由入侵PC组成的僵尸网络 Assemble a botnet of compromised PC’s.

* 等待攻击命令作出反应 They respond to an attack command.
* Typically, a botnet compromise PCS is assembled, and they are they're sitting there doing nothing waiting for the command, when they get an attack and while they all at the same device. And so they provide considerable computational resources to attack the victim. 
* 僵尸网络是指一组计算机，这些计算机已被恶意软件感染并受到恶意参与者的控制

## 直接攻击：Direct Attacks

DDoS - 直接攻击

(僵尸网络？)最常见的攻击是使用**假的From地址**进行SYN泛洪 The most common attack is SYN flooding using a fake From address.

SYN用于请求建立连接 SYN asks for a connection.

* 被攻击者用SYN ACK响应From地址，并以半开放的连接等待 The victim responds to the From address with SYN ACK and waits with a half open connection.
* **因为From地址是假的，所以永远不会有回复** There will never be a reply because the From address is fake.
  * 被攻击者通常会等待并发送几个SYN-ACK包后才会放弃 The victim will usually wait and send several SYN-ACK packets before giving up.

:orange: 目的是用尽半开连接的数量 The aim is to exhaust the number of half open connections.

* 所以最终，受害者无法打开任何新的连接，因为等待这些假连接的回复。So eventually, the victim can't open any new connections genuine ones versus waiting for these fake ones to reply. 
* 一种预防方法是双方在开始打开连接之前交换cookie，这是一种比较快的操作 One preventative method is for the two parties to exchange cookies, a relatively fast operation, before starting to open a connection.
  * 在底层软件确认from地址是真的之前不要开放连接，而是通过交换cookie的方式 So, simple remedy is the underlying software not to open the connection until they're sure that the farm address is genuine, by exchanging cookies. So, simple remedy is the underlying software not to open the connection until they're sure that the farm address is genuine, by exchanging cookies.

## 反射式攻击：Reflector Attacks

:orange: **路由器和服务器等内部互联网节点被无辜地用来发起攻击** Internal internet nodes such as routers and servers are innocently used to launch the attack.

:orange: 需要响应的数据包被发送到路由器和其他互联网基础设施，并伪造了受害者的发件人地址 Packets requiring a response are sent to the routers and other internet infrastructure with a forged From address of the victim.

* 互联网和网络基础设施，从受害者那里得到他们认为的请求，他们回应 vast amounts of internet and network infrastructure, get what they think are requests from the victim, and they respond to. 
* 路由器向受害者发送合法响应 The routers sends legitimate responses to the victim.
* 被攻击者被正常数据包淹没 The victim is overwhelmed with normal packets.
  * 路由器通常被诱导发送SYN-ACK数据包 Routers are usually induced to send SYN-ACK packets.
  * 它们可以被识别为假数据包 They can be recognised as bogus.
* 目的是压倒带宽 The aim is to overwhelm the bandwidth.

攻击者发送出一系列的连线建立SYN封包给服务器，但来源IP地址伪造成受害主机的IP地址。
服务器回复一系列的SYN/ACK封包给受害主机。

## 抵御DDoS

### 检测&ISP过滤：Detect and Filter

受害者侦测到攻击 Victim detects an attack.

* **请求 ISP 对攻击源数据包进行过滤** Requests that the attacking source packets are filtered by the ISP.
  * 当网络超载时，受害人如何向ISP提供信息 How can the victim get information to the ISP when the network is overloaded?
    * 此时应打电话或使用其他网络 Phone call or alternative network.
* **存在假阴性，假阳性** There will be false positives and false negatives.
  * 正常的数据包存活率可能变得相当低 The normal packet survival rate can become quite low.
* **追踪 IP 是一个好主意，但并不总是有效** IP traceback is a good idea that doesn’t always work.
  * idea:**找到数据包来源，然后阻止这些设备** find where all these road packets are coming from, and then block those devices.
  * 数据包可能在防火墙前停止（ so limits how far you can go） Stopped at firewalls.
  * 反射器攻击来自合法来源（无法被阻止） Reflector attacks come from a legitimate source (which cannot being blocked).

:orange: **检测过滤 & IP跨越 和代理可以抵御 DDOS 攻击** IP hopping and proxies can defend against a DDOS attack

# 防火墙：Firewalls

**防火墙用于监测传入和传出的数据包，寻找威胁**。 Firewalls are used to monitor incoming and outgoing packets, looking for threats.

* 他们用规则来决定是否允许流量通过。 They used rules to decide if traffic is allowed to pass.
* 它们通常被放置在网关上，以控制对内部网络的访问。 They are usually placed on gateway machines to control access to an internal network.
* 可以在外部使用隧道模式 Can use tunnel mode outside.

什么是防火墙？什么是DMZ？

* 防火墙是一个允许或阻止数据包通过的门。  防御可以用2个或更多的防火墙来分层。防火墙之间的机器不太安全，形成一个DMZ。A firewall is a gate that either allows or prevents packets from passing.   The defence may be layered with 2 or more firewalls. The machines between the firewalls are less secure and form a DMZ.

## 例子-深度防御：Defence in Depth

我们提供了多个防火墙，如下面的例子  We provide more than one firewall, as in the example below.

![](/static/2021-04-18-18-20-38.png)

Web服务器位于DMZ（非军事区） The Web Server is in a DMZ (demilitarised zone).

* 一共两道防火墙
* **部分保护** It has partial protection.
  * web server is protected with first firewall, **only HTTP**, so conly accepts communication on port `80`
  * 攻击者需要利用http command进入
* **完全保护在第二道防火墙后面** Full protection is behind the second firewall.

## 防火墙-代理服务：Proxy Service

什么是代理服务，它能提供什么保护？

* 代理服务通过一台计算机路由所有网络请求。这使得访问控制和入侵检测更容易，它都在一个地方。它也更有效率，因为许多计算机请求的页面可以被缓存。A proxy service routes all web requests through one computer. This makes control of access and intrusion detection easier, it is all in the one place. It is also more efficient since pages that many computers request can be cached.

---

防火墙提供代理服务

**所有与互联网的连接都通过防火墙进行**。All connections to the Internet are routed through the firewall.

* **集中检查**是否允许访问。Checking for allowed access is centralised.
  * 检查访问是集中的，它是在一个地方完成的 checking for access is centralized, it's being done in one one place. 
  * 每个要连入内网的连接先集中检查
* **常见内容只缓存一次**。Common content is only cached once.
* **日志都在一个地方，便于监控**。Logs are all in one place for ease of monitoring

## 包过滤功能：Packet Filetering

如何过滤单个数据包？

* 它们可以根据来源（黑名单或白名单）、请求的服务（端口）和目的地进行过滤。内容也可以被检查，只要它没有被加密。They can be filtered on origin (blacklist or whitelist), service requested (port), destination. The content can also be inspected, provided it is not encrypted.

---

防火墙包过滤功能 - **根据一组规则对数据包进行检查，以确定数据包是否应该通过**。 The packets are checked against a set of rules to determine if a packet should pass.

* **检查请求的服务（使用端口号和机器**） Check on the requested service (using port number and machine)
  * 只允许某些端口（如与HTTP相关的端口）Only some ports are allowed (eg those related to HTTP)
* **检查数据包的来源** Check the packet source
  * 黑名单--禁止这些网站 Black list – ban these sites
    * Unwanted sites can be banned
  * 白名单--**只允许**这些网站。White list – only allow these sites.
    * This is a list of sites and you're only allowed to have received packets from these sites.
* **深度数据包(itself)检查** Deep packet inspection
  * **内容**检查 Check the content.
  * **隐私**局限性 Has privacy implications
    * 涉及隐私，严格规定什么时候允许深度检查 it has privacy implications if the actual content of each packet is inspected and so stringent rules on when this has allowed. 

## 状态检测-防火墙技术：Stateful Inspection

入侵检测/预防与数据包过滤有什么不同？How does intrusion detection / prevention differ from packet filtering?

* 这是基于数据包的收集和识别行为。This is based on a collection of packets and identifying behaviour.

---

:orange: **只需检查数据包的关键部分，并应用匹配算法**。 Just check key parts of the packet and apply a matching algorithm.

* Port and protocol （可以只检测）

:orange: **管理员可以定义规则** The administrator can define rules.

* 规则可以使用以前连接到网站的过去经验，以及当前连接的以前数据包。Rules can use past experience from previous connections to the site, as well as previous packets of the current connection.

### 规则例子：Example Rules

:orange: 一个规则可能会规定：**如果一个特定的IP地址读取的文件太多，那么这个IP地址就会被屏蔽** A rule may state that if too many files are being read by a specific IP address then that IP address is blocked.

* So, the site might decide that he doesn't want one person to systematically download everything but it's done everything when it's hosting, and if you need to block that IP address.

:orange: **可以屏蔽对某些域名的所有访问**  All access to certain domain names can be blocked.

* <font color="red">可以直接使用IP地址来绕过这个规则 (DNS poisoning)</font>This can be bypassed by using IP addresses directly.

:orange:**可以为某些协议创建规则** It is possible to create rules for certain protocols

* IP, TCP, UDP
* HTTP, FTP
* ICMP (Internet Control Message Protocol)
* SMTP (Simple Mail Transport Protocol)
* SNMP (Simple Network Management Protocol)
* Telnet (Remote execution)

---

:orange: 特定的单词和短语。 Specific words and phrases.

* **深度数据包检查将检查带有禁止词列表的词，并阻止任何包含这些词的数据包** Deep packet inspection will check words with a list of forbidden words and block any packet containing any of these words.

:orange: 有些端口，例如FTP (21)只能在一台内部计算机上使用 Some ports, eg FTP (21) will only be available on one internal machine.

:orange: 禁用源路由 Disable source routing.

* 互联网是无连接的，但一个数据包可能会指定它将采取的确切路线 The internet is connectionless, but a packet may specify the exact route that it will take.
* 源路由可以是伪造的，以使信息看起来来自一个可信的来源 This can be forged to make it appear that the information came from a trusted source.

---

Example rule

![](/static/2021-04-18-19-50-53.png)

# 新生代防火墙：Next Generation Firewalls (NGFW)

**这些提供了额外的功能** These provide additional functionality.

* Integrated intrusion prevention.综合入侵预防
* Application awareness and control.应用感知和控制
  * Are threat focussed.以威胁为中心

---

## 入侵检测：Intrusion Detection

基于签名的 "和 "基于异常的 "入侵检测之间有什么区别？

* 签名是已知的行为，可以在数据库中表示为规则。这是一种快速捕捉已知攻击的方法。异常检测试图从一般的规则而不是具体的行为模式来检测攻击。A signature is known behaviour that can be represented as rules in a database. It is a quick way of catching known attacks. Anomaly detection trys to detect an attack from general rules rather than specific behaviour patterns.

**网络流量，而不是单个数据包（防火墙是基于但数据包，而入侵检测，流量为整体），检查是否出现攻击**。 Network traffic, rather than single packets, is examined to see if an attack is underway.

* **可以基于签名** It can be signature based
  * 用已知攻击数据库检查 Check with a database of known attacks.
  * 通过攻击的 "签名 "来识别他们 Recognise them by their ‘signature’
  * 不会识别新攻击。Will not recognise a novel attack.
* **基于异常检测** Anomaly detection.
  * AI，通常是机器学习，寻找非标准的情况，并试图将其分类为攻击或只是正常的变化。 AI, typically machine learning, looks for non-standard situations and tries to classify them as an attack or just normal variation.
  * Of course this will be false positives and false negatives with sort of classification.
  * AI is not good enough to be fully trusted
* 在这两种情况下，如果怀疑受到攻击，系统管理员会得到通知。 In both cases, sysadmins are notified if an attack is suspected.

## 入侵预防：Intrusion Prevention

会像以前一样检测到入侵 Will detect an intrusion as before.

* 在这种情况下，防火墙可以采取行动，而不需要等待系统管理员来启动 In this case the firewall can take action without waiting for a sysadmin to initiate it
  * is trusted the AI more, so you take action without waiting for a sysadmin to initiate it

## 应用感知和控制: Application Awareness and Control

什么是 "应用意识和控制"？

* 了解受到攻击的资产的价值，因此花费适当的金额来保护它们。Knowing the value of assets under attack and so spending an appropriate amount to protect them.

---

**以威胁为中心** Threat focussed.。

* 了解哪些资产的风险最大，并将其添加到背景信息中。Know which assets are most at risk and add this to context information.
* **取决于潜在的损害,决定操作** Deciding on what to do depends on the potential damage.
  * 如果不重要，忽略攻击，，
  * 如果为重要资产，需要严格保护，可能要做预防，然后在以后告诉系统管理员，你停止了攻击。如果是真正重要的资产，你宁可拒绝正常的服务，也不允许攻击者发生 important asset, more stringent your protection it's. So you might want to do prevention, and then tell the sysadmin later on that you stopped in attack. If it's a really important asset, and you'd rather deny normal service, dont allow an attacker to take place.
* **清理 Cleanup after an event.**
* **追溯性安全** Retrospective security.
  * 追溯最近发生的事件，并警惕它再次发生 Be aware of a recent event and be on the lookout for it to happen again.
  * 有一个最近事件的列表，等待同样的攻击发生不止一次 have a list of recent events, waiting for the same attack to happen more than once. 

# 虚拟专用网：Virtual Private Network (VPN)

VPN使用隧道模式将数据包从用户的计算机传送到VPN供应商的计算机，在那里，数据包被释放并被送上路。观察者将只看到与VPN机器的通信，而不是被联系的实际网站。A VPN uses tunnel mode to transport a packet from the user’s computer to the VPN provider’s computer, where it is let loose and sent on its way. An observer will just see the communication with the VPN machine and not the actual sites being contacted

---

VPN是保护提供商提供网络安全的另一种方式。VPNs are another way of protecting providers providing security with networks.

它采用隧道模式IPSEC。 It uses tunnel mode IPSEC.

* **用户的计算机将运行VPN应用程序，它将整个IP数据包，包括报头，作为加密的有效载荷放在一个外包中**The user’s computer will run the VPN application, which puts the whole of the IP packet, including the header, as an encrypted payload in an outer packet.

**外包被送到VPN提供商的机子上（destination is that you want to connect to）** The outer packet is delivered to the VPN provider’s computer.

* 公司网络的网关 Gateway to a company network.
* 另一个国家的主机 Computer in another country.
  * may want to use VPN to bypass the censorship

Results travel in reverse.

你看起来是在使用VPN提供商的机子 You appear to be using the VPN provider’s computer.

* 你可以看起来是在另一个国家，以绕过审查 You can appear to be in a different country to bypass censorship.

# Attacks:Heartbleed

**这是一种基于实现TLS和SSL的通用库OpenSSL中一个设计不良的功能的攻击** This is an attack based on a poorly designed feature in a common library to implement TLS and SSL, OpenSSL.

* 这个库有一个ping命令，用于查看服务器是否可达，存活 This library has a ping command for seeing if a server is alive.
* The client sends a 'are you alive' message to a server.
* **发送部分RAM的转储来证明存货性** The server replies by sending a dump of a portion of RAM to prove that it is alive.
  * 这个RAM转储通常会包含密码和其他敏感信息，其形式容易识别。 This dump will typically contain passwords and other sensitive information in an easily recognisable form.
  * 这个功能现在已经被删除，但旧的实现将不得不升级。 This feature has now been removed, but older implementations will have to be upgraded.

# 基于网络的应用程序的安全性:Security of a Web Based Application

我们将研究一个典型的四级网络应用的安全威胁和可能的对策。这四个层次是

* o 客户端
* o 网络服务器
* o 应用服务器
* o 数据库和其他专业设施。

每个层次都有自己的安全问题和解决方案，层次之间的三种通信方式也是如此。

我们将依次讨论每个领域，考虑威胁和对策。

We will look at the security threats and possible counter-measures of a typical 4 level web based application. The four levels are:

* o	Client
* o	Web Server
* o	Application Server
* o	Database and other specialist facilities.

Each level has its own security problems and solutions, as do the three types of communications between the levels.

	We will discuss each area in turn, considering threats and countermeasures.

## 客户端威胁 Threats : Client

客户机不在我们的控制范围之内。The client machine is outside our control.

* 我们希望客户端使用标准的浏览器，但是如果客户端决定攻击我们，他们可以自己生成手工制作的 HTTP 请求。We expect the client to use a standard browser, but oponents can generate their own hand crafted HTTP requests if they decide to attack us.

未能登出 Failure to logout

* 这将允许访问与用户相关的信息。This would allow access to user related information.

虽然严格来说不是我们的问题，但是我们可以采取措施限制用户的错误造成的任何损害。While not strictly our problem, we can take measures to limit any damage from this mistake by the user.

Browser Cache

* 浏览器可能会缓存网页或 cookies，以防再次需要。The browser may well cache pages or cookies in case they are needed again.
* 这些缓存的页面将位于用户的文件系统中，其他人可以访问它们These cached pages will be in the user's file system, where someone else can access them.

可下载资料 Downloadable Information

* 任何在客户机上运行的代码都可以看到。Any code that runs on the client can be seen.
* 这可以帮助我们深入了解我们系统的内部运作。This can provide insight into the internal workings of our systems.
* 这对知识产权有影响。This has implications for intellectual property rights.
* ID 号码也可用来猜测内部网页的 url、请求的结构等等 ID numbers may also be used to guess at the URLs of internal web pages, the structure of requests, and so on

## 反措施 : 客户 Countermeasures : Client

删除客户端代码中的含义Remove Meaning from Client Side Code 

* C1 客户端代码不应包含任何注释，也应尽可能地模糊化。Client side code should not contain any comments and should also be obfuscated as much as possible.
* C2 客户端代码中不应该出现业务逻辑，它应该只处理表现问题。No business logic should appear in client side code, which should only deal with presentation issues.

避免缓存 Avoid Caching

* C3 将页面过期设置为即时，这样浏览器就不会对页面进行缓存。不幸的是，后退按钮将不再带我们回到这些页面。
* C4 不要使用永久性的cookies。暂时性的内存cookie可以用来记录会话信息，但仍然可以被任何能够访问内存的人看到。

字段验证 Field Validation

* C5 对来自客户端的所有输入进行长度验证，以防止缓冲区溢出攻击。Length validation of all input from the client, to prevent buffer overflow attacks.
* C6 对来自客户端的所有数据进行内容验证，以确保其格式正确。这也将处理特殊字符，如&和<。Content validation of all data from client, to make sure that it is in the correct format. This would also deal with special characters such as & and <.
* C7 对所有往返信息使用消息摘要，如ID号码，以检测篡改和重放攻击。Use message digests on all round trip information such as ID numbers to detect tampering and replay attacks

避免在清晰的信息中Avoid In Clear Information 

* C8 不要使用HTTP GET命令，用POST代替。C9 使用TLS对HTTP主体进行加密。Do not use HTTP GET command, use POST instead。Encrypt HTTP body using TLS.
* C10 参考号码只在会话期间有效，不包含任何有意义的信息。Reference numbers are only valid during the session, and do not contain any meaningful information.
* C11 隐藏字段可用于记录会话信息，但它们也可能存在安全弱点。 Hidden fields can be used to record session information, but they can also have security weaknesses.
  - 攻击者可以保存一个页面，改变一个隐藏字段的值，然后重新播放该页面。An attacker can save a page, change a hidden field value and then replay that page.
  - 不要使用隐藏字段 Do not use hidden fields

## 威胁：网络服务器-Threats : Web Server

网络服务器在我们的控制之下，但仍有许多攻击需要我们去防范。The web server is under our control, but there are still many attacks that we need to protect against.

**利用服务器软件的漏洞** Exploiting Vulnerabilities of Server Software

* 像IIS这样的服务器有许多众所周知的漏洞，因此我们需要确保所有的补丁都是最新的。Servers such as IIS have a number of well known vulnerabilities, and so we need to make sure that all the patches are up to date.
* 新的漏洞不时出现，因此我们需要随时了解文献和相关网站的最新情况。New vulnerabilities come to light from time to time, and so we need to keep up to date with the literature and relevant web sites.

敏感信息的缓存 Caching of Sensitive Information

* 这在服务器上问题不大，但与会话相关的信息仍可能被缓存。This is less of a problem on the server, but session related information might still be cached.

拒绝服务攻击 Denial of Service Attacks

冒充用户 Impersonation of Users

重放攻击 Replay Attack

* 例如，重放可能是一个登录请求或付款请求。The replay may be a login request or a payment request, for example.

主持内向攻击 Hosting an Onward Attack

* 内部域的结构可以被发现，使敌人可以攻击它。The structure of the internal domain can be discovered, allowing an enemy to attack it.

跨进程污染 Cross Process Contamination

* 目的地为一个进程的信息可能被发送到另一个进程。Information destined for one process may be sent to another.
* 这可能发生在我们使用组件和网络服务时。This can happen when we use components and web services.

#### 反措施

C12	No Direct Access to Business Data. This access is achieved at the process server level.
C12无法直接访问业务数据。此访问是在流程服务器级别实现的。

This is the reason for this additional layer.
这就是这个额外层的原因。

C13	Session Timeout.
C13会话超时。

Protect against users who leave while still logged in.
防止用户在登录时离开。

C14	Null Memory After Use.
使用后 C14空记忆体。

Memory that has not been nulled is similar to a cache.
未为空的内存类似于缓存

C15	Session Management.
C15会话管理。

oCorrectly identify which session any client activity belongs to.
正确识别任何客户端活动属于哪个会话。

	DOS countermeasures have already been covered.
DOS 反制措施已经包括在内

## 进程服务器-Threats : Process Server

The process server processes requests from the web server.
进程服务器处理来自 web 服务器的请求。

* It can be attacked from a compromised web server or another machine masquerading as a web server. * 它可以被攻击从一个受损的网络服务器或其他机器伪装成一个网络服务器。
* We thus need to defend in depth. * 因此，我们需要进行深度防御。

Illegitimate Request 非法请求

* If the format of a valid request is known and authentication has been bypassed. 如果已知有效请求的格式，并且已绕过身份验证

Denial of Service 分布式拒绝服务攻击

* This would typically be from a single web server machine, and so not as series as a DDOS attack. 这通常来自单一的 web 服务器机器，因此不像 DDOS 攻击那样是一系列的

不合法的部件 Illegitimate Components

* 系统的某些部分可能已经被替换，要么是被成功的攻击，要么是被内部人攻击

---

![](/static/2021-05-05-20-52-21.png)

![](/static/2021-05-05-20-52-53.png)

![](/static/2021-05-05-20-53-02.png)

![](/static/2021-05-05-20-53-59.png)

## 数据库 & 其他服务 - Threats : Database and Other Services

The database is where we keep all of our persistent information. 数据库是我们保存所有持久信息的地方。

*	A lot of damage can be done if it is compromised. 如果它被破坏了，会造成很大的伤害。

Illegitimate Requests 非法请求

*	数据库将响应来自任何具有适当访问权限的机器的任何格式良好的查询。 The database will respond to any well formed query from any machine with the appropriate access rights.

来自组织内部的内部攻击。Internal Attack from someone inside our organisation.

* Database tools such as Query Analyser and Enterprise Manager may be used to compromise data 可以使用诸如 queryanalyser 和 enterprisemanager 之类的数据库工具来妥协数据
*	Other parts of the system may also be vulnerable to an internal attack, but an attack on the database would be especially bad. 系统的其他部分也可能容易受到内部攻击，但是对数据库的攻击尤其糟糕

### 反措施

![](/static/2021-05-05-21-19-55.png)

![](/static/2021-05-05-21-20-07.png)

## 威胁: 计算机之间的连接 - Threats : Connections Between Computers

客户机、 web 服务器、进程服务器和数据库服务器都是连接的，而且连接都是脆弱的。	The client machines, web servers, process servers and database servers are all connected, and the connections are all vulnerable

向数据包分析员披露 	Disclosure to Packet Analysers

* 数据包分析器放置在通信电缆旁边，记录所有来回传递的数据包，将它们重新组装成流 Packet analysers are placed next to communication cables and record all of the packets passing to and fro, reassembling them into streams.

## 安全矩阵： security matrix

什么是安全矩阵，为什么它是有用的？ What is a security matrix and why is it useful

它列出了所有的威胁和建议的反措施。它确保了没有任何东西被遗忘。It lists all threats and proposed countermeasures. It makes sure nothing is forgotten.

## 为什么4层架构对网站有用？Why is a 4 tier architecture useful for web sites?

客户机在我们的控制之外，可以发起攻击。网络服务器是第一道防线，所以最容易被攻击。由于这个原因，它只是处理客户端，不包含任何业务逻辑和活动。这些都是由进程服务器处理的。数据库持有所有有价值的数据，所以在它自己的机器上。 The client machine is outside our control and can launch attacks. The web server is the first line of defence and so most vulnerable to being compromised. For this reason it just handles the clients and does not contain any business logic and activities. These are handled by the process server. The data base hold all the valuable data and so is on its own machine.

## 客户机上有哪些问题，如何缓解这些问题？

3.	客户机上有哪些问题，如何缓解这些问题？What are some of the problems on the client machine, and how can they be mitigated?

发送给客户端的任何信息和代码都可以被攻击者看到。这可能显示系统是如何工作的。它不应该包含任何业务逻辑。往返信息不应透露序列号，并应通过消息摘要进行验证。

用户可能会忘记注销。我们将操作一个超时，并要求重新认证。

攻击者可能会尝试SQL注入或XSS。我们将验证所有的用户输入。

Any information and code that is sent to the client can be seen by an attacker. This might show how the system works. It should not contain any business logic. Round trip information should not reveal sequence numbers and should be verified by message digests.

The user might forget to logout. We will operate a timeout and require re- authentication.

An attacker might try SQL injecting or XSS. We will validate all user input.

## 网络服务器机器上有哪些问题，如何缓解这些问题？为什么它在DMZ中？

网络服务器机器上有哪些问题，如何缓解这些问题？为什么它在DMZ中？
What are some of the problems on the web server machine, and how can they be mitigated? Why is it in a DMZ?

重放攻击，通过会话管理来防止。Replay attacks, which are prevented by session management.

通过使用无状态组件可以防止跨进程污染。Cross process contamination is prevented by using stateless components.

## 在系统中，用户在哪里被认证？

在系统中，用户在哪里被认证？Where are users authenticated in the system?

在进程服务器上。On the process server.

## 如何处理电子邮件？为什么选择这种解决方案？

如何处理电子邮件？为什么选择这种解决方案？How is email handled? Why was this solution chosen?

我们不能对发送给用户的电子邮件进行加密，因此我们必须实现我们自己的电子邮件或信息传递系统。We cannot encrypt email that we send to a user and so we have to implement our own email or messaging system.

---

一个恶意的内部人员如何攻击这个系统？How can a malicious insider attack this system?

他们可以使用数据库检查和管理工具。我们应该启用数据库日志，并定期监测它们。They can use database inspection and management tools. We should enable database logs and monitor them regularly.

他们可以在初始安装和配置时安装被破坏的组件。我们将使用组件证书来防止这一点。They can install compromised components during initial installation and configuration. We will use component certificates to prevent this.
