package org.pac4j.oauth.profile.baidu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pac4j.core.profile.Gender;

/**
 * Tests for {@link BaiduGenderConverter}.
 */
class BaiduGenderConverterTest {

    private final BaiduGenderConverter converter = new BaiduGenderConverter();

    @Test
    void shouldReturnMaleForStringOne() {
        assertEquals(Gender.MALE, converter.convert("1"));
    }

    @Test
    void shouldReturnFemaleForStringZero() {
        assertEquals(Gender.FEMALE, converter.convert("0"));
    }

    @Test
    void shouldReturnMaleForStringM() {
        assertEquals(Gender.MALE, converter.convert("m"));
    }

    @Test
    void shouldReturnMaleForStringMale() {
        assertEquals(Gender.MALE, converter.convert("male"));
    }

    @Test
    void shouldReturnFemaleForStringF() {
        assertEquals(Gender.FEMALE, converter.convert("f"));
    }

    @Test
    void shouldReturnFemaleForStringFemale() {
        assertEquals(Gender.FEMALE, converter.convert("female"));
    }

    @Test
    void shouldReturnUnspecifiedForUnknownString() {
        assertEquals(Gender.UNSPECIFIED, converter.convert("unknown"));
    }

    @Test
    void shouldReturnMaleForIntegerTwo() {
        assertEquals(Gender.MALE, converter.convert(2));
    }

    @Test
    void shouldReturnFemaleForIntegerOne() {
        assertEquals(Gender.FEMALE, converter.convert(1));
    }

    @Test
    void shouldReturnUnspecifiedForIntegerZero() {
        assertEquals(Gender.UNSPECIFIED, converter.convert(0));
    }

    @Test
    void shouldReturnUnspecifiedForIntegerThree() {
        assertEquals(Gender.UNSPECIFIED, converter.convert(3));
    }

    @Test
    void shouldReturnNullForUnsupportedType() {
        assertNull(converter.convert(Boolean.TRUE));
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertNull(converter.convert(null));
    }

    @Test
    void shouldHandleCaseInsensitiveStringMale() {
        assertEquals(Gender.MALE, converter.convert("MALE"));
    }

    @Test
    void shouldHandleCaseInsensitiveStringFemale() {
        assertEquals(Gender.FEMALE, converter.convert("FEMALE"));
    }
}
