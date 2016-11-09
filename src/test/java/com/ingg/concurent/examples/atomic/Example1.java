package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Example1
{
    private static final Logger log = LoggerFactory.getLogger( Example1.class );

    AtomicInteger atomicInt = new AtomicInteger( 0 );
    int normalInt = 0;

    @Test public void atomicIncrement() throws InterruptedException {
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
            for( int i = 0; i < 10000; i++ )
                atomicInt.addAndGet( 1 );
        } );

        executor.submit( () -> {
            latch.countDown();
            try {
                latch.await();
            } catch( InterruptedException e ) {
                throw new RuntimeException();
            }

            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ )
                atomicInt.addAndGet( 2 );
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final value: {}", atomicInt.get() );
    }

    @Test public void intIncrement() throws InterruptedException {
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
            for( int i = 0; i < 10000; i++ )
                normalInt++;
        } );

        executor.submit( () -> {
            latch.countDown();
            try {
                latch.await();
            } catch( InterruptedException e ) {
                throw new RuntimeException();
            }

            log.info( "Starting to add..." );
            for( int i = 0; i < 10000; i++ )
                normalInt += 2;
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final value: {}", normalInt );
    }
}
