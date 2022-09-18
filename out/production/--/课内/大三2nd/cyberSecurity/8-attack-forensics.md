# Attack

* Hats, black, white, grey.
* 测试：被动或主动，黑盒或白盒。 Testing: passive or active, black or white box.
* 渗透测试步骤和工具。 Penetration testing steps and tools.

## Black and White Hats

攻击一个系统通常是一种敌对行为，但也可能是好事。Attacking a system is usually a hostile action, but can also be good.

* 如果你想知道你有多安全，你应该检查你的 防御措施。 If you want to find out how secure you are, you should check your defences.
  * 绕着你的房子走一圈，就像一个小偷一样。Walk round your house seeing it as a burglar would.

:orange: 黑帽（坏）和白帽（好）之间的区别取决于 **书面许可/权限**。The difference between black hat (bad) and white hat (good) depends on permissions.

* **白帽黑客在得到被攻击组织的许可后进行渗透测试。被攻击的组织的许可下进行渗透测试**。A white hat hacker conducts penetration testing with the permission of the organisation being attacked.
  * 我会付钱给你，让你在我的房子里走一圈 . .I’ll pay you to walk round my house . . .
  * 这必须是一份**书面**的法律合同，其中要<font color="red">详细说明什么是允许的和不允许的</font>。 This must be a written legal contract, with details of what is and is not allowed.
  * 必须采取**预防**措施以防止损害（在损害发生前）。Precautions must be taken to prevent damage.
    * 我们雇用某人来尝试并入侵你的系统，找出你的网站上是否有任何弱点。因此，如果我真的发现了一个弱点，不会发生什么坏事，然后你就有机会修复这个漏洞。在真正的攻击发生之前 the idea is that we employ someone to try and hack your system, find out if there are any weaknesses on your site. So, if I do find a weakness, nothing bad will happen, then you get the chance of fixing the loophole and discover before a real attack takes place
    * **白帽针对所做的有许可，需要确保不会造成书面许可外的额外损害（比如不会去打碎窗户，来检查安全性**）

## Grey Hats

黑客行为的另一个层面是黑客的动机。Another dimension to hacking is the motive of the hacker.

* **灰帽黑客没有书面权限（许可的白帽），但他的黑客行为是为了 整体利益**。 A grey hat hacker does not have permission, but does the hacking for the overall good.
* **黑帽黑客做同样的事情，也没有权限，但是 为了他们自己或其他人的利益**。A black hat hacker does the same thing, also without permissions, but for their own or other’s gain.
* <font color="deeppink">唯一的区别是动机</font>。 The only difference is the motive.
  * 在法律环境中，在犯罪发生时证明动机是非常困难的。 It is very difficult to prove motive at the time of the offence in a legal setting.
  * **法院关注的是可以证明的内容**。Courts focus on what can be proved.
    * 实际发生了什么。What actually happened.
    * 是否有合法的权限许可允许这样做。Was there legal permission to do it.
  * **证明你是为了社会的利益而黑客是很难的**。Proving you were hacking for the good of society is hard to prove.

## black hats, white hats and grey hats?

黑帽子未经许可进行攻击，而白帽子有许可（渗透测试）。

灰帽子和黑帽子一样，未经许可进行攻击，但声称有良好的动机。在犯罪发生时很难证明动机。灰帽子可能就像告密者一样，同样难以逃避惩罚。

Black hats attack without permission while white hats have permission (penetration testing).

Grey hats are like black hats, attacking without permission, but claim to have good motives. It is hard to prove motive at the time of the offence. Grey hats might be like whistle blowers and have a similarly difficult time avoiding punishment.

## 主动被动测试：Passive vs. Active Testing

那些寻找漏洞的人（被动测试）和主动测试是在试探他们，看他们是否真的有漏洞 Those looking for vulnerabilities and active testing is trying them out to see they really are vulnerability. 

:orange: **被动测试**包括**寻找漏洞**。Passive testing involves looking for vulnerabilities.

* 那扇门在门廊里面，**看起来**可以被踢开。That door is inside a porch and looks like it could be kicked open.

:orange: **主动测试**（试探） Active testing

* 进入门廊并试图踢开这扇门。Going inside the porch and trying to kick the door open.
* 黑帽

:candy: 所以，发现了这个漏洞，通过这种方式，我们显然会增加我们的防御措施 So, having discovered that vulnerability and in that way we will obviously increased our defenses

---

被动测试和主动测试的区别是什么？黑盒和白盒渗透测试呢？两者之间是否存在一些灰色地带？What is the difference between passive and active testing? What about black box and white box penetration testing? Are there some grey areas between the two?

被动测试涉及寻找漏洞，而主动测试涉及尝试闯入。Passive testing involves looking for vulnerabilities, while active testing involves trying to break in.

黑盒是秘密的，而白盒是公开的。Black box is secret while white box is out in the open.

一些探查漏洞的形式被认为是进攻性的。  端口扫描是一种，下载一个完整的网站进行离线调查是另一种。它们就像试探门把手，看门是否被打开。Some forms of probing for vulnerabilities are considered offensive.   Port scanning is one and downloading a complete web site for offline investigation another. They are like trying door handles to see if the door is unlocked.

---

:orange: 该系统将有一个公共接口，这是公共知识。The system will have a public interface, which is public knowledge.

* 任何人都可以尝试（被动），但 . . .Anyone is allowed to try it out, but . . .
  * **有些公共信息只能通过非正常使用系统的调查获得** Some public information can only be obtained by an investigation that is not normal use of the system.
    * 进入门廊详细查看门的情况。Going into the porch to check out the door in detail.

:orange: 被动测试和主动测试之间有一种灰色地带。使用公共信息，发现一些信息也被认为是进攻性的（这可以被称为攻击性，是一个灰色地带）。using public information and find some of the information are also considered offensive, This can be termed offensive and is a grey area.

* 最好先获得许可。Better to get permission first.
* 进入门廊并在门外详细检查，试图把它踢下来，是，可能不是一个非常好的事情。不是正常的使用系统。因此，如果你没有书面许可，你就会有麻烦 So, go into the porch and check it out the door in detail, just sort of tried to kick it down, is, is probably not a very nice thing to do. Not normal use of system. And so you'll be in trouble if you don't have written 

## 黑盒和白盒渗透测试：Black Box and White Box Penetration Testing

:orange:黑盒和白盒测试。Black and white box testing.

* **黑盒，测试功能** Black box, test the functionality
* **白盒，测试内部结构** White box, test the internals

:orange: **黑盒和白盒渗透测试是不同的**。Black and white box penetration testing is different.

* <font color="deeppink">渗透测试是通过模拟恶意黑客的攻击方法，来评估计算机网络系统安全的一种评估方法</font>
* 白盒，每个人都知道正在进行测试。White box, everyone knows that a test is taking place.
* 黑盒，大多数参与的人不知道正在进行测试 Black box, most people involved are unaware that testing is taking place.
  * 当然，<font color="red">签署权限的人知道</font>，他们通常做直接管理。Of course, the person signing the permissions knows. they do though directly management

:orange: **黑盒测试更有效，因为它测试整个系统，包括参与的人**。Black box testing is more effective because it tests the whole system, including the people involved.

* <font color="red">大多数安全问题是由人而不是由硬件或软件引起的</font>。Most security problems are caused by people rather than hardware or software
* 白盒测试 - 基于人们的最佳行为上 people will be on their best behavior
  * 因此，如果是一个被告知的检查，有人检查你正在做的事情，**你可以提前准备，所以你做的一切都是按部就班**。而有一些陌生人在周围徘徊，没有人知道他们是谁，而我实际上是在检查你是否遵循既定的程序。So there's a big difference between an announced inspection, someone's gonna come around and inspect what you're doing and you know when you can prepare in advance and so you're doing everything by the book. And there's some stranger wandering around, nobody knows who they are, and I'm actually checking to see if you're following established procedures.

### 例子

警察决定对我所在街道的所有房屋进行安全检查。The police decide to check the security of all the houses in my street.

* 白盒。**我被告知**，有人会在我的房子周围走动，寻找破门而入的方法 White box: I am told that someone will be walking round my house looking for ways to break in.
* 黑盒：有人在我的房子周围走动，寻找破门而入的方法。 Black box: someone walks round my house looking for ways to break in.
  * 我感到震惊并报警，**因为我没有被告知**（输入输出） I am alarmed and call the police

## 渗透测试(攻击系统)步骤：Penetration Testing Steps

1. 侦查 Reconnaissance
2. 扫描 Scanning
3. 获得访问权 Gaining Access
4. 维持访问 Maintaining Access
5. 掩盖踪迹 Covering Tracks

### 侦察：Reconnaissance

:orange: **这涉及到收集有关目标的信息**。This involves gathering information about the target.

* **这些信息大部分是公共信息，不需要获得许可**。 Much of this is public information and does not need permissions.
* **有些公共信息需要努力去收集**。 Some public information needs effort to collect.
  * 例如，端口扫描。For example, port scanning.
    * 在目标机器上看看有兴趣的端口，看看是否，如果你是，如果你能通过他们。所以你需要一些端口扫描的软件的支持。
    * **这种尝试可以被认为是进攻性的（即使是公共信息**）。This effort can be thought of as offensive.
      * 信息收集可以被记录下来，并向目标发出警报。The information gathering may be logged and the target alerted.
      * 即使在侦查阶段也要注意。如果你在试探系统，看是否能找到它的任何缺陷，那么这可以被认为是一种进攻性行动。 而信息收集工作很可能已经展开。目标很可能被提醒，攻击者也很可能被锁定。因此，你可以去尝试扫描一个敏感网站，然后发现他们并不真正喜欢它，而且他们知道是你在做这个敏感操 And so this is something to be aware of, even in the reconnaissance stage. If you're trying out the system to see if you can find any flaws in it, then that can be considered an offensive action. And the information gathering might well be launched. The target might well be alerted and the attacker might be well being locked as well. So you can go and try and scan all the force of a sensitive site, and then find that they don't really like it and they know it's you that's doing it.  
* **有两种主要的方式来获得这些信息** There are two main ways of getting this information
  * **社会工程** Social engineering
  * **电子手段** Electronic means

#### 社会工程：Social Engineering

社会工程如何帮助侦查？How can social engineering help in reconnaissance?

* Phone calls from respected institutions. Bogus trades people, phishing emails

---

**试图获取信息的电话** Phone calls trying to get information

* 要求捐款的慈善机构：以确定财务状况（确认是否值得shooting）。A charity asking for donations: to determine financial status.
* 内陆税务局要求退税：以获取地址/银行账户信息。The inland revenue with a tax rebate: to get addresses / bank account details.
  * 你会接到一个自称是税务局的人打来的电话，说要退税。谁会想从政府那里得到免费的钱。因此，他们想要的是得到你的银行账户细节中的地址，这样他们就可以把钱存入银行账户，所以很明显，他们试图通过SciTech获取个人信息

**虚假的商人**。Bogus tradespeople.

**凯文-米特尼克：欺骗的艺术** Kevin Mitnick: The Art of Deception

* 他通过采用第二个身份执行了完美的逃跑计划。He executed the perfect escape plan by adopting a second identity.
  * 因此，他很擅长入侵并看到正在发生的事情。而且他对未来颇有规划，所以他意识到在某些时候当局会来找他。所以他利用电子手段创建了第二个身份。完成了所有需要的文件... ...网站上的社会安全号码，和驾驶执照之类的So, he was good at hacking in and seeing what's going on. And he was quite a bit of planning for the future so he realized at some point the authorities would be coming after him. So he used electronic means to create a second identity. Complete with all of the documentation needed the social security numbers in the sites, and driving license and the like. 
  * 当当局对他步步紧逼时。他完成并采用了第二个身份
* 他之所以被抓，是因为他不断入侵警察系统以追踪追捕他的人。He was caught because he kept hacking into the police system to keep track of his pursuers.
* 他们设置了一个甜蜜陷阱，并在追踪他 They had set up a honey trap and were tracking him!
  * 对他来说是不幸的，对当局来说是幸运的。他很想看看警察是如何进行和搜索他的。所以他不断地黑进警方的系统，跟踪追捕他的人，而他们也在跟踪他。所以这就是他被抓住的原因。And so, when, when the authorities are closing in on him. He finished and adopted the second identity. 
Unfortunately for him, fortunately for the authorities. He was keen to see how the police were getting on and searching for him. So he kept hacking into the police systems to keep track of his pursuers honeytrap, and they were tracking him. So that's how he got caught. 
* 他应该在电子方面保持沉默。 He should have stayed quiet electronically.
  * 如果他采用了他的第二个身份，在电子方面保持沉默，那么他就可以逃脱。 但那样他就会被抓住，他就不会写出《欺骗的艺术》这本书 If he'd adopted his second identity stay quiet electronically, then he would have got away with it. But then he would have been caught and he wouldn't have written the book The Art of Deception.

#### 电子信息手段（用于侦察阶段信息收集）：Electronic Information Available

**公共IP地址**。Public IP address.

* 服务器的物理位置。Where the server is physically located.
* 它与哪些其他IP进行通信。What other IPs it communicates with.

**操作系统** Operating System

* **每个操作系统都有自己的弱点**。Each has its own weaknesses.
* 恶意软件可以被设计成只攻击操作系统。Malware can be crafted to attack just the OS.

**邮件列表** Mailing lists

* 用于钓鱼网站和鱼叉钓鱼(高级持续威胁) For phishing and spear fishing
* 如果你有一个公司员工的邮件列表，你可以针对个别雇主进行攻击 So, if you've got a mailing list of employees in the company you can target individual employers to attack them.

#### 哪些公开的信息可以帮助侦查

哪些公开的信息可以帮助侦查？	哪些免费的工具可以帮助？你不应该使用哪个工具，为什么？What publicly available information can help with reconnaissance?	What free tools can help? Which tool should you not use and why?

IP地址，O/S，邮件列表。Maltego, IPvoid, Netcraft, Domain Dossier, Ping, Tracrt, HTTrack. 不要使用HTTrack，因为它建立了一个完整网站的离线版本，这被认为是攻击性行为。IP address, O/S, mailing lists. Maltego, IPvoid, Netcraft, Domain Dossier, Ping, Tracrt, HTTrack. Do not use HTTrack because it builds an offline version of a complete web site, which is considered offensive behaviour.

#### 侦察工具：Reconnaissance Tools

* Maltego
* IPvoid
* Netcraft
* Domain Dossier
* Ping
* Tracert
* HTTrack

#### How to Teach This

我们将使用一个旨在测试侦查工具的**教育网站** We will use an educational site designed to test reconnaissance tools

o http://scanme.nmap.org
o http://45.33.32.156
o 不要在其他网站上使用!

这里是该网站说的。Here is what the site says:

我们建立这个机器是为了帮助人们学习Nmap，也是为了测试和 确保他们的Nmap安装(或互联网连接)是正常的。工作正常。您被授权用Nmap扫描这台机器 或其他端口扫描器扫描这台机器。尽量不要在服务器上敲打得太厉害。一天扫描几次是可以的，但不要一天扫描100次或用这个 网站来测试你的ssh暴力密码破解工具。We set up this machine to help folks learn about Nmap and also to test and make sure that their Nmap installation (or Internet connection) is working properly. You are authorized to scan this machine with Nmap or other port scanners. Try not to hammer on the server too hard. A few scans in a day is fine, but don't scan 100 times a day or use this site to test your ssh brute-force password cracking tool.

当然，我们可以检查自己的IP地址。 Of course, we can check our own IP address.

#### Maltego

![](/static/2021-05-04-16-09-08.png)
![](/static/2021-05-04-16-41-16.png)
![](/static/2021-05-04-16-41-42.png)
![](/static/2021-05-04-16-41-59.png)
![](/static/2021-05-04-16-42-42.png)

#### ipvoid

 http://www.ipvoid.com/
 有18种不同的测试，你可以运行。
 黑名单检查
o 188.40.75.132被列入黑名单，而45.33.32.156没有。
 Ping测试，看一个网站是否在线。
 Whois
 MX（邮件交换）找出哪些系统处理邮件。
 Traceroute：需要多少跳才能到达目的地
o 30跳，全部列出，以到达45.33.32.156

 http://www.ipvoid.com/
 There are 18 different tests that you can run.
 Blacklist check
o 188.40.75.132 is blacklisted, while 45.33.32.156 is not.
 Ping tests to see if a site is online.
 Whois
 MX (Mail Exchange) finds out which systems handle mail.
 Traceroute: how many hops needed to reach the destination
o 30 hops, all listed, to reach 45.33.32.156

#### netcraft

https://www.netcraft.com/

 这与ipvoid类似，但也能发现操作系统。
 有4组主要的功能。

o 反钓鱼

o 安全测试

o 互联网数据挖掘

o 性能测试

 它可以使用命令行运行

  This is similar to ipvoid, but also finds operating systems.
 There are 4 main groups of functionality:
o Anti-Phishing
o Security Testing
o Internet Data Mining
o Performance
 It can run using a command line

#### Domain Dossier 

 https://centralops.net/co/

域名档案工具从关于域名和IP地址的公共记录中生成报告，以帮助解决问题，调查网络犯罪，或只是更好地了解事情的设置。这些报告可能会显示你。 The Domain Dossier tool generates reports from public records about domain names and IP addresses to help solve problems, investigate cybercrime, or just better understand how things are set up. These reports may show you:

业主的联系信息

注册商和登记处信息

托管网站的公司

一个IP地址在地理上的位置

该地址上的服务器是什么类型的

一个网站的上游网络

以及更多

Owner’s contact information
Registrar and registry information
The company that is hosting a Web site
Where an IP address is geographically located
What type of server is at the address
The upstream networks of a site
and much more

---

检查你想要的信息。Check the information you want.

![](/static/2021-05-04-16-46-30.png)

#### HTTrack

http://www.httrack.com/ DO NOT USE

它允许你从互联网上下载一个万维网网站到本地目录，递归地建立所有目录，从服务器上获取HTML、图像和其他文件到你的计算机。HTTrack安排了原始网站的相对链接结构。只需在浏览器中打开 "镜像 "网站的一个页面，你就可以从一个链接到另一个链接浏览该网站，就像你在网上浏览一样。HTTrack还可以更新现有的镜像网站，并恢复中断的下载。HTTrack是完全可配置的，并有一个集成的帮助系统。 It allows you to download a World Wide Web site from the Internet to a local directory, building recursively all directories, getting HTML, images, and other files from the server to your computer. HTTrack arranges the original site's relative link-structure. Simply open a page of the "mirrored" website in your browser, and you can browse the site from link to link, as if you were viewing it online. HTTrack can also update an existing mirrored site, and resume interrupted downloads. HTTrack is fully configurable, and has an integrated help system.

它创建了一个网站的副本，可以在闲暇时离线研究。It creates a copy of the site that can be studied offline at your leisure.

有关网站可以很容易发现正在使用HTTrack。 The site in question can easily find out that HTTrack is being used.

**制作一个离线副本被认为是攻击性行为** Making an offline copy is considered offensive behaviour

### 扫描：Scanning

:orange: **侦查发现了一个有趣的目标** Reconnaissance has found an interesting target

* **然后，扫描将重点放在该目标上，找出更多的信息**。 Scanning then focuses on that target and finding out more information.

:orange: **网络扫描** Network scanning

* **识别活的主机** Identifies live hosts

:orange: **端口扫描** Port scanning

* **识别开放的端口** Identifies open ports

:orange: **漏洞扫描** Vulnerability scanning

* **从公共数据库中查找已知的漏洞，并查看任何已确定的主机和端口是否存在漏洞**。Looks up known vulnerabilities from public databases and sees if any of the identified hosts and ports are vulnerable.

---

哪三种类型的扫描是重要的？哪些免费工具可以提供帮助，为什么不应该在受控环境之外使用这些工具？What three types of scanning are important? What free tools can help and why should they not be used outside of a controlled environment?

网络，端口，漏洞。AngryIP, Advanced Port Scanner, nmap, Zenmap, OpenVAS。寻找漏洞被认为是攻击性的。Network, port, vulnerability. AngryIP, Advanced Port Scanner, nmap, Zenmap, OpenVAS. Looking for vulnerabilities is considered offensive.

#### AngryIP

AngryIP

![](/static/2021-05-04-18-26-20.png)

* https://angryip.org/ DO NOT USE
* It scans IP addresses and ports.

#### Advanced Port Scanner

* https://www.advanced-ipscanner.com/ DO NOT USE
* 与AngryIP类似，但也检测在它发现的端口上运行的程序。 Similar to AngryIP but also detects the programs running on the ports it finds

#### nmap and Zenmap

https://nmap.org DO NOT USE

* **Nmap是一个命令行程序，而Zenmap是一个GUI版本**。 Nmap is a command line program, while Zenmap is a GUI version.
* **它寻找活动的主机和端口**。 It looks for active hosts and ports.
* **它还可以找出在各个主机上运行的操作系统** It can also find out the operating system running on the various hosts

#### OpenVAS

http://www.openvas.org/ DO NOT USE

* A vulnerability scanner.
* OpenVAS是一个全功能的漏洞扫描器。它的功能包括非认证测试、认证测试、各种高水平和低水平的互联网和工业协议、大规模扫描的性能调整和一个强大的内部编程语言来实现任何类型的漏洞测试。 OpenVAS is a full-featured vulnerability scanner. Its capabilities include unauthenticated testing, authenticated testing, various high level and low level Internet and industrial protocols, performance tuning for large-scale scans and a powerful internal programming language to implement any type of vulnerability test.
* 该扫描器伴随着一个具有悠久历史和每日更新的漏洞测试源。这个Greenbone社区反馈包括50,000多个漏洞测试。 The scanner is accompanied by a vulnerability tests feed with a long history and daily updates. This Greenbone Community Feed includes more than 50,000 vulnerability tests.

### 获取访问权：Gaining Access

**网络钓鱼和社会工程试图获得密码，以便我们能够使用它们获得访问权**。 Phishing and Social Engineering try to get passwords so that we can gain access using them.

**扫描也可能发现了一个我们可以利用的漏洞**。Scanning might also have discovered a vulnerability that we can exploit.

实现这一目标的一个工具是Metasploit。请勿使用 One tool to achieve this is Metasploit. DO NOT USE

* Armitage是Metasploit的一个GUI版本。请勿使用 Armitage is a GUI version of Metasploit. DO NOT USE
* This course will not provide details on using Metasploit or Armitage.

### 维护访问：Maintaining Access

可以用什么招数来维护访问？What tricks can be used to maintain access?

* 安装木马程序，安装键盘记录器，安装病毒，获取密码文件进行离线攻击。 Install a trojan, install a keylogger, install a virus, get a password file for an offline attack.

---

**在一次访问中能做的事情有限**。There is only so much that can be done at a single visit.

* 因此，闯入后的首要任务之一是使其更容易再次闯入。 Therefore, one of the first tasks after breaking in is to make it easier to break in again.
* 可以安装**恶意软件**来做到这一点。 Malware can be installed to do this.
  * **特洛伊木马是可以在目标机上运行的可执行文件，无需明确激活。而不被明确激活**。 Trojan horses are executables that can run on the target machine without being explicitly activated.
    * 它们可以打开一个后门，以便更容易进入。The can open a back door for easier access.
    * 它们可以收集数据并将其送回去。They can gather data and send it home.
    * 它们可以提供root权限（root kit）。They can provide root access (root kit)
    * 它们可以提供远程访问（RAT）。They can provide remote access (RAT)

:orange: **病毒**是类似的，但却是附加在其他程序上的代码片段 Viruses are similar but are code fragments attached to other programs.

:orange: **蠕虫**，寻找其他主机进行感染，繁殖。Worms, look for other hosts to infect, multiplying.

键盘记录器记录每一个击键，并将其发送至家中。Keyloggers record every key stroke and send it home.

攻击者可以下载密码文件，进行离线攻击，破解一些密码。 An attacker can download the password file and conduct an offline attack to crack some passwords.

我们还可以尝试权限越界，获得管理权限。 We can also try and escalate privileges and gain admin access.

### 掩盖踪迹：Cover Your Tracks

有两种方法，两者都做是最好的。（不想让系统察觉，从而启动了防护机制）There are two approaches, doing both is best.

* **操纵日志**。Manipulate logs.
* 首先是**匿名连接** Anonymous connections in the first place

#### 操纵日志：Logs

事后运行`CCleaner`是不够的!Running CCleaner afterwards is not enough!

* **改变bash历史设置和其他设置，使信息不被储存在原先位置**。 Change the bash history setting and other settings so that information is not stored in the first place.
* **编辑日志，删除进入系统的痕迹和采取的任何行动**。Edit the logs and remove trace of entry to the system and any action taken.
* **隐藏任何被安装的文件**。 Hide any files that were installed.
  * 添加修改过的程序，如ls和ps以及Windows的等同程序，**使它们不列出选定的文件和进程**。 Add modified programs like ls and ps and Windows equivalent so that they don’t list selected files and processes.
  * **改变插入的任何新文件的日期** Change dates on any new files inserted.

#### 匿名连接：Anonymous Connections

**使用Tor和/或VPN来隐藏原始进入点**。Use Tor and / or VPN to hide the original entry point.

**在有开放WiFi连接的地方进行攻击** Conduct your attack somewhere with open WiFi connections

* 但**不是在有CCTV覆盖的地方**。But not where there is CCTV coverage.

**一些网站实际上是蜜罐，旨在收集攻击者的信息** Some sites are actually honeypots, designed to gather information on an attacker

# 数字取证：Digital Forensics

:orange: **Dead forensics**：过去发生了什么。what happened in the past

* （电脑停止工作时,信息不会改变，所以可以慢慢来 computer stop working, nothing would be changed, so can take time）硬盘、U盘和其他外部存储器的内容。Contents of the hard drive, usb sticks and other external memory

:orange: **Live Forensics**：目前正在发生什么。what is happening at the moment (computer is still working电脑仍工作)

* 内存的内容 Contents of RAM
* 网络流量 Network traffic
* 因此，Live Forensics似乎比dead Forensics好得多，因为你可以得到所有dead Forensics的东西，再加上一些其他信息。

:orange: **但是，此刻正在发生的事情可能正在抹去信息**。What is happening at the moment might be erasing information

* 决定是否立即关闭设备 Decide whether to shut down the device immediately
  * 或者调查它（系统）正在做什么 Or to investigate to see what it's doing. 

---

死亡取证检查永久内存的内容，如硬盘驱动器。这是最容易进行的，因为有充足的时间。  它最容易证明调查没有篡改证据，因为信息没有变化。  现场取证还检查RAM的内容和网络流量。这必须是实时进行的，而且目标可能在同一时间删除了信息。Dead forensics examines the contents of permanent memory such as the hard drive. It is the easiest to conduct since there is plenty of time.   It is easiest to prove that the investigation has not tampered with the evidence since the information is not changing.   Live forensics also examines the contents of RAM and network traffic. It has to be done in real time, and the target might be erasing information at the same time.

# 数字取证的用途：Uses of Digital Forensics

:orange: **在许多情况下，数字取证被用来决定是否发生了犯罪**。In many cases digital forensics is used to decide if a crime has been committed.

* 提供在法庭上站得住脚的证据 Produce evidence that will stand up in court
* 证明调查没有改变原始信息 Prove that the investigation has not changed the original information
  * 你调查的生活中的电脑硬盘，是被从电脑中取出来的，电脑所以是一个保管链的问题，而且在调查期间没有被改变 The computer hard drives in the life that you investigated, were the ones that were taken out of the computer, computer so it's a chain of custody issue, and that they haven't been changed during the investigation. 

:orange:**它还可以用来分析攻击是如何进行的**。It can also be used to analyse how an attack played out.

* 学习下次如何辩护，防护 Learn how to defend next time
* 不一定会导致出庭 Will not necessarily lead to a court appearance

:orange: **犯罪分子利用数字取证技术来查看他们的痕迹掩盖得如何** Criminals use digital forensics to see how well they have covered their tracks.

* 数字取证的第三个用途。如果你是一个罪犯。而你总是担心突然间，拍拍肩膀，警察部队进门来查封你的电脑，你可能会使用数字取证，看看如果警察来没收你的设备，他们会发现什么。因此，**犯罪分子可以使用数字取证，以确保他们正确地掩盖他们的踪迹** The third use of digital forensics. If you're a criminal. And you're always worried about sudden, tap on the shoulder and police force coming through the door and seizing your computer, you might use digital forensics to see what the police would find out if they came and confiscated your equipment. So, criminals can use digital forensics, to make sure that they cover their tracks correctly. 

# 计算机取证调查的步骤：Steps in a Computer Forensics Investigation

1. **证据获取** Seizure
2. **数据采集** Data Acquisition
3. **分析** Analysis
   1. 实际搜索 Physical searching
   2. 使用白名单 Using a whitelist
   3. 注册表检查 Registry examination
   4. 时间线重建 Timeline reconstruction
4. **取证提交** Reporting

---

取证调查的四个步骤是什么？采取哪些步骤来确保检查结果在法庭上站得住脚？What are the four steps in a forensic examination? What steps are taken to make sure that the results of the examination will stand up in court?

* 扣押、获取、分析、报告。对所有的静态信息进行复制，并通过哈希值进行验证。工作只在副本中进行，辩护方也可以接触到物理设备。Seizure, acquisition, analysis, report. Copies are made of all the static information and they are verified by taking hashes. Work is only done with the copies and the defence will also have access to the physical devices.

## 证据获取：Seizure

:orange: **如果一个系统是通电的，决定是【关闭】它还是进行一个 【现场调查**】。If a system is powered on, decide whether to shut it down or conduct a live investigation.

* 检查活动流量。如果路由器的灯在闪烁，那么 有人可能正在远程删除文件。Check for active traffic. If the router lights are flashing then someone might be erasing files remotely.
* 否则，现场取证将提供更多信息。Otherwise, live forensics will provide more information.

:orange: **从Bios中获取时间**。 Get the time from the Bios.

* 可能与实际时间不同。Might be different from the actual time.
* 在文件上伪造时间戳的一个简单方法是，将你的电脑与互联网断开，进入并将时间改为你想放在文件上的假时间，然后创建文件。 One easy way of faking timestamps on files, is to disconnect your computer from the internet, go in and alter the time to the fake time you want to put on the files, and then create the files. 
* 因此，该时间将被存储在BIOS中，所以要检查计算机认为的时间是什么。 So, that time will be stored in the bios, so check what that, what the computer thinks the time is. 

:orange: **对发现的所有设备进行标记和登记**。 Label and register all equipment found.

* 在断开连接之前，记录所有部件之间的连接。Document all connections between components before disconnecting.

:orange: **移除硬盘并放入防静电袋**。Remove hard drives and place in anti-static bags.

* 对所有活动进行拍照 Photograph all activity

## 数据采集：Data Acquisition

:orange: **复制硬盘，确保内容的完整性能够得到证明**。Copy the hard drive, making sure that the integrity of the contents can be proved.

* <font color="deeppink">创建整个硬盘内容的信息摘要</font>。Create a message digest of the entire drive contents.
* **检查该副本的信息摘要是否相同**。 Check that the message digest of the copy is the same.

:orange: **创建一个硬盘的精确拷贝（原始拷贝），确保写 是被阻止的**。Create an exact copy (raw copy) of the drive, making sure that writing is blocked.

* 不要更新文件修改时间等。Don’t update file modified times etc.

:orange: **创建一个副本来工作**。 Create a copy of the copy to work with.

* 如果我们弄乱了这个副本，我们可以回到我们的第一个副本来制作 另一个工作副本。If we mess up this copy, we can go back to our first copy to make another working copy.

:orange: **将最初的硬盘记录下来作为证据。它将不会被再次触及** Log the initial hard drive as evidence. It will not be touched again

2 copy 3 versions

### 采集工具：Acquisition Tools

 FTK: Forensic Tool Kit

 https://accessdata.com/product-download

o Also FTK Imager

## 分析-物理搜索：Analysis: Physical Searching

:orange: **在副本中搜索证据（分析**）。Searching the copy for evidence.

:orange: **许多不同的程序会有帮助**。Many different programs will help.（depends on OS）

* grep来搜索内容。 grep to search for content.
* find有大量的参数：所有最近的文件等。 find has a vast number of parameters: all recent files etc.

:orange: **不要忘记恢复已删除的文件**。 Don’t forget to recover deleted files.

* 如果我们删除一个文件的文件块进入回收，但它们实际上还没有被覆盖，在许多情况下。 if we delete a file for file blocks into recycling, but they haven't actually been overwritten, in many cases.

:orange: **有用的信息(Physical Search**) Useful information

* 用户名单 List of users
* 电子邮件 Emails
* 文件 Documents
* 图片 Pictures

## 分析-注册表检查： Registry examination

:orange: **Windows操作系统在注册表中存储配置信息**。 Windows operating systems store configuration information in the registry.

:orange: **可以使用regedit查看和更改条目**。 Entries can be viewed and changed using regedit.

* 清除假的启动文件。 Getting rid of bogus startup files.
* 要小心!Be careful!
  * often with Windows systems you want to get rid of bogus startup files that have been installed when you buy a computer but you don't want. But that is not very forgiving, and if you make a mistake, you can knock yourself out for computer for a long time. 

:orange: **regviewer更安全，并提供结构化的输出**。 regviewer is much safer and provides structured output.

* 不能做任何改变。Can’t make any changes.
* 比Regedit好，因为它是一个只读程序。is better than regedit because it's a read only program.

:orange: **Linux系统在许多不同的地方存储配置信息**。 Linux systems store configuration information in many different places.

* `/etc`**中的许多文本文件** Many text files in /etc
* **在用户的主目录中有许多隐藏的（.）文件**。Many hidden (.) files in user’s home directories.
  * 在Linux中搜索这种注册表类型的信息是相当困难的。 但它必须要做。 searching for this registry type information is quite difficult in the Linux. But it has to be done.

## 分析 - 时间线重建：Analysis: Timeline Reconstruction

这涉及**检查日志以确定各种事情发生的时间**。This involves examining logs to determine when various things happened.

* 文件的创建和修改时间。File create and modified times.
* 取决于一个准确的时钟 Depends on an accurate clock
* 因此，这就是为什么从BIOS中获取实际时间对于决定计算机认为的时间是什么时候很重要。And so that's why getting the actual time from the bios is important to decide when the computer thought the time was. 

## 分析工具：Analysis Tools

**Autopsy，The Sleuth Kit（TSK）的GUI版本** Autopsy, the GUI version of The Sleuth Kit (TSK)

o 时间线分析 Timeline analysis

o 关键字搜索 Keyword search

o 浏览历史、书签和cookies。Browsing history, bookmarks and cookies.

o 恢复已删除的文件 Recover deleted files

o 多媒体元数据 Multimedia metadata

o 注册表分析 Registry analysis

o 电子邮件分析。 Email analysis.

o 白名单 Whitelisting

* 因此，在白名单中，你提供了一个完全无害的程序清单，你对这些程序不感兴趣，所以现在削减了另一个搜索，实际上需要这样做。so with a whitelist you provide a list of programs that are totally innocuous and that you're not interested in so that cuts now be another search and actually needs to be done.

o 文件系统分析。 File system analysis.

o 创建一个证据柜 Create an evidence locker

---

Foremost

* 提取文件并按类型分类 Extracts files and sorts them by type.

Metacam

* 对图像进行专家分析 Specialist analysis of images.

Zeitline

* 建立一个时间线 Establishes a timeline.
* 一个古老的免费产品 An oldish free product

## 取证提交:Reporting

**最终报告应以适合法院的形式介绍调查结果**。The final report should present the finding in a form suitable for a court.

* 调查结果 The findings
* 方法的描述 Description of methodology
* 文物清单 List of artifacts
* 证据，而不是意见 Evidence, not opinions
  * 但是，当然你有任何意见，你不应该说它看起来像用户在做这个，有证据容易你可以证明他们是，或者你不能 But of course you have any opinions you shouldn't say it looks like the user was doing this, have the evidence easily you can prove that they were or you can't
* 监管链 Chain of custody
* 没有被篡改的证明。 Proof of no tampering.

**如果导致法庭审理，那么证据将在出庭前提交给被告**。 If it results in a court case then the evidence will be presented to the defence before the court appearance.

* 辩方将进行他们自己的取证检查。The defence will conduct their own forensic examination.

# 网络取证：Network Forensics

**在数据包在网络上流动时对其进行分析**。Analyse packets as they flow around the network.

**可以看到计算机的网络控制器可见的所有数据包**。 Can see all the packets visible to a computer’s network controller.

* 不仅仅是那些对计算机的地址。Not just those addresses to the computer.

**网络窃听可以捕获更多**。 Network taps can capture more.

**网络嗅探是非法的**。 Network sniffing is illegal.

**主要工具是Wireshark (wireshark.org) 不要使用** Main tool is Wireshark (wireshark.org) DO NOT USE