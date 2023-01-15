package hu.bme.aut.android.gamesales.model

import hu.bme.aut.android.gamesales.model.response.Store

sealed class StoreState {
    object inProgress : StoreState()
    data class dealsResponseSuccess(val data: List<Store>) : StoreState()
    data class dealsResponseError(val exceptionMsg: String) : StoreState()
}