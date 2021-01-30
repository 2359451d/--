# Lecture 1 - Preamble & Syntax

* [Lecture 1 - Preamble & Syntax](#lecture-1---preamble--syntax)
* [PL定义：What is a Programming Language](#pl定义what-is-a-programming-language)
* [语法&语义：Syntax & Semantics](#语法语义syntax--semantics)
* [设计概念：Design Concepts](#设计概念design-concepts)
* [实现：Implementation](#实现implementation)
* [语法：Syntax](#语法syntax)
  * [语法定义：what is syntax](#语法定义what-is-syntax)
  * [语法规定-正式/非正式规定：Informal vs Formal Specification](#语法规定-正式非正式规定informal-vs-formal-specification)
* [正式规定语法-标记：Notations for Formal Specification of PL Syntax](#正式规定语法-标记notations-for-formal-specification-of-pl-syntax)
  * [正则表达式：Regular Expressions](#正则表达式regular-expressions)
    * [RE基本标记：Basic RE Notation](#re基本标记basic-re-notation)
    * [RE局限性：Limitations of REs](#re局限性limitations-of-res)
    * [Case Study Example：Calc](#case-study-examplecalc)
    * [Shell中RE应用：Application of REs-Unix Shell](#shell中re应用application-of-res-unix-shell)
    * [egrep使用：Application of REs - egrep](#egrep使用application-of-res---egrep)
    * [Java-RE模式匹配：Application of REs - Java pattern Matching](#java-re模式匹配application-of-res---java-pattern-matching)
* [文法：Grammars](#文法grammars)
  * [Mini-English文法例子](#mini-english文法例子)
* [文法，符号，产生式规则：Grammars，Symbols，Production Rules](#文法符号产生式规则grammarssymbolsproduction-rules)
* [正式规定语法-巴科斯范式标记：Notations for Formal Specification of PL Syntax-BNF Notation](#正式规定语法-巴科斯范式标记notations-for-formal-specification-of-pl-syntax-bnf-notation)
  * [BNF表示Calc文法：Calc grammar in BNF](#bnf表示calc文法calc-grammar-in-bnf)
* [短语结构-语法树：Phrase Structure](#短语结构-语法树phrase-structure)
  * [Mini-English 语法树例子](#mini-english-语法树例子)
  * [Calc 语法树例子](#calc-语法树例子)
  * [语法树性质：Property - Syntax Tree](#语法树性质property---syntax-tree)
* [短语：Phrases](#短语phrases)
* [句子&语言：Sentences & Languages](#句子语言sentences--languages)
* [短语结构（语法树）&语义：Phrase Structure & Semantics](#短语结构语法树语义phrase-structure--semantics)
  * [expression结构例子](#expression结构例子)
* [模糊：Ambiguity](#模糊ambiguity)
  * [模糊例子](#模糊例子)
* [扩展巴科斯范式标记：EBNF Notation](#扩展巴科斯范式标记ebnf-notation)
  * [Calc EBNF例子](#calc-ebnf例子)

# PL定义：What is a Programming Language

![](/static/2021-01-23-16-57-40.png)
![](/static/2021-01-23-17-01-22.png)

* PL需要具有通用性，能表示任何计算 **universal**
  * 不支持迭代&递归 - not Universal
* PL对于其领域内的计算表达应为合理自然的 **natrual**
* PL需要为可实现的 **implementable**
  * 只要内存足够，需要能使用该PL运行每个程序
* PL需要负责合理有效实现 **efficient**
  * 运行程序不应该花费大量不合理时间&空间
  * what is reasonable? 取决于上下文

# 语法&语义：Syntax & Semantics

![](/static/2021-01-23-17-27-44.png)

* 语法
  * 关注程序的形式：表达式，命令，声明，其他结构如何组织，形成一个well-formed program
* 语义
  * 良好形式的程序的意义：运行时程序行为
* 语义是编程&语言实现的基础，而语法提供了如何定义语义的结构
  * **没有语法之前无法定义语义**

# 设计概念：Design Concepts

![](/static/2021-01-23-17-32-34.png)

* PL设计中涉及
  * 值 & 类型
  * 变量存储
  * 绑定 作用域
  * 过程抽象
  * 数据抽象
  * 泛型抽象
  * 进程通信

![](/static/2021-01-23-17-34-30.png)

:orange: paradigm 编程范式（编程风格，由核心概念的选择决定）

* **Functional Programming 函数式编程**
  * 关注，值，表达式，函数
* **Imperative programming 命令式（过程式）编程**
  * 变量，命令，过程
* **OO programming**
  * 对象，方法，类
* **Concurrent Programming**
  * 进程，通信

:candy: 对于设计概念&范式的理解，便于我们为项目选择特定PL

# 实现：Implementation

![](/static/2021-01-23-17-38-21.png)

* 用特定PL编写的程序不能直接被机器运行，需要先被**解释器/编译器**处理
* **解释器 interpreter**
  * 一行一行执行程序 runs the given program by fetching, analysing, and executing its ‘instructions’, one at a time.
* **编译器 compiler**
  * 将给定程序从PL（高级语言）转换至低级语言

:candy: understanding of how PL are implemented enables us to be more skilful programmers

# 语法：Syntax

![](/static/2021-01-23-20-59-23.png)

* 正式/非正式规范
* 正则表达式
* 巴科斯范式
* 扩展巴科斯范式
* Case Study：Calc语法

## 语法定义：what is syntax

![](/static/2021-01-23-21-02-18.png)

> 语法：注重程序的形式，如何安排表达式，命令，，其他结构来形成形式良好的程序The syntax of a PL is concerned with the form of programs: how expressions, commands, declarations, and other constructs are arranged to make a well-formed program.

* 学习PL，需要学习其语法
* 需要详细规定PL的语法

:orange:例子

* `if n>0 : write(n)`
* `write(n)` - 需要指定，单个命令或命令序列？
* `n` 指定，变量or任意表达式？

## 语法规定-正式/非正式规定：Informal vs Formal Specification

two way to specify the PL's syntax

![](/static/2021-01-24-17-43-21.png)

* **informal specification**
  * 用自然语言表示（如英语）
* **formal specification**
  * 使用精确标记表示

:orange: 正式规定的优缺点

* pros
  * 更精确
  * 通常更易懂
  * 不会过于模糊，不一致，不完整
* cons
  * 需要对于标记的熟识
  * 有时很难为PL定义正式规定的语法

---

:orange:例子

![](/static/2021-01-24-17-46-57.png)

* 注`command+`表示one or more commands

# 正式规定语法-标记：Notations for Formal Specification of PL Syntax

![](/static/2021-01-24-17-49-06.png)

* **正则表达式** regular expressions （REs）
  * 适用于指定程序中词汇元素的语法（如标识符、字元、注释
* **巴科斯范式** Backus Naur Form （BNF）
  * 适用于指定大型&嵌套结构语法（表达式，命令，迭代，声明）
* **扩展巴科斯范式** Extended Backus Naur Form （EBNF）
  * 前两个标记的结合，good for nearly everything

## 正则表达式：Regular Expressions

![](/static/2021-01-24-17-56-09.png)

* 一条正则，一种模式
* 每个正则匹配一个str集合
  * possibly an infinite set of str

:orange:应用于多种app

* 定义模式串用于匹配搜索
* 定义filename模式串用于FS中搜索
* 指定PL的词法元素语法

---

:orange: 例子

![](/static/2021-01-24-19-15-15.png)

### RE基本标记：Basic RE Notation

![](/static/2021-01-24-19-29-32.png)
![](/static/2021-01-25-01-24-01.png)

* `（RE）` 用于grouping
* `RE?` 空str 或 任意RE
  * 可以扩展表示为基本标记，`RE|''`
* `RE+` 匹配 one or more RE匹配串的拼接
  * 可以扩展表示为基本标记，`RE RE*`

### RE局限性：Limitations of REs

![](/static/2021-01-25-01-49-19.png)

* RE不能完全表示嵌套语句
  * 而PL中表达式/命令都可以嵌套，<font color="blue">因此RE不够足以完全表示一个PL</font>

### Case Study Example：Calc

mini language for calculator

![](/static/2021-01-24-17-53-30.png)

* 包含变量名 `a-z`
* 一个表达式包括：变量，数字，算术操作符
* calc仅有赋值&输出命令

---

![](/static/2021-01-25-01-28-19.png)

* calc的标识符为一个小写字母
  * 可用正式规定-正则表达其标识符语法
* calc数字由 1或多个数字位组成
  * `(‘0’ | ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’) +`

---

:orange: PL例子，标识符由一个多个大写&数字（字母开头）的序列组成

![](/static/2021-01-25-01-32-06.png)

* 使用grouping

### Shell中RE应用：Application of REs-Unix Shell

![](/static/2021-01-25-01-33-22.png)

* shell脚本语言使用 **ad hoc模式匹配标记**
  * `[...]`匹配括号中任意**一个**字符
  * `?` 匹配任意单char
  * `*` 匹配任意长度char （0 or more）
* 这种模式匹配标记是基本RE标记的限制变种，
  * 缺少`RE|RE`，`RE*`标记

:orange:例子

![](/static/2021-01-25-01-35-50.png)

### egrep使用：Application of REs - egrep

![](/static/2021-01-25-01-38-58.png)

* Unix用例`egrep`使用 **full pattern-matching notation**，以下与basic RE标记规定的用法一致
  * `RE|RE`
  * `RE*`
  * `RE+`
  * `RE?`
* 同时提供扩展
  * `[...]` 匹配括号中任意1char
  * **`.` 匹配任意1char**

---

:orange: 例子

![](/static/2021-01-25-01-45-07.png)

### Java-RE模式匹配：Application of REs - Java pattern Matching

![](/static/2021-01-25-01-46-36.png)

* 某些java类也使用**full pattern matching notation**（其中支持的扩展与`egrep`一致）
  * `[...]` 匹配括号内任意1char
  * `.` 匹配任意单char

# 文法：Grammars

![](/static/2021-01-25-02-03-08.png)

* 已知RE不能用于表示PL的嵌套词语，**为了规定嵌套表述（嵌套表达式/命令），需要定义一套文法（context-free grammar）**

> 一门语言的文法是**一套**规定该语言的短语如何形成的**规则**。 The grammar of a language is a set of rules specifying how the phrases of that language are formed.
> **每条规则**都规定了如何用符号（如单词和标点符号）和较简单的短语组成每个短语 Each rule specifies how each phrase may be formed from symbols (such as words and punctuation) and simpler phrases
> 即，描述语言的语法结构的形式规则称为文法。（组合成语言的规则）

## Mini-English文法例子

![](/static/2021-01-25-02-07-04.png)
![](/static/2021-01-25-02-11-17.png)

* 形成的简单句子中
  * 每个word/punctuation称为 **terminal synmbols 终结符**
* 其文法中使用以下符号表示其phrase
  * 称为**nonterminal symbols 非终结符**
* 其文法规则规定
  * （sentence symbol）`sentence = subject verb object '.'` sentence is consists of subject, followed by verb,followed by object then terminated with dot (terminal symbol)
  * （nonterminal symbol）`subject = 'I'|'a' noun | 'the' noun` I（alone） or a followed by noun or 'the' followed by noun （'the' is the terminal symbol？？）

---

根据其文法规则，可以树状结构（syntax tree语法树）形成其sentences

![](/static/2021-01-25-02-17-23.png)

# 文法，符号，产生式规则：Grammars，Symbols，Production Rules

![](/static/2021-01-25-02-21-36.png)

* **context-free grammar**由以下组成
  * 终结符集合 **terminal symbols**
    * 可能在句子中出现的**符号**
  * 非终结符集合 **nonterminal symbols**
    * 指代形成句子中某部分的**短语**
  * 句符 **sentence symbol**
    * 用非终结符表示的**完整句子**
  * 产生式集合 **production rules**
    * 每个产生式规则规定了如何用**终结符&子短语构成一个短语**
    * a production rule can be the combination of terminal & nonterminal symbol

# 正式规定语法-巴科斯范式标记：Notations for Formal Specification of PL Syntax-BNF Notation

![](/static/2021-01-24-17-49-06.png)

* **正则表达式** regular expressions （REs）
  * 适用于指定程序中词汇元素的语法（如标识符、字元、注释
* **巴科斯范式** Backus Naur Form （BNF）
  * 适用于指定大型&嵌套结构语法（表达式，命令，迭代，声明）
* **扩展巴科斯范式** Extended Backus Naur Form （EBNF）
  * 前两个标记的结合，good for nearly everything

---

![](/static/2021-01-25-02-31-39.png)

* 使用**BNF（Backus Naur Form**）表示文法grammar
* 用BNF表示最简单的**产生式**
  * `N = α`
  * `N` - 非终结符
  * `α` - 终结&非终结符序列

:orange: BNF表示产生式规则可以有多种方式

![](/static/2021-01-25-02-37-31.png)

* `N = α | β | γ`
  * 其中`α | β | γ`都为终结&非终结符组合的序列
* subject产生式规则例子
  * `subject = ‘I’ | ‘a’ noun | ‘the’ noun`

## BNF表示Calc文法：Calc grammar in BNF

:orange: calc文法由以下组成

![](/static/2021-01-25-02-44-42.png)
![](/static/2021-01-25-02-48-38.png)

* 终结符
  * 每个word，punctuation
* 非终结符（短语）
  * prog
  * com
  * expr
  * prim
  * num
  * id
* 句符
  * prog
* 产生式规则
  * prog = eof | com prog
  * com = 'put' expr eol(end of line) | 'set' id '=' expr eol
  * ...

# 短语结构-语法树：Phrase Structure

![](/static/2021-01-25-02-51-40.png)

> 文法定义了PL中如何用子短语(nonterminal symbol，最上面的)形成短语（最下面的所有的节点） --- **短语结构**
> A grammar defines how phrases may be formed from sub-phrases in the language. This is called phrase structure.

:orange: 语言中，每个短语都有**语法树**，明确**表示其短语结构** Every phrase in the language has a syntax tree that explicitly represents its phrase structure.

## Mini-English 语法树例子

![](/static/2021-01-25-02-54-33.png)

* sentence句符下面的都是nonterminal symbol（短语）
  * 该语法树描述了短语的结构
* root 是sentence
  * followed by the production rule of sentence, is subject, verb, object,...

## Calc 语法树例子

![](/static/2021-01-25-02-56-51.png)
![](/static/2021-01-25-02-59-13.png)

## 语法树性质：Property - Syntax Tree

![](/static/2021-01-25-03-00-23.png)

* 任意文法`G`，其语法树（表示其短语结构）是有以下性质的树
  * 每个终止节点都用终止符表示 – Every terminal node is labeled by a terminal symbol of G
  * 每个非终止节点都用非终止符表示 – Every nonterminal node is labeled by a nonterminal symbol of G
  * 仅当`G`有产生式规则`N= X Y Z`或`N = |X Y Z|`时，非终止节点`N`有多个孩子 `X,Y,Z`(alternatives表示)

# 短语：Phrases

![](/static/2021-01-25-03-06-39.png)

* 如果`N`是文法`G`的非终止符，则`N`类短语（class of N）即语法树中根为`N`的终止符节点组成的字符串 （注意一个phrase是个完整的str）
  * 终止节点访问顺序：左->右
  * 比如`N = X Y Z`, X,Y,Z整单独一个是一个短语
* <font color="deeppink">找个合适节点，下面衍生节点一起看为一个`N`类短语</font>
  * 比如句符，`S`看为一个非终止符节点，文法`G`的所有其他句子都为该节点`S`类的短语（衍生）

# 句子&语言：Sentences & Languages

![](/static/2021-01-25-03-13-22.png)

* 如果`S`是文法`G`的句符，则`G`的句子都为`S`类短语
  * ‘set n = 42 \n put x*(22-y) \n’ is a （prog class）sentence of Calc

:orange: 语言 languages

* 通过文法`G`生成的语言 --- 即，`G`的所有句子的集合
  * <font color="deeppink">注意：`G`文法生成的语言通常无穷大（即使`G`本身包括的规则&符号有限）</font>

---

# 短语结构（语法树）&语义：Phrase Structure & Semantics

> 通过文法`G`生成的语言 --- 即，`G`的所有句子的集合。【这个对于语言的定义，**限制于语法方面**（如何构成x语法，如何构成某命令，专注于短语的结构）】

![](/static/2021-01-25-03-18-51.png)

* 我们还应关注**语言的【语义】 --- 每个句子的含义**
* 已知，文法不仅仅用于产生句子集合（构成语言），文法也规定了每个句子的短语结构（nonterminal symbol为root的语法树）
* <font color="deeppink">一旦知道某句子的语法树（短语结构），就可以为该句子赋予特定含义</font> Once we know a sentence’s phrase structure, we can use it to ascribe a meaning to that sentence

## expression结构例子

![](/static/2021-01-25-03-25-43.png)
![](/static/2021-01-25-03-26-26.png)

* 注意无括号情况下，所有expr的children优先级相同，执行从左到右

![](/static/2021-01-25-03-32-09.png)

* 有括号情况需要遵循优先级

---

![](/static/2021-01-25-03-33-17.png)
![](/static/2021-01-25-03-34-22.png)

* 该文法用于大多PL中（java，c），短语结构与先前不同
  * 因为term最优先，而term又可以包含乘法表示，所以总是优先执行`*`

# 模糊：Ambiguity

![](/static/2021-01-25-03-36-33.png)

* 短语模糊
  * 如果某**短语**有多于`1`个语法树
  * 即，构成某相同特定短语，方式不确定。比如一个句子（句符`S`类短语），有2种方式/语法树可以构成该句子
* 文法模糊
  * 如果其任意一个短语是模糊的

:orange: 自然语言模糊很常见，如英语

:orange: PL文法不应该模糊，否则某些程序的含义可能不确定

## 模糊例子

![](/static/2021-01-25-03-39-47.png)
![](/static/2021-01-25-03-43-02.png)

* `G`文法，相同1个句子（`S`类句符的短语）有两个语法树可以构成该句子 --- 模糊

# 扩展巴科斯范式标记：EBNF Notation

![](/static/2021-01-25-03-44-41.png)

:orange:**Extended Backus Naur Form (EBNF** )

* 结合BNF& RE标记
* **产生式规则**
  * `N=RE`
  * `N` - 非终结符
  * `RE` - 正则，**由终结&非终结符号表示**（其正则，BNF是用组合的序列表示产生式）

:orange: EBNF标记适用于规定所有语法

## Calc EBNF例子

![](/static/2021-01-25-03-48-26.png)
![](/static/2021-01-25-03-48-31.png)

* 注意正则，字符单引号