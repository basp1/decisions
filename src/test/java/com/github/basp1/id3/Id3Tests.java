package com.github.basp1.id3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Id3Tests {
    Feature outlook, temp, humidity, wind;
    FeatureSample rainy, overcast, sunny;
    FeatureSample hot, mild, cool;
    FeatureSample low, normal, high;
    FeatureSample windy, calm;

    public Id3Tests() {
        this.outlook = new Feature("Outlook");
        this.sunny = outlook.assume("sunny", 0);
        this.overcast = outlook.assume("overcast", 1);
        this.rainy = outlook.assume("rainy", 2);

        this.temp = new Feature("Temp");
        this.cool = temp.assume("cool", 0);
        this.mild = temp.assume("mild", 1);
        this.hot = temp.assume("hot", 2);

        this.humidity = new Feature("Humidity");
        this.low = humidity.assume("low", 0);
        this.normal = humidity.assume("normal", 1);
        this.high = humidity.assume("high", 2);

        this.wind = new Feature("Wind");
        this.calm = wind.assume("calm", 0);
        this.windy = wind.assume("windy", 1);
    }

    @Test
    public void testId3() {
        Id3 id3 = new Id3(outlook, temp, humidity, wind);

        id3.add(26, rainy, hot, high, calm);
        id3.add(30, rainy, hot, high, windy);
        id3.add(48, overcast, hot, high, calm);
        id3.add(46, sunny, mild, high, calm);
        id3.add(62, sunny, cool, normal, calm);
        id3.add(23, sunny, cool, normal, windy);
        id3.add(43, overcast, cool, normal, windy);
        id3.add(36, rainy, mild, high, calm);
        id3.add(38, rainy, cool, normal, calm);
        id3.add(48, sunny, mild, normal, calm);
        id3.add(48, rainy, mild, normal, windy);
        id3.add(62, overcast, mild, high, windy);
        id3.add(44, overcast, hot, normal, calm);
        id3.add(30, sunny, mild, high, windy);

        Tree tree = id3.buildTree();

        double answer = tree.eval(overcast, mild, high, windy);
        assertEquals(62, answer, 1e-8);

        answer = tree.eval(rainy, cool, normal, windy);
        assertEquals(43, answer, 1e-8);
    }

    @Test
    public void testId3Pruning() {
        Id3 id3 = new Id3(outlook, temp, humidity, wind);

        id3.add(40, rainy, hot, high, calm);
        id3.add(10, rainy, cool, high, calm);
        id3.add(10, rainy, cool, normal, calm);
        id3.add(10, sunny, cool, normal, calm);
        id3.add(10, rainy, cool, normal, windy);
        id3.add(10, overcast, cool, high, windy);
        id3.add(10, overcast, cool, normal, calm);
        id3.add(10, sunny, cool, high, windy);

        Tree tree = id3.buildTree();

        assertEquals(6, tree.getNodes().size());

        double answer = tree.eval(rainy, cool, normal, calm);
        assertEquals(10, answer, 1e-8);

        answer = tree.eval(sunny, hot, high, calm);
        assertEquals(40, answer, 1e-8);
    }

    @Test
    public void testEntropy() {
        List<Integer> list = Arrays.asList(1, 2, 0, 2, 2, 1, 1, 1, 0);
        double entropy = Id3.entropy(list);
        Assert.assertEquals(1.5304930567574824, entropy, 1e-8);

        list = Arrays.asList(1, 2);
        entropy = Id3.entropy(list);
        Assert.assertEquals(1.0, entropy, 1e-8);

        list = Arrays.asList(1, 1, 1);
        entropy = Id3.entropy(list);
        Assert.assertEquals(0.0, entropy, 1e-8);
    }
}