package com.ingg.concurent.examples.executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2b
{
    private static final Logger logger = LoggerFactory.getLogger( Example2b.class );

    @Test
    public void somethingLonger() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        logger.info( "Hello from main" );
        executor.submit( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );
    }

    @Test
    public void future() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        logger.info( "Hello from main" );
        Future<?> future = executor.submit( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );

        Object o = future.get();
        logger.info( "What do I have here??? {}", o );

    }

    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
