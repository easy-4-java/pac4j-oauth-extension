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

import org.pac4j.oauth.profile.yiban.YibanProfile;
import org.pac4j.oauth.profile.yiban.YibanProfileCreator;
import org.pac4j.oauth.profile.yiban.YibanProfileDefinition;
import org.pac4j.scribe.builder.api.YibanApi20;

/**
 * Pac4j {@link org.pac4j.oauth.client.OAuth20Client} implementation that
 * authenticates users against the YiBan (易班) OpenAPI using OAuth 2.0.
 *
 * <p>The client returns a {@link YibanProfile} populated by
 * {@link YibanProfileCreator}. The {@link YibanApi20} helper is responsible
 * for the actual HTTP exchange, including the bespoke YiBan
 * {@code appid}/{@code secret} client authentication scheme.</p>
 *
 * <p>More info at: <a href="https://open.yiban.cn/wiki/index.php?page=oauth/authorize">YiBan OAuth authorization docs</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see org.pac4j.oauth.client.OAuth20Client
 * @see org.pac4j.oauth.profile.yiban.YibanProfile
 * @see org.pac4j.scribe.builder.api.YibanApi20
 */
public class YibanClient extends OAuth20Client {

    /**
     * Enumerates the OAuth scopes recognised by the YiBan authorization
     * endpoint.
     */
    public enum YibanScope {
        /**
         * QR-code login scope; returns the user's nickname, avatar and
         * gender.
         */
        SNSAPI_LOGIN,
        /**
         * Base scope that exchanges the authorization code for an
         * {@code access_token}, {@code refresh_token} and granted scope.
         */
        SNSAPI_BASE,
        /**
         * Extended scope that retrieves the user's personal information.
         */
        SNSAPI_USERINFO
    }

    /**
     * The scopes requested during the OAuth handshake. Defaults to
     * {@link YibanScope#SNSAPI_BASE} when left empty.
     */
    protected List<YibanScope> scopes;

    /**
     * Default no-argument constructor used by reflective configuration
     * frameworks.
     */
    public YibanClient() {
    }

    /**
     * Convenience constructor that seeds the OAuth key/secret pair.
     *
     * @param key    the YiBan API key (a.k.a. {@code client_id} /
     *               {@code appid}); may be {@code null} and supplied later.
     * @param secret the YiBan API secret; may be {@code null} and supplied
     *               later.
     */
    public YibanClient(final String key, final String secret) {
        setKey(key);
        setSecret(secret);
    }

    /**
     * Initialises the underlying pac4j configuration: registers the
     * {@link YibanApi20} endpoint, scope string,
     * {@link YibanProfileDefinition} and {@link YibanProfileCreator}.
     *
     * @param forceReinit when {@code true}, configuration objects are
     *                    rebuilt even if they already exist.
     */
    @Override
    protected void internalInit(final boolean forceReinit) {
        super.internalInit(forceReinit);
        configuration.setApi(YibanApi20.instance());
        configuration.setScope(getOAuthScope());
        configuration.setProfileDefinition(new YibanProfileDefinition());
        configuration.setWithState(true);
        setProfileCreatorIfUndefined(new YibanProfileCreator(configuration, this));
    }

    /**
     * Builds the comma-separated scope string sent to the YiBan
     * authorization endpoint, defaulting to
     * {@link YibanScope#SNSAPI_BASE} when no scope has been configured.
     *
     * @return the scope string, or {@code null} if no scopes can be resolved.
     */
    protected String getOAuthScope() {
        StringBuilder builder = null;
        if (scopes == null || scopes.isEmpty()) {
            scopes = new ArrayList<>();
            scopes.add(YibanScope.SNSAPI_BASE);
        }
        if (scopes != null) {
            for (YibanScope value : scopes) {
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
    public List<YibanScope> getScopes() {
        return scopes;
    }

    /**
     * Replaces the scope list with the supplied one.
     *
     * @param scopes the new scope list; may be {@code null}, in which case
     *               {@link #internalInit(boolean)} will fall back to
     *               {@link YibanScope#SNSAPI_BASE}.
     */
    public void setScopes(List<YibanScope> scopes) {
        this.scopes = scopes;
    }

    /**
     * Appends a single scope to the existing list, lazily creating the list
     * if it has not been initialised yet.
     *
     * @param scopes the scope to add; must not be {@code null}.
     */
    public void addScope(YibanScope scopes) {
        if (this.scopes == null)
            this.scopes = new ArrayList<>();
        this.scopes.add(scopes);
    }
}