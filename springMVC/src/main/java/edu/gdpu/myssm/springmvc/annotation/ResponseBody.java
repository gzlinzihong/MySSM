package edu.gdpu.myssm.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author 嘿 林梓鸿
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ResponseBody {
}
