package com.subakstudio.util

import java.io.InputStream
import java.io.PrintStream
import java.util.*

/**
 * Created by yeoupooh on 4/11/16.
 */

class RedirectIO {
    companion object {
        fun redirect(src: InputStream?, dest: PrintStream?) {
            Thread(Runnable {
                var sc = Scanner(src)
                while (sc.hasNextLine()) {
                    dest!!.println(sc.nextLine());
                }
            }).start()
        }
    }
}
