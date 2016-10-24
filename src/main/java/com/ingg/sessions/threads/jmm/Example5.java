package com.ingg.sessions.threads.jmm;

/**
 * Created by jiri.peinlich on 12/10/2016.
 */
public class Example5
{
    static Example5 element;

    final int x;
    int y;

    public Example5() {
        x = 1;
        y = 2;
    }

    public void creator() {
        element = new Example5();
    }

    public void reader() {
        if( element != null ) {
            assert y == 2;
            assert x == 1;
        }
    }
}
