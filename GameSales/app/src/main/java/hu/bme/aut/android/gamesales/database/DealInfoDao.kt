package hu.bme.aut.android.gamesales.database

import androidx.room.*
import hu.bme.aut.android.gamesales.model.response.DealInfo

class DealInfoDao {
    @Dao
    interface ShoppingItemDao {
        @Query("SELECT * FROM deal")
        fun getAll(): List<DealInfo>

        @Query("DELETE FROM deal")
        fun deleteAll()

        @Insert
        fun insert(shoppingItems: DealInfo): Long

        @Update
        fun update(shoppingItem: DealInfo)

        @Delete
        fun deleteItem(shoppingItem: DealInfo)
    }
}