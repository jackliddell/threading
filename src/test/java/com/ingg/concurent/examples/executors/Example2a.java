package com.ingg.concurent.examples.executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2a
{
    private static final Logger logger = LoggerFactory.getLogger(Example2a.class);

    @Test
    public void executor() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        logger.info( "Hello from main" );
        executor.submit( () -> logger.info( "Logging a message" ) );

    }


}
