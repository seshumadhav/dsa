package com.smc.dsa.treesandgraphs;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyBST {

    Node head;

    public class Node {
        int value;
        Node left = null;
        Node right = null;
    }

    public static void main(String[] args) {
        MyBST bst = newBst();
        bst.delete(3);
        assertEquals("5 6 7 10 12 15 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(17);
        assertEquals("3 5 6 7 10 12 15", bst.inorderT().trim());

        bst = newBst();
        bst.delete(7);
        assertEquals("3 5 6 10 12 15 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(12);
        assertEquals("3 5 6 7 10 15 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(6);
        assertEquals("3 5 7 10 12 15 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(5);
        assertEquals("3 6 7 10 12 15 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(15);
        assertEquals("3 5 6 7 10 12 17", bst.inorderT().trim());

        bst = newBst();
        bst.delete(10);
        assertEquals("3 5 6 7 12 15 17", bst.inorderT().trim());
    }

    private static MyBST newBst() {
        MyBST bst = new MyBST();

        bst.insert(10);
        assertEquals("10", bst.inorderT().trim());

        bst.insert(5);
        assertEquals("5 10", bst.inorderT().trim());

        bst.insert(15);
        assertEquals("5 10 15", bst.inorderT().trim());

        bst.insert(12);
        assertEquals("5 10 12 15", bst.inorderT().trim());

        bst.insert(6);
        assertEquals("5 6 10 12 15", bst.inorderT().trim());

        bst.insert(17);
        assertEquals("5 6 10 12 15 17", bst.inorderT().trim());

        bst.insert(3);
        assertEquals("3 5 6 10 12 15 17", bst.inorderT().trim());

        bst.insert(7);
        assertEquals("3 5 6 7 10 12 15 17", bst.inorderT().trim());

        return bst;
    }

    private String inorderT() {
        StringBuffer sb = new StringBuffer();

        if (head == null) {
            return sb.toString();
        }

        sb.append(inorderT(head));
        return sb.toString();
    }

    private String inorderT(Node node) {
        StringBuffer sb = new StringBuffer();

        if (node == null) {
            return "";
        }

        sb.append(inorderT(node.left));
        sb.append(node.value).append(" ");
        sb.append(inorderT(node.right));

        return sb.toString();
    }

    private void insert(int i) {
        if (head == null) {
            Node node = new Node();
            node.value = i;
            head = node;
            return;
        }

        insert(head, i);
    }

    private void insert(Node current, int i) {
        if (i < current.value) {
            if (current.left == null) {
                Node node = new Node();
                node.value = i;
                current.left = node;
                return;
            }

            insert(current.left, i);
        } else if (i >= current.value) {
            if (current.right == null) {
                Node node = new Node();
                node.value = i;
                current.right = node;
                return;
            }

            insert(current.right, i);
        }
    }

    boolean delete(int i) {
        if (head == null) {
            return false;
        }

        return delete(head, null, i);
    }

    private boolean delete(Node current, Node parent, int i) {
        // matches node
        if (current.value == i) {

            // are you leaf?
            if (current.left == null && current.right == null) {
                // reset link with parent.
                if (parent.left == current) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }

                return true;
            }

            // do you have single child
            boolean isLeftNull = current.left == null && current.right != null;
            boolean isRightNull = current.left != null && current.right == null;

            if (isLeftNull || isRightNull) {
                if (current.left == null) {
                    if (parent.left == current) {
                        parent.left = current.right;
                    } else if (parent.right == current) {
                        parent.right = current.right;
                    }
                }

                if (current.right == null) {
                    if (parent.left == current) {
                        parent.left = current.left;
                    } else if (parent.right == current) {
                        parent.right = current.left;
                    }
                }

                return true;
            }

            // do you have 2 children
            // find ios
            Map<Node, Node> iosResult = ios(current.right, current);
            Map.Entry<Node, Node> iosResult0  = iosResult.entrySet().iterator().next();
            Node ios = iosResult0.getKey();
            Node iosParent = iosResult0.getValue();

            ios.left = current.left;

            // adjust pointers{
            if (parent != null) {
                if (parent.left == current) {
                    parent.left = ios;
                } else if (parent.right == current) {
                    parent.right = ios;
                }
            } else { //you are head
                ios.right = head.right;
                head = ios;
            }

            adjustIosParentLinks(iosParent, ios);
            return true;
        }

        if (i < current.value) {
            return delete(current.left, current, i);
        } else {
            return delete(current.right, current, i);
        }
    }

    private static void adjustIosParentLinks(Node iosParent, Node ios) {
        if (iosParent.left  == ios) {
            iosParent.left = null;
        } else if (iosParent.right  == ios) {
            iosParent.right = null;
        }
    }

    Map<Node, Node> ios(Node node, Node parent) {
        if (node.left != null) {
            return ios(node.left, node);
        }

        return Map.of(node, parent);
    }
}
