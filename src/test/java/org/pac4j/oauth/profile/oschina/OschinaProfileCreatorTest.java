package org.pac4j.oauth.profile.oschina;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.client.OschinaClient;

/**
 * Tests for {@link OschinaProfileCreator}.
 */
class OschinaProfileCreatorTest {

    @Test
    void shouldConstructWithConfigurationAndClient() {
        OAuth20Configuration config = new OAuth20Configuration();
        OschinaClient client = new OschinaClient();
        OschinaProfileCreator creator = new OschinaProfileCreator(config, client);
        assertNotNull(creator);
    }
}
