package org.pac4j.oauth.profile.baidu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.client.BaiduClient;

/**
 * Tests for {@link BaiduProfileCreator}.
 */
class BaiduProfileCreatorTest {

    @Test
    void shouldConstructWithConfigurationAndClient() {
        OAuth20Configuration config = new OAuth20Configuration();
        BaiduClient client = new BaiduClient();
        BaiduProfileCreator creator = new BaiduProfileCreator(config, client);
        assertNotNull(creator);
    }
}
