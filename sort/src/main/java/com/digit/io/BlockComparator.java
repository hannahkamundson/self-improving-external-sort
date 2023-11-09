package com.digit.io;

import java.util.Comparator;

public class BlockComparator implements Comparator<Block> {

    @Override
    public int compare(Block o1, Block o2) {
        // If equal, return equal
        // If 01 < 02, return negative
        // If O1 > 02, return positive
        return o1.peek() - o2.peek();
    }
}
