# Content
* [Content](#content)
* [Sections](#sections)
* [问题描述](#问题描述)
* [RGS算法 - HR](#rgs算法---hr)
* [Kir´aly’s approximation algorithm for HRT](#kiralys-approximation-algorithm-for-hrt)
* [setup code](#setup-code)
* [Task](#task)
* [1](#1)
* [2](#2)
* [3](#3)
* [Task 4](#task-4)
* [提交](#提交)

# Sections

* Section2 - 主要两个匹配算法
* Section3 - skeleton code
  * main
  * input parser
  * dataset generator (for testing)
* Section 4 - 具体要实现的tasks
* Section 5 - 如何提交
* Section 6 - marking scheme

# 问题描述

![](/static/2021-11-15-21-46-20.png)
![](/static/2021-11-15-21-54-33.png)

HRT
![](/static/2021-11-15-21-57-59.png)

# RGS算法 - HR

注意I是HR，h没有ties

![](/static/2021-11-15-22-11-44.png)

---

例子

![](/static/2021-11-15-22-18-23.png)
![](/static/2021-11-15-22-18-43.png)
![](/static/2021-11-15-22-18-52.png)

# Kir´aly’s approximation algorithm for HRT

注意此例h带ties

![](/static/2021-11-15-22-25-58.png)

* 接近最大稳定匹配

![](/static/2021-11-15-23-14-52.png)
![](/static/2021-11-15-23-26-54.png)

例子

![](/static/2021-11-15-23-45-52.png)

# setup code

![](/static/2021-11-15-23-47-44.png)

两种运行方式

* `java main <I>`
  * 根据,RGS、K算法计算稳定匹配 & 打印
  * m should check the computed matching for stability in I and print it.
  * `java Main ExampleInstance.txt`
* `java main <I> <M>`
  * 读入匹配文件M和实例文件I，打印。check the computed matching for stability in I and print it.
  * `java Main ExampleInstance.txt MatchingUnstable.txt`

`generator.jar`

* 生成HR，HRT实例
* `java -jar generator.jar <parameter file> <instance file>`
  * `parameter file` - 指定生成实例的nature
  * `instance file` - 生成的实例名
  * `java -jar generator.jar param.txt instance.txt`

parameter

* 下面这个例子可以生成 20D, 5H

```
10 // D数量
3 // H数量
10 // H总容量
1 // D偏好列表min长度
3 // D偏好列表max长度
0.85 //H列表中entry与后继产生tie的概率,p=0时无tie,生成HR实例，p=1时，每个entry都出于一个tie
```

HR、HRT实例格式

```
10 \\ D数量
3 \\ H数量
1: 3 \\ D偏好列表
2: 2 3 1
3: 2 3 1
4: 3 2 1
5: 3 1
6: 2
7: 2 3
8: 1 2 3
9: 1
10: 3 2 1
1: 4: (5 10) 9 (4 3 8 2) \\ H偏好列表, j: cj: 
2: 3: (3 7) (2 8 6 10 4)
3: 3: (7 4 10 5 2) (8 3 1)
```

# Task

# 1

完成`printMatching`方法，

* 打印`run`计算出来的匹配，或者读入的M文件
* 输出格式
  * 每个di一行，分配到哪个hi，或者无匹配 `unmatched`
  * 最后一行 - 匹配大小
* 测试 - `java Main ExampleInstance.txt MatchingUnstable.txt`

```
Matching:
Doctor 1 is assigned to hospital 3
Doctor 2 is assigned to hospital 5
...
Doctor 9 is assigned to hospital 5
Doctor 10 is unmatched
...
Doctor 20 is assigned to hospital 3
Matching size: 18
```

# 2

完成`run()`

* 实现RGS-HR算法
* 最坏情况复杂度 - `O(L)`
  * `L` - D偏好列表的总长 or H偏好列表的总长
* `O(1)` - 判断hj是否更偏好某doctor
  * 使用 hj的`rankList`
* 抽出h的worst doctor
  * 利用`Hospital`对象的成员变量 `rankOfWorstAssignee`
    * 给定某hj, 该成员变量初始化为偏好列表的最后一个entry。 根据计算，这个变量只可能往左移动，根据情况变化

# 3

实现Kiraly approximation algo - HRT

* 涉及 `Doctor`类的修改
  * booleans - 判断某doctor是否被promoted/exhausted
* `O(L)` - 时间
  * 利用 rank list, O(1)时间对比两个doctor
  * Ties：`d1 (d2 d3) d4`
    * ranks 【1 2 2 3】 或 1 2 2 4
    * tie算一个rank
  * **注意HRT处理prefers & worst assignee的情况**
    * 考虑维护一个doctors集合，promoted的doctors优先提出 （避免线性搜索）。 **优先队列**？
      * 最后就是拿优先队列写出来。。。小根堆，每次把rank最高，还没promoted的优先出队

# Task 4

给HR实例实现`checkStability`

* read in的Matching 或者`run`计算出来匹配检测稳定性
* 存在blocking pairs，打印

# 提交

task 1-4源码 & 2个code listing files

* 源码，HR，HRT子文件夹包含源码 & 编译好的class文件
  * 不要包括分开的bin, src子文件夹
* code listing file
  * `a2ps -A fill -Ma4 *.java -o codeListingX.ps `
  * `ps2pdf -sPAPERSIZE=a4 codeListingX.ps`
  * `X` - `HR/HRT`

Status report

* 使用任何的external sources
* 实现中做的任何假设
* code存在的任何问题，如何解决？【如果code working，直接指明】

提交文件名

* `AlgII <family name> <given name>.zip`
  * 两个子文件夹 `HR/HRT`
  * 顶级放两个code listing files & status report

