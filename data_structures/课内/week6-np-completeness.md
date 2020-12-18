# NP Completeness

NP问题

* [NP Completeness](#np-completeness)
  * [Some Effient Algorithms We have seen](#some-effient-algorithms-we-have-seen)
  * [Recall: Eulerian cycle problem](#recall-eulerian-cycle-problem)
  * [Recall: Hamiltonian Cycle problem](#recall-hamiltonian-cycle-problem)
  * [多项式vs指数时间:Polynomial vs Exponential Time](#多项式vs指数时间polynomial-vs-exponential-time)
  * [A Brief Interlude](#a-brief-interlude)
    * [棘手的问题:Intractable Problem](#棘手的问题intractable-problem)
  * [NP(C): NP(-Complete) Problems](#npc-np-complete-problems)
  * [Intractable Problems](#intractable-problems)
  * [Summary: NPC Problem](#summary-npc-problem)
  * [问题&问题实例: Problem & Problem Instances](#问题问题实例-problem--problem-instances)
    * [其他NPC问题实例: Other NP-Complete Problem](#其他npc问题实例-other-np-complete-problem)
  * [优化&搜索算法:Optimisation & Search Problems](#优化搜索算法optimisation--search-problems)
  * [多项式时间内可解-决策问题: Class P(deterministic algorithm)](#多项式时间内可解-决策问题-class-pdeterministic-algorithm)
  * [不确定多项式时间内可解-决策问题:Class NP(Non-deterministic Polynomial)](#不确定多项式时间内可解-决策问题class-npnon-deterministic-polynomial)
  * [非确定算法:Non-deterministic algorithms(NDAs)](#非确定算法non-deterministic-algorithmsndas)
    * [Example](#example)
  * [多项式时间归约: Polynomial Time Reductions(PTR)](#多项式时间归约-polynomial-time-reductionsptr)
    * [多项式时间规约-属性:Properties](#多项式时间规约-属性properties)
      * [传递性: transitivity](#传递性-transitivity)
      * [相关性: Relevance](#相关性-relevance)
    * [例子-PTR Example](#例子-ptr-example)
  * [NPC: NP-Completeness](#npc-np-completeness)
    * [已知1NPC-证明另外1个问题是否为NPC: Proving NP-Completeness](#已知1npc-证明另外1个问题是否为npc-proving-np-completeness)
    * [Cook’s Theorem: Satisfiability](#cooks-theorem-satisfiability)
  * [问题限制(子集):Problem Restrictions](#问题限制子集problem-restrictions)
  * [SAT ∝ 3-SAT](#sat--3-sat)
  * [Coping with NP-Completeness](#coping-with-np-completeness)

## Some Effient Algorithms We have seen

常见多项式时间复杂度

![](/static/2020-11-09-22-50-06.png)

* polynomial-time algorithms
  * worst-case complexity `O(n^c)` for some constant `c`

## Recall: Eulerian cycle problem

欧拉回路 Euler cycle - P

![](/static/2020-11-09-22-54-24.png)

* 无向图`G`
  * 是否满足欧拉回路：**不存在重复边的路径(cycle中每条边只出现一次)**

🍊 当且仅当每个顶点的度数为偶数时，连通的无向图存在欧拉回路

* a **connected undirected graph** has an euler cycle
  * if and only if each vertex has even degree
* 由此可以验证，无向图`G`是否存在欧拉回路
  * **邻接矩阵**形式
    * `O(n^2)` time 内验证
  * **邻接表**形式
    * `O(m+n)` time 内验证
    * 即`O(|E|+|V|)`

## Recall: Hamiltonian Cycle problem

哈密顿回路 Hamiltonian cycle - NP（complete）

![](/static/2020-11-09-23-02-04.png)

* 无向图`G`是否存在**哈密顿回路**
  * 在一个回路中，除了经过初始结点两次以外，**恰好经过每个结点一次**，则称此回路为哈密顿回路
  * 哈密顿回路中每个结点都为偶结点
* <font color="red">与欧拉回路不同, 无法在多项式时间内验证一个无向G是否有哈密顿回路</font>

🍊 Brute Force Algorithm 朴素字符匹配 - 验证哈密顿回路

![](/static/2020-11-23-14-57-57.png)

* 生成每个顶点的排列(即每个顶点组合的路径)
* 确认每个排列是否为cycle
* `n` - 顶点数
  * 最坏情况下,生成`n!`个排列`Π`
* **针对每个排列`Π`,需要`O(n^2)`时间确认是否为哈密顿回路** [假设G邻接表表示]
  * 最坏情况: 邻接表下,每条边的检测需要`n-1`次确认, 有`n`条边确认
  * **花费`O(n^2 n!)`时间复杂度** [无法多项式时间内验证, 指数时间]

## 多项式vs指数时间:Polynomial vs Exponential Time

![](/static/2020-11-23-15-00-18.png)

该图表示了不同时间复杂度下,算法耗时

* `n`增大, 多项式 & 指数函数增长差异很大

![](/static/2020-11-23-15-02-04.png)

* 对于复杂度为`3^n` 的算法来说，计算能力增加一千倍只会使`1`小时内可解决的最大问题实例的大小增加`6`倍

![](/static/2020-11-23-15-04-41.png)

* 指数时间算法通常是“糟糕的”
  * 处理器速度的增加不会导致这种缓慢的行为的重大变化时，输入大小是巨大的
* 多项式时间算法通常"良好"
  * **多指 - 高效算法**
  * **多项式时间内可解的问题 - 如果该问题可以用多项式时间算法解决**

## A Brief Interlude

![](/static/2020-11-23-15-07-50.png)

* 很难直接找到解决验证哈密顿回路问题的多项式时间算法
  * 但也许可以证明,这个问题是棘手的**intractable**

### 棘手的问题:Intractable Problem

![](/static/2020-11-23-15-09-51.png)
![](/static/2020-11-23-15-12-21.png)

Intractable problem `Π`

* 如果**不存在多项式时间算法解决问题**`Π`,则该问题为棘手的**intractable**
* 要证明一个问题是难以解决intractable的是非常困难的，而且这样的证明非常罕见
  * 但可以尝试证明,该问题`Π`与其他困难问题(NP问题)一样, 难以证明

![](/static/2020-11-23-15-13-31.png)

* 哈密顿回路
  * 无法多项式时间内解决
  * **也无法证明它是intractable的**
  * 即, **NPC(NP-Complete)问题**

🍊 解决方案

* 搜索一个多项式时间算法应给予较低的优先级
* 只能解决问题的“特殊情况”
* 可以寻找一个**指数时间**算法，在**实践**中做得相当好
* 可以搜索一个**多项式**时间算法，**只满足一些问题规格**

## NP(C): NP(-Complete) Problems

![](/static/2020-11-23-15-16-31.png)

* NP(C)问题, 无**多项式时间算法**可解
  * <font color="red">如果存在一个多项式时间算法可解其中一个NPC问题,则所有NPC问题可解</font>
* NP(C)问题,无法证明其**难解性intractability(不存在多项式时间算法的解)**
  * <font color="red">如果可证其中一个NPC问题难解(intractable),则所有NPC问题都intractable</font>

🍊 但是普遍相信NPC问题都是难解的(intractable)

* 可以视所有NPC问题,等价难解(难解程度一样)

## Intractable Problems

![](/static/2020-11-23-15-38-04.png)

造成难解性(no polynomial algo)的原因

* **多项式时间内不足以找到问题的解[关注点]**
  * 该例有,难解性推论proof
  * **有些问题需要验证其是undecidable不可判定的(不可能解出)**
  * **有些不可判定的undecidable问题需要验证其是难解的intractable**
* 解本身需要指数时间输出(解本身很大)
  * 如输出给定图G中所有cycle

## Summary: NPC Problem

![](/static/2020-11-23-15-57-00.png)

* 如NPC问题存在1个多项式时间算法的解(是多项式时间内可解的问题)
  * 则NPC问题不是难解的问题
* 如NPC问题是难解的
  * 则NPC问题非多项式时间可解的问题

## 问题&问题实例: Problem & Problem Instances

![](/static/2020-11-23-16-01-01.png)

* 问题通常由(**未指明的)参数表示**
  * 通常一个给定的问题,有无限个问题实例
* **通常给定参数值,可以创建一个问题实例**

🍊 例子 - NPC问题(decision problem)

* Name
  * Hamiltonian Cycle哈密顿回路
* Instance 实例
  * G
* 问题
  * G存在一个回路, 每个顶点只出现1次?

🍬 该问题实例是**decision problem决策问题**

* 答案是 yes/no
* 每个问题实例
  * yes-instance
  * no-instance

### 其他NPC问题实例: Other NP-Complete Problem

![](/static/2020-11-23-16-06-43.png)
![](/static/2020-11-23-16-10-30.png)
![](/static/2020-11-23-16-11-49.png)
![](/static/2020-11-23-16-12-48.png)

## 优化&搜索算法:Optimisation & Search Problems

![](/static/2020-11-23-16-16-38.png)

* 优化问题
  * 找到max/min值
  * 例子 - travelling salesman optimisation problem - 求最短路径(小于target `k`)
* 搜索问题
  * 找到合适优化结构
  * 例子 - travelling salesman optimisation problem - 求最短路径(小于target `k`)
* Np完全性主要处理**决策问题(answer - yes/no)**
  * 对应每一个优化或搜寻问题的实例
  * 是通过设置“目标”值来解决决策问题的一系列实例
  * 几乎一成不变的是，一个最优化或搜索问题可以在多项式时间内解决，(当且仅当相应的决策问题能够)

## 多项式时间内可解-决策问题: Class P(deterministic algorithm)

![](/static/2020-11-23-16-21-14.png)

* `P`代表所有**多项式时间内可解的决策问题(deterministic algorithm, 1.多项式时间内可验证出一个解 & 2.存在一个多项式时间的算法解)**
  * 例子如图, answer: yes/no

🍊 通常扩展`P`,包含优化问题&搜索问题

## 不确定多项式时间内可解-决策问题:Class NP(Non-deterministic Polynomial)

![](/static/2020-11-23-16-25-33.png)

NP(决策问题)

* **不确定多项式时间内可解的决策问题(1.不确定是否存在多项式时间内的解 & 2.可以在多项式时间内验证出一个解)**
  * <font color="red">non-deterministic algorithm不确定算法-会做出不确定决策non-deterministic choices</font>
    * 不确定算法允许guess, 输出可能不一样
  * **不确定算法比普通确定算法更强大**

🍬 P属于NP(P是NP子集)

* 确定算法(多项式时间可解问题)属于非确定算法的一个特例
  * 因为P存在多项式时间的解(NP是不确定存不存在), & 总能在多项式时间内验证他(NP也能在多项式时间内验证出一个正确解)

## 非确定算法:Non-deterministic algorithms(NDAs)

NP问题的算法解?
![](/static/2020-11-23-16-39-41.png)

* 这种算法(NDA)有额外操作 - **非确定决策non-deterministic choice**
  * 返回一个从范围1，... ，n 中选择的正整数
  * NDAs 根据返回的值有许多可能的执行

🍊 NDA可解一个决策问题`Π`,如(不确定时间)

* **yes-instance**: 答案为yes的问题实例`I`.**存在某些**执行结果返回`yes`
* **no-instance**: 答案为no的问题实例`I`.**不存在**执行结果返回`yes`

🍊 NDA可在**多项式时间内**解一个决策问题`Π`,如

* **yes-instance**: 答案为yes的问题实例`I`.**存在某些**执行结果返回`yes`[且输入步骤耗时为多项式时间内]
* **no-instance**: 答案为no的问题实例`I`.**不存在**执行结果返回`yes`

---

![](/static/2020-11-23-17-11-30.png)

NDA算法可分为

* **guessing stage**
  * non-deterministic,不确定(不确定是否存在多项式时间的解)
* **checking stage**
  * 验证解,确定deterministic&**多项式时间内可完成验证**一个正确解

---

![](/static/2020-11-23-16-46-44.png)

* 实践中,这样的NDA算法无用
  * 因为(根据问题实例)只有某些执行步骤下,会返回yes
* 但是NDA在定义NP & NPC问题中有用

### Example

![](/static/2020-11-23-17-03-06.png)

## 多项式时间归约: Polynomial Time Reductions(PTR)

![](/static/2020-11-23-17-15-00.png)

PTR多项式时间规约 - 决策问题`Π1`到决策问题`Π2`的映射`f`, 满足

* 每个`Π1`中的实例`I1`
  * 映射`f(I1)`,到问题`Π2`,可以在多项式时间内完成(归约)
  * 当且仅当, `I1`是`Π1`的yes-instance问题实例时, `f(I1)`是`Π2`的yes-instance问题实例

🍊 `Π1 ∝ Π2`

* 表示存在映射`Π1`到`Π2`的**多项式时间内归约**

### 多项式时间规约-属性:Properties

#### 传递性: transitivity

![](/static/2020-11-23-17-26-54.png)
![](/static/2020-11-23-17-52-23.png)

* 传递性 transitivity
  * `Π1 ∝ Π2` and `Π2 ∝ Π3` -> `Π1 ∝ Π3`

#### 相关性: Relevance

![](/static/2020-11-23-17-55-01.png)

* `Π1 ∝ Π2`& `Π2∈P` `Π1`可在多项式时间内归约成`Π2`,且`Π2`属于问题集`P`
  * 则,`Π1∈P`,**Π1不比Π2"难解",至少Π2与Π1求解一样难**
* **解`Π1`,先将其化为`Π2`问题实例**
  * **Π1不比Π2"难解",至少Π2与Π1求解一样难**
  * 如果可解`Π2`,则不需要特别费力可解`Π1`(只需额外执行多项式时间归约)

### 例子-PTR Example

![](/static/2020-11-23-18-01-14.png)
![](/static/2020-11-23-18-05-18.png)
![](/static/2020-11-23-18-09-18.png)

## NPC: NP-Completeness

![](/static/2020-11-23-18-10-33.png)

* NPC决策问题`Π`,满足
  * `Π∈NP`
  * `NP`集中的每个问题`Π'`
    * `Π'`可以多项式时间内归约成`Π`, NP集中每个问题可以多项式时间内归约成NPC

🍊 可知

* 如果`Π`是`NPC`问题, 且`Π∈P` - (**NPC中每个问题Π 1.有多项式时间的解 2.多项式时间内可验证一个解**)
  * 则,`P=NP` - (因为前提NPC多项式时间内有解,属于P, 本身多项式时间内又可以验证.而NP可以归约成NPC, 则NP也有解,NP=P)
  * **`NP`**中每个问题可以归约成`Π(NPC)`,**求解**(因为NPC的Π∈P,可求解, 而NP可以规约,则NP也可以多项式时间内求解,只是需要额外加上归约时间)
  * 若假设`P≠NP`, 如果`Π`是`NPC`问题
    * 则`Π∉P`
    * **NP无多项式解. NP可以归约成NPC, 因此NPC也无多项式时间的解**

### 已知1NPC-证明另外1个问题是否为NPC: Proving NP-Completeness

![](/static/2020-11-23-18-37-28.png)

* 如何证明某问题是NPC?
  * 不可能对于NP集中每个问题都进行归约
  * **可以假设我们知道1个NPC问题`Π1`**

🍊 假设知道`1`个NPC问题`Π1`. 足够证明`Π2`是否为NPC [已知NPC,证明另外一个问题是否为NPC],需要以下条件

* `Π2`是NP问题
* 存在多项式时间内归约,从`Π1`到`Π2`的映射
  *  `Π1 ∝ Π2`

---

![](/static/2020-11-23-18-42-13.png)

* 对于`Π∈NP`,因为`Π1`是NPC
  * 则存在归约,` Π ∝ Π1`
* 因为` Π ∝ Π1` & ` Π1 ∝ Π2`
  * 因为传递性,则存在归约` Π ∝ Π2`
* 因为` Π∈NP`随机性(任意问题实例), 则
  * ` Π ∝ Π2` for all `Π∈NP`
* 因此`Π2`也是NPC问题

### Cook’s Theorem: Satisfiability

![](/static/2020-11-23-18-47-15.png)
![](/static/2020-11-23-18-47-32.png)

Cook’s Theorem

* SAT(satisfiability)是NPC问题
* 这个证明包含了一个通用的从一般问题的抽象定义到 SAT 的多项式时间归约
* 可以实例化generic归约，为每个单独的 NP 问题提供实际归约

🍊 给定Cook's Theorem, 证明决策问题`Π`是NPC,只要满足

* `Π`是NP
* 存在从`SAT`到`Π`的多项式时间归约

---

例子
![](/static/2020-11-23-20-47-57.png)

* G是否存在大小为k的clique?
  * 边的每个顶点都成对

🍊根据cook定理, 证明Clique是否为NPC, 即证明

* CP是NP
* 存在从SAT到CP的多项式时间归约
  * `SAT ∝ CP`
  * 不会考,只是给idea不同问题间如何构建PTR

![](/static/2020-11-23-20-52-10.png)

* 证明`SAT ∝ CP`
* 给定`SAT`中的一个例子`B`,构建CP问题集中的一个实例`(G,K)`
  * `k` - `B`中clause `C` 数量
  * `G`的顶点 - `(l,C)` l为clause `C`中的literal
  * `G`的边 - `{(l,C), (m,D)}` 当且仅当`l≠¬m & C≠D`
    * ¬l≠m
    * 如果可以同时满足来自不同子句clause的不同字面量，则为 edge
  * 多项式时间创建`O(n^2)`, `n`为literals数
    * 最坏情况: 构建边的时候需要比较每个literal
* 当且仅当, `G`有大小为`k`的clique时,`B(SAT)`满足

![](/static/2020-11-23-20-52-22.png)

* 证明多项式时间归约
  * 如`B`有满足赋值
    * 如果每个子句`C`中选择一个`true`的literal
    * 对应的顶点在 g 中形成一个大小为 k 的团
  * 如`G`存在大小为`K`的clique,则
    * 将团中与某个顶点相关联的每个literal赋值为`true`，可以得到`B`的一个满意的赋值

![](/static/2020-11-23-21-08-16.png)

* `G`的边 - `{(l,C), (m,D)}` 当且仅当`l≠¬m & C≠D`
  * 不同子句间的literal之间的边
  * 可以同时满足的不同literals之间的边
* 因此，在一个大小为 k 的团体中(回忆 k 是子句的数量)
  * 必须包括每个子句的一个字面值(`k`个 子句)
  * 我们可以同时满足clique中里所有的literal
  * 即可以满足所有子句
    * 子句是一种literals的分离，我们可以满足其中的一个
  * 因此满足`B`
    * `B`是子句的结合conjunction

![](/static/2020-11-23-21-13-38.png)
![](/static/2020-11-23-21-14-42.png)

## 问题限制(子集):Problem Restrictions

![](/static/2020-11-23-21-19-23.png)

* 问题的限制,由**原问题的子集实例组成**
  * 如果给定决策问题`Π`的**限制(子集)是NPC, 则`Π`本身也是NPC**
  * 给定NPC问题`Π`, 其限制子集**可能为NPC**

🍊 如分团例子中, 限制出一个cubic graphs `P`中实例

* 每个顶点都属于3条边
* 最大分团`k=4`,需要exhaustive search `O(N^4)`
* 然而图上色的cubic graph限制子集是NPC的

![](/static/2020-11-23-21-26-42.png)

* k-couring
  * 对固定数目 k 的颜色的图着色的限制子集
  * **2-couring**: 在 p 中(它简化为检查图是二部图)
  * **3-couring**是 NPC
* K-SAT
  * 对每个子句中包含 k 数目个literals的限制子集
  * **2-SAT**: P
  * **3-SAT**: NPC
  * 证明**3-SAT∈NP**很简单
    * 只需要证明`SAT ∝ 3-SAT`

## SAT ∝ 3-SAT

![](/static/2020-11-23-21-31-26.png)
![](/static/2020-11-23-21-41-19.png)
![](/static/2020-11-23-21-41-33.png)
![](/static/2020-11-23-21-42-47.png)

* 给定SAT实例`B`, 构建一个3-SAT的实例`B'`. 对于`B`的每个子句`C`,为`B'`构建一定数量的子句
  * 如`C=l1`

## Coping with NP-Completeness

![](/static/2020-11-23-21-50-32.png)

如何解决NPC问题?

* 限制参数, 可能为P问题
  * 2-SAT, 2-colouring
* 求一种改进穷举搜索的指数时间算法
  * 例如回溯(如在评估的练习中)、分支及界限
  * 应扩展可解实例的集合
* 对于优化问题(例如计算 min/max 值)
  * 满足于一个多项式时间的近似演算法
  * 特别是如果它给出了一个可证明的好结果(在某些最优因素范围内)
  * 使用启发式程序
    * 例如遗传算法、模拟退火、神经网络
* 对于决策问题
  * 满足于高概率的概率算法正确答案