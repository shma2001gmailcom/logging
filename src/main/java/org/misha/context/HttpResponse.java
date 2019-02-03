package org.misha.context;

import java.net.HttpURLConnection;

public interface HttpResponse {

    static HttpResponse get(HttpURLConnection connection) {
        if (connection == null) {
            return new Response(null, 500, "Wrong URL.");
        }
        return new Response(connection, Response.code(connection), Response.payLoad(connection));
    }

    int getCode();

    String getPayLoad();

    HttpURLConnection getConnection();
}
