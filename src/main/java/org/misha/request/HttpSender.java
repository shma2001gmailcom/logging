package org.misha.request;

import org.misha.context.HttpResponse;

public interface HttpSender {

    HttpResponse send();
}
