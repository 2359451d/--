# L4 - Improving Secure Connection Establishement

* TLS 1.3的一些限制，这些限制限制了它的安全性，并降低了它的连接建立速度。limitations  of TLS 1.3 that constrain its security,  and that slow down its connection establishment.
* 正在开发的解决这些问题的改进措施。improvements being developed  to address these problems.
  * 既要改进TLS 1.3与TCP配合使用的情况，Both to improve  TLS 1.3 when used with TCP,
  * 又要用一种新的传输协议QUIC完全取代TCP和TLS，它具有更快的连接建立速度和内置的安全性。and to entirely replace TCP and TLS  with a new transport protocol, QUIC,  that has faster connection establishment and in-built  security.

* [L4 - Improving Secure Connection Establishement](#l4---improving-secure-connection-establishement)
* [TLS局限性：Limitations of TLS v1.3](#tls局限性limitations-of-tls-v13)
  * [连接建立效率：TLS v1.3 Connection Establishment Performance](#连接建立效率tls-v13-connection-establishment-performance)
  * [0-RTT连接建立：0-RTT Connection Reestablishement](#0-rtt连接建立0-rtt-connection-reestablishement)
    * [TLS握手的作用：role of TLS Handshake](#tls握手的作用role-of-tls-handshake)
    * [PreKey如何加密初始数据：How to encrypt initial data](#prekey如何加密初始数据how-to-encrypt-initial-data)
    * [0-RTT潜在风险：Potential Risks of 0-RTT mode](#0-rtt潜在风险potential-risks-of-0-rtt-mode)
  * [TLS其他局限-元数据泄露：TLSv1.3 Metadata Lakage](#tls其他局限-元数据泄露tlsv13-metadata-lakage)
  * [协议僵化问题：TLSv1.3 Protocol Ossification](#协议僵化问题tlsv13-protocol-ossification)
    * [TLS设计的改变&改进局限性](#tls设计的改变改进局限性)
    * [如何避免协议僵化：How to Avoid Protocol Ossification](#如何避免协议僵化how-to-avoid-protocol-ossification)
      * [GREASE扩展机制:生成随机扩展和持续扩展性](#grease扩展机制生成随机扩展和持续扩展性)
* [QUIC传输协议：QUIC Transport Protocol](#quic传输协议quic-transport-protocol)
  * [背景：History of QUIC](#背景history-of-quic)
  * [定义概括：QUIC Overview](#定义概括quic-overview)
    * [关键优点：重叠连接建立&安全握手](#关键优点重叠连接建立安全握手)
  * [QUIC包，头，帧特点：QUIC Packets, Headers, and Frames](#quic包头帧特点quic-packets-headers-and-frames)
  * [QUIC头：QUIC Headers](#quic头quic-headers)
    * [Long Header Packet](#long-header-packet)
    * [Short Header Packet](#short-header-packet)
  * [QUIC帧-充当TCP头选项：QUIC Frames](#quic帧-充当tcp头选项quic-frames)
  * [QUIC连接建立&数据传输：QUIC Connection Establishment and Data Transfer](#quic连接建立数据传输quic-connection-establishment-and-data-transfer)
    * [连接建立过程：QUIC Connection Establishment](#连接建立过程quic-connection-establishment)
    * [初始包格式：QUIC Initial Packet Format](#初始包格式quic-initial-packet-format)
    * [Retry包](#retry包)
    * [握手包：Handshake Packets](#握手包handshake-packets)
  * [0-RTT](#0-rtt)
  * [0-RTT TLS vs QUIC](#0-rtt-tls-vs-quic)
  * [握手结束（开始数据传输）-短头包: Data Transfer, Streams, Reliability](#握手结束开始数据传输-短头包-data-transfer-streams-reliability)
    * [ACK帧确认](#ack帧确认)
    * [STREAM Frame帧 & 行首阻塞问题（QUIC流复用）](#stream-frame帧--行首阻塞问题quic流复用)
* [QUIC避免协议僵化：：Avoid Protocol Ossification](#quic避免协议僵化avoid-protocol-ossification)
  * [QUIC over UDP 好处](#quic-over-udp-好处)
  * [僵化例子2](#僵化例子2)
  * [协议僵化其他影响： Protocol Ossification Other Influence](#协议僵化其他影响-protocol-ossification-other-influence)
  * [避免QUIC僵化：Avoiding Ossification in QUIC](#避免quic僵化avoiding-ossification-in-quic)
    * [方法1-QUIC不变特性：QUIC Invariants](#方法1-quic不变特性quic-invariants)
    * [普遍加密-避免僵化：Avoiding Ossification via Pervasive Encryption](#普遍加密-避免僵化avoiding-ossification-via-pervasive-encryption)
    * [GREASE机制避免僵化：Avoiding Ossification via GREASE](#grease机制避免僵化avoiding-ossification-via-grease)
* [QUIC优点 & 开销：QUIC Benefits and Costs](#quic优点--开销quic-benefits-and-costs)

# TLS局限性：Limitations of TLS v1.3

![](/static/2021-02-12-15-44-38.png)

重新审视**1.建立缓慢连接的问题，并描述围绕2.元数据泄露和3.协议僵化的新问题**。讨论TLS是如何扩展来解决这些问题的revisit the problem of slow connection  establishment, and describe new issues around metadata  leakage and protocol ossification. And I’ll discuss  how TLS is being extended to solve  these problems.

---

![](/static/2021-02-12-15-47-47.png)

TLS 1.3，已经取得了巨大的成功。TLS  1.3 in particular, has been a great  success.

* **多年来，该协议已经成功地进行了扩展和更新，以跟上安全挑战的步伐**。The protocol has been successfully extended and  updated over many years, to keep abreast  with security challenges.  
  * **它增加了新的功能，以满足新的应用需求**，It’s added new features to meet the  needs of new applications,
    * 如数据报TLS用于视频会议通话的安全。such as datagram  TLS for security of video conferencing calls.
* TLS 1.3是TLS的最新版本，在2018年实现了标准化。 **与上一个版本TLS 1.2相比，它在安全性和性能上都有了一些显著的提升**。TLS 1.3 is the most recent version  of TLS, and was standardised in 2018.
  * 删除了对旧的和不太安全的加密和密钥交换算法的支持。Removed support for older and less secure encryption and key exchange algorithms
  * 删除了对已证明难以正确实施的安全算法的支持。Removed support for secure algorithms that have proven difficult to implement correctly
* 而且它的**性能良好，应用广泛(改进第一次握手 & 0-RTT模式性能** Some performance improvements to the initial handshake and with 0-RTT mode)。And it has good performance and is  widely used.
* **而且它简化了协议的设计**。And it simplified the design  of the protocol.

:orange: TLS v1.3局限性 TLS v1.3 has some limitations that are hard to fix

* **连接建立相对慢** Connection establishment is still relatively slow
* **连接建立泄露潜在敏感元数据** Connection establishment leaks potentially sensitive metadata
* **由中间盒干扰造成的协议僵化** The protocol is ossified due to middlebox interference
  * 由于实现和中间盒质量不高，难以扩展。due to  poor quality implementation and middleboxes, and difficult  to extend.

## 连接建立效率：TLS v1.3 Connection Establishment Performance

![](/static/2021-02-12-16-00-20.png)

> TLS是一个安全协议，而不是一个传输协议，需要在现有的传输层连接中运行。 在实践中，这意味着TLS通常在TCP连接内运行。TLS is a security protocol, not a  transport protocol, and needs to be run  inside an existing transport-layer connection.  In practise, this means that TLS usually  runs inside a TCP connection.

:orange:TCP连接建立 - `SYN-SYNACK-ACK`

* 当连接到一个支持TLS的服务器时，客户端必须首先建立一个TCP连接到服务器。这将通过通常的TCP连接建立握手进行。 客户端发送一个SYN位设置的TCP段，表示连接的开始。服务器用一个SYN ACK响应。而客户端用一个ACK包完成握手。When connecting to a TLS-enabled server,  a client must first establish a TCP  connection to the server. This proceeds with  the usual TCP connection establishment handshake.  The client sends a TCP segment with  the SYN bit set, indicating the start  of the connection. The server responds with  a SYN ACK. And the client completes  the handshake with an ACK packet.
* 这种握手需要一个半来回才能完成，客户端在第一个来回之后就可以发送数据。 也就是说，**客户端在握手中发送完最后一个ACK包后，可以立即发送数据包，而不需要等待服务器的响应**。This handshake takes one-and-a-half round trips to  complete, with the client being able to  send data after the first round trip.  That is, the client can send a  data packet immediately after it sends the  final ACK packet in the handshake,  without waiting for a response from the  server.

:orange: TLS连接建立

* TLS `ClientHello` sent with final ACK
  * 这是TLS握手的第一部分。它表明了**客户端支持的TLS版本**，并开始了加密和认证密钥的协商  This is the first part of the  TLS handshake. It indicates the version of  TLS the client supports, and starts the  negotiation of the encryption and authentication keys.
* TLS `ServerHello` sent in response
  * 包含建立安全连接所需的信息 containing the information needed to establish the secure connection.
* TLS `Finished` message concludes, and carries **initial secure data record**
* 确认安全连接,服务器选取客户端支持的算法的子集？confirming the secure connection

:candy: 在开始连接建立后，整整两个来回，客户端才可以向服务器发送安全请求，以及TLS Finished消息。two full  round trip times after starting the connection  establishment, that the client can send a  secure request to the server, along with  the TLS Finished message.

* 而最早能收到响应的时间是一个来回之后(finished)，**总共是启动连接建立后的三个来回** And the earliest that a response can  be received is one round trip later,  a total of three round trips after  starting the connection setup.

---

:orange: 3RTT 三个来回时间的影响

![](/static/2021-02-12-16-37-54.png)

* 右表显示了一些从格拉斯哥出发的典型往返时间。 三次往返可以很容易地在250到500毫秒的范围内。table on the right shows some  typical round trip times from Glasgow.  Three round trips can easily be somewhere  in the 250 to 500 millisecond range.  
  * 测量显示，一个网页平均要获取大约1.7兆字节的数据，包括大约69个HTTP请求，使用大约15个TCP连接。Measurements show that an average web page  fetches about 1.7 megabytes of data,  comprising around 69 HTTP requests, and using  around 15 TCP connections.
* 而这些连接中约有80%使用TLS。 **当然，有些TCP和TLS连接是并行运行的，但不是所有的连接**。And about 80% of those connections use  TLS.  Some of the TCP and TLS connections  run in parallel, certainly, but not all  of them.

:orange: <font color="deeppink">而加载一个典型的网页所花费的大部分时间其实都是在等待TCP和TLS连接建立握手</font>And most of the time taken to  load a typical web page is actually  spent waiting for TCP and TLS connection  establishment handshakes.

* 如果我们想让网页加载速度更快，减少连接建立时间是一个非常有效的方法。If we want to make web pages  load faster, reducing the connection establishment time  is a very effective way of doing so.

:orange: 如何加快TLS连接建立时间？how to speed up TLS connection setup

* 0-RTT连接复用技术 0-RTT connection re-establishment
  * 这可以让我们在第一次连接到服务器时保存安全参数，并在以后重复使用。 如果我们从同一个网站获取多个页面，它可以提高性能 This lets us save the security parameters  the first time we connect to a  server, and re-use them in future.  It improves performance if we fetch more  than one page from the same website.
* **并行TCP & TLS握手** concurrent TCP & TLS handshake
  * 目前，我们打开一个TCP连接，在客户端发送数据之前需要一个来回，然后协商TLS安全参数，需要另一个来回。我们可以**通过重叠这两次握手来加快这个过程**。At present, we open a TCP connection,  which takes one round trip before the  client can send data, and then negotiate  the TLS security parameters, taking another round  trip. We could speed up the process  by overlapping these two handshakes. 
    * 例如，我们可以在发送TCP SYN包的同时发送TLS ClientHello消息，而TLS ServerHello消息可以在SYN-ACK包中发送。 这样就可以让整个连接的建立握手，TCP和TLS，一次完成。 For example, we could send the TLS   ClientHello message along with the TCP SYN  packet, and the TLS ServerHello message could  be sent in the SYN-ACK packet.  This would let the whole connection establishment  handshake, TCP and TLS, complete in one  go.

:candy: 现在，用TCP来做并行需要广泛的操作系统变化，以及对世界上所有的防火墙进行更新，所以这不太可能发生 Now, doing this with TCP would require  widespread operating system changes, and updates to  all the firewalls in the world,  and so it isn’t likely to happen

* 但QUIC传输协议支持这种优化

## 0-RTT连接建立：0-RTT Connection Reestablishement

![](/static/2021-02-12-16-50-29.png)

0-RTT连接重新建立技术建立在观察到的基础上， The 0-RTT connection re-establishment technique builds on  the observation

* 即通常连接到一个**先前已知的TLS服务器**。 我们经常从一个网站获取多个网页。that it’s common to connect  to a previously known TLS server.  
* 或者每天访问同一个网站。或者连接到同一个消息、聊天或视频会议服务器。We frequently fetch more than one web  page from a site. Or visit the  same sites every day. Or connect to  the same messages, chat, or video conferencing  server.
* **当然，在这种情况下，应该可以捷径建立连接，重用我们第一次交换的信息来加快速度** Surely it should be possible to shortcut  the connection establishment in such cases,  and reuse information we exchanged the first  time to speed things up

:orange: 涉及的三个问题（需要知道的） 3 things need to understand

* **TLS握手的作用是什么，如何协商安全参数**？what the role of the TLS  handshake, and how are the security parameters  negotiated?
* 怎样加密**复用连接上的初始数据** how can we encrypt initial data  on re-establishing a secure connection?
* **捷径密钥交换的潜在风险是什么**？ what are the potential risks  in short-cutting the key exchange?

### TLS握手的作用：role of TLS Handshake

![](/static/2021-02-12-16-55-41.png)

1.首先是使用公钥加密技术安全地建立一个**会话密钥，然后使用该会话密钥对通过TLS连接发送的数据进行加密**。

* 然后，**该会话密钥被用于使用【对称加密算法】对通过TLS连接发送的数据进行加密**。That session key is then used to  encrypt the data being sent over the TLS connection, using a symmetric encryption algorithm.  
* **在TLS握手过程中交换的ClientHello和ServerHello消息被用来导出这个会话密钥**。The ClientHello and ServerHello messages, exchanged in the TLS handshake, are used to derive this session key.
  * 在TLS 1.3中，采用历时椭圆曲线Diffie-Hellman密钥交换算法--ECDHE来生成会话密钥。In TLS 1.3, this is done using  the ephemeral elliptic curve Diffie-Hellman key exchange  algorithm – ECDHE.
* **TLS客户端和服务器在ClientHello和ServerHello消息中交换他们的公钥【公号】，以及一个随机选择的值**。
  * **在TLS握手中交换的公钥和随机值，以及只有每个端点知道的私钥【私号**】，被用来推导会话密钥，使用**ECDHE算法**。The public keys and random values exchanged  in the TLS handshake, and the private keys known only to each of the endpoint, are used to derive the session key, using the elliptic curve Diffie-Hellman algorithm.  
* 但关键的一点是，<font color="deeppink">每一个TLS连接，即使是在同一个客户端和服务器之间，都会产生一个【唯一的会话密钥】。 这提供了一个被称为前向保密的属性</font>The key point, though, is that every  TLS connection, even if between the same  client and server, generates a unique session  key. This provides a property known as **forward secrecy**.
  * 如果一个TLS连接的加密密钥以某种方式泄露给公众，那么它就会暴露通过该连接发送的数据，但**它并不能帮助攻击者破解这些端点之间任何其他连接的加密**。If the encryption key for one  TLS connection somehow leaks to the public,  then it exposes the data sent over  that connection, but it doesn’t help an  attacker break the encryption on any other  connections between those endpoints.

2.TLS握手的另一个作用是**检索服务器的证书，该证书将有来自某个证书颁发机构的数字签名，这是公钥基础设施的一部分，允许客户端确认服务器的身份**。The other role of the TLS handshake  is to retrieve the server’s certificate, that will have a digital signature from  some Certificate Authority, part of the public  key infrastructure, allowing the client to confirm  the identity of the server.

* `ServerHello`信息包含**CA证书**

### PreKey如何加密初始数据：How to encrypt initial data

![](/static/2021-02-12-17-09-34.png)

**【ECDHE算法需要从ClientHello和ServerHello消息中获取信息，以得出会话密钥】**。在握手完成之前，它不能被使用。 这就是为什么TLS通常需要一个完整的往返来协商会话密钥。The ECDHE algorithm requires information from both  the ClientHello and the ServerHello messages to  derive a session key. It can’t be  used until the handshake has completed.  This is why TLS usually takes a  complete round trip to negotiate the session  keys.

* **在交换了ClientHello和ServerHello消息之后才能开始对数据进行加密，因为这些消息是用来推导加密密钥的**。【会话密钥-用于加密数据】。 You can’t start encrypting the data until  after the ClientHello and ServerHello messages have  been exchanged, since those messages are used  to derive the encryption key.【session key used to encrypt the data】
* **那么如何加密初始数据**？ --- 0-RTT连接复用通过在第一个TLS会话中共享一个密钥来解决这个问题，以便再下一个TLS会话中复用 0-RTT Connection Reestablishment gets around this by  sharing a key in one TLS session,  to be used in the next.

:orange: 0-RTT具体建立 & 会话密钥 & 共享密钥

* 当一个特定的客户机**第一次**连接到TLS服务器时，<font color="deeppink">服务器会在发送`ServerHello`消息的同时发送两个额外的信息。 它会发送一个`PreSharedKey`和一个`SessionTicket`</font> The first time a particular client makes  a connection to a TLS server,  the server sends it two additional bits  of information along with its ServerHello message.  It sends a PreSharedKey and a SessionTicket.  
  * **PreSharedKey是一个加密密钥，客户端可以用它来加密发送给服务器的数据【encrypt initial data sent from `ClientHello` in next connection】** The PreSharedKey is an encryption key,  that the client can use to encrypt  data it sends to the server.  
  * **而`SessionTicket`则是告诉服务器，哪个公钥是给客户端的【用于后续推导本次会话密钥】** And the SessionTicket tells the server which public key was given to the client.
  * 这些都是通过加密的TLS连接发送的，所以只有客户端和服务器知道  These are sent over the encrypted TLS  connection, so they’re only known to the  client and the server.
* **之后复用连接-当后续客户机重新连接到同一台服务器时**，When it later reconnects to the same  server,
  * **客户端可以使用这些信息（上一个连接共享密钥 & 本次session ticket公钥）与ClientHello消息一起发送加密数据**。 在这种情况下，客户端建立一个TCP连接到服务器。然后，它以通常的方式向服务器发送TLS ClientHello消息。 the client can use this information  to send encrypted data along with the  ClientHello message.  In this case, the client establishes a  TCP connection to the server. Then,  it sends a TLS ClientHello message to  the server in the usual way. Client sends SessionTicket, data encrypted using corresponding PreSharedKey, along with ClientHello
  * 收到客户端的消息时，服务器会像正常情况下一样，**以ServerHello来回应，【并使用SessionTicket来查找它的PreSharedKey副本，并解密客户端发送的数据client initial data**】【client use session key to decrypt server's response data】**On receiving such a message, the server  responds with a ServerHello as normal.  It also uses the SessionTicket to lookup  its copy of the PreSharedKey, and decrypt  the data sent from the client.
  * **如果服务器要回复客户端，它就会将它的响应和ServerHello消息一起发送。由于服务器已经收到了ClientHello消息【获取公号】，并且知道它在ServerHello消息中发送的内容，所以它可以推导出一个短暂的【会话密钥，并使用它来加密响应 using session key to encrypt server response data】** If the server wants to reply to  the client, it sends its response along  with the ServerHello message. Since the server  has received the ClientHello message, and knows  what it sent in its ServerHello message,  it can derive an ephemeral session key  and use it to encrypt the response.
  * **同样，客户端也可以解密该响应，因为它将跟随ServerHello提供完成密钥交换所需的信息，获取相同的会话密钥用于解密服务器响应【client using session key to decrypt the server's response data】**  Similarly, the client can decrypt that response,  since it will follow ServerHello that provides  the information needed to complete the key exchange and to derive the same session key using server's public number & random value and therefore to decrypt the server's response data

:orange: 总结

![](/static/2021-02-12-17-56-32.png)

* 在一个TLS会话中发送PreSharedKey，并使用它来加密下一个会话的初始消息中发送的数据，这个过程被称为0-RTT连接重建。
  * 它提供了一种方法，使客户端在TLS连接打开后发送的第一个数据包中向服务器发送加密数据。
  * 也就是说，它允许数据的发送不需要额外的往返。
* 通常的TLS握手也会进行，ClientHello和ServerHello消息用于导出会话密钥
* PreSharedKey只用于加密连接中从客户端发送到服务器的第一条消息。

### 0-RTT潜在风险：Potential Risks of 0-RTT mode

0-RTT连接重建可以让数据立即发送，减少TLS连接建立的延迟。 但是。 它带来了两个风险。

![](/static/2021-02-12-18-01-23.png)

1.和ClientHello一起发送的数据，**使用PreSharedKey加密的数据不是转发秘密**。使用PreSharedKey将两个TLS连接在一起。发送的共享密钥安全地通过第一个连接的短暂会话密钥进行加密，并用于加密下一个连接的第一个消息中发送的数据。The first is that data sent along  with the ClientHello, **and encrypted using a  PreSharedKey, is not forward secret**. The use of a PreSharedKey links two TLS connections  together. The key sent is securely over one connection, encrypted using the ephemeral session key of that session, and is used to encrypt the data sent in the first message of the next connection.

* <font color="deeppink">如果由于某种原因，第一个会话的短暂会话密钥被暴露，破坏了该会话的加密，那么可以用它来提取PreSharedKey。这反过来又会破坏下一个会话的第一个消息中发送的数据的加密【破坏initial data的加密】。</font> If, for some reason, the ephemeral session  key for the first session is exposed,  breaking the encryption of that session,  then it can be used to extract  the PreSharedKey. This, in turn, breaks the  encryption for the data sent in the  first message of the next session. 
  * 下一个会话中发送的其余数据是安全的，因为它是使用下一个会话的短暂会话密钥而不是PreSharedKey进行加密的。 The rest of the data sent in  the next session is safe, because it’s  encrypted using the ephemeral session key for  the next session, not the PreSharedKey.

2.其次，**与ClientHello消息一起发送并使用PreSharedKey加密的数据会受到回复攻击**。Secondly, data sent along with the ClientHello  message and encrypted using a PreSharedKey is  subject to a reply attack.

* **如果攻击者捕获并重新发送包含ClientHello、SessionTicket和用PreSharedKey保护的数据的TCP段，该数据将再次被服务器接受，服务器将做出响应，试图完成握手** If an on-path attacker captures and re-sends  the TCP segment containing the ClientHello,  the SessionTicket, and the data protected with  the PreSharedKey, that data will be accepted  by the server again, and the server  will respond, trying to complete the handshake.  
  * 这将会失败，因为连接已经建立，但那时数据已经被服务器接受和处理了。This will fail, since the connection has  already been established, but by then the  data has already been accepted and processed  by the server.  
  * TLS记录协议，一旦建立了连接，就可以防止这种回复攻击，但要做到这一点，它使用的是握手中交换的信息。The TLS record protocol, used once the  connection has been established, can protect against  such reply attacks, but to do so  it uses the information exchanged in the handshake.
  * TLS无法防止客户端向服务器发送的第一条消息中的0-RTT数据的重放。  TLS isn’t able to protect against replay of 0-RTT data sent in the first  message from the client to the server.
* 相应地，**如果你使用0-RTT重新建立的TLS，你应该确保你在第一条消息中发送的任何数据都是幂等的** Accordingly, if you use TLS with 0-RTT re-establishment, you should make sure that any  data you send in the first message  is idempotent.
  * 也就是说，如果接收到两次，不会造成任何伤害。That is, it won’t do any harm if it’s received twice.

:candy: 因为以上两种限制，在TLS中使用0-RTT数据时要小心。**0-RTT连接重建模式可以提高性能，但必须谨慎使用，以避免重放攻击**。since two limitations, it’s important  to be careful when using 0-RTT data  in TLS. The 0-RTT connection re-establishment mode  can improve performance, but has to be  used with care to avoid replay attacks.

## TLS其他局限-元数据泄露：TLSv1.3 Metadata Lakage

![](/static/2021-02-12-18-23-25.png)

1.首先，TLS在TCP/IP连接上运行，TCP和IP头没有加密。 这就向任何窃听连接的人暴露了某些信息。The first is that TLS runs over  a TCP/IP connection, and the TCP and  IP headers are not encrypted.  This exposes certain information to anyone eavesdropping  on the connection.

* **窃听者可以观察到发送数据包的IP地址和TCP端口号。An eavesdropper can observe the IP addresses  between which the packets are sent,  and the TCP port numbers.
  * **TCP端口号告诉你正在使用什么服务**。 The TCP port numbers tell you what  service is being used.
    * 例如，它们暴露了TLS连接是与Web服务器、电子邮件服务器、视频会议服务器、消息服务器，还是其他什么服务。For example,  they expose whether the TLS connection is  made to a web server, an email  server, a video conferencing server, a messaging  server, or whatever.
  * **而IP地址往往能告诉你访问的是什么网站。 小型网站往往使用共享主机，很多网站都是在共享服务器上运行，只有一个IP地址，但热门网站需要专用机器来托管，所以有自己的IP地址。窃听者往往可以通过IP地址来识别正在访问的是什么网站**。And the IP addresses often tell you  what site is being accessed.  Small sites tend to use shared hosting,  where many websites run on a shared  server with a single IP address,  but popular sites need dedicated machines to  host them, and so have their own  IP addresses. An eavesdropper can often use  the IP addresses to identify what site  is being accessed.
    * 也许这并不重要。 知道某个用户连接到了谷歌拥有的IP地址，也许并不能说明什么。Maybe that doesn’t matter.  Knowing that a particular user has connected  to an IP address owned by Google  maybe doesn’t tell you much. 
    * 但是，如果知道他们连接到一个IP地址，而这个IP地址的主机是一个提供特定疾病信息的网站，或者是一个特定的政党，或者是一个特定的小众社交网络，可能会暴露出更多关于用户的信息，甚至不知道他们说了什么，也无法访问通过该连接发送的数据。But knowing that they connected to an  IP address that hosts a website giving  information about a particular disease, or for  a particular political party, or for a  particular niche social network, might expose more  information about the user, even without knowing  what they said or being able to  access the data sent over that connection.
  
  2.**TCP头也会发送未加密的序列号。这暴露了通过连接发送了多少信息，以及数据下载的速度**。The TCP header also sends sequence numbers  unencrypted.This exposes how much information was  sent over the connection, and how fast  the data is being downloaded.
  
  * 这些信息通常比IP地址和TCP端口号更不敏感，但并不总是如此。This information is often less sensitive than  the IP addresses and TCP port numbers,  but not always.  
  * 例如，在Netflix这样的网站上，即使数据是加密的，也可以通过观察每个场景的下载速度模式来识别某人正在看什么电影。TCP序列号暴露了这些信息。For example, it’s been shown to be  possible to identify what movie someone is  watching, on a site like Netflix,  just by looking at the pattern of  download rates for each scene, even if  the data is encrypted. The TCP sequence  numbers expose this information.

3.TLS更大的风险是，当与HTTPS一起使用时，TLS ClientHello消息会包含服务器名指示扩展。A bigger risk with TLS is that,  when used with HTTPS, the TLS ClientHello  message will include the Server Name Indication  extension.

* **服务器名称指示提供了被请求的网站的名称，因此服务器知道在其ServerHello消息中包含什么公钥**。The server name indication provides the name  of the site being requested, so the  server knows what public key to include  in its ServerHello message.  
  * 它是为了支持共享主机而需要的，在共享主机中，许多网站被托管在一个单一IP地址的Web服务器上，服务器无法根据客户端连接的地址来判断被请求的是什么网站。It’s needed to support shared hosting,  where many websites are hosted on a  single web server with a single IP  address, where the server can’t tell what  site is being requested based on the  address to which the client connects.  
  * 由于客户端无法知道每台服务器上托管了多少个网站，所以它必须始终发送服务器名称信息字段来表明它想要的是什么服务器。Since the client can’t know how many  sites are hosted on each server,  it has to always send the server  name information field to indicate what server  it wants.
* **而且更糟糕的是，服务器名称指示字段必须是未加密的**And worse, the server name indication field  has to be unencrypted.
  * <font color="deeppink">这是因为它是在第一个数据包中发送的，在会话密钥还没有协商好之前，因为它是用来确定哪个服务器生成ServerHello的</font>This is because  it’s sent in the first packet,  before the session key has been negotiated,  because it’s used to determine what server  generates the ServerHello.  
  * 同样，服务器名指示也不能用PreSharedKey保护，因为那是服务器提供的，而目标是确定客户端选择的服务器Similarly, the server name indication can’t be  protected by a PreSharedKey, since that’s provided  by the server, and the goal is  to select the server.

:candy: 服务器名称信息是一种隐私风险。它将你连接的服务器名称暴露给任何窃听路径的人。The server name information is a privacy  risk. It exposes the name of the  server to which you’re connecting to anyone  eavesdropping on the path.

* 这是个重大风险吗？ 不清楚。正如我们在上一张幻灯片上看到的那样，网站的IP地址总是暴露的，无论网站名称是否暴露，这往往足以识别你正在访问的网站。Is it a significant risk?  Well, that’s not clear. As we saw  on the previous slide, the IP address  of the site is always exposed,  and that’s often enough to identify the  site you’re accessing, whether or not the  site name is exposed.
* 从根本上说，TLS是在互联网上运行的。 而互联网总是会暴露数据包中的IP地址，这样就可以知道谁在访问什么网站。Fundamentally, TLS runs over the Internet.  And the Internet always exposes the IP  addresses in the packets, making it possible  to tell who is accessing what site.
  * 如果你关心这个问题，你需要使用像洋葱路由器、Tor这样的应用程序来保护你的连接If you care about this problem,  you need to use an application like  The Onion Router, Tor, to protect your  connections.

## 协议僵化问题：TLSv1.3 Protocol Ossification

![](/static/2021-02-12-18-39-26.png)

TLS的实现非常广泛，但也有很多质量不高的实现 TLS is very widely implemented, but there  are many poor quality implementations.  

* **举个例子，TLS ClientHello表示客户端使用的TLS版本。服务器应该看这个，看它支持的TLS的不同版本，并发送一个ServerHello作为响应，表示最新的兼容版本**。As an example, the TLS ClientHello indicates  the version of TLS used by the  client. The server is supposed to look  at this, and look at the different  versions of TLS that it supports,  and send a ServerHello in response indicating  the most recent compatible version.  
  * 例如，如果ClientHello表示支持TLS 1.3，但服务器只支持TLS 1.2，那么服务器应该回复ServerHello说它支持TLS 1.2。客户端就会决定这是否足够，是否要进行通信If the ClientHello indicates support for TLS  1.3, for example, but the server only  supports TLS 1.2, the server is supposed  to respond with a ServerHello saying it  supports TLS 1.2. The client will then  decide if this is sufficient, and if  it wants to communicate.
  * 但在TLS 1.3的开发过程中，**发现如果ClientHello消息使用的版本号与TLS 1.2不同，一些TLS服务器就会崩溃**。During the development of TLS 1.3,  though, it was noticed that some TLS  servers would crash if the ClientHello message  used a version number different to TLS  1.2.  
  * **同样，也发现一些防火墙会检查TLS ClientHello消息，如果使用了不同于TLS 1.2的版本，就会阻止连接**Similarly, some firewalls were found that inspected  TLS ClientHello messages, and blocked the connection  if a version other than TLS 1.2  was used.
* **当部署了TLS 1.3的早期版本，在其ClientHello消息中使用1.3版本时**，When early versions of TLS 1.3 were  deployed, using version number 1.3 in their  ClientHello messages
  * 测量结果显示，这些问题影响了大约8%的TLS服务器，并在这些情况下阻止了TLS 1.3的工作。measurements showed that these problems  affected around 8% of TLS servers,and prevented TLS 1.3 from working in  those cases.
  * 也就是说，大约有8%的网站运行的TLS版本有问题，或者防火墙有问题，不能正确地实现TLS版本协商，所以如果使用新版本的TLS，就会失败。That is, around 8% of sites were  running buggy implementations of TLS, or had  buggy firewalls, that didn’t correctly implement TLS  version negotiation, and so would fail if  a newer version of TLS was used.

---

### TLS设计的改变&改进局限性

TLS的设计者们认为，在使用TLS 1.3时，有8%的连接失败是太多了。 所以他们改变了设计。The designers of TLS decided that having  8% of connections fail when using TLS  1.3 was too much.  So they changed the design.  

* **TLS 1.3现在在ClientHello和ServerHello消息的版本号上假装是TLS 1.2。但是，它也会在这些消息中发送一个额外的扩展头，以表示 "实际上，我使用的是TLS 1.3"**。TLS 1.3 now pretends to be TLS  1.2 in the version number in its  ClientHello and ServerHello messages. But, it also  sends an extra extension header in those  messages, to say “Actually, I’m really TLS  1.3”.
  * 经验和测试表明，只支持TLS 1.2的老式TLS服务器更有可能忽略这个额外的扩展头，而不是正确响应版本号字段的变化Experience and testing showed that older TLS  servers, that only support TLS 1.2,  are more likely to ignore this extra  extension header, than they are to correctly  respond to a change in the version  number field.
* **也就是说，当TLS 1.3客户端与TLS 1.2服务器对话时，【TLS 1.2服务器很可能会忽略扩展头，只看到ClientHello中的版本号。它将对此做出响应，并协商TLS 1.2】**。That is, when a TLS 1.3 client  talks to TLS 1.2 server, the TLS  1.2 server is likely to ignore the  extension header and see only the version  number in the ClientHello. It will respond  to this, and negotiate TLS 1.2.
* **但是，当一个TLS 1.3的客户端和一个TLS 1.3的服务器对话时，【TLS 1.3的服务器会看到这个扩展，并知道它应该忽略版本号字段中写着TLS 1.2的字样，而采用扩展头中的版本】**But, when a TLS 1.3 client talks  to a TLS 1.3 server, the TLS  1.3 server will see the extension,  and know it should ignore the version  number field that says TLS 1.2,  and instead go with the version signalled in the header.
  * 然后，它将用ServerHello来回应，它也会在版本字段中写上TLS 1.2，但是它有一个扩展头来表明它真正使用的是TLS 1.3。It will then respond  with a ServerHello, which also says TLS  1.2 in the version field, but that  has an extension header included to indicate  that it’s really using TLS 1.3.

:orange: **改进的局限性----这是可怕的。 这是写在TLS标准中的一个笨拙的东西**，This is horrible.  It’s a kludge written into the TLS  standard

* 因为经验表明，有太多的TLS错误的实现。because experience showed that there were  too many broken implementations of TLS out  there.
* TLS内置的版本协商功能在实践中是无法使用的。 在数以百万计的TLS服务器上，如果试图协商一个不同于TLS 1.2的TLS版本，就会导致服务器崩溃。The built-in version negotiation feature of TLS  turned out to be unusable in practice.  There were millions of deployed TLS servers  where trying to negotiate a TLS version  different to TLS 1.2 would cause the  server to crash.
* <font color="deeppink">将它们全部升级是不可行的。 唯一的变通办法是改变协议协商使用什么版本的方式，同时将旧版本留在那里，以便向后兼容。</font>Upgrading them all was infeasible.  The only workaround was to change the  way the protocol negotiated what version to  use, while leaving the older version there  for backwards compatibility.

:orange: **协议骨化是一个重要的问题。 而且这个问题不仅仅发生在TLS上**。Protocol ossification is a significant concern.  And the problem does not only occur  with TLS.

* **广泛部署的错误实现制约了大多数协议的设计**。 网络系统中最大的挑战之一是能够在不破坏以前部署的实现的情况下改变协议。 即使这些实现是错误的，并且以不同的方式被破坏。Widely deployed faulty implementations constrain the design  of most protocols.  One of the biggest challenges in networked  systems is being able to change the  protocol without breaking previously deployed implementations.  Even if those implementations are buggy and  broken in different ways.

### 如何避免协议僵化：How to Avoid Protocol Ossification

![](/static/2021-02-12-18-53-28.png)

**当协议中的扩展机制没有被使用时，就会发生僵化。当协议原则上允许灵活性，但协议的实施却没有使用这种灵活性**。Well, ossification occurs when extension mechanisms in  the protocol are not used. When the  protocol allows flexibility in principle, but the  implementations of the protocol don’t use that  flexibility.

* 例如，TLS 1.3是在TLS 1.2之后十年发布的。 在TLS被使用，并且越来越流行的十年中，有一段时期只使用TLS 1.2。For example, TLS 1.3 was released ten  years after TLS 1.2.  There was a period of ten years  when TLS was is use, and getting  increasingly popular, where only TLS 1.2 was  used.
* 这使得人们可以建立不正确的TLS版本协商的实现，因为他们不需要这样做。**由于没有新的版本可以协商，所以没有人正确地测试这个功能**。This allowed people to build implementations of  TLS that didn’t do version negotiation correctly,  because they didn’t need to. There were  no new versions to negotiate, so no-one  properly tested that feature.
* 同样的情况也发生在TLS的其他功能上。 The same happened with other features of  TLS.
* 同样的情况也发生在其他协议上。And the same happens with other protocols.  This hints at the solution to ossification
* <font color="red">这就提示了骨化的解决方案。 如果协议的功能或扩展机制没有被使用，协议就会僵化。这些特性和扩展机制的实现没有经过适当的测试，所以错误的代码会被部署，并限制了未来的变化</font>。Protocols ossify if they have features or  extension mechanisms that aren’t used. The implementations  of those features, those extension mechanisms,  aren’t properly tested, so buggy code gets  deployed, and constrains future changes.

#### GREASE扩展机制:生成随机扩展和持续扩展性

为什么不用那些扩展机制呢？ Why  not use those extension mechanisms?

* 这个想法叫做**GREASE**。生成随机扩展和持续扩展性。The idea is called GREASE. Generate Random  Extensions and Sustain Extensibility.
* <font color="deeppink">如果协议允许扩展，就发送扩展。 如果你没有其他东西可发，发送无意义的虚拟扩展，就会被忽略，【但要确保你的协议使用其扩展机制】</font>If the protocol allows extensions, send extensions.  Send meaningless dummy extensions that get ignored  if you have nothing else to send,  but make sure your protocol uses its  extension mechanisms.
* <font color="deeppink">如果协议允许不同的版本，就协商不同的版本。偶尔改变版本号，只是为了证明你可以使用。</font>If the protocol allows different versions,  negotiate different versions. Change the version number  occasionally, just to prove you can.
* Do this even if you don’t need  to.即使你不需要，也要这样做【上面两条】。
  * 也就是 "要么使用，要么失去"。 假设你的系统中任何不经常使用的功能都不会得到正确的测试，在实践中也无法使用，所以要确保所有的功能都能经常使用。That is “use it or lose it”.  Assume that any features of your system  that aren’t regularly used won’t get properly  tested, and won’t be usable in practice,  so make sure that all the features  get regularly used.

# QUIC传输协议：QUIC Transport Protocol

![](/static/2021-02-12-15-45-02.png)

QUIC是IETF刚刚完成开发的一个新的传输协议，旨在提高TLS和TCP的性能和安全性，并增加一些新的功能，同时避免由于协议僵化带来的问题。 QUIC is a new transport protocol,  just finishing development in the IETF,  that aims to improve on the performance  and security of TLS and TCP,  and add some new features, while avoiding  problems due to protocol ossification.

* QUIC是一种试图取代并改进TCP的尝试。它包含了TLS和其他各种有用的扩展。QUIC is an attempt to replace,  and improve on, TCP. It incorporates TLS  and various other useful extensions.
* QUIC的发展，以及它的基本功能。development of QUIC, and its basic features.
* 解释QUIC是如何建立连接、传输数据和避免骨化的。QUIC establishes connections, transfers data,  and avoids ossification.

---

![](/static/2021-02-12-20-55-50.png)

* TLS over TCP
  * 它建立安全连接的速度很慢，因为TCP连接建立握手和TLS安全参数协商【sessionticket、presharedkey、】是串联进行的，一个接一个。
  * 它泄露了连接的一些元数据
  * 而且TLS和TCP都是僵化的，已经很难扩展
  
  :orange: QUIC试图解决这些问题

* 这是一个单一的协议，它同时提供了传输和安全功能。It’s a single protocol, that provided both  transport and security features.
* **它通过重叠连接设置和加密密钥协商来降低连接建立的延迟。** It reduces connection  establishment latency by overlapping the connection setup and encryption key negotiation.
* **它试图通过使用普适性加密来避免元数据的泄露**。It tries to avoid metadata leakage by  use of pervasive encryption.
* **而它试图通过系统地应用GREASE**(生成随机扩展和持续扩展性)，并通过加密更多的传输头来防止骨化。And it tries to prevent ossification by  systematic application of GREASE, and through encryption  of more of the transport headers.

## 背景：History of QUIC

![](/static/2021-02-12-21-00-42.png)

QUIC是一个新的协议。 开发始于2012年，是谷歌内部的一个项目，旨在提高网络的安全性和性能。QUIC is a new protocol.  Development started in 2012, as a project  inside Google to improve the security and  performance of the web.

* 该项目的成果之一是SPDY，被IETF采用，最终成为HTTP/2。另一个是QUIC。One of the  outcomes of that project was SPDY,  which was adopted by the IETF and  eventually become HTTP/2. The other was QUIC.

谷歌在2016年将QUIC协议的控制权移交给了IETF，IETF成立了一个工作组来对协议进行标准化和开发。幻灯片上的链接指向了工作组的资料，如果你对这一过程的细节感兴趣的话。Google transferred control of the QUIC protocol  to the IETF in 2016, and the  IETF formed a working group to standardise  and develop the protocol. The links on  the slide point to the working group  materials, if you’re interested in the details  of how this was done.

* 截至2021年1月，QUIC规范在IETF中已经经历了34个不同的版本。链接的网站都有34个版本，所以你可以看到协议在发展过程中的变化。 而且，如果你顺着链接找到规范编写的GitHub repo，你会发现这是一个非常活跃的项目，到目前为止大约有2200个问题和2500个pull请求。As of January 2021, the QUIC specifications  have gone through 34 different versions in  the IETF. The websites linked have all  34 versions, so you can see how  the protocol has changed as it developed.  And, if you follow the links and  find the GitHub repo where the specification  was written, you’ll see that it’s a  highly active project, with around 2200 issues  and 2500 pull requests to date.
* 最终的结果显然是受到Google最初提案的启发，但经过IETF的过程，它已经发生了重大变化，现在是一个更好的协议。它的可扩展性更强，更安全，并且为进一步的开发提供了一个更干净的基础。The final result is clearly inspired by  Google’s initial proposal, but it’s been significantly  changed as a result of the IETF  process, and is now a much better  protocol. It’s more extensible, more secure,  and a provides a cleaner basis for  further development.
* 截至2021年1月底，QUIC规范的第34稿已经在IETF工作组完成了制定工作，正在接受互联网工程指导组（IESG）的最终审查。 很有可能在2月4日的IESG会议上被批准发布，该标准应在2021年复活节前后作为RFC发布。As of the end of January 2021,  draft 34 of the QUIC specification has  completed its development in the IETF working  group, and is undergoing final review by  the Internet Engineering Steering Group, the IESG.  It’s likely that it will be approved  for publication during the IESG meeting on  4th February, and the standard should be  published as an RFC around Easter 2021.

## 定义概括：QUIC Overview

![](/static/2021-02-12-21-04-20.png)

* 图显示了QUIC与互联网协议栈其他层的关系。The figure on the slide shows how  QUIC relates to the other layers of  the Internet protocol stack.

:orange: QUIC**执行了TCP的大部分功能，完全包含了TLS，并增加了HTTP的一些功能。本质上，它是想取代TCP和TLS**。QUIC performs most of the functions of  TCP, completely subsumes TLS, and adds some  features from HTTP. Essentially, it’s trying to  replace TCP and TLS.

* **QUIC在UDP上运行** QUIC runs on UDP.  
  * 也就是说，**QUIC数据包作为有效载荷在UDP数据包中发送，而不是直接在IP上运行**。这样做是为了方便开发和部署。That is, QUIC packets are sent as  the payload within UDP packets, rather than  running directly on IP. This was done  for ease of development and deployment.
* **除此之外，QUIC还提供了可靠性、排序和拥塞控制**On top of that, QUIC provides reliability,  ordering, and congestion control. 
  * 这些都是通常由TCP提供的功能。 它还结合了TLS的安全性，并提供了与TLS 1.3相同的安全保证。These are the  features usually provided by TCP.  It also incorporates TLS security, and provides  the same security guarantees as TLS 1.3.
* 最后，**它还加入了HTTP/2的流复用的思想**。And, finally, it adds the idea of  stream multiplexing from HTTP/2.
* 也就是说，<font color="deeppink">TCP连接传递的是单一的数据流，而QUIC则允许通过一个连接发送多个不同的数据流</font> That is,  where a TCP connection delivers a single  stream of data, QUIC allows multiple different  streams of data to be sent over  a single connection.
* <font color="red">其结果是一个通用的、可靠的、拥塞控制的、安全的客户端服务器传输协议。 </font>The result is a general purpose,  reliable, congestion controlled, and secure, client server  transport protocol.  
  * QUIC旨在为任何目前使用TCP的应用提供帮助，**并旨在为TCP提供一个更好、更安全的替代品。 它的设计也是为了有效地运行HTTP，并改进Web应用**。QUIC is intended to be useful for  any application that currently uses TCP,  and aims to provide a better,  and more secure, replacement for TCP.  It was also designed to run HTTP  effectively, and to improve web applications.
  * 在开发QUIC的同时，**IETF也在开发HTTP第3版，也就是运行在QUIC上的HTTP版本**。In parallel with the development of QUIC,  the IETF is also developing HTTP version  3, which is the version of HTTP  that runs on QUIC.

### 关键优点：重叠连接建立&安全握手

One of the key benefits of QUIC  is that it overlaps the connection establishment  and security handshakes.

![](/static/2021-02-12-21-12-00.png)

左图，通过TCP建立TLS连接的过程

* 2步
* 首先建立TCP连接，进行SYN、SYN-ACK、ACK交换。First the TCP connection is established,  with a SYN, SYN-ACK, ACK exchange.
* 然后，TLS接手协商安全参数和加密密钥，并对服务器进行认证。Then, TLS takes over to negotiate the  security parameters and encryption keys, and to  authenticate the server.
  * 它交换TLS ClientHello、ServerHello、Finished消息 It exchanges the TLS  ClientHello, ServerHello, and Finished messages.
* 而后才能发送数据 And only then can the data be  sent.

右图QUIC建立安全连接

* QUIC将这两个阶段合二为一 QUIC combines those two stages into one
* **QUIC在连接开始时在一个数据包中发送相当于`TCP SYN`和`TLS ClientHello`的消息**。QUIC sends the equivalent  of the TCP SYN and TLS ClientHello  messages in one packet at the start  of the connection.
  * 这将开始连接设置，并开始协商安全参数，在一个消息中。This begins connection setup, and starts to  negotiate security parameters, in one message.
* **响应包含相当于`TCP SYN-ACK`和`TLS ServerHello`，合二为一**。 The response contains the equivalent of the  TCP SYN-ACK and the TLS ServerHello,  combined into one.
  * 这将建立连接并提供安全参数 This establishes the connection  and provides the security parameters.
* **然后，握手中的最后一个数据包会发送等价的`TCP ACK`和`TLS Finished`消息，以及初始数据**。Then, the final packet in the handshake  sends the equivalents of the TCP ACK  and TLS Finished messages, and the initial  data.

:orange: 结果是QUIC比TCP和TLS的组合节省了一个来回。 The result is that QUIC saves one  round trip compared to the combination of  TCP and TLS.

* 它的正常情况下建立安全连接的**速度与TCP和TLS可以执行0-RTT会话恢复的速度一样快**，It’s normal case establishes  a secure connection as fast TCP and  TLS can perform 0-RTT session resumption,  
* 而且没有会话恢复的限制and without the limitations of session resumption.
* <font color="deeppink">而且QUIC还支持0-RTT会话恢复模式。这使得从【客户端向先前已知的服务器】发送的第一个数据包就可以同时建立连接，协商安全参数，并携带初始请求。</font>And QUIC also supports the 0-RTT session  resumption mode. This allows the very first  packet sent from a client to a  previously known server to both establish the  connection, negotiate security parameters, and carry an  initial request.
  * 这让QUIC实现了最好的性能：**向服务器发送一个请求，一个来回后就能得到响应**。This lets QUIC achieve the best possible  performance: to send a request to a  server and get the response one round  trip later.

## QUIC包，头，帧特点：QUIC Packets, Headers, and Frames

QUIC允许应用程序发送和接收数据流。QUIC allows applications to send and receive  streams of data.

![](/static/2021-02-12-21-25-14.png)

:orange: 与TCP不同的是，**每个TCP连接允许发送一个数据流和接收一个数据流，而QUIC允许在每个连接上发送和接收一个以上的数据流**。Unlike TCP, where each TCP connection allows  a single stream of data to be  sent and a single stream of data  to be received, QUIC allows more than  one stream of data to be sent  and received on each connection.

* <font color="deeppink">这使得应用程序可以分离出不同的对象，在 QUIC 连接中以单独的流传送每个对象</font>。This lets applications separate out different objects,  delivering each on a separate stream within  the QUIC connection.
* 例如，当获取一个包含多张图片的网页时，**QUIC将允许在该连接中以单独的流发送每张图片，而不是要求为每张图片打开一个新的连接**。For example, when fetching  a web page that includes multiple images,  QUIC would allow each image to be  sent on a separate stream within that  connection, rather than requiring a new connection  to be opened for each.

:orange: 这些连接的数据是在QUIC数据包中发送的。 **QUIC包又在UDP数据报中发送**。The data for these connections is sent  within QUIC packets.  QUIC packets are, in turn, sent within  UDP datagrams.

:orange: **每个 QUIC 数据包都有一个头，并包含一个或多个数据帧。这些帧包含各个流的数据、确认或其他控制信息**。Each QUIC packet starts with a header,  and contains one or more frames of  data. The frames contain data for the  individual streams, acknowledgements, or other control messages.

## QUIC头：QUIC Headers

:orange: <font color="deeppink">QUIC包可以是长头包，也可以是短头包</font>。QUIC packets can be long header packets  or short header packets.

### Long Header Packet

:orange: **在连接开始时，QUIC会发送所谓的长头包**At the start of a connection,  QUIC sends what are known as long  header packets.

![](/static/2021-02-12-21-30-04.png)

* 格式如图
* **它们以一个共同的报头开始**。
  * 这个共同头以单字节开始，<font color="deeppink">其中前两个位设为`1`，表示这是一个长头数据包</font> This common header begins with single byte,  where the first two bits are set  to one, to indicate that it’s a  long header packet. 
  * **这个字节的后两个位**，在图中标为`TT`，表示<font color="deeppink">长头包的类型</font> The next two bits  of this byte, labelled TT in the  diagram, indicate type of long header packet.
  * **而这个字节的剩余四位是未使用的**And the remaining four bits of this  byte are unused.
* 共同包头后是，**版本号、【目的连接标识符、源连接标识符】和包类型的具体数据**。This is followed by a version number,  a destination connection identifier, a source connection identifier, and the packet-type specific data.
  * **连接标识符允许QUIC连接在地址变化中存活**。The connection identifiers allow QUIC connections to  survive address changes.
    * 例如，如果连接到家庭WiFi网络的智能手机建立了与服务器的连接，然后移动到WiFi的范围之外，并切换到其4G连接，那么它的IP地址将发生变化。 这种IP地址的变化将导致TCP连接失败。<font color="deeppink">不过，连接标识符允许QUIC连接自动重新连接到服务器，并继续它离开的地方</font>。For example, if a  smartphone connected to a home WiFi network  establishes a connection to a server,  then moves out of range of the  WiFi and switches to its 4G connection,  then its IP address will change.  This change in IP address will cause  a TCP connection to fail.The connection identifiers allow a QUIC connection,  though, to automatically reconnect to the server  and continue where it left off.
  * 在连接标识符之后是**数据包类型的特定数据**。Following the connection identifiers is packet type  specific data
  * 有**四种**不同类型的长头数据包 【<font color="deeppink">这些不同类型的QUIC长头包都包含QUIC帧，而且都是在UDP数据报里面发送的</font>】There are four different types  of long header packet.
  * **初始数据包** An Initial packet
    * 用于启动连接并开始TLS握手，它的作用与**TCP SYN包**和TLS ClientHello（Crypto帧里）相同。used to initiate  a connection and start the TLS handshake.  It plays the same role as a  TCP SYN packet and a TLS ClientHello.
  * **0-RTT 数据包**
    * 用于携带0-RTT会话重建时发送的幂等数据used to carry  the idempotent data sent when 0-RTT session  re-establishment is being used.
  * **握手包** handshake packet
    * **用来完成TLS握手的**，used to complete  the TLS handshake.
    * 根据握手包的内容，**握手包相当于`TCP SYN-ACK`和**`TLS ServerHello`；Depending on its contents,  a Handshake packet is either the equivalent  of a TCP SYN-ACK with a TLS  ServerHello;
    * **或者`TCP ACK`和**`TLS Finished`消息。or a TCP ACK with a TLS Finished message.
  * **Retry** packet
    * 用于强制地址验证，作为连接迁移的一部分，并防止某些类型的拒绝服务攻击。used to  force address validation, as part of connection  migration, and to prevent some types of  denial of service attack.

:orange: **为了提高效率，QUIC允许在一个UDP数据报中包含多个Initial、0-RTT和握手包**(**长头包**)，**一个接一个，然后是一个短头包**。 To improve efficiency, QUIC allows several Initial,  0-RTT, and handshake packets to be included  in a single UDP datagram, one after  the other, followed by a short header packet.

### Short Header Packet

![](/static/2021-02-12-21-49-53.png)

**连接建立后**，QUIC就切换到发送**短头包**。QUIC switches to sending short header packets  once the connection has been established.

* <font color="deeppink">QUIC短头数据包为了节省空间而省略了版本号和源连接标识符</font>，QUIC short header packets omit the version  number and source connection identifier to save  space,
  * 因为这些可以推断为与连接建立握手期间发送的数据相同。since these can be inferred to be the same as those sent during the connection establishment handshake.
* <font color="deeppink">目前只有一种类型的短头数据包，称为1-RTT数据包</font> There is currently only one type of  short header packet, known as 1-RTT packets
  * 这些数据包用于QUIC握手完成后发送的所有数据包，并在所附的UDP数据包中包含一连串加密和认证的QUIC帧。 These are used for all packets sent  after the QUIC handshake has completed,  and contain a sequence of encrypted and  authenticated QUIC frames in the enclosing UDP packet

## QUIC帧-充当TCP头选项：QUIC Frames

![](/static/2021-02-12-21-56-10.png)

长头和短头数据包都包含**加密的QUIC帧序列**。Both long and short header packets contain encrypted sequence of QUIC frames

* **帧提供了QUIC的核心功能**。Frames provide the core functionality of QUIC.  
* 帧有许多不同的类型。There are many different types of frame.
  * **CRYPTO帧**CRYPTO frames
    * 用于携带TLS消息，如连接建立握手过程中用于协商加密密钥的`ClientHello`、`ServerHello`和`Finished`消息。 used to carry TLS  messages such as the ClientHello, ServerHello,  and Finished messages used during the connection  establishment handshake to negotiate the encryption keys.
  * **STREAM和ACK帧**
    * 发送数据和确认 STREAM and ACK frames send data and  acknowledgements.
  * **PATH_CHALLENGE**和**PATH_RESPONSE**帧
    * 支持网络接口之间的迁移。Migration between network interfaces is supported by  PATH_CHALLENGE and PATH_RESPONSE frames.
  * **而其他类型的帧**
    * 控制进度的QUIC连接。And the other types of frame control  progress of a QUIC connection.

:orange: **在许多情况下，QUIC帧发挥了TCP中头字段的作用** In many cases, QUIC frames play the  role taken in TCP by header fields.

* 例如，TCP 报头包含一个确认字段，该字段指示预期的下一个包。QUIC报头则没有 For example, a TCP header contains an  acknowledgement field that indicates the next packet  expected The QUIC header does not
* 相反，QUIC报文（QUIC短头数据包）中包含一个携带该信息的**ACK帧。 这使得QUIC更加灵活，可扩展性更强（而且还加密认证了**） Rather, QUIC packets include an ACK frame  that carries that information.  This makes QUIC more flexible and more  extensible. 
* **如果想要发送不同类型的控制信息，QUIC 只需【添加新的帧类型】，而不需要一个【固定的头】部**(这样就很难更改) Rather than have a fixed header,  that becomes difficult to change, QUIC can  just add new frame types if it  wants to send different types of control  information.

## QUIC连接建立&数据传输：QUIC Connection Establishment and Data Transfer

![](/static/2021-02-12-22-07-19.png)

与TCP一样，QUIC连接分两个阶段进行。首先是握手，然后进入数据传输阶段。Like TCP, a QUIC connection proceeds in  two phases. It starts with a handshake,  and then moves on to a data  transfer phase.

* **QUIC握手使用长头数据包**。 它建立连接，协商加密密钥，并对服务器进行认证。The QUIC handshake uses long header packets.  It establishes the connection, negotiates encryption keys,  and authenticates the server
* **数据传输阶段使用短头包**。在连接建立后，在这里发送和接收数据，并在这里生成确认。The data transfer phase uses short header  packets. It’s where the data is sent  and received, and where acknowledgements are generated, after the connection has been established.

### 连接建立过程：QUIC Connection Establishment

![](/static/2021-02-12-22-09-14.png)

QUIC通过将连接**TCP建立握手和TLS握手合并为一次往返，提高了TCP和TLS的性能**。QUIC improves on the performance of TCP  and TLS by combining the connection establishment  handshake and the TLS handshake into one  round-trip.

* **QUIC连接开始于客户端向服务器发送一个包含QUIC【初始包】的UDP数据报**。A QUIC connection starts with the client  sending a UDP datagram containing a QUIC  Initial packet to the server.
  * 这个初始数据包的作用**相当于`TCP SYN`数据包（长头包-初始数据包**），表示客户端希望建立一个连接This Initial  packet plays the equivalent role to a  TCP SYN packet and indicates that the  client wishes to establish a connection.
  * 初始数据包还包含一个QUIC **CRYPTO帧**。在这个CRYPTO帧里面是**一个`TLS ClientHello`消息**，就像TLS在TCP上使用一样，The Initial packet also contains a QUIC  CRYPTO frame. Inside that CRYPTO frame is  a TLS ClientHello message, the same as  if TLS was being used over TCP
  * <font color="deeppink">ClientHello消息开始了加密密钥和其他安全参数的协商。因此，QUIC初始包同时开始建立连接和TLS握手。</font> ClientHello message starts the negotiation of  the encryption keys and other security parameters. The QUIC Initial packet therefore simultaneously starts  both the connection establishment and the TLS  handshake.
* 作为回应，**服务器向客户端发送一个QUIC初始包和一个QUIC握手包**。 这两个包都包含在一个UDP数据报中。In response to this, the server sends  a QUIC Initial packet and a QUIC  Handshake packet back to the client.  These are both included in a single  UDP datagram.
  * **从服务器发回客户端的Initial数据包起到了`TCP SYN-ACK`数据包的作用，向客户端表明服务器愿意建立连接** The Initial packet sent from the server  back to the client plays the role  of the TCP SYN-ACK packet, and indicates  to the client that the server is  willing to establish the connection.
  * **Initial包还包括一个包含`TLS ServerHello`消息的CRYPTO帧**，它提供了完成加密密钥协商所需的信息。The Initial  packet also includes a CRYPTO frame containing  a TLS ServerHello message, that provides the  information needed to finish negotiating encryption keys.
  * **与初始数据包一起的还有一个QUIC Handshake数据包**。这包含了TLS认证服务器所需的信息，以及协商任何使用的QUIC扩展。Along with the Initial packet is a  QUIC Handshake packet. This contains the information  needed for TLS to authenticate the server,  and to negotiate any QUIC extensions used.
* <font color="red">一旦这个初始数据包和握手数据包到达客户端，连接就可以开始了。</font>Once this initial packet and the handshake  packet arrives at the client, the connection  is ready to go.
  * 客户端和服务器都同意进行通信，他们都有必要的信息来加密和验证通过连接发送的数据。Both the client  and server have agreed to communicate,  and they both have the information needed  to encrypt and authenticate the data sent  over the connection.
* **客户端通过发送第三个数据包结束握手，并开始数据传输. 这是一个UDP数据报，包含一个QUIC初始包、一个QUIC握手包和一个1-RTT短头包**The client concludes the handshake, and starts  the data transfer, by sending a third  packet. This is a UDP datagram that  contains a QUIC Initial Packet, a QUIC  Handshake Packet, and a 1-RTT short header  packet.
  * **这第二个QUIC初始包包含一个ACK帧，确认服务器发送的初始包**。This second QUIC Initial packet contains  an ACK frame, acknowledging the Initial packet  sent by the server.
  * **QUIC握手包包含一个CRYPTO帧**，该帧又包含完成安全握手所需的`TLS Finished`消息。The QUIC Handshake packet contains a CRYPTO  frame, that in turn contains the TLS  Finished message needed to complete the security  handshake.
  * 而**QUIC 1-RTT包中包含一个STREAM帧，初始数据由客户端发送到服务器**。And the QUIC 1-RTT packet contains a  STREAM frame, with the initial data sent  from the client to the server.

### 初始包格式：QUIC Initial Packet Format

![](/static/2021-02-13-00-31-07.png)

QUIC初始数据包的格式如图。The format of a QUIC Initial packet  is shown on the slide.

* 它是一个长头包，**在QUIC连接建立握手时使用**。It’s a  long header packet, used during the QUIC  connection establishment handshake.

:orange: 两个作用 QUIC Initial packets play two roles.

* **首先，它们是用来同步客户端和服务器的状态的**。在这方面，它的作用与TCP的`SYN`和`SYN-ACK`包相同。Firstly, they’re used to synchronise the client  and server state. In this respect,  the play the same role as TCP’s  SYN and SYN-ACK packets.
  * **它们还携带一个【CRYPTO帧】，其中包含一个`TLS ClientHello`或`TLS Finished`消息，作为加密设置的一部分。而且它们还可以包含【ACK帧**】。They also carry a CRYPTO frame,  containing either a TLS ClientHello or a  TLS Finished message, as part of the  encryption setup. And they can also contain ACK frames.
  * 初始数据包将**连接设置和安全协商**合并在一个QUIC数据包中 The Initial packets combine connection setup and  security negotiation into one packet.

### Retry包

* **QUIC初始数据包也可以携带一个可选的Token**。 QUIC Initial packets can also carry an  optional Token.
  * <font color="blue">QUIC服务器可以拒绝连接尝试，并向客户端发送一个包含Token的【重试包 Retry】。如果发生这种情况，客户端必须重试连接，在初始数据包中提供Token。</font>A QUIC server can refuse a connection  attempt, and send a Retry packet to  the client containing a Token. If this  happens, the client must retry the connection,  providing the Token in its Initial packet.
  * <font color="blue">如果令牌匹配，连接建立就会正常进行</font>。If the Token matches, the connection establishment  then proceeds as normal.
  * <font color="red">这是用来防止连接欺骗的。并验证在改变IP地址后重新建立的连接是否有效</font> This is used to prevent connection spoofing. And to validate that a connection that’s  being re-established after an IP address change  is valid.
  
### 握手包：Handshake Packets

![](/static/2021-02-13-02-03-38.png)

QUIC握手数据包完成了TLS 1.3的交换，QUIC Handshake packets complete the TLS 1.3  exchange.

* **它们包含`TLS ServerHello`消息或`TLS Finished`消息，（消息包含在CRYPTO帧中** They contain either a TLS ServerHello  message or a TLS Finished message,  contained within a CRYPTO frame.  
  * 在Initial数据包中发送的`TLS ClientHello`消息和在Handshake数据包中发送的`TLS ServerHello`和`Finished`消息的组合，完成了TLS安全握手。The combination of the TLS ClientHello,  sent in the Initial packet, and the  TLS ServerHello and Finished messages, sent in  the Handshake packets, completes the TLS security  handshake.
    * 它的工作原理和TCP上的TLS一样，**只是消息是在QUIC包内发送的（作为帧的一部分**）。It works just the same as  TLS over TCP, except that the messages  are sent inside QUIC packets.

:orange: 握手包使用长头 Handshake packets use a long header,

![](/static/2021-02-13-02-40-27.png)

* 在**大多数情况**下，为了减少开销，**握手数据包与初始数据包在同一个UDP数据报中发送**。In most cases, a Handshake packet is  sent in the same UDP datagram as  an Initial packet, to reduce overheads.
* <font color="red">如果合并后的数据包太大，无法容纳在一个UDP数据报中，Initial和Handshake数据包也可以用两个单独的数据报发送</font> If the combined packet is too big  to fit in a single UDP datagram,  though, the Initial and Handshake packets may  be sent in two separate datagrams.

## 0-RTT

正常的QUIC连接建立需要一个来回，之后客户机和服务器都同意通信，并掌握了他们需要的信息，得出对称加密密钥【会话密钥】。The normal QUIC connection establishment takes one  round trip, after which both client and  server have agreed to communicate and have  the information they need to derive the  symmetric encryption key【session key】.

![](/static/2021-02-13-02-42-30.png)

如果这样做太慢，**QUIC还支持TLS 0-RTT会话重新建立**。If this is too slow, QUIC also  supports TLS 0-RTT session re-establishment.

* 它的工作方式与TCP上的TLS类似。This works in a similar way to  TLS over TCP.
* QUIC客户端和服务器像平常一样建立连接，**服务器向客户端发送`PreSharedKey`和`SessionTicket`**。The QUIC client and  server establish a connection as normal,  and the server sends a PreSharedKey and  a SessionTicket to the client.
* **当客户端下一次建立与该服务器的quic连接时，它会将`SessionTicket`与`TLS ClientHello`消息一起添加到它在【初始数据包中】发送的`CRYPTO`帧中**。When the client next establishes a QUIC  connection to that server, it adds the  SessionTicket to the CRYPTO frame it sends  in its initial packet, along with the  TLS ClientHello message.
* 而且它**还包括一个`QUIC 0-RTT`包，其中包含一个`STREAM`帧，该帧承载着使用`PreSharedKey`加密的数据**。And it also includes a QUIC 0-RTT  packet, containing a STREAM frame that carries  data encrypted using the PreSharedKey.
* **QUIC服务器可以使用收到的`SessionTicket`来查找它的`PreSharedKey`副本，并使用它来解密`0-RTT`包中发送的流数据（客户端初始数据）**。【此步又可以通过client公号推导出session key】The QUIC server can use the SessionTicket  to look up its copy of the  PreSharedKey, and uses that to decrypt the  stream data sent in the 0-RTT packet.
  * :orange: <font color="red">与TCP上的TLS一样，0-RTT数据包中发送的数据并不是转发秘密，由于它有可能受到重放攻击，所以需要进行幂等化处理</font> As with TLS over TCP, the data  sent in the 0-RTT packet is not  forward secret, and needs to be idempotent  since it’s potentially subject to replay attacks.
* **然后，服务器会像正常情况一样，用【QUIC初始和握手数据包】进行响应，同时还有一个包含回复的`1-RTT`【短头】数据包** The server then responds with QUIC Initial  and Handshake packets, as normal, along with  a 1-RTT short header packet containing a  reply.
  * `1-RTT`，数据可以用session key加密，

## 0-RTT TLS vs QUIC

:orange: 不同的是，**QUIC中的0-RTT会话重建确实是在0-RTT后的第一个数据包中发送数据，而TCP上的TLS则是在TCP握手后的第一个数据包中发送数据**（QUIC 0RTT->第二次直接发初始数据【最大性能】，TLS 0RTT->省去一个TLS握手步骤，在TCP握手后直接发初始数据）。The difference is that 0-RTT session re-establishment  in QUIC really does send the data  in the very first packet, after zero  RTTs, whereas TLS over TCP sends it  in the first packet after the TCP  handshake.

* 即，QUIC建立一个安全连接，在通常情况下，只需要一个来回，而TLS通过TCP需要两个来回。The result of all this is that  QUIC establishes a secure connection, in the  usual case, in one round trip,  whereas TLS over TCP needs two round  trips.
* **QUIC将连接的建立和加密密钥的协商结合到一个单一的握手过程中。TLS-over-TCP则是按顺序进行两次握手。 因此，QUIC加快了连接建立的速度**。QUIC combines connection establishment and encryption key  negotiation into a single handshake. TLS-over-TCP runs  the two handshakes sequentially.  QUIC therefore speeds up the connection establishment.

## 握手结束（开始数据传输）-短头包: Data Transfer, Streams, Reliability

一旦**握手结束（连接建立后），QUIC就会切换到发送短头包。这些短头包用于传输和确认数据**。Once the handshake has finished, QUIC switches  to sending short header packets. These short  header packets are used to transfer,  and acknowledge, data.

![](/static/2021-02-13-03-02-53.png)

* **每个短头都包含一个包号字段**。这<font color="red">与TCP段中的序列号起着类似的作用</font>。Each short header contains a **Packet Number** field. This plays a similar role to  the sequence number in TCP segments.
  * **但与TCP不同的是，QUIC包号每发送一个数据包就增加一个。 QUIC中的数据包号计算的是发送的数据包数量**。Unlike TCP, though, QUIC Packet Numbers increase  by one for each packet sent.  Packet numbers in QUIC count the number  of packets sent.
  * 与此相反，**TCP**序列号计算的是**发送的数据字节数** In contrast, the TCP  sequence number counts the number of bytes  of data sent.
* **另外与TCP相比，QUIC从不重传数据包**。Also in contrast to TCP, QUIC never  retransmits packets.
  * 如果包含QUIC数据包的**UDP数据报丢失**，<font color="red">QUIC将在新的QUIC数据包中重传该数据包中的数据帧，这些数据包将有新的序列号</font> If the UDP datagram containing  a QUIC packet is lost, QUIC will  retransmit the frames of data that were  in that packet in new QUIC packets,  and those packets will have new sequence  numbers.
  * **而TCP则是用原来的序列号重传丢失的数据包**。TCP, on the other hand, retransmits lost  packets with their original sequence number.
    * 这意味着TCP无法分辨**重传数据包的到达**和**未丢包数据包的延迟到达**之间的区别。This means TCP cannot tell the difference  between the arrival of a retransmitted packet,  and a very delayed arrival of the  original packet.
    * <font color="deeppink">QUIC总是可以将这些区分开来，因为它们的数据包号不同。 与TCP相比，这简化了QUIC的拥塞控制算法的设计。</font> QUIC can always tell these  apart, since they have different packet numbers.  This simplifies the design of QUIC’s congestion  control algorithm, compared to TCP.
* 最后，**一个QUIC短头数据包以【受保护的有效载荷部分**】结束。Finally, a QUIC short header packet ends  with a protected payload section
  * 其中包含<font color="deeppink">经过加密和认证的QUIC帧。这些帧可以是包含数据的`STREAM`帧、包含确认的`ACK`帧或【其他控制数据】</font> This contains  encrypted and authenticated QUIC frames. These can  be STREAM frames containing data, ACK frames  containing acknowledgements, or other control data.
  * **QUIC头不包括任何等同于TCP确认号的内容。 取而代之的是，`ACK`帧作为受保护的【有效载荷数据】的一部分被发送，以指示收到的数据包号**。The QUIC header does not include any  equivalent of the TCP acknowledgement number.  Instead, ACK frames are sent as part  of the protected payload data, to indicate  received packet numbers.

### ACK帧确认

QUIC 以 **ACK 帧**的形式为**收到的数据包发送确认信息** QUIC sends acknowledgements for received packets in  ACK frames.

![](/static/2021-02-13-03-15-58.png)

* **ACK帧是在长头和短头数据包内发送的**。与TCP不同的是，确认ACK响应**不属于QUIC头的一部分**。ACK frames are sent inside long- and  short-header packets. Unlike TCP, the acknowledgements are  not part of the QUIC packet headers.
  * 更灵活，需要某些帧，只需要在载荷里包含QUIC帧，而不是TCP那样的固定头字段。
* <font color="red">另外与TCP不同的是，确认响应表示的是收到的QUIC数据包的序列号。 TCP序列号和确认响应计算的是发送的字节数，【而QUIC计算的是发送的数据包数】。</font>Also unlike TCP, acknowledgements indicate the **sequence numbers** of QUIC packets that were received.  TCP sequence numbers and acknowledgements count the  number of bytes sent, whereas QUIC counts  the number of packets sent.

### STREAM Frame帧 & 行首阻塞问题（QUIC流复用）

最后，数据是在**STREAM帧中发送的，这些数据是在QUIC包中发送的，是包含在UDP数据报中的**。Finally, data is sent within STREAM frames,  that are sent within QUIC packets,  that are contained within UDP datagrams.

![](/static/2021-02-13-03-19-01.png)

* 它包含一个**流标识符、流中数据的偏移量、数据的长度和数据本身**。It contains a **stream identifier, the offset  of the data within the stream,  the length of the data, and the  data itself**.

:orange: QUIC<font color="red">在单个连接中提供多个可靠的字节流(就不用，为每个独立数据开一个连接，，允许单个连接中开多个流来传输每个独立数据，流复用</font>）。QUIC provides multiple reliable byte streams within  a single connection.

* **每个数据流的数据按照发送的顺序在该数据流中可靠地传送，但数据流之间的数据顺序不会被保留**。Data for each stream is delivered reliably  and, in the order it was sent,  within that stream, but data order is  not preserved between streams.
  * 例如，假设一个客户端与服务器建立了连接，并在该连接内的两个流上发送数据，即流A和流B，For example, assume that a client has  a connection to a server and is  sending data on two streams, stream A  and stream B, within that connection.  
  * 客户端先在流A上发送消息，然后再在流B上发送另一个消息。The client first sends a message on  stream A, and then it sends another  message on stream B.  
  * **所有在流A上发送的数据都会按照发送的顺序可靠地到达。但先发送的流A上的消息可能会在流B上发送的消息之后到达**。All the data sent on stream A  will arrive reliably and in the order  it was sent. The same is true  of stream B. But the message sent  on stream A, which was sent first,  might arrive after the message sent on  stream B.  
  * <font color="red">也就是说，QUIC避免了流之间的行首阻塞，但没有避免流内（连接内）的阻塞</font>That is, QUIC avoids head-of-line blocking between  streams, but not within a stream.

:orange: There are two ways to view QUIC  streams

* **您可以将 QUIC 流视为允许您在单个连接中发送多个未加帧的字节流**。You can view QUIC streams as allowing  you to send multiple unframed byte streams  sent within a single connection.
  * **在这种观点中，一个 QUIC 连接提供的服务模式与多个并行 TCP 连接相同** In this  view, a QUIC connection offers the same  service model as several parallel TCP connections.
* **另外，您还可以将每个 QUIC 流视为一个消息的帧。在这种情况下，一个 QUIC 连接会传送一系列分帧的消息，每个消息都在一个单独的流上发送**。Alternatively, you can view each QUIC stream  as framing a message. In this view,  a QUIC connection delivers a series of  framed messages, each sent on a separate  stream.
* <font color="red">它们都是完全合理的看待QUIC连接的方式，但它们导致人们以不同的方式使用QUIC。 QUIC相当灵活，与TCP不同。它也是新的。我们仍在开发如何使用它、如何处理多个连接的最佳实践。
</font>They’re both entirely reasonable ways of looking  at a QUIC connection, but they lead  people to use QUIC in different ways.  QUIC is quite flexible, and is different  to TCP. It’s also new. We’re still  developing best practices for how to use  it, how to view the multiple connections.

# QUIC避免协议僵化：：Avoid Protocol Ossification

QUIC特性 & 避免僵化 & 成本与好处

## QUIC over UDP 好处

QUIC的一个不同寻常的特点是，它是一个在另一个传输协议上运行的传输协议。**QUIC在UDP上运行，而不是直接在IP上运行**。---为什么？One of the unusual features of QUIC  is that it’s a transport protocol that  runs over another transport protocol. QUIC runs  over UDP, rather than running directly on  IP.

![](/static/2021-02-13-03-29-13.png)

两个原因

1.**首先是使用户空间应用中的终端系统部署更加容易** The first is to make end-system deployment  in user-space applications easier.

* **直接通过IP运行的协议很难实现**。It’s difficult to implement protocols that run  directly over IP.  
  * **现有的传输协议TCP和UDP用来与IP实现对话的本地API隐藏在操作系统内核内部。它是一个内部接口，一般没有文档，专有的，无法访问**。 在这个层面上构建的任何东西都需要在操作系统内核内运行，并且会和内核的细节联系得非常紧密。The native API that the existing transport  protocols, TCP and UDP, use to talk  to the IP implementation is hidden inside  the operating system kernel. It’s an internal  interface, that’s generally undocumented, proprietary, and inaccessible.  Anything built at this level needs to  run within the operating system kernel,  and will be very tightly tied to  the details of that kernel.
  * **还有一个合理的可移植的接口，被称为raw sockets，它可以让你直接通过IP发送数据包。raw sockets接口并不是最容易使用的API，往往性能相对较低，并且提供有限的控制，但它可能是可行的**。There’s also a reasonably portable interface,  known as raw sockets, that lets you  send packets directly over IP. The raw  sockets interface is not the easiest API  to use, tends to be relatively low  performance, and offers limited control, but it  could be workable.
  * 只是无论是原始套接字，还是原生内核接口，都需要访问它们的程序**以管理员权限运行**。 这是个安全隐患，并且阻止这些程序上传到相关应用商店。 **这使得这类程序即使可以实现，也难以部署**。Except that both raw sockets, and the  native kernel interfaces, both require programs that  access them to run with administrator privileges.  This is a security risk, and prevents  those programs from being uploaded to the  relevant app stores.  This makes such programs, even if they  could be implemented, difficult to deploy.
* **另一方面，使用可移植的 Berkeley套接字 API 或 Windows Sockets 编写运行在 UDP 上的用户空间应用程序非常简单** On the other hand, writing user space  applications that run over UDP, using the  portable Berkeley Sockets API, or Windows Sockets,  is straightforward.
  * 同样的API在任何地方都可以使用。编程模式被广泛理解。而且不需要特权访问，所以程序易于分发和安装。The same API works everywhere. The programming  model is widely understood. And there’s no  need for privileged access, so the programs  are easy to distribute and install.
  * 通过UDP运行QUIC比原生通过IP运行QUIC要容易得多，也更便携。It’s much easier, and more portable,  to run QUIC over UDP than natively  over IP.

2.**在UDP上运行QUIC的第二个原因是协议僵化**The second reason for running QUIC over  UDP is protocol ossification.

* 几乎每一个连接到互联网的家庭和企业都是通过**NAT或防火墙（中间盒**）来连接的。Almost every home and business that connects  to the Internet does so via a  NAT or firewall.
  * **NAT设备**知道如何查找和翻译**TCP和UDP头文件中的端口号（TURN-relay，STUN，P2P**）。**防火墙**知道如何**检查TCP连接和UDP数据包的头和内容**。 NAT devices know how  to find and translate the port numbers  in TCP and UDP headers. Firewalls know  how to inspect the headers and contents  of TCP connections and UDP packets.  
* 而网络中所有其他代理、网关和中间盒也是如此，**这些设备都无法理解QUIC**。And the same is true of all  the other proxies, gateways, and middleboxes in  the network. None of these understand QUIC.
  * **之前协议已经广泛部署了，要让这些设备理解，需要大面积升级，很不现实**
* **如果我们在 UDP 数据包内运行 QUIC，它就有可能理解QUIC**。If we run QUIC inside UDP packets,  there’s a chance it will work.
  * 可以在不检查数据包内容的情况下转换 **UDP 报头，许多防火墙允许通过建立一个传入的防火墙针孔传出的 UDP 流量**，因为像 Zoom 这样的应用程序需要这样做。NATs can translate UDP packet headers without  inspecting the contents of those packets,  and many firewalls allow outgoing UDP traffic  to pass an establish an incoming firewall  pinhole, because this is needed for applications  like Zoom to work
  * 如果 QUIC 直接在 IP 上运行，情况就不会是这样了。<font color="red">Nat 不知道如何翻译 QUIC 数据包。防火墙倾向于阻止任何非 TCP 或 UDP 的东西</font>。This wouldn’t be the case if QUIC  ran directly over IP. NATs wouldn’t know  how to translate the QUIC packets.  And firewalls tend to block anything that  isn’t TCP or UDP.

:orange: 如果我们在 UDP 上运行 QUIC，它可能会在今天的互联网上工作。**但是如果我们直接在 IP 上运行它，那么我们必须在它工作之前更新每一个防火墙和 NAT(使所有中间设备理解QUIC，，因为他们只知道TCP/UDP) ——这基本上是不可能的任务，，协议过于僵化**。If we run QUIC over UDP,  it will probably work across the Internet  today. But if we run it directly  over IP, then we’d have to update  every firewall and NAT before it would  work – an essentially impossible task.

## 僵化例子2

> 图上显示的论文是一项研究，试图测量防火墙和NATs阻止包括标准化但**很少使用扩展的TCP数据包的频率**。 以及它们**阻止使用非标准扩展的TCP数据包的频率**，就像你在试验改变TCP时可能使用的那样。**它表明，TCP扩展（TCP的更新）很难部署**。而现在这个结果已经有近十年的历史了，但我怀疑对TCP的变化是否变得更容易部署了。
> The paper shown on the slide is  a study that tries to measure how  often firewalls and NATs block TCP packets  that include standardised but rarely used extensions.  And how often they block TCP packets  that use non-standard extensions, as you might  use when experimenting with changes to TCP.  It showed that TCP extensions were hard  to deploy. And while the results are  almost ten years old now, I doubt  it’s gotten easier to deploy changes to  TCP.

![](/static/2021-02-13-03-58-59.png)

**数据包头或有效载荷中可见的信息**，**没有经过加密和认证的信息，可以被网络中的设备检查和修改**。 Information that’s visible in the packet headers  or payload, that’s not encrypted and authenticated,  can be inspected and modified by devices  in the network.

* **这意味着NAT和防火墙可以检查IP数据包的内容**。 它们可以判断这些数据包是否包含TCP、UDP或其他内容。之所以能够做到这一点，是因为**IP头和IP有效载荷没有被加密（IP包数据部分，可以包含了TCP/UDP包**）。This means that NATs and firewalls can  inspect the contents of IP packets.  They can tell whether those packets contain  TCP, UDP, or something else. This is  possible because the IP header and payload  are not encrypted.
* **同样，由于这些IP数据包的内容没有经过认证，这些NAT、防火墙和其他设备可以修改数据包**。Similarly, because the contents of those IP  packets are not authenticated, these NATs,  firewalls, and other devices can modify the  packets.
  * 这意味着我们可以购买NAT盒子，修改数据包。那就是改变IP、TCP和UDP包头，允许多个设备共享一个IP地址。This means we can buy NAT boxes  that modify the packets. That change the  IP, TCP, and UDP packet headers to  allow several devices to share an IP  address.
  * 这意味着我们可以购买检查网络数据包的防火墙，并声称可以保护我们免受恶意软件的侵害。It means we can buy firewalls that  inspect network packets and claim to protect  us from malware.
  * 而这意味着我们可以购买所有其他的代理、缓存和其他检查和修改网络中流量的设备。And it means we can buy all  the other proxies, caches, and other devices  that inspect and modify traffic in the  network.
* **但也许我们不希望网络读取或修改我们的数据**。But maybe we don’t want the network  to read or modify our data.
  * 所以我们使用 **TLS 来保护** TCP 连接中**发送的数据（注意TLS局限性，还是会暴露元数据：TCP头，clienthello主机名**）。So we use TLS to protect the  data we send within TCP connections.
  * 或者数据报 TLS，它对 UDP 数据包中发送的数据做同样的事情。这个方法很有效。<font color="red">但是它不能做的是阻止这些设备检查和修改 TCP 和 UDP 数据包报头。而且它不会阻止他们查看 IP 数据包，并决定阻止它们，因为内容不是 TCP 或 UDP。或者是因为 TCP 或 UDP 报头不完全符合它们对应该如何格式化的理解</font>。Or datagram TLS, that does the same  for data sent within UDP packets.  And that works.  But what it doesn’t do, though,  is stop those devices inspecting and modifying  the TCP and UDP packet headers.  And it doesn’t stop them looking at  the IP packets, and deciding to block  them because the contents are not TCP  or UDP. Or because the TCP or  UDP header doesn’t exactly match their understanding  of how it should be formatted.
  * <font color="red">这意味着像 QUIC 这样的协议必须隐藏在 UDP 数据包中，才有机会通过网络。这使得更难改变 TCP 或 UDP 的工作方式</font>This means protocols like QUIC have to  hide inside UDP packets, to have a  chance of going through the network.  And it makes it harder to change  how TCP or UDP work.
    * 例如，**我们现在不能改变TCP头的格式，因为太多的设备希望采用现有的格式。 同样，即使是新的选项也很难添加到TCP数据包中，因为有太多的设备认为他们了解存在哪些选项，当他们看到一些意外的东西时就会失败**。We couldn’t change the format of the  TCP header now, for example, because too  many devices expect the existing format.  Similarly, it’s difficult to add even new  options to TCP packets, because too many  devices think they understand what options exist,  and fail when they see something unexpected.

## 协议僵化其他影响： Protocol Ossification Other Influence

:orange: 协议僵化正在成为一个越来越严重的问题，Protocol ossification is becoming an increasing problem.

* **当部署TLS 1.3时，它造成了问题**。it caused problems when  TLS 1.3 was being deployed.
* 当试图**扩展TCP时，它也会造成问题**。
  * 而这也是**为什么QUIC被设计成隐藏在UDP数据包中的原因之一** It causes  problems when trying to extend TCP.  And it was one of the reasons  why QUIC is designed to hide within  UDP packets.

:orange: 越来越多的标准社区将僵化视为一个问题。Ossification is Increasingly viewed as a problem  by the standards community.

* 这使得网络协议难以发展以满足新的需求，并导致担心网络将陷入困境。It makes it  difficult to evolve network protocols to address  new requirements, and leads to the concern  that the network will get stuck.  
* 它会知道问题和局限，我们知道如何解决，但是我们不能部署修复。That it’ll have known problems and limitations,  what we know how to solve,  but where we can’t deploy the fix.

## 避免QUIC僵化：Avoiding Ossification in QUIC 

:orange: **QUIC的设计者们为了使QUIC可以部署，防止其僵化**，费尽心思。The designers of QUIC went to great  lengths to make QUIC deployable, and to  prevent it from becoming ossified.

![](/static/2021-02-13-04-17-37.png)

* **在UDP上运行是其中的第一个**。Running over UDP was the first of these.
* 而且他们还使用了三种技术：
  * published the **protocol invariants** QUIC协议不变特性
  * make use of **pervasive encryption of transport header** 利用pervasive encryption 加密传输头
  * **GREASE**机制（经常使用，经常测试，否则舍弃）
* **我们的目标是使中间盒难以，最好是不可能干扰QUIC连接。也就是说，网络可以允许QUIC流量，也可以完全阻止它。 但除了不变特性外，它不能检查或修改QUIC流量**。The goal is to make it difficult,  and ideally impossible, for middleboxes to interfere  with QUIC connections. That is, a network  can allow QUIC traffic, or it can  block it entirely.  But it can’t inspect or modify QUIC  flows, apart from the invariant features.

### 方法1-QUIC不变特性：QUIC Invariants

**QUIC 不变量是 IETF 表示永远不会改变的 QUIC 属性**。QUIC invariants are the properties of  QUIC that the IETF has indicated will  never change.

![](/static/2021-02-13-04-20-59.png)

* QUIC的开发者和IETF标准社区已经写下了一套QUIC数据包的属性，他们保证每一个QUIC数据包、所有时间和所有版本都是真实的。The developers of QUIC, and the IETF  standards community, have written down a set  of properties of QUIC packets that they  guarantee will be true for every QUIC  packet, for all time, and for all  versions.
  * 这些是中间盒、NAT和防火墙被允许检查的属性。 IETF表示，**其他任何东西，都会在QUIC的未来版本中发生变化**。These are the properties that middleboxes,  NATs, and firewalls are allowed to inspect.  Anything else, the IETF has said,  is subject to change in future versions  of QUIC.

:orange: 具体**有什么不变特性**？ what are those invariants?

* **QUIC数据包将以长头或短头开始。** That QUIC packets will either start with  a long header or a short header.  
  * 没有其他报头格式。There will be no other header formats.  
  * **长报头数据包**的第一个字节的第一个位被设置为`1`。长头数据包的第2-5字节包含一个**版本号**。在版本号之后是**目标连接标识符和源连接标识符** That the first bit of the first  byte of a long header packet is  set to one. That bytes 2-5 of  a long header packet contain a version  number. And the following that version number  are the destination connection identifier then the  source connection identifier.
  * **短头数据包**，第一个字节的第一位总是为`0`。第一个字节后面跟着**目标连接标识符** short header packets, that the  first bit of the first byte is  always zero/0. And that the first byte  is followed by the destination connection identifier

:orange: **QUIC版本1的连接从连接建立握手开始，用长头包发送，然后连接切换到使用短头包，但不能保证未来的版本也会这样做**。A QUIC version 1 connection starts with  a connection establishment handshake, sent in long  header packets, then the connection switches to  use short header packets, but there is  no guarantee that future versions will do  the same.

* QUIC第1版定义了第一个字节的其他7位的格式，但不能保证未来版本的QUIC不会改变这个含义。QUIC version 1 defines the format of  the other 7 bits of the first  byte, but there’s no guarantee that future  versions of QUIC won’t change that meaning.
* **而且QUIC对连接标识符后面的数据包的内容不做任何保证**。And QUIC makes no guarantees about the  contents of the packets following the connection  identifiers.

### 普遍加密-避免僵化：Avoiding Ossification via Pervasive Encryption

当然，现在，写一个标准，说 "中间盒一定不要看QUIC数据包中的这些字段 "**并不能阻止中间盒查看这些字段**。 。Now, of course, writing a standard that  says “middleboxes MUST NOT look at these  fields in QUIC packets” doesn’t stop middleboxes  looking at those fields.

* <font color="red">相应地，QUIC【应用了另外两种技术来确保中间盒不能检查它的数据包】</font>Accordingly, QUIC applies two other techniques to  make sure middleboxes can’t inspect its packets.

![](/static/2021-02-13-17-46-43.png)

* 首先是**QUIC尽可能多地对数据包进行加密**。The first is that QUIC encrypts as  much of its packets as possible.
  * 除了不变字段和数据包第一个字节的最后7位之外，其他所有的数据包都是加密的。Everything except the invariant fields, and the  last 7 bits of the first byte  of the packet, is encrypted.
  * **这使得中间盒不可能检查连接标识符之后的 QUIC 报头的任何部分** This makes  it impossible for middleboxes to inspect any  part of a QUIC header after the  connection identifiers.
  * 由于 QUIC 融合了 TLS，所以对于大多数数据包来说，这是直接的Since QUIC incorporates TLS, this is straightforward  to do for most packets.  
* **在建立连接的过程中，【会商定一个加密密钥- 会话密钥 session key】，并将其用于加密数据包**。An encryption key is agreed using connection  establishment, and this is used to encrypt  the packets.
  * 那么<font color="deeppink">连接建立数据包本身</font>呢？ 它们是如何加密的？ **这些数据包包含了公钥，而公钥应该是公开的，所以它们实际上不需要加密**。What about the connection establishment packets themselves?How are they encrypted?  Well, these contain the public keys,  which are supposed to be public,  so they don’t actually need to be  encrypted
    * 例如，TLS over TCP并没有对这些数据包进行加密。**但是QUIC还是会对它们进行加密** TLS over TCP doesn’t encrypt these  packets, for example. But QUIC encrypts them  anyway.
  * **为什么使用会话密钥**
    * 它不能使用TLS密钥，因为这些数据包是在TLS握手完成之前发送的（因为QUIC重叠了两个步骤，，之前无密钥可用），it can’t use a TLS key,  since these packets are sent before the  TLS handshake is completed.
    * **而且它不能使用PreSharedKey，因为这可能是客户端和服务器第一次通信**。And it can’t  use a PreSharedKey, because this might be  the first time the client and server  have communicated.
    * <font color="red">它所做的，是从连接建立数据包中的长头中提取连接标识符，并将其作为加密密钥（会话密钥），对数据包的其他部分进行加密。</font>What it does, is to take the  connection identifiers from the long header in  the connection establishment packets, and use them  as the encryption key to encrypt the  rest of the packet.
    * 从表面上看，这没有任何好处。 当然，它也不提供任何安全保障。On the face of it, this offers  no benefit.  It certainly doesn’t provide any security.

:orange: **连接建立数据包的加密密钥（连接ID）包含在数据包的开头，没有保护。**。The encryption key for the connection establishment  packet is included, unprotected, at the start  of the packet

* 但这并不存在问题，因为连接建立包中没有什么秘密 That is doesn’t provide security doesn’t matter,  though, since there’s nothing secret in the  connection establishment packets.
* 它所做的，是让中间盒实现者思考。 你可以通过运行wireshark或tcpdump这样的工具来建立一个TCP中间盒，并在数据包经过时观察它们。What is does do, is make the  middlebox implementors think.  You can build a TCP middlebox by  running a tool like wireshark or tcpdump,  and looking at the packets as they  go by.
  * 一切都是可见的，在常见的情况下，头信息的变化模式，是很明显的。 你可以完全不看tcp规范，仍然可以建立一个有点用的中间盒。Everything is visible, and the  patterns of how the headers changes,  in the common cases, are obvious.  You can get away without reading the  TCP specification at all, and still build  a middlebox that sort-of works
  * 当然，它并不是真的能用，当人们试图用TCP做不常见的事情时，就会引起问题。但对于简单的情况来说，它是可行的。 Of course it doesn’t really work,  and causes problems when people try to  do uncommon things with TCP. But it  works for the simple cases.  
  * 你不能用QUIC来做。 一切看起来都是加密的。 你必须阅读并理解QUIC规范，才能意识到握手数据包是以一种让你轻松解密的方式加密的。否则，你得不到任何有用的东西You can’t do that with QUIC.  Everything appears to be encrypted.  You have the read and understand the  QUIC specification to realise that the handshake  packets are encrypted in a way that  lets you easily decrypt them. Otherwise,  you get nothing useful.

### GREASE机制避免僵化：Avoiding Ossification via GREASE

QUIC还大量使用了GREASE。QUIC also makes extensive use of GREASE.

![](/static/2021-02-14-19-53-20.png)

* 它确保**QUIC数据包中的每一个字段要么是加密的，要么是有一个至少有时是不可预知的值**。It makes sure that every field in  a QUIC packet is either encrypted,  or has a value that is,  at least sometimes, unpredictable.
  * **连接标识符**是在连接开始时**随机选择的**。The connection identifiers are randomly chosen at  the start of a connection.
    * QUIC 客户端会随机尝试**协商一个随机的版本号，服务器会拒绝这个版本号，以确保网络中的设备不会卡在只支持版本一的地方**。QUIC clients will randomly try to negotiate  a random version number, that the server  will reject, to make sure devices in  the network don’t get stuck only supporting  version one.
  * **而头文件中任何未使用的字段都被设置为随机值**。And any unused fields in the headers  are set to random values.
* 目标是，**中间盒不能对QUIC头值做任何假设，因为头中没有任何东西是可以预测的**。This goal is that middleboxes can’t make  any assumptions about QUIC header values,  because nothing in the header is predictable.
* **【希望(不知道是否起作用)】这样可以避免骨化。 头值没有模式，所以中间盒不能对头值的行为做出错误的假设，以免以后出现问题**。The hope is that this avoids ossification.  There are no patterns to the header  values, so middleboxes can’t make wrong assumptions  about how the headers behave that will  cause problems later.

# QUIC优点 & 开销：QUIC Benefits and Costs

![](/static/2021-02-14-19-58-49.png)

:orange: **为什么使用QUIC** why is quic desirable/ why might you want to use QUIC?

* **因为它可以加快安全连接的建立**。 Because it can speed-up secure connection establishment;
* **因为它解决了TLS 1.3在TCP上运行的一些局限性**；because it solves some of the limitations  of TLS 1.3 running over TCP
* **因为它支持在一个连接中发送多个流**。because it supports sending multiple streams  within a single connection.  
* **而且因为它，希望能够降低骨化的风险，为未来的协议发展提供长期的基础**And because it, hopefully, reduces risk of  ossification, and provides a long-term basis for  future protocol development.
* <font color="red">也就是说，QUIC是新的。标准还没有公布，我们也没有很多使用该协议的经验。</font>That said, QUIC is new. The standard  hasn’t yet been published, and we don’t  have a lot of experience with using  the protocol.

:orange: **QUIC存在的问题，局限性** Why is QUIC problematic

* 虽然有许多不同语言的**QUIC实现，但它们都还不成熟，文档不完善，而且经常出现错误**。And while there are many implementations of  QUIC, in many different languages, they’re all  still immature, poorly documented, and frequently buggy.  
* **而且目前，QUIC的速度往往比TCP-TLS慢，而且使用的CPU也更多**And QUIC is often, currently, slower,  and uses more CPU, than TLS over  TCP.
  * **这不是因为QUIC的固有局限性。而是因为TCP的实现多了40年的优化和调试时间**。This is not because of inherent limitations  of QUIC. Rather, it’s because the TCP  implementations have had 40 years more optimisation  and debugging time.
  * TCP协议栈目前优化得更好 TCP stack currently much better optimised
  * TCP和TLS经常有硬件卸载，QUIC还没有。TCP and TLS often has hardware offload; QUIC doesn’t yet
* **当实现完成、调试和优化后，QUIC 的性能至少应该和 TCP 一样好，而且可能明显优于 TCP。而且它应该更安全，更容易扩展**。TCP 持续了40年(优化和调试时间)—— QUIC 是一个类似的长期项目，刚刚达到1.0版本。When the implementations are finished, debugged,  and optimised, QUIC ought to perform at  least as well as, and probably significantly  better than TCP. And it should be  more secure and easier to extend.  TCP lasted 40 years – QUIC is  a similarly long-term project, that’s only just  reaching version 1.0.

