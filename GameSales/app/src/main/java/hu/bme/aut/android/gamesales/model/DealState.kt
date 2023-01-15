package hu.bme.aut.android.gamesales.model

import hu.bme.aut.android.gamesales.model.response.DealInfo

sealed class DealState {
    object inProgress : DealState()
    data class dealsResponseSuccess(val data: List<DealInfo> ) : DealState()
    data class dealsResponseError(val exceptionMsg: String) : DealState()
}