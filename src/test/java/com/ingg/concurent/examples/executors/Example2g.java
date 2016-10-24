package com.ingg.concurent.examples.executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by jiri.peinlich on 24/10/2016.
 */
public class Example2g
{
    @Test
    public void invokeAll() throws Exception {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator( Executors.newFixedThreadPool(10));

        Collection<Callable<String>> list = new ArrayList<>(  );
        list.add(() -> "test" ) ;
        List<Future<String>> futures = executor.invokeAll( list );

    }
}
