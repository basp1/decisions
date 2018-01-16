package com.github.basp1.id3;

public class FeatureValue implements Comparable<FeatureValue> {
    private Feature feature;
    private String name;
    private Integer value;

    public FeatureValue(Feature feature, String name, Integer value) {
        this.feature = feature;
        this.name = name;
        this.value = value;
    }

    public FeatureValue(FeatureValue other) {
        this.feature = other.getFeature();
        this.name = other.getName();
    }

    public Feature getFeature() {
        return feature;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int compareTo(FeatureValue other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public String toString() {
        return getName();
    }
}
