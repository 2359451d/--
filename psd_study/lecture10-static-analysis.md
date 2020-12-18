# Static Analysis, Readability and Design Quality

* [Static Analysis, Readability and Design Quality](#static-analysis-readability-and-design-quality)
  * [静态vs动态分析：Static vs Dynamic Analysis](#静态vs动态分析static-vs-dynamic-analysis)
    * [静态分析用例：Use Cases](#静态分析用例use-cases)
  * [自我注释的代码：Self Documenting Code](#自我注释的代码self-documenting-code)
  * [自动代码分析的意见谱：The Spectrum of Opinionation for Automated Code Analysis](#自动代码分析的意见谱the-spectrum-of-opinionation-for-automated-code-analysis)
  * [代码风格标准：Code Style Standards](#代码风格标准code-style-standards)
    * [检查工具：Linting Tools](#检查工具linting-tools)
    * [整合gitlab：Integration with Gitlab](#整合gitlabintegration-with-gitlab)
  * [bug检测：Bug Detection](#bug检测bug-detection)
    * [漏洞分析--Java中的SQL注入示例：Vulnerablity Analysis - Example SQL Injection in Java](#漏洞分析--java中的sql注入示例vulnerablity-analysis---example-sql-injection-in-java)
    * [IO & Memory Management例子](#io--memory-management例子)
  * [圈复杂度：Cyclomatic Complexity](#圈复杂度cyclomatic-complexity)
    * [嵌套深度](#嵌套深度)
  * [耦合度：Measuring Coupling](#耦合度measuring-coupling)
  * [扇入扇出复杂度：Fan in versus fan out complexity](#扇入扇出复杂度fan-in-versus-fan-out-complexity)
  * [继承深度&宽度：Inheritance Hierarchy Depth & Width](#继承深度宽度inheritance-hierarchy-depth--width)
  * [Other Topics](#other-topics)
  * [Summary](#summary)

## 静态vs动态分析：Static vs Dynamic Analysis

![](/static/2020-11-24-21-56-43.png)

静态分析

* 适用于静止状态下的程序工件

动态分析

* 是在执行过程中应用的

程序分析构件可以是

* 源代码
* 编译后的对象代码

### 静态分析用例：Use Cases

![](/static/2020-11-24-22-08-12.png)

* 开发人员在提交审核前检查更改
* CI管道可以**阻止未能通过静态分析检查的合并请求**
  * 防止缺陷进入主线
  * 优化代码审查过程
* 协助审查员评估拟议的变更
  * by providing the measurements of the code

## 自我注释的代码：Self Documenting Code

![](/static/2020-11-24-22-09-44.png)

静态分析如何用来提高代码可读性

self-documenting code

* 以这样风格写的code，不需要额外的解释（source code comments解释）
* variable name
* control structure

维护冗余性&解释代码作用，self-documenting不再必要

## 自动代码分析的意见谱：The Spectrum of Opinionation for Automated Code Analysis

![](/static/2020-11-24-22-10-46.png)

* 最左边的考虑，需要被修复

## 代码风格标准：Code Style Standards

![](/static/2020-11-24-22-11-47.png)

* 大多数语言都有风格指南
  * '官方'或社区语言指南，
    * 如PEP8 for Python。
  * 供应商驱动，
    * 例如许多Ruby、Oracle、Eclipse、Atlassian的Java指南。

🍊 大多数语言都有linting检查工具

* check automatically whether the code is compiled with code standards

---

![](/static/2020-11-24-22-13-33.png)
![](/static/2020-11-24-22-15-30.png)

Code style standards 包含多个issues

* 规则包括
  * 行长度
    * 80，120
  * 标识符命名规范
    * `f/F/f_hand` 与 `last_commit_file_handle`（full literals推荐）
  * 空格使用规范，水平，垂直
    * `y = x  + z **    2/3`（错）
    * `y = x + z ** 2 / 3`（可读）
* 控制结构的使用
* 括号的使用
* 冗余代码
* 隐式使用模式
  * `type(x) == int` versus `isinstance(x, int)`
* 检查拼写的工具

### 检查工具：Linting Tools

![](/static/2020-11-24-22-17-09.png)

* 静态分析工具？

### 整合gitlab：Integration with Gitlab

![](/static/2020-11-24-22-18-41.png)

## bug检测：Bug Detection

![](/static/2020-11-24-22-19-08.png)

例子

* 可以返回null的'属性'方法
* 访问空对象属性
* 方法或函数中不一致的返回语句
* 过载的方法名称
* 重新定义或重新分配变量或参数

### 漏洞分析--Java中的SQL注入示例：Vulnerablity Analysis - Example SQL Injection in Java

![](/static/2020-11-24-22-20-46.png)

JDBC Statement的SQL注入例子

### IO & Memory Management例子

![](/static/2020-11-24-22-22-40.png)
![](/static/2020-11-24-22-23-05.png)
![](/static/2020-11-24-22-23-24.png)

## 圈复杂度：Cyclomatic Complexity

![](/static/2020-11-24-22-23-52.png)

`复杂度= 边- 结点数 + 连接组件数目（相连节点的的最大集合））*2` <10

![](/static/2020-11-24-22-37-52.png)

---

### 嵌套深度

![](/static/2020-11-24-22-38-22.png)

* 6 - calculate the total number of block in each control structure

## 耦合度：Measuring Coupling

![](/static/2020-11-24-22-38-46.png)

* 原始测量
  * 方法调用 & 变量访问
  * import语句
  * 类型使用
  * 继承关系(class)
* 测量域
  * 方法
  * 类
  * 模块
  * 包
  * app

## 扇入扇出复杂度：Fan in versus fan out complexity

![](/static/2020-11-24-22-40-50.png)

fan-in

* 一个类改变，其他关联的类都得改

## 继承深度&宽度：Inheritance Hierarchy Depth & Width

![](/static/2020-11-24-22-41-35.png)
Deep inheritance hierarchies mean that classes at the top of the hierarchy are difficult to modify.

## Other Topics

![](/static/2020-11-24-22-42-04.png)

## Summary

![](/static/2020-11-24-22-42-22.png)

静态分析工具是自动检测错误和不良风格的有效手段，作为持续集成流水线的一部分，这些错误和不良风格可以在代码审查之前被消除。