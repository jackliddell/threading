package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Example4
{
    private static final Logger log = LoggerFactory.getLogger( Example4.class );

    @Test public void synchronizedMap() throws InterruptedException {
        Map<String, String> map = Collections.synchronizedMap( new HashMap<>() );
        millionWrites( "synchronized", map );
    }

    @Test public void concurrentHashMap() throws InterruptedException {
        Map<String, String> map = new ConcurrentHashMap<>();
        millionWrites( "concurrent", map );
    }

    public void millionWrites(String type, Map<String, String> map) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool( 20 );
        CountDownLatch latch = new CountDownLatch(1);

        for( int i = 0; i < 1000000; i++ ) {
            final String key = String.valueOf( i );
            final String value = "value" + String.valueOf( i );
            executor.submit( () -> {
                try {
                    latch.await();
                } catch( InterruptedException e ) {
                    throw new RuntimeException();
                }
                map.put( key, value );
            } );
        }

        long startTime = System.currentTimeMillis();
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination( 1, TimeUnit.DAYS );
        long endTime = System.currentTimeMillis();
        log.info( "Time taken for 1 million writes for {} map: {}ms", type, (endTime - startTime) );
    }
}
