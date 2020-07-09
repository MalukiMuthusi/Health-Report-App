package codes.malukimuthusi.healthreportapp.dataModels

data class KenyaSummary(
    val confirmed: SummaryItem,
    val recovered: SummaryItem,
    val deaths: SummaryItem,
    val lastUpdate: String
)

data class SummaryItem(
    val value: Int,
    val detail: String
)