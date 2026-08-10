/*
 * Copyright (c) 2018, Loong Wan (https://github.com/loong10k).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.pac4j.scribe.extractors;

import org.pac4j.scribe.model.YibanToken;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Specialised {@link OAuth2AccessTokenJsonExtractor} that parses the YiBan
 * token response and extracts the {@code userid} field into a
 * {@link YibanToken}.
 *
 * <p>YiBan's token response includes a {@code userid} field that identifies
 * the authenticated user; this value is not part of the standard OAuth 2.0
 * token schema, so a custom extractor is required.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see OAuth2AccessTokenJsonExtractor
 * @see YibanToken
 */
public class YibanJsonExtractor extends OAuth2AccessTokenJsonExtractor {

	private static final String USERID_REGEX_PATTERN = "userid";
    private static final String EXPIRES_REGEX_PATTERN = "expires";

    /**
     * Protected constructor; use {@link #instance()} to obtain the singleton.
     */
    protected YibanJsonExtractor() {
    }

    private static class InstanceHolder {

        private static final YibanJsonExtractor INSTANCE = new YibanJsonExtractor();
    }

    /**
     * Returns the lazily-initialised singleton instance.
     *
     * @return the shared {@link YibanJsonExtractor} instance; never {@code null}.
     */
    public static YibanJsonExtractor instance() {
        return YibanJsonExtractor.InstanceHolder.INSTANCE;
    }

    /**
     * Creates a {@link YibanToken} from the parsed JSON response, extracting
     * the {@code userid} and {@code expires} fields in addition to the standard
     * OAuth 2.0 token attributes.
     *
     * @param accessToken  the access token string.
     * @param tokenType    the token type (e.g. {@code "bearer"}).
     * @param expiresIn    the token lifetime in seconds; may be {@code null}.
     * @param refreshToken the refresh token; may be {@code null}.
     * @param scope        the granted scope; may be {@code null}.
     * @param response     the parsed JSON response node.
     * @param rawResponse  the raw HTTP response body.
     * @return a {@link YibanToken} populated with the YiBan-specific fields.
     */
    @Override
    protected OAuth2AccessToken createToken(String accessToken, String tokenType, Integer expiresIn,
		String refreshToken, String scope, JsonNode response, String rawResponse) {
	 String userid = extractRequiredParameter(response, USERID_REGEX_PATTERN, rawResponse).asText();
         String unionid = extractRequiredParameter(response, EXPIRES_REGEX_PATTERN, rawResponse).asText();;
         YibanToken token = new YibanToken(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse, userid);
         return token;
    }

}
