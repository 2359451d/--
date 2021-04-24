# Content

* [Content](#content)
* [Evaluating Test Suites](#evaluating-test-suites)
  * [为什么需要引入有效性/效率](#为什么需要引入有效性效率)
  * [测试（套件）目的 & 有效性/效率权衡](#测试套件目的--有效性效率权衡)
  * [权衡例子](#权衡例子)
* [Useful Metrics](#useful-metrics)
* [测试套件运行时间意义：Test Suite Run Time](#测试套件运行时间意义test-suite-run-time)
* [测试代码覆盖率-衡量有效性&效率：Test Code Coverage](#测试代码覆盖率-衡量有效性效率test-code-coverage)
  * [可忽略的代码行：If using LoC can ignore](#可忽略的代码行if-using-loc-can-ignore)
* [测试代码覆盖率局限性：Limits of test code coverage](#测试代码覆盖率局限性limits-of-test-code-coverage)
* [变异测试：Mutation testing](#变异测试mutation-testing)
  * [变异操作-概念&特性：Mutation operations](#变异操作-概念特性mutation-operations)
    * [例子](#例子)
    * [变异操作类型：Types of mutations](#变异操作类型types-of-mutations)
  * [变异测试流程：Mutation testing process](#变异测试流程mutation-testing-process)
    * [测试后对突变体进行分类：Classifying mutants after testing](#测试后对突变体进行分类classifying-mutants-after-testing)
  * [变异测试度量标准（有效性 & 效率）：Mutation testing metrics](#变异测试度量标准有效性--效率mutation-testing-metrics)
  * [例子-变异测试报告](#例子-变异测试报告)
  * [例子-变异测试效率：Evaluating test suite efficiency using mutation testing](#例子-变异测试效率evaluating-test-suite-efficiency-using-mutation-testing)
  * [变异测试配置影响指标：Configuration space and optimisation mutation testing](#变异测试配置影响指标configuration-space-and-optimisation-mutation-testing)
  * [跨项目比较突变测试指标:Comparing mutation test results across applications](#跨项目比较突变测试指标comparing-mutation-test-results-across-applications)
  * [Limitations of mutation testing](#limitations-of-mutation-testing)
* [Summary](#summary)

# Evaluating Test Suites

## 为什么需要引入有效性/效率

:orange: <font color="red">测试任何系统都需要投入成本。即使测试用例是自动化的，它们仍然需要被开发，更重要的是需要被维护</font> Testing any system requires effort. Even if test cases are automated, they still need to be developed, and more importantly, maintained.

* <font color="deeppink">因此，测试用例的每一行代码应被视为开发团队的持续成本</font>，就像维护一行应用代码的持续成本，或月维护服务器的成本。Each line of test case code should therefore considered as an on-going cost for a development team, just like the on-going cost of maintaining a line of application code, or paying a monthly fee to keep servers online.
* 因此，一个软件<font color="deeppink">团队需要能够判断他们在【测试工作上】可能获得的投资(投入成本)的回报</font>。A software team therefore needs to be able to judge what return on investment they are likely to make on testing effort.

## 测试（套件）目的 & 有效性/效率权衡

:orange: 鉴于<font color="red">测试套件的目的是为了防止引入缺陷</font>，有两个有用的属性需要考虑  Given that the purpose of a test suite is to prevent the introduction of defects, there are two useful properties to consider:

* **测试套件的有效性** The effectiveness of a test suite
  * <font color="deeppink">有效性是指测试套件防止缺陷被引入应用程序的能力</font> Effectiveness concerns the ability of a test suite to prevent a defect being introduced into an application.
  * <font color="red">有效的测试套件将确保缺陷被引入系统而未被发现的概率非常低</font>，因为该缺陷很有可能导致一个或多个测试用例失败 Highly effective test suites will ensure that the probability of a defect being introduced into a system undetected is very low, as there is a high probability that the defect will cause one or more test cases to fail
* **测试套件的效率** The efficiency of a test suite
  * <font color="deeppink">效率涉及维护测试套件的成本</font> Efficiency concerns the cost of maintaining the test suite

:candy: 在实践中，存在着以下的权衡 In practice, there is a trade off between

* 最大限度地**提高系统的测试覆盖率（增加测试用例），以增加发现的缺陷数量**（最大化有效性） maximising the test coverage of a system to increase the number of defects discovered;
* 最大限度地**减少每个发现的缺陷的测试成本**(最大化效率) minimising the cost of conducting testing per defect discovered.

:candy: <font color="deeppink">实现高效率（efficiency）测试套件的最简单方法是删除所有的测试用例</font>。The easiest way to implement an efficient test suite is to remove all the test cases.

* 同样，每当在<font color="deeppink">测试套件中添加测试用例以增加覆盖率时(实现高有效性 effectiveness)，就会增加冗余的概率，降低效率(因为测试套件的维护成本增加了)</font>。  Equally, whenever a test case is added to a test suite to increase coverage, it increases the probability of redundancy, reducing the efficiency.
* 一个软件团队需要努力为他们的项目找到这两者之间的折中方案 A software team needs to work to find the compromise between the two for their project

---

## 权衡例子

![](/static/2021-01-19-22-20-35.png)

* 上图中间点为optimal point，tradeoff (权衡有效性 & 效率)
  * develop team need to compromise two factors
* 往右，effectiveness↑，efficiency妥协，需要做更多的test case
  * 测试套件中测试用例增加，覆盖率增加，实现高有效性
  * 降低效率，因为测试套件维护成本增加
* 往左，优化test suite，减少test case，从而减少了effectiveness（preventing defect introduction的能力）。最后达到最高效率
  * 测试套件中测试用例减少，覆盖率减少，低有效性
  * 测试套件效率大大提升，因为测试套件维护成本大量减少

# Useful Metrics

衡量Efficiency & Effectiveness的度量标准

![](/static/2021-01-19-22-36-55.png)

* **发行后缺陷报告** post release reported defects
  * **有效性 - 缺陷发现能力** measuring the **effectiveness**
  * 如果一个应用程序的用户报告的缺陷很低，那么我们可以得出结论，测试套件实际上防止了缺陷引入 app that low user-reported defects then we may conclude that the test suite actually prevents the defects introduced
* **套件执行期间行覆盖率** LoC reached during test suite execution (lines of code reached during test suite execution)
  * **有效性 & 效率**
  * So if a test suite is able to exercise a high proportion of a test suite scope of applications codebase.
  * 可能更有效揭示该代码库中的任何缺陷 It may well be effective at uncovering any defects in that code base
  * 所以每个被阻止缺陷的测试套件执行的行数越少，可能期望测试套件越高效 so the fewer lines executed by a test suite per defect prevented the more efficient we might expect our test suite to be
* **测试套件执行时间** Test suite execution time
  * **效率 - 测试套件维护成本**
  * 可能非常重要，尤其单元测试套件 execution time is also useful for measuring efficiency and can be quite important, particularly if the unit test suites
* **每个缺陷失败的测试** failed tests per defect
  * 如果我们有大量的测试，或在引入一个缺陷时失败了，那么**针对每个缺陷的失败测试是衡量效率的一个有用指标，这可能表明这些测试内部是冗余的** failed tests per defect is a useful measure of efficiency if we have a large number of tests or failing when a single defect is introduced, that may indicate that those tests are internally redundant
* **测试套件的代码行数大小** test suite LoC
  * **效率 - 测试套件维护成本**
  * <font color="red">测试套件的代码库越大，效率就越低（因为维护成本增加），我们的代码我们的测试套件成本就越大</font> lines of code size of the test suite can also be an indicator of efficiency, the larger the code base for the test suite, the less efficient, and more costly our code our test suite is.

# 测试套件运行时间意义：Test Suite Run Time

![](/static/2021-01-19-22-40-45.png)

:orange: **测试套件的运行时间是一个重要的效率指标** Test suite run time is an important efficiency metric.

* <font color="red">特别是单元测试套件应该运行得非常快</font> Unit test suites in particular should run very quickly.
* 一些指导，如Fowler在他的持续集成书中主张**构建和测试过程的运行时间应少于10分钟** Some guidance, such as Fowler in his book on continuous integration advocate a run time for a build and test process to be less than 10 minutes
  * 对于整个系统的构建过程来说，这通常是可以接受的。**然而，对于单个类或包的测试套件来说，在几秒钟内就可以执行是非常有用的，这样开发人员就可以将其纳入他们的开发工作流程中**，而且重要的是，在对模块的更改提交到变更管理系统之前，确保其通过 This is usually acceptable for a build process for an overall system. However, it is useful for the test suite for a single class or package to be executable within a few seconds, so that a developer can incorporate them into their development workflow, and importantly, ensure that they pass before changes to the module are committed to the change management system.

:orange: **执行时间过长的测试套件（尤其是单元测试套件）很可能被废弃**。Test suites that take too long to be executed (particularly unit test suites) may well fall into disuse.

* 像profilers这样的工具可以用来评估测试套件的运行时间，也可以识别瓶颈。Tools like profilers can be used to evaluate the run time of test suites and also identify bottle necks.
* 一个常见的（**单元测试执行时间过长**）原因：<font color="red">是在单元测试中包含了IO交互</font>，这应该通过某种形式的**替换测试**来消除，例如模拟  A common cause is the inclusion of IO interactions in unit tests which should be eliminated using some form of test double, such as a mock.

# 测试代码覆盖率-衡量有效性&效率：Test Code Coverage

:orange: **测试覆盖率---衡量效率和有效性的一种方法** One way of measuring the efficiency and effectiveness

* <font color="deeppink">跟踪目标应用程序的不同部分，在测试套件的执行过程中的情况。</font> is to track how different parts of a target application are exercised during the execution of a test suite.
  * 测试覆盖率被定义为一种测试技术指标，它表明我们的测试用例是否真正完全覆盖了应用程序代码中的各种可能以及在运行这些测试用例时执行了多少代码。
* **测试覆盖率 - 测量单位** Granularity
  * Lines of Code （LoC）
  * Statement
  * Expression
  * 这通常是以执行过程中 "达到" 的**代码行**来衡量的。<font color="red">一个更精确的衡量标准是执行的语句，甚至是评估的表达式</font>。 This is often measured in terms of lines of code ‘touched’ during execution. A more precise measure would be in terms of statements executed, or even expressions evaluated.
    * 这是因为一行代码可能包含有条件的表达式，导致该行只有部分内容被完全评估 This is because a line of code may contain conditional expressions that cause only parts of the line to be fully evaluated.

![](/static/2021-01-19-22-45-43.png)

假设 测试覆盖率测量单位 - LoC

* **有效性 effectiveness**
  * 在执行测试套件期间接触到的唯一LoC / 应用程序中的总LoC
  * The number of unique lines of application code executed, divided by the total lines of code in the application.
* **效率 efficiency**
  * 应用中总LoC / 执行的非唯一LoC总数 + 唯一行总数（即，应用中总LoC数）
  * 因此，随着执行的总LoC的增加，效率会下降。So, as the total LoC executed increases efficiency declines.

:orange: **效率和有效性的衡量标准(单位)都可以进行调整**（除了LoC的测量，也可以换成statements，Expressions的测量）。Both the efficiency and effectiveness metrics can be adjusted.

* 例如，与其测量LoC，不如忽略某些类型的表达，如条件评估，这可能是合适的。这是因为条件在程序中创造了不同的执行路径，**所以无论如何，对它进行多次评估可能是合理的**。 For example, rather than measuring LoC, it may be appropriate to ignore certain kinds of expression, such as conditional evaluation. This is because a condition creates different paths through a program, so it may be reasonable to evaluate it more than once anyway.

## 可忽略的代码行：If using LoC can ignore

:orange: <font color="deeppink">在评估测试代码覆盖率时，【忽略某些类型的代码行】可能是有意义的</font> It may make sense to ignore certain types of lines of code when evaluating test code coverage.

* **声明**：如import语句 Declarations such as import statements
* **括号** Parentheses
* **空格** White space
* **源码文档** Source code documentation
* **重构类方法：getter,setter,mutators,accessors...** ‘Uninteresting’ methods, such as accessors and mutators.

:candy: 请注意，方法定义通常被包括在内，因为当方法被调用时，声明被认为已经被 "执行 "了，尽管严格来说声明并不会被执行 Note that method definitions are usually included, as the declaration is assumed to have been ‘executed’ when the method is called, even though this isn’t strictly true.

# 测试代码覆盖率局限性：Limits of test code coverage

![](/static/2021-04-21-19-02-20.png)

:orange: 尽管实施起来非常简单，<font color="red">但测试代码覆盖率可能会产生误导，主要是因为它并不直接测量与缺陷有关的东西。</font> Although quite simple to implement, test code coverage can be misleading, primarily because it doesn’t directly measure anything to do with defects.

* 实际上，测试代码覆盖率假定缺陷可以被统一引入到代码库中的任何一行代码中。 Effectively, test code coverage assumes that defects could be uniformly introduced into any line of code in the code base.
* **测试代码覆盖率假定对一行代码、语句或表达式（测试套件的）进行执行就足以暴露出它可能包含的任何缺陷**。<font color="deeppink">这不一定是一个非常安全的假设，因为它取决于测试套件的质量</font>  test code coverage assumes that exercising a line of code, statement or expression is sufficient to expose any defects it might contain. This is not necessarily a very safe assumption, as it depends on the quality of the test suite
  * 高覆盖率，不一定意味着测试套件包含了所有可能的情况，不一定包含了所有的执行路径，并不能发现未考虑的情况形成的缺陷
  * 代码覆盖率并不能告诉您测试的代码。它只告诉你运行了什么代码！您可以自己尝试一下：采用具有100％代码覆盖率的代码库。从测试中删除所有断言。现在代码库仍然有100％的覆盖率，但不测试任何东西！因此，代码覆盖率不会告诉您测试的是什么，只测试未测试的内容。

:orange: 一个更常见的问题是，在**强调测试代码覆盖而不是缺陷覆盖**的情况下，<font color="deeppink">开发人员可能会倾向于实现增加覆盖率的测试，但是缺少断言，从而有效地忽略了度量</font> A more common problem is that where test code coverage is emphasised over defect coverage, developers may be tempted to implement tests that increase coverage, but lack assertions, effectively gaming the metric

![](/static/2021-01-19-23-01-40.png)

* 在所示的例子中，这两个测试用例实现了**非常高的测试覆盖率，确保所有的语句都被执行，每个表达式都被评估**。然而，条件是不正确的，**因为如果金额0被作为一个参数提供，它将显示值"-£0.00"**（未正确覆盖的路径，，套件本身的缺陷）。这里的另一个问题是，条件可以修正为小于<而不破坏测试，这表明测试是不明确的 In the example shown, the two test cases achieve very high test coverage, ensuring all statements are executed and that every expression is evaluated. However, the conditional is incorrect, because it will display the value “-£0.00” if the amount 0 is supplied as an argument. A further problem here is that the conditional can be corrected to less than ‘&lt;’ without breaking the test, indicating the test is underspecified.

# 变异测试：Mutation testing

> 突变测试通常对程序的源代码或者目标代码做小的改动，并把截然不同的错误行为（或者怪异行为）作为预期。**如果测试代码没有觉察到这种小改动带来的错误，就说明这个测试是有问题的**
>
> 这些所谓的变异，是基于良好定义的变异操作，这些操作或者是模拟典型应用错误（例如：使用错误的操作符或者变量名字），或者是强制产生有效地测试（例如使得每个表达式都等于0）
>
> 目的是帮助测试者发现有效地测试，或者**定位测试数据的弱点**，或者是在执行中很少（或从不）使用的代码的弱点

:orange: <font color="deeppink">变异测试 --- 测试套件**有效性(检测缺陷引入的能力**)的一个更复杂的衡量标准</font> A more sophisticated measure of test suite effectiveness

* **测试套件在软件【系统改变时检测到缺陷的引入的程度**】。这种技术被称为突变测试，is the extent to which the test suite will detect the introduction of defects as a software system is changed. called mutation testing,
* 其**目的**是<font color="deeppink">估计软件系统的变化被一个或多个失败的测试用例所检测到,的可能性</font>。 The aim of this technique,  is to estimate the likelihood that a change to a software system will be detected by one or more failing test cases.

:orange: 工作原理是将**引入系统的缺陷**表现为**目标系统代码的小规模代码变异的组合**。测试用例作为这些变异存在的检测系统的能力被认为与它在检测缺陷引入方面的有效性相类似（引入变异的测试用例的检测变异能力 = 缺陷引入有效性）。The technique works by representing the introduction of defects into a system as combinations of small-scale code mutations of the target system’s code. The ability of the test case to act as a detection system for the presence of these mutants is considered to be analogous to its effectiveness in detecting the introduction of defects.

## 变异操作-概念&特性：Mutation operations

:orange: **变异测试的关键概念是变异操作**。The key concept in mutation testing is that of a mutation operation

* <font color="deeppink">这些变异操作是对程序逻辑的小的合法改变，可能会对程序的行为产生影响</font>。These are small legal alterations to a program’s logic that could potentially have an affect on it’s behaviour.

:orange: **特性** - <font color="deeppink">变异操作很小，而且定义得很好，所以它们可以在各种组合中自动应用，生成大量的变异程序。</font>Mutation operations are small and well defined, so they can be applied automatically in a wide variety of combinations to generate a large number of mutant programs.

:candy: 此外，<font color="blue">变异操作应该总是产生可行的变异体</font>，例如，**语法错误不应该被引入到源代码**  In addition, mutation operations should always result in viable mutants, e.g. syntax errors shouldn’t be introduced into sourcecode.

### 例子

:orange: 考虑一下下面的示例代码，它来自一个计算抵押贷款的期限（还款次数）的应用程序。 Consider the example code shown below from an application for calculating the term (number of repayments) of a mortgage loan

![](/static/2021-04-21-20-01-00.png)

![](/static/2021-04-21-20-09-52.png)

![](/static/2021-04-21-20-10-47.png)

![](/static/2021-04-21-20-11-52.png)

### 变异操作类型：Types of mutations

:orange: **有许多不同类型的变异操作，包括替换以下**：There are many different types of mutation operation, including replacing:

* **条件运算符与它们的边界对应物** conditional operators with their boundary counterparts
* **带有其他操作的infix（中缀）数学运算符** infix mathematical operators with other operations
  * 如 `2+2`的 `+`
* **带有默认值或其他值的成员字段值** member field values with default or other values
* **具有默认值的变量值** variable values with default values
* **带有空赋值的构造函数调用** constructor calls with null assignments
* **带有默认值的返回值** returned values with default values
* **带有默认值的方法调用结果** method call results with default values.

## 变异测试流程：Mutation testing process

![](/static/2021-04-21-20-20-50.png)

变异测试过程如图所示。The mutation testing process works as illustrated in the figure.

* **这个过程从软件系统的基线版本和附带的测试用例套件开始**。 The process begins with a baseline version of the software system and accompanying suite of test cases.
  * 基线系统被称为 "绿色"。**这意味着该系统通过了（变异前）测试套件中的所有测试，并且就测试套件所能确定的而言，没有缺陷**。 The baseline system is said to be `green’. This means that the system passes all the tests in the suite, and as far as the test suite can determine, is free of defects.
* 接下来，通过将<font color="red">变异操作的随机组合应用到系统的代码中</font>，生成目标系统的大量变异体（变异程序变种）。Next, a large number mutants of  the target system are generated by applying random combinations of mutation operations to the system’s code.
  * <font color="green">这些变异体可以应用于系统源代码、二进制文件或任何最适合于目标编程语言的中间产物</font>。 These mutants can be applied to the system source code, binaries or any intermediates as most appropriate for the target programming language.
* ↓测试后对突变体进行分类
  * 将原始测试线束（原始测试套件）依次应用于每个变异体（变异程序变体）
  * 然后根据结果给每个变异体贴上标签

### 测试后对突变体进行分类：Classifying mutants after testing

![](/static/2021-04-21-20-53-31.png)

然后将原始测试线束依次应用于每个变异体。The original test harness is then applied to each of the mutants in turn.

然后根据结果给每个变异体贴上标签：Each mutant is then labelled according to the outcome:

* 被杀死的变异体**被测试套件成功检测到** Killed mutants are successfully detected by the test suite.
* 幸存的变异体**成功地通过了所有的测试，并且没有被检测到** Survivor mutants successfully pass all tests and are undetected.
  * 幸存的变异体可能表明<font color="red">程序逻辑的某些方面没有被测试所覆盖(测试套件本身有缺陷)，或者突变体可能在功能上等同于原始程序基线（行为与源程序一样，，）</font> A survivor may indicate some aspect of program logic that isn’t covered by a test, or the mutant may be functionally equivalent to the original baseline.
* **未确定的变异体是没有停止的程序** Undetermined mutants are programs that do not halt.
  * 未确定的变异体可能是由**于运行时的运行错误，或者如果测试运行破坏了由于变异体的循环继续条件而造成的超时阈值** Undetermined mutants may be due to a runtime runtime error, or if the test run breaches a timeout threshold caused due to a mutant loop-continue condition, for example.

## 变异测试度量标准（有效性 & 效率）：Mutation testing metrics

![](/static/2021-04-21-21-02-04.png)

:orange: **变异体存活率【有效性**】Mutant survival rate 【effectiveness】

* 提供了一个测试套件在检测缺陷引入方面的有效性 <font color="red">存活率越低，测试套件的有效性就越高。</font> provides an indication of how effective the test suite is at detecting the introduction of defects. The lower the survival rate, the more effective the test suite.

:orange: **效率** efficiency

* 计算在测试套件的执行过程中**被杀死的变异体数量/失败的测试数量**。Efficiency can be calcuated as the number of killed mutants divided by the number of failed tests during the execution of a test suite.
* 理想情况下，变异体和失败测试之间应该是1-1的关系。<font color="red">如果一个变异体导致大量的测试失败，这可能表明测试用例重叠（存在冗余）</font>。 Ideally, there should be a 1-1 relationship between mutants and failed tests. If a single mutant causes a large number of tests to fail, it may indicate that the test cases overlap.

:candy: **这种方法（效率指标）的一个缺点是它没有解决冗余测试的相反问题，这些测试从不会失败(但仍然需要维护)**。One disadvantage of this approach is that it doesn’t address the converse issue of redundant tests that never fail (but still need to be maintained).

* 因此，<font color="red">另一种方法是计算最小测试用例集的大小</font>。 An alternative approach is therefore to calculate the size of the minimal test case set.
  * 对于一个给定的变异体集合，这是能够覆盖所有缺陷的最小的测试用例集。For a given set of mutants, this is the smallest set of test cases that can cover all defects
* <font color="red">不幸的是，找到最小的测试用例集是一个困难的搜索问题，因此提供这一功能的工具通常是作为一个近似值。</font> Unfortunately, finding the minimal test case set is a hard search problem, so tools that provide this feature usually, do so as an approximation.

## 例子-变异测试报告

![](/static/2021-04-21-21-38-06.png)

对有4个不同测试用例的程序运行 Pitest变异测试工具的结果。该程序很小，因此四个测试用例有很高的变异体死亡率（高有效性） The table summarises the results of running the Pitest mutation testing tool against a mortgage calculation that has four different test cases. The application is quite small, so it isn’t too surprising that a high proportion of mutants are killed by the four test cases

## 例子-变异测试效率：Evaluating test suite efficiency using mutation testing

![](/static/2021-04-21-23-01-35.png)

给定高测试代码覆盖率，团队决定尝试删除一些测试，看看**效率**是否可以提高。Given the high coverage, the team decide to experiment with removing some tests to see if efficiency can be improved.

* 该图说明了**从套件中删除测试用例时**，被杀死的变异体的不同数量。 The figure illustrates the different numbers of mutants killed when test cases are removed from the suite.

:orange: 图中显示，删除四个测试用例中的两个，对杀死的变异体的数量影响很小。The figure shows that removing two of the four test cases has very little effect on the number of mutants killed.

* 这表明**被删除的前两个测试用例对测试套件的【有效性贡献很小，不是那么重要，可以被删除】**。  This suggests that the first two test cases removed contribute little in terms of the effectiveness of the test suite.

:orange: 然而，再移除一个额外的测试用例会导致被杀死的变异体数量大幅减少。

* 因此，团队将考虑在回归测试套件中**保留两个最有效的测试案例**。<font color="red">随着目标应用程序和测试套件的发展，这一决定将需要定期审查</font>。However, removing one more additional test case causes a substantial reduction in the number of mutants killed. Consequently, the team will consider retaining the two most efficient test cases in the regression test suite. This decision will need to be periodically reviewed as the target application and test suite evolves.

## 变异测试配置影响指标：Configuration space and optimisation mutation testing

:orange: <font color="red">变异测试产生的指标取决于一系列的配置选项，如</font> Notice that the metrics produced by mutation testing are dependent on a range of configuration options, such as:

* **变异操作的选择** Choice of mutation operations.
  * <font color="deeppink">这些将对模拟的缺陷类型产生重大影响。例如，条件变异操作符可以模拟逐一的编程错误</font>。These will have a significant impact on the type of defects simulated. Conditional mutant operators simulate off-by-one programming errors, for example.
* **应用于程序的变异操作符的数量和组合**。 Number and combination of mutations applied to a program.
  * 应用于程序的变异运算符的数量和组合以产生变异体。Number and combination of mutation operators applied to a program to produce mutants.
  * <font color="red">创建的变异体越多，要评估的缺陷集就越丰富，但冗余变异体的风险也越大</font>。The more mutants created, the richer the set of defects to be evaluated, but also the greater the risk of redundant mutants.
* **完成一个测试用例的超时时间** Timeout period for completing a test case.
  * 变异体可能导致不终止的变异体。不可能确定哪些变异体不会终止（见停顿问题）。Mutations may lead to mutants that don’t terminate. It isn’t possible to determine which mutants won’t terminate (see the Halting problem).
  * 因此，<font color="red">变异测试需要为每个测试设置一个超时</font>。这可以配置为类似于绿色测试套件的超时，但是，这可能不允许导致额外（但不是无限）循环周期运行的变异体等等 Therefore, mutation testing needs to set a timeout for each test. This can be configured to be similar to the timeout for the green test suite, however, this may not allow for mutants that cause extra (but not infinite) loop cycles to run etc.
* **每个变异体的测试套件的范围** Scope of test suite for each mutant
  * 一些工具，如PiTest，<font color="red">在进行变异测试之前确定每个测试的覆盖范围</font>。ome tools like PiTest determine the coverage of each test before undertaking mutation testing.
    * 这有助于 This can be useful to
      * (a)**限制需要应用变异的测试用例的选择**，(a) limit the selection of test cases that need to be applied to a mutant
      * 以及(b)**如果范围自上次运行以来没有更改，则有助于避免运行测试**。and (b) helps to avoid running tests if their scope hasn’t changed since a previous run.

## 跨项目比较突变测试指标:Comparing mutation test results across applications

这些指标经常被调整以考虑到**目标应用程序的性质**。因此，在**跨项目比较突变测试指标**时，必须保持警惕，因为： These metrics are often tuned to take into account the nature of the target application. Consequently, it is important to be wary when comparing mutation testing metrics across projects, because:

* <font color="red">不同项目的两种不同配置将对基础测试套件的性能产生不同的影响</font>。Two different configurations for different projects will have different affects on the performance of the underlying test suite.
* <font color="red">两个项目可能有非常不同的特点，最好使用非常不同的配置进行测试</font> Two projects may have very different characteristics, that are better tested using very different configurations.

:orange: 一个简单的例子是比较为交互式使用的系统和为科学计算的批量处理设计的系统 A simple example is comparing a system designed for interactive used with that for volume batch processing of scientific calculations.

## Limitations of mutation testing

尽管通常比测试覆盖率更有信息量，但也要注意**变异测试的局限性**：Although often more informative than test coverage, it is also important to be aware of the limitations of mutation testing:

* 变异测试过程的<font color="red">【有效性】取决于对该过程的精心配置</font> The effectiveness of the mutation testing process is dependent on careful configuration of the process.
* <font color="red">变异操作（或其随机组合）可能不能代表引入项目的缺陷类型</font> Mutation operations (or their random combination) may not be representative of the types of defect that are introduced into a project.
  * <font color="deeppink">例如，精度缺陷可能不会通过数学突变表现出来。同样地，除非将突变应用于构建脚本，否则可能无法捕获外部依赖性缺陷</font>。Precision defects may not be manifested by mathematical mutations, for example. Similarly, external dependency defects may not be captured unless mutations are applied to a build script.
* <font color="red">变异测试需要很长的时间来运行，因为整个测试套件必须在每个突变体上执行</font> Mutation testing takes a long time to run, because the entire test suite must be executed on each mutant.

# Summary

评估测试套件是质量保证流程的一个重要组成部分。Evaluating test suites is an important component of a quality assurance process.