package com.digit.command;

import net.sourceforge.argparse4j.inf.Namespace;

public interface Command {
    /**
     * Run the command
     * @param namespace Namespace info when the command line arguments are parsed
     */
    void run(Namespace namespace);
}
