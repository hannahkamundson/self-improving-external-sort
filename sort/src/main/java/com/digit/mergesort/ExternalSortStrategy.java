package com.digit.mergesort;

import com.digit.io.Block;
import com.digit.sort.SortStrategy;

import java.io.File;

public interface ExternalSortStrategy {
    void sort(Block[] blocks, SortStrategy sortStrategy);

    void merge(Block[] blocks, File outputFile);
}
