#   Models ,Templates amd Views

##  基于data的page Workflow
    主要步骤

    1.  views.py 导入需要model
    2.  定义view function, query需要的model
    3.  将result render作为template context tag``{{variable}}穿进template
    4.  创建template文件
    5.  映射URL->view

---
```python
from rango.models import Category

def index(request):
    # Query the database for a list of ALL categories currently stored.
    # Order the categories by the number of likes in descending order.
    # Retrieve the top 5 only -- or all if less than 5.
    # Place the list in our context_dict dictionary (with our boldmessage!)
    # that will be passed to the template engine.

    #'-'表示降序排列, 去掉表示升序排列，[:]前5个category值 
    category_list = Category.objects.order_by('-likes')[:5]
    
    context_dict ={}
    context_dict['blodmessage'] = 'Crunchy, creamy, cookie, candy, cupcake!'
    context_dict['categories'] = category_list
    
    return render(request, 'rango/index.html', context=context_dict)
```

```html
<!DOCTYPE html>

{% load staticfiles %} 

<html>

<head>
    <title>Rango</title>
</head>

<body>
    <h1>Rango says...</h1>
    <div>
        hey there partner!  <br />
        <strong>{{ boldmessage }}</strong>
    </div>

    <div>
    {% if categories %}
        <ul>
            {% for catergory in categories %}
                <li>{{ catergory.name }}</li>
            {% endfor %}
        </ul>
    {% else %}
        <strong>There are no categories present.</strong>
    {% endif %}
    </div>

    <div>
        <a href="/rango/about/">About</a><br />
        <img src="{% static 'images/rango.jpg' %}" alt="Picture of Rango" /> 
    </div>
</body>

</html>
```
- 注意django template语法中, 所有template command 都用>{%and%}表示
-  变量表示{{and}}，
-  **内容在返回Httpresponse给客户端(浏览器)之前，由django templating engine解释**

---
    为每个category定义对相应page列表的新页面
    -   创建新ciew
    -   创建新url patterns, encode category name

---
### 需要处理的问题
####   · URL映射--Slug相关
    不可行的方法: 利用每个Category对象的PK(unique ID)进行映射 URL 如``/rango/category/1/`` 或``/rango/category/2/``

因此考虑使用name作为URL的一部分, 如 ``/rango/category/python/`` 此URL映射到python相关页面, readable&meaningful

注意
> url中无法解析空格, 空格需要被``percent-encoded``如 
> String ``Other Frameworks`` 会被encode成 ``Other%20Frameworks``

   - 可通过django的 ``slugify()``函数解决，使URL readable

---
admin站点支持自动生成slug变量,提高效率
```python
class CategoryAdmin(admin.ModelAdmin):
    # automatically populates the slug fields when type name 
    # specific variable in the admin to automate the process for us
    prepopulated_fields = {'slug':('name',)}

admin.site.register(Category, CategoryAdmin)
```
无法给user提供admin站点时, 需要修改model相关, 使用``slugify()``函数为每个模型类实例生成对应``slug`` field

```python
    #其他field按照正常流程定义& save
    slug = models.SlugField(unique=True)

    def save(self, *args, **kwargs):
        self.slug = slugify(self.name)
        #调用基类构造函数, 为其他变量save
        super(Category, self).save(*args, **kwargs)
```

***注意 slug变量必须为unique***

---
- WorkFLow
访问 /rango/category/<category-name-slug>

1.  import views import 模型
2.  新建新视图 show_category() 传参category_name_slug[存储slugify后的分类name]
    1.  此处需要function 来 encode & decode 变量 ``category_name_slug``

3.  新建模板 templates/rango/category.html
4.  更新url映射
5.  更新 index页面，link至每个分类名页面