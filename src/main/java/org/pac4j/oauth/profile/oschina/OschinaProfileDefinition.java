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

import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.Token;

/**
 * Profile definition that maps the JSON payload returned by OSChina's
 * {@code action/openapi/user} endpoint onto an {@link OschinaProfile}.
 *
 * <p>More info at: <a href="http://www.oschina.net/openapi/docs/openapi_user">OSChina OpenAPI user docs</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see OschinaProfile
 * @see org.pac4j.oauth.profile.definition.OAuthProfileDefinition
 */
public class OschinaProfileDefinition extends OAuthProfileDefinition {

    private static final long serialVersionUID = 1L;

    /**
     * Template URL used to fetch the OSChina user-info payload, with the
     * {@code %s} placeholder substituted by the raw access-token response.
     */
    public static final String PROFILE_URL = "https://www.oschina.net/action/openapi/user?access_token=%s";
    /** Numeric user identifier key. */
    public static final String ID = "id";
    /** Display name / login name key. */
    public static final String NAME = "name";
    /** Email address key. */
    public static final String EMAIL = "email";
    /** Gender string key. */
    public static final String GENDER = "gender";
    /** Free-form location key. */
    public static final String LOCATION = "location";
    /** Avatar URL key. */
    public static final String AVATAR_URL = "avatar";
    /** Canonical OSChina profile URL key. */
    public static final String URL = "url";

    /**
     * Default constructor; declares every primary attribute and the
     * converters used to normalise them.
     */
    public OschinaProfileDefinition() {
        super();
        primary(ID, Converters.STRING);
        primary(NAME, Converters.STRING);
        primary(EMAIL, Converters.STRING);
        primary(GENDER, Converters.STRING);
        primary(LOCATION, Converters.STRING);
        primary(AVATAR_URL, Converters.URL);
        primary(URL, Converters.URL);
    }

    /**
     * Composes the user-info URL using OSChina's documented template.
     *
     * @param accessToken   the OAuth access token issued by OSChina.
     * @param configuration the pac4j OAuth configuration (unused for
     *                      OSChina but required by the contract).
     * @return the formatted URL with the raw access-token response substituted.
     */
    @Override
    public String getProfileUrl(Token accessToken, OAuthConfiguration configuration) {
        return String.format(PROFILE_URL, accessToken.getRawResponse());
    }

    /**
     * Extracts an {@link OschinaProfile} from the raw JSON payload returned
     * by the OSChina user-info endpoint.
     *
     * <p>Sample success payload:</p>
     * <pre>
     * {
     *   "id": 899**,
     *   "email": "****@gmail.com",
     *   "name": "彭博",
     *   "gender": "male",
     *   "avatar": "http://www.oschina.net/uploads/user/****",
     *   "location": "广东 深圳",
     *   "url": "http://home.oschina.net/****"
     * }
     * </pre>
     *
     * <p>Sample failure payload:</p>
     * <pre>
     * {
     *   "error": "invalid_token",
     *   "error_description": "Invalid access token: 7fade311-d844-4159-9890-c8f0511337e5"
     * }
     * </pre>
     *
     * @param body the raw response body from OSChina.
     * @return a fully populated {@link OschinaProfile} (never {@code null}).
     * @throws org.pac4j.core.exception.TechnicalException when the payload
     *         cannot be parsed or OSChina signals an error.
     */
    @Override
    public OschinaProfile extractUserProfile(String body) {
        final OschinaProfile profile = new OschinaProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null && JsonHelper.getElement(json, "error") == null) {
            profile.setId(JsonHelper.getElement(json, ID).toString());
            for (final Object attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, attribute.toString(), JsonHelper.getElement(json, attribute.toString()));
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }
}