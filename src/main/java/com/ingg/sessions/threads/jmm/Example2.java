package com.ingg.sessions.threads.jmm;


class Example2
{

    private int i;
    private boolean bool;

    synchronized void  writer() {
        i = 1;
        bool = true;

    }

    synchronized void reader() {
        if( bool ) {
            assert i == 1;
        }
    }
}
