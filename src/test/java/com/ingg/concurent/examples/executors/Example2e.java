package com.ingg.concurent.examples.executors;

import com.google.common.util.concurrent.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2e
{
    private static final Logger logger = LoggerFactory.getLogger(Example2e.class);

    @Test
    public void listenableFuture() throws Exception {
        ListeningExecutorService service = MoreExecutors.listeningDecorator( Executors.newFixedThreadPool(10));

        ListenableFuture<String> future = service.submit( () -> {
            throw new RuntimeException( "something nasty" );
//            sleep( 1 );
//            logger.info( "future is executed..." );
//            return "Something nasty!";
        } );

        Futures.addCallback(future,new FutureCallback<String>(){

            @Override public void onSuccess( String message ) {
                logger.info( "Showing you message: {}", message );
            }

            @Override public void onFailure( Throwable throwable ) {
                logger.error( "There was an problem here..." );
            }
        });

        logger.info( "Shutting down the executor..." );
        service.shutdown();
        service.awaitTermination( 5, TimeUnit.SECONDS );

    }


    @Test
    public void listenableFutureAfterShutdown() throws Exception {
        ListeningExecutorService service = MoreExecutors.listeningDecorator( Executors.newFixedThreadPool(10));

        ListenableFuture<String> future = service.submit( () -> {
            sleep( 1 );
            logger.info( "future is executed..." );
            return "Something nasty!";
        } );

        logger.info( "Shutting down the executor..." );
        service.shutdown();


        Futures.addCallback(future,new FutureCallback<String>(){

            @Override public void onSuccess( String message ) {
                logger.info( "Showing you message: {}", message );
            }

            @Override public void onFailure( Throwable throwable ) {
                logger.error( "There was an problem here..." );
            }
        });

        service.awaitTermination( 5, TimeUnit.SECONDS );

    }


    private void sleep( int seconds ) {
        try {
            Thread.sleep( seconds * 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
