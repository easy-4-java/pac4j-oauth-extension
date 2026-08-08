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

import java.util.Optional;

import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;
import org.pac4j.scribe.model.YibanToken;

import com.github.scribejava.core.model.Token;

/**
 * Profile creator used by {@link org.pac4j.oauth.client.YibanClient} that
 * injects the YiBan {@code userid} carried by {@link YibanToken} into the
 * resulting {@link YibanProfile}.
 *
 * <p>YiBan encodes the user identifier inside the OAuth token itself rather
 * than in the user-info payload, so the standard pac4j flow has to be
 * subclassed to copy that value across.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20ProfileCreator
 * @see YibanToken
 */
public class YibanProfileCreator extends OAuth20ProfileCreator {

    /**
     * Standard pac4j creator constructor.
     *
     * @param configuration the OAuth 2.0 configuration used to sign requests.
     * @param client        the indirect client that initiated the flow.
     */
    public YibanProfileCreator(OAuth20Configuration configuration,
                                IndirectClient client) {
        super(configuration, client);
    }

    /**
     * Retrieves the {@link YibanProfile} associated with the supplied
     * {@link YibanToken} and copies the {@code userid} from the token onto
     * the profile (YiBan uses it as the canonical user identifier).
     *
     * @param context     the current web context.
     * @param accessToken the {@link YibanToken} issued by YiBan.
     * @return an {@link Optional} wrapping the populated
     *         {@link YibanProfile}; never {@code null}.
     */
    @Override
    protected Optional<UserProfile> retrieveUserProfileFromToken(final WebContext context, final Token accessToken) {
        final YibanToken token = (YibanToken) accessToken;
        final Optional<UserProfile> profile = super.retrieveUserProfileFromToken(context, token);
        ((YibanProfile) profile.get()).setId(token.getUserid());
        return profile;
    }
}