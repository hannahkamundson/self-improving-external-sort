package com.digit.io;

import com.digit.util.ByteArithmetic;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * A chunk is a section of a block.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Chunk {
    public static final long BYTES_PER_CHUNK = ByteArithmetic.gbToByte(1.0);
    /**
     * Where should we start in the file?
     */
    private final long offset;
}
