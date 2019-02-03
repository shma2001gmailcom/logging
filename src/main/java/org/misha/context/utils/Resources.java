package org.misha.context.utils;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static java.lang.Thread.currentThread;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.misha.context.utils.Convert.DECODE;
import static org.misha.context.utils.PostParams.ERROR;

public class Resources {
    private static final Logger log = Logger.getLogger(Resources.class);

    private Resources() {
    }

    public static InputStream getResource(String name) {
        return currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    public static void logRequest(final String query) {
        log.debug(query);
        log.debug(Arrays.stream(query.split("&"))
                        .map(pair -> joinEntry(DECODE, pair.split("=")))
                        .reduce((x, y) -> Joiner.on("&").join(x, y))
                        .orElse(ERROR));
    }

    public static InputStream readProperties() {
        return getResource("application.properties");
    }

    public static String joinEntry(Convert option, String... pair) {
        try {
            return Joiner.on("=").join(option.convert(pair[0]), option.convert(pair[1]));
        } catch (UnsupportedEncodingException e) {
            return "\n\n\n" + e.getMessage();
        }
    }

    static String escapeJson(String json) {
        return "\"" + trim(json) + "\"";
    }
}
