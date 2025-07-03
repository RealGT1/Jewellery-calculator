package com.jewellerycalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jewellerycalculator.R
import com.jewellerycalculator.data.BillSummary
import com.jewellerycalculator.data.ItemCalculation

@Composable
fun BillSummaryScreen(
    billSummary: BillSummary,
    onNewBill: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.bill_summary_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
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
                    ItemDetailCard(itemCalc)
                }
                
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.silver_total, billSummary.silverTotal),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
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
                    ItemDetailCard(itemCalc)
                }
                
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.gold_total, billSummary.goldTotal),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(R.string.grand_total, billSummary.grandTotal),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        
        Button(
            onClick = onNewBill,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.new_bill))
        }
    }
}

@Composable
fun ItemDetailCard(itemCalc: ItemCalculation) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = itemCalc.item.itemName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Weight: ${itemCalc.item.weight}g × ${itemCalc.item.quantity} = ${itemCalc.totalWeight}g",
                fontSize = 14.sp
            )
            
            Text(
                text = "Wastage (10%): ${String.format("%.1f", itemCalc.wastage)}g",
                fontSize = 14.sp
            )
            
            Text(
                text = "Final Weight: ${String.format("%.1f", itemCalc.finalWeight)}g",
                fontSize = 14.sp
            )
            
            Text(
                text = "Metal Cost: ₹${String.format("%.2f", itemCalc.metalCost)}",
                fontSize = 14.sp
            )
            
            Text(
                text = "Making Charge: ₹${itemCalc.item.makingCharge}",
                fontSize = 14.sp
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Text(
                text = "Total: ₹${String.format("%.2f", itemCalc.totalCost)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}