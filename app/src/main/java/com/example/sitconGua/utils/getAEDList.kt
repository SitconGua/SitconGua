package com.example.sitconGua.utils


import com.example.sitconGua.models.AEDList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

object httpClient {
    private const val BASE_URL = "http://192.168.169.159:8088/api/v1/dashboard/nearbyAED"
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAEDList(params: Map<String, String>): AEDList {
        client.use { client ->
            val res = client.get(BASE_URL) {
                url {
                    params.forEach { (key, value) -> parameters.append(key, value) }
                }
            }
            val body = res.body<AEDList>()
            return body
        }
    }
}

