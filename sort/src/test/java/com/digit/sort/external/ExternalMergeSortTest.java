package com.digit.sort.external;

import com.digit.io.Block;
import com.digit.sort.internal.InternalSortStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalMergeSortTest {
    @Test
    void testSort(@TempDir Path actualDir) throws IOException {
        File expectedDirectory = new File("src/test/resources/com/digit/sort/external/external_merge_sort/sort/expected");
        File inputDirectory = new File("src/test/resources/com/digit/sort/external/external_merge_sort/sort/input");

        // Setup
        Path block1File = actualDir.resolve("block1.txt");
        Files.copy(Paths.get(inputDirectory.getPath(), "block1.txt"), block1File);
        Path block2File = actualDir.resolve("block2.txt");
        Files.copy(Paths.get(inputDirectory.getPath(), "block2.txt"), block2File);
        Path block3File = actualDir.resolve("block3.txt");
        Files.copy(Paths.get(inputDirectory.getPath(), "block3.txt"), block3File);

        Block[] blocks = new Block[3];
        blocks[0] =  Block.builder()
                .filePath(block1File)
                .addChunk(0)
                .addChunk(3)
                .addChunk(6)
                .build();

        blocks[1] =  Block.builder()
                .filePath(block2File)
                .addChunk(0)
                .addChunk(3)
                .build();

        blocks[2] =  Block.builder()
                .filePath(block3File)
                .addChunk(0)
                .addChunk(3)
                .addChunk(5)
                .build();

        // Run the command
        ExternalMergeSort mergeSort = new ExternalMergeSort();
        mergeSort.sort(blocks, Arrays::sort);

        // Ensure it was sorted and acme out correctly
        assertThat(block1File.toFile())
                .hasSameTextualContentAs(Paths.get(expectedDirectory.getPath(), "block1.txt").toFile());
        assertThat(block2File.toFile())
                .hasSameTextualContentAs(Paths.get(expectedDirectory.getPath(), "block2.txt").toFile());
        assertThat(block3File.toFile())
                .hasSameTextualContentAs(Paths.get(expectedDirectory.getPath(), "block3.txt").toFile());
    }

    @Test
    void testMerge(@TempDir Path outputDir) {
        Path actualFile = outputDir.resolve("actual.txt");
        File inputDirectory = new File("src/test/resources/com/digit/sort/external/external_merge_sort/merge/input");
        File expected = new File("src/test/resources/com/digit/sort/external/external_merge_sort/merge/expected.txt");

        // Setup
        Block[] blocks = new Block[3];
        blocks[0] =  Block.builder()
                .filePath(Paths.get(inputDirectory.getPath(), "block1.txt"))
                .addChunk(0)
                .addChunk(3)
                .addChunk(5)
                .build();

        blocks[1] =  Block.builder()
                .filePath(Paths.get(inputDirectory.getPath(), "block2.txt"))
                .addChunk(0)
                .addChunk(2)
                .build();

        blocks[2] =  Block.builder()
                .filePath(Paths.get(inputDirectory.getPath(), "block3.txt"))
                .addChunk(0)
                .addChunk(3)
                .addChunk(4)
                .build();

        // Run the command
        ExternalMergeSort mergeSort = new ExternalMergeSort();
        mergeSort.merge(blocks, actualFile.toFile());

        assertThat(actualFile.toFile()).hasSameTextualContentAs(expected);
    }
}
