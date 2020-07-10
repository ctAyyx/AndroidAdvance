package ct.com.basics.thread.course02;

import ct.com.basics.CLog;

/**
 * ThreadLocal 线程隔离 为了实现多线程情况下 线程A在获取数据时 数据被线程B修改的情况
 */
public class ThreadLocalDemo {


    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        threadLocal.set(100);
        Thread01 thread01 = new Thread01();
        thread01.setThreadLocal(threadLocal);
        Thread02 thread02 = new Thread02();
        thread02.setThreadLocal(threadLocal);

        thread01.start();
        thread02.start();

        try {
            thread01.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            threadLocal.set(threadLocal.get() - i);
        }

        CLog.log("main Thread:" + threadLocal.get());

    }


    static class Thread01 extends Thread {

        private ThreadLocal<Integer> threadLocal;

        private int count = 0;

        public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
            this.threadLocal = threadLocal;
            threadLocal.set(0);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Integer value = threadLocal.get();
                CLog.log("----->" + value);
                if (value == null)
                    value = 0;
                threadLocal.set(value + 10 * i);
            }

            CLog.log("Thread01：" + threadLocal.get());
        }
    }

    static class Thread02 extends Thread {

        private ThreadLocal<Integer> threadLocal;

        public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                threadLocal.set(10);
            }

            CLog.log("Thread02：" + threadLocal.get());
        }
    }
}
