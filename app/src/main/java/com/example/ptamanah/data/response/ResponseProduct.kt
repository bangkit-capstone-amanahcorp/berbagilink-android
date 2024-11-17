package com.example.ptamanah.data.response

data class Product(
    val id: Int,
    val name: String,
    val type: String,
    val category: String,
    val price: Int,
    val stock: Int,
    var isActive: Boolean,
    val menuOptions: List<String>
)
