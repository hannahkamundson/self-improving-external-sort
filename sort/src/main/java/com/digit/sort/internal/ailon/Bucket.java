package com.digit.sort.internal.ailon;

import com.digit.sort.internal.InsertionSort;
import lombok.Getter;

import java.util.Arrays;

public class Bucket {
    @Getter
    private int[] values;

    public void add(int value) {
        if (values == null) {
            values = new int[] { value };
        }
        // Unlikely we will need to do this many times
        values = Arrays.copyOf(values, values.length + 1);
        values[values.length - 1] = value;

        InsertionSort.sort(values);
    }
}