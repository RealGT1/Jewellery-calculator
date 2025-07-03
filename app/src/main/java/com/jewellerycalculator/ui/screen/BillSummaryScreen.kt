package com.jewellerycalculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jewellerycalculator.model.Bill
import com.jewellerycalculator.model.JewelleryItem
import com.jewellerycalculator.model.MetalType
import java.text.DecimalFormat

@Composable
fun BillSummaryScreen(
    bill: Bill,
    onBackToBilling: () -> Unit,
    onNewBill: () -> Unit
) {
    val decimalFormat = DecimalFormat("#,##0.00")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Bill Summary",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        
        // Rates Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Gold Rate: ₹${decimalFormat.format(bill.goldRate)}/g",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Silver Rate: ₹${decimalFormat.format(bill.silverRate)}/g",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Items List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Silver Items
            val silverItems = bill.getSilverItems()
            if (silverItems.isNotEmpty()) {
                item {
                    SectionHeader("Silver Items")
                }
                items(silverItems) { item ->
                    SummaryItemCard(item = item, metalRate = bill.silverRate)
                }
                item {
                    SectionTotal("Silver Total", bill.calculateSilverTotal())
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // Gold Items
            val goldItems = bill.getGoldItems()
            if (goldItems.isNotEmpty()) {
                item {
                    SectionHeader("Gold Items")
                }
                items(goldItems) { item ->
                    SummaryItemCard(item = item, metalRate = bill.goldRate)
                }
                item {
                    SectionTotal("Gold Total", bill.calculateGoldTotal())
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        
        // Grand Total
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Grand Total:",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₹${decimalFormat.format(bill.calculateGrandTotal())}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onBackToBilling,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back to Billing")
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Button(
                onClick = onNewBill,
                modifier = Modifier.weight(1f)
            ) {
                Text("New Bill")
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SummaryItemCard(
    item: JewelleryItem,
    metalRate: Double
) {
    val decimalFormat = DecimalFormat("#,##0.00")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "₹${decimalFormat.format(item.calculateFinalPrice(metalRate))}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Column {
                Text(
                    text = "Weight: ${item.weight}g × ${item.quantity} = ${decimalFormat.format(item.calculateTotalWeight())}g",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Wastage (10%): ${decimalFormat.format(item.calculateWastage())}g",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Metal Cost: ${decimalFormat.format(item.calculateTotalWeight() + item.calculateWastage())}g × ₹${decimalFormat.format(metalRate)} = ₹${decimalFormat.format((item.calculateTotalWeight() + item.calculateWastage()) * metalRate)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Making Charge: ₹${decimalFormat.format(item.makingCharge)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SectionTotal(title: String, amount: Double) {
    val decimalFormat = DecimalFormat("#,##0.00")
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₹${decimalFormat.format(amount)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}