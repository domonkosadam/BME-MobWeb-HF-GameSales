package hu.bme.aut.android.gamesales.repository

import androidx.lifecycle.LiveData
import hu.bme.aut.android.gamesales.model.DealState
import hu.bme.aut.android.gamesales.model.GameInfoState
import hu.bme.aut.android.gamesales.model.StoreState
import hu.bme.aut.android.gamesales.network.NetworkManager

class GameApiRepository {

    fun getDeals(storeId: String?, lowestPrice: Int?, highestPrice: Int?, title: String?, onSale: Boolean?): LiveData<DealState> {
        val onSaleInt = if (onSale == null) null else if (onSale) 1 else 0
        return NetworkManager.getDeals(storeId, lowestPrice, highestPrice, title, onSaleInt)
    }

    fun getStores(): LiveData<StoreState> {
        return NetworkManager.getStores()
    }

    fun getDealLink(dealId: String): String {
        return "https://www.cheapshark.com/redirect?dealID=${dealId}"
    }


    fun getGameInfo(appId: String): LiveData<GameInfoState> {
        return NetworkManager.getGameInfo(appId)
    }

}