package com.digit.sort.internal.ailon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BucketTrainingTest {
    @Test
    void happyPath() {
        int numberPossibleValues = 16; // This means the max value = 15
        int numberSamples = 3;
        int numberLines = 6;
        BucketTraining training = new BucketTraining(numberPossibleValues, numberSamples, numberLines);

        int[] sample1 = new int[] { 1, 3, 3, 4, 4, 15 };
        int[] sample2 = new int[] { 2, 2, 2, 7, 7, 9 };
        int[] sample3 = new int[] { 5, 6, 7, 9, 10, 11 };

        training.trainSorted(sample1);
        training.trainSorted(sample2);
        training.trainSorted(sample3);

        int[] actual = training.bucketSeparatorResult();
        // We expect 6 buckets => 5 bucket separators
        // The sorted list is: 1, 2, 2, 2, 3, 3, 4, 4, 5, 6, 7, 7, 7, 9, 9, 10, 11, 15
        // buckets are: 1, 2, 2 | 2, 3, 3 | 4, 4, 5 | 6, 7, 7 | 7, 9, 9 | 10, 11, 15
        int[] expected = new int[] { 2, 3, 5, 7, 9, Integer.MAX_VALUE };

        assertArrayEquals(expected, actual);
    }
}
