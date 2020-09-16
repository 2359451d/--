# MySQL Study

- [MySQL Study](#mysql-study)
  - [distinct: 字段去重](#distinct-字段去重)
  - [连接查询](#连接查询)
    - [连接查询的分类](#连接查询的分类)
    - [连接查询原理&笛卡尔积](#连接查询原理笛卡尔积)
      - [表别名](#表别名)
    - [SQL99](#sql99)
    - [内连接：等值连接](#内连接等值连接)
    - [内连接：非等值连接](#内连接非等值连接)
    - [自连接](#自连接)
    - [外连接](#外连接)
      - [左(外)连接](#左外连接)
      - [右(外)连接](#右外连接)
    - [3表连接查询](#3表连接查询)
  - [子查询](#子查询)
    - [where：嵌套子查询](#where嵌套子查询)
    - [from: 嵌套子查询](#from-嵌套子查询)
    - [select：嵌套子查询](#select嵌套子查询)
  - [union](#union)
  - [limit(*) & 分页查询](#limit--分页查询)
    - [通用的标准分页sql](#通用的标准分页sql)
  - [创建表](#创建表)
    - [字段数据类型](#字段数据类型)
      - [char vs varchar](#char-vs-varchar)
  - [insert：表插入数据](#insert表插入数据)
  - [drop table: 删除表](#drop-table-删除表)
  - [表的复制 & 查询结果插入表](#表的复制--查询结果插入表)
  - [update: 修改表数据](#update-修改表数据)
  - [delete: 删除表数据](#delete-删除表数据)
    - [truncate: 怎么删除大表](#truncate-怎么删除大表)

## distinct: 字段去重

`select distinct <field_name>`

- 只能出现在所有字段的最前面
  - 表示**后面所有字段联合去重**

🍊 例子

- 统计岗位的数量
  - `select count(distinct job) from emp;`

## 连接查询

实际开发中，都不是从单表中查询

- 一般多张表联合查询取出最终结果
- 一般一个业务对应多张表
  - 如学生&班级，最少2张表

### 连接查询的分类

根据**语法出现的年代**划分

- SQL92(老DBA可能还在使用)
- SQL99(较新)

根据**表的连接方式**划分

- **内连接 - inner join**
  - 等值连接
  - 非等值连接
  - 自连接
- **外连接**
  - 左(外)连接 `left join`
  - 右(外)连接 `right join`
- 全连接（用的少）

### 连接查询原理&笛卡尔积

表的连接查询方面有一种现象

- **笛卡尔积现象**
  - 如果表连接，**不加任何过滤条件**，**返回数为两张表记录条数的乘积**

#### 表别名

好处

- 执行效率高
  - 两表都有的字段，会两表中都进行查找
- 可读性好

### SQL99

🍊 SQL99语法

- 结构更清晰一点，**表的连接条件和where条件分离**

```mysql
    A
join
    B
on
    connection_condition
where
    ...
```

### 内连接：等值连接

最大特点是**连接条件是等量关系**

🍊 例子

- 找出每个员工的部门名称，要求显示员工名&部门名
  - `select e.name,d.dname from emp e, dept d where e.deptno=d.deptno;`**SQL92语法，以后不用**
  - **SQL99语法**`select e.ename, d.dname from emp e join dept d on e.deptno=d.deptno;`<font color="red">省略了`inner join`的`inner`，带inner可读性好</font>

### 内连接：非等值连接

最大特点是**连接条件是非等量关系**

🍊 例子

- 找出每个员工的工资等级，要求显示员工名，工资，工资等级
  - `select e.ename, e.sal, s.grade from emp e join salgrade s on e.sal between s.losal and s.hisal;`

### 自连接

最大特点是，**一张表看作两张表**

- 自己连自己

🍊 例子

- 找出每个员工的上级领导，要求显示员工名和对应领导名
  - `select m.ename,e.ename from emp e join emp m on e.empno=m.mgr;`

### 外连接

🍊 **使用的更多**，数据不能直接扔了不要

- <font color="red">主表数据无条件全查询出来</font>

内连接&外连接区别

- **内连接**
  - A,B两表连接，**凡是能匹配的记录都会查询出来**（笛卡尔现象）
  - **匹配不上就丢了**
- **外连接**
  - A,B两表连接，AB中，**一张作为主表，一张作为副表**<font color="red">主要查询主表数据，捎带查询附表</font>
  - 如果**副表中的数据不能与主表数据匹配**，<font color="blue">自动模拟出NULL与之匹配</font>

🍊 分类

- 左连接 `left (outer) join`
- 右连接 `right (outer) join`

🍬 左连接右右连接写法，右连接有左连接写法

#### 左(外)连接

左边的表为主表

- 写法`left (outer) join`，`outer`可省

#### 右(外)连接

右边的表为主表

- 写法`right (outer) join`，`outer`可省

🍊 例子

- 找出哪个部门没有员工？
  - `select d.* from emp e right join dept d on e.deptno=d.deptno where e.empno is null;`

### 3表连接查询

![](/static/2020-09-14-22-13-11.png)

🍊 例子

- 找出每个员工的部门名称&工资等级
  - `select e.ename,d.dname,e.sal,s.grade from emp e join dept d join salgrade s on e.deptno = d.deptno and e.sal between losal and hisal;`
- 找出每个员工的部门名称&工资等级&上级领导
  - `select e.ename,d.dname,e.sal,s.grade,m.ename 'manager' from emp e join dept d join salgrade s on e.deptno = d.deptno and e.sal between losal and hisal left join emp m on e.mgr = m.empno;`外连接emp作为manager表

## 子查询

什么是子查询？

- select语句中嵌套select语句

🍊 可以出现在...后面

![](/static/2020-09-14-22-52-12.png)

- select
- from
- where

### where：嵌套子查询

🍬 note: **cannot directly use the aggregate function after where**

🍊 例子

- 找出高于平均薪资的员工信息
  - `select * from emp where sal > (select avg(sal) from emp);`多行处理函数最后只生成一个字段，一个数据的avg薪资表

### from: 嵌套子查询

🍊 例子

- 找出每个部门**平均薪水**的薪资**等级**
  - 每个部门平均薪水`select deptno, avg(sal) as avgsal from emp group by deptno;`
  - 将以上**查询结果当作临时表t**，让t和salgrade表连接，条件是t，`select t.*, s.grade from (select deptno, avg(sal) as avgsal from emp group by deptno) t join salgrade s where t.avgsal between s.losal and s.hisal;`
- 找出每个部门，员工的薪水的平均等级
  - `select e.ename,e.sal,e.deptno,s.grade from emp e join salgrade s where e.sal between s.losal and s.hisal`求出每个员工的薪资等级
  - 每个员工的薪资等级作为子表r，再根据deptno分组后调用聚合函数计算每个部门平均薪资水平【没必要】`select avg(r.grade) 'avggrade',deptno from (select e.ename,e.sal,e.deptno,s.grade from emp e join salgrade s where e.sal between s.losal and s.hisal) r group by deptno;`
  - 也可以使用简单写法`select avg(grade),deptno from emp e join salgrade s where e.sal between s.losal and s.hisal group by deptno;`

### select：嵌套子查询

🍊 例子

- 查出每个员工所在部门名称，要求显示员工名&部门名
  - 无嵌套写法`select e.ename, d.dname from emp e join dept d where e.deptno = d.deptno;`
  - 嵌套写法`select e.ename,(select d.dname from dept d where e.deptno = d.deptno) as dname from emp e;`

## union

可以**将查询结果集相加**

- <font color="red">注意：第一个查询结果的【列数量】需要和第二个一致</font>

🍊 例子

- 找出工作岗位是SALESMAN和MANAGER的员工
  - 非合并写法`select ename,job from emp where job in('SALESMAN','MANAGER');`
  - **使用union**`select ename,job from emp where job = 'SALESMAN' UNION select ename,job from emp where job='MANAGER';`

## limit(*) & 分页查询

**limit是mysql特有的，其他数据库不通用**（oracle有个相似机制叫做rownum）

- <font color="red">limit取结果集中的部分数据</font>
- 是sql语句的最后一环
  - from,where,group by, having,select,order by, limit

🍊 语法机制

- `limit startIndex, length`
  - `startIndex`起始位置,**默认`0`起始**
  - `length`表示取几个

🍊 例子

- 取出员工工资前5个
  - `select * from emp order by sal desc limit 0,5;`
  - `select * from emp order by sal desc limit 5;`（默认0起始）
- 取出工资排名4~9的员工
  - `select * from emp order by sal desc limit 3,6;`

### 通用的标准分页sql

每页显示3条记录

- 第1页：`0,3`
- 第2页：`3,3`
- 第3页：`6,3`

每页显示pagesize条记录

- 第pageNo页：`(pageNo-1)*pageSize,pageSize`
  - `pageSize`:每页显示多少条记录
  - `pageNo`：显示第几页
- 取`limit (pageNo-1)*pageSize,pageSize`

## 创建表

建表语句的语法格式

```mysql
create table <tb_name>(
    field data_type,
    field data_type,
    field data_type,
    ...
);
```

🍊 **表名在数据库中一般建议以`t_`或`tbl_`开始**

🍊 例子

- 创建学生表
  - 学号：bigint
  - 姓名：varchar
  - 性别：char
  - 班级编号：varchar
  - 生日：char

```mysql
create table t_student(
    no bigint,
    name varchar(255),
    gender char(1),
    classno varchar(255),
    dob char(10)
);
```

- 带默认值的建表

```mysql
create table t_student(
    no bigint,
    name varchar(255),
    gender char(1) default 1,
    classno varchar(255),
    birth char(10)
);
```

### 字段数据类型

🍊 常见字段数据类型

![](/static/2020-09-15-13-18-46.png)

- `int` 整数型（int）
- `bigint` 长整型（long）
- `float` 浮点型（float double）
- `double`
- `char` 定长字符串 （String）
- `varchar` 可变长字符串 （StringBuffer/StringBuilder）
- `date` 日期类型 （对应`java.sql.Date`类型）
- `BLOB` 二进制大对象（图片，视频等流媒体信息）Binary Large Object
- `CLOB` 字符大对象（较大文本，如4G字符串）Char Large Object

#### char vs varchar

char & varchar怎么选择？

- 实际开发中，**当某个字段中的数据长度不发生改变时，定长**
  - 如，性别，生日，都为`char`
- 当**一个字段数据长度不确定**
  - 如，简介，姓名，都为`varchar`

## insert：表插入数据

**语法格式**

```mysql
<!-- 普通格式，字段可以乱序（value对应的情况下） -->
insert into t_name(field,field,field,...) values (value,value,value,...);
```

```mysql
<!-- 省略field格式，字段-value,不可以乱序(一一对应表结构) -->
<!-- value不可以不填 -->
insert into t_name values(value,value,value);
```

```mysql
<!-- 一次插入多行数据 -->
insert into t_name(field,field,field,...) values(value,value,value,...) , (value,value,value,...);

```

- 要求：**字段数量&值数量相同，数据类型相同**
- <font color="red">只插部分字段，其他字段会初始化为`NULL`</font>
- <font color="red">一旦执行，会新插入一条记录</font>
  - **想要修改不能用insert**, 即使是`NULL`，<font color="blue">后期只能使用`UPDATE`（DML）进行更新</font>

🍊 例子

```msyql
insert into t_student(no,name,gender,classno,dob) values(1,'zhangsan','1','gaosan1ban','1950-10-12');
```

## drop table: 删除表

`drop table if exists t_name;`

- oracle不支持这种写法

`drop table t_name;`

- **如果表不存在，会报错**

`delete from t_name`

- 删除所有记录

`truncate table t_name`

- 删除大表数据，表被截断，不可回滚，永久丢失

## 表的复制 & 查询结果插入表

🍊 表的复制

`create table t_name as DQL(select语句)`

```mysql
create table emp1 as select * from emp;
```

- 将后面的查询结果当作表创建

---

🍊 查询结果插入到一张表中

![](/static/2020-09-15-16-33-57.png)

`insert into t_name DQL(select语句)`

- 将后面的查询结果插入表
  - 字段需要对应

## update: 修改表数据

语法格式

`update t_name set field=value,field=value,... where condition`

- 注意:<font color="red">没有where条件,整张表更新</font>

🍊 例子

- 将部门10的LOC修改为SHANGHAI,部门名称修改为RENSHIBU
  - `update t_student set loc='SHANGHAI',DNAME='RENSHIBU' where deptno=10;`
- 更新所有记录
  - `update dept1 set loc='x',dname='y';`

## delete: 删除表数据

语法格式

`delete from t_name where condition;`

- <font color="red">注意:没有条件全部删除</font>

🍊 例子

- 删除10部门数据
  - `delete from dept1 where deptno=10;`
- 删除所有记录
  - `delete from dept1;`

### truncate: 怎么删除大表

delete删大表效率低,没有释放数据的真实空间

- `truncate table t_name;`
- <font color="red">表被截断,不可回滚,永久丢失</font>

