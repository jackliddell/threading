package com.ingg.concurent.examples.threads;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example1c
{
    private static final Logger logger = LoggerFactory.getLogger( Example1c.class );

    @Test
    public void noJoin() throws Exception {
        Thread thread = new Thread( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );
        thread.setName( "Something nasty" );

        logger.info( "This comes from main thread" );
        thread.start();
        //        thread.join();
    }

    @Test
    public void join() throws Exception {
        Thread thread = new Thread( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );
        thread.setName( "Something nasty" );

        logger.info( "This comes from main thread" );
        thread.start();
        thread.join();

    }

    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
