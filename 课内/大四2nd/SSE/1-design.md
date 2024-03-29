# Content

* [Content](#content)
* [Intended Outcome](#intended-outcome)
* [Outline](#outline)
* [软件设计定义：What is Software Design](#软件设计定义what-is-software-design)
* [设计-一种全局概念：Design is a Universal Activity](#设计-一种全局概念design-is-a-universal-activity)
* [设计出现在不同层次：Design Occurs at Different Levels](#设计出现在不同层次design-occurs-at-different-levels)
* [设计步骤：Process of Design](#设计步骤process-of-design)
* [证明性框架（设计使软件可衡量）: Justificatory frameworks](#证明性框架设计使软件可衡量-justificatory-frameworks)
* [设计原则驱动设计：设计通常以一套设计原则为指导](#设计原则驱动设计设计通常以一套设计原则为指导)
* [软件开发原则 - SOLID Principles：Software Development Principles](#软件开发原则---solid-principlessoftware-development-principles)
* [设计中的安全原则 - OWASP Development Guide(应用安全原则)： Security By Design Principles](#设计中的安全原则---owasp-development-guide应用安全原则-security-by-design-principles)
* [隐私设计原则：Privacy-By-Design Principles](#隐私设计原则privacy-by-design-principles)
* [如何达成良好设计：Finding a Good Design](#如何达成良好设计finding-a-good-design)
* [设计决策：Making Design Decisions](#设计决策making-design-decisions)
* [设计决策特性：Characteristics of Design Decision Making](#设计决策特性characteristics-of-design-decision-making)
* [设计困难：Why is Design Difficult](#设计困难why-is-design-difficult)
* [设计好处/重要性-为什么设计：Why Design?](#设计好处重要性-为什么设计why-design)
* [设计模型：Double Diamond - Double Diamond Design Model](#设计模型double-diamond---double-diamond-design-model)
* [决策者：Who are the decision Makers](#决策者who-are-the-decision-makers)

# Intended Outcome

![](/static/2022-01-20-16-46-18.png)

# Outline

![](/static/2022-01-20-23-56-19.png)

* differences between engineering & development
  * 开发 - 如何设计，组成产品
  * 工程 - 如何设计，使得组件有效组织起来，以一种可以理解的方式进行设计。可扩展，遵循良好实践，使开发者和团队自己也能受益
    * 关于决策，设计决策，及结果

# 软件设计定义：What is Software Design

![](/static/2022-01-21-15-00-12.png)

* Design bridges the gap between knowing what is needed (requirements specification) and what code is required to make it functional - 设计在知道需要什么（需求说明）和需要什么代码来实现其功能之间架起了桥梁。.
* Design marks the transition from understanding the problem to inventing a solution for it. 设计标志着从理解问题到为其发明一个解决方案的过渡
* Design is a verb and noun. Design is both a process and a result/product that is acquired iteratively. 设计是一个动词和名词。设计既是一个过程，也是一个反复获得的结果/产品

# 设计-一种全局概念：Design is a Universal Activity

![](/static/2022-01-21-19-28-33.png)

**设计区域是一种普遍的活动，在一个产品中，由更多原始元素组成的集合体可以从设计活动中受益**。desgin zone

现代软件工程通常涉及多个软件模块的协调，在这个意义上，它本质上是关于理解如何协调所有这些单独的组件，当涉及到协调这些系统时，我们所做的任何决定都会对需求产生影响，

我们在接受需求和理解这些需求如何转化为某种功能的软件设计时做出的设计选择。我们所做的选择将影响到什么样的威胁适用于该系统，这些威胁有多大的可能性表现出来，而且本课程的一部分目的是试图让我们在不同的表现层次上思考这些威胁

# 设计出现在不同层次：Design Occurs at Different Levels

![](/static/2022-01-22-00-13-57.png)

现在，**设计发生在不同的层次**，因为我们可以很容易地表示软件系统和各种抽象层次。在低层次上我们有代码。有数据结构。算法，在这个课程中，我们将解开不同的方式来检查代码。因此，SSC的**静态分析部分将专注于软件系统的低层次设计，以尝试和了解软件系统中可能存在的潜在漏洞** Now design occurs at different levels because we are, we can easily represent software systems and various levels of abstraction. So at the low level we have the code. We have our data structures. We have our algorithms and we're going to be unpacking different ways by which we can examine code in this course. So the static analysis component of SSC is going to focus on the low level design of software systems to try and understand where potential vulnerabilities may exist within software system and likewise. 

我们还可以应用不同的方法和技术来观察更抽象的表示层次。你的中层设计，如你的类，你的UML图，甚至你的高层系统架构表示。We can also apply different methods and techniques to look at more abstract levels of representation. Your mid level design, such as your classes, your UML diagrams or even your higher level system architectural representations.

威胁建模的方法，stride和攻击树都非常好。**一个经过测试的方法来理解系统架构所面临的风险和威胁，而不是该系统架构的低水平实现**。Going to be having a look up, threat modeling approaches, stride and attack trees are very good. A tried and tested approaches to understanding the risks and the threats that are faced by a systems architecture rather than the low level implementation of that system architecture.

:orange: 因此，能够**理解如何在不同的抽象层次上分析软件系统**是很重要的，因为很多时候你会和拥有**各种技术经验和不同背景的人**一起工作。So it's important to be able to understand how you can analyze software systems at various levels of abstraction, because it's quite often the case that you will be working with people who have a range of technical experience and different backgrounds

* 例如，你的**同事**很可能是有技术头脑的。他们能够**理解软件系统的低层次设计**，因为他们和你一起开发。你的团队领导很可能也是如此，但如果你与**项目经理**联络，例如，这些人对**软件项目的方向**会有发言权，会有某种投入，但他们不一定是技术出身，不一定了解。例如，Java和JavaScript之间的**区别，但能够应用方法和技术向这些利益相关者阐述**。Your coworkers, for example, will likely be technically minded. They'll be able to understand the low level design of a software system because they're developing it with you. Your team lead will likely be in the same boat, but if you are liaising with project managers, for example, these people will have a say and will have some kind of input into the direction of a software project, but they may not necessarily come from a technical background and understand.For example, the difference between Java and JavaScript, but being able to apply methods and techniques to articulate to these stakeholders.
* 在一个软件系统中，**高水平的威胁**在哪里，将**使你能够融入各种不同的团队**和两个。与他们进行有效的沟通，所以我们很容易认为我们会在日常工作流程中与其他开发者合作，但在我们不得不与其他**不一定有技术头脑的人合作的情况下，能够理解我们如何抽象出我们的软件代表，并对该代表进行某种分析整个使你成为更有效的安全顾问**，例如，因为你需要理解谁是你的。与之合作，并相应地校准你的语言和校准你的方法。Where the threats are in a software system at the high level will allow you to integrate into various different teams and two. Communicate effectively with them so it's very easy to think that we will be working with other developers in our day-to-day workflows, but in the event that we have to work with other people who are not necessarily technically minded, being able to understand how we can abstract our software representation and to perform some kind of analysis on that representation whole make you a more effective security consultant, for example because you need to understand who it is your. Working with and calibrate your language and calibrate your methodology accordingly.
* 因此，现实地说，当涉及到实施我们的软件系统，拿出一个设计，我们可以从顶部开始和工作下来。或者我们可以从底层开始，然后一路向上。 So realistically speaking, when it comes to implementing our software system, coming up with a design, we can start rather from the top and work our way down. Or we can start from the bottom and work our way up.

# 设计步骤：Process of Design

自上而下 - 架构开始

Design is all about decisions设计都是关于决策的.

* Top-down design – start with the architecture. **自上而下的设计--从架构开始**
  * First design the very high-level structure of a system. 首先设计一个系统的高层次结构
  * Then gradually work down to detailed decisions about low level constructs. 然后再逐渐地对低层次的结构进行详细的决定
  * Finally arrive at detailed decisions such as: 最后得出详细的决定，比如
    * The format of data items. 数据项的格式
    * The individual algorithms that will be used. 将被使用的个别算法
    * How different algorithms communicate with each other. 不同算法之间如何交流

当我们从顶部开始时，我们基本上是**把更高层次的架构放在一起，然后把它向下延伸，看看什么样的低层次框架。什么样的底层技术是理想的选择，以促进该架构**？when we start from the top, we're essentially putting together the higher level architecture and then trickling this down to see what kind of low level frameworks. What kind of low level technologies are ideal choices in order to facilitate that architecture

* 这种方法在我们处理一个空白项目的情况下效果很好。如果没有任何东西被实施，那么最好的地方就是开始。实施就是白板，这非常类似于启动UPS或正在组建的新项目。你很可能参与了某种软件系统的最初概念化工作。例如，如果你是一名高级工程师，你将负责画出最初的计划，随后将传达给开发团队中的各个技术负责人 Now this approach works very well in the event that we are working with a blank project. If nothing has been implemented, then the best place to start. The implementation is the whiteboard and this is very much akin to start UPS or new projects that are being put together. You may very well be involved in the initial conceptualization of some kind of software system. If you are, for example, an engineer, a senior engineer, you will be responsible for drawing out the initial plan, which will later be communicated to the individual tech leads in the development team.

---

自底向上 - 低层次组件开始

* Bottom-up design – start with low level utilities: 自下而上的设计--从低级别的实用工具开始
* Make decisions about reusable low-level utilities. 对可重复使用的低级工具做出决定
* Then decide how these will be put together to create high level constructs. 然后决定如何将这些东西组合在一起以创建高水平的结构

Mix of top-down and bottom-up: 自上而下和自下而上的混合

* Top-down design is almost always needed to give the system a good structure. 几乎总是需要自上而下的设计来给系统一个良好的结构
* Botton-up design is normally useful so that reusable components can be created. **自下而上的设计通常是有用的，这样可以创建可重用的组件**

:orange: 但在这个行业中，你很少会和完全新鲜的代码打交道，更多的时候，你会和一个团队一起工作，**你会在一个已经实现的软件项目上工作，所以已经有某种代码基础供你使用，这就是自下而上的概念发挥作用的地方**，据此，我们看**低级别的实用工具**。我们正在看我们正在看的**框架**。**不同的数据结构和模式被用来实现软件系统，并思考这些软件系统如何结合在一起，以达到我们更高层次的架构构造** But seldom in the sector you'll be working with code that is completely fresh more often than not, you'll be working with a team of people, and you'll be working on a software project that has already been implemented, so there will already be some kind of code base for you to work off of, and this is where the bottom up concept comes into play, whereby we are looking at the low level utilities. We're looking at the frameworks we're looking at. Different data structures and patterns that have been used and all that to implement the software system and with thinking about how these software systems come together in order to arrive at our higher level architectural constructs.

:orange: 如果我们要**重构**某种软件系统，当然我们需要能够通过**检查代码的结构，来理解那个软件系统是什么**。编写代码，以便再次与参与决策过程的合适的利益相关者交流，因为很少，除非你是独自工作。你是否愿意独自承担与不同人员联络的决定？你必须与不同的团队联络。你甚至可能不得不联络来自不同部门的人，并再次理解不同的人会有不同的理解。对软件和对软件项目的不同理解，具体来说，就是讨论的重点【**感觉是再次强调了分层重要性，，然后重构的时候考虑Mix方法，，低层次来理解高层次**。 So the idea is that if we are refactoring some kind of software system, of course we need to be able to understand what that software system is by examining the code by examining the structure of that. Code in order to again perhaps communicate this with the appropriate stakeholders who will be involved in that decision making process, because very rarely, unless you're working solo. Will you be the one solely responsible for the for the decisions you will have to liaise with different people? You have to liaise with different teams. You may even have to liaise with people from different departments and again understand that different people will have a different understanding. Of the software and a different understanding of the software project, specifically, that is the focus of discussion. 

:orange: 所以这是两种不同的方法，我们可以**通过它们来进行设计的过程**。如果我们<font color="red">从更高层次开始，我们可以看看可以使用的高层次技术，以及这些技术如何转化为低层次的技术实现。如果我们开始使用现有的代码库，或多或少我们将处于低水平。我们希望了解代码库，以便抽象出某种中间表示形式，从而开始分析</font> So these are two different approaches by which we can go about the process of design. If we're starting at the higher level, we can have a look at the high level techniques that can be used and how these translate into low level technical implementations. If we're starting which more or less we will be at the low level with an existing codebase. We want to understand the code base in order to abstract some kind of intermediate representation for the analysis to begin there.

---

# 证明性框架（设计使软件可衡量）: Justificatory frameworks

:orange: <font color="deeppink">设计需要一套软件标准（也就是框架，，不同软件不同框架标准，，比如安全标准，，隐私标准。。然后制定的机构通过这些标准来衡量软件的可接受性,,与监管机构进行沟通，证明软件是否满足不同标准</font>

Collection of standards or criteria that software should meet **软件应满足的标准或准则的集合**.

* Typically, these standards are set and reviewed by some authority. 通常情况下，这些**标准是由一些权威机构制定和审查的**
* Different frameworks exist for different aspects of software use. 针对软件使用的不同方面存在不同的框架
* Security and privacy are common安全和隐私是常见的.
* Regional authorities gauge the acceptability of software based on these frameworks 区域当局根据这些框架来衡量软件的可接受性.

但是设计不仅仅是让代码更容易阅读和管理，它也是我们与监管机构沟通的一种方式。现在，在商业领域，如果你正在研究某种软件系统，它将被部署在全球范围内，那么这个**软件很可能需要满足各种不同的监管标准，而且这些标准在未来的守规都将是不同的**。对于企业来说，开发软件是一件大事，能够拥有一个软件设计，你可以向管理机构阐明，并转向是你证明的一种方式，或者更确切地说，是一种方式。**提供证据证明您的软件系统满足这些不同的标准**。But the design is not only about making code easier to read and manage, it also serves as a way for us to communicate with regulatory bodies. Now, again in business, if you're working on some kind of software system that is going to be deployed globally, there are various different regulatory standards that the software will likely have to meet and regulatory compliance. Is a big deal in the enterprise when it comes to developing software and being able to have a software design that you can articulate to regulatory bodies and turn is a way for you to justify, or rather. Provide evidence that your software system satisfies these different standards.

现在，针对软件使用的不同方面存在各种不同的框架。我们有一些安全和隐私。这是两个最常见的标准。例如，《Gged》是一部非常现代的隐私立法，**它影响了很多软件系统，包括它们是如何构思的，它们是如何实现的**。如果他们被记录，然后按照同样的原则退休，则适用于保安。**有各种不同的监管机构。你的软件系统必须满足各种不同的监管标准，而且这些标准在证明你的软件系统的守规上占有很大的比重**。通过模型的交流和理解你的软件系统，能够有效地抽象你的软件系统。我能够向不同的监管机构清楚地表达这种抽象。将使您在衡量某种软件对于不同框架的可接受性时处于良好的地位 Now there are various different frameworks that exist for different aspects of software use. We have some security and privacy. These are the two most common standards. For example, Gged is a very contemporary privacy legislation that affects a lot of software systems in terms of how they are conceived, how they're implemented. Where they are instrumented and then subsequently retired at the same principle, applies to security. There are various different regulatory bodies. There are various different regulatory standards that your software system will have to meet, and a big part in proving regulatory compliance. As through the communication of models and being able to understand your software system, being able to effectively abstract your software system. I'm being able to articulate that abstraction to different regulatory bodies. will put you in good standing when it comes to gauging the acceptability of some kind of software with respect to various different frameworks.

# 设计原则驱动设计：设计通常以一套设计原则为指导

无论是否为Katerra的框架，总是潮水般涌向立法。有的时候我只是结果。当涉及到软件系统的开发时，良好的实践或经验法则，**所以立法是1个方面，可以非常控制或影响你在开发软件系统时做出的决定** So just for Katerra frameworks or not, always tide to legislation. There are sometimes I'm simply the result of. Good practices or rules of thumb when it comes to the development of software systems and so legislation is 1 aspect that can very much control or influence the decisions that you make when it comes to developing software systems. 

---

:orange: <font color="deeppink">设计原则是经过充分测试的最佳实践，已知可以为软件所需的特定属性产生积极的结果，，根据设计原则可以影响我们的系统如何被组合。。当涉及到软件系统的开发时，我们可以使用各种不同的经验法则</font>

---

Often guided by a set of design principles: 通常以一套**设计原则**为指导

* These are well tested best practices known to generate positive results for specific attributes desired from software. **这些都是经过充分测试的最佳实践，已知可以为软件所需的特定属性产生积极的结果**
* Example:
  * Privacy is about norms of transmission: **隐私**是关于传输的规范
    * Consent, Notice, Reciprocity, Fiduciary, Entitlement etc. 同意、通知、互惠、信托、权利等
* Square for security (Nancy Mead) 安全的广场 (Nancy Mead)

另一方面，我们有一般的**设计原则，可以用来影响你的软件系统是如何组合的**。And on the other hand we have just general design principles that can be used to influence how your software system is put together.

* 适用于各种隐私问题和软件系统的传输规范。 So for example we have here. Norms of transmission that are applicable to various privacy concerns and software systems. 
  * 你想看看你的软件系统在做什么，以及在该软件系统中被实例化和实现的基础业务流程模型是什么，以便考虑技术实现是否是你的模型的实现，这些是如何坚持的。 So the idea is that you want to have a look at what your software system is doing and what the underlying business process model is that is being instantiated and implemented in that software system in order to consider whether or not the technical implementations which are of course implementations of your model how these adhere to.
  * 某些设计原则，所以你是否让数据主体参与数据处理活动？或者你是否涉及相关的利益相关者和数据处理活动？ Certain design principles, so are you involving data subjects in the data processing activity? Or are you involving the relevant stakeholders and that data processing activity?
  * 而且，除了隐私之外，还有不同的各种最佳实践，适用于安全 And again there are different various best practices that apply to security in addition to privacy.

# 软件开发原则 - SOLID Principles：Software Development Principles

![](/static/2022-01-22-17-10-08.png)

这里的审查是不同的过程。**当涉及到软件系统的开发时，我们可以使用各种不同的经验法则**，因此这里有一些一般的软件开发原则。不要自己打自己封装。开放封闭设计，依赖注入，偏向于组合而不是继承。利斯科夫替换原则。这些都是不同的。**一般的原则，如果你要坚持这些原则，确实开发或设计一般的软件系统。将导致整体上更好的软件质量，尽管如此，但不一定要解读每一个程序** the review is different processes here. There are various different rules of thumb that we can use when it comes to the development of software systems and so here are some **general software development principles** . Don't beat yourself encapsulation. Open close design, dependency injections, favor composition over inheritance. The liskov substitution principle. These are all different. General principles that if you were to adhere to them, indeed development or design of software systems in general. Will lead to better quality software overall though, but not necessarily going to unpack every single one of these procedures. 

# 设计中的安全原则 - OWASP Development Guide(应用安全原则)： Security By Design Principles

![](/static/2022-01-22-17-18-21.png)

* Minimise attack surface area.,
* Principle of least privilege.,
* Keep security simple.

---

有一个**安全设计原则**。"设计 "或多或少是一种范式，是一种思想流派。它是一种哲学，**为了体现安全的各个方面，有各种经验法则。我们可以做各种事情，以便在某种软件项目的开发生命周期中体现出这一点** we have a security by design principles. Now by design is more or less a paradigm is a school of thought. It's a philosophy whereby in order to enshrine various aspects of security, then there are various rules of thumb. There are various things that we can do in order to sort of enshrine that in the development lifecycle of some kind of software project. 【**安全原则，低层次-静态分析，高层次-威胁建模**】

* 有最小化的攻击面。建立安全的默认值，最小特权原则的深度防御。他们将安全地不信任服务，职责分离。通过隐蔽性实现安全。保持简单，正确地修复它们 So we have the minimalization of attack surface area. The establishment of secure defaults, least privilege principle defense in depth. They will securely don't trust services, separation of duties. Security through obscurity. Keep it simple and fix them correctly.
  * 攻击表面积最小化是指我们正在研究某种Java安全项目的低级代码实现，我们要看看该**代码中潜在的安全漏洞在哪里，我们要应用静态分析，特别是污点流分析，以确定攻击表面积，并尽量减少攻击面积** the minimisation of attack surface area is whereby we are looking at the low level code implementation of some kind of secure project in Java and we're going to have a look at where potential security vulnerabilities are in that code, and we're going to be applying static analysis, specifically taint flow analysis in order to identify The attack surface area and in order to minimize the attack surface area.
* 不信任服务或多或少与我们可以使用的不同威胁模型有关。这个想法是，如果我们要**使用一个威胁模型，以了解某种软件系统** don't trust services more or less relates to different threat models that we can use. The idea is if we are going to use a threat models in order to understand some kind of software system.
  * 我们不想对该模型是什么或该模型在做什么做出任何假设。我们**要假设最坏的情况，并考虑如果我们有某种实现。那个实施方案会出什么问题** We don't want to make any assumptions on what that model is or what the model is doing. We want to assume the worst case and to think about if we have some kind of implementation. What can go wrong on that implementation

:orange: <font color="deeppink">因此，最小化攻击面更多的是一个假设。有一个低层次的技术关注的实现。而对服务的信任可以被认为是一个更高层次的关注点。当然，如果需要的话，它们也可以反过来应用</font> So the minimization of attack surface area is more a it can be conceived. There's a low level technical concern with an implementation. And the trusting of services can be conceived as a more high level concern. But of course they can also apply in the inverse if need be.

# 隐私设计原则：Privacy-By-Design Principles

as discussed in Privacy by Design in Law Policy and Practice (Ann Cavoukian)

![](/static/2022-01-22-17-31-47.png)

还有一些隐私设计原则。因此，我们有一原则，比如，要主动，而不是被动。预防性的，而不是补救性的隐私是默认的，并在设计中考虑到功能端到端的安全性、可见性、透明度和尊重隐私的使用。在这个课程中，**不一定会进入各种不同的隐私设计原则，但同样，这些是公认的良好做法**，当它涉及到。**在开发过程中把隐私纳入某种软件系统，就像我们有安全设计原则一样。我们也有隐私设计原则。当你在某种软件开发团队中与其他人一起工作时，你很可能会在谈论设计或某种软件项目时听到这些原则中的一个被提及** And there are also some privacy by design principles. so we have things like, be proactive, not reactive. Preventative, not remedial privacy as the default and bed into the design for functionality end to end security, visibility, transparency and respectful use of privacy. There will not necessarily are going to go into various different privacy by design principles in this course, but again, these are recognized good practices when it comes to. Enshrining privacy into some kind of software system during the development process, much like we have security by design principles. We also have privacy by design principles . It is likely that when you are working with others in some kind of software development team, you will likely hear one of these principles being mentioned at some point when talking about the design or some kind of software project.

# 如何达成良好设计：Finding a Good Design

* Compare against similar systems (what attacks do their design contend with?) **与类似的系统进行比较（他们的设计能应对哪些攻击**？
* Understand past attacks and attack patterns (How do they apply to your system?) **了解过去的攻击和攻击模式**（它们如何适用于你的系统？
* Challenge assumptions in your design: 挑战你设计中的假设
  * What happens if an assumption is untrue? 如果一个假设是不真实的，会发生什么
  * What would a breach potentially cost you? 一个漏洞可能会给你带来什么损失
  * How hard would it be to get rid of an assumption, allowing for a stronger adversary? (what would that cost?) 要摆脱一种假设，允许一个更强大的对手存在，会有多难

---

所以问题是我们如何做出一个好的设计？我们怎样才能设计出一个好的设计，并且实事求是地说，这很大程度上取决于你对**需求规范**的理解。So the question is how do we make a good design? How do we come up with a good design and realistically speaking a lot of this comes down to your understanding of not only the requirement specification.

* **正在解决的问题&需求**，以及作为开发人员或工程师为了实现客户的愿景而可以使用的**工具和技术**。还有某种元件。 The problem that is being addressed but also the tools and technologies available to you as a developer or engineer in order to realize the client's vision. And some kind of artifact.
  * 现在坏消息这是**软技能**，本质上它不是在课堂上能有效教授的东西。理解什么是优秀的设计师，**并认识到如何组合一个优秀的软件设计是你通过【经验】获得的东西**。这本质上是一种生活体验， Now the bad news  is soft skill Essentially it's not something that can be effectively taught in a classroom. Understanding what a good designers and recognizing how to put together a good software design is something that you acquire through experience. It's a life experience essentially,
* 但好消息是有各种各样的方法、技术和工具，作为开发人员，**你可以使用这些方法、技术和工具，通过【设计原则】在这个过程中帮助你**。 but the good news is that there are various methods, techniques, and tools that you can use as a developer to help you in that process by design principles.
  * 举个例子，你**可以遵循不同的方法，让你思考一个计划或某种软件设计是否包含了某些方面，你希望在你的软件系统中看到某种属性**。就像我说的，归根结底是经验，你不需要为自己编写软件。你实际上是在为别人编码。从本质上来说，一件好事就是想想那些将要接替你的人。当您完成了软件系统的实现之后，就可以转移到其他项目中。**另一个开发人员会接替你，他们将不得不查看你整合的软件，试图理解你正在使用的低级技术，以了解项目的整体结构。你想尝试让他们的生活变得更加轻松，反过来，当你将来挑选一个项目时，你会欣赏好的设计**。 one example of different methods that you can follow in order to get you to think about whether or not a plan or some kind of software design enshrine certain aspects that you would want to see some kind of property that you would want to uphold in your software system. And like I said, it comes down to experience and You don't necessarily code software for yourself.  you're essentially making code for other people. A good thing to do is essentially is to think about the the people that are going to be coming after you. When you have finished the implementation of your software system, you've moved on to your other project. Another developer will come after you and will have to look at the software that you have put together to try and understand the low level technologies that you're using to understand the overall structure of the project. You want to try and make their lives easier and in turn you will appreciate good design when it comes to picking up a project in the future.

---

# 设计决策：Making Design Decisions

To make each design decision the software engineer leverages on: 为了做出每一个设计决定，软件工程师都会利用以下因素 【<font color="deeppink">如何达成良好的设计</font>

* Requirements. **需求**
* The design as it currently is. 目前的设计
* Principles and best practices **设计原则和最佳实践**.
* Previous solutions. **以前的解决方案**

Sometimes there is no best solution. **有时并没有最好的解决方案**

* There are often trade-offs with different design directions. 在不同的设计方向上，往往会有一些折衷

---

:orange: <font color="deeppink">除了应用设计原则最佳实践，能使我们的系统产生好的结果，并且理解用户需求，，能引导更好的设计。然而，理解风险 & 与stakeholders & 相关监管机构沟通很重要
</font>

所以这张幻灯片在某种程度上再次强调了我已经说过的，**有时软件系统没有最好的解决方案**。你能做的最好的事情就是【**理解需求】，你的问题，理解你可以采用的不同的技术方法，并且【理解和交流与这些不同的设计选择相关的安全风险**】 So this slide here kind of re emphasizes what I've already talked about., sometimes there is no best solution to software systems. The best thing that you can do is to understand the requirements, your problem and to understand the different approaches that you can take with the technology is available to you and understand and communicate the security risks associated with these different design choices that you have

* 现实地说，你的大部分工作**不仅涉及到软件系统的实现，而且还【涉及到与利益相关者沟通，告诉他们你所做的选择，以及你为什么要做出这些选择。因此，能够提出一个有效的设计，坚持客户规范或如果它不坚持客户规范，能够指出的设计和证明你的选择**】，为什么功能需求，例如，不一定是可行的，或为什么必须作出改变，以及**软件设计能够证明你的选择，不仅向客户，而且向不同的监管机构【监管机构制定不同软件标准框架，要证明软件是否满足某些标准**】。它是现代软件开发的一个非常重要的方面，尤其是在大型软件开发公司中。realistically speaking a lot of your job not only involves the implementation of software systems, but it also involves communicating to stakeholders about the choices that you have made and why you have made the choices that you have made. So being able to come up with an effective design that adheres to client specification or if it doesn't adhere to client specification, Being able to point to the design and justify your choices for why a functional requirement, for example, is not necessarily feasible or why changes had to be made, and that software design being able to justify your choices to not only the client but also to different regulatory bodies. It is a very important aspect of modern software development, especially in large Software development houses.

我们想试着理解这些权衡是什么。我们希望了解在**代码级别实现软件的结果。我们希望了解绘制软件系统的高级架构表示的结果**。We want to try and understand what these tradeoffs are. We want to understand the consequences of implementing software at the code level. We want to understand the consequences of drawing up a high level architectural representation of a software system.

# 设计决策特性：Characteristics of Design Decision Making

当您从事软件工作时，您所处理的需求可能会有些独特。在某种意义上，**如果我们个别的项目在某种意义上是唯一可以描述的**，并且由于这个原因我们说设计的方法不一定是自然界中确定性的东西。when you work on software, the chances are that the requirements that you are working with are going to be somewhat unique. In a sense, if we individual project is uniquely characterizable in a sense and for that reason we state that the method of design is not necessarily something that is deterministic in nature.

设计过程通常被描述为。•	Design process is often described as:

* **Non-Deterministic: 非决定性的**
  * A deterministic process is one that produces the same output given the same input. Design is non-deterministic. No two designers or design processes will likely produce the same output. 确定性的过程是指在相同的输入下产生相同的输出。**设计是非确定性的。没有两个设计师或设计过程会产生相同的输出**
  * 确定性过程是产生相同输出的过程。给定相同的输入，但是当您为客户机设计某种类型的软件系统时，**这并不一定成立，不同的项目可能有相似之处。然后可能在技术上有相似之处，项目本身的基础结构上也有相似之处，但是对于你所从事的【每一个项目来说，都有一些方面是独一无二的**】。Deterministic process is one that produces the same output. Given the same input, but that doesn't necessarily hold true when you are designing some kind of software system for a client, there may be similarities in different projects. Then maybe similarities in the technologies, the underlying Structure of the project itself, but there will be aspects that are unique for every single project that you work on.
* **Heuristic: 启发式**
  * Because design is non-deterministic, design techniques rely on heuristics and rules of thumb rather than repeatable processes. **因为设计是非确定性的，所以设计技术依赖于启发式方法和经验法则而不是可重复的过程**
  * 我们认为设计是一个启发式的过程。因为**设计是不确定的，所以技术依赖于启发式和经验法则**，而不是可重复的公式化过程。这又回到了不同设计原则的概念上。 We state that design is a heuristic process. Since it's nondeterministic, a technique rely on heuristics and rules of thumb, rather than repeatable, formulaic processes. And this sort of comes back to the notion of different by design principles.
    * 当涉及到采用客户端需求并考虑**如何在某种特定的工件中实现这些需求**时。我们可能需要**考虑安全性**，我们也可能需要**考虑与软件相关的不同的隐私问题**。这就是**不同的设计范式/原则发挥作用**的地方，因为原则是灵活的，它们的存在是为了让你思考它，你【**如何遵循这些不同的最佳实践**】类似于设计模式。这样我们就有了**一个共同的解决方案，而不是通过不同的设计范式对一个共同的问题进行尝试和测试**。解决方案是可以应用于不同软件系统的概念 When it comes to taking Client requirements and thinking about how we can implement these requirements in some kind of featured artifact. We may want to, of course think about security, and we may want to think about different privacy concerns both with respect to that software. And that's where these different by design paradigms come into play, because the principle is that they are flexible such that they exist to get you to think about it, how you can follow these different best practices is similar to Design patterns. Whereby we have a common solution, rather that is tried and tested to a common problem different by design paradigms are tried and tested. Solutions that are concepts that can be applied to different software systems.
* **Emergent: 涌现性**
  * A final design evolves from experience and feedback. Design is an iterative and incremental process where a complex system arises out of relatively simple interactions. **最终的设计是由经验和反馈演变而来的**。设计是一个**迭代和增量**的过程，**复杂的系统从相对简单的互动中产生**
  * 最后，我们说决策设计决策是紧急的，特别是在现代敏捷方法中，**软件系统的最终设计是随着时间的推移而出现的**。如果你幸运的话，你将会有一个客户从第一天就知道他们想要什么。他们把要求传达给你。你把一些原型架构放在一起。你把一些软件系统放在一起。你开发你的原型。你把它呈现给客户，他们会告诉你很棒，继续吧，这正是我想要的。但通常情况下，你会发现客户有时改变主意，有时他们不知道自己想要什么。当他们真正想知道为什么的时候，你可以实现 x，但是在他们理解之前，他们不会和你交流。他们也想要z。And finally, we state that a decisions design decisions are emergent because the idea is that, especially in modern agile methodologies, the final design of a software system is something that emerges over time. If you are lucky, you will have a client that knows exactly what they want from day one. They communicate the requirements to you. You put together some kind of prototype architecture. You put together some kind of software system. You develop your prototype. You present it to the client and they tell you fantastic, Just go ahead with that, That is exactly what I want. But more often than not, you'll find that client some they change their mind, sometimes they don't know what they want. You will implement X when they actually want why, but they won't communicate it with you until they understand. They also want Z. 
  * 这就是为什么**现代敏捷方法**论在当代软件工程和软件开发实践中如此重要的原因，因为**现在的需求本质上是如此的流动和动态。采用灵活的方法是必要的**。他们可以用**动态需求**来解决这个问题，因为你会发现客户改变了主意，或者根本不一定理解他们想要什么，或者甚至看一眼你已经放在一起的原型，这会让他们意识到其他需求或者其他他们想要的特性，而这些不一定在已经商定的项目的原始范围之内。so that's why modern agile methodologies are so important in contemporary software engineering and software development practice, because requirements nowadays are so fluid and dynamic in nature. It's necessary to have a flexible methodology. They can work with this with dynamic requirements because you'll find that client has changed minds or simply may not necessarily understand what they want or even having a look at a prototype that you have put together will make them aware of other requirements or other features that they may want that weren't necessarily within the original scope of the project that was agreed upon

# 设计困难：Why is Design Difficult

Design is difficult because you are trying to abstract a solution which does not exist yet. 设计是困难的，因为你正试图抽象出一个还不存在的解决方案

![](/static/2022-01-22-19-59-12.png)

那么为什么设计是困难的呢？因为你**不能为了得到一个有效的架构和有效的输出而必须提前应用一个公式化的流程【**<font color="red">前面提到了，设计是non deterministic的，heuristic的</font>。本质上，试图抽象出一个还不存在的解决方案。这就回到了两个重要的技能，当你组装一个软件设计的时候，你**需要能够理解你工作的需求。你需要能够使用你的客户，以了解他们的要求，了解技术，可以用来寻找一个解决方案的问题【**<font color="deeppink">前面提到了，我们达成良好设计除了遵循设计原则，最大程度上与需求理解相关(软技能，与经验,背景相关)，重要的是解决方案也许不会是最好的，但是与stakeholders和相关监管机制进行沟通是很重要的，阐述设计决策，证明决策有效性，系统是否符合监管机构指定的软件标准</font>。So why is design difficult? Well, it's difficult because one you can't necessarily apply a formulaic process ahead of time in order to arrive at an effective architecture and effective output. Essentially, trying to abstract a solution that doesn't yet exist. And This sort of comes back to the two important skills that you want to develop when you're putting together a software design, you need to be able to understand the requirements that you are working with. You need to be able to use with your client to understand what those they are asking for, and you want to understand The technologies that are available to you when it comes to Finding a solution to that problem.

同样，为了帮助你完成这个过程，你可以遵循各种各样的方法。但是，**设计是一种生活技能（软技能），当你作为开发人员、工程师、承包商或顾问获得经验时，你就会学到这种技能**。Again there are various methods that you can follow in order to help you Along with that process. But designing something is very much a life skill, and it's something that you will simply learn as you gain experience as a developer, engineer, as a contractor as a consultant.

* 但是从根本上说，**你想要理解你正在处理的问题，你想要能够理解你正在处理的解决方案领域，能够向不同的利益相关者表达这一点是很重要的**。 But I'm fundamentally you want to understand the problem that you're working with and you want to be able to understand the solution domain that you're also working with, and being able to articulate this to different stakeholders is important.
  * 你可以擅长编码，但是如果你不能与其他人交流你在做什么，那么你对开发团队就没有多大用处。 You can be fantastic at code, but if you cannot communicate what it is you're doing with other people, then you're not really of much use to a development team.
  * 同样，你也可以成为一个出色的沟通者。但是，如果你不理解代码，如果你不理解你需要使用哪些技术来促进项目，那么你对于项目开发公司来说同样没有用处， Likewise, you can be a fantastic communicator. But if you don't understand the code, if you don't understand the technologies that you need to use in order to facilitate the project, then you're equally useless to a project development house
  * **所以理解客户需求是至关重要的，理解你用来解决这些问题的技术对于彼此来说也同样重要** so understanding the client requirements are vitally important, and understanding the technologies that you use in order to address them are equally important to one another.

---

找到一个好的设计是不容易的。•	Finding a good design is not easy:

- 充分利用经验和对问题的理解。•	Leverages off experience and understanding of the problem.
- 迭代过程。•	Iterative process.
  - 客户需求变更 & 环境限制
- 工业界的大多数敏捷方法都希望在设计中有所改变。•	Most agile approaches in industry expect change in the design.

# 设计好处/重要性-为什么设计：Why Design?

能够以一种**精心设计**的方式把代码放在一起有许多好处。对于像我们这样的人来说，最具体的好处可能是**代码的可理解性**。Being able to put together a code in a way that is well designed has numerous benefits. Probably the most tangible benefit for folk like us will be the understandability of code.

* 如果我们拿来做某个软件项目，然后有时候运气好的话，有时候你会得到一个项目，这个项目的文档非常好。**它的设计非常好，因此对我们来说是相当容易理解的**，它使我们的工作不那么紧张，并且**简化了重构的过程**。这样做，对整个企业都有好处。**它减少了与重构或额外功能相关的成本。并减少了人力资源的数量**。 If we brought in to work on some kind of software project and then sometimes it's luck of the draw, sometimes you'll get a project that is very well documented. It's very well designed, therefore it is quite understandable for us and it makes our jobs less stressful, and streamlines the process of refactoring. This and done has benefits to the business as a whole. It reduces cost associated with refactoring or additional features. And reduces the amount of human resources.
* 例如，他们需要投向项目。 For example, they need to be thrown at the project

![](/static/2022-01-22-20-08-31.png)

* Software which is not well designed is difficult to understand and manage. **设计不好的软件是很难理解和管理的**
  * 其原理是，**在一个软件项目中包含的功能越多，与实施更多的功能相关的成本就越高**。the principle is that the more features that are included in a software project, the higher the cost associated with implementing more and more. 
* The larger the program, the more pronounced the consequences of poor design. **程序越大，不良设计的后果就越明显**
  * **该项目越复杂**，也许是不必要的，**就越难有效地协调不同的功能**，因为**实施新功能的最大问题是确保它们不会破坏现有的功能**，这是增加新功能所带来的额外成本的一个巨大因素。the more complicated, perhaps unnecessarily, that project is the more difficult it is to effectively coordinate different features with one another, because the biggest problem with implementing new features is to ensure that they don't break existing features, which is a huge proponent in that additional cost associated with adding new features.
* 但是，<font color="deeppink">有一个好的设计，有一个软件，将最大限度地减少在该项目上实施额外功能所涉及的开销</font>。But having a good design, having software that will minimize the overhead involved in implementing additional features on that project.

---

![](/static/2022-01-22-21-48-30.png)

* To understand complexity. 要理解复杂性
* However, predicting
* an accurate M is difficult. **然而，预测 一个准确的M成本是困难的**
* Too many unknowns when forecasting time, labour, integration costs. **在预测时间、劳动力、整合成本时，有太多的未知因素**

:orange: <font color="deeppink">很难预测一个项目所需要的成本，因为存在太多未知因素，但是一个良好的设计，能使我们更好理解系统，理解代码。能使我们有效控制系统在低成本区域，并且管理项目复杂性，与stakeholders进行交流，</font>

**你的工作的另一个方面可能涉及成本项目或项目成本的估计**。你可能不一定参与某种项目的预算编制，但你很可能被要求提供某种时间预算编制。in turn it all comes down to the bottom line in business. another aspect of your job might involve the project of costs or the estimation of project costs. You may not necessarily be involved with the budgeting of some kind of project, but you may very well be asked to provide some kind of time budgeting. 

* 这要花多长时间可能是你们中的许多人在你们的专业时间和软件开发中会听到的一个问题，但能够回答这个问题是**软件开发的一个重要方面**，反过来，**能够有效和准确地回答这个问题也能设计良好的软件系统**。How long is this going to take is probably a question many of you will Get sick of hearing during your professional time and in software development, but being able to Answer that question is an important aspect of software development and in turn being able to answer that question effectively and accurately comes down to well designed software systems.

**如果你能够理解你正在使用的软件，你就能够对实现新功能的时间要求提供一个更准确的评估，这反过来又使你的团队作为一个整体能够阐明一个更准确的最低成本区域**。事情出错了，技术退役了。新技术出现了. If you're able to understand the software that you're working with, you'll be able to provide a more accurate assessment of the time requirements For implementing new features Which in turn allows your team as a whole to articulate a more accurate region of minimum cost. this isn't touristic difficult. because Realistically speaking, requirements change. Things go wrong, technologies get retired. New technologies Appear.

当涉及到软件实现时，有太多的未知因素，特别是如果你正在做一个要跨越多年的软件项目。第一天就向项目经理预测一个时间框架以及预算是非常困难的。 There are so many unknowns when it comes to the implementation of software, especially if you're working on a software project that's going to span years. It's very difficult one day one to project a timeframe along with budgets to the project managers.

* 但作为一个经验法则。**拥有设计良好的软件系统将使你能够减轻这些成本，因为在软件开发中项目经常发生变化，但能够有效地理解软件系统，能够通过设计它的方式来管理复杂性，让所有的利益相关者都能理解，这将有助于管理最低成本的区域**，所以你能够在这个意义上让你的经理非常高兴。But as a rule of thumb. Having well designed software systems will allow you to mitigate those costs as they arise, because changes will happen to the the software project changes happen all the time in software development, but being able to effectively understand the software system being able to manage the complexity by designing it in a manner that is good and understandable to all the stakeholders involved will go along way to Managing That region of minimum cost, so you'll be able to make Your managers very happy in that sense.

# 设计模型：Double Diamond - Double Diamond Design Model

![](/static/2022-01-22-21-56-20.png)

**设计模型**和**实现**是**解决方案领域**的一部分 Design models and implementations are part of the solution domain

---

有一些我们可以使用的模型，以帮助我们进行这种设计。这是由**设计委员会**提出的一个框架。意思是说，简而言之，你要让客户参与到软件系统的设计中来。因此，**这有点类似于敏捷方法论，即你想建立某种软件系统的原型，将其交付给客户，从客户那里得到反馈，然后再次开始这个过程。这是一个你遵循的循环过程，这是一个更正式的敏捷方法的定义**。there are some models that we can use in order to help us with this design. This is a framework put together by the Design Council. The idea is that in a nutshell, you want to involve the client with the with the design of a software system. So this is kind of similar to that of agile methodologies whereby you want to prototype some kind of software system, deliver it to the client, get feedback from the client, and then sort of begin the process again. It's a cyclic process that you follow, and this is a more formal Definition of that agile methodology,

# 决策者：Who are the decision Makers

前面一直强调，设计决策跟这些角色交流很重要，，，还有监管机构 之类的，，这些人有不同背景，经验，知识。再次强调设计分层也很重要

- 软件工程师 Software engineers
- 管理人员 Management
- CIOs
- CPO
- 客户 Clients
- 供应商 Vendors
- 审计人员 Audit Staff
- 等等。

---

当你在一个软件系统上工作时，**你很少是该软件项目的唯一决策者**。公司里有各种不同的人，他们会对项目的方向和项目的实现方式有发言权。因此，你有像软件工程师这样的人，有像你和我这样的人，他们是在现场开发软件的技术人员，了解我们所使用的技术的复杂性。但你也有你的领域专家。when you are working on a software system, You're seldom the sole decision maker in that software project. There are various different people in the company who will have a say in what the direction of the project is and how the project should come to fruition. So you have people like software engineers, people like yourself, myself who are technical who are On the ground developing the software who understand the technical intricacies of the technologies that we are using. But you also have your domain experts. 

因此，如果你正在开发一个软件系统，以模拟某种科学过程，例如，你可能有来自某种学术机构的代表。也许你正在与物理学家合作，以模拟宇宙中的某种现象。你负责开发软件。他们负责提供科学建议，你的软件模型将再现这些建议。So if you're developing a software system that looks to model some kind of scientific process, for example, you may have representatives from some kind of academic institution. Maybe you're working with physicists in order to simulate some kind of phenomena in the universe. You are responsible for developing the software. They are responsible for providing the scientific advice that your software model will reproduce

当然，你也有业务方面的问题。可以这么说，就是算账的人。这些人将告诉你，我们根本没有预算来实现你想在软件系统中实现的任何功能，. And of course you also have the business aspects. The bean counters, so to speak. These are the guys who will tell you that we simply don't have the budget for whatever features you want to implement in the software system,

而且当你与项目经理沟通时，也是如此。你会希望以一种你想说他们的语言的方式与他们沟通，本质上，你不一定能在技术层面上与他们沟通，就像你与你的技术领导或你的同事一样， and again when you're communicating with project managers. You will want to communicate with them in a manner that you want to speak their language Essentially, you're not necessarily going to be able to communicate with them at the technical level, as you as you would your tech lead or your coworkers 

这又回到了一个想法，<font color="deeppink">即有不同的抽象层次，软件系统的不同表现层次，我们希望能够对这些不同的表现层次进行某种分析。并交流我们为什么在这些不同层次的表述中做出这样的选择</font> ; and that sort of comes back to the idea that there are various levels of abstraction, various levels of representation of a software system, and we want to be able to Perform some sort of analysis on these different levels of representation. And communicate why we make the choices that we do at these various levels of representation.

