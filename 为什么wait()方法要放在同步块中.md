# 为什么wait()方法要放在同步块中

```java
@Test
public void waitInSyncBlockTest() {
    try {
        new Object().wait();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

![image-20201018161135168](E:\GitHub\StudyNotes\为什么wait()方法要放在同步块中.assets\image-20201018161135168.png)

## Lost Wake-Up Problem
事情得从一个多线程编程里面臭名昭著的问题"Lost wake-up problem"说起。

这个问题并不是说只在Java语言中会出现，而是会在所有的多线程环境下出现。

假如有两个线程，一个消费者线程，一个生产者线程。生产者线程的任务可以简化成将count加一，而后唤醒消费者；消费者则是将count减一，而后在减到0的时候陷入睡眠：

生产者伪代码：

```txt
count+1;

notify();
```

消费者伪代码：

```txt
while(count<=0)

   wait()

count--
```

熟悉多线程的朋友一眼就能够看出来，这里面有问题。什么问题呢？

生产者是两个步骤：

1. count+1;

2. notify();

消费者也是两个步骤：

1. 检查count值；

2. 睡眠或者减一；

万一这些步骤混杂在一起呢？比如说，初始的时候count等于0，这个时候消费者检查count的值，发现count小于等于0的条件成立；就在这个时候，发生了上下文切换，生产者进来了，噼噼啪啪一顿操作，把两个步骤都执行完了，也就是发出了通知，准备唤醒一个线程。这个时候消费者刚决定睡觉，还没睡呢，所以这个通知就会被丢掉。紧接着，消费者就睡过去了……

![img](E:\GitHub\StudyNotes\为什么wait()方法要放在同步块中.assets\20190412142433326.jpg)

这就是所谓的lost wake up问题。

## 那么怎么解决这个问题呢？

现在我们应该就能够看到，问题的根源在于，消费者在检查count到调用wait()之间，count就可能被改掉了。

这就是一种很常见的竞态条件。

很自然的想法是，让消费者和生产者竞争一把锁，竞争到了的，才能够修改count的值。

于是生产者的代码是:

```java
tryLock()
count+1
 
notify()
releaseLock()
```

消费者的代码是:

```java
tryLock()
while(count <= 0)
    wait()
 
count-1
releaseLock()
```

注意的是，我这里将两者的两个操作都放进去了同步块中。

现在来思考一个问题，生产者代码这样修改行不行？

```java
tryLock()
count+1
notify()
releaseLock()
```

答案是，这样改毫无卵用，依旧会出现lost wake up问题，而且和无锁的表现是一样的。

## 终极答案

所以，我们可以总结到，为了避免出现这种lost wake up问题，在这种模型之下，总应该将我们的代码放进去的同步块中。

Java强制我们的wait()/notify()调用必须要在一个同步块中，就是不想让我们在不经意间出现这种lost wake up问题。

不仅仅是这两个方法，包括java.util.concurrent.locks.Condition的await()/signal()也必须要在同步块中：

```java
private ReentrantLock lock = new ReentrantLock();
private Condition condition = lock.newCondition();

@Test
public void test() {
    try {
        condition.signal();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

![image-20201018162536682](E:\GitHub\StudyNotes\为什么wait()方法要放在同步块中.assets\image-20201018162536682.png)

准确的来说，即便是我们自己在实现自己的锁机制的时候，也应该要确保类似于wait()和notify()这种调用，要在同步块内，防止使用者出现lost wake up问题。

Java的这种检测是很严格的。它要求的是，一定要处于锁对象的同步块中。举例来说：

```java
private Object obj = new Object();
private Object anotherObj = new Object();
 
@Test
public void produce() {
    synchronized (obj) {
        try {
            // 同步块要对当前线程负责
            // 而不是anotherObj.notify();
            obj.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## wait和notify的用法

wait()、notify()和notifyAll()

1. wait()、notify() 和 notifyAll()方法是本地方法，并且为 final 方法，无法被重写。

2. 调用某个对象的 wait() 方法能让当前线程阻塞，并且当前线程必须拥有此对象的 monitor（即锁，或者叫管程）。
3. 调用某个对象的 notify() 方法能够唤醒一个正在等待这个对象的 monitor 的线程，如果有多个线程都在等待这个对象的 monitor，则只能唤醒其中一个线程。
4. 调用 notifyAll() 方法能够唤醒所有正在等待这个对象的monitor的线程。

```java
/**
 * wait() && notify()方法
 * 这两个方法是在Object中定义的，用于协调线程同步，比 join 更加灵活
 */
public class NotifyDemo {
    public static void main(String[] args) {
        //写两个线程 1.图片下载
        Object obj=new Object();
        Thread download=new Thread(){
            public void run() {
                System.out.println("开始下载图片");
                for (int i = 0; i < 101; i+=10) {
                    System.out.println("down"+i+"%");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("图片下载成功");
                synchronized (obj) {
                    obj.notify();//唤起
                }
                System.out.println("开始下载附件");
                for (int i = 0; i < 101; i+=10) {
                    System.out.println("附件下载"+i+"%");
 
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                System.out.println("附件下载成功");
            }
        };
        //2.图片展示
        Thread show=new Thread(){
            public void run(){
                synchronized (obj) {
                    try {
                        obj.wait();//阻塞当前
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("show:开始展示图片");
                    System.out.println("图片展示完毕");
                }
 
            }
        };
        download.start();
        show.start();
    }
}
```

---

当一个线程需要调用对象的wait()方法的时候，这个线程必须拥有该对象的锁，接着它就会释放这个对象锁并进入等待状态直到其他线程调用这个对象上的notify()方法。同样的，当一个线程需要调用对象的notify()方法时，它会释放这个对象的锁，以便其他在等待的线程就可以得到这个对象锁。由于所有的这些方法都需要线程持有对象的锁，这样就只能通过同步来实现，所以他们只能在同步方法或者同步块中被调用。

---

首先，要明白，每个对象都可以被认为是一个"监视器monitor"，这个监视器由三部分组成（一个独占锁，一个入口队列，一个等待队列）。注意是一个对象只能有一个独占锁，但是任意线程线程都可以拥有这个独占锁。

对于对象的非同步方法而言，任意时刻可以有任意个线程调用该方法。（即普通方法同一时刻可以有多个线程调用）

对于对象的同步方法而言，只有拥有这个对象的独占锁才能调用这个同步方法。如果这个独占锁被其他线程占用，那么另外一个调用该同步方法的线程就会处于阻塞状态，此线程进入入口队列。

若一个拥有该独占锁的线程调用该对象同步方法的wait()方法，则该线程会释放独占锁，并加入对象的等待队列；（为什么使用wait()？希望某个变量被设置之后再执行，notify()通知变量已经被设置。）

某个线程调用notify(),notifyAll()方法是将等待队列的线程转移到入口队列，然后让他们竞争锁，所以这个调用线程本身必须拥有锁。