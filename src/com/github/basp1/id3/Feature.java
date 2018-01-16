package com.github.basp1.id3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Feature {
    private String name;
    private double metric;
    private Map<String, FeatureValue> values;

    public Feature(String name) {
        this.name = name;
        this.values = new HashMap<>();
        this.metric = 0;
    }

    public Feature(Feature other) {
        this.name = other.getName();
    }

    public String getName() {
        return name;
    }

    public FeatureValue assume(String name, Integer value) {
        FeatureValue featureValue = new FeatureValue(this, name, value);
        values.put(name, featureValue);
        return featureValue;
    }

    public Collection<FeatureValue> getFeatureValues() {
        return values.values();
    }

    public FeatureValue getFeatureValue(String name) {
        return values.get(name);
    }

    public void setMetric(double metric) {
        this.metric = metric;
    }

    public double getMetric() {
        return metric;
    }

    @Override
    public String toString() {
        return getName();
    }
}
