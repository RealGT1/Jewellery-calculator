# Jewellery Calculator Android App

A local, offline Android application for a jewellery shop to create detailed bills for customers, functioning like a shopping cart system.

## Features

### 1. Rate Input Screen
- First screen that prompts users to enter current day's rates for Gold and Silver
- Validates input to ensure rates are positive numbers
- Stores rates for the entire session

### 2. Main Billing Screen
- Primary screen for creating customer bills
- Add multiple items for both Gold and Silver
- Live totals display showing:
  - Running sub-total for Silver items
  - Running sub-total for Gold items
  - Grand Total for entire bill
- Item management (add/delete items)

### 3. Item Entry Process
For each item, users input:
- Item Name/ID (e.g., "Ring", "s1")
- Weight (in grams)
- Quantity
- Making Charge for that specific line item

### 4. Automatic Price Calculation
For each line item:
1. **Total Weight**: `Weight × Quantity`
2. **Wastage**: `Total Weight × 10%`
3. **Final Price**: `(Total Weight + Wastage) × Metal_Rate + Making_Charge`

### 5. Bill Summary Screen
- Detailed itemized bill with clear breakdown
- Lists all Silver items with individual calculations
- Lists all Gold items with individual calculations
- Shows totals for Silver, Gold, and Grand Total
- Option to start a new bill

## Technical Implementation

### Architecture
- **MVVM (Model-View-ViewModel)** pattern
- **Room Database** for local data storage
- **Jetpack Compose** for modern UI
- **Kotlin** programming language

### Key Components

#### Data Models
```kotlin
// Bill item entity
@Entity(tableName = "bill_items")
data class BillItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemName: String,
    val weight: Double,
    val quantity: Int,
    val makingCharge: Double,
    val metalType: MetalType,
    val billId: Long = 0
)

// Calculation result
data class ItemCalculation(
    val item: BillItem,
    val totalWeight: Double,
    val wastage: Double,
    val finalWeight: Double,
    val metalCost: Double,
    val totalCost: Double
)
```

#### Database Layer
- **Room Database** with TypeConverters for enum handling
- **DAO (Data Access Object)** for database operations
- **Flow-based** reactive queries for real-time updates

#### ViewModel
- **BillingViewModel** manages application state
- Handles rate storage and item calculations
- Provides reactive data streams to UI
- Manages item CRUD operations

#### UI Screens
1. **RateInputScreen** - Initial rate entry with validation
2. **BillingScreen** - Main billing interface with item management
3. **BillSummaryScreen** - Final bill display with detailed breakdown
4. **AddItemDialog** - Modal for adding new items

### Navigation
- **Jetpack Navigation Compose** for screen transitions
- Clear navigation flow: Rate Input → Billing → Bill Summary
- "New Bill" functionality resets state and returns to rate input

## Usage Example

1. **Open App** → Enter rates (Silver: 108, Gold: 9100)
2. **Add Items**:
   - Silver Item 1: s1, 10g, qty 4, making ₹250
   - Silver Item 2: s2, 25g, qty 3, making ₹350
   - Gold Item 1: g1, 15g, qty 1, making ₹1500
3. **View Live Totals** showing calculations in real-time
4. **Generate Bill** → See detailed breakdown with all calculations

## Calculation Logic

### Example Calculation
For a Silver item: 10g × 4 qty, making charge ₹250, Silver rate ₹108/g

1. **Total Weight**: 10g × 4 = 40g
2. **Wastage (10%)**: 40g × 0.1 = 4g
3. **Final Weight**: 40g + 4g = 44g
4. **Metal Cost**: 44g × ₹108 = ₹4,752
5. **Total Cost**: ₹4,752 + ₹250 = ₹5,002

## Project Structure

```
app/src/main/java/com/jewellerycalculator/
├── data/
│   └── Models.kt                 # Data models and enums
├── database/
│   ├── BillDatabase.kt          # Room database setup
│   └── BillItemDao.kt           # Database access object
├── ui/
│   ├── RateInputScreen.kt       # Initial rate entry screen
│   ├── BillingScreen.kt         # Main billing interface
│   ├── BillSummaryScreen.kt     # Final bill display
│   ├── AddItemDialog.kt         # Item addition dialog
│   └── theme/                   # UI theme and styling
├── viewmodel/
│   ├── BillingViewModel.kt      # Main view model
│   └── BillingViewModelFactory.kt # ViewModel factory
└── MainActivity.kt              # Entry point and navigation
```

## Key Features Implemented

- ✅ **Rate Input Validation**: Ensures positive numeric values
- ✅ **Real-time Calculations**: Live updates as items are added
- ✅ **Data Persistence**: Room database stores items locally
- ✅ **Reactive UI**: Jetpack Compose with State management
- ✅ **Professional UI**: Material Design 3 components
- ✅ **Type Safety**: Kotlin with strong typing
- ✅ **Error Handling**: Input validation and error messages
- ✅ **Offline Operation**: No internet connection required

## Future Enhancements

- Print functionality for physical bills
- Customer information storage
- Historical bill records
- Export to PDF/Excel
- Backup/restore functionality
- Multi-language support

## Building and Running

1. Open project in Android Studio
2. Sync Gradle files
3. Run on Android device or emulator (API 24+)

The application is designed to work completely offline and provides a robust solution for jewellery shop billing needs.
