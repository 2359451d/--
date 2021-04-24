# L19 System Scale Test

:sweat_smile: 到底讲了毛

* [L19 System Scale Test](#l19-system-scale-test)
* [非功能需求测试 & 优点：Testing Non-Functional Properties](#非功能需求测试--优点testing-non-functional-properties)
* [非功能性测试所需信息(需求信息)：Info needed for testing non-functional properties](#非功能性测试所需信息需求信息info-needed-for-testing-non-functional-properties)
* [安全关键环境（严格安全性环境）：Safety Critical Environment](#安全关键环境严格安全性环境safety-critical-environment)
  * [为什么严格安全系统需要测试？](#为什么严格安全系统需要测试)
* [系统-可靠性测试：Reliability Testing](#系统-可靠性测试reliability-testing)
  * [系统可靠性测试指标：Metrics](#系统可靠性测试指标metrics)
* [测试系统安全性方法：Methods for testing system safety](#测试系统安全性方法methods-for-testing-system-safety)
  * [敌对环境概念：Systems operating in Hostile Environments](#敌对环境概念systems-operating-in-hostile-environments)
  * [安全性测试指标/度量标准：Security Testing Metrics](#安全性测试指标度量标准security-testing-metrics)
  * [安全性测试方法：Security Testing Methods](#安全性测试方法security-testing-methods)
* [性能测试：Performance Testing](#性能测试performance-testing)
  * [指标（评估标准）](#指标评估标准)
  * [性能测试种类](#性能测试种类)
* [系统规模对系统测试的影响：Scaling Effects in System Testing](#系统规模对系统测试的影响scaling-effects-in-system-testing)
* [软件规模指标：Dimensions of Scale](#软件规模指标dimensions-of-scale)
* [大规模例子 - 异构系统：Heterogeneous System](#大规模例子---异构系统heterogeneous-system)
* [大规模例子-社会技术系统：Social-technical system](#大规模例子-社会技术系统social-technical-system)
* [Systems of Systems](#systems-of-systems)
* [用户测试：Getting Feedback from the wild](#用户测试getting-feedback-from-the-wild)
  * [用户测试类别](#用户测试类别)
* [Summary](#summary)

# 非功能需求测试 & 优点：Testing Non-Functional Properties

![](/static/2021-04-22-12-32-59.png)

> **非功能性需求描述了整个系统应满足的紧急特性或属性（需求），这些属性必须进行测量评估才能得知是否满足**，而不是作为功能性需求提供的特性被观察。Non-functional requirements describe emergent characteristics or properties of the overall system that must be measured to be satisfied rather than observed as a provided feature as for functional requirements.
>
> 因此，**通常不能在单元测试中或通过静态分析来检查非功能性需求**。它们只能在系统实现之后才能进行测试。 Consequently, non-functional requirements cannot typically be checked for in unit tests or through static analysis. They can only be tested once an implementation of a system has been realised.
>
> **非功能性需求测试的实现可能是最终的系统，也可能是原型**。或者，也可以建立一个模型，从中可以近似最终系统的特性。 The implementation tested may be the final system, or a prototype. Alternatively, it may be possible to build a model from which the characteristics of the final system can be approximated.

:orange: <font color="red">测试非功能性需求的一个优点是，它们可以作为更复杂的功能性需求的集合，但这些功能性需求过于复杂，无法单独进行测试</font> An advantage of testing non-functional requirements is that they may act as an aggregate for more complex functional properties that are too complex to test individually.

# 非功能性测试所需信息(需求信息)：Info needed for testing non-functional properties

从某种意义上说，**开发设计非功能性需求的测试与进行科学实验是一样的**。In one sense, developing tests for non-functional requirements is the same as conducting a scientific experiment.

* **测试人员对被测系统(需求)提出一个假设，然后构建一个实验来确定该假设是否满足** The tester formulates a hypothesis concerning the system under test (the requirement), and then constructs an experiment to determine whether the hypothesis is satisfied.

![](/static/2021-01-26-21-44-13.png)

:orange: **一个完善的非功能性需求应该描述** A well formulated non-functional requirement should describe the:

* **所评测系统的性质** property of the system to be measured
* **使用的度量标准** metric to be used
* **满足要求时需要控制的条件** operating conditions in which the requirement must be satisfied
* **为满足需求系统必须超过的行为阈值** threshold the system behaviour must exceed to satisfy the requirement

:candy: <font color="red">非功能性需求有许多不同的类别</font>。**每一个，都可以通过几种不同的方式进行测试** There are many different categories of non-functional properties. Each one, can be tested in several different ways

* 可靠性，安全性,...

# 安全关键环境（严格安全性环境）：Safety Critical Environment

:orange: 不利环境定义 adverse environment

* 许多系统的运行发生在**对系统有潜在危险的条件**下，或者**对操作或依赖系统的人有潜在危险的条件下**. <font color="red">如果系统发生故障，这些危险可能来自系统运作的不利环境，或系统本身的性质</font>。The operation of many systems occurs in conditions which are potentially harmful to the system or to humans who operate or depend on the system. These dangers may arise from an adverse environment in which the system operates, or the nature of the system itself, should it malfunction.

![](/static/2021-01-26-21-49-09.png)

:orange: 在安全关键环境下使用的软件例子包括 Examples of software used in safety critical contexts include

* 自动驾驶员用于运输，例如飞机 Automatic pilots for transportation, such as aeroplanes.
* 粒子束放射治疗设备操作中使用的设备 Equipment used in the operation of particle beam radio–therapy equipment.
* 天基技术，如卫星、太空船和行星探测器 Space based technology, such as satellites, space craft and planetary rovers.
* 核电站管理系统 Nuclear power station management systems.

:orange: 这些系统被称为**安全关键（系统/环境）**，These systems are called safety critical

* 因为这样的系统设计和评估过程的一个主要考虑是，<font color="red">确保系统的操作不会对系统或其用户造成伤害</font> because a principle concern for the system design and evaluation process is to gain confidence that the operation of the system will not cause the system or its users harm.
* <font color="red">如果某软件用来控制安全关键系统的行为，那么也必须保证其安全性</font> If software is used to control the system’s behaviour then assurance must also be gained about its safety.

## 为什么严格安全系统需要测试？

:orange: <font color="red">测试安全关键系统的安全性与开发测试用例有关，这些测试用例用于证明在使用该系统的不利环境中不会造成（对人，对系统的）损害</font> Testing the safety of a system is concerned with the development of test cases which demonstrate that harm will not be caused in the adverse environment in which the system will be used.

# 系统-可靠性测试：Reliability Testing

![](/static/2021-01-26-21-53-40.png)

:orange: **系统的可靠性是指系统根据其规范表现的程度**。the reliability of a system is the extent to which a system performs according to its specification.

## 系统可靠性测试指标：Metrics

:orange: (测量系统可靠性的)**指标包括** Metrics include

* **按需故障概率** probability of failure on demand(PFD)
  * 按需故障概率（PFD）提供了对<font color="red">每次访问系统时发生故障概率的估计</font> Probability of failure on demand (PFD) provides an estimate of the probability of failure each time a system is accessed
* **平均故障时间** Mean time to failure
  * 平均故障时间，<font color="red">表示一个软件系统从开始到故障所需的时间，或者故障之间的平均时间</font> Mean time to failure indicating how long it takes for a software system to fail from initiation, or the average time between failures.
  * 对于维修困难或昂贵的系统，如基于空间的系统，这是一个有用的指标。理想情况下，对于此类系统，平均故障时间应大于任务时间 This is a useful metric for systems where access for repairs is difficult or expensive, such as space based systems. Ideally, the mean time to failure should be greater than the mission time for such systems.
* **宕机时间** down time
  * **宕机时间(例如每年) ，表示一年以上的系统停机可能损失多少时间** Down time (e.g. per year) indicating how much time can be lost to system outages over a year.
  * 这对于高需求、连续使用的系统是很有用的，例如大批量的交易处理。例如，支付系统通常被设计为每年的停机时间少于几秒钟 This is useful for high demand, continuous use systems, such as high volume transaction processing. Payment systems, for example, are often designed to have down times of less than a few seconds per year.

# 测试系统安全性方法：Methods for testing system safety

![](/static/2021-01-26-21-57-43.png)

:orange: 测试系统安全性的几种方法

* **模型驱动开发** Model driven development
  * 模型驱动的开发允许从平台独立的模型中自动生成软件实现和测试套件 Model driven development allows implementation software and test suites to be generated automatically from platform independent models.
  * 测试用例来源于关于软件模型的合法和非法状态的断言  Test cases are derived from assertions about legal and illegal states for the software model
* **净室开发** Cleanroom development
  * 净室开发，使用自动化和统计方法来选择执行的测试用例，并用于计算软件系统的 PFD（按需故障概率） 估计值和估计值的可信度 Cleanroom development, which uses automated and statistical methods to select test cases for execution, and is used to calculate an estimate of a software system’s PFD, and a confidence in the estimate
* **Hazard and Operability Study (HAZOPS)**
  * 危害与可操作性分析来源于化学工程及类似的活动 derived from chemical engineering and similar activities
  * 对于软件，**可以使用类似 HAZOPS 的过程来识别软件组件之间的信息流。然后可以询问有关信息流的问题**，For software, a HAZOPS like process can be used to identify flows of information between software components. Questions can then be asked about the flows of information,
    * （然后检查每一个参数偏离设计条件的影响，采用经过挑选的关键词表，例如“大于”“小于”“部分”…等，来描述每一个潜在的偏离。最终应识别出所有的故障原因，得出当前的安全保护装置和安全措施。）
    * 例如当信息不正确或到达得太早/晚时会发生什么 such as what happens when the information is incorrect, or arrives too early

:orange: **使用正式的测试方法的重点是获得对系统【可靠性】的精确测量，为一个安全性测试用例提供输入**。The emphasis in the use of formal methods for testing is to gain accurate measurements as to the reliability of a system, to provide input into a safety case

* 安全用例是系统满足其安全（非功能性）要求的证明 Safety cases are justifications that a system meets its safety requirements
* 相比之下，**HAZOPS 的目的是探索一个或多个组件故障对信息系统其余部分的影响**  In contrast, the purpose of a HAZOPS is to explore the consequences of a failure in one or more components for the rest of an information system
* **HAZOPS 用于在系统发生故障时获得系统可靠性和安全性的保证** HAZOPS are used to gain assurance as to the reliability and safety of a system in the presence of failures

## 敌对环境概念：Systems operating in Hostile Environments

![](/static/2021-01-26-22-21-52.png)

:orange:系统环境除了不利（严格安全环境，安全关键环境）之外，还可能被认为是**敌对的** As well as being adverse, a system environment may also be considered hostile.

* **敌对环境**被认为包含主动代理(有时称为威胁或攻击者) ，<font color="red">其目的是故意渗透、破坏和/或破坏已部署的系统</font>。A hostile environment is assumed to contain active agents (sometimes called threats or attackers) whose goal is to deliberately penetrate, subvert and/or sabotage the deployed system.

:orange: 在敌对环境中部署系统的例子包括: Examples of systems deployed in hostile environments include

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

## 安全性测试指标/度量标准：Security Testing Metrics

![](/static/2021-01-26-22-32-31.png)

:orange: **WHY**：<font color="deeppink">系统威胁主动搜索缺陷 ，系统中的缺陷可以被攻击。因此，有必要在这些情况下进行测试，以保证系统能够抵抗这种攻击</font> System threats actively search for defects, defects in the system that can be attacked. Consequently, it is necessary in these contexts to conduct tests to gain assurance that the system is resistant to such attacks.

:orange: 安全性测试指标包括

* **攻击者渗透系统所需的能力或知识** attacker capabilities or knowledge required to penetrate the system
* **时间和资源渗透** time and resources to penetration
  * 渗透的时间和资源，估计敌人需要多长时间，以及需要多少资源才能渗透系统 time and resources to penetration, giving an estimate of how long it would take an adversary, and how much resource would need to be applied in order to penetrate a system.
* **每行代码中未打补丁的漏洞** unpatched vulnerabilities per line of code
  * 这个想法来自于对物理安全进行评估，并给予时间和资源评级 The idea comes from assessments used for physical safes which are given a time and resource rating
* **每年发出的补丁** patches issued per year
* **每年成功的渗透率** successful penetrations per year
* **攻击面暴露** attack surface exposure

:candy: 时间和资源渗透，对对手需要花费多长时间以及需要应用多少资源来渗透一个系统进行估计。这个想法来自于用于物理保险箱的评估，这些保险箱被赋予了时间和资源等级 time and resources to penetration, giving an estimate of how long it would take an adversary, and how much resource would need to be applied in order to penetrate a system. The idea comes from assessments used for physical safes which are given a time and resource rating.

## 安全性测试方法：Security Testing Methods

![](/static/2021-01-26-22-37-54.png)

有很多可用安全性测试方法

* **漏洞测试** vulnerability testing
  * **SQL 注入测试** SQL injection testing
  * **端口扫描** port scanning
  * **模糊测试** fuzz testing
    * Fuzz 测试，在这种测试中，<font color="deeppink">向软件系统提供异常的或格式不正确的(fuzzed)输入，这些输入可能导致系统以意想不到的(和不安全的)方式运行</font> Fuzz testing, in which a software system is supplied with unusual or malformed (fuzzed) inputs that may cause the system to behave in unexpected (and insecure) ways.
    * **模糊测试是一种检查输入在传递到业务逻辑代码之前的验证程度的方法**  Fuzz testing is a way of checking the extent to which inputs are validated before they are passed on to business logic code.
    * 例如，模糊测试是检测缓冲区溢出漏洞的有效手段 Fuzz testing is an effective means of detecting buffer-overflow vulnerabilities, for example.
* **渗透测试** Penetration testing
  * 渗透测试，其中一个“红队”或“攻击队”的任务是**未经授权地访问受系统保护的资源(模拟攻击者)**，这些资源与**攻击者**预期的资源相同  a red team</ or attack team is tasked with gaining unauthorised access to the resources protected by a system, equipped with the same resources expected of an attacker.
  * 渗透测试人员可能专注于技术上的弱点，也可能通过社会工程以获得进入一个系统的机会 Penetration testers may concentrate on technical vulnerabilities or also engage in social engineering to gain access to a system

# 性能测试：Performance Testing

![](/static/2021-01-26-22-47-31.png)

:orange: **性能测试关注的是评估一个系统如何应对不同的输入速度** Performance testing is concerned with assessing how a system copes with different rates of input

## 指标（评估标准）

Properties/Metric

* **吞吐量** throughput
  * 系统在给定时间内可以处理的事务量 the amount of transactions that the system can process in a given time
* **需求** demand
  * 系统可以处理的最大事务速率 the maximum rate of transactions that the system can process
* **响应时间** response
  * 响应事务请求所需的平均或最长时间 response time, the average or maximum length of time taken to respond to a transaction request.

## 性能测试种类

:orange: <font color="red">一旦选择了属性和度量，就可以针对阈值执行两种类型的测试</font> Once a property and metric has been selected, there are two types of test that can be performed with respect to the threshold

* **极限测试** Limit testing
  * 极限测试关注的是<font color="red">证明系统在规定的极限范围内表现正常</font> Limit testing is concerned with demonstrating that a system behaves normally within a required limit
* **压力测试** Stress testing
  * 关心的是发现当<font color="red">某个特定的极限被突破时会发生什么</font> Stress testing is concerned with discovering what happens when a particular limit is breached
  * 压力测试其实有两个目的，一是测试应用在高并发情况下是否会报错，进程是否会挂掉；二是测试应用的抗压能力，预估应用的承载能力，为运维同学提供扩容的依据。

---

:candy: 极限测试用于证明一个**系统在规定的操作极限内表现出预期的效果**。相反，压力测试是用来发现**系统在超出规定的操作极限时的表现** Limit testing is used to demonstrate that a system behaves as expected within required operating limits. Conversely, stress testing is used to discover how a system behaves beyond the operating limits specified.

:orange: 通常情况下，**压力测试**是用来**证明一个系统在超出正常限制的情况下继续表现{可靠的或可预测的** Typically, stress tests are used to demonstrate that a system continues to behaves {reliably or predictably when operating outside of normal limits

* 例如，一个系统对过度需求的反应可能是恢复到一个 "安全模式"，只向客户提供有限的服务。For example, a system’s response to excessive demand may be to revert to a ‘safe mode’ offering only a limited service to its clients. During the 9/11 terrorist attacks
* 例如，在9/11恐怖袭击期间，BBC的新闻网站经历了异常高的用户请求，试图获得最新的信息。该网站恢复了简单的外观，减少了图像和视频，这需要更少的带宽来传输。
 for example, the BBC’s News Website experienced unusually high requests from users attempting to obtain up to date information. The site reverted to a simpler appearance with less images and video, which required less bandwidth to transmit.

:orange: **另外，系统可能需要“优雅地”安全关闭，而不是完全故障** Alternatively, it may be required that the system performs a ‘graceful’ safe shutdown, rather than fail completely. Database servers,

* 另外，可能需要系统执行 "优雅的 "安全关闭，而不是完全失败。例如，数据库服务器通常配备了不间断电源（UPS），其本质是大型电池组。如果发生断电，在服务器完成数据库上任何未完成的事务时，将使用电池电源，然后启动系统关闭。 for example, are often equipped with Uninterruptable Power Supplies (UPS) which are essentially large battery packs. If a power loss is experienced, the battery power is used while the server completes any outstanding transactions on the database and then initiates a system shutdown

# 系统规模对系统测试的影响：Scaling Effects in System Testing

在关于使用行为驱动开发的验收测试的讲座中（L17-18），概述了三种规模的测试：单元测试、集成测试和系统测试。In the lecture on acceptance testing using behaviour driven development, I outlined three scales of testing: unit testing, integration testing and system testing. In unit testing, we keep tests focused on the business logic of a single module of application code (typically a class). We isolate the test target from other dependencies through the use of test doubles, such as mocks. An additional benefit of the use of test doubles is that unit tests are kept very fast, particularly by eliminating the use of any real input/output operations

* 在**单元测试**中，我们将<font color="red">测试集中在应用程序代码的单一模块（通常是一个类）的业务逻辑上</font>。我们通过使用测试替换（如模拟），将测试目标与其他依赖关系隔离。使用测试替换的另一个好处是，单元测试的速度非常快，特别是通过测试替换消除了任何实际输入/输出操作的使用（**没有IO的单元测试很快**）。In unit testing, we keep tests focused on the business logic of a single module of application code (typically a class). We isolate the test target from other dependencies through the use of test doubles, such as mocks. An additional benefit of the use of test doubles is that unit tests are kept very fast, particularly by eliminating the use of any real input/output operations.
* 在**系统测试（验收测试）**中，以及在一定程度上的**集成测试**中，我们关注的是<font color="red">证明系统作为一个整体是正确运作的</font>。<font color="blue">这意味着我们不应该真正使用测试替换，因为我们不能确定我们分配给他们的预期行为是否反映了实际的实现</font> In system tests, and to a certain extent, integration tests, we are concerned with demonstration that a system as a whole is functioning correctly. This means that we shouldn’t really use test doubles because we can’t be sure that the intended behaviour that we assign to them reflect the actual implementation

---

一个测试用例可以根据它的有效性和效率来评估。Recall the lecture that a test case can be evaluated in terms of its effectiveness and efficiency. 

* **有效性**告诉我们一个测试套件在防止缺陷的引入方面有多好(防止缺陷引入的能力) Effectiveness tells us how good a test suite is at preventing the introduction of defects.
* **效率**告诉我们每预防一个缺陷所需的维护测试套件的成本 Efficiency tells us about the cost of maintaining the test suite per defect prevented

---

:orange: 然而，一个问题是，随着<font color="red">软件系统规模的增加</font>（以组件数量、代码行数、用户或任何其他指标来衡量），为<font color="red">实现特定的有效性水平所需的测试数量将呈指数级增长</font> One problem however, is that as the size of a software system increases (measured in terms of number of components, lines of code, users or any other metric), the number of tests required to achieive a given level of effectiveness increases exponentially.

* 这是因为**随着系统规模的线性增长**，通过程序的可能**路径**或程序**可能处于的状态**的**数量呈指数级增长**。 This is because the number of possible paths through a program, or states that the program can be in increases exponentially as the size of the system increases linearly.

![](/static/2021-01-26-23-03-05.png)

:candy: 这里的图说明了这种关系（图里系统规模指标为 component，组件数量）。对效率的影响是，<font color="red">对于给定的有效性水平，维护测试套件的成本将呈指数级增长，从而导致效率呈指数级下降</font> The figure here illustrates this relationship. The knock on impact on efficiency is that for a given level of effectiveness, the cost of maintaining the test suite will increase exponentially, thus causing an exponential decline in efficiency.

# 软件规模指标：Dimensions of Scale

![](/static/2021-01-26-23-12-41.png)

:orange: **软件系统规模指标**（metrics/dimensions） - <font color="deeppink">规模可以影响软件系统的许多不同特性，从而对其测试产生不同影响</font>。例如 Scale can affect many different characteristics of software systems and consequently have different impacts on their testing.

* **代码行数** number of lines of code
* **软件模块的数量(和相互依赖性** number of software modules
* **软件平台的数量** number of software platforms
* **部署的构建和配置的变化** variations in build and configuration of deployments
* **输入的持续时间和变化** duration and variation in inputs
* **软件开发团队的规模、地点及经验** software development team(s) size, locations and experience
* **地理位置及网络连接数目** number of geographic locations and network connection
* **用户的数量和变化** number and variation in users

:candy: <font color="deeppink">增加其中任何一项都可能导致系统的复杂性和潜在系统状态的数量呈指数级增长</font> Increasing any one of these may cause an exponential increase in the complexity of the system and the number of potential system states:

:candy: <font color="red">最终，这意味着大规模执行系统测试（无论我们选择哪种指标）可能会非常昂贵，有时甚至接近于不可行</font>。因此，开发团队需要开发一个系统测试套件，仔细优先考虑系统中最重要的属性、功能或用例 Ultimately, this means that performing system tests at scale (regardless of which metric we pick) can be very expensive, sometimes bordering on the infeasible. As a consequence, development teams need to develop a system test suite that carefully prioritises the most important properties, features or use cases of a system.

# 大规模例子 - 异构系统：Heterogeneous System

让我们用一些案例研究来检验测试的规模和复杂性的一些特殊方面 Let’s examine some particular aspects of scale and complexity in testing with some case studies

![](/static/2021-01-26-23-19-21.png)

大规模的软件开发工作，包括几十万行或几百万行的代码，通常由一个或多个开发团队分担。**在整个开发工作中，每个开发团队负责一个或多个子系统的开发、测试和交付**。这些 **异构软件项目**可能由以下人员填充:Large scale software development efforts, consisting of hundreds of thousands, or millions of lines of code, are often divided amongst one or more teams of developers. Each team of developers is given responsibility for the development, testing and delivery of one or more sub-systems within the overall development effort. These heterogeneous software projects may be populated by: May be composed of:

* 同一机构内不同的业务单位 Different business units within a single organisation.
* 同一机构内不同附属公司的业务部门 Business units from different subsidiaries within the same umbrella organisation.
* 不同组织内的项目团队汇聚在一起，组成一个联营集团 Project teams within different organisations brought together as part of a consortia.
* 许多人来自不同的机构 Many individuals from different organisations

<font color="red">重要的是，在项目中工作的不同团队之间的组织文化差异（即不同的观点、社会背景和管理风格）越大，为软件项目制定一致的测试方案就越难</font> Significantly, the greater the variation in organisational culture (i.e. the different outlooks, social backgrounds and management styles) between the different teams working on the project, the harder it can be to develop a consistent test programme for a software project.

* 每个组织都会有自己的开发和质量保证的工作流程。即使有约定俗成的标准，也会发生自然的变化，**因此，为整个系统实现一致的、连贯的软件质量变得更加困难** Each organisation will have its own workflows for development and quality assurance. Even though there may be agreed standards, natural variation will occur, so it becomes harder to achieve a consistent and coherent software quality for the overall system
* 事实上，过度的限制可能会降低成员团队的生产力，在相互竞争的标准（或实施标准的方式）之间造成紧张或冲突，由于团队不愿意接受挑战而延迟整合，或者阻止合作者的参与 Indeed, being overly restrictive may reduce the productivity of the member teams, create tension or conflict between competing standards (or ways of implementing standards), delay integration as teams are reluctant to be challenged, or deter participation from collaborators.

自由和开放源码项目可以被看作是这种情况的一个很好的例子。这些项目必须在**吸引新的贡献者**的愿望与在**现有代码库中保持足够的质量和标准**的需要之间取得平衡。Free and open source projects may be seen as a good example of this situation. Such projects must balance a desire to attract new contributors, with the need to maintain sufficient quality and standards within the existing code base.

---

![](/static/2021-01-26-23-22-12.png)

该基础设施有一个核心架构，由一个小型的永久性开发团队维护。此外，更广泛的代码库由来自40个国家的伙伴组织的贡献者维护。这些贡献者也负责为他们所维护的各个模块提供测试 The infrastructure has a core architecture maintained by a small permanent development team. In addition, the wider code base is maintained by contributors from the partner organisations across 40 countries. These contributors are also responsible for supplying the tests for the individual modules that they maintain.

![](/static/2021-01-26-23-23-01.png)

项目团队已经开发了一个广泛的构建和测试基础设施来管理协作软件/项目。核心工程团队**没有在每次提交时都运行所有的测试(这根本不可行)，而是开发了一套定期运行的测试**，以便为软件项目提供“健康状态” The project team have developed an extensive build and test infrastructure for managing the collaborative software effort. Rather than run all tests on every commit, which would simply be infeasible, the core engineering team have developed a suite of tests that run periodically to provide a ‘health status’ for the software project

# 大规模例子-社会技术系统：Social-technical system

![](/static/2021-01-26-23-25-16.png)

社会技术系统，有时被称为**计算机系统，包括计算机软件和硬件，计算机系统的用户，以及周围的组织和文化实践**。因此，系统边界是**围绕整个组织划分**的: <font color="red">软件和硬件系统只是整个系统中需要考虑的部分组件</font>。这个视角引入了测试系统的规模和复杂性的社会技术问题Socio-technical systems, sometimes called computer-based systems incorporate both computer software and hardware, the computer system’s users, and the surrounding organisational and cultural practices. Consequently, the system boundary is drawn around the organisation as a whole: the software and hardware systems are just some of the components to be considered in the overall system. This perspective introduces socio-technical issues of scale and complexity for testing a system

从社会技术的角度来看，<font color="red">在引入新的软件系统时，必须对整个组织进行测试，而不仅仅是软件本身(尽管这仍然很重要)</font>。引入一个新的软件系统将会引起组织的其他部门的响应。因此，**必须对整个系统进行测试，以检查系统进化引入的潜在“缺陷”**。From a socio-technical perspective, the organisation as a whole must be tested when a new software system is introduced, and not just the software itself (although this remains important). Introducing a new software system will cause the rest of the organisation to evolve in response. Consequently, the whole system must be tested for potential ‘defects’ which have been introduced by the system evolution. 

:orange: 不幸的是，**有效地测试社会技术系统具有挑战性**，原因有以下几点:Unfortunately, testing socio-technical systems effectively is challenging for several reasons

* **机构成员可能不能参加测试** The members of the organisation may not be available to take part in tests
* **获得真实的测试场景可能不切实际** Obtaining realistic test scenarios may not be practical
* **识别和覆盖真实的工作实践可能是困难的** Identifying and covering the real working practices may prove difficult
* **组织可能独立于系统而进化** The organisation may evolve independently of the system.

设想一个新的机构的主要信息系统，例如大学的学生档案管理系统。新系统可能是为了取代旧系统而采购或开发的。**这意味着在开发新系统的同时不断使用旧系统。这意味着可能使用新系统的员工可能无法测试它，因为他们正忙于自己的工作**。用足够数量的用户(数百名员工和数万名学生)来运行系统测试，这些用户都在做奇怪和不可预测的事情，以使系统承受足够和现实的压力，这将是困难的——员工有工作，学生有课程要学习。此外，**围绕旧系统的实际工作流可能难以理解**。管理者将对系统应该如何使用有一个抽象的理解。然而，在实践中，大学内部的不同单位将制定专门的本地工作流程，以满足他们的需求。实际用户将开发出比管理人员意识到的更为复杂的工作流程，以处理范围更广的意外情况或异常。这些场景不太可能出现在系统测试中。最后，新信息系统的采购过程可能会持续数年。在此期间，大学可能会发生相当大的变化(可能是由于行政重组) ，这意味着新的信息系统将无法很好地融入其环境。Think about a major new information system for an organisation, such as a student record management system for a university. The new system is likely procured or developed to replace an older one. This means the old system is under constant use whilst the new system is being developed. This means that the employees who are likely to use the new system may not be available to test it, as they are busy doing their jobs. Running system tests with a sufficient number of users (hundreds of employees and tens of thousands of students) all doing strange and unpredictable things to put the system under sufficient and realistic stress, will be difficult - employees have jobs and students have courses to study. In addition, the actual workflows around the old system may be poorly understood. Managers will have an abstracted understanding of how the system should be used. However, in practice, different units within the university will have developed specialised local workflows to suit their needs. Actual users will have developed much more complex workflows to handle for a wider range of contingencies or exceptions than managers are aware of. These scenarios are not likely to be represented in system tests. Finally, the procurement process for the new information system may last several years. The university may change considerably during this time (perhaps due to administrative reorganisations), meaning that the new information system will not integrate well into its environment.

另一个例子是2007年苏格兰选举中使用的电子计票系统。开发团队在测试上投入了大量的资源。这包括支付每次部署该系统时手工完成的10000张纸质选票，以便在选举之夜投票前进行验收测试。尽管如此，这个系统在夜间还是出现了严重的问题。该系统需要能够处理数十万张选票，而不是10000张，并努力与这种增加的负荷。使用该系统的员工一夜之间变得越来越熟悉，并开始以意想不到的方式优化工作流程。用户填写选票的方式多种多样，这是没有预料到的，导致”坏(拒绝)选票率”高于预期。这导致存储电子结果的索引数据库损坏。总的来说，案例研究并没有显示资源分配不足以测试这类系统，只是在大规模投入使用之前很难这样做 Another example is the e-counting system used for elections in Scotland in 2007. The development team invested significant resources in testing. This included paying for 10000 paper ballots to be manually completed for each deployment of the system, so that an acceptance test could be run ahead of the go-live on election night. Nevertheless, the system experienced significant problems on the night. The system needed to be able to process hundreds of thousands of ballot papers, not 10000, and struggled with this increased load. The workers using the system became increasingly familiar with it overnight and began to optimise their workflows in unexpected ways. The variety of ways in which ballot papers were completed by users was not anticipated, leading to a higher ‘spoiled (rejected) ballot’ paper rate than was expected. This caused the indexes database where the electronic results were being stored to become corrupted. Overall, the case study does not show inadequate resource allocation to testing these sorts of systems, just the difficulty of doing so ahead of a big go-live

# Systems of Systems

![](/static/2021-01-26-23-31-45.png)

有时在军事背景下被称为联合系统，或者在最近的文献中被称为系统联盟，系统的系统代表了软件测试挑战的极端。一个系统的系统代表多个异质的半自治系统，这些系统相互协作或相互协调以产生突发效应。这方面的例子包括电子股票交易所、军事协调系统(军方或不同军方的多个部门)、空中交通管制系统和供应链管制系统。 Sometimes referred to as joint systems in military contexts, or coalitions of systems in more recent literature, <em>systems of systems</em> represent the extreme end of the scale for challenges in software testing. A system of system represents multiple heterogeneous semi-autonomous systems that cooperate or are coordinated to produce emergent effects. Examples include electronic stock exchanges, military coordination systems (across multiple arms of a military or different militaries) air traffic control systems and supply chain control systems.

* 一个很好的例子是2010年5月的 "闪电风暴"（还有其他例子），它在大约半小时内造成了股票价格的迅速崩溃和恢复。现代股票市场是非常复杂的，由各种各样的交易应用程序和算法控制，所有这些都是由广泛的金融机构开发和维护的专有软件。**了解这些快速发展的软件系统及其用户之间的相互作用是非常困难的，这使得对整个股票市场的测试变得非常困难**。A good example is the Flash Crash in May 2010 (there have been others), which caused a rapid collapse and recovery in stock prices over about half an hour. Modern stock markets are extremely complex, controlled by a diverse range of trading applciations and algorithms, all developed and maintained as proprietory software by a wide range of financial institutions. Understanding the interplay between these rapidly evolving software systems and their users is extremely difficult, making testing of the overall stock market for resillience extremely difficult.

:orange: 值得注意的是，<font color="red">一个系统的系统不受任何一个参与者的控制或任何真正的协调。单个系统可能独立于邻近系统和整个系统进化</font>。**测试联合系统极具挑战性**，因为 Significantly, a system of systems is not under the control or any real coordination of any one actor. Individual systems may evolve independently of neighbouring systems and the overall system of systems. Testing a system of systems is extremely challenging because:

* Each of the member systems will have different cultures and practices 每个成员系统都有不同的文化和实践
* The system cannot be isolated or configured for a test 系统不能隔离或配置用于测试
* One of the member systems may evolve during a test 一个成员系统可能在测试期间进化
* The expected outputs for the system are not easy to define 系统的预期输出不容易定义

# 用户测试：Getting Feedback from the wild

![](/static/2021-01-26-23-34-25.png)

由于**运行验收测试的困难**，许多开发团队已经<font color="red">通过从【用户那里收集信息】的机制来增强其应用程序</font>。这些机制可以是**自动提交崩溃报告**，包括堆栈跟踪和堆状态等信息，也可以是在**应用程序中手动提交缺陷报告的设施**。 Due to the difficulty of running acceptance tests many development teams have augmented their applications with mechanisms for gathering information from their users. These can either be automated submissions of crash reports, including information like stack traces and heap state, or facilities for manually submitting defect reports from within an application

* 该方法有效地将系统的<font color="red">用户群转化为其测试人员，利用扩展效应来缓解大规模系统测试的问题</font> The approach effectively turns the user base for a system into its testers, leveraging the scaling effect to mitigate the problems of large scale system testing
* 是请注意，<font color="blue">这并不是万能的</font>。该方法取决于**是否有愿意作为（通常是无偿的）测试人员合作的用户受众**。已经在庞大的用户群中获得认可的**成熟系统将在功能的测试覆盖方面获得最大利益**。相反，**较新的系统，可能会从用户测试中受益，但缺乏受众**。此外，发布一个**不成熟的、容易出现缺陷的产品也有风险，这将阻止用户的使用**。 Note however that this is not a panacea. The approach is dependent on having an audience of users who are willing to cooperate as (often unpaid) testers. Mature systems that have already gained acceptance amongst a large user base will recieve the greatest benefit in terms of test coverage of features. Conversely, newer systems, that might benefit from user testing lack the audience. Also, there is a risk that releasing a product that is immature and prone to defects will deter user take up.

## 用户测试类别

:orange: 出于**用户测试的目的**，有两种划分用户群的方法 There are two ways of partitioning the user base for user testing purposes

* **Beta 测试**计划向系统的**一部分用户发布现有系统的新功能**。Beta-testing programmes release new features for an existing system to a sub-set of the system’s users.
  * 从**用户**的角度来看，其<font color="red">优势在于他们可以提前收到即将完成的新功能的通知。</font> The advantage from the users’ perspective is that they receive advance notice of new features that are nearing completion.
  * 从**开发团队**的角度来看，<font color="red">beta测试有助于在新功能的分阶段发布过程中控制风险。如果一个新功能包含一个未知的、但在测试阶段出现的关键缺陷，那么危害就会降到最低</font>  From the development team’s point of view, beta-testing helps to control risk during the staged release of new features. If a new feature contains a unknown, but critical defect that emerges during the beta testing phase, then harm is minimised
* **A/b 测试**可以用来**评估一个新特性的不同版本**。 A/B testing can be used to evaluate different candidates for a new feature.
  * 开发团队向用户群的不同子集发布同一功能的两个不同版本。 The development team release two different versions of the same feature to different subsets of the user base.
  * **用户可以通过特定的策略（如地理）进行划分，也可以随机进行划分**。 The users may be partitioned by a particular strategy (such as geography), or randomly.
  * 然后**收集关于用户行为的指标**，以决定这两个版本中哪个更有效。 Metrics are then gathered about user behaviour to decide which of the two variants is more effective.
    * 例如，一个在线购物应用程序的开发团队可能正在为一个新用户开发登陆页面和注册过程。一种选择是在他们开始使用该应用程序之前收集所有的信息。第二个选择是在这个阶段只收集最低限度的信息，然后在用户进行第一次购买时收集更详细的信息。选项1有可能阻止新用户的注册。选项2可能会阻止注册用户进行购买。针对不同的用户实施这两种工作流程，可以让团队决定哪种流程可以最大限度地提高销售量 For example, a development team on an online shopping application might be working on the landing page and registration process for a new user. One option is to collect all information before they start using the application. A second option is to collect only minimal information at this stage and then collect more detailed information when a user makes their first purchase. Option 1 risks deterring new users from registering. Option 2 may deter registered users from making a purchase. Implementing both workflows for different users allows the team to decide which process maximises sales
  * <font color="red">由于易于分发新特性，a b 测试通常用于 web 应用程序开发</font> Due to the ease of distributing new features, A B testing is commonly used in web application development.

# Summary

> Increasing scale in a software or computer based system reduces the coverage and effectiveness of testing efforts, even if a proportionate number of resources are applied to testing. **在软件或计算机系统中增加规模会降低测试工作的覆盖率和有效性**，即使在测试中使用了相应数量的资源