package edu.gdpu.myssm.spring.annotation;

import java.lang.annotation.*;

/**
 * @author 嘿 林梓鸿
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Repository {
    String value() default "";
}
