# Content

课程的最后部分谈到了未来的方向和开放的问题。它指出，围绕着如何最好地支持并发编程、数据中心规模的计算和大规模分布式操作系统，仍有一些开放性问题。它还强调了现代编程语言对于开发在单一系统中运行的程序的好处，并指出了在扩展到大规模分布式系统中的挑战。

* [Content](#content)
* [系统编程：system programming](#系统编程system-programming)
* [Key lessons](#key-lessons)
* [并发：Concurrency](#并发concurrency)
* [数据中心规模计算:Data Centre Scale Computing](#数据中心规模计算data-centre-scale-computing)
* [分布式操作系统：Distributed Operating Systems](#分布式操作系统distributed-operating-systems)
* [未来系统编程语言：Future Systems Programming Languages](#未来系统编程语言future-systems-programming-languages)
* [扩展系统编程:Scaling Systems Programming](#扩展系统编程scaling-systems-programming)

# 系统编程：system programming

- 课程讨论了系统编程的高级主题。
- 什么是系统编程？
- 类型系统
- 基于类型的建模和设计
- 资源所有权和内存管理
- 垃圾收集
- 并发性
- 循环器和异步编程
- 安全考虑

> 所以，在这个课程中，我们讨论了系统编程领域的一些高级话题。我们花了一些时间讨论**什么是系统编程，以及系统程序与应用程序的区别，重点是对内存布局的控制，以及性能需求**，等等。我们花了相当多的时间讨论类型系统，以及基于类型的建模和设计，并思考**高级类型系统对构建系统程序的好处**。思考一些围绕**使用类型对问题空间进行建模的限制和问题，并利用这些限制和问题来帮助程序的健壮性和安全性**。我们**非常关注资源所有权和内存管理，以及跟踪程序如何使用、控制和拥有资源，包括内存，也包括状态机，以及状态机中的状态变量。我们谈到了不同的内存管理方法，引用计数方法，垃圾收集，以及基于区域的内存管理**。我们还花了相当多的时间来讨论**并发性。谈论信息传递的并发性方法。谈及事务，谈及协程和异步编程**。我们还花了一些时间来讨论**为什么使用多线程、锁和共享可变状态的常见方法不是一种有效的方法，而且不能扩展到更大的系统，而且很难得到正确的结果**。最后，我们讨论了这些系统中的一些**安全考虑**，并思考了我们**如何使用高级类型系统来明确代码中的假设，并减少导致安全漏洞的不一致的风险**。我们还花了一些时间讨论了**内存不安全是安全漏洞的主要原因**。So, in this course we've discussed a number of advanced topics in the systems programming space. We spent some time talking about what is systems programming, and what distinguishes a systems program from an applications program, focusing on control over memory layout, and performance needs, and so on We spent a fair amount of time talking about type systems, and type based modelling and design, and thinking about the benefits of advanced type systems for building systems programs. Thinking about some of the constraints and issues around modelling the problem space using types, and using that to help robustness and security of programs. We focused a lot on resource ownership and memory management, and tracking how the program works with, controls, and owns resources, including memory, but also including state machines, and the state variables in state machines. And we spoke about different approaches to memory management, the reference counting approaches, garbage collection, and region-based memory management. And we spent a fair amount of time talking about concurrency. Talking about message passing approaches to concurrency. Talking about transactions, talking about co-routines and asynchronous programming. And we spent some time discussing why the common approach of using multithreading, locks, and shared mutable state, is not an effective approach, and doesn't scale out to larger systems, and is very hard to get right. And, we just finished up by talking about some of the security considerations in these systems, and thinking about how we can use advanced type systems to make explicit the assumptions in the code, and reduce the risk of inconsistencies which lead to security breaches. And we spent some time talking about how memory unsafety is the primary cause of security vulnerabilities.

# Key lessons

- 系统程序需要对数据布局进行低层次控制•	Systems programs need low-level control of data layout
  - 设备驱动程序
  - 网络协议
  - 文件I/O
- **这种控制不需要以牺牲安全为代价--现代语言可以提供控制**。•	This control does not need to come at the expense of safety – modern languages can provide control
  - 通过对数据布局的控制实现内存安全
  - 避免自由使用后、数据竞赛、迭代器无效的情况
- **安全的并发性很重要，但所有的方法都是有取舍的**•	Safe concurrency is important, but all approaches have trade-offs
- **类型系统可以帮助对问题域进行建模**•	Type systems can help model the problem domain
  - 对假设进行建模--正确性和安全性

> 我们从这里学到了什么？好吧，我认为**系统程序的一个关键特征是，它们需要对数据布局进行低层次的控制。为了有效地编写设备驱动程序，为了有效地编写网络协议或文件I/O，为了有效地利用内存，适应缓存，并优化数据结构的布局以适应缓存，我们需要能够控制数据在内存中的布局方式**。**系统程序的关键特征之一就是它们提供了这种控制**。在未来，我希望系统编程语言能够继续这样做。我们**需要能够准确地指定数据结构在内存中的布局方式，并且能够控制它是在堆栈还是在堆上分配。并且能够根据我们的需要对数据结构进行装箱或拆箱，以获得良好的性能**。What did we learn from this? Well, I think one of the key distinguishing features with systems programs, is that they need low-level control over the data layout. In order to effectively write device drivers, in order to effectively write network protocols, or file I/O, and in order to make effective use of memory, and fit within the cache, and optimise the layout of data structures to fit within the cache, we need to be able to control the way data is laid out in memory. One of the key features of systems programs, is that they give that control. And going forward, I expect systems programming languages to continue to do that. We need to be able to specify exactly how a data structure is laid out in memory, and to be able to control whether it is allocated on the stack or on the heap. And to be able to box, or unbox, data structures as we need to, to get good performance. 
> 
> * 在传统的系统编程语言中，这种控制是以牺牲很多安全性为代价的。C语言为你提供了对数据在内存中布局的大量控制，以及对表示法和指针使用的大量控制，等等，但这种方式是相当不安全的，而且很难做到正确。In traditional systems programming languages, that control has come at the expense a lot of safety. C provides you with a tremendous amount of control over the way data is laid out in memory, with tremendous amount of control over representations, and pointer use, and so on, but in a way that's quite unsafe, and quite hard to get right. 
> * 更现代的语言，Rust是我们在本课程中使用的例子，但肯定不是这个领域的唯一语言，它以更安全的方式提供了这种类型的行为。他们让你控制数据布局，但他们也提供内存安全，他们检查数组边界，他们检查指针是否被正确使用，他们可以避免自由使用后的错误、数据竞赛、迭代器无效等问题，并获得对数据布局的控制，同时保持更多的安全，更强大的行为。More modern languages, Rust being the example we’re using in this course, but certainly not the only language in the space, provide this type of behaviour in a much safer way. They give you control over data layout, but they also provide memory safety, they check it array bounds they check pointers are used correctly, and they can avoid problems like use-after-free bugs, data races, iterator invalidation, and so on, and get the control over data layout whilst maintaining a lot more safety, a lot more robust behaviours. 
> 
> 我认为很明显，**并发性是很重要的，特别是当摩尔定律和Dennard扩展被打破时，我们会得到越来越多的并发性系统。我们谈到了几种不同的并发方法，但我认为很明显，它们都有权衡之处，而且没有一个是建立并发系统的万能方法**。I think it's clear that concurrency is important, especially as Moore's law and Dennard scaling break down, and we get increasingly concurrent systems. And we spoke about several different approaches to concurrency, but I think it's clear they've all got trade-offs, and none of them are a one size fits all approach to building concurrent systems. 
> 
> * 我认为，事务性内存方法在Haskell中非常有效，但不清楚它是否可以扩展到其他语言。消息传递方法在Erlang中效果很好，但在许多其他语言中却不太适合，也不太自然，而且在某些情况下有一些困难的安全问题。而基于异步程序的方法会导致饥饿和阻塞的奇怪行为。有一些限制，而且不清楚我们是否有正确的答案。The transactional memory approach, I think, works wonderfully in Haskell, but it's not clear it scales out to other languages. The message passing approach works wonderfully in Erlang, but fits less well, less naturally, into many other languages, and has some difficult safety issues in some cases. And the asynchronous coroutine based approach leads to strange behaviours with starvation and blocking. There’s constraints, and it’s not clear we have the right answer. 
> 
> 我们已经表明，**类型系统，我认为，可以帮助对问题域进行建模。我想这是关键的收获之一。我们可以对假设进行建模，我们可以帮助确保系统的安全和正确。我们可以通过将更多的信息编码到类型中，并让编译器帮助我们检查系统的正确性，从而使系统更加安全，并避免很多常见的问题**。And we’ve shown that type systems, I think, can help model the problem domain. I think that's one of the key takeaways. We can model the assumptions, we can help ensure the system is safe and correct. And we can make the systems much safer, and avoid a lot of common problems, by encoding more information into the types, and letting the compiler help us check the systems for correctness.

# 并发：Concurrency

- 并发编程仍然没有好的解决方案•	Still no good solutions for concurrent programming
  - **事务性内存**与不纯正的命令式语言的关系并不融洽•	Transactional memory doesn’t sit well with impure imperative languages
  - **消息传递**有背压、竞赛条件、死锁、大规模协调等问题。•	Message passing has issues with back pressure, race conditions, deadlocks, large-scale orchestration
  - **异步代码**引入了微妙的阻塞和饥饿的bug•	Asynchronous code introduces subtle blocking and starvation bugs
  - 不清楚什么是正确的解决方案•	Unclear what is the right solution

- **这些方法都不能用于GPU编程→隐式并行？ 数据并行阵列类型**？•	None of these approaches scale to GPU programming → implicit parallelism? data parallel array types?

> 我们的未来在哪里？嗯，正如我提到的，我认为我们仍然没有很好的并发编程的解决方案。**事务性内存**，我认为是非常有前途的，但并不适合不纯的命令式语言，而我们今天写的大部分程序都是用不纯的命令式语言写的。**消息传递**是一种很自然的方法，很适合主流语言，并且解决了共享可变状态、线程和锁的一些问题，但是它在提供背压方面有问题，它可能有竞赛条件和死锁，而且很难在不丢失消息流的情况下构造程序，构造大型的消息传递系统。**异步代码**引入了微妙的阻塞和饥饿的错误。而且，**现在还不太清楚什么是正确的解决方案**。 Where we going in the future? Well, as I mentioned, I think we still don't have good solutions for concurrent programming. Transactional memory, I think, is very promising, but doesn't fit well with impure imperative languages, and most of the programs we write today are written in impure imperative languages. Message passing is quite a natural approach, and fits nicely into mainstream languages, and solves some of the problems with shared mutable state, and threads and locks, but it's got problems with providing back pressure, it can have race conditions and deadlocks, and it's difficult to structure programs, structure large message passing systems, without losing track of the message flows. And asynchronous code introduces subtle blocking and starvation bugs. And it's not really clear what's the right solution.

# 数据中心规模计算:Data Centre Scale Computing

我们如何扩大系统 编程到 数据中心或边缘？How do we scale systems programming to the data centre or the edge?

- 在数以百万计的核心上进行分布式内存计算•	Distributed memory computation on millions of cores
- 在这种规模下很难配置节点和通信 - 自动配置、扩展、调整和容错至关重要•	Hard to configure nodes and communications at this scale – automatic configuration, scaling, tuning, and fault tolerance essential
- 不清楚未来是否应该是大规模的Linux集群--下一步是什么？•	Unclear that the future should be large-scale Linux clusters – what comes next?

目前还不清楚问题域是否足够广泛，以至于我们需要不同类型的解决方案来解决不同的问题域，而我们最终只是要用三到四种不同的方式来做并发，你为你的领域挑选合适的方式。或者，是否有一个更普遍的解决方案等待我们去发现。It’s not clear if the problem domain is sufficiently broad that we need different types of solution for different problem domains, and we are just going to end up with three-or-four different ways of doing concurrency, and you pick the appropriate one for your domain. Or if there's a more general solution waiting to be discovered. 
我认为这些方法都不能扩展到GPU编程，在那里你要同时在10个或10万个内核上做同样的操作。而且，也许有一些隐含并行的方法。也许有一些数据并行阵列类型可以解决这个问题。在这门课程中，我们还没有真正谈论过GPU编程。And I think none of these approaches scale out to GPU programming, where you're doing the same operation on 10s, or 100s, of thousands of cores simultaneously. And, maybe, there are approaches with implicit parallelism. Maybe there are data parallel array types that can solve this problem. We haven't really spoken about GPU programming in this course. 
但是，同样，有一种不同类型的并发性，这似乎又需要不同的解决方案。目前还不清楚我们是否有好的方法来利用数据中心规模的计算。不清楚我们是否知道如何将系统编程扩展到大规模的数据中心，或大规模的分布式边缘计算数据中心。不清楚我们是否知道如何在数以百万计的核心上进行分布式计算。But, again, there’s a different type of concurrency, which again seems to need different solutions. It's not clear that we have good approaches to making use of data centre scale computing. It’s not clear that we know how to scale systems programming to massive data centres, or to large scale distributed edge compute data centres. It’s not clear that we know how to do distributed computations across millions of cores. 
我们确实知道如何建立大规模的Linux集群，但我们是通过假设一个可靠的、强大的、同质的系统来实现的。而且我们是通过从本质上将传统的系统管理自动化来做到这一点。我们有DevOps工具，如Ansible和Salt Stack，等等，但他们所做的都是shell脚本和编辑配置文件。这是同一类型的方法，只是自动进入一堆机器并编辑一些配置文件的ssh。We do know how to build large-scale Linux clusters, but we do this by assuming a reliable, robust, homogeneous system. And we do this by essentially automating traditional systems administration. We have DevOps tools, like Ansible, and Salt Stack, and the like, but all they’re doing is shell scripting and editing config files. It's the same type of approach, just automating the ssh into a bunch of machines and edit some config files. 
而必须有一个更好的方法来做到这一点，一个更好的方法来构建系统。我们知道如何使用这些数据中心，但我们知道如何使用它们来运行同一事物的数百万个实例。And there has to be a better way of doing that, a better way of structuring the system. And we know how to use these data centres, but we know how to use them to run millions of instances of the same thing. 
目前还不清楚我们是否擅长将代码分发到这些环境中。我们可以让它在有限的任务和同质化的数据中心中发挥作用。但是，随着计算变得更加普遍，随着它走出受控的数据中心，走向网络的边缘，我们需要在遍布世界各地的普遍的计算环境中运行数以百万计的任务，而且在配置上更加异质，更加多变，我们不清楚如何做到这一点。目前还不清楚我们是否有好的方法来做到这一点。It's not clear that we are good at distributing code into these environments. We can make it work for restricted tasks and in homogenous data centres. But as compute becomes more ubiquitous, as it moves out of the controlled data centre and towards the edges of the network, where we need to run millions of tasks over a ubiquitous computing environment that spreads around the world, and is much more heterogeneous, and much more variable, in its configuration, it's not clear we know how to do that that. It’s not clear we have good approaches to doing that.

# 分布式操作系统：Distributed Operating Systems

- Barrelfish - **一个用于多核系统的消息传递内核**•	Barrelfish – a message passing kernel for multicore systems
  - 每个内核运行一个单独的操作系统实例•	Each core runs a separate operating systems instance
  - 所有内核之间的通信都是通过消息传递进行的；没有 "主 "CPU作为中央协调器。•	All communication between cores is via message passing; no “main” CPU to act as central orchestrator
- 一**个测试想法的研究原型，而不是一个产品**•	A research prototype to test an idea, not a product
  - 类似Barrelfish的系统的边界在哪里？•	Where is the boundary for a Barrelfish-like system?
  - 分布式多内核和分布式网络计算机系统之间的区别？是否应该有这样的区别？•	Distinction between a distributed multi-kernel and a distributed system of networked computers? Should there be such a distinction?
  - 它与并发式编程、数据中心、分布式边缘计算等有什么关系？•	How does it relate to concurrent programming, data centres, distributed edge computing, etc?

> 目前还不清楚我们是否一定有正确的操作系统抽象用于数据中心，或用于泛在计算、边缘计算等环境。但我认为在这个领域有一些有趣的想法。It’s not clear that we necessarily have the right operating systems abstraction for data centres, or for ubiquitous computing, edge computing, environments. But I think are some interesting ideas in this space. 
> 例如，被称为BarrelFish的系统，幻灯片上的论文谈到了这一点，它的目标是为多核系统建立一个消息传递的操作系统内核。这里的想法是，如果你有一个带有多个处理器的系统，每个处理器核心都运行自己的操作系统实例，它们通过消息传递相互沟通。除了实现消息传递系统的优化外，没有使用共享内存，而且系统中没有主CPU作为中央协调者。The system known as BarrelFish, for example, and the paper on the slide talks about this, has a goal of building a message-passing operating system kernel for multi core systems. And the idea here, is if you have a system with multiple processors in it, each processor core runs its own instance of the operating system, and they communicate with each other via message passing. And there's no use of shared memory, other than to implement the message passing system as an optimisation, and there's no main CPU in a system to act as a central coordinator. 
> 因此，如果你有一个四核手机，例如，那么手机上的每个内核都会运行这个操作系统的一个单独的实例，通过消息传递进行内部通信。它把消息传递作为基本的基本要素。它将这一想法发挥到极致，将每个CPU分离成自己的消息传递核心。So if you have a quad core phone, for example, then each of the cores on the phone would be running a separate instance of this operating system, communicating internally via message passing. And it's taking message passing as the fundamental primitive. It's taking this idea to its extreme, to separate every CPU into its own message passing core. 
> 这在很大程度上是一个研究原型，用来测试一个想法。这不是你目前可以制造一个强大产品的东西。但我认为这是一个有趣的方法，而且我认为它导致了一个有趣的讨论，即什么是操作系统的边界？分布式多内核，即在一台机器上运行的多个操作系统内核与线程交换信息，与在网络上运行的一个操作系统之间的区别是什么？而且，是否应该，或者甚至需要有这样一个假设，这样一个区别？And it's very much a research prototype, to test an idea. It's not something that you could currently make a robust product out. But I think it's an interesting approach, and I think it leads to an interesting discussion about what's the boundary of an operating system? What's the distinction between a distributed multi-kernel, multiple operating system kernels running on a single machine exchanging messages with threads, and one running across the network? And should there, or does there even need to be, such an assumption, such a distinction? 
> 例如，我们可以在数据中心和边缘计算环境中运行这种类型的多内核吗？系统之间的边界在哪里？系统之间的边界又应该是什么？所以我认为在这个领域有一堆有趣的问题，还没有被充分探索。我希望我们能看到我们在未来构建操作系统的方式上有一些创新。Can we just run this type of multi kernel across a data centre, across an edge computing environment, for example? Where are the boundaries between systems? And what should be the boundary between systems? So I think there's a bunch of interesting questions in this space, that have not yet been fully explored. And I hope we’ll see some innovation in the way we build operating systems going forward.

# 未来系统编程语言：Future Systems Programming Languages

- **越来越多地看到研究思想被纳入主流编程语言中**•	Increasingly seeing research ideas incorporated into mainstream programming languages
  - Rust、Swift--抽象、资源管理、强大而富有表现力的类型系统、低层控制•	Rust, Swift – abstraction, resource management, strong and expressive type systems, low-level control
  - Erlang, Go - 并发、容错、通信•	Erlang, Go – concurrency, fault tolerance, communication

- **根据问题领域、可用工具和专业知识来选择语言**•	Choose the language based on problem domain, available tooling, and expertise
  - 为单一系统编程的良好解决方案•	Good solutions for programming single systems
  - 在扩展到大型集群方面，我们还有很多东西需要学习•	We still have a lot to learn about scaling to large clusters

> 而且我们越来越多地看到编程语言的创新。我们花了很长时间停留在C和C++上，作为一种局部的最大值。但我们开始看到更多的研究想法进入主流语言。And we're increasingly seeing innovation in programming languages. We spent a long time stuck with C and C++, as a sort of local maxima. But we're starting to see more research ideas coming into mainstream languages. 
> 在Rust中，正如我们在本课程的大部分内容中所谈到的，以及在Swift中，我们开始看到抽象化、资源管理和更具表现力的类型系统，再加上低级别的控制，提供了更强大、更安全、低级别的编程环境。像Erlang和Go这样的语言，正在探索并发和容错以及通信空间。还有其他一些正在涌现的语言，人们开始探索事物的运作方式。And in Rust, as we've spoken about in most of this course, and in Swift, we're beginning to see abstraction, and resource management, and more expressive type systems, coupled with low-level control, giving more powerful, safer, low-level programming environments. And languages like Erlang and Go, are exploring the concurrency and fault tolerance and communication space. And there are others which are bubbling up, and people are starting to explore how things can work. 
> 我认为我们早就应该看到底层语言的这种发展，我们开始有更多的表达方式来描述我们的程序应该做什么。我认为，我们应该明确地根据问题领域、根据工具、根据我们的专长来为我们的系统选择语言。而且，并不清楚单一的语言是否适合整个空间。例如，Rust在构建操作系统方面效果特别好。但是像Erlang或Go这样的语言可能更适合在数据中心运行的大规模网络应用程序开发，比如说。And I think it’s long overdue that we're seeing this development in the underlying languages, and we're beginning to have more expressive ways of describing what our program should do. We should clearly, I think, going forward choose the language for our systems based on the problem domain, based on the tooling based, based on our expertise. And it's not clear that single language fits the entire space. Rust, for example, works extraordinarily well for building operating systems. But languages like Erlang or Go are possibly better suited to large-scale web application development running in a data centre, for example. 
> 我们已经有很好的解决方案来为单一系统编程。我认为Rust对于单一系统内的编程是很好的。我们开始学习如何扩展到大型集群，到无处不在的计算，到边缘计算环境，我认为Erlang和Go在这个领域开始有一些好的想法，但我们还没有一种语言来填补所有这些空间。We've got good solutions for programming single systems. I think Rust is great for programming within a single system. We're starting to learn about how to scale to large clusters, to ubiquitous computing, to edge computing environments, and I think Erlang and Go are starting to have some good ideas in this space, but we don't have a language that fills all of these spaces yet.

# 扩展系统编程:Scaling Systems Programming

- **在对简单的单核系统进行编程时，Unix和C是有意义的**•	Unix and C made sense when programming simple single-core systems
  - 并构成了大多数现有系统的基础 - 但下一步是什么？•	And form the basis of most existing systems – but what comes next?
- **现代大规模的并发和分布式应用需要新的方法**。•	Modern massively concurrent and distributed applications need new approaches:
  - **低级别的性能仍然至关重要，但也需要类型系统和抽象来隐藏复杂性**--没有人能够在自己的头脑中掌握所有的细节•	Low-level performance still crucial, but also need type systems and abstractions to hide the complexity – no-one can hold all the details in their head
  - 工具是必不可少的--检查不变量、记录假设、确保一致性•	Tooling is essential – to check invariants, document assumptions, ensure consistency
  - 部署、配置和管理必须变得越来越自主--DevOps、基础设施即代码、自我管理•	Deployment, configuration, and management must become increasingly autonomic – DevOps, infrastructure as code, self-managing

> 我们使用的系统，Unix和C，在我们为简单的单核系统编程时是有意义的。它们构成了我们今天使用的几乎所有计算系统的基础。The systems we use, Unix and C, made sense when we were programming simple single core systems. And they form on the basis of pretty-much every computing system we use today. 
> 但我们在左边看到的系统，即程序员在PDP 10上使用电传打字机，并不是我们在右边看到的数据中心环境，很多现代软件都生活在那里。But the system we see on the left, programmers at a PDP 10 with a teletype, is not the data centre environment we see on the right, where a lot of modern software lives. 
> 我确实认为我们需要新的方法来构建大规模并发和大规模分布的软件。像Rust这样的语言解决了部分问题，给我们提供了低级别的性能，给我们提供了一个有效的类型系统，给我们提供了在单机上运行的低级别的代码的抽象。这一点很重要，因为没有人能够把所有这些细节都记在脑子里，我们需要这种抽象，我们需要编译器的帮助来正确编写这种代码。And I do think we need new approaches to build software that is massively concurrent and massively distributed. Languages like Rust solve part of this problem space, and give us low-level performance, they give us an effective type system, they give us abstractions for writing the low-level code running on a single machine. And that's essential, because no-one can hold all those details in their head, and we need this abstraction, we need the compiler help to write this sort of code correctly. 
> 我们需要工具来检查我们的不变量，记录我们的假设，确保一致性。但是，一旦我们开始扩展到数据中心，以及未来无处不在的计算、边缘计算的世界，似乎我们有可能得到，我们是否有正确的DevOps、基础设施即代码、自我管理、自主管理系统，以及在如此大规模下编程的正确方法，就不清楚了。We need the tooling to check our invariants, document our assumptions, ensure consistency. But once we start scaling out to data centres, and to the sort of future ubiquitous computing, edge computing, world it seems we are likely to get, it's not clear that we have the right DevOps, infrastructure as code, self-managing, autonomic management systems, and the right approaches for programming at such large scales.