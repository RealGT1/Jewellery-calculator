package com.jewellerycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jewellerycalculator.data.MetalType
import com.jewellerycalculator.database.BillDatabase
import com.jewellerycalculator.ui.BillSummaryScreen
import com.jewellerycalculator.ui.BillingScreen
import com.jewellerycalculator.ui.RateInputScreen
import com.jewellerycalculator.ui.theme.JewelleryCalculatorTheme
import com.jewellerycalculator.viewmodel.BillingViewModel
import com.jewellerycalculator.viewmodel.BillingViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JewelleryCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JewelleryCalculatorApp()
                }
            }
        }
    }
}

@Composable
fun JewelleryCalculatorApp() {
    val navController = rememberNavController()
    val database = BillDatabase.getDatabase(androidx.compose.ui.platform.LocalContext.current)
    val billingViewModel: BillingViewModel = viewModel(
        factory = BillingViewModelFactory(database.billItemDao())
    )
    
    val metalRates by billingViewModel.metalRates.collectAsState()
    val billSummary by billingViewModel.billSummary.collectAsState()
    val showItemDialog by billingViewModel.showItemDialog.collectAsState()
    val selectedMetalType by billingViewModel.selectedMetalType.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = "rate_input"
    ) {
        composable("rate_input") {
            RateInputScreen(
                onRatesSubmitted = { goldRate, silverRate ->
                    billingViewModel.setMetalRates(goldRate, silverRate)
                    navController.navigate("billing") {
                        popUpTo("rate_input") { inclusive = true }
                    }
                }
            )
        }
        
        composable("billing") {
            BillingScreen(
                billSummary = billSummary,
                showItemDialog = showItemDialog,
                selectedMetalType = selectedMetalType,
                onAddGoldItem = { billingViewModel.showAddItemDialog(MetalType.GOLD) },
                onAddSilverItem = { billingViewModel.showAddItemDialog(MetalType.SILVER) },
                onDeleteItem = { billingViewModel.deleteItem(it) },
                onItemDialogDismiss = { billingViewModel.hideAddItemDialog() },
                onItemAdd = { name, weight, quantity, making, metal ->
                    billingViewModel.addItem(name, weight, quantity, making, metal)
                },
                onGenerateBill = {
                    navController.navigate("bill_summary")
                }
            )
        }
        
        composable("bill_summary") {
            BillSummaryScreen(
                billSummary = billSummary,
                onNewBill = {
                    billingViewModel.clearBill()
                    navController.navigate("rate_input") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}