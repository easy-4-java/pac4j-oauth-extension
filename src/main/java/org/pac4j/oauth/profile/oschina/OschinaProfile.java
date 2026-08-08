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
package org.pac4j.oauth.profile.oschina;

import java.net.URI;

import org.pac4j.oauth.profile.OAuth20Profile;


/**
 * Strongly-typed user profile returned by {@link org.pac4j.oauth.client.OschinaClient}.
 *
 * <p>Every getter simply delegates to
 * {@link org.pac4j.core.profile.CommonProfile#getAttribute(String)} using the
 * keys declared on {@link OschinaProfileDefinition}. Several attributes map to
 * the same source field (the {@code name} attribute serves as the user's
 * display name, login name and first name) because OSChina's profile model
 * is more compact than Baidu's.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20Profile
 * @see OschinaProfileDefinition
 */
public class OschinaProfile extends OAuth20Profile {

    private static final long serialVersionUID = 1L;

    /**
     * Display name fallback, sourced from the {@code name} field.
     *
     * @return the display name, or {@code null}.
     */
    @Override
    public String getDisplayName() {
        return (String) getAttribute(OschinaProfileDefinition.NAME);
    }

    /**
     * Login username fallback, also sourced from {@code name}.
     *
     * @return the username, or {@code null}.
     */
    @Override
    public String getUsername() {
        return (String) getAttribute(OschinaProfileDefinition.NAME);
    }

    /**
     * First-name fallback, sourced from {@code name}.
     *
     * @return the first name, or {@code null}.
     */
    @Override
    public String getFirstName() {
        return (String) getAttribute(OschinaProfileDefinition.NAME);
    }

    /**
     * Returns the user's avatar URL.
     *
     * @return the avatar URI, or {@code null}.
     */
    @Override
    public URI getPictureUrl() {
        return (URI) getAttribute(OschinaProfileDefinition.AVATAR_URL);
    }

    /**
     * Returns the canonical OSChina profile URL for the user.
     *
     * @return the profile URL, or {@code null}.
     */
    @Override
    public URI getProfileUrl() {
        return (URI) getAttribute(OschinaProfileDefinition.URL);
    }

}