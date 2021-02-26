# Overview

![](/static/2021-02-10-18-51-29.png)

* 公钥加密 Public key systems for encryption.
* 中间人攻击，认证必要Man in the middle attacks and the need for authentication.
* 公钥加密-电子签名Public key systems for digital signatures.
* 信息摘要 Message digests.

* [Overview](#overview)
* [传输秘钥问题：Problem-Communicating a Secret](#传输秘钥问题problem-communicating-a-secret)
* [公钥加密：Protocol: Public Key System](#公钥加密protocol-public-key-system)
  * [例子：Example: Alice sends a Secret to Bob](#例子example-alice-sends-a-secret-to-bob)
  * [密钥/信息认证问题：Key/Message Authentication Problem](#密钥信息认证问题keymessage-authentication-problem)
    * [身份认证问题：Problem-Authentication](#身份认证问题problem-authentication)
  * [中间人攻击：Man in the Middle Attack](#中间人攻击man-in-the-middle-attack)
    * [中间人攻击局限性：Problem-Authentication](#中间人攻击局限性problem-authentication)
* [公钥-电子签名：Protocol-Public Key Digital Signatures](#公钥-电子签名protocol-public-key-digital-signatures)
  * [电子签名-性质：Properties of Digital Signatures](#电子签名-性质properties-of-digital-signatures)
  * [保密和真实性：Secrecy and Authenticity](#保密和真实性secrecy-and-authenticity)
  * [公钥证书：Public Key Certificates](#公钥证书public-key-certificates)
  * [电子签名局限性-Problem: Authentication without Revelation](#电子签名局限性-problem-authentication-without-revelation)
    * [信息摘要：Protocol-Message Digest](#信息摘要protocol-message-digest)
    * [信息摘要结合电子签名：Message Digests & Signatures](#信息摘要结合电子签名message-digests--signatures)
* [公钥加密算法：Public Key Algorithms](#公钥加密算法public-key-algorithms)
  * [RSA: Public Key Encryption- RSA](#rsa-public-key-encryption--rsa)
    * [RSA Algorithm](#rsa-algorithm)
  * [Diffie-Hellman Key Exchange](#diffie-hellman-key-exchange)
    * [D-H algorithm](#d-h-algorithm)
    * [Security of D-H](#security-of-d-h)
  * [Elgamal Public Key Systems](#elgamal-public-key-systems)
    * [椭圆曲线密码学：Elliptic Curve Cryptography](#椭圆曲线密码学elliptic-curve-cryptography)
      * [原理：Elliptic Curve Crypto](#原理elliptic-curve-crypto)
    * [优点：Advantages of Elliptic Curve Cryptography](#优点advantages-of-elliptic-curve-cryptography)
* [信息摘要：Message Digest](#信息摘要message-digest)
  * [信息摘要要求：Requirements of Message Digest](#信息摘要要求requirements-of-message-digest)
  * [信息摘要-Hash函数：A Survey of Hash Functions](#信息摘要-hash函数a-survey-of-hash-functions)
    * [SHA-3 Competition](#sha-3-competition)
* [随机数：random number](#随机数random-number)
  * [生成真实随机序列：Generating Real Random Sequences](#生成真实随机序列generating-real-random-sequences)
  * [生成伪随机数序列：Pseudo-Random Sequences](#生成伪随机数序列pseudo-random-sequences)
    * [问题-Netscape伪随机序列生成器：Problems with PRNG](#问题-netscape伪随机序列生成器problems-with-prng)
    * [问题-Dual_EC_DRBG随机数生成器](#问题-dual_ec_drbg随机数生成器)
      * [转折：OpenSSL](#转折openssl)
      * [不再推荐使用：NIST and RSA](#不再推荐使用nist-and-rsa)
* [JAVA：Public Key Systems](#javapublic-key-systems)

# 传输秘钥问题：Problem-Communicating a Secret

![](/static/2021-02-10-18-53-36.png)

 单密钥加密算法，发现了密钥分配的问题,且单秘钥加密很难解决该问题 We looked at single key encryption algorithms and discovered the problem of key distribution.

# 公钥加密：Protocol: Public Key System

![](/static/2021-02-10-18-57-04.png)

* 两个密钥
  * **公钥** public key is used for encryption
    * 对所有人公开
    * 解决密钥分配问题 solves the key distribution problem
  * **私钥** secret key is used for decryption
    * Knowledge of the public key will not lead to knowledge of the secret key.
* 通常一个算法
  * 公钥加密 used with the public key encrypts
  * 私钥解密 used with the secret key decrypts

## 例子：Example: Alice sends a Secret to Bob

![](/static/2021-02-10-19-05-04.png)

* 不安全媒介获取公钥，安全地方加密
* 不安全媒介传输密文，只能用秘钥在安全区域解密获取明文

---

公钥使用

![](/static/2021-02-10-19-08-53.png)

* 不安全介质获取某人的公钥，
* 利用一个算法`X`&公钥作为参数，安全区域加密明文
* 通过不安全媒介传输密文，利用相同算法`X`&私钥作为参数，安全区域解密密文，拿到明文

:orange: 不存在密钥分配问题(因为使用公钥加密) no key distribution problem because the encryption key is public

## 密钥/信息认证问题：Key/Message Authentication Problem

![](/static/2021-02-10-19-12-28.png)

:orange: 不存在密钥分配问题(因为使用公钥加密)，但引入了密钥认证问题 no key distribution problem because the encryption key is public

:orange: key authentication 密钥认证

* 收方如何验证信息/秘钥确实来自发方？
  * 传输媒介不安全，所以信息有可能被篡改 trnasmission medium is insecure and so it could be substituted

:orange: message authentication 消息认证

* 需要解密的一方如何确认消息确实来自信任的发方
  * 因为所有人都可以使用公钥加密信息，并发送

### 身份认证问题：Problem-Authentication

![](/static/2021-02-10-23-09-52.png)

单秘钥系统中，存在认证问题 problems related with one key system

* 认证和密钥分配一样重要 Secrecy and authentication are nearly equivalent in one key systems.
  * 爱丽丝必须知道秘钥才能加密信息。
  * 她因此被授权使用它（可信）
* 如果多个人共享秘钥，无法验证发方身份  If several people share the secret key then it is not possible to determine which of them encrypted the message.

## 中间人攻击：Man in the Middle Attack

如何利用公钥系统中存在的秘钥/信息认证问题？ exploit the message/key authentication problems in public key system

![](/static/2021-02-10-23-01-27.png)

1. Bob sends his public key to Alice, but it is intercepted by Eve.
   1. doesnt arrived at Alice
2. Eve sends her public key to Alice, pretending it is Bob’s.
   1. Alice doesn't know it's Eve' public key
3. Alice encrypts the plaintext with Eve’s public key (thinking it is
Bob’s).
4. Alice sends the ciphertext to Bob.
5. Eve intercepts the ciphertext and decrypts it with her secret key.
   1. ciphertext doesn't arrived to Bob
6. She reads the plaintext and discovers what Alice has to say.
   1. She can also change Alice’s message at this stage.
7. She encrypts it with Bob’s public key and forwards it to Bob.
8. Both Alice and Bob are unaware of this attack.

![](/static/2021-02-10-23-05-56.png)

1. 发方公钥被中间人拦截，中间人将自己的公钥传输给收方
2. 收方用中间人公钥加密命名，中间人拦截密文用自己秘钥解密
3. 中间人可以篡改明文后，用发方公钥加密再传输给发方
4. 发方用自己秘钥解密中间人传来的密文

### 中间人攻击局限性：Problem-Authentication

中间人攻击局限性 limitations

* 是主动攻击 active attacks
  * Eve/Program必须置于Alice Bob之间进行截取

# 公钥-电子签名：Protocol-Public Key Digital Signatures

如何解决，公钥系统中存在的**认证问题**？ how to solve the authentication problem related to public key system?

![](/static/2021-02-10-23-24-16.png)

1. **Alice** encrypts her **plaintext document** with her **secret key**, rather than Bob’s public key.
   1. only Alice can sign the signature, cause only Alice the owner has the secret key
2. She sends the **(plaintext) document, together with the encrypted version**, to Bob.
3. Bob **decrypts the encrypted version with Alice’s public key** and **verifies that they are both the same**.

The encryption and decryption operations must cancel each other, no matter which order they are used

---

![](/static/2021-02-11-00-03-53.png)

## 电子签名-性质：Properties of Digital Signatures

![](/static/2021-02-11-00-05-07.png)

1. 收方拥有公钥解密签名，任何人都可以验证发方身份 Bob can verify the signature without Alice’s help.
2. 签名不能被伪造，因为只有秘钥拥有者能生成签名 The signature cannot be forged because only Alice knows her secret key.
3. 签名文件不能被篡改 The signed document in unalterable, except by Alice.
4. 签名不能转移到另一文件中 The signature cannot be transferred to another document.

## 保密和真实性：Secrecy and Authenticity

![](/static/2021-02-11-00-26-29.png)

签署文件并不意味着文件安全 Signing a document does not make it secure.

* 注意，Eve也可以撤销签名并读取文件 Notice that Eve could also undo the signature and read the document.
* 因为Eve也可以获取公钥 She can get Alice’s public key.

:orange: 所以单纯利用签名也不能保证真实性 Encrypting a document does not guarantee authenticity.

* **电子签名结合加密文件**可以提供保密和身份认证。 Signing and encrypting a document can provide both secrecy and authentication.

---

:orange: Signing and Encrypting 方法

![](/static/2021-02-11-00-36-02.png)

* complex

## 公钥证书：Public Key Certificates

电子签名-互联网应用

![](/static/2021-02-11-00-39-09.png)

在许多情况下（网上购物，需要公钥进行认证），要签署的纯文本文件包含一个公钥。 In many cases the plain text document to be signed contains a public key.

* **签署的密钥（Trent的秘钥）加上其他信息称为公钥证书**。 The signed key plus other information is called a public key certificate.
  * 证书被可信任第三方签署 It is signed by **Trent**, a trusted third party.
  * **Trent公钥**也必须以安全的方式分发 Trent’s public key must also be distributed in a secure way.
* **Bob可以在公钥证书中分发他的公钥** Bob can distribute his key in a public key certificate.
  * **前提是Alice已经有Trent的公钥，可以验证证书可靠性** Provided Alice already has Trent’s public key.
  * A利用Bob的公钥用来加密明文，传输给B

解决了中间人攻击问题：This defeats a man in the middle attack.

* Eve可以截取Trent签属的公钥证书（包含信息&Bob的公钥），但是最后只能获取Bob公钥
* **Eve不能用自己的公钥替换Bob的公钥**，而用自己的秘钥创建一个新公钥证书，因为它不是可信任的第三方（无法通过身份认证）

## 电子签名局限性-Problem: Authentication without Revelation

![](/static/2021-02-11-01-03-32.png)

在前面的例子中，**签署的文件与未签署的文件长度相同**  In the preceding example the signed document was the same length as the unsigned document.

* **这可能会很不方便，特别是当必须存储许多已签名的文档时** This can be inconvenient, especially when many signed documents must be stored.

**如果只是加了一个签名，那么签名文件的内容也会成为公知** If just a signature is added, then the contents of the signed document also becomes public knowledge.

* 通过签名者的公钥解密签名内容 It can be seen using the public key to undo the signature.
  * 中间人还是可以截取Trent证书，获取签名内容

### 信息摘要：Protocol-Message Digest

通过信息摘要解决签名内容泄露 & 签名过长问题

![](/static/2021-02-11-01-06-37.png)

信息摘要是原始文档的浓缩版本。A Message Digest is a condensed version of an original document.

* 它通常长约256位。It is typically around 256 bits long.

它是原始文档的一个唯一的简短描述 It is a unique short description of the original document.

* Like a fingerprint.
* **两个不同的文件产生相同的信息摘要的几率非常低**。 The chance of two different documents producing the same
message digest is very low.
  * 产生信息摘要的算法有时称为哈希函数 The algorithm to produce a message digest is sometimes called a **hash function**.

### 信息摘要结合电子签名：Message Digests & Signatures

很好解决了**需要对明文所有信息签名（过长）** & 签名内容泄露问题

![](/static/2021-02-11-01-11-20.png)
![](/static/2021-02-11-01-21-38.png)
![](/static/2021-02-11-01-41-19.png)

* 给别人一份带签名的信息摘要，相当于给别人一份签名（因为文档的信息摘要唯一）。 Giving someone a signed copy of a message digest is equivalent to giving them a signed copy of the document.
* 如果需要检查签名内容，从原始文件中第二次得到信息摘要。 If the signature needs to be checked then the message digest can be calculated from the original document a second time.
* **可以通过解密签名来恢复信息摘要**。The original message digest can be recovered by removing the signature.
* 如果两个消息摘要相同（解密获取的&从明文中生成的），那么签名就有效。 If both the message digests are the same then the signature is valid.

:orange: 注意此方法要利用混合加密，2对非对称加密的公钥私钥，和一对对称加密密钥

* 混合加密 - 传输明文
* 公钥加密&信息摘要 - 身份验证

# 公钥加密算法：Public Key Algorithms

## RSA: Public Key Encryption- RSA

![](/static/2021-02-11-01-51-22.png)

The RSA algorithm is based on number theory.(基于math)

* 它的安全性是基于对2个大质数的乘积进行整数分解的难度。Its security is based on the difficulty of factoring an integer that is the product of 2 large prime numbers.
* 有些问题，被称为NP-Complete问题，被认为是本质上的困难，不会有快速的解决方案。 Some problems, called NP-Complete problems, are thought to be intrinsically difficult and no quick solution will be found.
  * 整数因子化不是其中之一。Integer factorisation is not one of these.
  * **即，可能会找到一个快速的解决方案(破解RSA)，在这种情况下，RSA算法将不再有任何好处**。 A fast solution may be found, in which case the RSA algorithm will no longer be any good.
    * 最近比较找到了一种证明整数是否为质数的快速算法 A fast algorithm to prove whether an integer is a prime number or not was found relatively recently.

---

history

![](/static/2021-02-11-01-55-58.png)

RSA was invented by an English mathematician Clifford Cocks on his first day of working for GCHQ in 1973. RSA是由英国数学家Clifford Cocks在1973年为GCHQ工作的第一天发明的。

* Unfortunately GCHQ kept it secret until 1997.不幸的是，GCHQ一直保密到1997年。

It was independently invented by 3 mathematicians at MIT: Rivest,
Shamir, Adleman.

* The patented it, formed the RSA company and became very rich.

### RSA Algorithm

![](/static/2021-02-11-01-57-56.png)

* 随机质数 p q
* n=p*q - public
* d - decryption parameter(secret key)
* e - encryption parameter(public key)， inverse of d
* `d*e = 1 modulo (p-1)(q-1)`
* 3*1/3=1, 4* 1/4=1 inverse
* 如(p-1)(q-1) & d 有公因子，则inverse e不存在？？？？？？

---

![](/static/2021-02-11-02-07-52.png)

* slow
* only used for message digest & transfer the single-key-private-key, the plaintext is encrypted using single key(triple-DES or AES)

---

![](/static/2021-02-11-02-15-57.png)
![](/static/2021-02-11-16-06-30.png)

* 加密解密算法相同，指数函数
* 找到p，q解除d解密参数，秘钥
* 非常慢

## Diffie-Hellman Key Exchange

:orange: 同样采用指数算法，解决不同问题

* A&B想创建一个会话密钥，共享信息
  * <font color="deeppink">E可以监听通信，但不知道密钥详情</font>
* **RSA实际用来共享会话密钥（传输会话密钥，用互相的公钥来加密，交换后各自用私钥解密获取会话密钥，再用会话密钥解密密文）**，RSA is you is normally used to actually share a session key
  * 他会直接通过**创建会话密钥，然后会话密钥被用作加密** So it'd be held held approach does that directly by creating the session key and then the session key is used as encryption.
* 所以一个人，生成一个公号，密号，So, this person generates a public and secret number
  * 交换公号，**用自己的密号和对方的工号生成会话密钥**they exchanged the public numbers, and then they generate the keys with their secret number and the other person's public number.
  * 所以密钥不能只由公开信息生成  So the key cannot be generated just from the public information.

Diffie-Hellman密钥交换采用指数算法，以实现 产生一个**由两个人共享的单一密钥**。The Diffie-Hellman key exchange uses an exponential algorithm to generate a single key that is shared by two people.

* 双方通过不安全的通信渠道共享信息，使用密钥。 Both parties contribute to the key by sharing information over an insecure communication channel.
* 每一方都会对一些信息进行保密。这些秘密信息对于能够构建密钥至关重要。Each party keeps some information secret. This secret information is vital to be able to construct the key.
  * 每个人都要生成一个公共号码和秘密号码Each person generates a public and secret number.
  * 他们交换公共号码。They exchange the public numbers.
* 密钥不能从公共信息中生成 The key cannot be generated from the public information.
* **这种算法构成了许多互联网应用中会话密钥构建的基础**This algorithm forms the basis for session key construction in many internet applications

### D-H algorithm

![](/static/2021-02-11-16-21-54.png)

* 基于两个A&B已知要使用的公号 based on 2 public numbers that they're going to use
  * `p` , large number
  * `g`, primitive root of `p`
* A & B 创建随机数，`xA`,`xB`
  * 他们的秘钥 thir secret keys
* A & B 指数计算`y = g^x % p`
  * 他们的公钥 their public keys
  * **A&B交换这些数字** exchange these numbers
  * 如，A 计算 B的公钥`yb= g^xa % p`
* A计算 `K= Yb^xa % p = (g^xb)^xa % p = g^xa,xb %p`
* 跟RSA一样慢， just as slow as RSA

### Security of D-H

![](/static/2021-02-11-16-35-25.png)

但是基于这个的安全性是什么呢？安全性是基于这个方程的硬度，这个方程叫做离散对数问题。那么为什么你用的是公开的信息位，P是公开的都是公开位，x是隐藏值。所以如果你知道y、g和P，你解这个方程，如果你能解这个方程，你就能计算出x，所以如果所有的数都足够大，那么这个在计算上是无法解决的。所以数论，我又不打算讲了，表明这相当于因子。所以，如果你能找到一个快速的方法来分解数字，那么你就会打破RSA和Diffie Hellman。
But what's the security based on this? The security is based on the hardness of this equation, which is called the discrete logarithm problem. So why is the public bit of information do you use public, P is public are all public bits and x is the hidden value. So if you know y, g and P, and you solve the equation, and if you can solve this equation, you can calculate x. And so if all the numbers are big enough, then this is computationally infeasible to solve. And so number theory, again, I'm just not going to cover shows that this is equivalent to factoring. So if you can find a fast way to factor numbers, then you'll break RSA and diffie Hellman

如果你能解决离散对数问题 那么你也可以破解RSA了 对，所以这就是差分海尔曼。所以RSA和Diffie Hellman都涉及到解决棘手的数学问题。所以这引起了很多好莱坞的情节。通常会有一个主角，他是一个杰出的数学家，突然失踪了。那是因为他是自己的代数问题，但在大多数电影中，数学家的结局并不好。所以，如果你是一个数学家，你解决这个问题... ...你如何保持生存与一个困难的问题 So and also, if you can solve the discrete logarithm problem, then you'll also break RSA. Right, so that's diffie Hellman. And so both of these both the RSA and diffie Hellman involve solving tricky mathematical problems. So this gives rise to loads of Hollywood plots. And so there's usually a protagonist, which is who's a brilliant mathematician who suddenly goes missing. And that's because he's self the factoring problem and But in most of these movies, it doesn't end well for the mathematician. So if you're a mathematician and you solve this problem How do you stay alive with a difficult question?

## Elgamal Public Key Systems

![](/static/2021-02-11-16-43-21.png)

* 这个系统是基于Diffie-Hellman密钥交换的。This system is based on Diffie-Hellman key exchange.
* 它和RSA一样安全。 It is just as secure as RSA.
  * 它不像RSA那样方便。It is not quite as convenient as RSA.
  * 但它可以在没有许可证的情况下使用。But it can be used without as license.
* 它可以用于电子签名 It can be used to sign documents.
* 数学原理稍微复杂一些。The maths are slightly more complex.

---

所以RSA算法给了我们... ...给了我们签名之类的东西。而ElGamal系统是基于Diffie Hellman的。**所以它是数字签名和公钥加密的另一种方式。所以它和RSA一样安全，但没有RSA方便**。所以它是由El Gamal发明的 他是Martin Hellman的一个博士生。所以作为一个不同的Hellman试图与RSA竞争。**所以El Gamal系统的优势在于它可以在没有许可证的情况下使用，而RSA过去对任何使用他们技术的人都要收取许可证。如今，已经不用了，现在已经被RSA取代了，还有椭圆曲线密码学**

So the RSA algorithm gives us gives us signatures and the like. And the ElGamal system is based on diffie Hellman. So it's an alternative way of doing digital signatures and also public key encryption. So it's just as secure as RSA, but it's not as convenient as RSA. So it was invented by El Gamal who's one of Martin Hellman, PhD students. So as a diffie Hellman attempt to, to compete with the RSA crowd. So the advantage of the elgamal system is that it can be used without a license, whereas RSA used to charge a license to anyone using their technology. These days, it's not used. I'm just mentioning, here's an interesting curiosity. It's been superseded by RSA being free now and also elliptic curve cryptography. So what is elliptic curve cryptography.

### 椭圆曲线密码学：Elliptic Curve Cryptography

![](/static/2021-02-11-16-59-12.png)

所以，首先，我们知道椭圆是什么，但它没有什么更多的它与椭圆和椭圆或扁圆没有直接关系。所以还有一个椭圆的公式。所以注意它有x平方减y平方。但是在计算中使用椭圆有一个问题，就是计算椭圆部分的距离。这一点非常重要，因为椭圆是行星的轨道，卫星会接受椭圆的请求，宇宙飞船在没有动力的情况下，也会接受椭圆的请求。所以计算它们航行了多长时间是相当重要的，而且这种计算必须用数值积分来完成，但后来做了相当多的工作，试图找到一个计算距离的公式。这是前段时间几个诗人A写的一篇混沌理论论文。所以，最后我们不得不用数值积分的方法来兔子关于追踪距离的问题。所以这是一个小题大做，但是你曲线是在试图从嘴唇的距离领域找到数学函数的过程中产生的曲线。所以这就是椭圆椭圆位在他们名字中的由来So, first of all, we know what the ellipse is, but it's nothing more it's not directly related to ellipses and ellipses or squashed circle. So there's an equation for another ellipse. So notice it's got x squared minus y squared. But one problem with using ellipses in calculations is calculating the distance partway around the ellipse. And this is quite important because ellipses are the orbits of planets and satellites take requests to an ellipse and also spaceships when they're coasting without power. And so calculating how long they've traveled is quite important and bye This calculation has to be done using numerical integration, but then quite a lot of work done on trying to find a formula to calculate the distance stay away. crops up in surprising places to places. Here's a chaos theory paper written by a couple of poets A while back. And so, in the end we had to use numerical integration to the rabbits about tracing the distance. all round ellipses. So That's a little digression but you curves are curves that arise in an attempt to find a mathematical function from the distance realm of lips. So that's where the elliptic elliptic bit comes in their name. 

![](/static/2021-02-11-17-03-10.png)

而一般他们的方程与类似的东西。比如y平方等于a立方加上其他的一些东西。所以y的平方等于x的立方加上一些其他的位子和x这是一个典型的椭圆曲线，**这个是用这个曲线用一个位币。所以这是一个公式 所以，所以这是相当典型的，我们得到一个周线。左边是没有曲线的地方，还有一个位币** 好了，史蒂夫的讣告最终会消失。在X和Y两个方向的无限大 我知道它是对称的关于X轴， 这就是Y平方形成。And generally their equations with something like that. Like y squared equals A cubed plus some other. So y squared equals x cubed plus some other bits and x Here's a typical elliptic curve and this one is using this curve using a bit coin. So it's a formula there. And so so this is fairly Typical and that we get a weekly line. So there's been On the left, where there's no curve and the bit All right, Steve obits are eventually going to go off. To infinity both in the x and the y direction I know that it's symmetrical about the x axis, that's where the Y squared becomes. 

#### 原理：Elliptic Curve Crypto

那么，椭圆曲线加密怎么，怎么操作呢？So, how, how does elliptic curve crypto work?

![](/static/2021-02-11-17-06-58.png)
![](/static/2021-02-11-17-06-51.png)

所以我们有一条这样的曲线，我们有一组曲线上的所有点。所以我们沿着曲线绕行。就像一条弯曲的线，这样我们就可以沿着这条曲线的距离设置所有的点。对了，现在如果我们... 现在，如果我们... ...如果我们画一条穿过这条曲线的线... ...那么它将穿过这条曲线多少次... ...而解方程将给我们一个x的方程，并且有一个立方程。所以反而会有三种解法。所以任何一条直线都会把曲线切成三点，还有几种特殊情况，如果有两个点是一样的，然后有你切一条平行于y轴的线，它就会碰到曲线。有一个刚好绕过x轴上白色的负二点直上直下。这样就可以把曲线切到无穷大。所以我们可以用这个线切割的业务来定义一个加法 对于找算式和兴趣点里面之间的一个乘法 它就像零加法一样，也有线只发生一次切割的情况，但与我们无关。So we've got one of these curves and We have a set of all points on the curve. So We follow the curve round. It's like a bent line, so that we can have the distance along this curve sets up all the points. Right. Now if we if we draw a line that cuts through this curve then how many times will it go through the curve while solving the equation will Give us an equation in x, and there's a cubic equation. So instead There'll be three solutions. So any straight line will cut the curve in three points and a couple of special cases, if two of the points are the same, and then there's your cut a line that's parallel to the y axis, it just touches the curve. There's one just round about the minus two bit on the white on the x axis goes straight up and down. That will cut the curve at infinity. So we can use this line cutting business to define an addition For a multiplication between finding arithmetic and inside the point of interest It is like zero addition and there are also cases where the Line only cuts occurred once but they don't concern us.

所以我们可以找到一种叫做修改的东西。如果我们选择两点作为曲线上的两点，然后有一条线穿过这两点 然后有一条线穿过这两点 我们沿着这条线一直走到一个杯子里，直到那个点 看，我们可以写成C是B的8倍，这就定义了我们所说的乘法。所以这基本上就是椭圆曲线的采用方式。我们在椭圆曲线上定义了这个特殊的数学趣味。然后我们用这个图来进行计算，度量。写实数很明显的在我们身上，因为我们喜欢用整数来计算，所以整数多于或者类似的东西都会用到。So We can find something called modification. By if we pick two points Be the two points on the curve. Then there's The one line that goes through that through those two points We follow it all the log into a cup Come up again, till that point See, then we could write C is eight times B. And that defines what we mean by multiplication. So that's basically how elliptic curves adopt. We define this special funny with mathematics on elliptic curves. And then we do the calculations using this map. metrics. Write real numbers obviously on us because we like to work with integers and so integers more than or something similar are used. 

### 优点：Advantages of Elliptic Curve Cryptography

既然计算这么费劲，为什么还要用这个呢

![](/static/2021-02-11-17-08-31.png)
![](/static/2021-02-11-17-20-07.png)

* 现在，IT安全又是基于**最难的一个离散对数问题，但是密钥大小要小很多**。Now, IT security is based on the hardest of a discrete logarithm problem again, but the key sizes are much smaller.
  * 使用椭圆曲线的安全性是基于离散对数问题的硬度。Security using elliptic curves is based on the hardness of a discrete logarithm problem.
* **所以256位的椭圆曲线密码学提供的安全性与3072位的RSA相同**。 So 256 bit elliptic curve cryptography provides the same security as 3072 bits of RSA
  * 所以有不到10%的位数在里面。这意味着运算速度要快得多，因为数字越大，运算时间越长，所以国家安全局的任务是美国安全局。384个84位的密钥。So there's Less than 10% of the bits in it. So that means that the arithmetic is so much faster Because the bigger the number is the higher The longer it takes to do maths So the NSA mission Security Agency of America. You Is 384 84 bit keys
* 而比特币用户如此椭圆曲线的公钥系统 所以，有一点NIST，也就是美国国家标准与技术研究所代表了一种特殊的曲线，但后来发现疑似被RSA，也就是宣传它的组织给废止了 And Bitcoin users so elliptic curve the Public Key System So, one point NIST, which is the American National Institute for Standards and technologies represented a particular curve, but was later found suspected was deprecated by RSA, the organization that was publicizing it
* 所以我已经提到了比特币的曲线，还有一个曲线25519现在很流行。所以这就是这个椭圆曲线的公式。mentioned the Bitcoin curve, there's another one curve 255 point nine is now quite popular. So that's the formula for this elliptic curve.
  * 所以，基于密码学的复制品的公钥系统已经定义了比特币使用，所以它们也可以用于随机数生成器和分解算法 So public key systems based on the replica of cryptography have been defined Bitcoin uses so they can also be used in random number generators and factoring algorithms.

# 信息摘要：Message Digest

![](/static/2021-02-11-19-46-42.png)

信息摘要(MD)是一个较小的 "指纹"，可以唯一地识别信息。 The message digest (MD) of a message is a smaller ‘fingerprint’ that can uniquely identify the message.

* 信息摘要可以用秘钥生成签名(eg. signed MD5, 之后用于公钥解密进行身份认证) A message digest can be signed with a secret key.
* 信息摘要的唯一性保证了其安全性，用秘钥加密消息摘要与签属原始文件生成的电子签名一样有效 It will be just as valid as a signed version of the original document.
  * 提供不同的文件，不能创建相同的消息摘要。 Provided a different document with the same message digest cannot be created.
  * 创建一个新的文件，这个文件的信息摘要和之前的文件一样 So the reason for this condition for this to happen is that we can create a new document that has the same message digest as the previous document

## 信息摘要要求：Requirements of Message Digest

不能让两个不同的文件具有相同的信息摘要（否则，攻击者可以创建一个不同的文件并生成相同的信息摘要） Then, as far as contracts go, the attacker will then present this different document as the one that I've actually signed, and then hoping to whatever conditions are inside it. So that's obviously a very bad outcome. So so that's a condition for Mrs. digest. We can't have two documents that are different have the same message digest.

![](/static/2021-02-11-19-55-56.png)

* **给定信息，很容易计算出信息摘要** Given the message, it's easy to compute the message digest.
  * 效率选项，我们希望生成速度很快，但唯一可用的操作如果很慢，那么就需要妥协 That's just an efficiency option. We want the operations to be quick. If the only operations available are slow, then we'll have to accept them. But there are lots of ways of doing it. digest. So getting one that's quick doesn't impose any additional conditions.
* **给定信息摘要，很难计算出原信息** Given the message digest, it is hard to compute the message.
  * 现在，如果消息摘要被限制在256位，而原始文档要大得多，那么显然有更多不同的文档，那么就会有更多不同的消息摘要。所以，不同的可能消息摘要的数量是2的幂次方256和长度为10k 的文档，不同长度的消息摘要的数量是2的幂次方10k，显然这个数字必须是，所以这意味着总是有不止一个文档具有相同的消息摘要。如果我签署了一个合同，或者签署了一个合同的消息摘要，那么总是没有这个文档或者大量的文档有完全相同的消息摘要。Now if the message digest is limited to say 256 bits and the original documents are much bigger than that, then clearly there are More, more different documents then there are more different message digests. So, the number of different possible message digests are two to the power 256 and documents of length 10 K, the number of different ones with that length are two to the power of 10 K, which obviously must be the number So this means that there always will be more than one document with the same message digest. If I sign a contract, or sign the message digest of a contract, there will always be no The document or large number of them have exactly the same message digests. 
  * 所以，条件二基本上是说，计算信息并不是不可能的，这是不可行的。So, condition two is basically saying It's hard to compute the message is not impossible. It's computationally infeasible.
  * 怎么才能找到另一个文档有相同的消息摘要作为我？我会的。其中一个技巧就是穷举法。我可以查看所有可能的文件，计算需要消化的数量，看看这是否和我得到的是同一个人，所以理论上来说，这是可能的，但是计算上，如果计算所需的时间比宇宙的预期寿命还要长，那就不可行了 How would I find another document that has the same message digest as mine? I would. One technique is a brute force attack. I could look at all possible documents and calculate the to digest and see if this is digesting the same someone I've got and so theoretically That's possible but computationally it has to be infeasible if the calculations take longer than the expected lifetime of the universe.
* **给定信息`M`，很难找到另一个信息`M'`会产生相同信息摘要** Given a message M, it is hard to find another message M' with the same message digest. Preimage resistance of collisions
  * 和第二个条件一样，签`M`不签`M’`，但他们恰好有相同的信息摘要---Preimage resistance of collisions.I signed this contract. And so that's the message m. And the message is M dash is another contract that I don't want to sign that happens to have same message digest. So this is called preimage resistance of collisions.
  * 所以用技术语言来说，找到两个消息摘要相同的文档就是碰撞。 So in technical language, finding two documents with the same message digest is a collision.
* **在同一个消息摘要中很难找到两个随机的消息 m 和 m’**。(生日攻击或碰撞阻力)。**这是一个更难满足的要求** It should be hard to find two random messages M and M' with the same message digest. (Birthday Attack or postimage resistance of collisions). This is a harder requirement to meet
  * 前面的条件是给定消息`m`，要找到另一个产生相同信息摘要的信息 So the previous one is that given a message m, I've got to go and find another message and desperately same message digest.
    * 要做的就是写一个不想签的文档，然后用很多微小的这样的变化来改变它。在这里加一个空格或者去掉一个空格，或者增加一些不可打印的字符，直到找到&计算出我们要找的消息摘要 writing a contract that I don't want to sign and then changing it with lots of tiny little changes like that. Adding a space here or removing a space or adding some non printable characters until we find and calculate message digest until we find the message digest we're looking for.
    * 即，从一个文档开始，选中一个目标消息摘要。对另一个文档进行操作直到得到相同摘要 starting with one document, we one message digest that we're looking for. That's the target. So we get another logic document and manipulate it until you get the same message. digest.
    * 攻击者想让我签署他想让我签署的合同，只要让他们虚线，如果他能操纵他们两个人，那么就能更快、更容易地找到一个具有相同消息摘要的文档。你只要做一大堆的修改.把它们记录下来，然后把消息摘要存储起来就可以了.桌子上，然后可能会有一堆椅子 只是一个消息和破折号，然后计算出消息摘要，在桌子上查找。每次改成M破折号会有很多很多可能的目标。而不是只有一个目标，所以这就是为什么它很难达到要求，而且它的攻击也比较容易。attacker want me to sign the contract that he wants me to sign, just having them dashed and if he can manipulate both of them Then it's quicker and easier to find a document with the same message digest. You just make a whole bunch of changes. document them and store the message digests it. table and then might hold a bunch of chairs Just a message and dashed and then calculate the message digests and look them up in the table. Each change to M dash will have lots and lots of possible targets. Rather than just the one target so that's Why it's a hard requirement to meet and it's an easier attack. ---birthday attack
  * **所以，这基本上都归结为消息摘要必须足够长，必须有足够的空间**。So, this essentially this all boils down to message digests must be long enough must have enough space.
    * 信息越长，可能存在的信息就越多。因此，有越多不同的信息摘要。The longer they are, the more different possible messages there are. And so the more the more different message digests.
    * 发现其他消息具有相同消息摘要的概率不存在。the probability of finding another message with the same message digest is rejected
    * 如果消息摘要是一位的话，我们可以把这个问题提高到一个荒谬的程度，---那么一半的文档都会有相同的消息摘要。所以很容易找到一个相同的摘要。因此，我们添加的比特越多，就越难以攻击消息摘要。We can take this to an absurd level, if the message digest is one bit, so it's Either north or one, then half the documents will have the same message digests. So it's very easy to find one. So the more bits we add, the harder it is to be able to attack message digests.

## 信息摘要-Hash函数：A Survey of Hash Functions

![](/static/2021-02-11-20-59-29.png)

生成消息摘要的函数称为哈希函数。 Functions to generate message digests are called hash functions.

* **MD5** was invented by Ron Rivest (R in RSA)
  * 128bits
  * vulnerable to birthday attack
  * 因此不再使用
* **SHA-1**
  * 160 bits
  * vulnerable to birthday attack
  * 理应不再使用，但还是广泛被应用
* **SHA-2**
  * SHA-256 (or 224); SHA-512 (or 384) bits
  * Secure so far. Most widely used.
* **SHA-3**
  * SHA-3 public competition with adoption in 2012. 近些年通过的

### SHA-3 Competition

![](/static/2021-02-11-21-43-34.png)

* 继AES竞赛成功后，NIST于2007年11月宣布举办名为SHA-3的信息摘要竞赛。
* 截至2008年10月，共提交了64份申请
* 第一轮接受了51个，开始接受公众监督。
  * 约20人被破格录取。
* 14人进入第二轮。

---

![](/static/2021-02-11-21-44-39.png)

2010年12月公布了5个入围名单。
所有入围者的功能都根据公众的分析进行了调整。

2012年10月公布的冠军是Keccak。

* 他们的参赛作品明显比其他作品快。

NIST希望对Keccak稍加修改，以安全换速度，但由于不信任的气氛而打消了念头。

# 随机数：random number

随机数的重要性 & 我们应如何选取随机数？

* 如果攻击者可以反推随机数，推测出随机数，理论来讲能更快破解加密算法（如RSA）if an attacker could subvert the random number process and so that people pick p picking numbers that they can actually predict, then doesn't matter how secure RSA is. Theoretically, if random numbers are predictable, then it makes breaking it a lot easier.

---

:orange: 随机数定义

* **真实随机数序列**是一个数字序列，如果再次运行生成器就**不能重复** A real random number sequence is a sequence of numbers which cannot be repeated if the generator is run again.
* **伪随机数序列**看起来像一个真正的随机数序列，**但可以重复**。A pseudo-random number sequence looks like a real random number sequence but is repeatable.
  * 给定一个种子值，开始计算。如果种子是相同的，那么所有后续的值都是完全相同的。give it a seed value to start it off. And if the seed is the same, then all the subsequent values are exactly the same.
  * 它们看起来就像真正的随机数，因为它们的数字和行的分布是随机数的序列是一样的，它们是可重复的，它们非常适合模拟，这样你就可以重复模拟。如果你以同样的方式开始They just look like real random numbers because their distribution of digits and the line Is the sequence the same as random numbers are they're repeatable, and they're very good for simulations so that you can repeat the simulation. If you started exactly the same way
  * <font color="deeppink">其中一个性质是：如果你知道了一些随机数，可以预测出剩下的数</font>  one property of most pseudo random number sequence Is that if you know a few of the random numbers you can predict the rest of them
* **一个密码学上安全的伪随机数序列**不能通过知道序列中的一些数字来预测。A cryptographically secure pseudo-random number sequence cannot be predicted from knowing some of the numbers in the sequence
  * <font color="deeppink">不管知道其中的多少个随机数，都无法预测下一位</font>  it doesn't matter How about how many numbers you already You can't predict the next one

## 生成真实随机序列：Generating Real Random Sequences

![](/static/2021-02-11-21-56-22.png)

Computer Clock  电脑时钟

* Taking the **middle chunk** of bits from a very accurate clock. 从一个非常精确的时钟中取**中间的位块**。
  * 最低位他们与处理器的时钟周期没有关系，所以他们在某种程度上是可以预测的。而时钟上越大的数字，它们每年的变化不会很大。所以中间的那块是最好的 the least significant bits they weren't related to the clock cycle of processor, so they're predictable in some way. And the the the larger numbers on the clock, they won't change very much from year to year. So the middle chunk is best

Keyboard Latency  键盘延迟

* 测量连续按键之间的时间（if measured in milliseconds），这是很随机的。 Measure the time between successive key strokes, which is quite random.
  * 所以通过每一个键击位都可以获取随机位  so you can get a few bits of randomness each with each keystroke
* 这对于生成短的随机序列是可以的。This is OK for generating short random sequences.
* **局限性---需要用户输入按键（无法自动生成）** Needs a user to be present to enter the keystrokes.

Using Random Noise  使用随机噪音

* 测量随机事件之间的时间间隔，如大气噪声高于某一阈值或电压。 Measure the time interval between random events such as atmospheric noise being above a certain threshold or voltage

## 生成伪随机数序列：Pseudo-Random Sequences

![](/static/2021-02-11-22-04-13.png)

所有正确的伪随机序列，通常由一个非常简单的方程与模块运算产生。 All right pseudo random sequences that typically caused by a very simple equation with modular arithmetic.

* 伪随机序列是完全可以从起始值预测的序列。 Pseudo random sequences are sequences that are completely predicable from the starting value.
* **它们的分布看起来是随机的** Their distribution looks random
  * **因为值、值对等的频率分布与随机序列相同**The frequency distribution of values, pairs of values etc is the same as a random sequence.
  * 它们对模拟是有用的 They are useful for simulation.
* **它们有时可以用于加密，但必须小心使用** They can sometimes be useful for encryption, but care must be taken.
* **有很多劣质的伪随机序列生成器** There are a lot of poor pseudo-random sequence generators around.

### 问题-Netscape伪随机序列生成器：Problems with PRNG

how pseudo random sequences caused problems in the past

![](/static/2021-02-11-22-12-19.png)

1996年，两名计算机专业的学生发现了Netscape伪随机序列生成器PRNG的一个缺陷，使他们能够轻易地制动SSL。

* 安全套接字层，用于互联网安全。
* **用PRNG生成秘钥**

问题1 - 当时，美国政府将加密产品视为
弹药，并防止其出口到非美国人手中

* 美国的SSL密钥长度为128位，这将需要很长的时间暴力破解 The USA SSL key length was 128 bits, which would take a long time to break with a brute force attack.
* 出口版本的密钥长度为40位，这就需要2^40个 ≈1012次尝试。Export versions had a key length of 40 bits, which would require 2^40 ≈ 1012 attempts.
* 几乎任何人都可以用**暴力破解**导出版的程序 攻击。Just about anyone could break the export version with a brute force attack.
* 一个是，因为当时美国政府将加密项目视为军需品，防止向非美国人出口。所以，在美国，SSL的密钥长度是128位 这是很难用蛮力攻击打败的。出口版本的密钥长度为40位。所以，这是很容易破解与蛮力攻击。所以这个比DES算法少了很多比特。大约在这个时候被破解了。 One is, for at this time, the US government viewed crypto projects as munitions and prevented their export to non Americans. So, in the USA, the SSL key length was 128 bit Which is quite hard to beat with brute force attack. Export version has a key length of 40 bits. So this was quite easy to break with a brute force attack. So this is much fewer bits than the DES algorithm. It was broken at around this time.

---

what the flaw actually was?

![](/static/2021-02-11-22-17-36.png)

flaw 1

* 随机数发生器被调用了4次来生成密钥。A random number generator was called 4 times to generate the key.
* **弱点在于初始种子的选择方式**。 The weakness was in the way the initial seed was chosen.
  * 基于进程号、父进程号和时间（毫秒）。 It was based on the process number, the parent process number and the time in milliseconds.
* **攻击者要找出进程号和最接近秒的时间是相当容易的(如unix中，ps命令列出所有进程号&最近的一秒很简单，之后推测毫秒就不需要花费太大功夫)**。It was fairly easy for an attacker to find out the process numbers and the time to the nearest second.

flaw 2

* **通信是由一方发送一个随机数，另一方加密该随机数进行回复**。Communications were initiated by one party sending a random number and the other replying with the encrypted version.
  * **因此，已知明文攻击是可能的**。Thus a known plaintext attack was possible.
  * 因为如果我们是攻击者，我们发送系统一个随机数。我们知道它是什么， 我们得到回加密的版本。所以，我们知道什么是明文 和密码文本。because if we're the attacker, we send the system a random number. We know what it is, and we get back the encrypted version. So we know what plaintext is and the cipher text.
* 只需要106次尝试，就可以破解美国版和出口版的Netscape SSL。Just 106 attempts were needed to break both the USA and export versions of Netscape SSL.
  * 在当时的标准电脑上用了25秒。It took 25 seconds on a standard PC of the time.

:orange: 因此，问题是伪随机数生成器的设置方式并不是在伪随机数生成器的问题上，种子是基于相当可预测的参数。所以，一个暴力攻击只需要大约一百万次攻击就能破解 The problem was the way That the pseudo random number generator was set up wasn't In a problem with pseudo random random number generator, the seed was based on fairly predictable parameters. So a brute force attack only needed about a million attacks

### 问题-Dual_EC_DRBG随机数生成器

![](/static/2021-02-11-22-34-56.png)

尚未破解，但还是被废弃使用（太慢 & 算法有推导公共参数的秘密参数 & OpenSSL引出的：随机数生成器应确保安全性可信，开放透明的计算过程）

* This stands for **Dual Elliptic Curve Deterministic Random numBer Generator**.
* 2007年，NIST发布了一个新的加密安全随机数。生成器标准，有4种算法。In 2007 NIST issued a new cryptographically secure random number generator standard with 4 algorithms.
  * 推荐的，也是默认的算法是Dual_EC_DRBG The recommended, and default, algorithm was Dual_EC_DRBG.
  * 这是NSA推荐的
* **涉及的问题：它比其他算法慢了一个数量级**There were already questions about the algorithm.---It was an order of magnitude slower than the others.

---

涉及的问题2

2007年8月有一个重要的会议。微软的几个人演示了算法有推导公共参数的秘密参数 There's an important conference in August 2007. And a couple of guys from Microsoft demonstrated that the algorithm has secret parameters that we used to derive the public parameters.

* **如果你知道这些秘密参数 你就可以从32个字节的输出中 ，恢复数字生成器的所有内部工作原理**。And if you knew the secret parameters, then you can recover all of the internal workings of the number generator from 32 bytes of output.
  * 所以你只需要把这两个字节记录下来，作为一个微小的量。然后你就可以恢复和预测随机数发生器产生的所有其他字节。 So you just needed to record the two bytes as a tiny amount. And then you could recover and predict all of the other bytes produced by the random number generator.
* 而这在互联网安全中应用相当广泛，这所以如果这是一个**用于生成互联网通信随机密钥的随机数发生器，那么谁知道这些秘密参数就能破解所有使用Dual_EC_DRBG加密的互联网**。And this was used quite widely in Internet Security, which so if this was a random number generator used to generate the random keys for internet communications, then whoever knew these secret parameters will be able to break all encrypted, all internet encryption uses `dual ec drbg`
  * so Bruce Schneier, recommended that Dual_EC_DRBG should not be used.
* 这导致了对NIST推荐的所有曲线的怀疑，并采用了不同 curve25519 This lead to doubts about all the curves recommended by NIST and the adoption of the different curve25519.
  * 转折--OpenSSL

#### 转折：OpenSSL

![](/static/2021-02-11-22-43-45.png)

* **Open SSL是一个开源的加密实现库，提供安全套接字级别的加密**。 Open SSL is a open source library of crypto implementations to provide Secure Sockets level encryption.
* **用户可以在几个不同的伪随机数生成器器中选择**。The user could choose between several different pseudo-random number generators.
  * 提供了`Dual_EC_DRBG`生成器，但不是默认的 `Dual_EC_DRBG` was one of them, although not the default.
* 2013年12月发现的一个编码错误意味着实际上无法选择`Dual_EC_DRBG`作为随机数发生器。 A coding bug discovered in December 2013 meant that it was impossible to actually choose Dual_EC_DRBG as the random number generator.
  * 所以使用该随机数生成器不可能，如果你选择dual_ec_drbg，实则是选择了其他生成器it was impossible to actually choose dual ec drbg as a random number generator. If you picked it as your random number generator, another one would would be chosen.There were no bug reports, and so no one had actually used it.
* 没有BUG报告，所以没有人真正使用过。 Anyway, there were no bug reports. And so no one had actually used it who was using open SSL or open SSL has had its own problems.

---

#### 不再推荐使用：NIST and RSA

涉及的问题3

* **随机数生成器和加密产品的另一个问题是，需要确保人们相信它们会是安全的，所以这就意味着一个公开透明的评估过程**。Another problem with random number generators and crypto products is that the need to ensure that people believe that they're going to be secure and so that that means an open and transparent evaluation process

![](/static/2021-02-12-15-09-35.png)

从2004年开始，RSA组织根据NSA的建议，在其产品Bsafe中使用Dual_EC_DRBG作为默认值。 The RSA organisation used Dual_EC_DRBG in its product Bsafe as the default, on recommendation from the NSA, starting in 2004.

2013年9月，NIST强烈建议不再使用Dual_EC_DRBG。 In September 2013 NIST strongly recommended that Dual_EC_DRBG no longer be used.

然后RSA也建议不要使用它。 RSA then also recommended that it not be used.

# JAVA：Public Key Systems

![](/static/2021-02-12-15-18-06.png)

* `Key`对象
  * `PublicKey`
  * `PrivateKey(java.security)`
* `KeyPairGenerator, KeyPair`生成公钥-私钥对 --- `java.security`
* `Cipher`
  * 用于加密，`javax.crypto`
* `MessageDigest`
  * `java.security`

---

RSA - generating the key pairs

![](/static/2021-02-12-15-20-16.png)

RSA encryption & decryption

![](/static/2021-02-12-15-21-11.png)

calculate the message digests of long message

![](/static/2021-02-12-15-21-57.png)

sign the message digest --- digital signature

![](/static/2021-02-12-15-22-40.png)

加密算法

![](/static/2021-02-12-15-23-19.png)

print_bytes

![](/static/2021-02-12-15-23-50.png)

---

output

![](/static/2021-02-12-15-24-35.png)