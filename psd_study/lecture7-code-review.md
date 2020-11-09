# Lecture 7 Code Reviews

## What can be reviewed

![](/static/2020-11-03-21-16-06.png)

什么可以进行审阅

* 源码 app source code
* 源码文档 source code documentation
  * design
* 测试代码 test code
* 设计描述 design description
  * what the system should do
* 需求详情 requirements sepecifications

all the things in version control system could be reviewed

## Why undertake code Reviews

为什么进行代码审阅

![](/static/2020-11-03-21-18-35.png)

* 发现软件缺陷 detect software defects
* 尝试找到弱结构代码重构机会 identify refactoring chance for poorly structured code
* 开发者间对于共享代码的理解 develop a **shared understanding** of code between developers
* 代码审阅 - 团队的良好实践 good practice between team members
* 获得关于 **Legacy System（原有的系统，数据源系统）**的知识

### 代码审阅不应用于

![](/static/2020-11-03-21-22-25.png)

do not pernalty

## when do code reviews

何时进行代码审阅
![](/static/2020-11-03-21-23-14.png)

* 定期地在接受的代码库的一部分上 **periodically on a portion of the accepted code base**
* 针对代码库的建议更改上，proposed change to code base
* 在对原有系统的拟议更改之前进行 before a proposed change to a legacy system

## Code Review 有用吗 & 缺陷检测率

do code reviews work?(on quality)

![](/static/2020-11-03-21-25-44.png)

缺陷检测率 defect detection rate

* 缺陷检测率= (任意方法)检测出的缺陷/总缺陷
  * 总缺陷= 检测出的缺陷 + 未检测出的缺陷

## code review workflow

代码审阅工作流程

![](/static/2020-11-03-21-29-35.png)

* change proposal更改拟议（merge/pull request合并请求）(初始化更改拟议)
* 分配审阅者 nominate reviewers
* 进行审阅更改 review change
  * 接收更改 approve change
  * 审阅者注释，建议 reviewer sumbits recommendations
    * （可以涉及针对重变更的讨论 discussion）
    * 请求更改者再进行更改后，审阅者重新审阅 make changes then reviewer review change(again)

## Merge Requests: good practice

合并请求的良好实践

![](/static/2020-11-03-21-34-19.png)

* 每个合并请求尽量小 keep merge request small
  * **a single review最大300line**
  * **审阅能在30min内完成** finish within 30 min
  * 将大的合并请求分解小 break large merge requests in smaller change packages
  * **将代码审阅时间合并到团队的工作工作模型中** incorporate code review time into team's workload model
    * it should also be considered as part of the project time

### Describing the change

commit comment?
描述更改详情

* 给出一个简洁的标题 succinct title
* 解释更改目的 why（rationale）
  * corrective 改正
    * 改正性维护是指**改正在系统开发阶段已发生而系统测试阶段尚未发现的错误。**这方面的维护工作量要占整个维护工作量的17%～21%。所发现的错误有的不太重要，不影响系统的正常运行，其维护工作可随时进行：而有的错误非常重要，甚至影响整个系统的正常运行，其维护工作必须制定计划，进行修改，并且要进行复查和控制。
    * 纠正性维护: 在交付后对软件产品进行的反应性修改，以纠正发现的问题。纠正维护可以通过自动修复错误来实现自动化。
  * adaptive 自适应
    * **适应性维护是指使用软件适应信息技术变化和管理需求变化而进行的修改。**这方面的维护工作量占整个维护工作量的18%～25%。由于计算机硬件价格的不断下降，各类系统软件屡出不穷，人们常常为改善系统硬件环境和运行环境而产生系统更新换代的需求；企业的外部市场环境和管理需求的不断变化也使得各级管理人员不断提出新的信息需求。这些因素都将导致适应性维护工作的产生。进行这方面的维护工作也要像系统开发一样，有计划、有步骤地进行。
    * 自适应维护: 在交付后对软件产品进行的修改，以使软件产品在更改或更改的环境中仍然可用。
  * perfective 完善维护？
    * 完善性维护是为扩充功能和改善性能而进行的修改，**主要是指对已有的软件系统增加一些在系统分析和设计阶段中没有规定的功能与性能特征。**这些功能对完善系统功能是非常必要的。另外，还包括对处理效率和编写程序的改进，这方面的维护占整个维护工作的50%～60%，比重较大．也是关系到系统开发质量的重要方面。这方面的维护除了要有计划、有步骤地完成外．还要注意将相关的文档资料加入到前面相应的文档中去
    * Perfective maintenance: Modification of a software product after delivery to improve performance or maintainability.完善的维护: 在软件产品交付之后对其进行修改，以提高性能或可维护性。
  * preventative 预防/避免
    * 预防性维护为了改进应用软件的可靠性和可维护性，为了适应未来的软硬件环境的变化，**应主动增加【预防性的新的功能】，以使应用系统适应各类变化而不被淘汰**。例如将专用报表功能改成通用报表生成功能，以适应将来报表格式的变化。这方面的维护工作量占整个维护工作量的4%左右。
    * 预防性维护(Preventive maintenance) : 在软件产品交付之后对其进行修改，以便在它们成为有效的错误之前检测和纠正软件产品中潜在的错误
    * 是“定期检查的例行程序” ，目的是“发现小问题，并在大问题发生之前解决它们”[15]理想情况下，“没有什么坏掉。”[16]
    * **主要目标**
      * 提高资本设备的生产寿命。
      * 减少关键设备的故障。
      * 尽量减少设备故障造成的生产损失
  * (Lientz and Swanson 1980)

* 报告更广泛的变化影响，例如
突破变化 what change?

### 软件维护

![](/static/2020-11-04-00-00-05.png)

### Automate

自动审阅

![](/static/2020-11-03-21-39-55.png)

* **封锁任何不符合标准的请求**，不符合以下change标准
  * maximum size 超出大小（300line）
  * breaking tests 破坏测试
  * code style violations 违反代码风格
  * commented out code
  * spelling mistakes 拼写错误
  * inadequate tests 测试不足
* **静态&动态分析 - locating smells**
  * source code metrics 源码度量
  * design metrics 设计度量
    * detect the poorly structured code
  * test suit metrics 测试套件度量
  * incorrect usage patterns analysis 错误使用模式
    * for performance
* **创建合并请求模板，保证风格一致** merge requests template to ensure a consistent style

## Question to ask during review

non-exhaustive非详尽

![](/static/2020-11-03-21-46-14.png)

checklist of questions could be done during the review

目标 purpose

* 该更改是否解决问题?
* 怎样解决问题？
* 是否更新了相关的文档，如用户指南？

QA

* 使用这段代码的典型和极端情况是什么? 测试用例是否涵盖了这些情况？
* 是否考虑过边界检查pitfalls(空集合、参数边界...) ？
* 测试用例是否可读且组织良好？
* 这段代码如何处理异常情况？

architecture

* 更改是否遵循软件的现有体系结构约定？
* 是否错过了重用现有代码的机会？（是否不需要复用现有代码?）
* 在这个变化中是否存在克隆？

code

* 标识符是否遵循项目命名约定? 目的是否明确？
* 代码self-documenting？
* 是否所有的源代码注释都是必要的？

非功能性问题

* 性能优化是否可行？
* 效率优化是否可行？
* 是否遵循相关的安全模式？

## Realtime Code Reviews

实时代码审查

![](/static/2020-11-03-21-51-06.png)

* pair编程 - 结对编程
* mob编程
  * 结对编程的扩大

## Summary

代码审查是协作改进代码的有效手段。Code reviews are an effective means of collaboratively improving code.
