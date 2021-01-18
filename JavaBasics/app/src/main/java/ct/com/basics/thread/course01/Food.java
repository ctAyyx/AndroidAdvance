package ct.com.basics.thread.course01;

import java.util.concurrent.locks.ReentrantLock;

public class Food {

    //volatile 保证可见性 但不保证原子性
    //最轻量级的同步工具
    //在 一写多读的情况下 可以保证数据的安全和同步
    private volatile int count = 0;
    public synchronized void checkFood() {
        while (count < 10) {
            try {
                System.out.println("食物数量不足" + count);
                wait();
            } catch (InterruptedException e) {
                System.out.println("--->");
            }
        }

        System.out.println("我们现在有足够的食物了" + count);
    }

    public synchronized void providerFood() {

        try {
            count++;
            Thread.sleep(3000);
            System.out.println("通知检测食物是否足够");
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class Provider implements Runnable {

        private Food food;

        public Provider(Food food) {
            this.food = food;
        }

        @Override
        public void run() {

            food.providerFood();
        }
    }


    static class Check implements Runnable {
        private Food food;

        public Check(Food food) {
            this.food = food;
        }

        @Override
        public void run() {
            food.checkFood();
        }
    }

}
