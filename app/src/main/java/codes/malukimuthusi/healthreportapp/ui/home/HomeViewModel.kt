package codes.malukimuthusi.healthreportapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.malukimuthusi.healthreportapp.WebService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class HomeViewModel : ViewModel() {

    val retofit = Retrofit.Builder()
        .baseUrl("https://covid19.mathdro.id/api/")
        .build()

    fun fetching() {
        val service = retofit.create(WebService::class.java)

        viewModelScope.launch {

            // TODO put call in try block
            val answer = service.fetchKenyaData()

            when {
                answer.isSuccessful -> {
                    // TODO request succeeded
                }

                answer.errorBody() != null -> {
                    // TODO server error
                }

                else -> {
                    // TODO response with emty body
                }
            }
        }
    }

}