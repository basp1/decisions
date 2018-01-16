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

    public double eval(FeatureValue... featureValues) {
        Sample sample = new Sample(Double.NaN, featureValues);
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
            Integer value = sample.getFeatureValue(feature).getValue();

            node = suc.getSuccessors()
                    .stream()
                    .min(Comparator.comparingInt(a -> Math.abs(value - ((FeatureValue) a.getValue()).getValue())))
                    .get();
        }

        return Double.NaN;
    }
}