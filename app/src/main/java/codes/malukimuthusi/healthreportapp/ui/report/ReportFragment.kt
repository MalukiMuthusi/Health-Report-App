package codes.malukimuthusi.healthreportapp.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import codes.malukimuthusi.healthreportapp.dataModels.Offence
import codes.malukimuthusi.healthreportapp.databinding.FragmentReportBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class ReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel
    private lateinit var binding: FragmentReportBinding
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the adapter for the route autocomplete list.
        val list = listOf("Uhuru Road", "Lang'ata Road", "Waiyaki Way")
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            list
        )
        (binding.route.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.fab.setOnClickListener {

            val offence1 = Offence(
                "KBD 12K",
                "Driver",
                "Not Wearing Mask",
                "Driver was not wearing mask hence puting lives of other commuters in risk."
            )

            db.collection("offences")
                .add(offence1)
                .addOnSuccessListener {
                    Snackbar.make(binding.fab, "Offence Reported", Snackbar.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Timber.e(it)
                }

        }
    }
}
