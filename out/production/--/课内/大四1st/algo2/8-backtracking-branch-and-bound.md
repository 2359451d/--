# Content

* [Content](#content)
* [Overview](#overview)
* [Review of NP-completeness concepts](#review-of-np-completeness-concepts)
  * [NP Complete](#np-complete)
  * [](#)
* [==============](#-1)
* [Constant-factor approximation algorithms](#constant-factor-approximation-algorithms)
* [优化问题：Optimisation Problems](#优化问题optimisation-problems)
  * [例子](#例子)
* [优化问题的决策版本：Decision version of an optimisation problem](#优化问题的决策版本decision-version-of-an-optimisation-problem)
* [NPO & PO问题：The Classes NPO and PO](#npo--po问题the-classes-npo-and-po)
* [近似算法：Approximation algorithms](#近似算法approximation-algorithms)
* [MAX-SAT近似算法：Approximation algorithm for MAX-SAT](#max-sat近似算法approximation-algorithm-for-max-sat)
* [APX类NPO问题](#apx类npo问题)
* [近似算法-Travelling Salesman Problem (TSP)](#近似算法-travelling-salesman-problem-tsp)

# Overview

![](/static/2021-11-24-15-03-05.png)

* backtracking and branch-and-bound
  * solving hard problems optimally
* pseudo-polynomial-time algorithms
  * small input - can be solved in polynomial time
  * worst case when large input - exponential time
* Constant-factor approximation algorithms
  * can be solved approximately rather than optimally
  * can approximate with some constant factor of optimal
* Polynomial-time approximation schemes
  * can be solved approximately rather than optimally
  * can approximate the problem arbitrarily close to optimal
* Inapproximability results
  * prove negative results about the difficulty of approximating a given problem
  * give us thresholds, limits to which a problem is approximal using a polynomial-time algorithm

# Review of NP-completeness concepts

![](/static/2021-11-25-18-41-39.png)

* P
  * 多项式时间可解的决策问题，（take some inputs, output yes or no)
* NP
  * 给定一个instance，存在一个非决定性的算法，有多种可能的执行
  * 给定问题π的一个实例x，
    * 如果x是yes-instance。那么非决定性算法的其中一些执行**多项式时间会输出**yes
    * 如果x是no-instance，则非决定性算法不存在有执行在**多项式时间内会输出**yes
  * **非决定性算法**通常涉及**guessing & verification stage**
  * 即决策问题π的yes-instance实例，存在某certificate（某sequence of vertexes）能在多项式时间内验证。checking阶段即验证这个顶点序列能不能比如correspond to a hamiltonian cycle

---

P包含于NP，P?=NP

![](/static/2021-11-25-18-41-30.png)

* 即，P是多项式时间内可以用决定性算法解决的。决定性算法可以看为非决定性算法的一种特例，**省去了guessing stage**
* 通常认为，P！=NP
  * 因为P!=NP & P包含于NP, 所以 NP不包含于P。即，NP中不是每个问题都多项式时间内可解，尽管NP中存在某些问题多项式时间内可以解(e.g., determine whether a bipartite graph has a perfect matching)

## NP Complete

![](/static/2021-11-26-10-50-47.png)

假设P!=NP，我们能分辨属于NP但不属于P的问题吗（即NP中多项式时间内不能解的问题，分辨NPC问题?）

* Theory of NP-Completeness解决该问题

称π可以规约为π' - π is reducible to π'

* 首先属于class NP
* 且**满足多项式时间规约**
  * 如果π能多项式时间内转换为π'，满足π和π'有相同答案（当且仅当j是yes-answer of π'时，i是yes answer of π）
* **性质**
  * 如果π可以规约为π'，且π'∈P，则π∈P
    * 即，如果想得到π的一个多项式时间算法，可以先将其规约为π'，然后证明π'多项式时间内可解

NPC定义 - NP中某问题实例π是NP完全的， A problem π in NP is NP-complete if

* 如果存在多项式规约：π'(NP中所有问题实例π' instance)->π
* 即
  * 如果π∈P并且π是NPC问题，则P=NP。（如果 π是NPC问题，并且能找到一个多项式时间内的算法，那么NP中每个问题都能多项式时间内求解）
* **如P!=NP，并且π是NPC，则π∉P(π多项式时间内不可解**)
  * 不然就是上面那条性质了
* **如果π是NPC问题，那么很难找到一个算法能有效解决(多项式时间内)π问题的所有实例**
  * 如果存在一个算法能有效解决，那这个算法一定得解决π中所有Instances

## 

# ==============

# Constant-factor approximation algorithms

# 优化问题：Optimisation Problems

![](/static/2021-11-26-11-38-09.png)

## 例子

![](/static/2021-11-26-12-03-08.png)

* 计算max相对来说computationally hard

# 优化问题的决策版本：Decision version of an optimisation problem

注意决策版本也就是一个决策问题

![](/static/2021-11-26-12-25-45.png)

* MAX-SAT的决策版本是NPC问题

# NPO & PO问题：The Classes NPO and PO

![](/static/2021-11-26-12-39-30.png)

* NPO
  * 多项式可解的属于PO
  * 其他NPO多项式时间不可解

# 近似算法：Approximation algorithms

![](/static/2021-11-26-13-02-40.png)

例子
![](/static/2021-11-26-13-06-44.png)

# MAX-SAT近似算法：Approximation algorithm for MAX-SAT

![](/static/2021-11-26-13-19-12.png)

证明 - 近似算法返回的truth assignement f 至少能满足一半的(m)个clauses同时成立

![](/static/2021-11-26-13-31-51.png)

![](/static/2021-11-26-13-34-36.png)

* 以上能证明MAX-SAT存在2-approximation algorithm

# APX类NPO问题

![](/static/2021-11-26-13-53-57.png)

# 近似算法-Travelling Salesman Problem (TSP)

