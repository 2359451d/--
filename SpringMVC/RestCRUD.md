# Content

* [Content](#content)
* [效果](#效果)
* [URL](#url)
* [查询所有](#查询所有)
* [添加](#添加)
* [Spring表单标签](#spring表单标签)
* [员工修改](#员工修改)
* [删除](#删除)

# 效果

注意配置过滤器以使用其他HTTP请求

![](/static/2021-07-31-21-15-52.png)
![](/static/2021-07-31-21-16-07.png)
![](/static/2021-07-31-21-16-17.png)
![](/static/2021-07-31-21-16-30.png)

省略Service层

# URL

`/资源名/标识`

`/emp/id`

* GET, PUT, DELETE

`/emp`

* POST

# 查询所有

GetAll

![](/static/2021-07-31-21-24-14.png)

![](/static/2021-07-31-21-22-05.png)

![](/static/2021-07-31-21-23-14.png)

# 添加

![](/static/2021-07-31-21-25-49.png)
![](/static/2021-07-31-21-26-58.png)
![](/static/2021-07-31-21-30-00.png)

# Spring表单标签

![](/static/2021-07-31-21-31-12.png)

```xml
<%@ taglib prefix="form" uri="htp://www.springframework.org/tags/form" %>
```

![](/static/2021-07-31-21-48-11.png)
![](/static/2021-07-31-21-48-54.png)
![](/static/2021-07-31-21-48-19.png)

`form:input/radiobutton/...`

* `path`  - 相当于`name`
  * 可当做原生name属性
  * 自动回显**隐含模型**中某个对象中对应的值
    * 所以指定的每一个属性隐含模型中一定得存在一个对象对应的上 - <font color="deeppink">可以通过添加 `modelAttribute`属性显式指定，取对象时用的key（原本的key是取`command`，如果不存在抛出异常）</font>

`form:select`

* `items` - 指定要遍历的集合
* `itemLabel` - 指定遍历出这个对象的哪个属性作为option标签体的值
* `itemValue` - 指定遍历的对象，哪个属性作为要提交的value值

# 员工修改

查，信息放在请求域，在修改页面回显数据

![](/static/2021-07-31-22-12-24.png)
![](/static/2021-07-31-22-12-55.png)

---

提交修改时，来到列表页面

* 为了避免提交部分更新，其他部分为null的情况，利用ModelAttribute完成POJO填充

:bulb: ModelAttribute在其他方法前运行

* 这里不要获取路径上的可变id，因为不是所有其他路径都带id，**选择利用请求参数`RequestParam`获取可变id**

![](/static/2021-07-31-22-30-33.png)

# 删除

![](/static/2021-07-31-22-33-40.png)

1. 发delete请求（隐含域 `name=_method`)
   1. ![](/static/2021-07-31-22-34-29.png)
2. ![](/static/2021-07-31-22-35-40.png)

:bulb: 利用scripts，超链接提交表单

* 注意默认js请求交给tomcat，前端控制器拦不到
* spring‘配置 `mvc:default-servlet-handler`告诉spring，自己映射的请求自己处理，不能处理的交给tomcat

![](/static/2021-07-31-22-43-18.png)