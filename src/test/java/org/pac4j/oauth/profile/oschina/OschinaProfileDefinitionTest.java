package org.pac4j.oauth.profile.oschina;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Tests for {@link OschinaProfileDefinition}.
 */
class OschinaProfileDefinitionTest {

    private static final String VALID_JSON = """
            {
              "id": 899,
              "email": "test@gmail.com",
              "name": "TestUser",
              "gender": "male",
              "avatar": "http://www.oschina.net/uploads/user/test",
              "location": "Guangdong Shenzhen",
              "url": "http://home.oschina.net/test"
            }
            """;

    private static final String ERROR_JSON = """
            {
              "error": "invalid_token",
              "error_description": "Invalid access token"
            }
            """;

    private final OschinaProfileDefinition definition = new OschinaProfileDefinition();

    @Test
    void shouldExtractProfileFromValidJson() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        assertNotNull(profile);
        assertEquals("899", profile.getId());
    }

    @Test
    void shouldPopulateName() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("TestUser", profile.getDisplayName());
    }

    @Test
    void shouldPopulateUsername() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("TestUser", profile.getUsername());
    }

    @Test
    void shouldPopulateFirstName() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("TestUser", profile.getFirstName());
    }

    @Test
    void shouldPopulateAvatarUrl() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        URI pictureUrl = profile.getPictureUrl();
        assertNotNull(pictureUrl);
        assertTrue(pictureUrl.toString().contains("oschina.net"));
    }

    @Test
    void shouldPopulateProfileUrl() {
        OschinaProfile profile = definition.extractUserProfile(VALID_JSON);
        URI profileUrl = profile.getProfileUrl();
        assertNotNull(profileUrl);
        assertTrue(profileUrl.toString().contains("oschina.net"));
    }

    @Test
    void shouldThrowOnErrorResponse() {
        assertThrows(Exception.class, () -> definition.extractUserProfile(ERROR_JSON));
    }

    @Test
    void shouldThrowOnMalformedJson() {
        assertThrows(Exception.class, () -> definition.extractUserProfile("not json"));
    }

    @Test
    void shouldFormatProfileUrlWithToken() {
        OAuth2AccessToken token = new OAuth2AccessToken("tokenValue", "bearer", null, null, null, "test_token_123");
        String url = definition.getProfileUrl(token, null);
        assertNotNull(url);
        assertTrue(url.contains("test_token_123"), "URL should contain the raw access token response");
        assertTrue(url.startsWith("https://www.oschina.net/action/openapi/user"));
    }

    @Test
    void shouldReturnProfileUrlConstant() {
        assertNotNull(OschinaProfileDefinition.PROFILE_URL);
        assertTrue(OschinaProfileDefinition.PROFILE_URL.contains("%s"));
    }
}
