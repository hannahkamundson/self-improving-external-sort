package com.digit.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@AllArgsConstructor
public class Reader {
    private final File file;

    /**
     * Read an entire block into memory
     * @param block The block to read into memory. This assumes the entire block fits into memory
     * @return The contents of the block (note: the file should be a list of integers)
     */
    public int[] read(Block block) {
        try(FileInputStream inputStream = new FileInputStream(file)) {
            // TODO read all chunks

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

//try {
//        File file = new File("myFile");
//        FileInputStream is = new FileInputStream(file);
//        byte[] chunk = new byte[1024];
//        int chunkLen = 0;
//        while ((chunkLen = is.read(chunk)) != -1) {
//        // your code..
//        }
//        } catch (FileNotFoundException fnfE) {
//        // file not found, handle case
//        } catch (IOException ioE) {
//        // problem reading, handle case
//        }