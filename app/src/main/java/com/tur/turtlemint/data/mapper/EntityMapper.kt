package com.tur.turtlemint.data.mapper

import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity

fun IssueResponse.toEntity() = IssueEntity(
    id = id!!.toLong(),
    title = title!!,
    desc = body!!,
    uName = user!!.login!!,
    uDate = updatedAt!!,
)
