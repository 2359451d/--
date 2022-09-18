# Content

* [Content](#content)
* [指令周期、机器周期和时钟周期](#指令周期机器周期和时钟周期)
* [CPU性能方程-改进执行时间：Execution time](#cpu性能方程-改进执行时间execution-time)
* [改进例子-流水线&超前进加法器](#改进例子-流水线超前进加法器)
* [方法-减少执行时间:Approach for reducing execution time](#方法-减少执行时间approach-for-reducing-execution-time)
* [硬件并行性-加速执行例子：Parallelism in computer systems(hardware)](#硬件并行性-加速执行例子parallelism-in-computer-systemshardware)
* [并行性重要性](#并行性重要性)
* [==========](#)
* [电路重定时(加快时钟频率）：Retiming](#电路重定时加快时钟频率retiming)
* [重定时缩减关键路径例子](#重定时缩减关键路径例子)
* [流水线：Pipelining](#流水线pipelining)
* [流水线化RRR指令流例子：Pipelining a stream of RRR Instruction](#流水线化rrr指令流例子pipelining-a-stream-of-rrr-instruction)
* [其他例子](#其他例子)
* [有无流水线add, load指令](#有无流水线add-load指令)
* [五级流水线-完整指令集流水线：Pipeline for full instruction set](#五级流水线-完整指令集流水线pipeline-for-full-instruction-set)
* [流水线冒险：Pipeline Hazards](#流水线冒险pipeline-hazards)
* [结构冒险: Structural hazards](#结构冒险-structural-hazards)
* [数据冒险：Data Hazards](#数据冒险data-hazards)
* [数据冒险分类（3情况）：classification of data hazards](#数据冒险分类3情况classification-of-data-hazards)
* [检测数据冒险：Detecting data hazards](#检测数据冒险detecting-data-hazards)
* [避免数据冒险-转发vs暂停：Handling data hazards](#避免数据冒险-转发vs暂停handling-data-hazards)
* [数据冒险-RAW转发例子:Example of bypassing/forwarding](#数据冒险-raw转发例子example-of-bypassingforwarding)
* [实现转发：Implementing bypassing](#实现转发implementing-bypassing)
* [控制冒险：Control Hazards](#控制冒险control-hazards)
* [流水线效率：Performance of pipelining](#流水线效率performance-of-pipelining)
* [流水线&ISA设计-提高性能：Impact of instruction set on pipelining](#流水线isa设计-提高性能impact-of-instruction-set-on-pipelining)
* [流水线效率局限](#流水线效率局限)
* [==========](#-1)
* [超标量：superscalar](#超标量superscalar)
* [执行单元：Functional units](#执行单元functional-units)
* [超标量控制-scoreboard：Superscalar control](#超标量控制-scoreboardsuperscalar-control)
* [IBM system/360 Model 91](#ibm-system360-model-91)
* [IBM360 Model 91 数据通路](#ibm360-model-91-数据通路)
* [源汇：Source & sink](#源汇source--sink)
* [数据通路-组件单元：Basic datapath :units](#数据通路-组件单元basic-datapath-units)
* [数据通路-寄存器：Registers](#数据通路-寄存器registers)
* [Tomasulo算法：Tomasulo's Algorithm](#tomasulo算法tomasulos-algorithm)
* [Busy bit(繁忙位-保留指令流基本优先级Preservation of precedence](#busy-bit繁忙位-保留指令流基本优先级preservation-of-precedence)
* [忙位-指令发射：Issuing an instruction](#忙位-指令发射issuing-an-instruction)
* [忙位-指令执行：executing the instruction](#忙位-指令执行executing-the-instruction)
* [忙位策略局限：Limitations of busy bits scheme](#忙位策略局限limitations-of-busy-bits-scheme)
* [无CDB（保留站）例子](#无cdb保留站例子)
* [====================](#-2)
* [改进-保留站：Reservation stations](#改进-保留站reservation-stations)
* [保留站例子2](#保留站例子2)
* [CDB公共数据总线：Common Data Bus (CDB), Difficulties with busy bits](#cdb公共数据总线common-data-bus-cdb-difficulties-with-busy-bits)
* [CDB结构：Structure of CDB](#cdb结构structure-of-cdb)
* [CDB-指令发射：Issuing an instruction](#cdb-指令发射issuing-an-instruction)
* [CDB-功能单元完成: when a functional unit finishes](#cdb-功能单元完成-when-a-functional-unit-finishes)
* [CDB例子](#cdb例子)
* [CDB文字例子](#cdb文字例子)
* [使用CDB意义->Registers are no longer a bottleneck](#使用cdb意义-registers-are-no-longer-a-bottleneck)
* [CDB vs busy bit](#cdb-vs-busy-bit)
* [CDB vs scoreboard](#cdb-vs-scoreboard)
* [==========](#-3)
* [历史影响和当前意义：Historical impact and current significance](#历史影响和当前意义historical-impact-and-current-significance)
* [数据流：dataflow-beyond the CDB](#数据流dataflow-beyond-the-cdb)
* [执行单元调度：Scheduling functional units](#执行单元调度scheduling-functional-units)
* [==========](#-4)

# 指令周期、机器周期和时钟周期

![](/static/2022-04-27-23-11-52.png)

# CPU性能方程-改进执行时间：Execution time

目标：**减少程序的总CPU执行时间**•	Goal: reduce the total CPU execution time of programs

- **I = 指令的数量（ISA，编译器技术**） –	I = number of instructions (ISA, compiler technology)
- **CPI = 每条指令的平均周期数(组织，ISA**) –	CPI = average number of cycles per instruction (organisation, ISA)
- **T = 时钟周期时间(硬件技术，组织**) –	T = clock cycle time (hardware technology, organisation)
- CPU性能方程。	**`CPU_time = I × CPI × T`** •	CPU performance equation:	**`CPU_time = I × CPI × T`**
  - **要减少CPU_时间，就必须减少这个乘积**•	To reduce CPU_time it's necessary to reduce this product
    - 不仅仅是乘积的一个组成部分!–	Not just one component of the product!
- <font color="red">有些 "优化 "在减少一个因素的同时增加另一个因素</font> •	Some “optimisations” reduce one factor while increasing another
  - 而且它们实际上会使执行时间恶化 –	And they actually worsen the execution time

---

# 改进例子-流水线&超前进加法器

(d) 考虑一个32位的处理器，其数据通路有4个阶段(IF, RF, OP, WR)，并假设。1) 一个**全加器的延迟是2ns**; 2) **关键路径的延迟是由ALU电路决定的，它在OP阶段**。使用性能方程(CPU时间=I x CPI x T)计算以下情况下单条指令的平均执行时间：

* i)一个顺序处理器和用行波进位加法器实现的ALU；i) a sequential processor and the ALU implemented with a ripple carry adder;
* ii)一个顺序处理器和用**超前进加法**器实现的ALU；ii) a sequential processor and the ALU implemented with a fast adder;
* iii)一个流水线处理器(假设完美流水线)和用行波进位加法器实现的ALU； iii) a pipelined processor (assume perfect pipelining) and the ALU implemented with a ripple carry adder; 
* iv)一个流水线处理器(假设完美流水线)和用快速加法器实现的ALU。iv) a pipelined processor (assume a perfect pipelining) and the ALU implemented with a fast adder. What is the fastest case? Explain why.

什么是最快的情况？解释一下原因。

* 时钟周期T是由关键路径的延迟给出的，**使用行波进位加法器是32 x 2ns = 64ns，使用快速加法器是log2 32 x 2ns = 10ns**。
* **顺序处理器的CPI是4(4阶段数据通路)，而【完美】流水线处理器的CPI是1**。
* 那么，四种情况下的CPU时间是： 
  * i) 1 x 4 x 64ns = 256ns; 
  * ii) 1 x 4 x 10ns = 40ns; 
  * iii) 1 x 1 x 64ns= 64ns; 
  * iv) 1 x 1 x 10ns = 10ns。

最快的情况显然是iv），因为它使用了最好的电路设计技术，即完美的流水线和快速加法器。

# 方法-减少执行时间:Approach for reducing execution time

> 关键路径通常是**指同步逻辑电路中，组合逻辑时延最大的路径**（这里我认为还需要加上布线的延迟），也就是说关键路径是对设计性能起决定性影响的时序路径。
>
> 对关键路径进行时序优化，可以直接提高设计性能。对同步逻辑来说，常用的时序优化方法包括Pipeline、Retiming、逻辑复制、加法/乘法树、关键信号后移、消除优先级等解决。

- **减少I（指令的数量**）。•	Reduce I (the number of instructions)
  - 在每条指令中做更多的工作–	Do more work in each instruction
  - 但这可能会使CPI和T都恶化–	But this can worsen either/both CPI and T
    - 每条指令做更多事，平均要的时钟周期CPI和时钟周期T就更多
- **减少CPI（每条指令的平均时钟周期**）。•	Reduce CPI (average clock cycles per instruction)
  - **同时进行几条指令的工作：指令级并行性**–	Work on several instructions simultaneously: instruction level parallelism
    - CPU流水线pipeline
- **减少T(时钟周期**)•	Reduce T (clock cycle)
  - 使用**更快的晶体管**（受益于固态物理学的发展）。–	Use faster transistors (benefit from advances in solid state physics)
  - <font color="red">识别关键路径并减少它</font>–	Identify the critical path and reduce it

:orange: **最有效的方法涉及并行性**•	The most effective approaches involve parallelism

# 硬件并行性-加速执行例子：Parallelism in computer systems(hardware)

- **电路层面：使用硬件算法来降低关键路径**•	Circuit level: use hardware algorithms to lower critical path
  - **重定时，快速加法器**–	Retiming, fast adder
- **指令层面：多个指令同时执行（单处理器**）。•	Instruction level: multiple instructions executed concurrently (uniprocessor)
  - **指令部分重叠：流水线**–	Instructions partially overlapped: pipelining
  - **执行重叠（多个执行单元）：超标量**、VLIW、EPIC –	Execution overlapped (multiple execution units): superscalar, VLIW, EPIC
- **处理器级：使用多个处理器/核心**•	Processor level: use multiple processors/cores
  - 共享/分布式内存多处理器–	Shared/distributed memory multiprocessors
  - 芯片多处理器（多核）、加速器（如GPU、TPU)–	Chip-multiprocessors (multi-cores), accelerators (e.g. GPUs, TPUs)
- **计算机水平：使用多台计算机**•	Computer Level: use multiple computers
  - 分布式系统，集群–	Distributed systems, clusters

---

# 并行性重要性

其他例子

**多核处理器**，它通过同时运行几个不同的线程来加快整体性能，但多核并不能加快单个指令序列的速度。例子包括。**pipelining**在处于控制算法不同阶段的相邻指令之间引入了并行性；**超标量**并行使用多个功能单元来并行执行指令所要求的长计算；**推测性并行**在条件性分支中遵循两种选择，并且只在分支解决后提交到需要的路径。**并行扫描**允许关联函数在对数时间而不是线性时间内被扫描，这有很多应用，包括**快速加法器和快速ALU**；关联搜索使用嵌入在小型存储器中的并行电路，以几乎O(1)时间而不是O(n)时间进行搜索；关联搜索的一个应用是在TLB电路中。还有更多的例子（使用并行解码树在一个寄存器文件上提供两个输出端口，为每个算术指令节省一个完整的时钟周期......）multicore processor, which speeds up overall performance by running several different threads simultaneously, but the multiple cores do not speed up a single sequence of instructions. Examples include: pipelining introduces parallelism between adjacent instructions which are in different phases of their control algorithms; superscalar parallelism uses multiple functional units to perform long calculations requested by instructions in parallel; speculative parallelism follows both alternatives in a conditional branch, and only commits to the path that is required once the branch is resolved; parallel scan allows associative functions to be scanned in logarithmic rather than linear time, and this has many applications including fast adders and fast ALUs; associative searching uses parallel circuits embedded in a small memory to perform searches in almost O(1) time rather than O(n) time; one application of associative searching is in TLB circuits. And there are many more examples (use of parallel decoder trees to provide two output ports on a register file, saving a full clock cycle for each arithmetic instruction…)

---

* -- 使用并行扫描的快速ALU。推导出一个树状电路，在对数时间内实现关联函数f的扫描；波纹携带加法器是用扫描表示的，有一个非关联函数，但这可以用组合方式转变为关联扫描。这导致了一个在对数时间内执行加法的电路。-- Fast ALU using parallel scan. A tree circuit is derived that implements scan f for an associative function f in logarithmic time; the ripple carry adder is expressed using scan, with a non associative function but this can be transformed into an associative scan using composition. This leads to a circuit that performs addition in log time.
* --关联存储器。一个搜索可以在一个时钟周期内进行，使用连接到每个存储字的比较电路，以及一个树状网络来定位一个匹配和解决多个响应者。这可以用于TLB、高速缓存和高性能应用中。-- Associative memory. A search can be performed in one clock cycle, using a comparison circuit attached to each memory word, and a tree network to locate a match and resolve multiple responders. This can be used in TLB, cache, and high performance applications.
* --索引区间算法。一个数组使用明确的索引范围而不是地址来表示数组中的位置。这允许表示关于位置的部分信息。一系列的算法可以并行地细化许多索引区间。-- Index interval algorithms. An array is represented using an explicit range of indices rather than address to represent location in the array. This allows for representation of partial information about location. A family of algorithms can refine many index intervals in parallel.

---

:orange: 这些技术能使处理器的执行速度更快，但代价是需要大量的逻辑门和触发器。 lead to faster processor execution at the expense of a needing significantly more logic gates and flip flops.

使用携带式查找加法器来减少关键路径深度；流水线化指令执行；提供多个功能单元以允许超标量执行；用记分板或公共数据总线加强控制以提高超标量执行的效率；提供矢量或SIMD指令；缓存存储器；交错存储器；包括SIMD并行处理器和多核处理器的并行系统。Some suitable examples are: use of a carry lookahead adder to reduce the critical path depth; pipelining the instruction execution; providing multiple functional units to allow superscalar execution; enhancing the control with a scoreboard or common data bus to improve efficiency of the superscalar execution; providing vector or SIMD instructions; cache memory; interleaved memory; parallel systems including SIMD parallel processors and multicore processors. 

# ==========

# 电路重定时(加快时钟频率）：Retiming

> 重定时改变了一个系统的状态表示，但是并不改进它的逻辑行为。通常用它来平衡一个流水线系统中各个阶段之间的延迟

![](/static/2022-04-27-23-03-24.png)
![](/static/2022-04-27-23-03-47.png)
![](/static/2022-04-27-23-04-12.png)

---

目标：减少关键路径 •	Goal: reduce the critical path

- 对**不在关键路径上的电路进行加速是没有意义的**!–	There is no point in speeding up a circuit that isn't on the critical path!
- 思路：**将关键路径分成两部分，用锁存器(寄存器)分开**•	Idea: split the critical path into two parts, separated by a latch
  - **这缩短了关键路径，但需要两个时钟周期** –	This shortens the critical path but requires two clock cycles
- 它为流水线引入了机会! •	It introduces opportunities for pipelining!

---

一个典型的处理器包含一个核心路径，从寄存器文件开始，经过选择操作数的多路复用器，通过包括加法器的ALU，结果再经过多路复用器，最后回到寄存器文件中。(这条路径是算术指令所需要的）。这个核心路径通常是关键路径，因为有地址解码器、一些多路复用器，而且**加法器的路径深度很高**。为了提高时钟速度，有必要减少关键路径。做到这一点的一个技术是**重定时，即在一个长路径中插入额外的锁存器，以便将一个长路径分成两个短路径。这减少了关键路径的深度，但代价是需要额外的时钟周期**。通过在ALU的输入和输出上放置锁存器，关键路径被限制在加法器上，这使得时钟速度加快。**一条算术指令将需要一到两个额外的时钟周期，但是如果数据路径是流水线式的，那么这个开销就可以消除(((其他方法，比如用fast adder，，超前进位加法器**。 A typical processor contains a core path starting from the register files, going through multiplexer for selecting operands, through the ALU including an adder, with the result going through further multiplexers and finally back into the register file. (This path is needed for arithmetic instructions). This core path will typically be the critical path because there are address decoders, a number of multiplexers, and the adder has a high path depth. To improve the clock speed, it is necessary to reduce the critical path. One technique to do this is retiming, which is the insertion of additional latches into a long path in order to break one long path into two shorter ones. This reduces the critical path depth at the expense of requiring additional clock cycles. By placing a latch on the inputs and output of the ALU, the critical path is limited to the adder, and this allows a faster clock speed. An arithmetic instruction will require one or two additional clock cycles, but that overhead may be eliminated if the datapath is pipelined.

---

:orange: 还是数据通路关键路径

* 关键路径从寄存器文件中的触发器开始。它通过多路复用器树进行寄存器地址解码；然后通过多路复用器到x和y；然后进入包含波纹携带加法器的alu（它对关键路径的贡献最大，因为携带在字上传播；在这一点上，关键路径只涉及和的最有效位和携带输出。最后，p被广播（扇出）到寄存器文件中的寄存器，在那里它要经过另一个多路复用器（用于字的最重要位的那个）。时钟的设置必须提供一个至少足够长的时钟周期，以使关键路径上的所有信号都能沉淀。 The critical path starts with the flip flops in the register file. It goes through the multiplexer trees for register address decoding; then through multiplexers onto x and y; then into the alu which contains a ripple carry adder (which makes the largest contribution to the critical path as carry propagates across the word; at this point the critical path involves only the most significant bit of the sum and the carry output. The critical path then goes through two multiplexers onto p.	Finally, p is broadcast (fanout) to the registers in the register file, where it goes through another multiplexer (the one for the most significant bit of the word). The clock must be set to provide a clock cycle at least long enough to enable all signals on the critical path to settle.
  * 我们可以引入两个缓冲寄存器，分别将x和y作为输入，并将其状态直接提供给alu。这将减少关键路径（通过寄存器文件解码树的深度和从a,b到x,y的路径上的其他多路复用器），代价是RRR指令将需要两个时钟周期，但改进的时钟速度将影响所有机器操作，而不仅仅是RRR。 We can introduce two buffer registers that take x and y as inputs respectively, and provide their states directly into the alu. This will reduce the critical path (by the depth of the register file decoder trees and other multiplexers on the path from a,b to x,y) The cost is that two clock cycles will be needed by the RRR instructions, but the improved clock speed will affect all machine operations, not just RRR.

# 重定时缩减关键路径例子

![](/static/2022-04-26-01-46-04.png)
![](/static/2022-04-26-01-52-52.png)
![](/static/2022-04-26-01-55-20.png)

- **在组合关键路径的各部分之间引入寄存器**•	Introduce registers between portions of the combinational critical path
  - 新的寄存器ra, rb用来保存来自寄存器文件的输出–	New registers ra, rb to keep the outputs from the register file
  - 新的寄存器r用来保持alu的输出–	New register r to keep the alu output
- **RRR指令需要三个时钟周期，但时钟速度更高**•	RRR instructions require three clock cycles but the clock speed is higher
  - **关键路径短了，下面每一步变快了**
  - RF（从寄存器文件中获取）：ra := rf[ir_sa], `rb := rf[ir_sb]` –	RF (fetch from register file):	ra := rf[ir_sa], rb := rf[ir_sb]
  - OP (Alu操作):` r := alu ir_op ra rb` –	OP (alu operation):	r := alu ir_op ra rb
  - WR (写结果): `rf[ir_d] := r` –	WR (write result):	rf[ir_d] := r

---

(2016-iii) 定义重定时一词。使用重定时技术修改这个数据通路，以使其更快。解释一下修改后的电路是如何使时钟速度加快的，并说明如何使用重定时的数据通路来实现add R1,R2,R3的指令。(iii)	Define the term retiming. Modify this datapath using retiming in order to make it faster. Explain how the modified circuit allows a faster clock speed, and show how the instruction add R1,R2,R3 would be implemented using the retimed datapath.

重定时是一种优化，其目的是通过将关键路径分割成两个或多个由锁存器分隔的较短路径来降低时钟速度。其结果是，时钟速度可以提高，但需要几个周期来执行受影响的操作。对于Sigma16/M1数据通路，我们在ALU之前引入了锁存器，

```
xlatch = wlatch n x
ylatch = wlatch n y
(ovfl,r) = alu n (ctl_alu ctlsigs) xlatch ylatch
```

这意味着关键路径只是通过alu的携带链，但不包括寄存器文件的读出或控制多路复用器；因此，时钟可以运行得更快。数据通路中的许多操作现在都会变得更快，但RRR指令现在需要一个额外的时钟周期来使数据通过锁存器。为了执行加法指令，第一个周期锁存x和y；第二个周期执行加法。

# 流水线：Pipelining

> 流⽔线技术是⼀种将每条指令分解为多步，并让各步操作重叠，从⽽实现⼏条指令并⾏处理的技术。程序中的指令仍是⼀条条顺序执 ⾏，但可以预先取若⼲条指令，并在当前指令尚未执⾏完时，提前启动后续指令的另⼀些操作步骤。这样显然可加速⼀段程序的运⾏过程。

---

![](/static/2022-04-26-02-03-52.png)

- 每条指令的执行都需要几个步骤，而数据的依赖性要求这些步骤按顺序进行，而不是并行进行。例如，指令在被解码之前必须被获取；在执行之前必须被解码，而执行可能需要几个步骤（在加载或存储指令的内存访问之前必须计算有效地址）。然而，一些数据依赖性在连续（或邻近）的指令之间并不成立。例如，当一条指令被解码时，下一条指令可以被取走，而上一条指令可以计算其有效地址。流水线引入了额外的电路，使指令执行的每个阶段在一个单独的阶段进行，因此连续的指令可以通过流水线，并在顺序处理器中需要的相同周期内执行，但其他指令可以并行执行。Each instruction requires several steps to execute, and data dependencies require these steps to be performed in sequence, not in parallel. For example, the instruction must be fetched before it can be decoded; it must be decoded before it can execute, and the execution may require several steps (the effective address must be calculated before a memory access in a load or store instruction). However, some of the data dependencies do not hold between consecutive (or nearby) instructions. For example while one instruction is being decoded the following one can be fetched, and the preceding one can be calculating its effective address. Pipelining introduces additional circuitry that enables each phase of instruction execution to be carried out in a separate stage, so the consecutive instructions can pass through the pipeline and execute in the same number of cycles they would need in a sequential processor, yet other instructions can execute in parallel. 
- <font color="red">每个指令都需要几个时钟周期，因为各步骤之间存在数据依赖关系。【然而，一条指令的一个步骤和下一条指令的前一个步骤之间往往没有数据依赖关系。例如，一条指令可能在执行算术运算的同时，下一条指令访问其寄存器，而后一条指令则从缓存中加载】。理论上最大的改进是4倍，但是流水线的加速系数一般会小于阶段的数量</font>。Each instruction requires several clock cycles, because of data dependencies between the steps. However, there are often no data dependencies between one step of an instruction and an earlier step of the following instruction. For example an instruction might be executing an arithmetic operation in parallel with the following instruction accessing its registers and the instruction after that being loaded from cache. The theoretical maximum improvement would be 4x, but a pipeline will generally give a speedup factor less than the number of stages
  - 然而，并行性并不完美，一个有n个阶段的流水线会因为一些原因而使速度小于n，包括。数据的依赖性可能迫使流水线停滞，以防止出现不正确的结果。不同的指令格式可能需要不同的操作序列，但它们都必须经过同一流水线。流水线可能会引入额外的锁存器（例如，作为重定时的结果），这引入了锁存器的开销。某些需要较少工作的指令必须花费全部的时钟周期来通过流水线。 However, the parallelism is not perfect and a pipeline with n stages will give a speedup smaller than n for several reasons, including: Data dependencies may force the pipeline to stall in order to prevent incorrect results. Different instruction formats may require different sequences of operations yet they all must go through the same pipeline. Pipelining may introduce additional latches (e.g. as a result of retiming) which introduces latching overhead. Particular instructions that require less work must take the full number of clock cycles to go through the pipeline.
- 每条指令都需要几个必须依次执行的步骤•	Each instruction requires several steps that must be performed in sequence
- 数据通路包含**每个步骤**的**硬件（一个阶段**）。•	The datapath contains hardware (a stage) for each step
- 思路：**在一个时钟周期内，每个阶段都在处理一个单独的指令**•	Idea: during a clock cycle, every stage is working on a separate instruction
- 结果：一个**指令流**的速度最多可以达到n倍，有**n个阶段**。•	Result: a stream of instructions is faster by up to a factor of n, with n stages
  - 一个**单独的指令**仍然需要相同的时间（**相同的CPI，每条指令的平均周期数**）。–	An individual instruction still takes the same time (same CPI)
- **理想情况下，每周期的平均指令数为1（`IPC=1/CPI≈1`**）。•	Ideally, the average instructions per cycle would be 1 (IPC = 1/CPI ≈ 1)
  - **没有流水线，IPC<<1** –	Without pipelining IPC << 1
  - 我们想每个周期执行更多指令

:orange:流水线意思是，我们把一连串的任务，现在同时做几个（M2电路）。 Pipelining means that we're taking a sequence of tasks, and now we do several at the same time (M2 circuit)

* 比如一个RRR序列，，，里面steps就是每个子指令（对应一个stage），，流水线同时执行这些子指令

# 流水线化RRR指令流例子：Pipelining a stream of RRR Instruction

![](/static/2022-04-26-02-37-42.png)

问题：**几个阶段都提到了ir，但每个阶段都应该指代它所执行的指令** •	Problem: several of the stages refer to the ir, but each should be referring to the instruction that it is executing

- 解决方案：为**每个阶段引入一个单独的ir** •	Solution: introduce a separate ir for each stage

![](/static/2022-04-26-02-40-52.png)

- 现在，**各阶段之间的寄存器包括ir的副本** •	Now the registers between stages include copies of the ir
- **每个阶段都有自己的指令** –	Each stage has its own instruction

# 其他例子

![](/static/2022-04-26-03-25-32.png)

# 有无流水线add, load指令

(b) 给出Sigma16的基本数据通路（没有流水线）在执行(1)和加法指令以及(2)加载指令时的步骤顺序。指出这些序列之间的差异，以及它们对处理器流水线设计的影响。(b)	Give the sequence of steps executed by the basic datapath (without a pipeline) for Sigma16 as it executes (1) and add instruction, and (2) a load instruction. Identify the differences between these sequences, and their impact on the design of a pipeline for the processor.

对于加法，(1)指令被加载到ir中；(2)RTM的反馈环路执行加法并写入结果。

对于加载，（1）指令被加载到ir；（2）位移被加载到adr；（3）有效地址被计算；（4）内存被访问；（5）数据被写入寄存器文件中。

使用2级流水线执行一个完全由RRR指令组成的程序是可能的，通过重定时可以用3级流水线完成。然而，加载指令在4或5级流水线中表现得更好。在一个完整的处理器中，流水线需要支持RX指令，所以对于长序列的RRR指令，流水线不会实现最充分的加速。For add, (1) instruction is loaded into ir; (2) feedback loop in RTM performs the addition and writes result. For load, (1) instruction is loaded into ir; (2) displacement is loaded into adr; (3) effective address is calculated; (4) memory is accessed; (5) data is written into register file. It is possible to execute a program consisting entirely of RRR instructions using a 2-stage pipeline, and with retiming it could be done with a 3-stage pipeline. The load instruction, however, performs better with a 4 or 5 stage pipeline. In a full processor, the pipeline would need to support the RX instructions, so pipelining will not achieve the fullest possible speedup for long sequences of RRR instructions. 

# 五级流水线-完整指令集流水线：Pipeline for full instruction set

![](/static/2022-04-28-15-33-33.png)

我们已经看到如何为RRR指令引入一个3级流水线•	We've seen how to introduce a 3-stage pipeline for RRR instructions

- **对于RX指令，我们需要更多的阶段** •	For RX instructions, we need more stages
  - 计算有效地址，执行数据内存访问 –	Calculate effective address, perform data memory access
- 因此，**流水线必须有足够的阶段来应对最坏的情况**•	Therefore the pipeline must have enough stages for the worst case
- **所有指令都必须经过相同的阶段序列**•	All instructions must go through the same sequence of stages
  - IF、RF、OP、WR，等等
  - ![](/static/2022-04-26-03-22-39.png)
  - 译码/取数
- **有些阶段将执行不同的操作，这取决于指令的情况**•	Some stages will perform different operations, depending on the instruction
  - RRR、RX（加载、存储、跳转）等 –	RRR, RX (load, store, jumps), etc

# 流水线冒险：Pipeline Hazards

> 我们之前考虑流水线时，只有当指令之间是不相关时才是完全正确的。但是真实系统中，指令之间存在两种形式的相关：数据相关（Data Dependency），下一条指令会用到这条指令计算出来的结果；控制相关（Control Denpendency），一条指令要确定下一条指令的位置。**这些相关可能会导致流水线产生计算错误，称为冒险（Hazard），包括：数据冒险（Data Hazard）和控制冒险（Control Hazard**）。

流水线会导致几种被称为危险的冲突 •	Pipelining can lead to several kinds of conflict called hazards

- **结构：资源冲突（没有足够的资源来进行并行操作**）–	Structural: resource conflicts (not enough to operate in parallel)
  - 当一条指令需要的硬件部件还在为之前的指令工作，而无法为这条指令提供服务，那就导致了结构冒险。（这里结构是指硬件当中的某个部件、也称为资源冲突
- **数据**：**指令取决于前一个指令的结果**（还没有得到）。Data: instruction depends on result of previous one (not available yet)
- **控制**：改变PC的行动（跳转、分支指令）。–	Control: actions changing PC (jumps, branch instructions)
  - 控制相关（Control Denpendency），一条指令要确定下一条指令的位置
  - 正常情况下，指令在流水线中总是按顺序执行，当遇到改变指令执行顺序的情况时，流水线中指令的正常执行会被阻塞。这种由于发生了指令执行顺序改变而引起的流水线阻塞称为控制冒险（control hazards）。各类转移指令（包括调用、返回指令）的执行，以及异常和中断的出现都会改变指令执行顺序，因而都可能会引发控制冒险。

每种类型的危险都必须被识别和解决 •	Each type of hazard must be identified and solved

- 有些是可以通过**引入更多的电路**来避免的–	Some can be avoided by introducing more circuits
- 有些需要**让流水线停顿，并在短时间内回到顺序执行**。–	Some require stalling the pipeline and get back to sequential execution for a brief time

:orange: 重要的一点是...

- **保持正确的行为是至关重要的** –	It is essential to maintain correct behaviour
- 在**正确性的前提**下，我们希望有**最大限度的并行性** –	Subject to correctness, we want the maximum amount of parallelism

# 结构冒险: Structural hazards

在M1中，我们使用ALU来进行PC增量。•	In the M1, we used the ALU to increment the pc

- 在M2中【流水线】，**我们在第1阶段对PC进行递增，在第3阶段也使用ALU**。•	In M2 we increment the pc in stage 1 and also using the ALU in stage 3
- 简单的解决方案：**在第3阶段使用完整的ALU，在第1阶段引入一个简单的增量器电路**。–	Simple solution: use the full ALU for stage 3 and introduce a simple incrementer circuit for stage 1
  - 引入更多的电路，避免结构化危险

另一个结构性问题，**加载/存储指令需要访问内存，但我们也需要为下一条指令访问内存**。•	Another structural hazard, load/store instructions require a memory access but we also need to access memory for the next instruction

- 解决方案：为**指令和数据分开存储**–	Solution: separate memories for instructions and data
  - 数据存储，指令存储，，分开，，减少资源访问冲突
- 这似乎违背了 "冯-诺依曼 "架构（统一内存），但我们有...•	This seems to go against the “von Neumann” architecture (unified memory), but we have…
  - **一个**主存储器–	One main memory
  - **两个独立的缓存，一个用于指令，一个用于数据**–	Two separate caches, one for instructions and one for data

# 数据冒险：Data Hazards

IF、RF、OP、WR

![](/static/2022-04-26-03-29-41.png)

---

会发生什么，如果...•	What happens if…

- **阶段4正在完成执行R7 := R2-R3** –	Stage 4 is finishing up executing R7 := R2-R3
- **但是阶段2正在获取R7，因为它正在处理R6 := R7+R4(OP阶段等输入**) –	But stage 2 is fetching R7 because it's working on R6 := R7+R4
  - 阶段2将把错误的值放入Ra中。•	Stage 2 is going to put the wrong value into ra
  - 这是一个数据危险–	This is a data hazard
- 机器正在**同时执行几个赋值语句** •	The machine is executing several assignment statements simultaneously
  - 有时这很安全，有时不安全 –	Sometimes this is safe, sometimes not
- 我们需要确保获得R7的正确值 •	We need to ensure that the right value of R7 is obtained

# 数据冒险分类（3情况）：classification of data hazards

**先写后读相关性（RAW**）。	R7 :=R2-R3; R6 :=R7+R4•	**Read after Write (RAW**)

- 对R7的读取是在对R7的写入之后进行的，所以该写入的结果必须由读取得到。–	The read of R7 comes after the write to R7, so the result of that write must be obtained by the read
- RAW危险是指一条指令将结果写入寄存器，下一条指令读取该寄存器，但**获得了错误的值，因为结果在被获取时还没有到达目的寄存器**。A RAW hazard is a situation where an instruction writes a result into a register, and the next instruction reads that register but obtains the wrong value because the result has not yet reached the destination register by the time it is being fetched.
  - 例如：add **R1**,R2,R3; sub R4,**R1**,R5会导致一个RAW危险，因为sub指令是在add指令准备加载总和的同一周期内获取R1。For example: add R1,R2,R3; sub R4,R1,R5 causes an RAW hazard because the sub instruction is fetching R1 during the same cycle that the add instruction is preparing to load the sum into it.
  - **控制可以通过比较回写阶段的目标寄存器和执行阶段的两个操作数寄存器来检测**；如果其中任何一个相等，就有危险。在这种情况下，**控制器可以暂停流水线**，让加法完成，同时保留第二条指令。**另一种方法是使用转发：可以引入一个特殊的路径来连接ALU的输出（它有加法的结果）和子的操作数锁存器（绕过寄存器文件**）The control can detect this by comparing the destination register in the writeback stage with the two operand registers in the execute stage; if either of those is equal then there is a hazard. In this case the control can stall the pipeline, letting the add complete while holding back the second instruction. Another alternative is to use bypassing: a special path can be introduced to connect the output of the ALU (which has the result of the add) to the operand latch for the sub (bypassing the register file).
  - 其他例子
    - 当一条指令将一个值写入寄存器，随后的指令读取该值，但读取发生在写入之前，导致不正确的数据被用于第二条指令时，就会发生RAW危险。发生这种情况的原因是，从寄存器中读取数据发生在流水线的早期阶段，而结果的回写发生在最后。这个代码片段包含一个RAW危险：add R1,R2,R3; sub R5,R1,R6。问题是子程序会在add更新之前读取R1。这个代码片段不包含RAW危险，因为寄存器是不相连的：add R1,R2,R3; sub R4,R5,R6. A RAW hazard occurs when an instruction writes a value to a register, and a subsequent instruction reads that value, but the read occurs before the write, resulting in incorrect data being used in the second instruction. The reason this can happen is that reading data from the registers occurs in an early pipeline stage, while the writeback of the result occurs at the end. This code fragment contains a RAW hazard: add R1,R2,R3; sub R5,R1,R6. The problem is that the sub will read R1 before the add has updated it. This code fragment does not contain a RAW hazard, because the registers are disjoint: add R1,R2,R3; sub R4,R5,R6. 
    * RAW危险是指一条指令修改了一个被后续指令取走的寄存器，但是修改发生在流水线的后期，这使得新的数值不能在后续指令取走前的周期内进入寄存器。如果不采取任何措施，这将导致第二条指令获取错误的值，机器就出现了故障。**为了检测这个危险，前面流水线阶段的ir的目标字段与后面流水线阶段的ir的每个源字段进行并行比较，比较结果是or'ed。如果这个结果是1，就有一个RAW危险**。为了使流水线停滞，后面的指令保持在前面的状态，**同时在后面的阶段放置一个nop（气泡）。这可以确保正确的数据被获取，但代价是浪费了一个时钟周期。(根据流水线的组织结构，也可以使用转发，这样可以避免冒泡**，）。A RAW hazard means that an instruction modifies a register which is fetched by a subsequent instruction, but the modification occurs in a late pipeline stage and that prevents the new value from entering the register on the cycle before the later instruction fetches it. If nothing is done about it, this would cause the second instruction to fetch the wrong value, and the machine has malfunctioned. To detect the hazard, the destination field of the ir in the earlier pipeline stage is compared in parallel with each of the source fields of the ir in the later pipeline stage, and the comparison results are or’ed. If this is 1 there is a RAW hazard. To stall the pipeline, the subsequent instruction remains in the earlier state, while a nop (bubble) is placed in the following stage. This ensures that the correct data will be fetched, at the cost of a wasted clock cycle. (Depending on the pipeline organization, it may also possible to use bypassing, which avoids the bubble,) 

**先读后写相关性（WAR**）。	R1 :=R2+R3; R2 :=R4+R5 •	**Write after Read (WAR**)

- 第一条指令必须获取 R2 的旧值，因此我们必须确保第二条指令在第一条指令获取 R2 之前不会写入 R2。The first instruction must get the old value of R2, so we have to be sure that the second instruction doesn’t write R2 before the first one gets it
- **在所述的RRR流水线中是不可能的（在其他流水线中可能发生**）。–	Impossible in the RRR pipeline described (can happen in other pipelines)

**先写后写相关性（WAW**）。	R2 := result1; R2 := result2 •	**Write after Write** (WAW)

- R2的最终值必须是result2，**但在WAW危险中，它是result1** –	The final value of R2 must be result2 but in a WAW hazard it's result1
  - 可能第二条指令先执行
- WAW危险是指**两个连续的（或邻近的）指令都将其结果写入同一个寄存器，但第二条指令在第一条指令之前写入其结果；结果是指令顺序被错误地执行**（其效果与省略第二条指令相同）。A WAW hazard is a condition where two consecutive (or nearby) instructions both write their result to the same register, but the second instruction writes its result before the first one does; as a consequence the instruction sequence is executed incorrectly (the effect is the same as omitting the second instruction).
  - 在一个序列中，有两条指令向同一个寄存器写值，如div R1,R2,R3；add R1,R4,R5。其效果应该是加法的结果最终出现在R1。对于功能单元，这两个操作可以分别发给不同的单元。由于除法的速度可能比加法慢，它的结果可能在加法的结果被加载后才被加载到R1，所以错误的结果最终会出现在R1。这种情况发生的根本原因是，这些操作同时进行，可能有不同的执行时间 There are two instructions in a sequence that write a value to the same register, such as div R1,R2,R3; add R1,R4,R5. The effect should be that the result of the add ends up in R1. With functional units, both operations may be issued to separate units. Since the divide is likely to be slower than the add, its result may be loaded into R1 after the result of the add is loaded, so the wrong result ends up in R1. The fundamental reason this can happen is that the operations proceed concurrently and may have different execution times
    - 在繁**忙位系统**中，除法会将R1标记为繁忙。当流水线到达加法指令时，它将停滞，直到R1不再繁忙。这样可以确保除法的结果在加载加法的结果之前被加载到R1。With a Busy Bit system, the divide would mark R1 as Busy. When the pipeline reaches the add instruction, it would stall until R1 is no longer busy. This ensures that the result of the divide is loaded into R1 before the result of the add is loaded.
- 解释什么是WAW（写后）流水线危险。解释**为什么在ALU中执行所有计算的指令序列中不能发生WAW危险**。解释为什么在一个有**多个功能单元**的处理器中，一个包含由功能单元执行的操作的指令序列可以发生WAW危险。
  - 如果操作是在**ALU**中进行的（例如整数加法），第一条计算只需一个周期，并在第二条计算发生前完成，所以结果将以正确的顺序写入目标寄存器。If the operations are performed in the ALU (integer addition, for example) the first calculation will take only one cycle and will be ready before the second calculation takes place, so the results will be written to the destination register in the correct order.
  - 然而，如果操作是在不同的**功能单元**中进行的，那么第一个操作有可能比第二个操作多花几个周期；在这种情况下，结果可能以错误的顺序写入目标寄存器。有几种技术可以防止这种错误的发生，包括**繁忙位、公共数据总线和中央控制分析**（记分牌）。However, if the operations are performed in separate functional units it is possible for the first one to take several cycles longer than the second; in this case the results may be written to the destination in the wrong order. There are several techniques for preventing this error from occurring, including busy bits, common data bus, and central control analysis (scoreboard).
    - 在繁忙位系统中，除法会将R1标记为繁忙。当流水线到达加法指令时，它将停滞，直到R1不再繁忙。这样可以确保除法的结果在加载加法的结果之前被加载到R1。With a Busy Bit system, the divide would mark R1 as Busy. When the pipeline reaches the add instruction, it would stall until R1 is no longer busy. This ensures that the result of the divide is loaded into R1 before the result of the add is loaded.
* 证明在Sigma16的流水线处理器中不会发生WAW危险。**解释为什么在超标量处理器中可能出现WAW危险**。Show that a WAW hazard cannot occur in the pipelined processor for Sigma16. Explain why a WAW hazard is possible in a superscalar processor.
  * 当两条指令都写到同一个寄存器时，就会发生WAW的危险，但是第一条指令的结果比第二条指令的结果晚到，所以寄存器最后的值是错误的。如果控制算法能保证数据到达寄存器文件的顺序与相应的加载/算术指令的执行顺序相同，就不可能出现这种情况。**Sigma16总是在写阶段写入RRR指令的结果，而流水线中的后续指令会在晚些时候到达该阶段，所以这种危险不会发生**。如果一条加载指令因为内存响应缓慢而被延迟，那么流水线就会停滞不前，同样，较早的指令也不能在写阶段领先于其他指令。**在超标量处理器中，流水线不会停滞，但需要缓慢计算或内存访问的指令会被发布到一个功能单元。结果可以以任何顺序从功能单元回来，这使得WAW危险成为可能**。这可以用静态调度（记分牌）、繁忙位或公共数据总线来解决。A WAW hazard occurs when two instructions both write to the same register, but the result from the first instruction arrives later than the result from the second one, so the register ends up with the wrong value. This is impossible if the control algorithm ensures that data reaches the register file in the same order as the corresponding load/arithmetic instructions were executed. Sigma16 always writes the result of a RRR instruction in the write stage, and an instruction following in the pipeline will reach that stage later, so the hazard cannot occur. If a load instruction is delayed because the memory responds slowly, the pipeline stalls and again the earlier instruction cannot get ahead of the other one in the write stage. In a superscalar processor, the pipeline does not stall but instructions that require slow computations or memory accesses are issued to a functional unit. The results can come back in any order from the functional units, and this makes a WAW hazard possible. This can be addressed using static scheduling (scoreboard), busy bits, or common data bus.

:orange: **注意，没有RAR危险**•	Note that there is no RAR hazard

* RAR。这不能导致错误，所以不构成危险，管道也不会停滞。RAR. This cannot cause an error, so does not constitute a hazard, and the pipeline doesn’t stall.

# 检测数据冒险：Detecting data hazards

找到潜在冒险很重要 •	It's essential to find all the potential hazards

我们引入**组合逻辑**来检查它们–	We introduce combinational logic to check them all

- **每个阶段都有一个IR（每个IR都有d、sa、sb字段**）。•	There is an ir for each stage (each ir has d, sa, sb fields)
- 例如：**检测R7 := R2-R3; R6 := R7+R4中的RAW危险 RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)** •	Example: to detect the RAW hazard in	R7 := R2-R3;	R6 := R7+R4 RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)
  - `RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)`
  - RAW=1就是有hazard??????????

# 避免数据冒险-转发vs暂停：Handling data hazards

![](/static/2022-04-26-03-31-18.png)

- **暂停**：**暂时停止前一阶段的指令进行**，为后一阶段的指令完成其工作提供时间 •	**Stalling**: temporarily stop the instruction in an earlier stage from proceeding, giving time for a later stage to complete its work
  - 这在流水线中引入了一个 "气泡"。–	This introduces a “bubble” into the pipeline
  - ![](/static/2022-04-26-03-21-13.png)
    - 3个nop/bubble完全解决，D取数延迟到周期7
  - **为了检测这个危险，前面流水线阶段的ir的目标字段与后面流水线阶段的ir的每个源字段进行并行比较，比较结果是or'ed。如果这个结果是1，就有一个RAW危险**。为了使流水线停滞，后面的指令保持在前面的状态，**同时在后面的阶段放置一个nop（气泡）。这可以确保正确的数据被获取，但代价是浪费了一个时钟周期。(根据流水线的组织结构，也可以使用转发，这样可以避免冒泡**，）。A RAW hazard means that an instruction modifies a register which is fetched by a subsequent instruction, but the modification occurs in a late pipeline stage and that prevents the new value from entering the register on the cycle before the later instruction fetches it. If nothing is done about it, this would cause the second instruction to fetch the wrong value, and the machine has malfunctioned. To detect the hazard, the destination field of the ir in the earlier pipeline stage is compared in parallel with each of the source fields of the ir in the later pipeline stage, and the comparison results are or’ed. If this is 1 there is a RAW hazard. To stall the pipeline, the subsequent instruction remains in the earlier state, while a nop (bubble) is placed in the following stage. This ensures that the correct data will be fetched, at the cost of a wasted clock cycle. (Depending on the pipeline organization, it may also possible to use bypassing, which avoids the bubble,) 
- **旁路/转发**：引入一些额外的电路，使**流水线能够继续全速运行，不出错**。•	**Bypassing/Forwarding**: introduce some additional circuitry that allows the pipeline to continue at full speed, without error
  - ![](/static/2022-04-27-21-42-12.png)
    - 当运算完成后在 Exec/Mem 流水段寄存器中已经存在后面指令可能需要用到的数据，我们<font color="deeppink">可以不等Wr阶段写回寄存器而是在硬件上稍加改动直接将流水段寄存器中的数据送往下一指令的ALU输入端</font>，这种操作称为 转发（Forwarding）或旁路（Bypassing），可以理解为 “抄近道” 了。
  - 有时一个早期阶段需要一个**来自寄存器的值**–	Sometimes an early stage needs a value from a register
  - <font color="deeppink">寄存器还没有收到这个值，但这个值已经存在于机器的其他地方（还没有到达最终目的地</font>）–	The register hasn't yet received that value, but the value exists already somewhere else in the machine (hasn't reached the final destination yet)
  - 我们可以引入一个新的信号，"绕过 "寄存器，将值直接传送到需要的地方。–	We can introduce a new signal that “bypasses” the register, and transmits the value to where it is needed directly
  - 怎么做？我们需要更多的**比较器和复用器**!–	How? We need more comparators and multiplexers!
  - **转发是指将计算结果从产生计算结果的电路直接传送到需要计算结果的地方，而不必将计算结果加载到寄存器中，然后再读出寄存器**。例如，在指令序列add R1,R2,R3; sub R4,R5,R1中，子指令在计算结果的同一时钟周期内需要R1的值，但在该值被加载到R1之前的周期。在一个基本的数据通路中，流水线需要停顿一个周期，以便R1中的值可以使用。通过旁路，该值被直接传送到子操作数的锁存器中（除了被传送到R1中）。**这使得子指令可以继续进行而不需要停滞流水线**。Bypassing means transmitting the result of a calculation from the circuit where it is produced directly to the place where it is needed, without having to load the result into a register and then read out the register. For example, in the instruction sequence add R1,R2,R3; sub R4,R5,R1 the sub instruction needs the value of R1 during the same clock cycle where the result is calculated, but the cycle before that value is loaded into R1. In a basic datapath, the pipeline would need to stall for a cycle so the value is available in R1. With bypassing, the value is transmitted directly to the latch for the sub operands (in addition to being transmitted to R1). This enables the sub instruction to proceed without stalling the pipeline.

# 数据冒险-RAW转发例子:Example of bypassing/forwarding

- 考虑一个RAW危险：`add R1,R2,R3; sub R4,R1,R5`

- 假设在sub读取R1的同一阶段，add写入了R1的结果•	Assume the add writes result in R1 in the same stage that the sub reads R1
  - 即，，，sub先执行
  - 那么sub将得到错误的值•	Then the sub would get the wrong value
- <font color="deeppink">但是ALU在寄存器中出现结果之前就已经有了这个周期</font>。–	But the ALU has the result in the cycle before it appears in the register
- **数据通路可以让sub从两个不同的位置获得第一个操作数：寄存器文件和ALU输出**。•	The datapath can make the first operand available to the sub from two separate locations: the register file, and the ALU output
  - **多路复用器mux**在这两者之间进行选择（第二操作数也是如此）。–	A multiplexer selects between these (same for the second operand)
  - multi inputs one output
- <font color="red">控制单元检查后面阶段的目标字段和前面阶段的源字段</font>•	The control unit examines the destination field of the later stage and the source field of the earlier stage
  - RAW里面，write的目标和read的源
  - <font color="deeppink">如果它们相同，则设置多路复用器控制以选择ALU而不是第一个操作数的寄存器文件（否则，选中寄存器堆</font>。 If they are the same, it sets the multiplexor control to select the ALU rather than the register file for the first operand
  - 控制可以通过比较**回写阶段的目标寄存器和执行阶段的两个操作数寄存器（irx_a, irx_b）来检测**；如果其中任何一个相等，就有危险。在这种情况下，控制器可以**暂停流水线**，让加法完成，同时保留第二条指令。另一种方法是**使用转发**：可以引入一个特殊的路径来连接ALU的输出（它有加法的结果）和子的操作数锁存器（绕过寄存器文件）The control can detect this by comparing the destination register in the writeback stage with the two operand registers in the execute stage; if either of those is equal then there is a hazard. In this case the control can stall the pipeline, letting the add complete while holding back the second instruction. Another alternative is to use bypassing: a special path can be introduced to connect the output of the ALU (which has the result of the add) to the operand latch for the sub (bypassing the register file).

# 实现转发：Implementing bypassing

IF、RF、OP、WR

- 假设阶段3在**ir3中包含指令add R1,R2,R3，并且在周期中ALU输出总和**•	Suppose stage 3 contains instruction add R1,R2,R3 in ir3 and during the cycle the ALU is outputting the sum
  - 在下一个周期中，结果将在R1中得到。–	On the following cycle the result will be available in R1
- 阶段2在**ir2中包含指令sub R4,R1,R5**，现在需要加法的结果。•	Stage 2 contains instruction sub R4,R1,R5 in ir2 and needs the result of the addition right now
- **ALU输入缓冲寄存器的来源在寄存器文件中（通常），但在这种情况下源是ALU的输出**。•	The source of the ALU input buffer register is in the register file (normally), but it's the ALU output (in this situation)
  - 数据冒险时`sub R4,R1,R5(2), add R1,R2,R3(3)`

```
<!-- 前指令src==后指令des -->

ALU_input_buffer_1 =
 if ir2_sa1 == ir3_d
  then ALU_output 
  else reg[ir2_sa1]
```

# 控制冒险：Control Hazards

问题：在有条件的分支（跳转）指令中，**是否跳转的决定要到后期才做出**。•	Problem: In a conditional branch (jump) instruction, the decision of whether to jump is not taken until a late stage

![](/static/2022-04-27-22-48-26.png)

- 由于指令分支而引起的控制冒险也称为分支冒险（branch hazard
- 这就产生了**分支延迟槽**–	This creates branch delay slots
  - **不确定在这个slot中放入什么指令**
  - 早期的RISC采用微编程流水线的思路，改变了ISA语义定义，所以在分支/跳转后的指令老是在控制流改变发生以前被执行：
  -  因此软件应该**在slot放置有效的指令，或者放空指令**。
- <font color="deeppink">跳转后的指令可能假定跳转已满足，或未满足，或暂停</font>•	Instructions after the jump may assume the jump is **taken, not taken, or stall**
  - **如果假设跳转，最后没有跳转，我们需要取消这些指令**。–	If assume taken and finally is not taken we need to cancel them
  - 这些指令是**预测性**的–	These instructions are speculative

:orange: 解决方案：**在跳转之后，包括独立于跳转的指令，以填补分支延迟槽（即非预测性**）。•	Solution: after a jump include instructions that are independent of it to fill the branch delay slots (i.e. not speculative)

- 这样就可以做有用的工作，而不是在流水线上制造bubbles –	This allows to do useful work rather than creating bubbles in the pipeline

---

(b) 给出Sigma16的基本数据通路（没有流水线）执行条件跳转指令的步骤顺序。讨论如何在流水线中处理条件跳转而不造成错误的执行。(b)	Give the sequence of steps executed by the basic datapath (without a pipeline) for Sigma16 as it executes a conditional jump instruction. Discuss how a conditional jump can be handled in a pipeline without causing incorrect execution.

* (1) 下一条指令被取走，ir := mem[pc]，pc被递增。(2) 控件对操作码字段和d字段进行调度，因为这条指令使用了一个扩展操作码。	(3) 指令的第二个字被取走：adr := mem[pc], pc++. . (4) 计算有效地址。(5) 取出源寄存器，与0进行比较，控制执行一个调度。 (6) 如果要进行跳转，有效地址被移到pc寄存器中。**流水线的问题是，在确定是否应该跳转之前，存在修改pc的风险。如果流水线的结构允许这种危险，那么最简单的解决方案就是通过插入一个气泡来阻滞流水线的运行**。(1) the next instruction is fetched, ir := mem[pc], and the pc is incremented. (2) the control performs a dispatch on the opcode field and on the d field, because this instruction uses an expanding opcode.	(3) the second word of the instruction is fetched: adr := mem[pc], pc++. . (4) the effective address is calculated. (5) the source register is fetched, compared with 0 and the control performs a dispatch.  (6) if the jump should take place, the effective address is moved into the pc register. The problem with a pipeline is that there is a risk of modifying the pc before it is determined whether the jump should take place. If the structure of the pipeline would allow this hazard, then the simplest solution is to stall the pipeline by inserting a bubble. 

# 流水线效率：Performance of pipelining

流水线可以用来**提高时钟速度**，通过使用**重定时**将**几个组合操作分割成独立的周期** •	Pipelining can be used to improve clock speed by splitting several combinational operations into separate cycles using retiming

- 它可以**提高吞吐量**，**但不能提高执行单个操作的时间** •	It improves throughput, but not the time to perform individual operations
- 最大可能的**提速**因素是**阶段的数量（IPC(instruction per cycle≈1**）•	The maximum possible speedup factor is the number of stages (IPC ≈ 1)
- 在实践中，这很难实现(实践中，IPC < 1) • 	In practice, that is difficult to achieve (IPC < 1)
  - **暂停会降低性能，并不总是可以避免的** –	Stalls reduce performance and not always can be avoided
    - 处理器的时钟速度通常要比内存速度快一个数量级。这意味着，如果不仔细设计，流水线在每次加载或存储时都需要停顿。此外，流水线还需要等待许多周期来获得下一条指令。The processor clock speed is typically almost an order of magnitude faster than memory speed. This means that, without careful design, the pipeline would need to stall for every load or store. Furthermore, the pipeline would need to wait many cycles to obtain the next instruction.
  - **所有的指令都必须经过相同的阶段，即使其中一些指令可以用更少的阶段来解决** –	All instructions must go through the same stages, even if some of them could be solved with fewer stages
  - **在不同阶段完成的工作可能需要不同的时间，但所有阶段都必须以最慢的速度运行** –	The work done in different stages might require different time, but all stages have to run at the speed of the slowest

# 流水线&ISA设计-提高性能：Impact of instruction set on pipelining

解释为什么内存访问会导致流水线性能不佳。描述在系统执行**包含加载和存储指令**的程序时，可以使用三种技术来提高其性能

- **ISA的设计可以简化流水线，也可以使其变得复杂和无效**•	ISA can be designed to ease pipelining or to make it complex and ineffective
  - 例如，复杂的阶段会减缓时钟周期–	E.g. complex stages slow the clock period
- ISA可能有助于拥有**少量的指令类别**•	ISA may help to have a small number of classes of instructions
  - 它们的执行**可以被组织成相同的阶段序列**–	Their execution can be organised into the same sequence of stages
- RISC的目标：设计指令集，使流水线有效地工作。•	RISC goal: design the instruction set so that pipelining works effectively
- RISC解决方案：不要引入以下指令•	RISC solution: don’t introduce instructions that
  - 需要较慢的时钟–	Require a slower clock
  - 干扰优化技术（分支延迟槽等）。–	Interfere with optimisation techniques (branch delay slots, etc)

处理器的时钟速度通常要比内存速度快一个数量级。这意味着，如果**不仔细设计，流水线在每次加载或存储时都需要停顿。此外，流水线还需要等待许多周期来获得下一条指令**。The processor clock speed is typically almost an order of magnitude faster than memory speed. This means that, without careful design, the pipeline would need to stall for every load or store. Furthermore, the pipeline would need to wait many cycles to obtain the next instruction.

- 这个问题可以通过**指令缓存**来解决，这样几乎所有的指令都可以在一个周期内从缓存中获取。对于数据访问，高速缓存（无论是一般的高速缓存，还是特定的数据高速缓存）可以将大多数访问的时间减少到一个周期。-	That problem is solved using an instruction cache, so that almost all instructions will be fetched from the cache in one cycle. For data access, a cache (either a general cache, or a specific data cache) can reduce the time for most accesses to one cycle.
- 另一种技术是使用交错存储器，它允许对连续地址的访问同时进行。这有助于在实际需要数据之前的几个周期启动内存访问。-	Another technique is the use of interleaved memory, which allows accesses to consecutive addresses to take place concurrently. This is assisted by initiating memory accesses several cycles before the data will actually be needed.
- **RISC架构**也有助于将常用的数据保存在寄存器中，从而**减少内存访问的数量**。-	RISC architectures also help by keeping commonly used data in registers, thus reducing the number of memory accesses.
  - 寄存器足够大
- 另外，流水线可以被组织起来，以便**在等待内存访问时完成有用的工作**（例如，**分支延迟槽**）。-	Also, the pipeline can be organised so that useful work may get done while waiting for memory accesses (e.g. branch delay slots).

# 流水线效率局限

- <font color="red">每个指令都需要几个时钟周期，因为各步骤之间存在数据依赖关系。【然而，一条指令的一个步骤和下一条指令的前一个步骤之间往往没有数据依赖关系。例如，一条指令可能在执行算术运算的同时，下一条指令访问其寄存器，而后一条指令则从缓存中加载】。理论上最大的改进是4倍，但是流水线的加速系数一般会小于阶段的数量</font>。Each instruction requires several clock cycles, because of data dependencies between the steps. However, there are often no data dependencies between one step of an instruction and an earlier step of the following instruction. For example an instruction might be executing an arithmetic operation in parallel with the following instruction accessing its registers and the instruction after that being loaded from cache. The theoretical maximum improvement would be 4x, but a pipeline will generally give a speedup factor less than the number of stages

:orange: 流水线 speedup factor小于number of stages原因

* 1）所有指令都必须经过相同的阶段序列，即使不同的指令可能需要不同的步骤序列。1)	all instructions have to go through the same sequence of stages even though different instructions might require different sequences of steps;
* 2）有时会有数据依赖，如RAW危害，可能需要停顿流水线。2)	there are sometimes data dependencies, such as RAW hazards, that may require stalling the pipeline;
* 3）不同的阶段可能有不同的路径深度，但都必须以最慢的速度进行计时。3)	different stages may have different path depths but all have to be clocked at the speed of the slowest.
* 然而，并行性并不完美，一个有n个阶段的流水线会因为一些原因而使速度提高得小于n，包括。数据的依赖性可能会迫使流水线停顿，以防止出现不正确的结果。不同的指令格式可能需要不同的操作序列，但它们都必须通过同一流水线。流水线可能会引入额外的锁存器（例如，作为重定时的结果），这引入了锁存器的开销。某些需要较少工作的指令必须花费全部的时钟周期来通过流水线。However, the parallelism is not perfect and a pipeline with n stages will give a speedup smaller than n for several reasons, including: Data dependencies may force the pipeline to stall in order to prevent incorrect results. Different instruction formats may require different sequences of operations yet they all must go through the same pipeline. Pipelining may introduce additional latches (e.g. as a result of retiming) which introduces latching overhead. Particular instructions that require less work must take the full number of clock cycles to go through the pipeline.

# ==========

# 超标量：superscalar

> 超标量（superscalar）是指在CPU中有⼀条以上的流⽔线，并且每时钟周期内可以完成⼀条以上的指令，这种设计就叫超标量技术。
> 其实质是以空间换取时间。⽽超流⽔线是通过细化流⽔、提⾼主频，使得在⼀个机器周期内完成⼀个甚⾄多个操作，其实质是以时间换取 空间。

- 基本处理器（如M1）：**严格按顺序进行（IPC<<1**）•	Basic processor (e.g. M1): strictly sequential (IPC << 1)
- 流水线式处理器（如M2）：**在处于不同阶段的相邻指令之间引入并行性（理想IPC≈1，实际IPC<1**）•	Pipelined processor (e.g. M2): introduces parallelism between adjacent instructions that are in different phases (ideal IPC ≈ 1, real IPC < 1)
- 计算之间不存在并行性–	There is no parallelism among calculations

- 超标量处理器：使用多个执行/功能单元，使流水线更接近全速，获得计算之间的并行性 •	Superscalar processor: uses multiple execution/functional units to keep the pipeline closer to full speed, obtaining parallelism among calculations
- **单发射处理器 (IPC ≈ ≈ 1)–	Single-Issue Processors** (IPC ≈ ≈ 1)
- 多发射处理器 (IPC > 1)–	Multiple-Issue Processors (IPC > 1)

请注意，大多数超标量CPU也是流水线的，但也有可能有 •	Note that most superscalar CPUs are also pipelined, but it's possible to have

- 一个非流水线的超标量CPU–	A non-pipelined superscalar CPU
- 一个有流水线的非超标量CPU–	A pipelined non-superscalar CPU

# 执行单元：Functional units

一个执行单元是一个独立的子系统，执行一个特定的操作•	A functional unit is a separate subsystem that performs a specific operation

- 整数乘/除法，浮点加/乘法，sqrt，...。–	Integer multiplication/division, floating point addition/multiplication, sqrt, ..
- 它由一个**数据通路、控制和一个接口**组成•	It consists of a datapath, control, and an interface
  - 接口通常包括一个启动输入信号和一个准备输出信号–	Interface typically includes a **start input signal, and a ready output signal**

优点•	Advantages

- 使得流水线能够向一个功能单元发出一条 "困难 "指令，然后继续执行下一条 "容易 "指令–	Enables the pipeline to issue a “difficult” instruction to a functional unit and then to proceed with the next “easy” instruction
- 这使得它可以**并行地进行多项计算**–	This makes it possible to perform several calculations in parallel

ALU被限制在组合逻辑中可以在一个时钟周期内快速执行的操作（类似加法）。•	The ALU is restricted to operations that can be performed quickly in one clock cycle (addition-like) in combinational logic

- ALU中的复杂操作会使关键路径增加很多–	Complex operations in the ALU would increase a lot the critical path

---

:orange: 执行单元/功能单元对指令级并行性（流水线，超标量）的意义

* 由于数据的依赖性，一条指令需要几个时钟周期来执行。在流水线中，当一条指令经过第一个阶段后，它就会进入下一个阶段，而下一条指令则开始执行。因此，一个有n个阶段的流水线可以同时执行n个不同指令的一个阶段，尽管在实践中，流水线时常会出现停滞。在超标量执行中，在一个流水线阶段中执行的计算时间太长，会被卸载到一个单独的功能单元。有几个功能单元可用，独立的计算可以在不同的单元中同时进行。一个流水线可以在解码一条指令的同时获取下一条指令（这就是阶段1和阶段2之间的并行性）。同样，它可以在解码下一条指令的同时，对一条指令进行ALU计算（第2和第3阶段之间的并行性）。一个超标量的处理器可以并行地执行两个长的（如浮点）计算，而不会使流水线停顿。An instruction requires several clock cycles to execute, because of data dependencies. In pipelining, after an instruction has gone through the first phase, it moves along to the next phase while the next instruction begins. Thus a pipeline with n stages can execute one phase of n different instructions simultaneously, although in practice it is common that the pipeline will stall from time to time. In superscalar execution, calculations that are too long to perform in one pipeline stage are offloaded to a separate functional unit. There are several functional units available, and independent calculations can take place simultaneously in different units. A pipeline can decode an instruction while simultaneously fetching the next instruction (this is parallelism between stage 1 and stage 2). Similarly it can perform an ALU calculation for an instruction while decoding the next one (parallelism between stages 2 and 3). A superscalar processor can perform two long (e.g. floating point) calculations in parallel, without stalling the pipeline.
  * 为了发布一条指令并将其从流水线中移除，使后续指令能够取得进展，功能单元是必不可少的。Functional units are essential in order to issue an instruction and remove it from the pipeline, enabling subsequent instructions to make progress.
* **一个功能单元是一个顺序电路，在一个或多个时钟周期内计算一个函数。它有数据输入和一个启动控制输入；当start=1时，单元在下一个tick加载数据输入并开始计算。当它得到最终结果时，它将其放在一个输出信号上，并将一个准备输出信号设为1**。A functional unit is a sequential circuit that calculates a function in one or more clock cycles. It has data inputs and a start control input; when start=1 the unit loads the data inputs at the next tick and begins calculating. When it has the final result, it places that on an output signal and sets a ready output signal to 1.
  * **一个组合乘法器有一个大的路径深度，需要一个较长的时钟周期**。一个功能单元可以采取一个可变的周期，一旦知道结果就终止计算，但组合电路不能这样做，因为时钟速度是固定的。**一个功能单元可以与指令流水线同时启动和运行，允许指令的并行执行**。 A combinational multiplier has a large path depth, and would require a longer clock period. A functional unit can take a variable number of cycles, terminating the calculation as soon as the result is known, but a combinational circuit cannot do this as the clock speed is fixed. A functional unit can be started and run concurrently with the instruction pipeline, allowing parallel execution of instructions.
  * 当该单元在进行计算时，它在内部寄存器中保持其状态。如果在时钟滴答声中启动=1，该单元将加载输入数据并初始化内部寄存器。这样做的效果是放弃正在进行的计算。在开始新的计算时没有额外的开销。这种行为使该单元非常灵活。如果系统控制需要，它可以对新的计算进行缓冲，直到功能单元完成，控制甚至可以提供一个缓冲器。 While the unit is working on a calculation, it keeps its state in internal registers. If start = 1 at a clock tick, the unit will load the input data and initialize the internal registers. That has the effect of abandoning the calculation that was in progress. There is no additional overhead in starting the new calculation. This behavior makes the unit very flexible. If the system control wants, it can buffer the new calculation and wait until the functional unit has finished, and the control could even provide a buffer.
* 然而，当有指令级并行时，**流水线发出的指令可能会变成不必要的指令，这取决于条件性分支的走向，是很有用的。这种功能单元允许开始进行推测性计算，但如果控制装置确定不需要这个结果，它就可以开始新的计算而不受到惩罚**。相反，如果功能单元被设计成承诺完成它所启动的一切，而计算有时需要许多时钟周期，那么在推测性计算中启动它将是潜在的浪费。However, when there is instruction level parallelism, it is useful for the pipeline to issue instructions that might turn out to be unnecessary, depending on which way conditional branches go. This kind of functional unit allows a speculative calculation to start, but if the control determines that this result is not needed, it can start a new calculation with no penalty. In contrast, if the functional unit were designed so that it commits to completing everything it starts, and the calculations sometimes required many clock cycles, it would be potentially wasteful to start it on a speculative calculation. 

---

一个功能单元是一个顺序电路，用于计算一个或多个参数的函数。功能单元不使用ALU，可以与处理器流水线并行运行，处理器中可能有几个功能单元。一个功能单元可能需要若干个时钟周期来产生其结果，而这个数字可能取决于参数的值。该单元将参数作为输入，同时也接受一个启动控制信号。它产生的结果和一个就绪控制信号作为输出。A functional unit is a sequential circuit that calculates a function of one or more arguments. The functional unit does not use the ALU and can operate in parallel with the processor pipeline, and there may be several functional units in the processor. A functional unit may take a number of clock cycles to produce its result, and this number may depend on the values of the arguments. The unit takes the arguments as inputs, and also a start control signal. It produces the result and a ready control signal as outputs.

当start=1时，在下一个时钟点，功能单元锁住输入值并开始计算。当它完成计算结果时，它把这个结果放在输出信号上，同时把ready输出设置为1。When start=1, then at the next clock tick the functional unit latches the input values and starts its calculation. When it has finished calculating the result, it places this on the output signal and also sets the ready output to 1. 
如果在计算过程中start=1，功能单元放弃它可能正在进行的任何计算，锁住输入，开始新的计算。这使得它可以用于推测性的计算，如果发现没有必要，可以放弃。功能单元适合于在组合逻辑中需要较长路径深度的计算（这将导致较慢的时钟速度），也适合于根据数据值的不同而花费不同时间的计算。一般来说，ALU用于与加法有关的运算，而功能单元则适用于更复杂的运算，如乘、除、浮点和三角函数。If start=1 during a calculation, the functional unit abandons any calculation it may have been performing and latches the inputs, beginning a new calculation. This enables it to be used for a speculative calculation that may be aborted if it is found to be unnecessary. A functional unit is appropriate for calculations that would require a long path depth in combinational logic (which would result in a slower clock speed), and for calculations that take a variable amount of time depending on data values. In general, an ALU is used for operations related to addition, while a functional unit is useful for more complex operations like multiplication, division, floating point, and trigonometric functions.

# 超标量控制-scoreboard：Superscalar control

- 有了所有这些并行性，控制就变得至关重要！。•	With all this parallelism, control is critically important!

- **系统必须确保结果是正确的，同时允许尽可能多的并行性**•	The system must ensure that results are correct while allowing as much parallelism as possible

- **集中控制**（记分板Scoreboarding算法）：**控制算法分析指令流并安排功能单元执行这些指令**。 Centralised control (scoreboarding): the control algorithm analyses the stream of instructions and schedules the functional units to perform them
  - 例如，CDC 6600和后继者
- **分布式控制：控制分布在各子系统中**（流水线、功能单元、信号）。Distributed control: the control is distributed among the subsystems(pipeline, functional units, signals)
  - 例如：IBM 360/91和后续产品

# IBM system/360 Model 91

![](/static/2022-04-28-17-49-48.png)

第一个Tomasulo算法实现(- 该算法适用于任何具有多个执行单元的计算机)The algorithm is applicable to any computer with multiple execution units

- 高性能机器：在所有设计方面有新的发展•	High-performance machine: with novel developments in all design aspects
  - 我们专注于数据通路和控制–	We focus on datapath and control
- **流水线式处理器**•	Pipelined processor
  - **执行阶段有两个独立的区域（定点、浮点**）。–	Execution stage has two independent areas (fixed-point, floating-point)
- **超标量处理器（单发射）：功能单元并行运行**•	Superscalar processor (single-issue): functional units operate in parallel
  - **快速运算（类似加法运算）由ALU在一个周期内完成**–	Fast operations (add-like operations) performed in one cycle by ALU
  - **慢速运算（乘法、除法）需要几个时钟周期，由一个专门的功能单元处理**–	Slow operations (multiplication, division) require several clock cycles and are handled by a dedicated functional unit
- <font color="deeppink">流水线不会暂停等待一个功能单元的操作完成</font>•	The pipeline doesn't stall waiting for an operation to finish in a functional unit
  - 相反，流水线会向一个**可用的功能单元**发出一个操作 –	Instead, the pipeline issues an operation to an available functional unit

# IBM360 Model 91 数据通路

![](/static/2022-04-28-17-55-48.png)
![](/static/2022-04-28-18-22-01.png)

* 设计中有两个执行单元(function unit)，浮点加法单元和浮点乘法/除法单元。
* 有三处寄存器堆(register file)：Floating-point buffers(FLBs)，Floating-point registers(FLRs)，Store data buffers(SDBs)。

# 源汇：Source & sink

- 源：提供数据（与M1术语中的源相同）•	Source: provides data (same as source in M1 terminology)
- 汇：接收数据（在M1中称为目的地）•	Sink: receives data (called destination in M1)

在指令adr F2,F4中 # (F2 := F2 + F4)•	In the instruction	adr F2,F4	# (F2 := F2 + F4)

- F2被称为 "汇"，尽管它也是第一个操作数的源。–	F2 is called the “sink”, although it is also the source of the first operand
- F4被称为 "源"。–	F4 is called the “source”

**加法器（以及所有功能单元）的顶级通路包含缓冲寄存器**•	The top line of the adder (and all functional units) contains buffer registers

![](/static/2022-04-28-18-02-36.png)

- SINK将是第一个操作数（R2的内容）。–	SINK will be the first operand (contents of R2)
- SOURCE是第二个操作数（R4的内容）。–	SOURCE will be the second operand (contents of R4)

# 数据通路-组件单元：Basic datapath :units

**指令单元（或取数单元**）：来源•	Instruction unit (or fetch unit): source

- 执行中央控制功能，使...–	Implements central control functions, making…
  - **多路复用器，将正确的数值放在信号上（"传输路径"**）。•	Multiplexers to put the right values on the signals (“transfer paths”)
  - **子系统（"功能单元"、"寄存器"）来执行局部操作**•	Subsystems (“functional units”, “registers”) to perform local operations
  - 指令单元(Instruction uint)将所有指令译码并将浮点单元指令依次存入浮点操作栈(Floating-point operand stack，FLOS)，在浮点处理单元FPU中进行二次译码，并依据指令类型将指令发射到不同的执行单元。

**执行单元：源和汇**•	Functional units: source and sink

- **加法器和乘法器/除法器**：可以有任何数量的这些东西–	Adder and multiplier/divider: there could be any number of these
- **缓冲寄存器**：接收来自**流水线的操作数**（"汇"、"源"）。–	Buffer registers: to receive operands from pipeline (“sink”, “source”)
- **结果缓冲器：保存结果**，直到它可以被送走–	Result buffer: holds the result until it can be sent away
- **组合控制逻辑**–	Combinational control logic

**存储控制器**：源和汇•	Storage controllers: source and sink

- **两个单元来管理内存和I/O数据通道**–	Two units to manage memory and I/O data channels

# 数据通路-寄存器：Registers

![](/static/2022-04-28-18-10-22.png)

- **浮点运算栈（FLOS）：源**•	Floating-point operantion stack (FLOS): source
  - **暂时存放指令单元发出的指令的寄存器**–	Registers temporarily holding instructions issued by the instruction unit
  - 指令单元(Instruction uint)将所有指令译码并将浮点单元指令依次存入浮点操作栈(Floating-point operand stack，FLOS)，在浮点处理单元FPU中进行二次译码，并依据指令类型将指令发射到不同的执行单元。

- **浮点寄存器（FLR）：源和汇**•	Floating-point registers (FLR): source and sink
  - **用于浮点数据的寄存器文件**（数据路径中未显示整数的RF）。–	Register file for floating point data (RF for integers not shown in datapath)

- **浮点缓冲器（FLB）：源**•	Floating-point buffers (FLB): source
  - **保存来自内存的数据的寄存器，直到处理器可以使用它们**–	Registers holding data from memory until processor can use them
  - **处理器不需要等待数据到达（可以不按顺序到达**）。–	The processor doesn't wait for the data to arrive (can arrive out-of-order)
  - 当数据从存储器中取出，通过总线加载到 FLBs 的寄存器中。

- **存储数据缓冲区（SDB）：汇**•	Store data buffers (SDB): sink
  - **暂时保存来自处理器的数据的寄存器，将被发送到内存中**–	Registers that temporarily hold data from processor to be sent to memory
  - 当数据需要存入存储器，数据先被存进 SDBs 寄存器中，外部部件访问 SDBs 获得数据存入存储器中。

# Tomasulo算法：Tomasulo's Algorithm

![](/static/2022-04-28-18-36-55.png)

---

目标•	Goals

- **保留指令流中的基本优先级**–	Preserve essential precedences in the instruction stream
- **最大限度地实现独立指令的同时执行（IPC ↑**）。–	Maximise simultaneous execution of independent instructions (IPC ↑)

要求•	Requirements

- **识别依赖性的存在（即RAW危险**）。–	Recognise the existence of a dependency (i.e. RAW hazards)
- **促成依赖性指令的正确排序**–	Cause the correct sequencing of the dependent instructions
- **允许独立指令提前执行（乱序执行**）。–	Allow independent instructions to proceed ahead (out-of-order execution)

# Busy bit(繁忙位-保留指令流基本优先级Preservation of precedence

想法：**允许流水线以全速向功能单元发出指令，除非这将产生不正确的结果**•	Idea: allow the pipeline to issue instructions at full speed to the functional units unless this would produce incorrect results

- 问题：如果一个操作已经开始，它的结果将写入一个寄存器，而随后的指令读取该寄存器，它将得到错误的值。然而，如果一条指令的所有操作数都在操作数寄存器中，指令应该继续执行。The problem: If an operation has started which will write its result into a register, and a subsequent instruction reads that register, it will get the wrong value. However, if all the operands for an instruction are present in the operand registers, the instruction should go ahead.
  - 解决办法。当流水线向一个功能单元发出操作时，它将目标寄存器标记为忙。**当流水线遇到一个或两个操作数都很忙的指令时，它就会暂停，但如果两个操作数寄存器都不忙，指令的操作就会被发布到一个功能单元。当一个功能单元完成并将其结果发送到目标寄存器时，该寄存器被标记为不忙。这确保了正确的执行，并且仍然允许一些并行执行**。The solution: When the pipeline issues an operation to a functional unit, it marks the destination register as Busy. When the pipeline encounters an instruction where one or both operands are busy, it stalls, but if both operand registers are not busy the instruction’s operation is issued to a functional unit. When a functional unit finishes and sends its result to the destination register, that register is marked as Not Busy. This ensures correct execution and still allows some parallel execution.
- <font color="deeppink">如果数据的依赖性会导致不正确的结果，则让流水线暂停</font>。–	Stall the pipeline if a data dependency could cause incorrect result

- **每个浮点寄存器**（如F0）包含•	Each floating-point register (e.g. F0) contains
  - 一个浮点值–	A floating point value
  - 一个**忙位**–	A busy bit
  - **一些用于转发的控制位**–	Some control bits used for forwarding

**如果忙位为0`busy=0`，则该寄存器(FLR)包含一个有效的浮点数值**•	If the busy bit is 0, the register contains a valid floating point number

**如果忙位为1 `busy=1`，寄存器处于 "忙 "状态**•	If the busy bit is 1, the register is “busy”

- 它正在**等待一个功能单元的输出值**–	It is waiting the value from a functional unit
  - 或者等存储Buffer的值？？？？
- 其余的位都是垃圾–	The rest of the bits are garbage

# 忙位-指令发射：Issuing an instruction

- **如果 "汇 "寄存器繁忙，流水线停顿**。•	If the “sink” register is busy, the pipeline stalls
  - 没改进情况下会stall

- **如果 "源 "寄存器（非汇）繁忙，则向功能单元发出指令**•	If the “source” register (not sink) is busy, instruction issued to functional unit
  - 控制位被设置为**在数据值到达时将其转发给功能单元** –	Control bits are set to forward data value to functional unit when it arrives
  - 例子：**指令单元发出`mdr F2,F4`** •	Example: instruction unit issues	mdr F2,F4
    - 如果F2不忙，它的内容被传送到乘法器（对F4也一样）–	If F2 is not busy, its contents is transmitted to the multiplier (same for F4)

- **如果有数据危险，流水线会停顿，否则会继续进行**•	The pipeline stalls if there is a data hazard, otherwise it proceeds

- **决定（停顿或继续）是基于寄存器的状态（忙位**）。•	The decision (stall or proceed) is based on the state of the registers (busy)
  - 而不是基于比较指令中的字段（就像在正常的流水线中）。–	Not on comparing fields in the instructions (like in normal pipelining)
    - 之前是在比较某一指令流里前后两个指令的src,des

# 忙位-指令执行：executing the instruction

- **当一个功能单元收到它的两个操作数后**，它就会启动,**执行指令**•	When a functional unit has received its two operands, it fires
  - 就是说，它执行指令–	That is, it executes the instruction

- **当它完成后（可能是几个周期后），它将结果传送回目的寄存器**•	When it finishes (perhaps several cycles later) it transmits the result back to the destination register

- **目标寄存器加载结果并将其繁忙位设置为0**•	The destination register loads the result and sets its busy bit to 0

- **如果流水线在这个寄存器上暂停，它将在下一个时钟位时恢复**。•	If the pipeline was stalled on this register it will resume at the next clock tick

# 忙位策略局限：Limitations of busy bits scheme

没保留站，如果有数据依赖，暂停流水线 【【如果执行单元不可用也得停？

这个计划很容易满足前两个要求•	This scheme easily meets the first two requirements

- 识别依赖关系–	Recognise dependencies
- 正确安排依赖性指令的顺序–	Correct sequencing of dependent instructions

- 第三个要求（独立指令进行,**允许独立指令提前执行（乱序执行））需要编译器的帮助**•	Third requirement (independent instructions proceed) requires compiler help
  - **为了使独立指令重叠，必须使用不同的寄存器**–	To overlap independent instructions different registers must be used

# 无CDB（保留站）例子

在下面的指令序列中，请说明哪些操作可以立即发给一个功能单元，哪些操作会使流水线停滞，以及为什么。 【【题目没提到CDB就是未改进的model，，，IBM 360 Model 91

```
sub R3,R4 
div R1,R2 
div R5,R3 
sub R1,R5
```

* 可以将R3和R4的内容发给加法器，因为两个寄存器都不忙。R3被标记为忙。
* 第一条除法指令可以将R1和R2发给除法器，因为它们都不忙（没有数据依赖）。
* **第二条除法指令现在发现R3是忙的，所以它停顿下来。这就保证了程序的正确执行**
  * 流水线必须暂停，不准进行任何步骤，没有保留站
  * 数据依赖
* 当第一条sub指令完成后，它将结果加载到R3中，然后流水线可以向除法器发出第二条除法指令，也将R5标记为忙。
* 第二条sub指令因为R5繁忙而停顿，当第二次除法完成后，sub指令可以继续进行。**因此，繁忙位机制通过暂停流水线来确保数据的依赖性，同时允许独立的计算继续进行**。

---

IBM 360 Model 91（如Tomasulo论文中讨论的）。假设该架构有繁忙位，但没有公共数据总线. 请说明哪些操作可以立即发给功能单元，哪些操作会使流水线停滞，以及原因

```
add	R1,R2
mul	R3,R4
mul	R5,R1
add	R3,R5
```

* 加法指令可以将R1和R2的内容发给加法器，因为两个寄存器都不忙。R1被标记为忙。
* 第一条mul指令可以将R3和R4发给乘法器，因为它们都不忙（没有数据依赖性）。
* 第二条mul指令现在发现R1是忙的，所以它停顿下来。这就保证了程序的正确执行。
* 当第一条加法指令完成后，它将结果加载到R1中，然后流水线可以发出第二条mul指令到乘法器中，同时将R5标记为忙。
* 第二条加法指令就会因为R5繁忙而停顿下来，当第二次乘法完成后，加法就可以继续进行。
* 因此，**繁忙位机制通过停滞流水线来确保数据的依赖性，同时允许独立的计算继续进行**。

# ====================

# 改进-保留站：Reservation stations

> 问题是：**每个功能单元都有锁存器来保存操作数，当两个操作数都在时，单元就会启动**。当流水线向一个功能单元发出只有一个操作数的操作后，该单元必须等待，直到另一个操作数被送达。因此，一些功能单元可能会被闲置，这是有成本的。降低成本的一个方法是减少功能单元的数量，**但如果所有的功能单元都很忙，这将使流水线暂停**。The problem: Each functional unit has latch registers to hold operands, and the unit starts when both operands are present. After the pipeline has issued an operation with only one operand to a functional unit, the unit must wait until the other operand is delivered. Therefore a number of functional units may be idle, which is costly. One way to reduce the cost would be to reduce the number of functional units, but that would stall the pipeline if all the functional units are busy.
> 解决办法。**每个功能单元有三个保留站**。预留站是一组锁存器，用于保存未来操作的操作数，**当一个站有所有操作数时，它就会向功能单元发出信号，让其在可用时启动**。重点是，**保留站等待操作数，但功能单元不需要等待【就是流水线不必暂停**。如果任何保留站都准备好了。The solution: Each functional unit has three reservation stations. A reservation station is a set of latch registers to hold the operands for a future operation, and when a station has all operands present it signals the functional unit to start when it becomes available. The point is that the reservation stations wait on operands, but the functional unit doesn’t need to wait if any reservation station is ready. 

![](/static/2022-04-29-17-29-24.png)

- 问题：第二个加法和乘法不能并行执行。•	Problem: second add and multiply cannot be executed in parallel!
  - 只有一个FLR
- 即使它们使用不同的汇（没有一个操作数是忙的）。–	Even though they use different sinks (none of the operands are busy)
- 这类似于流水线上的结构性危险–	This is similar to a structural hazard in a pipeline

---

- 解决方案1（成本高）：增加一个或多个加法器•	Solution 1 (costly): addition of one or more adders
- 额外的加法器只在一小部分时间内使用–	The extra adders would be in use only a small part of the time

- **解决方案2（更便宜）：使用保留站对寄存器重新命名**•	Solution 2 (cheaper): register renaming using reservation stations
  - **为功能单元增加额外的缓冲寄存器（控制、汇、源**）。–	Add extra sets of buffer registers (control, sink, source) to functional units
  - **当一个功能单元的一个保留站有两个操作数出现时，该功能单元就会启动**。–	A functional unit fires when one of its reservation stations has both operands present
  - **这就把等待和计算这两个任务分开了**–	This separates the two tasks of waiting and calculating
  - <font color="deeppink">当 FLOS 发射一条指令到执行单元时，会给指令分配一个保留站，并检查该指令需要的源操作数是否可用，如果源操作数存在于 FLRs，FLRs中对应寄存器的值将会复制到保留站中去。如果不存在于 FLRs，将会将一个 标签(Tag)送到保留站中去</font>，例如某条指令的操作数，来自于另一条指令的目的寄存器，这样该操作数就是待定状态，将Tag写入保留站。

---

- **乘法被发出（汇F0不忙），随后，当E的值从内存中到达（乘法单元）时，乘法器开始工作**。•	The multiply is issued (the sink F0 is not busy), and later the multiplier starts when the value of E arrives from memory
- **第一个加法，adr，发给一个保留站（F0忙，F2非忙**）。•	The first add, adr, is issued to a reservation station (F0 is busy, but not F2)
- **第二个加法，ad，被发给另一个保留站**•	The second add, ad, is issued to another reservation station
  - <font color="deeppink">如果A在乘法结束前从内存到达，第二个加法可能在第一个加法之前执行（失序执行</font>）。–	If A arrives from memory before the multiply finishes, the second add may execute before the first one (out-of-order execution)

# 保留站例子2

![](/static/2022-04-29-17-49-19.png)

- 问题1：代码包含潜在的**并行性**，但**被繁忙位所抑制**•	Problem: the code contains potential parallelism that is inhibited by busy bits

- 问题2：Loop1包含例3a的代码
  - Loop1的每一次迭代都是独立的（因为它对数组的第i个元素进行操作），但繁忙位阻止了并行性 Each iteration of Loop1 is independent (because it operates on the ith elements of arrays), but busy bits prevent parallelism
- 解决方案。**Loop2使循环平行，展开Loop1（使用不同的汇**）。•	Solution: Loop2 makes the loop parallel, unrolling Loop1 (uses different sinks)
  - ![](/static/2022-04-29-17-51-46.png)
  - 编译器循环展开

# CDB公共数据总线：Common Data Bus (CDB), Difficulties with busy bits

> 问题是：一个繁忙的位可能会使一条指令的流水线停滞，即使后面的指令可能已经准备好了所有的操作数。这意味着，潜在的并行性将被丢失。The problem: A busy bit could stall the pipeline on one instruction even though the following instructions might have all operands ready. This means that potential parallelism would be lost.
> * 忙位方案可以保持正确的执行，但它是通过在寄存器等待结果时将其标记为忙位来实现的，进一步写入寄存器的指令会被停滞。**这不仅阻止了冲突的指令，也阻止了所有后续指令**。CDB避免了这种情况，因为结果可能会绕过寄存器文件。 The busy bit scheme maintains correct execution, but it does this by marking a register as busy when it is waiting on a result, and further instructions that would write to the register are stalled. This not only stops that conflicting instruction, it also blocks all subsequent instructions. The CDB avoids this as the results may bypass the register file. 
>
> 解决方案。当一条指令被发出时，目标寄存器被标记为忙，但有一个标签表明将执行操作的功能单元，其结果将进入该寄存器。**当功能单元完成一个操作时，它将结果和单元的标签一起在公共数据总线上广播。每个寄存器、存储缓冲器和保留站都会监听CDB，如果它正忙于等待一个与CDB上广播的（标签，值）相匹配的标签，该寄存器会锁住该值，并被标记为不忙**。实际上，这导致保留站动态地构建一个操作的有向无环图，**当所有操作数准备好时，这些操作就会启动。不再需要等待一个操作数在寄存器中可用**。The solution: When an instruction is issued, the destination register is marked as busy but with a tag indicating the functional unit that will perform the operation whose result will go into that register. When the functional unit completes an operation, it broadcasts the result, along with the unit’s tag, on the Common Data Bus. Every register, storage buffer, and reservation station listens to the CDB, and if it is busy waiting on a tag that matches the (tag, value) broadcast on the CDB, that register latches the value and is marked as not busy. In effect, this causes the reservation stations to construct dynamically a directed acyclic graph of operations, which fire when all operands become ready. It is no longer necessary to wait on an operand becoming available in a register.
> CDB的目的是将数据从产生数据的单元直接传送到所有需要它的单元，而不需要首先将数据加载到指定数据值的指令所指定的目标寄存器中。这允许通过直接从一个功能单元向下一个功能单元传输结果来计算一连串的操作（例如，几个因子的乘积），绕过了寄存器文件。**除了节省锁存和访问寄存器文件中的数据所需的时间外，它还避免了当同一寄存器被用于冲突的操作时需要停顿**。The purpose of the CDB is to transmit data from the unit that produces it directly to all units that require it, without needing first to load the data into the destination register specified by the instruction that specified the data value. This allows chains of operations (e.g. the product of several factors) to be calculated by transmitting results directly from one functional unit to the next, bypassing the register file. In addition to saving the time required to latch and then access the data in the register file, it avoids the need to stall when the same register is used for conflicting operations.


- **忙位集中在寄存器上，以及它们是否包含有效数据**•	Busy bits concentrate on the registers and whether they contain valid data
  - 这使得该方法对机器语言程序使用寄存器的方式很敏感–	This makes the approach sensitive to the way the machine language program uses the registers
    - 需要编译器辅助，，

- 程序的全部重点是计算表达式•	The whole point of the program is to compute expressions
  - 寄存器只是达到目的的一种手段–	The registers are just a means to an end

- 解决方案：**专注于数据值，而不是包含它们的寄存器**•	Solution: focus on the data values, not the registers that contain them!
  - 这个想法导致了**公共数据总线**的出现•	This idea leads to the Common Data Bus

---

> 执行单元的计算结果广播到 CDB上，**保留站里需要该结果的指令，会从 CDB 上接收该数据，FLR 和 SDB 如果是该计算结果的目的地，它们同样也会从 CDB上接收该数据**。CDB的使用使得可以直接将之前指令运行的结果送到需要使用该结果的指令那去，而不需要再去访问寄存器。同时寄存器也完成了更新。
> 
> 如果一条指令的操作数是来自存储器，访存完成之后，数据将会被送到 FLBs，**FLBs也可以将数据广播到 CDB上去，在保留站中等待该数据的指令也会接收该数据得到操作数**。
> 
> **执行单元和FLBs可以驱动数据发送到 CDB上去，FLRs 和 SDBs可以从CDB上接收数据**。

- 理念•	Ideas
  - 向功能单元发出操作（即使不知道数据）。–	Issue operations to functional units (even if the data is not known)
  - 将数据值从它们创建的地方发送到需要它们的地方–	Send data values from wherever they create to wherever they are needed

- 我们需要一个 "东西 "来**承载数据值**：**公共数据总线**•	We need a “something” to carry the data values: the common data bus

- 我们需要一种**识别数据值**的方法：**标签**•	We need a way of identifying the data values: tags
  - 把标签看成是一个值的名称而已–	Think of a tag as just a name for a value
  - 标签是作为**产生该值的单元的ID号**来实现的•	Tags are implemented as an id number for the unit that originates the value

# CDB结构：Structure of CDB

![](/static/2022-04-29-18-06-38.png)

- **源**（可以**发送数据到CDB**）：所有可以改变寄存器的来源•	Sources (can write values): all sources that can alter a register
  - **FLB**（来自内存的数据）–	FLB (data coming from memory)
  - **来自所有功能单元的结果**–	Results from all of the functional units
- **目标（可以从CDB读取值）：所有可以从寄存器接收数据的单元**•	Destinations (can read values): all units that can receive data from a register
  - 所有**保留站的操作数（包括源和汇**）。–	Operands of all the reservation stations (both source and sink)
  - **FLR（浮点寄存器**）–	FLR (the floating point registers)
  - **SDB（用于向内存写入的缓冲寄存器**）–	SDB (the buffer registers for writing to the memory)

- **总线控制**：<font color="red">几个源可能同时请求在CDB上写入数据</font>•	Bus control: several sources may request to write at the same time on CDB
  - 总线控制选择一个赢家，并延迟其他的。–	The bus control chooses a winner, and delays the others

# CDB-指令发射：Issuing an instruction

![](/static/2022-04-29-19-40-13.png)

- 如果源（操作数）不忙•	If the operands are not busy
  - 这些**值被发送到保留站**–	The values are sent to the reservation station
  - **汇被设置为忙，汇寄存器标签被设置为保留站的ID**–	The sink is set to busy and its tag is set to the id of the reservation station
  - 当FLOS发射一条指令时，**源操作数使用 FLRs 中的寄存器（src非忙），将其数值复制到保留站中，同时将目的寄存器对应的 FLR 寄存器中的 Busy 置1，表示该寄存器现在是一个待定的，待更新的，同时将该指令所在【保留站编号作为 tag 放到目的寄存器的 tag】 位置，指定该待定寄存器的来源**
  - 如果下一条指令被 FLOS 发射，会检查**两个源操作数寄存器**的 Busy 位，如果 Busy 位为0，直接将该寄存器的值复制到保留站，**如果 Busy 位为1，表示该寄存器的结果还没计算完毕，将寄存器旁边的 tag 复制到保留站，保留站在后续会根据 tag 值接收计算完毕的操作数**。

- 如果汇是忙的•	If the sink is busy
  - **忙位为1，指令被发出，流水线继续进行(没有停顿**)–	Busy bit is 1, the instruction is issued and the pipeline continues (no stall)
  - **汇标签（第一个操作数）被发送到保留站**–	The tag of the sink (first operand) is sent to the reservation station
  - **忙位保持为1，但汇寄存器中的标签被改变为保留站的ID号**–	The busy bit remains 1, but the tag in the register is changed to the id number of the reservation station

# CDB-功能单元完成: when a functional unit finishes

- 它请求在CDB上写东西【**功能单元请求写入CDB**•	It requests to write on the CDB
  - 请注意，几个功能单元可以同时这样做–	Note that several functional units could do it at the same time
  - 执行单元完成，计算结果写入CDB总线

- **如果CDB是空闲的，它将广播发送一对（标签，数据值**）。•	If CDB is free, it transmits a pair (tag, data value)
  - 标签是保留站的ID号–	The tag is the id number of the reservation station

- 每个可以**从CDB读取数据的单元（保留站操作数，FLR，SDB等）都会检查它是否包含一个与传输的标签匹配的标签**•	Every unit that can read from the CDB checks if it contains a tag that matches the transmitted tag
  - 如果**是，该单元从CDB加载数据值，并设置控制位以表示它不忙**。–	If so, the unit loads the data value from the CDB and sets control bits to indicate it is not busy
  - 保留站中等待待定操作数的指令，使用 tag 来监测 CDB 上的数据，一旦监测到自己的 tag 与总线上的 tag 匹配，那就说明待定操作数结果已出，保留站从总线上接收数据

- **有可能几个【目标同时收到该值**】•	It is possible that several destinations receive the value simultaneously

# CDB例子

![](/static/2022-04-29-19-46-40.png)
![](/static/2022-04-29-19-57-20.png)
![](/static/2022-04-29-20-05-49.png)

# CDB文字例子

说明第一次除法的结果如何转发给R5，而直接进入保留站进行第二次除法。

```
div R5,R2,R3; 
div R6,R5,R2; 
sub R5,R2,R2;
```

1. 管道通过将操作数发送到一个**除数保留站**来发出**第一个div**，它将**R5标记为忙**，并给它一个标签，以识别将产生结果的保留站。The pipeline issues the first div by sending the operands to a divisor reservation station, and it marks R5 as busy and gives it a tag identifying the reservation station that will be producing the result

2. 然后流水线发出第二个除法，并将其操作数发送到一个（不同的）保留站；现在R5的内容是标签，而不是实际的数字数据。**特别是，流水线不会因为R5在忙时被取走而暂停**。The pipeline then issues the second div, and sends its operands to a (different) reservation station; the contents of R5 now are the tag, not actual numeric data. In particular, the pipeline does not stall because R5 was fetched while busy.

3. 现在流水线发出sub指令（**第一个除法可能还在运行，因为它只过了两个时钟周期**）。sub指令发出了它的操作数，但是由于减法是一个更快的操作，<font color="red">它的结果将在第二次除法的结果**之前**（甚至可能在第一次除法完成之前）**出现**。这个结果被加载到R5中，R5不再繁忙</font>。 The pipeline now issues the sub instruction (and the first divide is probably still running, as it’s only two clock cycles later). The sub issues its operands, but since the substraction is a faster operation, its result will become available before the result of the second divide (and possibly even before the completion of the first divide).This result is loaded into R5, which is no longer busy.

4. 同时，当**第一次除法完成**后，功能单元在**CDB上广播结果和标签，CDB与所有的保留站、寄存器和存储缓冲器相连**。分配给**第二次除法**的预留站将其第一个操作数的标签与CDB上的标签进行比较，**发现匹配，它就从CDB上加载数据值。这标志着操作数准备就绪，功能单元(第二个div)启动**。因此，架构已经将每个数据值传送到需要它的地方，但第一次除法的结果绕过了R5。  Meanwhile, when the first division finishes, the functional unit broadcasts the result and tag on the CDB, which is connected to all reservation stations, registers, and store buffers. The reservation station allocated to the second division compares its first operand tag with the tag on the CDB, finds a match, and it loads the data value from the CDB. This marks the operand as ready, the functional unit fires. Thus the architecture has transmitted each data value to where it is needed, but the result of the first divide bypasses R5.

---

2，，第一个mul结果如何转发R3，直接给到第二个mul相关的保留站

```
mul R3,R1,R2; 
mul R4,R3,R1; 
add R3,R1,R1.
```

* 管道通过将操作数发送到一个乘法器保留站来发布**第一个mul，它将R3标记为忙，并给它一个标签，以识别将产生结果的保留站**。The pipeline issues the first mul by sending the operands to a multiplier reservation station, and it marks R3 as busy and gives it a tag identifying the reservation station that will be producing the result.
* 然后流水线发出**第二个mul，并将其操作数发送到一个（不同的）保留站**；现在R3的内容是标签，而不是实际的数字数据。 The pipeline then issues the second mul, and sends its operands to a (different) reservation station; the contents of R3 now are the tag, not actual numeric data.
  * 特别是，**流水线不会因为R3在忙时被取走而停滞**。 In particular, the pipeline does not stall because R3 was fetched while busy.
* 现在流水线发出了**add指令**（而第一个乘法可能还在运行，因为它只过了两个时钟周期）。加法指令发出了它的操作数，但是由于加法是一个更快的操作，**它的结果将在第二次乘法的结果之前（甚至可能在第一次乘法完成之前）出现**。 The pipeline now issues the add instruction (and the first multiply is probably still running, as it’s only two clock cycles later). The add issues its operands, but since the addition is a faster operation, its result will become available before the result of the second multiply (and possibly even before the completion of the first multiply)
* 这个结果被加载到R3，R3不再繁忙。同时，当第一次乘法完成后，功能单元在CDB上广播结果和标签，CDB与所有保留站、寄存器和存储缓冲器相连。. This result is loaded into R3, which is no longer busy. Meanwhile, when the first multiplication finishes, the functional unit broadcasts the result and tag on the CDB, which is connected to all reservation stations, registers, and store buffers. 
  * 分配给第二次乘法的预留站将其第一个操作数的标签与CDB上的标签进行比较，发现匹配，并从CDB上加载数据值。这标志着操作数准备就绪，功能单元启动。因此，架构已经将每个数据值传送到需要的地方，但第一次乘法的结果转发了R3 The reservation station allocated to the second multiplication compares its first operand tag with the tag on the CDB, finds a match, and it loads the data value from the CDB. This marks the operand as ready, the functional unit fires. Thus the architecture has transmitted each data value to where it is needed, but the result of the first multiply bypasses R3

# 使用CDB意义->Registers are no longer a bottleneck

- 寄存器的作用就像记号设备，帮助引导数据到需要的地方。•	Registers act like notational devices to help steer the data where is needed
  - 请注意，WAW实际上是一种名称依赖（不是数据依赖）。–	Note that WAW is actually a name dependence (not a data dependence)

- 机器不再服从于程序的字面意思•	The machine no longer obeys the program literally
  - **因为指令可以不按顺序执行** –	Instructions can execute out-of-order

- **一些指令现在可以在零时间内执行**!•	Some instructions can now execute in zero time!
  - 如例2中的LDR F2, F0 –	As LDR F2, F0 in Example 2
  - 简单加法本身需要1周期完成

- **CDB与相联内存（CAM）有关** •	CDB is related to associative memory (CAM)
  - **通过其值而不是地址来访问数据** –	Accessing data by its value rather than its address

# CDB vs busy bit

忙碌位技术确保了正确的排序，如果一个操作可能导致不正确的操作数被用于计算，或者不正确的结果被加载到一个寄存器中，那么忙碌位技术就会使流水线停顿。每个寄存器都有一个控制位，称为 "忙"，它表示（如果是1）该寄存器不包含正确的值，但正在等待一个结果被加载。当一条指令加载到一个寄存器中时，如果数值还不可用（来自内存或功能单元），则该寄存器被标记为忙。当数值变得可用时，它被加载到标记为不忙的寄存器中。The Busy Bit technique ensures correct sequencing by stalling the pipeline if an operation could cause either incorrect operands to be used for a calculation, or for an incorrect result to be loaded into a register. Each register has a control bit called busy, which indicates (if 1) that the register does not contain the right value, but is waiting on a result to be loaded. When an instruction loads into a register, if the value is not yet available (coming from memory or from a functional unit) the register is marked busy. When the value becomes available, it’s loaded into the register which is marked not busy.

在公共数据总线上，一个寄存器不被标记为忙，而是被标记为将提供其值的单元。以后访问该寄存器的指令会得到这个标签值。标签可以在数据通路中移动（从一个寄存器到另一个），但不能对其进行运算。当一个功能单元（或内存）获得一个结果时，它与标签一起被广播，任何持有标签的寄存器都会锁住这个值，使功能单元能够启动。这确保了所有计算的正确进行，但也避免了不必要的流水线停顿。这允许更多的并行性，也使流水线能够越过阻塞的操作，以便能够执行后续指令。 With the common data bus, a register is not marked busy but instead is tagged with the unit that will supply its value. A later instruction accessing the register gets this tag value. Tags may be moved within the datapath (from one register to another) but cannot have arithmetic performed on them. When a functional unit (or memory) obtains a result, it is broadcast along with the tag, and any register holding the tag latches the value, enabling functional units to fire. This ensures that all computations proceed correctly, but it avoids unnecessary pipeline stalls. That allows more parallelism and also enables the pipeline to get past a blocked operation to be able to execute subsequent instructions.

---

保留站是一个具有几组锁存器缓冲寄存器的功能单元，用于保存要执行的计算的表示。它很有用，因为在操作开始之前需要两块信息，而第一块到达的信息需要在寄存器中的某个地方保存，直到第二块到达。在一个完整的功能单元中做这种等待是很浪费的。保留位的好处是将操作数的等待与实际计算分开；它们不能加快执行速度，但可以降低成本（通过允许更有效的功能单元，它们有效地提高了性能/成本比）。繁忙位的问题是，如果操作数需要从繁忙寄存器中取出，它们会使流水线停滞。相比之下，CDB允许一个繁忙的寄存器被移到一个保留站，并有一个标签表明所缺的值是什么。这可以防止流水线停滞不前，当数据可用时，操作将正确进行，并以其标签广播。公共数据总线给程序中出现的每个值都起了一个名字，甚至在相应的数据被知道之前，它就把这些值传给了功能单元。当功能单元产生一个结果时，名称和结果会被广播，这样每个拥有名称的单元都可以用值来代替它。A reservation station is a functional unit with several sets of latch buffer registers for holding a representation of a calculation to perform. It is useful because two pieces of information are needed before the operation can start, and the first to arrive needs to be held somewhere in a register until the second arrives. It is wasteful to do this waiting in a full functional unit. The benefit of reservation stations is to separate the waiting for operands from the actual calculation; they don’t speed up the execution but they reduce the cost (by allowing for more effective functional units, they effectively improve the performance/cost ratio). The problem with busy bits is that they stall the pipeline if an operand needs to be taken from a busy register. In contrast, the CDB allows a busy register to be moved to a reservation station, with a tag indicating what the missing value is. That prevents the pipeline from stalling, and the operation will proceed correctly when the data becomes available and is broadcast with its tag. The common data bus gives a name to each value that occurs in the program, and it passes the values to the functional units even before the corresponding data is known. When the functional unit produces a result, the name and result are broadcast so that every unit that has the name can replace it with the value. 

# CDB vs scoreboard

记分牌在传入的指令流上保持一个移动窗口。它通过查看操作码（决定需要哪些类型的功能单元）和操作数来分析指令的要求。计分板使用未来的指令操作数预测数据的位置，并使用这些信息来安排功能单元之间的通信，并在必要时使流水线停滞。公共数据总线给程序中出现的每个值起一个名字，它甚至在相应的数据被知道之前就把这些值传递给功能单元。当功能单元产生一个结果时，名称和结果被广播出来，这样每个拥有该名称的单元都可以用该值替换它。A scoreboard maintains a moving window over the stream of incoming instructions. It analyses the requirements of the instructions by looking at the opcodes (which determine which types of functional unit will be required) as well as the operands. The scoreboard predicts the location of data using the future instruction operands, and uses this information to schedule communication among the functional units and to stall the pipeline when necessary. The common data bus gives a name to each value that occurs in the program, and it passes the values to the functional units even before the corresponding data is known. When the functional unit produces a result, the name and result are broadcast so that every unit that has the name can replace it with the value.

记分牌是一个比较简单的控制结构，当功能单元所需的时间可以预测时，它非常有效。共同数据总线更复杂，但可以适应功能单元和存储器所需时间的变化。记分牌计算一个静态的时间表并加以实施，而公共数据总线执行动态调度。The scoreboard is a simpler control architecture and is very effective when the time required by functional units is predictable. The common data bus is more complex but can adapt to variations in time required by functional units and the memory. The scoreboard calculates a static schedule and implements it, while the common data bus performs dynamic scheduling. 

# ==========

# 历史影响和当前意义：Historical impact and current significance

- **超标量执行增加了指令级并行性（IPC ↑**）。•	Superscalar execution increases Instruction Level Parallelism (IPC ↑)
  - 使用**多个功能单元并行运行**–	Using multiple functional units operating in parallel

- 今天，一个芯片上可以有数以亿计的晶体管•	Today there can be hundreds of millions of transistors on a chip
  - 高性能的微处理器使用超标量架构–	High performance microprocessors use superscalar architectures

- CDB导致了另一个想法：数据流•	The CDB led to another idea: dataflow

# 数据流：dataflow-beyond the CDB

- **数据流是一种有影响力的计算形式，程序直接指定数据的依赖关系**•	Dataflow is an influential form of computing where the program specifies the data dependencies directly
  - 而不是说数据值在机器中的位置–	Instead of saying where in the machine the data values are
  - CDB如何实现的？？

- **在其最纯粹的形式中，数据流程序是一个图形结构**•	In its most pure form, a dataflow program is a graph structure

- **机器负责执行程序并跟踪数据的位置**•	The machine is responsible for executing the program and keeping track of where the data is

- 这种方法**最大限度地提高了并行性**•	This approach maximises parallelism

- 一些数据流机器已经建成，这些想法影响了现代RISC架构（如超线程）。•	Some dataflow machines have been built, and these ideas have influenced modern RISC architectures (e.g. hyperthreading)

# 执行单元调度：Scheduling functional units

**静态调度：按顺序执行指令**•	Static scheduling: in-order instruction execution

- **流水线（硬件）：只检查2或3条指令的危险检测和转发**。–	Pipelining (hardware): just 2 or 3 instructions are examined for hazard detection and bypassing
- **编译器调度（软件技术）：减少了依赖性的影响，但增加了编译器的复杂性；在编译时的信息也有限**–	Compiler scheduling (software): reduces impact of dependences, but increases compiler complexity; also limited information at compile time

**动态调度：不按顺序执行指令**•	Dynamic scheduling: out-of-order instruction execution

- **记分板（硬件）：检查一个大的指令窗口（10条或更多），并将指令安排到功能单元；没有转发的问题**–	Scoreboarding (hardware): examine a large window of instructions (10 or more) and schedule instructions to functional units; no bypassing
- **Tomasulo算法（硬件）：等待功能单元的结果准备好，然后将它们转发给需要它们的电路**–	Tomasulo algorithm (hardware): wait until results from functional units are ready, and then forward them to the circuits that need them
  - 多个circuit可以同时取值

# ==========