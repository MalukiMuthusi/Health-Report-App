package codes.malukimuthusi.healthreportapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.kenya -> {
                    Toast.makeText(requireContext(), "KENYA selected", Toast.LENGTH_SHORT).show()
                }

                R.id.global -> {
                    Toast.makeText(requireContext(), "GLOBAL selecetd", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setUpPieChart()

        return binding.root
    }


    private fun setUpPieChart() {
        // list of data entries for the pie chart
        val pieList = listOf(
            PieEntry(2000f, "Recovered"),
            PieEntry(3000f, "Infected"),
            PieEntry(1000f, "Dead")
        )

        val dataSet = PieDataSet(pieList, "Covid-19 Stats")
        dataSet.apply {
            colors = getColorList()
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
