package org.pac4j.scribe.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link YibanToken}.
 */
class YibanTokenTest {

    @Test
    void shouldStoreAllConstructorArguments() {
        YibanToken token = new YibanToken(
                "accessToken", "bearer", 3600,
                "refreshToken", "scope", "rawResponse", "user123");
        assertEquals("accessToken", token.getAccessToken());
        assertEquals("bearer", token.getTokenType());
        assertEquals(Integer.valueOf(3600), token.getExpiresIn());
        assertEquals("refreshToken", token.getRefreshToken());
        assertEquals("scope", token.getScope());
        assertEquals("user123", token.getUserid());
        assertEquals(Integer.valueOf(3600), token.getExpires());
    }

    @Test
    void shouldAllowSettingUserid() {
        YibanToken token = new YibanToken(
                "at", "bearer", 100, "rt", "s", "raw", "old");
        token.setUserid("new");
        assertEquals("new", token.getUserid());
    }

    @Test
    void shouldAllowSettingExpires() {
        YibanToken token = new YibanToken(
                "at", "bearer", 100, "rt", "s", "raw", "uid");
        token.setExpires(7200);
        assertEquals(Integer.valueOf(7200), token.getExpires());
    }

    @Test
    void shouldBeEqualToItself() {
        YibanToken token = new YibanToken(
                "at", "bearer", 100, "rt", "s", "raw", "uid");
        assertEquals(token, token);
    }

    @Test
    void shouldNotBeEqualToNull() {
        YibanToken token = new YibanToken(
                "at", "bearer", 100, "rt", "s", "raw", "uid");
        assertNotEquals(null, token);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        YibanToken token = new YibanToken(
                "at", "bearer", 100, "rt", "s", "raw", "uid");
        assertNotEquals("string", token);
    }

    @Test
    void shouldBeEqualWhenAllFieldsMatch() {
        YibanToken t1 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid");
        YibanToken t2 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid");
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenUseridDiffers() {
        YibanToken t1 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid1");
        YibanToken t2 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid2");
        assertNotEquals(t1, t2);
    }

    @Test
    void shouldNotBeEqualWhenExpiresDiffers() {
        YibanToken t1 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid");
        YibanToken t2 = new YibanToken("at", "bearer", 200, "rt", "s", "raw", "uid");
        assertNotEquals(t1, t2);
    }

    @Test
    void shouldHandleNullUseridInEquals() {
        YibanToken t1 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", null);
        YibanToken t2 = new YibanToken("at", "bearer", 100, "rt", "s", "raw", null);
        assertEquals(t1, t2);
    }

    @Test
    void shouldProduceMeaningfulToString() {
        YibanToken token = new YibanToken(
                "myToken", "bearer", 100, "rt", "s", "raw", "user1");
        String str = token.toString();
        assertNotNull(str);
        assertTrue(str.contains("YibanToken"), "toString should contain class name");
        assertTrue(str.contains("myToken"), "toString should contain access token");
        assertTrue(str.contains("user1"), "toString should contain userid");
    }

    @Test
    void shouldHaveConsistentHashCode() {
        YibanToken token = new YibanToken("at", "bearer", 100, "rt", "s", "raw", "uid");
        int hash1 = token.hashCode();
        int hash2 = token.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void shouldAllowNullExpires() {
        YibanToken token = new YibanToken("at", "bearer", null, "rt", "s", "raw", "uid");
        assertNull(token.getExpires());
        assertNull(token.getExpiresIn());
    }
}
