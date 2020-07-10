package ct.com.basics.thread.course02;

import java.util.concurrent.atomic.AtomicInteger;

import ct.com.basics.CLog;

/**
 * CAS(Compare And Swap)比较和交换
 * <p>
 * 当代CPU提供一个指令 将比较和交换 作为一个原子操作
 *
 *
 */
public class CASDemo {

    public static void main(String[] args) {
        cas();

    }

    private static void cas() {


        AtomicInteger atomicInteger = new AtomicInteger(0);

        Thread01 thread01 = new Thread01();
        thread01.setAtomicInteger(atomicInteger);
        Thread01 thread02 = new Thread01();
        thread02.setAtomicInteger(atomicInteger);
        Thread01 thread03 = new Thread01();
        thread03.setAtomicInteger(atomicInteger);

        thread01.start();
        thread02.start();
        thread03.start();

    }


    static class Thread01 extends Thread {

        private AtomicInteger atomicInteger;
        private int oldValue;

        public void setAtomicInteger(AtomicInteger atomicInteger) {
            this.atomicInteger = atomicInteger;
            oldValue = atomicInteger.intValue();
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                int oldValue=atomicInteger.intValue();
                atomicInteger.compareAndSet(oldValue,oldValue+5);
                //int oldValue = atomicInteger.getAndAdd(5);
                CLog.log(Thread.currentThread().getName() + "---" + oldValue);
            }
        }
    }


}
