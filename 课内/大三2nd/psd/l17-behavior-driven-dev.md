# Behaviour Driven Development

行为驱动开发

* [Behaviour Driven Development](#behaviour-driven-development)
* [术语：Terminology](#术语terminology)
* [为什么需要测试：Reasons for testing](#为什么需要测试reasons-for-testing)
* [测试规模(分类)：Scales of testing](#测试规模分类scales-of-testing)
* [BDD应用场景 & 缺陷： Where does BDD Fit？](#bdd应用场景--缺陷-where-does-bdd-fit)
  * [BDD不足/缺陷](#bdd不足缺陷)
* [BDD生命周期（功能/特性开发）：BDD life-cycle for feature development](#bdd生命周期功能特性开发bdd-life-cycle-for-feature-development)
* [场景-步骤类型：Step Types in Scenarios](#场景-步骤类型step-types-in-scenarios)
* [利用AAA - 生成可执行规范（验收测试案例/规范）：Making Specifications Executable](#利用aaa---生成可执行规范验收测试案例规范making-specifications-executable)
* [步骤函数消除冗余：Frameworks for BDD](#步骤函数消除冗余frameworks-for-bdd)
  * [问题-场景映射测试用例存在冗余](#问题-场景映射测试用例存在冗余)
  * [步骤函数定义： Step function](#步骤函数定义-step-function)
  * [步骤函数例子](#步骤函数例子)
* [And步骤](#and步骤)
* [背景-减少规范冗余：Backgrounds](#背景-减少规范冗余backgrounds)
* [参数化步骤-减少规范冗余：Parameterised Steps](#参数化步骤-减少规范冗余parameterised-steps)
* [步骤抽象：Step Abstraction](#步骤抽象step-abstraction)
* [场景大纲&例表-减少规范冗余：Scenarios Outlines & Example Tables](#场景大纲例表-减少规范冗余scenarios-outlines--example-tables)
* [步骤函数实现位置：Where to implement the step functions?](#步骤函数实现位置where-to-implement-the-step-functions)
* [BDD良好实践：Good Practice](#bdd良好实践good-practice)
* [BDD相对优势](#bdd相对优势)
* [BDD局限-可维护性：Maintainability](#bdd局限-可维护性maintainability)
* [Summary](#summary)

# 术语：Terminology

![](/static/2021-01-16-15-11-40.png)

:orange:Test Case测试用例

* **描述软件中执行的行为操作集合，及预期结果** The description of a set of actions to be performed on software and an expected outcome

:orange: Test suite 测试套件

* 测试套件（test suite）有时也称为验证套件（validation suite），是**许多测试用例的集合**，

:orange:Test 测试

* **执行测试用例，产生测试输出** The execution of a test case, producing a test outcome.

:orange: Testing 测试过程

* **创建，维护，执行，测评测试用例的具体实践** The practice of creating, maintaining, executing and evaluating test cases.

Note that these definitions are frequently ‘abused’.

# 为什么需要测试：Reasons for testing

测试目的

![](/static/2021-01-16-15-18-51.png)

* **发现缺陷** Detection of defects
* **支持模块化实现 & 设计** Support the design and implementation of a module
* **避免引入缺陷** Prevent the introduction of defects
* **文档化系统行为** Document the behaviour of a system
* **证明某系统是否满足规范需求** Demonstrate that a system meets it specification

:orange: **【发现缺陷】** - 进行测试活动的最古老原因 detection of defects - oldest reason for undertaking testing activities

* 软件开发团队被分为开发人员&测试人员：
* 测试人员将得到一个**实施方案和对其预期功能的高级描述**，然后他们会**编写测试来检查两者之间的对应关系** The testers would be given an implementation and a high level description of its intended functionality. They would then develop tests to check for the correspondence between the two
* 这种做法在软件开发组织中仍然存在，**特别是当需要为审计目的而证明测试的独立性时** This practice still occurs in software development organisations, particularly where there is a need to demonstrate the independence of testing for audit purposes
* 然而，在现代软件实践中，尤其是在小型团队中，测试的其他原因变得更加重要。<font color="red">重点已经从识别和消除缺陷转移到首先【预防缺陷的引入】，作为持续变化管理系统的一部分</font> However, in modern software practice, particularly in small teams, the other reasons for testing have become more important. The emphasis has shifted away from identifying and removing defects and towards the prevention of their introduction in the first place as part of a continuous change management system

# 测试规模(分类)：Scales of testing

:orange: scales

* Unit tests **单元测试**
* Integration tests **集成测试**
* Acceptance (or system) tests **验收测试**

:candy: 测试规模与测试原因(系统状态类型)相关？Orthogonal to testing reasons

* **成熟软件**可能有大型【**单元测试**】套件 Any mature software application will likely have a very large suite of unit tests
  * 每个单元测试用例,都专注于检查单个模块（如class）的行为，独立 Each unit test case focuses on checking the behaviour of a single module, such as a class
  * <font color="deeppink">每个执行速度需要非常快，因此**单元测试**用例大量使用测试替换</font>。如，fake&mock，并避免执行缓慢行为，如IO，Because there are often so many unit test cases in an application, their execution needs to be very fast. As a consequence, unit test cases should make extensive use of 【test doubles】, such as fakes and mocks and avoid performing slow activities such as IO
* **完全集成系统 - 验收/系统测试** Acceptance or system tests
  * **通常在一个完全集成的系统上进行，因此，成本相对较高** typically performed on a fully integrated system and are as a consequence, relatively costly
  * 一个app通常有**较少的验收测试用例**，<font color="deeppink">主要用于证明系统符合规范, & 归档典型的使用用例</font> An application will typically have a smaller number of acceptance test cases primarily to demonstrate that a system meets it specification, as well as to document typical use cases
  * <font color="deeppink">某些情况下，验收测试也可能在项目生命周期的某个阶段由操作者在界面接口上【手动执行】</font>Acceptance tests may in some cases be manually executed by an operator on an interface at set phases during a project’s life-cycle
  * **非功能特性**也被作为验收测试用例，因为如果某些组件被替换成测试替换（doubles），就不能正确对其进行全面评估 Non-functional properties are also often treated as acceptance test cases, since they can’t be properly be fully evaluated if some components are replaced with doubles
* **集成测试** Integration tests
  * 用于模型中，**对系统组件的子集的集成进行测试** sit somewhere in the model, being performed on integrations of sub-sets of the system’s components
  * <font color="deeppink">然而这个层次上仍将使用测试替换</font> However, test doubles will still be used at this level.

> Test Doubles: 测试替换
>
> **测试中替换待测系统的某个部分从而实施测试**的通用术语 somewhere in the model, being performed on integrations of sub-sets of the system’s components. However, test doubles will still be used at this level

# BDD应用场景 & 缺陷： Where does BDD Fit？

:orange: BDD定义

> **行为驱动开发是以结构化的自然语言（称为 Gherkin）创建和维护需求规范，从该规范中可以推导出测试用例并针对目标实现自动执行**. Behaviour driven development is the creation and maintenance of a requirements specification in a structured natural language (called Gherkin) from which test cases can be derived and automatically executed against a target implementation.

---

![](/static/2021-01-16-17-27-58.png)

* 该图为，将需求表达为**user story** & 以及由它们衍生出来的**场景**。这是一个 We have already seen how requirements can be expressed as user stories (like the one shown here) and scenarios derived from them.
* **用户故事**为功特性/功能提供大纲，每个**场景**提供该功能特性的使用例子 User stories provide the high level outline for a feature. The scenarios each provides an example of a use case of the feature.

---

:candy: <font color="red">语言概念指问题领域而不是技术领域，因此，应如*避免在【场景】步骤中【引入编程语言概念】，如运算符或异常*</font> Language concepts should refer to the problem domain rather than the technical domain. So, for example avoid refer to programming language concepts such as operators or exceptions in the scenario steps.

:orange: **BDD更进一步，提供了技术基础设施，以明确地将这些组件（测试原件）与实现联系起来【BDD结构良好的实践？】** BDD goes further by providing technical infrastructure for explicitly linking these artefacts to the implementation

* 原则上，保持**需求规范**，**测试用例**，**实现**（3个 artefacts）之间的明确联系 In principle, maintaining the explicit linkages between requirements specification, test cases and implementation
  * <font color="deeppink">使在相应实现中的功能描述便于用户理解</font>  allows the presence of features described in a language understood by users to be demonstrated in a corresponding implementation
* 因此，<font color="blue">BDD一般用于编写系统/验收测试用例</font> BDD is therefore generally used for writing system or acceptance test cases
  * 目标 - 支持用户需求分析，验证实现能满足规范，并归档行为 supporting the design of a system with users, demonstrating the implementation meets the specification and documenting the behaviour
  * **BDD也可用于单元，集成测试用例** - 但**开销不值得** There isn’t a particular reason it can’t be used for describing unit or integration test cases as well, although the overhead of doing so may not justify it

## BDD不足/缺陷

:orange: <font color="deeppink">由于 BDD 测试用例可以自动执行，因此它们可以检测回归。然而，由于该技术用于描述系统测试，因此可能更难确定原因</font> as BDD test cases can be executed automatically, they may detect regressions. However, because the technique is used to describe system tests, it may be harder to determine the cause

* 过于笼统，难以确定出现缺陷的原因？

# BDD生命周期（功能/特性开发）：BDD life-cycle for feature development

![](/static/2021-01-16-17-53-50.png)

* 图中显示了高层用户故事、中间工件（场景...API等）和实现之间的层次关系 *diagram shows the hierarchical relationship between the high level user stories, intermediate artifacts and implementation*

> 该图描述了**行为驱动开发BDD的生命周期** The diagram shows a common portrayal of the life-cycle for behaviour driven development.
>
> 1. **功能概括成用户故事** （在用户故事研讨会上完成） The diagram shows a common portrayal of the life-cycle for behaviour driven development. First, features are outlined as user stories, perhaps in a user story workshop.
> 2. **完成各特性的不同用例使用场景**  Then scenarios showing different example use cases for the feature are developed
> 3. **为所有场景添加其步骤** Individual steps for these scenarios are added
> 4. **步骤被翻译成目标应用程序语言，作为步骤函数** steps must then be translated into the target app language as step functions
> 5. **步骤函数推动系统同API设计** These（step functions） then drive the design of the system API and ultimately, the implementation
> 6. **实现** implementation

:candy:注意该生命周期看起来像瀑布流，实践中在实现的同时开发新特性是可行的，当任何提议的设计的实际可行性和依赖性所带来的约束未知时，就避免太多的自上而下的设计  feasible to develop the features concurrently with the implementation. This avoids too much top down design when the actual feasibility of any proposed design and constraints imposed by dependencies are so unknown

* **只是在实现之前开发了测试**。这应该引起警觉 only with tests developed before implementation. This should set alarms bells ringing.
* **将功能开发与实现同时进行要可行得多**。当任何提议的设计的实际可行性和依赖性所带来的约束都是未知的时候，这可以避免太多的自上而下的设计 it is much more feasible to develop the features concurrently with the implementation. This avoids too much top down design when the actual feasibility of any proposed design and constraints imposed by dependencies are so unknown.</p>
* 在行为驱动开发（BDD）中，所需软件的行为是以与系统相互作用的例子集合的形式给出的，使用围绕 "Given-When-Then "结构组织的自然语言感性表达
  * 核心 - Triple A

# 场景-步骤类型：Step Types in Scenarios

BDD生命周期3 - 为所有场景添加其步骤

:orange: <font color="red">小黄瓜方案分为三个阶段，反映了单元测试用例的 "Triple A "模式</font>，表示安排将参与测试的对象的阶段（given步骤），对它们执行一些操作（when步骤），以及断言一些属性在操作后保持不变（then步骤） Gherkin scenarios are divided into three phases that mirror the ‘Triple A’ pattern for unit test cases, denoting phases for arranging the objects that will participate in the test (given steps), performing some actions upon them (when steps) and asserting some properties hold after the action (then steps).

![](/static/2021-01-16-18-09-23.png)

:orange: Triple A pattern（**Triple A 模式**） - **小黄瓜场景** Gherkin scenarios

* **Given**
  * **描述在测试用例执行前如何建立测试用例套件** describe how to set up the test case fixture before the test case is executed.
  * 安排将要参与测试的对象
* **When**
  * **描述测试用例中本身要采取的行为** describe the actions to be taken during the test case itself.
  * 对对象进行的操作
* **Then**
  * **描述了在测试用例结束时对系统状态和/或输出的【断言】** describe the assertions that are made about the state of the system and/or output at the end of the test case
  * 操作结束后对属性状态的断言

# 利用AAA - 生成可执行规范（验收测试案例/规范）：Making Specifications Executable

:orange: <font color="deeppink">根据BDD规范（AAA规范）描述系统行为形成的例子，既是一套验收测试，又是一套规范（AAA规范，描述用户故事），，可以看为，验证当前实现是否符合规范</font>

![](/static/2021-01-16-18-21-48.png)

* <font color="red">根据AAA模式（Triple A pattern），功能对应的每个场景都可以在测试框架（如python的unittest框架）中被**映射为测试用例**</font> Following this AAA pattern, each scenario in a feature can be mapped into a test case within a test framework, such as the unittest framework in Python, as shown here
* 创建BankAccount - Given
  * 通过创建对象，配置套件，实现GIVEN。The first statement arranges the fixture, by creating a bank account object, realising the Given step
  * **描述如何在测试用例执行前设置测试用例套件** Describe how to set up the test case fixture before the test case is executed
* 执行操作deposit(100) - When
  * 实现行为，实现When步骤 The second statement deposits 100 GBP into the bank account, performing an action and realising the When step.
  * 描述如何在测试用例执行前设置测试用例夹具
* 判断balance属性 - Then
  * 断言了应用行为后的值，实现Then步骤 Finally the third statement asserts the value of the balance following the action, realising the Then step.

![](/static/2021-01-16-18-22-27.png)

* 实现行为，，这里的代码显示了实现如何确保测试用例将通过 The code here shows how the implementation ensures that the test case will pass

# 步骤函数消除冗余：Frameworks for BDD

## 问题-场景映射测试用例存在冗余

![](/static/2021-01-16-18-27-40.png)

:candy: **将场景映射到测试用例的工作相当繁琐，并且可能涉及大量的重复**，The job of mapping scenarios to test cases is rather tedious, and may involve a lot of repetition

* <font color="blue">因为相同的步骤可能会出现在许多不同的场景中，甚至会在同一场景中重复</font>, as the same steps may appear in many different scenarios, or even be repeated in the same scenario.
  * 这可以通过<font color="red">重构</font>在一定程度上得到缓解，将步骤实现提取到各个方法中 This can be mitigated to a certain extent through refactoring by extracting the step implementations into individual methods as shown in this slide
* 然而，这仍然意味着**场景中的【步骤顺序】必须在两个不同的地方进行维护：Gherkin功能 & 测试用例中** However, this still means that the ordering of steps in a scenario has to be maintained in two different places: in the Gherkin feature and in the test case
  * 特性中 & 测试用例本身的编写，都要注意方法顺序

---

## 步骤函数定义： Step function

![](/static/2021-01-16-23-10-07.png)

* **BDD框架**（如[LIST]上）通过提供一种机制，**将单个Gherkin步骤与应用程序编程语言中的函数或方法**（称为**步骤定义函数** step definition function）联系起来，消除了这种冗余. BDD frameworks, such as [LIST] eliminate this redundancy by providing a mechanism for linking individual Gherkin steps to functions or method, called <em>step definition functions</em>, in the application programming language.
* 因此，开发人员【**无需维护一个测试用例来对应每个单独的场景**】，而只需确保 Gherkin 功能步骤中的<font color="deeppink">每个唯一步骤都有一个步骤定义函数</font> So, instead of maintaining a test case to correspond with each individual scenario, developers just have to ensure that there is one step definition function for each unique step in the Gherkin feature step. The BDD framework then takes care of executing the scenarios by selecting the correct step functions to execute in sequence.
  * 然后，BDD 框架将通过*选择正确的步骤函数依次执行*来处理场景的执行 The BDD framework then takes care of executing the scenarios by selecting the correct step functions to execute in sequence

## 步骤函数例子

:orange: step defintion function in Behave(python框架) 例子

![](/static/2021-01-16-23-17-54.png)

:orange: 每个唯一小黄瓜步骤对应一个步骤函数

* 每个步骤定义函数`step_impl`都使用适当的注解，注解其所实现的步骤名称 Each step definition function (step_impl) is annotated with the name of the step it implements, using the appropriate annotation.
  * <font color="red">Behave引擎简单解析每个Gherkin场景，查找要执行的正确步骤定义函数，并执行。传递一个上下文共享对象（类似`self`, `this`）</font>
* 包含step func的模块采用【**外观设计模式**】，提供一个方法集合，每个方法在底层应用中实现更复杂功能 Notice that the module containing the step functions is an example of the façade design pattern. The module provides a collection of methods each of which implements more complex functionality in the underlying application

![](/static/2021-01-16-23-18-05.png)

* feature, scenario, steps

---

:orange: JBehave(Java)

![](/static/2021-01-16-23-36-16.png)
![](/static/2021-04-21-12-55-36.png)

# And步骤

![](/static/2021-01-16-23-40-41.png)

* AND为WHEN/THEN/GIVEN同义词 synonym
* 同样重要的是，请注意该场景是如何通过用户可读的例子来定义需求规范的。Just as importantly, note how the scenario is being used to define the requirements specification through a user readable example.
  * 对于如何处理导致银行账户透支的交易，有几种选择。银行可能会对账户中超过限额的所有交易收取费用，可能会对债务适用更高的利率，可能会阻止交易，也可能会阻止进一步的交易。There are several options for how to treat a transaction that results in an overdrawn bank account. The bank may apply a fee, for all transactions whilst the account is over the limit, it may apply a higher interest rate to the debt, it may prevent the transaction, or it may prevent further transactions
* 采用 BDD 方法进行开发时，必须做出这些选择，并将其作为示例进行记录。 Following a BDD approach to development forces these choices to be made and documented as examples

# 背景-减少规范冗余：Backgrounds

有几个特征可以用来减轻**规范中可能出现的重复** There are several features available to mitigate the potential for duplication in specifications

---

![](/static/2021-01-16-23-42-22.png)

:orange:**降低规范中出现重复的可能性**

* 背景 - 用于分组特性中每个场景中出现的步骤 backgrounds can be used to group steps that occur in every scenario in a feature
* 背景将在每个场景中的第一步（测试）之前执行 background will execute before the first step in each scenario
  * 将应该在每个场景开始时执行的小黄瓜步骤组合在一起 Group together Gherkin steps that should be executed at the start of every scenario

# 参数化步骤-减少规范冗余：Parameterised Steps

![](/static/2021-01-16-23-49-23.png)

* `account_name`, `expected_balance`, `amount`参数传递给相关函数
  * **这些参数是在步骤定义函数中定义的，而不是在Gherkin规范中定义的(不像example tables)**  Notice that the parameters are defined in the step definition function, not in the Gherkin specification (unlike examples tables. example tables)
* `guard`参数值 -- `“{account_name}”`<font color="red">可以防止匹配步骤名称的正则超过匹配参数</font> Notice that it is useful to ‘guard’ the parameter values (in this case using quotes around the account name and a currency label) as this prevents the regular expression that matches the step name from over matching the parameter.

:orange: **步骤定义函数可以被参数化** Step definition functions can be parameterised.

* <font color="deeppink">相似步骤（在几个例子，变量上不同，并且以相同方式实现）可以被映射到一个单一的步骤定义函数中</font> This means that very similar steps that only differ for a few example variables and are all implemented in the same way can be mapped to a single step definition function

# 步骤抽象：Step Abstraction

![](/static/2021-01-16-23-55-24.png)

> **这并不是 Gherkin 的真正特性（尽管它应该是**）。它更像是某些 BDD 框架中的一个肮脏的黑客，例如 behave，**它允许更复杂的步骤由更简单的步骤组成**。这**有效地提供了一个比背景更灵活的机制，以避免一系列步骤的重复冗余**。This is not really a feature of Gherkin (although it should be). It’s more a dirty hack in some BDD frameworks, such as behave, that allows more complex steps to be composed of simpler ones. This effectively provides a more flexible mechanism than backgrounds for avoiding duplication of sequences of steps.
> 但是，从Gherkin特性文件中**失去了步骤的明确性**。However, the explicitness of the step is lost from the Gherkin feature file.
> 另一个问题是，**它经常违反AAA模式** Another problem is that it often results in violations of the AAA pattern.
> 例如，这里在后台中包含了一个**When语句来设置账户（违反，triple a规定 given来安排元件）**，然后在实际场景中的另一个Given语句来设置事务  For example here, a When statement is included in the background to set up the account, before another Given in the actual scenario to set up the transaction.

# 场景大纲&例表-减少规范冗余：Scenarios Outlines & Example Tables

:orange: 应用场景

* 当**相同的步骤**序列**可能导致不同的结果时**，这些工具可以被使用，这<font color="red">取决于输入参数的值</font> These can be used when the same sequence of steps can lead to different outcomes depending on the values of input parameters.
* 根据例表输入行数，，场景被执行n次，

![](/static/2021-01-16-23-57-49.png)
![](/static/2021-01-17-00-04-28.png)

* 此例（使用相同步骤序列），余额取决于[取款，存款的组合值] In the case above, the new balance that results from a deposit/withdrawal combination will depend on the values used.
* 该方案将对示例表中的每一行执行一次--在所示案例中为4次（4行输入组合） The scenario will be executed once for each row in the example table - in the case shown 4 times.

> 相同步骤顺序根据参数输入导致不同结果时，使用该工具，每执行一次，输出为example tables中的一行
> These can be used when the same sequence of steps can lead to different outcomes depending on the values of input parameters. In the case above, the new balance that results from a deposit/withdrawal combination will depend on the values used. The scenario will be executed once for each row in the example table.

---

:orange: <font color="deeppink">注意，这个功能与参数化步骤不同，但它经常与它们一起使用</font>Notice that this feature is different from parameterised steps, but it is often used in conjunction with them.

* 示例表从一个**场景大纲和示例表**中生成**许多硬编码**的场景。这些场景在理论上都可以由不同的步骤定义功能来实现。<font color="deeppink">然而，由于它们都非常相似，使用参数化的步骤定义函数来实现它们是有意义的</font>Examples table generate lots of hard coded scenarios from a single scenario outline and examples table. These scenarios could in theory all be realised by different step definition functions. However, as they are all very similar, it makes sense to use parameterised step definition functions to realise them.

# 步骤函数实现位置：Where to implement the step functions?

:orange: <font color="red">实现位置应基于当前测试的目标是什么</font> （对以下什么编写测试）

* （为API功能接口编写测试）**API实现步骤函数（包含步骤函数序列的模块，采用外观模式，，steps为外观？）** On the API (code steps are a facade)
* **系统接口（用户 or REST）** At the ‘system’ interface, e.g. user interface or REST API

---

请注意，在这个例子中，所需的行为是直接**针对应用程序的功能类进行测试**，而不是针对用户界面 Notice that in the example desired behaviour is tested directly against the functional classes of the application, rather than against a user interface

* 一些行为驱动开发的方法开发了测试套件，如果是面向用户的应用程序，则通过用户界面来锻炼应用程序，如果是网络服务，则通过网络端口来锻炼 Some approaches to behaviour driven development develop test suites that exercise the application via a user interface, in the case of a user facing application, or via a network port in the case of a network service.
* 原则上，这（用户接口？）可能是更理想的做法，因为测试是针对整个系统执行的，正如真实用户所观察到的 In principle, this may be more desirable, because the tests are being executed against the whole system, as observed by a real user
  * 然而，在实践中，这可能会大大增加成本，**因为在对这些面向用户的界面进行相对较小的更改时，测试将需要进行调整，即使应用程序真正重要的基础业务逻辑没有被改变**  In practice, however, this may increase costs considerably because the tests will need to be adjusted as relatively minor changes are made to these user facing interfaces, even though the really important underlying business logic of an application has not been altered.
  * 此外，当用于**开发用户界面的框架发生变化时，测试可能需要改变，而这确实不属于开发团队的职责范围** In addition, the tests may need to change as alterations are made to frameworks used to develop the user interface, which are really outside the responsibility of the development team.
* 已经开发了一些工具来缓解这些问题，例如用于 Web 应用程序测试的 Selenium 和 Katalon 框架；以及 BDD 工具的特定扩展，例如 django-behave  Some tools have been developed to mitigate these problems, such as the Selenium and Katalon frameworks for web application testing; and specific extensions to BDD tools, such as django-behave.

# BDD良好实践：Good Practice

* Keep scenarios (like any other test case) short **场景尽量短**
* Keep individual steps short **步骤尽量短**
* Comply with the AAA convention **遵从AAA规范**
* Write features in user domain language **使用用户域语言编写新功能**
* Use frameworks to cut down on redundancy **使用框架减少冗余**
  * 步骤函数
  * 以及减少规范冗余的技术
    * 背景
    * 参数化步骤
    * 步骤抽象
    * 场景大纲 & 例表
* Develop features and implementation * gradually **逐步开发&实现新特性**
* Work with the customer to validate * features **与用户协调，确认特性符合期望规范**

# BDD相对优势

创新被认为比其前身更好的程度"（[4], p.2）。这可以反映在敏捷方法能够提供的好处，如提高生产力和质量，降低成本和时间，产生可维护和可进化的代码，提高士气，合作和客户满意度[5]  “the degree to which the innovation is perceived to be better than its precursor” ([4], p.2). This can be reflected in the ability of an agile method to offer benefits like improved productivity and quality, reduced cost and time, producing maintainable and evolvable code, improved morale, collaboration and customer satisfaction [5].

使用特定领域术语、改善利益相关者之间的沟通、BDD规范的可执行性以及促进对代码意图的理解被认为是BDD的主要好处 The use of domain specific terms, improving communication among stakeholders, the executable nature of BDD specifications, and facilitating comprehension of code intention are revealed as the main benefits of BDD

# BDD局限-可维护性：Maintainability

![](/static/2021-04-25-14-49-25.png)

- 规范可能难以理解。
- 很难找到故障源，尤其是在大型BDD套件中。
- 为了纠正故障、适应新的需求或适应新的环境而改变规范可能很困难。
- 使缓慢的套件运行得更快。
- 除了单元测试，还需要维护BDD测试。
- 应对BDD工具可能带来的复杂性。
- BDD规范中的重复检测。
- BDD规范中的重复管理。

![](/static/2021-01-17-00-16-12.png)

* 按测试例子来规范意味着**任何所需的行为都必须以实例的形式来表达**。Specificiation by example means that any desired behaviour must be expressed as an example. This can lead to lots and lots of examples if the specification is to be complete.
  * 如果要使规范完整，这可能会导致例子过多
* 哲学使抽象(从而使简洁)变得困难 The philosophy makes abstraction (and thus brevity) difficult
* 当**功能或 API 发生变化时，需要维护步骤定义（仍有冗余**）Need to maintain step definitions as features or API change (still have redundancy)
  * 功能API改变，步骤定义（函数）也需要改变，
* **在给定的步骤定义中对依赖关系进行硬编码** Hard coding of dependencies in given step definitions
* **在Gherkin中只可能有一个层次的抽象，所以你会得到很多重复的步骤序列（过多冗余）** Only one level of abstraction possible in Gherkin, so you get lots of duplicate sequences of steps
* Gherkin步骤是潜在有序的，因此需要表达所有的顺序组合（**保证维护顺序**） Gherkin steps are implicitly sequential, so all combinations of orders need to be expressed
* BDD冗余
  * 重复会使规范难以理解
  * BDD规范中存在的重复会导致它们难以扩展和改变（可能导致功能冻结
  * BDD规范中存在的重复会导致BDD套件的执行时间超过必要时间

# Summary

> BDD provides a means, through executable specifications, of ensuring traceability between user facing requirements and implementations.
> 通过可执行的规范，BDD提供了一种手段，以确保面向用户的需求和实现之间的可追溯性

**虽然行为驱动开发是测试驱动开发的进化**，但关注的核心是设计。行为驱动开发中，定义系统的行为是主要工作，而对系统行为的描述则变成了测试标准。（AAA标准，步骤函数描述验收标准）

自动运行测试套件，提供快速反馈，进行测试套件。满足**持续集成**要求 （这里可以延申CI优点，，要点是针对CI的变更管理选择，自动化构建&测试，自动化部署）

BDD 已经包含了敏捷分析（需求管理L5延申，用户故事模板，用户故事质量评估INVEST标准）和自动化验收测试的更广泛的范围。

* 一个故事的行为仅仅是它的验收标准——如果系统满足所有的验收标准，那么它的行为就是正确的; 如果它不满足，那么它就不是。因此，我们创建了一个**模板来捕获故事的验收标准**
  * BDD优化需求管理（具体化用户故事模板，以方便映射故事每个场景为测试用例（利用AAA规范）或是**场景中每个步骤映射成步骤函数<减少冗余>**,形成可执行验收标准）
  * Triple A - Given,When,then模板 来编写**用户故事卡**

:orange: <font color="deeppink">根据BDD规范（AAA规范）描述系统行为形成的例子，既是一套验收测试，又是一套规范（AAA规范，描述用户故事），，可以看为，验证当前实现是否符合规范</font>
