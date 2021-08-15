# Content

* [Content](#content)
* [基于注解的AOP](#基于注解的aop)
* [基于XML配置的AOP](#基于xml配置的aop)
* [注解 vs 配置](#注解-vs-配置)

# 基于注解的AOP

1. 目标类 & 切面类添加进ioc容器
2. 声明哪个是切面类 `@Aspect`
3. 切面类中使用AspectJ通知注解 & 切面表达式，何时何地切入
   * `@Before/After/...(execution="切入点表达式")`
   * 可重用切入点`@PointCut`定义在void空方法上，通知注解`pointcut`属性引用
4. 开启基于注解的AOP功能

# 基于XML配置的AOP

1. bean声明目标类&切面类注册进ioc容器
2. AOP名称空间，`<aop:config>`下`<aop:aspect>`标签声明切面类
3. 配置何时切入
   1. `<aop:before/after/... method="" pointcut="">`等标签
      1. `method`指定通知方法
      2. `pointcut`指定切入点表达式，何处切入

:bulb: 可重用切入点 - `<aop:pointcut expression="" id="">`

# 注解 vs 配置

:bulb: **重要的用配置，不重要的用注解**

注解

* 快速方便

配置

* 功能完善

