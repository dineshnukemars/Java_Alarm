package com.sky.utility.alarm;

public class GenTest {

    <T extends A & B> void test(T t) {
        t.actionA();
        t.actionB();
    }
}


interface A {
    void actionA();
}

interface B {
    void actionB();
}