package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class Example3d
{
    private static final Logger logger = LoggerFactory.getLogger(Example3d.class);

    @Test
    public void semaphores() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    logger.info("Semaphore acquired");
                    sleep(5000);
                } else {
                    logger.info("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10).forEach(i -> executor.submit(longRunningTask));


        stop(executor);


    }

    private void stop( ExecutorService executor ) {
        executor.shutdown();
        try {
            executor.awaitTermination( 10, TimeUnit.SECONDS );
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
