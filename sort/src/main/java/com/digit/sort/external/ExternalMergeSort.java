package com.digit.sort.external;

import com.digit.io.Block;
import com.digit.io.BlockComparator;
import com.digit.io.Writer;
import com.digit.sort.internal.InternalSortStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

public class ExternalMergeSort implements ExternalSortStrategy {

    @Override
    public void sort(Block[] blocks, InternalSortStrategy internalSortStrategy) {
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            // Read all data into memory
            block.readAll();

            // Sort the data
            block.sort(i, internalSortStrategy);

            // Write the data to disk
            block.write();

            // Remove the data from memory
            block.flush();

            // Allow the internal sort strategy to update its own learning if needed
            // For example, allow the Ailon sort strategy to configure based on the training
            // phase. This is so this can occur without the entire block being in memory
            // Note: This is occurring after we've already flushed the data out of the block so we don't need
            // to consider as much in memory stuff
            internalSortStrategy.learn(i);
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
        for (Block block : blocks) {
            block.loadNextChunk();
            queue.add(block);
        }

        // Get the block with the smallest value
        Block smallestValueBlock;
        while (!queue.isEmpty()) {
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
