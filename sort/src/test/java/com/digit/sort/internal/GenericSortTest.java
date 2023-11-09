package com.digit.sort.internal;

import com.digit.sort.internal.GenericSort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GenericSortTest {
    @Test
    void testSort() {
        int[] input = new int[] {4, 2, 6, 1, 10, 22, 3, 1};
        GenericSort sort = new GenericSort();
        sort.sort(input);
        assertArrayEquals(new int[] {1, 1, 2, 3, 4, 6, 10, 22}, input);
    }
}
