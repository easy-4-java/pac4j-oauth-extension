package org.pac4j.oauth.profile.oschina;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link OschinaProfile}.
 */
class OschinaProfileTest {

    private OschinaProfile profile;

    @BeforeEach
    void setUp() {
        profile = new OschinaProfile();
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
    void shouldReturnNullFirstNameWhenNotSet() {
        assertNull(profile.getFirstName());
    }

    @Test
    void shouldReturnNullPictureUrlWhenNotSet() {
        assertNull(profile.getPictureUrl());
    }

    @Test
    void shouldReturnNullProfileUrlWhenNotSet() {
        assertNull(profile.getProfileUrl());
    }

    @Test
    void shouldReturnDisplayNameWhenSet() {
        profile.addAttribute(OschinaProfileDefinition.NAME, "TestUser");
        assertEquals("TestUser", profile.getDisplayName());
    }

    @Test
    void shouldReturnUsernameWhenSet() {
        profile.addAttribute(OschinaProfileDefinition.NAME, "TestUser");
        assertEquals("TestUser", profile.getUsername());
    }

    @Test
    void shouldReturnFirstNameWhenSet() {
        profile.addAttribute(OschinaProfileDefinition.NAME, "TestUser");
        assertEquals("TestUser", profile.getFirstName());
    }

    @Test
    void shouldReturnPictureUrlWhenSet() {
        URI uri = URI.create("http://example.com/avatar.png");
        profile.addAttribute(OschinaProfileDefinition.AVATAR_URL, uri);
        assertEquals(uri, profile.getPictureUrl());
    }

    @Test
    void shouldReturnProfileUrlWhenSet() {
        URI uri = URI.create("http://home.oschina.net/user");
        profile.addAttribute(OschinaProfileDefinition.URL, uri);
        assertEquals(uri, profile.getProfileUrl());
    }
}
