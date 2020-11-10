# NP Completeness

NP问题

* [NP Completeness](#np-completeness)
  * [Some Effient Algorithms We have seen](#some-effient-algorithms-we-have-seen)
  * [Recall: Eulerian cycle problem](#recall-eulerian-cycle-problem)
  * [Recall: Hamiltonian Cycle problem](#recall-hamiltonian-cycle-problem)

## Some Effient Algorithms We have seen

常见多项式时间复杂度

![](/static/2020-11-09-22-50-06.png)

* polynomial-time algorithms
  * worst-case complexity `O(n^c)` for some constant `c`

## Recall: Eulerian cycle problem

欧拉回路 Euler cycle - P

![](/static/2020-11-09-22-54-24.png)

* 无向图`G`
  * 是否满足欧拉回路：**不存在重复边的路径**

🍊 当且仅当每个顶点的度数为偶数时，连通的无向图存在欧拉回路

* a **connected undirected graph** has an euler cycle
  * if and only if each vertex has even degree
* 由此可以验证，`G`是否存在欧拉回路
  * 邻接矩阵形式
    * `O(n^2)` time 内验证
  * 邻接表形式
    * `O(m+n)` time 内验证
    * 即`O(|E|+|V|)`

## Recall: Hamiltonian Cycle problem

哈密顿回路 Hamiltonian cycle - NP（complete）

![](/static/2020-11-09-23-02-04.png)

* 无向图`G`是否存在哈密顿回路
  * 在一个回路中，除了经过初始结点两次以外，**恰好经过每个结点一次**，则称此回路为哈密顿回路
  * 哈密顿回路中每个结点都为偶结点

\\\