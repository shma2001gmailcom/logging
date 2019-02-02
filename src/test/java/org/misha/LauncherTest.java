package org.misha;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.google.common.io.Files.*;
import static java.nio.charset.Charset.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LauncherTest {

    private static final String LOGS_RESIDENCE = "../../logs/logging.log";

    @Test
    public void writeThenCheck() throws IOException {
        final ArrayList<String> expected = Lists.newArrayList(
                "DEBUG org.misha.Launcher - debug",
                "INFO  org.misha.Launcher - info",
                "WARN  org.misha.Launcher - warn",
                "ERROR org.misha.Launcher - error",
                "FATAL org.misha.Launcher - fatal");
        clear(new File(LOGS_RESIDENCE));
        Launcher.main("");
        checkLogs(expected);
    }

    private void checkLogs(final ArrayList<String> expected) throws IOException {
        int i = 0;
        for (String line: readLines(new File(LOGS_RESIDENCE), forName("UTF-8"))) {
            final String suffix = expected.get(i++);
            if(!line.endsWith(suffix)) fail(line + " " +suffix);
        }
    }



    private void clear(final File logs) throws IOException {
        if (logs.exists()) {
            FileUtils.forceDelete(logs);
            FileUtils.touch(logs);
        }
    }
}