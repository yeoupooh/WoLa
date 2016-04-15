package com.subakstudio.wola.model

import rx.Observable
import java.util.*

/**
 * Created by yeoupooh on 16. 4. 13.
 */
data class WolRow(
        var name: String = "",
        var type: String = "",
        var options: Map<String, String>) {

    init {
        options = HashMap()
    }
}