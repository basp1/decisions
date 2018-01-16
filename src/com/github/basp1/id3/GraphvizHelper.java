package com.github.basp1.id3;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GraphvizHelper {
    public static void Save(Tree tree, String filename) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filename);

        Set<TreeNode> visited = new HashSet<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(tree.getRoot());

        out.println("digraph tree {");
        while (!stack.empty()) {
            TreeNode node = stack.pop();

            visited.add(node);

            for (TreeNode suc : node.getSuccessors()) {
                out.println("  " + node.hashCode() + " ->  " + suc.hashCode());
                if (!visited.contains(suc)) {
                    stack.push(suc);
                }
            }
        }

        out.println();

        for (TreeNode node : tree.getNodes()) {
            out.println("  " + node.hashCode() + "[label=\"" + node.getValue().toString() + "\"]");
        }

        out.println("}");
        out.close();
    }
}