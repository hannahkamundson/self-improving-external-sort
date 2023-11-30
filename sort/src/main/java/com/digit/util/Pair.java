package com.digit.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pair<K, V> {
    private final K left;
    private final V right;
}
