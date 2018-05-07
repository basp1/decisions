package com.github.basp1.decisions;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Id3Tests {
    @Test
    public void testId3() {
        Dataset ds = new Dataset();
        ds.add(26, Data.rainy, Data.hot, Data.high, Data.calm);
        ds.add(30, Data.rainy, Data.hot, Data.high, Data.windy);
        ds.add(48, Data.overcast, Data.hot, Data.high, Data.calm);
        ds.add(46, Data.sunny, Data.mild, Data.high, Data.calm);
        ds.add(62, Data.sunny, Data.cool, Data.normal, Data.calm);
        ds.add(23, Data.sunny, Data.cool, Data.normal, Data.windy);
        ds.add(43, Data.overcast, Data.cool, Data.normal, Data.windy);
        ds.add(36, Data.rainy, Data.mild, Data.high, Data.calm);
        ds.add(38, Data.rainy, Data.cool, Data.normal, Data.calm);
        ds.add(48, Data.sunny, Data.mild, Data.normal, Data.calm);
        ds.add(48, Data.rainy, Data.mild, Data.normal, Data.windy);
        ds.add(62, Data.overcast, Data.mild, Data.high, Data.windy);
        ds.add(44, Data.overcast, Data.hot, Data.normal, Data.calm);
        ds.add(30, Data.sunny, Data.mild, Data.high, Data.windy);

        Id3 id3 = new Id3();
        DecisionTree tree = id3.buildTree(ds);

        List<Integer> answer = (List<Integer>) tree.eval(Data.overcast, Data.mild, Data.high, Data.windy);
        assertEquals(62, (int) answer.get(0), 1e-8);

        answer = (List<Integer>) tree.eval(Data.rainy, Data.cool, Data.normal, Data.windy);
        assertEquals(43, (int) answer.get(0), 1e-8);
    }

    @Test
    public void testId3Pruning() {
        Dataset ds = new Dataset();
        ds.add(40, Data.rainy, Data.hot, Data.high, Data.calm);
        ds.add(10, Data.rainy, Data.cool, Data.high, Data.calm);
        ds.add(10, Data.rainy, Data.cool, Data.normal, Data.calm);
        ds.add(10, Data.sunny, Data.cool, Data.normal, Data.calm);
        ds.add(10, Data.rainy, Data.cool, Data.normal, Data.windy);
        ds.add(10, Data.overcast, Data.cool, Data.high, Data.windy);
        ds.add(10, Data.overcast, Data.cool, Data.normal, Data.calm);
        ds.add(10, Data.sunny, Data.cool, Data.high, Data.windy);

        Id3 id3 = new Id3();
        DecisionTree tree = id3.buildTree(ds);
        assertEquals(6, tree.getNodes().size());

        List<Integer> answer = (List<Integer>) tree.eval(Data.rainy, Data.cool, Data.normal, Data.calm);
        assertEquals(10, (int) answer.get(0), 1e-8);

        answer = (List<Integer>) tree.eval(Data.sunny, Data.hot, Data.high, Data.calm);
        assertEquals(40, (int) answer.get(0), 1e-8);
    }

    @Test
    public void testEntropy() {
        List<Integer> list = Arrays.asList(1, 2, 0, 2, 2, 1, 1, 1, 0);
        double entropy = Id3.entropy(list);
        Assert.assertEquals(1.5304930567574824, entropy, 1e-8);

        list = Arrays.asList(1, 2);
        entropy = Id3.entropy(list);
        Assert.assertEquals(1d, entropy, 1e-8);

        list = Arrays.asList(1, 1, 1);
        entropy = Id3.entropy(list);
        Assert.assertEquals(0d, entropy, 1e-8);
    }
}