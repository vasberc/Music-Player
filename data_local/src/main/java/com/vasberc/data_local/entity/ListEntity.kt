package com.vasberc.data_local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_entity")
data class ListEntity(
    @PrimaryKey(false)
    val name: String,
    val editable: Boolean
)