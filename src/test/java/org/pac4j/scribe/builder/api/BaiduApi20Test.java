package org.pac4j.scribe.builder.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.scribejava.core.model.Verb;

/**
 * Tests for {@link BaiduApi20}.
 */
class BaiduApi20Test {

    @Test
    void shouldReturnNonNullSingleton() {
        assertNotNull(BaiduApi20.instance());
    }

    @Test
    void shouldReturnSameInstanceEveryTime() {
        assertSame(BaiduApi20.instance(), BaiduApi20.instance());
    }

    @Test
    void shouldUseGetVerbForAccessToken() {
        assertEquals(Verb.GET, BaiduApi20.instance().getAccessTokenVerb());
    }

    @Test
    void shouldReturnCorrectAccessTokenEndpoint() {
        assertEquals("https://openapi.baidu.com/oauth/2.0/token",
                BaiduApi20.instance().getAccessTokenEndpoint());
    }

    @Test
    void shouldReturnNonNullAccessTokenExtractor() {
        assertNotNull(BaiduApi20.instance().getAccessTokenExtractor());
    }
}
