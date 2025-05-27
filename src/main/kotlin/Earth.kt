import model.EarthResponse
import network.EarthApiService

class Earth(private val api: EarthApiService, private val apiKey: String) {
    suspend fun fetchData(lat: Double, lon: Double, dim: Double? = null, date: String? = null): EarthResponse {
        return api.getEarthImage(lat, lon, dim, date, apiKey)
    }

    fun displayResults(response: EarthResponse) {
        println("\n-----")
        println("Latitude: ${response.date}")
        println("Longitude: ${response.id}")
        println("Dimensão: ${response.resource.dataset}")
        println("Data: ${response.resource.planet}")
        println("Data: ${response.url}")
        println("Versão do serviço: ${response.serviceVersion}")
    }
}