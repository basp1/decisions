package com.github.basp1.id3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Sample {
    final double value;
    final Map<Integer, FeatureSample> featureSamples;

    public Sample(double value, FeatureSample... featureSamples) {
        this.value = value;
        this.featureSamples = new HashMap<>();
        for (FeatureSample featureSample : featureSamples) {
            this.featureSamples.put(featureSample.getFeature().hashCode(), featureSample);
        }
    }

    public Collection<FeatureSample> getFeatureSamples() {
        return featureSamples.values();
    }

    public FeatureSample getFeatureSample(Feature feature) {
        return featureSamples.get(feature.hashCode());
    }

    public double getValue() {
        return value;
    }
}