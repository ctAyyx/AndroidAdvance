package ct.com.basics.reflect.vo;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
public class JsonBean {

    public String name;
    public int age;

    @Override
    public String toString() {
        return "JsonBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
