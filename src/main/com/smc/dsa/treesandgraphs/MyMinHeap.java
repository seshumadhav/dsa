package com.smc.dsa.treesandgraphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MyMinHeap {

    int[] array = new int[1000];
    int size = 0;

    public static void main(String[] args) {
        MyMinHeap mmh = createAndPreset();

        System.out.println("");

        assertEquals(1, mmh.extractMin());
        String heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '1': " + heapContents);
        assertEquals("3 4 7 55 50 87 9", heapContents);

        assertEquals(3, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '1': " + heapContents);
        assertEquals("4 9 7 55 50 87", heapContents);

        assertEquals(4, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '1': " + heapContents);
        assertEquals("7 9 87 55 50", heapContents);

        assertEquals(7, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '7': " + heapContents);
        assertEquals("9 50 87 55", heapContents);

        assertEquals(9, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '9': " + heapContents);
        assertEquals("50 55 87", heapContents);

        assertEquals(50, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '50': " + heapContents);
        assertEquals("55 87", heapContents);

        assertEquals(55, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '55': " + heapContents);
        assertEquals("87", heapContents);

        assertEquals(87, mmh.extractMin());
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after extracting '87': " + heapContents);
        assertEquals("", heapContents);
    }

    private static MyMinHeap createAndPreset() {
        MyMinHeap mmh = new MyMinHeap();

        mmh.insert(7);
        String heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 7: " + heapContents);
        assertEquals("7", heapContents);

        mmh.insert(50);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 50: " + heapContents);
        assertEquals("7 50", mmh.traverse().trim());

        mmh.insert(4);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 4: " + heapContents);
        assertEquals("4 50 7", mmh.traverse().trim());

        mmh.insert(55);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 55: " + heapContents);
        assertEquals("4 50 7 55", mmh.traverse().trim());

        mmh.insert(3);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 3: " + heapContents);
        assertEquals("3 4 7 55 50", mmh.traverse().trim());

        mmh.insert(87);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 87: " + heapContents);
        assertEquals("3 4 7 55 50 87", mmh.traverse().trim());

        mmh.insert(9);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 9: " + heapContents);
        assertEquals("3 4 7 55 50 87 9", mmh.traverse().trim());

        mmh.insert(1);
        heapContents = mmh.traverse().trim();
        System.out.println("Heap after inserting 1: " + heapContents);
        assertEquals("1 3 7 4 50 87 9 55", mmh.traverse().trim());

        return mmh;
    }

    private String traverse() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(array[i]).append(" ");
        }

        return sb.toString();
    }

    private static int parent(int i) {
        return i % 2 == 0 ? (i / 2 - 1) : i / 2;
    }

    private void insert(int i) {
        array[size] = i;
        size++;

        adjustForMinHeapBottomUp(size - 1);
    }

    public int extractMin() {
        int target = 0;
        int min = array[target];
        int replacement = size - 1;

        array[target] = array[replacement];
        adjustForMinHeapTopDown(0);
        size--;

        return min;
    }

    private void adjustForMinHeapTopDown(int i) {
        int index = i;
        int left;

        int right;
        do {
            left = index * 2 + 1;
            right = index * 2 + 2;

            if (index >= size || left >= size || right >= size) {
                return;
            }

            if (array[index] <= array[left] && array[index] <= array[right]) {
                return;
            }

            // If not min-heap
            if (array[left] <= array[right]) { // left is same or small
                int temp = array[index];
                array[index] = array[left];
                array[left] = temp;
                index = left;
            } else { // right is small
                int temp = array[index];
                array[index] = array[right];
                array[right] = temp;
                index = right;
            }
        } while (true);
    }

    private void adjustForMinHeapBottomUp(int i) {
        int index = i;
        int parent = parent(index);

        while (index > 0 && array[index] < array[parent]) { // swap
            int temp = array[parent];
            array[parent] = array[index];
            array[index] = temp;
            index = parent(index);
            parent = parent(index);
        }
    }


}
