package com.ingg.sessions.threads.jmm;


class Example3
{

    private int i;
    private volatile boolean bool;

    void  writer() {
        i = 1;
        bool = true;

    }

    void reader() {
        if( bool ) {
            assert i == 1;
        }
    }
}
