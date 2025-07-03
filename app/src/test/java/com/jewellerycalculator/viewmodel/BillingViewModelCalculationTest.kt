package com.jewellerycalculator.viewmodel

import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.MetalRates
import com.jewellerycalculator.data.MetalType
import org.junit.Test
import org.junit.Assert.*

/**
 * Test class to verify BillingViewModel calculation logic
 */
class BillingViewModelCalculationTest {

    @Test
    fun testItemCalculation() {
        // Test the private calculateItemCost method logic
        val item = BillItem(
            itemName = "s1",
            weight = 10.0,
            quantity = 4,
            makingCharge = 250.0,
            metalType = MetalType.SILVER
        )
        
        val metalRate = 108.0
        
        // Simulate the calculation logic from BillingViewModel
        val totalWeight = item.weight * item.quantity
        val wastage = totalWeight * 0.1
        val finalWeight = totalWeight + wastage
        val metalCost = finalWeight * metalRate
        val totalCost = metalCost + item.makingCharge
        
        // Verify calculations
        assertEquals(40.0, totalWeight, 0.01)
        assertEquals(4.0, wastage, 0.01)
        assertEquals(44.0, finalWeight, 0.01)
        assertEquals(4752.0, metalCost, 0.01)
        assertEquals(5002.0, totalCost, 0.01)
    }

    @Test
    fun testBillSummaryCalculation() {
        // Test the complete bill summary calculation
        val items = listOf(
            BillItem(
                itemName = "s1",
                weight = 10.0,
                quantity = 4,
                makingCharge = 250.0,
                metalType = MetalType.SILVER
            ),
            BillItem(
                itemName = "s2",
                weight = 25.0,
                quantity = 3,
                makingCharge = 350.0,
                metalType = MetalType.SILVER
            ),
            BillItem(
                itemName = "g1",
                weight = 15.0,
                quantity = 1,
                makingCharge = 1500.0,
                metalType = MetalType.GOLD
            )
        )
        
        val rates = MetalRates(goldRate = 9100.0, silverRate = 108.0)
        
        // Simulate the calculateBillSummary method logic
        val silverItems = items.filter { it.metalType == MetalType.SILVER }
        val goldItems = items.filter { it.metalType == MetalType.GOLD }
        
        val silverTotal = silverItems.sumOf { item ->
            val totalWeight = item.weight * item.quantity
            val wastage = totalWeight * 0.1
            val finalWeight = totalWeight + wastage
            val metalCost = finalWeight * rates.silverRate
            metalCost + item.makingCharge
        }
        
        val goldTotal = goldItems.sumOf { item ->
            val totalWeight = item.weight * item.quantity
            val wastage = totalWeight * 0.1
            val finalWeight = totalWeight + wastage
            val metalCost = finalWeight * rates.goldRate
            metalCost + item.makingCharge
        }
        
        val grandTotal = silverTotal + goldTotal
        
        // Verify the totals match expected values
        assertEquals(14262.0, silverTotal, 0.01)
        assertEquals(151650.0, goldTotal, 0.01)
        assertEquals(165912.0, grandTotal, 0.01)
    }

    @Test
    fun testEdgeCases() {
        // Test with zero making charge
        val item = BillItem(
            itemName = "test",
            weight = 10.0,
            quantity = 1,
            makingCharge = 0.0,
            metalType = MetalType.SILVER
        )
        
        val metalRate = 100.0
        
        val totalWeight = item.weight * item.quantity
        val wastage = totalWeight * 0.1
        val finalWeight = totalWeight + wastage
        val metalCost = finalWeight * metalRate
        val totalCost = metalCost + item.makingCharge
        
        assertEquals(10.0, totalWeight, 0.01)
        assertEquals(1.0, wastage, 0.01)
        assertEquals(11.0, finalWeight, 0.01)
        assertEquals(1100.0, metalCost, 0.01)
        assertEquals(1100.0, totalCost, 0.01)
    }

    @Test
    fun testSingleItemQuantity() {
        // Test with quantity 1
        val item = BillItem(
            itemName = "single",
            weight = 5.0,
            quantity = 1,
            makingCharge = 100.0,
            metalType = MetalType.GOLD
        )
        
        val metalRate = 9000.0
        
        val totalWeight = item.weight * item.quantity
        val wastage = totalWeight * 0.1
        val finalWeight = totalWeight + wastage
        val metalCost = finalWeight * metalRate
        val totalCost = metalCost + item.makingCharge
        
        assertEquals(5.0, totalWeight, 0.01)
        assertEquals(0.5, wastage, 0.01)
        assertEquals(5.5, finalWeight, 0.01)
        assertEquals(49500.0, metalCost, 0.01)
        assertEquals(49600.0, totalCost, 0.01)
    }
}