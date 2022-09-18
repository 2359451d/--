# Content

Fundamental components in Optimization

* selection selectivity & join selectivity构成优化步骤的基础，，也就是选择性决策？
* Selection Selectivity: fraction of tuples satisfying a condition.
  * 选择选择性：满足where条件的元组比
* Join Selectivity: fraction of matching tuples in a Cartesian space.
  * 连接选择性：笛卡尔空间中匹配元组的比例

:orange: 执行前预测

挑战1：**执行查询前。预测选择查询cardinality，即预测满足选择查询的选择条件的元组数量**。 Challenge 1: Predict the selection cardinality, i.e., predict the number of tuples satisfying a selection condition of a selection query.

挑战2：**执行查询前，预测**给定的选择查询中我们**需要检索的块数** Challenge 2: Predict the number of blocks we need to retrieve given a selection query

挑战3：**细化涉及选择性指标的选择策略的预期成本**。 Challenge 3: Refine the expected cost of selection strategies involving the selectivity metric;

* 基于 两个选择性(是数值)，基于选择语句的策略，能细化预测块访问成本吗？
* **预期成本由选择性函数表示** expected cost is expressed as a function of (selection/join) selectivity

---

* [Content](#content)
* [查询优化概念 & 成本函数（优化参数）：QUERY OPTIMIZATION](#查询优化概念--成本函数优化参数query-optimization)
* [COST-BASED OPTIMIZATION](#cost-based-optimization)
* [选择选择性-基于成本查询优化基础：Selection Selectivity](#选择选择性-基于成本查询优化基础selection-selectivity)
* [选择基数：SELECTION CARDINALITY](#选择基数selection-cardinality)
* [选择性预测（计算方式）：SELECTIVITY PREDICTION](#选择性预测计算方式selectivity-prediction)
  * [无假设，直方图估算](#无假设直方图估算)
  * [（值）均匀分布假设：Uniformity Assumption](#值均匀分布假设uniformity-assumption)
    * [属性为键 - 均匀分布假设](#属性为键---均匀分布假设)
    * [属性为非键](#属性为非键)
* [范围选择选择性(均匀分布下)：Range selection selectivity](#范围选择选择性均匀分布下range-selection-selectivity)
* [合取选择性（均匀分布假设）：Conjunctive Selectivity](#合取选择性均匀分布假设conjunctive-selectivity)
* [析取选择性（均匀分布假设）：DISJUNCTIVE SELECTIVITY](#析取选择性均匀分布假设disjunctive-selectivity)
* [选择语句-选择性 & 基数总结](#选择语句-选择性--基数总结)
* [预测预期成本 （预测成本）- 例子](#预测预期成本-预测成本--例子)
* [细化选择成本：SELECTION COST REFINEMENT](#细化选择成本selection-cost-refinement)
  * [无关选择性](#无关选择性)
  * [主索引-范围查询细化](#主索引-范围查询细化)
  * [聚簇索引细化：Clustering Index](#聚簇索引细化clustering-index)
  * [二级索引-非排序键（无关选择性）](#二级索引-非排序键无关选择性)
  * [二级索引-非排序非键](#二级索引-非排序非键)
* [优化例子1：Optimization Example](#优化例子1optimization-example)
* [优化例子2](#优化例子2)

# 查询优化概念 & 成本函数（优化参数）：QUERY OPTIMIZATION

:orange: 概念

* input: Query本身
* Output：涉及所有要执行的操作，但以优化的方式执行，目标是最小化计算复杂性 --- 【optimal execution plan】

2种优化类型

* 启发式优化 Heuristic Optimization
  * **任务。使用关系代数将一个SQL查询转化为一个等价的高效查询** Task: Transform a SQL query into an equivalent and efficient query using Relational Algebra. (优化器能自动转换？)
* **基于成本优化** Cost-based Optimization
  * **针对任何SQL查询，提供替代执行方案，并能预测执行方案的成本** 【 提供备选的执行计划，并估计（预测）其成本  Provide alternative execution plans and estimate (predict) their costs 】
  * **选择成本最低的计划**。Choose the plan with the minimum cost;
  * 因此，**引入成本函数(带优化参数，出于何值时，能最小化成本函数**) `c(x1,x2,x3,x4,...)` with **optimization parameters**
    * **x1= # block accesses**,
    * **x2= memory requirements**,
    * x3= CPU computational cost
    * x4= network bandwidth,
      * 分布式计算管理系统中，传输数据

# COST-BASED OPTIMIZATION

利用：**统计信息**来估计一个查询的执行成本。Exploit: statistical information to estimate the execution cost of a query.

![](/static/2021-05-02-14-29-50.png)

* <font color="deeppink">注意，是在执行SQL前进行成本预测的，然后基于这个预测再决定优化执行方案</font>

:orange: **需要每个关系的统计信息** information for each Relation

* **记录数**（r）；**每个记录的（平均）大小**（R）。number of records (r); (average) size of each record (R)
* **块数**（b）；**块因子（f），即每块包含的记录数** number of blocks (b); blocking factor (f) i.e., records per block
* Primary File Organization: heap, hash, or sequential file
* Indexes: primary, clustering index, secondary index, B+ Trees

:orange: **需要每个关系种每个属性`A`的统计信息** Information for each attribute A of each Relation

* 属性A的 **不同值数（NDV**）
  * `n`
* **值域范围**。
  * [min(A), max(A)]。
* **类型**：
  * 连续或离散属性；continuous or discrete attribute;
  * 键或非键 key or non-key
* 如有索引，属性A的**索引层数**`t` Level t of Index of the attribute A, if exists

![](/static/2021-05-02-14-30-29.png)

:orange: **核心统计信息** core statistical information

* 每个属性`A`的(值的)，概率分布函数 `P(A = x)`
  * 代表 **属性`A`取某值的可能性/频率** indicates the frequency(probability/possibility) of each value `x` of the attribute `A` in the relation
* <font color="red">有多种预测概率分布函数的方式</font>
  * good approximation -> histogram **直方图**
* **WHY**：因为可以从直方图中提取概率值，用于预测成本
  * 如绿色部分 `18%`概率
    * P(Salary ≤ 30K) = integral: 0 to 30 = 0.18 or 18%
    * P(Salary = 30K) = 0.12 or 12%
  * Meaning 1: 18% of tuples have Salary ≤ 30K
    * 也许用来分析 inequality
  * Meaning 2: Each tuple has Salary ≤ 30K w.p. 18%
    * **随机选取一个记录，有18%的概率 <30K**

# 选择选择性-基于成本查询优化基础：Selection Selectivity

**选择选择性**（满足where条件的元组比） selection selectivity `sl(A)` of attribute A is a real number:

![](/static/2021-05-02-16-42-20.png)

* `0 ≤ sl(A) ≤ 1`
  * 特定值
* `sl(A) = 0 `，**没有一条记录满足属性A的条件**。 none of the records satisfies a condition over attribute A.
  * `SELECT * FROM EMPLOYEE WHERE Salary = 1,000,000,000`
* `sl(A) = 1`，**所有记录都满足属性A的一个条件**。 all the records satisfy a condition over attribute A
  * `SELECT * FROM EMPLOYEE WHERE Salary > 0`
* ` sl(A) = x`, **`x`%的记录满足属性A的一个条件** x% of the records satisfy a condition over attribute A

即，选择选择性 - 能满足选择条件的元组的概率

# 选择基数：SELECTION CARDINALITY

执行查询前，预测给定选择条件，能筛选出多少个元组记录 Given r tuples and a selection condition over A, predict the expected number of tuples satisfying this condition without scanning the file

![](/static/2021-05-02-16-49-59.png)

:orange: expected number (selection cardinality)

* `sl(A) * r` **满足属性A条件的元组的平均数量**。 average number of tuples satisfying a condition over attribute A.
* `s = r * sl(A) ∈ [0, r]`
  * `r` tuples

:orange:  选择性的预测确实很困难! (有时是难以解决的)；我们做出假设和/或近似值 Selectivity prediction is indeed difficult! (sometimes, intractable); as scientists, we make assumptions and/or approximations

---

:orange: 例如

![](/static/2021-05-02-16-50-10.png)

# 选择性预测（计算方式）：SELECTIVITY PREDICTION

## 无假设，直方图估算

![](/static/2021-05-02-17-11-50.png)

Solution 1 (**no assumption**; approximation):

* **无(针对值如何分布的)假设，利用直方图估算属性的概率分布函数**（利用直方图表示概率分布函数） Approximate the distribution of values via a histogram
* Gain: **较精确的选择选择性估计** accurate selectivity estimate;
* Cost: **引入直方图维护开销** maintenance overhead.
  * 因为每次改值，改属性，需要针对属性建立直方图
  * 注意要权衡，因为假设有很多关系，每个关系平均256个属性，那么几乎需要维护几千个直方图

:orange: selection selectivity & probability distribution function

* `sl(A = x) ≈ P(A = x)`, which depends on the value of `𝑥 ∈ [min 𝐴 , max 𝐴 ]`

例子

![](/static/2021-05-02-17-11-30.png)

## （值）均匀分布假设：Uniformity Assumption

![](/static/2021-05-02-17-20-43.png)

假设所有值均匀分布，不用维护直方图 All values are uniformly distributed (equiprobable), thus, no histogram.

* 每个值访问到的概率相同
* Gain: **少了维护直方图开销** no need to maintain (update) a histogram
* Impact: **选择选择性预测不准确** provide a less accurate prediction for sl(A)

:orange: 此时选择选择性，无关我们条件中的值，是一个常量

* `sl(A = x) ≈ constant independent of the x value`;
* `∀𝑥 ∈ [min 𝐴 , max 𝐴 ]`

### 属性为键 - 均匀分布假设

![](/static/2021-05-02-17-53-13.png)

:orange: **假设属性`A`是键，值唯一** Let an equality condition on the key attribute A. Then, a good estimate is:

* 这种情况下，作均匀分布假设很好，因为选择选择性就是 1/r，r为记录总数
* `sl(A = x) = 1/r`
  * `∀𝑥 ∈ [min 𝐴 , max 𝐴 ]`

:orange: 此时选择基数 `s=1`，因为只有1个记录能满足条件，属性为键 since only one tuple satisfies the condition; selection cardinality s = 1 tuple.

* 没有建立直方图的意义 …there is no meaning to build a histogram

### 属性为非键

![](/static/2021-05-02-18-20-34.png)

**NDV**： Number of distinct values of the attribute

* 假设 `NDV(A)=1`，则所有记录的A属性 值相同
* 假设 `NDV(A)=r`，则所有记录的A属性 值都不同
* 因此 `1<= NDV <= r`

:orange: **非键属性`A`上的条件，`n = NDV(A) <= r`个唯一值，则不好的估算是（均匀分布假设下的选择选择性）** Equality condition on a non-key attribute A, with n = NDV(A) < r number of distinct values. Then, a not-so-good estimate is:

* `sl(A=x) = 1/NDV(A) = 1/n`
* `∀𝑥 ∈ [min 𝐴 , max 𝐴 ]`

:orange: 注意均匀分布下，**假设所有记录都根据 `n`个唯一值均匀分布**

* `r/NDV(A) = r/n`，某一个唯一值下所有的记录数 number of tuples having a distinct value
* `P(A = x) = (r/n)/r = 1/n`

:candy: ...一个属性具有均匀分布的概率几乎为零 …the probability of an attribute having uniform distribution is almost zero

* but this assumption at least make life easier

# 范围选择选择性(均匀分布下)：Range selection selectivity

![](/static/2021-05-02-18-42-41.png)

range selection selectivity - inequality 不等式条件（关于某属性`A`的）

:orange: 两种range

* **值域范围 domain range**
  * `max(A) - min(A)`
  * `A ∈ [min(A), max(A)]`
* **范围查询范围 query range**
  * `max(A) – x`
  * `x ∈ [min(A), max(A)]`

当 `x>max(A)`时， `sl(A>=x)=0`

假设均匀分布, range query的selection selectivity

* `sl(A>=x) = (max(A)-x) / (max(A) - min(A)) ∈ [0,1]`

# 合取选择性（均匀分布假设）：Conjunctive Selectivity

![](/static/2021-05-02-18-50-18.png)

how many tuples can satisfy both the conditions,

* 每个属性的选择性相乘的积
* `sl(Q) = sl(A = x) * sl(B = y) ∈ [0, 1]`

---

假设A属性值均匀分布，B属性值也均匀分布

* 则， `P(A ∩ B) = P(A) * P(B)`
* 考虑到条件A在统计上独立于条件B，满足两个条件的雇员的联合概率 joint probability an employee satisfying both conditions, given that condition A is statistically independent of condition B

# 析取选择性（均匀分布假设）：DISJUNCTIVE SELECTIVITY

析取选择性

![](/static/2021-05-02-18-53-40.png)

* `sl(Q) = sl(A = x) * sl(B = y) ∈ [0, 1]`
* `P(A U B) = P(A) + P(B) - P(A).P(B)`
  * 雇员满足条件A**或**条件B的概率；这两个条件在统计学上是独立的。 probability an employee satisfying either condition A or condition B; both conditions are statistically independent.

# 选择语句-选择性 & 基数总结

挑战1：**预测满足选择条件的元组数量（也就是根据选择性，得到的选择基数 selection cardinality**）! Challenge 1: Predict the number of tuples satisfying a selection!

假设。**元组在属性A的值中是均匀分布的** Assumption: The tuples are uniformly distributed across the values of attribute A

---

Selection Selectivity: `1/NDV(A) = 1/n`

Selection Cardinality: `(1/NDV(A)) * r = r/n`

---

如属性为键 key，（值唯一）

* `n=NDV(A)=r`，所有记录的属性值都不同
  * 选择基数 `s=1 tuple`

如属性为非键 non-key

* `n=NDV(A) <r`
  * 选择基数 `s > 1 tuple`

# 预测预期成本 （预测成本）- 例子

![](/static/2021-05-02-20-18-13.png)

挑战2：预测满足选择的区块数量（**预测成本**）! Challenge 2: Predict the number of blocks satisfying a selection (predict cost)!

* 已知；Context
  * **块因子** `f` Blocking factor f tuples/block,
  * **选择基数** selection cardinality `s = (1/NDV(A)) * r = r/n`

:orange: 预测块访问次数 - 预测成本

* 预测检索到的块数是`n`的倒数函数。 The predicted number of blocks retrieved is a reciprocal function of n:
* `ceil( s / f ) = ceil( r / (f * NDV(A)) ) = r/nf` blocks
  * `s/f`, 满足检索条件的记录一共需要的块数

# 细化选择成本：SELECTION COST REFINEMENT

![](/static/2021-05-02-20-46-17.png)

精确到，用关于选择性的函数表示成本 `sl(A) = r/n`

`SELECT * FROM RELATION WHERE A = x`

* `b` blocks, `f` blocking factor (tuples/block), `r` records, `n = NDV(A)`

---

假设关系根据 属性`A`排序，进行二分查找

* 如`A`是键 key
  * 则预期成本 `log2 b`次块访问
  * **与 `sl(A)`选择性无关**（<font color="deeppink">无法细化，最坏情况下访问所有块，不够精确</font>）
* 如`A`非键 not a key
  * 二分查找定位**满足条件`A=x`的第一个块** - `log2 (b)` block accesses to reach the first block with record(s) A = x
  * 加载剩下的所有连续块，满足 `A=x`
  * 则，选择基数 `s=r*sl(A=x) tuples`
  * 一共需要**访问 `ceil(s/f) - 1`个剩余的块**
    * 块因子：`f tuples/block`
  * 则<font color="red">成本 （用关于选择性 `sl`的函数表示）</font>
    * `log2 b + ceil(s/f) - 1 = log2 b + ceil(r * sl(A)/f) - 1`
  * <font color="deeppink">因此，如果我们能提供高精度的选择性值，那么就能得到高精度的预期成本</font>

## 无关选择性

![](/static/2021-05-02-20-47-40.png)

* 无关选择性，无法细化得到精确预测成本

## 主索引-范围查询细化

![](/static/2021-05-02-20-55-07.png)

* `t` 先找到SSN=10的第一块
* 加载后续所有的连续块（满足选择条件的）
* **此时可以利用选择性，因为需要预测能检索多少个tuples**
* 范围选择基数 - `s = r * sl(A)`
* 块因子 `f` records/block
* 因此，需要 `ceil(s/f)`个块

:orange: 细化后预期成本 Expected Cost:

* `t + ceil(s/f) = t + ceil(r * sl(A) /f)` block accesses

## 聚簇索引细化：Clustering Index

1. `t`次块访问，定位第一块 `DNO=3`的块
2. 预测块访问次数，剩下满足 `DNO=3`的块的数量 【 **利用选择选择性**】
   1. 选择基数 `s=r * sl(A)` tuples
   2. 块因子 `f` records/block
   3. 块数量 `ceil(s/f)` blocks

预期成本 expected cost

* `t + ceil(s/f) = t + ceil(r * sl(A) /f)`

细化后成本 Fine-grained Cost:

* `t+ [r/f * P(A=x)]`
  * `P(A=x)` - 概率，，也就是某值的selectivity

## 二级索引-非排序键（无关选择性）

![](/static/2021-05-02-21-42-39.png)

* 因为是键值，直接`t+1`访问

## 二级索引-非排序非键

![](/static/2021-05-02-21-58-54.png)

此例，假设聚簇中只有1块装有记录指针的块

1. `t`
2. `1` 加载装有记录指针的块
3. **该块里装有多少个记录指针？** 
   1. 假设最坏情况下，含有薪资 40K的记录均匀分布在文件所有块中
   2. 选择基数 `s= r* sl(A)` tuples
   3. 最坏情况下，每个记录都在不同数据块中，`s`块 Each tuple may be in a different data block (worst case) thus, access up to s blocks

细化成本（**最坏情况**）

* `t + 1 + s = t + 1 + r * sl(A)`
* `t` - go down the tree
* `1` - load the actual block of block which store the record pointers
  * 这里可能需要加载更多块，因为**可能存在很多块，装有记录指针**，**取决于（记录指针块的）块因子**
* `s` - load blocks as many as the number of the employees (**因为假设均匀分布，所有员工有薪资40K的分布在文件的所有位置**，注意：实际情况中很不可能)

# 优化例子1：Optimization Example

![](/static/2021-05-02-22-29-25.png)

---

![](/static/2021-05-02-22-47-08.png)

Employee r = 10,000 records, b = 2,000 blocks, blocking factor f = 5

`SELECT * FROM EMPLOYEE WHERE DNO = 5 AND SALARY = 30000 AND EXP = 0`

Memory: 100 blocks; relation sorted by Salary.

* 提供多种执行方案 & 选择最优
* 考虑每个条件 & **中间结果集大小**

P0 - 线性搜索 - `2000`

P1 - `DNO=5`

![](/static/2021-05-02-22-46-56.png)

* 利用 B+ non-key (secondary index，假设均匀分布，最坏情况)
  * `xDNO + 1 + sDNO = 2 + 1 + 80 = 83 block accesses`
    * `1` - 假设只有1个包含记录指针的块
* **中间结果集**
  * 执行过程中，要存储 `sDNO=80`条记录，即 `ceil(80/5)=16 blocks`在内存中
    * `16<100`，可以容纳
* 之后应用剩下的 条件 `Salary=30000 AND exp=0`
* **P1成本**
  * `83` block accesses

---

![](/static/2021-05-02-22-47-46.png)

P2同理，先满足 `SALARY = 30000`，推测预期成本。之后再应用于其他条件。明显优于P1

---

P3，`EXP`二级索引，注意内存无法容纳，除了块访问次数，，**还需要计算写回磁盘的块数**

![](/static/2021-05-02-22-51-23.png)

---

归纳 P0-3成本

![](/static/2021-05-02-22-51-59.png)

* Best plan - 2

# 优化例子2

![](/static/2021-05-02-22-29-25.png)

---

任务1: 估计查询的选择性，并估计理想的区块访问次数! Task 1: Estimate the query selectivity and estimate the ideal block accesses!

![](/static/2021-05-03-00-01-09.png)

* 根据 independence, 先预测出 至少 968次块访问，未细化
  * 找到一个执行方案（利用访问结构），接近这个

---

Task 2: Find the Optimal Plan

Baseline: Linear Search: b = 2,000 block accesses

Memory: 1100 blocks

:orange: **析取策略：对应于OR条件的部分结果的并集** Disjunctive Strategy: union of partial results corresponding to OR conditions

![](/static/2021-05-03-00-09-43.png)

* 每次先关注OR的 某部分
* `DNO=5`
* `SALARY >= 500 AND EXP = 0`
  * 应用 **合取策略** conjunctive strategy: examine both conditions; filter-out intermeidate result
    * 先看 `SALARY >= 500`，在应用剩下的 `EXP=0`
    * 先看 `EXP=0`，再应用剩下的 `SALARY >= 500`

上面合取策略分析

先看 `EXP=0`，再应用剩下的 `SALARY >= 500`

![](/static/2021-05-03-00-14-30.png)

---

![](/static/2021-05-03-00-33-57.png)
![](/static/2021-05-03-15-55-53.png)

* 内存保留可容纳的快，剩下的先写回
* **加载在内存中的块，过滤**
  * 判断是否满足第二个条件 `EXP=0`
    * `1084`个块在内存里，834个在磁盘里， 释放 `0.5*1084=542`个块
    * 再从磁盘里读 `542`个，还剩 `292`个等待过滤。释放 `0.5 * 542= 271`个块。
    * 再从磁盘里读 `271`个，还剩 `21`个等待过滤。释放 `0.5 * 271= 135`个块
    * 再从磁盘里读 `21`个，。释放 `0.5 * 21`个块
  * 最后一共要 `16（DNO=5的） + 542 + 271 + 135 + 21*0.5 = 975`个内存块

---

总结

![](/static/2021-05-03-00-39-24.png)