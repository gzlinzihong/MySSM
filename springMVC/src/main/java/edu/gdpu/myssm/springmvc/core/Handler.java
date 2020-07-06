package edu.gdpu.myssm.springmvc.core;

import java.lang.reflect.Method;

/**
 * @author 嘿 林梓鸿
 */
public interface Handler {

    Object handle();

    Method getMethod();
}
