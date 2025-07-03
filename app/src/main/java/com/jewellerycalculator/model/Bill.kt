package com.jewellerycalculator.model

data class Bill(
    val items: List<JewelleryItem> = emptyList(),
    val goldRate: Double = 0.0,
    val silverRate: Double = 0.0
) {
    fun getSilverItems(): List<JewelleryItem> = items.filter { it.metalType == MetalType.SILVER }
    
    fun getGoldItems(): List<JewelleryItem> = items.filter { it.metalType == MetalType.GOLD }
    
    fun calculateSilverTotal(): Double = getSilverItems().sumOf { it.calculateFinalPrice(silverRate) }
    
    fun calculateGoldTotal(): Double = getGoldItems().sumOf { it.calculateFinalPrice(goldRate) }
    
    fun calculateGrandTotal(): Double = calculateSilverTotal() + calculateGoldTotal()
}