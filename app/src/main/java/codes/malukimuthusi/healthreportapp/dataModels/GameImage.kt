package codes.malukimuthusi.healthreportapp.dataModels

import codes.malukimuthusi.healthreportapp.R

data class GameImage(
    var resource: Int = R.drawable.ic_texture_black_24dp,
    var imageRevealed: Boolean = false,
    var imageIndex: Int = 0

)