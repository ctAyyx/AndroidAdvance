package ct.com.basics.reflect.vo;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :
 */
public class Person {

    private String name;
    public int age;
    String work;
    protected String address;
    public int height;


    public Person() {
    }

    public Person(int age, String work) {
        this.age = age;
        this.work = work;
    }

    protected Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Person(String name, int age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void setAge(int age) {
        this.age = age;
    }


    protected int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", work='" + work + '\'' +
                ", address='" + address + '\'' +
                ", height=" + height +
                '}';
    }
}
