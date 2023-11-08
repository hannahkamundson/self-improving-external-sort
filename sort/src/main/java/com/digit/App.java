package com.digit;

import com.digit.command.*;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;

public class App {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("external-sort").build().defaultHelp(true)
                .description("Self improving external sort benchmark tool");

        Subparsers subparsers = parser.addSubparsers();
        MergeSortCommand.configureParser(subparsers.addParser(CommandType.MERGE_SORT.toString()));
        SplitCommand.configureParser(subparsers.addParser(CommandType.SPLIT.toString()));

        Namespace namespace;

        try {
            namespace = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            throw new RuntimeException(e);
        }

        Command command = CommandFactory.create(CommandType.valueOf(args[0]));
        command.run(namespace);
    }
}
