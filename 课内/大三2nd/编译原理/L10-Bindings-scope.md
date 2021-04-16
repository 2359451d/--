# Content

机翻

* bindings and environments
* scope and block structure
* declarations

---

* [Content](#content)
* [绑定&命名空间（环境）：Bindings and environments](#绑定命名空间环境bindings-and-environments)
  * [Example: environments in a C program](#example-environments-in-a-c-program)
* [作用域：Scope](#作用域scope)
* [块结构：Blocks](#块结构blocks)
* [单块结构：Monolithic block structure](#单块结构monolithic-block-structure)
* [Flat Block structure](#flat-block-structure)
* [Nested block structure](#nested-block-structure)
  * [Example: C block structure](#example-c-block-structure)
* [联编出现&应用性出现:Binding and applied occurrences](#联编出现应用性出现binding-and-applied-occurrences)
  * [Example: binding and applied occurrences](#example-binding-and-applied-occurrences)
* [静态/动态作用域：Static vs dynamic scoping](#静态动态作用域static-vs-dynamic-scoping)
  * [Example: static scoping & dynamic scoping](#example-static-scoping--dynamic-scoping)
* [Declarations](#declarations)
  * [Recursive Declarations](#recursive-declarations)
  * [Example: Java recursive declarations](#example-java-recursive-declarations)
  * [Example: C recursive declarations](#example-c-recursive-declarations)

# 绑定&命名空间（环境）：Bindings and environments

表达式/命令的含义取决于表达式/命令使用的任何标识符的声明。 The meaning of an expression/command depends on the declarations of any identifiers used by the expression/command

为了使表达式的计算 & 命令执行更正式，需要引入以下两个概念

:orange: **绑定是一个标识符和一个实体（如一个值、变量或过程）之间的固定关联**。 A binding is a fixed association between an identifier and an entity (such as a value, variable, or procedure).

:orange: **环境(或名称空间)是一组绑定**。 An environment (or name-space) is a set of bindings

---

**每个声明都会产生一些绑定(标识符-实体)**，这些绑定被添加到环境中。 Each declaration produces some bindings, which are added to the surrounding environment.

每个表达式/命令都是在特定的环境中解释的。<font color="deeppink">表达式/命令中使用的每一个标识符都必须在该环境下有一个绑定</font>。Each expression/command is interpreted in a particular environment. Every identifier used in the expression/command must have a binding in that environment

## Example: environments in a C program

![](/static/2021-04-12-13-20-26.png)

* 表示其environment例子 （这里关注的是program全局的环境，不是特定点，所以编译时期所有声明应可见）

# 作用域：Scope

声明（或绑定）的作用域是指程序中它具有影响（可以产生影响）的部分 The scope of a declaration (or of a binding) is the portion of the program text over which it has effect.

在一些早期的PL（如Cobol）中，每个声明的作用域是整个程序 In some early PLs (such as Cobol), the scope of every declaration was the whole program.

* 因此早期PL，，如果有声明那么该声明作用于整个程序

在现代PL中，每个声明的**作用域**由程序的**块结构控制**。 In modern PLs, the scope of each declaration is controlled by the program’s block structure.

# 块结构：Blocks

块是一个程序结构，<font color="red">它限定了其中任何声明(绑定)的范围</font>。 A block is a program construct that delimits the scope of any declarations within it

每个PL块结构可能不同 Each PL has its own forms of blocks:

* c - block commands `{...}`
  * function bodies
  * compilation-units
* java - block commands `{...}`
  * method bodies
  * class declarations
* haskell - block expression
  * `let...in...`
  * function bodies
  * modules

:orange: PL的块结构是程序文本中块的排列的一种方式。 A PL’s block structure is the way in which blocks are arranged in the program text.

* **块结构用于规范，安排，限定声明（绑定）的作用域** a way to arrange the scope of declarations

# 单块结构：Monolithic block structure 

![](/static/2021-04-12-13-36-35.png)

有些PL（如Cobol）具有单块结构：**整个程序是一个块结构**。<font color="red">每一个声明的作用域就是整个程序</font> Some PLs (such as Cobol) have monolithic block structure: the whole program is a single block. The scope of every declaration is the whole program.

* every declaration as global scope (每个声明即可以应用全局，，作用域为全局)

# Flat Block structure

![](/static/2021-04-12-13-45-14.png)

Some PLs (such as Fortran) have flat block structure: the program is partitioned into blocks, **but these blocks may not contain inner blocks**. (cannot have)

# Nested block structure

![](/static/2021-04-12-13-47-00.png)

Modern PLs have nested block structure: blocks may be nested freely within other blocks

![](/static/2021-04-12-13-50-00.png)

## Example: C block structure

C has flat block structure for functions, but nested block structure for variables:

![](/static/2021-04-12-13-57-33.png)

* flat block strcture - func
  * cannot declar functions inside other functions
  * but can access each func during the entire program

# 联编出现&应用性出现:Binding and applied occurrences

<font color="red">标识符`I`联编出现 - 标识符`I`绑定至实体`e`</font> A **binding occurrence** of identifier I is an occurrence of I where I is bound to some entity e.

* 如声明中，某标识符绑定了值

<font color="red">标识符`I`应用出现 - 标识符`I`出现，使用了其绑定的实体`e`</font>  An **applied occurrence** of identifier I is an occurrence of I where use is made of the entity e to which I is bound.

* 即，，使用某标识符(绑定到了实体），静态语言

:orange: <font color="red">如果是静态语言那么每一个应用性出现对应一个联编出现</font>. If the PL is statically scoped (see later), every applied occurrence of I should correspond to exactly one binding occurrence of I.

## Example: binding and applied occurrences

![](/static/2021-04-12-15-22-44.png)

* 静态作用域

# 静态/动态作用域：Static vs dynamic scoping

:orange: **如果一个过程的主体是在过程定义的环境中执行的，那么PL就是静态作用域(或，词法作用域)的**。 A PL is statically scoped if the body of a procedure is executed in the environment of the procedure definition.

* 然后我们可以在编译时决定一个标识符的联编出现对应于一个给定的应用性出现 Then we can decide at compile-time which binding occurrence of an identifier corresponds to a given applied occurrence.
  * 即，编译时期能确定所有联编->应用出现的关联
* 编译时期，静态确定的作用域，，变量在作用域内可访问

:orange: **如果存储过程的主体在存储过程调用栈的环境中执行，则PL是动态作用域的**。 A PL is dynamically scoped if the body of a procedure is executed in the environment of the procedure call site

* 那么我们在**运行时才能决定**一个标识符的哪个绑定发生对应于一个给定的应用发生，因为环境可能在不同的调用栈之间变化。 Then we cannot decide until run-time which binding occurrence of an identifier corresponds to a given applied occurrence, since the environment may vary from one call site to another.
* 如，如果有个函数f，里面调用了函数g，那么在执行g的时候，f里的所有局部变量都会被g访问到。在静态作用域的情况下，g不能访问f的变量
  * 动态作用域里，取变量的值时，会由内向外逐层检查函数的调用链，并打印第一次遇到的那个绑定的值

---

Dynamic scoping fits badly with static typing

* static language easier for static typing

Nearly all PLs (including Pascal, Ada, C, Java, Haskell) are statically scoped

Only a few PLs (such as Smalltalk and Lisp) are dynamically scoped.

## Example: static scoping & dynamic scoping

![](/static/2021-04-12-15-41-12.png)

![](/static/2021-04-12-15-44-03.png)

# Declarations

**声明**是一个程序结构，它将被计算以**产生绑定** A declaration is a program construct that will be elaborated to produce binding(s).

* A declaration may also have **side effects** (such as creating a variable）

<font color="red">定义是一个声明，其唯一的作用是【产生绑定】</font> A definition is a declaration whose only effect is to produce binding(s).

* A definition has **no side effects**

:orange: simple declarations

* **type declaration**
  * 标识符绑定至存在的类型或新类型 A type declaration binds an identifier to an existing or new type
* **constant definition**
  * 标识符绑定至值（计算后）binds an identifier to a value （possibly computed）
* **variable declaration**
  * 标识符绑定至新创建的变量 binds an identifier to a newly created variable
* **procedure definition**
  * 标识符绑定至过程 binds an identifier to a procedure
* 根据PL不同，适用于其他实体 similarly for other entities (depending on the PL)

:orange: compound declarations

* **sequential declaration**
  * 结合多个子声明，满足，后续子声明可以使用前子声明产生的绑定 A sequential declaration combines several subdeclarations, such that the later sub-declarations can use bindings produced by the earlier sub-declarations. 
* **recursive declaration**
  * 使用自身产生的绑定 A recursive declaration is one that uses the bindings it produces itself

## Recursive Declarations

A recursive declaration is one that uses the bindings it produces itself.

在几乎所有的PL中，递归仅限于 In almost all PLs, recursion is restricted to

* type or class declarations
* procedure or method definitions

## Example: Java recursive declarations

![](/static/2021-04-12-20-01-54.png)

## Example: C recursive declarations

![](/static/2021-04-12-20-03-48.png)