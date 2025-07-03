package com.jewellerycalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jewellerycalculator.database.BillItemDao

class BillingViewModelFactory(
    private val billItemDao: BillItemDao
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillingViewModel::class.java)) {
            return BillingViewModel(billItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}