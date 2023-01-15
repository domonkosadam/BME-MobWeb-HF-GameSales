package hu.bme.aut.android.gamesales.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.gamesales.model.response.DealInfo

@Database(entities = [DealInfo::class], version = 1)

abstract class DealInfoDatabase : RoomDatabase() {
    abstract fun dealInfoDao(): DealInfoDao.ShoppingItemDao

    companion object {
        fun getDatabase(applicationContext: Context): DealInfoDatabase {
            return Room.databaseBuilder(
                applicationContext,
                DealInfoDatabase::class.java,
                "deal-list"
            ).build();
        }
    }
}