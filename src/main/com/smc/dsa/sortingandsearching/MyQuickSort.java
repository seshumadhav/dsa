package com.smc.dsa.sortingandsearching;

public class MyQuickSort {

    private int[] array = new int[10];

    private int[] getArray() {
        return array;
    }

    public String asString(int begin, int end) {
        StringBuilder sb = new StringBuilder();

        for (int i = begin; i < end; i++) {
            sb.append(array[i]).append(" ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        MyQuickSort mqs = new MyQuickSort();

        mqs.getArray()[0] = 40;
        mqs.getArray()[1] = 60;
        mqs.getArray()[2] = 50;
        mqs.getArray()[3] = 30;
        mqs.getArray()[4] = 90;
        mqs.getArray()[5] = 80;
        mqs.getArray()[6] = 10;
        mqs.getArray()[7] = 20;
        mqs.getArray()[8] = 100;
        mqs.getArray()[9] = 70;

        System.out.println("Array Before Sorting: " + mqs.asString(0, mqs.getArray().length));
        mqs.sort();
        System.out.println("\n\nArray After sorting: " + mqs.asString(0, mqs.getArray().length));

    }

    public void sort() {
        sort(0, array.length-1);
    }

    private void sort(int begin, int end) {
        if (begin >= end) {
            return;
        }

        if (begin+1 == end) {
            if (array[begin] > array[end]) {
                swap(begin, end);
            }

            return;
        }

        int pivot = partition(begin, end);

            sort(begin, pivot-1);
            sort(pivot + 1, end);
    }

    private int partition(int begin, int end) {
        System.out.printf("\n\nPartition(%d, %d) called. \nArray before Partition: %s", begin, end, asString(begin, end+1));

        int pivot = array[begin];
        swap(begin, end);

        int left = begin;
        int right = end - 1;

        while (left <= right) {
            while (left <= right && array[left] <= pivot) left++;
            while (right >= left && array[right] >= pivot) right--;

            if (left < right) {
                swap(left, right);
            }
        }

        swap(left, end);
        System.out.printf("\nArray after partition(%d, %d): %s\nPivot(%d) is at right position: %d", begin, end, asString(begin, end+1), pivot, left);
        return left;
    }

    void swap(int pos1, int pos2) {
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

}
