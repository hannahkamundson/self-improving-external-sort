package com.digit.sort.internal.ailon;

import com.digit.command.Config;
import com.digit.sort.internal.InternalSortStrategy;
import com.digit.util.Pair;

import java.util.Arrays;
import java.util.stream.IntStream;

public class AilonSort implements InternalSortStrategy {
    private final BinarySearchTree[] trees;

    private final BinarySearchTree balanced;

    public AilonSort(BinarySearchTree[] trees, BinarySearchTree balanced) {
        this.trees = trees;
        this.balanced = balanced;
    }

    public AilonSort(Pair<BinarySearchTree[], BinarySearchTree> pair) {
        this(pair.getLeft(), pair.getRight());
    }

    @Override
    public void sort(int sampleNumber, int[] unsorted) {
        Bucket[] buckets = new Bucket[trees.length];
        Arrays.fill(buckets, new Bucket());

        IntStream.range(0, trees.length).parallel().forEach(i -> {
            int value = unsorted[i];
            BinarySearchTree tree = trees[i];
            Node bucketIndex = tree.find(value);
            // If we have an index saved in the binary tree, add the value
            if (bucketIndex != null) {
                buckets[bucketIndex.getBucketIndex()].add(value);
            // Otherwise, we are seeing a value for the first time
            // Use the balanced binary tree to find it and possibly retrain the tree
            } else {
                Node index = balanced.find(value);
                buckets[index.getBucketIndex()].add(value);
                // If we're allowed to retrain the tree, do it
                if (Config.ALLOW_TREE_RETRAIN) {
                    // Don't worry about making this optimal since it has only happened once
                    // We are assuming this has a low probability of being called so it is okay that
                    // it is added to the bottom
                    trees[i].addNode(index.copyWithoutChildren());
                }
            }

        });

        int arrayIndex = 0;
        int bucketIndex = 0;

        while (arrayIndex < unsorted.length) {
            for (int val : buckets[bucketIndex].getValues()) {
                unsorted[arrayIndex] = val;
                arrayIndex++;
            }
            bucketIndex++;
        }
    }
}
