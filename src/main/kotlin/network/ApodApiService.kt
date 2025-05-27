package network

import model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    @GET("planetary/apod")
    suspend fun getApodSingle(
        @Query("date") date: String? = null,
        @Query("api_key") apiKey: String
    ): ApodResponse

    @GET("planetary/apod")
    suspend fun getApodMultiple(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("count") count: Int? = null,
        @Query("api_key") apiKey: String
    ): List<ApodResponse>
}