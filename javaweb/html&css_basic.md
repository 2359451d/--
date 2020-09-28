# HTML & CSS

Revision, records only for knowledge which is easily forgotten

## HTML

### table

```html
<!-- cellspacing 设置单元格间距 -->
<!-- 
    tr 是行标签
    th 是表头标签
    td 是单元格标签
    align 设置单元格文本对齐方式(table下是表格相对页面对齐方式)
 -->

 <!-- 
     colspan 跨列
     rowspan 跨行
  -->

<table border="1" width="300" height="300" align="left" cellspacing="5">
    <tr>
        <th alight="center"><b>1</b></th>
        <th>2</th>
        <th>3</th>
    </tr>
    <tr>
        <td>1</td>
        <td>2</td>
        <td>3</td>
    </tr>
</table>
```

### iframe

内嵌窗口

ifarme 标签它可以在一个 html 页面上,打开一个小窗口,去加载一个单独的页面

![](/static/2020-09-27-22-02-12.png)

### form

表单

![](/static/2020-09-27-22-04-59.png)

## CSS

### 外部引入css

`link`标签

![](/static/2020-09-27-22-07-42.png)

- 增强代码复用

### id & class selector

![](/static/2020-09-27-22-09-18.png)
![](/static/2020-09-27-22-09-34.png)

### group selector

组合选择器

![](/static/2020-09-27-22-10-18.png)

### div center

```css
margin-left:auto
margin-right:auto
```

### text center

```css
text-aligh:center
```

### table css

细线

```css
table{
    border:1px solid black;/*设置边框*/
    border-collapse:collapse;/*边框合并*/
}
td,th{
    /*行&表头*/
    border:1px solid black;
}
```