# Content

* [Content](#content)
* [边缘连接-互联网中转服务模式：Connecting at the Edge: Internet Transit Service Model](#边缘连接-互联网中转服务模式connecting-at-the-edge-internet-transit-service-model)
* [中转收费模式：Internet Transit Pricing Mode](#中转收费模式internet-transit-pricing-mode)
* [中转trasit技巧：Tricks of the (Internet transit) trade](#中转trasit技巧tricks-of-the-internet-transit-trade)
* [================](#)
* [对等：Connecting to the core of the Internet – Peering](#对等connecting-to-the-core-of-the-internet--peering)
* [对等步骤：The Internet Peering process](#对等步骤the-internet-peering-process)
* [对等会话：Implementation of Peering session](#对等会话implementation-of-peering-session)
* [商业案例：The business case for Peering](#商业案例the-business-case-for-peering)
* [全球互联网对等生态系统;The global Internet Peering ecosystem](#全球互联网对等生态系统the-global-internet-peering-ecosystem)
* [对等生态系统种类：Species of Internet Peering Ecosystem](#对等生态系统种类species-of-internet-peering-ecosystem)
* [对等演变：Evolution of the Internet Peering Ecosystem](#对等演变evolution-of-the-internet-peering-ecosystem)
* [国际互联：International Peering and the Future](#国际互联international-peering-and-the-future)
* [================](#-1)
* [内部网关，外部网关协议：Internal vs Extenral Gateway Protocols (IGP vs EGP)](#内部网关外部网关协议internal-vs-extenral-gateway-protocols-igp-vs-egp)
* [边界网关协议：Understanding BGP](#边界网关协议understanding-bgp)
* [BGP消息类型： Message Types](#bgp消息类型-message-types)
* [BGP基本属性： Base standard attributes](#bgp基本属性-base-standard-attributes)
* [内部BGP，外部BGP：Internal vs External BGP](#内部bgp外部bgpinternal-vs-external-bgp)
* [路由选择：Route Selection Process (tie-breakers)](#路由选择route-selection-process-tie-breakers)
* [路由策略-中转 vs 非中转：Routing Policy and Transit vs. Nontransit](#路由策略-中转-vs-非中转routing-policy-and-transit-vs-nontransit)
* [供应商对BGP的使用：Providers’ Use of BGP](#供应商对bgp的使用providers-use-of-bgp)
* [BGP扩展-路由抑制机制&联盟：BGP Extensions (ctd) – Route Flap Dampening and BGP Communities](#bgp扩展-路由抑制机制联盟bgp-extensions-ctd--route-flap-dampening-and-bgp-communities)
* [BGP联盟例子-强制transit: Communities Example: Enforcing transit policy](#bgp联盟例子-强制transit-communities-example-enforcing-transit-policy)

# 边缘连接-互联网中转服务模式：Connecting at the Edge: Internet Transit Service Model 

不对称互联(Transit)，，中转

![](/static/2022-05-15-18-09-33.png)

> 在此模式下，一个骨干网为了进行互联向另一个骨干网付费，双方实力相差悬殊，一方面小ISP不能也不需要建立全网状网连接，另一方面大ISP有足够多的路由来满足小ISP的需求，常见于上级ISP与下级ISP之间和国外互联网与国内互联网之间的互联。提供服务的一方有义务向另一方开放全部路由，即业务是完全穿透的，可以透过转接方进入其它骨干网。这是一种典型的“提供者—用户”的商务关系，用户(通常是较小的网络运营商)通过向提供者(通常是较大网络运营商)支付转接互联费以购买业务，实现对其它互联网的访问。

# 中转收费模式：Internet Transit Pricing Mode

![](/static/2022-05-15-18-15-11.png)

第95百分位数模型（也称为P95或95/5

why 95/5

![](/static/2022-05-15-18-22-52.png)

# 中转trasit技巧：Tricks of the (Internet transit) trade

![](/static/2022-05-15-18-23-53.png)
![](/static/2022-05-15-18-24-13.png)

# ================

# 对等：Connecting to the core of the Internet – Peering 

![](/static/2022-05-16-10-37-21.png)
![](/static/2022-05-15-18-25-10.png)

# 对等步骤：The Internet Peering process 

![](/static/2022-05-15-18-26-06.png)

# 对等会话：Implementation of Peering session 

![](/static/2022-05-15-18-28-01.png)

对等实现方式

* 直接电路 direct circuit
* IPX 互连交换点

# 商业案例：The business case for Peering 

![](/static/2022-05-15-18-28-45.png)

# 全球互联网对等生态系统;The global Internet Peering ecosystem 

![](/static/2022-05-15-18-29-19.png)
![](/static/2022-05-15-18-29-43.png)

# 对等生态系统种类：Species of Internet Peering Ecosystem

![](/static/2022-05-15-18-30-37.png)

# 对等演变：Evolution of the Internet Peering Ecosystem 

![](/static/2022-05-15-18-31-06.png)

# 国际互联：International Peering and the Future 

![](/static/2022-05-15-18-31-36.png)

# ================

# 内部网关，外部网关协议：Internal vs Extenral Gateway Protocols (IGP vs EGP)

![](/static/2022-05-15-18-32-55.png)

* BGP4为了支持CIDR而创建

# 边界网关协议：Understanding BGP

![](/static/2022-05-15-18-36-32.png)

# BGP消息类型： Message Types 

![](/static/2022-05-15-18-37-04.png)

# BGP基本属性： Base standard attributes 

![](/static/2022-05-15-18-37-58.png)
![](/static/2022-05-15-18-38-18.png)

# 内部BGP，外部BGP：Internal vs External BGP

![](/static/2022-05-15-18-38-55.png)

* iBGP

# 路由选择：Route Selection Process (tie-breakers)

![](/static/2022-05-15-18-40-03.png)

# 路由策略-中转 vs 非中转：Routing Policy and Transit vs. Nontransit

![](/static/2022-05-15-18-40-39.png)
![](/static/2022-05-15-18-40-44.png)

* 本地策略 LOCAL decision

# 供应商对BGP的使用：Providers’ Use of BGP

![](/static/2022-05-15-18-41-19.png)

* aggregation
* filter transit customers
* IXP, policing perring relationships

# BGP扩展-路由抑制机制&联盟：BGP Extensions (ctd) – Route Flap Dampening and BGP Communities

![](/static/2022-05-16-11-00-31.png)
![](/static/2022-05-15-18-43-19.png)

* route flap dampening
  * global routing state oscillation
* BGP community
  * route colouring

解决AS内部的IBGP网络连接激增问题，除了使用路由反射器之外，还可以使用联盟（Confederation）。

联盟将一个AS划分为若干个子AS。每个子AS内部建立IBGP全连接关系，子AS之间建立联盟EBGP连接关系，但联盟外部AS仍认为联盟是一个AS。

配置联盟后，原AS号将作为每个路由器的联盟ID。

这样有两个好处：一是可以保留原有的IBGP属性，包括Local Preference属性、MED属性和NEXT_HOP属性等；二是联盟相关的属性在传出联盟时会自动被删除，即管理员无需在联盟的出口处配置过滤子AS号等信息的操作。

# BGP联盟例子-强制transit: Communities Example: Enforcing transit policy 

![](/static/2022-05-15-18-44-18.png)