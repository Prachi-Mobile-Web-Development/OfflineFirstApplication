package com.example.offlinefirstapplication.data.mapper

import com.example.offlinefirstapplication.data.dto.PostDto
import com.example.offlinefirstapplication.data.local.PostEntity
import com.example.offlinefirstapplication.domain.model.Post

fun PostDto.toEntity(): PostEntity =
    PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )

