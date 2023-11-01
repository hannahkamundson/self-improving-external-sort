package com.digit.mergesort;

import com.digit.io.Block;
import com.digit.io.Reader;
import com.digit.io.Writer;
import com.digit.sort.SortStrategy;

public interface ExternalSortStrategy {
    void sort(Block[] blocks, SortStrategy sortStrategy, Reader reader, Writer writer);

    void merge(Block[] blocks, Reader reader, Writer writer);
}
