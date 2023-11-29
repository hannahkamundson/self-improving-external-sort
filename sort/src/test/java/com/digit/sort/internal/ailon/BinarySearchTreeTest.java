package com.digit.sort.internal.ailon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinarySearchTreeTest {

    @Test
    void turnBucketSeparatorsToNodeArray() {
        int[] input = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, 14, Integer.MAX_VALUE };
        Node n1 = new Node(Integer.MIN_VALUE, 2, 0);
        Node n2 = new Node(2, 3, 1);
        Node n3 = new Node(3, 7, 2);
        Node n4 = new Node(7, 9, 3);
        Node n5 = new Node(9, 14, 4);
        Node n6 = new Node(14, Integer.MAX_VALUE, 5);

        Node[] actual = BinarySearchTree.turnBucketSeparatorsToNodeArray(input);
        Node[] expected = new Node[] { n1, n2, n3, n4, n5, n6 };

        assertArrayEquals(expected, actual);
    }

    @Test
    void doCreateBalanced() {
        Node n1 = new Node(Integer.MIN_VALUE, 2, 0);
        Node n2 = new Node(2, 3, 1);
        Node n3 = new Node(3, 7, 2);
        Node n4 = new Node(7, 9, 3);
        Node n5 = new Node(9, 14, 4);
        Node n6 = new Node(14, Integer.MAX_VALUE, 5);

        Node[] input = new Node[] { n1, n2, n3, n4, n5, n6};

        Node actual = BinarySearchTree.doCreateBalanced(input, 0, input.length - 1);

        n1.setLargerChild(n2);
        n5.setSmallerChild(n4);
        n5.setLargerChild(n6);
        n3.setSmallerChild(n1);
        n3.setLargerChild(n5);

        assertEquals(n3, actual);
    }

    @Test
    void balanced() {
        int[] input = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, 14, Integer.MAX_VALUE };
        BinarySearchTree actual = BinarySearchTree.balanced(input);

        // Create expected
        Node n1 = new Node(Integer.MIN_VALUE, 2, 0);
        Node n2 = new Node(2, 3, 1);
        Node n3 = new Node(3, 7, 2);
        Node n4 = new Node(7, 9, 3);
        Node n5 = new Node(9, 14, 4);
        Node n6 = new Node(14, Integer.MAX_VALUE, 5);
        n1.setLargerChild(n2);
        n5.setSmallerChild(n4);
        n5.setLargerChild(n6);
        n3.setSmallerChild(n1);
        n3.setLargerChild(n5);

        BinarySearchTree expected = new BinarySearchTree();
        expected.addNode(n3);

        assertEquals(expected, actual);
    }

    @Test
    void nonZeroBalanced() {
        int[] input = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, 14, Integer.MAX_VALUE };
        long[] frequencies = new long[] { 0, 2, 0, 7, 0, 14, 3 };
        BinarySearchTree actual = BinarySearchTree.nonZeroBalanced(input, frequencies);

        // Create expected
        Node n1 = new Node(Integer.MIN_VALUE, 2, 0);
        Node n3 = new Node(3, 7, 2);
        Node n5 = new Node(9, 14, 4);
        Node n6 = new Node(14, Integer.MAX_VALUE, 5);
        n5.setLargerChild(n6);
        n3.setSmallerChild(n1);
        n3.setLargerChild(n5);

        BinarySearchTree expected = new BinarySearchTree();
        expected.addNode(n3);

        assertEquals(expected, actual);
    }

    @Test
    void addNode() {
        BinarySearchTree actual = new BinarySearchTree();
        Node n4 = new Node(3, 7, 2);
        Node n17 = new Node(9, Integer.MAX_VALUE, 4);

        actual.addNode(n4.copyWithoutChildren());
        actual.addNode(n17.copyWithoutChildren());

        BinarySearchTree expected = new BinarySearchTree();
        n4.setLargerChild(n17);
        expected.addNode(n4);


        assertEquals(expected, actual);
    }
}
