# Content

* [Content](#content)
* [路由器模型 & 瓶颈：Router Model and its Bottlenecks](#路由器模型--瓶颈router-model-and-its-bottlenecks)
* [Prefix-Match Lookups](#prefix-match-lookups)
* [可变长度前缀：Variable Length Prefixes](#可变长度前缀variable-length-prefixes)
* [地址查询模型-- 观察和影响：Address Lookup Model – observations and implications](#地址查询模型---观察和影响address-lookup-model--observations-and-implications)
* [精确查找 - 线程索引/标签交换：Finessing Lookups – Threaded Indices and Tag Switching](#精确查找---线程索引标签交换finessing-lookups--threaded-indices-and-tag-switching)
* [前缀匹配的算法技术 单比特 tries：Algorithmic techniques for prefix matching – Unibit tries](#前缀匹配的算法技术-单比特-triesalgorithmic-techniques-for-prefix-matching--unibit-tries)
* [前缀匹配的算法技术 - 多比特Trie：Algorithmic techniques for prefix matching – Multibit tries](#前缀匹配的算法技术---多比特triealgorithmic-techniques-for-prefix-matching--multibit-tries)
* [固定位跨度Trie: Fixed Stride Tries](#固定位跨度trie-fixed-stride-tries)
* [渐进式更新：Incremental update](#渐进式更新incremental-update)
* [Lulea-Compressed tries](#lulea-compressed-tries)
* [=================](#)
* [交换：Switching](#交换switching)
* [From buses to crossbars](#from-buses-to-crossbars)
* [Crossbar交换调度设计问题： schedulers design issues](#crossbar交换调度设计问题-schedulers-design-issues)
* [问题-行首阻塞Head-Of-Line (HOL) blocking](#问题-行首阻塞head-of-line-hol-blocking)
* [解决HOL：avoiding HOL blocking](#解决holavoiding-hol-blocking)
* [PIM in action](#pim-in-action)
* [iSLIP步骤 in action](#islip步骤-in-action)
* [iSLIP运行观察： operational observations](#islip运行观察-operational-observations)
* [=================](#-1)
* [数据包调度：Scheduling packets](#数据包调度scheduling-packets)
* [随机早期检测：Random Early Detection (RED)](#随机早期检测random-early-detection-red)
* [带宽保证-令牌桶算法: Token bucket policing](#带宽保证-令牌桶算法-token-bucket-policing)
* [带宽保证-DRR差分查询：Deficit Round-Robin for bandwidth guarantees](#带宽保证-drr差分查询deficit-round-robin-for-bandwidth-guarantees)
* [=================](#-2)
* [Summary](#summary)

# 路由器模型 & 瓶颈：Router Model and its Bottlenecks

数据包进入 （等待缓冲时间，内部处理

![](/static/2022-05-13-16-55-57.png)

1. 查找数据报地址 B1 - address lookup
   1. 看数据包地址，识别prvious prefix，跟建议的output link相匹配
      1. router & switch

2. packet switching B2
   1. high speed
3. scheduling the output
   1. 确保速率 & 拥塞问题 （基于路由的拥塞控制避免机制）

# Prefix-Match Lookups

![](/static/2022-05-14-15-49-25.png)

有限时间窗口，有限容量大小

* 如何压缩管理资源，，（有限内存），以保存尽可能多的信息
* 分流 A, B，，数据库大小减少到两个前缀
  * 问题：某地址两种前缀都匹配下  ，，DB应返回最长匹配
* 有限（路由器）内存下需要有一个压缩表示方法，让任何类型前缀都有可能匹配到

# 可变长度前缀：Variable Length Prefixes

![](/static/2022-05-14-15-57-37.png)

互联网前缀表示，

* 每个domian 8bit
* 需要将流量路由到IP地址所属的网络，变长前缀来确定

为什么需要变长前缀，

* 更有效利用地址空间
* 无类别域间路由（Classless Inter-Domain Routing、CIDR
  * 处理类耗尽的问题

# 地址查询模型-- 观察和影响：Address Lookup Model – observations and implications

![](/static/2022-05-14-16-05-06.png)

这些观察结果推动了前缀查找算法的研究 & 开发，&为什么这样做（为了使查找更快

* 何时何种环境下运行
* 第一行
  * 缓存效果差，因为路有缓存要经常更换
* 倒数2
  * SRAM高成本，必须尽量减少内存访问，设置扩展速度不耗尽内存上限

路由器中的查找方案适用性，取决于

* 内存的查找速度， & 更新时间

# 精确查找 - 线程索引/标签交换：Finessing Lookups – Threaded Indices and Tag Switching

![](/static/2022-05-14-16-22-20.png)

每个路由器向下一个路由的转发表传递一个索引，，避免前缀查找

* 索引由路由协议预先计算
  
索引更新&传播

* Bellman Ford算法，算最短路径成本

# 前缀匹配的算法技术 单比特 tries：Algorithmic techniques for prefix matching – Unibit tries

![](/static/2022-05-14-16-29-52.png)

# 前缀匹配的算法技术 - 多比特Trie：Algorithmic techniques for prefix matching – Multibit tries

前面单比特得一step一step查

controlled prefix expansion

![](/static/2022-05-14-16-37-47.png)
![](/static/2022-05-14-16-38-03.png)

# 固定位跨度Trie: Fixed Stride Tries

![](/static/2022-05-14-19-23-03.png)
![](/static/2022-05-14-19-31-18.png)

每个节点存储

* 前缀
* 指针，指向存储longer prefix的结构

# 渐进式更新：Incremental update

![](/static/2022-05-14-19-37-26.png)
![](/static/2022-05-14-19-40-34.png)

# Lulea-Compressed tries

先前用空间换时间，Lulea进一步压缩，bitmap compression

![](/static/2022-05-14-20-49-40.png)

# =================

# 交换：Switching

![](/static/2022-05-14-21-30-25.png)

下一条输出端口 output port

* 路由器核心 - 交换系统 B2
* 需要支持多播链接 multicast connections
  * 输入对应多个输出口

最简单交换实现

* **共享内存** shared memory
* **数据包从输入链路读入内存，并从存储器中读出至适当的输出链路**
* **内存带宽是主要问题**;
  * 一个有N个输入和N个输出链路的交换机的内存需要以每个链路的2N倍速度运行，才能对每个数据包或单元进行一次读写。
* 对于**少量的端口来说，可以是高效的**，因为数据只在内存中进出一次。
* **基于网状结构/交叉条的交换机(风下寸)几平无一例外地需要【对数据包进行两次缓冲**】Fabric-/crossbar-based switches (see later) almost invariably require buffering packets twice, potentially doubling power and memory costs
  * ，可能会使功率和内存成本翻倍。

# From buses to crossbars

交换机架构如何发展

![](/static/2022-05-14-21-35-48.png)

# Crossbar交换调度设计问题： schedulers design issues

![](/static/2022-05-14-21-40-20.png)

---

算法步骤 （当多个输入LC试图传输给输出，问题：如何最大化并行性同时解决争用contention)

![](/static/2022-05-14-22-04-00.png)
![](/static/2022-05-14-22-08-08.png)

* take a ticket scheme
* 每个LC S输出维护分布式队列，包含想与之交流的输入LC
  * 一种指示-票号simple ticket number，保存在input LC
* 发送数据包，通过单独控制总线发送请求
* 输出S通过控制总线将队列号送回输入R
  * 队列号 = 输入R在S的输出队列位置
* 输入R监控控制总线，输出S完成接受一个新包，S将当前队列号传至**控制总线**
  * 如果R注意到是自己的队列号，R将包传送至**输入数据总线**

# 问题-行首阻塞Head-Of-Line (HOL) blocking 

上面算法方案可以处理可变大小的数据包，不管数据包具体大小，只要到了就能队列处理 （因为输出端口异步广播下一个票号 asynchronously broadcast next ticket number

![](/static/2022-05-14-22-20-36.png)

* **行首阻塞问题 HOL**
  * 限制了并行性
  * 因为每个LC port一次只能服务一个packet，剩下的都得等待
* 阻塞概率 - 63%

# 解决HOL：avoiding HOL blocking

![](/static/2022-05-15-00-16-25.png)

1. 输出队列 output queueing
2. PIM - Parallel Iterative Matching
   1. VOQ, Virtual Output Queueing
   2. randomisation

---

# PIM in action

PIM步骤

![](/static/2022-05-15-00-20-04.png)

---

PIM problem

* 随机化问题
* 对数数量的迭代->达到最大匹配度
* **使用iSLIP** 取消PIM随机化 derandomize PIM , **1-2次迭代后最大匹配度**
  * 每个输出端口维护指针g，
    * 代表应该授权的输入端口
    * 匹配后，指针递增到下一端口号
  * 每个输入端口接受指针α
    * 在多个输出端选择，类似上面
* 当output port与input port X匹配时， output port递增

# iSLIP步骤 in action

![](/static/2022-05-15-00-41-04.png)
![](/static/2022-05-15-00-45-23.png)

# iSLIP运行观察： operational observations 

starvation 饥饿

* 避免饥饿，只在第一次（每轮）迭代递增指针
* VOQ虚拟输出队列分配优先级, QoS
  * VOQ是网络设备接口在无拥塞的情况下,防止HOL阻塞（Head of Line Blocking）的队列技术。

![](/static/2022-05-15-01-02-33.png)

# =================

# 数据包调度：Scheduling packets 

至此先前已经决定了数据包应该被传输到哪个输出端口，，，然后要再输出队列里调度

![](/static/2022-05-15-01-12-06.png)

* 比如数据包放入有tail-drop策略的FIFO队列中
  * tail-DROP 尾部丢弃，队列填满后丢弃后续的分组。
  * 传统的队列长度管理机制是“去尾”( Drop Tail )。它有点类似于FIFO(先入先出)的存储方式。Drop Tail最大的优点是原理简单。当路由器队列长度达到最大值时，通过丢包来指示拥塞，先到达路由器的分组首先被传输。由于路由器缓存有限，如果包到达时缓 存已满，那么路由器就丢弃该分组。一旦发生丢包，发送端立即被告知网络拥塞，从而调整发送速率。这种做法不考虑被丢弃包的重要程度。
* why anything beyond tail-drop
  * congestion
    * 通过调整输出缓冲 output buffer 改进拥塞

# 随机早期检测：Random Early Detection (RED)

> 随机早期检测（RED，Random Early Detection）算法将队列的平均队长作为决定拥塞避免机制是否应被处罚的随机函数的参数，增加了在队列长度变得太大之前平滑瞬时拥塞的可能性，减少了同时使多个流受分组丢弃影响的可能性。
> 
> **通过监控路由器输出端口队列的平均长度来探测拥塞，一旦发现拥塞逼近，就随机地选择连接来通知拥塞，使他们在队列溢出导致丢包之前减小拥塞窗口，降低发送数据速度，从而缓解网络拥塞**。由于RED是基于FIFO队列调度策略的，并且只是**丢弃正进入路由器的数据包**，因此其实施起来也较为简单

![](/static/2022-05-15-01-52-03.png)

* 监控输出队列平均长 average output queue
* 超过阈值，根据概率随机丢弃新到达的包
  * 即使还存在缓冲空间
* RED路由尝试避免TCP更多的超时timeout
  * TCP慢开始->速度到阈值，线性增长->第一个丢包，窗口减到一半->再次线性增长->多丢包->**等待200ms超时timeout**-> TCP慢开始
  * 减少这个timeout （**避免相同流多包丢失带来的excessive timeouts**

# 带宽保证-令牌桶算法: Token bucket policing 

> 流量整形通常是为了使报文速率与下游设备相匹配。**当从高速链路向低速链路传输数 据或发生突发流量时，带宽会在低速链路出口处出现瓶颈，导致数据丢失严重。这种 情况下，需要在进入低速链路的设备出口处进行流量整形**；所以流量整形是一种主动调整流量输出速率的措施，其作用是限制流量与突发，使这类报文以比较均匀的速率向外发送。
> 
> 流量整形是用于**限制某个或某些队列的输出速率，超速的报文不是直接丢弃，而是暂时存在缓存里，等空闲了还是会输出，只有缓存满了之后才会丢弃**。流量整形将上游不规整的流量进行削峰填谷，使流量输出比较平稳，从而解决下游设备的拥塞问题。

* 当报文到来的时候，首先对报文进行分类，使报文进入不同的队列。
* 若报文进入的队列没有配置队列整形功能，则直接发送该队列的报文；否则，进入下一步处理。
* 按用户设定的队列整形速率向令牌桶中放置令牌：
  * **如果令牌桶中有足够的令牌可以用来发送报文，则报文直接被发送，在报文被发送的同时，令牌做相应的减少**。
  * **如果令牌桶中没有足够的令牌**，则将报文放入缓存队列，如果报文放入缓存队列时，缓存队列已满，则丢弃报文。
* **缓存队列中有报文的时候，会与令牌桶中的令牌数作比较，如果令牌数足够发送报文则转发报文，直到缓存队列中的报文全部发送完毕为止**。

---

> 令牌桶（Token-Bucket）是目前最常采用的一种流量测量方法，用来**评估流量速率是否超过了规定值**。这里的**令牌桶是指网络设备的内部存储池**，而**令牌则是指以给定速率填充令牌桶的虚拟信息包**。
> 令牌桶可以看作是一个存放令牌的容器，预先设定一定的容量。
>
> 系统按设定的速度向桶中放置令牌，当桶中令牌满时，多余的令牌溢出。
> **匀速的产生令牌，往桶里面丢；每次请求来，看是否有多余的令牌。如果有，获取令牌执行正常业务；如没有，丢包限速**。

![](/static/2022-05-15-02-04-49.png)

令牌桶算法中想象了一种令牌和令牌桶。**令牌桶容量为 b，令牌以速率 r 产生，比如 r = 2 表示每秒产生 2 个令牌，即每 500ms 产生一个**。

* **当大量请求进来时，每个请求会消耗一个令牌，当令牌被消耗时，让后面的请求处于等待状态或直接将其拒绝掉**。
* 是不是很简单？实现的话，不需要实现令牌，也不需要实现令牌桶，
  * **只需要有一个变量记录令牌数，当请求进来时，判断令牌数是否为零，来决定处理还是拒绝，若处理的话要让令牌数 -1**。
  * 另外需要一个线程来只要令牌数小于 b 就以 r 的速率让令牌数 +1

![](/static/2022-05-15-02-06-35.png)

* 如何为公共队列中的流量提供带宽保证-限速  offer bandwidth guarantees to flows placed in the common queue, e.g., **rate limiting**
* 通过控制平均速率，限制流的最大突发大小  Limit the burstiness of a flow through controlling its average rate and maximum burst size (e.g., allow 100Kb/s average and up to 4KB as a burst)

# 带宽保证-DRR差分查询：Deficit Round-Robin for bandwidth guarantees 

> 拥塞管理是指在网络间歇性出现拥塞，时延敏感业务要求得到比其它业务更高质量的QoS服务时，通过调整报文的调度次序来满足时延敏感业务高QoS服务的一种流量控制机制。

DRR（Deficit Round Robin）调度实现原理与WRR调度基本相同。

DRR与WRR的区别是：WRR调度是按照报文个数进行调度，**而DRR是按照报文长度进行调度。如果报文长度超过了队列的调度能力，DRR调度允许出现负权重，以保证长报文也能够得到调度。但下次轮循调度时该队列将不会被调度，直到权重为正，该队列才会参与DRR调度**。

* DRR调度避免了采用PQ调度时**低优先级队列中的报文可能长时间得不到服务的缺点，也避免了各队列报文长度不等或变化较大时，WRR调度不能按配置比例分配带宽资源的缺点**。
* 但是，DRR调度也具有低延时需求业务（如语音）得不到及时调度的缺点。

![](/static/2022-05-15-02-24-50.png)
![](/static/2022-05-15-02-25-45.png)

* 赤字Di初始化为0
* 赤字计数器Di只有在还有数据包要发送的时候才会累积，否则重置为0
* a flow is guaranteed a minimum bandwidth over its duration, proportional to the ratio of its quantum size (over the sum of quantum sizes)* **保证一个流在其持续时间内的最小带宽，与它的量子大小Qi的比率（超过量子大小的总和）成正比**

# =================

# Summary

![](/static/2022-05-15-02-28-10.png)