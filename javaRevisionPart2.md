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

##### Properties(used with I/O)

**是一个Map集合，继承了HashTable**

* key&value都为String类型
* 称为属性类对象
* 线程安全

![](/static/2020-08-07-23-15-42.png)

🍬 需要掌握的方法

* 存`setProperty(str,str)`
* 取`getProperty(str)`

---

🍊 IO与Properties联合使用，动态解析读取文本，XML数据【<font color="red">经常变化的数据可以放入文本，xml这样的配置文件中，省去重新编译的麻烦</font>】

🍊 属性配置文件 - 后缀名以`properties`结尾

```txt
### 以下称为属性配置文件 ###
### java推荐该类文件后缀名以properties结尾 ###
### ‘userinfo.properties’ ###
### key=value ###
###key:value，不建议使用冒号###
###属性配置文件key重复的话，value会自动覆盖####
###最好不要有空格####
user=123
name=a
```

如，将以上文本的数据加载到Properties对象中

![](/static/2020-08-07-23-18-27.png)

* 直接通过Properties的实例方法**将(字节/字符)输入流管道(文件数据)加载到Map集合中**
* 再善用`getProperty`& `setProperty`

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

## I/O

🍬 重点掌握

* `FileInputStream` & `FileOutputStream`
* `ObjectInputStream` & `ObjectOutputStream`

🍊 InputStream & OutputStream继承结构

![](/static/2020-08-06-16-56-40.png)

🍊 Reader & Writer 继承结构

![](/static/2020-08-06-16-59-06.png)

🍊 IO流分类

* 按照流的方向进行分类
  * 以内存作为参照物
    * 往内存 - 输入，读
    * 从内存 - 输出，写
* 按照读取数据方式不同进行分类
  * 按照字节方式读，一次1B.万能，什么格式的文件都能读
  * <font color="red">按照字符方式读，一次读取一个字符，方便读取文本文件。</font>word文件也不能读(不是普通文档)

🍊 IO流四大家族【都为抽象类，实现`java.io.Closeable`接口，可关闭的】

* `java.io.InputStream`字节输入流
* `java.io.OutputStream`字节输出流
* `java.io.Reader`字符输入流
* `java.io.Writer`字符输出流

🍬 只要类名以`Stream`结尾都是字节流。以`Reader/Writer`结尾的都是字符流

🍊 `java.io`包下需要掌握的流`16`个

* 文件专属
  * `java.io.FileInputStream`
  * `java.io.FileOutputStream`
  * `java.io.FileReader`
  * `java.io.FileWriter`

* 转换流【字节流转换成字符流】
  * `java.io.InputStreamReader`
  * `java.io.OutputStreamWriter`

* 缓冲流专属
  * `java.io.BufferedReader`
  * `java.io.BufferedWriter`
  * `java.io.BufferedInputSream`
  * `java.io.BufferedOutputSream`

* 数据流专属
  * `java.io.DataInputStream`
  * `java.io.DataOutputStream`

* 标准输出流
  * `java.io.PrintWriter`
  * `java.io.PrintStream`

* 对象专属流
  * `java.io.ObjectInputStream`
  * `java.io.ObjectOutputStream`

### close() & flush()

* 所有输入输出流都是可关闭的`close()`
* **所有输出流**，实现了`java.io.Flushable`接口，是可刷新的`flush()`
  * 养成习惯，输出流最终输入后`flush()`。刷新管道中剩余未输出的数据，强行全部输出完。<font color="red">如果不刷新，可能导致丢失数据</font>

🍬 注意关闭流时，建议异常捕捉结构外声明初始化输入输出流，**最后在finally语句块中关闭【如流不为null，则关闭】**

![](/static/2020-08-06-17-20-14.png)

### InputStream && OutputStream

🍬 字节流辅助需要`byte[]`字节数组

🍊 字节输入流

![](/static/2020-08-06-17-06-56.png)

* `InputStream`是一个抽象类，所有子类都是字节输入流

主要方法

![](/static/2020-08-06-17-08-41.png)
![](/static/2020-08-06-17-08-53.png)

* `read()`返回读到的字节本身
  * `EOF = -1`，返回-1时读取完毕

---

### FileInputStream

文件字节输入流

![](/static/2020-08-06-17-10-14.png)

* 万能，任何类型文件都支持
* 完成输入，读操作（硬盘->内存）
* 按字节读，汉字在java(utf-16编码集)中占2B，所以直接打印会产生乱码

构造方法
![](/static/2020-08-06-17-14-57.png)

---

#### single byte - read()

不推荐使用：单字节`read()`循环读入框架

![](/static/2020-08-06-17-34-59.png)

```java
while((readData=fis.read())!=-1){
  //
}
while((readData=fis.read()) && readData!=-1){
  //
}
```

🍊 缺点【无缓冲的单字节`read()`读取】

* <font color="red">一般不用，</font>一次读1B，内存硬盘交互太频繁，浪费时间&资源

---

#### multi-byte read(byte[] b)

推荐使用：多字节读入`read(byte[] b)`方法

* 一次最多读取`b.length`个字节，放入`byte[]`数组
  * <font color="blue">读取了多少个字节就传入String构造函数，输出【考虑数组溢出情况，所以不推荐全部输出】</font>
* 减少硬盘和内存的交互，提高程序执行效率
* <font color="red">返回读取的字节数量，结束为-1(EOF)</font>
  * **注意数组如果满了，后续还有读入字节会重置读取的后续字节数量，并从0索引开始覆盖数组内容**。<font color="blue">所以推荐读了多少个，传入多少个至String构造函数</font>
  * ![](/static/2020-08-06-19-29-04.png)
  * ![](/static/2020-08-06-19-50-17.png)
  * `String(byte[] b,offset,length)` byte数组，起始位置，长度

🍊 推荐写法

```java
// 一次读到多少个，转换多少个String
int readCount = 0;
while ((readCount = file.read(bytes)!=-1){
  System.out.println(new String(bytes, 0, readCount));
}
```

#### other methods

`available()`返回剩下的未读字节数

![](/static/2020-08-06-20-05-26.png)
![](/static/2020-08-06-20-08-38.png)

* 可以用于初始化多字节读入，存放的byte数组大小`byte[] b = new byte[fis.available()]`
  * <font color="red">可以省去循环，因为一次性读完所有字节，不用考虑数组溢出覆盖&读了多少转换多少的问题</font>
  * <font color="blue">不适合大文件，因为byte数组不能过大</font>

`skip()`跳过几个字节不读取

![](/static/2020-08-06-20-12-14.png)

### FileOutputStream

**要按照字节方式写文件**

* 例如：文件的复制，首先读取文件，将该文件另写一份保存到磁盘上，完成备份

🍊 **注意，写完之后一定要刷新**！`flush()`

![](/static/2020-08-06-20-15-43.
png)

#### methods

![](/static/2020-08-06-20-28-29.png)
![](/static/2020-08-06-20-23-37.png)

🍊 以下方法会先清空源文件，再重新写入。不能进行源文件追加写入。

![](/static/2020-08-06-20-21-00.png)

🍊 <font color="red">以下可以完成追加写入</font> - 文件字节输出流`append`属性

![](/static/2020-08-06-20-27-45.png)

#### file copy

结合 FileInputStream & FileOutputStream完成文件的拷贝

* 边读边写

![](/static/2020-08-06-21-05-32.png)
![](/static/2020-08-06-21-02-17.png)

### Reader & Writer

🍬 字符流辅助需要`char[]`字符数组辅助

#### FileReader

![](/static/2020-08-06-21-15-52.png)
![](/static/2020-08-06-21-59-51.png)
![](/static/2020-08-06-21-59-07.png)
![](/static/2020-08-06-21-38-33.png)

#### FileWriter

![](/static/2020-08-06-22-03-23.png)
![](/static/2020-08-06-22-04-59.png)
![](/static/2020-08-07-12-39-57.png)
![](/static/2020-08-07-12-40-34.png)

文件字符输出流，写，只能输出普通文本

* 注意输出完毕需要`flush()`

#### file copy

使用FileReader&FileWriter进新拷贝。只能拷贝**普通文本文件【能用普通文本编辑的】**。

![](/static/2020-08-07-12-30-27.png)

### BufferedReader & BufferedWriter

自带缓冲区，不用指定辅助数组。为了提高效率，减少物理读取次数。

![](/static/2020-08-07-12-41-48.png)

#### BufferedReader

🍊 如果构造方法里面需要传入流，则该传入的流称为**节点流**。外部负责包装的流，称为**包装流/处理流**，关闭只需要关闭最外层(改写了方法最里层节点流会隐式自动关闭)。

注意构造函数参数`Reader`【**本身是抽象类，需要实例化其实现类**】，其他流构造函数大部分都是跟一个路径

![](/static/2020-08-07-12-47-42.png)
![](/static/2020-08-07-12-43-47.png)
![](/static/2020-08-07-15-14-03.png)

🍊 常用方法

* `readLine()`，至`EOF`返回`null`，其他返回某行本身的值`String`

##### conversion: InputStreamReader

字节流转换至字符流。`BufferedReader`构造函数只接收字符流对象

🍊 通过转换流转换`InputStreamReader reader = new InputStreamReader(in)`，传入字节流`InputStream`，返回字符流`Reader`

* `in`节点流，`reader`包装流

🍊 字符流传入缓冲流`BufferedReader br = new BufferedReader(reader)`，传入字符流

* `reader`是节点流，`br`是包装流

🍬 推荐嵌套写法

* `BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))`

#### BufferedWriter

同理，**带有缓冲区的字符输出流**，注意构造函数只接收字符输出流`Writer`。

* 需要传入字节输出流需要先通过转换流转换成字符输出流
* <font color="red">注意输出完毕进行刷新</font>

![](/static/2020-08-07-15-35-30.png)

🍊 字符流适用

* `BufferedWriter out =new BufferedWriter(new FileWriter())`

##### conversion: OutputStreamWriter

🍊 字节流使用，提前转换

* `BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputSream(String path)))`

### BufferedInputStream & BufferedOutputStream

带缓冲区的字节输入输出流，同理

* 直接传入字节流`InputStream`&`OutputStream`及其子类`FileInputStream`&`FileOuputStream`等

![](/static/2020-08-07-15-51-11.png)

### DataInputStream & DataOutputStream

数据专属流

* <font color="red">可以将数据连通<b>数据类型</b>一并写入文件</font>
  * 文件不能是普通文本文档(无法用记事本打开).<font color="blue">普通文本会乱码</font>

#### DataOutputStream

🍊 构造函数传入字节输出流`OutputStream`

* `DataOutputStream`写的文件，只能使用`DataInputStream`读。读的时候需要提前知道写入的顺序【源文件数据对象什么顺序写入】
* **只有顺序一致，才能正常取出数据**

![](/static/2020-08-07-15-59-56.png)
![](/static/2020-08-07-16-04-13.png)
![](/static/2020-08-07-16-07-15.png)

用的不多

#### DataInputStream

🍊 数据字节输入流

* `DataOutputStream`写的文件，只能使用`DataInputStream`读。读的时候需要提前知道写入的顺序【源文件数据对象什么顺序写入】
* **只有顺序一致，才能正常取出数据**

![](/static/2020-08-07-16-11-14.png)

### PrintStream & PrintWriter

标准输出流

#### PrintStream

标准字节输出流，**默认输出到控制台**

🍊 标准输出流不需要手动`close()`

🍊 `System.out`返回的是`PrintStream`

* ![](/static/2020-08-07-16-27-59.png)
* ![](/static/2020-08-07-16-29-39.png)

🍊 构造函数传入`OutputStream`字节输出流

* `new PrintStream(new FileOutputStream(String path))`

🍊 改变标准输出流的输出方向,<font color="red">使输出不再指向控制台，指其他文件or输出目标</font>

* 可以用于输出到日志文件`log`
* `System.setOut(new PrintStream(new FileOutputStream(String path)))`
* 之前的方法![](/static/2020-08-07-16-32-16.png)

##### print to log file

![](/static/
2020-08-07-16-43-12.png)

#### System.in

![](/static/2020-08-07-16-52-45.png)

* `System.in`是字节输入流
* 可结合缓冲字符输入流使用，输入源为用户输入![](/static/2020-08-07-16-53-40.png)

### ObjectInputStream & ObjectOutputStream

对象流

🍊 注意参与序列化&反序列化的自定义类需要**实现序列化接口**`Serializable`

* `Serializable`一个标志接口(无任何其他源码)，起到标识作用，VM可识别
* <font color="red">VM看到`Serializable`这个接口后，会自动为该类生成一个序列化版本号</font>

🍬 关于版本号，采用什么机制区分类?

* 通过类名进行对比，如果类名不一样，肯定不是同一个类
* <font color="red">类名一样，比较序列化版本号</font>
* <font color="blue">自动生成序列化版本号缺陷: 一旦代码修改，就不能进行反序列化（编译会生成新的序列化版本号，VM会标识为全新的类）</font>
  * <font color="green">凡是实现了`Serializable`接口，建议提供一个固定不变的序列化版本号</font>
  * ![](/static/2020-08-07-23-03-06.png)
  * 生成固定版本号有快捷键

🍊 序列化

* java对象存储到文件中，将java对象的状态保存下来的过程
* `writeObject()`

🍊 反序列化

* 将硬盘上的数据重新恢复到内存当中，恢复成java对象
* `readObject()`

#### ObjectOutputStream

🍊 注意自定义类需要**实现序列化接口**`Serializable`

![](/static/2020-08-07-22-22-06.png)

#### ObjectInputStream

![](/static/2020-08-07-22-41-42.png)

#### multi-serialization

🍊 `transient`<font color="blue"></font>关键字表示游离的，不参与序列化

一次多对象序列化，使用集合（单个多对象会报错）

![](/static/2020-08-07-22-45-40.png)
![](/static/2020-08-07-22-48-32.png)

* 集合实现了`Serializable`接口

## File

![](/static/2020-08-07-17-09-26.png)

🍊 **文件和目录路径名的抽象表示形式**

* 和IO四大家族无关系，所以`File`<font color="red">不能完成文件的读写</font>

🍊 创建File对象，传入路径

![](/static/2020-08-07-17-14-53.png)

🍊 某些方法

* `File file = new File(String path)`构造方法
* `file.exist()`判断文件/目录是否存在
* `file.createNewFile()`以文件形式新建
* `file.mkdir()`以目录形式新建
* `file.mkdirs()`以多重目录形式新建
* `file.getParent()`获取文件父路径
* `file.getAbsolutePath()`获取文件绝对路径
* `file.getName()`获取文件名
* `file.isDirectory()`是否是一个目录
* `file.isFile()`是否是一个文件
* `file.lastModified()`返回最后一次修改时间，类型`long`，从`1970 00：00：00`到现在的毫秒数
  * 毫秒数转换成日期![](/static/2020-08-07-18-35-42.png)
* `file.length()`获取文件大小，字节
* `File[] file.listFiles()`获取当前目录下所有子文件
  * ![](/static/2020-08-07-18-40-39.png)

## Multi-Threads

多线程

* 线程A&B，**堆内存&方法区内存共享**
* **栈内存独立**，每个线程一个栈
* JVM是一个进程，main是主线程，还有gc线程垃圾回收

---

实现多线程的方式？

* 类继承`java.lang.Thread`重写`run`方法
* <font color="red">类实现`Runnable`接口，推荐</font>

### Thread

方式

* 类继承`Thread`，覆写`run`方法
* 新建分支线程对象`MyThread myThread = new MyThread()`
* 启动线程`myThread.start()`
  * 并不会马上执行线程，而是使线程进入就绪。<font color="blue">作用是启动一个分支线程，会<b>先阻塞</b>，在JVM中开辟一个新的栈空间，只要开辟完成，`start()`立刻结束，线程启动成功，才继续执行后续代码</font>
  * 启动成功的线程自动调用`run()`方法，并且该栈帧位于分支栈的栈底`%ebp`

### Runnable

类实现`java.lang.Runnable`接口，<font color="red">并不是一个线程类，只是一个可运行的类</font>

🍊 推荐使用，因为可以实现多接口

方式

* 创建一个可运行对象(实现Runnable接口)`MyRunnable r =new MyRunnable()`
* 将可运行对象封装成一个线程对象`Thread t = new Thread(r)`
* 启动`t.start()`

🍬 推荐合并写法`Thread t = new Thread(new MyRunnable())`

* `t.start()`

🍬 采用匿名内部类方式

```java
Thread t = new Thread(new Runnable(){
  @Override
  public void run(){
    // ...
  }
})
//启动
t.start();
```

### Callable

JDK8新特性，**这种方式实现可以获取线程返回值**，但效率较

🍊 方式

![](/static/2020-08-12-00-37-56.png)

* 创建一个未来任务类对象`FutureTask task = new FutureTask(Callable<V>)`有返回值或`FutureTask task = new FutureTask(Runnable, V)`无返回值
* 覆写`Callable`接口，`call()`
  * ![](/static/2020-08-12-00-33-01.png)
  * `java.util.concurrent.FutureTask`JUC包中，属于java并发包
  * <font color="red">匿名类方式</font>
* 将未来任务对象传入`Thread`创建线程对象
  * `Thread t = new Thread(task)`

🍊 如何获取返回值？

* `task.get()`会使当前线程阻塞，待另一个线程执行完毕获取返回值

### Some Methods

1. 怎么获取当前线程对象
2. 获取线程对象的名字
3. 修改线程对象的名字

#### run()

注意`run()`中的异常不能用`throws`处理，只能`try...catch`

* 因为`run()`方法在父类中没有抛出任何异常

#### get/setName() & currentThread()

🍊 名字相关【假设继承了`Thread`类】

* 设置线程名字`t.setName("")`
* 获取线程名字`t.getName("")`
* 默认名字`Thread-0/1/2...`

🍊 获取当前线程对象？

![](/static/2020-08-08-23-13-11.png)

* 静态方法，返回对当前执行的线程的引用
* `Thread currentThread = Thread.currentThread()`
* 获取当前线程名`currentThread.getName()`

#### sleep() & interrupt()

![](/static/2020-08-09-14-57-49.png)

* 静态方法`Thread.sleep(long millis
* 参数`long millis`毫秒
* 让**当前线程**进入休眠，<font color="red">“阻塞状态”，放弃占有的CPU时间片，让给其他线程使用</font>

🍊 终断线程的睡眠 - 唤醒

![](/static/2020-08-09-15-29-40.png)

* `t.interrupt()`终断t线程的睡眠
* 依靠`java`异常处理机制。线程在睡眠状态被中断会抛出`IterruptedException`

#### stop()

`stop()`强行终止线程

* 已过时，不建议使用。非线程安全。

🍊 如何何理终止一个线程的执行？

![](/static/2020-08-09-16-10-04.png)

* 类中定义一个终断标记`flag`
  * 中断时，从外部更改该类标记字符，从而间接终止

### Methods Related to Scheduling

🍊 调度相关方法

#### Priority

🍬 设置/获取线程优先级

* `setPriority(int newPriority)`
* `getPriority()`
* 最低`1`，默认`5`，最高`10`
  * <font color="red">优先级高的获取CPU时间片可能多（大概率）</font>
* 最低优先级1`Thread.MIN_PRIORITY`
* 最高优先级10`Thread.MAX_PRIORITY`
* 默认优先级5`Thread.NORM_PRIORITY`

#### Thread.yield()

🍬 让位方法

* `Thread.yield()`暂停当前正在执行的线程对象，并执行其他线程
  * <font color="red">只能让同优先级的线程有执行的机会</font>
* <font color="red">不是阻塞方法，让当前线程让位，让给其他线程使用</font>
* 会让当前线程状态从`running->ready`

#### Thread.join()

🍬 合并线程

* `void join()`当前线程进入<font color="red">【阻塞】，调用的线程进行执行，直到结束。当前线程才可以继续执行</font>
* ![](/static/2020-08-09-19-15-51.png)
* `t.join()`t“合并”到当前线程中，当前线程阻塞（如sleep）

### Thread-Safe

🍬 异步&同步

* ![](/static/2020-08-10-15-55-19.png)

🍊 什么时候数据在多线程环境下不安全？【线程安全问题】

* 多线程并发
* 有共享数据
* 共享数据有修改行为

🍊 如何解决？

* 线程排队执行。这种机制称为 - **线程同步机制**
* **线程同步=线程排队，牺牲效率，保证安全**

#### Add Lock: synchronized()

🍊 每个<font color="red">对象</font>都有一个锁

![](/static/2020-08-10-16-41-39.png)
![](/static/2020-08-10-17-09-16.png)

语法

```java
// 在静态方法上使用synchronized，找类锁。只有1把锁。（保证静态变量安全）

public synchronized void run(){...} //影响效率 - 锁只能为this，不灵活。但代码简洁

/*
使用同步块，一个对象一把锁
*/
synchronized(){
  // ...
}
```

* 括号中传入**数据(希望线程共享的对象)**,关键
  * 该数据必须是多线程共享的数据，才能达到多线程排队
* <font color="blue">方法中的局部变量位于各自线程栈的方法栈帧中，互不干扰。资源类（实现Runnable）的成员变量以及这个资源类对象本身，位于堆中，由多个线程共享，异步执行时会冲突</font>
  * 实现线程类（继承Thread），需要额外堆中生成资源类对象，供线程对象共享

局部变量示意图
![](/static/2020-08-10-17-17-53.png)

成员变量共享示意图
![](/static/2020-08-10-17-17-13.png)

#### Without Lock

🍊 不加锁情况下，不采用线程同步方式

![](/static/2020-08-10-17-24-05.png)
![](/static/2020-08-10-17-24-30.png)

* 通过**给每个线程创建独立资源对象**，避免线程安全问题

#### Dead-Lock

嵌套使用`synchronized`不当导致

开发中如何解决线程安全问题？

* 不一上来直接使用`synchronized`，会让执行效率降低，用户体验不好。
* 尽量使用局部变量，代替实例变量(成员遍历)&静态变量
  * 如果必须使用实例变量，则考虑创建多个对象，这样实例变量的内存不共享。对象不共享，没有安全问题。
  * 如不使用局部变量，对象不能创建多个，使用`synchronized`，线程同步机制

### Daemon Thread

守护线程(后台线程)

* 最具代表性的，**垃圾回收线程**

🍊 特点

* 一般为**死循环**
* 用户线程只要结束，**守护线程自动结束**
  * main是用户线程

#### setDaemon(boolean)

🍊 将线程设置为守护线程

* `thread.setDaemon(true)`
  * 只要用户线程结束，即使守护线程为死循环也会直接结束

### Timer

🍊 定时器作用

* 间隔特定时间，执行特定程序
* 多种实现方法
  * `sleep(milliseconds)`设置睡眠时间，每到这个时间点醒来，执行特定任务，最原始方式
  * <font color="red">直接使用定时器API，`java.util.Timer`，使用较少，因为很多高级框架都支持定时任务</font>
  * `Spring`框架提供的`SpringTask`框架，只需要简单配置就可以完成定时器任务

#### Methods

![](/static/2020-08-11-22-40-38.png)

* `timer.schedule(定时任务， 第一次执行时间， 间隔多久执行一次)`

🍊 自定义定时任务类，继承`TimerTask`抽象类，本质是个线程

* 覆写`run()`

🍊 时间类相关

* `SimpleDateFormat`&`Date`结合
  * `parse()`返回`Date`
  * `format()`返回`String`

🍊 使用匿名内部类

* ![](/static/2020-08-11-23-46-51.png)

### wait() & notify() & notifyALL()

🍊 是java中每个对象都有的方法，`Object`自带

* `o.wait()`让正在o对象上活动的线程进入等待状态（<font color="red">阻塞</font>），无期限等待，直到被唤醒`o.notify()`
* `o.notify()`可唤醒当前在o对象上等待的线程
  
🍊 notifyAll()

* 唤醒o对象上所有处于等待的线程

#### Producer & Consumer

🍊 生产者消费者问题

* 专门解决特定需求
  * <font color="red">生产消费达到均衡</font>
* 由仓库负责调用`wait()`&`notify()`
  * 建立在`synchronized`线程同步基础上
  * `o.wait()` - 会让正在o对象上**活动的线程t进入等待状态，并且释放之前占有的o对象的锁**
  * `o.notify()` - 会让正在o对象上**等待的线程t被唤醒，只会通知，不会释放之前占有的o对象的锁**

🍊 生产者消费者实现

* 需求
  * 生产多少，消费多少，仓库有容量

🍊 生产者

![](/static/2020-08-14-21-54-11.png)

* 仓库未满，生产，唤醒消费者
  * <font color="blue">可以通过while死循环配合同步块实现，有条件判断可以直接用notifyAll()</font>
* 仓库满，Producer等待，释放仓库锁

#### is wait() blocked?

🍊 wait()是否使线程阻塞？

![](https://picb.zhimg.com/v2-287f87ad5328f2aa5cd7fbd48dadcd8f_r.jpg?source=1940ef5c)

* <font color="red">由上可知，wait()&join()本质都是使线程进入waiting等待状态</font>
* 只有被唤醒的waiting线程才有权利进入阻塞状态抢锁
  * 重新获取到对象的锁才能恢复执行