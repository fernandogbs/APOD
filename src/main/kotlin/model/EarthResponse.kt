package model

data class EarthResponse(
    val date: String,
    val id: String,
    val resource: Resource,
    val url: String,
    val serviceVersion: String
)

data class Resource(
    val dataset: String,
    val planet: String
)
