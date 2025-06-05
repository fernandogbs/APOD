import model.ApodResponse
import network.ApodApiService

class Apod(private val api: ApodApiService, private val apiKey: String) {

    suspend fun buscarDados(parametros: Map<String, Any?> = emptyMap()): List<ApodResponse> {
        val data = parametros["date"] as String?
        val dataInicio = parametros["startDate"] as String?
        val dataFim = parametros["endDate"] as String?
        val quantidade = parametros["count"] as Int?

        return when {
            data != null -> listOf(api.getApodSingle(data, apiKey))
            dataInicio != null -> api.getApodMultiple(startDate = dataInicio, endDate = dataFim, apiKey = apiKey)
            quantidade != null -> api.getApodMultiple(count = quantidade, apiKey = apiKey)
            else -> listOf(api.getApodSingle(apiKey = apiKey))
        }
    }

    fun mostrarResultados(respostas: List<ApodResponse>) {
        println("Imagens encontradas:")
        respostas.forEach {
            println("\n-----")
            println("Direitos autorais: ${it.copyright ?: "N/A"}")
            println("Data: ${it.date}")
            println("Título: ${it.title}")
            println("Explicação: ${it.explanation}")
            println("HD URL: ${it.hdUrl}")
            println("URL: ${it.url}")
            println("Tipo de mídia: ${it.mediaType ?: "N/A"}")
            println("Versão do serviço: ${it.serviceVersion ?: "N/A"}")
        }
    }
}