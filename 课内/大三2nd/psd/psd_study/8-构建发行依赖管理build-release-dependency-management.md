# Build, Release and Dependency Management

* [Build, Release and Dependency Management](#build-release-and-dependency-management)
* [为什么：WHY use Build, Release and Dependency Management](#为什么why-use-build-release-and-dependency-management)
* [构建工具：Tooling](#构建工具tooling)
* [构建管理：Build Management](#构建管理build-management)
  * [自动化构建术语：Terminology - Auto Build Management](#自动化构建术语terminology---auto-build-management)
  * [例子：目标，任务，映射](#例子目标任务映射)
  * [构建目标：Typical Targets](#构建目标typical-targets)
* [惯例优先原则: Configuration by Convention](#惯例优先原则-configuration-by-convention)
  * [例子：CbyC Maven Project Layout](#例子cbyc-maven-project-layout)
* [依赖-发行库管理：Dependency & Release Management](#依赖-发行库管理dependency--release-management)
  * [WHY](#why)
  * [依赖 & 接口类型](#依赖--接口类型)
* [发行库/依赖版本：Release/Dependency versioning](#发行库依赖版本releasedependency-versioning)
* [指明依赖项：Specify dependencies](#指明依赖项specify-dependencies)
  * [良好实践](#良好实践)
* [发行库类型：Types of Release](#发行库类型types-of-release)
* [发布分支：Release Branches](#发布分支release-branches)
* [接口API类型：Changes to different types of APIs](#接口api类型changes-to-different-types-of-apis)
* [语义化版本号：Semantic Versioning](#语义化版本号semantic-versioning)
* [废弃的功能：Deprecating Features](#废弃的功能deprecating-features)
* [迁移计划和脚本: Migration Plans & Scripts](#迁移计划和脚本-migration-plans--scripts)
  * [Summary](#summary)
* [什么是测试发行？](#什么是测试发行)

# 为什么：WHY use Build, Release and Dependency Management

为什么要使用构建、发行版（这里指的库 library）和依赖管理？

![](/static/2020-11-10-21-37-47.png)

* **所有项目依赖于其他软件** All projects depend on other software
* **编译依赖关系可能很慢，而且不能完全自动**。Compiling dependencies may be slow and not completely automatable
  * 原则上，当一个团队需要另一个项目版本的拷贝时，他们可以直接克隆所需的提交。然而，这将意味着该**团队需要克隆和打包它所依赖的每一个软件项目，以及它们的任何依赖项等等。这个过程可能很慢，或者不能完全自动化** In principle, whenever a team needed a copy of a version of another project, they could just clone the required commit. However, this would mean that the team would need to clone and package each and every software project it depended on, as well as any their dependencies and so on. This process may be slow or not completely automatable.
* **可以缓存 "oven ready "组件** Enables caching of ‘oven ready’ components

:orange: 单一的软件开发工作将管理他们所维护的[软件的许多不同标签版本的生产环境]。相反，该团队所**开发的软件将依赖于其他各种开发工作的软件版本**。 A single software development effort will manage the production of many different labelled releases of the software they maintain. Conversely, the software that the team develops will depends on versions of software from a variety of other development efforts.

* 该团队**需要维护这些依赖关系的规范，以便在构建过程中能够检索到它们并将其整合到整个软件项目中**。 The team will also need to maintain the specification of these dependencies so that they can be retrieved and integrated into the overall software project during a build process.
* 反过来，**这些发行版将报告它们自己的依赖关系，这些依赖关系也需要被整合，最终产生一个依赖关系的集合** In turn, these releases will report their own dependencies that will also need to be integrated, eventually producing an assembly of dependencies.

:orange: 构建、发布和依赖性管理为一个软件团队提供了一种严格的手段，**使其能够构建并将其项目的特定打包版本发布到一个称为发布库的缓存中**。反过来，其他团队可以通过<font color="red">引用该缓存中(发行库)的编译版本来指定其项目的依赖性，而无需指定源</font> Build, release and dependency management provides a rigorous means for one software team to build and publish particular packaged versions of their project to a cache called a release repository. In turn other teams can specify their project’s dependencies by referencing compiled releases in this cache, rather than having to specify the source.

:orange: 因此，构建、发布和依赖性管理是截然不同但又紧密相连的活动 So, build, release and dependency management are distinct but closely inter-connected activities.

# 构建工具：Tooling

构建工具

![](/static/2020-11-10-21-40-27.png)

几乎每个编程环境都有一个相关的构建管理工具。**现代的构建管理工具整合了依赖性管理，因此也整合了发行（库）管理**。有时，这些工具被称为**包管理器**，尽管它们的工作略有不同 Just about every programming environment has an associated build management tool. Modern build management tools integrate dependency management and consequently release management. Sometimes these are referred to as package managers, although these do slightly different jobs.

* 重要的是你要为你的框架确定正确的工具链 Important you identify the right tool chain for your framework.

# 构建管理：Build Management

构建管理

## 自动化构建术语：Terminology - Auto Build Management

**自动化构建管理** automating build management

:orange: 一个软件项目应该有一个**构建配置文件**。它将包含声明式和命令式的混合语句，并将描述目标、映射和任务 A software project should have a build configuration file. This will contain a mixture of declarative and imperative statements and will describe the targets, mappings and tasks

* 如，maven pom.xml

:orange: **构建目标** targets

![](/static/2021-04-24-14-02-01.png)

* **解析依赖** resolve (dependencies)
* **编译代码** compile the code
* **测试** test (binary)
* example: resolving the dependencies, downloading the necessary files, compile the produced code, testing binary produced reports
* <font color="deeppink">软件管理生命周期内的抽象目标</font> abstract goals within the software management life cycle
* <font color="red">一个目标可能依赖于其他目标</font> 1 target may depend on other targets

:orange: **映射** mappings

![](/static/2021-04-24-13-55-45.png)
![](/static/2020-11-10-21-42-36.png)

* 映射描述了为达到目标而创建的**源和生成的工件**之间的**关系** Mappings describe the relationship between source and generated artifacts to be created to reach the target
* 映射是具体目标和源文件之间的关系 Mappings are relationships between concrete goals and source files.
* 如 `.java` -> `.class` (artifacts)
* useful for automating the build process

:orange: **构建任务** tasks

* **任务是构建管理系统为满足映射而采取的[行动]**，Tasks are the actions the build management system will take to satisfy the mappings,
* 例如，执行一个特定的编译器或测试套件 for example, execute a particular compiler or test suite

:candy: <font color="red">请注意，构建过程应该是可重复的（有时也被称为等幂）</font> note that a build process should be repeatable (sometimes referred to as idempotent). 

* <font color="deeppink">这意味着在第一次之后，对一个软件项目的特定【提交重复应用构建】过程【不应该改变结果】</font> Finally, That means that the repeated application of the build process to a given commit of a software project after the first time should not change the result.
* 除了使整个过程**更加可靠**之外，这也使我们有可能从一个软件系统的源提交中明确地生成一个特定的版本 As well as making the whole process more reliable, this makes it possible to definitively generate a particular release of a software system from its source commit.

---

## 例子：目标，任务，映射

![](/static/2021-04-24-13-55-45.png)
![](/static/2020-11-10-21-42-36.png)

* 我们可以看到例子中的目标、映射和任务。We can see the targets, mappings and tasks in the example.
  * 请注意，在 Makefile 中，目标可以是一个抽象的目标，也可以是要编译的具体文件（或文件模式）。 Notice that in a Makefile, a target can either be an abstract goal or a concrete file (or file pattern) to be compiled.

![](/static/2020-11-10-21-42-36.png)

## 构建目标：Typical Targets

![](/static/2020-11-10-21-44-03.png)

* **解析依赖** resolve
  * 收集项目所需依赖 collecting the dependencies for projects
* **编译源码** compile
  * 源码转为二进制文件 source code tend to be the binary files generated by the results produced
* **测试** test
  * 对编译后元件执行单元/集成测试套件 units or integration suits are executed to compile code
* **打包** package
  * 编译二进制格式（从构建过程中已经编译的工件），以便后续使用（部署或自定义部署至本地仓库） compile binary formats(already compiled artifacts from build process) for suitable distribution
* **安装** Install
  * 将包安装至本地仓库 install the packages to local system & infrastructure
* **部署** deploy
  * 包部署至远程仓库 package is published to a remote release repositry
* **clean**
  * 删除构建阶段产生的二进制文件 removes generated binary files (of build process)
  * 会产生中间文件（test~install目标之间的） intermidate files are generated. from one of the target between test~install
* **cleanall**
  * 删除产生的所有元件 removes all the artefacts generated
  * 返回工作副本，只包含配置项 return working copy to presisting only contain the configuration items

# 惯例优先原则: Configuration by Convention

请注意，这个概念不仅仅是构建管理，还包括其他软件框架，比如 web 应用程序 Note that the concept goes beyond build management to other software frameworks, such as web applications.

![](/static/2020-11-10-21-46-34.png)
![](/static/2020-11-10-21-49-35.png)

:orange: 许多软件项目（框架）有**相似结构** Many software projects have similar structures

* 用于管理发布的工作流程任务也是类似的 Workflow of tasks for managing releases is also similar
* **遵循构建/发行版工具的规范** Follows conventions imposed by the build/release tool
  * 允许例外，**不用严格遵守**（只要规范允许） … but allow for exceptions
  * 开发者应遵循软件项目组织的发布工具所规定的惯例 developers should follow the convention set out by the release tool of organisation of softwares project

:orange: **遵循惯例的好处** benefit

* **尽量减少需要维护的【配置代码】** Minimises configuration code to be maintained
  * 将项目需要维护的配置代码量降至最低，因为相关工具希望项目以某种方式布局，以便发现资源 minimises the amount of configuration code a project needs to maintain because the associated tooling expects the project to be laid out in a certain way for resource discovery
* **促进对于项目的理解 & 可读性** Facilitates project comprehension and readability
  * 通过建立标准的项目布局来促进可读性 facilitates readability by establishing standard project layouts

:orange: 现代构建管理工具通常**采用（惯例优先原则**）非常有用的惯例配置概念。这意味着<font color="red">开发人员应该遵循构建/发布工具所规定的惯例</font> Modern build management tools often adopt the very useful concept of configuration by convention. This means that the developer should follow conventions set out by the build/release tool

---

> When the convention implemented by the tool matches the desired behavior, it behaves as expected without having to write configuration files. Only when the desired behavior deviates from the implemented convention is explicit configuration required.

**当工具实现的约定与所需的行为匹配时，它就会按照预期的方式运行，而无需编写配置文件。只有当期望的行为偏离实现的约定时，才需要显式配置**。

> Ruby on Rails' use of the phrase is particularly focused on its default project file and directory structure, which prevent developers from having to write XML configuration files to specify which modules the framework should load, which was common in many earlier frameworks.

Ruby on Rails 对这个短语的使用主要集中在默认的项目文件和目录结构上，这使得开发者不必编写 XML 配置文件来指定框架应该加载哪些模块，这在许多早期的框架中都很常见。

## 例子：CbyC Maven Project Layout

![](/static/2020-11-10-21-57-29.png)
![](/static/2020-11-10-21-57-55.png)
![](/static/2020-11-10-21-58-08.png)

`mvn test`将运行生成的maven项目的编译和测试阶段，Running mvn test would now run the compile and test phases of the generated maven project, 

* 编译所有代码并将其放置在一个名为target的目录中，compiling all code and placing it in a directory called target, 
* 然后执行测试目录中发现的任何测试。before executing any tests discovered in the test directory on it.

# 依赖-发行库管理：Dependency & Release Management

## WHY

![](/static/2020-11-10-21-58-42.png)

如前所述，大多数软件项目都存在一个复杂的依赖关系图。As already stated, most software projects exist in a complex graph of dependencies.

* 这里的图表显示了Maven项目的一些依赖关系图 The diagram here shows some of the dependency graph for the Maven project.
* 依赖和发布管理有助于部分地自动调解这些图 Dependency and release management help to partially automate the mediation of these graphs.

## 依赖 & 接口类型

![](/static/2020-11-10-22-04-40.png)

:orange: **依赖类型**

* **环境型依赖** Environmental
  * **为了使特定的软件能够工作，必须存在围绕软件基础设施的条件** conditions must exist surrounding software infrastructures in order to particular software to work
  * **显式依赖** - **在配置中明确指定，并由系统检查** explictly specified in the configuration & being checked by the system
    * 显式依赖原则指出: 方法和类应该显式地需要(通常通过方法参数或构造函数参数)任何他们需要的合作对象，以便正确地运行
  * **隐式依赖** - **假设存在，但在配置脚本中没有明确的记录** assume to exist, but not explicitly documented within the configuration scripts
    * JDK version xxx might exist
* **应用型依赖** - **依赖于构建元件中的其他软件** dependecy on other software within the software build artefacts
  * java libray ...

:orange: **仓库种类（发行库类型）** type of repo

* **公开** public
  * standard third party libray
  * 公共发布库更适合于向客户发布和获取第三方库的 "标准 "版本 Public release repositories are better for distribution to customers and for obtaining ‘standard’ releases of third party libraries.
* **私有/本地**
  * internal network to organisation
  * **组件的预公开稳定版本** Pre-public stable versions of components
  * **难以编译的组件** hard to compile components
  * **第三方组件的专业内部版本** specialist internal versions of third party components.

:orange: 任务 Tasks

* **创建要求** Establish requirements
* **获取每个依赖项的可用版本列表** Obtain list of available releases for each dependency
* **选择和检查版本组合的可行性** Choose and check feasibility of release version combination
* **找回丢失的元件** Retrieve missing artefacts

# 发行库/依赖版本：Release/Dependency versioning

![](/static/2020-11-10-22-11-32.png)

:orange: 有一个标签方案来描述**一个组件的不同版本**是很有用的 Useful to have a labelling scheme to describe different releases of a component.

* **包含在依赖性规范中** Included in dependency specification
* <font color="red">为了以后其他组件整合，用于筛选发行版</font>

:orange: **每个发布标签都是对版本控制库中一个commit的引用** Each release label is a reference to a commit in the version control repository.

* 可以**包含关于发布的信息** May incorporate information about the of the release
  * 与前一版本和未来版本的时间关系 Temporal relationship to previous and future releases
  * 发布的内容 The contents of the release
  * 发布的目的 The purpose of the release

# 指明依赖项：Specify dependencies

![](/static/2020-11-10-22-13-44.png)

:orange: 一个项目总是有**有传递特性的依赖** A project almost always has transitive dependencies.

* 如果组件A依赖组件B，组件B依赖组件C，那么A就会自动产生对C的依赖，以此类推。这使我们不必再关心众多依赖背后的关系，只需要着眼于自己需要的组件，很方便

:orange: **依赖的关系图可能是循环的** Dependency graphs may be cyclic.

* 一个项目依赖于另一个项目依赖于原来的项目，,复杂的解析 one project depends on another project depends on the original one,,complicated resolution
  * 任何依赖于Junit的项目也依赖于hamcrest，比如说 Any project that depends on Junit also depends on hamcrest, for example

:orange: **依赖解析是NPC问题** Dependency resolution is known to be NP complete.

* 使用**不同策略**来找到满意的依赖组合 Different strategies employed to find a satisfactory combination of dependencies
  * ‘Nearest’ version wins (e.g. Maven, PIP)**最近依赖优先**(nearest definition wins)
  * 允许在同一程序集中使用**多个版本依赖** Allow multiple releases to be used in the same assembly.

## 良好实践

![](/static/2020-11-10-22-18-44.png)

* 依赖解析优化 how to constraint the dependencies
  * 确保成功的依赖解析是一种艺术形式 Dependency resolution optimisations mean ensuring successful dependency resolution is an art form.
* 不受约束的规范可能导致不兼容的组合。Under constrained specifications may result in incompatible combinations.
* 过于受限的规范限制了灵活性 Over constrained specifications limit flexibility, require more effort to maintain and may persist the use of older, buggier releases.
  * 需要更多的努力来维护，并且可能会持续使用较旧的、错误较多的依赖版本
* **可用时迁移到依赖新版本** Migrate to new releases when available
* 不要依赖**传递性依赖关系** Don’t rely on transitive dependencies

---

传递依赖会使得项目依赖空间逐渐扩展。当依赖非常多时，不可避免地会出现相同组件版本不同的情况。**Maven内置了依赖仲裁（dependency mediation**）机制来处理该问题，具体来说有以下两条。

* **最近依赖优先**（nearest definition wins）
* 最先声明优先（first declaration wins）

# 发行库类型：Types of Release

![](/static/2020-11-10-22-25-12.png)

:orange: **发行库组成** Composition

* **核心可执行文件** - core qxecutable, convert binary ifles
* **定制可执行文件** - tailored executable, exetuable platform?
* **可选的扩展** - optional extensions, a customer requires
* **源码** sources
* **文档** documentation
* **数据集** datasets
* 完全体 release
  * all of the core binary & ... above

:orange: release目的，类型

* 前沿，**快照** bleeding edge/snapshot
  * release of each commit of this project
  * 这些是用于生产和分析的软件的稳定快照。它们是在第8节中描述的高度统计学验证之后部署的。我们会根据我们是否需要修复在生产过程中发现的问题，或者我们需要测试新的功能和/或新的外部软件变化来发布错误修复或开发编号的版本
* **增量发布** incremental
* **每晚构建版** Nightly builds
  * server to run integration tests as
  * too slow to run at every commit
  * 很多大型软件需要做一些每日构建的过程，用来分析是否能正常工作以便及时反馈。由于其耗费时间较长，如果在白天进行构建占大量的计算资源很不值，所以放在晚上。于是就有了daily builds变为nightly builds
  * 许多组织根据定时的时间表进行定期构建，比如每天晚上。这与持续构建不同，对于持续集成来说也是不够的。持续集成的全部意义在于尽快发现问题。夜间构建意味着在被人发现之前，bug 会潜伏一整天而未被发现。一旦它们在系统中的时间达到这么长，就需要很长时间才能找到并删除它们。
  * 这些是软件的日常构建，在被覆盖之前保留几天（通常是一个星期）。它们与后面描述的几个验证框架和涉及特定领域专家的软件包审批机制结合在一起，以尽量减少软件构建和测试过程中的不稳定性。这些每晚发布的版本是成为完全稳定版本的主要候选者，在此基础上制作的安装包可以分发用于生产
* **测试版本** Beta test releases
  * package release which could be provided to trusted third party to undertake user tests(prior to publish to generic public or wide variety of customers)
* **发行版候选** release candidates
  * special kind of beta tests
  * have the potential to be the final version of release
* **生产版本** rpoduction releases
  * general release to introduced to customers

> 版本存储库保存版本，快照存储库保存快照。在 maven 中，快照被定义为以 -SNAPSHOT 结尾的版本的工件。部署时，快照将转换为时间戳。根据定义，快照是可变的，版本是不可变的。这就是为什么 Nexus 让你把它们分开存储，因为通常你不在乎丢失快照，但是你会在乎丢失发行版。这使得快照清理更容易处理。
>
> Snapshot Repository vs Release Repository

# 发布分支：Release Branches

![](/static/2020-11-10-22-30-42.png)
![](/static/2020-11-10-22-30-57.png)

* 允许开发团队为客户准备一个，**新的稳定发行版** Allows the development team to prepare a new stable release to a customer
* 在发行版分支中，**只允许支持错误修复的更改** Only changes to support bug fixes are permitted in the release branch.
  * **only bug fixed are merged in release branches**
  * 不允许其他特性等，只能修复
* 发行版中的 **Bug 修复可以合并到master分支**中 Bug fixes in the release can be merged into master.
  * 修复完注意如果有develop分支记得及时合并变更
* **新的特性继续在master中开发** New features continue to be developed in master

# 接口API类型：Changes to different types of APIs

![](/static/2020-11-10-22-34-06.png)

:orange: **API 变更的可见性水平决定了应该如何记录和沟通它** The level of visibility of an API change determines how it should be documented and communicated.

* **私有API** private
  * 外部不可访问
  * 私有API的外部不可访问的接口。在这种情况下，文档可以是最小的，也许仅限于在修复重大缺陷时在一组发布说明中提及 Private APIs externally inaccessible interfaces. In this case, documentation can be minimal, perhaps limited to just a mention in a set of release notes if a significant defect is fixed.
  * 不应该对依赖有任何影响，因为它不能访问
* **公开API** public
  * 外部可以访问
  * 发布的功能指的是软件团队记录的**外部可访问的功能**，它是**与系统进行交互的预期手段** Published features refer to the externally accessible features that a software team has documented as being the expected means of interacting with the system.
* **发布接口** published
  * 内部团队特殊用途，
  * how the impact of change will affect users
  * and how integrate new ways
  * 另外，一些从业者认为，**任何外部可观察到的行为，包括可访问的编程语言API和实现细节**，都应该被认为是发布接口 Also, some practitioners argue that any externally observable behaviour, including both programming language APIs that are accessible and implementation details should be assumed to be at the published level.
  * 例如，依赖者可能希望 Java 中 toString() 方法的返回值有特定的字符串格式。然而，该格式是一个实现细节，很难在语言中指定 For example, a dependee may expect a particular string format for the return value of a toString() method in Java. However, the format is an implementation detail that is difficult to specify in the language.
  * 已发布接口是指已经发布出去为其他系统的构件所使用的接口，有多少接口的调用者是无法知道的，已发布接口必须保持稳定，否则一旦修改，将引起其调用者的失败
    * 假设A为一个已发布接口，如果对A进行了修改形成了新的接口B，则为了保证A的调用者能够正常工作，需要在A中调用B，
    * 而新的需要调用A接口的构件，直接调用B接口就可以了

---

:orange: **注意许多语言缺乏区分公共和已发布的接口的方法** Note that many languages lack a means of distinguishing between public and published interfaces,

* 这实际上是让开发人员承诺维护对所有公共接口的支持，无论他们是否期望这些接口被视为已发布接口 effectively committing developers to maintaining support for all public interfaces, whether they expected them to be treated as published or not.

:orange: 一些中间件框架，例如OSGI，提供了一种在包级指定接口的方法，限制了对底层组件中任何方法的访问，除非明确纳入组件级接口 Some middleware frameworks, such as OSGI provide a means for specifying interfaces at a package level, restricting access to any methods in the underlying component, unless explicitly incorporated in the component level interface

# 语义化版本号：Semantic Versioning

:orange: **破坏是指对已发布的API的更改** Breaking means alteration to the published API.

![](/static/2021-04-25-14-56-21.png)
![](/static/2020-11-10-22-36-47.png)

* **标签** Tag
  * 表示发布状态、类型或其他信息 Tag denotes release status, type or other information
* **补丁/快照增量** Patch Increment
  * 补丁增量表示非破坏性的错误修复变化。【不应该影响依赖，**不更改已发布接口**】Patch increments denote a non-breaking bug fixing change.
  * non-breaking
* **次要的增量** Minor Increment
  * **表示不破坏的特性增加更改【不更改已发布接口**】 Minor increments denotes a non-breaking feature addition change.
  * non-breaking
* **主要增量** Major Increment
  * 某些方面可能对依赖有影响
  * **表示对已公布的 API 进行突发性更改**
  * breaking
  * 请注意，主要数字为 0 的版本不应视为稳定版本。Note that releases with a major number of 0 should not be considered stable.

Major changes dominate minor and minor dominates incremental.

:orange: 语义版本控制的一个困难之处在于，**API行为的所有方面往往都会成为与用户签订的合同的一部分，而不仅仅是API的内容** A difficulty with semantic versioning is that all aspects of the behaviour of an API tend to become part of the contract with the user, not just the

* 而且，许多语言并不区分公共的和被删除的API（semantic versioning也不区分） Also, many languages don’t distinguish between public and pulished API (semantic versioning doesn’t either).

# 废弃的功能：Deprecating Features

![](/static/2020-11-10-22-41-40.png)
![](/static/2020-11-10-22-42-06.png)

> 定义: 出于兼容性原因而保留的特性，但将从更广泛系统的未来版本中删除 Definition: a feature that has been left in place for compatibility reasons, but will removed from a future release of the wider system.

---

过时API - 文档注释

![](/static/2020-11-10-22-42-35.png)

* **废弃作用域** the scope of the deprecation
* **包含新机制的软件发布版本** the release version of the software containing the new mechanism
* 一个schedule，
  * **何时废弃的功能将被删除** a schedule for when the deprecated feature will be removed
* 解释**为什么这个功能被废弃了** an explanation of why the feature has been deprecated
* **关于如何更改相关代码的描述** a description of how to change dependent code.

# 迁移计划和脚本: Migration Plans & Scripts

**在可能的情况下提供自动迁移** Provide automated migration where possible.

**已知的问题应明确说明何时不应迁移至新版本** Known issues should make clear when migration to a new release should not be undertaken.

![](/static/2020-11-10-22-48-18.png)

* 迁移计划
  * **一种估计迁移需要多长时间的方法** A means of estimating how long the migration will take.
  * **新版软件的已知问题摘要** Summary of known issues with the new release of the software
  * **数据迁移，包括必要的备份和数据格式的更改** Data migration, including necessary backups and alterations to data formats
  * **对更改的软件的任何依赖项进行升级** Upgrades of any dependencies of the changed software
  * **如何调整现有的源代码以使用新的已发布的API** How to adapt existing source code to use the new published APIs

## Summary

![](/static/2020-11-10-22-50-01.png)

构建、发布和依赖管理以及相互依赖的工作流，通过允许对变更进行整理和缓存，从而简化组件间变更管理的过程。Build, release and dependency management and interdependent workflows that that ease the process of inter-component change management by allowing changes to be collated and cached.

# 什么是测试发行？

> 测试阶段通常开始于软件功能完成，但可能包含一些已知或未知的错误。向用户提供测试版本的过程称为测试版本发布，这通常是该软件第一次在**开发组织之外可用**
> A Beta phase generally begins when the software is feature complete but likely to contain a number of known or unknown bugs. ... The process of delivering a beta version to the users is called beta release and this is typically the first time that the software is available outside of the organization that developed it.

What is Beta Testing?
什么是 Beta 测试？
Beta testing is the final round of testing before a product is finally released to a wide audience. The objective is to uncover as many bugs or usability issues as possible in this controlled setting.

Beta 测试是产品最终发布给广大用户之前的最后一轮测试。我们的目标是在这个受控的设置中发现尽可能多的 bug 或者可用性问题。

Beta testers are “real” users and conduct their testing in a production environment running on the same hardware, networks, etc., as the final release. This also means it’s the first chance for full security and reliability testing because those tests can’t be conducted in a lab or stage environment.

Beta 测试人员是“真正的”用户，他们在与最终版本相同的硬件、网络等上运行的生产环境中进行测试。这也意味着这是第一次进行全面的安全性和可靠性测试，因为这些测试不能在实验室或阶段性环境中进行。

Beta tests can either be open or closed. In an open beta test, anyone is able to use the product and is usually presented with some messaging that the product is in beta and given a method for submitting feedback. In a closed beta, the testing is limited to a specific set of testers, who may be composed of current customers, early adopters and/or paid beta testers. Sometimes they are conducted by diverting a certain percentage of users to the beta site instead of the current release.

测试可以是开放的，也可以是封闭的。在公开测试中，任何人都可以使用该产品，并且通常会收到一些信息，告诉他们该产品还处于测试阶段，并且提供了一种提交反馈的方法。在封闭测试版中，测试仅限于一组特定的测试人员，他们可能由现有客户、早期使用者和/或付费测试人员组成。有时候他们是通过将一定比例的用户转移到 beta 站点而不是当前版本来实现的。

