package com.digit.command;

import com.digit.distribution.Distribution;
import com.digit.distribution.DistributionFactory;
import com.digit.distribution.DistributionMode;
import com.digit.util.ByteArithmetic;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.File;
import java.nio.file.Paths;

/**
 * Generate data for the benchmark
 */
@Slf4j
public class GenerateCommand implements Command {
    public static void configureParser(Subparser parser) {
        parser.addArgument("--folder-name", "-f")
                .type(String.class)
                .help("A folder path to write the different files to")
                .required(true);

        parser.addArgument("--seed", "-s")
                .type(Integer.class)
                .help("Random seed")
                .setDefault(1234)
                .required(false);

        parser.addArgument("--size-of-file-gb", "-gb")
                .type(Integer.class)
                .help("How many GBs should each file/block be?")
                .required(true);

        parser.addArgument("--max-number", "-mx")
                .type(Integer.class)
                .help("The maximum number allowed in the sample")
                .required(true);

        parser.addArgument("--mode", "-md")
                .type(DistributionMode.class)
                .help("The maximum number allowed in the sample")
                .required(true);

        parser.addArgument("--number-files", "-nf")
                .type(Integer.class)
                .help("How many files do you want to produce?")
                .required(true);
    }

    @Override
    public void run(Namespace namespace) {
        // Set some of the configs
        Config.MAX_NUMBER = namespace.getInt("max_number");
        Config.SEED = namespace.getInt("seed");

        // Calculate the number of lines needed
        Config.NUMBER_LINES = ByteArithmetic.numberOfIntegersThatCanFit(namespace.getInt("size_of_file_gb"));

        // Temporary file location
        File outputFolder = new File(namespace.getString("folder_name"));

        // Get the distribution
        Distribution distribution = DistributionFactory.create(namespace.get("mode"));

        // Create a file for each
        for (int fileNumber = 0; fileNumber < namespace.getInt("number_files"); fileNumber++) {
            File file = Paths.get(outputFolder.getAbsolutePath(), String.format("block-%s.txt", fileNumber)).toFile();
            distribution.generateFile(file, Config.NUMBER_LINES);
        }
    }
}
