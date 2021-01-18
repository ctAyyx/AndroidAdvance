package ct.com.basics.thread.course01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import ct.com.basics.CLog;

/**
 * 1.线程的启动方式.
 */
public class Course01 {

    public static void main(String[] args) {
        Course01 course = new Course01();

        //启动线程
        //course.startThread();

        //停止线程
        // course.stopThread();
        //course.stopThread02();

        //测试守护线程
        //整个主线程销毁了 它就销毁了
        //course.daemon();

        //wait  notify
        //course.team();

        //
        course.pause();
    }


    /**
     * 启动线程的方式 只有两种
     * <p>
     * 1. 继承Thread 类
     * 2. 实现Runnable接口
     */
    private void startThread() {

        Thread thread01 = new Thread01();
        Thread thread02 = new Thread(new Thread02());

        Thread03 callable = new Thread03();
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread03 = new Thread(future);

        //还可以使用线程池

        thread01.start();
        thread02.start();
        thread03.start();
        try {
            String result = future.get();
            CLog.log("获取的值:" + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 线程锁
     * synchronized关键字:当它修饰的方法是静态方法,则使用的是类锁,否则 使用的是对象锁
     */
    private void threadLock() {

    }

    /**
     * 停止线程
     * 不能使用stop方法,就是不安全的
     * 1.可以使用发送中断信号
     * 2.将线程设置为守护线程
     */
    private void stopThread() {
        Thread04 thread = new Thread04();
        thread.start();

        try {
            Thread.sleep(3 * 1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void stopThread02() {
        Thread05 thread = new Thread05();
//设置Thread05为当前线程的守护线程
        thread.setDaemon(true);
        thread.start();


        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void daemon() {
        new Thread06().start();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程协作
     * wait notify/notifyAll
     * <p>
     * 需要 synchronized 关键字修饰的方法 或则代码块
     */

    private void team() {

        Food food = new Food();
        Thread provider = new Thread(new Food.Provider(food));
        Thread check = new Thread(new Food.Check(food));

        provider.start();
        check.start();

    }


    class Thread01 extends Thread {
        @Override
        public void run() {
            CLog.log("继承Thread:" + Thread.currentThread().getName());
        }
    }

    class Thread02 implements Runnable {

        @Override
        public void run() {
            CLog.log("实现Runnable接口:" + Thread.currentThread().getName());
        }
    }


    class Thread03 implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "实现Callable接口 实现数据返回";
        }
    }

    class Thread04 extends Thread {
        @Override
        public void run() {
            //我们可以根据中断信号 来停止线程的循环 让run方法执行完成

//            while (!interrupted()) {
//                CLog.log("------------------->");
//            }

            //但是 sleep的中断异常会重置中断信号,导致线程无法终止
            //所以我们要在异常捕获中再次发送一次中断操作
            while (!interrupted()) {
                try {
                    Thread.sleep(300);
                    CLog.log("------------------->");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //所以我们要在异常捕获中再次发送一次中断操作
                    interrupt();
                }
            }
        }
    }


    class Thread05 extends Thread {
        private int num = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                    CLog.log("------------------->" + num++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Thread06 extends Thread {
        @Override
        public void run() {
            Thread05 thread = new Thread05();
//设置Thread05为当前线程的守护线程
            thread.setDaemon(true);
            thread.start();


            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * sleep yield wait
     * <p>
     * 不同方法对锁的持有
     * <p>
     * sleep 和 yield 都不会释放锁
     */
    private static final String pauseLock = "pause";

    private void pause() {
        SleepRun sleepRun = new SleepRun();
        YieldRun yieldRun = new YieldRun();
        WaitRun waitRun = new WaitRun();

        Thread thread1 = new Thread(sleepRun);
        Thread thread2 = new Thread(yieldRun);
        Thread thread3 = new Thread(waitRun);
        thread2.start();
        thread1.start();
        //thread3.start();

    }

    static class SleepRun implements Runnable {

        private JoinRun joinRun = new JoinRun();

        @Override
        public void run() {
            synchronized (pauseLock) {
                try {

                    System.out.println("Sleep 5");
                    joinRun.start();
                    joinRun.join();
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class YieldRun extends Thread {

        @Override
        public void run() {
            synchronized (pauseLock) {
                System.out.println("Yield ");
                yield();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Yield ");

            }
        }
    }

    static class WaitRun implements Runnable {

        @Override
        public void run() {
            synchronized (pauseLock) {
                try {
                    System.out.println("Wait ");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class JoinRun extends Thread {

        @Override
        public void run() {
            synchronized (pauseLock) {
                try {
                    System.out.println("Join ");
                    Thread.sleep(3000);
                    System.out.println("Join Over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


