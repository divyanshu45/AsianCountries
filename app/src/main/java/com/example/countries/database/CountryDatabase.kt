package com.example.countries.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [Country::class],
        version = 1,
        exportSchema = false
)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): CountryDao

    companion object {
        @Volatile
        private var instance: CountryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
                ?: synchronized(LOCK) {

                    instance
                            ?: createDatabase(
                                    context
                            )
                                    .also { instance = it }
                }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                CountryDatabase::class.java,
                "all_countries.db"
        ).build()
    }
}