package com.jewellerycalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_items")
data class BillItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemName: String,
    val weight: Double,
    val quantity: Int,
    val makingCharge: Double,
    val metalType: MetalType, // GOLD or SILVER
    val billId: Long = 0 // For future use if storing multiple bills
)

enum class MetalType {
    GOLD, SILVER
}

data class MetalRates(
    val goldRate: Double,
    val silverRate: Double
)

data class ItemCalculation(
    val item: BillItem,
    val totalWeight: Double,
    val wastage: Double,
    val finalWeight: Double,
    val metalCost: Double,
    val totalCost: Double
)

data class BillSummary(
    val silverItems: List<ItemCalculation>,
    val goldItems: List<ItemCalculation>,
    val silverTotal: Double,
    val goldTotal: Double,
    val grandTotal: Double
)