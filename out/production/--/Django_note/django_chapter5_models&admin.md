#   Django 模型 & 数据库
##  机制
> django提供 ``Object Relational Mapper [ORM]``机制进行数据库交互
> 
> 数据库中模型表数据封装进python django模型类
   
    -   每个model描述数据库的table data

##  配置
django自带默认database - SQLite[轻量级DB]
-   db.sqlite3 可加进 .gitignore文件

- settings.py   中DB相关配置
```python
DATABASES = {
    'default': {
    'ENGINE': 'django.db.backends.sqlite3',
    'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
    #ENGINE: 采用的数据库
    #NAME:  DB path
}
```

##  创建模型
![model1](/static/model1.png)
```python
class Category(models.Model):
    name = models.CharField(max_length=128, unique=True)

    #用于解决错误拼写问题
    class Meta:
        verbose_name_plural = 'Categories'
    
    def __str__(self):
    #   return nice string when print()
        return self.name

class Page(models.Model):
    #category字段属性作为FK放入Page(1-N)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)
    title = models.CharField(max_length=128)
    url = models.URLField()
    views = models.IntegerField(default=0)

    def __str__(self):
        return self.title
```
一对多关系

**模型属性类型**(models类中)
-   CharField(max_length=)
    -   用于存String
-   URLField(max_lenght=)
    -   类似CharField, 用于存储 URL资源
-   IntegerField
-   DateField
    - 存储``datetime.date``对象     

> 属性字段中可指定选项
-   unique
    -   =True   该字段必须为unique (类似该字段可作为PK)
    -  =False
-   defalut = 'value'
-   NULL / null=True/ null=False
    -   该字段能否为null

-   模型关系字段选项
    -   ForeignKey ,用于建立`` 1-N``关系
    -   OneToOneFiled, 建立``1-N``关系
    -   ManyToManyFields

##  迁移
>migrate机制 : django在underlying database中创建模型表 ,帮助数据库的setup &修改更新
---
1.  进行数据库的初始化[生成模型表]
    1.  `` python manage.py migrate``
        -   该指令调用所有 settings.py 文件中的 INSTALLED_APPS 进行数据库生成&更新

2.  创建superuser用于管理数据库
    1.  `` ptyhon manage.py createsuperuser``
        -   superuser可以在admin站点进行数据库管理

3. 一旦app模型更新, 进行register数据库更新
   1.  `` python manage.py makemigrations <appname>``  
       - 包含特定 迁移文件(生成更新数据库)创建数据库schema 的过程datails
       - `` python manage.py sqlmigrate <app> 0001`` 可查看特定迁移文件sql detail

4.  app migrations file finished, commit them to database
    1.  ``python manage.py migrate``
        - output confirms that db tables have been created in DB and are ready to be used  

##  数据创建&管理
> 可使用 ``shell``或admin站点进行数据库管理
---
### shell
``python manage.py shell``指令进入shell
-   在当前project配置中加载python解释器, 此时可以与models交互
-   相关指令
```python
    # Import the Category model from the Rango application
    >>> from rango.models import Category
    # Show all the current categories
    >>> print(Category.objects.all())
    # Since no categories have been defined we get an empty QuerySet object.
    <QuerySet []>
    # Create a new category object, and save it to the database.
    >>> c = Category(name='Test')
    >>> c.save()
    # Now list all the category objects stored once more.
    >>> print(Category.objects.all())
    # You'll now see a 'Test' category.
    <QuerySet [<Category: Test>]
    # Quit the Django shell.
    >>> quit()
```
---
### Admin站点配置
> 该接口支持直接管理model instance
-  settings.py 文件中INSTALL_APPS中已经配置好preinstalled apps: ``django.contrib.admin``
-  urls.py 文件中 urlpattern同理
   -  因此可以直接访问 `` http://127.0.0.1:8000/admin/``
      -  确保此时已经创建superuser

1.  在Admin接口注册需要使用的模型
    1.  app目录下的 admin.py 中
```python
        from rango.models import Category, Page
        admin.site.register(Category)
        admin.site.register(Page)
```
> admin接口可用于check data是否正确存储, models是否正常work
> 
> Django Admin接口也是用户管理的调用端口，可以创建、修改和删除用户帐户
> admin站点也可以自定义化
---
##  创建population脚本 population script

> 用于随机生成hitting keys 测试database, 使数据库有credible data 而不是自定义data ``如之前定义的Category类的name='Test'``

- 可以通过运行该自定义脚本,生成**same sample data**进行数据库初始化
### 步骤
  1.项目目录下(django project root), 创建新文件 populate_rango.py

---
```python
if __name__ == '__main__':
    """ 此处代码只有该模块作为独立脚本时运行
        import 不会运行此处代码,但可以访问其他成员
     """
    pass
#表明该python模块为可复用脚本 or 独立脚本
# reusable script : 可以被import进其他模块
# standalon script: 可以在终端中执行 python module.py 
```
---
>   可以在setup结束后，部署时配置使用脚本进行演示
##  创建模型workflow

1. 配置数据库 settings.py 
   1. 配置  admin.py register需要用到的模型类

2.  添加模型
    1.  models.py 中定义模型类
    2.  更新 admin.py register定义好的模型,以便在admin页面访问
    3.  执行迁移 ``python manage.py makemigrations <appname>
    4.  apply change ``python manage.py migrate`` 在数据库中为model类创建shcema&table
    5.  为model创建/修改 population脚本
---
>刷新/删库

1. 针对 SQLITE数据库，删除 db.sqlite3 文件
2. 如果已经更新过app的model, 执行 ``python manage.py makemigrations <app>``
3. ``python manage.py migrate``创建新数据库文件,并将上一步的数据库tables更新进database
4. 创建新的admin用户 ``python manage.py createsuperuser``
5. 运行 ``population script``, 为新数据库创建可信test data 

---
###  自定义admin
>详情见第7章
需要修改 app目录下 admin.py

如
```python

""" 为Page页面添加新column的显示 """

class PageAdmin(admin.ModelAdmin):
    # customising the Page page
    list_display = ('title', 'category','url')

admin.site.register(Page, PageAdmin)
```

