# Content

* [Content](#content)
* [传统交换机架构：Traditional Switch Architecture](#传统交换机架构traditional-switch-architecture)
* [控制平面：Traditional Switch Control Plane](#控制平面traditional-switch-control-plane)
* [交换机和控制平面的演变：Evolution of switches and control planes](#交换机和控制平面的演变evolution-of-switches-and-control-planes)
* [Software-Defined Networking (SDN)](#software-defined-networking-sdn)
* [SDN – 减少成本：reducing cost (and complexity)](#sdn--减少成本reducing-cost-and-complexity)
* [SDN – 促进创新：facilitating innovation](#sdn--促进创新facilitating-innovation)
* [The road to SDN](#the-road-to-sdn)
* [抽象关键：Abstraction is key (Liskov – The power of abstractions)](#抽象关键abstraction-is-key-liskov--the-power-of-abstractions)
* [控制平面抽象：Abstractions for a control plane](#控制平面抽象abstractions-for-a-control-plane)
* [1.网络（分布式）状态抽象：Network (distributed) state abstraction](#1网络分布式状态抽象network-distributed-state-abstraction)
* [2.配置抽象：2. Specification (config) abstraction](#2配置抽象2-specification-config-abstraction)
* [3.转发抽象：3. Forwarding abstraction](#3转发抽象3-forwarding-abstraction)
* [Openflow基本操作：Basic Operations of OpenFlow](#openflow基本操作basic-operations-of-openflow)
* [SDN基本特性：Fundamental characteristics of SDN](#sdn基本特性fundamental-characteristics-of-sdn)
* [SDN操作 - 网络自动化和虚拟化：SDN Operation – Network Automation and Virtualization](#sdn操作---网络自动化和虚拟化sdn-operation--network-automation-and-virtualization)
* [SDN操作 -流，表，匹配：Operation: Flows, Tables & Matching](#sdn操作--流表匹配operation-flows-tables--matching)
* [SDN设备&应用：SDN Devices and Applications](#sdn设备应用sdn-devices-and-applications)
* [SDN控制器：Controller](#sdn控制器controller)
* [OpenFlow Specification](#openflow-specification)
* [OpenFlow 1.0消息类型： Controller-to-Switch Message Types](#openflow-10消息类型-controller-to-switch-message-types)
* [Controller-Switch Protocol Session and Programming Flow Table (V.1.0)](#controller-switch-protocol-session-and-programming-flow-table-v10)
* [1.0基本包转发&控制器转发：basic packet forwarding and forwarding to controller](#10基本包转发控制器转发basic-packet-forwarding-and-forwarding-to-controller)
* [==============](#)
* [OpenFlow 1.1 (02/2011) Additions](#openflow-11-022011-additions)
* [OpenFlow 1.2 (12/2011) Additions](#openflow-12-122011-additions)
* [OpenFlow 1.3 (04/2012) Additions](#openflow-13-042012-additions)
* [OpenFlow 1.4 (10/2013) Additions](#openflow-14-102013-additions)
* [OpenFlow 1.5 (12/2014) Additions](#openflow-15-122014-additions)
* [单表/多表处理管道：Single/Multiple Table Processing pipeline](#单表多表处理管道singlemultiple-table-processing-pipeline)
* [SDN Applications](#sdn-applications)
* [OpenFlow Limitations](#openflow-limitations)
* [SDN with OpenFlow – centralize networkwide decision-making](#sdn-with-openflow--centralize-networkwide-decision-making)

# 传统交换机架构：Traditional Switch Architecture

![](/static/2022-05-15-03-00-12.png)

* 数据平面
  * 非常具体的行为 & 算法，尽可能快的运行
* 控制平面
  * 数据平面，不知道如果处理特定的数据包，可能会向控制平面请求支持或发送数据包
* 管理平面
  * 配置 & 监控交换机行为
  * 管理平面还负责运行协议，例如下周将在网络网络性能和测量技术中研究的简单网络管理协议。 
    * 作为 MP，它是关于收集统计数据和交流统计数据，报告从点击时间到设备操作的统计数据。

# 控制平面：Traditional Switch Control Plane

![](/static/2022-05-15-03-06-35.png)

* L2
  * 泛洪 flooding 学习发送方MAC，更新转发表 forwarding table
    * 分层 （桥接） 拓扑结构会产生无限循环
    * 无TTL
  * 每个交换机执行生成树协议 SPT spanning tree protocol
* L3
  * 接收数据包基本决定：转发或本地处理 forward or process locallyu
    * **是控制数据包还是转发数据包**
  * 路由协议建立路由表
    * 内部，RIP,OSPF,IS-IS
    * 外部，BGP
* 30%路由器CPU资源用于控制平面

# 交换机和控制平面的演变：Evolution of switches and control planes

![](/static/2022-05-15-03-13-30.png)

* 早期都是由软件实现
  * 转发
  * 路由
  * plug-n-play即插即用网络设备（一段时间后，自动配置），手动配置manual configuration设备，小型网络
* 分布式
  * 垂直集成在一个设备中
* 问题
  * 如何扩展交换机行为，并摆脱垂直集成架构
  * 分布式智能越来越复杂，握手方式越来越复杂
    * 但是网络管理仍原始，比如cmd管理设备 CLI

# Software-Defined Networking (SDN)

上面演变导致SDN

![](/static/2022-05-15-03-20-11.png)

* 移动物理设备的控制到**通用计算环境** moving the control off the device into logically centralised compute resource
  * 可以维护网络全局视图
  * 能做出最优决策（对于本地机器来说
* SDN隔离了网络活动 segregate network activities
  * Forwarding, filtering, and prioritization – on-device
  * • Control – off-device
  * • Application – programmed in software 
  * 最后两个可以看为一个
* **SDN目的** main motivations
  * Reduce cost; improve configuration and management; flexibility and ease of service deployment – defined in software 降低成本；改进配置和管理；**服务部署的灵活性**和易用性——在软件中定义

# SDN – 减少成本：reducing cost (and complexity)

驱动因素1

![](/static/2022-05-15-03-25-39.png)

* 每个设备需要支持越来越多的算法核协议，控制平面变得非常复杂
  * 不想每年更替交换机，希望适应网络增长 & 服务
    * 如果有可编程环境可以降低复杂性，但是之前垂直架构不能实现
* 封闭环境，市场垄断
  * 初创公司更难进入市场
* 手动配置增加了网络运营成本

# SDN – 促进创新：facilitating innovation

另一个驱动因素

数据中心环境的推动

![](/static/2022-05-15-03-29-30.png)

# The road to SDN

多年项目经验的反映

![](/static/2022-05-15-03-33-18.png)

mid 90

* 难以在整个互联网上升级TCP IP更好的拥塞控制

领域的工作主要不是为了降低网络设备的核心成本，而是主要来自网络创新和加速引入新设备的需求

# 抽象关键：Abstraction is key (Liskov – The power of abstractions)

![](/static/2022-05-15-03-38-09.png)

* 抽象是问题的分解
  * 。寻找基本组件(任务)
  * 为每个组件定义一个抽象概念
  * 实施抽象化可以专注于一项任务
  * 如果某项任务难以实拖，就再次分解它
* 数据平面已有抽象
  * 创新 - 服务接口衔接上下层对话

为什么抽象重要

![](/static/2022-05-15-03-39-20.png)

# 控制平面抽象：Abstractions for a control plane

![](/static/2022-05-15-03-44-34.png)

控制平面计算转发状态 compute forwarding state

* 基于整个拓扑
  * 网络状态
* 所有网络设备
  * 简化配置
* 应该与低级别设备参数一致
  * 转发模型

# 1.网络（分布式）状态抽象：Network (distributed) state abstraction

![](/static/2022-05-15-03-49-43.png)

因此，就网络状态而言，您可能想要这样的东西，您想要一个带注释的图表，在逻辑中心中显示您的网络。

控制器，你知道你有多少个交换机，谁连接到谁，链路容量是多少，甚至更好的是，实际链路利用率是多少。

哪个成本连接到哪个交换机，如果链接断开，希望能知情，并尽快重新配置

* 分布式算法，集中方式
  * 路由算法可能需要几分钟才能收敛

# 2.配置抽象：2. Specification (config) abstraction

![](/static/2022-05-15-03-52-02.png)

控制程序表达网络预期行为

* 网络虚拟化 network virtualization

# 3.转发抽象：3. Forwarding abstraction

![](/static/2022-05-15-03-57-20.png)

数据包转发架构

* 独立实现，与硬件无关 hardware agnostic
* 独立于供应商 vendor independent

OpenFlow

* 转发决定基于流表信息 flow table
  * 关于流 & 针对流采取的动作
  * 根据流表entry配置交换机
  * match action scheme
* 有个流量控制器，逻辑集中的控制平面，(也可以是物理集中的，但是扩展到大型网络行不通 controller
  * 控制器对整个网络有了解

# Openflow基本操作：Basic Operations of OpenFlow

![](/static/2022-05-15-04-01-01.png)

* 有匹配的行动就执行
* 没有与控制器该数据包的匹配的流表entry，或交换机不知道怎么处理
  * 控制器扩展或修改流表进行响应
  * 或者采取显式操作来告诉交换机如何处理这个包

# SDN基本特性：Fundamental characteristics of SDN

![](/static/2022-05-15-04-03-39.png)

基于软件的控制器使用更高级别的策略管理网络；向简化的设备发送原始指令，使它们能够对进入的数据包做出快速决定 Software-based controller manages networks using higher level policies; primitive instructions sent to simplified devices to allow them to make fast decisions on incoming packets

# SDN操作 - 网络自动化和虚拟化：SDN Operation – Network Automation and Virtualization

![](/static/2022-05-15-04-07-54.png)

# SDN操作 -流，表，匹配：Operation: Flows, Tables & Matching 

每个SDN设备都有一个流表，包含流条目&相关动作

![](/static/2022-05-15-04-10-54.png)

# SDN设备&应用：SDN Devices and Applications 

![](/static/2022-05-15-04-13-00.png)

# SDN控制器：Controller

![](/static/2022-05-15-04-18-16.png)

软件

* 有核心模块实现拓扑发现，设备管理等

问题

* 南向，北向API
* 南向接口标准化，北向不是 While the Southbound interface is standardized, the Northbound Interface (NBI) is not
* 一个控制器上的多个SDN应用如何 协调（例如，流量优先）。How do multiple SDN applications on a controller coordinate (e.g., flow prioritisation)
  - 哪个应用应该首先拥有该事件？• Which application should have the event first?
  - 它应该把它传给谁，等等。• Who should it pass it on to the next, etc.?
- 很少有大规模部署的情况 • Few large-scale deployments

# OpenFlow Specification

* 数据包与流表条目匹配，采取相应动作
* 不匹配的时候控制器要么发到输出端口，要么传到包匹配函数 packet matching function

![](/static/2022-05-15-04-23-10.png)
![](/static/2022-05-15-04-25-36.png)

# OpenFlow 1.0消息类型： Controller-to-Switch Message Types

![](/static/2022-05-15-04-26-21.png)

# Controller-Switch Protocol Session and Programming Flow Table (V.1.0)

![](/static/2022-05-15-04-28-33.png)

# 1.0基本包转发&控制器转发：basic packet forwarding and forwarding to controller

![](/static/2022-05-15-04-34-03.png)

* **将数据包转发到控制器基本动作以进行异常处理** Forwarding packets to controller fundamental action for exception handling
*  没有找到匹配的流量条目 OFPR_NO_MATCH- no matching flow entry found
* 指定一个匹配的条目总是转发到控制器。OFPR_ACTION-OF specifies that a matching entry alwaysforwarded to controller
  * 一个控制或路由协议更新包 E.g., a control or routing protocol update packet
* 可能会导致控制器运行路由协议并进一步更新交换机中的流量条目（例如，通过OFPT FLOW MOD）。 Might result in the controller running the routing protocol andupdating further flow entries in the switch (e.g., through OFPT FLOW MOD)
* 控制器可能需要访问整个数据包或只访问标题；交换机可以在本地缓冲数据包，并在需要时向控制器提供ID，以便以后获取。
 Controller might need to access full packet or only headers;switch can buffer packet locally and provide lD to controller tofetch later if needed

![](/static/2022-05-15-04-36-41.png)

* 流量入口语义允许在各种协议头上进行匹配 Flow entry semantics allow matching on  wide variety of protocol headers 
* 但交换机将只对那些与其在数据包转发中的角色相对应的数据包进行编程（例如，L2与L3）。But a switch will be programmed only  for those that correspond to its role in  pkt forwarding (e.g., L2 vs. L3) 
* 如果潜在的流量匹配出现重叠，控制器分配给流量条目的优先级将决定哪个匹配优先。If overlap in potential matches of flows, priority assigned to flow entry by the controller determines which match takes precedence

# ==============

# OpenFlow 1.1 (02/2011) Additions

![](/static/2022-05-15-04-40-11.png)
![](/static/2022-05-15-04-40-56.png)

# OpenFlow 1.2 (12/2011) Additions

![](/static/2022-05-15-04-41-40.png)

# OpenFlow 1.3 (04/2012) Additions

![](/static/2022-05-15-04-42-12.png)

# OpenFlow 1.4 (10/2013) Additions

![](/static/2022-05-15-04-42-44.png)

# OpenFlow 1.5 (12/2014) Additions

![](/static/2022-05-15-04-43-11.png)

# 单表/多表处理管道：Single/Multiple Table Processing pipeline

![](/static/2022-05-15-13-47-37.png)

* 传统交换机 -> 针对不知道怎么转发处理的数据包，泛洪
* Openflow交换机->询问控制器

# SDN Applications 

![](/static/2022-05-15-13-41-41.png)

* reactive SDN application

# OpenFlow Limitations

![](/static/2022-05-15-13-40-43.png)

# SDN with OpenFlow – centralize networkwide decision-making 

![](/static/2022-05-15-13-42-06.png)

* 以便以更集中有效的方式实现分布式算法