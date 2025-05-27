package model

data class ApodResponse(
    val date: String,
    val explanation: String,
    val hdUrl: String,
    val mediaType: String? = null,
    val serviceVersion: String? = null,
    val title: String,
    val url: String,
    val copyright: String? = null,
)