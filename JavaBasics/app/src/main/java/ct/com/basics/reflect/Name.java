package ct.com.basics.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TIME : 2020/6/13 0013
 * AUTHOR : CT
 * DESC : 注解的实现
 * <p>
 * Java中所有的注解，默认实现 Annotation 接口
 * 注解的声明使用 @interface关键字
 * <p>
 * 元注解
 * 对注解类型进行注解的注解类，我们称之为 meta-annotation（元注解）
 * 一般我们在使用的元注解有:
 *
 * @Documented 标记注解可以被使用在文档中
 * @Inherited 表示可以继承
 * @Target 表示注解作用的对象 见ElementType
 * @Retention 表示该注解保留的阶段
 * RetentionPolicy.SOURCE  表示注解保留到编译阶段---一般用于APT和语法检测
 * RetentionPolicy.CLASS   表示注解保留在.class文件中,对JVM不可见.----用于字节码增强(字节码插桩等技术)
 * RetentionPolicy.RUNTIME 表示注解可以被JVM读取,可以在运行环境中使用它.----在程序运行期间，通过反射技术动态获取注解与其元素，从而完成不同的逻辑判定
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    //参数定义 类似接口
    //定义一个String类型参数 参数名是value 默认值是''
    String value() default "";
}
