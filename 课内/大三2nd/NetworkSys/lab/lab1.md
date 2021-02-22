# Lab1 - Networked “Hello, World” Application

`hello_server`

* 监听TCP port `5000`
* 接受第一个连接，读取所有data
* 打印data
* 关闭连接
* 退出
* **完成client所有操作后，修改server一次性处理多连接**

`hello_client`

* 连入已知port `5000`
* 发送 `Hello, world!`
  * 成功后，尝试发送更长消息（>1500 char）
  * **长消息成功后，尝试修改，利用server发送响应后client再打印server响应**
* 关闭连接
* 接收命令行参数 `hello_client server_hostname`
  * 利用dns查应连入的server ip

编写Makefile

* `clang -W -Wall -Werror`

The standard
library function `perror()` can be used to print an appropriate error message, based on the value of errno.

* 基于`errno`全局变量，可用于打印错误信息

# struct sockaddr

`sa_len`

* IP地址大小

`sa_family`

* IP地址类型

`sa_data`

* 足够大的字段存储ip地址

# struct sockaddr_in

<sys/socket.h> and <netinet/in.h> headers

```c
struct sockaddr_in {
  uint8_t sin_len; //ip地址大小
  sa_family_t sin_family; //ip地址类型
  in_port_t sin_port;
  struct in_addr sin_addr;
  char sin_pad[16]; //存储ip
}
struct in_addr {
   in_addr_t s_addr;
}
```

# struct sockaddr_in6

<sys/socket.h> and <netinet/in.h> headers

```c
struct sockaddr_in6 {
    uint8_t sin6_len;
    sa_family_t sin6_family;
    in_port_t sin6_port;
    uint32_T sin6_flowinfo;
    struct in6_addr sin6_addr;
}
struct in6_addr {
    uint8_t s6_addr[16];
}

```

# 难点：DNS

client `connect(fd, (struct sockaddr *) &addr, sizeof(addr))`

* client套接字需要连入server ip&port， 但通常只知道server 域名而非ip
* **利用`getaddrinfo()`方法**
  * servername 服务器域名
  * port 已知端口
  * type of address（hints） 链表存储所有可能的serverip信息（`struct addrinfo`链表）
    * 字段
      * `hints.ai_family`
      * `hints.ai_socktype`
      * `ai->ai_protocol`
      * `ai->ai_addr`
      * `ai->ai_addrlen`
    * **需要头指针，遍历指针**
  * 返回server可能的ip地址链表信息
    * 尝试连入每个ip，直到成功连入

# 读写

`send(fd, char* data, data_len, flags)`

* 注意server使用`accept()`返回的新`connfd`进行读写，不要用绑定监听的fd
* `MSG_NOSIGNAL`flags，返回error关闭连接
  * 而不是发送`SIGPIPE`信号直接结束进程

`recv(fd, buf*, BUFLEN, flags`

* buffer指针存储数据
* flags
  * `MSG_PEEK` peek流入数据，也可以不设置标志
* 读取成功，返回读取字节数，否则返回`-1`
* **需要注意的是，recv()函数不会在读取的数据中添加终止符零字节，所以在数据上使用字符串函数是不安全的，除非你自己添加终止符。**

recv()调用会在没有可用的套接字读取时被阻塞。send()调用将被阻塞，直到它能够发送一些数据（如果网络严重拥堵，send()可能会在只发送了部分请求的数据后返回--

* **你需要检查返回值，看看它是否发送了所有数据，然后有可能再次调用send()来发送任何未发送的数据**）

# 多套接字处理

server一次性处理创建多个连接

* 使用多线程，一个套接字对应一个线程
  * 一个线程使用`bind()` & `listen()`建立绑定监听套接字
  * 循环调用`accept()`接收新连接，每个新返回的`connfd`给一个新线程，进行特定处理
  * **因为只存在一个监听套接字，使用多线程调用accept为错误模式。应使用单线程接收新连接，并为每个新连接新建一个线程任务交给线程池处理**
  * **确保每个线程的内存空间，`malloc()`，避免fd的互相覆写**

:orange: 另外一种方式 - `select()`

* 监控多套接字，确认这些套接字是否有足够空间写，或可用数据接收
* **使单线程可以处理多套接字**
* <font color="deeppink">适用于短期连接，但过多连接（>1k）时操作缓慢，可以考虑`epoll()`</font>

```c
// 使用select()接收多套接字fd1，fd2的数据

#include <sys/select.h>
...
int fd1, fd2;
fd_set rfds; //集合存储多套接字

struct timeval timeout;
timeout.tv_sec = 1; // 1 second timeout; pass NULL as the last
timeout.tv_usec = 0; // argument to select() for no timeout

FD_ZERO(&rfds); // Create the set of file descriptors to 创建多套接字的集合

FD_SET(fd1, &rfds); // select upon (limited to FD_SETSIZE as
FD_SET(fd2, &rfds); // as defined in <sys/select.h>)

// The nfds argument is the value of the largest file descriptor
// used, plus 1 (note: not the number of file descriptors used).
int nfds = max(fd1, fd2) + 1; // 使用的最大fd值 +1，不是使用的fd数量

int rc = select(nfds, &rfds, NULL, NULL, &timeout); //不超时情况下选择可用套接字？

if (rc == 0) {
    // 超时
    ... // timeout, with nothing available to recv()
} else if (rc > 0) {
    if (FD_ISSET(fd1, &rfds)) {
    ... // Data is available to recv() from fd1
  }
    if (FD_ISSET(fd2, &rfds)) {
  ... // Data is available to recv() from fd2
  }
} else if (rc < 0) {
    ... // error
}
```

# 关闭连接

注意server关闭的是连接套接字connfd，如不再监听再是关闭底层fd

* client需要在server之前关闭，
  * 这是因为调用close()的系统会进入一个 "时间等待 "状态，在这个状态下，它不允许另一个套接字绑定到同一个端口，直到某个超时时间结束，以确保任何晚到的数据不会最终进入错误的连接
  * 如果服务器崩溃或者在客户端之前关闭了连接，那么你将无法重新启动它，直到超时结束（bind()调用将失败并返回一个EADDRINUSE错误）。可以设置SO_REUSEADDR套接字选项来避免超时，但这样做会引入一个安全漏洞，因为它允许以前连接的数据出现在新的连接上，所以不应该使用。

```c
#include <unistd.h>
close(fd);
```

