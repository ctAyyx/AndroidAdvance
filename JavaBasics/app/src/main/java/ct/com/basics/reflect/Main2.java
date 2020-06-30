package ct.com.basics.reflect;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ct.com.basics.CLog;
import ct.com.basics.reflect.vo.JsonBean;
import ct.com.basics.reflect.vo.MyTypeToken;
import ct.com.basics.reflect.vo.Response;
import ct.com.basics.reflect.vo.TestType;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
class Main2 {

    public static void main(String[] args) {

        fromJson();
    }

    /**
     * 获取泛型的具体类信息
     * <p>
     * 关于Gson中的TypeToken为什么要使用{}
     * <p>
     * 如果一个子类继承的父类泛型是确定的 则子类会保存泛型的信息 而不会擦除掉
     * 比如 ApplePlate extends Plate<Apple>
     * 则在ApplePlate的class中会保存父类的泛型信息
     * signature Lct/com/basics/genericity/vo/FruitPlate<Lct/com/basics/genericity/vo/Apple;>;
     * <p>
     * 通过这个特性，我们就可以通过实现TypeToken的子类来获取具体的泛型信息 实现反序列化
     */
    private static void getGenericType() {
        TestType testType = new TestType<String>() {
        };

        CLog.log(testType.getClass().getGenericSuperclass().toString());
        ParameterizedType parameterizedType = (ParameterizedType) testType.getClass().getGenericSuperclass();

        CLog.log("泛型的类型:" + parameterizedType.getActualTypeArguments()[0].toString());
        Type type = parameterizedType.getActualTypeArguments()[0];
    }


    /**
     * 测试实现反序列化
     */
    private static void fromJson() {

        JsonBean jsonBean = new JsonBean();
        jsonBean.age = 20;
        jsonBean.name = "测试序列化";

        Response<JsonBean> response = new Response<>();
        response.data = jsonBean;

        String json = new Gson().toJson(response);
        CLog.log("服务端获取的Json数据:" + json);


        Type type = new MyTypeToken<Response<JsonBean>>() {
        }.getClass().getGenericSuperclass();

        CLog.log("获取到的泛型信息:" + type);
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //获取需要转换的实体数据
        Type relType = parameterizedType.getActualTypeArguments()[0];
        CLog.log("获取需要转换的实体数据:" + relType);

        try {

            ParameterizedType parameterizedType1 = (ParameterizedType) relType;

            CLog.log("声明的信息:" + (Class<?>) parameterizedType1.getRawType());
            Class<?> cls = (Class<?>) parameterizedType1.getRawType();
            //获取Response的实体类

            Object o = cls.newInstance();
            CLog.log("获取的实体:" + o);
            CLog.log("response的泛型信息:" + parameterizedType1.getActualTypeArguments()[0]);


            //获取JsonBean的实体类
            Class<?> cls2 = (Class<?>) parameterizedType1.getActualTypeArguments()[0];
            Object o2 = cls2.newInstance();
            CLog.log("获取的实体:" + o2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
