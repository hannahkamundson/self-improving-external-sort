package com.digit.io;

import com.digit.util.ByteArithmetic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Writer {

    /**
     * Write a sorted list of ints to file
     * @param sorted The sorted array to write out
     * @param block The block to write the array to
     */
    public void write(int[] sorted, Block block) {
        try (FileOutputStream out = new FileOutputStream(block.getFilePath().toFile());
             FileChannel channel = out.getChannel()) {
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, (long) ByteArithmetic.BYTES_PER_INT * sorted.length);
            for (int i : sorted) {
                buf.putInt(i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
