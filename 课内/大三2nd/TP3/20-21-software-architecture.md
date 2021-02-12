# Software Architecture

* [Software Architecture](#software-architecture)
* [软件组件：Software components](#软件组件software-components)
* [什么是基于组件的软件系统: component based software system](#什么是基于组件的软件系统-component-based-software-system)
* [对象&组件区别：Distinguishing between objects and components](#对象组件区别distinguishing-between-objects-and-components)
* [为什么不完全使用组件？](#为什么不完全使用组件)
* [一般组件 & 特定应用组件：General purpose and application specific components](#一般组件--特定应用组件general-purpose-and-application-specific-components)
* [组件应提供多少功能：How much functionality to expose](#组件应提供多少功能how-much-functionality-to-expose)
* [组件图标记：Component Diagram Notation](#组件图标记component-diagram-notation)
* [组件系统业务分离：Separation of concerns for component systems](#组件系统业务分离separation-of-concerns-for-component-systems)
* [契约式设计：Design by Contract](#契约式设计design-by-contract)
* [编写组件接口契约：Preparing a component interface contract](#编写组件接口契约preparing-a-component-interface-contract)
  * [例子：Example Method Contract](#例子example-method-contract)
* [测试组件契约：Checking component contracts](#测试组件契约checking-component-contracts)
* [组件不兼容：Software failure caused by incompatible components](#组件不兼容software-failure-caused-by-incompatible-components)
* [抽象泄露问题：The problem of leaky abstractions](#抽象泄露问题the-problem-of-leaky-abstractions)
* [架构模式：Architectural Patterns](#架构模式architectural-patterns)
* [UI设计业务分离：Separating concerns in user interface design](#ui设计业务分离separating-concerns-in-user-interface-design)
* [MVC模式：Model-View-Control](#mvc模式model-view-control)
  * [修改MVC模式：Adapting the MVC pattern](#修改mvc模式adapting-the-mvc-pattern)
* [分布式系统模式：Building Distributed System](#分布式系统模式building-distributed-system)
  * [集中化存储服务优点：Benefits of centralising information storage and service provision](#集中化存储服务优点benefits-of-centralising-information-storage-and-service-provision)
* [Client-Server Architectural Pattern](#client-server-architectural-pattern)
  * [协议，会话，API：Protocols，sessions，APIs](#协议会话apiprotocolssessionsapis)
  * [C-S例子](#c-s例子)
  * [Thin vs Fat Clients](#thin-vs-fat-clients)
  * [局限性](#局限性)
* [P2P：Peer-to-peer architecture pattern](#p2ppeer-to-peer-architecture-pattern)
  * [例子：Tor](#例子tor)
  * [Peer Discovery](#peer-discovery)
* [信息处理模式：Information Processing Patterns](#信息处理模式information-processing-patterns)
  * [同步通信机制的缺点:Disadvantages of synchronous communication mechanisms](#同步通信机制的缺点disadvantages-of-synchronous-communication-mechanisms)
    * [同步异步例子：Synchronous vs. asynchronous communication: example](#同步异步例子synchronous-vs-asynchronous-communication-example)
  * [面向消息的架构模式：Message oriented architecture pattern](#面向消息的架构模式message-oriented-architecture-pattern)
    * [配置：Configuring message oriented architectures](#配置configuring-message-oriented-architectures)
    * [例子](#例子)
    * [采用面向消息的架构模式的库存控制系统：Stock control system using the message oriented architecture pattern](#采用面向消息的架构模式的库存控制系统stock-control-system-using-the-message-oriented-architecture-pattern)
    * [优点](#优点)
* [顺序数据处理应用：Sequential data processing applications](#顺序数据处理应用sequential-data-processing-applications)

# 软件组件：Software components

![](/static/2021-02-09-20-45-24.png)

> “A component is a physical manifestation of an object that has a well-defined interface and a set of implementations for the interface”
> "组件是一个对象的物理表现形式，该对象具有明确定义的接口和一组接口的实现"

> "可作为一个单元独立开发和交付，并可与其他组件一起组成、保持不变以构建更大的东西的软件工件的连贯包"
> “A coherent package of software artifacts that can be independently developed and delivered as a unit and that can be composed, unchanged, with other components to build something larger” 

> "组件通过加强接口的作用和单独的组件规范概念来扩展[面向对象]原则。组件必须符合组件标准"
> “Components extend [object oriented] principles by strengthening the role of the interface and by a separate notion of component specification. Components must conform to a component standard”

:orange:什么是软工中的组件 software engineering component

* 总之，<em>软件组件</em>的概念是指**具有明确定义的接口的自带状态和行为的软件束**。>In summary, the notion of a <em>software component</em> refers to a software bundle of self contained state and behaviours with well defined interfaces.
* **一些组件可以单独执行其功能，但其他组件则依赖于其他组件提供的信息和功能**。Some components can perform their functions in isolation, but others depend on information and functions provided by other components.
* 面向组件的系统 是**一组相互作用的组件的集合，这些组件被组合起来以实现一些更广泛的目的**。A <em>component-oriented system</em> is a collection of interacting components that have been combined to realise some wider purpose.
* **组件是有用的，因为它们让我们能够对整体架构中的大型子系统进行推理，而不必担心单个对象的行为或它们在代码中的实现** Components are useful because they allow us to reason about the large scale sub-systems within an overall architecture, without worrying about the behaviour of individual objects or their implementation in code

# 什么是基于组件的软件系统: component based software system

例如，一个航空公司预订系统可以设计成一个面向组件的系统，For example, an airline reservation system could be designed as a component oriented system,

* 其中包含管理预订、接受信用卡付款、发放登机牌和组织行李转移等活动的组件。containing components for managing bookings, taking credit card payments, issuing board passes and organising luggage transfers, amongst other activities.
* 预订组件可能与其他组件隔离使用，The bookings components could probably be used in isolation from other components,
* 而信用卡支付处理组件可能被许多其他系统（如航空公司的机上饮料收银机）使用。 and the credit card payment processing component might be used by many other systems (such as the airline’s inflight drinks cash register).
* 然而，其他组件，**例如登机牌发放器将依赖于预订系统**（向未付款的乘客发放登机牌并不是一个好主意），**而行李管理系统将依赖于由登机牌发放组件收集的信息**（知道行李将被送往何处）However, other components, such as the boarding pass issuer will depend on the bookings system (it isn’t a good idea to issue boarding passes to passengers who haven’t paid) and the luggage management system will depend on information gathered by the boarding pass issuing component (to know where the luggage will be sent).

# 对象&组件区别：Distinguishing between objects and components

* A component is a self-contained, self-controlled and evolving bundle of smaller scale objects. **组件是一个自足的、自控的、不断发展的较小规模的物体捆绑。**
* A component oriented system can be distinguished by a middleware framework to mediate interactions between components. **面向组件的系统可以【通过一个中间件框架来调解组件之间的交互关系】来区分。**
* The middleware can incorporate an interface definition language (IDL) that is used to define the published interfaces of each component in the system. **中间件可以加入接口定义语言（IDL），用于定义系统中每个组件的发布接口**。
  * <font color="deeppink">接口描述语言或接口定义语言(IDL) ，是一种规约语言语言，用来描述基于组件的软件工程的应用程序编程接口(API)。IDLs 以独立于语言的方式描述接口，使不共享一种语言的软件组件之间能够进行通信，例如，使用 c + + 编写的组件和使用 Java 编写的组件之间能够进行通</font>
* Components are orchestrated by defining an assembly either during system initialisation or dynamically at run time. Components in the same component oriented system may be implemented in several different programming languages. **组件是通过在系统初始化期间或在运行时动态地定义一个汇编来协调的。在同一个面向组件的系统中，组件可以用几种不同的编程语言来实现**。

:orange: 区别

* 组件和对象都是具有地址、状态和行为的软件运行时实体。此外，一个组件有一个规范，就像每个对象都有一个相关的类一样 In particular, both components and objects are software runtime entities with an address, state and behaviour. In addition, a component has a specification, just like every object has an associated class
* <font color="deeppink">事实上，在统一建模语言中，组件是对象类类型的一种特殊化，但具有一些不同的特征</font>
  * **组件是长期存在的实体，通常会在软件系统的整个生命周期内进行部署。相反，对象可以在系统的整个生命周期中被创建和销毁** Components are long lived entities that are generally deployed for the full life time of a software system. Conversely, objects may be created and destroyed throughout the lifetime of the system.
  * 使用**组件中间件**意味着，**面向组件的系统中的组件可以分布在多个不同的组件环境中，并托管在多个物理上不同的硬件系统上** The use of a component middleware means that the components in a component oriented system may be distributed between a number of different component environments and hosted on a number of physically different hardware systems.
  * 因此，不可能以与对象相同的方式直接与组件实现交互 Consequently, it is not possible to interact directly with the component implementation, in the same way as with an object.
  * **assembly不同于组件本身的实现。assembly指定了哪些组件将满足系统中每个组件的所需依赖性。通常，一个组件由一个类型和一个发布版本来标识** The assembly is distinct from the implementation of the components themselves. The assembly specifies which components will satisfy the required dependencies for each component in the system. Normally, an component is identified by a type and a release version.
  * **所有组件都为相同的中间件和 IDL 提供支持。此外，组件提供的每个接口都可以由组件中的不同对象实现**。All provide support for the same middleware and IDL. In addition, each of the interfaces provided by a component may be realised by a different object within the component.

# 为什么不完全使用组件？

实际上，**面向组件的中间件是最大限度地消除系统中各模块之间耦合的一种手段**。因此，一个合理的问题是 In effect, a component oriented middleware is a means of maximising the de-coupling between modules in a system. Consequently, a reasonable question to ask is：

* 既然我们知道松散的耦合是好的，那么为什么不把系统中的每一个对象都变成一个组件呢？Given we know that loose coupling is good, so why not make every object in the system a component?

:orange: 组件局限性

* 通过中间件调解组件交互，**除了对象对对象的直接交互所需的成本外，还需要额外的通信成本**。这是因为消息必须由中间件进行编码、传输和解码 Mediating component interactions through a middleware imposes additional communication costs on top of that required for direct object to object interactions. This is because messages must be encoded, transmitted and then decoded by the middleware
* **将对象作为组件公开会产生额外的开发成本**，因为它是系统<em>发布的</em>API的一部分。**需要维护组件的所有接口的文档，因为应该期望其他人需要了解如何使用它们**。There are additional development costs associated with exposing an object as a component, since it forms part of the system’s <em>published</em> API. The documentation for all of the interfaces to a component need to be maintained, as it should be expected that others will need to understand how to use them.
  * 此外，在不影响系统或基础设施中其他组件的实施的情况下，组件的接口规格不能轻易更改，这些组件可能由其他软件团队实施和维护 In addition, the interface specifications of a component cannot be readily changed without disrupting the implementation of other components in the system or infrastructure, which may be implemented and maintained by other software teams.
* <font color="deeppink">组件规格和设计应作为系统文档的一部分进行维护</font>。**如果系统中的每一个对象都是作为一个组件来实现的，那么系统文档就会有效地重复基础语言文档** Component specification and design should be maintained as part of the system documentation. If every object in a system is realised as a component then the system documentation effectively duplicates the underlying language documentation.
* 当然，在某种程度上，**决定将哪些对象视为组件的决定将取决于正在开发的软件系统**。Of course, the decision about which objects to treat as components will, to a certain extent, be specific to the software system under development.
  * 组件工程师的作用是决定系统中<font color="deeppink">哪些对象应该被指定为组件，并在组件中间件中公开</font> The role of the component engineer is to decide which are the right objects in a system that should be specified as components that are exposed within the component middleware

# 一般组件 & 特定应用组件：General purpose and application specific components

在构建**基于组件的系统**时，软件工程师将使用**两种不同类型的组件**：When building a component based system, a software engineer will work with two different types of component:

* **通用组件** General Component
  * 提供了通用功能，<font color="deeppink">这些功能是为了在许多不同的应用中重复使用</font>。components provide common functionality that is intended for reuse in many different applications.
  * 例如，上面描述的预订系统中的卡支付处理组件也可用于收取机场或飞机上免税购物的费用，以及支付机场餐厅的餐费等  For example, the card payment processing component in the reservation system described above could also be used to collect payments for duty-free shopping in an airport or on a plane, pay for meals in the airport restaurant and so on.These components are normally very well documented and stored in a component repository
* **特定应用组件** Application apecific
  * **组件实现了应用的特定问题功能和业务逻辑**  components implement the problem specific functionality and <em>business logic</em> of the application.
  * 例如，为在办理登机手续时签发登机牌而开发的组件将包含将航空公司的预订数据转换为登机牌的特定业务逻辑。这些组件通常被称为应用程序 <em>glue</em>，因为它们负责协调通用组件的行为以实现应用程序的特定需求 The component developed to issue boarding passes at check-in for example, will contain business logic specific to the job of converting the airline’s reservation data into a boarding pass. These components are often called the application glue, because they do the job of orchestrating the behaviour of the general purpose components to realise application specific needs.

:orange: Robert Glass(2001)认为，

* **软件工程非常擅长开发和管理通用组件** software engineering is very good at developing and managing general purpose components
  * (如卡支付处理系统)，(such as the card payment processing system),
* **但很不擅长将包含业务逻辑的组件转化为可重复使用的组件**。but quite bad at translating components that contain business logic into reusable components.
  * 例如，预订组件原则上可以调整为其他用途，如预订餐厅的桌子、公交车上的旅行或电影票。 For example, the reservation component could, in principle, be adapted to other uses, such as booking restaurant tables, travel on buses or cinema tickets.
* **然而，经常会有一些微妙的技术（和<em>社会技术</em>）原因，导致在一个领域中预订的业务逻辑在另一个环境中无法使用** However, there are often subtle technical (and <em>socio-technical</em>) reasons why the business logic of reservation booking in one domain doesn’t work in another context

# 组件应提供多少功能：How much functionality to expose

对于他们自己编写的组件，**组件工程师将需要决定开发中的每个组件将提供多少功能** For components they write themselves, a component engineer will need do decide how much functionality will be provided by each component under development

* 在**一个组件中整合大量功能会使组件的维护和使用变得非常复杂**，Combining lots of functionality in a component can make the maintenance and use of the component very complex,
  * 因为更改组件可能会影响许多其他不同系统对组件的使用。不同组件接口之间的交互也需要仔细记录。because changing the component may effect the use of the component by many other different systems. The interactions between the different component interfaces will also need to be carefully documented.
* 相反，**让组件变得非常简单意味着组件之间的交互将变得非常复杂**，Conversely, making components very simple means that the interaction between components becomes very complex,
  * 因为必须协调大量简单的组件任务以实现应用程序的一些有用功能  because lots of simple component tasks have to be orchestrated to achieve some useful functionality for the application
* <font color="blue">缓解这种窘境的一种方法是将较简单的组件嵌套在较复杂的组件中。</font> One way of mitigating this dilemma is to nest simpler components within more complex ones.
  * **这意味着一个组件的部分功能被其他子组件封装**（并可替换）。This means that some of the functionality of a component is encapsulated (and replaceable) with other sub-components
  * 这种方法还有**助于最大限度地减少**系统中任何给定分解级别的**组件之间的交互管理** This approach can also help minimise the management of interactions between components at any given level of decomposition in the system.

# 组件图标记：Component Diagram Notation

![](/static/2021-02-09-21-33-51.png)
![](/static/2021-02-09-21-34-18.png)

UML组件图是一种有用的符号，用**于勾画软件系统的高层架构**，UML component diagrams are a useful notation for sketching the high level architectures of software systems, expressing their major sub-systems as components and the interconnections between them as bound interfaces.

* **将其主要的子系统表达为组件**，
* **将它们之间的相互联系表达为绑定接口**
  * 图中说明了组件图的基本框线符号
  * **在图中，一个组件用一个带有 "插头 "小部件定型的方框来表示**。
  * **每个组件都有一个标签**
    * 在本例中，`A&B`
    * 此外，**组件A提供一个接口I**，它被显示为一个facet
    * 此外，该图还显示，**组件B需要I接口**，该接口由一个连接器表示，称为围绕面绘制的容器 denoted by a connector, called a receptacle drawn around the facet.

:orange: 请注意，与 Java 等编程语言中的接口相比，这对接口在此上下文中的含义有影响。汇编中的组件上的接口更像是服务器上的网络端口 Notice that this has implications for what an interface means in this context compared to an interface in a programming language like Java. An interface on a component in an assembly is more like a network port on a server.

* **接口是主动被消费的。接口是一个具有端点地址或标识符的实际实体，就像组件一样** The interface is <em>actively</em> being <em>consumed</em> The interface is an actual entity with an endpoint address or identifier, just like a component.
* 这意味着，如果一个组件被表示为**暴露了两个相同类型的接口**，那么它们在运行时将拥有**不同的端点地址**  This means that if a component is denoted as exposing two interfaces of the same type, they will have different endpoint addresses at run time.

# 组件系统业务分离：Separation of concerns for component systems

![](/static/2021-02-09-21-40-50.png)

* **一个组件对某些功能有明确的责任**；并且 a component has a clearly defined responsibility for some function; and
* **该组件不应受到其他组件实现细节变化的影响** the component should not be affected by changes to the implementation details of other component.

:orange: 了解了组件的概念，让我们开始看看如何使用它们来组织软件架构 Now that we’ve looked at the notion of components, lets begin to look at how they can be used to organise a software architecture.

* 正如我们所看到的那样，软件设计中的一个关键原则是确保业务分离。从架构的角度来看，这意味 a key principle in software design is to ensure a separation of concerns.

# 契约式设计：Design by Contract

> 通过合同设计的概念进一步加强了组件作为区分业务系统的一种手段的概念。**在这种方法中，一个组件的接口定义构成了某些功能的提供者和提供者之间的 "契约"**。开发Eiffel编程语言的Meyer称接口文档为 "契约"，因为它应该描述：
> The notion of components as a means of reasoning the separation of concerns systems has been further strengthened by the concept of <em>design by contract</em>. In this approach, the interface definition for a component constitutes a ‘contract’ between the provider and the supplier of some functionality. Meyer, who developed the Eiffel programming language, called the interface documentation a ‘contract’ because it should describe the:

* **使用组件所提供的接口的好处** Benefits of using the interface that are offered by the providing component.
* **对提议【使用该接口的组件的义务】**。 Obligations imposed on the component that proposes to use the interface.

:orange: 按契约设计

* 来描述**用这些非常精确的记录接口来组成软件系统的工作过程** Meyer also defined the term ‘design by contract’ to describe the process that works with these very precisely documented interfaces to compose software systems.

# 编写组件接口契约：Preparing a component interface contract

![](/static/2021-02-09-22-00-15.png)

为了提供契约，接口的文档可以包括：To provide a contract, the documentation for an interface can include:

* 由提供组件实现的**可见组件状态（组件属性**）Visible component state (component attributes) that is realised by the providing component.
* 描述**提供组件的合法状态的变量** *Invariants describing the legal states of the providing component.
* **对于接口中的每个方法** For each method in the interface
* **一个签名**，a signature
  * 包括方法标识符、参数标识符和类型、方法返回类型以及由于不当调用该方法而可能引发的任何异常 comprising a method identifier, argument identifiers and types, method return type and any exceptions that might be raised as a result of improperly invoking the method.
* **预先条件** pre-conditions
  * 描述了提供组件在方法被调用之前所需的状态，以及对所提供的不能在方法签名中表达的参数的任何限制。that describe the required state of the providing component before the method can be invoked and any restrictions on supplied arguments that cannot be expressed in the method signature.
* **Post-conditions** 后置条件
  * 描述了方法调用完成后提供组件的状态，以及对返回给调用组件的任何值的约束，这些值不能由IDL的类型系统表达。that describe the state of the providing component after the method invocation has been completed and constraints on any values returned to the calling component that cannot be expressed by the IDL’s type system.
* **Semantics**，
  * 包括，例如，方法应该以正确的顺序被调用 including, for example, the correct sequence that methods should be invoked in
* **每个方法对系统中其他组件的可见性** The visibility of each method

## 例子：Example Method Contract

![](/static/2021-02-09-22-06-16.png)

合同的不同部分可以用不同的方式来表达。The different parts of a contracts can be expressed in different ways. For example, method signatures are normally expressed formally in an interface definition language. Invariants, pre-conditions and post-conditions (collectively known as *constraints) and semantics can be documented informally using source code comments, but can also be expressed formally. Example formal notations include the <code>javax.validation</code> package annotations and the OCL constraint language.

* 例如，**方法签名通常用接口定义语言正式表达**。
* **不变量、前条件和后条件（统称为*约束**）和语义可以使用源代码注释进行非正式的记录，但也可以用正式的方式表达。
  * 正式符号的示例包括 <code>javax.validation</code> 包注释和 OCL 约束语言

---

请考虑以下包含非正式和正式文档的 Java 方法。在实践中，**如果约束条件是正式表达的，则可能需要较少的非正式文档**。The method specifies how a credit card payment can be processed using the specified component interface. The overall behaviour of the method is described in the method documentation, followed by a description of each of the parameters and their constraints. In addition, the return value for the method and the situations in which an exception might be thrown are described.

* 该方法指定了如何使用指定的组件接口处理信用卡支付。该方法的总体行为在方法文档中进行了描述，随后对每个参数及其约束条件进行了描述。此外，还描述了该方法的返回值和可能引发异常的情况

---

除了非正式的约束条件外，方法签名还有一些应用于 <code>javax.validation</code> 包的约束条件注释。这些约束应该与非正式文档保持一致。例如，<code>@Digits</code>约束可以用来指示提供的参数String必须是一个最大长度的数字序列。类似地，<code>@Future</code> 约束指定支付中使用的信用卡的到期日必须是在未来的某个时间点。<code>@Digits</code> 注解也用于限制方法的返回值。 In addition to the informal constraints, the method signature has a number of constraint annotations applied from the <code>javax.validation</code> package. These constraints should be consistent with the informal documentation. For example, the <code>@Digits</code> constraint can be used to indicate that a supplied argument String must be a sequence of digits of a maximum length. Similarly, the <code>@Future</code> constraint specifies that the expiry date for the credit card used in the payment must be at some point in the future. The <code>@Digits</code> annotation is also used to constrain the method’s return value.

# 测试组件契约：Checking component contracts

![](/static/2021-02-09-22-09-43.png)

一旦指定了正式的组件合同，就可以通过多种方式进行检查：Once specified, formal component contracts can be checked in a variety of ways:

* **Statically at compile time**. 编译器静态检测
* **Using test frameworks, such as JUnit**. 单元测试，测试框架
* **Within the program logic at runtime**. 运行期业务逻辑
* **By the component middleware at runtime**. 运行期组件中间件

# 组件不兼容：Software failure caused by incompatible components

退一步讲，我们有理由问为什么这些额外的文档如此重要？这无疑加强了这样一个论点，Taking a step back for a moment, it is reasonable to ask why is all this additional documentation so important? It certainly reinforces the argument

* **即软件工程师应该非常谨慎地选择哪些对象应该被视为组件**，that software engineers should be very careful about choosing which objects should be treated as components,
  * 因为这似乎涉及到文档的额外开销。given the extra overheads of documentation that seem to be involved.
  * 精确、准确的接口文档对于防止组件在不同的装配体中被重复使用时的误用是非常重要的 Precise, accurate interface documentation is important to prevent the misuse of components when they are reused in different assemblies.

---

阿丽亚娜5号灾难通常被作为组件规格不良的后果的例子。惯性参考软件中一个简单的铸造溢出（64位到16位）被报告为飞行控制系统的数据（而不是诊断信息），导致欧洲航天局损失了价值5亿美元的发射系统 The Ariane 5 disaster is commonly referenced as an example of the consequences of poor component specification. **A simple casting overflow (64 bit to 16 bit**) in inertial reference software was reported as data to the flight control system (rather than as diagnostic information) causing the loss of the European Space Agencies $500 million launch system <span class="citation" data-cites="lions96ariane">[@lions96ariane]

# 抽象泄露问题：The problem of leaky abstractions

![](/static/2021-02-09-22-14-19.png)

阿丽亚娜 5 的失败是由 Joel Spolsky 所指出的 <em>泄漏的抽象</em>问题引起的 The Ariane 5 failure was caused by the problem of <em>leaky abstractions</em>, noted by Joel Spolsky. 

:orange:定义

* **这意味着每当两个组件实现（接口的提供者和请求者）【在一个组件中连接在一起】时，它们之后的发展收到以下假设的影响**： this means that whenever two component implementations (a provider and a requirer of an interface) are wired together in an assembly, their future evolution is influenced by assumptions about
  * The way that a providing interface will be utilised; 提供接口的方式将被利用
  * and The way that the providing interface is realised 提供接口的实现方式

---

Spolsky最初认为，泄漏来自于底层通过抽象实现。需求组件的开发者要求更多地了解底层提供者的实现，以便构建一个更好的系统。Spolsky originally argued that the leak comes from the underlying implementation through the abstraction. The developer of the requiring component demands to know more about the underlying provider implementation in order to build a better system.

* 然而，信息的泄露是双向的，**因为接口的提供者会受到如何实现接口的假设的限制**。However, the leakage of information goes both ways, because the provider of an interface becomes constrained by assumptions as to how it is implemented
* 你的系统的每一个可观察的实现细节<em>都是</em>你的合同契约。可能会对偶然的实现细节进行假设的示例包括 every observable implementation detail of your system <em>is</em> your contract. Examples of where assumptions might be made about incidental implementation details include:
  * 从积分器收到的对象在无序集合上的排序 The ordering of objects received from an integrator over an-unordered set
  * 数据库系统的行为和结构--了解查询的评估方式会对性能产生很大影响 The behaviour and structure of a database system - knowing how queries will be evaluated can have a big impact on performance

:orange: 因此，尽管我们决心将组件的接口与其实现分开，但我们发现它们是相互关联的，因为组件设计的语义成为接口的一部分。So, despite a determination to separate the interface to a component from its implementation, we find that they become coupled, because <em>the semantics of a component’s design become part of the interface

* **实际上，接口提供什么和如何提供之间的区别并不明确，因为如何提供接口往往对需要的组件很重要**。In effect, the distinction between what an interface provides and how it provides it is not clear cut because often <em>how</em> the interface is provided matters to the requiring component.
* 因此，安全的组件组成是软件工程中积极研究的一个领域。Consequently, safe component composition is an area of active research in software engineering.

# 架构模式：Architectural Patterns

![](/static/2021-02-09-22-24-56.png)

架构模式（有时称为架构风格），它们显示了架构级问题的可重用解决方案。 architectural patterns, sometimes called architectural styles, that show reusable solutions to architecture level problems

* Model view control
* Client server
* Peer to peer
* Message oriented architecture
* Pipe and filter
* Plugin architecture

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

我们需要一种架构模式来分离这些不同的关注点。这张图说明了模型-视图-控制架构模式，它使用组件图体现了这种方法。**架构模式通常更容易使用组件图**而不是类图来描述，**因为它们代表了系统中长期存在的集合体的安排**，而不是少量类的逻辑组织。该模式主要关注的是以下几个方面的分离：We need an architectural pattern that separates these different concerns. The diagram illustrates the model-view-control architectural pattern which embodies this approach using a component diagram. Architectural patterns are often easier to describe using a component diagram, rather than a class diagram, as they represent the arrangement of long-lived assemblies in a system, rather than the logical organisation of a small number of classes. The pattern is primarily concerned with a separation of concerns between:

* 保存在<code>Store</code>中的数据的内部表示，并以<code>Model</code>界面的形式呈现；the internal representation of data held in a <code>Store</code> and presented as a <code>Model</code> interface;
* 通过<code>View</code>向外部世界展示的关于该（潜在的复杂）数据模型的不同观点；以及the different perspectives on that (potentially complex) data model presented to the outside world via the <code>View</code>;
* 可由<code>Control</code>应用于数据模型的变换 the transformations that can be applied to the data model by the <code>Control</code>

:orange: 请注意，<code>Model</code>接口和<code>UserInterface</code>边界上的端口之间的关联显示了这两个接口。然而，<code>Store</code>对其他组件没有任何依赖性 Notice that both the <code>Model</code> interface, as shown by the associations with the port on the <code>UserInterface</code> boundary. However, the <code>Store</code> has no dependency on the other components.
 
* 这种方法的一个主要好处是，每个组件都有明确的责任，并且可以在不影响其他组件的情况下进行更改。实现<code>View</code>和<code>Control</code>的组件可以在不改变底层数据表示的情况下被改变。同样，<code>Store</code>组件的实现可以独立于<code>UserInterface</code>进行更改，只要替换的<code>Store</code>仍然提供相同的<code>Model</code>接口 A key benefit of this approach is that each of the components has a clear responsibility and can be changed without affecting the other components. The component that realises the <code>View</code> and <code>Control</code> can be altered without changing the underlying data representation. Similarly, the implementation of the <code>Store</code> component can be changed independently of the <code>UserInterface</code>, so long as the replacement <code>Store</code> still provides the same <code>Model</code> interface.

:orange:通常（但不一定），<code>View</code>和<code>Control</code>组件被整理成一个单一的顶层<code>UserInterface</code>组件，如图所示。这反映了一种常见的情况，即使用Swing或GTK等框架或工具包构建用户界面。事实上，MVC 模式在用户界面工具包中非常普遍，以至于通常不需要有意识地考虑以这种方式安排用户和系统之间的交互：框架的设计将为您执行这些约定 Typically (but not necessarily) the <code>View</code> and <code>Control</code> components are collated into a single top level <code>UserInterface</code> component, as shown on the diagram. This reflects a common situation where the user interface is constructed using a framework or toolkit such as Swing or GTK. In fact, the MVC pattern is so prevalent in user-interface toolkits, that it often isn’t necessary to consciously think about arranging the interaction between a user and a system in this way: the design of the framework enforces the conventions for you.

* 这种惯例的一个结果是，<code>View</code>和<code>Control</code>组件通常会<em>耦合在一起。更改 <code>Control</code>也可能意味着更改<code>View</code>中向用户展示模型的方式，反之亦然 One consequence of this convention is that the <code>View</code> and <code>Control</code> components are often <em>coupled</em> together. Changing the <code>Control</code> may also mean changing how some of the model is presented to the user in the <code>View</code>, and vice-versa.

## 修改MVC模式：Adapting the MVC pattern

![](/static/2021-02-09-22-45-48.png)

* **在视图中保留一些模型信息** The retention of some model information in the View
* **视图可以根据模型发出的某项更改的通知进行更新，或者通过视图对模型进行轮询进行更新**。The View can be updated as a result of notifications from the model that something has changed or by the View polling the model
* **视图和控制直接与系统的用户在系统边界进行交互**。The View and Control interact directly with the system’s user at the system boundary.

:orange: 除了系统组织之外，该模式还为如何安排组件的内部实现细节和组件之间的交互留下了相当大的灵活性。例如，选项包括 Beyond system organisation, the pattern leaves considerable flexibility as to how the internal implementation details of components and interactions between components are arranged. Options include, for example:

* **如果模型的某些部分不经常更改，并且反复刷新视图会导致不必要的工作，那么这一点可能会很有用** This can be useful if parts of the model don’t change very often and repeatedly refreshing the view causes unnecessary work
* 投票可以作为用户操作的结果或定期进行。 Polling can occur either as a consequence of user action or periodically
* View和Control直接与系统边界上的系统用户交互。这可能是一个人，也可能是另一个软件系统。交互模式可以是文本、图形、视频等 The <code>View</code> and <code>Control</code> interact directly with the system’s user at the system boundary. This may be a human, or another software system. The mode of interaction could be textual, graphical, video and so on

# 分布式系统模式：Building Distributed System

![](/static/2021-02-09-22-49-20.png)

分布式系统是伴随着计算网络和基础设施的发展而产生的，例如以太网标准和因特网及其前身。这些技术允许将软件系统的不同组成部分部署在实际不同的计算机上，并根据标准协议进行通信。Distributed systems arose alongside the development of computing networks and infrastructures, such as the Ethernet standards and the Internet and its predecessors. These technologies permitted the different components of a software system to be deployed on physically different computers and communicate according to standard protocols.

* **分布式架构模式关注的是，根据影响该系统的特定设计考虑因素，在分布式系统中组织服务**。Distributed architectural patterns are concerned with the organisation of services within a distributed system in accordance with the particular design considerations that affect that system.
* 采用分布式系统的原因有以下几点 There are several reasons that a distributed system might be adopted:
  * **系统提供的服务必须可以从许多不同的地点获得** The services provided by the system must be accessible from many different locations.
  * **建造一台能够管理系统所有信息或计算需求的[物理计算机]是不可行的** It is infeasible to construct a single physical computer capable of managing all the information or computational demands on the system
  * 该系统将由许多不同的软件组件组成，由不同的组织管理和实施。The system is to be composed of many different software components that are managed and implemented by different organisations.

:orange: 我们将研究以下架构模式的动机、结构和实现  motivation, structure and implementation of the following architectural patterns

* 目标 motivations
  * 希望从不同的地点获得服务。Desirable to access services from different locations.
  * 无法在一台电脑上存储和处理数据。Infeasible to store and process data on a single computer.
  * 由来自不同组织的许多不同软件组件组成Composed of many different software components from different organisations
* 模式 patterns
  * 客户机-服务器client-server
  * 点对点peer-to-peer

## 集中化存储服务优点：Benefits of centralising information storage and service provision

![](/static/2021-02-09-22-54-40.png)

**对于分布式软件系统而言，集中对信息和服务的访问**可能是一个很好的设计选择，这有几个原因。这些原因包括：There are several reasons why centralising access to information and services could be a good design choice for a distributed software system. These include:

* **数据的管理和组织可以在一个单一的、一致的结构中进行维护（和控制**）。The management and organising of data can be maintained (and controlled) in a single, consistent structure.
* **避免了确保同一数据项目的多个副本之间的一致性问题**。The problem of ensuring consistency between multiple copies of the same data item is avoided.
* **这种方法为用户提供了一个单一的、全球已知的访问点**。The approach provides a single, globally known, point of access to users.
* **对服务功能变化的管理可以在服务器上进行，而不是在多个客户端上进行**。The management of changes to service functionality is can be undertaken in the server rather than in multiple clients.

---

在应用增加数据凝聚力设计原则和抽象-发生设计模式时，我们也看到了类似的好处：在这两种情况下，我们关注的是减少管理不必要的重复数据或软件的成本 We have seen similar benefits when applying the increase data cohesion design principle and the abstraction–occurrence design pattern: in both cases we were concerned with reducing the costs of managing unnecessary duplication of data or software

# Client-Server Architectural Pattern

![](/static/2021-02-09-22-57-27.png)

**客户机-服务器模式通过将信息和服务的管理整合到一个集中式服务器中**，在架构层面体现了这一理念。该图使用组件图显示了客户端服务器系统的状态快照。The <em>client-server</em> pattern embodies this philosophy at an architectural level by collating the management of information and services into a centralised server. The diagram shows a snapshot of the state of a client server system using a component diagram

* 本系统的职责安排如下 Responsibility in the system is organised as follows:
  * **服务器负责通过通用接口向一个或多个客户端提供对其托管的信息或服务的访问。服务器还负责通过验证客户的更改请求来确保其存储信息的完整性** The server is responsible for providing access to information or services it hosts to one or more clients through a common interface. The server is also responsible for ensuring the integrity of the information it stores by validating change requests from clients.
  * **客户端负责将从服务器收集到的信息呈现给最终用户，并向服务器发出命令。最终用户可以是人或其他软件组件** The client is responsible for presenting information gathered from the server to end-users and for issuing commands to the server. The end-users may be people or other software components

## 协议，会话，API：Protocols，sessions，APIs

让我们仔细看看服务器和客户端之间的通信是如何工作的。

* 客户机和服务器之间的通信由应用级<em>协议</em>的定义来规范，该协议描述了客户机和服务器之间可以传递的合法消息。Communication between clients and servers is regulated by the definition of an application level <em>protocol</em> describing the legal messages that can pass between clients and servers. 
  * 在面向组件的系统中，协议由服务器的应用程序编程接口（API）定义。In a component oriented system, the protocol is defined by the server’s application programming interfaces (APIs).
* 在客户端-服务器系统中，有两个接口。In a client-server system, there are two interfaces. 
  * 第一个接口由客户机使用，以表明它希望与服务器建立新的连接，以便进行通信。The first interface is used by the client to indicate that it wishes to establish a new connection with the server in order to communicate.
  * 服务器输出第二种类型的接口，即主API。该接口指定了客户端和服务器之间建立连接后可以发送的消息。 The server exports a second type of interface, the main API. This interface specifies the messages that can be sent once a connection has been established between client and server.
  * 每次客户端连接到服务器时，都会建立一个单独的<em>session</em>。这种安排允许服务器同时处理来自多个客户端的多个连接。连接的状态（如服务器所感知的那样）由负责该连接的会话来维护 A separate <em>session</em> is established each time a client connects to the server. This arrangement allows the server to handle multiple connections from many clients simultaneously. The state of a connection (as perceived by the server) is maintained by the session responsible for that connection.

:orange:**API由服务器通过端口提供，但实际行为是由嵌套在服务器中的Session组件实现的**。 The API is provided by the server through a port, but the actual behaviour is realised by Session components nested inside the Server.

* **每个Client有一个Session组件，每个组件通过不同端点的端口实现一个API接口**。 There is one Session component per Client, each realising an API interface through a port at a different endpoint.
* **客户端a和b已经建立了连接，并在程序集中与各自的Session组件连接** Clients a and b have already established connections and are wired to their respective Session components in the assembly.
* **InitConnection接口由Server提供并直接实现，用于创建新的Session组件。在所示的组件中，客户端n被连接到InitConnection接口，一个新的Session刚刚建立，准备被n的所需接口消耗**。 The InitConnection interface is provided by and realised directly by the Server and is used to create new Session components. In the assembly shown, Client n is wired to the InitConnection interface and a new Session has just been established ready to be consumed by n’s required interface.

## C-S例子

软件工程中客户机-服务器架构和标准的例子很多，包括万维网、subversion版本控制系统、Skype等即时通讯应用程序、FTP协议和支持实施安全通信的公钥基础设施。There are many examples of the client-server architectures and standards in software engineering, including the world wide web, the subversion version control system, instant messenger applications such as Skype, the FTP protocol and the public key infrastructure that supports the implementation of secure communications.

![](/static/2021-02-09-23-06-06.png)

该图展示了使用客户机-服务器模式的典型电子邮件系统的架构。该模式应用于 EmailClients（例如 Thunderbird 或 Outlook）和 EmailServer 组件之间。服务器实际上向客户机提供了两个独立的服务，每个服务都遵循客户机-服务器模式，并由子组件实现 The diagram illustrates the architecture of a typical email system using the client-server pattern. The pattern is applied between <code>EmailClient</code>s (Thunderbird or Outlook, for example) and <code>EmailServer</code> components. The server actually provides two separate services to the client, with each following the client-server pattern and implemented by sub-components

* Retrieval组件为客户端提供了通过IMAP接口（一种电子邮件检索协议）访问和下载存储在服务器上的电子邮件的功能。 <p>The <code>Retrieval</code> component provides facilities for the client to access and download emails stored on the server via the <code>IMAP</code> interface, a email retrieval protocol.</p>
* 传输组件允许客户端通过SMTP接口（一种电子邮件传输协议）提交电子邮件，以便向其他用户发送。<p>The <code>Transport</code> component allows the client to submit emails for delivery to other users, via the <code>SMTP</code> interface, an email transport protocol.</p>
* 电子邮件系统架构的其余部分对客户端是透明的。远程邮件传输客户端(MTA)提供的一个单独的SMTP接口用于通过中继机制将提交的电子邮件转发给预定的收件人。另一个MTA利用第三个SMTP接口将系统中其他用户的邮件传送到服务器。从这些外部 MTA 的角度来看，电子邮件服务器只是另一个邮件传输客户端 The rest of the email system architecture is transparent to the client. A separate SMTP interface provided by a remote Mail Transfer Client (MTA) is used to forward submitted emails to their intended recipient via a relay mechanism. Another MTA makes use of a third SMTP interface to deliver emails to the server from other users of the system. From the perspective of these external MTAs, the email server is just another mail transport client.
* 还请注意，将这两个独立的功能（检索和交付）组成一个单一的服务器组件是合理的，因为两者都依赖于对内部电子邮件存储组件的访问 Notice also that it makes sense to compose these two separate functions (retrieval and delivery) into a single server component, because both rely on access to the internal email storage component

## Thin vs Fat Clients

![](/static/2021-02-09-23-11-46.png)

瘦客户机架构是客户机服务器模式的经典或纯粹主义者方法。这种安排中的客户端有时被称为dumb客户端或终端，因为它们包含很少的程序逻辑。所有信息和尽可能多的功能都被收集到服务器中。客户机的功能非常有限，只负责向服务器发出指令并显示结果。甚至关于如何向用户展示信息的决定也可能由服务器控制。 Thin client architectures</em> are the classical or <em>purists</em> approach to the client server pattern. The clients in this arrangement are sometimes referred to as <em>dumb</em> clients or terminals, because they contain very little program logic. All information and as much functionality as possible is collected into the server. The client has very limited functionality and is responsible only for issuing instructions to the server and displaying results. Even decisions about how to present information to the user may be controlled by the server.

胖客户端架构采用了相反的方法，将更多的信息管理、验证、处理和存储责任下放给客户端本身。胖客户端架构之所以有用，是因为它们可以通过在客户端中缓存一些信息来减少客户端和服务器之间所需的通信量。此外，一些处理工作被委托给客户端，从而减少了对服务器的计算需求 Fat client architectures</em> take the reverse approach, devolving much more responsibility for information management, validation, processing and storage to the clients themselves. Fat client architectures can be useful because they can reduce the amount of communication required between the client and the server by caching some information in the client. In addition, some of the processing is delegated to the clients, reducing computational demands on the server.

<p>可见，在系统的瘦客户机架构和胖客户机架构之间需要进行权衡。将责任下放给客户机可以减少服务器的负荷，但也有可能在存储数据中引入不一致的风险，并意味着随着软件缺陷的发现，必须更频繁地更新客户机。
<p>随着客户端环境的处理能力的提高，胖客户端架构已变得越来越流行。这使得我们能够开发出更丰富、更互动的客户端用户界面，这些界面通常需要缓存更多信息。

<p>As can be seen, there is a trade-off to be made between a thin or fat client architecture for a system. Devolving responsibility to the clients reduces load on the server, but also risks introducing inconsistencies in stored data and means that the clients have to be updated more frequently as software defects are discovered.</p>
<p>Fat client architectures have become more popular as processing capabilities of client environments have improved. This has enabled the development of richer and more interactive client user interfaces, which often need to cache more information.</p>

## 局限性

![](/static/2021-02-09-23-20-14.png)

<li>服务器和客户端之间的通信可能会出现不可接受的<em>延迟</em></li>。
<li>架构的可扩展性受到服务器可用的物理资源（计算能力、网络带宽、内存）的限制。
<li>架构中的单一参考点（服务器）也是一个单一的故障点。
</ul>
<aside class="note">
<p>客户机-服务器模式有许多限制：</p>客户机-服务器模式有许多限制。
<ul>
<li>这意味着服务器接收命令或客户端接收响应的时间过长。客户机可以通过缓存从服务器接收到的一些信息来缓解这一问题（参见上文关于胖客户机的描述）。
<li>这种限制是不可避免的，因为服务器是所有客户的单一参考点：随着客户数量的增加，对服务器的要求也在增加。这个问题表现在网站很容易被拒绝服务攻击禁用。在最简单的情况下，攻击者会引导尽可能多的客户端同时从某个站点请求相同的网页。
<li>如果服务器被禁用，那么整个系统将停止运行。这个问题可以通过准备后备服务器来缓解，但是，这又会重新引入确保相同数据项或服务的多个副本之间的一致性的问题。
</ul>
<p>因此，我们开发了其他架构模式，以提供更高的可扩展性和冗余度。
</旁白>

# P2P：Peer-to-peer architecture pattern

![](/static/2021-02-09-23-20-50.png)

<p>正如我们所看到的，客户机-服务器架构的一个关键问题是，随着客户机数量的增长，服务器的可用资源难以扩展。胖客户机架构是通过将更多功能转移到客户机中来管理这一问题的一种方法。<em>peer-to-peer</em> 架构模式通过将系统中的所有服务和数据转移到客户机本身中，使每个对等体既是客户机又是服务器，从而扩展了这种方法。
<p>这种架构的一个结果是，托管和管理服务的责任由所有客户共同承担。随着客户数量的增加，对资源的需求也在增加，服务器的可用资源也会相应增加，因为每个对等体都能同时履行这两种角色。
<p>因此，当系统需要负责大量的计算处理或托管大量信息，但又受到硬件物理能力的限制时，点对点架构尤其有用。

<p>As we have seen, a key problem with the client-server architecture is the difficulty of scaling resources available to the server as the number of clients grows. The fat client architecture is one approach to managing this problem by moving more functionality into the clients. The <em>peer-to-peer</em> architectural pattern extends this approach by moving all services and data in the system into the clients themselves so that every peer is both a client and a server.</p>
<p>A consequence of this architecture is that responsibility for hosting and managing services is shared amongst all the clients. As the demand for resources grows due to an increase in clients, there is an equivalent increase in the resources available for servers, because each peer fulfils both roles.</p>
<p>Consequently, a peer-to-peer architecture is particularly useful when a system has responsibility for extensive computational processing, or hosting large amounts of information, but is constrained by the physical capabilities of the hardware.</p>

## 例子：Tor

<p>由于 Knutella 等文件共享应用程序和 BitTorrent 协议的各种实现的扩散，许多人都熟悉点对点应用程序。
<p>第三代洋葱路由（Tor）项目是利用点对点架构模式的另一个系统实例。该系统用于支持用户怀疑被攻击者监控的网络上的参与者之间进行匿名和不可追踪的通信。
<p>与直接通过互联网连接发送的通信不同，Tor 消息是通过 Tor 网络中随机选择的对等节点发送的。消息由发送者包裹在一系列加密层中（因此采用<em>onion</em>路由）。每当消息通过 Tor 节点时，都会移除一层加密，这样节点的观察者就无法匹配接收和发送的消息。
<p>最终，所有的加密层都将被移除，消息将被传递给最终的收件人。随着越来越多的消息通过Tor网络，观察者越来越难以确定消息的收件人是谁。
<p>点对点架构中的一个关键挑战是服务分发和发现：如果服务被分发到任何可用或参与的节点，您如何知道在哪里寻找提供相同服务的其他对等体。此外，如果任何人都可以参与到点对点系统中，那么您如何确定要信任哪些潜在的对等体？

<p>https://www.torproject.org/</p>
<aside class="notes">
<p>Many people are familiar with peer-to-peer applications as a result of the proliferation of file sharing applications such as Knutella and the various implementations of the BitTorrent protocol.</p>
<p>The Third Generation Onion Routing (Tor) project is another example of a system that leverages the peer-to-peer architectural pattern. The system is used to support anonymous and untraceable communication between participants on a network that the user suspects is being monitored by an attacker.</p>
<p>Unlike communications sent directly over an Internet connection, Tor messages are sent via a random selection of peer nodes in the Tor network. Messages are wrapped in a series of layers of encryption (hence <em>onion</em> routing) by a sender. Each time a message passes through a Tor node one layer of encryption is removed so that an observer of the node cannot match incoming and outgoing messages.</p>
<p>Eventually all the layers of encryption are removed and the message is delivered to the final recipient. As more messages pass through a Tor network, it becomes increasingly difficult for an observer to work out who the recipient of the message is.</p>
<p>One key challenge in peer-to-peer architectures is service distribution and discovery: if services are distributed to any available or participating node, how do you know where to look for other peers offering the same service. In addition, if anyone can participate in a peer-to-peer system, how do you determine which of the potential peers to trust?</p>

## Peer Discovery

![](/static/2021-02-09-23-22-10.png)

<ul>
<li>使用全球已知的注册表来记录系统中对等体的地址</li>。
<li>在整个基于同行的网络中进行搜索</li>。
<li>两种模式的混合体</li>。
</ul>
<aside class="note">
<p>为了解决这些问题，可以对点对点模式进行一些调整，包括：</p>。
<p>在这种方法中，对等体首先会查询登记处，以发现其他对等体的可用性。登记处实际上充当了超级对等体，所有其他对等体首先向其查询资源发现。
<p>每个对等体都会维护自己的已知其他对等体的注册表，这些对等体以前曾联系过它以请求资源。当发生这种情况时，请求的对等体也会报告它所拥有的可用资源。当一个对等体执行搜索另一个对等体所持有的资源时，它会向它所知道的所有对等体询问该资源。然后，这些对等体将请求传递给其已知的对等体，直到资源被发现或请求超时。
<p>在这种方法中，部署了多个注册表，每个注册表都包含一个可用对等体的列表。每个登记处都试图通过查询其他登记处来维持一个独立的、最新的可用对等体列表。
<p>所有这些方式都涉及到权衡。使用全局已知的注册表具有客户机-服务器模式的一些局限性：如果注册表不可用，那么没有一个对等体能够发现可能拥有其所需资源的新对等体。另一方面，如果搜索在到达正确的对等体之前就超时，那么基于对等体的搜索就不能保证找到所需资源。混合方法减轻了其他两种方法的缺点，但并没有消除这些缺点，同时还引入了大量额外的架构复杂性。
</旁白>
</节>

<ul>
<li>Using a globally known registry to record the addresses of peers in the system.</li>
<li>Searches across the peer based network.</li>
<li>A hybrid of both patterns.</li>
</ul>
<aside class="notes">
<p>There are several adaptations that can be made to the peer-to-peer pattern to accommodate these problems, including:</p>
<p>In this approach, a peer first queries the registry to discover what other peers are available. The registry effectively acts as a super-peer, to which all other peers refer first for resource discovery.</p>
<p>Each peer maintains its own registry of known other peers that have previously contacted it to request resources. When this happens the requesting peer also reports the resources it has available. When one peer performs a search for resources held by another peer it asks all the peers it knows for the resource. These peers then pass on the request to their known peers until the resource is discovered or the request times out.</p>
<p>In this approach, several registries are deployed, each of which contains a list of available peers. Each registry attempts to maintain an independent and up-to-date list of available peers by querying the other registries.</p>
<p>All of these styles involve trade-offs. The use of a globally known registry shares some of the limitations of the client-server pattern: if the registry becomes unavailable then none of the peers will be able to discover new peers that might have the resources they need. On the other hand, the peer based search has no guarantee of finding the required resources if the search times out before the right peer is reached. The hybrid approach mitigates but does not eliminate the disadvantages of the other two approaches while introducing significant additional architectural complexity.</p>

# 信息处理模式：Information Processing Patterns

![](/static/2021-02-09-23-23-10.png)

<aside class="note">
<p>到目前为止，我们已经研究了系统中责任分配的模式。信息处理模式解决的是系统内高效信息管理的挑战。这可能是为了处理大量数据或支持组件之间的有效协调。我们将研究的模式有

<li>面向消息的架构</li>
<li>管道和过滤器</li>

## 同步通信机制的缺点:Disadvantages of synchronous communication mechanisms

![](/static/2021-02-09-23-24-16.png)

基础通信渠道可能不可靠
在处理开始之前，计算的信息提供者。
按照预先确定的顺序从其他组件中请求的信息
无法利用计算并行来改善响应时间

<li>Underlying communication channels may be unreliable</li>
<li>Information providers for a computation before processing begins.</li>
<li>Information from other components requested in a pre-determined sequence.</li>
<li>Cannot exploit computational parallelism to improve response time.</li>

---

<p>到目前为止，我们忽略了组件之间如何传递消息的问题，而将此细节留给了组件中间件。我们还假设消息传递在很大程度上是<em>异步</em>和<em>瞬时</em>的。有时，这些假设对于组件间的通信来说是不合适的，因为：</p></p>
<ul>
<li>底层通信通道可能不可靠或会出现延迟，从而扰乱组件中的预期计算流。
<li>客户端必须在处理开始之前选择哪些组件将为计算提供信息，因为每次只能提出一个请求。这意味着计算的最短时间通常是所提出的信息请求数量的一个因素</li>。
<li>组件被迫按照预先确定的顺序向其他组件请求信息，因为它们需要依次等待每个组件的响应。然而，一些应用程序可能不需要每个请求的响应，只要获得足够数量的响应</li>。
<li>整个系统设计不能利用计算并行性来改善响应时间。当一个组件在等待将消息传递给另一个组件时，它可能会不必要地处于空闲状态。例如，它可能正在处理从其他组件收到的消息。
</ul>
<p>总之，同步通信会不必要地限制某些类型计算的设计灵活性。例如，考虑一个组件，该组件的开发是为了在给定的一些约束条件（财产价值、财产价值、最低限度的超额部分、购买者是否需要法律保障等等）下，为房屋保险费寻找最佳价格。该组件需要联系大量的保险供应商，并根据客户的规格要求报价。然后，该组件将选择前五名的报价，并将其提交给客户进行审查。
</旁白>

<p>So far we have ignored issues of how messages are passed between components, leaving this detail to the component middleware. We have also assumed that message passing is largely <em>synchronous</em> and <em>instantaneous</em>. Sometimes these assumptions are inappropriate for inter-component communication because:</p>
<ul>
<li>Underlying communication channels may be unreliable or subject to delays that disrupt the expected flow of computation in a component.</li>
<li>The client must choose which components will provide information for a computation before processing begins. because only a single request can be made at a time. This means that the minimum time for the computation is typically a factor of the number of information requests made</li>
<li>Components are forced to request information from other components in a pre-determined sequence because they need to wait for a response from each component in turn. However, some applications may not need responses from every request, provided a sufficient number of responses are obtained</li>
<li>The overall system design cannot exploit computational parallelism to improve response time. A component may be unnecessarily idle while it waits for a message to be delivered to another component. It could be handling messages received from other components, for example.</li>
</ul>
<p>In summary, synchronous communication can unnecessarily constrain design flexibility for certain types of computation. Consider, for example a component developed to search for the best possible price for a home insurance premium, given some constraints (property value, contents value, minimum excess, whether the purchaser wants legal cover and so on). The component needs to contact a large number of insurance providers and request a quotation according to the specifications of the customer. The component will then select the top five quotations and present them to the customer for review.</p>
</aside>

### 同步异步例子：Synchronous vs. asynchronous communication: example

![](/static/2021-02-09-23-25-46.png)

该图说明了如果客户使用同步通信提出请求，客户组件与保险提供商之间的通信。确定最佳报价所需的时间将等于被调查的各个保险提供商所需时间之和。此外，如果任何一个保险商无法提供响应（例如，由于网络故障），则计算时间将额外增加，但没有好处。同步通信还意味着，在第一个报价请求完成之前，该组件无法处理其他报价请求。The diagram illustrates the communication between the client component and the insurance providers if the client makes its requests using synchronous communication. The time taken to identify the best quotation will be equal to the sum of times taken by each of the individual insurance providers polled. In addition, if any of the providers are unable to provide a response (due to network failure, for example) additional time is added to the duration of the computation without benefit. The synchronous communication also means that the component cannot deal with other quotation requests until the first one is completed

![](/static/2021-02-09-23-26-44.png)

另外，如图所示，报价组件可以<em>异步</em>联系每个经纪人。在这种安排中，客户向每个保险提供商发送请求，而无需等待立即回复。一旦所有请求都被发送，报价组件就会等待最少数量的保险提供商回复报价，然后向客户发送初步估算。随着更多报价的出现，可根据需要更新估价。<p>Alternatively, the quotation component could contact each of the brokers <em>asynchronously</em>, as illustrated in the diagram. In this arrangement, the client sends a request to each insurance provider without waiting for an immediate reply. Once all the requests have been sent, the quotation component then waits for a minimum number of insurance providers to respond with a quotation and then sends an initial estimate to the customer. As further quotations arise the estimate can be updated as required.</p>
</aside>

## 面向消息的架构模式：Message oriented architecture pattern

![](/static/2021-02-09-23-27-12.png)

<p><em>面向消息</em>架构（MOA）模式为组件之间的一般异步通信提供了一个基础。在该模式中，所有的通信都以离散消息的形式发生，并通过消息总线进行。总线根据<em>路由策略</em>将消息路由到适当的客户机。
<p>这种模式有两个其他名称，取决于架构中哪些部分对设计问题最重要：</p>。
<ul>
<li><p><em>消息驱动</em>模式，因为组件中的所有内部计算都是接收到来自另一个组件的消息的结果</p></li>。
<li><p><em>消息经纪人</em>模式，因为经纪人负责决定哪个组件接收哪条消息。
</ul>
<p>该图说明了该模式。消息总线被建模为一个单一组件，它为客户机提供了两个接口：</p>。
<ul>
<li><code>IBroker</code>用于通过总线向其他客户机发送消息</li>。
<li><code>IQueue</code>用于从总线上读取其他客户机专门发送给客户机的消息。
</ul>
<p>使用标准接口可确保通过总线发送的所有消息都符合规定的格式。这意味着每个客户机将知道如何解析消息的结构，即使它可能无法解释内容。
<p>该图还显示了<code>MessageBus</code>组件的内部结构。单个客户端的消息存储在<code>Queue</code>中，因此每个客户端都有一个<code>Queue</code>。每个队列实现<code>IQueue</code>接口，由<code>MessageBus</code>提供给相应的客户端。通常情况下，客户端会尽可能快地消耗和处理来自<code>Queue</code>的消息，当它遇到消息的重载时，会使用<code>Queue</code>作为缓冲区。<code>客户端</code>通常也会在处理完其<code>队列</code>中的所有可用消息后进行阻塞。
<p>另外，客户端可以使用<code>IBroker</code>接口发送消息。该接口由<code>Broker</code>子组件实现。当消息到达该接口时，<code>Broker</code>将根据预期的终端客户端决定消息应添加到哪个<code>队列</code>。
<p>例如，如果队列已满，或者消息没有有效的目的地，则中间商可能无法发送消息。在这种情况下，中间商负责处理消息，要么放弃消息，要么通知客户端消息无效（在发件人队列中留下消息）。</p>
</旁白>

<p>The <em>Message Oriented</em> architectural (MOA) pattern provides a basis for general asynchronous communication between components. In the pattern, all communication occurs as discrete messages that pass through a message bus. The bus routes messages to the appropriate client based on a <em>routing policy</em>.</p>
<p>This pattern is known by two other names, depending on which parts of the architecture are most significant for the design problem:</p>
<ul>
<li><p>the <em>Message Driven</em> pattern because all internal computation in a component is the result of receiving a message from another component.</p></li>
<li><p>the <em>Message Broker</em> pattern, because a broker is responsible for deciding which component receives which message.</p></li>
</ul>
<p>The diagram illustrates the pattern. The message bus is modelled as a single component that provides two interfaces to clients:</p>
<ul>
<li><code>IBroker</code> is used to send messages to other clients via the bus.</li>
<li><code>IQueue</code> is used to read messages from the bus that have been sent specifically to the client by other clients.</li>
</ul>
<p>The use of standard interfaces ensures that all messages sent via the bus comply with a prescribed format. This means that every client will know how to parse the structure of a message, even though it may not be able to interpret the content.</p>
<p>The figure also shows the internal structure of the <code>MessageBus</code> component. Messages for individual clients are stored in a <code>Queue</code> such that there is one <code>Queue</code> maintained per client. Each queue realises the <code>IQueue</code> interface provided to the appropriate client by the <code>MessageBus</code>. Typically, the client will consume and process messages from the <code>Queue</code> as quickly as it can, using the <code>Queue</code> as a buffer when it experiences a heavy load of messages. The <code>Client</code> will also normally block once it has processed all available messages in its <code>Queue</code>.</p>
<p>Separately, clients can send messages using the <code>IBroker</code> interface. This interface is realised by the <code>Broker</code> sub-component. When a message arrives at the interface the <code>Broker</code> decides which <code>Queue</code> the message should be added to, normally, based on the intended end client.</p>
<p>The broker may not be able to deliver a message, if a queue is full, for example, or because the message does not have a valid destination. In this situation, the broker is responsible for handling the message, either by dropping the message, or by notifying the client that the message wasn’t valid (by leaving a message in the sender’s queue).</p>
</aside>

### 配置：Configuring message oriented architectures

![](/static/2021-02-09-23-29-09.png)

在通过两种机制影响面向消息的架构的行为方面，有相当大的灵活性。 There is considerable flexibility in influencing the behaviour of a message oriented architecture through two mechanisms

队列和客户端之间的关系。
* 例如，如果添加到队列中的消息数量通常过多，单个客户端无法处理，则可将多个客户端连接到同一队列。另外，一个客户端可以在几个不同的队列上监听消息。例如，客户端可以为正常消息和异常消息分别维护不同的队列。Several clients may be connected to the same queue, for example, if the number of messages added to the queue is typically too much for a single client to handle. Alternatively, a client may listen for messages on several different queues. For example, a client may maintain separate queues for normal and exception messages.
经纪人适用于分发消息的政策。

* 经纪人可以根据不同的因素在不同的队列之间进行选择。例如，经纪人可以根据特定的预期接收者、包含最少消息的队列或消息类型来选择队列 The broker may choose between different queues depending on different factors. The broker might choose the queue based on a specific intended recipient, the queue containing the least messages, or the message type, for example.


<li>The relationship between queues and clients.</li>
<li>The policy applied by the broker to distributing messages.</li>

### 例子

![](/static/2021-02-09-23-31-33.png)

让我们考虑一下该模式的应用实例：一个大型连锁超市的库存控制系统。该图说明了库存控制系统所管理的不同地理位置之间的库存流动情况。该连锁超市拥有大量的商店，每家商店的存货能力都是有限的。
<p>该图展示了面向消息的库存控制系统的架构。该架构不会让人感到太过惊讶：每一个组件，无论它是处理商店、配送中心还是供应商功能。
<p>该连锁店设有若干区域配送中心，代表所在地区的商店持有大量库存。当某一特定商品的存货量下降到某一水平以下时，商店管理层就会要求配送中心提供额外的供应。
<p>配送中心直接从供应商处接收库存。在某些情况下，存货无法从供应商直接送到适当的配送中心。在这种情况下，将向能够从现有供应商处接收存货的配送中心索取存货

<p>Let’s consider an example application of the pattern: a stock control system for a large supermarket chain. The diagram illustrates the flow of stock between the different geographical locations managed by the stock control system. The supermarket chain has a large number of stores, each with a limited capacity for holding stock.</p>
<p>The diagram illustrates the architecture of the message oriented stock control system. The architecture shouldn’t come as too much of a surprise: every component, regardless of whether it is handling store, distribution centre or supplier functionality.</p>
<p>The chain maintains a number of regional distribution centres for holding larger amounts of stock on behalf of the stores in their area. When the stock of some particular item drops below a certain level in store, the store management requests additional supplies from the distribution centre.</p>
<p>The distribution centres receive stock directly from suppliers. In some situations stock cannot be sent directly from a supplier to the appropriate distribution centre. In this case, stock is requested from the distribution centre that can receive stock from the available supplier.</p>
</aside>
</section>

### 采用面向消息的架构模式的库存控制系统：Stock control system using the message oriented architecture pattern

![](/static/2021-02-09-23-38-06.png)

<p>The diagram illustrates the architecture of the message oriented stock control system. The architecture shouldn’t come as too much of a surprise: every component, regardless of whether it is handling store, distribution centre or supplier functionality.</p>
<p>Each component has it’s own queue of messages maintained by the <code>MessageBus</code>. In addition, each component can send a message to any other component via the broker. In practice, only the following messages are required:</p>
<p>The system ensures that stock is delivered to stores through message passing:</p>
<ul>
<li><em>Store to RDC</em> requests for new supplies.</li>
<li><em>RDCs to Suppliers</em> requests for new stock to be manufactured and delivered.</li>
<li><em>Suppliers to RDCs</em> notifications when the requested products will be delivered.</li>
<li><em>Between RDCs</em> requesting stock to be transferred as available and notifications when that stock will be delivered.</li>
</ul>

### 优点

![](/static/2021-02-09-23-38-21.png)

<h1>Advantages of the message-oriented architecture</h1>
<ul>
<li>Communications security is centralised by ensuring that all messages pass through secured channels on the message bus.</li>
<li>The broker can be configured dynamically to route messages according to the state of the application.</li>
<li>The message bus provides a central location for monitoring inter-component communication.</li>
</ul>
<aside class="notes">
<p>There are several further advantages to using a centralised message bus to mediate communications:</p>
<ul>
<li>[BULLET 1]</li>
<li>[BULLET 2]<br />
</li>
<li>[BULLET 3] This is useful for logging interactions between components for debugging or auditing, for example. The structure also makes it easier to identify bottle necks or faulty components, since this is typically indicated by congested queues.</li>
</ul>
</aside>

# 顺序数据处理应用：Sequential data processing applications

![](/static/2021-02-09-23-38-58.png)

<h1>Sequential data processing applications</h1>
<ul>
<li>Video and audio media transcoding, where video is decoded, potentially re-formatted (size, colour model etc.) and then encoded.</li>
<li>Textual analysis, where prose is checked for spelling and grammar errors in one language before being translated into another.</li>
<li>Inter-bank payment processing, where very large numbers of small scale currency transfers must undergo a series of validations before being approved.</li>
<li>Processing and synthesising data gathered from scientific instruments.</li>
</ul>
<aside class="notes">
<p>Many software applications are required to process large volumes of data, applying a number of transformations between input and output. Examples include:</p>
<p>[BULLET 4] Raw instrument data often needs considerable re-processing and integration before it can be used in analyses. Weather and climate data (temperature, pressure, humidity and so on), for example, is gathered from a variety of sources and using a heterogeneous range of instruments.</p>
</aside>
</section>