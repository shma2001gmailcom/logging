package org.misha.context;

import javax.inject.Named;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Named
class Client implements HttpClient {

    @Override
    public HttpResponse request(String address, String query) {
        final HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(address).openConnection();
        } catch (IOException e) {
            return HttpResponse.get(null);
        }
        connection.setDoOutput(true);
        new Request(query, connection).doRequest();
        return HttpResponse.get(connection);
    }
}
