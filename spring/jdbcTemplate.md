# Content

spring提供jdbcTemplate快速操作数据库

* [Content](#content)
* [测试数据源](#测试数据源)
* [注入JDBCTemplate](#注入jdbctemplate)
* [单个更新](#单个更新)
* [批量插入](#批量插入)
* [查询单行](#查询单行)
* [查询集合](#查询集合)
* [查询最大salary](#查询最大salary)
* [具名参数](#具名参数)
* [自动装配DAO](#自动装配dao)

# 测试数据源

context命名空间引用外部配置

![](/static/2021-07-23-14-30-54.png)
![](/static/2021-07-23-14-31-08.png)

# 注入JDBCTemplate

1.导入spring数据库模块

![](/static/2021-07-23-14-32-39.png)

传入数据源，注册JDBCTemplate对象

```xml
<bean id= "template" class= "org.springframework.jdbc.core.JdbcTemplate" > 
<property name= "dataSource" ref= "dataSource" />
 </bean>
```

# 单个更新

将emp_id=5的记录的salary字段更新为1300.00

对应sql

```sql
UPDATE employee SET salary=1300 WHERE emp_id=5;
```

记得使用占位符`?`，方法`JdbcTemplate.update(String, Object...)`

![](/static/2021-07-23-14-37-35.png)

# 批量插入

单个插入

```java
String sql = "INSERT INTO employee(emp_name, salary) VALUES(?, ?)";
```

批量增删改

* `JdbcTemplate.batchUpdate(String, List<Object[]>)`
* Object[] 封装了 SQL 语句每一次执行时所需要的参数
  * 每次执行要用的参数
* List 集合封装了 SQL 语句多次执行时的所有参数
  * sql总共执行次数

---

例子

![](/static/2021-07-23-14-44-38.png)

* 返回`int`，每次sql影响多少行

# 查询单行

查询emp_id=5的数据库记录，封装为一个Java对象返回

`SELECT emp_id empId，emp_name <别名> FROM employee WHERE emp_id=?`

* 注意**javaBean属性需要和数据库中字段名一致**，否则无法封装对象
  * 起别名
* jdbctemplate查询方法级别进行了区别
  * **查询集合** - jdbctemplate.query()
  * **查询单个对象** - jdbctemplate.queryForObject()
    * 如果查询没结果会报错

`JdbcTemplate.queryForObject(String, RowMapper<Department>, Object...)`

![](/static/2021-07-23-14-49-49.png)

* bean属性映射行

---

![](/static/2021-07-23-14-50-38.png)
![](/static/2021-07-23-14-50-55.png)

# 查询集合

查询salary>4000的数据库记录，封装为List集合返回

![](/static/2021-07-23-14-58-26.png)

# 查询最大salary

`SELECT MAX(salary) FROM employee`

查询无论返回单行数据还是单个字段数据，都是调用queryForObject

![](/static/2021-07-23-15-01-03.png)

* 非自定义类型，不用RowMapper

# 具名参数

`?`

* 占位符参数，`?`的顺序不能乱

具名参数

* 不用占位符，用变量名
* 不用关心参数顺序
* `:参数名`
  * `(:empName, :salary)`

:bulb:先注入 `NamedParameterJdbcTemplate`对象才能使用

```xml
< ! - - 配置可以使用具名参数的 JDBCTemplate 类对象 - - >
 <bean id= "namedTemplate" class= "org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate" > 
 < ! - - 没有无参构造器， 必须传入数据源或 JdbcTemplate 对象 - - > 
 <constructor-arg ref= "dataSource" />
  </bean>
```

:bulb: 把所有具名参数要封装进`Map`再传入

* `NamedParameterJdbcTemplate.update(String, Map<String, ?>)`

---

例子1 - 使用带有**具名参数**的SQL语句插入一条员工记录，并以Map形式传入参数值

![](/static/2021-07-23-15-08-58.png)

例子2 - 以`SqlParameterSource`形式传入参数值

![](/static/2021-07-23-15-10-21.png)
![](/static/2021-07-23-15-12-23.png)

* 字段必须能对得上bean的属性名

```java
String sql = "INSERT INTO depts (dept_name) VALUES ( : deptName)";
 Department department = new Department( null , "YYY", null ) ; 
 SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(department);
  namedTemplate.update(sql, sqlParameterSource);
```

# 自动装配DAO

![](/static/2021-07-23-15-15-09.png)
![](/static/2021-07-23-15-15-55.png)
![](/static/2021-07-23-15-16-36.png)