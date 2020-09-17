# DBA命令 & 数据库设计三范式

DBA commands(brief understanding) & 3 main conventions to db design

## DBA command

### export the data from db

NOTE: operate in cmd,

将数据库中数据导出

`mysqldump db_name>path/...<t_name>.sql -uroot -p...` (**export the whole db**)

`mysqldump db_name t_name>path/...<t_name>.sql -uroot -p ...`(**export the specific table in db**)

- username & pass required

---

🍊 example

![](/static/2020-09-17-14-36-18.png)

### import the data into db

导入数据

```mysql
create databse db_name;
use db_name;
source path/db_name.sql;
```

🍊 example

![](/static/2020-09-17-14-36-30.png)

## 3 Conventions to DB Design

数据库设计三范式

> **设计表的依据。根据三范式设计的表不会出现数据冗余** the principle which can be used to design the table, avoidding the data redundancy probably invovled in the design.

### which the 3 conventions are

三范式有哪些？

1. **任何一张表都应该有主键**，并且**每一个字段原子性不可再分**(each table should have PK, and atomic to each fields cannot be split into more pieces.)
2. **建立在第一范式之上，所有非主键字段完全依赖主键，不能产生部份依赖**(based on the first principle, all the non-PK field should rely completely to the PK, partial dependency are not applicable)
   1. **避免使用复合PK**(avoiding the use of combined PK)
3. **建立在第2范式之上，所有非主键字段直接依赖主键字段，不能产生传递依赖**(based on the 2nd principle, all the non-PK fields directly rely on PK, transitive dependency is infeasible)
   1. ![](/static/2020-09-17-16-48-59.png)
   2. 一对多 1-N

🍊 Note that: **实际开发中，以满足用户需求为主，有时候拿冗余换执行速度**in the real development, sometimes would accept redundancy to exchange the execution speed

### 1-1

1-1有两种方案

- 主键共享
  - ![](/static/2020-09-17-18-19-51.png)
- 外键唯一
  - ![](/static/2020-09-17-18-20-48.png)