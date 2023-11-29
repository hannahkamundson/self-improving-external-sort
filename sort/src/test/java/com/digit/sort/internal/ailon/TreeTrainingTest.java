package com.digit.sort.internal.ailon;

import com.digit.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTrainingTest {
    @Test
    void testCountingCorrectly_withinBounds() {
        int[] bucketSeparators = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, Integer.MAX_VALUE };
        int numberOfLines = bucketSeparators.length - 1;

        TreeTraining trainer = new TreeTraining(bucketSeparators, numberOfLines);

        trainer.trainUnsorted(new int[] { 4, 4, 4, 4, 4 });

        long[] expectedCountLineFallsBelowJ = new long[] { 0, 0, 0, 1, 0, 0 };

        for (long[] countsForLineI: trainer.getCountLineFallsBelowJ()) {
            assertArrayEquals(expectedCountLineFallsBelowJ, countsForLineI);
        }
    }

    @Test
    void testCountingCorrectly_onBounds() {
        int[] bucketSeparators = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, Integer.MAX_VALUE };
        int numberOfLines = bucketSeparators.length - 1;

        TreeTraining trainer = new TreeTraining(bucketSeparators, numberOfLines);

        trainer.trainUnsorted(new int[] { 7, 7, 7, 7, 7 });

        long[] expectedCountLineFallsBelowJ = new long[] { 0, 0, 0, 1, 0, 0 };

        for (long[] countsForLineI: trainer.getCountLineFallsBelowJ()) {
            assertArrayEquals(expectedCountLineFallsBelowJ, countsForLineI);
        }
    }

    @Test
    void testCountingCorrectly_aboveMax() {
        int[] bucketSeparators = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, Integer.MAX_VALUE };
        int numberOfLines = bucketSeparators.length - 1;

        TreeTraining trainer = new TreeTraining(bucketSeparators, numberOfLines);

        trainer.trainUnsorted(new int[] { 17, 17, 17, 17, 17 });

        long[] expectedCountLineFallsBelowJ = new long[] { 0, 0, 0, 0, 0, 1 };

        for (long[] countsForLineI: trainer.getCountLineFallsBelowJ()) {
            assertArrayEquals(expectedCountLineFallsBelowJ, countsForLineI);
        }
    }

    @Test
    void testFullTraining() {
        int[] bucketSeparators = new int[] { Integer.MIN_VALUE, 2, 3, 7, 9, Integer.MAX_VALUE };
        int numberOfLines = bucketSeparators.length - 1;

        TreeTraining trainer = new TreeTraining(bucketSeparators, numberOfLines);

        trainer.trainUnsorted(new int[] { 4, 4, 4, 4, 4 });
        trainer.trainUnsorted(new int[] { 17, 1, 0, 1, 17 });
        trainer.trainUnsorted(new int[] { 17, 1, 0, 1, 17 });

        Pair<BinarySearchTree[], BinarySearchTree> actual = trainer.treeResults();
        BinarySearchTree[] actualTrees = actual.getLeft();
        BinarySearchTree actualBalanced = actual.getRight();

        // Assert the balanced are the same
        BinarySearchTree expectedBalanced = BinarySearchTree.balanced(bucketSeparators);

        assertEquals(expectedBalanced, actualBalanced);

        // Assert the non balanced are the same
        BinarySearchTree[] expectedTrees = new BinarySearchTree[5];
        Node n4 = new Node(3, 7, 2);
        Node n17 = new Node(9, Integer.MAX_VALUE, 4);
        Node n0Or1 = new Node(Integer.MIN_VALUE, 2, 0);

        BinarySearchTree bst4And17 = new BinarySearchTree();
        bst4And17.addNode(n4.copyWithoutChildren());
        bst4And17.addNode(n17.copyWithoutChildren());

        BinarySearchTree bst4And1Or0 = new BinarySearchTree();
        bst4And1Or0.addNode(n0Or1.copyWithoutChildren());
        bst4And1Or0.addNode(n4.copyWithoutChildren());

        assertEquals(5, actualTrees.length);
        assertEquals(bst4And17, actualTrees[0]);
        assertEquals(bst4And1Or0, actualTrees[1]);
        assertEquals(bst4And1Or0, actualTrees[2]);
        assertEquals(bst4And1Or0, actualTrees[3]);
        assertEquals(bst4And17, actualTrees[4]);
    }
}
