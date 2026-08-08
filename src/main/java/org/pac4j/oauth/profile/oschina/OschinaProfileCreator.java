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

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;

/**
 * Specialisation of {@link OAuth20ProfileCreator} used by
 * {@link org.pac4j.oauth.client.OschinaClient} that augments the signed
 * user-info request with the {@code dataType=json} query parameter expected
 * by OSChina.
 *
 * <p>The creator is otherwise identical to its parent; the only behavioural
 * difference is the query-string injection performed in
 * {@link #signRequest(OAuthService, Token, OAuthRequest)}.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20ProfileCreator
 */
public class OschinaProfileCreator extends OAuth20ProfileCreator {

    /**
     * Standard pac4j creator constructor.
     *
     * @param configuration the OAuth 2.0 configuration used to sign requests.
     * @param client        the indirect client that initiated the flow.
     */
    public OschinaProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

    /**
     * Signs the user-info request and adds OSChina-specific query parameters.
     *
     * @param service  the OAuth service used to compute the signature.
     * @param token    the access token issued by OSChina.
     * @param request  the outgoing user-info request that will be signed.
     */
    @Override
    protected void signRequest(OAuthService service, Token token, OAuthRequest request) {
        super.signRequest(service, token, request);
        // 指定返回值类型['json'|'jsonp'|'xml']
        request.addQuerystringParameter("dataType", "json");
    }

}