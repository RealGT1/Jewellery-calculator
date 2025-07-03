# Jewellery Calculator

A comprehensive Android application for jewelry shops to create detailed bills for customers, functioning like a shopping cart system.

## Features

### Core Functionality
- **Rate Input**: Enter daily Gold and Silver rates at app launch
- **Item Management**: Add multiple Gold and Silver items to a single bill
- **Automatic Calculations**: Calculate total weight, wastage (10%), and final pricing
- **Live Totals**: Real-time display of Silver total, Gold total, and Grand total
- **Bill Generation**: Detailed itemized bill with complete breakdown

### Calculation Logic
For each item, the app performs:
1. **Total Weight**: Weight × Quantity
2. **Wastage**: Total Weight × 10%
3. **Final Price**: (Total Weight + Wastage) × Metal_Rate + Making_Charge

### User Workflow Example
1. User enters Silver Rate: ₹108/g, Gold Rate: ₹9100/g
2. Add Silver Item: "s1", 10g, qty 4, making charge ₹250
3. Add Silver Item: "s2", 25g, qty 3, making charge ₹350
4. Add Gold Item: "g1", 15g, qty 1, making charge ₹1500
5. View live totals updating automatically
6. Generate final bill with complete breakdown

## Project Structure

### Android Application
The project includes a complete Android application structure with:
- **Kotlin** programming language
- **Jetpack Compose** for modern UI
- **MVVM Architecture** with ViewModels
- **Room Database** ready for data persistence
- **Navigation Component** for screen transitions

### Core Components

#### Data Models
- `JewelleryItem`: Represents individual jewelry items with calculations
- `Bill`: Manages collections of items and totals
- `MetalType`: Enum for Gold/Silver classification

#### Screens
- `RateInputScreen`: Initial rate entry interface
- `BillingScreen`: Main item management interface
- `BillSummaryScreen`: Final bill generation and display
- `AddItemDialog`: Item entry dialog

#### Business Logic
- `BillViewModel`: Manages application state and business logic
- Automatic calculation methods
- Live total updates

## Running the Application

### Option 1: Standalone Demo
For a quick demonstration without Android Studio:

```bash
# Make the script executable
chmod +x JewelleryCalculator.kt

# Run the Kotlin script
kotlin JewelleryCalculator.kt
```

Choose between:
- **Interactive Mode**: Full user interface
- **Demo Mode**: Pre-loaded sample data

### Option 2: Android Studio
1. Open the project in Android Studio
2. Sync Gradle files
3. Run on emulator or device

## Sample Calculation

### Input Data
- Gold Rate: ₹9,100/g
- Silver Rate: ₹108/g

### Items
1. **Silver Item 1 (s1)**
   - Weight: 10g × 4 = 40g
   - Wastage: 4g (10%)
   - Metal Cost: 44g × ₹108 = ₹4,752
   - Making Charge: ₹250
   - **Final Price: ₹5,002**

2. **Silver Item 2 (s2)**
   - Weight: 25g × 3 = 75g
   - Wastage: 7.5g (10%)
   - Metal Cost: 82.5g × ₹108 = ₹8,910
   - Making Charge: ₹350
   - **Final Price: ₹9,260**

3. **Gold Item 1 (g1)**
   - Weight: 15g × 1 = 15g
   - Wastage: 1.5g (10%)
   - Metal Cost: 16.5g × ₹9,100 = ₹150,150
   - Making Charge: ₹1,500
   - **Final Price: ₹151,650**

### Totals
- Silver Total: ₹14,262
- Gold Total: ₹151,650
- **Grand Total: ₹165,912**

## Technical Architecture

### MVVM Pattern
- **Model**: Data classes and business logic
- **View**: Compose UI components
- **ViewModel**: State management and UI logic

### State Management
- StateFlow for reactive UI updates
- Coroutines for asynchronous operations
- Immutable data structures

### Navigation
- Jetpack Navigation Compose
- Type-safe argument passing
- Proper back stack management

## Development

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8.0+
- Android SDK 33+
- JDK 8+

### Building
```bash
./gradlew build
```

### Testing
```bash
./gradlew test
```

## License

This project is developed as a demonstration of Android application development with Kotlin and Jetpack Compose.

## Future Enhancements

- Customer information storage
- Bill printing functionality
- Inventory management
- Sales reporting
- Tax calculations
- Multi-currency support
- Cloud synchronization
