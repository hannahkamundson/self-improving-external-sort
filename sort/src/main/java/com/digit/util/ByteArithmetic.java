package com.digit.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ByteArithmetic {
    public final static int BYTES_PER_INT = 4;

    public static int gbToByte(double gb) {
        if (gb < 0) {
            throw new IllegalArgumentException("Value has to be positive");
        }
        long value = (long) (1073741824L * gb);

        if (value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Value can't be that large");
        }

        return (int) value;
    }
}
