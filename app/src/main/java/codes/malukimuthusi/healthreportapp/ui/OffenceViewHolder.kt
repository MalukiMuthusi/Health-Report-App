package codes.malukimuthusi.healthreportapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import codes.malukimuthusi.healthreportapp.dataModels.Offence
import codes.malukimuthusi.healthreportapp.databinding.ReportedCasesSingleItemBinding

class OffenceViewHolder private constructor(private val binding: ReportedCasesSingleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(offence: Offence) {
        binding.offence = offence
    }

    companion object {
        fun instance(parent: ViewGroup): OffenceViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ReportedCasesSingleItemBinding.inflate(layoutInflater, parent, false)
            return OffenceViewHolder(binding)
        }
    }
}