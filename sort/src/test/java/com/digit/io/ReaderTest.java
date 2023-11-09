package com.digit.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ReaderTest {
    private final static Path testPath = new File("src/test/resources/com/digit/io/reader/numbers.txt").toPath();

    @Test
    void testReadAll_happyPath() {
        assertArrayEquals(new int[]{4, 3, 2, 5, 10}, Reader.readAll(testPath));
    }

    @Test
    void testRead_happyPath_firstElement() {
        assertArrayEquals(new int[]{4, 3}, Reader.read(testPath, 0, 1));
    }

    @Test
    void testRead_happyPath_middleSame() {
        assertArrayEquals(new int[]{ 3 }, Reader.read(testPath, 1, 1));
    }

    @Test
    void testRead_happyPath_allRemaining() {
        assertArrayEquals(new int[]{2, 5, 10}, Reader.read(testPath, 2, Long.MAX_VALUE));
    }
}
