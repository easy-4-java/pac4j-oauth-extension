package org.pac4j.scribe.builder.api;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;

/**
 * ScribeJava {@link DefaultApi20} implementation for the Baidu OAuth 2.0
 * authorization server.
 *
 * <p>Provides the Baidu-specific authorize and access-token endpoints as well
 * as the standard {@link OAuth2AccessTokenExtractor} for parsing the token
 * response. The access token is retrieved via HTTP GET.</p>
 *
 * <p>More info at:
 * <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization">Baidu OAuth authorization docs</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see DefaultApi20
 * @see org.pac4j.oauth.client.BaiduClient
 */
public class BaiduApi20 extends DefaultApi20 {

	/** Baidu OAuth 2.0 authorization endpoint URL. */
	public static final String AUTHORIZE_URL = "http://openapi.baidu.com/oauth/2.0/authorize";
	/** Baidu OAuth 2.0 access-token endpoint URL. */
	public static final String ACCESS_TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token";

	/**
	 * Protected constructor; use {@link #instance()} to obtain the singleton.
	 */
	protected BaiduApi20() {
	}

	private static class InstanceHolder {
		private static final BaiduApi20 INSTANCE = new BaiduApi20();
	}

	/**
	 * Returns the lazily-initialised singleton instance.
	 *
	 * @return the shared {@link BaiduApi20} instance; never {@code null}.
	 */
	public static BaiduApi20 instance() {
		return InstanceHolder.INSTANCE;
	}

	/**
	 * Baidu uses HTTP GET to retrieve the access token.
	 *
	 * @return {@link Verb#GET}.
	 */
	@Override
	public Verb getAccessTokenVerb() {
		return Verb.GET;
	}

	/**
	 * Returns the Baidu access-token endpoint URL.
	 *
	 * @return the access-token URL; never {@code null}.
	 */
	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	/**
	 * Returns the Baidu authorization base URL.
	 *
	 * @return the authorization URL; never {@code null}.
	 */
	@Override
	protected String getAuthorizationBaseUrl() {
		return AUTHORIZE_URL;
	}

	/**
	 * Returns the standard ScribeJava JSON-based access-token extractor.
	 *
	 * @return the {@link OAuth2AccessTokenExtractor} instance; never {@code null}.
	 */
	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		return OAuth2AccessTokenExtractor.instance();
	}

}
