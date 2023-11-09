package com.digit.io;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Chunks {
    private final long[] offsets;

    private int chunkIndex = 0;

    public Chunks(long[] offsets) {
        this.offsets = offsets;

        if (offsets[0] != 0) {
            throw new IllegalArgumentException("The first offset must be 0 in the chunks");
        }
    }

    public boolean hasNext() {
        return chunkIndex < offsets.length - 1;
    }

    /**
     * What line should we start at for this chunk?
     */
    public long minLine() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException();
        }

        return offsets[chunkIndex];
    }

    /**
     * How many lines should we read? (including the original line)
     */
    public long maxLine() {
        // If it is the last element, just include the final element
        if ((chunkIndex + 2) == offsets.length) {
            return offsets[chunkIndex + 1];
        }

        // Otherwise, only go up to the last value
        return offsets[chunkIndex + 1] - 1;
    }

    public void increment() {
        chunkIndex++;
    }
}
