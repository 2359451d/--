# Content

* [Content](#content)
* [用户认证：User Authentication](#用户认证user-authentication)
  * [密码：Passwords](#密码passwords)
  * [消息摘要算法是否适合用于散列密码](#消息摘要算法是否适合用于散列密码)
  * [选择密码的最佳方式是什么？](#选择密码的最佳方式是什么)
  * [密码熵：Password Entropy](#密码熵password-entropy)
  * [密码相关攻击-字典攻击：Dictionary Attacks](#密码相关攻击-字典攻击dictionary-attacks)
    * [字典创建选择例子：David Klein’s Dictionary](#字典创建选择例子david-kleins-dictionary)
  * [UNIX的用户认证方案](#unix的用户认证方案)
  * [加盐-阻止字典攻击：Salt](#加盐-阻止字典攻击salt)
  * [通行短语：Pass Phrases](#通行短语pass-phrases)
* [图形密码&认证：Alternative User Authentication](#图形密码认证alternative-user-authentication)
  * [为什么使用图形密码：Graphical Passwords](#为什么使用图形密码graphical-passwords)
  * [3种图形密码类型: Graphical Password Types](#3种图形密码类型-graphical-password-types)
  * [几个不同的图形密码认证系统的例子](#几个不同的图形密码认证系统的例子)
  * [为什么使用 or 不使用](#为什么使用-or-不使用)
  * [例子：基于识别类型认证：Passface - A Recognition Based System](#例子基于识别类型认证passface---a-recognition-based-system)
  * [例子：基于回忆类型认证：Draw a Secret (DAS) - Recall Based](#例子基于回忆类型认证draw-a-secret-das---recall-based)
  * [例子-基于线索的回忆型：ClickPoints - Locimetric](#例子-基于线索的回忆型clickpoints---locimetric)
  * [例子：Recognising Doodles](#例子recognising-doodles)
    * [E](#e)
* [生物识别：Biometrics](#生物识别biometrics)
  * [主要生物识别技术：指纹 & 虹膜](#主要生物识别技术指纹--虹膜)
  * [生物识别特性 & 涉及的问题](#生物识别特性--涉及的问题)
  * [假正例/假负例：False Positives and Negatives](#假正例假负例false-positives-and-negatives)
  * [多因素识别&生物识别不利于身份识别：multi authentication & not good for identification](#多因素识别生物识别不利于身份识别multi-authentication--not-good-for-identification)
  * [罕见事件&过多假阳性例子：False Positives and Rare Events](#罕见事件过多假阳性例子false-positives-and-rare-events)
* [Remote Authentication](#remote-authentication)
  * [（非正式）几种不安全的连入服务器认证方式](#非正式几种不安全的连入服务器认证方式)
  * [（正式）：3种连入服务器的认证方式-Authentication Over a Network](#正式3种连入服务器的认证方式-authentication-over-a-network)
  * [Kerberos网络认证概念：3层保护](#kerberos网络认证概念3层保护)
  * [Kerberos涉及的个体标记：Gradual Explanation](#kerberos涉及的个体标记gradual-explanation)
  * [C-A-S票据请求服务-简单协议例子：A Simple Protocol](#c-a-s票据请求服务-简单协议例子a-simple-protocol)
    * [涉及的问题](#涉及的问题)
  * [解决&改进->C-A-S票据请求服务问题：Solving These Problems](#解决改进-c-a-s票据请求服务问题solving-these-problems)
    * [解决](#解决)
    * [改进：Better Protocol](#改进better-protocol)
      * [票据中时间戳作用](#票据中时间戳作用)
  * [改进后存在的问题：More Problems](#改进后存在的问题more-problems)
  * [时间戳&相关问题：Timestamps](#时间戳相关问题timestamps)
  * [软件缺陷 & 保证Kerberos有效的前提：Software Weaknesses](#软件缺陷--保证kerberos有效的前提software-weaknesses)

# 用户认证：User Authentication

* password
* graphical password
* biometrics
* kerberos

## 密码：Passwords

保护访问计算机的标准方法是分配一个用户名和密码。 The standard way of protecting access to a computer is by issuing a user name and password.

* **电脑不需要存储密码（明文），只是对密码进行单向函数操作**。The computer does not need to store the passwords, just the result of a one way function operating on a password.
  * 只用存加密后的密码（类似消息摘要） An encrypted password, like a message digest
* 用户输入用户名密码，电脑在密码文件中查询密码 Each time a user logs on, the password is entered and the encrypted version of the entered password compared with the value stored in the database.
  * 进行密码对比（计算用户输入的密码的密文，再与存储在DB中的密文比较）

:orange: **因此，就算电脑被黑了也不会造成问题，，攻击者无法知道密码明文** This theoretically means that there is not a problem if the encrypted password file is stolen

* The initial versions of UNIX made the password file publicly readable

## 消息摘要算法是否适合用于散列密码

消息摘要算法是否适合用于散列密码 Are message Digest algorithms good for hashing passwords?

是的，如果它们在密码学上足够安全，而且密码是加盐的。Yes if they are cryptographically secure enough, and passwords are salted.

## 选择密码的最佳方式是什么？

选择密码的最佳方式是什么？What is the best way to choose passwords?

复杂的密码很难记住，导致用户把它们写下来，或者更糟的是重复使用它们。 斯坦福大学和CESG建议用户根据输入的设备，使用不同长度的密码。Complex passwords are hard to remember, leading to users  writing them down, or worse reusing them.  Stanford  University,  and  CESG  are suggesting that users use different length passwords depending on which device they are inputted to.

例如，当你在移动设备上输入密码时，长的令人难忘的密码短语更适合。 然而，如果你只使用台式机/笔记本电脑的键盘，更短更复杂的密码可能更好。For example, long memorable pass phrases are better suited when you are inputting the password on  a  mobile  device.  However,  shorter  more complex ones might be better if you are only using  a  desktop/laptop keyboard.

## 密码熵：Password Entropy

猜测一个密码的简易程度？how easy to get a password

不包括如何计算熵，只关注结论

---

如果完全随机的ASCII字符，很难猜测密码组合

![](/static/2021-04-09-14-42-43.png)

* 由95个主要的ASCII字符，熵公式涉及可能性
  * 假设所有字符被选中的概率，可能性相同
  * 则 `log 95 ≈ 6.5`
  * 即，需要存储所有可能值的位数（每个字符的熵） entropy per character
* 所以 8位密码的理论熵是 `8*6.5=52`
  * 即，`2^52`个不同密码，难以破解

---

如果我们假设密码更像英文（组合更像英文？），那么不同的密码就会减少。 If we assume that the passwords are more like English then there will be fewer different passwords

* 更容易猜测 （比`2^52`）

如果我们用英语的冗余度来位每个字符计算熵，就必须用语言的实验率，即约1．5 If we use the redundancy in English to calculate the entropy for each character, we must use the experimental rate of the language, which is ~1.5

* 为不同英文字符组合计算熵（1 letter, 2, 3,4,,...letter combinations）
  * 计算每个组合的频率 calculate the frequency of each combination
* 如果忽略大小写，英文中每个字符大概有 `1.5 bits`的熵 get roughly 1.5 bits of entrpy per letter in English
  * 假设8字符密码，则 `8*1.5=12 bits`
  * 即，一共 `2^12=4k`种选择

:orange: 实际熵会比理论熵大（因为英文存在其他不同情况，且可以使用数字）

* 因此实际熵，`>12` 且 `<52` bits

## 密码相关攻击-字典攻击：Dictionary Attacks

![](/static/2021-04-09-14-58-44.png)

**字典攻击是暴力破解的一种形式** A dictionary attack is a form of brute force attack.

* 这是一种离线攻击，在密码文件的副本（只有加密后的密码？）上使用。 It is an offline attack, and is used on a copy of the password file.
  * 不是单纯使用字典，无数次一行一行查看字典登录  you don't use the dictionary, to try and log it in umpteen times going through the dictionary one line at a time and typing in the next password.
* **构建一个可能的密码字典，并计算出字典相应的加密密码** A dictionary of possible passwords is constructed and the corresponding encrypted passwords calculated
  * 经常出现的组合口令作为字典的key
  * or 目标曾经泄露的口令集
* **它们会与密码文件进行比较，任何匹配的密码都意味着密码已被泄露**。They are compared with the password file, and any matches mean that a password has been compromised.
  * <font color="deeppink">将字典中所有密码组合与目标密码文件（密文）的副本进行比较，看看是否有命中</font> You get a copy of the password file, you use all of the words in the dictionary cap, calculate the encrypted version of the password, and then compare it with the encrypted versions from password file

:candy: 字典攻击与暴力破解的区别 difference between dictionary attack & brute force attack

* 在破解密码或密钥时，逐一尝试用户自定义词典中的可能密码（单词或短语）的攻击方式。与暴力破解的区别是，暴力破解会逐一尝试所有可能的组合密码，而字典式攻击会使用一个预先定义好的单词列表（可能的密码)
* <font color="deeppink">即，字典攻击找常用密码组合（弱口令等组成的字典），暴力破解尝试所有组合</font> dictionary attack looks for common words where a brute force attack looks for all combinations.

:orange: <font color="deeppink">测试大量密码的成本和单一密码的成本一样高(数据库允许以大致相同访问速度测试大量密码&单个密码，取决于数据库大小)</font>。 It costs just as much to test a large number of passwords as a single password.

* 如果有1000个密码要测试，那么几乎可以肯定有人选择了一个不安全的密码 If there are 1000 passwords to test, then someone will almost certainly have chosen an insecure password
  * 通常情况下，可以破解30%左右的密码。 Typically, about 30% of the passwords can be obtained.

### 字典创建选择例子：David Klein’s Dictionary

![](/static/2021-04-09-15-18-10.png)

One of the early dictionaries.

* Variations on user’s name, initials and login name: 130 choices.
* Men’s and women’s names: 16,000.
* Places, names of famous people, numbers, vulgar phrases,
keyboard patterns, etc: 60,000.
* Various permutations from the previous list: 1,000,000.
* Capitalisations from the previous list: 4,000,000.
* 因此需要注意密码的选择，，避免在字典中

如果我们在搜索引擎中输入 "常用密码列表"，会得到大量的列表，大小从10k到10M不等。 If we type “common password lists” into a search engine we will get a large number of lists, ranging in size from 10k to 10M

## UNIX的用户认证方案

描述UNIX的用户认证方案，解释密码文件和盐机制的使用。解释该系统如何被攻击，以及盐在防止某些攻击中的作用。 Describe the UNIX user authentication scheme, explaining the use of a password file and the salt mechanism. Explain how the system might be attacked, and the role of salt in preventing some attacks.

用户必须提供一个密码，该密码用于生成一个消息摘要，并与存储在密码文件中的值进行比较。在加密前，盐机制会在密码中加入两个随机字符。这些字符也被存储在密码文件中。攻击的主要方式是使用字典，根据常见的用户习惯来构建合理的密码。盐并不能减缓对单个密码的攻击，但可以防止构建一个似是而非的密码表，并利用它来攻击一个完整的密码文件。Users have to provide a password, which is used to generate a message digest which is compared with the value stored in the password file. The salt mechanism adds two random characters to the password before it is encrypted. These characters are also stored in the password file. The main way of attacking is to use a dictionary to construct plausible passwords based on common user habits. Salt does not slow down the attack on a single password, but does prevent the construction of a table of plausible passwords and its use to attack a complete password file.

## 加盐-阻止字典攻击：Salt

salt is a good way to foiling dictionary attacks on a large number of passwords

![](/static/2021-04-09-22-09-11.png)

:orange: <font color="red">在加密之前，会在密码中加入一个盐值（随机字符串），并与加密后的密码（&用户名）一起存储在密码文件中</font> A salt value (random string) is added to a password before encryption and stored in the password file along with the encrypted password.

* **这并不会增加破解给定密码的难度**。This does not make it harder to crack a given password.
  * **因为盐字符串是公开的** The salt string is publically available
    * 用户尝试登陆/注册，取 salt string过呢据用户名 & 输入密码，最后加盐再加密
* Salt意义：<font color="red">字典攻击不能一次性应用于密码文件中的所有密码，因为每个密码有单独的盐值，必须附加到每个密码上。【同时大规模攻击难以进行，需要耗费更多时间】</font> The dictionary attack cannot be run against all the passwords in a password file together, since they all have individual salt values which must be appended to each password.

:orange: 在UNIX中，salt字符串由2个ASCII字符组成，在进行批量字典攻击时，会增加到13位（2\*6.5）的熵。 In UNIX, the salt string consists of 2 ASCII characters, which adds 13 bits (2*6.5) to the entropy when conducting a bulk dictionary attack

* A factor of around 8,000.

## 通行短语：Pass Phrases

![](/static/2021-04-09-22-49-11.png)

**生成真正随机的密码可以阻止字典攻击，但密码可能很难记住(虽然可以选择写下来, wirte down with safety though)**。 Generating really random passwords will foil a dictionary attack, but the passwords can be hard to remember.

* **有一个办法就是把密码做得很长，是一个通行短语** One way round this is to make the password very long, a pass phrase.
  * 而记住很长的密码比记住一个有很多不同的奇怪符号的短密码要容易 And it's easy to remember the very long passwords than it is to remember a short password with lots of different weird symbols in it.
* 然后将其输入到一个生成64位密码的函数中。This is then fed into a function that generates a 64 bit password.
  * 我们在系统中从一个8字节的密码开始，在前端可以有通行短语（无法记住64bit随机密码，但能记住通行短语）。这是相当直观的功能，生成一个64位密码，这是相当随机的
* 然后再像以前一样使用这个密码。This password is then used as before.

:orange: 这作为Linux的前端是很有用的，因为Linux的密码是8字节的，比较短。This is useful as a front end to Linux, which has 8 byte passwords, which are rather short

* 通常，通行短语中的一个字符将在密码中产生1.5位的熵。As a rule of thumb, one letter in the pass phrase will generate 1.5 bits in the password.
  * 这与前面的熵论类似。 This is a similar entropy argument to earlier.

# 图形密码&认证：Alternative User Authentication

graphical password & authentication

![](/static/2021-04-09-23-26-54.png)

:orange: 3种认证类型 there are 3 ways of authentication (authenticating people)

* **What do you have**
  * smart card or something similar
* **what you know**
  * password
  * question about
    * how do you know
    * how well can you remember it
* **what you are**
  * biometrics

认证可分为：(a)你所拥有的；(b)你所知道的；(c)你是什么。解释这三个术语，并分别举例说明 Authentication can be classified as either (a) what you have, (b) what you know or (c) what you are. Explain these three term, giving examples of each.

* 例如，你所拥有的可以是一把物理钥匙、一张智能卡或一个员工徽章。你所知道的可以是一个密码或你母亲的婚前姓氏。你是什么是一个生物识别，如指纹。与其他两种不同的是，它不能被改变，而且是公共知识 What you have can be a physical key, a smart card or an employee badge, for example. What you know can be a passwords or your mother's maiden name. What you are is a biometric such as a fingerprint. Unlike the other two, it cannot be changed and is public knowledge.

---

:orange: 例子：One common way of authentication

* passport --- 2 factor authentication
* **what you have**
* inside there's a biometric which is your picture (**what you are**)

## 为什么使用图形密码：Graphical Passwords

![](/static/2021-04-09-23-33-22.png)

WHY：<font color="red">以视觉形式记忆复杂的信息比较容易</font>。 It is easier to remember complex information in visual form.

* 例如，我们可以识别许多不同的面孔。For example, we can recognise many different faces.
* **所以想法是，我们可以有一个比较复杂的图形密码，但又容易记住**。So the idea is we can have a more complicated graphical password that is still easy to remember.

## 3种图形密码类型: Graphical Password Types

**基于识别类型** Recognition Based

* 当你再次看到它时，就会认出它来 Recognise it when you see it again.
* 如：**注册时选择某些图片，或创建某些图片，再次登陆时就能识别他们**
  * 显然，你不能随便弹出。图像是用户在亲自登录时选择的，攻击者也可以很容易地选择。Obviously you can't just pop up. The image is a person chose when they log in person, an attacker can easily choose, choose them as well. 
  * **安全性**：所以你要做的就是你有一个所谓的挑战集。所以，这是一个人选择的图像 加上其他图像，他们没有选择。所以，你认识到你的图像，你可以选择它， 而攻击者将不得不猜测，或使用其他攻击 So what you do is you have what's called a challenge set. So it's the image that the person chose plus of other images that they didn't choose. And so, you recognize what your images and you can choose it, whereas an attacker will just have to guess, or use some other attack

**基于回忆类型** Recall Based

* Remember and recreate the image at login
* 如：**注册时你创建你的图像。同样，当你登录时，你重新创建它** registration time you create your image. Again, when you log in, you recreate it
  * **安全性**：类似签名，像一种生物识别方式，每次需要证明是本人，类似为一份文件签署签名，最后会与原始签名（注册时）的进行比较，，类似银行账户 So that's like a signature. So signature is kind of like a biometric, and that every time you want to prove it, you, you sign a document, and that's compared with the original signature you provided say when you open your bank account or something like that.

**基于线索的回忆型** Locimetric

* **用视觉线索记忆**。 Remembering with visual clues.
* **记住图像中的点**。Remember points in an image.
* 如：你得到一个大图像。you get an image, one big image.
  * **当你注册的时候，你在图像上选了一些点**， When you register, you pick some points on the image
  * **当你登录的时候，你又要按照同样的顺序记住这些点** when you log in, you have to remember the same points again in the same order.

## 几个不同的图形密码认证系统的例子

图形密码已经被提出来作为基于文本的密码的替代品。举出几个不同的图形密码认证系统的例子，说明每个系统的优点和缺点。Graphical passwords have been proposed as an alternative to text based passwords. Give examples of several different graphical password authentication systems, describing the pros and cons of each one.

图形密码可以是基于回忆的，你必须重新绘制一个简单的形状，如签名或 "画一个秘密"。也可以是基于识别的，即你必须从分散注意力的事物中识别出你所创造的或先前选择的事物。它可以是基于位置的，即你必须记住一个特定的位置或图像中的一系列位置。Graphical passwords can be recall based where you have to redraw a simple shape, such as a signature or 'draw a secret'. The can be recognition based, where you have to recognise something you have either created or chosen earlier from amongst distractors. It can be location based, where you have to remember a particular location or series of locations in an image.

## 为什么使用 or 不使用

想一想你个人为什么喜欢使用图形密码系统或不喜欢使用图形密码系统的原因。Think of reasons why you, personally, would either like to use a graphical password system or not like to use one.

图形密码可能更容易记忆，但更难设置。多个图形密码可能更难记住，特别是因为不可能在多个账户中重复使用相同的密码。Graphical passwords can be easier to remember but harder to set up. Multiple graphical passwords may be harder to remember, especially because it is not possible to reuse the same ones for multiple accounts.

## 例子：基于识别类型认证：Passface - A Recognition Based System

基于识别类型的图形认证

![](/static/2021-04-09-23-46-54.png)

On registration

* 选择几张你能再次认出的不同面孔。Choose several different faces that you will recognise again

On login

* 呈现多个屏幕，每个屏幕有16个faces。Presented with several screens, each with 16 faces
* 在每个屏幕上识别一张脸 Recognise a face on each screen
* **每次都需要选择正确，当完成所有选择才能通过认证**

:orange: 涉及的问题 -- **在实践中效果不佳** Does not work well in practice

* 不能选择亲戚朋友--社交工程攻击。Can’t choose relatives or friends – social engineering attack
  * 尤其是当你是社交媒体的用户时。，攻击者就可以看到你的朋友是谁，然后在这个人脸、身份里把他们挑出来，所以必须是不认识的人的照片 And so, an attacker can then see who your friends are, and pick them out in this face, ID, so I have to be pictures of people who don't know, and people are not very good at recognizing faces of people they don't know just from the features that they chose.
* 当人们从图片中选择人脸时，并不善于识别人脸（不认识的人） People are not very good at recognising faces when chosen from pictures

## 例子：基于回忆类型认证：Draw a Secret (DAS) - Recall Based

基于回忆类型 recall based approach

![](/static/2021-04-09-23-53-32.png)

为什么该模式可行？

* 人们之所以能记住他们所画的东西，是因为涉及想象/创造性。 People remember what they have drawn because of the creative effort involved.

---

On registration

* 画一个简单的形状。Draw a simple shape.

On Login

* 再画同样的形状（与注册时一致）。Draw the same shape again.

:orange: 很适合手持设备。Good for hand held devices. There is an Android app.

* squiggle,安卓解锁，，

:candy: **涉及的问题**

* 重画的准确度如何？How accurate is the redrawing?
  * **无法重画完全相同的形状，会有轻微的不同。所以，需要的是一些算法，从比较两个图形看它们的接近度和相似度** cannot redraw exactly the same shape, there'll be slightly different. So what you need is some algorithm from comparing two drawings to see how close and similar they are
* 真的能记得之前画的形状吗。Can you really remember an earlier drawing.
  * 通常对于记忆太过简单？？ Usually too simple to be memorable
  * **越简单的画越难记** the simpler the drawing is the harder it is to remember, there's **less creative effort goes into**

## 例子-基于线索的回忆型：ClickPoints - Locimetric

![](/static/2021-04-10-00-02-52.png)

* 点击图片中的点。Click on points in a picture.
* 一定要弄清顺序。Must get the order right.

:candy:涉及的问题

* （离点）多近才算近？How close is close enough?
  * **需要算法权衡用户从相同点地方自然形成的误差距离，排除随机选择和这两者之间的权衡** need algorithm within trade off the distance of people naturally make from where they draw the same point, more than once. And so that's the trade off between that and ruling out random choices.
 在实际工作中，人们即使在复杂的画面中，也会从一组小点中选择。In practice people choose from a small set of points, even in a complex picture.
 实践不可行 Does not work well

## 例子：Recognising Doodles

基于识别 &回忆？

![](/static/2021-04-10-00-11-12.png)

让画作更复杂，从而更容易让人记住。 Make the drawing more complex and hence more memorable.

---

On registration

* 画几个涂鸦 Draw several doodles

On login

* 在干扰图片中识别你的涂鸦 ，类似于passface例子。 Recognise your doodles amongst distractors, similar to passface.
  * 不过此例是选择doodles

**为什么可行**：用户很可能会记住其的涂鸦，因为涉及其自身的创作。 You are likely to remember your doodles because of the creative effort

* 它们是个人的，但可能不会受到社会工程的攻击。 They are personal but probably not open to a social engineering attack.
  * 其他人也许不能通过知道你是谁或者阅读你的Facebook页面就能猜到你的涂鸦，但也有概率能

:candy: 涉及的问题

* **可能存在类似干扰图片造成的干扰问题** There can be a problem with distractors that are similar.
  * <font color="deeppink">干扰图片可以源于其他用户创建的涂鸦，如果选择系统提供的涂鸦（专业艺术家）伪造的，对比普通用户创建的这之间会存在偏差</font>
  * 很多人可能会选择画一个类似的涂鸦。 Many people might choose to draw a similar doodle.

### E

![](/static/2021-04-10-11-10-56.png)

![](/static/2021-04-10-11-11-11.png)

![](/static/2021-04-10-11-11-25.png)

![](/static/2021-04-10-11-12-08.png)

---

![](/static/2021-04-10-11-12-34.png)
![](/static/2021-04-10-11-12-48.png)

---

![](/static/2021-04-10-11-13-01.png)

* Mobile phones are good for creating drawings

# 生物识别：Biometrics

哪些生物识别技术是最好的!

这取决于它们将被如何使用。照片被广泛使用，但要依靠人将人与照片进行比较，因为图像识别软件仍然不是那么精确。如果没有人在那里检查，那么指纹就不是那么好，因为它们可以被伪造。那么虹膜图案就比较好，因为它们比较难伪造。这些生物识别技术也需要昂贵的设备。It depends on how they will be used. A photograph is widely used but relies on a human comparing the person to their photo since image recognition software is still not that precise. If no person is there to check, then fingerprints are not that good because they can be faked. Iris patterns are then better because they are harder to fake. These biometrics also require expensive equipment.

## 主要生物识别技术：指纹 & 虹膜

![](/static/2021-04-10-11-14-59.png)

:orange: 指纹 Fingerprints

* **特点**：unique，cannot be revoke
* **问题**
  * 可以从任何触碰过的东西上提取，其他人很容易获取 Can be lifted from anything you touch. Easy for someone else to acquire them
  * dirty / oily fingers对指纹读取器的影响 Fingerprint readers can have trouble with dirty / oily fingers.
  * 指纹磨损 Fingerprints for manual workers can wear away
    * 磨损可能导致某些物品无法使用
    * 基于指纹识别的系统，可能危机人身安全 Owners of fingerprint based systems, eg some cars, can be in danger of loosing a finger!
  * 假指纹 Fake fingers are quite easy to create.
  * 指纹读取器能更好识别假指纹（无磨损） Fingerprint readers find it easier to read ‘fake fingers’ correctly

:orange: **虹膜扫描仪更好，因为更难制造 "假虹膜**"。 Iris scanners are better because it is harder to create a ‘fake iris’

---

## 生物识别特性 & 涉及的问题

![](/static/2021-04-10-11-54-55.png)

:orange: **要对生物识别认证进行监控，防止造假** Biometric authentication should be monitored to prevent faking.

* **最好有人工识别假指纹** Someone there to detect the use of a fake finger. (better done if someone is watching)
  * 因为可以进行自动生物识别， it can be an automatic process, so you have a entry gate where you need to put your finger into gate

:orange: **涉及的问题**：生物识别信息是固定的公共知识。 Biometric information is fixed public knowledge.

* **如果旧的生物识别系统被破坏，你就不能选择新的生物识别系统** You can’t choose a new biometric if the old one is compromised.

:orange: 最常见的生物识别是照片 The most common biometric is a photo

* 护照，学生证 Passport, student card.
* 很容易被人检查。 Easily checked by a human.
* 自动人脸识别不是很好，但在改进。Automatic facial recognition is not very good, but improving.
  * 机场结合自动护照检查&人脸识别，，这种改进使无人值守生物识别结合面部识别成为一种可能 is improving of that is becoming a possibility to have automatic unattended biometric authentication using facial recognition. 

## 假正例/假负例：False Positives and Negatives

![](/static/2021-04-10-12-05-13.png)

FP - 预测为正，真实为负
FN - 预测为负，真实为正

:orange: **假阳性--错误地认为生物识别技术与之匹配** False positive – wrongly say that the biometrics match

* 漏网之鱼 Let someone break in

:orange: **假阴性--错误的说生物识别技术不匹配** False negative – wrongly say the biometrics don’t match.

* 错误排他 Wrongly exclude someone.

:candy: **假阴性假阳性例子不仅只出现生物识别层面，但对于生物识别来说很重要**

:orange: <font color="deeppink">假阳性/假阴性比率通常可以调整。</font>The false positive / false negative ratio can often be adjusted.

* 通过改变系统参数来调整比例 devise a system, then you can typically adjust the false positive and false negative ratio by changing the parameters of the system.
* **如**： 照片和面部识别软件 photographs and facial recognition software.
  * 如果你让软件更容易找到匹配，那么你就**会增加假阳性率**。If you make it easier for the software to find the match, then you'll increase the false positive rate.
    * 因为匹配更容易找到，所以很容易找到错误的匹配  Because the match is easier to find so it's easy to find a wrong match
  * 会**减少假阴性匹配** reduce the false negative match
    * 因为如果一个人的长相和照片略有不同，还是会让他通过识别 Because if someone looks slightly different from their photo, they'll still be let him in

---

解释术语假阳性和假阴性，举例说明如何在安全方面考虑它们。Explain the terms false positive and false negative, giving examples of how they can be taken into account in security.

* 假阳性会错误地说两件事情是相同或相似的，而它们不是。如果对罕见事件进行大规模筛选，这将是一个问题。如果漏掉一个真实事件的惩罚很高，假阴性就会成为大规模筛查的问题。False positives wrongly say that two things are the same or similar when they are not. This will be a problem if undertaking mass screening for rare events. False negatives are a problem in mass screening if the penalty of missing a real event is high.

## 多因素识别&生物识别不利于身份识别：multi authentication & not good for identification

:candy: <font color="deeppink">由于存在假阴性问题，不应使用生物识别技术进行【身份识别】</font>。 Biometric should not be used for identification because of the false negative problem

* 生物识别可应用于认证，而不是身份识别（因此如果先前能认证身份，可以之后再利用生物识别）
* 使用另外的身份识别形式，并使用生物识别技术对其进行认证。 Use an alternative form of identification and use biometrics to authenticate it.

:orange: **使用多因素认证**。 Use multifactor authentication.

* Token (what you have) and biometric (what you are)

## 罕见事件&过多假阳性例子：False Positives and Rare Events

![](/static/2021-04-10-12-19-45.png)

:orange: 例子 - 检测某种特定罕见癌症

* **经常决定建议进行大规模筛查，以检测小概率事件** Mass screening is often proposed to detect rare events.
* Detect a rare form of cancer.

:orange: (数值例子假设，)例如，一个筛查系统的假阳性率为1%，假阴性率为1%。 Example, a screening system has a 1% false positive and a 1% false negative rate.

* 它正在寻找百万分之一中的癌症。 It is looking for a 1 in a million cancer.
* 假设进行test，**有99%准确**
  * 99% accurate but It is not 99% that you've got the cancer
* 如果你的检查结果是阳性（false positive），那么你患癌的概率有多大？ If your test is positive, what is the probability that you have the cancer?

![](/static/2021-04-10-12-29-43.png)

* In 1,000,000 tests（on people，and system with accuracy of 99%） there will be
  * 1 real positive
  * 10,000 false positives
  * 0 false negatives.
* 如果你有一个阳性的结果，有一个万分之一的机会，你有癌症 If you have a positive result, there is a 1 in 10,000 chance you have the cancer
  * 0.01%.

---

:orange: 因此此例，进行大规模筛查并不可行 So that's why masks screening of rare cancers is not a good idea,

* 任何技术都不是完美的，都会有一些假阳性和假阴性。 No technique is perfect, there will be some false positives and false negatives.
* 假阳性率可能很小，但如果我们要寻找的事件**很罕见**，那么我们发现的大多数阳性都是假的【会发现很多假阳性例子，占用过多工作人员时间 because we'll end up with lots and lots of false positives, and that will occupy all the staff time.】。 The false positive rate may be small, but if the events we are looking for are rare then most positives we find are false.
  * <font color="red">应，如发现positive例子，就专注该检验</font> The idea of the best screening is that if you find a positive, you go in and do more time consuming tests

:orange: 同理，in a security setting

* 监控面部识别恐怖分子
  * 99%准确率，但因为事件为罕见事件，所以大部分情况只能得到假阳性，当局会浪费过多时间内专注假阳性线索，难以发现一个真正的事件 --- **资源的滥用** some known terrorists by using CCTV camera images. Then the facial recognition software might be 99%, accurate, but we're looking for a rare thing, terrorists. And so, authorities. So, most of the hits, we'll get we get will be false positives, not real process and authorities will spend all their time chasing false leads and most investigators will never investigate a real case. So it's a misuse of resources to best screen for rare events.

# Remote Authentication

主要介绍 - Kerberos

* 解决类似TLS传输层安全问题
  * C-S之间，对客户端进行认证，确保能够访问服务器
  * 局域网，所有计算机可以事先配置好，不需要公钥加密，只使用单钥加密

---

描述Kerberos认证协议。	特别是，描述票证和认证器的结构和使用。Describe the Kerberos authentication protocol.	In particular, describe the structure and use of a ticket and an authenticator.

在使用一个服务之前，必须向该服务出示一个票据。它验证了使用该票证的人是被授予该票证的人。它包含客户的ID和地址、时间戳和会话密钥。所有这些信息都用服务器的密匙进行了加密。认证器用于验证客户是否是票据授予服务的预期收件人。它包含客户的ID和地址，一个时间戳和一个可选的nonce，用于相互认证。它是用会话密钥加密的。A ticket must be presented to a service before that service can be used. It verifies that the person using the ticket is the person for whom it was granted. It contains the client’s ID and address, a timestamp and session key. All of this information is encrypted with the server's secret key. An authenticator is used to verify that the client was the intended recipient from the ticket granting service. It contains the clients ID and address, a timestamp and an optional nonce for mutual authentication. It is encrypted with the session key.


## （非正式）几种不安全的连入服务器认证方式

![](/static/2021-04-10-14-14-30.png)

用户（客户端）想连入服务器 A user is sitting at a client computer but wants to log in to another computer (a server) connected to the client.

:orange: 几种不安全的连入方式

* **客户端可以将密码传输给服务器，让服务器对用户进行认证。** The client could transmit the password to the server, letting the server authenticate the user
  * **这是很危险的，因为【网络是不安全的】**。This is dangerous, since the network is insecure
    * 即使是局域网 even though its an internal network
* **客户端可以对用户进行认证，然后将用户连接到服务器** The client could authenticate the user and then connect the user to the server.
  * **这对服务器来说可能是一个危险，因为它必须信任客户端执行的远程认证**。 This could be a danger to the server, since it must trust the remote authentication performed by the client.
    * 如果客户机被攻击，攻击者可以绕过客户机的认证步骤 the attacker to bypass the client authentication.
* **服务器可以将密码明码发给客户端，让客户端对用户进行认证**。 The server could send the password to the client in the clear, letting the client authenticate the user.
  * 表单隐藏字段 Hidden field called passwd in a form!【hide in plain sight】
  * 明显，**很不安全的做法**，，然而有些组织仍使用这种方式进行认证

## （正式）：3种连入服务器的认证方式-Authentication Over a Network

![](/static/2021-04-10-14-28-29.png)

有3种方法（正规描述 formal conception），信任度逐级**递减**。 There are the 3 approaches, with decreasing levels of trust

1. **每个客户端宏都会对用户进行认证。每个服务器根据用户的ID执行安全策略**。 Each client macine authenticates users. Each server enforces a security policy based on the user’s ID.
   1. 服务器假设客户端机器是真实的，并信任它们来验证用户。 The server assumes the client machines are genuine and trusts them to authenticate users.
   2. <font color="red">此方法，要求服务器新人客户机。某些可信任的组织中使用可行</font> So this is where the server trust the client machine to do the authentication properly. And in some trusting organisations despite well work, But it does involve a lot of trust.
2. **客户端系统必须对服务器进行自我认证，但服务器信任客户端对用户进行认证**。 The client systems must authenticate themselves to servers, but the servers trust the clients to authenticate the users.
   1. 所以这里唯一增加的不安全因素就是服务器的方式。客户机要对自己和服务器进行认证，所以增加了客户机插入网络的难度 have to authenticate themselves and servers, and so it makes it harder to insert a client machine into the network.
3. **用户必须向每个服务器证明身份。每个服务器向客户证明身份**。The user must prove identity to each server. Each server proves identity to the clients.
   1. **Kerberos** uses the third approach

## Kerberos网络认证概念：3层保护

![](/static/2021-04-10-14-37-13.png)

:orange: **Kerberos是LINUX和微软Windows使用的可信第三方认证产品。** Kerberos is a trusted third-party authentication product used by LINUX and Microsoft Windows.

* <font color="deeppink">它提供安全的网络认证，允许访问网络上的不同服务</font>。 It provides secure network authentication, allowing access to different services on the network
* 源于：Originated with MIT project Athena.

:orange: 神话中的Kerberos是一只三头护卫犬，**Kerberos协议提供了三个层次的保护**。 The mythical Kerberos was a three headed guard dog, and the Kerberos protocol provides three levels of protection.

* **网络连接开始时的认证** Authentication at the start of a network connection
  * 客户机 & 服务器的认证
* **对每个信息进行认证**。 Authentication of each message.
* **对每个信息进行加密**。Encryption of each message.

:orange: 它采用单密钥加密。 It uses single key encryption.

## Kerberos涉及的个体标记：Gradual Explanation

![](/static/2021-04-10-14-43-26.png)

分步介绍Kerberos（因为整体复杂）

* 每一步都会解决上一步中发现的问题。Each step will solve a problem found in the previous step.
* 这将有助于解释 Kerberos 的每个部分。This will help to explain each part of Kerberos.

:candy: 涉及的个体：The Kerberos actors are:

* User (U)
* Client (C) machine, interacts with the user
* Server (S) machine, provides a service
* **Authenticator** (A), authenticates users
* **Ticket Granting Service** (T)

## C-A-S票据请求服务-简单协议例子：A Simple Protocol

我们不希望每个服务器都要对每个用户进行认证。 We do not want each server to have to authenticate every user.

* 用户将不得不不断输入密码。The user would have to keep entering their password.
* ??? <font color="red">所以，希望（？）每个服务器都需要进行身份验证，但我们并不希望用户每次使用不同的服务时都要不断输入密码。</font> So, each server needs to be authenticated, but we don't really want the user to have to keep entering their password every time they use a different service. 

我们有一个专门的流程，<font color="deeppink">一个认证服务器（A）</font>来做这个事情。We have a dedicated process, an authentication server (A) to do this.

* **它知道所有用户的密码(先前设置的)** It knows the passwords of all users
* **它与【每个服务器(S)】共享一个【不同的秘钥】，在初始化阶段就设置好了** It shares a different secret key with each server (S), set up in the initialisation phase
* <font color="red">因此，此方式只适用于局域网，不适用于互联网</font> this is why this approach doesn't work when doing the internet security, but it does work in the local area Network

:orange: 在下面的协议中，IP是网络地址，P是密码，K是密钥。 In the following protocol, IP is a network address, P is a password and K a key

* `C -> A: IDc, Pc, IDs`
  * **客户端向认证服务器发送客户端ID、密码和要连接的服务器ID**。client sends to authentication server with client ID, password & the id of the server which it wants to connect
* `A->C: Ticket= Ks[IDc, IPc, IDs]`
  * 使用 `Ks` 加密 (**单秘钥**)
  * **认证服务器向客户端发送一个票据** authentication server sends a ticket to the client
  * <font color="red">Ticket是一个特定的服务，用server的密钥加密的，只有认证服务器和服务本身知道服务器的密钥</font> so the ticket is a particular service, and it's specific to the service. And it's encrypted with the secret key of the service. So, the only people that know the key of a service are the authentication server, and the service itself
    * client无法知道Ticket内容，**但server可以解密，然后获取信息，确认客户端身份** client cannot know the information inside the ticket, but server can decrypt that and check to see if the client is the person says it.
* `C -> S: IDc, Ticket`
  * **然后客户端将票据和它的ID发送到服务器**。 then client sends the ticket & its ID to the server

---

![](/static/2021-04-10-15-21-21.png)

在这种情况下，C向用户索取用户密码（Pc）和所需服务器ID（IDs），并将这些信息发送给A（认证服务器）。 In this scenario C requests the users password (PC) and the service required (IDS) from the user and sends this information to A.

* **A检查密码（Pc）是否正确【认证 authentication】，是否允许用户使用S【授权 authorization】，如果正确则发送Ticket**。 A checks that the password is correct and that the user is allowed to use S. If this is OK then it sends a Ticket.
  * <font color="red">票据用S的密钥Ks加密，只能由S打开</font> The Ticket is encrypted with KS, S’s key and can only be opened by S
* **最后，C将Ticket和用户的ID（IDc）发送给S**。 C sends the Ticket and the user’s ID to S
  * <font color="red">S检查是否从是正确的用户从正确的IP地址请求服务，并是否携带一个正确的关于server自身的票</font>。 S checks that the correct user is calling from the correct IP address and has a ticket for the correct server (itself)

:candy: 认证 & 授权 authentication vs authorization

* 认证只是为了检查，这个客户端是被授权使用那个特定的服务的用户
* 授权：检查认证的用户是否有足够权利，或允许使用某服务

### 涉及的问题

:candy: **这个协议有两个问题** There are two problems with this protocol

* **密码是以明文发送的**。The password is sent in the clear.
* **每次使用（不同服务器）服务时，用户必须输入密码**。The user must enter a password every time a service is used.
  * <font color="red">每次要使用不同的服务，客户端需要重新输入密码，通过认证服务器再次生成一个Ticket，然后请求使用服务</font>

## 解决&改进->C-A-S票据请求服务问题：Solving These Problems

### 解决

![](/static/2021-04-10-15-39-49.png)

:orange: 根据用户密码创建**用户密钥(会话密钥session/encrpytion key)Ku**，解决密码传输问题(之前是明文传输密码)。 The password transmission problem is solved by creating a user key Ku based on the users password.

* **C和A（客户机&认证服务器）都知道用户的【密码，生成这个密钥】**。 Both C and A know the user’s password and can generate this key
  * client - ask user to type password
  * authentication server - have the copy of user's password
* 他们就可以安全地进行通信。They can then communicate securely.

:orange: 第二个问题是通过创建一个新的 "登录 "服务来解决的，这个服务叫做 "票证授予服务"（T）。 The second problem is solved by creating a new “Login” service, called a Ticket Granting Service (T).

* 问题：每次换到不同的服务器上的服务，，都需要新的票据，，而票证又是认证服务器根据客户机密码，IP，和使用的服务ID生成的，，所以用户每次都要重新输入密码
* **用户在登录时，会通过票证授予服务`T`得到一张票证**。The user gets a Ticket for T when he logs in.
  * <font color="red">登陆时，如果通过了认证服务器，生成generic票据，可以使用票据授予服务，因此每次要使用不同的服务，票证授予服务会生成对应的票证，这样一来，只用输入一次密码</font>
* **`T`会在有需要时，为不同的服务（器） `S`生成票据** T will issue Tickets for different S’s when needed

### 改进：Better Protocol

![](/static/2021-04-10-15-54-57.png)

:orange: 登录 When the user logs in

* `C->A: IDc`
  * 客户端发送ID到认证服务器 client sends ID to the authentication server
* `A->C: Ku[Ticket T]`
  * 认证服务器将通用票据发回给客户端。 authentication server sends back the generic ticket to the client
  * **密钥 `Ku`是由用户密码衍生出来的** generic ticket was encrypted with the key derived from the user's password
    * 因此，如果client符合，可以解密获取通用Ticket

:orange: 第一次使用每个服务 first use of each new service

* <font color="red">客户机代表用户保留通用票证</font> client kept the generic ticket on behalf of the user
* `C->T: IDc, IDs, Ticket T`
  * 客户端将自己的Generic ticket & ID、服务的ID发送给Ticket Grant Service。 client sends the Generic ticket & ID of itself, the service's ID to the Ticket Grant Service
* `T->C: Ticket S`
  * 赠票服务将他们想要的服务的票据发回给客户。 Ticket grant service sends back the ticket to the service they want to the client

:orange: **之后每次需要使用服务，客户机将之前的票据发给服务器** each time a service is used

* `C->S: IDc, Ticket S`
* <font color="red">只要一个通用票据，，之后每次客户机发送ID和想使用的服务ID给授予服务，，就能直接生成对应service的票据，而不需要再次输入密码</font>

:candy: 时间戳实际上是一个时间间隔，有开始和停止时间。A timestamp is actually a time interval, with start and stop times

* **通用票据** generic ticket `Ticket T = Kt[IDc, IPc, IDt, Timestamp1]`
  * C通过A认证后生成，并用Ku（用户密码衍生来的密钥）加密后传输给C
  * C将票据和其他信息发送给T，，T用自己的密钥解密获取信息，之后再为C生成想使用服务S的票据 
* **服务票据** `Ticket S = Ks[IDc, IPc, IDs, Timestamp2]`

---

![](/static/2021-04-10-16-31-15.png)

:orange: 第一张票`Ticket T`是用用户密钥（Ku）加密的。The first ticket is encrypted with the users key (KU).

* **Ku - 由用户密码衍生而来，用于C解密信息获取通用Ticket** The key is generated from the password and unlocks TicketT
* 提示用户输入密码，<font color="blue">只有（用户/客户机）C和A（认证服务器）知道</font>，能够解密。 The user is prompted for their password, and only the user and A knows this.

:orange: **客户端（代表用户）保留通用票据TicketT，每当有新的服务类型被请求时，就会使用它。** The client keeps TicketT and uses it whenever a new type of service is requested

* 之后需要使用服务，，把这个通用票据&其他信息发给T，，T之后再为C生成想使用服务S的服务票据（避免再次输入密码）

:orange: **客户端保留了其他所有的票据，以便每当使用每个服务时使用。** The client keeps all the other tickets to be used whenever each service is used.

#### 票据中时间戳作用

:candy: **需要时间戳来防止重放攻击**。The timestamps are needed to prevent replay attacks.

* 如果得到了一个旧的票证，，因为他里面有时间戳，就能知道它已经过期了。get an old ticket. Then, because he's got a timestamp inside it you know that it's expired.

## 改进后存在的问题：More Problems

![](/static/2021-04-10-16-47-16.png)

:orange: **如果时间戳的寿命太短，那么当旧的时间戳过期时（通用票据的？），用户必须不断输入密码来创建新的票据**。If the lifetime of the timestamp is too short then the user has to continually type in their password to create a new ticket when the old one expires

:orange: **如果时间戳的寿命太长，系统就容易受到重放攻击。** If the lifetime of the timestamp is too long, the system is vulnerable to a replay attack.

![](/static/2021-04-10-17-01-20.png)
![](/static/2021-04-10-18-54-19.png)

* 同一客户机上的不同用户会话可以使用尚未过期的票据访问服务 A different user session on the same client machine can use a ticket that has not yet expired to access a service
* T&S需要能证明使用票据人的身份（是否匹配同一个用户会话） Thus T and S must be able to prove that the person using a Ticket is the person who was issued with the Ticket
* **利用Authenticators解决**：A在票据内向C和T都提供一条秘密信息【**即，会话密钥 session key**】 A providing a secret piece of information to both C and T inside the ticket
  * C就可以向T证明，它与给定的Ticket是同一个客户端会话。 C can then prove to T that it is the same client session as the one given the Ticket.
  * 这个秘密信息是A提供的一个**会话密钥Kct**，供C和T之间使用。 This secret information is a session key Kct, for use between C and T.
* 则，**登陆时(假设，C通过A)**
  * `A->C: Ku[Kct, IDt, Ticket T, Timestamp1]`
    * 额外信息 - 会话密钥 Kct，会和通用票据等一起返回给C
  * `Ticket T = Kt[Kct, IDc,IPc, IDt, Timestamp2]`
    * 额外信息 - 会话密钥 Kct，票据授权服务能解密提取 通用票据中信息
  * 客户端会被告知会话密钥和票据的有效期。The client is told the session key and the validity of the Ticket
* **用户请求服务时**：When the user requests a service
  * `C->T: IDs, Ticket T, Authenticator c`
    * **这一步注意：多发一个Authenticator**
  * `Authenticator c = Kct[IDc, IPc, Timestamp3]`
    * <font color="red">authenticator就是用会话密钥加密的一些信息，因此只有匹配的客户端会话有权利加密（拥有会话密钥）</font> just some information encrypted with the session key so only right clients has a session key to encrypt
  * <font color="deeppink">身份验证器证明该请求来自于授予Ticket的同一个客户端会话（而不是重放会话not reply session），因为它是用会话密钥加密的</font> The authenticator proves that the request is coming from the same client session that was granted the Ticket, since it is encrypted with the session key.
  * <font color="blue">T要先用自己的密钥解开通用票据，，，获取 Kct，然后利用会话密钥Kct再解开Authenticator（这步可以验证客户端会话身份）</font>

:orange: **服务器也必须对用户进行自我认证**。The servers must also authenticate themselves to the user

![](/static/2021-04-10-19-14-42.png)
![](/static/2021-04-10-19-38-39.png)

* 攻击者可以插入一个虚假的服务器 An attacker could insert a false server
* **服务器认证 Server Authentication解决**：
  * 当用户请求服务时 When the user requests a service
    * `T->C: Kct[Kcs, IDs, Timestamp4, Ticket S]`
    * T发送回**客户端和服务器之间的会话密钥**，以及服务器的Ticket，其中也**包含会话密钥**。T sends back a session key between the client and server, together with a server Ticket, which also contains the session key.
      * 之前 T->C只传了一个服务票据
  * `C->S: Ticket S, Authenticator C` The client creates another authenticator and sends it to the server, together with the ticket
    * `Ticket S` - 只能S解，解后获取 `Kcs`
    * S利用`Kcs`解 `Authenticator C`，验证C身份
  * **`S -> C: Kcs[Timestamp+1]`**
    * <font color="deeppink">服务器在（票据中的）时间戳上加1，然后发回C，用会话密钥Kcs加密。</font> The server adds 1 to the timestamp and sends it back, encrypted with the session key
      * **目的**：说明服务器通过authenticator而非重放之前票据 Adding one to the timestamp shows that the server was able to open the authenticator and was not replaying a previous ticket.
      * 类似于其他协议中使用不可预知的数字 Similar to the use of unpredictable numbers in other protocols.

## 时间戳&相关问题：Timestamps

![](/static/2021-04-10-20-30-35.png)

:orange: **时间戳依赖于所有机器上的时间同步。** The timestamps rely on time being synchronised over all machines.

* **网络时间协议可能是不安全的，所以这可能是一个弱点，允许重放攻击。** Network time protocols can be insecure, and so this could be a weakness, allowing a replay attack.
* 时间戳有固定的生命周期，**代表的是一段时间，而不是一个时间点瞬间**。 Timestamps have a fixed lifetime, and represent a period of time, not a time instant.
  * 由于网络延迟等原因，他们无法准确。They can’t be exact because of network delays etc.
  * 这使得系统容易受到快速重放攻击，而时间戳仍然有效。 This makes the system vulnerable to a quick replay attack, while the timestamp is still valid.
  * **服务器应该把所有当前的票据放在一个表上，以阻止**。 The servers should keep a table of all current tickets to foil this.
    * 不再需要记住时间戳已经过期的票据 They no longer need to remember tickets whose timestamps have expired.

## 软件缺陷 & 保证Kerberos有效的前提：Software Weaknesses

描述Kerberos系统的四个弱点，并在每个案例中解释如何克服这些弱点。

* 客户端认证是基于一个密码的。运行 Kerberos 的机器必须是安全的。存储 Kerberos 数据库的机器必须是安全的。Kerberos 进程不应该被特洛伊木马所取代。Client authentication is based on a password. The machine running Kerberos must be secure. The machine storing the Kerberos database must be secure. The Kerberos processes should not be replaced by a Trojan horse.

![](/static/2021-04-10-20-38-07.png)

**客户端的密钥是基于他们的密码，该方面的常见漏洞都有** The client key is based on their password, with all of the usual vulnerabilities in that area

**运行 Kerberos 进程的机器必须是安全的，因为密钥存储在该机器的内部数据库中**。 The machine running the Kerberos processes must be secure, since the keys are stored in an internal database on that machine

* <font color="red">因为认证服务器存储的是实际密码，而不是密码的加密版本。所以这就使得他们不安全。</font>

**管理该数据库的机器也必须是安全的**。The machine administering this database must also be secure

**必须防止攻击者用他们自己设计的进程来替换 Kerberos 进程**。 An attacker must be prevented from replacing the Kerberos processes with ones of their own devising

* 这与登录仿真器类似 This is similar to login emulators

**协议必须正确执行**。 The protocol has to be implemented correctly.

* 通常用软件验证套件进行检查 This is usually checked with a software validation suite