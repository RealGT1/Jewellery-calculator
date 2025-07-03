package com.jewellerycalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jewellerycalculator.R
import com.jewellerycalculator.data.BillItem
import com.jewellerycalculator.data.BillSummary
import com.jewellerycalculator.data.MetalType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingScreen(
    billSummary: BillSummary,
    showItemDialog: Boolean,
    selectedMetalType: MetalType?,
    onAddGoldItem: () -> Unit,
    onAddSilverItem: () -> Unit,
    onDeleteItem: (BillItem) -> Unit,
    onItemDialogDismiss: () -> Unit,
    onItemAdd: (String, Double, Int, Double, MetalType) -> Unit,
    onGenerateBill: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.billing_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onAddGoldItem,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_gold_item))
            }
            
            Button(
                onClick = onAddSilverItem,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_silver_item))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Items List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (billSummary.silverItems.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.silver_items),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(billSummary.silverItems) { itemCalc ->
                    ItemCard(
                        item = itemCalc.item,
                        totalCost = itemCalc.totalCost,
                        onDelete = { onDeleteItem(itemCalc.item) }
                    )
                }
            }
            
            if (billSummary.goldItems.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.gold_items),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(billSummary.goldItems) { itemCalc ->
                    ItemCard(
                        item = itemCalc.item,
                        totalCost = itemCalc.totalCost,
                        onDelete = { onDeleteItem(itemCalc.item) }
                    )
                }
            }
            
            if (billSummary.silverItems.isEmpty() && billSummary.goldItems.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_items_added),
                        modifier = Modifier.padding(32.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
        
        // Totals Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (billSummary.silverTotal > 0) {
                    Text(
                        text = stringResource(R.string.silver_total, billSummary.silverTotal),
                        fontSize = 16.sp
                    )
                }
                
                if (billSummary.goldTotal > 0) {
                    Text(
                        text = stringResource(R.string.gold_total, billSummary.goldTotal),
                        fontSize = 16.sp
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Text(
                    text = stringResource(R.string.grand_total, billSummary.grandTotal),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Button(
            onClick = onGenerateBill,
            modifier = Modifier.fillMaxWidth(),
            enabled = billSummary.grandTotal > 0
        ) {
            Text(stringResource(R.string.generate_bill))
        }
    }
    
    // Item Add Dialog
    if (showItemDialog && selectedMetalType != null) {
        AddItemDialog(
            metalType = selectedMetalType,
            onDismiss = onItemDialogDismiss,
            onAdd = onItemAdd
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item: BillItem,
    totalCost: Double,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.itemName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${item.weight}g × ${item.quantity} + Making: ₹${item.makingCharge}",
                    fontSize = 14.sp
                )
                Text(
                    text = "Total: ₹${"%.2f".format(totalCost)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}