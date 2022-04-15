# Content

DES 加密理论

* [Content](#content)
* [Fiestel加密算法：Fiestel Ciphers](#fiestel加密算法fiestel-ciphers)
* [密钥：Fiestel Ciphers Keys](#密钥fiestel-ciphers-keys)
* [乘积加密算法：Product Ciphers](#乘积加密算法product-ciphers)
* [加密步骤：Fiestel Structure](#加密步骤fiestel-structure)
* [每轮加密算法:Fiestel Rounds Algorithm](#每轮加密算法fiestel-rounds-algorithm)
* [2轮加密&解密可视化：Visualization of 2 Round Fiestel Encryption](#2轮加密解密可视化visualization-of-2-round-fiestel-encryption)
* [函数F设计：Function Design](#函数f设计function-design)
* [DES加密：Data Encryption Standard](#des加密data-encryption-standard)
  * [起源：Origins](#起源origins)
* [S-DES](#s-des)
  * [初始排列：Initial Permutation](#初始排列initial-permutation)
  * [子密钥生成：Sub Key Generation](#子密钥生成sub-key-generation)
  * [S-DES Function F](#s-des-function-f)
* [S-DES与DES关系：Relation with DES](#s-des与des关系relation-with-des)
* [DES-Overview](#des-overview)
* [DES秘钥处理&生成](#des秘钥处理生成)
  * [DES-56bit密钥缩减：Key Reduction](#des-56bit密钥缩减key-reduction)
  * [DES-密钥位移：Key Bit Shifting](#des-密钥位移key-bit-shifting)
  * [DES-密钥选择：Key Selection](#des-密钥选择key-selection)
* [DES-数据初始排列：Initial Permutation of data](#des-数据初始排列initial-permutation-of-data)
* [DES-数据扩展：Data Expansion](#des-数据扩展data-expansion)
* [DES-XOR & S-Boxes](#des-xor--s-boxes)
  * [DES S-Boxes](#des-s-boxes)
* [DES-Final Permutation](#des-final-permutation)
* [DES-Summary](#des-summary)
* [16轮DES输出评估：混淆性, 扩散性 - Results from applying 16 steps in DES](#16轮des输出评估混淆性-扩散性---results-from-applying-16-steps-in-des)
* [S-Boxes设计：Designing the S-Boxes](#s-boxes设计designing-the-s-boxes)
* [DES破解：Breaking DES Encryption](#des破解breaking-des-encryption)
* [DES改进-双重DES：DES Advancements - Double DES](#des改进-双重desdes-advancements---double-des)
* [DES改进-三重DES：DES Advancements - Triple DES](#des改进-三重desdes-advancements---triple-des)
  * [解密: Decryption with Triple DES](#解密-decryption-with-triple-des)
* [TDES安全性-Overview](#tdes安全性-overview)
* [TDES/DES兼容性：Compatibility](#tdesdes兼容性compatibility)
* [Summary](#summary)
* [============](#)
* [AES历史:Advanced Encryption Standard](#aes历史advanced-encryption-standard)
* [Rjindael Blocks and States](#rjindael-blocks-and-states)
* [加密算法：Rjindael Encryption Algorithm](#加密算法rjindael-encryption-algorithm)
* [0-轮前转换：Pre-Round Transform](#0-轮前转换pre-round-transform)
* [每轮步骤：Rounds](#每轮步骤rounds)
* [1-字节替换：SubBytes](#1-字节替换subbytes)
* [例子](#例子)
* [映射：Rjindael S-Box (字节替换SubBytes)](#映射rjindael-s-box-字节替换subbytes)
  * [Step1-Map](#step1-map)
  * [Step1-Complete](#step1-complete)
* [2-行位移：Step 2 - Shift Rows](#2-行位移step-2---shift-rows)
* [3-列位移(列混淆)：Step 3 - Shift Columns](#3-列位移列混淆step-3---shift-columns)
  * [点积：Dot Product Process](#点积dot-product-process)
  * [点积溢出：多项式还原-Polynomial Mod](#点积溢出多项式还原-polynomial-mod)
  * [Galois Multiplication-溢出多项式还原](#galois-multiplication-溢出多项式还原)
  * [Galois乘法验证：Is it Correct](#galois乘法验证is-it-correct)
  * [点积例子2(*)](#点积例子2)
* [============](#-1)

# Fiestel加密算法：Fiestel Ciphers

- Fiestel密码器是一个块状密码器系列。Fiestel Ciphers are a family of block ciphers.
- **一些加密标准，如数据加密标准（DES）是基于Fiestel算法的**。Several encryption standards, such as the Data Encryption Standard (DES) are based on Fiestel algorithms.
- Fiestel密码是一种【**区块】密码技术**。Fiestel Ciphers are a block cipher technique.
  - 涉及到对**比特块的【多轮】加密**。Involves multiple rounds of encryption on blocks of bits.
- 所有的**加密/解密**都是用一把**秘钥**的操作。All encryption/decryption operates with a single key.
- 在**64位或128位的块大小上操作**。Operates on 64-bit or 128-bit block sizes.

---

# 密钥：Fiestel Ciphers Keys

**密钥在输入和输出二进制块之间进行映射**。The key maps between input and output binary blocks.

- **对于一个64位的块，密钥的最大长度为10^21位**。For a block of 64 bits, the maximum length of a key is 1021 bits.
  - 大约有64种组合。Approximately 64! combinations.
  - 要用暴力破解这么多的组合是很有挑战性的。Challenging to brute force that many combinations.
- **然而，密钥的大小被减少到128位**。However, the size of the key was reduced to 128 bits.
  - 这产生了大约2^128或10^38的密钥。This yields approximately 2128 or 10$8 keys.
  - 蛮力的挑战比64位要小! (仍然很困难) Less challenging to brute force than 64! (still difficult)

# 乘积加密算法：Product Ciphers

从单个加密 "回合 "中产生的密码文本随后被用于创建另一个密码文本。The produced cipher text from a single encryption ‘round’ being used subsequently to create another piece of cipher text.

- 原理是，你**做的转换越多，输出就越安全**。The principle is, the more transformations you make, the more secure the output will be.
- 理论上，你可以**实现任意多的变换**。在**时间复杂性和密码的安全性之间进行权衡**。In theory you can implement as many transforms as you wish. Trade off between time complexity and security of the cipher.

Fiestel密码器使用乘积加密算法。Fiestel Ciphers use product ciphers.

- **交替进行比特置换和转置**。Alternating bit substitutions and transpositions.
  - **每一次置换都使用一个从【主密钥】生成的子密钥**。Each substitution uses a sub-key generated from the master key.
  - 转置相对置换简单，因为一般不会涉及密钥的使用
  - 问题：如何从一个主密钥生成多个子密钥？

# 加密步骤：Fiestel Structure

- **一个数据块被分成两半，左边（L）和右边（R**）。A data block is split into two halves, left (L) and right (R).
  - 比如64bit的数据块，左32bit,右32bit
- 所有的操作**只对数据块的一半进行操作**。All operations only operate on one half of a data block.
    - 这样做是由于当时的计算/资源限制。This was done due to computational/resource constraints for the time.
    - 它**加快**了密码生成的速度。It sped up cipher generation.
- **有𝑛轮，每轮包括位的替换，然后是转置**。There are 𝑛 rounds, with each round consisting of bit substitutions followed with the transposition.
  - **转置基本上是将L和R对调，以形成下一个L和R块，用于下一步的工作**。The transposition is essentially swapping L and R over, to form the next L and R block to be used in the next step.

# 每轮加密算法:Fiestel Rounds Algorithm

![](/static/2022-04-08-15-12-56.png)

- **我们从一个【初始排列】（即原data块的比特的重新排序）开始** We start with an initial permutation (i.e. a reordering of bits)
- **𝐹是任何函数，有几个不同的Fiestel函数**。𝐹 is any function, there are several different Fiestel functions.
- **𝐾i是一个子密钥（由主密钥生成**）。𝐾i is a sub key (generated from the master key)

取一个数据块，然后排列，每轮左边是上一轮左边&用Ki,F加密上轮右边的结果。重复直到得到最后cipher

# 2轮加密&解密可视化：Visualization of 2 Round Fiestel Encryption

![](/static/2022-04-08-15-22-12.png)

- **L块与函数𝐹(𝐾i, 𝑅)的输出进行XOR**。•	The L block is xored with the output of the function 𝐹(𝐾i, 𝑅)
- **在𝑛轮结束时，会有一个最终的转置**。•	At the end of 𝑛 rounds, there is a final transposition.
  - **然后，L和R被结合起来，形成密文**。•	L and R are then combined to form the cipher.

---

![](/static/2022-04-08-15-27-21.png)

- 解密的过程与加密非常相似。• The process for decryption is very similar to encryption.
- 我们从密码开始，而不是从纯文本开始。•We start with the cipher instead of the plain text.
- **所用的钥匙是按相反的顺序使用的**。•The keys that are used are used in reverse order.

# 函数F设计：Function Design

**函数𝐹的原理是保持一些原则**。•	The principle of the function 𝐹 is to maintain some principles:

- **任何输出位都不应该接近于输入位子集的线性函数**。•	No output bit should be close to a linear function of a subset of the input bits.
  - 这将引入涉及线性密码分析的漏洞。•	This would introduce vulnerabilities involving linear cryptanalysis.
- **不应该对比特位置有任何偏向**。•	There should be no bias towards bit positions.
  - 如果可能的话，模式不应该是可识别的。•	Patterns should not be identifiable if possible.
- **如果两个不同的输入相差1位，输出必须至少相差2位**。•	If two different inputs differ by 1 bit, the output must differ by at least two bits.
  - **混淆和扩散的指标被用来衡量输出的 "随机性"，从而衡量加密的【有效性**】。•	Metrics of **confusion** and **diffusion** are used to measure the ‘randomness’ of the output and thus, the effectiveness of the encryption.

# DES加密：Data Encryption Standard

- 旧的算法，但今天仍在使用。•	Old algorithm, but still used today.
  - 主要是用于不能处理现代加密技术的基于传统的系统上。•	Mostly on legacy-based systems that cannot handle modern cryptographic techniques.
- 依靠Fiestel方法。•	Leverages on Fiestel method.
- **使用64位数据块和56位密钥**。•	Uses 64-bit data blocks and 56-bit keys.

## 起源：Origins

![](/static/2022-04-08-15-40-16.png)

# S-DES

简化DES是Edward Schaefer在圣克拉拉大学开发的DES的简化版。•	Simplified DES is a simplification of DES developed at the University of Santa Clara by Edward Schaefer.

- 它是DES的一个变种，用于学习。•	It’s a variant of DES for learning.
- 方法与DES中的方法一样。除了数据块和密钥更短。•	Methods are like those in DES. Except the data blocks and keys are shorter.
  - **S-DES使用8位数据块和10位密钥**。•	S-DES uses 8-bit blocks for data and 10 bits for keys.
  - **相比之下，DES使用64位的数据块和56位的密钥**。•	DES in comparison uses 64-bit blocks and 56 bits for keys.

下面是一些数据和一个我们将用来说明的样本密钥。•	Here is some data and a sample key we will use for illustration.

* data = 01101100
* key（初始master key） = 0110111101
* **有两个替换轮次，每个轮次都有自己的子密钥**。There are two substitution rounds that each have its own sub-key
  * 2轮，2个子密钥

## 初始排列：Initial Permutation

![](/static/2022-04-09-16-06-56.png)

将bit标号，然后根据偏移量重排序得到**初始排列**

## 子密钥生成：Sub Key Generation

**2个8位的子密钥是由10位的主密钥产生的**。•	Two 8-bit sub keys are generated from the 10-bit master key.

- 作为比较，DES将产生一个48位的子密钥。•	For comparison, DES will generate a 48-bit sub key.
- 5个步骤的过程。

1.	**原钥匙中的位被排列为2416390875**。1.	The bits from the original key are permuted as 2416390875:

* ![](/static/2022-04-09-16-18-29.png)

2.	**左边的5位和右边的5位都向左旋转1位**。2.	The left 5 bits and the right 5 bits are both rotated left 1 bit:

* 然后分为左右两部分，左旋1bit后再结合
* ![](/static/2022-04-09-17-20-39.png)

3.	**第一个8位的子密钥形成以下的偏移量52637498**。3.	The first 8-bit sub key is formed form the following offset 52637498:

* ![](/static/2022-04-09-17-22-56.png)
* 步骤2的10bit输出(**已经进行了左移的**)中选中8bit按偏移排序，**生成K1**

4.	**第二步的左5位和右5位都向左旋转2位(已经进行了左移的**)。4.	The left and right 5 bits from step 2 are both rotated left 2 bits:

* ![](/static/2022-04-10-15-35-27.png)
* 再把步骤2的输出（已经左旋过1bit的）data分成两半，**左旋2bit**，再结合

5.	**第二个8位子密钥是由与步骤3相同的位形成的**。5.	The second 8-bit sub key is formed from the same bits as step 3:

* ![](/static/2022-04-10-15-37-19.png)
* 按偏移量排序，选中8bit，形成K2

:orange: 偏移量被设计成固定的模式。。

## S-DES Function F

![](/static/2022-04-10-16-01-13.png)
![](/static/2022-04-10-15-57-23.png)

1.	将数据块【初始排列的】的**右半部分**从4位扩展到，偏移量为30121230的bit

* ![](/static/2022-04-10-16-05-42.png)

2.	这8位与**子密钥**的8位进行**XOR**操作。

* ![](/static/2022-04-11-15-09-56.png)

3.	**产生的8位被视为4个独立的2bit数。 被称为row_1, col_1, row_2, col_2**

* ![](/static/2022-04-11-15-13-06.png)
* The decimal values of the bit pairs are used to determine row and column values. **bit对的十进制值**被用来**确定行和列的值**

4.	(row_1, col_1)和(row_2, col_2)**构成两个4X4表格的行和列索引**，称为S-boxes。【substitution boxes】

* 用步骤3的bit对的十进制值，来**识别S1,S2应该有的2个十进制输出，然后转为2个二进制**

5.	由此产生的4位数，即**2个S-boxes的二进制输出经过另一次偏移量1320的变换**，产生**F的输出**。【最终排列 **Final Permutation**】

* ![](/static/2022-04-11-15-25-59.png)

# S-DES与DES关系：Relation with DES

DES的结构与S-DES相同，但有更多的步骤。•	DES has the same structure as S-DES, but with more steps.

- 有**16个**𝐹𝑘步骤，每个步骤有一个由**56位密钥产生的48位子密钥**。•	There are 16 𝐹𝑘 steps, each with a 48-bit sub key generated from the 56-bit key.
- 函数𝐹对数据的32位半进行操作。•	The function 𝐹 operates on 32-bit halves of the data.
  - **数据被扩展到48位，并与子密钥XOR**。•	The data is expanded to 48 bits and xored with the sub key.
  - 这48位被分成8个小块，每个6位长。•	These 48 bits are split into 8 chunks, each 6 bits long.
  - 每个6位的块被视为一个行（2位）和列（4位）。•	Each 6-bit chunk is treated as a row (2 bits) and column (4 bits)
  - 它们各自索引一个S-box。•	They each index an S-box.
- 在内部，它有8个S-Boxes，每个4 x 16。•	Internally it has 8 S-Boxes, each 4 x 16.
  - 每个S-Box产生一个4位数。•	Each S-Box produces a 4-bit number.
  - 这8个S-Boxes产生一个32位的值。•	The 8 S-Boxes produce a 32-bit value.

# DES-Overview

![](/static/2022-04-11-15-32-56.png)

# DES秘钥处理&生成

## DES-56bit密钥缩减：Key Reduction

使用DES的**初始密钥是64比特**。•	The initial key with DES is 64 bits.

![](/static/2022-04-11-21-30-39.png)

- 这是为**生成密钥表示法**提供的信息。•	This is the information provided to generate the key representation.
- 由于56位是用于密钥的。**我们丢弃密钥的每一个第8位**。•	Since 56 bits are used for the key. We discard each 8th bit of the key.
- 下表指出了64位密钥中被丢弃的那些位。•	The table below indicates those bits that are dropped in a 64-bit key.
  - 64里面丢8个

## DES-密钥位移：Key Bit Shifting

![](/static/2022-04-11-21-37-55.png)

**在对密钥进行缩减后，我们将其分成两个28位的数值**。•	After the reduction of the key, we split into two 28-bit values.

- 我们对**两半的数据**进行位移操作。•	We apply a bit shift operation on both halves of data.
  - 有一个**预设的表格**来说明我们**如何向左移位**。•	There is a table pre-defined to illustrate how we shift bits to the left.
  - **取决于DES中的回合**。•	Depends on the round in DES.
- 然后，这个操作的输出被用来根据预先定义的选择标准来**选择一个密钥**。•	The output of this operation is then used to select a key based on a predefined selection criteria.
- 先shift，在合并进行 选择
  - ![](/static/2022-04-11-21-37-40.png)

## DES-密钥选择：Key Selection

![](/static/2022-04-11-21-37-40.png)

两个移位后的半数被合并成56位。The two shifted halves are combined back to form 56bits.

- 我们要**从中选择一个48位**的子密钥。•We want to select a 48-bit sub-key from this.
- 这就是所谓的**压缩排列法**。•	Called Compression Permutation.

同样，我们可以利用一个**预定义表格**来实现这个目的(实现key选择)。•	Again, we can leverage on a predefined table in order to achieve this.

![](/static/2022-04-12-19-13-34.png)

- 这些代表了我们用来从56位中选择48位的偏移量。•	These represent offsets that we use to select 48 bits from the 56 bits.
- 输出将是**该轮的48位子密钥**。•	The output will be a 48-bit sub-key for that round.
  - 用于每轮F函数

# DES-数据初始排列：Initial Permutation of data

![](/static/2022-04-12-22-46-21.png)
![](/static/2022-04-12-22-45-11.png)

- 在代码中实现DES算法时，经常使用排列表格。•	Permutation tables are frequently used in the implementation of DES algorithms in code.
- 它们说明了**位的转置**应该如何发生。•	They illustrate how bit transpositions should occur.
- 就像我们在S-DES中看到的换位。•	Like the transpositions we saw with S-DES.
- 同样，这是一个静态表。•	Again, this is a static table.
  - **输出是一个经过排列组合的64位纯文本块** The output here is a permutated 64-bit block of plain text.

# DES-数据扩展：Data Expansion

如前所述，明文被分割成两半L和R。•	The plain text is split into two halves L and R as before.

- **所有操作都将发生在R块上**。•	All operations will occur on the R block.
  - R块被分割成**8个4位的块**。•	The R block is split into 8 4-bit blocks.
    - 32bit右块
- **每个块上的每个比特都被映射到另一个比特序列**。•	Each bit on each block is then mapped to another bit sequence.
  - ![](/static/2022-04-13-14-59-57.png)
  - **一个4位块的最后一位映射到下一个扩展的6位块的第1位**。•	The last bit of a 4-bit block maps to the first bit of the next expanded 6-bit block.
    - **如果它是最后一个4位块，它将映射到扩展后的位序列中的第1位**。•	If it is the last 4-bit block, it will map to the first bit in the expanded bit sequence.
  - **4位区块的第1位映射到前一个6位区块的最后一位**。•	The first bit of the 4-bit block maps to the last bit of the previous 6-bit block.
    - **如果它是第一个4位块，它将映射到扩展位序列中的最后一位**。•	If it is the first 4-bit block, it will map to the final bit in the expanded bit sequence.

最终right halve data从32bit扩展到48bit【扩展的目的就是和48bit的子密钥进行XOR

# DES-XOR & S-Boxes

![](/static/2022-04-13-15-21-44.png)

**来自48位扩展的输出与子密钥生成的输出进行XOR**。•	The output from the 48-bit expansion is XORed with the output form the sub-key generation.

- 然后，这个**输出被送入S-Boxes**。•	This output is then fed into the S-Boxes.
- 在S-Box步骤中，**数据再次被分割成8个6位块**。•	For the S-Box step, the data is again split into 8 6-bit blocks.
- 在DES中有**8个S-Boxes**，每个S-Boxes**在输入[6位值]时都会产生一个[4位值]**。•	There are 8 S-Boxes in DES, each one when input the 6-bit value will yield a 4-bit value.

## DES S-Boxes

![](/static/2022-04-13-15-16-06.png)
![](/static/2022-04-13-15-33-38.png)

* 第一个和最后一个bit合并起来用于形成row offset
  * 可能的 row offset: 0 1 2 3
* 其余的bit合并起来用于column offset
  * 最大15
* 最终指向的十进制值都可以用一个4 bit二进制表示
  * **一共8个S boxes，8个 6bit输入最后产生8*4 bit S boxes输出**

# DES-Final Permutation

final steps in the round

- **对于每个6位的输入，我们得到一个4位的输出，形成一个S-Box**。•	For each 6 bit input we get a 4 bit output form an S-Box.
- **8 x 4 = 32位的输出形成S-Box方法**。•	8 x 4 = 32 bits of output form the S-Box method.

**这个输出最后用一个排列表进行排列，得出我们最终处理过的右边数据块**。•	This output is finally permuted with a permutation table to arrive at our final processed right block of data.

![](/static/2022-04-13-15-55-21.png)

- **这个最终处理过的数据块再与未触及的左边数据块进行XOR**。•	This final processed block of data is then XORed with the untouched left block of data.
- 这样的**输出将成为下一个右块**。•	The output of this, will become the next Right block.
- **未经修改的右块将成为下一个左块**。•	The unmodified Right block will become the next Left block.

# DES-Summary

![](/static/2022-04-13-15-57-13.png)

# 16轮DES输出评估：混淆性, 扩散性 - Results from applying 16 steps in DES

> **混淆和扩散的指标被用来衡量输出的 "随机性"，从而衡量加密的【有效性】**。•	Metrics of **confusion** and **diffusion** are used to measure the ‘randomness’ of the output and thus, the effectiveness of the encryption.

下表列出了每轮DES后变化的比特数，其中有。•	The following table lists the number of bits that have changed after each round of DES with:

- **两个非常相似的纯文本块**(扩散)•	two very similar plain text blocks (diffusion)
- **两个非常相似的密钥**（混淆）。•	two very similar keys (confusion)
- 显然，扩散和混淆是相当有效的。•	Clearly diffusion and confusion is quite effective.
- **排列组合本身并不影响混乱或扩散**。•	Permutations on their own do not effect confusion or diffusion.
  - 原点的1位变化只改变目标的1位。•	A 1-bit change in the origin only changes one bit in the destination.

![](/static/2022-04-13-16-27-54.png)

* 4轮效果已经很好了，但是16轮是为了差分密码分析的概念

# S-Boxes设计：Designing the S-Boxes

该设计原则于1992年公布，以解决后门问题。•	The design principles were published in 1992 to address the concerns of backdoors.

- 该设计**使DES对差分密码分析具有抵抗力**。•	The design made DES resistant to differential cryptanalysis.
  - **差分密码分析【使用两个非常相似的选择的明文信息来确定加密算法的细节**】。•	Differential Cryptanalysis uses two very similar chosen plaintext messages to determine details on the encryption algorithm.
- **线性密码分析**是另一种攻击，它**依赖于两个提前知道的类似明文信息**。•	Linear cryptanalysis is another attack that relies on two similar plaintext messages that are known ahead of time.
- **DES对这两种攻击都有抵抗力**。•	DES is resistant to both attacks.

# DES破解：Breaking DES Encryption

![](/static/2022-04-13-16-33-26.png)

- 虽然DES可以抵御某些复杂的密码分析攻击，但**它并不是一种在现代系统中使用的算法**。
- **56位的短密钥长度使DES容易受到暴力攻击**。
- **蛮力攻击**包括系统地和详尽地检查**所有可能的密钥**。
- 1998年，电子前线基金会建立了一个DES破解器，可以在9天内破解所有可能的密钥。
- 现在，DES的整个密钥空间只需几天时间就可以被搜索到。识别正确的密钥并击败加密所需的时间会少得多。
  - 更复杂的攻击可以在几分钟内破解DES，记录是大约20秒。

# DES改进-双重DES：DES Advancements - Double DES

**双重DES使用两个不同的密钥进行两次加密**。•	Double DES uses two different keys to encrypt twice.

![](/static/2022-04-13-16-40-48.png)

- `𝐶 = 𝐸𝐾2(𝐸𝐾1(𝑃))`
- 它提供了一些比单一加密DES更多的保护。•	It provides some additional protection to that of single encryption DES.
- **用第一把钥匙对纯文本进行加密，然后用第二把钥匙对该密文再进行加密**。•	Encrypt the plain text with the first key, then take that cipher output and encrypt again with the second key.
- 大约需要两倍的时间来破解加密。•	Approximately takes twice as long to break encryption.

# DES改进-三重DES：DES Advancements - Triple DES

![](/static/2022-04-13-20-46-03.png)

- `𝐶 = 𝐸𝐾&(𝐷𝐾2(𝐸𝐾1(𝑃)))`
- 顾名思义，有三轮密码文本的生成。
- 第一轮是用𝐾1进行加密。
- 第二轮涉及用𝐾2进行解密
- 第三轮涉及用𝐾3进行加密。
- 加密涉及到从𝐾i中生成的子密钥，顺序为1 ... 𝑛。
- 解密涉及到从𝐾i中产生的子密钥，顺序为𝑛 ... 1

---

## 解密: Decryption with Triple DES

**解密步骤使TDES向后兼容DES的应用**。•	The decryption step makes TDES backwards compatible with DES applications.

- **如果𝐾1 = 𝐾2 = 𝐾3，那么结果将与单钥加密相同**。•	If 𝐾1 = 𝐾2 = 𝐾3 then the outcome will be the same as single key encryption.
  - 这是因为我们加密、解密和再次加密。•	This is because we encrypt, decrypt and encrypt again.
- **如果𝐾1、𝐾2和𝐾3是不同的，那么我们就会获得三重加密的好处**。•	If 𝐾1, 𝐾2 and 𝐾3 are distinct, then we reap the benefit of triple encryption.
  - **用不同的密钥进行'解密'，在功能上等同于加密**。•	‘Decryption’ with a different key is functionally equivalent to encryption.
  - **注意这里解密后，输出不是明文，，因为用的是不同的key解密，，所以输出并不是明文**

# TDES安全性-Overview

![](/static/2022-04-13-20-58-45.png)

- 3轮，加密，解密，加密。•	3 rounds, Encryption, Decryption, Encryption.
- **如果用不同的主密钥（在这里是MK1、MK2、MK3），那么输出将有三个 "层 "的加密**。•	If done with the distinct master keys (in this case MK1, MK2, MK3) then the output will have three ‘layers’ of encryption on it.
  - master key 1,2,3
- **这使得TDES可以在不使用较大的密钥长度的情况下加强安全性**。•	This allows TDES to strengthen security without using larger key lengths.

# TDES/DES兼容性：Compatibility

![](/static/2022-04-13-21-03-19.png)

- 3个回合，加密、解密、加密。•	3 rounds, Encryption, Decryption, Encryption.
- **如果用相同的主密钥（在本例中为MK1）进行，那么输出将等同于单轮DES加密**。•	If done with the same master key (in this case MK1) then the output will be equivalent to a single round of DES encryption.
- **这使得TDES硬件可以在不改变的情况下操作DES加密**。•	This allows TDES hardware to operate DES encryption without changes.

# Summary

- DES是对安全领域的一个重要贡献。•	DES was a significant contribution to the security space.
  - **它没有被用于现代应用中**。•	It is not used in modern applications.
  - **然而，有一些遗留的软件/硬件系统需要使用它**。•	However, there are legacy software/hardware systems that need to use it.
- **双重和三重DES试图通过执行更多的步骤来加强DES，而无需重新设计它**。•	Double and Triple DES were attempts to strengthen DES by performing more steps without redesigning it.
  - 然而，这些已经被破解，并被其他标准所取代，如HIST或AES。•	These however have been cracked and have been superseded by other standards such as HIST or AES.
- **然而，三重DES仍然被一些现代应用所使用**。•	However, Triple DES is still used by some modern applications.
  - SSL库支持TDES。•	SSL libraries support TDES.
  - 火狐在2021年才放弃对TDES的支持!•	Firefox only dropped support for TDES in 2021!

# ============

# AES历史:Advanced Encryption Standard

![](/static/2022-04-13-21-40-05.png)

- 由于DES的缺陷变得明显，NIST（美国国家标准与技术研究所）开始寻找其替代品。•	The NIST (US National Institute for Standards and Technology) began a search for a replacement for DES as its deficiencies became apparent.
- AES的设计是公开进行的，不像DES是秘密设计的。•	The design of AES was performed publicly, unlike DES which was designed in secret.
- 世界各地的参赛者都被邀请参加。•	Entries were invited from around the world.
- 算法和分析是公开的。•	The algorithms and analysis were made public.
- 任何组织都可以提出意见。•	Comments were invited from any organization.

# Rjindael Blocks and States

![](/static/2022-04-13-21-41-17.png)

* 该算法可用于数据长度为128、192或256比特的情况。
* 同样适用于密钥（它们的长度可以是128、192或256比特）。< 
* 我们将只解开**128，128的方法（数据，密钥长度**）.< 
* 唯一真正的区别是，**更多的比特意味着更多的轮次。**
* **所有操作都发生在数据块上**（我们将表示为`S`）<

# 加密算法：Rjindael Encryption Algorithm

数据块`S`

![](/static/2022-04-13-21-47-44.png)

```text
<!-- S数据块用子密钥XOR,第一步是第1个subkey -->
AddRoundKey(S, K[0]);
for (int round = 1; round <= 10; round++)
{
  SubBytes(S); ShiftRows(S); if (round != 10)
    MixColumns(S); 
    <!-- 另外的XOR操作，使用其他subkey -->
  AddRoundKey(S, K[round]);
}
```

# 0-轮前转换：Pre-Round Transform

轮密钥加方法里面的

* ![](/static/2022-04-13-21-53-48.png)
* ![](/static/2022-04-13-21-54-00.png)

**这涉及到取一个128位的区块和一个128位的密钥，并对两者进行XOR**。•	This involves taking a 128-bit block and a 128-bit key and performing an XOR on both.

![](/static/2022-04-13-21-51-14.png)
![](/static/2022-04-13-21-53-10.png)

- XOR在每个比特的比较上操作。•	XOR operates on each bit comparison.
- 下面是对两个块的快速概述。•	The following is a quick overview on two blocks.
  - **左边块=数据，右边块=密钥**。•	Left block = data, right block = key.
- **XOR函数的输出将是一个数据块**。•	The output of the XOR function will be a data block.
  - 然后，这将被传递到AES算法的主要部分。•	This is then passed into the main part of the AES algorithm.

# 每轮步骤：Rounds

AES的**每一轮**都由**一系列的步骤**组成。Each round in AES consists of a series of steps.

1.	SubBytes. **字节替代**
2.	ShiftRows.**行移位**
3.	MixColumns (if it is not the last round). **列混淆（如果不是最后一轮**）
4.	AddRoundKey.**轮密钥加**

Combination of substitution and permutation actions.替换和排列动作的组合。

# 1-字节替换：SubBytes

The objective is to map all the bytes 𝑏i to another byte value 𝑆(𝑏i) - **目标是将所有的字节`𝑏i`映射到另一个字节值`𝑆(𝑏i)`**。

![](/static/2022-04-14-16-44-51.png)

- **这种操作涉及到从查询表中交换字节值**。•	This operation involves swapping byte values from a lookup table.
- **每个区块`𝑆`可以表示为一个`4×4`的矩阵`𝑀`**。•	Each block 𝑆 can be represented as a 4 × 4 matrix 𝑀
- **原理是将一个[字节]`𝑠i∈𝑆`映射到查找表中的一个值**。•	Principle is to map a byte 𝑠i ∈ 𝑆 to a value in the lookup table.
- **查找表是静态的，其功能很像DES中的S-Boxes**。•	The lookup table is static, and functions much like S-Boxes from DES.

---

# 例子

假设我们要对 "安全软件 "这一信息进行加密 •	Suppose we are looking to encrypt the message “Secured Software”

- **每个字符都可以装入一个字节表示**。•	Each character can fit into a byte representation.
  - "Secured Software" ↦ 5365637572656420536F667477617265
  - **我们可以用这里的十六进制表示，并从中形成`𝑆`**。•	We can take the hex representation here and form 𝑆 from it.
  - 每个字符可以用1B(16进制)表示
- 右边的矩阵𝑀用十六进制表示这个字符串。The matrix 𝑀 to the right represents this String in hexadecimal.
  - ![](/static/2022-04-14-17-24-32.png)
  - ![](/static/2022-04-14-16-46-40.png)
  - 请注意，假设这个矩阵`M`是预轮xor转换(后?)的输出结果! Note we are assuming this is the output of the pre-round xor transformation!

# 映射：Rjindael S-Box (字节替换SubBytes)

用先前矩阵`M`的16进制表示，来索引查表

![](/static/2022-04-14-17-27-46.png)

- 静态映射表。•	Static mapping table.
- 允许在AES中有效地查找替换步骤的值。•	Allows for efficient lookup of values for the substitution step in AES.
- 如果你正在寻找映射表，维基百科是一个很好的参考。•	Wikipedia is an excellent reference for the table if you are looking for the mappings.

## Step1-Map

![](/static/2022-04-14-17-31-56.png)
![](/static/2022-04-14-17-32-04.png)
![](/static/2022-04-14-17-34-11.png)

- 我们要**把十六进制的值分成两半**。•	We want to split the hex value in two.
- 每一半的值用于识别Rjindael查找S-Box中的行和列的值。•	Each half of the value is used to identify row and column values in the Rjindael lookup S-Box.
- **因此，如果我们从𝑆（数据块,轮预转换后的16进制）中取第一个字节，`𝑏0 = 53`** •	Thus, if we take the first byte from 𝑆, 𝑏0 = 53
  - 在Rjindael查询表中要检查的**行是5 ,要检查的列是3**。
  - 如果要查的字节16进制为`FF`，则row=15, column=15

## Step1-Complete

如果我们要浏览整个例子，我们将得到以下输出。•	If we were to go through the whole example, we will get the following output:

* ![](/static/2022-04-14-17-35-20.png)

**有一些Rjindael S-Box的关键设计特性** •	There are some key design properties of the Rjindael S-Box

1.	**任何字节都不应该用自己来代替**。1.	No byte should be substituted with itself.【最大化混淆性 maximize confusion
2.	**没有反转**。2.	No inversions.
3.	**实现必须是快速的**。3.	The implementation must be fast.

# 2-行位移：Step 2 - Shift Rows

![](/static/2022-04-14-19-28-29.png)

- 这是一个简单的操作。•	This is a simple operation.
- **我们要将𝑆内的每个字节块向左移动**。•	We want to move each byte block within 𝑆 to the left.
- 我们**移动空格数量随行数增加**。•	How many spaces we move increases for each row.
- 第1行，我们不移动。•	Row 1, we don’t move.
- 第2行，我们移动1个空格。•	Row 2, we move 1 space.
- 第三行，我们移动2个空格。•	Row 3, we move 2 spaces.
- 第4行，我们移动3个空格。•	Row 4, we move 3 spaces.

# 3-列位移(列混淆)：Step 3 - Shift Columns

**步骤2的输出(行位移后输出)中的每一列都可以表示为一个矢量**。•	Each column in the output of Step 2 can be represented as a vector.

![](/static/2022-04-14-19-34-42.png)
![](/static/2022-04-14-19-35-27.png)

- 在这一步骤中，我们要将**每个向量与一个静态矩阵相乘**。•	In this step we want to multiply each vector by a static matrix.
- **产生一个新的向量**。•	Produces a new vector.
- **替换掉数据块中的旧列**。•	Replaces the old column from the data block.

---

- 在这一步骤中进行的所有操作据说都发生在一个Rinjdael Galois场或一个有限场内。•	All actions that are taken in this step are said to occur within a Rinjdael Galois field or a finite field.
- 这意味着，**所有的操作都应该产生符合某种映射的结果状态空间的结果**。•	This means that all operations should produce results that fit within some mapped statespace of outcomes.
- 之前看到的**乘法步骤涉及二进制序列的乘法和加法**。•	The multiplication steps seen previously involve the multiplication and addition of binary sequences.
- 在Rinjdael Galois场中的二进制序列上进行算术，意味着我们<font color="deeppink">不应该在二进制输出中观察到溢出或下溢。</font>	Performing arithmetic on a binary sequence in a Galois field means we should never observe overflow or underflow in our binary output.
  - 二进制field/space， 必须保证输出在这个范围内
    - max `1111 1111`
    - min `0000 0000`
- 因此，**将两个8位序列相乘/相加/相逆应该会产生另一个8位序列**。•	Therefore, multiplying/adding/inversing two 8-bit sequences together should create another 8-bit sequence.

---

![](/static/2022-04-14-19-44-54.png)

- **当𝑐i与矩阵中的一个值相乘时，我们采取点积的方式**。•	When multiplying 𝑐i with a value from the matrix, we take the dot product.
- **当相加数值时，我们采取xor操作**。•	When adding values, we take an xor operation.
- **在取点积时出现溢出的情况下**。•	In the event of overflow when taking the dot product.
  - 我们用<font color="deeppink">多项式还原法</font>进行还原。•	We reduce using polynomial reduction.

## 点积：Dot Product Process

如何将二进制序列表示为多项式？

![](/static/2022-04-14-19-47-57.png)

- **矩阵和向量的值可以用二进制表示**。•	The values from the matrix and the vector can be represented in binary.
- 我们可以利用这个二进制序列来创建多项式的表示方法。•	We can leverage on this binary sequence to create polynomial representations.
- **多项式的指数是由比特状态和它们的偏移量决定的**。•	The polynomial exponents are determined by bit state and their offsets.
  - 看哪个offset是1，就在多项式中表示出来
- 二进制序列和多项式的映射显示如下。•	Binary Sequence and polynomial mapping is shown as follows

---

- **矩阵中的值**和**要乘的字节值**可以用多项式来表示。•	The value in the matrix and the byte value to be multiplied can be represented as polynomials.
  - 列向量的值和矩阵的值都要转成多项式= =

![](/static/2022-04-14-19-56-37.png)

---

![](/static/2022-04-14-20-00-43.png)

- 我们根据被乘的项的指数构建一个表格。•	We construct a table from the exponents of the terms being multiplied.
  - 然后我们将表中的指数相加。•	We then add the exponents on the table.
  - ![](/static/2022-04-14-20-02-08.png)
- 但我们可以看到这里**有溢出**。•	But we can see we have overflow here.
  - 这个多项式将不止8位。•	This polynomial will not fit into 8 bits.
  - 这是因为我们在第8个偏移位上有一个bit。•	This is because we have a bit in the 8th offset.
  - 因此要进行多项式还原 polynomial reduction确保输出能在field中

## 点积溢出：多项式还原-Polynomial Mod

![](/static/2022-04-14-20-05-05.png)

- 我们要计算我们的结果的多项式模，如果它不适合8位的话(溢出)。•	We want to calculate the polynomial mod of our result in the event it does not fit into 8 bits.
- **我们使用一个特殊的多项式来减少这个值，以保持在有限域内**。There is a special polynomial we use in order to reduce this value to remain within the finite field.

---

具体步骤

1.	**取我们的结果的多项式并转换为二进制**。1.	We take the polynomial of our result and convert to binary.

2.	我们**取反**转的多项式**并转换为二进制**。2.	We take the polynomial for the inversion and convert to binary.

3.	我们**对比特进行xor操作**。3.	We perform an xor operation on the bits.

4.	我们**丢弃最左边的位**。4.	We discard the leftmost bit.
5.	我们剩下的是我们的模子输出，适合在有限域内。5.	What we are left with is our modulus output that fits within the finite field.

- 这就是**Galois乘法**的应用。This is the application of Galois multiplication.

## Galois Multiplication-溢出多项式还原

![](/static/2022-04-14-20-12-09.png)

- 我们取数据的二进制 take the binary of the data
- 我们取**特殊多项式的二进制**。take the binary of the special polynomial
  - 之前那个什x^8叽里呱啦的
- 我们对它们进行XOR。 we XOR them
- 去掉最左边的位。 remove the leftmost bit
- 剩下的就是我们的8位值。 what remains is our 8-bit value

## Galois乘法验证：Is it Correct

- 在开发具有加密功能的系统时，我们**可以使用查找表来识别基于两个因素的输出**。•	When developing systems with encryption, we can use lookup tables to identify the output based on two factors.

- **被改变的数据的值（在我们的例子中是`ED`**）•	The value of the data being changed (in our case ED)
  - 这个值乘完矩阵之后溢出了 ![](/static/2022-04-14-20-15-50.png)
  - 它在表中被乘以的值（在我们的例子中是2）•	The value it is multiplied with in the table (in our case 2)
- 我们可以**使用ED的偏移量来交叉检查输出，以检查正确性**。•	We can use the offsets from ED to cross examine the output to check for correctness.
  - ![](/static/2022-04-15-14-36-43.png)
  - 下面是x2时候的表![](/static/2022-04-15-14-38-47.png)
    - 查表后发现`c1`正好就是`1100 0001`
  - cross referencing the table![](/static/2022-04-15-14-41-12.png)

:orange: 能查表交叉验证，进一步表明了，做乘法的时候可以直接使用表来索引，省去乘法步骤，因此非常快

## 点积例子2(*)

![](/static/2022-04-15-14-44-02.png)

在表格中。•	In the table:

![](/static/2022-04-15-14-48-38.png)

- 注意表格中出现两个以上的项
  - 比如有两个`7`，意味着`2x^7`
  - <font color="deeppink">最后根据多项式系数 & 多项式指数来确定某位的bit值</font>
- ![](/static/2022-04-15-14-53-59.png)
  - **奇数系数**产生一个二进制`1` •	Odd number of exponents create a binary 1
  - **偶数系数**产生一个二进制的`0` •	Even number of exponents create a binary 0
- 从最大的数值开始，并勾选出这些数值。•	Start with the largest value and check off the values.
- **再次，我们需要进行还原操作**。
  - 特殊多项式XOR，discard最左位
  - 如果这是一个8位的值，我们可以把它作为我们的答案。
- **这样的输出将是00101100（十六进制2C**）。
- 让我们检查一下它是否正确
  - 交叉验证
  - ![](/static/2022-04-15-14-58-23.png)


# ============