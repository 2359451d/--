# Lecture 3 - Compilers and interpreters

![](/static/2021-02-07-22-54-28.png)

* [Lecture 3 - Compilers and interpreters](#lecture-3---compilers-and-interpreters)
* [翻译程序：Translators](#翻译程序translators)
* [解释器：Interpreters](#解释器interpreters)
  * [例子](#例子)
* [Compilers vs Interpreters](#compilers-vs-interpreters)
* [Tombstone diagrams](#tombstone-diagrams)
  * [例子](#例子-1)
  * [描述运行程序：Tombstone diagrams -  running ordinary programs](#描述运行程序tombstone-diagrams----running-ordinary-programs)
  * [翻译过程：Tombstone diagrams: translation](#翻译过程tombstone-diagrams-translation)
  * [解释过程：Tombstone diagrams- interpretation](#解释过程tombstone-diagrams--interpretation)
* [Real Machine vs Virtual Machine](#real-machine-vs-virtual-machine)
  * [硬件模拟例子：Hardware Emulation](#硬件模拟例子hardware-emulation)
* [解释型编译器：Interpretive compilers](#解释型编译器interpretive-compilers)
  * [例子：JDK](#例子jdk)
* [（Selective）JIT(Just-in-time) compilers](#selectivejitjust-in-time-compilers)
* [可移植编译器：Portable compilers](#可移植编译器portable-compilers)
  * [例子：portable compiler kit](#例子portable-compiler-kit)
* [自举：Bootstrapping](#自举bootstrapping)
  * [例子](#例子-2)

# 翻译程序：Translators

![](/static/2021-02-07-22-55-23.png)

:orange: 定义

* **接受用一种语言S表达的代码，并将其翻译成用另一种语言T表达的等价代码** An S → T translator accepts code expressed in one language S, and translates it to equivalent code expressed in another language T
  * `S` - 源语言 source language
  * `T` - 目标语言 target language
* 翻译程序例子
  * compilers 编译器
  * assemblers 汇编程序
  * high-level translators 高级翻译程序 (transpilers)
  * decompilers 反编译器

---

:orange: 例子

![](/static/2021-02-07-23-00-54.png)
![](/static/2021-02-07-23-04-13.png)

* **compiler**
  * 将高级PL翻译成低级语言代码 translates high-level PL code to low level code
* **assembler**
  * 将汇编语言翻译成机器码 translates assembly language code to the corresponding machne code
* **A high-level translator / transpiler**
  * 将一个高级PL中的代码翻译成另一个高级PL中的代码。 translates code in one high-level PL to code in another high-level PL
* **A disassembler / decompiler**
  * 反汇编器/反编译器将低级代码翻译成高级PL代码 translates lowlevel code to high(er)-level PL code

# 解释器：Interpreters

![](/static/2021-02-07-23-05-42.png)

:orange: 定义

* **一个S解释器接受用S语言表达的代码，并 "立即 "执行该代码，即不产生中间对象代码** An S interpreter accepts code expressed in language S, and “immediately” executes that code, i.e., no intermediate object code is generated.
* **解释器的工作原理是每次获取、分析和执行一条指令** An interpreter works by fetching, analysing, and executing one instruction at a time.
  * **如果一条指令被反复获取，就会被反复分析。这很耗时，除非指令的格式非常简单**。  If an instruction is fetched repeatedly, it will be analysed repeatedly. This is time-consuming unless instructions have very simple formats.

---

![](/static/2021-02-07-23-10-02.png)

:orange: 解释程序比执行本地机器代码要慢 Interpreting a program is slower than executing native machine code:

* 解读高级语言比解读中级(中间代码)语言（如JVM代码）要慢得多。 Interpreting a high-level language is much slower than interpreting an intermediate-level language, such as JVM code.
* 另一方面，解释一个程序会削减编译时间 On the other hand, interpreting a program cuts out compile-time.

---

:orange: 解释程序应用场景 Interpretation is sensible when (e.g.):

![](/static/2021-02-07-23-12-33.png)

* 用户正在交互式地输入指令，并希望在输入下一条指令之前看到每条指令的结果 a user is entering instructions interactively, and wishes to see the results of each instruction before entering the next one
* 程序用完即弃(执行速度不重要) the program is to be used once then discarded (so execution speed is unimportant)
* 每条指令只执行一次或几回 each instruction will be executed only once or a few times
* 指令的格式非常简单 the instructions have very simple formats
* 要求程序代码具有高度的可移植性 the program code is required to be highly portable

## 例子

![](/static/2021-02-07-23-12-47.png)

Unix command language interpreter (shell):
– 用户每次只输入一条命令。 The user enters one command at a time.
– shell读取命令，解析命令，确定命令名和参数，然后执行。 The shell reads the command, parses it to determine the command name and argument(s), and executes it.

JVM interpreter:
– 一个JVM程序由 "字节码 "组成。 A JVM program consists of “bytecodes”.
– 解释器每次获取、解码并执行一个字节码。The interpreter fetches, decodes, and executes one bytecode at a time.

# Compilers vs Interpreters

![](/static/2021-02-07-23-14-51.png)

不要混淆编译器和解释器

**编译器**将源码翻译成目标码 A compiler translates source code to object code.

* 它不执行源代码或对象代码。It does not execute the source or object code.

**解释器**一次只执行一条源代码指令 An interpreter executes source code one instruction at a time.

* 它不翻译源代码 It does not translate the source code.

# Tombstone diagrams

![](/static/2021-02-07-23-17-43.png)

* Tombstone Diagrams gives representation of intepreters, compiler,programs, hardwares...

## 例子

![](/static/2021-02-07-23-21-42.png)
![](/static/2021-02-07-23-23-35.png)

## 描述运行程序：Tombstone diagrams -  running ordinary programs

![](/static/2021-02-07-23-24-37.png)

* 给定用`M`机器码表示的程序`P`，可以在机器`M`上运行`P`Given a program P expressed in M machine code, we can run P on machine M
  * 前提是程序`P`必须用`M`机器码表示，因为机器`M`只能运行机器码`M`

:orange: 例子

![](/static/2021-02-07-23-27-29.png)

## 翻译过程：Tombstone diagrams: translation

![](/static/2021-02-07-23-32-40.png)

* 利用`S->T`，可以在`M`上运行该translator，将程序`P`（`S`语言）翻译成`T`语言的`P`程序

---

:orange: 例子 - compiling a program

![](/static/2021-02-07-23-34-49.png)

:orange: 例子 - 分步编译 compiling a program in stages

![](/static/2021-02-07-23-36-30.png)

:orange: 例子 - 编译编译器 compling a compiler

![](/static/2021-02-07-23-38-03.png)

* 如果有一个`C->x86`的编译器，则可以将用`C`写成的`Java->JVM`编译器转换成，用`x86`写成的`Java->JVM`编译器

:orange: 例子 - what can and can't be done

![](/static/2021-02-07-23-40-37.png)

## 解释过程：Tombstone diagrams- interpretation

![](/static/2021-02-07-23-41-42.png)

Given:
– an S interpreter, expressed in M machine code 一个S解释器，用M机器代码表示
– a program P, expressed in language S 一个程序P，用语言S表示

* 可以在机器`M`上，用解释器`S`（`M`机器码写成）解释程序`P`（S语言写成） 

---

:orange: 例子 - interpreting ordinary programs

![](/static/2021-02-07-23-43-30.png)

# Real Machine vs Virtual Machine

![](/static/2021-02-07-23-47-51.png)

A real machine is one whose machine code is executed by hardware.

* 真机 - 是指机器代码由硬件执行的机器

A virtual machine (or abstract machine) is one whose “machine code” is executed by an interpreter.

* 虚拟机（或抽象机）是指 "机器代码 "由解释器执行的机器。

## 硬件模拟例子：Hardware Emulation

![](/static/2021-02-07-23-52-58.png)

Suppose we have designed the architecture and instruction set of a new machine, MVM.假设我们设计了一台新机器MVM的架构和指令集
A hardware prototype of MVM will be expensive to build and modify.MVM的硬件原型制造，修改成本高

![](/static/2021-02-07-23-54-43.png)
![](/static/2021-02-07-23-56-15.png)

# 解释型编译器：Interpretive compilers

![](/static/2021-02-07-23-56-35.png)

* **编译器**将源程序翻译成原生机器代码需要相当长的时间，但后续执行速度很快。 A compiler takes quite a long time to translate the source program to native machine code, but subsequent execution is fast.
* **解释器**立即开始执行源程序，但执行速度很慢。An interpreter starts executing the source program immediately, but execution is slow.
* **解释型编译器**是一个很好的折中方案。它将源程序翻译成虚拟机（VM）代码，随后进行解释。An interpretive compiler is a good compromise. It translates the source program into virtual machine (VM) code, which is subsequently interpreted.

:orange: 解释型编译器将快速翻译与适度快速执行相结合，只要：An interpretive compiler combines fast translation with moderately fast execution, provided that:

* 虚拟机代码为中间级（比源代码低级，比原生机器代码高级）。 the VM code is intermediate-level (lower-level than the source code, higher-level than native machine code)
* 从源语言到虚拟机代码的翻译简单快捷 translation from the source language to VM code is easy and fast
* **VM指令具有简单的格式（因此可以被解释器快速分析**）the VM instructions have simple formats (so can be analyzed quickly by an interpreter).

## 例子：JDK

![](/static/2021-02-08-00-01-37.png)
![](/static/2021-02-08-00-03-18.png)
![](/static/2021-02-08-00-05-54.png)

JDK包含java解释性编译器，&解释器

This is based on the JVM (Java Virtual Machine), a virtual machine initially designed specifically for running Java programs: 这是基于JVM(Java虚拟机)，最初是专门为运行Java程序而设计的虚拟机。
– JVM provides powerful instructions that implement object creation, method calls, array indexing, etc. JVM提供了强大的指令，实现对象创建、方法调用、数组索引等。
– JVM instructions (often called “bytecodes”) are similar in format to native machine code: opcode + operand.JVM指令（通常被称为 "字节码"）的格式与本机代码类似：操作码+操作数。

# （Selective）JIT(Just-in-time) compilers

![](/static/2021-02-08-00-06-52.png)

A just-in-time (JIT) compiler translates virtual machine code to native machine code just prior to execution. 及时编译器(JIT)在执行前将虚拟机代码转化为本地机器代码。

* Java JIT编译器有选择地翻译JVM代码 More usually, a Java JIT compiler translates JVM code selectively:
  * 解释器和JIT编译器一起工作。The interpreter and JIT compiler work together.
  * 解释器的工具是用来计算方法调用。The interpreter is instrumented to count method calls.
  * 当解释器发现某个方法频繁调用，它就会告诉JIT编译器将该方法翻译成本地代码。 When the interpreter discovers that a method is “hot” (called frequently), it tells the JIT compiler to translate that particular method into native code.

# 可移植编译器：Portable compilers

![](/static/2021-02-08-00-11-53.png)

如果一个程序能够以最小的改动在不同的机器上运行，那么它就是可移植的。A program is portable if it can be made to run on different machines with minimal change:

* 一个生成原生机器代码的编译器在特殊意义上是不可移植的。**如果必须改变它的目标机器，它的代码生成器必须被替换**。 A compiler that generates native machine code is unportable in a special sense. If it must be changed to target a different machine, its code generator must be replaced.
* 然而，生成合适的虚拟机代码的编译器是可以移植的。However, a compiler that generates suitable virtual machine code can be portable.

## 例子：portable compiler kit

![](/static/2021-02-08-00-14-53.png)

* 存在问题：需要考虑到底先实现哪个(解释器or编译器)

![](/static/2021-02-08-00-22-36.png)
![](/static/2021-02-08-00-24-22.png)

# 自举：Bootstrapping

![](/static/2021-02-08-00-25-00.png)

假设一个翻译程序 `S->T`

* 自举 --- 程序语言编译器用自身的语言及其特性来编译自己 Such a translator can be used to translate itself! This is called bootstrapping.

:orange:自举是改进现有编译器的有用工具 Bootstrapping is a useful tool for improving an existing compiler:

* making it compile faster
* making it generate faster object code.

:orange: <font color="deeppink">我们可以通过将虚拟机代码翻译成本机代码，来自举一个可移植的编译器，使之成为一个真正的编译器</font>。 We can bootstrap a portable compiler to make a true compiler, by translating virtual machine code to native machine code.

## 例子

![](/static/2021-02-08-00-29-55.png)
![](/static/2021-02-08-00-36-30.png)
![](/static/2021-02-08-00-37-42.png)
![](/static/2021-02-08-00-40-20.png)
![](/static/2021-02-08-00-41-38.png)

