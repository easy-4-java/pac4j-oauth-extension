package org.pac4j.oauth.profile.yiban;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.client.YibanClient;

/**
 * Tests for {@link YibanProfileCreator}.
 */
class YibanProfileCreatorTest {

    @Test
    void shouldConstructWithConfigurationAndClient() {
        OAuth20Configuration config = new OAuth20Configuration();
        YibanClient client = new YibanClient();
        YibanProfileCreator creator = new YibanProfileCreator(config, client);
        assertNotNull(creator);
    }
}
