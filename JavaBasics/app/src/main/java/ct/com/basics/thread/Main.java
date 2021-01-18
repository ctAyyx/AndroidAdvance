package ct.com.basics.thread;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        String name = main.take(2);
        System.out.println("Name" + name);

    }

    private String take(int type) {

        try {
            if (type == 1)
                return "A";
        } finally {
            System.out.println("最后执行的数据");
        }
        return "B";
    }
}
