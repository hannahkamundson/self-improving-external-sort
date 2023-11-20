package com.digit.sort.internal;

public class InternalSortStrategyFactory {
    public static InternalSortStrategy create(InternalSortType type) {
        switch(type) {
            case GENERIC:
                return new GenericSort();
            case SELF_IMPROVING:
                return new SelfImprovingSort();
            case INSERTION_SORT:
                return new InsertionSort();
            default:
                throw new IllegalArgumentException(String.format("Your type does not exist: %s", type));
        }
    }
}
