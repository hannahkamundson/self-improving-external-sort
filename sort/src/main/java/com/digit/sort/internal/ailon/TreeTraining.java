package com.digit.sort.internal.ailon;

import com.digit.command.Config;
import com.digit.util.Pair;
import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.stream.IntStream;

public class TreeTraining {
    /**
     * The separator value in between all the buckets
     */
    @VisibleForTesting
    @Getter(AccessLevel.PACKAGE)
    private final int[] bucketSeparators;

    /**
     * countLineFallsBelowJ[i][j] is the number of times line i falls between bucket separator (j-1, j)
     */
    @VisibleForTesting
    @Getter(AccessLevel.PACKAGE)
    private final long[][] countLineFallsBelowJ;

    public TreeTraining(int[] bucketSeparators, int numberOfLines) {
        this.bucketSeparators = bucketSeparators;
        // We need one per line
        this.countLineFallsBelowJ = new long[numberOfLines][bucketSeparators.length];
    }

    public TreeTraining(int[] bucketSeparators) {
        this(bucketSeparators, Config.NUMBER_LINES);
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

    public Pair<BinarySearchTree[], BinarySearchTree> treeResults() {
        BinarySearchTree[] binarySearchTrees = IntStream.range(0, countLineFallsBelowJ.length).parallel()
                .mapToObj(line -> BinarySearchTree.nonZeroBalanced(bucketSeparators, countLineFallsBelowJ[line]))
                .toArray(BinarySearchTree[]::new);
        BinarySearchTree balanced = BinarySearchTree.balanced(bucketSeparators);
        return new Pair<>(binarySearchTrees, balanced);
    }
}
