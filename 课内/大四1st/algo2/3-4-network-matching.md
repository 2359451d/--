# Content

* [Content](#content)
* [图和匹配算法:Graph and matching algorithms](#图和匹配算法graph-and-matching-algorithms)
* [二分图中的匹配：Matching in bipartite graphs](#二分图中的匹配matching-in-bipartite-graphs)
  * [最大匹配](#最大匹配)
  * [项目分配例子](#项目分配例子)
* [Naïve algorithm for maximum matching](#naïve-algorithm-for-maximum-matching)
* [Towards a faster algorithm O(n^3)](#towards-a-faster-algorithm-on3)
* [增广路径：Augmenting paths in graphs](#增广路径augmenting-paths-in-graphs)
  * [增广路径定理：Augmenting Path Theorem](#增广路径定理augmenting-path-theorem)
  * [搜索增广路径算法：The augmenting path algorithm](#搜索增广路径算法the-augmenting-path-algorithm)
    * [例子](#例子)
  * [增广匹配M算法：Augmenting the matching along an augmenting path](#增广匹配m算法augmenting-the-matching-along-an-augmenting-path)
* [增广路径完整例子](#增广路径完整例子)
* [最大匹配-增广路径算法分析：Algorithm analysis](#最大匹配-增广路径算法分析algorithm-analysis)
* [增广路径算法总结：Summary](#增广路径算法总结summary)
* [===========](#)
* [网络流：Network Flow](#网络流network-flow)
  * [例子](#例子-1)
  * [应用场景](#应用场景)
  * [我们怎么得到一个max value的flow？](#我们怎么得到一个max-value的flow)
* [网络流-增广路径：Augmenting Paths](#网络流-增广路径augmenting-paths)
  * [例子](#例子-2)
  * [利用增广路径增广：Augmenting a flow along an augmenting path](#利用增广路径增广augmenting-a-flow-along-an-augmenting-path)
  * [增广路径定理-证明：theorem](#增广路径定理-证明theorem)
* [网络流-（最小）割：Cuts](#网络流-最小割cuts)
* [切和流的关系：Relationship between flows and cuts](#切和流的关系relationship-between-flows-and-cuts)
* [求最大流:Finding a maximum flow](#求最大流finding-a-maximum-flow)
* [残留网络：The residual graph](#残留网络the-residual-graph)
  * [例子](#例子-3)
* [求最大流算法：Ford-Fulkerson Algorithm](#求最大流算法ford-fulkerson-algorithm)
  * [分析](#分析)
  * [worst-case example](#worst-case-example)
* [改进Ford-Fulkerson: Improving the worst case](#改进ford-fulkerson-improving-the-worst-case)
* [残留网络完整例子](#残留网络完整例子)
* [二分图最大匹配->网络流：A faster algorithm for maximum matching](#二分图最大匹配-网络流a-faster-algorithm-for-maximum-matching)
* [===========](#-1)
* [稳定匹配问题：Stable matching problems](#稳定匹配问题stable-matching-problems)
  * [例子](#例子-4)
* [检测匹配稳定性算法：Stability Checking](#检测匹配稳定性算法stability-checking)
* [寻找稳定匹配算法:Finding a stable matching](#寻找稳定匹配算法finding-a-stable-matching)
* [Gale/Shapley algorithm ("man-oriented" version)](#galeshapley-algorithm-man-oriented-version)
* [Gale/Shapley algorithm算法正确性: Correctness of the Gale/Shapley Algorithm](#galeshapley-algorithm算法正确性-correctness-of-the-galeshapley-algorithm)
* [The “man-optimal” property](#the-man-optimal-property)
* [实现-Gale/Shapley algorithm：Implementation of the Gale/Shapley algorithm](#实现-galeshapley-algorithmimplementation-of-the-galeshapley-algorithm)
* [分析-Gale/Shapley algorithm：Analysis of the Gale/Shapley algorithm](#分析-galeshapley-algorithmanalysis-of-the-galeshapley-algorithm)
* [Lecture 8 - part 5============](#lecture-8---part-5)
* [匹配算法应用：Applications of Matching Problems/Algorithms](#匹配算法应用applications-of-matching-problemsalgorithms)
* [Hospitals/Residents Problem (HR)](#hospitalsresidents-problem-hr)
  * [例子](#例子-5)
  * [Blocking Pair](#blocking-pair)
  * [非稳定例子](#非稳定例子)
  * [稳定例子](#稳定例子)
* [算法和结构结果：Algorithmic and structural results](#算法和结构结果algorithmic-and-structural-results)

# 图和匹配算法:Graph and matching algorithms

Augmenting path algorithm for finding a maximum cardinality matching in a bipartite graph 用于在二部图中寻找最大基数匹配的增广路径算法

Ford-Fulkerson algorithm for finding a maximum flow in a network Ford-Fulkerson 算法，用于寻找网络中的最大流

Gale / Shapley algorithm for finding a stable matching in an instance of the stable marriage problem Gale/Shapley 算法，用于在稳定婚姻问题的实例中寻找稳定匹配

Applications of matching problems

Floyd-Warshall algorithm for computing all-pairs shortest paths in a graph 用于计算图中所有对最短路径的 Floyd-Warshall 算法

# 二分图中的匹配：Matching in bipartite graphs

![](/static/2021-10-13-01-26-10.png)

## 最大匹配

![](/static/2021-10-13-01-30-16.png)

* maximum (cardinality) matching
* perfect matching

## 项目分配例子

![](/static/2021-10-13-01-32-55.png)

将该问题建模为二分图

![](/static/2021-10-14-01-03-03.png)

* 利用算法找到最大匹配
* Seek maximum cardinality matching of students to projects in the constructed bipartite graph 在构建的二部图中寻求学生与项目的最大基数匹配

# Naïve algorithm for maximum matching

![](/static/2021-10-14-02-02-53.png)

* naive复杂度 O(n!)

# Towards a faster algorithm O(n^3)

sub-optimal matching -> maximum size matching

* 下面的各种操作需要利用结构 - **augmenting paths 增广路径**
  * 给定一个匹配，有增广路径等定义

![](/static/2021-10-14-02-05-17.png)

---

![](/static/2021-10-14-02-06-19.png)
![](/static/2021-10-14-02-06-30.png)

* 先增到4

---

![](/static/2021-10-14-02-07-12.png)
![](/static/2021-10-14-02-07-20.png)

* 删除替换，增到5，-> perfect matching

# 增广路径：Augmenting paths in graphs

概念 &一些术语term

![](/static/2021-10-14-04-25-12.png)
![](/static/2021-10-14-05-06-38.png)

* **交错路径** alternating path
* **增广路径** augmenting path
  * 基于交错道路
  * 起点，终点为exposed vertex
* **一旦能找到匹配M的增广路径，就能将匹配M大小增加1**
  * 找不到的情况下 - 已经是optimal matching 了？

## 增广路径定理：Augmenting Path Theorem

![](/static/2021-10-14-05-16-18.png)
![](/static/2021-10-14-05-16-28.png)

## 搜索增广路径算法：The augmenting path algorithm

![](/static/2021-10-14-05-25-24.png)

---

增广路径搜索

![](/static/2021-10-14-05-46-19.png)
![](/static/2021-10-14-05-36-52.png)

### 例子

自己画图把，，BFS，，不截图了

![](/static/2021-10-14-05-37-51.png)
![](/static/2021-10-14-05-43-40.png)

* 算法最后返回的是exposed右顶点，然后回溯就能获取整条增广路径
  * 右顶点-> predecessor
  * 左顶点->mate

## 增广匹配M算法：Augmenting the matching along an augmenting path

通过增广路径p增广匹配算法

![](/static/2021-10-14-06-09-00.png)
![](/static/2021-10-14-06-06-11.png)

* 前面通过搜索API获取到增广路径的结束顶点(exposed right vertex)
* mate - 匹配中相邻的顶点
* predecessor - 非匹配，相邻的顶点

# 增广路径完整例子

搜索API & 通过增广路径p进行匹配M的增广

![](/static/2021-10-14-06-11-15.png)

* 0-3，每次left->right找增广路径时，一条边就找到（left exposed->right exposed)，然后将当前匹配M大小+1
* 之后，3开始（蓝色部分）=> 4
  * 2->6->1->7 - 1条增广路径
  * 利用增广路径，将匹配大小|M+1|
* 4的增广路径搜索
  * 5(exposed unvisited)->9->3->8->4->10
  * 利用增广路径，将匹配大小|M+1|
* 图5时，left不存在exposed顶点了，也找不到别的增广路径了

# 最大匹配-增广路径算法分析：Algorithm analysis

![](/static/2021-10-14-09-11-16.png)

* 外循环 p
  * 只要找到了增广路径，就能匹配大小M+1
  * 所以外循环次数最多 = max matching cardinality <= p(左顶点数)

# 增广路径算法总结：Summary

![](/static/2021-10-14-09-39-54.png)

# ===========

# 网络流：Network Flow

![](/static/2021-10-14-20-34-43.png)
![](/static/2021-10-14-20-38-06.png)

* flow conservation constraint - 除了sink, source之外所有顶点，流进=流出
* capacity constraint - 0<=流<=capacity
  * 满足这个约束的，、边, is saturated
* `val(f)` - value of the flow
  * souce流出的总flow = sink流进的总flow

## 例子

![](/static/2021-10-14-20-47-56.png)

## 应用场景

![](/static/2021-10-14-20-52-11.png)

## 我们怎么得到一个max value的flow？

Can we find a flow with even larger value?

![](/static/2021-10-14-21-14-34.png)
![](/static/2021-10-14-21-23-02.png)

* 不一定所有网络都能有饱和流 staturated flow
* 但可以找到最大流 maximum flow

# 网络流-增广路径：Augmenting Paths

![](/static/2021-10-14-21-35-35.png)

* 增广路径，包含G中s-t的边，且路径中的边得满足上面两个条件
* foward edge - **有extra space to push in more flow**

## 例子

![](/static/2021-10-14-21-39-24.png)

## 利用增广路径增广：Augmenting a flow along an augmenting path

![](/static/2021-10-14-21-47-34.png)

## 增广路径定理-证明：theorem

only if证明

![](/static/2021-10-14-22-29-46.png)
![](/static/2021-10-14-22-54-13.png)

* **增广路径中第一条边肯定是 foward【不然没东西压入m unit**】
  * 表示：`e1=(s,v)`
  * 所以增广路径压入m，能保证新的图顶点flow：g(s,v) = f(s,v) + m > f(s,v)
  * 即，val(g) = val(f) + m >val(f)
* m - 能压入的最大额外flow 【bottleneck】
  * ![](/static/2021-10-14-22-23-35.png)
* 每个forward压入m，，，backward-m
  * 然后新的G，flow value+=m
* forward边+m后，仍满足**capacity constraint**
  * ![](/static/2021-10-14-22-32-49.png)
* backward边-m后，仍能满足**capacity constraint**
  * ![](/static/2021-10-14-22-34-43.png)
* **flow conservation constraint**
  * 如顶点v，如不邻接P（增广路径）中路径，，，就满足流守恒
    * 因为不存在改变，，所以，，f守恒了（原这个顶点先相关边的流），那么新的g也守恒
  * 如顶点v，如邻接P（增广路径）中2条路径，，，**分4种情况**
    * 2条都是forward
      * ![](/static/2021-10-14-22-43-00.png)
      * P两个路径，压m后，流进=流出，平衡
      * 其他不属于P，但与v邻接的，因为f中守恒，那么g也守恒，因为这些边不会压入m
    * 1条back，1条forward
      * ![](/static/2021-10-14-22-45-34.png)
      * 仍守恒
* **增广路径中第一条边肯定是 foward【不然没东西压入m unit**】
  * 表示：`e1=(s,v)`
  * 所以增广路径压入m，能保证新的图顶点flow：g(s,v) = f(s,v) + m > f(s,v)
  * 即，val(g) = val(f) + m >val(f)
* <font color="red">最后，能证明，如果存在增广路径，那么当前f就不可能是最大流</font>

---

if 证明

![](/static/2021-10-14-22-56-00.png)

(if): suppose f admits no augmenting path. Need to prove that f is maximum...(if): 假设 f 不存在增广路径。需要证明f是最大流...

* 这里证明不是细节，只是个outline
* 证明细节 --引入最小切

# 网络流-（最小）割：Cuts

A set of edges separating the source from the sink **一组将源与汇分开的边**

* 即，如果将这些边（切中的）移除，不能再从source到sink （informal definition)
* formal definition - 分成两个集合，然后匹配形成切中的边》。。

![](/static/2021-10-14-23-15-32.png)

---

![](/static/2021-10-14-23-18-09.png)

If C is defined with respect to the formal definition, removing the edges of C leaves no path from s to t **如果 C 是相对于形式定义定义的，则删除 C 中的边后不会留下从 s 到 t 的路径（没办法从source到sink**

* 假设，已经移除了所有的割，
  * 最后sink也应该划分到A集合，不然sink这条边应该被移除（因为属于割了）。但是实际上sink就属于B集合，所以存在冲突

# 切和流的关系：Relationship between flows and cuts

最小切 minimum cut

![](/static/2021-10-23-04-12-17.png)

![](/static/2021-10-23-04-19-37.png)

证明，如果f不存在增广路径，val(f)=cap(C)，f是最大流

![](/static/2021-10-23-05-31-32.png)

# 求最大流:Finding a maximum flow

**Ford-Fulkerson algorithm** (basis - augmenting theorem)

* 求最大流：求增广路径，不停增广，直到最大流
  * 重点是怎么**求增广路径 - 残留网络residual graph/network**

![](/static/2021-10-23-05-38-39.png)

# 残留网络：The residual graph

![](/static/2021-10-23-07-27-34.png)

* <font color="deeppink">残留网络中s~t的路径 对应 原网络中的一条增广路径</font>

## 例子

![](/static/2021-10-23-07-31-33.png)

# 求最大流算法：Ford-Fulkerson Algorithm

![](/static/2021-10-23-07-37-03.png)

## 分析

![](/static/2021-10-23-07-44-52.png)

## worst-case example

最坏情况下，每次flow+1，直到max （原ford-fulkerson algo

![](/static/2021-10-23-08-14-11.png)

# 改进Ford-Fulkerson: Improving the worst case

Edmonds and Karp Heuristic

![](/static/2021-10-23-08-18-15.png)

# 残留网络完整例子

![](/static/2021-10-23-08-19-28.png)
![](/static/2021-10-23-08-22-55.png)
![](/static/2021-10-23-08-23-40.png)

![](/static/2021-10-23-08-23-54.png)
![](/static/2021-10-23-08-24-04.png)
![](/static/2021-10-23-08-24-12.png)
![](/static/2021-10-23-08-24-23.png)
![](/static/2021-10-23-08-24-56.png)

![](/static/2021-10-23-08-25-49.png)
![](/static/2021-10-23-08-26-21.png)

# 二分图最大匹配->网络流：A faster algorithm for maximum matching

![](/static/2021-10-23-09-37-31.png)
![](/static/2021-10-23-09-38-11.png)

# ===========

# 稳定匹配问题：Stable matching problems

![](/static/2021-10-23-09-48-35.png)

* 带偏好的preference list
* block pair: M中没有被给到更想要的匹配

## 例子

![](/static/2021-10-23-10-01-36.png)

# 检测匹配稳定性算法：Stability Checking

![](/static/2021-10-23-10-08-35.png)

# 寻找稳定匹配算法:Finding a stable matching

给定一个实例，总是存在一个稳定匹配

![](/static/2021-10-23-21-51-50.png)

* Gale/Shapley algorithm

# Gale/Shapley algorithm ("man-oriented" version)

man-oriented

![](/static/2021-10-23-21-59-40.png)

# Gale/Shapley algorithm算法正确性: Correctness of the Gale/Shapley Algorithm

![](/static/2021-10-23-22-37-07.png)

这个算法是否每次都能输出稳定匹配？

![](/static/2021-10-23-22-41-06.png)

# The “man-optimal” property

![](/static/2021-10-23-22-46-30.png)

# 实现-Gale/Shapley algorithm：Implementation of the Gale/Shapley algorithm

需要constant time: w决定选择哪个m（w此时可能已经分配了m'，需要比较m和m'更偏好哪个）

* 如果线性搜索 - N^2

![](/static/2021-10-23-22-56-04.png)

# 分析-Gale/Shapley algorithm：Analysis of the Gale/Shapley algorithm

![](/static/2021-10-23-22-58-46.png)

# Lecture 8 - part 5============

不考，，

# 匹配算法应用：Applications of Matching Problems/Algorithms

![](/static/2021-10-28-05-10-50.png)

# Hospitals/Residents Problem (HR)

匹配问题的扩展

![](/static/2021-10-28-05-28-56.png)

* ci - 每个医院的容量

## 例子

![](/static/2021-10-28-05-35-46.png)

## Blocking Pair

![](/static/2021-10-28-05-45-35.png)

## 非稳定例子

![](/static/2021-10-28-05-47-51.png)

![](/static/2021-10-28-05-48-12.png)

![](/static/2021-10-28-05-48-35.png)

## 稳定例子

![](/static/2021-10-28-05-59-21.png)

# 算法和结构结果：Algorithmic and structural results

![](/static/2021-10-28-07-19-42.png)