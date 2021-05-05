# 公钥基础设施：Public Key Infrastructure

MegaCorp是一个跨国组织，在许多不同的地点运作。这些网站使用公钥加密进行通信。
你想监视他们的通信，并改变信息的内容。请描述你可以实现这一目标的几种方法，并概述你成功所需的资源。在每一种情况下，描述MegaCorp可以用来挫败你的企图并保护他们的资产和通信的反制措施。MegaCorp is a multination organisation that operates on many different sites. The sites communicate using public key encryption.
You would like to spy on their communications and also change the contents of messages. Describe several ways in which you can achieve this, outlining the resources needed for you to succeed. In each case describe the countermeasures that MegaCorp can use to foil your attempts and protect their assets and communications.

你需要能够截获两个站点之间的通信，并能够替换信息。然后，你可以用你自己的公钥替换传输中的公钥。MegaCorp可以通过在公钥证书内发送他们的密钥来进行防御。MegaCorp可以依靠一个受信任的认证机构来签署证书，在这种情况下，你可以尝试让受信任的机构给你颁发假的证书。最有效的方法是内部攻击，即颠覆进行身份检查的人之一。认证机构需要建立一个程序来防止这种情况的发生，也许可以通过监控其参与身份检查的工作人员的所有活动。你也可以黑进目标网站，把你自己的一个主证书添加到他们的证书列表中。MegaCorp可能大到足以创建自己的证书签署程序，并使用它来签署其每个站点的公钥证书。然后，它可以在自己的技术人员访问现场时交付主证书。这里的弱点是，他们不会像受信任的认证机构那样有经验，可能会犯错。You need to be able to intercept the communications between two sites, and be able to replaces messages. You can then replace public keys in transit with your own public keys. MegaCorp can defend by sending their keys inside public key certificates. MegaCorp could rely on a trusted certifying authority to sign the certificates, in which case you try and get the trusted authority to issue you with fake certificates. The most effective way of doing this is an insider attack where you subvert one of the people doing the identity checks. The certifying authority needs to put in place a process to prevent this, perhaps by monitoring all the activities of its staff involved in ID checks. You could also hack into the target site and add one of your own master certificates to their list of certificates. MegaCorp is probably big enough to create its own certificate signing process and use it to sign the public key certificates on each of its sites. It can then deliver the master certificate during site visits by its own technical staff. The weakness here is that they will not be as experienced as the trusted certifying authority and can make mistakes.

## 中间人攻击：Man in the Middle Attacks

![](/static/2021-02-25-15-43-19.png)

* 所有公钥系统都有中间人攻击风险
* 如果B将公钥送给A，E尝试用自己的公钥替换B的公钥
  * 当A发送密文给B，认为用的是“B的公钥”加密，然而此时E可以用自己的秘钥解密该明文
  * E可以二次篡改，并用B的公钥加密后发送给B

---

解释公钥基础设施（PKI）如何防止中间人攻击。描述与芯片和密码系统相关的PKI，并解释使其容易受到中间人攻击的缺陷。Explain how a public key infrastructure (PKI) prevents a man-in-the-middle attack. Describe the PKI associated with the Chip and PIN system and explain the flaws that made it vulnerable to a man in the middle attack.

公钥证书包含一个公钥、识别公钥所有者的信息和其他特定应用信息。它是用一个受信任的机构的秘密钥匙签署的。该证书必须用受信机构的公开密钥解锁，该密钥必须是安全分发的。可以构建证书链，以便只需要安全地分发单个可信机构的公开密钥。A public key certificate contains a public key, information identifying the owner of the public key and other application specific information. It is signed with the secret key of a trusted authority. The certificate must be unlocked with the public key of the trusted authority, which must have been distributed securely. Certificate chains can be constructed so that only the public key of a single trusted authority needs to be distributed securely.

终端和卡片都可以决定一个用户是否已经被认证。他们之间的通信是不加密的，因此中间人可以截获通信并向卡和终端提供 "已验证 "的答案。Both the terminal and the card can decide if a user has been authenticated. The communications between them are not encrypted and so a man in the middle can intercept communications and give the 'verified' answer to both the card and terminal.

## 公钥证书：Public Key Certificate

![](/static/2021-02-25-15-46-00.png)
![](/static/2021-02-25-16-01-14.png)

A public key certificate is a document that contains a public key, the owners ID and other application specific information

* 公钥证书是一份包含公钥码、所有者ID和其他特定应用信息的文件。
  * 如B的公钥证书包含B的公钥
* 公钥证书必须由Trent（可信任第三方）签发 It has been signed by Trent, a trusted third party

:orange: 存在的攻击

* **E创建假证书，自己的公钥伪装成B的公钥**
  * 实际不可行，因为E没有Trent的秘钥，不能生成签名
  * Trent必须保证秘钥安全，因为如果Trent遭到攻击也可能遗失秘钥
  * 必须提供召回Trent公钥的机制（一旦秘钥遗失），和发放新证书的机制 There must be a mechanism for Trent to revoke their public key and issue new certificates if their secret key is compromised.
* **E请求Trent为他签属公钥证书，伪装成B（E假装自己是B，请求Trent签属公钥证书）**
  * Trent签属证书前，必须验证拥有者ID Trent must verify the owner’s ID before signing the certificate
  * 证书必须包含ID（唯一标识） & 公钥 The certificate must contain the owner’s ID as well as the public key
* **E自己建立CA（证书授权中心）**
  * 实践可行，因为存在很多CA
  * 因此需要更高组织验证CA（国家级，国际组织） There must be a higher organisation to certify certifying authorities.

---

:orange: A必须有Trent的公钥才能获取证书

* 称为master key
* 拿到Trent的公钥应该比拿到B的公钥更容易

## 认证链：Certificate Chains

![](/static/2021-02-25-16-08-45.png)

公钥证书可能存在多层联系

* **Trent的公钥可以由国家CA签属作为公钥证书的一部分** Trent’s public key can be distributed as part of a public key certificate signed by a national certifying authority.
* **所以可以存在多个Trent，但A只需要一个国家级CA的公钥（master key**） just has to have the correct public key for the national certifying authority.
  * 使用国家CA公钥，从Trent的证书中获取Trent的公钥
  * 使用Trent公钥，从收方证书（由Trent签发）中获取收方公钥
* **CA公钥可以嵌入在硬件中**
  * 读卡器嵌入CA公钥，验证芯片
  * **防篡改或防篡改提醒** Tamper resistant or tamper evident.
    * tamper evident - 可能进行篡改，但会被发现

---

## Question

MegaCorp is a multination organisation that operates on many different sites. The sites communicate using public key encryption. You would like to spy on their communications and also change the contents of messages. Describe several ways in which you can achieve this, outlining the resources needed for you to succeed. In each case describe the countermeasures that MegaCorp can use to foil your attempts and protect their assets and communications. MegaCorp是一个跨国组织，在许多不同的网站上运作。这些网站使用公钥加密进行通信。你想监视他们的通信，并改变信息的内容。请描述你可以实现这一目标的几种方法，并概述你成功所需的资源。在每一种情况下，描述MegaCorp可以用来挫败你的企图并保护其资产和通信的反措施。

![](/static/2021-02-25-16-21-28.png)

# 公钥系统使用-芯片&PIN：Chip & PIN

## 证书树 & 信任网

证书树的一个替代方案是信任网。这是一种基于同行的方法，没有一个中央认证机构。你从你信任的人那里积累了足够多的公钥证书版本，以信任该公钥。讨论证书树和信任网的优点和缺点。
 An alternative to certificate trees is the web-of-trust. This is a peer-based approach without a central certifying authority. You accumulate enough versions of a public key certificate from people you trust to trust the public key. Discuss the advantages and disadvantages of certificate trees and a web-of- trust.

赞成的理由与赞成点对点网络而不是一个中央服务器的理由相同。你不必依赖一个中央机构。缺点是寻找值得信赖的人加入你的圈子。该算法更复杂，你的圈子的每个成员都有一个信任等级。这些评级是动态的，新成员开始时信任度较低，随着经验的增加，信任度也会增加。新成员通常由现有成员推荐，如果太多的成员由同一个人推荐，这可能是危险的。The arguments in favour are the same as in favour of peer-to-peer networks rather than one central server. You don't have to rely on one central authority. The disadvantages are finding trustworthy people to add to your circle. The algorithm is more complicated, with each member of your circle given a trust rating. These ratings are dynamic, with a new member starting with a low trust rating, which increases with good experience. New members are normally recommended by existing members, which can be dangerous if too many members were recommended by the same person.

## Context

![](/static/2021-02-25-16-26-50.png)

* **银行卡（CHIP and PIN）嵌入芯片，允许运行卡程序** Chip and PIN cards have a chip embedded in the card to allow programs to run on the card
  * 称为 smart cards, ICC cards, intergrated circuit cards
  * **旧卡只包含磁条** magnetic strip
    * **静态认证** static authentication - the card doesn;t do any work just contains some envrypted in relation on the magnetic strip
    * **现代卡仍有静态磁条，仍然可以降级为静态认证**
  * **动态认证** dynamic authentication
    * 使用芯片时运行程序 run programme
* EMV标准 They are part of the EMV standard
* 使用公钥基础设施 They use a public key infrastructure.【PKI】

---

![](/static/2021-02-25-16-35-20.png)

* 为打击欺诈行为，已推出智能卡。
* 实施芯片和个人识别码的成本约为10亿英镑。
* **芯片解决了克隆卡的伪造欺诈问题**。
  * 它可以验证卡片。
* **PIN通过验证持卡人来打击签名欺诈**
* 其他优势包括无需存储签名的卡凭证副本
  * 另一个好处是很重要的，就是不用在存储的签名的卡片复印件上签字，否则有这些纸片必须要保存多年

> 机翻：用老式的卡片，只有一个磁条。它有可能只是关闭卡， 使该条的精确复制。所以，他们曾经是一个骗局，虽然，工作在质量目的地餐厅，例如作为一个广泛的骗局。所以，你用你的信用卡支付 餐厅老板把你的卡出来支付， 然后秘密克隆它。然后他们可以把它卖出去，然后你的卡就开始被犯罪分子使用。
> 如果使用PIN就能避免复制秘密信息 & 签名

## 责任转移：Liability Shift

another advantage from the banks is a liability shift.

![](/static/2021-02-25-16-44-37.png)

> Starting from 2005, the liability for fraudulent transactions at the point of sale shifted from the bank to the retailer if the retailer is not using chip and PIN. On the other hand, if the bank does not support chip and PIN then they will be liable for losses. This changed on 1st November 2009, when the banks had to prove that the customer was at fault when money was taken from an account. This was a result in failures of the Chip and PIN system.
> 从2005年开始，如果零售商不使用芯片和个人识别码，**则在销售点进行的欺诈性交易的责任就由银行转移到零售商身上**。另一方面，如果银行不支持芯片和 PIN，那么他们将承担损失。
> 这种情况在2009年11月1日发生了变化，**当时银行必须证明客户在从账户中取款时有过错(而不是假设)。这是因为芯片和 PIN 系统存在缺陷**

## 缺陷：Failures

![](/static/2021-02-26-19-54-20.png)

> In 2006 Shell petrol stations were attacked. o Their implementation of the protocol allowed customer data to be leaked and around £1 million was stolen from their customers.  In October 2008, PIN terminals were targeted during their manufacture in China, again leaking customer data.  A man in the middle attack was successfully demonstrated in 2009. o More details later
> 2006年，壳牌公司的加油站遭到了攻击。他们实施的协议使得客户数据被泄露，约有100万英镑的客户数据被盗。
> 2008年10月，PIN终端机在中国生产过程中成为攻击目标，再次泄露了客户数据。
> 2009年，一次**中间人攻击**被成功演示。

## 支持的操作：Operations Provided

![](/static/2021-02-26-20-03-17.png)

* **银行卡支持两种认证方式**
  * 静态数据认证 - 磁条 Static Data Authentication – magnetic strip
  * 动态数据认证 - 芯片 Dynamic Data Authentication – chip on card
* **交易&信息通过信息摘要签名进行认证** Transactions and messages can be authenticated using a signed message digest
  * 这就是所谓的应用密码或消息验证码（MAC）。This is called an application cryptogram or a message authentication code (MAC).
  * 在对用户进行验证后，根据银行和客户双方的信息生成加密密钥（用于对称加密）。after the user has been verified, then an encryption key is generated based on information that both the bank and the customer though.
  * 发方用会话密钥加密信息摘要 & 明文，一起传输给收方，收方解密获取信息摘要 & 明文，明文再次生成信息摘要，进行对比，如一致则无篡改
* 所以，有PIN加密的切入点是动态数据认证，So, there's pin encryption that point of entry was the dynamic data authentication
  * 在一张纸上签字是PIN认证的一个替代方案 signing a piece of paper is an alternative to pin authentication

## 采用的算法：Algorithms Used

![](/static/2021-02-26-20-26-02.png)

**算法**

* **3-DES、RSA、SHA-1**(安全散列算法1) 3-DES, RSA, SHA-1 (Secure Hash Algorithm 1)
  * CHIP&PIN引入的时候， 有已经从字面上打破了三重数据，所以，用RSA公钥算法和Sha是最好的消化散列算法
* 未来可能出现的新算法（如ECDSA）椭圆曲线数字签名算法。Possibly new algorithms in the future (e.g. ECDSA) Elliptic Curve Digital Signature Algorithm.

**算法运用机制** Mechanisms

* **RSA数字签名和公钥证书** RSA digital signatures and public key certificates
  * 用来建立公钥基础设施，证书链  are used to create public key infrastructure, a certificate chain.
* **卡片上独一无二的3-DES密钥（唯一的对称加密密钥），源自主密钥**。Card unique 3-DES keys, derived from Master Keys
  * 主密钥唯一。所以，换句话说就是每次用卡的加密密钥都是一样的，卡是的唯一的而不是交易唯一 The encryption keys, triple DES AES keys are derived from Master keys which are called unique. So, in other words it's the same encryption key each time you use the card toast is unique to the card not the transaction.
* 用于加密和MAC(消息验证码)的**唯一的会话密钥** Unique session keys for encryption and MAC(message authentication code)
  * 而对于侧设备来说摘要后面的会话密钥比使用同一个加密密钥，每次都要好 for the side devices digest the back of the session key is better than using the same encryption key

## EMV（IC卡）公钥证书：EMV Public Key Certificate

![](/static/2021-02-26-20-36-03.png)

* **Certificate Core** 证书核心
  * 关于用户和应用程序的信息 General information about the user and the application
* **Public Key & Public Key Remainder** 公钥 & 优化参数
  * remainder - <font color="deeppink">从公钥中衍生而来，用于加速计算访问&优化参数。前略：公钥算法计算很慢，任何优化都很有用。卡中芯片比电脑计算慢的多，因此优化很重要</font> it's used to speed up the calculations essentially access and optimization parameter. public key algorithms, very very slow. So any optimization is is quite useful. And that's especially true when you use the chip on a card. So, you saw how long my computer to create the public keys and do the calculations. The chip on the card is much less powerful, and so the calculation. Time is a significant burden. So, so any optimization is doubly important with using a variable processing device. 
* **Message Digest** 消息摘要
* **EMV formatting**
  * 包括：证书核心，公钥部分，信息摘要
  * 最后被可信任第三方签名，signature by a trusted third party

## 公钥证书验证：Public Key Certificate Validation

![](/static/2021-02-26-23-19-16.png)

* 可信第三方（签署证书的人）的**公钥被用来解密证书**，从而揭示数据 The public key of the Trusted Third Party (who signed the certificate) is used to decrypt the certificate, thus revealing the data.
  * 即提取，公钥 & 用户信息
* **披露数据的EMV格式（头、预告片、证书格式）被检查** The EMV format of the revealed data (header, trailer, certificate format) is checked.
* **计算揭示数据的消息摘要，并检查其是否与证书中的信息摘要相同** The message digest of the revealed data is calculated and check that it is the same as the message digest in the certificate.
  * 确保信息未被篡改 make sure the information hasn't been tampered with
* 公钥的指数和模数，(e，n)从所揭示的信息中提取 The public key exponent and modulus, (e, n) is extracted from the revealed data.
  * `n` - product of the two primes in the RSA system
* <font color="deeppink">在密钥证书上发现的公钥余量是一种优化(参数)。为加快计算速度而设计的参数</font> The public key remainder found on key certificates is an optimisation parameter designed to speed up the calculations.
  * RSA【指数计算很慢】

## 交易中涉及的角色：Parties to the Transaction

:orange: 支付系统认证机构：负责整个流程的可信第三方 Payment System **Certifying Authority**: the trusted third party **in charge of the whole process**

* Trent’s big boss (之后用来建立证书链)

:orange: 发行人：一家银行  The Issuer: a bank.

* Trent

:orange: 与客户相关的卡  Card associated with a customer. （A）

* 主账户号码：卡上的16位数字or(PIN) Primary account number: the 16 digit number on the card.

:orange: 收单人：卖东西给客户的商家 (B) Acquirer: the merchant selling something to the customer.

:orange: 卡终端 The Card terminal

* 读卡器。。。The merchant who owns the terminal

## 静态数据认证：Static Data Authentication

![](/static/2021-02-26-23-34-09.png)

* 认证机构**CA的公钥证书以未签名的形式存储在终端中** A Certifying Authority public key certificate is stored in the terminal in unsigned form
  * 因此对终端的制造过程必须非常仔细，确保没有其他人能够用其他公钥替换终端中的CA公钥 has to be manufactured very carefully. So I saw that someone can't replace the key in the terminal with a different key.
  * 防改变&防篡改 protected against changed and tamper resistant
* **银行的公钥证书**，由**CA签名后**存储在银行卡中 A bank public key certificate, signed by the Certifying Authority is stored on the card
  * 因此终端可以安全识别银行的公钥
  * 银行卡插入终端时，终端需要验证银行卡信息身份可信度，即终端需要获取银行卡中公钥证书信息用来识别该银行卡

### 初始化阶段：Initialisation Phase

![](/static/2021-02-26-23-59-23.png)

* 5 parties
* CA发放PK给零售商，用于加载在终端中（终端持有CA公钥证书）
* 银行卡最后具有
  * 静态信息 - Bank的私钥（可以为签名）
  * 银行的公钥证书

### 认证阶段：Authorisation Phase

![](/static/2021-02-27-00-07-25.png)

* 银行卡给终端提供以下信息
  * CA签属的银行公钥证书 PKb
  * Bank电子签名（包含银行卡&银行静态信息）
* 终端使用CA公钥，检索银行的公钥（已被CA认证）
* 终端使用银行公钥，验证银行卡数据的电子签名

## 动态数据认证：Dynamic Data Authentication

采用动态认证，卡可以运行认证程序

![](/static/2021-02-27-00-22-57.png)

动态数据认证提供卡的真实性和完整性。Dynamic data authentication provides authenticity and integrity of the Card

* **在卡片个性化后，可以检测到卡片数据的未经授权的更改**。Allows detection of unauthorised alteration of Card data after the card has been personalised.
* **防止重放攻击和卡片伪造**。Prevents replay attacks and Card counterfeiting.
* **动态数据认证涉及到终端不可预知的数字 (UN)(nonce)和卡上的计算**。Dynamic data authentication involves a Terminal unpredictable number (UN) /(nonce) and calculations on the Card.
  * <font color="deeppink">终端生成随机数UN，发送给card，card用自己的私钥把UN签名，之后card将这个UN签名送回终端，之后终端用card的公钥解密该签名，认证该卡</font>
  * UN用卡的秘钥签名 The UN is signed by the card’s secret key
  * **之后终端可以用卡的公钥，认证卡的身份（只有正确的卡能通过认证），获取这个UN不可预测数字**
  * UN/nonce - random number
* **在这个证书链中使用了3个公钥/秘钥对** 3 public / secret key pairs are used in this certificate chain
  * 银行密钥对
  * 银行卡密钥对
  * 终端的密钥对

### 初始化阶段：Initialisation Phase

![](/static/2021-02-27-04-27-22.png)

* card的公钥被银行用私钥签名
* card上最后包含bank的公钥证书 & 卡静态数据 & 卡的私钥（？）

### 认证阶段： Authentication Phase

![](/static/2021-02-27-04-41-38.png)

1. 终端发送UN不可预测随机数给card
2. card给终端提供两个公钥证书
   1. bank公钥证书
   2. card公钥证书
3. card将UN用自己的私钥进行签名后发回终端（生成电子签名）
   1. 此步产生动态数据 dynamic Data generated
4. 终端使用内嵌的CA公钥检索bank的公钥
5. 终端使用bank公钥检索card公钥
6. 终端使用card公钥验证电子签名 & 终端数据

### 实际认证&交易详情：What Happens in Detail

![](/static/2021-02-27-04-51-37.png)

* 当卡插入终端时，芯片激活开始协商协议
  * 终端决定最好的协议，存在不同版本的协议
* 处理芯片支持的付款程序列表（根据芯片类型决定，信用卡，储蓄卡）
  * 给持卡人决定，询问确认
* 选择选中的程序应用，从芯片中读取相关信息
* 使用公钥基础设施（证书链？）验证芯片数据
* 检测PIN
  * 如果PIN操作超时，需要检测签名
  * 以前采用纸上签名，现在采用PIN操作
* （Mechant/bank）根据底限、交易类型、PIN码成功与否、有效期等因素，决定是线下审批、线上审批还是拒绝 Decide whether to approve off-line, go on-line or decline, based on floor limits, type of transaction, success of PIN, expiry dates, etc
* 芯片包含额外的银行政策，有机会覆写决策 The chip is given the opportunity to override this decision.
  * containts additional bank policies

---

假设终端为网络在线终端，在线交易

* 在线交易，额外芯片信息会送给bank If the transaction goes on-line then additional chip data is sent to the bank.
  * **银行使用芯片信息（card的公钥）验证card，芯片信息由card的私钥进行签名** The bank uses this data to verify the card. The data is signed by the cards secret key
  * bank将card芯片额外信息用私钥签名后送回card芯片，card利用bank的公钥进行身份认证 The bank sends additional data, signed with its secret key. The card uses this to verify the bank.
  * 可以通过芯片运行脚本，处理银行响应，当卡被盗，PIN冻结时禁用脚本 Part of the response from the bank can be a script which is processed by the chip. This can disable the chip if stolen, unblock the PIN, etc.
* 芯片含有额外信息，银行政策，有机会覆写决策 The chip is asked if it wants to override the decision to approve or decline the transaction.
* 交易发生 The transaction takes place.
* 芯片断点 The chip is powered down.

## 被破解-中间人攻击：Chip and PIN is Broken

![](/static/2021-02-27-05-16-55.png)

Murdoch, Drimer, Anderson and Bond demonstrated a flaw in the Chip and PIN protocol in 2009.  They were able to use a card without knowing the PIN in a man in the middle attack. o They used several cards, entering a PIN of 0000 each time, in their department canteen at the University of Cambridge with the knowledge and permission of the management.  They used the offline PIN verification protocol where the PIN was verified by the card and not the bank.  They built a man-in-the-middle machine with a laptop and special hardware that cost $200.

> 机翻：Murdoch、Drimer、Anderson和Bond在2009年展示了芯片和PIN协议的一个缺陷。 他们能够在不知道PIN码的情况下使用卡进行中间人攻击。 o 他们在剑桥大学食堂使用了几张卡，每次输入的PIN码都是0000，并得到了管理层的许可。 他们使用的是离线PIN验证协议，PIN由卡而不是银行验证。 他们用一台笔记本电脑和特殊的硬件搭建了一台中间人机器达到这种离线攻击，花费200美元。

offline attack - cannot send the info to the bank for digital info

* PIN was verified by the card (but not verified by the bank)
* 特殊的假卡插入终端，真卡PIN插入某部分
* ...

## 协议-认证：Authentication Protocol

![](/static/2021-02-27-05-23-55.png)

* Card->Terminal
  * get the PIN from user, send it back to the card
* terminal->card，3选1
  * PIN
  * user已认证，但未提供PIN，（user签名有效）
  * 交易拒绝 - 读卡器的cancel按钮  transaction rejected
* Card->Terminal（如果PIN提供，card加密信息验证PIN合法性）
  * PIN OK
  * PIN不正确，还可重输x次 PIN Incorrect, try for x(3 in total, incorrect 1 time then 2 more remain) more times

## 中间人：Man in the middle

当terminal->card, here is the PIN,出现中间人

![](/static/2021-02-27-14-40-26.png)

* 中间人网站（运行在电脑上的程序），介于card和终端之间，传递原始信息，直到card请求终端PIN
* MIM攻击 - MIM接收PIN，不直接传递给card，相反，给card&终端发送以下信息
  * MIM->Card:用户通过认证，未提供PIN（使card认为用户通过了认证，比如签属纸质签名）
  * MIM->Terminal: PIN OK(使终端认为，通过了card认证)
* 终端继续交易，因为认为PIN已通过认证（实质没有）
* card同样也认为用户已通过认证（实质没有）

### 中间人攻击存在的原因： Flaws the Made the Attack Possible

![](/static/2021-02-27-14-53-15.png)

错误的PIN欺骗获取bank的签名

:orange: 难以修复的缺陷 These are fundamental flaws which are difficult to fix.

* 因为用户可以通过两种方式两种设备被认证 The user can be verified in two different ways by two different devices
  * card验证PIN
  * Terminal验证签名
* 终端确认结果TVR，存在多种失败mode，但只有1个成功mode(实质有两种成功mode) The Terminal Verification Results (TVR) data format catered for many different failure modes but only 1 success mode
  * It did not report on which method had been successful.
* 该协议（TVR mode）允许许多不同的验证协议 The protocol allowed many different verification protocols.
  * 终端依靠卡上的成功代码，而不知道任何细节 The terminal relied on the success code from the card without knowing any details
* 卡和终端之间的对话没有经过认证，允许中间人攻击。The conversation between the Card and terminal is not authenticated, allowing a man-in-the-middle attack.

# 公钥系统使用-互联网安全：Network Security Using Key Certificates

## TLS

![](/static/2021-02-27-15-06-06.png)

* 传输层安全协议 TLS stands for Transport Level Security
  * The current protocol for internet communication.
* It replaced SSL, Secure Sockets Layer.
  * A number of flaws of SSL were discovered over time

---

描述一下TLS握手协议，在该协议中，客户和服务器就如何在他们之间安全地传输数据达成一致。他们如何能确定对方是真实的？Describe the TLS handshake protocol where a client and a server agree on how to transmit data between themselves securely. How can they be sure the other is genuine?

* 客户端提供一个它所支持的协议列表，服务器选择使用其中的哪一个。在理想的情况下，使用公钥证书，但如果它们不可用，那么它们可以退回到Diffie-Hellman密钥交换的形式。D-H可以只是一个会话（短暂的），也可以是更持久的。The client provides a list of protocols that it supports and the server chooses which of these to use. In an ideal situation a public key certificates are used, but if they are not available then they can fall back on a form of Diffie-Hellman key exchange. The D-H can be for just one session (ephemeral) or longer lasting.

## 握手协议：Handshake Protocol

![](/static/2021-02-27-15-08-40.png)

* Client & Server
* C->S
  * 提供支持的算法信息
* S->C
  * 选择支持的算法或算法子集
  * 给Client提供公钥证书（由CA签发的）
* C->S
  * 提供用Server公钥加密后的UN不可预测数字，给server

---

![](/static/2021-02-27-15-14-24.png)

* server的证书包含服务器名 & 公钥 The certificate contains the server's name and public key
  * 通过CA的私钥加密 It is encrypted with the secret key of a Certifying Authority (CA).
  * client客户端可以用CA的公钥解密该公钥证书签名，获取server公钥&服务器名 The client already has the public key of this CA
  * **master certificate**
* client生成UN不可预测数字
  * 用server公钥加密后的UN，传输给server。如果server是真的，可以用自己的私钥解密获取UN If the server is genuine then it knows the server secret key and so can decrypt UN
  * 如果server是假的，无法获取UN，也无法加密解密任何与cliet之间的流量。If it is not genuine then it cannot get the UN, It cannot encrypt / decrypt traffic with the client.
* <font color="deeppink">CLient&Server都【使用UN生成会话密钥】，用来加密大量数据（对称加密）</font>Both the client and the server use UN to generate session keys for bulk encryption of data
  * AES/3DES based on the algorithm agreed at the start

## 其他方式握手：Alternative Handshakes

如果服务器没有由客户拥有主证书的权威机构签署的证书，那么TLS就必须使用其他证书。If the server does not have a certificate signed by an authority that the client has a master certificate for then an alternative has to be used.

* TLS可以降级协议，强制通信。**最常见的是使用Diffie-Hellman算法，但是很容易受到中间人攻击** The most common is to use Diffie-Hellman. This is then vulnerable to a man in the middle attack.
  * server，client双方创建秘密数，并且创建两个公共数，双方针对秘密数字进行指数计算...Lecture 2
  * 最后双方有共享密钥shared key 可以用于对称加密
  * E可以作为中间人，在AB传输公共数的时候进行篡改
* **如果他们使用Ephemeral D-H密钥，则更加安全**：每个会话都会生成一个新的密钥。It is more secure if they use an Ephemeral D-H key: a new key is generated for each session.
* 如果客户机和服务器之间的连接将**定期使用，则可以提前设置连接参数** If a connection between a client and a server will be used regularly than the connection parameters can be set up in advance
  * 使用预共享密钥(PSK)或安全的远程密码(SRP)(如 Kerberos） Use pre-shared keys (PSK) or a secure remote password (SRP) (such as Kerberos

## 中间人攻击：Man in the Middle(MiM) Attacks

![](/static/2021-02-27-15-54-13.png)

* MiM攻击可以通过使用公钥证书避免，前提是CA可信，安全 A MiM attack can be prevented by using public key certificates, provided the certifying authority is secure
  * 前提client，CA，没有妥协假的master certificate, If the CA is compromised then an attacker can forge their own certificates.
* There are a number of CA's, many of which are weak
* MiM攻击的弱点在于，它必须在给定的时间和仅在2方之间发生，即给定的客户端和服务器。 The weakness of a MiM attack is that it must take place at a given time and between just 2 parties, a given client and server.
  * 可为自动化脚本，程序 But it can be automated

:orange: 两种方式避免MIM攻击

* Certificate pinning
* Certificate Perspectives

:orange: 注意这两种方法可以结合使用

* 第一次通过perspective，保证获取一个可信的公钥证书
* 进行缓存后，每次会话都比较公钥证书和第一次缓存的公钥证书

## 证书绑定 - 避免中间人共攻击：Certificate Pinning

解释PKI和Diffie-Hellman在TLS中的作用。说明在各种通信情况下，如何打败中间人攻击 Explain the role of PKI and Diffie-Hellman in the TLS. Show how a man-in- the-middle attack can be defeated in the various communication scenarios.

* PKI通过证书交换公钥来防止MiM。证书必须是可信的，客户必须有一个公共密钥发行者的根证书。一旦证书被交换，客户端就会生成一个用于批量传输的会话密钥，并用服务器的公钥对其进行加密。服务器通过解密会话密钥来表明它是真实的，而不是仅仅重放一个捕获的公钥证书。PKI protects against MiM by exchanging public keys via certificates. The certificates must be trusted and the client must have a root certificate for the public key issuer. Once certificates are exchanged, the client generates a session key for bulk transmission and encrypts it with the servers public key. The server shows that it is genuine, and not just replaying a captured public key certificate, by decrypting the session key.
* 如果对证书的真实性有一些怀疑，那么可以使用证书钉住。这假定与服务器第一次接触时交换的证书是真实的，所有随后的证书都与之核对。另一种方法是证书透视，让第三方（或不止一个）为客户获得所需的证书。If there is some doubt that the certificate is genuine then certificate pinning can be used. This assumes that the certificate exchanged with the first contact with the server is genuine and all subsequent certificates checked with it. Another approach is certificate perspectives, getting a third party (or more than one) to get the required certificate for the client.

![](/static/2021-02-27-16-04-00.png)

获取服务器证书的可信版本，然后与为每个会话提供的证书进行比较。获取服务器证书的可信版本，然后与为每个会话提供的证书进行比较。Obtain a trusted version of the server certificate and then compare it with the one provided for each session.

* 第一次获取证书时，进行缓存，之后的每一次通信都进行对比
  * 在许多情况下，客户会对第一次去网页时获得的证书进行处理。In many cases the client will treat the certificate obtained during the first visit to a site.
  * 如果某次证书出现了差异，可能存在中间人攻击 If a different one is subsequently provided, then a MiM is probably taking place

## Certificate Perspectives

![](/static/2021-02-27-16-08-29.png)

* **使用第三方(公证人)从不同的 IP 地址获得服务器证书** Use a third party, a notary, to obtain a server certificate from a different IP address.
* MiM 攻击可能不会在到服务器的两个不同连接上同时发生 The MiM attack will probably not be taking place simultaneously on two different connections to the server.
* **如果发生 MiM 攻击，这两个证书可能会有所不同**It is likely that the two certificates will be different if a MiM attack is taking place.
* 为了增加安全性，可以使用来自不同公证人的几种不同证书 Several different certificates from different notaries can be used for added security.

:orange: 如获取giffgaff公钥证书，之后从notary，不同IP在获取一个公钥证书。进行比较是否有差异
