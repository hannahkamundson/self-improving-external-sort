package com.digit.command;

import com.digit.sort.external.ExternalSortType;
import com.digit.sort.internal.InternalSortType;
import net.sourceforge.argparse4j.inf.Namespace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeSortCommandTest {
    @Test
    void end2endTest_genericSort(@TempDir Path tempDir) throws IOException {
        Path end2endPath = new File("src/test/resources/com/digit/command/mergesort/generic").toPath();

        // Setup
        Path tempInput = tempDir.resolve("input");
        tempInput.toFile().mkdir();

        // Copy files over to temp directory
        Files.copy(end2endPath.resolve("input").resolve("block1.txt"), tempInput.resolve("block1.txt"));
        Files.copy(end2endPath.resolve("input").resolve("block2.txt"), tempInput.resolve("block2.txt"));
        Files.copy(end2endPath.resolve("input").resolve("block3.txt"), tempInput.resolve("block3.txt"));

        Path actualPath = tempDir.resolve("actual.txt");

        Command command = new MergeSortCommand();

        Map<String, Object> namespaceMap = new HashMap<>();
        namespaceMap.put("folder_path", tempInput.toAbsolutePath());
        namespaceMap.put("output_file", actualPath.toAbsolutePath());
        namespaceMap.put("external_sort", ExternalSortType.MERGE_SORT);
        namespaceMap.put("internal_sort", InternalSortType.GENERIC);

        Namespace ns = new Namespace(namespaceMap);

        // Run the command
        command.run(ns);

        // Check that the output file is what is expected
        assertThat(actualPath.toFile()).hasSameTextualContentAs(end2endPath.resolve("expected.txt").toFile());
    }
}
