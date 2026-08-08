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

import org.pac4j.core.client.IndirectClient;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;

/**
 * Marker subclass of {@link OAuth20ProfileCreator} used by
 * {@link org.pac4j.oauth.client.BaiduClient} to extract a
 * {@link BaiduProfile} from the standard pac4j OAuth 2.0 flow.
 *
 * <p>The class deliberately inherits all default behaviour from
 * {@link OAuth20ProfileCreator}; the subtype exists purely so that the
 * configuration wiring in {@link org.pac4j.oauth.client.BaiduClient} can
 * reference a strongly-typed creator that returns {@link BaiduProfile}.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20ProfileCreator
 * @see BaiduProfile
 */
public class BaiduProfileCreator extends OAuth20ProfileCreator {

    /**
     * Standard pac4j creator constructor; both arguments are forwarded to
     * the {@link OAuth20ProfileCreator} parent.
     *
     * @param configuration the OAuth 2.0 configuration used to sign the
     *                      user-info request.
     * @param client        the indirect (web) client that initiated the
     *                      flow; may be used for callback URLs.
     */
    public BaiduProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

}