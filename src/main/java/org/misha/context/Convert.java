package org.misha.context;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;

enum Convert {

    ENCODE {
        @Override
        String convert(final String data) throws UnsupportedEncodingException {
            return encode(data, ENC);
        }
    },
    DECODE {
        @Override
        String convert(final String data) throws UnsupportedEncodingException {
            return decode(data, ENC);
        }
    };

    private static final String ENC = "UTF-8";

    abstract String convert(String data) throws UnsupportedEncodingException;
}
