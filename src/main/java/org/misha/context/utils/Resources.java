package org.misha.context.utils;

import java.io.InputStream;

import static java.lang.Thread.currentThread;

public class Resources {
    private Resources() {}

    public static InputStream getResource(String name) {
        return currentThread().getContextClassLoader().getResourceAsStream(name);
    }
}
