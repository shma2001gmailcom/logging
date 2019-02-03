package org.misha.context;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.misha.context.utils.PostParams;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static org.misha.context.utils.Convert.DECODE;
import static org.misha.context.utils.Resources.joinEntry;

public class SenderTest {
    private static final Logger LOG = Logger.getLogger(PostParams.class);
    private static final Pattern pattern = Pattern.compile("^((.)+=(.)+)(\\+&(.)+=(.))*");
    private static final String ERROR = "error";

    @Test
    public void writeThenCheck() throws IOException {
        final String query = new PostParams("author=Тереньтьев Терентий Тереньтьевич <teren'tiy@gmail.com>" +
                                                    "token=AAAhgdjdb+mnd744607474HGHbvxvv==").prepare();
        LOG.error(query);
        LOG.error(Arrays.stream(query.split("&"))
                        .map(pair -> joinEntry(DECODE, pair.split("=")))
                        .reduce((x, y) -> Joiner.on("&").join(x, y))
                        .orElse(ERROR));
        checkArgument(pattern.matcher(Arrays.stream(query.split("&"))
                                            .map(pair -> joinEntry(DECODE, pair.split("=")))
                                            .reduce((x, y) -> Joiner.on("&").join(x, y))
                                            .orElse(ERROR)).matches());
        checkArgument(pattern.matcher(query).matches());
    }
}