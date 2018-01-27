package com.github.basp1.id3;

import java.util.HashSet;
import java.util.Set;

public class TreeNode {
    private Tree tree;
    private Object value;
    private Set<TreeNode> successors;

    public TreeNode(Tree tree, Object value) {
        this.tree = tree;
        this.value = value;
        this.successors = new HashSet<>();
    }

    public Tree getTree() {
        return tree;
    }

    public Object getValue() {
        return value;
    }

    public Set<TreeNode> getSuccessors() {
        return successors;
    }

    void addSuccessor(TreeNode node) {
        successors.add(node);
    }
}