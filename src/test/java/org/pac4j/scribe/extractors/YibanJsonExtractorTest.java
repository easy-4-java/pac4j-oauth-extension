package org.pac4j.scribe.extractors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link YibanJsonExtractor}.
 */
class YibanJsonExtractorTest {

    @Test
    void shouldReturnNonNullSingleton() {
        assertNotNull(YibanJsonExtractor.instance());
    }

    @Test
    void shouldReturnSameInstanceEveryTime() {
        assertSame(YibanJsonExtractor.instance(), YibanJsonExtractor.instance());
    }
}
