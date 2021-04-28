# Agile Team Organisation

机翻

探讨了团队组织和管理的敏捷方法 This lecture explores an agile approach to team organisation and management.

---

* [Agile Team Organisation](#agile-team-organisation)
* [Project Stakeholds](#project-stakeholds)
* [Why Agile? - Empirical process control](#why-agile---empirical-process-control)
  * [传统开发问题](#传统开发问题)
* [瀑布流开发](#瀑布流开发)
* [敏捷宣言：Agile Manifesto](#敏捷宣言agile-manifesto)
* [敏捷在实践中意味着什么？What Does Agile Mean in Practice?](#敏捷在实践中意味着什么what-does-agile-mean-in-practice)
* [敏捷开发方法&实践：Agile Methods and Practices](#敏捷开发方法实践agile-methods-and-practices)
* [敏捷开发的风险：Risks of Agile Development](#敏捷开发的风险risks-of-agile-development)
* [what does Agile not mean?](#what-does-agile-not-mean)
* [Scrum](#scrum)
  * [Scrum特性](#scrum特性)
  * [Scrum阶段](#scrum阶段)
  * [Scrum交付](#scrum交付)
  * [Scrum优点](#scrum优点)
* [基于Roles (in Scrum and Beyond)](#基于roles-in-scrum-and-beyond)
* [Managing work in Scrum](#managing-work-in-scrum)
* [流程：Scrum Workflow](#流程scrum-workflow)
* [项目启动会议：Project Launch Meeting](#项目启动会议project-launch-meeting)
* [发行计划会议：Release Planning Meetings](#发行计划会议release-planning-meetings)
* [迭代计划会议：Sprint Planning Meeting](#迭代计划会议sprint-planning-meeting)
* [Determining Project Velocity - Burn down Charts](#determining-project-velocity---burn-down-charts)
* [Monitoring Progress in Stand-ups](#monitoring-progress-in-stand-ups)
* [Managing Delays - Deadlines or Feature Driven?](#managing-delays---deadlines-or-feature-driven)
* [What about adding more resources](#what-about-adding-more-resources)
* [Reviewing a Sprint](#reviewing-a-sprint)
* [迭代回顾会议：Sprint Review Meeting](#迭代回顾会议sprint-review-meeting)
* [Key point](#key-point)
* [团队成员数量](#团队成员数量)

# Project Stakeholds

![](/static/2021-04-28-11-00-58.png)

如果每种类型的利益相关者超过一个，**或者两组利益相关者对某一功能有不同意见，那么利益相关者之间有时会有冲突。指定一个客户代表，在客户和工程团队之间进行协商(PO?**，讨论需求和问题等，可能会很有用。你也可以把角色分开，在客户的团队里有一个客户代表，在软件工程团队里有一个产品负责人，他们可以互相联络。 There can sometimes be conflict between stakeholders if there is more than one stakeholder of each type, or if two groups of stakeholders disagree about a feature. It can be useful to appoint a customer representative who negotiates between the customer and the engineering team to discuss requirements and issues etc. You can also separate roles out and have a customer representative in the customer’s team and a product owner in the software engineering team who can liaise with each other.

# Why Agile? - Empirical process control

![](/static/2021-04-22-18-42-33.png)

他们说，系统开发有如此多的复杂性和不可预测性，因此必须通过他们称之为 "经验 "的过程控制模型来管理。

Schwaber和Beedle将旧方法描述为遵循一种确定的方法。项目是围绕着一个预先计划好的过程而进行的。Schwaber and Beedle describe older methods as following a defined approach. Projects are made to fit around a pre-planned process.

**相反，在敏捷方法中，流程是围绕着项目的状态进行的，并不断进行调整**。 Instead, in agile, the process fits around the state of the project and constant adjustments are made.

## 传统开发问题

- 许多开发过程是不受控制的。输入和输出要么是未知的，要么是松散的，转换过程缺乏必要的精确性，质量控制也没有定义。测试过程就是一个例子。Many of the development processes are uncontrolled. The inputs and outputs are either unknown or loosely defined, the transformation process lacks necessary precision, and quality control is not defined. Testing processes are an example.

- 衔接已知但不受控制的开发过程的数量不详。确保逻辑模型包含足够的内容以导致一个成功的物理模型的详细过程就是这样一个过程。An unknown number of development processes that bridge known but uncontrolled processes are unidentified. Detailed processes to ensure that a logical model contains adequate content to lead to a successful physical model is one such process.

- 环境输入（要求）只能在过程开始时被考虑到。此后需要复杂的变更管理程序。Environmental input (requirements) can only be taken into consideration at the beginning of the process. Complex change management procedures are required thereafter.

# 瀑布流开发

![](/static/2021-04-23-17-13-01.png)

尽管瀑布式方法强制要求使用未定义的流程，但其线性性质一直是其最大的问题。这个过程没有定义如何应对来自任何一个中间过程的意外输出。Although the waterfall approach mandates the use of undefined processes, its linear nature has been its largest problem. The process does not define how to respond to unexpected output from any of the intermediate process.

---

![](/static/2021-04-23-17-14-07.png)

Barry Boehm12引入了螺旋式方法来解决这个问题。每个瀑布阶段都以风险评估和原型设计活动结束。螺旋式方法论如图2所示。Barry Boehm12 introduced a Spiral methodology to address this issue. Each of the waterfall phases is ended with a risk assessment and prototyping activity. The Spiral methodology is shown in Figure 2.

螺旋式方法论 "剥洋葱"，通过开发过程的 "层 "来进行。原型让用户确定项目是否在正轨上，是否应该被送回之前的阶段，或者是否应该结束。然而，阶段和阶段过程仍然是线性的。需求工作仍然在需求阶段进行，设计工作在设计阶段进行，以此类推，每个阶段都由线性的、明确定义的过程组成。The Spiral methodology “peels the onion”, progressing through “layers” of the development process. A prototype lets users determine if the project is on track, should be sent back to prior phases, or should be ended. However, the phases and phase processes are still linear. Requirements work is still performed in the requirements phase, design work in the design phase, and so forth, with each of the phases consisting of linear, explicitly defined processes.

# 敏捷宣言：Agile Manifesto

![](/static/2021-04-22-18-46-08.png)

"我们正在通过实践和帮助他人来发现开发软件的更好方法。通过这项工作，我们已经开始重视。"We are uncovering better ways of developing software by doing it and helping others do it. Through this work we have come to value:

* **个人和互动**胜过过程和工具 Individuals and interactions over processes and tools
* **工作中的软件**而不是全面的文件 Working software over comprehensive documentation
* **客户合作**胜过合同谈判 Customer collaboration over contract negotiation
* **应对变化**而不是遵循计划 Responding to change over following a plan

也就是说，虽然右边的项目有价值，但我们更看重左边的项目"。That is, while there is value in the items on the right, we value the items on the left more

# 敏捷在实践中意味着什么？What Does Agile Mean in Practice?

* **小团队（3-12人）的软件开发人员** Small teams (3-12) software developers
* 经常性的 Frequent
  * 短期的、结构化的团队会议和非正式交流 Short, structured team meetings and informal communication
  * 小规模交付功能供审阅 Small deliveries of features for review
  * 客户参与 Customer engagement
* **通过持续的测试、分析、审查和重构来维护设计和代码质量**。Maintenance of design and code quality through continual testing, analysis, review and refactoring.
* **尽可能实现自动化**。Automation wherever possible.
* **经常审查和改变项目目标和优先级**。Frequent review and change of project objectives and priorities.
* **频繁地测量和审查软件流程，并进行调整以提高性能**。Frequent measurement and review of software processes and adjustment to enhance performance.

# 敏捷开发方法&实践：Agile Methods and Practices

很多实践都与敏捷性相关。Lots of practices have become associated with agility

![](/static/2021-04-22-18-56-59.png)

# 敏捷开发的风险：Risks of Agile Development

![](/static/2021-04-22-18-57-51.png)

* **缺少客户参与** Lack of customer engagement
* **利益相关者的冲突** Stakeholder conflict
* **复杂的合同安排** Complex contractual arrangements
* **组织记忆的丧失** Loss of organisational memory
* **代码质量差** Poor code quality
* **团队协调性或凝聚力差** Poor team coordination or cohesion

# what does Agile not mean?

在一个项目中做敏捷实践，**不考虑背景**。Doing every single agile practice within a single project regardless of context.

通过**不受控制**地改变目标或进程来实现灵活性。Achieving flexibility through uncontrolled change to objectives or process.

# Scrum

SCRUM是一种针对现有系统或生产原型的管理、改进和维护方法。它假定现有的设计和代码，由于类库的存在，在面向对象的开发中几乎总是这样。SCRUM将在以后解决全新的或重新设计的遗留系统开发工作 

* **专注于软件开发的团队、项目和流程管理方面** Focuses on the team, project and process management aspects of software development
Can be used as a wrap around for other agile practices that cover technical concerns
Name is supposed to be derived from rugby
* **可以作为涵盖技术问题的其他敏捷实践的包袱** Can be used as a wrap around for other agile practices that cover technical concerns
* Name is supposed to be derived from rugby

## Scrum特性

![](/static/2021-04-23-17-19-44.png)

**第一和最后一个阶段（规划和结束）由确定的过程组成，其中所有的过程、输入和输出都是明确的。关于如何做这些过程的知识是明确的。流程是线性的，在计划阶段有一些迭代**。The first and last phases (Planning and Closure) consist of defined processes, where all processes, inputs and outputs are well defined. The knowledge of how to do these processes is explicit. The flow is linear, with some iterations in the planning phase.

**冲刺阶段是一个经验性的过程。冲刺阶段的许多过程是未被识别或未被控制的。它被当作一个黑匣子，需要外部控制。因此，包括风险管理在内的控制措施被放在冲刺阶段的每个迭代上，以避免混乱，同时最大限度地提高灵活性**。The Sprint phase is an empirical process. Many of the processes in the sprint phase are unidentified or uncontrolled. It is treated as a black box that requires external controls. Accordingly, controls, including risk management, are put on each iteration of the Sprint phase to avoid chaos while maximizing flexibility.

**冲刺阶段是非线性的和灵活的。在可用的情况下，使用显性的流程知识；否则就使用隐性知识和试错来建立流程知识。阶段性工作是用来演化最终产品的** Sprints are nonlinear and flexible. Where available, explicit process knowledge is used; otherwise tacit knowledge and trial and error is used to build process knowledge. Sprints are used to evolve the final product.

项目对环境是开放的，直到结束阶段。**在项目的计划和冲刺阶段，可交付的产品可以随时改变**。在这些阶段，项目对环境的复杂性保持开放，包括竞争、时间、质量和财务压力 The project is open to the environment until the Closure phase. The deliverable can be changed at any time during the Planning and Sprint phases of the project. The project remains open to environmental complexity, including competitive, time, quality, and financial pressures, throughout these phases.

**可交付成果是在项目期间根据环境决定的**	The deliverable is determined during the project based on the environment

灵活的时间表--可交付成果可能比最初计划的时间更早或更晚需要。Flexible schedule - the deliverable may be required sooner or later than initially planned.

小型团队 - 每个团队的成员不超过6人。一个项目中可能有多个团队。Small teams - each team has no more than 6 members. There may be multiple teams within a project.

频繁的审查 - 根据环境的复杂性和风险的要求，对团队的进展进行审查（通常是1到4周的周期）。每个团队必须为每次审查准备一个可执行的功能。Frequent reviews - team progress is reviewed as frequently as environmental complexity and risk dictates (usually 1 to 4 week cycles). A functional executable must be prepared by each team for each review.

协作--在项目过程中，预期会有内部和相互之间的协作。Collaboration - intra and inter-collaboration is expected during the project.

面向对象 - 每个团队将处理一组相关的对象，有明确的接口和行为。Object Oriented - each team will address a set of related objects, with clear interfaces and behavior.

## Scrum阶段

![](/static/2021-04-23-19-02-58.png)

- **规划**：根据目前已知的积压，定义一个新的版本，以及对其进度和成本的估计。如果正在开发一个新的系统，这个阶段包括概念化和分析。如果一个现有的系统正在被加强，这个阶段包括有限的分析。Planning : Definition of a new release based on currently known backlog, along with an estimate of its schedule and cost. If a new system is being developed, this phase consists of both conceptualization and analysis. If an existing system is being enhanced, this phase consists of limited analysis.
- **架构**：设计如何实现积压的项目。这个阶段包括系统架构的修改和高层设计。Architecture : Design how the backlog items will be implemented. This phase includes system architecture modification and high level design.

- **开发冲刺**。开发新版本的功能，不断尊重时间、需求、质量、成本和竞争等变量。与这些变量的相互作用决定了这个阶段的结束。有多个迭代的开发冲刺，或周期，用来发展系统。Development Sprints : Development of new release functionality, with constant respect to the variables of time, requirements, quality, cost, and competition. Interaction with these variables defines the end of this phase. There are multiple, iterative development sprints, or cycles, that are used to evolve the system.
  - 与团队会面，审查发布计划。
  - 分发，审查和调整产品所要符合的标准。
  - 迭代的Sprints，直到产品被认为可以发布
  - **每个Sprint由一个或多个团队组成，执行以下内容**
    - 开发。定义实施积压需求到包中所需要的变化，打开包，进行领域分析，设计、开发、实施、测试和记录这些变化。开发包括发现、发明和实施的微观过程。
    - 包装。关闭数据包，创建一个可执行的变化版本，以及它们如何实现积压的需求。
    - 审查。所有团队开会介绍工作和审查进展，提出并解决问题和难题，增加新的积压项目。审查风险并确定适当的反应。
    - 调整。将从审查会议上收集到的信息整合到受影响的数据包中，包括不同的外观和新属性。

**结束**：为发布做准备，包括最后的文件，发布前的阶段性测试，以及发布 Closure : Preparation for release, including final documentation, pre-release staged testing, and release

## Scrum交付

交付的产品是灵活的。其内容由环境变量决定，包括时间、竞争、成本或功能。可交付的决定因素是市场情报、客户联系和开发人员的技能。在项目过程中，为了应对环境，会经常调整可交付的内容。可交付的内容可以在项目期间随时确定。The delivered product is flexible. Its content is determined by environment variables, including time, competition, cost, or functionality. The deliverable determinants are market intelligence, customer contact, and the skill of developers. Frequent adjustments to deliverable content occur during the project in response to environment. The deliverable can be determined anytime during the project.

## Scrum优点

另一方面，SCRUM方法论被设计成在整个过程中相当灵活。它提供了规划产品发布的控制机制，然后随着项目的进展管理各种变量。这使得组织能够在任何时间点改变项目和交付物，提供最合适的发布。The SCRUM methodology, on the other hand, is designed to be quite flexible throughout. It provides control mechanisms for planning a product release and then managing variables as the project progresses. This enables organizations to change the project and deliverables at any point in time, delivering the most appropriate release.

SCRUM方法论使开发者在整个项目过程中，随着学习的进行和环境的变化，可以设计出最巧妙的解决方案。The SCRUM methodology frees developers to devise the most ingenious solutions throughout the project, as learning occurs and the environment changes.

小型的、协作的开发者团队能够分享关于开发过程的隐性知识。为所有各方提供了一个良好的培训环境。Small, collaborative teams of developers are able to share tacit knowledge about development processes. An excellent training environment for all parties is provided.

面向对象的技术为SCRUM方法提供了基础。对象，或产品特征，提供了一个离散和可管理的环境。程序代码，由于它有许多相互交织的接口，不适合SCRUM方法。SCRUM可以有选择地应用于具有干净界面和强大数据导向的程序性系统。Object Oriented technology provides the basis for the SCRUM methodology. Objects, or product features, offer a discrete and manageable environment. Procedural code,a with its many and intertwined interfaces, is inappropriate for the SCRUM methodology. SCRUM may be selectively applied to procedural systems with clean interfaces and strong data orientation.

# 基于Roles (in Scrum and Beyond)

小组工作是复杂的，需要组织。
项目的一个部分的变化可能会影响到所有的团队成员，所以需要很好地了解做出决定的过程。
决策的过程需要被充分理解。

---

在团队组织过程中，你应该考虑每个人的技能、个性和情况。如果需要的话，角色可以在项目期间移动。
你还应该考虑什么是必须沟通的，多长时间一次，通过什么方式，以及在谁之间沟通。


![](/static/2021-04-22-19-01-53.png)

* Scrum master
  * ScrumMaster不是项目经理，他没有分配任务的权力，没有考核的权力，没有下命令的权力，他指导项目组的成员按照Scrum的原则、方法做事情，领导团队完成Scrum的实践以及体现其价值，排除团队遇到的困难，确保团队胜任其工作，并保持高效的生产率，使得团队紧密合作，使得团队个人具有多方面职能的工作能力，保护团队不受到外来无端影响
* 产品所有者 PO
  * 定义所有产品功能，决定产品发布的内容以及日期，对产品的投入产出负责，根据市场变化对需要开发的功能排列优先顺序，合理地调整产品功能和迭代顺序，认同或者拒绝迭代的交付。
  * 主要负责backlog
* 项目经理
  * 需求收集，优先级，...

团队经理

质量保证经理

工具工匠

首席架构师

用户体验设计师

开发人员

# Managing work in Scrum

**Scrum将工作组织成发布，并以发布计划会议为标志**。Scrum organises work into Releases are marked by release planning meetings.

**有些团队以项目启动会议开始第一个冲刺（迭代）**。Some teams begin a first sprint with a project launch meeting. 

**一个版本包括一个或多个冲刺**。A release comprises one or more sprints.

**每个冲刺（通常）持续1到3周**。Each sprint lasts (normally) between 1 and 3 weeks.

**一个冲刺从计划会议开始，以审阅会议和反思会结束**。A sprint starts with a planning meeting and ends with a review meeting and retrospective.

**在整个冲刺过程中，都会举行站立会议或争论会议**。Stand-ups or scrums take place throughout the sprint

# 流程：Scrum Workflow

![](/static/2021-04-22-19-12-35.png)

# 项目启动会议：Project Launch Meeting

**确定在一系列冲刺阶段要交付给客户的主要功能**。Determines the major features to be delivered to a customer over a series of sprints.

**了解客户的长期目标，确定最小可行产品**。Understand customers long term objectives and identify a minimum viable product.

**决定项目过程中的目标**。Decide on the goals for within the project course.

**开发一套初步的用户故事**。Develop an initial set of user stories.

**将用户故事细化为任务，并在GitLab上填入问题的产品待办事项列表**。Refine the user stories into tasks and populate a backlog of issues on GitLab.

**对产品待办事项列表的问题进行筛选，以确定成本估算和优先级**。Triage items in the backlog to establish cost estimates and priorities.

# 发行计划会议：Release Planning Meetings

* **较长的项目将由多个版本组成，每个版本由几个冲刺组成**。Longer projects will comprise multiple releases, each of several sprints.
* **确定将在一个版本中交付的高层次的功能或改进**。Identify the high level set of features or improvements to be delivered in a release.
* **根据客户的优先级和客户的需求，创建一个里程碑式的路线图**。Create a roadmap of milestones aligned with your customer priorities and the customer days.
* **在路线图中加入产品待办事项列表中的关键功能**。Populate the roadmap with key features from the backlog.
* **假设这些功能会在迭代计划会议上被修改**。Assume that these will be changed in the sprint planning meetings

---

团队项目将只有一个或最多两个版本 Team Project will have only one or at most two releases.

# 迭代计划会议：Sprint Planning Meeting

迭代计划会议

![](/static/2021-04-22-19-31-06.png)

* **决定迭代的主要目标**。Decide on main goal for the sprint.
  * **主要的新特性集合** Major new feature set
  * **性能或其他方面的提升** Performance or other enhancements
  * **需要纠正的bug** Bugs to be corrected
  * **需要改进的代码** Code to be improved
* **从问题追踪器上的产品待办事项列表任务中【选择符合本次迭代目标的任务**】。Select tasks from the backlog on your issue tracker that match the goal.
  * 先前由PO或团队编写backlog，然后放在迭代计划会议上进一步协商确认
* **确保所有选择的问题都足够详细**。Ensure all selected issues are sufficiently detailed.
  * **成本估算** Cost estimates
  * **优先级** Priorities
  * **被指派者** Assignees
* **确保任务成本估算在项目速度范围内**。Ensure that task cost estimates are within the project velocity

# Determining Project Velocity - Burn down Charts

![](/static/2021-04-22-19-42-48.png)

# Monitoring Progress in Stand-ups

在第一学期，每周至少在TP3举行一次站立会议。Hold a stand-up at least once a week in TP3 during semester 1.

由scrum master主持。Facilitated by the scrum master.

每个团队成员都会被问到。Each team member is asked:

你上周完成了什么？What did you accomplish last week?
你现在在做什么？What are you working on now?
你有什么障碍物吗？Do you have any blockers?
不应超过10分钟 Should not last more than 10 minutes

开始时，尝试在你的维基上记录站立的情况。Try documenting the stand up on your wiki to begin with.

尝试更频繁的站立，特别是在早期。Experiment with more frequent stand-ups, particularly early on

# Managing Delays - Deadlines or Feature Driven?

**关键的一点是，在敏捷方法中，最好是放弃一些功能并将你所拥有的东西交付给客户，而不是推迟交付以试图纳入所有功能** Key point is that in agile methods it is better to drop features and deliver what you have to the customer rather than delay the delivery to try to incorporate all features

# What about adding more resources

![](/static/2021-04-22-19-45-18.png)

![](/static/2021-04-22-19-45-29.png)

![](/static/2021-04-22-19-45-35.png)

![](/static/2021-04-22-19-45-42.png)

# Reviewing a Sprint

**在迭代审查会议上审查项目的进展情况** Review the progress on the project in the Sprint Review Meeting

**在回顾会议中回顾软件团队的进程** Review the software team’s process in the Retrospective Meeting

# 迭代回顾会议：Sprint Review Meeting

* **交付并演示在冲刺过程中开发的新版本软件**。Deliver and demonstrate new version of software developed over the course of the sprint.
* **与冲刺阶段的计划工作相比，总结进展**。Summarise progress compared with planned work for sprint.
  * **完成的额外工作** Additional work completed
  * **遗漏的功能** Missed features
* **解释偏离计划的原因。** Explain reason for deviations from plan.
* **确定要添加到系统中的新功能。** Identify new features to be added to the system

# Key point

敏捷方法通过频繁的审查和对项目目标和过程的微小调整来管理项目风险。
Agile methods manage project risk through frequent reviews and small adjustments to project objectives and process.

# 团队成员数量

Adding more team members is not necessarily a way to complete a task faster. If there are a lot of dependencies between tasks, you still need to wait for certain tasks to be completed before starting others. Also, if you need to train members of the team to do a task, then this takes time and adding more people means taking more time to train them. If a task cannot be broken down into smaller tasks, then obviously adding more people will have no effect (assuming one person can work on a task at once) as there will not be many tasks for people to do. Finally, if the project is complex, then adding too many team members will mean that more time is spent communicating with new team members and getting them up to speed with progress than is actually spent on the project itself. > Most software projects can be categorised as complex, and adding more team members above an optimal number will make the project take longer.

添加更多的团队成员不一定能更快地完成任务。如果任务之间有很多依赖关系，你仍然需要等待某些任务完成后再开始其他任务。另外，如果你需要培训团队成员来完成一项任务，那么这就需要时间，增加更多的人意味着要花更多的时间来培训他们。如果一项任务不能被分解成更小的任务，那么显然增加更多的人不会有任何影响（假设一个人可以同时处理一项任务），因为不会有很多任务让人们去做。最后，如果项目很复杂，那么增加太多的团队成员就意味着花在与新的团队成员沟通和让他们加快进度上的时间要比实际花在项目本身上的时间多。> **大多数软件项目都可以被归类为复杂的项目，增加超过最佳人数的团队成员会使项目耗时更长**。