package network

import model.EarthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthApiService {
    @GET("earth/assets")
    suspend fun getEarthImage(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("dim") dim: Double? = null,
        @Query("date") date: String? = null,
        @Query("api_key") apiKey: String
    ): EarthResponse
}