package ct.com.basics.reflect.job;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :通过反射 注解 动态代理实现点击事件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {

    @IdRes int[] value();
}
