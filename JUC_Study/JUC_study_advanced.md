# Prologue

部分重复已去除 & 新知识引入

![](/static/2020-08-30-23-31-39.png)
![](/static/2020-08-30-23-31-53.png)

学习 `java.util.concurrent`下

* `java.util.concurrent.locks`
* `java.util.concurrent.atomic`

## CountDownLatch

[参考](https://www.jianshu.com/p/795151ac271b)

需求：控制顺序，其他线程结束了之后主线程才能结束

![](/static/2020-08-31-13-37-51.png)

* 构造函数`public CountDownLatch(int count)`
* `java.util.concurrent`包下，jdk1.5引入
* <font color="red">使一个线程等待其他线程各自执行完毕后再执行</font>
  * 内部通过计数器实现，初始值为**线程的数量**
  * 每一个线程执行完毕，计数器值`-1`
  * 计数器值`=0`时，所有线程执行完毕，<font color="red"></font>在等待队列中的线程可以恢复工作
    * 等待队列类似condition等待队列

🍊 实例方法

* `public void await() throws InterruptedException`
* `public boolean await(long timeout, TimeUnit unit) throws InterruptedException`
  * 一定时间内count值还未为0，则继续执行
* `public void countDown()`
  * 计数器`count-1`

### CountDownLatch vs thread.join()

`thread.join()`

* 当前线程阻塞，直到thread线程执行完毕，才能继续执行
  * 不断检查thread是否存活，如存活，令当前线程一直`wait`，直到thread终止，调用this.notifyAll

## CycliBarrier

🍊 cyclicBarrier与CountDownLatch区别

![](/static/2020-08-31-15-43-27.png)

* 区别在于，CycliBarrier是加计数方式
* 允许线程组全部等待，直达彼此到达共同栏杆点（屏障点），屏障点循环指，<font color="red">在等待的线程被释放后可重新使用（复用）</font>
  * 支持传入`Runnable`，每个屏障点到达，运行一次

🍊 原理

* 内部lock对象，每个线程调用`cyclicbarrier.await()`时，线程数+1，判断剩余数是否为parties。如果不是进入等待。
* 如果是，执行Runnable方法

---

![](/static/2020-08-31-15-33-54.png)

* 全部线程都到达栏杆`.await()`后才会执行任务

🍊 构造方法

```java
/*
 - parties: 参与的线程个数
 - 第二个Runnable可通过lambda传接口参数，指定最后完成的任务
*/
public CyclicBarrier(int parties)
public CyclicBarrier(int parties, Runnable barrierAction)
```

🍊 实例方法

![](/static/2020-08-31-15-41-06.png)

```java
public int await() throws InterruptedException, BrokenBarrierException
public int await(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException
```

## Semaphore

信号灯/信号量

![](/static/2020-08-31-16-24-25.png)

* 信号量模拟资源类，假设3个资源对象
* 每个线程获取，后进行操作
* 完成操作后，释放信号量资源

🍬 主要用于多线程并发控制&资源类互斥

* 如果将信号量改为1，则相当于一个锁`new Semaphore(1)`

## ReadWriteLock

🍊 需求

![](/static/2020-08-31-18-40-30.png)

* 读读，允许
* 读写，不允许
* 写读，仅允许获取了写锁的当前线程进行读

🍬 锁升级（读->写）

* 不支持，未正确释放读锁情况下，无法申请写锁

🍬 锁降级（写->读）

* 支持，为正确释放写锁，降级为读锁（当前线程）。后续仍需要释放写锁

🍬 支持中断锁，支持Condition

🍊 使用

![](/static/2020-08-31-18-42-52.png)

```java
ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

// 调出写锁
readWriteLock.writeLock().lock();
// 调出读锁
readWriteLock.readLock().lock();
```

* 使用场景
  * 操作同一个数据，大量读取，少量线程写

### 获取锁顺序

默认

* 非公平模式
  * 读锁&写锁获取顺序不确定
  * 非公平锁主张**竞争获取，可能延缓多个读写线程，但是吞吐量更高**
* 公平模式
  * 线程以队列顺序获取锁
  * 当前线程释放锁后，等待时间最长的写锁线程，分配写锁
  * 如读线程等待时间比写线程长，且写锁已经释放掉，才分配读锁

## BlockingQueue(interface)

阻塞队列（控制线程阻塞问题）

![](/static/2020-08-31-19-37-06.png)

* 先进先出
* 当队列为空，获取元素操作阻塞
* 当队列为满，添加元素操作阻塞

`BlockingQueue`实现

![](/static/2020-08-31-19-38-19.png)

* <font color="red">`ArrayBlockingQueue`</font>
  * 由数组构成的，有界阻塞队列
  * `capacity`参数指定界限
* <font color="red">`LinkedBlockingQueue`</font>
  * 链表构成的有界`integer.MAX_VALUE(default)`阻塞队列
* `LinkedTransferQueue`
  * 由链表组成的双向阻塞队列
* <font color="red">`SynchronousQueue`</font>
  * 不存储元素的阻塞队列，即，单个元素队列
* `PriorityBlockingQueue`
  * 支持优先级排序的无界阻塞队列
* `DelayQueue`
  * 使用优先级队列实现的延迟无界阻塞队列

`BlockingQueue`核心方法

![](/static/2020-08-31-19-46-30.png)
![](/static/2020-08-31-20-15-15.png)

## ThreadPool

为什么使用线程池?

* 控制运行的线程数量。将任务放入队列，然后线程创建后启动任务
* 如果线程数超过MAX，需要排队，等待其他线程执行完毕，再取出任务执行

特点

![](/static/2020-08-31-20-41-18.png)

* 控制最大并发数，管理线程

### Executor(interface)

线程池子接口`ExecutorService`

![](/static/2020-08-31-20-51-23.png)

* 获取`ThreadPoolExecutor`,可看为一个线程池
* 使用完毕需要关闭，`threadpoolexecutor.shutdown()`
* 执行任务`threadpoolexecutor.execute(Runnable)`

🍊 获取`Executors.newFixedThreadPool(int)`

```java
// obtain the ThreadPoolExecutor, 5 fixed threads
ExecutorService threadPool = Executors.newFixedThreadPool(5);
```

🍊 获取`Executors.newSingleThreadExecutor()`

* 一池一个线程

🍊 获取`Executors.newCachedThreadPool()`

* 一池n线程，慎用

### 底层原理

![](/static/2020-09-01-14-20-39.png)
![](/static/2020-09-01-14-22-03.png)

涉及阻塞队列

![](/static/2020-08-31-21-15-49.png)

🍊 线程池7大参数

![](/static/2020-08-31-21-17-50.png)

* `corePoolSize`
  * 线程池中常驻核心线程数
* `maximumPoolSize`
  * 线程池中能够容纳同时执行的最大线程数，`>=1`
* `keepAliveTime`
  * 多余空闲线程的存活时间
  * 当前池中**线程数量超过corePoolSize时**，<font color="red">当空闲时间达到keepAliveTime时，多于线程会被销毁直到剩下`corePoolSize`个线程为止</font>
* `unit`
  * `TimeUnit`，上面`keepAliveTime`的单位
* `WorkQueue`
  * `BlockingQueue`任务队列，被提交但尚未执行的任务
  * **如核心处理任务数满了，任务在该队列中阻塞等待**
* `threadFactory`
  * 生成线程池中工作线程的线程工厂，用于创建线程，
  * 一般用使用默认即可
* `handler`
  * `RejectedExecutionHandler`拒绝策略。队列满了，并且工作线程>=线程池中最大线程数时，如何拒绝请求执行的Runnable

### 开发使用

开发中使用哪个线程池？

* 不使用Executors提供的，使用自定义的
  * 避免资源耗尽的风险
  * ![](/static/2020-09-01-17-56-22.png)
* 直接自定义创建`ThreadPoolExecutor`对象，需要指定`7`大参数

```java
/* 自定义例子 */
ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 2L,TimeUnit.SECONDS,new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
```

#### 拒绝策略

`ThreadPoolExecutor`类，以下为静态内部类

* `AbortPolicy`默认
  * 直接抛出`RejectedExecutionException`异常阻止系统正常运行
* `CallerRunsPolicy`
  * 调用者运行，调节机制。
  * 该策略不会抛弃任务，也不会抛出异常
  * 将某些任务回退到调用者，从而降低新任务流量
* `DiscardOldestPolicy`
  * 抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务
* `DiscardPolicy`
  * 丢弃无法处理的任务，不处理也不抛出异常
  * 如果允许任务丢失，该策略为最优

## ForkJoinPool

![](/static/2020-09-01-18-53-46.png)

* `ForkJoinPool`进一步封装`ExecutorService`
  * 多线程分治法，分支合并
* `ForkJoinTask`接口
  * `RecursiveTask<T >`抽象类
    * 覆写`compute()`方法

![](/static/2020-09-01-20-02-09.png)
![](/static/2020-09-01-20-05-45.png)

## CompletableFuture

异步回调

![](/static/2020-09-01-20-17-26.png)
![](/static/2020-09-01-20-25-40.png)

* `u`异常