package com.example.rarelygroovy.data.model

data class Event(
    val _id: String, // Event's ID
    val tags: List<String>?,
    val venue: Venue,
    val date: String, // ISO 8601 date string
    val doorTime: String?, // ISO 8601 date string or null
    val dateTime: String, // ISO 8601 date string
    val cover: Int?, // API sample shows null, so Int? is correct
    val artists: List<Artist>,
    val creationDateTime: String, // ISO 8601 date string
    val flyer: String?,
    val flyerColors: List<String>?, // Assuming list of strings if ever populated
    val updates: List<UpdateInfo>?,
    val verified: Boolean,
    val __v: Int?, // Version key from MongoDB, often an Int
    val promoter: Promoter?, // Optional nested promoter object
    val tickets: String? // Optional tickets URL
)

data class UpdateInfo( // For the 'updates' array
    val date: String,
    val message: String
)
data class Artist(
    val _id: String, // Artist's own ID
    val name: String,
    val link: String?, // Top-level profile link for the artist
    val location: String?,
    val genre: List<String>, // Matches JSON key 'genre'
    val status: String?,
    val medium: String?,
    val links: ArtistLinks?, // Nested links object
    val artists: List<String>?, // JSON shows "artists": [] - assuming list of strings if ever populated, adjust if it's List<Artist>
    val end: String?,
    val notes: String?,
    val women: Boolean? // Saw this in one artist object
)

data class ArtistLinks(
    val apple: String?,
    val bandcamp: String?,
    val youtube: String?,
    val instagram: String?,
    val spotify: String?,
    val facebook: String?,
    val soundcloud: String?,
    val tiktok: String?,
    val x: String?, // For Twitter/X
    val self: String?,
    val _id: String // ID for the links object itself
)

data class Venue(
    val name: String,
    val address: String?,
    val city: String,
    val state: String,
    val country: String?,
    val link: String?,
    val _id: String // Venue's own ID
)

data class Promoter(
    val name: String,
    val link: String?,
    val _id: String
)
