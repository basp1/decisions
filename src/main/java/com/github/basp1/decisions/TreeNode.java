package com.github.basp1.decisions;

import java.util.HashSet;
import java.util.Set;

public class TreeNode {
    private TreeNode parent;
    private Set<TreeNode> successors;
    private Object value;

    public TreeNode(TreeNode parent, Object value) {
        this.parent = parent;
        this.value = value;
        this.successors = new HashSet<>();
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
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