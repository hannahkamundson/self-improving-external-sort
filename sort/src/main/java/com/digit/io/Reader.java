package com.digit.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Reader {

    /**
     * Read an entire file path into
     * @param filePath The location of the path
     * @return The contents of the file (note: the file should be a list of integers)
     */
    public static int[] readAll(Path filePath) {
        return Reader.read(filePath, -1, -1);
    }

    public static int[] read(Path filePath, long beginning, long length) {
        try(Stream<String> lines = Files.lines(filePath)) {
            Stream<String> specificLines;
            if (beginning >= 0 && length >= 0) {
                specificLines = lines.skip(beginning).limit(length);
            } else {
                specificLines = lines;
            }

            return specificLines
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}