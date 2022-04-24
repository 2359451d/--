# Content

- 理解威胁建模的基础。
- 描述**威胁建模的四个步骤框架**。
- 将**STRIDE**应用于软件设计。
  - STRIDE是微软的Praerit Garg和Loren Kohnfelder建立的威胁模型[1]，目的是在找出电脑安全上的威胁[2]。是六种安全威胁分类英文的字首组成的助忆词[3]。
- 使用攻击树来寻找基于软件的系统或流程中的威胁。
  - 攻击树：attack tree, defense tree是一种意思
  - **与STRIDE互相补充**
  - 可视化威胁，，路径，，目等
- 使用**攻击树建立攻击-防御方案**。
  - 一种树形结构，提供攻击如何实现的思路
- 了解创建一个有效的攻击树的步骤。

---

* [Content](#content)
* [威胁建模定义：What is Threat Modelling](#威胁建模定义what-is-threat-modelling)
* [威胁模型优点：Advantages](#威胁模型优点advantages)
* [为什么进行威胁建模：Reasons to Threat Model](#为什么进行威胁建模reasons-to-threat-model)
* [威胁建模-4步框架：Four Step Framework](#威胁建模-4步框架four-step-framework)
* [如何威胁建模？How to Threat Model](#如何威胁建模how-to-threat-model)
* [1-(白板)图-理解/建模系统架构：What Are you building](#1-白板图-理解建模系统架构what-are-you-building)
* [图-信任边界：Trust Boundaries](#图-信任边界trust-boundaries)
* [2-识别威胁(STRIDE)：What can go wrong](#2-识别威胁stridewhat-can-go-wrong)
* [TIPS to Identify Threats](#tips-to-identify-threats)
* [3-解决威胁：What are we going to do about it](#3-解决威胁what-are-we-going-to-do-about-it)
* [例子-解决威胁](#例子-解决威胁)
  * [解决欺骗威胁: Addressing Spoofing Threats](#解决欺骗威胁-addressing-spoofing-threats)
  * [解决篡改威胁：Addressing Tampering](#解决篡改威胁addressing-tampering)
  * [解决否认：Addressing Repudiation](#解决否认addressing-repudiation)
  * [解决信息泄露：Addressing Informationn Disclosure](#解决信息泄露addressing-informationn-disclosure)
  * [解决拒绝服务：Addressing Denial of Service](#解决拒绝服务addressing-denial-of-service)
  * [解决特权提升：Addressing Elevation of Privilege](#解决特权提升addressing-elevation-of-privilege)
* [4-验证结果：Did we do a good job](#4-验证结果did-we-do-a-good-job)
* [Summary](#summary)
* [===========](#)
* [攻击树历史&概念：History - Attack Trees](#攻击树历史概念history---attack-trees)
* [攻击树例子](#攻击树例子)
* [构建攻击树：Working with Attack Trees](#构建攻击树working-with-attack-trees)
* [使用攻击树识别威胁：Using Attack Trees to find Threats](#使用攻击树识别威胁using-attack-trees-to-find-threats)
* [攻击-防御场景：Attack-Defence Scenarios](#攻击-防御场景attack-defence-scenarios)
* [建立新攻击树：Creating new attack trees](#建立新攻击树creating-new-attack-trees)
* [攻击树风险分析-结构化表示：Structured Representation](#攻击树风险分析-结构化表示structured-representation)
* [安全设计决策：Secured Design Decisions](#安全设计决策secured-design-decisions)
* [风险分析：Risk Analysis](#风险分析risk-analysis)
* [使用攻击树进行风险分析：Risk analysis using attack trees](#使用攻击树进行风险分析risk-analysis-using-attack-trees)
* [安全政策文档：Security Policy Document](#安全政策文档security-policy-document)
* [保护配置文件&安全目标：Profiles and targets](#保护配置文件安全目标profiles-and-targets)
* [安全功能性需求:Security FUnctional Requirements](#安全功能性需求security-functional-requirements)
* [安全机制：Security Mechanisms](#安全机制security-mechanisms)
* [安全保障需求：Assurance Requirements](#安全保障需求assurance-requirements)
* [评估保障级：evaluation assuarance level](#评估保障级evaluation-assuarance-level)
* [Summary](#summary-1)
* [===========](#-1)
* [OAuth Case Study](#oauth-case-study)
* [身份委托：Identity-Delegation](#身份委托identity-delegation)
* [身份委托模型：Identity-Delegation Model](#身份委托模型identity-delegation-model)
* [直接委托：Direct Delegation](#直接委托direct-delegation)
* [间接委托：Indirect Delegation / Brokered delegation](#间接委托indirect-delegation--brokered-delegation)
* [OAuth](#oauth)
* [访问令牌 & 认证码：Accesss Token & Authentication code](#访问令牌--认证码accesss-token--authentication-code)
* [OAuth原理：How it works](#oauth原理how-it-works)
* [===========](#-2)
* [OAuth2.0](#oauth20)
* [认证流：Authorization FLows](#认证流authorization-flows)
  * [资源所有者密码凭证 Resource Owner Password Credentials](#资源所有者密码凭证-resource-owner-password-credentials)
  * [认证码：Authorization Code](#认证码authorization-code)
  * [隐域模型：Implicit](#隐域模型implicit)
  * [客户端凭证：Client Credentials](#客户端凭证client-credentials)
* [Web应用安全：Security of a web based Application](#web应用安全security-of-a-web-based-application)
* [攻击面](#攻击面)
* [客户端威胁：Threats-client](#客户端威胁threats-client)
* [认证服务器威胁：Threats-Authentication Server](#认证服务器威胁threats-authentication-server)
* [资源服务器威胁：Threats-Resource Server](#资源服务器威胁threats-resource-server)
* [数据库服务器：Threats-Database Server](#数据库服务器threats-database-server)
* [===========](#-3)

# 威胁建模定义：What is Threat Modelling

- 简而言之，**威胁建模是使用【抽象概念】来帮助你考虑风险**。•	In a nutshell, threat modelling is the use of abstractions to help you consider risks.
- 涉及到**对产品或服务架构和可能发生的问题的共同理解**。•	Involves developing a shared understanding of a product or service architecture and the problems that could happen.

**当你为威胁建模时，你通常使用两种类型的模型之一**。•	When you model threats, you typically use one of two types of model.

1.	你**正在构建的模型**。1.	The model for what you are building.
2.	为你正在建造的东西的**威胁**建立的模型。2.	The model for the threats of what you are building. 【反用例，使用时可能存在什么问题

# 威胁模型优点：Advantages

威胁建模使用抽象概念来理解风险 •	Use abstractions to understand risk.

- <font color="red">不管怎么样，，代码层面的安全检测 & 系统概念层面的分析都很重要。使用合适方法能更有效进行安全分析，并且与stakeholders交流系统威胁。要考虑到每个层面沟通的人经验背景不同，而这些人都有可能参与决策过程</font>
- **静态分析=代码层面**的风险评估。•	Static analysis = risk assessment at the code level.
- **STRIDE/Attack Trees = 概念层面**的风险评估。•	STRIDE/Attack Trees = risk assessment at the conceptual level.
- 一个<font color="deeppink">比静态分析更全面的分析。</font>•	A more holistic analysis than static analysis.
  - 利用**人类的直觉**来理解什么会出错【而不是计算场景下什么威胁存在。•	Leverage on human intuition to understand what can go wrong.
    - 某物如何被攻击？•	How can something be attacked?
    - 它可能会受到什么样的极端影响？•	What kind of extremes can it be subject to?
  - 因为你从架构层面开始，你可以**把工作重点放在最重要的系统上，而不是应对渗透测试或合规性的 "随机 "问题**。•	Because you’re starting at the architectural level, you can focus your work on the systems that are most important, rather than responding to “random” issues from penetration testing or compliance.
  - 渗透测试能发现特定问题，，但不意味着能发现系统本身的设计/软件组件/外部依赖存在问题（低层次的
- 因为**威胁建模使用模型**，你可以在软件开发和运营中**应用相同的整体方法**，从而导致**更有效的沟通和协作**。•	Because threat modelling uses models, you can apply the same overall approach across software development and operations, leading to more effective communication and collaboration.
  - 一套可重复进行的步骤，能发现威胁的位置等。
  - <font color="red">最重要的是stakeholders，开发者等能以一种universal language进行问题的沟通</font>
    - STRIDE，攻击树。【**这些方法指导你对一个软件模型的分析**。 These approaches guide your analysis of a software model.
    - 尤其是攻击树提供可视化的树状结构，促进交流

# 为什么进行威胁建模：Reasons to Threat Model

- **尽早发现安全问题**。•	Find security issues early.
  - 如果在所有系统设计好之后，再进行问题的发现&解决，非常费成本
  - 如果定期的采取某过程措施，能在复杂问题出现的时候(或出现前)尽早发现，并在产生级联效应之前采取缓解措施
- **了解你的安全要求**。•	Understand your security requirements.
- **设计并交付更好的产品**。•	Engineer and deliver better products.
- **解决其他技术无法解决的问题**。•	Address issues that other techniques won’t.

# 威胁建模-4步框架：Four Step Framework

![](/static/2022-04-16-21-21-40.png)

1. **对你正在建立、部署或改变的系统进行建模**。Model the system you’re building, deploying or changing.

2. **使用模型寻找威胁**。Find threats using the model.

3. **解决**这些威胁 Address the threats.

4. **验证**结果的完整性和有效性 Validate the result for completeness and effectiveness.

# 如何威胁建模？How to Threat Model

- 威胁建模的简单化方法包括回答四个基本问题。•	The simplistic approach to threat modelling involves answering four fundamental questions:

1.	你**在建造什么**？1.	What are you building? 【理解架构

- 建立系统模型。•	Model the system.
- 识别**信任边界**。•	Identify trust boundaries.
  - 用于与stakeholder交流，组件之间通信的信任状态可能在哪改变(where the trust state of communications between components can change)
- 这些是分析的先导。•	These spearhead the analysis.
* 理解各个组件之间如何通信的
  * 通信频率，通信特性（内部/外部，受控/不受控
* 不管采取什么开发策略（敏捷，瀑布流），架构一开始可能是不够清晰的。重要的是**coding之前有一定的架构概念**，，威胁建模不需要特别具体的架构解释（简单表示就够了 【架构简单表示，复杂表示都不重要，STRIDE步骤都能应用】

2.	它**可能出什么问题**？2.	What can go wrong with it?

- 使用**STRIDE**
  - 这玩意除了威胁建模，还能用于评估一个系统风险，，做的够不够好，，可以检查软件架构图。但是源码级别的安全一般进行静态分析【**反正理解软件系统的威胁，要在不同层面分析。不同层面使用的工具，框架不同**
  - 把STRIDE方法应用于模型上的每一个实体和关系，步骤1的图。）•	Apply this to every entity and relation on the model from 1)
- **在考虑内部威胁之前，从外部关系开始总是好的**。•	Always good to start with the external relations, before thinking about internal threats.
* 考虑功能性&非功能型需求
  * 逆实现需求会发生什么
    * 如果链接断开，如果数据库泄露/损坏会怎么样
    * （物理安全层面）如果访问卡丢失？

3.	我们**要怎么做**？3.	What are we going to do about it?

* 缓解、转移、消除、接受。•	Mitigation, Transference, Elimination, Acceptance.
  * 看后面，不同应对方法有不同影响
  * 比如**消除威胁**一般要牺牲某些特性，如果真牺牲了某些特性，需要和stakeholders进行阐述，确保他们理解，并阐述决策原因
  * **接受威胁**，要么成本问题。要么不得不接受某些组件的威胁，比如消息摘要的哈希碰撞风险，不可能完全消除，，如果风险可能性足够低，那么也是能接受的。
* 针对威胁，可能采取的缓解措施mitigation

4.	我们的工作做得好吗？4.	Did we do a good job?

- 评估处理威胁方法的有效性。•	Evaluate the effectiveness of the threat handling approach.
- 通常是对成本/费用/开发时间与预期收益的预测。•	Often a projection of cost/effort/development time vs perceived benefit.
* **向自己,他人解释**是否有效的缓解，或消除了威胁
  * 除了要能识别威胁，还需要能够缓解 & 有效的证明确实缓解了（以**某种测试或其他方式**）

---

:orange: 先以高层次表示找出威胁位置 & 可能对高层次进行分解，再次考虑可能存在的威胁

# 1-(白板)图-理解/建模系统架构：What Are you building

:orange:考虑基于威胁的假设在实体间的哪个部分存在，，实体间 的关系是怎样的

- **图**解是沟通你正在建立的东西的好方法。•	Diagrams are a good way to communicate what you are building.
- 有很多方法来描绘软件，你可以从**白板图**开始，说明数据如何在系统中流动。•	There are lots of ways to diagram software and you can start with a whiteboard diagram of how data flows through the system.

- 一个简单的网络应用程序的例子，有一个网络浏览器、网络服务器、一些业务逻辑和一个数据库。•	Example of a simple web app with a web browser, web server, some business logic and a database.
  - ![](/static/2022-04-17-15-44-41.png)

# 图-信任边界：Trust Boundaries

- 添加边界以**显示谁控制了什么，是改进该图的一个简单方法**。•	Adding boundaries to show who controls what is a simple way to improve the diagram.
- 你可以很容易地看到，**跨越这些边界的威胁很可能是重要的威胁，而且可能是一个开始识别威胁的好地方**。•	You can easily see that the threats that cross those boundaries are likely important ones, and may be a good place to start identifying threats.
- 这些边界被称为信任边界，你应该在**不同实体控制不同东西的地方画出这些边界**。•	These boundaries are called trust boundaries, and you should draw them wherever different people control different things.

![](/static/2022-04-17-15-58-22.png)

* 比如busIness logic & offsite db之间可能存在问题，我们能以这两个实体间为起点。
* 建立信任边界的目的就是识别潜在问题可能在哪存在。实体间通信性质如何改变
* 可以考虑给实体间交互关系进行标记，以便与他人沟通交流分析问题存在位置时候没那么模糊
  * 用于与stakeholder交流，组件之间通信的信任状态可能在哪改变(where the trust state of communications between components can change)
  * 在哪点会引入威胁，在哪点更容易受到攻击

# 2-识别威胁(STRIDE)：What can go wrong

- **给出一个简单的图(添加信任边界），我们就可以开始思考可能出错的地方**。例子。•	Given a simple diagram, we can start thinking about what can go wrong. Example:
  - ![](/static/2022-04-17-16-40-26.png)
  - 你怎么知道网络浏览器是由你所期望的人在使用？•	How do you know that the web browser is being used by the person you expect?
    - 欺骗 Spoofing
  - 如果有人修改了数据库中的数据会发生什么？•	What happens if someone modifies data in the database?
    - 篡改 tampering
  - 信息在没有加密的情况下从一个盒子转移到另一个盒子，这样做可以吗？•	Is it okay for information to move from one box to the next without encryption?
    - 信息泄露 information disclosure

你可以**用STRIDE方法来识别像这样的威胁**。•	You can identify threats like these using the STRIDE approach.

* 实际取决于经验之谈，，但STRIDE提供了一种结构化方式来进行可能的考虑，理解什么方面可能会出错
* 使用STRIDE走完图中的每个部分。•	Use STRIDE to walk through each part of the diagram:
  * **欺骗**（Spoofing）
    * - 欺骗：假装是你不是的东西或人。•	Spoofing: pretending to be something or someone you’re not.
    * 钓鱼邮件等
  * **篡改**（Tampering）：也就是资料修改
    *  篡改：修改你不应该修改的东西。可以包括电线上的数据包、磁盘或内存中的比特。•	Tampering: modifying something you’re not supposed to. Can include packets on the wire, bits in disk or in memory.
    *  篡改bit, disk数据，，或中间人攻击等
  * **否认**（Repudiation）
    * 声称你没有做某事（不管你是否做过）。 Repudiation: claiming you didn’t do something (regardless of whether or not you did)
    * 无法否认资料是由他所作，或是无法否认相关契约的合法性[1]。此用语也用在法律上，多半会出现在某签名的真实性被质疑的情境。
  * **资讯泄露**（Information disclosure），可能是隐私泄露或是资料外泄
    * 将信息暴露给未经授权或不应该看到它的人。Information Disclosure: exposing information to people who are not authorized or supposed to see it.
    * 除了软件方面的信息泄露，，职工方面也要确保有合适的政策指导员工理解不应该泄露信息
  * **阻断服务攻击**（Denial of service）
    * 拒绝服务：旨在阻止系统提供服务的攻击，方法是使其崩溃、变得缓慢或充斥其存储。 Denial of service: attacks designed to prevent a system from providing services, by crashing it, making it slow or flooding its storage.
  * **特权提升**（Elevation of privilege）
    * 当一个程序或用户在技术上能够做他们不应该做的事情。Elevation of Privilege: when a program or user is technically able to do things that they are not supposed to do.
    * BLP，Biba模型尝试将各个层级所能访问的对象隔离(sandbox)，防止未经授权的篡改
* 上面是一系列可以考虑的方面 （针对各个实体 & 实体间关系
  * 比如，浏览器&server之间，存在什么欺骗事件，什么可能会被篡改，浏览器可能绕过安全检查等

# TIPS to Identify Threats

- **如果你不确定从哪里开始，就从驱动活动的外部实体或事件开始**。•	If you’re not sure where to start, start with the external entities or events which drive activity.
- **千万不要因为一个威胁不是你目前正在寻找的而忽视它**......•	Never ignore a threat because it’s not what you’re looking for currently…
- **专注于可行的威胁**。•	Focus on threats that are feasible.
  - 没有足够时间下确保所有威胁都能缓解/解决，这点尤为重要。威胁的优先级，选取最重要的，剩下的可以进行风险评估来决定&证明决策（risk assessment

# 3-解决威胁：What are we going to do about it

你可以采取四种类型的行动来对付威胁。•	There are four types of action you can take against a threat:

1.	**减轻它**。Mitigate it.

* **缓解威胁带来的影响，或者减少威胁再次出现的可能性**
* **做一些事情来使威胁更难被利用**。•	Doing things to make it harder to take advantage of a threat.
  - 要求用密码来控制谁可以登录，可以减轻欺骗的威胁。•	Requiring passwords to control who can log in mitigates the threat of spoofing.
  - 增加强制执行复杂性或过期的密码控制，使得密码被猜中或被盗后可用的可能性降低。•	Adding password controls that enforce complexity or expiration makes it less likely that a password will be guessed or usable if stolen.
- <font color="deeppink">最常用的方法通常是减轻威胁。对客户来说，缓解通常是最简单和最好的方法。
</font> The ‘go-to’ approach is typically mitigating threats. Mitigation is generally the easiest and best approach for customers.

2.	消除它。Eliminate it.

* 注意区分 缓解&消除威胁。
  * 缓解并不会消除威胁
  * 有时候会发现完全消除一个威胁的成本比缓解的要大得多。而且有时候完全消除威胁是不可能的
    * 比如用消息摘要验证文件完整性，，有生日攻击的风险 （两个不同文件有相同消息摘要），，完全去除是不可能的，除非不存在这样的通信验证 事件
* 通常消除一个威胁需要 移除或修改软件系统的特性
  * 如果只是缓解某些风险，仍然可以保留软件系统的某些特性
* **这几乎都是通过消除功能来实现的**。•	This is almost always achieved by eliminating features:
  - 如果你有一个威胁，即有人会通过访问/admin/<URL>访问网站的管理功能 •	If you have a threat that someone will access the admin function of a website by visiting the /admin/<URL>
  - 这种威胁只能通过取消接口来消除。例如，通过远离HTTP，通过命令行处理管理。•	This threat can only be eliminated by removing the interface. For instance, by moving away from HTTP and handling administration through the command line.
  - 仅有很少情况下，客户能接受牺牲某些功能，不过可能需要提议另外的替代功能

3.	转移它。Transfer it.

* 信任转交给**外部实体**来解决威胁
  * 如外部云服务，解决DOS攻击等
* **这是指让别人或其他东西来处理风险**。•	This is about letting someone or something else handle the risk.
  - 你可以把认证威胁传递给操作系统。•	You could pass authentication threats to the operating system.
  - 你可以把信任边界的执行交给一个防火墙产品。•	You could pass trust boundary enforcement to a firewall product.
  - 有一个**固有的风险**，即你**相信其他实体能充分处理威胁**。•	There is an inherit risk that you are trusting that other entity to adequately handle the threat.
  - <font color="deeppink">因为投入大量信任，一般都需要在合约下进行</font>

4.	接受它。Accept it.

* 识别威胁后，发现成本（时间，金钱）不足以解决/缓解
  * 所以文档记录可能的问题，，与stakeholders进行沟通 & 解释决策是很重要的
* 这是应对威胁的最终办法。•	This is the final approach to addressing threats.
  - 对于大多数组织来说，搜查每一个进入大楼的人对确保安全来说太麻烦了。•	For most organizations, searching everyone who enters the building is too much of a hassle to ensure security.
  - 防止有人在硬件中插入后门的成本很高(尤其是小型企业）。•	The cost of preventing someone from inserting a backdoor in hardware is expensive.
    - 所以一般直接信任制造商制造的芯片
  * 在这里，你可能想接受风险，因为它更容易。(但这要**经过充分的风险评估**) Here, you might want to accept the risk, because it is easier. (but this is subject to adequate risk assessment)
* 总而言之，评估前三种方式的可行度，，如果都不可行就接受威胁，并且文档整理 & 向stakeholders阐述为什么接受

---

- **第三步是问自己如何处理这些威胁**。•	Step 3 involves asking yourself how to handle the threats.

- **处理威胁的典型方法是减轻它们**。•	The typical approach to take in addressing threats is to mitigate them.
  - 当你减轻一个威胁时，你不是在消除它。相反，你是在**减少它的影响或降低威胁发生的可能性**。•	When you mitigate a threat, you are not removing it. Rather you are reducing it’s impact or reducing the likelihood of the threat occurring.
- 你也可以**消除一个威胁，转移它或接受它**。•	You can also eliminate a threat, transfer it or accept it.
  - **转移威胁的前提是其他一些实体可以解决这个威胁**。但就像异常处理一样，某个地方的某个实体将不得不处理它。•	Transferring the threat assumes that some other entity can address the threat. But like exception handling, some entity somewhere will have to deal with it.
  - **有时你将不得不接受风险，因为它要么不可能被消除，要么处理起来成本太高**。•	Sometimes you will have to accept risk, as it is either impossible to remove, or too costly to address.

# 例子-解决威胁

## 解决欺骗威胁: Addressing Spoofing Threats

![](/static/2022-04-17-21-04-50.png)
![](/static/2022-04-17-21-05-19.png)

## 解决篡改威胁：Addressing Tampering

![](/static/2022-04-17-21-07-22.png)

## 解决否认：Addressing Repudiation

![](/static/2022-04-17-21-08-27.png)

## 解决信息泄露：Addressing Informationn Disclosure

![](/static/2022-04-17-21-10-13.png)

## 解决拒绝服务：Addressing Denial of Service

![](/static/2022-04-17-21-11-25.png)

* 比如大流量服务，，采取灵活负载均衡策略

## 解决特权提升：Addressing Elevation of Privilege

![](/static/2022-04-17-21-16-32.png)

# 4-验证结果：Did we do a good job

1.	看看**图，它是否很好地代表了这个系统**？1.	Look at the diagram, does it represent the system well?

* 尽可能具体的建模系统

2.	看一下**威胁列表**，你是否发现**图中每个节点至少有5个威胁**？2.	Look at the list of threats, did you find at least 5 threats per node in the diagram?

* 尽可能识别多的威胁，，没有威胁不代表不存在威胁

3.	你**是否为每个威胁提供文档**？3.	Did you file a report per threat?

* 阐述Bug，威胁本身，分配合适的team解决，，优先化，，或是向stakeholder解释等

# Summary

- **当你发现违反你的需求的威胁，并且不能被缓解时，一般来说，调整你的需求是有意义的。有时可以从操作上减轻威胁，或者把决定权交给使用系统的人**。•	When you find threats that violate your requirements and cannot be mitigated, it generally makes sense to adjust your requirements. Sometimes it’s possible to either mitigate the threat operationally, or defer a decision to the person using the system.
- 还有许多其他的威胁建模技术，我们只谈了两种最流行的方法。•	There are many other threat modelling techniques, we have only touched on two of the most popular approaches.
- 其他一些威胁建模方法包括。•	Some other threat modelling approaches include:
  - PASTA（攻击模拟和威胁分析的过程） •	PASTA (Process for Attack Simulation and Threat Analysis)
  - CSVSS（通用漏洞计分系统）•	CSVSS (Common Vulnerability Scoring System)
  - HtMM (混合威胁建模法)•	HtMM (Hybrid Threat Modelling Method)

# ===========

# 攻击树历史&概念：History - Attack Trees

![](/static/2022-04-17-21-35-46.png)

树形结构表示威胁 - **可视化和组织与一个给定模型相关的威胁**。Visualize and organize threats associated with a given model.

* 威胁高级表示，然后分解威胁
  - 高层次分析的表示，不关系具体涉及的方法，工具，框架。所以无相关背景的人也能理解，有效沟通
* 其他人(hight level)通过什么步骤能达到威胁目标(root node)
  * 其他节点 - 潜在issues
* **以树状结构表示对一个系统的攻击，目标是根节点，实现该目标的不同方式是叶节点**。 A representation of attacks against a system in a tree structure, with the goal as the root node and different ways of achieving that goal as leaf nodes.
* STRIDE的一个替代方案（补充）。•	An alternative to STRIDE.
  - **你可以使用攻击树作为寻找威胁的方式，或者作为组织威胁的方式**。•	You can use attack trees as a way to find threats, or as a way to organize threats.
  - **攻击树作为四步框架中的威胁枚举的构建块，效果很好**。•	Attack trees work well as a building block for threat enumeration in the four- step framework.
    * 比如一起使用，攻击树来识别路径&各个节点-**并且与stakeholders进行阐述的可视化表示**，，，STRIDE来考虑特定节点level是否还能继续分解
* **这个想法是要像软件系统的攻击者或防御者那样思考**。•	The idea is to think like either an attacker or defender of a software system.
  - 你要从提议者的角度来建立攻击树的模型。•	You want to model the attack tree from the perspective of the proponent.

# 攻击树例子

![](/static/2022-04-17-21-42-28.png)

- **顶层节点（或根）代表攻击者的最终目标**。•	Top level node (or root) represents the ultimate goal of an attacker.
- **节点（或叶子）代表需要实现的子目标（共同或独立），以达到最高级别的目标**。•	The nodes (or leaves) represent sub goals that need to be achieved (together or independently) to arrive at the top level goal.
- 目标 - 伪造账号
  - 分解成subnode & 不同路径来实现这个目标 （独立 or 共同

# 构建攻击树：Working with Attack Trees

有三种方法可以使用攻击树来列举威胁。•	There are three ways you can use attack trees to enumerate threats:

- **你可以使用别人创建的攻击树来帮助你找到威胁**。•	You can use an attack tree someone else created to help you find threats.
  - 泛化的威胁，，可以利用到自己的系统上。
  - 或者付费咨询提供一些指导，如何应用到自己的系统中
- **你可以创建一棵树来帮助你思考你正在进行的项目中的威胁**。•	You can create a tree to hep you think through threats for a project you’re working on.
- 你可以在**创建树的时候，希望别人也能使用它们。(创建新的树供一般人使用是具有挑战性的，即使对专家来说也是如此**) •	You can create trees with the intent that others will use them. (Creating new trees for general use is challenging, even for experts)

# 使用攻击树识别威胁：Using Attack Trees to find Threats

**如果你有一个与你正在建立的系统相关的攻击树，你可以用它来寻找威胁**。•	If you have an attack tree that is relevant to the system you’re building, you can use it to find threats.

- **用图表示系统**。•	Represent the system in a diagram.
- **使用攻击树来分析这个模型**。•	Use an attack tree to analyse the model:
  - 攻击激发的任务是**迭代树中的每个节点，并考虑该问题（或该问题的变体）是否影响你的系统**。•	The attack elicitation task is to iterate over each node in the tree and consider if that issue (or a variant of the issue) impacts your system.
    - **考虑不同路径，路径可行度或成本，什么威胁更可能发生**
    - 针对更可能的，，在成本有限的情况下考虑应该要采取的缓解措施，优先级

---

![](/static/2022-04-17-22-13-35.png)

* 右边业务逻辑，左边攻击树
* 注意ATM节点下面两个子节点是和取的（conjunctive refinement），，对于攻击来说，两个都是必要的
* 绿方框是缓解措施, dot line链接对应的威胁
  * 注意**缓解措施也可以进一步分解，，并且也可能存在威胁**
    * 比如二次认证可以通过浏览器以某种方式绕过

# 攻击-防御场景：Attack-Defence Scenarios

- 攻击树被用来模拟攻击-防御场景。•	Attack trees are used to model attack-defence scenarios.
- 攻击-防御场景是两个玩家之间的游戏。•	The attack-defence scenario is a game between two players:

**下面两个概念取决于攻击树的根**

- 正方（表示为p）•	The proponent (denoted as p)
- 反方（表示为o）•	The opponent (denoted as o)
- **攻击树的根代表正方的主要目标**。The root of an attack tree represents the main goal of the proponent.
  - **当根是攻击节点时，正方是攻击者，反方是防御者（开发者团队等，阻止攻击者达到最终目标**）。When the root is an attack node, the proponent is an attacker and the opponent is a defender.
  - **反之，当根是防御节点时，正方是防御者，反方是攻击者**。Conversely, when the root is a defence node, the proponent is a defender and the opponent is an attacker.
- **攻击节点用圆圈表**示。Attack nodes are represented with circles.
- **防御节点用矩形表示**。Defence nodes are represented with rectangles.

攻击树细化refinements

细化可以是共轭的（AND聚合）或不共轭的（OR选择）。Refinements can be conjunctive (AND aggregation) or disjunctive (OR choice)

- **细化关系由节点之间的实线表示**。Refinement relations are indicated by solid edges between nodes.
- **反措施是由虚线表示的**。Countermeasures are indicated by dotted edges.
- **一个节点的共轭细化由连接该节点和其同等类型的子节点的所有边上的弧表示**。A conjunctive refinement of a node is represented by an arc over all edges connecting the node and its children of equal type.

# 建立新攻击树：Creating new attack trees

创建攻击树的基本步骤如下。•	The basic steps to create an attack tree are as follows:

1.	**决定一个表示**。1.Decide on a representation.

* 图 or 文本表示 or其他数据结构（输入算法来计算成本
  * ![](/static/2022-04-17-22-43-01.png)
- 两种类型的树。
  - ![](/static/2022-04-17-22-45-30.png)
  - AND树（聚合）。一个节点的状态取决于它下面所有的节点都是真的。AND trees (aggregation): The state of a node depends on all of the nodes below it being true.
  - 或树（选择）。如果一个节点的任何一个子节点都是真的，那么这个节点就是真的。Or trees (choice): A node is true if any of its subnodes are true.

2.	**创建一个根节点**。2.Create a root node.

* **如果根节点是一个对策/缓解行动**... If the root node is a countermeasure / mitigation action…
  * 防御树例子![](/static/2022-04-17-22-47-27.png)
  - 那么子节点就用来确定什么地方会出问题。Then the subnodes are used to identify what can go wrong.
  - 分解缓解行动，确定结果如何受到威胁。Decompose the mitigation action and identify how the results can be threatened.
- 如果根节点是攻击者的一个目标。•	If the root node is a goal of the attacker:
  - ![](/static/2022-04-17-23-06-15.png)
  - 那么我们就考虑实现该目标的方法。•	Then we consider ways to achieve that goal.
  - 每一种实现目标的替代方法都应该被画成一个子节点。•	Each alternative way to achieve the goal should be drawn as a subnode.

3.	**创建子节点**。3.Create subnodes.

* **你通常通过头脑风暴以某种结构化的方式创建子节点**。•	You typically create subnodes by brainstorming in some structured manner.
  * 可以采取STRIDE步骤来考虑，什么可能被temparing, 泄露, 欺骗等
- 子节点和父节点之间的关系可以是AND或OR。•	The relation between subnodes and parent node can be AND or OR.
- 一些可能的一级子节点的结构包括。•	Some possible structures for first-level subnodes include:
  - ![](/static/2022-04-17-23-09-15.png)

4.	**考虑完备性**。4.Consider completeness.

- **可以通过迭代节点来检查攻击树的质量，寻找达到目标的额外方法。使用STRIDE或攻击库可能会有帮助**。•	An attack tree can be checked for quality by iterating over the node, looking for additional ways to reach the goal. It may be helpful to use STRIDE or attack libraries.

5.	**修剪树**。5.Prune the tree.

- **在这一步中，浏览树中的每个节点，考虑每个子节点的行动是否被阻止或重复**。•	In this step, go through each node in the tree and consider whether the action in each subnode is prevented or duplicative.

6.	**检查表述**。6.Check the presentation.

- 树可以用两种方式表示。•	Trees can be represented in two ways:
  - **作为一个没有任何技术结构的自由形式（人类可以查看）的模型**。•	As a free form (human viewable) model without any technical structure.
    - 比如给Stakeholder解释，，
    - 攻击树可以用图形绘制，也可以用纲要形式显示。•	Attack trees can be drawn graphically or shown in outline form.
    - **应注意确保图形实际上是信息丰富和有用的**。•	Care should be taken to ensure that the graphics are actually information rich and useful.
    - **大纲式的表述比图形式的表述更容易创建，但它们往往不太吸引人的注意**。•	Outline representations are easier to create than graphical representations, but they tend to be less attention-grabbing.
    - **如果你在使用别人的树，一定要理解他们的意图。如果你正在创建一次，要确保你清楚你的意图，并清楚地传达你的意图**。•	If you are using someone else's tree, be sure to understand their intent. If you are creating once, be sure you are clear on your intent and communicate your intent clearly.
  - **作为具有变量类型和/或元数据的结构化表示，以方便程序化分析**。•	As a structured representation with variable types and/or metadata to facilitate programmatic analysis.

# 攻击树风险分析-结构化表示：Structured Representation

- 树作为一种数据结构。•	Tree as a data structure.
- **有可能将风险分析逻辑应用到树上，并反过来应用到你正在建模的系统上**。•	Makes is possible to apply risk analysis logic to the tree and in turn, the system you’re modelling.
- **例如，建模者可以给每个节点增加成本，然后评估一个拥有特定预算的攻击者可以执行哪些攻击**。•	For example, the modeller can add costs to each node, and then assess what attacks an attacker with a given budget can execute.

# 安全设计决策：Secured Design Decisions

- 良好的安全选择要求我们知道。•	Good security choices require that we know:
- 我们将如何被攻击。•	How we will be attacked.
- 攻击的影响。•	The impact of the attack.
- 如何建立具有成本效益的反措施。•	How to build cost effective countermeasures.
- **需要记录并证明决定的合理性**。•	Need to document and justify decisions.

# 风险分析：Risk Analysis

- **风险=（事件的概率）x（事件影响**）。•	Risk = (probability of incident) x (incident impact)
  - `Probability of Incident = Vulnerability x Threat x Motivation`
    - 成功的攻击是可能的，如果。•	Successful attacks are possible if:
      - 你有弱点【漏洞，bug。•	You have vulnerabilities.
      - 你的对手有能力【某技术的理解，绕过某些机制。•	You adversary has capabilities.
      - 你的对手相信他们会得到一些东西。•	Your adversary believes they will gain something.
    - **vulnerability** - 漏洞 - 攻击的 "难度 "有多大？Vulnerabilities – how “difficult” is the attack?
      - **这玩意是开发者能缓解的**
      - 但**如何客观地衡量难度**？为了回答这个问题，选择专家们同意的因素。But how do you objectively measure difficulty? To answer this, choose factors that experts agree on:
        - 攻击的成本。 Cost of attack.
        - 攻击者的技术理解。Technical understanding of attacker.
        - 需要的时间。Time required.
        - 其他资源。Other resources.
      - **Threat & Motivation** 基于能力的建模。Capabilities based modelling: 【开发者没办法控制
        - 能力。对手是否能够攻击（如果他们不能，那么他们就不会）。Capabilities: Whether the adversary can attack (if they can’t then they won’t)
        - 如果对手想而且他们能做到，那么他们就会。If an adversary wants to and they can, then they will.
        -  动机：如果你只考察能力，那么你会得到保守的答案，（即使他们能，他们也可能不想）。Motivation: if you examine only capabilities, then you get conservative answers, (even they can, they might not want to)
        - There is nothing wrong with being conservative however.
- **基于攻击树的分析方法可用于对敌对威胁（对手的攻击）进行建模** •	Attack tree based analytic methods can be used to model hostile threats (attacks by adversaries)

在处理敌对的风险时，要考虑以下几点。•	When dealing with hostile risk, consider the following:

- 系统中的漏洞不是常量。•	Vulnerabilities in systems are NOT constants.
- 敌方的能力不是恒定的。•	Capabilities of the adversary are NOT constant.
- 敌方的动机会即时改变。•	Motivators for adversary changes instantaneously.
- 对手可以随时攻击你的系统。•	Adversaries can attack your system anytime.

# 使用攻击树进行风险分析：Risk analysis using attack trees

- 首先，需要将**风险属性**添加到攻击树中。•	First, risk attributes need to be added to the attack trees.

1. 与以下两者之一有关的属性。1.	The attributes related to either:

- 脆弱性。•	Vulnerability.
- 威胁。•	Threat.
- 动机或...•	Motivation or…
- 影响。•	Impact.

2. 生成并排列攻击方案。2.	Generate and rank attack scenarios.

# 安全政策文档：Security Policy Document

精确的文件，详细说明。•	Precise document that details:

- 被保护的资产。•	Assets being protected.
  - 用户名，密码，credentials
- 用户角色可以和不能做什么。•	What user roles can and cannot do.
- 谁负责执行。•	Who is responsible for enforcement.
  - 什么team负责什么威胁等

它可以包含。•	It can contain:

- **安全策略模型，是对系统应该具有的保护属性的一页总结**。•	A security policy model, a one-page summary of the protection properties that systems should have.
- **保护配置文件，实现安全目标的方式**。•	Protection profiles, a manner of achieving a security target.
- **几个安全目标，实现的详细保护机制**。•	Several security targets, the detailed protection mechanisms of this implementation.

# 保护配置文件&安全目标：Profiles and targets

有一套由FIPS制定的**标准配置文件**•	There are set of standard profiles developed by FIPS

- 它们可以**指导安全目标的开发**。•	They can guide the development of security targets.

**保护配置文件**•	Protection Profile

- 一**套独立于实施的安全要求，用于满足特定消费者需求的一类产品**。•	An implementation independent set of security requirements for a category of products that meets specific consumer needs.

**安全目标**•	Security Target

- **一套安全需求**，用作**评估已确定产品的基础**。•	A set of security requirements to be used as the basis for evaluation of an identified product.
- 保护配置文件的一种**更具体**的形式。•	A more specific form of protection profile.
  - 可以参考一个或多个保护配置文件。•	May refer to one or more protection profile.
- 可以单独开发。•	Can be developed in isolation.

# 安全功能性需求:Security FUnctional Requirements

![](/static/2022-04-17-23-37-35.png)

* 利用来给其他个体提供理解安全规章的文档

# 安全机制：Security Mechanisms

- **有几种方式可以强制执行安全策略**。•	There are several ways in which the security policy can be enforced.
- 保证需求详细说明了证明安全的方式。•	Assurance requirements detail the ways in which security is demonstrated.
- 也有不同的方式来评估这些要求。•	There are also different ways of evaluating these requirements.
- 下面是FIPS标准。•	Here are the FIPS standards.

# 安全保障需求：Assurance Requirements

- **为确保符合声称的安全功能而采取的措施**。•	Measures taken to ensure compliance with the claimed security functionality.

![](/static/2022-04-17-23-41-23.png)

# 评估保障级：evaluation assuarance level

Depth and rigor of the evaluation.

![](/static/2022-04-17-23-44-59.png)

> IT 产品或系统的评估保证级别（EAL1到EAL7 ）是在完成通用标准安全评估后分配的数字等级，该评估是自 1999 年起生效的国际标准。不断提高的保证级别反映了必须满足的附加保证要求获得通用标准认证。更高级别的目的是为系统的主要安全功能可靠实施提供更高的信心。**EAL 级别并不衡量系统本身的安全性，它只是说明系统在哪个级别进行了测试**。
> **为了实现特定的 EAL，计算机系统必须满足特定的保证要求。这些要求中的大多数涉及设计文档、设计分析、功能测试或渗透测**试。与较低的 EAL 相比，较高的 EAL 涉及更详细的文档、分析和测试。与获得较低的 EAL 认证相比，获得更高的 EAL 认证通常需要更多的金钱和时间。分配给认证系统的 EAL 编号表明该系统完成了该级别的所有要求。

# Summary

- **攻击树作为利益相关者的可视化工具和作为计算分析的数据结构是非常有用的**。•	Attack trees are useful as a visualiser for stakeholders and as a data structure for computational analysis.
- **可以与STRIDE结合以丰富STRIDE，反之亦然**。•	Can be combined with STRIDE to enrich STRIDE or vice versa.
- 这两种方法都需要对该领域的理解才能有效。•	Both approaches require an understanding of the domain to be effective.
  - 经常被用于红队和蓝队的攻击/防御场景中。•	Used often in red team and blue team attack/defence scenarios.
- 由安全顾问使用•	Used by security consultants
  - 他们向你出售预先准备好的攻击树，然后根据你的具体使用情况进行定制。•	They sell you pre-prepared attack trees that are then customised to your specific usage context.
- **它最终是一个关于可能出错的结构化思想实验**。•	Its ultimately a structured thought experiment on what can go wrong.

# ===========

# OAuth Case Study

如何应用STRIDE于认证模型

- 能够描述身份委托模型。•	Be able to describe the identity delegation model.
- 理解OAuth的工作原理。•	Understand how OAuth works.
- 理解OAuth 2.0中授权流的类型。•	Understand types of authorization flows in OAuth 2.0.
- 解读一个关于可能面临的威胁的案例。•	Unpack a case study on possible threats faced.

# 身份委托：Identity-Delegation

身份授权是**企业安全**的关键。•	Identity delegation is key to enterprise security.

- **与想代表你访问你拥有的资源的第三方共享凭证（用户名、密码）是一种反模式**。•	Sharing credentials (usernames, passwords) with a third party who wants to access a resource you own on your behalf is an anti-pattern.
- 在黑暗时代的社交网络服务中普遍使用...•	Commonly used in social network services in the dark ages…
  - (2006年之前，真的)
  - 必须把证书交给SNS，以便它执行某种功能。(典型的是联系搜刮达到企业的扩充)•	Would have to hand over credentials to SNS for it to perform some kind of function. (typically contact scraping)
    - **非常不安全，因为在现代trust place存在问题**。所以就算交给第三方也必须以一种安全方式（**身份委托，使用令牌机制，而不是用户认证信息**
    - 用户**无法控制第三方使用交出去的credentials做什么** 【<font color="red">用委托模型解决这一问题</font>

第三方如果想代表你访问你拥有的资源，如何才能在不共享凭证的情况下做到这一点？•	How can a third party, who wants to access a resource you own on your behalf, do so without the sharing of credentials?

- 两种授权模型。•	Two delegation models:
  - 直接委托。•	Direct delegation.
  - 中介委托。•	Brokered delegation. (间接委托)
- <font color="red">除了解决信任问题和令牌认证灵活性等，，唯一缺陷就是第三方需要与认证服务器有合同，能够识别使用令牌，，如果不支持这一特性就没办法</font>

# 身份委托模型：Identity-Delegation Model

任何身份-委托模型都有四个主要角色。•	Any identity-delegation model has four main roles:

- **委托人拥有资源，也被称为【资源所有者**】。•	The **delegator** owns the resource and is also known as the resource owner.
  - 资源所有者通常是终端用户，他创建或以其他方式拥有一些被客户端访问的数据。•	A resource owner is typically the end user, who creates or otherwise possesses some data which is being accessed by the client.
- 被委托人希望代表委托人访问一项服务。**委托人将一组有限的权限委托给【被委托人】，以访问该服务，也称为【客户端**】。•	The **delegate** wants to access a service on behalf of the delegator. The delegator delegates a limited set of privileges to the delegate to access the service, also known as the client.
  - 客户端通常是一个希望访问某些数据的第三方应用程序。•	A client is often a third-party application looking to access some data.
- **资源监护人**通常是（但不总是）一些外部服务，负责一些数据的策划或永久存储。•	A resource custodian is typically (but not always) some external service that is responsible for the curation or permanent storage of some data.
  - 可能是数据，这取决于他们如何管理自己的信息。•	Could be the data, depending on how they manage their information.
- **资源服务器--提供访问所需信息的服务提供者**。•	The **resource server** – the service provider that provides access to the information requested.
  - 资源服务器是一些提供一些数据的服务。•	A resource server is some service that provides some data. 
  - 相当多的时候，这些被认为是托管人/保管人，很多模型不做区分（取决于模型的复杂性）。Quite often these are considered custodians/curators and a lot of models do not make the distinction (depending on the complexity of the model)
- **授权服务器是验证资源所有者的凭证并执行授权检查的服务。这通常与资源服务器相同**。•	The **authorization server** is the service that verifies the resource owner’s credentials and performs the authorization checks. This is often the same as the resource server.
  - 确认client是否有权限访问server服务
  - 授权服务器是一个守门人，用于验证用户的凭证，以向客户提供访问令牌。•	An authorization server is a gatekeeper that is used to authenticate user credentials to provide an access token to the client.
    - 通常知道资源所有者。•	Typically knows the resource owner.

# 直接委托：Direct Delegation

最简单的委托模型

- **委托人直接将他或她的权限的一个子集委托给一个受委托人（客户**）。The delegator directly delegates a subset of his or her privileges to a delegate (client).

---

- 例子。Flickr是一个基于云的服务，用于存储和分享照片。存储在Flickr中的照片是资源，而Flickr是资源服务器或服务提供者。假设你有一个Flickr帐户；你是你帐户下的照片的资源所有者。你也有一个Snapfish帐户。Snapfish是一个基于网络的照片共享和照片打印服务，它是由HP公司拥有的。•	Example: Flickr is a cloud based service for storing and sharing photos. Photos stored in Flickr are resources, and Flickr is the resource server or the service provider. Say you have a Flickr account; you’re the resource owner of the photos under your account. You also have a Snapfish account. Snapfish is a web based photo sharing and photo printing service that is owned by HP.

问题--你怎么能从SnapFish打印你的Flickr照片？Question - How can you print your Flickr photos from SnapFish?

* Filckr资源服务器, service provider
* snapfish client
* 通过直接委托实现
  * ![](/static/2022-04-18-16-08-59.png)
  * 用户个体(委托人delegator)授权snapfish（被委托delegate），代表用户访问Filckr。【然后delegate可以进行资源的读写操作等

# 间接委托：Indirect Delegation / Brokered delegation

- 委托人首先委托给一个**中间委托人**。该中间委托人进一步委托给另一个被委托人。这也被称为经纪式委托。The delegator first delegates to an **intermediate delegate (brokered delegator**). That delegate further delegates to another delegate. This is also known as brokered delegation.

如果一个被委托的委托人可以委托别人，那么一个被委托人可以委托多少个委托人？How many delegators could a delegate delegate, if a delegated delegator could delegate delegates?

---

例如：假设你有一个LucidChart账户。LucidChart是一个基于云的设计工具，允许你绘制各种图表。它还与Google Drive集成。Example: let’s say you have a LucidChart account. LucidChart is a cloud-based design tool that allows you to draw a variety of diagrams. It also integrates with Google Drive.

- 你如何从你的LucidChart账户发布完成的图表到你的Google Drive？How can you publish completed diagrams from your LucidChart account to your Google Drive?
  - 直接委托
    - user - delegator
    - lucidChart - client(delegate)
    - google drive - resource server
    - ![](/static/2022-04-18-16-33-36.png)
- 如何从Snapfish打印存储在Google Drive上的LucidChart图表？How can you print a LucidChart diagram that is stored on Google Drive from Snapfish?
- 间接委托
  - ![](/static/2022-04-18-17-18-45.png)
  - 用户不会直接授权snapfish，而是走中间委托授权

# OAuth

你如何**授权第三方代表你进行操作**而**不向他们暴露你的凭证（因为信任问题，，不能暴露凭证**？•	How do you authorize third parties to carry out actions on your behalf without exposing your credentials to them?

- **OAuth通过提供一种替代机制来解决这个问题，通过这种机制，我们可以授权特定的行动，并且只授权这些行动，而不提供无限制的或永久性的访问**。•	OAuth addresses this by providing an alternative mechanism through which we can authorize specific actions, and only those actions without providing unrestricted or permanent access.
  - 一个协议，它允许你代表你的应用程序访问一些资源，而不向他们提供你的证书。Open authentication is a protocol that allows you to provide an application access to some resource on your behalf without providing them with your credentials.
  - <font color="deeppink">这作为一种安全机制是非常可取的，因为向不需要你的证书的应用程序提供你的证书通常是一个非常糟糕的主意。•</font> This is very much preferred as a security mechanism as providing your credentials to applications that do not need them is generally a very bad idea.
- OAuth是一个用于**建立跨服务的身份管理标准的协议，为共享用户名和密码提供了一个替代方案**。•	OAuth, is a protocol for establishing identity management standards across services, providing an alternative to sharing usernames and passwords.
- token通过认证服务器生成

# 访问令牌 & 认证码：Accesss Token & Authentication code

**访问令牌：授权服务器创建的一块数据，让客户请求访问资源服务器。这是【客户端将使用的授权凭证】，以取代资源所有者自己的凭证**。•	Access token: a piece of data the authorization server creates that lets the client request access form the resource server. This is the authorization credential the client will use in place of the resource owner’s own credentials.

- 访问令牌是提供给应用程序的，允许它们在授权/资源服务器上识别和验证自己，而不需要你提供你的用户名和密码。•	Access tokens are provided to applications that allow them to identify and authenticate themselves with an authorization/resource server without you providing them your username and password.
- 这**允许不同的服务代表终端用户进行通信和执行功能**。•	This allows different services to communicate and perform functionality on the behalf of end users.
  - 作为对终端用户的一种方便。•	As a convenience to the end user.
- 访问令牌包含一系列数据，如：。•	Access tokens contain a series of data such as:
  - **范围**是指授予某些应用程序的**一系列权限**。•	Scope is the series of permissions that are granted to some application.
    - 这些通常是CRUD操作，而且往往是细粒度的。•	These are often CRUD operations and are often granular in nature.
    - 应用程序在特定资源上**拥有的权限**（CRUD操作）•	Permissions the application has on a specific resource (CRUD operations)
  - 权限的**到期时间**。•	The expiration of the permissions.
  - 应用程序是否有权进一步委托访问（甚至请求访问）。•	Whether the application has the right to further delegate access (or even request access)
    - 令牌可以在任何时间被revoke，，用户有更多的控制，而不用给认证凭证，且没有信任问题
    - <font color="red">给第三方，除了信任问题，，唯一阻止第三方使用凭证的方式就是修改凭证。如果只是给令牌，不需要修改凭证就能撤销令牌，使认证失效</font>

---

**认证码。授权服务器可以检查的一段数据，以验证客户的身份，通常在访问令牌分发之前**。•	Authentication code: A piece of data that the authorization server can check to verify the identity of a client, typically before access tokens are distributed.

- **客户端期望从授权服务器得到的响应类型**。•	Response type is the response type the client expects from the authorization sever.
- 授权码是一串数据，用于**唯一地识别客户的授权服务器**。•	An authorization code is a string of data that uniquely identifies a client to an authorization server.
  - OAuth1.0应用，，因为授权服务器要识别client，再提供令牌
- 用来给client生成token
  - 这通常**被传递给授权服务器以换取一个访问令牌**。•	This is often passed to the authorization server in exchange for an access token.
  - 一个访问令牌是包含应用程序的权限和时间限制的对象。•	An access token is the object that contains the permissions and time restrictions for an application.

# OAuth原理：How it works

:orange:相当于OAuth2.0的Authorization Code授权流

1. **资源所有者（用户）向客户（第三方应用）要求提供服务**。Resource owner (user) requests a service from a client.

* ![](/static/2022-04-18-17-26-49.png)
* 用户将首先**从第三方选择一个选项**。1.	The user will first select an option from the third party.
  * 比如Client上某些服务选项(LucidChart，export to Google drive)

2. **客户端将用户代理（UA）重定向到授权服务器** Client redirects user agent (UA) to the authorization server.

* ![](/static/2022-04-18-17-28-28.png)
* **客户端将生成他们所需要的权限的颗粒列表，并将浏览器重定向到认证服务器**。2.	The client will generate the granule list of permissions they need and redirect the browser to the auth server.
  * client响应用户请求，要求进行认证 （user agent重定向到认证服务器）

3. **授权服务器对资源所有者进行认证**。Authorization Server authenticates resource owner.

* ![](/static/2022-04-18-17-29-31.png)
* **认证服务器将提示用户确认他们是否希望向客户端授予权限**。3.	The auth server will prompt the user to confirm if they wish to grant permissions to the client.
  * 认证服务器响应user agent(浏览器)，然后prompt用户
    * ，google drive要求提供凭证

4. **用户接受/批准授权请求** User accepts/approves authorization request

* ![](/static/2022-04-18-17-36-24.png)
* **如果用户选择 "是"，他们会被提示进行认证**。4.	If user selects yes, they are prompted to authenticate.
  * 用户提供凭证，，请求发给认证服务器

5. **授权服务器将UA重定向到客户端，并包括授权代码**。Authorization server redirects UA back to the client, and includes the authorization code.

* ![](/static/2022-04-18-17-38-05.png)
* **在登录时，会产生一个授权代码并传递给浏览器。浏览器将此代码传递给客户端**。On login, an authorization code is generated and passed to the browser. The browser passes this code to the client.
  * 用于唯一标识client，<font color="deeppink">注意client和认证服务器之间没有直接交互</font>

6. **客户端使用授权码向授权服务器请求访问（授权）令牌** Client uses authorization code to request access (authorization) token from authorization server.

* ![](/static/2022-04-18-17-41-49.png)
* **客户端将授权代码提交给认证服务器**。The client presents the code to the auth server.

7. **授权服务器对客户进行认证，验证授权码，并返回访问令牌**。Authorization server authenticates client, validates authorization code, and returns access token.

* ![](/static/2022-04-18-17-43-36.png)
* 认证服务器检查client的认证码，如果有效就发放令牌。。之后client可以使用这个令牌来访问资源服务器
  * 认证服务器向客户端提供一个访问令牌。The auth server provides an access token to the client.

8. **客户端使用访问令牌来访问资源服务器，并代表用户执行CRUD服务**。Client uses the access token to access the resource server and performs CRUD services on users' behalf.

* ![](/static/2022-04-18-17-44-23.png)
* 客户端从现在起将向资源服务器出示该令牌以访问数据 The client from now on will present the token to the resource server to access data.
* <font color="deeppink">注意用户可以随时撤销令牌revoke token</font>


# ===========

# OAuth2.0

- OAuth的最初变体是为**基于网络的应用程序开发的**，用于委托访问资源。•	The initial variant of OAuth was developed for web-based applications to delegate access to resources.
  - 然而，传统的系统使用1.0•	However legacy systems will implement 1.0
  - 现代系统使用2.0•	Modern systems work with 2.0
    - 不是直接向后兼容的
- 该规范在OAuth 2.0中被取代。•	This specification is superseded in OAuth 2.0
  - **2.0专注于客户端开发人员的简单性，同时为网络应用、桌面应用、移动电话和物联网设备提供特定的授权流程**。•	Focuses on client developer simplicity while providing specific authorization flows for web applications, desktop applications, mobile phones, and IoT devices.
  - **该规范及其扩展正在IETF OAuth工作组内开发**。•	The specification and its extensions are being developed within the IETF OAuth Working Group.
- OAuth 2.0是一个**授权流系列**。•	OAuth 2.0 is a family of authorization flows.
  - 每个**授权流**都是一个**协议**，可以在应用程序之间进行授权。•	Each authorization flow is a protocol that can be followed for the purpose of delegation between applications.
  - **使用哪种协议取决于开发者的目标和项目的目标**。•	Which protocol to use depends on the objectives of the developers and the goals of the project.
  - <font color="red">一些授权流提供了更大的安全保证，而另一些则侧重于实施的速度（即用户体验）</font>。•	Some authorization flows offer greater security assurances whilst others focus on speed of implementation (i.e. user experience)
    - 要平衡
  * 2.0规范定义了**4种类型的授权流**。Specification defines 4 types of authorization flows:
    - **授权代码**。Authorization Code.
    - **资源所有者密码凭证** Resource Owner Password Credentials
    - **隐域模型** Implicit
    - **客户端凭证** Client Credentials

# 认证流：Authorization FLows

## 资源所有者密码凭证 Resource Owner Password Credentials

- **客户端必须是高度可信的，因为它【直接处理用户凭证**】。在大多数情况下，客户端应该是一个**第一方应用程序**。Client must be highly trusted, as it directly handles user credentials. In most cases, the client should be a first party app.
  - 凭证直接给client，，开发移动应用 & 测试委托引擎协议时有用
- ![](/static/2022-04-18-17-50-13.png)
  - 1.**用户输入凭证**。User enters credentials.
  - 2.**客户端向授权服务器传递凭证和其身份信息。 授权服务器验证这些信息并返回一个访问令牌** Client passes credentials and its identification to authorization server. Authorization server validates the information and returns an access token.
  - 3.**客户端使用访问令牌来访问资源服务器上的资源** Client uses access token to access resources on the resource server.

## 认证码：Authorization Code

相当于1.0流程

- **客户端必须能够与环境中的UA互动**。Client must be able to interact with a user agent in the environment.
- 用户不会直接把凭证给client

![](/static/2022-04-18-22-06-38.png)

1. **客户端在浏览器中打开一个新页面** Client opens a new page in the browser
2. **用户输入凭证**  User enters credentials.
3. **用户代理向授权服务器发送凭证**。User agent sends credentials to authorization server.
4. **授权服务器通过重定向返回授权代码**。Authorization server returns authorization code via redirection.
5. **客户端用授权码交换访问令牌**。Client exchanges authorization code for access token.
6. **客户端使用访问令牌来访问资源服务器**。Client uses access token to access resource server.

## 隐域模型：Implicit

- **客户端必须能够与环境中的UA互动**。Client must be able to interact with a user agent in the environment.
- 要**权衡便利性&速度 和 安全性**
  - 与授权代码流类似，只是步骤较少。•	Similar to authorization code, except there are fewer steps.
  - 这是为了速度和便于执行。•	This is for speed and ease of implementation.
  - **授权代码不会被生成并传递给客户端**。•	The authorization code is not generated and passed to the client.
    - <font color="red">所以在客户端和授权服务器之间有Implicit信任，，要确保获取到访问令牌的客户端就是实际操作的客户端【安全性牺牲，，需要考虑</font>
  - 因此，与客户端的授权比之前的流程更隐蔽。•	Therefore the authorization with the client is more implicit than the previous flow.

![](/static/2022-04-18-22-08-41.png)

1. **客户端在浏览器中打开一个新页面** Client opens a new page in the browser
2. **用户输入凭证**  User enters credentials.
3. **用户代理向授权服务器发送凭证**。User agent sends credentials to authorization server.
4. **授权服务器通过重定向返回访问令牌**。Authorization server returns **access token** via redirection.
  - 认证服务器返回访问令牌，，而不是提供认证码
  - **加速认证** （信任重定向是可信的）
5. **客户端使用访问令牌来访问资源服务器**。Client uses access token to access resource server.

## 客户端凭证：Client Credentials

- **更接近于资源所有者密码凭证流程。这个流程中的【客户使用客户ID和秘密来识别自己。他们还使用这些信息来与授权服务器交换访问令牌**】。Is closer to the **Resource Owner Password Credentials flow**. Clients in this flow use client IDs and secrets to identify themselves. They also use this info to exchange for access tokens with the authorization server.
- backend process可能利用这种flow

![](/static/2022-04-18-22-16-50.png)

1. **客户端交换ID和秘密以获得访问令牌**。Client exchanges ID and secret for an access token.
2. **客户端使用访问令牌来访问资源服务器**。Client uses access tokens to access resource server.

# Web应用安全：Security of a web based Application

**对于Oauth，有三个主要关注点需要解读**。There are three main points of concern to unpack with Oauth.

- **客户端** Client
- **认证服务器** Authentication Server
- **资源服务器** Resource Server
- **这些组件中的每一个都有自己的安全问题和解决方案，每个层次之间的三种通信类型也是如此**。 Each of these components have their own security problems and solutions as do the three types of communications between each levels.

# 攻击面

不同层次有不同意思

* low level - code
* high level - 攻击者能绕过安全机制的途径

# 客户端威胁：Threats-client

- 攻击面。HTTP请求。Attack Surface: HTTP requests.
  - **一个正常的用户会使用一个标准的浏览器通过HTTP进行通信**。A normal user will use a standard browser communicating via HTTP.
  - **攻击者可以使用HTTP编写自己的程序**。An attacker can write their own program using HTTP.
- **攻击面。可下载的信息**。•	Attack Surface: Downloadable information.
  - **任何在客户端运行的代码都可以保存在文件中并被处理**。•	Any code that runs on the client can be saved in a file and processed.
    - 威胁在于，这**可以提供对我们系统内部工作的洞察力**。•	The threat is that this can provide insight into the internal working of our system.
    - **Id号码可以用来猜测内部页面的URL和请求的结构**。Id numbers can be used to guess the URL of internal pages and the structure of requests.
  - **应对措施：通过以下方式减少攻击面的大小**。Countermeasures: reduce the size of the attack surface by:
    - **客户端代码中不应该出现业务逻辑，它应该只处理信息的呈现**。No business logic should appear in the client side code, which should only deal with the presentation of information.
    - **混淆客户端的代码**。Obfuscate the client side code.
      - 变量名函数名混淆等，使逆向工程困难
- **攻击面。缓存**•	Attack Surface: Caching
  - 浏览器可能会缓存页面或cookies，以备再次需要。•	The browser may cache pages or cookies in case they are needed again.
  - 这些信息在会话结束后可能仍留在客户机上。•	This information may remain on the client machine after the session.
  - 代理服务器也可能对网页进行缓存。•	Proxy servers may also cache the pages.
  - **对策：避免缓存**。•	Countermeasures: avoid caching.
    - 将页面过期设置为即时。后退按钮将不起作用。•	Set page expiration to immediate. The back button will not work.
    - **这将不会清除代理服务器的缓存**。我们将不得不接受这个风险。•	This will not clear proxy server caches. We will have to accept this risk.
- **攻击面：用户输入**。•	Attack Surface: user input.
  - 用户输入可能包含敏感信息，可能在传输过程中被盗。•	User input may contain sensitive information that could be stolen in transit.
  - 攻击者可能在用户输入中注入恶意代码以攻击网络服务器。•	An attacker may inject malicious code into user input to attack the web server.
  - 对策：敏感信息。•	Countermeasure: sensitive information.
    - **利用TLS或IPSec对网络流量进行加密**。•	Leverage on TLS or IPSec to encrypt network traffic.
  - 对策：注入 •	Countermeasures: injection
    - **验证字段长度并检查元字符**。•	Validate field lengths and check for metacharacters.
    - 在往返信息上使用**消息摘要来检测篡改**。•	Use message digests on round trip info to detect tampering.
- **攻击面：表单** •	Attack Surface: forms
  - 表单在发送到服务器之前会收集用户的输入。•	Forms will collect user input before sending to the server.
  - **隐藏字段可用于记录会话信息，但其值可被攻击者改变**。•	Hidden fields can be used to record session information but their values can be changed by an attacker.
  - 应对措施。•	Countermeasure:
    - 不要使用隐藏字段。•	Don’t use hidden fields.

# 认证服务器威胁：Threats-Authentication Server

- **攻击面：登录会话**。•	Attack Surface: login sessions.
  - 用户在网络服务器上进行认证。登录后在一段时间内是有效的。用户可能在登录的时候离开机器。•	Users are authenticated on the web server. The login is valid for some time afterwards. A user might leave the machine while they are logged in.
- 应对措施。•	Countermeasures:
  - **在用户不活动一段时间后，将其超时**。•	Time out a user after a period of inactivity.
  - 让用户**在任何敏感交易（如付款）之前重新认证**。•	Get the user to re-authenticate before any sensitive transaction such as making payments.
- **攻击面。非HTTP流量**•	Attack Surface: Non HTTP traffic
  - 服务器可能有几个**与我们的应用程序无关的开放端口**。可能有一个默认配置导致了这种情况。•	The server may have several open ports not associated with our application. There may be a default configuration that causes this.
  - 攻击者可以尝试用这些端口中的一个闯入。•	An attacker can try and break in with one of these ports.
  - 应对措施。•	Countermeasure:
    - **使用防火墙来阻止除HTTP以外的所有流量**。•	Use a firewall to prevent all but HTTP traffic.
- **攻击面。拒绝服务**•	Attack Surface: Denial of Service
  - 我们必须为一些**同时进行的客户提供访问**。攻击者可能利用这一点使系统不堪重负。•	We have to provide access for a number of simultaneous clients. An attacker may use this to overwhelm the system.
  - 应对措施。•	Countermeasures:
    - **与ISP/云供应商商定一个DOS计划**。•	Have a DOS plan agreed with the ISP.
- **攻击面。软件漏洞**•	Attack Surface: Software Vulnerabilities
  - 攻击者可以通过客户端的请求访问我们的服务器软件。他们将能够**找出版本号，并在网络上的表格中查找漏洞，然后他们可以手工制作攻击**。•	An attacker has access to our server software via requests from the client. They will be able to find out the version number and look up vulnerabilities in a table on the web They can then handcraft an attack.
  - 应对措施。•	Countermeasures:
    - 确保服务器软件是最新的。•	Make sue server software is up to date.
    - 使用经过认证的组件，由适当的机构签署。•	Use certified components, signed by an appropriate authority.
    - 使用信息摘要检查组件是否真实。•	Check the components are genuine using message digests.
    - 出现问题时，，train staff internally to recognize the problem
- **攻击面。网络钓鱼** •	Attack Surface: Phishing
  - 用户可能被说服登录到攻击者的网站，以为那是我们的网站。然后攻击者可以获得密码来冒充用户。•	A user may be persuaded to login to the attacker’s web site thinking it is ours. The attacker can then gain passwords to impersonate the user.
  - **应对措施**。•	Countermeasures:
    - 培训员工理解什么样的威胁可能存在 & 识别
    - **让用户提供与他们有关的信息，让他们以后能认识到**。•	Ge the user to provide information connected to them that they will recognize later.
    - 在他们每次登录时向他们展示这些信息。•	Present this information to them every time they log in.
    - 攻击者将无法获得这些信息。•	An attacker will not have access to this information.

# 资源服务器威胁：Threats-Resource Server

- **攻击面。不合法的请求**•	Attack Surface: Illegitimate Requests
  - 客户端提供的信息最终会以访问或修改数据库的请求而告终。•	Information provided by the client will eventually end up with requests to access or modify the database.
  - 任何**格式良好的请求**都会被执行。•	Any well-formed request will be executed.
  - **应对措施**。•	Countermeasures:
    - 访问控制。•	Access controls.
    - 加密的列。•	Encrypted columns.
    - 存储程序以限制创建临时的SQL查询。•	Stored procedures to limit the creation of ad-hoc SQL queries.

# 数据库服务器：Threats-Database Server

**攻击面。数据库工具可以被内部人员使用**。•	Attack Surface: Database tools can be used by an insider.

- 系统用户可以比普通用户有更大的权限进入数据库。•	System users can have greater access to the database than ordinary users.
- 这种特殊的访问对于快速整理问题是必要的。•	This special access is necessary to quickly sort out problems.
- **内部攻击者可以使用这些工具进行未经授权的访问**。•	An internal attacker can use these tools for unauthorized access.
- **应对措施**。•	Countermeasures.
  - 如果可能的话，避免使用根用户。•	Avoid root users if possible.
    - 避免root用户拥有所有权限，比如访问用户档案的私密
  - 内部人员需要认证和授权。•	Insiders need authentication and authorization.

# ===========