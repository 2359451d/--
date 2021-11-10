# Content

* [Content](#content)
* [Recap](#recap)
* [Problems with a point estimate](#problems-with-a-point-estimate)
  * [Averaging](#averaging)
* [Bayes rule](#bayes-rule)
  * [例子](#例子)
* [（密度估计）计算后验：Computing the posterior](#密度估计计算后验computing-the-posterior)
  * [何时可以计算后验：When can we compute the posterior?](#何时可以计算后验when-can-we-compute-the-posterior)
  * [例子](#例子-1)
* [Likelihood, Posterior, data](#likelihood-posterior-data)
* [How it works](#how-it-works)
* [正则化最小化损失:Different point of view: minimising loss with regularization](#正则化最小化损失different-point-of-view-minimising-loss-with-regularization)
  * [例子](#例子-2)
* [后验分布计算步骤-Computing posterior: recipe](#后验分布计算步骤-computing-posterior-recipe)
* [边缘似然性：Marginal likelihood](#边缘似然性marginal-likelihood)
  * [例子](#例子-3)
* [先验选择：Choosing a prior](#先验选择choosing-a-prior)
* [Summary](#summary)

# Recap

![](/static/2021-11-04-04-02-53.png)

# Problems with a point estimate

![](/static/2021-11-04-04-13-39.png)

点参数估计问题 - 最优解可能局部最优

* 局部最优，预测值可能非常不同

## Averaging

如何解决上面问题？

![](/static/2021-11-04-04-30-56.png)
![](/static/2021-11-04-04-48-12.png)

* 计算avg estimate of **prediction value** （weighted sum)
* qa可能与L成比例
* 假设wa参数已知(w1,w2)，预测值均值就是加权和
  * 根据分布，w1,w2的似然性，，

# Bayes rule

![](/static/2021-11-04-04-58-35.png)
![](/static/2021-11-04-06-16-19.png)
![](/static/2021-11-04-06-39-18.png)

* w是随机变量，未知的情况时。有概率密度函数，w的一个分布。
  * **预测值的期望**就是 Ex(f(w))
  * 分布中，每个w被选取的似然性 * 关于w的函数f(w) = 预测值期望
* 如何计算 p(w|stuff)? -> Bayes rule
  * 服从这个分布的w，，
* 后验密度
* 似然
* 先验
* 边缘似然性

## 例子

![](/static/2021-11-04-06-56-52.png)

![](/static/2021-11-04-06-58-43.png)
![](/static/2021-11-04-07-03-27.png)
![](/static/2021-11-04-07-06-10.png)

![](/static/2021-11-04-07-09-33.png)
![](/static/2021-11-04-07-17-23.png)

# （密度估计）计算后验：Computing the posterior

![](/static/2021-11-04-07-23-51.png)

## 何时可以计算后验：When can we compute the posterior?

![](/static/2021-11-04-07-56-27.png)

* 先验p(w)，共轭似然函数时。可以得出后验p(w|x,t)形状

意义？

![](/static/2021-11-04-07-59-52.png)
![](/static/2021-11-04-08-08-09.png)

* 共轭先验

## 例子

![](/static/2021-11-04-08-48-10.png)
![](/static/2021-11-04-09-11-22.png)

---

![](/static/2021-11-04-18-35-22.png)
![](/static/2021-11-04-18-54-27.png)

# Likelihood, Posterior, data

![](/static/2021-11-04-18-56-25.png)

![](/static/2021-11-04-18-59-42.png)

![](/static/2021-11-04-19-02-39.png)

* 训练集数据越多，后验分布越小，从这个分布sample的模型越拟合

# How it works

![](/static/2021-11-04-19-19-20.png)

# 正则化最小化损失:Different point of view: minimising loss with regularization

![](/static/2021-11-04-19-41-39.png)
![](/static/2021-11-04-20-12-11.png)

* 最小化正则项
* λ相关系数 - 协方差矩阵

## 例子

![](/static/2021-11-04-20-41-58.png)

* 中间能得出tnew的高斯分布
  * mean就是根据后验分布算出来的预测值期望
  * noise level不变

---

新的tnew预测值分布（其他数据集？）

![](/static/2021-11-04-20-55-44.png)
![](/static/2021-11-04-20-58-03.png)

# 后验分布计算步骤-Computing posterior: recipe

![](/static/2021-11-04-20-59-45.png)

# 边缘似然性：Marginal likelihood

![](/static/2021-11-04-21-07-07.png)
![](/static/2021-11-04-21-14-07.png)

* 边缘似然是似然函数*先验的积分？，也就是t的期望
* 可以用来比较模型

## 例子

![](/static/2021-11-04-21-26-52.png)

# 先验选择：Choosing a prior

![](/static/2021-11-04-21-59-19.png)

# Summary

![](/static/2021-11-04-22-13-06.png)

![](/static/2021-11-04-22-14-00.png)
