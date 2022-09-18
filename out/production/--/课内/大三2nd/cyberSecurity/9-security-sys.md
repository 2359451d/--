# Security Systems

在本讲座中，我们将探讨三种不同的方法，以确保不同安全许可的大量文件的安全，以及在一般情况下，确保信息的安全。In this lecture we're looking at three different ways of securing lots of files for different security clearances, And in general, security at securing information. 

3模型

* Bell-LaPadula
* Chinese Wall
* BMA Model

## Bell-LaPadula模型

为**计算机**处理信息而设计（是我们在考虑政府的安全和对政府文件和文档的安全时倾向于想到的模式 we tend to think of when we think about government's security and secure to government files and documents, ）。Designed for computer handling of information.

* 一个分层次的系统。 A hierarchical system.
  * 也被称为多级安全。 Also known as multi-level security.
  * 最高机密，秘密，机密，公开 Top Secret, Secret, Confidential, Open
* **拥有高安全级别的人可以访问较低安全级别的信息，但反之则不行**。 Someone with a high security clearance can access lower security information, but not vice versa.
* **不能向上读取** No Read Up
  * 任何程序都不能读取更高一级的信息。No process can read information at a higher level.
* **不能向下写** No Write Down
  * 任何进程都不能把信息写到较低层次（无明确许可情况下without explicit permission）。No process can write information to a lower level.
    * 没有明确的许可，你不能将秘密文件转换成机密文件。
  * 因为担心上级进程执行恶意代码。Fear of malicious code executed by a higher process
    * 威胁模型：如果有人黑入并获得最高机密许可。他们可以得到一大堆绝密文件，并将它们重新归类为公开文件，然后他们只需要做一次，那么所有这些文件现在都是公开的，然后任何人都可以去访问它们 threat model here is if someone hacks in and gets a top secret clearance. They can get a whole bunch of top secret documents and reclassify them as open, and then they only need to do that once then all these documents are now open, and anyone can then go and access them. 

---

它是一种强制性的访问控制模式。它有4个级别，绝密、机密、保密和公开。同一级别的不同项目有自己的独立隔间。格子模型涵盖了不同格子之间的关系。It is a mandatory access control model. It has 4 levels, Top Secret, Secret, Confidential and Open. Different projects on the same level have their own individual compartments. A lattice model covers the relationship between different compartments.

### 强制性访问控制 & 自由访问控制：Mandatory Access Controls

:orange: BLP的设计是为了**执行强制性的访问控制**。BLP was designed to enforce mandatory access controls.

* **不允许控制者有个人自由裁量权**。 No personal discretion by the controllers allowed.

:orange: 反之，则是**自由的访问控制**。 The opposite is discretionary access control.

* **有一个人在循环中行使自由裁量权**。With a person in the loop to exercise discretion.
* 所以这个人可以根据自己的判断来做决定。So the person can make decisions based on their own judgement.

:orange: **计算机程序不能行使自由裁量权，所以访问控制必须是强制性的** A computer program cannot exercise discretion and so the access controls have to be mandatory

* 所以自由裁量权的行使涉及到不通过请求信息来处理日常的信息，因为它是为计算机程序设计的，处理秘密数据必须是强制性的 So the exercise of discretion involves not handling information on a day to day basis by requesting information

### 文件分隔：Compartments

我们有很多很多的秘密信息，那么拥有秘密权限的人不应该被允许阅读每一份秘密文件.因此，当我们需要不同的区域和真正重要的隔间 We have lots lots of secret information, then someone with Secret clearance shouldn't be allowed to read every single secret document. So when we need different areas and really important compartments

:orange: **同一级别的信息如果必须分开保存，则保存在不同的隔间里**。 Information on the same level that must be kept separate is kept in separate compartments.

* 有人可以有权限阅读一个秘密隔间，但另一个秘密隔间，而且访问是由复杂的密码控制 someone can have permission to read one secret compartment, but another secret compartment and access is controlled by complex passwords.
* <font color="deeppink">通过密码控制访问</font>。 Access controlled by codewords.

:orange: **知道一个隔间细节的人不能知道其他隔间的任何信息**。 Someone who knows details about one compartment must not know anything about the others.

:orange: **在某些情况下，一个隔间的存在是保密的**。 In some cases the existence of a compartment is kept secret

### 合并隔间：Combining Compartments

两个相关的隔间需要结合 --- 合并隔间，创建一个新隔间 & 代码（密码），涵盖了两个较小隔间

:orange: **必须为使用一个以上隔间的人创建一个新的隔间和代码**。A new compartment and codeword must be created for people who use more than one compartment.

* 这将导致大量的隔间。This will lead to a large number of compartments.
  * 一个衍生作品基于使用公开来源的报告和另一个使用绝密报告的报告，将创建另一个绝密隔间。A derived work based on a report using open sources and another report using a top secret report will create another top secret compartment.
    * 我的工作是基于两个不同的隔间，一个是公开的来源，另一个是最高机密的报告，我们将不得不做另一个最高机密隔间。所以这被表示为一个格子模型。I derive work was based on two different compartments one with open sources and another one is top secret report, we'll have to make another top secret compartment. So this is expressed as a lattice model. 
  * 有些组织有超过一百万个活动区间。Some organisations have over a million active compartments.

:orange: **这可以用格子模型来表示**。 This can be expressed as a Lattice model.

* 当一个人必须将两个隔间结合起来时，格子模型并不起作用。The Lattice Model does not help when a person has to combine two compartments.
* 它只是创造了第三个隔间。 It just creates a third compartment.
* <font color="deeppink">使用标准软件来自动化这个过程也是很困难的</font>。 It is also difficult to use standard software to automate the process.

### 格子模型：Lattice Model

网格结构类似于树结构，但本质上是一个树结构，只是形式上的限制稍微少一些。lattice structure is like a tree structure, but in essence it is a tree structure but with slightly less formal restrictions. 

:orange: **格是一种逻辑结构，其中任意两个成员可能处于支配关系，也可能不处于支配关系**。A lattice is a logical structure where any two members may or may not be in a dominance relation.

* A > B，A < B，或者 A 和 B 不能相提并论。A > B, A < B, or A and B can’t be compared.
  * a可能比b重要或不那么重要。或者a，b不能比较

:orange: **没有支配循环** There are no dominance loops

* A > B，B > C，C > A 是不可能的。A > B, B > C, C > A is impossible.
* 可以把它写成树形结构，因为没有循环。

:orange: **信息流从高到低（不能反反向**）。Flow of information is from High to Low.

* **如果两个节点不能进行比较，那么就没有信息流**。There is no flow of information if the two nodes can’t be compared.

#### 例子

![](/static/2021-05-05-10-32-45.png)

有7人拥有以下许可（访问安全级别的 security clearance）和隔间密码。There are 7 people with the following clearances and compartment code words.

* Top Secret
* Top Secret + Fred
* Top Secret + Fred + Blogs
* Secret
* Secret + Blogs
* Secret + Fred + Blogs
* Confidential

---

* We can draw them as a graph.
* A straightforward extension to BLP.

![](/static/2021-05-05-10-37-15.png)

### 高水位原则：High Watermark Principle

**BLP 假设在创建隔间时，一系列文件的保密级别已知**。BLP assumes that the confidentiality level of a series of documents is known when the compartment is created.

* 所以安全级别不能改变。So the security level can’t be changed.

**在实践中，一个隔间可能从机密级别开始，但是随着更多文档的添加，它的级别需要增加**。In practice, a compartment may start off at the confidential level but then, as more documents are added, it’s level needs to be increased.

* 所以，如果你有这个盒子里的文件夹，里面有一大堆秘密文件，而你有一份绝密文件，那么外面的标签就必须从绝密改为绝密。
* 高水位原则意味着水位可以提高，但不能降低。The high watermark principle means that levels can be increased but not decreased.

:candy: **这可能会产生不希望看到的结果，即所有隔间最终都处于最高水平**。This can have the undesired effect that all compartments end up at the highest level.

:orange: 真正的系统通常需要可以覆盖安全模型的“可信”主题。Real systems usually need ‘trusted’ subjects that can override the security model.

* 根用户。Root users.
* 所以要让这样的系统运行起来，它们往往过于笨重，特别是当你有超过一百万个隔间的时候，你需要一些你真正信任的人

这总是一个坏主意，安全性往往发生在 blP 上

## 多边安全：Multilateral Security

:orange: **这种方法聚焦于许多不同的部分，都在同一个级别上**。This focuses on lots of different compartments, all at the same level.

:orange: **防止信息水平而不是垂直流动**。 Information is prevented from flowing horizontally rather than vertically.

* 人口普查数据 Census data
* 医疗资料 Medical data
* 拥有众多客户的律师事务所。Law firms with many clients.

:orange: **带隔间的 BLP 可以解决这些问题，但还有其他的方法**。 BLP with compartments can solve these problems. But there are other approaches.

* 但是在同一级别上有许多不同的组件，我们需要一个更好的模型来控制这些组件的保密性
* BLP过于麻烦

## The Chinese Wall 模型

:orange: **这是一种集中控制模式（比BLP简单**。This is a centralised control model.

:orange: 适用于投资银行，但也适用于其他一些机构。Used in investment banks, but applicable in a number of other organisations.

* 就是当我们有一个组织，为其他组织提供咨询服务。客户也是如此。例如，一家投资银行会有客户顾问，他们与各种希望投资的公司打交道。

:orange: 客户被划分为商业部门。Customers are divided into business sectors.

* 会计师、石油公司等。Accountants, oil companies, etc.

:orange: 如果一名雇员曾在某一部门的一家公司工作，他必须经过一段时间才能在同一部门的另一家公司工作。 If an employee has worked for one company in a sector, time must elapse before they can work for another company in the same sector.

* 如果一个雇员去为一家会计公司工作，例如，他们提供某种投资服务，这是一项投资回到那里，他们将不可避免地签署一份保密合约，并发现许多有趣的秘密信息 So the obvious security reason here is, if, if an employee goes to work for one firm of accountants, for example, and they're providing some sort of investment services and it's an investment back there, they will inevitably have to sign a nondisclosure agreement, and find out lots of interesting secret information. 
* 员工可以选择他在某个部门工作的第一家公司。 The employee can choose the first company he works for in a sector.
* 然后通过访问控制，他被禁止与其他人接触。 He is then barred from the others via access controls.

:orange: 攻击Chinese Wall。 Attacks on the Chinese Wall

* 现有专家提出的若干问题可能会得到有关该部门正在进行多少工作的信息。 Several questions on which specialists are available might recover information on how much work was going on in the sector.
  * 所以你可以要求攻击者寻找在特定区域工作的人。为了了解不同部门正在进行多少工作，这样他们或许可以了解有多少投资，会计师们正在做一些工作，以了解该部门的金融状况。但这并不是特别强烈的攻击，所以你从中得到的信息的质量并不是那么好 So you could ask an attacker could look for people working in specialist areas. To find out how much work is going on in different sectors, so they could perhaps find out how much investment, work is being done with accountants in general to get some feel for the financial state of that sector. But that's not particularly strong attack so the quality of the information you get out of that is not that brilliant. 
* 这是一个非常简单的模型，它是有效的，这就是为什么它被使用，除了贿赂人们，很少有攻击中国的墙，所以它确实依赖于人们的诚实 so this is a very simple model, and it works and that's why it's used, and there are very few attacks that can be made on the Chinese wall apart from bribing people so it does rely on people's honesty. 

---

在一个部门的一家公司工作的人，在一段固定的时间过去之前，不能为同一部门的另一家公司工作。其假设是，雇员在为第一家公司工作时可能获得机密信息，而这些信息可能对该部门的其他公司有用。它可以防止顾问将机密从一家公司传到另一家公司。A person who works in one company in a sector can't work for another company in the same sector until a fixed amount of time has passed. The assumption is that an employee might gain confidential information while working for the first company that might be useful for other companies in the sector. It prevents a consultant from passing secrets from one company to another.

## The BMA Model

BMA 模式是，是医疗保健代理模式 BMA 代表英国医学协会 The BMA model is, is healthcare proxy model BMA stands for the British Medical Association. 

* 因此，不久前，政府正在研究使医疗记录自动化和数字化的方法。各种各样的组织和个人提出了模型，其中之一就是 BMA 模型，由 Ross Anderson 和他的团队提出，并得到了英国医学协会的支持。

最终它没有被采纳，但是它终于开始发生了

---

**健康隐私是一个重要的问题**。Heathcare privacy is an important issue.

* 如果一个人接受治疗，医生必须有权查阅他们的医疗记录。If a person receives medical treatment, the doctors must have access to their medical records.
  * 这可能，可能有紧急情况。因此，有些人可能需要在国家的其他地方进行紧急医疗治疗，医生需要查看他们的医疗记录，看看他们是否被记录为对某些药物或类似的东西过敏 And this might, there might be an emergency. So someone might need emergency medical treatment in a different part of the country where the doctors treating them, need to have access to their medical records to see if they're recorded as being allergic to some, some drugs or something like that. 
* 可利用医学资料。Medical information can be exploited.
  * 因此，获得医疗记录是很重要的，但是医疗信息也可以被利用。因此，有许多不同的方法可以利用它 So, access to medical records is important, but medical information can be exploited as well. So, there are many different ways it can be exploited. 

:orange: **医疗保健信息的二次利用也很重要（医疗信息的主要用途是治疗病人，解决二次使用中出现的任何问题，是使用医疗记录，而不是涉及治疗病人的方式，**。 Secondary uses of healthcare information is also important. Well, the primary use of healthcare information is to treat the patient, fix whatever is wrong with secondary use is using the medical records, not that's not involved in a way that's not involved in treating the person. 

* 医学进步可以依靠这些信息。Medical advances can depend on such information.
* 数据可能会丢失。 The data can be lost.
* 很难匿名化这些记录。 It is difficult to anonymise the records.
  * 所以如果你想收集统计数据，比如某种特定形式的治疗或某种特定的药物，那么你就需要匿名化记录，而匿名化很难做到病人可能不希望他们的信息被用于研究
* 病人可能出于道德原因不希望自己的信息用于研究。A patient may have ethical reasons for not wanting their information used for research.
* 公司可以通过研究赚钱，病人应该得到报酬吗？Companies can make money from research. Should the patients be paid?
  * 公司可以从研究中赚钱。研究人员可以用于药物试验，如果药物试验成功，公司可以从他们生产的药物中赚钱。因此，关于病人参与研究是否应该得到报酬，他们的医疗记录被用于研究的问题就存在了

### 医疗记录的威胁模式：Medical Records Threat Model

:orange: 最常见的侵权行为是**内部人员滥用授权访问**。The most common violation is abuse of authorised access by insiders.

:orange: 最常见的威胁载体是**社会工程攻击**。 The most common threat vector is a social engineering attack.

* 有人打电话给病人的医生，声称是另一位需要紧急查看病人病历的医生。Someone rings the patient’s doctor claiming to be another doctor needing urgent access to the patient’s records.
  * 对策是总是回电话告诉对方你的电话号码（自称是医生的。The countermeasure is to always phone back with the number for that person that you have.

**健康护理人员专注于帮助病人，可能对安全问题不够重视**。Heathcare staff are focussed on helping the patient and may be careless about security.

* 电脑可以随时登录，这样病房里的任何人都可以在不验证自己身份的情况下快速查看医疗记录。 Computers can be left logged in so that anyone in the ward can get quick access to medical records without authenticating themselves.
  * 例如，如果你为了应对紧急情况而访问某人的医疗记录。你不希望每次需要找到一些新的信息才能真正完成照顾这个病人的工作时，都用密码再次登录
* 一个人被允许看的记录越多，滥用的空间就越大 The more records a person is allowed to see, the greater the scope for abuse

### BMA：医疗记录的定义-BMA: Definition of a Medical Record

病人的记录并不是所有与他们有关的信息的集合。A patient’s record is not the collection of all information relating to them.

它是同一个工作人员可以接触到的与病人有关的最大的一组事实。It is the maximum set of facts relating to a patient to which the same staff have access to.

* 每个病人将有一个以上的记录。Each patient will have more than one record.

许多法律要求每人需要一个以上的记录。 Many legal requirements require more than one record per person.

* 性传播疾病，出生记录，监狱医疗记录。Sexually transmitted diseases, birth records, prison medical records.
* 法定传染病 Notifiable infectious diseases

### 模型原则-BMA: Model Principles

**访问控制**: 每个临床记录都有一个可以看到它的人的列表，以及可以向其中添加数据的人的列表。Access controls: Each clinical record has a list of people who can see it and who can add data to it.

**记录打开**: 医生可以打开自己和访问控制列表上的病人的记录。 Record opening: A doctor can open a record with themselves and the patient on the access control list.

* 如果病人是由另一名医生转介，转介医生也在名单上。 If the patient has been referred by another doctor, the referring doctor is also on the list.

**控制**: 其中一名医生控制着访问控制列表。他们只能增加健康护理专业人员。 Control: One of the doctors is in control of the access control list. They can only add heathcare professionals to it.

**同意和通知**: 医生**必须通知病人**记录查阅名单上的姓名，以及名单上的所有更改。**除非在紧急情况下或有法定豁免，否则必须征得他们的同意**。 Consent and notification: The doctor must notify the patient of the names on the record access list, and all changes to the list. Their consent must be obtained, except in an emergency or if there are statutory exemptions.

### 描述BMA安全模型

一份医疗记录是由可以访问它的人的访问控制列表控制的。它包含所有具有相同访问控制列表的信息。一个人可以有一个以上的医疗记录。访问和变更的审计跟踪被保存下来。当访问列表发生变化时，以及当有人被添加到可以访问大量其他记录时，病人会被告知。A medical record is controlled by an access control list of people who can access it. It contains all the information that has the same access control list. A person can have more than one medical record. An audit trail of access and changes is kept. The patient is informed when the access list changes and also when someone is added who has access to a large number of other records.

---

**持久性**。在适当的时间段过后，任何人都不能删除临床信息。 Persistence: No one can delete clinical information until the appropriate time period has elapsed.

**归属权**。对临床记录的所有访问必须在记录上标明受试者的姓名、日期和时间。必须对所有的删除行为进行审计追踪。 Attribution: All access to the clinical records must be marked on the record with the subjects name, date and time. An audit trail must be kept of all deletions.

**信息流**。只有当记录B的访问控制列表中的所有人也在记录A的列表中时，信息才能从记录A流向记录B Information flow: Information can flow from record A to record B only if everyone in the access control list of record B is also in the list for record A.

**聚合控制**。如果建议加入其访问控制列表的人已经可以访问大量的其他记录，必须通知病人。 Aggregation control: Patients must be notified if a person who it is proposed should be added to their access control list already has access to a large number of other records.

**可信的计算基础**。计算机系统安全的有效性必须由独立专家验证。 Trusted computing base: The effectiveness of the security of the computer system must be verified by independent experts.

### 试点研究：Pilot Study

BMA模型与政府提议的大型医疗数据库不同。 The BMA model is different from the large medical database proposed by the government.

BMA模式的试点研究已经在英国的一个卫生当局进行，结果是有用的。A pilot study of the BMA model has been run in an English health authority and the results have been useful.

:orange: **当个人隐私很重要，而多边安全又不合适时，BMA模式可以应用于一些非医疗场合**。 The BMA model can be applied in a number of non-medical situations when individual privacy is important and a multi-level security is not appropriate

# 安全保证：Security Assurance

**安全保证是指对一个系统满足其安全要求的信心** Security assurance is confidence that a system meets its security requirements

## 问题源：Sources of Problems

系统经常被推销为安全的，但它们有严重的缺陷 Systems are often marketed as secure when they have serious flaws .

* 它们随后被用于错误的环境中 They are then used in the wrong environment

问题的来源 Sources of Problems

1. 需求的定义 Requirements definitions
2. 系统设计 System design
3. 硬件实现 Hardware implementation
4. 软件实现 Software implementation
5. 系统使用和操作错误 System use and operation errors
6. 故意误用系统 Wilful system misuse
7. 设备故障 Equipment malfunction
8. 演变、维护和退役 Evolution, maintenance and decommissioning
9. 上帝行为 Acts of god

## 术语-安全要求：Security Requirements

:orange: **一个受信任的系统是一个已经被评估证明符合安全要求的系统**。 A trusted system is one that has been shown to meet security requirements by an evaluation

:orange: **安全保证是指对一个系统满足其安全要求的自信程度** Security assurance is confidence that a system meets its security requirements

:orange: **安全要求是必须满足的目标的陈述** A security requirement is a statement of goals that must be satisfied

:orange: **安全策略是在安全系统中强制执行的一组语句** A security policy is a set of statements which when enforced result in a secure system.

:orange: **安全模型描述一系列安全策略** A security model describes a family of policies

* 必须考虑预期用途 Must consider intended use

作为一个例子，考虑一个对存储在计算机文件中的信息进行保密的程序（如实验室工作1）。一个安全要求是，文件中的信息是秘密的。安全保证是通过使用Java安全库提供的，该库已被独立验证为安全。必须进一步保证代码的正确实施。一旦安全得到保证，它就成为一个 可信赖的系统。安全策略规定了程序的使用方式，例如，允许使用的密码类型。它遵循 "用文本密码进行加密的安全 "模式。As an example, consider a program to keep information stored in computer files secret (like labwork 1). A security requirement is that the information in the files is secret. Security assurance is provided by using the Java security library, whch has been independently verified as secure. Further assurance that the code is correctly implemented must be provided. Once security has been assured, it becomes a trusted system. A security policy governs how the program is used, for example, the type of passwords that are allowed. It follows the 'security by encryption with text passwords' model.


---

**例子：安全要求是某些数据的保密性** Example: Requirement is the confidentiality of certain data

* 政策1：与数据相关的访问控制和安全属性 Policy 1: access control and security attributes associated with data
* 政策2：运输过程中的加密 Policy 2: encryption when in transit

## TCSEC标准：TCSEC Evaluation

TCSEC标准是计算机系统安全评估的第一个正式标准，具有划时代的意义 【评判？一个系统满足其安全要求的自信程度】

由美国政府开发(1985年) Developed by US government (1985)

* 也被称为橙皮书 Also called the Orange Book
  * 橙皮书有一系列的安全级别，安全产品可以达到。它是由美国政府运作的，他们进行评估，看是否达到了适当的安全级别。The Orange Book has a series of security levels that security products can be achieved. It was run by the US government, who carried out evaluations to see if the appropriate security levels had been met.
* **安全等级 D、 C1、 C2、 B1、 B2、 B3、 A1**。 Security levels D, C1, C2, B1, B2, B3, A1

**强调保密性** Emphasises confidentiality

* 保护政府机密资料 Protection of government classified information

**不完全评估完整性** Does not evaluate integrity completely

* 依赖访问控制 Relies on access controls

**不考虑信息的可用性** Does not consider availability of information

---

* **D = 没有保护** D = no protection
* **C1: 酌情保护** C1: discretionary protection
* **C2: 受控访问保护** C2: controlled access protection

### TCSEC B1, B2, B3, A1

:orange: **B: 安全系数从1增加到3**。 security increases from 1 to 3.

* 用户有访问控制和安全许可。 Users have access controls and security clearances.
  * 强制性保护意味着如果用户没有与安全等级相连，系统就不会让用户存取对象
* 数据文件有不同的安全级别。 Data files have different security levels.
* 关于如何开发代码的约束。 Constraints on how code is developed.
* 隐蔽通道分析 Analysis of covert channels.

---

:orange: **A1**

* 形式化的方法和数学上证明的保护 Formal methods and mathematically proven protection.
* A1系统的显著特征是，系统的设计者必须按照一个正式的设计规范来分析系统。对系统分析后，设计者必须运用核对技术来确保系统符合设计规范。A1系统必须满足下列要求：系统管理员必须从开发者那里接收到一个安全策略的正式模型;所有的安装操作都必须由系统管理员进行；系统管理员进行的每一步安装操作都必须有正式文档
* A1类与B3类相似，对系统的结构和策略不作特别要求

### 局限性：TCSEC Impact

**首先解决安全评估问题** First to address security evaluation 

* **范围有限，因为最初是为操作系统编写的** Scope limited because originally written for operating systems
  * 扩大到网络和数据库。Expanded to networks and databases.

**标准蠕变** Criteria creep

* 公布的其他文件的解释和澄清 Additional documents published interpretations and clarification
* 更新的 c 2产品要比旧的产品做得更多 Newer C2 products had to do more than older ones

**评估花的时间太长了** Evaluations took too long

* 各国政府进行评价 Governments carried out the evaluations 
* 产品在评估完成前发行 Products released before evaluations were completed 
* 有时它们在完成之前就已经过时了。 Sometimes they were obsolete before completion

## ITSEC标准

**与此同时，国际社会根据自己的标准开展工作** Meanwhile, the international community worked on their own criteria 

**1991年欧洲推出的信息技术安全评估标准** Information Technology Security Evaluation Criteria launched by Europeans in 1991

* 主要是法国，德国，英国，荷兰 Mainly France, Germany, UK, Netherlands

**两类标准: 功能和评估级别**。 Two types of criteria: functional and evaluation.

* 该标准将安全概念分为功能与评估两部分
* 但因为TCSEC被认为过分死板，因此ITSEC的一个主要目标就是为安全评估提供一个更灵活的标准。ITSEC和TCSEC的主要区别在于，**ITSEC不单针对了保密性，同时也把完整性和可用性作为评估的标准之一**。

**使用与 TCSEC 相同的功能标准** Used the same functional criteria as TCSEC

* 功能准则从F1～F10共分10级。 1～7级对应于TCSEC的D到A(F-D+E0~D、F-C1+E1~C1、F-C2+E2~C2、F-B1+E3~B3、F-B2+E4~B2、F-B3+E5~B1、F-B3+E6~A)
* F6至F10级分别对应数据和程序的完整性、系统的可用性、数据通信的完整性、数据通信的保密性以及机密性和完整性的网络安全

**定义自己的评估级别，即评估类型（保证性等级**）。 Defined its own evaluation levels, ie type of evaluation.

* E1，E2，E3，E4，E5，E6
* ITSEC则把完整性、可用性与保密性作为同等重要的因素。ITSEC定义了从E0级(不满足品质)到E6级(形式化验证)的7个安全等级

---

前者（**TCSEC）定义了功能类，例如 C2**。The vendors defined the functionality class, for example C2.

* **这被称为安全目标(ST**) This is called the security target (ST)

:orange: **ITSEC 提供了6个信任级别，评价级别**。 ITSEC provided 6 levels of trust, the evaluation levels.

* **也是一个默认级别 E0 = no trust**. The was also a default level E0 = no trust

:orange: 因此，一个产品可以被归类为 C2-E3. Thus a product could be classified as C2-E3

* 该功能在 C2级别 The functionality is at the C2 level
* 评估类型为 E3。 The type of evaluation was E3

### 意义 & 局限： Impact of ITSEC

* 功能和安全保证级别的分离是一件好事。Separation of functionality and assurance levels was a good thing.
* 但是**评估**只是证实了一个供应商的要求得到了满足，但是并没有说这个要求是合理的。But the evaluation just verified that a vendor claim had been met, but did not say the claim was sensible.

## 联邦标准 & PP保护配置文件：The Federal Criteria

**在评估功能时受到 TCSEC 的严重影响** Was heavily influenced by TCSEC when evaluating functionality

* 采用ITSEC进行评价 Used the ITSEC approach for the evaluation
* **增加了第三个维度: 评估保证级别(EAL**) Added a third dimension: Evaluation Assurance level (EAL)
  * 评估测试的好坏。How well the evaluation was tested.

**为许多安全情况定义了一个 PP (保护配置文件**)。 Defined a PP (Protection Profile) for a many security situations.

* 在计算技术中，保护配置文件是一种安全措施，通常被称为通用标准。保护配置文件的主要功能是确定各种类型的安全协议的有效性，为每种策略分配等级或级别。使用这种方法，有可能评估与网络相关的安全措施的能力，以及网络中集合在一起的各个系统和组件。
* 独立于特定的产品 Independent of a specific product
* 定义的功能需求 Defined functional requirements
* 确定的保证要求 Defined assurance requirements
* 说明预期的威胁和预期的使用方法 Described anticipated threats and expected method of use

**开发了一个 PP（保护配置文件） 的公共档案注册表** Developed a public profile registry of PP’s

* 一套 FIPS (联邦信息处理标准) A set of FIPS (Federal Information Processing Standards)

## 公共准则：Common Criteria

共同标准比橙皮书有哪些改进？ What improvements do the Common Criteria make over the Orange Book

* 通用标准考虑了安全保证的3个方面。功能分为保护配置文件（Protection Profiles）和安全目标（Security Targets），前者是对安全功能的相当一般的描述，后者是更具体的安全产品。第二个维度是安全评估，它描述了验证功能的基本方法。第三个维度包含保证级别，以验证评估是否正确进行。另外一个变化是，私营公司进行了评估。The Common Criteria considers 3 dimensions to security assurance. The functionality is split into Protection Profiles, fairly general descriptions of security functionality, and Security Targets, more specific security products. The second dimension is security evaluation, which describes fundamental approaches to verifying the functionality. The third dimension contains assurance levels to verify that the evaluations had been carried out correctly. One other change is that private companies carried out the evaluations.

---

CC标准提供了统一的方式表达安全需求，并定义了一整套严格的准则，其中产品的安全层面（比如：开发环境，安全功能和安全脆弱性处理）可以得到有效的评估

现行制度(2005) Current System (2005)

最初的参与者: NSA，NIST，加拿大，英国，法国，德国，荷兰 Initial participants: NSA, NIST, Canada, UK, France, Germany, Netherlands

许多其他国家要么已经加入，要么正在加入 Many other countries have either joined or are in the process of joining

TCSEC和ITSEC于2000年左右退休 TCSEC and ITSEC were retired around 2000

---

:orange: **功能需求** Functional requirements

* 11个班，每个班有几个家庭 11 classes, each with several families

:orange: **安全保证要求** Assurance requirements

* 10个班，每个班有几个家庭 10 classes, each with several families

:orange: **评估保证水平** Evaluation assurance levels

* 7个级别，显示已完成的评价水平 7 levels showing what level of evaluation has been done

## 保护配置文件（通用标准）& 安全目标：Profiles and Targets

:orange: **保护配置文件** Protection Profile (PP)

* 为满足特定消费者需要的一类产品提供一套独立于实施的安全要求 An implementation independent set of security requirements for a category of products that meets specific consumer needs
* 有六个部分，包括功能和保证需求 Has six parts, including functional and assurance needs

:orange: **安全目标** security target (ST)

* **用作评估已识别产品的基础的安全要求** A set of security requirements to be used as the basis for evaluation
* <font color="deeppink">更具体形式的保护配置文件</font> A more specific form of protection profile
* 可参考 PP 或多于一个 PP May refer to a PP or more than one
* 可以单独开发 Can be developed in isolation
* **八个部分，包括安全功能要求和保证要求** Eight parts, including functional and assurance requirements

### 安全功能要求：Security Functional Requirements

![](/static/2021-05-05-13-12-44.png)

FAU：安全审计，6个家庭

FCO：通信，2个系列

FCS：加密支持，2个系列

FDP：用户数据保护，13个系列

FIA：识别和认证，6个系列

FMT: 安全管理，6个系列

FPR：隐私，4个系列

FPT：安全功能的保护，16

FRU：资源利用，3个系列

FTA：访问，6个家族

FTP：可信路径，2个家族

## 保证要求：Assurance Requirements

![](/static/2021-05-05-13-14-29.png)

为确保符合声称的安全功能而采取的措施 Measures taken to ensure compliance with the claimed security functionality.

APE：保护状况评估，6个系列

ASE：安全目标评估，8个系列

ACM：配置管理，3个系列

ADO：交付和运营，2个系列

ADV：开发，7个系列

AGD：指导文件，2个系列

ALC: 生命周期，4个系列

ATE：测试，4个系列

AVA: 脆弱性评估，4个系列

AMA: 维护保证，4个系列

## 评估保证级：Evaluation Assurance Levels

评估的深度和严谨性 Depth and rigor of an evaluation.

![](/static/2021-05-05-13-16-18.png)

EAL1：功能测试 EAL1: functionally tested

EAL2：结构上的测试 EAL2: structurally tested

EAL3: 方法上的测试和检查  EAL3: methodically tested and checked

EAL4: 有方法地设计、测试和审查 EAL4: methodically designed, tested and reviewed

EAL5: 半正式地设计和测试 EAL5: semi-formally designed and tested

EAL6: 半正式地验证、设计和测试 EAL6: semi-formally verified, designed and tested

EAL7：正式验证、设计和测试 EAL7: formally verified, designed and tested

## 评估过程：Evaluation Process

认可的实验室做评估是要收费的 Accredited labs do evaluations for a fee

* 投标人选择评估的实验室 Vender selects the evaluating lab
  * 一种不正当的动机（道德风险）。A perverse incentive (moral hazard).

---

* 实验室与一个监督委员会协调 Labs coordinate with an oversight board
* 实验室向委员会提交评估结果，由委员会决定授予EAL等级。 Labs presents findings to board, who decide on the EAL rating to award
* 解释委员会负责处理解释要求，并保持标准的更新。 An interpretation board deals with requests for interpretation and keeps the standards up to date
* **对产品比对系统更有效** Works better for products than systems

## 其他安全保障的方法：Other Approaches

**敌对审查** Hostile Review

* 按发现的缺陷数量给予合同奖励。Give contract rewarded by number of flaws found.
* 给几个组织相同的任务，奖励最好的。 Give several organisations the same task and reward the best.

**渗透和补丁** Penetrate and Patch

* 系统在出厂时就会有缺陷。Systems will be shipped with bugs.
* 维护一个报告缺陷的机制。Maintain a mechanism for reporting bugs.
* 让你的用户为你测试产品。Get your users to test the product for you

**免费和开放源代码** Free and Open-Source

* 安全系统不应该依赖于隐藏的机制。 Security systems should not rely on hidden mechanisms.
* 希望爱好者能免费做安全审查。 Hope that enthusiasts will do the security review for free

# 安全开发：Secure Development

* 威胁建模 Threat Modelling
* 风险管理 Risk Management
* 安全政策文件 Security Policy Document
* 部署后的安全问题 Security after deployment
* 实际管理问题 Practical management issues

## 安全软件开发：Secure Software Development

大多数软件开发方法论都有一个**收集需求的初始阶段**。Most software development methodologies have an initial stage when requirements are gathered.

**安全软件开发也是在一开始就【收集安全需求**】。 Secure software development also gathers security requirements at the start.

* 建立一个威胁模型。Build a threat model.
* 确定风险并建立适当的缓解措施。Determine risks and establish appropriate mitigations.
* 编写安全政策。Write a security policy.
* 设置安全机制。Put in place security mechanisms.
* 管理部署后的持续安全。Manage continued security after deployment.

## 建立威胁模型-寻找威胁-威胁树：Finding Threats: Threat Tree

威胁模型(威胁树)的建立可以是 The threat model can either be built

* 自上而下：从不理想的结果开始。 Top down: start with undesirable outcomes.
* 自下而上：从可能的安全失误开始。 Bottom up: start with possible security failures.
* 或者是自上而下和自下而上两者的结合。 Or a combination of both top down and bottom up.

---

什么是威胁树，自下而上和自上而下的建设有什么区别？What is a threat tree and what is the difference between bottom up and top down construction?

* **将所有【不理想的结果】列成表格，并将它们与所有【潜在的安全故障原因】联系起来的方法**。A way of tabulating all undesirable outcomes and connecting them will all potential security failures
* 自上而下的建设从不良结果开始，找到直接原因，以及它的原因，直到达到潜在的安全故障模式。Top down construction starts with an undesirable outcomes, finds the immediate cause, and its cause until the underlying security failure modes have been reached.
* 自下而上，从基本的安全故障开始，一直到不理想的结果。 Bottom up starts with underlying security failures and progresses to undesirable outcomes.

### 威胁模型建立-自上而下 - Threat Model: Top Down Construction

**从一个不理想的结果开始**。Start with an undesirable outcome.

* 写下每个**直接原因**。 Write down each immediate cause.
* 向后寻找所有直接原因的前继。 Work backwards to find all the precursors for the immediate causes.
* 继续下去，直到我们**找到基本的安全问题**。 Keep going until we arrive at fundamental security problems.

:orange: **这是一本攻击者的手册，所以应该是保密的**。 This is a manual for an attacker and so should be confidential.

---

:candy: **下面的例子从一个【不理想结果- 成功的卡片伪造】，开始** The following example starts with the undesirable outcome of a successful card forgery.

![](/static/2021-05-05-14-15-58.png)

* 它以一些**潜在的基本安全问题**而结束 It ends up with a number of potential basic security problems

### 威胁模型建立-自下而上 - Threat Model: Bottom Up Construction

:orange: **这涉及到从可能的安全故障开始（潜在基本安全问题），然后列出每个故障的后果，一直到【不理想的结果**】。This involves starting with the possible security failures and then listing the consequences of each failure, working up to the undesirable consequences.

**通常自下而上和自上而下是一起使用的** Often bottom up and top down are used together.

**最终的结果是一份文件，显示** The end result is a document that shows

* 任何**安全问题的结果**，在**底部**。The results of any security problem, the items at the bottom,
* **被保护的资产**，在**顶部**。The assets being protected, the items at the top.

![](/static/2021-05-05-14-27-19.png)

## 误用案例：Misuse Cases

威胁建模找到用例，就像需求捕捉一样。Threat modelling finds use cases, just like requirements capture.

* 在这种情况下，**用户是攻击者**。In this case the users are attackers.
* 因此它们被称为**误用案例**。Hence they are called misuse cases.

**它们也是基于情景的**。 They are also based on scenarios.

**他们列出了外部的依赖性和安全假设**。They list external dependencies and security assumptions.

* 涉及哪些其他的软件。What other pieces of software are involved.
* **在敏捷过程中，它们是误用故事** In an Agile process they are misuse stories.
* <font color="red">定义攻击面可以帮助寻找误用案例</font>。Defining attack surfaces can help with finding misuse cases.

## 攻击面:帮助寻找误用案例 - Attack Surfaces

**一个产品的攻击面是【系统中可供攻击者使用的任何部分**】。The attack surface of a produce is any part of the system that is available to an attacker.

* 我们希望最大限度地减少攻击面。 We want to minimise the attack surface.
* 减少默认执行的功能。 Reduce the functionality that executes by default.
* 减少可以使用系统各部分的人的范围。 Reduce the scope of those who can use various parts of the system.
* 减少代码的权限。 Reduce the privilege of the code.

---

attack surface of the Bodgeit Store?

* 用户在一些地方的输入。User input in a number of places.
* 使用cookies进行会话管理。Use of cookies for session management.
* 为不同的用户权限使用密码。Use of passwords for different user privileges.
* 用户可以访问的内部数据库。Internal database accessible to users.

### 为减少攻击面而提出的问题：Questions to Ask to Reduce the Attack Surface

* 这个功能是大多数用户需要的吗？Is this feature required by most users?
* 所有的微型功能都是大多数用户需要的吗？ Are all micro-features required by most users?
* 该功能是否可以远程访问？ Is the feature accessible remotely?
* 该功能是否可以被匿名用户访问？ Is the feature accessible to anonymous users?
* 该功能是否以最低的权限执行？ Is the feature executing with the lowest set of privileges?
* 你的系统是否监听端口？ Does your system listen on ports?
* 你的系统是否接受来自互联网的网络连接？ Does your system accept network connections from the internet?
* 你的系统是否需要一个防火墙策略？ Does your system require a firewall policy?

## 风险管理：Risk Management

【<font color="deeppink">确定风险并建立适当缓解措施</font> Determine risks and establish appropriate mitigations.】

**风险：我们知道事情发生的概率**。Risk: we know the probability of something happening.

**不确定性：我们不知道准确的概率**。 Uncertainty: we don’t know the probability accurately.

* 很容易预测恐惧，夸大危险。 It is easy to project fears and exaggerate the dangers.

**大多数人厌恶不确定性，并尽可能地消除不确定性**。 Most people of uncertainty-averse and try and remove uncertainty as much as possible

* 即使更坏的结果并不那么严重。Even if the worse outcome is not that severe.

**项目经理在看待风险时应该多加分析**。 The project manager should be more analytical when viewing risk.

---

:orange: **风险管理包括威胁建模和安全目标，但也包括【风险资产的价值**】。Risk management covers threat modelling and security targets but also includes the values of the assets at risk.

* 安全要花钱，**结果应与风险价值有关**。 Security costs money and the result should be related to the value at risk.

### 如何对风险进行量化和缓解？How can risks be quantified and mitigated?

估计事件发生的概率和发生时的平均货币损失。年度损失预期。Estimate probability of an event happening and the monetary average loss if it does. Annual Loss Expectancy.

风险可以被消除、减少、接受、传递、抛弃。Risks can be removed, reduced, accepted, passed on, dumped.

### 年度损失预期

:orange: **一种技术是年度损失预期（ALE**）。 One technique is Annual Loss Expectancy (ALE).

* 将所有的损失类型制成表格。有4列。 A table of all the loss types is made. There are 4 columns.
* 损失类型 Loss type
* 每项损失的平均金额 Average amount per loss
* 损失概率，每年的案件数量。 Loss probabilities, number of cases per year.
* 合并年度损失预期（ALE）. Combined ALE

:candy: **其他变体技术也类似** Other variants are similar

---

例子 ALE Example

![](/static/2021-05-05-14-47-20.png)

* **有些数量是众所周知的，有些则是猜测的**。Some quantities are well known, others guesswork.
* **概率可以通过按摩来获得所需的数量**! The probabilities can be massaged to get the desired amount!
* **通常用于显示尽职调查** Normally used to show due diligence

### 缓解风险（措施）：Mitigating Risk

【<font color="deeppink">确定风险并建立适当缓解措施</font> Determine risks and establish appropriate mitigations.】

:orange: **风险和回报之间会有权衡**。There will be tradeoffs between risks and rewards.

* 缓解策略通常要花钱。Mitigation strategies will usually cost money.
* 防止坏事发生（风险）的花费不要超过它发生时的损失。Don’t spend more preventing a bad occurrence than would be lost if it happened.

:orange: **有几种处理风险（缓解风险）的策略**。 There are several strategies for dealing with risk.

* 通过适当的措施消除风险。Remove the risk by appropriate measures.
* 减少风险。Reduce the risk.
* 接受风险，什么都不做。Accept the risk and do nothing.
* 将风险转嫁给另一个组织（保险）。Pass the risk on to another organisation (insurance).
* 甩掉责任。如果出错时你不会受到指责，就不要担心风险。Liability dumping. Don’t worry about the risk if you won’t be blamed when things go wrong.

## 编写安全政策-创建一个安全政策文件：Create a Security Policy Document

:orange: **这是一份精确的文件，详细说明了**。This is a precise document that details:

* 被保护的资产。The assets being protected.
* 各种用户终端，雇员角色可以和不可以做什么。What various user end employee roles can and can’t do.
* 谁负责执行。Who is responsible for enforcement.

:orange: **安全政策文件可以包含**。 It can contain:

* **安全策略模型**，是对类似这些系统应该具有的保护属性的一页总结。A security policy model, a one page summary of the protection properties that systems like these should have.
* 一些**保护配置文件PP**，这是实现特定安全目标安全的独立实施方式。 A number of protection profiles, an implementation independent way of achieving security for a given security target.
* 一些**安全目标**，即该实施的详细保护机制 A number of security targets, the detailed protection mechanisms of this implementation.
* 一个安全策略文件包含保护配置文件和安全目标 A security policy document contains protection profiles and security targets

### 保护配置文件 & 安全目标 & FIPS标准：Profiles and Targets

**有一套标准配置文件开发的 FIPS (联邦信息处理标准**)。There are set of standard profiles developed by FIPS (Federal Information Processing Standards).

* 他们可以指导安全目标的开发 They can guide the development of security targets.

:orange: **保护配置文件** (PP) Protection Profile (PP)

* 为满足特定消费者需要的一类产品提供一套独立于实施的安全要求 An implementation independent set of security requirements for a category of products that meets specific consumer needs

:orange: **安全目标** Security Target (ST)

* 用作评估已识别产品的基础的**一套安全要求** A set of security requirements to be used as the basis for evaluation of an identified product
* 更具体形式的保护配置文件 A more specific form of protection profile
* 可参考一个或多个保护配置文件 May refer to one or more protection profile
* 可以单独开发 Can be developed in isolation

### 安全功能要求:Security Functional Requirements

* FAU：安全审计，6个家庭 FAU: security audit, 6 families
* FCO：通信，2个系列 FCO: communications, 2 families
* FCS：加密支持，2个系列 FCS: cryptographic support, 2 families
* FDP：用户数据保护，13个系列 FDP: user data protection, 13 families
* FIA：识别和认证，6个系列 FIA: identification and authentication, 6
* FMT: 安全管理，6个系列 FMT: security management, 6 families
* FPR：隐私，4个系列 FPR: privacy, 4 families
* FPT：安全功能的保护，16 FPT: protection of security function, 16
* FRU：资源利用，3个系列 FRU: resource utilisation, 3 families
* FTA：访问，6个家族 FTA: access, 6 families
* FTP：可信路径，2个家族 FTP: trusted path, 2 families

## 设置安全机制：Security Mechanisms

**有许多方式可以强制执行安全策略**（安全策略是在安全系统中强制执行的一组语句）。There are a number of ways in which the security policy can be enforced.

**保证要求**详细说明了显示安全的方式。 Assurance requirements detail the ways in which security is shown.

* 也有不同的方式来评估这些要求。 There are also different ways of evaluating these requirements.

:orange: **保证要求和评估保证级之间的区别是什么**？ 举出两者的例子 What is the difference between an assurance requirement and an evaluation assurance level?  Give examples of both.

* 保证要求是为检查声称的安全功能而采取的措施。FIPS有一套标准。例如，在产品的开发中采取的措施。An assurance requirement is a measure taken to check on the claimed security functionality. FIPS have a set of standards. For example, measures taken in development of the product.
* 一个评估保证级是指对评价的检查程度，例如，正式验证、设计和测试。An evaluation assurance level is how well an evaluation was checked, for example, formally verified, designed and tested.

---

### FIPS评估保证要求：Assurance Requirements

下面是FIPS标准。 Here are the FIPS standards.

**为确保符合声称的安全功能而采取的评估**。 Measures taken to ensure compliance with the claimed security functionality.

* APE：保护配置文件评估，6个系列 APE: protection profile evaluation, 6 families
* ASE：安全目标评估，8个系列 ASE: security target evaluation, 8 families
* ACM：配置管理，3个系列 ACM: configuration management, 3 families
* ADO：交付和运营，2个系列 ADO: delivery and operations, 2 families
* ADV：开发，7个系列 ADV: development, 7 families
* AGD：指导文件，2个系列 AGD: guidance documentation, 2 families
* ALC: 生命周期，4个系列 ALC: life cycle, 4 families
* ATE：测试，4个系列 ATE: tests, 4 families
* AVA: 脆弱性评估，4个系列 AVA: vulnerabilities assessment, 4 families
* AMA: 维护保证，4个系列 AMA: maintenance assurance, 4 families

### 评估保证级：Evaluation Assurance Levels

评估的深度和严谨性。Depth and rigor of an evaluation.

* EAL1：功能上的测试 EAL1: functionally tested
* EAL2: 结构上的测试 EAL2: structurally tested
* EAL3: 有方法地测试和检查 EAL3: methodically tested and checked
* EAL4: 有方法地设计、测试和审查 EAL4: methodically designed, tested and reviewed
* EAL5: 半正式地设计和测试 EAL5: semi-formally designed and tested
* EAL6: 半正式地验证、设计和测试 EAL6: semi-formally verified, designed and tested
* EAL7：正式验证、设计和测试 EAL7: formally verified, designed and tested

## 贯穿整个生命周期的保证：Assurance Through the Lifecycle

### 概念

**生命周期。概念** Lifecycle: Conception

* 从一个想法开始 Start with an idea
* 概念证明表明想法是有价值的 Proof of concept shows idea has merit
* 必须证明安全的可行性 Security feasibility must be demonstrated
  * 安全对于这个概念意味着什么？ What does secure mean for this concept?
  * 这个概念能安全吗？ Can this concept be secure?
  * 该组织是否愿意为安全付费？ Is the organisation willing to pay for the security ?
* 识别威胁 Identify threats
* 如何展现安全保证？ How will assurance be shown?

### 制造

**生命周期。制造** Lifecycle: Manufacture

* **计划的制定** Development of plans
  * 市场和销售，开发，测试策略 Marketing and sales, Development, Testing strategies
* **技术阶段** Technical stages
  * 设计、实施、工具 Design, implementation, tools
* **管理阶段** Management
  * 计划、安排、审查、文件指南 Planning, scheduling, reviewing, documentation guidelines

### 部署 & 现场产品寿命

**生命周期。部署** Lifecycle: Deployment

* 生产、分发、运输 Production, distribution, shipping
  * 确保安全在这些阶段得到保护 Make sure security protected in these stages
* 安装和配置 Installation and configuration
  * 对服务人员的培训 Training of service personnel
  * 操作环境 Operating environment

**生命周期。现场产品寿命** Lifecycle: Fielded Product Life

* 错误修复、维护、客户服务 Bug fixing, maintenance, customer service
  * 修改必须符合安全要求 Modifications must meet security requirements
* 产品退役 Product retirement
  * 删除敏感数据 Remove sensitive data

## 部署后管理安全要求的演变

管理部署后的持续安全。

**部署后如何管理安全？有哪些挑战**？How is security managed after deployment? What are some of the challenges?

* 系统需要被监控，以发现漏洞。漏洞需要尽快修复，所以要有一个随时准备行动的团队。必须有一个机制来安全地分发错误的修复。必须提前准备好新闻发布。The system needs to be monitored for bugs. Bugs need to be fixed as quickly as possible, so have a team ready to go in action. A mechanism must be in place to distribute bug fixes securely. Press releases must be prepared in advance.

---

### 安全要求变化原因

**安全要求的变化有四个原因**。There are four reasons why security requirements change.

* 错误修复。 Bug fixes.
* 根据经验调整控制。Tune the controls based on experience.
* 不断变化的环境 An evolving environment
* 组织的变化。The organisation changes.

:candy: <font color="deeppink">随着时间的推移，软件无法继续发展，那么就需要重新开发</font>。Over time the software can’t keep evolving and then a redevelopment is needed

### 安全要求变化原因 - 缺陷修复 :Bug Fixes

**缺陷需要快速修复**。Bugs need to be fixed quickly.

**信息可以被广泛传播**。 Information can be spread widely.

* 客户会变得惊慌失措。Customers can become alarmed.
* 攻击者可以发现新的漏洞。Attackers can find out the new exploits.
* 许多国家要求组织报告任何安全和隐私的漏洞。Many countries require organisations to report any security and privacy breaches.

**缺陷修复包括四个步骤** There are four steps involved

* 监测 Monitoring
* 修复 Repair
* 分发 Distribution
* 与媒体打交道 Dealing with the press

#### 检测：Bug Fixes: Monitoring

**顾客往往是第一个发现问题的人**。Customers are often the first to discover a problem.

* 有一个让顾客报告问题的机制。 Have a mechanism for customers to report problems.
* 提供奖励，使之值得他们去做。Offer rewards to make it worth their while.

**追踪错误报告网站**。 Keep track of bug reporting websites.

**确保有人负责监测bug**。 Make sure someone is responsible for monitoring bugs.

#### 修复 - Bug Fixes: Repair

许多应用都是时间性的。Many applications are time critical.

* **需要有人随叫随到，以应对紧急情况**。Someone needs to be on-call to respond to emergencies.
* **他们需要得到一个响应团队的支持**。 They need to be supported by a response team.
  * 有关各种类型的bug的技术专家。 Technical expertise on various types of bugs.
  * 一个测试机制，以便在发布之前可以快速测试错误的修复。 A testing mechanism so that bug fixes can be tested quickly before being released.

:orange: **文档需要更新以反映变化**。 The documentation will need to be updates to reflect the changes.

* 这可能意味着对威胁模型和安全目标的改变。 This might mean changes to the threat model and security target.

#### 分发-Bug Fixes: Distribution

:orange: **在客户数量较少**的组织中，错误修复可以只是 就可以**只向出现故障的客户推广**。In organisations with a small number of customers, the bug fix can just be rolled out to the customer experiencing the downtime.

:orange: **如果有更多的客户，定期更新机制是合适的**。 With more customers, a regular update mechanism is appropriate.

* 与微软合作的周二补丁。Patch Tuesday with Microsoft.
* 确保你能应付突然激增的流量。Make sure you can cope with sudden surges of traffic.

**确保你的错误修复已经被彻底测试**。 Make sure your bug fix has been tested thoroughly.

* 作为客户，通常最好是等待一段时间，让急切的客户发现，补丁会使他们的机器变砖。As a customer, it is often better to wait a while to let eager customers discover that the patch bricks their machine.
* 拖延会让漏洞再开放一段时间。A delay leaves the vulnerability open for a while longer.

#### 与媒体打交道 - Bug Fixes: Dealing with the Press

**新闻界不会离第一份安全漏洞报告太远**。The press won’t be far behind the first report of a security breach.

* **仅仅解决这个问题还不够，你必须让人们看到你已经解决了这个问题**。 It is not enough to fix the problem, you must be seen to have fixed the problem.

**准备一套已经写好的新闻稿，处理所有可能发生的情况**。 Have a set of press releases allready written, dealing with all possible scenarios.

* 你只需编辑细节，使其具体到实际的漏洞。 You just have to edit the details to make them specific to the actual breach.
* 你可以避免显得狡猾或无能。 You avoid appearing shifty or incompetent.

### 安全要求变化原因-根据经验调整控制：Tune the Controls based on Experience

:orange: **这与错误修复非常相似**，但 This is very similar to bug fixing, but

* 驱动力**不是发现一个错误**。 The driver is not the discovery of an error.
* **它是基于你的产品正在被使用的新方式**。 It is based on new ways in which your product is being used.
* **它也是由不断变化的威胁环境驱动的**。 It is also driven by the evolving threat environment.

:orange: **这使得根据经验调整控制具有主动性**。 This makes it proactive.

* 你审查你的威胁模型和安全目标，并 **在你受到攻击之前做出必要的改进**。 You review your threat model and security target and make the necessary improvements before you are attacked.
* 这可能很难向管理层推销，。**因为你要花钱修复一个没有人攻击过的潜在漏洞**。 This might be difficult to sell to management because you are spending money to fix a potential hole that no one has attacked.
  * 如果你成功了，那么就不会有攻击了! If you are successful then there is no attack!

### 安全要求的变化-应对不断变化的环境 Responding to an Evolving Environment

**操作系统的改变会在底层平台中引入新的错误**。An operation system change can introduce new bugs in the underlying platform.

**系统可能开始在具有不同计算机犯罪法律的外国运行**。 Systems may start operating in foreign countries with different computer crime laws.

理论上的攻击变得实际。 Theoretical attacks become practical.

* DDOS
* 网络钓鱼。Phishing.

**当你的系统依赖于无人负责的其他系统和设备时，这可能很困难**。This can be difficult when your systems depends on other systems and devices that no one is responsible for.

* 许多系统使用卡支付系统。Many systems use card payment systems.
* 没有一个用户有足够的动机来确保他们的安全 No one user has enough incentive to make sure they are safe

### 安全要求变化原因-组织变化：Organisational Change

这些往往是造成安全问题的主要原因。These are often the major causes of security problems.

**业务流程重组**。 Business process reorganisation.

* 采用一种计算机系统，迫使用户以一种不同的、往往是效率较低的方式工作。 Adopt a computer system that forces users to work in a different and often less efficient way.
* 设计不良的系统和怨恨的员工是灾难的源头。 Poorly designed systems and resentful staff is a recipe for disaster.
* 员工会找到更不安全的变通方法来跟上他们的工作量。 Staff will find less secure workarounds to keep up with their workload.

过去，银行分行的员工都有很好的工资和长期的职业。 Bank branches used to be staffed by people with good wages and a long term career.

* 如今，工资很低，工作人员的流动率很高。 These days wages are very low and staff turnover is high.
* 内部安全威胁则大不相同 The insider security threat is much different

## 实际管理问题：Practical Management Issues

* 萨班斯-奥克斯利法案 Sarbanes-Oxley
* 解决错误的问题。 Solving the wrong problem.
* 道德风险/反常的激励。 Moral hazard / perverse incentives.
* 内部人攻击。Insider attacks.
* 掩盖过失。 Fault Masking.

### Sarbanes-Oxley

这项立法是在安然事件后通过的，要求公司 展示减轻风险的内部控制。This legislation was passed after Enron and requires companies to demonstrate internal controls that mitigate risk.

组织创建一个风险登记册，以识别和记录风险。 Organisations create a risk register that identifies and documents risks.

每个风险都有一个所有者，负责监控风险并决定适当的对策。决定适当的应对措施。 Each risk has an owner who is responsible for monitoring the risk and deciding on appropriate countermeasures.

内部政治会影响这个过程。 Internal politics will effect this process.

* 在责备文化中，没有人愿意拥有一个风险，因为如果出了问题，他们会被责备。 In a blame culture, no one will want to own a risk since they will be blames if things go wrong.
* 在一个解决问题的文化中，处理风险可以是一条通往进步的道路。晋升的途径。In a problem solving culture, dealing with risks can be a route to advancement.
* S-O是一个过程，管理人员可以通过这个过程被成功起诉。S-O is a process whereby managers can be prosecuted successfully.

### 解决错误的问题：Solving the Wrong Problem

【why人们经常解决错误的问题 - 在我们熟悉的事情上努力工作，而不是处理意外的情况，是比较容易的。这种情况可能会使框框式的管理态度变得更糟。It is easier to work hard at something we are familiar with, rather than deal with an unexpected situation. This can be made worse a box-ticking management attitude.】

**人类的天性是，当面临一个困难的问题时，人们 往往非常努力地去解决一个相关的更容易的问题**。It is human nature that when faced with a difficult problem, people often work very hard to solve a related easier problem.

* **这通常是由避免不确定性的愿望驱动的**。 This is often driven by the desire to avoid uncertainty.
  * 管理者更喜欢核对清单上的方框。 Managers prefer a check-list box-ticking exercise.
  * 他们相信，如果他们的下属执行了一系列明确的任务，那么安全就会随之而来。They believe that if their underlings perform a set of well-defined tasks then security will follow.
* **这种方法往往会阻止批判性思考**。This approach tends to prevent critical thought.
* 没有考虑到每个案例中的实际安全要求。殊情况下的实际安全要求。 There is no consideration of the actual security requirements in each particular case.
* 有一个基于 "共同标准 "的安全认证行业。标准 "的检查清单。 There is a whole security certification industry based on the ‘Common Criteria’ check lists.

---

**作为一个例子，智能卡已经受到了探测和功率分析的攻击**。As an example, smartcards have come under attack from probing and power analysis attacks.

* 运行加密算法会消耗多少功率。How much power is consumed running crypto algorithms.

**这个问题是通过限制设计信息的可用性来解决的**。This was tackled by restricting available of design information.

* 真正的攻击并不取决于设计信息 The real attacks didn’t depend on design information

### 道德风险/反常的激励：Moral Hazard/perverse incentives.

**一个人做决定，另一个人被要求对后果负责的情况**。举例来说，一个销售团队可能会把抵押贷款卖给那些信用不良的人，在这个过程中赚取大量销售奖金。然后，该金融组织可能会破产，因为当水管的价值下降时，有太多人拖欠抵押贷款。A situation where one person makes the decisions and another is made responsible for the consequences. As an example, a sales force might sell mortgages to people who are bad credit risks, making large sales bonuses in the process. The financial organisation might then go bust because too many people default on their mortgages when the value of hoses goes down.

---

有很多情况下，做出重要决定的人在事情出错时并没有承担后果。There are many situation where people who make important decisions do not suffer the consequences when things go wrong.

* 工作人员流失率高。 High staff turnover.
* 广泛地使用承包商。 Extensive use of contractors.

另一种情况是，组织中的一部分人将某些决定所产生的利润归功于自己，而另一部分人则承担损失。 Another variant is when one part of an organisation takes the credit for profits generated by some decisions, while another part takes the loss.

* 市场部为销售量的增长邀功。 The marketing department takes the credit for rising sales.
* 财务部门不得不应对不断增加的坏账。 The finance department has to cope with increasing bad debt

#### 开发者的角色：Developer Roles

组织开发团队的一种方式是将程序员和测试员分开。One way of organising a development team is to have separate people as programmers and testers.

程序员有动力去生产尽可能多的代码。 The programmers have an incentive to produce as much code as possible.

如果有错误，测试人员就必须处理它。 If there are bugs then the testers have to deal with it.

如果没有人拥有代码的话，无自我的编程也会导致道德风险。 Egoless programming can also cause a moral hazard if no one owns the code.

* 有一种快速产生坏代码的动机。There is an incentive to produce bad code quickly.

在Agile方法中，程序员必须测试他们自己的代码。 In Agile approaches, programmers have to test their own code.

* 如果它不是很好，那么他们必须处理后果。If it not very good then they have to deal with the consequences

### 内部人攻击：Insider Attacks

大多数安全漏洞是由内部攻击造成的。Most security breaches are caused by insider attacks.

* 恶意  Malicious
* 只是不小心 Just careless

要让管理层承认这可能是一个问题，往往在政治上很困难。 It is often politically difficult to get management to admit that this might be a problem.

* 还要让他们监督向他们报告的员工。And also getting them to monitor staff who report to them.

往往需要间接地攻击这个问题。 It is often necessary to attack this problem obliquely.

* 要求并获得资金来抵御不存在的邪恶黑客。 Ask for and get money to defend against non-existent evil hackers.
* 把大部分钱花在也能抓住不诚实或粗心的工作人员的控制措施上。 Spend most of it on controls that will also catch dishonest or careless staff.

### 掩盖过失：Fault Masking

**故障有可能被不同安全技术的组合所掩盖**。It is possible that faults will be masked by a combination of different security techniques.

一家银行意外地给所有的客户发放了相同的PIN码。 One bank accidently issued all of its customers with the same PIN.

* 这并没有被注意到，因为**PIN码的发放单元与卡片操作单元是分开的**。 This was not noticed because the PIN issuing unit was kept separate from the card operating unit.
* 这是为了防止内部人员知道PIN码以及所有卡的细节。This is to prevent an insider from knowing the PIN as well as all the card details.
* 这影响了可测试性。 This affects testability.

## 开发者问题：Developer Issues

**开发人员如何提高其代码的质量**？How can developers improve the quality of their code

* 代码审查。在最初的设计中涵盖安全问题。尽量减少攻击面：用户是否真的需要能够做到这一点。使用渗透和模糊测试。考虑误用案例或故事。Code reviews. Cover security issues in the initial design. Minimise the attack surface: does the user really need to be able to do this. Use penetration and fuzz testing. Consider misuse cases or stories.

* 软件质量。Software quality.
* 安全设计原则。 Secure design principles
* 安全测试。 Security testing.
* 模糊测试。 Fuzz testing.
* 使用敏捷方法的安全开发。 Secure development with an Agile approach.

### 软件质量：Software Quality

**如果软件不能很好地工作，那么会有很多与安全无关的错误**。 If the software doesn’t work very well then there will be lots of errors that are unrelated to security.

**安全问题通常不会影响软件的工作方式**。 Security problems do not usually affect the way the software works.

* 除非是DOS攻击。 Unless it is a DOS attack.

**当工作人员为解决功能问题而捉襟见肘时，它们更有可能被忽略**。 They are more likely to be missed while the staff are stretched trying to resolve functionality issues.

**具有较好软件质量的系统也更安全**。 Systems with better software quality are also more secure.

**确保软件质量的最有效方法是代码审查**。 The most effective way of ensuring software quality is a code review.

## 安全设计原则：Secure Design Principle：

**减少代码中的复杂性**。Reduce complexity in the code.

* 这是一个普遍的好的编码原则，并导致了面向对象的编程。This is a general good coding principle and led to object-oriented programming.

**失败安全的默认值** Fail safe defaults

* 如果任何用户操作失败，那么系统就会停止工作。If any user action fails then the system stops working.

**每个用户对代码的每个安全部分的访问都应该被检查**。 Every user access to every secure part of the code should be checked.

* 即使它已经在外层的代码中被检查过了。 Even if it has already been checked in an outer layer of code.

**开放式设计，不要试图通过隐蔽性来保证安全**。 Open design, do not try security through obscurity.

* Kerchoff法则。系统不应该依赖于保密性。如果敌人掌握了代码，他们不应该有任何优势。Kerchoff’s Law: The system should not depend on secrecy. The enemy should not have any advantage if they get hold of the code

**最低权限：以完成操作所需的最低权限进行操作**。Least privilege: operate with the least privilege required to complete the operation. 

**尽量减少共享资源**。Minimise shared resources.

**确保安全行动对用户来说是容易使用的**。 Make sure the security actions are easy for users to use.

* 否则用户将禁用防御措施 Otherwise users will disable the defences

## 安全测试：Security Testing

* 模糊测试 Fuzz testing
* 渗透测试 Penetration testing
* 运行时验证 Run-time verification
* 审查威胁模型。 Reviewing threat models.
* 重新审视攻击面。 Revisiting attack surfaces.

**请注意，代码测试发现错误**。 Note that code testing finds bugs.

* 它**不会发现设计错误**。 It does not find design errors.
  * 它们是由用户测试发现的。 They are found by user testing.

### 模糊测试：Fuzz Testing

**这只是意味着使用不正确的输入**。 This just means using incorrect input.

* 它经常被应用于文件格式，例如图像文件格式。 It is often applied to file formats, eg image file formats.
* 它可以用于网络协议。 It can be used with network protocols.
* 如果我们的软件提供了一个API，它也可以用于API调用。 It can also work with API calls if our software provides an API.

**有很多不同的不正确的输入类型**。There are a lot of different incorrect input types.

**大量的测试用例需要被生成**。 A lot of test cases need to be generated.

* 自动化工具在某些情况下可以提供帮助。 Automated tools can help in some circumstances.

**智能模糊测试：尝试所有可能的错误输入** Smart fuzzing: try all possible wrong inputs

**愚蠢的模糊测试：随机地改变数据的位**。 Dumb fuzzing: randomly change bits of the data.

* 抓住被 "智能 "模糊测试遗漏的案例。 Catch cases missed by ‘smart’ fuzzing.

## 使用敏捷方法-安全开发：Secure Development with Agile Methods

**敏捷项目的启动**。Agile project startup.

* 任命一个安全指导员，即安全问题的首选人员。Appoint a security coach, the go-to person for security questions.
* 这个角色可以随意调动，但在限制范围内，这个人必须 了解安全编码。This role can be moved around, but within limits, the person must understand secure coding.
* 结对编程可以帮助传播安全编码实践。 Pair programming can help spread secure coding practice.

**可以不定期地运行安全尖峰，以测试威胁模型**。 A security spike can be run from time to time to test the threat model.

* 也可以运行它们来学习安全的不确定方面。They can also be run to learn about uncertain aspects of security.
* 它们可以被定期添加到产品积压中。 They can be added to the product backlog on a regular basis.
* 将安全约束添加到约束列表中。 Add security constraints to the list of constraints.

**用户故事** User stories

* 测试可以检查被禁止的功能是否已被使用 Tests can be checking to see if banned functionality has been used

# 立法：Legislation

## UK Legislation

* 1990年计算机滥用法。Computer Misuse Act 1990.
* 2000年调查权力监管法（RIPA）。 Regulation of Investigatory Powers Act 2000 (RIPA).
* 1998年数据保护法。 Data Protection Act 1998.
* 1988年版权、设计和专利法。 Copyright, Design and Patents Act 1988.

苏格兰和英国的法律是不同的。 Scottish and English law is different.

一个区别是，苏格兰法律要求有确凿的证据，而英国法律则没有。 One difference is that Scottish law requires corroborating evidence while English law does not.

* 在苏格兰，仅有一个来源是不够的 A single source is not enough in Scotland

---

苏格兰法律和英国法律之间有什么区别对数字取证很重要？What difference between Scottish and English law is important for digital forensics?

* 苏格兰对协作性证据的需求。 The need for collaboratory evidence in Scotland.

## 1990年计算机滥用法：Computer Misuse Act 1990

没有关于什么是计算机的定义。No definition of what a computer is.

1. 未经授权访问计算机材料 Unauthorised access to computer material
   * 必须知道该访问是未经授权的。不包括鲁莽的行为。There must be knowledge that the access is unauthorised. Reckless behaviour is not covered.
   * 必须有访问程序或数据的意图。There must be intent to access a program or data.
2. 未经授权的访问，意图实施或协助实施进一步的犯罪行为。Unauthorised access with intent to commit or facilitate commission of further offensives.
3. 意图损害计算机运行的未经授权的行为。Unauthorised acts with intent to impair the operation of a computer.
   * 这包括阻止访问：拒绝服务。 This includes preventing access: denial of service.
   * 鲁莽的行为也包括在内。Reckless behaviour is covered.

根据《计算机滥用法》，有哪些攻击行为 What offensives are there under the Computer Misuse Act?

* 未经授权的访问，损害了计算机的运行。制造严重损害的风险（所以不需要实际损害）。制作或提供物品以帮助以前的攻势。 Unauthorised access, impairing the operation of a computer. Creating a risk of serious damage (so no actual damage needed). Making or supplying articles to help the previous offensives.

### 计算机滥用法修正案：Computer Misuse Act Amendments

2006年的《警察和司法法》提高了最高刑期。The Police and Justice Act of 2006 increased maximum sentences.

2015年的《严重犯罪法》增加了一些新内容。 The Serious Crime Act of 2015 added some new aspects.

* 有一些澄清，也有一些新的罪行。There were a number of clarifications, as well as new offences.

3ZA 对人类福利、环境、经济或国家安全造成或创造严重损害风险的未经授权的行为。Unauthorised acts causing or creating a risk of serious damage to human welfare, the environment, economy or national security.

* 攻击关键基础设施。Attacks on critical infrastructure.

3A 制作、供应或获得用于违反第1、3、3ZA条的犯罪的物品。3A Making, supplying or obtaining articles for use in offenses contrary to sections 1, 3, 3ZA.

* 制作或提供恶意软件。Making or supplying malware.

## RIPA 2000

对调查权力的监管。Regulation of Investigatory Powers.

* 公共机构被允许做什么。What public bodies were allowed to do.

非法截获通信。 Unlawful interception of communications.

* 公共电信系统 Public telecommunication system
* 私人电信系统 Private telecommunication system

侵入网络。 Hacking into a network.

在2003年，2005年，2006年，2010年修订。 Amended in 2003, 2005, 2006, 2010.

---

RIPA涵盖什么？

* 非法通信。规定了公共机构可以做什么 Unlawful communications. Defines what public bodies are allowed to do.

## RIPA的权力:RIPA Powers

**某些公共机构可以要求互联网服务供应商提供对客户通信的秘密访问**。Certain public bodies can demand that an ISP provide access to customers communications in secret.

**某些公共机构可以要求ISP安装设备，以方便监视**。 Certain public bodies can demand that an ISP fit equipment to facilitate surveillance.

**启用大规模监控运输中的通信**。 Enabled mass surveillance of communications in transit.

**某些公共机构可以要求某人交出加密的 密钥**。 Certain public bodies can demand that someone hand over encryption keys.

**阻止拦截令的存在和任何收集的数据 防止在法庭上被披露**。 Prevents the existence of interception warrants and any data collected from being revealed in court.

**使用RIPA权力需要得到授权**。 Use of RIPA powers needs to be authorised.

* 2010年批准了约10,000份监视令 Around 10,000 surveillance orders granted in 2010

## 版权、设计和专利法1988:Copyright, Design and Patents Act 1988

* 为数字作品提供保护。To provide protection to digital works.
* 涵盖旨在规避电子形式作品的复制保护的装置。 Covers devices designed to circumvent copy-protection of works in electronic form.
* 欺诈性地接收传输信息。 Fraudulent reception of transmissions.
* 版权有效期为70年或50年 Copyright last either 70 or 50 years

---

版权、设计和专利法》包括哪些内容？What does the Copyright, Design and Patents Act cover?

* 保护知识产权。Protects intellectual property

## 辩方论点:Defence Arguments

木马的辩护。The Trojan defence.

* 犯罪是由其他人使用恶意软件访问我的电脑而犯下的。The crime was committed by someone else accessing my computer using malware.
* 我是否鲁莽地没有保护好我的电脑？ Was I reckless in not securing my computer?

我的无线宽带被其他人使用。 My wireless broadband was used by someone else.

* 我没有保护我的WiFi是鲁莽的吗？Was I reckless in not protecting my WiFi?

## 1998年数据保护法:Data Protection Act 1998

覆盖 Covers

* 获取或披露个人数据。Obtaining or disclosing personal data.
* 促成个人数据的披露。Procuring the disclosure of personal data.
* 出售或提议出售个人数据。Selling or offering to sell personal data.

数据保护法》包括哪些内容？What does the Data Protection Act cover?

* 获取或披露个人数据。信息自由法和主体访问请求 Obtaining or disclosing personal data. The freedom of information act and subject access requests

---

**保护个人数据**。Protects personal data.

* 有权以合理的费用查看某组织持有的他们的数据。合理的费用。Right to view the data an organisation holds on them for a reasonable fee.
* 要求纠正不正确的信息。Request that incorrect information be corrected.
* 不能以可能造成损害或困扰的方式使用数据。困扰。 Data cannot be used in a way that may potentially cause damage or distress.
* 数据不能被用于直接营销。 Data cannot be used for direct marketing.
* 用户需要同意收集和使用他们的数据。 The user needs to consent to the collection and use of their data.

2000年《信息自由法》修改了公共机构的法案。机构。 The Freedom of Information Act 2000 modified the act for public bodies.

对国家安全、犯罪和税收以及家庭目的的豁免。 Exemptions for national security, crime and taxation and domestic purposes (the individual and family).

## 主体访问要求:Subject Access Requests

* 这允许个人了解一个组织是否持有关于他们的个人数据。This allows an individual to find out if an organisation is holding personal data about them.
* 这项权利被2018年的《通用数据保护条例》修改并变得更加强大。 This right was modified and made more powerful by the General Data Protection Regulation of 2018.
* 信息专员监督遵守和执行情况。 The Information Commissioner monitors compliance and enforcement.
