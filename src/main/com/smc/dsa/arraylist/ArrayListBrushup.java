package com.smc.dsa.arraylist;

import java.util.ArrayList;

public class ArrayListBrushup {

    public static void main(String[] args) {
        System.out.println("Brushup ArrayList");

        ArrayList<String> list = new ArrayList<>();

        list.add("0");
        list.add(1, "1");
        list.add("3");
        list.add("4");
        list.add("5");

        System.out.println("Printing arraylist: \n" + print(list));

        list.remove("3");
        System.out.println("Print after removing \"3\": \n" + print(list));

        list.remove(2);
        System.out.println("Print after removing item at index: 2: \n" + print(list));

    }

    private static String print(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (String s : list) {
            sb.append("(").append(i++).append(", ").append(s).append(")\n");
        }

        return sb.toString();
    }
}
