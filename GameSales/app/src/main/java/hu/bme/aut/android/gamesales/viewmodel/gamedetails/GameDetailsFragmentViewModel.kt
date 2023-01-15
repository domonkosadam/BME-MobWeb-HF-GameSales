package hu.bme.aut.android.gamesales.viewmodel.gamedetails

import android.text.Html
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.gamesales.model.GameInfoState
import hu.bme.aut.android.gamesales.model.StoreState
import hu.bme.aut.android.gamesales.repository.GameApiRepository
import org.json.JSONObject

class GameDetailsFragmentViewModel : ViewModel() {

    private val repository: GameApiRepository

    init {
        repository = GameApiRepository()
    }

    fun getStores(): LiveData<StoreState> {
        return repository.getStores()
    }

    fun getStoreLink(dealLink: String): String {
        return repository.getDealLink(dealLink)
    }

    fun getGameInfo(appId: String): LiveData<GameInfoState> {
        return repository.getGameInfo(appId)
    }

    fun getDescription(data: JSONObject, gameId: String): String {
        var result = ""
        if (data.has(gameId)) {
            val d1 = data.getJSONObject(gameId)
            if (d1.has("data")){
                val d2 = d1.getJSONObject("data")
                if (d2.has("short_description")) {
                    result = d2.getString("short_description")
                }
            }
        }
        return result
    }

    fun getMinRequirements(data: JSONObject, gameId: String): String {
        var result = ""
        if (data.has(gameId)) {
            val d1 = data.getJSONObject(gameId)
            if (d1.has("data")){
                val d2 = d1.getJSONObject("data")
                if (d2.has("pc_requirements")) {
                    val d3 = d2.getJSONObject("pc_requirements")
                    if (d3.has("minimum")){
                        //result = Html.fromHtml(d3.getString("minimum")).toString()
                        result = d3.getString("minimum").replace("<br>", "\n").replace("\\/", "").replace("<.*?>".toRegex(), "")
                    }
                }
            }
        }
        return result
    }

    fun getRecommendedRequirements(data: JSONObject, gameId: String): String {
        var result = ""
        if (data.has(gameId)) {
            val d1 = data.getJSONObject(gameId)
            if (d1.has("data")){
                val d2 = d1.getJSONObject("data")
                if (d2.has("pc_requirements")) {
                    val d3 = d2.getJSONObject("pc_requirements")
                    if (d3.has("recommended")){
                        //result = Html.fromHtml(d3.getString("recommended")).toString()
                        result = d3.getString("recommended").replace("<br>", "\n").replace("\\/", "").replace("<.*?>".toRegex(), "")
                    }
                }
            }
        }
        return result
    }
}