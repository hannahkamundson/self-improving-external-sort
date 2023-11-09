package com.digit.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileBlocksTest {

    @Test
    void create_happyPath() {
        File directory = new File("src/test/resources/com/digit/io/file_blocks");
        Block[] actual = FileBlocks.create(directory, 3);
        Block[] expected = new Block[3];
        expected[0] = Block.builder()
                .addChunk(0)
                .addChunk(3)
                .addChunk(5)
                .filePath(Paths.get(directory.getPath(), "block1.txt"))
                .build();
        expected[1] = Block.builder()
                .addChunk(0)
                .addChunk(2)
                .filePath(Paths.get(directory.getPath(), "block2.txt"))
                .build();
        expected[2] = Block.builder()
                .addChunk(0)
                .addChunk(3)
                .addChunk(4)
                .filePath(Paths.get(directory.getPath(), "block3.txt"))
                .build();
        assertArrayEquals(expected, actual);
    }

    @Test
    void create_hasCorrectData() {
        File directory = new File("src/test/resources/com/digit/io/file_blocks");
        Block[] blocks = FileBlocks.create(directory, 3);

        for (Block block: blocks) {
            block.loadNextChunk();
        }

        assertEquals(8, blocks[0].pop());
        assertEquals(128, blocks[0].pop());
        assertEquals(20, blocks[0].pop());
        assertEquals(2, blocks[0].pop());
        assertEquals(1, blocks[0].pop());
        assertTrue(blocks[0].hasNext());
        assertEquals(0, blocks[0].pop());
        assertFalse(blocks[0].hasNext());

        assertEquals(9, blocks[1].pop());
        assertEquals(3, blocks[1].pop());
        assertTrue(blocks[1].hasNext());
        assertEquals(4, blocks[1].pop());
        assertFalse(blocks[1].hasNext());

        assertEquals(1, blocks[2].pop());
        assertEquals(4, blocks[2].pop());
        assertEquals(3, blocks[2].pop());
        assertEquals(2, blocks[2].pop());
        assertTrue(blocks[2].hasNext());
        assertEquals(32, blocks[2].pop());
        assertFalse(blocks[2].hasNext());
    }
}
