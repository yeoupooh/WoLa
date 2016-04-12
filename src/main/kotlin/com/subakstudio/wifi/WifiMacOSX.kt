package com.subakstudio.wifi

import com.subakstudio.util.RedirectIO
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.StringReader
import java.util.*

/**
 * Created by yeoupooh on 4/11/16.
 */
class WifiMacOSX : IWiFi {
    override fun updateSignals(): List<WiFiAccessPoint> {
        var p = ProcessBuilder().command("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport", "-s").start()
        var baos = ByteArrayOutputStream()
        var ps = PrintStream(baos)
        RedirectIO.redirect(p.inputStream, ps)
        RedirectIO.redirect(p.errorStream, System.err)
        p.waitFor()
        println("baos=" + baos.toString())
        return parseLines(baos.toString())
    }

    fun parseLines(multiLineStr: String): ArrayList<WiFiAccessPoint> {
        var aps = ArrayList<WiFiAccessPoint>()
        var lines = StringReader(multiLineStr).readLines()
        for ((i, line) in lines.withIndex()) {
            // i=0 header
            if (i > 0 && line.length > 0) {
                var pos = 0
                var ssid = line.substring(pos, pos + 32).trim()
                pos += 33
                var bssid = line.substring(pos, pos + 17).trim()
                pos += 18
                var rssi = Integer.parseInt(line.substring(pos, pos + 4).trim())
                pos += 5  // 57
                var channel = line.substring(pos, pos + 7).trim()
                pos += 8  // 62
                var ht = line.substring(pos, pos + 2).trim()
                pos += 3  // 68
                var cc = line.substring(pos, pos + 2).trim()
                pos += 3  // 71
                var securities = line.substring(pos).trim()
                aps.add(WiFiAccessPoint(ssid, bssid, rssi, channel, ht, cc, securities))
            }
        }
        return aps
    }
}

