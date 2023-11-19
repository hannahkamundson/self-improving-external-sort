package com.digit.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileBlocks {

    /**
     * For every file in the folder, create a block. Each block should have chunks for the bytes per chunk.
     * @param dataFolder The folder with individual files per block
     * @return An array of all blocks
     */
    public static Block[] create(File dataFolder, int linesPerChunk) {

        try (Stream<Path> paths = Files.walk(dataFolder.toPath())) {
            return paths.filter(path -> !path.toFile().isDirectory())
                    .sorted()
                    .map(path -> {
                        Block.Builder builder = Block.builder();
                        builder.filePath(path);

                        long offset = 0;
                        // Get the size of the data file
                        long lastIndexInFile = 0;
                        try {
                            lastIndexInFile = Files.lines(path).count() - 1;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        builder.addChunk(0);

                        // Split the file into
                        // It is okay if we end up doing lines in file twice because that just means we needed to
                        // read the final line
                        while (offset + linesPerChunk < lastIndexInFile) {
                            offset += linesPerChunk;
                            builder.addChunk(offset);
                        }

                        builder.addChunk(lastIndexInFile);

                        return builder.build();
                    }).toArray(Block[]::new);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
