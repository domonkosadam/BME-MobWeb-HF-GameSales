package hu.bme.aut.android.gamesales.viewmodel.gamelist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.gamesales.database.DealInfoDatabase
import hu.bme.aut.android.gamesales.model.DealState
import hu.bme.aut.android.gamesales.model.response.DealInfo
import hu.bme.aut.android.gamesales.repository.GameApiRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.InetAddress


class GameListFragmentViewModel : ViewModel() {

    private val repository: GameApiRepository

    var deals: LiveData<DealState>

    init {
        repository = GameApiRepository()
        deals = getDealsList()
    }

    private fun getDealsList(): LiveData<DealState> {
        return repository.getDeals(null, null, null, null, null)
    }
}