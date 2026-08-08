package org.pac4j.scribe.builder.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link YibanApi20}.
 */
class YibanApi20Test {

    @Test
    void shouldReturnNonNullSingleton() {
        assertNotNull(YibanApi20.instance());
    }

    @Test
    void shouldReturnSameInstanceEveryTime() {
        assertSame(YibanApi20.instance(), YibanApi20.instance());
    }

    @Test
    void shouldReturnCorrectAccessTokenEndpoint() {
        assertEquals("https://openapi.yiban.cn/oauth/access_token",
                YibanApi20.instance().getAccessTokenEndpoint());
    }

    @Test
    void shouldReturnCorrectRefreshTokenEndpoint() {
        assertEquals("https://openapi.yiban.cn/oauth/reset_token",
                YibanApi20.instance().getRefreshTokenEndpoint());
    }

    @Test
    void shouldReturnNonNullAccessTokenExtractor() {
        assertNotNull(YibanApi20.instance().getAccessTokenExtractor());
    }

    @Test
    void shouldReturnNonNullBearerSignature() {
        assertNotNull(YibanApi20.instance().getBearerSignature());
    }

    @Test
    void shouldReturnNonNullClientAuthentication() {
        assertNotNull(YibanApi20.instance().getClientAuthentication());
    }

    @Test
    void shouldBuildAuthorizationUrlWithAppidInsteadOfClientId() {
        Map<String, String> additionalParams = new HashMap<>();
        String url = YibanApi20.instance().getAuthorizationUrl(
                "code", "testAppId", "http://callback.example.com",
                "snsapi_base", "testState", additionalParams);
        assertNotNull(url);
        assertTrue(url.contains("appid=testAppId"),
                "URL should contain appid parameter: " + url);
        assertFalse(url.contains("client_id="),
                "URL should NOT contain client_id parameter: " + url);
        assertTrue(url.startsWith("https://openapi.yiban.cn/oauth/authorize"));
    }

    @Test
    void shouldCreateYibanService() {
        var service = YibanApi20.instance().createService(
                "apiKey", "apiSecret", "http://callback", "scope",
                "code", null, null, null, null);
        assertNotNull(service);
        assertTrue(service instanceof org.pac4j.scribe.service.YibanService);
    }
}
