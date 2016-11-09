package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class Example3f
{

    Runner runner = new Runner().setPrintStacks( true );

    Object lock1 = new Object();
    Object lock2 = new Object();

    private static final Logger logger = LoggerFactory.getLogger(Example3f.class);
    @Test
    public void deadlock() throws Exception {

        runner.submit( this::method1 );
        runner.submit( this::method2 );

        runner.executeTasks();

    }

    private void method1() {

        lock1();
        logger.info( "method 1 executed..." );
    }

    private void lock1() {
        logger.info( "Going to acquire lock 1.." );
        synchronized(lock1 ) {
            logger.info( "Lock 1 acquired..." );
            sleep(100);
            lock2();
        }
    }


    private void method2() {
        logger.info( "method 2 executed..." );
        lock2();
    }

    private void lock2() {
        logger.info( "Going to acquire lock 2.." );
        synchronized(lock2 ) {
            logger.info( "Lock 2 acquired..." );
            sleep(100);
            lock1();
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
