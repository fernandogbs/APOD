import model.ApodResponse
import network.ApodApiService

class Apod(private val api: ApodApiService, private val apiKey: String) {
    suspend fun fetchData(params: Map<String, Any?> = emptyMap()): List<ApodResponse> {
        val date = params["date"] as String?
        val startDate = params["startDate"] as String?
        val endDate = params["endDate"] as String?
        val count = params["count"] as Int?

        return when {
            date != null -> listOf(api.getApodSingle(date, apiKey))
            startDate != null -> api.getApodMultiple(startDate = startDate, endDate = endDate, apiKey = apiKey)
            count != null -> api.getApodMultiple(count = count, apiKey = apiKey)
            else -> listOf(api.getApodSingle(apiKey = apiKey))
        }
    }

    fun displayResults(responses: List<ApodResponse>) {
        println("Imagens")
        responses.forEach {
            println("\n-----")
            println("Direitos autorais: ${if (it.copyright == null) "N/A" else it.copyright}")
            println("date: ${it.date}")
            println("Titulo: ${it.title}")
            println("Explicação: ${it.explanation}")
            println("HD URL: ${it.hdUrl}")
            println("URL: ${it.url}")
            println("Tipo de arquivo: ${if (it.mediaType == null) "N/A" else it.mediaType}")
            println("Versão do serviço: ${if (it.serviceVersion == null) "N/A" else it.serviceVersion}")
        }
    }
}