package com.github.basp1.id3;

public interface Series <T> {
    T get(int index);

    Series<T> range(int from, int to);
}
