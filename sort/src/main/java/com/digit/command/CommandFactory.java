package com.digit.command;

public class CommandFactory {

    public static Command create(CommandType type) {
        switch(type) {
            case GENERATE:
                return new GenerateCommand();
            case MERGE_SORT:
                return new MergeSortCommand();
            default:
                throw new IllegalArgumentException(String.format("You must specify a command type: %s", type));
        }
    }
}
