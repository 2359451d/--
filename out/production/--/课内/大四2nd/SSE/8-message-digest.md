# Content

* [Content](#content)
* [消息摘要意义：Motivation](#消息摘要意义motivation)
* [消息摘要概念：Message Digest Overview](#消息摘要概念message-digest-overview)
* [消息摘要签名：Message Digest Signatures](#消息摘要签名message-digest-signatures)
* [散列函数2个关键性质：2 Key Properties of Hash Functions](#散列函数2个关键性质2-key-properties-of-hash-functions)
* [性质1-简易实现：Ease of Calculation](#性质1-简易实现ease-of-calculation)
* [性质2-不可逆：Non Reversable](#性质2-不可逆non-reversable)
* [伪随机(决定性的)：Pseudo Randomness](#伪随机决定性的pseudo-randomness)
* [散列函数例子：Hash Function Example](#散列函数例子hash-function-example)
* [消息摘要局限性：Challenges with Digests](#消息摘要局限性challenges-with-digests)
* [哈希碰撞：Collisions](#哈希碰撞collisions)
* [哈希碰撞攻击者目标：Objective of Attacker](#哈希碰撞攻击者目标objective-of-attacker)
* [碰撞攻击取决于概率：Collision Attacks Depend on Statistics](#碰撞攻击取决于概率collision-attacks-depend-on-statistics)
* [消息摘要攻击：Message Digest Attacks](#消息摘要攻击message-digest-attacks)
* [原像攻击原理：How it Relates](#原像攻击原理how-it-relates)
* [原像攻击例子：Preimage Attack Example](#原像攻击例子preimage-attack-example)
* [哈希碰撞(攻击)例子：Hash Collision Example](#哈希碰撞攻击例子hash-collision-example)
* [==========](#)
* [散列函数另外关键性质: Another 2 Key Properties of Hash Functions](#散列函数另外关键性质-another-2-key-properties-of-hash-functions)
* [Summary-散列函数要求: Requirements Summary of Hash Functions](#summary-散列函数要求-requirements-summary-of-hash-functions)
* [==========](#-1)
* [散列函数的一般形式：General Form of Hash Functions](#散列函数的一般形式general-form-of-hash-functions)
* [散列函数Overview](#散列函数overview)
* [MD5 Overview](#md5-overview)
* [MD5补位-Padding](#md5补位-padding)
* [MD5散列函数：MD5 Function](#md5散列函数md5-function)
* [SHA-1](#sha-1)
* [SHA-2](#sha-2)
* [SHA-256-预处理：Pre-Processing Steps](#sha-256-预处理pre-processing-steps)
* [SHA-2](#sha-2-1)
  * [SHA-2 Function](#sha-2-function)
* [SHA-3 Competition](#sha-3-competition)
* [Summary](#summary)
* [==========](#-2)

# 消息摘要意义：Motivation

- 消息摘要是一段数据的数学表示法•	A message digest is a mathematical representation of a piece of data
- **消息摘要消除了对来自任何外部来源的文件的完整性的任何必要或暗示的信任**。•	Message digests eliminate any required or implied trust in the integrity of a file from any external source.
  - 主要用于**确保在不安全的网络中发送的数据的完整性**。•	Used primarily to ensure the integrity of data sent across unsecure networks.
  - 通过**不安全的通信渠道**发送的文件可以被截获或修改。•	Documents sent over insecure communication channels can be intercepted or modified.
  - **重定向**也可以被用来提供一个完全不同的文件。•	Redirects can also be used to provide a completely different file.
  - 如果你**下载**一个.exe文件并运行它。•	If you download an .exe and run it.
    - 你对该程序的面值来源给予了很大的信任。•	You are placing a lot of trust in the face value origins of that program.
- **消息摘要也可以在其他场合使用**。•	Message Digests can also be used in other settings.
  - 确保审判中（数字）证据的完整性。•	Ensure the integrity of (digital) evidence in trials.
  - 确保视频等档案材料不被篡改。•	Ensure archival material such as video is not altered.
  - 它们也可以证明软件的作者身份。•	They can also prove authorship of software.
    - 生成的摘要可以用秘钥加密，并解密以证明作者。Generated digests can be encrypted with secret keys and decrypted to prove authorship.
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

# 性质1-简易实现：Ease of Calculation

- 给定一个信息M，应该**很容易计算**出M的摘要D。•	Given a message M it should be easy to compute the digest D on M.
- **在功能上需要进行验证**。•	Functionally required for verification to take place.
- **哈希函数的实现应该能够计算出𝑛位数据集的哈希值**。•	A hash function implementation should be able to calculate the hash value of a 𝑛 bit dataset.
- Linux 发行版和一些软件镜像将为用户提供计算的哈希值。•	Linux distributions and some software mirrors will provide calculated hash values for users.
  - **对于滚动发布，每当有更新推出时，能够重新计算哈希值是非常重要的**。•	For rolling releases, it is important to be able to re-calculate hash values whenever updates are rolled out.

# 性质2-不可逆：Non Reversable

**给定一个摘要D，计算原始信息M应该是非常困难的**。•	Given a digest D, it should be very difficult to compute the original message M.

- 这意味着**原始信息必须足够长**。•	Implies the original message must be long enough.
  - 例如，如果信息只有64比特长，那么就可以使用**暴力攻击**。•	If the message were only 64 bits long, for example, then a brute force attack could be used.
    - 试试所有可能的64位信息，看看哪个能产生所需的摘要。•	Try all possible 64 bit messages, seeing which produces the required digest.

# 伪随机(决定性的)：Pseudo Randomness

哈希函数通过一系列的操作来提供一个经常被讨论为 "伪随机 "(**看起来像是随机，但实际是一系列可重复操作的结果**)的函数•	Hash functions operate on a series of operations to provide a function that is often discussed as ‘pseudo random’

- 这意味着**与输入二进制序列相关的输出二进制序列看起来是随机的**。•	This means the output binary sequence associated with an input binary sequence appears to be random.
- 然而，它是**一系列可重复过程的结果**。•	It is, however, the result of a series of repeatable processes.
- 将**相同的二进制序列送入哈希函数将产生相同的结果**。•	Feeding the same binary sequence into a hash function will produce the same result.
  - 散列函数是决定性的，例如，相同的输入将产生完全相同的输出。They are deterministic, such that the same input will yield the exact same output.
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

---

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

---

破解摘要的方法是利用统计。•	Breaking digests comes down to exploiting statistics.

- 对**目标文件做一些小的改动**，得到一个与目标文件具有相同哈希值的伪造文件(原像攻击)•	Make small changes to a target document, to get a forgery that has the same hash digest as the target (preimage)
- 对**目标文件和伪造文件进行小的改动**，以获得其中一个的匹配（碰撞，，生日攻击）。•	Make small changes to both the target and forgery to get a match between one of them (collision)
- 这使得攻击者可以把一个假的文件，或文件，或程序当作真品。•	This allows an attacker to pass off a fake document, or file, or program as the genuine article.
  - 任何想检查摘要的人都会看到摘要被检查出来了!•	Anyone looking to check the digest will see the digest checks out!
  - 将它们与公钥加密结合使用，以证明作者身份。•	Use them in combination with public key encryption to prove authorship.

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

- 来自 "正常操作 "的碰撞通常是不可能的，而且通常被认为是可以接受的风险。•	Collisions from ‘normal operations’ are generally unlikely and are often considered an acceptable risk.
- **攻击是通过操纵文件来最大限度地提高发生碰撞的统计可能性**。•	The attacks operate on manipulating documents to maximize the statistical possibility of a collision occurring.
- **一般来说，我们要尝试修改【目标文件和伪造的文件】，试图找到一个能产生相同哈希值的组合**。•	Generally, we want to try and modify the target and the forged documents to try and find a combination that will generate the same hash value.

---

- 现在让我们**对文档D(真文件)做15个随机的修改，我们共有16个目标MD值**。•	Now let’s make 15 random changes to document D. We have a total of 16 target MD values.
  - 我们把它们储存在一个**查找表**中。•	We store them in a lookup table.
- 现在**对第二个文件（伪造文件）进行随机修改**。•	Now make random changes to the second document.
  - **每个改动都有1/16的机会与【16个目标MD值】中的一个相匹配**。•	Each change will have a 1/16 chance of matching one of the 16 target MD values.
    - 之前那个例子只有1个目标MD值，所以此例碰撞概率增加
      - 之前例子![](/static/2022-04-20-15-27-31.png)
  - **平均**而言，我们需要创建**8个文件**。•	On average we need to create 8 documents.
- 我们需要创建大约16+8=24个随机文件，才能有50%的概率找到一个匹配。•	We need to create about 16+8 = 24 random documents to have a 50% probability of finding a match.
  - 这就容易多了。•	This is a lot easier.

---

- 我们有两份文件。•	Again we have the two documents:
  - 合法的文件。•	The legitimate document.
  - 我们伪造的文件，想把它当作合法文件。•	The document we have forged and want to pass off as the legit document.
- 通过创建一个伪造的文件，我们可以看到哈希值并不匹配。
  - ![](/static/2022-04-20-15-30-21.png)
- 然而...我们要**修改伪造的文件和目标文件**。 we are altering the forged document and the target document.
  - ![](/static/2022-04-20-15-31-22.png)
  - 这些修改通常是很小的，并尽可能地使人类无法察觉。•	The modifications are often small and try to be as imperceivable to humans if possible.
  - 增加了在伪造文件和合法文件之间找到匹配的机会。•	Increases chance to find a match between the forgery and the legitimate document.

# ==========

# 散列函数另外关键性质: Another 2 Key Properties of Hash Functions

为了减少前面的攻击的成功，，

3.	它应该**很难生成与目标文件相匹配的文件M的摘要**。It should be hard to generate the digest of a document M that matches a target document.
4.	应该**很难从两个随机文件中产生相同的摘要**。It should be hard to generate the same digest from two random documents.

# Summary-散列函数要求: Requirements Summary of Hash Functions

1.	**给定消息，很容易计算出消息摘要【效率,速度**。1.	Given the message, it is easy to compute the message digest.
2.	**给出信息摘要，就很难计算出该信息【不可逆，避免逆向工程**。2.	Given the message digest, it is hard to compute the message.
3.	**给定一个消息`M`，很难找到另一个具有相同消息摘要的消息`M'`**。3.	Given a message M, it is hard to find another message M' with the same message digest.

- 这就加强了对预像攻击的防范。•	This hardens against a preimage attack.
  - 伪造不同随机文件来命中源文件(1个?)的hash
- <font color="deeppink">要求3是至关重要的，因为它可以防止一个信息摘要已被加密的文件被另一个具有相同信息摘要的文件取代。</font> •	Requirement 3 is vital since it prevents one document whose message digest has been signed from being replaced by another document with the same message digest.

4.	**应该很难找到两个具有相同消息摘要的随机消息M和M'**。4.	It should be hard to find two random messages M and M' with the same message digest.

- 这就加强了对哈希碰撞攻击(生日攻击)的防范。•	This hardens against a hash collision attack.
- 要求4更微妙，并依赖于以下几点。•	Requirement 4 is more subtle and relies on the following…
  - **如果m=64，那么第一个问题需要2^64=10^19次尝试，而第二个问题需要2^32=10^10次尝试**。•	If m = 64 then the first problem requires 2%4 = 10&9attempts, while the second requires 2($ = 10&)attempts.
  - **64位太小了，无法经受住生日攻击，信息摘要必须至少有128位长**。•	64 bits is too small to survive a birthday attack, and the message digest must be at least 128 bits long.
    - 在实践中，它们通常是256比特长。•	In practice they are usually 256 bits long.

---

# ==========

# 散列函数的一般形式：General Form of Hash Functions

- **哈希函数处理一个文件并产生一个信息摘要**。•	Hash functions take a document and produce a message digest.
- 哈希函数必须减少文件的大小，**通常的工作方式是首先将文件分解成块，每个块的长度与最终的哈希值相同**。•	Hash functions have to reduce the size of a document, and normally work by first breaking the document into blocks, each the same length as the final hash value.
- 定义一个函数，**将两个块作为输入，一个作为输出**。•	A function is defined that takes two blocks as input and one as output.
- 然后，该函数被反复地调用。•	The function is then called iteratively.
- 它的两个输入将是。•	Its two inputs will be:
  - 前一个输出•	The previous output
  - 下一个信息块。•	The next message block.
- `ℎi = 𝑓(𝑀i, ℎi-1)`
- 函数f通常是⊕（XOR）和简单运算的组合。•	The function f is usually a combination of ⊕ and simple operations.
- 迭代结束后，**哈希函数的结果将是h的最终值**。•	The result of the hash function will be the final value of h when the iteration has finished.

# 散列函数Overview

- **MD5**是由RSA的Ron Rivest发明的（见后面）。•	MD5 was invented by Ron Rivest of RSA fame (see later)
  - 128位
  - 被发现容易受到生日攻击。•	Found to be vulnerable to a birthday attack.
- SHA-1是由NIST制作的
  - 安全哈希算法1 •	Secure Hash Algorithm 1
  - 160比特
  - 被发现容易受到生日攻击。•	Found to be vulnerable to a birthday attack.
- **SHA-2**也由NIST生产，有4个版本
  - 到目前为止是安全的。•	Secure so far.
- SHA-3公开竞赛，于2012年采用 •	SHA-3 public competition with adoption in 2012

---

它们的操作原理都是一样的。They all however operate on the same principle.

- 初始化一系列的**内部寄存器**。•	Initialize a series of internal registers.
- 将输入的**数据分成一系列的数据块**。•	Chop up the input data into a series of data blocks.
- 在一系列的回合中**使用每个数据块来更新寄存器**。•	Use each block to update the registers over a series of rounds.
- 数据处理**结束时的寄存器就是摘要**。•	The registers at the end of data processing is the digest.
- 这意味着**任何大小的输入，总是会产生相同大小的输出**。•	This means any size of input, will always produce the same sized output.
  - 输入大小不重要，因为总会要处理，，并且最终输出是取决于固定的寄存器数量

# MD5 Overview

1. **补位padding**（如果原数据块不是512的倍数

![](/static/2022-04-20-20-13-03.png)

* block尾部补1作为padding和原数据的分割线
* 然后补0(补到是512的倍数-64)，再补64bit（代表原始数据长度）
* 补完后数据位数长度为512的倍数
* <font color="deeppink">即便是这个数据的位数长度对512求模的结果正好是448也必须进行补位。总之，至少补1位，而最多可能补512位</font>

2. 每个512bit块作为散列函数输入

![](/static/2022-04-20-20-18-37.png)
![](/static/2022-04-20-20-21-11.png)
![](/static/2022-04-20-20-21-17.png)
![](/static/2022-04-20-20-21-25.png)
![](/static/2022-04-20-20-21-32.png)

* 并且每个512bit块用于更新内部寄存器的状态【一系列的XOR和旋转操作】 internal registers （initial vectors）
  * <font color="deeppink">寄存器数量取决于在使用的散列函数</font>
  * 以MD5为例，ABCD拼接后128bit输出

# MD5补位-Padding

MD5在512位块上操作。•	MD5 operates on 512-bit blocks.

- 很多时候，需要生成哈希值的信息不是512位块的倍数。•	Quite often, messages that need a hash generated are not multiples of 512-bit blocks.
- 必要时，填充位被用来填充信息。•	Padding bits are used to pad the message, as necessary.

1.	**首先，在信息的末尾添加一个1**。1.	First, add a 1 to the end of the message.
2.	**然后，在信息中添加0，直到它比512的倍数少64位**。2.	Then, add 0s to the message until it is 64 bits less than a multiple of 512.
3.	**最后，添加64位，这样的64位代表被散列的原始信息的长度**。3.	Finally, add 64 bits, such that the 64 bits represent the length of the original message being hashed.

# MD5散列函数：MD5 Function

![](/static/2022-04-20-20-52-16.png)
![](/static/2022-04-20-20-56-27.png)
![](/static/2022-04-20-21-18-05.png)

- **每个512位的区块分【4轮】进行处理**。•	Each 512-bit block is processed in 4 rounds.
- **每一轮由16个单独的步骤组成**。•	Each round consists of 16 individual steps.
- 每一步都涉及到一系列的操作来修改**4个32位的初始化向量（寄存器**）。•	Each step involves a series of operations to modify 4 32-bit initialization vectors.
  - A B C和D
  - 注意**ABCD有初始值**
- **512位块被分割成16个32位块**。•	The 512-bit block is split into 16 32-bit chunks.
  - 每个32位块的偏移量为𝑀i。•	Each 32-bit chunk is offset as 𝑀*
- **一组类似的数据，被称为常数，也被分割成32x64块（64个32bit块**）。•	A similar set of data, known as a constant is also split into 32x64 chunks.
  - 这来自于一个预先确定的表格。•	This comes from a predetermined table.
- Message会变（不同数据块)，，但是Constant一旦计算不会改变
  - 分割信息的偏移量将被重复使用，常数则不会。•	The offsets for the split message will be reused, the constants are not.
- **每一轮都会涉及相关偏移量和寄存器之间的加法模数操作**。•	Each round will involve addition modulus operations between the relevant offsets and the register.
- **像DES和AES一样，也会使用位旋转**。•	A bit rotation is also used, like with DES and AES.
- 每一轮都会使用一系列的函数【每轮函数不同，，不过实质都是bit操作。•	A series of functions are used on each round.
  - 每个512位块将在4轮中被处理。•	Each 512-bit block will be processed in 4 rounds.

:orange:问题：128bit输出不够解决哈希碰撞问题

# SHA-1

- 在操作上与MD5非常相似•	Very similar in operation to MD5
- **在寄存器的更新方式上有细微差别**。•	There are minor differences in how the registers are updated.
- 摘要本身也比较大。•	The digest itself is also larger.
  - **较大的摘要位长度有助于使SHA-1抵御MD5的漏洞**。•	A larger digest bit length helps make SHA-1 resistant to vulnerabilities of MD5.

---

![](/static/2022-04-20-21-33-48.png)

- 与MD5的原理相同。•	Same principle with MD5.
- **初始化向量中有更多比特**。•	More bits in the initialization vectors.
  - 更多的寄存器
- **更多的旋转操作**。•	More rotation operations.
  - 2轮，增加复杂度，但是不能过于复杂
- 函数是不同的，并且再次根据轮次而改变。•	Functions are different and again change depending on the round.
- **每一轮步骤中使用的信息偏移量是被填充的**。•	The message offset used in each round step is padded.

---

- 在现代系统中不再使用。•	No longer used in modern systems.
- 和DES一样，由于计算能力的提高，碰撞的制造变得更加容易。•	Much like DES, due to computational power it became easier for collisions to be manufactured.
- SHA-2是今天使用的标准。•	SHA-2 is the standard in use today.
  - 与所讨论的原理相同，有一些额外的复杂性。•	Same principles as discussed, with some additional complexity.

# SHA-2

- SHA-2本身不是一种算法，而是一个算法系列。•	SHA-2 is not in itself an algorithm, but a family of algorithms.
  - sha-256 (2001)
  - SHA-512 (2001)
  - SHA-512/256 (2012)
- 我们将看一下SHA-256。

# SHA-256-预处理：Pre-Processing Steps

如何生成constant blocks

![](/static/2022-04-20-21-37-29.png)

- **生成常数表`Ki`**。•	Generate Constant table.
  - 用于此的素数。•	Prime numbers used for this.
- **前64个素数**的**立方根的小数部分**。•	Fractional part of the cube root of the first 64 prime numbers.

---

- **生成和扩展message schedule**。•	Generate and expand message schedule.
  - **message schedule是输入(512位块**)。•	Message schedule is the input 512-bit block.
    - **分成16个32位的字块**。•	Split into 16 32-bit word chunks.
- **我们需要为SHA-256生成另外48个小块**。•	We need to generate another 48 chunks for SHA-256.
  - 为此我们使用以下两个函数。•	We use the following two functions for this:
    * ![](/static/2022-04-20-21-41-10.png)
    * sigma0, sigma1
  - 这些函数也被用于主压缩函数中。•	These functions are also used in the main compression function.
- 之前MD5，，一共16个32bit块，，，，这里我们想用到64个32字块

即

- **对于𝑀1-𝑀16，我们保持原样，这是来自输入信息块**。•	With 𝑀1-𝑀16 we keep as is, this is from the input message block.
- **对于𝑀17-𝑀64，我们使用以下公式来生成数据**。•	For 𝑀17-𝑀64 we use the following equation to generate data:
  - ![](/static/2022-04-20-21-45-31.png)
    - 比如M17,,,t=17
  - 西格玛函数的操作与之前看到的一样。
    - ![](/static/2022-04-20-21-41-10.png)
    - 它们只是一系列的位操作。
- 这个过程一直持续到我们从 message schedule划中**得到64个32位块**。This process continues until we get 64 32-bit blocks from the message schedule.
  - **通常被标记为`𝑊i`** Commonly labelled as 𝑊i 

# SHA-2

![](/static/2022-04-20-22-07-09.png)
![](/static/2022-04-20-22-12-19.png)

- **8个32位寄存器**。•	8 32-bit registers.
  - **这些都是以静态值初始化的**。•	These are all initialized with static values.
  - 和以前一样，这些寄存器将随着512个块的处理而被更新。•	As before, the registers will be updated as the 512 blocks are processed.
- 再次类似于MD5，但比SHA-1有更多的步骤 •	Similar again as MD5, but with even more steps than SHA-1
  - 每步寄存器输出类似前面结果右移，，，An-1变成Bn，，以此类推

---

![](/static/2022-04-20-22-15-47.png)

- 所有的寄存器都是向右旋转的。•	All the registers are rotated to the right.
- 大部分的操作都是在右边进行的，**包括H的值、CH的输出和信息/常数的输入**。•	Most of the operations take place on the right with the value from H, the output of CH and the Message/Constant input.

## SHA-2 Function

SHA-2有四个函数•	There are four functions used in SHA-2

![](/static/2022-04-20-22-17-30.png)

- **与MD5不同的是，这些函数不因回合的不同而改变**。•	Unlike MD5, these do not change depending on the round.
- 这些是**预先确定的程序**，如下图所示。•	These are predetermined procedures as follows to the right.
  - 因为实质都是bit operation，所以速度够快

:orange:使用SHA-2的原因，，，初始化向量数量够大

* 8个32位寄存器

# SHA-3 Competition

- 继AES竞赛的成功之后，NIST于2007年11月宣布了一项名为SHA-3的信息摘要竞赛。•	Following the success of the AES competition, NIST announced a competition for a message digest, to be called SHA-3, in November 2007.
- 到2008年10月，有64个参赛者提交了作品•	64 entrants were submitted by October 2008
- 第一轮接受了51个，并开始进行公开审查。•	51 were accepted for the first round and public scrutiny began.
- 大约20个被打破。•	About 20 were broken.
- 14个进入了第二轮。•	14 made it into the second round.

- 2010年12月公布了5个入围项目。
  - BLAKE (Jean-Philippe Aumasson et. al.)
  - Gr stl (Knudsen et. al.) 基于AES的。
  - JH (吴红军)
  - Keccak (Daemen et. al.)
  - Skein (Schneier et. al.)
  - 所有入围者的功能都根据公众分析进行了调整。
- 2012年10月宣布的冠军是Keccak。
  - 他们的作品明显比其他作品快。
- NIST想稍微改变Keccak，以换取安全和速度，但由于不信任的气氛而退缩。

---

- 没有被广泛采用。•	Not widely adopted.
- **在SHA-2无法使用的情况下，作为一种主动的后备方案而开发**。•	Developed as a proactive fallback in the event SHA-2 becomes infeasible to use.
  - 仅在不得不使用的情况下
- 该标准偏离了以前的标准，在操作比特的方式上作为一种 "海绵 "算法。•	The standard deviates from the previous and operates as a ‘sponge’ algorithm in the way it operates on bits.

# Summary

- 消息摘要是一种压缩算法。•	Message digests are compression algorithms.
  - 它们被用来**验证软件、交易等的完整性**。•	They are used to verify the integrity of software, transactions, etc.
- 对**摘要攻击**往往依赖于**统计概率**。•	Attacks on digests often rely on statistical probability.
  - **现代散列函数被设计用来减轻这种情况**。•	Modern hash functions are designed to mitigate this.
    - fast,deterministic(相同输出，结果是一样的)
- **较大的摘要更难制造碰撞**。•	Larger digests are more difficult to manufacture collisions.
  - 生日攻击，原像攻击
  - 不同散列函数，，尝试在增加安全性时（增加内部寄存器数量）确保不会让算法变得过于复杂（过多寄存器）&慢

# ==========
