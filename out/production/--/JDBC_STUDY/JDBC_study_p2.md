# JDBC Study

study note：

- BLOB类型字段操作
- PrepareStatement批处理操作

- [JDBC Study](#jdbc-study)
  - [操作BLOB字段](#操作blob字段)
    - [Insert：Blob类型字段](#insertblob类型字段)
    - [Select：Blob类型字段](#selectblob类型字段)
    - [Insert Blob类型的特殊情况](#insert-blob类型的特殊情况)
  - [PreparedStatement vs Statement](#preparedstatement-vs-statement)
  - [PreparedStatement: 批量数据操作](#preparedstatement-批量数据操作)
    - [方式一：Statement迭代插入](#方式一statement迭代插入)
    - [方式二：PreparedStatement迭代批量插入](#方式二preparedstatement迭代批量插入)
    - [方式三&四：采用java批量更新机制](#方式三四采用java批量更新机制)
      - [例子](#例子)
  - [数据库事务](#数据库事务)
    - [事务的ACID属性](#事务的acid属性)
    - [四个隔离级别 & 并发问题](#四个隔离级别--并发问题)
    - [mysql中设置隔离级别](#mysql中设置隔离级别)
  - [DAO：DATA ACCESS OBJECT](#daodata-access-object)
  - [数据库连接池](#数据库连接池)
    - [多种开源数据库连接池](#多种开源数据库连接池)
    - [C3P0连接池：连接实现方式](#c3p0连接池连接实现方式)
    - [DBCP连接池](#dbcp连接池)
    - [(*)Druid德鲁伊连接池](#druid德鲁伊连接池)
  - [Apache-DBUtils：实现CRUD](#apache-dbutils实现crud)
    - [QueryRunner](#queryrunner)
      - [update](#update)
    - [query & ResultSetHandler（interface）](#query--resultsethandlerinterface)
      - [ResultSetHandler接口&实现类](#resultsethandler接口实现类)
      - [自定义ResultSetHandler](#自定义resultsethandler)
    - [DBUtiles关闭资源](#dbutiles关闭资源)

## 操作BLOB字段

> MySQL中，BLOB是一个二进制大型对象，是一个可以存储大量数据的容器，它能容纳不同大小的数据。

- 必须使用PreparedStatement
  - **因为BLOB类型数据无法用str拼接写**
- <font color="red">如果存储的文件过大，数据库性能下降</font>

🍊 四种BLOB类型

- **TinyBlob**
  - 255B
- **Blob**
  - 65K
- **MediumBlob**
  - 16M
- **LongBlob**
  - 4G

### Insert：Blob类型字段

![](/static/2020-09-20-22-09-35.png)

### Select：Blob类型字段

查询

- 重点怎么处理blob类型
  - 二进制流文件，**先获取`Blob`对象**，`resultSet.getBlob("...")`
  - 通过Bolb对象，**获取二进制流`blob.getBinaryStream()`**
  - ，再输出至本地

```java
String sql = "SELECT id, name, email, birth, photo FROM customer WHERE id = ?";
conn = getConnection();
ps = conn.prepareStatement(sql);
ps.setInt(1, 8);
rs = ps.executeQuery();
if(rs.next()){
    Integer id = rs.getInt(1);
    String name = rs.getString(2);
    String email = rs.getString(3);
    Date birth = rs.getDate(4);
    Customer cust = new Customer(id, name, email, birth);
    System.out.println(cust);
    //读取Blob类型的字段
    Blob photo = rs.getBlob(5);
    InputStream is = photo.getBinaryStream();
    OutputStream os = new FileOutputStream("c.jpg");
    byte [] buffer = new byte[1024];
    int len = 0;
    while((len = is.read(buffer)) != -1){
    os.write(buffer, 0, len);
    }
    JDBCUtils.closeResource(conn, ps, rs);
    if(is != null){
    is.close();
    }
    if(os != null){
    os.close();
    }
}
```

### Insert Blob类型的特殊情况

> 如果在指定了相关的Blob类型以后，还报错：xxx too large，那么在mysql的安装目录下，找my.ini文件加上如下的配置参数： `max_allowed_packet=16M`。同时注意：修改了my.ini文件之后，需要重新启动mysql服务

- mysql8.0默认大小`4M`

## PreparedStatement vs Statement

![](/static/2020-09-20-22-54-02.png)

## PreparedStatement: 批量数据操作

update & delete本身就具有批量操作效果

- **不指定筛选条件，会全表改变**

🍊 PreparedStatement如何**高效批量插入？**

### 方式一：Statement迭代插入

```java
Connection conn  = JDBCUtils.getConnection();
Statement st = conn.createStatement();
for (int i=1;i<=20000;i++){
    String sql = "insert into goods(name) values('name_'+"+i +")";
    st.executeUpdate(sql);
}
```

### 方式二：PreparedStatement迭代批量插入

```java
long start = System.currentTimeMillis();
Conncetion conn = JDBCUtils.getConnection();

String sql = "insert into goods(name) values(?)";
PreparedStatement ps = conn.prepareStatement(sql);
for (int i=1;i<=20000;i++){
    ps.setString(1, "name_" +i);
    ps.executeUpdate();
}
long end = System.currentTimeMillis();
System.out.println("Time cost:" + end-start);//82340

JDBCUtils.closeResource(conn,ps);
```

### 方式三&四：采用java批量更新机制

通过java批量**更新**机制，提升效率

- 允许多条语句**一次性提交(攒)**给数据库批量处理
  - <font color="red">比每次单独提交更高效</font>

🍊 批量处理语句，涉及的主要方法

- **添加需要批量处理的SQL语句或参数（预编译每条之后）**
  - `ps.addBatch(String)`批量插参数
  - `ps.addBatch()`//批量插语句
- **执行批量处理语句**
  - `ps.executeBatch()`
- **清空缓存数据**
  - `ps.clearBatch()`

🍊 通常两种批量执行SQL的情况

- 多条SQL语句，批量处理
- 一个SQL语句，批量传参

#### 例子

🍊 注意

- **mysql服务器默认关闭批处理**
  - 开启批处理`/rewriteBatchedStatements=true`追加写在配置文件`jdbc.properties`的url后
  - `url=jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true`
- 使用更新的mysql驱动
- <font color="red">优化 - 方式4:不允许自动提交事务`connection.setAutoCommit(false)`</font>

```java
@Test
public void testInsert1() throws Exception{
    long start = System.currentTimeMillis();
    Connection conn = JDBCUtils.getConnection();
    //不允许自动提交事务
    conn.setAutoCommit(false);
    String sql = "insert into goods(name)values(?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    for(int i = 1;i <= 1000000;i++){
        // 填充占位符
        ps.setString(1, "name_" + i);
        //1.“攒”sql - 批处理
        ps.addBatch();
        if(i % 500 == 0){//攒500次在执行
            //2.执行
            ps.executeBatch();
            //3.清空
            ps.clearBatch();
        }
    }
    long end = System.currentTimeMillis();
    System.out.println("花费的时间为：" + (end - start));//20000条：625
    //1000000条:14733
    JDBCUtils.closeResource(conn, ps);
}

```

## 数据库事务

什么是事务？

> **事务：一组逻辑操作单元从一种状态变换到另一种状态**（1个或多个DML操作）

事务处理原则

> 所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式。当在一个事务中执行多个操作时，**要么所有的事务都被提交(commit)**，那么这些修改就永久地保存下来；**要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)到最初**

- <font color="red">即，不能出现中间状态，要么commit，要么rollback状态</font>

🍊 会自动提交的操作

- DDL
  - **无法修改**
- DML(默认自动提交，可以修改)
  - `set autocommit =false`

🍬 **连接中设置取消数据的自动提交**

- **`conn.setAutoCommit(false)`**
- <font color="red">进行完事务操作后，无异常提交事务</font>
  - `conn.commit()`
- <font color="red">进行完事务操作后，有异常回滚事务</font>
  - `conn.rollback()`
- <font color="blue">完成后建议连接关闭之前，恢复DML自动提交功能</font>
  - **`conn.setAutoCommit(true)`**
- 查询隔离级别
  - **`conn.getTransactionIsolation()`**
- 设置隔离级别
  - **`conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED)`**

![](/static/2020-09-27-12-36-30.png)

### 事务的ACID属性

1. **原子性 Atomicity**
   1. 原子性指事务是一个不可分割的工作单位，要么都发生要么都不发生
2. **一致性 Consistency**
   1. 事务必须使数据库从一个一致性状态变换到另外一个一致性状态。
3. **隔离性 Isolation**
   1. 一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，**并发执行的各个事务之间不能互相干扰**
4. **持久性 Durability**
   1. 指一个事务**一旦被提交，它对数据库中数据的改变就是永久性的**，接下来的其他操作和数据库故障不应该对其有任何影响

### 四个隔离级别 & 并发问题

![](/static/2020-09-27-13-02-19.png)

![](/static/2020-09-27-13-02-29.png)
![](/static/2020-09-27-13-16-30.png)

脏读

- 没提交就被读出来最新的数据（临时，不一定是正确的数据）

- read uncommitted
  - 3种问题都不解决
- **read committed**
  - 解决脏读，不解决不可重复读&幻读
- **repeatable read**
  - 解决脏读&不可重复读，不解决幻读
- serializable
  - 所有问题解决，性能差

### mysql中设置隔离级别

查看会话隔离级别

- `select @@tx_isolation`

设置当前会话隔离级别

- `set transaction isolation level read committed`
- `set global transaction isolation level read committed`设置全局

创建mysql用户

- `create user tom identified by 'abc123'`
- 授权通过网络方式登录的tom用户，对所有库所有表的全部权限，密码设为abc123`grant all privileges on *.* to tom@'%' identified by 'abc123'`
- #给tom用户使用本地命令行方式，授予atguigudb这个库下的所有表的插删改查的权限`grant select,insert,delete,update on atguigudb.* to tom@localhost identified by 'abc123'`

## DAO：DATA ACCESS OBJECT

BaseDAO，封装了访问数据信息的**类&接口**

![](/static/2020-09-27-15-39-15.png)

- DAO抽象类，包括最基本的通用CRUD方法（**考虑事务**）
  - 不包含任何业务相关信息
  - <font color="red">针对具体业务，需要具体的DAO实现</font>
  - <font color="blue">通用cud操作定义好，但是命名还未统一（connection也需要复用，按id删/查之类的操作，...），需要在后续接口中定义好操作名，最后在统一实现类中实现接口方法（配合调用DAO抽象类中通用方法）</font>
- <font color="red">实现了功能模块化，有利于代码维护&升级</font>

接口

- 定义针对特定业务的CRUD常用操作的规范

具体业务实现类

- 继承BaseDAO抽象类
  - 封装了基本通用CRUD操作
- 实现业务接口
  - 决定实现类需要提供的业务操作
    - 查生日
    - 根据id查用户
    - 根据id更新
    - ...

## 数据库连接池

传统模式

![](/static/2020-09-27-16-43-06.png)

传统模式存在的问题

![](/static/2020-09-27-16-43-30.png)

- 连接资源的创建释放压力
- 每一次连接后都需要断开
  - **可能造成java内存泄漏（内存对象未被很好回收）**
- 无法控制创建的连接数
  - 类似线程池，不使用创建线程对象方面不受控制，不断使用系统资源

---

数据库连接池技术

![](/static/2020-09-27-16-51-44.png)

### 多种开源数据库连接池

![](/static/2020-09-27-16-57-30.png)
![](/static/2020-09-27-16-57-53.png)

- 注意
  - 数据源无需创建多个，**产生数据库连接的工厂**
  - 应用连接池后，关闭连接照常，只不过关闭后**归还给连接池**

### C3P0连接池：连接实现方式

连接方式1，不推荐该方法

![](/static/2020-09-27-17-10-35.png)
![](/static/2020-09-27-17-11-29.png)

连接方式2，使用配置文件`c3p0-config.xml`方式

![](/static/2020-09-27-17-12-52.png)
![](/static/2020-09-27-17-13-02.png)
![](/static/2020-09-27-17-21-12.png)

🍊 注意配合JBDCUtils工具类使用，连接池只需要一个，放在方法外

### DBCP连接池

![](/static/2020-09-27-17-27-22.png)

连接池相关属性配置

![](/static/2020-09-27-17-36-22.png)

---

连接方式一（显式配置）

![](/static/2020-09-27-17-39-30.png)
![](/static/2020-09-27-17-39-37.png)

连接方式二（使用配置文件`dbcp.properties`）

- `BasicDataSourceFactory.createDataSource(Properties properties)`通过工厂方法获取DataSource
- 连接池参数再配置文件中写明

![](/static/2020-09-27-17-42-11.png)
![](/static/2020-09-27-17-44-23.png)

---

🍊 注意配合JBDCUtils工具类使用，连接池只需要一个，放在方法外

![](/static/2020-09-27-17-48-14.png)

### (*)Druid德鲁伊连接池

![](/static/2020-09-27-17-49-54.png)

1. 先导驱动依赖`druid-1.1.10.jar`
2. **相关DataSource实现类`DruidDataSource`**
   1. **`DruidDataSourceFactory`工厂方法，根据配置文件获取连接池**
3. 相关连接池参数配置
   1. ![](/static/2020-09-27-17-56-16.png)
   2. ![](/static/2020-09-27-17-56-39.png)

---

连接方式（根据配置文件`druid.properties`）

![](/static/2020-09-27-18-01-42.png)
![](/static/2020-09-27-18-01-32.png)

连接池只生成一次（静态代码块）

![](/static/2020-09-27-18-03-38.png)

## Apache-DBUtils：实现CRUD

直接替换自己写的JDBCUtils工具类

![](/static/2020-09-27-18-07-02.png)
![](/static/2020-09-27-18-10-04.png)

- 使用其`org.apache.commons.dbutils`提供的`QueryRunner`&`ResultSetHandler`
  - 提供如关闭连接、装载JDBC驱动程序等常规工作的工具类，**里面的所有方法都是静态的**

🍊 DBUtils主要方法

![](/static/2020-09-27-18-13-13.png)
![](/static/2020-09-27-18-13-25.png)

### QueryRunner

![](/static/2020-09-27-18-22-20.png)

#### update

![](/static/2020-09-27-18-19-39.png)

- QueryRunner提供的`update`重载方法
  - **可以实现增删改**

```java
// 实现insert
@Test
public void testInsert() {
    QueryRunner runner = new QueryRunner();
    //从druid连接池中获取一个连接
    Connection connection = JDBCUtils.getConnection();
    String sql = "insert into customers(name,email,birth) values(?,?,?)";
    //返回影响的记录数
    int insertCount = runner.update(connection, sql, "tom", "tom@123.com", "1997-07-01");
    System.out.println("添加了"+insertCount+"条记录");
    JDBCUtils.closeResource(connection, null);
}
```

🍊 update实现delete

![](/static/2020-09-27-18-23-30.png)

### query & ResultSetHandler（interface）

![](/static/2020-09-27-18-24-05.png)

- QueryRunner提供的`query`重载方法
  - **可以实现查询**
- **ResultSetHandler**结果集处理器（接口）

#### ResultSetHandler接口&实现类

该接口用于处理`java.sql.ReusultSet`

- 提供单独方法`Objcet handle`

🍊 主要实现类

![](/static/2020-09-27-19-25-30.png)

🍊 例子

- 使用`BeanHandler`，**将数据结果封装到JavaBean实例中**
- 使用`BeanListHandler`，**将多个数据结果封装到JavaBean实例中，存放到List里**
- 使用`MapHandler`，**将数据结果封装到Map中**
  - key-列名
  - value-值
- 使用`MapListHandler`，**将多个数据结果封装到Map中，再放在List里**
- <font color="red">`ScalarHandler`查询单个值对象（分组函数/单行处理函数）</font>

```java
@Test
public void testQuery() {
    QueryRunner runner = new QueryRunner();
    Connection connection = JDBCUtils.getConnection();
    String sql = "select id,name,email,birth from customers where id=?";
    BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
    Customer customer = runner.query(connection, sql, handler, 23);

    JDBCUtils.closeResource(connection, null);
}
```

```java
@Test
public void testQuery() {
    QueryRunner runner = new QueryRunner();
    Connection connection = JDBCUtils.getConnection();
    String sql = "select id,name,email,birth from customers where id<?";
    BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
    List<Customer> list = runner.query(connection, sql, handler, 23);

    JDBCUtils.closeResource(connection, null);
}
```

```java
@Test
public void testQuery() {
    QueryRunner runner = new QueryRunner();
    Connection connection = JDBCUtils.getConnection();
    String sql = "select count(*) from customers";
    ScalarHandler handler = new ScalarHandler();
    Long count  = runner.query(connection, sql, handler);
    JDBCUtils.closeResource(connection, null);
}
```

#### 自定义ResultSetHandler

不满意API提供的实现类可以自定义

![](/static/2020-09-27-20-17-15.png)
![](/static/2020-09-27-20-18-30.png)

### DBUtiles关闭资源

![](/static/2020-09-27-20-45-47.png)

- `close`
- `closeQuietly`