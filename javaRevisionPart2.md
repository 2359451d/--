# Revision

Revision part2: Collection, I/O, Multi-Threads, Reflection, Annotation

## Generic Types

![](/static/2020-08-03-16-46-40.png)

---

JDK8新特性 - 自动推断(之前不填完会报错):
![](/static/2020-08-03-18-42-45.png)

---

```java
List l = new ArrayList();
l.add(1);
l.add(2);
l.add(3);

for (Iterator iter=l.iterator(); iter.hasNext();) {
    //出现了 java.lang.ClassCastException 异常
    //这种转型错误时运行期发现了
    //错误发现的越早越好，最好在编译器能发现类似的错误
    //如果想在编译器发现类似的错误，必须使用泛型
    String s = (String)iter.next();
    System.out.println(s);
}
```

![](/static/2020-08-03-17-08-27.png)
![](/static/2020-08-03-17-08-39.png)
![](/static/2020-08-03-17-47-20.png)

> 使用泛型能更早发现错误，如类型转换错误（通常在运行时期发现）。**如使用泛型，则可以在编译时期发现错误**，易于调试。**且迭代器中可以不进行强制转换(next默认返回Object)**。自定义比较器，**改写Comparable接口可以不用instanceof判断运行时期类型**。

Map & 泛型
![](/static/2020-08-03-17-53-53.png)

自定义泛型【<font color="red">注意泛型标识符T没有修改限制，但推荐与JDK泛型标识一样</font>】

![](/static/2020-08-03-18-04-40.png)
![](/static/2020-08-03-18-04-58.png)

## Utility Class: Collections

工具类

🍊 如何得到一个线程安全的List?

* `Collection.synchronizedList(List)`
* ![](/static/2020-08-03-16-43-51.png)

其他方法

* 排序`sort(List<T>)` 或 `sort(List<T>, comparator)`
  * 不能直接对`Set`排序

## Collection

🍊 集合不能直接存储基本数据类型，<font color="red">只能存储对象地址,引用</font>

* `java.util.*`包下

🍊 重点

* 背集合继承结构
* `Collection`接口常用方法
  * `remove` & `contains`底层调用`equals`
* 迭代器`Iterator`

---

集合继承结构图

![](/static/2020-08-01-20-59-16.png)
![](/static/2020-08-01-20-59-57.png)
![](/static/2020-08-01-21-10-15.png)

* `Collection`接口下集合都可迭代，实现`Iterable`接口
* 集合接口
  * 迭代器 - `Iterator`
    * 可迭代集合，遍历集合
  * 列表 - `List`元素有序可重复
    * `ArrayList`底层数组，查询效率高，增删低
    * `LinkedList`底层双向链表，查询只能从头节点找效率低，但增删快
    * <font color="blue">`Vector`底层与`ArrayList`相同，效率低，很少使用【线程安全】</font>，不建议使用
  * 集合 - `Set`元素无需不可重复
    * `HashSet`底层哈希表/散列表，实际是`HashMap`
    * <font color="red">`SortedSet`接口。不可重复，元素按大小顺序排序。</font>
      * `TreeSet`实现类，底层二叉树
  * Map键值对存储，key无序不可重复【可看为Set】
    * `HashMap`key无序不可重复，即一个`HashSet`哈希表实现类
    * <font color="red">`SortedMap`接口。key无序不可重复，但是存储的元素可以按大小自动排序。</font>
      * `TreeMap`实现类
    * <font color="blue">`Hashtable`效率低，使用少【线程安全】</font>，不建议使用
      * `Properties`属性类，键值对只能存储`String`类型

### Contains & Remove

集合这两个方法调用`equals`比较内容，而不是引用地址

* 注意一下有涉及常量池的equals，==问题

### Interface: Collection & Iterator

Collection是List&Set父接口。

![](/static/2020-08-01-21-19-47.png)
![](/static/2020-08-01-21-20-18.png)

#### Iterator

Iterator，迭代接口，通过该接口可以遍历集合中数据，主要方法【<font color="red">注意可以结合泛型使用，不使用next默认返回Object，需要进行强转</font>】

![](/static/2020-08-01-21-21-32.png)

---

如何将集合中元素一个个取出? - 使用迭代器(JDK5支持for循环迭代)

![](/static/2020-08-01-22-57-42.png)

* <font color="red">！！！注意：迭代器最初不指向第一个元素，【只要集合结构发生改变（增删清），迭代器需要重新获取，因为直接修改集合结构影响迭代器遍历，产生不可预知结果，未更新迭代器】</font>
  * 而通过迭代器进行`iterator.add()` & `iterator.remove()`来修改集合结构的操作，不会有影响
  * <font color="blue">Iterator可以统一数据结构的访问方式，不必关心各个数据结构由什么实现</font>
* 先获取集合对象的迭代器对象`Iterator it = c.iterator()`
* 通过迭代器对象开始迭代，有方法`boolean hasNext()`&`Object next()`迭代器指针后移并返回该元素

### List

主要两个实现类ArrayList & LinkedList

* 有顺序，进出顺序相同
* 基于线性存储，可看作可变数组

`ArrayList`

* 查快，增删慢
* 基于可变数组

`LinkedList`

* 查慢，增删快
* 基于（双向）链表

`Vector`

* 不建议使用，效率慢，被`ArrayList`取代
* 线程安全，`synchronized`修饰
* `Stack`继承`Vector`，被`LinkedList`取代

面向接口

* `List l = new ArrayList()`
* `List l = new LinkedList()`

`ArrayList/LinkedList`修改为`HashSet`

* ![](/static/2020-08-03-16-41-11.png)

#### Commonly Used Method

![](/static/2020-08-02-22-07-01.png)

* 注意转型问题

`list.toArray()`转换成数组

* ![](/static/2020-08-03-17-02-38.png)
* ![](/static/2020-08-03-16-30-49.png)
* ![](/static/2020-08-03-16-31-36.png)

如何得到一个线程安全的List?

* `Collection.synchronizedList(List)`
* ![](/static/2020-08-03-16-43-51.png)

#### ArrayList

🍊 特点

* 初始化`capacity=10`
* 扩容为原容量`1.5`倍
  * 最小增长值: 原容量右移`1`位
* 底层实现 - 数组
* <font color="red">末尾增删元素效率可以</font>
  * `add()/remove()`方法
  * <font color="blue">向特点索引插入/删除元素用的较少，效率较低</font>
* <font color="red">非线程安全</font>

🍊 优化

* 提前定一个大容量集合，减少之后扩容次数

🍊 (数组)优点

* 检索效率高

🍊 缺点

* 随即增删元素效率低
* 末尾插入/删除较高

#### LinkedList

底层实现: 双链表

##### SingleLinkedList

单向链表节点

* 值
* 下个节点的内存地址

优点

* 【内存不连续】随即增删元素效率高`O(1)`，不需要线性位移

缺点

* 查询效率低`O(N)`,需要从头节点遍历

🍬 链表可以有下标，但是ArrayList检索效率之所以高，是因为底层是数组，可以计算出地址。而链表内存地址不连续，每次查找需要从头节点遍历，效率低

##### DoublyLinkedList

双链表节点

* 值
* 前节点地址
* 后节点地址

#### Vector

特点

* 初始化容量`10`
* 扩容为原容量的`2`倍
  * 10(full)->20, 40(full)->80
* 底层: 数组
* 线程安全

### Set

#### HashTable

哈希表

![](/static/2020-08-03-21-57-12.png)
![](/static/2020-08-03-21-57-41.png)

#### HashSet

> 无序不可重复。根据哈希算法存取数据，性能良好

* 插入数据时，调用对象`hashCode()`获得哈希码，算出该数据对象应插入的位置
  * 根据值拿suffix

#### TreeSet(SortedSet)

> 可以对Set集合进行排序，**默认升序，也可以自定义排序（实现Comparable/Comparator接口）**。基本封装类&String都实现了Comparable接口。

* 底层是TreeMap，二叉树
* 放到TreeSet中的元素，等同于放到TreeMap集合的Key部分
* 元素无序不可重复，但可以根据大小顺序自动排序【可排序集合】

##### Self-Balanced Binary Tree

TreeSet/TreeMap自平衡二叉树

* 遵循左小右大原则

🍬 遍历方式

* 前序pre-order
* 中序in-order
* 后序post-order

🍬 TreeSet/TreeMap采用中序遍历

* Iterator迭代器采用中序遍历方式

##### Customize Sorting: Comparable & Comparator

TreeSet自定义类型排序

🍬 如果引用类没有实现comparable接口会报错，因为无参构造中会强转一个comparable

* 自定义引用类排序需要实现Comparable接口，覆写比较的规则`compareTo(Object o)`方法。可使用泛型
* `compareTo`
  * 返回`0`，表示相同，value会覆盖
  * 返回`>0`，会在右子树上找
  * 返回`<0`，会在左子树上找

---

🍊 第二种比较方式：使用比较器coomparator

* 创建TreeSet/TreeMap集合时，直接通过构造函数传入比较器
  * 可考虑匿名内部类![](/static/2020-08-05-16-13-07.png)

---

如何选择Comparable&Comparator？

* 类似String,Integer都实现Comparable。<font color="red">比较规则不会发生改变时，或只有一个时，使用Comparable接口</font>
* <font color="blue">比较规则有多个，并且需要多个比较规则之间频繁切换，建议使用Comparator接口</font>
  * Comparator接口设计符合OCP原则

### Map

> Map存放**键值对【都为引用，存放对象内存地址】**。较常用的是HashMap，HashMap中**键对象的存取**与HashSet一样，**采用哈希算法**。因此如果Map中存放自定义类，需要覆写equals&hashcode

🍬 存放在HashMap集合key部分和HashSet集合中的元素需要同时重写hashCode和equals

遍历集合的两种方式

* `keySet()`获取所有key的`Set<T>`集合，遍历每个key，再调用`get`方法
  * <font color="blue">再根据键找值，需要花费时间，效率较低</font>
* `entrySet()`获取该map的set集合`Set<Map.Entry<Key,Value>`，通过迭代器遍历Set集合中的`Map.Entry`
  * 调用`entry.getKey()` & `entry.getValue()`
  * <font color="red">Map.Entry是静态内部类</font>
  * 严格来讲set集合中每个`Map.Entry`是`Node`对象
  * <font color="blue">效率较高，直接通过Node获取【键值属性值】，适合大数据量</font>

`HashMap` & `HashTable`区别

![](/static/2020-08-04-13-43-57.png)

#### equals & hashcode

🍬 向Map集合中存取，都是先调用key的`hashCode`方法，再决定调不调用`equals`【下标位置元素为空时不调用】

* ![](/static/2020-08-04-21-40-08.png)

为什么重写equals？

* 因为equals默认比较的是两个对象的内存地址

为什么重写hashcode？

* 已知HashMap集合的**key部分无序，不可重复**【HashSet】，同理<font color="red">HashSet中的元素也同时需要重写hashCode&equals方法</font>
  * 无序: 因为不一定挂到哪个单链表上
  * 不可重复：由`equals`确保key不可重复。如果key重复，value覆盖

🍬 如果一个类equals重写，hashcode也需重写。如果equals返回true，hashcode返回的值必须一样

* ![](/static/2020-08-04-21-45-52.png)

##### Distribute Hash Values Evenly/Unevenly

![](/static/2020-08-04-18-28-07.png)

#### Commonly Used Methods

![](/static/2020-08-04-14-06-51.png)

* `Map.Entry`是静态内部类，泛型是`<K,V>`
* ![](/static/2020-08-04-14-15-54.png)
* ![](/static/2020-08-04-14-16-11.png)

#### HashMap

🍬 对于哈希表数据结构来说

* 如果o1&o2hash值相同，一定放在同一个单链表上
* <font color="red">如果o1&o2hash值不同，但由于哈希算法执行结束后转换的数组下标可能相同（同一链表），此时发生哈希碰撞</font>

🍬 向Map集合中存取，都是先调用key的`hashCode`方法，再决定调不调用`equals`【下标位置元素为空时不调用】

* ![](/static/2020-08-04-21-40-08.png)

🍬 掌握`map.put(k,v)` & `map.get(k)`方法的实现原理

🍬 为什么哈希表的随即增删&查询效率高(中庸)？
![](/static/2020-08-04-17-27-13.png)

🍬 <font color="red">重点：覆写hashCode&equals方法</font>

HashMap集合底层是哈希表/散列表

* 一维数组，这个数组中每个元素是一个单向链表(节点)
* 是数组&单向链表结合体
  * 数组: 查询效率高，随机增删低
  * 单向链表：随即增删高，查询低
* HashMap底层是一维数组，<font color="red">存储内部类Nodes</font>
  * ![](/static/2020-08-04-15-18-41.png)
  * `hash`哈希值，是`key`的`hashCode()`方法的执行结果。通过哈希函数/算法，可以转换成数组下标
* <font color="red">同一个单向链表上节点的hash值相同，因为数组下标相同</font>

🍬 HashMap集合默认初始化容量`16`，默认加载因子`0.75`

* 当HashMap集合底层数组容量达到`75%`,数组开始扩容。扩容后为原容量2倍
* <font color="red">HashMap集合初始化容量必须是2的次幂，官方推荐。因为达到散列均匀，提高存取效率是必须的</font>

---

entrySet遍历Map

```java
public static void main(String[] args) {
    Map map = new HashMap();
    map.put("1001", "张三");
    map.put("1002", "李四");
    map.put("1003", "王五");
    //采用 entrySet 遍历 Map
    // 或得到该Map的Set集合，用foreach循环遍历
    Set entrySet = map.entrySet();
    for (Iterator iter=entrySet.iterator(); iter.hasNext();
    {
      Map.Entry entry = (Map.Entry)iter.next(); 
      System.out.println(entry.getKey() + ", " + entry.getValue());
  }
}
```

##### JDK8: New Traits - Threshould

jdk8+新属性：提高检索效率

![](/static/2020-08-04-21-52-28.png)

* 哈希表中单链表节点超过阈值`8`个之后，变成红黑树。
* 当红黑树上节点小于`6`时，重新变成单链表

##### put

![](/static/2020-08-04-16-09-03.png)

`map.put(k, v)`实现原理

* 将`K，V`封装进`Node`对象中
* 底层调用`k`的`hashCode()`计算出hash值，再通过哈希函数/算法，将hash值转换成数组下标，<font color="blue">如果该节点下标位置上没有任何元素，则直接将该Node插入</font>,<font color="red">下标位置上如已存在链表，则</font>
  * 将该`k`对比链表中每一个节点的K，`equals=False`，则插入该新节点至链表末尾。如其中有一个`equals=True`，旧`value`会被新的覆盖

##### get

![](/static/2020-08-04-17-17-27.png)

`map.get(k)`

* 调用k的`hashCode()`算出哈希值，再通过哈希算法/函数转换成数组下标，定位到某位置。
  * 如该位置无元素，返回`null`
  * 如该位置上有单向链表，则拿`k`与单链表上每个`Node`的k进行`equals`比较。如所有`equals==false`，`get`返回`null`。如有一个节点匹配，`equals`返回`true`，`get`返回节点`value`

#### HashTable: Key null？

HashMap允许key&value为`null`

* 注意：HashMap集合的key null只能有一个

HashTable不允许key&value为`null`

![](/static/2020-08-04-23-38-11.png)

* HashTable线程安全，由synchronized修饰，因为对线程处理，效率较低，使用较少
* 初始化容量11
* 扩容为原容量的2倍

##### Properties

是一个Map集合，继承了HashTable

* key&value都为String类型
* 称为属性类对象
* 线程安全

🍬 需要掌握的方法

* 存`setProperty(str,str)`
* 取`getProperty(str)`

### Summary: initial capacity

![](/static/2020-08-05-18-29-06.png)

ArrayList

* 初始化`capacity=10`
* 扩容为原容量`1.5`倍

Vector

* 初始化容量`10`
* 扩容为原容量的`2`倍
  * 10(full)->20, 40(full)->80
* 底层: 数组
* 线程安全

HashSet【HashMap同理】

* 初始容量`16`
* 初始化容量建议2的次幂
* 扩容: 原容量的2倍

HashTable

* HashTable线程安全，由synchronized修饰，因为对线程处理，效率较低，使用较少
* 初始化容量`11`
* 扩容为原容量的2倍

TreeSet
