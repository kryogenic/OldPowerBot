package org.kryogenic.util;

/**
 * @author: Kale
 * @date: 20/08/12
 * @version: 0.0
 */
public class AutoIncInteger {
    private int value;
    private int incAmount = 1;

    public AutoIncInteger(int initValue) {
        value = initValue;
    }

    public int incAndGet() {
        inc();
        return get();
    }

    public void inc() {
        value += incAmount;
    }

    public void inc(int i) {
        value += i;
    }

    public int get() {
        return value;
    }

    public void setIncAmount(int incAmount) {
        this.incAmount = incAmount;
    }
}
