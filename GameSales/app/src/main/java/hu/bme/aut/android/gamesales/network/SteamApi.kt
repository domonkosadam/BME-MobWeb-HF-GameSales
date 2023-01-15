package hu.bme.aut.android.gamesales.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApi {
    @GET("appdetails")
    fun getGameInfo(@Query("appids") appId: String): Call<ResponseBody>
}