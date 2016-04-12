package com.subakstudio.wifi

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

/**
 * Created by yeoupooh on 4/11/16.
 */
class WifiMacOSXTest {

    @Test
    fun parseLines() {
        var wifi = WifiMacOSX()
        var multiLinedStr = File(this.javaClass.getResource("/airport-output.txt").file).readLines().joinToString ("\r")
        println("multiLinedStr=[$multiLinedStr]")
        var aps = wifi.parseLines(multiLinedStr)
        assertNotNull(aps)
        for (ap in aps) {
            println("ap=[$ap}]")
        }
        assertEquals(6, aps.size)
    }
}