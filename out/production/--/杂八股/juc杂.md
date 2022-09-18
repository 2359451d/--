# Content

* [Content](#content)
* [线程vs进程](#线程vs进程)
* [线程优先级：thread priority](#线程优先级thread-priority)
* [shutdown hook](#shutdown-hook)
* [busy spinning、busy waiting: 忙等待机制](#busy-spinningbusy-waiting-忙等待机制)
* [JVM线程调度器，时间片：Thread Scheduler, time slicing](#jvm线程调度器时间片thread-scheduler-time-slicing)
* [上下文切换：context switching](#上下文切换context-switching)
* [类锁，对象锁：class lock/object lock](#类锁对象锁class-lockobject-lock)
* [用户线程和守护线程有什么区别？difference between User thread and Daemon thread](#用户线程和守护线程有什么区别difference-between-user-thread-and-daemon-thread)
* [创建守护线程](#创建守护线程)
* [wait() vs sleep()](#wait-vs-sleep)
* [notify(), notifyAll()](#notify-notifyall)
* [wait()为什么不定义在Thread中](#wait为什么不定义在thread中)
* [Runnable vs Callable](#runnable-vs-callable)
* [start(), run()](#start-run)
* [join()](#join)
* [死锁deadlock](#死锁deadlock)
* [避免死锁](#避免死锁)
* [Volatile](#volatile)
  * [volatile禁用指令重排](#volatile禁用指令重排)
  * [volatile不保证原子性](#volatile不保证原子性)
* [(*) Volatile vs transient关键字](#-volatile-vs-transient关键字)
* [解释一下synchronization机制？](#解释一下synchronization机制)
  * [3种使用方式](#3种使用方式)
  * [底层原理](#底层原理)
  * [sync优化](#sync优化)
* [构造方法不能加synchronized](#构造方法不能加synchronized)
* [(*)sync对比volatile](#sync对比volatile)
* [(*)可重入锁：sync对比ReentrantLock](#可重入锁sync对比reentrantlock)
* [================](#)
* [ThreadLocal](#threadlocal)
  * [用法](#用法)
  * [原理](#原理)
* [ThreadLocal内存泄漏](#threadlocal内存泄漏)
* [================](#-1)
* [线程饥荒：thread starvation](#线程饥荒thread-starvation)
* [活锁：Live Lock](#活锁live-lock)
* [并发容器：BlockingQueue](#并发容器blockingqueue)
  * [ArrayBlockingQueue，LinkedBlockingQueue，PriorityBlockingQueue](#arrayblockingqueuelinkedblockingqueuepriorityblockingqueue)
* [ConcurrentHashMap](#concurrenthashmap)
* [ConcurrentHashMap对比HashTable](#concurrenthashmap对比hashtable)
* [================](#-2)
* [线程组：Thread Group](#线程组thread-group)
* [线程池](#线程池)
* [================](#-3)
* [AQS](#aqs)
* [信号量：Semaphore](#信号量semaphore)
* [CyclicBarrier & CountDownLatch](#cyclicbarrier--countdownlatch)

# 线程vs进程

![](/static/2022-09-15-15-30-40.png)
![](/static/2022-09-15-15-32-04.png)

![](/static/2022-09-15-15-39-19.png)
![](/static/2022-09-15-16-01-52.png)
![](/static/2022-09-15-16-08-48.png)

* 注意线程共享，独占什么资源（JVM角度

# 线程优先级：thread priority

![](/static/2022-09-15-23-09-25.png)

# shutdown hook

![](/static/2022-09-15-22-31-09.png)

* JVM关闭前的钩子

# busy spinning、busy waiting: 忙等待机制

![](/static/2022-09-15-22-33-05.png)

* 忙等待，虽然也是在等待信号，但是一直占用cpu
* 阻塞等待，阻塞期间不占用cpu

# JVM线程调度器，时间片：Thread Scheduler, time slicing

![](/static/2022-09-15-20-38-18.png)

# 上下文切换：context switching

![](/static/2022-09-15-19-42-40.png)
![](/static/2022-09-15-19-44-59.png)

* 线程/进程让出CPU，其独占信息/状态被保存，下次占用CPU时恢复状态。并加载调度下一个线程/进程的上下文、
* 频繁切换效率会降低
* 线程上下文切换情况
  * 主动让CPU, sleep(), wait()
  * CPU时间片用完
  * **系统中断，IO**

# 类锁，对象锁：class lock/object lock

![](/static/2022-09-15-16-10-50.png)

* 保护静态or非静态资源

# 用户线程和守护线程有什么区别？difference between User thread and Daemon thread

![](/static/2022-09-15-16-13-07.png)

* JVM创建守护线程，，在所有线程完成任务前都不会销毁
  * 用于辅助任务
  * 低优先级线程
  * 后台任务，垃圾回收

# 创建守护线程

![](/static/2022-09-15-16-15-55.png)

* `setDaemon()`

# wait() vs sleep()

![](/static/2022-09-15-16-20-28.png)
![](/static/2022-09-15-16-21-25.png)

* 类级锁，对象锁

# notify(), notifyAll()

![](/static/2022-09-15-16-28-22.png)
![](/static/2022-09-15-16-33-39.png)
![](/static/2022-09-15-17-03-51.png)
![](/static/2022-09-15-17-04-17.png)

* 在这种情况下，notify是优于notifyAll 因为唤醒所有这些因为我们知道只**有一个线程会受益而所有其他线程将再次等待**，所以调用notifyAll方法只是浪费CPU。
* notify可能会死锁

# wait()为什么不定义在Thread中

![](/static/2022-09-15-16-35-57.png)

* 为什么是对象级锁

# Runnable vs Callable

![](/static/2022-09-15-16-39-47.png)
![](/static/2022-09-15-16-40-49.png)

# start(), run()

![](/static/2022-09-15-16-42-01.png)

# join()

![](/static/2022-09-15-16-53-06.png)
![](/static/2022-09-15-16-58-31.png)
![](/static/2022-09-15-16-59-13.png)
![](/static/2022-09-15-17-02-11.png)
![](/static/2022-09-15-17-01-05.png)

* main阻塞直到其他线程完成，，移到ready

# 死锁deadlock

![](/static/2022-09-15-17-07-20.png)
![](/static/2022-09-15-17-12-33.png)
![](/static/2022-09-15-17-14-22.png)

# 避免死锁

![](/static/2022-09-15-17-18-40.png)

# Volatile

![](/static/2022-09-15-17-20-12.png)
![](/static/2022-09-15-17-22-12.png)

## volatile禁用指令重排

![](/static/2022-09-15-18-07-44.png)

* 单例多线程情况下会出现问题，，加volatile

## volatile不保证原子性

![](/static/2022-09-15-18-20-16.png)
![](/static/2022-09-15-18-20-37.png)

# (*) Volatile vs transient关键字

![](/static/2022-09-15-15-04-40.png)

# 解释一下synchronization机制？

![](/static/2022-09-15-18-24-39.png)

![](/static/2022-09-15-18-26-20.png)

* 早期优化差，，属于重锁。底层依赖os mutext lock实现，线程间通信（挂起，唤醒）都需要os从用户态转至内核态，成本高

## 3种使用方式

![](/static/2022-09-15-18-28-19.png)
![](/static/2022-09-15-18-29-23.png)

## 底层原理

![](/static/2022-09-15-18-35-55.png)
![](/static/2022-09-15-18-39-37.png)
![](/static/2022-09-15-18-41-12.png)

* 进入同步块，monitoenter，尝试获取对象monitor的所有权（即，获取锁）
* 如果锁计数器=0, 成功获取对象锁，计数器设为1
  * 锁计数器=1时，无法获取，，继续进锁池竞争
* **持有锁的线程** 执行`monitorexit`释放锁，将锁计数器设为`0`

---

sync方法

![](/static/2022-09-15-18-43-26.png)

* ACC_SYNCHRONIZED 标识指明x方法是同步方法，然后进一步获取 实例/类的锁（**即monitor对象的所有权**，

## sync优化

![](/static/2022-09-15-18-45-41.png)

# 构造方法不能加synchronized

![](/static/2022-09-15-18-32-22.png)

# (*)sync对比volatile

JMM角度答

![](/static/2022-09-15-18-51-47.png)

* volatile更多的禁用JVM指令重排，，禁用CPU cache，轻量锁，保证线程从内存读取最新数据
  * 但是数据原子性不被保证的时候，，还是需要sync来保证
  * 禁用指令重排一个例子是写多线程情况下的单例类，，重排了就算用双重锁检测，，可能导致某一个thread获取null pointer （之前先进来实例化的thread还没到初始化那一步，，只是分配好了引用地址）

# (*)可重入锁：sync对比ReentrantLock

![](/static/2022-09-15-19-05-32.png)
![](/static/2022-09-16-00-08-40.png)

* 可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁
  * **再次获取锁的时候会判断当前线程是否是已经加锁的线程，如果是对锁的次数+1，释放锁的时候加了几次锁，就需要释放几次锁**
* `ReentrantLock`
  * 可以中断锁池中还在等锁的线程，，这些线程能做别的任务
  * <font color="deeppink">可以实现公平锁，先到先得。**默认非公平锁**
</font>  * **选择性通知**
    * 配合`Condition`接口，线程可以注册进指定Condition实例，有选择的进行通知
      * sync就相当于底层lock只配了一个大Condition，绑定所有线程

# ================

# ThreadLocal

![](/static/2022-09-15-23-13-21.png)

---

![](/static/2022-09-15-23-16-19.png)

* 线程独占的变量

## 用法

![](/static/2022-09-15-23-19-46.png)

## 原理

![](/static/2022-09-15-23-21-26.png)
![](/static/2022-09-15-23-25-08.png)

* 底层靠`Thread`类的HashMap，，专门存ThreadLocal k-v信息
  * k是ThreadLocal实例本身，v是赋的值
* `set, get`时，先获取当前thread的threadlocals(**`ThreadLocalMap`哈希表**)，再查信息

# ThreadLocal内存泄漏

![](/static/2022-09-15-23-50-53.png)

# ================

# 线程饥荒：thread starvation

![](/static/2022-09-15-19-08-58.png)

* 某线程永远分配不到资源，，不公平

# 活锁：Live Lock

![](/static/2022-09-15-19-09-47.png)

# 并发容器：BlockingQueue

![](/static/2022-09-15-14-02-48.png)
![](/static/2022-09-15-19-14-34.png)
![](/static/2022-09-15-19-16-14.png)

---

![](/static/2022-09-15-19-18-06.png)

* 阻塞并发队列容器，，**生产-消费模型**

## ArrayBlockingQueue，LinkedBlockingQueue，PriorityBlockingQueue

![](/static/2022-09-15-19-27-06.png)

![](/static/2022-09-15-19-30-18.png)

![](/static/2022-09-15-19-41-23.png)

* put方法不会block，因为是无界队列

# ConcurrentHashMap

![](/static/2022-09-15-22-47-46.png)
![](/static/2022-09-15-22-50-12.png)

---

底层

![](/static/2022-09-15-23-02-46.png)
![](/static/2022-09-15-23-04-22.png)
![](/static/2022-09-15-23-05-54.png)

* 1.7
  * 分段锁，`Segment`数组
  * `Segment`继承`ReentrantLock`，，就是一种锁
* 1.8
  * CAS + Node数组 +sync
  * 只锁Node首节点 & 其链表，，其他Node读写不会被阻塞
  * 链表超过阈值(8)会转为红黑树

# ConcurrentHashMap对比HashTable

![](/static/2022-09-15-22-41-13.png)
![](/static/2022-09-15-22-47-46.png)

# ================

# 线程组：Thread Group

![](/static/2022-09-16-00-04-24.png)

* 线程组和线程池的概念以及作用是完全不同的，线程组主要是用于线程的管理，而线程池则是为了管理线程的生命周期，复用线程，管理线程资源，减少反复创建销毁消除的开销

# 线程池

![](/static/2022-09-15-16-43-01.png)
![](/static/2022-09-15-16-43-48.png)

# ================

# AQS

# 信号量：Semaphore

![](/static/2022-09-15-23-55-24.png)

---

![](/static/2022-09-15-23-54-55.png)
![](/static/2022-09-16-00-00-14.png)

# CyclicBarrier & CountDownLatch

![](/static/2022-09-15-19-59-30.png)

CountDownLatch比join更灵活

* 调用thread.join() 方法必须等thread 执行完毕，当前线程才能继续往下执行，而CountDownLatch通过计数器提供了更灵活的控制，只要检测到计数器为0当前线程就可以往下执行而不用管相应的thread是否执行完毕