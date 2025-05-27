package network

import model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    @GET("apod")
    suspend fun getApodSingle(
        @Query("date") date: String? = null,
        @Query("api_key") apiKey: String
    ): ApodResponse

    @GET("apod")
    suspend fun getApodMultiple(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("count") count: Int? = null,
        @Query("api_key") apiKey: String
    ): List<ApodResponse>
}