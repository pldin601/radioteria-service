package com.radioteria.backing.utils;

public class MathUtil {

    public static boolean between(long left, long right, long value) {
        return left <= value && right >= value;
    }

    public static boolean between(int left, int right, int value) {
        return left <= value && right >= value;
    }

    public static boolean between(float left, float right, float value) {
        return left <= value && right >= value;
    }

}
