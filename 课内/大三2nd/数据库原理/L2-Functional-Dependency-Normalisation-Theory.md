# Functional Dependency & Normalisation Theory

Relational Design

![](/static/2021-01-29-17-37-29.png)

* objective function to assess whether the proposed relational schema is good or bad
  * 针对查询优化方面评判基模效率 judge the efficiency of the schema with respect to the query optimization
* 使用 **Functional Dependency Theory**
  * 量化评判schema的好坏程度
* 使用 **Normalization Theory**
  * 定义，如何 transforming a relational schema into a set of good and efficient relations
* 使用 **Boyce-Codd Normal Form(BCNF)**
  * 将任意relation转换成高效relation，避免生成<font color="deeppink">fictitious tuples</font>时涉及的**fundamental problems**
  * <font color="blue">BCNF fundamental theorem - 泛化标准化步骤</font>

* [Functional Dependency & Normalisation Theory](#functional-dependency--normalisation-theory)
* [关系型模型评判标准：Assessment Guidelines & Performance Metrics](#关系型模型评判标准assessment-guidelines--performance-metrics)
* [函数依赖理论: Theory of Functional Dependency](#函数依赖理论-theory-of-functional-dependency)
  * [例子：存在函数依赖的意义](#例子存在函数依赖的意义)
  * [函数依赖定理&性质：Functional Dpendency Principles](#函数依赖定理性质functional-dpendency-principles)
    * [函数依赖性质应用例子](#函数依赖性质应用例子)
* [使用函数依赖 & 标准化步骤：Functional Dependency & Normalization](#使用函数依赖--标准化步骤functional-dependency--normalization)
* [标准化理论：Theory of Normalization](#标准化理论theory-of-normalization)
  * [主属性 & 非主属性：Prime Attribute & Non-Prime Attribute](#主属性--非主属性prime-attribute--non-prime-attribute)
* [First Normal Form (1NF)](#first-normal-form-1nf)
  * [冗余例子](#冗余例子)
* [Second Normal Form (2NF) & 完全函数依赖 & 部分函数依赖：Full FD](#second-normal-form-2nf--完全函数依赖--部分函数依赖full-fd)
  * [完全函数依赖 & 部分函数依赖](#完全函数依赖--部分函数依赖)
  * [2NF Second Normal Form - 选取造成部份依赖的冗余主属性](#2nf-second-normal-form---选取造成部份依赖的冗余主属性)
  * [例子](#例子)
* [1-2NF后的问题识别例子](#1-2nf后的问题识别例子)
* [THIRD NORMAL FORM (3NF) & 传递函数依赖：Transitive Functional Dependency](#third-normal-form-3nf--传递函数依赖transitive-functional-dependency)
  * [选取分解属性【造成传递依赖的非主传递属性】：方法 Methodology](#选取分解属性造成传递依赖的非主传递属性方法-methodology)
* [3NF例外：Generalized Third Normal Form 3NF](#3nf例外generalized-third-normal-form-3nf)
* [BOYCE-CODD NORMAL FORM (BCNF)](#boyce-codd-normal-form-bcnf)
  * [如何选取分解属性？](#如何选取分解属性)
  * [BCNF分解定理：BCNF Decomposition Theorem](#bcnf分解定理bcnf-decomposition-theorem)
  * [分解例子](#分解例子)

# 关系型模型评判标准：Assessment Guidelines & Performance Metrics

![](/static/2021-01-29-17-50-49.png)

:orange: Performance Metrics

* **goodness**
  * 评判relation的PK，FK
* **quantify the degree of goodness**
  * 需要涉及标准化过程，normalization process
* **what pitfalls exist** (of the proposal relational model)
* **efficiency** in CRUD operations
* **whether our relational schema conveys much information as possible** by <font color="deeppink">minimizing any inherent redundancy</font>
  * 最小化冗余&不确定性 repetitions of data & uncertainty

---

:orange: Guidelines for a good design

![](/static/2021-01-29-18-01-36.png)

> Guideline 1: The attributes of a relation should make sense
> **准则1。关系的属性应该有意义（引用，标识，说明）**

* 不同实体的属性不应（以普通属性形式）出现在同一relation中
  * **最小化relations之间的相似度**

:candy:如需关联两个实体，只使用FK &PK进行关联

---

![](/static/2021-01-29-18-06-54.png)

> Guideline 2: Avoid redundant tuples(repetition of the same information)
> **准则2.避免冗余的元组**（重复相同的信息)

:orange: 冗余影响 Impact of repetition

* 存储开销 storage cost
  * 存储冗余数据，浪费
* 不一致 & 操作异常 inconsistency cost & operation anomalies
  * CUD操作时，冗余值也应保持数据的一致，通常涉及更多的检查

:candy:例子 replicas result to consistency anomalies 重复值造成一致性异常（redundacy is the source of operation anomalies）

* 如果在多个server上进行值复制，后续删除，需要强制对其他server进行级联删除操作，以避免冗余
* 或进行值的更新，则需要对其他server进行更新传播，以避免数据不一致

:candy: 例子2 redundancy & anomaly

![](/static/2021-01-29-18-16-01.png)

:orange: 更新异常

![](/static/2021-01-29-18-21-12.png)

* 假设600个Employee工作于同一个project（number2，nameY）
  * 过程中如果只修改project2的name这一个属性，就需要涉及600次更新，以避免数据不一致（否则，employee可能工作于一个不存在的project，relation出现不一致情况）

:orange: 删除异常

![](/static/2021-01-29-18-23-58.png)

* 如果多个employee工作于project2，而project2数据被删除
  * 会造成相关数据也需要删除（避免数据不一致），但实践中更倾向于将这些employee分配至其他project而不是直接删除其数据

---

![](/static/2021-01-29-18-26-33.png)

> Guideline 3: Relations should have as a few NULL values as possible:
> **准则3。关系应尽量少用NULL值**

:orange: 何时使用 NULL？ reasons for NULL

* 值不适用，无效 a value is not applicable or invalid
* 值未知 a value is unknown (may exist; who knows?)
* 值已知，但当前不可用 a value is known to exist, but unavailable

:candy: 如果某属性涉及大量的NULL值，应该与原relation分离，**避免存储浪费&减少不确定性** Statistics: Attributes that are frequently NULL should be placed in separate relations to **avoid wasting storage & reducing uncertainty**!

:candy: 例子

![](/static/2021-01-29-18-31-30.png)

* 3个字段存储电话号码
  * 只有10人（1600人中）3个字段都有值，意味着phone3包含大量`NULL`值 --- **大量冗余 & 不确定性 huge redundancy & huge uncertainty**
* 重新设计
  * 移除phone3，定义一个新relation，（reflection from huge uncertainty），引用PK作为FK与原relation进行关联
  * 3rdPhone仅需要存储10条数据，并且减少了原relation中的不确定性（大量`NULL`）

---

![](/static/2021-01-29-18-37-08.png)

> Guideline 4: Design relations to avoid fictitious tuples after join
> 准则4: 设计关系以避免join后的虚构元组

:orange: **fictitious tuples【fundamental problem】** - 不存在于原relation的tuples

* 针对bad design（relational schema）
  * 通常使用分治法，divide-conquer，将原坏设计的relation分成2个子subrelation，**使用共同属性（专注思考FK，PK的设计）将他们关联 using common attribute to associate them and to recover all information**
* 后续query需要将relation左连/右连/自连完成
  * 但是**可能会产生fictitious tuples（如果common 属性选择的不好）**（原relation中不存在的tuples）

:candy:例子

![](/static/2021-01-29-19-27-25.png)
![](/static/2021-01-29-21-09-27.png)

* 此例，原relation `R` 分成两个subrelation `Q & P`
  * 随机选择一个共同关联属性 `Plocation`

:orange: 如果我们想聚合两个subrelation（to recover the information），获取原来的relaiton `R`

* 需要对共享的共同属性应用join算法
  * 由此relation-subrelation的重构，我们至少避免了operation anomalies问题

:candy: <font color="deeppink">涉及的问题 - 如何最佳选择一个共同属性用来join relation，以后续连接不会出现fictitious tuples？</font>

* 需要利用theory来找到不会产生虚构元组的最佳关联属性(join attr) find the best splitting attribute that does not generate fictitious tuples…a Theory is imperative! --- 【**Functional Dependency Theory**】

# 函数依赖理论: Theory of Functional Dependency

![](/static/2021-01-29-21-14-26.png)

> Functional Dependency (FD): 是判定relational schema 好坏程度的标准方法，它反映的是relation 中属性的内在关系，是一种**约束**
> Functional Dependency (FD) is a formal metric of the degree of goodness of relational schema:
>
> * FD is a **constraint** derived from the relationship between attributes

:orange: 定义

* *“Given a relation, an attribute X functionally determines an attribute Y, if a value of X determines a unique value for Y.”* "给定一个关系，一个属性X在功能上决定一个属性Y，如果X的一个值决定Y的一个唯一值。"
* In other words: give me a value of X and I’ll tell you which is the value of Y in a specific tuple! (X determines Y) **给定一个x值，能确定一个特定tuple中的y值，即用X的值来确定Y的值**

![](/static/2021-01-29-21-19-36.png)

:orange: 标记 - `FD：X->Y`

* `->`表示functional dependency
  * `X uniquely determines Y`
* <font color="deeppink">如果`FD：X->Y`成立(functional dependency exists)，则relation中所有tuples中对应的属性值都存在functional dependency</font>

:orange: 对于任意两个tuples，`t1` & `t2`

* 如果 `t1[X] =t2[X]` 那么 `t1[Y] =t2[Y]`
  * <font color="deeppink">如果已知存在`X->Y`的函数依赖functional dependency，则如果两个tuples 具有相同的`X`值，其`Y`值也应该相同</font> if there exists a functional dependency from attribute x towards y, then if 2 tuples have the same value in attribute x, then they should have the same value in attribute y
* 对于每一个X的值，只有唯一 一个 Y值与其对应

:orange: **函数依赖是一种约束**

* X → Y in R specifies a **constraint** on all instances, i.e., principle.
  * <font color="red">即，`X->Y`函数依赖对所有tuples定义了约束</font>

---

:candy: 存在函数依赖意义：

1. X属性用于唯一确定Y属性
   1. X可为composite attributes...
2. 为relation中所有tuples定义相关函数依赖约束
3. 可以通过已知函数依赖，得出 PK 与 非PK之间的函数依赖

## 例子：存在函数依赖的意义

![](/static/2021-01-29-21-31-11.png)
![](/static/2021-01-29-21-41-01.png)

* `FD：SSN->Ename` - 两字段存在函数依赖
  * SSN determines Ename
  * 如果后续tuple 存在`SSN=1`，那么其 `Ename`一定为`Ename=‘Chris’` --- 对relation中所有tuples的约束
* 同理 `FD:Pnumber->Pname`
  * Pnumber determines Pname
  * ... --- 对relation中所有tuples的约束

:orange: <font color="deeppink">可以通过已知函数依赖，得出 PK 与 非PK之间的函数依赖</font>

* derive from the functional dependecy between attributes belongs to PK and attributes not belongs to PK
* 如 `{SSN, Pnumber}->Hours` PK与非PK之间的函数依赖
  * {SSN, Pnumber} determines Hours， （一起）唯一确定hours属性的值
  * ... --- 对relation中所有tuples的约束

## 函数依赖定理&性质：Functional Dpendency Principles

![](/static/2021-01-29-21-41-46.png)

> Lemma 1: If K is a Candidate Key, then K functionally determines all attributes in relation R, i.e., FD: K → {R}

:orange: relation中的默认函数依赖 - default functional dependency in relaiton

* 如果 `K`为`CK`，则 可以用 `K`确定 relation `R`中的**所有属性（唯一确定一个tuple）！！！注意是所有属性**
  * 反之亦然，如果 某些属性能唯一确定一个tuple（确定所有属性），则该属性为 `CK`
* **标记**
  * `FD：K->{R}`

---

![](/static/2021-01-29-21-46-39.png)

> Lemma 2: William W. Armstrong’s inference rules for FDs (1974)

:orange: 推导 - 函数依赖性质 characteristic/property

* (Reflexive)`If Y ⊆ X then X → Y`
  * `X={SSN, Ename}; X->{SSN}, X->{Ename}`
    * 如果 X包含Y，则 X决定Y（X的任何一部分属性）
  * 如， `I->B, I->O`, 则`I⊆{I，B}，{I,B}->I`，则`{I，B}->B->O`
* (Augmentation) `If X → Y then X U {Z} → Y U {Z}`
* (Transitive) `If X → Y and Y → Z then X → Z`
  * <font color="deeppink">可用来识别relation中的CK</font>

### 函数依赖性质应用例子

![](/static/2021-01-29-21-58-01.png)
![](/static/2021-01-29-22-06-39.png)

* 不用关心特定属性的意义，semantics free
* 利用函数依赖性质，可推`{I,S}-> R`为CK
  * 因为`{I,S}`能确定所有属性（即唯一确定一个tuple）

# 使用函数依赖 & 标准化步骤：Functional Dependency & Normalization

![](/static/2021-01-29-22-19-56.png)

目的：利用FD决定哪些属性为PK/FK

:orange: 使用定理&标准化步骤

* Assert which are the FDs among attributes (no other semantics required)
  * 断言存在哪些FD（不需要其他语义）。
* Take a pool and put into all the asserted FDs.
* Create a universal big relation with all attributes
* Recursively decompose the big relation based on the FDs into many
smaller ones, such that, when we re-join them, it guarantees that no information is lost and re-constructs the original big relation **without fictitious tuples**.
  * 递归分解大relation（根据FD）成subrelation
  * 满足，当join时，保证无信息缺失，并在无虚构元组生成的情况下重构恢复成原来的relaiton
  * <font color="deeppink">标准化算法/步骤 normalization process 【process of decomposition】</font>

:orange: 涉及的难题

![](/static/2021-01-29-22-27-49.png)

> 通过渐进式分解，**找到基本关系，可以有效地重构整个信息，避免冗余，避免组成后的虚构元组**。find, via progressive decomposition, the basic relations, which can reconstruct the entire information efficiently avoiding redundancy and avoiding fictitious tuples after their composition

* 标准化步骤首先要利用FD（**找到最优分解属性 optimal splitting attribute**），一步步分解大relation成多个subrelation
* 之后join relation时，**不能生成任何虚构元组**（fictitious tuples），避免冗余
  * <font color="deeppink">虚构元组是否产生基于选取的分解属性是否合适</font>

# 标准化理论：Theory of Normalization

![](/static/2021-01-29-22-32-35.png)

> Progressive decomposition of unsatisfactory (bad) relations by breaking up their attributes into smaller good relations;

标准化一般按照下面的流程一步步的将 bad relations 变成 good relations (将 bad relation的属性逐步分解进 subrelation)

:orange: 分解级别 - levels of decomposition(normalization process)

* First Normal Form (1NF)
* Second Normal Form (2NF)
* Third Normal Form (3NF)
* **Boyce-Codd Normal Form** (**BCNF**)
* Fourth Normal Form (4NF)
* Fifth Normal Form (5NF)
* Sixth Normal Form (6NF)

:orange: <font color="deeppink">the higher the level of decomposition the higher the NF, then the better the relation is(quality of optimality)</font>

## 主属性 & 非主属性：Prime Attribute & Non-Prime Attribute

![](/static/2021-01-29-22-39-19.png)

:orange: prime attribute 主属性

* **属于relation的CK集合中的一个属性** an attribute that belongs to some candidate key of the relation
  * 包含在任一候选码中的属性称主属性。简单来说，**主属性是候选码所有属性的并集**
  * 如果**一个属性是构成某一个候选关键字（候选码）的属性集中的一个属性**，则称它为主属性
  * SSN& Pnumber

:orange: non-prime attribute 非主属性

* 其他属性都不算主属性，因为不属于任何CK， non-prime attribute is not a prime attribute , it is not a member of any candidate key
  * Hours, Ename, Pname, Plocation

# First Normal Form (1NF)

> The domain Di of each attribute Ai in a relation R refers only to **atomic (simple/indivisible) values**.

![](/static/2021-01-29-22-47-13.png)

* 1NF范式中，每个属性`Ai`的值域`Di`只能为原子值（单值）
  * **不允许嵌套属性** nested attributes
  * **不允许多值属性** multivalued attributes

![](/static/2021-01-29-22-49-09.png)

:orange: relation标准化为1NF后，可能具有冗余&重复值

## 冗余例子

![](/static/2021-01-29-22-50-36.png)

* 图1为非标准化，非1NF形式（bad design）
  * 其中属性允许多值
* 将多值属性的每个值展开，标准化成1NF后，引入冗余值

---

![](/static/2021-01-29-22-52-31.png)

* 图1存在composite attribute（嵌套属性）
* flatten attribute，1NF -> 引入冗余值

:orange:目的，如何将1NFrelation向更高NF转换，以增加relational schema的质量

# Second Normal Form (2NF) & 完全函数依赖 & 部分函数依赖：Full FD

## 完全函数依赖 & 部分函数依赖

<font color="red">完全&部份依赖：只指非主属性&PK之间的关系 - The full or partial FD relates the dependency of a non-prime attribute to the PK only.</font>

![](/static/2021-01-29-22-54-47.png)

2NF关注PK冗余

:orange: fully functional determines 完全函数依赖定义

* `X`为PK
* 当且仅当 去除 `X`中任意主属性`A`后，剩余的子集都不能再唯一标识`Y`，此时 有完全函数依赖`full FD X-> Y`
  * `X \ {A} → Y`
  * X \ {A} does not functionally determine Y anymore
* <font color="deeppink">即，完全函数依赖，需要所有的主属性用于唯一确定一个非主属性`Y`。如果去除任意一个主属性后，</font>
  * 都无法再确定`Y` --- 存在完全函数依赖 full FD
  * <font color="deeppink">还能确定`Y` --- 存在部分函数依赖 partial functional dependency，此时可能有属性为冗余PK</font>

:orange: 例子

* {SSN, Pnumber} → Ename is not a full FD (partial dependency), since
SSN → Ename holds true; 因为去掉一个主属性后，还能唯一标识Ename，存在部份依赖，因为Pnumber属性为冗余PK
* i.e., Pnumber does not need to be part of the primary key; it is verbose...

---

## 2NF Second Normal Form - 选取造成部份依赖的冗余主属性

![](/static/2021-01-29-23-14-51.png)

> A relation R is in 2NF if every non-prime attribute A in R is
fully functionally dependent on the primary key of R.

:orange: 2NF定义

* 前提 - relation已经为1NF
* `R`中，每个非主属性`A`完全函数依赖于PK（即，不能存在部分依赖，没有PK冗余） not allowed to have partial dependency

:orange: 目标 - 1NF->2NF

* 去除所有部分函数依赖
* <font color="deeppink">2NF选取splitting attribute基于，哪些原主属性会造成部份依赖</font>
  * 剔除，并分成subrelation，选取新的主&非主属性，产生完全依赖

:orange: 方法

1. Identify all the partial FDs in the original relation (already in 1NF)
   1. 找出原始关系中的所有部分FD（已在1NF中
2. For each partial FD, create a new relation such that all non-prime attributes in there are **fully functionally dependent** on the new primary key: 对于每个部分FD，创建一个新的relation，使其中的所有非主属性在**完全函数依赖**于新的主键（主属性）
   * i.e., the prime attribute in the original relation causing partial FDs.**剔除原relation中造成部分依赖FD的主属性（冗余PK）**，将剩下能用于识别的属性作为新relation的PK（并包括被识别的非主属性）
3. The new relation(s) will be in 2NF.

## 例子

![](/static/2021-01-29-23-26-40.png)

* 1NFrelation转换成2NF（主&非主属性具有完全函数依赖）
* note: 每个relation尽量存储相关信息，独立业务，减少之间的相似度

# 1-2NF后的问题识别例子

![](/static/2021-01-29-23-40-33.png)

* 如图relation已经是2NF形式，但仍然存在冗余值，造成CRUD异常（operation anomalies）
  * 更新其中一个属性 Dname，需要强制 update propogation --- 低效

目标：如何找到splitting attribute进行 后续分解

# THIRD NORMAL FORM (3NF) & 传递函数依赖：Transitive Functional Dependency

![](/static/2021-01-30-00-09-02.png)

> Definition: transitive FD means that given a Primary Key X and nonprime attributes Z and Y such that: X → Z and Z → Y, then the non-prime Y is transitively dependent on the primary key X via the non-prime Z.

* **传递函数依赖** transitive FD
  * 给定 PK（主属性） `X`， 非主属性 `Z，Y`。满足：`X->Z`, `Z->Y`则， 非主属性 `Y`通过另一个非主属性`Z`传递依赖于 PK主属性`X`
  * `X → Z and Z → Y then X → Y`
  * <font color="red">如果`Z`，是非主传递属性，则会造成传递依赖问题，违背了3NF</font>

:orange: 3NF定义

* A relation R is in 3NF (being already in 2NF) if there is no nonprime attribute which is transitively dependent on the primary key(via other **non prime transitive attribute**); <font color="deeppink">前提-已经是2NF形式，且【不存在非主属性（需要通过另一个非主传递属性）传递依赖于PK】</font>
  * <font color="red">即，剔除所有传递函数依赖，首先要找到哪些 non prime transitive attribute 非主传递属性造成了后续的传递性依赖，违背了3NF</font>
  * <font color="blue">注意要考虑例外情况，Z为主传递属性</font>
* That is, all non-prime attributes should be directly dependent on the primary key 即，所有非主属性应该直接依赖于PK

:orange: 例子

![](/static/2021-01-30-00-26-07.png)

## 选取分解属性【造成传递依赖的非主传递属性】：方法 Methodology

<font color="red">找传递依赖，看非键部分（非主属性部分之间，是否存在完全依赖）</font>

:orange: 3NF定义

* A relation R is in 3NF (being already in 2NF) if there is no nonprime attribute which is transitively dependent on the primary key(via other **non prime transitive attribute**); <font color="deeppink">前提-已经是2NF形式，且【不存在非主属性（需要通过另一个非主传递属性）传递依赖于PK】</font>
  * <font color="red">即，剔除所有传递函数依赖，首先要找到哪些 non prime transitive attribute 非主传递属性造成了后续的传递性依赖，违背了3NF</font>
  * <font color="blue">注意要考虑例外情况，Z为主传递属性</font>

---

![](/static/2021-01-30-00-27-06.png)
![](/static/2021-01-30-00-31-34.png)
![](/static/2021-01-30-00-35-30.png)

:orange: 方法

* Split the original relation into two relations: the **nonprime transitive attribute【splitting attribute】** (Dnumber) 根据**非主传递属性**，将原relation分解成2个子relation
* 非主传递属性 - 作为 新relation的PK，其余关联的非主属性为新relation的非主属性
* 非主传递属性 - 作为 原relation的FK，引用新relation

# 3NF例外：Generalized Third Normal Form 3NF

![](/static/2021-01-30-00-38-09.png)

> X → Z and Z → Y, with X as the primary key, we consider this as a problem if only if Z is non-prime attribute.
> Violation of 3NF: when a non-prime attribute (Z) determines another nonprime attribute (Y) and Y is transitively dependent on the prime key (X).

* 如果`X->Z, Z->Y`，`X`为PK，当且仅当`Z`为非主属性时，违反3NF（`Z`为非主属性，且决定另一个非主属性`Y`，造成`Y`传递依赖于PK）

:orange: <font color="red">当`Z`为CK候选关键字时，不违反 3NF 【if Z is a candidate key, there is no violation of 3NF】</font>

---

![](/static/2021-01-30-00-47-42.png)

因此决定是否为3NF形式，需要考虑

* 找出`R`中的所有非主属性`A`
  * 是否完全函数依赖于`R`的**每个CK(每个prime attribute)**（PK的选取是基于CK的）
  * 是否非传递依赖于`R`的**每个CK（每个 prime attribute）**
* <font color="deeppink">如果满足以上几点，该relation为3NF形式</font>

# BOYCE-CODD NORMAL FORM (BCNF)

![](/static/2021-01-30-00-56-25.png)

> Idea: remove all inherent dependencies: any attribute should be functionally dependent only on the Primary Key

* 任何属性应只函数依赖于PK
* A relation is in BCNF iff whenever there exists a FD: X → A then X
is a PK, i.e., the left-hand side should be a PK 【BCNF形式的relation，如存在函数依赖`FD：X->A`,则`X`应该为PK(prime attribute, 主属性)】---以移除所有inherent dependencies,只 保留functional dependencies

:candy: 注意区分FD和CK

* CK能唯一标识整一个tuple，可以选取作为PK
* <font color="deeppink">而存在FD只能说某属性能唯一标识别的属性，不一定是CK/主属性</font>

---

:orange:例子

![](/static/2021-01-30-01-25-31.png)

## 如何选取分解属性？

![](/static/2021-01-30-01-28-10.png)

* 如何对relation进行后续分解，避免其他任何inherent dependency，只关注于left hand side 为PK的函数依赖？

---

![](/static/2021-01-30-01-33-07.png)

* 考虑所有分解可能，那种情况根据共同属性join后不会产生虚构元组fictitious tuples？

## BCNF分解定理：BCNF Decomposition Theorem

例子

选取splitting attribute=course

![](/static/2021-01-30-01-35-22.png)

* 该例产生2个虚构元组
* 注意，枚举方法很低效，需要检查每一个分解可能
  * 假设256个属性， 每次分2个relaiton，需要进行32640次检查

---

:orange: 因此，需要利用BCNF定理来找到不会产生虚构元组的最优分解

![](/static/2021-01-30-01-39-43.png)

> Theorem 1: Let relation R not in BCNF and let X → A be the FD which causes a violation in BCNF.

:orange: 定理1

* `R`为非BCNF形式relation，存在`FD：X->A`，违反BCNF
* 则首先需要将`R`分成两个subrelation `R1` & `R2`，满足 Then, the relation R should be decomposed into two relations, such that
  * `R1`具有所有属性，除了right hand side违反BCNF的属性 **R1 with attributes: `R\{A}` (all attributes in R apart from A)**
  * `R2`具有 `X` & `A`属性 **R2 with attributes: {X} U {A} (put together X and A)**
* 之后，检查 `R1`和`R2`是否为BCNF形式 If either R1 or R2 is not in BCNF, repeat the process.
  * 如果其中一个不是，需要重复操作，直到两个relation都不违反BCNF

## 分解例子

![](/static/2021-01-30-01-53-15.png)
![](/static/2021-01-30-01-53-22.png)