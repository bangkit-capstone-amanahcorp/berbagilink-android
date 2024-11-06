package com.example.ptamanah.adapter.navigation

data class NavMenuItem(
    val id: String,
    val title: String,
    val icon: Int? = null,
    val badge: String? = null,
    var children: List<NavMenuItem> = emptyList(),
    var level: Int = 0
)