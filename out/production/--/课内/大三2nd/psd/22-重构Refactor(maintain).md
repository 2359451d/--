# Software Refactoring & 维护

重构是对软件系统进行修改的过程，以使其更容易理解和更便宜地修改，而不改变其可观察的行为。 Refactoring is the process of making changes to a software system to make it easier to understand and cheaper to modify without changing its observable behaviour.

* [Software Refactoring & 维护](#software-refactoring--维护)
* [维护良好的源码/项目维护WHY & 定义：Maintaining source code and system design](#维护良好的源码项目维护why--定义maintaining-source-code-and-system-design)
* [软件重构：software refactoring](#软件重构software-refactoring)
* [重构过程：The refactoring process](#重构过程the-refactoring-process)
* [何时重构 & 出现异味->可以重构：When to refactor](#何时重构--出现异味-可以重构when-to-refactor)
* [异味：Fowler’s ‘Bad smells](#异味fowlers-bad-smells)
* [过多注释：Excessive comments vs. self documenting code](#过多注释excessive-comments-vs-self-documenting-code)
* [重构类型：Types of refactorings](#重构类型types-of-refactorings)
* [重构解决方法：Smells to refactoring](#重构解决方法smells-to-refactoring)
* [重构例子-Book](#重构例子-book)
  * [Book.getHarvardReference()](#bookgetharvardreference)
  * [构建重构测试例子：BookTest](#构建重构测试例子booktest)
  * [重构计划：Refactoring plan](#重构计划refactoring-plan)
* [自动化重构：Automated refactoring](#自动化重构automated-refactoring)
* [重构的局限性：Limits of refactoring](#重构的局限性limits-of-refactoring)
* [重构改变API：Refactorings that alter APIs](#重构改变apirefactorings-that-alter-apis)
* [Key](#key)

# 维护良好的源码/项目维护WHY & 定义：Maintaining source code and system design

**如果源代码得到了精心的维护，那么软件就更容易被改变以应对系统环境的变化**。（重构前可能就有很好的可维护性）

:orange: 维护良好的软件源代码的特点包括: 

* **有效使用版本控制系统**（变更管理 & 9CI的变更管理需求） effective use of a version control system;
* **自动化的测试套件**（9CI， 测试套件评估等？） an automated test suite;
* **清晰的架构和设计**（20-21架构） a clear architecture and design;
* **简单和一致的编码风格，在可能的情况下通过自动化来执行** （10静态分析，代码风格）a simple and consistent coding style, enforced through automation where possible;
* **适当使用注释和其他文档** （10代码风格）appropriate use of comments and other documentation

# 软件重构：software refactoring

随着软件系统的发展，维护其结构的一个关键学科是重构。在软件开发项目中，**重构应该是一个持续的过程，与新功能的引入同时进行**。Fowler对重构的定义是 A key discipline for maintaining the structure of a software system as it evolves is <em>refactoring</em>. Refactoring should be an-going process in a software development project, alongside the introduction of new features. Fowler defines <em>a</em> refactoring as:

> 重构（名词）：对软件的内部结构所做的改变，以使其更容易理解和更便宜地修改，而不改变其可观察的行为 Refactoring (noun): a change made to the internal structure of software to make it easier to understand and cheaper to modify without changing its observable behaviour.

并继续将动词形式（重构）定义为**一系列重构的应用**。这是一个有用的标准定义，但可以改进为"......**同时尽量减少功能行为的变化**"（强调是后加的） and goes on to define the verb form (to refactor) as the application of a series of refactorings. This is a useful standard definition, but could be improved to say ``..while minimising changes functional behaviour’’ (emphasis added).

:orange: 正如我们将看到的，**重构通常会导致系统的非功能属性发生变化，实际上也会导致功能行为或规范的变化** As we will see, refactoring often causes changes in the non-functional properties of a system, and indeed, can also causes changes in the functional behaviour or specification.

# 重构过程：The refactoring process

重构可以被认为是**源代码清理的一种形式，并增加了一个定义明确的执行变更的过程**。Refactoring can be thought of as a form of source code cleanup, with the addition of a well defined process for performing the change.

这张图说明了重构的过程。

![](/static/2021-04-26-16-01-28.png)

* **这个过程从【识别重构机会】开始**。The process begins with the identification of opportunities to refactor.
  * Fowler称这些**重构机会指标**为程序源代码中的 "**异味**"。 Fowler calls indicators of these opportunities ‘bad smells’ in the program source code.
  * 异味 "是源代码中的一种模式，<font color="red">表明结构不良或模块之间不适当的耦合</font> A ‘bad smell’ is a pattern in the source code indicating poor structure or inappropriate coupling between modules.
* **一旦确定了重构的机会，就必须为受影响的类创建测试，使用自动测试线束框架，如JUnit** Once an opportunity to refactor is identified, it is important to create tests for the affected classes, using an automated test harness framework, such as JUnit.
  * **测试案例非常重要**，因为它们将被<font color="deeppink">用来证明重构后的代码的功能行为并没有因为应用任何重构而发生改变</font>  The test cases are important because they will be used to show that the functional behaviour of the refactored code has not changed as a result of applying any refactorings.
  * 当然，在维护良好的代码中，一个类或包将已经有一套完善的测试(重构前可能已经有了，CI集成/BDD需要的)，可用于此目的 Of course, in well maintained code, a class or package will already have a well established suite of tests that can be used for this purpose.
* **根据不良气味所提供的指导，对重构进行规划** Next the refactoring is planned, based on the guidance provided by the bad smells.
* 一旦**确定了修订后的设计，就可以进行应用**， Once the revised design has been established, it can be applied,
  * 通常**使用自动重构工具**，该工具作为许多集成开发环境（如 Eclipse）的一部分提供  often using automatic refactoring tools that are provided as part of many Integrated Development Environments (IDEs) such as Eclipse.
* **重新运行之前的测试** At this point, the tests that were developed previously should be rerun.
  * 如果**测试成功**，那么就应该将新的设计和源代码提交给主项目。 If the tests are successful, then it is appropriate to commit the new design and source code to the main project.
    * 这可能涉及将变化合并到项目源代码库的主要开发干线中（变更管理，使用VCS）。 This may involve merging the change into the main development trunk othe project source code repository.
  * 如果一个或多个**测试失败**，这表明<font color="red">重构已经改变了被改变的类的功能行为</font>。If one or more of the tests fail, this indicates that the refactoring(s) have altered the functional behaviour of the altered class.
    * 这里有必要退出（逆转）这些改变。 It is necessary here to back out of (reverse) the changes.
    * 然后可以进行**额外的计划和分析，以了解重构所带来的意外影响**。 Additional planning and analysis can then take place to understand what unexpected effect the refactoring(s) had
    * 一旦滑移被确定，就可以应用修改后的重构。这个过程持续迭代，**直到重构后的系统通过预先准备的测试** Once the slip has been established, the revised refactoring(s) can be applied. This process continues iteratively, until a refactored system passes the pre-prepared tests.

:orange: **在完成了重构之后，可能还会发现改进设计的新机会**。 Having completed the refactoring, new opportunities for improving the design may also be identified.

* 例如，如果提取的方法被应用于具有很大方法体的方法，那么将该新方法移到另一个类中也可能是合适的 For example, if extract method is applied to a method with a large body, it may then also be appropriate to move that new method to another class.

# 何时重构 & 出现异味->可以重构：When to refactor

**重构过程的第一步是确定重构的机会**。 The first step in the refactoring process is to identify opportunities for refactoring.

:orange: 重构应被视为一个并行的持续过程，**它与其他软件开发活动一起发生**。这意味着你应该在以下情况下寻找重构的机会 Refactoring should be treated as a parallel on-going process, that occurs alongside other software development activities. This means that you should be looking for opportunities to refactor when you are:

* **实现新功能** implementing new functionality;
* **纠正一个缺陷** correcting a defect;
* **进行代码审查** doing a code review; 
* **最重要 --- 当您试图了解一个软件工件是如何工作的时候** and (most importantly) when you are trying to understand how a software artifact works

:orange: 当你执行以上步骤时，你也应该注意**观察源代码中不良软件设计的例子**。 While you undertake these tasks, you should also be observant for examples of poor software design in the source code.

* Fowler把这些称为异味。 Fowler refers to these as bad smells.
* <font color="red">这些线索表明，源代码可以以某种方式加以改进</font> These are clues that the source code could be improved in some fashion.

# 异味：Fowler’s ‘Bad smells

:orange: **重构关注的是降低软件应用中的耦合度和增加内聚力** Unsurprisingly, refactoring is concerned with reducing the coupling and increasing the cohesion in a software application.

我们现在将对Fowler的代码异味进行总结。原书中没有按类别对气味进行分组，但在回忆不同的异味来源时可以起到作用。因此，异味可能来自

* **重复的代码** cloning - duplicate code
  * 被克隆而不是被重用的源代码会**增加维护难度**。 duplicate code Source code that has been cloned rather than reused makes maintenance harder.
  * 如果你在原始代码中发现一个错误，你也必须修复所有的克隆代码 If you find a bug in the original, you have to fix all the clones as well
* **复杂结构** complex structures
  * **长方法** long method
    * 长的指令序列的行为更难理解，因为所有的实现细节都一次性呈现在读者面前。一般来说，**在编辑器中几行都读不懂的方法就太长了** The behaviour of long sequences of instructions is harder to understand, because all of the details of the implementation are presented to the reader at once. In general, methods that can’t be read on a few lines in an editor are too long.
  * **过大类** large class
    * 表示太多的责任被分配给一个类。也有可能是大类包含了重复的代码 large class Indicates that too many responsibilities have been allocated to a single class. It is also possible that the large class contains duplicate code.
* **变量&参数** variables and parameters
  * **过长参数列表** long parameter list
    * 长参数列表给方法的设计带来了印章耦合，**因为每次参数列表的改变，对方法的每次调用也必须改变**。 long parameter list Imposes stamp coupling on the design of the method, because each time the parameter list changes, every call of the method must also be changed.
    * 长参数列表还表明，一个方法的使用方式不同，这取决于它的调用方式 A long parameter list also indicates that a method that is used in different ways, depending on how it is called.
  * **依恋情结** feature envy
    * 表示**一个方法在错误的类中，因为它从另一个类中获得大部分数据**。 feature envy Indicates that a method is in the wrong class, because it obtains most of its data from another class.
      * 某个函数为了计算某个值，从另一个对象那儿调用很多的取值函数
      * **数据本身和对数据的操作没有包装在同一个类中，则形成依恋情结**
    * 如果数据或访问数据的方法改变了，那么另一个也会改变 If either the data or the accessing method change, then so will the other.
  * **数据泥团** data clumps
    * **当相同的、独立来源的数据在系统中的不同位置被一起使用时**，就会出现。data clumps Occurs when the same, independently sourced data is used together in different locations in a system. 
      * **两个类中相同的字段，许多函数签名中相同的参数**
      * **一个需求变化会引发多个类进行相应修改，则为散弹式修改**
      * 多个类中重复出现的字段，或多个函数(方法)中相同的入参。
      * 与基本类别偏执类似，但涉及·几个items
      * ![](/static/2021-04-26-22-42-50.png)
      * **如果您想确定某些数据是否是一个数据簇，只需删除其中一个数据值，然后看看其他值是否仍然有意义。如果不是这样的话，这是一个好的迹象，表明这组变量应该组合成一个对象**
    * 这可以从方法的重复参数列表或重复的查询方法调用块中识别出来。这两个例子也是克隆的指标 This might be identifiable from duplicate parameter lists for methods, or from duplicate blocks of query method calls. Both of these examples are also indicators of cloning
    * 两个classes内的相同字段、许多方法签名式中的相同参数。这些[总是绑在一起出现的数据]真应该放进属于它们自己的对象中
  * **基本型别偏执** Primitive Obsession
    * 这是有问题的，因为当**数据类型被表示为原始值时，它的语义和补充属性是不明确的**，必须独立于数据来维护。 This is problematic because the semantics and supplementary properties of a data type is not explicit when it is represented as a primitive value, and has to be maintained independently of the data.
      * **刚接触面向对象时大部分情况都不愿意使用小对象去处理运用场景**
    * 例如，公历中的一个日期可以用三个整数来表示（日、月、年各一个）。然而，这意味着这些值之间的关系必须在系统的其他地方得到维护。 A date in the Gregorian calendar could be represented by three integers, for example (one each for day, month and year). However, this means the relationships between these values has to be maintained elsewhere in the system.
      * 一些基本类型的数值存在一定关联
  * **令人迷惑的临时字段** temporary field
    * 这些是**在方法体中作为变量使用的字段，但并不总是需要**。 These are fields that are used as variables in method bodies, but aren’t always needed.
    * 字段只应该被用来记录关于一个对象的状态的信息，这些信息必须在方法调用之间持续存在 Fields should only be used to record information about an object’s state that must persist between method calls.
* **修改** making changes
  * **发散式变化** divergent change
    * 另一个指标表明**一个类有太多的责任**，当**一个单一的类必须以不同的方式改变以应对系统环境的不同变化时**，可以识别。 Another indicator that a class has too many responsibilities, identifiable when a single class must be altered in different ways to respond to different changes in the system’s environment.
      * **如果在想修改代码逻辑时无法成功定位到具体某一点，则存在发散式变化的问题**
    * 这表明该类有两套或多套不同的责任 This suggests that the class has two or more different sets of responsibilities.
    * **由于代码的各种修改或扩展，每次都要修改某个类**
    * 在代码的不断更迭中，多注意看哪些类经常会因为更迭而修改 
      * **把这些经常变化的类独立出来，提取成单一类，专门负责此种类型的修改,考虑使用 Extract Class(提取类)，降低不同的代码修改和扩展时造成同一个类不断的被修改**
  * **霰弹式修改** shotgun surgery
    * 与发散性变化相反，当**环境的变化**需要在系统中的**几个不同的类中进行一些不同的变化时**，可以识别。 The opposite of divergent change, identifiable when a change in the environment necessitates a number of different changes in several different classes in a system.
      * **一个需求变化会引发多个类进行相应修改，则为散弹式修改**
    * 这使得维护工作更加耗时，也更容易出错 This makes maintenance more time consuming, and easier to get wrong
  * **平行继承体系** parallel inheritance hierarchies
    * 通过**两个继承层次之间的依赖关系**来识别，这样，**一个层次的变化会导致另一个层次的变化**，增加了耦合性和维护成本 parallel inheritance hierarchies Identifiable by dependencies between two inheritance hierarchies, such that a change in one hierarchy causes a change in another, increasing coupling and maintenance costs.
    * 平行继承体系其实是散弹式修改（Shotgun Surgery）的特殊情况。在这种情况下，**每当你为某个类增加1个子类，必须也为另一个类相应增加1个子类**。**如果你发现某个继承体系的类名前缀和另一个继承体系的类名前缀完全相同**，便是闻到了这种坏味道。
* **控制结构 & 多态** control structures and polymorphism
  * **switch惊悚现身** Switch Statements
    * **在面向对象中应该尽量少使用 switch 结构，从本质上来说，switch 结构的弊端在于重复，而多态是 switch 结构的优雅解决方式**
    *  表示**不愿意利用面向对象的多态性，并增加了维护成本**，switch statements Indicates a reluctance to exploit object oriented polymorphism and increases maintenance costs,
    *  因为**每次在系统中引入新的选项时，必须在Switch中加入一个标志和额外的行** because each time a new option is introduced into the system, a flag and additional line must be added to the switch.
  * **被拒绝的遗赠** Refused Bequest
    * 有时子类不需要超类提供的所有操作。这意味着**超类的所有方法都被不必要地耦合到子类中** Sometimes a sub-class doesn’t need all of the operations provided by a super class. This means that all the methods of the super class are unnecessarily coupled to the sub-class.
    * 子类复用了父类的实现，却不愿支持父类的接口
  * **异曲同工的类** alternative classes with different interfaces
    * **如果两个函数做同一件事，却有着不同的名字**
    * 具有不同接口的替代类 这是一种 "气味"，因为它意味着**无法利用面向对象的多态性**的好处。alternative classes with different interfaces This is a `smell’ because it means that the benefits of object-oriented polymorphism cannot be exploited
    * 这两个或更多的类必须在代码中手动选择使用 The two or more classes must be manually selected for use in the code.
* **设计不确定性** design uncertainty
  * **冗余类** lazy class
    * 这可能是一种难以识别的气味，因为许多气味是关于做得太多的类。然而，懒惰的类，如果没有足够的独立责任，维护起来就会有不必要的代价 This can be a difficult smell to identify, given that many of the smells are about classes that do too much}. However, lazy classes, that have insufficient independent responsibilities can be unnecessarily expensive to maintain.
    * **某个类的价值低于为他花费的维护成本，则形成冗余类**
      * 某些类没有起到足够的作用
      * 某些组件完全失去作用
  * **夸大未来性** speculative generality
    * 尽管对具体的实现细节进行抽象通常是一个好主意，但试图将所有的设计决策转移到软件系统的配置中是很诱人的。在最极端的情况下，这种现象被称为<em>内部平台</em>反模式，因为系统被设计成一个必须配置的软件平台，在现有的软件平台之上 Although abstracting over concrete implementation details is generally a good idea, it can be tempting to attempt to move all design decisions into the configuration for a software system. At its most extreme, this phenomenon is known as the <em>inner platform</em> anti-pattern, because the system is designed as a software platform that must be configured, on top of an existing software platform.
    * **提前准备某些类，却一直未使用过**
      * 某个抽象类基本没什么作用
      * 一些不必要的调用关系
      * 方法的某些参数未被使用
      * 方法的名称带有多余的解释意义
  * **不完美的类库** incomplete library class
    * 这与其说是一种气味，不如说是其他气味的潜在原因。**当发现一个库中的类不支持客户端所需的所有操作时，就会出现这种情况**。 This is less of a smell, than a potential causes of other smells. It occurs when it is discovered that a class from a library doesn’t support all the operations needed by the client.
    * 此外，**该库类不能被改变**，因为它被用于许多其他项目 In addition, the library class can’t be altered, because it is used in many other projects.
    * **类库构造的不够完善**
* **delegation**
  * **高耦合消息链** Message Chains
    * 当一个消息**一对象通过一系列中介对象访问一个数据项**时发生。因此，请求对象与链中的所有其他对象绑定 Occurs when a message one object accesses a data item through a series of intermediary objects. The requesting object is therefore bound to all the other objects in the chain.
    * **对象之间的请求路径过长，就容易导致高耦合消息链的出现**
  * **中间人** Middle Man
    * **当一个对象作为另一个对象的信息中介，但实际上自己不提供任何信息时**，就会出现这种情况。，Occurs when one object acts as an information broker to another object, but doesn’t actually provide any information itself.
    * 这可能有很好的理由,例如，如果中介是一个代理。然而，如果该对象**只是在传递信息，那么直接进行通信可能会更好**  There may be good reasons for this, if the broker is a proxy, for example. If the object is just passing on information, however, it may be better for the communication to occur directly.
    * **过度使用委托，导致存在多余的中间类**
  * **狎昵关系** inappropriate intimacy
    * 也被称为对象-狂欢反模式。这种味道与通过中间人或链子获取信息相反。相反，所有的对象都与其他对象的属性**自由交互，造成它们之间的耦合** "orgy" Also known as the object-orgy anti-pattern. This smell is the opposite of accessing information via middlemen or chains. Instead, all objects freely interact with the properties of other objects, causing an `orgy’ of couplings between them.
    * **两个类之前过于亲密就容易导致不适当的狎昵关系**
      * 两个类之间相互调用过多方法
      * 继承往往是造成狎昵关系的主要原因之一
    * 这种关系就是说两个类的关系过于亲密，对于这种问题。就应该**提取公共部分形成新类，然后让他们各司其职，在共性的部分直接使用新类**
      * **父类提供的强大功能如果超出了子类所需的程度就应该将其独立出来**，而不是继续存在与继承体系中。为何要提取出来的原因还是继承冗余导致类直接出现过度亲密的问题
  * **幼稚的数据类** data class
    * 类通常应该有**行为责任以及数据项**。Classes should generally have behavioural responsibilities as well as data items.
    * 如果一个类**缺乏行为**，这往往是因为该行为已经在其他地方实现了 If a class lacks behaviour, this is often because the behaviour has been implemented somewhere else.
    * **某些类拥有一些字段，以及访问/读写这些字段的方法，除此之外没有其他用处，则称为数据类**

# 过多注释：Excessive comments vs. self documenting code

为了解释某段代码的意义，而编写了过多的注释，这反而从另一方面说明这段代码需要被重构

Fowler所描述的最后一种不良气味是**过度使用注释来解释不必要的复杂源代码**。这些注释表明结构不佳，可以通过应用重构来改善。 A final bad smell described by Fowler is the excessive use of comments to explain unnecessarily complex source code. The comments are indicative of poor structure that can be improved through the application of refactoring.

* 这个过程有助于创建自我记录的代码。 This process helps to create self documenting code.
* **一旦代码被重构**，其目的和功能就会变得更加不言而喻，从而**减少对补充文档的需求**。 Once the code has been refactored, its purpose and functionality become much more self-evident, reducing the need for supplementary documentation.
* 当重构过程完成后，这些过多的文档就可以基本删除 This excessive documentation can be largely removed when the refactoring process is complete.

，由于当时可用的编程语言的局限性，这些文档是必要的。然而，过多的文档使得理解程序流程更加困难，而不是更加容易

# 重构类型：Types of refactorings

一旦确定了 "异味"，就有必要确定一个**合适的重构**来补救这个问题。有许多不同类别的重构 Once a `bad smell’ has been identified, it is necessary to identify a suitable refactoring to remedy the problem. There are a number of different categories of refactoring.

:orange: Fixing Methods

* 提取方法，内联函数 extract method ←→ inline method
  * 提炼方法 - 将这段代码放进一个独立方法中，并让方法名称解释该方法的用途
* 用方法对象替换方法 replace method with method object

:orange: moving functionality

* 移动方法，移动字段 move method, move field
* 提取类，内连类 extract class ←→ inline class

:orange: Organising data

* 封装字段 encapsulate field
* 对象替换数据值 replace data value with object
* 以符号常量/字面常量取代魔法数 Replace Magic Number with Symbolic Constant

:orange: simplifying method calls

* 移除参数 parameterise method ←→remove parameter
* 引用参数对象 use parameter object

:orange: simplifying conditions

* 分解条件表达式 decompose conditional
* 合并重复的条件片段 consolidate duplicate conditional fragments
* 使用多态代替条件判断 replace conditional with polymorphism

:orange: reorganising classes

* 上移方法，下移方法 pull up method ←→ push down method
  * 上移 - 将方法移动到父类中
  * 下移 - 将方法移动到相关的子类中
* 上移字段，下移字段 pull up field ←→ push down field
  * 上移 - 将字段移动到父类中
  * 下移 - 将字段移动到相关的子类中
* 提取父类 extract superclass
* 提取子类 extract subclass
* 合并继承 collapse hierarchy

# 重构解决方法：Smells to refactoring

异味和建议的补救措施 the bad smells described by Fowler and the proposed remedies

![](/static/2021-04-26-19-38-26.png)

![](/static/2021-04-26-21-32-29.png)

![](/static/2021-04-26-21-32-36.png)

# 重构例子-Book

我们现在将考虑应用重构过程来处理一个图书类。该图展示了该类的部分类图，涉及书籍和作者之间的关系 We will now consider applying the refactoring process a book class. The diagram illustrates a partial class diagram for the class, concerning the relationship between books and their authors.

![](/static/2021-04-26-21-36-28.png)

## Book.getHarvardReference()

```java
public class Book {

    private List<Author> authors = new ArrayList<Author>();
    private int yearPublished;
    private String monthPublished;
    private String title;
    private String publisher; 
    private String publisherAddress;
        
    public String getHarvardReference(){
        String result = "";
        
        for (Author author: authors){
            String surname = author.getSurname();
            result+=surname + ", ";

            List<String> forenames = author.getForenames();
        
            for (String forename: forenames)
                result += forename.charAt(0)+".,";
        }
        result+=" ("+monthPublished+", "+yearPublished+") ";
        
        result+=title+". ";
        
        result+=publisherAddress+": "+publisher+".";
        return result;
    }
    
    public Book(List<Author> authors, int yearPublished, String monthPublished,
            String title, String publisher, String publisherAddress) {
        this.authors.addAll(authors);
        this.yearPublished = yearPublished;
        this.monthPublished = monthPublished;
        this.title = title;
        this.publisher = publisher;
        this.publisherAddress = publisherAddress;
    }   
}
```

<code>Book</code>类中的<code>getHarvardReference（）</code>方法。该方法的目的是以哈佛引用风格来格式化书籍的细节

查看该方法，我们可以发现异味例子

* **过长方法** - 包含混合查询，处理，字符串组成的混合逻辑
* **基本型别偏执** - `yearPublished` & `monthPulished`属性，，
* **数据团积** - 因为出版商的名字和地址很可能会被大量使用。 publisher’s name and address 

## 构建重构测试例子：BookTest

```java
public class BookTest {

    private Book book;
    
    @Before
    public void setUp() throws Exception {
        List<String> forenames = new ArrayList<String>();
        forenames.add("Ian");
        Author author = 
            new Author("Sommerville",forenames,Title.PROFESSOR);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author);
        
        book = new Book(authors,2008,"September",
                "Software Engineering",
                "Addison Wesley",
                "London");
        
    }

    @Test
    public void testGetHarvardReference() {
        Assert.assertEquals(
                "Sommerville, I., (September, 2008) Software Engineering."+
                " London: Addison Wesley.",
                book.getHarvardReference());
    }
}
```

BookTest</code>类的代码，其中包含一个测试<code>getHarvardReference()</code>的方法，代码中显示

## 重构计划：Refactoring plan

![](/static/2021-04-26-21-52-22.png)

**提取方法 - 格式化 author name， 将方法移至result方法中** Apply extract method to the code for formatting the author’s name and move method to the resulting method.

**对象替换数据值，，替换 yearPublished & monthPulished属性，解决基本型别偏执**Apply replace data value with object to the combined yearPublished and monthPublished attributes, to deal with the primitive obsession smell.

**book的publisher属性进行类提取** Apply extract class to the attributes concerning the book publisher.

```java
public class Book {

    private List<Author> authors = new ArrayList<Author>();
    private Date published;
    private String title;
    private Publisher publisher;
    
    public String getHarvardReference(){
        String authors = getHarvardFormattedAuthors();

        String publisherDetails =
            publisher.getHarvardFormatted();
        
        String datePublished = getDatePublished();
        
        String harvardFormat = "%s (%s) %s. %s";
        
        String result = 
            String.format(
                harvardFormat,authors,datePublished,
                title,publisherDetails);    
        
        return result;
    }

    private String getDatePublished() {     
        DateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");
        String result = dateFormat.format(published);
        return result;
    }
    
    private String getHarvardFormattedAuthors(){
        String result = "";
        for (Author author: authors)
            result += author.getHarvardFormatted();
        return result;
    }
        
    public Book(List<Author> authors, Date published,
            String title, Publisher publisher) {
        this.authors.addAll(authors);
        this.published = published;
        this.title = title;
        this.publisher = publisher;
    }
}
```

# 自动化重构：Automated refactoring

许多软件工具，例如 IDE，都有对自动重构的集成工具支持。例如，Eclipse支持（上下文）自动的操作 Many software tools, such as IDEs have integrated tool support for automatic refactoring. Eclipse, for example, supports (contextualised) operations for automatically:

![](/static/2021-04-26-21-56-15.png)

# 重构的局限性：Limits of refactoring

**重构会影响系统的非功能属性或API**，所以在进行重构时需要注意不要意外地改变这些，因为你可以改变你认为是系统内部的部分，但实际上它是API的重要部分。改变API的重构的例子包括。 Refactoring can affect the non-functional properties or the API of the system, so you need to be careful that you don’t accidentally change these when performing your refactoring, as you can change what you think is an internal part of the system but it is actually an important part of the API. Examples of refactors that change the API include:

* 移动或重命名一个类成员，或改变其可见性 Moving or renaming a class member, or changing its visibility
* 改变一个操作的参数列表 Changing the list of parameters to an operation
* 改变一个操作的返回类型 Changing the return type of an operation
* 改变可能引发的异常情况 Changing the exceptions that may be raised

---

重构过程是一个强大的机制，可以在软件应用程序发展的过程中管理设计质量。**然而，重构本身也会导致软件的发展，因此可以也确实改变了软件系统的可观察行为**。重构可以导致软件系统在以下方面发生可观察到的变化 To summarise the previous section, the refactoring process is a powerful mechanism for managing design quality as a software application evolves. However, refactorings can themselves cause the software evolve and consequently can and do alter the observable behaviour of software systems. Refactorings can cause observable changes to a software system in either the:

* **非功能属性** non-functional properties; or
* 或，**系统的应用编程接口**（API） the application programming interface (API) of a system.

:orange: 导致**非功能属性变化的重构可能是有益的，也可能是有害的** Refactorings which causes changes to non-functional properties may be either beneficial or detrimental. 

* 重构可能会减少系统的整体内存占用，同时减少系统的响应时间 A refactoring might decrease a system’s overall memory footprint, whilst simultaneously reducing the system’s response time.
  * 这可能是因为重构减少了软件系统中的克隆量 This may happen because the refactoring reduces the amount of cloning in a software system.
* 不幸的是，这可能会增加必须进行的方法调用的数量，增加事务的执行时间  Unfortunately, this may increase the number of method calls that must be made, increasing execution time for a transaction.
* 这些考虑对于计算资源可能有限的移动、嵌入式或实时应用尤为重要 These considerations are particularly important for mobile, embedded or realtime applications in which computing resources may be limited.
* 然而，**清晰的设计通常更容易调整**，因此从长远来看，系统所需的非功能属性更容易实现 However, a clear design is usually easier to tune, so that over the long term, the required non-functional properties of a system are easier to achieve.

# 重构改变API：Refactorings that alter APIs

除了改变非功能行为外，有些重构需要改变软件模块的应用编程接口（API）。In addition to changing non-functional behaviour, some refactorings require that an application programming interface (API) to a software module be altered.

**API是一个模块的公共操作和属性的集合，系统中的其他模块在运行时可以访问它**。在面向对象系统中，每个对象都有一个由其类的公共成员（操作和属性）定义的API。对API的改变发生在以下情况  The API is the set of public operations and attributes of one module that can be accessed by other modules in a system at runtime. In object-oriented systems, every object has an API defined by the public members (operations and attributes) of its class. Changes to an API happens when:

* 一个类成员被移动 a class member is moved;
* 一个类成员被重新命名 a class member is renamed;
* 一个类成员的可见性被改变 the visibility of a class member is changed;
* 一个操作的参数列表被改变 the list of parameters to an operation is changed;
* 操作的返回类型被改变；或者 the return type of an operation is changed; or
* 可能引发的异常被改变 the exceptions that may be raised are changed.

**如果API是针对一个只在软件系统内访问的类，这可能不是一个重要的问题** This may not be a significant problem if the API is for a class that is only accessed within a software system. 

* 然而，有些AP**I必须暴露给系统的用户** However, some APIs must be exposed to users of the system.
  *  Fowler将这些接口称为<em>published</em>接口，因为它们已经被公开并记录下来，供整个软件系统的外部客户使用  Fowler refers to these as <em>published</em> interfaces, because they have been exposed and documented for use by external clients of the entire software system.
  * 因此，软件系统的每个使用都与API规范相耦合。所使用的系统版本被说成是其所有用户的<em>依赖性</em>  Consequently, every user of the software system is coupled to the API specification. The version of the system used is said to be a <em>dependency</em> for all its users.
* 如果API规范因重构过程而发生变化，那么**旧API中发生变化的部分**就会成为`deprecated`【被废弃的】If the API specification is changed as a result of the refactoring process, then the part of the old API that has changed becomes <em>deprecated</em>
  * 并且必须被丢弃，或者在更新依赖系统的同时保留在新版本的系统中 and must either be discarded, or retained in the new version of the system while the dependent systems are updated.
  * 通常，有**依赖关系**的软件系统的开发团队会发布一份**迁移计划**，解释用户如何使自己的系统适应新的 API Typically, the development team for a software system that has dependants issues a <em>migration plan</em> explaining how users can adapt their own systems to the new API.

# Key

重构是对软件系统进行修改的过程，以使其更容易理解和更便宜地修改，而不改变其可观察的行为。 Refactoring is the process of making changes to a software system to make it easier to understand and cheaper to modify without changing its observable behaviour.

重构是一种关键的做法，应该与其他软件活动一起进行。 Refactoring is a key practice that should occur alongside other software activities.

重构是一种正式的代码清理形式，在这种形式下，将根据明确的流程和预先定义的计划进行修改。 Refactoring is a formalised form of code cleanup, in which changes are made according to a well specified process and with respect to a pre-defined plan.

重构的机会是通过代码中出现的 "坏味道 "来检测的。 Refactoring opportunities are detected through the occurrence of `bad smells’ in code.

每个坏味道都可以通过应用一个或多个重构来补救。 Each bad smell can be remedied through the application of one or more refactorings.

应用一个重构可能会导致更多的重构机会 Applying a refactoring may lead to further opportunities to refactor.
