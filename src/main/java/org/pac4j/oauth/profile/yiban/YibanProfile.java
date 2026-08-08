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


import java.net.URI;
import java.util.Locale;

import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.profile.OAuth20Profile;
import org.pac4j.oauth.profile.weibo.WeiboProfileDefinition;

/**
 * Strongly-typed user profile returned by {@link org.pac4j.oauth.client.YibanClient}.
 *
 * <p>Because YiBan's user-info payload mirrors Sina Weibo's, the getters
 * here reuse {@link WeiboProfileDefinition} attribute keys. Relative
 * {@code profile_url} values are prefixed with the YiBan OpenAPI base URL
 * so callers always see an absolute {@link URI}.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20Profile
 * @see YibanProfileDefinition
 * @see WeiboProfileDefinition
 */
public class YibanProfile extends OAuth20Profile {

    private static final long serialVersionUID = -7486869356444327783L;

    /**
     * Returns the user's friendly display name sourced from Weibo's
     * {@code name} attribute.
     *
     * @return the first name, or {@code null}.
     */
    @Override
    public String getFirstName() {
        return (String) getAttribute(YibanProfileDefinition.NAME);
    }

    /**
     * Returns the YiBan display name, sourced from Weibo's
     * {@code screen_name} attribute.
     *
     * @return the display name, or {@code null}.
     */
    @Override
    public String getDisplayName() {
        return (String) getAttribute(WeiboProfileDefinition.SCREEN_NAME);
    }

    /**
     * Returns the YiBan login handle, sourced from Weibo's
     * {@code screen_name} attribute.
     *
     * @return the username, or {@code null}.
     */
    @Override
    public String getUsername() {
        return (String) getAttribute(WeiboProfileDefinition.SCREEN_NAME);
    }

    /**
     * Returns the user's language preference as a {@link Locale}.
     *
     * @return the locale, or {@code null}.
     */
    @Override
    public Locale getLocale() {
        return (Locale) getAttribute(WeiboProfileDefinition.LANG);
    }

    /**
     * Returns the URL of the user's HD avatar.
     *
     * @return the avatar URI, or {@code null}.
     */
    @Override
    public URI getPictureUrl() {
        return (URI) getAttribute(WeiboProfileDefinition.AVATAR_HD);
    }

    /**
     * Returns the canonical YiBan profile URL.
     *
     * <p>If the underlying {@code profile_url} value is a relative URI, the
     * method prefixes it with {@code https://openapi.yiban.cn/} so that the
     * caller always observes an absolute {@link URI}.</p>
     *
     * @return the absolute profile URI, or {@code null} when the underlying
     *         attribute is missing.
     */
    @Override
    public URI getProfileUrl() {
        final URI attribute = (URI) getAttribute(WeiboProfileDefinition.PROFILE_URL);
        if (attribute.isAbsolute()) {
            return attribute;
        } else {
            return CommonHelper.asURI("https://openapi.yiban.cn/" + attribute);
        }
    }
}