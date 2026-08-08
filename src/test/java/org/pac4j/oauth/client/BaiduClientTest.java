package org.pac4j.oauth.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.client.BaiduClient.BaiduScope;

/**
 * Tests for {@link BaiduClient}.
 */
class BaiduClientTest {

    @Test
    void shouldCreateWithDefaultConstructor() {
        BaiduClient client = new BaiduClient();
        assertNotNull(client);
    }

    @Test
    void shouldCreateWithKeyAndSecret() {
        BaiduClient client = new BaiduClient("myKey", "mySecret");
        assertEquals("myKey", client.getKey());
        assertEquals("mySecret", client.getSecret());
    }

    @Test
    void shouldCreateWithNullKeyAndSecret() {
        BaiduClient client = new BaiduClient(null, null);
        assertNull(client.getKey());
        assertNull(client.getSecret());
    }

    @Test
    void shouldReturnNullScopesByDefault() {
        BaiduClient client = new BaiduClient();
        assertNull(client.getScopes());
    }

    @Test
    void shouldSetAndGetScopes() {
        BaiduClient client = new BaiduClient();
        List<BaiduScope> scopes = new ArrayList<>();
        scopes.add(BaiduScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        assertEquals(1, client.getScopes().size());
        assertEquals(BaiduScope.SNSAPI_USERINFO, client.getScopes().get(0));
    }

    @Test
    void shouldSetScopesToNull() {
        BaiduClient client = new BaiduClient();
        client.setScopes(new ArrayList<>());
        client.setScopes(null);
        assertNull(client.getScopes());
    }

    @Test
    void shouldAddScope() {
        BaiduClient client = new BaiduClient();
        client.addScope(BaiduScope.SNSAPI_LOGIN);
        assertNotNull(client.getScopes());
        assertEquals(1, client.getScopes().size());
        assertEquals(BaiduScope.SNSAPI_LOGIN, client.getScopes().get(0));
    }

    @Test
    void shouldAddMultipleScopes() {
        BaiduClient client = new BaiduClient();
        client.addScope(BaiduScope.SNSAPI_BASE);
        client.addScope(BaiduScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldAddScopeWhenListAlreadyExists() {
        BaiduClient client = new BaiduClient();
        List<BaiduScope> scopes = new ArrayList<>();
        scopes.add(BaiduScope.SNSAPI_BASE);
        client.setScopes(scopes);
        client.addScope(BaiduScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesEmpty() {
        BaiduClient client = new BaiduClient();
        client.setScopes(new ArrayList<>());
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesNull() {
        BaiduClient client = new BaiduClient();
        client.setScopes(null);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldBuildCommaDelimitedScopeString() {
        BaiduClient client = new BaiduClient();
        List<BaiduScope> scopes = new ArrayList<>();
        scopes.add(BaiduScope.SNSAPI_BASE);
        scopes.add(BaiduScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base,snsapi_userinfo", scope);
    }

    @Test
    void shouldBuildSingleScopeString() {
        BaiduClient client = new BaiduClient();
        List<BaiduScope> scopes = new ArrayList<>();
        scopes.add(BaiduScope.SNSAPI_LOGIN);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_login", scope);
    }

    @Test
    void shouldHaveAllScopeValues() {
        assertEquals(3, BaiduScope.values().length);
        assertNotNull(BaiduScope.SNSAPI_LOGIN);
        assertNotNull(BaiduScope.SNSAPI_BASE);
        assertNotNull(BaiduScope.SNSAPI_USERINFO);
    }
}
