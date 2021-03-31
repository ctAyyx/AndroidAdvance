package ct.com.basics.thread;

import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        main.run();

    }

    private void run() {
//        String name = take(1);
//        System.out.println("Name" + name);

        doTest2();
    }

    /**
     * Finally
     */
    private String take(int type) {

        try {
            if (type == 1)
                return "A";
        } finally {
            System.out.println("最后执行的数据");

        }
        return "B";
    }

    /**
     * 享元设计模式
     * <p>
     * i in     -127~128之间     在装箱的时候 创建的Integer是同一个对象
     * i not in -127~128之间     在装箱的时候 创建的Integer不是同一个对象
     */
    private void doTest2() {
        //int i = 100;
        int i = 1000;

        Integer it01 = i;
        Integer it02 = i;

        System.out.println("是否是同一个对象:" + (it01 == it02));

    }

    private void doTest3() {

        //SDK 自带的线程池 都会有OOM的问题
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newScheduledThreadPool(1);
        Executors.newSingleThreadExecutor();
        Executors.newSingleThreadScheduledExecutor();
    }

}
