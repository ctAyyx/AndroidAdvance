package com.ct.apt_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ARouter {

    /**
     * 路径 /groupName/Main
     */
    String path();

    /**
     * 分组
     */
    String group() default "";
}