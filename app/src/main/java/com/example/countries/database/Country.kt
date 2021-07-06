package com.example.countries.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "all_countries")
data class Country(
        @ColumnInfo(name = "title")
        var name: String,
        @ColumnInfo(name = "amount")
        var capital: Double,
        @ColumnInfo(name = "type")
        var region: String,
        @ColumnInfo(name = "category")
        var subregion: String,
        @ColumnInfo(name = "date")
        var population: String,

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0
) : Serializable