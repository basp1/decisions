package com.github.basp1.decisions;

import com.github.basp1.extralib.Tree;
import com.github.basp1.extralib.TreeNode;

import java.util.Comparator;

public class DecisionTree extends Tree {

    public DecisionTree() {
        super();
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
}