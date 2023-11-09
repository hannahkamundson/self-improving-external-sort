package com.digit.io;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    private final static int[] expectedCache = new int[]{ 8, 128, 20, 2, 1, 0 };
    private final static int[] firstChunk = new int[]{ 8, 128, 20 };

    @Test
    void testReadAll() {
        Block block = createBlock();
        assertNull(block.getCache());

        block.readAll();

        assertArrayEquals(expectedCache, block.getCache());
    }

    @Test
    void testFlush() {
        Block block = createBlock();
        block.readAll();

        assertArrayEquals(expectedCache, block.getCache());

        block.flush();
        assertNull(block.getCache());
        assertEquals(0, block.getCacheIndex());
    }

    @Test
    void testLoadNextChunk() {
        Block block = createBlock();
        block.loadNextChunk();

        assertArrayEquals(firstChunk, block.getCache());
    }

    @Test
    void testHasNext_happyPath() {
        Block block = createBlock();
        block.loadNextChunk();

        assertEquals(expectedCache[0], block.pop());
        assertTrue(block.hasNext());
        assertEquals(expectedCache[1], block.pop());
        assertTrue(block.hasNext());
        assertEquals(expectedCache[2], block.pop());
        assertTrue(block.hasNext());
        assertEquals(expectedCache[3], block.pop());
        assertTrue(block.hasNext());
        assertEquals(expectedCache[4], block.pop());
        assertTrue(block.hasNext());
        assertEquals(expectedCache[5], block.pop());

        assertFalse(block.cacheHasNext());
        assertFalse(block.hasNext());
    }

    @Test
    void testHasNext_elementInCache() {
        Block block = createBlock();
        block.loadNextChunk();

        assertTrue(block.hasNext());
    }

    @Test
    void testHasNext_needNewChunk() {
        Block block = createBlock();
        block.loadNextChunk();
        assertEquals(firstChunk[0], block.pop());
        assertEquals(firstChunk[1], block.pop());
        assertEquals(firstChunk[2], block.pop());

        assertFalse(block.cacheHasNext());
        assertTrue(block.hasNext());
    }

    @Test
    void testPeek() {
        Block block = createBlock();
        block.loadNextChunk();

        assertEquals(firstChunk[0], block.peek());
        assertEquals(firstChunk[0], block.peek());
        assertEquals(0, block.getCacheIndex());
    }

    private static Block createBlock() {
        File file = new File("src/test/resources/com/digit/io/block/block.txt");

        return Block.builder()
                .filePath(file.toPath())
                .addChunk(0)
                .addChunk(3)
                .addChunk(6)
                .build();
    }
}
