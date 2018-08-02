package com.grape.supermarket.common.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LQW on 2017/9/26.
 */

public class ProgressSingleton {
    private volatile boolean compete = false;
    private int size;
    private byte[] data;
    private AtomicInteger success = new AtomicInteger(0);
    private AtomicInteger fail = new AtomicInteger(0);
    private AtomicInteger now = new AtomicInteger(0);

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getSuccess() {
        return success.get();
    }

    public void addSuccess(int num) {
        success.addAndGet(num);
    }

    public int getFail() {
        return fail.get();
    }

    public void addFail(int num) {
        fail.addAndGet(num);
    }

    public int getNow() {
        return now.get();
    }

    public void addNow(int num) {
        now.addAndGet(num);
    }

    public boolean isCompete() {
        return compete;
    }

    public void setCompete(boolean compete) {
        this.compete = compete;
    }
}
