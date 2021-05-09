# Database Fundamentals & Relational Model

Relational Design - 数据库原理

* [Database Fundamentals & Relational Model](#database-fundamentals--relational-model)
* [情境数据库：Contexual Database](#情境数据库contexual-database)
* [数据管理系统：Data Management System](#数据管理系统data-management-system)
* [Data](#data)
* [概念数据建模：Conceptual Data Modelling](#概念数据建模conceptual-data-modelling)
* [关系型概念模型：Relational Conceptual Model](#关系型概念模型relational-conceptual-model)
* [关系型模型：Relational Model](#关系型模型relational-model)
* [标准表示形式：Database Schema Formalism](#标准表示形式database-schema-formalism)
* [例子-RM-Company](#例子-rm-company)
* [约束条件：Relational Constraint](#约束条件relational-constraint)
  * [Key Constraint：Superkey](#key-constraintsuperkey)
  * [Key Constraint：Candidate Key](#key-constraintcandidate-key)
  * [Key Constraint: Primary Key](#key-constraint-primary-key)
  * [例子：CK & SK & PK](#例子ck--sk--pk)
  * [完整性约束：ENTITY INTEGRITY CONSTRAINT](#完整性约束entity-integrity-constraint)
  * [引用完整性约束：Referential Integrity Constraint](#引用完整性约束referential-integrity-constraint)
    * [例子：引用完整性约束](#例子引用完整性约束)
* [总结](#总结)

# 情境数据库：Contexual Database

![](/static/2021-01-17-14-06-04.png)

> Observation：人类活动由数据驱动，我们是根据观察到的数据进行决策，行动和推理
>
> Observation：我们在记忆中存储所有数据和回忆是有限的。即便可以调用这些数据的效率也不高
>

:orange:Idea

* 定义一个强大的基础，可以存储庞大数据，更新和删除数据，有效调用检索数据 --- Database
* 4种操作 - CRUD

:orange: Database（每个都关联一个context）

* *数据库保存着当前上下文活动的相关的数据*
* Web Search - 包含网页链接的数据库
* Data Mining - 管理多维数据的数据库，用于发现模式、离群值、新趋势、预测和分类[UCI ML Repository]
* Scientific/Medical - 用于药物发现、健康监测和病毒分析
* Customer/Retial - 数据库的客户资料&偏好，产品

# 数据管理系统：Data Management System

:orange:数据库管理系统DBMS - 基础功能

![](/static/2021-01-17-14-20-45.png)
![](/static/2021-01-17-14-20-54.png)

* **数据建模 model data**
  * DBMS提供方法：关系型数据建模，面向对象数据建模，一阶逻辑数据建模，描述逻辑数据建模模糊逻辑建模
* **数据访问 access data**
  * 数据的增删改查
* **数据分析 analyze data**
  * 复杂聚合查询，函数近，直方图，多维可视化、离群值检测
* **数据存储 store（physically）data**
  * memory -> hard disk
* **数据保护 secure data**
  * 控制对敏感&机密数据的访问，加密&对数据编码
* **维护数据一致性 maintain data consistency**
  * 由于软件缺陷，断电，磁盘崩溃而导致的机器崩溃情况下，能从故障中恢复数据
* **优化数据访问 - 使高效检索数据** optimize data access to efficientl retrieve data
  * 索引 & 底层哈希数据结构 - 快速访问数据
  * 优化算法

---

![](/static/2021-01-17-14-36-09.png)
![](/static/2021-01-17-14-48-18.png)

* engineering perspective, DBMS is a set of interconnected components
* 步骤
  * database connector，用户与db数据的接口，当SQL语句输入，先进行连接
  * 唤醒Parser，解析SQL语句，识别语句类型（哪一种操作）
  * Optimizer决定要执行的处理算法的最优执行序列
  * 执行序列决定好后，唤醒Processing algo
  * 其中OS生成特定code（code generator），真正执行SQl查询
  * 获取本地DB数据（或分布式DB数据）

:orange:DBMS

* 一个带接口的“box”，为用户/应用提供所讨论的内容
  * 数据建模
  * 声明式编程语言（SQL）管理&查询数据
* 声明式 Declarative
  * 指示DB做什么 & 而不是如何完成具体任务

# Data

![](/static/2021-01-17-14-49-26.png)

:orange: 区分以下三类数据

* **结构化数据** structured data
  * 明确定义的数据结构，如表格
  * 3 Kg。3是基准(datum)，Kg是该基准的元数据(meta-data) - metadata which interprets the data
* **非结构化数据** Unstructured data
  * web页面，文本，传感器测量
  * 只较少提供了关于解释数据的信息
* **半结构化数据** Semi-structured data
  * XML, JSON文件
* **自述性数据** Self-descriptive data
  * interpret themselves（medium entropy）
  * `<data type=real; unit=‘Kg’>3</data>`
* 现代DBMS管理所有数据族：文档、图表。多媒体内容

# 概念数据建模：Conceptual Data Modelling

![](/static/2021-01-17-15-02-00.png)

:orange: 概念数据建模 - 挑战

* *将一个真实问题的文字描述转化为一组概念，传达完全相同的信息*  transform a textual description of a real problem into a set of concepts conveying exactly the same information
* 即，如何将语言描述的数据存入数据库中？

:orange:建模方法

* **实体关系建模 Entity-Relationship Modeling**
  * 并不能保证操作和查询执行（CRUD）的最优化 does not guarantee optimality in operations and query executions
* **关系型建模 Relational Modeling**
  * 纯数学驱动：关系优化，集合论，函数依赖论的基础 foundation of relational algebra, set theory, functional dependency theory
  * **保证CRUD优化** Guarantees query optimization

:orange: 大致步骤

* 原始数据的概念，文本描述
* 抽象概念数据为ER模型 （这步开始都为选择概念数据建模的一种方法）
* 将ER模型转换为关系型模型RM
* 根据RM进行建模

---

![](/static/2021-01-17-15-16-21.png)

:orange: Conceptual Data Model

* *解释数据的数学模型 Mathematical model for interpreting our data*
  * 为什么是数学模型 why mathematical model？
    * 定理来自：集合论，函数依赖与归一化理论、关系代数 set theory, functional dependency & normalization theory, and relational algebra
  * 为什么要解释数据？ why interpretation
    * 需要理解上下文，背景
* **实体** entities
  * 银行账户
  * 学生
  * 员工
* **（数据实体）属性** attributes
  * name
  * address
  * ID
* **（实体间）关系** relationship
  * an employee works in a department;
  * a student attends many courses

# 关系型概念模型：Relational Conceptual Model

![](/static/2021-01-17-15-39-29.png)

:orange: 关系型建模 - 实体关联条件

> 在非正式的情况下，**当任何实体与任何其他实体有共同的属性时**，它们都可能与其他实体关联
> Informally, any entity might relate with any other entity when they both share common attributes.

relation - 注重一个，互相离不开引用（“关系”）

# 关系型模型：Relational Model

![](/static/2021-01-17-15-44-39.png)

> **任何实体或关系可以被建模为一个Relation**，并可以被可视化映射到一个二维表

* **有序**属性（列）集合 an **ordered** set of attributes(columns)
* 元组集合（行），每行为一条数据实例，a set of tuples(rows), which represents instances
* <font color="deeppink">存在一个特定属性（PK），可以唯一标识relation中的元组（一行数据实例）</font> There exists a specific attribute that uniquely identifies a tuple in the relation,
  * 如，顺序数字 1，2，3，...
  * 如，逻辑值，ID，学号

:candy: relation只是对实体&关系的一个建模
，关系型数据库中可以将relation映射成二维表格，，一个relation实例又可看为一个表格，包含多个tuples数据

---

![](/static/2021-01-17-16-16-51.png)

:orange:例子

* PK - accountnumber
* 每行为一个tuple实例
* 4列属性字段

:orange: Query

* 检索需求属性，限制属性过滤不相关元组attributes of interest to be retrieved, and constrained attributes to filter out irrelevant tuples.
* Return the names of those customers with active accounts and balance > £500
  * `SELECT name FROM BankAccount WHERE balance > 500 AND status = 'active'`
    * str尽量用单引号
    * 关键字大写，属性小写

# 标准表示形式：Database Schema Formalism

![](/static/2021-01-17-16-36-34.png)
![](/static/2021-01-17-18-04-39.png)

:orange: Relation schema标准表示形式

* `R(A1,A2,...,An)` schema标准表现形式 （数据表逻辑定义信息，属性名，约束条件, ...）
  * R是这个Relation 的名字，
  * A1到An是attributes.（**有序**）
  * 每一个attribute `Ai` 的每一个具体取值都应该落在集合`Di` 中，即 `Ai ∈Di`
    * <font color="deeppink">即，每个attribute都有corresponding domain（字段类型，约束）</font>
* schema`R`的元组`t`，满足为有序值序列，每个值对应`R`中属性字段，满足以下域限制
  * `t = (V1,V2,V3.....Vn), Vi ∈Di`
  * 每个`t`组成`r(R)`的其中一行
* `r(R)`**relation实例**，就是由有限个tuples组成的（**tuples集合**构成）
  * `r(R) = {t1, t2, t3....., tn}; t1 is a tuple of R`
  * 即，RDB中的一个表格实例
* `NULL`
  * 代表未知，不确定，不可使用，缺失值
* **Relational Database Schema** 关系型数据库基模
  * <font color="deeppink">relation集合构成（所有属于同一数据库的Relations构成 database Schema `S`）</font> set of relations that belong to the same database
  * `S = {R1, R2, R3, R4...Rk}∪{NULL}`
  * schema理解成数据库**逻辑信息**，(表格定义描述信息（约束，属性名，字段类型，relation名等）, relational schema & relational db schema完整**定义**)，<font color="deeppink">也就是表定义。。</font>
  * relation指代relational model based db中的表格，由多个tuples组成

---

:candy:例子

![](/static/2021-01-17-18-01-11.png)

* `BankAccount(account, name, balance, status)`
  * `#account ∈ N = {1, 2, 3, ...}`, natural numbers (positive integers)
  * `name ∈ Varchar(50)` max_len 50
  * `balance∈R` 实数
  * `status∈{"active","inactive"};`可枚举有限集合

![](/static/2021-01-17-18-06-59.png)

* `t = (1234567, ‘Thomas C’, 1000, ‘active’)`
* `t[account] = 1234567, t[name] = ‘Thomas C’, t[balance] = 1000, t[status]=‘active’`
* t构成r(R)实例中的一行数据，多个t（t集合）构成r（R）

# 例子-RM-Company

![](/static/2021-01-17-18-42-12.png)
![](/static/2021-01-17-18-43-25.png)

:orange:先通过文本描述标识ER中三大属性，然后表示出各relation的标准表示形式（formalisation） --概念建模

* **（relational DB）Schema**（set of relation，不要跟relation schema搞混）
  * Company
* **Entities** 实体
  * Department
  * Employee
  * Project
  * Dependent
* **Relationships** 关系
  * An employee manages a department.
  * A department may have several locations.
  * A department controls a number of projects.
  * An employee is assigned only to one department,
  * An employee may work on several projects (for each, store hours per week), which are not
  necessarily controlled by the same department.
  * A department controls several projects
  * An employee is supervised by a supervisor, who is another employee.
  * An employee has several dependents, each one corresponding to a specific relationship with the
  employee

---

多个relation（relation集合）构成DB Schema

![](/static/2021-01-17-18-52-17.png)
![](/static/2021-01-17-19-05-08.png)

# 约束条件：Relational Constraint

> 所有Relation（及tuple）都应满足的条件
> Condition that must hold on all instances, for each relation

:orange: 约束条件分类

![](/static/2021-01-17-19-10-48.png)

* **key constraint** 键约束
  * 定义属性，唯一确定一个tuple
* **Entity Integrity Constraint** 完整性约束
  * 某些KEY不能为`NULL`
  * 某些KEY必须为`Unique`，不允许重复值
  * 避免某些属性的不一致性&不确定性 inconsistency & uncertainty
* **Referential Integrity Constraint** 引用完整性约束
  * 反映表之间关系

## Key Constraint：Superkey

> Relation R的 Superkey(SK)是一个**包含至少一个属性的属性集合**，可用于确定唯一地标识任何tuple，即**至少包含了一个能唯一确定任何tuple的attribute**
> Superkey (SK) of relation R is a set of attributes containing at least one attribute that uniquely identifies any tuple.

![](/static/2021-01-17-20-18-16.png)

针对任何两个不同tuples `t1` & `t2` ∈`r(R)`，以下蕴含式成立

* `t1 ≠ t2 → t1[SK] ≠ t2[SK]`
* 因为t1!=t2，则他们SK的可以唯一标识数据

:orange: 例子

![](/static/2021-01-17-20-20-11.png)

* `EMPLOYEE(SSN, Ename, Lname, Bdate, Salary, Dno)` 以下属性集合，都可作为SK，来标识**唯一一个**tuple
* `{SSN, Ename, Bdate}`
* `{SSN}`
* `{SSN, Ename}`
* `{Ename, Salary}` <font color="deeppink">这个attribute组合不可以，因为可能有例外情况，正好2条一样的数据</font>

## Key Constraint：Candidate Key

> Candidate Key是**最小的**SuperKey；
> **即具有最小数量属性的集合，它能唯一地识别tuples**
> Candidate Key is the **minimal superkey**; i.e., the set with the smallest number of attributes that uniquely identify tuples.

:orange:Candidate Key 即是SK中**最小的**

* 包含Attributes最少的SK
* 必须用最少Attributes来唯一确定一个tuple

Let `K = {A1, …, Ak}`, then:

> K is candidate key ↔ K’ = K \ {Ai} is not a SK for any Ai ∈ K
>
> the removal of any attribute from K results in K' that is no longer a superkey
>
> 从K candidate key中删除任意属性构成的K' 都不属于superkey（因为K本身需要满足是SK中属性最少的，最小的SK）

* 即，如果从`K`中删除任意属性构成的`K'`为SK，则原`K`不是CK

---

:orange: 例子

![](/static/2021-01-20-18-34-28.png)

* `{SSN, Ename} \ {Ename} = {SSN}`（K'） is SK, thus {SSN, Ename} is not candidate key
* {SSN, Ename, Lname} \ {Ename, Lname} = {SSN} is SK,
  * thus {SSN, Ename, Lname}
is not candidate ey
* {SSN} is the minimal（CK）
  * since {SSN}\{SSN} = { } is not a SK

## Key Constraint: Primary Key

主键约束

![](/static/2021-01-20-19-18-49.png)

> if a relation has **several candidate** keys, one is chosen arbitrarily to be primary key; the rest candidate keys are called secondary keys
> 如一个relation有多个CK，选择其中任意一个作为PK（因为任何CK都可以唯一标识一个tuple）
> 其余CK被称为副键

## 例子：CK & SK & PK

![](/static/2021-01-20-19-24-45.png)

:orange:结论

* 多个CK构成的新属性集合不是CK

## 完整性约束：ENTITY INTEGRITY CONSTRAINT

:orange: PK不能为`NULL`

> Principle: Primary Key (PK) cannot be NULL in any tuple of instance r(R).
> 任何r(R) relation实例中，任何tuple中的PK的值都不能为NULL

![](/static/2021-01-20-19-29-12.png)

* `t[PK] ≠ NULL for any tuple t in r(R)`
* 且如果PK包含多个属性（<font color="deeppink">composite key/attribute</font>），任何属性也不能为`NULL` If PK has several attributes, NULL is not allowed in any of these attributes
  * 例如，`{student_id, course_id}`为PK组合集合（两个CK构成的SK），其中`student_id` & `course_id`都不能为`NULL`

:candy:注意某些非键属性也可能被设置，值不能为`NULL`， 如`name`字段设计为，值必须唯一，即<font color="red">PK一定不能为NULL，不能为NULL的属性不一定是PK</font>

> Note: There might be non-key attributes which are not allowed to be NULL, e.g., Employee’s surname specified by the database designer, e.g., unique value.

## 引用完整性约束：Referential Integrity Constraint

针对外键

![](/static/2021-01-20-19-31-59.png)

引用完整性约束 - 作用

> referencing relation R1 and referenced relation R2
> There exists an attribute FK in R1 that either has exactly the same value with the PK in R2 or is NULL 

![](/static/2021-01-20-22-27-15.png)

* R1 references R2, R1中存在一个属性作为外键FK，与R2中的PK值相同（或为`NULL`）
* `t1[FK] references t2[PK] → t1[FK] = t2[PK] or t1[FK] = NULL`
* `R1[FK] references R2[PK] → R1[FK] = R2[PK] or R1[FK] = NULL`

---

:orange:如果有`t1[FK]=NULL`则R1的FK不能是构成其PK的任何一部分

* 如果FK为NULL，又为PK的一部分则违反了**完整性约束 entity integrity constraint(PK不能为NULL)**

### 例子：引用完整性约束

![](/static/2021-01-20-22-32-57.png)

* 也不为relational schema design，无法表示其relationship的抽象
* 如果ISBN（PK）不为NULL，ISBN（FK）也不能为NULL，否则造成数据的不一致，无法参考

![](/static/2021-01-21-00-42-59.png)

# 总结

![](/static/2021-01-20-22-38-32.png)

* SK用于唯一标识relation中tuple
* 如果K是SK，那么K的任何**超集（K & K'，K'中每个元素都在K中，且K中可能包含K'没有的元素，那么K就是一个超集，K'是K的子集**）都为SK
* CK是最小的SK
  * 如果K的任何子集都不为SK，那么称K是CK（K/{A...} = K'!= SK）
* 一个relation可以包含多个不同CK（最小SK）
  * 选取其中一个CK作为其PK
  * 因为每个CK都一定能标识唯一tuple
* 引用了外键的relation的FK要么为`NULL`（<font color="deeppink">但这种情况下不应该为该relation的PK部分</font>），要么与被参考的relationPK有相同值