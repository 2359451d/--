# Content

* [Content](#content)
* [Technology Trends](#technology-trends)
* [Back to the early 2000s….](#back-to-the-early-2000s)
* [为什么需要多核处理器](#为什么需要多核处理器)
* [Recent chip multiprocessors (2018)](#recent-chip-multiprocessors-2018)
* [2020](#2020)
* [Amdahl’s law & efficiency](#amdahls-law--efficiency)
* [多核处理器分类：Multiprocessor Types](#多核处理器分类multiprocessor-types)
* [核内通信模型：Inter-processor communication models](#核内通信模型inter-processor-communication-models)
* [共享内存-优缺点：Shared Memory](#共享内存-优缺点shared-memory)
* [共享内存多核处理器：Shared Memory Multiprocessors](#共享内存多核处理器shared-memory-multiprocessors)
* [共享内存硬件支持（需要解决的问题:Hardware support for shared memory](#共享内存硬件支持需要解决的问题hardware-support-for-shared-memory)
* [实现内存一致性：Maintaining cache coherence](#实现内存一致性maintaining-cache-coherence)
* [缓存一致性协议：Cache coherence protocols](#缓存一致性协议cache-coherence-protocols)
* [例子-MSI协议：MSI Protocol](#例子-msi协议msi-protocol)
* [硬件实现缓存一致性协议：Possible implementations of protocols in hardware](#硬件实现缓存一致性协议possible-implementations-of-protocols-in-hardware)
* [内存一致性: Memory consistency](#内存一致性-memory-consistency)
* [=============](#)
* [硬件并行性：Parallelism in Hardware](#硬件并行性parallelism-in-hardware)
* [软件并行性：Parallelism in Software](#软件并行性parallelism-in-software)
* [费林分类法:Flynn’s taxonomy of computers](#费林分类法flynns-taxonomy-of-computers)
* [=============](#-1)

# Technology Trends

![](/static/2022-04-30-23-23-47.png)
![](/static/2022-04-30-23-34-29.png)

# Back to the early 2000s….

![](/static/2022-04-30-23-24-59.png)

# 为什么需要多核处理器

![](/static/2022-04-30-23-35-21.png)
![](/static/2022-04-30-23-33-09.png)

# Recent chip multiprocessors (2018)

![](/static/2022-04-30-23-36-00.png)

# 2020

![](/static/2022-04-30-23-36-16.png)

# Amdahl’s law & efficiency

![](/static/2022-04-30-23-37-37.png)

- 软件必须是并行的（记住Amdahl’s law）。•	Software must be parallel (remember Amdahl’s law)
- 程序员需要编写并行程序–	Programmers need to write parallel programs
- 旧系统代码需要被并行化–	Legacy code need to be parallelised

---

![](/static/2022-04-30-23-39-21.png)

* 60%未被使用

---

![](/static/2022-04-30-23-43-38.png)
![](/static/2022-04-30-23-44-27.png)

* benchmark效率

# 多核处理器分类：Multiprocessor Types

- **紧密耦合（共享内存）：多核/多线程处理器**•	Tightly coupled (shared memory): multicore/multithreaded processors
  - **不同处理器中的进程**使用**相同的虚拟地址空间**–	Processes in different processors use the same virtual address space
  - 任何**处理器都可以直接访问另一个处理器的内存**–	Any processor can directly access memory in another processor
  - **通信**是通过**共享内存变量**完成的–	Communication is done through shared memory variables
  - **同步**是明确的，有锁和关键部分–	Synchronisation is explicit with locks and critical sections
  - **更容易编程**：类似于单核处理器（多任务单核处理器）。–	Easier to program: similar to uniprocessors (multitasking uniprocessor)

- **松散耦合（分布式内存）。多计算机网络**•	Loosely coupled (distributed memory): Multicomputer network
  - **不同处理器中的进程**使用**不同的虚拟地址空间**–	Processes in different processors use different virtual address spaces
  - 每个处理器**只能直接访问自己的内存**–	Each processor can only directly access its own memory
  - **通信**是通过**消息传递**完成的–	Communication is done through message passing
  - **同步**是隐含在消息中的–	Synchronisation is implicit in the messages
  - **更难编程**：通过显式调用（发送、接收）、库（如MPI）。–	Harder to program: via explicit calls (send, receive), libraries (e.g. MPI)

# 核内通信模型：Inter-processor communication models

![](/static/2022-05-01-06-30-45.png)

# 共享内存-优缺点：Shared Memory

- 优点
  - 更容易编程（首先是正确性，其次是性能）–	Easier to program (correctness first, performance later)
  - 操作系统只需要（相对）小的扩展–	The Operating System only requires (relatively) minor extensions

- 缺点
  - 同步性复杂（锁，关键部分）。–	Synchronisation complex (locks, critical sections)
  - 隐含着共享变量的通信（更难优化）。–	Communication implicit with shared variables (harder to optimise)
  - 必须保证一致性–	Must guarantee coherence

# 共享内存多核处理器：Shared Memory Multiprocessors

![](/static/2022-05-01-06-41-19.png)

- 通过**将全部处理器（或核心）连接在一起获得**•	Obtained by connecting full processors (or cores) together
- **整个系统有一个单一的操作系统**•	Have a single OS for the whole system
- 适用于**线程可以遵循不同代码**的**并行程序**•	Suitable for parallel programs where threads can follow different code
- 两个常见的组织•	Two common organisations [根据内存组织方式的不同，共享内存多核处理器系统具有如下几个细分]
  - 物理上集中的内存统一内存访问（UMA）–	Physically centralised memory  uniform memory access (UMA)
    - UMA系统中所有的处理器共享统一的物理内存（集中式共享内存Centralized shared memory），所有处理器访问该物理内存的时间基本相同，每个处理器可以有它自己的私有缓存(cache)。所有的外设对内存的访问能力也完全平等相同。
    - 当所有的处理器对外设IO也拥有相同的访问能力时，这种共享的多核处理器就叫做对称处理器SMP(symmetric multiprocessor)。如果系统中只有一个或者少数几个拥有对外设的访问能力，则可以称为非对称处理器(asymmetric multiprocessor)。
  - 物理分布式内存非统一内存访问（NUMA）。–	Physically distributed memory  non-uniform memory access (NUMA)
    - 如果在多核处理器中，每个处理器访问内存所需要的时间不同，则我们称为NUMA系统，在NUMA系统中，内存不再集中在一起供所有处理器平等访问，不同的处理器都有其自身的本地内存（分布式共享内存系统DSM : Distributed Shared Memory）。但这些处理器上的内存共享同一个全局地址空间，其它处理器也都能访问，处理器访问本地内存需要的时间少于访问其他处理器的远端内存需要的时间。

# 共享内存硬件支持（需要解决的问题:Hardware support for shared memory

![](/static/2022-05-01-21-57-08.png)

- **缓存一致性**•	Cache Coherence
  - 缓存+多处理器-> 陈旧值(无效值？？没来得及更新的？) –	Caches + multiprocessors  stale values
  - 系统必须表现得像不存在缓存一样–	Systems must behave as if caches are not present
  - **通过写传播、写序列化来保证**–	Ensured via write propagation, write serialisation
    - 某一点缓存值改变了，立即改变其他缓存中相关的值
    - 每次只允许一个处理器写入某值

- **内存一致性**•	Memory Consistency
  - 什么时候（按什么顺序）写入的值应该让别人看到？–	When (in what order) should writes be made visible to others?
  - 读取应该返回什么值？–	What value should a read return?

- **原始同步指令**•	Primitive synchronisation instructions
  - Memory Fence同步机制（串行化加载与存储操作）：按要求进行内存排序–	Memory fences: memory ordering on demand
  - 原子操作（例如：读-修改-写）：支持锁（保护关键部分），事务性内存等。–	Atomic operations (e.g. Read-Modify-Write): support for locks (to protect critical sections), transactional memory, etc

# 实现内存一致性：Maintaining cache coherence

- 选项1：如果共享数据可以被修改，则不允许其被缓存（如现代GPU）。•	Option 1: do not allow shared data to be cached if it can be modified (e.g. modern GPUs)
  - 难以编程–	Difficult to program
  - 性能差–	Poor performance

- 选项2：系统软件（如编译器）确保数据保持一致•	Option 2: system software (e.g. compiler) ensures that data is kept coherent
  - 性能差–	Poor performance
  - 编译器设计变复杂

- **选项3：硬件强制执行缓存一致性**•	Option 3: hardware enforces cache coherence
  - **使用协议**–	Using protocols
  - 对程序员来说是透明的–	Transparent to the programmer

# 缓存一致性协议：Cache coherence protocols

- 想法•	Idea
  - **追踪哪些处理器拥有哪些数据的副本**–	Keep track of what processors have copies of what data
  - **强制规定在任何时候每个数据都有一个单一的值存在**–	Enforce that at any given time a single value of every data exists
  - **清除具有旧值的数据副本**->使协议无效•	Getting rid of copies of data with old values  invalidate protocols
    - n Write-invalidate: 一个处理器需要更新某个数据时，其先往总线上发送一条Invalidate请求，系统中其它处理器上的cache控制器监控到这条消息，把自己的状态设置为invalid。如果这些invalidated cache block在下一次需要访问该数据时，则会重新进行一个总线的read miss操作。
  - **更新每个人的数据副本**->更新协议•	Updating everyone’s copy of the data  update protocols
    - Write-update（或者叫write broadcast）: 当一个处理器更新某个数据时，其不往总线上发送invalidate消息，而是往总线上发送一条update A消息，直接告知该变量的最新值，其它处理器上的cache侦听到这个操作后更新本地的变量为这个广播的最新值。其它处理器在下一次使用该变量时，直接cache 命中，可以直接使用最新数据，而不产生read miss操作。
  - <font color="deeppink">在其他CPU对共享变量有写操作之后立刻更新的做法，被称为Write Update，这种方法逻辑简单，但效率不高，更好的做法是在其他CPU(P2)对cache line有写操作之后，(P1)仅仅是把自己的cache中对应的cache line标记为(I)nvalid的状态，这种做法被称为Write Invalidate</font>
    - 虽然在Invalid的状态时，各个CPU中对应内存同一位置的cache line中的数据并不相同，但没有使用到这个不相同的数据，就对程序的运行没有任何的影响，这并没有违反cache的一致性原则，cache一致性保证的是在CPU访问到某个cache line时，其中的数据是和内存的对应位置，以及和其他CPU对应的cache line是一致的。

- 在实践中•	In practice
  - **保证旧值无效或更新(写传播**)–	Guarantee that old values are invalidated or updated (write propagation)
  - **注意一个给定的负载可能不会返回最新的值，如果它还没有被传播，所以【需要同步】进行**•	Note that a given load may not return the most recent value, if it hasn’t been propagated yet, so synchronisation needed
  - **保证在任何时候只有一个处理器被允许修改某个数据（写序列化**）。–	Guarantee that only a single processor is allowed to modify a certain data at any given time (write serialisation)
  - **系统必须在没有缓存的情况下工作**!–	The system must work as if no caches were present!

- **为缓存块添加状态位，以跟踪块的状态**•	Add state bits to cache blocks to track the state of the block
  - 最常见的是：修改的、独占的、共享的、无效的–	Most common: Modified, Exclusive, Shared, Invalid
  - 协议通常以支持的状态命名（如MSI、MESI等）。–	Protocols usually named after the states supported (e.g. MSI, MESI, etc)

- **缓存块在本地处理器或远程处理器的加载/存储操作后，在不同的状态之间转换**。•	Cache blocks transition between states after load/store operations from the local processor or remote processors

- **状态转换必须保证不变性**•	State transitions must guarantee the invariant
  - 不能同时修改两个缓冲区副本–	No two cache copies can be simultaneously modified
  - SWMR：单写入多读取–	SWMR: Single writer multiple readers

# 例子-MSI协议：MSI Protocol

![](/static/2022-05-01-22-11-04.png)
![](/static/2022-05-01-22-20-11.png)
![](/static/2022-05-01-22-24-13.png)

> MSI代表了 cache block的三种不同状态 I,S,M，分别是invalid, Shared和Modified。任何时刻Cache block必处于这三种状态之中的一种状态。

状态•	States

- **已修改 (M): 区块只在这个缓存中被缓存，并且已经被修改**。–	Modified (M): block is cached only in this cache and has been modified
  - 该状态只能存在一处cache中，该状态下cache block，CPU能够直接读写而不需要知会其它CPU上的Cache.该状态的cache block负责为其它cache节点提供最新的数据，同时也负责把最新数据写回到memory中
- **共享（S）：区块被缓存在这个缓存中，也可能被缓存在其他缓存中（如果一个缓存修改了该区块，需要采取相应的措施**）–	Shared (S): block is cached in this cache and possibly in other caches (if a cache modifies the block, actions need to be taken)
  - 该cache block的内容没有被修改并且处于只读状态，该cache block存在于至少一处cache中和memory中，该状态下CPU能够直接读取该cache block数据而无需与其它cache进行通信，总线上没有操作。
- **无效（I）：区块没有被缓存**。–	Invalid (I): block is not cached
  - 该cache block在当前cache中不存在或者被总线上的invalidate操作设置为无效，**处于该状态的cache block需要从Memory或者其它cache中获取，在访问该cache block时，cache控制器需要往总线上产生一个read miss或者write miss操作**。
  - 无论什么时刻，在某个内存位置和它对应的所有cache line中，至多有一个CPU的cache line可以处于modified状态，代表着最新的数据。其他CPU中cache line中的数据过时没关系，把状态标记为失效就可以了

---

![](/static/2022-05-01-22-31-57.png)
![](/static/2022-05-01-22-32-43.png)
![](/static/2022-05-01-22-33-51.png)
![](/static/2022-05-01-22-34-45.png)

# 硬件实现缓存一致性协议：Possible implementations of protocols in hardware

- **窥探：所有的缓存控制器监控所有其他缓存的活动，并维护其块的状态**•	Snooping: all cache controllers monitor all other caches’ activities and maintain the state of their blocks
  - **实现起来相对简单**–	Relatively simple to implement
  - **依赖**于一个广播媒介（通常是**总线**）。–	Relies on a broadcast medium (typically a bus)
  - **超过16个处理器时，扩展性很差（产生大量的流量**）–	Scales poorly beyond 16 processors (a lot of traffic generated)

- **目录：一个中央控制器处理所有的一致性活动，并告诉缓存要做什么转换** •	Directory: a central controller handles all coherence activities and tells the caches what transitions to make
  - 比窥探式一致性的**复杂性更高**–	Higher complexity than snoopy coherence
  - **可扩展性高（存在512个处理器的系统**）–	High scalability (systems with 512 processors in existence)

# 内存一致性: Memory consistency

- 缓存一致性•	Cache coherence
  - 保证最终的写入传播–	Guarantees eventual write propagation
  - 保证所有写到同一地点的单一顺序–	Guarantees a single order of all writes to same location
  - **但不保证写的传播时间或写的原子性**–	But does not guarantee when writes propagate or write atomicity

![](/static/2022-05-01-22-45-45.png)
![](/static/2022-05-01-22-48-36.png)

> 根据内存一致性模型，多核多线程的程序无约束执行的结果是不可确定的，因此为限制指令的执行顺序，便引入了特殊的存储器屏障指令（memory fence）。
> 
> FENCE指令用于屏障“数据”存储器访问的执行顺序。如果在程序中添加一条FENCE指令，则该**FENCE能够保证“在FENCE前所有指令的数据访存结果”必须比“在FENCE后所有指令数据访存结果”先被观测到，即FENCE前的访存指令必须比FENCE后的访存指令先执行**。

- 内存一致性•	Memory consistency
  - **规定了加载和存储到不同内存位置的顺序**–	Specifies the ordering of loads and stores to different memory locations
  - 在所谓的**内存一致性模型**中定义–	Defined in so called Memory Consistency Models
  - 硬件、编译器和程序员之间的 "契约"–	A “contract” between the hardware, the compiler, and the programmer
    - 硬件和编译器不会违反指定的顺序•	Hardware and compiler will not violate the ordering specified
    - 程序员不会假设一个比模型更严格的顺序•	Programmer will not assume a stricter order than that of the model
  - 然而，硬件/编译器提供了 "安全网 "机制，因此**用户可以执行比模型提供的更严格的顺序**。–	However, hardware/compiler provide “safety net” mechanisms, so the user can enforce a stricter order than that provided by the model

# =============

# 硬件并行性：Parallelism in Hardware

- 单处理器•	Uniprocessor
  - 指令部分重叠。Pipelining –	Instructions partially overlapped: Pipelining
  - 执行重叠（执行单元）。超标量、VLIW、EPIC等–	Execution overlapped (execution units): Superscalar, VLIW, EPIC, etc

- SIMD指令，矢量处理器，GPU•	SIMD instructions, Vector processors, GPUs

- 专门的处理器/加速器。DSP、NPU、TPU、FPGA等•	Specialised processors/accelerators: DSPs, NPUs, TPUs, FPGAs, etc

- 多处理器•	Multiprocessors
  - 共享内存多核处理器–	Shared-memory multiprocessors
  - 分布式内存多处理器–	Distributed-memory multiprocessors
  - 芯片多处理器（多核或CMP）。–	Chip-multiprocessors (multicores or CMPs)

- 多计算机，又称集群•	Multicomputers a.k.a. clusters

# 软件并行性：Parallelism in Software

- **指令级并行**（ILP）•	Instruction-level parallelism (ILP)
  - 来自同一数据流的多条指令可以同时执行–	Multiple instructions from the same stream can be executed concurrently
  - 由硬件（超标量）或由编译器（VLIW）生成/管理–	Generated/managed by hardware (superscalar) or by compiler (VLIW)
  - 受数据和控制依赖性的限制–	Limited by data and control dependences

- **线程级或任务级并行**(TLP)•	Thread-level or task-level parallelism (TLP)
  - 来自同一应用程序的多个线程或指令序列同时执行–	Multiple threads or instruction sequences from the same app executed concurrently
  - 由编译器/用户产生，由编译器和硬件管理–	Generated by compiler/user and managed by compiler and hardware
  - 受到通信/同步化开销和算法特性的限制–	Limited by communication/syncronisation overheads and algorithm characteristics

- **数据级并行**(DLP)•	Data-level parallelism (DLP)
  - 来自单一数据流的指令在多个数据上同时操作–	Instructions from a single stream operate concurrently on several data
  - 受非规则数据操作模式和内存带宽的限制–	Limited by non-regular data manipulation patterns and memory bandwidth

- **事务级并行**•	Transaction-level parallelism
  - 来自不同事务的多个线程/进程可以并发执行–	Multiple threads/processes from different transactions can be executed concurrently
  - 受到并发开销的限制–	Limited by concurrency overheads

# 费林分类法:Flynn’s taxonomy of computers

> 费林分类法（Flynn's Taxonomy），是一种高效能计算机的分类方式。1972年费林（Michael J. Flynn）根据资讯流（information stream）可分成指令（Instruction）和资料（Data）两种。据此又可分成四种计算机类型：SISD, SIMD, MISD, and MIMD.
>
> 注意：费林分类是划分计算机的，不是划分CPU的。

- **SISD（单指令单数据）。相同的指令在相同的数据上** •	SISD (Single instruction single data): Same instruction on same data
  - 标准的单核处理器–	The standard uniprocessor
  - 单处理指令单数据，一条指令处理一个数据，所有的冯诺依曼体系结构的“单处理器计算机”都是这类，基本上07年前的PC都是这类。

- **SIMD（单指令，多数据）。不同数据上的相同指令** •	SIMD (Single instruction, multiple data): Same instruction on different data
  - 阵列/矢量处理器，SIMD指令，GPU–	Array/vector processors, SIMD instructions, GPUs
  - 单指令多数据，一个指令广播到多个处理器上，但每个处理器都有自己的数据。
  - 这种系统主要应用在“专有应用”系统上，例如数字信号处理、向量运算处理，其并行功能一般都是由编译器完成的。

- **MISD（多指令，单数据）。同一数据上的不同指令** •	MISD (Multiple instruction, single data): Different instructions on the same data
  - 容错计算机、收缩阵列（如TPU）、内存计算–	Fault-tolerant computers, systolic arrays (e.g. TPUs), in-memory computing
  - 多指令单数据，没有系统按照这个结构设计，这种类别仅仅是为了分类完整而提出来的。

- **MIMD（多指令，多数据）。不同数据上的不同指令** •	MIMD (Multiple instruction, multiple data): Different instructions on different data
  - "常见 "的多处理器，多线程处理器–	The “common” multiprocessor, multithreaded processor
  - **每个处理器使用自己的数据并执行自己的程序**–	Each processor uses it own data and executes its own program
  - **最灵活的方法**–	Most flexible approach
  - 通过把 "现成的 "处理器放在一起，更容易/更便宜地建立。–	Easier/cheaper to build by putting together “off-the-shelf ” processors
  - 多指令多数据，每个处理单元有独立指令和数据。
  - 这是并行处理系统中最常见的结构，现代流行的并行处理结构都可以划入这一类。
                 
# =============