package com.digit.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A block is a section of a file. It is made up of chunks. If we can fit M chunks into memory, a block holds M-1
 * chunks.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Block {
    private final Chunk[] chunks;

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private final List<Chunk> chunksToBe = new ArrayList<Chunk>();

        /**
         * Add a chunk with the specified offset
         * @param offset Where should we start in the file?
         */
        public void add(long offset) {
            chunksToBe.add(new Chunk(offset));
        }

        public Block build() {
            if (chunksToBe.isEmpty()) {
                throw new IllegalArgumentException("You must have at least one chunk per block.");
            }

            return new Block(chunksToBe.toArray(new Chunk[0]));
        }
    }
}
