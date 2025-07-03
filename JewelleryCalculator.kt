#!/usr/bin/env kotlin

import java.text.DecimalFormat
import java.util.Scanner

// Data classes for the jewelry calculator
data class JewelleryItem(
    val id: String = "",
    val name: String = "",
    val weight: Double = 0.0,
    val quantity: Int = 1,
    val makingCharge: Double = 0.0,
    val metalType: MetalType = MetalType.SILVER
) {
    fun calculateTotalWeight(): Double = weight * quantity
    
    fun calculateWastage(): Double = calculateTotalWeight() * 0.1
    
    fun calculateFinalPrice(metalRate: Double): Double {
        return (calculateTotalWeight() + calculateWastage()) * metalRate + makingCharge
    }
}

enum class MetalType {
    GOLD, SILVER
}

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

// Main calculator class
class JewelleryCalculator {
    private var bill = Bill()
    private val scanner = Scanner(System.`in`)
    private val decimalFormat = DecimalFormat("#,##0.00")
    
    fun start() {
        println("=================================")
        println("   JEWELLERY CALCULATOR")
        println("=================================")
        println()
        
        // Step 1: Get rates
        inputRates()
        
        // Step 2: Main billing loop
        mainBillingLoop()
        
        // Step 3: Generate final bill
        generateFinalBill()
    }
    
    private fun inputRates() {
        println("Enter Today's Metal Rates:")
        println("-------------------------")
        
        print("Gold Rate per gram: ₹")
        val goldRate = readDouble()
        
        print("Silver Rate per gram: ₹")
        val silverRate = readDouble()
        
        bill = bill.copy(goldRate = goldRate, silverRate = silverRate)
        
        println()
        println("Rates set successfully!")
        println("Gold: ₹${decimalFormat.format(goldRate)}/g")
        println("Silver: ₹${decimalFormat.format(silverRate)}/g")
        println()
    }
    
    private fun mainBillingLoop() {
        while (true) {
            println("=================================")
            println("         BILLING SCREEN")
            println("=================================")
            println()
            
            // Show current totals
            displayCurrentTotals()
            
            println("Options:")
            println("1. Add Gold Item")
            println("2. Add Silver Item")
            println("3. View All Items")
            println("4. Remove Item")
            println("5. Generate Bill")
            println("6. Exit")
            println()
            
            print("Choose an option (1-6): ")
            val choice = readInt()
            
            when (choice) {
                1 -> addItem(MetalType.GOLD)
                2 -> addItem(MetalType.SILVER)
                3 -> viewAllItems()
                4 -> removeItem()
                5 -> return
                6 -> {
                    println("Thank you for using Jewellery Calculator!")
                    return
                }
                else -> println("Invalid option. Please try again.")
            }
            
            println()
        }
    }
    
    private fun addItem(metalType: MetalType) {
        println()
        println("Adding ${metalType.name} Item:")
        println("-------------------------")
        
        print("Item Name/ID: ")
        val name = scanner.nextLine()
        
        print("Weight (grams): ")
        val weight = readDouble()
        
        print("Quantity: ")
        val quantity = readInt()
        
        print("Making Charge: ₹")
        val makingCharge = readDouble()
        
        val item = JewelleryItem(
            name = name,
            weight = weight,
            quantity = quantity,
            makingCharge = makingCharge,
            metalType = metalType
        )
        
        val currentItems = bill.items.toMutableList()
        currentItems.add(item)
        bill = bill.copy(items = currentItems)
        
        println()
        println("Item added successfully!")
        println("Item: $name")
        println("Total Weight: ${decimalFormat.format(item.calculateTotalWeight())}g")
        println("Wastage (10%): ${decimalFormat.format(item.calculateWastage())}g")
        println("Final Price: ₹${decimalFormat.format(item.calculateFinalPrice(if (metalType == MetalType.GOLD) bill.goldRate else bill.silverRate))}")
    }
    
    private fun viewAllItems() {
        println()
        println("All Items:")
        println("----------")
        
        if (bill.items.isEmpty()) {
            println("No items added yet.")
            return
        }
        
        bill.items.forEachIndexed { index, item ->
            val rate = if (item.metalType == MetalType.GOLD) bill.goldRate else bill.silverRate
            println("${index + 1}. ${item.name} (${item.metalType.name})")
            println("   Weight: ${item.weight}g × ${item.quantity} = ${decimalFormat.format(item.calculateTotalWeight())}g")
            println("   Wastage: ${decimalFormat.format(item.calculateWastage())}g")
            println("   Making Charge: ₹${decimalFormat.format(item.makingCharge)}")
            println("   Final Price: ₹${decimalFormat.format(item.calculateFinalPrice(rate))}")
            println()
        }
    }
    
    private fun removeItem() {
        if (bill.items.isEmpty()) {
            println("No items to remove.")
            return
        }
        
        println()
        println("Remove Item:")
        println("------------")
        
        bill.items.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name} (${item.metalType.name})")
        }
        
        print("Enter item number to remove: ")
        val itemIndex = readInt() - 1
        
        if (itemIndex >= 0 && itemIndex < bill.items.size) {
            val currentItems = bill.items.toMutableList()
            val removedItem = currentItems.removeAt(itemIndex)
            bill = bill.copy(items = currentItems)
            println("Removed: ${removedItem.name}")
        } else {
            println("Invalid item number.")
        }
    }
    
    private fun displayCurrentTotals() {
        println("Current Totals:")
        println("---------------")
        println("Silver Total: ₹${decimalFormat.format(bill.calculateSilverTotal())}")
        println("Gold Total: ₹${decimalFormat.format(bill.calculateGoldTotal())}")
        println("Grand Total: ₹${decimalFormat.format(bill.calculateGrandTotal())}")
        println()
    }
    
    private fun generateFinalBill() {
        println()
        println("=================================")
        println("        FINAL BILL SUMMARY")
        println("=================================")
        println()
        
        println("Metal Rates:")
        println("Gold: ₹${decimalFormat.format(bill.goldRate)}/g")
        println("Silver: ₹${decimalFormat.format(bill.silverRate)}/g")
        println()
        
        // Silver items
        val silverItems = bill.getSilverItems()
        if (silverItems.isNotEmpty()) {
            println("SILVER ITEMS:")
            println("=============")
            silverItems.forEach { item ->
                printItemDetails(item, bill.silverRate)
            }
            println("Silver Total: ₹${decimalFormat.format(bill.calculateSilverTotal())}")
            println()
        }
        
        // Gold items
        val goldItems = bill.getGoldItems()
        if (goldItems.isNotEmpty()) {
            println("GOLD ITEMS:")
            println("===========")
            goldItems.forEach { item ->
                printItemDetails(item, bill.goldRate)
            }
            println("Gold Total: ₹${decimalFormat.format(bill.calculateGoldTotal())}")
            println()
        }
        
        // Grand total
        println("=================================")
        println("GRAND TOTAL: ₹${decimalFormat.format(bill.calculateGrandTotal())}")
        println("=================================")
    }
    
    private fun printItemDetails(item: JewelleryItem, rate: Double) {
        println("${item.name}:")
        println("  Weight: ${item.weight}g × ${item.quantity} = ${decimalFormat.format(item.calculateTotalWeight())}g")
        println("  Wastage (10%): ${decimalFormat.format(item.calculateWastage())}g")
        println("  Total Weight: ${decimalFormat.format(item.calculateTotalWeight() + item.calculateWastage())}g")
        println("  Metal Cost: ${decimalFormat.format(item.calculateTotalWeight() + item.calculateWastage())}g × ₹${decimalFormat.format(rate)} = ₹${decimalFormat.format((item.calculateTotalWeight() + item.calculateWastage()) * rate)}")
        println("  Making Charge: ₹${decimalFormat.format(item.makingCharge)}")
        println("  Final Price: ₹${decimalFormat.format(item.calculateFinalPrice(rate))}")
        println()
    }
    
    private fun readDouble(): Double {
        while (true) {
            try {
                return scanner.nextLine().toDouble()
            } catch (e: NumberFormatException) {
                print("Invalid number. Please try again: ")
            }
        }
    }
    
    private fun readInt(): Int {
        while (true) {
            try {
                return scanner.nextLine().toInt()
            } catch (e: NumberFormatException) {
                print("Invalid number. Please try again: ")
            }
        }
    }
}

// Example usage with test data
fun main() {
    val calculator = JewelleryCalculator()
    
    // Ask user if they want to run demo or interactive mode
    println("=================================")
    println("   JEWELLERY CALCULATOR")
    println("=================================")
    println()
    println("Choose mode:")
    println("1. Interactive Mode")
    println("2. Demo with Sample Data")
    println()
    
    val scanner = Scanner(System.`in`)
    print("Enter choice (1 or 2): ")
    val choice = scanner.nextLine()
    
    if (choice == "2") {
        // Demo mode with sample data
        runDemo()
    } else {
        // Interactive mode
        calculator.start()
    }
}

fun runDemo() {
    println()
    println("=================================")
    println("         DEMO MODE")
    println("=================================")
    println()
    
    // Sample data from the problem statement
    val goldRate = 9100.0
    val silverRate = 108.0
    
    println("Sample rates:")
    println("Gold Rate: ₹${goldRate}/g")
    println("Silver Rate: ₹${silverRate}/g")
    println()
    
    // Create sample items
    val items = listOf(
        JewelleryItem("s1", "Silver Item 1", 10.0, 4, 250.0, MetalType.SILVER),
        JewelleryItem("s2", "Silver Item 2", 25.0, 3, 350.0, MetalType.SILVER),
        JewelleryItem("g1", "Gold Item 1", 15.0, 1, 1500.0, MetalType.GOLD)
    )
    
    val bill = Bill(items, goldRate, silverRate)
    
    println("Sample Items Added:")
    println("-------------------")
    items.forEach { item ->
        val rate = if (item.metalType == MetalType.GOLD) goldRate else silverRate
        println("${item.name}: ${item.weight}g × ${item.quantity}, Making: ₹${DecimalFormat("#,##0.00").format(item.makingCharge)}")
        println("  Final Price: ₹${DecimalFormat("#,##0.00").format(item.calculateFinalPrice(rate))}")
    }
    
    println()
    println("Live Totals:")
    println("------------")
    println("Silver Total: ₹${DecimalFormat("#,##0.00").format(bill.calculateSilverTotal())}")
    println("Gold Total: ₹${DecimalFormat("#,##0.00").format(bill.calculateGoldTotal())}")
    println("Grand Total: ₹${DecimalFormat("#,##0.00").format(bill.calculateGrandTotal())}")
    
    println()
    println("=================================")
    println("        DETAILED BILL")
    println("=================================")
    println()
    
    // Silver items detail
    val silverItems = bill.getSilverItems()
    if (silverItems.isNotEmpty()) {
        println("SILVER ITEMS:")
        println("=============")
        silverItems.forEach { item ->
            printItemDetailsDemo(item, silverRate)
        }
        println("Silver Total: ₹${DecimalFormat("#,##0.00").format(bill.calculateSilverTotal())}")
        println()
    }
    
    // Gold items detail
    val goldItems = bill.getGoldItems()
    if (goldItems.isNotEmpty()) {
        println("GOLD ITEMS:")
        println("===========")
        goldItems.forEach { item ->
            printItemDetailsDemo(item, goldRate)
        }
        println("Gold Total: ₹${DecimalFormat("#,##0.00").format(bill.calculateGoldTotal())}")
        println()
    }
    
    // Grand total
    println("=================================")
    println("GRAND TOTAL: ₹${DecimalFormat("#,##0.00").format(bill.calculateGrandTotal())}")
    println("=================================")
}

fun printItemDetailsDemo(item: JewelleryItem, rate: Double) {
    val decimalFormat = DecimalFormat("#,##0.00")
    println("${item.name}:")
    println("  Weight: ${item.weight}g × ${item.quantity} = ${decimalFormat.format(item.calculateTotalWeight())}g")
    println("  Wastage (10%): ${decimalFormat.format(item.calculateWastage())}g")
    println("  Total Weight: ${decimalFormat.format(item.calculateTotalWeight() + item.calculateWastage())}g")
    println("  Metal Cost: ${decimalFormat.format(item.calculateTotalWeight() + item.calculateWastage())}g × ₹${decimalFormat.format(rate)} = ₹${decimalFormat.format((item.calculateTotalWeight() + item.calculateWastage()) * rate)}")
    println("  Making Charge: ₹${decimalFormat.format(item.makingCharge)}")
    println("  Final Price: ₹${decimalFormat.format(item.calculateFinalPrice(rate))}")
    println()
}