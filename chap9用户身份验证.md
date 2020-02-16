# :bento:User authentiacation 用户身份验证

:blue_heart:了解可用工具&底层概念
:heart:本章只学习如何**手动建立用户验证**,后章包括如何使用提前定义的app处理注册验证过程

---

本章学习django提供的用户身份验证机制，使用``django.contrib.auth``中的``auth``应用. 该应用提供以下功能:

- User & User Model

- 权限permissions, 判断用户可以做&不允许做的``binary flag(true/false)``

- 用户组Groups, 批准多用户的权限(相关权限一次赋予多个用户)

- configurable password hashing system可配置的密码哈希系统，**保证数据安全**不可或缺

- 登陆或限制性内容所需表单&视图 forms & views tools for logging or restricting content

## :bento: 9.1 设置用户验证 set up authentication

- 在使用 Django 提供的身份验证机制之前，要在项目的 settings.py 文件中添加相关的设置。检查有没有列出** django.contrib.auth 和
django.contrib.contenttypes**
![](/static/2020-02-14-06-26-21.png)

``django.contrib.auth``提供对django验证系统的访问
``django.contrib.contenttypes``提供``auth``应用跟踪数据库中的模型

:speech_balloon:**迁移相关**
如果未配置以上两个entry,自行添加后需要``python manage.py migrate``进行更新数据库. 一般添加新的应用后都需要执行迁移,用于同步模型&数据库.

---

## :bento: 9.2 Django密码哈希(算法)配置相关 Pass Hashing

不要在数据库中存储明文密码``plaintext password``.
**``auth``应用默认使用``PBKDF2算法``计算存储密码后的哈希序列**. 

如果想修改哈希值生成方式,可以在 settings.py 文件中更换该算法, 添加``PASSWORD_HASHERS``元组tuple,例如
![](/static/2020-02-14-06-34-35.png)
:blue_heart:注意算法tuple的顺序，django默认使用第一个算法，如无效采用后面的``哈希算法hasher``

- 如想采用更安全的哈希算法,可以通过``pip``安装``Bcrypt``依赖包,采用该包中算法
  - ![](/static/2020-02-14-06-37-48.png)

---

## :bento: 9.3 密码验证器(用于规范用户密码输入) password validators

Django提供多种``password validation``机制,预防用户密码容易破解.
在 settings.py 文件中, ``AUTH_PASSWORD_VALIDATORS``, Django2.x版本自带几个提前定义的**验证器用于检测常见密码(如针对长度)**.

:blue_heart:**每个验证器有``OPTIONS``字典用于自定义选项**

- 如用于自定义密码最小长度, 将该验证器的``OPTIONS``字典的``min_length``选项改为6
![](/static/2020-02-14-06-45-29.png)
---

## :bento: 9.4 User模型

``User对象(django.contrib.auth.models.User中)``是验证系统的核心

- 表示与django应用交互的每个用户个体
  - User对象能用于多方面,如访问限制,注册新用户,网站内容与创建者之间的关系

:blue_heart:User模型(对象)的5个**关键属性字段**

- 用户名 username
- 密码 passwrod
- 邮箱地址 email address
- 用户 -  名 first name
- 用户 -  姓  surname

:lemon:也有其他属性``is_active,is_staff,is_superuser``等，值为布尔类型,用于指明账户是否激活，是否为团队成员，是否拥有超级用户权限, _完整属性参考Django文档_

---

## :bento:9.5 添加用户额外属性 Addtional user attributes

如要为User模型(对象)**添加其他额外属性字段**, 需要自定义一个与``User模型``关联的模型.

如为Rango应用添加两个属性:

- ``URLField``,让rango应用的用户设定自己的网站URL

- ``ImageField``，用户自定义头像

先在 models.py 中定义一个模型(与User模型关联)

```python
#因为引用了User model,记得从auth应用中导入
from django.contrib.auth.models import User

class UserProfile(models.Model):
    # links UserProfile with User Model instance(association)
    # construct one-to-one relationship with User model
    user = models.OneToOneField(User, on_delete=models.CASCADE)

    #additional attributes we wish to include
    website = models.URLField(blank=True)
    picture = models.ImageField(upload_to='profile_images', blank=True)

    def __str__(self):
        #return meaningful string，为UserProfile实例对象返回了有意义的值
        #注意如果使用python2.x,需要额外定义__unicode__()方法
        return self.user.username
```

:lemon:通过以上步骤,我们为User账户添加了2个额外属性字段

- ![](/static/2020-02-14-07-34-47.png)
  - 该两个字段都设定了``blank=True``表示该字段可以为空,不是必须提供
  - :blue_heart:**注意,``picture的ImageField``字段**有``upload_to``参数，与``MEDIA_ROOT``值关联, 确定用户上传的**media媒体头像文件**存储位置.
    - 如``MEDIA_ROOT = <workspace>/tango_with_django_project/media/`` && ``upload_to='profile_images'``则用户上传的头像存储在``<workspace>/tango_with_django_project/media/profile_images/``路径下

:lemon: 记得在admin模块中注册UserProfile模型,以便在admin站点中管理
:green_heart:<font color='blue'>记得定义了新模型后必须更新数据库，执行生成迁移文件后执行迁移</font>

```python
from rango.models import UserProfile
admin.site.register(UserProfile)
```

:key:**优先自定义新模型关联User模型，而不是继承**

- <font color='red'>考虑到其他应用也会访问User模型, 所以通过建立一对一关系，为User模型添加额外自定义字段</font>

:key:安装pillow **(ImageField字段需要使用)**
 - 如未安装, 通过``pip``安装该依赖,如未启用JPEG支持, 可以执行``pip install pillow==5.4.1 --global-option="build_ext" --global-option="--disable-jpeg``

- ``pip list``查看VE中安装了哪些依赖包

---

## :bento: 9.6创建用户注册view&template

接下来通过auth应用的user模型使用&验证系统，实现用户注册功能

:blue_heart:**尽管可以使用现成的用户注册应用,但是最好了解底层机制,用于巩固对表单的理解&如何扩展User模型的属性&如何上传媒体文件**

:lemon:实现用户注册功能的基本4个步骤:

- 新定义 ``UserForm`` & ``UserProfileForm``(用于html显示&存储&接收表单数据,省去view中冗余code)

- 定义新view，处理创建新用户的过程

- 创建模板，显示``UserForm`` & ``UserProfileForm``

- 映射URL&view

- <font color='red'>记得为index(根目录)页添加去resiter页面的链接</font>

---

### 定义UserForm & UserProfileForm表单

在``rango/forms.py``中定义两个新表单类, 继承``forms.ModelForm``。**定义这两个ModelForm子类创建的HTML表单，用于显示&接收相应模型的字段，减少了很多代码量**

```python
#导入模型类(定义了额外属性的新User模型)，UserProfileModel，与User模型建立1-1关系
from rango.models import UserProfile
from django.contrib.auth.models import User

class UserForm(forms.ModelForm):
    #PasswordInput()渲染HTML表单时，使password字段不可见
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ('username','email','password',)

class UserProfileForm(forms.ModelForm):
    class Meta:
        model = UserProfile
        fields = ('website','picture',)
```

:lemon: **Meta类**用于为所在类提供额外属性, 必须有``model``字段。如``UserForm``类对应``User``类。``fields``和``exclude``字段指定要在html表单中**显示或排除的字段**
:lemon:**只显示User模型的``username,email,password``字段 & UserProfile模型的``website,picture``字段**，UserProfile的user字段在注册用户时设定，因为刚创建时，还没有User实例.

---

### 创建register()视图

处理表单数据&渲染表单，在 views.py 中添加import语句&新 view

```python
from rango.forms import UserForm, UserProfileForm

def register(request):
    #a boolean value for telling the template
    # whether the registration is successful
    # initially  false, when succeeds true.
    registered = False

    # POST, handle the form data
    if request.method == "POST":
        # try to grab information from the raw form information
        # note that, we use both UserForm & UserProFileForm(additional attributes)
        user_form = UserForm(request.POST)
        profile_form = UserProfileForm(request.POST)

        # check whether the form data is valid
        if user_form.is_valid() and profile_form.is_valid():
            # save the UserForm data into database
            user = user_form.save()

            # Note that using the hasher to set password
            # And update the user instance
            user.set_password(user.password)
            user.save()

            # handle the UserProfile instance
            # we need to set the user attribute ourselves(self-defined)
            #we set commit = False, to <delay> the save(iniitially no user instance)
            profile = profile_form.save(commit=False)
            profile.user = User

            # if user provide the icon
            # get it from the input form then put it into UserProfile mdoel
            if 'picture' in request.FILES:
                profile.picture = request.FILES['picture']
            
            #save the UserProfile(Form to database) model instance
            profile.save()

            # tells the teplates, registration finished
            registered = True
        else:
            # invalid forms data
            # print problems to the terminal
            print(user_form.errors, profile_form.errors)
    else:
        # not HTTP POST(maybe get HttpRequest)
        # so provide&tender the blank form for user
        user_form = UserForm()
        profile_form = UserProfileForm()

    # Render the template
    return render(request,'rango/register.html',context={'user_form':user_form,
    'profile_form':profile_form,'registered':registered})
```

:blue_heart:注意，UseProfileForm表单的``user``属性必须引用User模型实例，**不能让用户自行填写**

![](/static/2020-02-14-09-34-56.png)
:green_heart:**并且要延迟commit，因为UserProfile模型中定义了与User模型的外键关联**，如果不延迟，不保证模型之间的关联，会造成``referential integrity error``

---

### 创建注册页模板

file ``rango/register.html``中

```html
{% extends 'rango/base.html' %}
{% load staticfiles %}

{% block title_block %}
    Register
{% endblock %}

{% block body_block %}
<h1>Register for Rango</h1>    

{% if registered %}
Rango says: <strong>thank you for registering</strong>
<a href="{% url 'rango:index' %}">Return to the homepage.</a><br />
{% else %}
Rango says: <strong>register here!</strong><br />
<form action="{% url 'rango:register' %}" method="post" id="user_form" enctype="multipart/form-data">
    {% csrf_token %}
    {{ user_form.as_p}}
    {{ profile_form.as_p}}
    <input type="submit" name="submit" value="Register" />
</form>
{% endif %}
{% endblock %}
```

:lemon:html标签中新引入的属性相关↓

- 使用url模板标签，用于reverse映射url
  
- ![](/static/2020-02-14-09-48-39.png)调用``as_p``模板函数，**目的是在段落中显示各个表单元素，一行显示一个表单元素**

- :blue_heart:``enctype``属性, 如果用户上传头像, 表单数据包含二进制数据,可能过大,因此传给服务器需要分块这些数据. 设定``enctype="multipart/form-data"``
  - <font color="red">表明让HTTP客户端(浏览器)分段打包&发送数据，不然服务器可能收不到用户提交的完整数据(数据过大情况)</font>

:speech_balloon:分段报文multipart messages&二进制文件 binary files

- 注意form标签的``enctype``属性, 如果让用户通过表单上传文件，必须把该属性设置成``enctype="multipart/form-data"``，**这样浏览器(客户端)会以特殊方式post表单数据, 分块发送**

- ``CSRF令牌``-``{% csrf_token %}``**记得添加至表单内，否则Django的跨站请求伪造保护中间件``cross-site forgery protection middleware layer``将拒绝接收表单内容，返回错误**，该令牌提供了安全性，防止钓鱼网站的表单提交``spoof website, phishing``

---

### 添加url映射

![](/static/2020-02-14-10-16-29.png)

---

## :bento: 9.7 登录功能实现

:lemon:用户能注册后，需要实现登录,基本流程:

- 创建login视图

- 创建login模板

- 映射url

- 在index主页添加login链接

```python
from django.contrib.auth import authenticate, login
def user_login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')

        # check whether the combination is valid
        user = authenticate(username=username, password=password)

        if user:
            if user.is_active:
                login(request,user)
                return redirect(reverse('rango:index'))
            else:
                # inactive account was used
                return HttpResponse("Your Rango account is disabled.")
        else:
            # bad login details ,cannot log the user in
            print(f"Invalid login details: {username}, {password}")
            return HttpResponse("Invalid login details supplied.")
    else:
        #HTTP GET
        return render(request,'rango/login.html')
```

:blue_heart:使用authenticate()函数确认username和password是否有效，并且针对User实例可以调用``active``属性判断是否激活

- 如active，则可以使用login()