# Content

* [Content](#content)
* [String and Text Algo](#string-and-text-algo)
* [Termnology](#termnology)
* [后缀树&应用：Suffix trees and applications](#后缀树应用suffix-trees-and-applications)
* [最长重复子串问题-Problem: finding a longest repeated substring](#最长重复子串问题-problem-finding-a-longest-repeated-substring)
* [公共子串：Common Substrings](#公共子串common-substrings)
* [后缀：Suffixes](#后缀suffixes)
* [Suffix Trie](#suffix-trie)
  * [Trie](#trie)
  * [Suffix Trie](#suffix-trie-1)
  * [后缀树使用：Using a suffix trie](#后缀树使用using-a-suffix-trie)
    * [长度m的字符串搜索](#长度m的字符串搜索)
    * [最长重复字串搜索](#最长重复字串搜索)
  * [建立：Building a Suffix Trie](#建立building-a-suffix-trie)
* [Suffix Trees](#suffix-trees)
  * [例子](#例子)
  * [性质：The suffix tree of a string S](#性质the-suffix-tree-of-a-string-s)
* [SuffixTree怎么减少存储空间？](#suffixtree怎么减少存储空间)
  * [例子](#例子-1)
* [Suffix Tree构建：Suffix Tree Construction](#suffix-tree构建suffix-tree-construction)
  * [Naive-使用Trie建立](#naive-使用trie建立)
  * [构建方法2](#构建方法2)
  * [其他构建方法](#其他构建方法)
* [sTree应用:Problem solving with a suffix tree 1](#stree应用problem-solving-with-a-suffix-tree-1)
  * [例子-模式串出现位置](#例子-模式串出现位置)
  * [例子-最长重复字串](#例子-最长重复字串)
  * [例子-公共最长子串](#例子-公共最长子串)
  * [如何找到公共分支节点：Identifying common branch nodes](#如何找到公共分支节点identifying-common-branch-nodes)
  * [为什么要加](#为什么要加)

# String and Text Algo

![](/static/2021-10-28-22-14-40.png)

# Termnology

![](/static/2021-10-28-22-28-42.png)
![](/static/2021-10-28-22-33-34.png)
![](/static/2021-10-28-22-35-38.png)

# 后缀树&应用：Suffix trees and applications

![](/static/2021-10-28-22-47-32.png)
![](/static/2021-10-28-22-47-43.png)

# 最长重复子串问题-Problem: finding a longest repeated substring

![](/static/2021-10-28-22-58-52.png)

暴力 - 搞出每一个可能的子串，然后检查每个字串是不是重复

* 每个检查要n^2
* 然后有n个子串

数组法（稍微快一点，O(n^2)）

![](/static/2021-10-28-23-02-19.png)

* 对角线连续1
  * 扫上面三角形所有对角线，找连续1最长的
* 复杂度：
  * 时间 - O(n^2) 建立数组，O(n^2)扫对角线
  * 空间 - O(n^2)

# 公共子串：Common Substrings

最长公共子串

* 数组法

![](/static/2021-10-28-23-05-46.png)
![](/static/2021-10-28-23-07-49.png)

# 后缀：Suffixes

![](/static/2021-10-28-23-22-27.png)

# Suffix Trie

## Trie

![](/static/2021-10-28-23-44-43.png)

## Suffix Trie

![](/static/2021-10-29-02-04-30.png)
![](/static/2021-10-28-23-49-27.png)
![](/static/2021-10-29-02-03-11.png)

* 如何解决-某后缀是另一个后缀的前缀，那么不存在后缀树？
  * string末尾加 `$`

## 后缀树使用：Using a suffix trie

![](/static/2021-10-29-02-07-16.png)

* 长度m的字符串搜索
* 最长重复字串搜索

### 长度m的字符串搜索

![](/static/2021-10-29-02-07-25.png)

![](/static/2021-10-29-02-23-42.png)

### 最长重复字串搜索

![](/static/2021-10-29-02-26-41.png)

## 建立：Building a Suffix Trie

![](/static/2021-10-29-02-37-11.png)

---

worst case - O(n^2) , each suffix is unique

![](/static/2021-10-29-02-38-20.png)

# Suffix Trees

后缀树优化空间 - O(n)

* unary - one child
  * suffix trie中允许存在

![](/static/2021-11-04-23-29-17.png)

## 例子

![](/static/2021-11-04-23-41-32.png)

## 性质：The suffix tree of a string S

![](/static/2021-11-04-23-38-09.png)
![](/static/2021-11-04-23-49-03.png)

* 每个后缀对应**唯一**的一个叶节点的edge label

# SuffixTree怎么减少存储空间？

![](/static/2021-11-04-23-49-31.png)

* 注意最坏情况下，suffix tree还是要O(n^2)的空间，因为edge label(str)的长度之和还是和trie中存的一样
* **用short edge labels方法减少至O(n)空间**
  * edge label改为存str对应的索引对

## 例子

生成index pair【自底向上通过short edge label方法从full edge labels缩短到short edge label】

![](/static/2021-11-04-23-57-06.png)

* 叶节点自底向上
* 如果两个叶节点，哪个叶节点先被遍历不重要。edge label被覆盖也不重要，因为对应的str一样的

# Suffix Tree构建：Suffix Tree Construction

## Naive-使用Trie建立

![](/static/2021-11-05-00-18-26.png)

## 构建方法2

![](/static/2021-11-05-00-28-57.png)

## 其他构建方法

![](/static/2021-11-05-00-30-45.png)

# sTree应用:Problem solving with a suffix tree 1

![](/static/2021-11-05-00-46-22.png)

* 模式串出现位置

## 例子-模式串出现位置

![](/static/2021-11-05-01-21-27.png)

## 例子-最长重复字串

![](/static/2021-11-05-01-50-38.png)

* 最长重复字串

![](/static/2021-11-05-03-09-07.png)

## 例子-公共最长子串

![](/static/2021-11-05-03-23-41.png)

![](/static/2021-11-05-03-26-16.png)
![](/static/2021-11-05-03-26-24.png)

## 如何找到公共分支节点：Identifying common branch nodes

![](/static/2021-11-05-04-49-22.png)

* v为分支节点时，查看v的所有孩子。
  * 如果某孩子有后继叶节点，后缀idx起始在S*的S1位置，则b1(v) = true
  * 如果某孩子有后继叶节点，后缀idx起始在S*的S2位置，则b2(v) = true
* 至少每个有一个孩子满足上面条件就行

---

步骤总和

LCSt - putting it all together

![](/static/2021-11-05-05-14-44.png)

## 为什么要加#

Finding an LCSt of S1 and S2

![](/static/2021-11-05-05-27-15.png)