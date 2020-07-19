package codes.malukimuthusi.healthreportapp.ui.gameInfo

import androidx.lifecycle.ViewModel
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.dataModels.GameImage

class GameInfoViewModel : ViewModel() {

    val resourcesList = listOf(
        R.drawable.ic_noun_avoid_hand_shake_3365076,
        R.drawable.ic_noun_avoid_hand_shake_3365076,
        R.drawable.ic_noun_cleaning_3311960,
        R.drawable.ic_noun_cleaning_3311960,
        R.drawable.ic_noun_cough_630783,
        R.drawable.ic_noun_cough_630783,
        R.drawable.ic_noun_crowded_place_3307204,
        R.drawable.ic_noun_crowded_place_3307204,
        R.drawable.ic_noun_flight_delay_3365080,
        R.drawable.ic_noun_flight_delay_3365080,
        R.drawable.ic_noun_soclai_distance_3365071,
        R.drawable.ic_noun_soclai_distance_3365071
    )

    // toast for success
    val imageStateList = listOf(
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage(),
        GameImage()
    )

    // track the clicked image
    var currentClickedImage: GameImage = GameImage(imageIndex = -1)


}