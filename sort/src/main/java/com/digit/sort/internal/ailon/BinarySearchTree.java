package com.digit.sort.internal.ailon;

import com.google.common.annotations.VisibleForTesting;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class BinarySearchTree {
    private Node root;

    public void addNode(Node node) {
        root = BinarySearchTree.addNode(root, node);
    }

    public Node find(int key) {
        return find(root, key);
    }

    public Node find(Node node, int key) {
        // If there isn't a node for it, return null
        if (node == null) {
            return null;
        // If the null is in the key path, return the bucket index
        } else if (node.isKey(key)) {
            return node;
        // If the node is smaller than what the node allows, search on the smaller child
        } else if (key < node.getMinKey()) {
            return find(node.getSmallerChild(), key);
        // Otherwise, if the node >= what the node key allows, search the larger child
        } else {
            return find(node.getLargerChild(), key);
        }
    }

    private static Node addNode(Node node, Node add) {
        if (node == null) {
            node = add;
        } else if (add.getMaxKey() <= node.getMaxKey()) {
            node.setSmallerChild(BinarySearchTree.addNode(node.getSmallerChild(), add));
        } else {
            node.setLargerChild(BinarySearchTree.addNode(node.getLargerChild(), add));
        }

        return node;
    }

    /**
     * Create a binary search tree from the bucket separators
     * @param bucketSeparators The bucket separators. bucketSep[0] should be - inf and bucketSep[length - 1] should be
     *                         inf
     */
    public static BinarySearchTree balanced(int[] bucketSeparators) {
        Node[] nodes = BinarySearchTree.turnBucketSeparatorsToNodeArray(bucketSeparators);
        BinarySearchTree tree = new BinarySearchTree();
        tree.addNode(BinarySearchTree.doCreateBalanced(nodes, 0, nodes.length - 1));
        return tree;
    }

    @VisibleForTesting
    static Node[] turnBucketSeparatorsToNodeArray(int[] bucketSeparators) {
        Node[] nodes = new Node[bucketSeparators.length - 1];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(bucketSeparators[i], bucketSeparators[i + 1], i);
        }

        return nodes;
    }

    @VisibleForTesting
    static Node doCreateBalanced(Node[] nodes, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        Node node = nodes[mid];

        node.setSmallerChild(doCreateBalanced(nodes, start, mid - 1));
        node.setLargerChild(doCreateBalanced(nodes, mid + 1, end));

        return node;
    }

    public static BinarySearchTree nonZeroBalanced(int[] bucketSeparators, long[] frequencies) {
        // Note, bucketSEparators and frequencies are different sizes
        // frequencies[0] is meaningless because nothing can fall below min
        Node[] nodes = BinarySearchTree.turnBucketSeparatorsToNodeArray(bucketSeparators);

        List<Node> nonZeroNodes = new ArrayList<>();

        for (int i = 1; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                nonZeroNodes.add(nodes[i - 1]);
            }
        }

        BinarySearchTree tree = new BinarySearchTree();
        tree.addNode(BinarySearchTree.doCreateBalanced(nonZeroNodes.toArray(Node[]::new), 0, nonZeroNodes.size() - 1));

        return tree;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
