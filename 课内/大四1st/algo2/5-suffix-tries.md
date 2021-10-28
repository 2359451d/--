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