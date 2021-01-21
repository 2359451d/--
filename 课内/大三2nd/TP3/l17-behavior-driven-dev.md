# Behaviour Driven Development

行为驱动开发

* [Behaviour Driven Development](#behaviour-driven-development)
* [术语：Terminology](#术语terminology)
* [为什么需要测试：Reasons for testing](#为什么需要测试reasons-for-testing)
* [测试规模(分类)：Scales of testing](#测试规模分类scales-of-testing)
* [BDD定位： Where does BDD Fit？](#bdd定位-where-does-bdd-fit)
* [功能/特性开发BDD生命周期：BDD life-cycle for feature development](#功能特性开发bdd生命周期bdd-life-cycle-for-feature-development)
* [场景-步骤类型：Step Types in Scenarios](#场景-步骤类型step-types-in-scenarios)
* [使规范可执行：Making Specifications Executable](#使规范可执行making-specifications-executable)
* [BDD框架：Frameworks for BDD](#bdd框架frameworks-for-bdd)
* [And步骤](#and步骤)
* [Backgrounds](#backgrounds)
* [参数化步骤：Parameterised Steps](#参数化步骤parameterised-steps)
* [步骤抽象：Step Abstraction](#步骤抽象step-abstraction)
* [场景大纲：Scenarios Outlines & Example Tables](#场景大纲scenarios-outlines--example-tables)
* [Where to implement the step functions?](#where-to-implement-the-step-functions)
* [BDD良好实践：Good Practice](#bdd良好实践good-practice)
* [BDD局限-可维护性：Maintainability](#bdd局限-可维护性maintainability)
* [Summary](#summary)

# 术语：Terminology

![](/static/2021-01-16-15-11-40.png)

:orange:Test Case测试用例

* 描述软件中执行的行为操作集合，及预期结果

:orange:Test 测试

* 执行测试用例，产生测试输出

:orange: Testing

* 创建，维护，执行，测评测试用例的具体实践

# 为什么需要测试：Reasons for testing

![](/static/2021-01-16-15-18-51.png)

* 发现缺陷
* 支持模块化实现 & 涉及
* 避免引入缺陷
* 文档化系统行为
* 证明某系统是否满足规范需求

> 发现缺陷 - 进行测试活动的最古老原因 detection of defects - oldest reason for undertaking testing activities
>
> 软件开发团队被分为开发人员&测试人员：
>
> * 测试人员将得到一个**实施方案和对其预期功能的高级描述**，然后他们会**开发测试来检查两者之间的对应关系** The testers would be given an implementation and a high level description of its intended functionality. They would then develop tests to check for the correspondence between the two
>
> 这种做法在软件开发组织中仍然存在，特别是当需要为审计目的而证明测试的独立性时 This practice still occurs in software development organisations, particularly where there is a need to demonstrate the independence of testing for audit purposes

> 然而，在现代软件实践中，尤其是在小型团队中，测试的其他原因变得更加重要。重点已经从识别和消除缺陷转移到首先预防缺陷的引入，作为持续变化管理系统的一部分 However, in modern software practice, particularly in small teams, the other reasons for testing have become more important. The emphasis has shifted away from identifying and removing defects and towards the prevention of their introduction in the first place as part of a continuous change management system

# 测试规模(分类)：Scales of testing

:orange: scales

* Unit tests 单元测试
* Integration tests 集成测试
* Acceptance (or system) tests 验收测试

:candy: 测试规模与测试原因(系统状态类型)相关？Orthogonal to testing reasons

* **成熟软件**可能有大型**单元测试**套件
  * 每个单元测试用例,都专注于检查单个模块（如class）的行为，独立 Each unit test case focuses on checking the behaviour of a single module, such as a class
  * <font color="deeppink">每个执行速度需要非常快，因此**单元测试**用例大量使用测试替换</font>。如，fake&mock，并避免执行缓慢行为，如IO，Because there are often so many unit test cases in an application, their execution needs to be very fast. As a consequence, unit test cases should make extensive use of 【test doubles】, such as fakes and mocks and avoid performing slow activities such as IO
* **完全集成系统** - 验收/系统测试
  * 成本较高
  * 一个app通常有较少的验收测试用例，主要用于证明系统符合规范, & 归档典型使用用例 An application will typically have a smaller number of acceptance test cases primarily to demonstrate that a system meets it specification, as well as to document typical use cases
  * <font color="deeppink">某些情况下，也可能在项目生命周期的某个阶段由操作者在接口上【手动执行】</font>Acceptance tests may in some cases be manually executed by an operator on an interface at set phases during a project’s life-cycle
  * **非功能特性**也被作为验收测试用例，因为如果某些组件被替换成双倍组件，就不能正确对其进行全面评估 Non-functional properties are also often treated as acceptance test cases, since they can’t be properly be fully evaluated if some components are replaced with doubles
* **集成测试**
  * 用于模型中，对系统组件的子集的集成进行测试
  * 然而这个层次上仍将使用测试替换

> Test Doubles: 测试替换
>
> 测试中替换待测系统的某个部分从而实施测试的通用术语 somewhere in the model, being performed on integrations of sub-sets of the system’s components. However, test doubles will still be used at this level

# BDD定位： Where does BDD Fit？

![](/static/2021-01-16-17-27-58.png)

* 该图为将需求表达为user story & 场景
* 用户故事为功特性/功能提供大纲，每个场景提供该功能特性的使用例子

> 行为驱动开发是以结构化的自然语言（称为 Gherkin）创建和维护需求规范，从该规范中可以推导出测试用例并针对目标实现自动执行. Behaviour driven development is the creation and maintenance of a requirements specification in a structured natural language (called Gherkin) from which test cases can be derived and automatically executed against a target implementation.

:candy: 语言概念指问题领域而不是技术领域，因此，应如*避免在场景步骤中引入编程语言概念，如运算符或异常*

:orange: BDD进一步提供了关联这些工件与实现的基础设施

* 原则上，保持需求规范，测试用例，实现之间的明确联系 In principle, maintaining the explicit linkages between requirements specification, test cases and implementation
  * 使在相应实现中的功能描述便于用户理解 allows the presence of features described in a language understood by users to be demonstrated in a corresponding implementation
* 因此，<font color="blue">BDD一般用于编写系统/验收测试用例</font> BDD is therefore generally used for writing system or acceptance test cases
  * 目标 - 支持用户需求分析，验证实现能满足规范，并归档行为 supporting the design of a system with users, demonstrating the implementation meets the specification and documenting the behaviour
* BDD也可用于单元，集成测试用例 - 但**开销不值得** There isn’t a particular reason it can’t be used for describing unit or integration test cases as well, although the overhead of doing so may not justify it
* <font color="orange">由于 BDD 测试用例可以自动执行，因此它们可以检测回归。然而，由于该技术用于描述系统测试，因此可能更难确定原因</font> as BDD test cases can be executed automatically, they may detect regressions. However, because the technique is used to describe system tests, it may be harder to determine the cause

# 功能/特性开发BDD生命周期：BDD life-cycle for feature development

![](/static/2021-01-16-17-53-50.png)

* *diagram shows the hierarchical relationship between the high level user stories, intermediate artifacts and implementation*

> 该图描述了行为驱动开发的生命周期
>
> 1. 功能概括成user stories （在用户故事研讨会上完成） The diagram shows a common portrayal of the life-cycle for behaviour driven development. First, features are outlined as user stories, perhaps in a user story workshop.
> 2. 完成显示功能的不同用例使用场景  Then scenarios showing different example use cases for the feature are developed
> 3. 为所有场景添加其步骤 Individual steps for these scenarios are added
> 4. 步骤被翻译成目标应用程序语言，作为步骤函数 steps must then be translated into the target app language as step functions
> 5. step functions推动系统同API设计 drive the design of the system API
> 6. 实现API implementation

:candy:注意该生命周期看起来像瀑布流，实践中在实现的同时开发新特性是可行的，当任何提议的设计的实际可行性和依赖性所带来的约束未知时，就避免太多的自上而下的设计  feasible to develop the features concurrently with the implementation. This avoids too much top down design when the actual feasibility of any proposed design and constraints imposed by dependencies are so unknown

# 场景-步骤类型：Step Types in Scenarios

![](/static/2021-01-16-18-09-23.png)

:orange: Triple A pattern

* **Given**
  * 描述在测试用例执行前如何建立测试用例套件
  * 安排将要参与测试的对象
* **When**
  * 描述测试用例中本身要采取的行为
  * 对对象进行的操作
* **Then**
  * 描述了在测试用例结束时对系统状态和/或输出的断言
  * 操作结束后对属性状态的断言

# 使规范可执行：Making Specifications Executable

![](/static/2021-01-16-18-22-27.png)
![](/static/2021-01-16-18-21-48.png)

* 根据AAA模式，功能的每个场景都可以在测试框架（如python的unittest框架）中被**映射为测试用例**
* 创建BankAccount - Given
* 执行操作deposit(100) - When
* 判断balance属性 - Then

# BDD框架：Frameworks for BDD

![](/static/2021-01-16-18-27-40.png)

:candy: 将场景映射到测试用例的工作相当繁琐，并且可能涉及大量的重复，

* <font color="blue">因为相同的步骤可能会出现在许多不同的场景中，甚至会在同一场景中重复</font>
  * 这可以通过重构在一定程度上得到缓解，将步骤实现提取到各个方法中
* 然而，这仍然意味着场景中的步骤顺序必须在两个不同的地方进行维护：Gherkin功能 & 测试用例中

---

![](/static/2021-01-16-23-10-07.png)

* **BDD框架**（如[LIST]）通过提供一种机制，将单个Gherkin步骤与应用程序编程语言中的函数或方法（称为步骤定义函数 step definition function）联系起来，消除了这种冗余
* 因此，开发人员无需维护一个测试用例来对应每个单独的场景，而只需确保 Gherkin 功能步骤中的<font color="deeppink">每个独特步骤都有一个步骤定义函数</font>
  * 然后，BDD 框架将通过*选择正确的步骤函数按顺序执行*来处理场景的执行

:orange: step defintion function in Behave(python框架) 例子

![](/static/2021-01-16-23-17-54.png)
![](/static/2021-01-16-23-18-05.png)

* 每个步骤定义函数`step_impl`都使用适当的注解，注解其所实现的步骤名称
  * Behave引擎简单解析每个Gherkin场景，查找要执行的正确步骤定义函数，并执行。传递一个上下文共享对象（类似`self`, `this`）
* 包含step func的模块采用外观设计模式，提供一个方法集合，每个方法在底层应用中实现更复杂功能

---

:orange: JBehave(Java)

![](/static/2021-01-16-23-36-16.png)
![](/static/2021-01-16-23-36-50.png)

# And步骤

![](/static/2021-01-16-23-40-41.png)

* AND为WHEN/THEN/GIVEN同义词

# Backgrounds

![](/static/2021-01-16-23-42-22.png)

:orange:降低规范中出现重复的可能性

* 背景 - 用于分组功能中每个场景的步骤 backgrounds can be used to group steps that occur in every scenario in a feature
* 背景将在每个场景中的第一步（测试）之前执行 background will execute before the first step in each scenario

# 参数化步骤：Parameterised Steps

![](/static/2021-01-16-23-49-23.png)

* `account_name`, `expected_balance`, `amount`参数传递给`revelant`函数
  * 这些参数是在步骤定义函数中定义的，而不是在Gherkin规范中定义的
* `guard`参数值 -- `“{account_name}”`可以防止匹配步骤名称的正则超过匹配参数

:orange:步骤定义函数可以被参数化

* <font color="deeppink">相似步骤（在几个变量例子上不同，并且以相同方式实现）可以被映射到一个单一的步骤定义函数中</font>

# 步骤抽象：Step Abstraction

![](/static/2021-01-16-23-55-24.png)

> 这并不是 Gherkin 的真正特征（尽管它应该是）。它更像是某些 BDD 框架中的一个肮脏的黑客，例如 behave，**它允许更复杂的步骤由更简单的步骤组成**。这有效地提供了一个比背景更灵活的机制，以避免步骤序列的重复。
> 但是，从Gherkin特征文件中失去了步骤的明确性。另一个问题是，它经常违反AAA模式。
> 例如，这里在后台中包含了一个When语句来设置账户，然后在实际场景中的另一个Given语句来设置事务

# 场景大纲：Scenarios Outlines & Example Tables

![](/static/2021-01-16-23-57-49.png)
![](/static/2021-01-17-00-04-28.png)

* 余额取决于取款，存款的组合值

> 相同步骤顺序根据参数输入导致不同结果时，使用该工具，每执行一次，输出为example tables中的一行
> These can be used when the same sequence of steps can lead to different outcomes depending on the values of input parameters. In the case above, the new balance that results from a deposit/withdrawal combination will depend on the values used. The scenario will be executed once for each row in the example table.

:orange: 该功能经常与参数化步骤结合使用

* 示例表从一个单一场景大纲中生成了很多hard-coded场景，理论上可以通过不同的步骤定义函数实现 Notice that this feature is different from parameterised steps, but it is often used in conjunction with them.
  * 由于这些场景（步骤）非常相似，因此可以用参数化步骤定义函数来实现 Examples table generate lots of hard coded scenarios from a single scenario outline and examples table. These scenarios could in theory all be realised by different step definition functions. However, as they are all very similar, it makes sense to use parameterised step definition functions to realise them.

# Where to implement the step functions?

* On the API (code steps are a facade)
* At the ‘system’ interface, e.g. user interface or REST API

# BDD良好实践：Good Practice

* Keep scenarios (like any other test case) short 场景尽量短
* Keep individual steps short 步骤尽量短
* Comply with the AAA convention 遵从AAA规范
* Write features in user domain language 使用用户域语言编写新功能
* Use frameworks to cut down on redundancy 使用框架减少冗余
* Develop features and implementation * gradually 逐步开发&实现新特性
* Work with the customer to validate * features

# BDD局限-可维护性：Maintainability

![](/static/2021-01-17-00-16-12.png)

* 举例说明意味着任何所需的行为都必须以实例的形式来表达。Specificiation by example means that any desired behaviour must be expressed as an example. This can lead to lots and lots of examples if the specification is to be complete.
  * 如果要使规范完整，这可能会导致例子过多
* 哲学使抽象(从而使简洁)变得困难 The philosophy makes abstraction (and thus brevity) difficult
* 当功能或 API 发生变化时，需要维护步骤定义（仍有冗余）Need to maintain step definitions as features or API change (still have redundancy)
* 在给定的步骤定义中对依赖关系进行硬编码 Hard coding of dependencies in given step definitions
* 在Gherkin中只可能有一个层次的抽象，所以你会得到很多重复的步骤序列 Only one level of abstraction possible in Gherkin, so you get lots of duplicate sequences of steps
* Gherkin步骤是隐式顺序的，因此需要表达所有的顺序组合 Gherkin steps are implicitly sequential, so all combinations of orders need to be expressed

# Summary

> BDD provides a means, through executable specifications, of ensuring traceability between user facing requirements and implementations.
> 通过可执行的规范，BDD提供了一种手段，以确保面向用户的需求和实现之间的可追溯性

