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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ull.insert(ull.getUllCurrentSize() + 1, 'A'));
        assertTrue(exception.getMessage().contains("cannot be greater than current size"));
    }

    @org.junit.jupiter.api.Test
    void insert_indexOne_doesNotThrowException() {
        ULLV1 ull = new ULLV1();
        assertDoesNotThrow(() -> ull.insert(0, 'B'));
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
        ull.insert(3, 'D');
        ull.insert(4, 'E');
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
        ull.insert(3, 'D');
        ull.insert(1, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('X', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_2ndHalf() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(3, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('X', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_atBeginning() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(0, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('X', ull.get(0));
        assertEquals('A', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_afterFirst() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(1, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('X', ull.get(1));
        assertEquals('B', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_middle() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(2, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('X', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_beforeLast() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(3, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('X', ull.get(3));
        assertEquals('D', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_oneFullNode_atEnd() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(4, 'X');
        assertEquals(5, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('D', ull.get(3));
        assertEquals('X', ull.get(4));
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertInFirstNode() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(4, 'E');
        ull.insert(5, 'F');
        ull.insert(2, 'X');
        assertEquals(7, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('X', ull.get(2));
        assertEquals('C', ull.get(3));
        assertEquals('D', ull.get(4));
        assertEquals('E', ull.get(5));
        assertEquals('F', ull.get(6));
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertInFilledPartOfSecondNode() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(4, 'E');
        ull.insert(5, 'F');
        ull.insert(5, 'Y');
        assertEquals(7, ull.getUllCurrentSize());
        assertEquals('A', ull.get(0));
        assertEquals('B', ull.get(1));
        assertEquals('C', ull.get(2));
        assertEquals('D', ull.get(3));
        assertEquals('E', ull.get(4));
        assertEquals('Y', ull.get(5));
        assertEquals('F', ull.get(6));
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertAtEndOfFilledPartOfSecondNode() {
        ULLV1 ull = new ULLV1();
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
        assertEquals(10, ull.getUllCurrentSize());
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
    }

    @org.junit.jupiter.api.Test
    void insert_random() {
        ULLV1 ull = new ULLV1();
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
        ull.insert(3, 'X');
        assertEquals(11, ull.getUllCurrentSize());
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
    }

    @org.junit.jupiter.api.Test
    void insert_twoNodes_insertBeyondEndOfFilledPartOfSecondNode_throws() {
        ULLV1 ull = new ULLV1();
        ull.insert(0, 'A');
        ull.insert(1, 'B');
        ull.insert(2, 'C');
        ull.insert(3, 'D');
        ull.insert(4, 'E');
        ull.insert(5, 'F');
        assertThrows(IllegalArgumentException.class, () -> ull.insert(8, 'Q'));
    }

}