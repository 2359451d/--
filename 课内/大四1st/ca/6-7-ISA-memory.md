# Content

* [Content](#content)
* [Review:三层次架构 Three Aspects of Computer Architecture](#review三层次架构-three-aspects-of-computer-architecture)
* [ISA:硬件-软件接口: Hardware-software interface](#isa硬件-软件接口-hardware-software-interface)
* [ISA设计： Design Considerations](#isa设计-design-considerations)
* [========](#)
* [历史：Historical context](#历史historical-context)
* [CISC vs RISC](#cisc-vs-risc)
* [========](#-1)
* [Instruction Operands](#instruction-operands)
* [操作数数量：Number of Operands](#操作数数量number-of-operands)
* [0-Address指令（堆栈机器）：0-Address Instruction (Stack Machine)](#0-address指令堆栈机器0-address-instruction-stack-machine)
* [0-Address优点&局限](#0-address优点局限)
* [1-Address指令： 1-Address Instructions (Accumulator Machine)](#1-address指令-1-address-instructions-accumulator-machine)
* [1-Address架构优点&局限](#1-address架构优点局限)
* [2-Address指令： 2-Address Instructions](#2-address指令-2-address-instructions)
* [2-Address指令优点&局限](#2-address指令优点局限)
* [3-Address指令：3-Address Instructions](#3-address指令3-address-instructions)
* [3-Address指令优点&局限](#3-address指令优点局限)
* [寄存器&内存：Registers and Memory](#寄存器内存registers-and-memory)
* [不可见寄存器：Invisible Registers](#不可见寄存器invisible-registers)
* [========](#-2)
* [Memory GAP](#memory-gap)
* [内存问题：Memory Problem](#内存问题memory-problem)
* [内存层次结构：Memory Hierarchy](#内存层次结构memory-hierarchy)
* [为什么采用多层次内存：Why is a memory hierarchy effective?](#为什么采用多层次内存why-is-a-memory-hierarchy-effective)
* [M1电路内存：M1 Memory](#m1电路内存m1-memory)
* [交叉存取内存(技术): Interleaved Memory](#交叉存取内存技术-interleaved-memory)
* [缓存：Cached Memory](#缓存cached-memory)
* [缓存组成：Whats in the cache](#缓存组成whats-in-the-cache)
* [缓存命中：Cache hits and misses](#缓存命中cache-hits-and-misses)
* [缓存块：Cache Block](#缓存块cache-block)
* [==============](#-3)
* [缓存映射算法/方式-缓存块位置：Cache Block Placement](#缓存映射算法方式-缓存块位置cache-block-placement)
* [缓存映射-查缓存：Searching the cache](#缓存映射-查缓存searching-the-cache)
* [直接映射缓存：Direct Mapped Cache](#直接映射缓存direct-mapped-cache)
* [全相联映射缓存:fully associative cache](#全相联映射缓存fully-associative-cache)
* [组相联映射缓存：Set associative cache](#组相联映射缓存set-associative-cache)
* [3个对比](#3个对比)
* [CAM内容可寻址存储器: content addressable memory](#cam内容可寻址存储器-content-addressable-memory)
* [==============](#-4)
* [缓存算法(缓存满)](#缓存算法缓存满)
* [缓存写模式（策略）：Cache Write Strategies](#缓存写模式策略cache-write-strategies)
* [缓存写未命中情况](#缓存写未命中情况)
* [多级缓存：Multi-Level Caches](#多级缓存multi-level-caches)
* [Summary缓存问题：Cache Issues](#summary缓存问题cache-issues)
* [========](#-5)
* [虚拟内存:Virtual Memory](#虚拟内存virtual-memory)
* [虚存其他功能：Additional Functions of Virtual memory](#虚存其他功能additional-functions-of-virtual-memory)
* [虚存 vs 缓存：Virtual memory vs cache](#虚存-vs-缓存virtual-memory-vs-cache)
* [内存管理单元MMU-页:Page](#内存管理单元mmu-页page)
* [虚拟内存地址 & 物理内存地址：Virtual and Physical memory](#虚拟内存地址--物理内存地址virtual-and-physical-memory)
* [页表:Page table](#页表page-table)
* [DAT动态地址转换：Dynamic Address translation](#dat动态地址转换dynamic-address-translation)
* [DAT算法](#dat算法)
* [问题：找到空闲物理帧 - finding an empty frame](#问题找到空闲物理帧---finding-an-empty-frame)
* [页面置换算法：Page replacement policy](#页面置换算法page-replacement-policy)
* [DAT页表访问开销：Accessing the page table](#dat页表访问开销accessing-the-page-table)
* [TLB页表缓冲区-解决页表访问开销：Translation Lookaside buffer](#tlb页表缓冲区-解决页表访问开销translation-lookaside-buffer)
* [实际：Further Complications](#实际further-complications)
* [架构OS支持：Architecture must support OS](#架构os支持architecture-must-support-os)
* [中断(OS,硬件)：Interrupts](#中断os硬件interrupts)
* [中断实现：Implementing interrupts](#中断实现implementing-interrupts)
* [PC值保存：Saving the pc](#pc值保存saving-the-pc)
* [用户态: Saving the state](#用户态-saving-the-state)
* [禁止中断指令：Disabling interrupts](#禁止中断指令disabling-interrupts)
* [中断丢失：Missing interrupt](#中断丢失missing-interrupt)
* [内存保护：Basic Memory Protection](#内存保护basic-memory-protection)
* [特权指令：Privileged Instructions](#特权指令privileged-instructions)
* [系统状态-用户/核心态：System State](#系统状态-用户核心态system-state)
* [========](#-6)
* [虚拟机：Virtual Machines](#虚拟机virtual-machines)
* [系统虚拟化实现：Implementing system virtualisation](#系统虚拟化实现implementing-system-virtualisation)
* [========](#-7)

# Review:三层次架构 Three Aspects of Computer Architecture

![](/static/2022-04-24-20-45-18.png)

**指令集架构**（ISA）。程序员/编译器角度•	Instruction Set Architecture (ISA): Programmer/compiler view

- 对（系统）程序员可见的指令–	Instructions visible to the (system) programmer
- 操作码、架构寄存器、地址转换等–	Opcode, architectural registers, address translation, etc

**微架构**。处理器设计者角度•	Microarchitecture: Processor designer view
- **实现ISA的逻辑组织**–	Logical organisation that implements the ISA
- 寄存器、功能单元、缓存、流水线等–	Registers, functional units, caches, pipelining, etc

**电路**。电路/芯片设计者角度•	Circuits: Circuit/chip designer view

- 详细的逻辑设计和封装技术–	Detailed logic design and packaging technology
- 触发器（DFF）、逻辑门、导线等–	Flip flops (dff), logic gates, wires, etc

# ISA:硬件-软件接口: Hardware-software interface

组成部分•	Components

- **数据类型**：int32/uint32, ..., int8/uint8, float32, float64 (16-bit in sigma16) –	Data types: int32/uint32, … , int8/uint8, float32, float64 (16-bit in sigma16)
- **内存寻址模式**：寄存器+偏移量，直接，缩放（sigma16中的r+o）。–	Memory addressing modes: register+offset, direct, scaled (r+o in sigma16)
- **指令类**（操作）–	Instructions classes (operations)
  - 对**数据进行操作**（算术和逻辑）：**add、sub、mul、xor**等（sigma16中的RRR）。•	Operate on data (artithmetic & logic): add, sub, mul, xor, etc (RRR in sigma16)
  - **移动数据**（在寄存器、内存、I/O之间）：加载、存储等（sigma16中的RX）。•	Move data (between registers, memory, I/O): load, store, etc (RX in sigma16)
  - **改变控制流**：分支、跳转、调用/返回等（Sigma16中的RX）。•	Change control flow: branch, jump, call/return, etc (RX in sigma16)
  - **特权和杂项**（不属于应用程序的一部分）•	Privileged and miscellaneous (not part of the application)
    - 因为它们只能在特权状态下执行，但是ISA引入这个概念
- **寄存器：数量，类型**（一般/特殊用途）（16GP，3SP在sigma16中）。–	Registers: number, type (general/special purpose) (16GP, 3SP in sigma16)
  - PC, address register...

ISA编码：每条指令有多少位？•	Encoding the ISA: How many bits per instruction?

- **固定的**：16/32/64 MIPS, arm, 等等 (16 in sigma16)–	Fixed: 16/32/64 MIPS, arm, etc (16 in sigma16)
- **可变的**。英特尔x86–	Variable: Intel x86

# ISA设计： Design Considerations

![](/static/2022-04-24-20-58-53.png)

- **编译器的简单目标**•	Simple target for compilers
- **支持操作系统和编程语言的特点**•	Support for OS and programming language features
- **支持数据类型**（浮点、向量）•	Support for data types (floating-point, vectors)
- **代码大小**•	Code size
- **对执行效率的影响**（例如，用流水线）。•	Impact on execution efficiency (e.g. with pipelining)
- **与传统处理器的后向兼容性**•	Backward compatibility with legacy processors
  - allow you to run new programs, and also are compatible with older versions of the ISA 
- **对扩展的规定**•	Provision for extensions
  -   什么是对扩展ISA的支持，用于多媒体的特殊指令或其他一些目的。？？？？what is the support for extending the ISA for special instructions for multimedia or for some other purposes. 

# ========

# 历史：Historical context

早期的计算机是直接用**汇编语言编程**的•	Early computers were programmed directly in assembly language

- 随着编程语言的发展，一个共同的想法是**为标准编程语言结构提供ISA支持**。•	As programming languages evolved, a common idea was to give ISA support for standard programming language constructs
- 这导致了**复杂指令（CISC**）。–	This led to complex instructions (CISC)
  - 将算术与内存访问相结合•	Combine arithmetic with memory access
  - 包含一个以上的内存访问•	Contain more than one memory access
  - 同时执行几个操作•	Perform several operations at once
  - 对应于高级编程语言中的构造（这种情况下编译器开销减少，因为有直接同等的结构•	Correspond to constructs in high level programming languages
- 前者引入了复杂度，一个新的想法是**只关注成本和性能**•	A new idea was to focus just on cost and performance
  - 使用**简单指令（RISC**）。–	Using simple instructions (RISC)

# CISC vs RISC

<font color="red">RISC架构一般在一条指令中只做一个操作，如算术操作或内存访问。由于CISC指令通常在一条指令中进行多项操作，因此一个程序可能需要较少的CISC指令</font>

程序的执行速度取决于时钟速度和每条指令的平均周期数；它并不取决于指令的数量【感觉这点有点奇怪】CPU=I * CPI*Time... The speed of program execution depends on the clock speed and the average number of cycles per instruction; it does not depend on the number of instructions. 

CISC：复杂指令集计算机•	CISC: Complex Instruction Set Computer

- **复杂指令（HLL特性-high level langugage）=>简单编译器(因为指令本身足够复杂?** –	Complex instructions (HLL features-high level langugage) => simple compiler
  - 指令的语义更接近于高级编程语言。困难但常见的计算由指令来支持  Instructions have semantics closer to high level programming languages. Difficult but common computations are supported by instructions.
  - 减少了编译器设计的复杂度
- **寄存器数量少（过去，内存 "快"）=>内存操作数** –	Small number of registers (memory “fast”) => in-memory operands
  - 寄存器数量少，导致存储临时变量，寄存器不够用情况下，要多次访问内存（将临时结果写入内存），效率低
- **代码大小小，=>每条指令的执行时钟周期数高**–	Code size small => high number of clock cycles per instruction
  - 对象代码更小，因为一条编码指令可以执行多个操作 Object code is smaller, because a single encoded instruction can perform several operations.
  - 每条指令要更长的执行周期
- **复杂而缓慢的解码=>变长指令**–	Complex and slow decoding => variable length instructions
- **向后兼容的=>复杂性随时间增长**–	Backward compatibility => complexity grows over time
  - 如果架构提供可写的控制存储，就有可能根据应用的需要调整指令集 There is the potential for adjusting the instruction set to the needs of the application, if the architecture provides writable control store.

RISC。精简指令集计算机•	RISC: Reduced Instruction Set Computer

- **简单的指令（标准化的）=>复杂编译器(因为简单指令要转换成高级表达**–	Simple instructions (standarised) => complex compiler
  - **但是RISC程序指令数量多**，，程序的大部分时间是在**循环**中度过的，而**指令缓存**使RISC机器几乎能以时钟速度获取指令；**由于这个原因，较多的指令数量并不会导致速度减慢** The crucial point is that programs spend most of their time in loops, and an instruction cache enables a RISC machine to fetch instructions at almost clock speed; for this reason the higher number of instructions does not lead to a slowdown.
  - **需要更多【访存取指令**】，，RISC指令只执行一个操作，与CISC相比，执行一个计算需要更多的指令，有些指令执行几个操作。More memory accesses for instructions: RISC instructions perform just one operation, and more of them are needed to perform a computation than for CISC, where some instructions perform several operations.
  - 需要高速缓存。因此，需要取用更多的RISC指令。**如果没有高速缓冲存储器，这将导致RISC的速度减慢，因为较慢的存储器访问将导致净减慢，而且流水线也不会有效，因为它将经常在存储器上停滞**。幸运的是，指令访问有很大的位置性，因为程序大部分时间都是在**循环**中度过的，这使得缓存存储器能够给指令提供有效的访问时间，从而使流水线的运行速度接近峰值。Need for cache: Therefore a larger number of RISC instructions need to be fetched. Without cache memory, this would cause a slowdown on RISC because the slower memory access would cause a net slowdown, and the pipeline wouldn’t be effective because it would frequently stall on the memory. Fortunately, instruction accesses have a great deal of locality because programs spend most of their time in loops, and that enables cache memory to give an effective access time for instructions that can keep the pipeline operating at close to peak speed.
- **大量的寄存器（访存操作非常慢）=>加载-存储架构**–	Large number of registers (memory very slow) => load-store architecture
  - <font color="red">更少的内存访问数据【访存取数据少】。RISC架构通常有一个较大的寄存器文件，允许在一长串指令中保持更多的变量在快速寄存器中被访问。CISC架构通常有从内存中获取一个或多个操作数的指令，这比寄存器到寄存器的操作要慢。</font>
  - 任何内存操作都需要涉及寄存器，，不能直接操作内存
  - **大的寄存器文件**允许将数据保存在寄存器中，<font color="deeppink">不需要将临时结果写入内存，从而减少了内存访问的次数</font> The large register file allows data to be kept in registers, without the need to write temporary results to memory, thus reducing the number of memory accesses
  - 一个大的寄存器文件允许大量的变量保存在寄存器中，从而减少了所需的加载和存储指令的数量；这一点很重要，因为内存访问比寄存器中的算术慢得多。**一个大的寄存器文件需要更多的时间进行寄存器地址解码，需要更多的比特来指定一个寄存器，这可能会导致更大的指令大小，并产生更多的中断开销，因为寄存器的状态需要被保存和恢复。**  A large register file allows a large number of variables to be kept in registers, thus reducing the number of load and store instructions needed; this is significant since memory accesses are much slower than arithmetic in registers. A large register file requires more time for register address decoding, requires more bits to specify a register which could lead to larger instruction size, and produces more overhead for interrupts as the register state needs to be saved and restored.
    - 其他的局限&优点看L5 寄存器堆大小
- ==============
- **代码大小大 =>每条指令的时钟周期数低**–	Code size large  low number of clock cycles per instruction
  - CPI减少
  - RISC架构的设计是为了**尽量减少时钟周期，并通过使用流水线和其他形式的指令级并行来减少每条指令的周期**  A RISC architecture is designed to minimize the clock period and to reduce cycles per instruction by using pipelining and other forms of instruction level parallelism
- **简单而快速地解码=>固定长度/格式的指令**–	Simple and fast decoding  fixed length/format instructions
  - (RISC）指令具有相同的大小（或只有几个大小），简化了指令的获取。所有指令只经过两到三种执行模式中的一种，这意味着**流水线**可以有一个规则的结构 (RISC) Instructions have the same size (or just a couple of sizes), simplifying the fetching of instructions. All instructions go through one of just two or three patterns of execution, which means the pipeline can have a regular structure. 
  - 指令集可以有一些特点（如延迟分支、寄存器窗口等），**提高流水线的性能或减少内存访问的数量** The instruction set can have features (such as delayed branches, register windows, etc.) that improves performance of the pipeline or reduces the number of memory accesses. 
- **向后兼容更简单**–	Backward compatibility => much simpler
- <font color="red">其他优点</font>
  - 避免了会延长关键路径的操作；指令集被设计成使流水线有效并减少停顿；当流水线停顿时可以进行有用的工作（例如分支延迟槽）. Many reasons can be given that RISC may be faster: operations that would lengthen the critical path are avoided; the instruction set is designed to make pipelining efficient and to reduce stalls; useful work can be done when the pipeline stalls (e.g. branch delay slots).

# ========

# Instruction Operands

# 操作数数量：Number of Operands

ISA可以**按照指令中明确规定的操作数进行分类**•	ISAs may be categorised by the number of operands explicitly specified in instructions

- **0-address结构**。操作数是通过**压栈弹栈**来访问的–	0-address architecture: The operands are accessed by pushing and popping a stack
- **1-address结构**。**只有一个隐含使用的寄存器（AC,累加器），并指定一个内存地址**–	1-address architecture: There is exactly one register, used implicitly, and one memory address is specified
- **2-address结构**。**典型的指令用一个寄存器指定一个操作数和一个内存地址**–	2-address architecture: Typical instructions specify one operand in a register and a memory address
- **3-address结构**。典型的指令**指定两个操作数和一个结果，都在寄存器中**；<font color="red">内存访问是通过特殊的Load和Store指令（如sigma16、MIPS等）。</font> –	3-address architecture: Typical instructions specify two operands and one result, all in registers; memory accesses are via special Load and Store instructions (e.g. sigma16, MIPS, etc)
- **k-address结构**。指令有可变的大小（如英特尔x86）。–	k-address architecture: Instructions have variable sizes (e.g. Intel x86)

# 0-Address指令（堆栈机器）：0-Address Instruction (Stack Machine)

![](/static/2022-04-24-22-04-34.png)

**大多数指令不包含地址**，所以**操作数的位置必须隐含地指定，如寄存器的栈顶**•	Most instructions contain no address, so the locations of the operands must be specified implicitly, as the top of a stack of registers

- 在前面的操作数先压，优先
- `push x`: 从**地址x的内存中获取**一个字，并将其推到寄存器堆栈中。–	push x: fetches a word from memory at address x and pushes it onto the register stack
- `pop x`：从寄存器堆栈中取出一个字，并将其**存储在地址为x的内存**中。–	pop x: pops a word from the register stack, and stores it in memory at address x
- `neg`：从堆栈中取出一个字，对其进行否定，并将结果推回堆栈中。–	neg: pops a word from the stack, negates it, and pushes the result back onto the stack
  - **无访存**
- `add, sub, mul, div`: **弹出堆栈两次**，对两个数据值进行操作，并将结果推到堆栈中。–	add, sub, mul, div: pop the stack twice, performs the operation on the two data values, and push the result onto the stack

# 0-Address优点&局限

- **编译器的标准基本算法与指令集很适合**。•	Standard elementary algorithms for compilers fit well with the instruction set
  - 它们产生逆波兰符号（运算符跟随操作数）。–	They generate reverse Polish notation (operators follow operands)
- **代码紧凑**的•	Code is compact
  - 只包含操作码和数据的地址–	Contains only operation codes and addresses of data
- **硬件（实现指令集的数字电路）必须在寄存器上提供堆栈操作**（pop, push）。•	The hardware (digital circuit implementing the instruction set) has to provide stack operations (pop, push) on the registers
  - 这并不困难–	This is not difficult

---

在一个表达式中**多次使用的变量需要被多次压栈**•	Variables used several times in an expression need to be pushed several times

- **压栈一个已经在快速寄存器（不在堆栈顶部）中的变量（具有缓慢的内存访问）是很浪费的**。–	It is wasteful to push a variable (with a slow memory access) that is already in a fast register (that is not at the top of the stack)
- **优化代码是困难的**–	Optimising code is difficult

# 1-Address指令： 1-Address Instructions (Accumulator Machine)

![](/static/2022-04-24-22-20-57.png)
![](/static/2022-04-24-22-26-21.png)

在许多早期的大型计算机中使用的一种风格，如IBM 7040。•	A style used in many early mainframe computers, such as IBM 7040

- <font color="red">注意写的时候不要打乱顺序。。。下面先做加法是可以优化的，但是括号算术顺序被打乱= = 好像不让这么写</font>
- **有一个寄存器，称为累加器**；因为<font color="red">只有一个，所以指令不需要明确地指定它</font>	There is one register, called the accumulator; since there is only one, the instructions don't need to specify it explicitly
- `Load x`: **从地址x的内存中获取一个字，并将其加载到累加器中(`AC := x`)** –	Load x: fetches a word from memory at address x and loads it into the accumulator (AC := x)
- `Store x`: **将累加器的值存储到内存地址x (`x := AC`)**–	Store x: stores the accumulator value into memory address x (x := AC)
- `Add x`: **从内存地址 x 取出一个字，并把它加到累加器 (`AC := AC + x`)**–	Add x: fetches word from memory at address x, and adds it to the accumulator (AC := AC + x)
  - 。。。加减乘除都是存入AC,但是 加减乘除让带一个直接操作数，，div b就是AC:= 别的操作数 / b
  - <font color="red">先算术操作，，然后AC内存操作</font>
- **乘法和除法**：**使用另一个【隐式寄存器】来保存一个额外的字**，允许双字乘法和除法。–	Multiplication and division: use another implicit register to hold an extra word, allowing for double word products and dividends

# 1-Address架构优点&局限

简单的指令允许用简单的硬件来实现指令集•	Simple instructions allow for simple hardware to implement the instruction set

- **指令很紧凑：只有一个操作码和一个地址**•	Instructions are compact: just an operation code and an address
- **由于只有一个寄存器，所以不可能把所有常用的数据都保存在寄存器中**。•	Since there is only one register, it is impossible to keep all the commonly used data in registers
  - 为了执行一个包含**多次出现x的表达式，x的值需要被多次取走【使程序更慢**–	To execute an expression that contains several occurrences of x, the value of x will need to be fetched several times
- 需要相对较多的指令•	A relatively large number of instructions are required
  - 导致大型目标代码的出现–	Resulting in large object code
- **大量的指令，尤其是大量的内存访问，导致执行速度缓慢**•	The large number of instructions, and especially the large number of memory accesses, result in slow execution

# 2-Address指令： 2-Address Instructions

> 2-address结构。典型的指令用一个寄存器指定一个操作数和一个内存地址– 2-address architecture: Typical instructions specify one operand in a register and a memory address

![](/static/2022-04-24-22-38-24.png)

**许多CISC和RISC机器都属于这一类别**•	Many CISC and RISC machines fall under this category

- 例子：用于IBM 360的寄存器-寄存器操作（CISC）。•	Example: used for the register-register operations of the IBM 360 (CISC)
- 由于一个典型的指令**有两个操作数和一个结果，所以有必要【覆盖】其中一个操作数**•	Since a typical instruction has two operands and a result, it is necessary to overwrite one of the operands
  - `AR 2,3`（"寄存器相加"）在IBM 360上意味着**Reg2 := Reg2 + Reg3** –	AR 2,3 (“add register”) on the IBM 360 means Reg2 := Reg2 + Reg3
  - `MR 2,3`（"移动寄存器"）在IBM 360上意味着**Reg2 :=Reg3** –	MR 2,3 (“move register”) on the IBM 360 means Reg2 := Reg3
  - 【**加载和存储】指令在内存和寄存器之间移动数据**–	Load and store instructions move data between memory and registers
- 也有一些机器使用双地址指令，操作数都在内存中。•	There have also been some machines with 2-address instructions with both operands in memory

# 2-Address指令优点&局限

这种风格是一种折中的方式•	This style is a compromise

- **用来代替3地址方式主要是为了使指令更短**。–	Used instead of 3-address style mainly to keep instructions shorter
- 例如，指令没有足够的位来表示三个寄存器–	E.g. instructions don’t have enough bits to represent three registers

缺点：**大多数指令都覆盖了数据**•	Drawback: most instructions overwrite data

- **如果再次需要某个寄存器中的数据，必须用额外的移动指令将其复制到备用寄存器中**。–	If data in a register is needed again, it has to be copied to a spare register with an extra move instruction
- **这在过去比较常见，当时内存非常昂贵**•	It was more common in the past, when memory was very expensive
  - **人们认为保持指令的小型化很重要**–	It was believed important to keep the instructions small

# 3-Address指令：3-Address Instructions

> 3-address结构。典型的指令指定两个操作数和一个结果，都在寄存器中；内存访问是通过特殊的Load和Store指令（如sigma16、MIPS等）。 – 3-address architecture: Typical instructions specify two operands and one result, all in registers; memory accesses are via special Load and Store instructions (e.g. sigma16, MIPS, etc)

![](/static/2022-04-24-22-44-20.png)

大多数操作**有两个操作数和一个结果，都是明确规定的**•	Most operations have two operands and a result, all specified explicitly

- 算术加减乘除等操作，**不会覆盖某寄存器**
- 在早期的超级计算机（控制数据6600及其后续产品）、现代RISC架构（MIPS和SPARC）和Sigma16中使用•	Used in early supercomputers (Control Data 6600 and its successors), modern RISC architectures (MIPS and SPARC) and Sigma16
- `add R1,R2,R3`：**获取两个源寄存器的内容，执行操作，并将结果放在目标寄存器中（R1 := R2 + R3)**–	add R1,R2,R3: fetches the contents of two source registers, performs operation, and places the result in destination register (R1 := R2 + R3)
- `load R1,x`: **从地址x的内存中获取一个字，并将其加载到寄存器中（R1 := x**）。–	load R1,x: fetches a word from memory at address x and loads it into the register (R1 := x)
- `store R1,x`: **在地址x处存储R1的值 (x := R1**)–	store R1,x: stores the value of R1 memory at address x (x := R1)

# 3-Address指令优点&局限

- **操作数都是寄存器，所以可以紧凑地指定**。•	The operands are all registers, so they can be specified compactly
- **加载/存储指令用于在内存和寄存器之间传输数据**•	Load/Store instructions used to transmit data between memory and registers
  - 它们是**唯一包含内存地址的指令**–	They are the only instructions that contain memory addresses
- **有了【合理数量的寄存器】（通常是32个），就有可能把常用的数据保存在寄存器中**•	With a reasonable number of registers (typically 32 of them), it is possible to keep commonly-used data in registers
  - 这就**减少了内存访问所花费的时间**–	This reduces the time spent on memory accesses
- 通常情况下，3地址指令集的性能最好，因为（1）**多次使用的变量，如a，可以在整个计算过程中留在寄存器中**，（2）不需要临时变量，因为**中间结果可以留在寄存器**中。Usually the 3-address instruction set will perform best, because (1) variables that are used several times, such as a, can be left in a register throughout the computation, and (2) temporary variables are not needed because intermediate results can be left in registers.

# 寄存器&内存：Registers and Memory

正如我们所看到的，**机器语言可以使用各种方法来指定寄存器或内存中的操作数**。•	As we have seen, the machine language can use a variety of methods for specifying operands in registers or memory

- 但底层的数字电路却没有这种自由【【就是操作寄存器比操作内存高效 = =。。。•	But the underlying digital circuit does not have this freedom
  - **直接对内存中的数据进行操作是低效的**，原因有以下几点•	It is inefficient to operate directly on data in memory, for several reasons
    - 一个寄存器文件有几个端口是合理的（比如sa、a、sb、b、 destination），但在一个大的内存中就不行了–	It's reasonable to have several ports to a register file (like sa, a, sb, b, destination) but not in a large memory
    - **内存的层次结构（多级缓存、主、虚拟）会使算术指令的实现复杂化【层次复杂**–	The memory hierarchy (multiple levels of cache, main, virtual) would complicate the implementation of arithmetic instructions
    - 内存（甚至是高速缓存）比寄存器的速度略微慢一些到中等。–	The memory (even cache) is slightly to moderately slower than registers

# 不可见寄存器：Invisible Registers

有些ISA显然提供了**直接对内存中的操作数进行运算的指令**•	Some ISAs apparently offer instructions that perform arithmetic directly on operands in memory

- 例子。IBM 370有一条**ap x,y指令，其中x和y是内存地址**•	Example: IBM 370 has the ap x,y instruction where x and y are memory addresses
  - 意义是**x = x + y** –	Meaning is x = x + y
  - "ap "是加法运算，操作数是可变长度的二进制编码十进制（BCD）。–	“ap” is add-packed, and the operands are variable length binary coded decimal (BCD)
- 为了实现它，**控制算法将操作数加载到【内部寄存器】（机器语言看不到），在那里计算，并将结果存储回来（存储到内存中**•	To implement it, the control algorithm loads the operands into internal registers (invisible to the machine language), calculates there, and stores the result back
  - 实际上还是依靠寄存器完成

# ========

# Memory GAP

![](/static/2022-04-24-23-19-10.png)

问题：gap导致从内存中取数据越来越慢

> 主要的原因是CPU的速度和内存的速度之间严重不匹配，Cpu处理速度极快，而访问内存慢，cache在这个背景下就诞生了。设计人员通过在CPU和内存之间建立一个缓冲区，提高访问的速度。
>   建立cache的好处在于：假设CPU和内存之间没有cache，那么CPU每次访问内存，都要从访问速度较慢的内存中读取，这无疑是很浪费cpu的性能的；但是如果在CPU和内存之间设立一个高速的cache，虽然第一次读，都要从内存中读取，但是第一次读完成之后，可以把数据放到这个高速cache里；那么第二次读，我就直接从高速cache里取数据就行，这个高速cache的速度基本上是和CPU匹配的，比内存速度快很多。这样的，CPU第2次，第3次去读这个数据的时候，就得到加速的效果了。这也是设计cache的最初的初衷。
> cache一般是集成在CPU内部的RAM，相对于外部的内存颗粒来说造价昂贵，因此一般cache是很小的RAM，但是访问速度和CPU是匹配的。此外，如果访问数据在cache命中的话，不仅仅能提速，提高程序的性能，还能降低功耗（cache命中的情况，不需要去访问外部的内存设备，自然会降低功耗）。

# 内存问题：Memory Problem

- **主存比寄存器慢得多，所以内存的加载和存储需要几个时钟周期**•	Main memory is much slower than registers, so memory loads and stores take several clock cycles
- **这意味着内存会拖慢整个系统的速度，除非它被精心设计为良好的性能**。•	This means that the memory will slow down the entire system, unless it is designed carefully for good performance
- **主存速度慢的根本原因是**•	The reasons that main memory is slow are fundamental
  - **内存越大，需要的地址位就越多，增加了解码地址所需的时间（更多的门延迟**）。–	The larger the memory, the more address bits are needed, increasing the time needed to decode addresses (more gate delays)
  - **较大的内存需要更多的物理空间，需要更长的电线，需要更多的时间来传输信号**–	Larger memory takes more physical space, requiring longer wires, which need more time for signal transmission

# 内存层次结构：Memory Hierarchy

![](/static/2022-04-24-23-28-19.png)
![](/static/2022-04-24-23-30-04.png)

- 想法：使用**各种内存类型的组合**•	Idea: use a combination of memory types
  - 接近处理器的**少量快速（昂贵）内存**–	Small amounts of fast (expensive) memory closer to the processor
  - 接近离处理器较远的**较慢（较便宜）的内存**–	Large amounts of slower (cheaper) memory farther from the processor
- 如果**常用的数据在最快的内存中，程序运行得更快**。•	Programs run faster if commonly used data is in the fastest memory
  - registers
- 计算机系统（硬件和软件）的一个重要方面是**管理数据在不同内存中的移动方式**。•	An essential aspect of computer systems (hardware and software) is managing the way data is moved among the different memories

# 为什么采用多层次内存：Why is a memory hierarchy effective?

![](/static/2022-04-24-23-30-04.png)

时间局部性•	Temporal Locality

- **最近访问的内存位置（指令或数据）在不久的将来可能会被再次访问**。–	A recently accessed memory location (instruction or data) is likely to be accessed again in the near future

空间局部性•	Spatial Locality

- **靠近最近访问的内存位置（指令或数据），在不久的将来可能会被访问**。–	Memory locations (instructions or data) close to a recently accessed location are likely to be accessed in the near future

:orange: 为什么程序中会存在局部性？•	Why does locality exist in programs?

- **指令重复使用**：循环、函数–	Instruction reuse: loops, functions
- **数据工作集**：记录、数组、对象–	Data working sets: records, arrays, objects

小而快的缓存由大而慢的内存支持，给人一种单一的、大而快的内存的错觉！！！。Small, fast caches backed up by larger, slower memories give the illusion of a single, large, fast memory!!!

# M1电路内存：M1 Memory

- M1只允许**每个内存操作有一个时钟周期，这是不现实的**!•	The M1 allows only one clock cycle for each memory operation  unrealistic!
  - 在**实际**系统中，**内存访问比CPU时钟速度慢2-500倍**–	In real systems memory access is 2-500x slower than CPU clock speed
- **M1控制算法**只是通过**将内存总线上的任何数值放入目标寄存器来进行**。•	The M1 control algorithm just proceeds by placing whatever value is on the memory bus into the destination register
  - 但如果内存操作未响应，这将是一个不正确的值– 	But if the memory has not responded, this will be an incorrect value
    - 内存未响应，，1个时钟周期一个内存操作，，值会一直在那
- **最简单的解决方案是让控制算法在while循环中等待，直到内存操作完成**。•	The simplest solution is to make the control algorithm wait in a while loop until the memory operation finishes
  - 这是对M1控制算法的一个小改动。–	This is a minor change to the M1 control algorithm
- **更复杂的解决方案包括【higher performance**•	More sophisticated solutions include
  - 缓存、交错内存、超标量内存–	Caches, interleaved memory, superscalar memory
  - 管道化、预取、OoO执行–	Pipelining, prefetching, OoO execution

# 交叉存取内存(技术): Interleaved Memory

![](/static/2022-04-24-23-54-33.png)

- 在**早期的高性能机器**中使用，例如IBM 360/91。•	Used in early high-performance machines, e.g. IBM 360/91
- **存储器是由2^k个独立的存储器组构成的**•	The memory is built from 2k individual memory banks
  - **连续的内存地址对应于bank 0，...，2^k-1** –	Consecutive memory addresses correspond to banks 0, …, 2k-1
- 许多程序**加载或存储一连串的内存访问**•	Many programs load or store a sequence of memory accesses
  - 它们**可以同时进行，因为它们指的是不同的内存bank**–	They can be  performed concurrently, as they refer to distinct memory banks

# 缓存：Cached Memory

![](/static/2022-04-24-23-30-04.png)

**位于寄存器和主存储器之间的小型快速存储器**•	Small fast memory located between the registers and main memory

- **缓存可以有多个级别**，但原则是相同的–	There can more than one levels of cache, but principles are the same

目标：利用局部性（即让**常见情况**变得快速）•	Goal: exploit locality (i.e. make common cases fast) by keeping
。

- **大部分的内存空间在大而慢的主存中**–	Most of the memory space in a large but slow main memory
- **最近使用的数据放在一个小而快的缓存中**（与CPU速度相匹配）–	Recently used data in a small but fast cache (that matches CPU speed)

数据通路和控制需要确定，对于**每个内存访问**，**有效地址是否指的是高速缓存中的一个字（高速缓存驻留验证**）。•	The datapath and control need to determine, for each memory access, if the effective address refers to a word in the cache (test for cache residency)

- 如果是的话(数据在高速缓存中)，访问就会很快，主存就不会被使用。–	If so, the access is quick and the main memory is not used

**缓存驻留验证**必须是快速的(**1 cycle**??)!•	The test for cache residency must be fast!

- **发生在每个内存访问**中（和每个指令）。–	Happens on every memory access (and for every instruction)
- **对于某些指令不止一次（如load, store**）。–	For some instructions more than once (e.g. load, store)
  - 比如先取某个字，再存入某个字

# 缓存组成：Whats in the cache

由于**高速缓存包含最近使用过的数据，所以它不包含连续位置的字序列**•	Since the cache contains recently used data, it does not contain a sequence of words at consecutive locations

![](/static/2022-04-25-00-10-05.png)

- **缓存中的每个位置包含两部分**•	Each location in the cache contains two parts
  - **一个字的数据**：低位–	A word of data: low order bits
  - **一个标签**（<font color="red">该数据在主存储器中的地址</font>）：高位–	A tag (the address of this data in main memory): high order bits
- <font color="deeppink">标签使处理器能够识别一个地址是否指的是缓存中的数据, 如果是的话，是在哪个位置（或条目）。</font>，•	The tag enables the processor to identify whether an address refers to data that is in the cache, and if so, in what location (or entry) it is

# 缓存命中：Cache hits and misses

在**每次内存访问中，硬件都会根据缓存中的标签检查地址**。•	On every memory access, the hardware checks the address against the tags in the cache

**如果地址是指高速缓存中的数据，那就是高速缓存命中**。•	If the address refers to data in the cache, it is a cache hit

- 数据就会**以处理器的速度**被**快速检索**出来。–	The data is retrieved quickly, at processor speed

否则就是高速缓存未命中•	Otherwise it is a cache miss

- **执行对主存的访问，并将结果放在高速缓存中，以便将来再使用**。–	An access to main memory is performed, and the result is placed in the cache for future reuse
- **处理器等待慢速内存访问的完成**–	The processor waits for the slow memory access to complete
  - many cycles

---

例子 - 假设缓存未满 assume cache not full

![](/static/2022-04-25-00-14-28.png)

- 处理器抓取位置36•	The processor fetches location 36
- 缓存中没有这个位置，所以在控制算法等待的时候，从内存中取–	The cache doesn't contain this location, so the memory fetches it while the control algorithm waits
- 结果被放置在高速缓存的一个空槽中（下一次获取将是快速的）。–	The result is placed in an empty slot in the cache (next fetch will be fast)
- **如果缓存已满，需要创建一个空位，删除一个字**。–	If the cache is full, a free slot needs to be created removing a word

# 缓存块：Cache Block

我们不只是在高速缓存中保留单个的内存位置（字节或字）。•	We don’t just keep individual memory locations (bytes or words) in the cache

- **缓存被组织成 "大块"，称为缓存块（或行**）。•	The cache is organised in “large chunks” called cache blocks (or lines)
  - 例如，在一个16的倍数的地址上有16个字节(**不同架构不同大小**)–	E.g. 16 bytes at an address which is a multiple of 16
    - **每16B，就是个缓存块**
  - **对一个地址a，a+1，...，a+15的内存访问实际上是指同一个高速缓存块（其中 "a "是16的倍数**）。•	A memory access to an address a, a + 1, …, a + 15 actually refers to the same cache block (where “a” is a multiple of 16)

:orange: **缓存块的大小有影响•**	The size of the cache block has implications

- **缓存块越大，越能增加缓存命中的概率（空间局限性**）。–	Making it bigger increases the probability of cache hit (spatial locality)
- **但同时也增加了缓存未命中的惩罚（更多的信息需要带来**）。–	But also increases the penalty of a cache miss (more info to bring)
  - 因为每个缓存块越大，，里面信息越多

# ==============

# 缓存映射算法/方式-缓存块位置：Cache Block Placement

内存地址有3种方式放入缓存（映射方式）

![](/static/2022-04-25-02-43-26.png)

* 全相连映射 full associative
* 直接映射 direct mapped
* 组相联映射 set associative

# 缓存映射-查缓存：Searching the cache

出于对成本的考虑，cache大小相对于主存来说会小很多。因此cache只能缓存主存中极小一部分数据。如何根据内存地址在有限大小的cache中查找数据呢？

**缓存在每次内存访问时都会被搜索到，所以这种搜索必须是快速的**。•	The cache is searched on each memory access, so this search must be fast!

![](/static/2022-04-25-02-43-26.png)

- **最快**的方法：**直接映射**的高速缓存•	The quickest approach: direct mapped cache
  - **每个内存地址可以【精确】地进入一个高速缓存条目**–	Each memory address can go to exactly one cache entry
  - 只有**一个条目需要搜索**–	There is only one entry to search
  - (1) 直接映射。主空间中的每个地址都被映射到高速缓存空间中的一个唯一的地址，例如，通过取地址的低阶k位，其中高速缓存包含2^k行。这就保证了一连串连续的内存位置将被映射到高速缓存的不同位置。(1)	direct mapped. Each address in primary space is mapped to a unique address in the cache space, e.g. by taking the lower order k bits of the address, where the cache contains 2^k lines. This ensures that a sequence of consecutive memory locations will be mapped to different locations in the cache.
- **最通用**的方法：**全相连搜索**•	The most general approach: associative search
  - 每个内存地址可以进入**任何一个缓存条目**–	Each memory address can go to any cache entry
  - 我们需要**在所有的缓存条目中进行搜索**–	We need to search in all the cache entries
  - 每个内存位置可以被放置在高速缓存中的任意位置。因此，**缓存行必须包含一个字的完整内存地址，以及该字的值**。fully associative. Each memory location could be placed at an arbitrary location in the cache. Thus the cache lines must contain the full memory address of a word, as well as the value of the word.
- 一个**折中**的办法：**组相联映射缓存**•	A compromise: set associative cache
  - 每个内存地址都可以**进入一组缓存条目（2，4，8等**）。–	Each memory address can go to a set of cache entries (2, 4, 8, etc)
  - 我们只需要**在相应的缓存项集合中进行搜索**–	We need to search only in the corresponding set of cache entries
  - 大多采用这个方式
  - 主空间中的每个地址都被映射到高速缓存空间中的一小部分可能的地址。对于一个4-way set associative cache来说，这可以通过生成一个初步的cache地址来完成，就像直接映射一样，但是要尝试所有2位的组合，这样每个内存地址就可以被放置在cache中4个可能的位置之一。set associative. Each address in primary space is mapped to a small set of possible addresses in the cache space. For a 4-way set associative cache, this may be done by generating a preliminary cache address as in direct mapped, but then by trying all combinations of a two bit field, so that each memory address could be placed in one of four possible locations in the cache.

# 直接映射缓存：Direct Mapped Cache

找到一个内存字在高速缓存中的位置的**简单方法，但不灵活**•	Simple approach to find the location of a word in the cache, but inflexible

- **地址空间中高 "k "位为x的每个字都被映射到缓存中的位置x上【高k位用于映射**•	Every word in the address space whose high order “k” bits are x is mapped to location x in the cache
- **要么这个字在那个位置，要么根本就不在缓存中**–	Either the word is in that location, or it isn't in the cache at all
  - 当我们根据地址中index位找到cache line后，取出当前cache line对应的tag，然后和地址中的tag进行比较，如果相等，这说明cache命中。如果不相等，说明当前cache line存储的是其它地址的数据，这就是cache缺失（cache miss

:orange: 问题：**这可能导致一连串的内存操作引起一连串的高速缓存未命中（高速缓存颠簸**）。•	Problem: it can cause a sequence of memory operations to induce a sequence of cache misses (cache thrashing)

- 例如，内存块映射到相同缓存位置,未命中率增加–	E.g. blocks mapping to the same location => increased miss rates

# 全相联映射缓存:fully associative cache

**最灵活的方法 失误率最低**•	The most flexible approach  lowest miss rate

- 问题：**硬件必须搜索缓存中的【所有标签】才能找到该区块**•	Problem: the hardware must search all the tags in the cache to find the block
  - **增加了访问时间和高功率消耗**–	Increased access time and high-power consumption
  - **但是解决了高速缓存颠簸问题**
- **每个缓存块都有一个专门的比较器**，根据这个**块标签**检查内存地址•	Each cache block has a dedicated comparator that checks the memory address against the tag for this block
  - **由硬件来完成，并行进行**–	Done by hardware, in parallel

# 组相联映射缓存：Set associative cache

根据**缓存位置子集的大小，有不同的灵活性**•	Variable flexibility depending on the size of the subset of the cache locations

- **把直接映射看作是子集只有一个元素的特殊情况，而把全相联看作是子集是整个集合的特殊情况**。•	Think of direct mapped as a **special case** where the subset has one element, and fully associative as a special case where the subset is the entire set
- 硬件必须**搜索与一个内存位置相对应的可能的缓存位置【子集的标签**】•	The hardware must search the tags of the subset of possible cache locations corresponding to a memory location
  - <font color="red">减少了检查标签所需的【比较器】的数量，也减少了解决匹配问题的树形电路的大小</font>。–	Reduces the number of comparators needed to check tags, and the size of the tree circuit to resolve matches
- 许多真实的机器都使用组相联缓存•	Many real machines use set associative cache
  - 较高级别的缓存（L1）：2-或4-路通用–	Higher level caches (L1): 2- or 4-way common
  - 低级别的缓存（L2，L3）：8-32路通用–	Lower level caches (L2, L3): 8- to 32-way common

# 3个对比

直接映射需要简单的硬件，但是工作集中的几个字很有可能映射到同一个高速缓存行，从而导致该行重复的高速缓存丢失。这个问题在集合关联高速缓存中会大大减少，而且所需的额外硬件也相当小，因为每个地址在高速缓存中都只有一小部分可能的位置。一个完全关联的高速缓存可以确保由内存位置冲突引起的额外的高速缓存失误数量最小，但是它要求每一个高速缓存行都有一个比较器来检查该行的地址和一个广播内存地址。这就导致了在高速缓存中增加了大量的逻辑门，而且还需要一些额外的时间来检查高速缓存。因此，完全关联式通常在硬件复杂性和门延迟方面的成本太高，以至于无法证明它比集合关联式高速缓存更有优势。Direct mapped requires simple hardware, but there is a high probability that several words in the working set might map to the same cache line, resulting in repeated cache misses on that line. This problem is significantly reduced in a set associative cache, and the additional hardware required is fairly small because each address has only a small set of possible locations it could go in the cache. A fully associative cache ensures the minimal number of additional cache misses caused by memory placement conflicts, but it requires every cache line to have a comparator to check the line address against a broadcast memory address. This results in a very large amount of additional logic gates in the cache, and also takes some additional time for checking the cache. Thus fully associative is usually too costly, in both hardware complexity and gate delay, to justify its benefits over set associative cache.

# CAM内容可寻址存储器: content addressable memory

> 标准存储器RAM查找方式为通过地址查找相应地址的数据，而CAM为通过数据查找相应地址，实现了更快的数据搜索。
> 
> CAM中，每个地址只能存储唯一数据，但特定的数据可以存在多个地址中
> 
> CAM存储器在其每个存储单元都包含了一个内嵌的比较逻辑，**写入CAM的数据会和其内部存储的每一个数据进行比较，并返回与端口数据相同的所有内部数据的地址**
> 
> CPU对Cache的搜索叫做Tag search，即通过Cache中的CAM（Content Addressed Memory）对希望**得到的Tag数据进行搜索**。CAM是一种存储芯器，延迟很低。
> 
> **CPU需要某个数据的时候，它会把所需数据的地址通过地址总线发出，一份发到与内存中，一份发到与Cache匹配的相联存储器（CAM）中，CAM通过分析对比地址，来确定所要的数据是否在Cache中，如果在，则以字为单位把CPU所需要的数据传送给CPU，如果不在，则 CPU在内存中寻找到该数据，然后通过数据总线传送给CPU，并且把该数据所在的块传送到Cache中**。
> 
> **CAM使用一组比较器，以比较输入的标签地址和存储在每一个有效cache行中的cache-tag。访问地址的tag部分被作为CAM的输入，输入标签同时与所有cache标签相比较。如果有一个匹配，那么数据就由cache存储器提供；如果没有匹配，存储器控制器就会产生一个失效（miss）信号**。

在非常**高速的搜索应用**中使用的**特殊类型的存储器**•	Special type of memory used in very-high-speed searching applications

- 例如：网络、数据库、翻译查找缓冲器–	Examples: networking, databases, translation lookaside buffers
- 也叫**相联内存**–	Also called Associative memory
- **每个字包含几个字段，数据由【其中一个字段】访问**•	Each word contains several fields, and data is accessed by one of the fields
- **在内存访问中，指定字段的值被广播出来**•	On a memory access, the specified field value is broadcasted
  - 同时，**每个位置将广播的字段与它的本地字段值进行比较，产生一个布尔匹配**。–	In parallel, each location compares the broadcasted field with its local field value, resulting in a Boolean match
  - **存储器返回与该字段匹配的字的其余部分(数据**–	The memory returns the rest of the word that matches this field
  - 一棵or-gates树可以在对数时间内确定是否存在匹配。–	A tree of or-gates can determine in logarithmic time if a match exists
- **如果出现几个匹配，存储器必须解决这个问题**。•	If several matches occur, the memory must resolve this
  - 一个树状电路也可以在对数时间内确定一个唯一的响应者–	A tree circuit can also determine a unique responder in logarithmic time

# ==============

# 缓存算法(缓存满)

如果缓存已满，为了带来一个新的区块，必须驱逐另一个区块。•	If the cache is full, to bring a new block another block must be evicted

- **直接映射缓冲：只有一个区块可以被驱逐**。•	Direct mapped caches: there is only one choice of block to evict
- **相联缓存**：如何选择一个 "受害者"？Associative caches: how to choose a “victim”?
  - **随机**：在集合中**随机选择**一个受害者区块 Random: select a victim block in the set randomly
  - **最近使用最少的（LRU**）：选择在**最长时间内没有被使用过的块**。Least-recently-used (LRU): select the block that has not been used for the longest period of time
    - 由于（时间）局限性原则，在实践中运行良好 Works well in practice because of the principle of (temporal) locality
  - **非最近使用的（NRU**）：选择一个**非最近使用的区块** Not-recently-used (NRU): select a block other than the most-recently used
    - <font color="red">与LRU相比，需要更少的存储空间，并且比随机表现更好</font> Need less storage than LRU and performs better than random
- 理想：**选择在最长时间内不会被使用的区块** Ideal: select the block that will not be used for the longest period of time
  - **需要了解未来的情况，这是不现实的** Requires knowledge of the future  unrealistic!

# 缓存写模式（策略）：Cache Write Strategies

如何处理写操作（存储指令）？•	How are the writes (store instructions) handled?

- **如果一个存储只改变了缓存条目而没有改变主存，那么缓存和主存就会变得不一致**。–	If a store changes only the cache entry but not the main memory, then the cache and main memory become inconsistent
- 缓存缺失->可能需要稍后将缓存条目写出来。–	A cache miss may require the cache entry to be written out later
- 在**透写**中，一个**存储指令同时改变了缓存条目和主存**。•	In write-through, a store changes both the cache entry and main memory
  - **为了减少内存操作的成本，数据可能被缓冲在寄存器中（再送入内存之前），使执行继续进行**。–	To reduce the cost of the memory operation, the data may be buffered in registers, enabling the execution to continue
  - 产生了**更多的内存流量**–	Generates more memory traffic
  - Write-through（直写模式）在数据更新时，同时写入缓存Cache和后端存储。此模式的优点是操作简单；缺点是因为数据修改需要同时写入存储，数据写入速度较慢。
- 在**回写**中，**允许缓存与主存不一致，存储指令只改变缓存条目**•	In write-back, the cache is allowed to become inconsistent with main memory, a store changes only the cache entry
  - 缓存缺失->可能需要存储缓存条目(数据至内存)（如果修改了）。–	A cache miss may require the cache entry to be stored (if modified)
  - **回写可以减少内存存储的数量，并提高性能**–	Write-back may reduce the number of memory stores, and improve performance
  - Write-back（回写模式）在数据更新时只写入缓存Cache。**只在数据被替换出缓存时，被修改的缓存数据才会被写到后端存储**。此模式的优点是数据写入速度快，因为不需要写存储；缺点是一旦更新后的数据未被写入存储时出现系统掉电的情况，数据将无法找回。

---

# 缓存写未命中情况

:orange: 如果在缓存中找不到该块，会发生什么？【存在写入缓存缺失数据的情况(缓存写未命中)，如何处理】•	What happens if the block is not found in the cache?

- **Write allocate**：将数据块带入缓存并写入该块【**先把要写的数据载入到Cache中，写Cache，然后再通过flush方式写入到内存中；**】•	Write allocate: bring the block into the cache and write to it
  - **如果该块很快就会被另一个内存访问使用，则是好的选择（局部性**）。–	Good if the block will be used soon by another memory access (locality)
  - 通常与**回写**一起使用–	Usually used with write-back
- **Write no-allocate**：不把区块带入缓存，修改内存中的数据【**直接把要写的数据写入到内存中**】•	Write no-allocate: do not bring block into cache and modify data in memory
  - 如果在不久的**将来不会发生对同一块的内存访问**，则是好事。–	Good if no memory access to the same block occur in the near future
  - 通常与**透写**一起使用–	Usually used with write-through

# 多级缓存：Multi-Level Caches

低级别的缓存是否保留了高级别的块的副本？•	Do lower-level caches keep a copy of the blocks in higher-level?

![](/static/2022-04-25-15-34-04.png)

- **包含式缓存**•	Inclusive caches
  - **低级别的缓存对高级别的每个块都有一个副本**–	Lower-level caches has a copy of every block in higher level
  - **浪费了低级缓存的容量**–	Wastes capacity of lower-level caches
  - **简化了另一个实体（处理器）寻找缓存块的过程**–	Simplifies finding a cache block by another entity (processor)
  - 当CPU试图从某地址load数据时，首先从L1 cache中查询是否命中，如果命中则把数据返回给CPU。如果L1 cache缺失，则继续从L2 cache中查找。当L2 cache命中时，数据会返回给L1 cache以及CPU。如果L2 cache也缺失，很不幸，我们需要从主存中load数据，将数据返回给L2 cache、L1 cache及CPU。某一地址的数据可能存在多级缓存中
- **独立式缓存**•	Exclusive caches
  - **一个区块只能存在于一个级别的高速缓存中**–	A block may reside in only one level of the cache hierarchy
  - **最大限度地提高缓存层次结构的总容量**–	Maximises aggregate capacity of the cache hierarchy
  - <font color="red">要求所有级别的高速缓存都有统一的块大小</font>–	Requires a uniform block size for all cache levels
  - 与inclusive cache对应的是exclusive cache，这种cache保证某一地址的数据缓存**只会存在于多级cache其中一级**。也就是说，任意地址的数据不可能同时在L1和L2 cache中缓存

# Summary缓存问题：Cache Issues

- 块的放置。一个块可以放在哪里？•	Block placement: Where can a block be placed?
  - 直接映射（正好1个位置），全相联（任何位置），组相联（一组位置）。–	Direct mapped (exactly 1 location), fully associative (any location), set associative (a set of locations)

- 区块替换。哪个区块应该被替换？•	Block replacement: Which block should be replaced?
  - 随机、最近最少使用（LRU）、最近未使用（NRU）。–	Random, Least recently used (LRU), Not recently used (NRU)

- 写入策略。写入（存储指令）时发生什么？•	Write strategy: What happens on a write (store instruction)?
  - 透写、回写、写入分配、写入不分配–	Write-through, write-back, write-allocate, write-no-allocate

- 包含性。下一层是否包含当前层中的所有数据？•	Inclusivity: Does the next level contain all the data found in the current level?
  - Inclusive, exclusive

# ========

# 虚拟内存:Virtual Memory

> 由程序产生的这些地址称为虚拟地址(virtual address)，它们构成了一个虚拟地址空间(virtual address space)。在没有虚拟内存的计算机上，系统直接将虚拟地址送到内存总线上，读写操作使用具有同样地址的物理内存字，而在使用虚拟内存的情况下，虚拟地址不是被直接送到内存总线上，而是被送到内存管理单元(Memory Management Unit, MMU)

缓存和主存储器一起工作•	Cache and main memory work together

- **它们提供了一个与主存大小相当的存储器（GBs），其访问时间通常与缓存一样快（1个时钟周期**）。–	They provide a memory with the size of the main memory (GBs) which usually has access time as fast as the cache (1 clock cycle)
- **它之所以起作用，是因为对主内存地址空间的大多数访问是根据一小部分位置【组相联**。–	It works because most accesses to the main memory address space refer to a small subset of locations

虚拟内存也是同样的想法•	Virtual memory is the same idea

- **小型快速存储器现在是主存**–	The small fast memory is now the main memory
- **大的慢速存储器是磁盘**–	The large slow memory is the disk
- 一种**内存管理技术**，它•	A memory management technique that
  - 提供一种 "机器上可用存储量的抽象"。–	Provides an "abstraction of the amount of storage available on a machine“
  - 给用户造成 "一个非常大的（主）内存的错觉"–	Creates the "illusion to users of a very large (main) memory"
- 同样，它的工作原理是，<font color="red">在虚拟内存中有一个小的工作地址集(虚拟地址空间)；大多数访问指的是工作地址集的数据</font>。–	Again, it works because there is a small working set of addresses in the virtual memory; most accesses refer to data in the working set
  - **虚拟地址空间**，指的是**进程所能访问的所有的虚拟内存地址的集合**。虚拟地址空间主要受程序的位数影响。除此之外，它还受 CPU 的实现的影响，比如 i7 处理器，它所支持的虚拟地址空间的范围是 [0,$2^{48}$)，即 256TB，不过一般这也够了。
  - **物理地址空间**的东西。顾名思义，物理地址空间表示的是所有能访问的物理地址的集合，它受计算机的主存大小影响。比如说，计算机的内存是 4GB，那么物理地址空间就是 [0, $2^{32}$)。
  - **虚拟寻址 的意思就是将 虚拟地址空间 中的地址翻译成 物理地址空间 中的地址，然后再执行相关的读指令或者写指令**。
    - 操作系统将程序使用的内存地址（称为虚拟地址）映射为计算机内存中的物理地址。•	The operating system maps the memory addresses used by a program, called virtual addresses, into physical addresses in the computer memory
      - 使用硬件和软件的组合 –	Using a combination of hardware and software
  - <font color="deeppink">虚拟内存给进程提供了一个更大的内存空间，不再受物理内存大小的限制。它将物理内存看作是存储在磁盘上的地址空间的缓存。 现在的电脑好一点的差不多就是 16GB 或者 32GB 的内存，而且内存越大，肯定就越贵。那如果只有物理内存，在很多情况下根本不够用，特别是需要运行很多程序的情况下。而磁盘空间相对来说是很便宜的，即使是 SSD，在同样的容积下也便宜太多了。虚拟内存技术在主存中只保留活动区域，然后根据需要在磁盘和主存之间来回传送数据，这样，它就可以更加高效的利用主存。</font>
- 最大的区别•	The big difference
  - **缓存完全由硬件实现**–	Cache is implemented entirely in hardware
  - <font color="red">虚拟内存部分由硬件实现，部分由软件实现</font>–	Virtual memory is implemented partially in hardware, partly in software
- 程序在执行过程中的一个较短时期内，所执行的指令地址和指令的操作数地址，分别局限于一定区域。这可以表现为：
  * 时间局部性：一条指令的一次执行和下次执行，一个数据的一次访问和下次访问都集中在一个较短时期内
  * 空间局限性：当前指令和邻近的几条指令，当前访问的数据和邻近的几个数据都集中在一个较小区域内
  * **局部性原理表明，从原理上来说，虚拟存储技术是可以实现的，而且实现之后应该能够取得较好的结果**

:orange: 虚存出现意义 motivation

- 每个进程都希望看到自己的、完整的地址空间–	Each process would like to see its own, full, address space
- **不可能为所有进程提供完整的物理内存**–	Impossible to provide full physical memory for all processes
- **进程每次只使用大地址空间的一小部分**–	Processes use only a small part of the large address space at a time
- 进程希望自己的内存不受其他进程的影响–	Processes want their memory protected from other processes
- 操作系统需要受到保护，不受应用程序影响–	The operating system needs to be protected from applications

# 虚存其他功能：Additional Functions of Virtual memory

**虚拟内存的核心目的是提供一个大的地址空间，可以（几乎）以主内存的速度访问**。•	The central purpose of virtual memory is to offer a large address space that can (almost) be accessed at the speed of main memory

- 还支持一些额外的功能•	Several additional features are also supported
  - **分段：将内存组织成独立的地址空间** –	Segmentation: organise memory into separate address spaces
    - 程序可以使用一系列相邻的虚拟地址来访问物理内存中不相邻的大内存缓冲区。
    - 虚拟内存为程序提供内存管理。我们在敲代码的时候，不需要考虑这个变量会不会被其它程序错误的修改。因为虚拟内存帮我们做了这些事情，它给程序提供了内存隔离，为程序提供了安全的共享物理内存的途径。使得每个进程的地址空间不会被其它进程破坏。 比如说我们在程序中定义了一个指针，并且为它分配了空间，这块内存最终会分配到物理内存上。你不用担心其它程序会分配相同的物理内存。
  - **保护：只允许有访问权限的进程访问**。–	Protection: allow access only to the process(es) that have access rights
    - 不同进程使用的虚拟地址彼此隔离。一个进程中的代码无法更改正在由另一进程或操作系统使用的物理内存。
    - **虚拟内存技术也给每个进程提供了一致的、完整的地址空间**。比如在操作系统上执行若干个进程，每个进程都有相同的地址空间，都在同样的起始位置放置了堆、栈以及代码段等。这样，它简化了像链接器、加载器这样的程序的内存管理。

# 虚存 vs 缓存：Virtual memory vs cache

- **基本理念：类似于缓存**•	Basic idea: similar to cache
  - 大型慢速存储器的容量以小型快速存储器的速度运行–	Capacity of large slow memory operating at speed of small fast memory

- **缓存：两者都是基于晶体管的存储器**•	Cache: both memories based on transistors
  - 大的慢速存储器是主存储器–	The large slow memory is the main memory
  - 主存储器大约大1K倍，慢10-100倍–	Main memory is about 1K times larger and 10-100 times slower

- **虚拟存储：二级存储器（磁盘）可以是磁性的、光学的等**。•	Virtual memory: secondary memory (disk) can be magnetic, optical, etc
  - 主存储器现在是小的快的–	Main memory is now the small fast one
  - 磁盘比主存储器大约1K倍，慢10M倍–	Disk is about 1K times larger and 10M times slower than main memory

- **其他差异**•	Other differences
  - <font color="red">访问磁盘需要执行指令和处理中断</font>–	Accessing disk requires executing instructions and processing interrupts
  - **一个磁盘记录包含一个大的字块（比缓存行/缓存块大得多**）–	A disk record contains a large block of words (much larger than cache line)

# 内存管理单元MMU-页:Page

虚拟内存**不单独处理每个字**•	Virtual memory doesn't handle each word individually

- **地址空间（每个进程的）被划分为固定大小的页**•	The virtual address space (of each process) is divided into fixed-sized pages
  - 例如，编号为`0,1,2,..., p - 1`– E.g. numbered 0,1, 2, …, p – 1
- **典型的页大小是2KB，4KB，等等**。•	The typical page size is 2KB, 4KB, etc
  - 页面地址的倍数为2K、4K等（K=1024）。–	With page addresses multiple of 2K, 4K, etc (K=1024)
  - 一页有多个地址

- **页面被保存在一起**•	Pages are kept together
  - 一个页**要么完全在磁盘上，要么在主内存上**–	A page is either entirely on disk or main memory

- **页在虚拟内存中的作用与缓存内存中的缓存行相同**•	Pages play the same role in virtual memory as cache lines in cache memory
  - 一组位置被视为一个单位–	A block of locations treated as one unit

# 虚拟内存地址 & 物理内存地址：Virtual and Physical memory

![](/static/2022-04-25-21-08-54.png)

**一个虚拟地址指的是虚拟地址空间中的一个位置** •	A virtual address refers to a location in a virtual address space

- 它是一个从0到n-1的自然数（n是地址空间的总大小）。–	It's a natural number from 0 to n – 1 (n is the size of the address space)

**如果一个页面的大小是2^k个地址，地址空间的大小就是n=p×2^k** •	If the size of a page is 2k addresses, the size of the address space is n=p×2k

- <font color="red">较低的s位指定了页内的地址（偏移）</font>。–	The lower s bits specify the **address within the page** (offset)
- <font color="deeppink">较高的位指定了页的编号</font>–	The upper bits specify the page number

使用中的**虚拟内存页被映射到物理内存的页**（有时称为**物理帧**）。•	Pages in use are mapped to pages of physical memory (called **page frames** = physical pages)

- **虚拟内存**：被划分为称为页的大块–	Virtual memory: divided into chunks called pages
- **物理内存**：被分为称为帧的大块–	Physical memory: divided into chunks called frames

:orange: **最近没有使用的虚拟页可以存储在磁盘上**•	Virtual pages not recently used may be stored on disk

# 页表:Page table

**每个虚拟地址空间都有一个页表【页表是记录页的状态的表，不同的进程间的页表是独立的**•	Every virtual address space has a page table

- 它是一个数组，每个页分配一个页表项，以**页号为索引**–	It is an array, with one entry for each page, indexed by the page number
- **保存在内存中**–	Kept in main memory

**页表项（Page Table Entry, PTE**）包括•	Entries of the page table include

- **驻留位：一个布尔值，表示该页是否在主内存中 "驻留"**。–	Resident: a Boolean that says if the page is “resident” in main memory
  - 是否在主存
- **物理帧地址**：如果一个页面是驻留的(resident=1)，它**给出了包含该页帧在主内存中的地址**（否则它是一个 "不关心 "的字段）。–	Frame address: if a page is resident, it gives the address in main memory of the frame containing the page (otherwise it’s a “don't care” field)
- **其他（取决于系统）：修改位、进程ID等** –	Others (depending on the system): modified bit, process ID, etc

# DAT动态地址转换：Dynamic Address translation

> 动态地址转换（dynamic address translation，DAT）机制在运行期间把虚拟地址转换为物理地址。使用动态地址转换的系统具有以下特性：进程的虚拟地址空间中的连续地址在物理内存空间中不一定是连续的，即所谓的假临近（artificial contiguity）。

- 当控制算法**计算出一个有效地址后，它需要从虚拟地址空间访问数据**•	When the control algorithm has calculated an effective address, it needs to access the data from the virtual address space
  - 首先，有效地址被分割成一个页号和偏移量（这基本上不需要时间）。通过将页号加到页表地址寄存器中来计算页表项的地址（1ns）。**需要一个硬件内存访问来获取页表条目（10ns**）。这将被分析并提供帧地址，并将其添加到偏移量中（1ns）。最后在**真实地址上访问数据（10ns**）。总时间约为22ns。First the effective address is split into a page number and offset (this takes essentially no time). The address of the page table entry is calculated by adding the page number to the page table address register (1ns). A hardware memory access is needed to fetch the page table entry (10ns). This is analyzed and provides the frame address, which is added to the offset (1ns). Finally the data is accessed at the real address (10ns). The total time is about 22ns;
- 这被称为**动态地址转换，要么**•	This is called dynamic address translation, either
  - 数据在一个帧中（**虚拟内存命中）：必须迅速找到实际的物理地址 (通过硬件**)–	The data is in a frame (virtual memory hit): the actual physical address must be found quickly! (by hardware)
  - 缺页错误（**虚拟内存未命中）：数据在磁盘上，必须启动I/O来获取数据（需要软件**）。–	Page fault (virtual memory miss): the data is on disk, and I/O must be started to get the data (requires software)
    - 当进程访问它的虚拟地址空间中的page时，如果这个page目前还不在物理内存中，此时CPU是不能工作的，Linux会产生一个hard page fault中断。系统需要从慢速设备（如磁盘）将对应的数据page读入物理内存，并建立物理内存地址与虚拟地址空间page的映射关系。然后进程才能访问这部分虚拟地址空间的内存。

# DAT算法

1. **给出一个虚拟地址（这是计算出来的有效地址**）1.	Given a virtual address (this is the calculated effective address)

2. **提取页号和偏移量（获得上/下位**）：无用时 2.	Extract the page number and offset (get upper/lower bits): zero time
   - <font color="deeppink">较高的位指定了页编号</font>–	The upper bits specify the page number
   - <font color="red">较低的s位指定了页内的地址（偏移，，页内偏移）</font>。–	The lower s bits specify the **address within the page** (offset)

3. **将页号加到页表的地址（在一个寄存器中）：加法器延迟时间** 3.	Add page number to address of page table (is in a register): adder delay time

4. **从主存中获取页表项：内存访问时间** 4.	Fetch the page table entry from main memory: a memory access time

5. **检查驻留位**5.	Examine the resident bit
   1. **如果为真(数据在内存中，虚拟内存命中)：把偏移量加到物理帧地址上（这就是真实地址**）。1.	If True: add the offset to the frame address (that’s the real address)
      1. ![](/static/2022-04-25-21-44-55.png)
   2. **如果为假的：page fault缺页错误**2.	If False: page fault
      1. **执行中断** Perform an interrupt
      2. **DAT算法的其余部分是软件，是操作系统的一部分** The rest of the DAT algorithm is software, part of the Operating System
      3. **操作系统启动I/O，从磁盘上读入页（时间长**）。 The Operating System initiates I/O to read in the page from disk (long time)
      4. **进程被阻断，操作系统给其他进程以时间片**。 The process is blocked and the OS gives time slices to other processes
      5. **当I/O完成后，操作系统【更新页表】并解除进程的阻塞**。5.	When I/O completes the OS updates the page table and unblocks the process

# 问题：找到空闲物理帧 - finding an empty frame

在理想的情况下，当一个缺页错误(虚存未命中)发生时，将有一个空的帧可以被读入。•	In the ideal case, when a page fault occurs, there will be an empty frame the page can be read into

- **如果所有的帧都满了，就必须把其中一个帧的内容写入磁盘来清除**。•	If all the frames are full, one must be cleared by writing its contents to disk
  - **只有当页在内存中被修改时才有必要**。–	Necessary only if the page has been modified in memory
- **在一些系统中，当对一个页进行存储（至主存）时，硬件会在页表中标记一个 "脏 "字**。•	In some systems, the hardware marks a “dirty” bit in the page table when a store is performed to a page
  - <font color="deeppink">只有当该页发生变化时才会写回磁盘（回写</font>）。–	The page is written back to disk only if it has changed (write-back)
    - 类似之前cache的操作，只有修改了才写回
- **任何情况下，保留一些空闲帧会更有效率**•	It's more efficient to keep some empty frames
  - 一个**缺页错误可以立即开始从磁盘上读取所需的页面** –	A page fault can immediately start reading the required page from disk

# 页面置换算法：Page replacement policy

> 页面置换算法产生的原因：由于虚拟地址空间中，他本身并不具备存储数据的能力，只是将部分数据存储在外存中，当使用该数据的时候，才将他加载到内存中；
> **但因为内存空间有限，所以说终究会出现内存已满的情况，这个时候就需要将内存中的部分空间转移到外存中，再将该页面加载到内存**

:orange: 为了提高效率，我们需要尽量**减少缺页错误** •	To be efficient, we need to minimise page faults

- 因此，**工作集必须是在物理帧中** •	Therefore the working set must be in physical frames
  - 一个进程在一个时间间隔内所需的内存量，以取得进展 –	Amount of memory a process requires in a time interval to progress 
  - 不在的话需要额外时间操作，加载到内存中
- **当一个帧需要被清除以腾出空间给未来的缺页错误时，操作系统需要做出一个很好的选择** •	When a frame needs to be cleared to make room for future page faults, the operating system needs to make a good choice
  - 清除一个物理帧并立即在该页上发生故障是非常昂贵的【如果不小心清除了一个马上要使用的物理帧。–	It is very expensive to clear a frame and immediately fault on that page
- 操作系统使用复杂的算法来选择要清除的帧 •	Operating systems use sophisticated algorithms to choose the frame to clear
  - 它们使用硬件收集并记录在页面表中的信息–	They use info that the hardware collects and records in the page table
- 一个常见的方法是 **最近最久未使用算法(LRU**)。•	A common approach is “least recently used” (LRU)
  - 需要硬件支持（如计数器）。–	Requires hardware support (e. g. counters)
    - 比如每页使用了多少次

# DAT页表访问开销：Accessing the page table

DAT算法的每一次内存访问都需要一个**额外的内存访问**•	The DAT algorithm needs an extra memory access for every memory access

- 对**页表的访问**–	The access to the page table
- 这就是100%的开销!•	This is 100% overhead!
- 一个**重要的优化是尽可能地减少这种开销** •	An important optimisation is to reduce this overhead as much as possible

# TLB页表缓冲区-解决页表访问开销：Translation Lookaside buffer

> 将一个虚拟地址放入MMU中进行转换时，硬件首先通过将该虚拟页号与TLB中所有表项同时(即并行)进行匹配，判断虛拟页面是否在其中。如果发现了一个有效的匹配并且要进行的访问操作并不违反保护位，**则将页框号直接从TLB中取出而不必再访问页表**。
> 
> 如果虚拟页号确实是在TLB中，但指令试图在一个只当虚拟页号不在TLB中时会怎样呢?如果MMU检测到没有有效的匹配项，就会进行正常的页表查询。接着从TLB中淘汰一个表项，然后用新找到的页表项代替它。这样，如果这一页面很快被再次访问。

想法：为**页表中最活跃的页表项**引入一个特殊的缓存（称为TLB）。•	Idea: introduce a special cache (called the TLB) for the most active entries in the page table

* 【无TLB情况】最重要的一点是，需要两次硬件内存访问。这意味着分页会使系统的速度降低2倍。然而，大多数分页表的访问可能是指一小部分活动页。TLB是一个特殊的高速缓存，用于保存页表中最活跃的部分。它有搜索电路，使用并行的硬件搜索来定位相关的页表条目。当TLB成功时，它避免了对页表的额外硬件内存访问，从而使速度提高了近2倍。The essential point is that two hardware memory accesses are required. This means that paging would slow the system down by a factor of 2. However, most page table accesses are likely to refer to a small set of active pages. A TLB is a special cache for holding the most active parts of the page table. It has searching circuitry to locate the relevant page table entry using a parallel hardware search. When the TLB succeeds, it avoids the extra hardware memory access to the page table, resulting in nearly a factor of 2 speedup.
- 在每次**内存访问**时，都会**搜索该TLB缓存** •	On each memory access, the cache is searched
  - 通常情况下，它将**提供所需的页表项**–	Usually it will provide the needed page table entry
    - ![](/static/2022-04-25-22-09-15.png)
- <font color="deeppink">TLB需要有合理数量的条目(大小合理)，而且必须快速搜索</font>•	The TLB needs to have a reasonably large number of entries, and it has to be searched fast
  - **对于快速时钟来说，要小于一个时钟周期**–	Less than a clock cycle, for a fast clock
- 唯一切实可行的解决方案是**在TLB中使用相联内存（CAM**）。•	The only practical solution is to use associative memory (CAM) for the TLB
  - 转换检测缓冲区(Translation Lookaside Buffer, TLB),有时又称为相联存储器(associate memory)或快表

# 实际：Further Complications

在实践中，虚拟内存并不像前面的幻灯片所显示的那样简单!

- **有许多地址空间，每个空间都有自己的页表**•	There are many address spaces, each with its own page table
- **一个进程可以访问多个地址空间**•	A process may have access to several address spaces
- **几个进程可能同时运行**•	Several processes may be running concurrently
  - 一个进程的中断会有多大的代价？–	How expensive will a process break be?
- 【**内存保护】被集成在虚拟内存系统中**•	Memory protection is integrated in the virtual memory system
- **页表可能很大（比物理主内存大**）。•	Page tables may be big (larger than the physical main memory)
  - **所以页表可能会被分页**!–	So the page tables may be paged themselves!

# 架构OS支持：Architecture must support OS

- **动态地址转换。虚拟内存部分在硬件（TLB）中实现，部分在软件（中断）中实现**•	Dynamic address translation: Virtual memory is implemented partly in hardware (TLB), and partly in software (interrupts)
  - **架构和操作系统的相关部分必须一起设计**–	The relevant parts of the architecture and OS must be designed together

- **虚拟内存的度量。为了提高效率，虚拟内存需要【关于使用模式的信息】（"最近使用最少的"，等等**）。•	Metrics for virtual memory: To be efficient, virtual memory needs information about usage patterns (“least recently used”, etc)
  - 这种信息是**操作系统所需要的，但必须由硬件来收集**。–	This info is needed by the OS but must be collected by the hardware

- 互斥的基元。在软件中实现互斥（隔离）是可能的，但**为了高效，它需要硬件的支持**（如test&set指令）。•	Primitives for mutual exclusion: It is possible to implement mutex in software, but to be efficient it needs hardware support (e.g. test&set instruction)

- **系统虚拟化。虚拟机需要能够模拟代码的关键部分，同时全速运行其他部分，而不失去控制**。•	System virtualisation: Virtual machines require the ability to emulate key parts of code while running other parts at full speed, without losing control
  - 这就给架构**增加了限制（一些特权指令会产生一种特殊的异常，需要被拦截和仿真**）。–	This adds constraints to the architecture (some privileged instructions generate a special kind of exception to be intercepted and emulated)

# 中断(OS,硬件)：Interrupts

中断涉及访存，，下面是寄存器堆大小的影响

在一个**小的寄存器文件**中，不会有足够的寄存器来容纳所有常用的变量。**程序将需要在内存中保存中间结果，并将其加载回来使用。这将导致执行许多额外的加载和存储指令，这特别昂贵，因为内存地址比处理器操作慢**。With a small register file, there won’t be enough registers to hold all the commonly used variables. The program will need to save intermediate results in memory, and load them back to use them. This will result in the execution of many additional load and store instructions, which is particularly expensive because memory addresses are slower than processor operations. 

对于一个**大得多的寄存器文件，不会比一个中等大小的文件有太大的好处，因为典型的循环不会使用数百个单独的标量变量**。但是**大的寄存器文件会降低时钟速度，因为它的地址解码树很可能在处理器的关键路径上**。此外，当一个**中断**发生时，它将需要更多的时间来保存状态。一般来说，**有必要将整个寄存器文件存储到内存中，所以如果寄存器的数量超过了实际需要，在每次中断时都会有很多不必要的内存访问。要有效地实现过程调用和返回，也可能变得更加复杂**。With a much larger register file, there won’t be much benefit over a moderate size one, as typical loops don’t use hundreds of individual scalar variables. But the large register file will slow down the clock speed, as its address decoder trees are likely to be on the critical path of the processor. Furthermore, it will take much more time to save state when an interrupt occurs. In general, it will be necessary to store the entire register file into memory, so if there are more registers than really needed, there will be a lot of unnecessary memory accesses on every interrupt. It may also become more complicated to implement procedure call and return efficiently.

---

- **由【跳转指令以外的事件】启动的跳转（到操作系统**）。•	A jump (to the OS) initiated by an event other than a jump instruction
  - 无要求的跳转 –	Unrequested jumps
- **为程序提供**一种**机制**来**请求操作系统的服务** •	Provide a mechanism for a program to request OS services
- **防止用户程序执行被禁止的指令**•	Prevent user programs from executing prohibited instructions
  - <font color="deeppink">如果他们尝试这些指令，他们会被打断，操作系统可以决定如何处理</font>–	If they try them, they are interrupted and the OS can decide what to do
- 是操作系统**实现并发进程和线程的基础**•	Foundation for implementation by OS of concurrent processes and threads
  - 它们是由中断实现的–	They are implemented by interrupts

# 中断实现：Implementing interrupts

**控制算法实现中断**•	The control algorithm implements interrupts

![](/static/2022-04-26-00-41-47.png)

- **在指令获取/执行循环的开始，控制算法检查中断请求（处理器的一个输入**）。•	At the beginning of the instruction fetch/execute loop, the control checks the interrupt request (an input to the processor)
  - M1中不存在 –	Not present in M1

# PC值保存：Saving the pc

![](/static/2022-04-26-00-41-47.png)

**操作系统必须能够恢复被中断的进程**•	The OS must be able to resume the process that was interrupted

- 因此，**PC必须在其旧值被破坏之前被保存下来**。•	Therefore the PC must be saved before its old value is destroyed

- **硬件**必须做到这一点!•	The hardware has to do this!
  - 如果你**在软件中这样做，你至少需要一条指令，而当该指令执行时，PC的旧值已经被破坏了**。–	If you do it in software, you need at least one instruction, and when that instruction executes, the old value of the PC is already destroyed
- 在哪里**保存PC**？•	Where to save the PC?
  - **一个特殊的寄存器、内存、堆栈（注意栈溢出**）。–	A special register, memory, stack (be careful with stack overflow)

# 用户态: Saving the state

**保存用户程序状态的寄存器**也必须被保存。•	The registers that hold the state of the user program must also be saved

- 对此有两种方法•	Two approaches to this
- **软件**：**中断处理程序**来做（灵活，**需要能访问所有状态的指令，如savepc寄存器的状态**）。–	Software: interrupt handler does it (flexible, requires instructions that can access all state, e.g, the savepc register)
- **硬件**：在**控制算法中保存**所有状态（简单、可靠）。–	Hardware: save all state in the control algorithm (simple, reliable)

# 禁止中断指令：Disabling interrupts

如果任何状态保存是由软件完成的，那么**在执行保存状态的指令时，中断处理程序在若干个时钟周期内是脆弱的**。 •	If any state-saving is done by software, the interrupt handler is vulnerable for a number of clock cycles while it is executing the instructions to save state

- **如果在处理程序易受攻击时发生另一个中断，那么第一个被中断的进程的状态就会丢失**。•	If another interrupt occurs while the handler is vulnerable, the state of the first interrupted process will be lost
  - 在最好的情况下，该**进程会被破坏** –	In the best case the process is destroyed
  - 但如果该进程是一个操作系统服务，**整个操作系统可能会崩溃** –	But if the process is an OS service, the entire system may crash
- **为了防止这种情况，当处理程序处于脆弱状态时，中断被禁用**。•	To prevent this, interrupts are disabled while the handler is vulnerable
  - 数据通路包含一个**触发器`enable`，它通常是0** –	The datapath contains a flip flop enable, which is normally 0
- 如果`and2 intreq enable`(为T)，**控制算法就会执行中断** •	The control algorithm performs an interrupt if and2 intreq enable
  - 当一个**中断发生时，控制将使`enable=0`** –	When an interrupt occurs, the control sets enable to 0
  - 否则有一条指令将使`enable=1` –	Otherwise there is an instruction that sets enable to 1

# 中断丢失：Missing interrupt

- **如果在【中断被禁用】时有一个新的中断请求，那么该中断【只需等待】就可以了**•	If a new interrupt request is made while interrupts are disabled, the interrupt simply has to wait
- <font color="deeppink">一些外部事件是实时的（硬性规定要处理的）</font>。•	Some external events are real time (hard deadline to be processed)
  - 在一些系统中，当数据即将从读头下通过时，**磁盘会产生一个中断请求；如果中断延迟的时间太长，在数据被访问之前，磁盘必须完全旋转起来**–	In some systems, a disk generates an interrupt request when the data is about to pass under the read head; if an interrupt is delayed too long, the disk must rotate fully before the data can be accessed
  - 一天中的时间是通过计算定时器中断来实现的；如果错过了其中一个中断，**时钟将运行得更慢**。–	The time of day clock is implemented by counting timer interrupts; if one of these is missed, the clock will run slower
- <font color="deeppink">中断处理程序（OS）必须快速保存状态并启用中断</font>•	The interrupt handler (OS) has to save state quickly and enable interrupts
- **一些操作系统（如Linux）将中断处理程序分为两部分** •	Some operating systems (e.g. Linux) organise interrupt handlers in two parts
  - 短：保存状态和要做的工作在内存中的指示 –	Short: saves state and an indication in memory of the work to be done
  - 长：处理程序完成工作，但可以推迟到其他中断完成之后 –	Long: does the work, but can be delayed until other interrupts finish

# 内存保护：Basic Memory Protection

> 内存保护—防止用户程序直接访问另一个用户程序甚至操作系统的内存。
> 为了保护内存，添加**两个寄存器来确定程序可以访问的合法地址范围**:基本寄存器Base register—拥有最小的合法物理内存地址;限位寄存器 limitregister—包含范围的大小。

- 简单的方法：**基本寄存器和限位寄存器（br和lr），定义用户程序可以访问的内存区域** •	Simple approach: base and limit registers (br and lr) that define the region of memory that a user program can access
- **每次访问内存，控制算法都会计算有效地址（ea**）。 •	Each memory access, the control algorithm computes the effective address (ea)
  - <font color="deeppink">如果`ea<br`或`ea>lr`，控制就会产生一个中断</font> •	If ea < br or ea > lr the control generates an interrupt
- 操作系统得到控制权，可以决定做什么•	The operating system gets control and can decide what to do
  - 例如：终止程序–	E.g. terminate the program
- <font color="red">大多数计算机将内存保护纳入虚拟内存的动态地址转换算法DAT中</font> •	Most computers incorporate the memory protection into the dynamic address translation algorithm of the virtual memory

# 特权指令：Privileged Instructions

> 因此将能引起损害的机器指令作为特权指令（privileged instruction）。如果在用户模式下试图执行特权指令，那么硬件并不执行该指令，而是认为该指令非法，并将其以陷阱（trap）的形式通知操作系统

许多指令可以执行有**潜在危险的操作**•	Many instructions can perform operations that are potentially dangerous

- **I/O（例如，允许用户在任何地方写入任何数据**）–	I/O (e.g. allow a user to write any data anywhere)
- **修改任何控制内存保护、虚拟内存或中断的寄存器**–	Modifying any of the registers that control memory protection, virtual memory, or interrupts
- <font color="red">这些指令是有特权的，只能由操作系统执行</font>•	These instructions are privileged and can be executed only by the OS

# 系统状态-用户/核心态：System State

为了**控制谁可以执行特权指令，在数据通路中有一个系统状态触发器**•	To control who can execute privileged instructions, there is a system state flip flop in the datapath

- 它的值是用户和核心（或监督者）。–	Its values are **`user` and `system`** (or supervisor)
- **当控制算法要执行一条特权指令时**，它会检查处理器的状态•	When the control algorithm is about to execute a privileged instruction, it checks the processor state
  - **如果是`user`，那么控制就会产生一个中断**–	If user, then the control generates an interrupt
    - 发生了中断，意味着需要操作系统介入，开展管理工作。 由于操作系统的管理工作需要使用特权指令，因此CPU要从用户态转向核心态。
  - **否则它就执行**该指令–	Otherwise it executes the instruction
- **在中断期间，控制算法将状态设置为`system`**•	During an interrupt, the control algorithm sets the state to system
  - **在将控制权返回给用户程序之前，操作系统使用一条特殊指令将状态设置为`user`** •	Before returning control to a user program, the operating system uses a special instruction to set the state to user
  - 1、当中断发生时，cpu立刻进入核心态
  - 2、当中断发生后，当前运行的程序暂停运行，并有操作系统内核对中断进行处理
  - 3、对不同的中断信号，会进行不同的处理

# ========

# 虚拟机：Virtual Machines

计算机的 "硬件 "是 "难以 "使用的•	The “hardware” of a computer is “hard” to use

- 例如，**即使做简单的I/O也需要大量的代码**。–	E.g. even doing simple I/O requires a lot of code

**操作系统为用户程序提供服务**•	An operating system provides services to the user program

- 另一种观点：**操作系统为用户提供一个虚拟机** •	Alternative perspective: the OS provides a virtual machine to the user
- **虚拟机进程：如Java虚拟机（JVM**）。•	Virtual machine process: e.g. Java Virtual Machine (JVM)
  - **一个虚拟机是由一个仿真器（非常慢）实现的，它作为一个进程运行**–	A VM is implemented by an emulator (very slow) that runs as a process
  - **虚拟机提供语言支持，但不是一个真正的机器** –	The VM provides language support, but is not a real machine
- **系统虚拟化**：例如，VirtualBox  •	System virtualisation: e.g. VirtualBox
  - **提供一个完整的虚拟 "真机"，有中断、特权指令和I/O硬件** –	Provides a complete virtual “real machine” with interrupts, privileged instructions, and I/O hardware
  - 虚拟机可以运行一个完整的操作系统（我们可以在一台机器里有好几个）。–	The VM can run a full operating system (we can have several in a machine)

# 系统虚拟化实现：Implementing system virtualisation

一种理论上的可能性是模拟一切•	A theoretical possibility is to emulate everything

- 灵活但缓慢–	Flexible but slow

理想情况下，我们要实现两个相互矛盾的目标•	Ideally, we want to achieve two contradictory aims

- **提供完全的机器语义（中断和一切**）。–	Provide exactly the machine semantics (interrupts and everything)
- 但**以几乎全速运行本地指令**–	But run at nearly full speed of native instructions
* 引发问题：**如果特权指令在硬件中全速运行**（即没有仿真），虚拟机可以控制并杀死原来的主机系统•	Problem: if privileged instructions run at full speed in the hardware (i.e. not emulated), the VM can take control and kill the original host system
- 解决方法：**仿真特权指令，直接执行普通指令** •	Approach: emulate privileged instructions and execute normal ones directly
  - 全速运行普通指令，由于特权指令仿真，，不会导致宿主机崩溃

# ========