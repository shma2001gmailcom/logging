package org.misha.request;

import org.apache.log4j.Logger;
import org.misha.context.HttpResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
    private static final Logger log = Logger.getLogger(Launcher.class);

    public static void main(String... a) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        logResponse(((HttpSender) context.getBean("sender")).send());
    }

    private static void logResponse(final HttpResponse response) {
        log.debug("code:" + response.getCode() + "\nresponse payload:" + response.getPayLoad());
    }
}
