package com.digit.mergesort;

import com.digit.io.Block;
import com.digit.io.Reader;
import com.digit.io.Writer;
import com.digit.sort.SortStrategy;

public class ExternalMergeSort implements ExternalSortStrategy {

    @Override
    public void sort(Block[] blocks, SortStrategy sortStrategy, Reader reader, Writer writer) {
        for (Block block: blocks) {
            // Read the chunks into memory
            int[] unsortedNumbers = reader.read(block);

            // Sort all the chunks together and write them out to disk
            writer.write(sortStrategy.sort(unsortedNumbers), block);
        }
    }

    public void merge(Block[] blocks, Reader reader, Writer writer) {
        // For every block, read in the chunk

        // Find the smallest of those values, write that value out, grab the next value
    }
}
