package com.digit.command;

import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

/**
 * Generate data for the benchmark
 */
public class GenerateCommand implements Command {
    public static void configureParser(Subparser parser) {
        parser.addArgument("--folder-path", "-f")
                .type(String.class)
                .help("A folder path to write the different files to")
                .required(true);

        parser.addArgument("--seed", "-s")
                .type(Integer.class)
                .help("Random seed")
                .required(false);
    }

    @Override
    public void run(Namespace namespace) {
        // TODO: Decide how many files and how much data should be written out
        // TODO: Randomly draw numbers and write them out for each file
        throw new RuntimeException("GenerateCommand.run isn't implemented yet");
    }
}
