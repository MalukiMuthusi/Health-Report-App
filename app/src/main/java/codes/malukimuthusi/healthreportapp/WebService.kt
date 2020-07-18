package codes.malukimuthusi.healthreportapp

import codes.malukimuthusi.healthreportapp.dataModels.KenyaSummary
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {
    @GET("api/countries/ke")
    suspend fun fetchKenyaData(): KenyaSummary

    @GET("api")
    suspend fun fetchGlobal(): KenyaSummary
}

object Covid19APIService {
    val baseURL = "https://covid19.mathdro.id/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val webService = retrofit.create(WebService::class.java)
}

