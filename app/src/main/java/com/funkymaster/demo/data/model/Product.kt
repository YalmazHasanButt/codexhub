package com.funkymaster.demo.data.model


data class Product(
    val name_en: String,
    val barcode: String,
    val sku: String,
    val category: Int,
    val cost_price: Double,
    val selling_price: Double,
    val quantity: Int,
    val alert_quantity: Int,
    val expiry_date: String
)