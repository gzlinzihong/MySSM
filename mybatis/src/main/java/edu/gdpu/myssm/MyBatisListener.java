package edu.gdpu.myssm;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 23:58:38
 */
public class MyBatisListener implements ApplicationListener {
    @Override
    public void begin() {
        System.out.println("begin...");
    }

    @Override
    public void completeScan() {
        System.out.println("completeScan...");
    }

    @Override
    public void prepared() {
        System.out.println("prepared...");
    }

    @Override
    public void start() {
        System.out.println("start...");
    }
}
