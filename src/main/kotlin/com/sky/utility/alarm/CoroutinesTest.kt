package com.sky.utility.alarm

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.resume

class CoroutinesTest {


    fun testAsync() = runBlocking {
        println("called async1")
        val deffered1 = async { sus1() }
        println("called async2")
        val deffered2 = async { sus2() }
        Thread.sleep(5000)
        println("completed ${deffered2.await()} ")
        println("completed ${deffered1.await()} ")
    }

    fun testNormal() = runBlocking {
        println("called async1")
        val str1 = sus1()
        println("called async2")
        val str2 = sus2()
        Thread.sleep(5000)
        println("completed $str2 ")
        println("completed $str1 ")
    }

    suspend fun sus1(): String {
        val result = suspendCancellableCoroutine<String> {
            thread {
                println("sus 1")
                Thread.sleep(10000)
                it.resume("completed sus 1")
            }
        }
        return result
    }

    suspend fun sus2(): String {
        val result = suspendCancellableCoroutine<String> {
            thread {
                println("sus 2")
                Thread.sleep(5000)
                it.resume("completed sus 2")
            }
        }
        return result
    }

}
