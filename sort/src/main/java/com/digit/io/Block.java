package com.digit.io;

import com.digit.sort.internal.InternalSortStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A block is a section of a file. It is made up of chunks. If we can fit M chunks into memory, a block holds M-1
 * chunks.
 * This assumes each block is a separate file
 */
@EqualsAndHashCode
public class Block {
    /**
     * The file that holds the data
     */
    @NonNull
    private final Path filePath;

    private final Chunks chunks;

    @Getter
    private int[] cache;

    @Getter
    private int cacheIndex = 0;

    private Block(Path filePath, Chunks chunks) {
        this.filePath = filePath;
        this.chunks = chunks;
    }

    /**
     * Read all chunks of data into memory
     */
    public void readAll() {
        cache = Reader.readAll(filePath);
    }

    /**
     * Sort the data according to the strategy
     * @param internalSortStrategy How should we sort data?
     */
    public void sort(int sampleNumber, InternalSortStrategy internalSortStrategy) {
        internalSortStrategy.sort(sampleNumber, cache);
    }

    /**
     * Write all data to the original file
     */
    // Visible for testing
    void write(Path path) {
        Writer.writeAll(cache, path);
    }

    public void write() {
        write(filePath);
    }

    /**
     * Remove all data from memory
     */
    public void flush() {
        cache = null;
        cacheIndex = 0;
    }

    // Visible for testing
    boolean cacheHasNext() {
        return cacheIndex < cache.length;
    }

    public boolean hasNext() {
        // It has more data if there is still an element in the cache or another chunk
        return cacheHasNext() || chunks.hasNext();
    }

    public void loadNextChunk() {
        if (!chunks.hasNext()) {
            throw new IndexOutOfBoundsException();
        }

        // Read the data into cache
        cache = Reader.read(filePath, chunks.minLine(), chunks.maxLine());
        cacheIndex = 0;
        chunks.increment();
    }

    /**
     * Peek at the next value
     */
    public int peek() {
        // If the cache needs to be loaded, load it
        if (!cacheHasNext() && hasNext())
            loadNextChunk();
        // If we can't do it, return the max value since we are sorting smallest to largest
        else if (!hasNext()) {
            return Integer.MAX_VALUE;
        }

        // Grab from the cache
        return cache[cacheIndex];
    }

    public int pop() {
        int value = peek();
        cacheIndex++;

        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<Long> chunksToBe = new ArrayList<>();
        private Path filePath = null;

        /**
         * Add a chunk with the specified offset
         * @param offset Where should we start in the file?
         */
        public Builder addChunk(long offset) {
            chunksToBe.add(offset);
            return this;
        }

        public Builder filePath(Path filePath) {
            this.filePath = filePath;
            return this;
        }

        public Block build() {
            if (chunksToBe.isEmpty()) {
                throw new IllegalArgumentException("You must have at least one chunk per block.");
            }

            return new Block(filePath, new Chunks(chunksToBe.stream().mapToLong(v -> v).toArray()));
        }
    }
}
