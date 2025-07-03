package com.jewellerycalculator.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import android.content.Context
import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.MetalType

@Database(
    entities = [BillItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BillDatabase : RoomDatabase() {
    abstract fun billItemDao(): BillItemDao
    
    companion object {
        @Volatile
        private var INSTANCE: BillDatabase? = null
        
        fun getDatabase(context: Context): BillDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BillDatabase::class.java,
                    "bill_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromMetalType(value: MetalType): String {
        return value.name
    }
    
    @TypeConverter
    fun toMetalType(value: String): MetalType {
        return MetalType.valueOf(value)
    }
}