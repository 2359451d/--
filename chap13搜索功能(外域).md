# 13. 添加搜索功能

前略: 装request包 `pip install requests`

本章学习引入搜索api(响应格式json)，确保用户可以进行搜索特定页面?

🍬 使用bing搜索

* 需要写一个wrapper(适配器，)，用于发送给api查询请求，并接受响应

## 13.1 bing api

响应数据可以`XML/JSON`格式返回.

1. 注册api key密钥.

* 注册微软azure账号
* ![](/static/2020-03-18-08-38-45.png)

2. 取密钥1

* ![](/static/2020-03-18-08-40-35.png)

## 13.2 添加搜索功能(取到密钥后)

1. rango目录下新建`bing_search.py`文件
2. 密钥1放入`bing.key`文件，**忽略文件中存入该文件名（避免上传至github）**

![](/static/2020-03-18-08-50-24.png)

```python
import json
import requests

# add key1 to file bing.key
# acquire the key
def read_bing_key():
   
    bing_api_key = None
   
    try:
        with open('bing.key','r') as f:
            bing_api_key = f.readline().strip()
    except:
        try:
            with open('../bing.key','r') as f:
                bing_api_key = f.readline().strip()
        except:
            raise IOError('bing.key file not found')

    if not bing_api_key:
        raise KeyError('Bing key not found')
    print(bing_api_key)
    return bing_api_key

def run_query(search_terms):
    # search_terms -> string
    # default: return top 10 results
    bing_key = read_bing_key()
    search_url = "https://api.cognitive.microsoft.com/bing/v7.0/search"
    search_term = "Azure Cognitive Services"
    headers = {'Ocp-Apim-Subscription-Key': bing_key}
    params = {"q": search_term, "textDecorations": True, "textFormat": "HTML"}
    # # Issue the request, given the details above.
    response = requests.get(search_url, headers=headers, params=params)
    response.raise_for_status()
    # return the response as json format
    search_results = response.json()

    # parse the json content

    results = []
    for result in search_results['webPages']['value']:
        results.append({
            'title': result['name'],
            'link': result['url'],
            'summary':result['snippet']})
    return results

# run the script
def main():
    search_terms = input("Please enter the query content:")
    results = run_query(search_terms)
    for result in results:
        print("Title: {0}, url: {1}, content: {2}".format(result['title'],result['link'],result['summary']))
        print("-------------------->")

if __name__=='__main__':
    main()
```

## 13.3 处理其他多种参数

[参考](https://docs.microsoft.com/zh-hk/rest/api/cognitiveservices-bingsearch/bing-web-api-v7-reference)

## 13.4 部署搜索功能进web app

两大步骤:

* 创建`search.html`模板，继承base
  * 提供一个表单用于接收用户查询输入
* 创建处理渲染搜索结果的视图
  * 该视图调用`run_query()`函数脚本，返回搜索结果

### 添加搜索模板

![](/static/2020-03-18-12-04-19.png)

* 一个表单处理，返回post用户查询输入
* 另一个部分用于刷新返回查询结果，视图列表`result_list`，
* 使用bootstrap列表布局[`list-groups`](https://getbootstrap.com/docs/4.2/components/list-group/)
  * 标题`list-group-item-heading`
  * entry `list-group-item-text`
  * 容器盒子`list-group`
* [表单样式](https://getbootstrap.com/docs/4.2/components/forms/)
* [按钮样式](https://getbootstrap.com/docs/4.2/components/jumbotron/)

🍬 注意，使用了django内置模板标签
* `safe` & `escape`
  * 因为查询结果json格式某些自带html标签

### 添加视图

两个搜索引擎切换，下拉框
![](/static/2020-03-18-12-45-39.png)

* 调整占位符，显示上一次查询关键字

**未引入ajax处理不刷新页面，下拉框默认选中值**

## 扩展: 谷歌自定义搜索api

原理同样，返回json格式

![](/static/2020-03-18-11-08-28.png)

先获取key & 引擎id

### 安装依赖

`pip install google-api-python-client`

### 脚本

参考twd中bing搜索脚本，读取两个密钥，获取json格式搜索结果
![](/static/2020-03-18-11-30-04.png)

```python
from googleapiclient.discovery import build
import pprint

# add key1 to file bing.key
# acquire the key
def read_google_key():
   
    google_api_key = None
   
    try:
        with open('google.key','r') as f:
            list1 = f.readlines()
            google_api_key = list1[0].strip()
            engine_api_key = list1[1].strip()
    except:
        try:
            with open('../google.key','r') as f:
                google_api_key = list1[0].strip()
                engine_api_key = list1[1].strip()
        except:
            raise IOError('google.key file not found')

    if not google_api_key:
        raise KeyError('Google key not found')

    key_list = [google_api_key,engine_api_key]
    return key_list


#define key
api_key = read_google_key()[0]
cse_key = read_google_key()[1]

# resource object: send the request
# first para: service- > customsearch
# 2 para: version
# 3 para: api_key

# call cse() to create custom search resource object
resource = build("customsearch", 'v1', developerKey=api_key).cse()

# return the list of search results
# q -> query string
# cx - > engine id
# call execute() will atcually executing the request

result = resource.list(q='simplified python', cx=cse_key).execute()

# pick the title and link
for item in result['items']:
    print(item['title'], item['link'], item['snippet'])


print("-----------------------{0} results are matched.-----------------------".format(len(result)))
# pretty print all the result tags(JSON FOMATTED)
# pprint.pprint(result)
```