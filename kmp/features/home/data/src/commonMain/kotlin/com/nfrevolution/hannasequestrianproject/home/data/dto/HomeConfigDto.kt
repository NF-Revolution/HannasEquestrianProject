package com.nfrevolution.hannasequestrianproject.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HomeConfigDto(
    @SerialName("intro_video_path")
    val introVideoPath: String,
)
