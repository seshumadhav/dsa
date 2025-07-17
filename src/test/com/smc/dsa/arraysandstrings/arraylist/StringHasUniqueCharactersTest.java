package com.smc.dsa.arraysandstrings.arraylist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHasUniqueCharactersTest {

    @Test
    void hasAllUniqueCharacters_Sol2() {
        // All unique characters
        assertTrue(StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("abcdefg"));

        // Duplicate characters
        assertFalse(StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("hello"));

        // Empty string
        assertTrue(StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2(""));

        // Single character
        assertTrue(StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("a"));

        // More than 128 characters (should be false)
        StringBuilder longStr = new StringBuilder();
        for (int i = 0; i < 129; i++) {
            longStr.append('a');
        }
        assertFalse(StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2(longStr.toString()));

        // Case sensitivity
        assertThrows(IllegalArgumentException.class, (() -> StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("aA")));

        // Special characters
        assertThrows(IllegalArgumentException.class, (() -> StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("!@#$%^&*()")));
        assertThrows(IllegalArgumentException.class, (() -> StringHasUniqueCharacters.hasAllUniqueCharacters_Sol2("!!@")));
    }
}