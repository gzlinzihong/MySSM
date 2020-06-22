package edu.gdpu.myssm;

/**
 * @author 嘿 林梓鸿
 */
public interface ApplicationListener {

    void begin();

    void completeScan();

    void prepared();

    void start();
}
