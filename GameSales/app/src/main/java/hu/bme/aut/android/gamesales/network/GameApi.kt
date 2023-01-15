package hu.bme.aut.android.gamesales.network

import hu.bme.aut.android.gamesales.model.response.DealInfo
import hu.bme.aut.android.gamesales.model.response.GameInfo
import hu.bme.aut.android.gamesales.model.response.GameSearchInfo
import hu.bme.aut.android.gamesales.model.response.Store
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET("deals")
    fun getDeals(
        @Query("storeID") storeID: String?,
        @Query("lowerPrice") lowestPrice: Int?,
        @Query("upperPrice") highestPrice: Int?,
        @Query("title") title: String?,
        @Query("onSale") onSale: Int?,

    ): Call<List<DealInfo>>

    @GET("stores")
    fun getStores(): Call<List<Store>>
}