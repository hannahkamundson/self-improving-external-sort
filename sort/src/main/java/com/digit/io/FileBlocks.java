package com.digit.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileBlocks {
    /**
     * This means we need to have 8 chunks in memory at a time => 8 GB at a time
     * TODO: This won't actually work. We really need to base it off of the file size
     */
    public static final int CHUNKS_PER_BLOCK = 8;

    public static Block[] create(File file) {
        List<Block> blocks = new ArrayList<>();
        long offset = 0;
        long fileSize = file.length();

        // Add blocks with chunks until the entire file is full
        while (offset < fileSize) {
            // Add a new chunk to the block
            int chunk = 0;
            Block.Builder builder = Block.builder();

            while (chunk < CHUNKS_PER_BLOCK) {
                builder.add(offset);
                offset = offset + Chunk.BYTES_PER_CHUNK;
                chunk++;
            }

            blocks.add(builder.build());
        }

        return blocks.toArray(new Block[0]);
    }
}
