package com.digit.sort.internal;

import java.util.Arrays;

/**
 * Just your everyday generic sort
 */
public class GenericSort implements InternalSortStrategy {

    @Override
    public void sort(int sampleNumber, int[] unsorted) {
        Arrays.sort(unsorted);
    }
}
