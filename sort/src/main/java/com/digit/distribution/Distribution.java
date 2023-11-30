package com.digit.distribution;

import java.io.File;

public interface Distribution {
    /**
     * Generate a file of data given the number of lines
     * @param file The file path to write to
     * @param numberOfLines The number of lines requested in that file
     */
    void generateFile(File file, int numberOfLines);
}
