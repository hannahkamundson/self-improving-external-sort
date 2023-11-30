package com.digit.sort.internal.ailon;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Node {
    @Getter
    private final int minKey;

    @Getter
    private final int maxKey;
    @Getter
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

    public Node copyWithoutChildren() {
        return new Node(minKey, maxKey, bucketIndex);
    }

    @Override
    public String toString() {
        return String.format("min key = %s | max key = %s | bucket index = %s | smaller = {%s} | larger = {%s}", minKey, maxKey, bucketIndex, smallerChild, largerChild);
    }
}
