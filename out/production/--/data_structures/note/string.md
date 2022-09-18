# String & text algorithms

自用笔记

* [String & text algorithms](#string--text-algorithms)
  * [Text compression](#text-compression)
  * [Huffman encoding](#huffman-encoding)
    * [Optimality](#optimality)
    * [Algorithmic requirements](#algorithmic-requirements)
  * [LZW](#lzw)
    * [LZW compression](#lzw-compression)

## Text compression

text compression 字符串压缩

![](/static/2020-10-26-21-29-39.png)

* special case of **data compression**数据压缩的特例
  * saves disk space & transmission time
* **must be lossless**字符串压缩必须不易失
  * the original must be recoverable without error 源必须能无错恢复
* **其他数据形式的压缩可以接收数据丢失**
  * pictures, sound, etc

## Huffman encoding

哈夫曼编码 - 无损字符串压缩

![](/static/2020-10-26-21-41-24.png)

* 经典统计方法（compared to LZW）
* **每个字符用变长代码代替固定的ascii码**
* 每个字符用**唯一代码字codeword（位字符串 bit string）**表示
  * **频繁出现**的char**用较短代码字codeword表示**
* 没有任何codeword（唯一，用于表示字符，bit string）是其他codeword的前缀【明确解压缩】

🍊 原理

* 基于哈夫曼树（真二叉树）
  1. 首先添加哈夫曼树的叶子
     1. **叶结点 - 用char出现频率标记**
     2. 频率：char在文本中出现的频率
  2. 有>=1的节点，无父节点
     1. 在**最小权重节点上添加新父节点**
     2. 新（父）节点的权重 = 子节点权重之和
  3. char codeword(bit string) - 从root到适当叶结点的**路径**
     1. `left = 0`
     2. `right = 1`

### Optimality

最优

![](/static/2020-10-26-21-52-32.png)

Weighted path length (WPL) of a Huffman tree T

* **带权路径长度WPL**
  * sum(weight of leaf nodes叶权重，char出现频率)*(distance from root,每条路径都是一个bit string表示)
* **WPB=压缩字符串中bit数量**
  * no. of bits = sum(frequency of char出现频率) * (bit string/codeword length of char)
* **哈夫曼树（指定叶权重）都有最小带权路径长度WPL**
  * 哈夫曼树不一定相等
    * compression file 不一定相同，
  * **给定频率（权重）下所有哈夫曼树WPL相同**
    * 但compression size相同
* 优化： 使用带前缀属性bit序列，优化哈夫曼算法

### Algorithmic requirements

操作分析

* **建树**
  * `O(n + mlog m)`
  * `n` frequency
  * `mlogm`how many heap storing the weight of nodes
* **压缩**（使用code table,【codes数组，用char索引】）
  * 建表
    * `O(mlogm)`
    * m个字符, m条路径,长度<=logm
  * 压缩
    * `O（n)`
    * n个字符，所以查找n次O(1)操作
  * 因此一共**`O(mlog m) + O(n)`**
* **解压**（直接用哈夫曼树， 重复trace树中路径）
  * `O(n log m)`
  * n个字符n条路径， <=log m

## LZW

串表压缩算法

![](/static/2020-10-26-22-31-43.png)

* dictionary是string集合
  * 每个string 由一个 **codeword(bit string**)表示
  * **codeword也可看为一个非负整型**
* 在压缩过程中输出codeword时，写入压缩文件的就是**bit string**
  * 使用由当前**codeword长度**确定的bit string长度
    * <font color="red">所以在任何时刻所有bit string长度相同</font>

### LZW compression

dictionary压缩&解压时**动态建立 build dynamically**

* 初始，dictionary**包含所有长度为`1`的可能string**
* 过程中，整个字典都是**以前缀结尾 closed under prefixes**
  * 如果字符串`s`在字典中，则`s`的每个前缀也在字典中
* **trie - 字典的理想表示**
  * <font color="red">每个节点 - 字典中一个'word'</font>

---

🍊 压缩&解压过程中，有一个**动态codeword length(k)**【值可改变】

* codeword - 长度为`k`的bit string
  * [使用由当前**codeword长度**确定的bit string长度，**保证所有bit string长度相同**] - **确保解压时知道要read多少bit**
* 给定`k`，可以有`2^k`种不同codeword（bit string）
  * 所有bit string长度相同，`k`，<font color="red">因此决定了字典的大小</font>
* <font color="blue">但是，codeword length(k)是动态的，可以按需增量</font>
  * `k+1`长度
  * doubling了不同的codeword组合（bit string, 2^k+1）
  * 所有codeword（bit string）长度变为`k+1`
  * <font color="red">因此`k`的初始值应足够大，能供编码所有长度为1的string</font>