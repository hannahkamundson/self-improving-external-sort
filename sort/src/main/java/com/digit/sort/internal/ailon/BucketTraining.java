package com.digit.sort.internal.ailon;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;

public class BucketTraining {
    /**
     * This holds how many numbers of each have been seen and is thread safe
     */
    private final AtomicLongArray valuesSeen;
    private final int numberSamples;
    private final int numberBucketSeparators;

    public BucketTraining(int numberPossibleValues, int numberSamples, int numberBuckets) {
        this.valuesSeen = new AtomicLongArray(numberPossibleValues);
        this.numberSamples = numberSamples;
        // We want 1 less than the number of lines because these are the separators between buckets
        // However, we're adding Integer.MAX for the last one because we want to know the probability it is after the
        // final line
        this.numberBucketSeparators = numberBuckets;
    }

    public void trainSorted(int[] sortedData) {
        // In parallel
        Arrays.stream(sortedData).parallel()
                .forEach(valuesSeen::incrementAndGet);
    }

    /**
     * Get the learned separators between buckets.
     */
    public int[] bucketSeparatorResult() {
        int[] results = new int[numberBucketSeparators];
        int resultsIndex = 0;
        long leftToAdd = 0;
        for (int i = 0; i < valuesSeen.length(); i++) {
            leftToAdd += valuesSeen.get(i);

            while (resultsIndex < (results.length - 1) && leftToAdd >= numberSamples) {
                results[resultsIndex] = i;
                leftToAdd = leftToAdd - numberSamples;
                resultsIndex++;
            }
        }

        results[resultsIndex] = Integer.MAX_VALUE;
        return results;
    }
}
