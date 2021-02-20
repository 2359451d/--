# Overview Background

C网络编程

# Concepts

![](/static/2021-02-20-18-35-17.png)

* **两种socket类型** `type`
  * stream - 提供虚拟回路服务
    * TCP
  * datagram -  发送单独包
    * UDP
* 与网络类型无关
  * 常用于TCP/IP  & UDP/UP
    * 但不需要指定互联网协议
* 本课只涉及TCP/IP

# TCP/IP连接

![](/static/2021-02-20-19-58-14.png)

两台计算机之间可靠的字节流连接。
- 最常用的是客户端-服务器的方式。
- **服务器监听一个已知的端口**
  - 端口是一个`16`位的数字，用来区分服务器。
  - 例如：Web服务器监听端口为`80`，电子邮件服务器监听端口为25。
- 客户端连接到该**已知）端口**
- **一旦建立了连接，任何一方都可以将数据写入连接中，在那里它可以供另一方读取。**
- Sockets API使用一个 **file descriptor**代表该连接

![](/static/2021-02-20-20-11-21.png)

* 服务器创建fd后，给fd绑定端口，进行已知端口的监听
* `connfd=accept(fd, ..)`准备建立连接，阻塞方法

# 创建套接字：Create  a socket

创建无限制socket，可用于client，server

unbound socket

```c
#include <sys/types.h>
#include <sys/socket.h>
int fd;
// ...
fd = socket(AF_INET, SOCK_STREAM, 0);//TCP, IPV4
fd = socket(AF_INET6, SOCK_STREAM, 0);//TCP, IPV6
fd = socket(AF_INET, SOCK_DGRAM, 0);//UDP
// 返回-1，设置全局变量errno，表示创建失败
// man指令可列出每个func可能出现的error
// man 2 socket
if (fd==-1){
    // Error: unable to create
}

```

# 绑定server套接字 & 监听

![](/static/2021-02-20-20-24-54.png)

给之前创建的套接字绑定port

* 需要server运行于已知端口
  * `addr`指定`INADDR_ANY`
* 不关心client使用的port
* **`bind(fd, addr, addrlen)`必须指定地址 & port**
  * IPv4/v6
  * 可以在C语言中建模为联合体，但socket API的设计者选择使用一些结构，并滥用强转来代替。

监听

* 让绑定了端口的套接字fd，监听新连接
  * `backlog`，最大队列连接数
  * 每个连接都在等待`accept()`返回

```c
#include <sys/types.h>
#include <sys/socket.h>
int fd;
// ...
fd = socket(AF_INET, SOCK_STREAM, 0);//TCP, IPV4
// 返回-1，设置全局变量errno，表示创建失败
// man指令可列出每个func可能出现的error
// man 2 socket
if (fd==-1){
    // Error: unable to create
}

if (bind(fd, addr, addrlen) ==-1){
    // Error: unable to bind
}

if (listen(fd, backlog) == -1){
    // error
}

```

# 连入server

等待连接建立（连接至服务器）

![](/static/2021-02-20-20-37-36.png)

* `addr` - `struct sockaddr`指针
* `addrlen` - struct字节大小
* 尝试与服务器建立连接
  * 75s后无响应则超时
* **`connect(fd, addr, addrlen)`必须指定地址 & port**
  * IPv4/v6
  * 可以在C语言中建模为联合体，但socket API的设计者选择使用一些结构，并滥用强转来代替。
  * `addr`为`struct sockaddr`指针
    * 字段
      * `char` - `sa_data[22]` 足够大的data字段，保存任何协议的最大地址
      * `uint8_t` - `sa_len` 指定地址长度
      * `sa_family_t` - `sa_family`  & 地址类型
    * 将地址视为不透明的二进制字符串

```c
#include <sys/types.h>
#include <sys/socket.h>
int fd;
// ...
fd = socket(AF_INET, SOCK_STREAM, 0);//TCP, IPV4
// 返回-1，设置全局变量errno，表示创建失败
// man指令可列出每个func可能出现的error
// man 2 socket
if (fd==-1){
    // Error: unable to create
}

if (bind(fd, addr, addrlen) ==-1){
    // Error: unable to bind
}

if (listen(fd, backlog) == -1){
    // error
}

if (connect(fd, addr, addrlen) ==-1){
    // Unable to open connection
}

```

# Struct sockaddr & 变体

![](/static/2021-02-20-20-37-36.png)

* **`connect/bind(fd, addr, addrlen)`必须指定地址 & port**
  * IPv4/v6
  * 可以在C语言中建模为联合体，但socket API的设计者选择使用一些结构，并滥用强转来代替。
  * `addr`为`struct sockaddr`指针
    * 字段
      * `char` - `sa_data[22]` 足够大的data字段，保存任何协议的最大地址
      * `uint8_t` - `sa_len` 指定地址长度
      * `sa_family_t` - `sa_family`  & 地址类型
    * 将地址视为不透明的二进制字符串

---

## sockaddr_in

变体

![](/static/2021-02-20-20-39-32.png)

```c
struct in_addr{
    in_addr_t s_addr;
}

// 用于IPV4地址
//与 struct sockaddr大小&布局相同
//但以不同的方式解释位，使地址具有结构性。

struct sockaddr_in {
    uint8_t sin_len; //地址长度
    sa_family_t sin_family; //地址类型
    in_port_t sin_port; //端口
    struct in_addr sin_addr; //地址
    char sin_pad[16]; //ipv4地址大小，二进制字符串
};

```

## sockaddr_in6

![](/static/2021-02-20-20-44-43.png)

```c
struct in6_addr {
    uint8_t s6_addr[16];
};

// 用于IPV6地址
//与 struct sockaddr大小&布局相同
//但以不同的方式解释位，使地址具有结构性。

struct sockaddr_in6 {
    uint8_t sin6_len; //地址长度
    sa_family_t sin6_family; //地址类型
    in_port_t sin6_port; //端口
    uint32_t sin6_flowinfo; //？
    struct in6_addr sin6_addr; //地址
};
```

# 绑定地址 & 创建地址：INADDR_ANY & bind

选取struct

* `sockaddr_in`
* `sockaddr_in6`
* 使用套接字流程之前强转成`sockaddr`

---

创建，填写地址

* 创建地址结构 `sockaddr_in/sockaddr_in6`
* **server通常监听默认地址**，使用`INADDR_ANY`传入`bind()`
* 使用`htons`转换端口号
* `addr(sockaddr_in).sin_addr`的**地址填入，使用DNS获取数字IP**
  * `getaddrinfo()`查询DNS域名
    * 返回`struct addrinfo`的链表结构体，代表主机地址

```c
struct addrinfo {
    int ai_flags; // input flags
    int ai_family; // AF_INET, AF_INET6, ...
    int ai_socktype; // IPPROTO_TCP, IPPROTO_UDP
    int ai_protocol; // SOCK_STREAM, SOCK_DRAM, ...
    socklen_t ai_addrlen; // length of socket-address
    struct sockaddr *ai_addr; // socket-address for socket
    char *ai_canonname; // canonical name of host
    struct addrinfo *ai_next; // pointer to next in list
};

```

---

```c
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int fd;

// ...
fd = socket(AF_INET, SOCK_STREAM, 0);//TCP, IPV4
// 返回-1，设置全局变量errno，表示创建失败
// man指令可列出每个func可能出现的error
// man 2 socket
if (fd==-1){
    // Error: unable to create
}

struct sockaddr_in addr;
addr.sin_addr.s_addr = INADDR_ANY; //server监听默认地址，传入bind
addr.sin_family = AF_INET; // 地址类型，ipv4
addr.sin_port = htons(80); // 转换端口号

// 强转
if (bind(fd, (struct sockaddr *) &addr, sizeof(addr)) ==-1){
    // Error: unable to bind
}

if (listen(fd, backlog) == -1){
    // error
}

// client连入server

// struct sockaddr_in addr;
// inet_pton(AF_INET, “130.209.240.1”, &addr.sin_addr); 不推荐直接硬编码IP
// addr.sin_family = AF_INET;
// addr.sin_port = htons(80);

// 使用DNS查询获取数字IP填写地址，
struct addrinfo hints, *ai, *ai0;
int i;
memset(&hints, 0, sizeof(hints));
hints.ai_family = PF_UNSPEC;
hints.ai_socktype = SOCK_STREAM;
// 根据域名查询IP
if ((i = getaddrinfo(“www.google.com”, "80", &hints, &ai0)) != 0) {
    printf("Unable to look up IP address: %s", gai_strerror(i));
 ...
}

for (ai = ai0; ai != NULL; ai = ai->ai_next) {
    //创建client-side socket
    //填写fd信息， 套接字类型，协议，网络类型
    fd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
    
    if (fd == -1) {
    perror("Unable to create socket");
    continue;
    }

    // client尝试通过fd连入IP，    
    if (connect(fd, ai->ai_addr, ai->ai_addrlen) == -1) {
        perror("Unable to connect");
        close(fd);
        continue;
    }

    // 成功建立连接success, use the connection
    break;
}
//连接失败
if (ai == NULL) {
 // Connection failed, handle the failure...
}

// if (connect(fd, (struct sockaddr *)&addr, sizeof(addr)) ==-1){
    // Unable to open connection
// }

```

## 客户端手动连入地址：不推荐

客户端想连入特定地址，使用`inet_pton()`创建地址，如果知道IP地址

* 不推荐，应该使用DNS

```c
struct sockaddr_in addr;
...
//硬编码
inet_pton(AF_INET, “130.209.240.1”, &addr.sin_addr); // 填入addr.sin_addr
addr.sin_family = AF_INET;
addr.sin_port = htons(80);
if (connect(fd, (struct sockaddr *)&addr, sizeof(addr)) == -1) {
```

# 服务器接收连接

![](/static/2021-02-20-21-39-44.png)

接受新连接，给该连接返回新fd & 客户端地址

* 返回`connfd`
* 返回`cliaddr`

TCP/IPserver可以有多个连接

* `accept()`可以串行处理队列中每个连接请求
* `accept()`可以并行处理
* 每次`accept()`


```c
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int fd;

// ...
fd = socket(AF_INET, SOCK_STREAM, 0);//TCP, IPV4
// 返回-1，设置全局变量errno，表示创建失败
// man指令可列出每个func可能出现的error
// man 2 socket
if (fd==-1){
    // Error: unable to create
}

struct sockaddr_in addr;
addr.sin_addr.s_addr = INADDR_ANY; //server监听默认地址，传入bind
addr.sin_family = AF_INET; // 地址类型，ipv4
addr.sin_port = htons(80); // 转换端口号

// 强转
if (bind(fd, (struct sockaddr *) &addr, sizeof(addr)) ==-1){
    // Error: unable to bind
}

//服务器监听port
if (listen(fd, backlog) == -1){
    // error
}

// server accepts
// 成功接受一个新连接，返回connfd & cliaddr
int connfd;
struct sockaddr_in cliaddr;
socklen_t cliaddrlen = sizeof(cliaddr);s

//serverfd 等待新连接，连接建立后返回的client地址信息传入cliaddr
connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen);
if (connfd==-1){
    //error
}


//-----------------------

// client连入server

// 使用DNS查询获取数字IP填写地址，
struct addrinfo hints, *ai, *ai0;
int i;
memset(&hints, 0, sizeof(hints));
hints.ai_family = PF_UNSPEC;
hints.ai_socktype = SOCK_STREAM;
// 根据域名查询IP
if ((i = getaddrinfo(“www.google.com”, "80", &hints, &ai0)) != 0) {
    printf("Unable to look up IP address: %s", gai_strerror(i));
 ...
}

for (ai = ai0; ai != NULL; ai = ai->ai_next) {
    
    //创建client-side socket
    //填写fd信息， 套接字类型，协议，网络类型
    fd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
    
    if (fd == -1) {
    perror("Unable to create socket");
    continue;
    }

    // client尝试通过fd连入IP，    
    if (connect(fd, ai->ai_addr, ai->ai_addrlen) == -1) {
        perror("Unable to connect");
        close(fd);
        continue;
    }

    // 成功建立连接success, use the connection
    break;
}
//连接失败
if (ai == NULL) {
 // Connection failed, handle the failure...
}

```

# 读写数据

读

![](/static/2021-02-20-21-48-27.png)

```c
// 指定缓冲大小
#define BUFLEN 1500
...
ssize_t i;
ssize_t rcount;
char buf[BUFLEN];
...
//通过fd套接字，每次读1500字节至buf数组
//除非有数据流入，否则read阻塞
rcount = read(fd, buf, BUFLEN);
//最后count=-1，则读入步骤出错，否则rcount=实际读入数字
if (rcount == -1) {
 // Error has occurred
 ...
}

// 打印读入的数据
for (i = 0; i < rcount; i++) {
 printf(“%c”, buf[i]);
}
```

写

![](/static/2021-02-20-21-54-34.png)

* 向套接字（连接）写入数据
* 阻塞方法，直到数据写入
* 返回实际写入字节or-1

```c
char data[] = “Hello, world!”;
int datalen = strlen(data);
...
if (write(fd, data, datalen) == -1) {
 // Error has occurred
 ...
}

```

# 处理多套接字

`select()`

* 返回多套接字中有数据流入的**套接字**

![](/static/2021-02-20-21-51-42.png)

```c
#include <sys/select.h>
...
int fd1, fd2;
fd_set rfds;
struct timeval timeout;

timeout.tv_sec = 1; // 1 second timeout
timeout.tv_usec = 0;
FD_ZERO(&rfds);
FD_SET(fd1, &rfds);
FD_SET(fd2, &rfds);

//返回有数据流入的套接字
int rc = select(max(fd1, fd2) + 1, &rfds, NULL, NULL, &timeout);

if (rc == 0) ... // timeout
if (rc > 0) {
    if (FD_ISSET(fd1, &rfds)) {
    ... // Data available to read on fd1
    }
    if (FD_ISSET(fd2, &rfds)) {
    ... // Data available to read on fd2
    }
}
if (rc < 0) ... // error
```

# 关闭连接（套接字）

```c
#include <unistd.h>
close(fd);
```

关闭&删除套接字（fd）

* 为每个连接关闭fd，再是关闭底层套接字fd

