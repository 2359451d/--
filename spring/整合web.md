# Content

* [Content](#content)
* [流程](#流程)
* [监听器 - 监听ioc容器](#监听器---监听ioc容器)

# 流程

ioc容器通过监听器创建&销毁

1. 导包
2. 配置
   1. 组件注入
      1. controller不能标在servlet层（tomcat用自己创建的）
   2. 组件自动装配（设置包扫描&注解，或者xml配置）
3. 配置声明式事务
   1. 注解or基于XML配置

:bulb: ioc容器创建 & 销毁要在合适时机完成，

* 项目启动 - ioc创建完成
* 项目销毁 - ioc销毁

**可以写一个监听器完成这个工作**

---

工具类（重写getBean)，辅助Servlet获取各个组件

![](/static/2021-07-26-18-41-34.png)
![](/static/2021-07-26-19-10-00.png)


---

配声明式事务

1. 数据源 c3p0
   1. 引用外部配置文件 context命名空间
2. JDBCTemplate操作数据库
   1. <font color="red">泛型依赖 --- 子类DAO（已知注入了ioc容器），所以接口BaseDAO不用注入也可以自动装配JDBCtemplate</font>
3. 事务管理器
   1. 注入事务管理器
   2. 配置事务增强，绑上面事务管理器组件
      1. 属性&指明事务方法
   3. 配置事务切面，绑定事务增强&切入点表达式

# 监听器 - 监听ioc容器

:bulb: ioc容器创建 & 销毁要在合适时机完成，

* 项目启动 - ioc创建完成
* 项目销毁 - ioc销毁

spring提供一个listener，要web jar包

![](/static/2021-07-26-18-58-12.png)
![](/static/2021-07-26-19-10-00.png)

* spring监听器创建好的ioc容器在 `ContextLoader.context`里
  * 通过静态方法 `getCurrentWebApplicationContext`获取

