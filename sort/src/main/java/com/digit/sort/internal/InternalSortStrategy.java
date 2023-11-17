package com.digit.sort.internal;

@FunctionalInterface
public interface InternalSortStrategy {
    void sort(int sampleNumber, int[] unsorted);
}
