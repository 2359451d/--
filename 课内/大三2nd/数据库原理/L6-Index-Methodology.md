# Lecture 6: Indexing Methodology: Part I

![](/static/2021-03-25-03-29-46.png)

index（任意） - 访问记录的一种方法

* Primary Index, Clustering Index, Secondary Index
  * 每个索引方式，**plot Trade-off**: 查询速度 vs 开销（index结构存储开销，维护开销）Search Speed vs Overhead (Storage, Maintenance)
* 关注：如何将搜索子域划分为更多子域 m>3
  * B+ tree, B Tree - multi level index data structure

* [Lecture 6: Indexing Methodology: Part I](#lecture-6-indexing-methodology-part-i)
* [索引设计目的：Objectives](#索引设计目的objectives)
* [开销vs效率 TRADE OFF PLOT: OVERHEAD VS SEARCH SPEED](#开销vs效率-trade-off-plot-overhead-vs-search-speed)
* [索引建立原则：Principles](#索引建立原则principles)
* [索引文件比数据文件占用的块少: Index file occupies less blocks than the data-file](#索引文件比数据文件占用的块少-index-file-occupies-less-blocks-than-the-data-file)
  * [两种索引类型：密集/分散](#两种索引类型密集分散)
* [通过索引搜索比通过文件搜索更快: Searching over index is faster than over file](#通过索引搜索比通过文件搜索更快-searching-over-index-is-faster-than-over-file)
* [3种索引类型](#3种索引类型)
  * [Primary Index](#primary-index)
    * [example](#example)
    * [有序文件主索引效率：例子](#有序文件主索引效率例子)
  * [CLUSTERING INDEX](#clustering-index)
    * [Clustering Analysis](#clustering-analysis)
    * [是否建立聚簇索引 & 无穷大唯一值时线性搜索成本：Decision Making](#是否建立聚簇索引--无穷大唯一值时线性搜索成本decision-making)
    * [聚簇索引开销&效率图：Tradeoff overhead vs speed](#聚簇索引开销效率图tradeoff-overhead-vs-speed)
  * [Secondary Index](#secondary-index)
    * [例子 - dense(non ordering key field)](#例子---densenon-ordering-key-field)
    * [Sparse - non ordering non key field](#sparse---non-ordering-non-key-field)
    * [例子 - sparse non ordering non key field](#例子---sparse-non-ordering-non-key-field)
* [MultiLevel Index](#multilevel-index)
  * [例子](#例子)
  * [t值 & m(fan-out, bfr)](#t值--mfan-out-bfr)
  * [例子](#例子-1)

# 索引设计目的：Objectives

![](/static/2021-03-25-03-36-04.png)

* 之前physical design 涉及3中文件数据结构，，，需要基于特定字段k才能优化组织
  * primary access path
* 而index design
  * secondary access path
  * 提供更多灵活性，可以存在多个字段k
  * 不关心文件类型
  * **好处：避免线性时间检索记录**

:orange: 然而，index structure造成的开销（空间换时间）

* 需要存储额外的元数据
* 并且需要维护元数据，开销
  * 每次修改完相关数据文件后，需要确保元数据等更新

# 开销vs效率 TRADE OFF PLOT: OVERHEAD VS SEARCH SPEED

每个index方式 与每种文件类型方面，都可以生成效率图

![](/static/2021-03-25-03-43-29.png)

# 索引建立原则：Principles

![](/static/2021-03-25-03-44-02.png)

1. 在一个字段上建立一个索引：索引字段
2. 索引结构即另外的文件
   1. 产生存储开销
   2. 产生维护开销，每次修改完相关数据文件后，需要确保元数据等更新
3. 每个索引项需要唯一，并且根据索引字段排序
   1. index-entry = (index-value, block-pointer)
      1. 两个固定字段，比数据实体更小
   2. **pointer指向物理块，（至少存在一条记录有该索引值的块**）
4. 首先在索引文件中搜索找到块指针，然后从数据文件中访问数据块。
   1. 首先索引文件中检索，找到物理块地址
   2. 之后，检索数据文件中的真实数据块
   3. First search within the index to find the block-pointer, then access the data block from the data-file.

# 索引文件比数据文件占用的块少: Index file occupies less blocks than the data-file

![](/static/2021-03-25-03-57-37.png)

index-entry = (index-value, block-pointer)

* 索引项：两个固定字段，比数据实体更小
* 因此，**相同block中**，能比存储记录**存储更多index**
  * 即，bfr(index-file) > bfr(data-file)

## 两种索引类型：密集/分散

![](/static/2021-03-25-03-58-06.png)

* dense index
* sparse index

# 通过索引搜索比通过文件搜索更快: Searching over index is faster than over file

设计时，索引项是根据索引字段排序的，所以默认情况下可以通过二分查找加快检索

![](/static/2021-03-25-04-01-03.png)

* 或通过树方法加快检索步骤（检索块指针，指向数据文件中物理块真实地址）

# 3种索引类型

![](/static/2021-03-25-04-09-22.png)

主索引：索引字段是**顺序文件的排序、关键字段**，如SSN；文件按SSN排序。 Primary Index: index field is ordering, key field of a sequential file, e.g., SSN; file is sorted by SSN

* 通过有序文件建立主索引，索引字段为有序文件的排序字段

聚类索引：索引字段是顺序文件的排序、非键（非主属性）字段，如DNO；文件按DNO排序。 Clustering Index: index field is ordering, non-key field of a sequential file, e.g., DNO; file is sorted by DNO

Secondary Index: index field is:

 非有序，键字段，如唯一的passport number，在有序文件(如按SSN排序)或非有序文件； non-ordering, key field, e.g., unique passport number, over an ordered (e.g., by SSN) or a non-ordered file.
 非顺序、非键字段，例如工资，在一个有序文件(例如按SSN)或无序的文件上； non-ordering, non-key field, e.g., salary, over an ordered (e.g., by SSN) or a non-ordered file.

---

针对这3种类型，需要衡量每个的效率 ： Tade-off plot （2 dimensional plane, searching delay vs expected cost）

## Primary Index

![](/static/2021-03-25-04-19-41.png)
![](/static/2021-03-25-04-40-49.png)

我们在一个顺序文件上建立一个主索引，并且我们使用索引字段作为排序字段。 We're building a primary index over a sequential file and we are using the indexing field to be the sorting field.

* 每个主索引项，
  * 有一对键值
  * k -> index field唯一值
  * p -> 指向包含k的i-th个物理块的第一条记录
* **因为顺序文件**
  * 不需要指向每个块所有记录的指针，只需要指向第一条记录的指针
    * 该记录称为 锚点记录 achor record

图例，索引项数量与数据文件的数据块一样多

### example

* 如果锚点记录（第一条记录）删除/更新
  * 首先更新有序文件的排序字段，高开销，可能需要重新定位锚点记录，而且更新之后他可能不再是锚点记录，因此需要将这个更新传播至索引文件
  * **即，两倍删除/更新开销**
    * 因为还需要考虑更新/删除索引文件 （维护成本，保证index file值，更新）
* 如果非锚点记录删除/更新
  * 只需要删除/更新data file，不影响index file（因为index file只存储anchor record/ordering field）

### 有序文件主索引效率：例子

![](/static/2021-03-28-16-39-32.png)

* baseline
  * worst -> unordered file
  * binary search -> ordered file

![](/static/2021-03-25-04-47-50.png)

* 注意根据索引获得块指针定位块后（二分查找成本），还需要一次块访问取记录

![](/static/2021-03-28-16-45-26.png)

## CLUSTERING INDEX

ordering , non key field (sparse index)

![](/static/2021-03-28-17-22-36.png)
![](/static/2021-03-28-17-41-14.png)

* 为有序文件的非键字段（允许重复值）建立索引 - 考虑聚簇索引
* 聚簇索引项 （每个索引字段的唯一值分配一个索引项）
  * 值 - 索引字段唯一值 One index-entry per distinct clustering value
  * 块指针 - 指向第一个含有索引字段值的记录的聚簇（块）【后面的连续块，链式连接】Block pointer points at the first block of the cluster. The other blocks of the same cluster are contiguous and accessed via chain pointers.

### Clustering Analysis

![](/static/2021-03-25-05-29-13.png)
![](/static/2021-03-28-19-35-50.png)

* 注意规定了是与线性搜索（exiting feature）比较

![](/static/2021-03-28-19-47-56.png)

### 是否建立聚簇索引 & 无穷大唯一值时线性搜索成本：Decision Making

![](/static/2021-03-29-01-25-02.png)

### 聚簇索引开销&效率图：Tradeoff overhead vs speed

![](/static/2021-03-29-01-27-12.png)

* speed up to about 82% by increasing a little bit overhead(1 block) in storage

## Secondary Index

二级/副级索引

![](/static/2021-03-29-21-48-55.png)
![](/static/2021-03-29-21-49-59.png)

* 只针对不关于indexing field的任意文件（文件组织类型不限，只是有序乱序全部无关索引字段） - index a file on a non-ordering field
* 两种index types
  * dense
    * non-ordering key field
    * 因为文件乱序（对于index field来说），无法利用anchor records，**必须给每个记录分配一个索引**
      * 索引项指针指向记录所在的块
  * sparse
    * non-ordering non key field

### 例子 - dense(non ordering key field)

计算检索成本（对比使用二级索引 & 线性搜索(exit feature)）

![](/static/2021-03-29-22-01-38.png)
![](/static/2021-03-29-22-02-33.png)

TRADE OFF: OVERHEAD VS SPEED
![](/static/2021-03-29-22-02-38.png)

### Sparse - non ordering non key field

![](/static/2021-03-29-22-03-44.png)

* 将具有相同索引字段值的记录分组
* 为每组/聚簇分配一个索引项
  * 索引项块指针指向 该聚簇本身
* 每个聚簇中存储 **记录指针**
  * 记录指针指向物理块中对应的各个记录
  * **注意每个聚簇存储的记录指针如果满了，那么就链式连接下一个聚簇**

### 例子 - sparse non ordering non key field

![](/static/2021-03-29-22-23-10.png)

# MultiLevel Index

![](/static/2021-03-29-22-23-43.png)

所有索引文件的相同点

* **有序**
* 值唯一
* 固定长度

---

![](/static/2021-03-29-22-31-52.png)

索引之上建立索引 - multilevel index

* 原始索引文件 - base/ level-1 index
  * (有时候将primary access path称为level1 index)
* 额外索引 - level-2 index

:candy: 找到level `t` 具体值，何时有最优效率，并权衡效率&开销（维护多级索引）

## 例子

primary of primary
![](/static/2021-03-29-22-46-15.png)
![](/static/2021-03-29-22-50-22.png)

## t值 & m(fan-out, bfr)

![](/static/2021-03-29-23-01-30.png)

* `m` 足够大，
* 给定level-1 index， 其块因子 `m`（fan out）
  * 最大多级索引层数(即，块访问成本) - `t=logm b`

## 例子

![](/static/2021-03-29-23-04-03.png)
![](/static/2021-03-29-23-20-06.png)

TRADE OFF: OVERHEAD VS SPEED
![](/static/2021-03-29-23-20-41.png)

