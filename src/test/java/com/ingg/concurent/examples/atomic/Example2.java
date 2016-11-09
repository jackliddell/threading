package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Example2
{
    private static final Logger log = LoggerFactory.getLogger( Example2.class );

    AtomicInteger atomicInt = new AtomicInteger( 1000 );
    int normalInt = 1000;

    @Test public void atomicOperation() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool( 2 );
        CountDownLatch latch = new CountDownLatch( 2 );
        log.info( "Starting..." );

        executor.submit( () -> {
            latch.countDown();
            try {
                latch.await();
            } catch( InterruptedException e ) {
                throw new RuntimeException();
            }

            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ ) {
                atomicInt.updateAndGet( n -> n-2 );
            }
        } );

        executor.submit( () -> {
            latch.countDown();
            try {
                latch.await();
            } catch( InterruptedException e ) {
                throw new RuntimeException();
            }

            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ ) {
                atomicInt.updateAndGet( n -> n+2 );
            }
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final value: {}", atomicInt.get() );
    }

    @Test public void normalOperation() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool( 2 );

        log.info( "Starting..." );

        executor.submit( () -> {
            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ )
                normalInt+=2;
        } );

        executor.submit( () -> {
            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ )
                normalInt -= 2;
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final value: {}", normalInt );
    }
}
