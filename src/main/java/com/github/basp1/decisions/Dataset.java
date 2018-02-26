package com.github.basp1.decisions;

import java.util.*;
import java.util.stream.Stream;

public class Dataset {
    List<Sample> samples;
    Set<Feature> features;

    public Dataset() {
        this.samples = new ArrayList<>();
        this.features = new HashSet<>();
    }

    public void add(Object value, FeatureSample... featureSamples) {
        add(new Sample(value, featureSamples));
    }

    public Collection<Sample> getSamples() {
        return samples;
    }

    public Stream<Sample> stream() {
        return samples.stream();
    }

    public Sample get(int index) {
        return samples.get(index);
    }

    public void add(Sample sample) {
        samples.add(sample);
        for (FeatureSample featureSample : sample.getFeatureSamples()) {
            features.add(featureSample.getFeature());
        }
    }

    public void addAll(Dataset dataset) {
        addAll(dataset.getSamples());
    }

    public void addAll(Collection<Sample> samples) {
        for (Sample sample : samples) {
            add(sample);
        }
    }

    public int size() {
        return samples.size();
    }

    public Sample first() {
        return get(0);
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Dataset: {\n");
        for (Sample sample : samples) {
            sb.append("  \"");
            sb.append(sample.getValue());
            sb.append("\" : [");
            for (FeatureSample featureSample : sample.getFeatureSamples()) {
                sb.append("\"");
                sb.append(featureSample.getName());
                sb.append("\",");
            }
            sb.append("], \n");
        }
        sb.append("}");

        return sb.toString();
    }
}