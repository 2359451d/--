# Content

* [Content](#content)
* [改进执行时间：Execution time](#改进执行时间execution-time)
* [方法-减少执行时间:Approach for reducing execution time](#方法-减少执行时间approach-for-reducing-execution-time)
* [硬件并行性：Parallelism in computer systems(hardware)](#硬件并行性parallelism-in-computer-systemshardware)
* [==========](#)
* [电路重定时：Retiming](#电路重定时retiming)
* [重定时缩减关键路径例子](#重定时缩减关键路径例子)
* [流水线：Pipelining](#流水线pipelining)
* [流水线化RRR指令流例子：Pipelining a stream of RRR Instruction](#流水线化rrr指令流例子pipelining-a-stream-of-rrr-instruction)
* [其他例子](#其他例子)
* [完整指令集流水线：Pipeline for full instruction set](#完整指令集流水线pipeline-for-full-instruction-set)
* [流水线冒险：Pipeline Hazards](#流水线冒险pipeline-hazards)
* [结构冒险: Structural hazards](#结构冒险-structural-hazards)
* [数据冒险：Data Hazards](#数据冒险data-hazards)
* [数据冒险分类（3情况）：classification of data hazards](#数据冒险分类3情况classification-of-data-hazards)
* [检测数据冒险：Detecting data hazards](#检测数据冒险detecting-data-hazards)
* [避免数据冒险：Handling data hazards](#避免数据冒险handling-data-hazards)
* [==========](#-1)
* [==========](#-2)
* [==========](#-3)

# 改进执行时间：Execution time

目标：**减少程序的总CPU执行时间**•	Goal: reduce the total CPU execution time of programs

- **I = 指令的数量（ISA，编译器技术**） –	I = number of instructions (ISA, compiler technology)
- **CPI = 每条指令的平均周期数(组织，ISA**) –	CPI = average number of cycles per instruction (organisation, ISA)
- **T = 时钟周期时间(硬件技术，组织**) –	T = clock cycle time (hardware technology, organisation)
- CPU性能方程。	CPU_time = I × CPI × T •	CPU performance equation:	**`CPU_time = I × CPI × T`**
  - **要减少CPU_时间，就必须减少这个乘积**•	To reduce CPU_time it's necessary to reduce this product
    - 不仅仅是乘积的一个组成部分!–	Not just one component of the product!
- <font color="red">有些 "优化 "在减少一个因素的同时增加另一个因素</font> •	Some “optimisations” reduce one factor while increasing another
  - 而且它们实际上会使执行时间恶化 –	And they actually worsen the execution time

# 方法-减少执行时间:Approach for reducing execution time

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
  - 重定时，快速加法器–	Retiming, fast adder
- **指令层面：多个指令同时执行（单处理器**）。•	Instruction level: multiple instructions executed concurrently (uniprocessor)
  - **指令部分重叠：流水线**–	Instructions partially overlapped: pipelining
  - **执行重叠（多个执行单元）：超标量**、VLIW、EPIC –	Execution overlapped (multiple execution units): superscalar, VLIW, EPIC
- **处理器级：使用多个处理器/核心**•	Processor level: use multiple processors/cores
  - 共享/分布式内存多处理器–	Shared/distributed memory multiprocessors
  - 芯片多处理器（多核）、加速器（如GPU、TPU)–	Chip-multiprocessors (multi-cores), accelerators (e.g. GPUs, TPUs)
- **计算机水平：使用多台计算机**•	Computer Level: use multiple computers
  - 分布式系统，集群–	Distributed systems, clusters

# ==========

# 电路重定时：Retiming

> 重定时改变了一个系统的状态表示，但是并不改进它的逻辑行为。通常用它来平衡一个流水线系统中各个阶段之间的延迟

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

![](/static/2022-04-26-02-03-52.png)

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

# 完整指令集流水线：Pipeline for full instruction set

我们已经看到如何为RRR指令引入一个3级流水线•	We've seen how to introduce a 3-stage pipeline for RRR instructions

- **对于RX指令，我们需要更多的阶段** •	For RX instructions, we need more stages
  - 计算有效地址，执行数据内存访问 –	Calculate effective address, perform data memory access
- 因此，**流水线必须有足够的阶段来应对最坏的情况**•	Therefore the pipeline must have enough stages for the worst case
- **所有指令都必须经过相同的阶段序列**•	All instructions must go through the same sequence of stages
  - IF、RF、OP、WR，等等
  - ![](/static/2022-04-26-03-22-39.png)
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

**先读后写相关性（WAR**）。	R1 :=R2+R3; R2 :=R4+R5 •	**Write after Read (WAR**)

- 第一条指令必须获取 R2 的旧值，因此我们必须确保第二条指令在第一条指令获取 R2 之前不会写入 R2。The first instruction must get the old value of R2, so we have to be sure that the second instruction doesn’t write R2 before the first one gets it
- **在所述的RRR流水线中是不可能的（在其他流水线中可能发生**）。–	Impossible in the RRR pipeline described (can happen in other pipelines)

**先写后写相关性（WAW**）。	R2 := result1; R2 := result2 •	**Write after Write** (WAW)

- R2的最终值必须是result2，**但在WAW危险中，它是result1** –	The final value of R2 must be result2 but in a WAW hazard it's result1
  - 可能第二条指令先执行

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

- **暂停**：暂时停止前一阶段的指令进行，为后一阶段的指令完成其工作提供时间 •	**Stalling**: temporarily stop the instruction in an earlier stage from proceeding, giving time for a later stage to complete its work
  - 这在流水线中引入了一个 "气泡"。–	This introduces a “bubble” into the pipeline
  - ![](/static/2022-04-26-03-21-13.png)
- **绕过**：引入一些额外的电路，使流水线能够继续全速运行，不出错。•	**Bypassing**: introduce some additional circuitry that allows the pipeline to continue at full speed, without error
  - 有时一个早期阶段需要一个来自寄存器的值–	Sometimes an early stage needs a value from a register
  - 寄存器还没有收到这个值，但这个值已经存在于机器的其他地方（还没有到达最终目的地）–	The register hasn't yet received that value, but the value exists already somewhere else in the machine (hasn't reached the final destination yet)
  - 我们可以引入一个新的信号，"绕过 "寄存器，将值直接传送到需要的地方。–	We can introduce a new signal that “bypasses” the register, and transmits the value to where it is needed directly
  - 怎么做？我们需要更多的比较器和复用器!–	How? We need more comparators and multiplexers!

# ==========
# ==========
# ==========