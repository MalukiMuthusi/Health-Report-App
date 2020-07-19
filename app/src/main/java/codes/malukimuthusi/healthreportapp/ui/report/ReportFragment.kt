package codes.malukimuthusi.healthreportapp.ui.report

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.dataModels.Offence
import codes.malukimuthusi.healthreportapp.databinding.FragmentReportBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class ReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel
    private lateinit var binding: FragmentReportBinding
    private val db = Firebase.firestore
    private lateinit var sharedPref: SharedPreferences

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

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        viewClickListerners()
        showAdminFeatures()


        // give option to view Reports
        shouldShowOption()
    }

    private fun viewClickListerners() {

        binding.fab.setOnClickListener {

            db.collection("offences")
                .add(getReport())
                .addOnSuccessListener {
                    Snackbar.make(binding.fab, "Offence Reported", Snackbar.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Timber.e(it)
                }

        }

        binding.fabreports.setOnClickListener {
            findNavController().navigate(ReportFragmentDirections.actionNavigationReportToReportsViewFragment())
        }
    }

    private fun getReport(): Offence {

        val mattId = binding.mattID.editText?.text.toString()
        val culprit = binding.offender.editText?.text.toString()
        val routeName = binding.route.editText?.text.toString()
        val saccoName = binding.sacco.editText?.text.toString()
        val shortSummary = binding.shortDescription.editText?.text.toString()
        val longDescription = binding.longDescription.editText?.text.toString()

        val offence = Offence(mattId, culprit, shortSummary, longDescription, routeName, saccoName)

        return offence
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                // enable admin features
                changeFlagToAdmin()
                showAdminFeatures()
            } else {
                // sign in failed
                if (response == null) {
                    // user pressesd back button
                    changeFlagToAdmin()
                    showAdminFeatures() // TODO remember to remove this.
                    Snackbar.make(binding.fab, "Failed to Log In", Snackbar.LENGTH_LONG).show()
                    return
                }

                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    // no network connections inform the user
                    changeFlagToAdmin()
                    showAdminFeatures()// TODO remember to remove this
                    Snackbar.make(binding.fab, "Network Error", Snackbar.LENGTH_LONG).show()
                    return
                }

                // Log the Error message.
            }
        }
    }

    private fun changeFlagToAdmin() {
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.admin_string), true)
            apply()
        }
    }

    // show admin features
    private fun showAdminFeatures() {
        val isAdmin = sharedPref.getBoolean(getString(R.string.admin_string), false)

        if (isAdmin) {
            binding.fabreports.visibility = View.VISIBLE
        }


    }


    private fun shouldShowOption() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val showAccountOption = sharedPref.getBoolean(getString(R.string.select_account), true)

        if (showAccountOption) {
            // Show Dialog
            dialog()

            // don't show account option again
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.select_account), false)
                    .apply()
            }
        }
    }

    // show dialog for to choose account type
    private fun dialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Login To Admin Account")
            .setMessage("To view Reportd Cases")
            .setPositiveButton("ACCEPT") { d, which ->
                logInForAdmin()
            }
            .setNegativeButton("DECLINE") { d, which -> }
            .setNeutralButton("CANCEL") { d, which -> }
            .show()
    }

    // Login Admin
    private fun logInForAdmin() {
        val auth = FirebaseAuth.getInstance()

        val actionCodeString = ActionCodeSettings.newBuilder()
            .setAndroidPackageName("codes.malukimuthusi.healthreportapp", true, null)
            .setHandleCodeInApp(true)
            .setUrl("https://google.com")
            .build()

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeString)
                .build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val loginIntent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        if (auth.currentUser == null) {
            // not signed in
            startActivityForResult(loginIntent, RC_SIGN_IN)
        }

        auth.currentUser?.let {
            // user is signed in
            showAdminFeatures()
        }

    }

    companion object {
        const val RC_SIGN_IN = 8925
    }
}
