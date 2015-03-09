package org.kryogenic.util;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class Calc {
    public static int greaterThan(int i1, int i2) {
        return i1 == i2 ? 0 : i1 > i2 ? 1 : -1;
    }
    public static int lessThan(int i1, int i2) {
        return i1 == i2 ? 0 : i1 < i2 ? 1 : -1;
    }
}
