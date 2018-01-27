package com.github.basp1.id3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Feature {
    private String name;
    private double metric;
    private Map<String, FeatureSample> samples;

    public Feature(String name) {
        this.name = name;
        this.samples = new HashMap<>();
        this.metric = 0;
    }

    public Feature(Feature other) {
        this.name = other.getName();
    }

    public String getName() {
        return name;
    }

    public FeatureSample assume(String name, Integer value) {
        FeatureSample featureSample = new FeatureSample(this, name, value, value);
        samples.put(name, featureSample);
        return featureSample;
    }

    public FeatureSample assume(String name, double spanStart, double spanEnd) {
        FeatureSample featureSample = new FeatureSample(this, name, spanStart, spanEnd);
        samples.put(name, featureSample);
        return featureSample;
    }

    public Collection<FeatureSample> getFeatureSamples() {
        return samples.values();
    }

    public FeatureSample getFeatureSample(String name) {
        return samples.get(name);
    }

    public double getMetric() {
        return metric;
    }

    public void setMetric(double metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return getName();
    }
}