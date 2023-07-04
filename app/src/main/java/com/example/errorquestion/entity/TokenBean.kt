package com.example.errorquestion.entity

data class TokenBean(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val session_key: String,
    val session_secret: String
)