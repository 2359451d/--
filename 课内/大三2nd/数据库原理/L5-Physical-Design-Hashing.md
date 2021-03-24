# Lecture 5 - Physical Design & Hashing

Part B.1: Physical Design, Indexing & Hashing

![](/static/2021-03-02-14-20-50.png)
![](/static/2021-03-02-14-21-46.png)

* physical storage and file organization
* record, block and blocking factor
* 3 fundamental file structures
  * heap file 堆文件
  * sequential file 序列文件
  * hash file 哈希文件
* algorithms for accessing data from files
  * operations: search, insert, delete, update
  * estimate the expected cost

# 物理存储结构：PHYSICAL STORAGE HIERARCHY

![](/static/2021-03-02-14-24-11.png)

3级存储结构 3-level storage hierarchy

* 主存 Primary storage
  * RAM:min memory, cache
* 辅存 Secondary storage
  * hard-drive disks HDD, solid-state disks SSD
* 三级储存 Tertiary storage
  * optical drives 光驱

从上至下，

* 存储容量增加 Storage capacity: increases 
* 访问读写速度下降 Access speed: decreases
* 成本下降 Money-costs: decreases

# 数据库物理存储：Physical Storage for a databse

![](/static/2021-03-02-14-35-21.png)

问题：数据库过大，无法存放在主存中 Database is too large to fit in the main memory

* 因此，默认省成本，数据存储在辅存（HDD）中 Data access involves secondary storage (HDD) due to low cost

:orange: 会导致的结果

* **因为HDD不能直接由CPU访问，进行操作** Since HDD is not CPU-accessible, then
  * 数据必须先从硬盘加载至主存 data must be first loaded into main memory from disk
  * 在主存中处理数据，进行计算 data are then processed in the main memory
* **读写速度很慢** The speed of data access becomes low
  * HDD读写-30ms，RAM读写-30ns Data access from HDD takes 30ms; while only 30ns in RAM, thus, HDD is the main bottleneck (data transfer)
  * HDD存在数据传输瓶颈

:orange: 因此，需要优化，减少HDD（减少硬盘到主存传输时间）时延，organize data on HDD to minimize the latency

* 需要一个能在文件，磁盘中组织数据的特定数据结构

# 基于数据组织的优化：ORGANIZATION-BASED OPTIMIZATION

![](/static/2021-03-02-14-50-01.png)

如何组织磁盘中tuples（数据逻辑结构），以减小IO读写访问成本（提高速度）

* 关系型基模中信息的逻辑表示a tuple - logical representation of a piece of information that established in the relational schema

tuple的表示 - representation

* 实际表示 - Record
  * bunch of **binary digits**
* **多个Record形成Block** Records are grouped together forming a Block
* File由多个Block组成 FIle is a group of blocks

:orange: Records长度

* 固定长度：属性字节大小固定 Fixed length, i.e., attributes are of fixed size in bytes.
  * 主要关注固定长度，以减小管理可变长度记录的开销
* 可变长度：属性大小可变 Variable length, i.e., the size of each attributes varies

# 记录 - 存储例子：Record as a tuple

![](/static/2021-03-02-14-57-17.png)

* 图例为tuple的record表示
* 需要存储74B的记录

## 块因子：Blocking Factor

![](/static/2021-03-02-14-59-26.png)

OS中块设计为固定长度，通常为512B~4096B（基于使用的OS） block is of fixed-length, normally 512B~4096B

:orange: `floor(x)`小于或等于x的最大int

---

:orange: 考虑R字节的记录，B字节的块 consider a record of R bytes & a block of B bytes

:orange: **块因子bfr** 每个块里存储的记录数 The number of records stored in a block, i.e., records per block, is called: blocking factor (bfr)

* `bfr=floor(B/R)` floor function of bytes of blocks out of bytes of records
* 假设块因子bfr=100,即，每个块能存储100个记录
* 假设bfr=9,每个块存储9条员工记录

:candy: 块大小至少比记录大一点，至少可以存储1条记录 at least 1 record per block

* `B>=R`

## 磁盘如何分配文件中块：Blocks to Files on Disk

![](/static/2021-03-02-15-12-34.png)

第二个挑战是，鉴于我们已经将所有这些记录存储在块中，而一组块组成文件，那么如何将这些文件块分配到磁盘上。second challenge is that given the fact that that we have stored all these records in blocks and a group of blocks consists a file, then how can allocate these blocks of files over the disk.

有许多方式在硬盘中，分配&存储块

* 最常用 - **linked allocation** 链式分配
* 每个块`i`都有一个指针，指向磁盘上任何地方逻辑上下一个块`i+1`的物理地址，即块的链表。 Each block i has a pointer to the physical address of the logically next block i+1 anywhere on the disk, i.e., a linked list of blocks;
  * 所以可以通过指针完成**相同文件中**块的访问

### 例子

![](/static/2021-03-02-15-18-25.png)

* 关系EMPLOYEE中有1103个元组，每个都对应一个固定长度的记录
  * ...
* OS提供512B的块
  * bfr块因子是多少？
  * 文件中一共多少个块？

---

![](/static/2021-03-02-15-22-00.png)

* **每个记录的大小**
  * 需要存储的所有属性的大小(即，**元组大小tuple size**) - 100B
* 块因子 `bfr` = floor(B/R) = floor(512/100) = 5
  * 每个块能存储5个职员记录
* 总文件块数 `b` = ceil(r/bfr) = 1103/5 = 221

# 3个数据文件结构：File Structure

![](/static/2021-03-02-15-29-35.png)

问题3 - 如何分配块中所有记录，以最小化IO成本? how to distribute records within blocks to minimize I/O cost

3个数据文件结构

* **Heap File** 堆文件 （unordered file）
  * 新纪录会直接追加至文件末尾(即追加在最后一个块中)，new record is added to the end of the file, at the end of the last block
* **Ordered File** 有序文件 （sequential file）
  * records are kept physically sorted 记录按顺序存储
  * 选取什么作为排序字段以最小化成本？ which is the best ordering field in order to minimize the cost?
* **Hash File**
  * 使用数学哈希函数，应用于每个记录中特定字段的值上（**需要自行选定最优哈希字段**，称为哈希字段hash field） adop a math hashing function which is applied on each record and specifically over the value of a specific field, that we have to choose, and is called the hash field
  * a hashing function y = h(x) is applied to each record field x (hash field) 对每个记录字段x（散列字段）应用散列函数y=h(x)。
  * 输出的y是物理块地址；将一条记录映射/存储到一个块上! The output y is the physical block address; mapping a record to a block!

:orange: 引出新问题，如何选取最优排序字段？最小化有序文件 的IO成本，如何选取最优哈希字段？最小化哈希文件IO成本

## 预期IO访问操作成本：Expected IO Access Cost

![](/static/2021-03-02-15-49-28.png)

已知文件结构后，需要定义IO访问成本

* **检索操作成本**：从磁盘到主存检索整个块，找出一条记录 - searching field, search cost
* **CUD操作成本**：对记录增删改，并将数据块从主存移至磁盘的成本（update cost）

:orange: 预期成本 - 成本函数（操作的预期成本，预期块访问次数） cost function

* 预期成本是指为了读取或写入块以搜索、插入、删除和更新特定记录的预期"**块访问次数"**。expected cost is the expected 'number of block accesses' in order to either read or write blocks to search, insert, delete and update a specific record

:candy: 注意：块是OS中最小的通信单元，我们只将块而不是记录从磁盘传输到内存，反之亦然! Note: block is the minimum communication unit; we transfer only blocks and not records from disk to memory and vice versa!

* 就算是更新一条记录，一个字段，也只能传输整个块

## 随机变量预期值：Expectation of a Random Variable

![](/static/2021-03-02-15-58-28.png)

expectation of random variable/ mean or advantage of varible. the weighted sum of the values

* 变量为`x`时的概率 * `x`

## 成本函数预期值：Expectation of a Cost Function

![](/static/2021-03-02-16-47-11.png)

`C(X)` - 访问`X`需要的成本，即**块访问次数**

* `C(X)` X is a random variable being a function of `X`
* 例如， `C(X=1)/ C(1)=3` 访问1 需要3次块访问，`C(3)=1`需要1次块访问...

因此，cost function's expectation， --- expetaction of another random variable, (`C(X)`)

* `𝐸[𝐶(𝑋)] = σ𝑥 𝑃 𝑋 = 𝑥 ∙ 𝐶 𝑥 = 2.5 block accesses`
* 结果就是预期块访问次数成本

:orange: 因此计算任何数据结构的预期成本，只需要计算 `C(X) & 其概率`

# 堆文件：Heap File

![](/static/2021-03-02-16-49-27.png)
![](/static/2021-03-02-17-31-49.png)

堆文件结构 - CRUD所需成本（块访问次数）

* **插入操作 - 高效** insertion of a new record - efficient
  * 加载硬盘中最后一个数据块至内存 （address in file header）
  * 在数据块末尾插入新记录 & 写回硬盘
  * **一共2次块访问**，`O(1)`常量复杂度
* **检索操作 - 非常低效**
  * 因为没有任何元数据供参考，不能直接找到特定记录，**所以需要线性扫描文件中所有数据块，假设某文件有`b`个块，就需扫描`b`个**
  * 然后每次将数据块从硬盘加载至内存，查找记录，重复此操作
  * 平均需要 `b/2`次块访问操作
    * 最好情况，记录就在第一个加载的块中， `1`次块访问
    * 最坏轻快，`b`次块访问
  * `O(b)`, 线性复杂度， `b`如果过大效率极其低下
* **删除 - 低效** deleting a record is inefficient
  * 找到并加载包含记录的块；（检索过程）。 Find and load the block containing the record; (retrieval process). - `O(b)`
  * 从块中删除记录并将块写回磁盘。 Remove the record from the block and write the block back to disk. - `O(1)`
  * **造成块中形成未使用的空间 This leaves unused spaces within blocks!**
  * 成本复杂度 - `O(b)+O(1)`
  * :orange: 为了解决未使用空间，**为每个块引入deletion markers**
    * 删除标记指示符有两个值 indicators taking 2 values
      * `1` - 记录已删除 the record is deleted
      * `0` - 记录未删除
      * <font color="deeppink">注意存储删除标记，表明元数据删除的位置，也会造成一些存储开销</font>
    * 定期，通过**收集未删除的记录(deletion marker `bit=0`)和释放已删除的记录**的块来重新组织文件。periodically, re-organize the file by gathering the non-deleted records (bit=0) and freeing up blocks with deleted records. 

# 有序文件-检索：Sequential File

有序文件结构是如何加快搜索/检索步骤的？

![](/static/2021-03-02-19-16-51.png)

* 图例，选取Name为排序字段
  * 对于Name的查询只需要顺序扫描
  * 包含排序字段的查询，可能比heap file还高效，因为数据有序
  * 针对范围查询也高效，因为可以加快查询（如果定位到第一个 & 最后一个记录的范围）

前提

* 确保选中一个排序字段 choosing the order field
* **并且每次要修改文件前，保证文件根据这个排序字段进行物理排序的** make sure every time about to modify the file, the file(all the records) is kept sorted by the ordering field

:orange: 因此，存在的问题是，如何选取最优排序字段，以最小化预期成本（块访问次数）which should be the ordering attribute in order to minimize the expected cost

---

![](/static/2021-03-02-19-40-56.png)

:orange: **根据排序字段检索记录** Retrieve a record using the ordering field; 

* efficient
* **针对ordering field的查询（如字段是Name，查的过滤条件也是Name）** - 成本复杂度：`O(log2 b)` sub-linear with b
  * `b` - 块数
  * 因为二分查找 binary search

:orange: **无关排序字段的检索** Retrieve a record using a non-ordering field; inefficient

* 低效（和heap file检索一样低效）
* `O(b)` linear with b. 查询不涉及相关ordering field则不可避免进行linear search

## 块模式-二分查找：BINARY SEARCH (BLOCK MODE)

块模式的二分查找

* 块是最小传输单元，
  * 无法传单个记录

![](/static/2021-03-02-22-37-16.png)

* 使用3个指针 3 pointers
  * lower pointer `l`
    * 指向文件第一个块
  * upper pointer `u`
    * 文件最后一个块
  * middle pointer `m`
    * 中间块

在内存中搜索之前，需要确认搜索的值是否大于/小于该块的第一个key value，或大于该块的最后一个key value

* 决定扫描m左半边 left-sub space or 右半边 right sub space 
* 否则正好扫描m

---

:orange: 图例 - 搜索 `k=5`，

* 中间块 `7，8，9`
  * 5<7 所以搜索左半边文件

![](/static/2021-03-02-22-54-05.png)

* 最后涉及2个块访问，搜索5的成本

## 二分查找算法：Binary Search Algorithm

![](/static/2021-03-02-22-55-13.png)

pseudocode

## 假设-范围查询高效：Discussion

如果找到了最优排序字段，可以假设范围查询高效 range queries are efficient

![](/static/2021-03-02-22-56-45.png)

* 图例 - 检索满足 £30K ≤ Salary ≤ £50K 范围的职员记录
  * Salary - ordering field
  * upper bound & lower bound
* 方法
  * 使用二分查找定位块，至少有一条记录满足范围查询的lower bound
    * 即，定位至少有一条记录，一个员工记录满足 salary >= 30K
    * 复杂度：`O(log2 b)`
  * 继续检索，直到某块有记录，满足salary <= 50K
    * 共`i+k`块， `k=0,...,<b`
    * 复杂度 `O(b)`
  * 总复杂度：`O(log2 b)+O(b)`次块访问

# 有序文件-插入：Sequential File-Insertion

![](/static/2021-03-03-00-25-53.png)

Insertion is expensive.插入的成本很高

 首先，找到应该插入记录的区块：二进制搜索。First, locate the block where the record should be inserted: binary search.
 **平均来说，有一半的记录必须被移动，以便为新记录腾出空间**。...对于大文件来说非常昂贵 On average, half of the records must be moved to make room for the new record. …very expensive for large files!

:orange: 图例

* 如果块中有空闲空间，就在那里插入新记录。
* **如果，不能在第一个块中插入新记录，避免移动其他tuple，在overflow block中使用chain pointers插入新数据**
  * 考虑使用 **chain Pointers**
    * 基于，**每个记录都逻辑指向下一个记录**（有序链表） Each record points to the logically next ordered record.
  * **并且，形成一个新block - overflow block**
* 插入后，pointers需要被更新，任何的增删改，都需要维护有序链表 pointers must be updated, it's a sorted-linked list

# 有序文件-删除&更新：Sequential File-Deletion & update

![](/static/2021-03-03-00-34-04.png)

首先，找到要删除记录的区块；二分查找。 First, locate the block where the record is to be deleted; binary search.
 将删除标记从0更新为1，并更新指针不指向删除的记录。 Update the deletion marker from 0 to 1 and update the pointer not to point to the deleted record.
 定期对文件进行重新排序，以恢复物理上的顺序（即外部排序......昂贵）。Periodically re-sort the file to restore the physically sequential order (i.e., external sorting…expensive)

---

更新排序字段的成本很高。Update on the ordering field is costly.
 记录从原来的位置被删除，然后插入新的位置。 The record is deleted from its old position & inserted into its new position.
* cost for deletion & cost for insertion

在非排序字段上更新效率高! Update on a non-ordering field is efficient!
 Complexity: O(log2b) + O(1) block accesses

* 只需要二分查找定位块，找到这个tuple记录，然后进行更新，写回内存

# 有序文件效率总结：Sequential File Performance

![](/static/2021-03-03-01-11-48.png)

为什么二分查找需要`O(log2 b)`次块访问成本？

* log(140) = 7 需要>=7步用2分离140，直到1，math层面
* db层面，每步都需要将搜索域分为2个子域，直到找到块

考虑，能不能每步分成更多的子域，如`m`个，`m>3`

* 则需要`logm b`次块访问，最优`m`是什么？
* 引出B树

# Hash File

![](/static/2021-03-05-14-50-45.png)

:orange: idea of hashing

* **将记录分为多个bucket**
  * 每个bucket编号
* **每个bucket可以有多个块**
* **选取hash function y= h(k), 对搜索字段应用该函数**
  * 输出 y∈{0,1,...,M-1} bucket index

:orange: Hash function requirements

* h将记录均匀地分布到桶中{0，...，M-1}，即对于每一个值k，每一个桶的选择为相等概率1/M。 h uniformly distributes records into the buckets {0, …, M-1}, i.e., for each value k, each bucket is chosen with equal probability 1/M:
* **candidate hash function**
  * `y = h(k) = k mod M`
  * 取余，output为0~M-1的index

## 外部哈希方法：Hash File Construction - External Hashing

![](/static/2021-03-05-14-59-11.png)

:orange: 外部哈希方法(external hashing methodology)-将记录映射至 y=h(k) 哈希函数结果，桶索引 Mapping a record to a bucket y = h(k) is called external hashing over hash-field k.

* 即，external hashing over hash-field k
  * 为tuple找到对应的 bucket index
* **Collisions 哈希冲撞（可能造成溢出）**
  * >=2 记录映射至同一个bucket collisions occur i.e., two or more records are mapped to the same bucket

:orange: 间接聚类-如果相同桶索引存在多个记录，只能说明对他们的hash field应用hash func后，他们的索引相同

* Indirect clustering: group tuples together w.r.t. their hashed-values y and not w.r.t. their hash-field values k

## 外部哈希优点&复杂度：External Hashing Algorithm

![](/static/2021-03-05-15-12-30.png)

:orange: 应用外部哈希算法/外部哈希方法时，主存中建立一个哈希表，索引为桶索引0~M-1，每个桶对应一个块的物理地址 When we apply the hashing algorithm or the external hashing methodology into a file, we are constructing in our main memory a hash-map where we are having the bucket numbers from 0 to M-1, and for each bucket number we're having a physical address of the block

* **也就是，可以通过每个bucket，直接访问硬盘上块(也就是定位存放某个记录的块**) bucket number means that can have direct access to the block on the disk

:orange: 例子 - 检索SSN=123

* hash field k =123, 
  * 应用hash function, h(SSN) = M-2
* 使用哈希表获取 M-2桶的物理块地址
* 定位硬盘中该块
* 对该块进行线性搜索，满足 h(SSN)=M-2，最终找到该记录

:orange: 复杂度 complexity - O(1) block access

* directly get the block containing the record.
* 不用扫描所有块

## 外部哈希溢出：External Hashing Overflow

![](/static/2021-03-05-15-22-34.png)

:orange: 需要解决哈希冲撞， - 多余1条记录映射于同一桶中，桶有可能满，溢出 Due to collisions, i.e., more than one record is mapped to the same bucket, the buckets might be full

* 问题：如何将新记录插入满桶？How can we insert a new record hashed to a full bucket
  * **需要应用链式指针方法** apot the chain pointers method
  * 每个桶有一个未使用的溢出位置，应用链式指针连接这些溢出桶，可以冲装冲撞

:orange: 平均复杂度 average complexity

* `O(1)` + `O(n)` block accesses
  * `n` - number of overflow blocks
  * `n<b`
  * `O(1)` - **第一个main bucket, can fin the specific tuple**
  * `O(n)` - **cannot find in the first main bucket, had to search the rest n overflow buckets**

:candy: **因此需要找到一个hash function, 均匀分布tuple进bucket** uniformly distriute the records

* 否则可能存在一个全是tuple的bucket，而其他bucket可能为空

:orange: 当一个新的记录与一个已经满了的桶进行哈希时，就会发生碰撞。一个溢出文件保存，用于存储这些记录（溢出的桶）。**每个桶的溢出记录（桶）可以通过链指针连接在一起**。 Collisions occur when a new record hashes to a bucket that is already full. An overflow file is kept for storing such records. Overflow records that hash to each bucket can be linked together.

## 外部哈希文件-删除/更新记录：External Hashing

![](/static/2021-03-05-18-12-50.png)

如何基于hash field，进行删除/更新？ how can delete/update record, based on the hash field

:orange: Delete

* 步骤
  * 找到记录 find the record first
  * 使用删除标记 use the deletion markers change the value from 0 to 1
  * 定期，通过**收集未删除的记录(deletion marker `bit=0`)和释放已删除的记录**的同桶中的块来重新组织文件。periodically, re-organize the file by gathering the non-deleted records (bit=0) and freeing up blocks with deleted records. (pack together blocks of the same bucket to free up block with deleted records)
* 如果记录在主桶中,`O(1)` - if record is in the main bucket
* 否则，需要通过链指针定位溢出桶 `O(1)+O(n)`， else follow the chain to  overflow block

:orange: update

* 先确认是否在更新hash field **check whether or not updating the value of the hash field**
* **假设更新non-hash field**
  * 定位记录（主桶or溢出桶） locate record in main or verflow bucket
  * 将块加载至主存，更新，写回 load block into memory,update, write it back
  * complexity: `O(1)` or `O(1) + O(n)`

:candy: <font color="deeppink">假设更新 hash field</font>

* change the hashed-value! delete form the old bucket and insert to the new bucket.
* 需要重新计算哈希值，定位record
  * **也就是进行删除操作，将记录从旧桶中删除** deletion process to delete the record from the old bucket
  * **再进行插入步骤，将更新后的记录插入hashing function计算后的新桶索引** insertion process to insert the record in the new bucket as dictated by the hashing function

## 例子-哈希文件块访问成本

![](/static/2021-03-05-19-15-06.png)

假设3个桶，bfr=2每个块存2条记录， hash field:SSN, k有6条员工工号记录

:orange: 成本计算&成本比较步骤

1. 应用hash function, 将所有记录分进桶
   1. `y=SSN mod 3`
2. **计算每个随机查询(根据SSN=k)所需的预期成本**（expected number of block accesses for a random SQL）
   1. worst case
      1. 记录在最后一个block, very last block
   2. best case
      1. record is in the first block(main bucket)
3. **与堆文件和顺序文件比较(按SSN的顺序排列)，得出最好、最差和平均的情况**。 Compare with the Heap File and Sequential File (order w.r.t. SSN) for **the best, worst, and average cases**

### 步骤1-分桶

![](/static/2021-03-05-19-34-47.png)

* heap file
  * 6条记录，只需要3个block存储（bfr=2）
* hash file
  * 应用hash func，计算index
  * 桶1 有4条记录记录，然而brf=2,因此需要额外1个桶(溢出桶)，一共2个桶
  * hash file一共4个block来存储所有的记录

:orange: 此例，所有记录没有均匀分布，所以hash function not ideal

### 步骤2-计算最好最坏块访问成本

随机查询下（random query）best,worst case ,expected number of block accesses

![](/static/2021-03-05-19-43-11.png)

From theory,

* M=3，有3个桶，每个桶概率相同，因此1/3=33%
* 假设访问
  * M=0，1次块访问
  * M=2，1次块访问
  * M=1，**worst case**（record in very last block）, 2次块访问 2个记录+2个溢出记录
* 因此计算预计成本 expected cost
  * 概率 * 块访问次数（每个桶内查找的成本）

---

best case

![](/static/2021-03-05-20-20-45.png)

* first block, main bucket

### 步骤3-比较堆/序文件效率

![](/static/2021-03-05-20-41-32.png)

* worst case
  * hash:1.32 block access
  * heap: 3 block (record is very end, linear scan)
  * sequential: binary search , log2 3=1.58
* best case
  * first block(main bucket)
* average case
  * heap: best case + worst case/2 = 1+3/2=2
  * sequential: binary search log2 3=1.58
  * hash: 因为bucket 1有两个block，两个block相同访问概率
    * 0.33是访问bucket1的概率
    * 0.5*1是bucket1内访问第一个block就能找到记录的成本
    * 0.5*2是bucket1内访问到第二个block能找到记录的成本

**因此可以说此例的hash方法最好，但总体其他情况还需要考虑Crash test**

# 哈希文件范围查询效率：Crash Test

我们先从处理假设的崩溃测试开始，当我们在哈希文件上执行范围查询时，那么这个过程是低效的。let's start first with the crash test dealing with the hypotheses that when we are executing range queries over hashed files, then this process is inefficient.

![](/static/2021-03-05-20-54-01.png)

hash file

* **先定位至少含有一条下限记录的块** locate locate the block that contains at least 1 record satisfying the lower bound of the range query
  * age=20
  * `O(1)+O(N)`
* 解决剩余的范围查询中的连续值
  * 注意这些连续的值不会与20分在同一个桶里，因为最理想的hash function会均匀映射这些剩余的记录进桶
  * 因此，**hash file的缺点就是不能很好处理范围查询，因为我们想均匀分配所有的值，但这点无法帮助提升范围查询的效率**
    * 因为，如果均匀分布了，针对范围查询中连续的值，需要多个桶的不同的查询
    * `O(m*1) + O(nm)`
      * m个不同桶查询，都在第一个block
      * m个不同桶查询，都涉及溢出块定位， n*m
* 因此，hash file处理range query比sequential file低效

# 哈希文件成本可预测吗？Predicatable or Unpredictable

![](/static/2021-03-05-21-25-22.png)

假设 哈希字段的潜在值分布很大程度决定，影响预计成本 distribution of the underlying values that having over the hash field influences significantly the expected cost

* 即，预计成本不可预测 --- expected cost is quite unpredictable

---

图例可以看出，查询age=20的记录，有2000条

* 成本 `O(1)+O(n)`次块访问
* 因为bfr=40，也就是一共50个块
  * 1 main block + 49 overflown blocks

查询age=60的记录，有23条

* 成本 `O(1)`次块访问，因为bfr=40，1 block可以装下所有23条记录

:orange: 即，预期成本基于哈希字段的分布值，无法预测，不同分布成本不同 expected cost is underlying distribution of the hash field

# 例子：选取sort file or hash file - Decision Making: Hash or Sort

![](/static/2021-03-05-21-34-31.png)

实际情况中，所有查询有的基于字段 k，有的无关k则概率，则概率

* p%涉及k 
* 1-p % 无关k

:orange: 注意如果无关k，无法利用hash file的特性，hash file一共4个blocks，

* worst case non-k field: (1-p) * 4 成本
  * 需要访问所有块找到记录，无关k概率为1-p

:orange: 无关k，无关ordering field, 也无法利用排序文件的特性

* 需要扫描整个块，worst case
  * （1-p）* 3

计算出两种方法的最坏情况预计成本后，解不等式，决策

* p>0.79 - 至少要79%的查询都与k有关，采取hash file
* 否则采取 sort file

![](/static/2021-03-05-21-55-49.png)

* p=0时，查询不涉及ordering field 或hash field所以成本都最大，
  * sort file -  3blocks linear scan
  * hash file - 3 buckets(3 main+1 overflown blocks)