package com.example.rarelygroovy.data.model

data class Event(
    val id: String,
    val name: String,
    val date: String,
    val venueName: String,
    val venueCity: String,
    val venueState: String,
    val promoterName: String?,
    val flyerUrl: String?,
    val cover: Int?,
    val verified: Boolean,
    val artists: List<Artist>
)

data class Artist(
    val name: String,
    val link: String,
    val location: String,
    val genres: List<String>
)
