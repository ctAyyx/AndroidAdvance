package ct.com.basics.reflect.vo;

public class A {

    static {
        System.out.println("A的静态块");
    }

    public A() {
        run();
        System.out.println("被执行A的构造");

    }

    public void run() {
        System.out.println("A的Run方法");
    }
}
