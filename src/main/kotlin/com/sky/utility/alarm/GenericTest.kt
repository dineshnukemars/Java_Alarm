package com.sky.utility.alarm

fun main() {
    sss()
}

fun <T> tes(t: T): Unit where T : TestListenerA, T : TestListenerB {
    println(t.actionA("testA"))
    println(t.actionB("testB"))
}

fun sss() {
    tes(object : TestListenerA, TestListenerB {
        override fun actionA(text: String): Int {
            println(text)
            return 1
        }

        override fun actionB(text: String): Int {
            println(text)
            return 2
        }

    })
}

interface TestListenerA {

    fun actionA(text: String): Int
}

interface TestListenerB {

    fun actionB(text: String): Int
}