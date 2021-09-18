# Research website for uploading materials

Content:

* [Research website for uploading materials](#research-website-for-uploading-materials)
  * [How to authenticate uploaders](#how-to-authenticate-uploaders)
    * [Moodle API](#moodle-api)
  * [The Features a server should have](#the-features-a-server-should-have)
  * [Possible Implementation: Existing Service](#possible-implementation-existing-service)
    * [FastDFS: Distributive File System](#fastdfs-distributive-file-system)
    * [File Browser](#file-browser)
    * [Django Based Projects](#django-based-projects)
  * [General Ideas](#general-ideas)
    * [How the Resource Server Works](#how-the-resource-server-works)
    * [Handle the GET Request to get resources](#handle-the-get-request-to-get-resources)

> Requirements: Research how to **create a website that will authenticate teachers** and **allow the upload of materials** when our main product is a mobile app

how to implement the resource server should depend on frameworks chosen(focus is on how to implement the upload function)

## How to authenticate uploaders

As mentioned in project requirements, the access rights could only be given to teachers by admins. The **simplest way** to implement it

* disable the any sign-up funtion but the sign-in function should be retained
* **username-password pairs for admin group** should always be stored in the database
* The teacher asks the admin for access rights (leave an email or username), then admin processes the access in the backends. If the request is approved, then the teacher is assgined a temporary password by email which could be changed later
  * Request could be processed automatically by storing an e-mail list for eligible access to the server, or by other means
* Or the admin directly adds/modifies some access accounts in server to manually control user access rights. Then privately inform the teacher to use it
  * Alternatively, admin *only mannually adds prefered username/email list to the server, but use moodle to verify whether the username-password matches (might be tricky)*

### Moodle API

It seems that there is no directly way to integrate the moodle login authentication API into a **non-PHP web app**. [Authentication plugins](https://docs.moodle.org/29/en/Authentication)

But could consider the web services to communicate with moodle. [Moodle Web Service](https://docs.moodle.org/dev/Web_service_API_functions)

## The Features a server should have

How to implement the uploading function below depends on which frameworks are chosen (if completely self-designed one)

1. **Authentication**
   * Check the user access rights, whether user who tries to login is staff in school of medicine or admin
2. **Uploading Materials**
   * teachers group could upload/modify different mateirals in specific modules
   * HTTPRequest handller function: resources data should be formatted in **JSON** as the response body used later. Should include:
     * file info
     * uploader info
     * .etc

服务器应有功能：

1. 验证authentication api，验证为moodle staff（医学院staff，能够有上传资源的rrights）
2. 能分模块上传文件，文件分扩展名，
3. 上传者，上传时间
4. 能够被爬取数据，数据转为json，xml格式进行解析后传至app【支持在线/离线】

## Possible Implementation: Existing Service

There are some possible ways to implement the uploading function, if choose to use an existing service (need to make time to study).

### FastDFS: Distributive File System

If access volume doesn’t put too much pressure on the server, fastdfs is not worth too much attention (complex)

[FastDFS](https://github.com/happyfish100/fastdfs]) & Nginx

> FastDFS is an open source high performance distributed file system. It's major functions include: file storing, file syncing and file accessing (file uploading and file downloading), and it can resolve the high capacity and load balancing problem. FastDFS should meet the requirement of the website whose service based on files such as photo sharing site and video sharing site.

FastDFS is not a common file system, accessible only through dedicated API, currently available with C & Java SDK, as well as PHP extension SDK.

It can be regarded as a file-based key-value storage system where key is the file ID, and value is the file content.

不是通用的文件系统，只能通过专有 API 访问，目前提供了 C 和 Java SDK ，以及 PHP 扩展 SDK.
解决大容量文件存储问题，追求高性能和高扩展性，它可以看做是基于文件的 key/value 存储系统，key 为文件 ID，value 为文件内容.

possible projects

* backend
  * [fastdfs-java-client](https://github.com/happyfish100/fastdfs-client-java)
  * springboot/mvc
* frontend
  * Vue.js
* related tutorials
  * [FastDFS Tutotial: Using Java code to manipulate files](https://programmer.help/blogs/fastdfs-tutorial-using-java-code-to-manipulate-files.html)
  * [SpringBoot integration FastDFS+Nginx integration Token-based anti-theft chain](https://programmer.group/springboot-integration-fastdfs-nginx-integration-token-based-anti-theft-chain.html)

### File Browser

Login & user control functions are implemented with a neat UI that can be deployed on linux using docker

[File Browser](https://github.com/filebrowser/filebrowser) & docker & (nginx)

> filebrowser provides a file managing interface within a specified directory and it can be used to upload, delete, preview, rename and edit your files. It allows the creation of multiple users and each user can have its own directory. **It can be used as a standalone app or as a middleware.**
>
> The project is mainly composed by one repository, hosted on GitHub. The backend side of the application is written in Go, while the frontend (located on a subdirectory of the same name) is written in Vue.js, a framework to produce JavaScript, CSS and HTML.
>
> Due to the tight coupling required by some features, basic knowledge of both Go and Vue.js is recommended.

[Learn Go](https://github.com/golang/go/wiki/Learn)

[Learn Vue.js](https://vuejs.org/v2/guide/index.html)

### Django Based Projects

Projects below based on django frameworks, giving some ideas about file server

[WF_WebBasedFileBrowser](https://github.com/DaiQiangReal/WF_WebBasedFileBrowser)

> A user-friendly install easily WebBased File Browser programed with Python Django with Material Design Interface.

[Django-WebApp](https://github.com/smahesh29/Django-WebApp)

> This is a web-app created using Python, Django. By using this user can login, upload files and also can view and download files uploaded by other users.

## General Ideas

### How the Resource Server Works

1. User uploads materials, storing to somewhere in server. And the database stores the stored path
2. Client requests the materials and backend sends back the access links or formatted data

3. 用户上传文件，前端上传的文件获取后放入服务器的某个文件夹中
4. 如果后续需要获取用户上传的资源，需要向后端请求资源（仅能返回工程下的资源）
   1. 情况：如果前后端分离，需要考虑访问工程域外资源

<font color="red">特殊情况解决方案</font>

* 用户上传完资源后，后端根据资源存放路径生成访问链接存入数据库
* 请求资源时，后端将访问链接返回给请求方
  * 请求方根据链接获取资源

### Handle the GET Request to get resources

1. create a HttpGet Request from Xmarin
   1. *`HttpClient.GetAsync`method is used to send the GET request to the web service specified by the URI, and then receive the response from the web service*
2. web server backend handles the Get Request and returns the json data(if succeeds) back to Xmarin
   1. json data(in `HttpResponseMessage.Content`) could look like `[{"resource_link": "...", "uploader": "...", "time":"", "module":"", "file_type":"", "file_name":""}, {...}]`