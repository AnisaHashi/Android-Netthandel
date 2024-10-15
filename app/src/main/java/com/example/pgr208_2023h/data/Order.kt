package com.example.pgr208_2023h.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
data class OrderHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDateTime,
    val items: List<String>,
    val sumPrice: Double,
    val sumItems: Int,
)

@Dao
interface OrderHistoryDao {
    @Insert
    suspend fun addOrder(orderHistory: OrderHistory)

    @Query("SELECT * FROM orderHistory")
    fun getAllOrderes(): Flow<List<OrderHistory>>
}

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        }
    }

    @TypeConverter
    fun toTimestamp(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let {
            it.split(",").map { item -> item.trim() }
        }
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        return value?.joinToString(",") { it }
    }
}

@Database(
    entities = [OrderHistory::class],
    version = 1
)
@TypeConverters(value = [LocalDateTimeConverter::class, StringListConverter::class])
abstract class OrderesDatabase : RoomDatabase() {
    abstract val dao: OrderHistoryDao
}