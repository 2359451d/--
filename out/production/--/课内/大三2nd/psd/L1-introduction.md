# Introduction to Software Engineering

<li>通过解释为什么软件开发是困难的，来激励软件工程的发展。Motivate software engineering by explaining why the development of software is hard
<li>回顾软件项目失败的案例研究 Review case studies of software project failures.</li>
<li>定义软件工程 Define software engineering</li>
<li>激发对软件工程的敏捷方法的需求 Motivate the need for an agile approach to software engineering</li>

---

* [Introduction to Software Engineering](#introduction-to-software-engineering)
* [软件项目失败原因：Software projects fail because](#软件项目失败原因software-projects-fail-because)
* [取消项目的原因](#取消项目的原因)
* [By the numbers](#by-the-numbers)
* [Case Study: London Ambulance Disaster (1991)](#case-study-london-ambulance-disaster-1991)
* [Case Study: Scottish Election System 2007](#case-study-scottish-election-system-2007)
* [What makes software so hard to get right (Brooks, 1995)?](#what-makes-software-so-hard-to-get-right-brooks-1995)
* [This means software projects fail because we build…](#this-means-software-projects-fail-because-we-build)
* [软件工程定义：Defining Software Engineering](#软件工程定义defining-software-engineering)
* [4类软件开发流程模型:Software development as construction](#4类软件开发流程模型software-development-as-construction)
  * [其他类型](#其他类型)
* [Ultra-large scale complexity](#ultra-large-scale-complexity)
* [Unique](#unique)
* [Brownfield software development](#brownfield-software-development)
* [Software development as infrastructure maintenance…](#software-development-as-infrastructure-maintenance)
* [..with continual incremental improvement, measurement and review.](#with-continual-incremental-improvement-measurement-and-review)
* [Summary](#summary)

# 软件项目失败原因：Software projects fail because

…software development is hard.

* 至少，开发高质量、无缺陷的软件，真正解决客户的问题是很难的 (Or at least, the development of high quality, defect free software, which really solves a customer’s problem is hard.)

Building a system for the wrong reason 为错误的理由建立一个系统

Building the wrong system 构建错误的系统

Building the system wrong 构建错误的系统

# 取消项目的原因

取消项目的原因有很多，从需求的改变，到缺乏管理或技术能力，再到超出进度/预算。

缺少管理或技术技能，以及超过计划/超过预算

There are a wide variety of reasons for cancelling a project, from requirements changes, to
lack of management or technical skills, to being over schedule/over budget.

# By the numbers

![](/static/2021-04-22-17-12-55.png)
![](/static/2021-04-22-17-13-09.png)

# Case Study: London Ambulance Disaster (1991)

原因 Causes

* **渴望完全自动化** Desire to completely automate
* **缺乏与实际用户的协商** Lack of consultation with actual users
* **要求在一年内完成该系统的压力** Pressure to complete the system within a year
* **严重低估了成本** Gross underestimation of costs
* **新技术（Visual Basic**）New technology (Visual Basic)
* **糟糕的变更管理** Poor change management
* **有限的测试** Limited testing
* **无组织的部署** Disorganised deployment

# Case Study: Scottish Election System 2007

大量投资于验收测试，但没有投资于性能测试（测试案例仅限于同一批次的10000张选票）。测试案例不能代表真实的用户行为（太多废弃的选票）

问题：

选民隐私的丧失

由于卡纸，选票处理速度低于预期

有14万张选票被拒绝。

由于数据库索引损坏，失去了关于计票状态的实时信息。

混乱的时间表

# What makes software so hard to get right (Brooks, 1995)?

软件在本质上是：

* 复杂的 complex
* 无形的 intangible
* 可扩展的 malleable
* 规模 scale
* 革命性的 evolutionary

# This means software projects fail because we build…

* 系统错误原因 a system for the wrong reason;
* 错误系统 the wrong system; or
* 错误方式使用系统 the system in the wrong way.

# 软件工程定义：Defining Software Engineering

**用于规范、开发、管理和进化软件系统的原则、方法、技术和工具** the principles, methods, techniques and tools for the specification, development, management and evolution of software systems

**在软件的开发、运行和维护中应用一种系统的、有纪律的、可量化的方法；也就是说，将工程应用于软件** The application of a systematic, disciplined, quantifiable approach to the development, operation, and maintenance of software; that is, the application of engineering to software

**在成本、时间和其他限制条件下，通过系统地开发大型、高质量的软件系统来解决客户的问题的过程** the process of solving customer’s problems by the systematic development of large, high-quality software systems within cost, time and other constraints

请注意系统性和纪律性的方法对减少风险的重要性 Note the importance of systematic and disciplined approaches to reduce risk.

# 4类软件开发流程模型:Software development as construction

![](/static/2021-04-22-18-18-25.png)

瀑布流 - Linear, waterfall model

不起作用，因为假定 Doesn’t work because assumes that:

* 根据以往的经验可以预测的 Predictable based on previous experience
* 项目范围完全在项目组的控制范围之内 Project scope is fully within the control of the project team.
* 需求可独立于设计和实施而被指定 Requirements can be specified independently of design and implementation
* 需求在开始时是完全已知的 Requirements are fully known at the start.
* 需求和领域在问题定义和交付之间的长期延迟中不会改变 Requirements and domain won’t change during long delay between problem definition and delivery.
* 将测试留到生命周期的后期 Leaves testing until late in the life-cycle

此外，不强调维护是一个成本领域。Also, de-emphasises maintenance as a cost area.

## 其他类型

Iterative

concurrent

configurable or adaptive

# Ultra-large scale complexity

# Unique

软件项目类似于大规模的建筑项目，但每个项目都是独一无二的。这使得它更难吸取教训。Software projects are similar to large scale construction projects, but each one is unique. This makes it harder to learn lessons.

# Brownfield software development

# Software development as infrastructure maintenance…

![](/static/2021-04-22-18-22-09.png)

# ..with continual incremental improvement, measurement and review.

# Summary

由于软件的独特特性，软件项目很容易失败。软件工程方法旨在减轻这些风险。Software projects are prone to failure due to the unique characteristics of software. Software engineering methods aim to mitigate these risks.