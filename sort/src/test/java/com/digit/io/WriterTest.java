package com.digit.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class WriterTest {
    private final static File expectedDirectory = new File("src/test/resources/com/digit/io/writer");

    @Test
    void testWriteAll(@TempDir Path tempDirectory) {
        int[] data = new int[] { 2, 3, 5, 1, 2, 2 };
        Path actualOutput = tempDirectory.resolve("actual_writeall.txt");

        Writer.writeAll(data, actualOutput);

        Path expectedOutput = Paths.get(expectedDirectory.getAbsolutePath(), "expected_writeall.txt");

        assertThat(actualOutput.toFile()).hasSameTextualContentAs(expectedOutput.toFile());
    }
}
