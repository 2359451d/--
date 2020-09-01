# Deploying

## Username

格式

`http://<username>.pythonanywhere.com`

## 创建虚拟环境

目录

`./.virtualenvs/<VE_name>/`

指令

`mkvirtualenv -p python3.7 rangoenv`

## 进入&退出VE

指令

`workon <VE_name>`

`deactivate`

## 依赖相关及配置生成

### 查看已有依赖

`pip list`

### 已知必须依赖

`django==2.1.5`

* 需要与app所选django库相同

`pillow==5.4.1`

`django-registration-redux==2.2`

* 注册相关API

`requests`

* 用于添加搜索API

`coverage`

`bcrypt`

* 可选， 密码哈希算法库

### 自动安装依赖 & 生成

生成app依赖文档`requirements.txt`用于安装

* `pip freeze > requirements.txt`

为新虚拟环境安装依赖

* `pip install -r requirements.txt`

检查是否正确安装django依赖

`which django-admin.py`

* `/home/rangodemo2020/.virtualenvs/rangoenv/bin/django-admin.py`

## 基本流程

### 克隆仓库至 pyanywhere

`$ git clone https://github.com/<OWNER>/<REPO_NAME>`

* 推荐可以使用组织repo

🍊 例如： `git clone https://github.com/rangodemo/tango_with_django_project.git`

### 🍊 配置数据库

注意以下在项目目录中进行`~/<project_name>`

1. 初始化数据库后，使用生成脚本`populate_rango.py`

* `python manage.py makemigrations rango`
* `python manage.py migrate`
* `python populate_rango.py`
* `python manage.py createsuperuser`

### 配置pya的nginx服务器

#### 配置VE路径

![](/static/2020-03-28-17-41-51.png)


#### 配置CODE部分

![](/static/2020-03-28-17-44-48.png)

🍊 注意WSGI脚本的配置 & 用户名的替换

* 桥接客户端-服务器映射
* 将每个htpp请求映射web app的子域

```python

# This file contains the WSGI configuration required to serve up your
# web application at http://wad2.pythonanywhere.com/
# It works by setting the variable 'application' to a WSGI handler of some
# description.
#
# The below has been auto-generated for your Django project

import os
import sys

# add your project directory to the sys.path
# change wad2 to your PythonAnywhere username

# Add your project's directory the PYTHONPATH

path = '/home/2359451d/tango_with_django_project/'
if path not in sys.path:
    # sys.path.append('/home/2359451d/mysite/')
    sys.path.append(path)

# IMPORTANTLY GO TO THE PROJECT DIR
os.chdir(path)

# set environment variable to tell django where your settings.py is
# os.environ['DJANGO_SETTINGS_MODULE'] = 'mysite.settings'

# new line
# Tell Django where the settings.py module is located
os.environ.setdefault('DJANGO_SETTINGS_MODULE','tango_with_django_project.settings')


# IMPORT THE DJANGO SETUP - NEW TO 1.7
import django
django.setup()

# serve django via WSGI
import django.core.handlers.wsgi
application = django.core.handlers.wsgi.WSGIHandler()

```

![](/static/2020-03-28-17-50-18.png)

#### 项目设置配置HOST

`ALLOWED_HOSTS = ['rangodemo2020.pythonanywhere.com']`

#### 更新项目设置密钥

![](/static/2020-03-28-18-48-57.png)

#### 关闭项目设置调试模式

`DEBUG = False`

#### 配置静态文件路径

![](/static/2020-03-28-18-36-19.png)

配置admin静态css文件

* `/static/admin/`
* `/home/2359451d/.virtualenvs/rangoenv/lib/python3.7/site-packages/django/ contrib/admin/static/admin`

配置static文件路径

* `/static/`
* `/home/rangodemo2020/tango_with_django_project/static`

