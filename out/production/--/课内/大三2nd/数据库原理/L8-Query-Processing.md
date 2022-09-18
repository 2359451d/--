# Content

Query Processing

* External Sorting: Fundamental Operator
* best Strategies for `SELECT`
  * Simple query, conjunctive selection query, disjunctive selection query
* Strategies for JOIN
  * Five fundamental algorithms for JOIN

原则：**首先对每个计划(sql)的成本进行估算，选择预期成本最低的计划，最后执行sql**! Principle: firstly, estimate the cost of each plan, choose the plan with the minimum expected cost, and finally execute!

---

* [Content](#content)
* [为什么存在基本的内部排序：FUNDAMENTAL TOOL: SORTING](#为什么存在基本的内部排序fundamental-tool-sorting)
* [外部排序概念 & 成本：EXTERNAL SORTING- OVERVIEW](#外部排序概念--成本external-sorting--overview)
  * [外部排序概念/原理](#外部排序概念原理)
  * [外部哈希成本](#外部哈希成本)
* [Strategies for Select](#strategies-for-select)
  * [普通选择查询：Simple selection query](#普通选择查询simple-selection-query)
    * [单关系查询-线性/二分搜索](#单关系查询-线性二分搜索)
      * [主索引 vs 哈希](#主索引-vs-哈希)
    * [范围查询-多层主索引：Range Query](#范围查询-多层主索引range-query)
    * [多层聚簇索引](#多层聚簇索引)
    * [B+：非排序键-二级索引](#b非排序键-二级索引)
    * [B+：非排序非键-二级索引](#b非排序非键-二级索引)
  * [析取选择：STRATEGIES FOR DISJUNCTIVE SELECT](#析取选择strategies-for-disjunctive-select)
  * [合取选择：STRATEGIES FOR CONJUNCTIVE SELECT](#合取选择strategies-for-conjunctive-select)
* [JOIN可选择的各执行算法：Strategy for JOIN](#join可选择的各执行算法strategy-for-join)
  * [Naive JOIN](#naive-join)
  * [NESTED-LOOP JOIN](#nested-loop-join)
    * [具体细节：Algorithm](#具体细节algorithm)
  * [INDEX-BASED NESTED-LOOP JOIN](#index-based-nested-loop-join)
  * [SORT-MERGE JOIN （需要有序）](#sort-merge-join-需要有序)
    * [Example](#example)
  * [HASH-JOIN](#hash-join)
  * [总结：So Far](#总结so-far)
* [嵌套循环JOIN成本预测：Nested Loop Join Cost Prediction](#嵌套循环join成本预测nested-loop-join-cost-prediction)
  * [成本决策归纳 & 决策例子](#成本决策归纳--决策例子)
* [基于索引嵌套循环成本预测：INDEX-BASED NESTED-LOOP COST PREDICTION](#基于索引嵌套循环成本预测index-based-nested-loop-cost-prediction)
  * [决策](#决策)
* [SORT-MERGE-JOIN COST PREDICTION](#sort-merge-join-cost-prediction)
  * [无序例子](#无序例子)
  * [决策](#决策-1)
* [HASH-JOIN COST PREDICTION](#hash-join-cost-prediction)
* [所有JOIN算法成本- PUT-ALL-TOGETHER: JOIN COST PREDICTION](#所有join算法成本--put-all-together-join-cost-prediction)
* [例子-递归关系：基于索引嵌套循环Join](#例子-递归关系基于索引嵌套循环join)
  * [使用基于PK索引](#使用基于pk索引)
  * [使用基于FK的索引](#使用基于fk的索引)
  * [Decision](#decision)

# 为什么存在基本的内部排序：FUNDAMENTAL TOOL: SORTING

![](/static/2021-04-16-12-53-57.png)

:orange: **几乎所有的SQL查询都涉及到由用户定义的元组排序请求，如** Almost all SQL queries involve sorting of tuples w.r.t. ad-hoc sorting requests defined by the user, e.g.

* `CREATE PRIMARY INDEX ON EMPLOYEE(SSN)`
  * 根据 `SSN`排序
* `ORDER BY Name`
  * 根据 `Name`排序
* `SELECT DISTINCT Salary`
  * 根据 `Salary`排序，创建聚簇，之后找出唯一值 sort by salary to create clusters and then identify the distinct values
* `SELECT DNO, COUNT (*) FROM EMPLOYEE GROUP BY DNO`
  * 根据 `DNO`排序，创建聚簇,之后再执行任意聚合操作...

:candy: 局限性 fundamental limitation

* **无法在内存中存储完整关系（large relation**）relation用任何排序算法用来排序记录 --- 【**内部排序** internal sorting 】Fundamental Limitation: we cannot store the entire relation into memory for sorting the records (bubble sort; quick sort; heap sort; merge sort; …)
* <font color="red">所以需要引入外部排序方法/算法，用于处理相对大的关系，因为大多数时间这些关系都无法装入主存</font> introduce a new sorting algorithm which is called external sorting algo and using that to deal with relatively large relations as most of the time a relation cannot fit in the main memory. i.e. cannot do that internally

# 外部排序概念 & 成本：EXTERNAL SORTING- OVERVIEW

## 外部排序概念/原理

当relation过大情况下，，外部排序的概念 & 基于块访问，通过外部排序排序记录所需成本

![](/static/2021-04-16-13-07-03.png)

:orange: 外部排序原理 --- 基于**Principle: Divide & Sort (Conquer**)

* **分 Divide**
  * 将 `b`块文件分为包含 `L`个子文件块的文件 a file of b blocks into L smaller sub-files (b/L blocks each).
  * 每个子块占 `b/L`，<font color="red">确保每个子块能从disk装入主存</font>
* **Sort 排**
  * 每次装入一个子文件块至主存，使用排序算法排序，之后再写回disk load each small sub-file to memory, sort using e.g., quick-sort, bubble-sort and write it back to the disk.
* 【**合 Merge**】
  * 每次从硬盘中加载>=2个子文件至主存，进行合并（得到比原来大的有序子文件），之后再写回 merge two (or more) sorted sub-files loaded from disk in memory creating bigger sorted sub-files, that are merged in turn
  * 重复操作，直到整个文件都有序 repeat the process & until the whole big file is sorted

## 外部哈希成本

sort-merge外部排序方法的成本，给出

![](/static/2021-04-16-14-30-51.png)

* `2b * （1+logM L）`
* `b` 文件块数
* `M` degree of merging
  * number of sorted blocks merged in each loop
  * <font color="red">合并程度，一次有多少个区块在一起合并。可为2或大于2的任何值</font> mndegree of merging, how many blocks at a time that are merging together.can get a value of 2 or any value greater than 2
* `L` 初始有序子文件数 （merging步骤之前） number of initial sorted sub-files (before entering merging phase)

:candy: **这种方法论(sort-merge strategy)的计算成本很高，因为不能很好地随着块的数量而扩展（`2b`**）。这就是为什么我们在决定对一个大的关系进行排序时，必须非常小心，（特定属性的决策） this methodology is computationally expensive because does not scale well with the number of the blocks. That's why we have to be very careful when we're about to decide on sorting a big relation w.r.t. specific attribute

---

:orange: 合并程度选值 `M`

![](/static/2021-04-16-14-31-06.png)

* `M=2`，最坏情况下外部排序成本 worst-case performance
  * 因为每次只合并一对子文件块 Because: merge in parallel only a pair of blocks at each step
* `M>2`，M-way merging
  * 每次合并>2个块，merge more than two blocks at each step

# Strategies for Select

基于外部哈希 & primary access path & secondary access path (索引)，，能为执行查询sql提供策略，分析之后再最终决定 如何执行

---

## 普通选择查询：Simple selection query

### 单关系查询-线性/二分搜索

:orange: Linear search & Binary Search

![](/static/2021-04-16-14-37-03.png)

* Binary search over a key
  * 乱序情况下需要额外，外部排序成本来排序再进行二分查找，，`log2 b + 2b (1+ logM L)`

---

#### 主索引 vs 哈希

:orange: Primary index or Hash function over a **key**

![](/static/2021-04-16-14-49-41.png)

* sorted file,primary index -  `t+1`
  * `1` - load actual data block
* hashed file - `1+O(n)`
  * `1` - if in main bucket
  * `O(n)` - in `n` overflown buckets

### 范围查询-多层主索引：Range Query

![](/static/2021-04-16-15-03-57.png)

:orange: t层主索引范围查询成本： cost of primary index of level `t` over the key (file sorted by key field)

* t（一趟定位下限块） + **连续块** （因为主索引，源文件一定是根据key排序的，可以直接加载，，最坏情况下加载所有的剩余块）
  * 即， `t+O(b)`

:orange: **范围查询不要使用哈希方式** Do not use Hashing for range queries

* 效率极低

### 多层聚簇索引

cluster index over **ordering, non-key field**

![](/static/2021-04-16-16-53-31.png)

假设 `DNO=n`，且每个部门的员工均匀分布（每个DNO记录数大体相等）

则，聚簇索引访问成本

* `t（索引层） + 1（第一个DNO聚簇，DNO=5）+ 通过链式指针访问剩余连续块（因为源文件根据DNO有序）`
  * <font color="red">假设一共 `b`块，且`DNO`唯一值有 `n`个，则假设均匀分布一共 `b/n`个聚簇要被加载</font>
  * `t + O（b/n）`

### B+：非排序键-二级索引

二级索引 over non-ordering key field  非排序键字段（这种情况考虑用B/B+实现多层二次级索引）

---

dense - secondary index(B+ tree) over **non-ordering key**，单记录查询，B+非排序键字段查询成本

![](/static/2021-04-16-17-05-44.png)

* `t + 1`
  * `t`层索引
  * `1` 加载实际块
* **B+叶结点指向唯一块** B+ Leaf Node points at the unique block

### B+：非排序非键-二级索引

sparse index - Secondary Index (B+ Tree) over non-ordering, non-key

![](/static/2021-04-16-18-55-00.png)

* 非排序非键，，每个唯一值形成一个聚簇，，每个聚簇分配一个索引项，索引项指针指向聚簇，，每个聚簇包含多个 记录指针块，包含f个记录指针，，每个记录指针指向记录本身
* **文件根据非键字段 non-key field 乱序**

成本

* `t` - 多层索引定位 聚簇（block/cluster）
* `1` - 假设这一个记录指针块，包含了所有记录的指针 （当然有可能需要加载不止1个记录指针块，这里只是简单假设）
* `O(b)` 加载所有记录指针对应的块，最坏情况下，需要加载所有块

## 析取选择：STRATEGIES FOR DISJUNCTIVE SELECT

![](/static/2021-04-16-19-44-06.png)

**析取选择：条件中包含`OR`的** Disjunctive Selections: conditions involving `OR` ---

Final result: contains tuples satisfying the **union of all selection conditions**

:orange: Methodology

* <font color="red">如果所有属性（条件中涉及的）都存在存在索引/哈希 e.g.,B+/hash/primary-index</font> if there exists primary access paths or secondary access paths for all of the attributes
  * **使用每个属性的访问方式来检索满足条件的查询集** use each to retrieve the set of records satisfying each condition
  * **取并集得到结果** union all sets（subsets） to get the final result.
  * **如**：build a secondary index over the salary and primary index over the name. use both indexes to retrieve those tuples of satisfying the first condition and those tuples satisfying the second condition. then take the union of these subsets
* <font color="red">如果所有属性（条件中涉及的）不存在索引/哈希 e.g.,B+/hash/primary-index，或是 只有几个属性存在访问结构</font> if none or some of the attributes have an access path,
  * linear search is unavoidable
  * **为什么**：我已经在工资上建立了一个索引，但在姓名属性上没有建立任何索引。所以可以利用工资属性的索引来尽可能快地检索。但是没有在Name上建立二级访问路径或一级访问路径，所以必须处理线性搜索。 I have built an index over the salary. but built nothing over the Name attribute. So can exploit the index over the salary attribute to retrieve as fast as possible. But have not built a secondary access path or primary access path over Name, so have to deal with the linear search

:orange: <font color="red">因此处理这类查询的时候，要警惕是否为条件中涉及的字段建立额外访问结构，以此来加速查询效率；否则线性搜索不可避免</font>

## 合取选择：STRATEGIES FOR CONJUNCTIVE SELECT

Conjunctive Selections: conditions involving `AND`

:orange: Methodology

* **如果条件中任意一个属性存在访问结构，使用该访问结构来访问该字段，检索对应记录** IF an access path exists (index) for an attribute, use it to retrieve the tuples satisfying the condition, e.g., Salary > 40000 [intermediate result]
  * **内存中存入中间结果** store these intermediate results in memory
  * :orange: **目标**：最小化中间结果集的数量，minimise the number of the intermediate result set
* **检查内存中中间结果是否满足别的条件** GO through this intermediate result to check which record satisfies also the other condition(s), e.g., Name LIKE ‘%Chris%’ in memory!

:orange: 如果所有属性都有索引，应该如何选择？If you have two indexes, which index is to be used first?

* **目标**：generate the smallest intermediate results so it fit in main memory, then go through the second condition to filter out irrelevant tuples to get a final outcome
  * **中间结果tuples越少越好** expect to get as few tuples as possible in the intermediate result set ---> 引出 **优化问题** the challenge of optimizaztion
  * 【use the index that generates the smallest intermediate result set hoping to fit in the memory! [selectivity = #tuples retrieved] 】

:orange: **优化** Optimization - <font color="red">如何找到查询条件的执行顺序，最小化预期成本</font>？ Optimization: find the execution sequence/order of conditions that minimizes
the expected cost

* **事先预测** Principle: Predict the <font color="red">selectivity（number of employees satisfying a condition）</font> beforehand!
  * selectivity of first & second condition
* **必须在执行查询之前完成**
  * 根据对各条件选择性的预测，制定出**执行查询时涉及的索引(选择什么索引能最小化预计成本**)的策略方案----查询优化 based on prediction of selectivity of each condition, create a strategic plan about which index to be involved to execute the query --- query optimization
* 假设通常不容易找到有特定名称的员工，所以我们先执行名称的查询，再筛出工资>40K的。
* 假设先使用 index检索所有有工资?40K的员工，假设此例筛出了所有员工（所有人的工资都>40K），此例就会导致中间结果为整个关系

# JOIN可选择的各执行算法：Strategy for JOIN

![](/static/2021-04-16-23-08-44.png)

Observation: `JOIN` is the most resource-consuming operator!

two-way equijoin，i.e., join two relations with equality ‘=’ (between foreign key & primary key)

* 等值连接，内连（默认？）

:orange: **5种执行策略** Five fundamental strategies for join processing:

* **Naïve join (no access path**)
  * cartesian product
* **nested-loop join (no access path**)
  * not built primary or secondary access path but more efficient than naive join
* Index-based nested-loop join (index; B+ Trees)
* Merge-join (which already sorted relations)
* Hash-join (which hashed relations)

:candy: 比较这些的成本来找到执行join语句的最佳决策

* 执行任何join语句前，都应该预测能匹配到的元组数 (selectivity)

## Naive JOIN

![](/static/2021-04-16-23-21-13.png)

1. **计算 R & S的笛卡尔乘积，，即，用R的所有tuples拼接S的所有tuples**. Compute the Cartesian product of R and S, i.e., all tuples from R are concatenated (combined) with all tuples from S
   1. 纯拼接，产生很多无意义元组，占据了很大的元组空间 meaningless tuples, generating a huge tuple space
2. **每次取一个拼接的元组，如果满足筛选条件，则将其存入结果集`T`** Store the result in a file `T` and for each concatenated tuple `t = (r, s)` with `r ∈R` and `s ∈S` check if `r.A = s.B`

:candy: **非常低效（baseline solution），通常结果集为笛卡尔乘积的子集** inefficient, typically the result is a small subset of the Cartesian

* <font color="red">假设最后无元组匹配（结果集为空），那么就浪费了过多开销来执行查询（拼接），因此需要进行JOIN选择性预测</font> What-If: no tuples are actually matched; predict the matching tuples in advance(join selectivity)!

---

Algorithm Naïve Join

* T = Cartesian R x S
* Scan T, a tuple t ∈T at a time: t = (r, s)
* If r.A = s.B then, add (r, s) to the result file
* Go to next tuple t ∈ T.

## NESTED-LOOP JOIN

注意，实际情况中，不会访问tuple，只访问块（因为块为最小操作单元， block mode）the outer & inner loops are over blocks and not over tuples!

![](/static/2021-04-16-23-32-21.png)

:candy: **Challenge 1 - 内外循环的关系选择，，如何能最小化JOIN操作成本**？ Which relation should be in the outer loop and which in the inner loop to minimize the join processing cost?

### 具体细节：Algorithm

![](/static/2021-04-17-12-58-18.png)

* 每次从外关系`R`中加载 一组块 LOAD a set(chunk) of blocks from the outer relation `R`
* 针对chunk中每个`R`数据块，加载`S`关系的一块
  * 进行比较，如满足条件 `r.A=s.B`, 将结果存入buffer
    * <font color="red">如果buffer满了，暂停操作，将buffer从主存写回磁盘再继续</font> if the buffer is full, pause the algorithm and write the output buffer back to the main disk then resume
  * <font color="deeppink">R chunk中每一块都与当前 `S`块比较完之后，加载下一块 `S`</font>
* **如果当前加载的 R chunk 与所有的 `S`块比较完成了，继续加载下一个 R chunk**
  * 并重复比较操作

## INDEX-BASED NESTED-LOOP JOIN

假设A，B两属性只有一个有index， `R.A=S.B`

* 假设使用 关系`S`-`B`属性的索引 `I`

![](/static/2021-04-17-13-34-31.png)

基于索引的嵌套循环JOIN

* 每次 `R`中取一个tuple
  * 使用 关系S的`B`属性索引`I`检索值， `I(r.A)` 检索元组满足条件，`s∈S, s.B=r.A`
  * 针对满足条件的元组 `s∈S`，**将匹配元组 `(r,s)`添加至结果集**

:candy: **为什么 index-based nested loop比 nested-loop快**？ why much faster compared to the nested-loop join?

* 因为能通过S关系 B属性索引`I`直接找到 与`r.A`值相同，且满足 `s∈S, s.B=r.A`的元组，**避免线性搜索** we get immediate access on s∈S with s.B=r.A by searching for r.A using the index I, avoiding linear search on S
  * 前例，针对每个`R`（chunk）中的每个块，，都需要线性扫描 `S`关系的tuple，比较是否满足筛选条件

---

:candy: 如果两个属性都存在Index，那么使用哪个才能最小化JOIN操作成本？Challenge -  Which index to use to minimize the join processing cost?  --- Optimization problem

## SORT-MERGE JOIN （需要有序）

前提：两个relation都根据Join属性排序，以用于sort-merge JOIN方法
Pre-condition: Relations R and S are physically ordered on their joining A and B;

* Use of the merge-sort algorithm over two sorted relations w.r.t. their joining attributes.

![](/static/2021-04-17-15-07-18.png)

:orange: Methodology

* **每次内存中加载一对有序块 `{R.block, S.block}`**  Load a pair {R.block, S.block} of sorted blocks into the memory;
* **基于JOIN属性，指针同时线性扫描两个块内部** Both blocks are linearly scanned concurrently over the joining attributes (sort-merge algorithm in memory);
* **如果元组匹配，则存入缓冲** If matching tuples found then store them in a buffer.

:candy: **每个文件的块仅被扫描一次** The blocks of each file are scanned only once!

* 前提是，`R`关系 & `S`关系需要先基于JOIN属性排序 ，否则需要外部排序，成本很高  If R and S are not a-priori physically ordered on A and B then sort them first!

### Example

要双指针交替扫描

![](/static/2021-04-17-15-12-22.png)

处理完一个块后，加载另一对块，进行相同操作

* **最终每个relation只被扫描一次**

## HASH-JOIN

利用两个关系的JOIN属性应用相同散列函数

![](/static/2021-04-17-15-22-25.png)

:orange: 前提 precondition

* **给定基于JOIN属性的散列函数，将文件R基于其属性A分至M个桶内** File R is partitioned into M buckets w.r.t. hash function h over joining attribute A
* **给定基于JOIN属性的散列函数（相同散列函数），将文件S基于其属性B分至M个桶内** File S is also partitioned into M buckets w.r.t. the same hash function h over attribute B
* <font color="deeppink">假设`R`是可容纳近主存的最小文件，即`M`个桶可容纳至主存</font>Assumption: R is the smallest file and fits into main memory: M buckets of R are in memory
  * 如果不能容纳，那需要外部哈希方式（later）

---

:orange: Algo - Hash-JOin

* **partition phase**
  * R中每个tuple的属性A都用散列函数计算出桶索引/地址（内存中） `y=h(r.A)` 
  * 将tuple `r`放入内存中地址
* **probing phase**
  * S中每个tuple的属性B，用相同散列函数计算桶索引/地址 `y=h(s.B)`
  * <font color="red">针对该桶索引`y=h(s.B)` 中的每个tuple `r∈R`，进行匹配，如果 满足`s.B=r.A`，则添加 `(r,s)`至结果集【JOIN】</font>
* **原理**：<font color="deeppink">如果不同两个关系中的tuple能映射到同一个桶里，（相同散列函数），那么增加了这两个tuple匹配的概率
</font>

---

![](/static/2021-04-17-15-58-26.png)

* probing phase
  * 要一个input buffer来散列S关系的每个块，每次从这个块中取一个tuple
  * **还需要一个output buffer存储匹配结果，满了写回磁盘** when the output buffer is full, pause the algorithm, write down the output buffer from the main memory to the disk then resume

## 总结：So Far

![](/static/2021-04-17-16-14-13.png)

* **Naive Join**
  * 不使用任何访问结构，单纯笛卡尔乘积，，几乎不会使用该方法
  * Naïve Join: Exploit nothing. Cartesian product and then check…
* **Nested-Loop**: Exploit nothing. Computing-oriented join.
  * 不使用任何访问结构
  * 需要决定外循环关系是什么，这会影响JOIN操作成本 Which relation should be in the outer loop? Influences the join cost.
    * **Optimization problem** --- predict beforehand(before execute the join query), join selectivity
* **Index-based Nested-Loop**
  * **至少利用一个索引。利用索引尽快找到匹配的元组**。 Exploit at least one index. Use index to find the matched tuples as quick as possible
  * **Optimization problem** -- 当两个JOIN属性都存在索引时，基于哪个索引的使用会影响JOIN操作成本 If we have two indexes (over R.A and over S.B), which one to use? Influences the join cost! Optimization…
* **Merge-Join**
  * **当两个关系都基于JOIN属性有序时，利用该方法非常高效** if both relations are ordered with respect to the joining attributes, it's nice to use the merge-join as it's very efficient
  * 否则，首先需要排序他们才能利用该方法 otherwise, if one or both of the lations are not sorted we have to sort them first before proceeding with this method
* **Hash-Join**
  * hash one or two relations, then about to find matching tuples over the same bucket
  * precondition: 1 or 2 relations being hashed with respect to the join attributes 【前提，>=1个关系根据JOIN属性哈希】

:orange: 问题

* 执行任何JOIN算法前，预测JOIN成本（块访问），再选择哪个作为最佳策略方法用来执行最终query
  * how to estimate the expected cost for each of these joining algorithms

# 嵌套循环JOIN成本预测：Nested Loop Join Cost Prediction

![](/static/2021-04-17-16-58-01.png)

* 每次从 `nE`中读取多个块（chunk of blocks）
* 每次从 `nD`中读取一个块，与 chunk中每个块（E）进行匹配
  * 如能匹配，则结果写入buffer，
  * 如buffer满，暂停algo，将buffer写回外存，再继续操作

:orange: 主存 （能容纳`nB`个块）

* **1个缓冲（块）用来每次从内关系中读取一块** in main memory need 1 buffer(block) to load 1 block at a time from the inner relation - 1 block for reading the inner file D
  * 用于与外关系chunk中每块做匹配
* **1个缓冲（块）写回JOIN结果** need 1 block(buffer) to write the join results - 1 block for writing the join result
  * 暂存匹配的结果集
* **剩余块（`nB-2`个块）用来读取外关系的chunk（一组块），即 `nB-2` = Chunk size** rest of the blocks used to transfer a chunk of blocks from the outer relation from the disk to the main memory
  * `nB-2` blocks for reading the outer file `E`: **chunk size.**
  * 内存一共容纳nB个块，需要一个**inner buffer来读内关系的每个块**，一个output buffer来写入结果，，所以剩余 nB-2个块来存外关系的块 （1 chunk）

---

:orange: 已知 Observation

![](/static/2021-04-17-17-15-28.png)

* **外关系`E`的所有块只加载一次** each block of the outer relation `E` is read once
* **针对外关系`E`的每个chunk（每次读`nB-2`个块），内关系`D`所有块都加载一次** the whole inner relation `D` is read every time we read a chunk of `nB-2` blocks of E
  * 每nB-2个块 （每个chunk），，最后都相当于他加载了一次内关系D的所有块，，（每次inner buffer从D关系中加载一个块，，但最后内关系D所有块都被加载一次）
* <font color="red">注意，最开始的时候整个外关系需要被加载一次，，算成本</font>

## 成本决策归纳 & 决策例子

![](/static/2021-04-17-17-29-08.png)

* 外关系`E`需要加载的块数 `nE` Total number of blocks read for outer relation E: nE
* **外循环次数（加载的chunk个数）**：`ceil(nE/(nB-2))`
  * 已知需要 inner, output buffer用来处理内关系块 & 结果集，，所以还剩 `nB-2`个块用于每次装入多个外关系的块（1 chunk）
* 每个外关系的chunk `nB-2`个块，最后都相当于加载一次内关系 `D`的所有块 for each chunk of `nB-2` blocks read all the blocks of inner relation `D`
* 因此，所有外循环读取的块总数 = `nD*ceil(nE/(nB-2))` total number of block read in all outer loops
* **该方法的预计成本** total expected cost
  * <font color="red">`nE + nD * ceil(nE/ (nB-2))` block accesses</font>

---

:candy: 例子

![](/static/2021-04-17-17-30-39.png)

* 内外关系的选择会影响JOIN操作成本
* 因此，**处理嵌套循环JOIN时，，将块数更少的作为外关系** when deal with nested loop join， always put the relation with fewer blocks in the outer loop as it gives us the minimum number of block access
  * The file with fewer blocks goes to the outer loop.

# 基于索引嵌套循环成本预测：INDEX-BASED NESTED-LOOP COST PREDICTION

![](/static/2021-05-01-20-40-06.png)

**Strategy 1** 每次从E中取一个tuple e，，利用关系**D的B+树**，找 e.SSN值(是否有匹配记录)，满足e.SSN=d.Mgr_ssn（如匹配）

:orange: 注意：<font color="red">大多数时间，搜索都是无意义的，因为不是所有员工都是manager，，员工为manager的概率为 50/6000=0.83%（因为有50个部门，即50个manager），即有99.16%的检索是无意义的</font>

:orange: 成本 Strategy Cost

* `nE + rE*(xD + 1)`
  * `nE` 加载整个 关系`E`
  * `rE*(xD+1)`
    * `rE`中所有tuples匹配检索所需成本，一共6000条职工记录
    * `xD` - B+树高，blocks to go down the tree
    * `1` - load actual block（leaf node 指向的物理块）

:orange: 例子1 - 使用关系D的索引

![](/static/2021-04-17-18-13-18.png)

* `4/10`个有意义检索
* `6/10`无意义

---

**Strategy 2**：针对关系D的每个tuple d，使用关系E的SSN属性的B+树判断是否存在匹配，`e.SSN=d.Mgr_ssn`，即树里找 是否存在key value = `e.SSN`

![](/static/2021-04-17-18-20-09.png)
![](/static/2021-04-17-18-19-59.png)

* **可知**：每个部门都有一个经理，所以，，检索是有意义的 every department has one manager –search is fruitful…
  * 概率：100% Probability a manager being an employee is 100%

:orange: 成本 strategy cost 2

* `nD + rD * (xE + 1) = 260` block accesses
  * `nD` 加载关系D所需成本
  * `rD * (xE + 1)` 与Strategy 1同理。。。

---

对比S1 ，S2成本，，

* Huge difference (20,000 vs 260 block accesses)
* S2 - 每个部门记录都能匹配一个员工记录 every record in Department is joined with exactly one record in Employee (manager)
* S1 - 只有部分员工记录能匹配部门记录 only some employees from Employee are managers of departments…

## 决策

<font color="red">如JOIN属性，都存在索引，选择使用基于PK的索引</font> Use the index built on the PK

:candy: <font color="deeppink">注意，不适用递归关系</font> not for recursive relationships, e.g., employee-supervisor

# SORT-MERGE-JOIN COST PREDICTION

前提：所有关系都基于JOIN属性有序，否则无法应用Merge algo

![](/static/2021-04-17-20-56-22.png)

* Efficient if both Employee E and Department D are already sorted by their joining attributes: SSN and Mgr_Ssn.
* **每次从两个不同关系中加载一对块，，同时在块内扫描，找出匹配元组** loading a pair of blocks at a time from 2 different relations and then concurrently scanning over these blocks in order to find matching tuples
* **处理完当前块后，加载下一对块** taking another pair of blocks

:orange: 成本 Strategy Cost

* `nE + nD = 2010` block accesses
* <font color="red">文件有序时，非常高效，因为每个关系只扫一次</font> very efficient as each relation only being scanned once

:orange: 但是，**当文件无序的时候，需要先排序（外部排序）** IF both files are not sorted THEN use external sorting!

![](/static/2021-04-17-20-56-38.png)
![](/static/2021-04-17-20-56-43.png)

* Strategy cost 1 - external sorting (2-way merge), sort E
  * `2nE + 2nE * log2(ceil(nE/nB))`
  * `ceil(nE/nB)` 初始有序子文件数量 (每个子文件大小，`nB`块) number of initial sorted sub-files (each sub-file is nB blocks)
  * `nB` 可用内存块数 number of available memory blocks
* Strategy cost 2 - external sorting (2-way merge), sort D
  * `2nD + 2nD * log2(ceil(nD/nB))`
* Total Strategy Cost
  * `nE + nD + 2nE + 2nE * log2(ceil(nE/nB)) + 2nD + 2nD * log2(ceil(nD/nB))`
    * `nE + nD` Sort-merge-join cost

---

## 无序例子

![](/static/2021-04-17-20-57-15.png)

* assume that both relations are not sorted with respect to their join attributes
* 只有2010次块访问属于 JOIN，，5.1%

## 决策

Lesson Learnt: Think before sort only for joining purposes!

* 如果关系无序，不要用这个JOIN algo来执行SQL
  * if these relations are not sorted then never use this join algorithm to join them together (join attributes)

# HASH-JOIN COST PREDICTION

假设，最好情况下，最小关系能容纳至主存，，（某关系分成M个桶后，M个桶能容纳至内存） assume with the best case scenario where the smallest relaiton can fit in the main memory (e.g. the number of blocks in Department relation))

* 注意还需要两个块用于 probing phase (input buffer, output buffer)
* Best case: Memory `nB >nD +2`
  * `nD` **最小关系**中的块数，，blocks for the smallest of the two relations (e.g. Department)

---

:orange: 步骤

![](/static/2021-05-02-12-54-17.png)

* **partition phase**
  * **根据特定散列函数，将【最小关系】分进M个桶（主存中，，）** hashing the smallest relation into M buckets, using the specific hash function
    * **每次加载一个block至input buffer进行散列映射**
  * Whole relation Department fits in memory and is hashed into M buckets.
  * Each Employee tuple is loaded and hashed on joining attribute SSN.
  * 最终，，整个关系`nE`被添加
* **probing pahse**
  * **加载另一个关系`nD`，应用相同散列函数，映射找到桶索引**
    * **每次加载一个block至input buffer进行散列映射，查找**
      * 在该桶内，查找是否存在匹配元组 The corresponding bucket is found and searched for a matching tuple.
  * 如，匹配，，结果写入 **output buffer** 。 The result is stored in another buffer (that’s why nB > nD + 2)
  * 最终，，整个关系`nD`被添加

:orange: Best Case 成本

* `nE + nD` block accesses
* 仅当最小文件能被容纳至内存时，高效

:orange: Normal Case

* <font color="red">最小关系无法装入主存，，此时必须应用外部哈希方法</font>，，most of the time, the smallest relation cannot fit in the main memory, have to deal with external hashing method (beyond the scope of the course)
* `3(nE + nD)` block accesses

# 所有JOIN算法成本- PUT-ALL-TOGETHER: JOIN COST PREDICTION

put together to predict the expected cost, based on the context and identify which is the methodology of minimizing the cost then executing the join query based on the methodology

![](/static/2021-04-17-21-20-49.png)
![](/static/2021-04-17-21-27-07.png)

* winner: index based nested loop join algo
  * built a B+ Tree over the PK attribute of the join relation,
* but 每次生成匹配元组后将他们存入内存缓冲，，缓冲满时需要暂停，，**还需要包括将缓冲写回外存的成本** the cost for writing the result-set buffer (block) from memory to disk in each strategy is not yet considered!
* **还需要明确，具体要写回多少个块**（因为JOIN是基于多个关系的,并不是考虑写，读单个块，，**也就是之后的JOIN选择性成本，，把中间结果集写回的块数**） have to identify how many blocks are about to be written
* **多少个元组会被匹配** how many tuples are about to be matched

# 例子-递归关系：基于索引嵌套循环Join

前略，，，一般使用基于PK的索引B+，，但是递归关系为例外

![](/static/2021-04-17-21-55-44.png)
![](/static/2021-04-17-21-57-42.png)

* 提出使用这2个索引的2种策略，并基于基于索引的嵌套循环加入算法，找到能使预期成本最小的策略 propose 2 strategies using these 2 indexes and find the one that will minimize the expected cost based on the index-based nested loop join algorithm

## 使用基于PK索引

证明，，直接使用基于PK的B+索引，，（递归关系）成本不低

* `PK -SSN`
* level x = 5

具体步骤

![](/static/2021-04-17-23-47-02.png)

* 先载入 关系 Employee
  * `b=2000`块访问
* 针对关系E的每一个块（每次加载一个块）
  * 每次选取 一个tuple e，，判断一下 e是不是supervisor，即 `Super_SSN=NULL`
  * 如果 e是supervisor， `Super_SSN=NULL`
    * 不使用索引，因为没有意义，10·%
  * 如果， e不是 supervisor，（概率，w.p 90%）
    * 90%的概率要使用 索引
    * 使用 `SSN`索引， 查找是否存在匹配`e.super_SSN`的值
  * 读取下一个tuple

:orange: 注意 需要访问关系`E`的所有块，

* `b + 0.9 * r * (x + 1) = 56000`
  * 0.9用来使用B+索引，查找特定员工的supervisor
  * `x` - cost of going down the tree
  * `1` - load actual block
  * `r` - 关系E的记录数

## 使用基于FK的索引

使用基于FK `Super_SSN`的B+索引

具体步骤

![](/static/2021-04-17-23-48-11.png)

* 先加载整个关系E，`b=2000` block accesss
* 针对关系E中每个块，，
  * 每次取一个tuple e，判断是否为supervisor `Super_SSN=NULL`
  * 如果 e不为supervisor，FK有值，90%概率
    * 使用索引 super-index ，查是否有匹配`e.super_ssn`
  * 否则，e为 supervisor， `Super_SSN=NULL`
    * 不适用索引，直接取下一个tuple

成本

* `b+0.9*r*(y+1) = 29000 (48% faster)`
  * 0.9概率，e不为supervisor

## Decision

对比PK，FK成本，，

* FK成本更小，使用 FK的B+索引