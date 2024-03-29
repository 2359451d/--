# Content

* [Content](#content)
* [Register Transfer Machine](#register-transfer-machine)
* [Register File](#register-file)
* [状态寄存器，通用寄存器](#状态寄存器通用寄存器)
* [取消寄存器堆？](#取消寄存器堆)
* [寄存器堆大小](#寄存器堆大小)
* [Register and memory](#register-and-memory)
* [寄存器文件的端口:Ports to the register file](#寄存器文件的端口ports-to-the-register-file)
* [Register file black box](#register-file-black-box)
* [例子-Simplified register file circuit (2 registers of 1 bit)](#例子-simplified-register-file-circuit-2-registers-of-1-bit)
* [Size parameters](#size-parameters)
* [1bit寄存器堆：Interface to the 1-bit register file](#1bit寄存器堆interface-to-the-1-bit-register-file)
* [组织1bit寄存器堆：Organisation of the 1-bit register file](#组织1bit寄存器堆organisation-of-the-1-bit-register-file)
  * [Recursive structure of general register file](#recursive-structure-of-general-register-file)
  * [Recursive definition of register file](#recursive-definition-of-register-file)
  * [寄存器堆实现：Register file for words](#寄存器堆实现register-file-for-words)
* [寄存器传输机：Register Transfer Machine](#寄存器传输机register-transfer-machine)
* [RTM电路： circuit](#rtm电路-circuit)
  * [Simulation Driver](#simulation-driver)
* [========](#)
* [Sigma16 architecture](#sigma16-architecture)
* [数据类型：Data types](#数据类型data-types)
* [寄存器&内存：Registers and memory](#寄存器内存registers-and-memory)
* [指令格式：Instruction formats](#指令格式instruction-formats)
  * [RRR指令：Representation of RRR instruction: one word](#rrr指令representation-of-rrr-instruction-one-word)
  * [RX指令：Representation of an RX instruction: two words](#rx指令representation-of-an-rx-instruction-two-words)
* [汇编例子-完整程序：A complete Example Program](#汇编例子-完整程序a-complete-example-program)
* [汇编例子-赋值语句：Compiling an assignment statement](#汇编例子-赋值语句compiling-an-assignment-statement)
* [汇编例子-if语句：Compiling an if-then statement](#汇编例子-if语句compiling-an-if-then-statement)
* [========](#-1)
* [Where we are going…](#where-we-are-going)
  * [Plan](#plan)
* [数据通路&控制单元（电路）：Datapath and control](#数据通路控制单元电路datapath-and-control)
* [为什么分开设计数据通路&控制电路：Why separate a design into datapath and control?](#为什么分开设计数据通路控制电路why-separate-a-design-into-datapath-and-control)
* [Review of Sigma16: Instruction formats](#review-of-sigma16-instruction-formats)
* [处理器基本数据通路：Processor Datapath - Basic datapath](#处理器基本数据通路processor-datapath---basic-datapath)
  * [接口](#接口)
* [控制信号-处理器基本数据通路：Basic datapath: Control signals](#控制信号-处理器基本数据通路basic-datapath-control-signals)
  * [控制信号参考：List of control signals (Interface.hs)](#控制信号参考list-of-control-signals-interfacehs)
* [寄存器-基本数据通路：Basic datapath: Registers](#寄存器-基本数据通路basic-datapath-registers)
  * [Registers and ALU (Datapath.hs)](#registers-and-alu-datapathhs)
* [内部信号-数据通路：Internal Processor Signals](#内部信号-数据通路internal-processor-signals)
* [运行数据通路：Operating the datapath](#运行数据通路operating-the-datapath)
  * [例子：写入IR寄存器-Writing a value into the instruction register](#例子写入ir寄存器-writing-a-value-into-the-instruction-register)
  * [获取指令&PC寄存器自增：Fetching an instruction and incrementing pc](#获取指令pc寄存器自增fetching-an-instruction-and-incrementing-pc)
  * [写入寄存器堆：Writing a number into the register file](#写入寄存器堆writing-a-number-into-the-register-file)
  * [读堆中寄存器：Looking at contents of the register file](#读堆中寄存器looking-at-contents-of-the-register-file)
* [Observations](#observations)
* [========](#-2)
* [设计控制单元:Designing the processor control](#设计控制单元designing-the-processor-control)
* [控制电路抽象分层：Levels of abstraction for processor control](#控制电路抽象分层levels-of-abstraction-for-processor-control)
* [控制算法结构-High level: control algorithm](#控制算法结构-high-level-control-algorithm)
  * [Straight-line control code](#straight-line-control-code)
  * [断言语句标签：Statement labels](#断言语句标签statement-labels)
  * [Blocks](#blocks)
  * [Repeat forever](#repeat-forever)
  * [Case](#case)
  * [if-then-else](#if-then-else)
* [需要的控制信号- Middle level: control signal settings](#需要的控制信号--middle-level-control-signal-settings)
* [设计控制电路-Low level: control circuit](#设计控制电路-low-level-control-circuit)
  * [控制电路设计方法：Delay element method](#控制电路设计方法delay-element-method)
  * [状态触发器和转换：State flip flops and transitions](#状态触发器和转换state-flip-flops-and-transitions)
  * [输出控制信号：Generating the control signals](#输出控制信号generating-the-control-signals)
* [========](#-3)
* [M1目标：Control for Sigma16/M1](#m1目标control-for-sigma16m1)
* [M1电路控制算法-High level: M1 control algorithm](#m1电路控制算法-high-level-m1-control-algorithm)
  * [取指令,pc++：Fetching an instruction and incrementing pc](#取指令pcfetching-an-instruction-and-incrementing-pc)
  * [Instruction fetch/execute cycle](#instruction-fetchexecute-cycle)
  * [Escape and lea/load/store …](#escape-and-lealoadstore-)
  * [Jumping](#jumping)
* [M1控制信号：Middle level: M1 control signal settings](#m1控制信号middle-level-m1-control-signal-settings)
  * [Lea: load effective address](#lea-load-effective-address)
  * [Load](#load)
  * [Jumpc0](#jumpc0)
* [M1电路设计-Low level: M1 control circuit](#m1电路设计-low-level-m1-control-circuit)
* [Start信号-Start: top of main loop (Control.hs)](#start信号-start-top-of-main-loop-controlhs)
* [解码opcode-Decoding the opcode (Control.hs)](#解码opcode-decoding-the-opcode-controlhs)
* [控制信号生成-Generating control signals (Control.hs)](#控制信号生成-generating-control-signals-controlhs)
* [Hydra查看多个信号-Keeping track of many individual signals](#hydra查看多个信号-keeping-track-of-many-individual-signals)
* [定义信号集合record-Define collection (Interface.hs)](#定义信号集合record-define-collection-interfacehs)
* [定义单个控制信号-Define control signals (Control.hs)](#定义单个控制信号-define-control-signals-controlhs)
* [========](#-4)
* [The M1 circuit](#the-m1-circuit)
* [Input/Output](#inputoutput)
* [直接内存存取：Direct memory access (DMA)](#直接内存存取direct-memory-access-dma)
* [启动阶段：Startup](#启动阶段startup)
* [引导：Bootstrapping](#引导bootstrapping)
* [M1引导程序：Bootstrapping the M1 using DMA](#m1引导程序bootstrapping-the-m1-using-dma)
* [The full M1 system](#the-full-m1-system)
* [System circuit (System.hs)](#system-circuit-systemhs)
* [运行机器语言程序：Running a machine language program](#运行机器语言程序running-a-machine-language-program)
  * [两种执行方式-Two execution environments](#两种执行方式-two-execution-environments)
* [运行例子-Example program: ArrayMax](#运行例子-example-program-arraymax)
* [sigma16仿真器-Running a program on the emulator](#sigma16仿真器-running-a-program-on-the-emulator)
* [M1电路运行程序-Running a program on M1 circuit](#m1电路运行程序-running-a-program-on-m1-circuit)
* [Running a program on M1 circuit (README.txt)](#running-a-program-on-m1-circuit-readmetxt)
* [Simulating M1](#simulating-m1)
* [DMA加载程序至内存-Reading program with DMA](#dma加载程序至内存-reading-program-with-dma)
  * [控制算法开始-Starting the control algorithm](#控制算法开始-starting-the-control-algorithm)
* [每个时钟周期的模拟输出-Simulation output for every clock cycle](#每个时钟周期的模拟输出-simulation-output-for-every-clock-cycle)
* [例子-某周期输出：Typical simulation output for one clock cycle](#例子-某周期输出typical-simulation-output-for-one-clock-cycle)
* [例子-lea指令输出：Control state transitions: lea is first instruction](#例子-lea指令输出control-state-transitions-lea-is-first-instruction)
* [指令完成消息- Message when an instruction has completed](#指令完成消息--message-when-an-instruction-has-completed)
* [驱动监控指令执行输出-Monitor instruction executions](#驱动监控指令执行输出-monitor-instruction-executions)

# Register Transfer Machine

# Register File

![](/static/2021-11-09-10-37-22.png)

* Many older computer architectures contain a set of unrelated registers, each 许多较旧的计算机体系结构包含一组不相关的寄存器，每个寄存器
  * With an individual name 单独名字
  * Playing a different role in program execution 不同作用
* Most modern architectures (both RISC and CISC) provide a set of indexed registers for the programmer to use 大多数现代架构（RISC 和 CISC）都提供了一组索引寄存器供程序员使用
  * With names like R0, R1, R2, etc
* Registers are implemented in a circuit called the register file 寄存器在称为寄存器文件的电路中实现
* A register file can read or write a value, given the register number 给定寄存器编号，寄存器文件可以读取或写入值

---

# 状态寄存器，通用寄存器

一些计算机体系结构将比较的结果（小于、等于或大于）保存在一个特殊的条件代码寄存器（或状态寄存器）中，而其他体系结构的比较指令则将结果作为布尔值保存在一个普通的寄存器中。讨论一下这两种方法对指令集设计的影响。

在数据路径中，将比较的结果保存在一个特殊的寄存器或一个普通的寄存器中，也同样有效和简单。条件代码允许一条比较指令可以有多个结果< = >，可以用来控制条件跳转。将完整的比较结果保存在一般的寄存器中是很尴尬的，除非有条件跳转可以由寄存器中的一个位来控制，这是不正常的，在简化比较的同时会使跳转复杂化。当比较结果是布尔值时，需要有几条比较指令（例如，cmplt计算`x<y`为布尔值）。如果要对布尔值进行逻辑运算，或者将结果保存在一个布尔变量中，那么通用寄存器的方法就更简单、更高效。使用通用寄存器还可以通过减少状态的数量来简化流水线和中断。It is equally efficient and simple in the datapath to save the result of a comparison in a special register or a general register. A condition code allows for a single compare instruction that can have multiple results < = > which can be used to control conditional jumps. It would be awkward to save a full comparison result in a general register unless there are conditional jumps that can be controlled by a single bit in a register, which is unusual and would complicate the jumps while simplifying the compares. When the comparison result is a Boolean, there need to be several compare instructions (e.g. cmplt to calculate `x<y` as a Boolean). If logic operations are to be performed on the Boolean, or the result is to be saved in a Boolean variable, then the general register approach is simpler and more efficient. The use of general registers also simplifies pipelining and interrupts by reducing the amount of state.

# 取消寄存器堆？

(c) 典型的架构有算术指令，其中一些或所有的操作数都在寄存器中。一个可能的替代方案是取消寄存器文件，并提供类似于add x,y,z的指令，其中y+z的总和存储在x中。的总和存储在x中，而所有三个操作数都在内存中。讨论一下这种方法对机器语言代码的简单性和大小以及执行速度的影响。(c)	Typical architectures have arithmetic instructions where some or all of the operands are in registers. A possible alternative would be to eliminate the register file, and to provide instructions like add x,y,z where the sum of y+z is stored in x, and all three operands are in memory. Discuss the impact this approach would have on simplicity and size of machine language code, and on speed of execution.

也可以用来答2-address vs 3-address

拟议的指令将简化机器语言编程，因为加载和存储指令（占所有指令的很大一部分）将被取消。此外，没有必要管理哪些数据被保存在寄存器中，编译器中也不需要寄存器分配算法。这条指令可能会因为避免了两次加载和一次存储而节省时间，但事实并非如此，因为数据路径仍然需要执行相同数量的加载和存储；这些都是由控制算法发出的。也就是说，机器语言代码将不需要加载和存储的指令，但这些操作仍然是必要的。有了流水线，要求明确的加载和存储指令的开销就很少或没有。然而，还有另一个原因，拟议的指令会导致速度减慢：在较大的算术表达式中，每个操作都需要三次内存访问，而通常的寄存器架构可以在数据全部在寄存器中的情况下执行一长串计算。
因此，建议的指令将大大增加内存访问，而内存访问是很慢的。
The proposed instruction would simplify machine language programming, as load and store instructions (which account for a large portion of all instructions) would be eliminated. Furthermore, it would not be necessary to manage which data is kept in registers, and register allocation algorithms would not be needed in compilers. It might appear that this instruction would save time by avoiding two loads and a store, but that is not the case because it would still be necessary for the datapath to perform the same number of loads and stores; these would be issued by the control algorithm. That is, the machine language code would not require instructions for the loads and stores, but those operations would still be necessary. With pipelining, there is little or no overhead in requiring the explicit load and store instructions. However, there is another reason that the proposed instruction would result in a slowdown: in larger arithmetic expressions every operation would require three memory accesses, while the usual register architecture can perform a long sequence of calculations with the data all in registers.
Thus the proposed instruction would significantly increase memory accesses which are slow.


# 寄存器堆大小

在一个**小的寄存器文件**中，不会有足够的寄存器来容纳所有常用的变量。**程序将需要在内存中保存中间结果，并将其加载回来使用。这将导致执行许多额外的加载和存储指令，这特别昂贵，因为内存地址比处理器操作慢**。With a small register file, there won’t be enough registers to hold all the commonly used variables. The program will need to save intermediate results in memory, and load them back to use them. This will result in the execution of many additional load and store instructions, which is particularly expensive because memory addresses are slower than processor operations.

对于一个**大得多的寄存器文件，不会比一个中等大小的文件有太大的好处，因为典型的循环不会使用数百个单独的标量变量**。但是**大的寄存器文件会降低时钟速度，因为它的地址解码树很可能在处理器的关键路径上**。此外，当一个**中断**发生时，它将需要更多的时间来保存状态。一般来说，**有必要将整个寄存器文件存储到内存中，所以如果寄存器的数量超过了实际需要，在每次中断时都会有很多不必要的内存访问。要有效地实现过程调用和返回，也可能变得更加复杂**。With a much larger register file, there won’t be much benefit over a moderate size one, as typical loops don’t use hundreds of individual scalar variables. But the large register file will slow down the clock speed, as its address decoder trees are likely to be on the critical path of the processor. Furthermore, it will take much more time to save state when an interrupt occurs. In general, it will be necessary to store the entire register file into memory, so if there are more registers than really needed, there will be a lot of unnecessary memory accesses on every interrupt. It may also become more complicated to implement procedure call and return efficiently.

* 较大的寄存器文件的优点是：需要加载和存储的数量减少了；编程更容易，因为当前的变量可以保存在寄存器中；有了寄存器窗口，就有可能完全在寄存器中执行函数调用。Advantages of a larger register file: the number of loads and stores that are needed is reduced; programming is easier because current variables can be kept in registers; with register windows it may be possible to perform function calls entirely in the registers
* 缺点
  * 一个大的寄存器文件允许大量的变量保存在寄存器中，从而减少了所需的加载和存储指令的数量；这一点很重要，因为内存访问比寄存器中的算术慢得多。**一个大的寄存器文件需要更多的时间进行寄存器地址解码，需要更多的比特来指定一个寄存器，这可能会导致更大的指令大小，并产生更多的中断开销，因为寄存器的状态需要被保存和恢复。**  A large register file allows a large number of variables to be kept in registers, thus reducing the number of load and store instructions needed; this is significant since memory accesses are much slower than arithmetic in registers. A large register file requires more time for register address decoding, requires more bits to specify a register which could lead to larger instruction size, and produces more overhead for interrupts as the register state needs to be saved and restored.
  * 由于地址**解码**树的深度增加，**关键路径**将变得更长，减缓**时钟速度**；在**指令格式中需要更多的比特来指定一个寄存器**；由于需要**保存和恢复更多的寄存器**，**中断**的成本将更大；无论如何在程序中很难有效地使用非常多的寄存器 Disadvantages include: the critical path will become longer, slowing down the clock speed, due to the increased depth of address decoding trees; more bits are needed in the instruction formats to specify a register; the cost of an interrupt will be larger as more registers need to be saved and restored; it’s difficult to use a very large number of registers effectively anyway in a program
    * 然而，更大的寄存器文件需要在用于解码寄存器号的多路复用器和解复用器中增加级别，而这很可能是在关键路径上，所以有可能降低时钟速度。另外，如果有中断，保存状态的开销也会变得更大  However, a larger register file requires extra levels in the multiplexers and demultiplexers used to decode the register number, and this is likely to be on the critical path, so there is a risk of decreasing the clock speed.

:orange: <font color="red">平衡大小，寄存器堆设计</font>

* 为了找到一个好的平衡点，我们可以在一系列大小的寄存器文件（如2、3、4、5、6个地址位）上使用模拟进行实验。这应该使用现实的软件来完成，这些软件是根据每个寄存器文件的大小有效地编译的，而模拟器则考虑到对时钟速度的影响。To find a good tradeoff, we could run experiments using simulation on register files of a range of sizes (e.g. 2, 3, 4, 5, 6 address bits). This should be done using realistic software which is compiled efficiently to each register file size, and a simulator that accounts for effects on clock speed. 
* 我们可以计算具有不同k值的设计的时钟速度，我们可以通过采取不同k值产生的具有代码的现实程序的组合来测量加载和存储所需的周期数，这些信息给出了不同k值的性能估计（这取决于用于基准测试的程序）。 We can calculate the clock speed of designs with various values of k, and we can measure the numbers of cycles needed for loads and stores by taking a mix of realistic programs with code generated for different values of k. This information gives a performance estimate for various values of k (it depends on the programs used for benchmarking).

---

# Register and memory

Registers are similar to memory 寄存器类似于内存

* **它们充当数据的数组，可以由地址索引**
 – They act as an array of data, indexed by an address
* 它们支持读取数据和写入数据（称为加载和存储内存） – They support reading the data and writing the data (called load and store for memory)

But also different

* 内存位置通常是字节，而寄存器包含字
 – Memory locations are usually bytes, while registers contain words
* 内存位置比寄存器多得多
 – There are many more memory locations than registers
* 以处理器时钟速度访问寄存器 – Registers are accessed at processor clock speed
* 内存访问要慢得多（通常慢 3 到 500 倍） – Memory access is much slower (typically 3 to 500 times slower)

# 寄存器文件的端口:Ports to the register file

![](/static/2021-11-09-11-08-04.png)

# Register file black box

![](/static/2021-11-09-11-15-49.png)

# 例子-Simplified register file circuit (2 registers of 1 bit)

![](/static/2021-11-09-11-19-30.png)

# Size parameters

![](/static/2021-11-09-11-29-00.png)

# 1bit寄存器堆：Interface to the 1-bit register file

![](/static/2021-11-09-11-37-34.png)

# 组织1bit寄存器堆：Organisation of the 1-bit register file

![](/static/2021-11-09-11-50-07.png)

* 16reg的寄存器堆
  * 两个8reg寄存器堆，用一个地址Bit选中其中一个

## Recursive structure of general register file

通用寄存器文件的递归结构

![](/static/2021-11-09-12-01-57.png)

## Recursive definition of register file

![](/static/2021-11-09-12-20-46.png)

## 寄存器堆实现：Register file for words

circuit generator

![](/static/2021-11-09-13-09-30.png)
![](/static/2021-11-09-13-19-45.png)
![](/static/2021-11-09-13-23-17.png)
![](/static/2021-11-09-13-24-21.png)

---

![](/static/2021-11-09-13-41-41.png)

# 寄存器传输机：Register Transfer Machine

![](/static/2021-11-09-13-56-35.png)
![](/static/2021-11-09-17-30-29.png)

RTM in schematic capture tool

![](/static/2021-11-09-17-31-20.png)

# RTM电路： circuit

![](/static/2021-11-09-17-50-25.png)

RTM 电路的输入：Inputs to the RTM circuit

![](/static/2021-11-09-19-28-38.png)

effect & outputs

![](/static/2021-11-09-19-39-31.png)

## Simulation Driver

![](/static/2021-11-09-19-48-53.png)

# ========

# Sigma16 architecture

![](/static/2021-11-09-19-57-47.png)

- 下面站Sigma16, RISC，3-address角度概括
  - (1) 编程更容易，因为每个操作只需要一条指令；不需要加载和存储。(2) 不需要分配寄存器，如果有太多的变量，也不需要将寄存器溢出到内存中。(1) programming is easier because each operation requires just one instruction; there is no need for loads and stores. (2) There is no need for register allocation, or for spilling registers to memory if there are too many variables.
  - 缺点包括 (1) 程序运行速度较慢，因为每条指令需要三次内存访问（除了获取指令本身）；相比之下，加载/存储风格的架构可以对一个变量进行一次加载，并多次使用，而无需进一步的内存访问。(2) 指令会更长，因为内存地址比寄存器需要更多的位来表示。(1) The program will run slower because each instruction requires three memory accesses (in addition to fetching the instruction itself); in contrast a load/store style architecture can load a variable once and use it several times without further memory accesses. (2) Instructions will be longer because memory addresses take more bits to represent than registers.

# 数据类型：Data types

![](/static/2021-11-09-20-00-58.png)

操作数16bit

# 寄存器&内存：Registers and memory

![](/static/2021-11-09-20-09-03.png)

有效地址

Pc保存下一条指令的地址；每当机器语言程序的一个字被取走，控制算法就会增加pc。Pc holds the address of the following instruction; whenever a word of the machine language program is fetched the control algorithm increments pc.

Ir保存着当前正在执行的指令，其中的字段被传送到寄存器文件以访问源寄存器和目的寄存器。控制算法还检查ir的操作码字段，以确定哪条指令正在被执行。Ir holds the instruction currently being executed, and fields within it are transmitted to the register file to access source and destination registers. The control algorithm also examines the opcode field of the ir to determine which instruction is being executed.

ad寄存器保存双字指令的第二个字，在计算有效地址时作为一个临时变量使用。The ad register holds the second word of two-word instructions, and is used as a temporary variable while calculating effective addresses.

---

S16只有一种内存访问的寻址模式，由一个索引寄存器和一个16位位移组成。有效地址只是索引和位移的总和

* 控制算法按以下步骤计算有效地址。
  * (1)获取位移并加载到adr寄存器中；
  * (2)将adr和reg[ir_sa]相加；总和就是有效地址并加载到adr中。
* 访问一个静态变量：load R1,x[R0]，其中x是变量的地址；
  * 这使用了R0总是包含0的不变性。 
* 访问一个数组x[i]的元素：load R1,i[R0]，load R2,x[R1]。
  * 这里的有效地址是x+i，也就是x[i]的位置。
* **访问一个指针p的目标：load R1,p[R0], load R2,0[R1]。这里的有效地址ad是指针的值**。
  * R1 = 指针p的内存地址，，R1= mem[p]
  * R2 = 解引p，值载入R2
  * 其他指针例子。。
    * loadind R1,x[R2]
    * reg[1] := mem[mem[x+reg[2]]
    * 假设p是一个指向int的指针。如果没有loadind，要加载p所指向的值需要两条指令：load R1,p[R0]; load R1,0[R1] （解引）。
    * 有了loadind，这可以在一条指令中完成：loadind R1,p[R0]。为了获取指令，需要以下步骤： 
      * ir := mem[pc], pc++; case ir_op of ...; 
      * 那么这个指令的控制是 
      * adr := mem[pc], pc++;
      * adr := adr + reg[ir_sa]; # 有效地址，，p指针存储位置
      * adr := mem[adr]; # p指针指向的地址
      * reg[d] := mem[adr] 。
      * 这与加载指令类似，但在adr包含有效地址后还有一个额外的步骤来获取mem[adr]。

# 指令格式：Instruction formats

![](/static/2021-11-09-20-39-18.png)

## RRR指令：Representation of RRR instruction: one word

![](/static/2021-11-09-20-43-40.png)

RRR Instructions

![](/static/2021-11-09-20-45-42.png)

## RX指令：Representation of an RX instruction: two words

![](/static/2021-11-09-21-01-24.png)

RX instructions

![](/static/2021-11-09-21-06-35.png)

# 汇编例子-完整程序：A complete Example Program

![](/static/2021-11-09-21-13-25.png)

# 汇编例子-赋值语句：Compiling an assignment statement

![](/static/2021-11-09-21-17-44.png)

# 汇编例子-if语句：Compiling an if-then statement

![](/static/2021-11-09-21-24-59.png)

# ========

# Where we are going…

![](/static/2021-11-09-21-30-36.png)

* M1电路 - 读入sigma16汇编程序对应的机器码，存入内存，执行程序

## Plan

![](/static/2021-11-09-21-34-34.png)

# 数据通路&控制单元（电路）：Datapath and control

![](/static/2021-11-09-21-38-36.png)

# 为什么分开设计数据通路&控制电路：Why separate a design into datapath and control?

![](/static/2021-11-09-21-43-07.png)

(1) 通过分而治之简化了设计，因为数据通路和控制通常具有相似的复杂性。(1)	Simplifies design through divide and conquer, as the datapath and control are typically of similar complexity.

(2) 控制被自然地指定为具有解释器结构的算法，所以它的设计与软件类似。(2)	The control is specified naturally as an algorithm with the structure of an interpreter, so it can be designed similarly to software.

(3) 有几种不同的方法可以从控制算法中合成控制电路，因此将控制表达为一种算法可以使设计者有多种选择。(3)	There are several different ways to synthesise a control circuit from a control algorithm, so expressing the control as an algorithm keeps the designer’s options open.

# Review of Sigma16: Instruction formats

![](/static/2021-11-09-21-43-22.png)

![](/static/2021-11-09-21-43-33.png)

![](/static/2021-11-09-21-43-43.png)

# 处理器基本数据通路：Processor Datapath - Basic datapath

![](/static/2021-11-09-21-48-51.png)

## 接口

![](/static/2021-11-09-21-55-33.png)

# 控制信号-处理器基本数据通路：Basic datapath: Control signals

控制信号命名规范

![](/static/2021-11-09-22-06-12.png)

## 控制信号参考：List of control signals (Interface.hs)

建议结合着内部信号（后面有）来看。。有配合组合逻辑。。。

![](/static/2021-11-09-22-42-19.png)

# 寄存器-基本数据通路：Basic datapath: Registers

![](/static/2021-11-09-22-48-41.png)

## Registers and ALU (Datapath.hs)

![](/static/2021-11-09-22-58-23.png)

# 内部信号-数据通路：Internal Processor Signals

![](/static/2021-11-09-23-40-58.png)

# 运行数据通路：Operating the datapath

![](/static/2021-11-09-23-46-07.png)

## 例子：写入IR寄存器-Writing a value into the instruction register

![](/static/2021-11-09-23-50-35.png)

## 获取指令&PC寄存器自增：Fetching an instruction and incrementing pc

![](/static/2021-11-10-00-01-24.png)

Setting the control signals 提供什么控制信号？

![](/static/2021-11-10-00-16-45.png)

## 写入寄存器堆：Writing a number into the register file

![](/static/2021-11-10-00-27-08.png)

## 读堆中寄存器：Looking at contents of the register file

![](/static/2021-11-10-00-51-04.png)

# Observations

![](/static/2021-11-10-02-02-19.png)

# ========

# 设计控制单元:Designing the processor control

![](/static/2021-11-10-02-08-07.png)

* 控制单元核心还是**状态序列**?
  * 即，**控制算法**是描述状态序列的**高级**表示
  * 达到某种state - assert 控制信号集合 set=1

# 控制电路抽象分层：Levels of abstraction for processor control

![](/static/2021-11-10-02-16-52.png)

* high level - control algorithm
* middle level - control signals
* low level - control circuits

# 控制算法结构-High level: control algorithm

![](/static/2021-11-10-02-21-40.png)

## Straight-line control code

![](/static/2021-11-10-02-24-01.png)

## 断言语句标签：Statement labels

![](/static/2021-11-10-02-27-02.png)

## Blocks

![](/static/2021-11-10-02-28-52.png)

## Repeat forever

![](/static/2021-11-10-02-50-21.png)

## Case

![](/static/2021-11-10-02-54-00.png)

* 此例x word为2Bit

## if-then-else

![](/static/2021-11-10-02-57-50.png)

# 需要的控制信号- Middle level: control signal settings

**对于高级控制算法中的每个语句，中级符号添加一个 Assert 语句，并指定应为 1 的控制信号（集合）** For each statement in the high level algorithm, the middle level notation adds an Assert statement that specifies the control signals that should be 1

* 就是提供要被置为1的信号。。特定是时钟周期才能达到特定状态

# 设计控制电路-Low level: control circuit

![](/static/2021-11-10-03-15-09.png)

## 控制电路设计方法：Delay element method

用这个方法设计控制电路，，再由这个控制电路中实现控制算法（即表示状态机）---low-level

![](/static/2021-10-20-04-48-07.png)

Connect each flip flop input to the flip flop of the preceding statement (this gives straight-line control) 将每个触发器输入连接到前面语句的触发器（这给出了直线控制） -- 为什么状态机的output也就是control signal?

* 体现了语句之间执行的切换（也就是状态切换）。。直线控制

For a case statement with a k-bit condition, use a demultiplexer to generate `2^k` signals to initiate each of the alternatives 对于具有 k 位条件的 case 语句，使用解复用器生成“2^k”信号以启动每个备选方案（状态）

* 控制算法中，case语句的表示
* k-bit 控制input，选择其中一个分支

A control signal is generated by or-ing together the outputs of the flip flops for the states where that signal is asserted **控制电路的控制信号是通过将【触发器的输出】进行【或运算】来生成该信号被置位的状态（设为1**）

## 状态触发器和转换：State flip flops and transitions

![](/static/2021-10-20-04-57-56.png)

## 输出控制信号：Generating the control signals

![](/static/2021-10-20-05-00-36.png)

* week3

Each control signal is generated by calculating the logical or of the states where it is asserted **控制电路的每个控制信号是通过计算其被断言的状态的【逻辑或】来生成的**

![](/static/2021-10-20-05-03-09.png)

# ========

# M1目标：Control for Sigma16/M1

![](/static/2021-11-10-03-37-22.png)

# M1电路控制算法-High level: M1 control algorithm

![](/static/2021-11-10-03-40-14.png)

## 取指令,pc++：Fetching an instruction and incrementing pc

![](/static/2021-11-10-03-42-28.png)

## Instruction fetch/execute cycle

![](/static/2021-11-10-03-45-22.png)

## Escape and lea/load/store …

RX

![](/static/2021-11-10-03-48-31.png)

* lea - Rd存入有效地址

## Jumping

![](/static/2021-11-10-04-01-47.png)

(1) 下一条指令被取走，ir := mem[pc]，pc被递增。(2) 控制对操作码字段和d字段进行调度，因为这条指令使用了一个扩展操作码。	(3) 指令的第二个字被取走：adr := mem[pc], pc++. (4) 计算有效地址。(5) 如果ir_d寄存器为真，那么adr寄存器被加载到pc寄存器中。(也可以提前一个周期检查，所以在没有跳转的情况下，有效地址的计算被跳过，尽管这在有流水线的情况下是不可行的)。(1) The next instruction is fetched, ir := mem[pc], and the pc is incremented. (2) The control performs a dispatch on the opcode field and on the d field, because this instruction uses an expanding opcode.	(3) The second word of the instruction is fetched: adr := mem[pc], pc++. (4) The effective address is calculated. (5) If the ir_d register is true, then the adr register is loaded into the pc register. (It is also possible to check for this a cycle earlier, so the effective address calculation is skipped in case of no jump, although that would not work in the presence of pipelining.)

---

(b) 给出Sigma16的基本数据通路（没有流水线）执行条件跳转指令的步骤顺序。讨论如何在流水线中处理条件跳转而不造成错误的执行。(b)	Give the sequence of steps executed by the basic datapath (without a pipeline) for Sigma16 as it executes a conditional jump instruction. Discuss how a conditional jump can be handled in a pipeline without causing incorrect execution.

* (1) 下一条指令被取走，ir := mem[pc]，pc被递增。(2) 控件对操作码字段和d字段进行调度，因为这条指令使用了一个扩展操作码。	(3) 指令的第二个字被取走：adr := mem[pc], pc++. . (4) 计算有效地址。(5) 取出源寄存器，与0进行比较，控制执行一个调度。 (6) 如果要进行跳转，有效地址被移到pc寄存器中。**流水线的问题是，在确定是否应该跳转之前，存在修改pc的风险。如果流水线的结构允许这种危险，那么最简单的解决方案就是通过插入一个气泡来阻滞流水线的运行**。(1) the next instruction is fetched, ir := mem[pc], and the pc is incremented. (2) the control performs a dispatch on the opcode field and on the d field, because this instruction uses an expanding opcode.	(3) the second word of the instruction is fetched: adr := mem[pc], pc++. . (4) the effective address is calculated. (5) the source register is fetched, compared with 0 and the control performs a dispatch.  (6) if the jump should take place, the effective address is moved into the pc register. The problem with a pipeline is that there is a risk of modifying the pc before it is determined whether the jump should take place. If the structure of the pipeline would allow this hazard, then the simplest solution is to stall the pipeline by inserting a bubble. 

# M1控制信号：Middle level: M1 control signal settings

![](/static/2021-11-10-04-03-57.png)

## Lea: load effective address

![](/static/2021-11-10-04-07-05.png)

## Load

![](/static/2021-11-10-04-09-08.png)

(1) 下一条指令被取走，ir := mem[pc]，pc被递增。(2) 控制对操作码字段和d字段进行调度，因为这条指令使用了一个扩展操作码。	(3) 指令的第二个字被取走：adr := mem[pc], pc++. (4) 有效地址被计算出来。(5) 有效地址的内存内容被获取并加载到目标寄存器中，控制算法停顿直到数据到达。为了实现这一点，存储器提供一个就绪信号，控制算法循环进行，直到就绪信号被断言。 (1) The next instruction is fetched, ir := mem[pc], and the pc is incremented. (2) The control performs a dispatch on the opcode field and on the d field, because this instruction uses an expanding opcode.	(3) The second word of the instruction is fetched: adr := mem[pc], pc++. (4) The effective address is calculated. (5) The contents of memory at the effective address is fetched and loaded into the destination register, and the control algorithm stalls until the data has arrived. To implement this, the memory provides a ready signal, and the control algorithm cycles until ready is asserted. 

## Jumpc0

![](/static/2021-11-10-04-10-45.png)

# M1电路设计-Low level: M1 control circuit

![](/static/2021-11-10-09-56-27.png)

# Start信号-Start: top of main loop (Control.hs)

![](/static/2021-11-10-10-09-22.png)

# 解码opcode-Decoding the opcode (Control.hs)

![](/static/2021-11-10-10-20-08.png)

![](/static/2021-11-10-10-30-01.png)

# 控制信号生成-Generating control signals (Control.hs)

![](/static/2021-11-10-10-35-57.png)

# Hydra查看多个信号-Keeping track of many individual signals

![](/static/2021-11-10-10-39-25.png)

* 不用record就得一个一个引用写出来

# 定义信号集合record-Define collection (Interface.hs)

![](/static/2021-11-10-10-41-50.png)

# 定义单个控制信号-Define control signals (Control.hs)

![](/static/2021-11-10-10-46-17.png)

Using a control signal (Datapath.hs)使用

* 数据通路中直接引用record，之后可以直接用特定控制信号名

![](/static/2021-11-10-10-47-03.png)

# ========

# The M1 circuit

![](/static/2021-11-10-10-59-49.png)

# Input/Output

![](/static/2021-11-10-14-18-31.png)

# 直接内存存取：Direct memory access (DMA)

![](/static/2021-11-10-14-40-15.png)

* 使用 DMA **将一个小的初始常量数据块【引导程序，常量数据，称为引导加载程序，是一种机器语言程序，它【将操作系统（或独立程序）读入内存并跳转到它】】存储到内存中**
  * DMA加载引导程序至内存，引导程序加载os至内存，并跳转

# 启动阶段：Startup

![](/static/2021-11-10-14-51-44.png)

使用 DMA 将一个小的初始常量数据块【引导程序，常量数据，称为引导加载程序，是一种机器语言程序，它【将操作系统（或独立程序）读入内存并跳转到它】】存储到内存中

# 引导：Bootstrapping

Every machine has a hardware facility that 每台机器都有一个硬件设施，

* **重置系统reset** – Resets the system
* **使用 DMA 将一个小的初始常量数据块【引导程序，常量数据，称为引导加载程序，是一种机器语言程序，它【将操作系统（或独立程序）读入内存并跳转到它】】存储到内存中** – Stores a small initial block of constant data into memory using DMA
* **将 pc 设置为该数据的地址（constant data）并启动控制** – Sets the pc to the address of that data and starts the control

**常量数据，称为引导加载程序，是一种机器语言程序，它【将操作系统（或独立程序）读入内存并跳转到它**】 The constant data, called bootstrap loader, is a machine language program that reads the Operating System (or standalone program) into memory and jumps to it

**引导加载程序保存在非易失性存储器中（例如闪存** The bootstrap loader is held in non-volatile memory (e.g. flash memory)

# M1引导程序：Bootstrapping the M1 using DMA

![](/static/2021-11-10-15-49-47.png)

# The full M1 system

![](/static/2021-11-10-16-04-40.png)

# System circuit (System.hs)

![](/static/2021-11-10-16-22-02.png)

# 运行机器语言程序：Running a machine language program

![](/static/2021-11-10-16-29-15.png)

## 两种执行方式-Two execution environments

![](/static/2021-11-10-16-32-29.png)

# 运行例子-Example program: ArrayMax

![](/static/2021-11-10-16-35-33.png)

Machine language program: ArrayMax.lst.txt

![](/static/2021-11-10-16-38-53.png)

![](/static/2021-11-10-16-39-05.png)

![](/static/2021-11-10-16-41-30.png)

![](/static/2021-11-10-16-44-54.png)
![](/static/2021-11-10-16-45-13.png)

* 主循环更新max值

![](/static/2021-11-10-16-46-24.png)

# sigma16仿真器-Running a program on the emulator

![](/static/2021-11-10-16-46-58.png)
![](/static/2021-11-10-16-47-19.png)
![](/static/2021-11-10-16-47-26.png)
![](/static/2021-11-10-16-48-05.png)

# M1电路运行程序-Running a program on M1 circuit

![](/static/2021-11-10-16-51-10.png)

# Running a program on M1 circuit (README.txt)

![](/static/2021-11-10-16-51-35.png)

# Simulating M1

![](/static/2021-11-10-17-04-51.png)

# DMA加载程序至内存-Reading program with DMA

注意程序一个word需要一个周期装入内存

![](/static/2021-11-10-17-07-39.png)

* bootstraping阶段

## 控制算法开始-Starting the control algorithm

![](/static/2021-11-10-17-09-20.png)

# 每个时钟周期的模拟输出-Simulation output for every clock cycle

![](/static/2021-11-10-17-17-42.png)

# 例子-某周期输出：Typical simulation output for one clock cycle

![](/static/2021-11-10-17-19-31.png)

# 例子-lea指令输出：Control state transitions: lea is first instruction

![](/static/2021-11-10-17-22-07.png)

# 指令完成消息- Message when an instruction has completed

![](/static/2021-11-10-17-30-18.png)

![](/static/2021-11-10-17-30-46.png)

# 驱动监控指令执行输出-Monitor instruction executions

![](/static/2021-11-10-17-31-48.png)

---

![](/static/2021-11-10-17-33-27.png)

* 完整输出太长了，需要关注特定事件

