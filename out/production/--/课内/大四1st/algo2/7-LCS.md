# Content

* [Content](#content)
* [最长公共子序列：Longest Common Subsequence](#最长公共子序列longest-common-subsequence)
* [迭代DP：Iterative DP](#迭代dpiterative-dp)
* [Algorithm for Iterative DP](#algorithm-for-iterative-dp)
* [迭代-DP数组例子](#迭代-dp数组例子)
* [迭代-复杂度分析](#迭代-复杂度分析)
* [递归DP-LCS](#递归dp-lcs)
* [记忆化搜索-避免重复计算：Avoiding repeated computation](#记忆化搜索-避免重复计算avoiding-repeated-computation)
  * [初始化-1例子：Example of lazy evaluation (an entry -1 indicates non-evaluation)](#初始化-1例子example-of-lazy-evaluation-an-entry--1-indicates-non-evaluation)
* [避免全局数组初始化-使用“虚拟初始化” - Avoiding initialisation using “virtual initialisation”](#避免全局数组初始化-使用虚拟初始化---avoiding-initialisation-using-virtual-initialisation)
* [记忆化搜索递归DP：Recursive DP with Memoisation](#记忆化搜索递归dprecursive-dp-with-memoisation)
* [===============](#)
* [串局部相似度(最长公共子串?)-Local similarities in strings / texts](#串局部相似度最长公共子串-local-similarities-in-strings--texts)
  * [应用场景：Applications](#应用场景applications)
* [例子-strong local similarity](#例子-strong-local-similarity)
* [问题具体描述：Towards a problem specification](#问题具体描述towards-a-problem-specification)
* [子串局部相似度&编辑距离例子：Local similarities in strings](#子串局部相似度编辑距离例子local-similarities-in-strings)
* [Smith-Waterman Algo](#smith-waterman-algo)
* [回溯求子串：Traceback phase](#回溯求子串traceback-phase)
* [回溯不同路径选择对齐不同](#回溯不同路径选择对齐不同)
* [复杂度-Smith Waterman](#复杂度-smith-waterman)
* [减少空间复杂-只需要相似度，不回溯情况下](#减少空间复杂-只需要相似度不回溯情况下)
* [DP找Origin：Recurrence relations for an origin](#dp找originrecurrence-relations-for-an-origin)
* [合并-Linear（almost） space](#合并-linearalmost-space)

# 最长公共子序列：Longest Common Subsequence

![](/static/2021-11-11-20-29-52.png)

* 子序列的字符不需要连续

# 迭代DP：Iterative DP

![](/static/2021-11-12-02-01-06.png)

* 先找到前几个**前缀的公共子序列**长度`fi,j`
  * Xi, X的前i个字符
  * Yj，Y的前j个字符
  * 注意都从1开始
* 然后通过找到子前缀的公共子序列长度fi,j，延伸到整个X,Y的公共子序列长度fm,n
* 初始状态 fi,0
  * Y空串，与X无公共子序列
* f0,j同理

# Algorithm for Iterative DP

(simple version – just to find LCS length)

* 最简单迭代版本，只求**LCS长度**
* 不匹配情况下，看上一次匹配位置的最大长度
  * 在数组里表示，，i-1,j - 上面，i,j-1左边
* 匹配情况下看 i-1,j-1 - 左上对角线

![](/static/2021-11-12-13-56-41.png)

# 迭代-DP数组例子

![](/static/2021-11-12-14-08-34.png)

如何根据DP数组得到LCS->回溯

* 不匹配位置，fi,j值一定从左边或上面entry得来
* 匹配位置，fi,j值一定从左上对角线来

# 迭代-复杂度分析

![](/static/2021-11-12-14-28-44.png)

* 时间空间都是O(mn)，2个for loop
* 如果只求LCS长度，转移方程只需要前面一个row的信息，空间减少至O(n)
* 递归可以优化
  * lazy LCS evaluation, DP数组只有子集项需要被计算

# 递归DP-LCS

![](/static/2021-11-12-14-31-52.png)
![](/static/2021-11-12-14-43-31.png)

# 记忆化搜索-避免重复计算：Avoiding repeated computation

lazy evaluation

![](/static/2021-11-12-14-48-18.png)
![](/static/2021-11-12-14-51-45.png)

* 如果i,j未被计算过，直接走正常计算流程，并且值存进全局二维数组
* 如果计算过，直接从二维数组中O(1)取值
* 目标：随机获取i,j就能获取到值，并且要能与没被计算过的i,j区分开
  * 全局数组如果初始化entry=-1，则还没有递归调用计算过。**但是意味着每个entry都要被计算**
  * 如何不初始化所有格子？

## 初始化-1例子：Example of lazy evaluation (an entry -1 indicates non-evaluation)

![](/static/2021-11-12-14-55-29.png)

# 避免全局数组初始化-使用“虚拟初始化” - Avoiding initialisation using “virtual initialisation”

![](/static/2021-11-12-15-04-41.png)

怎么区分存储的值是计算后的还是new, malloc分配堆空间时产生的值？

---

**一维数组避免初始化（二维相似**，判断区分是否是计算后的值（非垃圾值）

* array存 `(v,p)`对
  * v -> 要记录的值，比如LCS长度
  * p->指针值，指向int数组b

![](/static/2022-05-03-17-03-59.png)

---

![](/static/2022-05-03-17-05-17.png)
![](/static/2022-05-03-17-05-35.png)
![](/static/2022-05-03-17-05-53.png)

* 利用上面方法，很快能判断`a[5]`是垃圾值，

# 记忆化搜索递归DP：Recursive DP with Memoisation

![](/static/2022-05-03-17-07-47.png)

结合上面记忆化搜索二维全局数组，能O(1)取已经计算过的值，加快LCS计算

# ===============

Smith Waterman algorithm 局部相似度

# 串局部相似度(最长公共子串?)-Local similarities in strings / texts

![](/static/2021-11-12-15-33-02.png)

* LCS(subsequence)长度`<min(X.len, Y.len)`所以全局来讲X，Y不相似
* 给定X,Y字符串，，DP求最大局部相似度？strong local similarities
  * Smith-Waterman algorithm

## 应用场景：Applications

![](/static/2021-11-12-15-36-30.png)

# 例子-strong local similarity

![](/static/2021-11-12-15-37-59.png)

# 问题具体描述：Towards a problem specification

![](/static/2021-11-12-15-46-29.png)

- 编辑距离越大，两个子串越不相似
  - 匹配 - 0
  - 增伤改 +1
- 找两个子串的相似分数 similarity score
  - 每个匹配 分数+1
  - 增删改，分数-1
  - **相似度分数越高，两个子串越相似**

# 子串局部相似度&编辑距离例子：Local similarities in strings

![](/static/2022-05-03-17-21-43.png)

问：如何找到X，Y两个主串最高局部相似度

* Smith-Waterman算法
  * 尝试所有可能的X,Y**子串**，，如何最大化相似度分数（Optimal 对齐

# Smith-Waterman Algo

Dp

![](/static/2022-05-03-17-27-11.png)
![](/static/2022-05-03-17-36-41.png)
![](/static/2022-05-03-17-38-26.png)

---

不匹配时,看左-1，上-1，左上-1，0，取最大

* Smith waterman算法，最终结果不一定在s[i,j]。。因为最高分数不一定是最后子串结尾的位置

![](/static/2022-05-03-17-42-38.png)
![](/static/2022-05-03-17-42-48.png)

* 5种substring可以达到最高分数4

# 回溯求子串：Traceback phase

![](/static/2022-05-03-17-46-47.png)

边backtrace边记录两个子串结果

![](/static/2022-05-03-17-52-54.png)
![](/static/2022-05-03-17-58-02.png)

# 回溯不同路径选择对齐不同

![](/static/2022-05-03-18-00-05.png)

---

![](/static/2022-05-03-18-01-40.png)

extent

# 复杂度-Smith Waterman

![](/static/2022-05-03-18-05-55.png)

* 可以使用candidate set记录达成最高分数的i,j
  * ![](/static/2022-05-03-18-04-00.png)
  * 分数更新，更新set中的索引对

# 减少空间复杂-只需要相似度，不回溯情况下

![](/static/2022-05-03-18-09-17.png)
![](/static/2022-05-03-18-11-54.png)

* 结束点 (i, j)
* 起点origin (Xij, Yij)

# DP找Origin：Recurrence relations for an origin

![](/static/2022-05-03-18-15-50.png)

# 合并-Linear（almost） space

只求相似分数

candidate set,，，不回溯情况下

![](/static/2022-05-03-18-20-19.png)