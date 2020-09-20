# JDBC Study

study note

- [JDBC Study](#jdbc-study)
  - [JAVAWEB总览](#javaweb总览)
  - [数据持久化](#数据持久化)
  - [JDBC概述](#jdbc概述)
    - [JDBC程序编写步骤](#jdbc程序编写步骤)
  - [JAVA & SQL对应数据类型转换表](#java--sql对应数据类型转换表)
  - [获取DB连接](#获取db连接)
    - [JDBC URL](#jdbc-url)
    - [Driver接口](#driver接口)
      - [Conncetion: API直接获取](#conncetion-api直接获取)
      - [Connection: 反射获取](#connection-反射获取)
      - [Connection: DriverManager类实现](#connection-drivermanager类实现)
      - [Connection：DriverManager优化](#connectiondrivermanager优化)
      - [(*)Connection：Properties配置文件](#connectionproperties配置文件)
    - [MYSQL8.0驱动配置问题相关](#mysql80驱动配置问题相关)
  - [Statement：实现CRUD](#statement实现crud)
    - [Statement：弊端](#statement弊端)
  - [PreparedStatement：CRUD](#preparedstatementcrud)
    - [封装连接&关闭资源：自定义JDBCUtils](#封装连接关闭资源自定义jdbcutils)
    - [INSERT：例子](#insert例子)
    - [UPDATE：例子](#update例子)
    - [通用的增删改操作：CUD](#通用的增删改操作cud)
    - [SELECT：查询例子 & 处理结果集](#select查询例子--处理结果集)
    - [ORM方式处理信息(查询数据封装)](#orm方式处理信息查询数据封装)
    - [Customer表：通用查询（反射重点）](#customer表通用查询反射重点)
    - [Order表：解决字段名&类名冲突](#order表解决字段名类名冲突)
        - [字段名不对应：起别名](#字段名不对应起别名)
  - [PreparedStatement：不同表的通用查询操作](#preparedstatement不同表的通用查询操作)
  - [PreparedStatement：获取结果集合&封装](#preparedstatement获取结果集合封装)
  - [总结：查询操作流程](#总结查询操作流程)
  - [总结：PreparedStatement好处](#总结preparedstatement好处)

## JAVAWEB总览

![](/static/2020-09-19-14-37-58.png)

- servlet
  - 获取请求
  - 处理请求
  - 响应请求
- JSP（本质也是servlet）
  - 动态页面展示
  - EL & JSTL支持JSP，更好的显示

## 数据持久化

🍊 数据持久化

![](/static/2020-09-19-14-48-46.png)

- 主要应用
  - 将**内存中的数据存储在关系型数据库**中，当然也可以存储在**磁盘文件、XML数据文件**中

🍊 java中数据存取技术

- JDBC直接访问数据库，以下工具只是更好封装了JDBC
  - **JDO技术**
  - **第三方O/R工具（Hibernate,Mybatis...）**

## JDBC概述

>JDBC(Java Database Connectivity)是一个**独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口（一组API）**，定义了用来**访问数据库的标准Java类库，（java.sql,javax.sql**)使用这些类库可以以一种标准的方法、方便地访问数据库资源

![](/static/2020-09-19-15-04-40.png)
![](/static/2020-09-19-15-05-25.png)

- JDBC为访问不同的数据库提供了一种统一的途径，为开发者**屏蔽了一些细节问题**
- JDBC是个**接口规范**，各个**不同数据库JDBC驱动是由厂家编写的**

🍬 JDBC体系结构

- JDBC接口包括两个层次
  - **面向应用API**：抽象接口，给**开发人员使用**（**连DB,执行SQL，获得结果**）
  - **面向数据库API**：给开发商**开发数据库驱动**程序用

### JDBC程序编写步骤

![](/static/2020-09-19-15-14-40.png)

1. 获取Connection对象
2. 获取Statement对象
   1. 执行CRUD操作：需要结果集，不需要结果集
3. 关闭Connection对象
4. 关闭Statement对象

🍬 ODBC是微软提供的访问数据库的方式，在JDBC下又封装了一层：**JDBC-ODBC-DB**

## JAVA & SQL对应数据类型转换表

![](/static/2020-09-20-14-46-50.png)

## 获取DB连接

### JDBC URL

![](/static/2020-09-19-15-05-25.png)
![](/static/2020-09-19-16-18-47.png)

> 用于标识**选中对应的驱动程序**，从而**连接对应的数据库**

- JDBC URL组成：`jdbc:子协议:子名称`
  - **协议**：`JDBC`协议
  - **子协议**：识别<font color="red">特定数据库驱动程序</font>
  - **子名称**：标识数据库(根据不同子协议变化)，<font color="red">定位数据库提供足够信息（ip，port，数据库名）</font>

🍊 常用的JBDC URL

![](/static/2020-09-19-16-21-03.png)

### Driver接口

![](/static/2020-09-19-15-20-26.png)

- 获取Connection对象`Connection connect(String url, java.util.Properties info)`
- **需要使用JDBC-MYSQL驱动，作为module lib**
  - 提供了mysql的**Driver实现类**，再connet获取Connection对象
  - Oracle的驱动：`oracle.jdbc.driver.OracleDriver`
  - mySql的驱动： `com.mysql.jdbc.Drive`

#### Conncetion: API直接获取

```java
public void testConnection() throws SQLException, SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();// 传入mysql具体的driver实现类（mysql jdbc驱动）
        // 协议&子协议 - jdbc:mysql
        // 子名称 - ip:port:db_name： localhost:3306/test
        String url = "jdbc:mysql://localhost:3306/test";

        // 用户名&密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "12345");

        // 通过Driver实现类，获取Connection对象
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
```

#### Connection: 反射获取

相较于方式一，这里**使用反射实例化Driver，不在代码中体现第三方数据库的AP**I。体现了面向接口编程
思想。

```java
public void testConnection2() throws Exception {
    //反射获取Driver对象
    Class aClass = Class.forName("com.mysql.cj.jdbc.Driver");
    Driver driver = (Driver) aClass.newInstance();

    // 协议&子协议 - jdbc:mysql
    // 子名称 - ip:port:db_name： localhost:3306/test
    String url = "jdbc:mysql://localhost:3306/test";

    // 用户名&密码
    Properties info = new Properties();
    info.setProperty("user", "root");
    info.setProperty("password", "12345");

    // 通过Driver实现类，获取Connection对象
    Connection connect = driver.connect(url, info);
    System.out.println(connect);
}
```

#### Connection: DriverManager类实现

使用**DriverManager实现数据库的连接**

- 替换Driver实现连接

![](/static/2020-09-19-19-10-10.png)

- **注册驱动对象**`public static void registerDriver(java.sql.Driver driver)`
- `getConnection`重载方法获取`Connection`对象
  - `getConnection(String url, String user, String password)`无封装

```java
public void testConnection3() throws Exception {
    // 反射获取Driver对象
    Class aClass = Class.forName("com.mysql.cj.jdbc.Driver");
    Driver driver = (Driver) aClass.newInstance();
    // 注册驱动
    DriverManager.registerDriver(driver);
    // 连接信息
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "root";
    String password = "12345";

    // 获取连接
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
}
```

#### Connection：DriverManager优化

不必显式的注册驱动了。因为在**DriverManager的源码**中已经存**在静态代码块，实现了驱动的注册**。

```java
@Test
public void testConnection4() throws Exception {
    // 不必显式的注册驱动了。因为在DriverManager的源码中已经存在静态代码块，实现了驱动的注册
    // 随着Driver类的加载，静态代码块直接注册该驱动
    Class.forName("com.mysql.cj.jdbc.Driver");

    // 连接信息
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "root";
    String password = "12345";

    // 获取连接
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
}
```

#### (*)Connection：Properties配置文件

使用配置文件的方式保存配置信息，在代码中加载配置文件

- 建议存放在`src`路径下
- 可以考虑
  - IO & Properties集合联合使用
  - 通用路径改进properties文件的读取`Thread.currentThread.getContextClassLoader().getResource(...).getPath()`
  - <font color="red">通过`ResourceBundle`资源绑定器获取配置文件</font>

🍊 优点

- 实现了**代码和数据**的分离，如果需要修改配置信息，**直接在配置文件中修改，不需要深入代码**
- 如果修改了配置信息，**省去重新编译的过程**

```java
public void testConncetion5() throws IOException, ClassNotFoundException, SQLException {
    //    通过类加载器获取配置文件 ，字节输入流
    InputStream in = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

    // 或使用通用路径
    // String path = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath();
    // FileInputStream in = new FileInputStream(path);

    Properties properties = new Properties();
    properties.load(in);

    String user = properties.getProperty("user");
    String password = properties.getProperty("password");
    String url = properties.getProperty("url");
    String driverClass = properties.getProperty("driverClass");

    //    获取Driver&注册Driver
    Class aClass = Class.forName(driverClass);

    // 通过DriverManager获取连接
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
}
```

使用ResourceBundle获取配置文件

```java
public void testConncetion5() throws IOException, ClassNotFoundException, SQLException {
    //    通过ResourceBundle获取src，resource中的配置文件。免去定义Properties集合
    ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
    String user = bundle.getString("user");
    String password = bundle.getString("password");
    String url = bundle.getString("url");
    String driverClass = bundle.getString("driverClass");

    //    获取Driver&注册Driver
    Class aClass = Class.forName(driverClass);

    // 通过DriverManager获取连接
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
}
```

### MYSQL8.0驱动配置问题相关

> 报错：java.sql.SQLException: Unable to load authentication plugin 'caching_sha2_password'

maven项目中`pom.xml`

添加最新对应的`mysql connector`[jdbc-mysql驱动版本](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

- 这里使用`8.0.20`，作用域`compile`

```mysql
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.20</version>
</dependency>
```

> 报错：java.sql.SQLException: The server time zone value ‘xx time’ is unrecognized

- [解决参考](https://mkyong.com/jdbc/java-sql-sqlexception-the-server-time-zone-value-xx-time-is-unrecognized/)

## Statement：实现CRUD

`java.sql`包中3个接口

![](/static/2020-09-19-20-58-42.png)

- `Statement`：执行静态SQL语句&返回生成结果的对象
- `PreparedStatement`：**SQL 语句被预编译并存储在此对象中，可以使用此对象多次高效地执行该语句**
  - <font color="red">主要使用这个进行CRUD</font>
- `CallableStatement`：**执行**SQL**存储过程**

### Statement：弊端

Statement存在**sql注入问题**

- 而在用户输入数据中注入非法的 SQL 语句段
或命令(如：SELECT user, password FROM user_table WHERE user='a' OR 1 = ' AND password = ' OR '1' =
'1') ，从而利用系统的 SQL 引擎完成恶意行为的做法
- 用 PreparedStatement(从Statement扩展而来) 取代 Statement
  - 通过占位符`?`解决sql注入问题

![](/static/2020-09-19-21-13-14.png)
![](/static/2020-09-19-21-14-33.png)
![](/static/2020-09-19-21-15-47.png)
![](/static/2020-09-19-21-17-24.png)

## PreparedStatement：CRUD

子接口，代表预编译的SQL语句对象

![](/static/2020-09-19-21-20-15.png)

🍊 可将SQL语句看为2类

- 增删改
- 查

🍬 步骤

- 通过Connection对象**获取`PrepareStatement`实例**
- **预编译**：`connection.prepareStatement(sql)`其中`sql`values用占位符（<font color="red">该Statment对象清楚接下来要做什么</font>）
- **填充占位符**
  - 注意索引，sql索引在java中也从`1`起始

### 封装连接&关闭资源：自定义JDBCUtils

优化每次都需要建立连接 & 关闭资源的冗余代码

```java
public class JDBCUtils {
    public static Connection getConnection() throws IOException, SQLException {
    //    系统类加载器获取配置文件:
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //注册Driver
        Class.forName(driverClass);
        // 获取connection
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    /*
     * 关闭Connection & Statement资源
     * */
    public static void closeResource(Connection connection, Statement ps) {
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
```

### INSERT：例子

🍊 例子

![](/static/2020-09-19-21-34-03.png)
![](/static/2020-09-19-21-34-15.png)

```java
public class StatementTest {
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
        // 1.获取Connection
            ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
            String user = bundle.getString("user");
            String password = bundle.getString("password");
            String url = bundle.getString("url");
            String driverClass = bundle.getString("driverClass");
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, user, password);

            // 预编译sql语句，返回PreparedStatement对象
            String sql = "INSERT INTO customers(name,email,birth) VALUES(?,?,?)";
            ps = connection.prepareStatement(sql);// 2.通过Connection，获取PreparedStatement实例

            // 3.填充占位符
            ps.setString(1, "啊");
            ps.setString(2, "a@gmail.com");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = format.parse("1000-01-01");
            ps.setDate(3, new Date(date.getTime()));// getTime()返回Long

            // 4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 5.资源关闭：Connection & Statement
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
```

### UPDATE：例子

用JDBCUtil封装类改进

![](/static/2020-09-19-21-34-03.png)
![](/static/2020-09-19-21-34-15.png)

```java
/*
* 修改customers表中的一条记录
* */
@Test
public void testUpdate() {
    //获取连接
    Connection connection = null;
    PreparedStatement ps = null;
    try {
        connection = JDBCUtils.getConnection();
        //    获取PreparedStatement
        String sql = "UPDATE customers SET name=? WHERE id=?";
        ps = connection.prepareStatement(sql);
        //    填充占位符
        ps.setObject(1, "莫扎特");
        ps.setObject(2, 18);
        //    执行
        ps.execute();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //    资源关闭
        JDBCUtils.closeResource(connection, ps);
    }
}
```

### 通用的增删改操作：CUD

整合CUD操作，再封装

```java
/*
* 通用的增删改操作
* sql中占位符'?'的个数应与可变形参长度相同
* */
public void update(String sql, Object... args) {
    Connection connection = null;
    PreparedStatement ps = null;
    try {
        // 1.注册&获取Connection
        connection = JDBCUtils.getConnection();
        // 2.通过Connection对象，获取Statement进行CRUD
        ps = connection.prepareStatement(sql);//3.预编译，使用占位符
        // 4.填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1, args[i]);//sql索引从i+1开始
        }
        // 5.执行
        ps.execute();

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // 6.关闭资源
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
```

整合后更新的写法

```java
public void testCommonUpdate() {
    String sql = "UPDATE `order` SET order_name=? WHERE order_id=?";
    update(sql,"DD","2");
}
```

### SELECT：查询例子 & 处理结果集

需要接受结果集&处理

- <font color="red">结果集也需要关闭，close方法重载</font>

### ORM方式处理信息(查询数据封装)

处理结果集&获取字段信息后，**可以通过ORM思想处理数据** - **数据封装成对象**

![](/static/2020-09-20-18-27-27.png)

- 一个表对应一个类
- 一条记录对应一个对象
- 一个字段对应一个属性

```java
/*
* 对于Customers表的查询
* */
@Test
public void testQuery1() {
    // 1.注册&获取连接
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
        connection = JDBCUtils.getConnection();
        String sql = "SELECT id,name,email,birth FROM customers WHERE id=?";
        ps = connection.prepareStatement(sql);//2.预编译 & 获取Statement
        // 3.填充占位符
        ps.setObject(1, 1);
        // 4.执行 & 返回结果集
        resultSet = ps.executeQuery();
        // 5.处理结果集
        if (resultSet.next()) {//判断结果集是否有下条数据，如有返回true，指针下移，否则返回false，指针不动
            //获取当前数据的各个字段值
            int id = resultSet.getInt(1);//id
            String name = resultSet.getString(2);//name
            String email = resultSet.getString(3);//email
            Date birth = resultSet.getDate(4);//birth

            // 处理信息:ORM方式处理 - 数据封装成对象
            Customer customer = new Customer(id, name, email, birth);
            System.out.println(customer);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        6.资源关闭
        JDBCUtils.closeResource(connection, ps, resultSet);
    }
}
```

### Customer表：通用查询（反射重点）

🍊 重点

- `ResultSetMetaData`封装了结果集的元数据
  - 获取**结果集元数据**`resultSet.getMetaData()`
  - **字段数量**`metaData.getColumnCount()`
  - **获取字段名称**`metaData.getColumnName`
- `ResultSet`
  - 根据**字段索引数**，<font color="red">可以查出特定一条记录的字段值</font>
- <font color="red">反射：为Customer类对象，特定属性赋值</font>

```java
Field field = Customer.class.getDeclaredField(columnName);//获取特定字段的Field对象
//修改访问权限：如private
field.setAccessible(true);
//通过field对象，给customer实例的特定属性赋值
field.set(customer,columnValue);
```

---

```java
    @Test
    public Customer queryForCustomer(String sql, Object... args) throws Exception{
        //获取连接&注册
        Connection connection = JDBCUtils.getConnection();
        //获取Statement
        PreparedStatement ps = connection.prepareStatement(sql);//预编译
        //填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        //执行获取结果集
        ResultSet resultSet = ps.executeQuery();
        //获取结果集元数据ResultSetMetaData，封装了结果集的列数（字段数）
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取列数
        int columnCount = metaData.getColumnCount();
        //处理结果集，每条结果的字段进行处理
        if (resultSet.next()) {//如有记录返回T,指针移动
            Customer customer = new Customer();
            for (int i = 0; i < columnCount; i++) {//根据列数（字段数），获取几次数据
                Object columnValue = resultSet.getObject(i + 1);
                //如何封装成Customer，给他指定的某个【属性名赋值value】
                // 获取每个列的列名
                String columnName = metaData.getColumnName(i + 1);
                // 通过反射给 【指定属性名】赋值
                Field field = Customer.class.getDeclaredField(columnName);
                //针对私有属性，允许访问
                field.setAccessible(true);
                field.set(customer, columnValue);
            }
            return customer;
        }
        //关闭资源
        JDBCUtils.closeResource(connection, ps, resultSet);
        return null;
    }
```

### Order表：解决字段名&类名冲突

封装类

```java
static class Order {
    private int orderId;
    private String orderName;
    private Date orderDate;

    public Order() {
        super();//显示调用父类Object构造函数
    }

    public Order(int orderId, String orderName, Date orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
```

非反射封装

```java
// 最基本操作
 @Test
public void testQuery1() {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet=null;
    try {
        //获取&注册链接
        connection = JDBCUtils.getConnection();
        String sql = "SELECT order_id,order_name,order_date FROM `order` WHERE order_id=?";
        //预编译
        ps = connection.prepareStatement(sql);
        //填充占位符
        ps.setObject(1, 1);
        //获得结果集
        resultSet = ps.executeQuery();
        //1条记录，处理所有字段
        //元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (resultSet.next()) {
            int id = (int) resultSet.getObject(1);
            String name = (String) resultSet.getObject(2);
            Date date = (Date) resultSet.getObject(3);
            Order order = new Order(id, name, date);
            System.out.println(order);
        }
    } catch (Exception throwables) {
        throwables.printStackTrace();
    } finally {
        JDBCUtils.closeResource(connection, ps,resultSet);
    }
}
```

##### 字段名不对应：起别名

解决类属性名&表名不一致的问题

![](/static/2020-09-20-18-18-57.png)

- sql语句中**起别名（对应Order类属性）**
- 注意**获取列名改为获取列别名**
  - `getColumnLable`

反射封装写法

```java
 public Order QueryForOrder(String sql, Object... args) {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
        connection = JDBCUtils.getConnection();
        ps = connection.prepareStatement(sql);
        //填充
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (resultSet.next()) {
            Order order = new Order();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = resultSet.getObject(i + 1);
                String columnName = String columnLabel = metaData.getColumnLabel(i + 1);//获取列的别名
                //反射赋值
                Field field = Order.class.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(order, columnValue);
            }
            return order;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        JDBCUtils.closeResource(connection, ps,resultSet);
    }
    return null;
}
```

## PreparedStatement：不同表的通用查询操作

类泛型的使用

- 方法必须有泛型声明`<T> T`

```java
public <T> T getInstance(Class<T> clazz,String sql, Object... args) {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
        connection = JDBCUtils.getConnection();
        ps = connection.prepareStatement(sql);
        //填充
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (resultSet.next()) {
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = resultSet.getObject(i + 1);
                String columnLabel = metaData.getColumnLabel(i + 1);//获取列的别名
                //反射赋值
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t, columnValue);
            }
            return t;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        JDBCUtils.closeResource(connection, ps,resultSet);
    }
    return null;
};
```

## PreparedStatement：获取结果集合&封装

**while替换**&**封装对象添加至集合**

```java
public <T> List<T> getList(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            //填充
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<T> arrayList = new ArrayList<T>();
            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);//获取列的别名
                    //反射赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                arrayList.add(t);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, ps,resultSet);
        }
        return null;
    }

```

## 总结：查询操作流程

![](/static/2020-09-20-18-32-50.png)

## 总结：PreparedStatement好处

- 解决sql注入问题
- 可以操作`BLOB`数据，Statement不可以
- 可以实现更高效批量操作