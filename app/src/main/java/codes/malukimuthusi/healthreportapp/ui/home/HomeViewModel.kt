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
    var confirmed = "-"
    var recorvered = "-"
    var deaths = "-"

    // live data
    private val _kenyaData = MutableLiveData<KenyaSummary>()
    val kenyaData: LiveData<KenyaSummary>
        get() = _kenyaData

    // toast for error
    private val _toast = MutableLiveData<Boolean>()
    val showErrorToast: LiveData<Boolean>
        get() = _toast

    // toast for success


    fun fetching(which: Boolean = false) {
        val service = Covid19APIService.webService


        try {
            CoroutineScope(Dispatchers.Main).launch {
                if (which) {
                    _kenyaData.value = service.fetchKenyaData()
                    confirmed = _kenyaData.value?.confirmed?.value.toString()
                    recorvered = _kenyaData.value?.recovered?.value.toString()
                    deaths = _kenyaData.value?.deaths?.value.toString()
                }else{
                    _kenyaData.value = service.fetchGlobal()
                    confirmed = _kenyaData.value?.confirmed?.value.toString()
                    recorvered = _kenyaData.value?.recovered?.value.toString()
                    deaths = _kenyaData.value?.deaths?.value.toString()
                }

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

    // make a call to web service, to fetch data
    fun fetchData(apiPoint: String) {
        val service = Covid19APIService.webService

        try {
            CoroutineScope(Dispatchers.Main).launch {
                _kenyaData.value = service.fetchData(apiPoint)

                confirmed = _kenyaData.value?.confirmed?.value.toString()
                recorvered = _kenyaData.value?.recovered?.value.toString()
                deaths = _kenyaData.value?.deaths?.value.toString()
            }
        } catch (e: Error) {
            Timber.e(e)
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }

}