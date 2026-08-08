package org.pac4j.scribe.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pac4j.scribe.builder.api.YibanApi20;

/**
 * Tests for {@link YibanService}.
 */
class YibanServiceTest {

    @Test
    void shouldConstructWithValidArguments() {
        YibanService service = new YibanService(
                YibanApi20.instance(), "apiKey", "apiSecret",
                "http://callback", "scope", "code",
                null, null, null, null);
        assertNotNull(service);
    }

    @Test
    void shouldNotBeNullWhenCreatedViaApi() {
        var service = YibanApi20.instance().createService(
                "key", "secret", "http://cb", null, "code",
                null, null, null, null);
        assertNotNull(service);
        assertInstanceOf(YibanService.class, service);
    }
}
