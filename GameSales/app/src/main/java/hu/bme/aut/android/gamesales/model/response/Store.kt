package hu.bme.aut.android.gamesales.model.response

data class Store(
    val storeID: String,
    val storeName: String,
    val isActive: String,
    val images: StoreImage,
)
