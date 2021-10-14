# Native Code Generation

* 真实机器的特点 Characteristics of real machines
* § **寄存器分配** Register allocation
  * compared to lecture 8
* § 中间代码表示· Intermediate representation
* § 代码选择 code selection

---

* [Native Code Generation](#native-code-generation)
* [Characteristics of real machines](#characteristics-of-real-machines)
* [寄存器分配：Register allocation](#寄存器分配register-allocation)
  * [基础块：basic-blocks](#基础块basic-blocks)
    * [例子-复杂BB](#例子-复杂bb)
    * [例子-BB分配寄存器](#例子-bb分配寄存器)
* [表示JUMP：控制流图寄存器分配 - Register allocation: control-flow graphs](#表示jump控制流图寄存器分配---register-allocation-control-flow-graphs)
  * [例子](#例子)
  * [活性分析：数据流方程 Liveness analysis: data flow equations](#活性分析数据流方程-liveness-analysis-data-flow-equations)
  * [Liveness analysis: algorithm](#liveness-analysis-algorithm)
* [寄存器分配后代码选择 & 生成](#寄存器分配后代码选择--生成)
* [中间表示：Intermediate representation](#中间表示intermediate-representation)
  * [IR Tree 例子](#ir-tree-例子)
  * [Summary of IR trees](#summary-of-ir-trees)
* [目标指示建模：Modelling target instructions](#目标指示建模modelling-target-instructions)
* [RISC指令架构例子：Example: Jouette](#risc指令架构例子example-jouette)
  * [Example: modelling Jouette instructions](#example-modelling-jouette-instructions)
* [代码选择：Code Selection - method](#代码选择code-selection---method)
  * [例子 code selection](#例子-code-selection)
* [最大吞噬-Code selection: maximal-munch algorithm](#最大吞噬-code-selection-maximal-munch-algorithm)

# Characteristics of real machines

(为什么不能使用VM中代码生成的概念)

代码选择是困难的，因为。 Code selection is difficult because:

* CISC机器有非常复杂的指令，有多种寻址模式 – CISC machines have very complicated instructions, with multiple addressing modes.
* 即使是RISC机器也有一些相当复杂的指令 – even RISC machines have some fairly complicated instructions. 

**寄存器分配是一个问题**。 Register allocation is an issue:

* 应尽可能多地使用寄存器。 Registers should be used as much as possible.
  * cpu中，与主存快速交互。用来代码生成，中间结果
* RISC机器通常只有通用的寄存器 – RISC machines typically have only general-purpose registers.
* CISC机器通常有各种特殊用途的寄存器（例如，int寄存器、float寄存器、地址寄存器） – CISC machines typically have a variety of specialpurpose registers (e.g., int registers, float registers, address registers).

# 寄存器分配：Register allocation

争取尽可能多地使用寄存器作为局部变量和表达式的中间结果。 Aim to use registers as much as possible for local variables and intermediate results of expressions.

问题：**寄存器的数量是有限的**  Problem: The number of registers is limited

* - 特别是当有些是专用的（如FP、SP）。 – especially when some are dedicated (e.g., fp, sp). 

如果不同的变量在不同的时间内是存活的，它们可以被分配到同一个寄存器中（**生命周期不重叠情况下，可以复用同一个寄存器**） Opportunity: Different variables can be allocated to the same register if they are live at different times

* 在这里，只有**当一个变量【以后可能被检查时】，它才被认为是活的**。 Here, a variable is deemed to be live only if it might be inspected later. 

## 基础块：basic-blocks

:orange: **利用BB，画出每个变量的生命周期，利用【非重叠部分来分配寄存器】**

基本块（BB）是一个直线型的**指令序列**。 A basic-block (BB) is a straight-line sequence of instructions:

* 除了在BB结束时，不允许JUMp – no jumps except at the end of a BB
* 除了BB的开始，不允许JUMP – no jumps to anywhere except the start of a BB. 

在一个BB中，使用临时变量分解复杂的表达式，**使每个赋值指令最多包含一个运算符**。例如。 Within a BB, break up complicated expressions using temporary variables, such that each assignment instruction contains at most one operator. E.g.:

* `a = (b+c)*(d-e);`

```txt
// 一个BB例子
t1 ← b + c
t2 ← d – e
a ← t1 × t2
```

注意：BB与块结构无关。Note: Basic-blocks are unrelated to block structure.

### 例子-复杂BB

![](/static/2021-05-10-16-42-09.png)

### 例子-BB分配寄存器

在BB中，确定每个变量的生命周期，然后分配寄存器。 Within the BB, determine where each variable is live, then allocate registers:

![](/static/2021-05-10-16-47-49.png)

# 表示JUMP：控制流图寄存器分配 - Register allocation: control-flow graphs

控制流图是一个有向图，其中。 A control-flow graph is a directed graph in which:

* 每个顶点都是一个BB each vertex is a BB 
* 每条边都是从一个BB的终点跳到另一个BB的起点的。 each edge is a jump from the end of one BB to the start of another BB
* 一个顶点被指定为入口点 – one vertex is designated as the entry point
* 一个顶点被指定为出口点。 – one vertex is designated as the exit point.

## 例子

![](/static/2021-05-10-16-59-05.png)

## 活性分析：数据流方程 Liveness analysis: data flow equations

为控制流图中的**每个BB**`b`定义以下集合 Define the following sets for each BB b in a control-flow graph

* in[b]是**存活在b开头的**变量集 in[b] is the set of variables live at the start of b
* out[b]是**存活在b结束时**的变量集合。 out[b] is the set of variables live at the end of b
* use[b]是变量的集合，以便b**访问v**（在对v进行任何更新之前） use[b] is the set of variables v such that b inspects v (before any update to v) 
* def[b]是变量v的集合，使b**更新v**（在对v进行任何检查之前） def[b] is the set of variables v such that b updates v (before any inspection of v) 

需要满足以下数据流方程式 Data flow equations for liveness analysis

* `in[b] = use[b] U (out[b] – def[b]) `
* `out[b] = in[b'] U in[b'' ] U …`
  * 其中b'，b''，...是b在流程图中的后继。(where b', b'', … are the successors of b in the flow graph) 

## Liveness analysis: algorithm

![](/static/2021-05-10-17-59-38.png)

控制流程图中每个基本块的使用和def集。 The use and def sets for each basic block in the control flow graph

![](/static/2021-05-10-18-00-20.png)

联立方程式

![](/static/2021-05-10-18-00-54.png)

替换，得到最终 in[BB] 方程，，

![](/static/2021-05-10-18-02-00.png)

---

![](/static/2021-05-10-18-02-50.png)

这个过程终止，因为每一步都只能增加集合，而它们的增加不能超过所有变量的集合。The process terminates because each step can only increase the sets, and they can’t increase beyond the set of all variables.

# 寄存器分配后代码选择 & 生成

# 中间表示：Intermediate representation

通过使用源程序的低级中间表示（IR），简化了本地代码的生成。 Native code generation is simplified by using a low-level intermediate representation (IR) of the source program. 

IR应该能够。The IR should be capable of:

* **代表源代码的语义** – representing the semantics of the source code
* **代表目标机指令的语义** – representing the semantics of target-machine instructions

IR最好是独立于目标机的 The IR should ideally be independent of the target machine

## IR Tree 例子

![](/static/2021-05-10-18-14-54.png)

## Summary of IR trees

![](/static/2021-05-10-18-15-53.png)

# 目标指示建模：Modelling target instructions

使用IR树模式对每个目标机器指令的语义进行建模 Model the semantics of each target machine instruction using an IR tree pattern.

注意：我们使用**相同的IR对源代码和目标指令**进行建模 Note: We use the same IR to model both source code and target instructions

# RISC指令架构例子：Example: Jouette

Jouette is a hypothetical RISC machine

Jouette architecture:

– general-purpose registers r0, r1, r2, ..., r31

– **r0 always contains zero**.

![](/static/2021-05-10-18-36-05.png)

* 指令集

## Example: modelling Jouette instructions

有可能>1个模式来模拟1个指令。It’s possible for >1 patterns to model 1 instruction.

![](/static/2021-05-10-18-39-23.png)

![](/static/2021-05-10-18-43-11.png)

# 代码选择：Code Selection - method

将源代码或AST翻译成IR树 Translate the source code or AST into an IR tree.

“Cover” the tree with IR instruction patterns

生成与这些指令模式相对应的代码 Emit code corresponding to these instruction patterns

每步执行寄存器分配 performing register allocation as you go.

## 例子 code selection

![](/static/2021-05-10-19-15-10.png)

* `LOAD`  `ADDI` 可存在替换

![](/static/2021-05-10-19-16-36.png)

# 最大吞噬-Code selection: maximal-munch algorithm

from the largest,  then left to right

![](/static/2021-05-10-19-20-32.png)

使用指令模式`ps`来覆盖IR树`t` To cover the IR tree t using instruction patterns ps:

* 找出ps中覆盖`t`顶部的最大模式`p` Find the largest pattern p in ps that covers the top of t.
* 对于t的每个未覆盖的子树s（从左到右）For each uncovered subtree s of t (from left to right):
  * Cover s using ps.
  * Emit the instruction corresponding to p

The time complexity is O(size of t).

代码生成在以下意义上是最佳的 The emitted code is optimal in the sense that:

* 没有两个相邻的模式可以被一个模式所取代 no two adjacent patterns could be replaced by a single pattern
* 指令的数量是最少的 the number of instructions is minimal

