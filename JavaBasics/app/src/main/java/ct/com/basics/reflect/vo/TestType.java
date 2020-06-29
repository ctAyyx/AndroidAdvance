package ct.com.basics.reflect.vo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC :用来测试反射获取泛型类型
 */
public class TestType<T> {
    //可以通过 TypeVariable 获取泛型信息
    public T data;

    //可以通过 ParameterizedType获取字段的泛型信息
    public TestType<T> testType;
    public Map<String, Tom> map;

    //可以通过 GenericArrayType获取数组泛型信息
    public T[] toms;
    public List<T>[] lists;

    //
    public TestType<? extends Person> wildcard;


    public TestType() {

        Type genericSuperclass =getClass().getGenericSuperclass();
        ParameterizedType type  = (ParameterizedType) genericSuperclass;
    }
}
