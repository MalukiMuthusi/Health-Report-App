package codes.malukimuthusi.healthreportapp.dataModels

data class KenyaSummary(
    val confirmed: SummaryItem? = SummaryItem(4000),
    val recovered: SummaryItem? = SummaryItem(),
    val deaths: SummaryItem? = SummaryItem(1000),
    val lastUpdate: String? = "2020"
)

data class SummaryItem(
    val value: Int? = 2000,
    val detail: String? = "url"
)
