# Content

* [Content](#content)
* [安全网络系统：Secure Networked System](#安全网络系统secure-networked-system)
* [Trust vs Threats](#trust-vs-threats)
* [当前安全问题:Some current Security Concerns](#当前安全问题some-current-security-concerns)
* [网络&计算机安全要求：Network & Computer Security Requirements](#网络计算机安全要求network--computer-security-requirements)
* [CIA Triad](#cia-triad)
* [加密系统：Cryptosystems](#加密系统cryptosystems)
* [Cryptography表示： formal view](#cryptography表示-formal-view)
* [对称加密：Symmetric Cryptography](#对称加密symmetric-cryptography)
* [非对称加密：Assymetric Cryptography](#非对称加密assymetric-cryptography)
* [混合加密：Hybrid Cryptography](#混合加密hybrid-cryptography)
* [网络基本安全问题：Fundamental Security Issue](#网络基本安全问题fundamental-security-issue)
* [BGP安全问题：Security issues with BGP](#bgp安全问题security-issues-with-bgp)
* [BGP攻击：Attacks on BGP](#bgp攻击attacks-on-bgp)
* [BGP前缀劫持：Prefix Hijacking](#bgp前缀劫持prefix-hijacking)
* [BGP安全：Securing BGP](#bgp安全securing-bgp)
* [RPKI](#rpki)
* [RPKI - hierarchy](#rpki---hierarchy)
* [RPKI - 问题](#rpki---问题)
* [BGPsec](#bgpsec)
* [有无BGPsec: diffs & non-BGPsec](#有无bgpsec-diffs--non-bgpsec)
* [BGPsec - 开放安全问题：open security issues](#bgpsec---开放安全问题open-security-issues)
* [BGPsec-虫洞攻击：wormhole attack example](#bgpsec-虫洞攻击wormhole-attack-example)
* [路由安全：routing security](#路由安全routing-security)
* [===============](#)
* [why防火墙-网络问题:Network issue](#why防火墙-网络问题network-issue)
* [防火墙目标：Firewalls purpose](#防火墙目标firewalls-purpose)
* [防火墙功能：functions](#防火墙功能functions)
* [防火墙组件：components](#防火墙组件components)
* [防火墙组件-包过滤器：packet filter](#防火墙组件-包过滤器packet-filter)
* [防火墙组件-代理：proxy](#防火墙组件-代理proxy)
* [防火墙外围设备-NAT: peripheral-NAT](#防火墙外围设备-nat-peripheral-nat)
* [NGFW-Next Generation Firewalls](#ngfw-next-generation-firewalls)
* [防火墙架构：Firewwall architectures](#防火墙架构firewwall-architectures)
* [防火墙例子](#防火墙例子)
* [防火墙问题：issues](#防火墙问题issues)
* [===============](#-1)
* [入侵vs异常：Intrusion vs Anomaly](#入侵vs异常intrusion-vs-anomaly)
* [入侵检测系统IDS - intrusion detection system](#入侵检测系统ids---intrusion-detection-system)
* [Host-based IDS (HIDS)](#host-based-ids-hids)
* [Application-based IDS](#application-based-ids)
* [network-based IDS (NIDS)](#network-based-ids-nids)
* [Snort IDS （rule exmaple）](#snort-ids-rule-exmaple)
* [NIDS优点&缺点](#nids优点缺点)
* [异常检测:Anomaly detection (AD)](#异常检测anomaly-detection-ad)
* [AD modeling viewpoints](#ad-modeling-viewpoints)
* [AD problem spectrum](#ad-problem-spectrum)
* [有效AD；towards effective AD](#有效adtowards-effective-ad)
* [例子-PCA on Tier 2 backbone : Principal component analysis](#例子-pca-on-tier-2-backbone--principal-component-analysis)
* [例子-AD using PCA](#例子-ad-using-pca)
* [AD优点&缺点](#ad优点缺点)
* [===============](#-2)

# 安全网络系统：Secure Networked System

![](/static/2022-05-15-20-13-22.png)

# Trust vs Threats

![](/static/2022-05-15-20-14-16.png)

# 当前安全问题:Some current Security Concerns

![](/static/2022-05-15-20-14-57.png)

all could inter-relate in a single attack chain

# 网络&计算机安全要求：Network & Computer Security Requirements

![](/static/2022-05-15-20-15-44.png)

# CIA Triad

![](/static/2022-05-15-20-16-15.png)

# 加密系统：Cryptosystems

![](/static/2022-05-15-20-17-45.png)

# Cryptography表示： formal view

![](/static/2022-05-15-20-20-44.png)

# 对称加密：Symmetric Cryptography

![](/static/2022-05-15-20-21-25.png)

* 问题 - 秘钥分发 key distribution
* 快

# 非对称加密：Assymetric Cryptography

![](/static/2022-05-15-20-22-04.png)

* 加密解密慢

# 混合加密：Hybrid Cryptography

![](/static/2022-05-15-20-22-59.png)

* 公钥 & 对称加密 public key & symmetric cryptography
  * 公钥分发会话密钥 session key

# 网络基本安全问题：Fundamental Security Issue

![](/static/2022-05-15-20-24-37.png)

* 攻击针对网络可用性 network availability
* 问题 - 路由安全  routing security

# BGP安全问题：Security issues with BGP

![](/static/2022-05-15-20-25-47.png)

# BGP攻击：Attacks on BGP

![](/static/2022-05-15-20-27-12.png)

* Reconnaissance - passive eavesdropping
* Main in the Middle
* DDos
* prefix hijacking

# BGP前缀劫持：Prefix Hijacking

![](/static/2022-05-15-20-27-38.png)

how it works
![](/static/2022-05-15-20-27-56.png)

# BGP安全：Securing BGP

![](/static/2022-05-15-20-28-47.png)

# RPKI

![](/static/2022-05-15-20-29-13.png)
![](/static/2022-05-15-20-29-21.png)

# RPKI - hierarchy

![](/static/2022-05-15-20-29-38.png)

# RPKI - 问题

![](/static/2022-05-15-20-29-55.png)
![](/static/2022-05-15-20-30-54.png)

# BGPsec

![](/static/2022-05-15-20-31-07.png)
![](/static/2022-05-15-20-31-14.png)

# 有无BGPsec: diffs & non-BGPsec

![](/static/2022-05-15-20-31-46.png)

# BGPsec - 开放安全问题：open security issues

![](/static/2022-05-15-20-32-08.png)

# BGPsec-虫洞攻击：wormhole attack example

![](/static/2022-05-15-20-32-56.png)

# 路由安全：routing security

![](/static/2022-05-15-20-33-33.png)

# ===============

# why防火墙-网络问题:Network issue

![](/static/2022-05-15-20-38-45.png)

# 防火墙目标：Firewalls purpose

![](/static/2022-05-15-20-39-43.png)

# 防火墙功能：functions

![](/static/2022-05-15-20-40-10.png)

# 防火墙组件：components

![](/static/2022-05-15-20-41-32.png)

* NAT
* IDS 入侵检测系统 intrusion detection system
* VPN endpoints端点
* reactive components

# 防火墙组件-包过滤器：packet filter

![](/static/2022-05-15-20-43-00.png)

# 防火墙组件-代理：proxy

![](/static/2022-05-15-20-43-33.png)

# 防火墙外围设备-NAT: peripheral-NAT

![](/static/2022-05-15-20-44-20.png)

# NGFW-Next Generation Firewalls

![](/static/2022-05-15-20-44-39.png)

# 防火墙架构：Firewwall architectures

![](/static/2022-05-15-20-45-05.png)

de-militarised zone - DMZ

# 防火墙例子

![](/static/2022-05-15-20-45-57.png)
![](/static/2022-05-15-20-46-15.png)

# 防火墙问题：issues

![](/static/2022-05-15-20-47-14.png)

* ip fragmentation
* multiple flows with varing security treatments

# ===============

# 入侵vs异常：Intrusion vs Anomaly

![](/static/2022-05-15-20-48-29.png)

# 入侵检测系统IDS - intrusion detection system

![](/static/2022-05-15-20-49-18.png)

# Host-based IDS (HIDS)

![](/static/2022-05-15-20-50-10.png)

# Application-based IDS

![](/static/2022-05-15-20-50-29.png)

# network-based IDS (NIDS)

![](/static/2022-05-15-20-50-45.png)
![](/static/2022-05-15-20-50-50.png)

NIDS例子

![](/static/2022-05-15-20-51-01.png)

# Snort IDS （rule exmaple）

![](/static/2022-05-15-20-51-52.png)

# NIDS优点&缺点

![](/static/2022-05-15-20-52-33.png)

# 异常检测:Anomaly detection (AD)

![](/static/2022-05-15-20-53-54.png)

# AD modeling viewpoints

![](/static/2022-05-15-20-54-26.png)

# AD problem spectrum

![](/static/2022-05-15-20-54-47.png)

# 有效AD；towards effective AD

![](/static/2022-05-15-20-55-38.png)

# 例子-PCA on Tier 2 backbone : Principal component analysis

![](/static/2022-05-15-20-56-56.png)

# 例子-AD using PCA

![](/static/2022-05-15-20-57-15.png)
![](/static/2022-05-15-20-57-23.png)

# AD优点&缺点

![](/static/2022-05-15-20-57-38.png)

# ===============