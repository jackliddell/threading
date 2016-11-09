package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class Example3e
{
    private static final Logger logger = LoggerFactory.getLogger(Example3e.class);

    @Test
    public void cyclicBarrier() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CyclicBarrier barrier = new CyclicBarrier( 2, () -> logger.info( "Cyclic barrier passed..." ) );

        Runnable waitingTask = () -> {
            try{
                sleep(new Random().nextInt(50)*100+100);
                logger.info( "Going to wait on a barrier" );
                barrier.await();
                logger.info( "Finished waiting on a barrier..." );
            } catch( Exception e ) {
                throw new RuntimeException( e );
            }

        };

        IntStream.range(0, 2)
                .forEach(i -> executor.submit(waitingTask));

        stop( executor );

    }


    private void stop( ExecutorService executor ) {
        executor.shutdown();
        try {
            executor.awaitTermination( 100, TimeUnit.SECONDS );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
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
