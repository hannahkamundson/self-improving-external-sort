package com.digit.sort;

import java.util.Arrays;

/**
 * Just your everyday generic sort
 */
public class GenericSort implements SortStrategy {

    @Override
    public int[] sort(int[] unsorted) {
        Arrays.sort(unsorted);
        return unsorted;
    }
}
