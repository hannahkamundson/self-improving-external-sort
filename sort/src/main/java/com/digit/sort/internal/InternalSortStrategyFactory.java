package com.digit.sort.internal;

public class InternalSortStrategyFactory {
    public static InternalSortStrategy create(InternalSortType type) {
        switch(type) {
            case GENERIC:
                return new GenericSort();
            default:
                throw new IllegalArgumentException(String.format("Your type does not exist: %s", type));
        }
    }
}
