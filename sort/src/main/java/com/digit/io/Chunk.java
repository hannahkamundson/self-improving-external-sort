package com.digit.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A chunk is a section of a block.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Chunk {
    /**
     * Where should we start in the file?
     */
    private final long offset;
}
