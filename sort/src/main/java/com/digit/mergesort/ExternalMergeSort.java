package com.digit.mergesort;

import com.digit.io.Block;
import com.digit.io.BlockComparator;
import com.digit.io.Writer;
import com.digit.sort.SortStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

public class ExternalMergeSort implements ExternalSortStrategy {

    @Override
    public void sort(Block[] blocks, SortStrategy sortStrategy) {
        for (Block block: blocks) {
            // Read all data into memory
            block.readAll();

            // Sort the data
            block.sort(sortStrategy);

            // Write the data to disk
            block.write();

            // Remove the data from memory
            block.flush();
        }
    }

    @Override
    public void merge(Block[] blocks, File outputFile) {
        Writer.bufferedWriter(outputFile, writer -> {
            try {
                doMerge(blocks, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void doMerge(Block[] blocks, BufferedWriter writer) throws IOException {
        PriorityQueue<Block> queue = new PriorityQueue<>(blocks.length, new BlockComparator());

        // For every block, read in the first chunk and add it to the min heap
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            block.loadNextChunk();
            queue.add(block);
            blocks[i] = null;
        }

        // Get the block with the smallest value
        Block smallestValueBlock;
        while (queue.size() > 0) {
            smallestValueBlock = queue.poll();
            int smallestValue = smallestValueBlock.pop();
            writer.write(String.valueOf(smallestValue));
            writer.newLine();

            // If we have more values, add it back to the queue
            if (smallestValueBlock.hasNext()) {
                queue.add(smallestValueBlock);

            // If there are no more data points in this block, flush it, so we aren't taking up space
            } else {
                smallestValueBlock.flush();
            }
        }

        writer.flush();
    }
}
