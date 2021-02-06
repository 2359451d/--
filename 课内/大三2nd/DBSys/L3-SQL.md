# Lecture 3 - SQL

Part A.2: SQL & Advanced SQL

![](/static/2021-02-01-22-09-29.png)

* SQL （Structured Query Language）
  * 使用SQL定义表结构（db schema） & 创建表（relation）
  * SQL - KEY/Integrity/referential constraint 键，完整/参考性约束
* `SELECT`声明 select clause for selection queries - DQL数据定义语言
  * Multi-sets & sets
  * `NULL`值处理 dealing with NULL values
* Nested Correlated & Uncorrelated Queries

* [Lecture 3 - SQL](#lecture-3---sql)
* [背景：PHILOSOPHY OF THE DECLARATIVE LANGUAGE](#背景philosophy-of-the-declarative-language)
* [建库-数据库结构：CREATE SCHEMA/DATABASE](#建库-数据库结构create-schemadatabase)
* [建表：CREATE TABLE](#建表create-table)
  * [属性/字段 & 数据类型：ATTRIBUTES & DOMAINS](#属性字段--数据类型attributes--domains)
  * [字段约束：Value Constraint](#字段约束value-constraint)
  * [主/候选键约束：Key Constraint](#主候选键约束key-constraint)
  * [参考性约束：Referential Constraint](#参考性约束referential-constraint)
    * [例子](#例子)
* [SELECT-FROM-WHERE & JOIN（内）](#select-from-where--join内)
  * [例子](#例子-1)
  * [（Inner）JOIN例子](#innerjoin例子)
* [AS用法& 自连：TABLE AS A VARIABLE](#as用法-自连table-as-a-variable)
  * [自连例子：self-join](#自连例子self-join)
  * [是否省略WHERE：IF WHERE IS MISSING](#是否省略whereif-where-is-missing)
* [*使用：USE OF THE ASTERISK](#使用use-of-the-asterisk)
* [重复值&唯一值集合：Set/Multi-sets](#重复值唯一值集合setmulti-sets)
* [UNION使用例子](#union使用例子)
* [TF & UNKNOWN(NULL值处理):Three-Valued Logic](#tf--unknownnull值处理three-valued-logic)
  * [NULL值处理例子](#null值处理例子)
* [嵌套查询（WHERE子句,IN,ALL,Exists）：Nested(inner) query](#嵌套查询where子句inallexistsnestedinner-query)
  * [非相关子查询-IN例子：Nested Uncorrelated Query](#非相关子查询-in例子nested-uncorrelated-query)
  * [非相关子查询-ALL例子：Nested Uncorrelated Query](#非相关子查询-all例子nested-uncorrelated-query)
  * [相关子查询-IN例子：Nested Correlated Query](#相关子查询-in例子nested-correlated-query)
  * [相关子查询-EXISTS使用：Nested Correlated Query](#相关子查询-exists使用nested-correlated-query)
* [Worked Example](#worked-example)

# 背景：PHILOSOPHY OF THE DECLARATIVE LANGUAGE

![](/static/2021-02-01-22-17-12.png)

* SQL，结构化语言 - 声明性语言
  * declare what to do rather than how to do(不关心具体实现步骤)
  * 与过程语言区分开，different from preocedural language

:orange: 大多数据库管理系统都遵从标准SQL

# 建库-数据库结构：CREATE SCHEMA/DATABASE

即，建库,这里理解成 create (relational) database schema(不是单个relaiton表的schema)

![](/static/2021-02-01-22-24-41.png)

`CERATE SCHEMA <db_name>;`

* 等同于MYSQL中`CREATE DATABASE <db_name>;`

# 建表：CREATE TABLE

![](/static/2021-02-01-22-26-56.png)
![](/static/2021-02-01-23-46-29.png)

```sql
create table <tb_name>(
    field data_type,
    field data_type,
    field data_type,
    ...
);

CREATE TABLE IF NOT EXISTS t_name(
  ....
);
```

```sql
create table t_class(
  cno int,
  cname varchar(255),
  primary key(cno)
);

create table t_student(
  sno int,
  sname varchar(255),
  classno int,
  foreign key(classno) references t_class(cno)
)
```

## 属性/字段 & 数据类型：ATTRIBUTES & DOMAINS

![](/static/2021-02-01-22-27-43.png)
![](/static/2021-02-01-23-30-18.png)

:orange: 数值类型 numeric data types

* 整型`INT`
* 浮点，实数`REAL`, `DECIMAL(n,m)`
  * `DECIMAL(3,2)`,`3` digits, `2`digits adter the decimal, `9.99`

:orange: char, string data types

* `CHAR(n)` Fixed length
  * `n`chars
* `VARCHAR(n)` 可变字符类型 variable length
  * `0-n`chars

:orange: Bit-string data types(比特串)

* `BIT(n)` fixed length
* `BIT VARYING(n)` varying length

:orange: Boolean data type

* `TRUE/FALSE/NULL`

:orange: DATE data type

* `YEAR`,`MONTH`,`DAY`
* `YYYY-MM-DD`

[时间戳，日期区间其他类型](https://www.postgresql.org/docs/9.5/datatype.html)

## 字段约束：Value Constraint

所有约束

* 非空约束(not null)
* 唯一性约束(unique)
* 主键约束(primary key) PK
* 外键约束(foreign key) FK
* 检查约束(check

![](/static/2021-02-01-23-53-51.png)
![](/static/2021-02-01-23-50-49.png)

:orange: 定义默认值

* `DEFAULT{value}`

:orange: 列级非空约束

* `NOT NULL`
  * 属性不允许设置 `NULL`约束
* 例子 `DNO INT NOT NULL DEFAULT;`

:orange: **check检查约束** - check clause range domain constraint (范围值约束)

* `CHECK <表达式>`，用于指定需要检查的限定条件
* 例子 `Dnumber INT NOT NULL CHECK（Dnumber>0 AND Dnumber<21）` 对Dnumber字段进行值检测

## 主/候选键约束：Key Constraint

![](/static/2021-02-01-23-57-58.png)

* **主键约束** key constraint
  * PK值唯一，不允许重复值
* **完整性约束** entity integrity constraint
  * PK不能为`NULL`，即，任何构成PK部分的字段都不能为`NULL`

:orange: PK clause

* `Dnumber INT NOT NULL, PRIMARY KEY(Dnumber)`
* `<FIELD> TYPE VALUE_CONSTRAINT, PRIMARY KEY(FIELD_NAME)`

```sql
create table t_user(
  id int,
  username varchar(255),
  primary key(id)
);
```

:orange: CK clause (值唯一，候选键/副键，也可用来唯一标识某字段，)

* specifies CK(candidate key or secondary key)
* `Dname VARCHAR(15) NOT NULL, UNIQUE(Dname)`

## 参考性约束：Referential Constraint

![](/static/2021-02-02-00-05-49.png)

* `FOREIGN KEY (FK_NAME) REFERENCES Relation(PK)`

:candy: 例子

![](/static/2021-02-02-17-09-49.png)

* `FOREIGN KEY (Super_SSN） REFERENCES Employee(SSN)`
* `FOREIGN KEY (Mgr_SSN) REFERENCES Employee(SSN)`

:orange: 存在参考性约束，DBMS负责维护CRUD操作的一致性，存在以下事件

* **Triggered actions** - UPDATED/DELETED
  * 外键 `Mgr_SSN,Super_SSN`，当`SSN`更新/删除时
    * `ON DELETE SET NULL/ DEFAULT/ CASCADE`
    * `ON UPDATE SET NULL/ DEFAULT/ CASCADE`
* <font color="deeppink">`CASCADE`,触发事件时，引用relation自动广播</font>

:candy: 例子

* 更新/删除`SSN`字段，所有引用FK也强制更新/删除 `ON UPDATE/DELETE CASCADE`  when SSN is updated, then all foreign keys refer to it should be updated: ON UPDATE CASCADE

### 例子

* 每个约束可具有标签
  * `CONSTRAINT <Label> <CONSTRAINT_TYPE>`
* <font color="deeppink">建议使用，方便管理约束条件</font>

![](/static/2021-02-03-00-47-47.png)

* 删除 `department`时
  * `employee`的字段`dno`设置为默认值`1`，因为FK约束 `ON DELETE SET DEFAULT`
  * `departmentlocation`字段`dno`数据直接删除，因为约束 `ON DELETE CASCADE`
  * 触发以上事件将db从一致状态转换到另一个一致状态 transfer db from one consistent state into another consistent state

# SELECT-FROM-WHERE & JOIN（内）

![](/static/2021-02-03-01-06-08.png)

```sql
SELECT <attribute list>
FROM <table,...>
WHERE <condition>
```

![](/static/2021-02-03-01-07-36.png)

* 只用SQL进行声明，不关心底层实现
  * 如何加载数据至内存
  * 如何进行查询，匹配

## 例子

![](/static/2021-02-03-01-10-11.png)

## （Inner）JOIN例子

![](/static/2021-02-03-01-13-09.png)

内连 - 生成笛卡尔积

* 图例为`SQL92`写法，WHERE中包括 selection condition（筛选） & join condition，不推荐

```sql
-- sql 99

SELECT Fname, Lname, Address
FROM Employee e JOIN Department d
ON e.DNO = d.Dnumber
WHERE d.Dname = 'Research'
```

---

![](/static/2021-02-03-01-43-04.png)

```sql
-- sql 99

SELECT Pnumber, Dunm, Lname, Address, Bdate
FROM Project p JOIN Department d
ON p.Dum = d.Dnumber
JOIN  Employee e
ON d.dnumber = e.Dno
WHERE p.Plocation = 'Stafford'; 
```

# AS用法& 自连：TABLE AS A VARIABLE

![](/static/2021-02-04-12-23-28.png)

利用`AS`，将relation作为其relation类型的变量使用

* 最典型应用 - 自连（内连）

## 自连例子：self-join

![](/static/2021-02-04-12-28-34.png)

## 是否省略WHERE：IF WHERE IS MISSING

![](/static/2021-02-04-13-11-24.png)

* 如果单纯查询一个relation
  * 不需要筛选信息情况下where可省
* 如果涉及2个及2个以上的relation
  * 必须使用where进行FKPK匹配，否则只产生笛卡尔效应（笛卡尔乘积） CROSS (Cartesian) PRODUCT: all possible tuple combinations! ，无意义低效操作 Each tuple from EMLOYEE is concatenated with each tuple from DEPARTMENT…disaster, computationally heavy, and meaningless!

---

:candy: 例子

![](/static/2021-02-04-13-13-51.png)

* omit where clause - generate meaningless tuples

# *使用：USE OF THE ASTERISK

![](/static/2021-02-04-13-15-38.png)

# 重复值&唯一值集合：Set/Multi-sets

![](/static/2021-02-04-13-18-10.png)

:orange: Set

* 包含unique elements

:orange: Multiset

* 值可能重复 might have duplicates

:orange: 可选操作

* `UNION`
* `EXCEPT`
* `INTERSECT`
* `IN/EXIST/ALL`多行子查询
* 单行子查询 `> < = >= <=`

:orange: `DISTINCT`

* 生成set，建立索引index structure

:candy: <font color="deeppink">注意，如果生成Multiset，需要转换成set后进行操作</font>

# UNION使用例子

![](/static/2021-02-04-13-52-12.png)

* 将复杂查询分解成2个子查询后UNION，split this into two sub-queries and then use the set UNION operator
over the partial results
* <font color="deeppink">确保这两个子查询为set（值唯一），使用`DISTINCT`</font>

---

subquery1

![](/static/2021-02-04-13-55-40.png)

* 查询员工姓为smith的所有project
* 注意此例可能有多个员工为同一个project工作，所以可能产生project number的重复值，需要使用`DISTINCT`
* 查询结果为SET，注意`( );`

subquery2，同理

![](/static/2021-02-04-13-58-51.png)

UNION得到最终query

![](/static/2021-02-04-14-06-54.png)

# TF & UNKNOWN(NULL值处理):Three-Valued Logic

![](/static/2021-02-04-17-05-46.png)

SQL支持3类逻辑

* 1 - TRUE
* 0 - FALSE
* 其他或`NULL`，`UNKNOWN`
* 附：`<=>` 安全等与，既能判断null又能判断普通数值，但是可读性差

:orange: 任何与`NULL`进行逻辑比较的值都计算为`UNKNOWN`

* 因此当某字段可能有`NULL`值时，`WHERE`中使用`IS NULL`或`IS NOT NULL`进行处理
  * `WHERE`只匹配`TRUE`的tuples，不然会输出为`UNKNOWN`而跳过检索

---

逻辑表

假设`TRUE(1), FALSE(0), UNKNOWN(0.5)`

* `AND` - 取min
  * 因此`TRUE AND UNKNOWN = UNKNOWN`
  * 因此`FALSE AND UNKNOWN = FALSE`
* `OR` - 取max
* `NOT` - `1-x`
  * `NOT TRUE = 1-1=0=FALSE`
  * `NOT FALSE = 1-0=1=TRUE`
  * `NOT UNKNOWN = 1-0.5=0.5=UNKNOWN`

## NULL值处理例子

![](/static/2021-02-04-17-29-39.png)

* 如果WHERE声明中利用`=`判断某字段是否为`NULL`，最后会转换成判断该字段是否为 `UNKNOWN`，因此不会产生任何检索结果

:candy: 使用 `IS (NOT) NULL`操作符

# 嵌套查询（WHERE子句,IN,ALL,Exists）：Nested(inner) query

![](/static/2021-02-04-17-35-05.png)

> Nested query is a query within another (outer) query; 

* `WHERE` (outer) query
  * 嵌套查询 - `SELECT-FROM-WHERE` block (**inner)** query
* <font color="deeppink">嵌套查询结果又作为 outer WHERE的输入</font>，可应用操作
  * `IN`
  * `ALL`
  * `EXISTS`
* 聚合操作中常见。。

:orange:嵌套查询分类

* **Nested Uncorrelated Query 嵌套非相关子查询**
  * 先执行嵌套查询，再执行外部查询（where）first execute the nested query, and then execute the outer query using inner’s output
  * 其中，嵌套查询的结果会被缓存 storing in cache memory the results
* **Correlated Query 相关子查询**
  * 每个外查询都执行嵌套查询，for each tuple of the outer query, we execute the nested query.
  * 即，根据外查询的数量来多次执行内部嵌套查询 there is a correlation between outer query and inner query, such that the inner query is executed as many times as the number of the tuples in the outer query

## 非相关子查询-IN例子：Nested Uncorrelated Query

![](/static/2021-02-04-17-44-47.png)

嵌套非相关子查询 - `IN`操作符连接 inner query结果 作为 outer query的输入

* set operator
* 用于检查某元素是否属于 set/multiset check whether an element belongs to a set or multiset
* 内查询只执行一次

:orange:例子

* `IN(1,2,3);` in set, as the input of outer query

---

:orange: 例子2 - where子句使用（select-from-where结构）

![](/static/2021-02-04-18-34-36.png)

## 非相关子查询-ALL例子：Nested Uncorrelated Query

![](/static/2021-02-04-18-40-46.png)

* 两个uncorrelated Query
  * 1.找到所有在部门5工作的员工工资（multiset）
  * 每次拿一个employee tuple比较，是否大于1.中multiset的所有tuples
* inner query只执行一次
  * 之后结果缓存，用于跟outer query中所有tuples进行比较

## 相关子查询-IN例子：Nested Correlated Query

![](/static/2021-02-04-18-47-30.png)

* **为每个特定outer query tuple都执行一次inner query** For each tuple of the outer query, we execute the inner query!
  * inner query输入 - 来自outer query tuple
* 注意relation变量的使用，用于分辨inner outer query relation
* **变量作用域** variable scope
  * outer - **global scope**
    * 可访问 inner & outer query 的变量
  * inner - local scope

---

![](/static/2021-02-04-18-57-24.png)

* <font color="deeppink">因为有时候嵌套相关子查询使用`IN`过于复杂，可以移除相关子查询中的关系，并将子查询转换为单个查询块</font> sometimes would be complex to use IN in nested correlated query we can remove the correlation & collapse this nested query in the single block
* <font color="red">所以一般何时使用嵌套相关子查询？ - EXISTS</font>

## 相关子查询-EXISTS使用：Nested Correlated Query

![](/static/2021-02-04-19-01-35.png)

`EXISTS`set 操作符

* 用于检测set是否为空（即，内查询输出是否为空，<font color="deeppink">存不存在元素，不关心值</font>）checks whether the inner’s output is an empty set or not, and returns FALSE or TRUE,respectively, e.g., 𝑆 = {} or 𝑆 ≠{}
  * return `FALSE/TRUE`
  * `S={}/ S ≠{}`

---

:orange:例子

![](/static/2021-02-04-19-06-32.png)

* correlation - employee & department
  * 不关心具体哪个department，只关心只要存在一个department匹配（存在1个元素），则EXISTS-TRUE

---

:orange: 例子

![](/static/2021-02-04-19-12-39.png)

* find the last &first name of the employee who works in the deparment with name "Research" (EXISTS - not empty set - TRUE)

---

:orange:例子

![](/static/2021-02-04-19-15-20.png)

# Worked Example

![](/static/2021-02-04-19-17-23.png)

* 获取所有课程得A的学生的姓名

```sql
-- nested correlted query
-- 每个学生执行一次内查询，如果不为'A'分的集合元素为空，则筛选出来该学生，NOT EXISTS

SELECT s.Name
FROM Student as s
WHERE NOT EXISTS(SELECT * FROM Grades as g WHERE s.StudentID = g.StudentID AND Grade <> 'A');
```

