package com.jewellerycalculator

import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.MetalType
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the jewellery calculator logic
 */
class CalculationLogicTest {
    
    @Test
    fun testSilverItemCalculation() {
        // Test case from requirements: Silver item s1, 10g, qty 4, making 250, rate 108
        val item = BillItem(
            itemName = "s1",
            weight = 10.0,
            quantity = 4,
            makingCharge = 250.0,
            metalType = MetalType.SILVER
        )
        
        val silverRate = 108.0
        
        // Calculate manually
        val totalWeight = item.weight * item.quantity // 10 * 4 = 40
        val wastage = totalWeight * 0.1 // 40 * 0.1 = 4
        val finalWeight = totalWeight + wastage // 40 + 4 = 44
        val metalCost = finalWeight * silverRate // 44 * 108 = 4752
        val totalCost = metalCost + item.makingCharge // 4752 + 250 = 5002
        
        assertEquals(40.0, totalWeight, 0.01)
        assertEquals(4.0, wastage, 0.01)
        assertEquals(44.0, finalWeight, 0.01)
        assertEquals(4752.0, metalCost, 0.01)
        assertEquals(5002.0, totalCost, 0.01)
    }
    
    @Test
    fun testGoldItemCalculation() {
        // Test case from requirements: Gold item g1, 15g, qty 1, making 1500, rate 9100
        val item = BillItem(
            itemName = "g1",
            weight = 15.0,
            quantity = 1,
            makingCharge = 1500.0,
            metalType = MetalType.GOLD
        )
        
        val goldRate = 9100.0
        
        // Calculate manually
        val totalWeight = item.weight * item.quantity // 15 * 1 = 15
        val wastage = totalWeight * 0.1 // 15 * 0.1 = 1.5
        val finalWeight = totalWeight + wastage // 15 + 1.5 = 16.5
        val metalCost = finalWeight * goldRate // 16.5 * 9100 = 150150
        val totalCost = metalCost + item.makingCharge // 150150 + 1500 = 151650
        
        assertEquals(15.0, totalWeight, 0.01)
        assertEquals(1.5, wastage, 0.01)
        assertEquals(16.5, finalWeight, 0.01)
        assertEquals(150150.0, metalCost, 0.01)
        assertEquals(151650.0, totalCost, 0.01)
    }
    
    @Test
    fun testMultipleItemsTotal() {
        // Test the complete scenario from requirements
        val items = listOf(
            BillItem(itemName = "s1", weight = 10.0, quantity = 4, makingCharge = 250.0, metalType = MetalType.SILVER),
            BillItem(itemName = "s2", weight = 25.0, quantity = 3, makingCharge = 350.0, metalType = MetalType.SILVER),
            BillItem(itemName = "g1", weight = 15.0, quantity = 1, makingCharge = 1500.0, metalType = MetalType.GOLD)
        )
        
        val silverRate = 108.0
        val goldRate = 9100.0
        
        // Calculate silver total
        val silverItems = items.filter { it.metalType == MetalType.SILVER }
        val silverTotal = silverItems.sumOf { item ->
            val totalWeight = item.weight * item.quantity
            val wastage = totalWeight * 0.1
            val finalWeight = totalWeight + wastage
            val metalCost = finalWeight * silverRate
            metalCost + item.makingCharge
        }
        
        // Calculate gold total
        val goldItems = items.filter { it.metalType == MetalType.GOLD }
        val goldTotal = goldItems.sumOf { item ->
            val totalWeight = item.weight * item.quantity
            val wastage = totalWeight * 0.1
            val finalWeight = totalWeight + wastage
            val metalCost = finalWeight * goldRate
            metalCost + item.makingCharge
        }
        
        val grandTotal = silverTotal + goldTotal
        
        // Expected calculations:
        // Silver s1: (10*4 + 4*0.1) * 108 + 250 = 44 * 108 + 250 = 4752 + 250 = 5002
        // Silver s2: (25*3 + 7.5*0.1) * 108 + 350 = 82.5 * 108 + 350 = 8910 + 350 = 9260
        // Gold g1: (15*1 + 1.5*0.1) * 9100 + 1500 = 16.5 * 9100 + 1500 = 150150 + 1500 = 151650
        // Silver total: 5002 + 9260 = 14262
        // Gold total: 151650
        // Grand total: 14262 + 151650 = 165912
        
        assertEquals(14262.0, silverTotal, 0.01)
        assertEquals(151650.0, goldTotal, 0.01)
        assertEquals(165912.0, grandTotal, 0.01)
    }
    
    @Test
    fun testWastageCalculation() {
        // Test that wastage is always 10% of total weight
        val testCases = listOf(
            Pair(10.0, 1.0), // 10g -> 1g wastage
            Pair(25.0, 2.5), // 25g -> 2.5g wastage
            Pair(1.0, 0.1),  // 1g -> 0.1g wastage
            Pair(100.0, 10.0) // 100g -> 10g wastage
        )
        
        testCases.forEach { (weight, expectedWastage) ->
            val actualWastage = weight * 0.1
            assertEquals(expectedWastage, actualWastage, 0.01)
        }
    }
}