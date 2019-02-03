package org.misha.context;

public interface HttpClient {

    HttpResponse request(String address, String query);
}
