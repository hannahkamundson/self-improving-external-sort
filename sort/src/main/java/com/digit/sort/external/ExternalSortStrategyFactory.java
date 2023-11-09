package com.digit.sort.external;

public class ExternalSortStrategyFactory {
    public static ExternalSortStrategy create(ExternalSortType type) {
        switch(type) {
            case MERGE_SORT:
                return new ExternalMergeSort();
            default:
                throw new IllegalArgumentException("You must specify the external sort type");
        }
    }
}
