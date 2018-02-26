package com.github.basp1.decisions;

import java.util.*;

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
        TreeNode node = new TreeNode(null, value);
        nodes.add(node);
        return node;
    }

    public void putEdge(TreeNode nodeU, TreeNode nodeV) {
        nodeV.setParent(nodeU);
        nodeU.addSuccessor(nodeV);
    }

    public TreeNode getRoot() {
        return root;
    }

    public Object eval(Sample sample) {
        TreeNode node = getRoot();
        while (null != node) {
            TreeNode suc = node.getSuccessors()
                    .stream()
                    .findFirst()
                    .get();

            if (!(suc.getValue() instanceof Feature)) {
                return suc.getValue();
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

    public Object eval(FeatureSample... featureSamples) {
        return eval(new Sample(Double.NaN, featureSamples));
    }

    public List<TreeNode>[] getAllPaths() {
        List<List<TreeNode>> result = new ArrayList<>();

        Stack<TreeNode> path = new Stack<>();
        Stack<TreeNode> queue = new Stack<>();
        queue.push(getRoot());
        boolean backward = false;

        while (!queue.empty()) {
            TreeNode node = queue.pop();
            if (backward) {
                while (0 != path.size() && node.getParent() != path.pop().getParent()) ;
                backward = false;
            }
            path.push(node);

            if (0 == node.getSuccessors().size()) {
                List<TreeNode> t = new ArrayList<>();
                t.addAll(path);
                result.add(t);
                backward = true;
            }

            queue.addAll(node.getSuccessors());
        }

        return result.toArray(new List[0]);
    }
}