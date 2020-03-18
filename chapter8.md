# 8 Working with Templates

> 避免template repeating，重复css
> 避免template中都是``hard-coded URL paths``- bad practice,url template context tag?避免后期维护工作量过大

- :speech_balloon:如后期想要改变site整体结构或URL path,需要修改所有templates

- Django提供``模板继承`` & ``URL template tag`` 解决以上问题

---

## :bento:8.1 模板中使用相对URL地址,url模板标签的使用

前文一直使用``<a href="/rango/about/">About</a>``该格式的url，当需要改变url时，需要修改模板。

- 使用``template tag url``直接映射 urls.py 文件中的``mapping name``，**动态匹配URL路径**

```html
 <a href="{% url 'rango:about' %}">About</a>
 <--!随后django框架自动匹配tango目录下urls.py文件中name属性为'about'的url，进行反向查找-->
 <!-- 依旧可以通过url传参数 -->
<a href="{% url 'rango:show_category' category.slug %}">
```

- 也可以在template中直接引用view视图

```html
<a href="{% url 'rango.views.about' %}">About</a>
<!-- 需要确保rango有about视图 -->
```

:speech_balloon: URLS & 多APP
如何管理多APP的URL？**app视图重名会产生潜在冲突**

- Django为每个单独app提供``namespace URL configuration``模块，善利用``urls.py``中的``app_name=''``字段以及``name``属性

---

## :bento:8.2 解决模板重复 repetition,使用block tag

解决过多重复css样式，后期进行修改的问题

- 找出每个page重复元素``header bar,sidebar,footer,content pane``等

- 在``base template``中提供基本page的骨架结构,定义多个``blocks``用于存放修改的元素

- 继承``base template``，修改每个block中内容

```html
9 <body>
10 {% block body_block %}
<!-- block <name> -->
11 {% endblock %}
12 </body>

<!-- 以下可以自定义block默认内容,如继承后未覆写,默认使用 -->
{% block body_block %}
This is body_block's default content.
{% endblock %}

```

通过``django template blcok tag``指定后期会修改的block``django template command``格式是``{% and %}``

```html
  <hr />
        <div>
            <ul>
                <li><a href="{% url 'rango:add_category` %}">Add a New Category</a></li>
                <li><a href="{% url 'rango:about' %}">About</a></li>
                <li><a href="{% url 'rango:index` %'}">Index</a></li>
            </ul>
        </div>
```

- ``hr``标签用于可视分离user & content

---

## :bento:8.3 模板继承

```html
{% extends 'rango/base.html' %}
```

- 删除重复code,继承base模板

- 使用相对路径

- **模板继承不包括template tag**如``load staticfiles``

:speech_balloon:

```html
<a href="{% url 'rango:add_page' category.slug %}">Add Page</a> <br />
```

以上使用``url template tag``引用``rango/<category-name>/add_page``这一URL pattern

- ``category.slug``**作为参数传入**``url template tag``和``django template engine``

- 最终产生相应正确URL

---
:blue_heart:*以上只是减少结构上的小部分重复, django中可以支持自定义*``template tag``

---

## :bento: 8.4 render()方法& request内容

view中可以使用不同方法, 如``render()``

- 需要传递参数``request``

- ``request``中包含多种信息,包含``session``**(user信息等)**

:speech_balloon:几种render()&request context

```python
def about(request):
return HttpResponse('Rango says: Here is the about page.
<a href="/rango/">Index</a>')
```

- 以上通过render函数,传参request对象,使template引擎访问``REQUEST类型,如GET/POST``及``user status``

```python
def about(request):
# prints out whether the method is a GET or a POST
print(request.method)
# prints out the user name, if no one is logged in it prints `AnonymousUser`
print(request.user)
return render(request, 'rango/about.html', {})
```

- render函数的最后一个参数是,context dict，**可用于给模板引擎传递额外信息，最后返回给浏览器之前渲染于模板**

---

## :bento:8.5 自定义模板tag

### 使用template tag

- 在rango目录下新建``templatetags``文件夹

- 在该文件夹内新建``__init__.py``和``rango_template_tags.py``文件

```python
# rango_template_tags.py
from django import template
from rango.models import Category

register = template.Library()

@register.inclusion_tag('rango/categories.html')
def get_category_list():
    return {'categories': Category.objects.all()}
```

- 该函数返回dict

- categories代表数据库中``Category``对象的列表.

- ``register.inclusion_tag()``装饰器引用了新模板``rango/categories.html``
  
  - 改模板用于模板引擎渲染categories的列表，并作为view的HTTPRESPONSE
  
  - 渲染后的列表``categories``可以放入任意view的repsonse中

:blue_heart:**完成后重启server**

```html
<!-- base.html新加code，此后所有继承的模板都有categories列表 -->
<!-- 加载templatetags引擎 -->
{% load rango_template_tags %}

  <div>
    {% block sidebar_block %}
        {% get_category_list %}
    {% endblock  %}
    </div>
```

```html
<ul>
<!-- categories.html新加code -->
    {% if categories %}
        {% for c in categories %}
            <li><a href="{% url 'rango:show_category' c.slug}">{{ c.name }}</a></li>
        {% endfor %}
    {% else %}
        <li><strong>There are no categories present.</strong></li>
    {% endif %}
</ul>
```

:blue_heart:**template tags参数**

- 可以为自定义的template tags指定参数, 灵活使用.

更新``get_category_list()``方法:

```python
# highlight当前查看的分类
# 如参数未指定, None进行替代
def get_category_list(current_category=None):
return {'categories': Category.objects.all(),
'current_category': current_category}
```

更新``base.html``文件，使用自定义参数tags

```html
    {% block sidebar_block %}
                {% get_category_list category%}
    {% endblock  %}
```

更新``categories.html``文件

```html
<ul>
    {% if categories %}
        {% for c in categories %}
            {% if c == current_category %}
            <li>
            <!-- 如果是当前查看的分类,hightlight该分类 -->
                <strong>
                    <a href="{% url 'rango:show_category' c.slug}">{{ c.name }}</a>
                </strong>
            </li>
            {% else %}
            <li>
                <a href="{% url 'rango:show_category' c.slug %}">{{c.name}}</a>
            </li>
            {% endif %}
        {% endfor %}
    {% else %}
        <li><strong>There are no categories present.</strong></li>
    {% endif %}
</ul>
```

## :bento:8.6 总结

:speech_balloon:本章

- 减少URL和模板间的耦合, 通过使用url 模板标签指向相对URLpattern

- 模板继承，减少重复code

- 使用自定义模板标签，减少view中重复code
