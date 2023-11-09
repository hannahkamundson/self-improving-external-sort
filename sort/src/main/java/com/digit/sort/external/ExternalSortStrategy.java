package com.digit.sort.external;

import com.digit.io.Block;
import com.digit.sort.internal.InternalSortStrategy;

import java.io.File;

public interface ExternalSortStrategy {
    void sort(Block[] blocks, InternalSortStrategy internalSortStrategy);

    void merge(Block[] blocks, File outputFile);
}
