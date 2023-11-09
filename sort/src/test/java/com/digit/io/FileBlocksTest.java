package com.digit.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FileBlocksTest {

    @Test
    void create_happyPath() {
        File directory = new File("src/test/resources/com/digit/io/file_blocks");
        Block[] actual = FileBlocks.create(directory, 3);
        Block[] expected = new Block[3];
        expected[0] = Block.builder()
                .addChunk(0)
                .addChunk(3)
                .addChunk(6)
                .filePath(Paths.get(directory.getPath(), "block1.txt"))
                .build();
        expected[1] = Block.builder()
                .addChunk(0)
                .addChunk(3)
                .filePath(Paths.get(directory.getPath(), "block2.txt"))
                .build();
        expected[2] = Block.builder()
                .addChunk(0)
                .addChunk(3)
                .filePath(Paths.get(directory.getPath(), "block3.txt"))
                .build();
        assertArrayEquals(expected, actual);
    }
}
