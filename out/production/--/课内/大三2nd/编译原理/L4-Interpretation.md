# Week4 - Interpretation

概述

* 虚拟机解释程序
* 案例研究。SVM
* 案例研究。Java中的SVM解释器

* [Week4 - Interpretation](#week4---interpretation)
* [Overview](#overview)
* [虚拟机解释程序：Virtual Machine Interpretation](#虚拟机解释程序virtual-machine-interpretation)
* [案例学习：SVM](#案例学习svm)
  * [SVM存储空间&寄存器](#svm存储空间寄存器)
    * [存储可视例子](#存储可视例子)
  * [SVM指令集：Instruction Set](#svm指令集instruction-set)
  * [栈存储计算例子](#栈存储计算例子)
* [编写解释器：Writing an Interpreter](#编写解释器writing-an-interpreter)
  * [JAVA SVM interpreter](#java-svm-interpreter)
  * [初始化](#初始化)
  * [执行指令](#执行指令)
* [编译：Compilation](#编译compilation)
  * [Overview](#overview-1)
  * [编译阶段：Compilation Phases](#编译阶段compilation-phases)
* [案例学习：Fun](#案例学习fun)
  * [程序例子](#程序例子)
  * [Fun语言语法：Fun Syntax](#fun语言语法fun-syntax)
  * [Fun编译器& 编译阶段（Fun Compiler）](#fun编译器-编译阶段fun-compiler)
  * [Fun驱动器：Fun Driver](#fun驱动器fun-driver)
* [抽象语法树：Abstract Syntax Tree](#抽象语法树abstract-syntax-tree)
  * [Fun表达式语法-AST例子](#fun表达式语法-ast例子)
  * [Fun命令语法 - AST例子](#fun命令语法---ast例子)
  * [Fun程序语法 - AST例子](#fun程序语法---ast例子)
  * [Fun变量声明&类型语法 - AST](#fun变量声明类型语法---ast)
  * [Fun过程语法 - AST](#fun过程语法---ast)
  * [Fun编译3阶段AST例子](#fun编译3阶段ast例子)

# Overview

![](/static/2021-02-25-16-56-04.png)

一个S解释器接受用S语言表达的代码，并立即执行该代码。An S interpreter accepts code expressed in language S, and immediately executes that code

* 假设要解释的代码只是一个简单的指令序列（包括有条件/无条件跳转）。 Assume that the code to be interpreted is just a sequence of simple instructions (including conditional/unconditional jumps).
* 则解释程序的工作流程
  * **初始化状态** First it initializes the state.
  * **重复获取，分析，执行下条指令** Then it repeatedly fetches, analyses, and executes the next instruction.
  * **执行完一条指令后可能存在状态更新** Executing an instruction updates the state as required.

# 虚拟机解释程序：Virtual Machine Interpretation

![](/static/2021-02-25-16-59-44.png)

* VM通常包含以下**指令**
  * load/store instructions
  * arithmetic/logical instructions
  * conditional/unconditional jumps
  * call/return instructions
  * ...
* VM状态（state）通常由以下组成
  * 存储 storage(code,data)
  * 寄存器 registers(status, program counter, stack pointer,...)

# 案例学习：SVM

![](/static/2021-02-25-17-02-01.png)

* SVM适用于简单命令式编程语言 simple imperative PLs.

---

源码 & SVM指令代码

![](/static/2021-02-25-17-04-06.png)

## SVM存储空间&寄存器

![](/static/2021-02-25-17-06-52.png)

VM status由存储 & 寄存器组成

SVM存储（空间）

* code store/storage - 固定32KB的连续指令存储空间
* data store/storage - 固定 32K字的连续栈空间，存储全局&局部数据

SVM主要寄存器

* **pc** - program counter
  * 指向下一指令地址
* **sp** - stack pointer
  * 指向栈顶
* **fp** - frame pointer
  * 指向栈顶的低地址
* **status**
  * 指明程序状态， running, failed, halted

:orange: 栈，自顶向下生长，

![](/static/2021-02-25-19-12-28.png)

### 存储可视例子

![](/static/2021-02-25-17-22-08.png)

![](/static/2021-02-25-19-08-07.png)

## SVM指令集：Instruction Set

![](/static/2021-02-25-19-09-15.png)
![](/static/2021-02-25-19-09-54.png)

## 栈存储计算例子

![](/static/2021-02-25-19-14-32.png)

# 编写解释器：Writing an Interpreter

![](/static/2021-02-25-19-15-11.png)

* c or java编写

in such an interpreter:

– **虚拟机状态（state**）由一组**变量**来表示 the virtual machine state is represented by a group of variables
  - state 由 存储空间 & 寄存器组成
– 每条指令都是通过**检查和/或更新虚拟机状态（state**）来执行的。 each instruction is executed by inspecting and/or updating the virtual machine state

## JAVA SVM interpreter

状态由变量表示，指令通过检查/和或更新状态来执行【**注意是解释器**】

![](/static/2021-02-25-19-17-44.png)

* `final byte` 指令

![](/static/2021-02-25-20-58-35.png)

* 存储 - array数组
  * `byte[]` code 存储
  * `int[]` data 存储
* 寄存器 - `int`
* 状态 - `final byte`
  * `RUNNING = 0`
  * `FAILED = 1`
  * `HALTED = 2`

## 初始化

初始化状态，重复匹配下条指令&执行当前指令

![](/static/2021-02-25-21-01-07.png)

## 执行指令

![](/static/2021-02-25-21-01-53.png)

* 先确认操作码 opcode

---

![](/static/2021-02-25-21-02-21.png)

* 算术 & 逻辑指令 arithmetic/logical instructions

![](/static/2021-02-25-21-13-16.png)

* load/store instructions

![](/static/2021-02-25-21-13-40.png)

* jump/halt instructions

# 编译：Compilation

![](/static/2021-02-25-21-14-56.png)

* 编译阶段 compilation phases
  * 语法分析（&文法分析） syntactic analysis
  * 上下文分析（语义分析） contextual analysis
  * 代码生成 code generation
* 抽象语法树 abstract syntax trees
* Fun 案例学习

## Overview

![](/static/2021-02-25-21-20-59.png)

A **S → T** compiler translates a source program in S to object code in T, if it conforms to the source language’s syntax and scope/type rules. S → T编译器将**S中的源程序翻译成T中的对象代码**，如果它符合源语言的语法和范围/类型规则

这表明编译器可以分解为三个阶段。

* syntactic analysis 语法分析阶段
* contextual analysis 上下文分析阶段（语义分析）
* code generation 代码生成阶段

## 编译阶段：Compilation Phases

![](/static/2021-02-25-21-37-59.png)

* **语法分析 Syntactic analysis**
  * 根据源语言的语法，对源程序进行解析，检查其是否完善，并确定其短语结构 Parse the source program to check whether it is well formed, and to determine its phrase structure, in accordance with the source language’s syntax.
  * 也包含了文法分析
* **上下文分析（语义分析） Contextual analysis**
  * 分析解析程序，检查它是否符合源语言的作用域规则和类型规则。 Analyze the parsed program to check whether it conforms to the source language’s scope rules and type rules
* **代码生成 code generation**
  * 根据源语言的语义，将解析后的程序翻译成对象代码。 Translate the parsed program to object code, in accordance with the source language’s semantics.

---

图示

![](/static/2021-02-25-21-55-10.png)

* AST便于表示源程序

# 案例学习：Fun

![](/static/2021-02-25-21-55-53.png)

* 简单命令式语言
* 定义很多全局变量 & 过程/函数，总包括一个`main()`过程
* Fun 过程/函数可以有单参数
  * 也可以声明局部变量
  * 函数返回结果，过程无返回值
* 两种数据类型
  * `int`
  * `bool`
* 命令
  * 赋值 assignment
  * 函数过程调用 proc/func call
  * if命令 if-command
  * while命令 while-command
  * 串行命令 sequential command

## 程序例子

![](/static/2021-02-25-22-05-57.png)
![](/static/2021-03-15-21-24-03.png)

空格，缩进，EOL无严格要求

## Fun语言语法：Fun Syntax

![](/static/2021-02-25-22-08-09.png)

* `'='` terminal symbol 终结符号

![](/static/2021-02-25-22-09-30.png)

* `expr`sequential expression组成

...

## Fun编译器& 编译阶段（Fun Compiler）

![](/static/2021-02-25-22-11-26.png)

* java类
  * 语法分析器 syntactic analyser (`FunLexer`, `FunParser`)
* 上下文分析器 contextual analyser (`FunChecker`)
* 代码生成器 code generator (`FunEncoder`)

---

FUN编译器顺序调用以下类

* 语法分析器 - **对源程序进行词典和解析，打印任何错误信息，并生成AST。然后打印AST**。 The syntactic analyser lexes and parses the source program, printing any error messages, and generates an AST. Then the AST is printed.
* 上下文分析器 - **执行作用域/类型检测，打印任何错误信息** The contextual analyser performs scope/type checking, printing any error messages
* 代码生成器 - **将对象代码转移到SVM代码存储中。然后打印对象代码**

:orange: 语义分析 & 上下文分析期的任意阶段，如果检测到错误，编译结束 Compilation is terminated after syntactic or contextual analysis if any errors are detected

## Fun驱动器：Fun Driver

![](/static/2021-02-25-22-41-16.png)

* `FunRun`
  * **编译源程序为SVM对象码程序** It compiles the source program into an SVM object program
    * source code -> SVM, driver提供接口
  * **如果未检测到任何错误，【调用SVM解释器执行程序】** If no errors are detected, it calls the SVM interpreter to run the object program.
    * SVM，通过更新state执行指令

:orange: 即，SVM只是个解释器，Fun源程序通过Fun编译器生成SVM对象码后，通过SVM进行解释

# 抽象语法树：Abstract Syntax Tree

AST

![](/static/2021-02-25-22-43-04.png)

> An abstract syntax tree (AST) is a convenient way to represent a source program’s phrase structure 抽象语法树(AST)是表示源程序短语结构的一种方便的方式

AST结构 structure

* 每个叶结点 - 标识符或字面值 Each leaf node represents an identifier or literal.
* 每个内部节点对应一个源语言结构体（例如，一个变量声明或命令）。内部节点的子树代表该结构体的各个部分。 Each internal node corresponds to a source language construct (e.g., a variable declaration or whilecommand). The internal node’s subtrees represent the parts of that construct

:orange: 抽象语法树是更紧凑的语法树 ASTs are much more compact than syntax trees
(§week 1)

## Fun表达式语法-AST例子

![](/static/2021-02-25-22-46-05.png)

注意：AST没有区分（Fun的）exprs、sec-exprs等语法：它们都看为表达式。Note: The AST makes no distinction between exprs, sec-exprs, etc.: they are all just expressions.

---

![](/static/2021-02-26-19-18-48.png)

## Fun命令语法 - AST例子

![](/static/2021-02-26-17-22-27.png)

* sequential command

---

![](/static/2021-02-26-17-24-29.png)

## Fun程序语法 - AST例子

![](/static/2021-02-26-19-20-07.png)

## Fun变量声明&类型语法 - AST

![](/static/2021-02-26-19-28-45.png)

## Fun过程语法 - AST

![](/static/2021-02-26-19-31-37.png)

* PROC
  * 无返回值
  * `formal` 参数前缀
  * `var-decl`参数变量声明
  * 过程体 `com`

`NOFORMAL`无参数

## Fun编译3阶段AST例子

![](/static/2021-02-26-19-37-54.png

![](/static/2021-02-26-19-38-04.png)

* syntactic analysis

![](/static/2021-02-26-19-38-43.png)

* contextual analysis
  * scope & type

![](/static/2021-02-26-19-40-21.png)

* SVM object code generated after code generation