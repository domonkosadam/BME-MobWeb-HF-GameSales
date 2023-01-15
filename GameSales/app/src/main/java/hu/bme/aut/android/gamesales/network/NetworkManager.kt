package hu.bme.aut.android.gamesales.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.gamesales.model.DealState
import hu.bme.aut.android.gamesales.model.GameInfoState
import hu.bme.aut.android.gamesales.model.StoreState
import hu.bme.aut.android.gamesales.model.response.DealInfo
import hu.bme.aut.android.gamesales.model.response.GameInfo
import hu.bme.aut.android.gamesales.model.response.Store
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val retrofitSteam: Retrofit
    private val gameApi: GameApi
    private val steamApi: SteamApi

    private const val SERVICE_URL = "https://www.cheapshark.com/api/1.0/"
    private const val STEAM_URL = "https://store.steampowered.com/api/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gameApi = retrofit.create(GameApi::class.java)

        retrofitSteam = Retrofit.Builder()
            .baseUrl(STEAM_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        steamApi = retrofitSteam.create(SteamApi::class.java)
    }

    fun getDeals(storeId: String?, lowestPrice: Int?, highestPrice: Int?, title: String?, onSale: Int?): LiveData<DealState> {

        val resultData = MutableLiveData<DealState>()
        resultData.value = DealState.inProgress

        gameApi.getDeals(storeId, lowestPrice,highestPrice, title, onSale).enqueue(object : Callback<List<DealInfo>> {
            override fun onResponse(
                call: Call<List<DealInfo>>,
                response: Response<List<DealInfo>>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { DealState.dealsResponseSuccess(it) })
                } else {
                    resultData.postValue(DealState.dealsResponseError(response.message()))
                }
            }

            override fun onFailure(call: Call<List<DealInfo>?>, throwable: Throwable) {
                resultData.postValue(DealState.dealsResponseError("ERROR"))
            }
        })

        return resultData
    }
    fun getStores(): LiveData<StoreState> {
        val resultData = MutableLiveData<StoreState>()
        resultData.value = StoreState.inProgress

        gameApi.getStores().enqueue(object : Callback<List<Store>> {
            override fun onResponse(
                call: Call<List<Store>>,
                response: Response<List<Store>>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { StoreState.dealsResponseSuccess(it) })
                } else {
                    resultData.postValue(StoreState.dealsResponseError(response.message()))
                }
            }

            override fun onFailure(call: Call<List<Store>?>, throwable: Throwable) {
                resultData.postValue(StoreState.dealsResponseError("ERROR"))
            }
        })
        return resultData
    }

    fun getGameInfo(appId: String): LiveData<GameInfoState> {
        val resultData = MutableLiveData<GameInfoState>()
        resultData.value = GameInfoState.inProgress

        steamApi.getGameInfo(appId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { GameInfoState.gameResponseSuccess(JSONObject(
                            response.body()!!.string())) })
                } else {
                    resultData.postValue(GameInfoState.gameResponseError(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                resultData.postValue(GameInfoState.gameResponseError("ERROR"))
            }
        })
        return resultData
    }


}