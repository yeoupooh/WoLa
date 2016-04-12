package com.subakstudio.wifi

/**
 * Created by yeoupooh on 4/11/16.
 */
class WiFiAccessPoint(val ssid: String, val bssid: String, val rssi: Int, val channel: String, val ht: String, val cc: String, val securities: String) {
    override fun toString(): String {
        return String.format("${this.javaClass.simpleName}{ssid=[${ssid}] bssid=[${bssid}] rssi=[${rssi}] channel=[${channel}] ht=[${ht}] cc=[${cc}] securities=[${securities}]}")
    }
}