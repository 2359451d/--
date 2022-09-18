# L5 - Syntactic analysis

![](/static/2021-03-06-14-07-09.png)

* Aspects of syntactic analysis
* Tokens
* Lexer
* Parser
* Applications of syntactic analysis
* Compiler generation tool ANTLR
* Calc
* Fun syntactic analyser

* [L5 - Syntactic analysis](#l5---syntactic-analysis)
* [语法分析：Aspects of syntactic analysis](#语法分析aspects-of-syntactic-analysis)
* [令牌：Tokens](#令牌tokens)
  * [例子-Case study: Calc tokens](#例子-case-study-calc-tokens)
* [Separators](#separators)
* [词法分析器：Lexer](#词法分析器lexer)
* [语法分析器：Parser](#语法分析器parser)
  * [递归下降解析：Recursive descent parsing](#递归下降解析recursive-descent-parsing)
  * [mathch(t)方法](#mathcht方法)
  * [N() 方法](#n-方法)
  * [例子：Calc Parser](#例子calc-parser)
    * [Calc - Command文法 分析过程](#calc---command文法-分析过程)
    * [Calc - Prog文法 分析过程](#calc---prog文法-分析过程)
  * [递归下降解析的一般文法匹配规则: General rules for recursive-descent parsing](#递归下降解析的一般文法匹配规则-general-rules-for-recursive-descent-parsing)
    * [匹配终结 & 非终结符](#匹配终结--非终结符)
    * [匹配其他RE](#匹配其他re)
* [Applications of syntactic analysis](#applications-of-syntactic-analysis)
* [编译器自动生成工具：Compiler Generation Tools](#编译器自动生成工具compiler-generation-tools)
  * [ANTLR编译组件自动生成工具 - Compiler Generation Tool](#antlr编译组件自动生成工具---compiler-generation-tool)
    * [ANTLR符号 - 编写Calc语法：Case Study - Calc Grammar in ANTLER](#antlr符号---编写calc语法case-study---calc-grammar-in-antler)
  * [例子：Calc Driver](#例子calc-driver)
  * [遍历解释语法树-执行程序：Interpretation by syntax tree traversal](#遍历解释语法树-执行程序interpretation-by-syntax-tree-traversal)
  * [用访问者模式遍历语法树：Syntax Tree Traversal Using a Visitor](#用访问者模式遍历语法树syntax-tree-traversal-using-a-visitor)
    * [Case study: a visitor for Calc](#case-study-a-visitor-for-calc)
  * [引入ExecVisitor后驱动的更新](#引入execvisitor后驱动的更新)
* [Fun文法：Fun grammar in ANTLR](#fun文法fun-grammar-in-antlr)

# 语法分析：Aspects of syntactic analysis

![](/static/2021-03-06-15-00-28.png)

句法分析检查源程序的形成是否良好，并确定其短语结构 Syntactic analysis checks that the source program is well formed and determines its phrase structure

句法分析可以分解为 Syntactic analysis can be decomposed into

* **lexer** 词法分析器
  * 将字符序列转换为标记（token）序列的过程
  * **breaks the source program down into tokens**
* **parser** （语法分析器）解析器
  * 确定源程序的短语结构 determines the phrase structure of the source program

---

![](/static/2021-03-06-15-03-35.png)

前略，语法分析阶段的语法分析器输入源程序，输出AST The syntactic analyser inputs a source program and outputs an AST

* zoom in
  * **词法分析器输入源程序，如果没有词法错误，输出令牌流给语法分析器** the lexer channels a stream of tokens to the parser

# 令牌：Tokens

![](/static/2021-03-06-15-25-43.png)

令牌Tokens是影响源程序短语结构的文本符号 Tokens are textual symbols that influence the source program’s phrase structure

* literals 字面值
* identifiers 标识符
* operators 操作符
* keyword 关键字
* punctuation 标点符号（parentheses, commas, colons, etc 括号逗号分号）

:orange: **每个token都有tag & text**

- the addition operator might have tag PLUS and text ‘+’
– a numeral might have tag NUM, and text such as ‘1’ or ‘37’
– an identifier might have tag ID, and text such as ‘x’ or ‘a1’

## 例子-Case study: Calc tokens

![](/static/2021-03-06-15-33-23.png)
![](/static/2021-03-06-15-34-24.png)

how it translated into tokens

# Separators

![](/static/2021-03-06-15-31-34.png)

**分隔符是不影响短语结构的文本片段** Separators are pieces of text that do not influence the phrase structure

* **空格** spaces
* **注释** comments

EOL

* 大多PL中的separator
* 是python中的token，因为用于分离命令

# 词法分析器：Lexer

![](/static/2021-03-06-15-46-14.png)

**词法分析器 lexer 将源码转为令牌流** lexer converts source code into a token stream

**在每个步骤中，lexer 检查源代码的下一个字符并采取相应的行动** At each step, the lexer inspects the next character of the source code and acts accordingly

**当没有源代码时，lexer 输出一个 EOF 标记**。 When no source code remains, the lexer outputs an EOF token.

---

源码 & 采取的对应行动 if the next character of the source code is...

![](/static/2021-03-06-15-48-43.png)

* 源码下一字符为
  * 空格
    * 忽略
  * 注释起始
    * 扫描剩余注释，忽略
  * 标点
    * **输出token**(tag & text)
  * 字母
    * 扫描剩余字母，**输出token**（tag & text-identifier）

# 语法分析器：Parser

![](/static/2021-03-06-15-49-58.png)

**语法分析器将token流转为AST** The parser converts a token stream into an AST

* 分析分为两种，对应解析树如何生成
  * TOP-DOWN （Recursive descent parsing, backtracking）
  * Bottom-UP
* **递归下降解析是常见的，而且特别简单: 它使用递归过程来处理令牌流** Recursive-descent parsing is common and particularly simple: it uses recursive procedures to process the stream of tokens
  * **给定一种适合源语言的语法，我们可以系统地为它编写一个 RD 解析器(语法分析器)** Given a suitable grammar for the source language, we can systematically write a RD parser for it
  * 给定一种适合源语言的语法，我们可以系统地为它编写一个 RD 解析器(语法分析器)， 比如给定FUn语法，可以通过自动工具生成一个Fun的语法分析器（用来从源码中抽象出一个AST）
    * 仅编译过程第一阶段

## 递归下降解析：Recursive descent parsing

![](/static/2021-03-06-15-53-15.png)

递归下降**语法分析器**包括 recursive-descent parser consists of

* **一组解析方法 `N ()` ，对应源语言文法中的每个非终结符号N** a family of parsing methods N(), one for each nonterminal symbol N of the source language’s grammar
* **辅助方法`match()`** an auxiliary method match()
* 这些方法从左到右“使用”令牌流
  * **即lexer产生的Tokens（Tag & text）作为输入**

---

![](/static/2021-03-06-15-56-45.png)

## mathch(t)方法

**用于终止符？**

**mathch(t)检测下一个token, 是否包含tag `t`** Method `match(t)` checks whether the next token has tag `t`

* **token包含tag `t`**, 使用该token yes - consumes the token
* **token不包含tag `t`**，出现语法错误（syntactic analysis - parser）  no - report a syntactic error

## N() 方法

**每个非终止符N对应的N(),检查后几个tokens是否组成N类词语** For each nonterminal symbol N, method N() checks whether the next few tokens constitute a phrase of class N

* **token包含tag `t`**, **使用该token， 返回解析后短语的AST** yes, consumes tokens, returns an AST representing the parsed phrase
* **token不包含tag `t`**，出现语法错误 no, reports a syntactic error

## 例子：Calc Parser

![](/static/2021-03-06-16-00-42.png)

* AST中每个非终结符对应的方法 `N()`
  * 检查后几个tokens能否组成该非终结符节点
    * 即该节点子节点Token能否构成

![](/static/2021-03-06-16-01-22.png)

* N(), how the parsing methods work?

### Calc - Command文法 分析过程

![](/static/2021-03-06-16-10-26.png)

EBNF production rule for commands & parsing method for commands **Calc命令的产生式（EBNF表示**）

* Command AST如何生成？
* terminal symbol - match()
* non-terminal symbol - N()

![](/static/2021-03-06-16-10-55.png)

* match 先检测下一token是否为PUT
* expr() 检测后几个token是否构成expr
* match 检测下一token是否为 EOL

![](/static/2021-03-06-18-35-55.png)

* 下一token是否为SET
* 后几个token是否构成var
* 下一token是否为ASSN
* 后几个token是否构成expr
* match 检测下一token是否为 EOL

### Calc - Prog文法 分析过程

EBNF production rule for programs
![](/static/2021-03-06-19-19-01.png)

* while 下一token为 SET/PUT
* 后几个token是否构成com()
* 最后token是否为 EOF

## 递归下降解析的一般文法匹配规则: General rules for recursive-descent parsing

![](/static/2021-03-06-19-21-51.png)

---

### 匹配终结 & 非终结符

![](/static/2021-03-06-19-22-27.png)

* 终结符terminal symbol - match()
  * **文法中终结符节点，检测下一token是否含有 tag（即该终结符tag）** `t`
* nonterminal symbol - N()
  * **文法中非终结符节点，检测后几个token是否构成该短语**（节点）

### 匹配其他RE

![](/static/2021-03-06-19-22-58.png)

* RE1 RE2 （concate）拼接
  * 下一token是否匹配 RE1 match RE1
  * 下一token是否匹配 RE2 match RE2
* RE*
  * 下一token是否为可以匹配RE的token条件 if next token can start RE
  * 下一token是否为RE then matchRE

![](/static/2021-03-06-19-24-02.png)

* RE1 | RE2
* 只适用于 RE1 & RE2不能同时成立时，且产生式不能是左递归的

# Applications of syntactic analysis

![](/static/2021-03-06-19-25-58.png)

语法分析有多种应用。
- 在编译器中 compilers
  - **属于第一个阶段，生成抽象语法树AST**
- 在XML应用中（解析XML文档和
将其转换为树形）。) xml applications
- 在网络浏览器中（解析和渲染HTML）。
文件) in web browsers
- 在自然语言应用中（解析和
翻译NL文件）。) in natural language applications

# 编译器自动生成工具：Compiler Generation Tools

![](/static/2021-03-06-19-28-11.png)

**编译器生成工具可以自动构建编译器组件的过程**。 A compiler generation tool automates the process of building compiler components

* 编译器生成工具的输入是对编译器组件应该做什么的说明 The input to a compiler generation tool is a specification of what the compiler component is supposed to do
  * **语法生成器的输入是一个语法/文法**。 The input to a parser generator is a grammar.
  * **通过输入语言文法，生成一个编译器所需要的组件（如lexer，parser等？**）

例子

* lex yacc
* javacc
* antlr

## ANTLR编译组件自动生成工具 - Compiler Generation Tool

![](/static/2021-03-06-19-31-58.png)

**ANTLR从(.g4)语法开始，自动生成一个词法分析器(lexer)和一个递归递减解析器(parser)，并允许建立和遍历一个解析树**。 ANTLR automatically generates a lexer and a recursive-descent parser, starting from a grammar (.g4), and it allows to build and walk a parse tree

* **即编写一套文法/语法，`.g4`文件，可以通过ANTLR自动生成编译器组件（语法分析阶段的lexer，parser**）
* 我们首先用ANTLR符号（类似于EBNF）来表达源语言的语法。 we start by expressing the source language’s grammar in ANTLR notation (which resembles EBNF)
  * 需要学习 ANTLR符号（`.g4`文件）来编写表示语法

---

![](/static/2021-03-06-19-59-04.png)

ANTLR由两个部分组成

* tool
  * **java编写的工具，用于生成词法分析器&语法分析器** generate the lexer & parser
    * java program
  * **运行antlr tool时可以指定目标组件的语言，默认java，生成java特定语法分析器**
* runtime
  * run them

:orange: ANTLR可以默认生成监听器 & 访问者， 是实现上下文分析器 & 代码生成器的基础 ANTLR can generate listeners (by default) and visitors, which are the basis for implementing a contextual analyser and a code generator

* lexer&parser基于 listener & visitor 模式

### ANTLR符号 - 编写Calc语法：Case Study - Calc Grammar in ANTLER

![](/static/2021-03-06-20-07-34.png)

ANTLR符号规则

* **lexer rules（at the end of the file） - uppercase**
  * 用于生成tokens？TAG & Text
  * 全部大写，放在文件末尾
  * 由terminal symbol组成
* **parser rules - lowercase**
  * 用于抽象出AST
  * 全部小写
* **syntax of a antlr production rule**
  * name
  * colon
  * definition of the production rule
  * terminating semicolon

```txt
grammar Calc;

prog
  : com* EOF
  ;

com
  : PUT expr EOL
  | SET var ASSN expr EOL
  ;
```

:orange: Calc文法 - ANTLR符号表示

![](/static/2021-03-06-21-22-24.png)

* parser rule?

![](/static/2021-03-06-22-24-10.png)

* Token & 分隔符 - 大写 （lexer rule）

## 例子：Calc Driver

![](/static/2021-03-06-22-25-14.png)

**ANTLR从(.g4)语法开始，自动生成一个词法分析器(lexer)和一个递归递减解析器(parser)，并允许建立和遍历一个解析树**

* ANTLR TOOL 生成 lexer & parser
* Class `CalcLexer`
  * **包含转换源码流为令牌流的方法** contains methods that convert an input stream (source code) to a token stream.
* Class `CalcParser`
  * **包含使用令牌流的解析方法**（token->AST） contains parsing methods prog(), com(), …, that consume the token stream.
    * `match()`
    * `N()`

---

写一个driver program 驱动程序 class `CalcRun`，调用 `CalcParser`类的 `prog()`方法

* 即调用 解析方法处理 之前生成的Calc令牌
  * match token
    * yes - AST
    * no - report error

![](/static/2021-03-06-22-53-24.png)

![](/static/2021-03-06-22-53-43.png)

* 编译和运行时，`CalcRun`对源程序进行语法分析，报告任何语法错误。 When compiled and run, CalcRun performs syntactic analysis on the source program, reporting any syntactic errors
* 并且过程中创建AST It also constructs a syntax tree
  * The syntax tree can be viewed with the ANTLR TestRig tool, which is described in the assessed coursework specification
* **`CalcRun`本身不执行程序** does not execute the program.

## 遍历解释语法树-执行程序：Interpretation by syntax tree traversal

![](/static/2021-03-06-22-58-51.png)

程序的执行可以通过遍历（走过）语法树，从左到右，深度优先，解释命令 The program can be executed by traversing (walking over) the syntax tree, left to right, depth first, and interpreting the commands.

## 用访问者模式遍历语法树：Syntax Tree Traversal Using a Visitor

![](/static/2021-03-07-04-23-41.png)

通过ANTLR TOOL 自动生成语法树，但是需要遍历树&分析其结构来**生成解析器(interpreter) & 编译器(compiler-lexer, parser,...)** We get a syntax tree automatically, but to implement an interpreter or compiler we need to walk over the tree and analyse its structure

* 我们**使用抽象语法树（AST）进行解释**，但在ANTLR4中，**我们使用的是完整的语法树，包含所有的令牌** We use abstract syntax trees (ASTs) for explanation, but in ANTLR4 we work with full syntax trees, containing all the tokens
  * **ANTLR通过访问者模式定义树节点访问器** ANTLR uses the Visitor pattern to define tree walkers
    * 访问分析所有结构节点后 - 生成 interpreter & compiler
  * （It is possible to use the Listener pattern instead

### Case study: a visitor for Calc

![](/static/2021-03-07-04-41-49.png)

* `CalcVisitor` 接口
  * **为每个语法树节点定义`visit()`方法，用于访问**
    * `visitProg(CalcParser.ProgContext ctx)`

![](/static/2021-03-07-04-42-58.png)

* `CalcBaseVisitor`实现类（实现`CalcVisitor`接口，继承`AbstractParseTreeVisitor`抽象类），**遍历整个语法树，无其他特殊行为**）
  * 实现 `visitProg(CalcParser.ProgContext ctx) { return visitChildren(ctx)}`
    * `visitChildren(...)` 定义在 `AbstractParseTreeVisitor`返回 `null`

---

![](/static/2021-03-07-05-01-43.png)

* 假设`CalcRun`进行实际计算（其本身是不执行程序的） perform actual calculations
  * `put expr`文法 - 计算表达式 `expr`，打印结果
  * `set var=expr`文法 - 计算表达式 `expr`， 存储结果至变量 `var`

:orange: `ExecVisitor`继承`CalcBaseVisitor`访问者基类，

![](/static/2021-03-07-05-37-45.png)

* **覆写了`visit()`方法， 因此遍历节点时会执行特定行为， 即。遍历语法树时解释程序** they interpret the program while walking over the syntax tree
  * 原visit()只是访问节点

`visitPut(CalcParser.PutContext ctx)`

* 计算表达式 & 打印结果

`visitSet(CalcParser.SetContext ctx`

![](/static/2021-03-07-17-04-14.png)

* `set var=expr`
* 存储表达式计算结果（先获取地址）

`visitOp(CalcParser.OpContext ctx)`

![](/static/2021-03-07-17-04-47.png)

* switch the type of operator

![](/static/2021-03-07-17-07-09.png)

* `visitNum`
* `visitId`
* `visitParens`
  * Parentheses have no computational significance; they just influence the structure of the syntax tree.
  * `ctx.expr()`

## 引入ExecVisitor后驱动的更新

![](/static/2021-03-07-17-10-16.png)

驱动需要扩展，创建调用 `ExecVisitor`（访问AST节点时会执行计算）

---

新的 `CalcRun`驱动类class，**可以进行语法分析 （抽象AST）& 程序解释（执行计算）**

* `java CalcRun test.calc`

# Fun文法：Fun grammar in ANTLR

* **syntax of a antlr production rule**
  * name
  * colon
  * definition of the production rule
  * terminating semicolon
* lexer rules大写（放末尾），parser rules小写

![](/static/2021-03-07-17-14-09.png)
![](/static/2021-03-07-17-14-15.png)
![](/static/2021-03-07-17-15-12.png)

---

自动化工具生成lexer & parser

![](/static/2021-03-07-17-15-37.png)

* Fun文法（ANTLR符号表示），放于文件`Fun.g4`
* 启用ANTLR Tool生成lexer & parser
  * `java org.antlr.v4.Tool Fun.g4`
* 会自动生成以下类
  * `FunLexer` 将源码(输入流)转换为token流
  * `FunParser` 包含解析方法 `prog()`, `var_decl()`, `com()`,...将token流抽象生成AST短语
* `prog()`(CalcBaseVisitor) - 抽象全AST树
