package com.jewellerycalculator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.MetalType
import kotlinx.coroutines.flow.Flow

@Dao
interface BillItemDao {
    @Query("SELECT * FROM bill_items WHERE billId = :billId")
    fun getBillItems(billId: Long = 0): Flow<List<BillItem>>
    
    @Query("SELECT * FROM bill_items WHERE billId = :billId AND metalType = :metalType")
    fun getBillItemsByType(billId: Long = 0, metalType: MetalType): Flow<List<BillItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: BillItem)
    
    @Update
    suspend fun updateItem(item: BillItem)
    
    @Delete
    suspend fun deleteItem(item: BillItem)
    
    @Query("DELETE FROM bill_items WHERE billId = :billId")
    suspend fun clearBill(billId: Long = 0)
}