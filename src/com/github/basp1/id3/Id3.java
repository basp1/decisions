package com.github.basp1.id3;

import java.util.*;
import java.util.stream.Collectors;

public class Id3 {
    ArrayList<Feature> features;
    ArrayList<Sample> samples;

    public Id3(Feature... features) {
        this.features = new ArrayList<>();
        this.features.addAll(Arrays.asList(features));
        this.samples = new ArrayList<>();
    }

    public void add(double value, FeatureValue... featureValues) {
        samples.add(new Sample(value, featureValues));
    }

    public Tree buildTree() {
        Tree tree = new Tree();
        buildTree(tree.getRoot(), samples, features);
        return tree;
    }

    private void buildTree(TreeNode parent, List<Sample> samples, List<Feature> features) {
        final Tree tree = parent.getTree();
        for (Feature feature : features) {
            double metric = entropy(samples
                    .stream()
                    .map(sample -> sample.getFeatureValue(feature)).collect(Collectors.toList()));
            feature.setMetric(metric);
        }

        features = features
                .stream()
                .filter(feature -> feature.getMetric() > 0)
                .collect(Collectors.toList());

        final Feature winner = features
                .stream()
                .min(Comparator.comparingDouble(a -> a.getMetric()))
                .orElse(null);

        if (null == winner) {
            Object value = selectEnd(samples
                    .stream()
                    .map(sample -> sample.getValue())
                    .collect(Collectors.toList()));
            TreeNode leaf = tree.addNode(value);
            tree.putEdge(parent, leaf);
        } else {
            TreeNode node = tree.addNode(winner);
            tree.putEdge(parent, node);

            for (FeatureValue featureValue : winner.getFeatureValues()) {
                List<Sample> sucSamples = samples
                        .stream()
                        .filter(sample -> featureValue == sample.getFeatureValue(winner))
                        .collect(Collectors.toList());
                List<Feature> sucFeatures = features
                        .stream()
                        .filter(feature -> winner != feature)
                        .collect(Collectors.toList());

                if (0 == sucSamples.size()) {
                    continue;
                }

                TreeNode suc = tree.addNode(featureValue);

                tree.putEdge(node, suc);

                buildTree(suc, sucSamples, sucFeatures);
            }
        }
    }

    private Object selectEnd(List<Object> values) {
        return values.get(0);
    }

    public static <T> double entropy(Collection<T> values) {
        Map<T, Double> probs = new HashMap<>();
        for (T v : values) {
            if (!probs.containsKey(v)) {
                probs.put(v, 0d);
            }
            probs.put(v, 1d + probs.get(v));
        }

        double sz = values.size();
        for (T k : probs.keySet()) {
            probs.put(k, probs.get(k) / sz);
        }

        double sum = 0;
        for (Double p : probs.values()) {
            sum += -p * Math.log(p) / Math.log(2);
        }

        return sum;
    }
}