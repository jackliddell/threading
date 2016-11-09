package com.ingg.concurent.examples.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class Example7
{
    private static final Logger log = LoggerFactory.getLogger( Example7.class );

    @Test public void reduce() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        for(int i=0; i<10; i++) {
            map.put( String.valueOf( i ), "value" + String.valueOf(i) );
        }

        String result = map.reduce( 1,
                (key, value) -> {
                    log.info( "Reducing key value pair. key: {}, value: {}", key, value );
                    return key + "=" + value;
                },
                (s1, s2) -> {
                    log.info( "Joining strings, string one: [{}], string two: [{}]", s1, s2 );
                    return s1 + ", " + s2;
                });

        log.info("Result: {}", result);
    }
}
