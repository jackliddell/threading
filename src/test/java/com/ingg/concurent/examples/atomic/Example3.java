package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Example3
{
    private static final Logger log = LoggerFactory.getLogger( Example3.class );

    private String string = "";
    private AtomicReference<String> atomicString = new AtomicReference<>("");

    @Test public void atomicReference() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Random random = new Random();

        executor.submit( () -> {
            for( int i = 0; i < 50; i++ ) {
                atomicString.updateAndGet( s -> s + "1" );
            }
        } );

        executor.submit( () -> {
            for(int i=0; i<50; i++) {
                atomicString.updateAndGet( s -> s + "2" );
            }
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final String: {}", atomicString.get() );
        log.info( "String length: {}", atomicString.get().length() );

    }

    @Test public void stingIncrement() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit( () -> {
            for( int i = 0; i < 50; i++ ) {
                string = string + "1";
            }
        } );

        executor.submit( () -> {
            for(int i=0; i<50; i++) {
                string = string + "2";
            }
        } );

        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );

        log.info( "Final String: {}", atomicString.get() );
        log.info( "String length: {}", atomicString.get().length() );

    }
}
