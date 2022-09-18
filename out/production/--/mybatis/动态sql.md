# Content

简化sql语句动态拼串操作

* [Content](#content)
* [if](#if)
* [where](#where)
* [trim](#trim)
* [foreach](#foreach)
* [choose](#choose)
* [set](#set)
* [OGNL表达式](#ognl表达式)
* [bind](#bind)
* [可重用sql](#可重用sql)

# if

带了什么属性就按什么属性查

常规查询
![](/static/2021-08-06-22-05-37.png)

---

动态条件查询

* `if`判断
  * `test`判断条件（OGNL表达式）

![](/static/2021-08-06-22-09-14.png)
![](/static/2021-08-06-22-11-31.png)

* 传参是pojo - `#{bean属性}`取值

# where

![](/static/2021-08-06-22-15-43.png)
![](/static/2021-08-06-22-24-46.png)

官方文档：where 元素只会在至少有一个子元素的条件返回 SQL 子句的情况下才去插入“WHERE”子句。而且，若语句的开头为“AND”或“OR”，where 元素也会将它们去除。

# trim

![](/static/2021-08-06-22-20-39.png)
![](/static/2021-08-06-22-21-37.png)
![](/static/2021-08-06-22-25-15.png)

`prefix`

* 前缀，为sql整体添加前缀

`prefixOverrides`

* 去除整体字符串**前面多余的字符**

`suffix`

* 为整体添加后缀

`suffixOverrides`

* **去除结尾多余的字符**

# foreach

对一个集合进行 遍 历，通 常 是在 构建 I N 条 件 语句 的时 候

![](/static/2021-08-06-22-33-46.png)

* 这里利用`@Param`给传参取别名
  * 传入id集合

---

属性

* `collection` 指定要遍历的集合的key
* `open` 开始符
* `close` 结束符
* `item` 取出的每个元素的引用名
* `separator` 分隔符
* `index` 索引，
  * 如果是**list**
    * index - 指定变量保存当前索引
    * item - 当前遍历元素的值
  * 如果是**map**
    * index - 指定变量保存当前遍历的元素的**key**
    * item - 当前遍历元素的值

![](/static/2021-08-06-22-40-17.png)
![](/static/2021-08-06-22-39-42.png)
![](/static/2021-08-06-22-40-38.png)

# choose

分支选择（相当于ifelse，存在短路）

id不为空按id查，否则按别的（name，birth）。三个都不带，走otherwise。

![](/static/2021-08-06-22-05-37.png)
![](/static/2021-08-06-22-44-49.png)

# set

全字段更新

![](/static/2021-08-06-22-48-33.png)

---

替换update的set，实现动态更新（可以非全字段更新，带了哪个字段就更新哪个）

* `set`会动态地在行首插入 SET 关键字，并**会删掉额外的逗号**（这些逗号是在使用条件语句给列赋值时引入的

![](/static/2021-08-06-22-52-12.png)
![](/static/2021-08-06-22-52-21.png)

![](/static/2021-08-06-22-54-35.png)

# OGNL表达式

![](/static/2021-08-06-22-58-02.png)
![](/static/2021-08-06-22-59-44.png)

除了可以判断传入的参数，还可以判断

* `_parameter`
  * 单个参数
  * 多个参数
    * 代表**参数集合起来的map**
      * 默认key - `0,1,param1, param2`
      * 不清楚`@Param`取别名能不能按别名封装map
* `_databaseId`
  * 如果开了数据库移植，会自动注入
  * ![](/static/2021-08-06-23-14-37.png)

# bind

![](/static/2021-08-06-23-16-14.png)

# 可重用sql

`sql`标签 & `include`子标签结合使用

![](/static/2021-08-06-23-17-41.png)