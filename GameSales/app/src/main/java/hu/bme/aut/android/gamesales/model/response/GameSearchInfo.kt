package hu.bme.aut.android.gamesales.model.response

data class GameSearchInfo (
    val gameID: String,
    val steamAppID: String,
    val cheapest: String,
    val cheapestDealID: String,
    val external: String,
    val thumb: String,
)