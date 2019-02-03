package org.misha.context;

import java.net.CookieManager;

public interface HttpClient {

    HttpResponse request(String address, String query);

    String askToken(final String address, CookieManager manager);

    CookieManager authorize(String address);
}
