# Exercises

熟悉框架，本章改进rango

1. `Category`&`Page`模型中添加`views`字段

* 计算某分类被浏览次数
* 某页面(分类中)点击次数

2. AJAX，允许用户过滤侧边分类

3. 注册页面，允许用户上传img&url

* django`User`用户验证系统

4. 允许用户查看，修改档案

5. 允许用户查看其他用户&档案

## 14.1 记录页面访问次数

实现简单视图用于**记录点击次数**，之后再重定向用户至需要页面

1. 创建新视图`goto_url()`映射url `/rango/goto/`

* 检测所有`GET`请求参数，挑出`page_id`，如`/rango/goto/?page_id=1`重定向至id=1的页面实例

2. 将挑选出来的`Page`实例的`views`字段增量1(浏览量+1)，保存

3. 如`page_id`不存在，直接重定向至用户主页

---

🍬 更新url映射，`category`模板

![](/static/2020-03-18-14-43-26.png)

## 14.2 静态搜索：站内搜索分类&页面

🍬 exercise要求非静态搜索，直接搬代码就行

结合`Category`的搜索功能，站内数据库搜索

1. 查询模板中，放入 用户查询表单& 渲染查询结果的容器(无AJAX)

* 放在每个存在的分类页面的`Page list`下面

2. 表单参数`action`指向当前分类的url
3. 更新`show_category()`视图，处理`POST`请求，将查询请求返回进上下文列表`context_dict`用于渲染

* url参数，要么写进视图参数，要么从`request`字典中获取

4. 限制用户群组权限

![](/static/2020-03-18-15-40-24.png)

## 14.3 用户档案

注册成功后，重定向用户至`UserProfile`相关

* 涉及覆写`redux`包，使用,继承该包中基于类的视图`class-based view`
* ![](/static/2020-03-19-06-05-05.png)
* 项目目录下url

1. 创建模板`profile_registration.html`，渲染`UserProfileForm`表单
2. 创建视图`register_profile()`
3. 修改注册后重定向url

## 复杂视图处理: class-based view

支持覆写`get/post()`方法，实现视图的处理
![](/static/2020-03-19-06-17-05.png)
![](/static/2020-03-19-06-21-33.png)

* 使复杂视图处理更直观

![](/static/2020-03-19-06-26-39.png)