package ct.com.basics.reflect.job;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWrite {

    String value() default "";
}
