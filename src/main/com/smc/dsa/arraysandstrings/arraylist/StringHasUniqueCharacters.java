package com.smc.dsa.arraysandstrings.arraylist;

// Question: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?
// https://chatgpt.com/share/6878fc3f-fa74-8007-9dcb-9825dfdf1b31
// https://chatgpt.com/share/6878fc67-ecac-8007-9092-189fc564ff39
public class StringHasUniqueCharacters {

    public static void main(String[] args) {
        String input = "abcdefg";
        boolean hasUniqueChars = hasAllUniqueCharacters_Sol1(input);
        System.out.println("Does the string \"" + input + "\" have all unique characters? " + hasUniqueChars);
    }

    public static boolean hasAllUniqueCharacters_Sol1(String str) {
        if (str.length() > 128) { // Assuming ASCII characters
            return false; // More than 128 characters means at least one duplicate
        }

        boolean[] charSet = new boolean[128]; // ASCII character set

        for (int i = 0; i < str.length(); i++) {
            int charIndex = str.charAt(i);
            if (charSet[charIndex]) {
                return false; // Character already found
            }
            charSet[charIndex] = true; // Mark character as found
        }

        return true; // All characters are unique

    }
    // * if the character set is ASCII or Extended ASCII or Unicode
    // Clarifications questions:
    // * Case-sensitivity - Is 'a' and 'A' same or different?
    // * If the string is empty, should it be considered as having all unique characters?
    // * If the string has spaces, should they be considered as characters?
    //
    // Assumption: ASCII character set (0-127)
    // Bit masking solution :-
    public static boolean hasAllUniqueCharacters_Sol2(String str) {
        if (str.length() > 128) return false;

        int checker = 0;
        for (char ch : str.toCharArray()) {
            int val = ch - 'a'; // Assuming lowercase letters only

            if (val < 0 || val >= 26) {
                throw new IllegalArgumentException("Input string contains characters outside the range of 'a' to 'z'.");
            }

            if ((checker & (1 << val)) != 0) {
                return false;
            }

            checker |= (1 << val);
        }
        return true;
    }
}
