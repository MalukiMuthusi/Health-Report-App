package codes.malukimuthusi.healthreportapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import codes.malukimuthusi.healthreportapp.Covid19APIService
import codes.malukimuthusi.healthreportapp.dataModels.KenyaSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    // live data
    private val _kenyaData = MutableLiveData<KenyaSummary>()
    val kenyaData: LiveData<KenyaSummary>
        get() = _kenyaData

    // toast for error
    private val _toast = MutableLiveData<Boolean>()
    val showErrorToast: LiveData<Boolean>
        get() = _toast

    // toast for success


    fun fetching() {
        val service = Covid19APIService.webService

        try {
            CoroutineScope(Dispatchers.Main).launch {
                _kenyaData.value = service.fetchKenyaData()
            }

        } catch (e: Exception) {
            Timber.e(e, "Error Fetching Country Data")
            errorToast()
        }


    }

    private fun errorToast() {
        _toast.value = true
    }

    fun toastShown() {
        _toast.value = false
    }

}