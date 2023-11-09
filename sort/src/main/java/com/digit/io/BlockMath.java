package com.digit.io;

import com.digit.util.ByteArithmetic;

public class BlockMath {
    public static final int LINES_PER_CHUNK = ByteArithmetic.gbToByte(1.0);

    /**
     * This means we need to have 8 chunks in memory at a time => 8 GB at a time
     * TODO: This won't actually work. We really need to base it off of the file size
     */
    public static final int CHUNKS_PER_BLOCK = 8;

    public static final int BYTES_PER_BLOCK = BlockMath.LINES_PER_CHUNK * BlockMath.CHUNKS_PER_BLOCK;
}
