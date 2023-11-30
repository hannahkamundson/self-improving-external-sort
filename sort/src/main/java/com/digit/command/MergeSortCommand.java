package com.digit.command;

import com.digit.io.Block;
import com.digit.io.FileBlocks;
import com.digit.sort.external.ExternalSortStrategy;
import com.digit.sort.external.ExternalSortStrategyFactory;
import com.digit.sort.external.ExternalSortType;
import com.digit.sort.internal.InternalSortStrategy;
import com.digit.sort.internal.InternalSortStrategyFactory;
import com.digit.sort.internal.InternalSortType;
import com.digit.util.ByteArithmetic;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

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
                .type(InternalSortType.class)
                .help("The internal sort strategy you want to use")
                .required(true);
        parser.addArgument("--max-number", "-mx")
                .type(Integer.class)
                .help("The maximum number allowed in the sample")
                .required(true);
        parser.addArgument("--bucket-samples", "-bs")
                .type(Integer.class)
                .help("How many samples should be used for bucket training?")
                .required(false);
        parser.addArgument("--tree-samples", "-ts")
                .type(Integer.class)
                .help("How many samples should be used for tree training?")
                .required(false);
        parser.addArgument("--size-of-file-gb", "-gb")
                .type(Integer.class)
                .help("How many GBs should each file/block be?")
                .required(true);
    }

    @Override
    public void run(Namespace namespace) throws IOException {
        // Set some of the configs
        Config.MAX_NUMBER = namespace.getInt("max_number");
        Config.NUMBER_LINES = ByteArithmetic.numberOfIntegersThatCanFit(namespace.getInt("size_of_file_gb"));

        // Get the file and make sure it exists/isn't a directory
        File dataDirectory = new File(namespace.getString("folder_path"));

        if (!dataDirectory.exists() || !dataDirectory.isDirectory()) {
            throw new IllegalArgumentException(String.format("Your file path did not exist or was not a directory: %s", dataDirectory.getAbsolutePath()));
        }

        File outputFile = new File(namespace.getString("output_file"));

        int numberOfFiles = (int) Files.walk(dataDirectory.toPath())
                .filter(path -> !path.toFile().isDirectory())
                .count();

        // We expect an entire file to fit into memory so split by number of files to get chunk size
        Config.LINES_PER_CHUNK = Config.NUMBER_LINES / numberOfFiles;

        // Convert the file into blocks of chunks
        Block[] blocks = FileBlocks.create(dataDirectory, Config.LINES_PER_CHUNK);

        // Get the reader, writer, external sort, and internal sort
        ExternalSortStrategy externalSortStrategy = ExternalSortStrategyFactory.create(namespace.get("external_sort"));

        InternalSortType internalSortType = namespace.get("internal_sort");

        // if internalSortType, make sure the training sample numbers are set
        if (internalSortType == InternalSortType.SELF_IMPROVING) {
            Config.BUCKET_SAMPLES = Optional.of(namespace.getInt("bucket_samples"))
                    .orElseThrow(() -> new IllegalArgumentException("You must specify bucket-samples if you are doing the self improving sort"));
            Config.TREE_SAMPLES = Optional.of(namespace.getInt("tree_samples"))
                    .orElseThrow(() -> new IllegalArgumentException("You must specify tree-samples if you are doing the self improving sort"));
        }

        InternalSortStrategy internalSortStrategy = InternalSortStrategyFactory.create(internalSortType);

        // Apply the merge sort
        externalSortStrategy.sort(blocks, internalSortStrategy);
        externalSortStrategy.merge(blocks, outputFile);
    }
}
