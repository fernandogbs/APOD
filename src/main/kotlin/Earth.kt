import model.EarthResponse
import network.EarthApiService

class Earth(private val api: EarthApiService, private val apiKey: String) {
    suspend fun fetchData(lat: Double, lon: Double, dim: Double? = null, date: String? = null): EarthResponse {
        return api.getEarthImage(lat, lon, dim, date, apiKey)
    }

    fun displayResults(response: EarthResponse) {
        println("\n-----")
        println("Latitude: ${response.lat}")
        println("Longitude: ${response.lon}")
        println("Dimensão: ${response.dim}")
        println("Data: ${response.date}")
    }
}