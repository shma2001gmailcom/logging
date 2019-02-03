package org.misha.context;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import org.apache.log4j.Logger;
import org.misha.request.HttpSender;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.misha.context.Convert.DECODE;
import static org.misha.context.PostParams.ERROR;
import static org.misha.context.PostParams.joinEntry;

@Named("sender")
class Sender implements HttpSender {
    private static final Logger log = Logger.getLogger(Sender.class);
    private final ClientProperties properties;
    private final HttpClient client;

    @Inject
    Sender(final HttpClient client, final ClientProperties clientProperties) {
        this.properties = clientProperties;
        this.client = client;
    }

    @Override
    public HttpResponse send() {
        log.debug(properties.get("body.properties"));
        final String query;
        try {
            query = new PostParams(getParams()).prepare();
        } catch (IOException e) {
            log.error("Can not find file with request data.");
            throw new RequestDataFileNotFound();
        }
        logRequest(query);
        return client.request(makeAddress(properties), query);
    }

    private String getParams() throws IOException {
        return Resources.toString(getResource(properties.get("body.content")), UTF_8);
    }

    private static void logRequest(final String query) {
        log.debug(query);
        log.debug(Arrays.stream(query.split("&"))
                        .map(pair -> joinEntry(DECODE, pair.split("=")))
                        .reduce((x, y) -> Joiner.on("&").join(x, y))
                        .orElse(ERROR));
    }

    private static String makeAddress(final ClientProperties properties) {
        return properties.get("protocol") + "://" + properties.get("host") + ":" + properties.get("port");
    }
}
