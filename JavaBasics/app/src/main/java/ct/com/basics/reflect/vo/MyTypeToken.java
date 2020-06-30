package ct.com.basics.reflect.vo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ct.com.basics.CLog;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
public class MyTypeToken<T> {

    public MyTypeToken() {
        getSuperclassTypeParameter(this.getClass());

    }

    static void getSuperclassTypeParameter(Class<?> cls) {
        Type type = cls.getGenericSuperclass();
        if (type instanceof Class) {
            CLog.log("这是Class------");
        }else {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            CLog.log("----"+parameterizedType.toString());
        }


    }
}
