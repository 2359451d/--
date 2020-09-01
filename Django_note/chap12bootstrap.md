# 集成bootstrap

响应式设计框架

🍬 本章研究`dashboard layout`后台布局，提供

* 头部菜单栏 top menu bar
* 侧边栏 sidebar(分类)
* 主内容区

## 使用

[引入base模板](https://github.com/maxwelld90/tango_with_django_2_code/blob/3ce1561c5a69b6918ae9257636f4bc09068f4d72/tango_with_django_project/templates/rango/bootstrap-base.html)

## 基于源码的更改

* 所有`../../`的引用更改为`https://getbootstrap.com/docs/4.2/`
  * 意味着文件系统中，(以当前目录为基准)返回2级目录
  * 确保可以使用bootstrap外部文件，css，js
* 更改`dashboard.css`为绝对URL获取资源
* 删除导航栏navigation bar中的搜索框
* ![](/static/2020-03-17-15-23-56.png)
* ![](/static/2020-03-17-15-24-28.png)
* 项目名更改为`Rango`
* index页link链接
* **添加侧边栏** ![](/static/2020-03-17-15-25-44.png)
* ![](/static/2020-03-17-15-26-23.png)

## 12.2 快速风格改变-调整模板

### 侧边栏:添加图标,选中active效果

改进，侧边栏显示分类

![](/static/2020-03-17-15-43-27.png)

![](/static/2020-03-17-15-48-09.png)

* 重构后的模板，不使用`Strong`标签来强调，选中的分类
  * 选中的分类，添加`active`属性
* 使用配合span标签添加归档图标`feather-icon="archive"`
  * [其他图标](https://feathericons.com/)

### 索引页: 添加标题简介盒子布局

[参考此例子](https://getbootstrap.com/docs/4.2/examples/jumbotron/#)

![](/static/2020-03-17-15-57-57.png)

容器盒子，应用了`jumbotron p-4` 类

* `p-4`控制`jumbotron`周围空白区域，此例为padding=4(1~6)
* 还有其他属性`pt/pb/pr/pl` 上下左右padding

### 分列卡片显示: 栅格grid布局

[参考album显示布局](https://getbootstrap.com/docs/4.2/examples/album/)

🍬 css的grid栅格布局，分成12列

* 最外div调整每块卡片大小，如
  * `<div class="col-md-4">`嵌套`<div class="card mb-4 shadow-sm">`
  * 每块div相对宽度占4个单元长度(12，分3)

🍬 因为我们展示2列，所以属性改为`col-md-6` (50% 12/6)

* 更改index模板中，分类&页面的盒子

### list-group列表响应式显示

[参考](https://getbootstrap.com/docs/4.2/components/list-group/)

🍊 更改 ul，li标签属性应用
![](/static/2020-03-17-16-37-33.png)

### login页

[参考](https://getbootstrap.com/docs/4.2/examples/sign-in/)

![](/static/2020-03-17-16-40-49.png)

引入外部css
` `

### 按钮

[参考](https://getbootstrap.com/docs/4.2/components/buttons/)

### 添加分类/页面

![](/static/2020-03-17-17-21-22.png)

## 12.3 使用django-bootstrap-toolkit

除了手动集成，还可以使用`django-bootstrap-toolkit` 这样的包。

* `pip install django-bootstrap-toolkit`
* 配置 settings.py 文件，将`bootstrap_toolkit` 添加到app中