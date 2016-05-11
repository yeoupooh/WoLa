package com.subakstudio.wol

import com.subakstudio.wola.config.WolHost
import org.junit.Test
import rx.Observable
import rx.functions.Func1

/**
 * Created by jinwoomin on 5/4/16.
 */
class AsusRouterTest {
    @Test
    fun trigger() {
        var host = WolHost(type = "asus", name = "test",
                options = mapOf(
                        Pair("mac", ""),
                        Pair("url", ""),
                        Pair("username", ""),
                        Pair("password", "")))
        var wt: IWolTrigger = AsusRouterWolTrigger(host)
        wt.trigger()
    }

    @Test
    fun triggerWithRx() {
        val host: WolHost? = WolHost(type = "asus", name = "test",
                options = mapOf(
                        Pair("mac", ""),
                        Pair("url", ""),
                        Pair("username", ""),
                        Pair("password", "")))
        login(host)
                .flatMap {
                    logout(host)
                }
                .subscribe({ s ->
                    println("after flatmap: $s")
                })
    }

    private fun logout(host: WolHost?): Observable<WolHost> {
        return Observable.create { subscriber ->
            println("logout: $host")
            subscriber.onNext(host)
        }

    }

    private fun login(host: WolHost?): Observable<WolHost> {
        //        OkHttp
        return Observable.create { subscriber ->
            println("login: $host")
            subscriber.onNext(host)
        }
    }

}
