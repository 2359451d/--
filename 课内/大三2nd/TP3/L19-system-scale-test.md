# L19 System Scale Test

:sweat_smile: 到底讲了毛

* [L19 System Scale Test](#l19-system-scale-test)
* [非功能性需求：Testing Non-Functional Properties](#非功能性需求testing-non-functional-properties)
* [非功能性测试所需信息：Info needed for testing non-functional properties](#非功能性测试所需信息info-needed-for-testing-non-functional-properties)
* [安全关键环境：Safety Critical Environment](#安全关键环境safety-critical-environment)
* [可靠性测试：Reliability Testing](#可靠性测试reliability-testing)
* [测试系统安全性方法：Methods for testing system safety](#测试系统安全性方法methods-for-testing-system-safety)
* [敌对环境中运行系统：Systems operating in Hostile Environments](#敌对环境中运行系统systems-operating-in-hostile-environments)
* [安全测试度量标准：Security Testing Metrics](#安全测试度量标准security-testing-metrics)
* [安全测试方法：Security Testing Methods](#安全测试方法security-testing-methods)
* [性能测试：Performance Testing](#性能测试performance-testing)
* [系统测试中规模影响：Scaling Effects in System Testing](#系统测试中规模影响scaling-effects-in-system-testing)
* [规模方面：Dimensions of Scale](#规模方面dimensions-of-scale)
* [异构系统：Heterogeneous System](#异构系统heterogeneous-system)
* [社会技术系统：Social-technical system](#社会技术系统social-technical-system)
* [Systems of Systems](#systems-of-systems)
* [Getting Feedback from the wild](#getting-feedback-from-the-wild)
* [Summary](#summary)

# 非功能性需求：Testing Non-Functional Properties

> 非功能性需求描述了整个系统的紧急特性或属性，这些特性必须进行测量才能得到满足，而不是作为功能性需求提供的特性进行观察。
> 因此，**通常不能在单元测试中或通过静态分析来检查非功能性需求**。它们只能在系统实现之后才能进行测试。
> 测试的实现可能是最终的系统，也可能是原型。或者，也可以建立一个模型，从中可以近似最终系统的特性。
> 测试非功能性需求的一个优点是，它们可以作为更复杂的功能性属性的集合，但这些功能性属性过于复杂，无法单独进行测试

# 非功能性测试所需信息：Info needed for testing non-functional properties

从某种意义上说，开发非功能性需求的测试与进行科学实验是一样的。

* 测试人员对被测系统(需求)提出一个假设，然后构建一个实验来确定该假设是否满足

![](/static/2021-01-26-21-44-13.png)

* 一个完善的非功能性需求应该描述
  * 所评测系统的性质
  * 使用的度量标准
  * 满足要求时需要控制的条件
  * 为满足需求系统必须超过的行为阈值

非功能性有许多不同的类别。每一个，都可以通过几种不同的方式进行测试，并进行适当的讨论，这需要一个完整的讲座或课程。这次讲座提供了一些类别的高级概述

# 安全关键环境：Safety Critical Environment

许多系统的运行发生在对系统有潜在危险的条件下，或者**对操作或依赖系统的人有潜在危险的条件下**. 如果系统发生故障，这些危险可能来自系统运作的不利环境，或系统本身的性质。

在安全关键环境下使用的软件例子包括

![](/static/2021-01-26-21-49-09.png)

* 自动驾驶员用于运输，例如飞机
* 粒子束放射治疗设备操作中使用的设备
* 天基技术，如卫星、太空船和行星探测器
* 核电站管理系统

这些系统被称为**安全关键（系统/环境）**，

* 因为系统设计和评估过程的一个主要考虑是获得信心，**系统的操作不会对系统或其用户造成伤害**
* 如果软件是用来控制系统的行为，那么也必须保证其安全性

:orange:测试一个系统的安全性与测试案例的发展有关，这些案例表明在使用该系统的不利环境中不会造成损害

# 可靠性测试：Reliability Testing

![](/static/2021-01-26-21-53-40.png)

:orange: **系统的可靠性是指系统根据其规格表现的程度**。(测量可靠性的)指标包括 the reliability of a system is the extent to which a system performs according to its specification. Metrics include

* **需求失败的可能性** probability of failure on demand(PFD)
  * (PFD)提供了系统每次访问时的失效概率估计
* **平均失败时间** Mean time to failure
  * **平均失败时间，表示从启动到软件系统失败需要多长时间，或者失败之间的平均时间**
  * 这是一个有用的指标，系统的访问维修是困难或昂贵的，如天基系统。理想情况下，平均故障时间应大于此类系统的任务时间
* **宕机时间** down time
  * **宕机时间(例如每年) ，表示一年以上的系统停机可能损失多少时间**
  * 这对于高需求、连续使用的系统非常有用，例如大容量事务处理。例如，支付系统通常被设计为每年的停机时间少于几秒钟

# 测试系统安全性方法：Methods for testing system safety

![](/static/2021-01-26-21-57-43.png)

:orange: 测试系统安全性的几种方法

* **模型驱动开发** Model driven development
  * 模型驱动的开发允许实现软件和测试套件从平台独立的模型中自动生成
  * 测试用例来源于关于软件模型的合法和非法状态的断言 Model driven development allows implementation software and test suites to be generated automatically from platform independent models. Test cases are derived from assertions about legal and illegal states for the software model
* **净室开发** Cleanroom development
  * 净室开发，使用自动化和统计方法来选择执行的测试用例，并用于计算软件系统的 PFD（需求失败的可能性） 估计值和估计值的可信度 Cleanroom development, which uses automated and statistical methods to select test cases for execution, and is used to calculate an estimate of a software system’s PFD, and a confidence in the estimate
* **Hazard and Operability Study (HAZOPS)**
  * 危害与可操作性分析来源于化学工程及类似的活动 derived from chemical engineering and similar activities
  * 对于软件，可以使用类似 HAZOPS 的过程来识别软件组件之间的信息流。然后可以询问有关信息流的问题，For software, a HAZOPS like process can be used to identify flows of information between software components. Questions can then be asked about the flows of information,
    * 例如当信息不正确或到达得太早/晚时会发生什么 such as what happens when the information is incorrect, or arrives too early

使用正式的测试方法的重点是获得对系统可靠性的精确测量，为一个安全案例提供输入。The emphasis in the use of formal methods for testing is to gain accurate measurements as to the reliability of a system, to provide input into a safety case

* 安全案例是系统满足其安全要求的理由 Safety cases are justifications that a system meets its safety requirements
* 相比之下，**HAZOPS 的目的是探索一个或多个组件故障对信息系统其余部分的影响**  In contrast, the purpose of a HAZOPS is to explore the consequences of a failure in one or more components for the rest of an information system
* **HAZOPS 用于在系统发生故障时获得系统可靠性和安全性的保证** HAZOPS are used to gain assurance as to the reliability and safety of a system in the presence of failures

# 敌对环境中运行系统：Systems operating in Hostile Environments

![](/static/2021-01-26-22-21-52.png)

:orange:系统环境除了不利之外，还可能被认为是敌对的As well as being adverse, a system environment may also be considered hostile.

* 敌对环境被认为包含活动代理(有时称为威胁或攻击者) ，其目的是故意渗透、破坏和/或破坏已部署的系统。A hostile environment is assumed to contain active agents (sometimes called threats or attackers) whose goal is to deliberately penetrate, subvert and/or sabotage the deployed system.
* 在敌对环境中部署系统的例子包括: Examples of systems deployed in hostile environments include
  * **电子投票系统** electronic voting systems
    * 电子投票系统受到试图破坏选举政治结果的代理人的威胁 electronic voting systems threatened by agents who wish to subvert the political result of an election
  * **敌我识别系统** friend-or-foe detection systems
    * 自动化武器系统中的敌友探测系统，受到敌人的威胁，这些敌人想欺骗他们向友机开火，或者不向敌机开火 friend–or–foe detection systems on automated weapon systems, which are threatened by enemies who wish to trick them into firing on friendly aircraft, or not firing on hostile aircraft
  * **数字版权管理系统** digital rights management systems
    * 数码版权管理系统受到版权「盗版者」的威胁，这些盗版者企图制作未经授权的数码媒体复制品以供转售 digital rights management systems , threatened by copyright ‘pirates’ who wish to make unauthorised copies of digital media for re-sale
  * **网络安全系统** network security systems
    * 网络保安系统，例如受到攻击者威胁的防火墙，这些攻击者希望未经授权接达远端伺服器上易受攻击的服务 network security systems such as firewalls threatened by attackers who wish to gain unauthorised access to vulnerable services on remote servers
  * **认证系统** authentication systems
    * 攻击者威胁的认证系统希望在未获授权的情况下取用资讯资源 authentication systems threatened by attackers who wish gain unauthorised access to information resources
  * **电子现金** digital cash
    * 自动柜员机的现金分发机受到小偷的威胁，小偷想要进入它们的管理用户界面来偷钱 automated teller machine cash dispensers which are threatened by thieves who would like to gain access to their administrative user interfaces in order to steal money

:orange: 特别是**分布式系统**(或其组件) ，通常被认为是**在敌对环境中运行的**，因为系统本身并不知道一些或所有其他网络参与者 Distributed systems in particular (or their components), are often considered to operate in hostile environments, because some or all of the other network participants are not known to the system itself

# 安全测试度量标准：Security Testing Metrics

![](/static/2021-01-26-22-32-31.png)

:orange: 系统威胁主动搜索缺陷 ，系统中的缺陷可以被攻击。因此，有必要在这些情况下进行测试，以获得系统能够抵抗这种攻击的保证 System threats actively search for defects, defects in the system that can be attacked. Consequently, it is necessary in these contexts to conduct tests to gain assurance that the system is resistant to such attacks.

* 安全测试指标包括
  * **攻击者渗透系统所需的能力或知识** attacker capabilities or knowledge required to penetrate the system
  * **时间和资源渗透** time and resources to penetration
    * 渗透的时间和资源，估计敌人需要多长时间，以及需要多少资源才能渗透系统 time and resources to penetration, giving an estimate of how long it would take an adversary, and how much resource would need to be applied in order to penetrate a system.
  * **每行代码中未打补丁的漏洞** unpatched vulnerabilities per line of code
    * 这个想法来自于对物理安全进行评估，并给予时间和资源评级 The idea comes from assessments used for physical safes which are given a time and resource rating
  * **每年发出的补丁** patches issued per year
  * **每年成功的渗透率** successful penetrations per year
  * **攻击面暴露** attack surface exposure

# 安全测试方法：Security Testing Methods

![](/static/2021-01-26-22-37-54.png)

有很多可用安全测试方法

* **漏洞测试** vulnerability testing
  * **SQL 注入测试** SQL injection testing
  * **端口扫描** port scanning
  * **模糊测试** fuzz testing
    * Fuzz 测试，在这种测试中，向软件系统提供异常的或格式不正确的(fuzzed)输入，这些输入可能导致系统以意想不到的(和不安全的)方式运行 Fuzz testing, in which a software system is supplied with unusual or malformed (fuzzed) inputs that may cause the system to behave in unexpected (and insecure) ways.
    * **模糊测试是一种检查输入在传递到业务逻辑代码之前的验证程度的方法**  Fuzz testing is a way of checking the extent to which inputs are validated before they are passed on to business logic code.
    * 例如，模糊测试是检测缓冲区溢出漏洞的有效手段 Fuzz testing is an effective means of detecting buffer-overflow vulnerabilities, for example.
* **渗透测试** Penetration testing
  * 渗透测试，其中一个“红队”或“攻击队”的任务是**未经授权地访问受系统保护的资源**，这些资源与攻击者预期的资源相同  a red team</ or attack team is tasked with gaining unauthorised access to the resources protected by a system, equipped with the same resources expected of an attacker.
  * 渗透测试人员可能专注于技术上的弱点，也可能参与社会工程以获得进入一个系统的机会 Penetration testers may concentrate on technical vulnerabilities or also engage in social engineering to gain access to a system

# 性能测试：Performance Testing

![](/static/2021-01-26-22-47-31.png)

:orange: **性能测试是关于评估一个系统如何处理不同的输入率** Performance testing is concerned with assessing how a system copes with different rates of input, 可以引3种属性

* **吞吐量** throughput
  * 系统在给定时间内可以处理的事务量 the amount of transactions that the system can process in a given time
* **需求** demand
  * 系统可以处理的最大事务速率 the maximum rate of transactions that the system can process
* **响应时间** response
  * 响应事务请求所需的平均或最长时间 response time, the average or maximum length of time taken to respond to a transaction request.

:orange: 一旦选择了属性和度量，就可以对阈值执行两种类型的测试 Once a property and metric has been selected, there are two types of test that can be performed with respect to the threshold

* **限制测试** Limit testing
  * 是用来证明系统在规定的限制内运作正常 Limit testing is concerned with demonstrating that a system behaves normally within a required limit
  * 限制测试用于证明系统在规定的操作限制内按预期运行 Limit testing is used to demonstrate that a system behaves as expected within required operating limits
* **压力测试** Stress testing
  * 关心的是发现当某个特定的限制被突破时会发生什么 Stress testing is concerned with discovering what happens when a particular limit is breached
  * 相反，压力测试用于发现系统的行为如何超出指定的操作限制 Conversely, stress testing is used to discover how a system behaves beyond the operating limits specified

通常，压力测试用于证明当系统超出正常范围时，系统继续表现为{可靠或可预测的Typically, stress tests are used to demonstrate that a system continues to behaves {reliably or predictably when operating outside of normal limits

* 例如，系统对过度需求的反应可能是回复到“安全模式”，只向其客户提供有限的服务。For example, a system’s response to excessive demand may be to revert to a ‘safe mode’ offering only a limited service to its clients. During the 9/11 terrorist attacks

* 例如，在9/11恐怖袭击期间，BBC 新闻网站的用户试图获取最新信息的请求异常之高。该网站恢复到一个更简单的外观，更少的图像和视频，这需要更少的带宽来传输 for example, the BBC’s News Website experienced unusually high requests from users attempting to obtain up to date information. The site reverted to a simpler appearance with less images and video, which required less bandwidth to transmit.

另外，系统可能需要“优雅地”安全关闭，而不是完全失败 Alternatively, it may be required that the system performs a ‘graceful’ safe shutdown, rather than fail completely. Database servers,

* 例如，数据库服务器通常配备不间断电源(UPS) ，这些电源本质上是大型电池组。如果出现电源损耗，则在服务器完成数据库上任何未完成的事务并启动系统关机时使用电池电源 for example, are often equipped with Uninterruptable Power Supplies (UPS) which are essentially large battery packs. If a power loss is experienced, the battery power is used while the server completes any outstanding transactions on the database and then initiates a system shutdown

# 系统测试中规模影响：Scaling Effects in System Testing

![](/static/2021-01-26-23-03-05.png)

在关于使用行为驱动开发的验收测试的讲座中，我概述了测试的三个等级: 单元测试、集成测试和系统测试。In the lecture on acceptance testing using behaviour driven development, I outlined three scales of testing: unit testing, integration testing and system testing. In unit testing, we keep tests focused on the business logic of a single module of application code (typically a class). We isolate the test target from other dependencies through the use of test doubles, such as mocks. An additional benefit of the use of test doubles is that unit tests are kept very fast, particularly by eliminating the use of any real input/output operations

* 在**单元测试**中，我们将测试集中在单个应用程序代码模块(通常是一个类)的业务逻辑上。我们通过使用测试双精度测试(例如 mock)将测试目标与其他依赖关系隔离开来。使用测试双精度打印的另一个好处是，单元测试保持得非常快，特别是通过消除任何实际输入/输出操作的使用
* 在**系统测试**中，以及在一定程度上的**集成测试**中，我们关心的是系统作为一个整体是否正常运行的演示。这意味着我们不应该真正使用双精度测试，因为我们不能确定分配给它们的预期行为是否反映了实际的实现 In system tests, and to a certain extent, integration tests, we are concerned with demonstration that a system as a whole is functioning correctly. This means that we shouldn’t really use test doubles because we can’t be sure that the intended behaviour that we assign to them reflect the actual implementation

一个测试用例可以根据它的有效性和效率来评估。Recall the lecture that a test case can be evaluated in terms of its effectiveness and efficiency. 

* **有效性**告诉我们一个测试套件在防止缺陷的引入方面有多好 Effectiveness tells us how good a test suite is at preventing the introduction of defects.
* **效率**告诉我们维护每个被预防的缺陷的测试套件的成本 Efficiency tells us about the cost of maintaining the test suite per defect prevented

:orange: 然而，一个问题是，随着软件系统规模的增加(以组件、代码行、用户或任何其他指标的数量来衡量) ，达到给定效率水平所需的测试数量呈指数增长 One problem however, **is that as the size of a software system increases (measured in terms of number of components, lines of code, users or any other metric), the number of tests required to achieive a given level of effectiveness increases exponentially**.

* 这是因为通过程序的可能路径的数量，或者程序可以随着系统规模的线性增长而呈指数增长的状态 This is because the number of possible paths through a program, or states that the program can be in **increases exponentially as the size of the system increases linearly.**

:candy: 图片说明了这种关系。对效率的冲击是，对于给定的有效性水平，维护测试套件的成本将呈指数增长，从而导致效率的指数下降 The figure here illustrates this relationship. The knock on impact on efficiency is that for a given level of effectiveness, **the cost of maintaining the test suite will increase exponentially, thus causing an exponential decline in efficiency**

# 规模方面：Dimensions of Scale

![](/static/2021-01-26-23-12-41.png)

规模可以影响软件系统的许多不同特性，从而对其测试产生不同的影响，其中任何一个的增加都可能导致系统的复杂性和潜在系统状态的数量呈指数级增加,例如 Scale can affect many different characteristics of software systems and consequently have different impacts on their testing.Increasing any one of these may cause an exponential increase in the complexity of the system and the number of potential system states:

* 代码行数 number of lines of code
* 软件模组数目及相互依存关系 number of software modules
* 软件平台数目 number of software platforms
* 部署的构建和配置的变化 variations in build and configuration of deployments
* 输入的持续时间和变化 duration and variation in inputs
* 软件开发团队的规模、地点及经验 software development team(s) size, locations and experience
* 地理位置及网络连接数目 number of geographic locations and network connection
* 使用者人数及其变化 number and variation in users

其中任何一个的增加都可能导致系统的复杂性和潜在系统状态的数量呈指数级增加

最终，这意味着在规模上执行系统测试(不管我们选择哪个度量标准)可能非常昂贵，有时接近不可行。因此，开发团队需要开发一个系统测试套件，仔细地对系统最重要的属性、特性或用例进行优先排序 Ultimately, this means that performing system tests at scale (regardless of which metric we pick) can be very expensive, sometimes bordering on the infeasible. As a consequence, development teams need to develop a system test suite that carefully prioritises the most important properties, features or use cases of a system.

让我们用一些案例研究来检验测试的规模和复杂性的一些特殊方面 Let’s examine some particular aspects of scale and complexity in testing with some case studies

# 异构系统：Heterogeneous System

![](/static/2021-01-26-23-19-21.png)

大规模的软件开发工作，包括几十万行或几百万行的代码，通常由一个或多个开发团队分担。在整个开发工作中，每个开发团队负责一个或多个子系统的开发、测试和交付。这些 异构软件项目可能由以下人员填充:

* 同一机构内不同的业务单位
* 同一机构内不同附属公司的业务部门
* 不同组织内的项目团队汇聚在一起，组成一个联营集团
* 许多人来自不同的机构

值得注意的是，不同团队在软件项目上的组织文化差异越大(即不同的观点、社会背景和管理风格) ，就越难为软件项目开发出一致的测试程序

* 每个机构都有自己的发展和质量保证工作流程。即使有一致的标准，自然的变化也会发生，因此为整个系统实现一致和连贯的软件质量变得更加困难。
* 事实上，过于严格可能会降低成员团队的生产力，在相互竞争的标准(或执行标准的方式)之间造成紧张或冲突，由于团队不愿受到挑战而推迟整合，或阻止合作者参与

免费和开源项目可能被看作是这种情况的一个很好的例子

* 这些项目必须在吸引新捐助者的愿望与在现有守则基础内保持足够的质量和标准的需要之间取得平衡

---

![](/static/2021-01-26-23-22-12.png)
![](/static/2021-01-26-23-23-01.png)

项目团队已经开发了一个广泛的构建和测试基础设施来管理协作软件/项目。核心工程团队**没有在每次提交时都运行所有的测试，而是开发了一套定期运行的测试**，以便为软件项目提供“健康状态” The project team have developed an extensive build and test infrastructure for managing the collaborative software effort. Rather than run all tests on every commit, which would simply be infeasible, the core engineering team have developed a suite of tests that run periodically to provide a ‘health status’ for the software project

# 社会技术系统：Social-technical system

![](/static/2021-01-26-23-25-16.png)

社会技术系统，有时被称为计算机系统，包括计算机软件和硬件，计算机系统的用户，以及周围的组织和文化实践。因此，系统边界是围绕整个组织划分的: 软件和硬件系统只是整个系统中需要考虑的部分组件。这个视角引入了测试系统的规模和复杂性的社会技术问题Socio-technical systems, sometimes called computer-based systems incorporate both computer software and hardware, the computer system’s users, and the surrounding organisational and cultural practices. Consequently, the system boundary is drawn around the organisation as a whole: the software and hardware systems are just some of the components to be considered in the overall system. This perspective introduces socio-technical issues of scale and complexity for testing a system

从社会技术的角度来看，在引入新的软件系统时，必须对整个组织进行测试，而不仅仅是软件本身(尽管这仍然很重要)。引入一个新的软件系统将会引起组织的其他部门的响应。因此，必须对整个系统进行测试，以检查系统进化引入的潜在“缺陷”。不幸的是，有效地测试社会技术系统具有挑战性，原因有以下几点:From a socio-technical perspective, the organisation as a whole must be tested when a new software system is introduced, and not just the software itself (although this remains important). Introducing a new software system will cause the rest of the organisation to evolve in response. Consequently, the whole system must be tested for potential ‘defects’ which have been introduced by the system evolution. Unfortunately, testing socio-technical systems effectively is challenging for several reasons

* 机构成员可能不能参加考试 The members of the organisation may not be available to take part in tests
* 获得真实的测试场景可能不切实际 Obtaining realistic test scenarios may not be practical
* 识别和覆盖真实的工作实践可能是困难的Identifying and covering the real working practices may prove difficult
* 组织可能独立于系统而进化 The organisation may evolve independently of the system.

设想一个新的机构的主要信息系统，例如大学的学生档案管理系统。新系统可能是为了取代旧系统而采购或开发的。这意味着在开发新系统的同时不断使用旧系统。这意味着可能使用新系统的员工可能无法测试它，因为他们正忙于自己的工作。用足够数量的用户(数百名员工和数万名学生)来运行系统测试，这些用户都在做奇怪和不可预测的事情，以使系统承受足够和现实的压力，这将是困难的——员工有工作，学生有课程要学习。此外，围绕旧系统的实际工作流可能难以理解。管理者将对系统应该如何使用有一个抽象的理解。然而，在实践中，大学内部的不同单位将制定专门的本地工作流程，以满足他们的需求。实际用户将开发出比管理人员意识到的更为复杂的工作流程，以处理范围更广的意外情况或异常。这些场景不太可能出现在系统测试中。最后，新信息系统的采购过程可能会持续数年。在此期间，大学可能会发生相当大的变化(可能是由于行政重组) ，这意味着新的信息系统将无法很好地融入其环境。Think about a major new information system for an organisation, such as a student record management system for a university. The new system is likely procured or developed to replace an older one. This means the old system is under constant use whilst the new system is being developed. This means that the employees who are likely to use the new system may not be available to test it, as they are busy doing their jobs. Running system tests with a sufficient number of users (hundreds of employees and tens of thousands of students) all doing strange and unpredictable things to put the system under sufficient and realistic stress, will be difficult - employees have jobs and students have courses to study. In addition, the actual workflows around the old system may be poorly understood. Managers will have an abstracted understanding of how the system should be used. However, in practice, different units within the university will have developed specialised local workflows to suit their needs. Actual users will have developed much more complex workflows to handle for a wider range of contingencies or exceptions than managers are aware of. These scenarios are not likely to be represented in system tests. Finally, the procurement process for the new information system may last several years. The university may change considerably during this time (perhaps due to administrative reorganisations), meaning that the new information system will not integrate well into its environment.

另一个例子是2007年苏格兰选举中使用的电子计票系统。开发团队在测试上投入了大量的资源。这包括支付每次部署该系统时手工完成的10000张纸质选票，以便在选举之夜投票前进行验收测试。尽管如此，这个系统在夜间还是出现了严重的问题。该系统需要能够处理数十万张选票，而不是10000张，并努力与这种增加的负荷。使用该系统的员工一夜之间变得越来越熟悉，并开始以意想不到的方式优化工作流程。用户填写选票的方式多种多样，这是没有预料到的，导致”坏(拒绝)选票率”高于预期。这导致存储电子结果的索引数据库损坏。总的来说，案例研究并没有显示资源分配不足以测试这类系统，只是在大规模投入使用之前很难这样做 Another example is the e-counting system used for elections in Scotland in 2007. The development team invested significant resources in testing. This included paying for 10000 paper ballots to be manually completed for each deployment of the system, so that an acceptance test could be run ahead of the go-live on election night. Nevertheless, the system experienced significant problems on the night. The system needed to be able to process hundreds of thousands of ballot papers, not 10000, and struggled with this increased load. The workers using the system became increasingly familiar with it overnight and began to optimise their workflows in unexpected ways. The variety of ways in which ballot papers were completed by users was not anticipated, leading to a higher ‘spoiled (rejected) ballot’ paper rate than was expected. This caused the indexes database where the electronic results were being stored to become corrupted. Overall, the case study does not show inadequate resource allocation to testing these sorts of systems, just the difficulty of doing so ahead of a big go-live

# Systems of Systems

![](/static/2021-01-26-23-31-45.png)

有时在军事背景下被称为联合系统，或者在最近的文献中被称为系统联盟，系统的系统代表了软件测试挑战的极端。一个系统的系统代表多个异质的半自治系统，这些系统相互协作或相互协调以产生突发效应。这方面的例子包括电子股票交易所、军事协调系统(军方或不同军方的多个部门)、空中交通管制系统和供应链管制系统。

* 一个很好的例子是2010年5月的闪电崩盘(还有其他事件) ，它导致股票价格在大约半个小时内迅速崩溃和复苏。现代股票市场极其复杂，由各种各样的交易程序和算法控制，所有这些都是由各种各样的金融机构开发和维护的独有软件。理解这些快速发展的软件系统和它们的用户之间的相互作用是极其困难的，这使得测试整个股票市场的复杂性变得极其困难

值得注意的是，一个系统的系统不受任何一个参与者的控制或任何真正的协调。单个系统可能独立于邻近系统和整个系统进化。测试系统极具挑战性，因为

* Each of the member systems will have different cultures and practices 每个成员系统都有不同的文化和实践
* The system cannot be isolated or configured for a test 系统不能隔离或配置用于测试
* One of the member systems may evolve during a test 一个成员系统可能在测试期间进化
* The expected outputs for the system are not easy to define 系统的预期输出不容易定义

# Getting Feedback from the wild

![](/static/2021-01-26-23-34-25.png)

由于运行验收测试的困难，许多开发团队已经通过从用户那里收集信息的机制扩展了他们的应用程序。这些可以是**崩溃报告的自动提交**，包括堆栈跟踪和堆状态之类的信息，也可以是从**应用程序内部手动提交缺陷报告的工具** Due to the difficulty of running acceptance tests many development teams have augmented their applications with mechanisms for gathering information from their users. These can either be automated submissions of crash reports, including information like stack traces and heap state, or facilities for manually submitting defect reports from within an application

* 该方法有效地将系统的用户基础转化为测试人员，利用扩展效应来缓解大规模系统测试的问题 The approach effectively turns the user base for a system into its testers, leveraging the scaling effect to mitigate the problems of large scale system testing
* 但是请注意，这并不是万能药。这种方法依赖于有一群愿意作为测试人员(通常是没有报酬的)进行合作的用户。成熟的系统已经获得了大量用户的认可，在功能的测试覆盖率方面将获得最大的好处。相反，新的系统，可能从用户测试中获益，却缺少受众。此外，还有一个风险，即发布的产品是不成熟的，容易出现缺陷，将阻止用户采取 Note however that this is not a panacea. The approach is dependent on having an audience of users who are willing to cooperate as (often unpaid) testers. Mature systems that have already gained acceptance amongst a large user base will recieve the greatest benefit in terms of test coverage of features. Conversely, newer systems, that might benefit from user testing lack the audience. Also, there is a risk that releasing a product that is immature and prone to defects will deter user take up.

出于用户测试的目的，有两种划分用户基础的方法 There are two ways of partitioning the user base for user testing purposes

* **Beta 测试**程序将现有系统的新特性发布给系统的子集合的用户。从用户的角度来看，这样做的好处是他们可以提前收到即将完成的新特性的通知。从开发团队的角度来看，beta 测试有助于在新特性的阶段性发布期间控制风险。如果一个新特性包含了一个未知的但是关键的缺陷，这个缺陷在 beta 测试阶段出现，那么危害就被最小化了 Beta-testing programmes release new features for an existing system to a sub-set of the system’s users. The advantage from the users’ perspective is that they receive advance notice of new features that are nearing completion. From the development team’s point of view, beta-testing helps to control risk during the staged release of new features. If a new feature contains a unknown, but critical defect that emerges during the beta testing phase, then harm is minimised
* **A/b 测试**可以用来评估一个新特征的不同候选者。开发团队为用户群的不同子集发布相同特性的两个不同版本。用户可以根据特定的策略(如地理位置)进行分区，也可以是随机的。然后收集关于用户行为的度量标准，以决定两种变量中哪一种更有效。例如，在线购物应用程序上的开发团队可能正在为新用户处理登陆页面和注册过程。一种选择是在他们开始使用应用程序之前收集所有信息。第二个选择是在这个阶段只收集最少的信息，然后在用户第一次购买时收集更详细的信息。备选方案1有可能阻止新用户注册。选择2可能阻止注册用户进行购买。为不同的用户实现这两个工作流程，可以让团队决定哪个流程最大限度地提高销售额 A/B testing can be used to evaluate different candidates for a new feature. The development team release two different versions of the same feature to different subsets of the user base. The users may be partitioned by a particular strategy (such as geography), or randomly. Metrics are then gathered about user behaviour to decide which of the two variants is more effective. For example, a development team on an online shopping application might be working on the landing page and registration process for a new user. One option is to collect all information before they start using the application. A second option is to collect only minimal information at this stage and then collect more detailed information when a user makes their first purchase. Option 1 risks deterring new users from registering. Option 2 may deter registered users from making a purchase. Implementing both workflows for different users allows the team to decide which process maximises sales
  * 由于易于分发新特性，a b 测试通常用于 web 应用程序开发 Due to the ease of distributing new features, A B testing is commonly used in web application development.

# Summary

> Increasing scale in a software or computer based system reduces the coverage and effectiveness of testing efforts, even if a proportionate number of resources are applied to testing. **在软件或计算机系统中增加规模会降低测试工作的覆盖率和有效性**，即使在测试中使用了相应数量的资源