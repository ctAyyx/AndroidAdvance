package ct.com.basics.thread.course02;

import ct.com.basics.CLog;

/**
 * ThreadLocal 线程隔离 为了实现多线程情况下 线程A在获取数据时 数据被线程B修改的情况
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<Person> threadLocal2 = new ThreadLocal<>();
    public static Person person = new Person("去哪聚", 40);

    public static void main(String[] args) {

        Person person = new Person("AA", 10);
        threadLocal.set(100);
        Thread01 thread01 = new Thread01();
        thread01.setThreadLocal(threadLocal);
        thread01.setThreadLocal2(threadLocal2, person);
        Thread02 thread02 = new Thread02();
        thread02.setThreadLocal(threadLocal);
        thread02.setThreadLocal2(threadLocal2, person);

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
        private ThreadLocal<Person> threadLocal2;
        private Person person;

        private int count = 0;

        public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
            this.threadLocal = threadLocal;
            threadLocal.set(0);
        }

        public void setThreadLocal2(ThreadLocal<Person> threadLocal, Person person) {
            this.threadLocal2 = threadLocal;
            this.person = person;
            CLog.log("Thread01设置的person:" + person);
            this.threadLocal2.set(person);
        }

        @Override
        public void run() {
            threadLocal2.set(ThreadLocalDemo.person);
            for (int i = 0; i < 10; i++) {
                Integer value = threadLocal.get();
                CLog.log("----->" + value);
                if (value == null)
                    value = 0;
                threadLocal.set(value + 10 * i);
            }


            CLog.log("Thread01：" + threadLocal.get());
            CLog.log("Thread01：Person数据" + threadLocal2.get());
        }
    }

    static class Thread02 extends Thread {

        private ThreadLocal<Integer> threadLocal;
        private ThreadLocal<Person> threadLocal2;
        private Person person;

        public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
            this.threadLocal = threadLocal;
        }

        public void setThreadLocal2(ThreadLocal<Person> threadLocal, Person person) {
            this.threadLocal2 = threadLocal;
            this.person = person;
            CLog.log("Thread02设置的person:" + person);
            this.threadLocal2.set(person);
        }

        @Override
        public void run() {
            threadLocal2.set(ThreadLocalDemo.person);
            for (int i = 0; i < 10; i++) {
                threadLocal.set(10);
            }
            threadLocal2.get().name = "XXXXXX";
            CLog.log("Thread02：" + threadLocal.get());
            CLog.log("Thread02：Person数据" + threadLocal2.get());


        }
    }

    static class Person {
        public String name;
        public int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
