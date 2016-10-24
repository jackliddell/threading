package com.ingg.sessions.threads.jmm;


class Example1
{

    int i;
    boolean bool=false;

    void writer() {
        i = 1;
        bool = true;

    }

    void reader() {
        if( bool ) {
            assert i == 1;
        }
    }
}
