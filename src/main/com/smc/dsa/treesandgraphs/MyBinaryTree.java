package com.smc.dsa.treesandgraphs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MyBinaryTree {
    private Node head;

    public MyBinaryTree() {
        head = null;
    }

    public void insert(int value) {
        if (head == null) {
            head = new Node(value);
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node.left == null) {
                node.left = new Node(value);
                return;
            }
            queue.add(node.left);
            if (node.right == null) {
                node.right = new Node(value);
                return;
            }
            queue.add(node.right);
        }
    }

    public boolean isPresent(int value) {
        return isPresent(head, value);
    }

    private boolean isPresent(Node node, int value) {
        if (node == null) {
            return false;
        }
        return node.value == value || isPresent(node.left, value) || isPresent(node.right, value);
    }

    // true if delete was successful, otherwise false
    public boolean delete(int value) {
        if (head == null) {
            return false;
        }
        Map<Node, Node> parentMap = new HashMap<>();
        Node drmNode = deepestRightmost(head, parentMap);
        return delete(head, null, value, drmNode, parentMap);
    }

    // Find deepest rightmost node
    private Node deepestRightmost(Node node, Map<Node, Node> parentMap) {
        if (node == null) return null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        Node last = null;
        while (!queue.isEmpty()) {
            last = queue.remove();
            if (last.left != null) {
                parentMap.put(last.left, last);
                queue.add(last.left);
            }
            if (last.right != null) {
                parentMap.put(last.right, last);
                queue.add(last.right);
            }
        }
        return last;
    }

    private boolean delete(Node node, Node parent, int value, Node drmNode, Map<Node, Node> parentMap) {
        if (node == null) return false;
        if (node.value == value) {
            // Replace node with drmNode
            if (node == drmNode) {
                // If node to delete is the deepest rightmost node
                if (parent != null) {
                    if (parent.left == node) parent.left = null;
                    else if (parent.right == node) parent.right = null;
                } else {
                    head = null;
                }
                return true;
            }
            Node drmParent = parentMap.get(drmNode);
            if (drmParent != null) {
                if (drmParent.left == drmNode) drmParent.left = null;
                else if (drmParent.right == drmNode) drmParent.right = null;
            }
            drmNode.left = node.left;
            drmNode.right = node.right;
            if (parent != null) {
                if (parent.left == node) parent.left = drmNode;
                else if (parent.right == node) parent.right = drmNode;
            } else {
                head = drmNode;
            }
            return true;
        }
        boolean leftResult = delete(node.left, node, value, drmNode, parentMap);
        if (leftResult) return true;
        boolean rightResult = delete(node.right, node, value, drmNode, parentMap);
        return rightResult;
    }

    public String traverseInOrder(Node node) {
        if (node == null) {
            return "";
        }
        return traverseInOrder(node.left) + " " + node.value + " " + traverseInOrder(node.right);
    }

    public class Node {
        int value;
        Node left;
        Node right;
        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        MyBinaryTree tree = null;
        tree = reset(tree);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertTrue(tree.isPresent(3));
        assertFalse(tree.isPresent(30));
        assertTrue(tree.isPresent(1));
        assertFalse(tree.isPresent(10));
        assertTrue(tree.isPresent(2));
        assertTrue(tree.isPresent(4));
        assertTrue(tree.isPresent(5));
        assertFalse(tree.isPresent(50));
        Map<Node, Node> parentMap = new HashMap<>();
        assertEquals(6, tree.deepestRightmost(tree.head, parentMap).value);
        tree.delete(5);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("2  4  1  6  3", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        tree.delete(4);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("2  6  1  5  3", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        tree.delete(3);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("2  4  1  5  6", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        tree.delete(2);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("6  4  1  5  3", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        tree.delete(1);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("2  4  6  5  3", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        tree.delete(6);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertEquals("2  4  1  5  3", tree.traverseInOrder(tree.head).trim());

        tree = reset(tree);
        System.out.println("Tree: " + tree.traverseInOrder(tree.head));
        assertFalse(tree.delete(99999));
    }

    private static MyBinaryTree reset(MyBinaryTree tree) {
        tree = new MyBinaryTree();

        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        tree.insert(6);

        return tree;
    }
}
