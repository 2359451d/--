# L4 - Advanced SQL & Analytics

Part A.2: SQL & Advanced SQL

![](/static/2021-02-14-20-39-06.png)

* Join Query
  * dealing with NULL values in FK
* Analytics Query 聚合（分组）查询
  * Complex Un/Correlated Query using Aggregation Functions over Groups of Tuples; 多元组上聚合函数相关，非相关查询
  * 目的：从元组中提取知识，而不仅仅是检索元组 Objective: Extract knowledge from tuples and not just retrieving tuples
* DML Modification Query
  * CRUD

* [L4 - Advanced SQL & Analytics](#l4---advanced-sql--analytics)
* [内连：INNER JOIN](#内连inner-join)
* [外连：Outer Joins](#外连outer-joins)
  * [左连例子：LEFT OUTER JOIN](#左连例子left-outer-join)
* [聚合/分组/多行处理函数：Aggregate Function](#聚合分组多行处理函数aggregate-function)
  * [例子 - 聚合函数使用](#例子---聚合函数使用)
* [分析 - GROUP BY：Analytics:Grouping Tuples](#分析---group-byanalyticsgrouping-tuples)
  * [可视演示：Visualization](#可视演示visualization)
  * [GROUP BY 例子](#group-by-例子)
  * [GROUP BY 其他特性 - 例子2](#group-by-其他特性---例子2)
  * [GROUP BY 其他特性 - 例子3](#group-by-其他特性---例子3)
  * [多关系GROUP BY例子](#多关系group-by例子)
* [GROUP BY & HAVING](#group-by--having)
  * [例子](#例子)
* [Tricky Analytics Query 例子](#tricky-analytics-query-例子)
* [Having子句例子](#having子句例子)
* [DML数据操作语言: Modification Query](#dml数据操作语言-modification-query)
  * [INSERT](#insert)
  * [DELETE](#delete)
  * [UPDATE](#update)
    * [例子](#例子-1)
* [DDL：数据定义语言](#ddl数据定义语言)
  * [DROP](#drop)
  * [ALTER](#alter)

# 内连：INNER JOIN

等值内连 & 非等值内连

* 关注FK&PK的等值匹配，筛选出相关tuples focusing on the equality between FK and PK, get only the associated matched tuples

![](/static/2021-02-14-20-42-01.png)

:orange: INNER JOIN

* INNER JOIN matches tuples using FK and PK (`THETA-JOIN`).
* **equijoin** 等值内连
  * focus only on the equijoin between FK and PK of the 2 associated relations
* 之前通用的等值内连查询 【SQL92写法】
  * `EQUIJOIN: R1.PK = R2.FK`

:orange: 等值内连其他写法【**SQL99写法，分离了连接条件和where**】 - 例子

![](/static/2021-02-14-21-40-46.png)
![](/static/2021-02-14-21-42-47.png)

* SQL92写法，join操作 & 筛选全包括在WHERE声明中: Join and selection conditions are both in the WHERE clause

# 外连：Outer Joins

如果FK不构成PK的一部分，则可以为NULL，因为不违法完整性约束

* 因此，有时候需要引入外连来解决FK为`NULL`的情况
* <font color="red">INNER JOIN只适用于FK不为NULL的情况</font>inner join operator is focusing only on the fact that the foreign key is not `NULL`
  * A tuple is retrieved if and only if there exists a matching tuple
  * FK为`NULL`的tuple,使用内连`INNER JOIN`会直接被忽略, 因为只匹配不为`NULL`的tuple

![](/static/2021-02-14-22-33-08.png)

* 外连允许FK属性可以有`NULL`值，outer join operator is dealing with the fact that we can have certain `NULL` values in the foreign key attribute
* 分类
  * **左(外)连** LEFT OUTER JOIN （LR[left relation] LEFT OUTER JOIN RR[right relation]）
    * **左关系`LR`中的每个元组都必须出现在结果中**。Every tuple in the left relation LR must appear in result
    * **如果没有匹配的元组存在，只需为右关系`RR`的属性添加`NULL`值（而不是直接忽略这整个元组）**。If no matching tuple exists, just add NULL values for attributes of right relation RR
  * **右(外)连** RIGHT OUTER JOIN （LR[left relation] RIGHT OUTER JOIN RR[right relation]）
    * **右关系`RR`中的每个元组都必须出现在结果中**。Every tuple in the right relation LR must appear in result
    * **如果没有匹配的元组存在，只需为左关系`LR`的属性添加`NULL`值（而不是直接忽略这整个元组）**。If no matching tuple exists, just add NULL values for attributes of left relation LR

## 左连例子：LEFT OUTER JOIN

![](/static/2021-02-14-22-42-12.png)

* 因为`Borg`这行，`Supervisor.SSN=NULL`可以推断出`Borg`是supervisor，其他两个人为supervisees
  * <font color="purple">也就是通过left join 提取匹配&不匹配元组的信息</font>exploit the left outer join in order to extract knowledge about the matching tuples and also the tuples that cannot be matched
* <font color="deeppink">注意下面的SQL92写法，不会处理`NULL`值，WHERE声明中筛选含有`NULL`值直接被处理为`UNKNOWN`，tuple被忽略</font>
  * **因此只会输出 2条记录**
  * 所以只适用于，**FK不为NULL的情况**

---

![](/static/2021-02-14-22-51-52.png)

# 聚合/分组/多行处理函数：Aggregate Function

输入多行，输出单行

![](/static/2021-02-14-22-53-28.png)

:orange: 定义

* 聚合功能：对一组元组的统计摘要/值。 Aggregation function: statistical summary/value over group of tuples.
  * <font color="deeppink">analytic query - 对一组元组数据应用聚合函数提取信息，不关心元组的单个值</font> applying a specific aggregation function in order to extract knowledge over these group of tuples and we do not care about the individual values of the tuples.

:orange: 内置聚合函数，Built-in aggregation functions over attribute X

* `COUNT( * )`
  * how many tuples can have in a specific group
  * the cardinality of this set group of tuples
* `SUM(X)`
* `MAX(X)/MIN(X)`
* `AVG(X)`
* `CORR(X, Y)` Correlation between `X` & `Y`

:orange: 以上内置聚合函数，除了`COUNT`都会忽略`NULL`值的计入

## 例子 - 聚合函数使用

![](/static/2021-02-14-23-00-58.png)

# 分析 - GROUP BY：Analytics:Grouping Tuples

![](/static/2021-02-14-23-02-29.png)

:orange: **根据分组属性将关系分割成组，即在分组属性中具有相同值的聚类元组**。Partition a relation into groups based on grouping attribute, i.e., clustering tuples having the same value in the grouping attribute.

* `GROUP BY {grouping attribute}`

---

:orange: 例子 - 以下例子的分组属性 & 组数量？

* Group of employees with the same last-name
  * last name
  * as many groups as the **distinct** values of last name
* Group of employees working in the same department
  * dno
  * number is the same as the deparment number
* Group of employees with the same salary…
  * salary
  * as many groups as the **distinct** values of the salary
* Group of dependents of the same employee
  * as many groups of dependents as the number of these employees having at least 1 dependent

:orange: 根据分组属性&应用完聚合函数后

* **可以用select列出聚合后数据，聚合函数，&分组属性**
  * 其他数据不能包含于select声明中
  * <font color="deeppink">最好包括分组属性，不然难以分辨每组的具体统计信息</font> cannot identify where these two statistical values are corresponding in which group; that's why w# need to take into consideration the fact that the grouping attribute should also appear in the SELECT clause because this is information per group.

## 可视演示：Visualization

![](/static/2021-02-14-23-47-32.png)

* color - grouping attribute
* 3 groups

## GROUP BY 例子

![](/static/2021-02-15-00-21-16.png)
![](/static/2021-02-15-00-40-55.png)

* 3groups
* for each groups ,apply the aggregate functions

步骤

1. partition the relation
2. ranking the results in a descending order
   1. `COUNT(*)` outcome ---> the most populated department - no.5, having 4 employees

## GROUP BY 其他特性 - 例子2

![](/static/2021-02-15-00-43-57.png)

可以通过GROUP BY操作符，**生成直方图，模拟某属性的概率分布**

* Use this analytics to approximate the histogram of AGE, i.e., how the AGE is distributed over the tuples…

## GROUP BY 其他特性 - 例子3

解决回归分析 deal with regression analytics

![](/static/2021-02-15-00-46-57.png)

## 多关系GROUP BY例子

![](/static/2021-02-15-00-48-33.png)
![](/static/2021-02-15-03-05-08.png)

---

每个部门员工的平均工资是多少？请在结果处注明部门名称

![](/static/2021-02-15-16-45-49.png)
![](/static/2021-02-15-16-51-58.png)

```sql
SELECT d.dname, AVG(e.salary)
FROM department AS d JOIN employee AS e
ON d.dnumber =  e.dno
GROUP BY d.dname;
```

# GROUP BY & HAVING

having筛选

* **可用于筛选聚合函数的值** is a condition to either select or reject a group after the grouping based on the value that we are getting from a specific aggregation function
* 先执行where、 然后是group 、 然后是having 、 最后是order by

:orange: 例子 - 只显示有2名以上员工的项目的员工人数。在结果中包含项目名称

![](/static/2021-02-15-17-10-22.png)

## 例子

![](/static/2021-02-15-17-24-06.png)

谁是有两个以上受抚养人的雇员（SSN，姓）？

![](/static/2021-02-15-17-25-12.png)

* 先关联
* 后groupby
* 再聚合
* 再having

---

那些员工超过100人的部门的经理（姓）是谁？

![](/static/2021-02-15-17-26-12.png)
![](/static/2021-02-15-17-29-11.png)

* where子句
  * nested **uncorrelated** aggregation query
  * 内查询返回查询集 - 人数>100的部门号码DNO
  * 外查询 - 返回所有为manager，且在内查询结果集中部门的manager姓
* IN是set操作符

# Tricky Analytics Query 例子

每一个员工超过5人的部门，有多少人的收入超过4万英镑

* count(人)>5 然后需要进一步筛选符合这个查询集的部门里，所有收入>4的人数（where筛选后，再聚合）

![](/static/2021-02-15-17-46-07.png)

如果先筛选where，可能**先把本来部门有5个以上的人的部门给忽略了**，然后把查询变成了，找出有5个人以上收入超过4w的部门

![](/static/2021-02-15-20-57-27.png)

* WHERE filters out employees with Salary <= £40K before grouping…thus, the group sizes (employees per department) are not correct…

---

正确解法

![](/static/2021-02-15-20-58-35.png)
![](/static/2021-02-15-21-00-23.png)

* nested uncorrelated query
* 内查询先查出 人数>5的部门的号码 DNO
* 外查询筛 员工工资>40k 且 DNO 属于 内查询查询集的记录

# Having子句例子

![](/static/2021-02-15-21-03-22.png)

* **显示雇员人数最多的部门**。
  * 可能存在多个部门人数最多（一样多）的情况

![](/static/2021-02-15-21-16-06.png)

* 先group by dno, 聚合出count(number)
* 对1的查询集进行再聚合算出max(number) 
* group by dno，聚合求count(*),且筛选出count(*)=2的max num的tuple

# DML数据操作语言: Modification Query

## INSERT

![](/static/2021-02-16-14-47-09.png)

```sql
INSERT INTO relation_name(field, field,...)
VALUES (value, value, ...);
```

## DELETE

![](/static/2021-02-16-14-49-04.png)

```sql
DELETE FROM relation_name
WHERE ...
;
```

## UPDATE

![](/static/2021-02-16-14-51-57.png)

```sql
UPDATE relation_name
SET field=value, field=value,...
WHERE ...;
```

### 例子

![](/static/2021-02-16-14-53-12.png)

# DDL：数据定义语言

## DROP

删除表，约束，定义域

![](/static/2021-02-16-14-58-42.png)

* DROP is used to drop named schema elements, such as tables, domains, or constraint

`DROP SCHEMA relation_name CASCADE`

* removes schema & all its elements including tables & constraints

`DROP TABLE relation_name`

* drops tables & its tuples
* 如果表不存在，会报错

`DROP TABLE relation_name CASCADE CONSTRAINTS`

* It drops the **existing table** EMPLOYEE, all of **its tuples** and **drop the FOREIGN KEY constraints of the tables referring** to EMPLOYEE (but **not those tables**).

---

`truncate table t_name`

* 删除大表数据，表被截断，不可回滚，永久丢失

`delete from t_name`

* 删除所有记录(仅数据记录，不会删表)

## ALTER

修改表结构

![](/static/2021-02-16-15-01-36.png)

* 修改字段 Adding or dropping a column (attribute)
* 修改字段定义 Changing a column definition
* 修改表约束 Adding or dropping table constraints

:orange: add/drop column

`ALTER TABLE t_name ADD|DROP COLUMN field_name [field_type]`

:orange: add/drop constraint

`ALTER TABLE t_name ADD|DROP CONSTRAINT constraintlabel constraint_type (field_name)`

:orange: alter column

* `ALTER TABLE COMPANY.DEPARTMENT ALTER COLUMN Mgr_ssn DROP DEFAULT;`
* `ALTER TABLE COMPANY.DEPARTMENT ALTER COLUMN Mgr_ssn SET DEFAULT '333445555';`

