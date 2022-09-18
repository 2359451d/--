# 隐写术：Steganography

## Secret Writing

![](/static/2021-03-07-18-56-30.png)

隐写是一种在众目睽睽之下隐藏的形式。 Steganography is a form of hiding in plain sight.

* **Steganography这个词是由两个希腊词组成的，意思是秘密写作**。 The word steganography is made up of two Greek words meaning secret writing.
* **一个秘密的有效载荷隐藏在一个看起来无害的伪装对象，产生一个隐秘对象**。 A secret payload is hidden inside an innocuous looking cover object, producing a stego object.
  * payload是什么，加不加密不重要（明文，密文都可以）
* **可以对有效负载本身进行加密，以增加安全性** The payload itself can be encrypted for additional security.
* **在过去，隐写术包括以一种无法察觉的方式修改物理对象(或人!)**。 In the past steganography involved modifying physical objects (or
people!) in an undetectable way.
  * **现在它涉及到修改电子对象** Now it involves modifying electronic objects

## 文本例子：Text Example

![](/static/2021-03-07-19-01-41.png)

有效载荷，是一段简短的文本，可以转换为一个位串。 The payload, a short piece of text, can be converted into a bit string.
 伪装对象，一个较长的文本文档，如果下一位是1，可以通过在下一行的末尾加一个空格来修改。 The cover object, a longer text document, can be modified by adding a space to the end of the next line if the next bit is a 1.
 **必须有一种方法来指示有效载荷的结束，也许可以用一个NULL字符（8个零）来结束它（也可以不用）**。 There must be a way of indicating the end of the payload, perhaps by terminating it with a NULL character (8 zeros).
 stego，**隐藏对象将与原始伪装对象看起来一样**。 The stego object will look the same as the original cover object.
  o 因此在阅读文档时，一些行末添加的空格将不可见。 The added space at the end of some lines will not be visible when reading the document.

## 检测隐写术：Detecting Steganography

![](/static/2021-03-07-19-06-57.png)

 **文本文件在一些行末有额外的空格，看起来会很可疑**（因此利用程序检测行末，除非你不觉得使用了隐写术）。 Text documents with extra spaces at the ends of some lines would look suspicious.
    * 假设怀疑使用了隐写术 Provided steganography was suspected.
    * 需要电子分析 The analysis would have to be digital.
    * 但是也可能有其他原因造成空格，如生日攻击 There might be another reason for the spaces, such as a birthday attack.
 **如果原始的封面文件是公开的，比如dracula.txt文件，那么dracula.txt的stego版本就会更长**。 If the original cover document were publicly available, such as the file dracula.txt, then the stego version of dracula.txt would be longer.
  * 因此隐写很容易被发现。 Steganography would be easy to detect.

## 攻击隐写术：Attack Steganography

![](/static/2021-03-07-19-12-11.png)

* **如果对 stego 对象进行处理，去除每行末尾的所有空白，则可以去除有效载荷**。 The payload could be removed if the stego object was processed to remove all blank spaces at the end of each line.
* 如果怀疑有隐写行为，可以对所有的电子邮件采取预防措施。This could be done as a preventative measure on all emails if steganography was suspected.
* 这将防止通过隐写法进行通信，但不会恢复有效载荷。 This would prevent communications via steganography but would not
recover the payload
* 这种方法可能无法检测到是否使用了隐身术，这取决于它是如何实现的。 This approach may not detect if steganography has been used, depending on how it was implemented.
* **这种攻击的目的是防止通信**。 The aim of this attack is to prevent communications.
* 多余的空格可能在行的中间。The extra spaces could be put in the middle of lines
* 每个句子中可能有一个词拼写错误，也可能没有。A word may or may not be misspelt in each sentence

## 利用图像进行隐写：Steganography with Images

![](/static/2021-03-07-19-28-33.png)

文本文件的容量有限，只适合隐藏小型有效载荷。Text files have limited capacity and are only good for hiding small payloads.

* 图像文件要大得多，常用于通信交换 & 隐写。 Image files are much larger and are commonly exchanged.
* 有更多的余量来隐藏秘密信息。 There is much more spare capacity to hide secret information
  * 1M图片，可以使用10%的容量，100K来当有效载荷
* **有许多不同的图像文件格式，这可能会给隐写带来问题(所以无论如何图像是一个很好的虽然他们不是完美的解决方案，因为不同的英文文件格式，有些比别的更容易工作)**。 There are many different image file formats, which can cause problems for steganographers

## 图片像素：Image Pixels

图片隐写的简单处理方法

![](/static/2021-03-07-19-33-34.png)

图像文件包含头信息，然后是像素信息。 Image files contain header information and then the pixels

* 头部信息通常相当精确，几乎没有多余的容量 The header information is usually quite precise, with little spare capacity
* **图像文件的大部分是由像素组成的** The bulk of the file is made up of pixels
* **每个像素通常会包含三种颜色成分：红、绿、蓝**。 Each pixel will typically contain three colour components: red, green and blue.
* **每个颜色分量通常为8位(8个位面? 8 bit plane)，有256个不同的可能值**。Each colour component is typically 8 bits long, with 256 different possible values.
* 下面的幻灯片显示了图像中绿色分量的位面。The following slides show the bit planes for the green colour component of an image.
  * Each pixel is either 1 or 0
  * bit plane 0
    * bit 0 for every pixel (only green pixel's bit plane)

---

![](/static/2021-03-07-19-48-00.png)

绿色像素分量 这个图像绘制为灰度图像。所以，这些像素中的每一个像素，有256个不同的强度等级。这些像素中的每一个都要知道256个不同的强度等级。所以，你可以看到其中一些有高强度，显示为白色。有些是低强度的，显示为黑色。

bit plane x (bit4差不多)
![](/static/2021-03-07-19-50-08.png)
![](/static/2021-03-07-19-50-59.png)
![](/static/2021-03-07-19-51-02.png)
![](/static/2021-03-07-19-51-09.png)
![](/static/2021-03-07-19-51-17.png)
![](/static/2021-03-07-19-51-38.png)
![](/static/2021-03-07-19-51-47.png)
![](/static/2021-03-07-19-51-53.png)
![](/static/2021-03-07-19-51-57.png)

* 这个特殊的图像 **我们可以用有效载荷替换掉绿色像素中大约一半的比特**。我们看到有效载荷只是另一种形式的随机性， 所以它只是不同于现有的随机性。而且只是通过看图片,你无法检测到它。we can replace roughly half of the bits in green pixels with payload. And we're seeing the the payload is just another form of randomness, so it's just different from the existing randomness. And you can't detect it. Just by looking at the picture.

## 利用图像低位的随机性-算法：Least Significant Bit (LSB)

![](/static/2021-03-07-20-01-53.png)

exploiting this randomness that appears in the least significant bits in an image

* least significant bit algorithm
  * 原理：**每个颜色分量的最低位面（0 bit plane）往往是很随机的。可以替换成有效载荷** The least significant bit of each colour component is often quite random. replace the least significant bit in every pixel, and every colour component of every pixel with the payload.
    * **用有效载荷的位数代替位数1往往也能起到作用，使承载能力达到25%**。 Replacing bit 1 with bits from the payload can often work as well, giving a 25% carrying capacity.
  * **替换后不会产生任何差异，如果只是看图片** Replacing it with a different value will often not be noticed
  * **可以利用12.5%的容量作为有效载荷** This gives a payload capacity of 1/8th (12.5%).

:orange: 容量是相当大的，它足以在另一个图像中隐藏一个图像。所以，我们有一个高有一个小的图像它只占25%。我们可以把它隐藏在其中的一个图像里面，把两个最不重要的位（bit 2），用它代替。 capacity is quite large and it's enough to hide an image in another image. So, we have a high have a smaller image it's only 25% that's big. We can hide it inside one of these images by replacing the two least significant bits, with it. 

### LSB缺陷：Problems with LSB

![](/static/2021-03-07-20-09-29.png)

例子中的猴子伪装图片相当不错，**因为所有图片的颜色变化相当快（难以注意到哪个随机像素被替换了）** The example cover image is quite good because the colours change quite rapidly over all of the image

* **大多数图片会有颜色变化不大的地方** Most pictures will have areas where the colour doesn’t change very much.
  * Sky, furniture, machines.
  * The pixel values do not change very much.
* 用随机快速变化的值代替LSB，那么就会很明显了。 Replacing the LSB with random rapidly changing values is then quite noticeable
  * 如狗玩雪的图，大部分都是白色，如果替换掉一些白色，很起眼
  * 因为大部分图片都有很多变化不大的区域，所以任何的修改都很起眼 cause most images have large areas where the colour doesn't change very much and what changes are noticeable

## 位面复杂度分割隐写算法：Bit Plane Complexity Segment (BPCS)

不再是替换分量中低位面的每个bit，而是随机选取图像中低位面**区域**

* 比LSB算法实现更复杂

![](/static/2021-03-07-20-15-44.png)

* 位面是指从图像中的每一个像素中抽取一个位，例如绿色位3，来创建黑白图像。A bit plane is a black and white image created by taking one bit, say green bit 3, from every pixel in the image.
* 前面的例子显示了图像中所有像素的绿色色段的所有8位平面。 The previous example showed all 8 bit planes from the green colour segment of all the pixels in the image.
  * 另外还有8红位面和8蓝位面，（红蓝绿）共24个。There will be another 8 red and 8 blue bit planes, for a total of 24.
* **在这种算法中，每个位面被划分为长8像素、高8像素的小片段** In this algorithm, each bit plane is divided into small segments 8 pixels long and 8 pixels high
  * **每段包含64位** Each segment contains 64 bits
  * 段就是像素组，用来比较是否变化过大，(变化的位置) segment is a small group of groups of pixels, compare positions
* **一幅1024×1024的图像在每个方向上将有128组8像素，每个位面有128×128=16384段（长8pixel*宽8pixel**）。A 1024 x 1024 image will have 128 groups of 8 pixels in each direction, with 128 x 128 = 16,384 segments per bit plane.
  * 24bit位面一共24*128*128=393216段 24 bit planes gives a total of 24* 16,384 = 393,216 segments.

### 复杂段：Complex Segments

什么是复杂度？ complexity

![](/static/2021-03-07-22-48-35.png)

**在一些段中，相邻像素的位将相当随机地分布。它们被称为复杂段**。 In some segments the bits from neighbouring pixels will be distributed fairly randomly. They are called complex segments.

* Most segments in the Bit 0 example
* 它们可以被替换成有效载荷位而不被任何人发现。 They can be replaced with payload bits without anyone noticing.

**在其他片段中，相邻像素的位数变化不大(not complex)** In other segments the bits from neighbouring pixels don’t change very much.

* Many segments in the Bit 5 example
* 它们不应该被替换成有效载荷位。 They should not be replaced with payload bits.

:orange: **BPCS算法只替换复杂段**。 The BPCS algorithm only replaces complex segments.

* we are selectively replacing only the complex segments, and that's good because not only will that mean that it's much harder to detect, but steganography has been used, but it also increases the carrying capacity, because some of the high order bits could also be complex. 我们有选择地只替换复杂的段，这是好的，因为这不仅意味着它更难被检测到，但隐写术已经被使用了，而且它还增加了承载能力，因为一些高阶位也可能是复杂的

### 如何求复杂度：What is Complex?

how do you find the complexity

![](/static/2021-03-07-22-53-36.png)

**复杂度主要是衡量一个片段中相邻位改变值的频率**。 Complexity essentially measures how often neighbouring bits in a segment change value

* 每段长8pixel，宽8pixel，包含64bit

**段中每行&每列（共8行&8列**）For each row/column in a segment (there are 8 rows/column)

* <font color="red">计算像素值变化的次数</font>。 Count the number of times the pixels change value.
* Maximum value is 7, eg `10101010`
  * 每个pixel都不同，变化7次

因此行 & 列加起来的最大变化次数 -> `2 * 8 * 7 =112`

* The maximum number of changes is 2 x 8 x 7 = 112

:orange: 段复杂度`C=实际变化次数/112` the complexity of a segment C = number of actual changes / 112

* **定义一个阈值T，它是算法的一个参数**。 Define a threshold value T, which is a parameter of the algorithm
  * **如果C>T，则该段为复杂段**。 If C > T then the segment is complex.

:orange: 扫描所有24位面，每个红蓝绿位面，扫描其中所有段，为被替换成有效载荷的段计算复杂度

### 还原有效载荷：Recovering the Payload

![](/static/2021-03-07-23-10-04.png)

嵌入算法（BPCS）按顺序遍历所有段。The embedding algorithm looks through all the segments in order.

* 如果段足够复杂，那么它就被有效负载位所代替。If the segment is complex enough then it is replaced by payload bits.
* 不改变非复杂段。Non-complex segments are not changed.

**提取算法以相同的顺序查看所有的段**。The extraction algorithm also looks through all the segments in the same order

* 如果段是复杂的，那么它必须包含有效负载位。If the segment is complex then it must contain payload bits.
* 如果段不复杂，可能有两种原因！
  * **如果它不复杂，那么它可能是伪装图像的一部分**。If it is not complex then it could be part of the cover image.
  * **它也可能是有效载荷的一个非复杂的部分**！It could also be a non-complex part of the payload!

:orange: 因此，非复杂的有效载荷段必须复杂化,才能成功被提取算法提取。Thus non-complex payload segments must be made complex for the extraction algorithm to work.

* 需要在将其嵌入伪装图像之前，对有效载荷进行一些预处理。对每一个64位的有效载荷块都要做复杂处理。看起来像一个复杂的段
* Conjugation

#### 共轭复杂化预处理：Conjugation

对有效载荷的秘密小块做共轭处理（复杂后，再替换原段），处理后使得提取算法可行，因为bit string的每个64bit segements 都复杂

![](/static/2021-03-07-23-16-41.png)

如果一个有效负载段不复杂，**那么它的所有位都将使用国际象棋棋盘模式进行锚定，变得复杂，因为每个相邻位都在变化**。If a payload segment is not complex then all its bits are xored with a chess board pattern.

* 这就是所谓的共轭段 This is called a conjugated segment.
* 可以证明
  * **复杂度(段) + 复杂度(共轭段) = 112**
  * It can be shown that Complexity (segment) + Complexity (conjugated segment) = 112
* **因此，如果 t < 0.5（阈值 threshold），那么配合一个非复杂的片段总是会产生一个复杂的片段**。Thus if T < 0.5, then conjugating a non-complex segment will always produce a complex segment.

### BPCS隐写&提取算法：BPCS Embed & Extract Algorithm

![](/static/2021-03-07-23-24-31.png)
![](/static/2021-03-07-23-25-51.png)

### 共轭图/有效载荷结束: Conjugation Map / End of Payload

已知如何共轭，复杂化一个不复杂的段，**那么如何提取有效载荷后进行撤销共轭** undo the conjugation？

* 还需要一些方法来指示有效载荷结束位置 want some way of indicating when the payload ends.
  * 计算出一个有效载荷的长度和字节，然后放在有效载荷前面，作为长度指示，后面的就是载荷体 We calculate the length of a payload and bytes. And we then stick that on the front of the payload, so the payload first part of the payload is actually a length indication, followed by the contents of the payload. 
  * 所以提取算法可以跟踪我们提取了多少个有效载荷块或段，当我们得到所有的有效载荷块或段时，就会停止 So, when we extract the payload, then the first thing we do is get the first type of payload bits, and that will tell us how long the payload is. And so the extraction algorithm can keep track of how many payload blocks or segments we've extracted and stop when we've got them all

![](/static/2021-03-08-01-49-49.png)

我们仍然需要检测有效载荷段是否被共轭了（复杂化了，因为有些复杂的有效载荷段本身不需要复杂化就可以直接替换段）We still need to detect if a payload segment was conjugated or not.

* 利用1bit记录每个64bit段在哪做了共轭，**建立一个共轭映射，每段有一个位，表示在哪里使用了共轭** 1 bit per segment。 And we need to record, whether conjugation is done or not. So that's one bit of information so the payload segment, we need to create a conjugation map has one bit per segment, indicating where the conjugation was used.
* 共轭图的大小？ how big is the conjugation maps all these 1 or more bits from the conjugation map
* 假设图像是1024 x 1024，那么它包含393,216个片段
  * **一幅1024×1024的图像在每个方向上将有128组8像素，每个位面有128×128=16384段（长8pixel*宽8pixel** Assume the image is 1024 x x1024 then it contains 393,216 segments.
  * 24bit位面一共24*128*128=393216段
* **假设128k (为了保持数学上的简单)的片段是复杂的**, 并且包含有效负载片段 Assume 128k (to keep the maths simple) of the segments are complex and contain payload segments.
* 然后，**共轭映射包含128k 位，每段1位 (（128k bits , 128k segments)** Then the conjugation map contains 128k bits, one per segment.
  * 然后将他们放回64bit段中，这可以存储在2k 的额外片段（共轭图还是有2k段）。This can be stored in 2k extra segments.

---

![](/static/2021-03-08-02-34-01.png)

我们还需要知道什么时候我们已经到达了有效载荷的末端。We also need to know when we have reached the end of the payload.

我们可以对有效载荷进行预处理，计算其长度并根据需要共轭非复杂的片段，生成共轭图。We can pre-process the payload, calculating its length and conjugating non-complex segments as necessary, producing the conjugation map.

共轭映射（超级共轭映射图）和有效载荷长度可以添加到有效载荷的开始。The conjugation map and payload length can be added to the start of the payload.

在我们的示例中，这将在有效载荷的前面添加2k 段（共轭图大小）。In our example, this will add 2k segments to the front of the payload.

* **它们也需要被共轭，形成一个超级共轭图**。They will also need to be conjugated, producing a super- conjugation map.
  * 超级共轭映射，来表明哪些片段需要共轭。indicate which one of those segments needs to be conjugated.
  * 2kbit大小的共轭映射，每个bit对应一个段，这个共轭映射再被共轭

**在我们的示例中，超共轭映射将有32个段长（2k/64）**。In our example, the super-conjugation map will be 32 segments long.

* 递归最终会停止。The recursion will eventually stop.

### 共轭位：Conjugation Bit

![](/static/2021-03-08-02-48-09.png)

**或者，长度可以加到有效载荷的前面。**Alternatively, the length can be added to the front of the payload.

有效载荷分为63位部分。The payload is divided in 63 bit segments.

* 每个片段只包含63位数据。Every segments only contains 63 bits of data.
* **另一个位，比如0位，或者是1，或者是0，这取决于该段是否被共轭**The other bit, say bit 0, is either 1 or 0, depending in whether the segment was conjugated or not.

这更容易实现，也更容易检测与文件的数字检查This is easier to implement but also easier to detect with a digital examination of the file.

* 每个段中的1 bit的值的分布是奇数。One bit position in each segments has an odd distribution of values.

### BPCS容量：BPCS Capacity

![](/static/2021-03-08-02-51-37.png)

The BPCS algorithm can achieve a capacity of between 25% and 50% without visual detection.BPCS算法在没有视觉检测的情况下，可以达到25%到50%的容量。

## 图像类型：Image Types

有许多不同的图像文件格式，这可能会给隐写带来问题(所以无论如何图像是一个很好的虽然他们不是完美的解决方案，因为不同的英文文件格式，有些比别的更容易工作) 

![](/static/2021-03-08-02-53-05.png)

大多数图像在通信之前都要进行压缩。Most images are compressed before being communicated.

**无损数据压缩保留像素值**。A lossless compression preserves the pixel values.

* 精确值可以通过解压恢复。The exact values can be recovered by decompressing.
* 常见的无损算法是.png 和.gif Common lossless algorithms are .png and .gif

**有损数据压缩会失去信息**。A lossy compression looses information.

* 无法恢复精确的像素值。The exact pixel values cannot be recovered.
* 常见的损耗算法是. jpg Common lossy algorithms are .jpg

**利用有损算法进行隐写比较困难，但是可以实现**。Steganography with lossy algorithm is harder, but can be done.

## 加密有效载荷：Encrypted Payload

![](/static/2021-03-08-02-57-35.png)

* **为了增加安全性，可以首先对有效负载进行加密** The payload could be encrypted first for added security.
* 这里描述的技术适用于任何位字符串，**无论是否加密** The techniques described here work with any bit string, whether encrypted or not.
* 加密和隐写可以用两个独立的产品来完成 The encryption and steganography could be done with two separate products.
* **或者，键控隐写产品以密钥作为参数，同时进行加密和隐写** Alternatively, keyed steganographic products take a key as a parameter and do both the encryption and steganography.
  * 大多隐写产品都自动为用户加密，所以需要提供一个加密密钥（AES）

## 隐写分析：Steganalysis

![](/static/2021-03-08-03-00-44.png)

对隐写术的攻击可以有几个目标。Attacks on steganography can have several goals:

* 恢复有效载荷 Recover the payload.
  * 提取隐写的到底是什么
* 替换有效载荷。Replace the payload.
* 销毁有效载荷。Destroy the payload.

视觉攻击会观察图像，看它们是否有包含隐藏的有效载荷的迹象 A visual attack looks at images to see if they show signs of containing a hidden payload.

* 隐写技术是专门设计的（hide in plain sight relies on secrecy on people），使这种攻击变得困难。Steganography techniques are specifically designed to make this attack difficult.

如果你有原始图像，那么可以进行数字像素比较。If you have the original image then digital pixel comparisons can be made.

## 直方图：Histograms

![](/static/2021-03-08-03-04-42.png)

* 直方图计算给定值的像素数，并将它们显示为图形 A histogram counts the number of pixels with a given value and displays them as a graph.
* 自然图像的直方图趋于平滑 Histograms of natural images tend to be smooth.
* BPCS 图像的直方图趋向于有一个低计数的谷，大约在复杂度阈值附近 Histograms of BPCS stego images tend to have a valley, with lower counts, around the complexity threshold.
* LSB 图像的直方图往往在8位的倍数周围有一些尖峰 Histograms of LSB stego images tend to have some spikes around multiples of 8 bits.

## 数字水印：Digital Watermarks

![](/static/2021-03-08-03-07-02.png)

数字水印被添加到图像中。Digital watermarks are added to images.

有时它们是可见的，但通常是不可见的。Sometimes they are visible but are often invisible.

所以隐形数字水印的概念，是在图像中嵌入一些秘密信息，它们用于显示所有权或以其他方式识别图像。They are used to show ownership or otherwise identify images.

* 视频也是如此。所以我们的想法是，你可能会把一些电影分发给一群影评人，让他们在主要发行前对其进行评论，并审阅。

他们使用类似于隐写术的技术。They use techniques similar to steganography.

主要的攻击是试图删除水印。The main attack is to try and remove the watermark.

## Summary

![](/static/2021-03-08-03-11-12.png)