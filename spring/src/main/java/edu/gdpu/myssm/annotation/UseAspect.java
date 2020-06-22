package edu.gdpu.myssm.annotation;

import java.lang.annotation.*;

/**
 * @author 嘿 林梓鸿
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UseAspect {
    String value() default "";

    String[] exclude() default {};
}
