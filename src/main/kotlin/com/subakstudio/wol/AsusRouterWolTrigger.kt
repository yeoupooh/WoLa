package com.subakstudio.wol

import com.github.kittinunf.fuel.httpGet
import com.subakstudio.wola.config.WolHost
import rx.Subscriber

/**
 * Created by jinwoomin on 5/4/16.
 */
class AsusRouterWolTrigger(val host: WolHost) : IWolTrigger {
    override fun trigger() {
        // Login
        val (request, response, result) = "http://httpbin.org/get".httpGet().responseString()

        // Wakeup

        // Logout
    }
}