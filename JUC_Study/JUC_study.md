# JUC（concurrent API）

* `java.util.concurrent`
* `java.util.concurrent.locks`
  * `ReentrantLock`可重入锁

[针对多condition理解的参考](https://blog.csdn.net/u010862794/article/details/72892300)

- [JUC（concurrent API）](#jucconcurrent-api)
  - [SaleTicket Example](#saleticket-example)
  - [Collections: Thread-Safe Problem](#collections-thread-safe-problem)
    - [CopyOnWriteArrayList/Set](#copyonwritearraylistset)
      - [add](#add)
    - [ConcurrentHashMap](#concurrenthashmap)
  - [TimeUnit](#timeunit)
  - [Eight Locks](#eight-locks)
  - [Producer & Consumer](#producer--consumer)
    - [Condition](#condition)
      - [CAS](#cas)
    - [ReentrantLock](#reentrantlock)
      - [AQS](#aqs)
      - [Signal bit 标志位](#signal-bit-标志位)
      - [Producer & Consumer Example](#producer--consumer-example)
  - [Callable interface](#callable-interface)
    - [FutureTask<V >](#futuretaskv-)
  - [JMM](#jmm)
    - [可见性例子 - volatile](#可见性例子---volatile)
  - [加载代码练习](#加载代码练习)

## SaleTicket Example

```java
/* Resource Class */
class Ticket{
    private int number=30;
    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number>0){
                System.out.println(Thread.currentThread().getName()+"\t卖出第："+(number--)+"\t 还剩下："+number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

public class TestConcurrent {

    @Test
    public static void main(String[] args) {
        // resource class
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40; i++) {
                    ticket.sale();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40 ; i++) {
                    ticket.sale();
                }
            }
        }, "C").start();
    }
}
```

## Collections: Thread-Safe Problem

多线程同时操作同一<font color="red">非线程安全集合</font>时，可能出现

* `ConcurrentModificationException`异常

🍊 传统解决方法

* 使用线程安全的集合
  * 如`Vector`，`HashTable`低效
* 通过工具类静态方法转换得到线程安全的集合
  * `Collections.synchronizedList(List<T >)`
  * `Collections.synchronizedList(Set<T >)`
  * `Collections.synchronizedList(Map<T >)`

### CopyOnWriteArrayList/Set

🍬 <font color="red">优化解决方式</font>

* 写时复制`CopyOnWriteArrayList()`
  * 实现了`List`接口
  * 内部有`ReentrantLock`
    * 如add时需要加锁，否则会copy多个副本
    * get，读时不加锁
  * 底层是`volatile transient array`

🍊 适用读多写少的并发场景

* CopyOnWrite容器只能保证数据的最终一致性，**不能保证数据的实时一致性**
* <font color="red">效率比Vector高，因为仅增删改上加锁，读不加锁</font>
* <font color="red">内存开销大</font>

🍬 同时提供了`CopyOnWriteArraySet`，不支持MAP

#### add

![](/static/2020-08-24-17-01-07.png)

```java
/*
1. 先抢锁
2. 获取旧数组
3. 创建新数组 - 长度=旧数组+1
4. 插入元素至末尾
5. 修改引用指向新数组
6. return true
7. 释放锁
*/
public boolean add(E e) {
    synchronized (lock) {
        Object[] es = getArray();
        int len = es.length;
        es = Arrays.copyOf(es, len + 1);
        es[len] = e;
        setArray(es);
        return true;
    }
}
```

### ConcurrentHashMap

🍊 传统`Hashtable`线程安全但效率低

* 使用`synchronized`保证线程安全

🍊 使用线程安全的HashMap

* `ConcurrentHashMap`

## TimeUnit

可用于定义休眠时间

![](/static/2020-08-27-15-31-11.png)

* `TimeUnit.HOURS.sleep(4)`

## Eight Locks

八锁问题/场景

[reference](https://www.cnblogs.com/itiaotiao/p/12651573.html)

1. 标准访问
   1. 一个资源类，Thread实例进行操作
   2. **如不强制sleep(), 执行顺序由CPU决定**
   3. ![](/static/2020-08-27-15-36-03.png)
2. sendEmail()休眠后,执行顺序?
   1. ![](/static/2020-08-27-15-45-34.png)
3. 前面基础上新增普通方法,无同步修饰符,执行顺序?
   1. ![](/static/2020-08-27-15-47-26.png)
4. 两个资源类,执行顺序?
   1. ![](/static/2020-08-27-15-49-40.png)
5. 两个**静态同步方法**,**一个资源类**,执行顺序?
   1. ![](/static/2020-08-27-15-52-25.png)
   2. 锁住的是类模板字节码对象,所以先sendEmail
6. 两个**静态同步方法**,两个资源类,执行顺序?
   1. 锁住的是类模板字节码对象,所以先sendEmail
7. 一个**静态同步方法&一个普通同步方法&一个资源类**,执行顺序?【错】
   1. ![](/static/2020-08-27-15-56-45.png)
   2. 锁不同,一个锁的资源类模板字节码对象,一个锁操作的资源类对象.互不影响
8. 一个**静态同步方法&一个普通同步方法&两个资源类**,执行顺序?
   1. ![](/static/2020-08-27-15-59-06.png)
   2. 锁不同,一个锁的资源类模板字节码对象,一个锁操作的资源类对象.互不影响

## Producer & Consumer

生产者消费者问题

* 高内聚低耦合前提，线程交替操作资源类
* 判断，通知
* <font color="red">防止虚假唤醒,多线程的判断不能使用if，需要while重新进入判断</font>
  * 如使用if，可能出现，重新获得锁后，按wait后语句执行，**直接跳过了再次判断仓库容量**
  * ![](/static/2020-08-27-16-45-24.png)

### Condition

配合可重入锁ReentrantLock使用【<font color="red">可以一个锁，多个condition对象</font>】
![](/static/2020-08-27-17-18-40.png)

🍊 获取重入锁的Condition对象

* `public Condition newCondition()`

🍊 condition方法 -wait & notify

* `void await() throws InterruptedException`
* `void signal()`
* `void signalAll()`

#### CAS

[参考](https://blog.csdn.net/u010862794/article/details/72892300)

> CAS(Compare And Swap)，即比较并交换。**是解决多线程并行情况下使用锁造成性能损耗的一种机制**，CAS操作包含三个操作数——内存位置（V）、预期原值（A）和新值(B)。如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值。否则，处理器不做任何操作。无论哪种情况，它都会在CAS指令之前返回该位置的值。CAS有效地说明了“我认为位置V应该包含值A；如果包含该值，则将B放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可

* `if V=A set B`

### ReentrantLock

#### AQS

**ReentrantLock内部维护AQS同步队列（双向链表），Condition队列为单向链表**

[参考](https://blog.csdn.net/u010862794/article/details/72892300)
[参考2](https://zhuanlan.zhihu.com/p/97292945)

🍬 <font color="red">OS角度来看，应有阻塞队列，就绪队列等，此处实现简化成一个AQS同步执行队列 & Condition等待队列</font>

![](/static/2020-08-27-21-33-57.png)
![](/static/2020-08-27-21-32-35.png)

🍊 默认生成非公平锁，**维护一个AQS同步队列&多个Condition等待队列**，支持分组唤醒线程，精确唤醒，绑定多条件condition对象【<font color="red">以下都要根据抢占规则决定</font>】

* 构造方法传入`true`为公平锁，传入`false`为非公平锁
* <font color="blue">lock()调用，根据抢占规则，线程间进行抢占。【未获取锁的线程会直接创建新节点移入AQS同步队列，并挂起进入waiting状态必须强制唤醒】</font>
* <font color="blue">await()调用，创建当前线程的新节点加入对应Condition队列。释放锁，【并且唤醒AQS同步队列中的一个线程（如有，使挂起状态waiting->blocked重新进行锁抢占）】</font>
* <font color="blue">signal()调用，先验证当前线程是否获得锁否则抛异常。将对应Condition队列中首部线程节点移除，加入AQS同步队列进行竞争。【并唤醒AQS同步队列中的一个线程（如有，使挂起状态waiting->blocked重新进行锁抢占）】</font>
* <font color="red">如果为默认非公平锁，进行抢占，失败则移至阻塞队列（？下次仍可分配CPU进行抢占）。如为公平锁，直接移至同步队列尾部</font>

🍬 与synchronized区别

* 构成
  * synchronized关键字属于VM层面，wait/notify依赖底层monitor对象
  * Lock属于`java.util.concurrent.locks.lock`，api层面
* 手动释放 vs 自动释放
* synchronized不可中断，ReentrantLock可中断

#### Signal bit 标志位

🍬 以前传统synchronized是乱抢锁

* <font color="red">ReentrantLock可通过<b>标志位</b>实现定点通知（如，按照顺序控制谁抢到锁，精准通知某个线程）</font>

🍊 例子，如控制 A打印5次，B打印10次，C打印15次

* <font color="red">三个condition对象，用于控制一定顺序，而不是唤醒所有线程</font>
* **注意一定先修改标志位**

![](/static/2020-08-27-21-10-07.png)
![](/static/2020-08-27-21-10-23.png)

```java
class Ticket{
    private int number=1; // signal bit -  a: 1, b: 2, c: 3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printA(){
        // 5 times
        lock.lock();
        try {
            // signal bit!=1
            while(number!=1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try {
            while(number!=2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try {
            while(number!=3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class TestConcurrent {

    @Test
    public static void main(String[] args) {
        // resource class
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    ticket.printA();
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    ticket.printB();
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    ticket.printC();
                }
            }
        }, "C").start();
    }
}
```

#### Producer & Consumer Example

🍊 **ReentrantLock改进写法**

![](/static/2020-08-27-17-18-40.png)

* <font color="red">默认生成非公平锁，传统synchronized的是随机抢，<b>ReentrantLock是定点通知</b></font>
* 不能再配合使用wait&notify，配合使用`condition`对象（可替代所有wait&notify方法）
  * `condition.await()`
  * `condition.signalAll()`

```java
class Ticket{
    private int number=0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    // producing method
    public void produce(){
        lock.lock();
        try {
            while(number>0){
                // stop producing, stock full
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"\t生产后："+(number) +"\t张票");
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    // consuming method
    public void sale() {
        lock.lock();
        try {
            while (number <= 0) {
                // stop consuming, out of the stock
                condition.await();
            }
            // consuming here
            number--;
            System.out.println(Thread.currentThread().getName() + "\t卖出后：" + (number) + "\t张票");
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class TestConcurrent {
    @Test
    public static void main(String[] args) {
        // resource class
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40; i++) {
                    ticket.produce();
                }
            }
        },"Producer").start();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) {
                ticket.sale();
            }
        }, "Consumer").start();
    }
}
```

## Callable interface

带返回值的线程

* 类实现`Callable`函数式接口
  * `V call() throws Exception`
* Thread表面不提供能传Callable的实现的构造函数，可以传`RunnableFuture`子接口
  * 实现类`FutureTasK`，支持传参`FutureTask(Callable<V > callable)`

### FutureTask<V >

支持传参`Callable<V >` & `Runnable`

![](/static/2020-08-27-22-19-05.png)

* 继承了Runnable接口，可以传入Thread【一般配合Callable使用】
* `futuretask.get()`获取返回值`<V >`

## JMM

java内存模型，

![](/static/2020-08-30-22-50-45.png)

* <font color="red">本身是一种抽象概念（多线程访问内存的一种模型），一种规范，该规范中定义了程序中各个变量的访问方式</font>
  * 实例字段，静态字段，构成数组对象的元素
* <font color="blue">每个线程分配一个占空间，对变量操作，必须先拷贝进自己的内存空间，操作完毕再写回主内存（可看为main方法的栈）</font>
  * 线程间通信（传值），必须通过主内存完成，算一种访问机制

一般涉及`volatile`

* vm提供的**轻量级**同步机制，轻量指
  * **保证可见性**
  * 不保证原子性
  * 禁止指令重排

🍊 JMM规范

* 可见性
  * 即，通知机制，A改了B立马知道
* 原子性
* 有序性

### 可见性例子 - volatile

线程操作资源类例子
![](/static/2020-08-30-23-02-03.png)
![](/static/2020-08-30-23-02-31.png)

* 以上代码，无通知机制，对main不可见，不会跳出main的循环
* 主要关注，需要存在一种机制，当线程A值修改之后，立马通知main跳出循环

🍬 <font color="red">成员变量加上`volatile`，保证可见性</font>

## 加载代码练习

![](/static/2020-08-30-23-10-49.png)

优先

* 静态>构造块>构造方法