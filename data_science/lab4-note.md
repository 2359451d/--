# Guidance

* `np.argmin()`
* `np.argsort()`
* `np.linalg.norm(x, ord=None, axis=None, keepdims=False)`
  * 求范数
  * 默认2范数（矩阵所有元素的平方和，再开根）
  * `ord`范数类型
    * `2`每个元素的平方和，在开跟
    * `1`所有元素绝对值的和
    * `np.inf`行和的最大值
  * `axis`
    * `axis=0`按列向量处理，求多个列向量的范数
    * `axis=1`行向量处理，求多个行向量的范数
  * `keepding`是否保持矩阵的二维特性
* `np.linalg.svd(a,full_matrices=1,compute_uv=1)`奇异值分解
  * **因子分解，将矩阵分解为3个矩阵的乘积**
    * U,V是正交矩阵,S是奇异值
  * `full_matrices`的取值是为0或者1，默认值为1，这时u的大小为(M,M)，v的大小为(N,N) 。否则u的大小为(M,K)，v的大小为(K,N) ，K=min(M,N)
  * `compute_uv`的取值是为0或者1，默认值为1，表示计算u,s,v。
    * 为0的时候只计算s
  * **返回值**
    * `u` - (M,M)
    * `s` - (M,N)
      * **diagonal matrix，对角元素从大到小排列，所以只保留最大的奇异值**
      * 可以使用`np.diag(Sigma)`得到完整的奇异值矩阵
    * `v` - (N,N)
    * **原矩阵，A=u*s*v**

🍊 特征值 & 特征向量

🍬 求之前必须转为方阵（矩阵和矩阵的转置相乘）

* **特征值 eigenvalue**
  * 方程 `Ax=ax`的根（`矩阵*特征向量 = 特征值*特征向量`）
    * `A` 二维矩阵
    * `x` 一维向量
  * 标量
* **特征向量 eigenvector**
  * 关于特征值的向量
  * 每行是个PCA主成分，即特征vector，代表变化方向？
* `np.linalg.eigvals`
  * 计算矩阵特征值
* `np.linalg.eig`
  * 返回包括特征值&对应特征向量的元组
  * `0`列为特征值
  * `1`列为特征向量
  * 协方差矩阵的**每个特征向量 - dataset的主成分**？
  * **主成分重要性importance（看成每个主成分/特征向量的length?，越长越重要）** = **特征值绝对值的平方根 ？**？---开根号就是矩阵的奇异值（svd的第二个返回值，sigma）

🍊 协方差

![](/static/2020-11-07-20-40-10.png)
![](/static/2020-11-07-21-03-54.png)

* `np.cov(m, y=None, rowvar=True, bias=False, ddof=None, fweights=None, aweights=None)`
  * 矩阵**不同维度之间的协方差**
  * 斜对角线的元素是该维度的方差（可以通过标准差^2获得）
  * 例子：1和2维度的协方差
    * 连加（一维元素-一维均值 * 二维元素-二维均值）/ 样本数-1

## WHISKY：表示&比较向量

`data/whisky.csv`

* 不同威士忌酿酒厂数据
  * 每个酿酒厂都有对于他们产品**风味特征 flavour characterisitcs**的主观判断
* 每个酿酒厂**根据`12`个风味指标（烟度，甜度**) 判断
  * 值范围`0~4`
* **`12-d`array，每行为1个酿酒厂，一共`86`个酿酒厂**
  * 每个酿酒厂为12-d向量空间中的一个点
* **`2-d`array**
  * 代表每个酿酒厂的**地理位置geographical locations**
  * `locations(86, 2)`same order as whisky

## Available data

`whisky`

* 86x12
* 86个酿酒厂，12个风味评判指标

`distilleries`

* 86个酿酒厂的名称

`columns`

* 12个指标的`name: index`键值对

`locations`

* 86个酿酒厂的地理位置

## 范数， 插值，统计量

范数

* 可以计算，酿酒厂之间在12-d向量空间中的distance
  * distance between x & y: norm of their differnece **距离=两向量差异的范数**
    * 可找到最相似，差异最小的向量，np.argmin,或 np.argsort然后筛选
* 范数类型
  * L1
  * L2
  * inf

## numpy.linspace

作用为：在指定的大间隔内，返回固定间隔的数据。他将返回“num”个等间距的样本，在区间[start, stop]中。其中，区间的结束端点可以被排除在外。

![](/static/2020-11-08-01-09-52.png)
![](/static/2020-11-08-01-10-02.png)

## PCA：特征值分解协方差矩阵实现PCA

1. 计算出**数据的平均向量**
   1. **中心化数据整体**-mean vector(符合协方差公式)，center a dataset
2. 根据demeanded数据，**计算协方差矩阵**
   1. `np.cov()`
3. 利用特征分解，**从协方差矩阵中计算特征值&特征向量**
   1. `eig_val, eig_vec = np.linalg.eig(data)`
   2. 每个特征向量，就是一个PC，主成分
      1. 可以根据特征值，将这些特征向量按大到小排序
      2. <font color="red">取出最大的`k`个特征值对应的特征向量，构成新矩阵</font> ---**【特征矩阵，每列为样本的一维主成分pc】**
   3. **每个PC的重要性=其特征值绝对值的平方根**
      1. 开根号就是矩阵的奇异值（svd的第二个返回值，sigma）
4. 将数据集投影到特征向量集（矩阵）--【将`n`维数据降维成`k`维】
   1. 形成矩阵乘积 `P=XV`
      1. `P - Nxk`
      2. `X - NxD` 数据集
      3. `V - Dxk`矩阵，每一列都为用于投影的PCA向量？

## PCA：SVD分解协方差矩阵实现PCA

输入

![](/static/2020-11-08-17-54-29.png)

* 要降维数据

1. 中心化数据集 center a dataset
2. 计算协方差矩阵
3. **通过SVD计算协方差矩阵的特征值&特征向量**
4. 特征值从大到小排序，选择最大的`k`个
   1. 选中最大的`k`个对应的特征向量，作为特征矩阵的列向量
5. 投影数据集到特征矩阵中
   1. 降维