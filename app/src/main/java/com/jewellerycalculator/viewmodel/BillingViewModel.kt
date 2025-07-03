package com.jewellerycalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.BillSummary
import com.jewellerycalculator.data.ItemCalculation
import com.jewellerycalculator.data.MetalRates
import com.jewellerycalculator.data.MetalType
import com.jewellerycalculator.database.BillItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class BillingViewModel(private val billItemDao: BillItemDao) : ViewModel() {
    
    private val _metalRates = MutableStateFlow(MetalRates(0.0, 0.0))
    val metalRates: StateFlow<MetalRates> = _metalRates.asStateFlow()
    
    private val _billSummary = MutableStateFlow(BillSummary(
        silverItems = emptyList(),
        goldItems = emptyList(),
        silverTotal = 0.0,
        goldTotal = 0.0,
        grandTotal = 0.0
    ))
    val billSummary: StateFlow<BillSummary> = _billSummary.asStateFlow()
    
    private val _showItemDialog = MutableStateFlow(false)
    val showItemDialog: StateFlow<Boolean> = _showItemDialog.asStateFlow()
    
    private val _selectedMetalType = MutableStateFlow<MetalType?>(null)
    val selectedMetalType: StateFlow<MetalType?> = _selectedMetalType.asStateFlow()
    
    init {
        // Observe bill items and calculate totals
        viewModelScope.launch {
            billItemDao.getBillItems().combine(metalRates) { items, rates ->
                calculateBillSummary(items, rates)
            }.collect { summary ->
                _billSummary.value = summary
            }
        }
    }
    
    fun setMetalRates(goldRate: Double, silverRate: Double) {
        _metalRates.value = MetalRates(goldRate, silverRate)
    }
    
    fun showAddItemDialog(metalType: MetalType) {
        _selectedMetalType.value = metalType
        _showItemDialog.value = true
    }
    
    fun hideAddItemDialog() {
        _showItemDialog.value = false
        _selectedMetalType.value = null
    }
    
    fun addItem(itemName: String, weight: Double, quantity: Int, makingCharge: Double, metalType: MetalType) {
        viewModelScope.launch {
            val item = BillItem(
                itemName = itemName,
                weight = weight,
                quantity = quantity,
                makingCharge = makingCharge,
                metalType = metalType
            )
            billItemDao.insertItem(item)
        }
    }
    
    fun deleteItem(item: BillItem) {
        viewModelScope.launch {
            billItemDao.deleteItem(item)
        }
    }
    
    fun clearBill() {
        viewModelScope.launch {
            billItemDao.clearBill()
        }
    }
    
    private fun calculateBillSummary(items: List<BillItem>, rates: MetalRates): BillSummary {
        val silverItems = items.filter { it.metalType == MetalType.SILVER }
            .map { calculateItemCost(it, rates.silverRate) }
        
        val goldItems = items.filter { it.metalType == MetalType.GOLD }
            .map { calculateItemCost(it, rates.goldRate) }
        
        val silverTotal = silverItems.sumOf { it.totalCost }
        val goldTotal = goldItems.sumOf { it.totalCost }
        val grandTotal = silverTotal + goldTotal
        
        return BillSummary(
            silverItems = silverItems,
            goldItems = goldItems,
            silverTotal = silverTotal,
            goldTotal = goldTotal,
            grandTotal = grandTotal
        )
    }
    
    private fun calculateItemCost(item: BillItem, metalRate: Double): ItemCalculation {
        val totalWeight = item.weight * item.quantity
        val wastage = totalWeight * 0.1 // 10% wastage
        val finalWeight = totalWeight + wastage
        val metalCost = finalWeight * metalRate
        val totalCost = metalCost + item.makingCharge
        
        return ItemCalculation(
            item = item,
            totalWeight = totalWeight,
            wastage = wastage,
            finalWeight = finalWeight,
            metalCost = metalCost,
            totalCost = totalCost
        )
    }
}