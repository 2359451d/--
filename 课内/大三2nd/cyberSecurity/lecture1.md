# Lecture 1

![](/static/2021-01-25-20-27-27.png)

* keepping a secret
* communicating a secret
* common signle key algo
* single key encryption in Java

what is the goal of cyber security?

content

* [Lecture 1](#lecture-1)
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