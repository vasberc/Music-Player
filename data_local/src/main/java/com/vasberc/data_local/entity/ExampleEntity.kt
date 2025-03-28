package com.vasberc.data_local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExampleEntity(
    @PrimaryKey(true)
    val id: Int
)