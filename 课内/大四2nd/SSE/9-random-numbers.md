# Content

- 讨论基于硬件的随机数生成器。
- 讨论伪随机数生成器。
- 讨论密码学上安全的随机数生成器。
- 提供后两者的一个例子。
- 说明利用它们的库的一些问题

---

* [Content](#content)
* [为什么使用随机数生成器？Why random numbers](#为什么使用随机数生成器why-random-numbers)
* [随机数生成器类型-3种随机数生成方式：The types of random generators](#随机数生成器类型-3种随机数生成方式the-types-of-random-generators)
* [=========](#)
* [真随机数生成挑战：The challenge of Random numbers](#真随机数生成挑战the-challenge-of-random-numbers)
* [真随机序列：Real Sequences](#真随机序列real-sequences)
* [生成真随机数序列：Generate Real random sequences](#生成真随机数序列generate-real-random-sequences)
* [=========](#-1)
* [伪随机数生成器：Pseudorandom number generators](#伪随机数生成器pseudorandom-number-generators)
* [PRNG生成例子-LCG线性同余发生器:Linear Congruential Generators](#prng生成例子-lcg线性同余发生器linear-congruential-generators)
  * [Park and Miller](#park-and-miller)
* [PRNG生成例子-LFSR线性反馈移位寄存器：Linear Feedback Shift Register](#prng生成例子-lfsr线性反馈移位寄存器linear-feedback-shift-register)
  * [LFSR Process](#lfsr-process)
* [How good are LFSRs?](#how-good-are-lfsrs)
* [Problems with PRNG: Netscape](#problems-with-prng-netscape)
* [=========](#-2)
* [加密安全伪随机序列：Cryptographically Secure Pseudo- Random Sequences](#加密安全伪随机序列cryptographically-secure-pseudo--random-sequences)
* [椭圆曲线：Elliptic curves](#椭圆曲线elliptic-curves)
* [Dual_EC_DRBG缺陷](#dual_ec_drbg缺陷)
* [NIST and RSA](#nist-and-rsa)
* [Summary](#summary)

# 为什么使用随机数生成器？Why random numbers

- 随机数经常被用于**创建会话密钥**。•	Random numbers are often used for the creation of session keys.
  - **如果你的计算机通过某种TCP/IP连接与另一台计算机进行通信，会话密钥在需要时对通信进行加密**。•	If your computer is communicating with another over some TCP/IP connection, session keys encrypt traffic when needed.
- 它们也被用于**创建一次性密码（OTP**）。•	They are also used in the creation of one-time passwords (OTPs)
- 它们可以用来**创建对称加密的秘钥**。•	They can be used to create symmetric key secrets.
- 它们也被用于**密钥交换协议**中。•	They are also used in key exchange protocols.
- 它们也被用于计算的**其他方面**。•	They are also used in other aspects of computing.
  - 视频游戏领域的AI编程。•	AI programming in the video games sector.

# 随机数生成器类型-3种随机数生成方式：The types of random generators

通常情况下，随机数生成器可分为以下几类。•	Typically, random number generators can be categorized as follows: 

1.	**硬件随机数生成器 HRNGs** 1.	Hardware Random Number Generators HRNGs

* 必须以某种数据结构 & 算法来生成，，计算机需要某种指导来生成，**所以通过计算机来生成true random  numbers是具有挑战的**
  * <font color="red">因为计算机设备缺少人类直觉的抽象思维，很难提供真正随机数字，必须依赖硬件【通过软件是不可行的】，比如使用物理传感器来识别环境的随机属性（通常是难以被预测的。比如(后面例子)计算机时钟，连续按键间隔，噪音，光标位置，温度压力等，结合其他的一些测量来生成真随机数</font> use physical sensors to identify random properties of the environment.
* 一般提及true random number generator或real random number generator就是指HRNG
- **如果再次运行生成器，一个真正的随机数序列是不能重复的**。•	A real random number sequence cannot be repeated if the generator is run again.
  - 所以可以用来生成OTP(one time pass, one time keys)

2.	**伪随机数生成器PRNGs** 2.	Pseudorandom Number Generators PRNGs

* 使用算法来生成看似随机但实际上并不随机的数字序列。use algorithms to generate numerical sequences that appear random but are not.
* 软件层次，决定性的determinisitic，
* 一般都需要提供一个seed数据，然后生成器负责根据种子生成随机digits。
* 生成过程是可重复的repeatable,所以**输入相同，输出相同**（seed不变的情况下？?。。**输出会某种层面看起来是随机的（因为实质是一系列可重复过程的执行结果**）
- **伪随机数列看起来像一个真正的随机数列，但是可以重复**。•	A pseudo-random number sequence looks like a real random number sequence but is repeatable.

3.	**加密安全的随机数生成器 CSPRNGs** 3.	Cryptographically Secure (Pseudorandom) Random Number Generators CSPRNGs

* 使用算法生成难以预测的数字序列。同样，输出看起来是随机的，但不是。use algorithms to generate numerical sequences that are difficult to predict. Again, the output appears random but is not.
* 决定性的determinisitic，类似上面的，唯一区别就是逆向工程的难度
* 取决
  * 推测随机数由什么生成而来的难度
  * 给定攻击者已知信息，如何预测之后的数字
* **上面PRNG一般更好推算出下一个序列会是什么，容易推算出生成的随机数使用的初始种子【这就是为什么PRNG不可以被看为是cryptographically secure的**
  - 可以通过统计概率 & 分布或者观察潜在模式来预测，，
* **CSPRNG一般更难逆向工程使用的初始种子，并且难以预测下一个随机数序列是什么**
* <font color="deeppink">所以，CSPRNG一般用来生成加密安全的密钥，会话密钥，对称加密密钥</font>
- **一个加密安全的伪随机数字序列不能从序列中的一些数字中预测出来**。•	A cryptographically secure pseudo-random number sequence cannot be predicted from some of the numbers in the sequence

# =========

# 真随机数生成挑战：The challenge of Random numbers

**真正随机的随机数是很难在软件中产生的**。•	Random numbers that are truly random are very difficult to produce in software.

- 通常，**在计算中创建随机数序列需要从环境中进行某种程度的测量**。•	Often, the creation of random number sequences in computing requires some degree of measurements from the environment.
- 通常需要**基于硬件的监测的帮助**。•	Often requires the assistance of hardware-based monitoring.
- HRNG-真正的随机生成器通常被称为**硬件数字生成器**。•	**Real（true） random generators** are often called **hardware number generators**.
  - 因为需要提供seed, source, 指导，如何生成。而计算机本身不能利用人类抽象思维的直觉

# 真随机序列：Real Sequences

真随机序列**具有伪随机序列的所有属性，还有一个附加属性**。•	Real random sequences have all the properties of pseudo-random sequences, and an additional property.

- 它们**不能被可靠地复现**。•	They cannot be reliably reproduced.
- **如果你用相同的输入运行生成器两次，产生的序列将是不同的**。•	If you run the generator twice with the same input, the resulting sequences will be different.
- 所以可以用来生成OTP(one time pass, one time keys)
  - 这些序列可以作为OTP使用。所有的值都必须被储存起来。•	These sequences can be used as a one-time pad. All the values must be stored.
  - 1955年，兰德公司出版了一本包含100万个随机数字的书。•	In 1955 the RAND corporation published a book containing 1,000,000 random digits.
    - **它们是在计算机时代之前生产的，因此没有任何计算机引起的偏差**。•	They were produced before the computer era, and so do not have any computer induced bias.
      - 过去使用的，，true random，，现在一般使用加密安全的随机数
    - 因此，它们现在仍然被使用。•	Thus they are still used now.

# 生成真随机数序列：Generate Real random sequences

:orange: **计算机时钟**•	Computer Clock

- **最高比特将在很长一段时间内保持不变，并且很容易预测**。•	The most significant bits will stay the same for long periods of time and will be easy to predict.
  - 比如minute，，
  - 如果长时间不变，不足以看为inherently随机的
- 来自系统时间的**低几个位将是合理地难以预测**的。•	The least significant few bits from the system time will be reasonably difficult to predict.
  - **最低位可能有一些基于自然机器时钟周期的周期性**。•	The least significant bit may have some periodicity based on the natural machine clock cycle.
    - 模型难以分辨？经常性变化？
- <font color="deeppink">从一个**非常精确的时钟**中**取中间的一大块位**是最好的</font>。•	Taking the middle chunk of bits from a very accurate clock is best.

---

**下面两个与上面计算机时钟结合生成随机序列也是可行的，因为我们通常需要physical measurements**

:orange: **键盘延时**•	Keyboard Latency

- 测量**连续击键之间的时间**，这是很随机的。•	Measure the time between successive key strokes, which is quite random.
  - 如果能以离散方式测量，每个人连续按键的差异会很随机
- 这对于**生成短的随机序列**是可以的。•	This is OK for generating short random sequences.
- 需要一个人按下按键。•	Requires a person to press the keys.

:orange: **使用随机噪声**•	Using Random Noise

- 测量**随机事件之间的时间间隔**，如大气噪声高于某个阈值。•	Measure the time interval between random events such as atmospheric noise being above a certain threshold.

# =========

# 伪随机数生成器：Pseudorandom number generators

伪随机序列是指从起始值就**可以完全预测的序列**。•	Pseudo random sequences are sequences that are completely predicable from the starting value.

- 它们的分布**看起来是随机的(但实际上不是随机的** •	Their distribution looks random
  - 值、值对等的频率分布与随机序列相同。•	The frequency distribution of values, pairs of values etc is the same as a random sequence.
  - 给定相同输入，会得到相同输出
- 它们**对模拟很有用**。•	They are useful for simulation.
  - 视频游戏领域的AI编程（随机变量）。•	AI programming in the video games sector.
  - 模拟随机agent behavior
  - **如果想进行重复模拟，量化某些看起来是随机的行为，但是某种方面是可预测的**
- 它们有时对加密很有用，但必须注意。•	They can sometimes be useful for encryption, but care must be taken.
  - 但不够安全，因为更容易被预测，逆向工程

# PRNG生成例子-LCG线性同余发生器:Linear Congruential Generators

> LCG（linear congruential generator）线性同余算法，是一个古老的产生随机数的算法

- 这些都是用下面的公式来产生一系列的数字。•	These use the following formula to generate a series of numbers.
- ![](/static/2022-04-23-15-44-33.png)
  - `Xn = (aXn-1 + b) mod m`
- **第一个数值𝑋0是序列的种子**。•	The first value 𝑋0 is the seed of the sequence.
- **只要a、b和m的值选择得当，这些运算符是快速的，并产生良好的特性**。•	These operators are fast and produce good properties, provided the values of a, b and m are chosen well.
- 不幸的是，它们**在密码学上是不安全的**，所以**不应该被用于加密**。•	Unfortunately, they are not cryptographically secure, and so should not be used for encryption.
- 在密码学中还有许多其他的应用，这种类型的序列已经足够好了。•	There are many other applications in cryptography for which this type of sequence is good enough.

## Park and Miller

- 针对上面公式的一个既定的标准。•	An established standard. MINSTD
- m = 2147483647 (2^31 - 1 a Mersenne prime)
- 𝑎 = 16807 （7^5)
- b = 0
- 如果𝑋_𝑖非常小，那么𝑋i(1也会非常小。•	If 𝑋_𝑖 is very small, then 𝑋i+1 will also be very small.
  - 这是一个弱点。•	This is a weakness.

# PRNG生成例子-LFSR线性反馈移位寄存器：Linear Feedback Shift Register

![](/static/2022-04-23-15-57-39.png)

- **产生伪随机数的常见方法**。•	Common way of generating pseudorandom numbers.
- **涉及到一个n位数组**，如见。•	Involves an n-bit array as seen.
- 一个4位数组的例子。•	We will walk through a 4- bit array example.

## LFSR Process

![](/static/2022-04-23-16-01-15.png)

- 我们想修改LSFR寄存器的状态。•	We want to modify the state of the LSFR registers.
- 我们通过两个步骤来完成这个任务。•	We do this with two steps.
  - **首先，将比特向右移动**。•	First, shift the bits to the right.
  - 接下来，**将之前的两个最右边的位进行XOR**。•	Next, take the previous two rightmost bits and XOR them.
  - **输出将被分配到最左边的位**。•	The output will be assigned the leftmost bit.

---

![](/static/2022-04-23-16-04-52.png)
![](/static/2022-04-23-16-06-56.png)

- 这个过程根据我们的需要不断地进行。•	This process continues as often as we need.
  - **直到回到state 0，，因为再进行下去就是重复步骤了，因为PRNG是决定性的**
- 寄存器的状态会更新，而输出位会逐渐形成我们的输出。•	The state of the register will update, and the output bits will gradually form our output.
- **这是一个非常简单的系列操作，产生看似随机的输出**。•	This is a very simple series of operations to produce seemingly random output.
- **使得这个过程适合于几种不同的操作**。•	Makes this process good for several different operations.
- <font color="deeppink">注意，寄存器本身不会构成最终随机数的输出，，每轮抽出来的是随机数的一位</font>
  - The registers themselves are not overly important after the fact.
  - 我们感兴趣的是这个过程中每一步的输出(最右位rightmost bit)。•	What we are interested in are the outputs from each step of the process.

---

这里说明了16轮移位寄存器操作的输出。•	Visual here illustrates the output of 16 rounds of shift register operations.

![](/static/2022-04-23-16-13-00.png)
![](/static/2022-04-23-16-21-36.png)

- 我们可以看到**没有重复的情况**。•	We can see that there are no repetitions.
  - 周期内最大唯一排列状态数=14(unique states),,,state0的寄存器值给了一种指示
- 这很好，因为这提供了随机性的错觉。•	This is good, as this provides the illusion of randomness.
- 这里的**分布似乎是随机的**。•	The distribution here is seemingly random.
- 绿色的一列说明了16轮LSFR的输出。•	Columns in green illustrate the output of 16 rounds of LSFR.
- <font color="deeppink">初始状态state 0 一般由计算机时钟生成</font>

# How good are LFSRs?

- **它们通常在密码学上是不安全的**。•	They are typically not cryptographically secure.
- 根据我们选择种子位阵列的方式，可以使它们在**一定程度上具有加密安全性**。•	They could be made somewhat cryptographically secure depending on how we select the seed bit array.
  - 例如，如果我们**使用一个真随机位序列，这将加强序列的随机属性**。•	If we use a real random bit sequence for instance, this will strengthen the random properties of the sequences.
  - 我们还想利用比4位大得多的寄存器！但序列最终会重复。•	We would also want to leverage on much larger registers than 4-bits!
- **然而，该序列最终会重复**。•	However, the sequence will eventually repeat.

# Problems with PRNG: Netscape

- 1996年，两名计算机专业的学生发现了网景公司**PRNG中的一个缺陷，使他们能够轻易破解SSL**。•	In 1996 two Computing students discovered a flaw in Netscape’s PRNG that enabled them to easily break SSL.
  - 安全套接字层，现在被传输层安全取代。•	Secure Sockets Layer, now replaced by Transport Layer Security.
- 当时，美国政府将加密产品视为军火，并阻止其向非美国人出口。•	At that time, the USA government viewed crypto products as munitions and prevented their exports to non Americans
  - 美国的SSL密钥长度为128位，用暴力攻击需要很长时间才能破解。•	The USA SSL key length was 128 bits, which would take a long time to break with a brute force attack.
  - 出口版本的密钥长度为40比特，这需要2^40≈10^12次尝试。•	Export versions had a key length of 40 bits, which would require 2^40 ≈ 10^12 attempts.
  - 几乎任何人都可以用暴力攻击来破解出口版。•	Just about anyone could break the export version with a brute force attack.

---

- 一个随机数生成器被调用4次来生成密钥。•	A random number generator was called 4 times to generate the key.
- **其弱点在于选择初始种子的方式**。•	The weakness was in the way the initial seed was chosen.
  - 它是基于进程号、父进程号和时间（毫秒）。•	It was based on the process number, the parent process number and the time in milliseconds.
- **攻击者很容易找出进程号和时间，并将其精确到秒**。•	It was fairly easy for an attacker to find out the process numbers and the time to the nearest second.
- 通信是由一方发送一个随机数，另一方用加密的版本进行回复。•	Communications were initiated by one party sending a random number and the other replying with the encrypted version.
  - 因此，一个已知的明文攻击是可能的。•	Thus a known plaintext attack was possible.
- 只需要106次尝试就可以破解美国和出口版本的Netscape SSL。•	Just 106 attempts were needed to break both the USA and export versions of Netscape SSL.
  - 在当时的标准PC上需要25秒。•	It took 25 seconds on a standard PC of the time.

# =========

# 加密安全伪随机序列：Cryptographically Secure Pseudo- Random Sequences

- 这个序列**看起来是随机的，也是不可预测的（但是不是重复的**。The sequence looks random and is also unpredictable.
  - 对序列中所有前面的数字的了解并**不能使其更容易预测序列中的下一个数字**。•	Knowledge of all the preceding numbers in the sequence does not make it easier to predict the next number in the sequence.
- **不可能计算出生成一个给定序列的种子**。•	It is not possible to calculate the seed to generate a given sequence.
  - 因此，我们无法找到能够生成事先提供的随机数字短序列的种子。•	So we cannot find the seed which will generate a short sequence of random numbers provided in advance.
- **椭圆曲线**是常用的加密安全随机数生成器。•	Elliptic curves are commonly used cryptographically secure random number generators.

# 椭圆曲线：Elliptic curves

- 原理是利用数学公式来**计算二维空间中一个坐标的位置**。•	Principle is to leverage on mathematical formula in order to calculate the position of a coordinate in 2D space.
- 我们的想法是，用一些秘密值来控制我们用以下曲线修改位置的频率。•	The idea is that some secret value is used to control how often we modify the position with the following curve:
  - `𝑦 = 𝑥3 + 𝑎𝑥 + 𝑏`
- 我们想从某个起始位置计算出结果位置（以一个模数值为界）。•	We want to calculate from some starting position, the resulting position (bounded by a modulus value).
- 这是由一些**私密信息控制的**。•	This is controlled by some private information.

---

![](/static/2022-04-23-19-58-14.png)

- 这些点会以一种**看似随机的方式**在这条曲线上移动。•	The points will move around across this curve in a seemingly random manner.
  - 实际是依赖于数学公式的deterministic process
- 我们的目标是让这些点继续移动，**直到我们用完所有预定的迭代**。•	The objective is to have the points continue to move around until we have exhausted all the predetermined iterations.
- **攻击者很难通过观察原点和迭代次数的坐标来猜测**。•	It is difficult for an attacker to guess by looking at the coordinates where both the point of origin is and the number of iterations.
- 𝑔^𝑎 其中𝑔是生成器点，𝑎是我们乘以的次数。𝑔𝑎 where 𝑔 is the generator point and 𝑎 is the number of times we multiply it.

---

why secure

![](/static/2022-04-23-20-00-03.png)

- 如果我们在二维空间得出的坐标。•	If we arrive at the resulting coordinate in 2D space.
- **我们并不提前知道发生器被操作的频率**。•	We do not know ahead of time how often the generator has been operated on.
  - 这是永远不会透露的秘密信息。•	This is secret information that is never revealed.
- 可以透露的是输出。•	What can be revealed is the output.
- **因为我们无法猜测种子，这使得它在密码学上是安全的**。•	Because we can’t guess the seed, this makes it cryptographically secure.
  - 从这一点上很难猜到下一个序列会是什么。•	It is difficult to guess what the next sequence is going to be from this point.

# Dual_EC_DRBG缺陷

- 这是双椭圆曲线确定型随机数发生器的缩写。•	This stands for Dual Elliptic Curve Deterministic Random numBer Generator.
- 椭圆曲线是任何具有以下形式的曲线•	An elliptic curve is any curve with the form
  - `𝑦2 = 𝑥3 + 𝑎𝑥 + 𝑏`
- 2007年，NIST发布了一个新的随机数标准，有4种算法。推荐和默认的算法是Dual_EC_DRBG。•	In 2007 NIST issued a new random number standard with 4 algorithms. The recommended and default algorithm was Dual_EC_DRBG.
- 它是由NSA推荐的。•	It was recommended by NSA.
- 当时已经有人对该算法提出了质疑。•	There were already questions about the algorithm.

---

- 来自微软的Dan Shumow和Niels Ferguson证明，该算法有一些秘密参数，用于推导公共参数。•	Dan Shumow and Niels Ferguson from Microsoft demonstrated that the algorithm had some secret parameters used to derive the public parameters.
- 这些秘密参数可以用来从32字节的输出中恢复PRNG的所有内部运作。•	These secret parameters could be used to recover all the internal workings of the PRNG from 32 bytes of output.
- 当时密码学界的知名人士，如Bruce Schneier，建议不要使用Dual_EC_DRBG。•	Prominent figures in the crypto community at the times, such as Bruce Schneier, recommended that Dual_EC_DRBG should not be used.

---

OpenSSL

- 这是一个开放源码的密码实现库。•	This is an open source library of crypto implementations.
- 用户可以在几个不同的伪随机数生成器中进行选择。•	The user could choose between several different pseudo-random number generators.
  - Dual_EC_DRBG是其中之一，虽然不是默认的。•	Dual_EC_DRBG was one of them, although not the default.
- **2013年12月发现的一个编码错误意味着不可能真正选择Dual_EC_DRBG作为随机数发生器**。•	A coding bug discovered in December 2013 meant that it was impossible to actually choose Dual_EC_DRBG as the random number generator.
  - 没有任何错误报告，因此没有人真正尝试过。•	There were no bug reports, and so no one had actually tried.

# NIST and RSA

- RSA组织根据NSA的建议，从2004年开始在其产品Bsafe中使用Dual_EC_DRBG作为默认值。•	The RSA organisation used Dual_EC_DRBG in its product Bsafe as the default, on recommendation from the NSA, starting in 2004.
- 2013年9月，NIST强烈建议不再使用Dual_EC_DRBG。•	In September 2013 NIST strongly recommended that Dual_EC_DRBG no longer be used.
- RSA随后也建议不要使用它。•	RSA then also recommended that it not be used.

**因此一般首选公开密码标准，因为公众能参与漏洞发掘， 而一般不使用标准库的系统被视为是不安全的**

# Summary

- 基于键盘延迟时间或物理波动的真随机数•	Real random numbers based on keyboard latency time or physical fluctuations
- 伪随机数是可预测的，但有随机统计。•	Pseudo-random numbers are predictable but have random statistics.
- 密码学上安全的伪随机数--知道序列的一部分不能产生其他的。•	Cryptographically secure pseudo-random numbers - knowledge of part of the sequence can't generate the rest.