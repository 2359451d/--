# Content

* [Content](#content)
* [Assessment](#assessment)
* [Course structure](#course-structure)
* [Complexity](#complexity)
* [Course Structure](#course-structure-1)
* [计算几何：Geometric Algorithms](#计算几何geometric-algorithms)
* [几何表示：Geometric Objects](#几何表示geometric-objects)
* [代码几何表示：Geometric Representations - java/pseudo code](#代码几何表示geometric-representations---javapseudo-code)
* [线段相交问题：Determining if two line segments intersect](#线段相交问题determining-if-two-line-segments-intersect)
  * [除法解法](#除法解法)
  * [子问题划分](#子问题划分)
  * [onOppositeSides test](#onoppositesides-test)
  * [boundingBox test](#boundingbox-test)
  * [合并子问题](#合并子问题)
  * [多边形建立问题：Constructing a Simple Polygon](#多边形建立问题constructing-a-simple-polygon)
  * [naïve method](#naïve-method)
  * [circular scan](#circular-scan)
  * [改进circular scan refine](#改进circular-scan-refine)
  * [pseudocode - simplePolygon](#pseudocode---simplepolygon)
  * [多边形建立算法分析：Analysis of algorithm for constructing simple polygon](#多边形建立算法分析analysis-of-algorithm-for-constructing-simple-polygon)
    * [共线情况](#共线情况)
    * [简化](#简化)
* [求凸包问题：Finding the Convex Hull](#求凸包问题finding-the-convex-hull)
  * [凸包可以不由集合中所有点组成](#凸包可以不由集合中所有点组成)
  * [凸包性质：Properties of the convex hull](#凸包性质properties-of-the-convex-hull)
  * [Graham扫描算法:Graham scan algorithm](#graham扫描算法graham-scan-algorithm)
    * [例子](#例子)
    * [何时排除？](#何时排除)
  * [Pseudocode-Graham扫描算法](#pseudocode-graham扫描算法)
  * [复杂度：Complexity of grahamScan](#复杂度complexity-of-grahamscan)
* [凸包应用](#凸包应用)
  * [机器人运动](#机器人运动)
  * [找最远点对：Furthest Pair of Points](#找最远点对furthest-pair-of-points)
    * [旋转卡壳:Rotating calipers method](#旋转卡壳rotating-calipers-method)
    * [其他](#其他)

# Assessment

* 70% exam
* 20% practical exercise
  * late OCTO
* 10% in-class quizzes
  * 2-3 questions per lecture
  * Friday 1300

# Course structure

* lecutre - 2 per week
* tutorial - THUR 13-14 (recorded)

# Complexity

提及efficient algorithm -> polynomial-time algo

![](/static/2021-10-02-20-53-07.png)

Polynomial-time algo

![](/static/2021-10-02-20-57-48.png)

non polynomial-time algorithm (exponential-time algorithm)

# Course Structure

计算几何 Geometric algorithm

![](/static/2021-10-03-00-32-10.png)

图&匹配算法

![](/static/2021-10-03-00-34-42.png)

文本，串算法

![](/static/2021-10-03-00-39-37.png)

* longest common subsequence

algo for hard problems

![](/static/2021-10-03-00-41-33.png)

# 计算几何：Geometric Algorithms

Determining whether two line segments intersect **判断两条线段是否相交（避免除法**

• Constructing a simple polygon from a set of points **从一组点构造一个简单的多边形**

• Finding the convex hull of a set of points **找到一组点的凸包**

* the smallest convex polygon that includes them all 包含一组点的最小凸多边形

• Finding a closest pair among a set of points **在一组点中找到最接近的一对**

• Finding all the intersections of a set of horizontal and vertical line segments **查找一组水平和垂直线段的所有交点**

# 几何表示：Geometric Objects

![](/static/2021-10-03-00-47-28.png)
![](/static/2021-10-03-00-48-54.png)
![](/static/2021-10-03-00-51-49.png)

* line - ordered
* simple polygon
  * 闭合，无自我相交
* polygon何时称为convex （polygon）
  * polygon内两点，其中两点连接形成的线段完全在polygon内部
* **另一种判断convex的方法**
  * 顺时针走，是否每次方向都一样

# 代码几何表示：Geometric Representations - java/pseudo code

注意点集合(array)中元素顺序

![](/static/2021-10-03-00-54-35.png)

# 线段相交问题：Determining if two line segments intersect

## 除法解法

![](/static/2021-10-03-03-04-08.png)
![](/static/2021-10-03-03-03-35.png)
![](/static/2021-10-03-03-01-21.png)

* 数学方法解，，涉及除法运算，存在浮点数精确度问题
  * 当两个线段非常接近平行，但并不平行，这种算法解可能误判为平行，并且不存在交点

## 子问题划分

![](/static/2021-10-03-03-07-23.png)

1. Determine whether points p, q lie on opposite sides of the line through points r and s, and whether points r, s lie on opposite sides of the line through points p and q (onOppositeSides tests) 确定点 p、q 是否通过点 r 和 s 直线的两侧，以及点 r、s 是否位于通过点 p 和 q 直线的两侧（onOppositeSides tests）
2. Determine whether the smallest rectangles whose sides are parallel to the x and y axes containing each of p—q and r—s intersect (boundingBox test) 确定边平行于 x 和 y 轴的包含 p-q 和 r-s 中的**每一个的最小矩形是否相交**（boundingBox 测试）

## onOppositeSides test

只运用 乘法减法，避免了除法

![](/static/2021-10-03-03-11-58.png)
![](/static/2021-10-03-03-16-35.png)

* 判断a,b是否分别在直线l的两边。
  * 带入a,b坐标，两情况方程左边符号不同时才成立。
  * a: LHS<0，顺时针
  * b：LHS>0，逆时针
  * 其中一个为0时，也成立（正好在直线l上

## boundingBox test

The bounding box of a line segment p—q is the smallest rectangle containing p—q whose sides are parallel to the x and y axes **线段 p—q 的边界框是【包含线段 p—q 】【平行于 x 轴和 y 轴】的【最小矩形**】

![](/static/2021-10-03-03-20-05.png)

## 合并子问题

如何判断两线段pq,rs相交？

![](/static/2021-10-03-03-23-43.png)

* p,q在直线rs两边
* r,s在直线pq两边
* 且线段pq,rs的boundingbox相交

---

![](/static/2021-10-03-03-37-56.png)

* 思考：为什么还需要boudningBox test来进一步判断两线段是否相交？
  * 特殊情况

## 多边形建立问题：Constructing a Simple Polygon

simple polygon - 不存在任何两个边相交，形成边界boundary

## naïve method

naive建立simple polygon

任意顺序连接，p1连接p10时出现intersection

![](/static/2021-10-03-03-42-20.png)

## circular scan

![](/static/2021-10-03-03-46-21.png)
![](/static/2021-10-03-03-45-27.png)
![](/static/2021-10-03-03-49-24.png)

* 选取点`p`作为circular scan/sweep的支点，然后（vertical line）逆时针开始scan，顺序标记每个点
* 最后根据顺序连接每个点
* **形成了intersection，not a simple polygon**

## 改进circular scan refine

![](/static/2021-10-03-03-51-58.png)
![](/static/2021-10-03-03-52-38.png)
![](/static/2021-10-03-03-54-15.png)

* 支点pivot选择重要，选取Input set中x坐标最大，（y坐标最小的

## pseudocode - simplePolygon

![](/static/2021-10-03-04-24-55.png)

---

输入 - points set(array)

输出 - reordered points set

![](/static/2021-10-03-03-58-09.png)

* angle作用

线性搜索点集合，找到最大x坐标的点

运用三角函数，为点集合中每个点计算角度angle （根据denominator，y坐标差异进行分类讨论）

![](/static/2021-10-03-04-19-45.png)

* tanC = x2-x1/y2-y1。然后算反函数(arc tan)得到C
  * numerator-分子：x2-x1
  * denominator分母：y2-y1

最后，根据每个点的角度，来排序点集合

## 多边形建立算法分析：Analysis of algorithm for constructing simple polygon

![](/static/2021-10-03-04-32-55.png)

### 共线情况

![](/static/2021-10-03-04-27-58.png)
![](/static/2021-10-03-04-31-20.png)

* 需要考虑 在点共线情况下（与支点p形成的角度相同）的顺序
  * 最近的点，先被标记

### 简化

不需要计算arctan

* 斜率就能决定sort order

不需要计算距离平方根，

* 计算平方就足够

# 求凸包问题：Finding the Convex Hull

凸多边形 convex polygon - 某多边形，其中任意两点的线段也在该多边形内

The convex hull of a set Of points (in the plane) is the smallest convex polygon that contains the points **一组点（在平面中）的凸包是包含所有点的[最小]凸多边形**

* 最小 - > 边长度最小，or面积最小

![](/static/2021-10-03-04-53-56.png)

## 凸包可以不由集合中所有点组成

如果包含P中所有点，以下都不为凸包

![](/static/2021-10-03-04-55-14.png)
![](/static/2021-10-03-04-55-59.png)
![](/static/2021-10-03-04-56-10.png)

排除3，可以形成凸包

![](/static/2021-10-03-04-56-37.png)

## 凸包性质：Properties of the convex hull

![](/static/2021-10-03-04-59-06.png)

## Graham扫描算法:Graham scan algorithm

类似建立多边形的算法，选择支点

* 最大x坐标
  * 如果存在多个，就进一步选取y最小的坐标

Scan the remaining points in order of angle to the pivot 按与枢轴的角度顺序扫描剩余的点 （vertical line, anti-clockwise)

For each point, include it in temporary hull, possibly excluding one or more of its predecessors 对于每个点，将其包含在临时凸包中，可能排除一个或多个其前级

### 例子

![](/static/2021-10-03-05-02-44.png)

![](/static/2021-10-03-05-04-08.png)

* p5-p6,时，方向从left->right
  * p5需要被排除

![](/static/2021-10-03-05-04-58.png)

* p3-**p4-p6**时，方向left->right
  * p4被排除

![](/static/2021-10-03-05-18-31.png)

* 结束

---

### 何时排除？

![](/static/2021-10-03-05-10-40.png)
![](/static/2021-10-03-05-11-49.png)

## Pseudocode-Graham扫描算法

![](/static/2021-10-03-05-48-18.png)

* 先把前3个点放入convex hull，因为根据多边形建立算法，不可能最后两点存在turn right情况（逆时针扫）
* m - 当前临时凸包中含有的点-1（除支点pivot之外）

---

![](/static/2021-10-03-05-27-07.png)
![](/static/2021-10-03-05-29-18.png)

* 此步骤会循环判断，直到排除到某个点mx与当前点k，，且mx与mx-1的夹角<180 (left turn)

## 复杂度：Complexity of grahamScan

![](/static/2021-10-03-05-53-11.png)
![](/static/2021-10-03-05-53-01.png)

# 凸包应用

## 机器人运动

![](/static/2021-10-03-05-55-30.png)

* 先看，是否s-t线段与障碍物p(polygon)相交
  * 无相交则，机器人可以直接移动
* 不能直接移动，计算s,t与多边形p边界点形成的凸包
* 计算凸包最短路径
  * 顺时针s-t路径
  * 逆时针s-t路径

## 找最远点对：Furthest Pair of Points

![](/static/2021-10-03-06-02-13.png)

* 旋转卡壳中，不是所有凸包上点的距离都被测量，
  * 不然就是O(n^2)

### 旋转卡壳:Rotating calipers method

![](/static/2021-10-03-06-04-59.png)

![](/static/2021-10-03-06-05-16.png)
![](/static/2021-10-03-06-05-26.png)

![](/static/2021-10-03-06-05-38.png)
![](/static/2021-10-03-06-06-37.png)

* l1首先碰到凸包上点，l1 pivot从p变为r
* l2 pivot remains q
* record the new distance
  * distance of (r,q)

![](/static/2021-10-03-06-07-16.png)
![](/static/2021-10-03-06-07-23.png)

---

![](/static/2021-10-03-06-07-31.png)
![](/static/2021-10-03-06-07-38.png)
![](/static/2021-10-03-06-08-03.png)

---

![](/static/2021-10-03-06-08-15.png)
![](/static/2021-10-03-06-08-22.png)

---

![](/static/2021-10-03-06-08-37.png)
![](/static/2021-10-03-06-08-44.png)

---

end

![](/static/2021-10-03-06-08-54.png)
![](/static/2021-10-03-06-10-25.png)

* 可以找出上述点对距离最长

### 其他

A furthest pair of points don’t have to have smallest or largest x or y coordinate 最远的一对点不必具有最小或最大的 x 或 y 坐标

![](/static/2021-10-03-06-13-35.png)