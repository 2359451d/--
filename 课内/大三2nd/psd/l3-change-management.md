# Change Management

* [Change Management](#change-management)
* [Why Change Management](#why-change-management)
* [变更管理是许多其他软件工程实践的基础:Change management is fundamental to many other software engineering practices](#变更管理是许多其他软件工程实践的基础change-management-is-fundamental-to-many-other-software-engineering-practices)
* [What are Change Control Items?](#what-are-change-control-items)
* [Revision/Version/Source Change/Control Systems/Repositories](#revisionversionsource-changecontrol-systemsrepositories)
* [集中式与分布式的版本控制系统:Centralised versus Distributed Version Control Systems](#集中式与分布式的版本控制系统centralised-versus-distributed-version-control-systems)
* [Commits/Changesets/Revisions](#commitschangesetsrevisions)
* [提交ID:Commit IDs](#提交idcommit-ids)
* [提交日志信息： Commit Log Messages](#提交日志信息-commit-log-messages)
* [Branching](#branching)
* [主干开发模型：Trunk Based Development](#主干开发模型trunk-based-development)
* [Feature Branching](#feature-branching)
* [Staging Branches](#staging-branches)
* [Naming Conventions for Branches](#naming-conventions-for-branches)
* [Branching Strategies (or Workflows)](#branching-strategies-or-workflows)
* [管理分支的增长：Managing Branch Growth](#管理分支的增长managing-branch-growth)
* [Bad Reasons for Storing non-Configuration Items in an SCM (Speaking as a purist)](#bad-reasons-for-storing-non-configuration-items-in-an-scm-speaking-as-a-purist)
* [Solve these issues through Release and Dependency management](#solve-these-issues-through-release-and-dependency-management)
* [Summary](#summary)
* [Reading 补充](#reading-补充)

# Why Change Management

# 变更管理是许多其他软件工程实践的基础:Change management is fundamental to many other software engineering practices

![](/static/2021-04-22-20-08-25.png)

# What are Change Control Items?

![](/static/2021-04-22-20-08-48.png)

# Revision/Version/Source Change/Control Systems/Repositories

![](/static/2021-04-22-20-09-06.png)

一种软件工具，用于维护一组变更控制项目的变更历史。A software tool for maintaining the history of changes to a set of change control items.

核心工作流程。core workflow

* 进行本地修改 make local changes
* 接收来自远程存储库的更新 Receive updates from remote repository
* 解决冲突 resolve conflicts
* 提交本地修改到远程仓库 submit local changes to remote repository

# 集中式与分布式的版本控制系统:Centralised versus Distributed Version Control Systems

![](/static/2021-04-22-20-10-32.png)

![](/static/2021-04-22-20-10-39.png)

![](/static/2021-04-22-20-11-00.png)

# Commits/Changesets/Revisions

包括。Comprises

* **唯一的ID** Unique id
* **父级提交的Id(s)**。Id(s) of the parent commit(s)
* **一组变更控制项目的变更** The changes to a set of change control items
* **任何新增的变更控制项目** Any added change control items
* **任何被删除的变更控制项** Any removed change control items
* **元数据** Metadata
* **变更者** Change author
* **时间戳** Timestamp
* **日志信息** Log message

要么是描述项目自上一次提交后的变化情况所需的信息包；要么在概念上是当时项目状态的一个完整快照 Either the package of information needed to describe how a project has been changed since the previous commit; or conceptually as a complete snapshot of the state of the project at that time.

# 提交ID:Commit IDs

![](/static/2021-04-22-20-14-17.png)

**需要一种方法来唯一地引用版本库中的提交（否则我们就无法追踪历史**）。Need a way of uniquely referencing a commit within a repository (otherwise we can’t track the history).

集中式系统，如Subversion，**使用一个全局的修订号，每次有新的提交都会递增**。Centralised systems such as Subversion use a global revision number that gets incremented each time a new commit is made.

分布式系统则**依靠单向哈希函数来生成一个唯一的固定长度的提交内容摘要**（为了实用）。Distributed systems rely on one way hash functions to generate a (for practical purposes) unique fixed length digest of the content of the commit.

# 提交日志信息： Commit Log Messages

![](/static/2021-04-22-20-17-47.png)

各个团队都有自己的标准，但在常见的**良好实践中，提交信息应该** Teams agree their own standard, but in common good practice a message should:

* **描述一个提交的目的（或意图**） Describe the purpose (or intent) of a commit.
* **避免在一次提交中出现多个目的**。Avoids multiple purposes in a single commit.
* **解释为什么要提交，并尽可能地链接到项目中的问题追踪器**。Explain why the commit is being made and link to the issue in the project issue tracker if possible.
* **解释该提交如何解决该问题** Explain How the commit addresses the issue.
* **有一个简短但有意义的标题** Have a short, but meaningful title.
* **考虑在你的git终端配置一个消息编辑器，并实现一个提交消息模板以实现提交一致性** Consider configuring a message editor in your git terminal, and implementing a commit message template to encourage consistency.

# Branching

![](/static/2021-04-22-20-20-40.png)

在一个单一的版本控制库中，根据共同的历史维护多个开发线的做法。The practice of maintaining multiple development lines based on a common history within a single version control repository.

适用于（分支应用场景）。Useful for:

* **对新功能的实施进行实验** Experimenting with the implementation of new features
* **进行实质性的重组**。Undertaking a substantial reorganisation.
* **支持一个专门的应用程序的变体**。Supporting a specialised variant to an application.
* **创建一个版本**。Creation of a release.
* **不同的分支实践可以组合成一个团队的分支策略**。Different branching practices can be combined into a branching strategy for a team.

# 主干开发模型：Trunk Based Development

![](/static/2021-04-22-20-22-58.png)

* **更清楚地了解项目的进展**。Clearer visibility of progress on project.
* **减少了合并冲突的风险**。Reduces risk of merge conflicts.
* **需要开发人员将对主站的修改分解成更小的增量**。Requires developers to break changes to master down into smaller increments.
* **这可能会增加主程序中代码被破坏的风险**。Can increase risk of broken code in master.

# Feature Branching

![](/static/2021-04-22-20-24-10.png)

**允许开发人员有更大的自由度来开发孤立的新功能**。Allows developers greater freedom to develop new features in isolation.

**更便于管理代码审查**。More convenient for managing code reviews.

**鼓励更长的集成间隔时间** Encourages longer periods between integration

# Staging Branches

![](/static/2021-04-22-20-25-31.png)

**允许在上线前在用户验收测试（UAT）环境中测试变更**。Allows changes to be tested in a user acceptance testing (UAT) environment before go live.

**更改不能直接合并到部署中，必须先通过暂存**。 Changes can’t be merged directly to deployment - must pass through staging first.

**有些团队现在为每个功能分支配置一个UAT环境（每个功能分支也是一个暂存分支**）Some teams now configure a UAT environment for every feature branch (each feature branch is also a staging branch)

# Naming Conventions for Branches

![](/static/2021-04-22-20-26-52.png)

遵循普通分支的惯例，可以提高项目的可维护性 Following conventions for common branches enhances project maintainability:

* master/trunk
* uat, staging or preprod[uction]
* deploy[ment] or prod[uction]

对特性分支使用分层的命名策略，如 Use a hierarchical naming strategy for features branches, e.g.

* feature/webapp/splash
* feature/mobapp/login

# Branching Strategies (or Workflows)

![](/static/2021-04-22-20-28-06.png)

一组（通常是定制的）分支实践。A collection of (often customised) branching practices.

许多标准发布的分支策略 Many standard published branching strategies

* Gitflow
* GitHub流程
* GitLab流程

大多数团队都会根据自己的情况来定制策略。Most teams tailor their strategy to suit their circumstances.

# 管理分支的增长：Managing Branch Growth

* 删除分支 Delete branches
* commit squashing

# Bad Reasons for Storing non-Configuration Items in an SCM (Speaking as a purist)

![](/static/2021-04-22-20-31-03.png)

"源代码的编译时间太长了，所以我们需要存储二进制。"“The source code takes too long to compile, so we need to store the binary.”

"编译不能自动化，必须有人在GUI上按一个按钮。"“Compilation can’t be automated, someone has to press a button on the GUI.”

"第三方库在公共发布库中是不可用的。"“Third party libraries aren’t available in a public release repository.”

# Solve these issues through Release and Dependency management

通过发布和依赖性管理解决问题

![](/static/2021-04-22-20-31-52.png)

**将代码分解成更小的模块，并有独立的编译管道**。Break the code into smaller modules with separate compilation pipelines.

**使用发布库为每个模块商定和配置一个发布管理流程** Agree and configure a release management process for each module using a release repository.

# Summary

在自动版本控制系统的支持下，严格的变更管理是软件工程的基础。Disciplined change management, supported by automated version control systems is fundamental to software engineering.

# Reading 补充

feature branch通常基于最新的开发分支 develop branch

发行分支 release branch由开发分支 develop branch来

![](/static/2021-04-23-17-03-55.png)

![](/static/2021-04-23-17-04-25.png)