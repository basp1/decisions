package com.github.basp1.id3;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Tree {
    private TreeNode root;
    private Set<TreeNode> nodes;

    public Tree() {
        this.nodes = new HashSet<>();
        this.root = addNode(".");
    }

    public Set<TreeNode> getNodes() {
        return nodes;
    }

    public TreeNode addNode(Object value) {
        TreeNode node = new TreeNode(this, value);
        nodes.add(node);
        return node;
    }

    public void putEdge(TreeNode nodeU, TreeNode nodeV) {
        nodeU.addSuccessor(nodeV);
    }

    public TreeNode getRoot() {
        return root;
    }

    public double eval(FeatureSample... featureSamples) {
        Sample sample = new Sample(Double.NaN, featureSamples);
        TreeNode node = getRoot();
        while (null != node) {
            TreeNode suc = node.getSuccessors()
                    .stream()
                    .findFirst()
                    .get();

            if (!(suc.getValue() instanceof Feature)) {
                return (double) suc.getValue();
            }

            Feature feature = (Feature) suc.getValue();
            double[] span = sample.getFeatureSample(feature).getSpan();
            double value = (span[1] + span[0]) / 2;

            node = suc.getSuccessors()
                    .stream()
                    .min(Comparator.comparingDouble(a -> ((FeatureSample) a.getValue()).distance(value)))
                    .get();
        }

        return Double.NaN;
    }
}