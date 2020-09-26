# Shell Study

- [Shell Study](#shell-study)
  - [Shell脚本执行方式](#shell脚本执行方式)
  - [变量](#变量)
    - [变量定义](#变量定义)
    - [设置环境变量](#设置环境变量)
    - [多行注释](#多行注释)
    - [位置参数变量](#位置参数变量)
    - [预定义变量](#预定义变量)
  - [运算符](#运算符)
  - [条件判断](#条件判断)
    - [判断语句](#判断语句)
      - [常用判断条件](#常用判断条件)
    - [流程控制：if](#流程控制if)
    - [流程控制：case](#流程控制case)
    - [流程控制：for](#流程控制for)
    - [流程控制：while](#流程控制while)
  - [read：读取输入](#read读取输入)
  - [function](#function)
    - [系统函数：basename](#系统函数basename)
    - [系统函数：dirname](#系统函数dirname)
    - [自定义函数](#自定义函数)
    - [综合案例](#综合案例)

![](/static/2020-09-26-13-15-45.png)

## Shell脚本执行方式

![](/static/2020-09-26-13-19-07.png)

- 以`#!/bin/bash`开头
  - 指明用哪个shell来执行，此例，为bashell
- <font color="red">脚本需要有可执行权限`x`</font>
  - <font color="blue">不给`x`权限要执行的时候，需要指明执行的shell，`sh /myShell.sh`</font>**，不推荐**

🍊 执行方式

- 相对路径
  - `./file.sh`
- 绝对路径
  - `/fulldir/file.sh`

## 变量

🍊 分类

- 系统变量
  - 输出系统变量`echo $HOME`
- 用户自定义变量

🍬 显示**shell所有变量**

- `set`

### 变量定义

基本语法

- 定义自定义变量
  - `var=value`
- 撤销变量
  - `unset var`
- 定义静态变量（**不能unset**）
  - `readonly A=99`

🍬 变量名**一般大写**

- 变量名&值之间不能有空格

🍊 将命令的**返回值赋给变量**

![](/static/2020-09-26-14-21-52.png)

- 反引号
- `A=$(date)`

### 设置环境变量

可以供其他shell脚本用

![](/static/2020-09-26-14-24-05.png)

🍊 基本语法

- `export var=value`
  - 输出变量为**环境变量**
- `source 配置文件`
  - <font color="red">定义环境变量后，需要`source dir`或重启&注销后使环境变量生效</font>
- `echo $var`

### 多行注释

```shell
:<<!
    ...
!
```

### 位置参数变量

![](/static/2020-09-26-16-38-23.png)

- 可以获取**命令行参数信息**
  - 如`./myshell.sh 100 200`，有时候需要获取后面两个参数

🍊 基本语法

- `$数字`
  - `$0`命令本身
  - `$1-9`1-9个参数
  - <font color="red">10以上的参数需要用大括号`${10}`</font>
- `$*`，代表命令行中**所有参数，看作一个整体**
- `$@`，代表命令行中**所有参数，分别看待**
- `$#`，代表命令行中**所有参数的个数**

🍊 例子

![](/static/2020-09-26-16-48-23.png)

### 预定义变量

![](/static/2020-09-26-16-48-58.png)

- shell中事先定义好的变量，**可以直接在shell脚本中使用**

🍊 基本语法

- `$$`
  - **当前进程的PID**
- `$!`
  - **后台运行**的**最后一个进程的PID**
  - 后台方式运行`./myShell.sh &`
- `$?`
  - **最后一次执行的命令的返回状态**
  - `0` - 成功
  - 其他 - 不成功

🍊 例子

![](/static/2020-09-26-16-56-24.png)

## 运算符

![](/static/2020-09-26-16-59-37.png)

🍊 基本语法

- `$((运算式))`或
- **`$[运算式]`**
- `expr m + n`
  - 要用飘号``
- `expr m - n`
  - 要用飘号``
- `expr \* / %`
  - 要用飘号``

🍊 例子

![](/static/2020-09-26-17-08-19.png)
![](/static/2020-09-26-17-10-15.png)

## 条件判断

### 判断语句

![](/static/2020-09-26-17-11-28.png)

🍊 基本语法

- 注意空格
  - `[ condition ]`
  - 非空返回true，**可用`$?`验证最后一次执行命令的执行状态，`0`为`true`，其他为`false`**

🍊 例子

- `[ atguigu ]`
  - true
- `[]`
  - false
- `[ condition ] && echo OK || echo no`
  - 类似3目操作

#### 常用判断条件

![](/static/2020-09-26-17-24-00.png)

- 字符串比较
  - `=`
- int比较
  - `-lt`
  - `-le`
  - `-eq`
  - `-gt`
  - `-ge`
  - `-ne`
- 文件权限判断
  - `-r`
  - `-w`
  - `-x`
- 文件类型判读
  - `-f`常规文件
  - `-e`文件存在
  - `-d`文件是目录

🍊 例子

- ok=ok？（字符串）

```shell
if [ "ok" = "ok" ]
then
    echo "equal"
fi
```

- 23是否>22

```shell
#!/bin/bash

if [ 23 -gt 22 ]
then
    echo "greater"
fi
```

- 某文件是否存在

```shell
if [ -e /root/shell/aaa.txt ]
then
    echo "exists"
fi
```

### 流程控制：if

![](/static/2020-09-26-17-40-08.png)

基本语法

```shell
if [ condition ];then
    ...
fi
```

----

推荐使用👇

```shell
if [ condition ]
then
    ...
elif [ condition ]
then
    ...
fi
```

🍊 例子

```shell
#!/bin/bash

if [ $1 -ge 60 ]
then
    echo "pass"
elif [ $1 -lt 60 ]
    echo "fail"
fi
```

### 流程控制：case

![](/static/2020-09-26-17-44-45.png)

```shell
case $var in
"value")
    ...
;;
"value")
    ...
;;
"value")
    ...
;;
```

🍊 例子

```shell
#!/bin/bash

# 命令行参数为1，输出周一，2为周二，其他other
case $1 in
"1")
    echo "Mon"
;;
"2")
    echo "Tue"
;;
*)
    echo "other"
;;
```

### 流程控制：for

![](/static/2020-09-26-18-18-21.png)

基本语法

```shell
for var in value1 value2 value3...
do
    ...
done
```

```shell
for((initvalue;condition;change))
do
    ...
done
```

🍊 例子

加输出，区别位置参数`$*`&`$@`

```shell
#实用$*
for i in "$*"
do
    echo "num is $i"
done

#使用$@
for i in "$@"
do
    echo "num is $@"
done
```

```shell
SUM=0
for((i=1;i<100;i++))
do
    SUM=$[$SUM+$i]
done
echo "sum: $SUM"
```

### 流程控制：while

![](/static/2020-09-26-20-20-19.png)

```shell
while [ condition ]
do
    ...
done
```

🍊 例子

```shell
#输入n，统计1~n的连加
SUM=0
i=0
while [ $i -le $1]
do
    SUM=$[$SUM+$i]
    i=$[$i+1]
done
echo "sum=$SUM"
```

## read：读取输入

![](/static/2020-09-26-20-28-39.png)

- `read -p var`
  - 带提示符
- `read -t var`
  - 超时不等待

🍊 例子

读取控制台输入num

```shell
#!/bash/sh
read -p "please type a num" NUM1
echo "value=$NUM1"

```shell
#!/bash/sh
# TIMEOUT: NOT WAITING
read -t 10 -p "please type a num" NUM1
echo "value=$NUM1"
```

## function

分类

- 系统函数
- 用户自定义函数

### 系统函数：basename

![](/static/2020-09-26-20-32-20.png)

- 通常用于**获取文件名**
  - 返回完整路径的最后`/`部分

![](/static/2020-09-26-20-49-33.png)

- `basename dir [suffix]`
  - 返回最后`/`后面的部分
  - **如果有后缀名suffix**，则忽略其后缀

### 系统函数：dirname

![](/static/2020-09-26-20-57-47.png)

- `dirname dir`
  - 返回最后`/`前面的部分

![](/static/2020-09-26-20-59-17.png)

### 自定义函数

```shell
[ function ] funname[()]
{
    Action;
    [return int;]
}

```

🍊 例子

```shell
#计算两数之和
fucntion sum[()]
{
    SUM=[$n1+$n2]
    echo "sum: $SUM"
}
read -p "please type:" n1
read -p "please type:" n2

# call the func
sum $n1 $n2
```

### 综合案例

![](/static/2020-09-26-21-06-52.png)

每天凌晨2：10备份数据库 到`/data/backup/db`

```shell
#!/bin/bash

#backup
BACKUP=/data/backup/db
DATETIME=$(date +%Y_%m_%d_%H%M%S)

tar -zcvf $(DATATIME) $BACKUP
echo "开始备份"
echo "备份路径 $BACKUP/$DATETIME.tar.gz"

HOST=localhost
DB_USER=root
DB_PASS=12345
DB=test
#if backup dir doesnt exist, create one
[ ! -d "$BACKUP/$DATETIME" ] && mkdir -p "$BACKUP/$DATETIME"
mysqldump -u${DB_USER} -p${DB_PASS} --host=$HOST $DB | gzip > $BACKUP/$DATETIME/$DATETIME.sql.gz
cd $BACKUP
tar -zcvf $DATETIME.tar.gz $DATETIME
#remove temp
rm -rf $BACKUP/DATETIME

### remove files which are created 10 days ago
find $BACKUP -mtime +10 -name "*.tar.gz" -exec rm -rf {} \;
echo "backup finished"
```

- `-exec rm -rf {} \`固定写法，找到删除

```shell
#crontab 中
10 2 * * * 
```