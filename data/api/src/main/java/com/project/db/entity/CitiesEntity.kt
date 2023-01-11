package com.project.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
class CitiesEntity(
    @PrimaryKey(autoGenerate = true)
                   val id:Int=0,
                   val key:String,
                   val region:String,
                   val city:String,) {
}