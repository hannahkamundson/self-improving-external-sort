package com.digit.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class Writer {

    /**
     * Write a sorted list of ints to file
     * @param sorted The sorted array to write out
     * @param filePath The file path to write the array to
     */
    public static void writeAll(int[] sorted, Path filePath) {
        Writer.bufferedWriter(filePath.toFile(), writer -> {
            for (int i: sorted) {
                try {
                    writer.write(String.valueOf(i));
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Use a buffered writer to write to file
     * @param outputFile The output file
     * @param writeFn What you want to write
     */
    public static void bufferedWriter(File outputFile, Consumer<BufferedWriter> writeFn) {
        try (FileWriter fileWriter = new FileWriter(outputFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            writeFn.accept(bufferedWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
