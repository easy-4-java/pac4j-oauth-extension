package org.pac4j.oauth.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.pac4j.oauth.client.OschinaClient.OschinaScope;

/**
 * Tests for {@link OschinaClient}.
 */
class OschinaClientTest {

    @Test
    void shouldCreateWithDefaultConstructor() {
        OschinaClient client = new OschinaClient();
        assertNotNull(client);
    }

    @Test
    void shouldCreateWithKeyAndSecret() {
        OschinaClient client = new OschinaClient("myKey", "mySecret");
        assertEquals("myKey", client.getKey());
        assertEquals("mySecret", client.getSecret());
    }

    @Test
    void shouldCreateWithNullKeyAndSecret() {
        OschinaClient client = new OschinaClient(null, null);
        assertNull(client.getKey());
        assertNull(client.getSecret());
    }

    @Test
    void shouldReturnNullScopesByDefault() {
        OschinaClient client = new OschinaClient();
        assertNull(client.getScopes());
    }

    @Test
    void shouldSetAndGetScopes() {
        OschinaClient client = new OschinaClient();
        List<OschinaScope> scopes = new ArrayList<>();
        scopes.add(OschinaScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        assertEquals(1, client.getScopes().size());
        assertEquals(OschinaScope.SNSAPI_USERINFO, client.getScopes().get(0));
    }

    @Test
    void shouldSetScopesToNull() {
        OschinaClient client = new OschinaClient();
        client.setScopes(new ArrayList<>());
        client.setScopes(null);
        assertNull(client.getScopes());
    }

    @Test
    void shouldAddScope() {
        OschinaClient client = new OschinaClient();
        client.addScope(OschinaScope.SNSAPI_LOGIN);
        assertNotNull(client.getScopes());
        assertEquals(1, client.getScopes().size());
        assertEquals(OschinaScope.SNSAPI_LOGIN, client.getScopes().get(0));
    }

    @Test
    void shouldAddMultipleScopes() {
        OschinaClient client = new OschinaClient();
        client.addScope(OschinaScope.SNSAPI_BASE);
        client.addScope(OschinaScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldAddScopeWhenListAlreadyExists() {
        OschinaClient client = new OschinaClient();
        List<OschinaScope> scopes = new ArrayList<>();
        scopes.add(OschinaScope.SNSAPI_BASE);
        client.setScopes(scopes);
        client.addScope(OschinaScope.SNSAPI_USERINFO);
        assertEquals(2, client.getScopes().size());
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesEmpty() {
        OschinaClient client = new OschinaClient();
        client.setScopes(new ArrayList<>());
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldDefaultToSNSAPIBASEWhenScopesNull() {
        OschinaClient client = new OschinaClient();
        client.setScopes(null);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base", scope);
    }

    @Test
    void shouldBuildCommaDelimitedScopeString() {
        OschinaClient client = new OschinaClient();
        List<OschinaScope> scopes = new ArrayList<>();
        scopes.add(OschinaScope.SNSAPI_BASE);
        scopes.add(OschinaScope.SNSAPI_USERINFO);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_base,snsapi_userinfo", scope);
    }

    @Test
    void shouldBuildSingleScopeString() {
        OschinaClient client = new OschinaClient();
        List<OschinaScope> scopes = new ArrayList<>();
        scopes.add(OschinaScope.SNSAPI_LOGIN);
        client.setScopes(scopes);
        String scope = client.getOAuthScope();
        assertEquals("snsapi_login", scope);
    }

    @Test
    void shouldHaveAllScopeValues() {
        assertEquals(3, OschinaScope.values().length);
        assertNotNull(OschinaScope.SNSAPI_LOGIN);
        assertNotNull(OschinaScope.SNSAPI_BASE);
        assertNotNull(OschinaScope.SNSAPI_USERINFO);
    }
}
