# Content

* [Content](#content)
* [指令周期、机器周期和时钟周期](#指令周期机器周期和时钟周期)
* [CPU性能方程-改进执行时间：Execution time](#cpu性能方程-改进执行时间execution-time)
* [方法-减少执行时间:Approach for reducing execution time](#方法-减少执行时间approach-for-reducing-execution-time)
* [硬件并行性：Parallelism in computer systems(hardware)](#硬件并行性parallelism-in-computer-systemshardware)
* [==========](#)
* [电路重定时(加快时钟频率）：Retiming](#电路重定时加快时钟频率retiming)
* [重定时缩减关键路径例子](#重定时缩减关键路径例子)
* [流水线：Pipelining](#流水线pipelining)
* [流水线化RRR指令流例子：Pipelining a stream of RRR Instruction](#流水线化rrr指令流例子pipelining-a-stream-of-rrr-instruction)
* [其他例子](#其他例子)
* [五级流水线-完整指令集流水线：Pipeline for full instruction set](#五级流水线-完整指令集流水线pipeline-for-full-instruction-set)
* [流水线冒险：Pipeline Hazards](#流水线冒险pipeline-hazards)
* [结构冒险: Structural hazards](#结构冒险-structural-hazards)
* [数据冒险：Data Hazards](#数据冒险data-hazards)
* [数据冒险分类（3情况）：classification of data hazards](#数据冒险分类3情况classification-of-data-hazards)
* [检测数据冒险：Detecting data hazards](#检测数据冒险detecting-data-hazards)
* [避免数据冒险：Handling data hazards](#避免数据冒险handling-data-hazards)
* [数据冒险-RAW转发例子:Example of bypassing/forwarding](#数据冒险-raw转发例子example-of-bypassingforwarding)
* [实现转发：Implementing bypassing](#实现转发implementing-bypassing)
* [控制冒险：Control Hazards](#控制冒险control-hazards)
* [流水线效率：Performance of pipelining](#流水线效率performance-of-pipelining)
* [流水线&ISA设计-提高性能：Impact of instruction set on pipelining](#流水线isa设计-提高性能impact-of-instruction-set-on-pipelining)
* [流水线效率局限](#流水线效率局限)
* [==========](#-1)
* [超标量：superscalar](#超标量superscalar)
* [执行单元：Functional units](#执行单元functional-units)
* [超标量控制：Superscalar control](#超标量控制superscalar-control)
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
* [保留站：Reservation stations](#保留站reservation-stations)
* [保留站例子2](#保留站例子2)
* [公共数据总线：Common Data Bus (CDB), Difficulties with busy bits](#公共数据总线common-data-bus-cdb-difficulties-with-busy-bits)
* [CDB结构：Structure of CDB](#cdb结构structure-of-cdb)
* [CDB-指令发射：Issuing an instruction](#cdb-指令发射issuing-an-instruction)
* [CDB-功能单元完成: when a functional unit finishes](#cdb-功能单元完成-when-a-functional-unit-finishes)
* [CDB例子](#cdb例子)
* [CDB文字例子](#cdb文字例子)
* [使用CDB意义->Registers are no longer a bottleneck](#使用cdb意义-registers-are-no-longer-a-bottleneck)
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

# 硬件并行性：Parallelism in computer systems(hardware)

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

# 流水线：Pipelining

> 流⽔线技术是⼀种将每条指令分解为多步，并让各步操作重叠，从⽽实现⼏条指令并⾏处理的技术。程序中的指令仍是⼀条条顺序执 ⾏，但可以预先取若⼲条指令，并在当前指令尚未执⾏完时，提前启动后续指令的另⼀些操作步骤。这样显然可加速⼀段程序的运⾏过程。

---

![](/static/2022-04-26-02-03-52.png)

- <font color="red">每个指令都需要几个时钟周期，因为各步骤之间存在数据依赖关系。【然而，一条指令的一个步骤和下一条指令的前一个步骤之间往往没有数据依赖关系。例如，一条指令可能在执行算术运算的同时，下一条指令访问其寄存器，而后一条指令则从缓存中加载】。理论上最大的改进是4倍，但是流水线的加速系数一般会小于阶段的数量</font>。Each instruction requires several clock cycles, because of data dependencies between the steps. However, there are often no data dependencies between one step of an instruction and an earlier step of the following instruction. For example an instruction might be executing an arithmetic operation in parallel with the following instruction accessing its registers and the instruction after that being loaded from cache. The theoretical maximum improvement would be 4x, but a pipeline will generally give a speedup factor less than the number of stages
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

**先读后写相关性（WAR**）。	R1 :=R2+R3; R2 :=R4+R5 •	**Write after Read (WAR**)

- 第一条指令必须获取 R2 的旧值，因此我们必须确保第二条指令在第一条指令获取 R2 之前不会写入 R2。The first instruction must get the old value of R2, so we have to be sure that the second instruction doesn’t write R2 before the first one gets it
- **在所述的RRR流水线中是不可能的（在其他流水线中可能发生**）。–	Impossible in the RRR pipeline described (can happen in other pipelines)

**先写后写相关性（WAW**）。	R2 := result1; R2 := result2 •	**Write after Write** (WAW)

- R2的最终值必须是result2，**但在WAW危险中，它是result1** –	The final value of R2 must be result2 but in a WAW hazard it's result1
  - 可能第二条指令先执行
- WAW危险是指**两个连续的（或邻近的）指令都将其结果写入同一个寄存器，但第二条指令在第一条指令之前写入其结果；结果是指令顺序被错误地执行**（其效果与省略第二条指令相同）。A WAW hazard is a condition where two consecutive (or nearby) instructions both write their result to the same register, but the second instruction writes its result before the first one does; as a consequence the instruction sequence is executed incorrectly (the effect is the same as omitting the second instruction).
- 解释什么是WAW（写后）流水线危险。解释为什么在ALU中执行所有计算的指令序列中不能发生WAW危险。解释为什么在一个有多个功能单元的处理器中，一个包含由功能单元执行的操作的指令序列可以发生WAW危险。
  - 如果操作是在**ALU**中进行的（例如整数加法），第一条计算只需一个周期，并在第二条计算发生前完成，所以结果将以正确的顺序写入目标寄存器。If the operations are performed in the ALU (integer addition, for example) the first calculation will take only one cycle and will be ready before the second calculation takes place, so the results will be written to the destination register in the correct order.
  - 然而，如果操作是在不同的**功能单元**中进行的，那么第一个操作有可能比第二个操作多花几个周期；在这种情况下，结果可能以错误的顺序写入目标寄存器。有几种技术可以防止这种错误的发生，包括繁忙位、公共数据总线和中央控制分析（记分牌）。However, if the operations are performed in separate functional units it is possible for the first one to take several cycles longer than the second; in this case the results may be written to the destination in the wrong order. There are several techniques for preventing this error from occurring, including busy bits, common data bus, and central control analysis (scoreboard).

:orange: **注意，没有RAR危险**•	Note that there is no RAR hazard

# 检测数据冒险：Detecting data hazards

找到潜在冒险很重要 •	It's essential to find all the potential hazards

我们引入**组合逻辑**来检查它们–	We introduce combinational logic to check them all

- **每个阶段都有一个IR（每个IR都有d、sa、sb字段**）。•	There is an ir for each stage (each ir has d, sa, sb fields)
- 例如：**检测R7 := R2-R3; R6 := R7+R4中的RAW危险 RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)** •	Example: to detect the RAW hazard in	R7 := R2-R3;	R6 := R7+R4 RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)
  - `RAW = (ir2_sa = ir1_d) V (ir2_sb = ir1_d)`
  - RAW=1就是有hazard??????????

# 避免数据冒险：Handling data hazards

![](/static/2022-04-26-03-31-18.png)

- **暂停**：**暂时停止前一阶段的指令进行**，为后一阶段的指令完成其工作提供时间 •	**Stalling**: temporarily stop the instruction in an earlier stage from proceeding, giving time for a later stage to complete its work
  - 这在流水线中引入了一个 "气泡"。–	This introduces a “bubble” into the pipeline
  - ![](/static/2022-04-26-03-21-13.png)
    - 3个nop/bubble完全解决，D取数延迟到周期7
- **旁路/转发**：引入一些额外的电路，使**流水线能够继续全速运行，不出错**。•	**Bypassing/Forwarding**: introduce some additional circuitry that allows the pipeline to continue at full speed, without error
  - ![](/static/2022-04-27-21-42-12.png)
    - 当运算完成后在 Exec/Mem 流水段寄存器中已经存在后面指令可能需要用到的数据，我们<font color="deeppink">可以不等Wr阶段写回寄存器而是在硬件上稍加改动直接将流水段寄存器中的数据送往下一指令的ALU输入端</font>，这种操作称为 转发（Forwarding）或旁路（Bypassing），可以理解为 “抄近道” 了。
  - 有时一个早期阶段需要一个**来自寄存器的值**–	Sometimes an early stage needs a value from a register
  - <font color="deeppink">寄存器还没有收到这个值，但这个值已经存在于机器的其他地方（还没有到达最终目的地</font>）–	The register hasn't yet received that value, but the value exists already somewhere else in the machine (hasn't reached the final destination yet)
  - 我们可以引入一个新的信号，"绕过 "寄存器，将值直接传送到需要的地方。–	We can introduce a new signal that “bypasses” the register, and transmits the value to where it is needed directly
  - 怎么做？我们需要更多的**比较器和复用器**!–	How? We need more comparators and multiplexers!

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

# 超标量控制：Superscalar control

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

在下面的指令序列中，请说明哪些操作可以立即发给一个功能单元，哪些操作会使流水线停滞，以及为什么。

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

# ====================

# 保留站：Reservation stations

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

# 公共数据总线：Common Data Bus (CDB), Difficulties with busy bits

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

说明第一次除法的结果如何绕过R5，而直接进入保留站进行第二次除法。 = =、、什么b问法

```
div R5,R2,R3; 
div R6,R5,R2; 
sub R5,R2,R2;
```

1. 管道通过将操作数发送到一个**除数保留站**来发出**第一个div**，它将**R5标记为忙**，并给它一个标签，以识别将产生结果的保留站。The pipeline issues the first div by sending the operands to a divisor reservation station, and it marks R5 as busy and gives it a tag identifying the reservation station that will be producing the result

2. 然后流水线发出第二个除法，并将其操作数发送到一个（不同的）保留站；现在R5的内容是标签，而不是实际的数字数据。**特别是，流水线不会因为R5在忙时被取走而暂停**。The pipeline then issues the second div, and sends its operands to a (different) reservation station; the contents of R5 now are the tag, not actual numeric data. In particular, the pipeline does not stall because R5 was fetched while busy.

3. 现在流水线发出sub指令（**第一个除法可能还在运行，因为它只过了两个时钟周期**）。sub指令发出了它的操作数，但是由于减法是一个更快的操作，<font color="red">它的结果将在第二次除法的结果**之前**（甚至可能在第一次除法完成之前）**出现**。这个结果被加载到R5中，R5不再繁忙</font>。 The pipeline now issues the sub instruction (and the first divide is probably still running, as it’s only two clock cycles later). The sub issues its operands, but since the substraction is a faster operation, its result will become available before the result of the second divide (and possibly even before the completion of the first divide).This result is loaded into R5, which is no longer busy.

4. 同时，当**第一次除法完成**后，功能单元在**CDB上广播结果和标签，CDB与所有的保留站、寄存器和存储缓冲器相连**。分配给**第二次除法**的预留站将其第一个操作数的标签与CDB上的标签进行比较，**发现匹配，它就从CDB上加载数据值。这标志着操作数准备就绪，功能单元(第二个div)启动**。因此，架构已经将每个数据值传送到需要它的地方，但第一次除法的结果绕过了R5。  Meanwhile, when the first division finishes, the functional unit broadcasts the result and tag on the CDB, which is connected to all reservation stations, registers, and store buffers. The reservation station allocated to the second division compares its first operand tag with the tag on the CDB, finds a match, and it loads the data value from the CDB. This marks the operand as ready, the functional unit fires. Thus the architecture has transmitted each data value to where it is needed, but the result of the first divide bypasses R5.

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