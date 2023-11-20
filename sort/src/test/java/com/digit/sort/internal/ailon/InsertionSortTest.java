package com.digit.sort.internal.ailon;

import com.digit.sort.internal.GenericSort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InsertionSortTest {
    @Test
    void testSort() {
        int[] input = new int[] {4, 2, 6, 1, 10, 22, 3, 1};
        InsertionSort sort = new InsertionSort();
        sort.sort(1, input);
        assertArrayEquals(new int[] {1, 1, 2, 3, 4, 6, 10, 22}, input);
    }
}
