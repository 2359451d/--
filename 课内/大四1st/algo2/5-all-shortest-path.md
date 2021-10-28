# Content

* [Content](#content)
* [全顶点对的最短路径：All-Pairs Shortest Paths](#全顶点对的最短路径all-pairs-shortest-paths)
* [迪杰斯特拉-最短路径：Single-source shortest paths](#迪杰斯特拉-最短路径single-source-shortest-paths)
  * [负边权重错误 & 复杂度:Negative edge weights](#负边权重错误--复杂度negative-edge-weights)
* [单源最短路径 - Single-source shortest paths](#单源最短路径---single-source-shortest-paths)
* [Floyd-Warshall algorithm](#floyd-warshall-algorithm)
  * [Constructing D*](#constructing-d)
  * [关键性质：The key property](#关键性质the-key-property)
  * [实现](#实现)
  * [例子](#例子)

# 全顶点对的最短路径：All-Pairs Shortest Paths

![](/static/2021-10-28-07-50-50.png)

* 最大化profit,最小化loss

# 迪杰斯特拉-最短路径：Single-source shortest paths

![](/static/2021-10-28-08-01-40.png)
![](/static/2021-10-28-20-13-43.png)

## 负边权重错误 & 复杂度:Negative edge weights

![](/static/2021-10-28-20-23-10.png)

# 单源最短路径 - Single-source shortest paths

可以解决负权重

![](/static/2021-10-28-20-40-55.png)

* Bellman-Ford algorithm
* Floyd-Warshall algorithm
  * 负边环存在时需要注意，不过可以检测出来【如果不存在，则能保证这个算法得到的结果一定正确

# Floyd-Warshall algorithm

1.Floyd算法是求任意两点之间的距离，是**多源最短路**，而Dijkstra(迪杰斯特拉)算法是求一个顶点到其他所有顶点的最短路径，是单源最短路。

2.Floyd算法属于动态规划，我们在写核心代码时候就是相当于推dp状态方程，Dijkstra(迪杰斯特拉)算法属于贪心算法。

3.Dijkstra(迪杰斯特拉)算法时间复杂度一般是o(n^2),Floyd算法时间复杂度是o(n^3),Dijkstra(迪杰斯特拉)算法比Floyd算法块。

4.Floyd算法可以算带负权的，而Dijkstra(迪杰斯特拉)算法是不可以算带负权的。并且Floyd算法不能算负权回路。

## Constructing D*

![](/static/2021-10-28-20-56-13.png)

## 关键性质：The key property

![](/static/2021-10-28-21-13-00.png)
![](/static/2021-10-28-21-11-57.png)

* 初始化阶段，直接邻接的不用中间顶点，直接更新权重

## 实现

![](/static/2021-10-28-21-33-34.png)

* 可以O(n^3)时间实现所有顶点对之间的实际最短路径 actual shortest paths between all pairs of vertices

## 例子

![](/static/2021-10-28-21-48-19.png)

k=1
![](/static/2021-10-28-21-49-41.png)
![](/static/2021-10-28-21-49-53.png)

k=2
![](/static/2021-10-28-21-53-01.png)
![](/static/2021-10-28-21-53-24.png)
![](/static/2021-10-28-21-52-45.png)

k=3
![](/static/2021-10-28-21-54-36.png)

k=4
![](/static/2021-10-28-21-55-48.png)

d*
![](/static/2021-10-28-21-56-37.png)

