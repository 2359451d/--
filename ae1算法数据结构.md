# ADS AE1

:speech_balloon:

- 实现快排quicksort的4种优化变体

- 随机seed,比较每个变体的效率

- 定义&实现算法: compute adversaries for quicksort

:bento:提交

- 实现源码&short report[name&guid]&如何运行code&实现&每部分做了什么

---

## :blue_heart: PART1

实现以下算法:

- lecture 5 slide 74, 的快排虚码
  - 包括实现PARTITION,
  - 支点pivot选择=最右元素

- 快排优化变体,**不返回排序好的子类 returns without soring subarrays**
  - **结合插入排序**,排序接近排序好的array
  - slide 84
  - 针对少于k元素的子数组[小数组],当top-level返回pivot值,直接插排整个数组

- 使**用三数取中**支点放在倒数第二个位置,使用双轴快排法
  - 将原数组分为3部分
  - A[p..i]比支点小
  - A[i+1..j-1]等于支点
  - A[j..r]大于支点
  - **当输入有过多重复元素时适用**
  - **即,选取中间值作为pivot,可能会造成退化**
  - :heart:未排序的数组,很难直接计算出中间数
    - **因此三数取中写法中,pivot取left,mid,right三个索引中的中间值**
    - ![](/static/2020-02-10-10-32-01.png)
    - 将选出来的pivot交换最后位置
    - 左右两个指针[left向右扫,right为支点前面一个向左扫]
    - ![](/static/2020-02-10-10-35-41.png)
    - ![](/static/2020-02-10-12-10-38.png)
    - ![](/static/2020-02-10-12-13-40.png)
    - ![](/static/2020-02-10-12-53-31.png)
    - ![](/static/2020-02-10-12-56-19.png)

- **三路快排**,分3个区,从最左边开始扫

  - 