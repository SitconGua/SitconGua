package com.example.sitconGua.models

import kotlinx.serialization.Serializable

@Serializable
data class AED(val name: String, val address: String, val mapUrl: String, val place: String, val distance: Double) {
    constructor() : this("", " ", "", "",0.0)
}

@Serializable
data class AEDList(val data: List<AED>, val status: String)