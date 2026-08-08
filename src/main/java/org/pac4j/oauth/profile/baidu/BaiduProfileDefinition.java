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
package org.pac4j.oauth.profile.baidu;

import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.Token;

/**
 * Profile definition that maps the JSON payload returned by Baidu's
 * {@code users/getInfo} endpoint onto a {@link BaiduProfile}.
 *
 * <p>More info at: <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth/rest/file_data_apis_list">Baidu REST file APIs</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see BaiduProfile
 * @see org.pac4j.oauth.profile.definition.OAuthProfileDefinition
 */
public class BaiduProfileDefinition extends OAuthProfileDefinition {

    private static final long serialVersionUID = 1L;

    /**
     * Template URL used to fetch the Baidu user-info payload, with the
     * {@code %s} placeholder substituted by the raw access-token response.
     */
    public static final String PROFILE_URL = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=%s";

    /** 当前登录用户的数字ID */
    public static final String USER_ID = "userid";
    /** 当前登录用户的用户名，值可能为空。 */
    public static final String USER_NAME = "username";
    /** 用户真实姓名，可能为空。 */
    public static final String REAL_NAME = "realname";
    /**
     * 当前登录用户的头像
     *  头像的地址具体如下：
     *	small image: http://tb.himg.baidu.com/sys/portraitn/item/{$portrait}
     *	large image: http://tb.himg.baidu.com/sys/portrait/item/{$portrait}
     */
    public static final String PORTRAIT = "portrait";
    /** Secondary attribute key holding the small portrait URL string. */
    public static final String PORTRAIT_SMALL = "portrait_small_url";
    /** Secondary attribute key holding the large portrait URL string. */
    public static final String PORTRAIT_LARGE = "portrait_large_url";
    /** Resolved small-portrait URL attribute key (URI value). */
    public static final String PORTRAIT_SMALL_URL = "https://tb.himg.baidu.com/sys/portraitn/item/%s";
    /** Resolved large-portrait URL attribute key (URI value). */
    public static final String PORTRAIT_LARGE_URL = "https://tb.himg.baidu.com/sys/portrait/item/%s";

    /** 自我简介，可能为空。 */
    public static final String USER_DETAIL = "userdetail";
    /** 生日，以yyyy-mm-dd格式显示。 */
    public static final String BIRTHDAY = "birthday";
    /** 婚姻状况  */
    public static final String MARRIAGE = "marriage";
    /** 用户性别, 性别。"1"表示男，"0"表示女 */
    public static final String SEX = "sex";
    /** 血型 */
    public static final String BLOOD = "blood";
    /** 体型 */
    public static final String FIGURE = "figure";
    /** 星座 */
    public static final String CONSTELLATION = "constellation";
    /** 学历 */
    public static final String EDUCATION = "education";
    /** 当前职业 */
    public static final String TRADE = "trade";
    /** 职位  */
    public static final String JOB = "job";

    /**
     * Default constructor; declares every primary/secondary attribute and the
     * converters used to normalise them.
     */
    public BaiduProfileDefinition() {
        primary(USER_ID, Converters.STRING);
        primary(USER_NAME, Converters.STRING);
        primary(REAL_NAME, Converters.STRING);
        primary(PORTRAIT, Converters.STRING);
        primary(USER_DETAIL, Converters.STRING);
        primary(BIRTHDAY, Converters.STRING);
        primary(MARRIAGE, Converters.STRING);
        primary(SEX, new BaiduGenderConverter());
        primary(BLOOD, Converters.STRING);
        primary(FIGURE, Converters.STRING);
        primary(CONSTELLATION, Converters.STRING);
        primary(EDUCATION, Converters.STRING);
        primary(TRADE, Converters.STRING);
        primary(JOB, Converters.STRING);
        secondary(PORTRAIT_LARGE, Converters.URL);
        secondary(PORTRAIT_SMALL, Converters.URL);
    }

    /**
     * Composes the user-info URL using Baidu's documented template.
     *
     * @param accessToken   the OAuth access token issued by Baidu.
     * @param configuration the pac4j OAuth configuration (unused for Baidu
     *                      but required by the contract).
     * @return the formatted URL with the raw access-token response substituted.
     */
    @Override
    public String getProfileUrl(Token accessToken, OAuthConfiguration configuration) {
        return String.format(PROFILE_URL, accessToken.getRawResponse());
    }

    /**
     * Extracts a {@link BaiduProfile} from the raw JSON payload returned by
     * the Baidu user-info endpoint.
     *
     * <p>The method bails out with {@link org.pac4j.oauth.profile.definition.OAuthProfileDefinition#raiseProfileExtractionJsonError(String)}
     * whenever the body cannot be parsed or when Baidu returned an
     * {@code error_code} field.</p>
     *
     * <p>Sample payload:</p>
     * <pre>
     * {
     *   "userid":"2097322476",
     *   "username":"wl19871011",
     *   "realname":"阳光",
     *   "userdetail":"喜欢自由",
     *   "birthday":"1987-01-01",
     *   "marriage":"恋爱",
     *   "sex":"男",
     *   "blood":"O",
     *   "constellation":"射手",
     *   "figure":"小巧",
     *   "education":"大学/专科",
     *   "trade":"计算机/电子产品",
     *   "job":"未知",
     *   "birthday_year":"1987",
     *   "birthday_month":"01",
     *   "birthday_day":"01"
     * }
     * </pre>
     *
     * @param body the raw response body from Baidu.
     * @return a fully populated {@link BaiduProfile} (never {@code null}).
     * @throws org.pac4j.core.exception.TechnicalException when the payload
     *         cannot be parsed or Baidu signals an error.
     */
    @Override
    public BaiduProfile extractUserProfile(String body) {
        final BaiduProfile profile = new BaiduProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null && JsonHelper.getElement(json, "error_code") == null) {
            // 当前登录用户的数字ID
            profile.setId(JsonHelper.getElement(json, USER_ID).toString());
            // 主要属性
            for (final Object attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, attribute.toString(), JsonHelper.getElement(json, attribute.toString()));
            }
            // 次要属性
            convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, PORTRAIT_SMALL, String.format(PORTRAIT_SMALL_URL, JsonHelper.getElement(json, PORTRAIT).toString()));
            convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, PORTRAIT_LARGE, String.format(PORTRAIT_LARGE_URL, JsonHelper.getElement(json, PORTRAIT).toString()));
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }
}