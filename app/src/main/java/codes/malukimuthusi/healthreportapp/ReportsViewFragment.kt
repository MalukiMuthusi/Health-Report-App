package codes.malukimuthusi.healthreportapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import codes.malukimuthusi.healthreportapp.dataModels.Offence
import codes.malukimuthusi.healthreportapp.databinding.ReportsViewFragmentBinding
import codes.malukimuthusi.healthreportapp.ui.OffenceViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class ReportsViewFragment : Fragment() {

    companion object {
        fun newInstance() = ReportsViewFragment()
    }

    private lateinit var viewModel: ReportsViewViewModel
    private lateinit var binding: ReportsViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ReportsViewFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())

        // firestore
        val query = FirebaseFirestore.getInstance()
            .collection("offences")

        val options = FirestoreRecyclerOptions.Builder<Offence>()
            .setQuery(query, Offence::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        val recylerAdapter =
            object : FirestoreRecyclerAdapter<Offence, OffenceViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): OffenceViewHolder {
                    return OffenceViewHolder.instance(parent)
                }

                override fun onBindViewHolder(
                    holder: OffenceViewHolder,
                    position: Int,
                    model: Offence
                ) {
                    holder.bind(model)
                }
            }

        binding.recylerView.adapter = recylerAdapter
        return binding.root
    }

}