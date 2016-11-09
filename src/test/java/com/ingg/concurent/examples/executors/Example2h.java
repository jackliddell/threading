package com.ingg.concurent.examples.executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class Example2h
{
    private static final Logger logger = LoggerFactory.getLogger(Example2h.class);

    @Test
    public void completableFuture() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
            logger.info( "Going to have a small nap..." );
            sleep(2000);
            return 555;
        } , executor );


        sleep( 100 );

        CompletableFuture<Integer> newFuture = future
                .thenApply( this::square )
                .thenApply( this::addTwo );

        logger.info( "What could the value be?" );
        logger.info( "It is {} ", newFuture.get() );

        stop( executor );
    }

    private Integer addTwo( Integer value ) {
        logger.info( "Added 2" );
        return value+2;
    }

    private Integer square( Integer x ) {
        return x*x;
    }

    private void sleep( int i ) {
        if( i == 0 ) {
            return;
        }
        try {
            Thread.sleep( i );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }

    private void stop( ExecutorService executor ) {
        executor.shutdown();
        try {
            executor.awaitTermination( 100, TimeUnit.SECONDS );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
