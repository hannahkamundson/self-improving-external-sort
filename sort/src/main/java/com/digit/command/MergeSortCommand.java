package com.digit.command;

import com.digit.io.Block;
import com.digit.io.BlockMath;
import com.digit.io.FileBlocks;
import com.digit.sort.external.ExternalSortStrategy;
import com.digit.sort.external.ExternalSortStrategyFactory;
import com.digit.sort.external.ExternalSortType;
import com.digit.sort.internal.InternalSortStrategy;
import com.digit.sort.internal.SortStrategyFactory;
import com.digit.sort.internal.SortStrategyType;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.File;

/**
 * Run merge-sort on a folder of files where each file can fully fit into memory
 */
public class MergeSortCommand implements Command {
    public static void configureParser(Subparser parser) {
        parser.addArgument("--folder-path", "-f")
                .type(String.class)
                .help("The folder path to the data files")
                .required(true);
        parser.addArgument("--output-file", "-o")
                .type(String.class)
                .help("The output file")
                .required(true);
        parser.addArgument("--external-sort", "-es")
                .type(ExternalSortType.class)
                .help("The external sort strategy you want to use")
                .required(true);
        parser.addArgument("--internal-sort", "-is")
                .type(SortStrategyType.class)
                .help("The internal sort strategy you want to use")
                .required(true);
    }

    @Override
    public void run(Namespace namespace) {
        // Get the file and make sure it exists/isn't a directory
        File dataDirectory = new File(namespace.getString("folder_path"));

        if (!dataDirectory.exists() || !dataDirectory.isDirectory()) {
            throw new IllegalArgumentException(String.format("Your file path did not exist or was not a directory: %s", dataDirectory.getAbsolutePath()));
        }

        File outputFile = new File(namespace.getString("output_file"));

        // Convert the file into blocks of chunks
        Block[] blocks = FileBlocks.create(dataDirectory, BlockMath.LINES_PER_CHUNK);

        // Get the reader, writer, external sort, and internal sort
        ExternalSortStrategy externalSortStrategy = ExternalSortStrategyFactory.create(namespace.get("external_sort"));
        InternalSortStrategy internalSortStrategy = SortStrategyFactory.create(namespace.get("internal_sort"));

        // Apply the merge sort
        externalSortStrategy.sort(blocks, internalSortStrategy);
        externalSortStrategy.merge(blocks, outputFile);
    }
}
