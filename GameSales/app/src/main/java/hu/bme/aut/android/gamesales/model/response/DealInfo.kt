package hu.bme.aut.android.gamesales.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "deal")
data class DealInfo(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo val internalName: String?,
    @ColumnInfo val title: String?,
    @ColumnInfo val metacriticLink: String?,
    @ColumnInfo val dealID: String?,
    @ColumnInfo val storeID: String?,
    @ColumnInfo val gameID: String?,
    @ColumnInfo val salePrice: String?,
    @ColumnInfo val normalPrice: String?,
    @ColumnInfo val isOnSale: String?,
    @ColumnInfo val savings: String?,
    @ColumnInfo val metacriticScore: String?,
    @ColumnInfo val steamRatingText: String?,
    @ColumnInfo val steamRatingPercent: String?,
    @ColumnInfo val steamRatingCount: String?,
    @ColumnInfo val steamAppID: String?,
    @ColumnInfo val releaseDate: Int?,
    @ColumnInfo val lastChange: Int?,
    @ColumnInfo val dealRating: String?,
    @ColumnInfo val thumb: String?,
) : Serializable
