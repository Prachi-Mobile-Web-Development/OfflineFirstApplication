package com.example.offlinefirstapplication.data.mapper

import com.example.offlinefirstapplication.data.local.PostEntity
import com.example.offlinefirstapplication.domain.model.Post

fun PostEntity.toDomain(): Post =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )