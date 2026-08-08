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
package org.pac4j.scribe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.pac4j.scribe.builder.api.YibanApi20;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthAsyncRequestCallback;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * Custom {@link OAuth20Service} for YiBan that injects {@code appid} and
 * {@code secret} request-body parameters into every OAuth request.
 *
 * <p>As of ScribeJava 5.3, the {@code ClientAuthenticationType} enum does
 * not support subclassing, making it impossible to implement YiBan's bespoke
 * client authentication through the standard extension mechanism. This service
 * works around that limitation by intercepting the {@code execute} methods.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see OAuth20Service
 * @see org.pac4j.scribe.builder.api.YibanApi20
 */
public class YibanService extends OAuth20Service {

    private final String apiKey;
    private final String apiSecrect;

    /**
     * Constructs a new YiBan OAuth service.
     *
     * @param api              the YiBan API descriptor.
     * @param apiKey           the YiBan application identifier ({@code appid}).
     * @param apiSecret        the YiBan application secret.
     * @param callback         the redirect URI registered with YiBan.
     * @param scope            the requested OAuth scope; may be {@code null}.
     * @param responseType     the OAuth response type (typically {@code "code"}).
     * @param debugStream      the debug output stream; may be {@code null}.
     * @param userAgent        the HTTP user-agent header value; may be {@code null}.
     * @param httpClientConfig the HTTP client configuration; may be {@code null}.
     * @param httpClient       the HTTP client implementation; may be {@code null}.
     */
    public YibanService(DefaultApi20 api, String apiKey, String apiSecret, String callback, String scope,
            String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig,
            HttpClient httpClient) {
	super(api, apiKey, apiSecret, callback, scope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
        this.apiKey = apiKey;
        this.apiSecrect = apiSecret;
    }

    /**
     * Executes an asynchronous OAuth request after injecting YiBan client
     * authentication parameters.
     *
     * @param request   the OAuth request to execute.
     * @param callback  the asynchronous callback; may be {@code null}.
     * @param converter the response converter.
     * @param <R>       the expected response type.
     * @return a {@link Future} wrapping the converted response.
     */
    @Override
    public <R> Future<R> execute(OAuthRequest request, OAuthAsyncRequestCallback<R> callback,
                                 OAuthRequest.ResponseConverter<R> converter) {
        OAuthRequest authRequest = addClientAuthentication(request);
        return super.execute(authRequest, callback, converter);
    }

    /**
     * Executes a synchronous OAuth request after injecting YiBan client
     * authentication parameters.
     *
     * @param request the OAuth request to execute.
     * @return the HTTP response; never {@code null}.
     * @throws InterruptedException  if the calling thread is interrupted.
     * @throws ExecutionException    if the request execution fails.
     * @throws IOException           if an I/O error occurs.
     */
    @Override
    public Response execute(OAuthRequest request)
        throws InterruptedException, ExecutionException, IOException {
        OAuthRequest authRequest = addClientAuthentication(request);
        return super.execute(authRequest);
    }

    /**
     * Adds the YiBan-specific {@code appid} and {@code secret} parameters to
     * the request body.
     *
     * @param request the OAuth request to augment.
     * @return the same request instance, with client-auth parameters added.
     */
    private OAuthRequest addClientAuthentication(OAuthRequest request) {
        request.addParameter(YibanApi20.APPID, this.apiKey);
        request.addParameter(YibanApi20.SECRET, this.apiSecrect);
        return request;
    }
}
