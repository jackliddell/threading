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
public class Example2d
{
    private static final Logger logger = LoggerFactory.getLogger(Example2d.class);
    @Test
    public void callable() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit( () -> {
            sleep( 1 );
            return "nasty";
        } );

        logger.info( "Something {}", future.get() );

    }

    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }

}
