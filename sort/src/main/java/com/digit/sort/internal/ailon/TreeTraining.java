package com.digit.sort.internal.ailon;

import com.digit.command.Config;
import com.digit.sort.internal.ailon.bst.BinarySearchTree;

import java.util.stream.IntStream;

public class TreeTraining {
    /**
     * The separator value in between all the buckets
     */
    private final int[] bucketSeparators;

    /**
     * countLineFallsBelowJ[i][j] is the number of times line i falls between bucket separator (j-1, j)
     */
    private final long[][] countLineFallsBelowJ;

    public TreeTraining(int[] bucketSeparators) {
        this.bucketSeparators = bucketSeparators;
        // We need one per line
        this.countLineFallsBelowJ = new long[Config.NUMBER_LINES][bucketSeparators.length];
    }

    public void trainUnsorted(int[] unsortedData) {
        // For each line
        IntStream.range(0, unsortedData.length).parallel().forEach((int line) -> {
            int value = unsortedData[line];

            // Find what bucket separator is the minimum separator that is >= than the value
            // In other words, a value is included in a bucket j if j <= value < j+1
            int bucketIndex = 0;
            while(bucketIndex < bucketSeparators.length) {
                if (value <= bucketSeparators[bucketIndex]) {
                    break;
                }
                bucketIndex++;
            }

            // Add as another count
            countLineFallsBelowJ[line][bucketIndex]++;
        });
    }

    public BinarySearchTree[] treeResults() {
        return IntStream.range(0, countLineFallsBelowJ.length).parallel()
                .mapToObj(line -> OptimalBinarySearchTree.create(bucketSeparators, countLineFallsBelowJ[line]))
                .toArray(BinarySearchTree[]::new);
    }
}
