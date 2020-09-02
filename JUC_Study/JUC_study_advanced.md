# Prologue

部分重复已去除 & 新知识引入

![](/static/2020-08-30-23-31-39.png)
![](/static/2020-08-30-23-31-53.png)

学习 `java.util.concurrent`下

* `java.util.concurrent.locks`
* `java.util.concurrent.atomic`

- [Prologue](#prologue)
  - [CountDownLatch](#countdownlatch)
    - [CountDownLatch vs thread.join()](#countdownlatch-vs-threadjoin)
  - [CycliBarrier](#cyclibarrier)
  - [Semaphore](#semaphore)
  - [ReadWriteLock](#readwritelock)
    - [获取锁顺序](#获取锁顺序)
  - [BlockingQueue(interface)](#blockingqueueinterface)
  - [ThreadPool](#threadpool)
    - [Executor(interface)](#executorinterface)
      - [基本使用](#基本使用)
    - [ScheduledExecutorService: 线程池计划任务](#scheduledexecutorservice-线程池计划任务)
    - [底层原理: 7大参数](#底层原理-7大参数)
      - [可使用的阻塞队列](#可使用的阻塞队列)
      - [拒绝策略](#拒绝策略)
    - [ThreadFactory](#threadfactory)
    - [ThreadPoolExecutor：监控线程池的方法](#threadpoolexecutor监控线程池的方法)
    - [Worker: 扩展线程池 - before/afterExecute](#worker-扩展线程池---beforeafterexecute)
      - [Example](#example)
    - [优化线程池数量(大小)](#优化线程池数量大小)
    - [开发使用问题](#开发使用问题)
      - [开发中使用哪个池](#开发中使用哪个池)
      - [线程池死锁](#线程池死锁)
    - [线程池异常处理](#线程池异常处理)
  - [ForkJoinPool](#forkjoinpool)
    - [Example](#example-1)
  - [Future](#future)
    - [ListenableFuture](#listenablefuture)
    - [CompletableFuture](#completablefuture)
      - [Example](#example-2)
  - [PipeStream](#pipestream)
  - [线程管理](#线程管理)
    - [线程组(不常用)](#线程组不常用)
      - [创建线程组](#创建线程组)
      - [线程组基本操作（方法）](#线程组基本操作方法)
    - [线程组 vs 线程池](#线程组-vs-线程池)
    - [UncaughtExceptionHandler: 捕获线程运行(执行)时异常](#uncaughtexceptionhandler-捕获线程运行执行时异常)
      - [设置 & 获取线程异常捕获器](#设置--获取线程异常捕获器)
    - [Hook 钩子线程](#hook-钩子线程)
  - [JMM & 线程安全形式](#jmm--线程安全形式)
    - [原子性: 造成安全问题1](#原子性-造成安全问题1)
      - [原子性例子](#原子性例子)
        - [AtomicInteger: 解决方法](#atomicinteger-解决方法)
    - [可见性: 造成安全问题2](#可见性-造成安全问题2)
      - [脏读](#脏读)
      - [可见性例子 - volatile](#可见性例子---volatile)
    - [有序性: 造成安全问题3](#有序性-造成安全问题3)
      - [指令重排序](#指令重排序)
      - [存储子系统重排序](#存储子系统重排序)
    - [貌似串行语义](#貌似串行语义)
    - [保证内存访问的顺序性](#保证内存访问的顺序性)
    - [JAVA内存模型](#java内存模型)
      - [缓存同步](#缓存同步)
    - [volatile: 轻量级同步机制](#volatile-轻量级同步机制)
      - [volatile vs synchronized](#volatile-vs-synchronized)
      - [volatile非原子性](#volatile非原子性)
      - [12个原子变量类](#12个原子变量类)
        - [AtomicInteger：自增自减](#atomicinteger自增自减)
        - [AtomicLong: 模拟(服务器)计数器](#atomiclong-模拟服务器计数器)
        - [AtomicIntegerArray: 原子数组](#atomicintegerarray-原子数组)
        - [AtomicIntegerFieldUpdater: 原子整型字段更新器](#atomicintegerfieldupdater-原子整型字段更新器)
        - [AtomicReference: 原子操作对象](#atomicreference-原子操作对象)
        - [AtomicReference: ABA问题](#atomicreference-aba问题)
  - [CAS(Compare and Swap)](#cascompare-and-swap)
    - [使用CAS实现线程安全计数器](#使用cas实现线程安全计数器)
    - [CAS中的ABA问题](#cas中的aba问题)

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
  * 由数组构成的，**有界阻塞队列**
  * `capacity`参数指定界限
* <font color="red">`LinkedBlockingQueue`</font>
  * 链表构成的**有界**`integer.MAX_VALUE(default)`**阻塞队列**
* `LinkedTransferQueue`
  * 由链表组成的双向阻塞队列
* <font color="red">`SynchronousQueue`</font>
  * **不存储元素的阻塞队列【直接提交】**，即，单个元素队列
* `PriorityBlockingQueue`
  * 支持优先级排序的无界阻塞队列
* `DelayQueue`
  * 使用优先级队列实现的延迟无界阻塞队列

`BlockingQueue`核心方法

![](/static/2020-08-31-19-46-30.png)
![](/static/2020-08-31-20-15-15.png)

## ThreadPool

普通Thread出来的**线程对象**，一般run()结束，**会被gc释放**

* 四大gc算法：复制(MGC)，计数，标清(FGC)，标清-压缩（混合使用）
* 一般在新生代Eden&From区通过MGC清完了，很少有对象达年龄进入老年代

---

为什么使用线程池?

![](/static/2020-09-03-21-30-16.png)

* 真实开发中，可能需要很多个线程对象，<font color="red">如果不加以控制，容易耗尽CPU资源</font>，**线程主要开销包括**
  * 创建&启动开销
  * 销毁开销
  * 调度开销
  * 数量受限CPU数量
* <font color="green">线程池是有效使用线程的一种常用方法</font>
  * 内部可以**预先创建一定数量工作线程**
  * 客户端将任务作为对象，提交给线程池
  * <font color="green">线程池将任务缓存在【工作（阻塞）队列】中，线程池中工作线程不断从队列中取出任务并执行</font>
* **控制运行的线程数量**。将**任务放入队列，然后线程创建后启动任务**
* 如果线程数超过MAX，需要排队(队列满，且执行线程达上限，可能需要调用拒绝策略)，等待其他线程执行完毕，再取出任务执行

🍊 特点

![](/static/2020-08-31-20-41-18.png)

* 控制最大并发数，**有效管理线程【可管理性】**
* **降低CPU资源开销**，【过多线程对象资源相关开销】
* **提高任务响应速度**，**不需要等待线程创建**，能够立即执行

### Executor(interface)

JDK提供Executor框架，有效使用线程池

![](/static/2020-09-03-21-56-18.png)

* <font color="green">实际开发中，很少直接使用实现类，一般使用`Executors`工具类的工厂方法，获取对应线程池</font>
  * `ExecutorService Executors.newFixedThreadPool(int Threads)`
  * `ExecutorService Executors.newSingleThreadPool()`
  * `ExecutorService Executors.newCachedThreadPool()`

---

#### 基本使用

```java
public static void main(String[] args) throws IOException {
  //创建有5个线程大小的线程池（常驻核心线程数=最大容纳线程数=5）
  ExecutorService executorService = Executors.newFixedThreadPool(5);

  //向线程池提交18个任务
  for (int i = 0; i < 18; i++) {
      executorService.execute(()->{
          System.out.println(Thread.currentThread().getId() + " 该任务正在执行，开始时间: " + System.currentTimeMillis());
          try {
              //模拟任务执行时长
              TimeUnit.SECONDS.sleep(3);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      });
  }
}
```

---

线程池子接口`ExecutorService`

![](/static/2020-08-31-20-51-23.png)

* 获取`ThreadPoolExecutor`,可看为一个线程池
* 使用完毕需要关闭，`threadpoolexecutor.shutdown()`
* 执行任务`threadpoolexecutor.execute(Runnable)`
  * `Exectuor`原接口提供的方法
* 同样支持`threadpoolexecutor.sumbit(Callable)`

🍊 获取`Executors.newFixedThreadPool(int)`

```java
// obtain the ThreadPoolExecutor, 5 fixed threads
ExecutorService threadPool = Executors.newFixedThreadPool(5);
```

🍊 获取`Executors.newSingleThreadExecutor()`

* 一池一个线程

🍊 获取`Executors.newCachedThreadPool()`

* 一池n线程，慎用

### ScheduledExecutorService: 线程池计划任务

![](/static/2020-09-03-21-56-18.png)

* 通过Executors工厂方法获得`ScheduledExecutorService`线程池

🍊 实例方法

* `public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);`
  * 延迟n单位时间后执行任务，定时执行
* `public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);`
* ` public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit);`
  * 以固定频率执行任务，开启任务的时间固定
  * **如果任务用时超过频率延迟，则任务一结束直接重新执行任务**
* `public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
long initialDelay,long delay,TimeUnit unit);`
  * 在上次任务结束后 + 在固定延迟后，再次执行该任务
  * <font color="red">不管任务所需执行耗时多长，总是在任务【结束后】+等待时间单位延迟后，再次执行新的任务</font>

```java
// 创建带调度功能的线程池
ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

// 延迟2秒后执行任务
scheduledExecutorService.schedule(new Runnable() {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() +"--" + System.currentTimeMillis());
    }
}, 2, TimeUnit.SECONDS);

// 固定频率执行任务，起始3秒后执行，然后每隔2秒执行一次
scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() + "--固定频率开启任务" + System.currentTimeMillis());
        // 模拟任务执行所需时间
        try {
            TimeUnit.SECONDS.sleep(3);// 假设任务执行时长超过了任务执行频率，则任务完成后立即开启下次执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}, 3, 2, TimeUnit.SECONDS);
```

### 底层原理: 7大参数

查看Executors工具类中`newCachedThreadPool()` & `newSingleThreadExecutor()` & `newFixedThreadPool(int)`源码

![](/static/2020-08-31-21-15-49.png)

* 底层都使用了`ThreadPoolExecutor`线程池实现类，这些工厂方法都封装了该实现类
* 涉及阻塞队列

![](/static/2020-09-01-14-20-39.png)
![](/static/2020-09-01-14-22-03.png)

---

🍊 线程池`ThreadPoolExecutor`实现类7大参数

![](/static/2020-08-31-21-17-50.png)

* `corePoolSize`
  * 线程池中**常驻核心线程数**
* `maximumPoolSize`
  * 线程池中**能够容纳同时执行的最大线程数**，`>=1`
* `keepAliveTime`
  * 多余空闲线程的存活时间
  * 当前池中**线程数量超过corePoolSize时**，<font color="red">当空闲时间达到keepAliveTime个时间单位时，多余线程会被销毁，直到剩下`corePoolSize`个线程为止</font>
* `unit`
  * `TimeUnit`枚举，上面`keepAliveTime`的单位
* `WorkQueue`
  * `BlockingQueue`任务队列，被**提交但尚未执行的任务**，<font color="red">仅用于存Runnable任务</font>
  * **如核心处理任务数满了，任务在该队列中阻塞等待**
* `threadFactory`
  * 生成线程池中工作线程的**线程工厂，用于创建线程**
  * 一般用使用默认即可
* `handler`
  * `RejectedExecutionHandler`**拒绝策略**。队列满了，并且工作线程>=线程池中最大线程数时，**如何拒绝**请求执行的Runnable

#### 可使用的阻塞队列

根据队列功能分类

![](/static/2020-09-04-21-11-52.png)

* 直接提交队列
  * `SynchronousQueue`，无容量
  * 提交给线程池的**任务不会真实保存在队列中**，总是将新任务提交给线程，如果没有空闲线程，则尝试创建，如果超出maximumPoolSize，执行拒绝策略
* 有界任务队列（FCFS算法）
  * `ArrayBlockingQueue`实现，可指定容量
  * 当有任务需要执行时，如果线程池中线程数小于corePoolSize尝试创建，**如大于，则加入等待队列**
  * **如队列满，无法加入，如此时线程数小于maximumPoolSize时，创建新线程。否则，执行拒绝策略**
* 无(有)界任务队列（FCFS算法）
  * `LinkedBlockingQueue`链表构成的有界阻塞队列(默认大小integer.MAX_VALUE)
  * <font color="red">与数组实现的有界阻塞队列相比，除非系统资源耗尽，否则无界队列不存在任务入队失败的情况（默认界限）</font>
  * 当有新任务，在系统线程数小于corePoolSize核心线程数时创建新线程. 若大于，则任务进入队列
* 优先任务队列（特殊无界队列，支持优先级算法）
  * `PriorityBlockingQueue`，带有任务优先级的队列。特殊无界队列

#### 拒绝策略

🍊 ThreadPoolExecutor构造方法最后一参数为拒绝策略。当线程池线程用尽，等待队列满，如何解决?

`ThreadPoolExecutor`类，以下为**静态内部类**

* `AbortPolicy`默认
  * 直接**抛出**`RejectedExecutionException`**异常**阻止系统正常运行
* `CallerRunsPolicy`
  * 调用者运行，调节机制。
  * **该策略不会抛弃任务，也不会抛出异常**
  * 将某些任务回退到调用者，从而降低新任务流量
* `DiscardOldestPolicy`
  * **抛弃队列中等待最久的任务**，然后把当前任务加入队列中尝试再次提交当前任务
* `DiscardPolicy`
  * **丢弃无法处理的任务**，不处理也不抛出异常
  * 如果允许任务丢失，该策略为最优

🍬 如果自带的四大策略无法满足需求，

* 可以扩展`RejectedExectuionHandler`接口
  * 覆写其处理任务的方法

```java
public static void main(String[] args) throws IOException {
    /*
    * 模拟自定义拒绝策略（扩展）
    * */
    Runnable runnable = () -> {
        int num = new Random().nextInt(10);
        System.out.println(Thread.currentThread().getId() + "--" + System.currentTimeMillis() + "开始睡眠" + num + "s");
        try {
            TimeUnit.SECONDS.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    // 创建线程池 & 自定义拒绝策略
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), Executors.defaultThreadFactory(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    // r请求执行的任务， executor当前线程池
                    System.out.println("Task: " + r + " is discarded");
                }
    });

    // 提交Runnable任务给线程池
    for (int i = 0; i < 1000; i++) {
        threadPoolExecutor.execute(runnable);
    }
}
```

### ThreadFactory

线程池的线程哪来？

* `ThreadFactory`线程工厂**接口**
  * 只有一个用于创建线程的抽象方法`Thread newThread(Runnable r);`
* 当线程池中需要创建线程时，会调用该方法

🍬 支持自定义线程工厂

```java
public static void main(String[] args) throws IOException, InterruptedException {
    /*
    * 模拟自定义线程工厂
    * */

    Runnable runnable = () -> {
        int num = new Random().nextInt(10);
        System.out.println(Thread.currentThread().getId() + "--" + System.currentTimeMillis() + "开始睡眠" + num + "s");
        try {
            TimeUnit.SECONDS.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    ExecutorService executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    //r 为线程池接收的任务，根据Runnable任务r，创建线程,
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);//设置为守护线程，主线程结束时，会自动释放
                    System.out.println("创建了线程: " +thread);
                    return thread;
                }
    }, new ThreadPoolExecutor.AbortPolicy());

    // 执行5个任务
    for (int i = 0; i < 5; i++) {
        executor.execute(runnable);
    }

    Thread.sleep(10000);
    //主线程睡眠10s后，结束，线程池中守护线程会自动退出
}
```

### ThreadPoolExecutor：监控线程池的方法

`ThreadPoolExecutor`提供了**一组方法用于监控线程池**，追踪动态

* `int getPoolSize()`
  * 返回当前线程池中**线程数量，线程池大小**
* `int getActiveCount()`
  * 获取线程池中**当前活动线程数量**
* `long getCompletedTaskCount()`
  * 返回线程池**完成任务的数量**
* `long getTaskCount()`
  * 返回线程池**收到的任务总数**
* `int getLargestPoolSize()`
  * 返回线程池**曾经达到的线程最大数**
* `BlockingQueue<Runnable>getQueue()`
  * 返回**阻塞队列**

---

* `int getCorePoolSize()`
  * 线程池中核心线程的数量 - 参数
* `int getMaximumPoolSize()`
  * 返回线程池最大容量 - 参数

### Worker: 扩展线程池 - before/afterExecute

🍊 ThreadPoolExecutor内部定义一个内部类`Worker`，该内部类实现了`Runnable`

![](/static/2020-09-05-00-22-43.png)

* 线程池中**工作线程就是Worker实例**
  * 执行时会调用afterExecute&beforeExecute

有时候需要对已有提供的线程池进行扩展

* 如，监控每个任务开始&结束时间
* 或其他增强功能

🍬 `ThreadPoolExecutor`实现类**提供两个方法，用于扩展**

* `protected void beforeExecute(Thread t, Runnable r)`
  * 线程池执行某个任务前，会调用beforeExecute
* `protected void afterExecute(Runnable r, Throwable t)`
  * 线程池执行完某个任务后，或异常，**都会调用afterExecute**

🍬 也可以覆写`terminated`方法

* 线程池退出时，执行的操作

---

#### Example

```java
public class TestConcurrent {

  private static class MyTask implements Runnable{
      String name;

      public MyTask(String name) {
          this.name = name;
      }

      @Override
      public void run() {
          System.out.println(name + "正在被" + Thread.currentThread().getId() + "执行");
          try {
              Thread.sleep(1000);//模拟任务执行时长
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
      /*
      * 模拟扩展线程池，*/
      // 1.可以定义一个线程池类，继承ThreadPoolExecutor, 覆写before/afterExecute
      // 2. 直接重写ThreadPoolExecutor的内部类
      ExecutorService executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>()) {
          // 重写ThreadPoolExecutor内部类
          @Override
          protected void beforeExecute(Thread t, Runnable r) {
              System.out.println(t.getId() + "线程准备执行任务" + ((MyTask) r).name);
          }

          @Override
          protected void afterExecute(Runnable r, Throwable t) {
              System.out.println(((MyTask)r).name + "任务执行完毕");
          }

          @Override
          protected void terminated() {
              System.out.println("线程池退出");
          }
      };

      // 向线程池中添加任务
      for (int i = 0; i < 5; i++) {
          MyTask myTask = new MyTask("task"+i);
          executor.execute(myTask);
      }

      executor.shutdown();//仅仅表示线程池不再接受新任务，已接受的任务正常执行完毕
  }
```

### 优化线程池数量(大小)

线程池大小对系统性能有一定影响，过大过小都无法发挥最优性能

* 大小不需要非常精确，只需要**避免极大极小情况**
* 需要**考虑CPU数量，内存大小**等因素
* 在Java Concurrency in Practice书中给出了一个估算线程池大小的公式
  * `线程池大小 =CPU数量（核）* 目标CPU使用率*（1+等待时间/计算时间）`

### 开发使用问题

#### 开发中使用哪个池

* 不使用Executors提供的，使用自定义的
  * 避免资源耗尽的风险
  * ![](/static/2020-09-01-17-56-22.png)
  * `newCachedThreadPool`极端情况下，每次提交新任务都换创建新的线程执行
    * 适合用来执行大量耗时短，并且提交频繁的任务
* 直接自定义创建`ThreadPoolExecutor`对象，需要指定`7`大参数

```java
/* 自定义例子 */
ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 2L,TimeUnit.SECONDS,new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
```

#### 线程池死锁

如果线程池中任务A执行过程中，又向线程池提交了B

* 如果B添加至等待队列中，**并且A的结束需要等待B的执行结果(依赖)**，有可能造成
  * <font color="red">线程池中所有工作线程正等待任务的执行结果，而这些任务在阻塞队列中等待被执行</font>
  * 互相等待，从而造成死锁

🍊 尽量提交相互独立的任务，

* <font color="green">对于彼此依赖的任务，可以考虑提交给不同的线程池处理，避免死锁</font>

### 线程池异常处理

之前单个线程的异常处理可以通过设置异常处理器`UncaughtExceptionHandler`获取

🍊 演示线程池可能会吃掉程序中的异常

![](/static/2020-09-05-21-22-34.png)
![](/static/2020-09-05-21-23-42.png)

* 实际提交5个任务，只显示4个结果
  * 线程池吃掉了该0除数异常

🍬 解决方法

* `submit`改为`execute`方法
* 对线程池进行扩展，包装`submit()`

---

演示**自定义线程池类继承ThreadPoolExecutor**，对ThreadPoolExecutor进行扩展（处理线程池异常）

![](/static/2020-09-05-22-03-18.png)
![](/static/2020-09-05-22-05-45.png)

* 先包装Runnable任务，再重写submit & execute方法
  * 传入定义好的包装方法

## ForkJoinPool

![](/static/2020-09-05-22-16-52.png)
![](/static/2020-09-05-22-34-42.png)

* 系统对**ForkJoinPool线程池**进行了优化（窃取算法），提交的任务数量和线程数不一定是1：1关系
  * 多数情况下，一个物理线程实际上需要处理多个逻辑人物
* <font color="red">【实现了工作窃取算法】</font>线程A执行完自己的任务，会从线程B的**队列底部**开始窃取任务，协助执行
* <font color="red">注意：如果任务THRESHOLD阈值太小，每个任务包含的计算量小，层次划分可能很深(分叉很多)</font>，可能出现2种情况
  * 系统内线程数量越积越多，导致性能严重下降
  * 分解次数过多，方法调用过多，可能导致栈溢出

🍊 常用方法

* `ForkJoinTask<T> submit(ForkJoinTask<T>task)`向线程池提交`ForkJoinTask`任务
  * 该**任务对象支持，fork分解&join等待**
  * <font color="red">ForkJoinTask有两个重要子类：RecursiveAction，RecursiveTask，</font>区别在于<font color="blue">RecursiveAction任务没有返回值, RecursiveTask可有返回值</font>
* `fork()`将任务分为子任务&提交
* `join()`合并结果，如任务未执行完毕会阻塞

![](/static/2020-09-01-18-53-46.png)

* `ForkJoinPool`进一步封装`ExecutorService`
  * 多线程分治法，分支合并
* `ForkJoinTask`接口
  * `RecursiveTask<T >`抽象类
    * 覆写`compute()`方法

![](/static/2020-09-01-20-02-09.png)
![](/static/2020-09-01-20-05-45.png)

### Example

ForkJoinPool模拟数列求和

* <font color="red">注意：如果任务THRESHOLD阈值太小，每个任务包含的计算量小，层次划分可能很深(分叉很多)</font>，可能出现2种情况
  * 系统内线程数量越积越多，导致性能严重下降
  * 分解次数过多，方法调用过多，可能导致栈溢出

```java
public class TestConcurrent {
  // 计算数列的和，需要返回结果，可以定义任务类继承RecursiveTask
  private static class CountTask extends RecursiveTask<Long>{
      //定义一个数据规模的阈值，允许计算10000个数内的和
      //超过该【阈值】的数列需要【分解】
      private static final int THRESHOLD = 10000;
      private static final int TASKNUM = 100; //定义，每次大任务分解的任务数量
      private long start; //计算数列的【起始值】
      private long end; //计算数列的【结束值】

      public CountTask(long start, long end){
          this.start = start;
          this.end = end;
      }

      //重写RecursiveTask类的compute()方法，计算数列的结果
      @Override
      protected Long compute() {
          long sum = 0; //保存计算结果
          //判断任务是否需要继续分解
          //如计算范围(start~end)>threshold,需要分解
          if (end-start< THRESHOLD){
              //<threshold，可以直接执行计算
              for (long i=start; i<=end;i++){
                  sum += i;
              }
          }else{
              //需要分解
              //此处，规定每次分解为100个小任务，计算每个任务的计算量
              //假设start=0，end=20000，则每个step=200个任务量（可直接计算）
              long step = (start + end) / TASKNUM; //代表每个任务包含的计算量

              //创建一个存储任务的集合
              ArrayList<CountTask> subTask = new ArrayList<>();
              long pos = start;//每个任务起始位置
              long lastPos;
              //共创建TASKNUM个子任务
              for (int i = 0; i < TASKNUM; i++) {
                  lastPos = pos + step; //计算每个子任务结束位置
                  //调整最后一个任务的结束位置（因为父任务数量不均匀时，可能最后一个任务数量会超过原任务量）
                  if (lastPos > end) {
                      lastPos = end;
                  }
                  CountTask task = new CountTask(pos, lastPos);
                  //把任务添加到集合中
                  subTask.add(task);
                  task.fork();//分解&向ForkJoinPool提交任务

                  //调整下个任务的起始位置
                  pos += step + 1;
              }
              //等待所有子任务的计算结果
              for (CountTask task: subTask) {
                  sum += task.join();//一直等待子任务执行完毕&返回结果
              }
          }
          return null;
      }
  }
```

```java
public static void main(String[] args) throws IOException, InterruptedException {
    ForkJoinPool pool = new ForkJoinPool();
    CountTask task = new CountTask(0L, 200000L);
    ForkJoinTask<Long> result = pool.submit(task);
    try {
        System.out.println(result.get());
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
}
```

## Future

🍬 开发中，需要同步获取线程执行结果，最普通可以使用Callable接口。**但当任务需要【异步】处理，同时关注任务的处理结果**，需要使用Future及其子实现

![](/static/2020-09-01-23-22-16.png)

* 任务交给线程池处理，Future采用异步方式获取最后值
  * <font color="red">线程池会构造一个实现子类`FutureTask`，消费者将处理结果存储进`FutureTask`</font>
  * <font color="red">更新任务状态：未开始，处理中，完成</font>
  * <font color="red">最后`FutureTask`转型为`Future`接口</font>
  * `future.get()`阻塞获取结果
* <font color="blue">问题：如果需要当一个任务失败时，其他任务都终止，不应该在某行阻塞等待异常</font>
  * 可以`Guava`提供的使用带监听的`ListenableFuture`&带监听的线程池`ListeningExecutorService`，<font color="blue">用的少</font>
* <font color="blue">问题：不希望阻塞获取结果，但又希望通过Future处理结果？</font>
  * `CompletableFuture`提供后置处理方式，整合多个`Future`方法

### ListenableFuture

目前`ListenableFuture`用的少，JDK1.5的Future不好用。都使用JDK1.8的`Guava`提供的`ListenableFuture`，因此抄了一份`CompletableFuture`

![](/static/2020-09-01-23-35-08.png)

* 如果任务成功，则打印
* 任务失败，则抛异常

### CompletableFuture

目前`ListenableFuture`用的少，JDK1.5的Future不好用。都使用JDK1.8的`Guava`提供的`ListenableFuture`，因此抄了一份`CompletableFuture`

* <font color="red">一般使用该类`CompletableFuture`即可</font>
* 异步，流/函数式编程

🍊 方法

![](/static/2020-09-02-15-42-08.png)

* 异步执行`public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)`
  * supply函数式接口，无参带返回值
  * 返回`CompletableFuture`
* 对`CompletableFuture`返回值进行处理`public CompletableFuture<Void> thenAccept(Consumer<? super T> action)`
  * <font color="red">覆写Consumer接口方法，作为对Consumer传参返回值的处理</font>

🍊 常见使用方式

* 创建`CompletableFuture`
  * 重载，`Executor`默认传入`ForkJoinPool`
  * 比直接用构造方法创建得到简洁
  * `supplyXXX`
  * `runXXX`

```java
// 静态方法supplyAsync，有返回值
public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
  return asyncSupplyStage(asyncPool, supplier);
}

public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
{
  return asyncSupplyStage(screenExecutor(executor), supplier);
}

// 静态方法runAsync，无返回值
public static CompletableFuture<Void> runAsync(Runnable runnable) { 

return asyncRunStage(asyncPool, runnable); }

public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) { 

return asyncRunStage(screenExecutor(executor), runnable);

```

* 结果获取

```java
public T get()
// 超时将抛出异常
public T get(long timeout, TimeUnit unit)

// 立即获取结果，不阻塞
public T getNow(T valueIfAbsent)

// 不会抛出异常
public T join()
```

---

异步回调

![](/static/2020-09-01-20-17-26.png)
![](/static/2020-09-01-20-25-40.png)

* `u`异常

#### Example

![](/static/2020-09-01-23-42-09.png)
![](/static/2020-09-01-23-52-58.png)

---

如何优雅停止一个线程？

* 自定义一个内部静态枚举类：任务执行结束时候的状态

![](/static/2020-09-02-14-59-27.png)
![](/static/2020-09-02-15-01-43.png)
![](/static/2020-09-02-15-02-20.png)

## PipeStream

通过管道实现**线程间传输数据**

* `java.io`包
* 一个线程发送数据至输出管道
* 一个线程从输入管道接收数据

🍊 相关类

* `PipedInputStream`
* `PipedOutputStream`
* `PipedReader`
* `PipedWriter`

🍊 方法

* `connect()`连接两个管道流

```java
public static void writeData(PipedOutputStream out){
    // 线程向输出管道流中写入数据
    try {
        for (int i = 0; i < 100; i++) {
            String data = ""+i;
            out.write(data.getBytes());
        }
        out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void readData(PipedInputStream in){
    // 线程向输入管道流中读取数据
    byte[] buffer = new byte[1024];
    int readCounts = 0;
    while (true){
        try {
            if (!((readCounts=in.read(buffer))!=-1)) break;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(buffer, 0, readCounts));
    }
}
}
```

```java
public static void main(String[] args) throws IOException {
  PipedOutputStream out = new PipedOutputStream();
  PipedInputStream in = new PipedInputStream();

  // 建立连接
  in.connect(out);

  // 线程向管道out写入数据
  new Thread(()->{
      writeData(out);
  }).start();

  // 线程从管道in读取数据
  new Thread(()->{
      readData(in);
  }).start();
}
```

## 线程管理

### 线程组(不常用)

> 早期出于安全考虑，设计用来区分不同Applet。**ThreadGroup并未实现该目标，开发中目前不常用**。
>
> **一般将一组相关线程存入一个数组或集合中，如果仅用来区分线程，可以用线程名称区分**

类似使用文件夹管理文件，也可以使用线程组来管理线程

* 在线程组中定义一组相似的线程
* 也可以定义子线程组

🍬 main线程组的父线程组是`System`

#### 创建线程组

🍊 创建线程组方法

* Thread类提供构造方法，**允许在创建线程时指定线程组**。<font color="red">如果创建时没有指定线程组，则该线程属于父线程所在的线程组</font>
* VM创建main线程时，会默认指定一个线程组，因此每个线程都有一个线程组关联
  * `getThreadGroup()`返回当前线程所在线程组

```java
public static void main(String[] args) throws IOException {
    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    System.out.println(threadGroup);

    // 不指定线程组，默认线程归属到父线程线程组中
    ThreadGroup group1 = new ThreadGroup("group1");
    System.out.println(group1);

    // 指定线程组,main所在线程组
    ThreadGroup group2 = new ThreadGroup(threadGroup, "group2");
    System.out.println(group2);

    System.out.println(group1.getParent() == group2.getParent());

    // 指定线程的线程组
    new Thread(group1, ()->{System.out.println(Thread.currentThread())},"t1").start();
}
```

#### 线程组基本操作（方法）

`int activeCount()`

* 返回当前线程组&子线程组中**活动线程数量（预估）**

`int activeＧroupCount()`

* 返回当前线程组&子线程组中**活动线程组数量（预估）**

---

`int enumerate(Thread[] list)`

* 将**当前线程组中活动线程，复制到**`Thread[]`中

`int enumerate(ThreadGroup[] list)`

* 将**当前线程组中活动线程组，复制到**`Thread[]`中

---

`int getMaxPriority()`

* 返回线程组中最大优先级，默认`10`

`String getName()`

* 返回线程组名字

`ThreadGroup getParent()`

* 返回父线程组

`void interrupt()`

* 中断**线程组中所有线程**，给所有线程加上中断标志
* <font color="red">如果中断睡眠中的线程，会产生中断异常，同时清除中断标志</font>

`boolean isDaemon()`

* 判断是否为守护线程组

`void list()`

* 将当前线程组中活跃线程打印出来

`boolean parentOf(ThreadGroup g)`

* 判断当前线程组是否为参数线程组的父线程组

---

`setDaemon(boolean daemon)`

* 设置**当前线程组为守护线程组**
* <font color="blue">守护线程组中没有任何活动线程，守护线程组也会自动销毁</font>
* <font color="blue">守护线程组中线程可以是非守护线程</font>
* 守护线程为其他线程提供服务，当VM中只有守护线程时，守护线程会自动销毁，VM退出

### 线程组 vs 线程池

![](/static/2020-09-03-15-36-45.png)

### UncaughtExceptionHandler: 捕获线程运行(执行)时异常

线程run方法中，如果有受检异常，必须显式处理

* 如果需要获得运行时的异常信息，通过`UncaughtExceptionHandler`接口处理

🍬 线程出现异常时，VM会自动调用`thread.dispatchUncaughtException(Throwable e)`方法

* 底层，获取线程异常捕获器，并调用`unException(Thread t, Throwable e)`(覆写的)方法处理异常
* <font color="red">如，当前线程并未设置遗产处理器，默认使用所在线程组的回调接口UncaughtExceptionHandler。如果线程组也未设置，则直接把异常栈信息定向到System.err中，打印到控制台</font>

![](/static/2020-09-03-19-43-23.png)

---

#### 设置 & 获取线程异常捕获器

![](https://img2018.cnblogs.com/blog/897393/201902/897393-20190207133903768-1402508758.png)

🍊 获取UncaughtExceptionHandler

* 静态方法`Thread.getDefaultUncaughtExceptionHandler()`
  * 获得全局(默认)异常处理器
* 实例方法`thread.getUncaughtExceptionHandler()`
  * 获得当前线程的异常处理器

🍊 设置UncaughtExceptionHandler

![](/static/2020-09-03-19-40-31.png)

* 如果想要获取线程异常信息，需要先设置一个异常处理器
* 实现`Thread.UncaughtExceptionHandler`接口，并改写`uncaughtException(thread t, Throwable e)`方法 -- 得到一个自定义异常处理器

![](/static/2020-09-03-20-00-28.png)

### Hook 钩子线程

很多软件都存在Hook线程的校验机制, MySQL,Zookeeper,kafka

* **校验进程是否已启动，防止重复启动程序**
* VM退出时，会执行Hook线程
  * 经常在程序启动时创建一个`.lock`文件，校验程序是否启动
  * VM退出时，删除该`.lock`文件
* <font color="red">Hook线程中除了防止重新启动线程外，还可以进行资源释放</font>
  * 尽量避免Hook线程中的复杂操作
* 强制调用`halt`，强制终止VM时，才会强制中断shutdown队列（所有定义的hook线程）
  * 所有守护线程shutdown队列执行完毕前，都不会退出

```java
public static void main(String[] args) throws IOException {
    // 模拟给VM注入一个新自定义Hook线程，在程序退出时删除.lock文件
    // addShutdownHook(Thread hook)
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        System.out.println("VM退出，启动当前Hook线程，删除.lock文件");
        getLockFile().toFile().delete();
    }));

    // 程序运行时，检查.lock文件是否存在
    if (getLockFile().toFile().exists()){
        throw new RuntimeException("程序启动");
    }else{
        // 程序第一次启动，创建.lock文件
        try {
            getLockFile().toFile().createNewFile();
            System.out.println("程序启动，创建lock文件");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 模拟程序运行
    for (int i = 0; i < 100; i++) {
        System.out.println("程序运行");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//获取当前路径下，tmp.lock文件位置
public static Path getLockFile(){
    return Paths.get("","tmp.lock");
}
```

## JMM & 线程安全形式

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

### 原子性: 造成安全问题1

**Atomic**不可分割的操作，

* 针对多线程**访问共享变量(读/写)**，从其他线程来看，该操作要么尚未发生，要么执行完毕
  * 其他线程看不到该操作的**中间结果**
* 访问同一组共享变量的原子操作不能交错的

🍊 java有两种方式实现原子性

* 使用**锁**
  * 具有排他性，保证共享变量在某一时刻只能被一个线程访问
* 使用处理器的**CAS（compare and swap）指令**
  * <font color="red">硬件层次（处理器&内存）上实现，看为硬件锁</font>

#### 原子性例子

以下例子，未实现原子操作，会造成线程安全问题

```java
public class TestConcurrent {
    static class MyInt{
        int num;
        public int getNum(){
            return num++;//先读取再自增，不加锁（非原子操作）可能没来及的更新num就被其他线程读取
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MyInt myInt = new MyInt();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true){
                    System.out.println(Thread.currentThread().getId() + "->" + myInt.getNum());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
```

##### AtomicInteger: 解决方法

java提供了一个线程安全的`AtomicInteger`整型类，**可以保证操作的原子性**

```java
static class MyInt{
      AtomicInteger num = new AtomicInteger();
      public int getNum(){
          return num.getAndIncrement();//先读取再自增，不加锁（非原子操作）可能没来及的更新num就被其他线程读取
      }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
      MyInt myInt = new MyInt();
      for (int i = 0; i < 2; i++) {
          new Thread(() -> {
              while (true){
                  System.out.println(Thread.currentThread().getId() + "->" + myInt.getNum());
                  try {
                      TimeUnit.SECONDS.sleep(1);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }).start();
      }
  }
```

### 可见性: 造成安全问题2

**Visibility**

> 在多线程环境中，一个线程对**某个共享变量更新后，后续其他线程无法立即读到更新后的结果**

* 如果一个线程更新共享变量后，后续线程可以读到最新结果
  * 称这个**线程对共享变量的更新对其他线程可见**
  * 否则，称这个线程对共享变量的更新对其他线程不可见

#### 脏读

🍊 <font color="red">因为可见性问题，可能会导致其他线程读到旧数据</font>

* 也称，脏数据

🍊 因为读取属性值出现了意外

* 读取的是中间值，而不是修改后的值

#### 可见性例子 - volatile

造成不可见性的原因

![](/static/2020-09-06-19-52-24.png)

```java
static class MyTask implements Runnable{
    private boolean toCancel = false;

    @Override
    public void run() {
        while (!toCancel) {
            if (doSomething()){
            }
        }
        if (toCancel){
            System.out.println("任务取消");
        }else{
            System.out.println("任务正常结束");
        }
    }

    private boolean doSomething(){
        System.out.println("执行中");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
        return true;
    }
    public void cancel(){
        toCancel = true;
        System.out.println("取消任务");
    }
}

public static void main(String[] args) throws IOException, InterruptedException {
    MyTask myTask = new MyTask();
    new Thread(myTask).start();

    Thread.sleep(1000);
    //取消子线程
    myTask.cancel();//子线程可能看不到main线程对toCancel变量的修改
}

```

---

线程操作资源类例子
![](/static/2020-08-30-23-02-03.png)
![](/static/2020-08-30-23-02-31.png)

* 以上代码，无通知机制，对main不可见，不会跳出main的循环
* 主要关注，需要存在一种机制，当线程A值修改之后，立马通知main跳出循环

🍬 <font color="red">成员变量加上`volatile`，保证可见性</font>

### 有序性: 造成安全问题3

**Odering**

> **一个处理器上**运行**一个线程所执行的内存访问操作**在**另一个处理器运行的其他线程**看来是**乱序的**

* 乱序
  * 内存访问操作的顺序看起来发生了变化
* **多核处理器**的环境下，编写的顺序结构，**执行顺序可能没有保障**
  * **编译器**可能改变两个操作的先后顺序
  * **处理器**也可能不会按照目标代码顺序执行

🍊 重排序

* **一个处理器上执行的多个操作，在其他处理器看来，它的顺序与目标代码指定的顺序可能不一样**
* <font color="red">是对内存访问等操作的一种优化,可以在【不影响单线程程序正确的情况下】提升程序的性能</font>
* <font color="blue">但是，可能对多线程的正确性产生影响，可能导致线程安全问题</font>

🍬 与可见性问题相似，不是必然出现的

🍬 与内存操作顺序有关的概念

* 源代码顺序
  * **源码中指定的**内存访问顺序
* 程序顺序
  * 处理器上运行的**目标代码所指定的内存访问顺序**
* 执行顺序
  * 内存访问操作在处理器上的**实际执行顺序**
* 感知顺序
  * 给定处理器所感知到的该处理器&其他处理器的内存访问操作的顺序

🍊 重排序分类

* **指令重排序**
  * JIT编译器&处理器引起的
  * 程序顺序&执行顺序不一样
* **存储子系统重排序**
  * 高速缓存&写缓冲器引起的
  * 感知顺序&执行顺序不同

#### 指令重排序

> 源码顺序 & 程序顺序不一致。或，程序顺序 & 执行顺序不一致

* 指令重排是一种动作，确实对指令的顺序做了调整
* 重排序的对象指令

🍊 javac编译器一般不会执行指令重排序，而**JIT编译器可能执行指令重排序**

🍬 **处理器也可能执行指令重排序**

* 使得执行顺序&程序顺序不一致（有时候也称为处理器乱序执行）

🍊 <font color="red">指令重排不会对【单线程程序】的结果正确性产生影响</font>，但会对多线程程序产生非预期结果--线程安全问题

#### 存储子系统重排序

存储子系统

* 写缓冲器
  * 用来提高写高速缓存操作的效率
* 高速缓存
  * CPU中为了**匹配与主内存处理速度不匹配**而设计的一个高速缓存

🍊 即使处理器严格按照程序顺序执行两个内存访问操作

* 在存储子系统的作用下，**其他处理器对这两个操作的感知顺序与程序顺序不一致**
  * 即，这两个操作顺序看起来像发生了变化。称为存储子系统重排序
* <font color="blue">【存储子系统重排序】并没有真正的对指令执行顺序进行调整，而是造成一种指令执行顺序被调整的【假象】</font>

---

🍊 从处理器角度看，

* **读内存**
  * 从指定的RAM地址中加载数据到寄存器，`Load`
* **写内存**
  * 把数据存储到指定的内存地址指定的RAM存储单元中，`store`操作

🍬 **内存重排序有以下可能**，<font color="red">与具体的处理器微架构有关，不同架构的处理器允许的内存重排序不同</font>，可能会导致线程安全问题

![](/static/2020-09-07-18-39-48.png)

* LoadLoad重排序
  * 一个处理器先后执行两个读操作L1 & L2
  * 其他处理器对上面两个内存操作的感知顺序可能是L2 & L1
* StoreStore重排序
  * 一个处理器先后执行两个写操作W1 & W2
  * 其他处理器对上面两个内存操作的感知顺序可能是W2 & W1
* LoadStore重排序
  * 一个处理器先执行读内存操作L1，再执行写内存操作W1
  * 其他处理器对上面两个内存操作的感知顺序可能是W1 & L1
* StoreLoad重排序
  * 一个处理器先执行写内存操作W1，再执行读内存操作L1
  * 其他处理器对上面两个内存操作的感知顺序可能是L1 & W1

### 貌似串行语义

> JIT编译器，处理器，存储子系统按照一定规则对指令，内存操作的结果进行重排序
>
> **给单线程程序造成一种假象： 指令是按照源码的顺序执行的，可以保证单线程的正确性**【称为，貌似串行语义】，并不能保证多线程情况下程序的正确性

* 为了保证貌似串行语义，有数据依赖关系的语句不会被重排序
  * <font color="red">只有不存在数据依赖关系的语句才会被重排序</font>
* <font color="blue">存在控制依赖关系control dependency的语句，允许重排序，多线程环境下也可能造成线程安全问题</font>
  * 一条语句的执行结果会决定另一条语句能否被执行，这两条语句存在**控制依赖关系**
  * 如在if语句中允许重排，**可能存在处理器先执行if代码块，在判断if条件是否成立**

### 保证内存访问的顺序性

即，如何保证**感知顺序&源码顺序**一致。**将【貌似串行语义】扩展到能保证【多线程正确性】**

* 可以使用`volatile` & `synchronized`关键字保证有序性

### JAVA内存模型

硬件层次导致的可见性问题
![](/static/2020-09-07-19-47-11.png)

#### 缓存同步

> 一个CPU的cache不能直接读取到另一个CPU的cache.但一个CPU可以通过**缓存一致性协议(cache coherence protocol**)来**读取其他处理器cache中的数据**.并将读取的数据更新到**该处理器的cache中**

* 缓存同步：使得一个处理器上运行的线程**可以读取到**另一个处理器上运行的线程对共享数据所做的**更新**
  * **保证了可见性**

🍊 为了保证可见性，必须保证一个处理器对共享数据的更新，<font color="red">最终被写入该处理器的cache</font>

* 该过程称为，<font color="blue">冲刷处理器缓存</font>

### volatile: 轻量级同步机制

🍊 `volatile`作用

* 使变量在多个线程之间可见
* 强制线程从**公共内存中读取变量的值**，而不是从工作内存中读取

#### volatile vs synchronized

对比

* `volatile`是线程同步轻量级实现，性能比`synchronized`好
* `volatile`只能**修饰变量**，`synchronized`可以**修饰方法&代码块**
* 多线程访问volatile变量不会阻塞，而synchronized可能会阻塞
* **volatile可保证可见性，不能保证原子性.`synchronized`可以保证原子性，不能保证可见性**
* `volatile`解决变量在多个线程之间的**可见性**. `synchronized`解决多个线程之间访问公共资源**同步性**

🍊 `synchronized`已优化，效率提升

#### volatile非原子性

volatile可保证可见性，但不具备原子性

![](/static/2020-09-08-12-48-51.png)

* 需要配合synchronized保证原子性
  * 此例因为变量`count`由`static`修饰，因此只用synchornized修饰就可保证原子性&可见性

#### 12个原子变量类

**基于CAS实现的**，当对共享变量进行`read-modify-write`(变量新值依赖旧值时)更新操作时，

* 通过**原子变量类**可以保障操作的**原子性&可见性**

🍊 例如`i++`操作，

* 由于`volatile`**只能保证可见性，不能保证原子性**
* 借助原子变量类。**<font color="blue">内部借助一个`volatile`变量（可见性），并且保证了该变量的`read-modify-write`操作的原子性</font>**
  * <font color="red">因此，有时原子变量类可看作增强的`volatile`变量</font>

分组 | 原子变量类名
---------|----------
 基础数据型 | AtomicInteger,AtomicLong,AtomicBoolean
 数组型 | AtomicIntegerArray, AtomicLongArray, AtomicReferenceArray
 字段更新器 | AtomicIntegerFileUpdater, AtomicLongFieldUpdater,AtomicReferenceFieldUpdater
 引用型 | AtomicReference,AtomicStampedReference,AtomicMarkableReference

##### AtomicInteger：自增自减

已知`i++`不是原子操作，除了可以使用`synchronized`同步，**也可以使用`AtomicInteger/AtomicLong`原子类进行实现**

```java
static class MyTask implements Runnable{

    //使用原子类对象
    private AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            count.getAndIncrement();
        }
        System.out.println(Thread.currentThread().getId() + "count=" + count.get());
    }
}

public static void main(String[] args) throws IOException, InterruptedException {
    MyTask myTask = new MyTask();
    for (int i = 0; i < 100; i++) {
        new Thread(myTask).start();
    }
    Thread.sleep(1000);
    System.out.println("final result--> "+myTask.count);
}
```

##### AtomicLong: 模拟(服务器)计数器

单例模式 - 计数器AtomicLong原子类实现

```java
/*
  * 模拟服务器请求总数，处理成功数，处理失败数
  * 使用原子变量类定义一个计数器，在整个程序中都能使用，并且所有地方都使用这一个计数器，
  * - 计数器可采用单例模式【因为多处地方需要使用该计数器】
  * */
static class Indicator {
    //构造方法私有化
    private Indicator() {
    }

    // 定义一个私有的静态对象
    public static final Indicator INSTANCE = new Indicator();

    // 提供一个公共静态方法返回该类唯一实例
    public static Indicator getInstance() {
        return INSTANCE;
    }

    //  使用原子变量类，保存【请求总数，成功数，失败数】
    private final AtomicLong requestCount = new AtomicLong(0);//记录请求总数
    private final AtomicLong successCount = new AtomicLong(0);//记录成功总数
    private final AtomicLong failureCount = new AtomicLong(0);//记录失败总数

    //    有新的请求
    public void newRequestReceive() {
        requestCount.incrementAndGet();
    }

    //    处理成功
    public void requestProcessSuccess() {
        successCount.incrementAndGet();
    }

    //    处理失败
    public void requestProcessFail() {
        failureCount.incrementAndGet();
    }

    //    查看总数
    public long getRequestCount() {
        return requestCount.get();
    }

    // 查看成功数
    public long getSuccessCount() {
        return successCount.get();
    }

    // 查看失败数
    public long getFailureCount() {
        return failureCount.get();
    }
}
```

通过线程，模拟用户请求

```java
public static void main(String[] args) {
  for (int i = 0; i < 100; i++) {
      new Thread(() -> {
          //每个线程为一个用户请求, 获取单例静态计数器对象
          Indicator.getInstance().newRequestReceive();//请求总数+1
          //    随机数字，偶数-成功，奇数-失败
          int num = new Random().nextInt();
          if (num % 2 == 0) {
              Indicator.getInstance().requestProcessSuccess();
          } else {
              Indicator.getInstance().requestProcessFail();
          }
      }).start();
  }

  //打印结果
  try {
      Thread.sleep(1000);
  } catch (InterruptedException e) {
      e.printStackTrace();
  }
  System.out.println("Indicator.getInstance().getRequestCount() = " + Indicator.getInstance().getRequestCount());
  System.out.println("Indicator.getInstance().getSuccessCount() = " + Indicator.getInstance().getSuccessCount());
  System.out.println("Indicator.getInstance().getFailureCount() = " + Indicator.getInstance().getFailureCount());
}
```

##### AtomicIntegerArray: 原子数组

基本操作

```java
public static void main(String[] args) {
    //    创建一个指定长度的原子数组
    AtomicIntegerArray array = new AtomicIntegerArray(10);
    //    返回指定位置的元素
    System.out.println("array.get(0) = " + array.get(0));
    //    设置指定位置的元素
    array.set(0, 10);
    //    设置新值之前，返回数组旧值
    System.out.println("array.getAndSet(1, 11) = " + array.getAndSet(1, 11));//return 0
    System.out.println("array.get(1) = " + array.get(1));
    //    先加上某个值，再返回
    System.out.println("array.addAndGet(0, 22) = " + array.addAndGet(0, 22));//32
    //    先返回再加
    System.out.println("array.getAndSet(1, 33) = " + array.getAndSet(1, 33));//11
    System.out.println("array.get(1) = " + array.get(1));//44

    //    CAS操作, 如index=0的值为32，则赋值为新值222
    System.out.println("array.compareAndSet(0, 32, 222) = " + array.compareAndSet(0, 32, 222));
    //    自增1
    System.out.println("array.incrementAndGet(0) = " + array.incrementAndGet(0));//223
    System.out.println("array.getAndIncrement(1) = " + array.getAndIncrement(1));//44
    //    自减1
    array.decrementAndGet(0);
    array.getAndDecrement(1);
}
```

多线程中使用原子数组

##### AtomicIntegerFieldUpdater: 原子整型字段更新器

`AtomicIntegerFieldUpdater`原子整型字段更新器

* 要求
  * <font color="red">被更新的字段</font>，必须使用`volatile`修饰，保证可见性
  * 只能是实例变量，不能是静态or`final`

```java
 public static class User {
    volatile int age;//被修改的字段
    int id;

    public User(int age, int id) {
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", id=" + id +
                '}';
    }
}

public static class Updater extends Thread {
    private User user; //被更新的User对象
    private AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
    public Updater(User user) {
        this.user = user;
    }

    @Override
    public void run() {
    //子线程中对user对象age字段，自增10次
        for (int i = 0; i < 10; i++) {
            System.out.println(updater.getAndIncrement(user));
        }
    }
}

public static void main(String[] args){
  User user = new User(1234,10);
  // 10个线程同时更新user对象的age字段
  for (int i=0;i<10;i++){
    new Updater(user).start();
  }
  Thread.sleep(1000);
  System.out.println(user);
}

```

##### AtomicReference: 原子操作对象

使用AtomicReference原子读写一个string对象

```java
static AtomicReference<String> reference = new AtomicReference<>("abc");

public static void main(String[] args) {
    //创建100个线程修改原子字符串对象->def
    for (int i = 0; i < 100; i++) {
        new Thread(() -> {
            if (reference.compareAndSet("abc", "def")) {
                System.out.println(Thread.currentThread().getId()+" String is changed to def");
            }
        }).start();
    }
    //创建100个线程修改原子字符串对象->abc
    for (int i = 0; i < 100; i++) {
        new Thread(() -> {
            if (reference.compareAndSet("def", "abc")) {
                System.out.println(Thread.currentThread().getId()+" String is changed to abc");
            }
        }).start();
    }

    Thread.sleep(1000);
    System.out.println(reference.get());
}
```

##### AtomicReference: ABA问题

`AtomicReference`基于CAS实现，有可能出现ABA问题

![](/static/2020-09-09-19-26-13.png)

🍊 使用`AtomicStampedReference` & `AtomicMarkableReference`解决ABA问题

* `AtomicStampedReference`使用时间戳（版本号）
* `AtomicMarkableReference`使用标志

🍊 使用`AtomicStampedReference`演示

* 该原子类中，有一个int标记值`stamp`
* 每次执行CAS操作时，对比它的版本【比较`stamp`值】

```java
static AtomicStampedReference<String> reference = new AtomicStampedReference<>("abc", 0);

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        reference.compareAndSet("abc", "def", reference.getStamp(), reference.getStamp() + 1);
        System.out.println(Thread.currentThread().getId() + "--" + reference.getReference());
        reference.compareAndSet("def", "abc", reference.getStamp(), reference.getStamp() + 1);
    });

    Thread t2 = new Thread(() -> {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int stamp = reference.getStamp();
        System.out.println(reference.compareAndSet("abc", "ggg", stamp, stamp + 1));
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(reference.getReference());
}
```

## CAS(Compare and Swap)

> 由硬件实现.
>
> CAS可以将read-modify-write这类操作转换为原子操作

🍊 `i++`自增操作包括3个子操作，**通过CAS可以将该操作转换为原子操作**

* 主内存读取i
* i+1
* 更新后的i保存到主内存

🍬 CAS(实现原子操作)原理

![](/static/2020-09-08-13-35-44.png)
![](/static/2020-09-08-13-44-42.png)

* <font color="red">数据更新到主内存时，再次读取主内存变量的值</font>
  * 如果**现在变量的值与期望的值（操作起始读取的值）一样**，则更新

### 使用CAS实现线程安全计数器

```java
/*
* CAS实现计数器
* */
static class CASCounter{
    // volatile保证线程可见性
    volatile private long value;

    public long getValue() {
        return value;
    }

    //定义CAS方法
    private boolean compareAndSwap(long excepted, long newValue) {
        //如果当前value与excepted value相同，则更新成newValue
        synchronized (this){
            if (value == excepted) {
                value = newValue;
                return true;
            } else return false;
        }
    }

    //自增方法,while循环一直到value+1为止
    public long incrementAndGet() {
        long old;
        long newValue;
        do {
            old = value;
            newValue = old + 1;
        } while (!compareAndSwap(old, newValue));
        return newValue;
    }
}
```

```java
/*
* 进行100个线程自增1，最后值为100
*/
public static void main(String[] args) throws IOException, InterruptedException {
  CASCounter casCounter = new CASCounter();
  for (int i = 0; i < 100; i++) {
      new Thread(()->{
          System.out.println(casCounter.incrementAndGet());
      }).start();
  }
  Thread.sleep(1000);
  System.out.println(casCounter.getValue());
}
```

* 核心是`CASCounter`中的`compareAndSwap`方法
  * **再次读取主内存值，如果与预期值相同，则更新为新值**。否则撤销并重做，或者撤销并放弃

### CAS中的ABA问题

**CAS实现原子操作的假设不一定总是成立**

![](/static/2020-09-08-19-46-53.png)

* 以上就是CAS中的ABA问题，即共享变量经历了`A->B->A`的更新
* <font color="red">是否能够接收ABA问题，跟实现的算法有关</font>

🍊 如果想要规避ABA问题，可以为共享变量引入一个修订号（时间戳，如jdk8的Instant类）

* 每次修改共享变量时，**相应的修订号+1**
* 如，`ABA`变量更新过程
  * `[A,0]->[B,1]->[A,2]`
  * <font color="red">每次对【共享变量的修改】都会导致【修订号的增加】，通过修订号依然可以准确判断变量【是否被其他线程修改过】</font>