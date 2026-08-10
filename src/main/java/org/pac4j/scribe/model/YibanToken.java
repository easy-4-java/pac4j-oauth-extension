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
package org.pac4j.scribe.model;

import org.pac4j.core.util.CommonHelper;

import com.github.scribejava.core.model.OAuth2AccessToken;
import org.pac4j.util.MyCommonHelper;

/**
 * Extended {@link OAuth2AccessToken} that carries the YiBan-specific
 * {@code userid} field returned alongside the access token.
 *
 * <p>YiBan embeds the authenticated user's identifier inside the token
 * response rather than in the user-info payload. This class captures that
 * value so that {@link org.pac4j.oauth.profile.yiban.YibanProfileCreator}
 * can copy it onto the resulting profile.</p>
 *
 * <p>More info at:
 * <a href="https://open.yiban.cn/wiki/index.php?page=oauth/access_token">YiBan access_token</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see OAuth2AccessToken
 * @see org.pac4j.scribe.extractors.YibanJsonExtractor
 */
public class YibanToken extends OAuth2AccessToken {

    private static final long serialVersionUID = -4657457530761699382L;
    private String userid;
    private Integer expires;

    /**
     * Constructs a new YiBan token with all standard OAuth 2.0 fields plus
     * the YiBan-specific user identifier.
     *
     * @param accessToken  the access token string; must not be {@code null}.
     * @param tokenType    the token type (e.g. {@code "bearer"}).
     * @param expiresIn    the token lifetime in seconds; may be {@code null}.
     * @param refreshToken the refresh token; may be {@code null}.
     * @param scope        the granted scope; may be {@code null}.
     * @param rawResponse  the raw HTTP response body.
     * @param userid       the YiBan user identifier; must not be {@code null}.
     */
    public YibanToken(String accessToken, String tokenType, Integer expiresIn,
                       String refreshToken, String scope, String rawResponse,
                       String userid) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
        this.userid = userid;
        this.expires = expiresIn;
    }

    /**
     * Returns the YiBan user identifier associated with this token.
     *
     * @return the user ID string; never {@code null} for properly-issued tokens.
     */
    public String getUserid() {
		return userid;
	}

	/**
	 * Replaces the YiBan user identifier.
	 *
	 * @param userid the new user ID; must not be {@code null}.
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * Returns the token expiry duration in seconds.
	 *
	 * @return the expiry value, or {@code null} if not provided.
	 */
	public Integer getExpires() {
		return expires;
	}

	/**
	 * Replaces the token expiry duration.
	 *
	 * @param expires the new expiry value in seconds; may be {@code null}.
	 */
	public void setExpires(Integer expires) {
		this.expires = expires;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        YibanToken that = (YibanToken) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return expires != null ? expires.equals(that.expires) : that.expires == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (expires != null ? expires.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MyCommonHelper.toNiceString(YibanToken.class, "accessToken", getAccessToken(), "userid", userid);
    }

}
