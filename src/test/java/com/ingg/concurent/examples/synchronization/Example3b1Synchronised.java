package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by jiri.peinlich on 01/11/2016.
 */
@SuppressWarnings("Duplicates")
public class Example3b1Synchronised
{
    private static final Logger logger = LoggerFactory.getLogger( Example3b1Synchronised.class );
    private static final int SUM = 1000000;
    private static final int WRITER_COUNT = 10000;
    private static final int WRITER_SLEEP = 0;
    private static final int READER_COUNT = 10000;
    private static final int READER_SLEEP = 0;
    private Runner runner = new Runner();


    private CriticalSection section = new CriticalSection().generateRandomNumbers( 1000000 );

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
        section.addNewNumber();
    }

    private void readerThread() {
        Random random = new Random(1);
        int counter = 0;
        for( int i = 0; i < READER_COUNT; i++ ) {
            if ( reader(random.nextInt()) ){
                counter++;
            }
            sleep( READER_SLEEP );
        }
        logger.info( "Reader finished...: {}", counter );
    }

    private synchronized boolean reader(int i) {
        return section.contains( i );
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
