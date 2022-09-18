# Lecture2 - Values & Types

# 类型：Types

![](/static/2021-01-30-19-59-14.png)

> Values are grouped into types according to the operations that may be performed on them
> 根据可以对它们进行的操作，值被分成不同的类型

不同PL支持不同类型

* 例子见图

---

## 类型基数 & 操作：Cardinality & Operation

![](/static/2021-01-30-20-01-32.png)

> 除了在PL中，类型还可以在集合论中找到："所有集合的集合 "罗素悖论；用于区分 "大 "和 "小 "集合

:orange: 一个类型是一组值，配备了可以统一应用于所有这些值的**操作** A type is a set of values, equipped with **operations** that can be applied uniformly to all these values

* 如`BOOL`类型，值`{false, true}`，支持逻辑操作 `not,and,or`

:orange: cardinality - **类型基数**

* type`T`的cardinality - `#T`
  * number of values of type `T`
* 如，`#BOOL=2`

## 类型与值/表达式：Value & Expression

![](/static/2021-01-30-20-07-02.png)

:orange: value `v` is of type `T`

* if `v ∈ set of values of T`
* 如果`v∈T类型的值集合`，则称`v`为类型`T`的值

:orange: Expression `E` is of type `T`

* if, when evaluation of E terminates normally, it is guaranteed to yield a value v of type T
* 当**表达式E正常执行结束，并产生类型T的值v时，称E为类型为T的表达式**

## 基本类型：Primitive Types

![](/static/2021-01-30-20-11-49.png)
![](/static/2021-01-30-20-16-19.png)

> A primitive type is a type whose values are primitive (i.e., cannot be decomposed into simpler values).
> 基本类型是一种类型，其值是基本的（即不能分解为更简单的值）

:orange: 每个PL支持内置built-in基本类型，

* 基本类型的选取定义由PL涉及的领域决定

:orange:某些PL允许自定义新的基本类型

---

:candy:例子

![](/static/2021-01-30-20-14-07.png)

* `VOID={void}`
  * type VOID contains the set of value only void
* ...

:candy: cardinalities

![](/static/2021-01-30-20-15-34.png)

## 复合类型：Composite Type

> A composite type is a type whose values are composite (i.e., can be decomposed into simpler values)
> 有复合值的类型（其值还可以继续划分）

:orange: PL支持大量的复合类型，但所有的复合类型都可以只用四个基本概念来理解 PLs support a large variety of composite types, but all can be understood in terms of just four fundamental concepts:

* **cartesian products 笛卡尔积** (tuples, structs, records)
* **disjoint unions 不相交并集** (algebraic types, variant records, objects)
* **mappings 映射** (arrays, functions)
* **recursive types 递归类型** (lists, trees, etc.)

### 笛卡尔积类型：Cartesian Product Types

![](/static/2021-01-30-21-00-04.png)

* 给定类型的多个值构成对或元组 § In a cartesian product, values of two (or more) given types are grouped into <font color="deeppink">pairs (or tuples).</font>
* (tuples, structs, records)

来源于数学，`SxT`

* 是所有对 `(x,y)`的类型，满足 `x`来自类型`S`，`y`；来自类型`T` In mathematics, S ´ T is the type of all pairs (x, y) such that x is chosen from type S and y is chosen from type T
  * `S X T = { (x, y) | x ∈ S; y ∈ T }`

:orange: 笛卡尔积类型基数 - cardinality of a cartesian product type

* `#(SxT) = #S x #T`
* 注意`#(SxT)`，x为标记notation
* `#S x #T`x为乘法

---

![](/static/2021-01-31-21-03-09.png)

:candy: 即，我们可以将生成的pair直接泛化成 **tuples公式**

* `S1 x S2 x... x Sn = {(x1, x2, ..., xn) | x1 ∈ S1; x2 ∈ S2;...;xn ∈ Sn}`

:orange: tuples/pairs支持的基本操作 - Basic operations on tuples/pairs

* **construction** of a tuple from its component values 元组/对创建
* **selection** of a specific component of a tuple 元组/对选择
  * (e.g., its 1st component or its 2nd component or …).

---

#### 例子

![](/static/2021-01-31-21-07-39.png)

---

![](/static/2021-01-31-21-10-18.png)

### 不相交并集类型：Disjoint union types

![](/static/2021-01-31-21-10-55.png)

> In a disjoint union, a value is chosen from one of two (or more) different types

:orange: disjoint unions 不相交并集 

* (algebraic types, variant records, objects, C structs)
* 在不相交并集类型中，值从1个或多个不同类型中选取

:orange: 数学层面

* `S+T`是不相交并集类型，其每个值由一个不同类型`S/T`的变量&`TAG`组成 In mathematics, S + T is the type of disjoint-union values. Each disjoint-union value consists of a variant (chosen from either type S or type T) together with a tag
* `S + T = { left x | x ∈ S } ∪ { right y | y ∈ T }`
* value `left x` consists of `tag left` & variant `x∈S`
* value `right y` consists of `tag right` & variant `y∈T`

:orange: **显式**tag写法

* `S+T` 写为 `left S + right T` make the tags explicit

---

#### 基数 & 基本操作

:orange: cardinality of disjoint union type 不相交并集类型基数

![](/static/2021-01-31-21-51-55.png)

* `#(S + T) = #S + #T`
* 每个部分类型基数的和

:orange: 泛化不相交并集公式至多变量

* `T1+T2+...+Tn`

![](/static/2021-01-31-21-58-43.png)

:orange: 基本操作 - basic operations on disjoint-union values in `T1+T2+...+Tn`

* **construction** of a disjoint-union value from its tag and variant 通过`TAG` & 变量 variant 创建不相交并集
* **tag test**, to inspect the tag of the disjoint-union value 检验不相交并集（对象）`TAG`的值
* **projection**, to recover a specific variant of a disjointunion value (e g., its T1 variant or its T2 variant or …) 恢复一个特定的变量 （对象类型强转？）

#### 例子

![](/static/2021-01-31-22-02-46.png)
![](/static/2021-01-31-22-03-11.png)
![](/static/2021-01-31-22-03-18.png)

* 不相交并集可用于表示(程序中会有的所有)对象，`OBJECT = Point VOID + Circle INT + BOX（INTxINT）`
  * `TAG`用来标识每个对象的类型 each object's tag identifies its class
  * `VOID，INT，INTxINT`都是variant
* 其对象本身也是一个不相交并集（单个元素？）
  * `POINT VOid` 无参构造（无成员）`POINT`类对象

---

:orange: 不相交并集类型/object的基本操作

![](/static/2021-01-31-22-09-33.png)

* 创建 construction - `new TAG variant...`
* 检验 - `p（object/variant） instanceof CIRCLE(TAG)`
* 投影 -  （类型强转？）

:candy: 注意：java中 对象集合open-ended

![](/static/2021-01-31-22-13-20.png)

* 对象集合为非抽象类, new
* 整个对象集合用非抽象类声明

### 映射类型：Mapping Types

![](/static/2021-01-31-23-40-21.png)

> In mathematics, m : S -> T states that m is a mapping from type S to type T, i.e., m maps every value in S to some value in T
> 数学层面： S->T：m是类型S到类型T的映射， m将所有S值映射成T值

* <font color="deeppink">mappings 映射 (arrays, functions)</font>
* if m maps value x to value y, we write `y=m(x)`, and we call `y` the image of `x` under `m`
  * m将x映射为y，`y=m(x)`
  * `y` - image
* generalisation: `S->T = {m | x∈S => m(x) ∈T}`

 :orange: cardinality of mapping type - 基数

* `#(S->T) = (#T)^#s`

#### 例子

![](/static/2021-01-31-23-46-48.png)

#### 数组类型：Array Type

![](/static/2021-01-31-23-49-33.png)

* 数组可以理解为映射类型 mapping types
* 如果数组元素为类型`T`，索引值属于类型`S`，则数组的映射满足 每个类型`S`的值都对应元素类型`T`
  * `S->T`

:orange: 数组基本操作

* **construction** of an array from its components
* **indexing**, to select a component using a computed
index value

![](/static/2021-01-31-23-59-08.png)

:candy: 注意

* 数组类型是**有限**映射类型（属于映射类型子集） array types is a finite mapping type(subset)
  * <font color="deeppink">因为数组元素需要被存储</font>
* 如果数组为`S->T`的映射，则`S`必须属于连续有限值范围，又称索引范围 If an array is of type S ∈ T, S must be a finite range of consecutive values {lb, lb+1, …, ub},called the array’s **index range**.

##### 数组类型例子

![](/static/2021-02-01-00-04-05.png)

#### (一元)函数类型：(unary)Function Types

![](/static/2021-02-01-00-05-41.png)

* 函数类型也可以理解为映射类型
  * 参数->结果，map arguments to results
* unary function - 一元函数映射 `f: S->T`
  * 参数类型`S`
  * 输出结果类型`T`

:orange: 函数基本操作 - basic operations on functions

* 创建/定义 - **construction** (or definition) of a function
* 调用 - **application**, i.e., calling the function with an argument

:orange: 函数基数 - funtion types cardinality

* 函数类型的基数/映射(`#(S->T)`)可为无穷  因为其输出结果按需计算`#S=∞` funciton can represent an infinite mapping(where #S=∞ since its results are computed on demand)

##### 一元函数例子

![](/static/2021-02-01-00-17-12.png)

#### 多元函数类型：N-ary Function Types

![](/static/2021-02-01-00-14-26.png)

* binary function 二元函数`f`，具有一对参数 类型`S1，S2`，输出类型`T`
  * `f:(S1xS2)->T` 即 `f`映射一对参数至输出结果`T`
  * 参数为笛卡尔积类型 cartesian product type

:orange: 可得，多元函数 n-ary functions

* `f:(S1x..xSn)->T`
* 参数为笛卡尔积类型 cartesian product type
  * 多元形参formals

##### 多元函数例子

![](/static/2021-02-01-00-18-12.png)

* binary function

#### 数组 vs 函数：Arrays & Functions Differs

from Lab solution2

> Arrays and functions are fundamentally different in that the mapping represented by an array is stored in its entirety (and therefore must be a finite mapping), whereas a function is applied to its arguments on demand (and therefore may be an infinite mapping).

数组和函数有本质上的不同，数组所代表的映射是完整存储的（因此必须是**有限映射**），而函数则是按需应用于它的参数（因此可能是**无限映射**）。

* 也许还可以提一手数组index range必须为有限numerical类型（或值为numerical的表达式），而函数映射输入没有限制于numerical

### 递归类型：Recursive Types

![](/static/2021-02-01-00-20-42.png)

* recursive types 递归类型 (lists, trees, etc.)
* 递归类型定义取决于其本身
* <font color="deeppink">递归类型为特殊的不相交并集类型</font> recursive type is a disjoint-union type in which
  * 至少一个变量是递归的，at least one variant is recursive
  * 至少一个变量是非递归的，at least one variant is non-recursive.

:orange: 数学表示 mathematical notation

* `LIST = VOID + (VALUE x LIST)`
  * 理解为，LIST由对象组成
  * VOID - 非递归对象（VOID-variant）
  * （VALUExLIST） - 递归对象 (VALUExLIST tuples variant)
  * 并且为这两个的不相交并集
* `TREE = VOID + (VALUE ´ TREE ´ TREE)`
  * 同理

:orange: 递归类型`T`的基数 cardinality

* `#T = ∞`

#### 列表类型：List Types

![](/static/2021-02-01-00-37-08.png)

* 列表由 `0`或多个元素的序列组成 a sequence of 0 or more component values.
  * <font color="deeppink">这里与tuples，array很大的区别在于，允许为`0`，tuples一定为“一对pair”， array必须有对应的映射&索引范围 index range</font>
* 列表值
  * 空 empty
  * 非空列表 non-empty，**由头元素head & 尾元素tail(除head外所有元素)组成** non-empty, in which case it consists of a head (its first component) and a tail (a list consisting of all but its first component)

:orange: 根据递归类型泛化列表类型定义

* 特殊的不相交并集类型 disjoint union type
* `LIST = empty VOID + nonempty (VALUExLIST)`
  * 可看为 `empty（TAG） VOID（variant）`对象
  * `nonempty(TAG) (VALUExLIST)(VARIANT)`对象

##### JAVA 列表定义

java不存在实质“列表”定义，LIST接口之类的只是集合collection，因此此例为 类定义，其具有对象，看为disjoint union type

![](/static/2021-02-01-00-44-54.png)

* `VOID`变量对应`NULL`

## 字符串类型：String

![](/static/2021-02-01-00-46-49.png)

* string由`0`或多个`char`的序列组成 A string is a sequence of 0 or more characters

:orange: 不同PL对string类型的处理（定义）不同，提供的操作也不同

# 类型系统&错误：Type Systems & Error

![](/static/2021-02-01-00-49-01.png)

:orange:类型错误 type error

* 当进行无意义操作（类型不支持的操作）时，产生类型错误 A type error occurs if a program performs a meaningless operation

:orange: PL type system 类型系统

* 为每个类型整合值，groups values into types
  * 避免类型错误 help prevent type errors
  * 高效描述数据

:candy: 拥有类型系统使高级PL与低级语言区别开来

* 低级汇编语言，只有bytes, words 字节/字类型，很容易造成类型错误

# 类型检查：Type Checking

![](/static/2021-02-01-00-52-45.png)

:orange: 对类型值进行操作之前，必须对类型的操作数进行类型检查（避免产生类型错误）Before any operation is performed, its operands must be type-checked to prevent a type error.

* not 操作，必须检查操作数类型为 `boolean`
* add 操作， 必须检查两个操作数类型是否为`int/long/...`等数值类型
* indexing操作，左边操作数是否为`array`类型，右边操作数是否为`int`

## 静态类型检查：Static Typing

![](/static/2021-02-01-00-58-42.png)

静态类型语言

* 每个变量有固定类型，提前定义声明every variable has a fixed type (usually declared by the programmer)
* 每个表达式有固定类型，编译器直接推断 every expression has a fixed type (usually inferred by the compiler)
* 所有操作数在编译时期进行类型检查 all operands are type-checked at compile-time

---

（表达式）类型推断例子

![](/static/2021-02-01-01-36-45.png)

* 即使不知道具体值，但编译时期可以通过类型推断来避免一些类型错误

## 动态类型检查：Dynamic Typing

![](/static/2021-02-01-01-00-35.png)

:orange: 动态类型语言

* 只有values类型固定
* 变量无固定类型
* 表达式无固定类型
  * 当表达式E正常执行结束，并产生类型T的值v时，称E为类型为T的表达式
* 操作数在运行时期进行类型检查 operands must be type-checked when they are computed at run-time.

---

例子 - 运行时期类型检查

![](/static/2021-02-01-01-37-11.png)

![](/static/2021-02-01-01-37-51.png)
![](/static/2021-02-01-01-39-03.png)

## Static vs dynamic typing

![](/static/2021-02-01-01-39-27.png)

优缺点

* **静态类型**
  * 更高效，只需要编译时期类型检查 Static typing is more efficient: it requires only compiletime type checks
  * 更安全，编译器保证程序无类型错误（否则无法通过编译）Static typing is more secure: the compiler can guarantee that the object program contains no type errors.
  * **不灵活，某些计算无法自然表示**
* **动态类型** Static typing is less flexible: certain computations cannot be expressed naturally
  * 需要运行时期检查（使程序运行更慢），强制标记所有值（占更大内存空间）Dynamic typing requires run-time type checks (making the program run slower), and forces all values to be tagged (using up more space).
  * 处理编译时期未知数据更灵活，自然 Dynamic typing provides no such security
  * **不安全，编译器不能保证无类型错误，编译时期无类型检查**Dynamic typing provides no such security.

# 表达式：Expression

![](/static/2021-02-01-01-45-15.png)
![](/static/2021-02-01-01-46-24.png)

> 表达式是一个程序结构，计算后产生一个值。
> An expression is a program construct that will be evaluated to yield a value

Simple expressions:

– **literals** 字面值
– **variables** 变量

compound expression 符合表达式

* **function call** 函数调用
  * 传参给函数计算结果的表达式 A function call is an expression that computes a result by applying a function to argument(s)
* **construction**
  * 根据元素创建复合类型值的表达式  constructs a composite value from its components.
* **conditional expression** 条件表达式
  * 选择子表达式进行计算的表达式 expression that chooses one of its sub-expressions to evaluate.
* **iterative expression** 迭代表达式
  * 对集合进行迭代计算的表达式 expression that performs a computation over a collection (e.g., an array or list).
* **block expression** 块表达式
  * 包含局部变量声明的表达式 n expression that contains declarations of local variables, etc.

