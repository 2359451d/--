# 软件许可：Software Licensing

* [软件许可：Software Licensing](#软件许可software-licensing)
* [What can a software licence cover?](#what-can-a-software-licence-cover)
* [许可角色：Roles in licensing](#许可角色roles-in-licensing)
* [Licence eco-system](#licence-eco-system)
* [Cycles in Licensing](#cycles-in-licensing)
* [许可目标:What can software licences be applied to?](#许可目标what-can-software-licences-be-applied-to)
* [Copyright](#copyright)
* [版权下的拆解和修改：Disasssembly and modification under copyright](#版权下的拆解和修改disasssembly-and-modification-under-copyright)
* [Contributors and copyright](#contributors-and-copyright)
* [软件可能被纳入其他软件的不同方式:Different ways software may be incorporated in other software](#软件可能被纳入其他软件的不同方式different-ways-software-may-be-incorporated-in-other-software)
* [Copyleft and Incorporation](#copyleft-and-incorporation)
* [担保和责任:Warranties and liability](#担保和责任warranties-and-liability)
* [执法：Enforcement](#执法enforcement)
* [Reputational issues](#reputational-issues)
* [Considerations when choosing a licence for your software](#considerations-when-choosing-a-licence-for-your-software)
* [Example Licences](#example-licences)
* [Creative Commons Licence](#creative-commons-licence)
* [GNU General Public Licence](#gnu-general-public-licence)
* [注意事项：Caveats](#注意事项caveats)
* [Summary](#summary)

# What can a software licence cover?

**许可证是对软件工件的允许使用和使用条件的一种声明** A licence is an assertion of the permitted uses of a software artifact and the conditions of use. A licence may cover a range of topics, including:

* 所有权 Ownership
* 最终使用权，如展示和表演。End usage rights, such as display and performance.
* 发行权，如复制（版权）。Distribution rights, such as reproduction (copyright).
* 工程 "权利，如反汇编、修改和纳入其他软件。‘Engineering’ rights, such as disassembly, modification and incorporation in other software.
* 担保和责任 Warranties and liability

# 许可角色：Roles in licensing

![](/static/2021-04-26-23-04-39.png)

在最抽象的层面上，许可中有两个角色，At the most abstract level, there are two roles in licensing,

* 一个是希望从分发中获得利益的人工制品的**生产者或提供者**； those of the producer or provider of an artefact, who wishes to receive a benefit from distribution;
* 另一个是希望从人工制品的使用中获益的人工制品的**消费者** and the consumer of the artifact, who wants to benefit from the use of the artefact.

:orange: 软件的**生产者可能是原作者**。Producers of software may be the original authors.

* 然而，人们还可以通过很多其他方式参与到生产中。However, there are lots of other ways that people can be involved in production.
* 例如，**元件副本的所有者的概念与原作者**的概念不同  For example, the notion of an owner of a copy of an artifact is distinct from that or the original author.
  * <font color="red">在许可中确定谁拥有【软件副本】的所有权可能很重要</font>。 It may be important to establish in the licence who has ownership of a software copy.
* 同样地，一个**不是原作者的软件元件的分配者**也可以申请许可证。 Similarly, a licence might be applied by a distributor of a software artifact who wasn’t the original author.
  * 例如，存储库托管网站可能会对托管施加条件 A repository hosting site might impose conditions on hosting for example

:orange: 另一方面，可能有几种不同类型的消费者，许可证可能以不同方式处理他们。 On the other side, there may be several different types of consumer and a licence may address them differently.

* 一个许可证可能指的是**以某种方式获得软件的客户可能拥有的权利**。 A licence might refer to what a customer who is acquiring software in some way may have rights to.
  * 例如，<font color="red">许可证可以说明客户是否获得了软件副本的所有权，或者他们是否只是获得了为某些特定目的使用软件的权利</font> For example, the licence may state whether a customer is acquiring ownership of the copy of software, or whether they are just acquiring the right to use it for some specific purpose.
* **基础设施工程师**可能是许可证的消费者，因为他们负责代表终端用户安装和配置软件。他们可能需要确保在这个过程中配置的环境符合许可证的要求 Infrastructure engineers may be consumers of licences because they are responsible for installing and configuring software on behalf of end users. They may need to ensure that the environment that is configured complies with licences during this process.
* **终端用户**会受到许可证的影响，因为它们通常会描述允许和不允许的用途。例如，一些软件生产商提供了学术许可证，允许以较低的成本甚至免费使用软件，前提是该软件只能用于教育目的 End-users are affected by licences because they often describe permitted and non-permitted uses. For example, some software producers offer academic licences allowing software to be used at reduced or even no-cost provided it is only used for educational purposes.
* 如果**开发者**在自己的作品中加入他人制作的软件，他们也可能是软件许可证的消费者 Developers may also be consumers of software licences if they incorporate the software produced by others in their own work.

# Licence eco-system

鉴于所有的软件都存在于一个生态环境中，**大多数参与者都承担着软件及其各自许可证的生产者和消费者的角色**。Given that all software exists in an eco-system, most participants undertake roles as both producers and consumers of software and their respective licences.

![](/static/2021-04-26-23-45-43.png)

如图所示，你可以把参与者看作是一个消费者-生产者关系的网络。 As the diagram shows, you can think about the participants in a network of consumer producer relationships.

* **任何特定的许可证生产者，如果将其软件纳入自己的作品，也可能是其他人提供的许可证的消费者** Any given producer of a licence may also be a consumer of licences provided by others if they incorporate their software into their own work.

:orange: 这就是软件许可如此重要的原因。This is why software licensing is so important. 

* **即使你没有明确地将许可证应用于你所生产的软件，你仍然可能受到应用于你所依赖的其他软件的许可证的影响**。Even if you don’t explicitly apply a licence yourself to the software you produce, you may still be affected by licences that are applied to other software that you depend on.
* 此外，即使你不对你的软件适用许可证，也不意味着它将自动被视为处于公共领域。在一些司法管辖区，对软件等创造性作品的使用或再分配的限制是自动的，因此，如果你不应用许可证，你的限制可能比你的意图更多 In addition, even if you don’t apply a licence to your software, it doesn’t automatically mean that it will be treated as being in the public domain. In some jurisdictions, restrictions on the use or redistribution of a creative work, such as software, is automatic, so by not applying a licence you may be more restrictive than you mean to be.

# Cycles in Licensing

![](/static/2021-04-26-23-50-41.png)

另一个复杂的问题是，许可结构可能包含循环。一个简单的例子是单元测试框架JUnit。JUnit依赖于断言框架Hamcrest。对等地，Hamcrest框架使用JUnit作为其单元测试框架。**这意味着JUnit团队必须满足Hamcrest的许可条件，反之亦然** A further complication is that the licensing structure may contain cycles. A simple example is the unit testing framework JUnit. JUnit depends on the assertion framework Hamcrest. Reciprocally, the Hamcrest framework uses JUnit as its unit test framework. This means that the JUnit team must satisfy the licence conditions of Hamcrest and vice-versa.

请注意，在这种情况下，并不存在什么困难，因为Hamcrest使用的是允许的BSD许可证，而JUnit使用的是Eclipse公共许可证 Note that in this case, there isn’t a difficulty, as Hamcrest uses the permissive BSD licence and JUnit uses the Eclipse Public Licence.

# 许可目标:What can software licences be applied to?

广义上讲，许可证可以适用于任何软件工件。许可可以指软件项目中的任何相关工件。**但是请注意，并不是所有的许可都能适用于所有不同种类的工件** Broadly speaking, a licence can be applied to just about any software artefact. The licence may refer to any of the associated artefacts in a software project. Note however, that not everything that might be covered in a licence will be applicable to all of the different kinds of artifact.

* 源代码 Source code
* 二进制文件 Binaries
* 文件 Documentation
* 输出 Outputs

# Copyright

> "根据本章的下列规定，作品的版权所有者拥有在英国进行下列行为的**专有**权--(a)**复制**作品；(b)向公众**发放作品的副本**......(e)对作品进行改编或就改编进行上述任何行为" 1988年版权、外观设计和专利法
> “The owner of the copyright in a work has, in accordance with the following provisions of this Chapter, the exclusive right to do the following acts in the United Kingdom— (a) to copy the work; (b) to issue copies of the work to the public … (e) to make an adaptation of the work or do any of the above in relation to an adaptation” Copyright, Designs and Patents Act 1988

**版权是知识产权的一种形式**（其他例子是商标和专利，并与这些不同 Copyright is a form of intellectual property (other examples are trademarks and patents and is distinct from these).

* 版权是在**一段有限的时间内控制创造性作品的复制的法律权利**。时间的长短因司法管辖区而异，但通常增加到自出版或作者去世后的至少50年 Copyright is the legal right to control the reproduction of a creative work for a limited period of time. The length of time varies between jurisdictions, but has typically increased to at least 50 years since the publication or authors death.

# 版权下的拆解和修改：Disasssembly and modification under copyright

> "根据本章的下列规定，作品的版权所有者拥有在英国进行下列行为的专有权--(a)复制作品；(b)向公众发放作品的副本......(e)**对作品进行改编或就改编**进行上述任何行为" 1988年版权、外观设计和专利法
> “The owner of the copyright in a work has, in accordance with the following provisions of this Chapter, the exclusive right to do the following acts in the United Kingdom— (a) to copy the work; (b) to issue copies of the work to the public … (e) to make an adaptation of the work or do any of the above in relation to an adaptation” Copyright, Designs and Patents Act 1988

版权的一个关键概念是衍生，因为在许多司法管辖区，例如英国，版权持有人也有权获得任何衍生作品的版权 A key concept in copyright is a derivation, since in many jurisdictions, such as the United Kingdom, a copyright holder is also entitled to the copyright on any derivative works.

就软件而言，衍生通常被认为是指对现有源代码基础的修改 For the purposes of software, derivation is commonly thought to mean modifications to an existing source code base.

* 如果没有许可证中的具体授权，通常不允许试图对受版权保护的作品进行修改。在软件方面，这通常是指对代码进行反编译，然后对源代码进行修改等行为 Without specific authorisation in a licence, it is generally not permitted to attempt to make modifications to a copyrighted work. In software terms, this is generally taken to mean actions like decompiling code and then making modifications to the source code.
* 需要强调的是，版权并不妨碍对软件作品的复制、传播和修改。它意味着原作者有权决定在什么条件下可以这样做，并在给消费者的许可中记录这些条件。It is important to emphasis that copyright does not prevent copying, distribution and modification of a software artifact. It means that the original author has the right to decide under what conditions this may be done and document them in a licence to consumers.

# Contributors and copyright

![](/static/2021-04-26-23-56-44.png)

**几个不同的生产者可能会对一个单一的软件工件做出贡献**。在这种情况下，**贡献的版权可能首先由贡献者持有**。Several different producers may make contributions to a single software artifact. In this case, the copyright for the contributions may be held in the first instance by the contributor. 

* 为了处理这个问题，**可以要求贡献者将其版权转让给项目的所有者**。在商业组织的情况下，这可能是通过雇佣合同进行的 To handle for this, contributors can be asked to assign their copyright to the project’s owner. This may be through a contract of employment, in the case of a commercial organisation.

**在开放源码项目中，制作原始版本的源代码所依据的许可证可能要求衍生产品（即贡献）在相同的条款下获得许可**。在这种情况下，许可证可能要求将版权转让给原项目作者，或者要求所有贡献者共享版权。在这两种情况下，贡献者的利益（以及他们参与项目的积极性）都得到了保护，因为他们在许可证下做出贡献时，将继续能够访问和重新发布整个软件系统 In the case of an open source project, the licence under which the original version of the source code is produced may require derivations (i.e. contributions) to be licensed under the same terms. In this case, the licence may require copyright to be transferred to the original project author, or that the copyright becomes shared between all the contributors. In both cases the interests of the contributor (and hence their incentive for participating in the project) are protected, by ensuring that when they make a contribution under the licence they will continue to be able to access and redistribute the whole software system.

# 软件可能被纳入其他软件的不同方式:Different ways software may be incorporated in other software

开放源代码的贡献是衍生的一个特殊例子。还有其他的 Open source contribution is a particular example of derivation. There are others.

* 如果一个现有的源代码作品（P）被并入另一个源代码库（C），那么这可能算作衍生。需要考虑的因素是C的功能在多大程度上依赖于（P）的功能。例如，一行产生记录语句的代码可能不被视为衍生 If an existing work of source code (P) is incorporated into another source code base (C), then this may count as derivation. Factors to consider are the extent to which the functionality of C is dependent on the functionality of (P). A single line of code producing a logging statement might not be considered derivation, for example.
* 如果一个现有的库通过某种形式的链接机制被并入一个新的项目，那么这可能被认为是衍生。同样，这可能取决于链接程序对该库的依赖程度。If an existing library is incorporated into a new project through some form of linking mechanism, then this may be considered derivation. Again, this may depend on how dependent the linking program is on the library.
* 一些类型的程序本身可能被用来生成程序，或者程序的不同表现形式。有时这些程序可能会在生成的程序中加入它们自己的部分实现。明显的例子是编译器或解析器生成器。在这些情况下，生成的程序或表示法可以被认为是一个派生作品。请注意，程序输出一般不被视为派生作品。使用IDE开发一个新的软件应用程序本身不会使该软件应用程序成为IDE的衍生作品，除非它包含源自IDE程序的大量材料 Some types of program may themselves be used to produce programs, or different representations of programs. Sometimes these programs may incorporate portions of their own implementation in the generated program. Obvious examples are compilers or parser generators. In these cases, the generated program or representation may be considered a derived work. Note that program outputs generally aren’t considered derived works. Using an IDE to develop a new software application wouldn’t by itself make the software application a derived work of the IDE, unless it contained substantial material that originated in the IDE program.

# Copyleft and Incorporation

![](/static/2021-04-27-00-02-35.png)

鉴于衍生的这种潜在的非常普遍的性质，值得注意的是，软件许可的条款也可以是过渡性的。**这意味着，如果一个软件工件被纳入另一个软件项目，其部分或全部的许可条件也可能需要强加给纳入软件的消费者**。这有时被称为病毒式许可，因为许可条款可能会 "感染 "生态系统中某个距离的软件 Given this potentially very general nature of derivation, it is worth noting that the terms of a software licence can also be <em>transitive</em>. This means that if a software artifact is incorporated into another software project, some or all of its licence conditions may also need be imposed on consumers of the incorporating software. This is sometimes referred to as viral licensing because licence terms may come to ‘infect’ software some distance away in the eco-system.

# 担保和责任:Warranties and liability

担保是指消费者可以期望产品在没有缺陷的情况下运行的时间。许多自由和开放源码软件许可证（和专有商业许可证）宣称该软件没有保证，因此修复任何缺陷的费用最终由消费者承担 A warranty asserts the period of time in which a consumer can expect a product to function without exhibiting defects. Many FOSS licences (and proprietary commercial) assert that the software has no warranty, such that the cost of fixing any defects ultimately lies with the consumer.

在实践中，许多许可证承诺供应商在固定的时间段内提供更新，以修复在这段时间内出现的缺陷。许可证还可以保证软件的性能与相关文献（如广告）中描述的一样，并允许消费者在情况不允许的情况下寻求补救 In practice many licences commit providers to supplying updates for a fixed period of time, that will fix defects that become apparent in the intervening period. Licences may also warrant that software performs as described in associated literature, such as advertising and permit consumers to seek redress if this is not the case.

责任条款与保证相反。它们说明如果所提供的软件被证明有缺陷并造成损害，谁应负责（以及在何种程度上负责）。自由和开放源码软件许可证通常宣称，许可证的消费者要对任何损害负责 Liability clauses are the opposite of warranties. They state who is responsible (and to what extent) should provided software prove to be defective and cause damages. FOSS licences often assert that the consumer of a licence is responsible for any damages.

保修条款宣称供应商对确保软件功能负责的时间段 Warranty asserts the period of time in which the provider is responsible for ensuring the software functions.

责任条款断言，如果软件造成损害，由谁负责？Liability clauses assert who is responsible should software cause damages

# 执法：Enforcement

重要的是要认识到，软件许可证是对你使用软件的期望的一种声明，它不是一个绝对的规则。如果你对软件的分发、使用或修改施加限制，并不一定意味着这些限制会被自动遵守。许可人往往需要采取额外的措施来执行许可条件。这些措施大致可分为：实施技术措施以防止违规，或监测违规行为，然后通过某种形式的法律程序寻求补救 It is important to realise that a software licence is an assertion of your expectations as to the use of your software, it isn’t an absolute rule. If you impose restrictions on the distribution, use or modification of your software, it doesn’t necessarily mean that they will automatically be obeyed. Licensors often have to take additional steps to enforce licence conditions. These can be broadly categorised as implementing technical measures to prevent a violation, or monitoring for violations and then seeking redress through a legal process of some form.

生产者的第一种方法是实施能够防止违反许可条件的技术机制。技术措施的性质将取决于你试图保证的条件，例如：The first approach for a producer is to implement technical mechanisms that are able to prevent a licence condition being breached. The nature of the technical measure will depend on what condition you are trying to guarantee, for example:

* 可以通过使用数字版权管理硬件和软件来执行复制条件 Reproductions conditions might be enforced through the use of digital rights management hardware and software.
* 通过使用及时解密和混淆技术使程序更难修改 Making programs harder to modify by using just in time decryption and obfuscation techniques
* 通过许可证服务器或订阅机制防止未经授权的使用 Preventing unauthorised usage through licence servers or subscription mechanisms

如果寻求通过某种形式的法律或仲裁程序来强制执行许可，第一个问题是你需要知道已经发生了违规。同样，技术措施在这里可能会有帮助，这取决于你寻求执行的许可条件，例如通过使用文件共享监控和指纹技术检测未经授权的分发 If seeking to enforce a licence through a legal or arbitration process of some form, the first issue is that you will need to know that a violation has occurred. Again, technical measures may help here, depending on the licence condition that you are seeking to enforce, for example through detecting unauthorised distribution using file sharing monitoring and finger printing technologies.

---

防止违法行为发生 Preventing a violation

发现侵权行为并寻求补救 Discovering a violation and seeking redress

# Reputational issues

不合理的许可条件或执行机制可能会造成声誉上的损害。例如 Licence conditions or enforcement mechanisms that are unreasonable may cause reputational damage. For example,

* 要求对终端用户的信息进行侵入性的访问 Requiring intrusive access to end user information
* 只向有许可证的用户发布更新。Only issuing update to licenced users.
* 安装监控软件以检测违规行为 Installing monitoring software to detect violations
* 修改或禁用运行未经许可的软件的设备。Modifying or disabling devices running unlicensed software.
* 对违反许可证的人采取法律行动。Taking legal action against licence violators.

软件许可证可能要求使用方提供对存储在同一设备上的大量个人信息的访问，甚至是对服务账户的访问。这些信息通常很有价值，是供应商商业模式的一部分，但如果被广泛报道，可能会造成声誉上的损害 Software licences may require uses to provide access to extensive personal information stored on the same device, or even access to accounts for services. This information is often valuable and part of the business model for the provider, but may cause reputational harm if widely reported.

拒绝向违反许可条件的最终用户更新软件，例如不支付费用，可能是很诱人的做法。然而，这是有风险的，原因有二。其一是用于确定是否购买了许可证的软件可能存在缺陷，这意味着合法用户被拒绝更新。也许更重要的是，如果更新解决了一个安全漏洞，这意味着并非所有运行中的软件实例都被修补了。这使得整个用户群更容易受到攻击 It may be tempting to deny updates of software to end users who violate the licence conditions, e.g. by not paying a fee. However, this is risky for two reasons. One is that the software used to determine whether a licence has been procured or not may be defective, meaning legitimate users are denied the update. Perhaps more importantly, if the update addresses a security vulnerability it means that not all running instances of the software are patched. This leaves the user base as a whole more vulnerable to attack.

在终端用户的设备上安装监控软件，或主动阻止他们通过修改或禁用其他软件而违反许可条款的软件，可能是很诱人的。这可能会惹恼合法用户，他们可能有充分的理由使用该功能。此外，这可能会使他们的设备暴露在监控软件造成的漏洞中 It may be tempting to install monitoring software on an end users device, or software that actively prevents them from violating the terms of the licence by modifying or disabling other software. This is likely to annoy legitimate users who may have good reason for the functionality. In addition, it may expose their devices to vulnerabilities created by the monitoring software.

如果案件的双方都是拥有大量资源的大型组织，法律补救措施可能会很有效（涵盖双方。然而，如果一方 Legal redress may well be effective if the parties to a case are both large organisations with significant resources (to cover both . However, if one party

# Considerations when choosing a licence for your software

太长了，，，。。。。rnm。。。

谁是你的软件的目标终端用户？Who are the target end users of your software?

你希望你的软件被其他软件项目使用吗？Do you want your software to be used by other software projects?

谁是客户？他们的资源是什么？Who are the customers? What are their resources?

你想通过软件的分销获得什么好处？What benefits are you trying to achieve through the distribution of the software?

收入 Revenue

声誉 Reputation

该软件的商业模式是什么？ What is the business model for the software?

谁将拥有该软件的每个分发副本？Who will own each distributed copy of the software?

归根结底，许可证是一种通过控制他人对软件的使用而使软件经销商的利益最大化的手段。然而，对软件项目使用的限制越多，对消费者的吸引力就越小。此外，限制越苛刻，就越有可能发生违规行为（因此需要供应商在执行方面进行额外的投资）。因此，许可证的选择是在使产品对消费者具有吸引力的愿望、在可能危及商业模式可行性的方面的放任以及分销商执行任何条件的能力之间进行权衡。Ultimately, licences are a means of maximising benefit to a distributor of software by controlling what uses others can make of it. However, the more restrictions that are placed on the use of a software project, the less attractive the proposition becomes for the consumer. In addition, the more onerous the restrictions, the more likely it is that violations will occur (thus requiring additional investment in enforcement on the behalf of the provider). Selection of a licence is therefore a trade-off between the desire to make a product attractive to consumers, being permissive in ways that might risk the viability of the business model and the ability of the distributor to enforce any conditions.

（你预计如何获得你想要的利益）？(how do you anticipate accruing your desired benefits)?

---

使用你的软件有什么风险？What are the risks of using your software?

你想对合法使用施加什么限制？What restrictions do you want to impose on legitimate use?

你需要提供哪些保证？What warranties are your required to offer?

---

你有多大的能力来继续开发你的软件？How well equipped are you to continue to develop your software?

你想鼓励其他人对你的软件做出贡献吗？Do you want to encourage others to make contributions to your software?

他们将是谁？你在寻找什么能力？Who will they be? What capability are you looking for?

你能提供什么奖励？What incentives can you offer?

你的软件的依赖性是什么？What are the dependencies for your software?

他们的依赖性是什么？What are their dependencies?

分销商为他们申请了什么许可证？What licences have the distributors applied to them?

直接的分销商在问这个问题时有多严格？How rigorous have the immediate distributors been in asking this question?

---

对你的商业模式有哪些威胁？ What are the threats to your business model?

公众对你的执照会有什么看法？What will be the public perception of your licence?

你的执照条件是否可以通过法律行动来执行？Are your licence conditions enforcable through legal action?

你有多大的能力来执行许可证的条件？How well equipped are you to enforce the conditions of the licence?

# Example Licences

![](/static/2021-04-27-00-15-23.png)

# Creative Commons Licence

![](/static/2021-04-27-00-15-33.png)

创意共享组织发布了一些适合不同需求的不同许可。这些许可证混合了四个不同的属性The creative commons publish a number of different licences suited to different needs. These licences mix four different properties.

<li>署名意味着消费者应该承认被许可作品的贡献。Attribution means that a consumer should acknowledge the contribution of the licensed work.
<li>ShareAlike规定了一个类似于copyleft的条件。ShareAlike imposes a condition similar to copyleft.
<li>NoDerivs防止创造衍生作品。NoDerivs prevents the creation of derivative works.
<li>非商业性只允许被许可的作品被用于创作非商业性作品。Non Commercial only permits the licensed work to be used in the creation of non-commercial works.

这些属性被结合到许可中，例如归属-共享类似 These properties are combined into licences, for example attribution-sharealike.

# GNU General Public Licence

![](/static/2021-04-27-00-16-28.png)

GPL的第一版和第二版是相对较短的文件。该许可证的目标是通过确保所产生的工件保持在公共领域，以及尽可能地限制责任和保证，来激励对自由和开放源码软件项目的分享和贡献。许可证的第三版反映了软件许可法律环境的日益复杂，比早期的版本要长很多。此外，GPL还存在一些变体，以处理特定情况。例如，LGPL有一个较弱的复制权概念，使消费者能够将他们的代码链接到LGPL的产品上，而不要求链接的软件必须在同样的条件下发布。例如，这使得专有软件可以利用共享库。Bison和GCC许可证中的例外情况也类似，允许专有程序使用这些工具的工件进行编译，而不需要承担GPL义务 Version 1 and 2 of the GPL are relatively short documents. The goal of the licence is to incentivise sharing and contributions to FOSS projects, by ensuring that the resulting artifacts remain in the public domain, as well as limiting liability and warranty as far as is possible. The V3 of the licence reflects the growing complexity of the legal landscape for software licensing, being much longer than the earlier iterations. In addition, variations of the GPL exist to handle for specific circumstances. For example, the LGPL has a weaker notion of copyleft enabling consumers to link their code to LGPL artifacts without imposing the condition that the linking software must be distributed under the same conditions. This enables proprietary software to make use of shared libraries for example. The exception in the Bison and GCC licences are similar, allowing proprietary programs to be compiled with artifacts from these tools without incurring GPL obligations.

# 注意事项：Caveats

解释Interpretation

技术演变Technological evolution

管辖权Jurisdiction

---

软件许可是一个法律问题，所以很少有绝对的规定。我打算对选择软件许可时需要考虑的因素做一个高层次的概述。然而，你应该知道，许多问题都是一个正在进行的辩论问题。一个简单的例子是衍生的性质--软件作品什么时候可以被算作衍生品，什么时候是原创作品？Software licensing is a legal issue, so there are very few absolutes. I’ve intended to give a high level overview of the factors to consider when selecting a software licence. However, you should be aware that many of the issues are a matter of on-going debate. An easy example here is the nature of derivation - when can a software work be counted as derivative and when is it an original work?

技术演变也对这些讨论产生了影响。这反映在软件许可的演变和扩散上，以应对不同的情况。例如，Affero GPL的开发是为了反映软件即服务企业的日益扩散。Technological evolution also has an impact on these discussions. This is reflected in the evolution and proliferation of software licences to cope with different circumstances. For example, the Affero GPL was developed to reflect the growing proliferation of software as a service businesses.

最后，管辖权会对许可的性质产生影响。Finally, jurisdiction can have an impact on the nature of licensing.

# Summary

对于一个软件项目和团队来说，软件许可是一个困难但必须解决的问题，因为它直接关系到更广泛的项目商业模式。Software licensing is a difficult but necessary topic for a software project and team to address as it directly relates to the wider projects business model
