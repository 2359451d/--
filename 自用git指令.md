# 自用git指令
---
目录
-    [流程图](#流程图)
-    [零散指令](#零散指令)
-    [删除相关](#删除相关)
     -   [恢复push前](#恢复push前)
     -   [恢复push后](#恢复push后)
 -   [本地&远程同时修改文件](#本地&远程同时修改文件)
     -   [merge问题相关](#merge%e9%97%ae%e9%a2%98%e7%9b%b8%e5%85%b3)
         -   [保留两端代码,进行确认修改](#%e4%bf%9d%e7%95%99%e4%b8%a4%e7%ab%af%e4%bb%a3%e7%a0%81%e8%bf%9b%e8%a1%8c%e7%a1%ae%e8%ae%a4%e4%bf%ae%e6%94%b9)
             -   [暴力合并(舍弃本地代码)](#%e6%9a%b4%e5%8a%9b%e5%90%88%e5%b9%b6%e8%88%8d%e5%bc%83%e6%9c%ac%e5%9c%b0%e4%bb%a3%e7%a0%81)

---
##   流程图
![workflow](./static/1.png)

![workflow2](./static/2.png)

##  零散指令

>   查看**当前已追踪的文件**, index中
    
```
    git ls-files --stage
```
>   删除    **本地分支 & 远程分支**

```
    git branch -d <branchname>
    git push <remote_repo> --delete <branchname>
```

## 删除相关 
>当需要删除index&工作区文件

```
//即 rm真实删除FS中文件后，向远程repo推送更新
git rm <filename>
git commit -m
git push
```

>当只需要删除index&远程repo中文件， 不接触本地FS

```
git rm --cached <filename>
git commit -m
git push
```

## 恢复文件
### 恢复push前
>假设此时有误删文件情况，
- 此时**都未进行**``git push``操作
  
  - **未进行**``git commit -m``

    ```
    可直接通过 git checkout <filename>进行文件恢复
    ```
  - **已进行** `git commit -m`
    ```
    通过 git reset (--hard) HEAD +<filename> 指令进行文件恢复

### 恢复push后
>此时已进行`git push`操作, 无法直接恢复。
>> **通过版本号退回**

    git reset -hard <sequence>

##  本地&远程同时修改文件
### merge问题相关
---
####    保留两端代码,进行确认修改

>当远程仓库文件 1.txt 被修改

``remote/origin/master 1.txt`` 内容

      远程仓库新增文字

>当本地仓库文件 1.txt 被修改

``master 1.txt`` 内容

    本地仓库新增文字

此时假设想merge 1.txt内容 -> ``本地仓库新增文字``

>由于已经更改了本地仓库的文件，无法直接pull,
可以通过 
-   commit后再拉取远程repo修改需要merge的文件
    -   此时前后共会有两次commit
        -   unmerged file commit (本次需要commit后pull进行文件内容修改后产生)
        -   file after merging commit (pull产生,此阶段进行远程本地仓库同时修改文件的合并)
    -   ```git
        git commit -m
        git push <><>   \\push失败，但此步骤后, log中已存入本次commit信息
        git pull    \\拉取最新remote代码,进行合并修改
        ```
-   通过``git stash``暂存当前工作区内存
    -   该步骤只会有一次commit
    -   ``git diff <branch>``可以查看当前分支与fetch后分支的差异
    -   ```git
        git stash   //暂存当前工作区
        git pull origin master  //拉取最新文件
        git stash pop   //合并代码, 可以进行修改
        ```

---
####    暴力合并(舍弃本地代码)
>   即最终代码,文件以远程分支为准

 ``remote/origin/master 1.txt`` 内容

      远程仓库新增文字

```
git reset --hard
git pull
```