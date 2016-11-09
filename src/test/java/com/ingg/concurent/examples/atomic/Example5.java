package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class Example5
{
    private static final Logger log = LoggerFactory.getLogger( Example5.class );

    @Test public void forEach() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        for(int i=0; i<20; i++) {
            map.put( String.valueOf( i ), "value" + String.valueOf( i ) );
        }

        map.forEach( 1, (key, value) -> {
            log.info( "key: {}, value: {}", key, value );
        } );
    }
}

