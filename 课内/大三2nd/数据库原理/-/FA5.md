# Problem 1: 主索引建立决策

根据定理，决定是否创建 主索引 或 使用原访问方式

![](/static/2021-04-04-23-35-38.png)

* ordering key - SSN

**定理：如果且仅当文件块可以容纳至少两个主索引条目时，主索引被创建，而这两个主索引条目与关系中的元组数量无关**。 Theorem: A Primary Index is created if and only if the file block can accommodate at least two primary index entries independently of the number of tuples in the relation

* 假设 `b`个块，
  * 二分查找成本 `log b`
* 假设建立主索引
  * 索引块数量`m<b`
  * 二分查找定位索引项成本 `log m` + 根据索引项块指针访问物理块成本 `1`
    * `log m + 1`

---

* `b` - 文件总块数
* `r` - 记录数
* `B` - 块大小
* `R` - 记录大小
* `R'` - 索引项大小（key value + 指针大小）

---

:orange: 当且仅当以下成立(索引成本<有序文件成本)，建立主索引

`log m + 1` < `log b`

* `log m + log 2` < `log b`
* `log 2m` < `log b`
* `m < b/2`

且

* 主索引块数`m = b/bfri`
* 带入不等式 `b/bfri < b/2`
  * `bfri > 2`
    * `B/R' > 2`
      * `B > 2R'`, 则当前块大小至少能存储2条索引项时，建立主索引

# Problem 2

SQL查询 `SELECT DISTINCT(Salary) FROM EMPLOYEE`

* `SALARY` non key

---

2.1 - 伪码实现 DISTINCT 操作，，关系 `EMPLOYEE`根据 `SALARY` hashed，假设散列函数已知

* 假设散列函数 `h`，根据非键字段 `SALARY`哈希原文件

```txt
Algo
Repeat until the end of file
    Get the value of attribute Salary from the next tuple
    Hash the Salary value to get the memory-bucket - h(Salary)
    Search in the memory-bucket h(Salary) and check if Salary already exists
    If Salary does not exist (in the memory-bucket), then store Salary in the bucket and print out the value of Salary
End
```

2.2 - 如果文件有 `b`个块， 基于2.1实现的预计成本？

* `Based on the proposed algorithm, the relation is scanned once, thus, b block accesses`

# Problem 3：B+ max t level，建立决策

需要知道到底一共存储多少个key values(对应多少条记录)，得在哪层停止

* 求，max tree level `t`
  * **决定是否建立B+树**，或使用线性搜索
    * 需要建立成本不等式，进行决策

---

建立 非排序键字段 `UK POST CODE`的二级索引B+数

* **字段大小 `10B`**
* **块大小 `512B`**
* **记录大小 `10B`**
  * 关系仅包含post code字段，无其他任何字段
* B+叶结点对应 `1`个块
  * 每个叶结点 `90%`满，平均
  * `s=100`个块
* number of leaf node pointers `p=11`
  * next pointers + data pointers
  * -> data pointers , leaf node order `p=11-1`

---

注意：

* 每1块容纳一个叶结点
  * 但平均每个叶结点 `90%`full
    * `0.9*(p-1)`
      * 注意此例 `p`为number of pointers of leaf(Pnext + data pointers)
* 叶结点层的键值总数 = 关系中总 post codes记录数
  * `0.9*（p-1）*s = 0.9*10*100=900values`
    * `s=100`块
* **（B+树 & 文件）一共要存储900个key values**
  * 每个key values占 `10B`
  * `bfr=floor(512/10) = 51 values per block`
  * **存储900个key values需要的文件块数 `ceil(900/51)=18 blocks`**
* 因此，文件需要 `18`块， B+树需要 `s=100`块
  * 大部分都用于存储 元数据 meta-data
  * **线性搜索成本** `9` 块
  * **B+树成本** `t+1`
    * `t`树层数
    * `1`加载data pointer指向的块
* 当且仅当成本 `t+1<9`时，才建立 B+树
  * `t<8`
  * 因此最大层数 `t=7`（最坏情况），其他情况采用线性搜索

# P4：二级索引-非排序非键字段

注意：

* 索引项大小
  * 指针大小 + 键值大小 = `10B+50B=60B`
* 索引块因子
  * `floor(128B/60B)=2`
* 记录指针大小 = 10B
* 聚簇块因子
  * 1,2,3,4
* 记录大小 = 100B
* 块大小 = 128B

---

某distinct value查询所占比 * 其预期成本

* 特定distinct value成本 = 查secondary index成本 + 加载cluster中所有块所需成本 + 加载cluster中所有块的所有**记录指针**对应的块，所需成本

---

给定 `r=100000`

* 每个字段大小，得到每条记录大小
  * `50+10+30+10=100B`

文件按 `SSN`排序

* **每个地址/记录指针(address/record pointer)大小 = 10B**
* 块大小 `B=128B`

根据 `PN`字段的统计量（此句可推断`PN`字段为non ordering, non key field）

* 唯一值 `PN={1,2,3,4}`
* 分布
  * `1`，`10%`
  * `2`，`10%`
  * `3`，`10%`
  * `4`，`70%`

SQL `SELECT SSN FROM EMPLOYEE WHERE PN = x`

* 考虑是否针对`PN`非排序非键字段建立二级索引？（即，需要secondary index vs linear search建立不等式）

---

:candy: 注意非排序非键字段的secondary index

* 每个 distinct value对应一个索引项
  * 索引项指针指向对应聚簇
  * 每个聚簇包含多个块，每个块包含多个记录指针`f`

---

记录大小 `R=50+10+30+10=100B`

* 文件bfr `floor(128/100)=1 record/block`
* 文件块总数 `b=ceil(100000/1)=100000 blocks`

索引项大小 `R'=50+10=60B`

* 索引个数 `4` （PN唯一值）
* 索引bfr `floor(128/60)=2 index entreis/block`
* 索引块总数 `ib=ceil(4/2) = 2 blocks`

:orange: 每个cluster中包含的指针数 （每个指针对应一条记录）

* `PN={1，2，3}`
  * 指针数：`0.1*100000=10000` pointers for pointing records `PN={1,2,3}`
  * 聚簇块因子 `floor(128/10)=12 pointers/block`
  * 存储指针需要的聚簇块 总数 `ceil(10000/12) = 834` blocks for cluster 1,2,3
* `PN=4`
  * 指针数 `0.7*100000=70000` pointers for pointing records `PN=4`
  * 聚簇块因子 `floor(128/10)=12 pointers/block`
  * 存储指针需要的聚簇块数 `ceil(70000/12)=5834` blocks for cluster 4

预期成本 = P(PN=1) *  cluster1 + P(PN=2) * cluster 2 + P(PN=3) * cluster 3 + P(PN=4) * cluster 4

* Recall：特定distinct value成本 = 查secondary index成本 + 加载cluster中所有块所需成本 + 加载cluster中所有块的所有**记录指针**对应的块，所需成本
  * 查secondary index 成本 `log 2=1`
  * 加载cluster1，2，3成本 （聚簇中总块数） = 834
    * 加载cluster1，2，3所有块中所有指针 = 834*12
  * 加载cluster4 成本 = 5834
    * 加载cluster4所有块中所有指针 = 5834*12
* 即，
  * `3*0.1（1+834+834*12）+0.7（1+5834+5834*12）= 56,343 block accesses`
  * 而线性搜索= `100000 block accesses`
* speed-up gain `56343/100000=56.4%`
* storage overhead `2+3*834+5834=8338 blocks more`

![](/static/2021-04-05-17-19-44.png)