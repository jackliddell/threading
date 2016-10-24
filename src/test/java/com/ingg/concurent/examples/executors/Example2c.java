package com.ingg.concurent.examples.executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2c
{
    private static final Logger logger = LoggerFactory.getLogger( Example2c.class );

    @Test
    public void shutdown() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );

        logger.info( "Hello from main" );
        executor.shutdown();
        logger.info( "Let us wait for the thread to finish its work..." );
        executor.awaitTermination( 2, TimeUnit.SECONDS );
    }

//    @Test(expected = RejectedExecutionException.class)
    @Test
    public void shutdownPreventsSubmit() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();


        logger.info( "Hello from main" );
        executor.shutdown();

        executor.submit( () -> {
            sleep( 1 );
            logger.info( "Logging a message" );
        } );

        logger.info( "Let us wait for the thread to finish its work..." );
        executor.awaitTermination( 2, TimeUnit.SECONDS );
    }



    @Test
    public void shutdownNow() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit( () -> {
            try {
                sleep( 5 );
                logger.info( "Logging a message" );
            } catch( Exception e ) {
                logger.info( "I think I got interrupted ..." ,e);
            }
        } );

        logger.info( "Hello from main" );
        executor.shutdownNow();

        sleep( 1 );
    }

    @Test
    public void howToShutdown() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit( () -> {
            logger.info( "Logging a message" );
            try {
                logger.info( "Going to sleep..." );
                sleep( 5 );
            } catch( Exception e ) {
                logger.info( "Ok I am going to wake up here..." );
            }
        } );

        logger.info( "Hello from main" );
        sleep( 1 );
        try {
            logger.info( "Going to shutdown..." );
            executor.shutdown();
            executor.awaitTermination( 2, TimeUnit.SECONDS );
        } catch( InterruptedException e ) {
            logger.warn( "Timeout on executor" );
        } finally {
            if( !executor.isTerminated() ) {
                logger.info( "This time for real..." );
                executor.shutdownNow();
            }
        }
    }

    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }

}
