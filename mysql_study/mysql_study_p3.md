# MySQL Study

study note

- [MySQL Study](#mysql-study)
  - [约束 Constraint](#约束-constraint)
    - [非空约束:not null](#非空约束not-null)

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
  - 
- 检查约束`check`
  - 注意Oracle有check'约束,**mysql没有**

### 非空约束:not null

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

