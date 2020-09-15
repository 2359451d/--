# MySQL Study

- [MySQL Study](#mysql-study)
  - [sql & DB & DBMS: 区别和关系](#sql--db--dbms-区别和关系)
  - [表的理解](#表的理解)
  - [SQL语句分类](#sql语句分类)
  - [导入初始化数据(数据表)](#导入初始化数据数据表)
    - [对sql脚本的理解](#对sql脚本的理解)
  - [MYSQL常用命令](#mysql常用命令)
    - [查询当前使用的数据库](#查询当前使用的数据库)
    - [查看库表](#查看库表)
    - [查看表结构 & 表数据](#查看表结构--表数据)
    - [查看创建表的语句](#查看创建表的语句)
    - [终止语句 & 退出mysql](#终止语句--退出mysql)
  - [DQL: 简单查询](#dql-简单查询)
    - [列重命名：字符串的使用](#列重命名字符串的使用)
  - [DQL：条件查询](#dql条件查询)
    - [算术运算符 & between..and](#算术运算符--betweenand)
    - [is null & not null](#is-null--not-null)
    - [and & or：优先级](#and--or优先级)
    - [in](#in)
    - [like: 模糊查询](#like-模糊查询)
  - [数据排序: order by](#数据排序-order-by)
  - [分组函数(多行处理函数)](#分组函数多行处理函数)
    - [count(*) & count()](#count--count)
    - [单行处理函数 & NULL运算](#单行处理函数--null运算)
      - [ifnull()空处理函数(单行)](#ifnull空处理函数单行)
    - [group by & having](#group-by--having)
      - [多字段分组查询](#多字段分组查询)
      - [having vs where](#having-vs-where)

## sql & DB & DBMS: 区别和关系

DB

- **数据库**，硬盘上**文件**的形式存在

SQL

- **结构化查询语言**
  - 标准通用语言，<font color="red">标准的sql适用于所有数据库产品</font>
- **高级语言**
- **SQL语句执行时，实际内部也会进行编译，然后再执行sql**
  - **编译由DBMS完成**，用户看不见

DBMS

- **数据库管理系统**
- 常见：**MYSQL**,ORACLE,DB2,SYBASE,SQLSERVER
- <font color="red">DBMS负责执行sql语句，通过执行sql语句来操作DB中的数据</font>
  - DBMS->SQL->DB

## 表的理解

table - 表

- **数据库的基本组成单元**
  - 可读性强
- 一个表包括
  - **行row**：数据/记录
  - **列column**：字段

🍊 每个字段该包括的属性

- 字段名
- 数据类型
- 相关约束

## SQL语句分类

分类

- DQL - 数据查询语言
  - 查询语句
  - 凡是`SELECT`都为DQL语句
- DML - 数据操作语言
  - **对表数据**的增删语句
  - insert，delete，update
- DDL - 数据定义语言
  - **对表结构**的增删改
  - create，drop，alter
- TCL - 事务控制语言
  - commit**提交事务**
  - rollback**回滚事务**
- DCL - 数据控制语言
  - grant**授权**
  - revoke**撤销权限**

## 导入初始化数据(数据表)

1. 查看有哪些数据库
   1. `show databases;`属于mysql命令
2. 创建自定义数据库
   1. `create database <db_name>;`mysql命令
3. 使用自定义数据库中的数据
   1. `use <db_name>;`mysql命令
4. 查看当前使用的数据库中有哪些表
   1. `show tables;`mysql命令
5. 初始化数据（空数据库中导入数据表）
   1. `source <path>`
6. 删除数据库
   1. `drop database <db_name>;`

### 对sql脚本的理解

sql脚本

- 文件扩展名`.sql`，编写了**大量sql语句**
- 可以用`source`命令执行
  - 当sql脚本数据过大时，请使用`source`进行初始化

## MYSQL常用命令

### 查询当前使用的数据库

**查询当前使用的数据库**

- `select database();`
- ![](/static/2020-09-13-05-09-28.png)

或**查询数据库版本**

- `select version();`
- ![](/static/2020-09-13-05-09-17.png)

### 查看库表

查看**当前库中表**

- `show tables;`

查看**其它库中表**

- `show tables from <db_name>`

### 查看表结构 & 表数据

查看**表结构**`desc <table_name>;`

- mysql命令
![](/static/2020-09-13-04-57-52.png)

查看**表数据**`select ... from <table_name>`

- **sql语句**
![](/static/2020-09-13-05-01-06.png)

### 查看创建表的语句

查看**创建表的语句**

- `show create table <table_name>;`
- ![](/static/2020-09-13-05-17-34.png)

### 终止语句 & 退出mysql

如果想**终止一条正在编写的语句**

- `\c`
- ![](/static/2020-09-13-05-11-00.png)

**退出mysql.**

- `exit`
- `\q`

## DQL: 简单查询

语法格式 & 列重命名`as`使用

- `select 字段1,字段2,字段3, ... from <table_name>;`
- <font color="red">不分大小写</font>
- <font color="blue">字段可以参与数学运算</font>
  - 如`select sal*12 as yearsal from emp;`，**其中`as`对列重命名**，<font color="red">as关键字可以省略</font>
  - `select sal*12 yearsal from emp;`
  - `select sal*12 as '年薪' from emp;`
    - 目前版本可免写单引号

🍊 查询所有字段

- `select * from <tb_name>;`
  - **实际开发不建议使用，效率较低**

### 列重命名：字符串的使用

🍊 注意

- 标准sql语句中要求字符串用**单引号**括起
  - 虽然mysql支持双引号，尽量不用

## DQL：条件查询

需要对数据进行筛选

![](/static/2020-09-13-05-52-05.png)

```msyql
<!-- 语法格式 -->
select
  column 1, column2...
from
  tb_name
where
  condition;
```

🍊 执行顺序

- from->where(**第一次过滤**)->group by(**分组**)->having(**分完组之后的数据，再过滤**)->select->order by

### 算术运算符 & between..and

![](/static/2020-09-13-05-52-05.png)

- `!=`相当于`<>`
- `between...and...`相当于`>= and <=`
  - <font color="red">除了可以用于数字，还可以用于字符串方面</font>，**用于字符方面，左闭右开**

🍊 例子

- 查询SMITH工资
  - `select sal from emp where ename='SMITH';`
- 找出工资高于3000的员工
  - `select ename,sal from emp where sal>3000;`
- 找出工资不等于3000的员工
  - `select ename,sal from emp where sal<>3000;`
  - `select ename,sal from emp where sal!=3000;`
- 找出工资在**1100~3000之间**的员工
  - `select ename,sal from emp where sal>=1100 and sal<=3000;`
  - `select ename,sal from emp where sal between 1100 and 3000;`<font color="red">会翻译成上面的语句</font>
- `between...and...`**用于字符串方面**
  - `select ename from emp where ename between 'A' and 'C'`<font color="red">左闭右开，不会包括以C开头的员工的姓名</font>

### is null & not null

![](/static/2020-09-13-05-52-05.png)

- 数据库`null`不是一个值
  - 不能用`=`衡量，<font color="red">必须使用`is null`&`is not null`</font>

🍊 例子

- 判断**津贴是否为空**
  - `Select ename,sal,comm from emp where comm is null;`![](/static/2020-09-13-06-04-25.png)

### and & or：优先级

![](/static/2020-09-13-05-52-05.png)

- `and`优先级高于`or`

🍊 例子

- and，or联合使用：找出薪资>1000且部门编号为20或30的员工
  - `select ename,sal,deptno from emp where sal>1000 and (deptno = 20 or deptno = 30)`

### in

![](/static/2020-09-13-05-52-05.png)

- `in(...,...)`等同于`or`,相当于多个`or`
  - <font color="red">后面的值不是一个区间，是具体的值</font>
- `not in(...,...)`表示不在这个范围内

🍊 例子

- 找出工作岗位是manager和salesman的员工
  - `select ename,job from emp where job in('SALESMAN','MANAGER')`

### like: 模糊查询

![](/static/2020-09-13-05-52-05.png)

- 掌握`%` & `_`
  - `%`代表任意多个字符
  - `_`代表任意一个字符

🍊 例子

- 找出名字中**含有`o`的**
  - `select ename from emp where ename like '%o%';`随意o在哪个位置
- 找出名字中**第二个字母为`A`的**
  - `select ename from emp where ename like '_A%';`
- 找出名字中**含有`_`的**
  - `select ename from emp where ename like '%\_%';`使用转义符
- 找出名字中**最后一个字母为`T`的**
  - `select ename from emp where ename like '%T';`

## 数据排序: order by

🍊 默认升序，指定方式

- 升序`order by <field> asc;`
  - `order by <field>;`
- 降序`order by <field> desc;`
- 多字段`order by`
  - <font color="red">只有order by第一个条件相等的时候（无法排序时），第二个条件才会启用</font>
- <font color="red">也可以直接指定字段号</font>`select ename,sal from emp order by 2`，<font color="blue">不建议使用</font>
  - 根据sal升序排序

🍊 例子

- 按照工资升序，找出员工名&薪资？
  - `select ename,sal from emp order by sal;`
  - <font color="red">默认是升序</font>
- **按照工资降序排，工资相同的时候再按名字排**
  - `select ename,sal from emp order by sal desc, ename asc;`
  - <font color="red">只有order by第一个条件相等的时候（无法排序时），第二个条件才会启用</font>
- 找出岗位是salesman的员工，并按照薪资降序排列
  - `select ename,job,sal from emp where job='SALESMAN' order by sal desc;`

## 分组函数(多行处理函数)

🍊 <font color="red">所有分组函数对“某一组”数据进行操作</font>

- 又称，**多行处理函数**
  - 输入多行，输出一行
- <font color="blue">分组函数会自动忽略`NULL`</font>
  - `count(*)`计数总记录条数&`count(field)`具体**不为NULL的字段个数**
- <font color="red">分组函数不可以直接使用在`where`子句中</font>

🍊 <font color="blue">分组函数一般和`group by`联合使用</font>，

- 并且**任何一个分组函数，都是在`group by`执行结束之后才会执行**
- 如果**一个sql语句没有group by，则数据自成一组**
  - 如`select max(sal) from emp;`仅有一组最大sal字段，1个数据

**分组函数（多行处理函数**)

- `count`计数
- `sum`求和
- `avg`平均值
- `max`最大值
- `min`最小值

🍊 例子

- 找出工资总和
  - `select sum(sal) from emp;`
- 最高工资
  - `select max(sal) from emp;`
- 最低工资
  - `select min(sal) from emp;`
- 平均工资
  - `select avg(sal) from emp;`
- 总人数
  - `select count(*) from emp;`
  - `select count(ename) from emp;`
- 找出工资高于平均工资的员工
  - `select ename,sal from emp where sal>(select avg(sal) from emp);`<font color="red">select子句，没有group by自成一表</font>

### count(*) & count()

区别

- `count(*)`
  - 统计总记录条数，与**字段无关**
- `count(field)`
  - 统计某字段中**不为NULL的数据总数量**

### 单行处理函数 & NULL运算

🍊 注意

- 所有数据库(管理系统)规定，<font color="red">只要有NULL参与的运算结果一定是NULL</font>

什么是单行处理函数？

- 输入一行，输出一行

🍊 例子

- **计算每个员工的年薪，（不处理NULL）**
  - `select ename, (sal+comm)*12 as year from emp;`
    - 假设`sal=800`，`comm=NULL`，则`sal+comm=NULL`

#### ifnull()空处理函数(单行)

🍊 注意

- 所有数据库(管理系统)规定，<font color="red">只要有NULL参与的运算结果一定是NULL</font>

🍊 使用`ifnull(field,...)`空处理函数

![](/static/2020-09-13-07-49-51.png)

- 如果字段为`NULL`，则看作`...`处理

🍊 改进的例子

- **计算每个员工的年薪，处理NULL**
  - `select ename, (sal+ifnull(comm,0))*12 as yearsal from emp;`

### group by & having

`group by`

- 根据**某个字段，或某些字段进行分组**
- <font color="red">分组函数一般和`group by`联合使用</font>，
  - 并且**任何一个【分组函数】，都是在`group by`执行结束之后才会执行**
  - 如果**一个sql语句没有group by，则数据自成一组**
  - 如`select max(sal) from emp;`仅有一组最大sal字段，1个数据
- <font color="red">！！！当有group by时，select只能跟【分组函数】及【参与分组的字段】！！！！！</font>

`having`

- 对**分组之后的数据进行再次过滤**

🍊 例子

- 找出**每个工作岗位的最高薪资**
  - `select job, max(sal) from emp group by job`先对emp表根据job分组，之后再选中最高sal
- 找出**工资高于平均工资的员工**
  - `select ename,sal from emp where sal > (select avg(sal) from emp);`<font color="red">select子查询</font>

#### 多字段分组查询

🍊 例子

- 找出**每个部门**&**不同工作岗位的最高薪资**
  - `select deptno,job,max(sal) from emp group by deptno,job;`

#### having vs where

🍊 例子

- 找出**每个部门的最高薪资，要求显示薪资>2500的数据**
  - `select max(sal),deptno from emp group by deptno having max(sal)>2500;`**此例使用having效率低**
  - `select max(sal),deptno from emp where sal>2500 group by deptno;`**建议能够使用where过滤的就使用where进行过滤**
- 找出**每个部门平均薪资，并要求平均薪资>2000**
  - `select avg(sal),deptno from emp group by deptno having avg(sal) >2000;`