# P

## P1: Specification

### list

某数组`list`有`nlists`个元素

* 初始`list[0...nlist]`为空
* 初始值 `nlist=5`
* **每个元素**为某升序数字线性表**头部指针**
  * 通过头指针遍历，`header.next`

### recordsArray

`record[0...n]`

* 每个record对象代表一种commands
* 3个字段
  * `code` - 功能码
  * `i` - 索引，获取列表**头指针**
  * `x` - 值

功能码`code`
![](/static/2020-03-27-02-42-15.png)

* code=0，结束程序
* code=1，在`list[i]`位置插入元素`x`，维护列表顺序
* code=2，如存在，删除第一个`list[i]`中出现的元素`x`
* code=3，搜索。**查找成功，打印 “Found”，否则“Not Found”**, (转askii)
* code=4, 打印`list[i]`元素

### EXERCISE program

已经实现

* 建堆
* 遍历数组（loop）
* commad： `terminate(0) & insert(1)`

未实现

* command: `delete(2), search(3),print(4)`