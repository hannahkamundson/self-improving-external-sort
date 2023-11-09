package com.digit.io;

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
        return chunkIndex < offsets.length;
    }

    public long minByte() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException();
        }

        return offsets[chunkIndex];
    }

    public long maxByte() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException();
        }

        if (chunkIndex == (offsets.length - 1)) {
            return Long.MAX_VALUE;
        }

        return offsets[chunkIndex + 1] - 1;
    }

    public void increment() {
        chunkIndex++;
    }
}
