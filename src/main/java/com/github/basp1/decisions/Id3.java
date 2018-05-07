package com.github.basp1.decisions;

import com.github.basp1.extralib.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

public class Id3 {
    public Id3() {

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

    public DecisionTree buildTree(Dataset dataset) {
        DecisionTree tree = new DecisionTree();
        buildTree(tree, tree.getRoot(), dataset, dataset.getFeatures());
        return tree;
    }

    private void buildTree(DecisionTree tree, TreeNode parent, Dataset dataset, Collection<Feature> features) {
        Object firstValue = dataset.first().getValue();
        Boolean allTheSame = !dataset
                .stream()
                .anyMatch(sample -> !firstValue.equals(sample.getValue()));

        if (allTheSame) {
            List<Object> values = dataset
                    .stream()
                    .map(sample -> sample.getValue())
                    .collect(Collectors.toList());
            TreeNode leaf = tree.addNode(values);
            tree.putEdge(parent, leaf);
            return;
        }

        for (Feature feature : features) {
            double metric = entropy(dataset
                    .stream()
                    .map(sample -> sample.getFeatureSample(feature)).collect(Collectors.toList()));
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
            List<Object> values = dataset
                    .stream()
                    .map(sample -> sample.getValue())
                    .collect(Collectors.toList());
            TreeNode leaf = tree.addNode(values);
            tree.putEdge(parent, leaf);
        } else {
            TreeNode node = tree.addNode(winner);
            tree.putEdge(parent, node);

            for (FeatureSample featureSample : winner.getFeatureSamples()) {
                Dataset sucSamples = new Dataset();
                sucSamples.addAll(dataset
                        .stream()
                        .filter(sample -> featureSample == sample.getFeatureSample(winner))
                        .collect(Collectors.toList()));

                List<Feature> sucFeatures = features
                        .stream()
                        .filter(feature -> winner != feature)
                        .collect(Collectors.toList());

                if (0 == sucSamples.size()) {
                    continue;
                }

                TreeNode suc = tree.addNode(featureSample);

                tree.putEdge(node, suc);

                buildTree(tree, suc, sucSamples, sucFeatures);
            }
        }
    }
}