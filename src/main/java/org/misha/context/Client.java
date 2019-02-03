package org.misha.context;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Named
class Client implements HttpClient {
    private final ClientProperties properties;

    @Inject
    Client(final ClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public HttpResponse request(String address, String query) {
        HttpURLConnection connection = null;
        try {
            try {
                connection = (HttpURLConnection) new URL(address).openConnection();
            } catch (IOException e) {
                return HttpResponse.get(null);
            }
            connection.setDoOutput(true);
            CookieManager manager = authorize(address);
            Request request = new Request(query, connection, manager);
            request.addCookie();
            request.doRequest(askToken(address, manager));
            return HttpResponse.get(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public String askToken(final String address, CookieManager manager) {
        return EMPTY;
    }

    @Override
    public CookieManager authorize(String address) {
        CookieManager manager = new CookieManager();
        HttpURLConnection auth = null;
        try {
            try {
                auth = (HttpURLConnection) new URL(address).openConnection();
                auth.setRequestMethod("POST");
                auth.setRequestProperty("username", properties.get("user"));
                auth.setRequestProperty("password", properties.get("password"));
                auth.setDoOutput(true);
                final Request request = new Request(EMPTY, auth, manager);
                request.doAuthorize(auth);
                request.storeCookie(auth);
            } catch (IOException e) {
                return null;
            }
        } finally {
            if (auth != null) {
                auth.disconnect();
            }
        }
        return manager;
    }
}
