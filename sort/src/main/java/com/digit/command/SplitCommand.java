package com.digit.command;

import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Given a single file of integers (a single integer per line), spit the file into appropriate files
 */
public class SplitCommand implements Command {
    public static void configureParser(Subparser parser) {
        parser.addArgument("--original-path", "-f")
                .type(String.class)
                .help("The file path to the original data file")
                .required(true);

        parser.addArgument("--folder-path", "-f")
                .type(String.class)
                .help("A folder path to write the different files to")
                .required(true);
    }

    @Override
    public void run(Namespace namespace) {
        throw new NotImplementedException();
    }
}
