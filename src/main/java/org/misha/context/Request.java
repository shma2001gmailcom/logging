package org.misha.context;

import org.apache.log4j.Logger;
import org.misha.context.utils.PostParams;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

class Request {
    private static final Logger LOG = Logger.getLogger(Request.class);
    private final String parameters;
    private final HttpURLConnection connection;

    Request(final String parameters, final HttpURLConnection connection) {
        this.parameters = parameters;
        this.connection = connection;
    }

    void doRequest() {
        try (OutputStream out = connection.getOutputStream()) {
            final String preparedBody = new PostParams(parameters).prepare();
            LOG.debug("Prepared body: " + preparedBody);
            out.write(preparedBody.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            LOG.error(e, e.getCause());
        }
    }
}
