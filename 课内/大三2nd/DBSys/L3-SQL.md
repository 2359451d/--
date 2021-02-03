# Lecture 3 - SQL

Part A.2: SQL & Advanced SQL

![](/static/2021-02-01-22-09-29.png)

* SQL （Structured Query Language）
  * 使用SQL定义表结构（db schema） & 创建表（relation）
  * SQL - KEY/Integrity/referential constraint 键，完整/参考性约束
* `SELECT`子句 select clause for selection queries - DQL数据定义语言
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
* [SELECT-FROM-WHERE](#select-from-where)
  * [例子](#例子-1)
  * [（Inner）JOIN例子](#innerjoin例子)

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

# SELECT-FROM-WHERE

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

