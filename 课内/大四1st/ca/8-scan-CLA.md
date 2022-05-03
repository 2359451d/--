# Content

Carry Look-ahead Adder 超前进位加法器 (CLA

* [Content](#content)
* [map,fold,scan](#mapfoldscan)
* [线性fold-O(n): sequential fold requires time O(n)](#线性fold-on-sequential-fold-requires-time-on)
* [加法重排序: but we can rearrange the order of additions](#加法重排序-but-we-can-rearrange-the-order-of-additions)
* [结合性：Associativity](#结合性associativity)
* [Scan](#scan)
* [scan with associative operator](#scan-with-associative-operator)
* [Logarithmic scan is harder](#logarithmic-scan-is-harder)
* [二进制加法：Binary Addition](#二进制加法binary-addition)
* [能使用并行scan来加速加法吗：Can we just use parallel scan for addition](#能使用并行scan来加速加法吗can-we-just-use-parallel-scan-for-addition)
* [局部应用：Partial application](#局部应用partial-application)
* [转换1-局部应用：Transformation 1 - use partial application](#转换1-局部应用transformation-1---use-partial-application)
* [进位传播：Carry propagation across a sequence of bits](#进位传播carry-propagation-across-a-sequence-of-bits)
* [函数组合：Function composition is associative](#函数组合function-composition-is-associative)
* [并行scan计算传播信号：use parallel scan to compute propagations](#并行scan计算传播信号use-parallel-scan-to-compute-propagations)
* [局限性：Problem](#局限性problem)
* [转换2-传播函数：Propagation functions](#转换2-传播函数propagation-functions)
* [意义](#意义)
* [================](#)
* [全加器carry传播](#全加器carry传播)
* [超前进位加法器](#超前进位加法器)

# map,fold,scan

是对电路设计有用的设计模式 •	Map, fold and scan are design patterns useful for circuit design

- 对聚合数据结构（字、位片格式等）进行操作–	Operate on aggregate data structures (words, bitslice format, etc)
- 它们是具有不同变体（左、右等）的函数家族–	They are a family of functions with different variants (left, right, etc)

- **Map：取一个字的信号，用一个电路f来处理每一个信号（并行**）。Map: takes a word of signals and process each one with a circuit f (in parallel)
  - 对于一个有n个元素的字，map的门延迟为O(1)–	For a word of n elements, map has a gate delay of O(1)

- **Fold：从一个初始值 "a "开始，通过一个字进行线性计算** Fold: linear computation through a word starting with an initial value “a”
  - foldr
    - ![](/static/2022-04-29-20-32-18.png)

- **Scan：比折叠更普遍，因为输出的是中间值** Scan: more general than fold since outputs the intermediate values

- **对于一个有n个元素的词，折叠和扫描的门延迟为O(n**) For a word of n elements, fold and scan have a gate delay of O(n)

# 线性fold-O(n): sequential fold requires time O(n)

- 假设我们有 "n "个值需要加起来•	Suppose we have “n” values to add up
  - 例如：X0, X1, X2, X3–	Example:	x0, x1, x2, x3

- 我们需要进行 "n "次加法•	We need to perform “n” additions
  - foldr
  - 例如：x0 + (x1 + (x2 + (x3 + 0)) , 其中 "0 "是初始 "a"–	Example:	x0 + (x1 + (x2 + (x3 + 0))	, where “0” is the initial “a”

- **我们不能在同一时间开始所有的加法**!•	We cannot start all the additions at the same time!

- **如果我们把数据量增加一倍，就会把得到完整的和的时间增加一倍**•	If we double the amount of data, we double the time to get the complete sum
  - 例如：16位，32位，等等–	Example: 16 bits, 32 bits, etc

# 加法重排序: but we can rearrange the order of additions

![](/static/2022-04-30-15-41-31.png)

# 结合性：Associativity

![](/static/2022-04-30-15-42-39.png)

# Scan

![](/static/2022-04-30-15-44-45.png)

- 当你用像(+)这样的运算符折叠一组数据时，你会经历一连串的中间值，为了计算x0 + x1 + x2 + x3，我们计算了
  - temp1 = x2 + x3
  - temp2 = x1 + temp1
  - result = x0 + temp2

- 有时你并不关心中间结果temp1, temp2, ...。
  - **使用折叠：它抛弃了中间结果**–	Use fold: it discards the intermediate results

- 有时你想要所有的中间结果
  - **使用扫描：它产生所有的中间结果和最终结果**–	Use scan: it produces all the intermediate results and the final result

# scan with associative operator

- 为了计算一个scan，我们必须计算大量的fold•	To compute a scan we must compute a lot of folds

- 我们可以**独立地**计算这些fold 
  - temp1 = (x2 + x3)
  - temp2 = (x1 + (x2 + x3))
  - result = (x0 + (x1 + (x2 + x3))

- 如果运算符是关联的（例如+），我们可以使用树状结构，**以对数时间并行计算所有的fold**•	If the operator is associative (e.g. +) we can use a tree structure to compute all the folds in parallel in logarithmic time
  - 每个fold都由一个树状电路计算，所以会有n个树状电路–	Each fold is computed by a tree circuit, so there would be n trees

# Logarithmic scan is harder

- 问题：**独立完成所有的fold需要大量的（+）电路**，因为我们没有重复使用中间结果 •	Problem: doing all the folds independently takes a lot of (+) circuits because we are not reusing the intermediate results

- 想法：尽可能共享中间结果，但这需要...•	Idea: share the intermediate results where possible, but this requires…
  - 重新安排一些中间计算方法 –	Rearranging some of the intermediate calculations
  - 只用一棵树，而不是为每个中间结果单独建树–	Using just one tree, not a separate tree for each intermediate result
  - 在树上包括一些 "向下 "和 "向上 "的连接–	Including some “downward” and “upward” connections in the tree

- 其结果是并行扫描•	The result is Parallel Scan
  - 如果f是关联的，`scanr f`可以在O(log n)中使用树状电路完成–	If f is associative, scanr f can be done in O(log n) using a tree circuit

# 二进制加法：Binary Addition

RCA

> 但是这样的结构有什么缺点？最直接的就是第4号全加器要输出计算结果至少要等到第3号全加器把进位信息传过来。那如果级数很高会出现组合逻辑延时过长的情况。
> 所以关键问题就是如何避免等待上一级进位信号，不如提前把进位信号分配给每一位直接运算？
> The	most	significant	outputs	of	the	adder depends	on	the	least	significant	inputs	

- 行波进位加法器;是一个扫描电路 •	The ripple carry adder is a scan circuit
  - 它使用一个全加器将来自当前位(xi,yi)的位与来自右边的进位输入相结合，并产生一个和位si和一个进位输出发送到左边。 –	It uses a full adder to combine bits from the current bit position (xi,yi) with a carry input coming from the right, and to produce a sum bit si and a carry output to send to the left
  - 需要O(n)时间来添加n位数 •	The ripple carry adder takes O(n) time to add n-bit numbers
    - 这是很糟糕的!•	This is very bad!
- 目前的计算机至少有64位的加法器–	Current computers have at least 64-bit adders
  - 这就导致了一个大的路径深度 •	That results in a large path depth
- 加法器几乎总是在关键路径上，所以它产生了一个缓慢的时钟速度–	The adder is almost always on the critical path, so it produces a slow clock speed

---

rippleAdd4

![](/static/2022-04-30-15-55-02.png)

# 能使用并行scan来加速加法吗：Can we just use parallel scan for addition

- 加法使用mscanr•	Addition uses mscanr
- 并行扫描需要O(log n)时间•	Parallel scan takes O(log n) time
- 我们可以直接使用并行扫描来获得快速加法器吗？•	Can we can just use parallel scan to get a fast adder?
  - 不可以! **并行扫描要求被扫描的函数是关联的** •	No! Parallel scan requires the function being scanned to be associative
- 问题：bcarry电路不是关联的•	Problem: the bcarry circuit is not associative
  - 如果我们用平行扫描来计算bcarry，加法器将是错误的–	If we use parallel scan to compute bcarry, the adder will be wrong
  - bsum可以

---

解决方案：我们可以通过**一连串的转换**来进行 •	Solution: we can go through a sequence of transformations

- 从用**顺序扫描**（不是并行）定义的行波进位加法器开始–	Start with the ripple carry adder defined with sequential scan (not parallel)
- 将其转换为产生相同结果的不同电路–	Transform it into a different circuit that produces the same result
- 需要进行几次转换（在数学上验证）。–	Several transformations are needed (validated mathematically)

# 局部应用：Partial application

![](/static/2022-04-30-16-41-13.png)

# 转换1-局部应用：Transformation 1 - use partial application

![](/static/2022-04-30-16-47-50.png)

- 进位传播函数bcarry需要被应用于•	The carry propagation function bcarry needs to be applied to
  - 操作数的第i位（xi,yi）。–	The i’th bits of the operands (xi,yi)
  - 从右边的位置开始的进位输入–	The carry input from the position to the right

- 在开始时，我们有xi和yi的值，但没有进位输入。•	At the beginning we have the values of xi and yi but not the carry input

- **不要等进位输入来做完整应用，要做部分应用**•	Don’t wait for the carry input to do full applications, do partial applications
  - **每个部分应用都给出了另一个carry传播的函数pi** –	Each partial application gives another carry propagation function pi
  - 这就给出了一个**pi函数的列表**，我们称之为**ps**（p的复数）。–	This gives a list of pi functions that we call it ps (plural of p)

- 我们定义**`ps=map bcarry zs`**，其中zs是(xi,yi)对的字。•	We define ps = map bcarry zs where zs is the word of (xi,yi) pairs

- 该**map可以在O(1)时间内计算**出来，因为我们**不需要等待carry输入变得可用**。•	The map can be calculated in O(1) time because we don’t wait for the carry inputs to become available

# 进位传播：Carry propagation across a sequence of bits

- 一个**单独的传播函数pi只是说一个比特位置的输出**是什么（给定其进位输入）。•	An individual propagation function pi just says what the output from one bit position is (given its carry input)

  - 但我们想计算整个序列中的carry的传播情况•	But we want to calculate the propagation of carry across the entire sequence

- 我们使用一个新的函数•	We use a new function that
  - 是**单个pi函数的组合**–	Is the composition of the individual pi functions
  - 当**应用于carry输入时，返回序列的carry输出**–	Returns the carry output of the sequence when applied to the carry input

- 注意，我们可以**考虑从最右边的位开始到第i位的每一个序列的位置，对于每一个i** •	Note that we can consider every sequence of positions starting from the rightmost bit up to the i’th bit for every i

# 函数组合：Function composition is associative

- 对于任何函数f、g、h
  - （f ∘ g）∘ h = f ∘ （g ∘ h）。 
  - 所以我们可以去掉括号，只写成f ∘ g ∘ h .So we can drop the parentheses and write just	f ∘ g ∘ h

- 要证明这一点，只需应用于具体的数值，例如：x
  - ((f ∘ g) ∘ h) x = (f ∘ g) (h x) = f (g (h x)
  * (f ∘ (g ∘ h)) x = f ((g ∘ h) x) = f (g (h x))

# 并行scan计算传播信号：use parallel scan to compute propagations

![](/static/2022-04-30-19-42-54.png)

- 传播函数ps的列表是由一个map计算出来的•	The list of propagation functions ps is calculated by a map

- 我们可以用具有关联性的组合（∘）来组合一连串的pi。•	We can combine a sequence of pi using composition (∘) which is associative

- 我们希望传播函数从最右边的位到每个单独的位置•	We want propagation functions from rightmost bit to each individual position
  - 这些都是用scan来定义的 –	These are defined using a scan

- 所以，现在我们可以使用并行扫描了•	So, now we can use parallel scan!

- 用map将这些函数平行地应用于进位的输入。•	Apply the resulting functions to the carry input in parallel using map
  - 这就给出了每个比特位置的进位输出–	This gives the carry output for each bit position

- 整个过程需要对数时间O(log n)而不是线性O(n)•	The whole process takes logarithmic time O(log n) not linear O(n)

# 局限性：Problem

- 问题：部分应用给出的是函数而不是比特•	Problem: the partial applications give functions not bits

- 电路包含导线，导线携带比特，而不是函数•	Circuits contain wires and wires carry bits, not functions

- 然而，一个函数可以用比特表示......有多少比特？•	However, a function can be represented as bits… how many bits?
  - 平方根函数需要无限多的比特。{(0,0), (1,1), (2,4), (3,9), (4,16), ...} –	The square root function needs an infinite number of bits: {(0,0), (1,1), (2,4), (3,9), (4,16), …}
  - 倒数函数需要一个有限的位数：{(0,1), (1,0)}。–	The inverter function takes a finite number: {(0,1), (1,0)}

# 转换2-传播函数：Propagation functions

![](/static/2022-04-30-19-48-34.png)
![](/static/2022-04-30-19-48-53.png)

- 下一步是更仔细地研究传播函数•	The next step is to look more closely at the propagation functions

- 只有3种可能的传播函数•	There are only 3 possible propagation functions
  - ![](/static/2022-04-30-20-35-15.png)
  - ![](/static/2022-04-30-20-36-44.png)
  - ![](/static/2022-04-30-20-37-33.png)
  - K = bcarry (0,0) "取消 "进位输入–	K = bcarry (0,0)	“kills” the carry input
  - P = bcarry (0,1) "传播 "进位输入–	P = bcarry (0,1)	“propagates” the carry input
  - G = bcarry (1,0) "产生 "一个进位–	G = bcarry (1,0)	“generates” a carry

- 我们可以用2位来表示这3个传播函数•	We can represent the 3 propagation functions with 2 bits

- 所以我们可以设计电路，对传播函数的2位表示进行应用和组合•	So we can design circuits that perform application and composition of 2-bit representations of propagation functions

- 这样我们就有了超前进位加法器!•	And this gives us the fast adder!

# 意义

CLA，电路复杂换时间

- 快速加法器很重要，通常位于关键路径上•	The fast adder is important, typically is on the critical path
  - 它比RCA有一个数量级的速度提升–	It gives an order of magnitude speedup over ripple carry adder

- 快速加法器和缓存是提高处理器速度的非常有效的方法•	The fast adder and cache are very effective ways to speed up a processor
  - 比流水线、超标量等提供更大的速度提升–	Provide a bigger speedup than pipelining, superscalar, etc

- 并行扫描是困难的，但也是强大的，加法器只是一个例子•	Parallel scan is difficult but powerful, the adder is just one example
  - 还有很多：关联搜索，并行最大/最小，扫描排序，...–	There are many more: associative search, parallel max/min, scansort, …

# ================

# 全加器carry传播

![](/static/2022-04-30-19-55-07.png)

---

全加器function表示（P & G

![](/static/2022-04-30-19-57-53.png)

# 超前进位加法器

> 进位超前加法器是一种快速并行加法器，因为它通过更复杂的硬件减少了传播延迟，因此成本更高。在这个设计中，加法器的固定位组上的进位逻辑被简化为两级逻辑，这不过是对RCA设计的一种改造。
> 
> 这种方法利用逻辑门来查看**被加数和加数的低位**，看**是否要产生高位进位**
> 
> 所以能在做低位加法时计算高进位

![](/static/2022-04-30-20-02-04.png)

![](/static/2022-04-30-20-07-54.png)
![](/static/2022-04-30-20-08-35.png)
![](/static/2022-04-30-20-25-40.png)
![](/static/2022-04-30-20-27-54.png)
![](/static/2022-04-30-20-28-31.png)

* 3+1