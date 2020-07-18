package codes.malukimuthusi.healthreportapp

import codes.malukimuthusi.healthreportapp.dataModels.KenyaSummary
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {
    @GET("api/countries/ke")
    suspend fun fetchKenyaData(): KenyaSummary

    @GET("api")
    suspend fun fetchGlobal(): KenyaSummary

    @GET("{uss}")
    suspend fun fetchData(@Path("uss") apipp: String): KenyaSummary
}

object Covid19APIService {
    val baseURL = "https://covid19.mathdro.id/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val webService = retrofit.create(WebService::class.java)
}

