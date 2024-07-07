package com.example.sitconGua

import com.example.sitconGua.utils.httpClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val client = httpClient
        runBlocking {
            val res = client.getAEDList(mapOf("lat" to "25.126631", "lng" to "121.466292", "limit" to "10"))
            println(res)
        }

    }
}