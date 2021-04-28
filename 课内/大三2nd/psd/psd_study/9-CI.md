# Lecture Note 9: CI

持续集成的目的是帮助防止一个被称为 "集成地狱 "的问题，**即你花在将功能集成到现有代码库中的时间比你制作新功能本身的时间要多**。这种情况的发生是**因为不同的功能是由不同的开发人员同时开发的，所以代码库中会出现冲突**。 Continuous integration is designed to help prevent a problem known as “Integration Hell” where you spend more time integrating features into the existing code base than you do making new features themselves. This happens because different features are being developed concurrently by different developers, so conflicts can arise in the codebase.

* [Lecture Note 9: CI](#lecture-note-9-ci)
* [Integration Hell](#integration-hell)
* [CI实践：continuous integration practices](#ci实践continuous-integration-practices)
* [变更管理：Change Management](#变更管理change-management)
  * [集成频率：Integration Frequence](#集成频率integration-frequence)
  * [CI中的特性分支选择:Feature branching options in CI](#ci中的特性分支选择feature-branching-options-in-ci)
  * [维护多个 CI流程: Maintaining Multiple CI Processes](#维护多个-ci流程-maintaining-multiple-ci-processes)
* [构建监控&维护软件质量：Monitor & Maintain Software Quality](#构建监控维护软件质量monitor--maintain-software-quality)
  * [维护软件质量流程 - 检测破损构建](#维护软件质量流程---检测破损构建)
  * [避免破损公共构建：Preventing Broken Public Builds](#避免破损公共构建preventing-broken-public-builds)
  * [避免分支损坏构建:Preventing Broken builds with branches](#避免分支损坏构建preventing-broken-builds-with-branches)
* [暂存环境：Staging Environments](#暂存环境staging-environments)
  * [暂存环境局限性：Limitations of staging environments](#暂存环境局限性limitations-of-staging-environments)
* [CI环境：CI Environments](#ci环境ci-environments)
* [CI环境流程：CI Environment Workflow](#ci环境流程ci-environment-workflow)
* [管理CI过程状态可见性 & 度量指标：Maintaining Visibility & Metrics](#管理ci过程状态可见性--度量指标maintaining-visibility--metrics)
  * [例子 - CI Gitlab Metrics](#例子---ci-gitlab-metrics)
* [快速构建对CI的重要性：Fast build are essential](#快速构建对ci的重要性fast-build-are-essential)
* [Advanced topics](#advanced-topics)
  * [Summary](#summary)
* [CI好处](#ci好处)
* [持续部署](#持续部署)
* [CI自动测试](#ci自动测试)
* [为什么每天提交](#为什么每天提交)
* [CI vs 夜晚构建](#ci-vs-夜晚构建)

# Integration Hell

该术语指的是这样一种情况：**软件开发人员花在将改变的源代码重新整合到软件系统中的时间比他们首先创建新功能的时间还要多**。<font color="red">这种情况会随着软件的发展而发生，尽管使用了源代码管理系统，因为其他开发者可能同时做出了一些改变</font>。The term  refers to a situation where a software developer spends more time trying to re-integrate altered source code into a software system than they did creating the new feature in the first place. This situation can occur as software evolves, despite the use of source code management systems, because other developers may have concurrently made changes that:

* 阻止软件系统被编译，或导致其无法通过某些测试 prevent the software system from being compiled, or cause it to fail some of its tests;
* 与当前软件开发者所建立的新功能相冲突 conflicts with the new features that the current software developer has built.

:orange: 这些问题往往会在开发者试图进行以下工作时加剧 These problems tend to be exacerbated when a developer attempts to:

* 一次性集成一个非常大的代码库变更；或integrate a very large change to a code base in one go; or
* 当新功能不经常被集成时 when new features are integrated infrequently.

:orange: 当然，这些因素是相互关联的。**对软件系统进行的非常大的改动**往往涉及相当大的工作量，**这意味着将软件重新整合到主代码库的时间更长**。Of course, these factors are linked. Very large changes to a software system often involve a considerably greater amount of work, meaning longer periods between reintegration of the software into the main code base.

# CI实践：continuous integration practices

![](/static/2021-04-24-18-06-39.png)

连续集成是一种被提出来的技术，它是**缓解整合连续和并发的软件开发工作问题的手段**。Fowler（2006）将持续集成的实践总结为：Continuous integration</em> is a technique proposed as a means of mitigating the problem of integrating continuous and concurrent software development efforts. Fowler (2006) summarises the practices in continuous integration as (I have grouped related practices):

* **change management 变更管理实践**
  * 维护一个原repo Maintain a single source repository
  * 每个人**每天**向mainline提交 Everyone commits to the mainline every day
    * mainline- master branch, trunk
  * **每次提交都造成集成机器上构建主线**. Every commit builds the mainline on an integration machine
  * 每个人能看见发生什么 Everyone can see what’s happening
    * make transparency in single source repo
* **quality assurance CI质量保证实践**
  * 自动化构建 Automate the build
  * 使你的构建能够自我测试 Make your build self-testing
    * 有unit-test suite， 针对每个commit都能执行，构建后测试
  * 在生产环境的克隆中进行测试 Test in a clone of the production environment
    * **如果您在不同的环境中进行测试，每一个差异都会导致一种风险，即在测试中发生的事情不会在生产中发生**。
  * 保证构建快速 
Keep the build fast
    * within 10min
* **deployment 部署实践**
  * <font color="red">保证每个人能获取最新可执行文件</font> Make it easy for anyone to get the latest executable
    * 构建应该是自动的，并且任何人都可以访问的 build should be automatically & accessble by anyone who wanted
    * 任何参与软件项目的人都应该能够获得最新的可执行文件并能够运行它: 用于演示、探索性测试、或者只是看看本周发生了什么变化
  * **自动部署** Automate deployment
    * make it part of the pipeline

# 变更管理：Change Management

**持续集成提倡频繁地对代码进行小改动，而不是大范围的提交。因为这建议你应该不少于每天一次将分支合并到主干中，所以你需要谨慎地计划功能**。 Continuous integration advocates frequent small changes to the code, rather than big wide ranging commits. Because this advises that you should merge branches into the main trunk no less frequently than daily, you need to be careful how you plan features.

**最好是把功能分解成较小的任务，可以快速完成并合并，而不是把它作为一个大任务，可能需要几天的时间来实现，让主分支有时间偏离并导致以后的冲突问题**。 It is better to break features up into smaller tasks, that can be completed quickly and merged, rather than leaving it as one big task that might take days to implement, leaving the main branch time to deviate and cause conflict problems later.

## 集成频率：Integration Frequence

许多软件项目对新功能采用了【**后期集成**】的方法。这意味着项目的开发人员在一个迭代过程中，**各自在项目源代码的单独副本上工作，以实现新功能**。当每个开发者完成一个新功能时，他们提交他们对代码的修改，以重新整合到项目主干中。不幸的是，由于其他人也想在**同一时间重新整合变化，所有不同的新功能之间可能会有相当多的冲突，并且团队可能不得不在项目结束时花费大量时间进行整合**。Many software projects adopt a late integration approach to new features. This means that the developers on a project each work on a separate copy of the project’s source code to implement new features over the course of an iteration. As each developer completes a new feature they submit their changes to the code to be reintegrated into the project trunk. Unfortunately, because everybody else also wants to reintegrate changes at the same time, there may be a considerable amount of conflict between all the different new features and the team may have to spend a lot of time on integration at the end of the project.

![](/static/2021-04-24-18-07-23.png)

* 纵坐标表示 branch&trunk的差异 （x轴看为 baseline,master）
* 该图说明了为什么**延迟集成/后期集成**会在开发迭代结束时产生如此多的**额外工作**。 The figure illustrates why late integration creates so much extra work at the end of the development iteration.
* 水平通道代表时间，而垂直通道代表项目源代码版本之间的差异。The horizontal access represents time, while the vertical access represents differences between versions of project source code.
* 向上拱起的箭头代表源代码的一个分支，其中包含一个新功能。 The upward arching arrow represents a branch of the source code that contains a new feature.
  * 当开发者完成新功能时，它逐渐与时间0的基线相背离。**向下拱起的箭头表示当其他开发者对项目主干进行修改时，项目主干与基线的分歧**。 This gradually diverges from the baseline at time 0 as the developer completes the new feature.  The downward arching arrow is the divergence of the main project trunk from the baseline as other developers commit changes to it.
* 请注意，**主干与基线的分歧比特性分支更快，因为主干代表了其他每个开发者的努力**，他们的提交量比整个新特性（缺陷修复、性能增强等等）要小。这意味着当分支开发者最终准备重新整合时，将有相当多的工作要做，**因为他们是在基线没有被改变的基础上工作的** Notice that the trunk diverges from the baseline  faster than the feature branch, because the trunk represents the effort of every other developer making smaller commits than a whole new feature (defect fixes, performance enhancements and so on). This means that when the branch developer is finally ready to re-integrate there will be a considerable amount of work to do, since they were working on the basis that the baseline had not been altered.

![](/static/2021-04-24-18-41-15.png)

下图说明了**持续集成所倡导的另一种方法**。The next illustrates the alternative approach advocated for continuous integration.

* 在这里，<font color="red">分支开发人员将他们的工作划分为足够小的块，以便能够更频繁地集成变化（至少每天一次）</font>。 Here, the branch developer divides their work into sufficiently small chunks so that changes to can be integrated much more frequently (at least once a day).
* 当每个新的小任务完成后，它将立即被重新集成到主干中。这就限制了主干的指数发散率的影响，造成因后期整合而产生大量的重新整合工作。 As each new small task is completed, it is immediately re-integrated into the trunk. This limits the effect of the exponential rate of divergence of the trunk to create a large amount of reintegration work due to late integration.

## CI中的特性分支选择:Feature branching options in CI

对于主干开发、特定的客户开发和重要的功能分支，您可能需要不同的CI流程 You might want different CI processes for mainline trunk development, specific customer developments and for significant feature branches.

---

在其原生形式中，**持续集成不允许在项目中进行分支，因为所有的开发人员都应该对主干进行频繁的小改动**。一些项目团队发现这是不现实的，因为<font color="red">特性分支可以成为实验长期提案的有用手段。在这种情况下，允许使用分支可以使分支的变化在 SCM 仓库中得到维护</font> In its pure form, continuous integration doesn’t allow for  branching in the project, as all developers should make small frequent changes to the trunk instead. Some project teams find this impractical, as feature branches can be a useful means of experimenting with longer term proposals. Permitting the use of branches in this situation allows changes to the branch to be maintained in the SCM repository.

![](/static/2021-04-24-18-53-23.png)

如果要在一个用**CI管理的项目中使用分**支，那么有几种折中的策略来管理分支和主干之间的关系： If branches are going to be used in a project managed with CI, then there are several compromising strategies for managing the relationship between the branches and the trunk

:orange: **在主干上实现新功能，经常提交一些小改动** Implement the new feature on the trunk, with frequent commits of small changes.

* merge change from master frequently
* 这是 纯CI 主义者提倡的方法 This is the approach advocated by CI purists.

:orange: **在一个分支中实现新功能，并经常合并主干上的修改** Implement the new feature in a branch and merge changes from the trunk frequently.

* 这对**不确定性低的中期特性分支**来说效果最好。<font color="red">从主干中合并有助于确保该分支不会与主干相差太远，同时也不会影响主干本身</font> This works best for medium-term feature branches that are low uncertainty. Merging from the trunk helps to ensure that the branch never diverges too far from the trunk, whilst also not affecting the trunk itself.

:orange: **以原型分支的形式实现一个功能，然后在【主干上】以小的改动【重新实现】该功能**。Implement a feature as a prototype branch and then re-implement the feature on the trunk in small changes.

* **rather than merging from the prototype branch to trunk directly**
* useful to evaluate /explore the development of a **high risk feature**
* 这种方法允许分支在必要时发生分歧，并且可以为**评估高风险特性提供足够的自由度** This approach allows the branch to diverge as much as necessary and can be useful in providing sufficient freedom for evaluating high risk features.

:orange: **将特性作为一个永久的分支来实现** Implement the feature as a permanent branch.

* create a **folk** of the repo
* 作为独立的主干选择提供给客户，或在其母体项目中保持类似的代码库 as seperate trunk alternative provided to customers or maintain similar code base within its parent project
* 实际上，这在现有版本库中创建了一个项目。<font color="deeppink">该分支有效地成为另一个客户的独立主干项目，同时仍与父项目保持基本相似的代码库</font> In effect, this creates a  of the project within the existing repository. The branch effectively becomes a separate trunk project for an alternative customer, while still maintaining a substantially similar code base with the parent project.

## 维护多个 CI流程: Maintaining Multiple CI Processes

**对于主干开发、特定的客户开发和重要的功能分支，您可能需要不同的CI流程** You might want different CI processes for mainline trunk development, specific customer developments and for significant feature branches.


---

:orange: **分支的寿命越长，建立一个单独的CI流程就越重要**。<font color=""edr>一些较大的项目可能会为其项目的不同分支维护几个不同的永久性CI流程</font>。比如说 The longer-lived a branch, the more important it can be to set up a separate CI process. Some larger projects may maintain several different permanent CI processes for the different branches of their projects. For example:

* 最重要的**构建需要监控**。这种构建将同时包含新功能和缺陷修复的变更 the most important build to monitor. This will have both new features and defect fix changes applied
* **这些最终可能会成为他们自己的独立项目** These may eventually become separate projects in their own right.

![](/static/2021-04-24-19-05-27.png)

假设每个repo，project的CI都是独立的。但有情况需要一起管理不同分支上的CI

* **主干线开发** The mainline trunk development
* **为特定客户或目的而进行fork开发** Forked developments for specific customers or purposes.
* **最新的稳定版本，只接受缺陷修复** The latest stable release that only receives defect fixes.
* **重要特性分支** Significant feature branches.
  * 避免defects in branch before integrated into master

# 构建监控&维护软件质量：Monitor & Maintain Software Quality

什么是CI中成功的构建？What is meant by a successful build in continuous integration?

* 根据项目的需要，对代码进行**编译、测试和静态分析的任何组合**。Any combination of compilation, testing and static analysis of code as appropriate for the project.

**质量保证** --- 每次有人做出提交，都可能导致项目的破坏。为了避免这种情况，每次对系统进行修改时，我们都应该。**从源码编译一个克隆的生产环境 在整个系统上运行回归测试 执行静态分析检查，如代码风格的一致性，并将其与基准进行比较** Every time someone makes a commit, it could result in breaking the project. To avoid this, every time a change is made to the system we should: Compile from source a clone of the production environment Run regression tests on the complete system Perform static analysis checks such as code style conformity, and compare these to benchmarks

## 维护软件质量流程 - 检测破损构建

显然，**【不受控制地】高频率整合系统的变化可能会在无意中导致大量的缺陷被引入系统**。因此，需要<font color="red">采用同等频率的质量保证流程</font>，即 Clearly, uncontrolled high frequency integration of changes to a system could inadvertently lead to a large number of defects being introduced into a system. Therefore, quality assurance processes need to be employed with equivalent frequency, i.e.:

* 每次对系统进行更改时 Every time a change is made to the system.
* 如果任何一个步骤都失败了，那么构建就被认为是被破坏了。在这一点上，**破损的构建应立即被视为项目团队的最高优先级任务，其他开发活动（当然还有提交）应停止，直到构建不再破损**。 If any of the steps fail, the build is considered to be broken. At this point, a broken build should be immediately considered to be the highest priority task for a project team and other development activity (and certainly commits) should cease until the build is no longer broken

![](/static/2021-04-24-21-27-23.png)

:orange: **维护软件质量流程**，检测损坏构建 process to maintain software quality to  detecting broken builds

* **系统应该在一个克隆的生产环境中从源头上进行编译** The system should be compiled from source in a clone of the production environment.
* **应该在整个系统上执行一套自动化的回归测试** A suite of automated regression tests should be executed on the complete system.
* **应该执行适当的静态分析检查(例如代码样式的符合性) ，并与基准进行比较** Appropriate static analysis checks (such as code style conformity) should be performed and compared to benchmarks

---

## 避免破损公共构建：Preventing Broken Public Builds

![](/static/2021-04-24-21-36-49.png)

当然，在一个管理良好的项目中，破损的构建应该是相对**罕见的，因为在变更提交到主干之前，应该进行私有构建**。Of course, in a well managed project, a broken build should be relatively rare, because private builds should be undertaken before the change is committed to trunk.

* 首先，**项目源代码的最新版本被制作成一个工作副本**。为实现**新功能**而进行的必要**更改**，以及任何附带的**单元和验收测试** First, a working copy is made of the latest version of the project source code. The necessary changes to implement a new feature are made, along with any accompanying unit and acceptance tests.
* 然后，开发人员**在自己的环境中对他们的工作副本进行完整的私有构建**。Then, the developer performs a complete private build of their working copy in their own environment.
  * 如果失败了，就会进行更多的修改，直到构建通过。 If this fails, more changes are made until the build passes.
  * 一旦实现了成功的私有构建，所做的修改将被合并到主干中，并尝试进行公开构建。 Once a successful private build is achieved the modifications are merged into the trunk and a public build is attempted.
  * 如果失败了，那么开发者必须做进一步的修改，以确保公共构建尽快成功。这个循环一直持续到成功完成公共构建为止。  If this fails, then the developer must make further changes to ensure that the public build succeeds as soon as possible. This cycle continues until a successful public build is acheived.

1. clone & checking the branch
2. change made to code
3. private(testing) build
   1. compilation of the code
   2. running unit-test
   3. running appropriate analysis check
   4. 如果失败，回到2，重新测试
4. 如果build成功，merge to mainline
5. 进行public build,（testing步骤与3相同）
   1. pass
   2. fail回到2

## 避免分支损坏构建:Preventing Broken builds with branches

![](/static/2021-04-24-21-43-34.png)

一些软件团队要求开发人员在软件变更通过适当的**质量保证检查后，将变更提交给项目主干**。这意味着，除了上述过程之外，开发人员还必须采取所示的**额外步骤** Some software teams require a developer to  a software change passes appropriate quality assurance checks  the change is committed to the project trunk. This means that, in addition to the process described above the developer must take the additional steps shown.

* 有些团队使用feature branching, 并对每个分支应用CIpipeline
  * 对比上面选项1描述的步骤，需要**额外步骤**
* <font color="deeppink">针对为什么要使用特性分支，看前面的变更管理部分 - 维护多个CI流程 & CI分支选择</font>

:orange: **这些额外的步骤可以归纳为**：These additional steps can be summarised as:

* **在一个分支中开发一个特性** develop a feature in branch
* **将主干中的最新更改合并到分支中**。 merge the change from the trunk **into the branch**
* **对该分支进行成功的构建** perform a successful build on **branch**
* **将分支中的修改合并到主干中** merge the change to the branch **into the trunk**
* **对新的主干进行成功的构建** perform a successful build on **trunk**

🍬 <font color="red">虽然这种方法可以减少破坏性构建的数量，但它也可能产生不利的影响</font> Although this approach can reduce the number of broken builds, it may also have the adverse effect 

* 减少提交的频率，以及使单个提交的规模更大 reducing the frequency of commits, as well as making the size of a single commit larger.
* 这是因为整合一个新功能所需的工作量使得频繁执行这项任务的吸引力降低 This is because the amount of effort required to integrate a new feature makes it less attractive to perform this task frequently.

# 暂存环境：Staging Environments

**Staging是指在合并到主分支之前，将对系统的拟议修改部署到一个复制的真实世界环境中，以便对其进行测试。这是用来执行质量保证的** Staging is the act of deploying a proposed change to a system into a replica real-world environment in order to test it, before merging it into a master branch. This is used to perform quality assurance.

在向广大用户群发布软件之前，需要对其进行检查，以<font color="deeppink">确保任何拟议的更改不会引入额外的缺陷</font>。**一个暂存平台，一个配置为复制或模拟软件使用条件的环境，被用来在向用户发布之前测试软件**。<font color="red">暂存平台的配置与软件系统的当前基线的硬件和软件依赖性有关</font>。Changes to software need to be checked before they are released to a wider user base, in order to gain assurance that any proposed change does not introduce additional defects. A staging platform, an environment configured to replicate or simulate the conditions the software will be used in, is used to test the software before it is released to users. The staging platform is configured with the current hardware and software dependencies of the current baseline of the software system.

* 有时可能需要有**一个以上的暂存环境**。Sometimes it may be necessary to have more than one staging environment.
* 系统可能由几个分布式组件组成，每个组件都打算在不同的软件平台上使用，或者同一组件可能需要在不同平台上执行。 The system may be comprised of several distributed components, each of which are intended for use on a different software platform, or the same component may need to be executable on different platforms.
  * 例如，一个服务器场的监控系统可能有一些在嵌入式传感器（温度、湿度、功率等）集合上运行的代理组件；在监控软件代理的服务器上运行的服务组件；以及在普通桌面操作系统上为终端用户提供的一些客户端 "仪表板"。软件打算在其中运行的每个环境都需要在暂存期间进行测试  For example, a monitoring system for a server farm may have a number of agent components operating on a collection of embedded sensors (temperature, humidity, power and so on); a service component running on a server monitoring the software agents; and a number of client ‘dashboards’ for end users on common desktop operating systems. Each of the environments in which the software is intended to be run need to be available for testing during staging.
* 环境也需要能够测试

## 暂存环境局限性：Limitations of staging environments

![](/static/2021-04-24-21-59-30.png)

暂存环境的局限性 - 不是总能为相同生产暂存环境

* **系统分布在太多的计算节点上，以测试真实的场景** The system will distributed on too many computing nodes to test realistic scenarios.
  * **too large to replicate** on too many computing nodes
* **该系统将在太多不同的平台配置上使用，以至于无法全部测试** The system will be used on too many different platform configurations to test them all.
* **该系统将有太多的同时用户** The system will have too many simultaneous users.
* **有太多不同类型的用户需要测试** There are too many diverse user types for all to be tested.
* **只有一个软件平台可用，并用于生**产 Only one platform for the software is available and is used for production.
* **软件的依赖性，如网络端点和数据集不能从生产的外部访问（数据集出于安全，可信度原因不允许外部访问**）Software dependencies, such as network end points and data sets cannot be accessed from outside of the production

# CI环境：CI Environments

![](/static/2021-04-24-22-33-21.png)

实际上，CI pipeline配置&执行由CI环境管理

* 每个环境被配置用于执行pipeline或job
  * **每次commit**，都会执行。或**调度周期执行**

---

:orange: <font color="red">持续集成环境有助于与版本控制系统和自动构建系统一起工作，以实现许多步骤的自动化。</font> A continuous integration environment is useful for working in conjunction with a version control system and automated build system to automate many of these steps.

* 该环境被配置为管理一些作业job。**一个作业job可以在预定的时间执行，或者在版本控制系统中对系统的源代码进行更改时执行** The environment is configured to manage a number of jobs. A job may be executed at scheduled times, or when a change is made to the system’s source code in the version control system.

# CI环境流程：CI Environment Workflow

:orange: 该图说明了一个典型工作的持续集成环境的工作流程 The diagram illustrates the work flow of a continuous integration environment for a typical job.

* 持续集成环境检查出软件的最新版本，然后尝试执行**构建**，执行系统的**测试套件**，并**发布编译后的可执行文件**，以便**为终端用户提供最新版本**。The continuous integration environment checks out the latest version of the software and then attempts to perform a build, execute the system’s test suite and publish the compiled executable so that the latest release is available for end users.
* 该环境可以被配置为对主源代码版本的几个**不同分支**进行这样的操作 The environment may be configured to do this for several different branches of the main source code version

:orange: 此外，如果软件需要在**几个不同的平台或配置上进行测试，那么可以执行不止一次的构建**。<font color="red">持续集成环境的其他功能包括【记录构建和测试报告】、将【可部署的可执行文件导出到分发库】以及【执行定期的非功能测试】</font> In addition, more than one build may be performed, if the software needs to be tested for several different platforms or configurations. Other functions of continuous integration environments include logging build and test reports, exporting deployable executables to distribution repositories and executing periodic non-functional tests.

![](/static/2021-04-24-22-33-38.png)

workflow of a CI environment for a typical job or pipeline（**执行某特定job/pipeline的CI环境例子**）

1. 检查repo代码
   1. 来源于schedule build or new commit的代码
2. Build（compilation）
3. 产生**build report**
   1. 反应构建成功or失败
4. 假如build成功，对build中编译后的代码，
   1. 应用**unit & integration test**
   2. 产生**test report**
   3. 产生最新的可执行文件，latest executble【**注意某些CI环境可能会先执行test，在生成可执行文件，避免生成不通过测试的可执行文件**】

# 管理CI过程状态可见性 & 度量指标：Maintaining Visibility & Metrics

![](/static/2021-04-24-22-43-23.png)

:orange: 随着项目的进展，找到传播不同的相关CI过程的状态信息的方法是很有用的。传播信息的常见方式包括（维护可见性 - **发送项目进展信息的常用方式↓**）：As a project progresses it is useful to find ways to disseminate information about the status of the different associated CI processes. Common way of disseminating information include:

* 推送通知，如短信或电子邮件 Push notifications such as SMS or email.
* 广播机制，例如在开发区域的大屏幕显示上显示**状态监视器** Broadcast mechanisms such as displaying a status monitor on a large screen display in the development area.

有用的维护指标数据【可以包含在上述通知方式中的信息】

* static analysis - **不成功的构建的数量** Unsuccessful builds
  * 测量项目失败构建的频率
  * 过去一段时间的不成功率，衡量项目因集成冲突而被破坏的频率 Unsuccessful over last period, which measures how frequently a project is broken due to integration conflicts.
* **平均不成功的测试** Average unsuccessful tests
  * 可以用来估计**实现新特性会引入缺陷至项目的比例**
  * 每次构建的平均不成功测试可用于估计因实施新功能而将缺陷引入项目的速度 Average unsuccessful tests per build can be used to estimate the rate at which defects are introduced into a project by the implementation of new features
* **构建时间** Time to build
  * 监视CI过程保持可行的时间长短
  * 构建耗时过长，会阻止开发人员进行频繁提交
  * **构建时间对于监控确保 CI 流程保持可行性非常重要** Time to build is important to monitor to ensure that a CI process remains viable.

## 例子 - CI Gitlab Metrics

![](/static/2021-04-24-22-43-57.png)

gitlab CI pipeline

# 快速构建对CI的重要性：Fast build are essential

**快速构建对于确保快速发现问题，以及通过减少每次提交的等待时间来鼓励频繁提交非常重要**。 Fast builds are important to ensure problems are discovered quickly, and to encourage frequent commits by reducing waiting time for each commit.

---

:orange: **快速构建对于成功的CI流程至关重要，因为**：Fast builds are essential to a successful CI process because:

* **延迟完成构建也将延迟发现任何问题** delays of completing the build would also delay discovering any problems
  * **开发者会没察觉到构建已经失败的情况下继续开发** developers would continue working while build is still in progress without realise the broken build
* deter from make frequent commit **阻止开发者进行频繁commit**
  * 因为在提交后续进展之前，可能需要等待先前commit的构建结果
  * **开发人员将被阻止频繁提交，因为需要等待构建完成会降低生产力** Developers will be deterred from making frequent commits, because the need to wait for a build to complete will reduce productivity.
  * 集中精力实现它是值得的，因为每减少一分钟的构建时间，就为每个开发人员每提交一次都节省了一分钟。由于持续集成要求频繁提交，所以这需要花费大量的时间。

🍬 持续集成的倡导者，以及极限编程推荐的**最大构建时间为10分钟** Advocates of Continuous Integration, as well as Extreme Programming recommend a maximum build time of ten minutes.

* <font color="deeppink">不幸的是，随着项目变得越来越大，要确保构建时间保持在可接受的范围内可能会变得越来越困难</font> Unfortunately, as a project becomes larger, it may become more and more difficult to ensure that the build time remains acceptable.

🍬 因此，在某种程度上，需要在每次改变时执行**完整的构建和确保可接受的构建时间之间达成妥协**。这种折衷可以有几种形式  Consequently, at some point a compromise needs to be reached between performing a complete build every time a change is made and ensuring an acceptable build time. This compromise can take several forms:

* **重新配置构建过程，以确保在构建组件时不进行【不必要的步骤**】 Reconfigure the build process to ensure that no unnecessary steps are being undertaken when a component is being built.
  * 可以进行优化（但这并不会降低以前质量检查的严格性）
* **配置几种不同类型的构建，针对项目的不同部分和/或变化的原因进行** Configure several different types of build that are performed for different parts of the project and/or causes of change. 
  * 不影响类或组件的发布或公共 API 的变更可能只需要触发一个子系统的构建。 Changes that don’t affect the published or public API of a class or component may only need to trigger a build for a sub-system.
  * 对整个系统的日常构建可以测试一天内所有变化的效果 Daily builds of the complete system can test the effect of all changes over a day.
* **对测试用例进行优先排序，然后将其划分为不同的类别，用于不同类型的构建** Prioritise test cases and then partition them into different categories for different types of build.
  * 回归选择测试，所以不会对每个commit运行所有测试 implement a regression selection strategy so that not all test run on every commit
  * 测试用例的选择需要妥协效率
  * 为回归测试套件选择适当的测试用例，必须在<font color="red">效率（在10分钟内完成构建）和检测缺陷的测试有效性(检测缺陷的能)之间做出妥协</font> The selection of appropriate test cases for the regression test suite must compromise between efficiency (completing the build within 10 minutes) and test effectiveness in detecting defects
* **随着需要更多测试的新功能的增加，测试的选择将需要不断的审查** The selection of tests will need to be continually reviewed as new features are added requiring more tests.
* **将大型项目划分为较小的、定义明确的组件，可以同时进行开发** Partition large projects into smaller well defined components that can be developed concurrently.
  * 小组件成可为大项目的依赖 Some components will then become dependencies of the larger system.
  * <font color="red">当开发变更时，只有受影响的组件需要被构建和重新测试</font> When a development change is made, only the component that is affected will need to be built and retested.
  * 依赖的组件不会受到影响，直到依赖关系的变化反映在发布中。这通常是一种良好的做法，以确保软件项目保持可管理性  Dependent components will not be affected until the change in the dependency is reflected in a release. This is often good practice anyway to ensure that a software project remains manageable.
* **优化构建流程，即优化测试执行，并行运行测试** check the build process for optimisations, eg. ways to optimise test execution such as running tests in parallel

# Advanced topics

一个已经完成了软件项目**集成过程自动化**的团队也有机会将**自动化扩展到向【用户和客户交付软件的过程**中。这样做有两个主要优势 A team that has completed automated the process of integrating their software project also have the opportunity to extend the automation to the delivery of software to users and customers. There are two main advantages of doing this:

* **确保部署过程被定期使用。这意味着部署新的软件版本的风险更低，因为该过程是自动化的，而且更容易理解** Ensures that the deployment process is regularly used. This means that the risk of deploying new software releases is lower because the process is automated and better understood.
* **不同的功能变体可以被部署给不同的用户** Different feature variants can be deployed to different users.

CD】

* continuours deployment
* 自动集成项目 & 自动部署给user
* 部署的风险降低了，因为部署过程是自动化的
* 也可能包含一个回滚rollback新部署的自动化过程
* 同时将为不同用户集提供项目，特性的变体成为可能
  * 可应用AB测试

chaos engineering

* 在软件系统中引入defects/failures
  * 为了确认软件能应对这些failures的程度
* The deliberate introduction of failure into a software service or infrastructure to test the resilience of dependents.

## Summary

![](/static/2020-11-17-23-13-16.png)

持续集成实践**将软件系统的快速、并发变化造成的干扰降到最低** Continuous integration practices minimise the disruption caused by rapid, concurrent changes to software systems.

软件开发项目**经常经历快速、并发的变化，因为许多不同的贡献者都在修复缺陷和实施新功能**。 Software development projects routinely experience rapid, concurrent change as many different contributors fix defects and implement new features.

在项目<font color="red">迭代结束时整合这些变化可能会带来高昂的成本和困难，并给项目进度带来意想不到的延迟</font>。 Integrating these changes at the end of a project iteration can be costly, difficult and introduce unexpected delays into the project schedule.

持续集成是一个实践的集合，旨在最大限度地减少由变化引起的对项目的干扰。此外，CI实践提供了一种机制，可以在软件系统发展过程中积极监测其质量  Continuous integration is a collection of practices that are designed to minimise the disruption to a project caused by change. In addition, CI practices provide a mechanism for actively monitoring the quality of a software system as it evolves.

---

VCS来进行变更管理，分支管理（为CI项目选择特定分支开发模式），自动化构建，自动化测试，自动化部署

只有自动构建和测试都没有错误，我们才说是成功的构建。（这种构建有针对trunk，有针对feature branch的，针对CI项目选择的分支管理策略）。

:orange: 进行持续构建的一个关键部分是，如果主线构建失败，就需要立即修复它。使用 CI 的全部意义在于，您始终在一个已知的稳定基础上开发。对于 mainline build 来说，中断并不是一件坏事，尽管如果它一直在发生，这表明人们在提交之前没有足够小心地在本地更新和构建。**然而，当 mainline build 确实中断时，尽快修复它是很重要的**。

* “没有人拥有比修复构建更高优先级的任务”。这并不意味着团队中的每个人都必须为了修复构建而停止他们正在做的事情，通常只需要几个人就可以让事情重新运转起来。这确实意味着有意识地将构建修复作为一项紧急的、高优先级的任务进行优先排序。

有良好的构建后，有机会将更改提交到存储库。

**注意如果多人参与开发，他们的更改可能与自己的更改冲突，导致构建/测试失败。此时需要修复问题直到通过构建，测试。与主线同步 （CI情况中，，错误能被快速检测。良好实践：团队每天有许多正确构建。错误构建不时发生，但应该很快修复）

** 采用分支管理&持续小的构建提交的好处：每个人从共享的主线基础上开发发展，不会离基础太原，以至于后期整合需要很长时间。寻找bug的时间更少，因为很快就能发现。注意VCS中不应该存储构建的任何东西，这是一种异味，更深层的问题-->无法可靠的重新创建构建

** 变更提交到主线后，会再次基于mainline构建！！！只有当最后这个构建成功时，更改视为完成。

# CI好处

持续集成的最大和最广泛的好处是降低了风险

延迟集成的麻烦在于，很难预测需要多长时间才能完成，更糟糕的是，很难看到你在整个过程中走了多远

* 持续集成完全解决了这个问题。没有长期的集成，你完全消除了盲点。在任何时候，你都知道你在哪里，什么工作，什么不工作，你的系统中有哪些突出的 bug。

**连续集成并不能消除 bug，但是它确实使得发现和删除 bug 变得非常容易。在这方面，它更像是自测试代码**。如果你引入了一个 bug 并且很快发现它，那么摆脱它就会容易得多。因为你只改变了系统的一小部分，所以你不需要花太多的时间去寻找。由于系统的这一部分是您刚刚处理过的部分，因此它在您的内存中是新鲜的——再次使得查找 bug 更加容易。您还可以使用 diff 调试——将系统的当前版本与没有 bug 的早期版本进行比较。

**因此，具有持续集成的项目在生产和过程中都倾向于拥有少得多的 bug**。然而，我应该强调，这种好处的程度直接取决于测试套件的好坏。您应该会发现，构建一个能够产生显著差异的测试套件并不太困难。然而，通常团队需要一段时间才能真正达到他们有可能达到的低水平的 bug。到达那里意味着不断地工作和改进你的测试。

如果您有持续的集成，那么它消除了频繁部署的最大障碍之一。**频繁部署是有价值的，因为它允许用户更快地获得新功能，对这些功能提供更快速的反馈，并且通常在开发周期中变得更具协作性。这有助于打破客户和开发之间的障碍——我认为这是阻碍软件开发成功的最大障碍**。

# 持续部署

假设CI通过了，你就可以把建成的项目部署给客户。**这可以确保部署过程被定期使用，不同的功能变体可以被部署到不同的客户，以测试新事物**。Assuming that CI passes, you can then deploy the built project to customers. This ensures the deployment process is regularly used and different feature variants can be deployed to different customers to test new things.a


# CI自动测试

TDD很好，但是对于CI不必要，因为CI中对自测代码需求较弱

自动化测试，需要检查大部分代码库中的bug。

* 测试需要能够从一个简单的命令开始并进行自检。运行测试套件的结果应该表明是否有任何测试失败。对于要进行自测试的构建，测试的失败应导致构建失败

当然，你不能指望通过测试就能找到所有的东西。正如人们常说的那样: 测试并不能证明没有 bug。然而，完美并不是自测试版本的唯一回报。频繁运行的不完美测试要比从未编写过的完美测试好得多。

# 为什么每天提交 

开发人员提交到 mainline 的一个先决条件是他们能够正确地构建自己的代码。当然，这包括通过构建测试。与任何提交周期一样，开发人员首先更新其工作副本以匹配主线，解决与主线的任何冲突，然后在其本地机器上构建。如果构建通过，那么他们可以自由地向 mainline 提交。

通过经常这样做，开发人员可以很快发现两个开发人员之间是否存在冲突。快速解决问题的关键是快速发现问题。**由于开发人员每隔几个小时就提交一次冲突，因此在冲突发生后的几个小时内就可以检测到冲突，在这一点上并没有发生太多事情，而且很容易解决**。**长达数周未被发现的冲突可能很难解决**。

* 您在更新工作副本时进行构建的事实意味着您检测到了编译冲突以及文本冲突。由于构建是自测试的，因此您还可以检测代码运行过程中的冲突。如果这些冲突在代码中停留很长时间而未被发现，那么后一种冲突就尤其难以发现
* 由于**每次提交之间只有几个小时的更改时间，所以问题可能隐藏的地方就有那么多**。此外，由于没有太大的变化，您可以使用 diff-debugging 来帮助您找到 bug。

我的一般经验法则是，每个开发人员每天都应该对存储库做出承诺。 实际上，如果开发人员提交的次数超过这个频率，那么它通常是有用的。 您提交的越频繁，需要查找冲突错误的位置就越少，解决冲突的速度也就越快。

* 频繁的提交促使开发人员将他们的工作分解成几个小时的小块。这有助于跟踪进度并提供一种进度感。通常人们一开始觉得他们不能在几个小时内做一些有意义的事情，但是我们发现指导和实践能帮助他们学习。

# CI vs 夜晚构建

许多组织根据定时的时间表进行定期构建，比如每天晚上。这与持续构建不同，对于持续集成来说也是不够的。持续集成的全部意义在于尽快发现问题。夜间构建意味着在被人发现之前，bug 会潜伏一整天而未被发现。一旦它们在系统中的时间达到这么长，就需要很长时间才能找到并删除它们。
