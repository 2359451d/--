# MySQL Study

study note

- [MySQL Study](#mysql-study)
  - [约束 Constraint](#约束-constraint)
    - [非空约束:not null](#非空约束not-null)
    - [唯一约束：unique](#唯一约束unique)
    - [主键约束：primary key](#主键约束primary-key)
      - [列级约束：定义主键](#列级约束定义主键)
      - [表级约束：定义主键](#表级约束定义主键)
      - [复合主键使用](#复合主键使用)
      - [主键相关的术语](#主键相关的术语)
      - [主键有什么用](#主键有什么用)
      - [主键分类](#主键分类)
      - [(*)mysql提供PK自增: auto_increment](#mysql提供pk自增-auto_increment)
    - [外键约束：foreign key](#外键约束foreign-key)
      - [FK可以为null](#fk可以为null)
      - [FK可以不为PK，但必须唯一](#fk可以不为pk但必须唯一)
  - [存储引擎(了解)](#存储引擎了解)
    - [查看当前支持的存储引擎](#查看当前支持的存储引擎)
    - [MyISAM](#myisam)
    - [InnoDB](#innodb)
    - [MEMORY(\HEPA)](#memoryhepa)
  - [事务：Transaction](#事务transaction)
    - [事务相关步骤](#事务相关步骤)
    - [事务特性](#事务特性)
      - [事务隔离性：事务并发](#事务隔离性事务并发)
    - [事务分类](#事务分类)
    - [演示事务](#演示事务)
      - [关闭事务自动提交: start transaction](#关闭事务自动提交-start-transaction)
      - [设置&查看：事务隔离级别](#设置查看事务隔离级别)
      - [演示：read committed](#演示read-committed)
      - [演示：repetable read](#演示repetable-read)
      - [演示：serializable](#演示serializable)
  - [explain：查看sql语句执行计划](#explain查看sql语句执行计划)
  - [索引：index](#索引index)
    - [创建 & 删除索引](#创建--删除索引)
    - [为什么索引效率高](#为什么索引效率高)
    - [何时考虑给字段添加索引](#何时考虑给字段添加索引)
    - [索引底层原理](#索引底层原理)
    - [索引分类](#索引分类)
  - [View: 视图](#view-视图)
    - [View vs Table](#view-vs-table)
    - [创建 & 删除视图](#创建--删除视图)
    - [面向视图操作](#面向视图操作)
    - [视图无法更新的情况](#视图无法更新的情况)
    - [视图逻辑结构更新](#视图逻辑结构更新)
    - [视图结构查看](#视图结构查看)
    - [视图作用（seldom used）](#视图作用seldom-used)

## 约束 Constraint

什么是约束?

- 创建表的时候,可以给<font color="red">表字段添加相应约束</font>
  - **为了保证表中数据的合法性,有效性,完整性**

常见约束?

- 非空约束`not null`
  - 约束的字段不能为`null`
- **唯一约束`unique`**
  - 约束的字段不能重复
- **主键约束`pk`**
  - 约束的字段既不能为`null`,也不能重复
- **外键约束`fk`**
- 检查约束`check`
  - 注意Oracle有check'约束,**mysql没有**

### 非空约束:not null

🍊 not null**只有列级约束，没有表级约束**

```mysql
drop table if exists t_user;
create table t_user(
    id int,
    username varchar(255) not null,
    password varchar(255)
);

<!-- 插入数据 -->
insert into t_user(id,username,password) values(1,'123');
```

### 唯一约束：unique

- 唯一约束修饰的字段，
  - **有唯一性，不能重复**
  - 但**可以为`null`**

![](/static/2020-09-16-14-29-25.png)

给两个列&多个列**联合添加1个unique【表级约束】**

- 与多个**字段单独添加unique【列级约束】**不同

![](/static/2020-09-16-14-31-34.png)
![](/static/2020-09-16-14-34-02.png)

### 主键约束：primary key

🍊 特点

- 不能为空
- 不能重复

#### 列级约束：定义主键

怎么给一张表添加主键约束？（**列级约束**）

```mysql
drop table if exists t_user;
create table t_user(
  id int primary key,
  username varchar(255),
  email varchar(255)
);

insert into t_user(id, username, email) values(1,'zs','zs@123.com');
insert into t_user(id, username, email) values(1,'ls','ls@123.com');// not work
insert into t_user(username, email) values('ww','ww@123.com');// not work
select * from t_user;
```

#### 表级约束：定义主键

```mysql
drop table if exists t_user;
create table t_user(
  id int,
  username varchar(255),
  primary key(id)
);

insert int t_user(id,username) values(1,'zs');
```

#### 复合主键使用

不需要掌握

- 两个字段联合起来组成主键，联合主键不能重复

```mysql
drop table if exists t_user;
create table t_user(
  id int,
  username varchar(255),
  primary key(i，username)
);

insert int t_user(id,username) values(1,'zs');
```

#### 主键相关的术语

主键约束

- `primary key`

主键字段

- id字段添加pk后，id叫做**主键字段**

主键值

- **id字段中每个值都是主键值**

#### 主键有什么用

表的设计3范式中有要求

- **任何一张表都应该有主键**

🍊 主键的作用

- 主键值是**这行记录在这种表中的唯一标识**

#### 主键分类

🍊 根据**主键字段的字段数量**来划分

- **单一主键**
- 复合主键(弱实体)
  - **多个字段联合组成一个PK**
  - 不建议使用，<font color="blue">违背三范式</font>

🍊 根据**主键性质**划分

- 自然主键
  - <font color="red">主键值最好是一个与业务无关的自然数</font>
- 业务主键
  - **主键值和系统业务挂钩**，如银行卡号做PK,身份证号做PK，<font color="blue">不推荐使用，因为以后业务一旦改变，主键值可能也需要改变（有时候可能没办法改变）</font>

🍬 <font color="red">一张表的每条记录的主键约束只能为一个</font>

#### (*)mysql提供PK自增: auto_increment

`auto_increment`

- 被修饰的字段**自动维护一个自增数字**
  - `1`起始，`1`递增

🍊 oracle也提供一个自增机制

- **序列对象sequence**

![](/static/2020-09-16-16-51-31.png)

```mysql
drop table if exists t_user;
create table t_user(
  id int primary key auto_increment,
  username varchar(255)
);
insert into t_user(username) values('a');
insert into t_user(username) values('a');
insert into t_user(username) values('a');
select * from t_user;
```

### 外键约束：foreign key

外键约束相关术语

- 外键约束
  - FK
- 外键字段
- 外键值

🍊 例子

![](/static/2020-09-16-17-09-16.png)

- 其中一张表作为父表，引用其他表PK作为FK的表作为子表
  - 子：student，父：class
- 删除数据
  - 先删子，再删父
- 添加/创建数据
  - 先父，再子

```mysql
drop table if exists t_student;
drop table if exists t_class;

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

#### FK可以为null

#### FK可以不为PK，但必须唯一

一般情况都选PK作为FK

## 存储引擎(了解)

即，表的存储方式

- **不同引擎表的存储方式不同**
  - <font color="blue">只在mysql中，（oracle有对应机制，但不叫存储引擎，直接叫表的存储方式）</font>
- mysql支持很多存储引擎，每条引擎对应一种不同存储方式
  - <font color="green">每个存储引擎有自己的优缺点，需要在合适的时机选择合适的存储引擎</font>
- mysql
  - **默认存储引擎：`InnoDB`**
  - **默认字符集`utf8`（配置后）**

🍊 完整的建表语句

- <font color="red">建表时，可以指定存储引擎&字符集</font>

```mysql
create table 't_x'(
  'id' int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

### 查看当前支持的存储引擎

`show engines \G`

- mysql 5.5.36支持**9个存储引擎**

### MyISAM

🍊 特点

![](/static/2020-09-16-21-08-25.png)

- **最常用的引擎，但不是默认的**
- **不支持事务**
- 以**3种文件**表示每个表
  - 格式文件 - 存储**表结构定义**`mytable.frm`
  - 数据文件 - 存储**表行的内容**`mytable.MYD`
  - 索引文件 - 存储**表上索引**`mytable.MYI`

🍬 优点

- 可**压缩，节省存储空间**
- 可**转换为只读表，提高检索效率**

🍬 缺点

- **不支持事务**

```cmd
      Engine: MyISAM
     Support: YES
     Comment: MyISAM storage engine
Transactions: NO
          XA: NO
  Savepoints: NO
```

### InnoDB

![](/static/2020-09-16-21-12-44.png)

- mysql**缺省引擎**
- **表结构** - `...frm`文件中
- **表数据** - `tablespace`表空间（逻辑概念）中
  - **无法被压缩，无法转换为只读文件**
- <font color="red">mysql崩溃后，innoDB提供自动恢复</font>
- <font color="blue">支持级联删除&级联更新</font>

🍬 优点

- **支持事务**
- **支持行级锁**
  - <font color="red">最安全，数据安全得到保障</font>
- **支持外键**

```cmd
      Engine: InnoDB
     Support: DEFAULT
     Comment: Supports transactions, row-level locking, and foreign keys
Transactions: YES
          XA: YES
  Savepoints: YES
```

### MEMORY(\HEPA)

```cmd
      Engine: MEMORY
     Support: YES
     Comment: Hash based, stored in memory, useful for temporary tables
Transactions: NO
          XA: NO
  Savepoints: NO
```

🍬 优点

- **查询速度最快**

🍬 缺点

- **不支持事务**
- **数据容易丢失**
  - 因为所有**数据&索引都存储在内存中**

## 事务：Transaction

什么是事务？

> 通过一组逻辑操作单元（一组DML——sql语句），将数据从一种状态切换到另外一种状态

- **完整的业务逻辑单元**，不可再分
  - 比如银行账户转账，需要2条update语句，**必须同时成功，或者同时失败，需要使用数据库事务机制**
- <font color="red">为了保证数据的完整性，安全性</font>

🍊 **事务只跟`DML`语句有关**，因为跟数据相关

- insert
- delete
- update

### 事务相关步骤

1、开启事务`start transation;`
2、编写事务的一组逻辑操作单元（多条sql语句）`DML`
3、提交事务或回滚事务`commit/rollback;`

### 事务特性

事务包括四大特性：ACID

- A - **原子性**
  - 事务：最小的工作单元，不可再分
- C - **一致性**
  - 事务必须**保证多条DML语句同时成功&同时失败**
- I - **隔离性**
  - **事务A & 事务B之间有隔离**
- D - **持久性**
  - 最终数据**必须持久化到硬盘文件中**，事务才算成功结束

#### 事务隔离性：事务并发

存在隔离级别，**理论上**级别包括4个

1. **读未提交 read uncommitted**
   1. 对方事务未提交，当前事务可以读取到对方未提交的数据
   2. 问题：存在**脏读现象**
2. **读已提交 read committed**
   1. 对方事务提交后我方读取到
   2. **解决：脏读问题**
   3. 问题：**不可重复读（修改后再提交，无法读到原来未修改的数据）**
   4. <font color="red">【oracle默认隔离级别】类似普通synchronized，少了CAS的一层验证-更新机制</font>
3. **可重复读 repeatable read**
   1. 隔离，**解决：不可重复读问题**
   2. 问题：**幻读(这边删了，那边还未删)**
   3. <font color="red">【mysql默认隔离级别】</font>
4. **序列化读/串行化读 sequential read**
   1. **解决所有问题，不存在锁安全问题**
   2. <font color="red">效率低，需要事务排队</font>

🍬 mysql默认隔离级别：**可重复读**

### 事务分类

隐式事务

- **没有明显开启和结束事务的标志**
  - 如DML语句本身就是一个事务（不关闭自动提交事务情况下）

显示事务

- **有明显的开启&结束事务的标志**
- 开启事务
  - `set autocommit=0`&`start transaction;`取消自动提交事务的功能
  - `DML`
  - `commit/rollback;`提交事务或回滚事务

### 演示事务

🍊 mysql事务默认情况下自动提交

- 自动提交：**只要执行任意一条DML语句则提交一次**

#### 关闭事务自动提交: start transaction

🍬 怎么**关闭自动提交**？

- `set autocommit=0;`&
- `start transaction;`
- 不关闭，每执行一条DML就提交更新一次事务

![](/static/2020-09-16-22-29-54.png)

#### 设置&查看：事务隔离级别

设置事务全局隔离级别

`set global transaction isolation level <isolation_level>;`

查看事务全局隔离级别

`select @@global.tx isolation;`5.7
`select @@tx_isolation;`8.0

#### 演示：read committed

1. 设置全局隔离级别：read committed
   1. ![](/static/2020-09-16-22-42-13.png)
2. 事务B执行，未提交
   1. ![](/static/2020-09-16-22-45-10.png)
3. 读，已提交（可读到最新数据）
   1. ![](/static/2020-09-16-22-46-31.png)

#### 演示：repetable read

![](/static/2020-09-16-22-48-55.png)

- 事务B，怎么增删改DML数据，**事务A永远读的硬盘备份数据（幻读）**

#### 演示：serializable

设置全局隔离级别 - 序列化（串行）读: `set global transaction isolation level serializable;`

![](/static/2020-09-16-22-58-24.png)
![](/static/2020-09-16-22-58-59.png)

- 需要**阻塞排队**，效率低，**但安全**

## explain：查看sql语句执行计划

`explain sql`

![](/static/2020-09-17-00-13-49.png)

## 索引：index

🍬 注意

- <font color="red">主键&具有unique约束的字段会自动添加索引</font>
  - **根据PK查询效率较高，尽量根据PK检索**
- <font color="blue">使用模糊查询索引可能会失效，如`%A%`第一个通配符使用`%`，效率低</font>

什么是索引？

- 类似目录，通过索引可以快速找到对应资源
- 两种检索方式
  - **全表扫描**
  - **根据索引检索（效率高）**

### 创建 & 删除索引

🍊 **添加索引给字段**

`create index <index_name> on <t_name>(field);`

- 给sal字段添加索引
  - **`create index emp_sal_index on emp(sal);`**
- `select ename,sal from emp where ename='SMITH';`
  - 无索引时，会**扫描全表，ename所有的值**
  - **ename添加索引时**，会**根据索引扫描，快速定位**

🍊 **删除索引**

`drop index <index_name> on t_name;`

### 为什么索引效率高

🍊 为什么索引提高检索效率？

- 原理：缩小扫描范围

🍬 不能乱添加索引

- <font color="red">索引也是数据库中的对象，需要不断维护，有维护成本</font>
- 比如，表中数据经常被修改，不适合添加索引
  - **因为数据一旦修改，索引需要重新排序维护**

### 何时考虑给字段添加索引

🍊 满足什么条件给字段添加索引？

- **数据量庞大**
  - 根据客户需求
- **字段很少有DML操作**
  - 因为字段进行DML，索引也需要维护
- **字段经常出现在where子句中**

### 索引底层原理

使用B树

![](/static/2020-09-17-00-55-18.png)

- 通过B树**缩小扫描范围，提高检索效率**
- 每个索引对象实质就是一个树对象
  - **存放数据&对应物理地址**
- <font color="red">索引对象底层会自动排序&分区</font>，索引对象根据存储引擎的不同，**存放在内存/硬盘**
  - 一旦**通过索引对象，获取【数据关联的物理地址】**后，替换DQL where条件**直接定位原表中数据**，效率最高

### 索引分类

- 单一索引
  - **单个字段**添加索引
- 复合索引
  - **多个字段联合**添加1个索引
- 主键索引
  - **PK**会**自动添加**索引
- 唯一索引
  - **unique约束的字段会自动添加**索引

## View: 视图

from different perspetive to see the data
站在不同角度看待数据（同一张表的）

🍬 **对视图进行增删改查会影响到原表数据**if do crud on view, it will change the original data

- 通过视图影响数据，而不是直接操作原表

### View vs Table

🍊 视图&表的区别

- 视图：不占用物理空间，仅仅保存sql逻辑
- 表：占用物理空间
- 视图&表的使用方式完全相同

### 创建 & 删除视图

创建视图

- `create view <view_name> as <DQL>;`
  - only **DQL support** view creation, but we can do **crud operations on view**

删除视图

- `drop view <view_name>;`

### 面向视图操作

**select** the view

- `select ... from <view_name>;`

**update the view(crud**,to change the original table indirectly)

- `update <view_name> set field=...,field=... where ...;`

**delete the data from view(crud**, to delete the original table data indirectly)

- `delete from <view_name> where...`

### 视图无法更新的情况

包含以下关键字的sql

- 分组函数
- distinct
- group by
- having
- union
- union all

### 视图逻辑结构更新

```mysql
#方式一：创建or替换（整个替换）
CREATE OR REPLACE VIEW test_v7
AS
SELECT last_name FROM employees
WHERE employee_id>100;
```

```mysql
#方式二:整表结构更新
ALTER VIEW test_v7
AS
SELECT employee_id FROM employees;
```

### 视图结构查看

`DESC view_name;`
`show create view test_v7;`

### 视图作用（seldom used）

🍬 总结：视图优点

- sql语句提高重用性，效率高
- <font color="red">和表实现分离，实现安全性（对view的字段别名CRUD操作，可以不直接接触表的情况下，修改表)</font>

![](/static/2020-09-17-13-52-26.png)

- **视图可以隐藏表的实现细节**。<font color="red">保密级别较高的系统，数据库只对外提供相关视图</font>，java程序员只对视图对象进行CRUD（view can shield the details of the table, and for the high-level confidentiality of the system, db only provides the view to the external access & java developers only operates CRUD on views）
  - **对外只知道有别名的字段，不知道原表的原字段名**(external only see the renaming of the fields, but cannot know the original(actual) name of that)