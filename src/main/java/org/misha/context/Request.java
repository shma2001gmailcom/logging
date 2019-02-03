package org.misha.context;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.misha.context.utils.PostParams;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;

class Request {
    private static final Logger LOG = Logger.getLogger(Request.class);
    private final String parameters;
    private final HttpURLConnection connection;
    private final CookieManager cookieManager;

    Request(final String query,
            final HttpURLConnection connection,
            final CookieManager manager

    ) {
        this.parameters = query;
        this.connection = connection;
        this.cookieManager = manager;
    }

    void doRequest(String token) {
        try (OutputStream out = connection.getOutputStream()) {
            final String preparedBody = new PostParams(parameters+ "&" + "scrfToken=" + token).prepare();
            LOG.debug("Prepared body: " + preparedBody);
            out.write(preparedBody.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            LOG.error(e, e.getCause());
        }
    }

    void doAuthorize(HttpURLConnection auth) {

    }

    void storeCookie(HttpURLConnection auth) {
        List<String> cookiesHeader = auth.getHeaderFields().get("Set-Cookie");
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    void addCookie() {
        if (!cookieManager.getCookieStore().getCookies().isEmpty()) {
            connection.setRequestProperty("Cookie",
                                          Joiner.on(";").join(cookieManager.getCookieStore().getCookies()));
        }
    }
}
