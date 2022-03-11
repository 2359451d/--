# Content

* [Content](#content)
* [Outcome1](#outcome1)
* [Introduction](#introduction)
* [软件质量保证实践：Current Practice in Software Assurance](#软件质量保证实践current-practice-in-software-assurance)
  * [Testing（工具协助下）](#testing工具协助下)
  * [人工代码审计: Manual Code Audits](#人工代码审计-manual-code-audits)
  * [功能性测试：Feature Testing](#功能性测试feature-testing)
* [Static Analysis](#static-analysis)
* [静态分析工具：Examples of Static Analysis Tools](#静态分析工具examples-of-static-analysis-tools)
* [静态分析如何有效？What does Static Analysis do](#静态分析如何有效what-does-static-analysis-do)
  * [Halting Problem](#halting-problem)
  * [静态分析原理](#静态分析原理)
* [静态分析局限性&存在的问题：Challenges & issues](#静态分析局限性存在的问题challenges--issues)
* [静态分析健全性，完整性权衡：Balancing Act of Static Analysis](#静态分析健全性完整性权衡balancing-act-of-static-analysis)
  * [理想静态分析器 perfect analysis](#理想静态分析器-perfect-analysis)
  * [权衡](#权衡)
* [Summary](#summary)
* [==================](#)
* [Outcome2](#outcome2)
* [软件安全分析-确定程序安全性（攻击面）, 污点分析：Intro](#软件安全分析-确定程序安全性攻击面-污点分析intro)
* [两种流-污点流分析：Tainted Flow Analysis](#两种流-污点流分析tainted-flow-analysis)
  * [数据/控制流例子](#数据控制流例子)
* [定义-污点流分析](#定义-污点流分析)
* [Tax计算器例子：Tax Calculator Example](#tax计算器例子tax-calculator-example)
* [类型限定符-使用类型的信任规范:Trust Specification using Types](#类型限定符-使用类型的信任规范trust-specification-using-types)
* [流分析要点&合法流/非法流：The point of Flow Analysis](#流分析要点合法流非法流the-point-of-flow-analysis)
* [Sound Analysis-合法流:健全性分析](#sound-analysis-合法流健全性分析)
* [网格模型表示法：Lattice Model Representation](#网格模型表示法lattice-model-representation)
* [源&汇：Sources, sinks](#源汇sources-sinks)
* [Summary](#summary-1)
* [==================](#-1)
* [静态分析步骤：Static Analysis Approach](#静态分析步骤static-analysis-approach)
  * [例子](#例子)
* [分析例子：Example Analysis](#分析例子example-analysis)
  * [例子2](#例子2)
* [Summary](#summary-2)
* [==================](#-2)
* [添加路径敏感: Adding Sensitivity](#添加路径敏感-adding-sensitivity)
* [增加分析健全性-流敏感度：Flow Sensitivity](#增加分析健全性-流敏感度flow-sensitivity)
* [添加敏感度后例子](#添加敏感度后例子)
* [路径敏感度：Path Sensitivity](#路径敏感度path-sensitivity)
* [多控制条件例子：Multiple Conditionals](#多控制条件例子multiple-conditionals)
* [流/路径敏感分析问题：Challenges of Flow/Path Sensitivity](#流路径敏感分析问题challenges-of-flowpath-sensitivity)
* [函数调用：Handling Function Calls](#函数调用handling-function-calls)
* [函数调用例子：Handling Function Calls](#函数调用例子handling-function-calls)
* [上下文敏感度：Context Sensitivity is not a Silver Bullet](#上下文敏感度context-sensitivity-is-not-a-silver-bullet)
* [隐式流：Implicit Flows](#隐式流implicit-flows)
* [信息流（隐式流）分析：Information Flow Analysis](#信息流隐式流分析information-flow-analysis)
* [信息流分析例子](#信息流分析例子)
* [Summary](#summary-3)
* [==================](#-3)
* [Example1](#example1)
* [Example2](#example2)
* [Example3](#example3)
* [Example4](#example4)
  * [Example 5](#example-5)

# Outcome1

权衡，静态分析作用原理，为何有效

![](/static/2022-01-31-15-53-09.png)

所以今天讲座的重点，就像我说的，是让你具体思考静态分析器本身如何工作。通常你会发现，如果你作为一个顾问，如果你作为一个开发人员，如果你把安全作为你的工作重点，你就会**经常关注代码分析，因为对代码库进行某种安全分析的全部意义在于真正了解代码中的错误表现**。基础，因为现实中发生的漏洞会影响到像你这样的人。我自己，它总是归结为对代码中某种bug的利用，因此有几种技术我们可以使用，**静态分析是其中之一，以了解bug在代码库中的表现，以了解具体的漏洞在哪里表现出来**。就像我说的，**代码库和进行静态分析的整个要点是真正了解哪里存在漏洞**。为了让你作为一个开发人员或某种代码审查员带头进行分析，以磨练你想把注意力集中在代码库中的具体位置。因为**如果你在一个巨大的项目中工作在一个有数百万行的代码库上。对你来说，例如对这些代码库进行人工审查，并不实际。你需要某种工具，你需要某种可以应用算法的方法，以了解漏洞所在**，所以这就是静态分析发挥作用的地方，我们今天将解读静态分析。 So the whole point of the lectures today, like I said, is to get you to think about specifically how static analyzers themselves work. And quite often you'll find that if you work as a consultant, if you work as a developer and security as your focus, you're going to be looking at code analysis quite frequently, because the whole point of performing some sort of security analysis on a code base is really to understand where bugs manifest in code. Basis because the realistically speaking exploits that happen in the real world exploits that affect people like yourself. Myself, it always comes down to the exploitation of some kind of bug in code, and so there are several techniques that we can use, static analysis being one of them, to understand where the bugs can manifest on a codebase in order to understand where specifically vulnerabilities can manifest on. Like I said, the code base and the whole point of performing static analysis is really to understand where the vulnerabilities exist. In order for you as a developer or some kind of code reviewer to spearhead your analysis in order to hone in on specifically where you want to focus your attention on the code base. Because if you're working on a code base that is millions of lines along a huge project. Not really practical for you to perform, for example manual review of these codebases. You need some kind of tooling, you need some kind of method that you can apply algorithmically in order to understand where the vulnerabilities lie, so that's where static analysis comes into play, and we'll unpack static analysis today. 

# Introduction

A key element of security conscious software development process is performing code review with the assistance of tools… 有安全意识的软件开发过程的一个关键因素是**在工具的协助下进行代码审查**

# 软件质量保证实践：Current Practice in Software Assurance

## Testing（工具协助下）

![](/static/2022-01-31-16-21-35.png)

因此，当你在开发某种软件时，就像我说的，代码审查是会经常发生的事情。**安全委员会软件开发过程的一个关键因素是在工具的协助下进行代码审查。现在，这样做的全部意义其实是为了提供某种保证**。就像你在开发软件时。有一些功能，你正在把它们放在一起，你想从本质上测试这些功能，你知道。是的，你正在**对代码进行审查，以确保某些功能现在是有效的。这个想法是，我们会希望有某种程序。我们把它放到某种程序中，然后我们得到某种输出。我们确定它是否正确，这个过程背后的想法是要找出问题所在**。 So when you are developing some kind of software, like I said, code review is something that will happen frequently. A key element of Security Council software development process is performing code review with the assistance of tools. Now the whole point of doing this is really to provide some sort of assurance. Is like when you're developing software. There are features that you're putting together and you want to essentially test these features and you know. Yes, you are performing a review on code to make sure certain features are working now. The idea is that we would want to have some kind of program. We put it into some kind of process and then we get some kind of output. We determine whether or not it is correct and the idea behind this process is to identify where the problems are.

现在，当涉及到开发软件时，**测试是一种基本做法**，而且有整个。围绕着测试的概念发展的开发方法论。例如，**测试驱动开发**是你在职业生涯中很可能遇到的东西。当你在编写软件时。**但测试的问题是，它只允许你识别代码中的错误。它不一定能告诉你代码中不存在bug，因为你的能力是在测试时了解bug在哪里。当涉及到使用单元测试时，它真正归结于你作为一个程序员的经验。编写测试用例的人本身的经验。你可以有一个相当广泛的套件，但是。现实地讲，你受制于你的同行所知道的东西，【仅通过单元测试的功能测试来提供安全保证是非常困难的**】。 Now testing is a fundamental practice when it comes to developing software and there are entire. Development methodologies developed around the notion of testing. For example, test driven development is something that you may very well come across in your professional career. When you are writing software. But the issue with testing is that it only allows you to identify bugs in the code. It doesn't necessarily tell you that bugs don't exist in the code, because your ability to understand where the bugs are when it comes to testing. When it comes to using unit tests, it really comes down to your experience as a programmer. The experience of the individual that is writing the test cases themselves. You can have a pretty extensive suite, but. Realistically speaking, you are constrained by what it is you know by what your peers know, and it's very difficult really to provide security assurances through unit testing through feature testing alone,

## 人工代码审计: Manual Code Audits

![](/static/2022-01-31-20-53-34.png)

手动代码审核是在某种开发中经常发生的事情。这个想法是，你想让人审查你自己的代码，或者你可能与其他人一起审查代码，对结对编程作为一种实践，经常发生在开发公司内部。就像我说的，这背后的整个想法是为了向客户提供保证，向某种技术领导提供保证案例。现在负责监督项目的人。**使用人类来尝试和识别错误的好处是，你可以利用人类的直觉来识别问题所在。你可以让人们理解或抽象出代码会遇到的那种使用环境，以便思考，以引出某种可能不一定很明显的问题，但同样，你在利用人类的经验。你在利用人类的理解力来应用这种直觉**。manual code auditing is something that will happen frequently in some kind of development house. The idea is that you want to have people looking at code you want to have people reviewing code that you yourself, right, or you may review code with other people, right pair programming as a practice that happens frequently inside of a development house. And like I said, the whole idea behind this is to provide assurances to provide assurance cases to The client to some kind of tech lead. Whoever is in charge of overseeing the project now. The advantage of using humans in order to try and identify bugs is that you can leverage on human intuition in order to identify where the problems are. You can have people sort of understand or abstract and kind of usage context that the code will come across in order to think in order to elicit some kind of problem that may not necessarily be obvious, but again, you're leveraging on human experience. You're leveraging on human understanding in order to apply that intuition.

**但使用人类的问题是，他们要花钱。人类会累，人类会犯错，最终人类的分析速度非常非常慢**。因此，如果你想分析一个巨大的代码库，**比如说一个操作系统的大小，为了让人们手动审查整个代码，以识别所有的潜在错误，所有的潜在问题，这是非常困难的，所以没有保证**。But the problem with using humans is that they cost money. Humans get tired, humans can make mistakes and ultimately humans are very, very slow with their analysis. So if you wanted to analyze a huge codebase, something the size of an operating system say, it's very difficult in order to get people to manually review that code in its entirety in order to identify all of the potential bugs, all of the potential problems With it so there are no guarantees.

## 功能性测试：Feature Testing

![](/static/2022-01-31-21-16-12.png)

**虽然说功能测试不提供任何保证，但它允许我们建立某种开发程序的【有效性**】。我们要确保功能是有效的。我们要确保你为什么会在那里冒出来。我们要确保这些功能是有效的。我们要确保我们放在一起的软件是正常工作的。因为当然，如果功能要求没有得到满足，将客户委托的任何种类的软件或系统放在一起都没有意义。它根本不可能工作。因此，你可以把一系列的测试放在一起，有几个**测试框架**可以使用，以便提供保证。**行为验证保证**是一个Junit，但还有许多存在于不同的执行环境中。不同的语言，你可以而且可能会在你的职业生涯中使用。但是，软件的问题假定了行为测试的问题。验证的问题是，它不一定为你提供安全保证，那么还有什么可以做的呢？所以这就是静态分析发挥作用的地方。Though speaking feature testing doesn't offer any guarantees, it allows us to establish the validity of some kind of developed program. We want to ensure that the features are working. We want to ensure why have you popped up there. We want to ensure that the features are working. We want to ensure that the software we have put together is working correctly. Because of course there's no point in putting together any kind of software or system that has been commissioned by a client if the functional requirements aren't met. It's simply not going to work. So you can put together a series of tests and there are several testing frameworks that you can use in order to provide assurances. Behavioral validation assurance is a Junit I believe is one of the testing frameworks that the university teaches you in Level 2, I believe, but there are many more that exist for different execution environments. Different languages that you can and probably will use in your professional career. But the problem with software assumes the problem with behavioral testing. The problem with validation is that it doesn't necessarily provide you with security assurances, So what else can be done? So this is where static analysis comes into play.

# Static Analysis

![](/static/2022-01-31-21-17-48.png)

这就是静态分析发挥作用的地方。**静态分析背后的整个想法是，我们希望分析某种类型的代码，而不必运行它。这个程序应该使用一系列的规则，整个想法是我们得到一个程序来分析一个程序，以告诉我们问题在哪里**。因为如果你仔细想想。源代码本质上是一个文本文件。例如 Java，Python，c，c + + 。所有这些高级语言。这些都是文字。所以**静态分析工具是专门用来分析**。编程语言，反过来是分析某种类型的文本文件，所以您可能有某种类型的 Java 分析程序。您可能拥有某种 C 静态分析器，它了解这些不同语言的语法，并将**遵循一系列规则来理解或识别该代码中的模式以及获得计算机、程序和算法的最大优势**。so this is where static analysis comes into play. Now the whole idea behind static analysis is that we want to analyze some kind of code without necessarily running it. There are a series of rules that the program should use, and the whole idea is that we're getting a program to analyze a program in order to tell us where the problems are. Because if you think about it. Source code is essentially a text file. Java, Python, C, C++, for example. All these high level languages. It's all text at the end of the day. So static analysis tools that are designed specifically to analyze some kind of. Programming language, in turn are analyzing some kind of text file, so you may have some kind of Java analyzer. You may have some kind of C static analyzer that understands the syntax of these different languages and will follow a series of rules in order to understand or identify patterns within that code And the biggest advantage with getting a computer, a program and algorithm.

* **分析代码就是代码覆盖率 code coverage**。因为计算机不会累，计算机在技术上不会出错，**所以你可以让一个程序来处理你庞大的代码库并通过它来处理它。非常快速**，这是主要优势。 To analyze the code is that of code coverage. Because computers don't get tired, computers don't technically make mistakes, so you can have a program take your massive codebase and crunch through it in. Very quick fashion and that is the main advantage.
* 然而，使用**静态分析的最大缺点**是，您**不一定能够获得人类的直觉，所以您能够获得代码覆盖率，但是可以说是以智能为代价的**。你**在分析中没有得到人的因素**。 However, the biggest disadvantage with using static analysis is that you don't necessarily get that human intuition, so you are able to get code coverage, but at the expense of intelligence, so to speak. You're not necessarily getting that human intuition. You're not getting that human element in the analysis.
* 实际上有很多不同的程序，使用静态分析。
  * 例如，静态分析是您的防病毒软件将要使用的东西。反编译，或者说对硬盘上存在的二进制文件的检查，会根据一系列实际数据进行检查，**如果有匹配，那么你的防病毒软件就会标记出某个东西是恶意的，这就是为什么你有时会因为模式匹配而得到假阳性，但这并不意味着你看到的东西本质上就是恶意的**。 And there are various different programs really that use static analysis. Static analysis is something that your antivirus will use, for example. The DECOMPILATION, or the inspection of binaries that exist on your hard disk, will be checked against a series of real bases, and if there is a match, then your antivirus will flag something as being malicious, and This is why you can get false positives sometimes because the patterns have been matched, but that doesn't necessarily mean ahead of time that whatever you're looking at is inherently malicious in nature. 
  * 而其他的网络安全工具，例如在选择检测系统时，会在执行深度分组检测时使用静态分析。And other network security tools, for example in choosing detection systems will use static analysis when they are performing deep packet inspection, for instance.

# 静态分析工具：Examples of Static Analysis Tools

![](/static/2022-01-31-21-36-17.png)

好了，现在有各种工具存在。其中有些是专有的，有些是开源的，但我相信其中相当多的工具是以学生项目开始的。我认为Findbugs特别是作为一个学生项目开始的，它变成了一个宠物项目，然后变成了一个全功能的力量程序。对于静态分析，我们有像LVM这样的东西。Klein HP fortify吗？这些都是你可以在商业环境中使用的工具的例子，以便识别代码中的错误。因此，我们要做的是，我们要解开其中一些程序是如何在引擎盖下工作的，在这个幌子下，我们要把我们自己的静态分析程序放在一起，因为你可以很容易地使用这些工具，你可以阅读输出，你可以从输出中进行分析工作。但是，我今天想做的是在技术上深入了解这些分析器的工作原理，因为我们的想法是，如果你了解这些工具是怎样的，这些工具是怎样工作的，而不是在引擎盖下，那么当然这将使你比那些仅仅使用这些工具而不一定知道这些工具在引擎盖下是怎样工作的人处于更好的地位。OK, so there are various tools in existence. Some of them are proprietary, some of them are open source, but a fair number of these I believe started off as student projects. I think Findbugs specifically is something that started as a student project that turned into a pet project that turned into a fully featured force program. For static analysis we have things like LVM. Have Klein HP fortify? These are all examples of tools that you can use in a commercial setting in order to identify where bugs are in the code. So what we're going to do is we're going to sort of unpack how some of these programs work under the hood, under the guise that we are looking to put together our own static analysis program, because you can easily use these tools, you can read the output and you can sort of work on the analysis from the output. But what I want to do today is to take a technical dive into how some of these analyzers work, because the idea is that if you understand how these tools are, how these tools work, rather under the hood, then of course that will put you in better standing than people who are simply using the tools without necessarily knowing how the tools are working under the hood.

# 静态分析如何有效？What does Static Analysis do

## Halting Problem

![](/static/2022-01-31-21-38-00.png)

静态分析是如何做好的？ 停顿问题是现有的最著名的计算科学问题之一。它在图灵论文中被讨论过。这个想法是，**我们想尝试写一个分析工具，可以证明任何一种程序和所有的输入，程序是否会终止**。有某种分析工具？程序是否会终止，所以这里的这个问题已经被证明是在侧壁。**我们不一定有任何一种算法存在，可以为所有潜在的程序和所有潜在的输出解决它**。现在，停止问题与静态分析的概念有关，具体来说，**就是停止问题的不可解性适用于静态分析，因为。当涉及到识别某个东西是否会成为一个bug或者某个东西会有问题时，最大的挑战之一是当你在设计时编写它时，当你创建它时，你不一定了解该程序将在什么相关的使用环境下运行**。 how does static analysis work well? the halting problem is one of the most famous computing science problems in existence. It was discussed in the Turing thesis. The idea is that we want to try and write an analysis tool that can prove for any kind of program and all of the inputs into it, whether or not the program will terminate. So we have here our program we want to. Have some kind of analyzer? Will the program terminate, so this problem here has been proven to be on the side wall. We don't necessarily have any kind of algorithm in existence that can solve it for all potential programs and all potential outputs. Now the halting problem is relevant to the notion of static analysis, and specifically it is the undecidability of the halting problem that is applicable to static analysis because. One of the biggest challenges when it comes to identifying whether or not something is going to be a bug or something is going to be problematic is that when you're writing it at design time when you're creating it, you don't necessarily understand the associated usage context that that program is going to operate under. 

## 静态分析原理

![](/static/2022-01-31-21-54-45.png)

- 静态分析是试图**预测输入到源代码中的代码问题**的算法。
- 它们试图解决确定一个给定的变量在代码中的可信赖程度这一不可确定的问题。
- 它们通过**识别源代码中的模式**来做到这一点。
- **利用模式**，分析者可以**通过预测程序的行为来确定其安全问题**

例如，如果你正在写一个方法，这个方法负责**接受某种变量作为输入**，它将构建某种SQL查询，以便在某种数据库后端执行该查询，**你并不提前知道你可以期待什么样的输入进入该方法主体本身【模块化编码**】。For example, if you're writing a method and this method is responsible for taking some kind of variable as input and it will construct some kind of SQL query in order to execute that query on some kind of database back end, you don't know ahead of time what kind of input you can expect into that method body itself. 

* 现在从设计的角度来看，你有一个理解。关于那个方法，你知道你应该对输入做什么吗？你知道你想接受它。你想构建某种查询，你想在你正在创建的任何数据库后端API上执行该查询。或者你正在自己创建API，但任何一种输入参数该方法的挑战对我们来说都**很难在整个程序中进行算法追溯【输入不可预测**。Now from a design perspective, you have an understanding of. About that method should do you know what you should do with the input? You know that you want to take it. You want to construct some kind of query you want to perform that query on whatever database back end API that you are creating. Or maybe you're creating the API yourself, but the challenges that any kind of input parameter into that method is very difficult for us to algorithmically trace back throughout an entire program. 
  * 要详尽地了解这个变量是如何被使用的，**这是一场失败的战斗，尤其是在非常大的模块化代码基础上，现在以【模块化的方式编码】的优势是，当然是可用性、可理解性和可扩展性，通过各种方式，这是一个非常好的软件工程实践**。To understand exhaustively how that variable can be used, it's a losing battle on especially very large modular code base is now the advantage with coding something in a modular fashion is, of course re usability, understandability and scalability, and by all means it is a very good software engineering practice to use.
  * 但这种工程实践的缺点是，**它引入了不可知的概念**。But the disadvantage with that engineering practice is that it introduces this notion of undecidability. You don't know how these different modules are going to be communicating with each other at runtime. You don't necessarily know what the value of different input parameters are going to be at runtime. It may be the case that it is something that we are expecting that it could be normal, but at the same time it could be malicious in nature. We don't necessarily know, 
    * **你不知道这些不同的模块在运行时是如何相互交流的。你不一定知道不同的输入参数在运行时的值是什么**。可能是我们所期待的东西，它可能是正常的，但同时它也可能是恶意的。
* 所以静态分析的想法是，我们想尝试**了解程序如何处理变量，并了解【是否会以一种可能被认为具有风险的方式使用某些东西】，这使我们能够带头进行分析为了了解我们可能希望在哪里引入消毒剂等【缓解措施**】。所以我们想尝试在代码中<font color="deeppink">识别这些模式。我们希望将一系列规则应用于我们的代码，以了解这种危险行为可能或正在出现的位置，以便对代码库执行某种清理</font> so the idea with static analysis is that we want to try and understand how Programs behave with variables and order to understand whether or not Something will be used in a manner that could be considered risky in nature, and that allows us to spearhead our analysis in order to understand where we may want to introduce mitigation actions like sanitizers. So we want to try and identify these patterns in code. There are a series of rules that we want to apply to our code in order to understand where this risky behavior can be or is being exhibited in order to perform some kind of sanitization on the codebase. 

# 静态分析局限性&存在的问题：Challenges & issues

- **完美的分析是不可能的**。A perfect analysis is not possible.
- 不确定。**分析有可能永远不会终止**。Non determination. Possible the analyser will never terminate.
- 虚假警报。**报告的漏洞不正确**。False alarms. Reported vulnerabilities are not correct.
- **遗漏**的错误。**没有报告并不意味着没有任何东西可以报告**。Missed errors. The lack of reports doesn’t mean there isn’t anything to report.
- 尽管这样，有用的分析还是可能的。Despite this, useful analysis is possible.

---

静态分析的挑战在于，没有一个完美的静态分析器。它有三个主要问题。 the challenge with static analysis really is that perfection is impossible. There's no such thing as a perfect static analyzer. There are three major issues with it.

* **不确定**是其中之一。分析器有可能永远不会终止。**这在非常大的、复杂的软件系统上尤其如此**。我们将在一分钟内谈论一个给定的静态分析器的两个关键权衡。 Non determination is one of them. It's possible that the analyzer may never terminate. This is especially true on very large, complex software systems. We'll talk about in just a minute the two key tradeoffs for a given static analyzer.
* 虚假警报是另一个问题。例如，对于那些以前使用过IDE的人来说。有可能IDE将你的代码标记为有问题，但你提前知道该代码不一定会有问题。这是一个静态分析规则的例子，你的ID正在遵循这个规则。分析你的代码，并且是告诉你，嘿，我认为你写的东西是有问题的，或者我认为你写的代码是不可达的，或者类似的东西。就是说，一系列的规则。静态分析器正在检查，以便在环境本身中为你这个开发者提供一些反馈。但这也是一个错误警报的例子。因此，**即使你的静态分析器可能会报告某些东西是错误的，但它实际上可能不是错误的**。**当然，这也是为什么我们不一定能完全自动化的原因**，也是为什么我们作为安全分析师的工作是相当安全的，至少对于接下来的事情也是如此。 False alarms are another issue. With those of you, for example, who have used an IDE before. It's possible that the IDE may have flagged your code as being problematic, but you know ahead of time that the code is not necessarily going to be problematic. This is an example of a static analysis rules that your ID is following. Analyzing your code and is telling you, hey, I think that what you're writing is buggy or I think that the code you're writing is unreachable or something to that effect. That is, a series of rules. That are static analyzer is checking in order to provide you the developer some feedback within the environment itself. But it's also an example of a false alarm. So even though your static analyzer may report something is being buggy, it may not actually be buggy. And of course, that is why we cannot necessarily fully automate the process and why our jobs as security analysts are pretty much secure, at least for the next thing also.
* 当然，我们也有**遗漏的错误**，报告也不一定能找到bug。 Of course, we also have missed errors, and the reports may not necessarily find bugs.

# 静态分析健全性，完整性权衡：Balancing Act of Static Analysis

- 健全性。Soundness
  - 如果分析报告了一个漏洞，那么就存在一个漏洞。If an analysis reports a vulnerability then there is a vulnerability.
- 完整性。Completeness.
  - 如果有漏洞可寻，那么分析就会发现它。If there is a vulnerability to find then the analysis will find it.

---

当然，静态分析器识别和理解bug的能力又**取决于静态分析器所遵循的规则有多少**。

* 我们可能有一个非常简单的静态分析器，它能够在相当快的时间内将大量的代码切碎，但它可能会错过一些复杂的bug。
* 同样地，我们可以有一个静态分析器，它非常。复杂，因为它在这个分析中使用了很多规则，但它不会很快通过代码，我想这就把我们带到了这里的主要权衡。

我开始的分析器被说成是健全或完整的。现在，**健全性与正确性的概念密切相关**。当然，我们的想法是，如果你正在使用某种静态分析器，你希望能够信任该静态分析器。只是告诉你，**你希望能够接受它所说的并相信它是真的**。因此，这个想法是，如果你的代码中有一个bug，比如说你在某种代码库上工作，在第63128行有一个bug，静态分析器应该能够找到这个bug，并把这个特定的行作为一个bug报告给你。这就是这个健全性的概念。 Of course, the static analyzers ability to identify and understand bugs again is dependent on how many rules the static analyzer is following. We may have a very simple static analyzer that is able to chunk through a lot of code and a pretty quick time, but it might miss some complex bugs. Likewise, we can have a static analyzer that is very. Complex because it's using a lot of rules in this analysis, but it won't be able to pass code very quickly, and I suppose that segues us into the main tradeoffs here. I started analyzer is said to be either sound or complete. Now soundness relates closely to the notion of correctness. Of course, the idea is that if you are using some kind of static analyzer, you want to be able to trust what that static analyzer. Just telling you want to be able to take what it's saying and believe it to be true. So the idea is that if there is a bug in your code, say you're working on some kind of code base and there's a bug on line 63,128, the static analyzer should be able to find that and report that specific line to you as a bug. And that is this notion of soundness.

所以这个想法是如果你正在组装一个静态分析器。想要在分析中有一定程度的合理性，这里有视觉表示。简单地说，我所说的东西被封装在这些真实的东西中。所以这本质上意味着**我们的代码库中存在一些非错误**。如果我们神奇地知道它们在哪里，那是一组真实的事物，而我看到的事物本质上就是这些静态分析器提出的错误。对于他们**静态分析器能够正确报告的错误越多，内部分析就越合理** So the idea is if you're putting together a static analyzer. Want some degree of soundness in the analysis and there's visual representation here. Simply states that the things that I say are encapsulated within these set of true things. So what this essentially means is that there are some non bugs that exist in our code base. If we go to magically know where they are, that is the set of true things and the things that I see are essentially the things that these static analyzer propose as being bugs. For the more bugs that they static analyzer is able to correctly report the more sound the analysis as internal. 

我们也有这种完整性的概念。**这个想法是，如果在代码的某个地方有一个错误，静态分析器应该能够找到它**。 And we also have this notion of completeness. The idea is that if there is a bug in the code somewhere, the static analyzer should be able to find it.

当我们把任何种类的静态分析放在一起时，这是两个关键的矛盾，现实地说，**一个理想的静态分析器是一个既完整又健全的**。No, these are two key tensions when they when we're putting together any kind of static analysis.

* 它应该能够通过某种代码库中的每一行代码，而且它应该能够向你正确报告所有的错误，但这在很大程度上是一种幻想。Now these are two key tensions and realistically speaking an ideal stanick analyzer is one that is both complete and sound. It should be able to pass every single line of code within some kind of code base, and it should be able to report all of the bugs correctly to you, but this is very much a fantasy.
* 不幸的是，这不是存在的东西，当我们使用工具时，它们往往会左右摇摆于健全性或完整性，并试图将静态分析工具放在一起，从而找到你想要覆盖多少代码与多少代码之间的最佳平衡点。你的分析有多准确？那是静态分析社区中一个活跃的研究领域，所以它可能是你倾向于看的东西，我们在这里陈述了两种学术声明，如果它没有报告任何东西，分析是三重合理的，**所以你的静态分析器如果没有报告任何错误，从技术上讲没有错**。所以我认为它在技术上是正确的， It's not something that exists unfortunately, when we are using tools, they tend to Sort of Sway to either soundness or completeness and trying to put together a static analysis tool that sort of finds the sweet spot between how much code do you want to cover versus how accurate is your analysis? That is an active research area within the static analysis community, so it might be something that you are inclined to look at and there are two sort of academic statements here we stated and analysis is triply sound if it doesn't report anything, so your static analyzer if it doesn't report any bugs, it's technically not wrong. So it's technically correct I suppose,
* **如果它声明每一行代码都是一个错误，那么它就是微不足道的**。因为再一次，理论上，如果它声明一切都有问题，它会发现问题，但你可能会理解这一点。静态分析仪这个琐碎的声音或琐碎的完成。我想它对我们没有任何实用意义，对测试很有用，但是当你组装一个静态分析器时，你当然会希望它表现出一些正确的健全性和完整性的属性。 and something is trivially complete if it states that every single line of code as a bug. Because again, in theory if it states that everything is problematic, it will find the problems, but you can probably appreciate that. Static analyzer this trivially sound or trivially complete. It's not useful for us in any kind of pragmatic sense, useful for testing, I suppose, but when you're putting together a static analyzer, you will of course want it to exhibit some true properties of soundness and completeness.

---

## 理想静态分析器 perfect analysis

![](/static/2022-02-01-14-30-13.png)

所以这又回到了我之前所说的。这是一个理想化的场景，我们有一个既完整又健全的静态分析器。这并不存在，但这是任何一种静态分析程序的圣杯，很多关于创建静态分析程序的研究旨在尝试实现这一点，所以如果你能做到这一点，你将拥有一个非常宝贵的产品。 So this sort of comes back to what I was saying previously. This is the idealistic scenario whereby we have a static analyzer that is both complete and sound. This does not exist, but this is the Holy Grail of any kind of static analysis program and a lot of research on creating static analysis programs aims to try and achieve this so if you're able to do this you will have a very valuable product indeed. 

## 权衡

![](/static/2022-02-01-14-32-38.png)

**要么健全，要么就是完整的**。更完整的静态分析器通常是最好的复杂性分析本身，因为直观地认为，**我们在这些静态分析本身中使用的规则越多，分析就越准确**。因此，**我们增加了健全性，但同时也减少了程序的完整性**，因为我们不希望 相反，重要的是我们**能够在某种合理的时间范围内完成静态分析**，然后是专业的软件开发环境。你不一定有闲情逸致坐在那里好几天，比如说，等待你的静态分析器完成，以便向你报告某种bug。你根本没有那么多的时间，所以有一些东西是介于健全性和完整性之间的。这就是为什么很多人都这样做。There's either sound or it is either complete. The more complete static analyzer is usually the best complexity analysis itself is because it's intuitive to think that the more rules that we are using in these static analysis itself, the more accurate the analysis will be. Therefore, we increase the soundness, but we decrease Simultaneously, the completeness of the program because we don't want Rather, it's important for us to be able to complete the static analysis within some kind of reasonable time frame, and then a professional software development environment. You don't necessarily have the luxury of sitting there for days, for instance, waiting for your static analyzer to complete in order to report some kind of bugs to you. You simply don't have that luxury of time, So having something that sort of falls between soundness and completeness. That is why a lot of them do that. 

# Summary

- 构建静态分析工具通常需要考虑健全性和完整性之间的权衡。•	Building static analysis tools typically involves the consideration of the trade-offs between soundness and completeness.
- 设计师经常要考虑。•	Designers often have to consider:
  - 精确性。•	Precision.
  - 可扩展性。•	Scalability

# ==================

# Outcome2

- 了解作为静态分析的一种形式的污点信息流分析。Understand tainted information flow analysis as a form of static analysis.
- 知道如何通过使其对上下文、流量和路径敏感来提高基本分析的精度。Know how to increase the precision of basic analysis by making it context, flow and path sensitive.
- 安全实践中的污点信息流。Tainted information flow in security practice.

# 软件安全分析-确定程序安全性（攻击面）, 污点分析：Intro

3步法解决注入输入攻击等

- 在软件安全分析中，有三件事涉及到确定一个程序的安全性。There are three things involved in software security analysis to determine the safety of a program.
    1. 攻击面的分析。Analysis of the attack surface.
    2. 攻击面的最小化。Minimisation of the attack surface.
    3. **输入验证机制**的应用。Application of input validation mechanisms.
- **污染流分析有助于确定一个程序的攻击面**。Tainted flow analysis helps to determine the attack surface of a program.

---

例如，SQL注入是网络安全的典型例子之一，很多都是通过错误的代码促成的。这个想法是，你在代码库的某个地方有某种模块，假设它正在接受某种受信任的数据，并且正在对这些受信任的数据进行操作。**当涉及到测试软件时，测试可能会被清除。我们想确保这个特定的SQL查询在我们得到它的输入时能够工作**，而且可能是这样的情况，**我们不一定考虑到什么可能出错**。你经常期待着某种可信赖的输入。这就是你所期待的，你将对该特定的输入进行操作。但是，**网络安全中的很多问题很大程度上来自于某种组件所接收的输入，而这种输入不一定是我们所期望的**。So SQL injection, for example, is sort of one of the poster child examples of cyber security in general, and a lot of that rather is facilitated through buggy code. The idea is that you have some kind of module somewhere in a code base that assumes that it is taking in some kind of trusted data and is performing operations on that trusted data. when it comes to testing the software, the tests probably cleared. we want to ensure that this particular SQL query works when we get it as input, and there it may be the case that we're not necessarily thinking about what could possibly go wrong. the idea is that when you have multiple modular operations, you have multiple modular components in a software program. You're often expecting some kind of trusted input. You're often expecting input, that is what you are expecting, and you will perform operations on that particular input. But a lot of the problems in cyber security largely come from input that is being received by some kind of component that is not necessarily what we are expecting.

因此，例如，任何一种游戏，例如在于GPS导航。假设任何一种被报告给它的GPS坐标都是正确的，但不一定是，例如，捏造的输入，但你**很容易拦截**。GPS 坐标将简单地自己制造坐标并将它们输入到程序中，因此程序本身的行为符合设计。它正在做它设计要做的事情，要做的事情，但是你通过假装你在世界其他地方来给它恶意输入，例如，Pokémon Go就是一个例子，你可以操纵你在世界的位置，以改变程序的内容。游戏为您做的事情知道这是一个相当学术的例子，没有现实世界的后果。例如，它在本质上不一定是恶意的，但原则仍然是一样的。So for example, any kind of game, for example that lies on GPS navigation. Assumes that any kind of GPS coordinates that are being reported to it are correct and are not necessarily, for example, fabricated. but it's very easy for you to intercept. The GPS coordinates will simply manufacture the coordinates yourself and feed them to the into the program, so the program itself behaves as designed. It is doing what it's designed to do, what is built to do, but you are feeding it malicious input by pretending you're somewhere else in the world .Pokémon Go, for example is 1 instance, whereby you can manipulate where you are in the world and order to change what the program. What the game is doing for you know this is a fairly Academic example is has no real world consequence. For example, it's not necessarily malicious in nature, but the principle remains the same.

这个想法是我们有某种程序。它期待某种程度的输入，但它可能会收到一些恶意代码，告诉它做一些我们不一定提前预料到的事情，各种注入攻击，代码注入攻击就是一个例子。The idea is that we have some kind of program. It is expecting some degree of input, but it might receive some malicious code that is telling it to do something that we're not necessarily expecting ahead of time, and various injection attacks, Code injection attacks are an example of that.

所以我们要做的是，我们要在这里解开一个三步法，我们将在后面解开，以了解这些漏洞可能出现的地方。所以它允许我们**对攻击面进行静态分析**。因此，我们想具体**了解代码中的漏洞在哪里，这基本上是我们的攻击面，我们想尽量减少这个攻击面**。例如，通过引入消毒剂或我们想执行某种缓解行动。因此，<font color="deeppink">**污点分析**是我们将在此处解压缩的特定静态分析技术，它允许我们通过分析我们放在一起的代码来**考虑数据在运行时是如何传播的**</font>。So what we're going to do is we're going to unpack a three step approach here that we will unpack later on in order to understand where these vulnerabilities may arise. So it allows us to perform an analysis of the attack surface static analysis. So we want to understand specifically where the vulnerabilities are in a code, and that essentially is our attack surface, and we want to try and minimize that attack surface. By introducing sanitizers or we want to perform some sort of mitigation action, for instance. So tainted floor analysis is the specific static analysis technique that we're going to unpack here, and it allows us to consider how data propagates at runtime by analysis of the code that we put together

# 两种流-污点流分析：Tainted Flow Analysis

![](/static/2022-02-01-15-25-27.png)

因此，**流分析跟踪值如何在不同的内存地址和程序之间流动**。有两种不同的数据流存在【这是两种主要的方式，数据可以在某种应用代码中传播 These are the two main ways by which data can propagate within some kind of application code】，So flow analysis tracks how values may flow between different memory addresses and program. And there are two different data flows that exist within when we're talking about a team full analysis,

* 我们有**显式流/数据流**，它涉及到程序代码中的**可观察变量的信息分配**。因此，如果我们有例如左下方的例子。正在发生的事情是，我们正在保留一些内存地址或某种内存偏移，我们正在将原始的0放入该内存地址。同样，当我们创建int B时，我们也在做同样的事情，我们在内存中初始化可用的内存，然后我们将内存中的变量估值为a的值。 所以这是一个显式数据流的例子，因为我们**直接创建了内存地址空间，我们将变量放在这个内存地址中**  we have explicit flow dataflow that concerns the allocation of information to observable variables within program code. So if we have for instance the example here at the bottom left. And a = 0. What is happening is that we are reserving some memory address or some kind of memory offset and we are placing the primitive 0 into that memory address. Likewise, we're doing the same when we create int B we are initializing memory available in memory and then we are valuating that variable in memory to be the value of a. So this is an example of explicit dataflow, because we are directly creating memory address space and we are placing variables inside of this memory address space and valuating them. 
* 现在我们也有所谓的**隐式流/信息流**，这**涉及到控制流所产生的分配**。所以我们在这里可以看到，比如说我们像以前一样有a=0，但是如果我们想进行某种条件检查，我们想检查一下a是否是3，如果他是3，B被估值为5，否则，B被估值为7。Now we also have what is known as implicit flow or information flow, and this concerns the allocation resulting from control flow. So we can see here that let's say for instance we have a = 0 as before, but if we wanted to perform some kind of conditional check, we wanted to check to see if a is 3. If he is 3, B is valuated to be 5 otherwise, B is valuated to be 7.
  * 在这里的例子中，B的值取决于A的值，由于存在依赖关系，**B的值由控制流结构控制**。这是分析中隐含或信息流的一个例子。In this example here, the value of B is dependent on the value of A and because there is a dependency, the valuation of B is controlled by the control flow structure. This is an example of implicit or information flow within the analysis, 

## 数据/控制流例子

![](/static/2022-02-01-15-36-50.png)

因此，例子一。这里我们有数据流和信息流的实例。当我们在内存中创建我们的整数时，我们有数据流，当我们评估输出作为条件检查的结果与秘密的值时，我们有信息流。与明智的做法不同，我们在这里得到的是同样的东西。So example one. Here we have both instances of data flow and information flow. We have data flow when we are creating our integers in memory and we have information flow when we are evaluating output as a result of the conditional checks with the value of secret. Unlike wise, we get the same thing here. 

在例子二中，我们在评估secret和output时有数据流发生。因为我们正在内存中获取secret和output的值，我们把它们加在一起，然后这个版本的结果将从适当的寄存器转移到新形成的内存偏移量，这个偏移量正在为变量的估值而创建。这就是y这个产量作为数据流例子的原因，然后当然我们有信息流发生在事后，据此我们正在检查Y的变量状态，如果它是10，我们分配。数据到输出。With example two, we have data flow occurring when we are evaluating secret and output. y is an example of dataflow. Because we are taking the values of secret and output in memory, we are adding them together and then the result of that edition will be transferred from the appropriate registers to the newly formed memory offset that is being created for the valuation of the variable. that is the reason y This yield as an example of data flow, and then of course we have information flow that occurs after the fact whereby we are checking the the variable state of Y, and if it's ten we allocate. Data to output. 

# 定义-污点流分析

Flow analysis tracks how values might ‘flow’ between the different memory relocations in a program. 流分析追踪数值如何在程序中的不同内存重定位之间 "流动"

* Can help us identify vulnerabilities in software that are caused by trusting input that is not validated.**可以帮助我们识别软件中的漏洞，这些漏洞是由于【信任未经验证的输入】而造成的**
* Data that is not trusted is known as tainted. **不信任的数据被称为 "污点"**
* Data that is trusted is known as untainted. 信任的数据被称为未被污染的
* Various operations in programs will expect to operate on trusted data, but when tainted data is used where untainted data is expected, we may have a security problem. 程序中的各种操作都**希望对受信任的数据进行操作，但当污点数据被使用**于预期未受污染的数据时，我们**可能会有安全问题**

---

因此，污点流分析的想法是试图**识别，没有验证数据输入时，产生的错误**。这个想法是**在信息进入某种程序范围时对其进行标记，以考虑该程序将流向何处**，该数据将在某种代码库中传播，分析的关键目标本质上是沙盒代码，我们要尝试**让不信任的数据远离信任的数据**  So the idea with tainted flow analysis is to try and identify bugs that arises when we are not necessarily verifying our data input. The idea is to sort of tag information as it comes into some kind of program scope in order to consider where that program will flow, where that data will propagate within some kind of code base, and the key objective with painful analysis is to essentially Sandbox code we want to try and keep data that we do not trust away from data that we do trust. 

* 因此，我们**不信任的数据通常被称为污点**，或者我们将其称为**污点数据或污点变量**，而被**信任的数据被称为未受污染的数据或我们控制的东西** So data that we do not trust is often known as tainted, or we refer to it as tainted data or tainted variables, and data that is trusted is known as untainted or something that we control.
* 这是**区分有污点和无污点**的关键。当我们进行污点分析时，**任何一种代码或任何一种变量，只要我们作为开发者可以直接控制，就是我们可以信任的东西**。 that is the key Distinction between what is tainted and what is untainted. When we are performing this analysis, any kind of code or any kind of variable that we as developers have direct control over is something that we can trust.
  * 因此，如果我们要创建某种程序，例如，它不一定与任何种类的外部模块合作，它不与任何外部API合作。它不与用户输入一起工作，它不与任何一种基于互联网的输入一起工作。**它在技术上将比任何一种将暴露在互联网上或将在某种程度上暴露给最终用户的程序更安全，因为我们不一定对输入到该特定程序的参数有那么多的控制【暴露互联网上的程序，暴露给用户的程序，外部API，缺少对输入的控制**】。 So if we were to create some kind of program, for example, that didn't necessarily work with any kind of external module, it didn't work with any external API's. It didn't work with user input, it didn't work with any kind of Internet based input. It's going to be technically more secure than any kind of program that will be exposed to the Internet or will be exposed to end users in some degree because we don't necessarily have as much control over the input parameters into that specific program. 

因此，一言以蔽之，**凡是我们自己控制的东西都被认为是可信赖的、没有污染的。任何我们不能直接控制的东西。从用户或任何类型的外部软件模块中获取的数据，在分析中往往被标记为有污点**。So in a nutshell, anything we control ourselves is considered trusted and untainted. Anything that we do not have direct control. Over that is elicited from users or any kind of external software module is often flagged as tainted in the analysis.

* 而我们要做的是，当这些**污点数据进入某种程序模块时，我们要尝试考虑它的去向，看它最终是否会评估某种未受污染的输入参数，因为很多操作和程序会期望在受信任的数据上进行操作**。 And what we want to do is we want to try and consider where this tainted data goes when it enters into some kind of program module to see whether or not it will eventually evaluate some kind of untainted input parameter because a lot of operations and program will expect to operate on trusted data.
  * 例如 SQL。我们正在组合的处理代码将期望某种可信数据作为输入，并且它将使用该可信数据对数据库执行操作。 For example the SQL. The handling code that we're putting together it will expect some kind of trusted data as input into it, and it will perform operations on the database with that trusted data. 
  * 因此，我们在**静态分析**中想要做的是考虑在何处或**是否有可能使用受污染的数据来评估 SQL 查询**，以便让我们认为 OK，我们可能需要一些缓解措施此句柄内的操作或一些**缓解代码应该对输入执行适当的清理**。So what we want to do in static analysis is to think about where or whether or not there is a possibility that tainted data could be used to evaluate that SQL query as it comes through in order to make us think OK, we might need some mitigation Action or some mitigation code inside of this handle, ought to perform appropriate sanitization of the input. 

# Tax计算器例子：Tax Calculator Example

这里的这段代码，或者只是为了存在。帮助我们了解静态分析，以及静态分析如何在污点分析而不是流分析上发挥作用 在我们的代码库上发挥作用。 This code here, or simply exists in order to. Help us learn about static analysis and how static analysis works on tainted analysis rather than flow analysis Works on our codebase. 

![](/static/2022-02-01-15-58-22.png)

# 类型限定符-使用类型的信任规范:Trust Specification using Types

type qualifier

![](/static/2022-02-01-16-03-04.png)

- 根据**对象的计算来源**，指定对其的信任。Specifying trust on objects based on the origin of their valuation.
- **信任的程度被指定为一个类型限定符**。The level of trust is specified as a type qualifier.
  - 污染的意味着信息可能被攻击者控制。Tainted means info may be controlled by an attacker.
  - 无污点意味着信息被认为不会被攻击者控制。Untainted means info that is assumed not to be controlled by an attacker.

---

所以我们可以看到，我们在这里有两个标签。我们有污点红色，我们有未受污染的绿色。**我们自己控制的任何东西都被认为是未受污染的，因此是值得信任的。所以我们的想法是，getBand()。也许是一个方法，。算是存在于程序中某种预定的范围内。我们知道它将向我们的大程序返回某种可信的输出，而它反过来也会接受某种可信的数据**。so we can see here that we have two labels here. We have tainted and red, and we have untainted and green. anything that we control ourselves is considered untainted and therefore trusted. So the idea is that getBand(). Maybe a method that. Sort of exists within some kind of predetermined scope within the program. We know that it will return some kind of trusted output to our larger program and it will in turn take some kind of trusted data. 

* 现在，具体来说，你想把什么标记为可信的和不可信的，很大程度上取决于你自己对代码的经验和。现实地讲，这也将在很大程度上取决于项目。 Now specifically what you want to label as trusted and untrusted largely comes down to your own experience with code and. Realistically speaking, it will also be very much project dependent .
  * 这里不一定能说明你想如何标记你将会遇到的任何种类的方法，而是为了学习而在这里。例如，你会想**把任何类型的外部API调用当作不受信任的，因为你无法控制它们**。 Here is not necessarily illustrative of how you would want to label any kind of method that you will ever come across, but rather it's here for the sake of learning. You will want to, for example, treat any kind of external API calls as untrusted, because you have no control over them. 
  * 但是，当涉及到识别什么是可信的时候，**经验法则是如果它是你控制的东西或你直接创造的东西，那么你可以认为它在本质上是可信的**。But the rule of thumb when it comes to identifying what is trusted is if it is something that you control or something you create directly, then you can consider it to be trusted in nature. 

所以这里的想法是，我们要我们有两个方法，我们有金额，这是getinput()的结果，get input在这里将返回某种有污点的输出，它将被用来评估这个变量的金额。所以getinput()没有说明，但我们可以假设它可能连接到互联网，或者它甚至可能提示最终用户输入某种信息并返回。 So the idea here is that we want to we have two methods here we have amount which is the result of getinput() get input here is going to return some kind of tainted output which will be used to evaluate this variable amount. So getinput() isn't illustrated, but we can assume for example it might connect to the Internet or it might even prompt the end user for some kind of input and return that. 

与此不同的是，我们还有getBand()，这是get band的结果。这里的方法调用。我们还有一个tax。tax还没有被标记为任何东西，一会儿就会回到这个想法或原因上了。所以你可能会问，**为什么不立即对这个进行简单的消毒？好吧，静态分析的全部意义在于确定代码中的问题所在**。我可以给大家讲讲消毒器，但现实地说，你的消毒器的样子在很大程度上还是取决于你正在做的项目。消毒器的功能意味着一定要有相同的外观。你也许想使用预先创建的消毒器库，但这些讲座的全部意义不一定是让你去思考如何使用。消毒器，**而是让你思考在什么地方要使用消毒器，或者为什么在代码中使用消毒器会很重要**。 Unlike wise, we also have getBand() which is the result of the get band. Method call here. And we also have a tax. tax isn't labeled as anything yet and will come back to the idea or the reason why in just a moment. So you might be asking why not simply sanitize this immediately? Well, the whole point of static analysis is to identify where the problems are in the code. Could have a lecture on sanitizers, but realistically speaking how your sanitizer will look will largely be dependent again on the project that you're working on. sanitizer functions mean necessarily look the same. You may want to use pre created sanitizer libraries perhaps, but the whole point of these lectures is not necessarily to get you to think about how you use. Sanitizers, but it's to get you to think about where you would want to use sanitizers or why it would be important to use sanitizers in the code. 

因此，这里的想法是，**我们正试图阻止不受信任的数据与受信任的数据进行互动，而这个例子在这里失败了，因为当我们对税收进行估价时，受污染的数据和未受污染的数据彼此相遇**。So the idea here is that we are trying to keep the untrusted data from interacting with the trusted data and this example here fails because the tainted and the untainted data meet one another when we are valuating tax. 

# 流分析要点&合法流/非法流：The point of Flow Analysis

![](/static/2022-02-01-16-28-57.png)

因此，对于所有可能的输入，我们要尝试并**证明污点数据永远不会被用于预期的未受污染的数据**。因此，我们想尝试并确保我们有这种水平的沙盒。因此，**当我们在代码中寻找解决方案时，我们想尝试并推断出代码中的流动方式。数据是如何具体流入变量的**，

* 这本质上意味着我们**是否有任何类型的不受信任的数据最终会被重新分配给不同的变量，或者影响不同的变量？这些不受信任的数据最终会评估我们想要在静态分析中监控的某种输入吗？**

而我们在讨论污点分析时，有两个术语是我们倾向于谈论的。那就是，**一个流是否合法，因此是允许的，或者是非法的，因此是不允许的**。So for all possible inputs, we want to try and prove the tainted data will never be used where untainted data is expected. So we want to try and ensure that we have that level of sandboxing. So when we are looking for solutions in the code, we are wanting to try and infer how flow within that code. How data flows into variables specifically, and what this essentially means is do we have any kind of untrusted data that will eventually, as it is being reassigned to different variables, or is influencing different variables? Will this untrusted data eventually evaluate some kind of input that we want to monitor in static analysis? And we there are two terms that we tend to talk about when discussing tainted analysis. That is, whether or not a flow is legal and thus permissible or is illegal and therefore not permissible.

# Sound Analysis-合法流:健全性分析

- 如果分析发现**没有非法流，而且确实没有，那么分析就是合理的**。换句话说，该分析是正确的。If the analysis finds no illegal flows and there are indeed none, then the analysis is sound. In other words, the analysis is correct.

![](/static/2022-02-01-16-35-22.png)

在合法的例子中，我们有一个**方法void F，它接收某种整数，被认为是不可信任的**。但我们也有下面的方法。整数A，这是我们自己创建的，因此可以认为是可信的。我们正在调用那个函数，该函数期望用实际上是可信的数据来获取不信任的数据。现在的想法是，**如果一个方法期望的数据是不被信任的，那么给一个被信任的数据也可以**。因为在理论上。这个**F方法。我们会在其中有一些缓解动作，比如说，或者说，是否有恶意代码被传入这个方法，可能并不重要。他们可能不会做任何有意义的事情来影响一个特定程序的安全**。但我们的想法是，如果你期待的是不可信任的数据，而你得到的是可信任的数据，传入也没问题。 with the legal example, we have a method void F which takes some kind of integer which is assumed to be untrusted. But we also have underneath. And integer A which we are creating ourselves and can therefore be assumed to be trusted. And we are calling That function that is expecting untrusted data with data that is actually trusted. Now the idea here is that if a method expects data that is not trusted, then it's OK to give a data that is trusted. Because in theory. This void F method. We'll have some kind of mitigation action within it, for instance, or it may be inconsequential whether or not a malicious code is passed into this method. They may not do anything of significance to impact the security of a given program. But the idea is that if you're expecting untrusted data and you get trusted data, then that's perfectly fine. 

但是，在相反的情况下，我们有一个**期待可信数据的G方法**。所以这可能是我们的SQL处理代码。也许不是，因为它**只接受一个未受污染的整数输入**。但这里的想法是，我们有一个正在被评估的整数B，但它是不被信任的，然后我们**调用G并传入这个不被信任的变量B**。因此，期待着可信数据的G，可能会被传入恶意数据，这是一个**非法流**。 But to take the opposite case, here we have void G which is expecting trusted data. So this could be for example our SQL handling code. Maybe not because it's simply taking an untainted integer input. But the idea here is that we have an integer B which is being evaluated, but it is not to be trusted and then we call G and pass in this variable B which is on which is not to be trusted. So G which is expecting trusted data which is expecting good data could be passed malicious data and that is an illegal flow.

*  在另一个例子中，G期待的东西是好的，是我们控制的。但它可能会被传递一些恶意的东西。它可能被传递了垃圾，这在**运行时可能是一个问题**。简而言之，这就是我们**在静态分析中想要识别的东西。我们要识别这些实例，以便为我们的分析打头阵。当涉及到识别像这样的错误和应用消毒剂时**。 something we control and on the other example G is expecting something which is OK, which we control. But it could be passed something malicious. It could be passed garbage and that could be a problem at runtime. And this is in a nutshell, what we're looking to identify in static analysis. We want to identify instances of this in order to spearhead our analysis. When it comes to Identifying the bugs like this one and the application of sanitizers, 
* 具体来说，你想如何处理这个问题取决于你。这取决于程序，项目的背景。相反，当你在专业背景下处理代码时，你发现自己身处其中，但这从根本上说是在引擎盖下。你的**静态分析器是如何工作的。他们正在寻找识别代码中的一个模式**，以报告给作为开发者的你。**为什么这个功能和这个输入会有问题？你想对它做什么**？这不会有问题。我们将忽略它，或者这将是一个关键任务。我们需要尽快用消毒剂来解决这个问题。specifically how you want to deal with this problem is up to you. It's dependent on the program, the project context. Rather, you found yourself in when you are working on code in a professional context, but this is fundamentally under the hood. How your static analyzers are working. They're looking to identify a pattern in code to report to you as a developer. Why this function with this input could be problematic? What do you want to do about it and on review it may be the case that OK? This isn't going to be problematic. We'll ignore it or this is going to be mission critical. We need to fix this with sanitisers ASAP. 

# 网格模型表示法：Lattice Model Representation

我们可以把合法流视为一个格子 We can view permissible flows as a lattice where:

![](/static/2022-02-01-16-49-48.png)

- 告诉我们，有污点的信息永远不会流入无污点的环境中。Tells us that tainted information will never flow into untainted contexts.
- tainted ≤ untainted意味着当有污点和无污点的变量都是等价的时候，不受信任的数据会流入受信任的变量。ainted ≤ untainted means untrusted data flows into trusted variables when both tainted and untainted variables are equivalent.
  - (因此是非法流) (Hence illegal flow)

---

我们可以正式地把**可允许的流**看作是一个晶格表示法，我们可以看到这里我们**有污点小于未污点`tainted<untainted`**，这基本上告诉我们的是，**未污点的数据永远不会和污点的数据一起被评估，所以在不被信任的和被信任的之间有一个明确的分离**，并且用大于符号`untainted>tainted`表示。We can formally rather view a permissible flow as a lattice representation, and we can see here that we have tainted less than untainted, and what this is essentially telling us is that. untainted data will never be evaluated with tainted data, so there is a clear separation between what is untrusted and what is trusted, and that is represented with greater than symbol.

非法流&合法流的概念，据此我们有`tainted<=untainted`的概念。~~`untainted>=tainted`~~大于或等于运算符向我们表明，将有一个实例，受信任的数据将与不受信任的或有污点的数据一起被评估。因此，这两者在某一点上会成为等价物，这就是我们想要识别的问题。因此，通过晶格表征，我们使用它来具体了解数据是如何流动的。 And the notion of legal flows or illegal flows here whereby we have tainted less than or equal to untainted. The greater than or equal to operator indicates to us that there will be an instance whereby trusted data is going to be evaluated with untrusted or tainted data. So the two will become equivalent at one point and that is the problem that we're looking to identify. So with the lattice representation we use that in order to understand specifically how data is going to flow. 

* `<=`表示，右边值将用左边变量值来计算，所以某点这两个变量的trusted state相等
  * `<`表示，右边永远不会被赋值跟左边一样的值。有明显的分离【trusted state永远不会相等】
* 因此，如果我们有一个整数 X，它将用于评估整数 Y，如果 X 是不受信任的，那么当我们使用 X 评估 Y.Y 时，turn 将变得不受信任。所以 y 将变得等价于 X，同样，Y 的可信状态将变为与 X 的等价状态。**所以我们在这里要做的是我们正在寻找识别可信数据反过来变为的实例不受信任，因为它正在被污染的输入数据或垃圾数据评估，因此我们得到了非法的流**。 So if we have an integer X which is then going to be used to evaluate integer Y if X is, say untrusted, then when we use X to evaluate Y.Y and turn is going to become untrusted. So y is going to become equivalent to X and likewise the trusted state of Y is going to become Equivalent to that of X. So what we're looking to do here is we're looking to identify instances whereby trusted data will in turn become untrusted because it is being evaluated by Tainted input data or garbage data, and hence we get our illegal flows.

# 源&汇：Sources, sinks

- 静态分析中的两个常用术语。

1.	来源--估值数据的来源。Sources – Where valuated data comes from:

* api, 无法直接控制的东西？污点来源
* 用来识别污点可能的来源

2.	汇--**在程序流中被【监控的一些变量**】。Sinks – Some variable that is monitored in program flow:

* 可能会被垃圾输入影响的数据

- **当进行静态分析时，你基本上是在看源是否流入汇**。When performing static analysis you are essentially looking to see if sources flow into sinks.
- 与我们一直在看的概念完全相同，但安全术语通常区分源和汇。Exactly the same concept as what we have been looking at, but security terminology often distinguishes sources and sinks.
  - 比如，污点数据是否会流入非污点的输入参数。然后，污点&非污点数据某点变成相等的

# Summary

- **污点分析**使我们能够通过**跟踪变量分配如何在内存中传播变量来考虑数据在代码中的流动**。Taint analysis allows us to consider how data flows in code by tracking how variable assignments propagate variables in memory.
- **我们信任的数据被认为是未受污染的**。Data that we trust is considered untainted.
  - 我们可以控制的数据是非污点。（经验法则）
- 我们**不信任的数据被认为是有污点的**。Data we do not trust is considered tainted.
- 我们**不确定的数据它是未知的**。Data we are not sure about it unknown.
  - 但我们将在下一次讨论这个问题。
  - 这种分析中，不一定要把所有东西都一定标记为污点，非污点。分析能告诉我们，之后这些未知变量会不会产生问题
- **静态分析的目的是通过预测信息流的性质来发现漏洞。**Static analysis aims to find vulnerabilities by predicting the nature of information flow.
  - 信息流：控制流
  - **寻找不受信任的数据与受信任的变量互动的情况**Looks for instances where untrusted data interacts with trusted variables.
  - 然后取决于项目上下文&经验，来根据分析结果，找出哪里需要修复，考虑是否需要缓解措施，

# ==================

# 静态分析步骤：Static Analysis Approach

type qualifier: tainted, untainted, 及其他的Geek（用于不知道某变量是否可信，未知的）

* 限定词一般用于识别信任的变量类型

---

主要是用来推断未知限定符，最后是否为污点？

- 把**流分析**看作是一种**类型推断**（如果没有限定词，我们必须推断出来）Think of flow analysis as a kind of type inference (and if no qualifier is present, we must infer it)
- 步骤。Steps:
1. 为每个**缺失的限定词创建一个名称**（如α，β）。Create a name for each missing qualifier (e.g. α, β)
   1. **不确定的变量**，标记为`α,β, ...`
   2. **迭代代码库，生成限定符**
      1. 污点，非污点，未知
2. 对于程序中的每一条语句，**产生对可能解决方案的约束**（形式为`𝑞1≤𝑞2`） For each statement in the program, generate constraints (of the form 𝑞1 ≤ 𝑞2) on possible solutions.
   1. 再次迭代，对语句生成约束
      1. `q1<=q2`，<font color="deeppink">包含在变量中被限定为 Q1 的数据将用于计算用于限定 Q2 的数据</font>  The data contained within the variable that is qualified as Q1 will be used to evaluate the data that is being used to qualify Q2. 
   2. (例如，语句`𝑥=𝑦`产生约束`𝑞𝑦≤𝑞𝑥`，其中𝑞𝑦是𝑦的限定词，而𝑞𝑥是𝑥的限定词。 (e.g. statement 𝑥 = 𝑦 generates constraint 𝑞𝑦 ≤ 𝑞𝑥 where 𝑞𝑦 is the qualifier for 𝑦 and 𝑞𝑥 is the qualifier for 𝑥
      1. 假设x, tainted. y untainted（反了：应该是x untainted, y tainted吧 = =我去你码的吧）.且`x=y`某点， 则 `tainted<=untainted`，推出`qy<=qx`。因为x 中的污染数据用于**限定** y 中的未污染数据 (tainted data in x is used to qualify the untainted data in y)
      2. **左边 - source，右边 - target**
      3. **分析会针对限定符本身**
      4. **如果某变量是污点，则限定符q是污点。反之亦然。不然q限定符为其他希腊字符：未知的**
3. Solve the constraints to produce solutions for 𝛼, 𝛽 etc.解决约束条件，得出𝛼、𝛽等的解决方案。
   1. 将希腊字符替换为污点/非污点限定符，或是将污点/非污点限定符替换为希腊字符。考虑最后是否存在污点流入非污点数据
   2. 解的时候，当数据继续传播，**最后看这些未知限定符是否最后会成为污点，非污点**。解出来这些约束之后，判断是否存在问题

## 例子

- 如果我们没有任何有污点的输入，那么我们可以很容易地说没有问题。If we did not have any tainted input then we could easily say there is no problem.
- 但我们想**推断在有不信任的输入的情况下是否有问题**。But we want to infer if there are problems in the event there is untrusted input.

![](/static/2022-02-01-20-36-46.png)

---

- 这里没有问题，因为**没有污点的数据源导致问题**。
- 这里的代码很简单，我们只需看一下就可以推断出正确的结论。

![](/static/2022-02-01-20-37-37.png)

* 假设移除污点限定符，把所有变量，方法限定为非污点
* 显然，tax是非污点

---

- 随着有**污点的数据源的存在**，它变得不那么清晰。
- 特别是在**大型代码库中**。
  - 分析会变得非常复杂。**不可能手动来推断**

![](/static/2022-02-01-20-39-14.png)

* 将限定符改为污点。tax最后可能会变成污点，因为tax是由污点&非污点数据计算得来的

# 分析例子：Example Analysis

![](/static/2022-02-01-20-44-25.png)

---

1

![](/static/2022-02-01-20-45-18.png)

* 生成限定符
  * 随时间，一开始只知道特定的部分的限定符
  * amount,band是未知的，标记为希腊限定符

---

2

![](/static/2022-02-01-21-05-01.png)

* 生成约束
  * `C1,C2,C3`
  * **注意表达约束用的是限定符，因为不在乎变量名是什么，只关心与变量相关的限定符**
  * C1是第一个逻辑数据流
    * 不可信数据被getInput()返回，然后这个不可信结果用来计算amount变量的值
  * C2
    * 调用getBand()，**先考虑local scope**
      * amount（`α`）作为实参传入形参`a`，假**设getBand中该形参是非污点（假设形参是可信的**），因此α被计算为非污点. `α<=untainted`
        * 注意这里只是一开始假设，形参是可信的，但随时间推移，可能getBand最后是不可信的，**这一点最后到底有没有问题是希望静态分析器能报告出来的，然后就能考虑到底要不要对getBand采用缓解措施**
  * C3
    * getBand(amount)返回非污点(看getBand的签名最前面的限定符是untainted),trusted data
    * 因此`untainted<=β`

---

3

`tainted<=a<=untainted<=b`

* tainted（getInput返回的）数据流入被限定为a的变量
* a流入getBand的形参（untainted）
* getBand非污点返回值流入β

![](/static/2022-02-01-21-40-53.png)

1. 我们可以看到，第一个约束条件要求𝑎 = tainted。We can see that the first constraint required 𝑎 = tainted.
2. 反之，要满足第二个约束条件，就是要让𝑎=未被污染。Conversely, to satisfy the second constraint is to have 𝑎 = untainted.

阿尔法既要被认为是未受污染的（作为输入参数传入getBand），也要被认为是受污染的（当被估价为getInput的返回值时）--这就是为什么没有解决阿尔法的问题。Alpha is to be considered both untainted (being passed into getBand as an input parameter) and tainted (when being valuated as the returned value from getInput) – this is why there is no solution for alpha.

* `α`约束无法解出，存在非法流
  * `α(tainted)<=untainted`，非法流

## 例子2

考虑在下面的HTTP请求/响应的实现中是否存在非法流 Consider whether there is an illegal flow in the following HTTP request/response implementation.

对所有变量形参先假设为非污点，自己创建的参数（字面量之类的）也看为非污点

* sink是最后一行的e变量。**想要找出是否会存在非可信数据流入变量e**

---

第一步 - 标记限定符，qualify constraints

![](/static/2022-02-01-22-00-13.png)

```java
// 实参“foo”可信。 假设 `request.getParameter`返回污点（外部API，没有过多控制）。把`a`变量标记为污点
String	a	=	request.getParameter("foo");
// a最后结果非运行时是不知道的，所以拼接结果未知，变量b标记为希腊字符
String	b	=	a + "bar";
// 非运行时b的可信特性trust state也未知，所以c标记为希腊字符
String	c	=	b.replaceAll("foo", "bar");
// 同理c特性不确定，d标记为希腊字符
byte[]	d	=	c.getBytes();
// 同理d的trust state也未知，标记e为希腊字符
String	e	=	new String(d, "UTF-8");
// e是要监控的sink，【最终想知道运行时期e是否会变成污点】
response.getWriter().println(e);
```

第二步 - 生成约束（statements）

* C1 = tainted(变量a假设是污点)<=α
* C2 = α<=β
  * 变量b拼接结果，用来计算最终变量c的值 （b.replaceAll）
* C3 = β<=γ
  * 变量c用来计算变量d的结果 （c.getBytes）
* C4 = γ<=Ω
  * 变量d用来计算变量e的结果
* C5 = Ω<=untainted(实参e,,要监控的sink)

第三步 - 结合，解约束 combination & solve

![](/static/2022-02-01-22-19-14.png)

* tainted<=α<=β<=γ<=Ω<=untainted
* 明显这个数据流最后会产生问题。污点数据通过线性路径传播
  * `tainted<=untainted`非法流

# Summary

- 分析方法是一个3步过程。The analysis approach is a 3 step process.
- 首先，我们对变量进行限定。First we qualify the variables.
- 其次，我们根据公式𝑞1& ≤ 𝑞2产生约束。Second we generate constraints based on the formula 𝑞& ≤ 𝑞2
  - 记住𝑞1和𝑞2的顺序是很重要的!Remember the ordering of 𝑞1and 𝑞2 is important!
- 第三，我们解决每个未知数的约束，以确定是否发生非法流动。Third we solve the constraints for each unknown to determine whether illegal flows occur.
- **在没有非法流动的情况下，该分析是健全的**。The analysis is sound in the event there are no illegal flows.

# ==================

- **静态分析帮助你确定程序代码的攻击面（漏洞位置**）。Static analysis helps you determine the attack surface of a programs code.
- 识别程序代码中的漏洞包括3个步骤。Identifying vulnerabilities in program code involves 3 steps:
  - 变量的限定。Qualification of variables.
  - 生成约束条件。Generation of constraints.
  - 解决约束条件，观察合法和非法流动。Solving of constraints and the observation of legal and illegal flows.
- 受信任的数据是未受污染的，不受信任的数据是受污染的。Trusted data is untainted, untrusted data is tainted.
- 未知数据被标记为𝛼，𝛽等。Unknown data is labelled 𝛼, 𝛽 etc.
  - 我们希望程序能够确定使用未知数据的后果。We want the program to determine the consequence of using unknown data.

# 添加路径敏感: Adding Sensitivity

C1和C2约束无法组合，因为α既要是untainted也要是tainted，因此分析中产生问题（分析器不知道具体α的值）

C1，C3约束组合后同理，非法流

这个分析不一定是健全的，因为报告了存在问题，但是是false alarm（假阳性）。因为直观的来看最后其实不存在问题

* 因为 后序 `amount=0`添加了rudimentary sanitiser，传入getBand方法。前面不管amount的值是什么都不会被传入使用，也不会影响后序sink

# 增加分析健全性-流敏感度：Flow Sensitivity

![](/static/2022-02-02-13-59-44.png)

- 到目前为止，该分析对流不敏感。Thus far, the analysis is flow insensitive.
- 每个变量都有一个确切的**限定词**，抽象出它所包含的所有值的**可信度**。Each variable has exactly one qualifier which abstracts the trustworthiness of all values it will ever contain.
- **因此，如果𝛼被认为是污点，那么𝛼总是污点**。So if 𝛼 is considered tainted, 𝛼 is always tainted.
- 一个对流敏感的分析会考虑到内容发生变化的变量。A flow sensitive analysis would account for variables whose contents change.
  - 允许我们为变量的每次使用分配不同的限定词。Allows us to assign each use of a variable to have a different qualifier:
  - 例如，在第1行𝑥=0时，**𝛼1可以是𝑥的限定符**，但在第2行𝑥=1时，我们**对𝑥有一个不同的限定符，即𝛼2**。For example, at line 1 where 𝑥 = 0, 𝛼1 can be the qualifier for 𝑥 but at line 2 where 𝑥 = 1, we have a different qualifier for 𝑥, 𝛼2
- 这类似于**使用静态单一赋值（SSA）符号**来转换代码的表示方法 This is similar to transforming the representation of the code using Static Single Assignment (SSA) notation.
  - 表示法中的每个变量最多分配一次 Every variable in the representation is at most assigned once.
  - **遇到变量重新赋值，重新分配一个限定词**

---

- 我们已经发现了三步法的一个缺点，即我们可能会得出一个不正确的结论。We have identified a shortcoming of the three step approach where we can arrive at an incorrect conclusion.
- 因此影响了分析器的健全性。Thus affecting the soundness of the analyser.
- 我们已经讨论了我们如何通过应用对流量敏感的分析来缓解假阳性的问题。We have discussed how we can mitigate the problem of false positives with the application of analysis that is flow sensitive.
  - 虽然引入了更多限定词，可能增加复杂度，但是分析器会变得更健全
- 我们希望**增加更多**的**复杂性，讨论条件式** We want to add more complexity and discuss conditionals
- 关注**嵌套的控制流**，以及我们如何缓解那里产生的问题。Focus on nested control flow and how we mitigate the problems incurred there.

# 添加敏感度后例子

![](/static/2022-02-02-14-28-13.png)

- 我们可以看到，限定词在这里只被分配一次。We can see that the qualifiers are only ever assigned once here.
- 以前我们无法区分amount的两种分配状态。Previously we could not distinguish the 2 assignment states of amount.
- 但我们可以用SSA表示。But we can with SSA representation.
  - amount有两个限定符

# 路径敏感度：Path Sensitivity

- 一个对路径敏感的分析，在确定程序中是否存在潜在的问题时，会**考虑路径的可行性**。An analysis that is path sensitive considers path feasibility when determining whether or not there is a potential problem in the program.
  - 为不同路径执行，分配约束
- 如果程序中的 "else "子句有问题，那么当 "if "块启动时可能就不会有问题了。If there is a problem in a programs ‘else’ clause, there might not be a problem when the ‘if’ block fires instead.

# 多控制条件例子：Multiple Conditionals

当前分析器（仅有流敏感）没有办法消除分支，，先观察每个约束

![](/static/2022-02-02-15-16-58.png)
![](/static/2022-02-02-15-21-52.png)

* C1，C2无法结合，因为α要同时为trusted & untrusted，，会报告假阳性
  * 但实际上取决于分支

---

如何去除假阳性报告？(false alarm)

* 路径敏感
  * **增加分析的健全性，引入时间开销（要枚举所有可能路径）&复杂度**

1. 标记出代码中所有steps（path，，if else只计算if的条件表达式一次)

![](/static/2022-02-02-16-01-52.png)

* 可以看出有两种路径

---

2. 根据路径条件限定约束

* C1 = untainted<=α
  * `amount=0`这行，，仅当 x=true时成立，关联这个precondition
* C2 = tainted<=α
  * `amount=getInput()`，仅当x=false,
  * associated with breadcrumbs 1-3
* C3同理

![](/static/2022-02-02-17-08-01.png)

* 结合C1，C3
* C2单独

# 流/路径敏感分析问题：Challenges of Flow/Path Sensitivity

- 流量敏感度增加了精度，路径敏感度增加了更多。Flow sensitivity adds precision and path sensitivity adds more.
- 这很好，但要**权衡的是时间问题**。This is good, but the trade-off is that of time.
- **流敏感度**增加了约束图中的**节点数量**。Flow sensitivity increases the number of nodes in the constraint graph.
- **路径敏感度**需要更多的解算程序**来处理所有的路径条件**。Path sensitivity requires more solving procedures to handle all path conditions.

当你分析一个大型程序时，你最终必须做出决定，是关注精度还是关注可扩展性。When you are analysing a large program, you will ultimately have to make a decision on whether to focus on accuracy or scalability.

* 可以有两种分析器，一个半健全（没那么可靠）但能快速完整整个代码库分析。如果代码库中存在高敏感组件，可以用更精确的分析器来分析

# 函数调用：Handling Function Calls

![](/static/2022-02-02-20-24-45.png)

- 为**参数**和**返回值**指定名称（类型标签）。Assign names (type labels) to arguments and return value
- 调用创建流 Calls create flows
  1.	从调用方法的代码到被调用方法的参数。From the code calling the method to the invoked methods arguments.
  2.	从方法的结果到返回值，再到调用代码。From the methods result to the returned value to the calling code.
- 类似假设某方法，进入的时候一个可信状态，结束的时候另一种状态 

# 函数调用例子：Handling Function Calls

![](/static/2022-02-02-20-31-03.png)

---

两个flow存在，能够将污点&非污点数据分离，不会造成非法流

![](/static/2022-02-02-20-35-12.png)

# 上下文敏感度：Context Sensitivity is not a Silver Bullet

- 语境敏感性是一种权衡。Context sensitivity is a trade-off:
  - 精度与可扩展性。Precision vs scalability:
    - 精度关联健全性，一个高精度的分析器也许能复现人类直觉，因为应用了很多层规则
    - 如果要自己创建分析，需要考虑对于代码库的精确度 & 可扩展性
  - 不敏感的算法有𝑂的复杂度（可扩展）。Insensitive algorithm has 𝑂 	complexity (scalable)
  - 然而，敏感的算法有𝑂(𝑛3)的复杂度！（不能扩展）。(不能扩展) Sensitive algorithm however has 𝑂(𝑛3) complexity! (doesn’t scale)
- **然而，更高的精度有时可以提高性能，因为被考虑的程序的大小（𝑛）变得更小，因为不可行的路径被忽略了**。However, higher precision can sometimes improve performance, as the size of the program being considered (𝑛) becomes smaller as infeasible paths are ignored.

# 隐式流：Implicit Flows

比如受控制流结构影响的赋值语句，此时是隐式流（信息流）

之前概念（流敏感，路径敏感）不够用来得出下面例子的结论(存不存在非法流)

隐式非法流，显式非法流

![](/static/2022-02-02-20-49-34.png)

# 信息流（隐式流）分析：Information Flow Analysis

![](/static/2022-02-02-20-49-55.png)

- 先前的流是一种隐性流，因为一个值中的信息隐性地影响着另一个值。The prior flow is an implicit flow, since information in one value implicitly influences another.
- 发现这些威胁的一种方法是**维护一个范围内的程序计数器（pc）标签** One way to discover these threats is to maintain a scoped program counter (pc) label
- **赋值语句产生涉及pc实例的约束。赋值𝑥=𝑦将产生两个约束**。Assignments generate constraints involving the pc example. The assignment 𝑥 = 𝑦 will produce two constraints:
  - label(y)<=label(x)
  - **label(x)<=pc**

# 信息流分析例子

![](/static/2022-02-02-20-54-59.png)

---

引入pc标签，建立约束，进行隐式检测

![](/static/2022-02-02-20-58-32.png)
![](/static/2022-02-02-21-02-06.png)

---

![](/static/2022-02-02-22-06-41.png)

* pc1非污点
  * 因为 `if src==0`始终被触发，不依赖任何其他东西
* pc2 tainted
  * 因为dst=0依赖if条件，而if src涉及tainted data
* pc3 tainted
  * else块涉及tainted data source才触发
* pc4 untainted
  * 不涉及任何branch logics, 始终触发

---

结合约束 & 解

![](/static/2022-02-02-23-00-15.png)

* α既要是untainted,同时<=pc2又要是tainted,所以非法流
* 红框圈出来的意味着，dst=0赋值最终会被tainted data影响

# Summary

- 信息流控制使用有污点和无污点的类型为数据流（网络上的数据传输、从磁盘上读取的文件等）分配元数据。Information flow control assigns metadata to data flows (data transfer over networks, files read from disk etc.) using tainted and untainted types.
- 使用污点分析，我们能够验证没有敏感信息可以从一个较高的安全环境流向一个较低的环境。Using tainted analysis, we are able to verify that no sensitive information can flow from a higher security context to a lower one.

# ==================

# Example1

![](/static/2022-02-03-15-38-54.png)

---

![](/static/2022-02-03-16-07-38.png)

* 先分析显式流（数据流）& 合并约束

![](/static/2022-02-03-16-42-28.png)

* 引入隐式流分析（信息流）【pc】&路径敏感【标路径】
  * 分析是否不同路径的执行，存在问题
  * pc：分析是否存在控制块，赋值语句依赖于tainted data 【给PC标，**PC是否为污点**】
  * pc1永远触发，untainted
  * pc2 `z=2`，依赖于`if(y==0)`
    * 解y相关的约束，得出y tainted.因此pc2 tainted
  * 因为pc3,pc4都在pc1下面受控，，所以也为tainted
* 再迭代步骤，进控制块，建立约束
  * `z=2`重新赋值，约束C6
  * `w=0`重新赋值，约束 C7
  * C8， `z=2`关联的pc2，，`γ2<=Pc2(tainted)`
  * C9同理


结合约束

![](/static/2022-02-03-16-46-16.png)

* C6&C8.C7&C9，存在非法流

标记Path

* 一共3条
* 还有一条 y!=0，，直接到sink，，，显式流分析了
  * 不存在问题
  * ![](/static/2022-02-03-16-48-35.png)
* Path1 （1,2）
  * ![](/static/2022-02-03-17-37-17.png)
  * 语句约束
  * 【C6+C8】untaitned<=γ2<=tainted(pc2)
    * 这个code block存在隐式非法流**implicit illegal flow**【不一定会造成过大问题，但也许想要解决】
  * 但结合前面显式流分析，并不存在显式非法流
    * ![](/static/2022-02-03-17-35-53.png)
* Path2 （1，2,3,4）
  * ![](/static/2022-02-03-17-42-18.png)

# Example2

![](/static/2022-02-03-17-45-00.png)

![](/static/2022-02-03-18-02-54.png)

* 先标好路径，重新赋值限定符，pc

![](/static/2022-02-03-22-57-25.png)

建立约束，显式流路径

![](/static/2022-02-03-23-05-52.png)

隐式流，每个赋值语句存在两个约束

---

分析路径

* P1 - 1,2,3,4
  * C10约束， w2<=untainted
  * ![](/static/2022-02-03-23-09-53.png)

# Example3

![](/static/2022-02-03-23-11-02.png)

先看显式流,C1,C2

隐式流，引入PC1,2,3。约束C3，C4

![](/static/2022-02-03-23-28-41.png)

推出PC是否为污点。结合各约束

![](/static/2022-02-03-23-33-25.png)

* 存在隐式非法流，可能会造成问题

# Example4

![](/static/2022-02-03-23-37-36.png)

---

![](/static/2022-02-03-23-45-10.png)

* C2,C3是modCheck,return true, return false

![](/static/2022-02-03-23-52-13.png)
![](/static/2022-02-03-23-53-07.png)
![](/static/2022-02-03-23-54-36.png)

* 然后引入PC做隐式流分析，，，

---

分析各路径执行（推出路径下PC是否污点），结合约束，存不存在非法流

![](/static/2022-02-04-00-10-24.png)

* 传入tainted
  * PC1,2,3
* 传入untainted
  * PC4,5,6

![](/static/2022-02-04-00-12-57.png)

* 第一个Call
  * C2,C4不能结合
  * C4 ： 存在untainted<=tainted(pc2)，C2: 存在untainted（bool）<=δ
  * 因为return type delta要同时为untainted & tainted。存在隐式非法流
  * ![](/static/2022-02-04-00-25-35.png)
* 第二个call, C8,C10不能结合
  * C8 (bool) : untainted（bool）<=δ
  * ![](/static/2022-02-04-00-29-36.png)

第二个call

![](/static/2022-02-04-00-15-58.png)

* 得出结论，存在隐式非法流（第一个call），但是最终不存在tainted data用于untainted data

## Example 5

![](/static/2022-02-09-22-32-51.png)