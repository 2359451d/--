# Content

* [Content](#content)
* [消息摘要意义：Motivation](#消息摘要意义motivation)
* [消息摘要概念：Message Digest Overview](#消息摘要概念message-digest-overview)
* [消息摘要签名：Message Digest Signatures](#消息摘要签名message-digest-signatures)
* [散列函数2个关键性质：2 Key Properties of Hash Functions](#散列函数2个关键性质2-key-properties-of-hash-functions)
* [性质-简易实现：Ease of Calculation](#性质-简易实现ease-of-calculation)
* [性质-不可逆：Non Reversable](#性质-不可逆non-reversable)
* [伪随机：Pseudo Randomness](#伪随机pseudo-randomness)
* [散列函数例子：Hash Function Example](#散列函数例子hash-function-example)
* [消息摘要局限性：Challenges with Digests](#消息摘要局限性challenges-with-digests)
* [哈希碰撞：Collisions](#哈希碰撞collisions)
* [哈希碰撞攻击者目标：Objective of Attacker](#哈希碰撞攻击者目标objective-of-attacker)
* [碰撞攻击取决于概率：Collision Attacks Depend on Statistics](#碰撞攻击取决于概率collision-attacks-depend-on-statistics)
* [消息摘要攻击：Message Digest Attacks](#消息摘要攻击message-digest-attacks)
* [原像攻击原理：How it Relates](#原像攻击原理how-it-relates)
* [原像攻击例子：Preimage Attack Example](#原像攻击例子preimage-attack-example)
* [哈希碰撞(攻击)例子：Hash Collision Example](#哈希碰撞攻击例子hash-collision-example)

# 消息摘要意义：Motivation

- **消息摘要消除了对来自任何外部来源的文件的完整性的任何必要或暗示的信任**。•	Message digests eliminate any required or implied trust in the integrity of a file from any external source.
  - 通过**不安全的通信渠道**发送的文件可以被截获或修改。•	Documents sent over insecure communication channels can be intercepted or modified.
  - **重定向**也可以被用来提供一个完全不同的文件。•	Redirects can also be used to provide a completely different file.
  - 如果你**下载**一个.exe文件并运行它。•	If you download an .exe and run it.
    - 你对该程序的面值来源给予了很大的信任。•	You are placing a lot of trust in the face value origins of that program.
- **消息摘要也可以在其他场合使用**。•	Message Digests can also be used in other settings.
  - 确保审判中（数字）证据的完整性。•	Ensure the integrity of (digital) evidence in trials.
  - 确保视频等档案材料不被篡改。•	Ensure archival material such as video is not altered.
  - 它们也可以证明软件的作者身份。•	They can also prove authorship of software.
  - 它们可以用来存储和检查密码。•	They can be used to store and check passwords.

:orange:避免存储明文

# 消息摘要概念：Message Digest Overview

![](/static/2022-04-19-15-00-42.png)

- **信息的摘要（MD）是一个较小的 "指纹"，可以唯一地识别信息**。•	The message digest (MD) of a message is a smaller ‘fingerprint’ that can uniquely identify the message.
- 这使用户能够**验证消息或文件的完整性**。•	This allows users to verify the integrity of messages or documents.
  - 文件或消息以二进制序列表示
- 图中说明了高层的主要过程。•	Figure here illustrates the main process at the high level.
  - provider和user使用相同hash func
- **有几种散列函数的实现方式**。•	There are several implementations of hash functions.

- 消息摘要可以加密（用秘钥签名。•	A message digest can be signed with a secret key.
- 它将与原始文件的签名版本(加密源文件)一样有效。•	It will be just as valid as a signed version of the original document.
  - 能加速加密，因为消息摘要更小
  - <font color="deeppink">但前提是不能创建具有相同消息摘要的不同文件</font>。•	Provided a different document with the same message digest cannot be created.
- 用来**证明文件、软件等的作者身份**。•	Used to prove authorship of documents, software etc.
- 利用**公钥加密**来实现这一点。•	Leverages on public key cryptography to achieve this.

# 消息摘要签名：Message Digest Signatures

![](/static/2022-04-19-15-55-33.png)

- **通常进行消息摘要的签名，而不是关联的文件进行签名**。•	Signing message digests are often done instead of the document the digest is associated with.
  - 这是为了加速。•	This is for speed.
- 该文件可能是一些小东西。•	The document could be something small.
- 它也可能是整个操作系统。•	It could also be an entire operating system.
- 比较摘要结果如果出现差异，要么是公钥不对不能够解密，，要么就是信息被篡改了

# 散列函数2个关键性质：2 Key Properties of Hash Functions

1.	它们应该是**快速实施的**。1.	They should be quick to implement.

* 应对用户想短时间内运行下载经验证的可执行文件

2.	它们必须是**不可逆转的**。2.	They must not be reversable.

哈希函数通常被称为陷阱门或单程函数。•	Hash functions are often called trap-door or 1-way functions.

- 其原理是**转换一个二进制序列以产生一个哈希值**。•	The principle is to transform a binary sequence to produce a hash.
- 然而，这**不是加密**，所以**这个过程不需要是可逆的**。•	This is not encryption however, so the process does not need to be reversable.
  - 攻击者应不能根据消息摘要还原源文件

# 性质-简易实现：Ease of Calculation

- 给定一个信息M，应该**很容易计算**出M的摘要D。•	Given a message M it should be easy to compute the digest D on M.
- **在功能上需要进行验证**。•	Functionally required for verification to take place.
- **哈希函数的实现应该能够计算出𝑛位数据集的哈希值**。•	A hash function implementation should be able to calculate the hash value of a 𝑛 bit dataset.
- Linux 发行版和一些软件镜像将为用户提供计算的哈希值。•	Linux distributions and some software mirrors will provide calculated hash values for users.
  - **对于滚动发布，每当有更新推出时，能够重新计算哈希值是非常重要的**。•	For rolling releases, it is important to be able to re-calculate hash values whenever updates are rolled out.

# 性质-不可逆：Non Reversable

**给定一个摘要D，计算原始信息M应该是非常困难的**。•	Given a digest D, it should be very difficult to compute the original message M.

- 这意味着**原始信息必须足够长**。•	Implies the original message must be long enough.
  - 例如，如果信息只有64比特长，那么就可以使用**暴力攻击**。•	If the message were only 64 bits long, for example, then a brute force attack could be used.
    - 试试所有可能的64位信息，看看哪个能产生所需的摘要。•	Try all possible 64 bit messages, seeing which produces the required digest.

# 伪随机：Pseudo Randomness

哈希函数通过一系列的操作来提供一个经常被讨论为 "伪随机 "(**看起来像是随机，但实际是一系列可重复操作的结果**)的函数•	Hash functions operate on a series of operations to provide a function that is often discussed as ‘pseudo random’

- 这意味着**与输入二进制序列相关的输出二进制序列看起来是随机的**。•	This means the output binary sequence associated with an input binary sequence appears to be random.
- 然而，它是**一系列可重复过程的结果**。•	It is, however, the result of a series of repeatable processes.
- 将**相同的二进制序列送入哈希函数将产生相同的结果**。•	Feeding the same binary sequence into a hash function will produce the same result.
- 哈希函数被设计为**对输入的微小变化高度敏感**。•	Hash functions are designed to be highly sensitive to small changes in the input.
  - avoid others to gain insight

# 散列函数例子：Hash Function Example

![](/static/2022-04-20-14-18-57.png)

- 字符串代表一个将被发送到网上的人的信息。•	The String represents a message that will be sent to someone online.
- 如果他们想确保他们收到的字符串没有被篡改，可以用这个哈希函数进行验证。•	If they want to ensure that the string they receive has not been tampered with, it can be verified with this hash function.

---

**哈希函数被设计为在输入发生微小变化时产生不同的输出**。•	Hash functions are designed to produce different output in the event of minor changes to the input.

![](/static/2022-04-20-14-19-46.png)

- 被称为雪崩效应。•	Known as the avalanche effect.
- 它们是通过一系列成对的XOR和旋转操作实现的。•	They are implemented using a series of pairwise XOR and rotation operations.
- 类似DES和AES •	Like DES and AES

---

![](/static/2022-04-20-14-20-35.png)

- 再次改变错别字，我们可以看到，我们得出了完全相同的哈希值。•	Changing the typo again we can see that we arrive at the exact same hash value.
- 假设信息从未改变，并且使用相同的哈希函数实现，情况将永远如此。•	This will always be the case, assuming the message never alters, and the same hash function implementation is used.

# 消息摘要局限性：Challenges with Digests

- **摘要的大小往往比它所关联的文件要小**。•	A digest is quite often smaller in size than the file it is associated with.
  - 使得它更容易传输。•	Makes it easier to transmit.
  - 使得用私钥签名更快。•	Makes it quicker to sign with private keys.
- **然而，减少信息摘要的比特长度意味着两个相同的信息摘要更有可能与不同的文件有关**。•	However, reduced bit length for digests means it is more likely that two message digests that are identical can relate to different documents.
- 对信息摘要的攻击常常依赖于**统计概率**。•	Attacks on message digests often rely on statistical probabilities.

# 哈希碰撞：Collisions

![](/static/2022-04-20-14-33-03.png)

- 这里的每个文件是一个8位的值。•	Each document here is an 8-bit value.
- 每个哈希值（摘要）表示为一个4位的值。•	Each hash (digest) is represented as a 4-bit value.
- 可能的唯一哈希值的最大数量可以是2^4=16 •	The maximum number of possible unique hashes can be 24 = 16
- **如果我们有至少17个文档，我们保证至少观察到1个重复的哈希值**。
- 据统计，这种情况可能发生在更少的文件上。
- 这是一个非常简单的例子，并不代表现实中的情况。•	This is a very simple example and does not represent a realistic scenario.
  - 但这个原理适用于真正的哈希函数。•	But the principle applies to real hash functions.
  - 如果我们有**数以千计**的这些文件，我们将观察到几个具有相同哈希值的文件。•	If we have thousands of these documents, we will observe several documents with the same hash value.
  - 这就是所谓的**哈希碰撞**。•	This is known as a hash collision.

# 哈希碰撞攻击者目标：Objective of Attacker

碰撞是一个大问题，因为**不合法的文件可以被当作真实的文件**。•	Collisions are a big deal because illegitimate documents can be passed off as authentic.

- 这是因为两个文件都有相同的信息摘要。•	This is because both documents will have the same message digest.
- **原像攻击和哈希碰撞攻击的目的是创建具有与真实文件相同哈希值的假文件**。•	Both preimage and hash collision attacks aim to create fake documents with the same hash value as an authentic document.
  - 利用统计概率。•	Leverages off statistical probability.

# 碰撞攻击取决于概率：Collision Attacks Depend on Statistics

- 一个房间里要有多少人，才有可能有一个人与我同一天生日，这概率大于平均值？•	How many people must be in a room before there is a greater than even probability that one of them shares a birthday with me?
  - 183
- 一个房间里必须有多少人，才会有大于偶数的概率让他们中的两个人共享一个生日？•	How many people must be in a room before there is a greater than even probability that two of them share a birthday?
  - 23    
- 随机找到两个生日相同的人比找到与指定的人生日相同的人要容易得多。•	It is much easier to find two random people with the same birthday than it is to find someone with the same birthday as a specified person.
  - 掷骰子也一样，，随机roll两个一样结果 的概率比roll一个与规定值一样的结果概率高

# 消息摘要攻击：Message Digest Attacks

有两种高调的攻击是利用哈希碰撞的。•	There are two high profile attacks that leverage on hash collisions:

1.	原像攻击。 1.	Preimage attacks.
2.	碰撞攻击(生日攻击)。2.	Collision attacks(birthday attacks).

# 原像攻击原理：How it Relates

通过subtle变更来创建多个散列，来找出碰撞

如果我们从一个文件开始，我们可以通过以下方式创建许多**看起来相似的不同文件**。•	If we start with one document, we can create many different documents that look similar by: 

- 在行末添加一个空格。•	Adding a space at the end of a line.
- 添加一个空格/退格组合•	Adding a space / backspace combination
- 让我们**假设信息摘要有m个比特**。•	Let’s assume the message digest has m bits.
  - **找到另一个具有相同信息摘要的文件**需要大约`2^m`次尝试。•	Finding another document with the same message digest requires around 2𝑚 attempts.
  - **找到两个具有相同信息摘要的随机文件**需要大约`2^m/2`的尝试。•	Finding two random documents with the same message digest requires around 2𝑚/$ attempts.
  - 需要**建立一个先前尝试的表格**。•	Requires building a table of previous attempts.

# 原像攻击例子：Preimage Attack Example

- 让我们假设m=8(消息摘要位数)，所以**有256个可能的消息摘要**。•	Let us assume that m = 8, so there are 256 possible message digests.
- 我们有一个具有消息摘要MD的文件D。•	We have a document D with a message digest MD.
- 我们想创建另一个具有相同消息摘要MD的随机文件。
- **每个随机文件**将有1/256的机会做到这一点。
- 我们平均需要尝试大约128个随机文件才能找到一个。
  - 因此，如果MD很短，，容易伪造出有相同消息摘要的不同文件

---

- 假设我们有两个文件。•	Suppose we have two documents:
  - 合法文件•	The legitimate document
  - 我们伪造的文件，想要冒充合法文件。•	The document we have forged and want to pass off as the legit document.
- 通过创建**一个**伪造的文件，我们可以看到哈希值并不匹配。•	By creating a forged document, we can see the hash values do not match.
  - ![](/static/2022-04-20-15-01-35.png)
- 然而，不断变更文档，N次尝试以后
  - ![](/static/2022-04-20-15-02-04.png)

# 哈希碰撞(攻击)例子：Hash Collision Example

- 现在让我们**对文档D(真文件)做15个随机的修改，我们共有16个目标MD值**。•	Now let’s make 15 random changes to document D. We have a total of 16 target MD values.
  - 我们把它们储存在一个**查找表**中。•	We store them in a lookup table.
- 现在**对第二个文件进行随机修改**。•	Now make random changes to the second document.
  - **每个改动都有1/16的机会与16个目标MD值中的一个相匹配**。•	Each change will have a 1/16 chance of matching one of the 16 target MD values.
  - **平均**而言，我们需要创建**8个文件**。•	On average we need to create 8 documents.
- 我们需要创建大约16+8=24个随机文件，才能有50%的概率找到一个匹配。•	We need to create about 16+8 = 24 random documents to have a 50% probability of finding a match.
- 这就容易多了。•	This is a lot easier.