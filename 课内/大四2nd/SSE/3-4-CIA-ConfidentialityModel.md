# Content

通过框架验证项目安全性，是否达到某些性质

- 区分安全策略和构建安全软件的机制。•	Distinguish between security policies and mechanisms for building secured software.
- 定义不同的安全属性。•	Define different security properties.
- 描述什么是受保护的软件。•	Describe what protected software is.
- 生成和审查访问控制矩阵。•	Generate and review Access Control Matrices.

![](/static/2022-03-09-15-57-54.png)

* [Content](#content)
* [3政策与机制：Policy And Mechanism](#3政策与机制policy-and-mechanism)
* [安全策略，系统状态：Policies and Computer Systems](#安全策略系统状态policies-and-computer-systems)
* [有限状态表示：Finite State Representation](#有限状态表示finite-state-representation)
* [政策重要性&CIA框架:Importance of Policies](#政策重要性cia框架importance-of-policies)
* [什么决定政策：What Informs Policy](#什么决定政策what-informs-policy)
* [机密性-安全性质：Confidentiality - Security Properties](#机密性-安全性质confidentiality---security-properties)
  * [目标：Objectives of Confidentiality](#目标objectives-of-confidentiality)
* [强制机密性的机制：Confidentiality Mechanisms](#强制机密性的机制confidentiality-mechanisms)
* [Summary](#summary)
* [===================](#)
* [完整性：Integrity - Security Property](#完整性integrity---security-property)
  * [例子：Mechanism Exmaple](#例子mechanism-exmaple)
  * [检测机制：Detection Mechanisms](#检测机制detection-mechanisms)
* [可用性：Availability-Security Properties](#可用性availability-security-properties)
* [Summary](#summary-1)
* [===================](#-1)
* [威胁：Threats (Briefly)](#威胁threats-briefly)
* [政策满足：Policy Satisfaction](#政策满足policy-satisfaction)
* [可信任的机制应满足(要求): Trusting mechanisms](#可信任的机制应满足要求-trusting-mechanisms)
* [访问控制机制：Access Control Mechanisms](#访问控制机制access-control-mechanisms)
* [状态，机制&政策：Protection Software](#状态机制政策protection-software)
* [ACM访问控制矩阵：Access Control Matrix](#acm访问控制矩阵access-control-matrix)
  * [例子1](#例子1)
  * [例子2](#例子2)
* [ACM局限性-访问控制矩阵模型: Problems with ACM](#acm局限性-访问控制矩阵模型-problems-with-acm)
* [代理问题:The Deputising Problem](#代理问题the-deputising-problem)
* [Summary](#summary-2)
* [=====================](#-2)
* [4信息流：Information Flow](#4信息流information-flow)
* [信息流控制：Information Flow Control](#信息流控制information-flow-control)
* [（BLP）Bell-LaPadula Model](#blpbell-lapadula-model)
* [Summary](#summary-3)
* [=====================](#-3)
* [场景](#场景)
* [读取规则：Reading Information](#读取规则reading-information)
* [写入规则：Writing Information](#写入规则writing-information)
* [读写规则：Reading Writing Information](#读写规则reading-writing-information)
* [保护原则: Principles of Protection](#保护原则-principles-of-protection)
* [类别(集)：Categories](#类别集categories)
* [网格层次结构:The Lattice Hierarchy](#网格层次结构the-lattice-hierarchy)
* [BLP安全级别=许可+类别集：BLP Categories](#blp安全级别许可类别集blp-categories)
* [Dominate (dom) Relationship](#dominate-dom-relationship)
  * [例子](#例子)
* [读取(BLP)：Reading Information](#读取blpreading-information)
* [写入(BLP)：Writing Information](#写入blpwriting-information)
* [Summary](#summary-4)
* [=====================](#-4)
* [Exercise 1](#exercise-1)
* [Summary (Principles)](#summary-principles)
* [=====================](#-5)
* [5Integrity Properties in Software](#5integrity-properties-in-software)
* [完整性政策：Integrity Policies](#完整性政策integrity-policies)
* [完整性定义&目标：Integrity](#完整性定义目标integrity)
* [商业完整性政策：Requirements of Commercial Integrity Policies (Lipner)](#商业完整性政策requirements-of-commercial-integrity-policies-lipner)
* [操作原则-完整策略：Integrity Policy - Principles of Opeartion](#操作原则-完整策略integrity-policy---principles-of-opeartion)
* [Biba完整性级别=分类+类别集：Integrity Levels](#biba完整性级别分类类别集integrity-levels)
* [完整性分类：Classification of Integrity](#完整性分类classification-of-integrity)
* [类别集：Set Categories](#类别集set-categories)
* [完整性级别：Integrity Level](#完整性级别integrity-level)
* [Biba-主体&对象：Subjects And Objects](#biba-主体对象subjects-and-objects)
* [访问模式（访问权）：Access Modes](#访问模式访问权access-modes)
* [Summary](#summary-5)
* [=====================](#-6)
* [6Biba Policies](#6biba-policies)
* [严格完整性政策：Strict Integrity Policy](#严格完整性政策strict-integrity-policy)
  * [（读）简单完整性条件图：Simple Integrity Condition Diagram](#读简单完整性条件图simple-integrity-condition-diagram)
  * [(写)Integrity Star Property Diagram](#写integrity-star-property-diagram)
* [(读变体)主体低水标策略：Low-Watermark Policy for Subjects](#读变体主体低水标策略low-watermark-policy-for-subjects)
  * [图：Low-Watermark Policy for Subjects Diagram](#图low-watermark-policy-for-subjects-diagram)
* [(写变体)对象低水标策略：Low-Watermark Policy for Objects](#写变体对象低水标策略low-watermark-policy-for-objects)
* [低水标完整性审计策略：Low-Watermark Integrity Audit Policy](#低水标完整性审计策略low-watermark-integrity-audit-policy)
* [Ring Policy](#ring-policy)
* [Biba优缺点：Advantages And Disadvantages](#biba优缺点advantages-and-disadvantages)
* [Summary](#summary-6)
* [=====================](#-7)
* [Tutorial](#tutorial)
* [ACM](#acm)
* [2](#2)
* [3](#3)
* [loose/strict model](#loosestrict-model)

# 3政策与机制：Policy And Mechanism

- 政策以及其机制是安全软件工程的关键方面。Policies and in turn, their mechanisms are critical aspects to engineering secured software.
- **政策**是关于什么是允许的，什么是不允许的**声明**。A policy is a statement on what is, and is not permitted.
- **机制**是一种方法、工具或程序，用于**执行政策**。A mechanism is a method, tool or procedure that is enacted to enforce a policy.
- 例子
  - CPU执行，用户和系统分层（policy），mechanism确保用户系统分离，policy能被满足

# 安全策略，系统状态：Policies and Computer Systems

- 一个计算机系统可以被表示为一个有限状态机。A computer system can be represented as a finite-state machine.
  - 系统可能处于的每个状态都被表示为一个节点。Each possible state the system can be in is represented as a node.
  - 一系列可以在不同状态之间转换系统的行动连接着这些节点。A series of actions that can transform the system between states connect the nodes.
- **安全策略**反过来将**系统的不同状态归类为被认为是授权的和安全的，或未授权的和不安全的集合**。Security policy in turn categorizes different states of the system into sets that are considered authorized and secure, or unauthorized and nonsecure.
- **安全系统**从**授权状态开始，不能进入未授权状态**。Secure systems start in an authorized state and cannot enter an unauthorized state.

# 有限状态表示：Finite State Representation

![](/static/2022-03-09-16-12-31.png)

* 一个安全的系统，从任意一个状态开始（通常从一个授权的状态）都不可能进入未授权状态
* 但不一定是坏事，很难达到100%安全

# 政策重要性&CIA框架:Importance of Policies

- 主要是为了**确保CIA的安全属性(high level**)。Mainly to ensure CIA security properties:
  - CIA框架涵盖三个概念。
    - **保密性**：信息的隐蔽性。Confidentiality: the concealment of information.
    - **完整性**：信息的可信度。Integrity: the trustworthiness of information.
    - **可用性**：使用或获取信息的能力。Availability: the capacity to use, or acquire information.
- 这些是**安全软件的3个主要成分**。These are the 3 main ingredients of secured software.
  - 即，- 这些概念共同构成了一个 "安全 "系统。•	These concepts together make up a ‘secure’ system.

---

如何实现上面特定的一个CIA概念(属性)？

- 用**政策规定**。•	Specified with policies.
- 用**机制实现**。•	Implemented with mechanisms.
  - 注意要机制本身要正确实现，不然系统仍存在缺陷

# 什么决定政策：What Informs Policy

- **商业目标**。Business objectives.
- **最终用户的安全和隐私偏好**。End user security and privacy preferences.
  - 比如安卓，决定app授权，访问文件，wifi access等
- **标准和法规的权威机构**。Authority bodies for standards and regulations:
  - 国际信息安全管理标准，ISO/IEC。International Information security management standard, ISO/IEC.
  - 电气和电子工程师协会标准协会(IEEE-SA)Institute of Electrical and Electronic Engineers Standards Association (IEEE-SA)
  - 众多的隐私监管机构（如GDPR。CCPA等）。Numerous regulatory jurisdictions for privacy (such as GDPR. CCPA etc.)
- **共同商定的原则**。Commonly agreed on principles:
  - 信息处理原则（如FTC FPPA，PIPPA，OCED等）Information processing principles (such as FTC FPPA, PIPPA, OCED etc.)

# 机密性-安全性质：Confidentiality - Security Properties

![](/static/2022-03-09-16-27-11.png)

Definition. Let 𝑈 be a set of entities and let 𝜓 be some information. Then 𝜓 has the property of confidentiality with respect to 𝑈 if no member of 𝑈 can obtain information about 𝜓. 定义。设𝑈是一个实体集合，设𝜓是一些信息。如果𝑈的任何成员都不能获得关于𝜓的信息，那么𝜓对于𝑈来说就具有保密的特性。

* 阻止未授权行为获取信息

## 目标：Objectives of Confidentiality

1.	保护信息不被未经授权的人访问。Protecting information from being accessed by unauthorized parties.
- 只有获得合法授权的个人才被允许访问信息。Only individuals with the legitimate authorisation are permitted to access information.
- 也可以认为是 "需要知道 "的基础。Can also be thought of ‘need to know’ basis.

![](/static/2022-03-09-16-33-57.png)

* map principal `p` to one of these states
  * state of
    * knowledge
      * 授权的map到这
    * uncertainty

---

2.	保护某人的理解的存在。Protecting the existence of someone’s understanding.
- 在封闭式投票系统中，知道一个选项相对于另一个选项的得票率并不重要。In closed voting systems, knowing the ratio of votes for one option as opposed to the other is not important.
- 重要的是确保特定个人的投票方式不为人知。What is important is ensuring the way specific individuals voted remains unknown.

![](/static/2022-03-09-16-38-08.png)

* **其他人知道的事实**，`φ` others' understanding
  * 我知道其他人知道的事实 `Kφ`
  * 不知道 `not Kφ`
* 机密性目标，保证软件中，P能映射到其中一种状态
  * 授权的 -> `Kφ`
  * 非授权的 -> `not Kφ`

# 强制机密性的机制：Confidentiality Mechanisms

- **密码学**。Cryptography.
- **访问控制**.Access Control.
- 差异性方法.Differential Methods.
- 选择性披露。Selective Disclosure.
- 等等。

# Summary

- 政策规定了在确保系统安全时应该和不应该发生什么。Policies dictate what should and shouldn’t happen when ensuring the security of systems.
- 政策规定了规则。Policies dictate the rules.
- 机制强制执行这些规则。Mechanisms enforce the rules.
- 机制并不总是与软件和工程联系在一起。Mechanisms are not always tied to software and engineering.
  - 比如学校里的一些规定
- 保密性是机制和政策旨在维护的CIA属性之一。Confidentiality is one CIA property that mechanisms and policy aim to uphold.

# ===================

# 完整性：Integrity - Security Property

定义。设𝑈是一个实体集合，设𝜓是某种信息或资源。如果𝑈的所有成员都信任𝜓，那么𝜓对于𝑈具有完整性的属性。Definition. Let 𝑈 be a set of entities and let 𝜓 be some information or a resource. Then 𝜓 has the property of 𝑖𝑛𝑡𝑒𝑔𝑟𝑖𝑡𝑦 with respect to 𝑈 if all members of 𝑈 trust 𝜓.

---

![](/static/2022-03-09-20-24-43.png)

- **可信度**与防止不正当或未经授权的改变有关。Trust has to do with preventing improper or unauthorized change:
  - **数据的完整性**--信息的内容。Data integrity – the content of information.
  - **源头完整性**--信息的来源。 Origin integrity – the source of information.
- 预防机制，**通过以下方式维护数据的完整性**。Prevention mechanisms, maintain the integrity of data by:
    1.	**阻止未经授权的用户试图改变/访问它**。Blocking unauthorized users attempts to change/access it.
    2.	**阻止【授权用户以未经授权的方式】修改数据**。Blocking authorized users modifying data in unauthorized ways.

## 例子：Mechanism Exmaple

![](/static/2022-03-09-20-27-47.png)

## 检测机制：Detection Mechanisms

![](/static/2022-03-09-20-39-00.png)

- 这样的机制并不试图防止违反完整性。Such mechanisms do not try and prevent integrity violations.
- 它们所做的只是**报告某物的完整性不再得到保证**。All they do is report that the integrity of something is no longer assured.
- **可供鉴证的软件设计**。Forensic-ready software design:
  * **分析系统事件（用户或系统行为）以检测问题**，或分析**数据本身**以查看所需的约束是否成立。Analysis of system events (user or system actions) to detect problems or analyse data itself to see if required constraints hold.
  * 机制可以报告**具体**的问题，也可以**简单地报告**文件被破坏。Mechanisms may report on specific problems, or they may simply report that the file is corrupt.

# 可用性：Availability-Security Properties

![](/static/2022-03-09-20-42-21.png)

定义。设𝑈是一个实体集合，设𝜓是一个资源。如果𝑈的所有成员都能访问𝜓，那么𝜓相对于𝑈来说就具有可用性的属性。Definition. Let 𝑈 be a set of entities and let 𝜓 be a resource. Then 𝜓 has the property of 𝑎𝑣𝑎𝑖𝑙𝑎𝑏𝑖𝑙𝑖𝑡𝑦 with respect to 𝑈 if all members of 𝑈 can access 𝜓.

* 比如验证系统关联到db的可用

---

- 软件设计中需要考虑的**重要方面**。Important aspect to consider in the design of software. 
- **没有可用的资源和没有资源一样糟糕**。No available resources are as bad as no resources at all
- **拒绝服务**是一种针对可用性的常见攻击。Denial of service is a common attack that targets availability.
- 保证机制。Assurance Mechanisms.
  * 通常表现为一些**网络监控**解决方案。Typically manifest as some network monitoring solution. 
  * **统计模型**。Statistical models:
    * (确定不寻常的访问模式是否表明是故意操纵的)(Determine if the unusual access patterns suggest deliberate manipulation)

# Summary

- CIA是一个安全框架，在确定一个软件的安全程度时，囊括了三个关键的关注点。CIA is a security framework that encapsulates three key concerns when determining how secure a software is.
  - 保密性 - 对未经授权的用户来说，数据是模糊的。Confidentiality – the obfuscation of data to unauthorised users.
  - 完整性--保证数据的**可信度**。Integrity – the assurance of trustworthiness of data.
  - 可用性--数据对人和服务的持续可及性。Availability – the continued accessibility of data to people and services.
- 政策规定了一个软件系统中什么是允许的，什么是不允许的。Policies specify what is and what is not permissible on a software system.
  - 它本质上是一本规则手册。It is a rulebook essentially.
- 机制在软件中执行政策。Mechanisms enforce the policies in software.
  - 为了保障我们迄今为止所讨论的属性。To safeguard the properties that we have discussed thus far.

# ===================

# 威胁：Threats (Briefly)

- **威胁是一种脆弱的软件状态，使系统不安全**。A threat is a vulnerable software state that makes the system insecure.
  - 一个脆弱的状态可能会导致违反安全/隐私政策。A vulnerable state may result in the violation of security/privacy policy.
  - **攻击是**一组将**系统的状态转移到脆弱状态**的行动。An attack is a set of actions that moves the state of a system to a vulnerable one.
  - 安全的软件工程试图**减轻甚至防止攻击的发生**。Secured Software Engineering attempts to mitigate or even prevent attacks from occurring.
- 我们将在本课程的后期对威胁进行全面的讲授。We will have a full lecture on threats later in the course.

# 政策满足：Policy Satisfaction

![](/static/2022-03-09-20-52-46.png)

- 所选机制是否符合政策？Does a chosen mechanism satisfy a policy?
- 可以通过分析来研究这个问题。Can investigate this by analysing:
    1.	**源代码**（静态分析）Source code (static analysis)
    2.	**软件设计**（静态分析可以适用于抽象的系统表示）。Software design (static analysis can apply to abstract system representations)
    3.	已部署的应用（反射模型/动态分析）。Deployed application (reflection models/dynamic analysis)

---

- 政策规定有什么允许不允许的状态，机制防止系统从安全状态转移到非安全状态，**基于两个基本假设**。Based on two fundamental assumptions:

1.	政策被适当地指定。The policy is properly specified.
2.	机制被正确执行。The mechanisms are correctly implemented.

- **对于政策**。For the policy:

1.	它必须是**正确和不含糊的**。It must be correct and unambiguous.
2.	具体说明**哪些状态是允许的，哪些是不允许的**。Specific about what states are permissible and which aren’t.

- **对于机制**。For the mechanism:

1.	在执行中必须是没有错误的。Must be error free in the implementation.

---

- 机制被认为是安全、精确或广泛的。Mechanisms are considered either secure, precise or broad.

![](/static/2022-03-09-21-07-07.png)

定义

- 如果𝑅⊆𝑄，一个安全机制是**安全的**。A security mechanism is secure if 𝑅 ⊆ 𝑄.
  - R为Q子集或=Q
- 如果𝑅 = 𝑄，一个安全机制是**精确的**。A security mechanism Is precise if 𝑅 = 𝑄.
- 如果𝑟 ∈ 𝑅 和 r ∉ 𝑄 ，则安全机制是**广泛的**。A security mechanism is broad if 𝑟 ∈ 𝑅 and r ∉ 𝑄.

---

- **理想情况下，**当你实现了所有的安全机制后，你会有一个**精确的机制，即𝑅 = 𝑄** Ideally, when you have implemented all your security mechanisms you will have a precise mechanism, i.e. 𝑅 = 𝑄
  - 这通常是不现实的，**大多数安全机制是宽泛的**。他们确实允许系统进入非安全状态。This is often not realistic, and most security mechanisms are broad. They do allow the system to enter nonsecure states.
- **非安全状态并不总是意味着坏事会发生。它只是表明有风险的因素**。Non secure states do not always mean bad things will happen. It merely indicates an element of risk.
  - 大多数为broad,并不意味着一定是坏事。但我们希望能够理解broad机制存在的情况下，所关联的风险

---

# 可信任的机制应满足(要求): Trusting mechanisms

- **可信机制的工作需要一些假设**。Trusting that mechanisms work requires some assumptions:
  - **每个机制都是为了实现安全策略的一个或多个部分**。Each mechanism is designed to implement one or more parts of the security policy.
  - 它们结合在一起以满足整个政策。They combine to satisfy the policy as a whole.
  - 这些机制应该被正确地实施。The mechanisms should be implemented correctly.
  - 机制应该被正确地安装和管理 The mechanisms should be installed and administered correctly
  - 也就是说，使用这些机制的人应该知道如何正确使用它们。i.e. humans that use such mechanisms should be aware of how to use them properly.

# 访问控制机制：Access Control Mechanisms

"旨在检测和拒绝未经授权的访问，并允许对信息系统的授权访问"。“Aims to detect and deny unauthorized access and permit authorized access to an information system”.

- **有两种基本的访问控制方式**。There are two fundamental flavours of access control:
    - 1.	**自由裁量**。Discretionary:
      - 个人用户控制ACL以允许或拒绝对某物的访问。Individual users control ACLs to permit or deny access to something.
      - **信息的拥有者可以决定如何控制它**。Owners of information are left to decide how to control it.
      - DAC
    - 2.	**强制性的**。Mandatory:
      - 通常在操作系统中讨论。**系统控制谁**可以访问数据。Commonly discussed in operating systems. System controls who has access to data.
      - 对象访问的规则**不是由用户控制**的。Rules for object access are not controlled by users.
- **有许多访问控制的变体，其中大多数都以某种方式与自由裁量或强制方法有关**。There are numerous variants of access control, most of them relate to either discretionary or mandatory approaches in one way or another.

# 状态，机制&政策：Protection Software

- **考虑所有可能状态的集合𝑃**。Consider the set of all possible states 𝑃
- **𝑃的某个子集𝑄**正好由系统被授权驻留的那些状态组成。Some subset 𝑄 of 𝑃 consists of exactly those states in which the system is authorized to reside.
  - 如果系统状态**在𝑄中，系统是安全的**。If the system state is in 𝑄, the system is secure.
  - 当当前状态处于**𝑃 - 𝑄时，系统不安全**。When the current state is in 𝑃 − 𝑄, the system is not secure.
- **安全策略定义了哪些状态是𝑄**。Security policies define what states define 𝑄.
- **机制试图阻止系统进入𝑃 - 𝑄的状态**。Mechanisms try to prevent the system from entering a state in 𝑃 − 𝑄
  - ACM机制

# ACM访问控制矩阵：Access Control Matrix

- **访问控制矩阵模型是一个用于描述保护状态的精确模型**。The access control matrix model is a precise model used to describe a protection state.
  - 它**描述了每个主体的权利**。It characterises the rights of each subject.
    - 主体是一个活跃的实体，如一个进程、一个用户等。A subject is an active entity, such as a process, a user etc.
  - **决定了一个主体对其他对象有什么权利**。Determines what rights a subject has with other objects.
    - 一个对象可以是任何东西，一个资源，基本上是一个实体。An object can be anything, a resource, an entity essentially.
  - 一种**自由裁量的访问控制的形式**。A form of discretionary access control.

---

![](/static/2022-03-09-21-44-54.png)

- **所有对象的集合 𝑂** Set of all objects 𝑂
  - **所有受保护实体的集合**（那些与系统的保护状态有关的实体）。The set of all protected entities (those that are relevant to the protection state of the system)
- **所有主体的集合 𝑆** Set of all subjects 𝑆
  - **活动对象**的集合，如进程和用户。The set of active objects, such as processes and users.
- **所有权限的集合 𝑅** Set of all rights 𝑅
  - **对对象允许或不允许的行动/操作**。Actions/operations that are either allowed or disallowed on objects.

## 例子1

![](/static/2022-03-09-22-00-27.png)

- **能力**列表是矩阵中的一行 Capability list is a row in the matrix
  - **描述了某人可以对其他事物做什么(某人对系统中其他东西，能做什么**)。Describes what someone can do to other things.
- ACL是矩阵中的一列 ACL is a column in the matrix
  - **描述某物上可以有某人做什么（同一种资源，其他人可以做什么**） Describes what can be done to something by someone
- M1,M1
  - cyclic privileges on itself, have the ability to read or wrtie on itself

## 例子2

![](/static/2022-03-09-22-01-49.png)

# ACM局限性-访问控制矩阵模型: Problems with ACM

1.**信息禁锢问题**。Information Confinement Problem:

* **无法确定是否存在任何机制，使【被授权】访问一个对象的主体【可能将该对象中包含的信息泄露】给其他【未被授权】访问该对象的主体**。It is impossible to determine whether there is any mechanism by which a subject authorized to access an object may leak information contained in that object to some other subject not authorized to access that object.

2.**信息敏感度问题**。Information Sensitivity Problem:

* **ACM模型从【未考虑过正在被访问的信息的敏感性】。它不能根据访问不敏感与高度敏感信息的后果来做出决定**。The sensitivity of information that is being accessed is never considered by the ACM model. It cannot make decisions based on the consequence of accessing insensitive vs highly sensitive information.

# 代理问题:The Deputising Problem

![](/static/2022-03-09-22-11-08.png)

- 两个资源（编译器和计费信息文件）。Two resources (compiler and billing info file)
- 编译器可以写到计费文件中。Compiler can write to the billing file.
- Alice可以用一个调试文件名来调用编译器。Alice can invoke the compiler with a debug filename.
- Alice不允许写到计费信息。Alice is not allowed to write to billing info.
- 如果用ACM可能只能关注是否有权限，不能关注到context
  - 所以通常想利用更复杂的模型

![](/static/2022-03-09-22-13-53.png)

- 编译器是代表爱丽丝行事的代理人。Compiler is the deputy acting on behalf of Alice.
- 编译者将自己的权利与爱丽丝的权利混淆了。Compiler has confused its rights with Alice’s.
- **ACL没有保持权力和预期目的之间的联系**。ACL does not maintain association between authority and intended purpose.

# Summary

- 政策规定了可允许的状态，机制尝试并执行政策。Policies specify states that are permissible, mechanisms try and enforce policies.
- 多个机制被用来满足一个政策。Multiple mechanisms are used to satisfy a policy.
- CIA模型提供了三个与安全软件工程相关的安全属性。CIA model provides three security properties that are relevant to Secured Software Engineering.
  - 保密性。
  - 完整性。
  - 可用性。
- 自由裁量访问控制DAC和强制访问控制是访问控制的两种主要形式。Discretionary access control and mandatory access control are the two main forms of access control.
- **访问控制矩阵是一种基于DAC的基本访问控制机制**。Access control matrix is a basic access control mechanism based on DAC.

# =====================

![](/static/2022-03-09-22-22-25.png)
![](/static/2022-03-09-22-23-30.png)

- 能够使用Bell-LaPadula（BLP）模型指定保密政策。
- 理解如何用类别来增强保密性政策。
- 展示如何利用保密性政策实现信息流控制。

# 4信息流：Information Flow

![](/static/2022-03-09-23-23-18.png)

* 信息转换流 - 对象序列：o1,...,on+1
* 并且对应的主体序列 s1..sn满足
  * si `r` oi
    * 当 si `r` oi 信息从oi流向si
  * si `w` oi+1 ∀ i, 1 ≤ i ≤ n
    * 信息从si流向oi+1

# 信息流控制：Information Flow Control

- 信息流控制**涉及到信息如何从一个对象传播或扩散到另一个对象**。Information flow control is concerned with how information is disseminated or propagated from one object to another.
- 一个信息流政策（**保密性政策**）。An information flow policy (confidentiality policy):
  - 防止未经授权的信息披露。Prevents the unauthorized disclosure of information.
  - 未经授权的信息更改是次要的。Unauthorized alteration of information is secondary.
  - 多层次安全模型是最著名的例子。Multi level security models are best-known examples:
    - 贝尔-拉帕杜拉模型是许多，或大部分的基础。Bell-LaPadula Model basis for many, or most of these.
      - 不关心 信息完整性

# （BLP）Bell-LaPadula Model

- 一种信息保密模型。An information confidentiality model.
- 结合了**强制性（安全级别）和DAC自由决定访问控制（需要许可**）。Combines mandatory (security levels) and discretionary (permission required)
- 开发于20世纪70年代 Developed in the 1970s

---

1. Arrange **security levels** in linear order. 按线性顺序排列**安全级别**

![](/static/2022-03-09-23-58-43.png)

- 安全级别用于区分以下内容。•	Security levels are used to distinguish the following:
  - 文件上的重要程度。•	Levels of importance on a document.
  - 一个用户（被称为主体）所拥有的访问级别。•	Levels of access held by a user (referred to as a subject).
    - 我们可以使用安全级别本身来确定一个主体是否可以访问一个文档。•	We can use security levels on their own to determine whether a subject can access a document or not.

2. 具体说明哪些主体有什么**安全许可𝐿**(𝑠) Specify which subjects have what security clearance 𝐿(𝑠)
3. 明确哪些对象有什么**安全分类** 𝐿(𝑜) Specify which objects have what security classification 𝐿(𝑜)

![](/static/2022-03-10-00-26-38.png)

---

general representation

![](/static/2022-03-10-00-31-28.png)

* 为什么对于高于i层，能进行写而不是读
  * 因为不在乎 完整性，只关注机密性

# Summary

- **Bell-LaPadula使我们能够解决ACM的不足之处**。Bell-LaPadula allows us to address the shortcomings of ACM:
  - 信息敏感。Information sensitivity.
  - 信息禁锢。Information confinement.
- 信息流路径涉及**信息如何在主体和客体之间传播**。Information flow paths concern how info propagates between subjects and objects.
  - 通过读和写的动作。Through read and write actions.
  - 具体的行动通知了流动的方向。The specific action informs the direction of flow.
    - read,write
- **BLP在线性安全模型上规定了文件的安全敏感性** BLP specifies the security sensitivity of files on linear security model.
  - 级别越高，数据就越敏感。The higher the clearance, the more sensitive the data.

# =====================

# 场景

一家公司正在购买新的软件，但他们担心在互联网上存在潜在的漏洞。哪种设计方案最适用于解决这个问题？A company is purchasing new software, but they are concerned about potential vulnerabilities over the internet. Which design solution is most applicable to solving the problem?

1. 该软件将用AES加密进行通信。The software will communicate with AES encryption.
2. 用差异化的方法混淆他们的数据库。Obfuscate their database with differential methods.
3. 为所有的软件通信实施一个REST API。Implement a REST API for all software communication.
4. 该软件不会与任何网络系统进行通信 The software will not communicate with any networked systems

不清楚该软件的context

# 读取规则：Reading Information

“read ups" are not allowed, "read downs" are allowed 不允许“向上读”，允许“向下读” ( **模型是基于mandatory access control mechanisms，主体基于discretionary mechanisms** ）

1. **Simple Security Property**

* **如果𝐿(𝑜)≤𝐿(𝑠)，并且𝑠有权限读取𝑜，则主体𝑠可以读取对象𝑜**。Subject 𝑠 can read object 𝑜 iff 𝐿(𝑜) ≤ 𝐿(𝑠) and 𝑠 has permission to read 𝑜
  * read downs permission
  * lateral read permission
* 结合后面的dom关系就是扩展成
  * iff L(o)≤𝐿(𝑠), and C(o) ⊆ C(s)

**阻止主体读取更高层次的物体**（禁止向上读取规则）Prevents subjects from reading objects at higher levels (**no read up rule**)

# 写入规则：Writing Information

“write ups” allowed, “write downs” disallowed

1. star Property (*property)

* **如果𝐿(𝑠)≤𝐿(𝑜)并且𝑠有权限写入𝑜，则主体𝑠可以写入对象𝑜**。Subject 𝑠 can write object 𝑜 iff 𝐿(𝑠) ≤ 𝐿(𝑜) and 𝑠 has permission to write 𝑜
* 防止主体往较低层次写入对象（没有写下规则）Prevents subjects from writing objects at lower levels (no write down rule)
  * 向下写情况，下面层级系统的主体可能能访问到上层的机密性文件
* 结合后面的dom关系就是扩展成
  * iff L(s)≤𝐿(o), and C(s) ⊆ C(o)

# 读写规则：Reading Writing Information

Read/write up and down is not allowed.

Strong Star Property (Strong *property)

![](/static/2022-03-10-02-55-00.png)

* **如果𝐿(𝑠)= 𝐿(𝑜)，并且𝑠有权限写入𝑜，则主体s可以写入对象𝑜**。Subject 𝑠 can write object 𝑜 iff 𝐿(𝑠) = 𝐿(𝑜) and 𝑠 has permission to write 𝑜
* **防止主体读写更高或更低层次的对象**（没有向上/向下读/写规则）Prevents subjects from reading and writing objects at higher or lower levels (no read/write up/down rule)

# 保护原则: Principles of Protection

- **指导原则--最小特权原则**。Guiding principle – principle of least privilege.
  - **主体应该在一个对象上被赋予【足够的特权】来完成他们的任务**。Subjects should be given just enough privileges on an object to perform their tasks.
- **需要知道的原则**。Need to know principle.
  - **主体应该只能够访问那些目前需要完成其工作的对象**。Subjects should only be able to access those objects that are currently required to complete their jobs.
- 这两个原则都可以用**类别**来实现。Both principles can be implemented using categories.

# 类别(集)：Categories

- 也被称为**隔间**。Also known as compartments.
- 典型的军事安全类别。Typical military security categories:
  - 陆军、海军、空军 Army, navy air force
- 典型的商业安全类别。Typical commercial security categories:
  - 销售研发、人力资源 Sales R&D, HR

# 网格层次结构:The Lattice Hierarchy

![](/static/2022-03-10-14-56-59.png)

- 类别在运算⊆（子集）下形成一个格子。Categroies form a lattice under the operation ⊆ (subset of)
- None
  - low level, might have access to initial documents

# BLP安全级别=许可+类别集：BLP Categories

secret level情况下，还需要考虑catrgories (what category applicable to an object or a document)

- **扩大安全级别的概念，为每个安全分类增加类别**。Expand notion of **security level** to add **categories** to each security classification.
  - 安全级别用于区分以下内容。•	Security levels are used to distinguish the following:
    - 文件上的重要程度。•	Levels of importance on a document.
    - 一个用户（被称为主体）所拥有的访问级别。•	Levels of access held by a user (referred to as a subject).
      - 我们可以使用安全级别本身来确定一个主体是否可以访问一个文档。•	We can use security levels on their own to determine whether a subject can access a document or not.
- 鉴于NUC、EUR、US的类别。Given categories NUC, EUR, US:
  - 基于 "需要知道 "的原则，人们可以接触到其中的任何内容。Based on “need to know” principle one can have access to any of these:
  - None, {NUC}, {EUR}, {US}, {NUC, EUR} … {NUC, EUR, US}
- 安全级别是（许可，类别集）。**Security level** is (**clearance<level of privilege**>, **category set<what aspects of business do they belong to**>) (结合这两个概念，，感觉就是标签+类别集= =，，<font color="deeppink">L=(C, S)，C是clearance/classification(也就是机密性标签,下面3个), S是set of categories</font>。。后面那个Biba一样的就是概念用的名字可以不一样,Biba-C是classification指完整性分类)
  - 最高机密，{NUC, EUR, ASI}。`Top Secret, {NUC, EUR, ASI}`
  - 机密，{EUR, ASI}。`Confidential, {EUR, ASI}`
  - 秘密，{NUC，ASI}。`Secret, {NUC, ASI}`
  - 根据这种notion，可以表示出domination relationship

# Dominate (dom) Relationship

- 捕获**安全分类(许可)和类别集**的组合。Captures the combination of **security classification and category set**.
  - 消除combination of sensitivity levels & category sets. 然后根据一系列操作，决定who is access to what and in what capacity

![](/static/2022-03-10-15-01-52.png)

* 如果某sensitivity level不dominate其他level的object，可能就不具有读的权利

## 例子

![](/static/2022-03-10-15-09-06.png)

# 读取(BLP)：Reading Information

![](/static/2022-03-10-15-51-30.png)

* 即当且仅当
  * L(o)<=L(s), s has permission to read o
  * **s dom o**
    * iff o<=s (security classification/level), and C(o) ⊆ C(s)

---

例子

![](/static/2022-03-10-15-53-14.png)

confinement 问题--假设保罗被批准进入安全级别(Secret, {EUR, US, NUC})，并对DocB有酌情读取的权限。保罗可以读取DocB；他可以将其内容复制到DocA并相应地设置其访问权限，然后乔治可以读取DocB。

* BLP禁止write down，防止其他人read up (what should not able to)

# 写入(BLP)：Writing Information

![](/static/2022-03-10-16-06-16.png)

---

解决前面问题

![](/static/2022-03-10-16-10-41.png)

- 假设保罗被批准进入安全级别(Secret, {EUR, US, NUC})，并拥有对DocB的自由读取权限。Suppose Paul is cleared into security level (Secret, {EUR, US, NUC}) and has discretionary read access to DocB.
- 因为DocA dom Paul是禁止的，所以Paul不能写到DocA（先前Paul复制了DocB的内容并写到DocA，所以这防止George阅读DocB）。Because DocA dom Paul is false, Paul cannot write to DocA (preventing George from reading DocB due to Paul copying its content and writing to DocA)

# Summary

- **安全级别**允许我们表明一个主体有什么特权，或者对一个对象需要什么级别的访问。Security levels allow us to indicate what privilege a subject has, or level of access is required for an object.
- **类别集**允许我们根据类别集的成员资格来表明谁应该访问什么。Category sets allow us to indicate who should have access to what depending on membership of category sets
  - 与安全级别无关。Irrespective of security levels.
- **支配权是我们整合安全级别和类别集的一种方式，以检查一个主体对一个对象的访问权，基于两个一般规则**。Dominance is a way for us to consolidate security levels and category sets to check the access right a subject has with an object based on two general rules:
  - 如果一个主体支配着一个对象，它就可以阅读它。If a subject dominates an object, it can read it.
  - 如果一个主体被一个对象所支配，它就可以对其进行写入。If a subject is dominated by an object, it can write to it.
- 这就解决了ACM的限制性和敏感性问题。This solves the confinement and sensitivity problems of ACMs

# =====================

# Exercise 1

Identify all possible ways that users can interact with objects to maintain confidentiality based on the simple security property.

* **如果𝐿(𝑜)≤𝐿(𝑠)，并且𝑠有权限读取𝑜，则主体𝑠可以读取对象𝑜**。Subject 𝑠 can read object 𝑜 iff 𝐿(𝑜) ≤ 𝐿(𝑠) and 𝑠 has permission to read 𝑜
  * read downs permission
  * lateral read permission

# Summary (Principles)

- BLP模式使我们能够解决信息禁锢和信息敏感性的表达问题。BLP model allows us to address the issue of information confinement and the expression of information sensitivity.
- BLP在3个原则上运作。BLP operates on 3 principles.
  - 简单安全原则，这涉及到阅读权限。Simple, which concerns read permissions.
  - `*`，涉及写权限。* which concerns write permissions.
  - 强*，涉及读和写的权限。`Strong *` which concerns read and write permissions.
- 保密性政策通常不关心完整性。Confidentiality policies typically do not care about integrity.
  - 强*是一个例外。The strong * is an exception to this.
- **分类和类别**通常被用来确定用户对哪些对象有什么权限。Classifications(security clearance) and categories are typically used to determine what permissions users have with what objects.
  - 支配性检查涉及检查分类和类别的成员资格 Dominance checking concerns checking classification and category memberships

# =====================

# 5Integrity Properties in Software

前面BLP关注机密性，read access，Biba关注完整性（文档可信度），write access

- 能够使用Biba模型指定完整性策略。Be able to specify integrity policies using Biba Model.
- 理解元素之间基于其完整性等级的关系。Understand the relationship between elements based on their integrity levels.
- 展示如何使用完整性策略实现信息流控制。Demonstrate how information flow control can be achieved using integrity policy.

# 完整性政策：Integrity Policies

没有绝对观点，完整性是否优先于机密性，取决于上下文

- 要求。Requirements:
  - 与保密性政策不同。Different from confidentiality policies.
- Biba模型。Biba models:
  - 低水印政策。Low-Water-Mark policy.
  - 环形政策。Ring policy.
  - 严格完整性政策。Strict integrity policy.

# 完整性定义&目标：Integrity

- 完整性是**指数据或资源的可信度**。Integrity refers to the trustworthiness of data or resources.
- **完整性通常是指防止对数据进行不正当或未经授权的改变**。Integrity is usually defined in terms of preventing improper or unauthorised change to data.

有三个主要目标。Three main goals:

1. **防止【未经授权的用户】对数据或程序进行修改**。Preventing unauthorized users from making modifications to data or programs.
2. **防止【授权用户】进行不正当或未经授权的修改**。Preventing authorized users from making improper or unauthorized modifications.
3. **保持数据和程序的内部和外部一致性**Maintaining internal and external consistency of data and programs

# 商业完整性政策：Requirements of Commercial Integrity Policies (Lipner)

1. 用户不会编写自己的程序，而是使用现有的生产程序和数据库。Users will not write their own programs but will use existing production programs and databases.
2. 程序员将在一个非生产系统上开发和测试程序；如果他们需要访问实际数据，他们将通过一个特殊的程序获得生产数据，但将在自己的开发系统上使用。Programmers will develop and test programs on a non-production system; If they need access to actual data, they will be given production data via a special process but will use it on their own development systems.
3. 必须遵循一个特殊的过程，将程序从开发系统安装到生产系统上。A special process must be followed to install a program from the development systems onto the production system.
4. 要求3中的特殊过程必须被控制和审计。The special process in requirement 3 must be controlled and audited.
5. 管理人员和审计人员必须能够接触到系统状态和生成的系统日志。The managers and auditors must have access to both the system state and the system logs that are generated.

# 操作原则-完整策略：Integrity Policy - Principles of Opeartion

- 有两个操作原则。There are two principles of operation:

* 业务分离：不应允许单个人执行一项关键功能的所有步骤。Separation of Duty: Single person should not be allowed to carry out all steps of a critical function.
  - 将一个程序从开发中转移到生产中。Moving a program from Development to production.
  - 一个程序的开发者和认证者（安装者）Developer and Certifier (installer) of a program
  - 授权支票和兑现支票。Authorizing checks and cashing them.
* 函数分离。Separation of function:
  - **不要在开发系统上处理生产数据**。Do not process production data on a development system.

# Biba完整性级别=分类+类别集：Integrity Levels

- **完整性等级**由标签定义，由两部分组成。Integrity levels are defined by labels, consisting of two parts:
  - 一个**分类**。A classification.
  - 一组**类别集**。A set of categories.
- 完整性等级被赋予系统中的主体和对象。Integrity levels are given to the subjects and objects in the systems.
- **完整性标签规定了可以放在数据中的信任程度** Integrity labels specify the degree of confidence that may be placed in the data.

# 完整性分类：Classification of Integrity

- 一个**分类**是一个**分层元素集的元素**。A classification is an element of hierarchical set of elements.
- 它由三个要素组成。It consists of three elements: （不是硬性的定义，只是通用标签）
  - **关键的** Crucial
  - **非常重要** Very important
  - **重要的** Important
- 元素的关系如下。The relationship of elements is as follows:
  - C > VI > I

# 类别集：Set Categories

- **标签中包含的【类别集合】将【是系统中所有集合的主体**】。The set of categories contained in the label will be a subject of all the sets in the system.
- **类别集的分类是不分等级的**。The classification of the set of categories is non-hierarchical.

---

例子

![](/static/2022-03-11-12-17-30.png)

- 在这种情况下，X≥Y（X dom Y），因为Y是X的一个子集。In this case X ≥ Y (X dominates Y), because Y is a subset of X.
- 如果有一个包含`{Detroit, Chicago, Miami}`的第三个区间Z。在这种情况下，隔间Z和X是不可比的，因为集合的第三个元素是不同的。If there were to be a third compartment Z containing {}. Compartment Z and X in this case are non-comparable because the third element of the set is different.

# 完整性级别：Integrity Level

- 完整性等级由**标签定义**，由两部分组成。Integrity levels are defined by labels, consisting of two parts:
  - 一个**分类**。A classification.
  - 一组**类别集**。A set of categories.
- 完整性等级被赋予系统中的主体和对象。Integrity levels are given to the subjects and objects in the systems.
- **完整性标签规定了可以放在数据中的信任程度** Integrity labels specify the degree of confidence that may be placed in the data.

---

- 每个**完整性等级将被表示为𝐿=（𝐶，𝑆**），其中。Each integrity level will be represented as 𝐿 = (𝐶, 𝑆) where:
  * 𝐿是完整性等级(标签。𝐿 is the integrity level.
  * **𝐶是分类**。𝐶 is the classification.
  * **𝑆是类别集**。𝑆 is the set of categories.
- 然后，**完整性级别形成支配关系**。The integrity of levels then form a dominance relationship.
  - ![](/static/2022-03-11-12-21-11.png)

# Biba-主体&对象：Subjects And Objects

- 像其他模型一样，Biba模型支持**对主体和客体的访问控制**。Like other models, the Biba model supports the access control of both subjects and objects.
- **主体**是系统中可以访问信息的主动元素（代表用户行事的过程）。Subjects are the active elements in the system that can access information (process acting on behalf of the users).
- **对象**是可以请求访问的被动系统元素（文件、程序等）。Objects are the passive system elements for which access can be requested (files, programs, etc.)
- Biba模型中的**每个主体和对象都有一个与之相关的完整性级别**。Each subject and object in the Biba model will have an integrity level associated with it.

# 访问模式（访问权）：Access Modes

Biba模型由以下访问权限组成。The Biba model consists of the following access rights:

- **修改**：允许一个主体对一个对象进行写入。Modify: allows a subject to write to an object.
- **观察**：允许一个主体读取一个对象。这个命令与其他模型的读关系是同义的。Observe: allows a subject to read an object. This command is synonymous with the read relation of other models.
- **调用**。允许一个主体与另一个主体交流。Invoke: Allows a subject to **communicate** with another subject.
- **执行**。允许一个主体执行一个对象。该命令本质上允许一个主体执行一个作为对象的程序。Execute: Allows a subject to execute an object. The command essentially allows a subject to execute a program which is the object.

# Summary

- Biba模型关注维护信息的**完整性**。Biba model concerns maintaining the integrity of information.
- **完整性等级和分类集与支配权检查结合**使用，以确定**访问权**。Integrity levels and category sets are used in conjunction with dominance checks to determine access rights.
  - 就像BLP对保密性能的操作一样。Like how BLP operates with confidentiality properties.
- 完整性策略一般不关心保密性。Integrity policies generally don't care about confidentiality.
  - 同样地，保密性政策通常也不关心完整性。Likewise, confidentiality policies typically don't care about integrity.
- **Lipner模型是Biba模型在商业环境中的一个改编**。Lipner model is an adaptation of Biba model for commercial environments.
  - 在企业中以某种形式普遍使用。Commonly used in enterprise in some form.

# =====================

# 6Biba Policies

![](/static/2022-03-11-12-35-09.png)

- Biba模型是一个不同政策的系列。The Biba model is a family of different policies.
- 该模型的目标是**防止 "干净 "的高层实体被 "肮脏 "的低层实体所污染**。The goal of the model is to prevent the contamination of ‘clean’ high level entities from ‘dirty’ low level entities.
  - 这些都是相对的定义。These are relative definitions.
- 该模型支持**强制性和自由裁量的政策**。The model supports both mandatory and discretionary policies.

# 严格完整性政策：Strict Integrity Policy

The Strict Integrity Policy is the first part of the Biba model.

组成

* **Simple Integrity Condition**
  * 𝑠 ∈ 𝑆 can observe 𝑜 ∈ 𝑂 iff 𝑖 𝑠	≤ 𝑖(𝑜)
  * (“no read down”)
  * dom关系
    * o dom s
* **Integrity Star Property**
  * 𝑠 ∈ 𝑆 can modify 𝑜 ∈ 𝑂 iff 𝑖 𝑜	≤ 𝑖 𝑠
  * (“no write-up”)
  * dom关系
    * s dom o
* **Invocation Property**
  * 𝑠1 ∈ 𝑆 can invoke 𝑠2 ∈ 𝑆 iff 𝑖(𝑠2)	≤ 𝑖(𝑠1)

---

- **当大多数人提到Biba模型时，他们指的是严格的完整性模式**。When most people refer to the Biba model they are referring to the strict integrity model.
  - 这个策略是该模型中**最常用**的策略。This policy is the most common policy used form the model.
- 严格的完整性策略对数据执行 "无向上写 "和 "无向下读"。The strict integrity policy enforces “no write-up” and “no read-down” on data.
  - 可以被认为是Bell-LaPadula的反面。Can be thought of as the opposite of Bell-LaPadula.
- **政策限制了更高层次的数据污染，因为一个主体只被允许修改他们这个层次或更低层次的数据**。Policy restricts the contamination of data at higher level, since a subject is only allowed to modify data at their level or at a lower level.

## （读）简单完整性条件图：Simple Integrity Condition Diagram

no read down

![](/static/2022-03-11-12-47-57.png)

左边是3个security/integrity levels

## (写)Integrity Star Property Diagram

![](/static/2022-03-11-12-51-01.png)

# (读变体)主体低水标策略：Low-Watermark Policy for Subjects

主体的低水印策略是**简单完整性条件（读）的一个放松的变体**。The low-watermark policy for subjects is a relaxed variant of the simple integrity condition.

* 针对read没有限制，任何主体都能读object，但是之后主体完整性级别会变化

组成

* 主体可以检查任何物体
  * 如果s检查o，则 i'(s)=min(i(s), i(o))
    * i'(s)是读取后主体的完整性级别
  * ![](/static/2022-03-11-12-57-38.png)
* * **Integrity Star Property**
  * 𝑠 ∈ 𝑆 can modify 𝑜 ∈ 𝑂 iff 𝑖 𝑜	≤ 𝑖 𝑠
  * (“no write-up”)
  * remains unmodified
* **Invocation Property**
  * 𝑠1 ∈ 𝑆 can invoke 𝑠2 ∈ 𝑆 iff 𝑖(𝑠2)	≤ 𝑖(𝑠1)
  * remains unmodified

---

- 主体的低水标策略对主体阅读对象没有任何限制。The low-watermark policy for subject does nothing to restrict a subject from reading objects.
- 主体的低水标策略是一个**动态的策略**，因为它**根据观察到的对象**来**降低主体的完整性等级**。The low-watermark policy for subjects is a dynamic policy, because it lowers the integrity level of a subject based on what objects are observed.

## 图：Low-Watermark Policy for Subjects Diagram

![](/static/2022-03-11-13-00-35.png)

# (写变体)对象低水标策略：Low-Watermark Policy for Objects

The low-watermark policy for objects is a relaxed variant of the Integrity Star Property.**对象的低水标政策是Integrity Star Property（写）的一个放松的变体**。

* (调用和简单的完整性条件不受此政策的影响)(The invocation and simple integrity conditions are unaffected with this policy)
* 对象的低水印政策也是一个动态政策，与主体的低水印政策类似。The low-watermark policy for objects is also a dynamic policy, similar to the low-watermark policy for subjects.
- 这个政策的缺点是它**不能阻止一个不受信任的主体修改一个受信任的对象**。The disadvantage of this policy is it does nothing to prevent an untrusted subject from modifying a trusted object.
- 许多人会认为这不是一个实用的政策。Many would argue that this is not a practical policy.
  - 至少实践中不那么可用

组成

![](/static/2022-03-11-13-06-16.png)
![](/static/2022-03-11-13-09-38.png)

1.	𝑠 ∈ 𝑆可以修改任何𝑜 ∈ 𝑂，无论其完整性等级如何。𝑠 ∈ 𝑆 can modify any 𝑜 ∈ 𝑂 regardless of integrity level.
2.	如果𝑠∈𝑆修改了𝑜∈𝑂，那么𝑖'(o) = min(𝑖(s), 𝑖(𝑜)) **其中𝑖′(𝑜) 是对象被修改后的完整性级别**。If 𝑠 ∈ 𝑆 modifies 𝑜 ∈ 𝑂 then 𝑖‘(o)	= min(𝑖(s), 𝑖(𝑜)) where 𝑖′(𝑜) is the objects integrity level after it is modified.
* **Simple Integrity Condition**
  * 𝑠 ∈ 𝑆 can observe 𝑜 ∈ 𝑂 iff 𝑖 𝑠	≤ 𝑖(𝑜)
  * (“no read down”)
  * dom关系
    * o dom s
* **Invocation Property**
  * 𝑠1 ∈ 𝑆 can invoke 𝑠2 ∈ 𝑆 iff 𝑖(𝑠2)	≤ 𝑖(𝑠1)
  * remains unmodified

# 低水标完整性审计策略：Low-Watermark Integrity Audit Policy

低水印完整性审计策略由以下规则组成。The low-watermark integrity audit policy consists of the following rules:

1. 任何主体都可以修改任何对象，无论完整性等级如何。Any subject may modify any object, regardless of integrity levels.
2. 如果一个主体**在更高的完整性级别上修改一个对象**（**一个更可信的对象**），**它将导致该交易被记录在审计日志中**。If a subject modifies an object at higher integrity level (a more trusted object) it results in the transaction being recorded in an audit log.

- 这个策略的缺点是它**不能防止对一个对象的不正当修改**。The drawback to this policy is it does not prevent improper modifications to an object.
- 这个政策类似于对象的低水标政策，除了在这种情况下，**对象的完整性等级不会被降低，而是被记录**。This policy is similar to the low-watermark for objects policy, except in this case the objects integrity level is not lowered, it is recorded.

# Ring Policy

用于环形策略的完整性标签是固定的，类似于严格完整性策略中的标签。Integrity labels used for the ring policy are fixed similar to those in the strict integrity policy.

- 环形政策**允许任何主体观察任何物体**。这个政策**只关注直接修改**。The Ring Policy allows any subject to observe any object. This policy is only concerned with direct modification.
- 这个策略的缺点是它**允许不正当的修改间接发生**。The drawback to this policy is that it allows improper modifications to indirectly take place.
  - **一个主体可以读取一个不太信任的对象。然后该主体可以在自己的完整性级别上修改它所观察到的数据**。A subject can read a less trusted object. Then the subject could modify the data it observed at its own integrity level.
  - 这方面的一个例子是，一个用户读了一个不太受信任的对象，然后后来以自己的完整性级别将该数据写入一个对象中。An example of this would be a user reading a less trusted object, then later write that data to an object at their own integrity level.

组成

1.	任何主体都可以观察任何物体，而不考虑完整性等级。Any subject can observe any object, regardless of integrity levels.
2. * **Integrity Star Property**
  * 𝑠 ∈ 𝑆 can modify 𝑜 ∈ 𝑂 iff 𝑖 𝑜	≤ 𝑖 𝑠
  * (“no write-up”)
  * remains unmodified
3. **Invocation Property**
  * 𝑠1 ∈ 𝑆 can invoke 𝑠2 ∈ 𝑆 iff 𝑖(𝑠2)	≤ 𝑖(𝑠1)
  * remains unmodified

# Biba优缺点：Advantages And Disadvantages

- 优点。
  - Biba模型简单而容易实现。The Biba model is simple and easy to implement.
  - Biba模型提供了许多不同的政策，可以根据需要来选择。The Biba model provides a number of different policies that can be selected based on need.
- 缺点。
  - 该模型在执行保密性方面没有任何作用。The model does nothing to enforce confidentiality.
  - Biba模型不支持授权的授予和撤销。The Biba model doesn’t support the granting and revocation of authorization.

# Summary

- Biba模型实际上是一个可以选择不同模式的家族。The Biba model is actually a family of different models that can be selected.
- **该模型应与另一个模型相结合，因为它不提供保密性**。应该用Bell-LaPadula这样的模型来补充它。The model should be combined with another, because it does not provide confidentiality. A model such as the Bell-LaPadula should be used to complement it.
- **Lipner模型是一个结合Biba和Bell-LaPadula模型的例子，以解决它们各自的一些缺点**。The Lipner model is an example of a model that combines the Biba and Bell-LaPadula models to address some of their individual shortcomings.

# =====================

# Tutorial

# ACM

![](/static/2022-03-11-13-40-52.png)

ACM中每行指明了subject的权限，每列是access control lists that applicable to an object

* 缺点，表达敏感级别等的能力

# 2

注意subject,object label可以不同，用相同label更简单结合

![](/static/2022-03-11-13-43-17.png)

---

使用BLP模型，填表

* 有三个原则
  * **simple security property**
  * **star**
  * strong star
* 先看前两个，决定read,write访问权
  * ![](/static/2022-03-11-13-48-03.png)
    * 注意根据dom关系来推
  * 没有给category set就不用考虑

D能写所有对象因为BLP模型只关注机密性不关注完整性

![](/static/2022-03-11-14-02-37.png)

注意能拿BLP output(mandatory access model，MAC)交叉参考ACM（discretionary access control model，DAC）

* 如果假设优先级：discretionary > mandatory （至少本课中优先）
* ![](/static/2022-03-11-15-07-54.png)

# 3

填表 (注意本例有类别集category set)

* 注意用dom关系来推Access right
* 分析confidentiality的时候不关注integrity反之亦然
* 最终表格output要结合两个模型 **与ACM交叉验证**
  * 比如confidentiality - r，integrity -r ，最后就是r（只要其中一个模型有权利r,loose model中）
  * 最后跟ACM（其他discretionary access model）对比

BLP （Simple Security, Star)

* ![](/static/2022-03-11-15-20-10.png)

Biba (simple integrity, integrity star)

* ![](/static/2022-03-11-15-21-20.png)

# loose/strict model

关于模型是松散的还是严格的概念被说成是在结果的含义上是不明确的。因此，在松散的模型中，只要有一个属性成立，与该属性相关的关系就可以作为MAC的有效输出。在严格的设置中，Biba和BLP属性都应该同意一个关系，它才是有效的输出。The notion on whether the models are loose or strict was stated to be unambiguous in what the results mean. So with the loose model it is sufficient for one property to hold for the relation associated with that property to be valid as output from the MAC. In a strict setting, both Biba and BLP properties should agree on a relation for it to be valid output.

有可能 "松散模型 "一词并不适用，这取决于被检查的属性的组合。在这种情况下，当然采取输出并与DAC进行检查就可以了。在tutorial中，这是非常相关的，因为BLP和Biba属性都可以返回多个读写权限。在课件或考试中可能不会出现这种情况。It is possible that the term 'loose model' may not be applicable depending on the combination of properties that are being checked. In this case, of course it will be fine to take the output and check it with the DAC. It was very relevant during the tutorial as both BLP and Biba properties could return multiple read and write permissions. This may not be the case in the coursework or exam.

当检查DAC时，就像在tutorial中讨论的那样，DAC应该与MAC的输出保持一致，这样MAC才有效。When checking the DAC, much like how this was discussed in the tutorial, the DAC should align with the output from the MAC for the MAC to be valid.
