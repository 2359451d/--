# JAVA网络编程（Socket Programming）

网络编程中的两个问题

* 如何定位网络上一台/多台主机，定位主机上特定应用
* 找到主机后如何可靠高效低进行数据传输

## IP: InetAddress

ip地址 - `InetAddress`

* `127.0.0.1`本机localhost
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

🍬 其他实例方法

* 获取**ip地址**的String表示`String getHostAddress()`
* 获取数组形式的IP地址`byte[] getAddress()`
  * 类似`getHostAddress()`
* 获取**域名dns**，或自己电脑名字(主机名)`String getHostName()`
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

## Port

端口号 - 标识计算机上运行的进程

* 16bit整数，`0~65535`
* 分类
  * 公认端口，`0~1023`，被预先定义的服务通信占用，如
    * HTTP占用`80`，FTP-`21`,Telnet-`23`
  * 注册端口，`1024~49151`，分配给用户进程或应用程序，如
    * Tomcat-`8080`，mySQL-`3306`，Oracle-`1521`
  * 动态/私有端口，`49152~65535`

🍬 端口号&ip地址 = **网络套接字 - Socket**

* 可理解为，网络通信中封装的节点。再配合相应协议即可通信
* 客户端，指定对方IP，端口号，创建套接字`public Socket(InetAddress address, int port) throws IOException`
* 服务器，绑定端口号，创建服务端套接字`public ServerSocket(int port) throws IOException`
  * `Socket accept() throws IOException`，接收客户端套接字

## TCP Networking Programming

🍊 基本步骤

* 客户端- 创建Socket对象
  * 指明服务器ip&port
  * 获取输出流，输出数据
* 服务端- 创建ServerSocket对象
  * 绑定端口号
  * 调用`accept()`接收客户端套接字
  * 获取输入流，读取数据(到buffer，输出到byteArrayOutputStream等操作)

### Example 1

```java
@Test
public void Client(){
    Socket socket =null;
    OutputStream outputStream = null;
    InetAddress inet = null;// 对方的IP
    try {
        inet = InetAddress.getByName("127.0.0.1");
        socket = new Socket(inet, 8899);
        outputStream = socket.getOutputStream();
        outputStream.write("Hello, this is the client".getBytes());

    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();//close the socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@Test
public void Server() throws IOException {
    InputStream inputStream = null;
    InetAddress byName = InetAddress.getByName("127.0.0.1");
    ServerSocket serverSocket = new ServerSocket(8899);
    Socket socket = serverSocket.accept();// accepts the client socket
    System.out.println("Receiving the information from: "+socket.getInetAddress().getHostAddress());
    inputStream = socket.getInputStream();
    /*
    * NOT RECOMMENDED: Garbled Risk
    * */
//        int content = 0;
//        while((content = inputStream.read())!=-1){
//            System.out.println(content);
//        }
//        byte[] buffer = new byte[1024];
//        int readCount=0;
//        while((readCount=inputStream.read(buffer))!=-1){
//            System.out.println(new String(buffer, 0, readCount));
//        }

    // using the ByteArrayOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[5];
    int readCount=0;
    while ((readCount = inputStream.read(buffer))!=-1){
        byteArrayOutputStream.write(buffer,0,readCount);
    }
    System.out.println(byteArrayOutputStream.toString());

    // closing
    inputStream.close();
    serverSocket.close();
    socket.close();
    byteArrayOutputStream.close();
}
```

### Example 2

模拟客户端发送文件给服务端，服务端将文件保存在本地

```java
public void Client() throws UnknownHostException {
    FileInputStream in = null;
    OutputStream out = null;
    Socket socket = null;
    try {
        socket = new Socket(InetAddress.getByName("localhost"), 9090);
        out = socket.getOutputStream();
        in = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("Zoom_3710129000_01.png").getFile());
        byte[] buffer = new byte[1024];// read all the bytes once(at a time)
        int readCount=0;
        while((readCount=in.read(buffer))!=-1){
            out.write(buffer,0,readCount);// write the data in buffer to the outputStream
        }

    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

```java
public void Server(){
    InputStream in = null;
    FileOutputStream out= null;
    ServerSocket server = null;
    Socket client =null;
    try {
        out = new FileOutputStream(new File(Thread.currentThread().getContextClassLoader().getResource("Zoom_3710129000_01.png").getFile()));
        server = new ServerSocket(9090);
        client = server.accept();
        in = client.getInputStream();
        byte[] buffer = new byte[1024];
        int readCount=0;
        while((readCount=in.read(buffer))!=-1){
            out.write(buffer,0,readCount);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

### Example 3

* 模拟客户端发送文件给服务器，服务端保存到本地，并返回“发送成功”给客户端
* 并关闭相应连接
