# Requirements Management

lecture 5

* [Requirements Management](#requirements-management)
  * [验收测试](#验收测试)
  * [Requirements sepecify the system boundary](#requirements-sepecify-the-system-boundary)
  * [Requirements vs Design](#requirements-vs-design)
  * [Managing Requirements](#managing-requirements)
  * [Actors/Roles/Personas](#actorsrolespersonas)
  * [User Stories/Features](#user-storiesfeatures)
    * [Requirements Scope](#requirements-scope)
    * [Non-Functional Requirements](#non-functional-requirements)
    * [Refinements of User Stories](#refinements-of-user-stories)
    * [adding more details](#adding-more-details)
    * [User Story Tasks](#user-story-tasks)
    * [Story Prioritisation - MOSCOW(Agile)](#story-prioritisation---moscowagile)
    * [Estimating Tasks](#estimating-tasks)
    * [Scenarios](#scenarios)
  * [where to store user stories/scenarios/tasks](#where-to-store-user-storiesscenariostasks)
  * [Assess User Story Quality - INVEST](#assess-user-story-quality---invest)
  * [Summary](#summary)

## 验收测试

> 验收测试是部署软件之前的最后一个测试操作。在软件产品完成了单元测试、集成测试和系统测试之后，产品发布之前所进行的软件测试活动。它是技术测试的最后一个阶段，也称为交付测试。验收测试的目的是确保软件准备就绪，并且可以让最终用户将其用于执行软件的既定功能和任务。验收测试是向未来的用户表明系统能够像预定要求那样工作。

## Requirements sepecify the system boundary

![](/static/2020-10-20-20-22-35.png)

## Requirements vs Design

![](/static/2020-10-20-20-23-12.png)

* requirements
* design
* implementation
* pre-existing framework or library

需求表明了whay system should do
设计表明how to do it
存在的框架constrains what kind of requirements could be

## Managing Requirements

![](/static/2020-10-20-20-23-29.png)

* 在项目发展过程中维护需求非常重要
  * 与客户进行持续讨论 discussion with customers
  * 驱动验收测试(demonstrates what is realised during the implementation)
* 许多不同软件可以制定&管理需求说明
  * **formal specifications - mathmatical methods such sa Z**
  * **view frames**
  * **UML 用例&活动图**
  * **Pseudo code**
  * **自然语言**
* <font color="red">User stories 通常应用于敏捷开发的需求管理</font>

## Actors/Roles/Personas

![](/static/2020-10-20-21-43-17.png)

* Actors【角色roles】描述目标用户类别（will perform actions on the system）
  * 避免过于笼统或具体 avoid actors that are too generic/specific
* 包括参与者使用系统，项目的动机目的 motivation
  * **goals**
  * **frustrations（requirements）**
* actors是证明项目特性功能存在的必要条件
* **system actor does not need to be a human**
* actors help to identify one of the types of system stakeholder to negotiate requirements with 分辨某一类特定项目参与人，进行协商需求

🍊 example

![](/static/2020-10-20-21-47-02.png)

* 目标
  * 卖出更多的房子(得到佣金)
  * 通过代理商，争取更多卖家买家
* frustration（当前需求，问题）
  * 顾客无法自己注册
  * 无法回答买家家访方面的问题

## User Stories/Features

once have a category of actor, could write user stories

![](/static/2020-10-20-21-52-44.png)

* 【角色actor】&【行为action want to perform on the system】& **【可实现目标rationale: what goal of frustration could be satisfy】**

### Requirements Scope

![](/static/2020-10-20-22-02-04.png)
![](/static/2020-10-20-22-16-55.png)

* 理论上，每个动作都应该为一个用户故事
  * 需求域无穷大【unbounded】
  * 许多用户故事多个系统之间会重复【duplicated，最基本需求重复】
  * 不应浪费时间在底层框架&依赖的验收测试上
* 专注开发中的**业务逻辑（特定业务，而不是最基本流程）**
  * 将框架特性看作为用户角色的假设

🍊 例子（业务逻辑，not底层框架）

* 底层框架
  * as a user i want to login so that i can access privileged features
* 业务逻辑
  * as an [actor/roles] i want to review a summary of my bank balances so that i can monitor my cash flow

### Non-Functional Requirements

![](/static/2020-10-20-22-20-06.png)

* can also be part of the user stories
  * 作为一个买家,我希望我的属性搜索返回在不到5秒钟,这样我就不会浪费时间了【**期待，预计，而不是特定功能**】
* 属于performance requirements

### Refinements of User Stories

![](/static/2020-10-20-22-24-02.png)

改进

* ->refine workflow
  * 添加场景描述
* partition epics -> stories
  * 将一个epics分割成更小的、独立的用户故事
* user story
  * add detail
  * cost
  * priority
* ->implementation breakdown
  * tasks

### adding more details

![](/static/2020-10-20-22-36-33.png)

### User Story Tasks

![](/static/2020-10-20-22-39-02.png)

Identification/separating of work tasks to realise the user story, using issue tracker

### Story Prioritisation - MOSCOW(Agile)

![](/static/2020-10-20-22-42-08.png)

* Must have
  * absolute critical minimum functions, without wont work
* Should have
  * **important but not critically essential**
  * make system **more convenient** use
* could have
* would be nice to have(or wont have time to do)
  * currently out of the budget of the project
  * potential function but wont be implemented at current scope

### Estimating Tasks

![](/static/2020-10-20-22-46-52.png)

* 难以**估计软件任务**
  * 一些从业者认为，**估计是徒劳的**
  * 有时候**估算对于预算和更广泛的规划是必要的**
  * 估算**可帮助识别不易理解的任务并强制改进**
* 估算方法
  * 德尔菲法Delphi methods -Planning Poker(Agile)
  * 市场测试(以中标价格，投标) Market testing (priced to win, bidding)
  * 经验方法 Empirical methods -COCOMO
  * 算法方法 Algorithmic methods-  Function point analysis

### Scenarios

![](/static/2020-10-20-23-00-32.png)
![](/static/2020-10-20-23-06-05.png)

* useful for
  * 描述基本工作流程
  * 指定验收测试
* 遵循 arrange/act/assert 流，类似于单元测试
  * condition holds...
  * fixture given
  * action performed on a feature

## where to store user stories/scenarios/tasks

![](/static/2020-10-20-23-07-31.png)

* 将用户故事保存在贴在团队空间的**墙上便签或白板上**
  * 作为一个整体**很容易重新组织和审查**reorganise & review
  * 很难跨团队地点分享 har to share across team locations
  * requires effort to maintain 需要维护

or

![](/static/2020-10-20-23-10-10.png)

* version control system
  * user stories
* issue tracker
  * tasks

## Assess User Story Quality - INVEST

![](/static/2020-10-20-23-11-10.png)

🍊 example

![](/static/2020-10-20-23-11-43.png)

作为一个用户，我想搜索属性，这样我就可以找到属性

![](/static/2020-10-20-23-13-55.png)
作为英格兰板球队的粉丝，我希望得到即将到来的比赛的通知，并能够按照匹配类型(T20、50-50、 Test)筛选这些通知。
一旦接到通知，我希望能够检索所有的比赛信息，比如谁在击球，比分是多少，谁在打保龄球，有多少次投球等等。
一旦我得到了详细的视图，我还希望能够找到一个球员的历史，如他们的击球数据。
这样我就可以了解我最喜欢的球队正在发生的一切

* too long, not independent

![](/static/2020-10-20-23-14-07.png)

作为一个司机

我希望乘客在商定的地点等候

这样我就可以接他们

## Summary

User stories are used to document the requirements (what should be implemented) for in a software system.

用户故事用于记录软件系统中的需求(应该实现什么)