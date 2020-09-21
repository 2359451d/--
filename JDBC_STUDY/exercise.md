# Exercise

- [Exercise](#exercise)
  - [E1： executeUpdate()判断执行后是否成功](#e1-executeupdate判断执行后是否成功)
  - [E2](#e2)

## E1： executeUpdate()判断执行后是否成功

![](/static/2020-09-20-19-11-09.png)

```java
@Test
public void testInsert() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("username:");
    String username = scanner.next();
    System.out.println("email:");
    String email = scanner.next();
    System.out.println("birth:");
    String birth = scanner.next();

    String sql = "INSERT INTO customers(name,email,birth) values(?,?,?)";
    int insertCount = update(sql, username, email, birth);
    if (insertCount > 0) {
        System.out.println("添加成功");
    } else {
        System.out.println("添加失败");
    }
}

/*
    * 通用的增删改操作
    * sql中占位符'?'的个数应与可变形参长度相同
    * 返回接收int，执行后影响的行数，0为不成功
    * */
public int update(String sql, Object... args) {
    // 注册&获取Connection
    Connection connection = null;
    PreparedStatement ps = null;
    try {
        connection = JDBCUtils.getConnection();
        //通过Connection对象，获取Statement进行CRUD
        ps = connection.prepareStatement(sql);//预编译，使用占位符
        // 填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);//sql索引从i+1开始
        }
        /*
        * 如果执行的查询：有结果集，返回T
        * 否则，无返回结果，返回F*/
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // 关闭资源
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
    return 0;
}
```

## E2

