package com.digit.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChunksTest {
    @Test
    void testMinMax_HappyPath_firstElement() {
        Chunks chunk = makeChunk();
        assertEquals(0, chunk.minByte());
        assertEquals(3, chunk.maxByte());
    }

    @Test
    void testMinMax_HappyPath_middleElement() {
        Chunks chunk = makeChunk();
        chunk.increment();
        assertEquals(4, chunk.minByte());
        assertEquals(4, chunk.maxByte());
    }

    @Test
    void testMinMax_HappyPath_lastElement() {
        Chunks chunk = makeChunk();
        chunk.increment();
        chunk.increment();
        chunk.increment();
        assertEquals(8, chunk.minByte());
        assertEquals(Long.MAX_VALUE, chunk.maxByte());
    }

    private static Chunks makeChunk() {
        return new Chunks(new long[] {0, 4, 5, 8});
    }
}
