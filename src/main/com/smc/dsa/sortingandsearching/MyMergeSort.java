package com.smc.dsa.sortingandsearching;

public class MyMergeSort {

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
        MyMergeSort mms = new MyMergeSort();

        mms.getArray()[0] = 40;
        mms.getArray()[1] = 60;
        mms.getArray()[2] = 50;
        mms.getArray()[3] = 30;
        mms.getArray()[4] = 90;
        mms.getArray()[5] = 80;
        mms.getArray()[6] = 10;
        mms.getArray()[7] = 20;
        mms.getArray()[8] = 100;
        mms.getArray()[9] = 70;

        System.out.println("Array Before Sorting: " + mms.asString(0, mms.getArray().length));
        mms.sort();
        System.out.println("Array After sorting: " + mms.asString(0, mms.getArray().length));

    }

    private void sort() {
        sort(0, getArray().length -1);
    }
    
    private void sort(int begin, int end) {
        if (begin == end) {
            return;
        }

        if (begin + 1 == end) {
            if (array[begin] > array[end]) {
                int temp = array[begin];
                array[begin] = array[end];
                array[end] = temp;
            }

            return;
        }

        int mid = (begin+end) / 2;
        sort(begin, mid);
        sort(mid+1, end);
        merge(begin, mid, end);
    }

    private void merge(int begin, int mid, int end) {
        System.out.println("\nBefore merge: " + asString(begin, end+1));

        int[] temp = new int[end - begin + 1];

        int i;
        int j;
        int k=0;

        for (i = begin, j = mid+1; i <= mid && j <= end;) {
            temp[k++] = array[i] < array[j] ? array[i++] :array[j++];
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= end) {
            temp[k++] = array[j++];
        }

        for (int x = begin, y=0; x <= end; x++, y++) {
            array[x] = temp[y];
        }

        System.out.println("After merge: " + asString(begin, end+1));
    }

}
