package com.example.que4;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class que4b {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        int left = 0, right = inorder.size() - 1;
        while (right - left + 1 > k) {
            if (Math.abs(inorder.get(left) - target) > Math.abs(inorder.get(right) - target))
                left++;
            else
                right--;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            result.add(inorder.get(i));
        }
        return result;
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null) return;
        inorderTraversal(root.left, inorder);
        inorder.add(root.val);
        inorderTraversal(root.right, inorder);
    }

    public static void main(String[] args) {
        // Construct the balanced binary search tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        // Define the target value and the number of closest values
        double target = 3.8;
        int numClosest = 2;

        que4b solution = new que4b();
        List<Integer> result = solution.closestKValues(root, target, numClosest);
        System.out.println(result);  // Output: [3, 4]
    }
}
