# Graph & Graph Algo 1

* [Graph & Graph Algo 1](#graph--graph-algo-1)
  * [Graph Basics](#graph-basics)
  * [图表示：Graph Representations](#图表示graph-representations)
    * [邻接表实现：无向图](#邻接表实现无向图)
  * [图搜索/遍历算法：Graph search & traversal algo](#图搜索遍历算法graph-search--traversal-algo)
    * [深度优先算法：Depth first search](#深度优先算法depth-first-search)
    * [广度优先算法：Breadth first search](#广度优先算法breadth-first-search)
  * [带权图：weighted graphs](#带权图weighted-graphs)
    * [最短路径：Shortest path](#最短路径shortest-path)
    * [迪杰斯特拉算法：Dijkstra’s algorithm](#迪杰斯特拉算法dijkstras-algorithm)
    * [迪杰斯特拉算法-边松弛：Edge Relaxation](#迪杰斯特拉算法-边松弛edge-relaxation)

## Graph Basics

无向图

![](/static/2020-11-10-12-20-22.png)

有向图

![](/static/2020-11-10-12-20-40.png)

* u与v邻接(u,v)
  * u is ajacent to v
  * v is ajacent from u

路径 path

![](/static/2020-11-10-12-22-43.png)

环 cycle

![](/static/2020-11-10-12-25-52.png)

## 图表示：Graph Representations

![](/static/2020-11-10-12-26-32.png)

### 邻接表实现：无向图

![](/static/2020-11-10-12-32-06.png)

类设计

* 邻接节点`AdjListNode`
  * 边表节点
* 顶点`Vertex`
  * 顶点表节点
  * **包含表示邻接边的链表**
* 图`Graph`
  * 包含顶点表array

```java
/* 边表节点 */
public class AdjListNode{
    private int vertexIndex;

    public AdjListNode(int i){
        vertexIndex=i;
    }
    public int getVertexIndex(){
        return vertexIndex;
    }
    public int setVertexIndex(int i){
        vertexIndex=i;
    }
}
```

```java
/* 顶点 */
public class Vertex{
    private int index;
    private LinkedList<AdjListNode> adjList;//邻接表
}
public Vertex(int i){
    index =i;
    adjList = new LinkedList<AdjListNode>();
}

public int getIndex(){
    return index;
}

public int setIndex(int i){
    index=i;
}

public LinkedList<AdjListNode> getAdjList(){
    return adjList;
}

//邻接表添加边，
public void addToAdjList(int j){
    adjList.addLast(new AdjListNode(j));
}

// 返回节点度数

public int vertexDegree(){
  return adjList.size();
}
```

```java

/* Graph */

public class Graph{
    private Vertex[] vertices;//顶点表
    private int numVertices=0;

    public Graph(int n){
        numVertices = n;
        vertices = new Vertex[n];
        for (int i=0;i<n; i++) vertices[i]= new Vertex(i);
    }

    public int size(){
        return numVertices;
    }
}
```

## 图搜索/遍历算法：Graph search & traversal algo

![](/static/2020-11-10-14-14-31.png)

例子

* 网络爬虫，爬超文本文档
  * 顶点- 超文本文档
  * 边（u，v）- u包含v的超链接

高效遍历，时间复杂度

* `o(|V|+|E|)`

### 深度优先算法：Depth first search

![](/static/2020-11-10-14-19-38.png)

1. 沿着未访问点的路径一直访问，直到路径上再无为访问的点
2. 返回上一个有其他邻接未访问的顶点，重复1，2
3. 直到所有点都被访问

### 广度优先算法：Breadth first search

![](/static/2020-11-10-14-24-21.png)
![](/static/2020-11-10-14-24-54.png)

1. 选中每个顶点，依此访问其边表中所有顶点，同时入队
2. 每次从队列队头取出顶点，访问其未访问过的邻接点
   1. 被访问过的顶点 `visited=1`
3. 直到所有顶点被处理

## 带权图：weighted graphs

![](/static/2020-11-10-14-29-15.png)
![](/static/2020-11-10-14-46-12.png)

* 每条边有权重
  * cost
  * length
  * capacity
  * 权重不存在 - `∞/或数据类型能表示的最大值`

### 最短路径：Shortest path

![](/static/2020-11-10-15-02-37.png)

* 使得u，v顶点之间路径加权和最短

### 迪杰斯特拉算法：Dijkstra’s algorithm

![](/static/2020-11-10-15-28-27.png)

* 找到某顶点`u`到所有其他顶点的最短路径
* 维护集合`S`：保存已知`u`在最短路径中的其他顶点【已经纳入最短路径中的顶点】
  * <font color="red">分为两部分：纳入顶点部分，未处理顶点部分</font>
  * 初始，只包含`u`（u-u，最短路径0）
  * 最后，包含所有顶点 - 所有最短路径都已知
* 每个顶点`v`
  * 有标签`d(v)`代表`(u,v)`**最短路径长度 length of a shortest path**
    * `v`必须在`S`集合中
  * `d(v)=∞`，如果没有路径
  * `d(v)=(u,v)最短路径长度`，如果`v`在集合`S`中

🍊 dijkstra algo变体

* 如果`v`在集合`S`中，且`w`不在
  * 则`(u,w)`最短路径长度至少`(u,v)`的最短路径长
  * **即，`(u,w)`边的加权和至少为`d(v)`**（u~v之间最短路径经过的顶点已经包括在S中了，剩下未纳入的点需要的路径至少是目前d(v)已有的最短路径）

### 迪杰斯特拉算法-边松弛：Edge Relaxation

Dijkstra算法中的**松弛过程是指更新连接到顶点v的所有顶点的成本，如果可以通过包含通过v的路径来改善这些成本**

* 一开始，最短路径的成本被高估了(更新过程中，未完成)，比作拉长的弹簧。
* 当发现较短的路径时，估计成本就会降低，成本也就放松了。最终，找到了最短的路径，如果有的话，成本（看作弹簧）就放松到了它的静止长度

---

![](/static/2020-11-10-17-57-44.png)

* 假设顶点`v`&`w`都未处理，不在集合`S`中
  * `(u,v)`最短路径 - `d(v)`
  * `(u,w)`最短路径 - `d(w)`
* 现假设，`v`加入`S`（纳入最短路径涉及的顶点）
  * 则，边`(v,w)`权重`wt(e)`
* **求，`(u,w)`最短路径？**，两种情况
  * 包括上次纳入顶点`v`， = `d(v) + wt(e)`
  * 不包括上次纳入顶点`v`---原来路径 = `d(w)`

🍊 因此到`w`最短路径，更新应符合

* `d(w) = min{d(w), d(v) + wt(e)}`

伪码

![](/static/2020-11-10-18-06-33.png)
![](/static/2020-11-10-18-14-06.png)
![](/static/2020-11-10-18-16-16.png)
![](/static/2020-11-10-18-22-10.png)