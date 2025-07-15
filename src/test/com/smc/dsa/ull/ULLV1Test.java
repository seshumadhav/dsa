package com.smc.dsa.ull;

import static org.junit.jupiter.api.Assertions.*;

class ULLV1Test {
    @org.junit.jupiter.api.Test
    void insert_negativeindex_throwsIndexOutOfBoundsException() {
        ULLV1 ull = new ULLV1();
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> ull.insert(-1, 'A'));
        assertTrue(exception.getMessage().contains("out of bounds"));
    }

    @org.junit.jupiter.api.Test
    void insert_biggerThanCurrentSize_throwsIndexOutOfBoundsException() {
        ULLV1 ull = new ULLV1();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ull.insert(ull.getCurrentSize() + 1, 'A'));
        assertTrue(exception.getMessage().contains("cannot be greater than current size"));
    }

    @org.junit.jupiter.api.Test
    void insert_indexOne_doesNotThrowException() {
        ULLV1 ull = new ULLV1();
        ULLV1.Node currentNode;
        currentNode = assertDoesNotThrow(() -> ull.insert(0, 'B'));
    }

    @org.junit.jupiter.api.Test
    void insert_intoEmptyList_createsHeadNode() {
        ULLV1 ull = new ULLV1();
        ULLV1.Node node = ull.insert(0, 'A');
        assertNotNull(ull.getHead());
        assertNull(node.next);
        assertEquals(node, ull.getHead());
        assertEquals('A', ull.getHead().array[0]);
        assertEquals(1, ull.getHead().currentSize);
    }

    @org.junit.jupiter.api.Test
    void insert_atEndOfNodeWithSpace_simpleInsert() {
        ULLV1 ull = new ULLV1();
        assertEquals(0, ull.insert(0, 'A').nodeId);
        assertEquals(0, ull.insert(1, 'B').nodeId);
        assertEquals(0, ull.insert(2, 'C').nodeId);

        // Insert at the end of the node (index 3, NODE_CAPACITY=4)
        assertEquals(0, ull.insert(3, 'D').nodeId);
        assertEquals('D', ull.getHead().array[3]);
        assertEquals(4, ull.getHead().currentSize);
    }

    @org.junit.jupiter.api.Test
    void insert_inMiddleOfNode_shiftsElements() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        // Insert in the middle (index 1)
        ull.insert(1, 'X');
        assertEquals('A', ull.getHead().array[0]);
        assertEquals('X', ull.getHead().array[1]);
        assertEquals('B', ull.getHead().array[2]);
        assertEquals('C', ull.getHead().array[3]);
        assertEquals(4, ull.getHead().currentSize);
    }

    @org.junit.jupiter.api.Test
    void insert_atEndOfFullNode_triggersMoveOrSplit() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Insert at the end (index 4)
        ull.insert(4, 'E');
        // After split/move, head should still have 4 items, and next node should exist
        assertNotNull(ull.getHead().next);
        assertEquals(4, ull.getHead().currentSize);
        assertEquals('E', ull.getHead().next.array[0]);
        assertEquals(1, ull.getHead().next.currentSize);
    }

    @org.junit.jupiter.api.Test
    void insert_atEndOfList_appends() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        // Insert at the end (index 3)
        ull.insert(3, 'D');
        assertEquals('D', ull.getHead().array[3]);
        assertEquals(4, ull.getHead().currentSize);
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_FirstHalf() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D' & insert(1, 'X')");
        ull.printList();

        // Final insert
        ull.insert(1, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('X', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_FirstHalf");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_2ndHalf() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D' & insert(3, 'X')");
        ull.printList();

        // Final insert
        ull.insert(3, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('X', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_2ndHalf");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_atBeginning() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'X', 'A', 'B', 'C' & insert(0, 'X')");
        ull.printList();

        // Final insert
        ull.insert(0, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('X', ull.get(0));
        assertEquals('A', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_atBeginning");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_afterFirst() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'X', 'B', 'D' and insert(1, 'X')");
        ull.printList();

        // Final insert
        ull.insert(1, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('X', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_afterFirst");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_middle() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D' and insert(2, 'X')");
        ull.printList();

        // Final insert
        ull.insert(2, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('X', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_middle");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_beforeLast() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D' and insert(3, 'X')");
        ull.printList();

        // Final insert
        ull.insert(3, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('X', ull.get(3));
        assertEquals('D', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_beforeLast");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_atEnd() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D'); // Node is now full (NODE_CAPACITY=4)

        // Print before final insert
        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D' and insert(4, 'X')");
        ull.printList();

        // Final insert
        ull.insert(4, 'X');
        assertEquals(5, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('D', ull.get(3));
        assertEquals('X', ull.get(4));

        System.out.println("ULL after all verifications for: insert_oneFullNode_atEnd");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertInFirstNode() {
        ULLV1 ull = new ULLV1();
        // Fill first node
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        // Fill part of second node
        ull.insert(4, 'E');
        ull.insert(5, 'F');

        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D', 'E', 'F' and insert(2', 'X')");
        ull.printList();

        ull.insert(2, 'X');


        assertEquals(7, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('X', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
        assertEquals('E', ull.get(5));
        assertEquals('F', ull.get(6));

        System.out.println("ULL after all verifications for: insert_twoNodes_insertInFirstNode");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertInFilledPartOfSecondNode() {
        ULLV1 ull = new ULLV1();
        // Fill first node
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        // Fill part of second node
        ull.insert(4, 'E');
        ull.insert(5, 'F');

        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D', 'E', 'F' and insert(5, 'Y')");
        ull.printList();

        ull.insert(5, 'Y');


        assertEquals(7, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('D', ull.get(3));
        assertEquals('E', ull.get(4));
        assertEquals('Y', ull.get(5));
        assertEquals('F', ull.get(6));

        System.out.println("ULL after all verifications for: insert_twoNodes_insertInFilledPartOfSecondNode");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertAtEndOfFilledPartOfSecondNode() {
        ULLV1 ull = new ULLV1();
        // Fill first node
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        // Fill part of second node
        ull.insert(4, 'E');
        ull.insert(5, 'F');
        ull.insert(6, 'G');
        ull.insert(7, 'H');
        ull.insert(8, 'I');
        ull.insert(9, 'J');

        // Print after all inserts
        printInsertedItems(ull);
        ull.printList();

        assertEquals(10, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('D', ull.get(3));
        assertEquals('E', ull.get(4));
        assertEquals('F', ull.get(5));
        assertEquals('G', ull.get(6));
        assertEquals('H', ull.get(7));
        assertEquals('I', ull.get(8));
        assertEquals('J', ull.get(9));

        System.out.println("\nULL after all verifications for: insert_twoNodes_insertAtEndOfFilledPartOfSecondNode");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_random() {
        ULLV1 ull = new ULLV1();
        // Fill first node
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');

        ull.insert(4, 'E');
        ull.insert(5, 'F');
        ull.insert(6, 'G');
        ull.insert(7, 'H');

        ull.insert(8, 'I');
        ull.insert(9, 'J');

        System.out.println("\n\nHere are the items inserted and in the same order: 'A', 'B', 'C', 'D', 'E', 'F, 'G, 'H', 'I', 'J' and insert(3, 'X')");
        ull.printList();
        // Insert at end of filled part of 2nd node (index 6)
        ull.insert(3, 'X');
        assertEquals(11, ull.getCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('X', ull.get(3));
        assertEquals('D', ull.get(4));
        assertEquals('E', ull.get(5));
        assertEquals('F', ull.get(6));
        assertEquals('G', ull.get(7));
        assertEquals('H', ull.get(8));
        assertEquals('I', ull.get(9));
        assertEquals('J', ull.get(10));
        System.out.println("ULL after all verifications for: insert_twoNodes_insertAtEndOfFilledPartOfSecondNode");
        ull.printList();
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertBeyondEndOfFilledPartOfSecondNode_throws() {
        ULLV1 ull = new ULLV1();
        // Fill first node
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        // Fill part of second node
        ull.insert(4, 'E');
        ull.insert(5, 'F');
        // Try to insert at index 7 (should throw, as currentSize is 6, so valid is 0..6)
        assertThrows(IllegalArgumentException.class, () -> ull.insert(8, 'Q'));
    }

    // Utility method to print the current state of the ULL in a consistent format
    private void printInsertedItems(ULLV1 ull) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the items inserted and in the same order: ");
        for (int i = 0; i < ull.getCurrentSize(); i++) {
            sb.append("'").append(ull.get(i)).append("'");
            if (i < ull.getCurrentSize() - 1) sb.append(", ");
        }
        System.out.println(sb.toString());
    }


}