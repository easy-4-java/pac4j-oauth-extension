package org.pac4j.oauth.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.client.YibanClient.YibanScope;

/**
 * Tests for {@link YibanClient}.
 */
class YibanClientTest {

    @Test
    void shouldCreateWithDefaultConstructor() {
        YibanClient client = new YibanClient();
        assertNotNull(client);
    }

    @Test
    void shouldCreateWithKeyAndSecret() {
        YibanClient client = new YibanClient("myKey", "mySecret");
        assertEquals("myKey", client.getKey());
        assertEquals("mySecret", client.getSecret());
    }

    @Test
    void shouldCreateWithNullKeyAndSecret() {
        YibanClient client = new YibanClient(null, null);
        assertNull(client.getKey());
        assertNull(client.getSecret());
    }

    @Test
    void shouldReturnNullScopesByDefault() {
        YibanClient client = new YibanClient();
        assertNull(client.getScopes());
    }

    @Test
    void shouldSetAndGetScopes() {
        YibanClient client = new YibanClient();
        List<YibanScope> scopes = new ArrayList<>();
        scopes.add(YibanScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        assertEquals(1, client.getScopes().size());
        assertEquals(YibanScope.SNSAPI_USERINFO, client.getScopes().get(0));
    }

    @Test
    void shouldSetScopesToNull() {
        YibanClient client = new YibanClient();
        client.setScopes(new ArrayList<>());
        client.setScopes(null);
        assertNull(client.getScopes());
    }

    @Test
    void shouldAddScope() {
        YibanClient client = new YibanClient();
        client.addScope(YibanScope.SNSAPI_LOGIN);
        assertNotNull(client.getScopes());
        assertEquals(1, client.getScopes().size());
        assertEquals(YibanScope.SNSAPI_LOGIN, client.getScopes().get(0));
    }

    @Test
    void shouldAddMultipleScopes() {
        YibanClient client = new YibanClient();
        client.addScope(YibanScope.SNSAPI_BASE);
        client.addScope(YibanScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldAddScopeWhenListAlreadyExists() {
        YibanClient client = new YibanClient();
        List<YibanScope> scopes = new ArrayList<>();
        scopes.add(YibanScope.SNSAPI_BASE);
        client.setScopes(scopes);
        client.addScope(YibanScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesEmpty() {
        YibanClient client = new YibanClient();
        client.setScopes(new ArrayList<>());
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesNull() {
        YibanClient client = new YibanClient();
        client.setScopes(null);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldBuildCommaDelimitedScopeString() {
        YibanClient client = new YibanClient();
        List<YibanScope> scopes = new ArrayList<>();
        scopes.add(YibanScope.SNSAPI_BASE);
        scopes.add(YibanScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base,snsapi_userinfo", scope);
    }

    @Test
    void shouldBuildSingleScopeString() {
        YibanClient client = new YibanClient();
        List<YibanScope> scopes = new ArrayList<>();
        scopes.add(YibanScope.SNSAPI_LOGIN);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_login", scope);
    }

    @Test
    void shouldHaveAllScopeValues() {
        assertEquals(3, YibanScope.values().length);
        assertNotNull(YibanScope.SNSAPI_LOGIN);
        assertNotNull(YibanScope.SNSAPI_BASE);
        assertNotNull(YibanScope.SNSAPI_USERINFO);
    }
}
