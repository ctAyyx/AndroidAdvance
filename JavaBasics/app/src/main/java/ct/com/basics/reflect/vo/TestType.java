package ct.com.basics.reflect.vo;

import java.util.List;
import java.util.Map;

import ct.com.basics.genericity.vo.Apple;
import ct.com.basics.genericity.vo.Food;
import ct.com.basics.genericity.vo.Pear;
import ct.com.basics.genericity.vo.Plate;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :用来测试反射获取泛型类型
 */
public class TestType<M extends Person> extends Plate<Apple> {
    //可以通过 TypeVariable 获取泛型信息
    public M data;

    public String name;

    public int age;

    //可以通过 ParameterizedType获取字段的泛型信息
    public TestType<M> testType;
    public Map<String, Plate<Apple>> map;

    //可以通过 GenericArrayType获取数组泛型信息
    public M[] toms;
    public List<M>[] lists;

    //
    public TestType<? extends Person> wildcard;
    public TestType<? super Person> wildcard2;


    public TestType() {


    }
}
