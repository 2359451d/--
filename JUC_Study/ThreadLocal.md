# ThreadLocal

> 官方描述：用于提供**线程内部局部变量**。
>
> 允许在多线程环境下访问（get&set）时，保证各个线程变量相对独立于其他线程内变量
>
> 作用：
>
> * **提供线程内局部变量**
> * **不同线程间不会相互干扰**
> * **减少同一线程内传递公共变量的复杂度**

ThreadLocal学习

* 线程并发
* 传递数据
  * 可以通过ThreadLocal在同一线程内，不同组件中传递公共变量
* 线程隔离
  * 每个线程的变量都独立，不会互相影响

## 基本使用

常用方法

* `ThreadLocal()`创建对象
* `public void set(T set)`设置**当前线程**绑定的局部变量
* `public T get()`获取**当前线程**绑定的局部变量
* `public void remove()`移除**当前线程**绑定的局部变量

### Example 1

需求

* 线程隔离
  * 多并发情况下，每个线程中变量相互独立
  * A：设置变量1，只能获取变量1
  * B：设置变量2，只能获取变量2

以下写法输出正确为巧合，因为多个线程操作同一资源类，**且无锁**。此例加锁也可以实现，但效率不一定有ThreadLocal高，使用场景也不一样

![](/static/2020-09-02-19-19-35.png)
![](/static/2020-09-02-19-19-56.png)

使用synchronized解决

![](/static/2020-09-02-19-33-35.png)

---

使用ThreadLocal解决

![](/static/2020-09-02-19-29-11.png)

### Example 2

SimpleDateFormat的应用，

* 多线程下，把字符串转换(解析`parse()`)为日期对象
* **每个线程需要定义一个SimpleDataFormat对象**

![](/static/2020-09-02-20-35-28.png)

## ThreadLocal vs synchronized

**都用于处理多线程并发访问变量**，但处理角度&思路不同

![](/static/2020-09-02-19-38-26.png)

* synchronized
  * exchange the space with time
  * **one copy of variable**
  * multi-threads **queued access**
* ThreadLocal
  * exchange the time with space
  * <font color="red">variable copy for each threads, without interfere</font>
  * enable **the insulation of data** between threads

## ThreadLocal: 初始值 initial value

单纯定义ThreadLocal会返回`null`
![](/static/2020-09-02-20-45-02.png)

一般，如果无初始值，则设置
![](/static/2020-09-02-20-46-16.png)

---

🍊 如何设置ThreadLocal初始值？

* 定义ThreadLocal子类，覆写方法

```java
static class SubThreadLocal extends ThreadLocal<Date>{
    // 重写initialValue()方法,设置初始值
    @Override
    protected Date initialValue(){
        // return super.initialValue();
        return new Date(System.currentTimeMillis());
    }
}
```