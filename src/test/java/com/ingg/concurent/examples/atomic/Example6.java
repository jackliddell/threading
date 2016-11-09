package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Example6
{
    private static final Logger log = LoggerFactory.getLogger( Example6.class );

    @Test public void search() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        for( int i = 0; i < 10; i++ ) {
            map.put( String.valueOf( i ), "value" + String.valueOf( i ) );
        }

        String result = map.search( 1, ( key, value ) -> {
            log.info( "Searching for \"7\"...." );
            if( "7".equals( key ) ) {
                log.info( "I found it!" );
                return value;
            }
            return null;
        } );

        log.info( "Result={}", result );
    }

    @Test public void searchValues() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        Random random = new Random();

        for(int i = 0; i < 20; i++) {
            map.put( i, random.nextInt( 100 ) );
        }

        int result = map.searchValues( 1, value -> {
            log.info( "Checking value: {}", value );
            if(value > 50) {
                return value;
            }
            return null;
        } );

        log.info( "Result={}", result );
    }
}
