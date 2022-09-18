# Primary Access Path Decision

1. 给定记录大小 = 10+40+50+50=150B
   1. ![](/static/2021-03-29-18-42-11.png)
2. 给定记录总数=1000
3. 操作 - 检索
4. 统计量
   1. 30% - SALARY selection
   2. 50% - BONUS selection
   3. 20% - WORKING_HOURS
   4. 0% - ID
5. OS 提供 1024B块大小
6. 外部哈希 `h(x） = X mod 5`

---

# Solution

重点：

1. 分析统计量（查询语句涉及的不同字段，所占比）
2. 比较选取的每种file type组织方式所对应的成本

## 分析字段数据分布：Distribution

分析检索查询涉及的3个字段，BONUS，WORKING_HOURS, SALARY（因为ID占比为0%，忽略）

* 追求均匀分布uniformly distribution，**属性字段值出现几率相同**
  * 利益于哈希文件

---

![](/static/2021-03-29-20-22-51.png)

* exponential distribution
  * 会影响hash file organization
* Normal like distribution
  * around mean

---

因为HOURS字段的数据均匀分布，

* 如果选择hash file，可以进行1block（最好情况）块访问定位数据
* 所有桶大小相同，不会存在某桶大部分空间未使用
  * 因此检索操作的块访问成本可预测

### HOURS：总期望值分析

:orange: 注意

* 只有**20**%的查询语句涉及HOURS，
  * <font color="deeppink">剩下80%的语句都必须线性扫描，因为哈希文件乱序</font>
* 如果选取HOURS为哈希字段
  * 则只适用于非范围查询，
  * best case expectation: `E1=0.3*O(b) + 0.5*O(b) +0.2*O(1)=0.8*O(b) + 0.2 * O(1)`
  * 如果选取其他字段为hash field， **成本只会比E1大（因为其他字段分布不均匀**）

## Sequential File

利用二分查找

有序文件不关心字段值分布

* 需要确保排序后，不会出现聚簇（有相同排序字段值的记录）
  * 预计 log2 b成本
  * **如果出现聚簇，可能需要>=1个额外块访问，最坏情况下成本 > O(log2(b))**
  * <font color="red">本例不涉及，因为所有字段都为key，可以确保二分查找成本</font>

### sorting field选取 - BONUS

选取占比最大的字段， BONUS（50%）

* 因此，如果对文件进行关于BONUS字段的排序，期望值
  * `E2= 0.5*O(log b) + 0.3*O(b) +0.2 * O(b)=0.5 * O(log b) + 0.5*O(b)`
  * 剩下50%的查询需要线性搜索，无关BONUS

### sorting field选取 - HOURS

注意HOURS只占20%， 如果选取HOURS为排序字段

* 成本
  * 0.8*O(b) + 0.2 * log b
  * 80%的查询需要线性搜索
* **选取HOURS，此时有序文件效率也不客观，所以order field的选取也影响决策，通常基于查询占比统计量决定**

## 非范围查询的期望值比较

<font color="red">注意，有序文件对于范围查询也高效。而哈希文件不利</font>

* 比较 E1 & E2（非范围检索查询），建立不等式

![](/static/2021-03-29-21-33-08.png)

* 可知，hash file成本为指数增长，因此不适合选择

## 范围查询成本比较

哈希文件的范围查询，很差

## Heap file

heap file的随即检索效率极低（乱序）

* 成本
  * `E3=0.5*O(b)+0.3*O(b)+0.2*O(b)=O(b)` 所有查询都需要线性扫描
* **所以不考虑HEAP file**

