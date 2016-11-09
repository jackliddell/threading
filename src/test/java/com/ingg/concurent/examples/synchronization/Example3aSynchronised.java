package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiri.peinlich on 01/11/2016.
 */
@SuppressWarnings("Duplicates")
public class Example3aSynchronised
{
    private static final Logger logger = LoggerFactory.getLogger( Example3aSynchronised.class );
    private static final int SUM = 1000000;
    private static final int WRITER_COUNT = 10000;
    private static final int WRITER_SLEEP = 1;
    private static final int READER_COUNT = 10000;
    private static final int READER_SLEEP = 1;
    private Runner runner = new Runner();


    private int a = 0;
    private int b = SUM;

    @Test
    public void synchronization() throws Exception {

        runner.submit( this::writerThread );
        runner.submit( this::readerThread );

        runner.executeTasks();

    }

    private void writerThread() {
        for( int i = 0; i < WRITER_COUNT; i++ ) {
            writer( i );
            sleep( WRITER_SLEEP );
        }
        logger.info( "Writer finished..." );
    }

    private synchronized void writer( int i ) {
        a = i;
        b = SUM - i;
    }

    private void readerThread() {
        int counter = 0;
        for( int j = 0; j < READER_COUNT; j++ ) {
            int sum = reader();
            if( sum != SUM ) {
                logger.info( "{} != {}", sum,SUM );
                counter++;
            }
            sleep( READER_SLEEP );
        }
        logger.info( "This many errors: {}", counter );
    }

    private synchronized int reader() {
        return a + b;
    }

    private void sleep( int i ) {
        if (i==0){
            return;
        }
        try {
            Thread.sleep( i );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
