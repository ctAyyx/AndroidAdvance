package ct.com.basics.thread.course04;

public class AQSDemo {

    private static int count = 0;

    private CustomLock lock = new CustomLock();

    public static void main(String[] args) {

        AQSDemo demo = new AQSDemo();
        //demo.run01();
        //测试锁的可重入
        demo.run02();

    }


    private void run01() {
        LockRun run1 = new LockRun(lock, "Run1");
        LockRun run2 = new LockRun(lock, "Run2");
        LockRun run3 = new LockRun(lock, "Run3");
        LockRun run4 = new LockRun(lock, "Run4");

        run1.start();
        run2.start();
        run3.start();
        run4.start();

        try {
            Thread.sleep(300);
            System.out.println("最后获取Count:" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private void run02() {
        LockRun2 run2 = new LockRun2(lock, "ReEnterLock");
        run2.start();
    }

    private class LockRun extends Thread {
        private CustomLock lock;

        public LockRun(CustomLock lock, String name) {
            this.lock = lock;
            setName(name);
        }

        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 50; i++) {
                    count = count + 1;
                }

            } finally {
                lock.unlock();
            }

            System.out.println(Thread.currentThread().getName() + ":" + count);
        }
    }

    //测试锁的可重入
    private class LockRun2 extends Thread {
        private CustomLock lock;

        public LockRun2(CustomLock lock, String name) {
            this.lock = lock;
            setName(name);
        }

        @Override
        public void run() {
            doWork1();
        }

        private void doWork1() {
            lock.lock();
            doWork2();
            lock.unlock();
        }

        private void doWork2() {
            lock.lock();
            lock.unlock();
            System.out.println("完成了所有的任务");
        }

    }
}
