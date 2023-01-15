package hu.bme.aut.android.gamesales.model.response

data class GameInfo(
    val storeID: String,
    val gameID: String,
    val name: String,
    val steamAppID: String,
    val salePrice: String,
    val retailPrice: String,
    val steamRatingText: String,
    val steamRatingPercent: String,
    val steamRatingCount: String,
    val metacriticScore: String,
    val metacriticLink: String,
    val releaseDate: Int,
    val publisher: String,
    val steamworks: String,
    val thumb: String,
)
