package com.subakstudio.wol

import com.subakstudio.wola.config.WolHost
import org.junit.Test

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
}
