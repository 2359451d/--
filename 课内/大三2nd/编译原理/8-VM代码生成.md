# VM Code Generation

![](/static/2021-03-21-12-57-00.png)

* Aspects of code generation
* Address allocation
* Code Selection
* Fun code generator
* java - representing addresses
* handling jumps

* [VM Code Generation](#vm-code-generation)
* [Aspects of code generation](#aspects-of-code-generation)
* [Fun编译例子：Fun Compilation](#fun编译例子fun-compilation)
* [Address Allocation](#address-allocation)
  * [地址表生成 & 使用](#地址表生成--使用)
* [Code selection](#code-selection)
  * [Code Template](#code-template)
  * [Fun->SVM对象码模板例子：code templates](#fun-svm对象码模板例子code-templates)
  * [code templates vs action](#code-templates-vs-action)
* [Handling jumps](#handling-jumps)
  * [Fun->SVM code templates](#fun-svm-code-templates)
* [Code generation with ANTLR](#code-generation-with-antlr)
* [Storage organization](#storage-organization)
  * [Storage for global and local variables](#storage-for-global-and-local-variables)
  * [stack frame](#stack-frame)
  * [SVM变量存储例子：Storage for global and local variables in SVM](#svm变量存储例子storage-for-global-and-local-variables-in-svm)
* [Fun 过程声明 代码生成](#fun-过程声明-代码生成)
* [Fun 参数 代码生成](#fun-参数-代码生成)
* [Fun program 代码生成](#fun-program-代码生成)
* [Fun Compiler3阶段](#fun-compiler3阶段)
* [地址表示：Representing addresses](#地址表示representing-addresses)

# Aspects of code generation

![](/static/2021-03-21-12-58-17.png)

> 将源程序（AST）转换为目标对象码 Code generation translates the source program (represented by an AST / syntax tree) into equivalent object code.

* from AST to get some sequence of instructions for VM

:orange: 3 phases/steps

* address allocation
  * 表示源程序中每个变量的地址 deciding the representation and address of each variable in the source program
* code selection
  * 选择生成对象码 selecting and generating object code
* register allocation
  * 分配寄存器，存储局部临时变量 assigning resigers to local and temporary variables

---

![](/static/2021-03-21-13-02-05.png)

* 基于栈VM的代码生成 stack based VMs

# Fun编译例子：Fun Compilation

![](/static/2021-03-21-13-02-49.png)

![](/static/2021-03-21-13-03-11.png)

* from AST to obtain sequence of instructions for VM

![](/static/2021-03-21-13-07-46.png)

* 注意维护地址表 address table

# Address Allocation

:orange: Address Table

> 地址分配需要收集和传播已声明的变量、程序等信息。 Address allocation requires collection and dissemination of information about declared variables, procedures, etc

* 类似 type checking(收集传播 已声明变量的信息)
* 代码生成器维护地址表，包含每个已声明变量/过程/...标识符对应的地址 The code generator employs an address table. This contains the address of each declared variable, procedure, etc 

---

![](/static/2021-03-21-13-17-39.png)

* 根据变量大小，为其分配对应的 连续地址 Allocate consecutive addresses to variables, taking account of their sizes

:candy: Fun中变量大小为1

## 地址表生成 & 使用

:orange: variable

* **每声明一个变量(过程/...标识符)**，为其分配一个合适大小的地址 At each variable declaration, allocate a suitable address
  * 地址表中填入新 标识符-地址 的映射 and put the identifier and address into the address table
* **每当变量在命令/表达式中使用**，在地址表中检索该变量对应的地址 Wherever a variable is used (e.g., in a command or expression), retrieve its address

:orange: procedure

* **每声明一个过程，将过程绑定的标识符与地址填入地址表** At each procedure declaration, note the address of its entry point, and put the identifier and address into the address table.
* **每调用一个过程，从地址表中检索其地址** Wherever a procedure is called, retrieve its address

# Code selection

![](/static/2021-03-21-13-20-17.png)

* 代码生成器遍历AST code generator walk the AST
  * 负责给每个AST中结构生成合适对象码 For each construct (expression, command, etc.) in the AST, the code generator must emit suitable object code
* 所以必须决定每个结构（被选中的）对应的对象码 The developer must plan what object code will be selected by the code generator

## Code Template

> 对于**源语言中的每个构造，开发者应该设计一个代码模板。这规定了语法树中的特定元素将选择/对应什么对象码**。 For each construct in the source language, the developer should devise a code template. This specifies what object code will be selected for the specific element in syntax tree.

计算表达式的代码模板应该包括评估任何子表达式的代码，以及任何其他必要的指令。 The code template to evaluate an expression should include code to evaluate any subexpressions, together with any other necessary instructions.

执行命令的代码模板应该包括评估任何子表达式的代码和执行任何子命令的代码，以及任何其他必要的指令。 The code template to execute a command should include code to evaluate any subexpressions and code to execute any subcommands, together with any other necessary instructions.

## Fun->SVM对象码模板例子：code templates

## code templates vs action

![](/static/2021-03-21-13-27-37.png)

![](/static/2021-03-21-14-04-45.png)

---

![](/static/2021-03-21-14-08-47.png)

![](/static/2021-03-21-14-09-37.png)

code action - 更具体的行动，templates只是指定什么会被选中

# Handling jumps

![](/static/2021-03-21-14-16-11.png)

* The code generator emits instructions one by one. When an instruction is emitted, it is added to the end of the object code.
* At the destination of a jump instruction, the code generator must note the destination address and incorporate it into the jump instruction.

![](/static/2021-03-21-14-19-43.png)

* backward jump - 执行到jump指令时，目标地址已知（回跳）For a backward jump, the destination address is already known when the jump instruction is emitted
* **forward jump - 执行到jump指令时，目标地址未知（前跳**）
  * 发出一条不完整的跳转指令（其地址域为0），并记下其地址。 Emit an incomplete jump instruction (with 0 in its address field), and note its address
  * 当以后知道目标地址后，将该地址补入跳转指令。 When the destination address becomes known later, patch that address into the jump instruction.

## Fun->SVM code templates

if command
![](/static/2021-03-21-14-24-22.png)
![](/static/2021-03-21-15-53-26.png)

while command
![](/static/2021-03-21-15-55-02.png)
![](/static/2021-03-21-15-59-27.png)
![](/static/2021-03-21-16-02-08.png)

# Code generation with ANTLR

![](/static/2021-03-21-16-05-41.png)

* 初始化SVM实例，，代码生成器encodeVisitor遍历AST时，选中的代码会直接将应生成的对象码插入SVM实例的代码存储
  * `obj.emitXXX(...)`

生成Num & Id指令

![](/static/2021-03-21-16-12-07.png)

生成 expr 指令

![](/static/2021-03-21-16-20-34.png)

生成 assignment command 指令 （存储expr值进var地址）

![](/static/2021-03-21-16-26-15.png)

if structure， jump command处理 （前跳，目标地址先设为0，后进行patch改为正确地址）

![](/static/2021-03-21-16-46-50.png)

variable declaration (生成address table的新映射)

![](/static/2021-03-21-16-50-52.png)

# Storage organization

![](/static/2021-03-21-16-52-04.png)

静态语言，每个变量类型，所占大小编译时期已知

## Storage for global and local variables

![](/static/2021-03-21-16-54-32.png)

## stack frame

![](/static/2021-03-21-16-59-10.png)
![](/static/2021-03-21-16-59-45.png)

## SVM变量存储例子：Storage for global and local variables in SVM

![](/static/2021-03-21-17-11-13.png)
![](/static/2021-03-21-17-12-30.png)

# Fun 过程声明 代码生成

procedure declaration
![](/static/2021-03-21-17-14-40.png)

* 注意currentLocale控制scope

# Fun 参数 代码生成

formal

![](/static/2021-03-21-17-19-40.png)

# Fun program 代码生成

![](/static/2021-03-21-17-22-24.png)

# Fun Compiler3阶段

![](/static/2021-03-21-17-23-15.png)

* 最后生成SVM目标码

# 地址表示：Representing addresses

三种地址

![](/static/2021-03-21-17-23-51.png)
![](/static/2021-03-21-17-24-41.png)