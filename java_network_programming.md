# JAVA网络编程

## IP

ip地址 - `InetAddress`

* `127.0.0.1`本低localhost
* 分类
  * ipv4/ipv6
    * ipv4: 127.0.0.1, 4B, 0~255, 42亿
    * ipv6: f480::915d:470e:d522:4339%16 128bit, 8个无符号整数
  * 公网(互联网)/私网(局域网)
    * ABCD类地址
    * 192.168.xx.xx 专门给组织内部使用

🍬 常用方法

* 获取指定ip`static InetAddress getByName(String host)`
* 获取本机地址`static InetAddress getLocalHost() throws UnknownHostException`
* 

🍬 其他方法

* 获取ip地址的String表示`String getHostAddress()`
* 获取数组形式的IP地址`byte[] getAddress()`
  * 类似`getHostAddress()`
* 获取域名，或自己电脑名字(主机名)`String getHostName()`
* 获取规范ip`String getCanonicalHostName()`

```java
/* 测试IP */
// static InetAddress getByName(String host)
// static InetAddress getLocalHost() throws UnknownHostException

// 查询本机地址
InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
InetAddress inetAddress = InetAddress.getByName("localhost");
InetAddress inetAddress = InetAddress.getLocalHost();
// 查询网站ip地址
InetAddress i2 = InetAddress.getByName("www.baidu.com");

```