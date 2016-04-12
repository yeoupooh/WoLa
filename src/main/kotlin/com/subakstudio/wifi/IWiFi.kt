package com.subakstudio.wifi

/**
 * Created by yeoupooh on 4/11/16.
 */
interface IWiFi {
    fun updateSignals(): List<WiFiAccessPoint>
}