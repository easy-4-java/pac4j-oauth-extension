package org.pac4j.scribe.builder.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.scribejava.core.model.Verb;

/**
 * Tests for {@link OschinaApi20}.
 */
class OschinaApi20Test {

    @Test
    void shouldReturnNonNullSingleton() {
        assertNotNull(OschinaApi20.instance());
    }

    @Test
    void shouldReturnSameInstanceEveryTime() {
        assertSame(OschinaApi20.instance(), OschinaApi20.instance());
    }

    @Test
    void shouldUseGetVerbForAccessToken() {
        assertEquals(Verb.GET, OschinaApi20.instance().getAccessTokenVerb());
    }

    @Test
    void shouldReturnCorrectAccessTokenEndpoint() {
        assertEquals("http://www.oschina.net/action/openapi/token",
                OschinaApi20.instance().getAccessTokenEndpoint());
    }

    @Test
    void shouldReturnNonNullAccessTokenExtractor() {
        assertNotNull(OschinaApi20.instance().getAccessTokenExtractor());
    }
}
