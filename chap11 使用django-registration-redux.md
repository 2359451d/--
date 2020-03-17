

# 使用django-registration-redux

🗨 `form.as_p`作为p标签渲染

api: 为django项目提供登录，注册，身份验证，密码修改，重设等功能

## 11.1 安装&设置

1. `pip install -U django-registration-redux==2.2`

2. 配置 settings.py

`INSTALLED_APPS=['registration']`

```PYTHON
# 设为 True，允许用户注册
REGISTRATION_OPEN = True

# 留一周的激活时间；当然，也可以设为其他值
ACCOUNT_ACTIVATION_DAYS = 7

# 设为 True，注册后自动登录
REGISTRATION_AUTO_LOGIN = True

# 登录后呈现给用户的页面
LOGIN_REDIRECT_URL = '/rango/'

# 未登录以及访问需要验证身份的页面时重定向的页面
LOGIN_URL = '/accounts/login/'

```

3. 配置urls

```python
url(r'^accounts/', include('registration.backends.simple.urls'))
```

> 这里，我们使用的是简单的一步注册
过程，输入用户名、电子邮件地址和密码即可注册，而且成功注册后自动登录
![](/static/2020-03-17-10-02-45.png)

## 11.3 模板

templates目录中新建一个`registration`目录，该api相关模板存放在该路径下

![](/static/2020-03-17-09-36-24.png)
![](/static/2020-03-17-10-23-49.png)
`?next={% url 'rango:index' %}`重定向去index页

* 如果不指定会重定向至logout页

## exercise

使用`django-registration-redux`包，提供用户修改密码功能

base模板添加该link(登陆后可见)