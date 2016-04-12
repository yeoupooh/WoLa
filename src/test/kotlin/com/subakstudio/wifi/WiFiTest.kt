package com.subakstudio.wifi

import com.subakstudio.util.RedirectIO
import com.subakstudio.wifi.WifiMacOSX
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.util.*

/**
 * Created by yeoupooh on 4/11/16.
 */
public class WiFiTest {

    // Macosx
    // http://www.infoworld.com/article/2614879/mac-os-x/mac-os-x-top-20-os-x-command-line-secrets-for-power-users.html
    // http://osxdaily.com/2007/01/18/airport-the-little-known-command-line-wireless-utility/http://osxdaily.com/2007/01/18/airport-the-little-known-command-line-wireless-utility/
    @Ignore
    @Test fun testOnMacosx() {
        var p = ProcessBuilder().command("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport", "-s").start()
        RedirectIO.redirect(p.inputStream, System.out)
        RedirectIO.redirect(p.errorStream, System.out)
        //        inheritIO(p.inputStream, System.out)
        //        inheritIO(p.errorStream, System.err)
        p.waitFor()
    }

    @Ignore
    @Test fun testUpdateSignalOnMacOSX() {
        var wifi = WifiMacOSX()
        var aps = wifi.updateSignals()
        assertNotNull(aps)
        for (ap in aps) {
            println("ap=[$ap]")
        }
    }

    // Windows
    // http://stackoverflow.com/questions/5378103/finding-ssid-of-a-wireless-network-with-java
    @Ignore
    @Test fun testOnWindows() {
        var ssids = ArrayList<String>()
        var signals = ArrayList<String>()
        var builder = ProcessBuilder(
                "cmd.exe", "/c", "netsh wlan show all")
        builder.redirectErrorStream(true)
        var p = builder.start()
        var r = BufferedReader(InputStreamReader(p.getInputStream()))
        var line: String?
        while (true) {
            line = r.readLine();
            if (line.contains("SSID") || line.contains("Signal")) {
                if (!line.contains("BSSID"))
                    if (line.contains("SSID") && !line.contains("name") && !line.contains("SSIDs")) {
                        line = line.substring(8)
                        ssids.add(line)

                    }
                if (line!!.contains("Signal")) {
                    line = line!!.substring(30)
                    signals.add(line)
                }

                if (signals.size == 7) {
                    break
                }
            }

        }
        for ((i, value) in ssids.withIndex()) {
            System.out.println("SSID name == " + ssids.get(i) + "   and its signal == " + signals.get(i))
        }
    }
}

