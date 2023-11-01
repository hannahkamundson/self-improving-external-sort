package com.digit.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ByteArithmetic {
    public static long gbToByte(double gb) {
        return (long) (1073741824L * gb);
    }
}
