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