package codes.malukimuthusi.healthreportapp.ui.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import codes.malukimuthusi.healthreportapp.R

// culprit string
@BindingAdapter("culpritString")
fun culpritString(view: TextView, culprit: String) {
    view.text = view.context.getString(R.string.culprit, culprit)
}

// number plate number string
@BindingAdapter("numberPlate")
fun numberPlate(view: TextView, numberPlate: String) {
    view.text = view.context.getString(R.string.number_plate, numberPlate)
}

// route name string
@BindingAdapter("routeName")
fun routeName(view: TextView, routeName: String) {
    view.text = view.context.getString(R.string.route_name, routeName)
}

// sacco name
@BindingAdapter("saccoName")
fun saccoName(view: TextView, saccoName: String) {
    view.text = view.context.getString(R.string.sacco_name, saccoName)
}

// Images view for the game
