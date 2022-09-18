# Content

* [Content](#content)
* [基于注解的AOP](#基于注解的aop)
* [基于XML配置的AOP](#基于xml配置的aop)
* [注解 vs 配置](#注解-vs-配置)
* [aop:aspect-autoproxy](#aopaspect-autoproxy)

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

# aop:aspect-autoproxy

要说Spring xml配置中的<aop:aspectj-autoproxy />，先从@EnableAspectJAutoProxy注解说起，

Spring的配置除了使用xml之外，还可以使用Java类+ 注解的方式，例如下面的AppConfig类：

```java
// 配置文件类

 @Configuration
 @EnableAspectJAutoProxy
 public class AppConfig {
     @Bean
     public FooService fooService() {
         return new FooService();
     }

     @Bean // the Aspect itself must also be a Bean
     public MyAspect myAspect() {
         return new MyAspect();
     }
 }

----------------------------------------

// Service类

 public class FooService {
     // various methods
 }

----------------------------------------

// 切面类

 @Aspect
 public class MyAspect {
     @Before("execution(* FooService+.*(..))")
     public void advice() {
         // advise FooService methods as appropriate
     }
 }
```

**其中@EnableAspectJAutoProxy注解会确保MyAspect切面被合理地处理并且FooService类会被自动创建代理,Spring 在内部会采用AnnotationAwareAspectJAutoProxyCreator进行自动代理的创建工作**。

用户可以自己控制代理的类型，通过proxyTargetClass属性，默认false，采用JDK动态代理织入增强；如果设为true，则采用CGLIB动态代理织入增强。不过即使proxyTargetClass设置为false，如果目标类没有声明接口，则Spring将自动使用CGLib动态代理。

注意，@Aspect切面类也可以同时加上@Component注解，通过包扫描来实现bean的自动注册。

如果使用xml配置，则使用<aop:aspectj-autoproxy/>，效果等同于@EnableAspectJAutoProxy。

在配置类上添加@EnableAspectJAutoProxy注解，便能够开启注解版的AOP功能。也就是说，如果要使注解版的AOP功能起作用的话，那么就得需要在配置类上添加@EnableAspectJAutoProxy注解