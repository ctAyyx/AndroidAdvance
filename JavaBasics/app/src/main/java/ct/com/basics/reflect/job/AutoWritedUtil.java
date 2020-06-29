package ct.com.basics.reflect.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;


import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
public class AutoWritedUtil {

    public static void autoWrite(Activity activity) {

        //第一步 获取Intent对象
        Intent intent = activity.getIntent();
        //第二步 获取Activity中被AutoWrite注解的字段
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoWrite.class)) {
                try {
                    //获取注解信息
                    AutoWrite autoWrite = field.getAnnotation(AutoWrite.class);
                    String key = autoWrite.value();
                    //如果key == "" 则使用Field的名称
                    if (key.equals(""))
                        key = field.getName();

                    //获取Intent中的值
                    Object value = intent.getExtras().get(key);

                    //针对数组的处理
                    if (field.getType().isArray()) {
                        //获取数组对象如果不是数组 则返回是null
                        Class<?> componentType = field.getType().getComponentType();
                        if (Parcelable.class.isAssignableFrom(componentType)) {
                            Object[] objs = (Object[]) value;
                            Object[] arrays = Arrays.copyOf(objs,objs.length,(Class<? extends java.lang.Object[]>) field.getType());
                            value = arrays;
                        }
                    }


                    //赋值
                    field.setAccessible(true);
                    field.set(activity, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }

}
