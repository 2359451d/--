# Lecture 7 Code Reviews

**代码审查是一种重要的、非常流行的软件质量保证实践，是对其他技术（如测试）的补充。本讲座在很大程度上是对该实践的简要介绍，但在网络上有很多很好的资源可供利用**。Code reviews are a important and very popular software quality assurance practice that complement other techniques such as testing. This lecture is very much a brief introduction to the practice, but there are many, many good resources available on the web.

---

* [Lecture 7 Code Reviews](#lecture-7-code-reviews)
* [审阅对象：What can be reviewed](#审阅对象what-can-be-reviewed)
* [为什么审阅：Why undertake code Reviews](#为什么审阅why-undertake-code-reviews)
* [代码审阅不应用于:don’t use code reviews for](#代码审阅不应用于dont-use-code-reviews-for)
* [何时审阅：when do code reviews](#何时审阅when-do-code-reviews)
* [Code Review 有用吗 & 缺陷检测率](#code-review-有用吗--缺陷检测率)
* [代码审阅流程：code review workflow](#代码审阅流程code-review-workflow)
* [合并请求的良好实践: Good practice for merge requests](#合并请求的良好实践-good-practice-for-merge-requests)
  * [良好实践-描述变更：Describing the change](#良好实践-描述变更describing-the-change)
  * [（补）变更目的-软件维护](#补变更目的-软件维护)
  * [审阅前自动化:Automate](#审阅前自动化automate)
* [审阅问题清单：Question to ask during review](#审阅问题清单question-to-ask-during-review)
* [实时代码审查：Realtime Code Reviews](#实时代码审查realtime-code-reviews)
* [Summary](#summary)

# 审阅对象：What can be reviewed

![](/static/2020-11-03-21-16-06.png)

什么可以进行审阅

* **源码** app source code
* **源码文档** source code documentation
  * design
* **测试代码** test code
* **设计描述** design description
  * what the system should do
* **需求规范** requirements sepecifications

管本讲座的重点是源代码审查实践，但各种各样的项目工件。最终，任何配置文件都可以被审查 Although this lecture focuses on source code review practices, a wide variety of project artifacts. Ultimately, any configuration item can be reviewed.

* 版本控制系统中的所有东西都可以被审查 all the things in version control system could be reviewed
  * 不以这种方式控制的工件不应该被检查，因为不能跟踪对工件的更改建议的结果 Artifacts that are not controlled in this way should not be inspected, as it is not possible to track the consequences of change recommendations to the artifact.

# 为什么审阅：Why undertake code Reviews

为什么进行代码审阅

![](/static/2020-11-03-21-18-35.png)

* **发现软件缺陷** detect software defects
  * <font color="red">代码审查的核心目的是检测缺陷并提出改进建议。然而，审查员的工作并不是要实际进行修改</font> The core purpose of code reviews is to detect defects and recommend improvements. However, it isn’t the job of the reviewer to actually make the changes.
* **尝试找到弱结构代码重构机会** identify refactoring chance for poorly structured code
* **开发者间对于共享代码的理解** develop a **shared understanding** of code between developers
  * 代码的集体所有权 collective ownership of code.
* **代码审阅 - 团队的良好实践** good practice between team members
* 获得关于 **Legacy System（原有的系统，数据源系统）**的知识

# 代码审阅不应用于:don’t use code reviews for

![](/static/2020-11-03-21-22-25.png)

do not pernalty

众所周知，检查可以激励人们交付更高质量的软件。然而，让审阅具有**惩罚性将适得其反，在团队中制造紧张气氛**。Inspections are known to incentivise the delivery of higher quality software. However, making this punitive will be counterproductive, creating tensions within a team.

# 何时审阅：when do code reviews

**代码审查，在历史上被称为检查，是软件开发人员检查源工件并建议修改的机会** Code reviews, historically referred to as inspections, are opportunities for software developers to inspect a source artifact and recommend changes.

何时进行代码审阅

![](/static/2020-11-03-21-23-14.png)

* **定期地在接受的代码库的一部分上** periodically on a portion of the accepted code base
  * 在历史上，代码审查被称为检查，并定期进行。例如，在**发布之前**，可能会**选择部分代码库进行检查**，以便发现缺陷并建议将其删除 Historically, code reviews were referred to as inspections and took place periodically. Portions of a code base might be selected for inspection prior to a release for example, in order to detect defects and recommend their removal.
* **针对代码库的建议更改上**，proposed change to code base
  * 现代代码审查通常是在创建合并请求后对代码库的拟议修改上进行的 Modern code review is typically conducted on proposed changes to a code base following the creation of a merge request.
* **在对原有系统的拟议更改之前进行** before a proposed change to a legacy system
  * 如果一个团队采用了一个遗留系统，也可能需要进行代码审查。在这种情况下，目标是在尝试改变之前**理解一部分代码库** A code review may also be necessary if a team has adopted a legacy system. In this case, the goal will be to comprehend a portion of the code base before attempting a change.

# Code Review 有用吗 & 缺陷检测率

do code reviews work?(on quality)

![](/static/2020-11-03-21-25-44.png)

缺陷检测率 defect detection rate

* 缺陷检测率= (任意方法)检测出的缺陷/总缺陷
  * 总缺陷= 检测出的缺陷 + 未检测出的缺陷

---

<li><span class="citation" data-cites="wilkerson12comparing">[@wilkerson12comparing]</span>发现，**与测试驱动开发的实施方法相比，审阅导致留在系统中的缺陷更少**。  found that inspections led to fewer defects left in a system than a test driven development approach to implementation.

<li><span class="citation" data-cites="runeson06what">[@runeson06what]</span>从12个案例研究的调查中报告了一个更复杂的情况。

* 其结果很可能取决于应用于不同方法的努力程度和目标项目的性质。 The results for may well depend on the amount of effort applied to different methods and the nature of the target projects.

# 代码审阅流程：code review workflow

代码审阅工作流程

![](/static/2020-11-03-21-29-35.png)

* **一个变更被提议**，A change is proposed
  * 例如作为合并请求，从源分支到目标分支。来源和目标的性质将取决于项目的类型。小型团队通常在master中维护开发分支。开放源代码项目接受来自fork仓库中的分支的合并请求  e.g. as a merge request, from a source branch to a target branch. The nature of the source and target will depend on the type of project. Small teams often maintain development branches within the master repository. Open source projects accept merge requests from branches in forked repositories
* **评审员从开发团队的其他成员中选出**。Reviewer(s) are selected from the rest of the development team
  * 该团队可能有这方面的指导原则。一些现代工具可以根据不同的策略推荐审查员，例如，谁在代码库中最频繁地修改了受影响的工件。许多团队认为，审查员至少应该和提交的开发人员一样有经验。然而，让资历较浅的同事进行代码审查也有好处。这可以帮助提高对代码的共同理解，以及引出构建代码的新方法 The team may have guidelines for this. Some modern tools can recommend reviewers based on different policies, for example, who has changed the affected artifacts most frequently in the code base. Many teams assume that reviewers should be at least as experienced as the submitting developer. However, there are benefits to having more junior colleagues conduct code reviews. This can help improve shared understanding of code, as well as eliciting new approaches to structuring the code.
* **审查者在合并请求上标注建议的修改** Reviewer(s) annotate merge request with recommended changes.
* 对于开发者和评审员来说，**就任何建议进行后续讨论通常是非常重要/有用的**。这可以帮助澄清为什么提出建议，以及评审员想要实现什么 Often very important/useful for developers and reviewers to have a follow up discussion about any recommendations. This can help clarify why the recommendation was made and what the reviewer is trying to achieve.
* 开发人员进行**修改并重新提交合并请求** Developer makes changes and re-submits merge request
* **审查者批准合并请求** Reviewer(s) approve merge request.
* **开发人员**应用**合并** Merge is applied by the developer.

---

# 合并请求的良好实践: Good practice for merge requests

典型的合并请求来自于服务器上版本库的本地分支（小型团队项目），或者来自fork版本库的分支（开源项目）Typical merge requests are from either from a local branch in a repository on the server (small team projects), or from a branch in a forked repository (open source projects).

* 有些团队通过自动拒绝任何过大的请求来强制执行小的合并请求，迫使开发人员将他们的更改分成小块 Some teams enforce small merge requests by automatically rejecting any request that is too large, forcing developers to break their changes into small chunks

![](/static/2020-11-03-21-34-19.png)

* 每个合并请求尽量小 keep merge request small
  * **a single review最大300line**
* **审阅能在30min内完成** finish within 30 min
* **将大的合并请求分解小** break large merge requests in smaller change packages
* **将代码审阅时间合并到团队的工作工作模型中** incorporate code review time into team's workload model
  * it should also be considered as part of the project time

## 良好实践-描述变更：Describing the change

commit comment?
描述更改详情

![](/static/2021-04-24-11-40-21.png)

* **给出一个简洁的标题** succinct title
* **解释变更目的（选1个**） why（rationale）
  * **corrective** 改正性变更
    * 改正性维护是指**改正在系统开发阶段已发生而系统测试阶段尚未发现的错误**这方面的维护工作量要占整个维护工作量的17%～21%。所发现的错误有的不太重要，不影响系统的正常运行，其维护工作可随时进行：而有的错误非常重要，甚至影响整个系统的正常运行，其维护工作必须制定计划，进行修改，并且要进行复查和控制。
    * 纠正性维护: 在交付后对软件产品进行的反应性修改，以纠正发现的问题。纠正维护可以通过自动修复错误来实现自动化。
    * <font color="red">纠正性更改应解决错误报告，并明确提及正在修复的错误。该请求应总结有缺陷的行为；以及通过更改实现的正确行为</font>。Corrective changes should resolve bug reports and refer explicitly to the bug that is being fixed. The request should summarise the defective behaviour; and correct behaviour realised by the change.
  * **adaptive** 适应性变更
    * **适应性维护是指使用软件适应信息技术变化和管理需求变化而进行的修改**。这方面的维护工作量占整个维护工作量的18%～25%。由于计算机硬件价格的不断下降，各类系统软件屡出不穷，人们常常为改善系统硬件环境和运行环境而产生系统更新换代的需求；企业的外部市场环境和管理需求的不断变化也使得各级管理人员不断提出新的信息需求。这些因素都将导致适应性维护工作的产生。进行这方面的维护工作也要像系统开发一样，有计划、有步骤地进行。
    * 自适应维护: 在交付后对软件产品进行的修改，以使软件产品在更改或更改的环境中仍然可用。
    * <font color="red">适应性变更是对系统环境变化的响应。一个常见的例子是需要迁移到依赖性或语言实现的新版本</font> Adaptive changes are responses to changes in a system’s environment. A common example is the need to migrate to a new release of a dependency or language implementation.
  * **perfective** 完善维护(变更)
    * 完善性维护是为扩充功能和改善性能而进行的修改，**主要是指对已有的软件系统增加一些在系统分析和设计阶段中没有规定的功能与性能特征**。这些功能对完善系统功能是非常必要的。另外，还包括对处理效率和编写程序的改进，这方面的维护占整个维护工作的50%～60%，比重较大．也是关系到系统开发质量的重要方面。这方面的维护除了要有计划、有步骤地完成外．还要注意将相关的文档资料加入到前面相应的文档中去
    * Perfective maintenance: Modification of a software product after delivery to improve performance or maintainability.完善的维护: 在软件产品交付之后对其进行修改，以提高性能或可维护性。
    * <font color="red">完善性变更增加了新的功能。变更请求应说明新功能的作用，并提及需求</font> Perfective changes add new features. The change request should state what the new feature does and refer to the reqiurement.
  * **preventative** 预防/避免性变更
    * 预防性维护为了改进应用软件的可靠性和可维护性，为了适应未来的软硬件环境的变化，**应主动增加【预防性的新的功能】，以使应用系统适应各类变化而不被淘汰**。例如将专用报表功能改成通用报表生成功能，以适应将来报表格式的变化。这方面的维护工作量占整个维护工作量的4%左右。
    * 预防性维护(Preventive maintenance) : 在软件产品交付之后对其进行修改，以便在它们成为有效的错误之前检测和纠正软件产品中潜在的错误
    * 是“定期检查的例行程序” ，目的是“发现小问题，并在大问题发生之前解决它们”[15]理想情况下，“没有什么坏掉。”[16]
    * **主要目标**
      * 提高资本设备的生产寿命。
      * 减少关键设备的故障。
      * 尽量减少设备故障造成的生产损失
    * <font color="red">预防性修改可以提高代码的可维护性。一个常见的例子是重构</font> Preventative changes enhance the maintainability of code. A common example is refactoring.

:orange: **报告**更广泛的变化影响，例如 **突破变化** Report wider impacts of the change, e.g. breaking changes.

## （补）变更目的-软件维护

![](/static/2020-11-04-00-00-05.png)

## 审阅前自动化:Automate

:orange: 代码审查的成本很高，所以应该**在合并请求准备好进行代码审查之前，尽可能多地自动消除问题**。代码<font color="red">审查的重点是那些无法自动化的主观判断的东西</font> Code reviews are expensive, so should eliminate as many issues as possible automatically, before a merge request is ready for code review. Code reviews focus on things that are subjective judgements that can’t be automated.

* 可以对 CI 工具进行配置，以便**在自动质量检查得到满足之前不能应用更改** The CI tool can be configured so that changes can’t be applied until automated quality checks are satisfied.

![](/static/2020-11-03-21-39-55.png)

* **封锁任何不符合标准的请求**，不符合以下change标准
  * maximum size 超出大小（300line）
  * breaking tests 破坏测试
  * code style violations 违反代码风格
  * commented out code 有注释的代码
  * spelling mistakes 拼写错误
  * inadequate tests 测试不足
* **静态&动态分析定位异味 - Static and dynamic analysis can be useful for locating smells.** 【<font color="red">所有这些指标都有助于帮助发现潜在的气味，这些气味也可能表明存在缺陷</font> All these metrics are useful for helping to spot potential smells that may also indicate defects.】
  * 静态分析集成是代码审查中最普遍要求的功能。自动分析允许审查员专注于理解能力和修改的可维护性，而不是被琐碎的评论（例如，关于格式的评论）分散注意力
  * **源码指标** source code metrics
    * 源代码指标包括每个函数或模块的代码行数以及可执行代码行和注释代码行之间的比率。Source code metrics include the number of lines of code per function or module and the ratio between executable and comment lines of code.
  * **设计指标** design metrics
    * detect the poorly structured code
    * 设计指标包括诸如扇入和扇出耦合、循环复杂度等内容 Design metrics include things like fan-in and fan-out coupling, cyclometric complexity.
  * test suit metrics 测试套件指标
    * 测试套件指标通常涉及测试套件的有效性和效率。例子包括测试代码覆盖率和变异测试 Test suite metrics usually address the effectiveness and efficiency of a test suite. Examples include test code coverage and mutation testing.
  * incorrect usage patterns analysis 错误使用模式
    * for performance
    * 使用模式分析可以发现诸如内存和 IO 泄漏或类型转换错误等问题 Usage pattern analysis can detect things like memory and IO leaks or type cast errors.
* **创建合并请求模板，保证风格一致** merge requests template to ensure a consistent style

# 审阅问题清单：Question to ask during review

non-exhaustive非详尽

![](/static/2020-11-03-21-46-14.png)

checklist of questions could be done during the review

**目标** purpose

* 该更改是否解决问题? Does this change solve a problem?
* 怎样解决问题？ How would I solve this problem?
* 是否更新了相关的文档，如用户指南？ Has dependent documentation, such as user guides been updated

**QA**

* 使用这段代码的典型和极端情况是什么? 测试用例是否涵盖了这些情况？
* 是否考虑过边界检查pitfalls(空集合、参数边界...) ？
* 测试用例是否可读且组织良好？
* 这段代码如何处理异常情况？

**architecture**

* 更改是否遵循软件的现有体系结构约定？
* 是否错过了重用现有代码的机会？（是否不需要复用现有代码?）
* 在这个变化中是否存在克隆？

**code**

* 标识符是否遵循项目命名约定? 目的是否明确？
* 代码self-documenting？
* 是否所有的源代码注释都是必要的？
* <font color="red">源代码注释应该解释为什么采用了某个特定的实现，而不是如何实现</font> Source code comments should explain why a particular implementation has been adopted, not how.

**非功能性问题** Non-functional considerations

* 性能优化是否可行？Are performance optimisations possible?
* 效率优化是否可行？Are efficiency optimisations possible?
* 是否遵循相关的安全模式？Are relevant security patterns followed

# 实时代码审查：Realtime Code Reviews

实时代码审查

![](/static/2020-11-03-21-51-06.png)

* pair编程 - 结对编程
* mob编程
  * 结对编程的扩大

# Summary

代码审查是协作改进代码的有效手段。Code reviews are an effective means of collaboratively improving code.

代码审查是一种重要的、非常流行的**软件质量保证实践**，**是对其他技术（如测试）的补充**