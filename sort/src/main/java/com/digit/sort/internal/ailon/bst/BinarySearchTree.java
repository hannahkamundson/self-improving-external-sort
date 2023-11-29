package com.digit.sort.internal.ailon.bst;

import lombok.Getter;
import lombok.Setter;

public class BinarySearchTree {
    private Node root;

    public void addNode(Node node) {
        root = BinarySearchTree.addNode(root, node);
    }

    private static Node addNode(Node node, Node add) {
        if (node == null) {
            node = add;
        } else if (add.getMaxKey() <= node.getMaxKey()) {
            BinarySearchTree.addNode(node.getSmallerChild(), add);
        } else {
            BinarySearchTree.addNode(node.getLargerChild(), add);
        }

        return node;
    }

    public static class Node {
        private final int minKey;

        @Getter
        private final int maxKey;
        private final int bucketIndex;

        @Setter
        @Getter
        private Node smallerChild;

        @Setter
        @Getter
        private Node largerChild;

        public Node(int minKey, int maxKey, int bucketIndex) {
            this.minKey = minKey;
            this.maxKey = maxKey;
            this.bucketIndex = bucketIndex;
        }

        public boolean isKey(int value) {
            return value >= minKey && value < maxKey;
        }
    }
}
