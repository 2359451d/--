# cookie & 会话

> 会话和 cookie 是互不可分的两个概念，登录和退出功能就是基于会话和 cookie 实现的。(具体过程被 Django 框架隐藏)

## 10.1 cookie 无处不在

🍬 本章介绍**客户端cookie & 服务器端会话存储**

> 服务器收到请求后会响应请求页面内容,以及cookie

🍊 准备发送请求时,客户端检查有无与服务器地址匹配的cookie,如果有,一起发送.

* 服务器收到请求后,将`cookie`在上下文中解释,生成合适响应

* 会话非永久,cookie会在某一时刻过期

---

🍬 例如,登录网站.

* 通过身份验证后,服务器把包含用户名的`cookie`发送给服务器,**表明用户已登陆网站**
  * 从而渲染已登录用户页面

---

🗨 cookie非正确设置会造成web漏洞

* 思考:是否需要将cookie存储在客户端
* 如电商网站通过cookie 传送用户的信用卡卡号就极不安全。试想，如果用户的电脑被攻击,恶意程序可能会攫取 cookie

## 10.2 会话和无状态协议

> HTTP 是无状态的协议。客户端每次请求服务器中的资源都要建立新的网络连接（TCP 连接)

🗨 **HTTP 1.1** 其实**支持在一个 TCP 网络连接中发送多个请求**这样能极大地提升性能

🍊 **维持会话(连接)状态**最常用的一种方式是<font color="red">在客户端cookie 中存储会话 ID</font>.唯一标识web中对话

* 通过该会话id能访问所需信息,更安全.
* 通过会话id可以获取**服务器端存储的所有会话信息**

![](/static/2020-03-16-06-53-57.png)

## 10.3 在DJANGO中设置会话

django提供会话api,**由中间件实现**.

🍊 (基础)中间件配置

settings.py 中`MIDDLEWARE=[django.contrib.sessions]`,`INSTALLED_APPS=[django.contrib.sessions]`

* 会将会话信息存储在模型中`django.contrib.sessions.models.Session`

## 10.4 测试是否支持cookie

测试,**此步基本多余**,大多数浏览器都支持cookie(某些安全级别下可能会禁用)

![](/static/2020-03-16-07-04-27.png)

* `set_test_cookie()`
* `test_cookie_worked()`
* `delete_test_cookie()`

![](/static/2020-03-16-07-06-30.png)

## 10.5 客户端存储cookie应用: 访问次数统计

🍊 创建两个cookie

* 记录访问rango应用的次数
* 用户最后一次访问时间(用于防止多次恶意刷新访问)

🍬 定义一个函数,传入`request` & `response`

* 辅助函数,不返回任何`response`对象

```python
def visitor_cookie_handler(request, response):
    # 获取网站的访问次数
    # 使用 COOKIES.get() 函数读取“visits”cookie
    # 如果目标 cookie 不存在，返回默认值 1
    visits = int(request.COOKIES.get('visits', '1'))
    last_visit_cookie = request.COOKIES.get('last_visit', str(datetime.now()))
    last_visit_time = datetime.strptime(last_visit_cookie[:-7],
    '%Y-%m-%d %H:%M:%S')

    # 如果距上次访问已超过一天……
    # 计算两次访问间隔是否超过一天
    if (datetime.now() - last_visit_time).days > 0:
    visits = visits + 1
    # 增加访问次数后更新“last_visit”cookie
    response.set_cookie('last_visit', str(datetime.now()))
    else:
    # 设定“last_visit”cookie
    response.set_cookie('last_visit', last_visit_cookie)
    # 更新或设定“visits”cookie
    response.set_cookie('visits', visits)
```

```python
def index(request):
    category_list = Category.objects.order_by('-likes')[:5]
    page_list = Page.objects.order_by('-views')[:5]
    context_dict = {'categories': category_list, 'pages': page_list}
    # 提前获取 response 对象，以便添加 cookie
    response = render(request, 'rango/index.html', context_dict)
    # 调用处理 cookie 的辅助函数
    visitor_cookie_handler(request, response)
    # 返回 response 对象，更新目标 cookie
    return response
```

从入站请求中读取cookie,添加到响应中.

### request.COOKIES.get()

django提供的辅助函数

* 如果指定的cookie存在,返回cookie值.否则,返回默认值.
* cookie默认字符串类型

### response.set_cookie()

如果cookie不存在,可以创建.

* 参数1: 想创建的cookie名称(字符串形式)
* 参数2: cookie值(类型不限,自动转为string)

## 10.6 服务器存储cookie:会话数据

🍬 为了会话信息安全,最好存储在服务器端.

* <font color='red'>通过存储在客户端的会话id访问服务器中会话数据</font>

🍊 保存在后台数据中的一个键值对,是cookie的升级

### 🍊 原理

登陆成功后,会向后台数据库与前端cookie同时发放一段`随机字符串`

* 数据保存在后台服务器session中
* **session id(cookie键)** 保存在客户端中用于拿去后台进行验证匹配

![](/static/2020-03-16-10-16-12.png)

![](/static/2020-03-16-12-17-22.png)

* 第一次，请求头中cookie无会话id，服务器接收后生成id，并返回给客户端进行存储，同时存储一份`sessionid:{k-v}`在服务器中。
* 第二次获取，请求头中cookie带着会话id，服务器获取后，根据session_id从session字典中取出数据

### 🍊 配置

1. settings.py 模块中`MIDDLEWARE_CLASSES=[django.contrib.sessions.middleware.SessionMiddleware]`
2. settings.py `INSTALLED_APPS=[django.contrib.sessions]` 必要时进行数据库迁移更新

🍬 注意,此时**数据cookie**不直接存储在客户端请求中

* 读取cookie: `request.session.get()`
* 存储cookie: `request.session[]`

🗨 必要时,为了识别客户端,**需要存储会话id(cookie)于客户端中**

### 辅助函数

从请求中读取cookie,如果服务器会话数据中有指定cookie,返回值.否则默认

🍬 因为所有cookie数据存储在服务器端(除了会话id),可以不用直接修改响应.辅助函数可以不需要response

```python
# 辅助函数
def get_server_side_cookie(request, cookie, default_val=None):
    val = request.session.get(cookies)
    if not val:
    val = default_val
    return val

# 更新后的函数定义
def visitor_cookie_handler(request):
    visits = int(get_server_side_cookie(request, 'visits', '1'))
    last_visit_cookie = get_server_side_cookie(request,
    'last_visit',
    str(datetime.now()))
    last_visit_time = datetime.strptime(last_visit_cookie[:-7],
    '%Y-%m-%d %H:%M:%S')

    # 如果距上次访问已超过一天……
    if (datetime.now() - last_visit_time).days > 0:
    visits = visits + 1
    # 增加访问次数后更新“last_visit”cookie
    request.session['last_visit'] = str(datetime.now())
    else:
    # 设定“last_visit”cookie
    request.session['last_visit'] = last_visit_cookie
    # 更新或设定“visits”cookie
    request.session['visits'] = visits
```

### 视图

```python
def index(request):
    request.session.set_test_cookie()
    category_list = Category.objects.order_by('-likes')[:5]
    page_list = Page.objects.order_by('-views')[:5]
    context_dict = {'categories': category_list, 'pages': page_list}
    visitor_cookie_handler(request)
    context_dict['visits'] = request.session['visits']
    response = render(request, 'rango/index.html', context=context_dict)
    return response

```

## 10.7会话期限

🍊 会话期限分为两种

* 续期会话: 浏览器关闭后过期
* 持久会话：指定时间

🍬 默认情况下，续期会话禁用。需要配置 settings.py 文件

* `SESSION_EXPIRE_AT_BROWSER_CLOSE=True`

🍬 默认启用持久会话

![](/static/2020-03-16-14-48-40.png)

## 10.8 清理会话数据库

session存储于数据库，需要定期清理。

`python manage.py clearsessions`

## 10.9 注意事项&基本流程

* 使用哪种cookie，使用期限

🍊 如使用客户端cookie

* 检查cookie是否存在，`request.COOKIES.has_key('<cookie_name>')`，判断某cookie是否存在客户端浏览器中
* 如果目标cookie存在，可以获取值`request.COOKIES['key']`，某cookie是否存在于客户端电脑中<font color='red'>注意保存的是string格式</font>
* 如果目标cookie不存在(response中)，或想更新cookie，`response.set_cookie('<cookie_name>',value)`

🍊 如使用服务端session

* 配置 settings.py 模块中的`MIDDLEWARE_CLASSES=[django.contrib.sessions.middleware.SessionMiddleware]`
* 使用`SESSION_ENGINE`配置会话后端
* `requests.session.get()`检查cookie是否存在
* 更新cookie，`requests.session['<cookie_name>']`

