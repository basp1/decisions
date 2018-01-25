package com.github.basp1.id3;

public class FeatureSample {
    private Feature feature;
    private String name;
    private double spanStart;
    private double spanEnd;

    public FeatureSample(Feature feature, String name, double spanStart, double spanEnd) {
        this.feature = feature;
        this.name = name;
        this.spanStart = spanStart;
        this.spanEnd = spanEnd;
    }

    public FeatureSample(FeatureSample other) {
        this.feature = other.getFeature();
        this.name = other.getName();
    }

    public Feature getFeature() {
        return feature;
    }

    public String getName() {
        return name;
    }

    public double[] getSpan() {
        return new double[]{spanStart, spanEnd};
    }

    public double distance(double value) {
        return Math.min(Math.abs(value - spanStart), Math.abs(value - spanEnd));
    }

    @Override
    public String toString() {
        return getName();
    }
}
