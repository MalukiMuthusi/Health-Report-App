package codes.malukimuthusi.healthreportapp.ui.gameInfo

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
import codes.malukimuthusi.healthreportapp.databinding.FragmentGameInfoBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.compound_view.view.*

class GameInfoFragment : Fragment() {

    private val viewModel: GameInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentGameInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.fetching()

        binding.piChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 60f
            transparentCircleRadius = 0f
            invalidate()
        }


        binding.chipGroupGame.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.global -> {
                    Toast.makeText(requireContext(), "GLOBAL selecetd", Toast.LENGTH_SHORT).show()
//                    viewModel.fetching()
                }
            }
        }



        viewModel.kenyaData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.lastUpdate, Toast.LENGTH_SHORT).show()
            binding.confirmedGame.stat_value.text = it.confirmed?.value.toString()
            binding.deathsGame.stat_value.text = it.deaths?.value.toString()
            binding.recoveredGame.stat_value.text = it.recovered?.value.toString()

            setUpPieChart(
                it.recovered?.value?.toFloat() ?: 0f,
                it.confirmed?.value?.toFloat() ?: 0f,
                it.deaths?.value?.toFloat() ?: 0f
            )

        })

        viewModel.showErrorToast.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Failed to Get Data", Toast.LENGTH_SHORT)
                    .show()
                viewModel.toastShown()
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
        binding.piChart.data = PieData(dataSet)
        binding.piChart.invalidate()
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
