# Content

* [Content](#content)
* [边缘连接-互联网中转服务模式：Connecting at the Edge: Internet Transit Service Model](#边缘连接-互联网中转服务模式connecting-at-the-edge-internet-transit-service-model)
* [不对称互联收费模式：Internet Transit Pricing Mode](#不对称互联收费模式internet-transit-pricing-mode)
* [trasit技巧：Tricks of the (Internet transit) trade](#trasit技巧tricks-of-the-internet-transit-trade)
* [================](#)
* [对称互联：Connecting to the core of the Internet – Peering](#对称互联connecting-to-the-core-of-the-internet--peering)
* [对称互联步骤：The Internet Peering process](#对称互联步骤the-internet-peering-process)
* [互联会话：Implementation of Peering session](#互联会话implementation-of-peering-session)
* [商业案例：The business case for Peering](#商业案例the-business-case-for-peering)
* [全球互联网对等生态系统;The global Internet Peering ecosystem](#全球互联网对等生态系统the-global-internet-peering-ecosystem)
* [对等生态系统种类：Species of Internet Peering Ecosystem](#对等生态系统种类species-of-internet-peering-ecosystem)
* [演变：Evolution of the Internet Peering Ecosystem](#演变evolution-of-the-internet-peering-ecosystem)
* [国际互联：International Peering and the Future](#国际互联international-peering-and-the-future)
* [================](#-1)
* [内部网关，外部网关协议：Internal vs Extenral Gateway Protocols (IGP vs EGP)](#内部网关外部网关协议internal-vs-extenral-gateway-protocols-igp-vs-egp)
* [边界网关协议：Understanding BGP](#边界网关协议understanding-bgp)
* [BGP消息类型： Message Types](#bgp消息类型-message-types)
* [BGP基本属性： Base standard attributes](#bgp基本属性-base-standard-attributes)
* [内部BGP，外部BGP：Internal vs External BGP](#内部bgp外部bgpinternal-vs-external-bgp)
* [路由选择：Route Selection Process (tie-breakers)](#路由选择route-selection-process-tie-breakers)
* [Routing Policy and Transit vs. Nontransit](#routing-policy-and-transit-vs-nontransit)
* [供应商对BGP的使用：Providers’ Use of BGP](#供应商对bgp的使用providers-use-of-bgp)
* [BGP扩展-路由抑制机制&社区：BGP Extensions (ctd) – Route Flap Dampening and BGP Communities](#bgp扩展-路由抑制机制社区bgp-extensions-ctd--route-flap-dampening-and-bgp-communities)
* [BGP社区例子-强制transit: Communities Example: Enforcing transit policy](#bgp社区例子-强制transit-communities-example-enforcing-transit-policy)

# 边缘连接-互联网中转服务模式：Connecting at the Edge: Internet Transit Service Model 

不对称互联(Transit)

![](/static/2022-05-15-18-09-33.png)

> 在此模式下，一个骨干网为了进行互联向另一个骨干网付费，双方实力相差悬殊，一方面小ISP不能也不需要建立全网状网连接，另一方面大ISP有足够多的路由来满足小ISP的需求，常见于上级ISP与下级ISP之间和国外互联网与国内互联网之间的互联。提供服务的一方有义务向另一方开放全部路由，即业务是完全穿透的，可以透过转接方进入其它骨干网。这是一种典型的“提供者—用户”的商务关系，用户(通常是较小的网络运营商)通过向提供者(通常是较大网络运营商)支付转接互联费以购买业务，实现对其它互联网的访问。

# 不对称互联收费模式：Internet Transit Pricing Mode

![](/static/2022-05-15-18-15-11.png)

第95百分位数模型（也称为P95或95/5

why 95/5

![](/static/2022-05-15-18-22-52.png)

# trasit技巧：Tricks of the (Internet transit) trade

![](/static/2022-05-15-18-23-53.png)
![](/static/2022-05-15-18-24-13.png)

# ================

# 对称互联：Connecting to the core of the Internet – Peering 

![](/static/2022-05-15-18-25-10.png)

# 对称互联步骤：The Internet Peering process 

![](/static/2022-05-15-18-26-06.png)

# 互联会话：Implementation of Peering session 

![](/static/2022-05-15-18-28-01.png)

# 商业案例：The business case for Peering 

![](/static/2022-05-15-18-28-45.png)

# 全球互联网对等生态系统;The global Internet Peering ecosystem 

![](/static/2022-05-15-18-29-19.png)
![](/static/2022-05-15-18-29-43.png)

# 对等生态系统种类：Species of Internet Peering Ecosystem

![](/static/2022-05-15-18-30-37.png)

# 演变：Evolution of the Internet Peering Ecosystem 

![](/static/2022-05-15-18-31-06.png)

# 国际互联：International Peering and the Future 

![](/static/2022-05-15-18-31-36.png)

# ================

# 内部网关，外部网关协议：Internal vs Extenral Gateway Protocols (IGP vs EGP)

![](/static/2022-05-15-18-32-55.png)

# 边界网关协议：Understanding BGP

![](/static/2022-05-15-18-36-32.png)

# BGP消息类型： Message Types 

![](/static/2022-05-15-18-37-04.png)

# BGP基本属性： Base standard attributes 

![](/static/2022-05-15-18-37-58.png)
![](/static/2022-05-15-18-38-18.png)

# 内部BGP，外部BGP：Internal vs External BGP

![](/static/2022-05-15-18-38-55.png)

# 路由选择：Route Selection Process (tie-breakers)

![](/static/2022-05-15-18-40-03.png)

# Routing Policy and Transit vs. Nontransit

![](/static/2022-05-15-18-40-39.png)
![](/static/2022-05-15-18-40-44.png)

# 供应商对BGP的使用：Providers’ Use of BGP

![](/static/2022-05-15-18-41-19.png)

# BGP扩展-路由抑制机制&社区：BGP Extensions (ctd) – Route Flap Dampening and BGP Communities

![](/static/2022-05-15-18-43-19.png)

# BGP社区例子-强制transit: Communities Example: Enforcing transit policy 

![](/static/2022-05-15-18-44-18.png)