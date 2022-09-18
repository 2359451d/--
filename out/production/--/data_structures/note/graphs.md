# Graphs

图,自用扩展

* [Graphs](#graphs)
  * [Definition & Related Concepts](#definition--related-concepts)
  * [图存储方式](#图存储方式)
    * [邻接矩阵: 存储图](#邻接矩阵-存储图)
    * [邻接表: 存储图](#邻接表-存储图)
  * [图的应用: 搜索算法](#图的应用-搜索算法)
    * [邻接表实现: 无向图](#邻接表实现-无向图)
    * [BFS: 广度优先搜索](#bfs-广度优先搜索)
    * [DFS: 深度优先搜索](#dfs-深度优先搜索)

## Definition & Related Concepts

🍊 定义

![](/static/2020-10-16-19-11-32.png)

* 图
  * 与树相比,更为复杂的**非线性结构**
* **顶点 V**
  * **图中每个元素**
* **边 E**
  * 图中一个**顶点可以与其他顶点建立关系,这种关系叫做边**
* **度数 Degree**
  * <font color="red">某顶点相连有多少条边,度数-跟顶点相连接的边的条数</font>
  * <font color="blue">无向图的概念, 有向图概念为入度&出度</font>

🍊 分类

![](/static/2020-10-16-19-17-55.png)
![](/static/2020-10-16-19-11-32.png)
![](/static/2020-10-16-19-22-30.png)

* **有向图 directed**
  * **有向图度分类**
    * **入度** In-degree: 表示有多少条边指向这个顶点
    * **出度** Out-degree: 表示有多少条边以该顶点为起点指向其他顶点
* **无向图 undirected**
* **带权图 weighted**
  * **每条边带有一个权重**

## 图存储方式

图有很多中存储形式包括：

* **邻接矩阵**
* **邻接表**
* 十字链表
* 邻接多重表
* 边集数组

### 邻接矩阵: 存储图

底层可看为**二维数组**

![](/static/2020-10-16-19-30-01.png)

* **无向图**
  * i->j, `A[i][j]=1` & `A[j][i]=1`
* **有向图**
  * i->j, `A[i][j]=1`
  * j->i, `A[j][i]=1`
* **带权图**
  * <font color="blue">数组中存储相应权重</font>

🍊 适用场景

* **用于稠密的图**
  * **可以快速定位到指定的边**
* 如果是稀疏的图
  * **浪费存储空间**

### 邻接表: 存储图

邻接表 Adjacency List

![](/static/2020-10-16-19-41-05.png)

* 解决邻接数组浪费空间问题
* **性能低,时间成本高(链表)**
* <font color="red">类似哈希表,解决冲突</font>
  * 如果链表过长,为了提高查找效率,可以转换成AVL查找树/红黑树/跳表

🍊 邻接表形成步骤

![](/static/2020-10-16-19-44-32.png)

* **存储图中顶点**
  * 一维数组
* **存储边指向关系**
  * 链表

## 图的应用: 搜索算法

以下搜索算法 - **都基于图,但也可以用于树**

* **深度优先搜算算法**
* **广度优先搜索算法**
* <font color="red">从一个顶点出发,到另一个顶点的路径</font>

### 邻接表实现: 无向图

![](/static/2020-10-16-20-52-55.png)

```java
/*
 * 采用邻接表实现存储无项图
 * */
public class UndirectedG {
    //途中顶点数
    private int points;
    // 邻接表, 线性数组中存储链表<Integer>
    private LinkedList<Integer> ajacencyList[];

    public UndirectedG(int points) {
        this.points = points;
        //初始化数组
        this.ajacencyList = new LinkedList[this.points];
        //    初始化数组中每个槽位的链表
        for (int i = 0; i < this.points; i++) {
            ajacencyList[i] = new LinkedList<>();
        }
    }

    //邻接表中添加V: index - 顶点值
    // 无向图
    public void addPoint(int s, int t) {
        ajacencyList[s].add(t);//linkedlist.add(t)
        ajacencyList[t].add(s);
    }

    @Test
    public static void main(String[] args) {
        UndirectedG g = new UndirectedG(8);//points = 8
        g.addPoint(0,1);
        g.addPoint(0,3);
        g.addPoint(1,2);
        g.addPoint(1,4);
        g.addPoint(2,5);
        g.addPoint(4,4);
        g.addPoint(4,5);
        g.addPoint(4,6);
        g.addPoint(6,7);
        g.addPoint(7,5);
        System.out.println(g);
    }
}
```

### BFS: 广度优先搜索

广度优先搜索算法（Breadth-First-Search）

![](/static/2020-10-16-20-52-55.png)
![](/static/2020-10-16-20-59-27.png)

* 是一种**图形搜索算法**
* 地毯式层层推进的搜索策略
  * 先查找离起始顶点最近的,内往外搜索

### DFS: 深度优先搜索

深度优先遍历(Depth First Search)

* 深度优先遍历的策略就是**首先访问
第一个邻接结点，然后再以这个被访问的邻接结点作为初始结点，访问它的第一个邻接结点**
* **每次访问完当前结点后首先访问当前结点的第一个邻接节点**
* **递归**

