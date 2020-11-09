# Software Process Improvement

study note

* [Software Process Improvement](#software-process-improvement)
  * [Process Improvement Frameworks](#process-improvement-frameworks)
  * [Principles/Pre-conditions for agile process improvement](#principlespre-conditions-for-agile-process-improvement)
  * [Process Improvement Process](#process-improvement-process)
  * [Scrum Retrospective](#scrum-retrospective)
    * [Gather data from team members](#gather-data-from-team-members)
    * [root cause: five whys](#root-cause-five-whys)
    * [Monitoring & Measuring](#monitoring--measuring)
  * [Continuous Process Improvement](#continuous-process-improvement)
  * [Improve Process Improvement](#improve-process-improvement)

## Process Improvement Frameworks

过程改进的方法/框架

* ISO 9001
* 6 Sigma
* **CMMI (Capability Maturity Model)**

## Principles/Pre-conditions for agile process improvement

敏捷开发**软件过程改进 - 理论/先决条件**

![](/static/2020-10-27-20-39-40.png)

* 评测有特定域
  * **仅适用于1个项目内的改进**
* 改进是**持续性活动** on-going activity
  * a series of sprints & process
* 改进**需要整个团队的参与**
  * maximise the opportunity of data gathering & ideas for improvements
  * team members feel bring-in actions & participate the debate & stake on decision taken
* **最大努力假设 assume every team member try their best to improve the process in that sprint**， 结果：
  * 努力工作不会改进开发过程 work harder wont improve the process
  * 责备毫无意义，可能会适得其反 Blaming is pointless & counterproductive
* **根本原因** root causes(real reason why a software failed) **should be solved** during the improvement process, not symptoms

## Process Improvement Process

开发过程的**改进过程**?

![](/static/2020-10-27-20-44-14.png)

* gather data about software process **收集过程相关数据**
  * gather from team members
* analyse **分析**
  * using automatic tool: version control system, static analysis tool,.etc
* identify actions **决定措施**
  * actions to address the problems
* Prioritise and implement **优先&实现**
  * prioritise the actions & decide which one to be implemented

## Scrum Retrospective

Scrum团队回顾会议（过程改进的一种方法？）[after the review meeting with the customers]

* timing 计时
* participants参与者
  * scrum master & team members
  * **or some teams include product owner & customers(could make data gathering harder, since team member may not be willing to be frank about previous sprint)**
* duration 时长
  * **at least 30 mins**, vary from different sprints
  * may be longer for longer sprint, **but maxmium should be within half a day**
* data sources 数据源
  * project artifacts 项目元件
    * **objective** about problem evidence
  * **team members 团队成员**
    * useful, team provides detailed insights about sprints
    * **subjective**
  * 结合前两个，可以互相弥补

### Gather data from team members

improvement process improvement - gather data about process 【general process conducting the retrospective】

![](/static/2020-10-27-20-50-22.png)
![](/static/2020-10-27-21-01-25.png)

* 基本方案
  * **团队选择一个模板，分成不同区域，团队成员单独使用便签填充每个模板区** populate the template board independently using sticky ntoes
  * **相似的点合并** similar items are grouped together
  * **就优先issues进行投票讨论**
  * **讨论根本原因root cause**（出现在软件开发过程改进中的问题根本原因？）
  * **选择措施进行改进** actions for improvement

🍊 各种数据收集模板

* Stop, start, continue
* Mad, sad, glad
* Loved, learned, lacked/longed for
* Sailboat

🍊 data gathering templates example：stop,start,continue

![](/static/2020-10-27-21-04-30.png)
![](/static/2020-10-27-21-07-23.png)

* 我们做得好，应该继续做什么，或者做得更多？
* 我们做了什么没有帮助的事情，我们应该停止？
* 我们应该开始做哪些我们还没有做的事情？

### root cause: five whys

（敏捷？开发改进过程中出现问题的？）根本原因

**root cause不是问题为什么出现的原因**

* **而是一种可行solution，substantial/underlying潜在的最真实原因**
* 找到根本原因可以防止问题的再次发生 to recover the problem in the future

![](/static/2020-10-27-21-09-19.png)

* observation: 第一个发行版发布过晚
  * why：整合花费的时间比预期的要长
  * why（ask again）：在迭代后期出现了一些不兼容的问题
  * why(again)：在迭代结束时才进行整合
  * **root cause: 应该在整个迭代期间进行整合**

🍊 不一定真要问5次，Empirical evidence

* 可多，可少，只要能找到根本原因

### Monitoring & Measuring

一旦找出了可采取的措施，之后：

![](/static/2020-10-27-21-23-42.png)

* 为下一个发行版/里程碑创建适当ticket
* 确保每个措施有负责人
* 未来回顾会中讨论中应包括先前决策&措施
* 回顾，过程回顾的过程

## Continuous Process Improvement

continuous retrospective

## Improve Process Improvement

改进过程改进

![](/static/2020-10-27-21-28-46.png)

**回顾会议可能会变得陈旧（需要改进**）:

* 改变结构
  * using different retrospective templates board(gathering data)
  * include different participants
* 反思回顾本身
* 尝试不同的频率的回顾
* 允许其他团队成员协助

🍬 关键点

* 过程改进
  * 是基本**质量保证的过程**Process improvement is an essential **quality assurance process**