package com.digit;

import com.digit.io.Block;
import com.digit.io.FileBlocks;
import com.digit.io.Reader;
import com.digit.io.Writer;
import com.digit.mergesort.ExternalSortStrategy;
import com.digit.mergesort.ExternalSortStrategyFactory;
import com.digit.mergesort.ExternalSortType;
import com.digit.sort.SortStrategy;
import com.digit.sort.SortStrategyFactory;
import com.digit.sort.SortStrategyType;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;

public class App {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("external-sort").build().defaultHelp(true)
                .description("Self improving external sort benchmark tool");
        parser.addArgument("--file-path", "-f")
                .type(String.class)
                .help("The file path to the data file")
                .required(true);
        parser.addArgument("--external-sort", "-es")
                .type(ExternalSortType.class)
                .help("The external sort strategy you want to use")
                .required(true);
        parser.addArgument("--internal-sort", "-is")
                .type(SortStrategyType.class)
                .help("The internal sort strategy you want to use")
                .required(true);
        Namespace ns;

        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            throw new RuntimeException(e);
        }

        // Get the file and make sure it exists/isn't a directory
        File dataFile = new File(ns.getString("file"));

        if (!dataFile.exists() || dataFile.isDirectory()) {
            throw new IllegalArgumentException(String.format("Your file path did not exist or was a directory: %s", dataFile.getAbsolutePath()));
        }

        // Convert the file into blocks of chunks
        Block[] blocks = FileBlocks.create(dataFile);

        // Get the reader, writer, external sort, and internal sort
        Reader reader = new Reader(dataFile);
        // TODO: actually implement
        Writer writer = null;
        ExternalSortStrategy externalSortStrategy = ExternalSortStrategyFactory.get((ExternalSortType) ns.get("external_sort"));
        SortStrategy sortStrategy = SortStrategyFactory.get((SortStrategyType) ns.get("internal_sort"));

        // Apply the merge sort
        externalSortStrategy.sort(blocks, sortStrategy, reader, writer);
        externalSortStrategy.merge(blocks, reader, writer);
    }
}
