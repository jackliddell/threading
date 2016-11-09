package com.ingg.concurent.examples.synchronization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jiri.peinlich on 01/11/2016.
 */
public class Runner
{
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    List<Runnable> tasks = new ArrayList<>(  );
    private boolean printStacks;

    public void submit( Runnable runnable ) {
        this.tasks.add( runnable );
    }

    public void executeTasks() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch( tasks.size() );
        List<Thread> threads = new ArrayList<>();


        int counter =0;
        long timeStart = System.nanoTime();
        for( Runnable task : tasks ) {
            CountDownLatch innerLatch = new CountDownLatch( 1 );
            Thread thread = new Thread( () -> {
                try {
                    innerLatch.countDown();
                    latch.await();
//                    logger.debug( "Lets go..." );
                } catch( InterruptedException e ) {
                    throw new RuntimeException( e );
                }
                task.run();
            } );
            thread.setName( "MyThread-"+counter++ );
            thread.start();
            threads.add( thread );
//            logger.debug( "Thread {} started",thread.getName() );
            innerLatch.await();
            timeStart = System.nanoTime();
            latch.countDown();
        }

//        logger.debug( "Waiting for threads to finish..." );

        printStackTraces();

        for( Thread thread : threads ) {
            thread.join();
        }
        long timeEnd = System.nanoTime();
        long o = timeEnd - timeStart;
        logger.info( "It took {} ns, which was around {} seconds",o, o/1000000000 );
    }

    private void printStackTraces() {
        if (printStacks){

            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            logger.info( ThreadDumpPrettyPrinter.print( allStackTraces ));
        }
    }

    public Runner setPrintStacks( boolean printStacks ) {
        this.printStacks = printStacks;
        return this;
    }
}
