# Content

![](/static/2021-04-11-11-09-01.png)

* vairables & how they modelled in storage
* simple, composite vairbales
* Pointers
* Commands
* Expressions with side effects

---

* [Content](#content)
* [变量定义：What are variables?](#变量定义what-are-variables)
* [存储抽象模型：Abstract Storage Model](#存储抽象模型abstract-storage-model)
* [Simple vs composite variables](#simple-vs-composite-variables)
  * [复合变量：Composite variables](#复合变量composite-variables)
    * [例子](#例子)
* [变量生命周期：Lifetimes](#变量生命周期lifetimes)
* [全局/局部/堆变量：Global vs local vs heap variables](#全局局部堆变量global-vs-local-vs-heap-variables)
  * [例子：C global and local variables](#例子c-global-and-local-variables)
  * [例子： C heap variables](#例子-c-heap-variables)
* [Allocators and deallocators](#allocators-and-deallocators)
* [Reachability](#reachability)
* [Pointers](#pointers)
* [野指针（悬挂指针）：Dangling Pointers](#野指针悬挂指针dangling-pointers)
  * [Example: C dangling pointers](#example-c-dangling-pointers)
* [Commands](#commands)
  * [Example: Java commands](#example-java-commands)
* [Expressions with side effects](#expressions-with-side-effects)
  * [Example: side effects](#example-side-effects)

# 变量定义：What are variables?

![](/static/2021-04-11-11-12-48.png)

> 在函数式PL（和数学）中，变量代表一个固定（但可能是未知）的值。In functional PLs (and in mathematics), a variable stands for a fixed (but possibly unknown) value.
> 在命令式和面向对象 PL中，**一个变量包含一个值。变量可以根据需要随时检查和更新** In imperative and OO PLs, a variable contains a value. The variable may be inspected and updated as often as desired
>
> * 这样的变量可以用来模拟一个真实世界的对象，其状态会随着时间的变化而变化 Such a variable can be used to model a real-world object whose state changes over time
> every time the state changes we update the var

# 存储抽象模型：Abstract Storage Model

![](/static/2021-04-11-11-26-56.png)

为了理解变量，设想有一个抽象的存储模型(代码生成阶段的内存抽象) To understand such variables, assume an abstract storage model:

* 存储是存储单元的集合，每个单元都有一个**唯一的地址** A store is a collection of cells, each of which has a unique address
* 每个存储单元要么是**分配的**要么**未分配**的。 Each cell is either allocated or unallocated.
  * 每个分配的存储单元包含 **简单值** 或 **未定义** each allocated cell contains either a simple value or undefined
  * <font color="red">已分配的单元可以被检查，除非包含 `undefined`</font> – An allocated cell can be inspected, unless it contains undefined
  * 已分配的存储单元随时可以更新 an allocated cell can be updated at any time

# Simple vs composite variables

![](/static/2021-04-11-11-44-31.png)

:orange: 一个简单的变量是包含一个基本值或指针的变量。 A simple variable is one that contains a primitive value or pointer

* **一个简单的变量占用一个分配的存储单元格**。 A simple variable occupies a single allocated cell.

:orange: 复合变量是指包含一个复合值的变量。 A composite variable is one that contains a composite value

* **复合变量占用一组相邻的已分配存储单元** A composite variable occupies a group of adjacent allocated cells.

## 复合变量：Composite variables

![](/static/2021-04-11-11-45-34.png)

> 复合类型的变量与其同类型的值具有相同的结构。 A variable of a composite type has the same structure as a value of the same type.

* A tuple variable is a tuple of component variables.
* index到组成变量的映射 An array variable is a mapping from an index range to a group of component variables.

:orange: 基于PL，符合变量的更新可分为 depending on the PL, a composite var can be:

* **totally updated**
  * 一次性更新 update the composite variable at once
* **selectively updated**
  * 每次更新某元素 update one component at a time

### 例子

![](/static/2021-04-11-11-54-35.png)

# 变量生命周期：Lifetimes

![](/static/2021-04-11-11-55-03.png)

:orange: 每一个变量都是在某个特定的时间被创建，并在以后不再需要它的时候被销毁。 Every variable is created at some definite time, and destroyed at some later time when it is no longer needed

:orange: 变量的生命周期是指其创建和销毁的时间间隔。A variable’s lifetime is the interval between its creation and destruction.

**变量只在其生命周期内占用存储单元，当变量被销毁时，这些单元可以被重新分配**。 A variable occupies cells only during its lifetime. When the variable is destroyed, these cells may be deallocated

* 而这些单元格（被释放的）随后可能会被重新分配给其他变量。 And these cells may subsequently be re-allocated to other variable(s).

# 全局/局部/堆变量：Global vs local vs heap variables

![](/static/2021-04-11-13-37-12.png)

:orange: **一个全局变量的寿命就是程序的整个运行时间。它是由全局声明创建的** A global variable’s lifetime is the program’s entire run-time. It is created by a global declaration.

* 程序结束，全局变量销毁

:orange: **一个局部变量的生命周期由一个块激活。它由该块中的声明创建，并在退出该块时销毁**。 A local variable’s lifetime is an activation of a block. It is created by a declaration within that block, and destroyed on exit from that block.

:orange: <font color="red">一个堆变量的生命周期是任意的，但受程序运行时间的限制。它可以在任何时候被创建（由**allocator**），也可以在任何时候被销毁。它是通过一个指针来访问的</font>。 A heap variable’s lifetime is arbitrary, but bounded by the program’s run-time. It can be created at any time (by an allocator), and may be destroyed at any later time. It is accessed through a pointer.

## 例子：C global and local variables

![](/static/2021-04-11-13-45-15.png)
![](/static/2021-04-11-13-51-30.png)

* Global and local variables’ lifetimes are nested. They can never be overlapped.

---

special case: recursion

![](/static/2021-04-11-13-53-32.png)
![](/static/2021-04-11-13-53-39.png)

* Note: A local variable of a recursive procedure/ function has several nested lifetimes.

## 例子： C heap variables

![](/static/2021-04-11-14-14-26.png)
![](/static/2021-04-11-14-16-49.png)
![](/static/2021-04-11-14-20-06.png)

* c allocator - `malloc`, delcared the heap var
* Heap variables’ lifetimes can overlap one another and local/global variables’ lifetimes.
  * heap var 's lifetime is arbitary (ends or starts at some conditions)

# Allocators and deallocators

![](/static/2021-04-11-14-21-56.png)

分配器是一个**创建堆变量的操作**，产生一个指向该堆变量的**指针**。 An allocator is an operation that creates a heap variable, yielding a pointer to that heap variable

* c allocator - library function `malloc()`
* java allocator expression - `new`

显式**销毁指定堆变量**的操作 A **deallocator** is an operation that explicitly destroys a designated heap variable

* c deallocator - library function `free()`
* java - 有gcc回收机制

# Reachability

![](/static/2021-04-12-10-15-12.png)

堆变量可达: 只要可以通过指针访问到（全局/局部变量）A heap variable remains reachable as long as it can be accessed by following pointers from a global or local variable

一个**堆变量**的寿命从它被创建开始一直到 A heap variable’s lifetime extends from its creation until:

* it is destroyed by a deallocator,or
* <font color="deeppink">it becomes unreachable, or</font>
* the program terminates

# Pointers

![](/static/2021-04-12-10-17-51.png)

指针是对一个特定变量的引用。事实上，指针有时也被称为引用）。 A pointer is a reference to a particular variable. (In fact, pointers are sometimes called references.)

指针的引用是指它所指向的变量 A pointer’s referent is the variable to which it refers.

* referent is something which refer with the pointer

空指针是一个特殊的指针值，它没有引用。A null pointer is a special pointer value that has no referent

指针本质上是它在存储中的引用地址 A pointer is essentially the address of its referent in the store

* 然而，每个指针也有一个类型，一个指针的类型允许我们推断出它的引用的类型。 However, each pointer also has a type, and the type of a pointer allows us to infer the type of its referent

---

![](/static/2021-04-12-10-24-13.png)

指针和堆变量可以用来表示**递归值**，如列表和树 Pointers and heap variables can be used to represent **recursive values** such as lists and trees.

但指针本身是一个低级的概念。对指针的操作是出了名的容易出错和难以理解。 But the pointer itself is a low-level concept. Manipulation of pointers is notoriously error-prone and hard to understand.

* eg. `p->succ=q;` to manipulate a list but which list
  * does it delete nodes from the list
    * 前例 `node 5` 是 创建了unreachable var
  * does it stitch together parts of 2 different lists?
  * does it introduce a cycle?

# 野指针（悬挂指针）：Dangling Pointers

![](/static/2021-04-12-10-29-20.png)

指向非法内存地址的指针（指向已摧毁变量的指针）A dangling pointer is a pointer to a variable that has been destroyed

何时出现？

* 堆变量摧毁后仍指向改变量的指针 a pointer to a heap variable still exists after the heap variable is destroyed by a deallocator
* 在退出声明局部变量的代码块时，指向局部变量的指针仍然存在。 a pointer to a local variable still exists at exit from the block in which the local variable was declared

deallocator会立即销毁一个堆变量；所有指向该堆变量的现有指针都会变成野指针。 A deallocator immediately destroys a heap variable; all existing pointers to that heap variable then become dangling pointers

* 因此deallocators本质上是不安全的。 Thus deallocators are inherently unsafe
  * 如，需要用户自己检查现有的变量是否被正确操作

---

![](/static/2021-04-12-10-36-40.png)

* c
  * 在一个堆变量被销毁后，指向它的指针可能仍然存在。 After a heap variable is destroyed, pointers to it might still exist.
  * 在退出块时，指向局部变量的指针可能仍然存在（例如，如果存储在全局变量中，那么退出时这个全局变量的值可能还未改变） At exit from a block, pointers to its local variables might still exist (e.g., if stored in global variables)
* java
  * It has no deallocator.
  * Pointers to local variables cannot be obtained

## Example: C dangling pointers

![](/static/2021-04-12-10-42-39.png)

# Commands

![](/static/2021-04-12-10-42-52.png)

**命令**（~~通常称为语句~~,,不要混淆，，，statement有的地方指->expression一般可以有计算值的，，command没有，，）是一个程序结构，它将被**执行以更新变量** A command (often called a statement) is a program construct that will be executed to update variables

命令是命令式和OO式PL的特征（但不是函数式PL）。 Commands are characteristic of imperative and OO PLs (but not functional PLs).

:orange: simple commands

* **skip command**
  * does nothing
* **assignment command**
  * uses a value to update a var
* **procedure call**
  * calls a proper procedure with arguments. Its net effect is to update some var

:orange: compound commands

![](/static/2021-04-12-10-52-22.png)

* **sequential command**
  * executes its sub-commands in sequence
* **conditional command** (if)
  * chooses one of its sub-commands to execute
* **iterative command** (while, for)
  * executes its sub-command repeatedly
  * definite iteration (where the number of repetitions is known in advance)
  * indefinite iteration (where the number of repetitions is not known in advance).
* **block command**
  * contains declarations of local variables, etc

## Example: Java commands

assignment commands

![](/static/2021-04-12-11-59-48.png)

conditional commands

![](/static/2021-04-12-12-00-19.png)

iterative commands

![](/static/2021-04-12-12-00-35.png)
![](/static/2021-04-12-12-00-43.png)

block command

![](/static/2021-04-12-12-01-07.png)

# Expressions with side effects

计算表达式得到输出结果值 The primary purpose of evaluating an expression is to yield a value.

大多命令式 & OO PL中计算表达式可以更新变量 --- 【side effects】In most imperative and OO PLs, evaluating an expression can also update variables – these are side effects

在C和Java中，函数的主体是一条命令。如果该命令更新了全局变量或堆变量，调用该函数就会产生影响（更新变量）。 In C and Java, the body of a function is a command. If that command updates global or heap variables, calling the function has side effects.

* In C and Java, assignments （commands）are in fact expressions with side effects: “V = E” stores the value of E in V as well as yielding that value.

## Example: side effects

![](/static/2021-04-12-12-13-00.png)