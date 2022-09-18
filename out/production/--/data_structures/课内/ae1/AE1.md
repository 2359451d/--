# AE1

## Specification

java program

* 研究由**五个字母组成的词梯 word ladders composed of 5 letter words**
* 词梯
  * words序列
  * 序列中每个成员与前继节点predecessor相差1个letter
  * 例如，`6`个节点的ladder，将`flour`转换为`bread`

提供一个字典文件dictionary file `words5.txt`

* 包含了2000个 5-letters words
* 用于构建词梯

## Program1

* read in dictionary file
* read 2 more 5-letter 单词
* 所以读取3个用户参数
  * dictionary file
  * start word
  * end word

程序应该在标准输出通道上PrintWriter输出

* 最短路径的长度和
* 将起始字转换为结束字的**最短路径/(梯子, 就是节点间最短路径序列**)，
* 或者应该报告不可能有梯子[如果不存在路径]

🍊 输出最后一行报告**程序执行用时(seconds)**

* (生成此输出的代码包含在所提供的框架程序中
* 仅为运行时间，而不是实际运行时间

## Program2

P1的带权版本

* 权重 - 不匹配letter，在字母表位置的绝对差异
  * 如`angel`和`anger`权重=r位置-l位置，`6`

需要实现迪杰斯特拉算法，求最短路径

* 类似P1，读取字典文件 & start word(5-letter), end word(5-letter)
* 输出
  * minimum distance 最短路径长度，
  * 最短路径序列
  * 执行时长

## Clarification

* 字典文件`words5.txt`
  * 只包含5-letter单词，全小写
  * 每个一行
* 不需要检查输入format
* 假设每个输入都来自字典文件？

## Submission

提交`.tar.gz`

* 命名`guid.tar.gz`，包含文件夹`guid/`
* 文件夹下
  * `report.pdf`
  * `wordladder/`
  * `dijkstra/`

## Report

it should be one to two pages not including the space for the outputs (and include a summary of your design choices and why you used certain data structures).

 a summary of your design choices including why you used certain data structures (one to two pages should certainly be sufficient).

```txt
wordladder样例输出

shortest word ladder of length 5
example shortest word ladder:
    preen
    green
    greed
    breed
    bleed
    blend

word1 = rabid
word2 = gloss
no word ladder exists

-----
java Main words5.txt small large
size of dictionary = 1638
word1 = small
word2 = large
shortest word ladder of length 16
example shortest word ladder:
    small
    shall
    shale
    share
    shard
    chard
    charm
    chasm
    chase
    cease
    tease
    terse
    verse
    verge
    merge
    marge
    large
elapsed time: 79 milliseconds

```

```cpp
class Solution {
public:
    bool compare(string &a,string &b){//判定两个字符串是否只有一个字符不同
        if(a.size()!=b.size())return false;
        int diff=0;
        for(int i=0;i<(int)a.size();i++){
            if(a[i]!=b[i])diff++;
        }
        return diff==1;
    }
    int ladderLength(string st, string end, vector<string>& wordlist) {
        vector<vector<short>>G(wordlist.size()+1,vector<short>(wordlist.size()+1,0));
        int sti=(wordlist.size()),endi=-1;//起始字符串不在数组中，放在邻接表的最后一行、一列
        for(int i=0;i<(int)wordlist.size();i++){
            if(wordlist[i]==end){//寻找目标单词的序号
                endi=i;
                break;
            }
        }
        if(endi==-1)return 0;//如果目标单词不存在，不可能有解，直接返回0；
        for(int i=0;i<(int)wordlist.size();i++){//利用字符串之间的差异生成邻接表
            if(compare(st,wordlist[i])){
                G[i][sti]=1;G[sti][i]=1;
            }
        }
        for(int i=0;i<(int)wordlist.size()-1;i++){
            for(int j=i+1;j<(int)wordlist.size();j++){
                if(compare(wordlist[i],wordlist[j])){
                    G[i][j]=1;G[j][i]=1;
                }
            }
        }
        //Dijkstra算法核心部分：
        vector<short>len(G[sti]);//初始化各个单源最短路径
        vector<short>mark(len.size(),0);//字符串的访问标记
        for(int i=0;i<(int)len.size();i++){
            int minn=INT_MAX;
            int index=0;
            for(int j=0;j<(int)len.size();j++){
                if(mark[j]==0&&len[j]>0&&len[j]<minn){
                    minn=len[j];
                    index=j;
                }
            }
            mark[index]=1;
            for(int j=0;j<(int)G[index].size();j++){
                if(G[index][j])len[j]=len[j]==0?len[index]+G[index][j]:min((int)len[index]+G[index][j],(int)len[j]);
            }
        }
        return len[endi]==0?0:len[endi]+1;
    }
};

```

