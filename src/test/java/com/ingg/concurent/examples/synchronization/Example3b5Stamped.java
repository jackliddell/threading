package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by jiri.peinlich on 01/11/2016.
 */
@SuppressWarnings("Duplicates")
public class Example3b5Stamped
{
    private static final Logger logger = LoggerFactory.getLogger( Example3b5Stamped.class );
    private static final int SUM = 1000000;
    private static final int WRITER_COUNT = 1000;
    private static final int WRITER_SLEEP = 10;
    private static final int READER_COUNT = 10000;
    private static final int READER_SLEEP = 0;

    StampedLock lock = new StampedLock();
    private Runner runner = new Runner();
    private CriticalSection section = new CriticalSection().generateRandomNumbers( 1000000 );

    @Test
    public void synchronization() throws Exception {

        runner.submit( this::writerThread );
        runner.submit( this::readerThread );
        runner.submit( this::readerThread );
        runner.submit( this::readerThread );
        runner.submit( this::readerThread );

        runner.executeTasks();

    }

    private void writerThread() {
        for( int i = 0; i < WRITER_COUNT; i++ ) {
            writer(  );
            sleep( WRITER_SLEEP );
        }
        logger.info( "Writer finished..." );
    }

    private void writer( ) {
        long l = lock.writeLock();
        try {
            section.addNewNumber();
        } finally {
            lock.unlockWrite( l );
        }
    }

    private void readerThread() {
        Random random = new Random( 1 );
        int counter = 0;
        logger.info( "Started..." );
        for( int i = 0; i < READER_COUNT; i++ ) {
            if( reader( random.nextInt() ) ) {
                counter++;
            }
            sleep( READER_SLEEP );
        }
        logger.info( "Reader finished...: {}", counter );
    }

    private boolean reader( int i ) {
        long l = lock.readLock();
        try {
            return section.contains( i );
        } finally {
            lock.unlockRead(l);
        }
    }

    private void sleep( int i ) {
        if( i == 0 ) {
            return;
        }
        try {
            Thread.sleep( i );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}
