package com.digit.sort.internal;

@FunctionalInterface
public interface InternalSortStrategy {
    /**
     * Sort the unsorted data based on the sample number we are on. Note: some algorithms won't care about sample
     * number
     * @param sampleNumber What sample is this that we are sorting?
     * @param unsorted The array that will be sorted
     */
    void sort(int sampleNumber, int[] unsorted);

    /**
     * Take a break and learn whatever needs to be learned. This is so learning doesn't have to
     * happen when we have a large number of unsorted data in memory. This occurs after the sort method is
     * called.
     */
    default void learn(int sampleNumber) {
        // Do nothing in general because a lot of algorithms don't have any learning
    }
}
