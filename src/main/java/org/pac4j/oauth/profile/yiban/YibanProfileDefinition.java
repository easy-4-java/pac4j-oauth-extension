/*
 * Copyright (c) 2018-present, easy-4-java (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pac4j.oauth.profile.yiban;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

import java.util.Arrays;

import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.Token;

/**
 * Profile definition that maps the JSON payload returned by YiBan's
 * {@code user/me} endpoint onto a {@link YibanProfile}.
 *
 * <p>YiBan's schema mirrors Sina Weibo's; the attribute names are therefore
 * identical and the same converter pipeline (URL, STRING, BOOLEAN, INTEGER,
 * LONG, LOCALE, GENDER) is reused.</p>
 *
 * <p>More info at: <a href="https://open.yiban.cn/wiki/index.php?page=user/real_me">user/real_me</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see YibanProfile
 * @see org.pac4j.oauth.profile.definition.OAuthProfileDefinition
 */
public class YibanProfileDefinition extends OAuthProfileDefinition {

    private static final long serialVersionUID = 1L;

    /**
     * Template URL used to fetch the YiBan user-info payload, with the
     * {@code %s} placeholder substituted by the raw access-token response.
     */
    public static final String PROFILE_ME_URL = "https://openapi.yiban.cn/user/me?access_token=%s";

    /** int64    User UID */
    public static final String ID = "id";
    /** string   String-type user UID */
    public static final String IDSTR = "idstr";
    /** string   User's Nickname */
    public static final String SCREEN_NAME = "screen_name";
    /** string   Friendly display name */
    public static final String NAME = "name";
    /** int  User's provincial ID */
    public static final String PROVINCE = "province";
    /** int  User's city ID */
    public static final String CITY = "city";
    /** string   User location */
    public static final String LOCATION = "location";
    /** string   User personal description */
    public static final String DESCRIPTION = "description";
    /** url  User blog address */
    public static final String URL = "url";
    /** url  User avatar address (middle), 50×50 pixels */
    public static final String PROFILE_IMAGE_URL = "profile_image_url";
    /** url  User cover image url */
    public static final String COVER_IMAGE_PHONE = "cover_image_phone";
    /** url  User's Weibo unified URL address */
    public static final String PROFILE_URL = "profile_url";
    /** string   User's personalized domain name */
    public static final String DOMAIN = "domain";
    /** string   User's weihao number */
    public static final String WEIHAO = "weihao";
    /** string   Gender, m: male, f: female, n: unknown */
    public static final String GENDER = "gender";
    /** int  Number of fans */
    public static final String FOLLOWERS_COUNT = "followers_count";
    /** int  Number of followers */
    public static final String FRIENDS_COUNT = "friends_count";
    /** int  Weibo number */
    public static final String STATUSES_COUNT = "statuses_count";
    /** int  Number of favorites */
    public static final String FAVOURITES_COUNT = "favourites_count";
    /** string   User creation (registration) time */
    public static final String CREATED_AT = "created_at";
    /** boolean  Not supported yet */
    public static final String FOLLOWING = "following";
    /** boolean  Whether to allow everyone to send me a private message, true: yes, false: no */
    public static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";
    /** boolean  Whether to allow identification of the user's geographic location, true: yes, false: no */
    public static final String GEO_ENABLED = "geo_enabled";
    /** boolean    Whether it is a Weibo authenticated user, that is, a V-user, true: yes, false: no */
    public static final String VERIFIED = "verified";
    /** int    Not supported yet */
    public static final String VERIFIED_TYPE = "verified_type";
    /** string    User note information, this field is only returned when querying user relationships */
    public static final String REMARK = "remark";
    /** object    User's recent Weibo information field */
    public static final String STATUS = "status";
    /** boolean    Whether to allow everyone to comment on my Weibo, true: yes, false: no */
    public static final String ALLOW_ALL_COMMENT = "allow_all_comment";
    /** string    User avatar address (larger image), 180 × 180 pixels */
    public static final String AVATAR_LARGE = "avatar_large";
    /** string    User avatar address (HD), HD avatar original */
    public static final String AVATAR_HD = "avatar_hd";
    /** string    Reason for certification */
    public static final String VERIFIED_REASON = "verified_reason";
    /** boolean    Whether the user is concerned about the currently logged in user, true: yes, false: no */
    public static final String FOLLOW_ME = "follow_me";
    /** int    User's online status, 0: not online, 1: online */
    public static final String ONLINE_STATUS = "online_status";
    /** int    User's mutual powder count */
    public static final String BI_FOLLOWERS_COUNT = "bi_followers_count";
    /** string    User's current language version, zh-cn: Simplified Chinese, zh-tw: Traditional Chinese, en: English */
    public static final String LANG = "lang";

    /**
     * Default constructor; declares every primary attribute and the
     * converters used to normalise them.
     */
    public YibanProfileDefinition() {
        Arrays.stream(new String[]{
            URL,
            PROFILE_IMAGE_URL,
            COVER_IMAGE_PHONE,
            PROFILE_URL,
            AVATAR_LARGE,
            AVATAR_HD,
        }).forEach(a -> primary(a, Converters.URL));
        Arrays.stream(new String[]{
            IDSTR,
            SCREEN_NAME,
            NAME,
            LOCATION,
            DESCRIPTION,
            DOMAIN,
            WEIHAO,
            CREATED_AT,
            REMARK,
            VERIFIED_REASON
        }).forEach(a -> primary(a, Converters.STRING));
        Arrays.stream(new String[]{
            FOLLOWING,
            ALLOW_ALL_ACT_MSG,
            GEO_ENABLED,
            VERIFIED,
            ALLOW_ALL_COMMENT,
            FOLLOW_ME
        }).forEach(a -> primary(a, Converters.BOOLEAN));
        Arrays.stream(new String[]{
            PROVINCE,
            CITY,
            FOLLOWERS_COUNT,
            FRIENDS_COUNT,
            STATUSES_COUNT,
            FAVOURITES_COUNT,
            VERIFIED_TYPE,
            ONLINE_STATUS,
            BI_FOLLOWERS_COUNT
        }).forEach(a -> primary(a, Converters.INTEGER));
        primary(ID, Converters.LONG);
        primary(LANG, Converters.LOCALE);
        primary(GENDER, Converters.GENDER);
    }

    /**
     * Composes the user-info URL using YiBan's documented template.
     *
     * @param accessToken   the OAuth access token issued by YiBan.
     * @param configuration the pac4j OAuth configuration (unused for YiBan
     *                      but required by the contract).
     * @return the formatted URL with the raw access-token response substituted.
     */
    @Override
    public String getProfileUrl(Token accessToken, OAuthConfiguration configuration) {
        return String.format(PROFILE_ME_URL, accessToken.getRawResponse());
    }

    /**
     * Extracts a {@link YibanProfile} from the raw JSON payload returned by
     * the YiBan {@code user/me} endpoint.
     *
     * @param body the raw response body from YiBan.
     * @return a fully populated {@link YibanProfile} (never {@code null}).
     * @throws org.pac4j.core.exception.TechnicalException when the payload
     *         cannot be parsed.
     */
    @Override
    public YibanProfile extractUserProfile(final String body) {
        final YibanProfile profile = new YibanProfile();
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(JsonHelper.getElement(json, ID).toString());
            for (final Object attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute.toString(), JsonHelper.getElement(json, attribute.toString()));
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }

}