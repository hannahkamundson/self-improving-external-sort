package com.digit.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChunksTest {
    @Test
    void testMinMax_HappyPath_firstElement() {
        Chunks chunk = makeChunk();
        assertEquals(0, chunk.minLine());
        assertEquals(3, chunk.maxLine());
    }

    @Test
    void testMinMax_HappyPath_middleElement() {
        Chunks chunk = makeChunk();
        chunk.increment();
        assertEquals(4, chunk.minLine());
        assertEquals(4, chunk.maxLine());
    }

    private static Chunks makeChunk() {
        return new Chunks(new long[] {0, 4, 5, 8});
    }
}
