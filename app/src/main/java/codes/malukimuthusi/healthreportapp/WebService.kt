package codes.malukimuthusi.healthreportapp

import codes.malukimuthusi.healthreportapp.dataModels.KenyaSummary
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {
    @GET("countries/ke")
    suspend fun fetchKenyaData(): KenyaSummary
}

object Covid19APIService {
    val baseURL = "https://covid19.mathdro.id/api/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val webService = retrofit.create(WebService::class.java)
}

