package com.smc.dsa.ull;

public class ULLV1 {
    public static final int NODE_CAPACITY = 4;
    private int currentSize = 0;
    private Node head = null;

    public Node getHead() {
        return head;
    }

    public ULLV1() {}

    public char get(int index) {
        // Error handling
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Index (" + index + ") out of bounds");
        }

        Node currentNode = head;
        int relIndexInNode = index;

        while (currentNode != null) {
            if (relIndexInNode < currentNode.currentSize) {
                // If the index is within the current node's size, we can return the item directly
                return currentNode.array[relIndexInNode];
            }

            // Move to the next node
            relIndexInNode -= currentNode.currentSize;
            currentNode = currentNode.next;
        }

        // If we reach here, it means the index is out of bounds
        throw new RuntimeException("Unexpected error: index (" + index + ") is out of bounds, but should have been caught earlier");
    }

    public Node insert(int index, char item) {
        // Error handling
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index for insertion (" + index + ") out of bounds");
        }

        if (index > currentSize) {
            throw new IllegalArgumentException("Index for insertion(" + index + ") cannot be greater than current size(" + currentSize + ")");
        }

        if (head == null) {
            // If the list is empty, create a new node and set it as head
            head = new Node(0);
            head.array[0] = item;
            head.currentSize++;
            currentSize++;
            return head;
        }

        Node currentNode = gotoTargetNodeAndReturnIt(index, head);

        // Double check if our index is still valid at this node
        int relIndexInNode = index % NODE_CAPACITY;
        if (relIndexInNode > currentNode.currentSize) {
            throw new RuntimeException("this cannot happen, relIndexInNode should be less than currentSize of the node");
        }

        currentNode.insert(relIndexInNode, item);
        currentSize++;

        // printList();
        // Can remove this and make the method void
        return currentNode;
    }

    private Node gotoTargetNodeAndReturnIt(int index, Node head) {
        int targetNodeNumber = index / NODE_CAPACITY;
        int thisNodeNumber = 0;
        Node currentNode = head;

        // Traverse to the target node, creating nodes as needed, and name them sequentially
        while (thisNodeNumber < targetNodeNumber) {
            if (currentNode.next == null) {
                // Always name nodes sequentially: head/node0, node1, node2, ...
                Node newNode = new Node(currentNode.nodeId+1);
                currentNode.next = newNode;
            }
            currentNode = currentNode.next;
            thisNodeNumber++;
        }
        return currentNode;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!\n");
        ULLV1 ull = new ULLV1();
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
            // Adjust pointers to maintain the linked list structure
            newNode.next = thisNode.next;
            thisNode.next = newNode;
        }

        public void insert(int relIndexInNode, char item) {
            if (relIndexInNode >= NODE_CAPACITY) {
                throw new IllegalArgumentException("Something wrong. This cannot happen. relIndexInNode (" + relIndexInNode + ") is greater than NODE_CAPACITY (" + NODE_CAPACITY + ")");
            }

            if (relIndexInNode >= currentSize) {
                // If we are inserting at an index greater than current size but smaller than NODE_CAP, there is free space. So insert directly
                array[relIndexInNode] = item;
                currentSize++;
                return;
            }

            // We are overwriting an existing element but there is room in the node to accommodate another item. We should insert and shift elements
            if (currentSize + 1 <= NODE_CAPACITY) {
                // If we are inserting in the middle, we need to shift elements
                for (int i = currentSize; i > relIndexInNode; i--) {
                    array[i] = array[i - 1];
                }
                array[relIndexInNode] = item;
                currentSize++;
                return;
            }

            Node nextNode = this.next != null? this.next : new Node(this.nodeId+1);
            
            char[] tempArray = new char[2*NODE_CAPACITY];
            int tempArraySize = 0;

            for (int i = 0; i < relIndexInNode; i++) {
                tempArray[i] = array[i];
                tempArraySize++;
            }

            tempArray[relIndexInNode] = item;
            tempArraySize++;

            for (int i = relIndexInNode; i < currentSize; i++) {
                tempArray[i+1] = array[i];
                tempArraySize++;
            }

            Node nodeOfInsertion = this;
            while (tempArraySize > NODE_CAPACITY) {
                if (nodeOfInsertion.next == null) {
                    // System.out.println("Next Node is null. Creating a new node...");
                    // If next node is null, we need to create a new node
                    nextNode = new Node(nodeOfInsertion.nodeId+1);
                    adjustPointers(nodeOfInsertion, nextNode);
                } else {
                    // If next node is not null, we can use it
                    nextNode = nodeOfInsertion.next;
                }

                // System.out.println("Copying first half of temp Array into this node's array and second half into next node's array");
                // We need to move first half of temp Array into this node's array and 2nd half into next node's array

                int numInserted = 0;

                int numItemsToCopy = tempArraySize%2 == 0 ? tempArraySize/2 : tempArraySize/2 + 1;
                // 6 -> 3 items - 0, 1, 2
                // 5 -> 3 items - 0, 1, 2

                for (int i = 0; i < numItemsToCopy; i++) {
                    nodeOfInsertion.array[i] = tempArray[i];
                    numInserted++;
                }
                // Update current size to half
                nodeOfInsertion.currentSize = numInserted;

                // Create new temp array to hold the second half of the temp array
                char[] nextNodeTempArray = new char[2*NODE_CAPACITY];
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


                // Reset artifacts to do the same for next node
                // 1) Reset tempArray before updating and update it with nextNodeTempArray
                tempArray = new char[2*NODE_CAPACITY];
                tempArraySize = nextNodeTempArraySize;
                System.arraycopy(nextNodeTempArray, 0, tempArray, 0, nextNodeTempArray.length);
                // 2) Move to next Node
                nodeOfInsertion = nextNode;
            }
            // If we reach here, it means we have enough space in the next node to accommodate the remaining elements
            // System.out.println("No split needed. Copying remaining elements to next node");
            for (int i = 0; i < tempArraySize; i++) {
                nextNode.array[i] = tempArray[i];
            }
            nextNode.currentSize = tempArraySize;


        }
    }

    public void printList() {
        Node currentNode = head;
        while (currentNode != null) {
            System.out.print("[node" + currentNode.nodeId + "]: ");
            for (int i = 0; i < currentNode.currentSize; i++) {
                System.out.print(currentNode.array[i] + " ");
            }
            System.out.println();
            currentNode = currentNode.next;
        }
    }

    // Utility method to print char arrays as [a, b, c]
    private static String printCharArray(char[] arr, int len) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < len; i++) {
            if (i > 0) sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
