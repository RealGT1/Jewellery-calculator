package com.jewellerycalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jewellerycalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateInputScreen(
    onRatesSubmitted: (goldRate: Double, silverRate: Double) -> Unit
) {
    var goldRate by remember { mutableStateOf("") }
    var silverRate by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.rate_input_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        OutlinedTextField(
            value = goldRate,
            onValueChange = { goldRate = it },
            label = { Text(stringResource(R.string.gold_rate_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = silverRate,
            onValueChange = { silverRate = it },
            label = { Text(stringResource(R.string.silver_rate_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )
        
        if (showError) {
            Text(
                text = stringResource(R.string.invalid_rate),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        Button(
            onClick = {
                val gold = goldRate.toDoubleOrNull()
                val silver = silverRate.toDoubleOrNull()
                
                if (gold != null && silver != null && gold > 0 && silver > 0) {
                    showError = false
                    onRatesSubmitted(gold, silver)
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.proceed_button))
        }
    }
}