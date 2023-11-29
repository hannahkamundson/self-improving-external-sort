package com.digit.sort.internal.ailon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeTest {
    @Test
    void isKey() {
        Node node = new Node(1, 3, 2);
        assertTrue(node.isKey(1));
        assertTrue(node.isKey(2));
        assertFalse(node.isKey(3));
        assertFalse(node.isKey(0));
        assertFalse(node.isKey(4));
    }
}
