package org.pac4j.oauth.profile.baidu;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pac4j.core.profile.Gender;

/**
 * Tests for {@link BaiduProfile}.
 */
class BaiduProfileTest {

    private BaiduProfile profile;

    @BeforeEach
    void setUp() {
        profile = new BaiduProfile();
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
    void shouldReturnNullPortraitLargeUrlWhenNotSet() {
        assertNull(profile.getPortraitLargeUrl());
    }

    @Test
    void shouldReturnNullUserdetailWhenNotSet() {
        assertNull(profile.getUserdetail());
    }

    @Test
    void shouldReturnNullBirthdayWhenNotSet() {
        assertNull(profile.getBirthday());
    }

    @Test
    void shouldReturnNullMarriageWhenNotSet() {
        assertNull(profile.getMarriage());
    }

    @Test
    void shouldReturnNullGenderWhenNotSet() {
        assertNull(profile.getGender());
    }

    @Test
    void shouldReturnNullBloodWhenNotSet() {
        assertNull(profile.getBlood());
    }

    @Test
    void shouldReturnNullFigureWhenNotSet() {
        assertNull(profile.getFigure());
    }

    @Test
    void shouldReturnNullConstellationWhenNotSet() {
        assertNull(profile.getConstellation());
    }

    @Test
    void shouldReturnNullEducationWhenNotSet() {
        assertNull(profile.getEducation());
    }

    @Test
    void shouldReturnNullTradeWhenNotSet() {
        assertNull(profile.getTrade());
    }

    @Test
    void shouldReturnNullJobWhenNotSet() {
        assertNull(profile.getJob());
    }

    @Test
    void shouldReturnDisplayNameWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.REAL_NAME, "John");
        assertEquals("John", profile.getDisplayName());
    }

    @Test
    void shouldReturnUsernameWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.USER_NAME, "john123");
        assertEquals("john123", profile.getUsername());
    }

    @Test
    void shouldReturnFirstNameSameAsRealName() {
        profile.addAttribute(BaiduProfileDefinition.REAL_NAME, "John");
        assertEquals("John", profile.getFirstName());
    }

    @Test
    void shouldReturnGenderWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.SEX, Gender.MALE);
        assertEquals(Gender.MALE, profile.getGender());
    }

    @Test
    void shouldReturnBirthdayWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.BIRTHDAY, "1990-01-01");
        assertEquals("1990-01-01", profile.getBirthday());
    }

    @Test
    void shouldReturnMarriageWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.MARRIAGE, "Single");
        assertEquals("Single", profile.getMarriage());
    }

    @Test
    void shouldReturnBloodWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.BLOOD, "AB");
        assertEquals("AB", profile.getBlood());
    }

    @Test
    void shouldReturnFigureWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.FIGURE, "Slim");
        assertEquals("Slim", profile.getFigure());
    }

    @Test
    void shouldReturnConstellationWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.CONSTELLATION, "Leo");
        assertEquals("Leo", profile.getConstellation());
    }

    @Test
    void shouldReturnEducationWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.EDUCATION, "Master");
        assertEquals("Master", profile.getEducation());
    }

    @Test
    void shouldReturnTradeWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.TRADE, "IT");
        assertEquals("IT", profile.getTrade());
    }

    @Test
    void shouldReturnJobWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.JOB, "Developer");
        assertEquals("Developer", profile.getJob());
    }

    @Test
    void shouldReturnUserdetailWhenSet() {
        profile.addAttribute(BaiduProfileDefinition.USER_DETAIL, "Bio text");
        assertEquals("Bio text", profile.getUserdetail());
    }
}
