package ct.com.basics.reflect.job;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
class InjectUtil {

    public static void inject(final Activity activity) {
        //遍历方法
        Method[] methods = activity.getClass().getDeclaredMethods();

        for (final Method method : methods) {
            if (!method.isAnnotationPresent(OnClick.class))
                continue;
            OnClick onClick = method.getAnnotation(OnClick.class);
            //获取数据
            for (int id : onClick.value()) {

                activity.findViewById(id)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {

                                    method.invoke(activity, v);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }

        }

    }


    public static void inject2(final Activity activity) {

        Object obj = Proxy.newProxyInstance(activity.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(activity, args);
            }
        });


        View.OnClickListener listener = (View.OnClickListener) obj;

    }

}
