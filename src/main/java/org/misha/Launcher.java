package org.misha;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {
    private static final Logger log = LogManager.getLogger(Launcher.class);

    public static  void main(String... args) {
        new Launcher().makeLogs();
    }

    private void makeLogs() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.fatal("fatal");
    }
}
