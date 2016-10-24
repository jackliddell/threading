package com.ingg.concurent.examples.executors;

import com.google.common.util.concurrent.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2f
{
    private static final Logger logger = LoggerFactory.getLogger(Example2f.class);

    @Test
    public void whyListenableFutureHelps() throws Exception {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator( Executors.newFixedThreadPool(10));

        ListenableFuture<String> future = executor.submit( () -> {
            sleep( 1 );
            logger.info( "future is executed..." );
            return "Something nasty!";
        } );

        AsyncFunction<String,Integer> asyncFunction = input -> executor.submit( () -> {
            Example2f.this.sleep( 1 );
            logger.info( "Second future got executed..." );
            return input != null ? input.length() : 0;
        } );

        ListenableFuture<Integer> integerListenableFuture = Futures.transformAsync( future, asyncFunction, executor );


        logger.info("Hopefully futures did not start yet...");
        logger.info( "The message was {} long",integerListenableFuture.get() );

    }

    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
