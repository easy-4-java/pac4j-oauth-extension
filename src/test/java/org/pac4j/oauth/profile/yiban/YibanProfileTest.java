package org.pac4j.oauth.profile.yiban;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pac4j.oauth.profile.weibo.WeiboProfileDefinition;

/**
 * Tests for {@link YibanProfile}.
 */
class YibanProfileTest {

    private YibanProfile profile;

    @BeforeEach
    void setUp() {
        profile = new YibanProfile();
    }

    @Test
    void shouldReturnNullFirstNameWhenNotSet() {
        assertNull(profile.getFirstName());
    }

    @Test
    void shouldReturnNullDisplayNameWhenNotSet() {
        assertNull(profile.getDisplayName());
    }

    @Test
    void shouldReturnNullUsernameWhenNotSet() {
        assertNull(profile.getUsername());
    }

    @Test
    void shouldReturnNullLocaleWhenNotSet() {
        assertNull(profile.getLocale());
    }

    @Test
    void shouldReturnNullPictureUrlWhenNotSet() {
        assertNull(profile.getPictureUrl());
    }

    @Test
    void shouldReturnFirstNameWhenSet() {
        profile.addAttribute(YibanProfileDefinition.NAME, "John");
        assertEquals("John", profile.getFirstName());
    }

    @Test
    void shouldReturnDisplayNameFromScreenName() {
        profile.addAttribute(WeiboProfileDefinition.SCREEN_NAME, "JohnDoe");
        assertEquals("JohnDoe", profile.getDisplayName());
    }

    @Test
    void shouldReturnUsernameFromScreenName() {
        profile.addAttribute(WeiboProfileDefinition.SCREEN_NAME, "JohnDoe");
        assertEquals("JohnDoe", profile.getUsername());
    }

    @Test
    void shouldReturnLocaleWhenSet() {
        Locale locale = Locale.CHINA;
        profile.addAttribute(WeiboProfileDefinition.LANG, locale);
        assertEquals(locale, profile.getLocale());
    }

    @Test
    void shouldReturnPictureUrlWhenSet() {
        URI uri = URI.create("http://example.com/avatar_hd.jpg");
        profile.addAttribute(WeiboProfileDefinition.AVATAR_HD, uri);
        assertEquals(uri, profile.getPictureUrl());
    }
}
