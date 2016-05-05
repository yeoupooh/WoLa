package com.subakstudio.rx

import org.junit.Test
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * Created by jinwoomin on 5/4/16.
 */

class RxJavaInKotlinTest {
    @Test
    fun testCreate() {
        rx.Observable.create<String>({ subscriber ->
            for (i in 1..10) {
                subscriber.onNext(i.toString())
            }
            subscriber.onCompleted()
        }).subscribe(object : Subscriber<String>() {
            override fun onNext(t: String?) {
                println("onNext $t")
            }

            override fun onError(e: Throwable?) {
                println("onError")
            }

            override fun onCompleted() {
                println("onCompleted")
            }
        })
    }

    fun getLongRunMethod1Observable(): Observable<String> {
        return Observable.create { subscriber ->
            println("this is long run method1")
            for (i in 1..10) {
                subscriber.onNext(i.toString())
            }
            subscriber.onCompleted()
        }
    }

    fun getLongRunMethod2Observable(): Observable<String> {
        return Observable.create { subscriber ->
            println("this is long run method2")
            for (i in 1..10) {
                subscriber.onNext(i.toString())
            }
            subscriber.onCompleted()
        }
    }

    @Test
    fun testChainingObservables() {
        var o1 = getLongRunMethod1Observable()
        var o2 = getLongRunMethod2Observable()
        var chainedO = o1.subscribeOn(Schedulers.io()).concatWith(o2)
        chainedO.subscribe(object : Subscriber<String>() {
            override fun onCompleted() {
                println("onCompleted")
            }

            override fun onNext(t: String?) {
                println("onNext: $t")
            }

            override fun onError(e: Throwable?) {
                println("onError: $e")
            }
        })
    }

}