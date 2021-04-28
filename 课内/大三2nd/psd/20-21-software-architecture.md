# Software Architecture

面向对象的软件工程课程涵盖了对小规模软件设计的良好实践的介绍，并涉及一些设计模式。本讲座通过**研究整个系统的软件架构规模的设计，对其进行了扩展**。The Object Oriented Software Engineering course covers an introduction to good practices for small scale software designs and covers a number of design patterns. This lecture extends this, by looking at design at the scale of software architecture for whole systems.

* [Software Architecture](#software-architecture)
* [软件组件/构件：Software components](#软件组件构件software-components)
* [什么是面向组件的软件系统: component based software system](#什么是面向组件的软件系统-component-based-software-system)
  * [例子](#例子)
* [对象&组件区别：Distinguishing between objects and components](#对象组件区别distinguishing-between-objects-and-components)
  * [区别](#区别)
* [为什么不完全使用组件？](#为什么不完全使用组件)
* [通用组件 & 应用特定组件：General purpose and application specific components](#通用组件--应用特定组件general-purpose-and-application-specific-components)
* [组件应提供多少功能：How much functionality to expose](#组件应提供多少功能how-much-functionality-to-expose)
* [UML组件图标记：Component Diagram Notation](#uml组件图标记component-diagram-notation)
* [组件系统业务分离：Separation of concerns for component systems](#组件系统业务分离separation-of-concerns-for-component-systems)
* [契约式设计（组件接口规范）：Design by Contract](#契约式设计组件接口规范design-by-contract)
* [编写组件接口规范（契约）：Preparing a component interface contract](#编写组件接口规范契约preparing-a-component-interface-contract)
* [接口契约正式&非正式表达：Example Method Contract](#接口契约正式非正式表达example-method-contract)
  * [例子-（非）正式表达接口规范（源码注释）](#例子-非正式表达接口规范源码注释)
* [测试组件接口规范/契约：Checking component contracts](#测试组件接口规范契约checking-component-contracts)
* [组件接口规范重要性 & 成本](#组件接口规范重要性--成本)
* [组件规范不良导致错误：Software failure caused by incompatible components](#组件规范不良导致错误software-failure-caused-by-incompatible-components)
* [必须知道接口实现-抽象泄露问题：The problem of leaky abstractions](#必须知道接口实现-抽象泄露问题the-problem-of-leaky-abstractions)
* [架构模式：Architectural Patterns](#架构模式architectural-patterns)
* [UI设计业务分离：Separating concerns in user interface design](#ui设计业务分离separating-concerns-in-user-interface-design)
* [MVC模式：Model-View-Control](#mvc模式model-view-control)
  * [MVC局限性](#mvc局限性)
  * [修改MVC模式：Adapting the MVC pattern](#修改mvc模式adapting-the-mvc-pattern)
* [分布式系统模式：Building Distributed System](#分布式系统模式building-distributed-system)
  * [采用分布式模式原因](#采用分布式模式原因)
* [分布式-集中化存储服务优点：Benefits of centralising information storage and service provision](#分布式-集中化存储服务优点benefits-of-centralising-information-storage-and-service-provision)
* [C/S分布式模式：Client-Server Architectural Pattern](#cs分布式模式client-server-architectural-pattern)
  * [协议，会话，API：Protocols，sessions，APIs](#协议会话apiprotocolssessionsapis)
  * [C-S-邮件应用例子](#c-s-邮件应用例子)
  * [胖瘦客户机：Thin vs Fat Clients](#胖瘦客户机thin-vs-fat-clients)
  * [C/S模式优点](#cs模式优点)
  * [C/S模式局限性](#cs模式局限性)
* [P2P分布式模式：Peer-to-peer architecture pattern](#p2p分布式模式peer-to-peer-architecture-pattern)
  * [例子：Tor](#例子tor)
  * [优点](#优点)
  * [P2P局限性](#p2p局限性)
  * [Peer Discovery](#peer-discovery)
* [信息处理模式：Information Processing Patterns](#信息处理模式information-processing-patterns)
* [组件通信-同步通信机制的缺点:Disadvantages of synchronous communication mechanisms](#组件通信-同步通信机制的缺点disadvantages-of-synchronous-communication-mechanisms)
  * [同步异步例子：Synchronous vs. asynchronous communication: example](#同步异步例子synchronous-vs-asynchronous-communication-example)
* [信息处理模式-面向消息架构模式（异步）：Message oriented architecture pattern](#信息处理模式-面向消息架构模式异步message-oriented-architecture-pattern)
  * [配置影响面向消息队列：Configuring message oriented architectures](#配置影响面向消息队列configuring-message-oriented-architectures)
  * [例子-库存控制系统](#例子-库存控制系统)
  * [面向消息架构模式-库存控制系统：Stock control system using the message oriented architecture pattern](#面向消息架构模式-库存控制系统stock-control-system-using-the-message-oriented-architecture-pattern)
  * [优点-面向消息架构](#优点-面向消息架构)
* [顺序数据处理应用问题：Sequential data processing applications](#顺序数据处理应用问题sequential-data-processing-applications)
  * [设计问题：Design Problem](#设计问题design-problem)
* [管道过滤架构-信息处理模式：pipe and filter architectural pattern](#管道过滤架构-信息处理模式pipe-and-filter-architectural-pattern)
  * [管道过滤模式优点](#管道过滤模式优点)
  * [管道过滤架构变种](#管道过滤架构变种)
  * [Push or Pull Data Flow](#push-or-pull-data-flow)
    * [选择](#选择)
  * [Sequential or concurrent data processing](#sequential-or-concurrent-data-processing)
  * [Re-orderable vs. inter-changeable filters](#re-orderable-vs-inter-changeable-filters)
    * [选择](#选择-1)
  * [Branching Filters in pipelines](#branching-filters-in-pipelines)
* [插件架构：Plugin Architecture Motivation](#插件架构plugin-architecture-motivation)
  * [WHY](#why)
  * [Pattern](#pattern)
  * [How flexible should a plugin architecture be?](#how-flexible-should-a-plugin-architecture-be)
  * [注意！！](#注意)
* [Summary](#summary)

# 软件组件/构件：Software components

构件（component）是可复用的软件组成成份，可被用来构造其他软件。它可以是被封装的对象类、类树、一些功能模块、软件框架（framwork）、软件构架（或体系结构Architectural）、文档、分析件、设计模式（Pattern）等。构件分为构件类和构件实例，通过给出构件类的参数，生成实例，通过实例的组装和控制来构造相应的应用软件，这不仅大大提高了软件开发者的开发效率，也大大提高了软件的质量

![](/static/2021-02-09-20-45-24.png)

在我们正式开始之前，我们应该了解软件工程中的组件是什么意思。文献中有许多定义，例如

> “A component is a physical manifestation of an object that has a well-defined interface and a set of implementations for the interface”
> "**组件是一个对象的物理表现形式，该对象具有明确定义的接口和一组接口的实现**"

> "**可作为一个单元独立开发和交付，并可与其他组件一起组成、保持不变以构建更大的东西的软件工件的连贯包**"
> “A coherent package of software artifacts that can be independently developed and delivered as a unit and that can be composed, unchanged, with other components to build something larger” 

> "**组件通过加强接口的作用和单独的【组件规范】概念来扩展[面向对象]原则。组件必须符合组件标准**"
> “Components extend [object oriented] principles by strengthening the role of the interface and by a separate notion of component specification. Components must conform to a component standard”

:orange:什么是软工中的组件 software engineering component

* 总之，软件组件的概念是指**具有明确定义的接口的自包含状态和行为的软件束**。>In summary, the notion of a <em>software component</em> refers to a software bundle of self contained state and behaviours with well defined interfaces.
  * 软件构件是一种独立于特定的程序设计语言和应用系统、可重用和自包含的软件成分
* **一些组件可以单独执行其功能，但其他组件则依赖于其他组件提供的信息和功能**。Some components can perform their functions in isolation, but others depend on information and functions provided by other components.
* **面向组件的系统** 是**一组相互作用的组件的集合，这些组件被组合起来以实现一些更广泛的目的**。A <em>component-oriented system</em> is a collection of interacting components that have been combined to realise some wider purpose.
* **组件是有用的，因为它们让我们能够对整体架构中的大型子系统进行推理，而不必担心单个对象的行为或它们在代码中的实现** Components are useful because they allow us to reason about the large scale sub-systems within an overall architecture, without worrying about the behaviour of individual objects or their implementation in code

# 什么是面向组件的软件系统: component based software system

组件之间需要中间件协调通信，，中间件使用IDL接口定义语言，为每个组件定义接口，所以组件可以用不同编程语言实现，但IDL得一样。

这些接口是组件在中间件框架中通信的唯一方式

Component oriented systems require a middleware framework to mediate interactions between components, which uses a interface definition language to define the interfaces of each component in the system. These interfaces are the only way that components will communicate within a middleware framework.

## 例子

例如，一个航空公司预订系统可以设计成**一个面向组件的系统**，For example, an airline reservation system could be designed as a component oriented system,

* 其中包含管理预订、接受信用卡付款、发放登机牌和组织行李转移等活动的组件。containing components for managing bookings, taking credit card payments, issuing board passes and organising luggage transfers, amongst other activities.
* 订票组件可能可以与其他组件分开使用，The bookings components could probably be used in isolation from other components,
* 而信用卡支付处理组件可能被许多其他系统（如航空公司的机上饮料收银机）使用。 and the credit card payment processing component might be used by many other systems (such as the airline’s inflight drinks cash register).
* 然而，其他组件，**例如登机牌发放器将依赖于预订系统**（向未付款的乘客发放登机牌并不是一个好主意），**而行李管理系统将依赖于由登机牌发放组件收集的信息**（知道行李将被送往何处）However, other components, such as the boarding pass issuer will depend on the bookings system (it isn’t a good idea to issue boarding passes to passengers who haven’t paid) and the luggage management system will depend on information gathered by the boarding pass issuing component (to know where the luggage will be sent).

# 对象&组件区别：Distinguishing between objects and components

* **一个组件是一个自我包含的、自我控制的和不断发展的较小规模的对象的捆绑** A component is a self-contained, self-controlled and evolving bundle of smaller scale objects.
* **一个】面向组件的系】可以通过一个【中间件】框架来区分，以【调解组件】之间的相互作用** A component oriented system can be distinguished by a middleware framework to mediate interactions between components.
*   * 中间件屏蔽了分布式应用中各种复杂的而又非常重要的问题，如各个应用程序间的通信，互操作，以及它们的效率，可靠性，容错性，安全性，完整性，而这些问题是所有开发分布式应用所必需考虑的，但和应用相关的业务逻辑有无直接关系，中间件使得软件开发者不必再这些问题上耗费太多的精力，而集中精力于业务逻辑本身
* **中间件可以加入接口定义语言（IDL），用于定义系统中每个组件的发布接口** The middleware can incorporate an interface definition language (IDL) that is used to define the published interfaces of each component in the system.。
  * <font color="deeppink">接口描述语言或接口定义语言(IDL) ，是一种规约语言语言，用来描述基于组件的软件工程的应用程序编程接口(API)。IDLs 以独立于语言的方式描述接口，使【不共享一种语言的软件组件】之间能够进行【通信】，例如，使用 c + + 编写的组件和使用 Java 编写的组件之间能够进行通信</font>
* **组件通过在系统初始化时或在运行时动态地定义一个配件来进行协调** Components are orchestrated by defining an assembly either during system initialisation or dynamically at run time.
  * Assembly, 这里把它翻译为配件或程序集, 以示和组件(Component)加以区别。**一个配件有时候是指一个EXE或者DLL文件, 实际上是一个应用程序(就是指带有主程序入口点的模块)或者一个库文件。但是配件实际上可以是由一个或者多个文件组成(dlls, exes, html等等), 代表一组资源, 以及类型的定义和实现的集合**.。一个配件也可以包含对其它配件的引用。 所有这些资源、类型和引用都在一个列表(manifest)中描述。manifest也是配件的一部分，所以配件是一个自我描述的，不需要其它附加的部件
* <font color="red">在同一个面向组件的系统中，组件可以用几种不同的编程语言来实现 </font>Components in the same component oriented system may be implemented in several different programming languages.

## 区别

* 组件和对象都是具有地址、状态和行为的软件运行时实体。此外，<font color="red">一个组件有一个规范</font>，就像每个对象都有一个相关的类一样 In particular, both components and objects are software runtime entities with an address, state and behaviour. In addition, a component has a specification, just like every object has an associated class
* <font color="deeppink">事实上，在统一建模语言中，组件是对象类类型的一种特殊化，但具有一些不同的特征</font>
  * **【组件是长期存在的实体，通常会在软件系统的整个生命周期内进行部署**】。相反，对象可以在系统的整个生命周期中被创建和销毁 Components are long lived entities that are generally deployed for the full life time of a software system. Conversely, objects may be created and destroyed throughout the lifetime of the system.
  * 使用**组件中间件**意味着，**面向组件的系统中的组件可以分布在多个不同的组件环境中，并托管在多个物理上不同的硬件系统上** The use of a component middleware means that the components in a component oriented system may be distributed between a number of different component environments and hosted on a number of physically different hardware systems.
  * <font color="red">因此，不可能以与对象相同的方式直接与组件实现交互【需要中间件调解</font>】 Consequently, it is not possible to interact directly with the component implementation, in the same way as with an object.

:orange: **配件不同于组件本身的实现。【配件指定了哪些组件将满足系统中每个组件所需的依赖性】。通常，一个组件由一个类型和一个发布版本来标识** The assembly is distinct from the implementation of the components themselves. The assembly specifies which components will satisfy the required dependencies for each component in the system. Normally, an component is identified by a type and a release version.
  * **所有组件都为相同的中间件和 IDL 提供支持。此外，组件提供的每个接口都可以由组件中的不同对象实现**。All provide support for the same middleware and IDL. In addition, each of the interfaces provided by a component may be realised by a different object within the component.

# 为什么不完全使用组件？

:orange: 实际上，**面向组件的中间件是最大限度地消除系统中各模块之间耦合的一种手段**。因此，一个合理的问题是 In effect, a component oriented middleware is a means of maximising the de-coupling between modules in a system. Consequently, a reasonable question to ask is：

* 既然我们知道松散的耦合是好的，那么为什么不把系统中的每一个对象都变成一个组件呢？Given we know that loose coupling is good, so why not make every object in the system a component?

:orange: 面向组件局限性

* 通过<font color="red">中间件调解组件间交互通信</font>，**除了对象对对象的直接交互所需的成本外，还需要额外的通信成本**。这是因为消息必须由中间件进行编码、传输和解码 Mediating component interactions through a middleware imposes additional communication costs on top of that required for direct object to object interactions. This is because messages must be encoded, transmitted and then decoded by the middleware
* **将对象作为组件公开会产生额外的开发成本**，<font color="deeppink">因为它形成系统已发布接口（API）的一部分</font>。**需要维护组件的所有接口的文档，因为应该期望其他人需要了解如何使用它们**。There are additional development costs associated with exposing an object as a component, since it forms part of the system’s <em>published</em> API. The documentation for all of the interfaces to a component need to be maintained, as it should be expected that others will need to understand how to use them.
  * 此外，<font color="deeppink">在不影响系统或基础设施中其他组件的实施的情况下，组件的接口规范不能轻易更改</font>，这些组件可能由其他软件团队实施和维护 In addition, the interface specifications of a component cannot be readily changed without disrupting the implementation of other components in the system or infrastructure, which may be implemented and maintained by other software teams.
* <font color="deeppink">组件规格和设计应作为系统文档的一部分进行维护</font>。**如果系统中的每一个对象都是作为一个组件来实现的，那么系统文档就会有效地重复基础语言文档【冗余**】 Component specification and design should be maintained as part of the system documentation. If every object in a system is realised as a component then the system documentation effectively duplicates the underlying language documentation.
* 当然，在某种程度上，**决定将哪些对象视为组件的决定将取决于正在开发的软件系统**。Of course, the decision about which objects to treat as components will, to a certain extent, be specific to the software system under development.
  * 组件工程师的作用是决定系统中<font color="deeppink">哪些对象应该被指定为组件，并在组件中间件中暴露出来</font> The role of the component engineer is to decide which are the right objects in a system that should be specified as components that are exposed within the component middleware
  * 中间件屏蔽了分布式应用中各种复杂的而又非常重要的问题，如各个应用程序间的通信，互操作，以及它们的效率，可靠性，容错性，安全性，完整性，而这些问题是所有开发分布式应用所必需考虑的，但和应用相关的业务逻辑有无直接关系，中间件使得软件开发者不必再这些问题上耗费太多的精力，而集中精力于业务逻辑本身

# 通用组件 & 应用特定组件：General purpose and application specific components

:orange: 在构建**面向组件的系统**时，软件工程师将使用**两种不同类型的组件**：When building a component based system, a software engineer will work with two different types of component:

* **通用组件** General Component
  * 提供了通用功能，<font color="deeppink">这些功能是为了在许多不同的应用中重复使用</font>。components provide common functionality that is intended for reuse in many different applications.
  * 例如，上面描述的预订系统中的卡支付处理组件也可用于收取机场或飞机上免税购物的费用，以及支付机场餐厅的餐费等  For example, the card payment processing component in the reservation system described above could also be used to collect payments for duty-free shopping in an airport or on a plane, pay for meals in the airport restaurant and so on.These components are normally very well documented and stored in a component repository
* **特定应用组件** Application apecific
  * **组件实现了应用的特定问题功能和业务逻辑**  components implement the problem specific functionality and <em>business logic</em> of the application.
  * 这些组件通常被称为应用程序的<em>胶水</em>，<font color="red">因为它们负责协调通用组件的行为以实现应用程序的特定需求</font> These components are often called the application glue, because they do the job of orchestrating the behaviour of the general purpose components to realise application specific needs.
  * 例如，为在办理登机手续时签发登机牌而开发的**组件将包含**将航空公司的预订数据转换为登机牌的**特定业务逻辑(为实现特定功能**)。 The component developed to issue boarding passes at check-in for example, will contain business logic specific to the job of converting the airline’s reservation data into a boarding pass.

:orange: Robert Glass(2001)认为，

* **软件工程非常擅长开发和管理通用组件** software engineering is very good at developing and managing general purpose components
  * (如卡支付处理系统)，(such as the card payment processing system),
* **但很不擅长将包含业务逻辑的组件（特定应用组件，包含了业务逻辑来实现其他功能）转化为可重用组件**。but quite bad at translating components that contain business logic into reusable components.
  * 例如，预订组件**原则上**可以调整为其他用途（原则上这些特定应用组件可重用），如预订餐厅的桌子、公交车上的旅行或电影票。 For example, the reservation component could, in principle, be adapted to other uses, such as booking restaurant tables, travel on buses or cinema tickets.
  * **然而，经常会有一些微妙的技术（和<em>社会技术</em>）原因，导致在一个领域中预订的业务逻辑在另一个环境中无法使用（无法重用特定应用组件**） However, there are often subtle technical (and <em>socio-technical</em>) reasons why the business logic of reservation booking in one domain doesn’t work in another context

# 组件应提供多少功能：How much functionality to expose

:orange: 对于他们自己编写的组件，**组件工程师将需要决定开发中的每个组件将提供多少功能** For components they write themselves, a component engineer will need do decide how much functionality will be provided by each component under development

* 【过复杂】在**一个组件中整合大量功能会使组件的维护和使用变得非常复杂**，Combining lots of functionality in a component can make the maintenance and use of the component very complex,
  * <font color="deeppink">因为更改组件可能会影响许多其他不同系统对组件的使用</font>。不同组件接口之间的交互也需要仔细记录。because changing the component may effect the use of the component by many other different systems. The interactions between the different component interfaces will also need to be carefully documented.
* 【过简单】相反，**让组件变得非常简单(功能很少)意味着组件之间的交互将变得非常复杂**，<font color="deeppink">因为必须协调大量简单的组件任务以实现应用程序的一些有用功能</font> Conversely, making components very simple means that the interaction between components becomes very complex because lots of simple component tasks have to be orchestrated to achieve some useful functionality for the application

:orange: <font color="blue">缓解这种窘境的一种方法是将较简单的组件嵌套在较复杂的组件中。</font> One way of mitigating this dilemma is to nest simpler components within more complex ones.

* **这意味着一个组件的部分功能由其他子组件封装形成**（并可替换）。This means that some of the functionality of a component is encapsulated (and replaceable) with other sub-components
  * 这种方法还有**助于最大限度地减少**系统中任何给定分解级别的**组件之间的交互管理** This approach can also help minimise the management of interactions between components at any given level of decomposition in the system.

# UML组件图标记：Component Diagram Notation

![](/static/2021-02-09-21-34-18.png)

UML组件图是一种有用的符号，用**于勾画软件系统的高层架构**，UML component diagrams are a useful notation for sketching the high level architectures of software systems, expressing their major sub-systems as components and the interconnections between them as bound interfaces.

* **将其主要的子系统表达为组件**， expressing their major sub-systems as components
* **将子系统之间的相互联系表达为绑定接口** the interconnections between them as bound interfaces
* **端口**可以理解为组件的入口与出口，组件通过端口与外部元素相互协作。端口上可以添加提供接口或需求接口来使组件得以扩展
  * 别的组件可以通过一个特定端口与另一个组件通信。在实现时，组件的内部组件通过特定的外部端口来与外界交互，端口提供的封装性和独立性更大程度上保证了组件的封装性和可替换性

![](/static/2021-04-25-19-56-29.png)
![](/static/2021-04-25-20-04-11.png)

* 图中说明了组件图的基本框线符号 The figure illustrates the basic wiring notation for component diagrams.
* **在图中，一个【组件】用一个带有 "插头 "小部件定型的方框来表示**。 In the diagram, a component is represented by a box with a ‘plug’ widget stereotype.
* **每个组件都有一个标签**  Each component has a label (in this case, A and B
  * 在本例中，`A&B`
* 此外，**组件A提供一个接口I** In addition, component A is providing an interface I, which is shown as a **facet** (but looks like a lollipop) stereotype attached to the component.
* 此外，该图还显示，**组件B需要I接口**，该接口由一个连接器表示，称为围绕面绘制的容器 component B requires interface I, denoted by a connector, called a **receptacle** drawn around the facet.

:orange: 请注意，与 Java 等编程语言中的接口相比，<font color="red">这对接口在此上下文中的含义有影响</font>。**配件中的组件上的接口**更像是服务器（配件）上的网络端口（组件接口） Notice that this has implications for what an interface means in this context compared to an interface in a programming language like Java. An interface on a component in an assembly is more like a network port on a server.

* **接口是主动被消费的。接口是一个具有端点地址或标识符的实际实体，就像组件一样** The interface is <em>actively</em> being <em>consumed</em> The interface is an actual entity with an endpoint address or identifier, just like a component.
* 这意味着，如果一个组件被表示为**暴露了两个相同类型的接口**，那么它们在运行时将拥有**不同的端点地址**  This means that if a component is denoted as exposing two interfaces of the same type, they will have different endpoint addresses at run time.

# 组件系统业务分离：Separation of concerns for component systems

为组件系统实现关注点分离的架构模式，即一个组件对某些功能有明确的责任，该组件不应该受到其他组件实现的变化的影响。

![](/static/2021-02-09-21-40-50.png)

:orange: 了解了组件的概念，让我们开始看看如何**使用组件来组织软件架构** Now that we’ve looked at the notion of components, lets begin to look at how they can be used to organise a software architecture.

:orange: 正如我们所看到的那样，**软件设计中的一个关键原则是确保业务分离**。从**架构**的角度来看，这意味 a key principle in software design is to ensure a separation of concerns.

* **一个组件对某些功能有明确的责任**；并且 a component has a clearly defined responsibility for some function; and
* **组件不应受到其他组件实现细节变化的影响** the component should not be affected by changes to the implementation details of other component.

# 契约式设计（组件接口规范）：Design by Contract

> 通过合同设计的概念进一步加强了组件作为区分业务系统的一种手段的概念。 The notion of components as a means of reasoning the separation of concerns systems has been further strengthened by the concept of <em>design by contract</em>.
>
> **在这种方法中，一个组件的接口定义构成了某些功能的提供者和提供者之间的 "契约"**。 In this approach, the interface definition for a component constitutes a ‘contract’ between the provider and the supplier of some functionality.

:orange: 开发Eiffel编程语言的Meyer称**接口文档为 "契约"**，因为它应该描述：Meyer, who developed the Eiffel programming language, called the interface documentation a ‘contract’ because it should describe the:

* **使用组件所提供的接口的好处(为什么使用该组件接口？**) Benefits of using the interface that are offered by the providing component.
* **对提议【使用该接口的组件】施加义务（如果要使用该组件接口，应该遵循什么**）。 Obligations imposed on the component that proposes to use the interface.

:orange: 按契约设计 定义

* 描述**用这些有精确文档规范的接口来组成软件系统的工作过程** Meyer also defined the term ‘design by contract’ to describe the process that works with these very precisely documented interfaces to compose software systems.

# 编写组件接口规范（契约）：Preparing a component interface contract

![](/static/2021-02-09-22-00-15.png)

为了**提供契约规范（用来指导使用 &描述接口**），接口的文档可以包括：To provide a contract, the documentation for an interface can include:

* 由**提供组件**实现的**可见组件状态（组件属性**）Visible component state (component attributes) that is realised by the providing component.
* 描述**提供组件的合法状态的常量（不变量** *Invariants describing the legal states of the providing component.
* **对于接口中的每个方法** For each method in the interface
  * **方法签名**，a signature
    * 包括方法标识符、参数标识符和类型、方法返回类型以及由于不当调用该方法而可能引发的任何异常 comprising a method identifier, argument identifiers and types, method return type and any exceptions that might be raised as a result of improperly invoking the method.
  * **预先条件** pre-conditions
    * 描述了提供组件在方法被调用之前所需的状态，以及对所提供的不能在方法签名中表达的参数的任何限制。that describe the required state of the providing component before the method can be invoked and any restrictions on supplied arguments that cannot be expressed in the method signature.
* **Post-conditions** 后置条件
  * 描述了**方法调用完成后提供组件的状态**，以及对返回给调用组件的任何值的约束，这些值不能由IDL的类型系统表达。that describe the state of the providing component after the method invocation has been completed and constraints on any values returned to the calling component that cannot be expressed by the IDL’s type system.
  * **Semantics** 方法语义，
    * 包括，例如，方法应该以正确的顺序被调用 including, for example, the correct sequence that methods should be invoked in
  * **每个方法对系统中其他组件的可见性** The visibility of each method

# 接口契约正式&非正式表达：Example Method Contract

![](/static/2021-02-09-22-06-16.png)

:orange: **契约的不同部分可以用不同的方式来表达**。The different parts of a contracts can be expressed in different ways. 

* 例如，**【方法签名】通常用接口定义语言【正式表达**】。For example, method signatures are normally expressed formally in an interface definition language.
* **不变量（提供接口的合法状态）、前条件和后条件（统称为*约束**）和语义<font color="deeppink">可以使用【源代码注释】进行非正式的记录</font>，但也可以用正式的方式表达。Invariants, pre-conditions and post-conditions (collectively known as *constraints) and semantics can be documented informally using source code comments, but can also be expressed formally.
  * 正式符号的示例包括 <code>javax.validation</code> 包注释和 OCL 约束语言   Example formal notations include the <code>javax.validation</code> package annotations and the OCL constraint language.

---

## 例子-（非）正式表达接口规范（源码注释）

请考虑以下包含非正式和正式文档的 Java 方法。在实践中，**如果约束条件是正式表达的，则可能需要较少的非正式文档（源码注释更少**

* 该方法指定了如何使用指定的组件接口处理信用卡支付。The method specifies how a credit card payment can be processed using the specified component interface.
* 方法文档中描述了该方法的整体行为，随后是对每个参数及其约束的描述 The overall behaviour of the method is described in the method documentation, followed by a description of each of the parameters and their constraints.
* 此外，还描述了该方法的返回值和可能抛出异常的情况 In addition, the return value for the method and the situations in which an exception might be thrown are described.

除了非正式的约束条件外，方法签名还有一些**来自 <code>javax.validation</code> 包的约束条件注释**。（正式） In addition to the informal constraints, the method signature has a number of constraint annotations applied from the <code>javax.validation</code> package.

* 这些约束应该与非正式文档保持一致。 These constraints should be consistent with the informal documentation.
* 例如，<code>@Digits</code>约束可以用来指示提供的参数String必须是一个最大长度的数字序列。 For example, the <code>@Digits</code> constraint can be used to indicate that a supplied argument String must be a sequence of digits of a maximum length.
* 类似地，<code>@Future</code> 约束指定支付中使用的信用卡的到期日必须是在未来的某个时间点。 Similarly, the <code>@Future</code> constraint specifies that the expiry date for the credit card used in the payment must be at some point in the future.
* <code>@Digits</code> 注解也用于限制方法的返回值。 The <code>@Digits</code> annotation is also used to constrain the method’s return value.

# 测试组件接口规范/契约：Checking component contracts

![](/static/2021-02-09-22-09-43.png)

一旦指定了正式的组件契约规范，就可以通过多种方式进行检查：Once specified, formal component contracts can be checked in a variety of ways:

* **Statically at compile time**. 编译时期静态检测
* **Using test frameworks, such as JUnit**. 单元测试，测试框架
* **Within the program logic at runtime**. 运行期业务逻辑
* **By the component middleware at runtime**. 运行期组件中间件

# 组件接口规范重要性 & 成本

退一步讲，我们有理由问为什么这些额外的文档规范如此重要？这无疑加强了这样一个论点，Taking a step back for a moment, it is reasonable to ask why is all this additional documentation so important? It certainly reinforces the argument

* **即软件工程师应该非常谨慎地选择哪些对象应该被视为组件**，that software engineers should be very careful about choosing which objects should be treated as components,
  * 因为这似乎<font color="red">涉及到文档的额外开销(如果想选择某对象，就需要对外提供接口，编写接口文档规范契约，产生开销)</font>。given the extra overheads of documentation that seem to be involved.
  * 精确、准确的接口文档规范对于**防止组件在不同的配件（多组件组成的）中被重复使用时的误用**是非常重要的 Precise, accurate interface documentation is important to prevent the misuse of components when they are reused in different assemblies.

# 组件规范不良导致错误：Software failure caused by incompatible components

阿丽亚娜5号灾难通常被作为组件规格不良的后果的例子。惯性参考软件中一个简单的铸造溢出（64位到16位）被报告为飞行控制系统的数据（而不是诊断信息），导致欧洲航天局损失了价值5亿美元的发射系统 The Ariane 5 disaster is commonly referenced as an example of the consequences of poor component specification. **A simple casting overflow (64 bit to 16 bit**) in inertial reference software was reported as data to the flight control system (rather than as diagnostic information) causing the loss of the European Space Agencies $500 million launch system <span class="citation" data-cites="lions96ariane">[@lions96ariane]

# 必须知道接口实现-抽象泄露问题：The problem of leaky abstractions

**理想情况下，当使用一个组件的接口时，你应该能够在不了解其实现的情况下使用它。然而，有时这是不可能的，你需要使用关于该接口是如何实现的信息，以便使用该抽象。这就是所谓的泄漏性抽象**。 Ideally when using a component interface you should be able to use it without any knowledge of its implementation. However, sometimes this isn’t possible, and you need to use information about how the interface was implemented in order to use the abstraction. This is called a leaky abstraction.

这方面的一个例子是SQL标准--理论上，你应该能够写出能够在任何基于SQL的数据库管理系统上运行的SQL，但是不同实现之间的细微差别（例如PostgreSQL与mysql）意味着你需要知道你使用的是哪种实现，以便为该版本编写特定的语法。 An example of this is the SQL standard - in theory you should be able to write SQL that will operate on any SQL-based database management system, however slight differences between implementations (e.g. PostgreSQL vs. mysql) mean that you need to know which implementation you are using to write specific syntax for that version.

![](/static/2021-02-09-22-14-19.png)

阿丽亚娜 5 的失败是由 Joel Spolsky 所指出的 <em>泄漏的抽象</em>问题引起的 The Ariane 5 failure was caused by the problem of <em>leaky abstractions</em>, noted by Joel Spolsky.

:orange: 这意味着**每当两个组件实现（接口的提供者和请求者）【在一个配件中连接在一起】时**，它们之后收到以下假设的影响： this means that whenever two component implementations (a provider and a requirer of an interface) are wired together in an assembly, their future evolution is influenced by assumptions about

* **提供接口如何被使用** The way that a providing interface will be utilised;
* **提供接口的实现方式** and The way that the providing interface is realised

---

:candy: Spolsky**最初认为**，**泄漏来自于底层抽象实现**。需求组件的开发者要求**更多地了解底层提供接口的实现**，以便构建一个更好的系统。Spolsky originally argued that the leak comes from the underlying implementation through the abstraction. The developer of the requiring component demands to know more about the underlying provider implementation in order to build a better system.

* 然而，信息的泄露是双向的，**因为提供接口会受到如何它的实现的假设的限制**。However, the leakage of information goes both ways, because the provider of an interface becomes constrained by assumptions as to how it is implemented

:candy: 你的系统的每一个可观察的实现细节<em>都是</em>你的合同契约。可能会对偶然的实现细节进行假设的示例包括 every observable implementation detail of your system <em>is</em> your contract. Examples of where assumptions might be made about incidental implementation details include:

* 从积分器收到的对象在无序集合上的排序 The ordering of objects received from an integrator over an-unordered set
* 数据库系统的行为和结构--了解查询的评估方式会对性能产生很大影响 The behaviour and structure of a database system - knowing how queries will be evaluated can have a big impact on performance

:orange: 因此，尽管我们决心<font color="deeppink">将组件的接口与其底层实现分开，但我们发现它们是相互关联的，因为组件设计的语义成为接口的一部分</font>。So, despite a determination to separate the interface to a component from its implementation, we find that they become coupled, because <。这种安排允许服务器同时处理来自多个客户端的多个连接。连接的状态（如服务器所感知的那样）由负责该连接的会话来维护>the semantics of a component’s design become part of the interface

* **实际上，接口提供什么和如何提供之间的区别并不明确，因为如何提供接口往往对需要的组件很重要**。In effect, the distinction between what an interface provides and how it provides it is not clear cut because often <em>how</em> the interface is provided matters to the requiring component.
* 因此，安全的组件组成是软件工程中积极研究的一个领域。Consequently, safe component composition is an area of active research in software engineering.

# 架构模式：Architectural Patterns

设计模式解决了类交互尺度上的设计问题。然而，**设计模式不太适合解决更大规模的设计问题**，这些问题涉及可能分布在许多不同计算机或环境中的软件系统的整体架构 （分布式系统架构）As was covered in OOSE, design patterns address design problems at the scale of class interactions. However, they are less suited to addressing larger-scale design problems that concern the overall architecture of a software system that may be distributed across many different computers or environments. 

![](/static/2021-02-09-22-24-56.png)

:orange: 架构模式（有时称为架构风格），它们显示了**架构级的问题的可重用解决方案**。 architectural patterns, sometimes called architectural styles, that show reusable solutions to architecture level problems

* Model view control
* Client server
* Peer to peer
* **面向消息** Message oriented architecture
  * 使用消息传送提供者来协调消息传送操作。
  * MOM 需要提供 API 和管理工具。客户端使用api调用，把消息发送到由提供者管理的目的地。
  * 在发送消息之后，客户端会继续执行其他工作，并且在接收方收到这个消息确认之前，提供者一直保留该消息。
* **管道过滤器** Pipe and filter
* **插件架构** Plugin architecture

# UI设计业务分离：Separating concerns in user interface design

![](/static/2021-02-09-22-36-09.png)

**存储用户数据** storing user data

**呈现不同的数据观** presenting different views of the data

**更改数据** changing the data

让我们考虑一下这一原则如何应用于软件系统与其用户之间的交互设计。一个典型的信息系统将负责：

* （文字、图片、视频、音频、关系......）
* 表格、图形、预览、缩略图、样本、流媒体、下载）
* 创建、删除、编辑、复制......

# MVC模式：Model-View-Control

![](/static/2021-02-09-22-38-41.png)

我们需要一种架构模式来分离这些不同的关注点。这张图说明了模型-视图-控制架构模式，它使用组件图体现了这种方法。**架构模式通常更容易使用组件图**而不是类图来描述，**因为它们代表了系统中长期存在的集合体的安排**，而不是少量类的逻辑组织。We need an architectural pattern that separates these different concerns. The diagram illustrates the model-view-control architectural pattern which embodies this approach using a component diagram. Architectural patterns are often easier to describe using a component diagram, rather than a class diagram, as they represent the arrangement of long-lived assemblies in a system, rather than the logical organisation of a small number of classes.

:orange: MVC模式主要关注的是以下几个方面的分离：The pattern is primarily concerned with a separation of concerns between:

* **保存在<code>Store</code>中的数据的内部表示，并以<code>Model</code>接口的形式呈现**；the internal representation of data held in a <code>Store</code> and presented as a <code>Model</code> interface;
* **通过<code>View</code>向外部世界展示的关于该（潜在的复杂）数据模型的不同观点**； the different perspectives on that (potentially complex) data model presented to the outside world via the <code>View</code>;
* **可由<code>Control</code>应用到数据模型的转换** the transformations that can be applied to the data model by the <code>Control</code>

:orange: 请注意，正如与UserInterface边界上的端口的关联所显示的那样，这两个Model接口。然而，Store对其他组件没有依赖性 Notice that both the Model interface, as shown by the associations with the port on the UserInterface boundary. However, the Store has no dependency on the other components.

* 这种方法的一个主要【好处】是，**每个组件都有明确的责任，可以在不影响其他组件的情况下进行改变**。 A key benefit of this approach is that each of the components has a clear responsibility and can be changed without affecting the other components.
* 实现View和Control的组件可以被改变而不改变底层的数据表示。 The component that realises the View and Control can be altered without changing the underlying data representation.
* **同样地，Store组件的实现可以独立于UserInterface而被改变，只要替换的Store仍然提供相同的模型接口**。 Similarly, the implementation of the Store component can be changed independently of the UserInterface, so long as the replacement Store still provides the same Model interface.

---

:orange:通常情况下（但不一定），**View和Control组件被整理成一个单一的顶级UserInterface组件**，如图所示。

* 这反映了使用Swing或GTK等框架或工具箱构建用户界面的常见情况。 Typically (but not necessarily) the View and Control components are collated into a single top level UserInterface component, as shown on the diagram. This reflects a common situation where the user interface is constructed using a framework or toolkit such as Swing or GTK.
* 事实上，MVC模式在用户界面工具包中非常普遍，**以至于通常不需要有意识地考虑以这种方式安排用户和系统之间的交互：框架的设计为你执行了这些约定** In fact, the MVC pattern is so prevalent in user-interface toolkits, that it often isn’t necessary to consciously think about arranging the interaction between a user and a system in this way: the design of the framework enforces the conventions for you.

## MVC局限性

这种惯例的一个后果是，**View和Control组件经常被耦合在一起**。改变控件可能也意味着改变一些模型在View中呈现给用户的方式，反之亦然 One consequence of this convention is that the View and Control components are often coupled together. Changing the Control may also mean changing how some of the model is presented to the user in the View, and vice-versa.

## 修改MVC模式：Adapting the MVC pattern

**如果我们需要在短时间内重复使用相同的数据，我们可以在视图中保留一些模型信息。这就是所谓的缓存。这很有用，因为有时访问或使用模型会很昂贵，所以如果我们能防止重复刷新这些数据，那么我们就能使我们的应用程序更有效率**。 We can retain some model information in the view, if we need to use the same data repeatedly in a short space of time. This is called caching. This is useful because sometimes accessing or using the model can be expensive, so if we can prevent repeated refreshes of this data then we can make our application more efficient.

**我们还可以改变视图获取数据的机制。视图的更新可以是来自模型的通知，也可以是视图对模型的轮询（如果数据变化非常频繁** We can also vary the mechanism by which the view gets data. The view can be updated as a result of notifications from the model that something has changed, or by the view polling the model (if the data changes very frequently).

![](/static/2021-02-09-22-45-48.png)

:orange: 除了系统组织之外，MVC模式在如何安排组件的内部实施细节和组件之间的相互作用方面留下了相当大的灵活性。选项包括，例如： Beyond system organisation, the pattern leaves considerable flexibility as to how the internal implementation details of components and interactions between components are arranged. Options include, for example:

* **在视图中保留一些模型信息** The retention of some model information in the View
  * **如果模型的某些部分不经常更改，并且反复刷新视图会导致不必要的工作，那么这一点可能会很有用** This can be useful if parts of the model don’t change very often and repeatedly refreshing the view causes unnecessary work
  * 依据模型操作接口的不同，视图可能需要多次调用才能获得足够的显示数据。对未变化数据的不必要的频繁访问，也将损害操作性能。
* **视图可以根据模型发出的某项更改的通知进行更新，或者通过视图对模型进行轮询进行更新**。The View can be updated as a result of notifications from the model that something has changed or by the View polling the model
  * **轮询可以作为用户操作的结果或定期发生**。 Polling can occur either as a consequence of user action or periodically
* **视图和控制直接与系统的用户在系统边界进行交互**。The View and Control interact directly with the system’s user at the system boundary.
  * **视图和控制在系统边界上与系统的用户直接互动**。这可能是一个人，也可能是另一个软件系统。互动的方式可以是文字、图形、视频等等 The <code>View</code> and <code>Control</code> interact directly with the system’s user at the system boundary. This may be a human, or another software system. The mode of interaction could be textual, graphical, video and so on

# 分布式系统模式：Building Distributed System

当系统所提供的服务必须从许多不同的地方访问时，或者当构建一个能够管理系统需求的单一计算机不可行时，就会使用分布式系统。当系统由许多不同的软件组件组成并由不同的组织管理时，它们也很有用 A distributed system is used when the services provided by the system must be accessible from many different locations, or when it is infeasible to construct a single computer capable of managing the demands on the system. They are also useful for when the system is to be composed of many different software components that are managed by different organisations.

![](/static/2021-02-09-22-49-20.png)

分布式系统是随着计算网络和基础设施的发展而出现的，例如以太网标准和互联网及其前身。这些技术允许<font color="red">将软件系统的不同组件部署在物理上不同的计算机上，并根据标准协议进行通信</font>。Distributed systems arose alongside the development of computing networks and infrastructures, such as the Ethernet standards and the Internet and its predecessors. These technologies permitted the different components of a software system to be deployed on physically different computers and communicate according to standard protocols.

## 采用分布式模式原因

:orange: **分布式架构模式关注的是，根据影响该系统的特定设计考虑因素，在分布式系统中组织服务**。采用分布式系统的原因有以下几点 。Distributed architectural patterns are concerned with the organisation of services within a distributed system in accordance with the particular design considerations that affect that system.There are several reasons that a distributed system might be adopted:

* **系统提供的服务必须可以从许多不同的地点获得** The services provided by the system must be accessible from many different locations.
* **建造一台能够管理系统所有信息或计算需求的[单一物理计算机]是不可行的** It is infeasible to construct a single physical computer capable of managing all the information or computational demands on the system
* 该系统将由许多<font color="red">不同的软件组件组成，由不同的组织管理和实施</font>。The system is to be composed of many different software components that are managed and implemented by different organisations.

:orange: 我们将研究以下分布式架构模式的动机、结构和实现  motivation, structure and implementation of the following architectural patterns

* 目标 motivations
  * 希望从不同的地点获得服务。Desirable to access services from different locations.
  * 无法在一台电脑上存储和处理数据。Infeasible to store and process data on a single computer.
  * 由来自不同组织的许多不同软件组件组成Composed of many different software components from different organisations
* 模式 patterns
  * **客户机-服务器**client-server
  * **点对点**peer-to-peer

# 分布式-集中化存储服务优点：Benefits of centralising information storage and service provision

![](/static/2021-02-09-22-54-40.png)

**对于分布式软件系统而言，集中对信息和服务的访问**可能是一个很好的设计选择，这有几个原因。这些原因包括：There are several reasons why centralising access to information and services could be a good design choice for a distributed software system. These include:

* **数据的管理和组织可以在一个单一的、一致的结构中进行维护（和控制**）。The management and organising of data can be maintained (and controlled) in a single, consistent structure.
* **避免了确保同一数据项目的多个副本之间的一致性问题**。The problem of ensuring consistency between multiple copies of the same data item is avoided.
* **这种方法为用户提供了一个单一的、全球已知的访问点**。The approach provides a single, globally known, point of access to users.
* **对服务功能变化的管理可以在服务器上进行，而不是在多个客户端上进行**。The management of changes to service functionality is can be undertaken in the server rather than in multiple clients.

---

在应用增加数据内聚力设计原则和抽象-发生设计模式时，我们也看到了类似的好处：在这两种情况下，我们关注的是**减少管理不必要的重复数据或软件的成本** We have seen similar benefits when applying the increase data cohesion design principle and the abstraction–occurrence design pattern: in both cases we were concerned with reducing the costs of managing unnecessary duplication of data or software

# C/S分布式模式：Client-Server Architectural Pattern

![](/static/2021-02-09-22-57-27.png)

**客户机-服务器模式通过将【信息和服务的管理整合到一个集中式服务器中**】，在架构层面体现了这一理念。该图使用组件图显示了客户端服务器系统的状态快照。The <em>client-server</em> pattern embodies this philosophy at an architectural level by collating the management of information and services into a centralised server. The diagram shows a snapshot of the state of a client server system using a component diagram

* 本系统的职责安排如下 Responsibility in the system is organised as follows:
  * **服务器负责通过【公开接口向一个或多个客户端提供对其托管的信息或服务的访问】** The server is responsible for providing access to information or services it hosts to one or more clients through a common interface.
  * **服务器还负责通过验证客户的更改请求来确保其存储的信息的完整性** The server is also responsible for ensuring the integrity of the information it stores by validating change requests from clients.
  * **客户端负责将从服务器收集到的信息呈现给最终用户，并向服务器发出命令**。 The client is responsible for presenting information gathered from the server to end-users and for issuing commands to the server.
    * **终端用户可以是人或其他软件组件** The end-users may be people or other software components

## 协议，会话，API：Protocols，sessions，APIs

让我们仔细看看服务器和客户端之间的通信是如何工作的。

:orange: 客户机和服务器之间的**通信由应用级协议的定义来规范**，该<font color="red">协议描述了客户机和服务器之间可以传递的合法消息</font>。Communication between clients and servers is regulated by the definition of an application level <em>protocol</em> describing the legal messages that can pass between clients and servers.

* 在**面向组件系统**中，协议<font color="red">由服务器的API接口（接口规范）定义</font>。In a component oriented system, the protocol is defined by the server’s application programming interfaces (APIs).

:orange: 在C/S模式中，有**两个接口**。In a client-server system, there are two interfaces.

* 第一个接口由客户机使用，以表明它希望**与服务器建立新的连接，以便进行通信**。The first interface is used by the client to indicate that it wishes to establish a new connection with the server in order to communicate.
  * `InitConnection`
* 服务器输出第二种类型的接口，即**主API(公开接口**)。该接口**指定了客户端和服务器之间建立连接后可以发送的消息**。 The server exports a second type of interface, the main API. This interface specifies the messages that can be sent once a connection has been established between client and server.

:orange: 每次客户端连接到服务器时，都会建立一个单独的session。 A separate <em>session</em> is established each time a client connects to the server.

* 这种安排<font color="red">允许服务器【同时处理】来自多个客户端的多个连接</font>。连接的状态（如服务器所感知的那样）由负责该连接的会话来维护 This arrangement allows the server to handle multiple connections from many clients simultaneously. The state of a connection (as perceived by the server) is maintained by the session responsible for that connection.

:orange:**API由服务器的端口提供，但实际行为是由嵌套在服务器中的Session组件实现的**。 The API is provided by the server through a port, but the actual behaviour is realised by Session components nested inside the Server.

* **每个Client对应一个Session组件，每个Client组件通过不同端点的端口实现一个API接口**。 There is one Session component per Client, each realising an API interface through a port at a different endpoint.
* **客户端a和b已经建立了连接，并在程序集/配件中与各自的Session组件连接** Clients a and b have already established connections and are wired to their respective Session components in the assembly.

:orange: **`InitConnection`接口由Server提供并直接实现，用于创建新的Session组件。**。 The InitConnection interface is provided by and realised directly by the Server and is used to create new Session components. 

* 在所示的配件中，客户端n被连接到InitConnection接口，一个新的Session刚刚建立，准备被n的所需接口消耗 In the assembly shown, Client n is wired to the InitConnection interface and a new Session has just been established ready to be consumed by n’s required interface.

## C-S-邮件应用例子

软件工程中客户机-服务器架构和标准的例子很多，包括万维网、subversion版本控制系统、Skype等即时通讯应用程序、FTP协议和支持实施安全通信的公钥基础设施。There are many examples of the client-server architectures and standards in software engineering, including the world wide web, the subversion version control system, instant messenger applications such as Skype, the FTP protocol and the public key infrastructure that supports the implementation of secure communications.

![](/static/2021-02-09-23-06-06.png)

该图展示了使用客户机-服务器模式的典型电子邮件系统的架构。该模式应用于 EmailClients（例如 Thunderbird 或 Outlook）和 EmailServer 组件之间。服务器实际上向客户机**提供了两个独立的服务，每个服务都遵循客户机-服务器模式，并由子组件实现** The diagram illustrates the architecture of a typical email system using the client-server pattern. The pattern is applied between <code>EmailClient</code>s (Thunderbird or Outlook, for example) and <code>EmailServer</code> components. The server actually provides two separate services to the client, with each following the client-server pattern and implemented by sub-components

* `Retrieval`组件为客户端提供了通过IMAP接口（有**电子邮件检索协议规范**）访问和下载存储在服务器上的电子邮件的功能。 <p>The <code>Retrieval</code> component provides facilities for the client to access and download emails stored on the server via the <code>IMAP</code> interface, a email retrieval protocol.</p>
* `Transport`组件允许客户端通过SMTP接口（有**电子邮件传输协议**）提交电子邮件，以便向其他用户发送。<p>The <code>Transport</code> component allows the client to submit emails for delivery to other users, via the <code>SMTP</code> interface, an email transport protocol.</p>
* **电子邮件系统架构的其余部分对客户端是透明的**。 The rest of the email system architecture is transparent to the client.
  * 远程邮件传输客户端(MTA)提供的一个单独的SMTP接口用于**通过中继机制将提交的电子邮件转发给预定的收件人**。 A separate SMTP interface provided by a remote Mail Transfer Client (MTA) is used to forward submitted emails to their intended recipient via a relay mechanism.
  * 另一个MTA利用第三个SMTP接口**将系统中其他用户的邮件传送到服务器。** Another MTA makes use of a third SMTP interface to deliver emails to the server from other users of the system.
* <font color="red">从这些外部 MTA 的角度来看，电子邮件服务器只是另一个邮件传输客户端</font> From the perspective of these external MTAs, the email server is just another mail transport client.

:orange: 还请注意，将这两个独立的功能（**检索 `Retrieval` 和交付 `Transport`**）组成一个单一的服务器组件是合理的，因为两者都依赖于<font color="red">访问内部电子邮件存储组件(传送，检索都需要访问内部存储模型 `Store`)</font> Notice also that it makes sense to compose these two separate functions (retrieval and delivery) into a single server component, because both rely on access to the internal email storage component

## 胖瘦客户机：Thin vs Fat Clients

![](/static/2021-02-09-23-11-46.png)

瘦客户机架构是客户机服务器模式的经典或纯粹主义者方法。**这种安排中的客户端有时被称为dumb客户端或终端，因为它们包含很少的程序逻辑。所有信息和尽可能多的功能都被收集到服务器中。客户机的功能非常有限，只负责向服务器发出指令并显示结果。甚至关于如何向用户展示信息的决定也可能由服务器控制**。 Thin client architectures</em> are the classical or <em>purists</em> approach to the client server pattern. The clients in this arrangement are sometimes referred to as <em>dumb</em> clients or terminals, because they contain very little program logic. All information and as much functionality as possible is collected into the server. The client has very limited functionality and is responsible only for issuing instructions to the server and displaying results. Even decisions about how to present information to the user may be controlled by the server.

胖客户端架构采用了相反的方法，**将更多的信息管理、验证、处理和存储责任下放给客户端本身**。胖客户端架构之所以有用，是因为它们可以通过**在客户端中缓存一些信息来减少客户端和服务器之间所需的通信量**。此外，**一些处理工作被委托给客户端，从而减少了对服务器的计算需求** Fat client architectures</em> take the reverse approach, devolving much more responsibility for information management, validation, processing and storage to the clients themselves. Fat client architectures can be useful because they can reduce the amount of communication required between the client and the server by caching some information in the client. In addition, some of the processing is delegated to the clients, reducing computational demands on the server.

:orange: 可见，在系统的瘦客户机架构和胖客户机架构之间需要进行权衡。**将责任下放给客户机可以减少服务器的负荷，但也有可能在存储数据中引入不一致的风险**，并意味着随着软件缺陷的发现，必须更频繁地更新客户机。As can be seen, there is a trade-off to be made between a thin or fat client architecture for a system. Devolving responsibility to the clients reduces load on the server, but also risks introducing inconsistencies in stored data and means that the clients have to be updated more frequently as software defects are discovered.

:orange:n **随着客户端环境的处理能力的提高，胖客户端架构已变得越来越流行**。这使得我们能够开发出更丰富、更互动的客户端用户界面，这些界面通常**需要缓存更多信息**。Fat client architectures have become more popular as processing capabilities of client environments have improved. This has enabled the development of richer and more interactive client user interfaces, which often need to cache more information.

## C/S模式优点

数据的管理和组织可以在一个单一的、一致的结构中被维护和控制。 Management and organisation of data can be maintained and controlled in a single, consistent structure.

确保同一数据项目的多个副本之间的一致性的问题被避免了。The problem of ensuring consistency between multiple copies of the same data item is avoided.

该方法为用户提供了一个单一的、全球知名的访问点。The approach provides a single, globally known, point of access to users.

对服务功能变化的管理可以在服务器中进行，而不是在多个客户端进行。The management of changes to service functionality can be undertaken in the server rather than in multiple clients.

## C/S模式局限性

![](/static/2021-02-09-23-20-14.png)

* **服务器和客户之间的通信可能会受到不可接受的延迟的影响** Communication between servers and clients may suffer from unacceptable latency
  * 这意味着服务器接收命令或客户端接收响应的时间过长。<font color="red">这个问题可以通过客户端缓存从服务器收到的一些信息来缓解（见上文关于胖客户端的描述</font> This means that it takes too long for the server to receive commands, or for the client to receive a response. This problem can be mitigated by the clients caching some of the information received from the server (see the description of fat clients above).
* **架构的可扩展性受到服务器可用的物理资源（计算能力、网络带宽、内存）的限制** The scalability of the architecture is bounded by the physical resources (computational power, network bandwidth, memory) available to the server.
  * 这种限制是不可避免的，因为服务器作为所有客户的单一参考点：**对服务器的需求随着客户数量的增加而增加**。This limitation is inevitable because the server acts as a single point of reference for all clients: the demands on the server grow as the number of clients increase. 
  * 这个问题表现在网站很容易被拒绝服务攻击所禁用。在最简单的情况下，攻击者引导尽可能多的客户在同一时间从一个网站上请求同一个网页 This problem is exhibited by the ease with which websites can be disabled by denial of service attacks. In the simplest case, an attacker directs as many clients as possible to request the same webpage from a site at the same time.
* **架构中的单一参考点（服务器）也是一个单点故障** The single point of reference in the architecture (the server) is also a single point of failure.
  * **如果服务器被禁用，那么整个系统就会停止运作**。If the server is disabled, then the entire system ceases to function. 
  * 可以通过准备备份服务器来缓解，但这重新引入了确保同一数据项/服务的多个副本之间的一致性问题 This problem can be mitigated by preparing fall-back servers, however, this then re-introduces the problem of ensuring consistency between multiple copies of the same data items or services.

因此，人们开发了其他架构模式，以提供更大的可扩展性和冗余

# P2P分布式模式：Peer-to-peer architecture pattern

在一个**分布式**软件系统中。将系统中的所有服务和数据转移到客户本身，因此每个对等体既是客户又是服务器。托管和管理服务的责任由所有客户共享。**适用于负责大量计算处理或托管大量信息的系统，但受到物理硬件能力的限制**。Tor项目使用点对点 Within a distributed software system. Moves all services and data in the system into the clients themselves so that ever peer is both a client and a server. Responsibility for hosting and managing services is shared amongst all clients. Suitable for systems that have responsibility for extensive computational processing or hosting large amounts of information, but is constrained by the physical hardware capabilities. Tor project uses peer-to-peer. 

---

正如我们所看到的，**C/S架构的一个关键问题是，随着客户机数量的增加，服务器的可用资源难以扩展**。 As we have seen, a key problem with the client-server architecture is the difficulty of scaling resources available to the server as the number of clients grows.

* 胖客户端架构是通过将更多功能转移到客户端来管理这一问题的一种方法。 The fat client architecture is one approach to managing this problem by moving more functionality into the clients.

![](/static/2021-02-09-23-20-50.png)

:orange: peer-to-peer架构模式通过**将系统中的所有服务和数据转移到客户端本身来扩展**这种方法，从而<font color="deeppink">使每个对等体既是客户端又是服务器</font> The <em>peer-to-peer</em> architectural pattern extends this approach by moving all services and data in the system into the clients themselves so that every peer is both a client and a server.

* 这种架构的一个结果是，托管和管理服务的责任由所有客户共享。A consequence of this architecture is that responsibility for hosting and managing services is shared amongst all the clients.
* 当**客户的增加导致对资源的需求增长时，服务器的可用资源也会相应增加**，因为每个对等体都要履行这两个角色  As the demand for resources grows due to an increase in clients, there is an equivalent increase in the resources available for servers, because each peer fulfils both roles.
* 因此，当**系统（服务器）负责广泛的计算处理或托管大量信息，但受到硬件（服务器）物理能力的限制时**，<font color="red">点对点架构就特别有用</font> Consequently, a peer-to-peer architecture is particularly useful when a system has responsibility for extensive computational processing, or hosting large amounts of information, but is constrained by the physical capabilities of the hardware.

## 例子：Tor

由于Knutella等文件共享应用的普及和BitTorrent协议的各种实现，许多人对点对点应用非常熟悉 Many people are familiar with peer-to-peer applications as a result of the proliferation of file sharing applications such as Knutella and the various implementations of the BitTorrent protocol.

第三代洋葱式路由（Tor）项目是另一个利用点对点架构模式的系统的例子。该系统用于支持用户怀疑被攻击者监视的网络上的参与者之间的匿名和不可追踪的通信 The Third Generation Onion Routing (Tor) project is another example of a system that leverages the peer-to-peer architectural pattern. The system is used to support anonymous and untraceable communication between participants on a network that the user suspects is being monitored by an attacker.

与直接通过互联网连接发送的通信不同，Tor消息是通过Tor网络中随机选择的对等节点发送的。信息被发件人包裹在一系列的加密层中（因此是<em>onion</em>路由）。每次消息通过Tor节点时，都会去掉一层加密，这样节点的观察者就无法匹配传入和传出的消息 Unlike communications sent directly over an Internet connection, Tor messages are sent via a random selection of peer nodes in the Tor network. Messages are wrapped in a series of layers of encryption (hence <em>onion</em> routing) by a sender. Each time a message passes through a Tor node one layer of encryption is removed so that an observer of the node cannot match incoming and outgoing messages.

几乎所有的加密层都被移除，信息被送到了最终的收件人手中。随着越来越多的信息通过Tor网络，观察者越来越难找出信息的收件人 Eventually all the layers of encryption are removed and the message is delivered to the final recipient. As more messages pass through a Tor network, it becomes increasingly difficult for an observer to work out who the recipient of the message is.

## 优点

随着客户数量的增长，服务器可用资源的扩展没有困难。

良好的安全性（Tor项目）。

No difficulty of scaling resources available to the server as the number of clients grow.

Good security (Tor project).


## P2P局限性

点对点架构的一个关键挑战是**服务分配和发现**：如果服务被分配给任何可用的或参与的节点，你怎么知道在哪里寻找提供相同服务的其他对等体。此外，如果任何人都可以参与点对点系统，你如何确定哪些潜在的对等体值得信任？

<p>One key challenge in peer-to-peer architectures is service distribution and discovery: if services are distributed to any available or participating node, how do you know where to look for other peers offering the same service. In addition, if anyone can participate in a peer-to-peer system, how do you determine which of the potential peers to trust?</p>

## Peer Discovery

服务分配和发现的挑战：如果服务被分配给任何可用的或参与的节点，**你怎么知道在哪里寻找其他提供相同服务的对等体？如果任何人都可以参加，我们如何知道哪些对等体值得信任**？解决方案。Challenge of service distribution and discovery: if services are distributed to any available or participating node, how do you know where to look for other peers offering the same service? If anyone can take part, how do we know which peers to trust? Solutions:

![](/static/2021-02-09-23-22-10.png)

为了解决这些问题，可以对点对点模式进行一些调整，包括： There are several adaptations that can be made to the peer-to-peer pattern to accommodate these problems, including:

* **使用全球已知的注册表来记录系统中对等体的地址** Using a globally known registry to record the addresses of peers in the system.
  * 使用一个全球知名的注册表来记录对等体的地址。一个对等体首先查询注册表，以发现哪些其他对等体是可用的，注册表作为一个超级对等体，所有其他对等体首先参照它来发现资源。 In this approach, a peer first queries the registry to discover what other peers are available. The registry effectively acts as a super-peer, to which all other peers refer first for resource discovery.
* **在基于对等体的网络中进行搜索** Searches across the peer based network.
  * **每个对等体都维护自己的已知其他对等体的注册表，这些对等体以前曾与它联系以请求资源**。当这种情况发生时，提出请求的对等体也会报告它的可用资源。当一个对等体对另一个对等体持有的资源进行搜索时，**它会向它所知道的所有对等体询问该资源**。这些对等体随后将请求传递给它们的已知对等体，**直到资源被发现或请求超时为止** Each peer maintains its own registry of known other peers that have previously contacted it to request resources. When this happens the requesting peer also reports the resources it has available. When one peer performs a search for resources held by another peer it asks all the peers it knows for the resource. These peers then pass on the request to their known peers until the resource is discovered or the request times out.
* **两种模式的混合体** A hybrid of both patterns.
  * 上述两种解决方案的混合体，**其中部署了几个注册中心，每个注册中心都包含可用对等体的列表。每个注册中心都试图通过查询其他注册中心来维持一个独立的、最新的可用对等体列表** Hybrid of both above solutions in which several registries are deployed, each of which contains a list of available peers. Each registry attempts to maintain an independent and up-to-date list of available peers by querying the other registries.

:orange: 所有这些方式都涉及权衡。All of these styles involve trade-offs. 

* **使用全球知名的注册表与C/S模式有一些共同的局限性**：The use of a globally known registry shares some of the limitations of the client-server pattern:
  * 如果注册表不可用（类似C/S模式，服务器down了），那么没有一个对等体将能够发现可能拥有他们所需资源的新对等体。 if the registry becomes unavailable then none of the peers will be able to discover new peers that might have the resources they need.
* 另一方面，**基于对等体的网络搜索不能保证找到所需的资源，如果在到达正确的对等体之前搜索超时**。 On the other hand, the peer based search has no guarantee of finding the required resources if the search times out before the right peer is reached.
* <font color="red">混合方法缓解了其他两种方法的缺点，但并没有消除这些缺点，同时引入了大量额外的架构复杂性</font> The hybrid approach mitigates but does not eliminate the disadvantages of the other two approaches while introducing significant additional architectural complexity.

# 信息处理模式：Information Processing Patterns

信息处理模式，**处理组件之间如何传递消息，而不是将此留给组件中间件**，此时消息传递并不总是同步和即时的

为组件之间的一般**异步**通信提供基础。**所有的通信都以离散的消息形式发生，通过消息总线。总线根据路由策略将消息路由到适当的客户端**

![](/static/2021-02-09-23-23-10.png)

到目前为止，我们已经研究了在一个分布式系统责任的模式。 So far we have looked at patterns for distributing responsibilities in a system.

:orange: **信息处理模式解决了系统内高效信息管理的难题**。这可能是为了<font color="red">处理大量的数据或支持组件之间的有效协作</font>Information processing patterns address challenges of efficient information management within a system. This may be for processing large amounts of data or for supporting effective coordination between components. The patterns we will look at are:

* 面向消息的架构模式
* 管道和过滤器模式

# 组件通信-同步通信机制的缺点:Disadvantages of synchronous communication mechanisms

![](/static/2021-02-09-23-24-16.png)

到目前为止，我们忽略了**组件之间如何传递消息的问题**，将这一细节**留给了组件的中间件**。So far we have ignored issues of how messages are passed between components, leaving this detail to the component middleware.

我们还**假设消息传递在很大程度上是同步的和即时的**。有时这些假设对于**组件间的通信**来说是**不合适**的，因为  We have also assumed that message passing is largely synchronous and instantaneous. Sometimes these assumptions are inappropriate for inter-component communication because: （同步通信是指一个组件向另一个组件发送一个消息，然后等待回复。这很有用，因为它**导致了可预测的通信**，但也有一些缺点 Synchronous communication is when a component sends a message to another component and then waits for a reply. This is useful as it leads to predictable communication, but has a number of downsides:）

* **底层的通信通道可能不可靠，或者受到延迟的影响，从而破坏了组件中预期的计算流** Underlying communication channels may be unreliable or subject to delays that disrupt the expected flow of computation in a component.
* **在处理开始之前，计算的信息提供者**。Information providers for a computation before processing begins.
  * 客户端必须在处理开始前选择**哪些组件将为计算提供信息**，因为一次只能提出一个请求。这意味着计算的最小时间通常是所提出的信息请求数量的一个系数 The client must choose which components will provide information for a computation before processing begins. because only a single request can be made at a time. This means that the minimum time for the computation is typically a factor of the number of information requests made
* **按照预先确定的顺序从其他组件中请求的信息** Information from other components requested in a pre-determined sequence.
  * 组件被迫按照预先确定的顺序向其他组件请求信息，因为它们需要依次等待每个组件的响应。**然而，一些应用程序可能不需要每个请求的响应，只要获得足够数量的响应即可** Components are forced to request information from other components in a pre-determined sequence because they need to wait for a response from each component in turn. However, some applications may not need responses from every request, provided a sufficient number of responses are obtained
* **无法利用计算并行来改善响应时间** Cannot exploit computational parallelism to improve response time.
  * 整个系统设计不能利用计算的并行性来提高响应时间。当一个组件等待消息被传递给另一个组件时，它可能会**不必要地空闲**。例如，它可能正在处理从其他组件收到的消息 The overall system design cannot exploit computational parallelism to improve response time. A component may be unnecessarily idle while it waits for a message to be delivered to another component. It could be handling messages received from other components, for example.

## 同步异步例子：Synchronous vs. asynchronous communication: example

![](/static/2021-02-09-23-25-46.png)

该图说明了如果**客户使用同步通信提出请求，客户组件和Insurance提供者**之间的通信。The diagram illustrates the communication between the client component and the insurance providers if the client makes its requests using synchronous communication.

* 确定最佳报价所需的时间将等于被轮询的Insurance提供者所需时间的总和。 The time taken to identify the best quotation will be equal to the sum of times taken by each of the individual insurance providers polled.
* 此外，如果任何一个<font color="red">提供者无法提供响应（例如由于网络故障），额外的时间将被添加到计算的持续时间中而没有任何好处</font>。 In addition, if any of the providers are unable to provide a response (due to network failure, for example) additional time is added to the duration of the computation without benefit.
* 同步通信还意味着，<font color="red">在第一个(报价)请求完成之前，该组件无法处理其他报价请求</font>。 The synchronous communication also means that the component cannot deal with other quotation requests until the first one is completed

![](/static/2021-02-09-23-26-44.png)

另外，报价组件可以**异步**联系每个提供者（brokers）组件，如图所示。在这种安排下，客户向每个保险商发送请求，而**不等待立即回复**。一旦所有的请求都被发送，报价组件就会等待最少数量的保险商回应报价，然后向客户发送一份初步估计。随着进一步报价的出现，该估算可以根据需要进行更新。Alternatively, the quotation component could contact each of the brokers asynchronously, as illustrated in the diagram. In this arrangement, the client sends a request to each insurance provider without waiting for an immediate reply. Once all the requests have been sent, the quotation component then waits for a minimum number of insurance providers to respond with a quotation and then sends an initial estimate to the customer. As further quotations arise the estimate can be updated as required.

# 信息处理模式-面向消息架构模式（异步）：Message oriented architecture pattern

:orange: 面向消息的架构（MOA）模式为**组件之间的一般异步通信**提供了基础。 The <em>Message Oriented</em> architectural (MOA) pattern provides a basis for general asynchronous communication between components.

* 在该模式中，所有通信都是以**通过消息总线**的离散消息形式进行的。 In the pattern, all communication occurs as discrete messages that pass through a message bus.
* 该总线根据路由策略将消息路由到适当的客户端 The bus routes messages to the appropriate client based on a <em>routing policy</em>.

:orange: 这种模式还有另外两个名字，这取决于**架构中哪些部分对设计问题最重要** This pattern is known by two other names, depending on which parts of the architecture are most significant for the design problem:

* **消息驱动模式**，因为一个组件的所有**内部计算都是接收另一个组件的消息的结果** the Message Driven pattern because all internal computation in a component is the result of receiving a message from another component.
* **消息代理模式**，因为**Broker负责决定哪个组件接收哪个消息** the Message Broker pattern, because a broker is responsible for deciding which component receives which message.

![](/static/2021-02-09-23-27-12.png)

这张图说明了这种模式。**消息总线被建模为一个单一的组件，为客户提供两个接口（IQueue，IBroker）** The diagram illustrates the pattern. The message bus is modelled as a single component that provides two interfaces to clients:

* `IBroker`，用于通过总线向其他客户端**发送**消息 IBroker is used to send messages to other clients via the bus.
* `IQueue` 用于从总线上**读取**由其他客户端专门发送给客户端的消息 IQueue is used to read messages from the bus that have been sent specifically to the client by other clients.

:orange: **标准接口的使用确保所有通过总线发送的消息都符合规定的格式(协议/接口规范，控制能传送的消息**)。The use of standard interfaces ensures that all messages sent via the bus comply with a prescribed format.

* 这意味着每个客户端将知道如何解析消息的结构，尽管它可能无法解释内容 This means that every client will know how to parse the structure of a message, even though it may not be able to interpret the content.

:orange: `MessageBus`**组件的内部结构**。The figure also shows the internal structure of the <code>MessageBus</code> component.

* 各个客户的消息被存储在一个Queue中，这样每个客户就有一个Queue被维护。 Messages for individual clients are stored in a <code>Queue</code> such that there is one <code>Queue</code> maintained per client.
* 每个队列实现了IQueue接口，该接口由MessageBus提供给适当的客户端。 Each queue realises the <code>IQueue</code> interface provided to the appropriate client by the <code>MessageBus</code>.
* 通常，客户端将尽可能快地消费和处理来自Queue的消息，<font color="red">当它遇到沉重的消息负载时，使用Queue作为缓冲区</font>。 Typically, the client will consume and process messages from the <code>Queue</code> as quickly as it can, using the <code>Queue</code> as a buffer when it experiences a heavy load of messages.
* **当Client处理完其Queue中的所有可用消息后，通常也会阻塞** The <code>Client</code> will also normally block once it has processed all available messages in its <code>Queue</code>.

:orange: **客户端可以使用IBroker接口发送消息**。 Separately, clients can send messages using the <code>IBroker</code> interface. 

* 这个接口是由**Broker子组件（在MessageBus中也看为一个队列，可以定义消息分发策略，有大小**）实现的。This interface is realised by the <code>Broker</code> sub-component.
* <font color="deeppink">当消息到达该接口时，Broker决定该消息应被添加到哪个Queue，通常是基于预期的终端客户</font> When a message arrives at the interface the <code>Broker</code> decides which <code>Queue</code> the message should be added to, normally, based on the intended end client.

:orange: **Broker可能无法交付消息**，

* 例如，如果**队列已满**，或者因为**消息没有有效的目的地**。在这种情况下，<font color="deeppink">Broker负责处理该消息，要么放弃该消息，要么通知客户该消息无效（通过将消息留在Sender的队列中</font>）The broker may not be able to deliver a message, if a queue is full, for example, or because the message does not have a valid destination. In this situation, the broker is responsible for handling the message, either by dropping the message, or by notifying the client that the message wasn’t valid (by leaving a message in the sender’s queue).

## 配置影响面向消息队列：Configuring message oriented architectures

![](/static/2021-02-09-23-29-09.png)

在通过**两种机制影响面向消息的架构的行为**方面，有相当大的灵活性。 There is considerable flexibility in influencing the behaviour of a message oriented architecture through two mechanisms

* **队列和客户端之间的关系**。The relationship between queues and clients.
  * **几个客户端可以连接到同一个队列**，。Several clients may be connected to the same queue,
    * 例如，如果<font color="red">添加到队列中的消息数量通常太多，单个客户端无法处理</font>。 for example, if the number of messages added to the queue is typically too much for a single client to handle.
  * **一个客户端可以监听几个不同队列上的消息**。 Alternatively, a client may listen for messages on several different queues.
    * 例如，一个客户端可以为正常消息和异常消息保持单独的队列 For example, a client may maintain separate queues for normal and exception messages.
* **Broker的消息分发策略** The policy applied by the broker to distributing messages.
* **Broker可能会根据不同的因素在不同的队列之间进行选择（进行消息分发**）。 The broker may choose between different queues depending on different factors.
  * 例如，代理可能根据特定的**预期收件人**、包含**最少消息的队列**或**消息类型**来选择队列 The broker might choose the queue based on a specific intended recipient, the queue containing the least messages, or the message type, for example.

:orange: 您可能已经注意到，Broker模式（消息代理模式）在结构上与<em>负载平衡</em>企业模式相似，只是**Broker根据负载决定将请求发送到何处** You may have noticed that the broker pattern is structurally similar to the <em>Load Balancing</em> enterprise pattern, except that the broker decides where to send a request based on load.

## 例子-库存控制系统

让我们考虑该模式的一个应用实例：一个大型连锁超市的库存控制系统。图中说明了库存控制系统所管理的**不同地理位置之间的库存流动情况**。该连锁超市拥有**大量的Store，每家Store的存货能力都很有限** Let’s consider an example application of the pattern: a stock control system for a large supermarket chain. The diagram illustrates the flow of stock between the different geographical locations managed by the stock control system. The supermarket chain has a large number of stores, each with a limited capacity for holding stock.

![](/static/2021-02-09-23-31-33.png)

* 该图展示了面向消息的库存控制系统的架构。该架构不会让人感到太过惊讶：每一个组件，无论它是处理Store、配送中心（RDC）还是供应商（Supplier）功能。The diagram illustrates the architecture of the message oriented stock control system. The architecture shouldn’t come as too much of a surprise: every component, regardless of whether it is handling store, distribution centre or supplier functionality.
* 该连锁店维持着一些区域性的分销中心（RDC），代表其区域内的Store持有大量的Stock。**当某些特定物品的Stock低于Store的某一水平时，Store就会向RDC请求额外的Stock**。 The chain maintains a number of regional distribution centres for holding larger amounts of stock on behalf of the stores in their area. When the stock of some particular item drops below a certain level in store, the store management requests additional supplies from the distribution centre.
* RDC直接从Supplier那里接收Stock。在**某些情况下，Stock不能直接从Supplier那里送到适当的RDC。在这种情况下，会向能够从可用Supplier接收Stock的RDC申请存货** The distribution centres receive stock directly from suppliers. In some situations stock cannot be sent directly from a supplier to the appropriate distribution centre. In this case, stock is requested from the distribution centre that can receive stock from the available supplier.

## 面向消息架构模式-库存控制系统：Stock control system using the message oriented architecture pattern

MessageBus中队列被忽略

![](/static/2021-04-26-12-26-59.png)

:orange: **每个组件都有它自己的消息队列，由MessageBus维护**。 Each component has it’s own queue of messages maintained by the <code>MessageBus</code>.

* 此外，**每个组件可以通过Broker向任何其他组件发送消息**。 In addition, each component can send a message to any other component via the broker.

:orange: 在实践中，只需要以下消息 In practice, only the following messages are required:

* 该系统通过（异步）消息传递机制**确保Stock被送到Store** The system ensures that stock is delivered to stores through message passing:
* Store->RDC的请求新Stock Store to RDC requests for new supplies.
* RDC->Suppliers请求生产要被分发的新Stock RDCs to Suppliers requests for new stock to be manufactured and delivered.
* Suppliers->RDC通知所要求的产品何时交付 Suppliers to RDCs notifications when the requested products will be delivered.
* RDC之间，请求转移可用的库存，并通知何时交付该库存 Between RDCs requesting stock to be transferred as available and notifications when that stock will be delivered.

## 优点-面向消息架构

![](/static/2021-02-09-23-38-21.png)

:orange: 使用集中式消息总线（MessageBus）来调解通信还有几个优点 There are several further advantages to using a centralised message bus to mediate communications:

* **通过确保所有消息通过消息总线(MessageBus)上的安全通道，通信安全性得到集中** Communications security is centralised by ensuring that all messages pass through secured channels on the message bus.
* **可以动态地配置Broker(消息分发策略的灵活性)，以便根据应用程序的状态路由消息** The broker can be configured dynamically to route messages according to the state of the application.
* **消息总线为监控组件间的通信提供了一个中央位置**。这对于记录组件间的交互非常有用，例如，用于调试或审计 The message bus provides a central location for monitoring inter-component communication. This is useful for logging interactions between components for debugging or auditing, for example.
* **该结构也使得识别瓶颈或故障组件变得更加容易，因为这通常是由拥塞队列来表示的** The structure also makes it easier to identify bottle necks or faulty components, since this is typically indicated by congested queues.

# 顺序数据处理应用问题：Sequential data processing applications

![](/static/2021-02-09-23-38-58.png)

许多软件应用程序都需要**处理大量数据，在输入和输出之间应用一些转换**。例子包括 Many software applications are required to process large volumes of data, applying a number of transformations between input and output. Examples include:

* 视频和音频媒体转码，视频被解码，可能被**重新格式化（尺寸、颜色模型等），然后被编码** Video and audio media transcoding, where video is decoded, potentially re-formatted (size, colour model etc.) and then encoded.
* 文本分析，即在将散文翻译成另一种语言之前，**检查其拼写和语法错误** Textual analysis, where prose is checked for spelling and grammar errors in one language before being translated into another.
* 银行间支付处理，大量的小规模货币转账**必须经过一系列的验证才能被批准** Inter-bank payment processing, where very large numbers of small scale currency transfers must undergo a series of validations before being approved.
* 处理和综合从科学仪器收集的数据。**原始仪器数据在用于分析之前，往往需要进行大量的重新处理和整合**。例如，天气和气候数据（温度、压力、湿度等）是从各种来源和使用各种不同的仪器收集的 Processing and synthesising data gathered from scientific instruments. Raw instrument data often needs considerable re-processing and integration before it can be used in analyses. Weather and climate data (temperature, pressure, humidity and so on), for example, is gathered from a variety of sources and using a heterogeneous range of instruments.

## 设计问题：Design Problem

这些相关的应用提出了几个设计问题 These related applications present several design problems:

* 对于一个给定的数据项，**转换中的每个步骤都可能需要不同的处理时间**。<font color="red">这可能导致一个或多个步骤成为瓶颈，从而限制了整个应用的性能</font>。Each of the steps in the transformation can take a variable amount of processing time for a given data item. This can result in one or more of the steps becoming an bottleneck which limits the performance of the application as a whole. 
  * 例如，视频数据压缩在开始之前可能需要大量的缓冲帧数据，即使其他进程，如帧大小调整完成得更快 For example, video data compression may need significant amounts of buffered frame data before it can begin, even though other processes such as frame re-sizing finish more quickly.
* 随着应用程序适应不同的需求，**步骤可能需要被替换或重新排序**。Steps may need to replaced or re-ordered as the application is adapted to different needs.
  * 例如，一个通用的翻译应用程序可能需要将文本从法语翻译成英语，英语翻译成普通话，等等。此外，纠正语言的语法和标点符号也有不同的标准 For example, a generic translation application might need to translate text from French to English, English into Mandarin and so on. In addition, there are different standards for correcting language grammar and punctuation.

:orange: 可能希望有一种方便的手段来**重复使用一些功能的组合** It may be desirable to have a convenient means of re-using some functions in combination.

* 管道和过滤器的架构模式 The pipe and filter architectural pattern
* <font color="red">信息处理模式-管道过滤器模式，解决顺序数据处理应用的设计问题</font>

# 管道过滤架构-信息处理模式：pipe and filter architectural pattern

**WHY**：许多应用需要**连续的数据处理**--数据需要通过一连串的转换来进行转化。例如，视频和音频媒体转码，视频被解码，重新格式化，然后以不同的格式再次编码 Many applications require sequential data processing - data needs transforming through a sequence of transformations. For example, video and audio media transcoding, where video is decoded, reformatted, then encoded again in a different format. 这就造成了设计上的问题，因为根据数据项目的不同，转换中的每一个步骤都会花费不同的时间，而且根据不同的需要，可能需要替换或重新排序。This creates design problems, as each of the steps in a transformation can take a variable amount of time depending on the data item, and steps may need to be replaced or re- ordered depending on different needs.

<font color="red">信息处理模式-管道过滤器模式，解决连序数据处理应用的设计问题</font>

![](/static/2021-04-26-12-51-00.png)

:orange: **管道和过滤器架构模式通过利用被处理数据的相似格式来解决这些设计问题**。该图说明了该模式的基本形式 The pipe and filter architectural pattern addresses these design problems by leveraging the homogeneous format of the data being processed. The diagram illustrates the basic form of the pattern.

* 每个必须**应用于数据的转换**都被实现为一个叫做`Filter`的单一组件。 Each transformation that must be applied to the data is implemented as a single component called a filter.
* **`Filter`【提供】并【实现】相同的接口，有时称为`Pipe`**。 Each filter provides and implements the same interface, sometimes called the pipe.

:orange: `Filter`被连接到一个称为管道（**pipeline**）的单个连续配件中，【**多个Filter组成pipeline**】

* <font color="red">每个连续的组件从左边的组件获得数据，并将输出传递给右边的组件</font> The filters are then wired into a single sequential assembly called the pipeline, with each successive component obtaining data from a component on the left and passing output to the component on the right.
* 管道作为过滤器之间数据流动的通道的软件部件,它的主要功能是<font color="red">连接各个过滤器,充当过滤器之间数据流的通道</font>。管道具有数据缓冲以及提高过滤器之间的并行性操作的作用。管道由数据缓冲区,向数据缓冲区读和写数据,判断管道为空或已满等操作定义组成

:orange: **两个特殊组件约束pipeline**。 Two special components bound the pipeline.

* `Source`组件在右侧**提供输入**，**消费`pipe`接口**(Filter提供)。 A data source component provides input on the right and requires the pipe interface.
* `Sink`组件在右边**提供`pipe`接口，以接受系统在右边的输出** Similarly, a data sink component provides the pipe interface on the right to accept the system’s output on the right.

:orange: `Scheduler`**组件，负责决定其他组件中的哪一个应该在何时执行** Finally, a <code>Scheduler</code> component is responsible for deciding which of the other components should execute at anyone time.

* `Scheduler`的工作是**管理其他组件之间的负载**，以便没有一个组件导致管道中的阻塞 The <code>Scheduler</code>’s job is to manage load between the other components so that no one component causes a bottleneck in the pipeline.
* Scheduler **通过`Control`接口控制每个组件**  The <code>Scheduler</code> controls each component via the <code>Control</code> interface.

## 管道过滤模式优点

**处理器时间是根据负载分配给不同的Filter的**。 Processor time is allocated to the different filters based on load.

* 这确保了数据在管道中的均匀流动 This ensures an even flow of data through the pipeline.

所有的**Filter都提供并需要一个Pipe接口(压入前面处理单位的数据**)，因此**可以根据需要进行重新排序和组合**（通过Scheduler？）All of the filters provide and require exactly one pipe interface, so can be re-ordered and composed as necessary.

---

> 面向对象的体系结构的一个明显的缺点是:当一个对象通过过程调用与其它对象交互时,它必须知道其它对象的标识。而当一个对象的标识改变时,需要对所有调用这一方法的对象进行修改。而在管道(过滤器这种体系结构中,过滤器与其它过滤器相连接时不必知道系统中的其它过滤器。**而且当某个过滤器发生改变时,不需要对其他过滤器进行改动**。

## 管道过滤架构变种

* 推拉驱动数据流 push or pull driven data flows
* 连续/并发数据处理过滤 sequential or concurrent data processing filters
* 可重排或替换的过滤器 re-orderable or inter-changeable filters
* 分支数据过滤 branching data filters

## Push or Pull Data Flow

**推式或拉式驱动的数据流反映了将数据移动到管道的两种不同控制过程** The push or pull driven data flows reflect two different controlling processes for moving data to the pipeline,

![](/static/2021-04-26-13-43-47.png)

:orange: 推送模型 push model

* 在推送驱动的流程中，数据提供者负责驱动应用程序 - 它将数据推送到管道中，这将触发过滤器/水槽被动地对到达的数据采取行动。**当有设定好的数据量要放入管道时，这种方式效果最好** In a push driven flow, the data provider is responsible for driving the application - it pushes data into the pipeline, and this triggers the filters / sink to passively act on that data as it arrives. This works best when there is a set amount of data to put into the pipeline.
* 数据由一个活跃的`DataProvider`驱动进入系统，它将数据提交给`FilterA`进行处理 In the push model, data is driven into the system by an active <code>DataProvider</code>, which submits the data to <code>FilterA</code> for processing.
* `DataProvider`将**继续向过滤器提供数据，直到Filter被一个满缓冲区阻塞**  The <code>DataProvider</code> will continue to supply data to the filter, until the Filter is blocked by a full buffer.
* FilterA将处理这些数据，然后将结果传递给FilterB。FilterA</code> will process the data and then pass the results on to <code>FilterB</code>. 
  * 这个过程一直持续到数据被**最后一个Filter输出到一个被动的`DataSink`为止** This process continues until the data is written out to a passive <code>DataSink</code> by the last filter.

---

![](/static/2021-04-26-14-00-15.png)

:orange: 拉取模型 pull model

* 在拉动式流程中，**请求者主动**从管道中取出数据。这导致每个过滤器依次从它上面的过滤器请求更多的数据，最终导致第一个过滤器从数据源请求数据。当有一个取之**不尽用之不竭的数据源**时，这种方法很有效，而且**请求者可以请求它所能处理的数据**。 In a pull driven flow, the requester actively takes data out the pipeline. This causes each filter in turn to request more data from the filter above it, eventually causing the first filter to request data from the data source. This works well when there is an inexhaustible source of data, and the requester can request as much as it can handle.
* `DataRequester`**主动轮询**最后一个过滤器`FilterC`的数据。The <code>DataRequester</code> actively polls the last filter, <code>FilterC</code> for data. 
* `FilterC`依次轮询`FilterB`，这个过程一直**持续到最后一个Filter轮询DataSource来处理数据**。 FilterC</code> polls <code>FilterB</code> in turn and the process continues until the last filter polls the <code>DataSource</code> for data to process.
* 然后，结果将沿着**请求管道传递回来** The results are then passed back along the request pipeline.

### 选择

选择采用哪种数据模型**取决于应用程序所要使用的系统**：The choice of which data model to employ depends on the system for which the application is to be used:

* 当待处理的数据以**不可预测或持续的方式**由**Provider持续生成时，推送数据模型是合适的**。A push data model is appropriate when the data to be processed is being continually generated in an unpredictable or continual way by the <code>Provider</code>.
  * 这意味着数据在<font color="red">其可用时被送入管道</font>。整个媒体文件的多媒体格式转换，或持续的直播流就是这种情况的一个很好的例子 This means that the data is fed into the pipeline as and when it becomes available. Multi-media format conversion of an entire media file, or of a continual live stream is a good example of this situation.
* 当数据Requester中的**一些外部事件导致数据需要被处理时**，**拉动数据模型**更合适 A pull data model is more appropriate when data needs to be processed as a result of some external event in the data <code>Requester</code>.
  * Requester只有<font color="red">在需要处理数据时才会启动管道</font>。执行一个整合了大量不同数据集的科学实验就是这种情况的一个很好的例子 The <code>Requester</code> only initiates the pipeline when it needs the data to be processed. Execution of a scientific experiment that integrates a large collection of diverse data sets is a good example of this situation.

## Sequential or concurrent data processing

数据也可以在**每个单独的过滤器中以不同的方式进行处理** The data can also be processed in different ways within each individual filter.

![](/static/2021-04-26-14-07-00.png)

该图使用推送式管道模型说明了这两种变体 The diagram illustrates the two variants using a push-style pipeline, as described above.

:orange: 在**顺序管道**中，所有提交给管道的数据都被每个Filter依次处理，然后再把数据传给下一个过滤器。 In a sequential pipeline, all the data submitted to the pipeline is processed by each filter in turn, before the data is passed on to the next filter

* 这意味着处理是串行的：处理的总时间为 This means that processing is serialised: the total time taken to process

---

![](/static/2021-04-26-14-11-07.png)

在一个**并发的管道**中，少量的数据（通常称为文本的块，或原始字节的块）**一旦可用就被送入管道**。In a concurrent pipeline, small amounts of data (often called chunks for text, or blocks for raw bytes) are fed into the pipeline as soon as they become available.

* 这意味着，<font color="red">一旦数据块被最后一个Filter处理，DataSink就会收到第一批数据块</font> This means that the data sink receives the first blocks of data as soon as they are processed by the last filter.

:orange: 乍一看，似乎并发模型总是更好的，因为**它意味着数据开始到达DataSink的时间更早，如果系统分布在一些处理节点上，整体计算应该更快结束**。这对于某些需要**不断将数据流**写入DataSink的应用（例如实时视频流）来说可能很重要 At first glance, it might seem that the concurrent model is always better, since it means that data begins arriving at the data sink earlier and if the system is distributed on a number of processing nodes the overall computation should end more quickly. This may be important for certain applications that require a constant stream of data to be written to the data sink (live video streaming for example)

* 然而，在许多应用中，<font color="red">一次性处理较大的数据块会更好</font>。However, there are many applications where it is better to process larger blocks of data in one go. 
* 例如，压缩算法如果在较大的数据块上执行，通常会实现更大的压缩率（原始数据和未压缩数据之间的大小关系）.非正式地讲，这是因为随着数据块大小的增加。Compression algorithms, for example, generally achieve a greater compression ratio (the size relationship between the raw and uncompressed data) if they are executed on larger blocks of data.Informally, this is because the probability of redundancy (repeated data) increases as the size of the data block increases

:orange: 在实践中，这意味着**执行速度和性能之间有一个权衡**，即在管道中操作的数据块大小 In practice, this means that there is a trade-off between speed of execution and performance, made in terms of the size of data block operated on in the pipeline.

## Re-orderable vs. inter-changeable filters

在设计管道架构或框架时需要做出的另一个决定是**允许管道构造器的灵活程度** Another decision to made when designing a pipeline architecture or framework is the extent of flexibility that will be permitted to a pipeline constructor.

* 两个可能的变体是<em>可重新排序的</em>或<em>可相互替换的</em>过滤器 Two possible variants are <em>re-orderable</em> or <em>inter-changeable</em> filters:

![](/static/2021-04-26-14-17-14.png)

最灵活的选择是允许过滤器**按照软件架构师的要求重新排序**。The most flexible option is to allow the filters to be re-ordered as desired by the software architect.

* 为了实现这一安排，<font color="blue">每个Filter必须提供并要求相同的接口</font> To enable this arrangement, every single filter <em>must</em> provide and require the same interface.
* 可互换的过滤器不暴露相同的API，因为它们对不同的数据类型进行操作，所以它们不能被重新排序。然而，我们可以用相同的API在作用于同一数据类型的不同组件之间进行更换--例如，在下面的例子中，**如果有更好的OCR组件发布，我们可以把OCR组件换成另一个OCR组件**。 Interchangeable filters do not expose the same API as they operate on different data types, so they cannot be reordered. However, we can change between different components that act on the same data type with the same API - e.g. in the below example, we could swap out the OCR component for another OCR component if a better one was released.

:orange: 这种灵活性意味着可以**通过重新排序处理活动来优化整体处理管道**。This flexibility means that the overall processing pipeline can be optimised by re-ordering processing activity.

* 例如，在视频处理应用中，如图所示，**裁剪**每个视频帧然后将其**转换**为灰度是比较快的。 For example, in a video processing application it is quicker to crop each video frame and then convert them to grey scale, as shown in the diagram.
* **如果采用相反的配置**，那么将每一帧的区域转换为灰度的计算时间就会被**浪费**，而这些区域无论如何都会被删除 If the reverse configuration is employed, computation time is wasted converting regions of each frame to grey scale that will be removed anyway.

---

![](/static/2021-04-26-14-19-50.png)

一个更具限制性的方案是**固定过滤器的顺序，只允许过滤器实现的互换**。A more restrictive option is to fix the ordering of filters and only permit the inter-change of filter implementations.

* 在这种情况下，<font color="red">每个过滤器可以提供并消费不同的接口</font>。In this case, each filter can provide and require different interfaces.
* 任何**过滤器都可以被另一个提供和消费相同接口的过滤器所取代**。 Any filter can be replaced by another filter implementation that provides and requires the same interfaces.
* 然而，过滤器接口的异质性意味着它们通常**不能被重新排序**  However, the heterogeneous nature of the filter interfaces means that they cannot generally be re-ordered.
* 例如，考虑将一页书的扫描图像转换为另一种语言的语音的应用，如图所示。Consider, for example, an application for converting a scanned image of a page of book into audible speech in another language, as shown in the diagram.
  * 该系统的过滤器可能是：光学字符识别；拼写检查；语法检查；文本翻译；和语音合成器。**这些组件不能重新排序，每个过滤器需要不同形式的输入数据**。然而，每个组件的实现可以**根据需要进行调换** The filters of the system might be: optical character recognition; spell check; grammar check; text translation; and speech synthesizer. These components cannot be re-ordered and each filter requires a different form of input data. However, the implementation of each component can be swapped as required.

### 选择

我们也可以在这两种架构之间做出妥协，以便管道的某些部分可以重新排序，而其他部分则需要过滤器类型的设定序列 Again, it is possible to compromise between these two architectures, so that some parts of the pipeline can be re-ordered, while others require set sequences of filter types.

## Branching Filters in pipelines

最后一种变体是允许在管道中使用分支过滤器 A final variant is to allow the use of branching filters in the pipeline, as shown in the diagram.

* 分支管道的拆分，使你可以在几个不同的管道中处理数据，每个管道的终点都是不同的汇。例如，在下图中，数据同时进入了logger和最终Sink。 Branching pipelines split so you can process data in several different pipelines, each ending in a different sink. For example, in the below diagram the data goes both to the logger and the final sink.
* 同一数据源，来自相同处理单元

![](/static/2021-04-26-14-29-54.png)

:orange: 分支过滤器可以对传入的数据做以下两件事**之一**：A branching filter can do one of two things with incoming data:

* **将数据流复制到传出Filter**； replicate the data stream to the out-going filters;
* 或者将**每个数据项过滤到传出的分支中** or filter each data item into one of the out-going branches.

:orange: 分支在管道中的作用有几个原因[为什么有分支？] Branches can be useful in pipelines for several reasons:

* **在数据的精确【副本】上执行辅助功能**，例如，在计算过程中为调试、验证或审计目的记录数据集的**中间版本**。Performing ancillary functions on exact copies of data, e.g. logging intermediate versions of a data set during a computation for debugging, verification or auditing purposes
  * 这意味着这些功能不需要内置于过滤器本身 This means that these functions do not have to be built into the filters themselves.
* **将两种不同的转换同时应用于【同一传入数据源！！！！】（相同数据源，不同转换）**。Applying two different transformations to the same incoming data source simultaneously.
  * 例如，一个视频流可能需要为高质量和低质量的分发进行编码 A video stream might need to be encoded for high and low quality distribution, for example.
* **将数据项分离成不同的类别，以便进行替代处理**。Separating data items into different categories for alternative processing.
  * 例如，一个数据流可能包含两种不同语言的单词，需要被**分离并进行不同的处理** A data stream might contain words in two different languages that need to be separated and processed differently, for example.

![](/static/2021-04-26-15-15-01.png)

# 插件架构：Plugin Architecture Motivation

当你认为**随着需求的变化可能需要添加新的功能**，或者**不同的用户会有不同的需求/想要开发自己的功能**时，就可以使用插件架构 Plugin architectures are used when you think new features might need to be added over time as requirements change, or if different users will have different requirements / want to develop their own functionality.

## WHY

有时，除了一组核心功能之外，可能很难预测一个应用程序所需的所有功能。这可能是因为 Sometimes it can be difficult to predict all the required functionality for an application, beyond a core set of features. This may be because:

* **预计随着需求的变化，可能需要逐渐添加新的功能**。It is anticipated that new features may need to be added over time as requirements change.
* **该应用程序的不同用户有不同的要求**。Different users of the application have different requirements.
  * 向所有用户提供所有需要的功能将导致系统和用户界面过于复杂 Providing all the required features to all users would result in an overly complex system and user interface.
* **预计一些用户将希望为该系统开发他们自己的功能，使它的功能适合他们的个人需要** It is anticipated that some users will want to develop their own functionality for the system, tailoring it’s features to suit their personal needs.
* **该应用程序将在各种不同的平台上运行**，其中一些平台将无法支持该应用程序的所有功能，或将以不同的方式支持这些功能。The application will run on a variety of different platforms., some of which will not be able to support all the application’s functionality, or will do so in different ways.

:orange: **插件架构为扩展软件系统的功能提供了一种灵活的机制**。A plugin architecture provides a flexible mechanism for extending the functionality of a software system.

* 在该模式中，应用程序能够**动态地搜索并将包含额外功能的组件加载到主系统中** In the pattern, an application is able to dynamically search for and load components that contain additional functionality into the main system.

## Pattern

该图说明了插件架构模式中的关键组件。

![](/static/2021-04-26-14-48-49.png)

这种架构**允许应用程序在运行时动态地加载不同的功能插件**。注册表存储了可用的组件，加载器将注册表指定的插件**构建成一个功能齐全的组件供应用程序使用**。 This architecture allows the application to dynamically load different plugins of functionality at runtime. The registry stores the available components, and the loader builds the plugin specified by the registry into a fully functioning component for the application to use.

架构中的主要组件是`Registry`，**它存储了系统可用的所有插件的规范**。The main component in the in architecture is the <code>Registry</code> that stores the specification of all plugins available to the system

* **插件组件通常提供一个符合插件架构要求的标准接口**。这可以提供： A plugin component normally provides a standard interface that complies with the requirements of the plugin architecture. This can provide
  * **如何在应用程序中实例化该插件的细节**；和/或 details of how to instantiate the plugin in the application; and/or
  * **如何**在应用程序中**使用该组件的详细信息** details of how the component can be used in the application

:orange: `Application`可以查询`Registry`以**获取特定类型的可用插件规格** The main <code>Application</code> can query the registry for available plugin specifications of a particular type.

* 当找到一个合适的插件时，`Loader`组件被**用来实例化和配置该组件**，以便由主应用程序使用，并**使用由`Registry`提供的插件规范**  When an appropriate plugin is located, a <code>Loader</code> component is used to instantiate and configure the component for use by the main application, using the specification supplied by the <code>Registry</code>.
* 然后，该**插件的功能可以通过指定的接口被访问**，就像系统中的任何其他组件一样 The plugin’s functionality can then be accessed via the specified interface, just like any other component in the system.

:orange: 插件架构类似于显式钩子和插槽的**软件框架**。然而，两者的目的是不同的。 A plugin architecture is similar to a software framework that exhibits hooks and slots. However, the purpose of the two are different.

* **一个软件框架不是一个应用程序。它是一个实用程序、库和基础设施的集合**，用于<font color="red">以特定的架构风格或为特定目的构建应用程序</font> A software framework is not an application. It is a collection of utilities, libraries and infrastructures for building applications in a particular architectural style, or for a particular purpose.

## How flexible should a plugin architecture be?

相反，插件架构对于**扩展特定应用程序的功能很有用，而不是用于构建整个应用程序**。Conversely, a plugin architecture is useful for extending the functionality of a specific application, not for building the whole application.

* 因此，**插件可以扩展应用程序的功能和目的的范围应该【受到严格的限制**】。Consequently, the range of ways in which a plugin can extend the application’s functionality and purpose should be tightly constrained.
* **纯粹**由插件构建的应用程序面临着表现出**内部平台效应的严重危险** An application that is built purely from plugins is in serious danger of exhibiting the <em>inner platform effect

另一种表达方式是观察这个过程只是重新发明了Java语言的原始机制，即使用`new`关键字将类实例化为对象。Another way of expressing this is to observe that this process simply re-invents the Java language’s primitive mechanism of instantiating classes as objects using the <code>new</code> keyword.

* 在某种程度上这是正确的，除了**程序员不需要知道什么类在运行时将被实例化为一个插件**。 To a certain extent this is correct, except that the programmer does not need to know what class will be instantiated as a plugin at runtime.
* 此外，通过`newInstance`方法创建一个实例，在计算上要昂贵得多。 In addition, creating an instance via the <code>newInstance</code> method is computationally much more expensive. 
* **因此，选择哪些类将作为插件来实现是非常重要的，因为它们更适合成为松散耦合的组件，而哪些是主应用程序的核心部分** So, it is important to choose which classes will be implemented as plugins because they are better suited to being loosely coupled components, and which are core parts of the main application

## 注意！！

你需要小心，**不要让你的整个程序只是插件**，因为这可能导致一些你没有意识到的讨厌的相互**依赖关系**。 You need to be careful to not let your entire program just be plugins, as this can lead to some nasty interdependencies that you aren’t aware of.

# Summary

软件架构有助于开发人员对软件系统的高层结构和关键组件进行推理 Software architecture helps developers to reason about the high level structure and key components of a software system.

复杂的系统可以由较小的部件构建而成，通过软件将其结合在一起，形成系统的独特行为和形式

* 然而，在开发一个基于组件的系统时，有两个工程驱动因素
  * 重用。重用现有组件以创建一个更复杂的系统的能力
  * 演变。通过创建一个高度组件化的系统，该系统更容易维护。在一个设计良好的系统中，变化将是局部的，对系统的变化可以对其余的组件几乎没有影响

