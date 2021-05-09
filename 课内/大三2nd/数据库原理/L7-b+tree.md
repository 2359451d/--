# Lecture 7 - Indexing Methodology Part 2

![](/static/2021-03-30-19-47-52.png)

* 多级索引实现
  * B Tree
  * B+ Tree
* 二级索引 （dense， secondary index on non-ordering key field）
* 权衡 效率 & 开销
  * Primary Access Path (3 file types)
  * Secondary index
  * B+ Tree (multi-level indexing)

---

* [Lecture 7 - Indexing Methodology Part 2](#lecture-7---indexing-methodology-part-2)
* [MultiLevel Index](#multilevel-index)
* [多级索引->Tree](#多级索引-tree)
  * [局限性：Limitation](#局限性limitation)
* [B Tree: Index on Non-ordering key](#b-tree-index-on-non-ordering-key)
  * [例子1 - 检索](#例子1---检索)
  * [例子2](#例子2)
    * [例子-索引文件大小 & 索引包含块数](#例子-索引文件大小--索引包含块数)
* [最大化扇出&最小化存储开销：Maximize fan-out & minimize storage](#最大化扇出最小化存储开销maximize-fan-out--minimize-storage)
* [B+树-非排序键索引：B+ tree - index on non-ordering key](#b树-非排序键索引b-tree---index-on-non-ordering-key)
* [B+ Tree：Internal Nodes](#b-treeinternal-nodes)
* [B+ Tree: Leaf Nodes](#b-tree-leaf-nodes)
* [B+ Tree Example](#b-tree-example)
* [B Tree vs B+ Tree](#b-tree-vs-b-tree)
  * [Range query：B/B+ tree accessing pattern](#range-querybb-tree-accessing-pattern)
* [B+加快检索证明例子](#b加快检索证明例子)
  * [B+树节点](#b树节点)
  * [B树节点](#b树节点-1)
* [Example:3 level B+ tree vs B tree](#example3-level-b-tree-vs-b-tree)
* [例子-是否建立B+树：Decision Making on B+ Tree Use](#例子-是否建立b树decision-making-on-b-tree-use)
  * [B+树层数t](#b树层数t)
  * [给定范围比α，表示B+所需预计成本](#给定范围比α表示b所需预计成本)
  * [比较B+ & 线性搜索成本](#比较b--线性搜索成本)

# MultiLevel Index

![](/static/2021-03-30-19-54-57.png)

* `t` level索引成本
  * `t+1` block accesses

---

需要引入B/B+ Tree解决以下问题

:candy: 多级索引 插入删除更新成本很高 Insertions, deletions, and updates are costly! [*]

* 因为每层索引文件本质是有序文件，结构种任何增删改都会增加维护成本 all encapsulated indexes are physically ordered files. Hence, all updates should be reflected to all levels

:candy: 难以建立动态（动态扩展，调整） 多级索引 define dynamic multi-level indexes

* adjust to deletions, insertions of records
* expand and shrink following the distribution of the index values
* self-balancing (sub-trees of same depth)

# 多级索引->Tree

![](/static/2021-03-30-20-08-45.png)
![](/static/2021-03-30-20-08-58.png)

* `t=2`，2级索引（secondary index,）
  * L2 - 二级索引项，指针指向两个聚簇
  * L1 - 聚簇 （注意每个聚簇种，如果块装不下记录指针，采用链式连接）
    * 存放记录指针，指向每条对应记录
  * L0 - Data FIle(non ordering in terms of the index field)

## 局限性：Limitation

![](/static/2021-03-30-20-34-05.png)

* 直接转换会造成树不平衡
  * 不根据键值分布调整，每层叶结点不同
  * Worst case - 退化成链表
  * 深度 `t` -> 高成本搜索时间 `O(t)`

---

:candy: 确保任何操作后，树都为平衡的 balanced

:candy: 需要最小化树深度 `t`（即，访问成本）

* 当键值插入满节点？
  * 避免生成新的一层，可以将同层节点一分为二 **split**
* 节点键值被删除？
  * 避免删除某层，可以合并某些同层节点 **merge**

# B Tree: Index on Non-ordering key

B树索引确保所有叶结点都处于同一层 ensures that all the leaf nodes of the same level

---

:orange: B-Tree Node order `p`

* **每次将搜索域划分为`p`个搜索子域（子树）** each time can splits the searching space up to `p` subspaces
  * `p>2`

![](/static/2021-03-30-20-49-41.png)
![](/static/2021-03-30-21-06-27.png)

:orange: 每个节点 `pi`

* block/data pointer `Qi` - 指向物理块，存储了对应 key value 记录的物理块
* **key value `Ki`**
  * 树中，<font color="red">节点按key value排序，</font>
  * 即，<font color="deeppink">可以通过二分查找，快速定位某key value</font>
* `Pi+1` 数指针 Tree Pointer，用于树中遍历
  * moving from one level to another level

## 例子1 - 检索

`p=3`，每次搜索子域划分为`3`,each node

* `2` block/data pointers
* `2` key values
* `3` tree pointers

![](/static/2021-03-30-21-23-23.png)

* 利用树指针，树层级之间移动

## 例子2

针对non ordering key field非排序键字段上建立3级树索引(secondary index) 3-level B tree index，order `p=23`

* 满足仅69%的树点为满（不是所有树结点都满，使用所有树节点空间）
  * **平均扇出（Average fan-out）**：搜索子域（用子树表示）每次划分为 `0.69p=0.69*23=16`个
  * 平均，每个树节点可容纳 `0.69p=16 tree pointers/ 15 key values/ 15 data(block) pointers`

![](/static/2021-03-30-21-58-28.png)

* L3停止，已满足， null （tree）pointers

---

总共能存储的键值数 = 能存储的索引数 = 能指向的物理块数量（data/block pointers数量）

* 每层键值数量总和

![](/static/2021-03-30-22-08-32.png)

---

:candy: 新的一个key引入，，已经超出当前3层索引能够容纳的data pointer & key values,

![](/static/2021-04-02-21-08-01.png)

* 需要引入新的一层，， level 4 (1million keys)，但仅使用该层的其中一个 key value空间，**造成过多未使用空间（93.3%）**
* 需要考虑最优 `p`值（order）
  * 过大，，加快搜索过程，但扩展性可能差（造成过多未使用空间）
  * 过小，可能增加树的深度，增加访问成本

---

### 例子-索引文件大小 & 索引包含块数

![](/static/2021-04-02-21-17-24.png)

* 注意B树最后一层没有树指针！！！所以是16+256+4096

# 最大化扇出&最小化存储开销：Maximize fan-out & minimize storage

![](/static/2021-04-03-16-13-06.png)

* B树需要存储过多元数据，需要引入其他结构最大化扇出（order `p`） & 最小化存储成本

1. 最大利用存储开销（storage efficient, free up space from the nodes）
2. 最大化搜索速度（maximize the fan-out [splitting factor]  order `p`）

:orange: node order `p` 是某树结点能存储的 tree pointers 数量 （number of tree-pointers per node）

* 将无关的元数据（data pointer）移出 tree pointers,这样能引入更多的tree pointers (增大了 node order p [fan-out]) - maximize the number of tree-pointers per node to maximize fan-out
  * maximize the blocking factor by squeezing more tree-pointers
* <font color="red">思考：无关数据移出树节点后存放位置？ --- 引出B+ tree</font>

# B+树-非排序键索引：B+ tree - index on non-ordering key

![](/static/2021-04-03-16-23-19.png)

:orange: B+ tree 节点类型

* Internal Nodes
  * guide the searching process
* Leaf Nodes
  * point to actual data blocks

---

:orange: 节点存储Principle

![](/static/2021-04-03-18-13-03.png)

* **Internal nodes不存储数据/块节点 （最大化扇出**） internal nodes have no data-pointers to maximize fan-out
* **Leaf nodes存储数据指针** Only Leaf nodes hold the actual data-pointers
* **Leaf nodes 有序存储所有键值 & 对应的数据/块指针** Leaf nodes hold all the key values sorted and their corresponding data-pointers
* **某些键值会被复制进internal nodes用于 导向/加速搜索步骤** some key values are replicated in the Internal Nodes to guide & expedite the search process
  * **有序 sorted**
  * **这些键值对应子树中间值** corresponding to medians of key values in sub-trees

# B+ Tree：Internal Nodes

![](/static/2021-04-03-18-32-55.png)

# B+ Tree: Leaf Nodes

![](/static/2021-04-03-18-44-09.png)

* 从leaf nodes level（包含所有 key values）选取每个子树的medians作为internal nodes 的key values(guid & expedite the searching process)

# B+ Tree Example

利用internal nodes最快定位一个leaf node（存放对应key value的），然后根据对应的块/数据指针 访问实际物理块

* 注意访问某记录存在的数据块，需要遍历B+树每层，（速度比B树快）

---

![](/static/2021-04-03-18-53-52.png)

* internal node
  * `p=3`，
    * 最多3个tree pointers
    * 最多2个key values
* leaf node
  * `pL=2`
    * 最多2个 {键值，data pointers}对

---

:orange: 例子，访问 record value =  7

加载root节点，，7>5 中间子树，7=7沿左边树结点进入左子树（下一层）
在叶结点内部搜索key value=7， 最后通过data/block pointer 访问实际物理块

# B Tree vs B+ Tree

![](/static/2021-04-03-19-52-55.png)

* 因为B+树，平衡，需要**go down the whole tree** to access the actual block,因此成本可预计
  * 而B树未知，所以**通常在root注入 the most popular searching values** in order to avoid going down the whole tree to avoid retreiving many blocks

---

## Range query：B/B+ tree accessing pattern

注意：因为相当于 secondary index（**非排序**）,,所以原本的data file根据SSN无序，，**无法利用连续加载相邻数据块的特性**

![](/static/2021-04-03-22-53-07.png)

* B tree
  * 得上下访问每个在范围内的节点，，低效
* B+ Tree
  * go down the tree -> find the first leaf node where can have the employee with SSN=3
  * retreive sibling leaf nodes until finding the last leaf node that has the employee with SSN=10
  * 每次匹配下一个叶结点时，同时访问相应的数据块，**省去存储父节点的开销 & 省去再返回上层的开销**

# B+加快检索证明例子

![](/static/2021-04-03-23-06-14.png)

证明假设：将internal nodes中的data pointers全部移除后，扇出增加，能引入更多 index entries，加快搜索步骤

计算：**最大 order `p`** （maximum fan-out）,使得 B树 & B+树每个节点能容纳于一个块中，each node fitting 1 block

* `B=512B`
* `V=9B`
* `Q=7B`
* `P=6B`
* B+
  * internal, order `p`
    * `p` tree pointers
    * `p-1` key values
* B
  * node order `p`
    * `p` tree pointers
    * `p-1` data pointers & key values

---

## B+树节点

Internal node order `p`

![](/static/2021-04-03-23-22-03.png)

leaf node order `pL`

![](/static/2021-04-03-23-23-09.png)

* B+叶结点总大小
  * `pL`个data pointers - `Q`
  * `pL`个key values - `V`
  * `1`个 next tree pointer  - `P`

## B树节点

![](/static/2021-04-03-23-38-03.png)

* B树节点总大小
  * `p-1`个data pointers
  * `p-1`个key values
  * `p`个tree pointers

23<34，B+树有更高扇出

# Example:3 level B+ tree vs B tree

![](/static/2021-04-03-23-47-48.png)

建立 3-level B+ Tree index

* `3` internal levels
* `1` leaf level
* internal node order`p=34`
* leaf node order `pL=31`

假设每个 internal & leaf tree node is **69% full**

比较同样 3-level B Tree下的存储开销

---

3-level B+ tree visualisation

![](/static/2021-04-04-00-02-04.png)

:orange: 注意，能够存储的键值数 = 叶结点键值数 （即，能够有的索引项数）

![](/static/2021-04-04-00-11-17.png)

---

:candy: 因此，可以通过增加扇出，从而增加能映射的索引项个数，但是需要更多的存储开销（存储更多节点，每个对应1块）

* 因此需要，找到最优 node order，权衡维护，存储，预期成本 find the best node order in order to trade-off the maintainance, storage, complexity, expected cost

# 例子-是否建立B+树：Decision Making on B+ Tree Use

whether or not to build the B+ tree

* if not ,use primary access path

---

secondary index之上，，利用B+树结构来建立 `t`层 二级索引(secondary index of level `t`, `t>1`)

![](/static/2021-04-04-00-29-02.png)

* index field - non-ordering key `SSN`
* `SSN` range - `{1，2，...,1250}`

---

SQL aggregation语句 - range query

![](/static/2021-04-04-00-29-02.png)

* `a` 为检索到的所有记录所占比， ratio of number of employees that retrieved (between the upper & lower bound)
* 比例表示 range ratio expressed as function
  * `(U-L)/n`
    * 比例范围 `[1/n, 1]`
      * `1/n` 最小为1条记录的所占比
      * 非严格范围，因为 `WHERE`条件为 `>= & <=`
* <font color="red">该比率用于判断是否建立B+树，或只使用 primary access path</font> range ratio give us a decision making rule on whether to use the B+ Tree or not
  * 注意，因为 index field `SSN` 为 non-ordering field，**所以如果不利用索引，可能需要扫描整个文件**

---

:orange: B+ 树 层数 `t`是什么？

:orange: 最大range ratio `a`需要为多少，才能避免线性搜索？

## B+树层数t

Recall, SSN(key) values -> `{1,2,...,1250}`

![](/static/2021-04-04-00-41-19.png)

* internal node order `p=5`
  * root - 1 node, 5 tree pointers
  * L1 - 5 nodes, 5*5=25 tree pointers
  * L2 - 25 nodes, 25*5=125 leaf node pointers
* Leaf level, node order `pL=q=10`
  * 125 nodes, `125 *10=1250` data pointers & key values
  * stop
* hence, `t=4` levels???
  * 不要混淆之前 3层索引，，3层internal + 1层leaf（没用t表示）

---

## 给定范围比α，表示B+所需预计成本

注意，处理范围查询

* 首先定位lower bound记录所在块

Recall，

* 索引一共`t`层 （即，预计成本=`t`）
* internal order `p=5`
* leaf order `pL=q=10`
* 范围查询中，需要检索的记录总数
  * `U-L`

:orange: 成本 （用range ratio `α`表示）

![](/static/2021-04-04-15-46-55.png)

* `C= t + q +（[αn / p] -1）+（[αn / p] -1）* q`
  * `t=4`，，找到lower bound需要的成本，，go down the tree till the leaf level
  * `q=10`，，加载含有 lower bound value的叶结点的所有 data pointers
* <font color="deeppink">之后，通过next tree pointer, `pNext`访问兄弟节点（sibling），直到upper bound停止</font>
  * <font color="red">因此需要知道，需要访问多少个 sibling nodes</font>
  * 范围查询中，需要检索的记录总数`U-L`
    * <font color="purple">因此，一共需要 `U-L / p`个叶结点来存储这些key values</font>
    * <font color="red">因为此之前已经加载了第一个leaf node(包含lower bound的)，因此，还需要`[U-L / p] - 1`个兄弟节点</font>
* 因为 range ratio `α= U-L / n`
  * 带入 需要的兄弟节点数 `[U-L / p] - 1`，<font color="red">得到 `[αn / p] -1`</font>
* 每次访问兄弟叶节点，也需要访问每个叶节点其中的每个data blocks
  * `（[αn / p] -1）* q`

![](/static/2021-04-04-15-46-55.png)

* 然后得到用range ratio表示的成本公式，，用来判断是否建立B+树，，如 < primary access path cost则可以建立

## 比较B+ & 线性搜索成本

注意原数据文件对于 SSN是无序的，所以primary access path情况下需要线性搜索 - 成本 `b`

![](/static/2021-04-04-16-10-10.png)

