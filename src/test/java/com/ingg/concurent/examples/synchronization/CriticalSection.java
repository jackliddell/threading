package com.ingg.concurent.examples.synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jiri.peinlich on 02/11/2016.
 */
public class CriticalSection
{
//    List<Integer> integers = new CopyOnWriteArrayList<>();
    List<Integer> integers = new ArrayList<>();

    Random rand = new Random( 0 );

    public boolean contains( int x ) {
        return integers.contains( x );
    }

    public CriticalSection generateRandomNumbers( int numbersToGenerate ) {
        List<Integer> result = new ArrayList<>( numbersToGenerate );
        for( int i = 0; i < numbersToGenerate; i++ ) {
            result.add( rand.nextInt() );
        }
        integers.addAll( result );
        return this;
    }

    public void addNewNumber() {
        integers.add( 0, rand.nextInt() );
    }
}
