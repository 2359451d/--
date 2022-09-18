# DRF框架速查

通过serializer可以直接将合法json对象存进database（省去了反序列化步骤？） & 并且一个serializer可以支持在view中封装fields成serialization对象（json）

* [DRF框架速查](#drf框架速查)
* [基本视图：Views](#基本视图views)
  * [使用APIView](#使用apiview)
  * [使用DRF Generic View：简化代码](#使用drf-generic-view简化代码)
* [改进视图&重构URL](#改进视图重构url)
* [视图集&路由：Introducing Viewsets & Routers](#视图集路由introducing-viewsets--routers)
* [如何选择视图基类？](#如何选择视图基类)
* [访问控制：Access Control](#访问控制access-control)
  * [创建用户-API](#创建用户-api)
  * [建立认证模式：Authentication Scheme](#建立认证模式authentication-scheme)
  * [认证用户Login API](#认证用户login-api)
  * [限制访问：Fine Grained Access Control](#限制访问fine-grained-access-control)

# 基本视图：Views

使用`APIView` & `generics.ListCreateAPIView`

---

## 使用APIView

![](/static/2021-01-26-16-47-28.png)

GET请求/polls/

![](/static/2021-01-26-16-48-31.png)

## 使用DRF Generic View：简化代码

某些视图中重复的冗余代码，改进

* generic views推断序列器/基类响应类型&允许的方法
* **改进后效果相同，但`OPTION`请求包含更多内容**

![](/static/2021-01-26-16-53-14.png)
![](/static/2021-01-26-16-54-47.png)

---

完整code

![](/static/2021-01-26-17-00-14.png)

* `queryset`
* `serializer_class`

:orange: `rest_framework.generic`中提供的类

* `ListCreateAPIView`
  * `GET/POST`
* `RetrieveDestroyAPIView`
  * `GET/DELETE`
* `CreateAPIView`
  * `POST`

# 改进视图&重构URL

改进基本视图（可选择完全自定义视图`APIView`或泛型类视图generic view），正确嵌套视图

原urls设计

![](/static/2021-01-26-17-14-47.png)

重新设计

![](/static/2021-01-26-17-16-06.png)

---

原视图，

![](/static/2021-01-26-17-17-39.png)

改进视图 & urls

![](/static/2021-01-26-17-20-58.png)

* 重点，重写`get_queryset`方法，根据`poll_id`筛选choices
* createvote，传入poll&choice id
  * 继承`APIView`类，而不是泛型类generic view
  * 因为想要完全自定义该视图的行为（不限制于几个请求）
  * <font color="red">将post数据传给序列化器，进行验证其合法性。如果合法直接存入DB（序列器会自动反序列化数据存入DB，`serializer.save()`）</font>

`GET http://localhost:8000/polls/1/choices/`

```json
[
    {
        "id": 1,
        "votes": [],
        "choice_text": "Flask",
        "poll": 1
    },
    {
        "id": 2,
        "votes": [
        ],
        "choice_text": "Django",
        "poll": 1
    }
]
```

`POST http://localhost:8000/polls/1/choices/2/vote/` data `{"voted_by": 1}`

```json
{
    "id": 2,
    "choice": 2,
    "poll": 1,
    "voted_by": 1
}
```

# 视图集&路由：Introducing Viewsets & Routers

之前重构后的urls可行，但是视图有一些重复代码，可以进行改进

已知，`/polls/` & `/polls/<pk>/`的url需要两个视图，但是其序列化器`serializer_class`&初始查询集`queryset`完全一样。

![](/static/2021-01-26-17-34-37.png)

* **可以将它们封装进视图集，使用1条路由进行关联**

![](/static/2021-01-26-17-36-14.png)

# 如何选择视图基类？

已知有4种视图

* 原生Django视图
* `APIView`子类
  * 用于完全自定义针对某模型，某视图的操作（不局限于CRUD，并且可以覆写被筛选出来的查询集`queryset`）
* `generics.*`子类
  * 限制对模型的操作
  * `ListCreateAPIView`
    * `GET/POST`
  * `RetrieveDestroyAPIView`
    * `GET/DELETE`
  * `CreateAPIView`
    * `POST`
* `viewsets.ModelViewSet`视图集
  * 用于允许对某模型的大多或全部CRUD操作

# 访问控制：Access Control

为API添加访问权限& 添加用于创建&认证用户的API

:orange: 没有访问控制的API，任何权限都被允许，任何用户组都可以进行CRUD，想添加以下访问控制

* user必须认证后查看，访问poll（list）
* 只有认证用户能创建poll
* 只有认证用户能创建choice
* 认证用户只能为已存在的poll创建选项
* 认证用户只能删除自己创建的poll
* 只有认证用户能进行投票
  * 能给其他用户创建的poll投票

:orange: 为了实现以上访问控制，需要添加2个API

* 创建用户API，`/users/`
* 验证用户&获取token API， `/login/`

## 创建用户-API

![](/static/2021-01-26-17-57-07.png)

测试

![](/static/2021-01-26-17-57-58.png)

## 建立认证模式：Authentication Scheme

DRF框架支持对**所有视图**的默认认证模式，`DEFAULT_AUTHENTICATION_CLASSES`

![](/static/2021-01-26-18-07-51.png)

* 视图中覆写全局认证模式权限
  * `authentication_classes = ()`
  * `permission_classes = ()`
  * `UserCreate`视图不用验证

![](/static/2021-01-26-18-09-05.png)

* 确保通过`UserCreate`视图创建用户的同时，为该user创建密令
  * `Token.objects.create(user=user)`

## 认证用户Login API

因为我们已经添加`rest_framework.authentication.TokenAuthentication`默认认证类，

* 还需要设置头内容 `Authorization: Token c2a84953f47288ac1943a3f389a6034e395ad940` 用于认证
  * 需要一个login API，用户提交username & pass，**返回一个token用于验证（是否与该user实例token匹配**）

---

:orange:因为login API不需要存储token，只是验证，所以不需要添加序列化器

![](/static/2021-01-26-18-19-06.png)

测试

![](/static/2021-01-26-18-20-27.png)

## 限制访问：Fine Grained Access Control

如果无请求头信息（user token）访问`/polls/`应该返回error status code

* 限制未认证用户的访问
* 如果添加了认证头，`Authorization: Token <your token>`，则可以访问polls API（`/polls/`）

:orange:后续HTTP请求头都包括 `Authorization: Token <your token>`

* 强制认证用户只能为其创建的polls创建选项
* 认证的用户只能删除其创建的polls

![](/static/2021-01-26-19-01-12.png)