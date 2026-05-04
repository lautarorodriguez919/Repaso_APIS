package com.example.apilistapp.data.mapper

import com.example.apilistapp.data.local.entity.SWCharacterEntity
import com.example.apilistapp.data.remote.dto.SWCharacterDto
import com.example.apilistapp.domain.SWCharacter

fun SWCharacterDto.toDomain(): SWCharacter {
    val info = volumeInfo
    val rawThumb = info.imageLinks?.thumbnail
        ?: info.imageLinks?.smallThumbnail
        ?: ""
    val thumbnail = rawThumb.replace("http://", "https://")

    return SWCharacter(
        id            = id,
        title         = info.title         ?: "Títol desconegut",
        authors       = info.authors       ?: emptyList(),
        publisher     = info.publisher     ?: "",
        publishedDate = info.publishedDate ?: "",
        description   = info.description   ?: "Sense descripció",
        pageCount     = info.pageCount     ?: 0,
        categories    = info.categories    ?: emptyList(),
        averageRating = info.averageRating ?: 0.0,
        language      = info.language      ?: "",
        thumbnail     = thumbnail
    )
}

fun SWCharacter.toEntity(): SWCharacterEntity {
    return SWCharacterEntity(
        id            = id,
        title         = title,
        authors       = authors.joinToString(","),
        publisher     = publisher,
        publishedDate = publishedDate,
        description   = description,
        pageCount     = pageCount,
        categories    = categories.joinToString(","),
        averageRating = averageRating,
        language      = language,
        thumbnail     = thumbnail
    )
}

fun SWCharacterEntity.toDomain(): SWCharacter {
    return SWCharacter(
        id            = id,
        title         = title,
        authors       = authors.split(",").filter { it.isNotBlank() },
        publisher     = publisher,
        publishedDate = publishedDate,
        description   = description,
        pageCount     = pageCount,
        categories    = categories.split(",").filter { it.isNotBlank() },
        averageRating = averageRating,
        language      = language,
        thumbnail     = thumbnail
    )
}