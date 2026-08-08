package org.pac4j.oauth.profile.yiban;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Tests for {@link YibanProfileDefinition}.
 */
class YibanProfileDefinitionTest {

    private static final String VALID_JSON = """
            {
              "id": 12345,
              "idstr": "12345",
              "screen_name": "TestUser",
              "name": "Test",
              "province": 11,
              "city": 1,
              "location": "Beijing",
              "description": "A test user",
              "url": "http://example.com",
              "profile_image_url": "http://example.com/avatar.jpg",
              "cover_image_phone": "http://example.com/cover.jpg",
              "profile_url": "testuser",
              "domain": "testuser",
              "weihao": "123",
              "gender": "m",
              "followers_count": 100,
              "friends_count": 50,
              "statuses_count": 200,
              "favourites_count": 10,
              "created_at": "2020-01-01",
              "following": false,
              "allow_all_act_msg": true,
              "geo_enabled": false,
              "verified": false,
              "verified_type": 0,
              "remark": "",
              "allow_all_comment": true,
              "avatar_large": "http://example.com/avatar_large.jpg",
              "avatar_hd": "http://example.com/avatar_hd.jpg",
              "verified_reason": "",
              "follow_me": false,
              "online_status": 0,
              "bi_followers_count": 20,
              "lang": "zh-cn"
            }
            """;

    private final YibanProfileDefinition definition = new YibanProfileDefinition();

    @Test
    void shouldExtractProfileFromValidJson() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        assertNotNull(profile);
        assertEquals("12345", profile.getId());
    }

    @Test
    void shouldPopulateDisplayName() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("TestUser", profile.getDisplayName());
    }

    @Test
    void shouldPopulateUsername() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("TestUser", profile.getUsername());
    }

    @Test
    void shouldPopulateFirstName() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Test", profile.getFirstName());
    }

    @Test
    void shouldPopulatePictureUrl() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        URI pictureUrl = profile.getPictureUrl();
        assertNotNull(pictureUrl);
        assertTrue(pictureUrl.toString().contains("avatar_hd"));
    }

    @Test
    void shouldPopulateProfileUrl() {
        YibanProfile profile = definition.extractUserProfile(VALID_JSON);
        URI profileUrl = profile.getProfileUrl();
        assertNotNull(profileUrl);
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
        assertTrue(url.startsWith("https://openapi.yiban.cn/user/me"));
    }

    @Test
    void shouldReturnProfileMeUrlConstant() {
        assertNotNull(YibanProfileDefinition.PROFILE_ME_URL);
        assertTrue(YibanProfileDefinition.PROFILE_ME_URL.contains("%s"));
    }

    @Test
    void shouldDeclareAllAttributeConstants() {
        assertNotNull(YibanProfileDefinition.ID);
        assertNotNull(YibanProfileDefinition.IDSTR);
        assertNotNull(YibanProfileDefinition.SCREEN_NAME);
        assertNotNull(YibanProfileDefinition.NAME);
        assertNotNull(YibanProfileDefinition.PROVINCE);
        assertNotNull(YibanProfileDefinition.CITY);
        assertNotNull(YibanProfileDefinition.LOCATION);
        assertNotNull(YibanProfileDefinition.DESCRIPTION);
        assertNotNull(YibanProfileDefinition.URL);
        assertNotNull(YibanProfileDefinition.PROFILE_IMAGE_URL);
        assertNotNull(YibanProfileDefinition.COVER_IMAGE_PHONE);
        assertNotNull(YibanProfileDefinition.PROFILE_URL);
        assertNotNull(YibanProfileDefinition.DOMAIN);
        assertNotNull(YibanProfileDefinition.WEIHAO);
        assertNotNull(YibanProfileDefinition.GENDER);
        assertNotNull(YibanProfileDefinition.FOLLOWERS_COUNT);
        assertNotNull(YibanProfileDefinition.FRIENDS_COUNT);
        assertNotNull(YibanProfileDefinition.STATUSES_COUNT);
        assertNotNull(YibanProfileDefinition.FAVOURITES_COUNT);
        assertNotNull(YibanProfileDefinition.CREATED_AT);
        assertNotNull(YibanProfileDefinition.FOLLOWING);
        assertNotNull(YibanProfileDefinition.ALLOW_ALL_ACT_MSG);
        assertNotNull(YibanProfileDefinition.GEO_ENABLED);
        assertNotNull(YibanProfileDefinition.VERIFIED);
        assertNotNull(YibanProfileDefinition.VERIFIED_TYPE);
        assertNotNull(YibanProfileDefinition.REMARK);
        assertNotNull(YibanProfileDefinition.STATUS);
        assertNotNull(YibanProfileDefinition.ALLOW_ALL_COMMENT);
        assertNotNull(YibanProfileDefinition.AVATAR_LARGE);
        assertNotNull(YibanProfileDefinition.AVATAR_HD);
        assertNotNull(YibanProfileDefinition.VERIFIED_REASON);
        assertNotNull(YibanProfileDefinition.FOLLOW_ME);
        assertNotNull(YibanProfileDefinition.ONLINE_STATUS);
        assertNotNull(YibanProfileDefinition.BI_FOLLOWERS_COUNT);
        assertNotNull(YibanProfileDefinition.LANG);
    }
}
