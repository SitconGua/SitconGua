package com.example.sitconGua.utils


import com.example.sitconGua.models.AEDList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

object httpClient {
    private const val BASE_URL = "https://api.wavjaby.nckuctf.org/api/v0/"
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAEDList(path: String, params: Map<String, String>): AEDList {
        client.use { client ->
            val res = client.get(BASE_URL + path) {
                url {
                    params.forEach { (key, value) -> parameters.append(key, value) }
                }
            }
            val body = res.body<AEDList>()
            return body
        }
    }
}

