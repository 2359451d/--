# Software Process Improvement & Retrospectives

本周的讲座涵盖了软件过程改进的原则和敏捷的回顾性实践。This weeks lectures cover the principles of software process improvement and the agile retrospective practice.

* [Software Process Improvement & Retrospectives](#software-process-improvement--retrospectives)
* [软件过程改进定义：Software Process Improvement](#软件过程改进定义software-process-improvement)
* [过程改进框架：Process Improvement Frameworks](#过程改进框架process-improvement-frameworks)
* [软件过程改进 - 理论/先决条件：Principles/Pre-conditions for agile process improvement](#软件过程改进---理论先决条件principlespre-conditions-for-agile-process-improvement)
* [过程改进流程：Process Improvement Process](#过程改进流程process-improvement-process)
* [回顾会议：Scrum Retrospective](#回顾会议scrum-retrospective)
* [收集团队成员数据：Gather data from team members](#收集团队成员数据gather-data-from-team-members)
* [根本原因 root cause: five whys](#根本原因-root-cause-five-whys)
* [结束回顾会后：Monitoring & Measuring](#结束回顾会后monitoring--measuring)
* [持续过程改进：Continuous Process Improvement](#持续过程改进continuous-process-improvement)
  * [为什么？回顾会缺点](#为什么回顾会缺点)
  * [Improve Process Improvement](#improve-process-improvement)
* [Summary](#summary)

# 软件过程改进定义：Software Process Improvement

**软件工程流程**可以包含一系列旨在支持质量保证的实践。例如，版本控制、计划、持续集成、自动测试、代码审查、持续重构等等。Software engineering processes can encompass a whole range of practices that are intended to support quality assurance. Examples include version control, planning, continuous integration, automated testing, code reviews, continual refactoring and so on.

许多团队不会在一开始就实施每一项实践，而**团队所遵循的一系列实践将随着时间的推移而不断发展** Many teams won’t start with implementing every single practice right at the start, and the set of practices followed by a team will evolve over time.

:orange: 软件过程改进实践，<font color="red">为一个团队提供了一种【结构化的手段】，以反思他们的软件过程并确定改进的机会</font> Software process improvement practices provide a structured means for a team to reflect on their software process and identify opportunities for improvement.

* 指责并不是一种解决办法。 Blame is not a solution.
* 过程中的问题不会因为更加努力工作而得到解决。Process problems won’t be fixed by working harder.

# 过程改进框架：Process Improvement Frameworks

在敏捷的背景下，**过程改进框架**类似于定义的软件开发流程。**它们预先定义了成熟的软件流程的特征，而不是允许进行经验性的测量、目标设定和调整**。In the context of agile, process improvement frameworks are analagous to defined software development processes. They pre-define the characteristics of a mature functioning software process, rather than permitting empirical measurement, goal setting and adjustment.

---

过程改进的方法/框架

* agile retrospectives
* ISO 9001
* 6 Sigma
* **软件能力成熟度模型 CMMI (Capability Maturity Model)**
  * 奇怪的是，CMM将最成熟的软件过程确定为**优化过程**，并以非常类似于Schwaeber描述经验过程控制的方式描述它们。然而，**他们预计软件过程首先通过不太成熟的结构（可重复的、定义的和管理的）过渡**。Oddly, the CMM identifies the most mature software processes as optimising and describes them in a very similar way to how Schwaeber would describe empirical process control. However, they anticipate that software processes transition through less mature structures first (**repeatable, defined and managed**).

# 软件过程改进 - 理论/先决条件：Principles/Pre-conditions for agile process improvement

敏捷开发**软件过程改进 - 理论/先决条件**

![](/static/2020-10-27-20-39-40.png)

* **测量是针对具体领域的** Measurement is domain specific
  * **仅适用于1个项目内的改进**
* 改进是**持续性活动** Improvement is an on-going activity
  * a series of sprints & process
* 改进**需要整个团队的参与** Requires whole team participation
  * 最大限度地提高数据收集和改进意见的机会 maximise the opportunity of data gathering & ideas for improvements
  * 团队成员感受到行动的重要性，并参与辩论，对所做的决定有兴趣 team members feel bring-in actions & participate the debate & stake on decision taken
* **最大努力假设 assume every team member try their best to improve the process in that sprint**， 结果：
  * 努力工作不会改进开发过程 work harder wont improve the process
  * 责备毫无意义，可能会适得其反 Blaming is pointless & counterproductive
* **根本原因** root causes(real reason why a software failed) **should be solved** during the improvement process, not symptoms

# 过程改进流程：Process Improvement Process

![](/static/2020-10-27-20-44-14.png)

* gather data about software process **收集过程相关数据**
  * 数据是由各种来源收集的，包括项目团队成员和自动工具 Data is gathered from a variety of sources, including project team members and automated tools. Some additional metrics may be synthesised from this data.
* analyse **分析**
  * 然后，收集到的数据被用来评估项目团队在前一次迭代中的表现，并**确定问题的根源**。The data gathered is then used to evaluate the project team’s performance in the previous iteration and identify root causes of problems.
  * using automatic tool: version control system, static analysis tool,.etc
* identify actions **决定措施**
  * actions to address the problems
  * 团队可能会根据上一次迭代的结果，确定一系列潜在的行动 The team may identify a whole range of potential actions to take as a result of the previous iteration.
* Prioritise and implement **优先&实现**
  * prioritise the actions & decide which one to be implemented
  * 最后，选定的行动将在下一次迭代中实施 Finally, the selected actions are implemented during the next iteration.

:orange: 实施这些行动的效果将在下一阶段的工作完成后进行审查。The effect of implementing these actions will be reviewed after the next period of work is complete.

# 回顾会议：Scrum Retrospective

**敏捷中最流行的流程改进方式之一是Scrum回顾。复盘发生在迭代之间**。 One of the most popular ways of process improvement in agile is the scrum retrospective. Retrospectives occur between iterations.

* **团队成员可以提供非常详细的信息，因为他们是团队中从事该项目工作的人，但他们也可能相当主观，所以获得所有团队成员的意见很重要。你也可以使用项目的人工制品作为项目历史表现的信息来源**。 The team members can provide very detailed information, as they were the ones in the team working on the project, but they can also be quite subjective so it is important to get the views of all team members. You can also use project artefacts as a source of information about the project’s historic performance.

发生在**迭代结束时，在与客户的审查会议之后** Occurs at the end of the sprint, after the review meeting with the customer

Scrum团队回顾会议（过程改进的一种方法？）[after the review meeting with the customers]

* timing 计时
* participants参与者 （通常是整个开发团队）
  * scrum master & team members
  * 或者有些团队包括产品所有者和客户（可能会使数据收集更加困难，因为团队成员可能不愿意对之前的冲刺坦诚相待 **or some teams include product owner & customers(could make data gathering harder, since team member may not be willing to be frank about previous sprint)**
  * Scrum master经常充当促进者，特别是在新团队中。有些团队包括产品所有者甚至是客户，尽管这可能会使数据收集更加困难。 Participants is normally the whole development team. Scrum master often acts as a facilitator, particularly in new teams. Some teams include the product owner or even the customer, although this can make data gathering harder.
* **duration 时长**
  * 时间可以不同，但如果做得好的话，至少应该是30分钟（以便于收集数据），如果是较长的冲刺阶段，应该更长。最长的时间应该是半天 Duration can vary, but should be at least 30 minutes if done properly (to allow for data gathering) and should be longer for longer sprints. Maximum time should be half a day.
  * **at least 30 mins**, vary from different sprints
  * may be longer for longer sprint, **but maxmium should be within half a day**
* data sources **数据源**
  * project artifacts 项目元件
    * **objective** about problem evidence
    * 对于问题的客观证据来说，项目元件更好，但可能需要仔细解释。缺陷报告的突然增加可能是因为一个没有经验的同事加入了团队，他还在学习质量保证的做法 Artifacts are better for objective evidence of problems, but may need to be interpreted carefully. A sudden increase in defect reports may be because an inexperienced colleague has joined the team, who is still learning quality assurance practices.
  * **team members 团队成员**
    * useful, team provides detailed insights about sprints
    * **subjective**
    * 团队成员可以提供非常详细和反思的见解。从团队成员那里收集数据也有助于获得对后续行动的认同。当然，团队成员会有主观的观点，所以这需要得到验证 Team members can provide very detailed and reflective insights. Gathering data from team members also helps to get buy in for subsequent actions. Of course, team members will have subjective view points, so this needs to be validated.
  * 结合前两个，可以互相弥补

# 收集团队成员数据：Gather data from team members

improvement process improvement - gather data about process 【general process conducting the retrospective】

![](/static/2020-10-27-20-50-22.png)
![](/static/2020-10-27-21-01-25.png)

* 基本方案
  * **团队选择一个模板，分成不同区域，团队成员单独使用便签填充每个模板区** Team members populate the a template board independently using sticky notes
  * **相似的点合并** similar items are grouped together
  * **就优先issues进行投票讨论** Team votes on priority issues for discussion
  * **讨论根本原因root cause**（出现在软件开发过程改进中的问题根本原因？） Discussion to reveal the root cause
  * **选择措施进行改进** Select actions for improvement

🍊 各种数据收集模板

* Stop, start, continue
* Mad, sad, glad
* Loved, learned, lacked/longed for
* Sailboat

🍊 data gathering templates example：stop,start,continue

![](/static/2020-10-27-21-04-30.png)

* 我们做得好，应该继续做什么，或者做得更多？
* 我们做了什么没有帮助的事情，我们应该停止？
* 我们应该开始做哪些我们还没有做的事情？

![](/static/2020-10-27-21-07-23.png)

* 上例可以观察到几件事
  * 团队已经开始对问题进行分组（not VOTE），这表明两名团队成员对提交破损代码的做法感到担忧（估计是提交给master），而且他们希望站立会的频率更高（也许每隔几天才进行一次）The team have started grouping issues, which reveals that two team members are concerned about the practice of committing broken code (presumably to master) and also that they would like standups to be more frequent (perhaps they are only happening every few days).
  * 团队还没有进行跨主题的分组。这将揭示出小组编程和审查是几个团队成员可能想要讨论的主题，因为结对编程、暴徒编程和代码审查都是单独提到的。The team haven’t yet grouped across themes. This would reveal that group programming and review is a topic that several team members might want to discuss as pair programming, mob programming and code reviews are all mentioned separately.
  * Start往往会显示出团队成员**已经思考过的候选解决方案，而不是一个根本问题(not root cause)**。<font color="red">根本原因分析可以帮助确定他们为什么要采用这个建议</font>。这可能会导致更好的解决方案 Start tends to reveal candidate solutions that a team member has thought about, rather than an underlying problem. A root cause analysis can help identify why they want to adopt this recommendation. This might lead to better solutions.
  * **团队还没有对问题进行投票**。请注意，这应该与分组分开进行，因为只有一个人产生的问题(分组里只有一个人提出)可能仍然是最优先的 The team haven’t yet voted on issues. Note that this should be done as a separate step from grouping because an issue that only one person generated may still be the highest priority.

# 根本原因 root cause: five whys

（敏捷？开发改进过程中出现问题的？）根本原因

**root cause不是问题为什么出现的原因**

* **而是一种可行solution，substantial/underlying潜在的最真实原因**
* 找到根本原因可以防止问题的再次发生 to recover the problem in the future

请注意，"5 "是一种启发式方法--根本原因可能会通过询问更多或更少的问题来揭示。判断你是否已经确定了根本原因的一个很好的指南是，询问应用所建议的解决方案是否能够防止它在未来再次发生。Note that ‘5’ is a heuristic - the root cause may be revealed by asking the question why more or less times. A good guide to whether you’ve identified the root cause is to ask whether applying the proposed solution would prevent it from reoccurring in the future.

![](/static/2020-10-27-21-09-19.png)

* observation: 第一个发行版发布过晚
  * why：整合花费的时间比预期的要长
  * why（ask again）：在迭代后期出现了一些不兼容的问题
  * why(again)：在迭代结束时才进行整合
  * **root cause: 应该在整个迭代期间进行整合**

🍊 不一定真要问5次，Empirical evidence

* 可多，可少，只要能找到根本原因

# 结束回顾会后：Monitoring & Measuring

**在回顾性工作结束时，你需要优先考虑哪些行动要在下一次迭代中解决或进行工作。你可以根据优先级、实施的难易程度、或投资回报的多少来选择这些行动**。 At the end of the retrospective, you need to prioritise which actions are going to be resolved or worked on during the next iteration. You might choose these by priority, by how easy they are to implement, or by how much they will provide a return on any investment required.

重要的是要监控回顾中决定的审查行动，以确保这些行动得到跟进。**这可能很简单，因为要确保有人负责每项行动，或者为下一个里程碑制作票据**。你也可以把以前的决定纳入到未来的回顾性研究中。 It is important to monitor the review actions decided upon in a retrospective, to ensure the actions are followed up on. This might be as simple as ensuring someone is responsible for each action, or making tickets for the next milestone. You can also include previous decisions in future retrospectives.

---

一旦找出了可采取的措施，之后：

![](/static/2020-10-27-21-23-42.png)

* **为下一个发行版/里程碑创建适当ticket** Create tickets as appropriate for next milestone/release
  * identify actions
* **确保每个措施有负责人** Ensure someone is responsible for each action
  * 最后，选定的行动将在下一次迭代中实施 Finally, the selected actions are implemented during the next iteration.
* **未来回顾会中讨论中应包括先前决策&措施** Include previous decisions in future retrospectives
* **回顾，过程回顾的过程** Review the process review process

# 持续过程改进：Continuous Process Improvement

## 为什么？回顾会缺点

continuous retrospective

**回顾性思考的一个缺点是，它可能每隔两到四周才会进行一次**。A disadvantage of retrospectives is that they may only take place every two to four weeks.

* 一个团队成员可能会发现一个问题，而这个问题会持续2-4周（不能被解决）。A team member may identify a problem that is left to persist for 2 - 4 weeks.
* 在迭代期间注意到的问题可能不会在回顾中被回忆起来（因为回顾会频率不高，有可能忘记）Problems noted during a sprint may not be recalled in the retrospective.
* 细节和情况可能会被遗忘 Details and circumstances may be forgotten.

---

:orange: 有些团队采用持续的方法进行过程改进，<font color="red">一旦发现流程问题，就立即诊断并应用解决方案</font> Some teams adopt a continuous approach to process improvement, diagnosing and applying solutions to process problems as soon as they are identified

* 意味着**问题可以得到快速有效的处理** Means that the problem can be dealt with quickly and effectively.
* 可能意味着**团队不参与诊断或实施解决方案** May mean the team isn’t involved in the diagnosis or implementing the solution.
* 如果**在迭代期间进行更改，可能会对整个软件流程造成破坏** May be disruptive to the overall software process if changes are made during a sprint.
* <font color="red">一个折中方案可能是实时诊断并推荐解决方案，但在回顾期间与团队讨论</font> A compromise may be to diagnose and recommend a solution in real time, but discuss with the team during a retrospective.

## Improve Process Improvement

改进过程改进

![](/static/2020-10-27-21-28-46.png)

**回顾会议可能会变得陈旧（需要改进**）: Retrospective meetings can become stale - important to:

* **改变结构** Vary the structure
  * using different retrospective templates board(gathering data)
  * include different participants
* **反思回顾本身** Reflect on the retrospective itself
* **尝试不同的频率的回顾** Experiment with different frequencies
* **允许其他团队成员协助** Allow other team members to facilitate

# Summary

🍬 关键点

**过程改进是一个重要的质量保证过程** Process improvement is an essential quality assurance process

Consider the following user stories, concerning the development of a smartphone application to allow patients with breathing difficulties to monitor their ‘peak flow’ capacity. The application can have a Bluetooth link to a peak flow meter, which they blow into as hard as they can to create a reading, in litres/minute. Peak flow readings of less than 250 l/min are potential signs of problems, such as an asthma attack.