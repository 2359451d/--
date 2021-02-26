# tcpdump

用简单的话来定义tcpdump，就是：dump the traffic on a network，根据使用者的定义对网络上的数据包进行截获的包分析工具。 tcpdump可以将网络中传送的数据包的“头”完全截获下来提供分析。它支持针对网络层、协议、主机、网络或端口的过滤，并提供and、or、not等逻辑语句来帮助你去掉无用的信息。

* `tcpdump -r lab04.pcap`
  * 输出数据包截获内容
  * `tcpdump -r lab04.pcap | more`分页预览
  * `tcpdump -r lab04.pcap > filename`输出到文件
* **tcpdump默认显示第一个包（server，client）的实际序列号**
  * 之后的序列号会与之相对，如`ack=1`，因为TCP中一个设置了SYN标志的包假定为一个序列号
  * 可以用 `tcpdump -S -r lab04.pcap`打印每个包的实际序列号
    * 第三个，ACK包的ack应比SYN-ACK的seq+1

`tcpdump -v -r lab04.pcap`

* 打印头信息&内容体详情

`tcpdump -X -r lab04.pcap`

* 打印完整数据包截获内容体

```txt
13:15:49.840002 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [.], ack 2088141, win 4944, options [nop,nop,TS val 729677297 ecr 2521817918], length 0
        0x0000:  4500 0034 0000 4000 4006 98fd c0a8 0042  E..4..@.@......B
        0x0010:  5d5d 837f dd9f 0050 6e6c 5889 62ff 4a78  ]].....PnlX.b.Jx
        0x0020:  8010 1350 cc4b 0000 0101 080a 2b7d fdf1  ...P.K......+}..
        0x0030:  964f e33e
```

## 显示IP&port

默认解析ip & port为域名，禁用解析（还可防止tcpdump发出DNS查找，排除网络问题时降低网络流量）

* `tcpdum -nn`

## 显示前3个包

`tcpdump -c3 -r lab04.pcap`

## 标准TCP输出格式理解

```txt
08:41:13.729687 IP 192.168.64.28.22 > 192.168.64.1.41916: Flags [P.], seq 196:568, ack 1, win 309, options [nop,nop,TS val 117964079 ecr 816509256], length 372
```

* `08:41:13.729687`代表接收到的数据包的时间戳（根据本地时钟）
* `IP` 代表网络层协议
* `192.168.64.28.22` 源IP，&port
* `192.168.64.1.41916`目标IP & port
* `Flags [P.]` TCP标志
* `seq 196:568`
  * 该包包含 196-568bytes
* `ack 1`
  * side of sending data,
  * side of receiving data - 该ack应为预期收到的byte(data)
    * 如本次发送 seq=568，下一个包（接收）的ack=568
* `win 309`
  * 接收缓冲区的可用字节数
* `options`
  * TCP选项，如 `MSS(最大段大小)`，窗口大小
  * https://www.iana.org/assignments/tcp-parameters/tcp-parameters.xhtml
* `length 372`
  * payload数据字节大小
  * 长度是序列号中最后一个字节和第一个字节之间的差异。

:orange: 常见标志

Value | Flag Type | Description
---------|----------|---------
 S | SYN | Connection Start
 S. | SYN-ACK | initial packet of server,SYN同步标识，以及确认[S]的ACK=seq+1
 F | FIN | Connection Finish
 P | PUSH | data push
 [P.] | PSH| ,push推送，数据传输
 R | RST | connection reset
 . | ACK | Acknowledgment

## 截获数据包的过滤：Filtering Packets

tcpdump最强大的功能之一是它可以使用各种参数对捕获的数据包进行过滤，如源和目的IP地址、端口、协议等

### 协议过滤

如`tcpdump -i any -c5 icmp`

* 过滤ICMP包

### 主机过滤

`tcpdump -i any -c5 -nn host 54.204.39.132`

* 直截获 from-to host的数据包

### port过滤

`tcpdump -i any -c5 -nn port 80`

### 源/目标IP/主机名过滤

`tcpdump -i any -c5 -nn src 192.168.122.98`

* 只截获from`ip`的数据包

`tcpdump -i any -c5 -nn dst 192.168.122.98`

* 只截获to`ip`的数据包

### 复合过滤

`tcpdump -i any -c5 -nn "port 80 and (src 192.168.122.98 or src 54.204.39.132)"`

* http 80服务，源`192.168.122.98`或源`54.204.39.132`

## 检查包内容

检查数据包的内容，以确保我们发送的消息包含了我们需要的内容，或者我们收到了预期的响应。

* `-x/X` 十六进制打印内容体
* `-A` ASCII打印内容体

```txt
13:15:48.900817 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [S], seq 1852594190, win 65535, options [mss 1460,nop,wscale 6,nop,nop,TS val 729676479 ecr 0,sackOK,eol], length 0
        0x0000:  4500 0040 0000 4000 4006 98f1 c0a8 0042
        0x0010:  5d5d 837f dd9f 0050 6e6c 580e 0000 0000
        0x0020:  b002 ffff ca8d 0000 0204 05b4 0103 0306
        0x0030:  0101 080a 2b7d fabf 0000 0000 0402 0000
13:15:48.941961 IP yali.mythic-beasts.com.http > 192.168.0.66.56735: Flags [S.], seq 1658809771, ack 1852594191, win 28960, options [mss 1400,sackOK,TS val 2521817020 ecr 729676479,nop,wscale 7], length 0
        0x0000:  4500 003c 128b 0000 3a06 cc6a 5d5d 837f
        0x0010:  c0a8 0042 0050 dd9f 62df 6dab 6e6c 580f
        0x0020:  a012 7120 2405 0000 0204 0578 0402 080a
        0x0030:  964f dfbc 2b7d fabf 0103 0307
13:15:48.942010 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [.], ack 1658809772, win 2060, options [nop,nop,TS val 729676520 ecr 2521817020], length 0
        0x0000:  4500 0034 0000 4000 4006 98fd c0a8 0042
        0x0010:  5d5d 837f dd9f 0050 6e6c 580f 62df 6dac
        0x0020:  8010 080c bb80 0000 0101 080a 2b7d fae8
        0x0030:  964f dfbc
```

```
<!-- ascii -->
13:15:48.900817 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [S], seq 1852594190, win 65535, options [mss 1460,nop,wscale 6,nop,nop,TS val 729676479 ecr 0,sackOK,eol], length 0
E..@..@.@......B]].....PnlX........................
+}..........
13:15:48.941961 IP yali.mythic-beasts.com.http > 192.168.0.66.56735: Flags [S.], seq 1658809771, ack 1852594191, win 28960, options [mss 1400,sackOK,TS val 2521817020 ecr 729676479,nop,wscale 7], length 0
E..<....:..j]].....B.P..b.m.nlX...q $......x...
.O..+}......
13:15:48.942010 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [.], ack 1658809772, win 2060, options [nop,nop,TS val 729676520 ecr 2521817020], length 0
E..4..@.@......B]].....PnlX.b.m............
+}...O..
```

## 输出/读取截获文件

`-w`选项

* `tcpdump -i any -c10 -nn -w webserver.pcap port 80`
  * 保存前10个http的数据截获至`webserver.pcap`文件，禁用域名解析

`-r`读取二进制文件（注意可以结合过滤，读取）

* `tcpdump -nn -r webserver.pcap`


# Handshake

```txt
<!-- SYN bit set:初始序列号seq, 拥塞窗口win, TCP options, 0 length(不携带任何数据) -->
13:15:48.900817 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [S], seq 1852594190, win 65535, options      [mss 1460,nop,wscale 6,nop,nop,TS val 729676479 ecr 0,sackOK,eol], length 0
```

```txt
<!-- SYN-ACK包 -->
<!--初始序列号seq用于server-to-client定向，拥塞窗口win,TCP options, 0length,  -->
13:15:48.941961 IP yali.mythic-beasts.com.http > 192.168.0.66.56735: Flags [S.], seq 1658809771, ack 1852594191, win 28960, options [mss 1400,sackOK,TS val 2521817020 ecr 729676479,nop,wscale 7], length 0
```

```txt
<!-- ACK 用于确认SYN-ACK包 -->
<!-- 之后的序列号会与之相对，如`ack=1`，因为TCP中一个设置了SYN标志的包假定为一个序列号 -->
13:15:48.942010 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [.], ack 1, win 2060, options [nop,nop,TS val 729676520 ecr 2521817020], length 0

<!-- tcpdump -S -r lab04.pcap -->
13:15:48.942010 IP 192.168.0.66.56735 > yali.mythic-beasts.com.http: Flags [.], ack 1658809772, win 2060, options [nop,nop,TS val 729676520 ecr 2521817020], length 0
```

3个包（header信息）之后的是，握手连接建立的包，最后是server-to-client的数据包 & 连接关闭包

# TCP data Transfer

`tcpdump -ttttt -r lab04.pcap > packets.dat`

* 格式化截获的数据包，**并显示相对于连接开始的时间戳，并将结果保存在文件packets.dat中**

1. 删除client-to-server包
   1. 只保留TCP连接中传输的数据，server-to-client
2. 删除握手开始&握手结束的包
   1. 删除 文件开头的 SYN-SYNACK-ACK握手包（建立连接）， & 文件末尾的FIN-FINACK-ACK握手包（关闭连接）
3. 删除不含数据的包
   1. 删除只包含ack的行，length=0

最后只保留server-to-client的数据包（每行）

处理文件，**从每行的序列号范围中提取时间戳和最后的序列号**。
将处理后的结果保存在文件lab04 timeseq.dat中。例如，如果一行包含文本。

```txt
00:00:00.093456 IP yali.mythic-beasts.com.http > 192.168.0.66.56735: Flags [.], seq 1:1389, ack 122...
<!-- 提取 -->
00:00:00.093456 1389
```

Prepare a plot of the sequence number vs. timestamp for the lab04-timeseq.dat file. The x-axis of this plot will represent timestamp, in seconds since the start of the connection; the y-axis will represent sequence numbers. For each line in the lab04-timeseq.dat file, place a mark on the appropriate point on the graph, joining the points with a line. You may use any graph plotting tool you want to prepare this graph. Then, prepare a second plot, this time including only packets received in the first 250ms of the connection. Inspect the two plots to understand the data transfer behaviour of the TCP connection. 

* 为lab04-timeseq.dat文件准备一个序列号与时间戳的图。
  * `x`轴代表**时间戳，从连接开始后的秒数**；
  * `y`轴代表**序列号**
* 对于 lab04-timeseq.dat **文件中的每一行，请在图形上的相应点上做一个标记，**
  * 用一条线将这些点连接起来。
  * 你可以使用任何图形绘制工具来准备这个图形

然后，准备第二张图，这次只包括在连接的**前250ms内收到的数据包**。检查这两张图以了解TCP连接的数据传输行为。