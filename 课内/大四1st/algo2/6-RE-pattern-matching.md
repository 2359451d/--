# Content

* [Content](#content)
* [Matching Regular Expression](#matching-regular-expression)
* [正则表达式标记：Regular Expressions](#正则表达式标记regular-expressions)
* [egrep例子](#egrep例子)
* [非确定性有限自动机：Non-deterministic finite automata (NDFAs)](#非确定性有限自动机non-deterministic-finite-automata-ndfas)
* [NDFA构建-NDFA construction (for a regular expression R)](#ndfa构建-ndfa-construction-for-a-regular-expression-r)
* [NDFA性质](#ndfa性质)
* [模拟NDFA](#模拟ndfa)
  * [例子](#例子)
  * [复杂度分析：Analysis of NDFA simulation](#复杂度分析analysis-of-ndfa-simulation)

# Matching Regular Expression

有时候想匹配RE表达式表示的模式而不是特定str

![](/static/2021-11-05-06-08-37.png)

# 正则表达式标记：Regular Expressions

![](/static/2021-11-05-06-23-58.png)
![](/static/2021-11-05-06-39-33.png)

# egrep例子

![](/static/2021-11-05-06-50-41.png)

* pattern matching algorithm - 完成RE的匹配
  * 涉及NDFA，方便分析time complexity

# 非确定性有限自动机：Non-deterministic finite automata (NDFAs)

NDFAs来track前缀

![](/static/2021-11-05-06-59-13.png)

![](/static/2021-11-05-07-04-32.png)

* 目标：对于任何正则表达式 R，为 R 构造一个 NDFA A，使得当且仅当 s 匹配 R 时，A 才接受字符串 s Objective: for any regular expression R, construct an NDFA A for R such that A accepts a string s if and only if s matches R

# NDFA构建-NDFA construction (for a regular expression R)

偏自动机 partial automata

![](/static/2021-11-05-07-42-09.png)
![](/static/2021-11-05-07-43-12.png)

闭包 closure

![](/static/2021-11-05-07-58-56.png)

# NDFA性质

![](/static/2021-11-05-07-59-17.png)

* 1个start，1个halt状态
* 最多2r个状态
  * r - 正则表达式R长度
* 每个状态，要么
  * 1个出边 -char
  * <=2个出边 - epsilon

# 模拟NDFA

pattern matching

辅助类 & 函数
![](/static/2021-11-05-08-16-23.png)
![](/static/2021-11-05-08-20-43.png)

![](/static/2021-11-05-09-11-50.png)

## 例子

![](/static/2021-11-05-09-12-53.png)

![](/static/2021-11-05-09-14-24.png)

![](/static/2021-11-05-09-16-17.png)

![](/static/2021-11-05-09-17-52.png)

![](/static/2021-11-05-09-19-06.png)
![](/static/2021-11-05-09-19-32.png)

![](/static/2021-11-05-09-24-46.png)
![](/static/2021-11-05-09-25-16.png)

![](/static/2021-11-05-09-33-52.png)
![](/static/2021-11-05-09-34-26.png)

![](/static/2021-11-05-09-34-34.png)
![](/static/2021-11-05-09-34-57.png)

![](/static/2021-11-05-09-35-10.png)
![](/static/2021-11-05-09-35-26.png)

![](/static/2021-11-05-09-35-36.png)
![](/static/2021-11-05-09-35-51.png)

![](/static/2021-11-05-09-37-29.png)
![](/static/2021-11-05-09-38-01.png)

## 复杂度分析：Analysis of NDFA simulation

![](/static/2021-11-05-09-52-01.png)