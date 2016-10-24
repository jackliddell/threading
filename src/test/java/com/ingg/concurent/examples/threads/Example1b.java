package com.ingg.concurent.examples.threads;

import org.junit.Test;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example1b
{

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Example1b.class);

    @Test
    public void multipleThreads() throws Exception {

        Thread thread = new Thread( () -> logger.info( "Logging a message" ) );
        thread.setName( "Something nasty" );

        logger.info( "This comes from main thread" );
        thread.start();

    }


}
