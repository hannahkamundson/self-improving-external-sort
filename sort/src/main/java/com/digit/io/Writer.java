package com.digit.io;

import com.digit.util.ByteArithmetic;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.function.Consumer;

public class Writer {

    /**
     * Write a sorted list of ints to file
     * @param sorted The sorted array to write out
     * @param filePath The file path to write the array to
     */
    public static void writeAll(int[] sorted, Path filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath.toFile());
             FileChannel channel = out.getChannel()) {
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, (long) ByteArithmetic.BYTES_PER_INT * sorted.length);
            for (int i : sorted) {
                buf.putInt(i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
