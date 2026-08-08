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

import org.pac4j.oauth.profile.baidu.BaiduProfileCreator;
import org.pac4j.oauth.profile.baidu.BaiduProfileDefinition;
import org.pac4j.scribe.builder.api.BaiduApi20;

import java.util.ArrayList;
import java.util.List;

/**
 * Pac4j {@link org.pac4j.oauth.client.OAuth20Client} implementation that
 * authenticates users against the Baidu Open Platform using OAuth 2.0.
 *
 * <p>It produces a {@link org.pac4j.oauth.profile.baidu.BaiduProfile} via
 * {@link BaiduProfileCreator} after the OAuth dance completes. The endpoint
 * configuration, scope assembly and creator wiring happen in
 * {@link #internalInit(boolean)} so that callers only need to supply a
 * {@code key}/{@code secret} pair (or rely on configuration-time injection).</p>
 *
 * <p>More info at: <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization">Baidu OAuth authorization docs</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see org.pac4j.oauth.client.OAuth20Client
 * @see org.pac4j.oauth.profile.baidu.BaiduProfile
 * @see org.pac4j.scribe.builder.api.BaiduApi20
 */
public class BaiduClient extends OAuth20Client {

    /**
     * Enumerates the OAuth scopes understood by Baidu's authorization server.
     */
    public enum BaiduScope {
        /**
         * QR-code based login that returns the user's nickname, avatar and
         * gender. Equivalent to the historical WeChat {@code snsapi_login}.
         */
        SNSAPI_LOGIN,
        /**
         * Base scope that exchanges the authorization code for an
         * {@code access_token}, {@code refresh_token} and the granted scope.
         */
        SNSAPI_BASE,
        /**
         * Extended scope that retrieves the user's personal information
         * (requires the user's explicit consent).
         */
        SNSAPI_USERINFO
    }

    /**
     * The ordered list of scopes requested during the OAuth handshake. When
     * left empty the client defaults to {@link BaiduScope#SNSAPI_BASE}.
     */
    protected List<BaiduScope> scopes;

    /**
     * Default no-argument constructor used by reflective configuration
     * frameworks (Spring, JFinal, etc.).
     */
    public BaiduClient() {
    }

    /**
     * Convenience constructor that pre-populates the OAuth key/secret pair.
     *
     * @param key    the Baidu API key (a.k.a. {@code client_id}); may be
     *               {@code null} and supplied later via configuration.
     * @param secret the Baidu API secret (a.k.a. {@code client_secret}); may
     *               be {@code null} and supplied later via configuration.
     */
    public BaiduClient(final String key, final String secret) {
        setKey(key);
        setSecret(secret);
    }

    /**
     * Initialises the underlying pac4j configuration: installs the Baidu
     * Scribe {@link BaiduApi20} endpoint, computes the scope string, registers
     * the {@link BaiduProfileDefinition} and finally the
     * {@link BaiduProfileCreator}.
     *
     * @param forceReinit when {@code true}, configuration objects are
     *                    rebuilt even if they already exist; required by the
     *                    pac4j lifecycle contract.
     */
    @Override
    protected void internalInit(final boolean forceReinit) {
        super.internalInit(forceReinit);
        configuration.setApi(BaiduApi20.instance());
        configuration.setScope(getOAuthScope());
        configuration.setProfileDefinition(new BaiduProfileDefinition());
        configuration.setWithState(true);
        setProfileCreatorIfUndefined(new BaiduProfileCreator(configuration, this));
    }

    /**
     * Builds the comma-separated OAuth scope string sent to the Baidu
     * authorization endpoint.
     *
     * <p>When {@link #scopes} is {@code null} or empty the client falls back
     * to {@link BaiduScope#SNSAPI_BASE}; otherwise each entry is rendered in
     * lowercase and joined with commas.</p>
     *
     * @return the scope string, or {@code null} only if no scopes could be
     *         resolved (which is not expected because of the fallback).
     */
    protected String getOAuthScope() {
        StringBuilder builder = null;
        if (scopes == null || scopes.isEmpty()) {
            scopes = new ArrayList<>();
            scopes.add(BaiduScope.SNSAPI_BASE);
        }
        if (scopes != null) {
            for (BaiduScope value : scopes) {
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
     * Returns the currently configured scopes; never {@code null} after
     * {@link #internalInit(boolean)} has run because the method ensures a
     * default list.
     *
     * @return the mutable scope list owned by this client.
     */
    public List<BaiduScope> getScopes() {
        return scopes;
    }

    /**
     * Replaces the scope list with the supplied one.
     *
     * @param scopes the new scope list; may be {@code null}, in which case
     *               the next call to {@link #internalInit(boolean)} will
     *               default back to {@link BaiduScope#SNSAPI_BASE}.
     */
    public void setScopes(List<BaiduScope> scopes) {
        this.scopes = scopes;
    }

    /**
     * Appends a single scope to the existing list, lazily creating the list
     * if it has not been initialised yet.
     *
     * @param scopes the scope to add; must not be {@code null}.
     */
    public void addScope(BaiduScope scopes) {
        if (this.scopes == null) {
            this.scopes = new ArrayList<>();
        }
        this.scopes.add(scopes);
    }

}