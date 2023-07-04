package com.example.errorquestion.entity

data class EnterBean(
    val log_id: String,
    val words_result: List<WordsResult>,
    val words_result_num: Int
)

data class WordsResult(
    val location: Location,
    val words: String
)

data class Location(
    val height: Int,
    val left: Int,
    val top: Int,
    val width: Int
)