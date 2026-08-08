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

import java.net.URI;

import org.pac4j.core.profile.Gender;
import org.pac4j.oauth.profile.OAuth20Profile;

/**
 * Strongly-typed user profile returned by {@link org.pac4j.oauth.client.BaiduClient}.
 *
 * <p>The class is essentially a thin, typed view over the raw JSON payload
 * returned by Baidu's {@code users/getInfo} endpoint; every getter simply
 * delegates to {@link org.pac4j.core.profile.CommonProfile#getAttribute(String)}
 * using the keys declared on {@link BaiduProfileDefinition}.</p>
 *
 * <p>Most getters may return {@code null} for optional fields Baidu left
 * empty. The user-visible display name, username and first-name are all
 * mapped from Baidu's {@code realname}/{@code username} attributes; this
 * matches the conventions documented at
 * <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth/rest/file_data_apis_list">Baidu REST file APIs</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20Profile
 * @see BaiduProfileDefinition
 */
public class BaiduProfile extends OAuth20Profile {

    private static final long serialVersionUID = 1L;

    /**
     * The user's real name ({@code realname}); may be {@code null} when the
     * user did not disclose it.
     *
     * @return the display name, or {@code null}.
     */
    /* 用户真实姓名，可能为空。 */
    @Override
    public String getDisplayName() {
        return (String) getAttribute(BaiduProfileDefinition.REAL_NAME);
    }

    /**
     * The login username ({@code username}); may be {@code null}.
     *
     * @return the Baidu username, or {@code null}.
     */
    /* 当前登录用户的用户名，值可能为空。 */
    @Override
    public String getUsername() {
        return (String) getAttribute(BaiduProfileDefinition.USER_NAME);
    }

    /**
     * Real name fallback used by pac4j's first-name contract; identical to
     * {@link #getDisplayName()}.
     *
     * @return the real name, or {@code null}.
     */
    /* 用户真实姓名，可能为空。 */
    @Override
    public String getFirstName() {
        return (String) getAttribute(BaiduProfileDefinition.REAL_NAME);
    }

    /**
     * The user's small portrait (avatar). Baidu assembles the URL using the
     * {@code portrait_small_url} template from {@link BaiduProfileDefinition}.
     *
     * @return the small portrait URI, or {@code null} when unavailable.
     */
    /*
     * 当前登录用户的头像 头像的地址具体如下： small image:
     * http://tb.himg.baidu.com/sys/portraitn/item/{$portrait} large image:
     * http://tb.himg.baidu.com/sys/portrait/item/{$portrait}
     */
    @Override
    public URI getPictureUrl() {
        return (URI) getAttribute(BaiduProfileDefinition.PORTRAIT_SMALL_URL);
    }

    /**
     * Returns the larger (180 px-class) portrait URI.
     *
     * @return the large portrait URI, or {@code null} when unavailable.
     */
    public URI getPortraitLargeUrl() {
        return (URI) getAttribute(BaiduProfileDefinition.PORTRAIT_LARGE_URL);
    }

    /**
     * Short user bio ({@code userdetail}); may be {@code null}.
     *
     * @return the bio text, or {@code null}.
     */
    /* 自我简介，可能为空。 */
    public String getUserdetail() {
        return (String) getAttribute(BaiduProfileDefinition.USER_DETAIL);
    }

    /**
     * Birthday formatted as {@code yyyy-mm-dd}.
     *
     * @return the birthday string, or {@code null}.
     */
    /* 生日，以yyyy-mm-dd格式显示。 */
    public String getBirthday() {
        return (String) getAttribute(BaiduProfileDefinition.BIRTHDAY);
    }

    /**
     * Marital status string as defined by Baidu.
     *
     * @return the marriage description, or {@code null}.
     */
    /* 婚姻状况 */
    public String getMarriage() {
        return (String) getAttribute(BaiduProfileDefinition.MARRIAGE);
    }

    /**
     * Normalised gender via {@link BaiduGenderConverter}.
     *
     * @return the {@link Gender}, or {@code null}.
     */
    /* 用户性别 */
    @Override
    public Gender getGender() {
        return (Gender) getAttribute(BaiduProfileDefinition.SEX);
    }

    /**
     * Blood type ({@code blood}); may be {@code null}.
     *
     * @return the blood type string, or {@code null}.
     */
    /* 血型 */
    public String getBlood() {
        return (String) getAttribute(BaiduProfileDefinition.BLOOD);
    }

    /**
     * Body figure ({@code figure}); may be {@code null}.
     *
     * @return the figure string, or {@code null}.
     */
    /* 体型 */
    public String getFigure() {
        return (String) getAttribute(BaiduProfileDefinition.FIGURE);
    }

    /**
     * Astrological constellation ({@code constellation}); may be {@code null}.
     *
     * @return the constellation string, or {@code null}.
     */
    /* 星座 */
    public String getConstellation() {
        return (String) getAttribute(BaiduProfileDefinition.CONSTELLATION);
    }

    /**
     * Highest education ({@code education}); may be {@code null}.
     *
     * @return the education string, or {@code null}.
     */
    /* 学历 */
    public String getEducation() {
        return (String) getAttribute(BaiduProfileDefinition.EDUCATION);
    }

    /**
     * Current profession ({@code trade}); may be {@code null}.
     *
     * @return the trade string, or {@code null}.
     */
    /* 当前职业 */
    public String getTrade() {
        return (String) getAttribute(BaiduProfileDefinition.TRADE);
    }

    /**
     * Job title ({@code job}); may be {@code null}.
     *
     * @return the job string, or {@code null}.
     */
    /* 职位 */
    public String getJob() {
        return (String) getAttribute(BaiduProfileDefinition.JOB);
    }

}