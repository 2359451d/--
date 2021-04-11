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

显式销毁指定堆变量的操作 A **deallocator** is an operation that explicitly destroys a designated heap variable