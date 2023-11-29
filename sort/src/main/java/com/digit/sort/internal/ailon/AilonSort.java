package com.digit.sort.internal.ailon;

import com.digit.sort.internal.InternalSortStrategy;
import com.digit.sort.internal.ailon.bst.BinarySearchTree;

public class AilonSort implements InternalSortStrategy {
    private final BinarySearchTree[] trees;

    public AilonSort(BinarySearchTree[] trees) {
        this.trees = trees;
    }
    @Override
    public void sort(int sampleNumber, int[] unsorted) {

    }
}
