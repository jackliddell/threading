package com.ingg.concurent.examples.synchronization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class Example3c
{
    private static final Logger logger = LoggerFactory.getLogger(Example3c.class);

    @Test
    public void optimisticLock() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                logger.info("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1);
                logger.info("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(2);
                logger.info("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                logger.info("Write Lock acquired");
                sleep(2);
            } finally {
                lock.unlock(stamp);
                logger.info("Write done");
            }
        });

        stop(executor);


    }

    int count;

    @Test
    public void convertToWriteLock() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                if (count == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        logger.info("Could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count = 23;
                }
                logger.info("Count is {}",count);
            } finally {
                lock.unlock(stamp);
            }
        });

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
