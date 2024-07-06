package com.example.sitconGua.models

import kotlinx.serialization.Serializable

@Serializable
data class AED(val name: String, val url: String, val loc: String)

@Serializable
data class AEDList(val data: List<AED>)