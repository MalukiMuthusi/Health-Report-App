package codes.malukimuthusi.healthreportapp

import codes.malukimuthusi.healthreportapp.dataModels.KenyaSummary
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("countries/ke")
    suspend fun fetchKenyaData(): Response<KenyaSummary>
}

