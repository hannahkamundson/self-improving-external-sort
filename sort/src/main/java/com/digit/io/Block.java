package com.digit.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.*;

/**
 * A block is a section of a file. It is made up of chunks. If we can fit M chunks into memory, a block holds M-1
 * chunks.
 *
 * This assumes each block is a separate file
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Block {
    /**
     * The file that holds the data
     */
    @NonNull
    private final Path filePath;

    private final Chunk[] chunks;

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private final List<Chunk> chunksToBe = new ArrayList<Chunk>();
        private Path filePath = null;

        /**
         * Add a chunk with the specified offset
         * @param offset Where should we start in the file?
         */
        public void addChunk(long offset) {
            chunksToBe.add(new Chunk(offset));
        }

        public void filePath(Path filePath) {
            this.filePath = filePath;
        }

        public Block build() {
            if (chunksToBe.isEmpty()) {
                throw new IllegalArgumentException("You must have at least one chunk per block.");
            }

            return new Block(filePath, chunksToBe.toArray(new Chunk[0]));
        }
    }
}
