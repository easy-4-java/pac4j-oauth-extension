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
package org.pac4j.oauth.client;

import java.util.ArrayList;
import java.util.List;

import org.pac4j.oauth.profile.oschina.OschinaProfileCreator;
import org.pac4j.oauth.profile.oschina.OschinaProfileDefinition;
import org.pac4j.scribe.builder.api.OschinaApi20;

/**
 * Pac4j {@link org.pac4j.oauth.client.OAuth20Client} implementation for the
 * OSChina (开源中国) OpenAPI, backed by OAuth 2.0.
 *
 * <p>The client delegates endpoint discovery to {@link OschinaApi20} and
 * returns a {@link org.pac4j.oauth.profile.oschina.OschinaProfile} populated
 * by {@link OschinaProfileCreator}. The client also requests JSON responses
 * from the user-info endpoint.</p>
 *
 * <p>More info at: <a href="http://www.oschina.net/openapi/docs/openapi_user">OSChina OpenAPI docs</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see org.pac4j.oauth.client.OAuth20Client
 * @see org.pac4j.oauth.profile.oschina.OschinaProfile
 * @see org.pac4j.scribe.builder.api.OschinaApi20
 */
public class OschinaClient extends OAuth20Client {

    /**
     * Enumerates the OAuth scopes recognised by the OSChina authorization
     * endpoint.
     */
    public enum OschinaScope {
        /**
         * QR-code login scope; returns the user's nickname, avatar and
         * gender.
         */
        SNSAPI_LOGIN,
        /**
         * Base scope that exchanges the authorization code for an
         * {@code access_token} and {@code refresh_token}.
         */
        SNSAPI_BASE,
        /**
         * Extended scope that retrieves the user's personal information.
         */
        SNSAPI_USERINFO
    }

    /**
     * The scopes requested during the OAuth handshake. Defaults to
     * {@link OschinaScope#SNSAPI_BASE} when left empty.
     */
    protected List<OschinaScope> scopes;

    /**
     * Default no-argument constructor used by reflective configuration
     * frameworks.
     */
    public OschinaClient() {
    }

    /**
     * Convenience constructor that seeds the OAuth key/secret pair.
     *
     * @param key    the OSChina API key; may be {@code null} and supplied
     *               later via configuration.
     * @param secret the OSChina API secret; may be {@code null} and supplied
     *               later via configuration.
     */
    public OschinaClient(final String key, final String secret) {
        setKey(key);
        setSecret(secret);
    }

    /**
     * Initialises the underlying pac4j configuration: registers the
     * {@link OschinaApi20} endpoint, the scope string, the
     * {@link OschinaProfileDefinition} and the {@link OschinaProfileCreator}.
     *
     * @param forceReinit when {@code true}, configuration objects are
     *                    rebuilt even if they already exist.
     */
    @Override
    protected void internalInit(final boolean forceReinit) {
        super.internalInit(forceReinit);
        configuration.setApi(OschinaApi20.instance());
        configuration.setScope(getOAuthScope());
        configuration.setProfileDefinition(new OschinaProfileDefinition());
        configuration.setWithState(true);
        setProfileCreatorIfUndefined(new OschinaProfileCreator(configuration, this));
    }

    /**
     * Builds the comma-separated scope string sent to the OSChina
     * authorization endpoint, defaulting to
     * {@link OschinaScope#SNSAPI_BASE} when no scope has been configured.
     *
     * @return the scope string, or {@code null} if no scopes can be resolved.
     */
    protected String getOAuthScope() {
        StringBuilder builder = null;
        if (scopes == null || scopes.isEmpty()) {
            scopes = new ArrayList<>();
            scopes.add(OschinaScope.SNSAPI_BASE);
        }
        if (scopes != null) {
            for (OschinaScope value : scopes) {
                if (builder == null) {
                    builder = new StringBuilder();
                } else {
                    builder.append(",");
                }
                builder.append(value.toString().toLowerCase());
            }
        }
        return builder == null ? null : builder.toString();
    }

    /**
     * Returns the currently configured scopes. Never {@code null} after
     * {@link #internalInit(boolean)} has executed.
     *
     * @return the mutable scope list owned by this client.
     */
    public List<OschinaScope> getScopes() {
        return scopes;
    }

    /**
     * Replaces the scope list with the supplied one.
     *
     * @param scopes the new scope list; may be {@code null}, in which case
     *               {@link #internalInit(boolean)} will fall back to
     *               {@link OschinaScope#SNSAPI_BASE}.
     */
    public void setScopes(List<OschinaScope> scopes) {
        this.scopes = scopes;
    }

    /**
     * Appends a single scope to the existing list, lazily creating the list
     * if it has not been initialised yet.
     *
     * @param scopes the scope to add; must not be {@code null}.
     */
    public void addScope(OschinaScope scopes) {
        if (this.scopes == null)
            this.scopes = new ArrayList<>();
        this.scopes.add(scopes);
    }

}