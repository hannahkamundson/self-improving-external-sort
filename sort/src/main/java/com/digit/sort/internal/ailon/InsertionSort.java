package com.digit.sort.internal.ailon;

import com.digit.sort.internal.InternalSortStrategy;

public class InsertionSort implements InternalSortStrategy {
    @Override
    public void sort(int sampleNumber, int[] unsorted) {
        sort(unsorted);
    }

    public void sort(int[] unsorted) {
        int n = unsorted.length;
        for (int i = 1; i < n; ++i) {
            int key = unsorted[i];
            int j = i - 1;

            while (j >= 0 && unsorted[j] > key) {
                unsorted[j + 1] = unsorted[j];
                j = j - 1;
            }
            unsorted[j + 1] = key;
        }
    }
}
