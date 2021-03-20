# Requirement

程序 `dnslookup`

* 取命令行中 >=1个DNS域名
* 为每个域名进行DNS查找
  * `getaddrinfo()`
  * 替换 `socket()` & `connect()`调用 为 `inet_ntop()` 以打印 IP 地址
* 每次DNS查找结束后，打印IP地址列表
  * 域名 & 地址类型 为其前缀

---

返回的IP地址 & 类型可能不同，取决于查询的时间 & 地点，输出符合以下格式 （域名hostname，address type， IP）

```bash
$ ./dnslookup www.google.com
www.google.com IPv4 172.217.23.36
www.google.com IPv6 2a00:1450:4009:801::2004
$
```

* `clang -W -Wall dnslookup.c -o dnslookup`

使用你的 dnslookup 工具，找到一系列网站的 IP 地址。这些应该是学术，商业，社交网络，新闻和个人网站的组合，来自不同国家

* 你应该找到大约15-20个不同网站的地址  You should find the addresses of around 15-20 different websites

# 2

Traceroute 工具可以发现用于到达特定目的地的网络路径。例如，从一台 stlinux 机器到之前发现的 www.google. com 的 IPv6地址的 traceroute 可能会显示:

$ traceroute -6 -q 1 -n 2a00:1450:4009:801::2004
traceroute to 2a00:1450:4009:801::2004 (2a00:1450:4009:801::2004), 30 hops max, 80 byte packets
1 2001:630:40:f00:e22f:6dff:fe2c:ed80 0.276 ms
2 2001:630:40:20::11 0.298 ms
3 2001:630:40:20::72 0.723 ms
4 2001:630:0:900c::1 0.587 ms
5 2001:630:0:10::185 1.016 ms
6 2001:630:0:10::1fd 5.337 ms
7 2001:630:0:10::1f1 7.400 ms
8 2001:630:0:10::1dd 11.276 ms
9 2001:630:0:10::1c9 11.742 ms
10 *
11 2001:4860:0:1::ca1 12.514 ms
12 2001:4860:0:1::2b3 12.918 ms
13 2a00:1450:4009:801::2004 12.234 ms

---

`traceroute -6 -q 1 -n `

* `-4`  ipv4, `-6` ipv6
* `-q 1` 每跳只探测一次
* `-n` 不查找路由域名
* 距离（跳）， 路由IP，响应时间

---

在本练习的第1部分中，使用 dnslookup 工具对您发现的每个 IP 地址运行 traceroute，将输出重定向到文件中，使用针对 IPv4和 IPv6 traceroute 输出的单独文件。您可能会发现编写一个脚本来完成这个任务会很有帮助。这会给你一个 IP 的记录

数据经过的路由器的地址到达每个选定的站点(或者，如果有防火墙，直到它到达阻止 traceroute 的防火墙)

# 3

理解拓扑 & `traceroute`

Review the IP addresses found for each site in Part 1 of this exercise, and the router level network topology maps you prepared in Part 2, and write up a short report, not to exceed two sides of A4 paper when printed using a 10pt font, that discusses the following points:

查看本练习第1部分中为每个站点找到的 IP 地址，以及在第2部分中准备的路由器级别的网络拓扑地图，并写一份简短的报告，使用10号字体打印时不超过 a 4纸的两面，讨论以下几点:

* IP 地址: 查看第1部分中不同站点的地址，有些站点是否有多个 IP 地址，如果有，这意味着什么？如果你多次运行你的 dnslookup 程序，你是否总是得到同一个网站的 IP 地址，如果没有，为什么会这样？如果你从不同的位置运行 dnslookup 程序，你会得到相同的网站 IP 地址吗? 如果没有，为什么？有 IPv6地址的网站比例是多少？ IP addresses: Looking at the addresses found for the different sites in Part 1, do some sites have more than one IP address, and what does it mean if they do? If you run your dnslookup program several times, do you always get the same IP addresses for a site, and if not, why might this be? Do you get the same IP addresses for a site if you run your dnslookup program from different locations, and if not, why? What proportion of sites have an IPv6 address?

路由器级拓扑映射: 查看您在本练习的第2部分中生成的路由器级拓扑映射，您能在网络中找到的最长路径是什么？从不同地点到同一目的地的路径是不相交的吗？到某些目的地有多条路线吗？你能从 IP 地址前缀的变化中推断出关于组织边界(例如，网络的哪些部分由不同的 isp 操作)的任何信息吗？ Router-level Topology Maps: Looking at the router level topology maps you produced in Part 2 of this exercise, what is the longest path you can find in the network? Are paths from different locations to the same destination disjoint? Are there multiple routes to some destinations? Can you infer anything about organisational boundaries (e.g., which parts of the network are operated by different ISPs) from changes in the IP address prefixes?

IPv4和 IPv6: IPv4和 IPv6路由器级网络拓扑地图匹配吗？当然，地址是不同的，但是拓扑映射是否具有相似的结构？想想你是否期望他们应该有相似的结构-有什么理由为什么 IPv4和 IPv6网络应该匹配？ IPv4 and IPv6: Do the IPv4 and IPv6 router level network topology maps match? The addresses will be different, of course, but do the topology maps have the similar structure? Think about whether you expect that they should have similar structure – is there any reason why the IPv4 and IPv6 networks should match?

Traceroute 工具: traceroute 工具是如何工作的？做一些背景阅读，了解 traceroute 如何能够找到路由器的 IP 地址的路径到目的地。您应该了解 TTL 在 traceroute 期间是如何变化的; ICMP 是什么，它与 IP、 UDP 和 TCP 有什么关系; 以及 traceroute 如何使用 ICMP The traceroute Tool: How does the traceroute tool work? Do some background reading to understand how traceroute can find the IP addresses of the routers on the path to a destination. You should learn how the TTL is varied during a traceroute; what is ICMP, and how does it relate to IP, UDP, and TCP; and how traceroute uses ICMP

Prepare this report using the word processing package of your choice, formatted for A4 size paper, and using the Times font in 10pt. Save a copy of your report in PDF format, under the filename report.pdf. You are encouraged to discuss the answers to these questions with the lecturer or lab demonstrators to make sure you understand the issues raised.