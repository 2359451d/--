# SQL Exercise

34 questions to practice

## Q1

找到每个部门最高薪水的人员名称find the name of employee in each dept who has the max salary

![](/static/2020-09-17-18-38-49.png)

- from子查询，内连接

```mysql
select e.ename,t.deptno,t.maxsal  from ( select deptno, max(sal) 'maxsal' from emp group by deptno) t join emp e where t.maxsal = e.sal and t.deptno = e.deptno;

<!-- +-------+--------+---------+
| ename | deptno | maxsal  |
+-------+--------+---------+
| BLAKE |     30 | 2850.00 |
| SCOTT |     20 | 3000.00 |
| KING  |     10 | 5000.00 |
| FORD  |     20 | 3000.00 |
+-------+--------+---------+
4 rows in set (0.00 sec) -->
```

## Q2

那些人的薪水在部门的平均薪水之上？find employee whose salary is above the average salary level in each dept

```mysql
select e.ename,t.deptno,e.sal from emp e join ( select deptno,avg(sal) 'avg_sal' from emp group by deptno) t where e.sal>t.avg_sal and e.deptno=t.deptno;
<!-- +-------+--------+---------+
| ename | deptno | sal     |
+-------+--------+---------+
| ALLEN |     30 | 1600.00 |
| JONES |     20 | 2975.00 |
| BLAKE |     30 | 2850.00 |
| SCOTT |     20 | 3000.00 |
| KING  |     10 | 5000.00 |
| FORD  |     20 | 3000.00 |
+-------+--------+---------+
6 rows in set (0.00 sec) -->
```

## Q3

取得部门中（所有人的）平均的薪水等级

- 内连接 & group by & aggregate func

```mysql
<!-- 先计算每一个薪水的等级，然后找出薪水等级的平均值。 -->
select e.ename,e.sal,e.deptno,s.grade from emp e join salgrade s where e.sal between s.losal and s.hisal;
<!-- +--------+---------+--------+-------+
| ename  | sal     | deptno | grade |
+--------+---------+--------+-------+
| SMITH  |  800.00 |     20 |     1 |
| ALLEN  | 1600.00 |     30 |     3 |
| WARD   | 1250.00 |     30 |     2 |
| JONES  | 2975.00 |     20 |     4 |
| MARTIN | 1250.00 |     30 |     2 |
| BLAKE  | 2850.00 |     30 |     4 |
| CLARK  | 2450.00 |     10 |     4 |
| SCOTT  | 3000.00 |     20 |     4 |
| KING   | 5000.00 |     10 |     5 |
| TURNER | 1500.00 |     30 |     3 |
| ADAMS  | 1100.00 |     20 |     1 |
| JAMES  |  950.00 |     30 |     1 |
| FORD   | 3000.00 |     20 |     4 |
| MILLER | 1300.00 |     10 |     2 |
+--------+---------+--------+-------+
14 rows in set (0.00 sec) -->
```

```mysql
select e.deptno,avg(grade) from emp e join salgrade s on e.sal between s.losal and s.hisal group by e.deptno;
<!-- +--------+------------+
| deptno | avg(grade) |
+--------+------------+
|     20 |     2.8000 |
|     30 |     2.5000 |
|     10 |     3.6667 |
+--------+------------+
3 rows in set (0.00 sec) -->
```

## Q4

不准用组函数（Max ），取得最高薪水

- order by&limit

```msyql
select ename,sal from emp order by sal desc limit 1;
<!-- +-------+---------+
| ename | sal     |
+-------+---------+
| KING  | 5000.00 |
+-------+---------+
1 row in set (0.01 sec) -->
```

![](/static/2020-09-17-19-08-33.png)

- 因为最高薪资比自连接后,每个薪资都高,不会在表中`not in`

## Q5

取得平均薪水最高的部门的部门编号

```mysql
select deptno, avg(sal) from emp group by deptno order by avg(sal) desc limit 1;
<!-- +--------+-------------+
| deptno | avg(sal)    |
+--------+-------------+
|     10 | 2916.666667 |
+--------+-------------+
1 row in set (0.01 sec) -->
```

---

![](/static/2020-09-17-19-14-01.png)
![](/static/2020-09-17-19-14-45.png)

## Q6

取得平均薪水最高的部门的部门名称

```mysql
select d.dname,t.avg_sal from dept d join (select deptno, avg(sal) 'avg_sal' from emp group by deptno order by avg(sal) desc limit 1) t on d.deptno = t.deptno;
<!-- +------------+-------------+
| dname      | avg_sal     |
+------------+-------------+
| ACCOUNTING | 2916.666667 |
+------------+-------------+
1 row in set (0.01 sec) -->
```

## Q7

求平均薪水的等级最低的部门的部门名称

```mysql
select t.*,s.grade from salgrade s join (select deptno,avg(sal) 'avg_sal' from emp group by deptno) t on t.avg_sal between s.losal and s.hisal order by grade limit 1;
+--------+-------------+-------+
| deptno | avg_sal     | grade |
+--------+-------------+-------+
|     30 | 1566.666667 |     3 |
+--------+-------------+-------+
1 row in set (0.00 sec)
```

![](/static/2020-09-17-19-21-29.png)

## Q8

取得比普通员工(员工代码没有在 mgr 字段上出现的) 的最高薪水还要高的领导人姓名

```mysql
select distinct mgr from emp where mgr is not null;
+------+
| mgr  |
+------+
| 7902 |
| 7698 |
| 7839 |
| 7566 |
| 7788 |
| 7782 |
+------+
6 rows in set (0.00 sec)

select empno,ename,sal  from emp where empno not in(select distinct mgr from emp where mgr is not null) order by sal desc limit 1;
+-------+-------+---------+
| empno | ename | sal     |
+-------+-------+---------+
|  7499 | ALLEN | 1600.00 |
+-------+-------+---------+
1 row in set (0.00 sec)

select ename, sal from emp where sal>(select sal  from emp where empno not in(select distinct mgr from emp where mgr is not null) order by sal desc limit 1) and empno in (select distinct mgr from emp where mgr is not null);
+-------+---------+
| ename | sal     |
+-------+---------+
| JONES | 2975.00 |
| BLAKE | 2850.00 |
| CLARK | 2450.00 |
| SCOTT | 3000.00 |
| KING  | 5000.00 |
| FORD  | 3000.00 |
+-------+---------+
6 rows in set (0.02 sec)

```

## Q9

取得薪水最高的前五名员工

```MYSQL
select ename,sal from emp order by sal desc limit 5;
+-------+---------+
| ename | sal     |
+-------+---------+
| KING  | 5000.00 |
| FORD  | 3000.00 |
| SCOTT | 3000.00 |
| JONES | 2975.00 |
| BLAKE | 2850.00 |
+-------+---------+
5 rows in set (0.00 sec)

```

## Q10

取得薪水最高的第六到第十名员工

```MYSQL
select ename,sal from emp order by sal desc limit 5, 5;
+--------+---------+
| ename  | sal     |
+--------+---------+
| CLARK  | 2450.00 |
| ALLEN  | 1600.00 |
| TURNER | 1500.00 |
| MILLER | 1300.00 |
| MARTIN | 1250.00 |
+--------+---------+
```

## Q11

取得最后入职的 5 名员工
日期也可以降序，升序。

```mysql
select ename,hiredate from emp order by hiredate desc limit 5;

+--------+------------+
| ename  | hiredate   |
+--------+------------+
| ADAMS  | 1987-05-23 |
| SCOTT  | 1987-04-19 |
| MILLER | 1982-01-23 |
| FORD   | 1981-12-03 |
| JAMES  | 1981-12-03 |
+--------+------------+

```

## Q12

取得每个薪水等级有多少员工
分组count

```mysql
 select e.empno,e.sal,s.grade from emp e join salgrade s where e.sal between s.losal and s.hisal;
+-------+---------+-------+
| empno | sal     | grade |
+-------+---------+-------+
|  7369 |  800.00 |     1 |
|  7499 | 1600.00 |     3 |
|  7521 | 1250.00 |     2 |
|  7566 | 2975.00 |     4 |
|  7654 | 1250.00 |     2 |
|  7698 | 2850.00 |     4 |
|  7782 | 2450.00 |     4 |
|  7788 | 3000.00 |     4 |
|  7839 | 5000.00 |     5 |
|  7844 | 1500.00 |     3 |
|  7876 | 1100.00 |     1 |
|  7900 |  950.00 |     1 |
|  7902 | 3000.00 |     4 |
|  7934 | 1300.00 |     2 |
+-------+---------+-------+

select t.grade,count(t.empno) from (select e.empno,e.sal,s.grade from emp e join salgrade s where e.sal between s.losal and s.hisal) t group by t.grade;
+-------+----------------+
| grade | count(t.empno) |
+-------+----------------+
|     1 |              3 |
|     3 |              2 |
|     2 |              3 |
|     4 |              5 |
|     5 |              1 |
+-------+----------------+
5 rows in set (0.00 sec)

```

## Q13

![](/static/2020-09-17-19-52-47.png)

1

```mysql
select sc.sno from s s join c c join sc sc on c.cteacher='黎明' and c.cno = sc.cno;//选了黎明老师课的学生学号

select sno,sname from s where sno not in (select sc.sno from s s join c c join sc sc on c.cteacher='黎明' and c.cno = sc.cno);//选中不在选课范围内学生姓名
```

2

```mysql
select * from sc where scgrade between 0 and 60;//不及格的成绩记录

select sno,count(scgrade) from sc where scgrade between 0 and 60 group by sno having count(scgrade)>=2;
//两门以上不及格的记录,group by

select sno,sname from s where sno=(select sno,count(scgrade) from sc where scgrade between 0 and 60 group by sno having count(scgrade)>=2);
```

3

```mysql
//即学过 1 号课程又学过 2 号课所有学生的姓名。
select sno from sc where cno in(1,2);//选中1,2课中所有学生学号

select sno, count(sno) 'take' from sc where cno in(1,2) group by sno having 'take'=2;//统计学生号出现次数为2的

select sno,sname from s where sno = (select sno, count(sno) 'take' from sc where cno in(1,2) group by sno having 'take'=2);
```

## Q14

列出所有员工及领导的姓名

```mysql
select e.empno,e.ename,e.mgr,m.ename from emp e left join emp m on e.mgr = m.empno;
+-------+--------+------+-------+
| empno | ename  | mgr  | ename |
+-------+--------+------+-------+
|  7369 | SMITH  | 7902 | FORD  |
|  7499 | ALLEN  | 7698 | BLAKE |
|  7521 | WARD   | 7698 | BLAKE |
|  7566 | JONES  | 7839 | KING  |
|  7654 | MARTIN | 7698 | BLAKE |
|  7698 | BLAKE  | 7839 | KING  |
|  7782 | CLARK  | 7839 | KING  |
|  7788 | SCOTT  | 7566 | JONES |
|  7839 | KING   | NULL | NULL  |
|  7844 | TURNER | 7698 | BLAKE |
|  7876 | ADAMS  | 7788 | SCOTT |
|  7900 | JAMES  | 7698 | BLAKE |
|  7902 | FORD   | 7566 | JONES |
|  7934 | MILLER | 7782 | CLARK |
+-------+--------+------+-------+
14 rows in set (0.00 sec)
```

## Q15

列出受雇日期早于其直接上级的所有员工的编号,姓名,部门名称
emp a 员工表
emp b 领导表
a.mgr = b.empno and a.hiredate < b.hiredate

```mysql
select a.empno,a.ename 'employee',a.hiredate,a.mgr,b.ename 'manager',b.hiredate from emp a join emp b where a.hiredate<b.hiredate and a.mgr=b.empno;
+-------+----------+------------+------+---------+------------+
| empno | employee | hiredate   | mgr  | manager | hiredate   |
+-------+----------+------------+------+---------+------------+
|  7369 | SMITH    | 1980-12-17 | 7902 | FORD    | 1981-12-03 |
|  7499 | ALLEN    | 1981-02-20 | 7698 | BLAKE   | 1981-05-01 |
|  7521 | WARD     | 1981-02-22 | 7698 | BLAKE   | 1981-05-01 |
|  7566 | JONES    | 1981-04-02 | 7839 | KING    | 1981-11-17 |
|  7698 | BLAKE    | 1981-05-01 | 7839 | KING    | 1981-11-17 |
|  7782 | CLARK    | 1981-06-09 | 7839 | KING    | 1981-11-17 |
+-------+----------+------------+------+---------+------------+

select a.empno,a.ename 'employee',a.hiredate,a.mgr,b.ename 'manager',b.hiredate,d.dname from emp a join emp b on a.hiredate<b.hiredate and a.mgr=b.empno join dept d on a.deptno =d.deptno;
+-------+----------+------------+------+---------+------------+------------+
| empno | employee | hiredate   | mgr  | manager | hiredate   | dname      |
+-------+----------+------------+------+---------+------------+------------+
|  7369 | SMITH    | 1980-12-17 | 7902 | FORD    | 1981-12-03 | RESEARCH   |
|  7499 | ALLEN    | 1981-02-20 | 7698 | BLAKE   | 1981-05-01 | SALES      |
|  7521 | WARD     | 1981-02-22 | 7698 | BLAKE   | 1981-05-01 | SALES      |
|  7566 | JONES    | 1981-04-02 | 7839 | KING    | 1981-11-17 | RESEARCH   |
|  7698 | BLAKE    | 1981-05-01 | 7839 | KING    | 1981-11-17 | SALES      |
|  7782 | CLARK    | 1981-06-09 | 7839 | KING    | 1981-11-17 | ACCOUNTING |
+-------+----------+------------+------+---------+------------+------------+
6 rows in set (0.00 sec)
```

## Q16

 列出部门名称和这些部门的员工信息, 同时列出那些没有员工的部门

```mysql
select e.*,d.dname  from emp e right join dept d on e.deptno= d.deptno;
+-------+--------+-----------+------+------------+---------+---------+--------+------------+
| EMPNO | ENAME  | JOB       | MGR  | HIREDATE   | SAL     | COMM    | DEPTNO | dname      |
+-------+--------+-----------+------+------------+---------+---------+--------+------------+
|  7782 | CLARK  | MANAGER   | 7839 | 1981-06-09 | 2450.00 |    NULL |     10 | ACCOUNTING |
|  7839 | KING   | PRESIDENT | NULL | 1981-11-17 | 5000.00 |    NULL |     10 | ACCOUNTING |
|  7934 | MILLER | CLERK     | 7782 | 1982-01-23 | 1300.00 |    NULL |     10 | ACCOUNTING |
|  7369 | SMITH  | CLERK     | 7902 | 1980-12-17 |  800.00 |    NULL |     20 | RESEARCH   |
|  7566 | JONES  | MANAGER   | 7839 | 1981-04-02 | 2975.00 |    NULL |     20 | RESEARCH   |
|  7788 | SCOTT  | ANALYST   | 7566 | 1987-04-19 | 3000.00 |    NULL |     20 | RESEARCH   |
|  7876 | ADAMS  | CLERK     | 7788 | 1987-05-23 | 1100.00 |    NULL |     20 | RESEARCH   |
|  7902 | FORD   | ANALYST   | 7566 | 1981-12-03 | 3000.00 |    NULL |     20 | RESEARCH   |
|  7499 | ALLEN  | SALESMAN  | 7698 | 1981-02-20 | 1600.00 |  300.00 |     30 | SALES      |
|  7521 | WARD   | SALESMAN  | 7698 | 1981-02-22 | 1250.00 |  500.00 |     30 | SALES      |
|  7654 | MARTIN | SALESMAN  | 7698 | 1981-09-28 | 1250.00 | 1400.00 |     30 | SALES      |
|  7698 | BLAKE  | MANAGER   | 7839 | 1981-05-01 | 2850.00 |    NULL |     30 | SALES      |
|  7844 | TURNER | SALESMAN  | 7698 | 1981-09-08 | 1500.00 |    0.00 |     30 | SALES      |
|  7900 | JAMES  | CLERK     | 7698 | 1981-12-03 |  950.00 |    NULL |     30 | SALES      |
|  NULL | NULL   | NULL      | NULL | NULL       |    NULL |    NULL |   NULL | OPERATIONS |
+-------+--------+-----------+------+------------+---------+---------+--------+------------+
15 rows in set (0.00 sec)
```

## Q17

列出至少有 5 个员工的所有部门

```mysql

select count(empno),deptno from emp group by deptno having count(empno)>=5;
+--------------+--------+
| count(empno) | deptno |
+--------------+--------+
|            5 |     20 |
|            6 |     30 |
+--------------+--------+
2 rows in set (0.00 sec)
```

## Q18

列出薪金比"SMITH" 多的所有员工信息

```mysql
 select * from emp where sal>(select sal from emp where ename='SMITH');
+-------+--------+-----------+------+------------+---------+---------+--------+
| EMPNO | ENAME  | JOB       | MGR  | HIREDATE   | SAL     | COMM    | DEPTNO |
+-------+--------+-----------+------+------------+---------+---------+--------+
|  7499 | ALLEN  | SALESMAN  | 7698 | 1981-02-20 | 1600.00 |  300.00 |     30 |
|  7521 | WARD   | SALESMAN  | 7698 | 1981-02-22 | 1250.00 |  500.00 |     30 |
|  7566 | JONES  | MANAGER   | 7839 | 1981-04-02 | 2975.00 |    NULL |     20 |
|  7654 | MARTIN | SALESMAN  | 7698 | 1981-09-28 | 1250.00 | 1400.00 |     30 |
|  7698 | BLAKE  | MANAGER   | 7839 | 1981-05-01 | 2850.00 |    NULL |     30 |
|  7782 | CLARK  | MANAGER   | 7839 | 1981-06-09 | 2450.00 |    NULL |     10 |
|  7788 | SCOTT  | ANALYST   | 7566 | 1987-04-19 | 3000.00 |    NULL |     20 |
|  7839 | KING   | PRESIDENT | NULL | 1981-11-17 | 5000.00 |    NULL |     10 |
|  7844 | TURNER | SALESMAN  | 7698 | 1981-09-08 | 1500.00 |    0.00 |     30 |
|  7876 | ADAMS  | CLERK     | 7788 | 1987-05-23 | 1100.00 |    NULL |     20 |
|  7900 | JAMES  | CLERK     | 7698 | 1981-12-03 |  950.00 |    NULL |     30 |
|  7902 | FORD   | ANALYST   | 7566 | 1981-12-03 | 3000.00 |    NULL |     20 |
|  7934 | MILLER | CLERK     | 7782 | 1982-01-23 | 1300.00 |    NULL |     10 |
+-------+--------+-----------+------+------------+---------+---------+--------+
13 rows in set (0.02 sec)
```

## Q19

列出所有"CLERK"( 办事员) 的姓名及其部门名称, 部门的人数

```mysql
select r.*,r1.people from (select d.dname,t.* from dept d join (select ename,deptno from emp where job='CLERK') t on d.deptno=t.deptno) r join (select deptno,count(*) 'people' from emp group by deptno) r1 on r.deptno=r1.deptno;
+------------+--------+--------+--------+
| dname      | ename  | deptno | people |
+------------+--------+--------+--------+
| RESEARCH   | SMITH  |     20 |      5 |
| RESEARCH   | ADAMS  |     20 |      5 |
| SALES      | JAMES  |     30 |      6 |
| ACCOUNTING | MILLER |     10 |      3 |
+------------+--------+--------+--------+
4 rows in set (0.00 sec)
```

## Q20

列出最低薪金大于 1500 的各种工作及从事此工作的全部雇员人数

按照工作岗位分组求最小值。

```mysql
select job,min(sal),count(empno) from emp group by job having min(sal)>1500;
+-----------+----------+--------------+
| job       | min(sal) | count(empno) |
+-----------+----------+--------------+
| MANAGER   |  2450.00 |            3 |
| ANALYST   |  3000.00 |            2 |
| PRESIDENT |  5000.00 |            1 |
+-----------+----------+--------------+
3 rows in set (0.00 sec)

```

## Q21

列出在部门"SALES"< 销售部> 工作的员工的姓名, 假定不知道销售部的部门编号.

```MYSQL
 select e.ename,d.deptno,d.dname from emp e join dept d on e.deptno = d.deptno where d.dname='SALES';
+--------+--------+-------+
| ename  | deptno | dname |
+--------+--------+-------+
| ALLEN  |     30 | SALES |
| WARD   |     30 | SALES |
| MARTIN |     30 | SALES |
| BLAKE  |     30 | SALES |
| TURNER |     30 | SALES |
| JAMES  |     30 | SALES |
+--------+--------+-------+
6 rows in set (0.00 sec)
```

## Q22

列出薪金高于公司平均薪金的所有员工, 所在部门, 上级领导, 雇员的工资等级

```mysql
select e.empno,e.ename,e.mgr,m.ename,e.sal,s.grade,d.dname from emp e left join emp m on e.mgr=m.empno join dept d on d.deptno=e.deptno join salgrade s on e.sal between s.losal and s.hisal where e.sal>(select avg(sal) from emp);
+-------+-------+------+-------+---------+-------+------------+
| empno | ename | mgr  | ename | sal     | grade | dname      |
+-------+-------+------+-------+---------+-------+------------+
|  7566 | JONES | 7839 | KING  | 2975.00 |     4 | RESEARCH   |
|  7698 | BLAKE | 7839 | KING  | 2850.00 |     4 | SALES      |
|  7782 | CLARK | 7839 | KING  | 2450.00 |     4 | ACCOUNTING |
|  7788 | SCOTT | 7566 | JONES | 3000.00 |     4 | RESEARCH   |
|  7902 | FORD  | 7566 | JONES | 3000.00 |     4 | RESEARCH   |
|  7839 | KING  | NULL | NULL  | 5000.00 |     5 | ACCOUNTING |
+-------+-------+------+-------+---------+-------+------------+
6 rows in set (0.00 sec)
```

## Q23

列出与"SCOTT" 从事相同工作的所有员工及部门名称

```MYSQL
select e.ename,e.deptno,e.job,d.dname from emp e join dept d on e.deptno=d.deptno where job=(select job from emp where ename='SCOTT') and ename<>'SCOTT';
+-------+--------+---------+----------+
| ename | deptno | job     | dname    |
+-------+--------+---------+----------+
| FORD  |     20 | ANALYST | RESEARCH |
+-------+--------+---------+----------+
1 row in set (0.00 sec)
```

## Q24

列出薪金等于部门 30 中员工的薪金的其他员工的姓名和薪金.

```mysql
select ename,sal from emp where sal in(select distinct sal from emp where deptno=30) and deptno<>30;
Empty set (0.00 sec)
```

## Q25

列出薪金高于在部门 30 工作的所有员工的薪金的员工姓名和薪金. 部门名称

```mysql
select e.ename,e.sal,d.deptno,d.dname from emp e join dept d on e.deptno = d.deptno where sal >( select max(sal) from emp where deptno=30) and e.deptno<>30;
+-------+---------+--------+------------+
| ename | sal     | deptno | dname      |
+-------+---------+--------+------------+
| JONES | 2975.00 |     20 | RESEARCH   |
| SCOTT | 3000.00 |     20 | RESEARCH   |
| KING  | 5000.00 |     10 | ACCOUNTING |
| FORD  | 3000.00 |     20 | RESEARCH   |
+-------+---------+--------+------------+

```

## Q26(*)

列出在每个部门工作的员工数量, 平均工资和平均服务期限

```mysql
select 
	d.deptno, count(e.ename) ecount,ifnull(avg(e.sal),0) as avgsal, ifnull(avg(timestampdiff(YEAR, hiredate, now())), 0) as avgservicetime
from
	emp e
right join
	dept d
on
	e.deptno = d.deptno
group by
	d.deptno;

+--------+--------+-------------+----------------+
| deptno | ecount | avgsal      | avgservicetime |
+--------+--------+-------------+----------------+
|     10 |      3 | 2916.666667 |        38.0000 |
|     20 |      5 | 2175.000000 |        35.8000 |
|     30 |      6 | 1566.666667 |        38.3333 |
|     40 |      0 |    0.000000 |         0.0000 |
+--------+--------+-------------+----------------+
```

### TimeStampDiff函数使用

![](/static/2020-09-17-21-48-59.png)

`TimeStampDiff(timeunit/lagtype,date,date)`

## Q27

列出所有员工的姓名、部门名称和工资。

```mysql
 select e.ename,e.sal,d.deptno,d.dname from emp e join dept d on e.deptno=d.deptno;
+--------+---------+--------+------------+
| ename  | sal     | deptno | dname      |
+--------+---------+--------+------------+
| SMITH  |  800.00 |     20 | RESEARCH   |
| ALLEN  | 1600.00 |     30 | SALES      |
| WARD   | 1250.00 |     30 | SALES      |
| JONES  | 2975.00 |     20 | RESEARCH   |
| MARTIN | 1250.00 |     30 | SALES      |
| BLAKE  | 2850.00 |     30 | SALES      |
| CLARK  | 2450.00 |     10 | ACCOUNTING |
| SCOTT  | 3000.00 |     20 | RESEARCH   |
| KING   | 5000.00 |     10 | ACCOUNTING |
| TURNER | 1500.00 |     30 | SALES      |
| ADAMS  | 1100.00 |     20 | RESEARCH   |
| JAMES  |  950.00 |     30 | SALES      |
| FORD   | 3000.00 |     20 | RESEARCH   |
| MILLER | 1300.00 |     10 | ACCOUNTING |
+--------+---------+--------+------------+
14 rows in set (0.00 sec)

```

## Q28

列出所有部门的详细信息和人数

```mysql
select d.*,t.people from dept d left join (select count(empno) 'people',deptno from emp group by deptno) t on d.deptno=t.deptno;
+--------+------------+----------+--------+
| DEPTNO | DNAME      | LOC      | people |
+--------+------------+----------+--------+
|     10 | ACCOUNTING | NEW YORK |      3 |
|     20 | RESEARCH   | DALLAS   |      5 |
|     30 | SALES      | CHICAGO  |      6 |
|     40 | OPERATIONS | BOSTON   |   NULL |
+--------+------------+----------+--------+
4 rows in set (0.00 sec)
```

## Q29

列出各种工作的最低工资及从事此工作的雇员姓名

```mysql
select ename,job,sal from emp where sal in(select min(sal) from emp group by job);
+--------+-----------+---------+
| ename  | job       | sal     |
+--------+-----------+---------+
| SMITH  | CLERK     |  800.00 |
| WARD   | SALESMAN  | 1250.00 |
| MARTIN | SALESMAN  | 1250.00 |
| CLARK  | MANAGER   | 2450.00 |
| SCOTT  | ANALYST   | 3000.00 |
| KING   | PRESIDENT | 5000.00 |
| FORD   | ANALYST   | 3000.00 |
+--------+-----------+---------+
7 rows in set (0.00 sec)

```

![](/static/2020-09-17-21-58-34.png)

## Q30

列出各个部门的 MANAGER( 领导) 的最低薪金

```mc
select deptno,min(sal) from emp where empno in(select distinct mgr from emp) group by deptno;
+--------+----------+
| deptno | min(sal) |
+--------+----------+
|     20 |  2975.00 |
|     30 |  2850.00 |
|     10 |  2450.00 |
+--------+----------+
3 rows in set (0.00 sec)
```

## Q31

列出所有员工的 年工资, 按 年薪从低到高排序

```mysql
select ename,sal*12 'annual_sal' from emp;

+--------+------------+
| ename  | annual_sal |
+--------+------------+
| SMITH  |    9600.00 |
| ALLEN  |   19200.00 |
| WARD   |   15000.00 |
| JONES  |   35700.00 |
| MARTIN |   15000.00 |
| BLAKE  |   34200.00 |
| CLARK  |   29400.00 |
| SCOTT  |   36000.00 |
| KING   |   60000.00 |
| TURNER |   18000.00 |
| ADAMS  |   13200.00 |
| JAMES  |   11400.00 |
| FORD   |   36000.00 |
| MILLER |   15600.00 |
+--------+------------+
14 rows in set (0.00 sec)
```

## Q32

求出员工领导的薪水超过3000的员工名称与领导

```mysql
select 
	a.ename '员工',b.ename '领导'
from
	emp a
join
	emp b
on
	a.mgr = b.empno
where
	b.sal > 3000;

+-------+------+
| 员工  | 领导   |
+-------+------+
| JONES | KING |
| BLAKE | KING |
| CLARK | KING |
+-------+------+
```

## Q33

求出部门名称中, 带'S'字符的部门员工的工资合计、部门人数

```mysql
select d.deptno,count(e.empno),ifnull(sum(e.sal),0) from emp e right join dept d on d.deptno=e.deptno where d.dname like '%S%' group by d.deptno;
+--------+----------------+----------------------+
| deptno | count(e.empno) | ifnull(sum(e.sal),0) |
+--------+----------------+----------------------+
|     20 |              5 |             10875.00 |
|     30 |              6 |              9400.00 |
|     40 |              0 |                 0.00 |
+--------+----------------+----------------------+
3 rows in set (0.00 sec)
```

## Q34

给任职日期超过 30 年的员工加薪 10%.

`update emp set sal=sal*1.1 where TimeStamp(year,hiredate,noew())>30;`