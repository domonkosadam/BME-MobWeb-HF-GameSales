package hu.bme.aut.android.gamesales.model

import org.json.JSONObject

sealed class GameInfoState {
    object inProgress : GameInfoState()
    data class gameResponseSuccess(val data: JSONObject) : GameInfoState()
    data class gameResponseError(val exceptionMsg: String) : GameInfoState()
}