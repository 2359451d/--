# Web Security

![](/static/2021-03-08-03-18-21.png)

Web 基本上是一个运行在网络上的客户机服务器应用程序。The web is basically a client server application running over a network.

* 至今所提及的保安技术均适用。The security techniques mentioned so far are all applicable.

**然而，网络确实有其特殊的安全问题**。The web does however have its own peculiar security issues.

* 网络安全很重要 --- 对于许多公司、政府机构和公共机构来说，网络是一个高度可见的前端。The web is a highly visible front end to many corporations, government agencies and public bodies.
* 如果网络服务器被破坏，名誉就会受损。Reputations can be damaged if web servers are subverted.

Web 浏览器易于使用，Web 服务器相对容易配置，Web 内容容易开发。Web browsers are easy to use, web servers are relatively easy to configure and web content is easy to develop.

* **很多人都可以制作网络内容。所以，不一定要经过很好的训练才能做到这一点。所以，很多不安全的网络内容&数据很容易被创建** a lot of people can produce web content. And so, I don't necessarily have to be well trained to do that. So a lot of insecure web content can easily be created and the data is easily created. 

## web相关问题：Problems Specific to the Web

![](/static/2021-03-08-03-21-04.png)

* **底层软件非常复杂，可能隐藏着潜在的安全漏洞** The underlying software is very complex and may hide potential security flaws.
* **有许多新的和升级的系统可能已经正确安装，但容易受到攻击** There are many new and upgraded systems that may have been installed correctly but are vulnerable to attack.
  * 必须安装一个安全更新，因为它之前来最安全的软件有漏洞了，等等不断的更新。所以，软件是不断更新变化的。所以，这是一个问题 you must instal a security update, because it's most secure ever piece of software has got holes in it, and so on that goes on and on and on. So, the software is constantly changing. So that's a problem.
  * 安装需要正确（如不小心安装了带恶意软件的补丁）
* **未经训练的用户通常管理基于网络的服务器** Casual and untrained users commonly manage web based servers.
* **网络服务器可以被利用作为进入整个公司网络的发射台** Web servers can be exploited as launching pads into the entire corporate network.
  * 这意味着，一个安全弱点会导致更多的漏洞被利用
* **大量的 "僵尸 "机器可以用来进行攻击** A large number of ‘zombie’ machines can be used to make an attack.

# 网页安全：Web Page Security

![](/static/2021-03-08-03-27-11.png)

我们将首先考虑 IP 数据包可以加密的两种不同方式 We will start by considering two different ways that IP packets can be encrypted

* **传输及隧道模式** Transport and tunnel mode

HTTP 的快速概述。We then have a quick overview of HTTP.

最后，我们将首先看看网页被攻击的方式。Finally we will first look at ways in which web pages can be attacked.

前10个安全漏洞 Look at the top 10 security vulnerabilities.

---

# IP包加密：Encrypting IP Packets

## IP层安全：Security at the IP Layer

![](/static/2021-03-08-03-30-04.png)

**对于较高层次的应用软件是透明的**。It is transparent to the application software in higher levels.

* 无论应用程序是否生成和使用它们，所有数据包都是加密的。All packets are encrypted, regardless of application generating and consuming them.
* 使用者无须接受保安事宜训练。Users do not need to be trained in security matters.

**IP安全性也可以在防火墙中实现**。It can be implemented in a firewall.

* 内部通信没有加密，也不会导致性能损失。（因为通过防火墙，向外传送数据时，自动得到防火墙加密保护）Internal traffic is not encrypted and so does not incur the performance penalties.

**它可以在个人的基础上实施**。It can be implemented on an individual basis.

* 对外地工作人员有用。Useful for offsite workers.

**可用于敏感应用程序的子网** Can be used in a subnet for sensitive applications.

## 传输或隧道运作模式：Transport or Tunnel Modes of Operation

![](/static/2021-03-08-11-59-32.png)

IP数据包由两部分组成 An IP packet has two parts

* 有效载荷 Payload: 所携带的数据
* IP头：起点、目的地和其他信息 Header: origin, destination and other information.
  * 保证通信双方地址正确

**传输模式** Transport Mode

* **为 IP 有效负载提供保护，但不保护 IP 头** Protection is provided for the IP payload but not the IP header.

**隧道模式** Tunnel Mode

* **为整个 IP 数据包提供保护，包括报头** Protection is provided for the entire IP packet, including the header.
* 它是使用传输模式的外部 IP 数据包的有效载荷 It is the payload of an outer IP packet, using transport mode.
*** 它对于从一个防火墙到另一个防火墙的外部传输很有用，可以隐藏流量分析（以及隐藏头信息，如收方IP）** It is useful for transport in the outside world from one firewall to another, hiding traffic analysis.

## IP提供的安全服务：Services Provided

![](/static/2021-03-08-14-42-46.png)

**无连接完整性，因为它处于 IP 级别**。Connectionless Integrity because it is at the IP level.

**使用签名消息摘要进行身份验证**。Authentication using a signed message digest.

* 在传输模式下，对有效负载进行身份验证。In transport mode the payload is authenticated.
* 在隧道模式下，验证原点和目标。In tunnel mode the origin and destination are authenticated.

**封装的安全有效负载的机密性** Confidentiality through encapsulated security payload

* 在运输方式下，有效载荷是保密的。In transport mode the payload is confidential.
* 在隧道模式下，起点和目的地是保密的。In tunnel mode the origin and destination are confidential.

**连接细节类似于 TLS**。The connection details are similar to TLS.

# HTTP

![](/static/2021-03-08-14-54-03.png)

**HTTP 是 WWW 的基本协议**。HTTP is the basic protocol for the WWW.

客户机向服务器发出请求。A client makes a request to a server.

**这是一个无状态协议**。It is a stateless protocol.

* 客户机和服务器之间的每次传输都是独立的。Each transmission between a client and server is independent.
* 服务器只处理即时请求，它不记忆以前的任何请求。The server only deals with the immediate request, it does not remember any of the previous ones.
* 协议不识别一个会话，它只是处理一组传输。The protocol does not identify a session, it just deals with a collection of transmissions.
* 应用程序必须提供会话信息以连接相关的传输。The application must provide session information to tie together related transmissions.

没有内置的加密技术。There is no built in encryption.

## GET

![](/static/2021-03-08-14-57-41.png)

GET 命令由客户机发送并请求指定的资源。A GET command is sent by a client and requests a named resource.

* GET /images/logo.png HTTP/1.1
* 使用 HTTP 版本1.1给我一个名为/images/logo.png 的文件 Give me the file called /images/logo.png using HTTP version 1.1

有一种同时发送信息的机制是不安全的，但经常使用。There is a mechanism for sending information at the same time that is insecure but is often used.

* GET /images/logo.png?user=ron HTTP/1.1
* 这将发送参数用户的值。This sends the value of the parameter user.

GET 消息在 IP 数据包的头部发送。The GET message is sent in the header part of the IP packet.

* 标头还包含服务器的地址。The header also contains the address of the server.
* 它必须清楚地送来，然后才能交付。It must sent in the clear to be delivered.

## POST

![](/static/2021-03-08-15-01-28.png)

POST sends information to the server

* POST /images/logo.png HTTP/1.1

文件和任何参数现在都在 IP 数据包的主体中发送。 The file and also any parameters are now sent in the body of the IP packet.

* 在 IPSEC，内容体可以使用传输模式进行加密

## COOKIES

![](/static/2021-03-08-15-02-36.png)

**Cookie 是从服务器发送到客户机并存储在客户机上的文件**。Cookies are files sent from the server to the client and stored on the clients machine.

它们主要用于维护会话。They are mainly used to maintain a session.

在会话的第一次传输中，cookie 存储在客户机上。The cookie is stored on the client machine in the first transmission of the session.

它唯一地标识会话。It uniquely identifies the session.

这个 cookie 然后在后续 GET 命令的主体中发送回来。This cookie is then sent back in the body of the subsequent GET commands.

还可以用来跟踪用户。Cookies can also be used to track users.

# OWASP Top Ten Security Risks

![](/static/2021-03-08-15-49-50.png)
![](/static/2021-03-08-15-51-38.png)

开放式 Web 应用安全项目。Open Web Application Security Project.

* 提供免费资源以协助网络保安的网上社区。An online community providing free resources to help web security.

1. 注入（漏洞）攻击 Injection.
2. 失效的验证。Broken Authentication.
3. 敏感数据泄露。Sensitive Data Exposure.
4. XML 外部实体泄露 XXE。XML External entity XXE.
5. 无效访问控制 Broken Access Controls.
6. 安全配置错误 Security Misconfiguration.
7. 跨站脚本攻击 Cross Site Scripting XSS.
8. 不安全的反序列化漏洞 Insecure Deserialisation.
9. 使用含有已知漏洞的组件 Using Components with Known Vulnerabilities.
10. 日志与监控不足 Insufficient Logging and Monitoring.

## 注入攻击：Injection

![](/static/2021-03-08-22-23-56.png)

**用户向应用程序提供需要数据的命令**。Users provide commands to application that are expecting data.

数据由一个混合**命令和用户提供的数据**的脚本处理。The data is being processed by a script that mixes commands and user supplied data.

许多脚本语言(例如 SQL)可能会被愚弄，将用户提供的部分信息视为**命令**。Many scripting languages, such as SQL, can be fooled into treating part of the user supplied information as a command.

* 它们将在服务器上运行，得到服务器“用户”(通常是管理员)的授权。They will be running on the server with the authorisation of the server ‘user’, often administrator.
* 他们可以访问和返回敏感信息。They can access and return sensitive information.
  * 利用应用程序弱点，通过恶意字符将恶意代码(命令)写入数据库，获取敏感数据或进一步在服务器执行命令。

---

### SQL注入：SQL Injection

![](/static/2021-03-08-22-28-38.png)

许多系统将 SQL 查询传递给数据库子系统。Many systems pass SQL queries to a database subsystem.

* 查询通常是由用户输入构建的。The queries are often constructed from user input.
* **这些查询将包含可能被攻击者误用的元字符**。These queries will contain meta-characters which can be misused by an attacker.

在第一个示例中，我们使用一个名为 Usr 的数据库，该数据库将用户名和密码存储在名为 `UserName` 和 `Password` 的列中。In this first example, we are using a database called Usr that stores user names and passwords in columns called UserName and Password.

* 我们的登入页面将从用户那里获得相关信息，并将其存储在变量`user` & `pass`中，然后传递。Our login page will get the relevant information from the user and store it in the variables user and pass.

---

![](/static/2021-03-08-22-31-49.png)

然后，我们在 Java 中构造一个查询字符串，并使用它来访问数据库。We then construct a query string in Java and use it to access the database.

```java
String Query = "SELECT * FROM Usr "
+ "WHERE UserName='" + user +"' "
+ "AND Password='" + pass + "'";
```

* 如果输入文本是 `user = ron` 和 `pass = abcdefgh`，那么提交到数据库的查询将是
* `SELECT * FROM Usr WHERE UserName='ron' AND Password='abcdefgh'` If the input text was user = ron and pass = abcdefgh then the query submitted to the database would be:
  * 这将如预期的那样工作。This would work as expected.
  * `"` command mode
  * `'`single quote - data mode

---

#### 例子1

SQL注入问题↓

![](/static/2021-03-08-22-38-36.png)

如输入，On the other hand, if the input text was user = ron' -- and **pass was empty**, the generated SQL would be:

* `SELECT * FROM Usr WHERE UserName='ron' –-' AND Password=''`
  * 前提存在用户ron，注释标记就使密码校验无效 The `–-` introduces a SQL comment, which invalidates the password check, provided there is a user called ron.
* 这种攻击只有在我们知道用户名的情况下才有效，而用户名通常不像密码那样秘密。 This attacks only works if we know the user name, which is not usually as secret as the password.

问题不在于 SQL 中的--注释。The problem is not the –- comment in SQL.

![](/static/2021-03-09-00-00-20.png)

* 问题出在 Java 程序中，该程序允许将元字符 `‘` 作为数据输入。The problem is in the Java program which has allowed the metacharacter ' to be entered as data.
* 当 SQL 解析其查询时，它使用第一个`‘`从命令模式切换到数据模式。When SQL parses its query, it used the first ' to switch from command mode to data mode.
* 第二个’切换回命令模式，依此类推。The second ' switches back to command mode, and so on.

这使我们能够将命令作为数据输入。This has allowed us to enter commands as data.

* 这是一个棘手的问题（可以禁用元字符，但是，`‘`可能是名称的有效部分。This is a tricky problem because ' can be a valid part of a name.

例如，o’donnell。O'Donnell, for example.

#### 例子2

![](/static/2021-03-09-00-01-30.png)

下面是一个将代码注入 SQL 查询的真实例子。Here is a real life example of injecting rogue code into a SQL query.

* 可透过以下网址浏览付款系统网页:A payment system web page could be accessed with the URL:
  * Http://pay.example/default.asp?id=3;shutdown
* **将字符串3; SHUTDOWN 作为 id 号注入到 SQL 中，并多次适当地关闭数据库**。The string 3;SHUTDOWN was injected into the SQL as the id number and duly shut down the database, several times.
* 注意通过 GET 参数传递的参数。Note the passing of a parameter via a GET parameter.
* 这个建议是在一个讨论小组里提出来的，提出这个建议的人和那些不相信这个建议的人一起被警察拜访。This suggestion was made in a discussion group, and the person making the suggestion, together with the unbelievers who tried it out were visited by the police.

### 随机SQL注入：Blind SQL Injection

![](/static/2021-03-09-00-05-55.png)

前例sql语句都是预先编译的，

* 但是不知道字段名，情况下，仍可以通过查询的错误信息推断出字段存在与否等信息 If we did not know this we can still get some information by trying different queries.
  * Telling if they worked or not would provide some information.
  * Error messages may tell us something.

### 命令注入：Command Injection

![](/static/2021-03-09-00-09-22.png)

**Linux 使用命令语言 bash 来运行命令**。Linux uses a command language called bash to run commands.

* **可以编写脚本(程序)来运行公共序列**。Scripts (programs) can be written to run common sequences.
* **这些脚本可以使用变量使它们更加通用**。These scripts can use variables to make them more general.
* **这些变量可以包含恶意代码**。These variables can contain malicious code.

```
finger $user user='ron;
rm –rf *'
```

### 不可信的用户输入：Never Trust User Supplied Input

![](/static/2021-03-09-00-48-38.png)

在将用户输入包含到命令脚本中之前检查用户输入 Check user input before including it in a command script.
这可能很困难，因为要检查的东西太多了 This can be difficult because there are so many things to check.

## 失效验证：Broken Authentication

![](/static/2021-03-09-00-50-22.png)

糟糕的实现会破坏密码(密码控制失效)，让用户拥有超出应有权限的权限。Poor implementation can compromise passwords, letting users have more permissions than they should.

* 例如，允许大量尝试猜测密码的登录页面。Example, a login page that allows a large number of attempts to guess a password.

### 用户密码问题：Password Problems: Users

![](/static/2021-03-09-00-52-24.png)

* 好的密码很难记住。Good passwords are hard to remember.
* 弱密码容易受到穷举法的攻击。Weak passwords are vulnerable to a brute force attack.

**许多账户，所有需要密码的账户都鼓励密码重用**。Many accounts, all requiring passwords encourages password reuse.

* 创建一个人们想要使用的应用程序，并收集用户名和密码。Create an app that people would want to use and collect user names and passwords.
  * 然后在不同的网站上尝试用户名&密码，比如网上银行。。Try the user names and passwords on sensitive sites such as internet banking.
* **如果非要复用，不要使用敏感密码**

密码轮换和复杂性要求会导致用户忘记密码，不得不重置密码。Password rotation and complexity requirements result in users forgetting their passwords and having to reset them.

* 这通常要求用户提供较弱的信息，如其母亲的姓。（**这些信息可以通过社交媒体获得**）This often requires the user to provide weaker information such as their mother’s maiden name.（which might be obtained through social media. ）

### 系统密码问题：Password Problems - Systems

![](/static/2021-03-09-00-59-39.png)

该系统可能有弱密码存储。The system may have weak password storage.

* 以纯文本形式存储。Store as plain text.
* 使用无加盐的散列。Use unsalted hashes.
  * 可以运行大量密码破解程序  bulk password cracking programmes can be run.
* 使用弱散列算法，例如 MD5/SHA1。Use a weak hash algorithm such as MD5/SHA1.

:orange: 解决办法 solutions

* 使用2因素身份验证 Use 2 factor authentication
  * like banking
* **记录认证失败并监视日志** Log authentication failures and monitor the logs.

## 敏感数据泄露: Sensitive Data Exposure.

![](/static/2021-03-09-01-03-07.png)

**只有获授权的用户才可查阅敏感资料**。sensitive data should only be accessible to authorised users.

* **有些数据没有受到保护，可能被未经授权的用户看到**。Some data is not protected and can be seen by unauthorised users.
* 它应该被加密并存储在防火墙后面的计算机中。It should be encrypted and stored in computers behind a firewall.

**一些网站通过隐藏来使用安全性，在这种情况下，敏感页面不会受到登录过程的保护**。Some web sites use security by obscurity, where sensitive pages are not protected by a login process.

* 如果攻击者可以猜到页面的 URL，那么他们就可以访问页面。 If the attacker can guess the page URL then they can access the page.
* 因此，安全性依赖于没有人意识到敏感网页的名称。So the security relies on people not realising the name of a sensitive web page.
  * 有一些工具可以让你得到一个网站的site map，这样你就可以得到所有的网页名称。因此，如果攻击者可以获得页面 URL，那么他们就可以访问页面，而不需要登录，这是一种常见的敏感数据公开方式 There are tools that allow you to get a complete map of a website so that you can get all of the web page names. So if the attacker can get the page URL, then they can access the page without being required to login is a common way of sensitive data exposure. Another way is hidden. 

### 表单隐藏字段：Hidden Fields in Forms

![](/static/2021-03-09-01-08-00.png)

* **另一种依赖于隐藏的安全性的技术是将敏感信息放在表单的隐藏字段中** Another technique that relies on security by obscurity is to put sensitive information in hidden fields of forms.
  * 隐藏字段不显示在网页上。The hidden field does not show up on the web page.
  * 但是如果检查网页的 html，就可以看到 But it can be seen if the html for the web page is examined.
* **这通常用于会话管理** This is often used for session management.
  * 如果字段被更改，则可以访问与不同用户关联的会话 If the field is changed then a session associated with a different user can be accessed.

```
<form action=blah.jsp method=“post”>

<!-- hidden user pass, server sends to client -->
<input type=“hidden” name=“sessid” value=“123”/> 
</form>
```

## XML 外部实体泄露 XXE。XML External entity XXE.

![](/static/2021-03-09-01-12-01.png)

XML 是一种层次化的数据文件格式。XML is a hierarchical data file format.

* 可以通过外部实体引用服务器内部数据 Data can come from inside the server via an external entity reference.
  * 这可能会暴露敏感数据 This might expose sensitive data.
    * General information gathering.
* 还可以实现远程代码执行 It is also possible to achieve remote code execution.

## 无效访问控制 Broken Access Controls

![](/static/2021-03-09-01-14-29.png)

**登录到一个系统让用户在机器上运行程序**。Login into a system lets the user run programs on the machine.

* 与用户认证的区别,认证-允许使用服务的用户，权限访问控制-决定这些认证用户的特定访问权限
* **访问控制决定他们被允许做什么**。Access controls decide what they are allowed to do.
  * 例如，只访问该用户的文件。Example, access only that user’s files.
* 如果访问控制没有得到正确实现，那么用户可能会越权。If the access controls are not implemented properly then the user might be able to do more than they should.
* 可能导致权限提升。This can lead to privilege escalation.

---

存在的问题 main problems

* 不正确实现 Incorrect implementation.
* 用户提供的数据未经充分检查就被使用 User provided data is used without sufficient checks

解决方式 solutions

* 禁用目录列表 Disable directory listings.
* 记录访问失败和触发警告 Log access failures and trigger alerts.

## 安全配置错误： Security Misconfiguration

![](/static/2021-03-09-01-19-40.png)

* 没有正确设置安全系统。Not setting the security up properly.
* 不安全默认配置 Insecure default configurations.
* 包含敏感信息的错误消息 Error messages containing sensitive information.
  * 可能会允许SQL注入攻击  can allow SQL injection attack.
* 没有修补或升级系统 Not patching or upgrading systems.

## 跨站脚本攻击： Cross Site Scripting XSS.

![](/static/2021-03-09-01-22-14.png)

数据输入相关问题，客户端提供的数据是在受害者浏览器中运行的脚本(如 javascript 程序)。Data provided by the client is a script (EG javascript program) that is run in the victim’s browser.

* 具有潜在的管理员特权。With potentially admin privileges.
* 可以劫持用户会话。Can hijack user sessions.

如果脚本可以存储在服务器上，那么稍后可以使用其他用户的特权运行该脚本，并且获取信息。If the script can be stored on the server then the script can be run later, potentially with another user’s privileges.

* 如脚本收集用户输入，然后作为网页的一部分，发回恶意机器或者运行在服务器上 The system gathers user input. And then it incorporates that as part of a webpage that is perhaps sent back to the machine or run on the server. 

## 不安全的反序列化漏洞：Insecure Deserialisation

![](/static/2021-03-09-01-27-22.png)

**一些编程语言，例如 Java，允许以二进制形式(串行化)存储复杂的数据结构，然后恢复到二进制形式(反序列化**)。Some programming languages, eg Java, allow complex data structures to be stored in their binary form (serialisation) and then restored to binary form (deserialised).

* **这样做更有效率，但也允许利用反序列化机制的恶意数据对象**。This is more efficient but also allows for malicious data objects that exploit the deserialisation mechanism.
* **它还可以应用于具有内部结构的对象，如 cookie**。It can also apply to objects with internal structure such as cookies.
* **Cookie 通常用于获得序列化响应**。Cookies are often used to obtain serialisation.
  * **它们存储在客户机上，因此可以被恶意客户机修改**。They are stored on the client machine and so can be modified by a malicious client.

## 使用含有已知漏洞的组件： Using Components with Known Vulnerabilities

![](/static/2021-03-09-01-30-49.png)

**组件是应用程序的一部分，例如库、框架和软件模块**。Components are parts of an application, such as libraries, frameworks and software modules.

* **它们通常作为单独的文件存储(例如. dll) ，并在应用程序需要时加载**。They are often stores as separate files (.dll for example) and loaded when needed by the application.
  * **因此，无需更换应用程序即可更换（可以被更换成恶意组件 replaced by a malicious component）**。They can thus be replaced without having to replace the application.
* **它们具有与应用程序相同的特权**。They run with the same privileges as the application.

## 日志记录和监测不足: Insufficient Logging and Monitoring

![](/static/2021-03-09-01-33-25.png)

**记录运行的程序和所采取的行动**。Logs record programs that run and actions taken.

* 它们可以启用或禁用。They can be enabled or disabled.
* 如果它们被禁用，那么恶意活动将不会被记录。If they are disabled then malicious activity will not be recorded.

**如果保存了日志，那么就需要对它们进行监控，以便及时发现任何攻击**。If logs are kept then they need to be monitored to detect any attacks in a timely manner.

日志记录和监控不足是一个常见的问题 insufficient logging and monitoring is a common problem
