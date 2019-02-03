package org.misha.context;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import static com.google.common.base.Preconditions.checkArgument;

class Response implements HttpResponse {
    private final HttpURLConnection connection;
    private final int code;
    private final String payLoad;

    Response(final HttpURLConnection connection, final int code, final String payLoad) {
        this.connection = connection;
        this.code = code;
        this.payLoad = payLoad;
    }

    @Override
    public HttpURLConnection getConnection() {
        return connection;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getPayLoad() {
        return payLoad;
    }

    static String payLoad(HttpURLConnection connection) {
        String result = StringUtils.EMPTY;
        if (connection == null) {
            return "Wrong request";
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())))) {
            result += read(br);
        } catch (ConnectException e) {
            result += e.getCause();
        } catch (Exception e) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader((connection.getErrorStream())))) {
                result += read(br);
            } catch (IOException ex) {
                result += ex.getCause();
            }
        }
        return result;
    }

    static int code(HttpURLConnection connection) {
        checkArgument(connection != null);
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            return -11;
        }
    }

    private static String read(@Nonnull final BufferedReader br) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
