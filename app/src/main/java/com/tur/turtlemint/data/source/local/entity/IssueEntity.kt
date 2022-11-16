package com.tur.turtlemint.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Issue")
data class IssueEntity(
    @PrimaryKey var id: Long,
    var title: String?,
    var desc: String?,
    var uName: String?,
    var uDate: String?,
)
