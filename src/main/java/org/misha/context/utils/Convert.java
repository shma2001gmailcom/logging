package org.misha.context.utils;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;

public enum Convert {

    ENCODE {
        @Override
        public String convert(final String data) throws UnsupportedEncodingException {
            return encode(data, ENC);
        }
    },
    DECODE {
        @Override
        public String convert(final String data) throws UnsupportedEncodingException {
            return decode(data, ENC);
        }
    };

    private static final String ENC = "UTF-8";

    public abstract String convert(String data) throws UnsupportedEncodingException;
}
