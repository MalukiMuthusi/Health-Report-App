package codes.malukimuthusi.healthreportapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class ReportsViewFragment : Fragment() {

    companion object {
        fun newInstance() = ReportsViewFragment()
    }

    private lateinit var viewModel: ReportsViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reports_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReportsViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}