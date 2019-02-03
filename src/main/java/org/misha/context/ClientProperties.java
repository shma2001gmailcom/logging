package org.misha.context;

import org.apache.log4j.Logger;
import org.misha.context.utils.Resources;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static org.misha.context.utils.Resources.*;

@Named("clientProperties")
class ClientProperties {
    private static final Logger log = Logger.getLogger(ClientProperties.class);
    private final Properties properties;
    private final ReadWriteLock lock;

    @Inject
    ClientProperties(@Named("properties") final Properties properties,
                            @Named("lock") final ReadWriteLock lock
    ) {
        this.properties = properties;
        this.lock = lock;
    }

    @PostConstruct
    void create() {
        newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.debug(properties);
            lock.writeLock().lock();
            try (InputStream in = readProperties()) {
                properties.load(in);
            } catch (IOException e) {
                log.error(e, e.getCause());
            } finally {
                lock.writeLock().unlock();
            }
            log.debug(properties);
        }, 0L, 45L, TimeUnit.SECONDS);
    }

    private static InputStream readProperties() {
        return getResource("application.properties");
    }

    String get(String key) {
        lock.readLock().lock();
        try {
            return properties.getProperty(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}
