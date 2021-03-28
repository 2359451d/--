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

![](/static/2021-03-25-04-41-09.png)

![](/static/2021-03-25-04-47-50.png)

![](/static/2021-03-25-04-52-58.png)

## CLUSTERING INDEX

。。。

![](/static/2021-03-25-05-29-13.png)

## Clustering Analysis


