package org.pac4j.oauth.profile.baidu;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.pac4j.core.profile.Gender;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Tests for {@link BaiduProfileDefinition}.
 */
class BaiduProfileDefinitionTest {

    private static final String VALID_JSON = """
            {
              "userid":"2097322476",
              "username":"wl19871011",
              "realname":"Sunshine",
              "userdetail":"Likes freedom",
              "birthday":"1987-01-01",
              "marriage":"Dating",
              "sex":"1",
              "blood":"O",
              "constellation":"Sagittarius",
              "figure":"Slim",
              "education":"University",
              "trade":"IT",
              "job":"Engineer",
              "portrait":"abc123"
            }
            """;

    private static final String ERROR_JSON = """
            {
              "error_code": 1,
              "error_msg": "Invalid access token"
            }
            """;

    private final BaiduProfileDefinition definition = new BaiduProfileDefinition();

    @Test
    void shouldExtractProfileFromValidJson() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertNotNull(profile);
        assertEquals("2097322476", profile.getId());
    }

    @Test
    void shouldPopulateUsername() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("wl19871011", profile.getUsername());
    }

    @Test
    void shouldPopulateDisplayName() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Sunshine", profile.getDisplayName());
    }

    @Test
    void shouldPopulateFirstName() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Sunshine", profile.getFirstName());
    }

    @Test
    void shouldPopulateGender() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals(Gender.MALE, profile.getGender());
    }

    @Test
    void shouldPopulateBirthday() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("1987-01-01", profile.getBirthday());
    }

    @Test
    void shouldPopulateMarriage() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Dating", profile.getMarriage());
    }

    @Test
    void shouldPopulateBlood() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("O", profile.getBlood());
    }

    @Test
    void shouldPopulateConstellation() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Sagittarius", profile.getConstellation());
    }

    @Test
    void shouldPopulateFigure() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Slim", profile.getFigure());
    }

    @Test
    void shouldPopulateEducation() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("University", profile.getEducation());
    }

    @Test
    void shouldPopulateTrade() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("IT", profile.getTrade());
    }

    @Test
    void shouldPopulateJob() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Engineer", profile.getJob());
    }

    @Test
    void shouldPopulateUserDetail() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        assertEquals("Likes freedom", profile.getUserdetail());
    }

    @Test
    void shouldPopulateSmallPortraitUrl() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        // Portrait URLs are stored under the secondary attribute key "portrait_small_url"
        // which differs from the PORTRAIT_SMALL_URL template constant.
        Object smallUrl = profile.getAttribute(BaiduProfileDefinition.PORTRAIT_SMALL);
        assertNotNull(smallUrl, "Small portrait should be stored under PORTRAIT_SMALL key");
    }

    @Test
    void shouldPopulateLargePortraitUrl() {
        BaiduProfile profile = definition.extractUserProfile(VALID_JSON);
        Object largeUrl = profile.getAttribute(BaiduProfileDefinition.PORTRAIT_LARGE);
        assertNotNull(largeUrl, "Large portrait should be stored under PORTRAIT_LARGE key");
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
        // The 6-arg constructor's last parameter is rawResponse, which getProfileUrl uses
        OAuth2AccessToken token = new OAuth2AccessToken("tokenValue", "bearer", null, null, null, "test_token_123");
        String url = definition.getProfileUrl(token, null);
        assertNotNull(url);
        assertTrue(url.contains("test_token_123"), "URL should contain the raw access token response");
        assertTrue(url.startsWith("https://openapi.baidu.com/rest/2.0/passport/users/getInfo"));
    }

    @Test
    void shouldReturnProfileUrlConstant() {
        assertNotNull(BaiduProfileDefinition.PROFILE_URL);
        assertTrue(BaiduProfileDefinition.PROFILE_URL.contains("%s"));
    }

    @Test
    void shouldExtractFemaleGender() {
        String json = """
                {
                  "userid":"123",
                  "username":"user",
                  "realname":"Name",
                  "userdetail":"",
                  "birthday":"",
                  "marriage":"",
                  "sex":"0",
                  "blood":"",
                  "constellation":"",
                  "figure":"",
                  "education":"",
                  "trade":"",
                  "job":"",
                  "portrait":"p1"
                }
                """;
        BaiduProfile profile = definition.extractUserProfile(json);
        assertEquals(Gender.FEMALE, profile.getGender());
    }

    @Test
    void shouldExtractUnspecifiedGenderForUnknownValue() {
        String json = """
                {
                  "userid":"123",
                  "username":"user",
                  "realname":"Name",
                  "userdetail":"",
                  "birthday":"",
                  "marriage":"",
                  "sex":"unknown",
                  "blood":"",
                  "constellation":"",
                  "figure":"",
                  "education":"",
                  "trade":"",
                  "job":"",
                  "portrait":"p1"
                }
                """;
        BaiduProfile profile = definition.extractUserProfile(json);
        assertEquals(Gender.UNSPECIFIED, profile.getGender());
    }
}
