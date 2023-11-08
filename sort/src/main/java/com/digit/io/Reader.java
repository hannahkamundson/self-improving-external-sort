package com.digit.io;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Reader {

    /**
     * Read an entire block into memory
     * @param block The block to read into memory. This assumes the entire block fits into memory
     * @return The contents of the block (note: the file should be a list of integers)
     */
    public int[] read(Block block) {
        try(Stream<String> lines = Files.lines(block.getFilePath())) {
            return lines.mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}