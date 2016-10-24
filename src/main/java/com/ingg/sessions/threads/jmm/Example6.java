package com.ingg.sessions.threads.jmm;

/**
 * Created by jiri.peinlich on 12/10/2016.
 */
public class Example6
{
    static Example6 element;

    final int x;
    int y;

    public Example6() {
        x = 1;
        y = 2;
        element = this;
    }

    public void creator() {
        new Example6();
    }

    public void reader() {
        if( element != null ) {
            assert y == 2;
            assert x == 1;
        }
    }
}
