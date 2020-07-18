package codes.malukimuthusi.healthreportapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.compound_view.view.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        binding.chart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 60f
            transparentCircleRadius = 0f
            invalidate()
        }
        homeViewModel.fetching()

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.kenya -> {
                    Toast.makeText(requireContext(), "KENYA selected", Toast.LENGTH_SHORT).show()
//                    homeViewModel.fetching()
                }
            }
        }



        homeViewModel.kenyaData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.lastUpdate, Toast.LENGTH_SHORT).show()
            binding.confirmed.stat_value.text = it.confirmed?.value.toString()
            binding.deaths.stat_value.text = it.deaths?.value.toString()
            binding.recovered.stat_value.text = it.recovered?.value.toString()

            setUpPieChart(
                it.recovered?.value?.toFloat() ?: 0f,
                it.confirmed?.value?.toFloat() ?: 0f,
                it.deaths?.value?.toFloat() ?: 0f
            )

        })

        homeViewModel.showErrorToast.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Failed to Get Data", Toast.LENGTH_SHORT)
                    .show()
                homeViewModel.toastShown()
            }
        })

        return binding.root
    }


    private fun setUpPieChart(recovered: Float, inflected: Float, dead: Float) {
        // list of data entries for the pie chart
        val pieList = listOf(
            PieEntry(recovered, "Recovered"),
            PieEntry(inflected, "Confirmed"),
            PieEntry(dead, "Deaths")
        )

        val dataSet = PieDataSet(pieList, "Covid-19 Stats")
        dataSet.apply {
            colors = getColorList()
            setDrawValues(false)
        }
        binding.chart.data = PieData(dataSet)
        binding.chart.invalidate()
    }

    private fun getColorList(): List<Int> {
        return listOf(
            getColor(R.color.GREEN_A_700), getColor(R.color.YELLOW_A_700),
            getColor(R.color.RED_A_700)
        )
    }

    private fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(requireContext(), colorId)
    }
}
