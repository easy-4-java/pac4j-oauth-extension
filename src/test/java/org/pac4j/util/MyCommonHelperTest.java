package org.pac4j.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MyCommonHelper}.
 */
class MyCommonHelperTest {

    @Test
    void shouldReturnClassHeaderWithNoArgs() {
        String result = MyCommonHelper.toNiceString(String.class);
        assertEquals("#String# |", result);
    }

    @Test
    void shouldFormatSingleKeyValuePair() {
        String result = MyCommonHelper.toNiceString(MyCommonHelperTest.class, "key", "value");
        assertEquals("#MyCommonHelperTest# | key: value |", result);
    }

    @Test
    void shouldFormatMultipleKeyValuePairs() {
        String result = MyCommonHelper.toNiceString(String.class, "a", 1, "b", true);
        assertEquals("#String# | a: 1 | b: true |", result);
    }

    @Test
    void shouldHandleNullValuesGracefully() {
        String result = MyCommonHelper.toNiceString(Object.class, "key", null);
        assertEquals("#Object# | key: null |", result);
    }

    @Test
    void shouldNeverReturnNull() {
        assertNotNull(MyCommonHelper.toNiceString(Object.class));
    }

    @Test
    void shouldHandleOddNumberOfArgs() {
        // When an odd number of args is provided, the last arg is treated as a key
        // and appends a colon (the method alternates key/value but doesn't guard
        // against an odd count).
        String result = MyCommonHelper.toNiceString(Object.class, "k1", "v1", "k2");
        assertEquals("#Object# | k1: v1 | k2:", result);
    }
}
