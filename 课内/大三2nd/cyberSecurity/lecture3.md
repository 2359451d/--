# 公钥基础设施：Public Key Infrastructure

## 中间人攻击：Man in the Middle Attacks

![](/static/2021-02-25-15-43-19.png)

* 所有公钥系统都有中间人攻击风险
* 如果B将公钥送给A，E尝试用自己的公钥替换B的公钥
  * 当A发送密文给B，认为用的是“B的公钥”加密，然而此时E可以用自己的秘钥解密该明文
  * E可以二次篡改，并用B的公钥加密后发送给B

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

![](/static/2021-02-25-16-44-37.png)

> Starting from 2005, the liability for fraudulent transactions at the point of sale shifted from the bank to the retailer if the retailer is not using chip and PIN. On the other hand, if the bank does not support chip and PIN then they will be liable for losses. This changed on 1st November 2009, when the banks had to prove that the customer was at fault when money was taken from an account. This was a result in failures of the Chip and PIN system.
> 从2005年开始，如果零售商不使用芯片和个人识别码，**则在销售点进行的欺诈性交易的责任就由银行转移到零售商身上**。另一方面，如果银行不支持芯片和 PIN，那么他们将承担损失。这种情况在2009年11月1日发生了变化，当时银行必须证明客户在从账户中取款时有过错。这是芯片和 PIN 系统故障的结果。

