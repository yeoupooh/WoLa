package com.subakstudio.crypto

import org.junit.Test
import java.security.MessageDigest
import java.util.*

/**
 * Created by yeoupooh on 16. 4. 12.
 */
class CryptoTest {
    // https://quickhash.com/
    @Test fun testSha256() {
        org.junit.Assert.assertEquals("n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=", sha256("test"))
    }

    fun sha256(str: String): String {
        var sh: MessageDigest = MessageDigest.getInstance("SHA-256")
        sh.update(str.toByteArray())
        return Base64.getEncoder().encodeToString(sh.digest())
    }
}