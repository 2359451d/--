# JS Basics Study

Revision, records only for knowledge which is easily forgotten

## Declaration

1. 头部文件`script`标签定义
2. 头部文件引入外部js
   1. `<script type="text/javascript" src="1.js"></script>`

## Variable

![](/static/2020-09-27-22-17-35.png)

🍊 定义

- `var name;`

🍊 特殊值

- `undefined`
  - **未赋值都为undefined**
- `null`
- `NAN`
  - **非数字，非数值**

🍬 `typeof(var)`

- **返回变量的数据类型**

## Array

定义

```js
var name1 =[];//empty array
name1[0] = 12;
name1[1] = 'haha';
alert(name1.length);//2

var name = [1,'abc',true];

// iterate
for (var i=0;i<arr.length;i++){
    alert(arr[i]);
}
```

## Function

🍬 <font color="red">Method Overloads are not allowed in JS!!!!!</font>

🍊 definition method

```js
function name(args...){
    // body
}
// or

var func_name = function(args...){
    //body
}
```

🍊 example

```js
function fun(){
    alert("non args");
}

// call
fun();

var func2 = function (a,b){
    alert("a: " +a + ",b: "+b);
}

fun2(12,"abc");

function sum(a,b){
    var result =a+b;
    return result;
}

alert(sum(1,2));

```

### Hidden arguments: arguments

隐形参数，function中可以直接用

![](/static/2020-09-27-22-32-37.png)

- 类似java基础中可变长参数`...args`

🍊 check these fields in func

- `arguments.length`
- `arguments[0]`
- `arguments[1]`
- ...

### Customise Object

自定义对象

🍊 方式1

```js
var name = new Object(); //null
name.field = value; // define a property
name.func_name = function(){
    // ...
}
```

🍊 方式2（{}形式定义）

```js
var name ={
    field:value,
    field:value,
    func_name: function(){
        //...
    }
}

// to access it

name.field;
name.func_name();
```

## Events

什么是事件注册/绑定？

- 告诉浏览器，事件响应后执行哪些操作

🍊 event register categories

1. static register
   1. gained according to the tags' event property and direclty grant it to the response code通过html标签的事件属性直接赋予事件响应代码
2. dynamic register
   1. gain the dom object by js通过js获取dom对象
   2. register the events by dom object `dom_obj.event = function(){}`通过dom对象绑定事件响应代码

🍊 example

```js
function onLoadFun(){
    // static register
    alert('static');
}

window.onload = function(){
    alert('dynamic');
}
```

### onclick

![](/static/2020-09-27-23-05-37.png)
![](/static/2020-09-27-23-05-51.png)

### onblur

lose the focus失去焦点事件

![](/static/2020-09-27-23-06-43.png)