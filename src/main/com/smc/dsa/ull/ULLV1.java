package com.smc.dsa.ull;

public class ULLV1 {
    public static final int NODE_CAPACITY = 4;
    private int ullCurrentSize = 0;
    private Node head = null;

    public Node getHead() {
        return head;
    }

    public ULLV1() {}

    public char get(int index) {
        if (index < 0 || index >= ullCurrentSize) {
            throw new IndexOutOfBoundsException("Index (" + index + ") out of bounds");
        }
        Node currentNode = head;
        int relIndexInNode = index;
        while (currentNode != null) {
            if (relIndexInNode < currentNode.currentSize) {
                return currentNode.array[relIndexInNode];
            }
            relIndexInNode -= currentNode.currentSize;
            currentNode = currentNode.next;
        }
        throw new RuntimeException("Unexpected error: index (" + index + ") is out of bounds, but should have been caught earlier");
    }

    public Node insert(int index, char item) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index for insertion (" + index + ") out of bounds");
        }
        if (index > ullCurrentSize) {
            throw new IllegalArgumentException("Index for insertion(" + index + ") cannot be greater than current size(" + ullCurrentSize + ")");
        }
        if (head == null) {
            head = new Node(0);
            head.array[0] = item;
            head.currentSize++;
            ullCurrentSize++;
            return head;
        }

        // TODO(smc): This is buggy code. Fix it
        Node currentNode = head;
        int numItemsCountedInULLSoFar = 0;
        int relIndexInNode;
        while (true) {
            if (numItemsCountedInULLSoFar + currentNode.currentSize > index) {
                relIndexInNode = index - numItemsCountedInULLSoFar;
                currentNode.insert(relIndexInNode, item);
                ullCurrentSize++;
                return currentNode;
            }
            numItemsCountedInULLSoFar += currentNode.currentSize;
            if (currentNode.next == null) {
                Node newNode = new Node(currentNode.nodeId + 1);
                Node.adjustPointers(currentNode, newNode);
            }
            currentNode = currentNode.next;
        }
        // -- End Buggy Code ---
    }

    public int getUllCurrentSize() {
        return ullCurrentSize;
    }

    public class Node {
        char[] array = new char[ULLV1.NODE_CAPACITY];
        int currentSize = 0;
        Node next = null;
        int nodeId;

        public Node(int id) {
            this.nodeId = id;
        }

        public boolean isFull() {
            return currentSize >= array.length;
        }

        private static void adjustPointers(Node thisNode, Node newNode) {
            newNode.next = thisNode.next;
            thisNode.next = newNode;
        }

        public void insert(int relIndexInNode, char item) {
            if (relIndexInNode > NODE_CAPACITY) {
                throw new IllegalArgumentException("relIndexInNode (" + relIndexInNode + ") is greater than NODE_CAPACITY (" + NODE_CAPACITY + ")");
            }
            if (relIndexInNode >= currentSize) {
                array[relIndexInNode] = item;
                currentSize++;
                return;
            }
            if (currentSize + 1 <= NODE_CAPACITY) {
                for (int i = currentSize; i > relIndexInNode; i--) {
                    array[i] = array[i - 1];
                }
                array[relIndexInNode] = item;
                currentSize++;
                return;
            }
            Node nextNode = this.next != null ? this.next : new Node(this.nodeId + 1);
            char[] tempArray = new char[2 * NODE_CAPACITY];
            int tempArraySize = 0;
            for (int i = 0; i < relIndexInNode; i++) {
                tempArray[i] = array[i];
                tempArraySize++;
            }
            tempArray[relIndexInNode] = item;
            tempArraySize++;
            for (int i = relIndexInNode; i < currentSize; i++) {
                tempArray[i + 1] = array[i];
                tempArraySize++;
            }
            tempArraySize = currentSize + 1;
            Node nodeOfInsertion = this;
            while (tempArraySize > NODE_CAPACITY) {
                if (nodeOfInsertion.next == null) {
                    nextNode = new Node(nodeOfInsertion.nodeId + 1);
                    adjustPointers(nodeOfInsertion, nextNode);
                } else {
                    nextNode = nodeOfInsertion.next;
                }
                int numInserted = 0;
                int numItemsToCopy = tempArraySize % 2 == 0 ? tempArraySize / 2 : tempArraySize / 2 + 1;
                for (int i = 0; i < numItemsToCopy; i++) {
                    nodeOfInsertion.array[i] = tempArray[i];
                    numInserted++;
                }
                nodeOfInsertion.currentSize = numInserted;
                char[] nextNodeTempArray = new char[2 * NODE_CAPACITY];
                int j = 0;
                int nextNodeTempArraySize = 0;
                for (int i = numInserted; i < tempArraySize; i++) {
                    nextNodeTempArray[j++] = tempArray[i];
                    nextNodeTempArraySize++;
                }
                for (int i = 0; i < nextNode.currentSize; i++) {
                    nextNodeTempArray[j++] = nextNode.array[i];
                    nextNodeTempArraySize++;
                }
                tempArray = new char[2 * NODE_CAPACITY];
                tempArraySize = nextNodeTempArraySize;
                System.arraycopy(nextNodeTempArray, 0, tempArray, 0, nextNodeTempArraySize);
                nodeOfInsertion = nextNode;
            }
            for (int i = 0; i < tempArraySize; i++) {
                nextNode.array[i] = tempArray[i];
            }
            nextNode.currentSize = tempArraySize;
        }
    }
}
