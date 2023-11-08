package com.digit.sort;

public class SortStrategyFactory {
    public static SortStrategy create(SortStrategyType type) {
        switch(type) {
            case GENERIC:
                return new GenericSort();
            default:
                throw new IllegalArgumentException(String.format("Your type does not exist: %s", type));
        }
    }
}
