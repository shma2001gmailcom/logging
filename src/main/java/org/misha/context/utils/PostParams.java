package org.misha.context.utils;

import com.google.common.base.Joiner;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.misha.context.utils.Convert.ENCODE;

public class PostParams {
    public static final String ERROR = "error";
    private final String body;

    public PostParams(final String body) {
        this.body = body;
    }

    public String prepare() {
        Map<String, String> params = parseBody();
        return params.entrySet()
                     .stream()
                     .map(e -> joinEntry(ENCODE, e.getKey(), escapeJson(e.getValue())))
                     .reduce((x, y) -> Joiner.on("&").join(x, y))
                     .orElse(ERROR);
    }

    private LinkedHashMap<String, String> parseBody() {
        final LinkedHashMap<String, String> result = new LinkedHashMap<>();
        Arrays.stream(body.split("&"))
              .map(pair -> pair.split("="))
              .forEach((l -> result.put(l[0], l[1])));
        return result;
    }

    public static String joinEntry(Convert option, String... pair) {
        try {
            return Joiner.on("=").join(option.convert(pair[0]), option.convert(pair[1]));
        } catch (UnsupportedEncodingException e) {
            return "\n\n\n" + e.getMessage();
        }
    }

    private static String escapeJson(String json) {
        return "\"" + trim(json) + "\"";
    }
}

