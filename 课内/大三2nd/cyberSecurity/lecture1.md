# Lecture 1

![](/static/2021-01-25-20-27-27.png)

* keepping a secret
* communicating a secret
* common signle key algo
* single key encryption in Java

what is the goal of cyber security?

content

* [Lecture 1](#lecture-1)
* [一个使用所有最好的加密技术的系统如何仍然是不安全的](#一个使用所有最好的加密技术的系统如何仍然是不安全的)
* [各种场景涉及的安全问题](#各种场景涉及的安全问题)
* [什么是安全工程，它与软件工程有什么相似/不同之处？](#什么是安全工程它与软件工程有什么相似不同之处)
* [资产 & 资产保护机制：Assets & Protection Mechanism](#资产--资产保护机制assets--protection-mechanism)
  * [保密性 & 隐私：Confidentiality & Privacy](#保密性--隐私confidentiality--privacy)
  * [完整性：Integrity](#完整性integrity)
  * [可用性：Availability](#可用性availability)
  * [真实性，可追溯性，不可否认性：Authenticity, Accountability, Nonrepudiation](#真实性可追溯性不可否认性authenticity-accountability-nonrepudiation)
* [威胁：Threats](#威胁threats)
* [漏洞 & 攻击面：Vulnerability & Attack Surface](#漏洞--攻击面vulnerability--attack-surface)
* [保护 & 风险：Protection & Risk](#保护--风险protection--risk)
* [技术解决方案的重要性：Technical Solutions are Essential](#技术解决方案的重要性technical-solutions-are-essential)
* [保密性：How to keep a secret? Techinal solution](#保密性how-to-keep-a-secret-techinal-solution)
  * [The Cast](#the-cast)
  * [记忆-最简单方法/协议： Keeping a secret - Memorise](#记忆-最简单方法协议-keeping-a-secret---memorise)
    * [记忆涉及的威胁：Memorise - Threats](#记忆涉及的威胁memorise---threats)
  * [Paper协议：Keeping a Secret](#paper协议keeping-a-secret)
    * [Paper隐藏 - 另一种协议：Another Protocol - Paper - Hiding and Safe（Hiding in Plain Sight）](#paper隐藏---另一种协议another-protocol---paper---hiding-and-safehiding-in-plain-sight)
    * [Paper加密-另一种协议：Paper Encryption](#paper加密-另一种协议paper-encryption)
  * [Computer File电子文件协议：Keeping a Secret](#computer-file电子文件协议keeping-a-secret)
    * [隐藏文件: Computer File - Hide in Plain Sight](#隐藏文件-computer-file---hide-in-plain-sight)
      * [隐藏文件攻击：Hiding a File - Search Tools](#隐藏文件攻击hiding-a-file---search-tools)
    * [外部存储文件：Computer File - Hiding a File - External Storage](#外部存储文件computer-file---hiding-a-file---external-storage)
    * [隐写术：Steganography - Hiding a File](#隐写术steganography---hiding-a-file)
      * [隐写术相关攻击：Steganography - Attacks](#隐写术相关攻击steganography---attacks)
    * [电子文件加密：Encryption - Computer File](#电子文件加密encryption---computer-file)
  * [保密加密算法协议：Protocol-Secret Encryption Algorithm](#保密加密算法协议protocol-secret-encryption-algorithm)
  * [公共加密密钥协议：Protocol - Public Algorithm, Secret Key](#公共加密密钥协议protocol---public-algorithm-secret-key)
    * [单秘钥加密攻击：Attacking Single Key Encryption](#单秘钥加密攻击attacking-single-key-encryption)
    * [问题-太多需要加密的文件：Problem: Many Secret Documents](#问题-太多需要加密的文件problem-many-secret-documents)
* [通信加密传输：Communicating a Secret](#通信加密传输communicating-a-secret)
  * [安全传输中介协议：Protocol - Secure Transmission Medium](#安全传输中介协议protocol---secure-transmission-medium)
  * [隐藏信息协议：Protocol - Hiding in Plain Sight](#隐藏信息协议protocol---hiding-in-plain-sight)
  * [安全区域加密后不安全介质传输协议：Protocol - Secure Preparation](#安全区域加密后不安全介质传输协议protocol---secure-preparation)
    * [安全区域：Secure Area](#安全区域secure-area)
    * [加密算法：Using Encryption](#加密算法using-encryption)
    * [涉及加密算法分配问题：Encryption Distribution Problem](#涉及加密算法分配问题encryption-distribution-problem)
  * [保密加密算法协议：Protocol- Use a Secret Algorithm](#保密加密算法协议protocol--use-a-secret-algorithm)
  * [单秘钥/对称加密: Public Algorithm & Secret Key](#单秘钥对称加密-public-algorithm--secret-key)
    * [原理 - 秘钥使用： Using a Secret Key](#原理---秘钥使用-using-a-secret-key)
    * [涉及密钥分配问题：Key Distribution Problem](#涉及密钥分配问题key-distribution-problem)
* [常见单秘钥加密算法：Common Single Key  Encryption Algorithms](#常见单秘钥加密算法common-single-key--encryption-algorithms)
  * [DES: data encryption standard](#des-data-encryption-standard)
    * [统计攻击：Statistical Attack](#统计攻击statistical-attack)
    * [DES破解：Breaking DES](#des破解breaking-des)
    * [三重DES：Triple DES](#三重destriple-des)
  * [AES：advanced encryption standard](#aesadvanced-encryption-standard)
    * [密码段连接模式（CBC） & 电子密码本模式（EBC）：Cipher Block Chaining & Electronic Codebook](#密码段连接模式cbc--电子密码本模式ebccipher-block-chaining--electronic-codebook)
  * [数据擦除算法：Erase Algorithm](#数据擦除算法erase-algorithm)
* [【】AES Using JAVA](#aes-using-java)

# 一个使用所有最好的加密技术的系统如何仍然是不安全的

一个使用所有最好的加密技术的系统如何仍然是不安全的 Explain, with examples, how a system that uses all the best encryption techniques can still be insecure.

* 工作人员可能会绕过安全机制来完成他们的工作。他们可能被迫使用强大的密码，并把它们写下来。他们可能会分享密码，以便其他人可以为他们打掩护。Staff may bypass security mechanisms to get their work done. They may be forced to use strong passwords and write them down. They may share passwords so that other people can cover for them.

# 各种场景涉及的安全问题

举例说明在硬件、软件、网络、人员、场地和组织中如何出现安全问题。你的每个例子会影响到CIA的哪些方面？Explain with examples how security problems can arise in hardware, software, networks, personnel, site and organization. What CIA aspects are affected by each of your examples

* 硬件。系统可能出现故障，影响可用性。Hardware: The system may fail, affecting availability.
* 软件。软件可能不按预期工作，无论是故意的还是由于错误的。这将影响CIA的所有方面。Software: The software may not work as intended, either deliberately or through bugs. This would affect all the CIA aspects.
* 网络。流量可能被窥探，影响保密性；流量可能被替换，影响完整性；网络可能被DOS攻击所淹没，影响可用性。Networks: Traffic can be spied on, affecting confidentiality, traffic can be replaced, affecting integrity, and the network can be overwhelmed with a DOS attack, affecting availability.
* 人员。内部攻击会影响保密性和完整性。Personnel: An insider attack can affect confidentiality and integrity.
* 场地。场地可能容易发生自然事件，影响可用性。组织。可能存在不正当的激励机制，即如果决定是错误的，做出决定的人不负责任。这可能会导致钱花在错误的地方，影响CIA的所有方面。Site: The site may be prone to natural events, affecting availability. Organisation: There may be perverse incentives, where those making decisions are not responsible if the decisions are bad. This may lead to money being spent on the wrong things, affecting all CIA aspects.

# 什么是安全工程，它与软件工程有什么相似/不同之处？

什么是安全工程，它与软件工程有什么相似/不同之处？What is security engineering and how is it similar to / different from Software Engineering?

软件工程是一种构建软件的系统方法。安全工程是一种构建安全软件的系统方法。它涉及到识别安全要求，以及误用案例和其他技术。下一步是创建政策以满足需求。最后一步是创建和使用机制，以确保对政策的遵守。Software engineering is a systematic approach to building software. Security engineering is a systematic approach to building secure software. It involves identifying the security requirements, with misuse cases and other techniques. The next step is creating policies to satisfy the requirements. The last step is to create and use mechanism to ensure compliance with the policies.

# 资产 & 资产保护机制：Assets & Protection Mechanism

![](/static/2021-01-25-20-32-53.png)

* 资产是指需要保护的东西，通常是数字化的，比如文件。
* 有些资产，如钥匙和密码，对网络安全很重要，但**不以文件的形式存储**
* 多种不同方式/机制保护资产 different protection mechanism for assets
  * **保密性** Confidentiality
  * **可用性** Availability
  * **完整性** Integrity
  * **真实性** Authenticity
  * **可审性/可追溯性** Accountability
  * **不可否认性** Non-repudiation

## 保密性 & 隐私：Confidentiality & Privacy

![](/static/2021-01-25-20-39-26.png)

* 保密性 - 意味着访问控制机制
  * 必须识别用户
  * 进行用户认证
  * 授权用户访问各种资产，访问权限可以受控，如读写，执行权

:orange:Privacy

* 隐私指, 对**个人信息**的保密  Privacy is the confidentiality of personal information

:orange: 例子

* 使用密码控制访问
* 加密文件，以确保信息的保密性

## 完整性：Integrity

![](/static/2021-01-25-20-43-20.png)

> 确保没有任何东西/信息丢失或删除。(无论是意外或故意删除/修改/丢失) Ensure nothing is lost or deleted.(Either accidentally or deliberately)
> 即，确保没有任何改变 Make sure nothing is changed.

:orange: 例如：

* 使用消息摘要来检测文件是否被更改。
* 使用公钥证书进行网络通信

## 可用性：Availability

![](/static/2021-01-25-20-46-24.png)

> make sure the document is available 确保文件信息可用

* 系统需要有满足以下需求的能力
  * 公平分配资源
  * 容错能力 & 故障恢复能力

:orange: 例子

* 保证最新备份
* 防止拒绝服务攻击

## 真实性，可追溯性，不可否认性：Authenticity, Accountability, Nonrepudiation

![](/static/2021-01-25-20-50-17.png)

* 真实性 authenticity
  * make sure document's authenticity: the document has not been replcaed by another one
  * authenticate people: person accessing the system is who say they are
* 可追溯性 accountability
  * 提供审计记录追踪
  * 例如，记录对资产的所有访问
* 不可否认性 non-repudiation
  * The author / owner of a document cannot say it was not them，不能否认签名（they cannot repudiate the signature）

# 威胁：Threats

![](/static/2021-01-25-20-54-57.png)

* 我们保护资产不受威胁影响 What are we protecting assets from? Threats
* <font color="deeppink">不同类型的资产安全，受不同威胁</font> Different types of asset security are subjects to different threats.
  * 如：针对保密性的威胁与对可用性的威胁不同，(所需要采取的保护措施也不同）
* <font color="deeppink">在保护资产时，我们需要考虑所有可能的威胁</font> When protecting an asset, we need to consider all possible threats.
  * 如果攻击者想到了我们没有想到的威胁，那么他们就会得逞
* <font color="blue"> 有许多不同的技术来确保我们已经**考虑到所有的威胁**</font> --- threat modelling
  * Standard lists of threats.
  * Standard techniques for dealing with them.

# 漏洞 & 攻击面：Vulnerability & Attack Surface

![](/static/2021-01-25-21-02-06.png)

* 保护资产的方式/机制不同，导致的漏洞也不同
* 我们可以通过两种不同的方式来**检查系统的安全性**
  * 从攻击者的角度来看。攻击者的目标是什么？攻击者的目标是什么？他们能否实现这些目标？
  * 从维护者的角度来看。系统有哪些漏洞？

:orange: attack surface - 攻击面

* 所有的漏洞的集合 --- 攻击面 All the vulnerabilities collected together are called the attack surface
  * <font color="deeppink">作为防御者，我们要减少攻击面</font>

# 保护 & 风险：Protection & Risk

![](/static/2021-01-25-21-21-54.png)

* risk is a kind of **dealing costs** --- 保护我们的资产不受威胁，这就引出了对风险的讨论
  * 保护是有成本的
  * **一项资产的价值可能低于保护它的成本(预算有限制，需要结合实际)**
  * 某些形式的保护措施可能比其他形式的保护措施更便宜

:orange: 风险是指事情发生的可能性，以及攻击成功后的影响 Risks involve the probability of something happening, together with the effect of the attack succeeding.

:orange: 应对风险的措施 techniques for dealing with risks (要考虑costs)

* 完全保护，防止攻击发生 【贵，重要文件】
* 降低风险，使其发生的可能性降低
* 减轻风险，降低影响
* 投保，降低罕见昂贵事件的成本
* 倾销责任，别人支付费用 【常用】

# 技术解决方案的重要性：Technical Solutions are Essential

![](/static/2021-01-25-21-28-38.png)

* **牢不可破的加密 unbreakable encryption**来保守秘密，确保数据不被更改
  * 没有密钥就无法破解算法。
  * 密钥必须保密
* **数字签名 digital signatures**，让法律上可执行的合同
  * 使签名无法被伪造
* **安全的信息摘要 secure message digests**，以提供文件指纹，而不泄露文件内容
  * 所以两个不同的文档不能有相同的消息摘要
* **安全协议 secure protocols**，以确保正确使用加密签名和消息摘要的基本构件
  * 这样就不可能绕过密钥的使用

---

technical solution is essential but note enough

![](/static/2021-01-25-21-35-27.png)

* 用户造成的问题
  * 用户可能不遵守安全政策（密码组合规定...）
  * 组织可能会制定一些用户难以使用的政策
  * 開發人員在建立系統時，可能沒有遵守安全指引
  * 监管机构可能没有提供适当的政策和规则，或者提供了政策但可能没执行它们

:orange: 因此，也需要考虑社会技术系统 Need to consider socio-technical systems

* 考虑任何系统的技术方面的同时，也要考虑人造成的问题 Consider people as well as the technical aspects of any system

# 保密性：How to keep a secret? Techinal solution

![](/static/2021-01-25-21-42-17.png)

网络安全的技术方面**使用算法来保证信息安全**。

* 不同的协议以不同的方式使用相同的算法
* 许多协议通过将**问题转化为更简单的问题**来解决问题
  * 相当复杂的协议是用较简单的协议建立起来的

先从保守秘密的问题说起：保密性 **keeping** a secret: confidentiality

* 只有我们才会知道的东西。 然后我们会考虑将一个秘密传达给别人
  * 本课只涉及如何keeping
* 这也是一个保密性问题

## The Cast

![](/static/2021-01-25-21-47-38.png)

* 在描述协议时，常规的做法是使用以下字符
  * Alice, Bob, Carol and Dave have secrets to share.
  * Eve the eavesdropper tries to find out the secrets and cause other problems.
  * Trent is a trusted third party.

## 记忆-最简单方法/协议： Keeping a secret - Memorise

![](/static/2021-01-25-21-49-14.png)

Our **first protocol** is to **memorise** the secret

* 只有当秘密相当短且容易记住时适用 This is only appropriate if the secret is fairly short and easy to remember
  * 可以是密码或加密密钥
  * 不应该是比特币ID

### 记忆涉及的威胁：Memorise - Threats

what is the threats involved in memorise protocol?

![](/static/2021-01-25-21-51-06.png)

* **忘记秘密** Forgetting the secret
  * 如果概率很高，秘密的价值很高，那么我们
可以通过**写下秘密**来减轻这种威胁【另一种协议】If the probability is high and the value of the secret high then we can mitigate this threat by **writing the secret down**
* **欺骗** Trickery
  * 比如在钓鱼攻击中输入密码
  * 人为因素导致的问题 human factors problem
* **胁迫** Coercion
  * 有人强迫你透露秘密 someone forces you to reveal your secret，**也可以看为人为因素导致的问题** human factors problem
  * 技术解决方案：**求救密钥** technical solution to this problem: **a distress key**
    * 系统可以设置为采取两个密钥或密码
    * 一把是真实的钥匙【real key】，另一把则会产生看似真实但虚假的信息【distress key】
    * 这依赖于一种秘密的加密算法 relies on a secret encryption algorithm

## Paper协议：Keeping a Secret

![](/static/2021-01-25-21-58-33.png)

如果秘密**太长/太复杂，记不住**，可用该协议 --- 写在一张纸上

* 秘密的长度是有限制的
* 如果输入在电脑中，使用起来会很不方便

:orange:该协议涉及保密性 confidentiality

* 产生的保密性威胁，取决于**攻击者的物理访问**The confidentiality threat relies on the attacker having physical access
  * 可以搜查我们的家或办公室

:orange:该协议涉及可用性 availability

* 可用性威胁是丢失这张纸 An availability threat is losing the piece of paper
  * 可以通过备份、制作副本来缓解该威胁 This can be mitigates by backups, making a copy
  * 所有的副本都需要被保护 All copies now need to be secured.
  * 必须跟踪所有副本 We must keep track of all copies
  * 不再需要时销毁所有副本 destroy all copies when they are no longer needed

---

### Paper隐藏 - 另一种协议：Another Protocol - Paper - Hiding and Safe（Hiding in Plain Sight）

如果**攻击者有物理访问权限**，可以通过 "隐藏在众目睽睽之下 "来缓解该问题造成的威胁 If the attacker has physical access, this can be mitigated by **Hiding in Plain Sight**.

![](/static/2021-01-25-22-15-36.png)

* 可能还有很多其他纸片。
* 攻击者必须在看到秘密时识别出它。
* 攻击者必须知道这个秘密的存在。
* 但我们可能无法自己找到它!

:orange: 坚持搜索可以发现这个秘密 A determined search can uncover the secret

* 这可能会耗费相当大的人力 It may be quite expensive in manpower.

:orange: 另一种协议是把这张纸锁在保险柜里 Another protocol is to **lock the piece of paper in a safe**.

* 这使用的是比较**简单的协议**。记忆 This uses the simpler protocol: Memorise
  * 协议的结合 - 记忆 & keep secret in a safe
* 必须记住保险箱的密码 The combination to the safe must be remembered

### Paper加密-另一种协议：Paper Encryption

another protocol based on writing down on a paper

![](/static/2021-01-25-22-21-22.png)

* 秘密可以加密后，写在纸上
  * 加密协议后面会介绍。
* 纯文本版本的秘钥必须擦除(paper版本-烧，或者碎纸机)
* 碎纸机效果较差
  * 纸条可以重新组装，但目前是非常耗费人力的。
  * 纸条可以数字化，并使用jigsaw puzzle algorithms重新组装成文件

## Computer File电子文件协议：Keeping a Secret

新协议

![](/static/2021-01-25-22-33-06.png)

:orange:大多数文件都是在计算机上编写的，这就引入了一些可以被攻击者利用的**漏洞** Most documents are prepared on a computer, which introduces a number of vulnerabilities that can be exploited by attackers. (hard to keep a secret)

* 用于创建文件的程序可能会**定期进行备份**。所以有很多不同版本的文件需要被保护 The program used to create the document may make periodic backups, and so there are many different versions of the file that need to be protected
  * 很容易创建文件的副本 It is easy to create copies of the file
  * 删除的文件可以恢复 Deleted files can be recovered
    * 删除一个文件只是把组成该文件的块放到了
循环使用，以便以后作为另一个文件的一部分重新使用。Deleting a file just puts the blocks making up the file into
recycling, to be reused as part of another file later on
    * 实际的数据仍然存在，直到它被覆盖 The actual data will still be there until it is overwritten

### 隐藏文件: Computer File - Hide in Plain Sight

电子文件协议相关，hide in plain

![](/static/2021-01-25-22-53-39.png)

:orange: 这个协议依赖于计算机上有大量的文件This protocol relies on there being a large number of files on the computer.

* **文件可以存放在一个不知名的目录中**。The document could be stored in an obscure directory
* 它可以**依靠密码访问计算机** It could rely on password access to the computer
  * 我们将在后面考虑系统安全问题。

:orange: 现在，假设攻击者已经获得了对计算机的访问权限 For now, assume an attacker has already gained access to the computer 【threats】

* 这可能是通过物理访问计算机 This could be through physical access to the computer
* 可能是通过从网络上的任何地方进行远程访问 It could be through remote access from anywhere on the network

#### 隐藏文件攻击：Hiding a File - Search Tools

![](/static/2021-01-25-22-58-41.png)

 :orange: 计算机上的数据的主要问题是，攻击者可以**使用自动化工具进行攻击** The main problem with data on a computer is that an attacker can use automated tools to make the attack.

* 数据科学工具可以根据内容对自然语言文件进行分类。Data science tools can classify natural language files based on content
* 图像文件也可以进行分类。Image files can also be classified.
* **攻击者可以搜索特定的内容，收集所有符合搜索条件的文件** The attacker can search for specific content, collecting all files matching the search terms
  * 然后需要人工调查 Human investigation is then needed
  * 数据科学的进步可以**使搜索更加具体，减少需要人类查看的文件数量** Advances in data science can make the search more specific, reducing the number of files that have to be looked at by a human

### 外部存储文件：Computer File - Hiding a File - External Storage

另一种hiding file的方式

![](/static/2021-01-25-23-22-16.png)

* 文件可以**存储在外部文件系统**中，如U盘。
* 用于准备的计算机上的原始文件必须被删除 The original file on the computer used to prepare it must be erased
  * 也包括任何备份，包括文件编制程序自动制作的备份 Also any backups, including those made automatically by the document preparation program

:orange:攻击者现在需要对存储设备进行**物理访问**

* 一旦实现了这一点，那么这就变成了隐藏在众目睽睽之下的协议 Once this is achieved, then this becomes the hide in plain sight protocol(有效)
  * 我们的USB文件放在哪里

### 隐写术：Steganography - Hiding a File

隐藏文件方式

![](/static/2021-01-25-23-32-17.png)

:orange: 隐写的意思是秘密写作

* **在现代计算机环境中，它意味着将一个秘密文件隐藏在另一个文件中** In a modern computer setting it means hiding a secret file inside another file
  * **有效载荷payload是秘密文件** payload is the secret document
  * **cover object对象是一个没有秘密信息的无关文件** The cover object is an innocent file with no secret message
* **有效载荷payload（秘密文件）与cover object对象相结合，产生隐写对象**The payload is combined with the cover object to produce the stego object

:orange: 图像和视频文件是很好的掩护对象，因为它们是大文件，其精确度超过了需要 Image and video files make good cover objects because they are large files with more precision than is needed

* 小的变化可能很难被发现 Small changes can be hard to notice
* **需要特殊的程序来插入和提取有效载荷** Special programs are needed to insert and extract the payload

#### 隐写术相关攻击：Steganography - Attacks

![](/static/2021-01-25-23-37-38.png)

* 假设原始文件已经被删除
* 如果攻击者怀疑有一个文件使用了隐写术，那么有程序，以寻找使用该技术的迹象
  * 图像中颜色字节的直方图。一些隐写算法以标准的方式扭曲字节值
* **尝试一系列标准的隐写程序/算法，尝试并提取出有效载荷** Try a range of standard steganography programs to try and extract the payload
* 一个更普遍的攻击是扭曲所有发布到社交媒体系统上的图像（例如），**摧毁任何有可能隐藏了的有效载荷** A more general attack is to distort all images posted to a social media system (for example), **destroying any possible hidden payload**
  * facebook就是这样做的
  * 这是对可用性的攻击 This is an attack on availability

### 电子文件加密：Encryption - Computer File

![](/static/2021-01-25-23-46-27.png)

🍊 加密程序将明文作为输入，并产生密文作为输出 An encryption program takes plaintext as input and produces ciphertext as output
:orange: 解密程序将密文作为输入，并将明文作为输出 A decryption program takes ciphertext as input and produces plaintext as output

* 解密必须撤销加密效果 Decryption must undo encryption
* 加密后解密，必须产生与原始输入相同的输出 Encryption followed by decryption must produce the same output as the original input
* 原始明文文件必须被删除 The original plaintext document must be erased
  * 包括所有副本和备份 Including all copies and backups

:candy: 这个协议有时间限制的漏洞 This protocol has a time limited vulnerability

* 明文文件存储在文件系统中时 While the plaintext document is in the file system

## 保密加密算法协议：Protocol-Secret Encryption Algorithm

另一种协议(not very good,有几种保密性威胁)
![](/static/2021-01-25-23-51-26.png)

:orange: 加密和解密算法的细节（位置）是保密的

:candy: 威胁/攻击：（攻击者）找到算法 Threat: finding the algorithms

* 这些**算法**将是**计算机程序**
* 它们**不能被加密**，因为它们必须在计算机上运行

:orange: 该协议【保密加密算法协议】对其算法采用隐藏在众目睽睽之下的协议【hiding in plain sight】 This protocol uses the **hiding in plain sight** protocol for the algorithms

* 同样的威胁和对策也适用

:candy: 威胁/攻击：猜测所用算法 Threat: guessing the algorithm used.

* 大多数人不能发明一个好的新算法
* **在自制算法中，使用伪随机数生成器的运行密钥密码是非常常见的**  A running key cipher using a **pseudo-random number generator（to generate pseudo-random number bit）** is very common among **home-made algorithms**

## 公共加密密钥协议：Protocol - Public Algorithm, Secret Key

![](/static/2021-01-26-00-02-34.png)

:orange: 加密和解密算法是公开的
:orange: 加密和解密需要一个额外的参数，即密钥

* **只知道公开算法不知道秘钥是没有用的** Knowledge of the algorithm without the key is useless
* 为了安全起见，算法可以进行**同行评审** The algorithms can be peer-reviewed for safety
  * 很容易发明一个差的算法，所以在差的算法被使用之前，专家的审查会发现缺陷 It is easy to invent a poor algorithm, so scrutiny by experts will reveal the flaws before the poor algorithm is used

:orange: 这个协议就简化为密钥的 "保密 "问题

* "how to keep a key secretly"

### 单秘钥加密攻击：Attacking Single Key Encryption

![](/static/2021-01-26-00-24-51.png)

:orange: 加密算法应该不会被破解，那么又是如何攻击它们的呢？主要有四种方式 Crypto algorithms should not be broken, so how can they be attacked

* **攻击算法** Attacking the algorithm
  * 算法可能存在缺陷，可以被利用 The algorithm may have flaws that can be exploited.
* **攻击系统** Attacking the system
  * 这主要包括试图找到密钥。可能在进入、传输中或内存中暴露 This mainly involves trying to find the key, which may be exposed on entry, in transit or in memory.
* 当密钥长度过短时的**暴力破解攻击** Brute force attacks when the key length is too short
  * 这包括 尝试所有可能的密钥 This involves trying all possible keys.
* 利用密钥空间的熵 Exploiting the entropy of the key space
  * 用户可能选择了弱和可预测的键。试用常用键的字典 Users may choose weakand predictable keys. A dictionary of common keys is tried

### 问题-太多需要加密的文件：Problem: Many Secret Documents

1个单秘钥协议对应1个文件，问题：用户通常有许多想要保密的文件

* **标准协议是对所有文件使用相同的密钥** The standard protocol is to use the same key for all documents
  * 这些文件可能都存储在一个加密的文件系统中 They may all be stored in an encrypted file system
* **如果可以从一个文档中找到密钥，就有可能造成漏洞** This is vulnerable if it is possible to find the key from knowledge on one document
  * 该秘钥明文可能还可用于已知的明文攻击 The plaintext may also be available for a known plaintext attack
  * 其中一个文档可能具有已知的特殊结构 One of the documents may have a known special structure

:orange:  由于人为因素，很难拥有许多不同的密钥 Having many different keys fails for human factor reasons

# 通信加密传输：Communicating a Secret 

![](/static/2021-01-27-01-05-47.png)

:orange: 通信中产生的保密相关问题

* 通常为**保密性问题**，confidentiality problem
* 也可以为**完整性问题**integrity problem
  * 通信其中一方过程中改变了信息
* 还可为**可用性问题** availability problem
  * 通信中其中一方被动拒收了信息（其他因素导致的）

## 安全传输中介协议：Protocol - Secure Transmission Medium

![](/static/2021-01-27-01-17-53.png)

其中一个协议，

* 双方在安全的地点，房间见面
  * “**选择一个安全传输介质**” --- 地点

:orange: 不足 --- **电子传输很容易被截取**

* 转发器可以连接到以太网电缆，并对流量进行分析
* 电子邮件被存储并可在以后进行分析（电子邮件服务器缓存）

:orange: 这个协议，适用场景

* spy场景，alice获取信息后在安全地点将信息告知bob
* 不适用于购物情景，因为网上购物不会存在线下见面交易

---

举出一些安全传输媒介的例子。这种加密方法的局限性是什么？Give some examples of a secure transmission medium. What are the limitations of this approach to encryption?

* 两个人可以在一个隔音的房间里见面。他们必须确保便携式电脑，如手机，没有人在听。应该没有窗户，以避免读唇语。这很难实现，只在有限的情况下实用。Two people can meet in a soundproof room. They have to make sure that portable computers, such as mobile phones, are not listening. There should be no windows to avoid lip reading. This is difficult to achieve and is only practical in a limited number of cases.

## 隐藏信息协议：Protocol - Hiding in Plain Sight

![](/static/2021-01-27-01-23-16.png)

a way to "hide" the info

* alice send bob an email(不安全) or phone him
* 大量的数据意味着消息可能被忽略(won't be noticed)
  * 如果自动工具没有发现它们的可疑之处 --- 对于email纯文本的检测很简单
  * 而且Alice和Bob都没有被监视（如果被监视了可以通过某种方法对整个通信进行监听）

email vs phone

* plaintext & written down before analysed
* 这两种方法都不够安全，因为automatic tool还是能提供方法分析

## 安全区域加密后不安全介质传输协议：Protocol - Secure Preparation

![](/static/2021-01-27-01-32-35.png)

* Alice提前将信息在**安全区域**加密 secure area
* 通过**不安全的介质/中介**进行传输（可假设为email）
  * 假设Eve可以窃听该数据
* Bob在安全区域进行解密
* 加密后解密抵消效果

### 安全区域：Secure Area

![](/static/2021-01-27-01-35-51.png)

* 不要把明文放在分布式文件系统上 Don’t put the plaintext on a distributed files system
  * 它会通过不安全的以太网电缆传到另一台电脑上 It will travel to another computer via an insecure Ethernet cable
* 注意击键记录程序和其他间谍软件 Watch out for keystroke logging programs and other spyware
* 任何连接到互联网的计算机都有可能被感染 Any computer connected to the Internet can potentially be
compromised
  * 使用不与任何东西连接的独立计算机 Use a stand-alone computer not connected to anything
* 加密后的信息必须传输到面向网络的电脑上 The encrypted message will have to be transferred to a network-facing
computer

### 加密算法：Using Encryption

![](/static/2021-01-27-16-09-28.png)

how protocol works - encrypted secure area & transferred by an insecure medium 在

* left line - alice secure area
* right line - bob secure area
* eve - can intecept the communication

:orange:过程

* plaintext is secured by encryption algorithm
* then is sent to Bob, but since the medium is insecure so **Eve can intecept it(ciphertext)**
* Bob uses decryption algo to decrypt and get the plaintext

### 涉及加密算法分配问题：Encryption Distribution Problem

![](/static/2021-01-27-16-20-09.png)

:orange: 该协议产生的问题（事先加密，后不安全传输） --- 加密算法分配问题

* Alice和Bob需要**知道如何加密和解密明文**(只有双方知道，如何分配加密算法)
  * Bob怎么知道是否正确解密？
* **解决**
  * 他们必须使用一种**兼容的方法**
  * 他们必须通过**不同的协议**来交流这些信息
  * 他们可能会在一个**安全区域**见面

:orange: 该解决方式适用于spy情景，但不适用于互联网购物

* 因为需要在购物之前见面

## 保密加密算法协议：Protocol- Use a Secret Algorithm

使用秘密算法进行加密和解密有什么问题？什么是更好的解决方案？What is wrong with using a secret algorithm for encryption and decryption? What is a better solution?

算法的发明者要承担个人风险。该算法不会受到广泛的审查，很可能包含弱点。最好是有一个带有秘密钥匙的公开算法。The inventor of the algorithm is at personal risk. The algorithm will not be subject to a wide review and may well contain weaknesses. It is better to have a public algorithm with a secret key.

---

![](/static/2021-01-27-16-38-57.png)

:orange: 保密加密算法的实现细节 keep the details of the algorithm secret（use secret algorithm）

* **缺点** bad for
* **算法设计者**知道这个秘密，可能会有**人身危险** The algorithm designers know the secret and may be physically at risk
  * 胁迫的威胁 Threat of coercion
* **公开算法的同行审阅可以减少算法缺陷** Peer review of a public algorithm reduces flaws
  * 很容易自欺欺人地认为算法比实际情况更安全（如果不进行审阅）It is easy to fool oneself that an algorithm is more secure than it really is （without peer review）

## 单秘钥/对称加密: Public Algorithm & Secret Key

单一密钥加密是如何工作的，它的主要问题是什么？How does single key encryption work and what is the major problem with it?

* 算法是公开的，而密钥是秘密的。交易双方都必须知道秘密密钥，这导致了密钥分配问题。密钥也很难记住，所以必须储存起来以备将来使用，这就导致了密钥储存问题。The algorithm is public while the key is secret. Both parties to the transaction have to know the secret key, leading to the key distribution. Keys are also hard to remember and so must be stored for future use, leading to the key storage problem.

---

> 加密解密使用相同密钥算法，且加密解密算法细节公开 Details of the encryption and decryption algorithms are public

![](/static/2021-01-27-17-00-58.png)

* 具有一个参数，**key 秘钥**，进行保密
* 如果只知道公开的加解密算法，不知道秘钥也无法获取明文  Knowledge of the algorithm is useless without the key

:orange: 又称为**单秘钥加密 single key encryption/对称加密 symmetric encryption**

### 原理 - 秘钥使用： Using a Secret Key

![](/static/2021-01-27-17-21-34.png)

:orange: 步骤

* 使用**秘钥secret key， & 加密算法**（E，Encryption algorithm）加密明文，得到密文（C，ciphertext）
* 密文传送给Bob，ciphertext is sent to Bob
* 秘钥sent by Alice to Bob
* Bob使用**秘钥 & 解密算法** 得到明文 plain text

### 涉及密钥分配问题：Key Distribution Problem

思考：单秘钥加密\解密涉及的问题？

* 更好的解决方法 - 非对称加密

![](/static/2021-01-27-17-30-17.png)

* **(加密算法分配->)密钥分配问题 & 秘钥存储问题** key distribution problem & key storage problem
  * <font color="deeppink">秘钥必须以安全形式存储</font> key must be stored safely

:orange: 先前协议需要考虑，如何安全分配算法 The problem of transferring data between Alice and Bob has been transformed into an easier problem.

* 对称加密只需要考虑如何安全传输秘钥 The problem of transferring a key

:orange: 相同密钥可以用于多个不同通信会话中 The same key can be used for several different communication sessions

* 双方只需要传输共享一次 It just needs to be shared once
* 传输的秘钥通常比密文更短

:candy: 使用哪种协议进行**秘钥传输**会更简单？which protocol to be used for transferring the key?

* have a meeting
  * 针对某些场景可能不适用（互联网购物）
* **hide in plain sight**
  * 如，用某种安全形式的email进行密钥传输

# 常见单秘钥加密算法：Common Single Key  Encryption Algorithms

:orange: 常见单秘钥加密算法 common single key encryption algorithms

![](/static/2021-01-27-17-55-46.png)

* **DES** - data encryption standard
* **AES** - advanced encryption standard
* Also consider an erase algorithm

## DES: data encryption standard

想象一下，你的任务是设计一个基于DES芯片的硬件加密设备。你的公司预计，一个资金雄厚的组织将认真尝试破解你的设备所加密的数据。你将如何选择使用DES芯片？Imagine you have the task of designing a hardware encryption device based on DES chips. Your company anticipates that a well funded organisation will make a serious attempt to break the data encrypted by your device. How would you choose to employ the DES chips?

DES本身是相当弱的，因为它的密钥很短。它可以被修改为使用更长的密钥，因此可以使用一种组合方法，如双密钥三重DES或三密钥三重DES。DES itself is rather weak because of the short key. It can be modified to use a longer key and so a combination method such as two key triple DES or three key triple DES could be used.

---

![](/static/2021-01-27-18-26-24.png)

* **块密码/分组加密 block cipher**
  * **数据被分成若干块，每块长64位(不是对数据的每个字符进行加密)** The data is divided into blocks, each 64 bits long
  * <font color="deeppink">最后一个数据块可能会很短（小于64位），所以**填充**，将其扩展到64位</font> The last block may often be short (less than 64 bits) and so is **padded** to expand it to 64 bits
  * 为什么使用块密码？ --- <font color="blue">这使得**统计攻击**变得更加困难，因为它不太可能是两个区块将是相同的</font> This makes a **statistical attack** harder because it is less likely that two blocks will be the same
  * <font color="deeppink">将明文分成多个等长的块（ block ），然后用相同的一个密钥，对每个块进行加密，得到密文块。</font>
* 它始于IBM的Lucifer项目
  * 几个小的变换一个接一个的重复。Several small transformations are repeated one after the other.
  * 每个小的变换都有一个不同的子密钥(主密钥偏移几位得来)，由一个主密钥生成。Each small translation has a different sub-key, generated from a master key
  * master密钥长度为128位 It had a master key length of 128 bits

---

![](/static/2021-01-27-18-50-41.png)

NSA（美国国家安全局）对《路西法》稍作修改。并在1977年被采用。

* 他们将密钥长度**减少到56位（原128），使暴力破解变得更容易**。They reduced the key length to 56 bits, making a brute force attack easier
* 改进优点 -- **它使用了非常简单的操作，所以它的速度很快**，很容易在硅中实施 It uses very simple operations so that it is fast and easy to
implement in silicon
* NSA没有公布其安全性分析，鼓励一些人认为他们已经插入了后门 The NSA did not publish an analysis of its security, encouraging some to think that they had inserted a backdoor
  * 没有发现后门，但NSA后来说，他们已经改变了算法，以防止差分密码学。No back door has been found but NSA later said that they had changed the algorithm to protect against differential cryptography
  * 竞争对手的算法，如欧洲的IDEAL，后来被发现容易受到差分密码学的影响（详情不在此赘述）Rival algorithms, such as IDEAL in Europe, were later found to be vulnerable to differential cryptography

### 统计攻击：Statistical Attack

为什么使用块密码？ --- <font color="blue">这使得**统计攻击**变得更加困难（块密码避免该攻击），因为它不太可能是两个区块将是相同的</font> This makes a **statistical attack** harder because it is less likely that two blocks will be the same

![](/static/2021-01-27-18-38-58.png)

> 统计攻击试图寻找单字母、双字母（digram）和三字母（trigram）组合频率。A statistical attack tries to look for frequencies of single letter, double letter (digram) and triple letter (trigram) combinations

* **明文中的频率会有很大的不同** The frequency in plain text will vary greatly
* **这可能会导致密码文本中的频率变化，从而帮助攻击者** This may cause varying frequencies in the cipher text and can help the attacker

:orange: 统计攻击在计算机前的算法中是一个严重的问题，但现代技术在很大程度上防止了这种攻击 Statistical attacks were a serious problem in pre-computer algorithms but modern techniques largely prevent them
o **在加密前进行压缩**：压缩后的数据看起来更加随机 Compressing before encryption: compressed data looks more random
o **块密码**的使用 The use of block ciphers
o **密码块链和初始化向量的使用** Cipher block chaining and the use of an initialisation vector

### DES破解：Breaking DES

目前大多DES都被替换

![](/static/2021-01-27-18-56-08.png)

**56位的短密钥长度（手动设置的56bit）**使得DES**很容易受到暴力破解**，所有密钥都会被尝试。($2^56$)

* 1997年1月29日，RSA组织提供了一个10000美元的奖金给第一个找到密文密钥的人，他们被提供3个明文块(一个已知的明文攻击)并且需要找到秘钥
  * Rocke Versur在1997年5月25日领取了奖金
* 在1976年，Hellman和Diffie估计要花费2000万美元才能完成。制造一台特殊用途的机器，在1天内破解了DES
* 1998年，电气前沿基金会制造了一台DES破解机. 它可以在9天内搜索所有可能的密钥
* 所以DES存在了20年才变得容易破解

### 三重DES：Triple DES

![](/static/2021-01-27-19-02-26.png)

在DES上已经投入了大量的资金，以努力改进它的使用方式

* **通过使用两次不同的密钥，DES算法的密钥长度可以增加一倍（2*56=112bit）** The key length can be doubled by using the DES algorithm twice with 2 different keys.
* <font color="deeppink">这很容易受到中途相遇攻击</font> This is vulnerable to a meet-in-the-middle attack 【攻击者先获得几组明文密文。顾名思义，取一组(明文，密文),枚举所有可能的key1，对明文加密得到中间结果，这所有中间结果保存起来；也枚举key2，对密文解密，得到中间结果，和保存起来的中间结果比较，如果有match的，那认为已经找到这2个key了】

:orange: 目前使用的都为 Triple DES

* <font color="deeppink">三密钥三层DES（3*56=168bit）不容易受到攻击，其密钥长度为168位</font> Three key triple DES is not vulnerable and has a key length of 168 bits
* `C = EK3(DK2(EK1(P)))`，（3DES加密过程）
  * 其中E是加密，D是解密
  * K1加密，K2解密，K3加密，3个Key=168bit long
  * <font color="blue">为什么中间步骤用K2解密？ --- 使用这个顺序是为了使它能**适用于只用一个相同密钥K加密的旧数据(3DES适用于普通DES)**（K=K1=K2=K3）</font> This order is used so that it will work with old data encrypted with just one key K (K = K1 = K2 = K3)
  * `C = EK(DK(EK(P))) = EK(P)`，而，如果K相同，最后明文相当于只加密了一次，因为前两次加密解密效果相互抵消【好处：三重DES对DES具备向下兼容性】 since one E D pair cancels out

## AES：advanced encryption standard

描述选择AES算法的过程与导致DES的过程有什么不同。新的过程是更好还是更坏？AES在哪些方面比DES好？Describe how the process of choosing the AES algorithm was different from the one that led to DES. Was the new process better or worse? In what ways is AES better than DES?

DES是秘密委托的，而导致AES的过程是公开的。因此，尽管没有发现DES的后门，但人们更相信AES的过程更安全。DES的主要问题是密钥长度太短，AES已经解决了这个问题。 DES was secretly commissioned, while the process that led to AES was public. There is thus more confidence that the AES process is more secure, even though no backdoors in DES have been discovered. The main problem with DES was the short key length, which has been fixed with AES.

---

history

![](/static/2021-01-27-19-20-33.png)
![](/static/2021-01-27-19-23-10.png)

* 美国国家标准与技术研究院： 一旦DES的缺陷显现出来，就开始寻找其替代品
* **与DES不同，AES的设计是公开进行的** The design of AES was performed in public, unlike DES
  * 邀请了世界各地的参赛者。
  * 算法和分析是公开的。
  * 邀请任何组织提出意见。
* 申请者减少到5个，进行最后的评估

---

![](/static/2021-01-27-19-27-02.png)

> 这是一个块大小为128、192或256位(可选择)的【**块算法**】，密钥长度为128、192或256位（可选择）This is a block algorithm with block sizes of 128, 192 or 256 bits and key lengths of 128, 192 or 256 bits.

* 128-128 pair最快
* 它在回合中重复几个简单的步骤，每一回合都有它自己的子密钥，这些子密钥来自一个主密钥，就像 DES 一样。It repeats a few simple steps several times in rounds and each round has its own sub-key generated from a master key, just like DES
* **AES加密和解密算法是相互关联的，但又是不同的**
  * **DES** 解密算法使用了与加密相同的步骤，只是顺序相反
  * **DES**使用查找表(看起来像随机数字) DES used lookup tables to what seemed like random numbers The DES decryption algorithm used the same steps as for encryption, just in the reverse order.
  * **AES**
    * **它使用简单的数学运算** uses simple mathematical operations.
    * 快 It is fast
    * 它可以被分析（因为使用了数学运算） It can be analysed.

### 密码段连接模式（CBC） & 电子密码本模式（EBC）：Cipher Block Chaining & Electronic Codebook

:orange: **一个数据文件有许多块，所有的块都必须加密**。 A data file will have many blocks, all of which must be encrypted.

![](/static/2021-01-27-19-32-52.png)

:orange:**电子密码本模式**(ECB)

![](/static/2021-01-27-20-07-06.png)

* **这样做的一个【简单方法（AES一代）】是对所有块进行单独加密（一个明文分组加密成一个密文分组）**。A simple way of doing this is to encrypt all the blocks individually
  * 这被称为使用**电子密码本模式** Electronic Code Book
  * *将明文按分组密码的分组规模分成若干个分组，并将明文分组按照一定的顺序编号，每一个明文分组直接作为分组密码算法的输入，在同一个密钥的控制下加密得到相应的密文分组，各密文分组按照与明文分组相同的顺序链接得到密文*
  * 加密：用相同的key将分好组的明文进行加密函数加密
  * 解密：使用相同的key将明文通过解密函数解密
* <font color="deeppink">如果消息太长，它很容易受到统计攻击</font> It is vulnerable to a statistical attack if the message is long
* 如果纯文本具有内部结构，这可能会反映在密文中 If the plain text has an internal structure, this might be reflected in the cipher text
* **具有相同初始块并使用相同密钥加密的两个文件将生成相同的密码文本（缺点是无法隐蔽数据模式，相同的明文分组被加密成相同的密文分组）**。许多文字处理程序使用相同的样板代码启动每个文件 Two files with the same initial blocks and encrypted with the same key will produce the same cipher text. Many word processors start each file with the same boilerplate code

---

> 密码段连接（CBC）是一种操作分段密码的方式（在一段bit序列加密成一个单独的单元或分成一个密钥提供给整个部分）。密码段连接使用一定长度初始化向量（IV）。他的一个主要特点是他完全依靠前面的密码文段来译码后面的内容。因此，整个过程的正确性决定于前面的部分
> CBC模式中，每个明文块（加密前）都与先前的密文块进行异或 In cipher block chaining, each block of plaintext is xored (exclusive or) with the previous block of cipher text before encrypting

* 明文中的常规模式不会出现在密文中 Regular patterns in the plain text do not appear in the cipher text
* 加密：`Ci = EK(Ci-1⊕Pi)` （该密文输出可做下一块明文用于异或的密文）
  * 明文与前一个密文快进行异或
  * 为什么使用异或？--- xor has the useful properties: `a ⊕ a = 0, a ⊕ 0 = a, x ⊕ y = y ⊕ x`
* 解密（中间式，通过解密加密方程得到）：`DK(Ci) = DK(EK(Ci-1 ⊕ Pi)) = Ci-1 ⊕ Pi`
  * 密文块先解密，之后与先前的密文块进行异或 `Ci-1 ⊕ DK(Ci) = Ci-1 ⊕ Ci-1 ⊕ Pi = 0 ⊕ Pi = P` = 原明文块（即，解密过程）

---

:orange: 初始向量 Initialisation Vector

![](/static/2021-01-27-20-32-33.png)

* 假设前一个密文`C-1`不存在，无法与`P0`进行异或后加密
  * <font color="deeppink">因此需要**随机**块，用于充当IV初始向量，作为`C-1`</font> use the random bit block called the initialisation vector and use it as C-1
* 必须作为明文和加密文件/块一起传输，以便文件能被解密 It must be transmitted as plain text with the file so that the file can be decrypted

:orange: 【为什么需要IV，好处】 --- 如果我们有许多文档都以相同的块开始，加密后的密文将是不同的，因为它们**有不同的初始化向量（随机）**

---

描述电子密码本和使用DES等区块密码的密码块链方法，提及每种操作模式的优点和缺点。Describe the electronic codebook, and cipher block chaining methods of using block ciphers such as DES, mentioning the strengths and weaknesses of each mode of operation.

电子密码本一次对数据进行一个块的加密。它的弱点是，相同的明文块总是产生相同的密码文本块，使其容易受到统计分析的影响。Electronic codebook encrypts the data one block at a time. Its weakness is that the same block of plain text always produces the same block of cipher text, making it vulnerable to statistical analysis.

密码块链在加密前将纯文本块与前一个密码文本块进行排他性处理，对第一个块使用初始化向量。这不容易受到统计分析的影响。一个初始的随机块称为初始化向量，也必须提供并与信息一起传输。Cipher block chaining exclusive ors the plain text block with the previous cipher text block before encrypting, using an initialisation vector for the first block. This is not vulnerable to statistical analysis. An initial random block,called the Initialisation Vector, must also be provided and transmitted with the message.

## 数据擦除算法：Erase Algorithm

Methods of erasing

![](/static/2021-01-27-21-00-20.png)

> 删除文件中的数据最简单的方法是在删除文件之前用随机值覆盖它，之后再进行删除 The easiest way to erase the data in a file is to overwrite it with random values before deleting the file.(and then deleting the files)

* 块现在被重新安排回收，但不包含原始数据 The blocks are now rescheduled for recycling, but do not contain the original data.
  * 这在Java中很容易做到
* 可以检查物理文件存储设备和几个不同层次的数据  It is possible to examine the physical file storage device and several different levels of data
  * 擦掉的数据可以恢复，比如从磁性材料中恢复 Erased data can be recovered, say from the magnetic material 【最简单的擦除方式，但可以恢复】
* 竞争技术是用不同的随机数据覆盖7次  The technique to beat this is to overwrite with different random data 7 times 【BSI/VSITR，DOD】

# 【】AES Using JAVA

![](/static/2021-01-27-21-17-13.png)

* 实例程序，加密解密文本
* 该库方法支持`bytes`
  * `String`<->`byte[]`
* 包括以16进制格式化输出`byte[]`
  * 秘钥
  * 密文

---

![](/static/2021-01-27-21-19-03.png)

---

![](/static/2021-01-27-21-19-14.png)

---

数据擦除算法

![](/static/2021-01-27-21-19-21.png)