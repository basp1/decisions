package com.github.basp1.id3;

import java.util.*;

public class Sample {
    final double value;
    final Map<Integer, FeatureValue> featureValues;

    public Sample(double value, FeatureValue... featureValues) {
        this.value = value;
        this.featureValues = new HashMap<>();
        for (FeatureValue featureValue : featureValues) {
            this.featureValues.put(featureValue.getFeature().hashCode(), featureValue);
        }
    }

    public Collection<FeatureValue> getFeatureValues() {
        return featureValues.values();
    }

    public FeatureValue getFeatureValue(Feature feature) {
        return featureValues.get(feature.hashCode());
    }

    public double getValue() {
        return value;
    }
}