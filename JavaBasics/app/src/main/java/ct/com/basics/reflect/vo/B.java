package ct.com.basics.reflect.vo;

public class B extends A {
    static {
        System.out.println("B的静态块");
    }
    public B() {
        System.out.println("被执行B的构造");
    }

    @Override
    public void run() {
        System.out.println("B的Run方法");
    }
}
