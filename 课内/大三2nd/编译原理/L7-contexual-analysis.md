# Lecture 7 - Contexual analysis

![](/static/2021-03-20-14-18-55.png)

* 上下文分析 Aspects of contextual analysis
* 作用域检测 scope checking
* 类型检测 type checking
* case study: FUn contexual analysis
* 类型表示 representing types
* 作用域表示 representing scopes

* [Lecture 7 - Contexual analysis](#lecture-7---contexual-analysis)
* [Aspects of contextual analysis](#aspects-of-contextual-analysis)
* [例子：Fun 编译 - Fun Compilation](#例子fun-编译---fun-compilation)
* [作用域检测：Scope Checking](#作用域检测scope-checking)
  * [type table 如何生成 & 使用](#type-table-如何生成--使用)
  * [Fun作用域检测例子：Fun Scope Checking](#fun作用域检测例子fun-scope-checking)
* [类型检测: Type Checking](#类型检测-type-checking)
  * [类型检测具体实现](#类型检测具体实现)
  * [Fun类型检测例子](#fun类型检测例子)
* [ANTLR上下文分析：Contexual Analysis with ANTLR](#antlr上下文分析contexual-analysis-with-antlr)
* [语法&上下文分析 FunDriver](#语法上下文分析-fundriver)
* [类型表示：Representing Types](#类型表示representing-types)
  * [FUN类型表示：Fun types](#fun类型表示fun-types)
  * [FUN类型class定义](#fun类型class定义)
* [作用域表示：Representing Scopes](#作用域表示representing-scopes)
  * [FUN Scopes例子](#fun-scopes例子)
  * [Type table 类表示](#type-table-类表示)

# Aspects of contextual analysis

![](/static/2021-03-20-14-22-56.png)

> 上下文分析 - 检查源程序（AST表示）是否满足源语言的作用域&类型规则。Contextual analysis checks whether the source program (represented by an AST/syntax tree) satisfies the source language’s scope rules and type rules

上下文分析（语义分析）可分为两步： contexual analysis could be broken down into 2 steps

* scope checking 作用域检测
  * 确保每个标识符都提前声明了 ensuring every identifier used in the source program is previously declared
* type checking 类型检测
  * 确保每个操作的操作数都有预期类型 ensuring every operation has operands with the expected ytpes
* ![](/static/2021-10-09-22-43-08.png)

# 例子：Fun 编译 - Fun Compilation

![](/static/2021-03-20-14-28-29.png)
![](/static/2021-03-20-14-29-22.png)

生成作用域&类型检测 - 上下文分析过程中，遍历AST时，需要一个**类型表 type table**,

* <font color="deeppink">存储程序中所有变量及其类型</font> storing all the vairables in the program with its corresponding types

![](/static/2021-03-20-14-35-26.png)

# 作用域检测：Scope Checking

![](/static/2021-03-20-14-46-11.png)

> 作用域检测 - **收集&传播【已声明标识符的信息】** Scope checking is the collection and dissemination of information about declared identifiers.

上下文分析器包括type table（hash table, key-value, exchange the variable to its type）

* 包含每个已声明标识符的类型 contains the type of each declared identifier

---

## type table 如何生成 & 使用

![](/static/2021-03-20-15-09-04.png)

* 遍历AST时每声明一个标识符，将该标识符&类型放入类型表 Wherever an identifier is declared, put the identifier and its type into the type table.
  * 如果该标识符已经存在（相同作用域中），**报告作用域错误** if the identifier already exists in the type table(in the same scope), report a **scope error**
* 当标识符出现于命令或表达式中（被使用），检测该标识符是否存在于type table,检索其类型 Wherever an identifier is used (e.g., in a command or expression), check that it is in the type table, and retrieve its type
  * 如果标识符不存在，报告**作用域错误** If the identifier is not in the type table, report a scope error

## Fun作用域检测例子：Fun Scope Checking

![](/static/2021-03-20-15-15-11.png)

# 类型检测: Type Checking

![](/static/2021-03-20-15-15-42.png)

> 类型检查是检查**每条【命令和表达式是否具有良好的类型】**，即**没有类型错误**的过程。 Type checking is the process of checking that every command and expression is well-typed, i.e., free of type errors.

:orange: 只有静态源语言（编译时期类型已知），编译器编译时期才会执行类型检测 The compiler performs type checking only if the source language is statically-typed(type is known at compile time).

## 类型检测具体实现

![](/static/2021-03-20-15-18-34.png)

* 表示式，检测任何子**表达式**类型。并推断整个表达式的类型 At each expression, check the type of any subexpression. Infer the type of the expression as a whole
  * 如果子表达式出现非预期类型，**报告类型错误** If a sub-expression has unexpected type, report a type error
* 在每个**命令**中，检查任何组成表达式的类型。 At each command, check the type of any constituent expression.
  * 如果表达式有非预期类型，**报告类型错误** – If an expression has unexpected type, report a type error
  * command不用做类型推断

## Fun类型检测例子

![](/static/2021-03-20-15-37-25.png)

# ANTLR上下文分析：Contexual Analysis with ANTLR

AST生成之后，可以通过**遍历AST**来进行上下文分析 Contextual analysis is done by walking over the syntax tree

![](/static/2021-03-20-15-43-41.png)

* 访问表达式，返回表达式类型 Visiting an expression returns the type of the expression.

---

需要每个节点（表达式，命令，其他终结符）的visit方法

:orange: Type checking(command，expression是否具有良好类型)

![](/static/2021-03-20-15-49-02.png)

* NUM
* ID

![](/static/2021-03-20-15-57-29.png)

* expression

![](/static/2021-03-20-16-00-40.png)

* assignment command

![](/static/2021-03-20-16-06-26.png)

* if command

![](/static/2021-03-20-16-08-34.png)

* sequential command

:orange: scope checking(收集已定义标识符类型信息，生成type table)

![](/static/2021-03-20-16-14-23.png)

* varaible declaration 变量声明过程

---

![](/static/2021-03-20-16-17-09.png)

# 语法&上下文分析 FunDriver

![](/static/2021-03-20-16-18-31.png)

# 类型表示：Representing Types

![](/static/2021-03-20-16-18-53.png)

* 需要用**源语言的类型**来实现类型检测
  * primitive types - BOOL & INT
  * cartesian product types - （takes 2 operands）
  * disjoint union types - N/A
  * mapping types - function/procedure or **other operators**

## FUN类型表示：Fun types

![](/static/2021-03-20-16-26-20.png)
![](/static/2021-03-20-16-29-16.png)

## FUN类型class定义

![](/static/2021-03-20-16-30-23.png)

---

:orange: Primitive class

![](/static/2021-03-20-16-31-17.png)

:orange: Pair(Cartersian product)

![](/static/2021-03-20-17-27-52.png)

:orange: Mapping Type

![](/static/2021-03-20-17-28-33.png)

# 作用域表示：Representing Scopes

![](/static/2021-03-20-17-34-26.png)

考虑一个PL，其中**所有的声明**为**全局or局部**。这样的PL被称为具有平**块结构** Consider a PL in which all declarations are either global or local. Such a PL is said to have flat block structure

---

![](/static/2021-03-20-17-37-06.png)

* Type Table 必须区分全局&局部变量 type table must distinguish between global and local entries
* Global entries are always present.
* Local entries are present only when analysing an inner scope
  * disappear if leave the scope
* 源码上下文分析过程中，遇到相同标识符，对应
  * 最多1个全局变量
  * 最多1个局部变量

:candy: 大多PL嵌套作用域，而不存在平块结构(作用域) Most PLs have nested scopes, not flat global/local scopes

## FUN Scopes例子

![](/static/2021-03-20-17-42-21.png)

---

## Type table 类表示

simply represent the type table using hash table

* 1 for globals
* 1 for locals

![](/static/2021-03-20-17-43-52.png)

---

enter & exit local scope

![](/static/2021-03-20-17-45-08.png)

* enter local scope -> initialize the locals

put & get entry to/from the table

![](/static/2021-03-20-17-52-48.png)

完整procedure declaration proc_decl 上下文分析，作用域检测

![](/static/2021-03-20-18-02-16.png)